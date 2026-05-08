package Servlets.FAS.FAS1.MonthEnd.servlets;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
 * Servlet implementation class BRS_status_report
 */
public class BRS_status_report extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private String CONTENT_TYPE = "text/xml; charset=windows-1252";
//	 private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	 Connection connection = null;
       
    
    public BRS_status_report() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  System.out.println("BRS_status_report::::");
		  doGet(request, response);
	}
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
    String xml="";
    int count=0;
String cmd=request.getParameter("command");
System.out.println("test"+cmd);
    if(cmd.equalsIgnoreCase("load_bank")){
    	System.out.println("load bank test");
    	xml="<response>";
    	 PrintWriter out=response.getWriter();
       xml=xml+"<command>loadBank</command>";

      String qry="select BANK_ID,BANK_NAME from FAS_BANK_LIST ";
      System.out.println(qry);
      PreparedStatement ps2=null;
      ResultSet rs2=null;
        try
        {
                 ps2=connection.prepareStatement(qry);
                   
                 rs2=ps2.executeQuery();                                 
                 while(rs2.next()) 
                 {
              	   xml+="<id>"+rs2.getInt("BANK_ID")+"</id>";
	          		 xml+="<name>"+rs2.getString("BANK_NAME")+"</name>";                                                                                
                         count++;
                 }                                                
                 if(count==0)
                         xml+="<flag>NoData</flag>";                                                   
                 else               
                         xml+="<flag>success</flag>";
                             
        }
        catch(Exception e) 
        {
                 System.out.println("Exception in loadBankWise..."+e);
                 xml+="<flag>"+e.getMessage()+"</flag>";
        }  
        xml=xml+"<count>"+count+"</count></response>";
        System.out.println(xml);
        out.println(xml);
        out.close();
  }

	
    
    
    if(cmd.equalsIgnoreCase("PDF")){
    try 
    {
        System.out.println("inside servlet>>>>>>>>>>>>>>>..");
        String txtCB_Year=request.getParameter("BRStxtCB_Year");
        String txtCB_Month=request.getParameter("BRStxtCB_Month");
        System.out.println("txtCB_Month>>>>"+txtCB_Month);
    
        int yearfrom=Integer.parseInt(txtCB_Year);
        int monthfrom=Integer.parseInt(txtCB_Month);
        String displayingOrder=request.getParameter("displayingOrder"); 
        System.out.println("displayingOrder"+displayingOrder);
      
       
        String re_id=request.getParameter("txtRegionId"); System.out.println("re_id"+re_id);
        if(re_id=="")re_id="";System.out.println("re_id"+re_id);
        String Bank_id=request.getParameter("txtBankName");	System.out.println("Bank_id"+Bank_id);
        if(Bank_id=="")Bank_id="";System.out.println("Bank_id"+Bank_id);
     /*  int Region_id=Integer.parseInt(re_id);
       int txtBankName=Integer.parseInt(Bank_id);*/
      
    	  
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
   String heading="BRS Status Update Unit Wise For the Month - "+monthInWords+" "+yearfrom;
   

   if(displayingOrder.equalsIgnoreCase("RW")){
   

        reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/BRS/jaspers/brs_status_update_RE.jasper")); 
   }
   else if(displayingOrder.equalsIgnoreCase("BW"))
   {
	   reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/BRS/jaspers/brs_status_update_BankWise.jasper"));   
   }
   else if(displayingOrder.equalsIgnoreCase("ALL"))
   {
	   reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/BRS/jaspers/brs_status_update.jasper")); 
 
   }
          System.out.println("reportFile"+reportFile);   
        if (!reportFile.exists())
        throw new JRRuntimeException("File J not found. The report design must be compiled first.");
        
        JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
       System.out.println("jasperReport"+jasperReport);
       Map map=new HashMap();
       if(displayingOrder.equalsIgnoreCase("RW")){
        
      
        map.put("cashbookmonth",monthfrom);
        map.put("cashbookyear",yearfrom);  
        map.put("monthInWords",monthInWords);
        map.put("heading", heading);
        map.put("Region_id", re_id);
     }
       else if(displayingOrder.equalsIgnoreCase("BW")){
          
         
           map.put("cashbookmonth",monthfrom);
           map.put("cashbookyear",yearfrom);  
           map.put("monthInWords",monthInWords);
           map.put("heading", heading);
           map.put("txtBankName", Bank_id);}
       else if(displayingOrder.equalsIgnoreCase("ALL"))
       {
    	   map.put("cashbookmonth",monthfrom);
           map.put("cashbookyear",yearfrom);  
           map.put("monthInWords",monthInWords);
           map.put("heading", heading);	 
       }
       
        System.out.println("************* "+re_id+" ***************** "+Bank_id); 
      
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, connection);            
       
       
        	System.out.println("PDF:::::::::::");
                    byte buf[]=JasperExportManager.exportReportToPdf(jasperPrint);
                    response.setContentType("application/pdf");
                    response.setContentLength(buf.length);
                    response.setHeader ("Content-Disposition", "attachment;filename=\"BRS_Status_Update_Report.pdf\"");
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
    }  }
}
	

