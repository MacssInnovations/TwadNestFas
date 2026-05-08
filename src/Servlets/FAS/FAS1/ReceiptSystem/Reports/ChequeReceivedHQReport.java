package Servlets.FAS.FAS1.ReceiptSystem.Reports;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

/**
 * Servlet implementation class ChequeReceivedHQReport
 */
public class ChequeReceivedHQReport extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	 private static final String CONTENT_TYPE =
	        "text/html; charset=windows-1252";
	    Connection connection = null;

	    public void init(ServletConfig config) throws ServletException {
	        super.init(config);
	    }
	    
    public ChequeReceivedHQReport() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

	            String txtCB_YearFrom = request.getParameter("txtCB_YearFrom");
	            String txtCB_MonthFrom = request.getParameter("txtCB_MonthFrom");
	            String txtCB_YearTo = request.getParameter("txtCB_YearTo");
	            String txtCB_MonthTo = request.getParameter("txtCB_MonthTo");
	            String rtype = request.getParameter("txtoption");
	            String cmbAcc_UnitCode = request.getParameter("cmbAcc_UnitCode");
	            String cmbOffice_code = request.getParameter("cmbOffice_code");

	            String monthvalue = "";
	            System.out.println("Cashbook Year From :" + txtCB_YearFrom);
	            System.out.println("Cashbook Month: FRom " + txtCB_MonthFrom);
	            System.out.println("Cashbook Year To :" + txtCB_YearTo);
	            System.out.println("Cashbook Month To " + txtCB_MonthTo);
	            System.out.println("accounting unit id is:" + cmbAcc_UnitCode);
	            System.out.println("office code is:" + cmbOffice_code);


	            int accountingunit = Integer.parseInt(cmbAcc_UnitCode);
	            int accountingoffice = Integer.parseInt(cmbOffice_code);

	            int yearFrom = Integer.parseInt(txtCB_YearFrom);
	            int monthFrom = Integer.parseInt(txtCB_MonthFrom);

	            int yearTo = Integer.parseInt(txtCB_YearTo);
	            int monthTo = Integer.parseInt(txtCB_MonthTo);
	            
	            String monthInWords = "";
	            if (monthFrom == 1)
	                monthInWords = "January";
	            else if (monthFrom == 2)
	                monthInWords = "February";
	            else if (monthFrom == 3)
	                monthInWords = "March";
	            else if (monthFrom == 4)
	                monthInWords = "April";
	            else if (monthFrom == 5)
	                monthInWords = "May";
	            else if (monthFrom == 6)
	                monthInWords = "June";
	            else if (monthFrom == 7)
	                monthInWords = "July";
	            else if (monthFrom == 8)
	                monthInWords = "August";
	            else if (monthFrom == 9)
	                monthInWords = "September";
	            else if (monthFrom == 10)
	                monthInWords = "October";
	            else if (monthFrom == 11)
	                monthInWords = "November";
	            else if (monthFrom == 12)
	                monthInWords = "December";

	            
	            String monthInWords1 = "";
	            if (monthTo == 1)
	            	monthInWords1 = "January";
	            else if (monthTo == 2)
	                monthInWords1 = "February";
	            else if (monthTo == 3)
	                monthInWords1 = "March";
	            else if (monthTo == 4)
	                monthInWords1 = "April";
	            else if (monthTo == 5)
	                monthInWords1 = "May";
	            else if (monthTo == 6)
	                monthInWords1 = "June";
	            else if (monthTo == 7)
	                monthInWords1 = "July";
	            else if (monthTo == 8)
	                monthInWords1 = "August";
	            else if (monthTo == 9)
	                monthInWords1 = "September";
	            else if (monthTo == 10)
	                monthInWords1 = "October";
	            else if (monthTo == 11)
	                monthInWords1 = "November";
	            else if (monthTo == 12)
	                monthInWords1 = "December";
	            if (!rtype.equalsIgnoreCase("EXCEL")) {   
	            if (accountingunit == 5) {
	                reportFile =
	                        new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/ChequeReceivedReport_HO.jasper"));
	            } else {
	                reportFile =
	                        new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/ChequeReceivedHQReport_Office.jasper"));
	            }
	            }if (rtype.equalsIgnoreCase("EXCEL")) {
	            	   
		            if (accountingunit == 5) {
		                reportFile =
		                        new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/ChequeReceived_HO.jasper"));
		            } else {
		                reportFile =
		                        new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/ChequeReceivedHQ_Office.jasper"));
		            }
		            
	            }

	            if (!reportFile.exists())
	                throw new JRRuntimeException("File J not found. The report design must be compiled first.");

	            JasperReport jasperReport =
	                (JasperReport)JRLoader.loadObject(reportFile.getPath());


	            System.out.println("opt::" + opt);
	            //   JasperReport jasperReport =     JasperCompileManager.compileReport(jasperDesign);
	            Map map = new HashMap();
	            map.put("accountofficeid", accountingoffice);
	            map.put("accountingunitid", accountingunit);
	            map.put("cashbookyearFrom", yearFrom);
	            map.put("cashbookmonthFrom", monthFrom);
	            map.put("cashbookyearTo", yearTo);
	            map.put("cashbookmonthTo", monthTo);
	           // map.put("monthvalue", monthInWords);
	           // map.put("monthvalue1", monthInWords1);
	            JasperPrint jasperPrint =
	                JasperFillManager.fillReport(jasperReport, map, connection);


	            if (rtype.equalsIgnoreCase("HTML")) {
	                response.setContentType("text/html");
	                response.setHeader("Content-Disposition",
	                                   "attachment;filename=\"ChequeReceivedHQReport.html\"");
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
	                                   "attachment;filename=\"ChequeReceivedHQReport.pdf\"");
	                OutputStream out = response.getOutputStream();
	                out.write(buf, 0, buf.length);
	                out.close();
	            } else if (rtype.equalsIgnoreCase("EXCEL")) {

	                response.setContentType("application/vnd.ms-excel");
	                response.setHeader("Content-Disposition",
	                                   "attachment;filename=\"ChequeReceivedHQReport.xls\"");
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
	                                   "attachment;filename=\"ChequeReceivedHQReport.txt\"");

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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
