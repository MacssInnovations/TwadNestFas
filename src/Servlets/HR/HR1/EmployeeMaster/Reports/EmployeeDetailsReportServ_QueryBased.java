package Servlets.HR.HR1.EmployeeMaster.Reports;

import Servlets.Security.classes.UserProfile;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

public class EmployeeDetailsReportServ_QueryBased extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        String cmd = request.getParameter("command");
        System.out.println("command:" + cmd);
        if (!cmd.equalsIgnoreCase("EmployeeProfile")) {
            PrintWriter out = response.getWriter();
            String CONTENT_TYPE = "text/xml";
            response.setContentType(CONTENT_TYPE);
            response.setHeader("cache-control", "no-cache");
            Connection con = null;
            Statement st = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            String xml = null;
            System.out.println("welcome to servlet");
            try {
                ResourceBundle rs2 =
                    ResourceBundle.getBundle("Servlets.Security.servlets.Config");
                String ConnectionString = "";

                String strDriver = rs2.getString("Config.DATA_BASE_DRIVER");
                String strdsn = rs2.getString("Config.DSN");
                String strhostname = rs2.getString("Config.HOST_NAME");
                String strportno = rs2.getString("Config.PORT_NUMBER");
                String strsid = rs2.getString("Config.SID");
                String strdbusername = rs2.getString("Config.USER_NAME");
                String strdbpassword = rs2.getString("Config.PASSWORD");
                //  strfile_path=rs2.getString("Config.FILE_PATH");

                ConnectionString =
                        strdsn.trim() + "@" + strhostname.trim() + ":" +
                        strportno.trim() + ":" + strsid.trim();
                Class.forName(strDriver.trim());
                con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
                try {
                    st = con.createStatement();
                    con.clearWarnings();
                } catch (SQLException e) {
                    System.out.println("Exception in creating statement:" + e);
                }
            } catch (Exception e) {
                System.out.println("Exception in opening connection:" + e);
            }
            HttpSession session = request.getSession(false);
            UserProfile empProfile =
                (UserProfile)session.getAttribute("UserProfile");
            int eid = empProfile.getEmployeeId();
            System.out.println("employee id:" + eid);
            xml = "<response>";
            if (cmd.equalsIgnoreCase("loadServiceGroup")) {
                CONTENT_TYPE = "text/html";
                System.out.println("inside loadServiceGroup");
                String html = "";
                try {
                    rs =
  st.executeQuery("select SERVICE_GROUP_ID,SERVICE_GROUP_NAME from HRM_MST_SERVICE_GROUP  order by SERVICE_GROUP_NAME");
                    int count = 0;
                    html =
"<table cellpadding='1%' cellspacing='1%' border='1' width='100%'>";
                    boolean bool = false;
                    while (rs.next()) {
                        int val = rs.getInt("SERVICE_GROUP_ID");
                        String name = rs.getString("SERVICE_GROUP_NAME");
                        System.out.println("value :" + val);
                        if (bool = !bool) {
                            html =
html + "<tr bgcolor='pink'><td><input type='checkbox' name='service_group' id='service_group' value=" +
 val + " onclick='checkDesignation()'></input></td>";
                            html = html + "<td>" + name + "</td></tr>";
                        } else {
                            html =
html + "<tr ><td><input type='checkbox' name='service_group' id='service_group' value=" +
 val + " onclick='checkDesignation()'></input></td>";
                            html = html + "<td>" + name + "</td></tr>";
                        }
                        count++;
                    }
                    if (count == 0) {
                        html = "There is no Service Group";
                    } else {
                        html =
html + "<tr ><td colspan='2'><a href=javascript:elementSelectAll('ServiceGroup')>Select All</a>&nbsp;&nbsp;&nbsp;<a href=javascript:elementclose('ServiceGroup')>Close</a></td></tr>";
                    }
                    html = html + "</table>";
                    System.out.println("html:" + html);
                    out.println(html);
                } catch (Exception e) {
                    System.out.println("Sample selection error " + e);
                    System.out.println("html:" + html);
                }
            } else if (cmd.equalsIgnoreCase("loadDesignation")) {
                String sgroup[] =
                    request.getParameter("servicegroup").split(",");
                CONTENT_TYPE = "text/html";
                System.out.println("inside loadDesignation");
                String html = "", sql = "";
                try {
                    sql =
 "select distinct designation_id,designation from hrm_mst_designations where service_group_id in(";
                    for (int i = 0; i < sgroup.length; i++) {
                        if (i == sgroup.length - 1)
                            sql = sql + sgroup[i];
                        else
                            sql = sql + sgroup[i] + ",";
                    }

                    sql = sql + ") order by designation";
                    System.out.println("sql:" + sql);
                    rs = st.executeQuery(sql);
                    int count = 0;
                    html =
"<table cellpadding='1%' cellspacing='1%' border='1' width='100%'>";
                    boolean bool = false;
                    while (rs.next()) {
                        int val = rs.getInt("designation_id");
                        String name = rs.getString("designation");
                        System.out.println("value :" + val);
                        if (bool = !bool) {
                            html =
html + "<tr bgcolor='pink'><td><input type='checkbox' name='design' id='design' value=" +
 val + "></input></td>";
                            html = html + "<td>" + name + "</td></tr>";
                        } else {
                            html =
html + "<tr ><td><input type='checkbox' name='design' id='design' value=" +
 val + "></input></td>";
                            html = html + "<td>" + name + "</td></tr>";
                        }
                        count++;
                    }
                    if (count == 0) {
                        html = "There is no Designation";
                    } else {
                        html =
html + "<tr ><td colspan='2'><a href=javascript:elementSelectAll('designation')>Select All</a>&nbsp;&nbsp;&nbsp;<a href=javascript:elementclose('designation')>Close</a></td></tr>";
                    }
                    html = html + "</table>";
                    System.out.println("html:" + html);
                    out.println(html);
                } catch (Exception e) {
                    System.out.println("Sample selection error " + e);
                    System.out.println("html:" + html);
                }
            }
            // out.close();
        } else {
            doPost(request, response);
        }
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        response.setContentType(CONTENT_TYPE);
        System.out.println("Welcome to post servlet");
        Connection con = null;
        Statement st = null;
        JasperPrint jasperPrint = null;
        try {
            /*Class.forName("oracle.jdbc.OracleDriver");
                       con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","wqlabs","wqlabs123");*/

            ResourceBundle rs1 =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";

            String strDriver = rs1.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rs1.getString("Config.DSN");
            String strhostname = rs1.getString("Config.HOST_NAME");
            String strportno = rs1.getString("Config.PORT_NUMBER");
            String strsid = rs1.getString("Config.SID");
            String strdbusername = rs1.getString("Config.USER_NAME");
            String strdbpassword = rs1.getString("Config.PASSWORD");

            ConnectionString =
                    strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
                    ":" + strsid.trim();
            Class.forName(strDriver.trim());
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
            try {
                st = con.createStatement();
                con.clearWarnings();
            } catch (SQLException e) {
                System.out.println("Exception in creating statement:" + e);
            }
        } catch (Exception e) {
            System.out.println("Exception in opening connection:" + e);
        }
        File reportFile = null;
        JasperReport jasperReport = null;
        Map map = new HashMap();
        String servicegroup = request.getParameter("servicegroup");
        String designation = request.getParameter("designation");
        String community = request.getParameter("community");
        String district = request.getParameter("district");
        String qualification = request.getParameter("qualification");
        String specialisation = request.getParameter("specialisation");
        String qry = "", path = "";
        try {
            String command = request.getParameter("command");
            System.out.println("command:" + command);
            if (command == null) {
                qry =
 "select b.employee_id,b.office_id,date_effective_from,employee_name||decode(employee_initial,null,' ','.'||employee_initial)as employee_name,qualifications,office_name from" +
   "(select service_group_id,designation_id from hrm_mst_designations" +
   ")a inner join" +
   "(select employee_id,office_id,designation_id,date_effective_from,department_id,employee_status_id from hrm_emp_current_posting  " +
   "where employee_id in (select employee_id from hrm_mst_employees where is_consolidate='N') " +
   ")b on a.designation_id=b.designation_id left outer join" +
   "(select employee_id,employee_name,employee_initial,community_id,native_district_code,qualifications from hrm_mst_employees where is_consolidate='N' " +
   ")c on b.employee_id=c.employee_id left outer join" +
   "(select office_id,office_name from com_mst_offices" +
   ")d on b.office_id=d.office_id where b.department_id='TWAD' and b.employee_status_id not in('SAN','VRS','DIS','DTH') ";
                System.out.println("qry:" + qry);
                if (!servicegroup.equals("")) {
                    String group[] = servicegroup.split(",");
                    qry = qry + " and a.service_group_id in(";
                    for (int i = 0; i < group.length; i++) {
                        if (i == group.length - 1)
                            qry = qry + group[i];
                        else
                            qry = qry + group[i] + ",";
                    }
                    qry = qry + ")";
                }
                if (!designation.equals("")) {
                    String design[] = designation.split(",");
                    qry = qry + " and a.designation_id in(";
                    for (int i = 0; i < design.length; i++) {
                        if (i == design.length - 1)
                            qry = qry + design[i];
                        else
                            qry = qry + design[i] + ",";
                    }
                    qry = qry + ")";
                }
                System.out.println("qry2:" + qry);
                System.out.println("community:" + community);
                if (!community.equals("") && !community.equals("All")) {
                    qry = qry + " and c.community_id='" + community + "'";
                }
                System.out.println("district:" + district);
                if (!district.equals("") && !district.equals("All")) {
                    if (!district.equalsIgnoreCase("All")) {
                        qry =
 qry + " and c.native_district_code=" + Integer.parseInt(district);
                    }
                }
                if (!qualification.equals("")) {
                    qry =
 qry + " and c.qualifications like '%" + qualification + "%'";
                }
                if (!specialisation.equals("")) {
                    qry =
 qry + " and c.qualifications like '%" + specialisation + "%'";
                }
                qry = qry + " order by b.employee_id";
                System.out.println("qry:" + qry);

                String ctxpath = request.getRequestURL().toString();
                System.out.println("The Context URL Path = " + ctxpath);
                map.put("reportquery", qry);
                map.put("ctxpath", ctxpath);
                path =
getServletContext().getRealPath("/WEB-INF/ReportSrc/Employee_Details_Report_QueryBased.jasper");
                jasperPrint = JasperFillManager.fillReport(path, map, con);
                System.out.println("Report is Created from Jasper Print...");
                OutputStream outuputStream = response.getOutputStream();
                JRExporter exporter = null;
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition",
                                   "attachment; filename=\"EmployeeDetails.pdf\"");
                exporter = new JRPdfExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT,
                                      jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
                                      outuputStream);
                exporter.exportReport();
                System.out.println("The File is Downloaded");
                outuputStream.close();
            } else if (command != null) {
                System.out.println("second path:====>" +
                                   getServletContext().getRealPath("/WEB-INF/ReportSrc/db_Retiirement_Employee_Profile1_SR_New.jasper"));
                reportFile =
                        new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/db_Retiirement_Employee_Profile1_SR_New.jasper"));
                if (!reportFile.exists())
                    throw new JRRuntimeException("File J not found. The report design must be compiled first.");
                else
                    System.out.println("file found");
                map = new HashMap();
                System.out.println("test1");
                int empid =
                    Integer.parseInt(request.getParameter("txtEmpId1"));
                System.out.println("empid :" + empid);
                map.put("emp_id", empid);
                path =
getServletContext().getRealPath("/WEB-INF/ReportSrc") + File.separator;
                System.out.println("path=" + path);
                map.put("SUBREPORT_DIR", path);
                System.out.println(JRLoader.loadObject(reportFile.getPath()));
                jasperReport =
                        (JasperReport)JRLoader.loadObject(reportFile.getPath());
                jasperPrint =
                        JasperFillManager.fillReport(jasperReport, map, con);
                System.out.println("The jasperprint is" + jasperPrint);
                byte buf[] =
                    JasperExportManager.exportReportToPdf(jasperPrint);
                response.setContentType("application/pdf");
                response.setContentLength(buf.length);
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"employee_details.pdf\"");
                OutputStream out = response.getOutputStream();
                out.write(buf, 0, buf.length);
                out.close();
            }
        } catch (Exception e) {
            System.out.println("Err in Reportquery:" + e.getMessage());
        }


    }
}
