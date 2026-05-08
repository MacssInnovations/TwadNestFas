package Servlets.FAS.FAS1.Masters.Reports;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
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

/**
 * Servlet implementation class AA52_Un_Verified_List
 */
public class AA52_Un_Verified_List extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE ="text/html; charset=windows-1252";
	 Connection connection = null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AA52_Un_Verified_List() {
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
		       
				HttpSession session = request.getSession(false);

				try {

					if (session == null) {
						System.out.println(request.getContextPath() + "/index.jsp");
						response.sendRedirect(request.getContextPath() + "/index.jsp");

					}

				} catch (Exception e) {
					System.out.println("Redirect Error :" + e);
				}
				String userid = (String) session.getAttribute("UserId");
				System.out.println("User Id is:" + userid);
				try {
					if (session == null) {
						System.out.println(request.getContextPath() + "/index.jsp");
						response.sendRedirect(request.getContextPath() + "/index.jsp");

					}
					System.out.println(session);

				} catch (Exception e) {
					System.out.println("Redirect Error :" + e);
				}

				

				Servlets.Security.classes.UserProfile empProfile = (Servlets.Security.classes.UserProfile) session
						.getAttribute("UserProfile");
				int empid = empProfile.getEmployeeId();
				String empName = empProfile.getEmployeeName();
				long l = System.currentTimeMillis();
				Timestamp ts = new Timestamp(l);
	  	        JasperDesign jasperDesign = null;
		        File reportFile = null;
		        try {
		            System.out.println("++++++++calling AA52 Un Verified List servlet*****");
		            String cmd="";
			            String rtype="PDF" ;
			           String fyear="";
		            try{
		            fyear=request.getParameter("cmbFinancialYear");	
		      cmd=request.getParameter("command");
		              }catch(Exception e){
		            	System.out.println("input get from jsp---"+e);
		            	
		            }
		             // System.out.println("cmmmd  "+cmd);
		              if(cmd.equalsIgnoreCase("AA52Verified")){
		            	 // System.out.println("AA52Verified   report");
		            	  reportFile =new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/Masters/jasper/AA52_Verified_List.jasper")); 
		              }else if(cmd.equalsIgnoreCase("AA52CircleVerified")){
		            	 // System.out.println("AA52Verified   report");
		            	  reportFile =new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/Masters/jasper/AA52_Circle_Verified_List.jasper")); 
		            	              }
		              else if(cmd.equalsIgnoreCase("AA52UnVerified")){
		            	  reportFile =new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/Masters/jasper/AA52_Unverify_List.jasper")); 
		              }else if(cmd.equalsIgnoreCase("AA52CircleUnVerified")){
		            	  reportFile =new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/Masters/jasper/AA52_Circle_Unverify_List.jasper")); 
		              }
		              else if(cmd.equalsIgnoreCase("AA52QtyFreezeStatus")){
		            	  reportFile =new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/Masters/jasper/AA52_Verified_List_New.jasper")); 
		              }
		          
		          
		          
		            if (!reportFile.exists())
		                throw new JRRuntimeException("File J not found. The report design must be compiled first.");
		            JasperReport jasperReport =(JasperReport)JRLoader.loadObject(reportFile.getPath());

		            Map map = new HashMap();
		            map.put("finyear",fyear);
		            JasperPrint jasperPrint =JasperFillManager.fillReport(jasperReport, map, connection);


		            if (rtype.equalsIgnoreCase("HTML")) {
		            	
		                response.setContentType("text/html");
		                if(cmd.equalsIgnoreCase("AA52Verified")){
		                	  response.setHeader("Content-Disposition","attachment;filename=\"AA52_Verify.html\"");
			              }else if(cmd.equalsIgnoreCase("AA52UnVerified")){
			            	  response.setHeader("Content-Disposition","attachment;filename=\"AA52_UnVerify.html\"");
			              }
			              else if(cmd.equalsIgnoreCase("AA52QtyFreezeStatus")){
			            	  response.setHeader("Content-Disposition","attachment;filename=\"AA52_status.html\"");
			              }
		                
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
		            	
		            	System.out.println("the option chosen is :::::"+rtype);
		                byte buf[] =
		                    JasperExportManager.exportReportToPdf(jasperPrint);
		                response.setContentType("application/pdf");
		                response.setContentLength(buf.length);
		                
		                if(cmd.equalsIgnoreCase("AA52Verified")){
		                	 response.setHeader("Content-Disposition","attachment;filename=\"AA52_Verify.pdf\"");
			              }else if(cmd.equalsIgnoreCase("AA52UnVerified")){
			            	  response.setHeader("Content-Disposition","attachment;filename=\"AA52_UnVerify.pdf\"");
			              }
			              else if(cmd.equalsIgnoreCase("AA52QtyFreezeStatus")){
			            	  response.setHeader("Content-Disposition","attachment;filename=\"AA52_status.pdf\"");
			              }
		               
		                OutputStream out = response.getOutputStream();
		                out.write(buf, 0, buf.length);
		                System.out.println("testing***"+jasperPrint);
		                out.close();
		            } else if (rtype.equalsIgnoreCase("EXCEL")) {

		                response.setContentType("application/vnd.ms-excel");
		                
		                if(cmd.equalsIgnoreCase("AA52Verified")){
		                	 response.setHeader("Content-Disposition","attachment;filename=\"AA52_Verify.xls\"");
			              }else if(cmd.equalsIgnoreCase("AA52UnVerified")){
			            	  response.setHeader("Content-Disposition","attachment;filename=\"AA52_UnVerify.xls\"");
			              }
			              else if(cmd.equalsIgnoreCase("AA52QtyFreezeStatus")){
			            	  response.setHeader("Content-Disposition","attachment;filename=\"AA52_status.xls\"");
			              }
		               
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
		                
		                if(cmd.equalsIgnoreCase("AA52Verified")){
		                	response.setHeader("Content-Disposition","attachment;filename=\"AA52_Verify.txt\"");
			              }else if(cmd.equalsIgnoreCase("AA52UnVerified")){
			            	  response.setHeader("Content-Disposition","attachment;filename=\"AA52_UnVerify.txt\"");
			              }
			              else if(cmd.equalsIgnoreCase("AA52QtyFreezeStatus")){
			            	  response.setHeader("Content-Disposition","attachment;filename=\"AA52_status.txt\"");
			              }

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
