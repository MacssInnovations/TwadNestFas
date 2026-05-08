package Servlets.FAS.FAS1.MonthEnd.servlets;

import java.awt.print.PrinterJob;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.*;

import java.util.*;

import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.JasperReport;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;

import java.text.SimpleDateFormat;

import javax.print.DocFlavor;

import javax.print.PrintService;

import net.sf.jasperreports.engine.JasperPrintManager;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

import javax.print.attribute.PrintServiceAttributeSet;

import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.JobName;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.PrinterName;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;

import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;


public class SL_Summary extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";
    Connection connection = null;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        doGet(request, response);
    }

    /**
     *  GET Method
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public

    void doGet(HttpServletRequest request,
               HttpServletResponse response) throws ServletException,
                                                    IOException {


        /**
         * Session Checking
         */
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


        /**
         * Database Connection
         */
        String opt = "";
        response.setContentType(CONTENT_TYPE);
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


        /**
         *  Get Parameters
         */

        try {
            /** Get Accounting Unit ID */
            String cmbAcc_UnitCode = request.getParameter("cmbAcc_UnitCode");
            /** Get Accounting Office ID */
            String cmbOffice_code = request.getParameter("cmbOffice_code");


            String txtCB_Year_From = request.getParameter("txtCB_Year_From");
            String txtCB_Month_From = request.getParameter("txtCB_Month_From");

            String txtCB_Year_To = request.getParameter("txtCB_Year_To");
            String txtCB_Month_To = request.getParameter("txtCB_Month_To");

            //  int txtAcc_HeadCode=Integer.parseInt(request.getParameter("txtAcc_HeadCode"));

            String rtype = "PDF";

            int accountingunit = Integer.parseInt(cmbAcc_UnitCode);
            int accountingoffice = Integer.parseInt(cmbOffice_code);

            int year_from = Integer.parseInt(txtCB_Year_From);
            int month_from = Integer.parseInt(txtCB_Month_From);

            int year_to = Integer.parseInt(txtCB_Year_To);
            int month_to = Integer.parseInt(txtCB_Month_To);

            int txtsupplement_no = 0;
            /** Get Supplement Number */
            try {
                txtsupplement_no =
                        Integer.parseInt(request.getParameter("txtsupplement_no"));
            } catch (Exception e) {
                System.out.println("Error getting Supplement Number -->" + e);
            }


            String monthInWords = "";
            if (month_from == 1)
                monthInWords = "January";
            else if (month_from == 2)
                monthInWords = "February";
            else if (month_from == 3)
                monthInWords = "March";
            else if (month_from == 4)
                monthInWords = "April";
            else if (month_from == 5)
                monthInWords = "May";
            else if (month_from == 6)
                monthInWords = "June";
            else if (month_from == 7)
                monthInWords = "July";
            else if (month_from == 8)
                monthInWords = "August";
            else if (month_from == 9)
                monthInWords = "September";
            else if (month_from == 10)
                monthInWords = "October";
            else if (month_from == 11)
                monthInWords = "November";
            else if (month_from == 12)
                monthInWords = "December";


            String monthInWords_to = "";
            if (month_to == 1)
                monthInWords_to = "January";
            else if (month_to == 2)
                monthInWords_to = "February";
            else if (month_to == 3)
                monthInWords_to = "March";
            else if (month_to == 4)
                monthInWords_to = "April";
            else if (month_to == 5)
                monthInWords_to = "May";
            else if (month_to == 6)
                monthInWords_to = "June";
            else if (month_to == 7)
                monthInWords_to = "July";
            else if (month_to == 8)
                monthInWords_to = "August";
            else if (month_to == 9)
                monthInWords_to = "September";
            else if (month_to == 10)
                monthInWords_to = "October";
            else if (month_to == 11)
                monthInWords_to = "November";
            else if (month_to == 12)
                monthInWords_to = "December";

            String Acc_Head_Type="";
            int txtAcc_HeadCode = 0; 
            String sql="";
            File reportFile=null;     
            try {
                Acc_Head_Type = request.getParameter("Acc_Head_Type");                    
            }
            catch (Exception e) {
                System.out.println("Error Getting Account Head Type --->"+Acc_Head_Type);
            }
            
            if ( Acc_Head_Type.equalsIgnoreCase("S") )
            {
               txtAcc_HeadCode=Integer.parseInt(request.getParameter("txtAcc_HeadCode"));
               sql="and account_head_code = "+txtAcc_HeadCode;
               System.out.println("**********************************************************single");
                reportFile =
                        new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/Reg_Summary/SL_Summary_single.jasper"));

               
            }
            else
            {
                 System.out.println("**********************************************************Else");
                
                 reportFile =
                         new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/Reg_Summary/SL_Summary.jasper"));

                
             }
            



            
    


System.out.println("report File "+reportFile);
          
          
            if (!reportFile.exists())
                throw new JRRuntimeException("File J not found. The report design must be compiled first.");

            JasperReport jasperReport =
                (JasperReport)JRLoader.loadObject(reportFile.getPath());


            PreparedStatement ps = null;
            ResultSet rs = null;
            String UnitName = "", OfficeName = "";
            ps =
  connection.prepareStatement("select ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=?");
            ps.setInt(1, accountingunit);
            rs = ps.executeQuery();
            if (rs.next())
                UnitName = rs.getString("ACCOUNTING_UNIT_NAME");

            ps =
  connection.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=?");
            ps.setInt(1, accountingoffice);
            rs = ps.executeQuery();
            if (rs.next())
                OfficeName = rs.getString("OFFICE_NAME");


            Map map = new HashMap();
            map.put("unitId", accountingunit);
            map.put("officeId", accountingoffice);
            map.put("cbmonth_from", month_from);
            map.put("cbyear_from", year_from);
            map.put("cbmonth_from_value", monthInWords);
            map.put("cbmonth_to", month_to);
            map.put("cbyear_to", year_to);
            map.put("cbmonth_to_value", monthInWords_to);
            map.put("supplement_no", txtsupplement_no);
            map.put("headcode", txtAcc_HeadCode);

            System.out.println("Unit id -->" + accountingunit);
            System.out.println("accountingoffice -->" + accountingoffice);
            System.out.println("month_from -->" + month_from);
            System.out.println("year_from -->" + year_from);
            System.out.println("monthInWords -->" + monthInWords);
            System.out.println("month_to -->" + month_to);
            System.out.println("year_to -->" + year_to);
            System.out.println("monthInWords_to -->" + monthInWords_to);

            System.out.println("This is SL Summary Report..................");

            JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, map, connection);

            if (rtype.equalsIgnoreCase("HTML")) {
                response.setContentType("text/html");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"SubLedgerReport.html\"");
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
                                   "attachment;filename=\"GLSummary.pdf\"");
                OutputStream out = response.getOutputStream();
                out.write(buf, 0, buf.length);
                out.close();
            } else if (rtype.equalsIgnoreCase("EXCEL")) {

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"SubLedgerReport.xls\"");
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
                                   "attachment;filename=\"SubLedgerReport.txt\"");

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
