package Servlets.FAS.FAS1.CivilBills.servlets;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Servlets.Security.classes.UserProfile;

/**
 * Servlet implementation class DrawingOfficerDetails
 */
public class PowerToWriteOff extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	PreparedStatement ps1 = null;
	ResultSet rs1 = null;
	PreparedStatement ps2 = null;
	ResultSet rs2 = null;
       
     public PowerToWriteOff() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	// TODO Auto-generated method stub
			PrintWriter out = response.getWriter();
	   HttpSession session=request.getSession(true);
	    String cmd=request.getParameter("command");
	    System.out.println("command"+cmd);
	    
            Calendar cal=Calendar.getInstance();
            int CByear=cal.get(Calendar.YEAR);
            int CBmonth=cal.get(Calendar.MONTH)+1;
  	     int autonumber=0;
  	     String FinYear=null;
       
         System.out.println("cash book year************"+CByear);
         System.out.println("cash book month************"+CBmonth);  
         
         int lastyear=0;
        // int month=2;
//        if(month<=9&&month>=1)
//         {
//         month=month+1;
         //}
//         if(yearValue<1900)
//         {
//      	   yearValue=yearValue+1900;
//         }
//         if(currentyear<1900)
//         {
//        	 currentyear=currentyear+1900;
//         }
//         
//         if(month<4)
//         {
//        	 lastyear=yearValue-1;
//      	 FinYear=lastyear+"-"+yearValue;
//         }
//         else
//         {
//        	 lastyear=yearValue+1;
//        	 FinYear=yearValue+"-"+lastyear;
//         }
	   
         String profile=(String)session.getAttribute("UserId");
			//int employeeid=profile.getEmployeeId();
			System.out.println("values..............................."+profile);
		
			long l = System.currentTimeMillis();
			Timestamp ts = new Timestamp(l);
			
			
        String xml="";
        try {
			ResourceBundle rsb = ResourceBundle
					.getBundle("Servlets.Security.servlets.Config");
			String ConnectionString = "";

			String strDriver = rsb.getString("Config.DATA_BASE_DRIVER");
			String strdsn = rsb.getString("Config.DSN");
			String strhostname = rsb.getString("Config.HOST_NAME");
			String strportno = rsb.getString("Config.PORT_NUMBER");
			String strsid = rsb.getString("Config.SID");
			String strdbusername = rsb.getString("Config.USER_NAME");
			String strdbpassword = rsb.getString("Config.PASSWORD");

			ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection

			Class.forName(strDriver.trim());
			con = DriverManager.getConnection(ConnectionString,
					strdbusername.trim(), strdbpassword.trim());
			
		} catch (Exception e) {
			System.out.println("Exception in openeing connection:" + e);
		}
		
		 if(cmd.equalsIgnoreCase("add"))
		{
			
			xml=xml+"<response><command>add</command>";
			String cmbLevel=request.getParameter("cmbLevel");
			String radio=request.getParameter("radio");
		
			String txtValueUpto=request.getParameter("txtValueUpto");
		
			String remark=request.getParameter("mtxtRemarks");
			FinYear=request.getParameter("cmbFinancialYear");
			
            try
            {
			
            
			
           
            ps=con.prepareStatement("insert into FAS_POWER_TO_OFF values(?,?,?,?,?,?,?,?,?,?)");
            ps.setString(1, FinYear);
            ps.setInt(2,CByear);
            ps.setInt(3,CBmonth);
            ps.setString(4,cmbLevel);
            ps.setString(5,radio);
            ps.setString(6,txtValueUpto);
            ps.setString(7,remark);
            ps.setString(8,"L");
            ps.setString(9, profile);
            ps.setTimestamp(10, ts);
           
            ps.executeUpdate();
            xml=xml+"<flag>success</flag>";
          
         
            
            }
            catch(Exception e)
            {
            	 xml=xml+"<flag>failure</flag>";
            System.out.println(e);
            }
			xml=xml+"</response>";
			System.out.println(xml);
            
		}
		else if(cmd.equalsIgnoreCase("update"))
		{
			xml=xml+"<response><command>update</command>";
			String cmbLevel=request.getParameter("cmbLevel");
			String radio=request.getParameter("radio");
		
			String txtValueUpto=request.getParameter("txtValueUpto");
			
			FinYear=request.getParameter("cmbFinancialYear");
			String remark=request.getParameter("mtxtRemarks");
            try{
            	//ps=con.prepareStatement("update FAS_POWER_TO_OFF set CASHBOOK_YEAR=?,CASHBOOK_MONTH=?,VALUE=?,REMARKS=?,STATUS=?,UPDATED_BY_USERID=?,UPDATED_DATE=? where FINANCIAL_YEAR='"+yearValue+"' and LEVEL='"+cmbLevel+"'and TYPE='"+radio+"'");
       
            	ps=con.prepareStatement("update FAS_POWER_TO_OFF set CASHBOOK_YEAR=?,CASHBOOK_MONTH=?,VALUE=?,REMARKS=?,STATUS=?,UPDATED_BY_USERID=?,UPDATED_DATE=? where FINANCIAL_YEAR='"+FinYear+"' and LEVEL1='"+cmbLevel+"'and TYPE='"+radio+"'");
            	ps.setInt(1, CByear);
            	ps.setInt(2,CBmonth );
            	ps.setString(3,txtValueUpto);
            	ps.setString(4,remark);
            	ps.setString(5,"L");
            	ps.setString(6, profile);
            	ps.setTimestamp(7, ts);
            	int count1=ps.executeUpdate();
            	if(count1>0){
            		xml=xml+"<flag>success</flag>";
            	}else{
            		xml=xml+"<flag>failure</flag>";
            	}
      
           
           
            
           }catch(Exception e){
            	 xml=xml+"<flag>failure</flag>";
            	 System.out.println(e);
            }
			xml=xml+"</response>";
			System.out.println(xml);
		}
		else if(cmd.equalsIgnoreCase("deleted"))
		{
			
			xml=xml+"<response><command>deleted</command>";
			String cmbLevel=request.getParameter("cmbLevel");
			String radio=request.getParameter("radio");
			FinYear=request.getParameter("cmbFinancialYear");
		    System.out.println("radio!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+radio);
		    System.out.println("radio!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+cmbLevel);
		    System.out.println("radio!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+FinYear);
            try
            {
            	  //ps=con.prepareStatement("delete from  FAS_POWER_TO_OFF  where FINANCIAL_YEAR='"+FinYear+"'and LEVEL1='"+cmbLevel+"'and TYPE='"+radio+"'");
            	  ps=con.prepareStatement("update FAS_POWER_TO_OFF set STATUS='C' where FINANCIAL_YEAR='"+FinYear+"' and LEVEL1='"+cmbLevel+"' and TYPE='"+radio+"'");
          
            int count=ps.executeUpdate();
            if(count>0){
            	   xml=xml+"<flag>success</flag>";
            }
            else
            {
            	 xml=xml+"<flag>failure</flag>";
            }
         
            
            }
            catch(Exception e)
            {
            	 xml=xml+"<flag>failure</flag>";
            System.out.println(e);
            }
			xml=xml+"</response>";
			System.out.println(xml);
			
			
			
			
			
			
			
		}
		else if(cmd.equalsIgnoreCase("gridvalue"))
		{
			
			xml=xml+"<response><command>gridvalue</command>";
			
			
			try
			{
				
				ps=con.prepareStatement("select * from  FAS_POWER_TO_OFF");
				rs=ps.executeQuery();
				 xml=xml+"<flag>success</flag>";
				while(rs.next())
				{
					xml=xml+"<level>"+rs.getString("LEVEL1")+"</level>";
					xml=xml+"<type>"+rs.getString("TYPE")+"</type>";
					xml=xml+"<value>"+rs.getString("VALUE")+"</value>";
					xml=xml+"<remarks>"+rs.getString("REMARKS")+"</remarks>";
					xml=xml+"<status>"+rs.getString("STATUS")+"</status>";
    				
				}
				
				
			}
			catch(Exception e)
			{
				 xml=xml+"<flag>failure</flag>";
				System.out.println(e);
			}
			xml=xml+"</response>";
			System.out.println(xml);
			
			
			
			
		}
		
		
		out.write(xml);
	}

}
