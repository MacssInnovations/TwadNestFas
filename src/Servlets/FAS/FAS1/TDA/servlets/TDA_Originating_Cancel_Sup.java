package Servlets.FAS.FAS1.TDA.servlets;

import Servlets.FAS.FAS1.CommonControls.servlets.Com_CashBook1;

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

public class TDA_Originating_Cancel_Sup extends HttpServlet
{
    private String CONTENT_TYPE = "text/html; charset=windows-1252";
  
    public void init(ServletConfig config) throws ServletException 
    {
        super.init(config);
      
    }
    public void doPost(HttpServletRequest request, 
                      HttpServletResponse response) throws ServletException, IOException 
    {
    
         
	         String strCommand="";
	         Connection con=null;        
	         PreparedStatement ps=null,ps1=null,ps2=null,ps5=null;
	         String xml="",tt1=null;
	         Statement st=null;
	         ResultSet rs=null,rs5=null;
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
	       
	        if(strCommand.equalsIgnoreCase("Cancel")) 
	        {
	             String CONTENT_TYPE = "text/html; charset=windows-1252";
	             response.setContentType(CONTENT_TYPE);
	            
	             //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	             Calendar c;
	             int cmbAcc_UnitCode=0,cmbOffice_code=0,txtCash_Month_hid=0,txtCash_year=0,originated_jvr_no=0,supNo=0;
	             Date txtCrea_date=null;
	             String Journal_type="",sql="",originated_dt="";
	             Boolean flag=true;
	                int test_count=0;                     // changes here
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
	             int Originated_SL_No=0;
	             try{Originated_SL_No=Integer.parseInt(request.getParameter("originated_slno"));}
	 	         catch(NumberFormatException e){System.out.println("exception"+e );}
	 	    
	             try{Journal_type=request.getParameter("Journal_type");}
	             catch(Exception e){System.out.println("Journal_type "+e );}
	             String TDA_TCA="";
	             if(Journal_type.equalsIgnoreCase("TCAO")){
	           		 TDA_TCA="TCACB','TCAO";
	                }else if(Journal_type.equalsIgnoreCase("TDAO")){
	           		 TDA_TCA="TDACB','TDAO";
	                }
	             
	             try{supNo=Integer.parseInt(request.getParameter("supNo"));}
	             catch(NumberFormatException e){System.out.println("exception"+e );}
	             
	             //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~        
	                 
	             try 
	             {   
	                      con.clearWarnings();
	                      con.setAutoCommit(false);
                              
                      ps=con.prepareStatement("select ORGINATING_JVR_NO,to_char(ORGINATING_JVR_DATE,'dd-mm-yyyy')as ORGINATING_JVR_DATE from FAS_TDA_TCA_RAISED_MST where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=? and SUPPLEMENT_NO=? ");
                      ps.setInt(1,cmbAcc_UnitCode);
                      ps.setInt(2,cmbOffice_code);
                      ps.setInt(3,txtCash_year);
                      ps.setInt(4,txtCash_Month_hid);
                      ps.setInt(5,Originated_SL_No);
                      ps.setInt(6,supNo);
                      rs=ps.executeQuery();
                      if(rs.next()) {
                          originated_jvr_no=rs.getInt("ORGINATING_JVR_NO");
                          originated_dt=rs.getString("ORGINATING_JVR_DATE");
                          test_count++;
                      }
                            if(test_count==0) {
                                System.out.println("no record for cancel");
                            }
                            else{
	                      ps=con.prepareStatement("update FAS_TDA_TCA_RAISED_MST set STATUS=?,ORGINATING_JVR_NO='0',ORGINATING_JVR_DATE=null,UPDATED_BY_USERID=?,UPDATED_DATE=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=? and TDA_OR_TCA in ('"+TDA_TCA+"') and SUPPLEMENT_NO=?");
	                                           	                     
	                      ps.setString(1,"C");
	                      ps.setString(2,update_user);
	                      ps.setTimestamp(3,ts);
	                      ps.setInt(4,cmbAcc_UnitCode);
	                      ps.setInt(5,cmbOffice_code);
	                      ps.setInt(6,txtCash_year);
	                      ps.setInt(7,txtCash_Month_hid);
	                      ps.setInt(8,Originated_SL_No);
	                      ps.setInt(9,supNo);
	                     // ps.setString(9,Journal_type);
	                      errcode=ps.executeUpdate();
	                      if(errcode==0)
	                      {         
	                    	  		flag=false;                                        
	                      }
	                      else
	                      {
                              System.out.println("comes 1::::");
			                    	
		                      		    System.out.println("comes 2::::");
		                      		  int upd=0;           
		                      				if(originated_jvr_no!=0)
		                      				{
		                                        flag=true;
                                                                int tt=originated_jvr_no;
                                                          System.out.println("org jvr no:::"+tt);
                                                                String sql11="select CB_REF_TYPE from fas_journal_master where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and CASHBOOK_YEAR="+txtCash_year+" and CASHBOOK_MONTH="+txtCash_Month_hid+" and VOUCHER_NO="+tt+" and SUPPLEMENT_NO="+supNo;
                                                                System.out.println("sql11 for :::::::"+sql11);
                                                                ps5=con.prepareStatement(sql11);
                                                                     rs5=ps5.executeQuery();
                                                                     if(rs5.next()){
                                                          System.out.println("comes in result set >>   "+rs5.getString("CB_REF_TYPE"));
		                                      if( rs5.getString("CB_REF_TYPE")!=null && rs5.getString("CB_REF_TYPE").equals("T")){
		                                            System.out.println("in type     tttttttttttttttttttttt");
		                                            //changed by BS on 09/06/2017 MODE_OF_CREATION='M' by MODE_OF_CREATION='A' for CB_REF_TYPE = 'T' //  
		                                                   ps=con.prepareStatement("update FAS_JOURNAL_MASTER set CB_REF_TYPE =null where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and VOUCHER_DATE=? and VOUCHER_NO=? and MODE_OF_CREATION='A' and CB_REF_TYPE='T' and SUPPLEMENT_NO="+supNo);
		                                                   ps.setInt(1,cmbAcc_UnitCode);
		                                        ps.setInt(2,cmbOffice_code);
		                                        ps.setString(3,originated_dt);
		                                        ps.setInt(4,originated_jvr_no);
		                                        }
		                                        else  {
		                                        System.out.println("in status ccccccccccc");
		                                            ps=con.prepareStatement("update FAS_JOURNAL_MASTER set Journal_status='C' where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and VOUCHER_DATE=? and VOUCHER_NO=? and MODE_OF_CREATION='A' and SUPPLEMENT_NO="+supNo);
		                                            ps.setInt(1,cmbAcc_UnitCode);
		                                            ps.setInt(2,cmbOffice_code);
		                                            ps.setString(3,originated_dt);
		                                            ps.setInt(4,originated_jvr_no);
		                                        }
                                                                     }
                                                                     
                                                         
                                                                     
                                                                     try{              
                    		                                      		 upd=ps.executeUpdate();
                                                                                         }catch (Exception e) {
                                                                                        	 System.out
                    																				.println("Exception in Null check ... ");
                                                                                         
                    																		e.printStackTrace();
                    																	}
		                                      		if(upd>0){
		                                      				System.out.println("journal_master flag updated successfully"); }
		                                      		else{
		                                      				flag=false;}
		                                    }
                                                                
		                      				else
		                      						flag=true;
		                      		//}                		
		                      		
	                      }
                              }
                            System.out.println("flag >>>> "+flag);
	                      if(flag)
                          {
                                
                                    con.commit();
                                    System.out.println("Journal_type >> "+Journal_type);
                                    if(Journal_type.equalsIgnoreCase("TDAO"))
                                      		sendMessage(response,"The TDA Originated Sl.No '"+Originated_SL_No+"' has been Cancelled Successfully ","ok");
                                    else
                                      		sendMessage(response,"The TCA Originated Sl.No '"+Originated_SL_No+"' has been Cancelled Successfully ","ok");
                          }
                          else
                          {
                                   
                                    con.rollback();
                                    if(Journal_type.equals("TDAO"))
          	                    	  		sendMessage(response,"The TDA Cancellation Failed ","ok");
          	                        else
          	                  	  			sendMessage(response,"The TCA Cancellation Failed ","ok");     
                          }
	                     
                  }
                  catch(Exception e) 
                  {
	                      try{con.rollback();}catch(SQLException sqle){System.out.println("exception in rollback "+sqle);}
	                      e.getStackTrace();
	                      if(Journal_type.equals("TDAO"))
	                    	  		sendMessage(response,"The TDA Cancellation Failed ","ok");
	                      else
	                  	  	  		sendMessage(response,"The TCA Cancellation Failed ","ok");                    	  	
	                      System.out.println("Exception occur due to "+e);
                      
                  }
                  finally
                  {
                     
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
