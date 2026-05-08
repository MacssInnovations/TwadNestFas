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
import java.sql.Timestamp;

import java.util.Calendar;
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


public class BRS_Twad_Report extends HttpServlet {
    private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
    Connection connection=null;  
   public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
   {
    }

   
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{/*
		
		    *//**
		     * Session Checking 
		     *//*
	      	HttpSession session=request.getSession(false);
	      	try
	      	{
		            if(session==null)
		            {
			              System.out.println(request.getContextPath()+"/index.jsp");              
			              response.sendRedirect(request.getContextPath()+"/index.jsp");
			              return;
		            }
		            System.out.println(session);
	              
	      	}catch(Exception e)
	      	{
	      			System.out.println("Redirect Error :"+e);
	      	}
	     
	      
	      *//**
	       * Variables Declaration 
	      **//*
	       
	           
	      PreparedStatement ps2=null;     
	      response.setContentType(CONTENT_TYPE);
	      response.setHeader("Cache-Control","no-cache");
	      PrintWriter out = response.getWriter();
	      *//**
	       * Database Connection 
	      *//*      
	      try 
	      {
                    ResourceBundle rs1=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
                    String ConnectionString="";
                 
                    String strDriver=rs1.getString("Config.DATA_BASE_DRIVER");
                    String strdsn=rs1.getString("Config.DSN");
                    String strhostname=rs1.getString("Config.HOST_NAME");
                    String strportno=rs1.getString("Config.PORT_NUMBER");
                    String strsid=rs1.getString("Config.SID");
                    String strdbusername=rs1.getString("Config.USER_NAME");
                    String strdbpassword=rs1.getString("Config.PASSWORD");
                    ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
                    Class.forName(strDriver.trim());
                    con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
	      }
	      catch(Exception e)
	      {
	          		System.out.println("Exception in opening connection :"+e);
	      }
	      
	       Get Parameters  
	      int cmbAcc_UnitCode =0;
	      int cmbOffice_code  =0;
	      int txtCB_Year=0;
	      int txtCB_Month =0;
	      Calendar c;
	      int cmbBankAccNo=0;
	      String txtOprCode=null;      
	      int txtBankID=0;
	      double OB1=0,OB2=0,OB3=0;
	      int txtBranchID=0;//txtCheque_NO=0;
	      
	      String command=request.getParameter("command");
	      try
	      {
		    	    cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
		    	    System.out.println("cmbAcc_UnitCode-->"+cmbAcc_UnitCode);
	      }
	      catch(Exception e)
	      {
	    	  		System.out.println("Error Not Getting Accounitng Unit ID --> "+e);
	      }
	      try
	      {
			    	cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
			    	System.out.println("cmbOffice_code-->"+cmbOffice_code);
	    	  
	      }
	      catch(Exception e)
	      {
	    	  		System.out.println("Error Not Getting Accounting for Office Id --> "+e);
	      }
	     
	      try
	      {
	    	  		cmbBankAccNo = Integer.parseInt(request.getParameter("cmbBankAccNo"));
	    	  		System.out.println("cmbBankAccNo-->"+cmbBankAccNo);
	      }
	      catch(Exception e)
	      {
	    	  		System.out.println("Error Not Getting Bank Account Number -->"+e);
	      }
	      try
	      {
	    	  		txtOprCode = request.getParameter("txtOprCode");
	    	  		System.out.println("txtOprMode-->"+txtOprCode);
	      }
	      catch(Exception e)
	      {
	    	  		System.out.println("Error Not Getting Operation Mode -->"+e);
	      }
     

	      String update_user=(String)session.getAttribute("UserId");
	     // System.out.println("update_user-->"+update_user);

	      long l=System.currentTimeMillis();
	      Timestamp ts=new Timestamp(l);
	      System.out.println("Timestamp -->"+ts); 
	     
	      if(command.equalsIgnoreCase("PDF")){
	    		int fromyear = Integer.parseInt(request.getParameter("txtCB_Year_from"));
	    		int toyear = Integer.parseInt(request.getParameter("txtCB_Year_to"));
	    		int frommonth = Integer.parseInt(request.getParameter("txtCB_Month_from"));
	    		int tomonth = Integer.parseInt(request.getParameter("txtCB_Month_to"));
	    	// char ss='NT';
	    	  System.out.println("inside pdf ");
	    	  JasperDesign jasperDesign = null;
		        File reportFile = null;
		        String rtype="PDF";
		        try{
	    	  reportFile =new File(getServletContext().getRealPath("/org/FAS/FAS1/BRS/jaspers/BRS_TWAD_OB_Report.jasper"));
	          
	            if (!reportFile.exists())
	                throw new JRRuntimeException("File J not found. The report design must be compiled first.");
	            JasperReport jasperReport =
	                (JasperReport)JRLoader.loadObject(reportFile.getPath());
	           // input get from

	            Map map = new HashMap();
	
							 
	            map.put("fromyear", fromyear);
	            map.put("toyear", toyear);
	            map.put("frommonth", frommonth);
	          
	            map.put("tomonth", tomonth);
	            map.put("unitid",cmbAcc_UnitCode);	           
	            map.put("officeid", cmbOffice_code);
	            map.put("accountno",cmbBankAccNo);	           
	            map.put("twad","NT" );
	         
	            System.out.println("map "+map);
	            JasperPrint jasperPrint =JasperFillManager.fillReport(jasperReport, map, con);


	           if (rtype.equalsIgnoreCase("PDF")) {
	            	System.out.println("the option chosen is :::::"+rtype);
	                byte buf[] =JasperExportManager.exportReportToPdf(jasperPrint);
	                response.setContentType("application/pdf");
	                response.setContentLength(buf.length);
	                response.setHeader("Content-Disposition",
	                                   "attachment;filename=\"BRS_OB_BANK.pdf\"");
	                OutputStream out = response.getOutputStream();
	                out.write(buf, 0, buf.length);
	                System.out.println("testing***"+jasperPrint);
	                out.close();
	                
	              	            } 

	        } catch (Exception ex) {
	            String connectMsg =
	                "Could not create the report " + ex.getMessage() + " " +
	                ex.getLocalizedMessage();
	            System.out.println(connectMsg);
	        }
	    	  
	    	  
	      }
	      
	    */  
		
		
		
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

	        String selstr = "";
	        String selspestr = "";
	        String sel = "";
	        String opt = "";
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
    // doGet(request, response);
 	 JasperDesign jasperDesign = null;
	        File reportFile = null;
	        try {
	            //System.out.println("++++++++calling A52Register_OBEntry servlet*****");
	           
			      int cmbAcc_UnitCode =0;
			      int cmbOffice_code  =0;
			      int txtCB_Year=0;
			      int txtCB_Month =0;
			      Calendar c;
			      int cmbBankAccNo=0;
			      String txtOprCode=null;      
			      int txtBankID=0;
			      double OB1=0,OB2=0,OB3=0;
			      int txtBranchID=0;//txtCheque_NO=0;
			      
			      String command=request.getParameter("command");
			      try
			      {
				    	    cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
				    	    System.out.println("cmbAcc_UnitCode-->"+cmbAcc_UnitCode);
			      }
			      catch(Exception e)
			      {
			    	  		System.out.println("Error Not Getting Accounitng Unit ID --> "+e);
			      }
			      try
			      {
					    	cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
					    	System.out.println("cmbOffice_code-->"+cmbOffice_code);
			    	  
			      }
			      catch(Exception e)
			      {
			    	  		System.out.println("Error Not Getting Accounting for Office Id --> "+e);
			      }
			     
			      try
			      {
			    	  		cmbBankAccNo = Integer.parseInt(request.getParameter("cmbBankAccNo"));
			    	  		System.out.println("cmbBankAccNo-->"+cmbBankAccNo);
			      }
			      catch(Exception e)
			      {
			    	  		System.out.println("Error Not Getting Bank Account Number -->"+e);
			      }
			      try
			      {
			    	  		txtOprCode = request.getParameter("txtOprCode");
			    	  		System.out.println("txtOprMode-->"+txtOprCode);
			      }
			      catch(Exception e)
			      {
			    	  		System.out.println("Error Not Getting Operation Mode -->"+e);
			      }
			     // PDFBANK
			    if( command.equalsIgnoreCase("PDFBANK")){
			    	
			   
			      int fromyear = Integer.parseInt(request.getParameter("txtCB_Year_from"));
		    		int toyear = Integer.parseInt(request.getParameter("txtCB_Year_to"));
		    		int frommonth = Integer.parseInt(request.getParameter("txtCB_Month_from"));
		    		int tomonth = Integer.parseInt(request.getParameter("txtCB_Month_to"));
		    		
	           reportFile =new File(getServletContext().getRealPath("/org/FAS/FAS1/BRS/jaspers/BRS_TWAD_OB_Report.jasper"));
			  
	            if (!reportFile.exists())
	                throw new JRRuntimeException("File J not found. The report design must be compiled first.");
	            JasperReport jasperReport =
	                (JasperReport)JRLoader.loadObject(reportFile.getPath());
	           // input get from

	            Map map = new HashMap();
	            map.put("fromyear", fromyear);
	            map.put("toyear", toyear);
	            map.put("frommonth", frommonth);
	            map.put("tomonth", tomonth);
	            map.put("unitid",cmbAcc_UnitCode);	           
	            map.put("officeid", cmbOffice_code);
	            map.put("accountno",cmbBankAccNo);	           
	            map.put("twad","NT" );
				
	            JasperPrint jasperPrint =
	                JasperFillManager.fillReport(jasperReport, map, connection);
	            	String rtype="PDF";
	            	//System.out.println("map"+map );
		
	            if (rtype.equalsIgnoreCase("PDF")) {
	            	System.out.println("the option chosen is :::::"+rtype);
	                byte buf[] =
	                    JasperExportManager.exportReportToPdf(jasperPrint);
	                response.setContentType("application/pdf");
	                response.setContentLength(buf.length);
	                response.setHeader("Content-Disposition",
	                                   "attachment;filename=\"BRS_OB_BANK.pdf\"");
	                OutputStream out = response.getOutputStream();
	                out.write(buf, 0, buf.length);
	                System.out.println("testing***"+jasperPrint);
	                out.close();
	            } 
			    }

	        } catch (Exception ex) {
	            String connectMsg =
	                "Could not create the report " + ex.getMessage() + " " +
	                ex.getLocalizedMessage();
	            System.out.println(connectMsg);
	        }
 
	    
	}


}