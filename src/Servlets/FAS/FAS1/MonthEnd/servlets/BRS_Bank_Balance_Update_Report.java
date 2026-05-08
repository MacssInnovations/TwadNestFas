package Servlets.FAS.FAS1.MonthEnd.servlets;

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
 * Servlet implementation class BRS_Bank_Balance_Update_Report
 */
public class BRS_Bank_Balance_Update_Report extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private static final String CONTENT_TYPE ="text/html; charset=windows-1252";
	    Connection connection = null;

	    public void init(ServletConfig config) throws ServletException {
	        super.init(config);
	    }
    public BRS_Bank_Balance_Update_Report() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("inside BRS_Bank_Balance_Update_Report");
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
	           

	           // String monthvalue = "";
	            System.out.println("Cashbook Year  :" + txtCB_Year);
	            System.out.println("Cashbook Month:  " + txtCB_Month);
	           
	            int year = Integer.parseInt(txtCB_Year);
	            int month = Integer.parseInt(txtCB_Month);

	           
	            String monthInWords = "";
	            if (month == 1)
	                monthInWords = "Jan";
	            else if (month == 2)
	                monthInWords = "Feb";
	            else if (month== 3)
	                monthInWords = "Mar";
	            else if (month == 4)
	                monthInWords = "Apr";
	            else if (month == 5)
	                monthInWords = "May";
	            else if (month == 6)
	                monthInWords = "Jun";
	            else if (month == 7)
	                monthInWords = "Jul";
	            else if (month== 8)
	                monthInWords = "Aug";
	            else if (month== 9)
	                monthInWords = "Sep";
	            else if (month == 10)
	                monthInWords = "Oct";
	            else if (month== 11)
	                monthInWords = "Nov";
	            else if (month == 12)
	                monthInWords = "Dec";

	           
	                reportFile =
	                        new File(getServletContext().getRealPath("/org/FAS/FAS1/MonthEnd/reports/BRS_Bank_BalanceUpdateReport.jasper"));
	              //  WebContent/org/FAS/FAS1/MonthEnd  /org/FAS/FAS1/MonthEnd/reports
	            if (!reportFile.exists())
	                throw new JRRuntimeException("File J not found. The report design must be compiled first.");

	            JasperReport jasperReport =
	                (JasperReport)JRLoader.loadObject(reportFile.getPath());


	            System.out.println("opt::" + opt);
	            //   JasperReport jasperReport =     JasperCompileManager.compileReport(jasperDesign);
	            Map map = new HashMap();
	           
	            map.put("cbyear", year);
	            map.put("cbmonth", month);
	            map.put("cbmonthwords", monthInWords);
	           /* map.put("cashbookmonthTo", monthTo);
	            map.put("monthvalue", monthInWords);*/
	           // map.put("monthvalue1", monthInWords1);
	            JasperPrint jasperPrint =
	                JasperFillManager.fillReport(jasperReport, map, connection);


	            if (rtype.equalsIgnoreCase("HTML")) {
	                response.setContentType("text/html");
	                response.setHeader("Content-Disposition",
	                                   "attachment;filename=\"BRS_Bank_BalanceUpdate_Report.html\"");
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
	                                   "attachment;filename=\"BRS_Bank_BalanceUpdate_Report.pdf\"");
	                OutputStream out = response.getOutputStream();
	                out.write(buf, 0, buf.length);
	                out.close();
	            } else if (rtype.equalsIgnoreCase("EXCEL")) {

	                response.setContentType("application/vnd.ms-excel");
	                response.setHeader("Content-Disposition",
	                                   "attachment;filename=\"BRS_Bank_BalanceUpdate_Report.xls\"");
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
	                                   "attachment;filename=\"BRS_Bank_BalanceUpdate_Report.txt\"");

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
