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
public class Adj_Memo_MultipleReceipt_Edit extends HttpServlet {
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
	            
	            System.out.println("cashbookmonth "+cashbookmonth);
	            String dd=request.getParameter("txtCrea_date");
	            System.out.println("***********************************************"+dd);
	            String txtCrea_date[]=request.getParameter("txtCrea_date").split("/");
	            System.out.println(txtCrea_date);
	            int year=Integer.parseInt(txtCrea_date[2]);
	            System.out.println("_________________________________________________"+year);
	            int month=Integer.parseInt(txtCrea_date[1]);
	            System.out.println("-----------------------------------------------"+month);
	            
	            
	            
	            
	            
	            
	            
	          if(strCommand.equals("load_Voucher_No"))
	          {
	        	  
	        	  xml="<response><command>receiptNo1</command>"; 
	              try 
	                      {
	                              ps = con.prepareStatement("select VOUCHER_NO from FAS_CROSS_REFERENCE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and DOC_TYPE=? and AUTHORIZED_TO=?");
	                              ps.setInt(1, cmbAcc_UnitCode);
	                              ps.setInt(2, cmbOffice_code);
	                              ps.setInt(3, year);
	                              ps.setInt(4, month);
	                              ps.setString(5, "ADM");
	                              ps.setString(6, "M");
	                              result = ps.executeQuery();                                
	                              while(result.next()) 
	                              {
//	                            	  System.out.println(">>>>>>>>>>>>>>>>>"+result.getInt("VOUCHER_NO"));
//	                            	  ps=con.prepareStatement("select ADVICE_TYPE from fas_adjust_memo_mst where voucher_no='"+result.getInt("VOUCHER_NO")+"'");
//	                            	  result1=ps.executeQuery();
//	                            	  if(result1.next())
//	                            	  {
//	                            		  System.out.println("------------------------------------------>"+result1.getInt("ADVICE_TYPE"));
//	                            	  if(result1.getInt("ADVICE_TYPE")==2);
//	                            	  {
	                            		  System.out.println("--------------RK-----------");
	                            		  ps=con.prepareStatement("select VOUCHER_NO from FAS_ADJUST_MEMO_MST where VOUCHER_NO= '"+result.getInt("VOUCHER_NO")+"' and ADVICE_TYPE='1'");
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
	              xml="<response><command>load_Ref_No1</command>";
	              try 
	              {
	              	
	            	  cmbvocharNo=Integer.parseInt(request.getParameter("cmbvocharNo"));
	            	 System.out.println("txtReceipt_No"+cmbvocharNo);
	                 ps=con.prepareStatement("select * from FAS_ADJUST_MEMO_MST where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=?"); 
	                 ps.setInt(1, cmbAcc_UnitCode);
                     ps.setInt(2, cmbOffice_code);
                     ps.setInt(3, year);
                     ps.setInt(4, month);
	                 ps.setInt(5, cmbvocharNo);
	                 result=ps.executeQuery();
	                 System.out.println("result"+result);
	                 if(result.next()) 
	                 {
	                	
	                      xml=xml+"<accountunitid>"+result.getInt("FOR_ACCOUNTING_UNIT_ID")+"</accountunitid>";  
	                      
	                      ps=con.prepareStatement("select ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID='"+result.getInt("FOR_ACCOUNTING_UNIT_ID")+"'");
		                	 ResultSet rs1=ps.executeQuery();
		                	 if(rs1.next())
		                	 {
	                        xml=xml+"<officename>"+rs1.getString("ACCOUNTING_UNIT_NAME")+"</officename>"; 
		                	 }
	                      xml=xml+"<letterno>"+result.getInt("LETTER_NO")+"</letterno>";  
	                      String date1[]=result.getDate("LETTER_DATE").toString().split("-");
	                      String date2=date1[2]+"/"+date1[1]+"/"+date1[0];
	                      xml=xml+"<letterdate>"+date2+"</letterdate>";
	                      Double amt=result.getDouble("TOTAL_AMOUNT");
	 					  DecimalFormat formatter = new DecimalFormat("#0.00");
	 					  System.out.println(formatter.format(amt));
	                      xml=xml+"<totalAmt>"+formatter.format(amt)+"</totalAmt>";
	                      xml=xml+"<particulars>"+result.getString("PARTICULARS")+"</particulars>";
	                      
	                      xml=xml+"<authorityname>"+result.getString("AUTHORITY_NAME")+"</authorityname>";
	                      xml=xml+"<authorityaddress>"+result.getString("AUTHORITY_ADDRESS")+"</authorityaddress>";
	                      String date3[]=result.getDate("VOUCHER_DATE").toString().split("-");
	                      String date4=date3[2]+"/"+date3[1]+"/"+date3[0];
	                      xml=xml+"<vdate>"+date4+"</vdate>";
	                   
	                      
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
	                    //  xml=xml+"<office>"+result1.getInt("FOR_ACCOUNTING_UNIT_ID")+"</office>";
	                    
	                     
	                      
	                      xml=xml+"<remaks>"+result1.getString("REMARKS")+"</remaks>";
	                   
	                      xml=xml+"<letterno>"+result1.getInt("HO_REF_NO")+"</letterno>";
	                      
	                      String date1[]=result1.getDate("HO_REF_DATE").toString().split("-");
	                      String date2=date1[2]+"/"+date1[1]+"/"+date1[0];
	                      xml=xml+"<letterdate>"+date2+"</letterdate>";
	                      System.out.println();
	                      String date3[]=result1.getDate("RECEIPT_DATE").toString().split("-");
	                      String date4=date3[2]+"/"+date3[1]+"/"+date3[0];
                           xml=xml+"<year>"+date3[0]+"</year>";
	                      xml=xml+"<month>"+date3[1]+"</month>";
	                      xml=xml+"<receiptno>"+result1.getInt("RECEIPT_NO")+"</receiptno>";
	                      xml=xml+"<slno>"+result1.getInt("SL_NO")+"</slno>";
	                   
	                	 count1++;
	                	 
	                	 
	                	    System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++>"+count);
	   	                 System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++>"+count1);
	                	 
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
		            java.sql.Date d1=null,d2;
		            int txtAcc_HeadCode=0,cmbAcc_UnitCode=0,cmbOffice_code=0,txtCash_Month_hid=0,txtCash_year=0;
		            int office_id=0,count=0,cashbookyear=0,cashbookmonth=0,txtReceipt_No=0,ho_ref_no=0,errcode=0;
		            double txtTotalAmt=0;
		            ConvertDate cc=new ConvertDate();
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
		            
		            try{office_id=Integer.parseInt(request.getParameter("office_id"));}
		            catch(NumberFormatException e){System.out.println("exception"+e );}
		          	System.out.println("txtReceipt_No "+office_id); 
		            
		            
		            try{txtReceipt_No=Integer.parseInt(request.getParameter("txtReceipt_No"));}
		            catch(NumberFormatException e){System.out.println("exception"+e );}
		          	System.out.println("txtReceipt_No "+txtReceipt_No); 
		            
		          	try{ho_ref_no=Integer.parseInt(request.getParameter("letterNo"));}
		            catch(NumberFormatException e){System.out.println("exception"+e );}
		          	System.out.println("ho_ref_no "+ho_ref_no); 
		          	
		          	try{d1=cc.convert(request.getParameter("letterDate"));}
		            catch(NumberFormatException e){System.out.println("exception"+e );}
		            System.out.println("ho_ref_date "+ho_ref_date);
		          	
		            try{txtTotalAmt=Double.parseDouble(request.getParameter("txtTotalAmt"));}
		            catch(Exception e){System.out.println("exception"+e );}
		            System.out.println("txtAmount "+txtTotalAmt);
		            int memo_advice_No1=Integer.parseInt(request.getParameter("cmbvocharNo"));
		            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~        
	                        
		            try 
		            {   
		                     con.clearWarnings();
		                     con.setAutoCommit(false);
		                     ps=con.prepareStatement("update FAS_ADJUST_MEMO_MST set FOR_ACCOUNTING_UNIT_ID=?,VOUCHER_DATE=?,LETTER_NO=?,LETTER_DATE=?,AUTHORITY_NAME=?,AUTHORITY_ADDRESS=?,PARTICULARS=?,TOTAL_AMOUNT=?, UPDATED_BY_USER_ID=?,UPDATED_DATE=?,ADVICE_TYPE=?,MEMO_STATUS=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=?");
		                    
		                     ps.setInt(13,cmbAcc_UnitCode);
		                     ps.setInt(14,cmbOffice_code);
		                     ps.setInt(15,txtCash_year);
		                     ps.setInt(16,txtCash_Month_hid);
		                     ps.setInt(17,memo_advice_No1);
		                     ps.setDate(2,txtCrea_date);
		                     ps.setInt(1,office_id);
		                     ps.setInt(3,ho_ref_no);
		                     ps.setDate(4,d1);
		                     ps.setString(5,autName);
		                     ps.setString(7,particulars);		                     
		                     ps.setDouble(8,txtTotalAmt);
		                     ps.setString(9,update_user);
		                     ps.setTimestamp(10,ts);
		                     ps.setInt(11,1);
		                     ps.setString(6,autAddress);
		                   
		                     ps.setString(12, "L");
		                     System.out.println("last");
		                     errcode=ps.executeUpdate();
		                     System.out.println("errcode"+errcode);
		                     if(errcode==0)
		                     {         
			                       System.out.println("redirect");
			                       sendMessage(response,"The Adjustment Memo Creation Failed in master ","ok");		                       
		                     }
		                     else
		                     {  
		                    	 
			                      	                       
		                    	 System.out.println("inside 2 nd table");	                       
		                    	 String Grid_H_code[]=request.getParameterValues("H_code");
		                         String Grid_CR_DR_type[]=request.getParameterValues("CR_DR_type");
		                         String Grid_SL_type[]=request.getParameterValues("SL_type");
		                         String Grid_SL_code[]=request.getParameterValues("SL_code");
		                         String Grid_cbyear[]=request.getParameterValues("cashbkyear");
		                         String Grid_cbmonth[]=request.getParameterValues("cashbkmonth");
		                         String receiptNumber[]=request.getParameterValues("receiptNo");
		                         String ref_no[]=request.getParameterValues("ref_no");
		                         String ref_date[]=request.getParameterValues("ref_date");
		                         String Grid_sl_amt[]=request.getParameterValues("sl_amt");
		                         String Grid_particular[]=request.getParameterValues("sl_particular");
		                         
		                         System.out.println("after grid");
		                        
		                         String txtParticular="",letterdate="";
			                     Date sl_ref_date=null;
			                     int SL_NO=0,cmbSL_type=0,cmbSL_Code=0,ref_num=0,letter_code=0,office_Code=0;
			                     double txtsub_Amount=0;
			                     
			                     
		                         for(int k=0;k<Grid_H_code.length;k++) 
		                         {	  
		                        	 
		                        	 try
		                        	 {
		                        		 ps=con.prepareStatement("select SL_NO from FAS_ADJUST_MEMO_TRN where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=?");
		                        		 ps.setInt(1,cmbAcc_UnitCode);	
				                           System.out.println("cmbAcc_UnitCode:  "+cmbAcc_UnitCode);				                          
			                               
				                           ps.setInt(2,cmbOffice_code);	   
			                               System.out.println("cmbOffice_code:  "+cmbOffice_code);				                          
			                              
			                               ps.setInt(3,txtCash_year);
			  		                       System.out.println("cashbookyear::::"+txtCash_year);
			  		                       ps.setInt(4,txtCash_Month_hid);
			  		                       System.out.println("cashbookmonth::::"+txtCash_Month_hid);
			  		                    
			  		                       ps.setInt(5,memo_advice_No1);
			  		                       System.out.println("memo_advice_No::::"+memo_advice_No1);
		                        		  result1=ps.executeQuery();
		                        		  if(result1.next())
		                        		  {
		                        			  SL_NO=result1.getInt("SL_NO");
		                        			  System.out.println("SL_NO::::"+SL_NO);
		                        		 
		                        		  }}
		                        	 catch(Exception e)
		                        	 {
		                        		 System.out.println(e);
		                        	 }
		                        	 
		                        	 
		                        	 
		                        	
		                        	   cmbSL_type=0;cmbSL_Code=0;ref_num=0;
		                        	   int receiptNo=0;
		                        	   txtAcc_HeadCode=0;	
		                        	   txtsub_Amount=0;
		                        	   
		                        		java.sql.Date receipt_date,ho_date;
		                        	     
		                        	   String sql="update FAS_ADJUST_MEMO_TRN  set ACCOUNT_HEAD_CODE=? ,CR_DR_TYPE=?,SUB_LEDGER_TYPE_CODE=?,SUB_LEDGER_CODE=?,RECEIPT_NO=?,RECEIPT_DATE=?,HO_REF_NO=?,HO_REF_DATE=?,AMOUNT=?,REMARKS=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=? where ACCOUNTING_UNIT_ID=?and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=? and SL_NO=?";

	                         
				                       System.out.println("sql>>>"+sql);
		                        	   ps=con.prepareStatement(sql);
			                           try
			                           {
			                        	     
			                        	   ps.setInt(13,cmbAcc_UnitCode);	
				                           System.out.println("cmbAcc_UnitCode:  "+cmbAcc_UnitCode);				                          
			                               
				                           ps.setInt(14,cmbOffice_code);	   
			                               System.out.println("cmbOffice_code:  "+cmbOffice_code);				                          
			                               
			                               cashbookyear=Integer.parseInt(Grid_cbyear[k]);
			                               ps.setInt(15,txtCash_year);
			                               System.out.println("cashbookyear:  "+cashbookyear);	
			                               
			                               cashbookmonth=Integer.parseInt(Grid_cbmonth[k]);
			                               ps.setInt(16,txtCash_Month_hid);
			                               System.out.println("cashbookmonth:  "+cashbookmonth);
			                               
			                               ps.setInt(17,memo_advice_No1);	
			                               System.out.println("txtAdvice_No:  "+memo_advice_No1);		
			                               
			                               txtAcc_HeadCode=Integer.parseInt(Grid_H_code[k]);
			                               ps.setInt(1,txtAcc_HeadCode);
			                               System.out.println("txtAcc_HeadCode:  "+txtAcc_HeadCode);		
			                               
			                               System.out.println("Grid_CR_DR_type["+k+"]: "+Grid_CR_DR_type[k]);
			                               String rad_sub_CR_DR=Grid_CR_DR_type[k];	                              
			                               ps.setString(2,rad_sub_CR_DR);
			                               
			                               System.out.println("Grid_SL_type["+k+"]: "+Grid_SL_type[k]);	                               
			                               try{cmbSL_type=Integer.parseInt(Grid_SL_type[k]);}
			               	               catch(NumberFormatException e){System.out.println("exception"+e );}
			                               ps.setInt(3,cmbSL_type);	
			                               
			                               System.out.println("Grid_SL_code["+k+"]: "+Grid_SL_code[k]);	                               
			                               try{cmbSL_Code=Integer.parseInt(Grid_SL_code[k]);}
			               	               catch(NumberFormatException e){System.out.println("exception"+e );}
			               	               ps.setInt(4,cmbSL_Code);
			                               
			               	               
			               	               System.out.println("*********************************************************************************"+receiptNumber[k]);
			                               
			                               
			               	               receiptNo=Integer.parseInt(receiptNumber[k]);
			                               ps.setInt(5,receiptNo);
			                               System.out.println("receiptNo:  "+receiptNo);
			                               
			                               receipt_date=cc.convert(ref_date[k]);
			                               ps.setDate(6,receipt_date);	    
			                               
			                               
			               	               System.out.println("ref_no["+k+"]: "+ref_no[k]);	                               
			                               try{ref_num=Integer.parseInt(ref_no[k]);}
			               	               catch(NumberFormatException e){System.out.println("exception"+e );}
			               	               ps.setInt(7,ref_num);
			               	               
			               	               ho_date=cc.convert(ref_date[k]);
			                               ps.setDate(8,ho_date);	
			                              
			                               
			                               System.out.println("Grid_sl_amt["+k+"]: "+Grid_sl_amt[k]);	                               
			                               try{txtsub_Amount=Double.parseDouble(Grid_sl_amt[k]);}
			               	               catch(NumberFormatException e){System.out.println("exception"+e );}
			               	               ps.setDouble(9,txtsub_Amount);
			                                                          	                              
			                               txtParticular=Grid_particular[k];
			                               ps.setString(10,txtParticular);	    
			                               
			                               ps.setString(11,update_user);
			                               System.out.println("update_user : "+update_user);
			                               ps.setTimestamp(12,ts);
			                               System.out.println("ts : "+ts);
			                               ps.setInt(18,SL_NO);
			                             
			                               
				                               System.out.println("office_Code"+office_Code);
				                               int i=ps.executeUpdate(); 
				                               if(i>0)
				                            	   count++;
				                              
			                           }
			                           catch(Exception e)
			                           {
			                        	   e.getStackTrace();
			                        	   System.out.println("Err in value setting for insertion:::"+e.getMessage());
			                        	  // con.rollback(); 
			                           }
		                         }
		                         ps.close();
		                      
		                         if(count==Grid_H_code.length)
		                         {
			                         System.out.println("b4 commit");
			                         con.commit();
			                         sendMessage(response,"The Memo Advice Number '"+memo_advice_No1+"' has been Created Successfully ","ok");
		                         }
		                         else
		                         {
		                        	 System.out.println("b4 Rollback");
		                        	 con.rollback();
		                         }
		                         
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
