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

public class WQS_RegularParamServ extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

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
        response.setHeader("cache-control", "no-cache");
        System.out.println("Welcome to get servlet");
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        String xml = null, bcode = null, desc = null, pcode = null, hcode =
            null, lcode = null;
        int cnt = 0;
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
        //String dcode=request.getParameter("dcode");
        //System.out.println("district Code:"+dcode);
        if (cmd.equalsIgnoreCase("changeDistrict")) {
            String dcode = request.getParameter("dcode");
            System.out.println("district Code:" + dcode);
            xml = "<response><command>" + cmd + "</command>";
            try {
                System.out.println("inside try");
                ps =
  con.prepareStatement("select * from(select distinct RESURVEY_BLK_CODE,RESURVEY_DIST_CODE from WQS_SAMPLERESULT where CUSTOMER_TYPE=? and RESURVEY_DIST_CODE=?)a inner join(select STATECODE,DISTRICT_CODE,BLOCKCODE,BLOCKNAME from COM_MST_BLOCKS)b on a.RESURVEY_DIST_CODE=b.DISTRICT_CODE and a.RESURVEY_BLK_CODE=b.BLOCKCODE");
                ps.setString(1, "Twad");
                ps.setString(2, dcode);
                rs = ps.executeQuery();
                while (rs.next()) {
                    xml = xml + "<count>";
                    bcode = rs.getString("RESURVEY_BLK_CODE");
                    desc = rs.getString("BLOCKNAME");
                    xml =
 xml + "<bcode>" + bcode + "</bcode><bdesc>" + desc + "</bdesc>";
                    xml = xml + "</count>";
                    cnt = cnt + 1;
                }
                if (cnt > 0) {
                    System.out.println("Success");
                    xml = xml + "<flag>Success</flag>";
                } else {
                    System.out.println("Failure");
                    xml = xml + "<flag>Failure</flag>";
                }
            } catch (Exception e) {
                System.out.println("Err in changesupplier:" + e.getMessage());
            }
            xml = xml + "</response>";
            System.out.println("xml:" + xml);
            out.println(xml);
        } else if (cmd.equalsIgnoreCase("changeBlock")) {
            String dcode = request.getParameter("dcode");
            System.out.println("district Code:" + dcode);
            bcode = request.getParameter("bcode");
            System.out.println("block code:" + bcode);
            xml = "<response><command>" + cmd + "</command>";
            try {
                System.out.println("inside try");
                ps =
  con.prepareStatement("select * from(select distinct RESURVEY_PAN_CODE,RESURVEY_BLK_CODE,RESURVEY_DIST_CODE from WQS_SAMPLERESULT where CUSTOMER_TYPE=? and RESURVEY_DIST_CODE=? and RESURVEY_BLK_CODE=?)a inner join(select STATE_CODE,DISTRICT_CODE,BLOCK_CODE,PANCH_CODE,PANCHAYATNAME from COM_MST_PANCHAYATS)b on a.RESURVEY_DIST_CODE=b.DISTRICT_CODE and a.RESURVEY_BLK_CODE=b.BLOCK_CODE and a.RESURVEY_PAN_CODE=b.PANCH_CODE");
                ps.setString(1, "Twad");
                ps.setString(2, dcode);
                ps.setString(3, bcode);
                rs = ps.executeQuery();
                while (rs.next()) {
                    xml = xml + "<count>";
                    pcode = rs.getString("RESURVEY_PAN_CODE");
                    desc = rs.getString("PANCHAYATNAME");
                    xml =
 xml + "<pcode>" + pcode + "</pcode><pdesc>" + desc + "</pdesc>";
                    xml = xml + "</count>";
                    cnt = cnt + 1;
                }
                if (cnt > 0) {
                    System.out.println("Success");
                    xml = xml + "<flag>Success</flag>";
                } else {
                    System.out.println("Failure");
                    xml = xml + "<flag>Failure</flag>";
                }
            } catch (Exception e) {
                System.out.println("Err in changesupplier:" + e.getMessage());
            }
            xml = xml + "</response>";
            System.out.println("xml:" + xml);
            out.println(xml);
        } else if (cmd.equalsIgnoreCase("changePanchayat")) {
            String dcode = request.getParameter("dcode");
            System.out.println("district Code:" + dcode);
            bcode = request.getParameter("bcode");
            pcode = request.getParameter("pcode");
            System.out.println("block code:" + bcode + "pan code:" + pcode);
            xml = "<response><command>" + cmd + "</command>";
            try {
                System.out.println("inside try");
                ps =
  con.prepareStatement("select * from(select distinct RESURVEY_HAB_CODE,RESURVEY_PAN_CODE,RESURVEY_BLK_CODE,RESURVEY_DIST_CODE from WQS_SAMPLERESULT where CUSTOMER_TYPE=? and RESURVEY_DIST_CODE=? and RESURVEY_BLK_CODE=? and RESURVEY_PAN_CODE=?)a inner join(select STATE_CODE,DISTRICT_CODE,BLOCK_CODE,PANCH_CODE,HAB_CODE,NAME from COM_MST_HABITATIONS where STATE_CODE=(select STATE_CODE from COM_MST_STATE where STATE_NAME=?))b on a.RESURVEY_DIST_CODE=b.DISTRICT_CODE and a.RESURVEY_BLK_CODE=b.BLOCK_CODE and a.RESURVEY_PAN_CODE=b.PANCH_CODE and a.RESURVEY_HAB_CODE=b.HAB_CODE");
                System.out.print("select * from(select distinct RESURVEY_HAB_CODE,RESURVEY_PAN_CODE,RESURVEY_BLK_CODE,RESURVEY_DIST_CODE from WQS_SAMPLERESULT where CUSTOMER_TYPE='Twad' and RESURVEY_DIST_CODE='" +
                                 dcode + "' and RESURVEY_BLK_CODE='" + bcode +
                                 "' and RESURVEY_PAN_CODE='" + pcode +
                                 "')a inner join(select STATE_CODE,DISTRICT_CODE,BLOCK_CODE,PANCH_CODE,HAB_CODE,NAME from COM_MST_HABITATIONS where STATE_CODE=(select STATE_CODE from COM_MST_STATE where STATE_NAME='TAMIL NADU'))b on a.RESURVEY_DIST_CODE=b.DISTRICT_CODE and a.RESURVEY_BLK_CODE=b.BLOCK_CODE and a.RESURVEY_PAN_CODE=b.PANCH_CODE and a.RESURVEY_HAB_CODE=b.HAB_CODE");
                ps.setString(1, "Twad");
                ps.setString(2, dcode);
                ps.setString(3, bcode);
                ps.setString(4, pcode);
                ps.setString(5, "TAMIL NADU");
                rs = ps.executeQuery();
                while (rs.next()) {
                    xml = xml + "<count>";
                    hcode = rs.getString("RESURVEY_HAB_CODE");
                    desc = rs.getString("NAME");
                    xml =
 xml + "<hcode>" + hcode + "</hcode><hdesc>" + desc + "</hdesc>";
                    xml = xml + "</count>";
                    cnt = cnt + 1;
                }
                if (cnt > 0) {
                    System.out.println("Success");
                    xml = xml + "<flag>Success</flag>";
                } else {
                    System.out.println("Failure");
                    xml = xml + "<flag>Failure</flag>";
                }
            } catch (Exception e) {
                System.out.println("Err in changesupplier:" + e.getMessage());
            }
            xml = xml + "</response>";
            System.out.println("xml:" + xml);
            out.println(xml);
        } else if (cmd.equalsIgnoreCase("changeHabitation")) {
            String dcode = request.getParameter("dcode");
            System.out.println("district Code:" + dcode);
            bcode = request.getParameter("bcode");
            pcode = request.getParameter("pcode");
            hcode = request.getParameter("hcode");
            System.out.println("block code:" + bcode + " pan code:" + pcode +
                               " Hab Code:" + hcode);
            xml = "<response><command>" + cmd + "</command>";
            try {
                System.out.println("inside try");
                ps =
  con.prepareStatement("select distinct LOCATION from WQS_SAMPLERESULT where CUSTOMER_TYPE=? and RESURVEY_DIST_CODE=? and RESURVEY_BLK_CODE=? and RESURVEY_PAN_CODE=? and RESURVEY_HAB_CODE=?");
                ps.setString(1, "Twad");
                ps.setString(2, dcode);
                ps.setString(3, bcode);
                ps.setString(4, pcode);
                ps.setString(5, hcode);
                rs = ps.executeQuery();
                while (rs.next()) {
                    xml = xml + "<count>";
                    lcode = rs.getString("LOCATION");
                    System.out.println("location :" + lcode);
                    xml = xml + "<lcode>" + lcode + "</lcode>";
                    xml = xml + "</count>";
                    cnt = cnt + 1;
                }
                if (cnt > 0) {
                    System.out.println("Success");
                    xml = xml + "<flag>Success</flag>";
                } else {
                    System.out.println("Failure");
                    xml = xml + "<flag>Failure</flag>";
                }
            } catch (Exception e) {
                System.out.println("Err in changesupplier:" + e.getMessage());
            }
            xml = xml + "</response>";
            System.out.println("xml:" + xml);
            out.println(xml);
        } else if (cmd.equalsIgnoreCase("loadParameter")) {
            CONTENT_TYPE = "text/html";
            response.setContentType(CONTENT_TYPE);
            String html = "";
            try {
                String parameter[] =
                { "TDS", "Alkalinity", "Total Hardness", "Fluoride", "Iron",
                  "Nitrite" };
                String param[] =
                { "tds", "alkalinity", "hardness", "fluoride", "fe", "no2" };
                html =
"<table cellpadding=0 cellspacing=0 border=1 width='100%'>";
                boolean bool = false;
                html = html + "<tr><td>Parameter</td>";
                html = html + "<td>Min Value</td>";
                html = html + "<td>Max Value</td></tr>";
                for (int i = 0; i < parameter.length; i++) {
                    if (bool = !bool) {
                        html =
html + "<tr bgcolor=\"pink\"><td><input type='checkbox' name='chkelement' id=" +
 i + " value='" + param[i] + "' onclick='checkParam(this.id)'>";
                        html = html + parameter[i] + "</td>";
                        html =
html + "<td><input type='text' name='minvalue' style='background-color:pink' disabled='disabled' onkeypress='return filter_real(event,this,8,4)'></td>";
                        html =
html + "<td><input type='text' name='maxvalue' style='background-color:pink' disabled='disabled' onkeypress='return filter_real(event,this,8,4)'></td></tr>";
                    } else {
                        html =
html + "<tr ><td><input type='checkbox' name='chkelement' id=" + i +
 " value='" + param[i] + "' onclick='checkParam(this.id)'>";
                        html = html + parameter[i] + "</td>";
                        html =
html + "<td><input type='text' name='minvalue' style='background-color:rgb(255,255,225)' disabled='disabled' onkeypress='return filter_real(event,this,8,4)'></td>";
                        html =
html + "<td><input type='text' name='maxvalue' style='background-color:rgb(255,255,225)' disabled='disabled' onkeypress='return filter_real(event,this,8,4)'></td></tr>";
                    }
                }
                html = html + "</table>";
                System.out.println("html:" + html);
            } catch (Exception e) {
                System.out.println("Region selection error " + e);
                System.out.println("html:" + html);
            }
            out.print(html);
            System.out.println("output.written");
            out.flush();
        } else if (cmd.equalsIgnoreCase("changeDist")) {
            String dcode = request.getParameter("dcode");
            System.out.println("district Code:" + dcode);
            xml = "<response><command>" + cmd + "</command>";
            try {
                System.out.println("inside try");
                ps =
  con.prepareStatement("select distinct LOCATION from WQS_SAMPLERESULT where SOURCE_DIST_NAME=? and LOCATION is not null");
                ps.setString(1, dcode);
                rs = ps.executeQuery();
                while (rs.next()) {
                    xml = xml + "<count>";
                    lcode = rs.getString("LOCATION");
                    xml = xml + "<lcode>" + lcode + "</lcode>";
                    xml = xml + "</count>";
                    cnt = cnt + 1;
                }
                if (cnt > 0) {
                    System.out.println("Success");
                    xml = xml + "<flag>Success</flag>";
                } else {
                    System.out.println("Failure");
                    xml = xml + "<flag>Failure</flag>";
                }
            } catch (Exception e) {
                System.out.println("Err in changeDist:" + e.getMessage());
            }
            xml = xml + "</response>";
            System.out.println("xml:" + xml);
            out.println(xml);
        }
        out.close();
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        response.setContentType(CONTENT_TYPE);
        System.out.println("Welcome to post servlet");
        //response.setHeader("cache-control","no-cache");
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        PreparedStatement ps = null, ps1 = null;
        JasperPrint jasperPrint = null;
        String val = null, bcode = "", hcode = "", pcode = "", loc = "";
        Date dt = null;
        float fval = 0;
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
        JasperDesign jasperDesign = null;
        File reportFile = null;
        JasperReport jasperReport = null;
        Map map = null;
        try {
            System.out.println("calling servlet");
            String cmd = request.getParameter("command");
            System.out.println("command=====>" + cmd);
            /*String lab=request.getParameter("lab");
                        String lb[]=lab.split("--");
                        int lcode=Integer.parseInt(lb[0]);

                        System.out.println("lab=====>"+lcode);*/

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

            String dname = request.getParameter("dname");

            String es[] = request.getParameter("es").split(",");
            System.out.println("Element Symbol Length:" + es.length);

            String max[] = request.getParameter("max").split(",");
            System.out.println("Max Length=====>" + max.length);

            String min[] = request.getParameter("min").split(",");
            System.out.println("Min Length=====>" + min.length);
            try {
                st = con.createStatement();
                st.executeUpdate("delete from WQS_REGULAR");
                System.out.println("table deleted");
            } catch (Exception e) {
                System.out.println("Err in delete:" + e.getMessage());
            }
            try {
                st = con.createStatement();
                st.executeUpdate("delete from WQS_REGULAR_NONTWAD");
                System.out.println("table deleted");
            } catch (Exception e) {
                System.out.println("Err in delete:" + e.getMessage());
            }
            System.out.println("Element Symbol Length   :" + es.length);
            if (cmd.equalsIgnoreCase("District")) {
                String district[] = dname.split("--");
                System.out.println("district=====>" + district[1]);
                for (int i = 0; i < es.length; i++) {
                    String es1 = es[i];
                    System.out.println("element symbol:" + es1);
                    float max1 = Float.parseFloat(max[i]);
                    float min1 = Float.parseFloat(min[i]);
                    System.out.println("max1  :" + max1);
                    System.out.println("min1  :" + min1);
                    System.out.println("Query   :" + "select " + es1 +
                                       " as f1,sample_collection_date,resurvey_blk_code,resurvey_pan_code,resurvey_hab_code,location from wqs_sampleresult where (sample_collection_date between to_date('" +
                                       f + "','dd/MM/yyyy') and to_date('" +
                                       t +
                                       "','dd/MM/yyyy')) and  resurvey_dist_code='" +
                                       district[0] + "'and " + es1 +
                                       " is not null and " + es1 +
                                       "!='-' and " + es1 + "!='Nil' and " +
                                       es1 + "!='nil' and " + es1 +
                                       "!='None' and " + es1 + "!='none'");
                    ps =
  con.prepareStatement("select " + es1 + " as f1,sample_collection_date,resurvey_blk_code,resurvey_pan_code,resurvey_hab_code,location from wqs_sampleresult where (sample_collection_date between to_date('" +
                       f + "','dd/MM/yyyy') and to_date('" + t +
                       "','dd/MM/yyyy')) and resurvey_dist_code=? and " + es1 +
                       " is not null and " + es1 + "!='-' and " + es1 +
                       "!='Nil' and " + es1 + "!='nil' and " + es1 +
                       "!='None' and " + es1 + "!='none'");
                    // ps.setInt(1,lcode);
                    ps.setString(1, district[0]);
                    rs = ps.executeQuery();
                    System.out.println("field selected");
                    while (rs.next()) {
                        System.out.println("value   :" + rs.getString("f1"));
                        val = rs.getString("f1");
                        fval = Float.parseFloat(val);
                        dt = rs.getDate("sample_collection_date");
                        System.out.println("date  :" + dt);
                        bcode = rs.getString("resurvey_blk_code");
                        pcode = rs.getString("resurvey_pan_code");
                        hcode = rs.getString("resurvey_hab_code");
                        loc = rs.getString("location");
                        if (fval >= min1 && fval <= max1) {
                            System.out.println("condition satisfied");
                            ps1 =
 con.prepareStatement("insert into WQS_REGULAR(DISTRICT_CODE,BLOCK_CODE,PANCHAYAT_CODE,HABITATION_CODE,LOCATION,ELEMENT_SYMBOL,ELEMENT_DATE,ELEMENT_VALUE)values(?,?,?,?,?,?,?,?)");
                            ps1.setString(1, district[0]);
                            ps1.setString(2, bcode);
                            ps1.setString(3, pcode);
                            ps1.setString(4, hcode);
                            ps1.setString(5, loc);
                            ps1.setString(6, es1);
                            ps1.setDate(7, dt);
                            ps1.setString(8, val);
                            ps1.executeUpdate();
                            ps1.close();
                        } else {
                            System.out.println("condition not satisfied");
                        }
                    }
                    System.out.println(i +
                                       "=======================================================");
                    ps.close();
                    //ps1.close();
                }
                System.out.println("path:" +
                                   request.getRealPath("/WEB-INF/ReportSrc/WQS_District_RegularReport.jasper"));
                reportFile =
                        new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_District_RegularReport.jasper"));
                System.out.println("after path");

                if (!reportFile.exists()) {
                    System.out.println("does not exsist");
                    throw new JRRuntimeException("File J not found. The report design must be compiled first.");
                }
                System.out.println(JRLoader.loadObject(reportFile.getPath()));
                jasperReport =
                        (JasperReport)JRLoader.loadObject(reportFile.getPath());
                map = new HashMap();
                // map.put("lab",lb[1]);
                map.put("fromdt", dtfrom);
                map.put("todt", dtto);
                map.put("district", district[0]);
            } else if (cmd.equalsIgnoreCase("Block")) {
                String district[] = dname.split("--");
                System.out.println("district=====>" + district[1]);
                String bname = request.getParameter("block");
                String block[] = bname.split("--");
                System.out.println("block=====>" + block[1]);

                for (int i = 0; i < es.length; i++) {
                    String es1 = es[i];
                    float max1 = Float.parseFloat(max[i]);
                    float min1 = Float.parseFloat(min[i]);
                    System.out.println("max1  :" + max1);
                    System.out.println("min1  :" + min1);
                    System.out.println(i +
                                       "=======================================================");
                    System.out.println("Query   :" + "select " + es1 +
                                       " as f1,sample_collection_date,resurvey_pan_code,resurvey_hab_code,location from wqs_sampleresult where (sample_collection_date between to_date('" +
                                       f + "','dd/MM/yyyy') and to_date('" +
                                       t +
                                       "','dd/MM/yyyy')) and  resurvey_dist_code='" +
                                       district[0] +
                                       "' and resurvey_blk_code='" + block[0] +
                                       "' and " + es1 + " is not null and " +
                                       es1 + "!='-' and " + es1 +
                                       "!='Nil' and " + es1 + "!='None' and " +
                                       es1 + "!='nil' and " + es1 +
                                       "!='none'");
                    ps =
  con.prepareStatement("select " + es1 + " as f1,sample_collection_date,resurvey_pan_code,resurvey_hab_code,location from wqs_sampleresult where (sample_collection_date between to_date('" +
                       f + "','dd/MM/yyyy') and to_date('" + t +
                       "','dd/MM/yyyy')) and resurvey_dist_code=? and resurvey_blk_code=? and " +
                       es1 + " is not null and " + es1 + "!='-' and " + es1 +
                       "!='Nil' and " + es1 + "!='None' and " + es1 +
                       "!='none' and " + es1 + "!='nil'");
                    // ps.setInt(1,lcode);
                    ps.setString(1, district[0]);
                    ps.setString(2, block[0]);
                    rs = ps.executeQuery();
                    System.out.println("field selected");
                    while (rs.next()) {
                        System.out.println("value   :" + rs.getString("f1"));
                        val = rs.getString("f1");
                        fval = Float.parseFloat(val);
                        dt = rs.getDate("sample_collection_date");
                        System.out.println("date  :" + dt);
                        pcode = rs.getString("resurvey_pan_code");
                        hcode = rs.getString("resurvey_hab_code");
                        loc = rs.getString("location");
                        if (fval >= min1 && fval <= max1) {
                            System.out.println("value is equal");
                            ps1 =
 con.prepareStatement("insert into WQS_REGULAR(DISTRICT_CODE,BLOCK_CODE,PANCHAYAT_CODE,HABITATION_CODE,LOCATION,ELEMENT_SYMBOL,ELEMENT_DATE,ELEMENT_VALUE)values(?,?,?,?,?,?,?,?)");
                            ps1.setString(1, district[0]);
                            ps1.setString(2, block[0]);
                            ps1.setString(3, pcode);
                            ps1.setString(4, hcode);
                            ps1.setString(5, loc);
                            ps1.setString(6, es1);
                            ps1.setDate(7, dt);
                            ps1.setString(8, val);
                            ps1.executeUpdate();
                            ps1.close();
                        } else {
                            System.out.println("value is not equal");
                        }
                    }
                    System.out.println(i +
                                       "=======================================================");
                    ps.close();
                    // ps1.close();
                }
                System.out.println(request.getRealPath("/WEB-INF/ReportSrc/WQS_Block_RegularReport.jasper"));
                reportFile =
                        new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_Block_RegularReport.jasper"));
                System.out.println("after path");

                if (!reportFile.exists()) {
                    System.out.println("does not exsist");
                    throw new JRRuntimeException("File J not found. The report design must be compiled first.");
                }
                System.out.println(JRLoader.loadObject(reportFile.getPath()));
                jasperReport =
                        (JasperReport)JRLoader.loadObject(reportFile.getPath());
                map = new HashMap();
                // map.put("lab",lb[1]);
                map.put("fromdt", dtfrom);
                map.put("todt", dtto);
                map.put("district", district[0]);
                map.put("block", block[0]);
            } else if (cmd.equalsIgnoreCase("Panchayat")) {
                String district[] = dname.split("--");
                System.out.println("district=====>" + district[1]);
                String bname = request.getParameter("block");
                String block[] = bname.split("--");
                System.out.println("block=====>" + block[1]);
                String Pname = request.getParameter("Panchayat");
                String Panchayat[] = Pname.split("--");
                System.out.println("Panchayat=====>" + Panchayat[1]);

                for (int i = 0; i < es.length; i++) {
                    String es1 = es[i];
                    float max1 = Float.parseFloat(max[i]);
                    float min1 = Float.parseFloat(min[i]);
                    System.out.println("max1  :" + max1);
                    System.out.println("min1  :" + min1);
                    System.out.println(i +
                                       "=======================================================");
                    System.out.println("Query   :" + "select " + es1 +
                                       " as f1,sample_collection_date,resurvey_hab_code,location from wqs_sampleresult where (sample_collection_date between to_date('" +
                                       f + "','dd/MM/yyyy') and to_date('" +
                                       t +
                                       "','dd/MM/yyyy')) and  resurvey_dist_code='" +
                                       district[0] +
                                       "' and resurvey_blk_code='" + block[0] +
                                       "' and resurvey_pan_code='" +
                                       Panchayat[0] + "' and " + es1 +
                                       " is not null and " + es1 +
                                       "!='-' and " + es1 + "!='Nil' and " +
                                       es1 + "!='NIL' and " + es1 + "!='nil'");
                    ps =
  con.prepareStatement("select " + es1 + " as f1,sample_collection_date,resurvey_hab_code,location from wqs_sampleresult where (sample_collection_date between to_date('" +
                       f + "','dd/MM/yyyy') and to_date('" + t +
                       "','dd/MM/yyyy')) and  resurvey_dist_code=? and resurvey_blk_code=? and resurvey_pan_code=? and " +
                       es1 + " is not null and " + es1 + "!='-' and " + es1 +
                       "!='Nil' and " + es1 + "!='NIL' and " + es1 +
                       "!='nil'");
                    // ps.setInt(1,lcode);
                    ps.setString(1, district[0]);
                    ps.setString(2, block[0]);
                    ps.setString(3, Panchayat[0]);
                    rs = ps.executeQuery();
                    System.out.println("field selected");
                    while (rs.next()) {
                        System.out.println("value   :" + rs.getString("f1"));
                        val = rs.getString("f1");
                        fval = Float.parseFloat(val);
                        dt = rs.getDate("sample_collection_date");
                        System.out.println("date  :" + dt);
                        hcode = rs.getString("resurvey_hab_code");
                        loc = rs.getString("location");

                        if (fval >= min1 && fval <= max1) {
                            System.out.println("value is equal");
                            ps1 =
 con.prepareStatement("insert into WQS_REGULAR(DISTRICT_CODE,BLOCK_CODE,PANCHAYAT_CODE,HABITATION_CODE,LOCATION,ELEMENT_SYMBOL,ELEMENT_DATE,ELEMENT_VALUE)values(?,?,?,?,?,?,?,?)");
                            ps1.setString(1, district[0]);
                            ps1.setString(2, block[0]);
                            ps1.setString(3, Panchayat[0]);
                            ps1.setString(4, hcode);
                            ps1.setString(5, loc);
                            ps1.setString(6, es1);
                            ps1.setDate(7, dt);
                            ps1.setString(8, val);
                            ps1.executeUpdate();
                            ps1.close();
                        } else {
                            System.out.println("value is not equal");
                        }
                    }
                    System.out.println(i +
                                       "=======================================================");
                    ps.close();
                    // ps1.close();
                }
                System.out.println(request.getRealPath("/WEB-INF/ReportSrc/WQS_Panchayat_RegularReport.jasper"));
                reportFile =
                        new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_Panchayat_RegularReport.jasper"));
                System.out.println("after path");

                if (!reportFile.exists()) {
                    System.out.println("does not exsist");
                    throw new JRRuntimeException("File J not found. The report design must be compiled first.");
                }
                System.out.println(JRLoader.loadObject(reportFile.getPath()));
                jasperReport =
                        (JasperReport)JRLoader.loadObject(reportFile.getPath());
                map = new HashMap();
                //map.put("lab",lb[1]);
                map.put("fromdt", dtfrom);
                map.put("todt", dtto);
                map.put("district", district[0]);
                map.put("block", block[0]);
                map.put("panchayat", Panchayat[0]);
            } else if (cmd.equalsIgnoreCase("Habitation")) {
                String district[] = dname.split("--");
                System.out.println("district=====>" + district[1]);
                String bname = request.getParameter("block");
                String block[] = bname.split("--");
                System.out.println("block=====>" + block[1]);
                String Pname = request.getParameter("Panchayat");
                String Panchayat[] = Pname.split("--");
                System.out.println("Panchayat=====>" + Panchayat[1]);
                String hname = request.getParameter("Habitation");
                String Habitation[] = hname.split("--");
                System.out.println("Habitation=====>" + Habitation[1]);
                for (int i = 0; i < es.length; i++) {
                    String es1 = es[i];
                    float max1 = Float.parseFloat(max[i]);
                    float min1 = Float.parseFloat(min[i]);
                    System.out.println("max1  :" + max1);
                    System.out.println("min1  :" + min1);
                    System.out.println(i +
                                       "=======================================================");
                    System.out.println("Query   :" + "select " + es1 +
                                       " as f1,sample_collection_date,location from wqs_sampleresult where (sample_collection_date between to_date('" +
                                       f + "','dd/MM/yyyy') and to_date('" +
                                       t +
                                       "','dd/MM/yyyy')) and  resurvey_dist_code='" +
                                       district[0] +
                                       "' and resurvey_blk_code='" + block[0] +
                                       "' and resurvey_pan_code='" +
                                       Panchayat[0] +
                                       "' and resurvey_hab_code='" +
                                       Habitation[0] + "' and " + es1 +
                                       " is not null and " + es1 +
                                       "!='-' and " + es1 + "!='Nil' and " +
                                       es1 + "!='NIL' and " + es1 + "!='nil'");
                    ps =
  con.prepareStatement("select " + es1 + " as f1,sample_collection_date,location from wqs_sampleresult where (sample_collection_date between to_date('" +
                       f + "','dd/MM/yyyy') and to_date('" + t +
                       "','dd/MM/yyyy')) and  resurvey_dist_code=? and resurvey_blk_code=? and resurvey_pan_code=? and resurvey_hab_code=? and " +
                       es1 + " is not null and " + es1 + "!='-' and " + es1 +
                       "!='Nil' and " + es1 + "!='NIL' and " + es1 +
                       "!='nil'");
                    //ps.setInt(1,lcode);
                    ps.setString(1, district[0]);
                    ps.setString(2, block[0]);
                    ps.setString(3, Panchayat[0]);
                    ps.setString(4, Habitation[0]);
                    rs = ps.executeQuery();
                    System.out.println("field selected");
                    while (rs.next()) {
                        System.out.println("value   :" + rs.getString("f1"));
                        val = rs.getString("f1");
                        fval = Float.parseFloat(val);
                        dt = rs.getDate("sample_collection_date");
                        System.out.println("date  :" + dt);
                        loc = rs.getString("location");
                        if (fval >= min1 && fval <= max1) {
                            System.out.println("value is equal");
                            ps1 =
 con.prepareStatement("insert into WQS_REGULAR(DISTRICT_CODE,BLOCK_CODE,PANCHAYAT_CODE,HABITATION_CODE,LOCATION,ELEMENT_SYMBOL,ELEMENT_DATE,ELEMENT_VALUE)values(?,?,?,?,?,?,?,?)");
                            ps1.setString(1, district[0]);
                            ps1.setString(2, block[0]);
                            ps1.setString(3, Panchayat[0]);
                            ps1.setString(4, Habitation[0]);
                            ps1.setString(5, loc);
                            ps1.setString(6, es1);
                            ps1.setDate(7, dt);
                            ps1.setString(8, val);
                            ps1.executeUpdate();
                            ps1.close();
                        } else {
                            System.out.println("value is not equal");
                        }
                    }
                    System.out.println(i +
                                       "=======================================================");
                    ps.close();
                    // ps1.close();
                }
                System.out.println(request.getRealPath("/WEB-INF/ReportSrc/WQS_Habitation_RegularReport.jasper"));
                reportFile =
                        new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_Habitation_RegularReport.jasper"));
                System.out.println("after path");

                if (!reportFile.exists()) {
                    System.out.println("does not exsist");
                    throw new JRRuntimeException("File J not found. The report design must be compiled first.");
                }
                System.out.println(JRLoader.loadObject(reportFile.getPath()));
                jasperReport =
                        (JasperReport)JRLoader.loadObject(reportFile.getPath());
                map = new HashMap();
                //map.put("lab",lb[1]);
                map.put("fromdt", dtfrom);
                map.put("todt", dtto);
                map.put("district", district[0]);
                map.put("block", block[0]);
                map.put("panchayat", Panchayat[0]);
                map.put("habitation", Habitation[0]);
            } else if (cmd.equalsIgnoreCase("Location")) {
                String district[] = dname.split("--");
                System.out.println("district=====>" + district[1]);
                String bname = request.getParameter("block");
                String block[] = bname.split("--");
                System.out.println("block=====>" + block[1]);
                String Pname = request.getParameter("Panchayat");
                String Panchayat[] = Pname.split("--");
                System.out.println("Panchayat=====>" + Panchayat[1]);
                String hname = request.getParameter("Habitation");
                String Habitation[] = hname.split("--");
                System.out.println("Habitation=====>" + Habitation[1]);
                String location = request.getParameter("Location");
                System.out.println("location=====>" + location);

                for (int i = 0; i < es.length; i++) {
                    String es1 = es[i];
                    System.out.println("Selected Element Symbol is :" + i +
                                       "------>" + es1);
                    float max1 = Float.parseFloat(max[i]);
                    float min1 = Float.parseFloat(min[i]);
                    System.out.println("max1  :" + max1);
                    System.out.println("min1  :" + min1);
                    System.out.println("Element Symbol:" + es[i]);
                    System.out.println(i +
                                       "=======================================================");
                    try {
                        System.out.println("Query   :" + "select " + es1 +
                                           " as f1,sample_collection_date from wqs_sampleresult where (sample_collection_date between to_date('" +
                                           f +
                                           "','dd/MM/yyyy') and to_date('" +
                                           t +
                                           "','dd/MM/yyyy')) and  resurvey_dist_code='" +
                                           district[0] +
                                           "' and resurvey_blk_code='" +
                                           block[0] +
                                           "' and resurvey_pan_code='" +
                                           Panchayat[0] +
                                           "' and resurvey_hab_code='" +
                                           Habitation[0] + "' and location='" +
                                           location + "'and " + es1 +
                                           " is not null and " + es1 +
                                           "!='-' and " + es1 +
                                           "!='Nil' and " + es1 +
                                           "!='NIL' and " + es1 + "!='nil'");
                        ps =
  con.prepareStatement("select " + es1 + " as f1,sample_collection_date from wqs_sampleresult where (sample_collection_date between to_date('" +
                       f + "','dd/MM/yyyy') and to_date('" + t +
                       "','dd/MM/yyyy')) and resurvey_dist_code=? and resurvey_blk_code=? and resurvey_pan_code=? and resurvey_hab_code=? and location=? and " +
                       es1 + " is not null and " + es1 + "!='-' and " + es1 +
                       "!='Nil' and " + es1 + "!='None'");
                        //ps.setInt(1,lcode);
                        ps.setString(1, district[0]);
                        ps.setString(2, block[0]);
                        ps.setString(3, Panchayat[0]);
                        ps.setString(4, Habitation[0]);
                        ps.setString(5, location);
                        rs = ps.executeQuery();
                        System.out.println("field selected");
                        while (rs.next()) {
                            System.out.println("value   :" +
                                               rs.getString("f1"));
                            val = rs.getString("f1");
                            fval = Float.parseFloat(val);
                            dt = rs.getDate("sample_collection_date");
                            System.out.println("date  :" + dt);
                            if (fval >= min1 && fval <= max1) {
                                System.out.println("value is equal");
                                ps1 =
 con.prepareStatement("insert into WQS_REGULAR(DISTRICT_CODE,BLOCK_CODE,PANCHAYAT_CODE,HABITATION_CODE,LOCATION,ELEMENT_SYMBOL,ELEMENT_DATE,ELEMENT_VALUE)values(?,?,?,?,?,?,?,?)");
                                ps1.setString(1, district[0]);
                                ps1.setString(2, block[0]);
                                ps1.setString(3, Panchayat[0]);
                                ps1.setString(4, Habitation[0]);
                                ps1.setString(5, location);
                                ps1.setString(6, es1);
                                ps1.setDate(7, dt);
                                ps1.setString(8, val);
                                ps1.executeUpdate();
                                ps1.close();
                            } else {
                                System.out.println("value is not equal");
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Err in district parameter value selection  :" +
                                           e.getMessage());
                    }
                    System.out.println(i +
                                       "=======================================================");
                    ps.close();
                    // ps1.close();
                }
                System.out.println(request.getRealPath("/WEB-INF/ReportSrc/WQS_Location_RegularReport.jasper"));
                reportFile =
                        new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_Location_RegularReport.jasper"));
                System.out.println("after path");

                if (!reportFile.exists()) {
                    System.out.println("does not exsist");
                    throw new JRRuntimeException("File J not found. The report design must be compiled first.");
                }
                System.out.println(JRLoader.loadObject(reportFile.getPath()));
                jasperReport =
                        (JasperReport)JRLoader.loadObject(reportFile.getPath());
                map = new HashMap();
                // map.put("lab",lb[1]);
                map.put("fromdt", dtfrom);
                map.put("todt", dtto);
                map.put("district", district[0]);
                map.put("block", block[0]);
                map.put("panchayat", Panchayat[0]);
                map.put("habitation", Habitation[0]);
                map.put("location", location);
            } else if (cmd.equalsIgnoreCase("SourceDistrict")) {
                System.out.println("district=====>" + dname);
                for (int i = 0; i < es.length; i++) {
                    String es1 = es[i];
                    System.out.println(i +
                                       "=======================================================");
                    float max1 = Float.parseFloat(max[i]);
                    float min1 = Float.parseFloat(min[i]);
                    System.out.println("max1  :" + max1);
                    System.out.println("min1  :" + min1);
                    System.out.println("Query   :" + "select " + es1 +
                                       " as f1,sample_collection_date,location from wqs_sampleresult where (sample_collection_date between to_date('" +
                                       f + "','dd/MM/yyyy') and to_date('" +
                                       t +
                                       "','dd/MM/yyyy')) and  source_dist_name='" +
                                       dname + "'and " + es1 +
                                       " is not null and " + es1 +
                                       "!='-' and " + es1 + "!='Nil' and " +
                                       es1 + "!='nil' and " + es1 +
                                       "!='None' and " + es1 + "!='none'");
                    ps =
  con.prepareStatement("select " + es1 + " as f1,sample_collection_date,location from wqs_sampleresult where (sample_collection_date between to_date('" +
                       f + "','dd/MM/yyyy') and to_date('" + t +
                       "','dd/MM/yyyy')) and source_dist_name=? and " + es1 +
                       " is not null and " + es1 + "!='-' and " + es1 +
                       "!='Nil' and " + es1 + "!='nil' and " + es1 +
                       "!='None' and " + es1 + "!='none'");
                    // ps.setInt(1,lcode);
                    ps.setString(1, dname);
                    rs = ps.executeQuery();
                    System.out.println("field selected");
                    while (rs.next()) {
                        System.out.println("value   :" + rs.getString("f1"));
                        val = rs.getString("f1");
                        fval = Float.parseFloat(val);
                        dt = rs.getDate("sample_collection_date");
                        System.out.println("date  :" + dt);
                        loc = rs.getString("location");
                        if (fval >= min1 && fval <= max1) {
                            ps1 =
 con.prepareStatement("insert into WQS_REGULAR_NONTWAD(DISTRICT_NAME,LOCATION,ELEMENT_SYMBOL,ELEMENT_DATE,ELEMENT_VALUE)values(?,?,?,?,?)");
                            ps1.setString(1, dname);
                            ps1.setString(2, loc);
                            ps1.setString(3, es1);
                            ps1.setDate(4, dt);
                            ps1.setString(5, val);
                            ps1.executeUpdate();
                            ps1.close();
                        }
                    }
                    System.out.println(i +
                                       "=======================================================");
                    ps.close();
                    // ps1.close();
                }
                System.out.println("path:" +
                                   request.getRealPath("/WEB-INF/ReportSrc/WQS_District_Regular_NonTwad.jasper"));
                reportFile =
                        new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_District_Regular_NonTwad.jasper"));
                System.out.println("after path");

                if (!reportFile.exists()) {
                    System.out.println("does not exsist");
                    throw new JRRuntimeException("File J not found. The report design must be compiled first.");
                }
                System.out.println(JRLoader.loadObject(reportFile.getPath()));
                jasperReport =
                        (JasperReport)JRLoader.loadObject(reportFile.getPath());
                map = new HashMap();
                // map.put("lab",lb[1]);
                map.put("fromdt", dtfrom);
                map.put("todt", dtto);
                map.put("district", dname);
            } else if (cmd.equalsIgnoreCase("SourceLocation")) {
                System.out.println("district=====>" + dname);
                loc = request.getParameter("Location");
                System.out.println("Location is:" + loc);
                for (int i = 0; i < es.length; i++) {
                    String es1 = es[i];
                    System.out.println(i +
                                       "=======================================================");
                    float max1 = Float.parseFloat(max[i]);
                    float min1 = Float.parseFloat(min[i]);
                    System.out.println("max1  :" + max1);
                    System.out.println("min1  :" + min1);
                    System.out.println("Query   :" + "select " + es1 +
                                       " as f1,sample_collection_date from wqs_sampleresult where (sample_collection_date between to_date('" +
                                       f + "','dd/MM/yyyy') and to_date('" +
                                       t +
                                       "','dd/MM/yyyy')) and  source_dist_name='" +
                                       dname + "'and location='" + loc +
                                       "' and " + es1 + " is not null and " +
                                       es1 + "!='nil' and " + es1 +
                                       "!='Nil' and " + es1 + "!='none' and " +
                                       es1 + "!='None'");
                    ps =
  con.prepareStatement("select " + es1 + " as f1,sample_collection_date from wqs_sampleresult where (sample_collection_date between to_date('" +
                       f + "','dd/MM/yyyy') and to_date('" + t +
                       "','dd/MM/yyyy')) and source_dist_name=? and location=? and " +
                       es1 + " is not null and " + es1 + "!='nil' and " + es1 +
                       "!='Nil' and " + es1 + "!='none' and " + es1 +
                       "!='None'");
                    // ps.setInt(1,lcode);
                    ps.setString(1, dname);
                    ps.setString(2, loc);
                    rs = ps.executeQuery();
                    System.out.println("field selected");
                    while (rs.next()) {
                        System.out.println("value   :" + rs.getString("f1"));
                        val = rs.getString("f1");
                        fval = Float.parseFloat(val);
                        dt = rs.getDate("sample_collection_date");
                        System.out.println("date  :" + dt);
                        if (fval >= min1 && fval <= max1) {
                            ps1 =
 con.prepareStatement("insert into WQS_REGULAR_NONTWAD(DISTRICT_NAME,LOCATION,ELEMENT_SYMBOL,ELEMENT_DATE,ELEMENT_VALUE)values(?,?,?,?,?)");
                            ps1.setString(1, dname);
                            ps1.setString(2, loc);
                            ps1.setString(3, es1);
                            ps1.setDate(4, dt);
                            ps1.setString(5, val);
                            ps1.executeUpdate();
                            ps1.close();
                        }

                    }
                    System.out.println(i +
                                       "=======================================================");
                    ps.close();
                    //ps1.close();
                }
                System.out.println("path:" +
                                   request.getRealPath("/WEB-INF/ReportSrc/WQS_Location_Regular_NonTwad.jasper"));
                reportFile =
                        new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_Location_Regular_NonTwad.jasper"));
                System.out.println("after path");

                if (!reportFile.exists()) {
                    System.out.println("does not exsist");
                    throw new JRRuntimeException("File J not found. The report design must be compiled first.");
                }
                System.out.println(JRLoader.loadObject(reportFile.getPath()));
                jasperReport =
                        (JasperReport)JRLoader.loadObject(reportFile.getPath());
                map = new HashMap();
                // map.put("lab",lb[1]);
                map.put("fromdt", dtfrom);
                map.put("todt", dtto);
                map.put("district", dname);
                map.put("location", loc);
            }
            jasperPrint = JasperFillManager.fillReport(jasperReport, map, con);

            String rtype = request.getParameter("cmbReportType");
            if (rtype.equalsIgnoreCase("HTML")) {
                response.setContentType("text/html");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"WQS_Periodic_SampleResultRep.html\"");
                PrintWriter out = response.getWriter();
                JRHtmlExporter exporter = new JRHtmlExporter();
                exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,
                                      false);
                exporter.setParameter(JRExporterParameter.JASPER_PRINT,
                                      jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
                exporter.exportReport();
                out.flush();
                out.close();
            } else if (rtype.equalsIgnoreCase("PDF")) {
                byte buf[] =
                    JasperExportManager.exportReportToPdf(jasperPrint);
                response.setContentType("application/pdf");
                response.setContentLength(buf.length);
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"WQS_Periodic_SampleResultRep.pdf\"");
                OutputStream out = response.getOutputStream();
                out.write(buf, 0, buf.length);
                out.close();
            } else if (rtype.equalsIgnoreCase("EXCEL")) {
                System.out.println("test1");
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"WQS_Periodic_SampleResultRep.xls\"");
                JRXlsExporter exporterXLS = new JRXlsExporter();
                exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT,
                                         jasperPrint);
                System.out.println("test2");
                ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
                exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,
                                         xlsReport);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
                                         Boolean.FALSE);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE,
                                         Boolean.TRUE);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,
                                         Boolean.FALSE);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
                                         Boolean.TRUE);
                exporterXLS.exportReport();
                System.out.println("test3");
                byte[] bytes;
                bytes = xlsReport.toByteArray();
                ServletOutputStream ouputStream = response.getOutputStream();
                ouputStream.write(bytes, 0, bytes.length);
                System.out.println("test4");
                ouputStream.flush();
                ouputStream.close();
                System.out.println("test5");
            } else if (rtype.equalsIgnoreCase("TXT")) {
                response.setContentType("text/plain");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"WQS_Periodic_SampleResultRep.txt\"");

                JRTextExporter exporter = new JRTextExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT,
                                      jasperPrint);
                ByteArrayOutputStream txtReport = new ByteArrayOutputStream();
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
                                      txtReport);
                exporter.setParameter(JRTextExporterParameter.CHARACTER_WIDTH,
                                      new Integer(200));
                exporter.setParameter(JRTextExporterParameter.CHARACTER_HEIGHT,
                                      new Integer(50));
                exporter.exportReport();

                byte[] bytes;
                bytes = txtReport.toByteArray();
                ServletOutputStream ouputStream = response.getOutputStream();
                ouputStream.write(bytes, 0, bytes.length);
                ouputStream.flush();
                ouputStream.close();


            }
        } catch (Exception ex) {
            String connectMsg =
                "Could not create the report " + ex.getMessage() + " " +
                ex.getLocalizedMessage();
            System.out.println(connectMsg);
        }

    }
}
