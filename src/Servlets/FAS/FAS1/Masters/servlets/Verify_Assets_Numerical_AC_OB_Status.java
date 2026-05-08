package Servlets.FAS.FAS1.Masters.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Verify_Assets_Numerical_AC_OB_Status
 */
public class Verify_Assets_Numerical_AC_OB_Status extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
    public Verify_Assets_Numerical_AC_OB_Status() {
        super(); 
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 Connection connection=null;
	        ResultSet result=null; 
	   	    PreparedStatement ps=null,ps2=null;
	      
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
	           connection=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
	           /*try
	           {
	        	   statement=connection.createStatement();
	               connection.clearWarnings();
	           }
	           catch(SQLException e)
	           {
	               System.out.println("Exception in creating statement:"+e);
	           }*/
	        }
	        catch(Exception e)
	        {
	           System.out.println("Exception in openeing connection:"+e);
	        }
	        response.setContentType(CONTENT_TYPE);
	        String strCommand = ""; 
	        String xml="";
	        int cmbAcc_UnitCode = 0;
	        int cmbOffice_code = 0;
	        String assetsnumstatus=null;
	        String cmbFinancialYear = null;

	        HttpSession session=request.getSession(false);
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
	        System.out.println("Session id is:"+userid);
	        response.setContentType("text/xml");
	        PrintWriter pw=response.getWriter();    
	        response.setHeader("Cache-Control","no-cache");
	        long l=System.currentTimeMillis();
	        Timestamp ts=new Timestamp(l);
	        try
	        {
	        	strCommand = request.getParameter("command");     
	        	System.out.println("strCommand : " + strCommand);
	        }
	        catch(Exception e)
	        {
	          e.printStackTrace();
	        }
	     
	        try
	        {

	        	cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode").trim());
	        }
	        catch(Exception e)
	        { 
	            System.out.println("IGNORABLE Exception getting cmbAcc_UnitCode from jsp " + e);
	        }try{
				cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code").trim());
			}
		    catch(Exception e)
		    { 
		        System.out.println("IGNORABLE Exception getting cmbOffice_code from jsp " + e);
		    }try{
				cmbFinancialYear = request.getParameter("cmbFinancialYear");
		    }
		    catch(Exception e)
		    { 
		        System.out.println("IGNORABLE Exception getting cmbFinancialYear from jsp " + e);
		    }try{
				assetsnumstatus=request.getParameter("verifyassetsnum");
	            
	        }
	        catch(Exception e)
	        { 
	            System.out.println("IGNORABLE Exception getting assetsnumstatus from jsp " + e);
	        }         
	        System.out.println("accounting_unit_id : " + cmbAcc_UnitCode);
        	System.out.println("accounting_unit_office_id : " + cmbOffice_code);
        	System.out.println("financial_year : " + cmbFinancialYear);
        	System.out.println("assetsnumstatus : " + assetsnumstatus);
        	
	      if(strCommand.equals("checkStatus"))
	        { 
	        	int count=0;
	        	System.out.println("\n*************\n checkStatus \n**************\n");
	            xml="<response><command>checkStatus</command>";
	            try 
	            {
	             
	            		ps = connection.prepareStatement("select accounting_unit_id,accounting_for_office_id,NUM_OB_STATUS from fas_assets_num_ob_verify where accounting_unit_id=? and accounting_for_office_id=? and FINANICAL_YEAR=?");
						ps.setInt(1, cmbAcc_UnitCode);
						ps.setInt(2, cmbOffice_code);
						ps.setString(3, cmbFinancialYear);				
						result = ps.executeQuery();

	           try
	             {
	            	 xml=xml+"<flag>success</flag>";
	            	 String valExists = "No";
	                while(result.next())
	                 { 
	                	 valExists = "Yes";
			             xml += "<accounting_unit_id>" + result.getInt("accounting_unit_id") + "</accounting_unit_id>"; 
			             xml += "<accounting_for_office_id>" + result.getInt("accounting_for_office_id") + "</accounting_for_office_id>"; 
	                     xml=xml+"<NUM_OB_STATUS>" + result.getString("NUM_OB_STATUS") + "</NUM_OB_STATUS>";  
	                	 count++;
	                 }
	                 xml =xml+ "<exists>"+valExists+"</exists>";
	                 xml =xml+ "<count>"+count+"</count>";
	             }
	           catch(Exception e)
	             {
	            	 System.out.println("Exception in getting values from DB - check status: " + e);
	             }

	            }
	            catch(Exception e1)
	            {
	            	System.out.println("Exception is in check "+e1);
	            	xml=xml+"<flag>failure</flag>";
	            }
	            xml=xml+"</response>";
	        } 
	   
	        else if(strCommand.equals("AddVerifyAssetsNumericalACOBStatus"))
	        { 
	        	System.out.println("\n*************\n AddVerifyAssetsNumericalACOBStatus \n**************\n");
	            xml="<response><command>AddVerifyAssetsNumericalACOBStatus</command>";
	            java.sql.Date AssetsNumDate=new java.sql.Date(ts.getTime());
	            int res=0;
	            try 
	            {
	            	int cc=0;
					ps2=connection.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID from fas_assets_num_ob_verify where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" AND ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" AND FINANICAL_YEAR='"+cmbFinancialYear+"' AND NUM_OB_STATUS='Y'");
					cc=ps2.executeUpdate();
					System.out.println("Already exists ");
					if(cc>0){
						System.out.println("Already exists");
						xml=xml+"<flag>AlreadyExist</flag>";									
					}else{
						
		            	String assetsnumstatus1="Y";
	            		ps = connection.prepareStatement("insert into fas_assets_num_ob_verify(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANICAL_YEAR,NUM_OB_STATUS,NUM_OB_DATE,updated_by_user_id,updated_date) values(?,?,?,?,?,?,?)");
						ps.setInt(1, cmbAcc_UnitCode);
						ps.setInt(2, cmbOffice_code);
						ps.setString(3, cmbFinancialYear);		
						ps.setString(4, assetsnumstatus1);
						ps.setDate(5, AssetsNumDate);
						ps.setString(6, userid);	
						ps.setTimestamp(7, ts);
						res = ps.executeUpdate();
						if(res>0){
							xml=xml+"<flag>success</flag>"; 
						}else{
							xml=xml+"<flag>NotInsert</flag>"; 
						}
					}
					
	            }
	            catch(Exception e1)
	            {
	            	System.out.println("Exception is in Get"+e1);
	            	xml=xml+"<flag>failure</flag>";
	            }
	            xml=xml+"</response>";
	        } 
	        
	        
	       System.out.println("xml is : " + xml);
	        pw.write(xml);
	        pw.flush();
	        pw.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
