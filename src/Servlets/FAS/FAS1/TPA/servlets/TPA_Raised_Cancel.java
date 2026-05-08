package Servlets.FAS.FAS1.TPA.servlets;

import Servlets.FAS.FAS1.CommonControls2.servlets.GL_SL_Checking_for_TPA;
import Servlets.FAS.FAS1.ReceiptSystem.servlets.SL_TYPE_CODE_NAME_GENERAL;

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
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import java.sql.CallableStatement;

import javax.servlet.*;
import javax.servlet.http.*;

public class TPA_Raised_Cancel extends HttpServlet
{
    private String CONTENT_TYPE = "text/html; charset=windows-1252";
  
    public void init(ServletConfig config) throws ServletException 
    {
        super.init(config);
      
    }
    public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException
    {
    	  	 String strCommand="";
	         Connection con=null;        
	         PreparedStatement ps=null,ps1=null,ps2=null;
	         String xml="";
	         Statement st=null;
	         ResultSet rs=null;
	 //-----------------------------------------------------------------------------------------------        
	 
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
	        
	//-----------------------------------------------------------------------------------------------        
	        
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
	              ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection	              Class.forName(strDriver.trim());
	              con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
	              st=con.createStatement();
	         }
	         catch(Exception e)
	         {
	              System.out.println("Exception in opening connection :"+e);
	              //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");
	
	         }
	                 
	 //-----------------------------------------------------------------------------------------------        
	        
	        try
	        {        
	              strCommand=request.getParameter("Command");
	              System.out.println("assign..here command..."+strCommand);
	           
	        }
	        
	        catch(Exception e) 
	        {
	             System.out.println("Exception in assigning..."+e);
	        }
	        
	//-----------------------------------------------------------------------------------------------        
	        System.out.println("jjj"+strCommand);
	        if(strCommand.equalsIgnoreCase("Cancel")) 
	        {
	        	System.out.print("i am Here");
	        	
	             String CONTENT_TYPE = "text/html; charset=windows-1252";
	             response.setContentType(CONTENT_TYPE);
	            
	             //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	             Calendar c;
	             int cmbAcc_UnitCode=0,cmbOffice_code=0,txtCash_Month_hid=0,txtCash_year=0,originated_jvr_no=0;
	             Date txtCrea_date=null;
	             String Journal_type="",sql="",originated_dt="";
	             Boolean flag=true;
	                                     // changes here
	             String update_user=(String)session.getAttribute("UserId");
	             long l=System.currentTimeMillis();
	             Timestamp ts=new Timestamp(l);                      
	             int errcode=0;
	             
	             //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	                                
	             try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
	             catch(NumberFormatException e){System.out.println("exception"+e );}
	             System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);
	             
	             try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
	             catch(NumberFormatException e){System.out.println("exception"+e );}
	             System.out.println("cmbOffice_code "+cmbOffice_code);
	             
	             String[] sd=request.getParameter("Voucher_Date").split("/");
	             c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
	             java.util.Date d=c.getTime();
	             txtCrea_date=new Date(d.getTime());
	     
	             try{txtCash_year=Integer.parseInt(sd[2]);}
	             catch(Exception e){System.out.println("exception"+e );}
	             System.out.println("txtCash_year "+txtCash_year);
	             
	             try{txtCash_Month_hid=Integer.parseInt(sd[1]);}
	             catch(Exception e){System.out.println("exception"+e );}
	             System.out.println("txtCash_Month_hid "+txtCash_Month_hid);
	             int Originated_SL_No=0;
	             try{Originated_SL_No=Integer.parseInt(request.getParameter("adviceNumber"));}
	 	         catch(NumberFormatException e){System.out.println("exception"+e );}
	 	         System.out.println("Originated_SL_No "+Originated_SL_No);
	           
	             try{Journal_type=request.getParameter("Org_CR_DR");
	             if(Journal_type.equalsIgnoreCase("CR"))
	             {
	            	 Journal_type="TPAOC";
	             }else{
	            	 Journal_type="TPAOD"; 
	             }
	             }
	             catch(Exception e){System.out.println("Journal_type "+e );}
	             System.out.println("Journal_type "+Journal_type);
	           
	             
	             
	             //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~        
	                 
