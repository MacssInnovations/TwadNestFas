package Servlets.HR.HR1.EmployeeMaster.Reports;

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


import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

public class Status_officewise_Summary_Report extends HttpServlet {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        String desigval = "", workstatus = "", statusval = "", newstatusval =
            "";

        Connection connection = null;
        ResultSet result1;
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
        String oflevel = "";
        String hier = "";

        Map map = null;
        try {

            System.out.println("inside employee details report");

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

            oflevel = request.getParameter("rad_off");
            System.out.println("office level new..." + oflevel);

            hier = request.getParameter("allhier");
            System.out.println("include off hier:" + hier);

        } catch (Exception e) {
            System.out.println("Assigning Error:" + e);
        }

        try {
            System.out.println("calling Employee Detail servlet");

            /*************************************************************************/
            /* to get the office level */
            HttpSession session = request.getSession(false);
            UserProfile empProfile =
                (UserProfile)session.getAttribute("UserProfile");

            System.out.println("user id::" + empProfile.getEmployeeId());
            int empid = empProfile.getEmployeeId();
            int oid = 0;
            String deptid = "", officelevel = "";

            PreparedStatement ps = null;
            PreparedStatement ps1 = null;

            try {

                ps =
  connection.prepareStatement("select b.OFFICE_LEVEL_ID,a.office_id from hrm_emp_current_posting a " +
                              " inner join com_mst_offices b on b.office_id=a.office_id " +
                              " where a.employee_id=?");
                ps.setInt(1, empid);
                ResultSet results = ps.executeQuery();
                System.out.println("Employee id:" + empid);

                if (results.next()) {
                    officelevel = results.getString("OFFICE_LEVEL_ID");
                    oid = results.getInt("office_id");
                }
                results.close();
                ps.close();
                /* other office */
                String profile = (String)session.getAttribute("profile");
                if (profile.equalsIgnoreCase("other")) {
                    officelevel = "HO";
                    ps =
  connection.prepareStatement("select office_id from com_mst_offices where  office_level_id=? ");
                    ps.setString(1, officelevel);
                    results = ps.executeQuery();
                    if (results.next()) {
                        oid = results.getInt("office_id");
                    }
                }
                /* other office */
                System.out.println("office id:" + oid);
                System.out.println("dept id:" + deptid);

            } catch (Exception e) {
                System.out.println(e);
                e.printStackTrace();
            }

            try {

                int destRank = 0;
                // if(optbase.equalsIgnoreCase("Dest"))
                // {
                // destRank=Integer.parseInt(request.getParameter("cmbDesignation"));
                String testval1[] = (request.getParameterValues("chkstatus"));
                for (int i = 0; i < testval1.length; i++) {
                    desigval = desigval + testval1[i] + "','";
                    System.out.println("Test values:-----" + testval1[i]);
                }
                workstatus = desigval.substring(0, desigval.length() - 3);
                System.out.println("new desgi values:" + workstatus);
                // }
                String Designation_type = request.getParameter("OffType");
                System.out.println("dESIGNATION TYPE" + Designation_type);
                String status = "";

                String testval2[] = (request.getParameterValues("cmbsgroup"));
                for (int i = 0; i < testval2.length; i++) {
                    statusval = statusval + "'" + testval2[i] + "'" + ",";
                    System.out.println("Test status values:-----" +
                                       testval2[i]);
                }
                newstatusval = statusval.substring(0, statusval.length() - 1);
                System.out.println("new status values:" + newstatusval);
                status = newstatusval;

                String s =
                    request.getRealPath("/Reports/DesignationwiseStatus.jrxml");
                System.out.println("The Servlet Path---> " + s);
                System.out.println("Before");
                String Query = "";
                if (Designation_type.equalsIgnoreCase("all")) {
                    Query =
"select a.DESIGNATION_ID, b.designation,a.total from" +
" (select DESIGNATION_ID,count(DESIGNATION_ID) as total from HRM_EMP_CURRENT_POSTING " +
"where EMPLOYEE_STATUS_ID in('" + workstatus +
"') group by DESIGNATION_ID ) " +
"a left outer join(select DESIGNATION_ID, designation from hrm_mst_designations) b " +
"on a.DESIGNATION_ID=b.DESIGNATION_ID";
                } else {

                    Query =
"select a.DESIGNATION_ID, b.designation,a.total from " +
"(select DESIGNATION_ID,count(DESIGNATION_ID) as total " +
"from HRM_EMP_CURRENT_POSTING where EMPLOYEE_STATUS_ID in('" + workstatus +
"') " + "and DESIGNATION_ID in (select DESIGNATION_ID from " +
" HRM_MST_DESIGNATIONS where SERVICE_GROUP_ID in (" + status + ")) " +
"group by DESIGNATION_ID) a left outer join " +
"(select DESIGNATION_ID, designation from hrm_mst_designations" +
") b on a.DESIGNATION_ID=b.DESIGNATION_ID";
                }
                System.out.println(Query);
                try {
                    JasperDesign jasperDesign = JasperManager.loadXmlDesign(s);
                    JRDesignQuery query = new JRDesignQuery();
                    query.setText(Query);
                    jasperDesign.setQuery(query);
                    jasperDesign.setProperty("workstat", workstatus);
                    JasperReport jasperReport =
                        JasperManager.compileReport(jasperDesign);

                    JasperPrint jasperPrint =
                        JasperManager.fillReport(jasperReport, null,
                                                 connection);

                    // ByteOutputStream bout=new ByteOutputStream();
                    // ByteArrayOutputStream bout=new ByteArrayOutputStream();
                    // response.setContentType("application/vnd.ms-excel");
                    String output = request.getParameter("optoutputtype");
                    if (output.equalsIgnoreCase("pdf")) {
                        OutputStream os = response.getOutputStream();
                        response.setContentType("application/pdf");
                        response.setHeader("Content-Disposition",
                                           "attachment;filename=\"statuswise.pdf\"");

                        os.write(JasperManager.printReportToPdf(jasperPrint));
                    } else if (output.equalsIgnoreCase("excel")) {
                        response.setContentType("application/vnd.ms-excel");
                        response.setHeader("Content-Disposition",
                                           "attachment;filename=\"Office_Wise_Gender.xls\"");
                        OutputStream os = response.getOutputStream();
                        ByteArrayOutputStream bout =
                            new ByteArrayOutputStream();
                        JRXlsExporter exporterXLS = new JRXlsExporter();
                        exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT,
                                                 jasperPrint);
                        exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,
                                                 bout);
                        exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
                                                 Boolean.TRUE);
                        exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE,
                                                 Boolean.TRUE);
                        exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,
                                                 Boolean.FALSE);
                        exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
                                                 Boolean.TRUE);
                        exporterXLS.exportReport();
                        byte[] buf = bout.toByteArray();
                        os.write(buf);

                    } else if (output.equalsIgnoreCase("html")) {
                        response.setContentType("text/html");
                        response.setHeader("Content-Disposition",
                                           "attachment;filename=\"Office_Wise_Gender.html\"");
                        PrintWriter out2 = response.getWriter();
                        JRHtmlExporter exporter = new JRHtmlExporter();
                        // File f=new
                        // File(getServletContext().getRealPath("/WEB-INF/Report/"));
                        // exporter.setParameter(JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR,true);
                        // exporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR_NAME,getServletContext().getRealPath("/WEB-INF/Report/"));
                        // exporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR,f);
                        exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,
                                              false);
                        exporter.setParameter(JRExporterParameter.JASPER_PRINT,
                                              jasperPrint);
                        exporter.setParameter(JRExporterParameter.OUTPUT_WRITER,
                                              out2);
                        exporter.exportReport();
                        out2.flush();
                        out2.close();
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println("After");

            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }

        } catch (Exception ex) {
            String connectMsg =
                "Could not create the report " + ex.getMessage() + " " +
                ex.getLocalizedMessage();
            System.out.println(connectMsg);
            ex.printStackTrace();
        }
        // RequestDispatcher dis=request.getRequestDispatcher("succ.jsp") ;
        // dis.forward(request, response);

    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {

        Connection connection = null;
        PreparedStatement ps1 = null;
        Statement s = null;
        ResultSet result1 = null;
        try {

            ResourceBundle rs =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
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
            System.out.println("Command....********" + strCommand);


        }

        catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }
        String html = "";
        if (strCommand.equalsIgnoreCase("region")) {

            /* to get the office level */
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
                /* other office */
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
                /* other office */
                System.out.println("office id:" + oid);
                System.out.println("dept id:" + deptid);

            } catch (Exception e) {
                System.out.println(e);
            }

            /* to get the office level */

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

            /* end of find the specific office region */

            System.out.println("region");
            System.out.println("Command::" + strCommand);
            try {
                // String
                // sql="select  REGION_OFFICE_ID,REGION_OFFICE_NAME from COM_MST_REGIONS_HVIEW  ";
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
                html =
html + "<tr ><td colspan='2'><a href='javascript:regionselectAll()'>Select All</a>&nbsp;&nbsp;&nbsp;<a href='javascript:regionclose()'>Close</a></td></tr>";
                boolean bool = false;
                while (result.next()) {
                    if (bool = !bool) {

                        html =
html + "<tr bgcolor=\"pink\"><td><input type='checkbox' name='chkregion' value='" +
 result.getInt("REGION_OFFICE_ID") + "'  ></td>";
                        html =
html + "<td>" + result.getString("REGION_OFFICE_NAME") + "</td></tr>";
                    } else {

                        html =
html + "<tr ><td><input type='checkbox' name='chkregion' value='" +
 result.getInt("REGION_OFFICE_ID") + "' ></td>";
                        html =
html + "<td>" + result.getString("REGION_OFFICE_NAME") + "</td></tr>";
                    }
                    count++;
                }

                if (count == 0) {
                    html = "There is no Region";
                }
                /*
				 * else { if(offlevel.equalsIgnoreCase("HO")){html=html+
				 * "<tr ><td colspan='2'><a href='javascript:regionselectAll()'>Select All</a>&nbsp;&nbsp;&nbsp;<a href='javascript:regionclose()'>Close</a></td></tr>"
				 * ; } else {html=html+
				 * "<tr ><td colspan='2'><a href='javascript:regionclose()'>Close</a></td></tr>"
				 * ; } //html=html+"</table>";
				 *
				 * }
				 */
                html = html + "</table>";

            } catch (Exception e) {
                System.out.println("Region selection error " + e);
                html = "There is no Region";

            }

        }

        else if (strCommand.equalsIgnoreCase("Status")) {
            try {

                String sql =
                    "select employee_status_id,employee_status_desc from hrm_mst_employee_status where employee_status_id not in ('SAN','VRS','DTH','DIS') order by employee_status_id";
                System.out.println(sql);
                Statement st = connection.createStatement();
                result = st.executeQuery(sql);

                int count = 0;
                html =
"<table cellpadding=0 cellspacing=0 border=0  width='100%'>";
                html =
html + "<tr ><td colspan='2'><a href='javascript:statusselectAll()'>Select All</a>&nbsp;&nbsp;&nbsp;<a href='javascript:statusclose()'>Close</a></td></tr>";
                boolean bool = false;
                while (result.next()) {
                    if (bool = !bool) {
                        html =
html + "<tr bgcolor=\"pink\" ><td ><input type='checkbox' name='chkstatus' value='" +
 result.getString("employee_status_id") + "'  ></td>";
                        html =
html + "<td >" + result.getString("employee_status_desc") + "</td></tr>";
                    } else {
                        html =
html + "<tr ><td ><input type='checkbox' name='chkstatus' value='" +
 result.getString("employee_status_id") + "' ></td>";
                        html =
html + "<td >" + result.getString("employee_status_desc") + "</td></tr>";
                    }
                    count++;
                }
                html = html + "</table>";

                if (count == 0) {
                    html = "There is no Status";
                }

                html = html + "</tbody></table>";
                System.out.println("----------------------------------------------------------");
            } catch (Exception e) {
                System.out.println("Circle selection error " + e);
                html = "There is no Circle";

            }

            System.out.println("------------------------End-----------------------");
        } else if (strCommand.equalsIgnoreCase("circle")) {

            System.out.println("circle");
            System.out.println("Command::" + strCommand);

            /* to get the office level */
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
                /* other office */
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
                /* other office */
                System.out.println("office id:" + oid);
                System.out.println("dept id:" + deptid);

            } catch (Exception e) {
                System.out.println(e);
            }

            /* to get the office level */

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

            /* end of find the specific office region */

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
                html =
