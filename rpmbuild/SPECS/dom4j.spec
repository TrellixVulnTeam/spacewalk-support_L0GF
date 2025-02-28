# Copyright (c) 2000-2007, JPackage Project
# All rights reserved.
#
# Redistribution and use in source and binary forms, with or without
# modification, are permitted provided that the following conditions
# are met:
#
# 1. Redistributions of source code must retain the above copyright
#    notice, this list of conditions and the following disclaimer.
# 2. Redistributions in binary form must reproduce the above copyright
#    notice, this list of conditions and the following disclaimer in the
#    documentation and/or other materials provided with the
#    distribution.
# 3. Neither the name of the JPackage Project nor the names of its
#    contributors may be used to endorse or promote products derived
#    from this software without specific prior written permission.
#
# THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
# "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
# LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
# A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
# OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
# SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
# LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
# DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
# THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
# (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
# OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
#

Summary:        Open Source XML framework for Java
Name:           dom4j
Version:        1.6.1
Release:        20%{?dist}
Epoch:          0
License:        BSD
URL:            http://www.dom4j.org/
Group:          Development/Libraries
# ./create-tarball.sh %%{version}
Source0:        %{name}-%{version}-clean.tar.xz
Source1:        dom4j_rundemo.sh
Source2:        http://repo1.maven.org/maven2/%{name}/%{name}/%{version}/%{name}-%{version}.pom
Source3:        create-tarball.sh
Patch0:         dom4j-1.6.1-build_xml.patch
# See https://bugzilla.redhat.com/show_bug.cgi?id=976180
Patch1:         dom4j-1.6.1-Remove-references-to-ConcurrentReaderHashMap.patch
BuildRequires:  jpackage-utils >= 0:1.6
BuildRequires:  ant >= 0:1.6
#BuildRequires:  junit
BuildRequires:  jtidy
#BuildRequires:  junitperf
BuildRequires:  isorelax
BuildRequires:  jaxen-bootstrap >= 0:1.1-0.b7
BuildRequires:  msv-msv
BuildRequires:  relaxngDatatype
BuildRequires:  bea-stax
BuildRequires:  bea-stax-api
BuildRequires:  ws-jaxme
BuildRequires:  xalan-j2
BuildRequires:  xerces-j2
BuildRequires:  jaxp = 1.2
BuildRequires:  xpp3
BuildRequires:  msv-xsdlib
Requires:  xpp3
Requires:  xerces-j2
Requires:  msv-msv
Requires:  msv-xsdlib
Requires:  relaxngDatatype
Requires:  isorelax
Requires:  jaxen-bootstrap >= 0:1.1-0.b7
Requires:  jpackage-utils >= 0:1.6
Requires:  bea-stax
Requires:  bea-stax-api
Requires:  ws-jaxme
Requires:  xalan-j2
BuildArch:      noarch

%description
dom4j is an Open Source XML framework for Java. dom4j allows you to read,
write, navigate, create and modify XML documents. dom4j integrates with 
DOM and SAX and is seamlessly integrated with full XPath support. 

%package demo
Summary:        Samples for %{name}
Group:          Documentation
Requires:       dom4j = 0:%{version}-%{release}

%description demo
Samples for %{name}.

%package manual
Summary:        Manual for %{name}
Group:          Documentation

%description manual
Documentation for %{name}.

%package javadoc
Summary:        Javadoc for %{name}
Group:          Documentation

%description javadoc
Javadoc for %{name}.


%prep
%setup -q 
# replace run.sh
cp -p %{SOURCE1} run.sh
# fix for deleted jars
mv build.xml build.xml.orig
sed -e '/unjar/d' -e 's|,cookbook/\*\*,|,|' build.xml.orig > build.xml

%patch0 -b .sav
%patch1 -p1

# XPP2 is no longer developed and is on maintenance mode.
# Packages (if any) using XPPReader should migrate to XPP3Reader.
rm -rf src/java/org/dom4j/{xpp,io/XPPReader.java}
sed -i s/XPPReader/XPP3Reader/g src/samples/org/dom4j/samples/{performance/PullParseTest,PullParserDemo}.java

