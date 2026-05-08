package Servlets.FAS.FAS1.ReceiptSystem.Reports;

import java.awt.print.PrinterJob;
import java.io.IOException;
import java.io.OutputStream;

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
import java.math.BigDecimal;
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


public class CashBookReceiptServlet extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";
    Connection connection = null;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
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

        String selstr = "";
        String selspestr = "";
        String sel = "";
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
        int amt=0;
       

        JasperDesign jasperDesign = null;
        File reportFile = null;
       
       
        PreparedStatement pss,pss2 =null;
        ResultSet rss,rss2 =null;
     
        try {
            System.out.println("calling  CashBookReceipt servlet");

            int txtCB_Year =
                Integer.parseInt(request.getParameter("txtCB_Year"));
            int txtCB_Month =
                Integer.parseInt(request.getParameter("txtCB_Month"));
            int off_id =
                Integer.parseInt(request.getParameter("cmbOffice_code"));

            String fromdate = request.getParameter("txtfromdate");
            String todate = request.getParameter("txttodate");
            String DisMode = request.getParameter("DisMode");

            String rtype = request.getParameter("txtoption");

            String cmbAcc_UnitCode = request.getParameter("cmbAcc_UnitCode");
            String cmbOffice_code = request.getParameter("cmbOffice_code");
            String cmbAccHeadCode = request.getParameter("cmbAccHeadCode");
String hid_value=request.getParameter("hid");

            System.out.println("cmbAccHeadCode is:" + cmbAccHeadCode);
            System.out.println("accounting unit id is:" + cmbAcc_UnitCode);
            System.out.println("office code is:" + cmbOffice_code);
            System.out.println(" txtCB_Year is:" + txtCB_Year);
            System.out.println(" txtCB_Month is:" + txtCB_Month);
        
            int accountingunit = Integer.parseInt(cmbAcc_UnitCode);


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


            Double bankOpeningBal_for_COL = 0.0, bankOpeningBal_for_OPR = 0.0;
            String accountingunit_name = "";

            String accunitName_sql =
                "select  ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=?";
            PreparedStatement ps =
                connection.prepareStatement(accunitName_sql);
            ps.setInt(1, accountingunit);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                accountingunit_name = rs.getString("ACCOUNTING_UNIT_NAME");
            rs.close();
            ps.close();

       /*     String bankBal =
                "select  OPENING_BALANCE as OB_COL  from FAS_MST_BANK_BALANCE where ACCOUNTING_UNIT_ID=? and BANK_ID=? and AC_OPERATIONAL_MODE_ID='COL'";
            ps = connection.prepareStatement(bankBal);
            ps.setInt(1, accountingunit);
            ps.setInt(2, cmbBankId);
            rs = ps.executeQuery();
            if (rs.next())
                bankOpeningBal_for_COL = rs.getDouble("OB_COL");
            rs.close();
            ps.close();

            bankBal =
                    "select OPENING_BALANCE as OB_OPR  from FAS_MST_BANK_BALANCE where ACCOUNTING_UNIT_ID=? and BANK_ID=? and AC_OPERATIONAL_MODE_ID='OPR'";
            ps = connection.prepareStatement(bankBal);
            ps.setInt(1, accountingunit);
            ps.setInt(2, cmbBankId);
            rs = ps.executeQuery();
            if (rs.next())
                bankOpeningBal_for_OPR = rs.getDouble("OB_OPR");
            rs.close();
            ps.close();  */


            System.out.println("fromdate" + fromdate);
            System.out.println("todate" + todate);
String bankname="",Str_dt="";
long	cmbBankAccNo =0; 
String acpara="";String new_param="";
            if (DisMode.equalsIgnoreCase("Bank")) {
            	acpara=" ";
                if (accountingunit == 5) {
                    if (hid_value.equalsIgnoreCase("month_val")) {
                    	Str_dt=" and g.CASHBOOK_YEAR="+txtCB_Year+"  and g.CASHBOOK_MONTH="+txtCB_Month;
                        reportFile =
                        
                                new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/CashBook/BankWise/new_BanklingSection_Report2.jasper"));
                    } else{
                    	
                    	Str_dt=" AND g.vouDate BETWEEN to_date('"+fromdate+"','DD/MM/YYYY') AND to_date('"+todate+"','DD/MM/YYYY')";
                        reportFile =
                                new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/CashBook/BankWise/new_BanklingSection_Report2.jasper"));
                    }
                    } else {
                    if (hid_value.equalsIgnoreCase("month_val")) {
                    	Str_dt=" and g.CASHBOOK_YEAR="+txtCB_Year+"  and g.CASHBOOK_MONTH="+txtCB_Month;
                        reportFile =
                                //new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/CashBook/BankWise/CBREceipt_BankWise_final.jasper"));
                        	new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/CashBook/BankWise/new_BanklingSection_Report2.jasper"));
                    } else {
                    	Str_dt=" AND g.vouDate BETWEEN to_date('"+fromdate+"','DD/MM/YYYY') AND to_date('"+todate+"','DD/MM/YYYY')";
                        reportFile =new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/CashBook/BankWise/new_BanklingSection_Report2.jasper"));
                               // new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/CashBook/BankWise/CBREceipt_BankWise_final.jasper"));
                    }
                }
            } else if (DisMode.equalsIgnoreCase("AccNo")) {
            	bankname=request.getParameter("cmbBankAccNo");
            	if(bankname.equalsIgnoreCase("")){
            		//System.out.println("if");
            		cmbBankAccNo=0;
            	}else{
            		//System.out.println("else ");
            	cmbBankAccNo=Long.parseLong(bankname.split("-")[0]);
            	//acpara="and g.account_no="+cmbBankAccNo;
            	acpara=bankname;
            	}
            	
            	System.out.println("cmbBankAccNo-->" + acpara);
            	try{
            	  PreparedStatement psnew=connection.prepareStatement("select BANK_NAME from FAS_MST_BANKS where BANK_ID =(select BANK_ID from FAS_MST_BANK_BALANCE where BANK_AC_NO="+cmbBankAccNo+")");
            ResultSet rsnew=psnew.executeQuery();
            while(rsnew.next()){
            	new_param=rsnew.getString(1)+" - "+cmbBankAccNo;
            }
            	}catch (Exception e){
            		e.printStackTrace();
            	}
                if (accountingunit == 5) {
                    if (hid_value.equalsIgnoreCase("month_val")) {
                    	Str_dt=" and g.CASHBOOK_YEAR="+txtCB_Year+"  and g.CASHBOOK_MONTH="+txtCB_Month;
                        reportFile =
                                new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/CashBook/BankWise/new_BanklingSection_Report_AccNo.jasper"));
                    } else {
                    	
                    	Str_dt=" AND g.vouDate BETWEEN to_date('"+fromdate+"','DD/MM/YYYY') AND to_date('"+todate+"','DD/MM/YYYY')";
                        reportFile =
                                new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/CashBook/BankWise/new_BanklingSection_Report_AccNo.jasper"));
                    }
                } else {
                    if (hid_value.equalsIgnoreCase("month_val")) {
                    	Str_dt=" and g.CASHBOOK_YEAR="+txtCB_Year+"  and g.CASHBOOK_MONTH="+txtCB_Month;
                        System.out.println("without date new::::");
                        reportFile =
                                new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/CashBook/BankWise/new_BanklingSection_Report_AccNo.jasper"));
                    } else  {
                    	Str_dt=" AND g.vouDate BETWEEN to_date('"+fromdate+"','DD/MM/YYYY') AND to_date('"+todate+"','DD/MM/YYYY')";
                        reportFile =
                                new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/CashBook/BankWise/new_BanklingSection_Report_AccNo.jasper"));
                    }
                }
            }
System.out.println(Str_dt +" >>>>reportFile >>> "+reportFile);

            if (!reportFile.exists())
                throw new JRRuntimeException("File J not found. The report design must be compiled first.");
            JasperReport jasperReport =
                (JasperReport)JRLoader.loadObject(reportFile.getPath());

System.out.println("acpara "+acpara);
            Map map = new HashMap();

            map.put("txtfrom", fromdate);
            map.put("txtto", todate);
            map.put("accountingunitid", accountingunit);
            map.put("accountingofficeid", off_id);
            map.put("accountingunit_name", accountingunit_name);
      
            map.put("txtCB_Year", txtCB_Year);
           
            map.put("Str_dt",Str_dt);
            map.put("txtCB_Month", txtCB_Month);
            
       map.put("new_param", new_param);
            map.put("bankOpeningBal_for_COL", bankOpeningBal_for_COL);
            map.put("bankOpeningBal_for_OPR", bankOpeningBal_for_OPR);
            map.put("monthInWords", monthInWords);
            map.put("acpara",acpara);
      
System.out.println("map value >>> "+map);
            JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, map, connection);


            if (rtype.equalsIgnoreCase("HTML")) {
                response.setContentType("text/html");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"CashBookReceipt.html\"");
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
                                   "attachment;filename=\"CashBookReceipt.pdf\"");
                OutputStream out = response.getOutputStream();
                out.write(buf, 0, buf.length);
                out.close();
            } else if (rtype.equalsIgnoreCase("EXCEL")) {

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"CashBookReceipt.xls\"");
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
                                   "attachment;filename=\"CashBookReceipt.txt\"");

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

    public void doPost(HttpServletRequest request,
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

        String selstr = "";
        String selspestr = "";
        String sel = "";
        String opt = ""; 
        int PassBook_Year =0;
        int PassBook_Month =0;
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
       
       
        PreparedStatement pss,pss2 =null;
        ResultSet rss,rss2 =null;
     
        try {
            System.out.println("calling  CashBookReceipt servlet Do Post Method *******");

            int txtCB_Year =
                Integer.parseInt(request.getParameter("txtCB_Year"));
            int txtCB_Month =
                Integer.parseInt(request.getParameter("txtCB_Month"));

            String fromdate = request.getParameter("txtfromdate");
            String todate = request.getParameter("txttodate");
            String DisMode = request.getParameter("DisMode");

            String rtype = request.getParameter("txtoption");

            String cmbAcc_UnitCode = request.getParameter("cmbAcc_UnitCode");
          //  String cmbOffice_code = request.getParameter("cmbOffice_code");
           // String cmbAccHeadCode = request.getParameter("cmbAccHeadCode");


          //  System.out.println("cmbAccHeadCode is:" + cmbAccHeadCode);
            System.out.println("accounting unit id is:" + cmbAcc_UnitCode);
           System.out.println("DisMode is:" + DisMode);
            System.out.println(" txtCB_Year is:" + txtCB_Year);
            System.out.println(" txtCB_Month is:" + txtCB_Month);
          


            int accountingunit = Integer.parseInt(cmbAcc_UnitCode);


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


            Double bankOpeningBal_for_COL = 0.0, bankOpeningBal_for_OPR = 0.0,PassBook_Balance_OB =0.0,PassBook_Balance_CB =0.0;
            String accountingunit_name = "";

            String accunitName_sql =
                "select  ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=?";
            PreparedStatement ps =
                connection.prepareStatement(accunitName_sql);
            ps.setInt(1, accountingunit);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                accountingunit_name = rs.getString("ACCOUNTING_UNIT_NAME");
            rs.close();
            ps.close();
           // if()

       /*  

            bankBal =
                    "select OPENING_BALANCE as OB_OPR  from FAS_MST_BANK_BALANCE where ACCOUNTING_UNIT_ID=? and BANK_ID=? and AC_OPERATIONAL_MODE_ID='OPR'";
            ps = connection.prepareStatement(bankBal);
            ps.setInt(1, accountingunit);
            ps.setInt(2, cmbBankId);
            rs = ps.executeQuery();
            if (rs.next())
                bankOpeningBal_for_OPR = rs.getDouble("OB_OPR");
            rs.close();
            ps.close();  */
          //added by B.Sathya NIC on 19/12/2014 as Opening and Closing balance are taken from brs_bank_balance_update........
            if(txtCB_Month== 1)
            {
            	PassBook_Year = txtCB_Year-1;
            	PassBook_Month = 12;
            }
            else
            {
            	PassBook_Year = txtCB_Year;
            	PassBook_Month = txtCB_Month -1;
            }
            
            System.out.println(" PASS BOOK YEAR is:" + PassBook_Year);
            System.out.println(" PASS BOOK MONTH is:" + PassBook_Month);

            System.out.println("fromdate::::::::::" + fromdate);
            System.out.println("todate::::::::::::" + todate);
String bankname="",new_param="";
long	cmbBankAccNo =0;double amt_v=0,amt=0,PBBalance=0; 
String acpara="";
            if (DisMode.equalsIgnoreCase("Bank")) {
                if (accountingunit == 5) {
                    if (fromdate.equalsIgnoreCase("") ||
                        todate.equalsIgnoreCase("")) {
                        reportFile =
                               // new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/CashBook/BankWise/CashBookReceipt_withoutDate_BANKING.jasper"));
                        	 new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/CashBook/BankWise/cash_book_Details.jasper"));
                    } else
                        reportFile =
                               // new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/CashBook/BankWise/CashBookReceipt_withDate_BANKING.jasper"));
                        	new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/CashBook/BankWise/cash_book_DetailsDate.jasper"));
                } else {
                    if (fromdate.equalsIgnoreCase("") ||
                        todate.equalsIgnoreCase("")) {
                        reportFile =
                               // new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/CashBook/BankWise/CashBookReceipt_withoutDate_office.jasper"));
                        	new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/CashBook/BankWise/cash_book_Details.jasper"));
                    } else {
                        reportFile =
                              //  new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/CashBook/BankWise/CashBookReceipt_withDate_office.jasper"));
                        	 new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/CashBook/BankWise/cash_book_DetailsDate.jasper"));
                    }
                }
            } else if (DisMode.equalsIgnoreCase("AccNo")) {
            	bankname=request.getParameter("cmbBankAccNo");
            	if(bankname.equalsIgnoreCase("")){
            		//System.out.println("if");
            		cmbBankAccNo=0;
            	}else{
            		//System.out.println("else ");
            	cmbBankAccNo=Long.parseLong(bankname.split("-")[0]);
            	acpara="and a.acno="+cmbBankAccNo;
            	}
            	System.out.println("cmbBankAccNo-->" + cmbBankAccNo);
            	try{
            	   String bankBal =
            		   " SELECT MONTH_OPENING_BALANCE as OB_COL " +
            		   " FROM FAS_GENERAL_LEDGER l " +
            		   " WHERE ACCOUNTING_UNIT_ID=? " +
            		   " AND YEAR                =? " +
            		   " AND MONTH               =? " +
            		   " AND ACCOUNT_HEAD_CODE   = " +
            		   "  (SELECT distinct AC_HEAD_CODE " +
            		   "  FROM FAS_OFFICE_BANK_AC_CURRENT c " +
            		   "  WHERE c.accounting_unit_id=l.accounting_unit_id " +
            		   "  AND c.bank_ac_no          =? " ;
            	   
            		   if(bankname.split("-")[1].equalsIgnoreCase("COL")||bankname.split("-")[1].equalsIgnoreCase("SCH")){
            			   bankBal+= "  AND c.module_id           ='MF004' ";
            			   }else{
            				   bankBal+= "  AND c.module_id           ='MF005' ";
            			   }
            			   
            		   bankBal+=  "  )";
            		   System.out.println("bankBal  >> "+bankBal);
                   ps = connection.prepareStatement(bankBal);
                   ps.setInt(1, accountingunit);
                   ps.setInt(2, txtCB_Year);
                   ps.setInt(3, txtCB_Month);
                   ps.setLong(4, Long.parseLong(bankname.split("-")[0]));
                   rs = ps.executeQuery();
              
                   while (rs.next())
                   { 
                	   amt_v=rs.getDouble("OB_COL");
                   }
                   if(bankname.split("-")[1].equalsIgnoreCase("COL")||bankname.split("-")[1].equalsIgnoreCase("SCH")){
                       bankOpeningBal_for_COL = amt_v;
                       //bankOpeningBal_for_OPR=amt;
                	   }else  {
                		   bankOpeningBal_for_COL = amt_v;
                		   // bankOpeningBal_for_OPR=amt_v;
                	   }
                   rs.close();
                   ps.close();
            	
            	}catch (Exception e) {
					e.printStackTrace();
				}
            	System.out.println("bankOpeningBal_for_COL   :: "+bankOpeningBal_for_COL);
            	System.out.println("bankOpeningBal_for_OPR   :: "+bankOpeningBal_for_OPR);
            	//Finding the Pass Book Balance OB and CB from BRS_Bank_Balance_UPDATE table by sathya
            	try
            	{
            	String PassBookBal = "SELECT X.Pb_Balance AS Passbook_ob, " +
            			"  X.Cr_Dr_Type      AS Passbookob_Ind, " +
            			"  Y.Pb_Balance      AS Passbook_cb, " +
            			"  y.Cr_Dr_Type      AS Passbook_cb_ind " +
            			"FROM Brs_Bank_Balance_Update X, " +
            			"  Brs_Bank_Balance_Update Y " +
            			"WHERE X.Accounting_Unit_Id = Y.Accounting_Unit_Id " +
            			"AND X.Bank_Ac_No           = Y.Bank_Ac_No " +
            			"AND X.ACCOUNTING_UNIT_ID   = ? " +
            			"AND X.Cb_Month             = ? " +
            			"AND X.Cb_Year              = ? " +
            			"AND X.Bank_Ac_No::numeric           =? " +
            			"AND Y.PS_MONTH             = ? " +
            			"AND Y.PS_YEAR              = ? " ;
            	
            	ps = connection.prepareStatement(PassBookBal);
                ps.setInt(1, accountingunit);
                ps.setInt(2, txtCB_Month);
                ps.setInt(3, txtCB_Year);
                ps.setLong(4, Long.parseLong(bankname.split("-")[0]));
                ps.setInt(5, txtCB_Month);
                ps.setInt(6, txtCB_Year);
                rs = ps.executeQuery();
           
                while (rs.next())
                { 
                	PassBook_Balance_CB=rs.getDouble("Passbook_cb");
                	PassBook_Balance_OB=rs.getDouble("Passbook_ob");
                }
                ps.close();
                rs.close();
                System.out.println("Opening Balance of Pass Book :: "+PassBook_Balance_OB);
                System.out.println("Closing Balance of Pass Bokk :::"+PassBook_Balance_CB);
                
            	}
            	catch (Exception e) {
					e.printStackTrace();
				}
            	
            	
            	
            	try{
              	  PreparedStatement psnew=connection.prepareStatement("select BANK_NAME from FAS_MST_BANKS where BANK_ID =(select BANK_ID from FAS_MST_BANK_BALANCE where BANK_AC_NO="+cmbBankAccNo+")");
              ResultSet rsnew=psnew.executeQuery();
              while(rsnew.next()){
              	new_param=rsnew.getString(1)+" - "+cmbBankAccNo;
              }
              	}catch (Exception e){
              		e.printStackTrace();
              	}
            	if (accountingunit == 5) {
                    if (fromdate.equalsIgnoreCase("") ||
                        todate.equalsIgnoreCase("")) {
                        reportFile =
                               // new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/CashBook/AccNoWise/Acc_CashBookReceipt_withoutDate_BANKING.jasper"));
                        	new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/CashBook/BankWise/cash_book_ACCDetails.jasper"));
                    } else {
                        reportFile =
                               // new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/CashBook/AccNoWise/Acc_CashBookReceipt_withDate_BANKING.jasper"));
                        	new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/CashBook/BankWise/cash_book_ACCDetailsDate.jasper"));
                    }
                } else {
                    if (fromdate.equalsIgnoreCase("") ||
                        todate.equalsIgnoreCase("")) {
                        System.out.println("without date new::::");
                        reportFile =
                                //new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/CashBook/AccNoWise/Acc_CashBookReceipt_withoutDate_office.jasper"));
                        	new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/CashBook/BankWise/cash_book_ACCDetails.jasper"));
                        		//new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/ChequeIssue/RegisterChequeReport.jasper"));

                        System.out.println("Coming Here report");
                    } else {
                        reportFile =
                                //new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/CashBook/AccNoWise/Acc_CashBookReceipt_withDate_office.jasper"));
                        	new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/CashBook/BankWise/cash_book_ACCDetailsDate.jasper"));
                    }
                }
            }
            // String reportsDirPath = request.getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/CashBook/BankWise/");
            //  reportsDirPath=reportsDirPath+"/";
           String reportsDirPath = getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/CashBook/BankWise/cash_book_ACCDetails.jasper");
            System.out.println("reportsDirPath****"+reportsDirPath);
            
            String ctxpath = reportsDirPath.substring(0, reportsDirPath.lastIndexOf("cash_book_ACCDetails.jasper"));
            System.out.println("ctxpath****"+ctxpath);
            
       
         
            if (!reportFile.exists())
                throw new JRRuntimeException("File J not found. The report design must be compiled first.");
            JasperReport jasperReport =
                (JasperReport)JRLoader.loadObject(reportFile.getPath());
  
            System.out.println("acpara "+acpara);
            Map map = new HashMap();
          
            /* int cashbookmonth =6;
           int cashbookyear = 2014;
           int accountingunitid =3;
           int accountofficeid =5000;
           String monthvalue = "sree";
           map.put("cashbookyear", cashbookyear);
            map.put("cashbookmonth", cashbookmonth);
            map.put("accountingunitid", accountingunitid);
            map.put("accountofficeid", accountofficeid);
            map.put("monthvalue", monthvalue);*/
          
            
            map.put("txtfrom", fromdate);
            map.put("txtto", todate);
            map.put("accountingunitid", accountingunit);
            map.put("accountingunit_name", accountingunit_name);
            map.put("accountheadcode", fromdate+"="+todate);
            map.put("txtCB_Year", txtCB_Year);
            map.put("txtCB_Month", txtCB_Month);
            
           map.put("txtAcc_No", cmbBankAccNo);
           map.put("SUBREPORT_DIR", ctxpath);
            //map.put("SUBREPORT_DIR", "D:/live_source_dec/twadFas30Dec2014/twadFas/WebContent/org/FAS/FAS1/Reports/ReceiptSystem/jasper/CashBook/New_AccNoWise/");
           map.put("PassBook_OB", PassBook_Balance_OB);
           map.put("PassBook_CB", PassBook_Balance_CB);
            
          /*  if (DisMode.equalsIgnoreCase("Bank")) {
                map.put("cmbBankId", cmbBankId);
            } else if (DisMode.equalsIgnoreCase("AccNo")) {
                map.put("cmbBankAccNo", cmbBankAccNo);
            } */

            map.put("bankOpeningBal_for_COL", bankOpeningBal_for_COL);
           // map.put("bankOpeningBal_for_OPR", bankOpeningBal_for_OPR);
            map.put("monthInWords", monthInWords);
            map.put("acpara", acpara);
            map.put("new_param", new_param);
        //    map.put("BankName", cmbBankName);
          //  map.put("AccountName", AccNumber);
            
System.out.println("map"+map);
System.out.println("map"+reportFile);
            JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, map, connection);


            if (rtype.equalsIgnoreCase("HTML")) {
                response.setContentType("text/html");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"CashBookReceipt.html\"");
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
            	System.out.println("*****inside report PDF Option*****");
             byte buf[] =
                    JasperExportManager.exportReportToPdf(jasperPrint);
                response.setContentType("application/pdf");
                response.setContentLength(buf.length);
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"CashBookReceipt.pdf\"");
                OutputStream out = response.getOutputStream();
                out.write(buf, 0, buf.length);
                System.out.println("report generated in PDF format ****"+buf.length);   
                
                out.flush();
                out.close();
            	                        
            } else if (rtype.equalsIgnoreCase("EXCEL")) {

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"CashBookReceipt.xls\"");
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
                                   "attachment;filename=\"CashBookReceipt.txt\"");

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
