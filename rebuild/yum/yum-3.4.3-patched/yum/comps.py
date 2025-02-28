#! /usr/bin/python2 -tt
# This program is free software; you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation; either version 2 of the License, or
# (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU Library General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program; if not, write to the Free Software
# Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
# Copyright 2005 Duke University

import types
import sys
from yum.constants import *
from yum.Errors import CompsException
#FIXME - compsexception isn't caught ANYWHERE so it's pointless to raise it
# switch all compsexceptions to grouperrors after api break
import fnmatch
import re
from yum.i18n import to_unicode
from yum.misc import get_my_lang_code
from yum.misc import cElementTree_iterparse as iterparse 

lang_attr = '{http://www.w3.org/XML/1998/namespace}lang'

def parse_boolean(strng):
    return BOOLEAN_STATES.get(strng.lower(), False)

def parse_number(strng):
    return int(strng)

class CompsObj(object):
    """ Group/Category helper object. """

    # Could be the same as ui_name?
    def __str__(self):
        """ Return the "name" of the object for the C locale. """
        return self.name

    @property
    def compsid(self):
        """ Return the "id": categoryid, groupid, environmentid. """

        for idT in ('categoryid', 'groupid', 'environmentid'):
            if hasattr(self, idT):
                return getattr(self, idT)

        return None

    @property
    def ui_name(self):
        """ Return the "name" of the object for the current locale. """
        return self.nameByLang(get_my_lang_code())
    
    @property
    def ui_description(self):
        """ Return the "description" of the object for the current locale. """
        return self.descriptionByLang(get_my_lang_code())

    def __cmp__(self, other):
        if other is None:
            return 1

        if self.display_order > other.display_order:
            return 1
        if self.display_order < other.display_order:
            return -1

        return cmp(self.ui_name, other.ui_name)

    def _expand_languages(self, lang):
        import gettext
        languages = [lang]

        if 'C' not in languages:
            languages.append('C')
         
        # now normalize and expand the languages
        nelangs = []
        for lang in languages:
            for nelang in gettext._expand_lang(lang):
                if nelang not in nelangs:
                    nelangs.append(nelang)
        return nelangs
        
    def nameByLang(self, lang):

        for langcode in self._expand_languages(lang):
            if langcode in self.translated_name:
                return to_unicode(self.translated_name[langcode])

        return to_unicode(self.name)

    def descriptionByLang(self, lang):
        for langcode in self._expand_languages(lang):
            if langcode in self.translated_description:
                return to_unicode(self.translated_description[langcode])
        return to_unicode(self.description)


