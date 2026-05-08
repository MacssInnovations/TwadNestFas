package Servlets.FAS.FAS1.AdjustmentMemoNew;

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

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Servlets.FAS.FAS1.CommonClass.ConvertDate;



/**
 * Servlet implementation class Adj_Memo_SingleReceipt_Creation
 */
public class Adj_Memo_SingleReceipt_Cancel extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";   
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			  response.setContentType(CONTENT_TYPE);
			  String CONTENT_TYPE = "text/xml";
			  response.setHeader("Cache-Control", "no-cache");
			  PrintWriter out = response.getWriter();
			  String strCommand="";
	          Connection con=null;        
	          PreparedStatement ps=null;
	          ResultSet result=null,result1=null,result2=null;
	          String xml="";
			  String sql="";
			  int count=0;
			  int count1=0;
			  int cashbookyear=0,cashbookmonth=0,cmbAcc_UnitCode=0,cmbOffice_code=0,cmbvocharNo=0;
	          
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
		             ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
		             Class.forName(strDriver.trim());
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
	          try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
	            catch(NumberFormatException e){System.out.println("exception"+e );}
	            System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);
	            
	            try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
	            catch(NumberFormatException e){System.out.println("exception"+e );}
	            System.out.println("cmbOffice_code "+cmbOffice_code);
	            
	            try{cashbookyear=Integer.parseInt(request.getParameter("txtCB_Year"));}
	            catch(NumberFormatException e){System.out.println("exception"+e );}
	            System.out.println("cashbookyear "+cashbookyear);
	            
	            try{cashbookmonth=Integer.parseInt(request.getParameter("txtCB_Month"));}
	            catch(NumberFormatException e){System.out.println("exception"+e );}
	            
	          if(strCommand.equals("load_Voucher_No"))
	          {
	        	  System.out.println("cashbookmonth "+cashbookmonth);
		            String date1=request.getParameter("txtCrea_date");
		            System.out.println("***********************************************"+date1);
		            String txtCrea_date[]=request.getParameter("txtCrea_date").split("/");
		            System.out.println(txtCrea_date);
		            int year=Integer.parseInt(txtCrea_date[2]);
		            System.out.println(year);
		            int month=Integer.parseInt(txtCrea_date[1]);
		            System.out.println(month);
	        	  xml="<response><command>receiptNo</command>"; 
	              try 
	                      {
	                              ps = con.prepareStatement("select VOUCHER_NO from FAS_CROSS_REFERENCE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and DOC_TYPE=? and AUTHORIZED_TO=?");
	                              ps.setInt(1, cmbAcc_UnitCode);
	                              ps.setInt(2, cmbOffice_code);
	                              ps.setInt(3, year);
	                              ps.setInt(4, month);
	                              ps.setString(5, "ADM");
	                              ps.setString(6, "C");
	                              result = ps.executeQuery();                                
	                              while(result.next()) 
	                              {
	                            	  System.out.println(">>>>>>>>>>>>>>>>>"+result.getInt("VOUCHER_NO"));
//	                            	  ps=con.prepareStatement("select ADVICE_TYPE from fas_adjust_memo_mst where voucher_no='"+result.getInt("VOUCHER_NO")+"'");
//	                            	  result1=ps.executeQuery();
//	                            	  if(result1.next())
//	                            	  {
//	                            		  System.out.println("------------------------------------------>"+result1.getInt("ADVICE_TYPE"));
//	                            	  if(result1.getInt("ADVICE_TYPE")==2);
//	                            	  {
	                            		  System.out.println("--------------RK-----------");
	                            		  ps=con.prepareStatement("select VOUCHER_NO from FAS_ADJUST_MEMO_MST where CASHBOOK_YEAR="+ year + "and CASHBOOK_MONTH=" +month + " and VOUCHER_NO= '"+result.getInt("VOUCHER_NO")+"' and ADVICE_TYPE='2' AND MEMO_STATUS!='C' ");
	                            		  result2=ps.executeQuery();
	                            		  
	                            		  if(result2.next())
	                            		  {
	                            			  
	                            			  xml=xml+"<receiptno>"+result2.getInt("VOUCHER_NO")+"</receiptno>";
	                            			  
	                            		  }
	                            		  
	                            		  
	                            	  }  
	                            	
	                            	
	                            	  
	                            	
	                            	 
	                                 
	                                  count++;
	                              
	                              if(count>0)
	                                  xml=xml+"<flag>success</flag>";
	                              else
	                                  xml=xml+"<flag>failure</flag>";
	                      }
	                catch(Exception e) 
	                      {
	                              System.out.println("Exception in receipt no ===> "+e);   
	                              xml=xml+"<flag>failure</flag>";  
	                      }
	                  xml=xml+"</response>";
	        	    }
	          else if(strCommand.equalsIgnoreCase("load_Ref_No")) 
	          {
	              xml="<response><command>load_Ref_No</command>";
	              try 
	              {
	              	
	            	  cmbvocharNo=Integer.parseInt(request.getParameter("cmbvocharNo"));
	            	 System.out.println("txtReceipt_No"+cmbvocharNo);
	                 ps=con.prepareStatement("select * from FAS_ADJUST_MEMO_MST where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=?"); 
	                 ps.setInt(1, cmbAcc_UnitCode);
                     ps.setInt(2, cmbOffice_code);
                     ps.setInt(3, cashbookyear);
                     ps.setInt(4, cashbookmonth);
	                 ps.setInt(5, cmbvocharNo);
	                 result=ps.executeQuery();
	                 System.out.println("result"+result);
	                 if(result.next()) 
	                 {
	                	
	                      xml=xml+"<HoNo>"+result.getInt("HO_REF_NO")+"</HoNo>";  
	                      
	                      String date1[]=result.getDate("HO_REF_DATE").toString().split("-");
	                      String date2=date1[2]+"/"+date1[1]+"/"+date1[0];
	                      xml=xml+"<HoDate>"+date2+"</HoDate>";
	                      Double amt=result.getDouble("TOTAL_AMOUNT");
	 					  DecimalFormat formatter = new DecimalFormat("#0.00");
	 					  System.out.println(formatter.format(amt));
	                      xml=xml+"<totalAmt>"+formatter.format(amt)+"</totalAmt>";
	                      xml=xml+"<particulars>"+result.getString("PARTICULARS")+"</particulars>";
	                      
	                      xml=xml+"<authorityname>"+result.getString("AUTHORITY_NAME")+"</authorityname>";
	                      xml=xml+"<authorityaddress>"+result.getString("AUTHORITY_ADDRESS")+"</authorityaddress>";
	                      String date3[]=result.getDate("RECEIPT_DATE").toString().split("-");
	                      String date4=date3[2]+"/"+date3[1]+"/"+date3[0];
	                      xml=xml+"<year>"+date3[0]+"</year>";
	                      xml=xml+"<month>"+date3[1]+"</month>";
	                      xml=xml+"<receiptno>"+result.getInt("RECEIPT_NO")+"</receiptno>";
	                      
	                   
	                      
	                      count++;
	                 }
	                 
	                 ps=con.prepareStatement("select * from FAS_ADJUST_MEMO_TRN where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=?");
	                 ps.setInt(1, cmbAcc_UnitCode);
                     ps.setInt(2, cmbOffice_code);
                     ps.setInt(3, cashbookyear);
                     ps.setInt(4, cashbookmonth);
	                 ps.setInt(5, cmbvocharNo);
	                 result1=ps.executeQuery();
	                 while(result1.next())
	                 {
	                	 
	                	 
	                	 xml=xml+"<accheadcode>"+result1.getInt("ACCOUNT_HEAD_CODE")+"</accheadcode>"; 
	                	 
	                	 
	                	 ps=con.prepareStatement("select ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_CODE='"+result1.getInt("ACCOUNT_HEAD_CODE")+"'");
	                	 ResultSet rs=ps.executeQuery();
	                	 if(rs.next())
	                	 {
	                		 xml=xml+"<accheaddes>"+rs.getString("ACCOUNT_HEAD_DESC")+"</accheaddes>"; 
	                	 }
	                	 
	                	 
	                	 
	                	 
	                      xml=xml+"<type>"+result1.getString("CR_DR_TYPE")+"</type>";
	                      Double amt=result1.getDouble("AMOUNT");
	 					  DecimalFormat formatter = new DecimalFormat("#0.00");
	 					  System.out.println(formatter.format(amt));
	                      xml=xml+"<amount>"+formatter.format(amt)+"</amount>";
	                      xml=xml+"<SUB_LEDGER_TYPE_CODE>"+result1.getInt("SUB_LEDGER_TYPE_CODE")+"</SUB_LEDGER_TYPE_CODE>";
	                     
	                      xml=xml+"<SUB_LEDGER_CODE>"+result1.getString("SUB_LEDGER_CODE")+"</SUB_LEDGER_CODE>";
	                      xml=xml+"<office>"+result1.getInt("FOR_ACCOUNTING_UNIT_ID")+"</office>";
	                     // xml=xml+"<officename>xxx</officename>";
	                      ps=con.prepareStatement("select ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID='"+result1.getInt("FOR_ACCOUNTING_UNIT_ID")+"'");
		                	 ResultSet rs1=ps.executeQuery();
		                	 if(rs1.next())
		                	 {
	                        xml=xml+"<officename>"+rs1.getString("ACCOUNTING_UNIT_NAME")+"</officename>"; 
		                	 }
	                      
	                      xml=xml+"<remaks>"+result1.getString("REMARKS")+"</remaks>";
	                   
	                      xml=xml+"<letterno>"+result1.getInt("LETTER_NO")+"</letterno>";
	                      
	                      String date1[]=result1.getDate("LETTER_DATE").toString().split("-");
	                      String date2=date1[2]+"/"+date1[1]+"/"+date1[0];
	                      xml=xml+"<letterdate>"+date2+"</letterdate>";
	                      xml=xml+"<slno>"+result1.getInt("SL_NO")+"</slno>";
	                   
	                	 count1++;
	                	 
	                	 
	                	 
	                	 
	                 }
	                 
	                 
	                 
	                 if(count>0&&count1>0) 
	                 xml=xml+"<flag>success</flag>";
	                 else
	                 xml=xml+"<flag>failure</flag>";
	              }
	              catch(Exception e) 
	              {
	                System.out.println("Exception in loading ref no:::"+e.getMessage());  
	                xml=xml+"<flag>failure</flag>";
	              }
	              xml=xml+"</response>";
	           }
	          
	          System.out.println("xml::::"+xml);
	          out.println(xml);
	          out.close();	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		  response.setContentType(CONTENT_TYPE);
		  String CONTENT_TYPE = "text/html";
		  response.setHeader("Cache-Control", "no-cache");
		  PrintWriter out = response.getWriter();
		  
		  String strCommand="";
	         Connection con=null;        
	         PreparedStatement ps=null;
	         String xml="";
	         ResultSet result=null,result1=null,result2=null;
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
	                              ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
	                              Class.forName(strDriver.trim());
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
	        if(strCommand.equalsIgnoreCase("Add")) 
	        {
	        	System.out.println("add fn starts");
//		            String CONTENT_TYPE = "text/html; charset=windows-1252";
//		            response.setContentType(CONTENT_TYPE);
		            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		            xml="<response><command>Add</command>";
		            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		            Calendar c;
		            Date txtCrea_date=null;
		            int txtAcc_HeadCode=0,cmbAcc_UnitCode=0,cmbOffice_code=0,txtCash_Month_hid=0,txtCash_year=0;
		            int office_id=0,count=0,cashbookyear=0,cashbookmonth=0,txtReceipt_No=0,ho_ref_no=0,memo_advice_No1=0;
		            double txtTotalAmt=0;
		         //   Date txtCrea_date=null;
		            String particulars="",memodate="",authority="",ho_ref_date="";
		             String autName="",autAddress="";
		                                    // changes here
		            String update_user=(String)session.getAttribute("UserId");
		            long l=System.currentTimeMillis();
		            Timestamp ts=new Timestamp(l);	                
		            int insmaster=0;
		            
		            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		                               
		            try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
		            catch(NumberFormatException e){System.out.println("exception"+e );}
		            System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);
		            
		            try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
		            catch(NumberFormatException e){System.out.println("exception"+e );}
		            System.out.println("cmbOffice_code "+cmbOffice_code);
		            
		            String[] sd=request.getParameter("txtCrea_date").split("/");
		            c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
		            java.util.Date d=c.getTime();
		            txtCrea_date=new Date(d.getTime());
		            System.out.println("txtCrea_date "+txtCrea_date);
		            
		            System.out.println("b4 getting month and year");
		            try{txtCash_year=Integer.parseInt(sd[2]);}
		            catch(Exception e){System.out.println("exception"+e );}
		            System.out.println("txtCash_year "+txtCash_year);
		            
		            try{txtCash_Month_hid=Integer.parseInt(sd[1]);}
		            catch(Exception e){System.out.println("exception"+e );}
		            System.out.println("txtCash_Month_hid "+txtCash_Month_hid);
		            
		            try{memodate=request.getParameter("txtCrea_date");}
		            catch(NumberFormatException e){System.out.println("exception"+e );}
		            System.out.println("memodate "+memodate);
		            
		            autName=request.getParameter("authority");
			           autAddress=request.getParameter("authorityaddress");
			           
			         System.out.println("autName"+autName);
			         System.out.println("autAddress"+autAddress);
		           
		            /*
		              try{authority=request.getParameter("authority");}
		            catch(NumberFormatException e){System.out.println("exception"+e );}
		            System.out.println("authority "+authority);
		             
		              String[] sd=request.getParameter("agreedate1").split("/");
                System.out.println("b4 getting month and year");
                try{cashbookyear=Integer.parseInt(sd[2]);}
                catch(Exception e){System.out.println("exception"+e );}
                System.out.println("cashbookyear "+cashbookyear);
		             */
		            
		            int memo_advice_No=0;
		          
		            		            
		            particulars=request.getParameter("particulars");
		            
		            try{txtReceipt_No=Integer.parseInt(request.getParameter("txtReceipt_No"));}
		            catch(NumberFormatException e){System.out.println("exception"+e );}
		          	System.out.println("txtReceipt_No "+txtReceipt_No); 
		            
		          	try{ho_ref_no=Integer.parseInt(request.getParameter("ho_ref_no"));}
		            catch(NumberFormatException e){System.out.println("exception"+e );}
		          	System.out.println("ho_ref_no "+ho_ref_no); 
		          	
		          	try{ho_ref_date=request.getParameter("ho_ref_date");}
		            catch(NumberFormatException e){System.out.println("exception"+e );}
		            System.out.println("ho_ref_date "+ho_ref_date);
		          	
		            try{txtTotalAmt=Double.parseDouble(request.getParameter("txtTotalAmt"));}
		            catch(Exception e){System.out.println("exception"+e );}
		            System.out.println("txtAmount "+txtTotalAmt);
		            memo_advice_No1=Integer.parseInt(request.getParameter("cmbvocharNo"));
		            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~        
	                        
		            try 
		            {   
		                     con.clearWarnings();
		                     con.setAutoCommit(false);
		                     ps=con.prepareStatement("update FAS_ADJUST_MEMO_MST set MEMO_STATUS=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=?");
		                      ps.setString(1,"C");
		                      ps.setInt(2,cmbAcc_UnitCode);
		                      ps.setInt(3,cmbOffice_code);
		                     
		                     ps.setInt(4,txtCash_year);
		                     ps.setInt(5,txtCash_Month_hid);
		                     
		                    
		                     ps.setInt(6,memo_advice_No1);
		                
		                   
		                     count=ps.executeUpdate();
		                     
	                         if(count>0)
	                         {
		                         System.out.println("b4 commit");
		                         con.commit();
		                         sendMessage(response,"The Memo Advice Number  has been cancel  Successfully ","ok");
	                         }
	                         else
	                         {
	                        	 System.out.println("b4 Rollback");
	                        	 con.rollback();
	                         }
		                  
		                    
		                    
		                 } 
		            
		               catch(Exception e) 
		                 {
		                     try{con.rollback();}catch(SQLException sqle){System.out.println("exception in rollback "+sqle);}
		                     e.getStackTrace();
		                     sendMessage(response,"The Adjustment Memo Creation Failed ","ok");
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
