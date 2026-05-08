package Servlets.FAS.FAS1.FundTransferSystem.servlets;

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

public class ListFundAllotmentServ1 extends HttpServlet {
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
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
            Class.forName(strDriver.trim());
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
        String xml = "";
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");

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
            cmbOffice_codestr = request.getParameter("diviname");
            // cmbOffice_code=Integer.parseInt(cmbOffice_codestr);
            System.out.println(" cmbOffice_code========" + cmbOffice_codestr);
        } catch (NumberFormatException e) {
            System.out.println("exception" + e);
        }
        System.out.println("cmbOffice_code " + cmbOffice_code);
        String stryear = request.getParameter("txtCB_Year");
        String strmonth = request.getParameter("txtCB_Month");
        int year = Integer.parseInt(stryear);
        int month = Integer.parseInt(strmonth);
        System.out.println(year + "  " + month);
        if (strCommand.equalsIgnoreCase("fetch")) {
            xml = "<response><command>fetch</command>";
            String fundtype = "";
            int cnt = 0, CheqNo = 0;

            double transAmt = 0, fundreq = 0;
            String CheqorDD = "", letter_gen = "", CheqDate = "", LetterDate =
                "", OffLetterDate = "", tranOffice = "", tranOfficeName =
                "", fundtypeid = "", LetterNo = "", remarks = "", OffLetterNo =
                "", reason = "";
            // int accheadcode=Integer.parseInt(request.getParameter("accheadcode"));
            //System.out.println("acc head code is..........."+accheadcode);
            // int SL_TYPE=0;
            //    int SL_CODE=0;
            try {
                ps =
  con.prepareStatement("select a.SL_NO,a.ACCOUNTING_UNIT_ID,a.OFFICE_ID,a.CASHBOOK_YEAR,a.cashbook_month,a.FUND_TYPE, " +
                       "a.REF_NO,a.REF_DATE,a.AMOUNT,a.PARTICULARS,a.FUND_REQUESTED,a.voucher_no,a.updated_by_user_id, " +
                       "a.updated_date,a.CHEQUE_OR_DD,a.CHEQUE_DD_NO, " +
                       "a.CHEQUE_DD_DATE,a.HO_REF_NO,a.HO_REF_DATE,a.REASON,a.LETTER_GEN,b.office_name from FUND_ALLOTMENT_TRANSACTION a,com_mst_offices b " +
                       " where a.OFFICE_ID=b.OFFICE_ID and a.CASHBOOK_YEAR=? and  a.cashbook_month=?");
                ps.setInt(1, year);
                ps.setInt(2, month);


                rs = ps.executeQuery();


                while (rs.next()) {
                    cnt++;
                    if (rs.getString("PARTICULARS") == null)
                        remarks = "";
                    else {
                        remarks = rs.getString("PARTICULARS");
                        System.out.println("listremarks" + remarks);
                    }
                    if (rs.getString("LETTER_GEN") == null)
                        letter_gen = "";
                    else {
                        letter_gen = rs.getString("LETTER_GEN");
                        System.out.println("letter_gen" + letter_gen);
                    }


                    try {
                        if (rs.getDate("REF_DATE") == null)
                            OffLetterDate = "";
                        else {
                            java.sql.Date dd = rs.getDate("REF_DATE");
                            java.text.SimpleDateFormat sdf =
                                new java.text.SimpleDateFormat("dd/MM/yyyy");
                            OffLetterDate = sdf.format(dd);
                            System.out.println("date1 is" + OffLetterDate);
                        }
                        if (rs.getDate("HO_REF_DATE") == null)
                            LetterDate = "";
                        else {
                            java.sql.Date dd = rs.getDate("HO_REF_DATE");
                            java.text.SimpleDateFormat sdf =
                                new java.text.SimpleDateFormat("dd/MM/yyyy");
                            LetterDate = sdf.format(dd);
                            System.out.println("date1 is" + LetterDate);
                        }
                        if (rs.getDate("CHEQUE_DD_DATE") == null)
                            CheqDate = "";
                        else {
                            java.sql.Date dd = rs.getDate("CHEQUE_DD_DATE");
                            java.text.SimpleDateFormat sdf =
                                new java.text.SimpleDateFormat("dd/MM/yyyy");
                            CheqDate = sdf.format(dd);
                            System.out.println("cheqdate2 is" + CheqDate);
                        }

                        fundtype = rs.getString("FUND_TYPE");
                        if (fundtype.equalsIgnoreCase("C")) {
                            fundtype = "Civil";
                        } else {
                            fundtype = "Work";
                        }

                        System.out.println("the letter number is" + LetterNo);
                        if (LetterNo.equals(null)) {
                            LetterNo = "-";
                        } else {
                            LetterNo = rs.getString("HO_REF_NO");
                            System.out.println("LetterNo" + LetterNo);
                        }


                        System.out.println("the letter number is" +
                                           OffLetterNo);
                        if (OffLetterNo.equals(null)) {
                            OffLetterNo = "-";
                        } else {
                            OffLetterNo = rs.getString("REF_NO");
                            System.out.println("OffLetterNo" + OffLetterNo);
                        }


                        if (reason.equals(null)) {
                            reason = " ";
                        } else {
                            reason = rs.getString("REASON");
                            System.out.println("reason" + reason);
                        }
                        if (CheqorDD.equals(null)) {
                            CheqorDD = " ";
                        } else {
                            CheqorDD = rs.getString("CHEQUE_OR_DD");
                            System.out.println("CheqorDD" + CheqorDD);
                        }
                        CheqNo = rs.getInt("CHEQUE_DD_NO");
                        tranOffice = rs.getString("OFFICE_ID");
                        System.out.println("transofficeid" + tranOffice);
                        tranOfficeName = rs.getString("office_name");
                        transAmt = rs.getDouble("AMOUNT");
                        fundreq = rs.getDouble("FUND_REQUESTED");
                        if (fundtypeid.equals(null)) {
                            fundtypeid = "";
                        } else {
                            fundtypeid = rs.getString("FUND_TYPE");
                            System.out.println("fundtypeid" + fundtypeid);
                        }

                        rs.close();


                    } catch (Exception e) {
                        System.out.println("Error in getting date values");

                    }

                    xml =
 xml + "<flag>success</flag><slno>" + rs.getInt("SL_NO") +
   "</slno><tranOffice>" + tranOffice + "</tranOffice><tranOfficeName>" +
   tranOfficeName + "</tranOfficeName>" + "<OffLetterNo>" + OffLetterNo +
   "</OffLetterNo><OffLetterDate>" + OffLetterDate +
   "</OffLetterDate><LetterNo>" + LetterNo + "</LetterNo><LetterDate>" +
   LetterDate + "</LetterDate>" + "<fundtypeid>" + fundtypeid +
   "</fundtypeid><fundtype>" + fundtype + "</fundtype><fundreq>" + fundreq +
   "</fundreq><transAmt>" + transAmt + "</transAmt><reason>" + reason +
   "</reason>" + "<reason>" + reason + "</reason><CheqorDD>" + CheqorDD +
   "</CheqorDD><CheqNo>" + CheqNo + "</CheqNo><CheqDate>" + CheqDate +
   "</CheqDate><remarks>" + remarks + "<remarks></letter_gen>" + letter_gen +
   "</letter_gen>";


                }


                if (cnt == 0)
                    out.println("<tr><td>No data found<td><td></td><td></td><td></td><td></td><td></td></tr>");

            } catch (Exception e) {
                System.out.println("Exception in grid.." + e);
            }


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
                        new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/FundTransferSystem/jasper/FundAllotmentReport_v1.jasper"));
            } else {
                reportFile =
                        new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/FundTransferSystem/jasper/FundAllotmentReport_v2.jasper"));
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
            map.put("wtype", wtype);
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
