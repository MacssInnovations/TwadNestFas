package Servlets.WQS.WQS1.WQTesting.Reports.servlets;

import Servlets.Security.classes.UserProfile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.Types;

import java.text.Format;
import java.text.SimpleDateFormat;

import java.util.Calendar;

import java.util.HashMap;
import java.util.Map;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

public class WQS_SampleResult_CustomerRep extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        PrintWriter out = response.getWriter();
        String CONTENT_TYPE = "text/xml";
        response.setContentType(CONTENT_TYPE);
        response.setHeader("cache-control", "no-cache");
        Connection con = null;
        Statement st = null;
        PreparedStatement ps = null, ps1 = null;
        ResultSet rs = null, rs1 = null;
        int lab = 0, ino = 0, cnt = 0;
        String xml, dname = null, off_name = null, cd = null, cyear =
            null, sd = null, syear = null, idt = null, iyear = null, amt =
            null, city = null;
        Date sdate = null, cdate = null, idate = null;
        String off_rno = null, cus_rno = null, inv_det = null, res =
            null, test_purpose = "";
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

//            ConnectionString =
//                    strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
//                    ":" + strsid.trim();
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
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
        try {
            ps =
  con.prepareStatement("select a.office_id,district_code," + "(case when district_code is null then (select office_name from com_mst_offices where office_id=a.office_id) else '-' end" +
                       ")as off_name," +
                       "(case when district_code is not null then (select district_name from com_mst_districts where district_code=b.district_code) else '-' end" +
                       ")as dist_name,city_town_name from" +
                       "(select office_id,designation_id from hrm_emp_current_posting where employee_id=?" +
                       ")a left outer join" +
                       "(select office_id,district_code,city_town_name from com_mst_offices" +
                       ")b on a.office_id=b.office_id");
            ps.setInt(1, eid);
            rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getString("dist_name").equalsIgnoreCase("-")) {
                    String distname[] = rs.getString("off_name").split(",");
                    System.out.println(distname.length);
                    System.out.println("dname from off_name:" +
                                       distname[distname.length - 1].trim());
                    dname = distname[distname.length - 1].trim();
                } else {
                    dname = rs.getString("dist_name");
                    System.out.println("dname from district name:" + dname);
                }
                city = rs.getString("city_town_name");
            }
            rs.close();
            ps.close();
            if (dname.equalsIgnoreCase("chennai")) {
                off_name = "CWA/TWAD Lab/HO";
            } else if (dname.equalsIgnoreCase("vellore") ||
                       dname.equalsIgnoreCase("Thanjavur") ||
                       dname.equalsIgnoreCase("coimbatore") ||
                       dname.equalsIgnoreCase("madurai")) {
                off_name = "AWA/TWAD Lab/" + city;
            } else
                off_name = "JWA/TWAD Lab/" + city;
            System.out.println(off_name);
        } catch (Exception e) {
            System.out.println("Err in eid selection:  " + e.getMessage());
        }
        System.out.println("welcome to servlet");
        xml = "<response>";
        String cmd = request.getParameter("command");
        System.out.println(cmd);
        if (cmd.equalsIgnoreCase("changeIno")) {
            lab = Integer.parseInt(request.getParameter("lab"));
            ino = Integer.parseInt(request.getParameter("invoice_no"));
            xml = xml + "<command>changeIno</command>";
            try {
                String sql =
                    "select * from(" + "select distinct b.customer_id,customer_type,b.customer_ref_no,c.name,b.invoice_amount,b.invoice_date,b.iyear,b.final_result,a1.cdate,extract(year from a1.cdate)as cyear from " +
                    "(select lab_code,invoice_number,process_flow_status_id from wqs_watersample_result " +
                    ")a left outer join " +
                    "(select distinct lab_code,invoice_number,min(date_of_collection)as cdate from wqs_sample_entry group by lab_code" +
                    ")a1 on a.lab_code=a1.lab_code and a.invoice_number=a1.invoice_number left outer join" +
                    "(select lab_code,invoice_number,customer_type,customer_id,customer_ref_no,invoice_amount,invoice_date,extract(year from invoice_date)as iyear,final_result from wqs_invoice_details" +
                    ")b on a.lab_code=b.lab_code and a.invoice_number=b.invoice_number left outer join" +
                    "(select lab_code,customer_id,name from wqs_customer" +
                    ")c on b.lab_code=c.lab_code and b.customer_id=c.customer_id " +
                    "where a.lab_code=? and a.invoice_number=? and process_flow_status_id='FR')," +
                    "(select now(),extract(year from now())as syear )";
                System.out.println("query:" + sql);
                ps = con.prepareStatement(sql);
                ps.setInt(1, lab);
                ps.setInt(2, ino);
                rs = ps.executeQuery();
                if (rs.next()) {
                    test_purpose = "";
                    String test_purpose_id = "";
                    xml =
 xml + "<cid>" + rs.getString("customer_id") + "</cid>";
                    xml = xml + "<cname>" + rs.getString("name") + "</cname>";
                    xml =
 xml + "<ctype>" + rs.getString("customer_type") + "</ctype>";
                    Format formatter = new SimpleDateFormat("dd.MM.yyyy");
                    idate = rs.getDate("invoice_date");
                    idt = formatter.format(idate);
                    iyear = rs.getString("iyear");
                    amt = rs.getString("invoice_amount");
                    cdate = rs.getDate("cdate");
                    cd = formatter.format(cdate);
                    cyear = rs.getString("cyear");
                    sdate = rs.getDate("now()");
                    sd = formatter.format(sdate);
                    syear = rs.getString("syear");
                    res = rs.getString("final_result");
                    off_rno =
                            "Lr.No. " + ino + "/" + off_name + "/" + syear + "/dt." +
                            sd;
                    //cus_rno="Your Lr.No. "+rs.getString("customer_ref_no")+"/"+off_name+"/"+cyear+"/dt."+cd;
                    cus_rno = "Your Lr.No. " + rs.getString("customer_ref_no");
                    inv_det =
                            "T.O.Invoice No. " + ino + "/dt. " + idt + " for Rs." +
                            amt + "/- only";
                    System.out.println("Office Ref No:" + off_rno);
                    System.out.println("Customer Ref No:" + cus_rno);
                    System.out.println("Invoice_details :" + inv_det);
                    xml = xml + "<rno>" + cus_rno + "</rno>";
                    xml = xml + "<off_rno>" + off_rno + "</off_rno>";
                    xml = xml + "<inv_det>" + inv_det + "</inv_det>";
                    if (res == null || res.equalsIgnoreCase(""))
                        res = "-";
                    xml = xml + "<final_result>" + res + "</final_result>";
                    sql =
 "select distinct a.test_purpose_id,b.test_purpose from wqs_watersample_result a left outer join wqs_test_purpose b on a.test_purpose_id=b.test_purpose_id where a.lab_code=? and a.invoice_number=? and process_flow_status_id='FR'";
                    ps1 = con.prepareStatement(sql);
                    ps1.setInt(1, lab);
                    ps1.setInt(2, ino);
                    rs1 = ps1.executeQuery();
                    while (rs1.next()) {
                        test_purpose += rs1.getString("test_purpose") + ",";
                        test_purpose_id +=
                                rs1.getString("test_purpose_id") + ",";
                    }
                    rs1.close();
                    test_purpose =
                            test_purpose.substring(0, test_purpose.length() -
                                                   1);
                    test_purpose_id =
                            test_purpose_id.substring(0, test_purpose_id.length() -
                                                      1);
                    xml =
 xml + "<test_purpose>" + test_purpose + "</test_purpose>";
                    xml =
 xml + "<test_purpose_id>" + test_purpose_id + "</test_purpose_id>";
                    System.out.println("test_purpose=========>" +
                                       test_purpose);
                    xml = xml + "<flag>Success</flag>";
                } else {
                    xml = xml + "<flag>Failure</flag>";
                }
                xml = xml + "</response>";
                System.out.println("xml is:" + xml);
                out.println(xml);
            } catch (Exception e) {
                System.out.println("Err in commit:" + e.getMessage());
            }
        } else if (cmd.equalsIgnoreCase("sample")) {
            CONTENT_TYPE = "text/html";
            System.out.println("inside sample");
            lab = Integer.parseInt(request.getParameter("lab_code"));
            ino = Integer.parseInt(request.getParameter("ino"));
            test_purpose = request.getParameter("test_purpose");
            System.out.println("Lab_code=" + lab + "  ino=" + ino);
            String html = "";
            try {
                ps =
  con.prepareStatement("Select distinct sample_number from wqs_watersample_result where lab_code=? and invoice_number=? and test_purpose_id=? and process_flow_status_id=? order by sample_number");
                ps.setInt(1, lab);
                ps.setInt(2, ino);
                ps.setString(3, test_purpose);
                ps.setString(4, "FR");
                rs = ps.executeQuery();
                int count = 0;
                html =
"<table cellpadding='1%' cellspacing='1%' border='1' width='100%'>";
                boolean bool = false;
                while (rs.next()) {
                    String val = rs.getString("sample_number");
                    System.out.println("value :" + val);
                    if (bool = !bool) {
                        html =
html + "<tr bgcolor='pink'><td><input type='checkbox' name='chkelement' id='chkelement' value='" +
 val + "'></input></td>";
                        html = html + "<td>" + val + "</td></tr>";
                    } else {
                        html =
html + "<tr ><td><input type='checkbox' name='chkelement' id='chkelement' value='" +
 val + "'></input></td>";
                        html = html + "<td>" + val + "</td></tr>";
                    }
                    count++;
                }
                if (count == 0) {
                    html = "There is no Sample Number";
                }
                html = html + "</table>";
                System.out.println("html:" + html);
                out.println(html);
            } catch (Exception e) {
                System.out.println("Sample selection error " + e);
                System.out.println("html:" + html);
            }
        } else if (cmd.equalsIgnoreCase("addResult")) {
            lab = Integer.parseInt(request.getParameter("lab_code"));
            ino = Integer.parseInt(request.getParameter("ino"));
            System.out.println("Lab_code=" + lab + "  ino=" + ino);
            String sno[] = request.getParameter("sample_number").split(",");
            res = "";
            xml = xml + "<command>addResult</command>";
            try {
                for (int i = 0; i < sno.length; i++) {
                    System.out.println("sample number:" + sno[i]);
                    ps =
  con.prepareStatement("select reason from wqs_sample_entry where lab_code=? and invoice_number=? and sample_number=?");
                    ps.setInt(1, lab);
                    ps.setInt(2, ino);
                    ps.setInt(3, Integer.parseInt(sno[i]));
                    rs = ps.executeQuery();
                    if (rs.next()) {
                        if (rs.getString("reason") != null) {
                            res = res + " " + rs.getString("reason");
                        }
                    }
                }
                if (res.equalsIgnoreCase(" ") || res.equalsIgnoreCase(""))
                    res = "-";
                xml = xml + "<result>" + res + "</result>";
                xml = xml + "<flag>success</flag>";
            } catch (Exception e) {
                System.out.println("Err in addResult:" + e.getMessage());
                xml = xml + "<flag>exception</flag>";
                xml = xml + "<message>" + e.getMessage() + "</message>";
            }
            xml = xml + "</response>";
            System.out.println("xml is:" + xml);
            out.println(xml);
        } else if (cmd.equalsIgnoreCase("load_invoice")) {
            int labcode = Integer.parseInt(request.getParameter("lab"));
            xml = xml + "<command>load_invoice</command>";
            try {
                String sql =
                    "select a.invoice_number,b.invoice_date,b.invoice_amount,b.customer_type from" +
                    "(select distinct lab_code,invoice_number,process_flow_status_id from wqs_watersample_result" +
                    ")a left outer join" +
                    "(select lab_code,invoice_number,invoice_date,invoice_amount,customer_type from wqs_invoice_details" +
                    ")b on a.lab_code=b.lab_code and a.invoice_number=b.invoice_number where a.lab_code=? and a.process_flow_status_id=? order by a.invoice_number";
                System.out.println(sql);
                ps = con.prepareStatement(sql);
                ps.setInt(1, labcode);
                ps.setString(2, "FR");
                rs = ps.executeQuery();
                while (rs.next()) {
                    test_purpose = "";
                    xml = xml + "<count>";
                    ino = rs.getInt("invoice_number");
                    xml =
 xml + "<ino>" + rs.getString("invoice_number") + "</ino>";
                    System.out.println("invoice number=========>" +
                                       rs.getString("invoice_number"));
                    Date fdate = rs.getDate("invoice_date");
                    Format formatter;
                    formatter = new SimpleDateFormat("dd/MM/yyyy");
                    String f = formatter.format(fdate);
                    System.out.println("invoice date=========>" +
                                       rs.getString("invoice_date"));
                    xml = xml + "<idate>" + f + "</idate>";
                    xml =
 xml + "<amt>" + rs.getString("invoice_amount") + "</amt>";
                    System.out.println("invoice amount=========>" +
                                       rs.getString("invoice_amount"));
                    xml =
 xml + "<ctype>" + rs.getString("customer_type") + "</ctype>";
                    System.out.println("customer type=========>" +
                                       rs.getString("customer_type"));
                    sql =
 "select distinct a.test_purpose_id,b.test_purpose from wqs_watersample_result a left outer join wqs_test_purpose b on a.test_purpose_id=b.test_purpose_id where a.lab_code=? and a.invoice_number=? and process_flow_status_id='FR'";
                    ps1 = con.prepareStatement(sql);
                    ps1.setInt(1, labcode);
                    ps1.setInt(2, ino);
                    rs1 = ps1.executeQuery();
                    while (rs1.next()) {
                        test_purpose += rs1.getString("test_purpose") + ",";
                    }
                    rs1.close();
                    test_purpose =
                            test_purpose.substring(0, test_purpose.length() -
                                                   1);
                    xml =
 xml + "<test_purpose>" + test_purpose + "</test_purpose>";
                    System.out.println("test_purpose=========>" +
                                       test_purpose);
                    xml = xml + "</count>";
                }
                xml = xml + "<flag>success</flag>";
            } catch (SQLException e) {
                xml = xml + "<flag>failure</flag>";
                System.out.println(e.getMessage());
            }
            xml = xml + "</response>";
            System.out.println("xml is:" + xml);
            out.println(xml);
        }
        out.close();
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        response.setContentType(CONTENT_TYPE);
        System.out.println("Welcome to servlet");
        Connection con = null;
        Statement st = null, st1 = null;
        ResultSet rs = null, rs1 = null;
        PreparedStatement ps = null, ps1 = null, ps2 = null;
        JasperPrint jasperPrint = null;
        int lab = 0, snum = 0, ino = 0, rowcount = 0, eid = 0, dcode = 0;
        File reportFile = null;
        String cust_address[] = null;
        String param_result = null;
        Map map = null;
        map = new HashMap();
        String off_rno = null, det = null, cus_rno = null, dname =
            null, customer_name = null, parameter = null, result =
            null, result_in_percentage = null, minval = null, maxval = null;
        String ename = null, qual = null, design = null, dept = null, oadd =
            null, town = null, pcode = null, tadd = null, customer_ref =
            null, sadd = null, prefix = null, design1 = null, res =
            null, page_size = null, sadd1 = null;
        String header = "", designation = "";
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

