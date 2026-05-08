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

/**
 * Servlet implementation class IBT_Report
 */
public class IBT_Report extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IBT_Report() {
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
		// TODO Auto-generated method stub
		System.out.println("test servlet");
		
		
		Connection con = null;
		String qry=null;
		String heading="";
		long txtBankAccountNo = 0;
		Date txtFrom_date=null,txtTo_date=null;
		Calendar c;
		
		 long l = System.currentTimeMillis();
         Timestamp ts = new Timestamp(l);
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
		String strcommand=request.getParameter("command");
		System.out.println("command:::"+strcommand);
		 //String formattyp=request.getParameter("format_type");
		 String rtype= request.getParameter("txtoption");
		int cboAcc_UnitCode = Integer.parseInt(request
				.getParameter("cmbAcc_UnitCode"));
		int cboOffice_code = Integer.parseInt(request
				.getParameter("cmbOffice_code"));
		/*int txtFrom_date = Integer.parseInt(request
				.getParameter("txtFrom_date"));*/
		 String[] sd = request.getParameter("txtTo_date").split("/");
         c =new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,Integer.parseInt(sd[0]));
         java.util.Date d = c.getTime();
         txtTo_date = new Date(d.getTime());
         String[] sd1 = request.getParameter("txtFrom_date").split("/");
         c =new GregorianCalendar(Integer.parseInt(sd1[2]), Integer.parseInt(sd1[1]) - 1,Integer.parseInt(sd1[0]));
         java.util.Date d1 = c.getTime();
         txtFrom_date = new Date(d1.getTime());
         txtBankAccountNo = Long.parseLong(request
					.getParameter("txtBankAccountNo"));
         System.out.println("txtBankAccountNo servlet..."+txtBankAccountNo);
     	
	/*	try {
			txtBankAccountNo = Long.parseLong(request
					.getParameter("txtBankAccountNo"));
		} catch (NumberFormatException e) {
			System.out.println("exception" + e);
		}*/
		
	    
		System.out.println("rtype::::"+rtype);
		System.out.println("cboAcc_UnitCode:::::::"+cboAcc_UnitCode);
		System.out.println("cboOffice_code:::::::"+cboOffice_code);
		System.out.println("txtBankAccountNo:::::::"+txtBankAccountNo);
		//System.out.println("txtCB_Year_to:::::::"+txtCB_Year_to);
		
		
		/*String monthInWords="";
        if(txtCB_Month_from==1)
        monthInWords="January";
        else if(txtCB_Month_from==2)
        monthInWords="February";
        else if(txtCB_Month_from==3)
        monthInWords="March";
        else if(txtCB_Month_from==4)
        monthInWords="April";
        else if(txtCB_Month_from==5)
        monthInWords="May";
        else if(txtCB_Month_from==6)
        monthInWords="June";
        else if(txtCB_Month_from==7)
        monthInWords="July";
        else if(txtCB_Month_from==8)
        monthInWords="August";
        else if(txtCB_Month_from==9)
        monthInWords="September";
        else if(txtCB_Month_from==10)
        monthInWords="October";
        else if(txtCB_Month_from==11)
        monthInWords="November";
        else if(txtCB_Month_from==12)
        monthInWords="December"; 
         
        String monthInWords1="";
        if(txtCB_Month_to==1)
        monthInWords1="January";
        else if(txtCB_Month_to==2)
        monthInWords1="February";
        else if(txtCB_Month_to==3)
        monthInWords1="March";
        else if(txtCB_Month_to==4)
        monthInWords1="April";
        else if(txtCB_Month_to==5)
        monthInWords1="May";
        else if(txtCB_Month_to==6)
        monthInWords1="June";
        else if(txtCB_Month_to==7)
        monthInWords1="July";
        else if(txtCB_Month_to==8)
        monthInWords1="August";
        else if(txtCB_Month_to==9)
        monthInWords1="September";
        else if(txtCB_Month_to==10)
        monthInWords1="October";
        else if(txtCB_Month_to==11)
        monthInWords1="November";
        else if(txtCB_Month_to==12)
        monthInWords1="December"; */
	
		
		
		heading="List of IBT Transferred To an Account For ";
			
			File reportFile = null;
			
			if(strcommand.equalsIgnoreCase("printFunc"))
			{
			try {
				
									
				System.out.println("calling servlet...");
				reportFile = new File(getServletContext().getRealPath(
						"/org/FAS/FAS1/Reports/InterBankTransferSystem/jasper/IBT_Report.jasper"));
				
					/*System.out.println("calling servlet...");
					reportFile = new File(getServletContext().getRealPath(
							"/org/FAS/FAS1/CivilBudget/jasper/Civil_Budget_Report_3.jasper"));*/
				
				
				//System.out.println("formattyp:::"+formattyp);
				if (!reportFile.exists())
					throw new JRRuntimeException(
							"File J not found. The report design must be compiled first.");

				JasperReport jasperReport = (JasperReport) JRLoader
						.loadObject(reportFile.getPath());
				Map map = null;
				map = new HashMap();
				System.out.println("from_date:::::"+txtFrom_date+"txtTo_date:::"+txtTo_date);
				map.put("query", qry);
				map.put("heading", heading);
				map.put("unitid", cboAcc_UnitCode);
				map.put("officeid", cboOffice_code);
				map.put("Accuntno", txtBankAccountNo);
				map.put("from_date", txtFrom_date);
				map.put("to_date",txtTo_date);
				
				
				
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						jasperReport, map, con);
				System.out.println("upto");
				
				//String rtype = "PDF";// request.getParameter("cmbReportType");
				System.out.println(rtype);
				if (rtype.equalsIgnoreCase("HTML")) {
					response.setContentType("text/html");
					response.setHeader("Content-Disposition",
							"attachment;filename=\"IBT_Report.html\"");
					PrintWriter out = response.getWriter();
					JRHtmlExporter exporter = new JRHtmlExporter();
					// File f=new
					// File(getServletContext().getRealPath("/WEB-INF/Report/"));
					// exporter.setParameter(JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR,true);
					// exporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR_NAME,getServletContext().getRealPath("/WEB-INF/Report/"));
					// exporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR,f);
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
				} else if (rtype.equalsIgnoreCase("PDF")) {
					System.out.println(rtype);
					byte buf[] = JasperExportManager.exportReportToPdf(jasperPrint);
					System.out.println("Length  " + buf.length);
					response.setContentType("application/pdf");
					response.setContentLength(buf.length);
					// response.setHeader("content-disposition",
					// "inline;filename=OpenActionItems.pdf");
					// response.setContentType("application/force-download");

					response.setHeader("Content-Disposition",
							"attachment;filename=\"IBT_Report.pdf\"");
					OutputStream out = response.getOutputStream();
					out.write(buf, 0, buf.length);
					out.close();
				} else if (rtype.equalsIgnoreCase("EXCEL")) {

					response.setContentType("application/vnd.ms-excel");
					response.setHeader("Content-Disposition",
							"attachment;filename=\"IBT_Report.xls\"");
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

				} else if (rtype.equalsIgnoreCase("TXT")) {

					response.setContentType("text/plain");
					response.setHeader("Content-Disposition",
							"attachment;filename=\"IBT_Report.txt\"");

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
				String connectMsg = "Could not create the report "
						+ ex.getMessage() + " uu " + ex.getLocalizedMessage();
				System.out.println(connectMsg);
			}
		}
	}

}
