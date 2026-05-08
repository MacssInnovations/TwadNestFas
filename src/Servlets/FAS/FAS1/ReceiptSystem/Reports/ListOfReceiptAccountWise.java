package Servlets.FAS.FAS1.ReceiptSystem.Reports;

import java.awt.print.PrinterJob;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

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


public class ListOfReceiptAccountWise extends HttpServlet {
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

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
System.out.println("testing the List of Receipt Account wise****");
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
         * Set Content Type
         */
        String opt = "";
        response.setContentType(CONTENT_TYPE);


        /**
         * Database Connection
         */
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

        File reportFile = null;

        try {

            String cmbAcc_UnitCode = null;
            String cmbOffice_code = null;
            String fromdate="",dateString1="",dateString="";
            String todate = null;
            String rtype = null;


            int accountingunit = 0;
            int accountingoffice = 0;
            int txtCB_Year = 0;String txtCB_Year1="",txtCB_Month1="";
            int txtCB_Month = 0;

            /** Get Accounting Unit ID */
            try {

                cmbAcc_UnitCode = request.getParameter("cmbAcc_UnitCode");
                accountingunit = Integer.parseInt(cmbAcc_UnitCode);
            } catch (Exception e) {
                System.out.println(e);
            }


            /** Get Accounting Office ID */
            try {

                cmbOffice_code = request.getParameter("cmbOffice_code");
                accountingoffice = Integer.parseInt(cmbOffice_code);
            } catch (Exception e) {
                System.out.println(e);
            }


            /** Get Cashbook Year */
            try {
                txtCB_Year =
                        Integer.parseInt(request.getParameter("txtCB_Year"));
                txtCB_Year1=request.getParameter("txtCB_Year");
                        
            } catch (Exception e) {
                System.out.println(e);
            }


            /** Get Cashbook Month */
            try {
                txtCB_Month =
                        Integer.parseInt(request.getParameter("txtCB_Month"));
                txtCB_Month1=request.getParameter("txtCB_Month");
            } catch (Exception e) {
                System.out.println(e);
            }


            /** Get From Date */
            try {
                fromdate = request.getParameter("txtfromdate");
                //System.out.println("fromdate***********"+fromdate);
            } catch (Exception e) {
                System.out.println(e);
            }


            /** Get To Date */
            try {
                todate = request.getParameter("txttodate");
                //System.out.println("todate***********"+todate);
            } catch (Exception e) {
                System.out.println(e);
            }


            /** Get Report Type */
            try {

                rtype = request.getParameter("txtoption");

            } catch (Exception e) {
                System.out.println(e);
            }


            java.util.Date d = null;
            java.util.Date d1 = null;
            java.sql.Date FromDate = null;
            java.sql.Date ToDate = null;

            /** Convert Java Date (From , To )  into SQL Date */
            if (!fromdate.equalsIgnoreCase("") &&
                !todate.equalsIgnoreCase("")) {


                System.out.println("before converting from date");
                 dateString = fromdate;
                SimpleDateFormat dateFormat =
                    new SimpleDateFormat("dd/MM/yyyy");

                d = dateFormat.parse(fromdate.trim());
                System.out.println("util date is:" + d);
                dateFormat.applyPattern("dd-MMM-yy");
                dateString = dateFormat.format(d);
                System.out.println("dateString "+dateString);
                //FromDate = java.sql.Date.valueOf(dateString);
                //System.out.println("FromDate "+FromDate );


                System.out.println("before converting  to date");
                 dateString1 = todate;
                SimpleDateFormat dateFormat1 =
                    new SimpleDateFormat("dd/MM/yyyy");

                d1 = dateFormat1.parse(todate.trim());
                dateFormat1.applyPattern("dd-MMM-yy");
                dateString1 = dateFormat1.format(d1);
                //ToDate = java.sql.Date.valueOf(dateString1);
                
                //System.out.println("Todate      :" + ToDate);
            }


            /**
                    * Find Month Value
                    */

            String monthInWords = "";
            if (txtCB_Month == 1)
                monthInWords = "January";
            else if (txtCB_Month == 2)
                monthInWords = "February";
            else if (txtCB_Month == 3)
                monthInWords = "March";
            else if (txtCB_Month == 4)
                monthInWords = "April";
            else if (txtCB_Month == 5)
                monthInWords = "May";
            else if (txtCB_Month == 6)
                monthInWords = "June";
            else if (txtCB_Month == 7)
                monthInWords = "July";
            else if (txtCB_Month == 8)
                monthInWords = "August";
            else if (txtCB_Month == 9)
                monthInWords = "September";
            else if (txtCB_Month == 10)
                monthInWords = "October";
            else if (txtCB_Month == 11)
                monthInWords = "November";
            else if (txtCB_Month == 12)
                monthInWords = "December";


            /**
             * Drill Down
             */

            String Major_Grp = null;
            int Minor_Grp = 0;
            int cmbAccHeadCode = 0;

            /** Get Major Group */
            try {
                Major_Grp = request.getParameter("Major_Grp");
                System.out.println("Major_Grp******"+Major_Grp);
            } catch (Exception e) {
                System.out.println(e);
            }

            /** Get Minor Group */
            try {
                Minor_Grp =
                        Integer.parseInt(request.getParameter("Minor_Grp"));
                        System.out.println("Minor_Grp"+Minor_Grp);
            } catch (Exception e) {
                System.out.println(e);
            }

            /** Get Account head Code */
            try {
                cmbAccHeadCode =
                        Integer.parseInt(request.getParameter("cmbAccHeadCode"));
                System.out.println("cmbAccHeadCode"+cmbAccHeadCode);
            } catch (Exception e) {
                System.out.println(e);
            }


            /** Where Account Head Code Query String */
            String ACCHEAD,ACCHEAD1 = null;
            String CR_ACCHEAD,CR_ACCHEAD1= null;
            String DR_ACCHEAD,DR_ACCHEAD1 = null;

            /** Find which Group has been selected */

            /** No Selected Account Head Codes  */
            if (Major_Grp.equalsIgnoreCase("All") && Minor_Grp == -100 &&
                cmbAccHeadCode == -100) {
                ACCHEAD = "";
                CR_ACCHEAD = "";
                DR_ACCHEAD = "";
                ACCHEAD1 = "";
                CR_ACCHEAD1 = "";
                DR_ACCHEAD1 = "";
            } else if (Minor_Grp == -100 && cmbAccHeadCode == -100) {
                ACCHEAD =
                        "a.ACCOUNT_HEAD_CODE in ( select account_head_code from com_mst_account_heads where major_head_code='" +
                        Major_Grp + "' ) and ";
                CR_ACCHEAD =
                        "a.CR_ACCOUNT_HEAD_CODE in ( select account_head_code from com_mst_account_heads where major_head_code='" +
                        Major_Grp + "' ) and ";
                DR_ACCHEAD =
                        "a.DR_ACCOUNT_HEAD_CODE in ( select account_head_code from com_mst_account_heads where major_head_code='" +
                        Major_Grp + "' ) and ";
                ACCHEAD1 =
                    "b.ACCOUNT_HEAD_CODE in ( select account_head_code from com_mst_account_heads where major_head_code='" +
                    Major_Grp + "' ) and ";
                CR_ACCHEAD1 =
                    "b.CR_ACCOUNT_HEAD_CODE in ( select account_head_code from com_mst_account_heads where major_head_code='" +
                    Major_Grp + "' ) and ";
                DR_ACCHEAD1 =
                    "b.DR_ACCOUNT_HEAD_CODE in ( select account_head_code from com_mst_account_heads where major_head_code='" +
                    Major_Grp + "' ) and ";
            } else if (cmbAccHeadCode == -100) {
                ACCHEAD =
                        "a.ACCOUNT_HEAD_CODE in ( select account_head_code from com_mst_account_heads where major_head_code='" +
                        Major_Grp + "' and minor_head_code= " + Minor_Grp +
                        " ) and ";
                CR_ACCHEAD =
                        "a.CR_ACCOUNT_HEAD_CODE in ( select account_head_code from com_mst_account_heads where major_head_code='" +
                        Major_Grp + "' and minor_head_code= " + Minor_Grp +
                        " ) and ";
                DR_ACCHEAD =
                        "a.DR_ACCOUNT_HEAD_CODE in ( select account_head_code from com_mst_account_heads where major_head_code='" +
                        Major_Grp + "' and minor_head_code= " + Minor_Grp +
                        " ) and ";
                ACCHEAD1 =
                    "b.ACCOUNT_HEAD_CODE in ( select account_head_code from com_mst_account_heads where major_head_code='" +
                    Major_Grp + "' and minor_head_code= " + Minor_Grp +
                    " ) and ";
                CR_ACCHEAD1 =
                    "b.CR_ACCOUNT_HEAD_CODE in ( select account_head_code from com_mst_account_heads where major_head_code='" +
                    Major_Grp + "' and minor_head_code= " + Minor_Grp +
                    " ) and ";
                DR_ACCHEAD1 =
                    "b.DR_ACCOUNT_HEAD_CODE in ( select account_head_code from com_mst_account_heads where major_head_code='" +
                    Major_Grp + "' and minor_head_code= " + Minor_Grp +
                    " ) and ";
            } else {
                ACCHEAD = "a.ACCOUNT_HEAD_CODE =" + cmbAccHeadCode + " and ";
                CR_ACCHEAD =
                        "a.CR_ACCOUNT_HEAD_CODE =" + cmbAccHeadCode + " and ";
                DR_ACCHEAD =
                        "a.DR_ACCOUNT_HEAD_CODE =" + cmbAccHeadCode + " and ";
                ACCHEAD1 = "b.ACCOUNT_HEAD_CODE =" + cmbAccHeadCode + " and ";
                CR_ACCHEAD1 =
                    "b.CR_ACCOUNT_HEAD_CODE =" + cmbAccHeadCode + " and ";
                DR_ACCHEAD1 =
                    "b.DR_ACCOUNT_HEAD_CODE =" + cmbAccHeadCode + " and ";
            }

            String DATEWISE = "";
            String IBTDATEWISE = "";
            String PDate = "";
            String RDate = "";
            String VDate = "";
            String IBTDate = "",fundTrfDate="";
            

            /** Report by Cashbook Month/Year wise or Date wise */
            if (fromdate.equalsIgnoreCase("") || todate.equalsIgnoreCase("")) {
                DATEWISE =" b.cashbook_year=" + txtCB_Year + " and  b.cashbook_month = " +txtCB_Month;
                IBTDATEWISE ="a.cashbook_year=" + txtCB_Year + " and  a.cashbook_month = " +txtCB_Month;
                monthInWords="For the Month Of "+monthInWords+" "+txtCB_Year1;
               
            } else {
                PDate =" b.payment_date between '" + dateString + "' and '" + dateString1 +"'";
                RDate =" b.receipt_date between '" + dateString + "' and '" + dateString1 +"'";
                VDate =" b.voucher_date between '" + dateString + "' and '" + dateString1 +"'";
                IBTDate =" a.DATE_OF_TRANSFER between '" + dateString + "' and '" + dateString1 +"'";
                fundTrfDate=" b.DATE_OF_TRANSFER between '" + dateString + "' and '" + dateString1 +"'";
                monthInWords="For the Period "+dateString+" To "+dateString1;
            }


            /** Jasper Report Spefication */
            // reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/TransactionListing/ListOfReportAccountWise.jasper"));
            if (fromdate.equalsIgnoreCase("") || todate.equalsIgnoreCase("")) {
            reportFile =
                   // new File("/root/Desktop/ListOfReportAccountWise_New.jasper");
            new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/" +
            "ReceiptSystem/jasper/TransactionListing/ListOfReportAccountWise_New.jasper"));
            }
            else {
            	 reportFile =
                         // new File("/root/Desktop/ListOfReportAccountWise_New.jasper");
                  new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/" +
                  "ReceiptSystem/jasper/TransactionListing/ListOfReportAccountWise_New_date.jasper"));
            }
            System.out.println("Report file--hhhhhhhh> " + reportFile);
            if (!reportFile.exists())
                throw new JRRuntimeException("File J not found. The report design must be compiled first.");

            JasperReport jasperReport =
                (JasperReport)JRLoader.loadObject(reportFile.getPath());
            System.out.println("jaspert report object****" + jasperReport);
           
            /** Parameter Passing */
            Map map = new HashMap();
            
            if (fromdate.equalsIgnoreCase("") && todate.equalsIgnoreCase(""))
            {
            	
                String monthWise="A/c Head Total";
                map.put("accountingunitid", accountingunit);
                map.put("accountofficeid", accountingoffice);

                map.put("txtCB_Year", txtCB_Year1);
                map.put("txtCB_Month", txtCB_Month1);
                map.put("monthInWords", monthInWords);

                map.put("accountheadcode", ACCHEAD);
                map.put("accountheadcode1", ACCHEAD1);
                map.put("DATEWISE", DATEWISE);
               
                map.put("PDate", PDate);
             
                map.put("RDate", RDate);
                map.put("VDate", VDate);

                map.put("IBTDate", IBTDate);
                map.put("IBTDATEWISE", IBTDATEWISE);
                map.put("craccountheadcode", CR_ACCHEAD);
                map.put("draccountheadcode", DR_ACCHEAD);
                map.put("craccountheadcode1", CR_ACCHEAD1);
                map.put("draccountheadcode1", DR_ACCHEAD1);
                map.put("fundTrfDate", fundTrfDate);
                map.put("monthWise", monthWise);
                

                /** Print All the Values for Testing  */

              System.out.println("accountingunit--->" + accountingunit);
                System.out.println("accountingoffice--->" + accountingoffice);

                System.out.println("monthInWords--->" + monthInWords);
                System.out.println("ACCHEAD--->" + ACCHEAD);
                System.out.println("ACCHEAD--->" + ACCHEAD1);
                System.out.println("DATEWISE--->" + DATEWISE);
                System.out.println("PDate-->" + PDate);
                System.out.println("RDate-->" + RDate);
                System.out.println("VDate-->" + VDate);


                System.out.println("txtCB_Year--->" + txtCB_Year);
                System.out.println("txtCB_Month--->" + txtCB_Month);

                System.out.println("IBTDate--->" + IBTDate);
                System.out.println("IBTDATEWISE--->" + IBTDATEWISE);
                System.out.println("craccountheadcode--->" + CR_ACCHEAD);
                System.out.println("draccountheadcode--->" + DR_ACCHEAD);  
                System.out.println("craccountheadcode1--->" + CR_ACCHEAD1);
                System.out.println("draccountheadcode1--->" + DR_ACCHEAD1);
            }
            else if(!fromdate.equalsIgnoreCase("") && (!todate.equalsIgnoreCase("")))
            {
            	System.out.println("elseeeeeeeeeeeeeeee");
                String monthWise="Month Total for the A/c Head";
                map.put("accountingunitid", accountingunit);
                map.put("accountofficeid", accountingoffice);

                map.put("txtCB_Year", txtCB_Year1);
                map.put("txtCB_Month", txtCB_Month1);
                map.put("fromDate", fromdate);
                map.put("toDate", todate);
                
                map.put("monthInWords", monthInWords);

                map.put("accountheadcode", ACCHEAD);
                map.put("accountheadcode1", ACCHEAD1);
               
                map.put("DATEWISE", DATEWISE);
                map.put("PDate", PDate);
                map.put("RDate", RDate);
                map.put("VDate", VDate);

                map.put("IBTDate", IBTDate);
                map.put("IBTDATEWISE", IBTDATEWISE);
                map.put("craccountheadcode", CR_ACCHEAD);
                map.put("craccountheadcode1", CR_ACCHEAD1);
                map.put("draccountheadcode", DR_ACCHEAD);
                map.put("draccountheadcode1", DR_ACCHEAD1);
                map.put("fundTrfDate", fundTrfDate);
                map.put("monthWise", monthWise);

                /** Print All the Values for Testing  */

                System.out.println("accountingunit---eleeeeeeeeeee>" + accountingunit);
                System.out.println("accountingoffice--->" + accountingoffice);

                System.out.println("monthInWords--->" + monthInWords);
                System.out.println("ACCHEAD--->" + ACCHEAD);
                System.out.println("ACCHEAD1 value:"+ACCHEAD1);
                System.out.println("DATEWISE--->" + DATEWISE);
                System.out.println("PDate-->" + PDate);
                System.out.println("RDate-->" + RDate);
                System.out.println("VDate-->" + VDate);
                System.out.println("fundTrfDate-->" + fundTrfDate);
                

                System.out.println("txtCB_Year--->" + txtCB_Year);
                System.out.println("txtCB_Month--->" + txtCB_Month);

                System.out.println("IBTDate--->" + IBTDate);
                System.out.println("IBTDATEWISE--->" + IBTDATEWISE);
                System.out.println("craccountheadcode--->" + CR_ACCHEAD);
                System.out.println("draccountheadcode--->" + DR_ACCHEAD); 
                System.out.println("craccountheadcode1--->" + CR_ACCHEAD1);
                System.out.println("draccountheadcode1--->" + DR_ACCHEAD1); 
            
            }

            
            System.out.println(" map "+map);
            JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, map, connection);

            

            System.out.println("coming here");
//            JasperPrint jasperPrint =
//                JasperFillManager.fillReport(jasperReport, map, connection);

            System.out.println("juhu");


            if (rtype.equalsIgnoreCase("HTML")) {
                response.setContentType("text/html");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"ListOfReceiptAccountHeadWise.html\"");
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
                try {
                    byte buf[] =
                        JasperExportManager.exportReportToPdf(jasperPrint);
                    response.setContentType("application/pdf");
                    response.setContentLength(buf.length);
                    response.setHeader("Content-Disposition",
                                       "attachment;filename=\"ListOfReceiptAccountHeadWise.pdf\"");
                    OutputStream out = response.getOutputStream();
                    out.write(buf, 0, buf.length);
                    out.close();
                } catch (Exception e) {
                    System.out.println("1. Error in PDF Block -->" +
                                       e.getMessage());
                }

            } else if (rtype.equalsIgnoreCase("EXCEL")) {

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"ListOfReceiptAccountHeadWise.xls\"");
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
                                   "attachment;filename=\"ListOfReceiptAccountHeadWise.txt\"");

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
            ex.printStackTrace();
        }
            


    }
}