html + "<tr ><td colspan='2'><a href='javascript:circleselectAll()'>Select All</a>&nbsp;&nbsp;&nbsp;<a href='javascript:circleclose()'>Close</a></td></tr>";
                boolean bool = false;
                while (result.next()) {
                    if (bool = !bool) {
                        html =
html + "<tr bgcolor=\"pink\" ><td ><input type='checkbox' name='chkcircle' value='" +
 result.getInt("CIRCLE_OFFICE_ID") + "'  ></td>";
                        html =
html + "<td >" + result.getString("CIRCLE_OFFICE_NAME") + "</td></tr>";
                    } else {
                        html =
html + "<tr ><td ><input type='checkbox' name='chkcircle' value='" +
 result.getInt("CIRCLE_OFFICE_ID") + "' ></td>";
                        html =
html + "<td >" + result.getString("CIRCLE_OFFICE_NAME") + "</td></tr>";
                    }
                    count++;
                }
                html = html + "</table>";

                if (count == 0) {
                    html = "There is no Circle";
                }
                /*
				 * else { if(offlevel.equalsIgnoreCase("HO") ||
				 * offlevel.equalsIgnoreCase("RN")){html=html+
				 * "<tr ><td colspan='2'><a href='javascript:circleselectAll()'>Select All</a>&nbsp;&nbsp;&nbsp;<a href='javascript:circleclose()'>Close</a></td></tr>"
				 * ; } else {html=html+
				 * "<tr ><td colspan='2'><a href='javascript:circleclose()'>Close</a></td></tr>"
				 * ; } html=html+"</tbody></table>";
				 *
				 * }
				 */

                html = html + "</tbody></table>";

            } catch (Exception e) {
                System.out.println("Circle selection error " + e);
                html = "There is no Circle";

            }
        }

        else if (strCommand.equalsIgnoreCase("division")) {

            System.out.println("division");
            System.out.println("Command::" + strCommand);
            /* to get the office level */
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
                /* other office */
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
                /* other office */
                System.out.println("office id:" + oid);
                System.out.println("dept id:" + deptid);

            } catch (Exception e) {
                System.out.println(e);
            }

            /* to get the office level */

            /* find the specific Office */

            System.out.println("division office Id:" + oid);

            /* end of find the specific office region */

            try {
                String options = request.getParameter("circles");
                String oftyp = request.getParameter("offtype");
                String reg = request.getParameter("regions");

                System.out.println("circle selected:" + options);
                System.out.println("office type selected..." + oftyp);
                System.out.println("region selected...." + reg);
                if (offlevel.equalsIgnoreCase("HO")) {
                    // String
                    // sql="select  DIVISION_OFFICE_ID,DIVISION_OFFICE_NAME from COM_MST_DIVISIONS_HVIEW where  CIRCLE_OFFICE_ID in ("+options+")";

                    String sql =
                        " select DIVISION_OFFICE_ID,DIVISION_OFFICE_NAME,primary_work_id from " +
                        " (" +
                        " select DIVISION_OFFICE_ID,DIVISION_OFFICE_NAME,circle_office_id,REGION_OFFICE_ID from COM_MST_DIVISIONS_HVIEW" +
                        " where REGION_OFFICE_ID in (" + reg +
                        ") and CIRCLE_OFFICE_ID in (" + options + ") " +
                        " ) a" + " left outer join" + " (" +
                        " select office_id ,primary_work_id from com_mst_offices" +
                        " ) b" + " on a.division_office_id=b.office_id" +
                        " where primary_work_id in (" + oftyp + ")";

                    System.out.println(sql);
                    /*
					 * ps1=connection.prepareStatement(sql);
					 * ps1.setString(1,options); ps1.setString(2,oftyp);
					 * result=ps1.executeQuery();
					 */

                    Statement st = connection.createStatement();
                    result = st.executeQuery(sql);

                } else if (offlevel.equalsIgnoreCase("RN")) {

                    String sql =
                        " select DIVISION_OFFICE_ID,DIVISION_OFFICE_NAME,primary_work_id from " +
                        " (" +
                        " select DIVISION_OFFICE_ID,DIVISION_OFFICE_NAME,circle_office_id,REGION_OFFICE_ID from COM_MST_DIVISIONS_HVIEW" +
                        " where REGION_OFFICE_ID in (" + oid +
                        ") and CIRCLE_OFFICE_ID in (" + options + ") " +
                        " ) a" + " left outer join" + " (" +
                        " select office_id ,primary_work_id from com_mst_offices" +
                        " ) b" + " on a.division_office_id=b.office_id" +
                        " where primary_work_id in (" + oftyp + ")";

                    System.out.println(sql);
                    /*
					 * ps1=connection.prepareStatement(sql);
					 * ps1.setString(1,options); ps1.setString(2,oftyp);
					 * result=ps1.executeQuery();
					 */

                    Statement st = connection.createStatement();
                    result = st.executeQuery(sql);
                } else if (offlevel.equalsIgnoreCase("CL")) {
                    String sql =
                        " select DIVISION_OFFICE_ID,DIVISION_OFFICE_NAME,primary_work_id from " +
                        " (" +
                        " select DIVISION_OFFICE_ID,DIVISION_OFFICE_NAME,circle_office_id,REGION_OFFICE_ID from COM_MST_DIVISIONS_HVIEW" +
                        " where CIRCLE_OFFICE_ID in (" + options + ") " +
                        " ) a" + " left outer join" + " (" +
                        " select office_id ,primary_work_id from com_mst_offices" +
                        " ) b" + " on a.division_office_id=b.office_id" +
                        " where primary_work_id in (" + oftyp + ")";

                    System.out.println(sql);
                    /*
					 * ps1=connection.prepareStatement(sql);
					 * ps1.setString(1,options); ps1.setString(2,oftyp);
					 * result=ps1.executeQuery();
					 */

                    Statement st = connection.createStatement();
                    result = st.executeQuery(sql);
                }

                else {
                    String sql =
                        "select  DIVISION_OFFICE_ID,DIVISION_OFFICE_NAME from COM_MST_DIVISIONS_HVIEW where  DIVISION_OFFICE_ID =" +
                        oid;
                    Statement st = connection.createStatement();
                    result = st.executeQuery(sql);
                }

                int count = 0;
                html =
"<table cellpadding=0 cellspacing=0 border=0  width='100%'>";
                html =
html + "<tr ><td colspan='2'><a href='javascript:divisionselectAll()'>Select All</a>&nbsp;&nbsp;&nbsp;<a href='javascript:divisionclose()'>Close</a></td></tr>";
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
                    html = "There is no Division";
                }
                /*
				 * else { if(offlevel.equalsIgnoreCase("HO") ||
				 * offlevel.equalsIgnoreCase("RN") ||
				 * offlevel.equalsIgnoreCase("CL")){html=html+
				 * "<tr ><td colspan='2'><a href='javascript:divisionselectAll()'>Select All</a>&nbsp;&nbsp;&nbsp;<a href='javascript:divisionclose()'>Close</a></td></tr>"
				 * ; } else{html=html+
				 * "<tr ><td colspan='2'><a href='javascript:divisionclose()'>Close</a></td></tr>"
				 * ; } html=html+"</tbody></table>";
				 *
				 * }
				 */

                html = html + "</tbody></table>";

            } catch (Exception e) {
                System.out.println("Division selection error " + e);
                html = "There is no Division";

            }

        } else if (strCommand.equalsIgnoreCase("EmpType")) {
            System.out.println("########################################################");
            try {
                String workstat = request.getParameter("status");
                String designation = request.getParameter("des");
                String officeDetails = request.getParameter("officeselected");
                int hlevel = Integer.parseInt(request.getParameter("hlevel"));
                String sql = "";
                if (officeDetails.equalsIgnoreCase("all")) {
                    System.out.println("----------------------****************-----all part");
                    if (designation.equalsIgnoreCase("All")) {
                        sql =
 "select a.DESIGNATION_ID, b.designation,a.total from ( " +
   "select DESIGNATION_ID,count(DESIGNATION_ID) as total from HRM_EMP_CURRENT_POSTING " +
   "where EMPLOYEE_STATUS_ID in(" +
   workstat.substring(0, workstat.length() - 1) + ") " +
   " group by DESIGNATION_ID " + ") a left outer join( " +
   "select DESIGNATION_ID, designation from hrm_mst_designations" +
   ") b on a.DESIGNATION_ID=b.DESIGNATION_ID";
                    } else {
                        sql =
 "select a.DESIGNATION_ID, b.designation,a.total from " +
   "(select DESIGNATION_ID,count(DESIGNATION_ID) as total " +
   "from HRM_EMP_CURRENT_POSTING where EMPLOYEE_STATUS_ID in(" +
   workstat.substring(0, workstat.length() - 1) + ") " +
   "and DESIGNATION_ID in (select DESIGNATION_ID from " +
   " HRM_MST_DESIGNATIONS where SERVICE_GROUP_ID in ('" + designation +
   "')) " + "group by DESIGNATION_ID) a left outer join " +
   "(select DESIGNATION_ID, designation from hrm_mst_designations" +
   ") b on a.DESIGNATION_ID=b.DESIGNATION_ID";
                    }
                } else {
                    officeDetails =
                            "'" + officeDetails.replace(",", "','") + "'";
                    System.out.println("enter *********************8" +
                                       workstat.substring(0,
                                                          workstat.length() -
                                                          1));

                    if (designation.equalsIgnoreCase("All")) {
                        if (hlevel == 1) {
                            sql =
 "select a.DESIGNATION_ID, b.designation,a.total from ( " +
   "select DESIGNATION_ID,count(DESIGNATION_ID) as total from HRM_EMP_CURRENT_POSTING " +
   "where EMPLOYEE_STATUS_ID in(" +
   workstat.substring(0, workstat.length() - 1) + ") " +
   "and OFFICE_ID in (select OFFICE_ID from COM_OFFICE_CONTROL where controlling_office_id in(" +
   officeDetails + ")) group by DESIGNATION_ID " + ") a left outer join( " +
   "select DESIGNATION_ID, designation from hrm_mst_designations" +
   ") b on a.DESIGNATION_ID=b.DESIGNATION_ID";
                        } else {
                            sql =
 "select a.DESIGNATION_ID, b.designation,a.total from ( " +
   "select DESIGNATION_ID,count(DESIGNATION_ID) as total from HRM_EMP_CURRENT_POSTING " +
   "where EMPLOYEE_STATUS_ID in(" +
   workstat.substring(0, workstat.length() - 1) + ") " + "and OFFICE_ID in (" +
   officeDetails + ") group by DESIGNATION_ID " + ") a left outer join( " +
   "select DESIGNATION_ID, designation from hrm_mst_designations" +
   ") b on a.DESIGNATION_ID=b.DESIGNATION_ID";
                        }
                    } else {
                        if (hlevel == 1) {
                            sql =
 "select a.DESIGNATION_ID, b.designation,a.total from " +
   "(select DESIGNATION_ID,count(DESIGNATION_ID) as total " +
   "from HRM_EMP_CURRENT_POSTING where EMPLOYEE_STATUS_ID in(" +
   workstat.substring(0, workstat.length() - 1) + ") " +
   "and DESIGNATION_ID in (select DESIGNATION_ID from " +
   " HRM_MST_DESIGNATIONS where SERVICE_GROUP_ID in ('" + designation +
   "')) and OFFICE_ID in (select OFFICE_ID from COM_OFFICE_CONTROL where controlling_office_id in(" +
   officeDetails + ")) " + "group by DESIGNATION_ID) a left outer join " +
   "(select DESIGNATION_ID, designation from hrm_mst_designations" +
   ") b on a.DESIGNATION_ID=b.DESIGNATION_ID";
                        } else {
                            sql =
 "select a.DESIGNATION_ID, b.designation,a.total from " +
   "(select DESIGNATION_ID,count(DESIGNATION_ID) as total " +
   "from HRM_EMP_CURRENT_POSTING where EMPLOYEE_STATUS_ID in(" +
   workstat.substring(0, workstat.length() - 1) + ") " +
   "and DESIGNATION_ID in (select DESIGNATION_ID from " +
   " HRM_MST_DESIGNATIONS where SERVICE_GROUP_ID in ('" + designation +
   "')) and OFFICE_ID in (" + officeDetails + ") " +
   "group by DESIGNATION_ID) a left outer join " +
   "(select DESIGNATION_ID, designation from hrm_mst_designations" +
   ") b on a.DESIGNATION_ID=b.DESIGNATION_ID";
                        }
                    }
                }

                System.out.println(sql);
                Statement st = connection.createStatement();
                result = st.executeQuery(sql);

                int count = 0;
                html =
"<center><table cellpadding=0 cellspacing=0 border=1  width='100%'>";
                html =
html + "<tr class=tdH><th align=left>Designation</th><th align=left>Total</tr>";
                boolean bool = false;
                while (result.next()) {
                    String s1 = "";

                    s1 =
  workstat.substring(0, workstat.length() - 1).replace("'", "");

                    String desid = result.getString("DESIGNATION_ID"), desi =
                        designation;
                    s1 =
  s1 + "&desid=" + desid + "&designation=" + result.getString("designation").replace(" ",
                                                                                     "&nbsp;");
                    s1 =
  s1 + "&officeDetail=" + officeDetails.replace("'", "");
                    s1 = s1 + "&hlevel=" + hlevel;
                    System.out.println("--------------------" + s1);
                    if (bool = !bool) {

                        // html=html+"<tr ><td ><a HREF='javascript:void(0)' " +
                        // "onclick='javascript:window.open('status_report_popup.jsp?&"+s1.replace("'","")+"')'>"+result.getString("designation")+"</a></td>";
                        html =
html + "<tr><td align=left><a href='javascript:viewEmpbyDesg(" + desid +
 ");'>" + result.getString("designation") + "</a><input type=hidden id=name" +
 desid + " name=name" + desid + " value=" + s1 + " /></td>";
                        html =
html + "<td align=left>" + result.getString("total") + "</td></tr>";
                    } else {
                        // html=html+"<tr ><td ><a HREF='javascript:void(0)' " +
                        html =
html + "<tr><td align=left><a href='javascript:viewEmpbyDesg(" + desid +
 ");'>" + result.getString("designation") + "</a><input type=hidden id=name" +
 desid + " name=name" + desid + " value=" + s1 + " /></td>";
                        html =
html + "<td align=left>" + result.getString("total") + "</td></tr>";
                    }
                    count++;
                }
                html = html + "</table></center>";

                if (count == 0) {
                    html =
"<center><table cellpadding=0 cellspacing=0 border=1  width='100%'>";
                    html = html + "There is no Status";
                }

                html = html + "</tbody></table>";
                System.out.println("----------------------------------------------------------");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Circle selection error " + e);
                html = "error occured";

            }

            System.out.println("------------------------End-----------------------");

        }

        else if (strCommand.equalsIgnoreCase("viewEmp")) {
            System.out.println("enter view employess");
            try {
                String workstat = request.getParameter("workstat");
                String designation = request.getParameter("desid");
                String officedetail = request.getParameter("officeDetail");
                int hlevel = Integer.parseInt(request.getParameter("hlevel"));
                System.out.println("enter" +
                                   workstat.substring(0, workstat.length() -
                                                      1) +
                                   "5555555555555555555&&&&&&&&" + hlevel);
                String sql = "";
                System.out.println("workstat---33333" + workstat);
                if (officedetail.equalsIgnoreCase("all")) {
                    System.out.println("---------------------------all part");
                    sql =
 "select EMPLOYEE_ID,empname,designation,EMPLOYEE_STATUS_DESC,DATE_EFFECTIVE_FROM,DECODE(OFFICE_NAME,null,' ',(null),' ',OFFICE_NAME) as OFFICE_NAME from " +
   "(select EMPLOYEE_ID,empname,designation,EMPLOYEE_STATUS_DESC,DATE_EFFECTIVE_FROM,OFFICE_ID " +
   "from (select EMPLOYEE_ID,empname,designation,EMPLOYEE_STATUS_ID,DATE_EFFECTIVE_FROM,OFFICE_ID " +
   "from (select designation,EMPLOYEE_ID as id1,EMPLOYEE_STATUS_ID,DATE_EFFECTIVE_FROM,OFFICE_ID from " +
   "(select DESIGNATION_ID,EMPLOYEE_ID,EMPLOYEE_STATUS_ID,DATE_EFFECTIVE_FROM,OFFICE_ID " +
   "from HRM_EMP_CURRENT_POSTING where EMPLOYEE_STATUS_ID in('" +
   workstat.replace(",", "','") + "')" + " and DESIGNATION_ID=" + designation +
   " ) a left outer join (select DESIGNATION_ID, designation " +
   " from hrm_mst_designations) b on a.DESIGNATION_ID=b.DESIGNATION_ID) c left outer join" +
   " (select EMPLOYEE_ID,EMPLOYEE_NAME ||'.'|| EMPLOYEE_INITIAL as empname from HRM_MST_EMPLOYEES) d on c.id1=d.EMPLOYEE_ID) e left outer join" +
   " (select EMPLOYEE_STATUS_ID,EMPLOYEE_STATUS_DESC from HRM_MST_EMPLOYEE_STATUS)f " +
   " on e.EMPLOYEE_STATUS_ID=f.EMPLOYEE_STATUS_ID) g left outer join (select OFFICE_ID as id2,OFFICE_NAME from COM_MST_OFFICES) h on g.OFFICE_ID=h.id2";

                } else {
                    officedetail = officedetail.replace(",", "','");
                    if (hlevel == 1) {
                        sql =
 "select EMPLOYEE_ID,empname,designation,EMPLOYEE_STATUS_DESC,DATE_EFFECTIVE_FROM,DECODE(OFFICE_NAME,null,' ',(null),' ',OFFICE_NAME) as OFFICE_NAME from " +
   "(select EMPLOYEE_ID,empname,designation,EMPLOYEE_STATUS_DESC,DATE_EFFECTIVE_FROM,OFFICE_ID " +
   "from (select EMPLOYEE_ID,empname,designation,EMPLOYEE_STATUS_ID,DATE_EFFECTIVE_FROM,OFFICE_ID " +
   "from (select designation,EMPLOYEE_ID as id1,EMPLOYEE_STATUS_ID,DATE_EFFECTIVE_FROM,OFFICE_ID from " +
   "(select DESIGNATION_ID,EMPLOYEE_ID,EMPLOYEE_STATUS_ID,DATE_EFFECTIVE_FROM,OFFICE_ID " +
   "from HRM_EMP_CURRENT_POSTING where EMPLOYEE_STATUS_ID in('" +
   workstat.replace(",", "','") + "')" + " and DESIGNATION_ID=" + designation +
   " and OFFICE_ID in (select OFFICE_ID from COM_OFFICE_CONTROL where controlling_office_id in('" +
   officedetail +
   "'))) a left outer join (select DESIGNATION_ID, designation " +
   " from hrm_mst_designations) b on a.DESIGNATION_ID=b.DESIGNATION_ID) c left outer join" +
   " (select EMPLOYEE_ID,EMPLOYEE_NAME ||'.'|| EMPLOYEE_INITIAL as empname from HRM_MST_EMPLOYEES) d on c.id1=d.EMPLOYEE_ID) e left outer join" +
   " (select EMPLOYEE_STATUS_ID,EMPLOYEE_STATUS_DESC from HRM_MST_EMPLOYEE_STATUS)f " +
   " on e.EMPLOYEE_STATUS_ID=f.EMPLOYEE_STATUS_ID) g left outer join (select OFFICE_ID as id2,OFFICE_NAME from COM_MST_OFFICES) h on g.OFFICE_ID=h.id2";

                    } else {


                        sql =
 "select EMPLOYEE_ID,empname,designation,EMPLOYEE_STATUS_DESC,DATE_EFFECTIVE_FROM,DECODE(OFFICE_NAME,null,' ',(null),' ',OFFICE_NAME) as OFFICE_NAME from " +
   "(select EMPLOYEE_ID,empname,designation,EMPLOYEE_STATUS_DESC,DATE_EFFECTIVE_FROM,OFFICE_ID " +
   "from (select EMPLOYEE_ID,empname,designation,EMPLOYEE_STATUS_ID,DATE_EFFECTIVE_FROM,OFFICE_ID " +
   "from (select designation,EMPLOYEE_ID as id1,EMPLOYEE_STATUS_ID,DATE_EFFECTIVE_FROM,OFFICE_ID from " +
   "(select DESIGNATION_ID,EMPLOYEE_ID,EMPLOYEE_STATUS_ID,DATE_EFFECTIVE_FROM,OFFICE_ID " +
   "from HRM_EMP_CURRENT_POSTING where EMPLOYEE_STATUS_ID in('" +
   workstat.replace(",", "','") + "')" + " and DESIGNATION_ID=" + designation +
   " and OFFICE_ID in ('" + officedetail +
   "')) a left outer join (select DESIGNATION_ID, designation " +
   " from hrm_mst_designations) b on a.DESIGNATION_ID=b.DESIGNATION_ID) c left outer join" +
   " (select EMPLOYEE_ID,EMPLOYEE_NAME ||'.'|| EMPLOYEE_INITIAL as empname from HRM_MST_EMPLOYEES) d on c.id1=d.EMPLOYEE_ID) e left outer join" +
   " (select EMPLOYEE_STATUS_ID,EMPLOYEE_STATUS_DESC from HRM_MST_EMPLOYEE_STATUS)f " +
   " on e.EMPLOYEE_STATUS_ID=f.EMPLOYEE_STATUS_ID) g left outer join (select OFFICE_ID as id2,OFFICE_NAME from COM_MST_OFFICES) h on g.OFFICE_ID=h.id2";
                    }
                }
                System.out.println(sql);
                Statement st = connection.createStatement();
                result = st.executeQuery(sql);

                int count = 0, i = 0;
                html =
"<center><table cellpadding=0 cellspacing=0 border=1  width='100%'>";
                // html=html+"<tr class=tdH><th align=left> </th><th align=left>SNo</th><th align=left>Employee ID</th><th align=left>Employee Name</th>"
                // +
                // "<th align=left>Office Name</th><th align=left>Joining Date</th><th align=left>Status</tr>";
                boolean bool = false;
                while (result.next()) {
                    i++;
                    if (bool = !bool) {
                        html =
html + "<tr><td rowspan='6'><img src='ShowImageProfile.jsp?empid=" +
 result.getString("EMPLOYEE_ID") +
 "' alt='image' height='115' width='100'></img></td>";
                        html =
html + "<td >SNO</td><td>" + i + "</td></tr><tr><td>Employee ID</td><td >" +
 result.getString("EMPLOYEE_ID") + "</td></tr>";
                        html =
html + "<tr><td>Employee Name</td><td >" + result.getString("empname") +
 "</td></tr>";
                        html =
html + "<tr><td>Office Name</td><td >" + result.getString("OFFICE_NAME") +
 "</td></tr>";
                        html =
html + "<tr><td>joining Date</td><td >" + result.getDate("DATE_EFFECTIVE_FROM") +
 "</td></tr>";
                        html =
html + "<tr><td>status</td><td >" + result.getString("EMPLOYEE_STATUS_DESC") +
 "</td></tr>";
                    } else {
                        html =
html + "<tr><td rowspan='6'><img src='ShowImageProfile.jsp?empid=" +
 result.getString("EMPLOYEE_ID") +
 "' alt='image' height='115' width='100'></img></td>";
                        html =
html + "<td >SNO</td><td>" + i + "</td></tr><tr><td>Employee ID</td><td >" +
 result.getString("EMPLOYEE_ID") + "</td></tr>";
                        html =
html + "<tr><td>Employee Name</td><td >" + result.getString("empname") +
 "</td></tr>";
                        html =
html + "<tr><td>Office Name</td><td >" + result.getString("OFFICE_NAME") +
 "</td></tr>";
                        html =
html + "<tr><td>joining Date</td><td >" + result.getDate("DATE_EFFECTIVE_FROM") +
 "</td></tr>";
                        html =
html + "<tr><td>status</td><td >" + result.getString("EMPLOYEE_STATUS_DESC") +
 "</td></tr>";
                    }
                    count++;
                }
                html = html + "</table></center>";

                if (count == 0) {
                    html = "There is no Status";
                }

                html = html + "</tbody></table>";
                System.out.println("----------------------------------------------------------");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Circle selection error " + e);
                html = "There is no Records";

            }

            System.out.println("------------------------End-----------------------");

        }

        else if (strCommand.equalsIgnoreCase("offtype")) {

            System.out.println("office type.......");
            System.out.println("Command::" + strCommand);
            try {
                // String
                // sql="select  REGION_OFFICE_ID,REGION_OFFICE_NAME from COM_MST_REGIONS_HVIEW  ";
                System.out.println("before select statement");
                /*
				 * if(strCommand.equalsIgnoreCase("offtype")) {
				 */
                System.out.println("inside offtype");
                String sql = "select * from COM_MST_WORK_NATURE";
                System.out.println(sql);
                ps1 = connection.prepareStatement(sql);
                // s=connection.createStatement();
                result1 = ps1.executeQuery(sql);
                System.out.println(result1);

                // }
                /*
				 * else { Stringsql=
				 * "select  REGION_OFFICE_ID,REGION_OFFICE_NAME from COM_MST_REGIONS_HVIEW where REGION_OFFICE_ID=? "
				 * ; ps=connection.prepareStatement(sql ); ps.setInt(1,oid);
				 * result=ps.executeQuery(); }
				 */
                int count = 0;
                System.out.println(count);
                html =
"<table cellpadding=0 cellspacing=0 border=0 width='100%'>";
                html =
html + "<tr ><td colspan='2'><a href='javascript:offtypeselectAll()'>Select All</a>&nbsp;&nbsp;&nbsp;<a href='javascript:offtypeclose()'>Close</a></td></tr>";
                System.out.println(html);

                boolean bool = false;
                System.out.println(bool);
                System.out.println(result1);
                while (result1.next()) {

                    if (bool = !bool) {

                        html =
html + "<tr bgcolor=\"pink\"><td><input type='checkbox' name='chkofftype' value='" +
 result1.getString("WORK_NATURE_ID") + "' onclick='oftypeonchange()' ></td>";
                        html =
html + "<td>" + result1.getString("WORK_NATURE_DESC") + "</td></tr>";
                        System.out.println(html);

                    }

                    else {
                        System.out.println("inside else part");
                        html =
html + "<tr ><td><input type='checkbox' name='chkofftype' value='" +
 result1.getString("WORK_NATURE_ID") + "' onclick='oftypeonchange()'></td>";
                        html =
html + "<td>" + result1.getString("WORK_NATURE_DESC") + "</td></tr>";
                    }
                    count++;
                }
                // html=html+"</table>";

                if (count == 0) {
                    html = "There is no Office Type";
                }
                /*
				 * else { if(strCommand.equalsIgnoreCase("offtype")) {
				 * html=html+
				 * "<tr ><td colspan='2'><a href='javascript:offtypeselectAll()'>Select All</a>&nbsp;&nbsp;&nbsp;<a href='javascript:offtypeclose()'>Close</a></td></tr>"
				 * ; } else {html=html+
				 * "<tr ><td colspan='2'><a href='javascript:offtypeclose()'>Close</a></td></tr>"
				 * ; } html=html+"</tbody></table>";
				 *
				 * }
				 */

                html = html + "</tbody></table>";
            }

            catch (Exception e) {
                System.out.println("Office Type selection error " +
                                   e.getMessage());
                html = "There is no Office Type";

            }

        }

        System.out.println("Html:" + html);
        out.println(html);

    }

}