//            ConnectionString =
//                    strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
//                    ":" + strsid.trim();
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
            Class.forName(strDriver.trim());
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
            try {
                st = con.createStatement();
                st1 = con.createStatement();
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
        System.out.println("user id::" + empProfile.getEmployeeId());
        int eid1 = empProfile.getEmployeeId();
        lab = Integer.parseInt(request.getParameter("lab_code"));
        try {
            ps =
  con.prepareStatement("select a.office_id,district_code," + "(case when district_code is null then (select office_name from com_mst_offices where office_id=a.office_id) else '-' end" +
                       ")as off_name," +
                       "(case when district_code is not null then (select district_name from com_mst_districts where district_code=b.district_code) else '-' end" +
                       ")as dist_name from" +
                       "(select office_id,designation_id from hrm_emp_current_posting where employee_id=?" +
                       ")a left outer join" +
                       "(select office_id,district_code from com_mst_offices" +
                       ")b on a.office_id=b.office_id");
            ps.setInt(1, eid1);
            rs = ps.executeQuery();
            while (rs.next()) {
                dcode = rs.getInt("district_code");
                if (rs.getString("dist_name").equalsIgnoreCase("-")) {
                    String distname[] = rs.getString("off_name").split(",");
                    System.out.println(distname.length);
                    System.out.println("dname from off_name:" +
                                       distname[distname.length - 1].trim());
                    dname = distname[distname.length - 1].trim();
                } else {
                    dname = rs.getString("dist_name");
                    System.out.println("dname from district name:" + dname);
                }
            }
            rs.close();
            ps.close();
            System.out.println("District :" + dname);
            if (dname.equalsIgnoreCase("chennai")) {
                ps =
  con.prepareStatement("select employee_id from" + "(select designation_id from hrm_mst_designations where designation_short_name='CWA'" +
                       ")a left outer join" +
                       "(select employee_id,office_id,designation_id,employee_status_id,department_id from hrm_emp_current_posting" +
                       ")b on a.designation_id=b.designation_id " +
                       "where b.office_id=? and b.employee_status_id='WKG' and b.department_id='TWAD'");
                ps.setInt(1, lab);
                rs = ps.executeQuery();
                if (rs.next()) {
                    eid = rs.getInt("employee_id");
                } else
                    eid = eid1;
            } else if (dname.equalsIgnoreCase("vellore") ||
                       dname.equalsIgnoreCase("Thanjavur") ||
                       dname.equalsIgnoreCase("coimbatore") ||
                       dname.equalsIgnoreCase("madurai")) {
                ps =
  con.prepareStatement("select * from" + "(select office_id,district_code from com_mst_offices " +
                       ")a inner join" +
                       "(select employee_id,office_id,designation_id,department_id,employee_status_id from hrm_emp_current_posting " +
                       ")b on a.office_id=b.office_id inner join" +
                       "(select designation_id,designation_short_name from hrm_mst_designations" +
                       ")c on b.designation_id=c.designation_id inner join" +
                       "(select employee_name,employee_id from hrm_mst_employees" +
                       ")c on b.employee_id=c.employee_id where district_code=? and department_id='TWAD' and employee_status_id='WKG' and designation_short_name='AWA'");
                ps.setInt(1, dcode);
                rs = ps.executeQuery();
                if (rs.next()) {
                    eid = rs.getInt("employee_id");
                } else
                    eid = eid1;
            } else {
                ps =
  con.prepareStatement("select employee_id from" + "(select designation_id from hrm_mst_designations where designation_short_name='JWA'" +
                       ")a left outer join" +
                       "(select employee_id,office_id,designation_id,employee_status_id,department_id from hrm_emp_current_posting" +
                       ")b on a.designation_id=b.designation_id " +
                       "where b.office_id=? and b.employee_status_id='WKG' and b.department_id='TWAD'");
                ps.setInt(1, lab);
                rs = ps.executeQuery();
                if (rs.next()) {
                    eid = rs.getInt("employee_id");
                } else
                    eid = eid1;
            }
        } catch (Exception e) {
            System.out.println("Err in eid selection:  " + e.getMessage());
        }

        String path =
            getServletContext().getRealPath("/images") + File.separator;
        // String path1 = getServletContext().getRealPath("/WEB-INF/ReportSrc") + File.separator;
        System.out.println("Path================" + path);


        ino = Integer.parseInt(request.getParameter("invoice_num"));
        String test_purpose = request.getParameter("test_purpose");
        String cid = request.getParameter("cid");
        String ctype = request.getParameter("ctype");
        String sample_number = request.getParameter("sno");
        String sno[] = sample_number.split(",");
        System.out.println("Sample no:" + sno[0]);
        System.out.println("Lab ====>" + lab);
        System.out.println("ino ====>" + ino);
        System.out.println("ctype ====>" + ctype);
        System.out.println(sno.length);

        off_rno = request.getParameter("off_rno");
        System.out.println("office rno===============>" + off_rno);
        det = request.getParameter("det");
        cus_rno = request.getParameter("rno");
        System.out.println("office rno===============>" + cus_rno);
        res = request.getParameter("result");
        String final_result = request.getParameter("final_result");
        page_size = request.getParameter("psize");
        String pre_printed = request.getParameter("pre_printed");
        String location_type = null, algae = "";
        String pname[] = null, pname1[] = null;
        if (ctype.equalsIgnoreCase("Twad")) {
            try {
                ps =
  con.prepareStatement("select location_type from wqs_sample_entry where lab_code=? and invoice_number=?");
                ps.setInt(1, lab);
                ps.setInt(2, ino);
                rs = ps.executeQuery();
                if (rs.next()) {
                    location_type = rs.getString("location_type");
                }
                if (location_type.equalsIgnoreCase("VP")) {
                    if (test_purpose.equalsIgnoreCase("LIME") ||
                        test_purpose.equalsIgnoreCase("ALUM") ||
                        test_purpose.equalsIgnoreCase("BP") ||
                        test_purpose.equalsIgnoreCase("PAC") ||
                        test_purpose.equalsIgnoreCase("NADCC") ||
                        test_purpose.equalsIgnoreCase("SOLID")) {
                        pname =
new String[] { "Date of Collection", "Date of Receipt", "District",
               "Location" };
                        pname1 =
                                new String[] { "date_of_collection", "date_of_receipt",
                                               "district_code", "location" };
                    } else {
                        pname =
new String[] { "Date of Collection", "Date of Receipt", "District", "Block",
               "Panchayat", "Habitation", "Scheme", "Source", "Location" };
                        pname1 =
                                new String[] { "date_of_collection", "date_of_receipt",
                                               "district_code", "block_code",
                                               "panchayat_code",
                                               "habitation_code",
                                               "scheme_type", "source_type",
                                               "location" };
                    }
                } else {
                    if (test_purpose.equalsIgnoreCase("LIME") ||
                        test_purpose.equalsIgnoreCase("ALUM") ||
                        test_purpose.equalsIgnoreCase("BP") ||
                        test_purpose.equalsIgnoreCase("PAC") ||
                        test_purpose.equalsIgnoreCase("NADCC") ||
                        test_purpose.equalsIgnoreCase("SOLID")) {
                        pname =
new String[] { "Date of Collection", "Date of Receipt", "District",
               "Location" };
                        pname1 =
                                new String[] { "date_of_collection", "date_of_receipt",
                                               "district_code", "location" };
                    } else {
                        pname =
new String[] { "Date of Collection", "Date of Receipt", "District",
               "Location_type", "Local Body", "Scheme", "Source", "Location" };
                        pname1 =
                                new String[] { "date_of_collection", "date_of_receipt",
                                               "district_code",
                                               "location_type",
                                               "local_body_code",
                                               "scheme_type", "source_type",
                                               "location" };
                    }
                }
                ps.close();
                //con.close();
                rs.close();
            } catch (Exception e) {
                System.out.println("Err in set parameter:" + e.getMessage());
            }
        } else {
            if (test_purpose.equalsIgnoreCase("LIME") ||
                test_purpose.equalsIgnoreCase("ALUM") ||
                test_purpose.equalsIgnoreCase("BP") ||
                test_purpose.equalsIgnoreCase("PAC") ||
                test_purpose.equalsIgnoreCase("NADCC") ||
                test_purpose.equalsIgnoreCase("SOLID")) {
                pname =
new String[] { "Date of Collection", "Date of Receipt", "District",
               "Location" };
                pname1 =
                        new String[] { "date_of_collection", "date_of_receipt",
                                       "district_code", "location" };
            } else {
                pname =
new String[] { "Date of Collection", "Date of Receipt", "District", "Source",
               "Location" };
                pname1 =
                        new String[] { "date_of_collection", "date_of_receipt",
                                       "district_code", "source_type",
                                       "location" };
            }
        }
        try {
            st.executeUpdate("delete from wqs_customer_sample_main");
            st1.executeUpdate("delete from wqs_customer_sample_sub");
        } catch (Exception e) {
            System.out.println("Err in delete:" + e.getMessage());
        }
        String fval = null;
        try {
            for (int i = 0; i < sno.length; i++) {
                snum = 0;
                for (int j = 0; j < pname1.length; j++) {
                    fval = "";
                    if (pname1[j].equalsIgnoreCase("date_of_receipt")) {
                        ps =
  con.prepareStatement("select distinct date_of_receipt from wqs_watersample_result where lab_code=? and invoice_number=? and sample_number=? and test_purpose_id=? and process_flow_status_id=?");
                        ps.setInt(1, lab);
                        ps.setInt(2, ino);
                        ps.setInt(3, Integer.parseInt(sno[i]));
                        ps.setString(4, test_purpose);
                        ps.setString(5, "FR");
                        rs = ps.executeQuery();
                        if (rs.next()) {
                            Date f = rs.getDate("date_of_receipt");
                            Format formatter =
                                new SimpleDateFormat("dd/MM/yyyy");
                            fval = formatter.format(f);
                        }
                    } else if (pname1[j].equalsIgnoreCase("date_of_collection")) {
                        ps =
  con.prepareStatement("select date_of_collection from wqs_sample_entry where lab_code=? and invoice_number=? and sample_number=? and test_purpose_id=?");
                        ps.setInt(1, lab);
                        ps.setInt(2, ino);
                        ps.setInt(3, Integer.parseInt(sno[i]));
                        ps.setString(4, test_purpose);
                        rs = ps.executeQuery();
                        if (rs.next()) {
                            Date f = rs.getDate("date_of_collection");
                            Format formatter =
                                new SimpleDateFormat("dd/MM/yyyy");
                            fval = formatter.format(f);
                        }
                    } else if (pname1[j].equalsIgnoreCase("district_code") ||
                               pname1[j].equalsIgnoreCase("block_code") ||
                               pname1[j].equalsIgnoreCase("panchayat_code") ||
                               pname1[j].equalsIgnoreCase("habitation_code") ||
                               pname1[j].equalsIgnoreCase("location_type") ||
                               pname1[j].equalsIgnoreCase("local_body_code")) {
                        System.out.println("location:" + pname1[j]);
                        String sql =
                            "select fieldval," + "(case when '" + pname1[j] +
                            "'='district_code' then (select district_name from com_mst_districts where district_code=a.fieldval)" +
                            "when '" + pname1[j] +
                            "'='location_type' then location_type " +
                            "when '" + pname1[j] +
                            "'='local_body_code' then " +
                            "(case when location_type='Corporation' then (select corporation_eng from pms_corporation where district_code=a.district_code and corp_code=fieldval)" +
                            "when location_type='Municipality' then (select municipality_eng from pms_mst_municipality where district_code=a.district_code and mucode=fieldval)" +
                            "when location_type='UTP' then (select tpname from pms_mst_town_panchayats where dcode=a.district_code and type='U' and tpcode=fieldval)" +
                            "else (select tpname from pms_mst_town_panchayats where dcode=a.district_code and type='R' and tpcode=fieldval)end" +
                            ")" + "when '" + pname1[j] +
                            "'='block_code' then (select blockname from com_mst_blocks where district_code=a.district_code and block_code=fieldval)" +
                            "when '" + pname1[j] +
                            "'='panchayat_code' then (select panchayatname from com_mst_panchayats where district_code=a.district_code and block_code=a.block_code and panch_code=fieldval)" +
                            "else (select hname from com_mst_habitations where district_code=a.district_code and block_code=a.block_code and panch_code=a.panchayat_code and hab_code=fieldval) end" +
                            ")as typeval from" + "(select " + pname1[j] +
                            " as fieldval,district_code,location_type,local_body_code,block_code,panchayat_code,habitation_code from wqs_sample_entry where lab_code=? and invoice_number=? and sample_number=? and test_purpose_id=?)a";
                        System.out.println("**************sql:" + sql);
                        ps = con.prepareStatement(sql);
                        ps.setInt(1, lab);
                        ps.setInt(2, ino);
                        ps.setInt(3, Integer.parseInt(sno[i]));
                        ps.setString(4, test_purpose);
                        rs = ps.executeQuery();
                        if (rs.next()) {
                            fval = rs.getString("typeval");
                        }
                    } else {
                        System.out.println("select fieldval,(case when " +
                                           pname1[j] +
                                           "='scheme_type' then (select scheme_type_name from rws_mst_scheme_types where scheme_type_id=fieldval) when " +
                                           pname1[j] +
                                           "='source_type' then(select water_source_type from rws_mst_water_source_type where water_source_type_id=fieldval) else fieldval end)as typeval from(select " +
                                           pname1[j] +
                                           " as fieldval from wqs_sample_entry where lab_code=" +
                                           lab + " and invoice_number=" + ino +
                                           " and sample_number=" +
                                           Integer.parseInt(sno[i]) +
                                           " and test_purpose_id='" +
                                           test_purpose + "')");
                        ps =
  con.prepareStatement("select fieldval,(case when '" + pname1[j] +
                       "'='scheme_type' then (select scheme_type_name from rws_mst_scheme_types where scheme_type_id=fieldval) when '" +
                       pname1[j] +
                       "'='source_type' then(select water_source_type from rws_mst_water_source_type where water_source_type_id=fieldval) else fieldval end)as typeval from(select " +
                       pname1[j] +
                       " as fieldval from wqs_sample_entry where lab_code=? and invoice_number=? and sample_number=? and test_purpose_id=?)");
                        ps.setInt(1, lab);
                        ps.setInt(2, ino);
                        ps.setInt(3, Integer.parseInt(sno[i]));
                        ps.setString(4, test_purpose);
                        rs = ps.executeQuery();
                        if (rs.next()) {
                            fval = rs.getString("typeval");
                        }
                    }
                    snum++;
                    ps1 =
 con.prepareStatement("insert into wqs_customer_sample_sub(lab_code,invoice_number,sample_number,field_name,field_value,sno)values(?,?,?,?,?,?)");
                    ps1.setInt(1, lab);
                    ps1.setInt(2, ino);
                    ps1.setInt(3, Integer.parseInt(sno[i]));
                    ps1.setString(4, pname[j]);
                    ps1.setString(5, fval);
                    ps1.setInt(6, snum);
                    int r = ps1.executeUpdate();
                    if (r > 0)
                        System.out.println("row " + snum +
                                           " inserted successfully");
                    else
                        System.out.println("err in insert row===>" + snum);
                }
            }
        } catch (Exception e) {
            System.out.println("Err in first table :" + e.getMessage());
        }
        String tname[] =
        { "PHYSICAL EXAMINATION", "CHEMICAL EXAMINATION", "BATERIOLOGICAL EXAMINATION" };
        String tname1[] = { "Physical", "Chemical", "Bacteriological" };
        try {
            if (test_purpose.equalsIgnoreCase("DRI") ||
                test_purpose.equalsIgnoreCase("SEW") ||
                test_purpose.equalsIgnoreCase("EFF")) {
                for (int i = 0; i < sno.length; i++) {
                    snum = 0;
                    for (int j = 0; j < tname1.length; j++) {
                        rowcount = 0;
                        String sql1 =
                            "select a.lab_code,a.invoice_number,a.sample_number,a.parameter,b.description,a.result,result_in_percentage,b.std_nonstd,b.sno,b.test_result," +
                            "(case when a.test_purpose_id!='SEW' then" +
                            "    (case when std_nonstd='S' then(select desirable_value from wqs_std_result where element_symbol=parameter) " +
                            "          else (select desirable_value from wqs_nonstd_result where element_symbol=parameter)end" +
                            "     )" + "     else '' end" + " )as min_val," +
                            " (case when a.test_purpose_id!='Sewage' then" +
                            "       (case when std_nonstd='S' then(select maximum_value from wqs_std_result where element_symbol=parameter) " +
                            "             else (select maximum_value from wqs_nonstd_result where element_symbol=parameter)end" +
                            "       )" + "       else '' end" +
                            "  )as max_val from" +
                            " (select lab_code,invoice_number,sample_number,test_purpose_id,parameter,result,result_in_percentage,process_flow_status_id from wqs_watersample_result" +
                            " )a left outer join" +
                            " (select test_purpose_id,sno,element_symbol,description,test_result,std_nonstd from wqs_element_symbol " +
                            " )b on a.parameter=b.element_symbol " +
                            " where a.lab_code=? and a.invoice_number=? and a.sample_number=? and a.test_purpose_id=? and a.process_flow_status_id=? and b.test_purpose_id=(case when a.test_purpose_id='SEW' then 'DRI' when a.test_purpose_id='EFF' then 'DRI' else a.test_purpose_id end) and b.test_result=? order by b.sno";
                        System.out.println(sql1);
                        ps1 = con.prepareStatement(sql1);
                        ps1.setInt(1, lab);
                        ps1.setInt(2, ino);
                        ps1.setInt(3, Integer.parseInt(sno[i]));
                        ps1.setString(4, test_purpose);
                        ps1.setString(5, "FR");
                        ps1.setString(6, tname1[j]);
                        rs1 = ps1.executeQuery();
                        while (rs1.next()) {
                            rowcount++;
                            if (rowcount == 1) {
                                snum++;
                                ps =
  con.prepareStatement("insert into wqs_customer_sample_main(lab_code,invoice_number,sample_number,parameter,sno)values(?,?,?,?,?)");
                                ps.setInt(1, lab);
                                ps.setInt(2, ino);
                                ps.setInt(3, Integer.parseInt(sno[i]));
                                ps.setString(4, tname[j]);
                                ps.setInt(5, snum);
                                ps.executeUpdate();
                                System.out.println("----------1 st row parameter insertion-------");
                            }
                            int s = Integer.parseInt(rs1.getString("sno"));
                            System.out.println("Sno===>" + s);
                            snum = s + 2;
                            parameter = rs1.getString("description");
                            minval = rs1.getString("min_val");
                            maxval = rs1.getString("max_val");
                            result = rs1.getString("result");
                            result_in_percentage =
                                    rs1.getString("result_in_percentage");

                            System.out.println(parameter);
                            ps2 =
 con.prepareStatement("insert into wqs_customer_sample_main(lab_code,invoice_number,sample_number,parameter,min_val,max_val,result,result_in_percentage,sno)values(?,?,?,?,?,?,?,?,?)");
                            ps2.setInt(1, lab);
                            ps2.setInt(2, ino);
                            ps2.setInt(3, Integer.parseInt(sno[i]));
                            ps2.setString(4, parameter);
                            ps2.setString(5, minval);
                            ps2.setString(6, maxval);
                            ps2.setString(7, result);
                            ps2.setString(8, result_in_percentage);
                            ps2.setInt(9, snum);
                            ps2.executeUpdate();
                            ps2.close();
                        }
                    }
                }
            } else if (test_purpose.equalsIgnoreCase("CON")) {
                for (int i = 0; i < sno.length; i++) {
                    snum = 0;
                    for (int j = 0; j < tname1.length; j++) {
                        rowcount = 0;
                        String sql1 =
                            "select a.lab_code,a.invoice_number,a.sample_number,a.parameter,description,result,result_in_percentage,permissible_limit,test_result,sno from \n" +
                            "wqs_watersample_result a left outer join \n" +
                            "wqs_element_symbol b on a.test_purpose_id=b.test_purpose_id and a.parameter=b.element_symbol left outer join \n" +
                            "wqs_construction_standards c on b.element_symbol=c.element_symbol \n" +
                            "where a.lab_code=? and a.invoice_number=? and a.sample_number=? and a.test_purpose_id=? and a.process_flow_status_id=? and test_result=? \n" +
                            "order by a.invoice_number,a.sample_number,b.sno";
                        System.out.println(sql1);
                        ps1 = con.prepareStatement(sql1);
                        ps1.setInt(1, lab);
                        ps1.setInt(2, ino);
                        ps1.setInt(3, Integer.parseInt(sno[i]));
                        ps1.setString(4, test_purpose);
                        ps1.setString(5, "FR");
                        ps1.setString(6, tname1[j]);
                        rs1 = ps1.executeQuery();
                        while (rs1.next()) {
                            rowcount++;
                            if (rowcount == 1) {
                                snum++;
                                ps =
  con.prepareStatement("insert into wqs_customer_sample_main(lab_code,invoice_number,sample_number,parameter,sno)values(?,?,?,?,?)");
                                ps.setInt(1, lab);
                                ps.setInt(2, ino);
                                ps.setInt(3, Integer.parseInt(sno[i]));
                                ps.setString(4, tname[j]);
                                ps.setInt(5, snum);
                                ps.executeUpdate();
                                System.out.println("----------1 st row parameter insertion-------");
                            }
                            int s = Integer.parseInt(rs1.getString("sno"));
                            System.out.println("Sno===>" + s);
                            snum = s + 2;
                            parameter = rs1.getString("description");
                            minval = rs1.getString("permissible_limit");
                            result = rs1.getString("result");
                            result_in_percentage =
                                    rs1.getString("result_in_percentage");
                            snum = Integer.parseInt(rs1.getString("sno"));
                            ps2 =
 con.prepareStatement("insert into wqs_customer_sample_main(lab_code,invoice_number,sample_number,parameter,min_val,result,result_in_percentage,sno)values(?,?,?,?,?,?,?,?)");
                            ps2.setInt(1, lab);
                            ps2.setInt(2, ino);
                            ps2.setInt(3, Integer.parseInt(sno[i]));
                            ps2.setString(4, parameter);
                            ps2.setString(5, minval);
                            ps2.setString(6, result);
                            ps2.setString(7, result_in_percentage);
                            ps2.setInt(8, snum);
                            ps2.executeUpdate();
                            ps2.close();
                        }
                    }
                }
            } else if (test_purpose.equalsIgnoreCase("PAC") ||
                       test_purpose.equalsIgnoreCase("SOLID")) {
                for (int i = 0; i < sno.length; i++) {
                    snum = 0;
                    String sql1 =
                        "select a.lab_code,a.invoice_number,a.sample_number,a.parameter,description,result,result_in_percentage,sno from \n" +
                        "wqs_watersample_result a left outer join \n" +
                        "wqs_element_symbol b on a.test_purpose_id=b.test_purpose_id and a.parameter=b.element_symbol \n" +
                        "where a.lab_code=? and a.invoice_number=? and a.sample_number=? and a.test_purpose_id=? and a.process_flow_status_id=? \n" +
                        "order by a.invoice_number,a.sample_number,b.sno";
                    System.out.println(sql1);
                    ps1 = con.prepareStatement(sql1);
                    ps1.setInt(1, lab);
                    ps1.setInt(2, ino);
                    ps1.setInt(3, Integer.parseInt(sno[i]));
                    ps1.setString(4, test_purpose);
                    ps1.setString(5, "FR");
                    rs1 = ps1.executeQuery();
                    while (rs1.next()) {
                        snum++;
                        parameter = rs1.getString("description");
                        result = rs1.getString("result");
                        result_in_percentage =
                                rs1.getString("result_in_percentage");
                        ps2 =
 con.prepareStatement("insert into wqs_customer_sample_main(lab_code,invoice_number,sample_number,parameter,result,result_in_percentage,sno)values(?,?,?,?,?,?,?)");
                        ps2.setInt(1, lab);
                        ps2.setInt(2, ino);
                        ps2.setInt(3, Integer.parseInt(sno[i]));
                        ps2.setString(4, parameter);
                        ps2.setString(5, result);
                        ps2.setString(6, result_in_percentage);
                        ps2.setInt(7, snum);
                        ps2.executeUpdate();
                        ps2.close();
                    }
                }
            } else if (test_purpose.equalsIgnoreCase("ALUM")) {
                for (int i = 0; i < sno.length; i++) {
                    snum = 0;
                    String sql1 =
                        "select a.lab_code,a.invoice_number,a.sample_number,a.parameter,description,result,result_in_percentage,grade1,grade2,grade3,sno from \n" +
                        "wqs_watersample_result a left outer join \n" +
                        "wqs_element_symbol b on a.test_purpose_id=b.test_purpose_id and a.parameter=b.element_symbol  left outer join \n" +
                        "wqs_alum_standards c on a.parameter=c.element_symbol \n" +
                        "where a.lab_code=? and a.invoice_number=? and a.sample_number=? and a.test_purpose_id=? and a.process_flow_status_id=? \n" +
                        "order by a.invoice_number,a.sample_number,b.sno";
                    System.out.println(sql1);
                    ps1 = con.prepareStatement(sql1);
                    ps1.setInt(1, lab);
                    ps1.setInt(2, ino);
                    ps1.setInt(3, Integer.parseInt(sno[i]));
                    ps1.setString(4, test_purpose);
                    ps1.setString(5, "FR");
                    rs1 = ps1.executeQuery();
                    while (rs1.next()) {
                        snum++;
                        parameter = rs1.getString("description");
                        result = rs1.getString("result");
                        result_in_percentage =
                                rs1.getString("result_in_percentage");
                        String grade1 = rs1.getString("grade1");
                        String grade2 = rs1.getString("grade2");
                        String grade3 = rs1.getString("grade3");
                        ps2 =
 con.prepareStatement("insert into wqs_customer_sample_main(lab_code,invoice_number,sample_number,parameter,grade1,grade2,grade3,result,result_in_percentage,sno)values(?,?,?,?,?,?,?,?,?,?)");
                        ps2.setInt(1, lab);
                        ps2.setInt(2, ino);
                        ps2.setInt(3, Integer.parseInt(sno[i]));
                        ps2.setString(4, parameter);
                        ps2.setString(5, grade1);
                        ps2.setString(6, grade2);
                        ps2.setString(7, grade3);
                        ps2.setString(8, result);
                        ps2.setString(9, result_in_percentage);
                        ps2.setInt(10, snum);
                        ps2.executeUpdate();
                        ps2.close();
                    }
                }
            } else if (test_purpose.equalsIgnoreCase("LIME")) {
                for (int i = 0; i < sno.length; i++) {
                    snum = 0;
                    String sql1 =
                        "select a.lab_code,a.invoice_number,a.sample_number,a.parameter,description,result,result_in_percentage,grade1,grade2,grade3,sno from \n" +
                        "wqs_watersample_result a left outer join \n" +
                        "wqs_element_symbol b on a.test_purpose_id=b.test_purpose_id and a.parameter=b.element_symbol  left outer join \n" +
                        "wqs_alum_standards c on a.parameter=c.element_symbol \n" +
                        "where a.lab_code=? and a.invoice_number=? and a.sample_number=? and a.test_purpose_id=? and a.process_flow_status_id=? \n" +
                        "order by a.invoice_number,a.sample_number,b.sno";
                    System.out.println(sql1);
                    ps1 = con.prepareStatement(sql1);
                    ps1.setInt(1, lab);
                    ps1.setInt(2, ino);
                    ps1.setInt(3, Integer.parseInt(sno[i]));
                    ps1.setString(4, test_purpose);
                    ps1.setString(5, "FR");
                    rs1 = ps1.executeQuery();
                    while (rs1.next()) {
                        snum++;
                        parameter = rs1.getString("description");
                        result = rs1.getString("result");
                        result_in_percentage =
                                rs1.getString("result_in_percentage");
                        String grade1 = rs1.getString("grade1");
                        String grade2 = rs1.getString("grade2");
                        String grade3 = rs1.getString("grade3");
                        ps2 =
 con.prepareStatement("insert into wqs_customer_sample_main(lab_code,invoice_number,sample_number,parameter,grade1,grade2,grade3,result,result_in_percentage,sno)values(?,?,?,?,?,?,?,?,?,?)");
                        ps2.setInt(1, lab);
                        ps2.setInt(2, ino);
                        ps2.setInt(3, Integer.parseInt(sno[i]));
                        ps2.setString(4, parameter);
                        ps2.setString(5, grade1);
                        ps2.setString(6, grade2);
                        ps2.setString(7, grade3);
                        ps2.setString(8, result);
                        ps2.setString(9, result_in_percentage);
                        ps2.setInt(10, snum);
                        ps2.executeUpdate();
                        ps2.close();
                    }
                }
            } else if (test_purpose.equalsIgnoreCase("BIO")) {
                for (int i = 0; i < sno.length; i++) {
                    snum = 0;
                    algae = "";
                    String sql1 =
                        "select a.lab_code,a.invoice_number,a.sample_number,a.parameter,description,result,result_in_percentage,sno from \n" +
                        "wqs_watersample_result a left outer join \n" +
                        "wqs_element_symbol b on a.test_purpose_id=b.test_purpose_id and a.parameter=b.element_symbol  \n" +
                        "where a.lab_code=? and a.invoice_number=? and a.sample_number=? and a.test_purpose_id=? and a.process_flow_status_id=? and result is not null \n" +
                        "order by a.invoice_number,a.sample_number,b.sno";
                    System.out.println(sql1);
                    ps1 = con.prepareStatement(sql1);
                    ps1.setInt(1, lab);
                    ps1.setInt(2, ino);
                    ps1.setInt(3, Integer.parseInt(sno[i]));
                    ps1.setString(4, test_purpose);
                    ps1.setString(5, "FR");
                    rs1 = ps1.executeQuery();
                    while (rs1.next()) {
                        snum++;
                        parameter = rs1.getString("description");
                        result = rs1.getString("result");
                        result_in_percentage =
                                rs1.getString("result_in_percentage");
                        ps2 =
 con.prepareStatement("insert into wqs_customer_sample_main(lab_code,invoice_number,sample_number,parameter,result,result_in_percentage,sno)values(?,?,?,?,?,?,?)");
                        ps2.setInt(1, lab);
                        ps2.setInt(2, ino);
                        ps2.setInt(3, Integer.parseInt(sno[i]));
                        ps2.setString(4, parameter);
                        ps2.setString(5, result);
                        ps2.setString(6, result_in_percentage);
                        ps2.setInt(7, snum);
                        ps2.executeUpdate();
                        ps2.close();
                    }
                    ps1.close();
                    rs1.close();
                    sql1 =
"select parameter from wqs_watersample_result where lab_code=? and invoice_number=? and sample_number=? and test_purpose_id=? and process_flow_status_id=? and result is null order by invoice_number,sample_number,parameter";
                    System.out.println(sql1);
                    ps1 = con.prepareStatement(sql1);
                    ps1.setInt(1, lab);
                    ps1.setInt(2, ino);
                    ps1.setInt(3, Integer.parseInt(sno[i]));
                    ps1.setString(4, test_purpose);
                    ps1.setString(5, "FR");
                    rs1 = ps1.executeQuery();
                    while (rs1.next()) {
                        algae = algae + rs1.getString("parameter") + "  ";
                    }
                    if (algae.equalsIgnoreCase(""))
                        algae = "Nil";
                    System.out.println("Algae:" + algae);
                    snum++;
                    ps2 =
 con.prepareStatement("insert into wqs_customer_sample_main(lab_code,invoice_number,sample_number,parameter,result,sno)values(?,?,?,?,?,?)");
                    ps2.setInt(1, lab);
                    ps2.setInt(2, ino);
                    ps2.setInt(3, Integer.parseInt(sno[i]));
                    ps2.setString(4, "Microscopical Examination");
                    ps2.setString(5, algae);
                    ps2.setInt(6, snum);
                    ps2.executeUpdate();
                    ps2.close();
                }
            } else if (test_purpose.equalsIgnoreCase("BP") ||
                       test_purpose.equalsIgnoreCase("NADCC")) {
                param_result = "";
                for (int i = 0; i < sno.length; i++) {
                    snum = 0;
                    String sql1 =
                        "select description,(case when result_in_percentage='Y' then result||'%' else result end)as result from \n" +
                        "wqs_watersample_result a left outer join \n" +
                        "wqs_element_symbol b on a.test_purpose_id=b.test_purpose_id and a.parameter=b.element_symbol \n" +
                        "where a.lab_code=? and a.invoice_number=? and a.sample_number=? and a.test_purpose_id=? and a.process_flow_status_id=? \n" +
                        "order by a.invoice_number,a.sample_number,b.sno";
                    System.out.println(sql1);
                    ps1 = con.prepareStatement(sql1);
                    ps1.setInt(1, lab);
                    ps1.setInt(2, ino);
                    ps1.setInt(3, Integer.parseInt(sno[i]));
                    ps1.setString(4, test_purpose);
                    ps1.setString(5, "FR");
                    rs1 = ps1.executeQuery();
                    while (rs1.next()) {
                        parameter = rs1.getString("description");
                        result = rs1.getString("result");
                        System.out.println("Result======>" + result);
                        param_result =
                                param_result + sno[i] + " : " + parameter +
                                " - " + result + "\n";
                    }
                    System.out.println("param_result=====>" + param_result);
                    map.put("param_result", param_result);
                }
            }
            rs.close();
            ps.close();
            ps =
  con.prepareStatement("update wqs_invoice_details set final_result=? where lab_code=? and invoice_number=?");
            ps.setString(1, final_result);
            ps.setInt(2, lab);
            ps.setInt(3, ino);
            int upd = ps.executeUpdate();
            if (upd > 0)
                System.out.println("final result updated successfully");
            else
                System.out.println("err in final result updation");
        } catch (Exception e) {
            System.out.println("Err in second table======>" + e.getMessage());
        }
        try {
            ps.close();
            rs.close();
            ps1.close();
            rs1.close();
            String qry = "select * from";
            qry =
 qry + "(select a.employee_id,employee_prefix,employee_name,decode(qualifications,null,',',','||qualifications)as qualifications,designation,decode(designation,null,' ',designation||',')as design,";
            qry =
 qry + "decode(department,null,',',department||',')as department,office_address2,city_town_name,decode(office_pin_code,null,' ','-'||office_pin_code)as pin_code from";
            qry =
 qry + "(select employee_id,office_id,designation_id,(case when department_id='TWAD' then 'TWAD Board' else department_id end)as department from hrm_emp_current_posting where employee_id=?";
            qry = qry + ")a left outer join";
            qry =
 qry + "(select employee_id,employee_initial,employee_prefix,decode(EMPLOYEE_INITIAL,null,' ',EMPLOYEE_INITIAL||'.')||employee_name AS employee_name,qualifications from hrm_mst_employees";
            qry = qry + ")b on a.employee_id=b.employee_id left outer join";
            qry =
 qry + "(select designation_id,designation from hrm_mst_designations";
            qry =
 qry + ")c on a.designation_id=c.designation_id left outer join";
            qry =
 qry + "(select office_id,office_address1,office_address2,city_town_name,office_pin_code from com_mst_offices";
            qry = qry + ")d on a.office_id=d.office_id),";
            qry =
 qry + "(select a1.customer_id,name,address,customer_ref_no from(select lab_code,customer_id,name,address from wqs_customer where lab_code=? and customer_id=?)a1 left outer join(select lab_code,customer_id,customer_ref_no from wqs_invoice_details)b1 on a1.lab_code=b1.lab_code and a1.customer_id=b1.customer_id)";
            System.out.println("qry:" + qry);
            ps = con.prepareStatement(qry);
            ps.setInt(1, eid);
            ps.setInt(2, lab);
            ps.setString(3, cid);
            rs = ps.executeQuery();
            if (rs.next()) {
                prefix = rs.getString("employee_prefix");
                if (prefix == null)
                    prefix = "";
                ename = rs.getString("employee_name");
                // if(ctype.equalsIgnoreCase("Twad Staff"))
                //ename+="  ("+rs.getInt("employee_id")+")";
                qual = rs.getString("qualifications");
                designation = rs.getString("designation");
                design = rs.getString("design");
                dept = rs.getString("department");
                System.out.println("Depatment========================>" +
                                   dept);
                if (dept == null)
                    dept = "";
                oadd = rs.getString("office_address2");
                if (oadd == null)
                    oadd = "";
                town = rs.getString("city_town_name");
                if (town == null)
                    town = "";
                pcode = rs.getString("pin_code");
                if (pcode == null)
                    pcode = "";
                customer_name = rs.getString("name");
                if (ctype.equalsIgnoreCase("Twad Staff"))
                    customer_name +=
                            "  (E.Code:" + rs.getInt("customer_id") + ")";
                cust_address = rs.getString("address").split(",");
                int j = 0;
                tadd = "";
                for (int a = 0; a < cust_address.length; a++) {
                    if (j == 0) {
                        if (a != cust_address.length - 1) {
                            tadd = tadd + cust_address[a] + ",";
                            j++;
                        } else
                            tadd = tadd + cust_address[a];
                    } else {
                        if (a != cust_address.length - 1) {
                            tadd = tadd + cust_address[a] + ",\n";
                            j = 0;
                        } else
                            tadd = tadd + cust_address[a];
                    }

                }
                customer_ref = rs.getString("customer_ref_no");
            }
            ename = prefix + " " + ename + qual;
            if (pre_printed.equals("YES"))
                design1 = designation + ".";
            else
                design1 = design + dept;
            oadd = oadd + town + pcode;
            System.out.println("emp name:" + ename);
            System.out.println("designation:" + design);
            System.out.println("from_address:" + oadd);
            sadd1 = design.toUpperCase() + "\n" + dept + town + pcode;
            sadd = design.toUpperCase() + " " + dept + town + pcode;
            System.out.println("sadd1:" + sadd1);
            System.out.println("Signature:" + sadd);
            System.out.println("customer_ref_no:" + customer_ref);
        } catch (Exception e) {
            System.out.println("Err in address selection:" + e.getMessage());
        }
        try {
            System.out.println("Customer Type=========>" + ctype);
            System.out.println("purpose_id=========>" + test_purpose);
            if (pre_printed.equals("YES")) {
                System.out.println("inside if");
                if (test_purpose.equalsIgnoreCase("SEW") ||
                    test_purpose.equalsIgnoreCase("EFF")) {
                    if (page_size.equalsIgnoreCase("A3")) {
                        reportFile =
                                new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_Watersample_PrintedResultRep_Student_New.jasper"));
                    } else {
                        reportFile =
                                new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_Watersample_PrintedResultRep_Student.jasper"));
                    }
                } else if (test_purpose.equalsIgnoreCase("DRI")) {
                    if (ctype.equalsIgnoreCase("Student")) {
                        if (page_size.equalsIgnoreCase("A3")) {
                            reportFile =
                                    new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_Watersample_PrintedResultRep_Student_New.jasper"));
                        } else {
                            reportFile =
                                    new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_Watersample_PrintedResultRep_Student.jasper"));
                        }
                    } else {
                        if (page_size.equalsIgnoreCase("A3")) {
                            reportFile =
                                    new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_Watersample_PrintedResult_NewRep.jasper"));
                        } else {
                            reportFile =
                                    new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_Watersample_PrintedResultRep.jasper"));
                        }
                    }
                } else if (test_purpose.equalsIgnoreCase("CON")) {
                    if (page_size.equalsIgnoreCase("A3")) {
                        reportFile =
                                new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_Construction_PrintedResult_NewRep.jasper"));
                    } else {
                        reportFile =
                                new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_Construction_PrintedResultRep.jasper"));
                    }
                } else if (test_purpose.equalsIgnoreCase("PAC")) {
                    if (page_size.equalsIgnoreCase("A3")) {
                        reportFile =
                                new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_PAC_PrintedResult_NewRep.jasper"));
                    } else {
                        reportFile =
                                new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_PAC_PrintedResultRep.jasper"));
                    }
                } else if (test_purpose.equalsIgnoreCase("SOLID")) {
                    if (page_size.equalsIgnoreCase("A3")) {
                        reportFile =
                                new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_SoilSolid_PrintedResult_NewRep.jasper"));
                    } else {
                        reportFile =
                                new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_SoilSolid_PrintedResultRep.jasper"));
                    }
                } else if (test_purpose.equalsIgnoreCase("ALUM")) {
                    if (page_size.equalsIgnoreCase("A3")) {
                        reportFile =
                                new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_Alum_PrintedResult_NewRep.jasper"));
                    } else {
                        reportFile =
                                new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_Alum_PrintedResultRep.jasper"));
                    }
                } else if (test_purpose.equalsIgnoreCase("LIME")) {
                    if (page_size.equalsIgnoreCase("A3")) {
                        reportFile =
                                new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_Lime_PrintedResult_NewRep.jasper"));
                    } else {
                        reportFile =
                                new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_Lime_PrintedResultRep.jasper"));
                    }
                } else if (test_purpose.equalsIgnoreCase("BP")) {
                    if (page_size.equalsIgnoreCase("A3")) {
                        reportFile =
                                new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_Bleaching_PrintedResult_NewRep.jasper"));
                    } else {
                        reportFile =
                                new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_Bleaching_PrintedResultRep.jasper"));
                    }
                } else if (test_purpose.equalsIgnoreCase("BIO")) {
                    if (page_size.equalsIgnoreCase("A3")) {
                        reportFile =
                                new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_Biological_PrintedResult_NewRep.jasper"));
                    } else {
                        reportFile =
                                new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_Biological_PrintedResultRep.jasper"));
                    }
                } else if (test_purpose.equalsIgnoreCase("NADCC")) {
                    if (page_size.equalsIgnoreCase("A3")) {
                        reportFile =
                                new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_NADCC_PrintedResult_NewRep.jasper"));
                    } else {
                        reportFile =
                                new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_NADCC_PrintedResultRep.jasper"));
                    }
                }
            } else {
                System.out.println("inside else");
                if (test_purpose.equalsIgnoreCase("SEW") ||
                    test_purpose.equalsIgnoreCase("EFF")) {
                    if (page_size.equalsIgnoreCase("A3")) {
                        reportFile =
                                new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_Watersample_ResultRep_Student_New.jasper"));
                    } else {
                        reportFile =
                                new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_Watersample_ResultRep_Student.jasper"));
                    }
                } else if (test_purpose.equalsIgnoreCase("DRI")) {
                    if (ctype.equalsIgnoreCase("Student")) {
                        if (page_size.equalsIgnoreCase("A3")) {
                            reportFile =
                                    new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_Watersample_ResultRep_Student_New.jasper"));
                        } else {
                            reportFile =
                                    new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_Watersample_ResultRep_Student.jasper"));
                        }
                    } else {
                        if (page_size.equalsIgnoreCase("A3")) {
                            reportFile =
                                    new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_Watersample_Result_NewRep.jasper"));
                        } else {
                            reportFile =
                                    new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_Watersample_ResultRep.jasper"));
                        }
                    }
                } else if (test_purpose.equalsIgnoreCase("CON")) {
                    if (page_size.equalsIgnoreCase("A3")) {
                        reportFile =
                                new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_Construction_Result_NewRep.jasper"));
                    } else {
                        reportFile =
                                new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_Construction_ResultRep.jasper"));
                    }
                } else if (test_purpose.equalsIgnoreCase("PAC")) {
                    if (page_size.equalsIgnoreCase("A3")) {
                        reportFile =
                                new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_PAC_Result_NewRep.jasper"));
                    } else {
                        reportFile =
                                new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_PAC_ResultRep.jasper"));
                    }
                } else if (test_purpose.equalsIgnoreCase("SOLID")) {
                    if (page_size.equalsIgnoreCase("A3")) {
                        reportFile =
                                new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_SoilSolid_Result_NewRep.jasper"));
                    } else {
                        reportFile =
                                new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_SoilSolid_ResultRep.jasper"));
                    }
                } else if (test_purpose.equalsIgnoreCase("ALUM")) {
                    if (page_size.equalsIgnoreCase("A3")) {
                        reportFile =
                                new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_Alum_Result_NewRep.jasper"));
                    } else {
                        reportFile =
                                new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_Alum_ResultRep.jasper"));
                    }
                } else if (test_purpose.equalsIgnoreCase("LIME")) {
                    if (page_size.equalsIgnoreCase("A3")) {
                        reportFile =
                                new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_Lime_Result_NewRep.jasper"));
                    } else {
                        reportFile =
                                new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_Lime_ResultRep.jasper"));
                    }
                } else if (test_purpose.equalsIgnoreCase("BP")) {
                    if (page_size.equalsIgnoreCase("A3")) {
                        reportFile =
                                new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_Bleaching_Result_NewRep.jasper"));
                    } else {
                        reportFile =
                                new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_Bleaching_ResultRep.jasper"));
                    }
                } else if (test_purpose.equalsIgnoreCase("BIO")) {
                    if (page_size.equalsIgnoreCase("A3")) {
                        reportFile =
                                new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_Biological_Result_NewRep.jasper"));
                    } else {
                        reportFile =
                                new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_Biological_ResultRep.jasper"));
                    }
                } else if (test_purpose.equalsIgnoreCase("NADCC")) {
                    if (page_size.equalsIgnoreCase("A3")) {
                        reportFile =
                                new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_NADCC_Result_NewRep.jasper"));
                    } else {
                        reportFile =
                                new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_NADCC_ResultRep.jasper"));
                    }
                }
            }

            if (!reportFile.exists()) {
                System.out.println("does not exsist");
                throw new JRRuntimeException("File J not found. The report design must be compiled first.");
            }
            String path1 =
                getServletContext().getRealPath("/WEB-INF/ReportSrc") +
                File.separator;

            System.out.println("path=" + path1);
            System.out.println("image Path================" + path);
            System.out.println("Report Path:" + reportFile);

            System.out.println(JRLoader.loadObject(reportFile.getPath()));
            JasperReport jasperReport =
                (JasperReport)JRLoader.loadObject(reportFile.getPath());
            map.put("lab", lab);
            map.put("invoice_no", ino);
            map.put("emp_name", ename);
            map.put("designation", design1);
            map.put("from_address", oadd);
            if (!tadd.equalsIgnoreCase(""))
                customer_name = customer_name + ",";
            map.put("customer_name", customer_name);
            map.put("to_address", tadd);
            map.put("signature", sadd);
            map.put("sign", sadd1);
            map.put("off_rno", off_rno);
            map.put("det", det);
            map.put("customer_ref", cus_rno);
            String res1[] = res.split("--");
            result = "";
            for (int a = 0; a < res1.length; a++) {
                if (a == 0)
                    result = res1[a];
                else
                    result = result + "\n            " + res1[a];
            }
            if (!result.equalsIgnoreCase(""))
                result = "Result :" + result;
            System.out.println("result===========>" + result);
            map.put("result", result);
            map.put("SUBREPORT_DIR", path1);
            map.put("img_path", path);
            if (lab == 5000) {
                map.put("iso_code",
                        "ISO 9001-2000 Certified Laboratory(c.f.STQC,GOI No.SQ/ISO 9001/434/dt 22.01.04)\n TEST REPORT");
                map.put("header", "State Level Water Testing Laboratory");
            } else {
                map.put("iso_code", "TEST REPORT");
                map.put("header", "District Level Water Testing Laboratory");
            }
            jasperPrint = JasperFillManager.fillReport(jasperReport, map, con);

            byte buf[] = JasperExportManager.exportReportToPdf(jasperPrint);
            response.setContentType("application/pdf");
            response.setContentLength(buf.length);
            response.setHeader("Content-Disposition",
                               "attachment;filename=\"SampleResultRep.pdf\"");
            OutputStream out = response.getOutputStream();
            out.write(buf, 0, buf.length);
            out.close();
        } catch (Exception e) {
            System.out.println("Err in generating report:" + e.getMessage());
        }
    }
}
