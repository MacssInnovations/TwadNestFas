package Servlets.FAS.FAS1.MonthEnd.servlets;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
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
 * Servlet implementation class BRS_Bank_Balance_UpdateList
 */
public class BRS_Bank_Balance_UpdateList extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private String CONTENT_TYPE = "text/xml; charset=windows-1252";
	 Connection connection = null; 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BRS_Bank_Balance_UpdateList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
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
	 if(cmd.equalsIgnoreCase("PDF")){
	


    try 
    {
        System.out.println("inside servlet>>>>>>>>>>>>>>>..");
        
        int cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
        int cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));
        int txtCB_Year=Integer.parseInt(request.getParameter("txtCB_Year"));
        int txtCB_Month=Integer.parseInt(request.getParameter("txtCB_Month"));
        System.out.println("txtCB_Month>>>>"+txtCB_Month);
    
    
        String displayingOrder=request.getParameter("displayingOrder"); 
        System.out.println("displayingOrder"+displayingOrder);
      
       
        String re_id=request.getParameter("txtRegionId"); System.out.println("re_id"+re_id);
        if(re_id=="")re_id="";System.out.println("re_id"+re_id);
        String Bank_id=request.getParameter("txtBankName");	System.out.println("Bank_id"+Bank_id);
        if(Bank_id=="")Bank_id="";System.out.println("Bank_id"+Bank_id);
     /*  int Region_id=Integer.parseInt(re_id);
       int txtBankName=Integer.parseInt(Bank_id);*/
      
    	  
        String monthInWords="";
        if(txtCB_Month==1)
            monthInWords="January";
        else if(txtCB_Month==2)
            monthInWords="February";
        else if(txtCB_Month==3)
            monthInWords="March";
        else if(txtCB_Month==4)
            monthInWords="April";
        else if(txtCB_Month==5)
            monthInWords="May";
        else if(txtCB_Month==6)
            monthInWords="June";
        else if(txtCB_Month==7)
            monthInWords="July";
        else if(txtCB_Month==8)
            monthInWords="August";
        else if(txtCB_Month==9)
            monthInWords="September";
        else if(txtCB_Month==10)
            monthInWords="October";
        else if(txtCB_Month==11)
            monthInWords="November";
        else if(txtCB_Month==12)
            monthInWords="December";
   String heading="BRS Status for Not Update Bank Balance Unit Wise upto the Month - "+monthInWords+" "+txtCB_Year;
   

	   reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/BRS/jaspers/brs_status_update.jasper")); 
 
  
          System.out.println("reportFile"+reportFile);   
        if (!reportFile.exists())
        throw new JRRuntimeException("File J not found. The report design must be compiled first.");
        
        JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
       System.out.println("jasperReport"+jasperReport);
       Map map=new HashMap();
      
        
      
        map.put("cashbookmonth",txtCB_Month);
        map.put("cashbookyear",txtCB_Year);  
        map.put("monthInWords",monthInWords);
        map.put("heading", heading);
       
     
      
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
    } 
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	doGet(request, response);
	}

}
