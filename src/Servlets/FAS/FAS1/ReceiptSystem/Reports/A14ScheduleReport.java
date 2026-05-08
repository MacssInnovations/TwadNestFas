package Servlets.FAS.FAS1.ReceiptSystem.Reports;

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
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * Servlet implementation class A14ScheduleReport
 */
public class A14ScheduleReport extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=windows-1252"; 
	 Connection connection = null;
	 
    public A14ScheduleReport() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 doGet(request, response);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 try
	        {
	            HttpSession session=request.getSession(false);
	            if(session==null)
	            {
	                System.out.println(request.getContextPath()+"/index.jsp");
	                response.sendRedirect(request.getContextPath()+"/index.jsp");
	            
	            }
	            System.out.println(session);
	                
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

	            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
	            Class.forName(strDriver.trim());
	            connection =DriverManager.getConnection(ConnectionString, strdbusername.trim(),strdbpassword.trim());
	        } catch (Exception ex) {
	            String connectMsg ="Could not create the connection" + ex.getMessage() + " " + 
	            ex.getLocalizedMessage();
	            System.out.println(connectMsg);
	        }
	        JasperDesign jasperDesign = null;
	        File reportFile=null;
	        
	        try 
	        {
	        	System.out.println("A14Schedule  Checking...1");
	           
	            String txtCB_Year=request.getParameter("txtCB_Year");
	            String txtCB_Month=request.getParameter("txtCB_Month");
	                       
	            String rtype= request.getParameter("txtoption");
	            String cmbAcc_UnitCode=request.getParameter("cmbAcc_UnitCode");
	            String cmbOffice_code=request.getParameter("cmbOffice_code");
	          
	          
	            int accountingunit=Integer.parseInt(cmbAcc_UnitCode);
	            int accountingoffice=Integer.parseInt(cmbOffice_code);
	                
	            int yearfrom=Integer.parseInt(txtCB_Year);
	            int monthfrom=Integer.parseInt(txtCB_Month);
	            String monthInWords="";
	            if(monthfrom==1)
	            monthInWords="January";
	            else if(monthfrom==2)
	            monthInWords="February";
	            else if(monthfrom==3)
	            monthInWords="March";
	            else if(monthfrom==4)
	            monthInWords="April";
	            else if(monthfrom==5)
	            monthInWords="May";
	            else if(monthfrom==6)
	            monthInWords="June";
	            else if(monthfrom==7)
	            monthInWords="July";
	            else if(monthfrom==8)
	            monthInWords="August";
	            else if(monthfrom==9)
	            monthInWords="September";
	            else if(monthfrom==10)
	            monthInWords="October";
	            else if(monthfrom==11)
	            monthInWords="November";
	            else if(monthfrom==12)
	            monthInWords="December"; 
	            
	            reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/A14ScheduleReport.jasper")); 
	                 
	            if (!reportFile.exists())
	            throw new JRRuntimeException("File J not found. The report design must be compiled first.");
	            
	            JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
	           
	            Map map=new HashMap();
	           // System.out.println("accountingunitid"+accountingunit);
	         //   System.out.println("accountingoffice"+accountingoffice);
	            //System.out.println("monthfrom"+monthfrom);
	            //System.out.println("yearfrom"+yearfrom);
	            
	            //try {
                   /*PreparedStatement ps=null;
                   ResultSet rs=null;
                   String UnitName="",OfficeName="";
                   ps=connection.prepareStatement("select ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=?");
                   ps.setInt(1,accountingunit);
                   rs=ps.executeQuery();
                   if(rs.next())
                        UnitName=rs.getString("ACCOUNTING_UNIT_NAME");*/
                
                map.put("Accounting_unit_id",accountingunit);
   	            map.put("Accounting_forofficeid",accountingoffice);
   	            map.put("year",yearfrom);
   	            map.put("month",monthfrom); 
   	            map.put("monthwords", monthInWords);
   	            
   	         System.out.println("map values"+map);
   	            
	           /* }
	            catch (Exception e) {
	                System.out.println("SQL Exception -->"+e);
	            }*/
	           
	         
	            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, connection);
	           
	            if (rtype.equalsIgnoreCase("HTML"))   
	            {
	            	
	                        response.setContentType("text/html");
	                        response.setHeader ("Content-Disposition", "attachment;filename=\"A14ScheduleReport.html\"");
	                        PrintWriter out = response.getWriter();
	                        JRHtmlExporter exporter = new JRHtmlExporter();
	                       
	                        exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,  false);
	                        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
	                        exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
	                        exporter.exportReport();
	                        out.flush();
	                        out.close();
	            }
	            else      if (rtype.equalsIgnoreCase("PDF"))   
	            {
	            	
	                        byte buf[]=JasperExportManager.exportReportToPdf(jasperPrint);
	                        response.setContentType("application/pdf");
	                        response.setContentLength(buf.length);
	                                         
	                        response.setHeader ("Content-Disposition", "attachment;filename=\"A14ScheduleReport.pdf\"");
	                        OutputStream out = response.getOutputStream();
	                        out.write(buf, 0, buf.length);
	                        out.close();
	            }
	            else      if (rtype.equalsIgnoreCase("EXCEL"))   
	            {
	    
	            	
	                    	response.setContentType("application/vnd.ms-excel");
		                    response.setHeader ("Content-Disposition", "attachment;filename=\"A14ScheduleReport.xls\"");
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
	            else      if (rtype.equalsIgnoreCase("TXT"))   
	            {
	            	
	            
			                response.setContentType("text/plain");
			                response.setHeader ("Content-Disposition", "attachment;filename=\"A14ScheduleReport.txt\"");
			                     
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
	            String connectMsg = 
	            "Could not create the report " + ex.getMessage() + " " + 
	            ex.getLocalizedMessage();
	            System.out.println(connectMsg);
	        }
	        
	}

}
