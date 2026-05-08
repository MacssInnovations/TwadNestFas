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
import java.sql.Types;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Adj_Memo_SingleReceipt_Creation
 */
public class Adj_Memo_SingleReceipt_Creation extends HttpServlet {
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
			  int count=0,txtAcc_HeadCode=0;
			  int cashbookyear=0,cashbookmonth=0,cmbAcc_UnitCode=0,cmbOffice_code=0,txtReceipt_No=0,receiptyear=0,receiptmonth=0;;
			   Calendar c;
	            Date txtCrea_date=null;
	            int txtCash_Month_hid=0,txtCash_year=0;
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
	            
	           
	          if(strCommand.equals("load_Voucher_No"))
	          {
	        	  
	        	  System.out.println("==========================================>"+request.getParameter("txtCrea_date"));
	        	  String[] sd=request.getParameter("txtCrea_date").split("/");
		            c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
		            java.util.Date d=c.getTime();
		            txtCrea_date=new Date(d.getTime());
		            System.out.println("txtCrea_date "+txtCrea_date);
		            
		            System.out.println("b4 getting month and year");
		            try{txtCash_year=Integer.parseInt(request.getParameter("txtCB_Year"));}
		            catch(Exception e){System.out.println("exception"+e );}
		            System.out.println("txtCash_year "+txtCash_year);
		            
		            try{txtCash_Month_hid=Integer.parseInt(request.getParameter("txtCB_Month"));}
		            catch(Exception e){System.out.println("exception"+e );}
		            System.out.println("txtCash_Month_hid "+txtCash_Month_hid);
		            
		            try{txtAcc_HeadCode=Integer.parseInt(request.getParameter("txtAcc_HeadCode"));}
		              catch(Exception e){System.out.println("exception"+e );}
		              System.out.println("txtAcc_HeadCode "+txtAcc_HeadCode);
		            String adj_Type=request.getParameter("adj_Type");
		            System.out.println("adj_Type--->"+adj_Type);
		             
		            String qry="";
	        	  xml="<response><command>receiptNo</command>"; 
	              try 
	                      {
	            	  if(adj_Type.equalsIgnoreCase("Receipt"))
	            	  {
	            		  //changed on 11-01-2018 by Kanaga (geetharani mam)
	            		  //	            	  qry="select RECEIPT_NO from FAS_RECEIPT_TRANSACTION where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=?  and CASHBOOK_MONTH=? and ACCOUNT_HEAD_CODE=? and RECEIPT_NO not in (select RECEIPT_NO from FAS_ADJUST_MEMO_MST where RECEIPTNO_YEAR=? and RECEIPTNO_MONTH=? and RECEIPT_NO is not null union (select RECEIPT_NO from FAS_ADJUST_MEMO_TRN where  RECEIPTNO_YEAR=? and RECEIPTNO_MONTH=? and RECEIPT_NO is not null )) ";
	            	  
//		              qry="select a.RECEIPT_NO ,a.SL_NO from FAS_RECEIPT_TRANSACTION a where a.ACCOUNTING_UNIT_ID=? and a.ACCOUNTING_FOR_OFFICE_ID=? and a.CASHBOOK_YEAR=?  and a.CASHBOOK_MONTH=? and a.ACCOUNT_HEAD_CODE=? and a.RECEIPT_NO not in (select RECEIPT_NO from FAS_ADJUST_MEMO_MST where RECEIPTNO_YEAR=? and RECEIPTNO_MONTH=? and RECEIPT_NO is not null AND MEMO_STATUS = 'L' AND RECEIPT_SLNO=a.SL_NO union (select RECEIPT_NO from FAS_ADJUST_MEMO_TRN where  RECEIPTNO_YEAR=? and RECEIPTNO_MONTH=? and RECEIPT_NO is not null  )) and RECEIPT_DATE=? ";
	            	 
	            		  /*@NK ON 15JUL20*/
	            	/*	  qry="SELECT a.RECEIPT_NO ," + 
	            		  		"  A.SL_NO " + 
	            		  		"FROM FAS_RECEIPT_TRANSACTION a, " + 
	            		  		"  FAS_RECEIPT_MASTER B " + 
	            		  		" WHERE A.ACCOUNTING_UNIT_ID=B.ACCOUNTING_UNIT_ID " + 
	            		  		" AND A.ACCOUNTING_FOR_OFFICE_ID=B.ACCOUNTING_FOR_OFFICE_ID " + 
	            		  		" AND A.CASHBOOK_YEAR=B.CASHBOOK_YEAR " + 
	            		  		" AND A.CASHBOOK_MONTH=B.CASHBOOK_MONTH " + 
	            		  		" and A.RECEIPT_NO=B.RECEIPT_NO " + 
	            		  		" and A.ACCOUNTING_UNIT_ID    =? " + 
	            		  		" AND A.ACCOUNTING_FOR_OFFICE_ID=? " + 
	            		  		" AND A.CASHBOOK_YEAR           =? " + 
	            		  		" AND A.CASHBOOK_MONTH          =? " + 
	            		  		" AND a.ACCOUNT_HEAD_CODE       =? " + 
	            		  		" AND a.RECEIPT_NO NOT         IN " + 
	            		  		"  (SELECT RECEIPT_NO " + 
	            		  		"  FROM FAS_ADJUST_MEMO_MST " + 
	            		  		"  WHERE RECEIPTNO_YEAR=? " + 
	            		  		"  AND RECEIPTNO_MONTH =?  " + 
	            		  		"  AND RECEIPT_NO     IS NOT NULL " + 
	            		  		"  AND MEMO_STATUS     = 'L' " + 
	            		  		"  AND RECEIPT_SLNO    =a.SL_NO " + 
	            		  		"  UNION " + 
	            		  		"    (SELECT RECEIPT_NO " + 
	            		  		"    FROM FAS_ADJUST_MEMO_TRN " + 
	            		  		"    WHERE RECEIPTNO_YEAR=? " + 
	            		  		"    AND RECEIPTNO_MONTH =? " + 
	            		  		"    AND RECEIPT_NO     IS NOT NULL " + 
	            		  		"    ) " + 
	            		  		"  ) " + 
	            		  		"  and B.RECEIPT_DATE=? " ;*/
	            		  /*@NK ON 15JUL20*/
	            		  
	            		  qry	 = ""
	            				  + "SELECT a.RECEIPT_NO , "
	            				  + "  A.SL_NO "
	            				  + "FROM FAS_RECEIPT_TRANSACTION a, "
	            				  + "  FAS_RECEIPT_MASTER B "
	            				  + "WHERE A.ACCOUNTING_UNIT_ID    =B.ACCOUNTING_UNIT_ID "
	            				  + "AND A.ACCOUNTING_FOR_OFFICE_ID=B.ACCOUNTING_FOR_OFFICE_ID "
	            				  + "AND A.CASHBOOK_YEAR           =B.CASHBOOK_YEAR "
	            				  + "AND A.CASHBOOK_MONTH          =B.CASHBOOK_MONTH "
	            				  + "and a.receipt_no              =b.receipt_no "
	            				  + "and a.accounting_unit_id      =? "
	            				  + "and a.accounting_for_office_id=? "
	            				  + "and a.cashbook_year           =? "
	            				  + "and a.cashbook_month          =? "
	            				  + "AND a.ACCOUNT_HEAD_CODE       =? "
	            				  + "and a.receipt_no not         in "
	            				  + "  (select adjm.receipt_no "
	            				  + "  from fas_adjust_memo_mst adjm, "
	            				  + "  fas_adjust_memo_trn adjt "
	            				  + "  where adjm.receiptno_year=? "
	            				  + "  and adjm.receiptno_month =? "
	            				  + "  and adjm.receiptno_year=b.cashbook_year "
	            				  + "  and adjm.receiptno_month =b.cashbook_month "
	            				  + "  and adjm.voucher_no=adjt.voucher_no "
	            				  + "  and adjm.receipt_no     is not null "
	            				  + "  and adjm.memo_status     = 'L' "
	            				  + "  and adjm.receipt_slno    =a.sl_no "
	            				  + "   and adjm.receipt_date    =b.receipt_date "
	            				  + "   and adjm.receipt_no      =b.receipt_no "
	            				  + "  and adjt.cb_ref_type='R' "
	            				  + "    ) "
	            				  + "and b.receipt_date=?";

	            		  
	            	  }
	            	  else if(adj_Type.equalsIgnoreCase("Journal"))
	            	  {
	            		  
	            		  //changed on 11-01-2018 by Kanaga (geetharani mam)
	            		  //	            	  qry="  select distinct voucher_no as RECEIPT_NO from FAS_JOURNAL_TRANSACTION where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=?  and CASHBOOK_MONTH=? and ACCOUNT_HEAD_CODE=? and voucher_no not in (select RECEIPT_NO from FAS_ADJUST_MEMO_MST where RECEIPTNO_YEAR=? and RECEIPTNO_MONTH=? and RECEIPT_NO is not null  union (select RECEIPT_NO from FAS_ADJUST_MEMO_TRN where  RECEIPTNO_YEAR=? and RECEIPTNO_MONTH=? and RECEIPT_NO is not null )) ";
		              qry="  select distinct voucher_no as RECEIPT_NO,SL_NO from FAS_JOURNAL_TRANSACTION where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=?  and CASHBOOK_MONTH=? and ACCOUNT_HEAD_CODE=? and voucher_no not in (select RECEIPT_NO from FAS_ADJUST_MEMO_MST where RECEIPTNO_YEAR=? and RECEIPTNO_MONTH=? and RECEIPT_NO is not null AND MEMO_STATUS = 'L' union (select RECEIPT_NO from FAS_ADJUST_MEMO_TRN where  RECEIPTNO_YEAR=? and RECEIPTNO_MONTH=? and RECEIPT_NO is not null )) ";

	            	  }
	            	  else if(adj_Type.equalsIgnoreCase("Payment"))
		            		//changed on 11-01-2018 by Kanaga (geetharani mam)
		            		  //	            	  qry="  select distinct voucher_no as RECEIPT_NO from FAS_JOURNAL_TRANSACTION where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=?  and CASHBOOK_MONTH=? and ACCOUNT_HEAD_CODE=? and voucher_no not in (select RECEIPT_NO from FAS_ADJUST_MEMO_MST where RECEIPTNO_YEAR=? and RECEIPTNO_MONTH=? and RECEIPT_NO is not null  union (select RECEIPT_NO from FAS_ADJUST_MEMO_TRN where  RECEIPTNO_YEAR=? and RECEIPTNO_MONTH=? and RECEIPT_NO is not null )) ";
	            	  {
	            		  
//	            		  qry="  select  a.voucher_no as RECEIPT_NO,a.SL_NO from FAS_PAYMENT_TRANSACTION a where a.ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and a.ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and a.CASHBOOK_YEAR="+cashbookyear+"  and a.CASHBOOK_MONTH="+cashbookmonth+" and a.ACCOUNT_HEAD_CODE="+txtAcc_HeadCode+" and a.voucher_no not in (select RECEIPT_NO from FAS_ADJUST_MEMO_MST where RECEIPTNO_YEAR="+cashbookyear+" and RECEIPTNO_MONTH="+cashbookmonth+" and RECEIPT_NO is not null AND MEMO_STATUS = 'L' AND RECEIPT_SLNO=a.SL_NO union (select RECEIPT_NO from FAS_ADJUST_MEMO_TRN where  RECEIPTNO_YEAR="+cashbookyear+" and RECEIPTNO_MONTH="+cashbookmonth+" and RECEIPT_NO is not null )) ";
	            		  
	            	  	
	            	  		qry="SELECT DISTINCT A.VOUCHER_NO AS RECEIPT_NO, " + 
	            	  				"  A.SL_NO " + 
	            	  				" FROM FAS_PAYMENT_TRANSACTION a, " + 
	            	  				"  FAS_PAYMENT_MASTER B " + 
	            	  				" WHERE A.ACCOUNTING_UNIT_ID    ="+ cmbAcc_UnitCode +
	            	  				" AND A.ACCOUNTING_FOR_OFFICE_ID="+ cmbOffice_code + 
	            	  				" AND A.CASHBOOK_YEAR           ="+ cashbookyear +
	            	  				" AND A.CASHBOOK_MONTH          ="+ cashbookmonth +
	            	  				" AND a.ACCOUNT_HEAD_CODE       ="+ txtAcc_HeadCode +
	            	  				" AND A.VOUCHER_NO NOT         IN " + 
	            	  				"  (SELECT m.RECEIPT_NO" + 
	            	  				"  FROM FAS_ADJUST_MEMO_MST m," + 
	            	  				"  FAS_ADJUST_MEMO_TRN T" + 
	            	  				"  WHERE M.ACCOUNTING_UNIT_ID=T.ACCOUNTING_UNIT_ID" + 
	            	  				"  AND M.ACCOUNTING_FOR_OFFICE_ID=T.ACCOUNTING_FOR_OFFICE_ID" + 
	            	  				"  AND M.CASHBOOK_YEAR=T.CASHBOOK_YEAR" + 
	            	  				"  AND M.CASHBOOK_MONTH=T.CASHBOOK_MONTH" + 
	            	  				"  and M.VOUCHER_NO=T.VOUCHER_NO" + 
	            	  				"  AND M.RECEIPTNO_YEAR=" + cashbookyear +
	            	  				"  AND m.RECEIPTNO_MONTH =" + cashbookmonth + 
	            	  				"  AND m.RECEIPT_NO     IS NOT NULL" + 
	            	  				"  AND M.MEMO_STATUS     = 'L'" + 
	            	  				"  AND m.RECEIPT_DATE    =B.PAYMENT_DATE" + 
	            	  				"  AND M.RECEIPT_NO      =B.VOUCHER_NO" + 
	            	  				"  AND M.RECEIPT_SLNO    =A.SL_NO" + 
	            	  				"  and T.CB_REF_TYPE='P'" + 
	            	  				"  )" + 
	            	  				" AND A.ACCOUNTING_UNIT_ID      =B.ACCOUNTING_UNIT_ID" + 
	            	  				" AND A.ACCOUNTING_FOR_OFFICE_ID=B.ACCOUNTING_FOR_OFFICE_ID" + 
	            	  				" AND A.CASHBOOK_YEAR           =B.CASHBOOK_YEAR" + 
	            	  				" AND A.CASHBOOK_MONTH          =B.CASHBOOK_MONTH" + 
	            	  				" AND A.VOUCHER_NO              =B.VOUCHER_NO " +
	            	                " and B.PAYMENT_DATE            =?" ;
	            	  
	            	  }
	            		  
//	            		  qry="SELECT DISTINCT A.VOUCHER_NO AS RECEIPT_NO," + 
//	            		  		"  A.SL_NO " + 	            		  		
//	            		  		" FROM FAS_PAYMENT_TRANSACTION a,FAS_PAYMENT_MASTER b " + 
//	            		  		" WHERE A.ACCOUNTING_UNIT_ID    =? " + 
//	            		  		" AND A.ACCOUNTING_FOR_OFFICE_ID=? " + 
//	            		  		" AND A.CASHBOOK_YEAR           =? " + 
//	            		  		" AND A.CASHBOOK_MONTH          =? " + 
//	            		  		" AND a.ACCOUNT_HEAD_CODE       =? " + 
//	            		  		" AND a.voucher_no NOT         IN " + 
//	            		  		"  (SELECT RECEIPT_NO " + 
//	            		  		"  FROM FAS_ADJUST_MEMO_MST " + 
//	            		  		"  WHERE RECEIPTNO_YEAR=? " + 
//	            		  		"  AND RECEIPTNO_MONTH =? " + 
//	            		  		"  AND RECEIPT_NO     IS NOT NULL" + 
//	            		  		"  AND MEMO_STATUS     = 'L' " + 
//	            		  		"  AND RECEIPT_DATE=B.PAYMENT_DATE " + 
//	            		  		"  and RECEIPT_NO=B.VOUCHER_NO " + 
//	            		  		"  AND RECEIPT_SLNO    =A.SL_NO  " + 
//	            		  		"  UNION" + 
//	            		  		"    (SELECT RECEIPT_NO " + 
//	            		  		"    FROM FAS_ADJUST_MEMO_TRN " + 
//	            		  		"    WHERE RECEIPTNO_YEAR=? " + 
//	            		  		"    AND RECEIPTNO_MONTH =? " + 
//	            		  		"    AND RECEIPT_NO     IS NOT NULL " + 
//	            		  		"    AND RECEIPT_DATE=B.PAYMENT_DATE " + 
//	            		  		"    AND RECEIPT_NO=B.VOUCHER_NO " + 
//	            		  		"    ) " + 
//	            		  		"    ) " + 
//	            		  		"  AND A.ACCOUNTING_UNIT_ID=B.ACCOUNTING_UNIT_ID " + 
//	            		  		"  AND A.ACCOUNTING_FOR_OFFICE_ID=B.ACCOUNTING_FOR_OFFICE_ID " + 
//	            		  		"  AND A.CASHBOOK_YEAR=B.CASHBOOK_YEAR " + 
//	            		  		"  AND A.CASHBOOK_MONTH=B.CASHBOOK_MONTH " + 
//	            		  		"  and A.VOUCHER_NO=B.VOUCHER_NO ";
	            	  System.out.println("Sql Query--->"+qry);
	            	  
	            	  ps = con.prepareStatement(qry);
	            	  
	            	  if(adj_Type.equalsIgnoreCase("Payment"))
	            	  {
	            	  ps.setDate(1,txtCrea_date);
	            	  }
	            	  if(!adj_Type.equalsIgnoreCase("Payment"))
	            	  {
	            	  
	            	  ps.setInt(1, cmbAcc_UnitCode);
	                  ps.setInt(2, cmbOffice_code);
	                  ps.setInt(3, cashbookyear);
	                  ps.setInt(4, cashbookmonth);
	                  ps.setInt(5, txtAcc_HeadCode);
	                  ps.setInt(6, cashbookyear);
	                  ps.setInt(7, cashbookmonth);
	                  ps.setDate(8,txtCrea_date);
	            	  }
//	                  ps.setInt(8, cashbookyear);
//	                  ps.setInt(9, cashbookmonth);
	                  result = ps.executeQuery();                                     
	                              while(result.next()) 
	                              {
	                                  xml=xml+"<receiptno>"+result.getInt("RECEIPT_NO")+"</receiptno>";
	                                  xml=xml+"<sl_no>"+result.getInt("SL_NO")+"</sl_no>";
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
	              xml="<response><command>load_Ref_No</command>";
	              try 
	              {
	              	int sl_no=0;
	            	 txtReceipt_No=Integer.parseInt(request.getParameter("txtReceipt_No"));
	            	 sl_no=Integer.parseInt(request.getParameter("sl_no"));
	            	 System.out.println("sl_no"+sl_no);
	            	 System.out.println("txtReceipt_No"+txtReceipt_No);
	                 String adj_Type=request.getParameter("adj_Type");
	                 xml=xml+"<adj_Type>"+adj_Type+"</adj_Type>";   
			            String qry="";	 
			            if(adj_Type.equalsIgnoreCase("Receipt"))
			            {
			            	  qry="select a.RECEIPT_NO as RECEIPT_NO,to_char(a.RECEIPT_DATE,'dd/mm/yyyy')as RECEIPT_DATE,trim(to_char(b.AMOUNT,'99999999999999.99')) as TOTAL_AMOUNT,b.account_head_code, a.RECEIVED_FROM  as PARTICULARS,  b.CR_DR_INDICATOR,  b.amount,b.sub_ledger_code as code,(select ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=b.sub_ledger_code) as code_name from FAS_RECEIPT_MASTER a,fas_receipt_transaction b where a.ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and a.ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and a.CASHBOOK_YEAR="+cashbookyear+" and a.CASHBOOK_MONTH="+cashbookmonth+" and a.RECEIPT_NO="+txtReceipt_No+" and b.ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and b.ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and b.CASHBOOK_YEAR="+cashbookyear+" and b.CASHBOOK_MONTH="+cashbookmonth+" and b.RECEIPT_NO="+txtReceipt_No+" and b.account_head_code=610101 and b.SL_NO="+sl_no;
			            }
			            else if(adj_Type.equalsIgnoreCase("Journal"))
			            {
			            	 qry="select a.voucher_NO as RECEIPT_NO,to_char(a.voucher_DATE,'dd/mm/yyyy')as RECEIPT_DATE,"
			            	 		//+ "trim(to_char(sum(b.AMOUNT),'99999999999999.99')) as TOTAL_AMOUNT ,"
			            	 		+"  (SELECT trim(TO_CHAR(SUM(AMOUNT),'99999999999999.99')) " +
			            	 		"  FROM fas_journal_transaction " +
			            	 		"  WHERE ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID= " +cmbOffice_code+
			            	 		"  AND CASHBOOK_YEAR             = " +cashbookyear+
			            	 		"  AND CASHBOOK_MONTH            = " +cashbookmonth+
			            	 		"  AND voucher_NO                = " +txtReceipt_No+
			            	 		"  AND account_head_code         =610101 " +
			            	 		"  AND SL_NO=" + sl_no +  
			            	 		"  )                 AS TOTAL_AMOUNT ,b.account_head_code,  PARTICULARS,  CR_DR_INDICATOR,  b.amount, " 
			            	 		+ "b.sub_ledger_code as code,(select ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=b.sub_ledger_code) as code_name from FAS_journal_MASTER a,fas_journal_transaction b where a.ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and a.ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and a.CASHBOOK_YEAR="+cashbookyear+" and a.CASHBOOK_MONTH="+cashbookmonth+" and a.voucher_NO="+txtReceipt_No+" and b.ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and b.ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and b.CASHBOOK_YEAR="+cashbookyear+" and b.CASHBOOK_MONTH="+cashbookmonth+" and b.voucher_NO="+txtReceipt_No+" and b.account_head_code=610101  and SL_NO="+sl_no;
			            }
			            else if(adj_Type.equalsIgnoreCase("Payment"))
			            {
//			            	 qry="select a.voucher_NO as RECEIPT_NO,to_char(a.PAYMENT_DATE,'dd/mm/yyyy')as RECEIPT_DATE,"
//			            	 		+"  (SELECT trim(TO_CHAR(SUM(AMOUNT),'99999999999999.99')) " +
//			            	 		"  FROM FAS_PAYMENT_TRANSACTION " +
//			            	 		"  WHERE ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID= " +cmbOffice_code+
//			            	 		"  AND CASHBOOK_YEAR             = " +cashbookyear+
//			            	 		"  AND CASHBOOK_MONTH            = " +cashbookmonth+
//			            	 		"  AND voucher_NO                = " +txtReceipt_No+
//			            	 		"  AND account_head_code         =900201 " +
//			            	 		"  AND SL_NO=" + sl_no +  
//			            	 		"  )                 AS TOTAL_AMOUNT ,b.account_head_code,  b.PARTICULARS,  b.CR_DR_INDICATOR,  b.amount, " 
//			            	 		+ "b.sub_ledger_code as code,B.SUB_LEDGER_TYPE_CODE,(select SL_CODENAME from SL_TYPE_CODE_NAME_VIEW_M where SL_CODE=b.sub_ledger_code and SL_TYPE=B.SUB_LEDGER_TYPE_CODE) as code_name from FAS_PAYMENT_MASTER a,FAS_PAYMENT_TRANSACTION b where a.ACCOUNTING_UNIT_ID=? and a.ACCOUNTING_FOR_OFFICE_ID=? and a.CASHBOOK_YEAR=? and a.CASHBOOK_MONTH=? and a.voucher_NO=? and b.ACCOUNTING_UNIT_ID=? and b.ACCOUNTING_FOR_OFFICE_ID=? and b.CASHBOOK_YEAR=? and b.CASHBOOK_MONTH=? and b.voucher_NO=? and b.account_head_code=900201  and b.SL_NO="+sl_no;			            
//			            	changed by nk on 10oct2020
			            	/* qry="select a.voucher_NO as RECEIPT_NO,to_char(a.PAYMENT_DATE,'dd/mm/yyyy')as RECEIPT_DATE,"
				            	 		+"  (SELECT trim(TO_CHAR(SUM(AMOUNT),'99999999999999.99')) " +
				            	 		"  FROM FAS_PAYMENT_TRANSACTION " +
				            	 		"  WHERE ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID= " +cmbOffice_code+
				            	 		"  AND CASHBOOK_YEAR             = " +cashbookyear+
				            	 		"  AND CASHBOOK_MONTH            = " +cashbookmonth+
				            	 		"  AND voucher_NO                = " +txtReceipt_No+
				            	 		"  AND account_head_code         =900201 " +
				            	 		"  AND SL_NO=" + sl_no +  
				            	 		"  )                 AS TOTAL_AMOUNT ,b.account_head_code,  b.PARTICULARS,  b.CR_DR_INDICATOR,  b.amount, " 
				            	 		+ " b.sub_ledger_code,(SELECT U.ACCOUNTING_UNIT_ID FROM FAS_MST_ACCT_UNITS u WHERE U.ACCOUNTING_UNIT_OFFICE_ID=B.SUB_LEDGER_CODE ) AS code,(SELECT U.ACCOUNTING_UNIT_NAME FROM FAS_MST_ACCT_UNITS u WHERE U.ACCOUNTING_UNIT_ID=(SELECT U.ACCOUNTING_UNIT_ID FROM FAS_MST_ACCT_UNITS U WHERE U.ACCOUNTING_UNIT_OFFICE_ID=B.SUB_LEDGER_CODE) ) AS code_name "
				            	 		+ " from FAS_PAYMENT_MASTER a,FAS_PAYMENT_TRANSACTION b where a.ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and a.ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and a.CASHBOOK_YEAR="+cashbookyear+" and a.CASHBOOK_MONTH="+cashbookmonth+" and a.voucher_NO="+txtReceipt_No+" and b.ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and b.ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and b.CASHBOOK_YEAR="+cashbookyear+" and b.CASHBOOK_MONTH="+cashbookmonth+" and b.voucher_NO="+txtReceipt_No+" and b.account_head_code=900201  and b.SL_NO="+sl_no;*/
//			            	changed by nk on 10oct2020
			            	 qry="select a.voucher_NO as RECEIPT_NO,to_char(a.PAYMENT_DATE,'dd/mm/yyyy')as RECEIPT_DATE,"
				            	 		+"  (SELECT trim(TO_CHAR(SUM(AMOUNT),'99999999999999.99')) " +
				            	 		"  FROM FAS_PAYMENT_TRANSACTION " +
				            	 		"  WHERE ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID= " +cmbOffice_code+
				            	 		"  AND CASHBOOK_YEAR             = " +cashbookyear+
				            	 		"  AND CASHBOOK_MONTH            = " +cashbookmonth+
				            	 		"  AND voucher_NO                = " +txtReceipt_No+
				            	 		"  AND account_head_code         =900201 " +
				            	 		"  AND SL_NO=" + sl_no +  
				            	 		"  )                 AS TOTAL_AMOUNT ,b.account_head_code,  b.PARTICULARS,  b.CR_DR_INDICATOR,  b.amount, " 
				            	 		+ " b.sub_ledger_code,case  when   b.sub_ledger_code=5000 then   5  else (SELECT U.ACCOUNTING_UNIT_ID FROM FAS_MST_ACCT_UNITS u WHERE U.ACCOUNTING_UNIT_OFFICE_ID=B.SUB_LEDGER_CODE )end AS code,case  when   b.sub_ledger_code=5000 then   'Head Office- Banking Section' else (SELECT U.ACCOUNTING_UNIT_NAME FROM FAS_MST_ACCT_UNITS u WHERE U.ACCOUNTING_UNIT_ID=(SELECT U.ACCOUNTING_UNIT_ID FROM FAS_MST_ACCT_UNITS U WHERE U.ACCOUNTING_UNIT_OFFICE_ID=B.SUB_LEDGER_CODE) ) end AS code_name "
				            	 		+ " from FAS_PAYMENT_MASTER a,FAS_PAYMENT_TRANSACTION b where a.ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and a.ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and a.CASHBOOK_YEAR="+cashbookyear+" and a.CASHBOOK_MONTH="+cashbookmonth+" and a.voucher_NO="+txtReceipt_No+" and b.ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and b.ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and b.CASHBOOK_YEAR="+cashbookyear+" and b.CASHBOOK_MONTH="+cashbookmonth+" and b.voucher_NO="+txtReceipt_No+" and b.account_head_code=900201  and b.SL_NO="+sl_no;
			            
			            
			            
			            }
			          
//	                 ps.setInt(1, cmbAcc_UnitCode);
//                     ps.setInt(2, cmbOffice_code);
//                     ps.setInt(3, cashbookyear);
//                     ps.setInt(4, cashbookmonth);
//	                 ps.setInt(5, txtReceipt_No);
//	                 ps.setInt(6, cmbAcc_UnitCode);
//                     ps.setInt(7, cmbOffice_code);
//                     ps.setInt(8, cashbookyear);
//                     ps.setInt(9, cashbookmonth);
//	                 ps.setInt(10, txtReceipt_No);
			         
			         ps=con.prepareStatement(qry);
	                 result=ps.executeQuery();
	                 
	                 
	                 System.out.println("result"+result);
	                 System.out.println("qry==>"+qry);
	                 
	                 
	                 
	                 while(result.next()) 
	    	                
	                 {
	                	System.out.println("inside while loop!!!!!!!");
	                      xml=xml+"<HoNo>"+result.getInt("RECEIPT_NO")+"</HoNo>";    
	                      xml=xml+"<HoDate>"+result.getString("RECEIPT_DATE")+"</HoDate>";
	                      xml=xml+"<totalAmt>"+result.getString("TOTAL_AMOUNT")+"</totalAmt>";
	                      xml=xml+"<account_head_code>"+result.getString("account_head_code")+"</account_head_code>";
	                      xml=xml+"<PARTICULARS><![CDATA["+result.getString("PARTICULARS").trim()+"]]></PARTICULARS>";
	                      xml=xml+"<amount>"+result.getString("amount").trim()+"</amount>";
	                      xml=xml+"<crDR>"+result.getString("CR_DR_INDICATOR").trim()+"</crDR>";
	                      xml=xml+"<codename>"+result.getString("code_name").trim()+"</codename>";
	                      xml=xml+"<code>"+result.getInt("code")+"</code>";
	                      count++;
	                 }
	                 
	                 
	                 
//	                if(adj_Type.equalsIgnoreCase("Receipt"))
//	                {
//	                	while(result.next()) 
//	                
//	                 {
//	                      xml=xml+"<HoNo>"+result.getInt("RECEIPT_NO")+"</HoNo>";    
//	                      xml=xml+"<HoDate>"+result.getString("RECEIPT_DATE")+"</HoDate>";
//	                      xml=xml+"<totalAmt>"+result.getString("TOTAL_AMOUNT")+"</totalAmt>";
//	                      xml=xml+"<codename>"+result.getString("code_name")+"</codename>";
//	                      xml=xml+"<code>"+result.getInt("code")+"</code>";
//	                      count++;
//	                 }
//	                }
//	                if(adj_Type.equalsIgnoreCase("Payment"))
//	                {
//	                	while(result.next()) 
//	    	                
//		                 {
//		                	
//		                      xml=xml+"<HoNo>"+result.getInt("RECEIPT_NO")+"</HoNo>";    
//		                      xml=xml+"<HoDate>"+result.getString("RECEIPT_DATE")+"</HoDate>";
//		                      xml=xml+"<totalAmt>"+result.getString("TOTAL_AMOUNT")+"</totalAmt>";
//		                      xml=xml+"<codename>"+result.getString("code_name")+"</codename>";
//		                      xml=xml+"<code>"+result.getInt("code")+"</code>";
//		                      count++;
//		                 }
//	                }
	                
	               
	                	
	                
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
		            int office_id=0,count=0,cashbookyear=0,cashbookmonth=0,txtReceipt_No=0,ho_ref_no=0,receiptyear=0,receiptmonth=0,Receipt_SlNo=0;
		            double txtTotalAmt=0;
		         //   Date txtCrea_date=null;
		            String particulars="",memodate="",authority="",ho_ref_date="";
		             String autName="",autAddress="";
		                                    // changes here
		            String update_user=(String)session.getAttribute("UserId");
		            long l=System.currentTimeMillis();
		            Timestamp ts=new Timestamp(l);	                
		            int insmaster=0;
		            System.out.println("TESTING >>>>>>>>>>>>>> "+request.getParameter("rad_adj"));
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
		            
		            
		            try{receiptyear=Integer.parseInt(request.getParameter("txtCB_Year"));}
		            catch(Exception e){System.out.println("exception"+e );}
		            System.out.println("receiptyear "+receiptyear);
		            
		            try{receiptmonth=Integer.parseInt(request.getParameter("txtCB_Month"));}
		            catch(Exception e){System.out.println("exception"+e );}
		            System.out.println("receiptmonth "+receiptmonth);
		            
		            
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
		            try
		            {
		            	Statement st=con.createStatement();
		            	ResultSet rs=st.executeQuery("SELECT VOUCHER_NO FROM FAS_ADJUST_MEMO_MST GROUP BY VOUCHER_NO HAVING VOUCHER_NO =(select max(VOUCHER_NO) from FAS_ADJUST_MEMO_MST where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and CASHBOOK_YEAR="+txtCash_year+" and CASHBOOK_MONTH="+txtCash_Month_hid+")");
		                if(rs.next()) 
		                {
		                	memo_advice_No = rs.getInt(1);                		                   
		                }
		                memo_advice_No=memo_advice_No+1;
		            }
		            catch(Exception e){System.out.println("exception"+e );}
		            System.out.println("memo_advice_No "+memo_advice_No);
		            		            
		            particulars=request.getParameter("particulars");
		            String[] receipt_no=request.getParameter("txtReceipt_No").split("-");
		            
		            try{txtReceipt_No=Integer.parseInt(receipt_no[0]);}
		            catch(NumberFormatException e){System.out.println("exception"+e );}
		          	System.out.println("txtReceipt_No "+txtReceipt_No); 
		          	
		          	try{Receipt_SlNo=Integer.parseInt(receipt_no[1]);}
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
		            String[] letter_no=null;
		            String[] letter_date = null;
		            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~        
		            String letterNo="",   letterDate="";   
		            try 
		            {   
		                     con.clearWarnings();
		                     con.setAutoCommit(false);
		                     ps=con.prepareStatement("insert into FAS_ADJUST_MEMO_MST(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,VOUCHER_DATE,AUTHORITY_NAME,PARTICULARS,RECEIPT_NO,HO_REF_NO,HO_REF_DATE,TOTAL_AMOUNT,UPDATED_BY_USER_ID,UPDATED_DATE,RECEIPT_DATE,ADVICE_TYPE,AUTHORITY_ADDRESS,MEMO_STATUS,RECEIPTNO_YEAR,RECEIPTNO_MONTH,VERIFIED_STATUS,RECEIPT_SLNO) values (?,?,?,?,?,to_date(?,'dd/mm/yyyy'),?,?,?,?,to_date(?,'dd/mm/yyyy'),?,?,?,to_date(?,'dd/mm/yyyy'),?,?,?,?,?,?,?)");
		                    
		                     ps.setInt(1,cmbAcc_UnitCode);
		                     ps.setInt(2,cmbOffice_code);
		                     
		                     ps.setInt(3,txtCash_year);
		                     ps.setInt(4,txtCash_Month_hid);
		                     
		                     ps.setInt(5,memo_advice_No);
		                     ps.setString(6,memodate);
		                     ps.setString(7,autName);
		                     ps.setString(8,particulars);
		                     ps.setInt(9,txtReceipt_No);
		                     ps.setInt(10,ho_ref_no);
		                     ps.setString(11,ho_ref_date);
		                     		                     
		                     ps.setDouble(12,txtTotalAmt);
		                   
		                     ps.setString(13,update_user);
		                    
		                     ps.setTimestamp(14,ts);
		                     
		                     ps.setString(15,ho_ref_date);
		                     
		                     ps.setInt(16,2);
		       
		                     ps.setString(17,autAddress);
		                   
		                     ps.setString(18,"L");
		                     ps.setInt(19,receiptyear);
		                     ps.setInt(20,receiptmonth);
		                     ps.setString(21,"N");
		                     ps.setInt(22,Receipt_SlNo);
		                     insmaster=ps.executeUpdate();
		                     System.out.println("insmaster"+insmaster);
		                     if(insmaster==0)
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
		                         String Grid_Office[]=request.getParameterValues("OfficeCode");
		                         String Grid_sl_amt[]=request.getParameterValues("sl_amt");
		                         String Grid_particular[]=request.getParameterValues("remarkss");
		                      
		                        	
		                       if(request.getParameter("rad_adj").equalsIgnoreCase("Receipt"))
		                    	   {
		                    	    letter_no=request.getParameterValues("letterno");
		                    	    System.out.println();
		                        letter_date=request.getParameterValues("letterdate");
		                       }
		                       else if(request.getParameter("rad_adj").equalsIgnoreCase("Payment"))
	                    	   {
		                    	   letterNo=request.getParameter("letterNo");
		                    	   letterDate=request.getParameter("letterDate");
	                    	   }
		                       else{
		                    	   letterNo=request.getParameter("letterNo");
		                    	   letterDate=request.getParameter("letterDate");
		                       }
		                       
		                       
		                      
		                         System.out.println("2 nd table insert");
		                        
		                         String txtParticular="",letterdate="";
			                     Date sl_ref_date=null;
			                     int SL_NO=0,cmbSL_type=0,cmbSL_Code=0,ref_num=0,letter_code=0,office_Code=0;
			                     double txtsub_Amount=0;
		                         for(int k=0;k<Grid_H_code.length;k++) 
		                         {	                                                             	                        	  
		                        	   cmbSL_type=0;cmbSL_Code=0;ref_num=0;
		                        	   txtAcc_HeadCode=0;	
		                        	   txtsub_Amount=0;
		                        		  try
		  	  		     	            {
		  	  		     	            	Statement st=con.createStatement();
		  	  		     	            //	ResultSet rs=st.executeQuery("SELECT VOUCHER_NO FROM FAS_ADJUSTMENT_MASTER GROUP BY VOUCHER_NO HAVING VOUCHER_NO =(select max(VOUCHER_NO) from FAS_ADJUSTMENT_MASTER where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and CASHBOOK_YEAR="+txtCash_year+" and CASHBOOK_MONTH="+txtCash_Month_hid+")");
		  	  		     	            	ResultSet rs=st.executeQuery("select max(SL_NO) from FAS_ADJUST_MEMO_TRN where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and CASHBOOK_YEAR="+txtCash_year+" and CASHBOOK_MONTH="+txtCash_Month_hid);
		  	  		     	            	if(rs.next()) 
		  	  		     	                {
		  	  		     	            		System.out.println("inside the if loop");
		  	  		     	            		SL_NO = rs.getInt(1);                		                   
		  	  		     	                }
		  	  		     	            	SL_NO=SL_NO+1;
		  	  		     	            }
		  	  		     	            catch(Exception e){System.out.println("exception"+e );}
		  	  		     	            System.out.println("txtAdvice_No "+SL_NO);
		                        	   String sql="insert into FAS_ADJUST_MEMO_TRN(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,ACCOUNT_HEAD_CODE,CR_DR_TYPE,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,AMOUNT,REMARKS,LETTER_NO,LETTER_DATE,UPDATED_BY_USER_ID,UPDATED_DATE,FOR_ACCOUNTING_UNIT_ID,SL_NO,VERIFIED_STATUS,CB_REF_TYPE) values(?,?,?,?,?,?,?,?,?,?,?,?,to_date(?,'dd/mm/yyyy'),?,?,?,?,?,?)" ;	                         
				                       System.out.println("sql>>>"+sql);
		                        	   ps=con.prepareStatement(sql);
			                           try
			                           {
			                        	     
					                           ps.setInt(1,cmbAcc_UnitCode);	
					                           System.out.println("cmbAcc_UnitCode:  "+cmbAcc_UnitCode);				                          
				                               
					                           ps.setInt(2,cmbOffice_code);	   
				                               System.out.println("cmbOffice_code:  "+cmbOffice_code);				                          
				                              
				                               ps.setInt(3,txtCash_year);
				  		                       System.out.println("cashbookyear::::"+txtCash_year);
				  		                       ps.setInt(4,txtCash_Month_hid);
				  		                       System.out.println("cashbookmonth::::"+txtCash_Month_hid);
				  		                    
				  		                       ps.setInt(5,memo_advice_No);
				  		                       System.out.println("memo_advice_No::::"+memo_advice_No);
				                              
				                               txtAcc_HeadCode=Integer.parseInt(Grid_H_code[k]);
				                               ps.setInt(6,txtAcc_HeadCode);
				                               System.out.println("txtAcc_HeadCode:  "+txtAcc_HeadCode);		
				                               
				                               System.out.println("Grid_CR_DR_type["+k+"]: "+Grid_CR_DR_type[k]);
				                               String rad_sub_CR_DR=Grid_CR_DR_type[k];	                              
				                               ps.setString(7,rad_sub_CR_DR);
				                               
				                               System.out.println("Grid_SL_type["+k+"]: "+Grid_SL_type[k]);	                               
				                               try{cmbSL_type=Integer.parseInt(Grid_SL_type[k]);}
				               	               catch(NumberFormatException e){System.out.println("exception"+e );}
				                               ps.setInt(8,cmbSL_type);	
				                               
				                               System.out.println("Grid_SL_code["+k+"]: "+Grid_SL_code[k]);	                               
				                               try{cmbSL_Code=Integer.parseInt(Grid_SL_code[k]);}
				               	               catch(NumberFormatException e){System.out.println("exception"+e );}
				               	               ps.setInt(9,cmbSL_Code);
				                               
				               	               
				                               System.out.println("Grid_sl_amt["+k+"]: "+Grid_sl_amt[k]);	                               
				                               try{txtsub_Amount=Double.parseDouble(Grid_sl_amt[k]);}
				               	               catch(NumberFormatException e){System.out.println("exception"+e );}
				               	               ps.setDouble(10,txtsub_Amount);
				                                                          	                              
				                               txtParticular=Grid_particular[k];
				                               ps.setString(11,txtParticular);	    
				                               
				                               if(request.getParameter("rad_adj").equalsIgnoreCase("Receipt"))                                  
				                               {
											//letter_code=Integer.parseInt(letter_no[k]);
				               	            //letter_code changed the type from number to varchar2.. SS 
											letter_code=Integer.parseInt(letter_no[k]);
				               	               ps.setInt(12,letter_code);
				                               
				               	               letterdate=letter_date[k];
				                               ps.setString(13,letterdate);	
				                               }
				                               else if(request.getParameter("rad_adj").equalsIgnoreCase("Payment"))                                  
				                               {
				                            	   ps.setInt(12,Integer.parseInt(letterNo));  
				                            	   ps.setString(13,letterDate);	
				                               }
				                               
				                               else{
				                            	   ps.setInt(12,Integer.parseInt(letterNo));  
				                            	   ps.setString(13,letterDate);	
				                               }
				                               ps.setString(14,update_user);
				                               System.out.println("update_user : "+update_user);
				                               ps.setTimestamp(15,ts);
				                               System.out.println("ts : "+ts);
				                               try{office_Code=Integer.parseInt(Grid_Office[k]);}
				               	               catch(NumberFormatException e){System.out.println("exception"+e );}
				                               ps.setInt(16,office_Code);
				                               ps.setInt(17, SL_NO);
				                              
				                               ps.setString(18, "N");
				                             
				       			     //System.out.println("rad_adj============>"+request.getParameter("rad_adj"));
				       			            if(request.getParameter("rad_adj").equalsIgnoreCase("Receipt"))
				       			            {
				       			            	ps.setString(19, "R");
				       			            }
				       			            else if(request.getParameter("rad_adj").equalsIgnoreCase("Payment"))
				       			            {
				       			            	ps.setString(19, "P");
				       			            }
				       			            else
				       			            {
				       			             ps.setString(19, "J");
				       			            }
				                       
				                               System.out.println("office_Code"+office_Code);
				                               int i=ps.executeUpdate(); 
				                               if(i>0)
				                               {
				                            	   count++;
				                               }
				                              
			                           }
			                           catch(Exception e)
			                           {
			                        	   e.getStackTrace();
			                        	   System.out.println("Err in value setting for insertion:::"+e.getMessage());
			                        	   con.rollback(); 
			                           }
		                         }
		                         ps.close();
		                         System.out.println("Count=====123======>"+count);
		                      System.out.println("Grid_H_code.length "+Grid_H_code.length);
		                         if(count==Grid_H_code.length)
		                         {
			                         System.out.println("b4 commit");
			                         con.commit();
			                         sendMessage(response,"The Memo Advice Number '"+memo_advice_No+"' has been Created Successfully ","ok");
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
