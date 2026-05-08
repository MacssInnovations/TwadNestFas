package Servlets.FAS.FAS1.ReceiptSystem.Reports;

import java.awt.print.PrinterJob;
import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.*;
import java.util.*;
import java.sql.Date;

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


public class ListOfReceiptAccountWise_Single extends HttpServlet {
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


        String selstr = "";
        String selspestr = "";
        String sel = "";
        String opt = "";
        Calendar c = null;
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
        String heading="";
        try {
            System.out.println("calling servlet");

            int txtCB_Year =
                Integer.parseInt(request.getParameter("txtCB_Year"));
            int txtCB_Month =
                Integer.parseInt(request.getParameter("txtCB_Month"));

            String fromdate = request.getParameter("txtfromdate");
            String todate = request.getParameter("txttodate");
            
            
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
        
			if(fromdate=="" || todate=="")
            heading =	"For the Month "+monthInWords+" "+txtCB_Year;
            else
            	  heading =	"For the Period from "+fromdate+" to "+todate;
            String rtype = request.getParameter("txtoption");
            String cmbAcc_UnitCode = request.getParameter("cmbAcc_UnitCode");
            String cmbOffice_code = request.getParameter("cmbOffice_code");
            String cmbAccHeadCode = request.getParameter("cmbAccHeadCode");

            System.out.println("cmbAccHeadCode is:" + cmbAccHeadCode);
            System.out.println("accounting unit id is:" + cmbAcc_UnitCode);
            System.out.println("office code is:" + cmbOffice_code);
            int accountingunit = Integer.parseInt(cmbAcc_UnitCode);
            int accountingoffice = Integer.parseInt(cmbOffice_code);
            int accountheadcode = Integer.parseInt(cmbAccHeadCode);
            
            String accountingunit_name = "";

            String accunitName_sql =
                "select  ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=?";
            PreparedStatement ps =
                connection.prepareStatement(accunitName_sql);
            ps.setInt(1, accountingunit);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                accountingunit_name = rs.getString("ACCOUNTING_UNIT_NAME");
            
            //Date Conversion for Date
            String sd[] = null;
            java.util.Date d = null;
            java.util.Date d1 = null;
            System.out.println("here i come");
            Date txtfrom_date = null, txtto_date = null;

            //Date Conversion for Date
            if (!fromdate.equalsIgnoreCase("") &&
                !todate.equalsIgnoreCase("")) {
                sd = request.getParameter("txtfromdate").split("/");
                c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                d = c.getTime();
                txtfrom_date = new Date(d.getTime());
                System.out.println("txtfromdate " + txtfrom_date);

                sd = request.getParameter("txttodate").split("/");
                c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                d = c.getTime();
                txtto_date = new Date(d.getTime());
                System.out.println("txttodate " + txtto_date);

                System.out.println("fromdate" + txtfrom_date);
                System.out.println("todate" + txtto_date);
            }
            String RptSel=request.getParameter("rptsel");
            System.out.println("RptSel-===>"+RptSel);
            System.out.println("txtCB_Month====>"+txtCB_Month);
            
            
            if(txtCB_Month==3)
            {
            	if(RptSel.equalsIgnoreCase("all"))
            	{
            	
            		if (fromdate.equalsIgnoreCase("") ||
            				todate.equalsIgnoreCase("")) // if any date is empty , then list based on cash book month

            		{
            			System.out.println("ifffffffffff");
            			if(accountingunit==5)
            			{
            				reportFile =new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/TransactionListing/ListOfReportAccountWise_withoutDate_banking.jasper"));
            			}
            			else{
            				reportFile =new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/TransactionListing/ListOfReportAccountWise_withoutDate_office.jasper"));
            			}
            		}
            /* else{
            System.out.println("elseeeeeeeeeeeeeeeeeeeeeeeeee");
                reportFile =     new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/TransactionListing/ListOfReportAccountWise_withDate.jasper"));
            }*/
            		else{
            			System.out.println("elseeeeeeeeeeeeeeeeeeeeeeeeee");
            			if(accountingunit==5){
            				reportFile =new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/TransactionListing/LORAccountWise_withoutDate_bankingNew.jasper"));
                	
            			}else{
            				reportFile =     new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/TransactionListing/LORAccountWise_withoutDate_officeNew.jasper"));
            			}
            		   }
            	}
            	else if(RptSel.equalsIgnoreCase("Regular"))
            	{
            		if (fromdate.equalsIgnoreCase("") ||
            				todate.equalsIgnoreCase("")) // if any date is empty , then list based on cash book month

            		{
            			System.out.println("ifffffffffff");
            			if(accountingunit==5)
            			{
            				reportFile =new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/TransactionListing/ListOfReportAccountWise_withoutDate_banking_Reg.jasper"));
            			}
            			else{
            				reportFile =new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/TransactionListing/ListOfReportAccountWise_withoutDate_office_Reg.jasper"));
            			}
            		}
               		else{
            			System.out.println("elseeeee=======Regular");
            			if(accountingunit==5){
            				reportFile =new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/TransactionListing/LORAccountWise_withoutDate_bankingNew_Reg.jasper"));
                	
            			}else{
            				reportFile =     new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/TransactionListing/LORAccountWise_withoutDate_officeNew_Reg.jasper"));
            			}
            		   }
            		
            	}
            	else
            	{
            		if (fromdate.equalsIgnoreCase("") ||
            				todate.equalsIgnoreCase("")) // if any date is empty , then list based on cash book month

            		{
            			System.out.println("ifffffffffff");
            			if(accountingunit==5)
            			{
            				reportFile =new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/TransactionListing/ListOfReportAccountWise_withoutDate_banking_SJV.jasper"));
            			}
            			else{
            				reportFile =new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/TransactionListing/ListOfReportAccountWise_withoutDate_office_SJV.jasper"));
            			}
            		}
               		else{
            			System.out.println("elseeeee=======Supplement");
            			if(accountingunit==5){
            				reportFile =new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/TransactionListing/LORAccountWise_withoutDate_bankingNew_SJV.jasper"));
                	
            			}else{
            				reportFile =     new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/TransactionListing/LORAccountWise_withoutDate_officeNew_SJV.jasper"));
            			}
            		   }
            	}
            		
            }
            else
            {
            	if (fromdate.equalsIgnoreCase("") ||
                        todate.equalsIgnoreCase("")) // if any date is empty , then list based on cash book month

                    {
                    	System.out.println("ifffffffffff");
                    	if(accountingunit==5)
                    	{
                    		reportFile =new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/TransactionListing/ListOfReportAccountWise_withoutDate_banking.jasper"));
                    	}
                    	else{
                            reportFile =new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/TransactionListing/ListOfReportAccountWise_withoutDate_office.jasper"));
                         }
                    }
            	else{
                        System.out.println("elseeeeeeeeeeeeeeeeeeeeeeeeee");
                        if(accountingunit==5){
                        	reportFile =new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/TransactionListing/LORAccountWise_withoutDate_bankingNew.jasper"));
                        	
                        }else{
                            reportFile =     new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/TransactionListing/LORAccountWise_withoutDate_officeNew.jasper"));
                        }
                    }
            }


            if (!reportFile.exists())
                throw new JRRuntimeException("File J not found. The report design must be compiled first.");

            JasperReport jasperReport =
                (JasperReport)JRLoader.loadObject(reportFile.getPath());


            System.out.println(reportFile+"opt::" + opt);
            //   JasperReport jasperReport =     JasperCompileManager.compileReport(jasperDesign);
            Map map = new HashMap();
            System.out.println("RptSel---------------***********--------------->"+RptSel);
            if(txtCB_Month==3)
            {
            if(RptSel.equalsIgnoreCase("Supplement"))
            {
           	 System.out.println("Inside the Supplement Map");
            	
            	int supno = Integer.parseInt(request.getParameter("supno"));
            	System.out.println("Supplement_No===>"+supno);
           	    map.put("txtCB_Year", txtCB_Year);
                map.put("txtCB_Month", txtCB_Month);
                map.put("monthInWords", monthInWords);
                map.put("txtfrom", txtfrom_date);
                map.put("txtto", txtto_date);
                map.put("heading", heading);
                map.put("ori_todate",  request.getParameter("txttodate"));
                map.put("ori_fromdate",  request.getParameter("txtfromdate"));
                map.put("accountingunitid", accountingunit);
                map.put("accountofficeid", accountingoffice);
                map.put("accountheadcode", accountheadcode);
                map.put("accountingunit_name", accountingunit_name);	
                map.put("supno", supno);
            }
            
            else
            {
            	map.put("txtCB_Year", txtCB_Year);
                map.put("txtCB_Month", txtCB_Month);
                map.put("monthInWords", monthInWords);
                map.put("txtfrom", txtfrom_date);
                map.put("txtto", txtto_date);
                map.put("heading", heading);
                map.put("ori_todate",  request.getParameter("txttodate"));
                map.put("ori_fromdate",  request.getParameter("txtfromdate"));
                map.put("accountingunitid", accountingunit);
                map.put("accountofficeid", accountingoffice);
                map.put("accountheadcode", accountheadcode);
                map.put("accountingunit_name", accountingunit_name);	
            }
            }
            else
            {
            	map.put("txtCB_Year", txtCB_Year);
                map.put("txtCB_Month", txtCB_Month);
                map.put("monthInWords", monthInWords);
                map.put("txtfrom", txtfrom_date);
                map.put("txtto", txtto_date);
                map.put("heading", heading);
                map.put("ori_todate",  request.getParameter("txttodate"));
                map.put("ori_fromdate",  request.getParameter("txtfromdate"));
                map.put("accountingunitid", accountingunit);
                map.put("accountofficeid", accountingoffice);
                map.put("accountheadcode", accountheadcode);
                map.put("accountingunit_name", accountingunit_name);
            }
            
            System.out.println(" map===> "+map);
            JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, map, connection);


            if (rtype.equalsIgnoreCase("HTML")) {
                response.setContentType("text/html");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"ListOfReceiptAccountHeadWise.html\"");
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
                                   "attachment;filename=\"ListOfReceiptAccountHeadWise.pdf\"");
                OutputStream out = response.getOutputStream();
                out.write(buf, 0, buf.length);
                out.flush();
                out.close();
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
        }


    }
}
