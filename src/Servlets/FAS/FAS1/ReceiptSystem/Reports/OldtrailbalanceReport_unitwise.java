package Servlets.FAS.FAS1.ReceiptSystem.Reports;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.math.MathContext;

import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

import java.util.HashMap;
import java.util.Map;

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

public class OldtrailbalanceReport_unitwise extends HttpServlet {
	private static final String CONTENT_TYPE = "text/html; charset=windows-1252";

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
				return;
			}
			System.out.println(session);

		} catch (Exception e) {
			System.out.println("Redirect Error :" + e);
		}
		Connection con = null;
		ResultSet rs = null, rs2 = null, rs3 = null;
		//CallableStatement cs=null,cs1=null;
		PreparedStatement ps = null, ps1 = null, ps2 = null, ps3 = null;
		String xml = "";
		String strCommand = "";
		try {
			ResourceBundle rs1 = 
				ResourceBundle.getBundle("Servlets.Security.servlets.Config");
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
			con = 
				DriverManager.getConnection(ConnectionString, strdbusername.trim(),
						strdbpassword.trim());
		} catch (Exception e) {
			System.out.println("Exception in opening connection :" + e);
			//sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

		}

		response.setContentType(CONTENT_TYPE);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		ResultSet    rs1=null;
		try {
			strCommand = request.getParameter("Command");
			System.out.println("assign..here command..." + strCommand);
		}

		catch (Exception e) {
			System.out.println("Exception in assigning..." + e);
		}         

		/** Get Cash Book Month and Year */
		String txtCB_Year_from=request.getParameter("txtCB_Year_from");
		String txtCB_Year_to=request.getParameter("txtCB_Year_to");
		//  int year=Integer.parseInt(txtCB_Year);
		// int month=Integer.parseInt(txtCB_Month);
		System.out.println("finyearfrom"+txtCB_Year_from);
		System.out.println("month"+txtCB_Year_to);

		//System.out.println("accountheadcode"+accountheadcode);
		if(strCommand.equalsIgnoreCase("loadunit"))
		{  System.out.println("loadunit");
		String CONTENT_TYPE = "text/xml; charset=windows-1252";
		response.setContentType(CONTENT_TYPE);

		int oid=0;
		xml="<response><command>"+strCommand+"</command>";

		try 
		{
			ps=con.prepareStatement("SELECT distinct a.ACCOUNTING_UNIT_ID,b.ACCOUNTING_UNIT_NAME "+
					" FROM FAS_TRAIL_BALANCE_DATA_9507 a,FAS_MST_ACCNT_UNIT_OLD9507 b where a.ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID and a.fin_year between '"+txtCB_Year_from+"' and '"+txtCB_Year_to+"' ") ;

			//   ps.setInt(1,year);
			//    ps.setInt(2,month);
			rs1=ps.executeQuery();

			int cnt=0;
			while(rs1.next())
			{
				xml=xml+"<option>"; 
				xml=xml+"<accounting_unit_id>"+rs1.getInt("ACCOUNTING_UNIT_ID")+"</accounting_unit_id>";                                      
				//                xml=xml+"<accounting_unit_name>"+rs1.getString("ACCOUNTING_UNIT_NAME")+"</accounting_unit_name>";     
				xml=xml+"<accounting_unit_name><![CDATA["+rs1.getString("ACCOUNTING_UNIT_NAME")+"]]></accounting_unit_name>";

				xml=xml+"</option>";  
				cnt++;  
				//System.out.println(cnt+"count");
			}
			if(cnt>0) {
				xml=xml+"<flag>success</flag>"; 
			}
			else if(cnt==0) {
				xml=xml+"<flag>nodata</flag>";
			}
		}


		catch(Exception e)
		{
			System.out.println(e);
			xml=xml+"<flag>failure</flag>";
		}
		xml=xml+"</response>";
		System.out.println(xml);
		out.println(xml);
		}



	}

	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
	{
		Connection connection=null;
		Statement statement=null;
		CallableStatement cs=null;

		try
		{
			ResourceBundle rs=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
			String ConnectionString="";

			String strDriver=rs.getString("Config.DATA_BASE_DRIVER");
			String strdsn=rs.getString("Config.DSN");
			String strhostname=rs.getString("Config.HOST_NAME");
			String strportno=rs.getString("Config.PORT_NUMBER");
			String strsid=rs.getString("Config.SID");
			String strdbusername=rs.getString("Config.USER_NAME");
			String strdbpassword=rs.getString("Config.PASSWORD");

			ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
			Class.forName(strDriver.trim());
			connection=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
			try
			{
				statement=connection.createStatement();
				connection.clearWarnings();
			}
			catch(SQLException e)
			{
				System.out.println("Exception in creating statement:"+e);
			}          
		}
		catch(Exception e)
		{
			System.out.println("Exception in openeing connection:"+e);
		}

		response.setContentType(CONTENT_TYPE);
		// PrintWriter out = response.getWriter();
		HttpSession session=request.getSession(false);
		try
		{
			if(session==null)
			{
				response.sendRedirect(request.getContextPath()+"/index.jsp");
			}
		}catch(Exception e)
		{
			System.out.println("Redirect Error :"+e);
		}
		
		String userid=(String)session.getAttribute("UserId");
		int AccountUnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode2"));
		String Yearfrom=request.getParameter("txtCB_Year_from");
		String Yearto=request.getParameter("txtCB_Year_to");

		System.out.println("Account_unit_Code----------------"+AccountUnitCode+"/"+Yearfrom+"/"+Yearto);
		System.out.println("Report Name---------old_trilbalancefinyear_spUnit");
		
		try 
		{
			File reportFile=null;
			try
			{

				reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/old_trilbalancefinyear_spUnit.jasper"));

				if (!reportFile.exists())
					throw new JRRuntimeException("File J not found. The report design must be compiled first.");
				JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
				Map map=null;
				map = new HashMap();

				map.put("fromfinyear",Yearfrom);
				map.put("tofinyear",Yearto);
				map.put("unit",AccountUnitCode);
                System.out.println("test"+AccountUnitCode+"/"+Yearfrom+"/"+Yearto);
				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map,connection);

				String rtype="PDF";// request.getParameter("cmbReportType");
				
				if (rtype.equalsIgnoreCase("HTML"))   
				{
					response.setContentType("text/html");

					response.setHeader ("Content-Disposition", "attachment;filename=\"Challan.html\"");
					PrintWriter out = response.getWriter();
					JRHtmlExporter exporter = new JRHtmlExporter();
					exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,  false);
					exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
					exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
					exporter.exportReport();
					out.flush();
					out.close();
				}
				else if (rtype.equalsIgnoreCase("PDF"))   
				{    
				byte buf[] = 
					JasperExportManager.exportReportToPdf(jasperPrint);
				response.setContentType("application/pdf");
				response.setContentLength(buf.length);
				//response.setHeader ("Content-Disposition", "attachment;filename=\"Challan.pdf\"");
				OutputStream out = response.getOutputStream();
				out.write(buf, 0, buf.length);
				out.close();
				}
				else if (rtype.equalsIgnoreCase("EXCEL"))   
				{

					response.setContentType("application/vnd.ms-excel");
					response.setHeader ("Content-Disposition", "attachment;filename=\"Challan.xls\"");
					JRXlsExporter exporterXLS = new JRXlsExporter(); 
					exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint); 

					ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
					exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,xlsReport); 
					exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE); 
					exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE); 
					exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE); 
					exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE); 
					exporterXLS.exportReport(); 
					byte []bytes;
					bytes = xlsReport.toByteArray();
					ServletOutputStream ouputStream = response.getOutputStream();
					ouputStream.write(bytes, 0, bytes.length);
					ouputStream.flush();
					ouputStream.close();

				}
				else if (rtype.equalsIgnoreCase("TXT"))   
				{

					response.setContentType("text/plain");
					response.setHeader ("Content-Disposition", "attachment;filename=\"Challan.txt\"");

					JRTextExporter exporter = new JRTextExporter();
					exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
					ByteArrayOutputStream txtReport = new ByteArrayOutputStream();
					exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,txtReport); 
					exporter.setParameter(JRTextExporterParameter.CHARACTER_WIDTH, new Integer(200));
					exporter.setParameter(JRTextExporterParameter.CHARACTER_HEIGHT, new Integer(50));
					exporter.exportReport(); 

					byte []bytes;
					bytes = txtReport.toByteArray();
					ServletOutputStream ouputStream = response.getOutputStream();
					ouputStream.write(bytes, 0, bytes.length);
					ouputStream.flush();
					ouputStream.close();

				}

			} 
			catch (Exception ex) 
			{
				String connectMsg = "Could not create the report " + ex.getMessage();//+ " uu " +  ex.getLocalizedMessage();
				sendMessage(response,"Unable to display the PDF file","ok");
			}
			//////////////////---------------------------- End -----------------

		}catch(Exception e) {
			System.out.println("Exception in Main:"+e);
			try{connection.rollback();}catch(SQLException e1){System.out.println("catch:"+e1);}
			String msg="Trial Balance Has failed to Update";
			msg=msg+"<br><br>";
			sendMessage(response,msg,"ok");

		}
	}


	private void sendMessage(HttpServletResponse response,String msg,String bType)
	{
		try
		{
			String url="org/Library/jsps/Messenger.jsp?message=" + msg + "&button=" + bType;
			response.sendRedirect(url);          
		}
		catch(IOException e)
		{
			System.out.println("ERROR");
		}
	}  


}