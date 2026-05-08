package Servlets.FAS.FAS1.AdjustmentMemoNew;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Servlet implementation class Adjust_Memo_Reject_List
 */
public class Adjust_Memo_Reject_List extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";   
    
 	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		  response.setContentType(CONTENT_TYPE);
		  response.setHeader("Cache-Control", "no-cache");
		  PrintWriter out = response.getWriter();
		  String strType = "";
	      String xml="<response>";
	      System.out.println("tis is servlet");
		    
	    	 try
	         {
	             HttpSession session=request.getSession(false);
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
	         
	         /*---------------------------------------------------------------------------*/
	         /*------------------------- Variables Declaration--------------------------- */
	         
	         Connection con=null;
	         ResultSet rs=null;
	       //  Statement stmt=null;
	         PreparedStatement ps=null;
	 	         
	         /*----------------------------------------------------------------------------*/
	        /*--------------------------------- Database Connection------------------------*/
	       
	         try {
	                          ResourceBundle rs1=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
	                          String ConnectionString="";

	                          String strDriver=rs1.getString("Config.DATA_BASE_DRIVER");
	                          String strdsn=rs1.getString("Config.DSN");
	                          String strhostname=rs1.getString("Config.HOST_NAME");
	                          String strportno=rs1.getString("Config.PORT_NUMBER");
	                          String strsid=rs1.getString("Config.SID");
	                          String strdbusername=rs1.getString("Config.USER_NAME");
	                          String strdbpassword=rs1.getString("Config.PASSWORD");
	                          ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection	                          Class.forName(strDriver.trim());
	                          con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
	         }
	         catch(Exception e)
	         {
	             System.out.println("Exception in opening connection :"+e);
	         }
	         
	        /*-----------------------------------------------------------------------------*/
	         
	         /* Get Command Parameter */     
	         try
	         {
	         	strType = request.getParameter("Command");
	         }
	         catch(Exception e)
	         {
	         	e.printStackTrace();
	         }
	         /* Variables Declaration */
	         int txtCB_Year=0;
	         int txtCB_Month=0;
	         int cmbAcc_UnitCode=0;
	         int cmbOffice_code=0;
	         Date txtFrom_date=null;
	         Date txtTo_date=null;
	         Calendar c;
	         String sql="";
	    
	         String cmbStatus="";
	         
	         /* Accounting Unit ID */
	         try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
	         catch(NumberFormatException e){System.out.println("exception"+e );}
	         System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);
	         
	         /* Accounting For Office ID */
	         try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
	         catch(NumberFormatException e){System.out.println("exception"+e );}
	         System.out.println("cmbOffice_code "+cmbOffice_code);
	         
	         
	         /* Get Cashbook Month and Year */
	         txtCB_Year=Integer.parseInt(request.getParameter("txtCB_Year"));
	         txtCB_Month=Integer.parseInt(request.getParameter("txtCB_Month"));
	         
	         System.out.println("year..."+txtCB_Year);
	         System.out.println("Month..."+txtCB_Month);
	         
	         /* Get Voucher Status */
	         cmbStatus=request.getParameter("cmbStatus");
	         System.out.println("cmbStatus.."+cmbStatus);
	         
	         if(strType.equalsIgnoreCase("searchByMonth"))  
	         {
 	             xml="<resp><command>searchByMonth</command>";
 	             
 	           
 	             
 	             
 	      	     sql="SELECT VOUCHER_NO FROM FAS_ADJUST_MEMO_MST WHERE ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID=? AND CASHBOOK_YEAR=? AND CASHBOOK_MONTH=? AND ACCEPTANCE_STATUS='N'  UNION SELECT VOUCHER_NO FROM FAS_ADJUST_MEMO_TRN WHERE ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID=? AND CASHBOOK_YEAR=? AND CASHBOOK_MONTH=?";
 	             System.out.println("SQL:::"+sql); 	
 	             
 	            try
 	            {
 			            int count=0;
 			            ps=con.prepareStatement(sql);
 			            ps.setInt(1,cmbAcc_UnitCode);
 			            ps.setInt(2,cmbOffice_code);
 			            ps.setInt(3,txtCB_Year);
 			            ps.setInt(4,txtCB_Month);
 			            ps.setInt(5,cmbAcc_UnitCode);
 			            ps.setInt(6,cmbOffice_code);
 			            ps.setInt(7,txtCB_Year);
 			            ps.setInt(8,txtCB_Month);
 			          
 			            
 			            xml=xml+"<flag>success</flag>" ;
 			            xml=xml+"<Ucode>"+cmbAcc_UnitCode+"</Ucode>" ;
 			            xml=xml+"<Offid>"+cmbOffice_code+"</Offid>";
 			            xml=xml+"<txtCB_Year>"+txtCB_Year+"</txtCB_Year>";
 			            xml=xml+"<txtCB_Month>"+txtCB_Month+"</txtCB_Month>";
 			            
 			            rs=ps.executeQuery();
 			            
 			            ResultSet rs2=null;
 			            while(rs.next())
 			            {
 			            	System.out.println("enter while");
 			            	ps=con.prepareStatement("select * from FAS_ADJUST_MEMO_MST where CASHBOOK_YEAR='"+txtCB_Year+"' and VOUCHER_NO='"+rs.getInt("VOUCHER_NO")+"'  and CASHBOOK_MONTH='"+txtCB_Month+"'");
 			            	ResultSet rs1=ps.executeQuery();
 			            	
 			            	if(rs1.next())
 			            	{
 			            		System.out.println("enter if");
 			            		xml=xml+"<lengcheck>";
 			            		xml=xml+"<VOUCHER_NO>"+rs1.getInt("VOUCHER_NO")+"</VOUCHER_NO>";
 			            		
 			            		if(rs1.getDate("VOUCHER_DATE")!=null)
 								{
 									String date1[]=rs1.getDate("VOUCHER_DATE").toString().split("-");
 									String date2=date1[2]+"/"+date1[1]+"/"+date1[0];
 									xml=xml+"<VOUCHER_DATE>"+date2+"</VOUCHER_DATE>";
 								}
 								else
 								{
 										xml=xml+"<VOUCHER_DATE>--</VOUCHER_DATE>";
 														
 								}   
 			            		
 			            		if(rs1.getString("ACCOUNTING_UNIT_ID")!=null)
  	 			        	    {
 			            			System.out.println("Enter iffffffffff");
  	 			        	        ps=con.prepareStatement("select * from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID='"+rs1.getInt("FOR_ACCOUNTING_UNIT_ID")+"'");
  	 			        	        rs2=ps.executeQuery();
  	 			        	        if(rs2.next())
  	 			        	        {
  	 			        	           xml=xml+"<OFFICE_NAME>"+rs2.getString("ACCOUNTING_UNIT_NAME")+"</OFFICE_NAME>";
  	 			        	           System.out.println("OFFICE_NAME.........."+rs2.getString("ACCOUNTING_UNIT_NAME"));
  	 			        	        }
  	 			        	   
  	 			        	   else
  	 			        	   {
  	 			        		  xml=xml+"<OFFICE_NAME>---</OFFICE_NAME>";
  	 			        	   }
  			            		
  			            	  }
 			            	
 			            	 if(rs1.getString("PARTICULARS")==null)
 								{
 									
 									ps=con.prepareStatement("select * from FAS_ADJUST_MEMO_TRN where CASHBOOK_YEAR='"+txtCB_Year+"' and VOUCHER_NO='"+rs.getInt("VOUCHER_NO")+"'  and CASHBOOK_MONTH='"+txtCB_Month+"' and  ACCEPTANCE_STATUS='N'");
 									rs2=ps.executeQuery();
 									if(rs2.next())
 									{
 										xml=xml+"<PARTICULARS>"+rs2.getString("REMARKS")+"</PARTICULARS>";
 									}				
 									
 								}
 								else
 								{
 								xml=xml+"<PARTICULARS>"+rs1.getString("PARTICULARS")+"</PARTICULARS>";
 								}
 			            	try
								{
									ps=con.prepareStatement("select * from FAS_ADJUST_MEMO_MST where CASHBOOK_YEAR='"+txtCB_Year+"' and VOUCHER_NO='"+rs.getInt("VOUCHER_NO")+"' and CASHBOOK_MONTH='"+txtCB_Month+"' and  ACCEPTANCE_STATUS='N'");
									rs2=ps.executeQuery();
								
									if(rs2.next())
									{ 
										System.out.println("enter if");
										Double amt=rs2.getDouble("TOTAL_AMOUNT");
										 NumberFormat formatter = new DecimalFormat("#0.00");
										 System.out.println(formatter.format(amt));
										 xml=xml+"<TOTAL_AMOUNT>"+formatter.format(amt)+"</TOTAL_AMOUNT>";
									}
									else
									{
										ps=con.prepareStatement("select * from FAS_ADJUST_MEMO_TRN where CASHBOOK_YEAR='"+txtCB_Year+"' and VOUCHER_NO='"+rs.getInt("VOUCHER_NO")+"'  and CASHBOOK_MONTH='"+txtCB_Month+"' and  ACCEPTANCE_STATUS='N'");
										rs2=ps.executeQuery();
										if(rs2.next())
										{
											System.out.println("enter else");
											Double amt=rs2.getDouble("AMOUNT");
											 NumberFormat formatter = new DecimalFormat("#0.00");
											 System.out.println(formatter.format(amt));
											 xml=xml+"<TOTAL_AMOUNT>"+formatter.format(amt)+"</TOTAL_AMOUNT>";
										}
									}
								}
								catch(Exception e)
								{
									
								}
								if(rs1.getInt("LETTER_NO")==0)
								{
									ps=con.prepareStatement("select * from FAS_ADJUST_MEMO_TRN where CASHBOOK_YEAR='"+txtCB_Year+"' and VOUCHER_NO='"+rs.getInt("VOUCHER_NO")+"'  and CASHBOOK_MONTH='"+txtCB_Month+"' and  ACCEPTANCE_STATUS='N'");
									rs2=ps.executeQuery();
									if(rs2.next())
									{
										xml=xml+"<LETTER_NO>"+rs2.getInt("LETTER_NO")+"</LETTER_NO>";
									}																
									
								}
								else
								{
									xml=xml+"<LETTER_NO>"+rs1.getInt("LETTER_NO")+"</LETTER_NO>";
								}
								if(rs1.getDate("LETTER_DATE")!=null)
								{
									String date1[]=rs1.getDate("LETTER_DATE").toString().split("-");
									String date2=date1[2]+"/"+date1[1]+"/"+date1[0];
									xml=xml+"<LETTER_DATE>"+date2+"</LETTER_DATE>";
								}
								else
								{
									ps=con.prepareStatement("select * from FAS_ADJUST_MEMO_TRN where CASHBOOK_YEAR='"+txtCB_Year+"' and VOUCHER_NO='"+rs.getInt("VOUCHER_NO")+"'  and CASHBOOK_MONTH='"+txtCB_Month+"' and  ACCEPTANCE_STATUS='N'");
									rs2=ps.executeQuery();
									if(rs2.next())
									{
										String date1[]=rs2.getDate("LETTER_DATE").toString().split("-");
										String date2=date1[2]+"/"+date1[1]+"/"+date1[0];
										xml=xml+"<LETTER_DATE>"+date2+"</LETTER_DATE>";
									}
									
								}  
								if(rs1.getString("REASON_FOR_REJECT")!=null)
								{
								
								xml=xml+"<ACCEPT_DATE>"+rs1.getString("REASON_FOR_REJECT")+"</ACCEPT_DATE>";
								}
								else
								{
									ps=con.prepareStatement("select * from FAS_ADJUST_MEMO_TRN where CASHBOOK_YEAR='"+txtCB_Year+"' and VOUCHER_NO='"+rs.getInt("VOUCHER_NO")+"'  and CASHBOOK_MONTH='"+txtCB_Month+"' and  ACCEPTANCE_STATUS='N'");
									rs2=ps.executeQuery();
									if(rs2.next())
									{
										xml=xml+"<ACCEPT_DATE>"+rs2.getString("REASON_FOR_REJECT")+"</ACCEPT_DATE>";
									}
								}
			    	}
 			                xml=xml+"</lengcheck>";
 			                count++;
 		             }
 		               if(count==0) 
 		               {
 			                    System.out.println("inside count==0");
 			                    xml="<resp><command>searchByMonth</command><flag>failure</flag>";
 		               }
 	           }
 	           catch(SQLException sqle)
 	           {
 	                System.out.println("error while fetching data " + sqle);
 	                xml="<resp><command>searchByMonth</command><flag>failure</flag>";
 	           }
             
         }
	         /* Search By Given Date */
	         
	         else if(strType.equalsIgnoreCase("searchByDate"))
	         {
	             
		         	xml="<resp><command>searchByDate</command>";
		      	    
		             System.out.println("here "+strType.equalsIgnoreCase("searchByDate"));
		            
		             /* Get From Date */
		             String[] sd=request.getParameter("txtFrom_date").split("/");
		             c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
		             java.util.Date d=c.getTime();
		             txtFrom_date=new Date(d.getTime());
		             System.out.println("from_date "+txtFrom_date);
		             
		             /* Get To Date */
		             sd=request.getParameter("txtTo_date").split("/");
		             c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
		             d=c.getTime();
		             txtTo_date=new Date(d.getTime());            
		             System.out.println("txtTo_date "+txtTo_date);
		             
		             /* Get Cashbook Month and Year */
			         txtCB_Year=Integer.parseInt(request.getParameter("txtCB_Year"));
			         txtCB_Month=Integer.parseInt(request.getParameter("txtCB_Month"));
			         
			         System.out.println("year..."+txtCB_Year);
			         System.out.println("Month..."+txtCB_Month);       

		        	 
		        	 xml="<resp><command>searchByDate</command>";
		 	          
		        	 sql="SELECT VOUCHER_NO FROM FAS_ADJUST_MEMO_MST WHERE ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID=? AND VOUCHER_DATE>=? AND VOUCHER_DATE<=? AND ACCEPTANCE_STATUS='N' AND ACCEPT_VOUCHER_NO  is not null AND ACCEPT_VOUCHER_DATE is not null ";
	 	             System.out.println("SQL:::"+sql); 	
	 	             
	 	            try
	 	            {
	 			            int count=0;
	 			            ps=con.prepareStatement(sql);
	 			            ps.setInt(1,cmbAcc_UnitCode);
	 			            ps.setInt(2,cmbOffice_code);
	 			            ps.setDate(3,txtFrom_date);
	 			            ps.setDate(4,txtTo_date);
	 			             			            
	 			            xml=xml+"<flag>success</flag>" ;
	 			            xml=xml+"<Ucode>"+cmbAcc_UnitCode+"</Ucode>" ;
	 			            xml=xml+"<Offid>"+cmbOffice_code+"</Offid>";
	 			            xml=xml+"<txtCB_Year>"+txtCB_Year+"</txtCB_Year>";
	 			            xml=xml+"<txtCB_Month>"+txtCB_Month+"</txtCB_Month>";
	 			            
	 			            rs=ps.executeQuery();
	 			            
	 			            ResultSet rs2=null;
	 			            while(rs.next())
	 			            {
	 			            	System.out.println("enter while");
	 			            	ps=con.prepareStatement("select * from FAS_ADJUST_MEMO_MST where CASHBOOK_YEAR='"+txtCB_Year+"' and VOUCHER_NO='"+rs.getInt("VOUCHER_NO")+"'  and CASHBOOK_MONTH='"+txtCB_Month+"' and  ACCEPTANCE_STATUS='N'");
	 			            	ResultSet rs1=ps.executeQuery();
	 			            	
	 			            	if(rs1.next())
	 			            	{
	 			            		System.out.println("enter if");
	 			            		xml=xml+"<lengcheck>";
	 			            		xml=xml+"<VOUCHER_NO>"+rs1.getInt("VOUCHER_NO")+"</VOUCHER_NO>";
	 			            		
	 			            		if(rs1.getDate("VOUCHER_DATE")!=null)
	 								{
	 									String date1[]=rs1.getDate("VOUCHER_DATE").toString().split("-");
	 									String date2=date1[2]+"/"+date1[1]+"/"+date1[0];
	 									xml=xml+"<VOUCHER_DATE>"+date2+"</VOUCHER_DATE>";
	 								}
	 								else
	 								{
	 										xml=xml+"<VOUCHER_DATE>--</VOUCHER_DATE>";
	 														
	 								}   
	 			            		
	 			            		if(rs1.getString("ACCOUNTING_UNIT_ID")!=null)
	  	 			        	    {
	 			            			System.out.println("Enter iffffffffff");
	  	 			        	        ps=con.prepareStatement("select * from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID='"+rs1.getInt("FOR_ACCOUNTING_UNIT_ID")+"'");
	  	 			        	        rs2=ps.executeQuery();
	  	 			        	        if(rs2.next())
	  	 			        	        {
	  	 			        	           xml=xml+"<OFFICE_NAME>"+rs2.getString("ACCOUNTING_UNIT_NAME")+"</OFFICE_NAME>";
	  	 			        	           System.out.println("OFFICE_NAME.........."+rs2.getString("ACCOUNTING_UNIT_NAME"));
	  	 			        	        }
	  	 			        	   
	  	 			        	   else
	  	 			        	   {
	  	 			        		  xml=xml+"<OFFICE_NAME>---</OFFICE_NAME>";
	  	 			        	   }
	  			            		
	  			            	  }
	 			            	
	 			            	 if(rs1.getString("PARTICULARS")==null)
	 								{
	 									
	 									ps=con.prepareStatement("select * from FAS_ADJUST_MEMO_TRN where CASHBOOK_YEAR='"+txtCB_Year+"' and VOUCHER_NO='"+rs.getInt("VOUCHER_NO")+"'  and CASHBOOK_MONTH='"+txtCB_Month+"' and  ACCEPTANCE_STATUS='N'");
	 									rs2=ps.executeQuery();
	 									if(rs2.next())
	 									{
	 										xml=xml+"<PARTICULARS>"+rs2.getString("REMARKS")+"</PARTICULARS>";
	 									}				
	 									
	 								}
	 								else
	 								{
	 								xml=xml+"<PARTICULARS>"+rs1.getString("PARTICULARS")+"</PARTICULARS>";
	 								}
	 			            	try
								{
									ps=con.prepareStatement("select * from FAS_ADJUST_MEMO_MST where CASHBOOK_YEAR='"+txtCB_Year+"' and VOUCHER_NO='"+rs.getInt("VOUCHER_NO")+"' and CASHBOOK_MONTH='"+txtCB_Month+"' and  ACCEPTANCE_STATUS='N'");
									rs2=ps.executeQuery();
								
									if(rs2.next())
									{ 
										System.out.println("enter if");
										Double amt=rs2.getDouble("TOTAL_AMOUNT");
										 NumberFormat formatter = new DecimalFormat("#0.00");
										 System.out.println(formatter.format(amt));
										 xml=xml+"<TOTAL_AMOUNT>"+formatter.format(amt)+"</TOTAL_AMOUNT>";
									}
									else
									{
										ps=con.prepareStatement("select * from FAS_ADJUST_MEMO_TRN where CASHBOOK_YEAR='"+txtCB_Year+"' and VOUCHER_NO='"+rs.getInt("VOUCHER_NO")+"'  and CASHBOOK_MONTH='"+txtCB_Month+"' and  ACCEPTANCE_STATUS='N'");
										rs2=ps.executeQuery();
										if(rs2.next())
										{
											System.out.println("enter else");
											Double amt=rs2.getDouble("AMOUNT");
											 NumberFormat formatter = new DecimalFormat("#0.00");
											 System.out.println(formatter.format(amt));
											 xml=xml+"<TOTAL_AMOUNT>"+formatter.format(amt)+"</TOTAL_AMOUNT>";
										}
									}
								}
								catch(Exception e)
								{
									
								}
								if(rs1.getInt("LETTER_NO")==0)
								{
									ps=con.prepareStatement("select * from FAS_ADJUST_MEMO_TRN where CASHBOOK_YEAR='"+txtCB_Year+"' and VOUCHER_NO='"+rs.getInt("VOUCHER_NO")+"'  and CASHBOOK_MONTH='"+txtCB_Month+"' and  ACCEPTANCE_STATUS='N'");
									rs2=ps.executeQuery();
									if(rs2.next())
									{
										xml=xml+"<LETTER_NO>"+rs2.getInt("LETTER_NO")+"</LETTER_NO>";
									}																
									
								}
								else
								{
									xml=xml+"<LETTER_NO>"+rs1.getInt("LETTER_NO")+"</LETTER_NO>";
								}
								if(rs1.getDate("LETTER_DATE")!=null)
								{
									String date1[]=rs1.getDate("LETTER_DATE").toString().split("-");
									String date2=date1[2]+"/"+date1[1]+"/"+date1[0];
									xml=xml+"<LETTER_DATE>"+date2+"</LETTER_DATE>";
								}
								else
								{
									ps=con.prepareStatement("select * from FAS_ADJUST_MEMO_TRN where CASHBOOK_YEAR='"+txtCB_Year+"' and VOUCHER_NO='"+rs.getInt("VOUCHER_NO")+"'  and CASHBOOK_MONTH='"+txtCB_Month+"' and  ACCEPTANCE_STATUS='N'");
									rs2=ps.executeQuery();
									if(rs2.next())
									{
										String date1[]=rs2.getDate("LETTER_DATE").toString().split("-");
										String date2=date1[2]+"/"+date1[1]+"/"+date1[0];
										xml=xml+"<LETTER_DATE>"+date2+"</LETTER_DATE>";
									}
									
								}  
								
								xml=xml+"<ACCEPT_DATE>"+rs1.getString("REASON_FOR_REJECT")+"</ACCEPT_DATE>";
								if(rs1.getDate("ACCEPT_VOUCHER_DATE")!=null)
 								{
									String date1[]=rs1.getDate("ACCEPT_VOUCHER_DATE").toString().split("-");
									String date2=date1[2]+"/"+date1[1]+"/"+date1[0];
									xml=xml+"<JOUR_DATE>"+date2+"</JOUR_DATE>";
 									
 								}
 								else
 								{
 									ps=con.prepareStatement("select * from FAS_ADJUST_MEMO_TRN where CASHBOOK_YEAR='"+txtCB_Year+"' and VOUCHER_NO='"+rs.getInt("VOUCHER_NO")+"'  and CASHBOOK_MONTH='"+txtCB_Month+"' and  ACCEPTANCE_STATUS='N'");
 									rs2=ps.executeQuery();
 									if(rs2.next())
 									{
 										String date1[]=rs2.getDate("ACCEPT_VOUCHER_DATE").toString().split("-");
 	 									String date2=date1[2]+"/"+date1[1]+"/"+date1[0];
 	 									xml=xml+"<JOUR_DATE>"+date2+"</JOUR_DATE>";
 									}				
 							    }
					    	}
	 			                xml=xml+"</lengcheck>";
	 			                count++;
	 		             }
	 			           if(count==0) 
		 	                {
		 	                    System.out.println("inside count==0");
		 	                    xml="<resp><command>searchByDate</command><flag>failure</flag>";
		 	                }
		              }
		              catch(SQLException sqle)
		              {
		                  System.out.println("error while fetching data " + sqle);
		                  xml="<resp><command>searchByDate</command><flag>failure</flag>";
		              }
		               
		         }
	         xml=xml+"</resp>";   
	         out.println(xml); 
	         System.out.println(xml);     
	         
	
	}


}
