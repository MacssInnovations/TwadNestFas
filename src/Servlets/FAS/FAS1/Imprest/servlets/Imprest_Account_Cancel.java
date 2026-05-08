package Servlets.FAS.FAS1.Imprest.servlets;

import Servlets.FAS.FAS1.CommonControls.servlets.Com_CashBook1;
import Servlets.FAS.FAS1.ReceiptSystem.servlets.SL_TYPE_CODE_NAME_DETAILS;
import Servlets.FAS.FAS1.ReceiptSystem.servlets.SL_TYPE_CODE_NAME_GENERAL;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

import java.sql.SQLException;

import java.sql.Statement;
import java.sql.Timestamp;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class Imprest_Account_Cancel extends HttpServlet
{
    private String CONTENT_TYPE = "text/xml; charset=windows-1252";
  
    public void init(ServletConfig config) throws ServletException 
    {
        super.init(config);
      
    }  
    public void doPost(HttpServletRequest request, 
                      HttpServletResponse response) throws ServletException, IOException 
    {
    
         
         String strCommand="";
         Connection con=null;        
         PreparedStatement ps=null;
         String txtMode_of_creat="";
         
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
                              ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection                              Class.forName(strDriver.trim());
                              con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
         }
         catch(Exception e)
         {
            System.out.println("Exception in opening connection :"+e);
            //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

         }
                 
 //-----------------------------------------------------------------------------------------------        
        
        try {
        
            strCommand=request.getParameter("Command");
            System.out.println("assign..here command..."+strCommand);
           
        }
        
        catch(Exception e) 
        {
            System.out.println("Exception in assigning..."+e);
        }
        
//-----------------------------------------------------------------------------------------------        
       
        if(strCommand.equalsIgnoreCase("Cancel")) 
        {
 	            String CONTENT_TYPE = "text/html; charset=windows-1252";
 	            response.setContentType(CONTENT_TYPE);
 	            Calendar c;
 	            int cmbAcc_UnitCode=0,cmbOffice_code=0,txtCash_Month_hid=0,txtCash_year=0;
 	            Date txtCrea_date=null;
 	            
 	      
 	                                    // changes here
 	            String update_user=(String)session.getAttribute("UserId");
 	            long l=System.currentTimeMillis();
 	            Timestamp ts=new Timestamp(l);	                
 	            int errcode=0;
 	            
 	            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 	                               
 	            try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
 	            catch(NumberFormatException e){System.out.println("exception"+e );}
 	            
 	            try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
 	            catch(NumberFormatException e){System.out.println("exception"+e );}
 	            
 	            String[] sd=request.getParameter("txtCrea_date").split("/");
 	            c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
 	            java.util.Date d=c.getTime();
 	            txtCrea_date=new Date(d.getTime());
 	            
 	            try{txtCash_year=Integer.parseInt(sd[2]);}
 	            catch(Exception e){System.out.println("exception"+e );}
 	            
 	            try{txtCash_Month_hid=Integer.parseInt(sd[1]);}
 	            catch(Exception e){System.out.println("exception"+e );}
 	            int txtVoucher_No=0;
 	            try
 	            {txtVoucher_No=Integer.parseInt(request.getParameter("txtVoucher_No"));}
 	            catch(Exception e){System.out.println("exception "+e );}
 	            
 	            try{txtMode_of_creat=request.getParameter("cmbPayment_type");}
	            catch(Exception e){	System.out.println("Exception in txtMode_of_creat ::: "+e.getMessage()); }
 	            
 	          
               
 	            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~        
                        
 	            try 
 	            {   
 	                   
 	                     ps=con.prepareStatement("update FAS_PAYMENT_MASTER set PAYMENT_STATUS=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and PAYMENT_DATE=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=? and MODE_OF_CREATION=?");
 	                    	                    
 	                    
 	                     ps.setString(1,"C");
 	                     ps.setString(2,update_user);
 	                     ps.setTimestamp(3,ts);
 	                     ps.setInt(4,cmbAcc_UnitCode);
 	                     ps.setInt(5,cmbOffice_code);
 	                     ps.setDate(6,txtCrea_date);
 	                     ps.setInt(7,txtCash_year);
 	                     ps.setInt(8,txtCash_Month_hid);
 	                     ps.setInt(9,txtVoucher_No);
 	                     ps.setString(10,txtMode_of_creat);
 	                     errcode=ps.executeUpdate();
 	                     if(errcode==0)
 	                     {         
 		                       System.out.println("redirect");
 		                       if(txtMode_of_creat.equals("I"))
 		                    	   sendMessage(response,"The Imprest Payment Cancellation Failed ","ok");
 		                       else
 		                    	   sendMessage(response,"The Temporary Advance Payment Cancellation Failed ","ok");
 	                     }
 	                     else
 	                     {
 	                    	   if(txtMode_of_creat.equals("I"))
 	                    		   sendMessage(response,"The Imprest Payment Voucher Number '"+txtVoucher_No+"' has been cancelled Successfully ","ok");
 	                    	   else
 	                    		   sendMessage(response,"The Temporary Advance Payment Voucher Number '"+txtVoucher_No+"' has been cancelled Successfully ","ok");
 	                     }
 	                    
                  }                 
                  catch(Exception e) 
                  {
                      try{con.rollback();}catch(SQLException sqle){System.out.println("exception in rollback "+sqle);}
                      e.getStackTrace();
                      if(txtMode_of_creat.equals("I"))
                             sendMessage(response,"The Imprest Payment Cancellation Failed ","ok");
                      else
                             sendMessage(response,"The Temporary Advance Payment Cancellation Failed ","ok");
                      System.out.println("Exception occur due to "+e);
                      
                  }
                  finally
                  {
                      System.out.println("done");
                      try{con.setAutoCommit(true);con.close();  }catch(SQLException sqle){}
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