class Group(CompsObj):
    """ Group object parsed from group data in each repo. and merged. """

    def __init__(self, elem=None):
        self.user_visible = True
        self.default = False
        self.selected = False
        self.name = ""
        self.description = ""
        self.translated_name = {}
        self.translated_description = {}
        self.mandatory_packages = {}
        self.optional_packages = {}
        self.default_packages = {}
        self.conditional_packages = {}
        self.langonly = None
        self.groupid = None
        self.display_order = 1024
        self.installed = False
        self.toremove = False

        self._weak = False

        if elem:
            self.parse(elem)

    def _packageiter(self):
        # Gah, FIXME: real iterator/class
        lst = self.mandatory_packages.keys() + \
              self.optional_packages.keys() + \
              self.default_packages.keys() + \
              self.conditional_packages.keys()

        return lst

    packages = property(_packageiter)

    def parse(self, elem):
        for child in elem:

            if child.tag == 'id':
                myid = child.text
                if self.groupid is not None:
                    raise CompsException
                self.groupid = myid
            
            elif child.tag == 'name':
                text = child.text
                if text:
                    text = text.encode('utf8')
                
                lang = child.attrib.get(lang_attr)
                if lang:
                    self.translated_name[lang] = text
                else:
                    self.name = text
    
    
            elif child.tag == 'description':
                text = child.text
                if text:
                    text = text.encode('utf8')
                    
                lang = child.attrib.get(lang_attr)
                if lang:
                    self.translated_description[lang] = text
                else:
                    if text:
                        self.description = text
    
            elif child.tag == 'uservisible':
                self.user_visible = parse_boolean(child.text)
    
            elif child.tag == 'display_order':
                self.display_order = parse_number(child.text)

            elif child.tag == 'default':
                self.default = parse_boolean(child.text)
    
            elif child.tag in ['langonly', 'lang_only']: 
                text = child.text
                if self.langonly is not None:
                    raise CompsException
                self.langonly = text
    
            elif child.tag == 'packagelist':
                self.parse_package_list(child)
    
    def parse_package_list(self, packagelist_elem):
        for child in packagelist_elem:
            if child.tag == 'packagereq':
                genre = child.attrib.get('type')
                if not genre:
                    genre = u'mandatory'

                if genre not in ('mandatory', 'default', 'optional', 'conditional'):
                    # just ignore bad package lines
                    continue

                package = child.text
                if not package:
                    #  Ignore this too, or we end up doing:
                    # .searchNevra(name=None)
                    continue
                if genre == 'mandatory':
                    self.mandatory_packages[package] = 1
                elif genre == 'default':
                    self.default_packages[package] = 1
                elif genre == 'optional':
                    self.optional_packages[package] = 1
                elif genre == 'conditional':
                    self.conditional_packages[package] = child.attrib.get('requires')



    def add(self, obj):
        """Add another group object to this object"""
    
        # we only need package lists and any translation that we don't already
        # have
        
        for pkg in obj.mandatory_packages:
            self.mandatory_packages[pkg] = 1
        for pkg in obj.default_packages:
            self.default_packages[pkg] = 1
        for pkg in obj.optional_packages:
            self.optional_packages[pkg] = 1
        for pkg in obj.conditional_packages:
            self.conditional_packages[pkg] = obj.conditional_packages[pkg]
        
        # Handle cases where a comps.xml without name & decription tags
        # has been setup first, so the name & decription for this object is blank.
            
        
        if self.name == '' and obj.name != '':
            self.name = obj.name

        if self.description == '' and obj.description != '':
            self.description = obj.description
            
        # name and description translations
        for lang in obj.translated_name:
            if lang not in self.translated_name:
                self.translated_name[lang] = obj.translated_name[lang]
        
        for lang in obj.translated_description:
            if lang not in self.translated_description:
                self.translated_description[lang] = obj.translated_description[lang]
        
    def xml(self):
        """write out an xml stanza for the group object"""
        msg ="""        
  <group>
   <id>%s</id>
   <default>%s</default>
   <uservisible>%s</uservisible>
   <display_order>%s</display_order>\n""" % (self.groupid, str(self.default).lower(), 
                                  str(self.user_visible).lower(), self.display_order)
   
        if self.langonly:
            msg += """   <langonly>%s</langonly>""" % self.langonly
            
        msg +="""   <name>%s</name>\n""" % self.name
        for (lang, val) in sorted(self.translated_name.items()):
            msg += """   <name xml:lang="%s">%s</name>\n""" % (lang, val)
        
        msg += """   <description>%s</description>\n""" % self.description
        for (lang, val) in sorted(self.translated_description.items()):
            msg += """   <description xml:lang="%s">%s</description>\n""" % (lang, val)

        msg += """    <packagelist>\n"""
        for pkg in sorted(self.mandatory_packages):
            msg += """      <packagereq type="mandatory">%s</packagereq>\n""" % pkg
        for pkg in sorted(self.default_packages):
            msg += """      <packagereq type="default">%s</packagereq>\n""" % pkg
        for pkg in sorted(self.optional_packages):
            msg += """      <packagereq type="optional">%s</packagereq>\n""" % pkg
        for (pkg, req) in sorted(self.conditional_packages.items()):
            msg += """      <packagereq type="conditional" requires="%s">%s</packagereq>\n""" % (req, pkg)
        msg += """    </packagelist>\n"""
        msg += """  </group>"""

        return msg      

