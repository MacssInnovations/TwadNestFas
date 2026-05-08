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
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Adj_Memo_SingleReceipt_List
 */
public class Adj_Memo_MultipleReceipt_Reject_List extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";  
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		  String CONTENT_TYPE = "text/xml";
		  response.setHeader("Cache-Control", "no-cache");
		  PrintWriter out = response.getWriter();
		  String strType = "";
	      String xml="<response>";
		  
		  /*-------------------------- Session Checking -----------------------------*/
	        
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
	         Statement stmt=null;
	         PreparedStatement ps=null;
	         PreparedStatement ps2=null;
	         
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
	                          ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
	                          Class.forName(strDriver.trim());
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
	         String txtReceipt_type="";
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
	 	             xml="<response><command>searchByMonth</command>";
	 	          
					 	             sql=" SELECT T.VOUCHER_NO, "+
									  " TO_CHAR(M.VOUCHER_DATE,'DD/MM/YYYY') AS VOUCHER_DATE, "+
					 	            	" t.FOR_ACCOUNTING_UNIT_ID, "+
					 	            	" (SELECT OFFICE_NAME "+
					 	            	" FROM COM_MST_OFFICES "+
					 	            	" WHERE OFFICE_ID=t.FOR_ACCOUNTING_UNIT_ID "+
					 	            	" ) AS OFFICE_NAME, "+
					 	            	" T.REMARKS, "+
					 	            	" TRIM(TO_CHAR(T.AMOUNT,'99999999999999.99'))AS TOTAL_AMOUNT, "+
					 	            	" T. LETTER_NO, "+
					 	            	" TO_CHAR(t.LETTER_DATE,'dd/mm/yyyy') AS LETTER_DATE,TO_CHAR(t.ACCEPT_VOUCHER_DATE,'dd/mm/yyyy') AS rejected_date "+
					 	            	" FROM FAS_ADJUST_MEMO_MST M,FAS_ADJUST_MEMO_TRN T "+
					 	            	" WHERE M.ACCOUNTING_UNIT_ID=T.ACCOUNTING_UNIT_ID "+
					 	            	" AND M.ACCOUNTING_FOR_OFFICE_ID=T.ACCOUNTING_FOR_OFFICE_ID "+
					 	            	" AND M.CASHBOOK_YEAR=T.CASHBOOK_YEAR "+
					 	            	" AND M.CASHBOOK_MONTH=T.CASHBOOK_MONTH "+
					 	            	" AND M.VOUCHER_NO=T.VOUCHER_NO "+
					 	            	" AND T.ACCOUNTING_UNIT_ID    =? "+
					 	            	" AND T.ACCOUNTING_FOR_OFFICE_ID=? "+
					 	            	" AND T.CASHBOOK_YEAR           =? "+
					 	            	" AND T.CASHBOOK_MONTH          =? "+
					 	            	" AND M.ADVICE_TYPE             =2 "+
					 	            	" AND M.MEMO_STATUS             =? "+
					 	            	" AND T.ACCEPTANCE_STATUS       ='N' "+
					 	            	" ORDER BY t.VOUCHER_NO ";
	 	             System.out.println("SQL:::"+sql);
	 	             try
	 	            {
	 			            int count=0;
	 			            ps=con.prepareStatement(sql);
	 			            ps.setInt(1,cmbAcc_UnitCode);
	 			            ps.setInt(2,cmbOffice_code);
	 			            ps.setInt(3,txtCB_Year);
	 			            ps.setInt(4,txtCB_Month);
	 			          //  ps.setInt(5, 1);
	 			            ps.setString(5, cmbStatus);
	 			            xml=xml+"<flag>success</flag>" ;
	 			            xml=xml+"<Ucode>"+cmbAcc_UnitCode+"</Ucode>" ;
	 			            xml=xml+"<Offid>"+cmbOffice_code+"</Offid>";
	 			            xml=xml+"<txtCB_Year>"+txtCB_Year+"</txtCB_Year>";
	 			            xml=xml+"<txtCB_Month>"+txtCB_Month+"</txtCB_Month>";
	 			            
	 			            rs=ps.executeQuery();
	 			            
	 			           while(rs.next())
	 		                {
	 			        	  xml=xml+"<lengcheck>";
	 			        	   if(rs.getString("FOR_ACCOUNTING_UNIT_ID")!=null)
	 			        	   {
	 			        	        ps=con.prepareStatement("select * from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID='"+rs.getInt("FOR_ACCOUNTING_UNIT_ID")+"'");
	 			        	        ResultSet rs1=ps.executeQuery();
	 			        	        if(rs1.next())
	 			        	        {
	 			        	           xml=xml+"<OFFICE_NAME>"+rs1.getString("ACCOUNTING_UNIT_NAME")+"</OFFICE_NAME>";
	 			        	        }
	 			        	   
	 			        	   else
	 			        	   {
	 			        		  xml=xml+"<OFFICE_NAME>---</OFFICE_NAME>";
	 			        	   }
	 			        	   }
	 			        	
	 			                    xml=xml+"<for_unit>"+rs.getInt("FOR_ACCOUNTING_UNIT_ID")+"</for_unit>";
	 			                    xml=xml+"<VOUCHER_NO>"+rs.getInt("VOUCHER_NO")+"</VOUCHER_NO>";
	 			                    xml=xml+"<VOUCHER_DATE>"+rs.getString("VOUCHER_DATE")+"</VOUCHER_DATE>";
	 			                 
	 			                    xml=xml+"<PARTICULARS>"+rs.getString("REMARKS") +"</PARTICULARS>";
	 			                    xml=xml+"<TOTAL_AMOUNT>"+rs.getString("TOTAL_AMOUNT") +"</TOTAL_AMOUNT>"; 
	 			                    xml=xml+"<receiptno>"+rs.getInt("LETTER_NO")+"</receiptno>";
	 			                   xml=xml+"<receiptdate>"+rs.getString("LETTER_DATE")+"</receiptdate>";
	 		 	                    xml=xml+"<rejected_date>"+rs.getString("rejected_date")+"</rejected_date>";
	 		 	                  
	 			                    xml=xml+"</lengcheck>";
	 			                    count++;
	 		                }
	 		               if(count==0) 
	 		               {
	 			                    System.out.println("inside count==0");
	 			                    xml="<response><command>searchByMonth</command><flag>failure</flag>";
	 		               }
	 	           }
	 	           catch(SQLException sqle)
	 	           {
	 	                System.out.println("error while fetching data " + sqle);
	 	                xml="<response><command>searchByMonth</command><flag>failure</flag>";
	 	           }
	             
	         }
	         /* Search By Given Date */
	         
	         else if(strType.equalsIgnoreCase("searchByDate"))
	         {
	             
	         	xml="<response><command>searchByDate</command>";
	             
	             
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
	             
	             sql=" SELECT T.VOUCHER_NO, "+
				  " TO_CHAR(M.VOUCHER_DATE,'DD/MM/YYYY') AS VOUCHER_DATE, "+
	            	" t.FOR_ACCOUNTING_UNIT_ID, "+
	            	" (SELECT OFFICE_NAME "+
	            	" FROM COM_MST_OFFICES "+
	            	" WHERE OFFICE_ID=t.FOR_ACCOUNTING_UNIT_ID "+
	            	" ) AS OFFICE_NAME, "+
	            	" T.REMARKS, "+
	            	" TRIM(TO_CHAR(T.AMOUNT,'99999999999999.99'))AS TOTAL_AMOUNT, "+
	            	" T. LETTER_NO, "+
	            	" TO_CHAR(t.LETTER_DATE,'dd/mm/yyyy') AS LETTER_DATE,TO_CHAR(t.ACCEPT_VOUCHER_DATE,'dd/mm/yyyy') AS rejected_date "+
	            	" FROM FAS_ADJUST_MEMO_MST M,FAS_ADJUST_MEMO_TRN T "+
	            	" WHERE M.ACCOUNTING_UNIT_ID=T.ACCOUNTING_UNIT_ID "+
	            	" AND M.ACCOUNTING_FOR_OFFICE_ID=T.ACCOUNTING_FOR_OFFICE_ID "+
	            	" AND M.CASHBOOK_YEAR=T.CASHBOOK_YEAR "+
	            	" AND M.CASHBOOK_MONTH=T.CASHBOOK_MONTH "+
	            	" AND M.VOUCHER_NO=T.VOUCHER_NO "+
	            	" AND T.ACCOUNTING_UNIT_ID    =? "+
	            	" AND T.ACCOUNTING_FOR_OFFICE_ID=? "+
	            	" and m.VOUCHER_DATE between ? and ? "+
	            	" AND M.ADVICE_TYPE             =? "+
	            	" AND M.MEMO_STATUS             =? "+
	            	" AND T.ACCEPTANCE_STATUS       ='N' "+
	            	" ORDER BY t.VOUCHER_NO ";
 System.out.println("SQL:::"+sql);
	             
	             try
	             {
	 	            	int count=0;
	 	            	ps=con.prepareStatement(sql);
	 	            	ps.setInt(1,cmbAcc_UnitCode);
	 	            	ps.setInt(2,cmbOffice_code);

	 	            	ps.setDate(3,txtFrom_date);
	 	            	ps.setDate(4,txtTo_date);
	 	            	 ps.setInt(5, 2);
	 	            	ps.setString(6,cmbStatus);
	 	            	xml=xml+"<flag>success</flag>" ;
	 	                xml=xml+"<Ucode>"+cmbAcc_UnitCode+"</Ucode>" ;
	 	                xml=xml+"<Offid>"+cmbOffice_code+"</Offid>";
 	                xml=xml+"<txtCB_Year>"+txtCB_Year+"</txtCB_Year>";
			            xml=xml+"<txtCB_Month>"+txtCB_Month+"</txtCB_Month>"; 
	 	                
	 	                rs=ps.executeQuery();
	 	                  
	 	            	while(rs.next())
	 	                {

	 			        	  xml=xml+"<lengcheck>";
	 			        	   if(rs.getString("FOR_ACCOUNTING_UNIT_ID")!=null)
	 			        	   {
	 			        	        ps=con.prepareStatement("select * from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID='"+rs.getInt("FOR_ACCOUNTING_UNIT_ID")+"'");
	 			        	        ResultSet rs1=ps.executeQuery();
	 			        	        if(rs1.next())
	 			        	        {
	 			        	           xml=xml+"<OFFICE_NAME>"+rs1.getString("ACCOUNTING_UNIT_NAME")+"</OFFICE_NAME>";
	 			        	        }
	 			        	   
	 			        	   else
	 			        	   {
	 			        		  xml=xml+"<OFFICE_NAME>---</OFFICE_NAME>";
	 			        	   }
	 			        	   }
	 			        	
	 			                    xml=xml+"<for_unit>"+rs.getInt("FOR_ACCOUNTING_UNIT_ID")+"</for_unit>";
	 			                    xml=xml+"<VOUCHER_NO>"+rs.getInt("VOUCHER_NO")+"</VOUCHER_NO>";
	 			                    xml=xml+"<VOUCHER_DATE>"+rs.getString("VOUCHER_DATE")+"</VOUCHER_DATE>";
	 			                 
	 			                    xml=xml+"<PARTICULARS>"+rs.getString("REMARKS") +"</PARTICULARS>";
	 			                    xml=xml+"<TOTAL_AMOUNT>"+rs.getString("TOTAL_AMOUNT") +"</TOTAL_AMOUNT>"; 
	 			                    xml=xml+"<receiptno>"+rs.getInt("LETTER_NO")+"</receiptno>";
	 			                 
	 		 	                    xml=xml+"<receiptdate>"+rs.getString("LETTER_DATE")+"</receiptdate>";
	 		 	                  xml=xml+"<rejected_date>"+rs.getString("rejected_date")+"</rejected_date>";
	 			                    xml=xml+"</lengcheck>";
	 			                    count++;
	 		                }
	 	                
	 	                if(count==0) 
	 	                {
	 	                    System.out.println("inside count==0");
	 	                    xml="<response><command>searchByDate</command><flag>failure</flag>";
	 	                }
	              }
	              catch(SQLException sqle)
	              {
	                  System.out.println("error while fetching data " + sqle);
	                  xml="<response><command>searchByDate</command><flag>failure</flag>";
	              }
	         }
	         xml=xml+"</response>";   
	         out.println(xml); 
	         System.out.println(xml);     
	         
	}

}