	             try 
	             {   
	                      con.clearWarnings();
	                      con.setAutoCommit(false);
	                      ps=con.prepareStatement("update FAS_TPA_MASTER set STATUS=?,UPDATED_BY_USERID=?,UPDATED_DATE=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=? and TPA_TYPE=?");
	                                           	                     
	                      ps.setString(1,"C");
	                      ps.setString(2,update_user);
	                      ps.setTimestamp(3,ts);
	                      ps.setInt(4,cmbAcc_UnitCode);
	                      ps.setInt(5,cmbOffice_code);
	                      ps.setInt(6,txtCash_year);
	                      ps.setInt(7,txtCash_Month_hid);
	                      ps.setInt(8,Originated_SL_No);
	                      ps.setString(9,Journal_type);
	                      errcode=ps.executeUpdate();
	                      if(errcode==0)
	                      {         
	                    	  		flag=false;                                        
	                      }
	                      else
	                      {
			                    	ps=con.prepareStatement("select ORGIN_VOUCHER_NO,to_char(ORGIN_VOUCHER_DATE,'dd-mm-yyyy')as ORGIN_VOUCHER_DATE from FAS_TPA_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=? ");
		                      		ps.setInt(1,cmbAcc_UnitCode);
		                      		ps.setInt(2,cmbOffice_code);
		                      		ps.setInt(3,txtCash_year);
		                      		ps.setInt(4,txtCash_Month_hid);
		                      		ps.setInt(5,Originated_SL_No);
		                      		rs=ps.executeQuery();
		                      		if(rs.next())
		                      		{
		                      				System.out.println("ORGIN_VOUCHER_NO :: "+rs.getInt("ORGIN_VOUCHER_NO"));
		                      				originated_jvr_no=rs.getInt("ORGIN_VOUCHER_NO");
		                      				originated_dt=rs.getString("ORGIN_VOUCHER_DATE");
		                      				if(rs.getInt("ORGIN_VOUCHER_NO")!=0)
		                      				{
		                      						ps.close();
		                      						ps=con.prepareStatement("update FAS_JOURNAL_MASTER set JOURNAL_STATUS='C' where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and VOUCHER_DATE=to_date(?,'dd-mm-yy') and VOUCHER_NO=?");
		                      						System.out.println(cmbAcc_UnitCode);                						
		                      						ps.setInt(1,cmbAcc_UnitCode);
		                      						System.out.println(cmbOffice_code);
		                                      		ps.setInt(2,cmbOffice_code);
		                                      		System.out.println(originated_dt);
		                                      		ps.setString(3,originated_dt);
		                                      		System.out.println(originated_jvr_no);
		                                      		ps.setInt(4,originated_jvr_no);
		                                      		int upd=ps.executeUpdate();
		                                      		if(upd>0)
		                                      				System.out.println("journal_master flag updated successfully"); 
		                                      		else
		                                      				flag=false;
		                                    }
		                      				else
		                      						flag=true;
		                      		}                		
		                      		else
		                      		{
		                      				System.out.println("Record not available");	
		                      				flag=true;
		                      		}
	                      }
	                      if(flag)
                       {
                                 System.out.println("b4 commit");
                                 con.commit();
                                 if(Journal_type.equals("TDAO"))
                                   		sendMessage(response,"The TPA Originated Sl.No '"+Originated_SL_No+"' has been Cancelled Successfully ","ok");
                                 else
                                   		sendMessage(response,"The TPA Originated Sl.No '"+Originated_SL_No+"' has been Cancelled Successfully ","ok");
                       }
                       else
                       {
                                 System.out.println("b4 Rollback");
                                 con.rollback();
                                 if(Journal_type.equals("TDAO"))
       	                    	  		sendMessage(response,"The TPA Cancellation Failed ","ok");
       	                        else
       	                  	  			sendMessage(response,"The TPA Cancellation Failed ","ok");     
                       }
	                     
               }
               catch(Exception e) 
               {
	                      try{con.rollback();}catch(SQLException sqle){System.out.println("exception in rollback "+sqle);}
	                      e.getStackTrace();
	                      if(Journal_type.equals("TDAO"))
	                    	  		sendMessage(response,"The TPA Cancellation Failed ","ok");
	                      else
	                  	  	  		sendMessage(response,"The TPA Cancellation Failed ","ok");                    	  	
	                      System.out.println("Exception occur due to "+e);
                   
               }
               finally
               {
                   System.out.println("done");
                   try{con.setAutoCommit(true);  }catch(SQLException sqle){}
               }
		             
		                  
		                
	        }
	    }
	   
	    private void sendMessage(HttpServletResponse response,String msg,String bType)
	    {
	        try
	        {
	                  String url="org/Library/jsps/MessengerOkBack.jsp?message=" + msg + "&button=" + bType;
	                  response.sendRedirect(url);
	        }
	        catch(IOException e)
	        {
	        }
	    }
}
