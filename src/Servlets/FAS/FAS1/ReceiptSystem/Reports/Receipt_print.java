package Servlets.FAS.FAS1.ReceiptSystem.Reports;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.sql.Connection;
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

public class Receipt_print extends HttpServlet {

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        Connection con = null;
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
        } catch (Exception e) {
            System.out.println("Exception in opening connection :" + e);
            //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

        }

        int txtCB_Year = 0, txtCB_Month = 0, cmbAcc_UnitCode =
            0, cmbOffice_code = 0, txtReceipt_No = 0;
        ;
        String sql = "", txtReceipt_type = "";
        try {
            cmbAcc_UnitCode =
                    Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
        } catch (NumberFormatException e) {
            System.out.println("exception" + e);
        }
        System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);

        try {
            cmbOffice_code =
                    Integer.parseInt(request.getParameter("cmbOffice_code"));
        } catch (NumberFormatException e) {
            System.out.println("exception" + e);
        }
        System.out.println("cmbOffice_code " + cmbOffice_code);

        txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));
        txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
        System.out.println("year..." + txtCB_Year);
        System.out.println("Month..." + txtCB_Month);
        txtReceipt_type = request.getParameter("txtReceipt_type");
        //txtReceipt_type="CR";
        System.out.println(txtReceipt_type);
        //txtReceipt_No=4;
        try {
            txtReceipt_No =
                    Integer.parseInt(request.getParameter("txtReceipt_No"));
        } catch (NumberFormatException e) {
            System.out.println("exception" + e);
        }
        System.out.println("txtReceipt_No " + txtReceipt_No);

        //  for the purpose of Self-Cheque Acq. Deatails printing--

        //String cheqNO=request.getParameter("cheqNO");
        //String drawdate=request.getParameter("drawdate");

        // checking printing type

        String recdate = request.getParameter("recdate");
        System.out.println("recdate" + recdate);
        File reportFile = null;
        try {
            System.out.println("calling servlet...");
            PreparedStatement ps = null;
            ResultSet rs = null;
            String strprintType = "";
            if (txtReceipt_type.equalsIgnoreCase("CR") ||
                txtReceipt_type.equalsIgnoreCase("BR")) {
                System.out.println("here 1");
                ps =
  con.prepareStatement("select NO_OF_COPY,PRINT_TYPE from  FAS_TRACK_PRINT_RECEIPT where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and to_char(VOUCHER_DATE,'DD/MM/YYYY')=? and VOUCHER_NO=? and NO_OF_COPY=(select max(NO_OF_COPY) from FAS_TRACK_PRINT_RECEIPT where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and to_char(VOUCHER_DATE,'DD/MM/YYYY')=? and VOUCHER_NO=?)");
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setString(3, recdate);
                ps.setInt(4, txtReceipt_No);
                ps.setInt(5, cmbAcc_UnitCode);
                ps.setInt(6, cmbOffice_code);
                ps.setString(7, recdate);
                ps.setInt(8, txtReceipt_No);
                rs = ps.executeQuery();

                if (rs.next()) {
                    //System.out.println("inside if");
                    //System.out.println("NO_OF_COPY.."+ rs.getInt("NO_OF_COPY"));
                    if (rs.getInt("NO_OF_COPY") == 1) {
                        sendMessage(response,
                                    "Original already issued...you have to specify Revised or Duplicate in Tracking Receipt Print",
                                    "ok");
                        return;
                    } else
                        strprintType = rs.getString("PRINT_TYPE");
                } else
                    strprintType = "O";
                //System.out.println("here 2");
            }

            if (txtReceipt_type.equalsIgnoreCase("CR")) {
            System.out.println("strprintType:::::::::::::::::::::::::::::::::"+strprintType);
                if (strprintType.equalsIgnoreCase("O"))
                    reportFile =
                            new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/ReceiptPrint_UnitwiseModule/Cash_Receipt_Report_byCR.jasper"));
                else if (strprintType.equalsIgnoreCase("R"))
                    reportFile =
                            new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/ReceiptPrint_UnitwiseModule/Cash_Receipt_Report_byCR_Revised.jasper"));
                else if (strprintType.equalsIgnoreCase("D"))
                    reportFile =
                            new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/ReceiptPrint_UnitwiseModule/Cash_Receipt_Report_byCR_Duplicate.jasper"));
            } else if (txtReceipt_type.equalsIgnoreCase("BR")) {
                if (strprintType.equalsIgnoreCase("O"))
                    reportFile =
                            new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/ReceiptPrint_UnitwiseModule/Cash_Receipt_Report_byBR.jasper"));
                else if (strprintType.equalsIgnoreCase("R"))
                    reportFile =
                            new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/ReceiptPrint_UnitwiseModule/Cash_Receipt_Report_byBR_Revised.jasper"));
                else if (strprintType.equalsIgnoreCase("D"))
                    reportFile =
                            new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/ReceiptPrint_UnitwiseModule/Cash_Receipt_Report_byBR_Duplicate.jasper"));
            } else if (txtReceipt_type.equalsIgnoreCase("SC"))
                reportFile =
                        new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/ReceiptPrint_UnitwiseModule/Cash_Receipt_Report_byCR.jasper"));
            else if (txtReceipt_type.equalsIgnoreCase("ACQ")) // See the Report path differs
                reportFile =
                        new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/SelfChequeSystem/jasper/Self_cheq_Acq_Emp_details.jasper"));

            if (!reportFile.exists())
                throw new JRRuntimeException("File J not found. The report design must be compiled first.");

            JasperReport jasperReport =
                (JasperReport)JRLoader.loadObject(reportFile.getPath());
            Map map = null;
            map = new HashMap();

            map.put("acc_unit_id", cmbAcc_UnitCode);
            map.put("off_id", cmbOffice_code);
            map.put("yr", txtCB_Year);
            map.put("mon", txtCB_Month);
            map.put("recNo", txtReceipt_No);
            map.put("recType", txtReceipt_type);
            map.put("imagepath",
                    getServletContext().getRealPath("/images/twademblem.gif"));

            //  for the purpose of Self-Cheque Acq. Deatails printing--

            // map.put("cheqNO",cheqNO);
            // map.put("drawdate",drawdate);

            JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, map, con);
            System.out.println("upto");
            String rtype = "PDF"; // request.getParameter("cmbReportType");
            System.out.println(rtype);
            if (rtype.equalsIgnoreCase("HTML")) {
                response.setContentType("text/html");

                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"Receipt.html\"");
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
                System.out.println(rtype);
                byte buf[] =
                    JasperExportManager.exportReportToPdf(jasperPrint);
                response.setContentType("application/pdf");
                response.setContentLength(buf.length);
                // response.setHeader("content-disposition", "inline;filename=OpenActionItems.pdf");
                //response.setContentType("application/force-download");

                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"Receipt.pdf\"");
                OutputStream out = response.getOutputStream();
                out.write(buf, 0, buf.length);
                out.close();
            } else if (rtype.equalsIgnoreCase("EXCEL")) {

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"Receipt.xls\"");
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
                                   "attachment;filename=\"Receipt.txt\"");

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
                "Could not create the report " + ex.getMessage() + " uu " +
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
            System.out.println("Excep" + e);
        }
    }
}