%build
pushd lib
ln -sf $(build-classpath relaxngDatatype)
ln -sf $(build-classpath jaxme/jaxmeapi) 
ln -sf $(build-classpath msv-xsdlib) 
ln -sf $(build-classpath msv-msv) 
ln -sf $(build-classpath jaxen) 
ln -sf $(build-classpath bea-stax-api) 
#pushd test
#ln -sf $(build-classpath bea-stax-ri)
#ln -sf $(build-classpath junitperf)
#ln -sf $(build-classpath junit)
#popd
ln -sf $(build-classpath xpp3) 
pushd tools
ln -sf $(build-classpath jaxme/jaxmexs) 
ln -sf $(build-classpath xalan-j2) 
ln -sf $(build-classpath jaxme/jaxmejs) 
ln -sf $(build-classpath jtidy) 
ln -sf $(build-classpath isorelax) 
ln -sf $(build-classpath jaxme/jaxme2) 
ln -sf $(build-classpath xerces-j2) 
popd
popd

# FIXME: test needs to be fixed
ant all samples # test

%install
# jars
mkdir -p $RPM_BUILD_ROOT%{_javadir}
cp -p build/%{name}.jar \
  $RPM_BUILD_ROOT%{_javadir}/%{name}.jar

# javadoc
mkdir -p $RPM_BUILD_ROOT%{_javadocdir}/%{name}
pushd build/doc/javadoc
for f in `find -name \*.html -o -name \*.css`; do
  sed -i 's/\r//g' $f;