class Environment(CompsObj):
    """ Environment object parsed from group data in each repo, and merged """

    def __init__(self, elem=None):
        self.name = ""
        self.environmentid = None
        self.description = ""
        self.translated_name = {}
        self.translated_description = {}
        self.display_order = 1024
        self.langonly = None
        self.installed = False
        self._groups = {}
        self._options = {}
        self._defaultoptions = {}

        self._weak = False

        if elem:
            self.parse(elem)

    def _allgroupiter(self):
        lst = self._groups.keys() + \
              self._options.keys()
        return lst

    allgroups = property(_allgroupiter)

    def _groupiter(self):
        return self._groups.keys()

    groups = property(_groupiter)

    def _optioniter(self):
        return self._options.keys()

    options = property(_optioniter)

    def _defaultoptioniter(self):
        return self._defaultoptions.keys()

    defaultoptions = property(_defaultoptioniter)

    def parse(self, elem):
        for child in elem:
            if child.tag == 'id':
                myid = child.text
                if self.environmentid is not None:
                    raise CompsException
                self.environmentid = myid

            elif child.tag == 'name':
                text = child.text
                if text:
                    text = text.encode('utf8')

                lang = child.attrib.get(lang_attr)
                if lang:
                    self.translated_name[lang] = text
                else:
                    self.name = text

            elif child.tag == 'description':
                text = child.text
                if text:
                    text = text.encode('utf8')

                lang = child.attrib.get(lang_attr)
                if lang:
                    self.translated_description[lang] = text
                else:
                    self.description = text

            elif child.tag == 'grouplist':
                self.parse_group_list(child)

            elif child.tag == 'optionlist':
                self.parse_option_list(child)

            elif child.tag == 'display_order':
                self.display_order = parse_number(child.text)

    def parse_group_list(self, grouplist_elem):
        for child in grouplist_elem:
            if child.tag == 'groupid':
                groupid = child.text
                self._groups[groupid] = 1

    def parse_option_list(self, optionlist_elem):
        for child in optionlist_elem:
            if child.tag == 'groupid':
                optionid = child.text
                self._options[optionid] = 1
                defopt = child.attrib.get('default')
                if defopt:
                    default = parse_boolean(defopt)
                else:
                    default = False
                if default:
                    self._defaultoptions[optionid] = 1

    def add(self, obj):
        """Add another environment object to this object"""

        for grp in obj.groups:
            self._groups[grp] = 1

        for grp in obj.defaultoptions:
            self._defaultoptions[grp] = 1

        for grp in obj.options:
            self._options[grp] = 1

        # name and description translations
        for lang in obj.translated_name:
            if lang not in self.translated_name:
                self.translated_name[lang] = obj.translated_name[lang]

        for lang in obj.translated_description:
            if lang not in self.translated_description:
                self.translated_description[lang] = obj.translated_description[lang]

    def xml(self):
        """write out an xml stanza for the environment object"""
        msg ="""
  <environment>
   <id>%s</id>
   <display_order>%s</display_order>\n""" % (self.environmentid, self.display_order)

        msg +="""   <name>%s</name>\n""" % self.name
        for (lang, val) in self.translated_name.items():
            msg += """   <name xml:lang="%s">%s</name>\n""" % (lang, val)

        msg += """   <description>%s</description>\n""" % self.description
        for (lang, val) in self.translated_description.items():
            msg += """    <description xml:lang="%s">%s</description>\n""" % (lang, val)

        msg += """    <grouplist>\n"""
        for grp in self.groups:
            msg += """     <groupid>%s</groupid>\n""" % grp
        msg += """    </grouplist>\n"""
        msg += """    <optionlist>\n"""
        for grp in self.options:
            if grp in self.defaultoptions:
                msg += """     <groupid default="true">%s</groupid>\n""" % grp
            else:
                msg += """     <groupid>%s</groupid>\n""" % grp
        msg += """    </optionlist>\n"""
        msg += """  </environment>\n"""

        return msg

