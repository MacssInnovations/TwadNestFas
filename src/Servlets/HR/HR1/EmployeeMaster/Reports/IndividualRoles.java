package Servlets.HR.HR1.EmployeeMaster.Reports;


import Servlets.HR.HR1.EmployeeMaster.Model.ReportUtil;

import Servlets.Security.classes.UserProfile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.Statement;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperManager;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;


public class IndividualRoles extends HttpServlet {
    /**
     *
     */
    private static final long serialVersionUID = -547628776620117567L;
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";


    public void init(ServletConfig config) throws ServletException {
        super.init(config);

    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        Connection connection = null;
        try {


            ResourceBundle rs =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";

            String strDriver = rs.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rs.getString("Config.DSN");
            String strhostname = rs.getString("Config.HOST_NAME");
            String strportno = rs.getString("Config.PORT_NUMBER");
            String strsid = rs.getString("Config.SID");
            String strdbusername = rs.getString("Config.USER_NAME");
            String strdbpassword = rs.getString("Config.PASSWORD");

            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection


            Class.forName(strDriver.trim());
            connection =
                    DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                                                strdbpassword.trim());
        } catch (Exception ex) {
            String connectMsg =
                "Could not create the connection" + ex.getMessage() + " " +
                ex.getLocalizedMessage();
            System.out.println(connectMsg);
        }
        response.setContentType(CONTENT_TYPE);
        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");

            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }

        // JasperDesign jasperDesign = null;
        File reportFile = null;

        String offlevel = "";
        String office = "";
        String officetype = "";
        String officeselected = "";
        String designationlevel = "";
        String designation = "";
        String outputtype = "";
        String ordertype = "";

        Map map = null;
        try {
            offlevel = request.getParameter("offlevel");
            designationlevel = request.getParameter("designationlevel");
            outputtype = request.getParameter("outputtype");
            ordertype = request.getParameter("ordertype");

            System.out.println("Office Level:" + offlevel);
            System.out.println("Designation  Level:" + designationlevel);
            System.out.println("Output Type:" + outputtype);
            System.out.println("Order Type:" + ordertype);

            designation = request.getParameter("designation");
            System.out.println("Designation  Level:" + designation);

            office = request.getParameter("office");
            System.out.println("Office Range Combo:" + office);

            officetype = request.getParameter("officetype");
            System.out.println("Office Type Option:" + officetype);

            officeselected = request.getParameter("officeselected");
            System.out.println("Office Selected:" + officeselected);


        } catch (Exception e) {
            System.out.println("Assigning Error:" + e);
        }
        String sql = "";

        try {
            System.out.println("calling Employee Detail servlet");
            // order by  Office / Designation
            sql = "";
            if (offlevel.equalsIgnoreCase("all")) { // all offices
                System.out.println("report 1.1");
                sql =
 "select upper(g.OFFICE_NAME) as OFFICE_NAME,f.DESIGNATION,b.EMPLOYEE_ID,b.EMPLOYEE_NAME||decode(b.EMPLOYEE_INITIAL,null,' ','.'||b.EMPLOYEE_INITIAL) EMPLOYEE_NAME,\n" +
   "d.ROLE_NAME,a.user_id,decode(a.LOGIN_ENABLED,1,'Active',0,'Deactive') as LOGIN_ENABLED\n" +
   "\n" + "\n" + "from SEC_MST_USERS a\n" +
   "inner join  HRM_MST_EMPLOYEES b on b.EMPLOYEE_ID=a.EMPLOYEE_ID\n" +
   "inner join SEC_MST_USER_ROLES c on c.EMPLOYEE_ID=a.EMPLOYEE_ID\n" +
   "inner join SEC_MST_ROLES d on d.ROLE_ID = c.ROLE_ID\n" + "\n" +
   "inner join HRM_EMP_CURRENT_POSTING e on e.EMPLOYEE_ID = a.EMPLOYEE_ID\n" +
   "inner join HRM_MST_DESIGNATIONS f on f.DESIGNATION_ID=e.DESIGNATION_ID\n" +
   "inner join COM_MST_OFFICES g on g.OFFICE_ID = e.OFFICE_ID\n" +
   "inner join COM_MST_OFFICE_LEVELS h on h.OFFICE_LEVEL_ID = g.OFFICE_LEVEL_ID\n" +
   "where d.ROLE_CATEGORY='I'\n" +
   "order by h.HIERARCHICAL_SEQUENCE,OFFICE_NAME,EMPLOYEE_NAME,ROLE_NAME";

            } else //specific offcies
            {
                if (office.equalsIgnoreCase("HO")) { //Head Office
                    System.out.println("report 2.1");
                    sql =
 "select upper(g.OFFICE_NAME) as OFFICE_NAME,f.DESIGNATION,b.EMPLOYEE_ID,b.EMPLOYEE_NAME||decode(b.EMPLOYEE_INITIAL,null,' ','.'||b.EMPLOYEE_INITIAL) EMPLOYEE_NAME,\n" +
   "d.ROLE_NAME,a.user_id,decode(a.LOGIN_ENABLED,1,'Active',0,'Deactive') as LOGIN_ENABLED\n" +
   "\n" + "\n" + "from SEC_MST_USERS a\n" +
   "inner join  HRM_MST_EMPLOYEES b on b.EMPLOYEE_ID=a.EMPLOYEE_ID\n" +
   "inner join SEC_MST_USER_ROLES c on c.EMPLOYEE_ID=a.EMPLOYEE_ID\n" +
   "inner join SEC_MST_ROLES d on d.ROLE_ID = c.ROLE_ID\n" +
   "inner join HRM_EMP_CURRENT_POSTING e on e.EMPLOYEE_ID = a.EMPLOYEE_ID\n" +
   "inner join HRM_MST_DESIGNATIONS f on f.DESIGNATION_ID=e.DESIGNATION_ID\n" +
   "inner join COM_MST_OFFICES g on g.OFFICE_ID = e.OFFICE_ID\n" +
   "inner join COM_MST_OFFICE_LEVELS h on h.OFFICE_LEVEL_ID = g.OFFICE_LEVEL_ID\n" +
   "where h.OFFICE_LEVEL_ID ='HO' and  d.ROLE_CATEGORY='I'\n" +
   "order by h.HIERARCHICAL_SEQUENCE,OFFICE_NAME,EMPLOYEE_NAME,ROLE_NAME";


                } else if (office.equalsIgnoreCase("RN")) { //Regin  Office
                    if (officetype.equalsIgnoreCase("all")) // Region All
                    {
                        sql =
 "select upper(g.OFFICE_NAME) as OFFICE_NAME,f.DESIGNATION,b.EMPLOYEE_ID,b.EMPLOYEE_NAME||decode(b.EMPLOYEE_INITIAL,null,' ','.'||b.EMPLOYEE_INITIAL) EMPLOYEE_NAME,\n" +
   "d.ROLE_NAME,a.user_id,decode(a.LOGIN_ENABLED,1,'Active',0,'Deactive') as LOGIN_ENABLED\n" +
   "\n" + "\n" + "from SEC_MST_USERS a\n" +
   "inner join  HRM_MST_EMPLOYEES b on b.EMPLOYEE_ID=a.EMPLOYEE_ID\n" +
   "inner join SEC_MST_USER_ROLES c on c.EMPLOYEE_ID=a.EMPLOYEE_ID\n" +
   "inner join SEC_MST_ROLES d on d.ROLE_ID = c.ROLE_ID\n" +
   "inner join HRM_EMP_CURRENT_POSTING e on e.EMPLOYEE_ID = a.EMPLOYEE_ID\n" +
   "inner join HRM_MST_DESIGNATIONS f on f.DESIGNATION_ID=e.DESIGNATION_ID\n" +
   "inner join COM_MST_OFFICES g on g.OFFICE_ID = e.OFFICE_ID\n" +
   "inner join COM_MST_OFFICE_LEVELS h on h.OFFICE_LEVEL_ID = g.OFFICE_LEVEL_ID\n" +
   "where  h.OFFICE_LEVEL_ID ='" + office + "' and d.ROLE_CATEGORY='I'\n" +
   "order by h.HIERARCHICAL_SEQUENCE,OFFICE_NAME,EMPLOYEE_NAME,ROLE_NAME";
                        System.out.println("report 3.1");
                        reportFile =
                                new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/EmployeeRoles_RN_Individual_New.jasper"));


                    } else // Region Specific
                    {
                        sql =
 "select upper(g.OFFICE_NAME) as OFFICE_NAME,f.DESIGNATION,b.EMPLOYEE_ID,b.EMPLOYEE_NAME||decode(b.EMPLOYEE_INITIAL,null,' ','.'||b.EMPLOYEE_INITIAL) EMPLOYEE_NAME,\n" +
   "d.ROLE_NAME,a.user_id,decode(a.LOGIN_ENABLED,1,'Active',0,'Deactive') as LOGIN_ENABLED\n" +
   "\n" + "\n" + "from SEC_MST_USERS a\n" +
   "inner join  HRM_MST_EMPLOYEES b on b.EMPLOYEE_ID=a.EMPLOYEE_ID\n" +
   "inner join SEC_MST_USER_ROLES c on c.EMPLOYEE_ID=a.EMPLOYEE_ID\n" +
   "inner join SEC_MST_ROLES d on d.ROLE_ID = c.ROLE_ID\n" +
   "inner join HRM_EMP_CURRENT_POSTING e on e.EMPLOYEE_ID = a.EMPLOYEE_ID\n" +
   "inner join HRM_MST_DESIGNATIONS f on f.DESIGNATION_ID=e.DESIGNATION_ID\n" +
   "inner join COM_MST_OFFICES g on g.OFFICE_ID = e.OFFICE_ID\n" +
   "inner join COM_MST_OFFICE_LEVELS h on h.OFFICE_LEVEL_ID = g.OFFICE_LEVEL_ID\n" +
   "where  h.OFFICE_LEVEL_ID ='" + office + "' and g.OFFICE_ID in (" +
   officeselected + ") and  d.ROLE_CATEGORY='I'\n" +
   "order by h.HIERARCHICAL_SEQUENCE,OFFICE_NAME,EMPLOYEE_NAME,ROLE_NAME";
                        System.out.println("report 4.1");
                        reportFile =
                                new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/EmployeeRoles_RN_Spe_New_Individual.jasper"));


                    }

                } else if (office.equalsIgnoreCase("CL")) { //Circle  Office
                    if (officetype.equalsIgnoreCase("all")) // Circle All
                    {
                        sql =
 "select upper(g.OFFICE_NAME) as OFFICE_NAME,f.DESIGNATION,b.EMPLOYEE_ID,b.EMPLOYEE_NAME||decode(b.EMPLOYEE_INITIAL,null,' ','.'||b.EMPLOYEE_INITIAL) EMPLOYEE_NAME,\n" +
   "d.ROLE_NAME,a.user_id,decode(a.LOGIN_ENABLED,1,'Active',0,'Deactive') as LOGIN_ENABLED\n" +
   "\n" + "\n" + "from SEC_MST_USERS a\n" +
   "inner join  HRM_MST_EMPLOYEES b on b.EMPLOYEE_ID=a.EMPLOYEE_ID\n" +
   "inner join SEC_MST_USER_ROLES c on c.EMPLOYEE_ID=a.EMPLOYEE_ID\n" +
   "inner join SEC_MST_ROLES d on d.ROLE_ID = c.ROLE_ID\n" +
   "inner join HRM_EMP_CURRENT_POSTING e on e.EMPLOYEE_ID = a.EMPLOYEE_ID\n" +
   "inner join HRM_MST_DESIGNATIONS f on f.DESIGNATION_ID=e.DESIGNATION_ID\n" +
   "inner join COM_MST_OFFICES g on g.OFFICE_ID = e.OFFICE_ID\n" +
   "inner join COM_MST_OFFICE_LEVELS h on h.OFFICE_LEVEL_ID = g.OFFICE_LEVEL_ID\n" +
   "where  h.OFFICE_LEVEL_ID ='" + office + "' and d.ROLE_CATEGORY='I'\n" +
   "order by h.HIERARCHICAL_SEQUENCE,OFFICE_NAME,EMPLOYEE_NAME,ROLE_NAME";
                        System.out.println("report 3.1");
                        reportFile =
                                new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/EmployeeRoles_RN_Individual_New.jasper"));

                    } else // Circle Specific
                    {
                        sql =
 "select upper(g.OFFICE_NAME) as OFFICE_NAME,f.DESIGNATION,b.EMPLOYEE_ID,b.EMPLOYEE_NAME||decode(b.EMPLOYEE_INITIAL,null,' ','.'||b.EMPLOYEE_INITIAL) EMPLOYEE_NAME,\n" +
   "d.ROLE_NAME,a.user_id,decode(a.LOGIN_ENABLED,1,'Active',0,'Deactive') as LOGIN_ENABLED\n" +
   "\n" + "\n" + "from SEC_MST_USERS a\n" +
   "inner join  HRM_MST_EMPLOYEES b on b.EMPLOYEE_ID=a.EMPLOYEE_ID\n" +
   "inner join SEC_MST_USER_ROLES c on c.EMPLOYEE_ID=a.EMPLOYEE_ID\n" +
   "inner join SEC_MST_ROLES d on d.ROLE_ID = c.ROLE_ID\n" +
   "inner join HRM_EMP_CURRENT_POSTING e on e.EMPLOYEE_ID = a.EMPLOYEE_ID\n" +
   "inner join HRM_MST_DESIGNATIONS f on f.DESIGNATION_ID=e.DESIGNATION_ID\n" +
   "inner join COM_MST_OFFICES g on g.OFFICE_ID = e.OFFICE_ID\n" +
   "inner join COM_MST_OFFICE_LEVELS h on h.OFFICE_LEVEL_ID = g.OFFICE_LEVEL_ID\n" +
   "where  h.OFFICE_LEVEL_ID ='" + office + "' and g.OFFICE_ID in (" +
   officeselected + ") and  d.ROLE_CATEGORY='I'\n" +
   "order by h.HIERARCHICAL_SEQUENCE,OFFICE_NAME,EMPLOYEE_NAME,ROLE_NAME";


                    }

                } else if (office.equalsIgnoreCase("DN")) { //Division  Office
                    if (officetype.equalsIgnoreCase("all")) // Division All
                    {
                        sql =
 "select upper(g.OFFICE_NAME) as OFFICE_NAME,f.DESIGNATION,b.EMPLOYEE_ID,b.EMPLOYEE_NAME||decode(b.EMPLOYEE_INITIAL,null,' ','.'||b.EMPLOYEE_INITIAL) EMPLOYEE_NAME,\n" +
   "d.ROLE_NAME,a.user_id,decode(a.LOGIN_ENABLED,1,'Active',0,'Deactive') as LOGIN_ENABLED\n" +
   "\n" + "\n" + "from SEC_MST_USERS a\n" +
   "inner join  HRM_MST_EMPLOYEES b on b.EMPLOYEE_ID=a.EMPLOYEE_ID\n" +
   "inner join SEC_MST_USER_ROLES c on c.EMPLOYEE_ID=a.EMPLOYEE_ID\n" +
   "inner join SEC_MST_ROLES d on d.ROLE_ID = c.ROLE_ID\n" +
   "inner join HRM_EMP_CURRENT_POSTING e on e.EMPLOYEE_ID = a.EMPLOYEE_ID\n" +
   "inner join HRM_MST_DESIGNATIONS f on f.DESIGNATION_ID=e.DESIGNATION_ID\n" +
   "inner join COM_MST_OFFICES g on g.OFFICE_ID = e.OFFICE_ID\n" +
   "inner join COM_MST_OFFICE_LEVELS h on h.OFFICE_LEVEL_ID = g.OFFICE_LEVEL_ID\n" +
   "where  h.OFFICE_LEVEL_ID ='" + office + "' and d.ROLE_CATEGORY='I'\n" +
   "order by h.HIERARCHICAL_SEQUENCE,OFFICE_NAME,EMPLOYEE_NAME,ROLE_NAME";
                        System.out.println("report 3.1");
                        reportFile =
                                new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/EmployeeRoles_RN_Individual_New.jasper"));


                    } else // Division Specific
                    {
                        sql =
 "select upper(g.OFFICE_NAME) as OFFICE_NAME,f.DESIGNATION,b.EMPLOYEE_ID,b.EMPLOYEE_NAME||decode(b.EMPLOYEE_INITIAL,null,' ','.'||b.EMPLOYEE_INITIAL) EMPLOYEE_NAME,\n" +
   "d.ROLE_NAME,a.user_id,decode(a.LOGIN_ENABLED,1,'Active',0,'Deactive') as LOGIN_ENABLED\n" +
   "\n" + "\n" + "from SEC_MST_USERS a\n" +
   "inner join  HRM_MST_EMPLOYEES b on b.EMPLOYEE_ID=a.EMPLOYEE_ID\n" +
   "inner join SEC_MST_USER_ROLES c on c.EMPLOYEE_ID=a.EMPLOYEE_ID\n" +
   "inner join SEC_MST_ROLES d on d.ROLE_ID = c.ROLE_ID\n" +
   "inner join HRM_EMP_CURRENT_POSTING e on e.EMPLOYEE_ID = a.EMPLOYEE_ID\n" +
   "inner join HRM_MST_DESIGNATIONS f on f.DESIGNATION_ID=e.DESIGNATION_ID\n" +
   "inner join COM_MST_OFFICES g on g.OFFICE_ID = e.OFFICE_ID\n" +
   "inner join COM_MST_OFFICE_LEVELS h on h.OFFICE_LEVEL_ID = g.OFFICE_LEVEL_ID\n" +
   "where  h.OFFICE_LEVEL_ID ='" + office + "' and g.OFFICE_ID in (" +
   officeselected + ") and  d.ROLE_CATEGORY='I'\n" +
   "order by h.HIERARCHICAL_SEQUENCE,OFFICE_NAME,EMPLOYEE_NAME,ROLE_NAME";


                    }

                }


            }

            String reportFile1 =
                getServletContext().getRealPath("/WEB-INF/ReportSrc/EmployeeRoles_RN_Spe_New_Individual.jrxml");
            JRDesignQuery query = new JRDesignQuery();
            query.setText(sql);
            JasperDesign jasperDesign =
                JasperManager.loadXmlDesign(reportFile1);
            jasperDesign.setQuery(query);
            JasperReport jasperReport =
                JasperManager.compileReport(jasperDesign);

            if (!(new File(reportFile1)).exists())
                throw new JRRuntimeException("File J not found. Create the JRXML first.");
            else
                new ReportUtil().GenerateReportFromJrxml(jasperReport, null,
                                                         outputtype,
                                                         "Office Details",
                                                         response);


        } catch (Exception ex) {
            String connectMsg =
                "Could not create the report " + ex.getMessage() + " " +
                ex.getLocalizedMessage();
            System.out.println(connectMsg);
        }


    }


    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {

        Connection connection = null;
        try {


            ResourceBundle rs =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";

            String strDriver = rs.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rs.getString("Config.DSN");
            String strhostname = rs.getString("Config.HOST_NAME");
            String strportno = rs.getString("Config.PORT_NUMBER");
            String strsid = rs.getString("Config.SID");
            String strdbusername = rs.getString("Config.USER_NAME");
            String strdbpassword = rs.getString("Config.PASSWORD");

            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection


            Class.forName(strDriver.trim());
            connection =
                    DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                                                strdbpassword.trim());
        } catch (Exception ex) {
            String connectMsg =
                "Could not create the connection" + ex.getMessage() + " " +
                ex.getLocalizedMessage();
            System.out.println(connectMsg);
        }
        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");

            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }
        ResultSet result = null;
        PreparedStatement ps = null;

        System.out.println("Employee Detail Report GET");
        response.setContentType("text/html");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
        String strCommand = "", optview = "";

        try {
            strCommand = request.getParameter("OLevel");
            System.out.println("Command....." + strCommand);
            optview = request.getParameter("optview");
            System.out.println("optview....." + optview);

        }

        catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }
        String html = "";
        if (strCommand.equalsIgnoreCase("region")) {


            /*  to get the office level  */
            HttpSession session = request.getSession(false);
            UserProfile empProfile =
                (UserProfile)session.getAttribute("UserProfile");

            System.out.println("user id::" + empProfile.getEmployeeId());
            int empid = empProfile.getEmployeeId();
            int oid = 0;
            String deptid = "", offlevel = "";

            try {

                ps =
  connection.prepareStatement("select b.OFFICE_LEVEL_ID,a.office_id from hrm_emp_current_posting a " +
                              " inner join com_mst_offices b on b.office_id=a.office_id " +
                              " where a.employee_id=?");
                ps.setInt(1, empid);
                ResultSet results = ps.executeQuery();
                if (results.next()) {
                    offlevel = results.getString("OFFICE_LEVEL_ID");
                    oid = results.getInt("office_id");
                }
                results.close();
                ps.close();
                /* other office  */
                String profile = (String)session.getAttribute("profile");
                if (profile.equalsIgnoreCase("other")) {
                    offlevel = "HO";
                    ps =
  connection.prepareStatement("select office_id from com_mst_offices where  office_level_id=? ");
                    ps.setString(1, offlevel);
                    results = ps.executeQuery();
                    if (results.next()) {
                        oid = results.getInt("office_id");
                    }
                }
                /* other office  */
                System.out.println("office id:" + oid);
                System.out.println("dept id:" + deptid);

            } catch (Exception e) {
                System.out.println(e);
            }

            /*  to get the office level  */

            /* find the specific Office */


            try {
                if (offlevel.equalsIgnoreCase("CL")) {
                    PreparedStatement psc =
                        connection.prepareStatement("select CONTROLLING_OFFICE_ID from COM_OFFICE_CONTROL where OFFICE_ID=?");
                    psc.setInt(1, oid);
                    ResultSet rsc = psc.executeQuery();
                    if (rsc.next()) {
                        oid = rsc.getInt("CONTROLLING_OFFICE_ID");
                    }
                } else if (offlevel.equalsIgnoreCase("DN")) {
                    PreparedStatement psd =
                        connection.prepareStatement("select CONTROLLING_OFFICE_ID from COM_OFFICE_CONTROL where OFFICE_ID=?");
                    psd.setInt(1, oid);
                    ResultSet rsd = psd.executeQuery();
                    if (rsd.next()) {
                        int officecl = rsd.getInt("CONTROLLING_OFFICE_ID");
                        PreparedStatement psc =
                            connection.prepareStatement("select CONTROLLING_OFFICE_ID from COM_OFFICE_CONTROL where OFFICE_ID=?");
                        psc.setInt(1, officecl);
                        ResultSet rsc = psc.executeQuery();
                        if (rsc.next()) {
                            oid = rsc.getInt("CONTROLLING_OFFICE_ID");
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("error in find specific region :" + e);
            }


            System.out.println("region office Id:" + oid);

            /* end of find the specific office  region*/


            System.out.println("region");
            System.out.println("Command::" + strCommand);
            try {
                //String sql="select  REGION_OFFICE_ID,REGION_OFFICE_NAME from COM_MST_REGIONS_HVIEW  ";
                if (offlevel.equalsIgnoreCase("HO")) {
                    String sql =
                        "select  REGION_OFFICE_ID,REGION_OFFICE_NAME from COM_MST_REGIONS_HVIEW ";
                    ps = connection.prepareStatement(sql);
                    result = ps.executeQuery();
                } else {
                    String sql =
                        "select  REGION_OFFICE_ID,REGION_OFFICE_NAME from COM_MST_REGIONS_HVIEW where REGION_OFFICE_ID=? ";
                    ps = connection.prepareStatement(sql);
                    ps.setInt(1, oid);
                    result = ps.executeQuery();
                }

                int count = 0;
                html =
"<table cellpadding=0 cellspacing=0 border=0 width='100%'>";

                boolean bool = false;
                while (result.next()) {
                    if (bool = !bool) {
                        html =
html + "<tr bgcolor=\"pink\"><td><input type='checkbox' name='chkregion' value='" +
 result.getInt("REGION_OFFICE_ID") + "' onclick='regiononchange()' ></td>";
                        html =
html + "<td>" + result.getString("REGION_OFFICE_NAME") + "</td></tr>";
                    } else {
                        html =
html + "<tr ><td><input type='checkbox' name='chkregion' value='" +
 result.getInt("REGION_OFFICE_ID") + "' onclick='regiononchange()'></td>";
                        html =
html + "<td>" + result.getString("REGION_OFFICE_NAME") + "</td></tr>";
                    }
                    count++;
                }

                if (count == 0) {
                    html = "There is no Region";
                } else {
                    if (offlevel.equalsIgnoreCase("HO")) {
                        html =
html + "<tr ><td colspan='2'><a href='javascript:regionselectAll()'>Select All</a>&nbsp;&nbsp;&nbsp;<a href='javascript:regionclose()'>Close</a></td></tr>";
                    } else {
                        html =
html + "<tr ><td colspan='2'><a href='javascript:regionclose()'>Close</a></td></tr>";
                    }
                    html = html + "</table>";

                }


            } catch (Exception e) {
                System.out.println("Region selection error " + e);
                html = "There is no Region";

            }

        } else if (strCommand.equalsIgnoreCase("circle")) {

            System.out.println("circle");
            System.out.println("Command::" + strCommand);


            /*  to get the office level  */
            HttpSession session = request.getSession(false);
            UserProfile empProfile =
                (UserProfile)session.getAttribute("UserProfile");

            System.out.println("user id::" + empProfile.getEmployeeId());
            int empid = empProfile.getEmployeeId();
            int oid = 0;
            String deptid = "", offlevel = "";

            try {

                ps =
  connection.prepareStatement("select b.OFFICE_LEVEL_ID,a.office_id from hrm_emp_current_posting a " +
                              " inner join com_mst_offices b on b.office_id=a.office_id " +
                              " where a.employee_id=?");
                ps.setInt(1, empid);
                ResultSet results = ps.executeQuery();
                if (results.next()) {
                    offlevel = results.getString("OFFICE_LEVEL_ID");
                    oid = results.getInt("office_id");
                }
                results.close();
                ps.close();
                /* other office  */
                String profile = (String)session.getAttribute("profile");
                if (profile.equalsIgnoreCase("other")) {
                    offlevel = "HO";
                    ps =
  connection.prepareStatement("select office_id from com_mst_offices where  office_level_id=? ");
                    ps.setString(1, offlevel);
                    results = ps.executeQuery();
                    if (results.next()) {
                        oid = results.getInt("office_id");
                    }
                }
                /* other office  */
                System.out.println("office id:" + oid);
                System.out.println("dept id:" + deptid);

            } catch (Exception e) {
                System.out.println(e);
            }

            /*  to get the office level  */

            /* find the specific Office */


            try {
                if (offlevel.equalsIgnoreCase("DN")) {
                    PreparedStatement psd =
                        connection.prepareStatement("select CONTROLLING_OFFICE_ID from COM_OFFICE_CONTROL where OFFICE_ID=?");
                    psd.setInt(1, oid);
                    ResultSet rsd = psd.executeQuery();
                    if (rsd.next()) {
                        oid = rsd.getInt("CONTROLLING_OFFICE_ID");

                    }
                }
            } catch (Exception e) {
                System.out.println("error in find specific region :" + e);
            }


            System.out.println("circle office Id:" + oid);

            /* end of find the specific office  region*/


            try {
                String options = request.getParameter("regions");
                System.out.println("Region selected:" + options);
                if (offlevel.equalsIgnoreCase("HO") ||
                    offlevel.equalsIgnoreCase("RN")) {

                    String sql =
                        "select  CIRCLE_OFFICE_ID,CIRCLE_OFFICE_NAME from COM_MST_CIRCLES_HVIEW where REGION_OFFICE_ID in (" +
                        options + ")";
                    System.out.println(sql);
                    Statement st = connection.createStatement();
                    result = st.executeQuery(sql);
                } else {
                    String sql =
                        "select  CIRCLE_OFFICE_ID,CIRCLE_OFFICE_NAME from COM_MST_CIRCLES_HVIEW where CIRCLE_OFFICE_ID =" +
                        oid;
                    System.out.println(sql);
                    Statement st = connection.createStatement();
                    result = st.executeQuery(sql);

                }
                int count = 0;
                html =
"<table cellpadding=0 cellspacing=0 border=0  width='100%'>";
                boolean bool = false;
                while (result.next()) {
                    if (bool = !bool) {
                        html =
html + "<tr bgcolor=\"pink\" ><td ><input type='checkbox' name='chkcircle' value='" +
 result.getInt("CIRCLE_OFFICE_ID") + "' onclick='circleonchange()' ></td>";
                        html =
html + "<td >" + result.getString("CIRCLE_OFFICE_NAME") + "</td></tr>";
                    } else {
                        html =
html + "<tr ><td ><input type='checkbox' name='chkcircle' value='" +
 result.getInt("CIRCLE_OFFICE_ID") + "' onclick='circleonchange()'></td>";
                        html =
html + "<td >" + result.getString("CIRCLE_OFFICE_NAME") + "</td></tr>";
                    }
                    count++;
                }
                html = html + "</table>";

                if (count == 0) {
                    html = "There is no Circle";
                } else {
                    if (offlevel.equalsIgnoreCase("HO") ||
                        offlevel.equalsIgnoreCase("RN")) {
                        html =
html + "<tr ><td colspan='2'><a href='javascript:circleselectAll()'>Select All</a>&nbsp;&nbsp;&nbsp;<a href='javascript:circleclose()'>Close</a></td></tr>";
                    } else {
                        html =
html + "<tr ><td colspan='2'><a href='javascript:circleclose()'>Close</a></td></tr>";
                    }
                    html = html + "</tbody></table>";

                }


            } catch (Exception e) {
                System.out.println("Circle selection error " + e);
                html = "There is no Circle";

            }
        }

        else if (strCommand.equalsIgnoreCase("division")) {

            System.out.println("division");
            System.out.println("Command::" + strCommand);
            /*  to get the office level  */
            HttpSession session = request.getSession(false);
            UserProfile empProfile =
                (UserProfile)session.getAttribute("UserProfile");

            System.out.println("user id::" + empProfile.getEmployeeId());
            int empid = empProfile.getEmployeeId();
            int oid = 0;
            String deptid = "", offlevel = "";

            try {

                ps =
  connection.prepareStatement("select b.OFFICE_LEVEL_ID,a.office_id from hrm_emp_current_posting a " +
                              " inner join com_mst_offices b on b.office_id=a.office_id " +
                              " where a.employee_id=?");
                ps.setInt(1, empid);
                ResultSet results = ps.executeQuery();
                if (results.next()) {
                    offlevel = results.getString("OFFICE_LEVEL_ID");
                    oid = results.getInt("office_id");
                }
                results.close();
                ps.close();
                /* other office  */
                String profile = (String)session.getAttribute("profile");
                if (profile.equalsIgnoreCase("other")) {
                    offlevel = "HO";
                    ps =
  connection.prepareStatement("select office_id from com_mst_offices where  office_level_id=? ");
                    ps.setString(1, offlevel);
                    results = ps.executeQuery();
                    if (results.next()) {
                        oid = results.getInt("office_id");
                    }
                }
                /* other office  */
                System.out.println("office id:" + oid);
                System.out.println("dept id:" + deptid);

            } catch (Exception e) {
                System.out.println(e);
            }

            /*  to get the office level  */

            /* find the specific Office */


            System.out.println("division office Id:" + oid);

            /* end of find the specific office  region*/


            try {
                String options = request.getParameter("circles");
                System.out.println("circle selected:" + options);
                if (offlevel.equalsIgnoreCase("HO") ||
                    offlevel.equalsIgnoreCase("RN") ||
                    offlevel.equalsIgnoreCase("CL")) {
                    String sql =
                        "select  DIVISION_OFFICE_ID,DIVISION_OFFICE_NAME from COM_MST_DIVISIONS_HVIEW where  CIRCLE_OFFICE_ID in (" +
                        options + ")";
                    Statement st = connection.createStatement();
                    result = st.executeQuery(sql);
                } else {
                    String sql =
                        "select  DIVISION_OFFICE_ID,DIVISION_OFFICE_NAME from COM_MST_DIVISIONS_HVIEW where  DIVISION_OFFICE_ID =" +
                        oid;
                    Statement st = connection.createStatement();
                    result = st.executeQuery(sql);
                }


                int count = 0;
                html =
"<table cellpadding=0 cellspacing=0 border=0  width='100%'>";
                boolean bool = false;
                while (result.next()) {
                    if (bool = !bool) {
                        html =
html + "<tr bgcolor=\"pink\" ><td ><input type='checkbox' name='chkdivision' value='" +
 result.getInt("DIVISION_OFFICE_ID") + "' ></td>";
                        html =
html + "<td >" + result.getString("DIVISION_OFFICE_NAME") + "</td></tr>";
                    } else {
                        html =
html + "<tr ><td ><input type='checkbox' name='chkdivision' value='" +
 result.getInt("DIVISION_OFFICE_ID") + "' ></td>";
                        html =
html + "<td >" + result.getString("DIVISION_OFFICE_NAME") + "</td></tr>";
                    }
                    count++;
                }
                html = html + "</table>";

                if (count == 0) {
                    html = "There is no Circle";
                } else {
                    if (offlevel.equalsIgnoreCase("HO") ||
                        offlevel.equalsIgnoreCase("RN") ||
                        offlevel.equalsIgnoreCase("CL")) {
                        html =
html + "<tr ><td colspan='2'><a href='javascript:divisionselectAll()'>Select All</a>&nbsp;&nbsp;&nbsp;<a href='javascript:divisionclose()'>Close</a></td></tr>";
                    } else {
                        html =
html + "<tr ><td colspan='2'><a href='javascript:divisionclose()'>Close</a></td></tr>";
                    }
                    html = html + "</tbody></table>";

                }


            } catch (Exception e) {
                System.out.println("Circle selection error " + e);
                html = "There is no Circle";

            }

        }


        System.out.println("Html:" + html);
        out.println(html);


    }


}