done
popd
cp -pr build/doc/javadoc/* $RPM_BUILD_ROOT%{_javadocdir}/%{name}

# manual
mkdir -p $RPM_BUILD_ROOT%{_docdir}/%{name}-%{version}
rm -rf docs/apidocs docs/clover
pushd docs
for f in `find -name \*.html -o -name \*.css -o -name \*.java`; do
  sed -i 's/\r//g' $f;
done
popd
cp -pr docs/* $RPM_BUILD_ROOT%{_docdir}/%{name}-%{version}
tr -d \\r <LICENSE.txt >tmp.file; mv tmp.file LICENSE.txt
cp -p LICENSE.txt $RPM_BUILD_ROOT%{_docdir}/%{name}-%{version}

# demo
mkdir -p $RPM_BUILD_ROOT%{_datadir}/%{name}-%{version}/classes/org/dom4j
cp -pr xml $RPM_BUILD_ROOT%{_datadir}/%{name}-%{version}
mkdir -p $RPM_BUILD_ROOT%{_datadir}/%{name}-%{version}/src
cp -pr src/samples $RPM_BUILD_ROOT%{_datadir}/%{name}-%{version}/src
cp -pr build/classes/org/dom4j/samples $RPM_BUILD_ROOT%{_datadir}/%{name}-%{version}/classes/org/dom4j
install -m 755 run.sh $RPM_BUILD_ROOT%{_datadir}/%{name}-%{version}

# POM and depmap
install -d -m 755 $RPM_BUILD_ROOT%{_mavenpomdir}
install -p -m 644 %{SOURCE2} $RPM_BUILD_ROOT%{_mavenpomdir}/JPP-%{name}.pom
%add_maven_depmap

%files
%dir %{_docdir}/%{name}-%{version}
%doc %{_docdir}/%{name}-%{version}/LICENSE.txt
%{_javadir}/%{name}.jar
%{_mavenpomdir}/JPP-%{name}.pom
%{_mavendepmapfragdir}/%{name}

%files javadoc
%dir %{_docdir}/%{name}-%{version}
%doc %{_docdir}/%{name}-%{version}/LICENSE.txt
%{_javadocdir}/*

%files manual
%doc %{_docdir}/%{name}-%{version}

%files demo
%attr(0755,root,root) %{_datadir}/%{name}-%{version}/run.sh
%{_datadir}/%{name}-%{version}

%changelog
* Fri Dec 27 2013 Daniel Mach <dmach@redhat.com> - 01.6.1-20
- Mass rebuild 2013-12-27

* Fri Aug 02 2013 Michal Srb <msrb@redhat.com> - 0:1.6.1-19
- Add create-tarball.sh script to SRPM

* Thu Jul 25 2013 Michal Srb <msrb@redhat.com> - 0:1.6.1-18
- Properly remove references to ConcurrentReaderHashMap

* Tue Jul 02 2013 Michal Srb <msrb@redhat.com> - 0:1.6.1-17
- Remove file with unclear licensing (Resolves: rhbz#976180)

* Fri Jun 28 2013 Mikolaj Izdebski <mizdebsk@redhat.com> - 0:1.6.1-16
- Rebuild to regenerate API documentation
- Resolves: CVE-2013-1571

* Wed Jun 19 2013 Michal Srb <msrb@redhat.com> - 0:1.6.1-15
- Install license file with javadoc subpackage

* Mon May  6 2013 Mikolaj Izdebski <mizdebsk@redhat.com> - 0:1.6.1-14
- Remove XPP2 reader

* Wed Feb 13 2013 Fedora Release Engineering <rel-eng@lists.fedoraproject.org> - 0:1.6.1-13
- Rebuilt for https://fedoraproject.org/wiki/Fedora_19_Mass_Rebuild

* Thu Nov  1 2012 Mikolaj Izdebski <mizdebsk@redhat.com> - 0:1.6.1-12
- Add maven POM

* Mon Oct 29 2012 Mikolaj Izdebski <mizdebsk@redhat.com> - 0:1.6.1-11
- Cleanup source tarball from non-free content
- Resolves: rhbz#848875

* Fri Oct 12 2012 Mikolaj Izdebski <mizdebsk@redhat.com> - 0:1.6.1-10
- Disable test dependencies because tests are skipped

* Wed Jul 18 2012 Fedora Release Engineering <rel-eng@lists.fedoraproject.org> - 0:1.6.1-9
- Rebuilt for https://fedoraproject.org/wiki/Fedora_18_Mass_Rebuild

* Fri Apr 6 2012 Alexander Kurtakov <akurtako@redhat.com> 0:1.6.1-8
- Simplify packaging and remove old things.

* Fri Jan 13 2012 Fedora Release Engineering <rel-eng@lists.fedoraproject.org> - 0:1.6.1-7
- Rebuilt for https://fedoraproject.org/wiki/Fedora_17_Mass_Rebuild

* Tue Feb 08 2011 Fedora Release Engineering <rel-eng@lists.fedoraproject.org> - 0:1.6.1-6
- Rebuilt for https://fedoraproject.org/wiki/Fedora_15_Mass_Rebuild

* Fri Jul 24 2009 Fedora Release Engineering <rel-eng@lists.fedoraproject.org> - 0:1.6.1-5
- Rebuilt for https://fedoraproject.org/wiki/Fedora_12_Mass_Rebuild

* Tue Feb 24 2009 Fedora Release Engineering <rel-eng@lists.fedoraproject.org> - 0:1.6.1-4
- Rebuilt for https://fedoraproject.org/wiki/Fedora_11_Mass_Rebuild

* Wed Jul  9 2008 Tom "spot" Callaway <tcallawa@redhat.com> 1.6.1-3
- drop repotag

* Wed Oct 17 2007 Deepak Bhole <dbhole@redhat.com> 1.6.1-2jpp.3
- Resaolve bz#302321: Add copyright header that was accidentally removed.

* Mon Mar 26 2007 Nuno Santos <nsantos@redhat.com> - 0:1.6.1-2jpp.2
- fix unowned directory

* Wed Feb 14 2007 Jeff Johnston <jjohnstn@redhat.com> - 0:1.6.1-2jpp.1
- Resolves: #227049
- Updated per Fedora package review process
- Modified dom4j-1.6.1-build_xml.patch to include jaxp 1.2 apis on
  boot classpath
- Added new patch for javadocs
- Add buildrequires for jaxp = 1.2

* Mon Jan 30 2006 Ralph Apel <r.apel@r-apel.de> - 0:1.6.1-2jpp
- Change STAX dependency to free bea-stax and bea-stax-api

* Wed Aug 17 2005 Ralph Apel <r.apel@r-apel.de> - 0:1.6.1-1jpp
- Upgrade to 1.6.1
- Now requires xpp3 additionally to xpp2

* Thu Sep 09 2004 Ralph Apel <r.apel@r-apel.de> - 0:1.5-1jpp
- Upgrade to 1.5
- Drop saxpath requirement as this is now included in jaxen

* Fri Aug 20 2004 Ralph Apel <r.apel@r-apel.de> - 0:1.4-3jpp
- Upgrade to Ant 1.6.X
- Build with ant-1.6.2

* Tue Jul 06 2004 Ralph Apel <r.apel@r-apel.de> - 0:1.4-2jpp
- Replace non-free msv with free relaxngDatatype xsdlib isorelax msv-strict
- Relax some versioned dependencies

* Mon Jan 19 2004 Ralph Apel <r.apel@r-apel.de> - 0:1.4-1jpp
- First JPackage release
