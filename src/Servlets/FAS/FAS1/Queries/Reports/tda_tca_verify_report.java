package Servlets.FAS.FAS1.Queries.Reports;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * Servlet implementation class tda_tca_verify_report
 */
public class tda_tca_verify_report extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
	Connection connection = null;
    
    public tda_tca_verify_report() {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("post");
		String update_user=null;
		  int errcode=0,month=0;
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
	    try {

            strCommand = request.getParameter("command");
            System.out.println("assign..here command..." + strCommand);
        }

        catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }
	   if(strCommand.equals("allUnit"))
	   {
		   System.out.println("For Single Unit::");
			    	 try 
			 	    {
			 	     int fin=Integer.parseInt(request.getParameter("txtCB_Year"));
			 	       if(fin==2011)
			 	       {
			 	    	  month=3; 
			 	       }
			 	       else
			 	       {
			 	    	  month=0; 
			 	       }
			            
			 	        reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/Queries/jasper/tda_tca_verification_report.jasper")); 
			 	          System.out.println("reportFile"+reportFile);   
			 	        if (!reportFile.exists())
			 	        throw new JRRuntimeException("File J not found. The report design must be compiled first.");
			 	        
			 	        JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
			 	       System.out.println("jasperReport"+jasperReport);
			 	        Map map=new HashMap();
			 	      System.out.println("fin::"+fin+":::month:::"+month);
			 	        map.put("year1",fin);
			 	       map.put("month",month);
			 	       
			 	        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, connection);            
			 	        
			 	        	System.out.println("PDF:::::::::::");
			 	                    byte buf[]=JasperExportManager.exportReportToPdf(jasperPrint);
			 	                    response.setContentType("application/pdf");
			 	                    response.setContentLength(buf.length);
			 	                    response.setHeader ("Content-Disposition", "attachment;filename=\"TDA_TCA_VERIFICATION.pdf\"");
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
	   else
	   {
		   System.out.println("For Particular Unit::");
		   try 
	 	    {
			   
			   int cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
			   int cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));
			   
	 	     int fin=Integer.parseInt(request.getParameter("txtCB_Year"));
	 	       if(fin==2011)
	 	       {
	 	    	  month=3; 
	 	       }
	 	       else
	 	       {
	 	    	  month=0; 
	 	       }
	            
	 	        reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/Queries/jasper/tda_tca_verification_report_singleUnit.jasper")); 
	 	          System.out.println("reportFile"+reportFile);   
	 	        if (!reportFile.exists())
	 	        throw new JRRuntimeException("File J not found. The report design must be compiled first.");
	 	        
	 	        JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
	 	       System.out.println("jasperReport"+jasperReport);
	 	        Map map=new HashMap();
	 	      System.out.println("fin::"+fin+":::month:::"+month);
	 	        map.put("year1",fin);
	 	       map.put("month",month);
	 	      map.put("unitId",cmbAcc_UnitCode);
	 	       
	 	        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, connection);            
	 	        
	 	        	System.out.println("PDF:::::::::::");
	 	                    byte buf[]=JasperExportManager.exportReportToPdf(jasperPrint);
	 	                    response.setContentType("application/pdf");
	 	                    response.setContentLength(buf.length);
	 	                    response.setHeader ("Content-Disposition", "attachment;filename=\"TDA_TCA_VERIFICATION.pdf\"");
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
