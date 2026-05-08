package Servlets.FAS.FAS1.Queries.Reports;

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
import java.sql.Timestamp;
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
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;


public class funsTransferReport extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
	Connection connection = null;

	public funsTransferReport() {
		super();

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("comes in FT_FR_report_servlet servlet *dhana");
		String update_user=null;
		int errcode=0;
		Timestamp ts=null;
		try
		{
			HttpSession session=request.getSession(false);
			if(session==null)
			{
				System.out.println(request.getContextPath()+"/index.jsp");
				response.sendRedirect(request.getContextPath()+"/index.jsp");
			}
			System.out.println(session);
			// changes here
			update_user=(String)session.getAttribute("UserId");
			long l=System.currentTimeMillis();
			ts=new Timestamp(l);                      

		}catch(Exception e)
		{
			System.out.println("Redirect Error :"+e);
		}
		String opt="";
		response.setContentType(CONTENT_TYPE);
		try 
		{
			ResourceBundle rs = ResourceBundle.getBundle("Servlets.Security.servlets.Config");
			String ConnectionString = "";
			String strDriver = rs.getString("Config.DATA_BASE_DRIVER");
			String strdsn = rs.getString("Config.DSN");
			String strhostname = rs.getString("Config.HOST_NAME");
			String strportno = rs.getString("Config.PORT_NUMBER");
			String strsid = rs.getString("Config.SID");
			String strdbusername = rs.getString("Config.USER_NAME");
			String strdbpassword = rs.getString("Config.PASSWORD");
			ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection			Class.forName(strDriver.trim());
			connection =DriverManager.getConnection(ConnectionString, strdbusername.trim(),strdbpassword.trim());
		} catch (Exception ex) {
			String connectMsg ="Could not create the connection" + ex.getMessage() + " " + 
					ex.getLocalizedMessage();
			System.out.println(connectMsg);
		}
		JasperDesign jasperDesign = null;
		File reportFile=null;
		int prevYear=0;
		int nextYear=0;
		int next_year2=0;
		int next_year3=0;
		int pre_year1=0;
		int pre_year2=0;
		int pre_year3=0;
		try 
		{

			System.out.println("inside servlet>>>>>>>>>>>>>>>..");
			String txtCB_Year[]=request.getParameter("txtCB_Year").split("-");

			System.out.println("txtCB_Month>>>>"+txtCB_Year[0]);
			prevYear=Integer.parseInt(txtCB_Year[0]);
			nextYear=Integer.parseInt(txtCB_Year[1]);


			String fin=request.getParameter("txtCB_Year");
			String report_Type=request.getParameter("type_report");
			System.out.println("report_Type>>>>"+report_Type);
			next_year2=prevYear+1;
			next_year3=nextYear+1;
			if(report_Type.equalsIgnoreCase("one")){
				reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/Queries/jasper/fundTransferCollect.jasper")); 
				System.out.println("reportFile"+reportFile);   
			}
			else if(report_Type.equalsIgnoreCase("two"))
			{ 

				reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/Queries/jasper/fundTransferCollect1.jasper")); 
				System.out.println("reportFile"+reportFile);  	
			}
			else

			{
				pre_year1=prevYear-1;
				pre_year2=nextYear-1;
				pre_year3=pre_year2+1;
				reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/Queries/jasper/fundTransferCollect2.jasper")); 
				System.out.println("reportFile"+reportFile);  	
			}

			if (!reportFile.exists())
				throw new JRRuntimeException("File J not found. The report design must be compiled first.");

			JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
			System.out.println("jasperReport"+jasperReport);
			Map map=new HashMap();
			if(report_Type.equalsIgnoreCase("one")){
				map.put("year1",prevYear);
				map.put("year2",nextYear); 
			}
			else if(report_Type.equalsIgnoreCase("two"))
			{
				System.out.println("year1"+prevYear);
				System.out.println("year2"+nextYear);
				System.out.println("year3"+nextYear);
				System.out.println("year4"+next_year3);
				map.put("year1",prevYear);
				map.put("year2",nextYear); 
				map.put("year3",nextYear);
				map.put("year4",next_year3); 
			}
			else
			{
				System.out.println(pre_year1);
				System.out.println(pre_year2);
				System.out.println(pre_year2);
				System.out.println(pre_year3);
				map.put("year1",pre_year1);
				map.put("year2",pre_year2);   
				map.put("year3",pre_year2);
				map.put("year4",pre_year3); 
			}

			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, connection);            

			System.out.println("PDF:::::::::::");
			byte buf[]=JasperExportManager.exportReportToPdf(jasperPrint);
			response.setContentType("application/pdf");
			response.setContentLength(buf.length);
			response.setHeader ("Content-Disposition", "attachment;filename=\"FT_FR.pdf\"");
			OutputStream out = response.getOutputStream();
			out.write(buf, 0, buf.length);
			out.close();
		} 
		catch (Exception ex) 
		{
			String connectMsg = 
					"Could not create the report " + ex.getMessage() + " " + 
							ex.getLocalizedMessage();
			System.out.println(connectMsg);
		}

	}


}
