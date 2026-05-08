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


public class FT_FR_report_servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
	 Connection connection = null;
   
    public FT_FR_report_servlet() {
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
	    String strCommand=null;
	  
	    try 
        {        
              strCommand=request.getParameter("Command");
              System.out.println("assign..here command..."+strCommand);           
        }
        catch(Exception e) 
        {
              System.out.println("Exception in assigning..."+e);
        }  
	    System.out.println("strCommand:"+strCommand);
	    if(strCommand.equalsIgnoreCase("OnlyReport"))
	    {
	    	 try 
	 	    {
	 	    	
	 	        System.out.println("inside servlet>>>>>>>>>>>>>>>..");
	 	        String txtCB_Year[]=request.getParameter("txtCB_Year").split("-");
	 	       
	 	        System.out.println("txtCB_Month>>>>"+txtCB_Year[0]);
	 	        int prevYear=Integer.parseInt(txtCB_Year[0]);
	 	        int nextYear=Integer.parseInt(txtCB_Year[1]);
	 	       
	 	    
	 	        String fin=request.getParameter("txtCB_Year");
	 	       
	            
	 	        reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/FundReceiptSystem/jasper/fund_receipt_by_office_before_TB_FORALLUNITS.jasper")); 
	 	          System.out.println("reportFile"+reportFile);   
	 	        if (!reportFile.exists())
	 	        throw new JRRuntimeException("File J not found. The report design must be compiled first.");
	 	        
	 	        JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
	 	       System.out.println("jasperReport"+jasperReport);
	 	        Map map=new HashMap();
	 	      
	 	        map.put("year1",txtCB_Year[0]);
	 	        map.put("year2",txtCB_Year[1]); 
	 	     
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
	    else{
	    try 
	    {
	    	
	        System.out.println("inside servlet>>>>>>>>>>>>>>>..");
	        String txtCB_Year[]=request.getParameter("txtCB_Year").split("-");
	       
	        System.out.println("txtCB_Month>>>>"+txtCB_Year[0]);
	        int prevYear=Integer.parseInt(txtCB_Year[0]);
	        int nextYear=Integer.parseInt(txtCB_Year[1]);
	       
	     //   String rtype= request.getParameter("txtoption");
	        String cmbAcc_UnitCode=request.getParameter("cmbAcc_UnitCode");
	        String cmbOffice_code=request.getParameter("cmbOffice_code");
	        
	        
	        int accountingunit=Integer.parseInt(cmbAcc_UnitCode);
	        int accountingoffice=Integer.parseInt(cmbOffice_code);
	        String fin=request.getParameter("txtCB_Year");
	       
           if(accountingunit==6)
           {
        	   reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/FundReceiptSystem/jasper/com_wing_treasury/fund_receipt_by_office_before_TB_WING.jasper")); 
 	          System.out.println("reportFile 6666:::"+reportFile);  
           }
           else if(accountingunit==3)
           {
        	   reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/FundReceiptSystem/jasper/com_wing_treasury/fund_receipt_by_office_before_TB_WING.jasper")); 
 	          System.out.println("reportFile 333333:::"+reportFile);  
           }
           else{
	        reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/FundReceiptSystem/jasper/fund_receipt_by_office_before_TB.jasper")); 
	          System.out.println("reportFile other::::::"+reportFile);   
           }
	          if (!reportFile.exists())
	        throw new JRRuntimeException("File J not found. The report design must be compiled first.");
	        
	        JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
	       System.out.println("jasperReport"+jasperReport);
	        Map map=new HashMap();
	        map.put("accountofficeid",accountingoffice);
	        map.put("year1",txtCB_Year[0]);
	        map.put("year2",txtCB_Year[1]); 
	        map.put("accountingunit",accountingunit);
	       
	      
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


}
