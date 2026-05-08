package Servlets.FAS.FAS1.AdjustmentMemoNew;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
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
import net.sf.jasperreports.engine.util.JRLoader;

import Servlets.FAS.FAS1.CommonClass.ConvertDate;



/**
 * Servlet implementation class Adj_Memo_SingleReceipt_Creation
 */
public class AdjustmentMemo_SingleReceiptMultipleUnit_Report1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = null;   
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			  
			  String CONTENT_TYPE = "text/xml";
			  response.setContentType(CONTENT_TYPE);
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
		             ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection		             Class.forName(strDriver.trim());
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
	                             
	                            		  System.out.println("--------------RK-----------");
	                            		  ps=con.prepareStatement("select VOUCHER_NO from FAS_ADJUST_MEMO_MST where ADVICE_TYPE='2'");
	                            		  result2=ps.executeQuery();
	                            		  
	                            		 while(result2.next())
	                            		  {
	                            			  
	                            			  xml=xml+"<receiptno>"+result2.getInt("VOUCHER_NO")+"</receiptno>";
	                            			    count++;
	                            		  }
	                            		  
	                            		  
	                            	  
	                            	
	                            	
	                            	  
	                            	
	                            	 
	                                 
	                              
	                              
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
	                 ps=con.prepareStatement("select * from FAS_ADJUST_MEMO_TRN where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=?"); 
	                 ps.setInt(1, cmbAcc_UnitCode);
                     ps.setInt(2, cmbOffice_code);
                     ps.setInt(3, year);
                     ps.setInt(4, month);
	                 ps.setInt(5, cmbvocharNo);
	                 result=ps.executeQuery();
	               //  System.out.println("result"+result);
	                 while(result.next()) 
	                 {
	                	System.out.println("Enter into While**************");
	                      xml=xml+"<accountunitid>"+result.getInt("FOR_ACCOUNTING_UNIT_ID")+"</accountunitid>";  
	                      
	                      ps=con.prepareStatement("select ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID='"+result.getInt("FOR_ACCOUNTING_UNIT_ID")+"'");
		                	 ResultSet rs1=ps.executeQuery();
		                	 if(rs1.next())
		                	 {
	                        xml=xml+"<officename>"+rs1.getString("ACCOUNTING_UNIT_NAME")+"</officename>"; 
		                	 }
	              
	                   
	                      
	                      count++;
	                 }
	                 
    
	                 
	             
	                 
	                 if(count>0) 
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
	          
	          else if(strCommand.equalsIgnoreCase("verify"))
	          {
	        	  
	        	  xml="<response><command>verify</command>";
	        	  
	        	  try
	        	  {
	        		  
	        		  cmbvocharNo=Integer.parseInt(request.getParameter("cmbvocharNo"));
		            	 System.out.println("txtReceipt_No"+cmbvocharNo);
		                 ps=con.prepareStatement("select OFFICE_ADDRESS from FAS_ADJUST_MEMO_TRN where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=?"); 
		                 ps.setInt(1, cmbAcc_UnitCode);
	                     ps.setInt(2, cmbOffice_code);
	                     ps.setInt(3, year);
	                     ps.setInt(4, month);
		                 ps.setInt(5, cmbvocharNo);
		                 result=ps.executeQuery();
		                 if(result.next())
		                 {
		                	 xml=xml+"<flag>success</flag>";
		                	 
		                 }
		                 else
		                 {
		                	 xml=xml+"<flag>failure</flag>";
		                 }
		                
	    
	        		  
		                 xml=xml+"</response>";
	        		  
	        		  
	        		  
	        		  
	        	  }
	        	  catch(Exception e)
	        	  {
	        		  System.out.println(e);
	        	  }
	        	  
	        	  
	        	  
	          }
	          
	          System.out.println("xml::::"+xml);
	          out.println(xml);
	          out.close();	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		 
		  
		  String strCommand="",strCommand1="",strCommand2="";
	         Connection con=null;        
	         PreparedStatement ps=null;
	         String xml="";
	         ResultSet result=null,result1=null,result2=null;
	         
	         
	         Calendar c;
	            Date txtCrea_date=null;
	            int txtAcc_HeadCode=0,cmbAcc_UnitCode=0,cmbOffice_code=0,txtCash_Month_hid=0,txtCash_year=0;
	            int office_id=0,count=0,cashbookyear=0,cashbookmonth=0,txtReceipt_No=0,ho_ref_no=0;
	            double txtTotalAmt=0;
	         //   Date txtCrea_date=null;
	            String particulars="",memodate="",authority="",ho_ref_date="";
	             String autName="",autAddress="",office_address="";
	             File reportFile=null;
	             int sup_no=0;
	                                    // changes here
	       
	             int memo_advice_No1=0;
	             
	         
	         
	         
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
	                              ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection	                              Class.forName(strDriver.trim());
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
	        if(strCommand.equalsIgnoreCase("REPORT"))
	        {
	        	System.out.println("enter into report servlet  function***************************");
	        	
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
		            memo_advice_No1=Integer.parseInt(request.getParameter("cmbvocharNo"));
		            
		            try{
		            	
		            	sup_no=Integer.parseInt(request.getParameter("office_id"));
		            	
		            	
		            }
		            catch(Exception e){ System.out.println (e);}
	        	System.out.println("enter into report function***************************");
	        	
                String monthInWords="";
                if(txtCash_Month_hid==1)
                    monthInWords="January";
                else if(txtCash_Month_hid==2)
                    monthInWords="February";
                else if(txtCash_Month_hid==3)
                    monthInWords="March";
                else if(txtCash_Month_hid==4)
                    monthInWords="April";
                else if(txtCash_Month_hid==5)
                    monthInWords="May";
                else if(txtCash_Month_hid==6)
                    monthInWords="June";
                else if(txtCash_Month_hid==7)
                    monthInWords="July";
                else if(txtCash_Month_hid==8)
                    monthInWords="August";
                else if(txtCash_Month_hid==9)
                    monthInWords="September";
                else if(txtCash_Month_hid==10)
                    monthInWords="October";
                else if(txtCash_Month_hid==11)
                    monthInWords="November";
                else if(txtCash_Month_hid==12)
                    monthInWords="December";

              
             
              
         try
         {   
        	 
          reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/AdjustmentMemoNew/Reports/singleUnit_MultipleReceiptReport1.jasper"));
          System.out.println("reportFile:"+reportFile);
          if (!reportFile.exists())
          throw new JRRuntimeException("File J not found. The report design must be compiled first.");
          JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
          Map map=new HashMap();
          System.out.println("txtCash_year"+txtCash_year);
          System.out.println("txtCash_Month_hid"+txtCash_Month_hid);
          System.out.println("monthInWords"+monthInWords);
          System.out.println("memo_advice_No1"+memo_advice_No1);
          System.out.println("sup_no"+sup_no);
          
          map.put("cashbookyear",txtCash_year);
          map.put("cashbookmonth",txtCash_Month_hid);
          map.put("monthinwords",monthInWords);	
          map.put("voucherNo",memo_advice_No1);	
          map.put("unitid", sup_no);
        
            
               JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, con);
               byte buf[] = 
               JasperExportManager.exportReportToPdf(jasperPrint);
              response.setContentType("application/pdf");
              response.setContentLength(buf.length);
                      System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%"+buf.length);
              response.setHeader ("Content-Disposition", "attachment;filename=\"SingleunitMultipleReceiptReport.pdf\"");
              OutputStream out1 = response.getOutputStream();
              out1.write(buf, 0, buf.length);
              out1.close();
            
            
            
            
         }
         catch(Exception e)
         {            String connectMsg = 
             "Could not create the report " + e.getMessage() + " " + 
             e.getLocalizedMessage();
         System.out.println(connectMsg);
         }        	
	        	     	
	        	
	        }
	        
	       
		
	
	///////////////////////////////////////////////////////////////////////////////
	        
	    
	        if(strCommand.equalsIgnoreCase("Add")) 
	        {
	        	
	        	String CONTENT_TYPE = "text/xml";
	  		  response.setContentType(CONTENT_TYPE);
	  		 
	  		  response.setHeader("Cache-Control", "no-cache");
	  		  PrintWriter out = response.getWriter();
	        	System.out.println("add fn starts");
//		            String CONTENT_TYPE = "text/html; charset=windows-1252";
//		            response.setContentType(CONTENT_TYPE);
		            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		            xml="<response><command>Add</command>";
		            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		         
		            String update_user=(String)session.getAttribute("UserId");
		            long l=System.currentTimeMillis();
		            Timestamp ts=new Timestamp(l);	                
		            int insmaster=0, offid=0;
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
		            
//		           String[] ath=request.getParameter("authority").split("-");
//		           autName=ath[0];
//		           autAddress=ath[1];
//		           
//		         System.out.println("autName"+autName);
//		         System.out.println("autAddress"+autAddress);
//		           
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
		            
		           
		          
		            		            
		            particulars=request.getParameter("particulars");
		            
		           /* try{txtReceipt_No=Integer.parseInt(request.getParameter("txtReceipt_No"));}
		            catch(NumberFormatException e){System.out.println("exception"+e );}
		          	System.out.println("txtReceipt_No "+txtReceipt_No); */
		            
		          	/*try{ho_ref_no=Integer.parseInt(request.getParameter("ho_ref_no"));}
		            catch(NumberFormatException e){System.out.println("exception"+e );}
		          	System.out.println("ho_ref_no "+ho_ref_no); 
		          	
		          	try{ho_ref_date=request.getParameter("ho_ref_date");}
		            catch(NumberFormatException e){System.out.println("exception"+e );}
		            System.out.println("ho_ref_date "+ho_ref_date);*/
		          	
		            try{txtTotalAmt=Double.parseDouble(request.getParameter("txtTotalAmt"));}
		            catch(Exception e){System.out.println("exception"+e );}
		            System.out.println("txtAmount "+txtTotalAmt);
		           memo_advice_No1=Integer.parseInt(request.getParameter("cmbvocharNo"));
		           office_address=request.getParameter("txtOfficeAddress");
		           
		           try{ offid=Integer.parseInt(request.getParameter("officeid"));}
		           catch(Exception e){System.out.println(e);}
		       	System.out.println("offid "+offid); 
		            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~        
	                        
		            try 
		            {   
		                     con.clearWarnings();
		                     con.setAutoCommit(false);
//		                     ps=con.prepareStatement("update FAS_ADJUST_MEMO_MST set VERIFIED_STATUS=?,OFFICE_ADDRESS=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=?");
//		                      ps.setString(1,"Y");
//		                      ps.setString(2,office_address);
//		                      ps.setInt(3,cmbAcc_UnitCode);
//		                      ps.setInt(4,cmbOffice_code);
//		                      ps.setInt(5,txtCash_year);
//		                      ps.setInt(6,txtCash_Month_hid);
//		                      ps.setInt(7,memo_advice_No1);
//		                    
//		                   
//		                     count=ps.executeUpdate();
		                     
		                     ps=con.prepareStatement("update FAS_ADJUST_MEMO_TRN set VERIFIED_STATUS=?,OFFICE_ADDRESS=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=? and FOR_ACCOUNTING_UNIT_ID=?");
		                      ps.setString(1,"Y");
		                      ps.setString(2,office_address);
		                      ps.setInt(3,cmbAcc_UnitCode);
		                      ps.setInt(4,cmbOffice_code);
		                      ps.setInt(5,txtCash_year);
		                      ps.setInt(6,txtCash_Month_hid);
		                      ps.setInt(7,memo_advice_No1);
		                      ps.setInt(8,offid);
		                    
		                      count=ps.executeUpdate();
		                      
		                         if(count>0)
		                         {
			                         System.out.println("b4 commit");
			                         con.commit();
			                         xml=xml+"<flag>success</flag>";
			                        //sendMessage(response,"The Memo Advice Number  has been ceacel  Successfully ","ok");
		                         }
		                         else
		                         {
		                        	 System.out.println("b4 Rollback");
		                        	 con.rollback();
		                        	 xml=xml+"<flag>failure</flag>";
		                         }
		                         
		                     //}
		                    
		                 } 
		            
		               catch(Exception e) 
		                 {
		                     try{con.rollback();}catch(SQLException sqle){System.out.println("exception in rollback "+sqle);}
		                     e.getStackTrace();
		                     xml=xml+"<flag>failure</flag>";
		                     //sendMessage(response,"The Adjustment Memo Creation Failed ","ok");
		                     System.out.println("Exception occur due to "+e);
		                     
		                     
		                 }
		                 finally
		                 {
		                	 xml=xml+"<flag>success</flag>";
		                     System.out.println("done");
		                     try{con.setAutoCommit(true);  }catch(SQLException sqle){}
		                 }  
		                 xml=xml+"</response>";
		                 
		                 
		                 System.out.println("xml::::"+xml);
		                 out.println(xml);
		                 out.close();	
	                 
	        }
	
	      
	}    
	        /////////////////////////////////////////////////////////////////////////////////////////For Report
	      
	        
	        
	       
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
