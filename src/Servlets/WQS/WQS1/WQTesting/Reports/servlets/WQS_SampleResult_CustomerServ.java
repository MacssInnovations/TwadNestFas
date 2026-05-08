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

public class WQS_SampleResult_CustomerServ extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        String CONTENT_TYPE = "text/xml";
        response.setContentType(CONTENT_TYPE);
        response.setHeader("Cache-Control", "no-cache");
        System.out.println("Welcome to get servlet");
        PrintWriter out = response.getWriter();
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        String xml = null, bcode = null, desc = null, pcode = null, hcode =
            null, lcode = null;
        int cnt = 0;

        try {
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
        String cmd = request.getParameter("command");
        String dcode = request.getParameter("dcode");
        System.out.println("district Code:" + dcode);
        if (cmd.equalsIgnoreCase("changeDistrict")) {
            xml = "<response><command>" + cmd + "</command>";
            try {
                System.out.println("inside try");
                ps =
  con.prepareStatement("select * from(select distinct RESURVEY_BLK_CODE,RESURVEY_DIST_CODE from WQS_SAMPLERESULT where CUSTOMER_TYPE=? and RESURVEY_DIST_CODE=?)a inner join(select STATECODE,DISTRICT_CODE,BLOCKCODE,BLOCKNAME from COM_MST_BLOCKS where STATECODE=(select STATE_CODE from COM_MST_STATE where STATE_NAME=?))b on a.RESURVEY_DIST_CODE=b.DISTRICT_CODE and a.RESURVEY_BLK_CODE=b.BLOCKCODE");
                ps.setString(1, "Twad");
                ps.setString(2, dcode);
                ps.setString(3, "TAMIL NADU");
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
        }
        if (cmd.equalsIgnoreCase("changeBlock")) {
            bcode = request.getParameter("bcode");
            System.out.println("block code:" + bcode);
            xml = "<response><command>" + cmd + "</command>";
            try {
                System.out.println("inside try");
                ps =
  con.prepareStatement("select * from(select distinct RESURVEY_PAN_CODE,RESURVEY_BLK_CODE,RESURVEY_DIST_CODE from WQS_SAMPLERESULT where CUSTOMER_TYPE=? and RESURVEY_DIST_CODE=? and RESURVEY_BLK_CODE=?)a inner join(select STATE_CODE,DISTRICT_CODE,BLOCK_CODE,PANCH_CODE,PANCHAYATNAME from COM_MST_PANCHAYATS where STATE_CODE=(select STATE_CODE from COM_MST_STATE where STATE_NAME=?))b on a.RESURVEY_DIST_CODE=b.DISTRICT_CODE and a.RESURVEY_BLK_CODE=b.BLOCK_CODE and a.RESURVEY_PAN_CODE=b.PANCH_CODE");
                ps.setString(1, "Twad");
                ps.setString(2, dcode);
                ps.setString(3, bcode);
                ps.setString(4, "TAMIL NADU");
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
        }
        if (cmd.equalsIgnoreCase("changePanchayat")) {
            bcode = request.getParameter("bcode");
            pcode = request.getParameter("pcode");
            System.out.println("block code:" + bcode + "pan code:" + pcode);
            xml = "<response><command>" + cmd + "</command>";
            try {
                System.out.println("inside try");
                ps =
  con.prepareStatement("select * from(select distinct RESURVEY_HAB_CODE,RESURVEY_PAN_CODE,RESURVEY_BLK_CODE,RESURVEY_DIST_CODE from WQS_SAMPLERESULT where CUSTOMER_TYPE=? and RESURVEY_DIST_CODE=? and RESURVEY_BLK_CODE=? and RESURVEY_PAN_CODE=?)a inner join(select STATE_CODE,DISTRICT_CODE,BLOCK_CODE,PANCH_CODE,HAB_CODE,NAME from COM_MST_HABITATIONS where STATE_CODE=(select STATE_CODE from COM_MST_STATE where STATE_NAME=?))b on a.RESURVEY_DIST_CODE=b.DISTRICT_CODE and a.RESURVEY_BLK_CODE=b.BLOCK_CODE and a.RESURVEY_PAN_CODE=b.PANCH_CODE and a.RESURVEY_HAB_CODE=b.HAB_CODE");
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
        }
        if (cmd.equalsIgnoreCase("changeHabitation")) {
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
        }
        if (cmd.equalsIgnoreCase("element")) {
            CONTENT_TYPE = "text/html";
            String html = "";
            try {
                st = con.createStatement();
                rs =
  st.executeQuery("Select ELEMENT_SYMBOL from WQS_ELEMENT_SYMBOL");
                int count = 0;
                html =
"<table cellpadding='1%' cellspacing='1%' border='0' width='100%'>";
                boolean bool = false;
                while (rs.next()) {
                    if (bool = !bool) {
                        html =
html + "<tr bgcolor='pink'><td><input type='checkbox' name='chkelement' value='" +
 rs.getString("ELEMENT_SYMBOL") + "'></input></td>";
                        html =
html + "<td>" + rs.getString("ELEMENT_SYMBOL") + "</td></tr>";
                    } else {
                        html =
html + "<tr ><td><input type='checkbox' name='chkelement' value='" +
 rs.getString("ELEMENT_SYMBOL") + "'></input></td>";
                        html =
html + "<td>" + rs.getString("ELEMENT_SYMBOL") + "</td></tr>";
                    }
                    count++;
                }
                if (count == 0) {
                    html = "There is no Region";
                } else {
                    html =
html + "<tr ><td colspan='2'><a href='javascript:elementSelectAll()'>Select All</a>&nbsp;&nbsp;&nbsp;<a href='javascript:elementclose()'>Close</a></td></tr>";
                }
                html = html + "</table>";
                System.out.println("html:" + html);
                out.println(html);
            } catch (Exception e) {
                System.out.println("Region selection error " + e);
                System.out.println("html:" + html);
            }
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
        ResultSet rs = null;
        PreparedStatement ps = null, ps1 = null;
        JasperPrint jasperPrint = null;
        String bis_des = null, bis_max = null, cphe_des = null, cphe_max =
            null, pract_des = null, pract_max = null, who_des = null, who_max =
            null;

        String flag = null;
        try {
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
        try {
            System.out.println("calling servlet");
            String lab = request.getParameter("lab");
            String lb[] = lab.split("--");
            int lcode = Integer.parseInt(lb[0]);

            System.out.println("lab=====>" + lcode);

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
            String es[] = request.getParameter("es").split(",");
            try {
                st = con.createStatement();
                st.executeUpdate("delete from WQS_NEWTABLE1");
                System.out.println("table deleted");
            } catch (Exception e) {
                System.out.println("Err in delete:" + e.getMessage());
            }
            System.out.println("Element Symbol Length:" + es.length);
            for (int i = 0; i < es.length; i++) {
                System.out.println("row-------------------------------->" + i);
                String es1 = es[i];
                System.out.println("Element Symbol:" + es1);
                System.out.println("Query---------->:" + "select " + es1 +
                                   " as f1,sample_collection_date from wqs_sampleresult where lab_code='" +
                                   lcode +
                                   "' and (sample_collection_date between to_date('" +
                                   f + "','dd/MM/yyyy') and to_date('" + t +
                                   "','dd/MM/yyyy')) and  resurvey_dist_code='" +
                                   district[0] + "' and resurvey_blk_code='" +
                                   block[0] + "' and resurvey_pan_code='" +
                                   Panchayat[0] + "' and resurvey_hab_code='" +
                                   Habitation[0] + "' and location='" +
                                   location + "'");
                ps =
  con.prepareStatement("select " + es1 + " as f1,sample_collection_date from wqs_sampleresult where lab_code=? and (sample_collection_date between to_date('" +
                       f + "','dd/MM/yyyy') and to_date('" + t +
                       "','dd/MM/yyyy')) and resurvey_dist_code=? and resurvey_blk_code=? and resurvey_pan_code=? and resurvey_hab_code=? and location=?");
                ps.setInt(1, lcode);
                ps.setString(2, district[0]);
                ps.setString(3, block[0]);
                ps.setString(4, Panchayat[0]);
                ps.setString(5, Habitation[0]);
                ps.setString(6, location);
                rs = ps.executeQuery();
                System.out.println("field selected");
                while (rs.next()) {
                    System.out.println("value +" + rs.getString("f1"));
                    String val = rs.getString("f1");
                    Date dt = rs.getDate("sample_collection_date");
                    System.out.println("date :" + dt);

                    try {
                        ps =
  con.prepareStatement("select STD_NONSTD from WQS_ELEMENT_SYMBOL where ELEMENT_SYMBOL=?");
                        ps.setString(1, es1);
                        rs = ps.executeQuery();
                        if (rs.next()) {
                            flag = rs.getString("STD_NONSTD");
                            System.out.println("Element flag:" + flag);
                        }
                    } catch (Exception e) {
                        System.out.println("Err in select std&nonstd value");
                    }


                    if (flag.equalsIgnoreCase("S")) {
                        try {
                            ps =
  con.prepareStatement("select DESIRABLE_VALUE,MAXIMUM_VALUE from WQS_STD_RESULT where ELEMENT_SYMBOL=? and STANDARD_CODE=?");
                            ps.setString(1, es1);
                            ps.setString(2, "BIS");
                            rs = ps.executeQuery();
                            if (rs.next()) {
                                bis_des = rs.getString("DESIRABLE_VALUE");
                                bis_max = rs.getString("MAXIMUM_VALUE");
                            } else {
                                bis_des = "-";
                                bis_max = "-";
                            }
                            ps.close();
                            ps =
  con.prepareStatement("select DESIRABLE_VALUE,MAXIMUM_VALUE from WQS_STD_RESULT where ELEMENT_SYMBOL=? and STANDARD_CODE=?");
                            ps.setString(1, es1);
                            ps.setString(2, "CPHE");
                            rs = ps.executeQuery();
                            if (rs.next()) {
                                cphe_des = rs.getString("DESIRABLE_VALUE");
                                cphe_max = rs.getString("MAXIMUM_VALUE");
                            } else {
                                cphe_des = "-";
                                cphe_max = "-";
                            }
                            ps =
  con.prepareStatement("select DESIRABLE_VALUE,MAXIMUM_VALUE from WQS_STD_RESULT where ELEMENT_SYMBOL=? and STANDARD_CODE=?");
                            ps.setString(1, es1);
                            ps.setString(2, "WHO");
                            rs = ps.executeQuery();
                            if (rs.next()) {
                                who_des = rs.getString("DESIRABLE_VALUE");
                                who_max = rs.getString("MAXIMUM_VALUE");
                            } else {
                                who_des = "-";
                                who_max = "-";
                            }
                            ps =
  con.prepareStatement("select DESIRABLE_VALUE,MAXIMUM_VALUE from WQS_STD_RESULT where ELEMENT_SYMBOL=? and STANDARD_CODE=?");
                            ps.setString(1, es1);
                            ps.setString(2, "PRACTICAL");
                            rs = ps.executeQuery();
                            if (rs.next()) {
                                pract_des = rs.getString("DESIRABLE_VALUE");
                                pract_max = rs.getString("MAXIMUM_VALUE");
                            } else {
                                pract_des = "-";
                                pract_max = "-";
                            }
                        } catch (Exception e) {
                            System.out.println("Err in standard selection err" +
                                               e.getMessage());
                        }
                    } else if (flag.equalsIgnoreCase("N")) {
                        try {
                            ps =
  con.prepareStatement("select DESIRABLE_VALUE,MAXIMUM_VALUE from WQS_NONSTD_RESULT where ELEMENT_SYMBOL=? and STANDARD_CODE=?");
                            ps.setString(1, es1);
                            ps.setString(2, "BIS");
                            rs = ps.executeQuery();
                            if (rs.next()) {
                                bis_des = rs.getString("DESIRABLE_VALUE");
                                bis_max = rs.getString("MAXIMUM_VALUE");
                            } else {
                                bis_des = "-";
                                bis_max = "-";
                            }
                            ps.close();
                            ps =
  con.prepareStatement("select DESIRABLE_VALUE,MAXIMUM_VALUE from WQS_NONSTD_RESULT where ELEMENT_SYMBOL=? and STANDARD_CODE=?");
                            ps.setString(1, es1);
                            ps.setString(2, "CPHE");
                            rs = ps.executeQuery();
                            if (rs.next()) {
                                cphe_des = rs.getString("DESIRABLE_VALUE");
                                cphe_max = rs.getString("MAXIMUM_VALUE");
                            } else {
                                cphe_des = "-";
                                cphe_max = "-";
                            }
                            ps =
  con.prepareStatement("select DESIRABLE_VALUE,MAXIMUM_VALUE from WQS_NONSTD_RESULT where ELEMENT_SYMBOL=? and STANDARD_CODE=?");
                            ps.setString(1, es1);
                            ps.setString(2, "WHO");
                            rs = ps.executeQuery();
                            if (rs.next()) {
                                who_des = rs.getString("DESIRABLE_VALUE");
                                who_max = rs.getString("MAXIMUM_VALUE");
                            } else {
                                who_des = "-";
                                who_max = "-";
                            }
                            ps =
  con.prepareStatement("select DESIRABLE_VALUE,MAXIMUM_VALUE from WQS_NONSTD_RESULT where ELEMENT_SYMBOL=? and STANDARD_CODE=?");
                            ps.setString(1, es1);
                            ps.setString(2, "PRACTICAL");
                            rs = ps.executeQuery();
                            if (rs.next()) {
                                pract_des = rs.getString("DESIRABLE_VALUE");
                                pract_max = rs.getString("MAXIMUM_VALUE");
                            } else {
                                pract_des = "-";
                                pract_max = "-";
                            }
                        } catch (Exception e) {
                            System.out.println("Err in standard selection err" +
                                               e.getMessage());
                        }
                    }
                    ps1 =
 con.prepareStatement("insert into WQS_NEWTABLE1(ELEMENT_SYMBOL,ELEMENT_DATE,BIS_MAX,BIS_DES,WHO_MAX,WHO_DES,CPHE_MAX,CPHE_DES,PRACT_MAX,PRACT_DES,ELEMENT_RESULT)values(?,?,?,?,?,?,?,?,?,?,?)");
                    ps1.setString(1, es1);
                    System.out.println("es1:" + es1);
                    ps1.setDate(2, dt);
                    System.out.println("dt:" + dt);
                    ps1.setString(3, bis_max);
                    System.out.println("bis_des:" + bis_des);
                    ps1.setString(4, bis_des);
                    System.out.println("bis_des:" + bis_des);
                    ps1.setString(5, who_max);
                    System.out.println("who_max:" + cphe_max);
                    ps1.setString(6, who_des);
                    System.out.println("who_des:" + cphe_des);
                    ps1.setString(7, cphe_max);
                    System.out.println("cphe_max:" + bis_max);
                    ps1.setString(8, cphe_des);
                    System.out.println("cphe_des:" + bis_des);
                    ps1.setString(9, pract_max);
                    System.out.println("pract_max:" + bis_max);
                    ps1.setString(10, pract_des);
                    System.out.println("pract_des:" + bis_des);
                    ps1.setString(11, val);
                    System.out.println("val:" + val);
                    ps1.executeUpdate();
                }
            }
            reportFile =
                    new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_SampleResult_CustomerRep.jasper"));
            System.out.println("after path");

            if (!reportFile.exists()) {
                System.out.println("does not exsist");
                throw new JRRuntimeException("File J not found. The report design must be compiled first.");
            }
            System.out.println(JRLoader.loadObject(reportFile.getPath()));
            JasperReport jasperReport =
                (JasperReport)JRLoader.loadObject(reportFile.getPath());
            Map map = null;
            map = new HashMap();
            map.put("lab", lb[1]);
            map.put("fromdt", dtfrom);
            map.put("todt", dtto);
            map.put("district", district[1]);
            map.put("block", block[1]);
            map.put("Panchayat", Panchayat[1]);
            map.put("Habitation", Habitation[1]);
            map.put("location", location);
            jasperPrint = JasperFillManager.fillReport(jasperReport, map, con);

            String rtype = request.getParameter("cmbReportType");
            if (rtype.equalsIgnoreCase("HTML")) {
                response.setContentType("text/html");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"WQS_SampleResult_CustomerRep.html\"");
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
                                   "attachment;filename=\"WQS_SampleResult_CustomerRep.pdf\"");
                OutputStream out = response.getOutputStream();
                out.write(buf, 0, buf.length);
                out.close();
            } else if (rtype.equalsIgnoreCase("EXCEL")) {
                System.out.println("test1");
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"WQS_SampleResult_CustomerRep.xls\"");
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
                                   "attachment;filename=\"WQS_SampleResult_CustomerRep.txt\"");

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