class Category(CompsObj):
    """ Category object parsed from group data in each repo. and merged. """

    def __init__(self, elem=None):
        self.name = ""
        self.categoryid = None
        self.description = ""
        self.translated_name = {}
        self.translated_description = {}
        self.display_order = 1024
        self._groups = {}        

        if elem:
            self.parse(elem)
            
    def _groupiter(self):
        return self._groups.keys()
    
    groups = property(_groupiter)
    
    def parse(self, elem):
        for child in elem:
            if child.tag == 'id':
                myid = child.text
                if self.categoryid is not None:
                    raise CompsException
                self.categoryid = myid

            elif child.tag == 'name':
                text = child.text
                if text:
                    text = text.encode('utf8')
                    
                lang = child.attrib.get(lang_attr)
                if lang:
                    self.translated_name[lang] = text
                else:
                    self.name = text
    
            elif child.tag == 'description':
                text = child.text
                if text:
                    text = text.encode('utf8')
                    
                lang = child.attrib.get(lang_attr)
                if lang:
                    self.translated_description[lang] = text
                else:
                    self.description = text
            
            elif child.tag == 'grouplist':
                self.parse_group_list(child)

            elif child.tag == 'display_order':
                self.display_order = parse_number(child.text)

    def parse_group_list(self, grouplist_elem):
        for child in grouplist_elem:
            if child.tag == 'groupid':
                groupid = child.text
                self._groups[groupid] = 1

    def add(self, obj):
        """Add another category object to this object"""
    
        for grp in obj.groups:
            self._groups[grp] = 1
        
        # name and description translations
        for lang in obj.translated_name:
            if lang not in self.translated_name:
                self.translated_name[lang] = obj.translated_name[lang]
        
        for lang in obj.translated_description:
            if lang not in self.translated_description:
                self.translated_description[lang] = obj.translated_description[lang]

    def xml(self):
        """write out an xml stanza for the category object"""
        msg ="""        
  <category>
   <id>%s</id>
   <display_order>%s</display_order>\n""" % (self.categoryid, self.display_order)
   
        msg +="""   <name>%s</name>\n""" % self.name
        for (lang, val) in self.translated_name.items():
            msg += """   <name xml:lang="%s">%s</name>\n""" % (lang, val)
        
        msg += """   <description>%s</description>\n""" % self.description
        for (lang, val) in self.translated_description.items():
            msg += """    <description xml:lang="%s">%s</description>\n""" % (lang, val)

        msg += """    <grouplist>\n"""
        for grp in self.groups:
            msg += """     <groupid>%s</groupid>\n""" % grp
        msg += """    </grouplist>\n"""
        msg += """  </category>\n"""

        return msg                

class Langpacks(CompsObj):
    def __init__(self, elem=None):
        self.langpacks = []
        self.name = "" # prevent CompsObj.__str__() throwing an AttributeError
        if elem is not None:
            self.parse(elem)

    def __getitem__(self, indx):
        return self.langpacks[indx]

    def __iter__(self):
        for i in self.langpacks:
            yield i

    def __len__(self):
        return len(self.langpacks)

    def add(self, name, install):
        langpack = {
            "name": name,
            "install": install,
        }
        self.langpacks.append(langpack)

    def parse(self, elem):
        for child in elem:
            if child.tag == "match":
                langpack = {
                    "name": child.attrib.get("name"),
                    "install": child.attrib.get("install"),
                }
                self.langpacks.append(langpack)
            else:
                raise CompsException("Unexpected element in <langpacks>: %s" % child.tag)

        self.name = elem.attrib.get("name")
        self.install = elem.attrib.get("install")

    def xml(self):
        """write out an xml stanza for the Langpacks object"""
        if not self.langpacks:
            return ''
        msg  = '  <langpacks>\n'
        for i in self:
            msg += '    <match name="%s" install="%s"/>\n' % (i["name"], i["install"])
        msg += '  </langpacks>\n'
        return msg

