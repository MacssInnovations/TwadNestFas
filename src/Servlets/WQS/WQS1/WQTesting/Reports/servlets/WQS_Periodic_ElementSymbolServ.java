package Servlets.WQS.WQS1.WQTesting.Reports.servlets;

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

import java.text.Format;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.GregorianCalendar;
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


public class WQS_Periodic_ElementSymbolServ extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        String CONTENT_TYPE = "text/xml";
        response.setContentType(CONTENT_TYPE);
        System.out.println("Welcome to get servlet");
        response.setHeader("cache-control", "no-cache");
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        String xml = null, bcode = null, pcode = null, hcode = null, ltype =
            null, ctype = null, qry = null, lbody = null;
        int cnt = 0, lab = 0;
        String dcode = null;
        try {
            /*Class.forName("oracle.jdbc.OracleDriver");
             con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","wqlabs","wqlabs123");*/
            ResourceBundle rb =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";

            String strDriver = rb.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rb.getString("Config.DSN");
            String strhostname = rb.getString("Config.HOST_NAME");
            String strportno = rb.getString("Config.PORT_NUMBER");
            String strsid = rb.getString("Config.SID");
            String strdbusername = rb.getString("Config.USER_NAME");
            String strdbpassword = rb.getString("Config.PASSWORD");

//            ConnectionString =
//                    strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
//                    ":" + strsid.trim();
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
            Class.forName(strDriver.trim());
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
            System.out.println("Connected THRO JSP");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        String cmd = request.getParameter("command");
        System.out.println(cmd);
        dcode = request.getParameter("dcode");
        System.out.println("district Code:" + dcode);
        if (!cmd.equalsIgnoreCase("element")) {
            if (cmd.equalsIgnoreCase("loadData")) {
                xml = "<response><command>" + cmd + "</command>";
                lab = Integer.parseInt(request.getParameter("lab"));
                try {
                    System.out.println("inside try");
                    String sql =
                        "select distinct customer_type from" + "(select distinct lab_code,invoice_number from wqs_watersample_result " +
                        ")a inner join" +
                        "(select lab_code,invoice_number,customer_type from wqs_invoice_details" +
                        ")b on a.lab_code=b.lab_code and a.invoice_number=b.invoice_number " +
                        "where a.lab_code=?";
                    ps = con.prepareStatement(sql);
                    ps.setInt(1, lab);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        cnt = cnt + 1;
                        xml = xml + "<count>";
                        xml =
 xml + "<ctype>" + rs.getString("customer_type") + "</ctype>";
                        xml = xml + "</count>";
                    }
                    if (cnt > 0) {
                        System.out.println("Success");
                        xml = xml + "<flag>Success</flag>";
                    } else {
                        System.out.println("Failure");
                        xml = xml + "<flag>Failure</flag>";
                    }
                    ps.close();
                    rs.close();
                } catch (Exception e) {
                    System.out.println("Err in changesupplier:" +
                                       e.getMessage());
                }
            } else if (cmd.equalsIgnoreCase("loadDistrict")) {
                xml = "<response><command>" + cmd + "</command>";
                lab = Integer.parseInt(request.getParameter("lab"));
                ctype = request.getParameter("ctype");
                ltype = request.getParameter("ltype");
                System.out.println("Location type:" + ltype);
                try {
                    st = con.createStatement();
                    qry =
 "select distinct b.district_code,c.district_name from" +
   "(select distinct lab_code,invoice_number,sample_number,date_of_receipt from wqs_watersample_result" +
   ")a inner join" +
   "(select lab_code,invoice_number,customer_type from wqs_invoice_details" +
   ")a1 on a.lab_code=a1.lab_code and a.invoice_number=a1.invoice_number inner join" +
   "(select lab_code,invoice_number,sample_number,district_code,location_type from wqs_sample_entry" +
   ")b on a.lab_code=b.lab_code and a.invoice_number=b.invoice_number and a.sample_number=b.sample_number inner join" +
   "(select district_code,district_name from com_mst_districts" +
   ")c on b.district_code=c.district_code where a.lab_code=" + lab +
   " and a1.customer_type='" + ctype + "'";
                    if (ltype == null)
                        System.out.println("inside null");
                    else {
                        System.out.println("inside not null");
                        qry = qry + " and b.location_type='" + ltype + "'";
                    }
                    System.out.println("query  :" + qry);
                    rs = st.executeQuery(qry);
                    while (rs.next()) {
                        cnt++;
                        String dc = rs.getString("district_code");
                        String dn = rs.getString("district_name");
                        System.out.println(dc + " " + dn);
                        xml = xml + "<count>";
                        xml = xml + "<dcode>" + dc + "</dcode>";
                        xml = xml + "<dname>" + dn + "</dname>";
                        xml = xml + "</count>";
                    }
                    if (cnt > 0)
                        xml = xml + "<flag>success</flag>";
                    else
                        xml = xml + "<flag>success</flag>";
                } catch (Exception e) {
                    System.out.println("Err in loadDistrict:" +
                                       e.getMessage());
                }
            } else if (cmd.equalsIgnoreCase("changeDistrict")) {
                xml = "<response><command>" + cmd + "</command>";
                lab = Integer.parseInt(request.getParameter("lab"));
                ctype = request.getParameter("ctype");
                System.out.println("lab:" + lab + "  Customer type:" + ctype);
                cnt = 0;
                try {
                    qry =
 ("select distinct location from" + "(select distinct lab_code,invoice_number,sample_number from wqs_watersample_result order by invoice_number,sample_number" +
  ")a inner join" +
  "(select lab_code,invoice_number,customer_type from wqs_invoice_details" +
  ")a1 on a.lab_code=a1.lab_code and a.invoice_number=a1.invoice_number inner join" +
  "(select lab_code,invoice_number,sample_number,district_code,location from wqs_sample_entry" +
  ")b on a1.lab_code=b.lab_code and a1.invoice_number=b.invoice_number and a.sample_number=b.sample_number  " +
  "where a.lab_code=? and a1.customer_type=? and b.district_code=?");
                    System.out.println("Query:" + qry);
                    ps = con.prepareStatement(qry);
                    ps.setInt(1, lab);
                    ps.setString(2, ctype);
                    ps.setString(3, dcode);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        cnt++;
                        xml = xml + "<count>";
                        xml =
 xml + "<location>" + rs.getString("location") + "</location>";
                        xml = xml + "</count>";
                    }
                    if (cnt > 0)
                        xml = xml + "<flag>success</flag>";
                    else
                        xml = xml + "<flag>failure</flag>";
                } catch (Exception e) {
                    System.out.println("Err in changeLocation:" +
                                       e.getMessage());
                }
            } else if (cmd.equalsIgnoreCase("changeLocationType")) {
                lab = Integer.parseInt(request.getParameter("lab"));
                ctype = request.getParameter("ctype");
                dcode = request.getParameter("dcode");
                ltype = request.getParameter("ltype");
                xml = "<response><command>" + cmd + "</command>";
                if (ltype.equalsIgnoreCase("VP")) {
                    xml = xml + "<ltype>" + ltype + "</ltype>";
                    try {
                        qry =
 ("select distinct block_code,blockname from" + "(select distinct lab_code,invoice_number,sample_number from wqs_watersample_result" +
  ")a inner join" +
  "(select lab_code,invoice_number,customer_type from wqs_invoice_details" +
  ")b on a.lab_code=b.lab_code and a.invoice_number=b.invoice_number inner join" +
  "(select lab_code,invoice_number,sample_number,district_code,location_type,block_code from wqs_sample_entry" +
  ")c on a.lab_code=c.lab_code and a.invoice_number=c.invoice_number and a.sample_number=c.sample_number inner join" +
  "(select district_code,block_code,blockname from com_mst_blocks" +
  ")d on c.district_code=d.district_code and c.block_code=d.block_code " +
  "where a.lab_code=? and b.customer_type=? and c.district_code=? and c.Location_type=?");
                        ps = con.prepareStatement(qry);
                        ps.setInt(1, lab);
                        ps.setString(2, ctype);
                        ps.setString(3, dcode);
                        ps.setString(4, ltype);
                        rs = ps.executeQuery();
                        while (rs.next()) {
                            cnt++;
                            xml = xml + "<count>";
                            xml =
 xml + "<bcode>" + rs.getString("block_code") + "</bcode>";
                            xml =
 xml + "<bname>" + rs.getString("blockname") + "</bname>";
                            xml = xml + "</count>";
                        }
                        if (cnt > 0)
                            xml = xml + "<flag>success</flag>";
                        else
                            xml = xml + "<flag>failure</flag>";
                    } catch (Exception e) {
                        System.out.println("Err in changeLocationType:" +
                                           e.getMessage());
                    }
                } else {

                    xml = xml + "<ltype>" + ltype + "</ltype>";
                    try {
                        qry =
 "select distinct c.district_code,c.location_type,c.local_body_code," +
   "(case when location_type='Corporation' then (select corporation_eng from pms_corporation where district_code=c.district_code and corp_code=c.local_body_code)" +
   "when location_type='Municipality' then (select municipality_eng from pms_mst_municipality where district_code=c.district_code and mucode=c.local_body_code)" +
   "when location_type='UTP' then (select tpname from pms_mst_town_panchayats where dcode=c.district_code and tpcode=c.local_body_code and type='U')" +
   "else (select tpname from pms_mst_town_panchayats where dcode=c.district_code and tpcode=c.local_body_code and type='R') end" +
   ") as local_body_name " + "from  " +
   "(select distinct lab_code,invoice_number,sample_number from wqs_watersample_result  " +
   ")a inner join  " +
   "(select lab_code,invoice_number,customer_type from wqs_invoice_details  " +
   ")b on a.lab_code=b.lab_code and a.invoice_number=b.invoice_number inner join  " +
   "(select lab_code,invoice_number,sample_number,district_code,location_type,local_body_code from wqs_sample_entry  " +
   ")c on a.lab_code=c.lab_code and a.invoice_number=c.invoice_number and a.sample_number=c.sample_number " +
   "where a.lab_code=? and b.customer_type=? and c.district_code=? and c.location_type=?";
                        ps = con.prepareStatement(qry);
                        ps.setInt(1, lab);
                        ps.setString(2, ctype);
                        ps.setString(3, dcode);
                        ps.setString(4, ltype);
                        rs = ps.executeQuery();
                        while (rs.next()) {
                            cnt++;
                            xml = xml + "<count>";
                            xml =
 xml + "<local_body_code>" + rs.getString("local_body_code") +
   "</local_body_code>";
                            xml =
 xml + "<local_body_name>" + rs.getString("local_body_name") +
   "</local_body_name>";
                            xml = xml + "</count>";
                        }
                        if (cnt > 0)
                            xml = xml + "<flag>success</flag>";
                        else
                            xml = xml + "<flag>failure</flag>";
                    } catch (Exception e) {
                        System.out.println("Err in load corporation:" +
                                           e.getMessage());
                    }
                }
            } else if (cmd.equalsIgnoreCase("changeLocalBody")) {
                xml = "<response><command>" + cmd + "</command>";
                lab = Integer.parseInt(request.getParameter("lab"));
                ctype = request.getParameter("ctype");
                dcode = request.getParameter("dcode");
                ltype = request.getParameter("ltype");
                lbody = request.getParameter("lbody");
                System.out.println("ltype" + ltype + "  local body:" + lbody);
                try {
                    System.out.println("inside try");
                    qry =
 "select distinct location from" + "(select distinct lab_code,invoice_number,sample_number from wqs_watersample_result " +
   ")a inner join " +
   "(select lab_code,invoice_number,customer_type from wqs_invoice_details" +
   ")b on a.lab_code=b.lab_code and a.invoice_number=b.invoice_number inner join" +
   "(select lab_code,invoice_number,sample_number,district_code,location_type,local_body_code,location from wqs_sample_entry " +
   ")c on a.lab_code=c.lab_code and a.invoice_number=c.invoice_number and a.sample_number=c.sample_number " +
   "where a.lab_code=? and b.customer_type=? and c.district_code=? and c.location_type=? and c.local_body_code=?";
                    ps = con.prepareStatement(qry);
                    System.out.println(qry);
                    ps.setInt(1, lab);
                    ps.setString(2, ctype);
                    ps.setString(3, dcode);
                    ps.setString(4, ltype);
                    ps.setString(5, lbody);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        cnt++;
                        xml = xml + "<count>";
                        xml =
 xml + "<location>" + rs.getString("location") + "</location>";
                        xml = xml + "</count>";
                    }
                    if (cnt > 0) {
                        System.out.println("Success");
                        xml = xml + "<flag>success</flag>";
                    } else {
                        System.out.println("Failure");
                        xml = xml + "<flag>failure</flag>";
                    }
                } catch (Exception e) {
                    System.out.println("Err in changesupplier:" +
                                       e.getMessage());
                }
            } else if (cmd.equalsIgnoreCase("changeBlock")) {
                lab = Integer.parseInt(request.getParameter("lab"));
                ctype = request.getParameter("ctype");
                dcode = request.getParameter("dcode");
                ltype = request.getParameter("ltype");
                bcode = request.getParameter("bcode");
                xml = "<response><command>" + cmd + "</command>";
                try {
                    System.out.println("inside try");
                    qry =
 "select distinct panchayat_code,panchayatname from " +
   "(select distinct lab_code,invoice_number,sample_number from wqs_watersample_result " +
   ")a inner join " +
   "(select lab_code,invoice_number,customer_type from wqs_invoice_details" +
   ")b on a.lab_code=b.lab_code and a.invoice_number=b.invoice_number inner join" +
   "(select lab_code,invoice_number,sample_number,district_code,location_type,block_code,panchayat_code from wqs_sample_entry" +
   ")c on a.lab_code=c.lab_code and a.invoice_number=c.invoice_number and a.sample_number=c.sample_number inner join" +
   "(select district_code,block_code,panch_code,panchayatname from com_mst_panchayats" +
   ")d on c.district_code=d.district_code and c.block_code=d.block_code and c.panchayat_code=d.panch_code " +
   "where a.lab_code=? and b.customer_type=? and c.district_code=? and c.Location_type=? and c.block_code=?";
                    ps = con.prepareStatement(qry);
                    ps.setInt(1, lab);
                    ps.setString(2, ctype);
                    ps.setString(3, dcode);
                    ps.setString(4, ltype);
                    ps.setString(5, bcode);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        cnt++;
                        xml = xml + "<count>";
                        xml =
 xml + "<panch_code>" + rs.getString("panchayat_code") + "</panch_code>";
                        xml =
 xml + "<panch_name>" + rs.getString("panchayatname") + "</panch_name>";
                        xml = xml + "</count>";
                    }
                    if (cnt > 0) {
                        System.out.println("Success");
                        xml = xml + "<flag>Success</flag>";
                    } else {
                        System.out.println("Failure");
                        xml = xml + "<flag>Failure</flag>";
                    }
                } catch (Exception e) {
                    System.out.println("Err in changesupplier:" +
                                       e.getMessage());
                }
            } else if (cmd.equalsIgnoreCase("changePanchayat")) {
                lab = Integer.parseInt(request.getParameter("lab"));
                ctype = request.getParameter("ctype");
                dcode = request.getParameter("dcode");
                ltype = request.getParameter("ltype");
                bcode = request.getParameter("bcode");
                pcode = request.getParameter("pcode");
                xml = "<response><command>" + cmd + "</command>";
                try {
                    System.out.println("inside try");
                    qry =
 "select distinct habitation_code,hname from  " + "(select distinct lab_code,invoice_number,sample_number from wqs_watersample_result  " +
   ")a inner join  " +
   "(select lab_code,invoice_number,customer_type from wqs_invoice_details  " +
   ")b on a.lab_code=b.lab_code and a.invoice_number=b.invoice_number inner join  " +
   "(select lab_code,invoice_number,sample_number,district_code,location_type,block_code,panchayat_code,habitation_code from wqs_sample_entry  " +
   ")c on a.lab_code=c.lab_code and a.invoice_number=c.invoice_number and a.sample_number=c.sample_number inner join" +
   "(select district_code,block_code,panch_code,hab_code,hname from com_mst_habitations  " +
   ")d on c.district_code=d.district_code and c.block_code=d.block_code and c.panchayat_code=d.panch_code and c.habitation_code=d.hab_code " +
   "where a.lab_code=? and b.customer_type=? and c.district_code=? and c.Location_type=? and c.block_code=? and c.panchayat_code=?";
                    ps = con.prepareStatement(qry);
                    System.out.print(qry);
                    ps.setInt(1, lab);
                    ps.setString(2, ctype);
                    ps.setString(3, dcode);
                    ps.setString(4, ltype);
                    ps.setString(5, bcode);
                    ps.setString(6, pcode);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        cnt++;
                        xml = xml + "<count>";
                        xml =
 xml + "<hab_code>" + rs.getString("habitation_code") + "</hab_code>";
                        xml =
 xml + "<hab_name>" + rs.getString("hname") + "</hab_name>";
                        xml = xml + "</count>";
                    }
                    if (cnt > 0) {
                        System.out.println("Success");
                        xml = xml + "<flag>Success</flag>";
                    } else {
                        System.out.println("Failure");
                        xml = xml + "<flag>Failure</flag>";
                    }
                } catch (Exception e) {
                    System.out.println("Err in changesupplier:" +
                                       e.getMessage());
                }
            } else if (cmd.equalsIgnoreCase("changeHabitation")) {
                lab = Integer.parseInt(request.getParameter("lab"));
                ctype = request.getParameter("ctype");
                dcode = request.getParameter("dcode");
                ltype = request.getParameter("ltype");
                bcode = request.getParameter("bcode");
                pcode = request.getParameter("pcode");
                hcode = request.getParameter("hcode");
                xml = "<response><command>" + cmd + "</command>";
                try {
                    System.out.println("inside try");
                    qry =
 "select distinct location from  " + "(select distinct lab_code,invoice_number,sample_number from wqs_watersample_result  " +
   ")a inner join  " +
   "(select lab_code,invoice_number,customer_type from wqs_invoice_details  " +
   ")b on a.lab_code=b.lab_code and a.invoice_number=b.invoice_number inner join  " +
   "(select lab_code,invoice_number,sample_number,district_code,location_type,block_code,panchayat_code,habitation_code,location from wqs_sample_entry  " +
   ")c on a.lab_code=c.lab_code and a.invoice_number=c.invoice_number and a.sample_number=c.sample_number " +
   "where a.lab_code=? and b.customer_type=? and c.district_code=? and c.Location_type=? and c.block_code=? and c.panchayat_code=? and habitation_code=?";
                    ps = con.prepareStatement(qry);
                    ps.setInt(1, lab);
                    ps.setString(2, ctype);
                    ps.setString(3, dcode);
                    ps.setString(4, ltype);
                    ps.setString(5, bcode);
                    ps.setString(6, pcode);
                    ps.setString(7, hcode);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        cnt++;
                        xml = xml + "<count>";
                        xml =
 xml + "<location>" + rs.getString("location") + "</location>";
                        xml = xml + "</count>";
                    }
                    if (cnt > 0) {
                        System.out.println("Success");
                        xml = xml + "<flag>success</flag>";
                    } else {
                        System.out.println("Failure");
                        xml = xml + "<flag>failure</flag>";
                    }
                } catch (Exception e) {
                    System.out.println("Err in changesupplier:" +
                                       e.getMessage());
                }
            }
            xml = xml + "</response>";
            System.out.println("xml:" + xml);
            out.println(xml);
        } else {
            CONTENT_TYPE = "text/html";
            response.setContentType(CONTENT_TYPE);
            String html = "";

            try {
                qry =
 "select * from(select element_symbol from wqs_all_parameters)a inner join(select sno,element_symbol,description from wqs_element_symbol)b on a.element_symbol=b.element_symbol order by sno";
                ps = con.prepareStatement(qry);
                rs = ps.executeQuery();

                int count = 0;
                html =
"<table cellpadding=0 cellspacing=0 border=0 width='100%'>";
                boolean bool = false;
                while (rs.next()) {
                    String val = rs.getString("element_symbol");
                    String valdesc = rs.getString("description");
                    System.out.println("value :" + val);

                    if (bool = !bool) {
                        html =
html + "<tr bgcolor=\"pink\"><td><input type='checkbox' name='es' value='" +
 val + "'></td>";
                        html = html + "<td>" + valdesc + "</td></tr>";
                    } else {
                        html =
html + "<tr ><td><input type='checkbox' name='es' value='" + val + "'></td>";
                        html = html + "<td>" + valdesc + "</td></tr>";
                    }
                    count++;
                }
                if (count == 0) {
                    html = "There is no Parameter";
                } else {
                    html =
html + "<tr ><td colspan='2'><a href='javascript:elementSelectAll()'>Select All</a>&nbsp;&nbsp;&nbsp;<a href='javascript:elementclose()'>Close</a></td></tr>";
                }
                html = html + "</table>";
                System.out.println("html:" + html);
            } catch (Exception e) {
                System.out.println("Region selection error " + e);
                System.out.println("html:" + html);
            }
            out.print(html);
            System.out.println(html);
            out.flush();
        }
        out.close();
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        response.setContentType(CONTENT_TYPE);
        System.out.println("Welcome to post servlet");
        Connection con = null;
        Statement st = null;
        JasperPrint jasperPrint = null;
        String ctype = null, ltype = null, lbody = null;
        String district[] = null;
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
        File reportFile = null;
        JasperReport jasperReport = null;
        Map map = new HashMap();
        try {
            System.out.println("calling servlet");
            int lab = Integer.parseInt(request.getParameter("lab"));

            String[] fd = request.getParameter("fdate").split("/");
            Calendar c =
                new GregorianCalendar(Integer.parseInt(fd[2]), Integer.parseInt(fd[1]) -
                                      1, Integer.parseInt(fd[0]));
            java.util.Date dtfrom = c.getTime();
            System.out.println("fdate=====>" + dtfrom);

            String[] td = request.getParameter("tdate").split("/");
            c =
   new GregorianCalendar(Integer.parseInt(td[2]), Integer.parseInt(td[1]) - 1,
                         Integer.parseInt(td[0]));
            java.util.Date dtto = c.getTime();
            System.out.println("tdate=====>" + dtto);

            Format formatter;
            formatter = new SimpleDateFormat("dd/MMM/yyyy");
            String f = formatter.format(dtfrom);

            System.out.println("after fdate=====>" + f);
            String t = formatter.format(dtto);
            System.out.println("after tdate=====>" + t);

            ctype = request.getParameter("ctype");
            ltype = request.getParameter("ltype");

            String dname = request.getParameter("dname");
            String es[] = request.getParameter("es").split(",");
            String operation = request.getParameter("operation");
            System.out.println("operation:" + operation);
            String cval = request.getParameter("cval");
            System.out.println("Parameter:" + es);
            lbody = request.getParameter("lbody");
            String bname = request.getParameter("block");
            String pname = request.getParameter("Panchayat");
            String hname = request.getParameter("Habitation");
            String location = request.getParameter("Location");
            System.out.println("location=====>" + location);
            StringBuffer sb = new StringBuffer();
            st = con.createStatement();
            sb.append("select distinct a.invoice_number,a.sample_number,date_of_receipt,receipt_time,customer_type,district_code,sno,description,result,location," +
                      "(case when block_code!='-' and block_code!='0' then (select blockname from com_mst_blocks where district_code=b.district_code and block_code=b.block_code) else b.block_code end" +
                      ")as block_name," +
                      "(case when panchayat_code!='-' and panchayat_code!='0' then (select panchayatname from com_mst_panchayats where district_code=b.district_code and block_code=b.block_code and panch_code=b.panchayat_code) else b.panchayat_code end" +
                      ")as panchayat_name," +
                      "(case when habitation_code!='-' and habitation_code!='0' then (select hname from com_mst_habitations where district_code=b.district_code and block_code=b.block_code and panch_code=b.panchayat_code and hab_code=b.habitation_code) else b.habitation_code end" +
                      ")as habitation_name," +
                      "(case when location_type!='VP' and location_type!='-'  then " +
                      "           (case when location_type='Corporation' then (select corporation_eng from pms_corporation where district_code=b.district_code and corp_code=b.local_body_code) " +
                      "                 when location_type='Municipality' then (select municipality_eng from pms_mst_municipality where district_code=b.district_code and mucode=b.local_body_code)" +
                      "                 when location_type='UTP' then (select tpname from pms_mst_town_panchayats where dcode=b.district_code and tpcode=b.local_body_code and type='U')" +
                      "                 when location_type='RTP' then (select tpname from pms_mst_town_panchayats where dcode=b.district_code and tpcode=b.local_body_code and type='R') end" +
                      "           )" + "      else b.location_type end" +
                      ")as local_body,location_type from");
            sb.append("(select lab_code,invoice_number,sample_number,date_of_receipt,receipt_time,parameter,result from (select lab_code,invoice_number,sample_number,date_of_receipt,receipt_time,parameter,to_number(result)as result from wqs_watersample_result where parameter in(");
            for (int j = 0; j < es.length; j++) {
                sb.append("'");
                sb.append(es[j]);
                if (j == es.length - 1)
                    sb.append("'");
                else
                    sb.append("',");
            }
            sb.append(") and result not in('None','none','NONE'))");
            sb.append(")a inner join");
            sb.append("(select lab_code,invoice_number,customer_type from wqs_invoice_details");
            sb.append(")aa on a.lab_code=aa.lab_code and a.invoice_number=aa.invoice_number inner join");
            sb.append("(select lab_code,invoice_number,sample_number,district_code,location_type,local_body_code,block_code,panchayat_code,habitation_code,location from wqs_sample_entry");
            sb.append(")b on a.lab_code=b.lab_code and a.invoice_number=b.invoice_number and a.sample_number=b.sample_number left outer join");
            sb.append("(select sno,element_symbol,description from wqs_element_symbol)c on a.parameter=c.element_symbol ");
            sb.append(" where a.lab_code=");
            sb.append(lab);
            sb.append(" and date_of_receipt between to_date('");
            sb.append(f);
            sb.append("','dd/mm/yyyy') and to_date('");
            sb.append(t);
            sb.append("','dd/mm/yyyy')");
            if (operation.equalsIgnoreCase("Equal"))
                sb.append(" and result =to_number('");
            else if (operation.equalsIgnoreCase("Lessthan"))
                sb.append(" and result <to_number('");
            else if (operation.equalsIgnoreCase("Greaterthan"))
                sb.append(" and result >to_number('");
            else if (operation.equalsIgnoreCase("Lessthanequal"))
                sb.append(" and result <=to_number('");
            else
                sb.append(" and result >=to_number('");
            sb.append(cval);
            if (!ctype.equals("")) {
                sb.append("') and customer_type='");
                sb.append(ctype);
                sb.append("'");
                if (!dname.equals("")) {
                    sb.append(" and district_code='");
                    district = dname.split("--");
                    System.out.println("district=====>" + district[1]);
                    sb.append(district[0]);
                    sb.append("'");
                }
                if (ctype.equalsIgnoreCase("Twad")) {
                    if (!ltype.equals("")) {
                        sb.append(" and location_type='");
                        sb.append(ltype);
                        sb.append("'");
                        if (ltype.equalsIgnoreCase("VP")) {
                            if (!bname.equals("")) {
                                sb.append(" and block_code='");
                                String block[] = bname.split("--");
                                sb.append(block[0]);
                                sb.append("'");
                                if (!pname.equals("")) {
                                    sb.append(" and panchayat_code='");
                                    String Panchayat[] = pname.split("--");
                                    System.out.println("Panchayat=====>" +
                                                       Panchayat[1]);
                                    sb.append(Panchayat[0]);
                                    sb.append("'");
                                    if (!hname.equals("")) {
                                        sb.append(" and habitation_code='");
                                        String Habitation[] =
                                            hname.split("--");
                                        System.out.println("Habitation=====>" +
                                                           Habitation[1]);
                                        sb.append(Habitation[0]);
                                        sb.append("'");
                                        if (!location.equals("")) {
                                            sb.append(" and location='");
                                            sb.append(location);
                                            sb.append("'");
                                            System.out.println("location is not null");
                                            sb.append(" order by invoice_number,sample_number,sno");
                                            System.out.println("Report Query:" +
                                                               sb.toString());
                                            reportFile =
                                                    new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_Location_TwoParam.jasper"));
                                            map.put("fromdt", dtfrom);
                                            map.put("todt", dtto);
                                            map.put("district", district[1]);
                                            map.put("block", block[1]);
                                            map.put("panchayat", Panchayat[1]);
                                            map.put("habitation",
                                                    Habitation[1]);
                                            map.put("location", location);
                                            map.put("ReportQuery",
                                                    sb.toString());
                                        } else {
                                            System.out.println("location is null");
                                            sb.append(" order by location,invoice_number,sample_number,sno");
                                            System.out.println("Report Query:" +
                                                               sb.toString());
                                            reportFile =
                                                    new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_Habitation_TwoParam.jasper"));
                                            map.put("fromdt", dtfrom);
                                            map.put("todt", dtto);
                                            map.put("district", district[1]);
                                            map.put("block", block[1]);
                                            map.put("panchayat", Panchayat[1]);
                                            map.put("habitation",
                                                    Habitation[1]);
                                            map.put("ReportQuery",
                                                    sb.toString());
                                        }
                                    } else {
                                        System.out.println("habitation code is null");
                                        sb.append(" order by habitation_name,location,invoice_number,sample_number,sno");
                                        System.out.println("Report Query:" +
                                                           sb.toString());
                                        reportFile =
                                                new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_Panchayat_TwoParam.jasper"));
                                        map.put("fromdt", dtfrom);
                                        map.put("todt", dtto);
                                        map.put("district", district[1]);
                                        map.put("block", block[1]);
                                        map.put("panchayat", Panchayat[1]);
                                        map.put("ReportQuery", sb.toString());
                                    }
                                } else {
                                    System.out.println("panchayat code is null");
                                    sb.append(" order by panchayat_name,habitation_name,location,invoice_number,sample_number,sno");
                                    System.out.println("Report Query:" +
                                                       sb.toString());
                                    reportFile =
                                            new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_Block_TwoParam.jasper"));
                                    map.put("fromdt", dtfrom);
                                    map.put("todt", dtto);
                                    map.put("district", district[1]);
                                    map.put("block", block[1]);
                                    map.put("ReportQuery", sb.toString());
                                }
                            } else {
                                System.out.println("block code is null");
                                sb.append(" order by block_name,panchayat_name,habitation_name,location,invoice_number,sample_number,sno");
                                System.out.println("Report Query:" +
                                                   sb.toString());
                                reportFile =
                                        new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_District_TwoParam.jasper"));
                                map.put("fromdt", dtfrom);
                                map.put("todt", dtto);
                                map.put("district", district[1]);
                                map.put("ReportQuery", sb.toString());
                            }
                        } else {
                            System.out.println("inside local body");
                            if (!lbody.equals("")) {
                                sb.append(" and local_body_code='");
                                String lb[] = lbody.split("--");
                                System.out.println("Local Body=====>" + lb[1]);
                                sb.append(lb[0]);
                                sb.append("'");
                                if (!location.equals("")) {
                                    sb.append(" and location='");
                                    sb.append(location);
                                    sb.append("'");
                                    System.out.println("Location in Local body is not null");
                                    sb.append(" order by invoice_number,sample_number");
                                    System.out.println("Report Query:" +
                                                       sb.toString());
                                    reportFile =
                                            new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_Location_TwoParam_Others.jasper"));
                                    map.put("fromdt", dtfrom);
                                    map.put("todt", dtto);
                                    map.put("district", district[1]);
                                    map.put("location_type", ltype);
                                    map.put("local_body", lb[1]);
                                    map.put("location", location);
                                    map.put("ReportQuery", sb.toString());
                                } else {
                                    System.out.println("Location in Local body is null");
                                    sb.append(" order by location,invoice_number,sample_number,sno");
                                    System.out.println("Report Query:" +
                                                       sb.toString());
                                    reportFile =
                                            new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_LocalBody_TwoParam_Others.jasper"));
                                    map.put("fromdt", dtfrom);
                                    map.put("todt", dtto);
                                    map.put("district", district[1]);
                                    map.put("location_type", ltype);
                                    map.put("local_body", lb[1]);
                                    map.put("ReportQuery", sb.toString());
                                }
                            } else {
                                System.out.println("localbody in location type local body is null");
                                sb.append(" order by local_body,location,invoice_number,sample_number,sno");
                                System.out.println("Report Query:" +
                                                   sb.toString());
                                reportFile =
                                        new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_District_TwoParam_Others.jasper"));
                                map.put("fromdt", dtfrom);
                                map.put("todt", dtto);
                                map.put("district", district[1]);
                                map.put("ReportQuery", sb.toString());
                            }

                        }
                    }
                } else {
                    if (!location.equals("")) {
                        sb.append(" and location='");
                        sb.append(location);
                        sb.append("'");
                        sb.append(" order by invoice_number,sample_number,sno");
                        System.out.println("Report Query:" + sb.toString());
                        reportFile =
                                new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_Location_TwoParam_NonTwad.jasper"));
                        map.put("fromdt", dtfrom);
                        map.put("todt", dtto);
                        map.put("district", district[1]);
                        map.put("location", location);
                        map.put("ReportQuery", sb.toString());
                    } else {
                        sb.append(" order by location,invoice_number,sample_number,sno");
                        System.out.println("Report Query:" + sb.toString());
                        reportFile =
                                new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_District_TwoParam_NonTwad.jasper"));
                        map.put("fromdt", dtfrom);
                        map.put("todt", dtto);
                        map.put("district", district[1]);
                        map.put("ReportQuery", sb.toString());
                    }
                }
            }
            if (!reportFile.exists()) {
                System.out.println("does not exsist");
                throw new JRRuntimeException("File J not found. The report design must be compiled first.");
            }
            System.out.println(JRLoader.loadObject(reportFile.getPath()));
            jasperReport =
                    (JasperReport)JRLoader.loadObject(reportFile.getPath());
            jasperPrint = JasperFillManager.fillReport(jasperReport, map, con);
            /* reportFile = new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_TwoParameter.jasper"));
                       System.out.println("after path");

                       if (!reportFile.exists())
                       {
                           System.out.println("does not exsist");
                           throw new JRRuntimeException("File J not found. The report design must be compiled first.");
                       }
                       System.out.println(JRLoader.loadObject(reportFile.getPath()));
                       jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
                       map = new HashMap();
                       map.put("fromdt",dtfrom);
                       map.put("todt",dtto);
                       map.put("ctype",ctype);
                       map.put("district",district[0]);
                       map.put("ltype",ltype);
                       map.put("lbody",lbody);
                       map.put("panchayat",Panchayat[0]);
                       map.put("habitation",Habitation[0]);
                       map.put("location",location);
                       map.put("es",es);
                       map.put("symbol1",symbol1);
                       map.put("symbol2",symbol2);
                       map.put("value1",value1);
                       map.put("value2",value2);*/
            jasperPrint = JasperFillManager.fillReport(jasperReport, map, con);


            byte buf[] = JasperExportManager.exportReportToPdf(jasperPrint);
            response.setContentType("application/pdf");
            response.setContentLength(buf.length);
            response.setHeader("Content-Disposition",
                               "attachment;filename=\"WQS_Periodic_SampleResultRep.pdf\"");
            OutputStream out = response.getOutputStream();
            out.write(buf, 0, buf.length);
            out.close();
        } catch (Exception ex) {
            String connectMsg =
                "Could not create the report " + ex.getMessage() + " " +
                ex.getLocalizedMessage();
            System.out.println(connectMsg);
        }

    }
}
