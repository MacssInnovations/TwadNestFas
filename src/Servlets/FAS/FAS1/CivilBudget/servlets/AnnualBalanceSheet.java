package Servlets.FAS.FAS1.CivilBudget.servlets;

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
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;




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

/**
 * Servlet implementation class Civil_Budget_Format_Report_1
 */
public class AnnualBalanceSheet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AnnualBalanceSheet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("testing Annaul balance Sheeting");
		
		Connection con = null;
		String qry="",qry1="",Format="",Format1="",Main_desc="";
		File reportFile = null;
		int format_ac_lst_yr=0;
		String u_desc="",u_value_no="",u_valie_desc="";
		//PreparedStatement ps=null;
	//	ResultSet rs=null;
		StringBuilder sb=new StringBuilder();
		StringBuilder sb1=new StringBuilder();
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
			ResourceBundle rs1 = ResourceBundle
					.getBundle("Servlets.Security.servlets.Config");
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
			con = DriverManager.getConnection(ConnectionString, strdbusername
					.trim(), strdbpassword.trim());
		} catch (Exception e) {
			System.out.println("Exception in opening connection :" + e);
			// sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

		}
		
		 try {


	            int cmbAcc_UnitCode = 0,txtCB_Month=0,txtCB_Year=0;
	            int cmbOffice_code = 0;
	            int txtCB_Year_from = 0;
	            int txtCB_Month_from = 0;
	            int txtCB_Year_to = 0;
	            int txtCB_Month_to = 0;
	            String cmbBankAccNo = "";
	            String rtype = "";
	            String monthInWords="",monthInWords1 = "";
	            String head="";

	          
String hid="";
int month_from = 0;
int month_to = 0;




					try {
						txtCB_Year_from =
					            Integer.parseInt(request.getParameter("txtCB_Year_from"));
					} catch (Exception e) {
					    System.out.println("Error getting CB Month " + e);
					}

	            try {
	            	txtCB_Month_from =
	                        Integer.parseInt(request.getParameter("txtCB_Month_from"));
	            } catch (Exception e) {
	                System.out.println("Error getting CB Month " + e);
	            }
	         
	            try {
	            	txtCB_Year_to =
	                        Integer.parseInt(request.getParameter("txtCB_Year_to"));
	            } catch (Exception e) {
	                System.out.println("Error getting CB Year " + e);
	            }

	            try {
	            	txtCB_Month_to =
	                        Integer.parseInt(request.getParameter("txtCB_Month_to"));
	            } catch (Exception e) {
	                System.out.println("Error getting CB Month " + e);
	            }
	      
	            try {
	                rtype = request.getParameter("txtoption");
	            } catch (Exception e) {
	                System.out.println("Error getting report type " + e);
	            }
	         
	            System.out.println("Cashbook Month from:" + txtCB_Month_from);
	            System.out.println("Cashbook Year_from:" + txtCB_Year_from);
	            System.out.println("Cashbook Year to:" + txtCB_Year_to);
	            System.out.println("Cashbook month to:" + txtCB_Month_to);
	            month_from=txtCB_Month_from;
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
	            month_to=txtCB_Month_to;          
	           
	            if (month_to == 1)
	                monthInWords1 = "January";
	            else if (month_to == 2)
	                monthInWords1 = "February";
	            else if (month_to == 3)
	                monthInWords1 = "March";
	            else if (month_to == 4)
	                monthInWords1 = "April";
	            else if (month_to == 5)
	                monthInWords1 = "May";
	            else if (month_to == 6)
	                monthInWords1 = "June";
	            else if (month_to == 7)
	                monthInWords1 = "July";
	            else if (month_to == 8)
	                monthInWords1 = "August";
	            else if (month_to == 9)
	                monthInWords1 = "September";
	            else if (month_to == 10)
	                monthInWords1 = "October";
	            else if (month_to == 11)
	                monthInWords1 = "November";
	            else if (month_to == 12)
	                monthInWords1 = "December";
	          
		 
	         
	           
	     
	reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/CivilBudget/jasper/AnnualAccSub_Main.jasper"));
	System.out.println("report file path"+reportFile.getAbsolutePath());
//added to send directory path for sub reports
	            String reportsDirPath = getServletContext().getRealPath("/WEB-INF/ReportSrc/AnnualAccSubReport_one.jasper");
	            System.out.println("reportsDirPath****"+reportsDirPath);
	            
	            String ctxpath = reportsDirPath.substring(0, reportsDirPath.lastIndexOf("AnnualAccSubReport_one.jasper"));
	            System.out.println("ctxpath****"+ctxpath);
	            
				if (!reportFile.exists())
				throw new JRRuntimeException(
					"File J not found. The report design must be compiled first.");
				
				JasperReport jasperReport = (JasperReport) JRLoader
				.loadObject(reportFile.getPath());
				Map map = null;
				map = new HashMap();				
				map.put("CASHBOOK_MONTH_FROM",txtCB_Month_from );
				map.put("CASHBOOK_YEAR_FROM",txtCB_Year_from);
				map.put("CASHBOOK_MONTH_TO",txtCB_Month_to );
				map.put("CASHBOOK_YEAR_TO",txtCB_Year_to);
				map.put("SUBREPORT_DIR", ctxpath);
				map.put("SUBREPORT_DIR1", ctxpath);
				
			/*	System.out.println("heading  "+head);
				System.out.println("qry  "+qry);*/
				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, con);
				System.out.println("upto");
				System.out.println(rtype);

	        


	            if (rtype.equalsIgnoreCase("HTML")) {
	                response.setContentType("text/html");
	                response.setHeader("Content-Disposition",
	                                   "attachment;filename=\"Annual_Balance_Sheet.html\"");
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
	                                   "attachment;filename=\"Annual_Balance_Sheet.pdf\"");
	                OutputStream out = response.getOutputStream();
	                out.write(buf, 0, buf.length);
	                out.close();
	            } else if (rtype.equalsIgnoreCase("EXCEL")) {

	                response.setContentType("application/vnd.ms-excel");
	                response.setHeader("Content-Disposition",
	                                   "attachment;filename=\"Annual_Balance_Sheet.xls\"");
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
	                                   "attachment;filename=\"Annual_Balance_Sheet.txt\"");

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