class Comps(object):
    def __init__(self, overwrite_groups=False):
        self._groups = {}
        self._environments = {}
        self._categories = {}
        self._langpacks = Langpacks()
        self.compscount = 0
        self.overwrite_groups = overwrite_groups
        self.compiled = False # have groups been compiled into avail/installed 
                              # lists, yet.


    def get_groups(self):
        grps = self._groups.values()
        grps.sort(key=lambda x: (x.display_order, x.name))
        return grps

    def get_environments(self):
        environments = self._environments.values()
        environments.sort(key=lambda x: (x.display_order, x.name))
        return environments

    def get_categories(self):
        cats = self._categories.values()
        cats.sort(key=lambda x: (x.display_order, x.name))
        return cats

    def get_langpacks(self):
        return self._langpacks
    
    groups = property(get_groups)
    environments = property(get_environments)
    categories = property(get_categories)
    langpacks = property(get_langpacks)
    
    def has_group(self, grpid):
        exists = self.return_groups(grpid)
            
        if exists:
            return True
            
        return False
    
    def return_group(self, grpid):
        """Return the first group which matches"""
        grps = self.return_groups(grpid)
        if grps:
            return grps[0]

        return None

    def return_groups(self, group_pattern, case_sensitive=False):
        """return all groups which match either by glob or exact match"""
        returns = {}

        if not group_pattern:
            return []

        for item in group_pattern.split(','):
            item = item.strip()
            if item in self._groups:
                thisgroup = self._groups[item]
                returns[thisgroup.groupid] = thisgroup
                continue
            
            if case_sensitive:
                match = re.compile(fnmatch.translate(item)).match
            else:
                match = re.compile(fnmatch.translate(item), flags=re.I).match

            done = False
            for group in self.groups:
                for name in group.name, group.groupid, group.ui_name:
                    if match(name):
                        done = True
                        returns[group.groupid] = group
                        break
            if done:
                continue

            # If we didn't match to anything in the current locale, try others
            for group in self.groups:
                for name in group.translated_name.values():
                    if match(name):
                        returns[group.groupid] = group
                        break

        return returns.values()

    def has_environment(self, environmentid):
        exists = self.return_environments(environmentid)

        if exists:
            return True

        return False

    def return_environment(self, environmentid):
        """Return the first group which matches"""
        environments = self.return_environments(environmentid)
        if environments:
            return environments[0]

        return None

    def return_environments(self, env_pattern, case_sensitive=False):
        """return all environments which match either by glob or exact match"""
        returns = {}

        if not env_pattern:
            return []

        for item in env_pattern.split(','):
            item = item.strip()
            if item in self._environments:
                env = self._environments[item]
                returns[env.environmentid] = env
                continue

            if case_sensitive:
                match = re.compile(fnmatch.translate(item)).match
            else:
                match = re.compile(fnmatch.translate(item), flags=re.I).match

            done = False
            for env in self.environments:
                for name in env.name, env.environmentid, env.ui_name:
                    if match(name):
                        done = True
                        returns[env.environmentid] = env
                        break
            if done:
                continue

            # If we didn't match to anything in the current locale, try others
            for env in self.environments:
                for name in env.translated_name.values():
                    if match(name):
                        returns[env.environmentid] = env
                        break

        return returns.values()

    #  This is close to returnPackages() etc. API ... need to std. these names
    # the above return_groups uses different, but equal, API.
    def return_categories(self, pattern, ignore_case=True):
        """return all categories which match either by glob or exact match"""
        returns = {}

        for item in pattern.split(','):
            item = item.strip()
            if item in self._categories:
                cat = self._categories[item]
                returns[cat.categoryid] = cat
                continue

            if not ignore_case:
                match = re.compile(fnmatch.translate(item)).match
            else:
                match = re.compile(fnmatch.translate(item), flags=re.I).match

            done = False
            for cat in self.categories:
                for name in cat.name, cat.categoryid, cat.ui_name:
                    if match(name):
                        done = True
                        returns[cat.categoryid] = cat
                        break
            if done:
                continue

            for cat in self.categories:
                for name in cat.translated_name.values():
                    if match(name):
                        returns[cat.categoryid] = cat
                        break

        return returns.values()

    def add_group(self, group):
        if group.groupid in self._groups:
            thatgroup = self._groups[group.groupid]
            if thatgroup._weak:
                # If what we had was weak, use this one and merge the weak one.
                tmp = group
                group = thatgroup
                thatgroup = self._groups[group.groupid] = tmp
            thatgroup.add(group)
        else:
            self._groups[group.groupid] = group

    def add_environment(self, environment):
        if environment.environmentid in self._environments:
            env = self._environments[environment.environmentid]
            if env._weak:
                # If what we had was weak, use this one and merge the weak one.
                tmp = environment
                environment = env
                env = self._environments[environment.environmentid] = tmp
            env.add(environment)
        else:
            self._environments[environment.environmentid] = environment

    def add_category(self, category):
        if category.categoryid in self._categories:
            thatcat = self._categories[category.categoryid]
            thatcat.add(category)
        else:
            self._categories[category.categoryid] = category

    def add_langpack(self, name, install):
        self._langpacks.add(name, install)

    def add(self, srcfile = None):
        if not srcfile:
            raise CompsException
            
        if type(srcfile) in types.StringTypes:
            # srcfile is a filename string
            try:
                infile = open(srcfile, 'rt')
            except IOError, e:
                raise CompsException, 'open(%s): #%u %s' % (srcfile, e.errno, e.strerror)
        else:
            # srcfile is a file object
            infile = srcfile
        
        self.compscount += 1
        self.compiled = False
        
        parser = iterparse(infile)
        try:
            for event, elem in parser:
                if elem.tag == "group":
                    group = Group(elem)
                    self.add_group(group)
                if elem.tag == "environment":
                    environment = Environment(elem)
                    self.add_environment(environment)
                if elem.tag == "category":
                    category = Category(elem)
                    self.add_category(category)
                if elem.tag == "langpacks":
                    self._langpacks.parse(elem)
        except SyntaxError, e:
            raise CompsException, "comps file is empty/damaged"
            
        del parser
        
    def compile(self, pkgtuplist):
        """ compile the groups into installed/available groups """
        
        # convert the tuple list to a simple dict of pkgnames
        inst_pkg_names = {}
        for (n,a,e,v,r) in pkgtuplist:
            inst_pkg_names[n] = 1
        

        for group in self.groups:
            # if there are mandatory packages in the group, then make sure
            # they're all installed.  if any are missing, then the group
            # isn't installed.
            if len(group.mandatory_packages) > 0:
                group.installed = True
                for pkgname in group.mandatory_packages:
                    if pkgname not in inst_pkg_names:
                        group.installed = False
                        break
            # if it doesn't have any of those then see if it has ANY of the
            # optional/default packages installed.
            # If so - then the group is installed
            else:
                check_pkgs = group.optional_packages.keys() + group.default_packages.keys() + group.conditional_packages.keys()
                group.installed = False
                for pkgname in check_pkgs:
                    if pkgname in inst_pkg_names:
                        group.installed = True
                        break

        # Now do basically the same thing for evgroups.
        inst_grp_names = {}
        for group in self.groups:
            inst_grp_names[group.groupid] = group.installed
        for evgroup in self.environments:
            if evgroup.groups:
                evgroup.installed = True
                for grpname in evgroup.groups:
                    if not inst_grp_names.get(grpname):
                        evgroup.installed = False
                        break
            else:
                evgroup.installed = False
                for grpname in evgroup.options:
                    if grpname in inst_grp_names:
                        evgroup.installed = True
                        break
        
        self.compiled = True
    
    def xml(self):
        """returns the xml of the comps files in this class, merged"""

        if not self._groups and not self._categories and \
            not self._environments and not len(self._langpacks):
            return ""
            
        msg = """<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE comps PUBLIC "-//Red Hat, Inc.//DTD Comps info//EN" "comps.dtd">
<comps>
""" 
 
        for g in self.get_groups():
            msg += g.xml()
        for c in self.get_categories():
            msg += c.xml()
        for e in self.get_environments():
            msg += e.xml()
        msg += self.get_langpacks().xml()
        msg += """\n</comps>\n"""
        
        return msg
            
        
        
def main():

    try:
        print sys.argv[1]
        p = Comps()
        for srcfile in sys.argv[1:]:
            p.add(srcfile)

        print
        print "===== GROUPS ====="
        for group in p.groups:
            print "%s (id: %s)" % (group, group.groupid)
            for pkg in group.packages:
                print '  ' + pkg

        print
        print "===== ENVIRONMENTS ====="
        for environment in p.environments:
            print "%s (id: %s)" % (environment.name, environment.environmentid)
            for group in environment.groups:
                print '  ' + group
            for group in environment.options:
                print '  *' + group

        print
        print "===== CATEGORIES ====="
        for category in p.categories:
            print "%s (id: %s)" % (category.name, category.categoryid)
            for group in category.groups:
                print '  ' + group

        print
        print "===== LANGPACKS ====="
        for langpack in p.langpacks:
            print '  %s (%s)' % (langpack["name"], langpack["install"])

    except IOError:
        print >> sys.stderr, "newcomps.py: No such file:\'%s\'" % sys.argv[1]
        sys.exit(1)
        
if __name__ == '__main__':
    main()

