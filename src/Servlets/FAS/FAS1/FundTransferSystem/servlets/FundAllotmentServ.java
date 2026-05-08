package Servlets.FAS.FAS1.FundTransferSystem.servlets;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

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
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

public class FundAllotmentServ extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }


    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        Connection con = null;
        ResultSet rs = null, rs2 = null, rs3 = null, rs4 = null;
        //CallableStatement cs=null,cs1=null;
        PreparedStatement ps = null, ps2 = null, ps3 = null, ps4 = null;

        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                return;
            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }
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
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection            Class.forName(strDriver.trim());
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
            System.out.println("connected");
        } catch (Exception e) {
            System.out.println("Exception in opening connection :" + e);
            //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");
        }
        response.setContentType(CONTENT_TYPE);
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
        String strCommand = "";

        try {
            strCommand = request.getParameter("Command");
            System.out.println("assign.. command..." + strCommand);
        }

        catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }
        String cmbAcc_UnitCodestr = "", cmbOffice_codestr = "";
        int cmbAcc_UnitCode = 0, cmbOffice_code = 0;
        Date txtCrea_date = null;
        try {
            //cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            cmbAcc_UnitCodestr = (request.getParameter("accunit"));
            cmbAcc_UnitCode = Integer.parseInt(cmbAcc_UnitCodestr);
            System.out.println("cmbAcc_UnitCode========" + cmbAcc_UnitCodestr);
        } catch (NumberFormatException e) {
            System.out.println("exception  here" + e);
        }
        System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);
        try {
            //cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            cmbOffice_code =
                    Integer.parseInt(request.getParameter("officeId"));
            //  cmbAcc_UnitCode=Integer.parseInt(cmbAcc_UnitCodestr);
            System.out.println("cmbOffice_code========" + cmbOffice_code);
        } catch (NumberFormatException e) {
            System.out.println("exception  here" + e);
        }
        System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);

        try {
            cmbOffice_codestr = request.getParameter("diviname");
            // cmbOffice_code=Integer.parseInt(cmbOffice_codestr);
            System.out.println(" cmbOffice_codestr========" +
                               cmbOffice_codestr);
        } catch (NumberFormatException e) {
            System.out.println("exception" + e);
        }
        System.out.println("cmbOffice_code " + cmbOffice_code);


        if (strCommand.equalsIgnoreCase("loadDivision")) {
            System.out.println("Inside load division .......");
            int count = 0;
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);

            String xml = "";
            xml = "<response><command>loadDivision</command>";

            String stryear = request.getParameter("txtyear");
            String strmonth = request.getParameter("txtmonth");
            int year = Integer.parseInt(stryear);
            int month = Integer.parseInt(strmonth);
            int accunit = Integer.parseInt(request.getParameter("accunit"));


            System.out.println("txtmonth " + month);
            try {
                ps2 =
 con.prepareStatement("select a.office_id,b.office_name from FUND_ALLOTMENT_TRANSACTION a,com_mst_offices b" +
                      " where a.office_id=b.office_id and " +
                      " cashbook_year=? and CASHBOOK_MONTH=?");
                ps2.setInt(1, year);
                ps2.setInt(2, month);

                // ps2.setInt(3,accunit);
                //   ps2.setInt(4,cmbOffice_code);
                rs2 = ps2.executeQuery();
                System.out.println("here is ok");
                xml = xml + "<cbo_root>";
                while (rs2.next()) {
                    xml = xml + "<item>";
                    xml =
 xml + "<tranOfficeId>" + rs2.getInt("OFFICE_ID") + "</tranOfficeId>" +
   "<divname>" + rs2.getString("office_name") + "</divname>";
                    xml = xml + "</item>";
                    count++;
                }
                xml = xml + "</cbo_root>";
                if (count == 0)
                    xml = xml + "<flag>failure</flag>";
                else
                    xml = xml + "<flag>success</flag>";
                System.out.println("count  " + count);
                //ps2.close();
                //rs2.close();
            } catch (Exception e) {
                System.out.println("catch..HERE.in load head code." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);

        }


        else if (strCommand.equalsIgnoreCase("loadLetNoNew")) {
            System.out.println("here letterno");
            try {
                //cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
                cmbOffice_code =
                        Integer.parseInt(request.getParameter("officeId"));
                //  cmbAcc_UnitCode=Integer.parseInt(cmbAcc_UnitCodestr);
                System.out.println("cmbOffice_code========" + cmbOffice_code);
            } catch (NumberFormatException e) {
                System.out.println("exception  here" + e);
            }
            System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);
            int count = 0;
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);

            String xml = "";
            xml = "<response><command>loadLetNoNew</command>";
            String stryear = request.getParameter("txtyear");
            String strmonth = request.getParameter("txtmonth");
            int year = Integer.parseInt(stryear);
            int month = Integer.parseInt(strmonth);
            int accunit = Integer.parseInt(request.getParameter("accunit"));


            String wtype = request.getParameter("worktype");
            System.out.println("Work type is :" + wtype);
            System.out.println("year" + year);
            System.out.println("month" + month);
            //      String strdivname=request.getParameter("diviname");
            String newoffice_id = request.getParameter("officeId");
            int tran_id = Integer.parseInt(newoffice_id);
            System.out.println("transferid" + tran_id);
            //      int divname=Integer.parseInt(strdivname);
            //   System.out.println("divname"+divname);
            try {
                ps2 =
 con.prepareStatement("select HO_REF_NO from fund_allotment_transaction where accounting_unit_id=? and office_id=? and" +
                      " cashbook_year=? and cashbook_month=? and fund_type=?");
                ps2.setInt(1, cmbAcc_UnitCode);
                ps2.setInt(2, tran_id);
                ps2.setInt(3, year);
                ps2.setInt(4, month);
                ps2.setString(5, wtype);
                // ps2.setInt(5,divname);
                rs2 = ps2.executeQuery();
                System.out.println("here is ok......2");
                while (rs2.next()) {
                    // String refdate=(rs2.getString("refdate1")=="")?" ":rs2.getString("refdate1");
                    // <option value='ACCOUNTING_UNIT_ID'>rs3.getInt("TRANSFER_TO_OFFICE_ID")+"</option>
                    xml =
 xml + "<LetterNo>" + rs2.getString("HO_REF_NO") + "</LetterNo>";
                    // xml=xml+"<LetterDate>"+refdate+"</LetterDate>";
                    //xml=xml+"<transAmt>"+rs2.getInt("AMOUNT")+"</transAmt>";
                    count++;
                }
                if (count == 0)
                    xml = xml + "<flag>failure</flag>";
                else
                    xml = xml + "<flag>success</flag>";
                System.out.println("count  " + count);
                //ps2.close();
                //rs2.close();
            } catch (Exception e) {
                System.out.println("catch..HERE.in load head code." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);

        }


    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        System.out.println("hello");
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
        } catch (Exception e) {
            System.out.println("Exception in connection..." + e);
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
        String wtype = request.getParameter("worktype");
        String param = request.getParameter("cmbLetNo");
        System.out.println("Parameter values are :" + param);

        // JasperDesign jasperDesign = null;
        File reportFile = null;
        try {
            System.out.println("calling servlet");
            if (wtype.equals("C")) {
                reportFile =
                        new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/FundTransferSystem/jasper/FundAllotmentReport_Civil.jasper"));
            } else {
                reportFile =
                        new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/FundTransferSystem/jasper/FundAllotmentReport_Work.jasper"));
            }
            System.out.println("Report file is :" + reportFile);
            if (!reportFile.exists())
                throw new JRRuntimeException("File J not found. The report design must be compiled first.");
            System.out.println("1sssss");
            JasperReport jasperReport =
                (JasperReport)JRLoader.loadObject(reportFile.getPath());
            System.out.println("2ssss");
            Map map = null;
            map = new HashMap();
            map.put("letNo", param);
            //  map.put("wtype",wtype);
            System.out.println("3sssss");
            JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, map, connection);
            System.out.println("4ssss");
            // String rtype= request.getParameter("cmbReportType");
            String rtype = "PDF";
            if (rtype.equalsIgnoreCase("HTML")) {
                response.setContentType("text/html");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"OfficeSpecificDetail.html\"");
                PrintWriter out = response.getWriter();
                JRHtmlExporter exporter = new JRHtmlExporter();
                // File f=new File(getServletContext().getRealPath("/WEB-INF/Report/"));
                //  exporter.setParameter(JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR,true);
                //  exporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR_NAME,getServletContext().getRealPath("/WEB-INF/Report/"));
                //  exporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR,f);
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
                // response.setHeader("content-disposition", "inline;filename=OpenActionItems.pdf");
                //response.setContentType("application/force-download");

                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"OfficeSpecificDetail.pdf\"");
                OutputStream out = response.getOutputStream();
                out.write(buf, 0, buf.length);
                out.close();
            } else if (rtype.equalsIgnoreCase("EXCEL")) {

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"OfficeSpecificDetail.xls\"");
                JRXlsExporter exporterXLS = new JRXlsExporter();
                exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT,
                                         jasperPrint);

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
                byte[] bytes;
                bytes = xlsReport.toByteArray();
                ServletOutputStream ouputStream = response.getOutputStream();
                ouputStream.write(bytes, 0, bytes.length);
                ouputStream.flush();
                ouputStream.close();

            } else if (rtype.equalsIgnoreCase("TXT")) {

                response.setContentType("text/plain");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"OfficeDetail.txt\"");

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


    private void sendMessage(HttpServletResponse response, String msg,
                             String bType) {
        try {
            String url =
                "org/Library/jsps/MessengerOkBack.jsp?message=" + msg +
                "&button=" + bType;
            response.sendRedirect(url);
        } catch (IOException e) {
        }
    }
}
