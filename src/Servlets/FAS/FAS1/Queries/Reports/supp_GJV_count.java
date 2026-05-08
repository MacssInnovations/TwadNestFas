package Servlets.FAS.FAS1.Queries.Reports;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import antlr.preprocessor.Preprocessor;

import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * Servlet implementation class supp_GJV_count
 */
public class supp_GJV_count extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
	 Connection connection = null;
       
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
	    Connection con = null;
		 PreparedStatement ps=null,ps_l=null;
		    ResultSet rs=null,rs_l=null;
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
	    if(strCommand.equalsIgnoreCase("formReport"))
	    {
	    	 try 
	 	    {
	    		 Map map=new HashMap();
	 	        String alltype=request.getParameter("alltype");
	 	       String rr="";
	            if(alltype.equals("all"))
	            {
	            	rr ="SELECT count(SUPPLEMENT_NO) as total_count, "+
							"  CASHBOOK_YEAR,"+
							" CASHBOOK_MONTH,"+
							" CASHBOOK_YEAR-1||'-'||CASHBOOK_YEAR finyear"+
							" FROM FAS_SUPPLEMENT_GJV group by CASHBOOK_YEAR,CASHBOOK_MONTH order by finyear";
	            }
	            else
	            {
	            	String[] txtCB_Year=(request.getParameter("txtCB_Year")).split("-");
	            	String prevyear=txtCB_Year[0];
	            	String ctyear=txtCB_Year[1];
	            	rr="SELECT count(SUPPLEMENT_NO) as total_count, "+
	            			" CASHBOOK_YEAR,"+
	            			" CASHBOOK_MONTH,"+
	            			" CASHBOOK_YEAR-1||'-'||CASHBOOK_YEAR finyear"+
	            			" FROM FAS_SUPPLEMENT_GJV"+
	            			" WHERE TO_CHAR(CASHBOOK_YEAR"+
	            			" ||'-'"+
	            			" ||CASHBOOK_MONTH)"+
	            			" between to_char("+prevyear+"||'-'||4) and to_char("+ctyear+"||'-'||3)"+
	            			" group by CASHBOOK_YEAR,"+
	            			" CASHBOOK_MONTH order by finyear";
	            	 
	            }
	 	        reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/Queries/jasper/SJV_REPORT.jasper")); 
	 	       map.put("sql", rr);
	 	       
	 	        if (!reportFile.exists())
	 	        throw new JRRuntimeException("File J not found. The report design must be compiled first.");
	 	        
	 	        JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
	 	      
	 	        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, connection);            
	 	        
	 	        	System.out.println("PDF:::::::::::");
	 	                    byte buf[]=JasperExportManager.exportReportToPdf(jasperPrint);
	 	                    response.setContentType("application/pdf");
	 	                    response.setContentLength(buf.length);
	 	                    response.setHeader ("Content-Disposition", "attachment;filename=\"SupList.pdf\"");
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
