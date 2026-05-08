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
import javax.servlet.ServletRequest;
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
 * Servlet implementation class Fund_Transfer_Fund_Receipt_servlet
 */
public class Fund_Transfer_Fund_Receipt_servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	 Connection connection = null;
    
    public Fund_Transfer_Fund_Receipt_servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("dogetttttt");
		PrintWriter out = response.getWriter();
	      response.setHeader("cache-control","no-cache");
	      String CONTENT_TYPE = "text/xml; charset=windows-1252";
	      response.setContentType(CONTENT_TYPE);
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
	    String xml=null;
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
	   
	    try 
	    {
	    	 xml="<response>";
	        System.out.println("inside servlet>>>>>>>>>>>>>>>..");
	        String txtCB_Year[]=request.getParameter("txtCB_Year").split("-");
	       
	        System.out.println("txtCB_Month>>>>"+txtCB_Year[0]);
	        int prevYear=Integer.parseInt(txtCB_Year[0]);
	        int nextYear=Integer.parseInt(txtCB_Year[1]);
	       
	        String cmbAcc_UnitCode=request.getParameter("cmbAcc_UnitCode");
	        String cmbOffice_code=request.getParameter("cmbOffice_code");
	        
	       
	        int accountingunit=Integer.parseInt(cmbAcc_UnitCode);
	        int accountingoffice=Integer.parseInt(cmbOffice_code);
	        String fin=request.getParameter("txtCB_Year");
	        String txtParticular=request.getParameter("txtParticular");
	    int count=0;
           try{
        	   xml=xml+"<command>loadData</command>";
        	   PreparedStatement ps1=null,ps2=null;
	            ResultSet rs=null;
	            ps1=connection.prepareStatement("select PARTICULARS from FAS_FT_FR_VERIFY where ACCOUNTING_UNIT_ID="+accountingunit+" and FINANCIALYEAR='"+fin+"'");
	            rs=ps1.executeQuery();
	            while(rs.next())
	            {
	            	System.out.println("while::::::");
	            	count++;
	            	ps2=connection.prepareStatement("delete from FAS_FT_FR_VERIFY where ACCOUNTING_UNIT_ID="+accountingunit+" and FINANCIALYEAR='"+fin+"'");
	            	ps2.executeUpdate();
	            }
           }
           catch(Exception e)
           {
        	  System.out.println("excep in delete:"+e); 
           }
			
	        try {
	            PreparedStatement ps=null;
	            String UnitName="",OfficeName="";
	            ps=connection.prepareStatement("insert into FAS_FT_FR_VERIFY(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIALYEAR,CASHBOOK_YEAR,CASHBOOK_MONTH,PARTICULARS,UPDATED_BY_USERID,UPDATED_DATE) values (?,?,?,?,?,?,?,?)");
                ps.setInt(1,accountingunit);
                ps.setInt(2,accountingoffice);
                ps.setString(3,fin);
                ps.setInt(4,nextYear);//2011-2012->2012
                ps.setInt(5,3);
                ps.setString(6,txtParticular);
                ps.setString(7,update_user);
                ps.setTimestamp(8,ts);
                
               errcode=ps.executeUpdate();
               System.out.println("errcode:::"+errcode);
	          if(errcode>0)
	          {
	        	  xml+="<flag>success</flag>";
	          }
	          else
	          {
	        	  xml+="<flag>Failure</flag>";
	          }
	           
	            }
	            catch (SQLException e) {
	                System.out.println("SQL Exception -->"+e);
	                xml+="<flag>Failure</flag>";
	            } 
			
	    } 
	    catch (Exception ex) 
	    {
	    	 xml+="<flag>Failure</flag>";
	        System.out.println(ex);
	       
	    }
	    xml=xml+"</response>";
        System.out.println(xml);
        out.println(xml);
        out.close();
	}
	 
	

}
