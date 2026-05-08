package Servlets.FAS.FAS1.ReceiptSystem.Reports;


import java.awt.print.PrinterJob;

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


public class RegisterChequeServletAbs extends HttpServlet {
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
        JasperDesign jasperDesign = null;
        File reportFile = null;

        try {
            System.out.println("calling servlet");

            String txtCB_Year = request.getParameter("txtCB_Year");
            String txtCB_Month = request.getParameter("txtCB_Month");
            String rtype = request.getParameter("txtoption");
            String cmbAcc_UnitCode = request.getParameter("cmbAcc_UnitCode");
            String cmbOffice_code = request.getParameter("cmbOffice_code");
            String total_wise=request.getParameter("cnrn");
            System.out.println("total_wise:::::"+total_wise);

            
            String cmbBankAccNo = request.getParameter("cmbBankAccNo");
            System.out.println("Cashbook Year:" + txtCB_Year);
            System.out.println("Cashbook Month:" + txtCB_Month);
            System.out.println("accounting unit id is:" + cmbAcc_UnitCode);
            System.out.println("office code is:" + cmbOffice_code);
            int accountingunit = Integer.parseInt(cmbAcc_UnitCode);
            int accountingoffice = Integer.parseInt(cmbOffice_code);
            long accno=Long.parseLong(cmbBankAccNo);
            
            int year = Integer.parseInt(txtCB_Year);
            int month = Integer.parseInt(txtCB_Month);
            String monthInWords = "";
            if (month == 1)
                monthInWords = "January";
            else if (month == 2)
                monthInWords = "February";
            else if (month == 3)
                monthInWords = "March";
            else if (month == 4)
                monthInWords = "April";
            else if (month == 5)
                monthInWords = "May";
            else if (month == 6)
                monthInWords = "June";
            else if (month == 7)
                monthInWords = "July";
            else if (month == 8)
                monthInWords = "August";
            else if (month == 9)
                monthInWords = "September";
            else if (month == 10)
                monthInWords = "October";
            else if (month == 11)
                monthInWords = "November";
            else if (month == 12)
                monthInWords = "December";


            if (accountingunit == 5) {
            	if (rtype.equalsIgnoreCase("EXCEL")) { 	
            	System.out.println("55555");
            	if(total_wise.equals("che"))
            	{
                reportFile =new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/ChequeRec_HOEX.jasper"));
            	}
            	else if(total_wise.equals("rec"))
            	{
            		reportFile =new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/RegisterChequeAbstractReport_HO_rec_wise.jasper"));
            	}
            	}else{ 	
                	System.out.println("55555");
                	if(total_wise.equals("che"))
                	{
                    reportFile =new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/RegisterChequeAbstractReport_HO.jasper"));
                	}
                	else if(total_wise.equals("rec"))
                	{
                		reportFile =new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/RegisterChequeAbstractReport_HO_rec_wise.jasper"));
                	}
            	}
            } else {
            	if (rtype.equalsIgnoreCase("EXCEL")) {
            	if(total_wise.equals("che"))
            	{
                reportFile =new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/RegisterChequeAb_OfficeEX.jasper"));
            	}
            	else if(total_wise.equals("rec"))
            	{
            		 reportFile =new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/RegisterChequeAbstractReport_Office_rec_wise.jasper"));
            	}
            }else{

            	if(total_wise.equals("che"))
            	{
                reportFile =new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/RegisterChequeAbstractReport_Office.jasper"));
            	}
            	else if(total_wise.equals("rec"))
            	{
            		 reportFile =new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/RegisterChequeAbstractReport_Office_rec_wise.jasper"));
            	}
            
            }
            }
            // reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/RegisterChequeAbstractReport.jasper"));


            if (!reportFile.exists())
                throw new JRRuntimeException("File J not found. The report design must be compiled first.");

            JasperReport jasperReport =
                (JasperReport)JRLoader.loadObject(reportFile.getPath());


            System.out.println("opt::" + reportFile);
            // JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            Map map = new HashMap();
            map.put("cashbookyear", year);
            map.put("cashbookmonth", month);
            map.put("accountingunitid", accountingunit);
            map.put("accountofficeid", accountingoffice);
            map.put("monthvalue", monthInWords);
            map.put("accNo", accno);
            System.out.println("accno  46  "+accno);
            JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, map, connection);


            if (rtype.equalsIgnoreCase("HTML")) {
                response.setContentType("text/html");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"ListOfCheque.html\"");
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
                                   "attachment;filename=\"ListOfCheque.pdf\"");
                OutputStream out = response.getOutputStream();
                out.write(buf, 0, buf.length);
                out.close();
            } else if (rtype.equalsIgnoreCase("EXCEL")) {
////
            	
            	//edited
            	/*
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"ChequeReceivedReport.xls\"");
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
                */
//------
            	
            	//// original
            	
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"ListOfCheque.xls\"");
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
}
