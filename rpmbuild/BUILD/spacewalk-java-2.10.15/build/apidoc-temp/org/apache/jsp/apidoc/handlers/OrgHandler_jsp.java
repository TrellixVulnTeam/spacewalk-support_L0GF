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

public final class OrgHandler_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("<html>\n<head>\n<meta http-equiv=\"cache-control\" content=\"no-cache\" />\n\n<style type=\"text/css\">\nul.apidoc {\n   list-style-image: url('/img/parent_node.gif');\n}\n\n.deprecated {\n   text-decoration: line-through;\n}\n</style>\n</head>\n<body>\n<div class=\"spacewalk-toolbar-h1\">\n<h1><i class=\"fa fa-gears\"></i>API Overview</h1>\n</div>\n\n<h2>Description</h2>\n<p><strong>Namespace</strong>:\norg\n</p>\n<p>Contains methods to access common organization management\n functions available from the web interface.</p>\n<span class=\"small-text\">( <a href=\"/rhn/apidoc/index.jsp\">Return to API Overview</a> )</span>\n<hr />\n\n<div class=\"rounded-box\">\n<h2><a name=\"top\">Available methods</a></h2>\n<ul class=\"apidoc\">\n\n<li><a href=\"#create\"/>create</a></li>\n<li><a href=\"#delete\"/>delete</a></li>\n<li><a href=\"#getCrashFileSizeLimit\"/>getCrashFileSizeLimit</a></li>\n<li><a href=\"#getDetails\"/>getDetails</a></li>\n<li><a href=\"#getDetails\"/>getDetails</a></li>\n<li><a href=\"#getPolicyForScapFileUpload\"/>getPolicyForScapFileUpload</a></li>\n<li><a href=\"#getPolicyForScapResultDeletion\"/>getPolicyForScapResultDeletion</a></li>\n");
      out.write("<li><a href=\"#isCrashReportingEnabled\"/>isCrashReportingEnabled</a></li>\n<li><a href=\"#isCrashfileUploadEnabled\"/>isCrashfileUploadEnabled</a></li>\n<li><a href=\"#isErrataEmailNotifsForOrg\"/>isErrataEmailNotifsForOrg</a></li>\n<li><a href=\"#isOrgConfigManagedByOrgAdmin\"/>isOrgConfigManagedByOrgAdmin</a></li>\n<li><a href=\"#listOrgs\"/>listOrgs</a></li>\n<li><a href=\"#listUsers\"/>listUsers</a></li>\n<li><a href=\"#migrateSystems\"/>migrateSystems</a></li>\n<li><a href=\"#setCrashFileSizeLimit\"/>setCrashFileSizeLimit</a></li>\n<li><a href=\"#setCrashReporting\"/>setCrashReporting</a></li>\n<li><a href=\"#setCrashfileUpload\"/>setCrashfileUpload</a></li>\n<li><a href=\"#setErrataEmailNotifsForOrg\"/>setErrataEmailNotifsForOrg</a></li>\n<li><a href=\"#setOrgConfigManagedByOrgAdmin\"/>setOrgConfigManagedByOrgAdmin</a></li>\n<li><a href=\"#setPolicyForScapFileUpload\"/>setPolicyForScapFileUpload</a></li>\n<li><a href=\"#setPolicyForScapResultDeletion\"/>setPolicyForScapResultDeletion</a></li>\n<li><a href=\"#updateName\"/>updateName</a></li>\n</ul>\n");
      out.write("</div>\n<hr />\n\n<h3> <a name=\"create\" href=\"#top\">Method: create</a></h3>\nDescription:<br />\nCreate a new organization and associated administrator account.\n<p />\n\n\n\n\nParameters:<br />\n<ul>\n<li>\n\n    string sessionKey\n </li>\n<li>\n\n    string orgName - Organization name. Must meet same\n criteria as in the web UI.\n </li>\n<li>\n\n    string adminLogin - New administrator login name.\n </li>\n<li>\n\n    string adminPassword - New administrator password.\n </li>\n<li>\n\n    string prefix - New administrator's prefix. Must\n match one of the values available in the web UI. (i.e. Dr., Mr., Mrs., Sr., etc.)\n </li>\n<li>\n\n    string firstName - New administrator's first name.\n </li>\n<li>\n\n    string lastName - New administrator's first name.\n </li>\n<li>\n\n    string email - New administrator's e-mail.\n </li>\n<li>\n\n    boolean usePamAuth - True if PAM authentication\n should be used for the new administrator account.\n </li>\n</ul>\n<p />\nReturns:\n<code><ul><li>\n\n\n\n\n\t     struct - organization info\n\t<ul>\n   \t<li> int \"id\"</li>\n   \t<li> string \"name\"</li>\n");
      out.write("       <li> int \"active_users\" - Number of active users in the organization. </li>\n       <li> int \"systems\" - Number of systems in the organization. </li>\n       <li> int \"trusts\" - Number of trusted organizations. </li>\n       <li> int \"system_groups\" - Number of system groups in the organization. (optional) </li>\n       <li> int \"activation_keys\" - Number of activation keys in the organization. (optional) </li>\n       <li> int \"kickstart_profiles\" - Number of kickstart profiles in the organization. (optional) </li>\n       <li> int \"configuration_channels\" - Number of configuration channels in the organization. (optional) </li>\n       <li> boolean \"staging_content_enabled\" - Is staging content enabled in organization. (optional) </li>\n \t</ul>\n  \n \n</li></ul>\n</code>\n<p />\n<hr />\n\n<h3> <a name=\"delete\" href=\"#top\">Method: delete</a></h3>\nDescription:<br />\nDelete an organization. The default organization\n (i.e. orgId=1) cannot be deleted.\n<p />\n\n\n\n\nParameters:<br />\n<ul>\n<li>\n\n    string sessionKey\n </li>\n<li>\n");
      out.write("\n    int orgId\n </li>\n</ul>\n<p />\nReturns:\n<code><ul><li>\n\n\n    int - 1 on success, exception thrown otherwise.\n \n</li></ul>\n</code>\n<p />\n<hr />\n\n<h3> <a name=\"getCrashFileSizeLimit\" href=\"#top\">Method: getCrashFileSizeLimit</a></h3>\nDescription:<br />\nGet the organization wide crash file size limit. The limit value\n must be a non-negative number, zero means no limit.\n<p />\n\n\n\n\nParameters:<br />\n<ul>\n<li>\n\n    string sessionKey\n </li>\n<li>\n\n    int orgId\n </li>\n</ul>\n<p />\nReturns:\n<code><ul><li>\n\n\nint - Crash file size limit. \n \n</li></ul>\n</code>\n<p />\n<hr />\n\n<h3> <a name=\"getDetails\" href=\"#top\">Method: getDetails</a></h3>\nDescription:<br />\nThe detailed information about an organization given\n the organization ID.\n<p />\n\n\n\n\nParameters:<br />\n<ul>\n<li>\n\n    string sessionKey\n </li>\n<li>\n\n    int orgId\n </li>\n</ul>\n<p />\nReturns:\n<code><ul><li>\n\n\n\n\n\t     struct - organization info\n\t<ul>\n   \t<li> int \"id\"</li>\n   \t<li> string \"name\"</li>\n       <li> int \"active_users\" - Number of active users in the organization. </li>\n");
      out.write("       <li> int \"systems\" - Number of systems in the organization. </li>\n       <li> int \"trusts\" - Number of trusted organizations. </li>\n       <li> int \"system_groups\" - Number of system groups in the organization. (optional) </li>\n       <li> int \"activation_keys\" - Number of activation keys in the organization. (optional) </li>\n       <li> int \"kickstart_profiles\" - Number of kickstart profiles in the organization. (optional) </li>\n       <li> int \"configuration_channels\" - Number of configuration channels in the organization. (optional) </li>\n       <li> boolean \"staging_content_enabled\" - Is staging content enabled in organization. (optional) </li>\n \t</ul>\n  \n \n</li></ul>\n</code>\n<p />\n<hr />\n\n<h3> <a name=\"getDetails\" href=\"#top\">Method: getDetails</a></h3>\nDescription:<br />\nThe detailed information about an organization given\n the organization name.\n<p />\n\n\n\n\nParameters:<br />\n<ul>\n<li>\n\n    string sessionKey\n </li>\n<li>\n\n    string name\n </li>\n</ul>\n<p />\nReturns:\n<code><ul><li>\n\n\n\n\n\t     struct - organization info\n");
      out.write("\t<ul>\n   \t<li> int \"id\"</li>\n   \t<li> string \"name\"</li>\n       <li> int \"active_users\" - Number of active users in the organization. </li>\n       <li> int \"systems\" - Number of systems in the organization. </li>\n       <li> int \"trusts\" - Number of trusted organizations. </li>\n       <li> int \"system_groups\" - Number of system groups in the organization. (optional) </li>\n       <li> int \"activation_keys\" - Number of activation keys in the organization. (optional) </li>\n       <li> int \"kickstart_profiles\" - Number of kickstart profiles in the organization. (optional) </li>\n       <li> int \"configuration_channels\" - Number of configuration channels in the organization. (optional) </li>\n       <li> boolean \"staging_content_enabled\" - Is staging content enabled in organization. (optional) </li>\n \t</ul>\n  \n \n</li></ul>\n</code>\n<p />\n<hr />\n\n<h3> <a name=\"getPolicyForScapFileUpload\" href=\"#top\">Method: getPolicyForScapFileUpload</a></h3>\nDescription:<br />\nGet the status of SCAP detailed result file upload settings\n");
      out.write(" for the given organization.\n<p />\n\n\n\n\nParameters:<br />\n<ul>\n<li>\n\n        string sessionKey\n </li>\n<li>\n\n    int orgId\n </li>\n</ul>\n<p />\nReturns:\n<code><ul><li>\n\n\n\t     struct - scap_upload_info\n\t<ul>\n             <li> boolean \"enabled\" - Aggregation of detailed SCAP results is enabled. </li>\n             <li> int \"size_limit\" - Limit (in Bytes) for a single SCAP file upload. </li>\n     \t</ul>\n \n</li></ul>\n</code>\n<p />\n<hr />\n\n<h3> <a name=\"getPolicyForScapResultDeletion\" href=\"#top\">Method: getPolicyForScapResultDeletion</a></h3>\nDescription:<br />\nGet the status of SCAP result deletion settings for the given\n organization.\n<p />\n\n\n\n\nParameters:<br />\n<ul>\n<li>\n\n        string sessionKey\n </li>\n<li>\n\n    int orgId\n </li>\n</ul>\n<p />\nReturns:\n<code><ul><li>\n\n\n\t     struct - scap_deletion_info\n\t<ul>\n             <li> boolean \"enabled\" - Deletion of SCAP results is enabled </li>\n             <li> int \"retention_period\" - Period (in days) after which a scan can be deleted (if enabled). </li>\n     \t</ul>\n \n</li></ul>\n");
      out.write("</code>\n<p />\n<hr />\n\n<h3> <a name=\"isCrashReportingEnabled\" href=\"#top\">Method: isCrashReportingEnabled</a></h3>\nDescription:<br />\nGet the status of crash reporting settings for the given organization.\n Returns true if enabled, false otherwise.\n<p />\n\n\n\n\nParameters:<br />\n<ul>\n<li>\n\n    string sessionKey\n </li>\n<li>\n\n    int orgId\n </li>\n</ul>\n<p />\nReturns:\n<code><ul><li>\n\n\nboolean - Get the status of crash reporting settings. \n \n</li></ul>\n</code>\n<p />\n<hr />\n\n<h3> <a name=\"isCrashfileUploadEnabled\" href=\"#top\">Method: isCrashfileUploadEnabled</a></h3>\nDescription:<br />\nGet the status of crash file upload settings for the given organization.\n Returns true if enabled, false otherwise.\n<p />\n\n\n\n\nParameters:<br />\n<ul>\n<li>\n\n    string sessionKey\n </li>\n<li>\n\n    int orgId\n </li>\n</ul>\n<p />\nReturns:\n<code><ul><li>\n\n\nboolean - Get the status of crash file upload settings. \n \n</li></ul>\n</code>\n<p />\n<hr />\n\n<h3> <a name=\"isErrataEmailNotifsForOrg\" href=\"#top\">Method: isErrataEmailNotifsForOrg</a></h3>\nDescription:<br />\n");
      out.write("Returns whether errata e-mail notifications are enabled\n for the organization\n<p />\n\n\n\n\nParameters:<br />\n<ul>\n<li>\n\n    string sessionKey\n </li>\n<li>\n\n    int orgId\n </li>\n</ul>\n<p />\nReturns:\n<code><ul><li>\n\n\nboolean - Returns the status of the errata e-mail notification\n setting for the organization \n \n</li></ul>\n</code>\n<p />\n<hr />\n\n<h3> <a name=\"isOrgConfigManagedByOrgAdmin\" href=\"#top\">Method: isOrgConfigManagedByOrgAdmin</a></h3>\nDescription:<br />\nReturns whether Organization Administrator is able to manage his\n organization configuration. This organization configuration may have a high impact\n on the whole Spacewalk/Satellite performance\n<p />\n\n\n\n\nParameters:<br />\n<ul>\n<li>\n\n    string sessionKey\n </li>\n<li>\n\n    int orgId\n </li>\n</ul>\n<p />\nReturns:\n<code><ul><li>\n\n\nboolean - Returns the status org admin management setting \n \n</li></ul>\n</code>\n<p />\n<hr />\n\n<h3> <a name=\"listOrgs\" href=\"#top\">Method: listOrgs</a></h3>\nDescription:<br />\nReturns the list of organizations.\n<p />\n\n\n\n\nParameters:<br />\n");
      out.write("<ul>\n<li>\n\n    string sessionKey\n </li>\n</ul>\n<p />\nReturns:\n<code><ul><li>\n\n\narray:\n  <ul>\n   <li>\n     \n\n\t     struct - organization info\n\t<ul>\n   \t<li> int \"id\"</li>\n   \t<li> string \"name\"</li>\n       <li> int \"active_users\" - Number of active users in the organization. </li>\n       <li> int \"systems\" - Number of systems in the organization. </li>\n       <li> int \"trusts\" - Number of trusted organizations. </li>\n       <li> int \"system_groups\" - Number of system groups in the organization. (optional) </li>\n       <li> int \"activation_keys\" - Number of activation keys in the organization. (optional) </li>\n       <li> int \"kickstart_profiles\" - Number of kickstart profiles in the organization. (optional) </li>\n       <li> int \"configuration_channels\" - Number of configuration channels in the organization. (optional) </li>\n       <li> boolean \"staging_content_enabled\" - Is staging content enabled in organization. (optional) </li>\n \t</ul>\n \n   </li></ul>\n \n</li></ul>\n</code>\n<p />\n<hr />\n\n<h3> <a name=\"listUsers\" href=\"#top\">Method: listUsers</a></h3>\n");
      out.write("Description:<br />\nReturns the list of users in a given organization.\n<p />\n\n\n\n\nParameters:<br />\n<ul>\n<li>\n\n    string sessionKey\n </li>\n<li>\n\n    int orgId\n </li>\n</ul>\n<p />\nReturns:\n<code><ul><li>\n\n\narray:\n  <ul>\n   <li>\n     \n\n\t     struct - user\n\t<ul>\n   \t<li> string \"login\"</li>\n   \t<li> string \"login_uc\"</li>\n   \t<li> string \"name\"</li>\n   \t<li> string \"email\"</li>\n   \t<li> boolean \"is_org_admin\"</li>\n \t</ul>\n \n   </li></ul>\n \n</li></ul>\n</code>\n<p />\n<hr />\n\n<h3> <a name=\"migrateSystems\" href=\"#top\">Method: migrateSystems</a></h3>\nDescription:<br />\nMigrate systems from one organization to another.  If executed by\n a Satellite administrator, the systems will be migrated from their current\n organization to the organization specified by the toOrgId.  If executed by\n an organization administrator, the systems must exist in the same organization\n as that administrator and the systems will be migrated to the organization\n specified by the toOrgId. In any scenario, the origination and destination\n organizations must be defined in a trust.\n");
      out.write("<p />\n\n\n\n\nParameters:<br />\n<ul>\n<li>\n\n    string sessionKey\n </li>\n<li>\n\n    int toOrgId - ID of the organization where the\n system(s) will be migrated to.\n </li>\n<li>\n\narray:\n<ul>\n    <li>int - systemId</li>\n</ul>\n </li>\n</ul>\n<p />\nReturns:\n<code><ul><li>\n\n\narray:\n<ul>\n    <li>int - serverIdMigrated</li>\n</ul>\n \n</li></ul>\n</code>\n<p />\n<hr />\n\n<h3> <a name=\"setCrashFileSizeLimit\" href=\"#top\">Method: setCrashFileSizeLimit</a></h3>\nDescription:<br />\nSet the organization wide crash file size limit. The limit value\n must be non-negative, zero means no limit.\n<p />\n\n\n\n\nParameters:<br />\n<ul>\n<li>\n\n    string sessionKey\n </li>\n<li>\n\n    int orgId\n </li>\n<li>\n\n    int limit - The limit to set (non-negative value).\n </li>\n</ul>\n<p />\nReturns:\n<code><ul><li>\n\n\n    int - 1 on success, exception thrown otherwise.\n \n</li></ul>\n</code>\n<p />\n<hr />\n\n<h3> <a name=\"setCrashReporting\" href=\"#top\">Method: setCrashReporting</a></h3>\nDescription:<br />\nSet the status of crash reporting settings for the given organization.\n Disabling crash reporting will automatically disable crash file upload.\n");
      out.write("<p />\n\n\n\n\nParameters:<br />\n<ul>\n<li>\n\n    string sessionKey\n </li>\n<li>\n\n    int orgId\n </li>\n<li>\n\n    boolean enable - Use true/false to enable/disable\n </li>\n</ul>\n<p />\nReturns:\n<code><ul><li>\n\n\n    int - 1 on success, exception thrown otherwise.\n \n</li></ul>\n</code>\n<p />\n<hr />\n\n<h3> <a name=\"setCrashfileUpload\" href=\"#top\">Method: setCrashfileUpload</a></h3>\nDescription:<br />\nSet the status of crash file upload settings for the given organization.\n Modifying the settings is possible as long as crash reporting is enabled.\n<p />\n\n\n\n\nParameters:<br />\n<ul>\n<li>\n\n    string sessionKey\n </li>\n<li>\n\n    int orgId\n </li>\n<li>\n\n    boolean enable - Use true/false to enable/disable\n </li>\n</ul>\n<p />\nReturns:\n<code><ul><li>\n\n\n    int - 1 on success, exception thrown otherwise.\n \n</li></ul>\n</code>\n<p />\n<hr />\n\n<h3> <a name=\"setErrataEmailNotifsForOrg\" href=\"#top\">Method: setErrataEmailNotifsForOrg</a></h3>\nDescription:<br />\nDis/enables errata e-mail notifications for the organization\n<p />\n\n\n\n\nParameters:<br />\n");
      out.write("<ul>\n<li>\n\n    string sessionKey\n </li>\n<li>\n\n    int orgId\n </li>\n<li>\n\n    boolean enable - Use true/false to enable/disable\n </li>\n</ul>\n<p />\nReturns:\n<code><ul><li>\n\n\n    int - 1 on success, exception thrown otherwise.\n \n</li></ul>\n</code>\n<p />\n<hr />\n\n<h3> <a name=\"setOrgConfigManagedByOrgAdmin\" href=\"#top\">Method: setOrgConfigManagedByOrgAdmin</a></h3>\nDescription:<br />\nSets whether Organization Administrator can manage his organization\n configuration. This organization configuration may have a high impact\n on the whole Spacewalk/Satellite performance\n<p />\n\n\n\n\nParameters:<br />\n<ul>\n<li>\n\n    string sessionKey\n </li>\n<li>\n\n    int orgId\n </li>\n<li>\n\n    boolean enable - Use true/false to enable/disable\n </li>\n</ul>\n<p />\nReturns:\n<code><ul><li>\n\n\n    int - 1 on success, exception thrown otherwise.\n \n</li></ul>\n</code>\n<p />\n<hr />\n\n<h3> <a name=\"setPolicyForScapFileUpload\" href=\"#top\">Method: setPolicyForScapFileUpload</a></h3>\nDescription:<br />\nSet the status of SCAP detailed result file upload settings\n");
      out.write(" for the given organization.\n<p />\n\n\n\n\nParameters:<br />\n<ul>\n<li>\n\n        string sessionKey\n </li>\n<li>\n\n    int orgId\n </li>\n<li>\n\n\t     struct - scap_upload_info\n\t<ul>\n             <li> boolean \"enabled\" - Aggregation of detailed SCAP results is enabled. </li>\n             <li> int \"size_limit\" - Limit (in Bytes) for a single SCAP file upload. </li>\n     \t</ul>\n </li>\n</ul>\n<p />\nReturns:\n<code><ul><li>\n\n\n    int - 1 on success, exception thrown otherwise.\n \n</li></ul>\n</code>\n<p />\n<hr />\n\n<h3> <a name=\"setPolicyForScapResultDeletion\" href=\"#top\">Method: setPolicyForScapResultDeletion</a></h3>\nDescription:<br />\nSet the status of SCAP result deletion settins for the given\n organization.\n<p />\n\n\n\n\nParameters:<br />\n<ul>\n<li>\n\n        string sessionKey\n </li>\n<li>\n\n    int orgId\n </li>\n<li>\n\n\t     struct - scap_deletion_info\n\t<ul>\n             <li> boolean \"enabled\" - Deletion of SCAP results is enabled </li>\n             <li> int \"retention_period\" - Period (in days) after which a scan can be deleted (if enabled). </li>\n");
      out.write("     \t</ul>\n </li>\n</ul>\n<p />\nReturns:\n<code><ul><li>\n\n\n    int - 1 on success, exception thrown otherwise.\n \n</li></ul>\n</code>\n<p />\n<hr />\n\n<h3> <a name=\"updateName\" href=\"#top\">Method: updateName</a></h3>\nDescription:<br />\nUpdates the name of an organization\n<p />\n\n\n\n\nParameters:<br />\n<ul>\n<li>\n\n    string sessionKey\n </li>\n<li>\n\n    int orgId\n </li>\n<li>\n\n    string name - Organization name. Must meet same\n criteria as in the web UI.\n </li>\n</ul>\n<p />\nReturns:\n<code><ul><li>\n\n\n\n\n\t     struct - organization info\n\t<ul>\n   \t<li> int \"id\"</li>\n   \t<li> string \"name\"</li>\n       <li> int \"active_users\" - Number of active users in the organization. </li>\n       <li> int \"systems\" - Number of systems in the organization. </li>\n       <li> int \"trusts\" - Number of trusted organizations. </li>\n       <li> int \"system_groups\" - Number of system groups in the organization. (optional) </li>\n       <li> int \"activation_keys\" - Number of activation keys in the organization. (optional) </li>\n       <li> int \"kickstart_profiles\" - Number of kickstart profiles in the organization. (optional) </li>\n");
      out.write("       <li> int \"configuration_channels\" - Number of configuration channels in the organization. (optional) </li>\n       <li> boolean \"staging_content_enabled\" - Is staging content enabled in organization. (optional) </li>\n \t</ul>\n  \n \n</li></ul>\n</code>\n<p />\n<hr />\n</body>\n</html>\n");
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
