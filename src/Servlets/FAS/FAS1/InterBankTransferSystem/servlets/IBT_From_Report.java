package Servlets.FAS.FAS1.InterBankTransferSystem.servlets;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

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
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
public class IBT_From_Report extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public IBT_From_Report() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("test servlet");
		Connection con = null;
		 Calendar c;
		
		
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

		}
		String strcommand=request.getParameter("command");
		long l = System.currentTimeMillis();
         Timestamp ts = new Timestamp(l);
		Date txtTo_date=null,txtFrom_date=null;
		int cboAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
		int cboOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
        String[] sd = request.getParameter("txtTo_date").split("/");
         c =new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,Integer.parseInt(sd[0]));
         java.util.Date d = c.getTime();
         txtTo_date = new Date(d.getTime());
         String[] sd1 = request.getParameter("txtFrom_date").split("/");
         c =new GregorianCalendar(Integer.parseInt(sd1[2]), Integer.parseInt(sd1[1]) - 1,Integer.parseInt(sd1[0]));
         java.util.Date d1 = c.getTime();
         txtFrom_date = new Date(d1.getTime());
     	
         long txtBankAccountNo = Long.parseLong(request
					.getParameter("txtSubBankAccountNo"));
		
		System.out.println("txtBankAccountNo servlet..."+txtBankAccountNo);
		String title="List of IBT Transferred From a Bank Account for the Period ";
			File reportFile = null;
			
			
			try {
				
									
				System.out.println("calling servlet...");
				reportFile = new File(getServletContext().getRealPath(
						"org/FAS/FAS1/Reports/InterBankTransferSystem/jasper/IBT_From_Report.jasper"));
				
				if (!reportFile.exists())
					throw new JRRuntimeException(
							"File J not found. The report design must be compiled first.");

				JasperReport jasperReport = (JasperReport) JRLoader
						.loadObject(reportFile.getPath());
				Map map = null;
				map = new HashMap();
				
				map.put("unitid", cboAcc_UnitCode);
				map.put("officeid", cboOffice_code);
				map.put("Accountno",txtBankAccountNo);
				map.put("heading",title);
				map.put("txtFrom_date", txtFrom_date);
				map.put("txtTo_date", txtTo_date);
				
				
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						jasperReport, map, con);
				System.out.println("upto");
				
				if (strcommand.equalsIgnoreCase("HTML")) {
					response.setContentType("text/html");
					response.setHeader("Content-Disposition",
							"attachment;filename=\"IBT_From_Report.html\"");
					PrintWriter out = response.getWriter();
					JRHtmlExporter exporter = new JRHtmlExporter();
					
					exporter
							.setParameter(
									JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,
									false);
					exporter.setParameter(JRExporterParameter.JASPER_PRINT,
							jasperPrint);
					exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
					exporter.exportReport();
					out.flush();
					out.close();
				} else if (strcommand.equalsIgnoreCase("PDF")) {
					System.out.println("accno::::"+txtBankAccountNo);
					byte buf[] = JasperExportManager.exportReportToPdf(jasperPrint);
					System.out.println("Length  " + buf.length);
					response.setContentType("application/pdf");
					response.setContentLength(buf.length);
					System.out.println("test");
					response.setHeader("Content-Disposition",
							"attachment;filename=\"IBT_From_Report.pdf\"");
					OutputStream out = response.getOutputStream();
					System.out.println("test11111");
					out.write(buf, 0, buf.length);
					out.close();
			
				
				} 
				else if (strcommand.equalsIgnoreCase("EXCEL")) {

					response.setContentType("application/vnd.ms-excel");
					response.setHeader("Content-Disposition",
							"attachment;filename=\"IBT_From_Report.xls\"");
					JRXlsExporter exporterXLS = new JRXlsExporter();
					exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT,
							jasperPrint);

					ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
					exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,
							xlsReport);
					exporterXLS.setParameter(
							JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
							Boolean.FALSE);
					exporterXLS.setParameter(
							JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE,
							Boolean.TRUE);
					exporterXLS.setParameter(
							JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,
							Boolean.FALSE);
					exporterXLS
							.setParameter(
									JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
									Boolean.TRUE);
					exporterXLS.exportReport();
					byte[] bytes;
					bytes = xlsReport.toByteArray();
					ServletOutputStream ouputStream = response.getOutputStream();
					ouputStream.write(bytes, 0, bytes.length);
					ouputStream.flush();
					ouputStream.close();

				} 
			} catch (Exception ex) {
				String connectMsg = "Could not create the report "
						+ ex.getMessage() + " uu " + ex.getLocalizedMessage();
				System.out.println("connectMsg"+connectMsg);
			}
		}
	

}