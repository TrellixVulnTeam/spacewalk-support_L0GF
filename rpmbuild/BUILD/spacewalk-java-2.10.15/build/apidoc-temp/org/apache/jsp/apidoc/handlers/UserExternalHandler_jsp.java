/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat9
 * Generated at: 2020-03-09 19:26:49 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.apidoc.handlers;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class UserExternalHandler_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = null;
  }

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public javax.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
      throws java.io.IOException, javax.servlet.ServletException {

    if (!javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
      final java.lang.String _jspx_method = request.getMethod();
      if ("OPTIONS".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        return;
      }
      if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPs only permit GET, POST or HEAD. Jasper also permits OPTIONS");
        return;
      }
    }

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("<html>\n<head>\n<meta http-equiv=\"cache-control\" content=\"no-cache\" />\n\n<style type=\"text/css\">\nul.apidoc {\n   list-style-image: url('/img/parent_node.gif');\n}\n\n.deprecated {\n   text-decoration: line-through;\n}\n</style>\n</head>\n<body>\n<div class=\"spacewalk-toolbar-h1\">\n<h1><i class=\"fa fa-gears\"></i>API Overview</h1>\n</div>\n\n<h2>Description</h2>\n<p><strong>Namespace</strong>:\nuser.external\n</p>\n<p>If you are using IPA integration to allow authentication of users from\n an external IPA server (rare) the users will still need to be created in the Satellite\n database. Methods in this namespace allow you to configure some specifics of how this\n happens, like what organization they are created in or what roles they will have.\n These options can also be set in the web admin interface.</p>\n<span class=\"small-text\">( <a href=\"/rhn/apidoc/index.jsp\">Return to API Overview</a> )</span>\n<hr />\n\n<div class=\"rounded-box\">\n<h2><a name=\"top\">Available methods</a></h2>\n<ul class=\"apidoc\">\n\n<li><a href=\"#createExternalGroupToRoleMap\"/>createExternalGroupToRoleMap</a></li>\n");
      out.write("<li><a href=\"#createExternalGroupToSystemGroupMap\"/>createExternalGroupToSystemGroupMap</a></li>\n<li><a href=\"#deleteExternalGroupToRoleMap\"/>deleteExternalGroupToRoleMap</a></li>\n<li><a href=\"#deleteExternalGroupToSystemGroupMap\"/>deleteExternalGroupToSystemGroupMap</a></li>\n<li><a href=\"#getDefaultOrg\"/>getDefaultOrg</a></li>\n<li><a href=\"#getExternalGroupToRoleMap\"/>getExternalGroupToRoleMap</a></li>\n<li><a href=\"#getExternalGroupToSystemGroupMap\"/>getExternalGroupToSystemGroupMap</a></li>\n<li><a href=\"#getKeepTemporaryRoles\"/>getKeepTemporaryRoles</a></li>\n<li><a href=\"#getUseOrgUnit\"/>getUseOrgUnit</a></li>\n<li><a href=\"#listExternalGroupToRoleMaps\"/>listExternalGroupToRoleMaps</a></li>\n<li><a href=\"#listExternalGroupToSystemGroupMaps\"/>listExternalGroupToSystemGroupMaps</a></li>\n<li><a href=\"#setDefaultOrg\"/>setDefaultOrg</a></li>\n<li><a href=\"#setExternalGroupRoles\"/>setExternalGroupRoles</a></li>\n<li><a href=\"#setExternalGroupSystemGroups\"/>setExternalGroupSystemGroups</a></li>\n<li><a href=\"#setKeepTemporaryRoles\"/>setKeepTemporaryRoles</a></li>\n");
      out.write("<li><a href=\"#setUseOrgUnit\"/>setUseOrgUnit</a></li>\n</ul>\n</div>\n<hr />\n\n<h3> <a name=\"createExternalGroupToRoleMap\" href=\"#top\">Method: createExternalGroupToRoleMap</a></h3>\nDescription:<br />\nExternally authenticated users may be members of external groups. You\n can use these groups to assign additional roles to the users when they log in.\n Can only be called by a satellite_admin.\n<p />\n\n\n\n\nParameters:<br />\n<ul>\n<li>\n\n    string sessionKey\n </li>\n<li>\n\n    string name - Name of the external group. Must be\n unique.\n </li>\n<li>\n\narray:\n<ul>\n    <li>string - role - Can be any of:\n satellite_admin, org_admin (implies all other roles except for satellite_admin),\n channel_admin, config_admin, system_group_admin, or\n activation_key_admin.</li>\n</ul>\n </li>\n</ul>\n<p />\nReturns:\n<code><ul><li>\n\n\n\n\n\t     struct - externalGroup\n\t<ul>\n      \t<li> string \"name\"</li>\n          <li>array \"roles\"\n        <ul>\n            <li>string role</li>\n        </ul>\n    </li>\n  \t</ul>\n  \n \n</li></ul>\n</code>\n<p />\n<hr />\n\n<h3> <a name=\"createExternalGroupToSystemGroupMap\" href=\"#top\">Method: createExternalGroupToSystemGroupMap</a></h3>\n");
      out.write("Description:<br />\nExternally authenticated users may be members of external groups. You\n can use these groups to give access to server groups to the users when they log in.\n Can only be called by an org_admin.\n<p />\n\n\n\n\nParameters:<br />\n<ul>\n<li>\n\n    string sessionKey\n </li>\n<li>\n\n    string name - Name of the external group. Must be\n unique.\n </li>\n<li>\n\narray:\n<ul>\n    <li>string - groupName - The names of the server\n groups to grant access to.</li>\n</ul>\n </li>\n</ul>\n<p />\nReturns:\n<code><ul><li>\n\n\n\n\n\t     struct - externalGroup\n\t<ul>\n      \t<li> string \"name\"</li>\n          <li>array \"roles\"\n        <ul>\n            <li>string role</li>\n        </ul>\n    </li>\n  \t</ul>\n  \n \n</li></ul>\n</code>\n<p />\n<hr />\n\n<h3> <a name=\"deleteExternalGroupToRoleMap\" href=\"#top\">Method: deleteExternalGroupToRoleMap</a></h3>\nDescription:<br />\nDelete the role map for an external group. Can only be called\n by a satellite_admin.\n<p />\n\n\n\n\nParameters:<br />\n<ul>\n<li>\n\n    string sessionKey\n </li>\n<li>\n\n    string name - Name of the external group.\n");
      out.write(" </li>\n</ul>\n<p />\nReturns:\n<code><ul><li>\n\n\n    int - 1 on success, exception thrown otherwise.\n \n</li></ul>\n</code>\n<p />\n<hr />\n\n<h3> <a name=\"deleteExternalGroupToSystemGroupMap\" href=\"#top\">Method: deleteExternalGroupToSystemGroupMap</a></h3>\nDescription:<br />\nDelete the server group map for an external group. Can only be called\n by an org_admin.\n<p />\n\n\n\n\nParameters:<br />\n<ul>\n<li>\n\n    string sessionKey\n </li>\n<li>\n\n    string name - Name of the external group.\n </li>\n</ul>\n<p />\nReturns:\n<code><ul><li>\n\n\n    int - 1 on success, exception thrown otherwise.\n \n</li></ul>\n</code>\n<p />\n<hr />\n\n<h3> <a name=\"getDefaultOrg\" href=\"#top\">Method: getDefaultOrg</a></h3>\nDescription:<br />\nGet the default org that users should be added in if orgunit from\n IPA server isn't found or is disabled. Can only be called by a satellite_admin.\n<p />\n\n\n\n\nParameters:<br />\n<ul>\n<li>\n\n    string sessionKey\n </li>\n</ul>\n<p />\nReturns:\n<code><ul><li>\n\n\nint - Id of the default organization. 0 if there is no default. \n \n</li></ul>\n");
      out.write("</code>\n<p />\n<hr />\n\n<h3> <a name=\"getExternalGroupToRoleMap\" href=\"#top\">Method: getExternalGroupToRoleMap</a></h3>\nDescription:<br />\nGet a representation of the role mapping for an external group.\n Can only be called by a satellite_admin.\n<p />\n\n\n\n\nParameters:<br />\n<ul>\n<li>\n\n    string sessionKey\n </li>\n<li>\n\n    string name - Name of the external group.\n </li>\n</ul>\n<p />\nReturns:\n<code><ul><li>\n\n\n\n\n\t     struct - externalGroup\n\t<ul>\n      \t<li> string \"name\"</li>\n          <li>array \"roles\"\n        <ul>\n            <li>string role</li>\n        </ul>\n    </li>\n  \t</ul>\n  \n \n</li></ul>\n</code>\n<p />\n<hr />\n\n<h3> <a name=\"getExternalGroupToSystemGroupMap\" href=\"#top\">Method: getExternalGroupToSystemGroupMap</a></h3>\nDescription:<br />\nGet a representation of the server group mapping for an external\n group. Can only be called by an org_admin.\n<p />\n\n\n\n\nParameters:<br />\n<ul>\n<li>\n\n    string sessionKey\n </li>\n<li>\n\n    string name - Name of the external group.\n </li>\n</ul>\n<p />\nReturns:\n<code><ul><li>\n\n\n\n");
      out.write("\n\t     struct - externalGroup\n\t<ul>\n      \t<li> string \"name\"</li>\n          <li>array \"roles\"\n        <ul>\n            <li>string role</li>\n        </ul>\n    </li>\n  \t</ul>\n  \n \n</li></ul>\n</code>\n<p />\n<hr />\n\n<h3> <a name=\"getKeepTemporaryRoles\" href=\"#top\">Method: getKeepTemporaryRoles</a></h3>\nDescription:<br />\nGet whether we should keeps roles assigned to users because of\n their IPA groups even after they log in through a non-IPA method. Can only be\n called by a satellite_admin.\n<p />\n\n\n\n\nParameters:<br />\n<ul>\n<li>\n\n    string sessionKey\n </li>\n</ul>\n<p />\nReturns:\n<code><ul><li>\n\n\nboolean - True if we should keep roles\n after users log in through non-IPA method, false otherwise. \n \n</li></ul>\n</code>\n<p />\n<hr />\n\n<h3> <a name=\"getUseOrgUnit\" href=\"#top\">Method: getUseOrgUnit</a></h3>\nDescription:<br />\nGet whether we place users into the organization that corresponds\n to the \"orgunit\" set on the IPA server. The orgunit name must match exactly the\n Satellite organization name. Can only be called by a satellite_admin.\n");
      out.write("<p />\n\n\n\n\nParameters:<br />\n<ul>\n<li>\n\n    string sessionKey\n </li>\n</ul>\n<p />\nReturns:\n<code><ul><li>\n\n\nboolean - True if we should use the IPA\n orgunit to determine which organization to create the user in, false otherwise. \n \n</li></ul>\n</code>\n<p />\n<hr />\n\n<h3> <a name=\"listExternalGroupToRoleMaps\" href=\"#top\">Method: listExternalGroupToRoleMaps</a></h3>\nDescription:<br />\nList role mappings for all known external groups. Can only be called\n by a satellite_admin.\n<p />\n\n\n\n\nParameters:<br />\n<ul>\n<li>\n\n    string sessionKey\n </li>\n</ul>\n<p />\nReturns:\n<code><ul><li>\n\n\narray:\n  <ul>\n   <li>\n     \n\n\t     struct - externalGroup\n\t<ul>\n      \t<li> string \"name\"</li>\n          <li>array \"roles\"\n        <ul>\n            <li>string role</li>\n        </ul>\n    </li>\n  \t</ul>\n \n </li></ul>\n \n</li></ul>\n</code>\n<p />\n<hr />\n\n<h3> <a name=\"listExternalGroupToSystemGroupMaps\" href=\"#top\">Method: listExternalGroupToSystemGroupMaps</a></h3>\nDescription:<br />\nList server group mappings for all known external groups. Can only be\n");
      out.write(" called by an org_admin.\n<p />\n\n\n\n\nParameters:<br />\n<ul>\n<li>\n\n    string sessionKey\n </li>\n</ul>\n<p />\nReturns:\n<code><ul><li>\n\n\narray:\n  <ul>\n   <li>\n     \n\n\t     struct - externalGroup\n\t<ul>\n      \t<li> string \"name\"</li>\n          <li>array \"roles\"\n        <ul>\n            <li>string role</li>\n        </ul>\n    </li>\n  \t</ul>\n \n </li></ul>\n \n</li></ul>\n</code>\n<p />\n<hr />\n\n<h3> <a name=\"setDefaultOrg\" href=\"#top\">Method: setDefaultOrg</a></h3>\nDescription:<br />\nSet the default org that users should be added in if orgunit from\n IPA server isn't found or is disabled. Can only be called by a satellite_admin.\n<p />\n\n\n\n\nParameters:<br />\n<ul>\n<li>\n\n    string sessionKey\n </li>\n<li>\n\n    int defaultOrg - Id of the organization to set\n as the default org. 0 if there should not be a default organization.\n </li>\n</ul>\n<p />\nReturns:\n<code><ul><li>\n\n\n    int - 1 on success, exception thrown otherwise.\n \n</li></ul>\n</code>\n<p />\n<hr />\n\n<h3> <a name=\"setExternalGroupRoles\" href=\"#top\">Method: setExternalGroupRoles</a></h3>\n");
      out.write("Description:<br />\nUpdate the roles for an external group. Replace previously set roles\n with the ones passed in here. Can only be called by a satellite_admin.\n<p />\n\n\n\n\nParameters:<br />\n<ul>\n<li>\n\n    string sessionKey\n </li>\n<li>\n\n    string name - Name of the external group.\n </li>\n<li>\n\narray:\n<ul>\n    <li>string - role - Can be any of:\n satellite_admin, org_admin (implies all other roles except for satellite_admin),\n channel_admin, config_admin, system_group_admin, or\n activation_key_admin.</li>\n</ul>\n </li>\n</ul>\n<p />\nReturns:\n<code><ul><li>\n\n\n    int - 1 on success, exception thrown otherwise.\n \n</li></ul>\n</code>\n<p />\n<hr />\n\n<h3> <a name=\"setExternalGroupSystemGroups\" href=\"#top\">Method: setExternalGroupSystemGroups</a></h3>\nDescription:<br />\nUpdate the server groups for an external group. Replace previously set\n server groups with the ones passed in here. Can only be called by an org_admin.\n<p />\n\n\n\n\nParameters:<br />\n<ul>\n<li>\n\n    string sessionKey\n </li>\n<li>\n\n    string name - Name of the external group.\n");
      out.write(" </li>\n<li>\n\narray:\n<ul>\n    <li>string - groupName - The names of the\n server groups to grant access to.</li>\n</ul>\n </li>\n</ul>\n<p />\nReturns:\n<code><ul><li>\n\n\n    int - 1 on success, exception thrown otherwise.\n \n</li></ul>\n</code>\n<p />\n<hr />\n\n<h3> <a name=\"setKeepTemporaryRoles\" href=\"#top\">Method: setKeepTemporaryRoles</a></h3>\nDescription:<br />\nSet whether we should keeps roles assigned to users because of\n their IPA groups even after they log in through a non-IPA method. Can only be\n called by a satellite_admin.\n<p />\n\n\n\n\nParameters:<br />\n<ul>\n<li>\n\n    string sessionKey\n </li>\n<li>\n\n    boolean keepRoles - True if we should keep roles\n after users log in through non-IPA method, false otherwise.\n </li>\n</ul>\n<p />\nReturns:\n<code><ul><li>\n\n\n    int - 1 on success, exception thrown otherwise.\n \n</li></ul>\n</code>\n<p />\n<hr />\n\n<h3> <a name=\"setUseOrgUnit\" href=\"#top\">Method: setUseOrgUnit</a></h3>\nDescription:<br />\nSet whether we place users into the organization that corresponds\n to the \"orgunit\" set on the IPA server. The orgunit name must match exactly the\n");
      out.write(" Satellite organization name. Can only be called by a satellite_admin.\n<p />\n\n\n\n\nParameters:<br />\n<ul>\n<li>\n\n    string sessionKey\n </li>\n<li>\n\n    boolean useOrgUnit - True if we should use the IPA\n orgunit to determine which organization to create the user in, false otherwise.\n </li>\n</ul>\n<p />\nReturns:\n<code><ul><li>\n\n\n    int - 1 on success, exception thrown otherwise.\n \n</li></ul>\n</code>\n<p />\n<hr />\n</body>\n</html>\n");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
