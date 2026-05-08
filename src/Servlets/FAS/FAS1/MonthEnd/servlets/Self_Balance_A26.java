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


public class Self_Balance_A26 extends HttpServlet {


    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";
    Connection connection = null;

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {


        /** Session Checking */
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


        /** Set Content Type */
        response.setContentType(CONTENT_TYPE);
        PreparedStatement ps=null;
        Connection con=null;
        ResultSet rs1 = null,rs2=null;
        String xml="";
        
        /** Database Connection */
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
        //reporttype
        int type = Integer.parseInt(request.getParameter("reporttype"));
        if (type == 1) 
        {
        
        String singleSlCode = request.getParameter("singleSlCode");
        if (singleSlCode.equalsIgnoreCase("single_code")) {
     
       System.out.println("singleSlCode*********"+singleSlCode);
       
        /** File Object Creation */
        File reportFile = null;



        String cmbAcc_UnitCode = "";
        String cmbOffice_code = "";
        String txtCB_Year = "";
        String txtCB_Month = "";
        String txtCB_Year_to = "";
        String txtCB_Month_to = "";
        String rtype = "";
        String cmbSL_type = "";
        String cmbSL_Code = "";
     //   String month_year="";
        
        int accountingunit = 0;
        int accountingoffice = 0;
        int year = 0;
        int month = 0;
        int year_to = 0;
        int month_to = 0;
        int sl_type = 0;
        int sl_code = 0;


       String month_year = request.getParameter("month_year");
       
        if (month_year.equalsIgnoreCase("particular_cb")) {
            System.out.println("month_year*********"+month_year);
            txtCB_Year = request.getParameter("txtCB_Year");
            txtCB_Month = request.getParameter("txtCB_Month");
            txtCB_Year_to = request.getParameter("txtCB_Year_to");
            txtCB_Month_to = request.getParameter("txtCB_Month_to");
       }
        else  if (month_year.equalsIgnoreCase("more_cb")) {
        
            txtCB_Year = request.getParameter("txtCB_Year_from");
            txtCB_Month = request.getParameter("txtCB_Month_from");
            txtCB_Year_to = request.getParameter("txtCB_Year_to");
            txtCB_Month_to = request.getParameter("txtCB_Month_to");
        }
        
       
         
       // }
       
        

        try {
            cmbAcc_UnitCode = request.getParameter("cmbAcc_UnitCode");
            cmbOffice_code = request.getParameter("cmbOffice_code");
            cmbSL_Code = request.getParameter("cmbSL_Code");
            
            rtype = request.getParameter("txtoption");
            cmbSL_type = request.getParameter("cmbSL_type");
          
         
        } catch (Exception e) {
            System.out.println("Error in Get Parameter -->" + e);
        }

        try {
            /** Convert String Data into int type */
            accountingunit = Integer.parseInt(cmbAcc_UnitCode);
            System.out.println("accountingunit-->" + accountingunit);
            accountingoffice = Integer.parseInt(cmbOffice_code);
            System.out.println("accountingoffice-->" + accountingoffice);
            year = Integer.parseInt(txtCB_Year);
            System.out.println("year-->" + year);
            month = Integer.parseInt(txtCB_Month);
            System.out.println("month-->" + month);


            year_to = Integer.parseInt(txtCB_Year_to);
            System.out.println("year_to-->" + year_to);

            month_to = Integer.parseInt(txtCB_Month_to);
            System.out.println("month_to-->" + month_to);

            sl_type = Integer.parseInt(cmbSL_type);
            System.out.println("sl_type-->" + sl_type);

            sl_code = Integer.parseInt(cmbSL_Code);
            System.out.println("sl_code-->" + sl_code);

        } catch (Exception e) {
            System.out.println("Error in Convert String to Int Data -->" + e);
        }


        String monthInWords = "";
        String monthInWords_to = "";

        try {
            /** Find From Month Value */

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

            /** Find To Month Value */

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

        } catch (Exception e) {
            System.out.println("Error in Calculate Month Value -->" + e);
        }

        
        JasperReport jasperReport = null;
        if (month_year.equalsIgnoreCase("particular_cb")) {
            try {
                reportFile =
                        new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/Self_Balance/A26_SelfBalance2.jasper"));
                if (!reportFile.exists())
                    throw new JRRuntimeException("File J not found. The report design must be compiled first.");
                jasperReport =
                        (JasperReport)JRLoader.loadObject(reportFile.getPath());
            } catch (JRException e) {
                System.out.println("Error in Creating Report Object ---> " + e);
            }
        }
        else  if (month_year.equalsIgnoreCase("more_cb")) 
        {
        	if(month_to==3){
        	
	            try {
	                reportFile =
	                        new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/Self_Balance/A26_SelfBalance2_moreMonth_including.jasper"));
	                if (!reportFile.exists())
	                    throw new JRRuntimeException("File J not found. The report design must be compiled first.");
	                jasperReport =
	                        (JasperReport)JRLoader.loadObject(reportFile.getPath());
	            } catch (JRException e) {
	                System.out.println("Error in Creating Report Object ---> " + e);
	            }
        	}
        	else
        	{
        		try {
	                reportFile =
	                        new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/Self_Balance/A26_SelfBalance2_moreMonth.jasper"));
	                if (!reportFile.exists())
	                    throw new JRRuntimeException("File J not found. The report design must be compiled first.");
	                jasperReport =
	                        (JasperReport)JRLoader.loadObject(reportFile.getPath());
	            } catch (JRException e) {
	                System.out.println("Error in Creating Report Object ---> " + e);
	            }
        	}
        }

        Map map = new HashMap();
        try {
          //  PreparedStatement ps = null;
            
            String UnitName = "", OfficeName = "";
            ps =
  connection.prepareStatement("select ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=?");
            ps.setInt(1, accountingunit);
            rs1 = ps.executeQuery();
            if (rs1.next())
                UnitName = rs1.getString("ACCOUNTING_UNIT_NAME");

            ps =
  connection.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=?");
            ps.setInt(1, accountingoffice);
            rs1 = ps.executeQuery();
            if (rs1.next())
                OfficeName = rs1.getString("OFFICE_NAME");


            map.put("accountingunitid", accountingunit);
            map.put("accountofficeid", accountingoffice);
            map.put("cashbookmonth_from", month);
            map.put("cashbookyear_from", year);
            map.put("monthvalue_from", monthInWords);
            map.put("UnitName", UnitName);
            map.put("OfficeName", OfficeName);
            map.put("cashbookmonth_to", month_to);
            map.put("cashbookyear_to", year_to);
            map.put("monthvalue_to", monthInWords_to);
            map.put("sl_type", sl_type);
            map.put("sl_code", sl_code);
            if (month_year.equalsIgnoreCase("more_cb")) 
            {
            	if(month_to==3)
	            	{
            		int supNo=Integer.parseInt(request.getParameter("supno"));
            		 map.put("supplement", supNo);
	            	}
            	}
            

        } catch (SQLException e) {
            System.out.println("SQL Exception -->" + e);
        }

        try {

            System.out.println("1");
            JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, map, connection);
            System.out.println("2");
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
                System.out.println("3");
                byte buf[] =
                    JasperExportManager.exportReportToPdf(jasperPrint);
                System.out.println("4");
                response.setContentType("application/pdf");
                System.out.println("5");
                response.setContentLength(buf.length);
                System.out.println("6");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"SubLedgerReport.pdf\"");
                System.out.println("7");
                OutputStream out = response.getOutputStream();
                System.out.println("8");
                out.write(buf, 0, buf.length);
                System.out.println("9");
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
                "Could not create the report ff" + ex.getMessage() + " " +
                ex.getLocalizedMessage();
            System.out.println(connectMsg);
        }
     }
        else  if (singleSlCode.equalsIgnoreCase("all_code")) 
        {
        
            System.out.println("SlCode*********"+singleSlCode);
            
             /** File Object Creation */
             File reportFile = null;



             String cmbAcc_UnitCode = "";
             String cmbOffice_code = "";
             String txtCB_Year = "";
             String txtCB_Month = "";
             String txtCB_Year_to = "";
             String txtCB_Month_to = "";
             String rtype = "";
             String cmbSL_type = "";
             String cmbSL_Code = "";
            //   String month_year="";
             
             int accountingunit = 0;
             int accountingoffice = 0;
             int year = 0;
             int month = 0;
             int year_to = 0;
             int month_to = 0;
             int sl_type = 0;
             int sl_code = 0;


            String month_year = request.getParameter("month_year");
            
             if (month_year.equalsIgnoreCase("particular_cb")) {
                 System.out.println("month_year*********"+month_year);
                 txtCB_Year = request.getParameter("txtCB_Year");
                 txtCB_Month = request.getParameter("txtCB_Month");
                 txtCB_Year_to = request.getParameter("txtCB_Year_to");
                 txtCB_Month_to = request.getParameter("txtCB_Month_to");
            }
             else  if (month_year.equalsIgnoreCase("more_cb")) {
             
                 txtCB_Year = request.getParameter("txtCB_Year_from");
                 txtCB_Month = request.getParameter("txtCB_Month_from");
                 txtCB_Year_to = request.getParameter("txtCB_Year_to");
                 txtCB_Month_to = request.getParameter("txtCB_Month_to");
             }
             
            
             try {
                 cmbAcc_UnitCode = request.getParameter("cmbAcc_UnitCode");
                 cmbOffice_code = request.getParameter("cmbOffice_code");
              //   cmbSL_Code = request.getParameter("cmbSL_Code");
                 
                 rtype = request.getParameter("txtoption");
                 cmbSL_type = request.getParameter("cmbSL_type");
               
              
             } catch (Exception e) {
                 System.out.println("Error in Get Parameter -->" + e);
             }

             try {
                 /** Convert String Data into int type */
                 accountingunit = Integer.parseInt(cmbAcc_UnitCode);
                 System.out.println("accountingunit-->" + accountingunit);
                 accountingoffice = Integer.parseInt(cmbOffice_code);
                 System.out.println("accountingoffice-->" + accountingoffice);
                 year = Integer.parseInt(txtCB_Year);
                 System.out.println("year-->" + year);
                 month = Integer.parseInt(txtCB_Month);
                 System.out.println("month-->" + month);


                 year_to = Integer.parseInt(txtCB_Year_to);
                 System.out.println("year_to-->" + year_to);

                 month_to = Integer.parseInt(txtCB_Month_to);
                 System.out.println("month_to-->" + month_to);

                 sl_type = Integer.parseInt(cmbSL_type);
                 System.out.println("sl_type-->" + sl_type);

//                 sl_code = Integer.parseInt(cmbSL_Code);
//                 System.out.println("sl_code-->" + sl_code);

             } catch (Exception e) {
                 System.out.println("Error in Convert String to Int Data -->" + e);
             }


             String monthInWords = "";
             String monthInWords_to = "";

             try {
                 /** Find From Month Value */

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

                 /** Find To Month Value */

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

             } catch (Exception e) {
                 System.out.println("Error in Calculate Month Value -->" + e);
             }
             
           
             
             JasperReport jasperReport = null;
             if (month_year.equalsIgnoreCase("particular_cb")) {
                 try {
                     reportFile =
                             new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/Self_Balance/A26_SelfBalance2_Alll_oneMonth.jasper"));
                     if (!reportFile.exists())
                         throw new JRRuntimeException("File J not found. The report design must be compiled first.");
                     jasperReport =
                             (JasperReport)JRLoader.loadObject(reportFile.getPath());
                 } catch (JRException e) {
                     System.out.println("Error in Creating Report Object ---> " + e);
                 }
             }
             else  if (month_year.equalsIgnoreCase("more_cb")) 
             {
            	 System.out.println("date::::");
            	 if(month_to==3)
            	 {
            		 System.out.println("march inc supplement:::");
	                 try {
	                     reportFile =
	                             new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/Self_Balance/A26_SelfBalance2_Alll_moreMonth_including.jasper"));
	                     if (!reportFile.exists())
	                         throw new JRRuntimeException("File J not found. The report design must be compiled first.");
	                     jasperReport =
	                             (JasperReport)JRLoader.loadObject(reportFile.getPath());
	                 } catch (JRException e) {
	                     System.out.println("Error in Creating Report Object ---> " + e);
	                 }
            	 }
            	 else
            	 {
            		 try {
	                     reportFile =
	                             new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/Self_Balance/A26_SelfBalance2_Alll_moreMonth.jasper"));
	                     if (!reportFile.exists())
	                         throw new JRRuntimeException("File J not found. The report design must be compiled first.");
	                     jasperReport =
	                             (JasperReport)JRLoader.loadObject(reportFile.getPath());
	                 } catch (JRException e) {
	                     System.out.println("Error in Creating Report Object ---> " + e);
	                 }
            	 }
                 
             }

             Map map = new HashMap();
             try {
           //      PreparedStatement ps = null;
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
                 String stype="";

					if(sl_type!=0)
					{
						stype=" AND s.SUB_LEDGER_TYPE_CODE= "+sl_type; 
					}
					else
					{
						stype=" ";
					}
System.out.println("stype "+stype);
                 map.put("accountingunitid", accountingunit);
                 map.put("accountofficeid", accountingoffice);
                 map.put("cashbookmonth_from", month);
                 map.put("cashbookyear_from", year);
                 map.put("monthvalue_from", monthInWords);
                 map.put("UnitName", UnitName);
                 map.put("OfficeName", OfficeName);
                 map.put("cashbookmonth_to", month_to);
                 map.put("cashbookyear_to", year_to);
                 map.put("monthvalue_to", monthInWords_to);
                 map.put("sl_type", stype);
                 
               System.out.println("map "+map);  
                 if (month_year.equalsIgnoreCase("more_cb")) 
                 {
                	 System.out.println("date::::");
                	/* if(month_to==3)
                	 {
                		 int supNo=Integer.parseInt(request.getParameter("supno"));
                		 map.put("supplement", supNo);
                	 }*/
                	 }
               
             } catch (SQLException e) {
                 System.out.println("SQL Exception -->" + e);
             }

             try {

                 System.out.println("1");
                 JasperPrint jasperPrint =JasperFillManager.fillReport(jasperReport, map, connection);
                 System.out.println("2");
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
                     System.out.println("3");
                     byte buf[] =
                         JasperExportManager.exportReportToPdf(jasperPrint);
                     System.out.println("4");
                     response.setContentType("application/pdf");
                     System.out.println("5");
                     response.setContentLength(buf.length);
                     System.out.println("6");
                     response.setHeader("Content-Disposition",
                                        "attachment;filename=\"SubLedgerReport.pdf\"");
                     System.out.println("7");
                     OutputStream out = response.getOutputStream();
                     System.out.println("8");
                     out.write(buf, 0, buf.length);
                     System.out.println("9");
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
                     "Could not create the report mmm" + ex.getMessage() + " " +
                     ex.getLocalizedMessage();
                 System.out.println(connectMsg);
             }
           
         }
        }
        else if (type == 3) {
        	//this is for supplement
        	
        	System.out.println("supplement codingsssssssssssss");
        	int supno =Integer.parseInt(request.getParameter("supno"));
        	
            String singleSlCode = request.getParameter("singleSlCode");
            if (singleSlCode.equalsIgnoreCase("single_code")) {
         
           System.out.println("singleSlCode*********"+singleSlCode);
           
            /** File Object Creation */
            File reportFile = null;



            String cmbAcc_UnitCode = "";
            String cmbOffice_code = "";
            String txtCB_Year = "";
            String txtCB_Month = "";
            String txtCB_Year_to = "";
            String txtCB_Month_to = "";
            String rtype = "";
            String cmbSL_type = "";
            String cmbSL_Code = "";
         //   String month_year="";
            
            int accountingunit = 0;
            int accountingoffice = 0;
            int year = 0;
            int month = 0;
            int year_to = 0;
            int month_to = 0;
            int sl_type = 0;
            int sl_code = 0;


           String month_year = request.getParameter("month_year");
           
            if (month_year.equalsIgnoreCase("particular_cb")) {
                System.out.println("month_year*********"+month_year);
                txtCB_Year = request.getParameter("txtCB_Year");
                txtCB_Month = request.getParameter("txtCB_Month");
                txtCB_Year_to = request.getParameter("txtCB_Year_to");
                txtCB_Month_to = request.getParameter("txtCB_Month_to");
           }
            else  if (month_year.equalsIgnoreCase("more_cb")) {
            
                txtCB_Year = request.getParameter("txtCB_Year_from");
                txtCB_Month = request.getParameter("txtCB_Month_from");
                txtCB_Year_to = request.getParameter("txtCB_Year_to");
                txtCB_Month_to = request.getParameter("txtCB_Month_to");
            }
            
           
             
           // }
           
            

            try {
                cmbAcc_UnitCode = request.getParameter("cmbAcc_UnitCode");
                cmbOffice_code = request.getParameter("cmbOffice_code");
                cmbSL_Code = request.getParameter("cmbSL_Code");
                
                rtype = request.getParameter("txtoption");
                cmbSL_type = request.getParameter("cmbSL_type");
              
             
            } catch (Exception e) {
                System.out.println("Error in Get Parameter -->" + e);
            }

            try {
                /** Convert String Data into int type */
                accountingunit = Integer.parseInt(cmbAcc_UnitCode);
                System.out.println("accountingunit-->" + accountingunit);
                accountingoffice = Integer.parseInt(cmbOffice_code);
                System.out.println("accountingoffice-->" + accountingoffice);
                year = Integer.parseInt(txtCB_Year);
                System.out.println("year-->" + year);
                month = Integer.parseInt(txtCB_Month);
                System.out.println("month-->" + month);


                year_to = Integer.parseInt(txtCB_Year_to);
                System.out.println("year_to-->" + year_to);

                month_to = Integer.parseInt(txtCB_Month_to);
                System.out.println("month_to-->" + month_to);

                sl_type = Integer.parseInt(cmbSL_type);
                System.out.println("sl_type-->" + sl_type);

                sl_code = Integer.parseInt(cmbSL_Code);
                System.out.println("sl_code-->" + sl_code);

            } catch (Exception e) {
                System.out.println("Error in Convert String to Int Data -->" + e);
            }


            String monthInWords = "";
            String monthInWords_to = "";

            try {
                /** Find From Month Value */

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

                /** Find To Month Value */

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

            } catch (Exception e) {
                System.out.println("Error in Calculate Month Value -->" + e);
            }

            
            JasperReport jasperReport = null;
            if (month_year.equalsIgnoreCase("particular_cb")) {
                try {
                	System.out.println("here:");
                    reportFile =
                            new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/Self_Balane_SJV/A26_SelfBalance2.jasper"));
                    if (!reportFile.exists())
                        throw new JRRuntimeException("File J not found. The report design must be compiled first.");
                    jasperReport =
                            (JasperReport)JRLoader.loadObject(reportFile.getPath());
                } catch (JRException e) {
                    System.out.println("Error in Creating Report Object ---> " + e);
                }
            }
            else  if (month_year.equalsIgnoreCase("more_cb")) 
            {
                try {
                	reportFile =
                        new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/Self_Balane_SJV/A26_SelfBalance2.jasper"));
                    if (!reportFile.exists())
                        throw new JRRuntimeException("File J not found. The report design must be compiled first.");
                    jasperReport =
                            (JasperReport)JRLoader.loadObject(reportFile.getPath());
                } catch (JRException e) {
                    System.out.println("Error in Creating Report Object ---> " + e);
                }
                
            }  

            Map map = new HashMap();
            try {
              //  PreparedStatement ps = null;
                
                String UnitName = "", OfficeName = "";
                ps =
      connection.prepareStatement("select ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=?");
                ps.setInt(1, accountingunit);
                rs1 = ps.executeQuery();
                if (rs1.next())
                    UnitName = rs1.getString("ACCOUNTING_UNIT_NAME");

                ps =
      connection.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=?");
                ps.setInt(1, accountingoffice);
                rs1 = ps.executeQuery();
                if (rs1.next())
                    OfficeName = rs1.getString("OFFICE_NAME");


                map.put("accountingunitid", accountingunit);
                map.put("accountofficeid", accountingoffice);
                map.put("cashbookmonth_from", month);
                map.put("cashbookyear_from", year);
                map.put("monthvalue_from", monthInWords);
                map.put("UnitName", UnitName);
                map.put("OfficeName", OfficeName);
                map.put("cashbookmonth_to", month_to);
                map.put("cashbookyear_to", year_to);
                map.put("monthvalue_to", monthInWords_to);
                map.put("sl_type", sl_type);
                map.put("sl_code", sl_code);
                map.put("supplementno", supno);

            } catch (SQLException e) {
                System.out.println("SQL Exception -->" + e);
            }

            try {

                System.out.println("1");
                JasperPrint jasperPrint =
                    JasperFillManager.fillReport(jasperReport, map, connection);
                System.out.println("2");
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
                  
                    response.setContentLength(buf.length);
                  
                    response.setHeader("Content-Disposition",
                                       "attachment;filename=\"SubLedgerReport.pdf\"");
                   
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
                    "Could not create the report  midle" + ex.getMessage() + " " +
                    ex.getLocalizedMessage();
                System.out.println(connectMsg);
            }
         }
            else  if (singleSlCode.equalsIgnoreCase("all_code")) 
            {
            
                System.out.println("SlCode*********"+singleSlCode);
                
                 /** File Object Creation */
                 File reportFile = null;



                 String cmbAcc_UnitCode = "";
                 String cmbOffice_code = "";
                 String txtCB_Year = "";
                 String txtCB_Month = "";
                 String txtCB_Year_to = "";
                 String txtCB_Month_to = "";
                 String rtype = "";
                 String cmbSL_type = "";
                 String cmbSL_Code = "";
                //   String month_year="";
                 
                 int accountingunit = 0;
                 int accountingoffice = 0;
                 int year = 0;
                 int month = 0;
                 int year_to = 0;
                 int month_to = 0;
                 int sl_type = 0;
                 int sl_code = 0;


                String month_year = request.getParameter("month_year");
                
                 if (month_year.equalsIgnoreCase("particular_cb")) {
                     System.out.println("month_year*********"+month_year);
                     txtCB_Year = request.getParameter("txtCB_Year");
                     txtCB_Month = request.getParameter("txtCB_Month");
                     txtCB_Year_to = request.getParameter("txtCB_Year_to");
                     txtCB_Month_to = request.getParameter("txtCB_Month_to");
                }
                 else  if (month_year.equalsIgnoreCase("more_cb")) {
                 
                     txtCB_Year = request.getParameter("txtCB_Year_from");
                     txtCB_Month = request.getParameter("txtCB_Month_from");
                     txtCB_Year_to = request.getParameter("txtCB_Year_to");
                     txtCB_Month_to = request.getParameter("txtCB_Month_to");
                 }
                 
                
                 try {
                     cmbAcc_UnitCode = request.getParameter("cmbAcc_UnitCode");
                     cmbOffice_code = request.getParameter("cmbOffice_code");
                  //   cmbSL_Code = request.getParameter("cmbSL_Code");
                     
                     rtype = request.getParameter("txtoption");
                     cmbSL_type = request.getParameter("cmbSL_type");
                   
                  
                 } catch (Exception e) {
                     System.out.println("Error in Get Parameter -->" + e);
                 }

                 try {
                     /** Convert String Data into int type */
                     accountingunit = Integer.parseInt(cmbAcc_UnitCode);
                     System.out.println("accountingunit-->" + accountingunit);
                     accountingoffice = Integer.parseInt(cmbOffice_code);
                     System.out.println("accountingoffice-->" + accountingoffice);
                     year = Integer.parseInt(txtCB_Year);
                     System.out.println("year-->" + year);
                     month = Integer.parseInt(txtCB_Month);
                     System.out.println("month-->" + month);


                     year_to = Integer.parseInt(txtCB_Year_to);
                     System.out.println("year_to-->" + year_to);

                     month_to = Integer.parseInt(txtCB_Month_to);
                     System.out.println("month_to-->" + month_to);

                     sl_type = Integer.parseInt(cmbSL_type);
                     System.out.println("sl_type-->" + sl_type);

//                     sl_code = Integer.parseInt(cmbSL_Code);
//                     System.out.println("sl_code-->" + sl_code);

                 } catch (Exception e) {
                     System.out.println("Error in Convert String to Int Data -->" + e);
                 }


                 String monthInWords = "";
                 String monthInWords_to = "";

                 try {
                     /** Find From Month Value */

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

                     /** Find To Month Value */

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

                 } catch (Exception e) {
                     System.out.println("Error in Calculate Month Value -->" + e);
                 }
                 
               
                 
                 JasperReport jasperReport = null;
                 if (month_year.equalsIgnoreCase("particular_cb")) {
                	 System.out.println("particular::::::");
                     try {
                         reportFile =
                                 new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/Self_Balane_SJV/A26_SelfBalance2_Alll_oneMonth.jasper"));
                         if (!reportFile.exists())
                             throw new JRRuntimeException("File J not found. The report design must be compiled first.");
                         jasperReport =
                                 (JasperReport)JRLoader.loadObject(reportFile.getPath());
                     } catch (JRException e) {
                         System.out.println("Error in Creating Report Object ---> " + e);
                     }
                 }
                 else  if (month_year.equalsIgnoreCase("more_cb")) 
                 {
                     try {
                    	 reportFile =
                             new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/Self_Balane_SJV/A26_SelfBalance2_Alll_oneMonth.jasper"));
                         if (!reportFile.exists())
                             throw new JRRuntimeException("File J not found. The report design must be compiled first.");
                         jasperReport =
                                 (JasperReport)JRLoader.loadObject(reportFile.getPath());
                     } catch (JRException e) {
                         System.out.println("Error in Creating Report Object ---> " + e);
                     }
                     
                 }  

                 Map map = new HashMap();
                 try {
               
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
                     {
                         OfficeName = rs.getString("OFFICE_NAME");
                      }
                     String stype="";

					if(sl_type!=0)
					{
						stype=" AND s.SUB_LEDGER_TYPE_CODE="+sl_type; 
					}
					else
					{
						stype="";
					}
                     map.put("accountingunitid", accountingunit);
                     map.put("accountofficeid", accountingoffice);
                     map.put("cashbookmonth_from", month);
                     map.put("cashbookyear_from", year);
                     map.put("monthvalue_from", monthInWords);
                     map.put("UnitName", UnitName);
                     map.put("OfficeName", OfficeName);
                     map.put("cashbookmonth_to", month_to);
                     map.put("cashbookyear_to", year_to);
                     map.put("monthvalue_to", monthInWords_to);
                     map.put("sl_type", stype);
                     map.put("supplementno", supno);
                   
                 } catch (SQLException e) {
                     System.out.println("SQL Exception -->" + e);
                 }

                 try {

                     System.out.println("1");
                     JasperPrint jasperPrint =
                         JasperFillManager.fillReport(jasperReport, map, connection);
                     System.out.println("2");
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
                         System.out.println("3");
                         byte buf[] =
                             JasperExportManager.exportReportToPdf(jasperPrint);
                         System.out.println("4");
                         response.setContentType("application/pdf");
                         System.out.println("5");
                         response.setContentLength(buf.length);
                         System.out.println("6");
                         response.setHeader("Content-Disposition",
                                            "attachment;filename=\"SubLedgerReport.pdf\"");
                         System.out.println("7");
                         OutputStream out = response.getOutputStream();
                         System.out.println("8");
                         out.write(buf, 0, buf.length);
                         System.out.println("9");
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
                         "Could not create the report last " + ex.getMessage() + " " +
                         ex.getLocalizedMessage();
                     System.out.println(connectMsg);
                 }
               
             }
            
        }
       
    }
}
