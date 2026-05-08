package Servlets.FAS.FAS1.BRS.servlets;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
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
import java.math.BigDecimal;
/**
 * Servlet implementation class Breakup_Report_for_Part1
 */
public class Breakup_Report_for_Part1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Breakup_Report_for_Part1() {
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
		Connection con = null;
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
		int cboAcc_UnitCode = Integer.parseInt(request
				.getParameter("cmbAcc_UnitCode"));
		int cboOffice_code = Integer.parseInt(request
				.getParameter("cmbOffice_code"));
		int cboCashBook_Year = Integer.parseInt(request
				.getParameter("cboCashBook_Year"));
		int cboCashBook_Month = Integer.parseInt(request
				.getParameter("cboCashBook_Month"));
		long cmbBankAccNo = Long.parseLong(request
				.getParameter("cmbBankAccNo"));
                String type_one=request.getParameter("typeName");
                System.out.println("type_one::::::::"+type_one);

		String month = null;
		double Opening_Balance=0.0;
		BigDecimal bb=null;
		if(cboCashBook_Month == 1){
			month = "January";
		}else if(cboCashBook_Month == 2){
			month = "Febrary";
		}else if(cboCashBook_Month == 3){
			month = "March";
		}else if(cboCashBook_Month == 4){
			month = "April";
		}else if(cboCashBook_Month == 5){
			month = "May";
		}else if(cboCashBook_Month == 6){
			month = "June";
		}else if(cboCashBook_Month == 7){
			month = "July";
		}else if(cboCashBook_Month == 8){
			month = "August";
		}else if(cboCashBook_Month == 9){
			month = "September";
		}else if(cboCashBook_Month == 10){
			month = "October";
		}else if(cboCashBook_Month == 11){
			month = "November";
		}else if(cboCashBook_Month == 12){
			month = "December";
		}

		try {
		    PreparedStatement ps=null;
		    ResultSet rs=null;
		    
		    ps=con.prepareStatement("Select Decode(Sum(Ob_Part1),Null,0,Sum(Ob_Part1)) As Opening_Balance From Fas_Brs_Ob " +
		    		"Where Accounting_Unit_Id    =? And Accounting_For_Office_Id=? And Cashbook_Year           =? " +
		    		"AND CASHBOOK_MONTH          =? AND ACCOUNT_NO              =?");
		    ps.setInt(1,cboAcc_UnitCode);
		    ps.setInt(2,cboOffice_code);
		    ps.setInt(3,cboCashBook_Year);
		    ps.setInt(4,cboCashBook_Month);
		    ps.setLong(5,cmbBankAccNo);
		    rs=ps.executeQuery();
		    if(rs.next())
		    {
		    	Opening_Balance=rs.getDouble("Opening_Balance");
		    bb=new BigDecimal(Opening_Balance);
		    }
		    }
		    catch (SQLException e) {
		        System.out.println("SQL Exception -->"+e);
		    }
		
		File reportFile = null;
		try {
			System.out.println("calling servlet...");
                        if(type_one.equalsIgnoreCase("onlyBoth"))
                        {
                            reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/BRS/jaspers/Breakup_Report_for_Part1.jasper"));
                        }
                        else  if(type_one.equalsIgnoreCase("onlyNT"))
                        {
                        	reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/BRS/jaspers/Breakup_Report_for_Part1_onlyNT.jasper"));
                        }
		    else  if(type_one.equalsIgnoreCase("onlyT"))
		    {
		    	reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/BRS/jaspers/Breakup_Report_for_Part1_onlyT.jasper"));
		    }
			if (!reportFile.exists())
				throw new JRRuntimeException(
						"File J not found. The report design must be compiled first.");

			JasperReport jasperReport = (JasperReport) JRLoader
					.loadObject(reportFile.getPath());
			
			System.out.println("reportFile****************>"+reportFile);
			
			Map map = null;
			map = new HashMap();

			map.put("UnitId", cboAcc_UnitCode);
			map.put("OfficeId", cboOffice_code);
			map.put("cbyear", cboCashBook_Year);
			map.put("cbmonth", cboCashBook_Month);
			map.put("accNo", cmbBankAccNo);
			map.put("month", month);
			map.put("openingbalance", bb);
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					jasperReport, map, con);
			System.out.println("upto");
			String rtype = "PDF";// request.getParameter("cmbReportType");
			System.out.println(rtype);
			if (rtype.equalsIgnoreCase("HTML")) {
				response.setContentType("text/html");
				response.setHeader("Content-Disposition",
						"attachment;filename=\"Receipt.html\"");
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
						"attachment;filename=\"Receipt.pdf\"");
				OutputStream out = response.getOutputStream();
				out.write(buf, 0, buf.length);
				out.close();
			} else if (rtype.equalsIgnoreCase("EXCEL")) {

				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Disposition",
						"attachment;filename=\"Receipt.xls\"");
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
						"attachment;filename=\"Receipt.txt\"");

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

	