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
public class Adj_Memo_MultipleReceipt_Accept_List extends HttpServlet {
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
	         String cmbStatus="",cmbAdvice="";
	         
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
	         
	         cmbAdvice=request.getParameter("cmbAdvice");
	         System.out.println("cmbAdvice.."+cmbAdvice);
	         
	         
	         if(cmbAcc_UnitCode==5){
	        	 System.out.println("Banking section");
	         if(strType.equalsIgnoreCase("searchByMonth"))  
	         {
	        	 if(cmbAdvice.equalsIgnoreCase("CR"))   
	        	 {
	        	 xml="<response><command>searchByMonth</command>";
	 	          sql="SELECT t.VOUCHER_NO,  "+
					  " TO_CHAR(m.VOUCHER_DATE,'DD/MM/YYYY') AS VOUCHER_DATE, "+
	 	        	 " t.FOR_ACCOUNTING_UNIT_ID, "+
	 	        	 " (SELECT u.accounting_unit_name FROM fas_mst_acct_units u WHERE u.accounting_unit_id=" +
	 	        	 " t.FOR_ACCOUNTING_UNIT_ID) AS OFFICE_NAME, "+
	 	        	 " t.REMARKS, "+
	 	        	 " trim(TO_CHAR(t.AMOUNT,'99999999999999.99'))AS TOTAL_AMOUNT, "+
	 	        	 " t.LETTER_NO, "+
	 	        	 " TO_CHAR(t.LETTER_DATE,'dd/mm/yyyy') AS LETTER_DATE "+
	 	        	 " FROM FAS_ADJUST_MEMO_MST m,FAS_ADJUST_MEMO_TRN t "+
	 	        	 " WHERE m.ACCOUNTING_UNIT_ID=t.ACCOUNTING_UNIT_ID "+
	 	        	 " and m.ACCOUNTING_FOR_OFFICE_ID=t.ACCOUNTING_FOR_OFFICE_ID "+
	 	        	 " and m.CASHBOOK_YEAR=t.CASHBOOK_YEAR "+
	 	        	 " and m.CASHBOOK_MONTH=t.CASHBOOK_MONTH "+
	 	        	 " and m.VOUCHER_NO=t.VOUCHER_NO "+
	 	        	 " and m.ACCOUNTING_UNIT_ID    =? "+
	 	        	 " AND m.ACCOUNTING_FOR_OFFICE_ID=? "+
	 	        	 " and extract (year from t.ACCEPT_VOUCHER_DATE)=? "+
	 	        	 " and extract (month from t.ACCEPT_VOUCHER_DATE)=? "+
	 	        	 " AND m.ADVICE_TYPE             =? "+
	 	        	 " AND m.MEMO_STATUS             =? "+
	 	        	 " AND t.ACCEPTANCE_STATUS       ='Y' "+
	 	        	 " AND t.ACCOUNT_HEAD_CODE       =610101 "+
	 	        	 " ORDER BY t.VOUCHER_NO ";
	        	 } else
	        	 {
	        		 xml="<response><command>searchByMonth</command>";
		 	          sql="SELECT t.VOUCHER_NO,  "+
						  " TO_CHAR(m.VOUCHER_DATE,'DD/MM/YYYY') AS VOUCHER_DATE, "+
		 	        	 " t.FOR_ACCOUNTING_UNIT_ID, "+
		 	        	 " (SELECT u.accounting_unit_name FROM fas_mst_acct_units u WHERE u.accounting_unit_id=" +
		 	        	 " t.FOR_ACCOUNTING_UNIT_ID) AS OFFICE_NAME, "+
		 	        	 " t.REMARKS, "+
		 	        	 " trim(TO_CHAR(t.AMOUNT,'99999999999999.99'))AS TOTAL_AMOUNT, "+
		 	        	 " t.LETTER_NO, "+
		 	        	 " TO_CHAR(t.LETTER_DATE,'dd/mm/yyyy') AS LETTER_DATE "+
		 	        	 " FROM FAS_ADJUST_MEMO_MST m,FAS_ADJUST_MEMO_TRN t "+
		 	        	 " WHERE m.ACCOUNTING_UNIT_ID=t.ACCOUNTING_UNIT_ID "+
		 	        	 " and m.ACCOUNTING_FOR_OFFICE_ID=t.ACCOUNTING_FOR_OFFICE_ID "+
		 	        	 " and m.CASHBOOK_YEAR=t.CASHBOOK_YEAR "+
		 	        	 " and m.CASHBOOK_MONTH=t.CASHBOOK_MONTH "+
		 	        	 " and m.VOUCHER_NO=t.VOUCHER_NO "+
		 	        	 " and m.ACCOUNTING_UNIT_ID    =? "+
		 	        	 " AND m.ACCOUNTING_FOR_OFFICE_ID=? "+
		 	        	 " and extract (year from t.ACCEPT_VOUCHER_DATE)=? "+
		 	        	 " and extract (month from t.ACCEPT_VOUCHER_DATE)=? "+
		 	        	 " AND m.ADVICE_TYPE             =? "+
		 	        	 " AND m.MEMO_STATUS             =? "+
		 	        	 " AND t.ACCEPTANCE_STATUS       ='Y' "+
		 	        	 " AND t.ACCOUNT_HEAD_CODE       =900201 "+
		 	        	 " ORDER BY t.VOUCHER_NO ";
	        	 }
	 	           System.out.println("sql:::"+sql);
	 	            try
	 	            {
	 			            int count=0;
	 			            ps=con.prepareStatement(sql);
	 			            ps.setInt(1,cmbAcc_UnitCode);
	 			            ps.setInt(2,cmbOffice_code);
	 			            ps.setInt(3,txtCB_Year);
	 			            ps.setInt(4,txtCB_Month);
	 			          //  ps.setInt(5, 1);
	 			           ps.setInt(5, 2);
	 			            ps.setString(6, cmbStatus);
	 			            xml=xml+"<flag>success</flag>" ;
	 			            xml=xml+"<Ucode>"+cmbAcc_UnitCode+"</Ucode>" ;
	 			            xml=xml+"<Offid>"+cmbOffice_code+"</Offid>";
	 			            xml=xml+"<txtCB_Year>"+txtCB_Year+"</txtCB_Year>";
	 			            xml=xml+"<txtCB_Month>"+txtCB_Month+"</txtCB_Month>";
	 			            
	 			            rs=ps.executeQuery();
	 			            
	 			           while(rs.next())
	 		                {
	 			        	  xml=xml+"<lengcheck>";
	 			        	  		xml=xml+"<for_unit_id>"+rs.getInt("FOR_ACCOUNTING_UNIT_ID")+"</for_unit_id>";
	 			        	  		xml=xml+"<OFFICE_NAME>"+rs.getString("OFFICE_NAME")+"</OFFICE_NAME>";
	 			                    xml=xml+"<VOUCHER_NO>"+rs.getInt("VOUCHER_NO")+"</VOUCHER_NO>";
	 			                    xml=xml+"<VOUCHER_DATE>"+rs.getString("VOUCHER_DATE")+"</VOUCHER_DATE>";
	 			                 
	 			                   xml=xml+"<PARTICULARS><![CDATA[" +rs.getString("REMARKS") + "]]></PARTICULARS>";
	 			                    xml=xml+"<TOTAL_AMOUNT>"+rs.getString("TOTAL_AMOUNT") +"</TOTAL_AMOUNT>"; 
	 			                    xml=xml+"<receiptno>"+rs.getInt("LETTER_NO")+"</receiptno>";
	 			                 
	 		 	                    xml=xml+"<receiptdate>"+rs.getString("LETTER_DATE")+"</receiptdate>";
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
	            
	             
	             if(cmbAdvice.equalsIgnoreCase("CR"))   
	        	 {
	             sql="SELECT t.VOUCHER_NO,  "+
				  " TO_CHAR(m.VOUCHER_DATE,'DD/MM/YYYY') AS VOUCHER_DATE, "+
	        	 " t.FOR_ACCOUNTING_UNIT_ID, "+
	        	 " (SELECT u.accounting_unit_name FROM fas_mst_acct_units u WHERE u.accounting_unit_id=" +
	        	 " t.FOR_ACCOUNTING_UNIT_ID) AS OFFICE_NAME, "+
	        	 " t.REMARKS, "+
	        	 " trim(TO_CHAR(t.AMOUNT,'99999999999999.99'))AS TOTAL_AMOUNT, "+
	        	 " t.LETTER_NO, "+
	        	 " TO_CHAR(t.LETTER_DATE,'dd/mm/yyyy') AS LETTER_DATE "+
	        	 " FROM FAS_ADJUST_MEMO_MST m,FAS_ADJUST_MEMO_TRN t "+
	        	 " WHERE m.ACCOUNTING_UNIT_ID=t.ACCOUNTING_UNIT_ID "+
	        	 " and m.ACCOUNTING_FOR_OFFICE_ID=t.ACCOUNTING_FOR_OFFICE_ID "+
	        	 " and m.CASHBOOK_YEAR=t.CASHBOOK_YEAR "+
	        	 " and m.CASHBOOK_MONTH=t.CASHBOOK_MONTH "+
	        	 " and m.VOUCHER_NO=t.VOUCHER_NO "+
	        	 " and m.ACCOUNTING_UNIT_ID    =? "+
	        	 " AND m.ACCOUNTING_FOR_OFFICE_ID=? "+
	        	 " and (t.ACCEPT_VOUCHER_DATE between ? and ?) "+
	        	 " AND m.ADVICE_TYPE             =? "+
	        	 " AND m.MEMO_STATUS             =? "+
	        	 " AND t.ACCEPTANCE_STATUS       ='Y' "+
	        	 " AND t.ACCOUNT_HEAD_CODE       =610101 "+
	        	 " ORDER BY t.VOUCHER_NO "; 
	        	 }else
	        	 {
	        		 sql="SELECT t.VOUCHER_NO,  "+
	       				  " TO_CHAR(m.VOUCHER_DATE,'DD/MM/YYYY') AS VOUCHER_DATE, "+
	       	        	 " t.FOR_ACCOUNTING_UNIT_ID, "+
	       	        	 " (SELECT u.accounting_unit_name FROM fas_mst_acct_units u WHERE u.accounting_unit_id=" +
	       	        	 " t.FOR_ACCOUNTING_UNIT_ID) AS OFFICE_NAME, "+
	       	        	 " t.REMARKS, "+
	       	        	 " trim(TO_CHAR(t.AMOUNT,'99999999999999.99'))AS TOTAL_AMOUNT, "+
	       	        	 " t.LETTER_NO, "+
	       	        	 " TO_CHAR(t.LETTER_DATE,'dd/mm/yyyy') AS LETTER_DATE "+
	       	        	 " FROM FAS_ADJUST_MEMO_MST m,FAS_ADJUST_MEMO_TRN t "+
	       	        	 " WHERE m.ACCOUNTING_UNIT_ID=t.ACCOUNTING_UNIT_ID "+
	       	        	 " and m.ACCOUNTING_FOR_OFFICE_ID=t.ACCOUNTING_FOR_OFFICE_ID "+
	       	        	 " and m.CASHBOOK_YEAR=t.CASHBOOK_YEAR "+
	       	        	 " and m.CASHBOOK_MONTH=t.CASHBOOK_MONTH "+
	       	        	 " and m.VOUCHER_NO=t.VOUCHER_NO "+
	       	        	 " and m.ACCOUNTING_UNIT_ID    =? "+
	       	        	 " AND m.ACCOUNTING_FOR_OFFICE_ID=? "+
	       	        	 " and (t.ACCEPT_VOUCHER_DATE between ? and ?) "+
	       	        	 " AND m.ADVICE_TYPE             =? "+
	       	        	 " AND m.MEMO_STATUS             =? "+
	       	        	 " AND t.ACCEPTANCE_STATUS       ='Y' "+
	       	        	 " AND t.ACCOUNT_HEAD_CODE       =900201 "+
	       	        	 " ORDER BY t.VOUCHER_NO ";  
	        	 }
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
			        	  		xml=xml+"<for_unit_id>"+rs.getInt("FOR_ACCOUNTING_UNIT_ID")+"</for_unit_id>";
			        	  		xml=xml+"<OFFICE_NAME>"+rs.getString("OFFICE_NAME")+"</OFFICE_NAME>";
			                    xml=xml+"<VOUCHER_NO>"+rs.getInt("VOUCHER_NO")+"</VOUCHER_NO>";
			                    xml=xml+"<VOUCHER_DATE>"+rs.getString("VOUCHER_DATE")+"</VOUCHER_DATE>";
			                 
			                    xml=xml+"<PARTICULARS><![CDATA[" +rs.getString("REMARKS") + "]]></PARTICULARS>";
			                    xml=xml+"<TOTAL_AMOUNT>"+rs.getString("TOTAL_AMOUNT") +"</TOTAL_AMOUNT>"; 
			                    xml=xml+"<receiptno>"+rs.getInt("LETTER_NO")+"</receiptno>";
			                 
		 	                    xml=xml+"<receiptdate>"+rs.getString("LETTER_DATE")+"</receiptdate>";
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
	       }
			else
			{
			System.out.println("all other units");	
				 if(strType.equalsIgnoreCase("searchByMonth"))  
		         {
		        	    xml="<response><command>searchByMonth</command>";
		        	    
		        	    sql="SELECT t.VOUCHER_NO," + 
		        	    		"  TO_CHAR(m.VOUCHER_DATE,'DD/MM/YYYY') AS VOUCHER_DATE," + 
		        	    		"  t.accounting_unit_id," + 
		        	    		"  (SELECT u.accounting_unit_name" + 
		        	    		"  FROM fas_mst_acct_units u" + 
		        	    		"  WHERE u.accounting_unit_id= t.ACCOUNTING_UNIT_ID" + 
		        	    		"  ) as office_name," + 
		        	    		"  t.for_accounting_unit_id as acceptunit," + 
		        	    		"  (SELECT u.accounting_unit_name" + 
		        	    		"  from fas_mst_acct_units u" + 
		        	    		"  where u.accounting_unit_id= t.for_accounting_unit_id" + 
		        	    		"  ) as acpt_office_name," + 
		        	    		" TO_CHAR(t.accept_voucher_date,'dd/mm/yyyy') AS accept_voucher_date, " +
		        	    		"  t.REMARKS," + 
		        	    		"  trim(TO_CHAR(t.AMOUNT,'99999999999999.99'))AS TOTAL_AMOUNT," + 
		        	    		"  m.receipt_no," + 
		        	    		"  TO_CHAR(m.receipt_date,'dd/mm/yyyy') AS receipt_date " +
				 	        	 " FROM FAS_ADJUST_MEMO_MST m,FAS_ADJUST_MEMO_TRN t "+
				 	        	 " WHERE m.ACCOUNTING_UNIT_ID                    =t.ACCOUNTING_UNIT_ID "+
				 	        	 " AND m.ACCOUNTING_FOR_OFFICE_ID                =t.ACCOUNTING_FOR_OFFICE_ID "+
				 	        	 " AND m.CASHBOOK_YEAR                           =t.CASHBOOK_YEAR "+
				 	        	 " AND m.CASHBOOK_MONTH                          =t.CASHBOOK_MONTH "+
				 	        	 " AND m.VOUCHER_NO                              =t.VOUCHER_NO "+
				 	        	 " AND t.for_accounting_unit_id=? "+
				 	        	 " AND extract (YEAR FROM t.ACCEPT_VOUCHER_DATE) =? "+
				 	        	 " AND extract (MONTH FROM t.ACCEPT_VOUCHER_DATE)=? "+
				 	        	 " AND m.ADVICE_TYPE                             =? "+
				 	        	 " AND m.MEMO_STATUS                             =? "+
				 	        	 " AND t.ACCEPTANCE_STATUS                       ='Y' "+
				 	        	 " ORDER BY t.VOUCHER_NO  ";
		        	    
		        	    
		        	    
		        	    
//	commended on 05-11-2019     sql="SELECT t.VOUCHER_NO, "+
//						"  TO_CHAR(m.VOUCHER_DATE,'DD/MM/YYYY') AS VOUCHER_DATE, "+
//						  " t.ACCOUNTING_UNIT_ID, "+
//		 	        	 " (SELECT u.accounting_unit_name FROM fas_mst_acct_units u "+
//		 	        	 " WHERE u.accounting_unit_id= t.ACCOUNTING_UNIT_ID "+
//		 	        	 " ) AS OFFICE_NAME, "+
//		 	        	 " t.REMARKS, "+
//		 	        	 " trim(TO_CHAR(t.AMOUNT,'99999999999999.99'))AS TOTAL_AMOUNT, "+
//		 	        	 "   m.receipt_no, "+
//		 	        	 "  TO_CHAR(m.receipt_date,'dd/mm/yyyy') AS receipt_date "+
//		 	        	 " FROM FAS_ADJUST_MEMO_MST m,FAS_ADJUST_MEMO_TRN t "+
//		 	        	 " WHERE m.ACCOUNTING_UNIT_ID                    =t.ACCOUNTING_UNIT_ID "+
//		 	        	 " AND m.ACCOUNTING_FOR_OFFICE_ID                =t.ACCOUNTING_FOR_OFFICE_ID "+
//		 	        	 " AND m.CASHBOOK_YEAR                           =t.CASHBOOK_YEAR "+
//		 	        	 " AND m.CASHBOOK_MONTH                          =t.CASHBOOK_MONTH "+
//		 	        	 " AND m.VOUCHER_NO                              =t.VOUCHER_NO "+
//		 	        	 " AND t.for_accounting_unit_id=? "+
//		 	        	 " AND extract (YEAR FROM t.ACCEPT_VOUCHER_DATE) =? "+
//		 	        	 " AND extract (MONTH FROM t.ACCEPT_VOUCHER_DATE)=? "+
//		 	        	 " AND m.ADVICE_TYPE                             =? "+
//		 	        	 " AND m.MEMO_STATUS                             =? "+
//		 	        	 " AND t.ACCEPTANCE_STATUS                       ='Y' "+
//		 	        	 " ORDER BY t.VOUCHER_NO  ";
		 	           System.out.println("sql:::"+sql);
		 	            try
		 	            {
		 			            int count=0;
		 			            ps=con.prepareStatement(sql);
		 			            ps.setInt(1,cmbAcc_UnitCode);
		 			            ps.setInt(2,txtCB_Year);
		 			            ps.setInt(3,txtCB_Month);
		 			            ps.setInt(4, 2);
		 			            ps.setString(5, cmbStatus);
		 			            xml=xml+"<flag>success</flag>" ;
		 			            xml=xml+"<Ucode>"+cmbAcc_UnitCode+"</Ucode>" ;
		 			           System.out.println("cmbAcc_UnitCode>>>>"+cmbAcc_UnitCode);
		 			            xml=xml+"<txtCB_Year>"+txtCB_Year+"</txtCB_Year>";
		 			           System.out.println("txtCB_Year>>>>"+txtCB_Year);
		 			            xml=xml+"<txtCB_Month>"+txtCB_Month+"</txtCB_Month>";
		 			           System.out.println("txtCB_Month>>>>"+txtCB_Month);
		 			            
		 			            rs=ps.executeQuery();
		 			            
		 			           while(rs.next())
		 		                {
		 			        	    xml=xml+"<lengcheck>";
	 			        	  		xml=xml+"<for_unit_id>"+rs.getInt("ACCOUNTING_UNIT_ID")+"</for_unit_id>";
	 			        	  	 System.out.println("for_unit_id>>>>"+rs.getInt("ACCOUNTING_UNIT_ID"));
	 			        	  		xml=xml+"<OFFICE_NAME>"+rs.getString("OFFICE_NAME")+"</OFFICE_NAME>";
	 			        	  		
	 			        	  		
	 			        	  	 System.out.println("OFFICE_NAME>>>>"+rs.getString("OFFICE_NAME"));
	 			        	  		xml=xml+"<VOUCHER_NO>"+rs.getInt("VOUCHER_NO")+"</VOUCHER_NO>";
	 			        	  	 System.out.println("VOUCHER_NO>>>>"+rs.getInt("VOUCHER_NO"));
	 			        	  		xml=xml+"<VOUCHER_DATE>"+rs.getString("VOUCHER_DATE")+"</VOUCHER_DATE>";
	 			        	  	 System.out.println("VOUCHER_DATE>>>>"+rs.getString("VOUCHER_DATE"));
	 			        	  		xml=xml+"<acceptunit>" +rs.getInt("acceptunit")+"</acceptunit> ";
	 			        	  	 System.out.println("acceptunit>>>>"+rs.getInt("acceptunit"));
	 			        	  		xml=xml+"<acpt_office_name>" +rs.getString("acpt_office_name")+"</acpt_office_name> ";
	 			        	  	 System.out.println("acpt_office_name>>>>"+rs.getString("acpt_office_name"));
		 			                xml=xml+"<accept_voucher_date>"+rs.getString("accept_voucher_date")+"</accept_voucher_date>";
		 			             System.out.println("accept_voucher_date>>>>"+rs.getString("accept_voucher_date"));
	 			                    xml=xml+"<PARTICULARS><![CDATA[" +rs.getString("REMARKS") + "]]></PARTICULARS>";
	 			                 System.out.println("PARTICULARS>>>>"+rs.getString("REMARKS"));
	 			                    xml=xml+"<TOTAL_AMOUNT>"+rs.getString("TOTAL_AMOUNT") +"</TOTAL_AMOUNT>"; 
	 			                 System.out.println("TOTAL_AMOUNT>>>>"+rs.getString("TOTAL_AMOUNT"));
	 			                    xml=xml+"<receiptno>"+rs.getInt("receipt_no")+"</receiptno>";	
	 			                 System.out.println("receiptno>>>>"+rs.getInt("receipt_no"));
	 		 	                    xml=xml+"<receiptdate>"+rs.getString("receipt_date")+"</receiptdate>";
	 		 	                System.out.println("receiptdate>>>>"+rs.getString("receipt_date"));
	 			                    xml=xml+"</lengcheck>";
		 			        	   
		 			        	   
//		 			        	  xml=xml+"<lengcheck>";
//		 			        	  		xml=xml+"<for_unit_id>"+rs.getInt("ACCOUNTING_UNIT_ID")+"</for_unit_id>";
//		 			        	  		xml=xml+"<OFFICE_NAME>"+rs.getString("OFFICE_NAME")+"</OFFICE_NAME>";
//		 			                    xml=xml+"<VOUCHER_NO>"+rs.getInt("VOUCHER_NO")+"</VOUCHER_NO>";
//		 			                    xml=xml+"<VOUCHER_DATE>"+rs.getString("VOUCHER_DATE")+"</VOUCHER_DATE>";
//		 			                    xml=xml+"<acceptunit>" +rs.getInt("acceptunit")+"</acceptunit> ";
//		 			                    xml=xml+"<acpt_office_name>" +rs.getInt("acpt_office_name")+"</acpt_office_name> ";
//		 			                   xml=xml+"<accept_voucher_date>"+rs.getString("accept_voucher_date")+"</accept_voucher_date>";
//		 			                    xml=xml+"<PARTICULARS>"+rs.getString("REMARKS") +"</PARTICULARS>";
//		 			                    xml=xml+"<TOTAL_AMOUNT>"+rs.getString("TOTAL_AMOUNT") +"</TOTAL_AMOUNT>"; 
//		 			                    xml=xml+"<receiptno>"+rs.getInt("receipt_no")+"</receiptno>";
//		 			                 
//		 		 	                    xml=xml+"<receiptdate>"+rs.getString("receipt_date")+"</receiptdate>";
//		 			                    xml=xml+"</lengcheck>";
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
		             
		             sql="SELECT t.VOUCHER_NO, "+
						"  TO_CHAR(m.VOUCHER_DATE,'DD/MM/YYYY') AS VOUCHER_DATE, "+
						  " t.ACCOUNTING_UNIT_ID, "+
		 	        	 " (SELECT u.accounting_unit_name FROM fas_mst_acct_units u "+
		 	        	 " WHERE u.accounting_unit_id= t.ACCOUNTING_UNIT_ID "+
		 	        	 " ) AS OFFICE_NAME, "+
		 	        	 " t.REMARKS, "+
		 	        	 " trim(TO_CHAR(t.AMOUNT,'99999999999999.99'))AS TOTAL_AMOUNT, "+
		 	        	 "   m.receipt_no, "+
		 	        	 "  TO_CHAR(m.receipt_date,'dd/mm/yyyy') AS receipt_date "+
		 	        	 " FROM FAS_ADJUST_MEMO_MST m,FAS_ADJUST_MEMO_TRN t "+
		 	        	 " WHERE m.ACCOUNTING_UNIT_ID                    =t.ACCOUNTING_UNIT_ID "+
		 	        	 " AND m.ACCOUNTING_FOR_OFFICE_ID                =t.ACCOUNTING_FOR_OFFICE_ID "+
		 	        	 " AND m.CASHBOOK_YEAR                           =t.CASHBOOK_YEAR "+
		 	        	 " AND m.CASHBOOK_MONTH                          =t.CASHBOOK_MONTH "+
		 	        	 " AND m.VOUCHER_NO                              =t.VOUCHER_NO "+
		 	        	 " AND t.for_accounting_unit_id=? "+
		 	        	 " AND (t.ACCEPT_VOUCHER_DATE between ? and ?) "+
		 	        	 " AND m.ADVICE_TYPE                             =? "+
		 	        	 " AND m.MEMO_STATUS                             =? "+
		 	        	 " AND t.ACCEPTANCE_STATUS                       ='Y' "+
		 	        	 " ORDER BY t.VOUCHER_NO  ";
		 	           System.out.println("sql:::"+sql);
		             try
		             {
		 	            	int count=0;
		 	            	ps=con.prepareStatement(sql);
		 	            	ps.setInt(1,cmbAcc_UnitCode);
		 	            	
		 	            	ps.setDate(2,txtFrom_date);
		 	            	ps.setDate(3,txtTo_date);
		 	            	 ps.setInt(4, 2);
		 	            	ps.setString(5,cmbStatus);
		 	            	xml=xml+"<flag>success</flag>" ;
		 	                xml=xml+"<Ucode>"+cmbAcc_UnitCode+"</Ucode>" ;
		 	              //  xml=xml+"<Offid>"+cmbOffice_code+"</Offid>";
		 	                xml=xml+"<txtCB_Year>"+txtCB_Year+"</txtCB_Year>";
				            xml=xml+"<txtCB_Month>"+txtCB_Month+"</txtCB_Month>"; 
		 	                
		 	                rs=ps.executeQuery();
		 	                  
		 	            	while(rs.next())
		 	                {
		 	            		xml=xml+"<lengcheck>";
 			        	  		xml=xml+"<for_unit_id>"+rs.getInt("ACCOUNTING_UNIT_ID")+"</for_unit_id>";
 			        	  		xml=xml+"<OFFICE_NAME>"+rs.getString("OFFICE_NAME")+"</OFFICE_NAME>";
 			                    xml=xml+"<VOUCHER_NO>"+rs.getInt("VOUCHER_NO")+"</VOUCHER_NO>";
 			                    xml=xml+"<VOUCHER_DATE>"+rs.getString("VOUCHER_DATE")+"</VOUCHER_DATE>";
 			                 
 			                   xml=xml+"<PARTICULARS><![CDATA[" +rs.getString("REMARKS") + "]]></PARTICULARS>";
 			                    xml=xml+"<TOTAL_AMOUNT>"+rs.getString("TOTAL_AMOUNT") +"</TOTAL_AMOUNT>"; 
 			                    xml=xml+"<receiptno>"+rs.getInt("receipt_no")+"</receiptno>";
 			                 
 		 	                    xml=xml+"<receiptdate>"+rs.getString("receipt_date")+"</receiptdate>";
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
				
			}
	         xml=xml+"</response>";   
	         out.println(xml); 
	         System.out.println(xml);     
	         
	}

}
