package Servlets.FAS.FAS1.JournalSystem.Reports;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

import Servlets.Security.classes.UserProfile;

/**
 * Servlet implementation class journal_list_report
 */
public class journal_list_report extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public journal_list_report() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("ttttttttttttttttttttt");
		 response.setHeader("Cache-Control", "no-cache");
         String CONTENT_TYPE = "text/xml; charset=windows-1252";
         response.setContentType(CONTENT_TYPE);
        
        
         String cmd;
         int major;
         int unitid=0,offid=0,invoiceNo=0,billno=0,headcode=0;
         String todate="";
         int agreeno=0,count=0;
         String isection="",expen="";
         String xml="",sql="";;
         
         Connection con=null,connection=null;
         PreparedStatement ps;
         Statement st=null;
         ResultSet result=null;
         int eid=0;
         cmd=request.getParameter("command");
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
                   con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
                   try
                   {
                         st=con.createStatement();
                         con.clearWarnings();
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
         
         HttpSession session=request.getSession(false);
         UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
         eid=empProfile.getEmployeeId();
         int cmbAcc_UnitCode=0,cmbOffice_code=0,txtCB_Month=0,txtCB_Year=0,advnumber=0;
         System.out.println("employee id:"+eid);
          try
          {
             
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
          String userid=(String)session.getAttribute("UserId");
        //  System.out.println("session id is:"+userid);
          long l=System.currentTimeMillis();
          Timestamp ts=new Timestamp(l);
          
          try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
          catch(NumberFormatException e){System.out.println("exception"+e );}
          
          try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
          catch(NumberFormatException e){System.out.println("exception"+e );}
         
          try{txtCB_Year=Integer.parseInt(request.getParameter("txtCB_Year"));}
          catch(NumberFormatException e){System.out.println("exception"+e );}
          
          try{txtCB_Month=Integer.parseInt(request.getParameter("txtCB_Month"));}
          catch(NumberFormatException e){System.out.println("exception"+e );}
          
          
          if(cmd.equalsIgnoreCase("callVoucherNo"))
          {  
              PrintWriter out = response.getWriter();
        //  System.out.println("callVoucherNo...........");
             xml="<response><command>voucher_no</command>"; 
              try 
                      {
                              ps = con.prepareStatement("select voucher_no from FAS_JOURNAL_MASTER " +
                              " where ACCOUNTING_UNIT_ID    ="+cmbAcc_UnitCode+"	\n" + 
                              "  AND ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+"\n" + 
                              "  AND CASHBOOK_YEAR           ="+txtCB_Year+"\n" + 
                              "  AND CASHBOOK_MONTH          ="+txtCB_Month+" AND JOURNAL_STATUS           ='L' ");
                              result = ps.executeQuery();                                
                              while(result.next()) 
                              {
                                  xml=xml+"<voucherno>"+result.getInt("voucher_no")+"</voucherno>";
                                
                                  count++;
                              }
                              if(count>0)
                                  xml=xml+"<flag>success</flag>";
                              else
                                  xml=xml+"<flag>failure</flag>";
                      }
                catch(Exception e) 
                      {
                              System.out.println("Exception in advno ===> "+e);   
                              xml=xml+"<flag>failure</flag>";  
                      }
                  xml=xml+"</response>";
                  System.out.println("xml ::"+xml);
                  out.println(xml);
                  out.close();
          }  
          else if(cmd.equalsIgnoreCase("viewReport"))
          {
        	  JasperDesign jasperDesign = null;
        	    File reportFile=null;
        		
        	    try 
        	    {
        	       
        	        String txtCB_Year_new=request.getParameter("txtCB_Year");
        	        String txtCB_Month_new=request.getParameter("txtCB_Month");
        	        
        	       
        	        String rtype= request.getParameter("txtoption");
        	        String cmbAcc_UnitCode_new=request.getParameter("cmbAcc_UnitCode");
        	        String cmbOffice_code_new=request.getParameter("cmbOffice_code");
        	        String voucher_no_new=request.getParameter("voucher_no");
        	        
        	        int accountingunit=Integer.parseInt(cmbAcc_UnitCode_new);
        	        int accountingoffice=Integer.parseInt(cmbOffice_code_new);
        	        int yearfrom=Integer.parseInt(txtCB_Year_new);
        	        int monthfrom=Integer.parseInt(txtCB_Month_new);
        	        int vou_no=Integer.parseInt(voucher_no_new);
        	        
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
        	        
        	         
 String type_year="List Of Journals For The Month of "+monthInWords+" "+yearfrom;
        	                
        	            if (rtype.equalsIgnoreCase("PDF"))   
        	        {
        	        
        	        	
        	        	    
                                 
                                
    		String path = getServletContext().getRealPath("/WEB-INF/subReport/list_of_journals.jasper");
    		// String path = getServletContext().getRealPath("/WebContent/org/FAS/FAS1/Reports/JournalSystem/jasper/list_of_journals.jasper");
    		String ctxpath = path.substring(0, path.lastIndexOf("list_of_journals.jasper"));
                 Map parameters2 = new HashMap();
    					                    
                 parameters2.put("unitId",accountingunit);
                 parameters2.put("officeId", accountingoffice); 
                 parameters2.put("year1",yearfrom);
                 parameters2.put("month1", monthfrom);
                 parameters2.put("voucherNo", vou_no);
                 parameters2.put("type_year", type_year);
        	 parameters2.put("SUBREPORT_DIR", ctxpath);
                JasperPrint jasperPrint = JasperFillManager.fillReport(path, parameters2, con);   
        	            
             OutputStream outuputStream_1n = response.getOutputStream();
             JRExporter exporter = null;
             response.setContentType("application/pdf");
             response.setHeader("Content-Disposition","attachment; filename=\"list_of_journals.pdf\"");
             exporter = new JRPdfExporter();
             exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
             exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outuputStream_1n);
             exporter.exportReport();
             outuputStream_1n.close();
    					              
        	        	
        	        	 
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

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);   
		
	}

}
