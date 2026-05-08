package Servlets.FAS.FAS1.MonthEnd.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.Calendar;

import javax.servlet.*;
import javax.servlet.http.*;

public class FreezeTB extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }
    public void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException,
                                                 IOException {
    	  Connection con = null;
          Statement statement = null;
          ResultSet rst = null, results = null;
          PreparedStatement ps1=null,ps2=null;
          CallableStatement cs = null;
          try {
              ResourceBundle rs =
                  ResourceBundle.getBundle("Servlets.Security.servlets.Config");
                  
              String conString = "";

              String strDriver = rs.getString("Config.DATA_BASE_DRIVER");
              String strdsn = rs.getString("Config.DSN");
              String strhostname = rs.getString("Config.HOST_NAME");
              String strportno = rs.getString("Config.PORT_NUMBER");
              String strsid = rs.getString("Config.SID");
              String strdbusername = rs.getString("Config.USER_NAME");
              String strdbpassword = rs.getString("Config.PASSWORD");

//              conString =
//                      strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
//                      ":" + strsid.trim();
              conString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection


              Class.forName(strDriver.trim());
              con =
   DriverManager.getConnection(conString, strdbusername.trim(),
                               strdbpassword.trim());
              try {
                  statement = con.createStatement();
                  con.clearWarnings();
              } catch (SQLException e) {
                  System.out.println("Exception in creating statement:" + e);
              }
          } catch (Exception e) {
              System.out.println("Exception in openeing con:" + e);
          }


          response.setContentType(CONTENT_TYPE);


          HttpSession session = request.getSession(false);
          try {

              if (session == null) {
                  System.out.println(request.getContextPath() + "/index.jsp");
                  response.sendRedirect(request.getContextPath() + "/index.jsp");

              }
              System.out.println(session);

          } catch (Exception e) {
              System.out.println("Redirect Error :" + e);
          }
          int cmbAcc_UnitCode =0;
          try {
              cmbAcc_UnitCode =
                      Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
          } catch (NumberFormatException e) {
              System.out.println("exception" + e);
          }
          
          int txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));

          int txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
          
      String cmd=request.getParameter("command");
      String xml="";
      PrintWriter ps=response.getWriter();
      
          
      if(cmd.equalsIgnoreCase("check_head"))
      {
          
          
          xml="<response><command>check_head</command>";
          
          BigDecimal amt_402=null;
          BigDecimal amt_403=null;
          int count_haed1=0;
          try{
          	String sql="select  sum(CURRENT_MONTH_CREDIT) h402, sum(CURRENT_MONTH_debit) h403 from FAS_TRIAL_BALANCE where accounting_unit_id=?  and cashbook_month=?  and cashbook_year=? and account_head_code in (391402,391403)";
          	 ps1 = con.prepareStatement(sql);
               ps1.setInt(1, cmbAcc_UnitCode);
               ps1.setInt(2, txtCB_Month);
               ps1.setInt(3, txtCB_Year);
               ResultSet rs = ps1.executeQuery();
               System.out.println("after execution");
               while (rs.next()) {
              	 if(rs.getInt(1)==1)
              		 amt_402 = rs.getBigDecimal(1);
              	 else if(rs.getInt(1)==2)
              		 amt_403 = rs.getBigDecimal(2);
               }
               System.out.println(" >> "+amt_402+" >> "+amt_403);
              if(txtCB_Year==2015){
              	if(txtCB_Month>=6){
              		 if(amt_402.equals(amt_403) )
                       {
                      	 count_haed1=0;
                      	 xml=xml+"<flag>success</flag>";
                      	 xml=xml+"<count>"+count_haed1+"</count>";
                       }else{
                      	 count_haed1=1;
                      	xml=xml+"<flag>success</flag>";
                    	 xml=xml+"<count>"+count_haed1+"</count>";
                       }	
              	}else{
              		 count_haed1=0;
              		xml=xml+"<flag>success</flag>";
              		 xml=xml+"<count>"+count_haed1+"</count>";
              	}
              }else if(txtCB_Year>2015){
               if(amt_402.equals(amt_403) )
               {
              	 count_haed1=0;
              	xml=xml+"<flag>success</flag>";
            	 xml=xml+"<count>"+count_haed1+"</count>";
               }else{
              	 count_haed1=1;
              	xml=xml+"<flag>success</flag>";
            	 xml=xml+"<count>"+count_haed1+"</count>";
               }
              }else{
              	 count_haed1=0;
              	xml=xml+"<flag>success</flag>";
            	 xml=xml+"<count>"+count_haed1+"</count>";
              }
          }
          catch (Exception e) {
        		 xml=xml+"<flag>faliure</flag>";
              System.out.println("head credit amt equal");
              e.printStackTrace();
          }
          
      }
      
      else  if(cmd.equalsIgnoreCase("verify"))
      {
    	  
    	  xml="<response><command>verify</command>";
    	  System.out.println("Inside Verify!!!!!!!");
    	 
    	  String sql1="";ResultSet rs=null;
    	 
    	  try{
    	  
    		  System.out.println("cmbAcc_UnitCode>>>>"+cmbAcc_UnitCode);
    		  
    		int count1=0;  
    		  
    		  try {
    	            PreparedStatement ps11 = null;
    	            ps11 =
    	  con.prepareStatement("  select 'X'                  \n" + "  from                        \n" +
    	                       "    FAS_TRIAL_BALANCE_STATUS  \n" +
    	                       "  WHERE                       \n" +
    	                       "     ACCOUNTING_UNIT_ID=?     \n" +
    	                       "  AND CASHBOOK_YEAR=?      \n" +
    	                       "  AND CASHBOOK_MONTH=?");
    	            ps11.setInt(1, cmbAcc_UnitCode);
    	            ps11.setInt(2, txtCB_Year);
    	            ps11.setInt(3, txtCB_Month);
    	            ResultSet rh = ps11.executeQuery();
    	            if (rh.next()) // if the row doesn't return by the query
    	            {
    	            	 xml=xml+"<flag>Already_Frozen</flag>";
    	            	 count1=1;
    	            }
    	            rh.close();
    	            ps11.close();
    	        } catch (Exception e) {
    	            System.out.println(e);
    	        }
    		  
    		  
    		  
    		if(count1==0)  {
    			
    	  if(cmbAcc_UnitCode!=5)
    	 {
    	  System.out.println("inside !=5 ........");
    	  
    	  //sheron_10_27_2023
    		  
    		  sql1="SELECT Descr," + 
    	 		"  accounting_unit_id," + 
    	 		"  YEAR," + 
    	 		"  MONTH," + 
//    	 		"  SUM(debit)  AS debit," + 
//    	 		"  SUM(credit) AS credit," + 
//    	 		"  SUM(debit-credit) as diff" + 
    	 		"SUM ( round(debit::numeric,2) ) AS debit,"+
    	 		"SUM ( round(credit::numeric,2) ) AS credit,"+	
    	 	"sum(round(debit::numeric,2)) - sum(round(credit::numeric,2)) as diff"+

    	 		
    	 		
    	 		" FROM" + 
    	 		"  (SELECT 'GL' AS Descr," + 
    	 		"    g.accounting_unit_id," + 
    	 		"    g.year," + 
    	 		"    g.month," + 
    	 		"    SUM(g.current_month_debit)  AS debit ," + 
    	 		"    SUM(g.current_month_credit) AS credit" + 
    	 		"  FROM fas_general_ledger g" + 
    	 		"  WHERE g.accounting_unit_id =" + cmbAcc_UnitCode +
    	 		"  AND g.year                 =" + txtCB_Year +
    	 		"  AND g.month                =" + txtCB_Month +
    	 		"  GROUP BY g.accounting_unit_id," + 
    	 		"    g.year," + 
    	 		"    g.month" + 
    	 		"  UNION ALL" + 
    	 		"  SELECT 'TB' AS Descr," + 
    	 		"    g.accounting_unit_id," + 
    	 		"    g.cashbook_year             AS YEAR ," + 
    	 		"    g.cashbook_month            AS MONTH," + 
    	 		"    SUM(g.current_month_debit)  AS debit ," + 
    	 		"    SUM(g.current_month_credit) AS credit" + 
    	 		"  FROM fas_trial_balance g" + 
    	 		"  WHERE g.accounting_unit_id =" + cmbAcc_UnitCode +
    	 		"  AND g.cashbook_year        =" + txtCB_Year +
    	 		"  AND g.cashbook_month       =" + txtCB_Month +
    	 		"  GROUP BY g.accounting_unit_id," + 
    	 		"    g.cashbook_year," + 
    	 		"    g.cashbook_month" + 
    	 		"  UNION ALL" + 
    	 		"  SELECT 'Transaction' AS Descr," + 
    	 		"    accounting_unit_id," + 
    	 		"    cashbook_year  AS YEAR," + 
    	 		"    cashbook_month AS MONTH," + 
    	 		"    SUM(debit)     AS debit ," + 
    	 		"    SUM(credit)    AS credit" + 
    	 		"  FROM" + 
    	 		"    (SELECT m.accounting_unit_id," + 
    	 		"      m.cashbook_year," + 
    	 		"      m.cashbook_month," + 
    	 		"      m.total_amount AS debit," + 
    	 		"      0              AS credit" + 
    	 		"    FROM fas_receipt_master m" + 
    	 		"    WHERE m.receipt_status   ='L'" + 
    	 		"    AND m.accounting_unit_id =" + cmbAcc_UnitCode +
    	 		"    AND m.cashbook_year      =" + txtCB_Year +
    	 		"    AND m.cashbook_month     =" + txtCB_Month +
    	 		"    UNION ALL" + 
    	 		"    SELECT t.accounting_unit_id," + 
    	 		"      t.cashbook_year," + 
    	 		"      t.cashbook_month," + 
    	 		"      0        AS debit," + 
    	 		"      t.amount AS credit" + 
    	 		"    FROM fas_receipt_transaction t," + 
    	 		"      fas_receipt_master m" + 
    	 		"    WHERE t.accounting_unit_id     = m.accounting_unit_id" + 
    	 		"    AND t.accounting_for_office_id = m.accounting_for_office_id" + 
    	 		"    AND m.cashbook_year            = t.cashbook_year" + 
    	 		"    AND m.cashbook_month           = t.cashbook_month" + 
    	 		"    AND m.receipt_status           ='L'" + 
    	 		"    AND m.receipt_no               =t.receipt_no" + 
    	 		"    AND t.cr_dr_indicator          = 'CR'" + 
    	 		"    AND m.accounting_unit_id       =" + cmbAcc_UnitCode +
    	 		"    AND m.cashbook_year            =" + txtCB_Year +
    	 		"    AND m.cashbook_month           =" + txtCB_Month +
    	 		"    UNION ALL" + 
    	 		"    SELECT t.accounting_unit_id," + 
    	 		"      t.cashbook_year," + 
    	 		"      t.cashbook_month," + 
    	 		"      0        AS credit," + 
    	 		"      t.amount AS debit" + 
    	 		"    FROM fas_receipt_transaction t," + 
    	 		"      fas_receipt_master m" + 
    	 		"    WHERE t.accounting_unit_id     = m.accounting_unit_id" + 
    	 		"    AND t.accounting_for_office_id = m.accounting_for_office_id" + 
    	 		"    AND m.cashbook_year            = t.cashbook_year" + 
    	 		"    AND m.cashbook_month           = t.cashbook_month" + 
    	 		"    AND m.receipt_status           ='L'" + 
    	 		"    AND m.receipt_no               =t.receipt_no" + 
    	 		"    AND t.cr_dr_indicator          = 'DR'" + 
    	 		"    AND m.accounting_unit_id       =" + cmbAcc_UnitCode +
    	 		"    AND m.cashbook_year            =" + txtCB_Year +
    	 		"    AND m.cashbook_month           =" + txtCB_Month +
    	 		"    )as sm" + 
    	 		"  GROUP BY accounting_unit_id," + 
    	 		"    cashbook_year," + 
    	 		"    cashbook_month" + 
    	 		"  UNION ALL" + 
    	 		"  SELECT 'Transaction' AS Descr," + 
    	 		"    accounting_unit_id," + 
    	 		"    cashbook_year  AS YEAR," + 
    	 		"    cashbook_month AS MONTH," + 
    	 		"    SUM(debit)     AS debit ," + 
    	 		"    SUM(credit)    AS credit" + 
    	 		"  FROM" + 
    	 		"    (SELECT m.accounting_unit_id," + 
    	 		"      m.cashbook_year," + 
    	 		"      m.cashbook_month," + 
    	 		"      m.total_amount AS credit," + 
    	 		"      0              AS debit" + 
    	 		"    FROM fas_payment_master m" + 
    	 		"    WHERE m.payment_status   ='L'" + 
    	 		"    AND m.accounting_unit_id =" + cmbAcc_UnitCode +
    	 		"    AND m.cashbook_year      =" + txtCB_Year +
    	 		"    AND m.cashbook_month     =" + txtCB_Month +
    	 		"    UNION ALL" + 
    	 		"    SELECT t.accounting_unit_id," + 
    	 		"      t.cashbook_year," + 
    	 		"      t.cashbook_month," + 
    	 		"      0        AS credit," + 
    	 		"      t.amount AS debit" + 
    	 		"    FROM fas_payment_transaction t," + 
    	 		"      fas_payment_master m" + 
    	 		"    WHERE t.accounting_unit_id     = m.accounting_unit_id" + 
    	 		"    AND t.accounting_for_office_id = m.accounting_for_office_id" + 
    	 		"    AND m.cashbook_year            = t.cashbook_year" + 
    	 		"    AND m.cashbook_month           = t.cashbook_month" + 
    	 		"    AND m.payment_status           ='L'" + 
    	 		"    AND m.voucher_no               =t.voucher_no" + 
    	 		"    AND t.cr_dr_indicator          = 'DR'" + 
    	 		"    AND m.accounting_unit_id       =" + cmbAcc_UnitCode +
    	 		"    AND m.cashbook_year            =" + txtCB_Year +
    	 		"    AND m.cashbook_month           =" + txtCB_Month +
    	 		"    UNION ALL" + 
    	 		"    SELECT t.accounting_unit_id," + 
    	 		"      t.cashbook_year," + 
    	 		"      t.cashbook_month," + 
    	 		"      t.amount AS credit," + 
    	 		"      0        AS debit" + 
    	 		"    FROM fas_payment_transaction t," + 
    	 		"      fas_payment_master m" + 
    	 		"    WHERE t.accounting_unit_id     = m.accounting_unit_id" + 
    	 		"    AND t.accounting_for_office_id = m.accounting_for_office_id" + 
    	 		"    AND m.cashbook_year            = t.cashbook_year" + 
    	 		"    AND m.cashbook_month           = t.cashbook_month" + 
    	 		"    AND m.payment_status           ='L'" + 
    	 		"    AND m.voucher_no               =t.voucher_no" + 
    	 		"    AND t.cr_dr_indicator          = 'CR'" + 
    	 		"    AND m.accounting_unit_id       =" + cmbAcc_UnitCode +
    	 		"    AND m.cashbook_year            =" + txtCB_Year +
    	 		"    AND m.cashbook_month           =" + txtCB_Month +
    	 		"    )as sm" + 
    	 		"  GROUP BY accounting_unit_id," + 
    	 		"    cashbook_year," + 
    	 		"    cashbook_month" + 
    	 		"  UNION ALL" + 
    	 		"  SELECT 'Transaction' AS Descr," + 
    	 		"    accounting_unit_id," + 
    	 		"    cashbook_year  AS YEAR," + 
    	 		"    cashbook_month AS MONTH," + 
    	 		"    SUM(debit)     AS debit ," + 
    	 		"    SUM(credit)    AS credit" + 
    	 		"  FROM" + 
    	 		"    (SELECT m.accounting_unit_id," + 
    	 		"      m.cashbook_year," + 
    	 		"      m.cashbook_month," + 
    	 		"      t.amount AS credit," + 
    	 		"      0        AS debit" + 
    	 		"    FROM fas_journal_master m," + 
    	 		"      fas_journal_transaction t" + 
    	 		"    WHERE t.accounting_unit_id     = m.accounting_unit_id" + 
    	 		"    AND t.accounting_for_office_id = m.accounting_for_office_id" + 
    	 		"    AND m.cashbook_year            = t.cashbook_year" + 
    	 		"    AND m.cashbook_month           = t.cashbook_month" + 
    	 		"    AND m.voucher_no               = t.voucher_no" + 
    	 		"    AND m.Journal_status           ='L'" + 
    	 		"    AND t.cr_dr_indicator          = 'CR'" + 
    	 		"    AND m.CREATED_BY_MODULE not in ('SJV') " +
    	 		"    AND m.accounting_unit_id       =" + cmbAcc_UnitCode +
    	 		"    AND m.cashbook_year            =" + txtCB_Year +
    	 		"    AND m.cashbook_month           =" + txtCB_Month +
    	 		"    UNION ALL" + 
    	 		"    SELECT m.accounting_unit_id," + 
    	 		"      m.cashbook_year," + 
    	 		"      m.cashbook_month," + 
    	 		"      0        AS credit," + 
    	 		"      t.amount AS debit" + 
    	 		"    FROM fas_journal_master m," + 
    	 		"      fas_journal_transaction t" + 
    	 		"    WHERE t.accounting_unit_id     = m.accounting_unit_id" + 
    	 		"    AND t.accounting_for_office_id = m.accounting_for_office_id" + 
    	 		"    AND m.cashbook_year            = t.cashbook_year" + 
    	 		"    AND m.cashbook_month           = t.cashbook_month" + 
    	 		"    AND m.voucher_no               = t.voucher_no" + 
    	 		"    AND m.Journal_status           ='L'" + 
    	 		"    AND t.cr_dr_indicator          = 'DR'" + 
    	 		"    AND m.CREATED_BY_MODULE not in ('SJV') " +
    	 		"    AND m.accounting_unit_id       =" + cmbAcc_UnitCode +
    	 		"    AND m.cashbook_year            =" + txtCB_Year +
    	 		"    AND m.cashbook_month           =" + txtCB_Month +
    	 		"    )as sm" + 
    	 		"  GROUP BY accounting_unit_id," + 
    	 		"    cashbook_year," + 
    	 		"    cashbook_month" + 
    	 		"  UNION ALL" + 
    	 		"  SELECT 'Transaction' AS Descr," + 
    	 		"    accounting_unit_id," + 
    	 		"    cashbook_year     AS YEAR," + 
    	 		"    cashbook_month    AS MONTH," + 
    	 		"    SUM(total_amount) AS debit ," + 
    	 		"    SUM(total_amount) AS credit" + 
    	 		"  FROM fas_fund_trf_from_office" + 
    	 		"  WHERE transfer_status  ='L'" + 
    	 		"  AND accounting_unit_id =" + cmbAcc_UnitCode +
    	 		"  AND cashbook_year      =" + txtCB_Year +
    	 		"  AND cashbook_month     =" + txtCB_Month +
    	 		"  GROUP BY accounting_unit_id," + 
    	 		"    cashbook_year," + 
    	 		"    cashbook_month" + 
    	 		"  UNION ALL" + 
    	 		"  SELECT 'Transaction' AS Descr," + 
    	 		"    accounting_unit_id," + 
    	 		"    cashbook_year     AS YEAR," + 
    	 		"    cashbook_month    AS MONTH," + 
    	 		"    SUM(total_amount) AS debit ," + 
    	 		"    SUM(total_amount) AS credit" + 
    	 		"  FROM fas_fund_Receipt_by_office" + 
    	 		"  WHERE receipt_status   ='L'" + 
    	 		"  AND accounting_unit_id =" + cmbAcc_UnitCode +
    	 		"  AND cashbook_year      =" + txtCB_Year +
    	 		"  AND cashbook_month     =" + txtCB_Month +
    	 		"  GROUP BY accounting_unit_id," + 
    	 		"    cashbook_year," + 
    	 		"    cashbook_month" + 
    	 		"  UNION ALL" + 
    	 		"  SELECT 'Transaction' AS Descr," + 
    	 		"    accounting_unit_id," + 
    	 		"    cashbook_year     AS YEAR," + 
    	 		"    cashbook_month    AS MONTH," + 
    	 		"    SUM(total_amount) AS debit ," + 
    	 		"    SUM(total_amount) AS credit" + 
    	 		"  FROM fas_inter_bank_trf_at_HO" + 
    	 		"  WHERE transfer_status  ='L'" + 
    	 		"  AND accounting_unit_id =" + cmbAcc_UnitCode +
    	 		"  AND cashbook_year      =" + txtCB_Year +
    	 		"  AND cashbook_month     =" + txtCB_Month +
    	 		"  GROUP BY accounting_unit_id," + 
    	 		"    cashbook_year," + 
    	 		"    cashbook_month" + 
    	 		"  )as sm" + 
    	 		" GROUP BY descr," + 
    	 		"  accounting_unit_id," + 
    	 		"  YEAR," + 
    	 		"  MONTH " + 
    	 		" ORDER BY accounting_unit_id," + 
    	 		"  YEAR," + 
    	 		"  MONTH," + 
    	 		"  descr ";	 
    		  
    		  System.out.println("SQL1>>>1>>>>"+sql1);
    		  
    		  
    	 }
    	 else if(cmbAcc_UnitCode==5)
    	 {

        	 sql1="SELECT Descr," + 
        	 		"  accounting_unit_id," + 
        	 		"  YEAR," + 
        	 		"  MONTH," + 
        	 		
//     sheron[27_10_2023] round   	 		
//					"  SUM(debit)  AS debit," + 
//        	 		"  SUM(credit) AS credit," + 
//        	 		"  SUM(debit)-sum(credit) as diff" + 
					"SUM ( round(debit::numeric,2) ) AS debit,"+
					"SUM ( round(credit::numeric,2) ) AS credit,"+	
					"sum(round(debit::numeric,2)) - sum(round(credit::numeric,2)) as diff"+
        	 		"  FROM" + 
        	 		"  (SELECT 'GL' AS Descr," + 
        	 		"    g.accounting_unit_id," + 
        	 		"    g.year," + 
        	 		"    g.month," + 
        	 		"    SUM(g.current_month_debit)  AS debit ," + 
        	 		"    SUM(g.current_month_credit) AS credit" + 
        	 		"  FROM fas_general_ledger g" + 
        	 		"  WHERE g.accounting_unit_id =5" +
        	 		"  AND g.year                 =" + txtCB_Year +
        	 		"  AND g.month                =" + txtCB_Month +
        	 		"  GROUP BY g.accounting_unit_id," + 
        	 		"    g.year," + 
        	 		"    g.month" + 
        	 		"  UNION ALL" + 
        	 		"  SELECT 'TB' AS Descr," + 
        	 		"    g.accounting_unit_id," + 
        	 		"    g.cashbook_year             AS YEAR ," + 
        	 		"    g.cashbook_month            AS MONTH," + 
        	 		"    SUM(g.current_month_debit)  AS debit ," + 
        	 		"    SUM(g.current_month_credit) AS credit" + 
        	 		"  FROM fas_trial_balance g" + 
        	 		"  WHERE g.accounting_unit_id =5" + 
        	 		"  AND g.cashbook_year        =" + txtCB_Year +
        	 		"  AND g.cashbook_month       =" + txtCB_Month +
        	 		"  GROUP BY g.accounting_unit_id," + 
        	 		"    g.cashbook_year," + 
        	 		"    g.cashbook_month" + 
        	 		"  UNION ALL" + 
        	 		"  SELECT 'Transaction' AS Descr," + 
        	 		"    accounting_unit_id," + 
        	 		"    cashbook_year  AS YEAR," + 
        	 		"    cashbook_month AS MONTH," + 
        	 		"    SUM(debit)     AS debit ," + 
        	 		"    SUM(credit)    AS credit" + 
        	 		"  FROM" + 
        	 		"    (SELECT m.accounting_unit_id," + 
        	 		"      m.cashbook_year," + 
        	 		"      m.cashbook_month," + 
        	 		"      m.total_amount AS debit," + 
        	 		"      0              AS credit" + 
        	 		"    FROM fas_receipt_master m" + 
        	 		"    WHERE m.receipt_status   ='L'" + 
        	 		"    AND m.accounting_unit_id =5" + 
        	 		"    AND m.cashbook_year      =" + txtCB_Year +
        	 		"    AND m.cashbook_month     =" + txtCB_Month +
        	 		"    UNION ALL" + 
        	 		"    SELECT t.accounting_unit_id," + 
        	 		"      t.cashbook_year," + 
        	 		"      t.cashbook_month," + 
        	 		"      0        AS debit," + 
        	 		"      t.amount AS credit" + 
        	 		"    FROM fas_receipt_transaction t," + 
        	 		"      fas_receipt_master m" + 
        	 		"    WHERE t.accounting_unit_id     = m.accounting_unit_id" + 
        	 		"    AND t.accounting_for_office_id = m.accounting_for_office_id" + 
        	 		"    AND m.cashbook_year            = t.cashbook_year" + 
        	 		"    AND m.cashbook_month           = t.cashbook_month" + 
        	 		"    AND m.receipt_status           ='L'" + 
        	 		"    AND m.receipt_no               =t.receipt_no" + 
        	 		"    AND t.cr_dr_indicator          = 'CR'" + 
        	 		"    AND m.accounting_unit_id       =5" + 
        	 		"    AND m.cashbook_year            =" + txtCB_Year +
        	 		"    AND m.cashbook_month           =" + txtCB_Month +
        	 		"    UNION ALL" + 
        	 		"    SELECT t.accounting_unit_id," + 
        	 		"      t.cashbook_year," + 
        	 		"      t.cashbook_month," + 
        	 		"      0        AS credit," + 
        	 		"      t.amount AS debit" + 
        	 		"    FROM fas_receipt_transaction t," + 
        	 		"      fas_receipt_master m" + 
        	 		"    WHERE t.accounting_unit_id     = m.accounting_unit_id" + 
        	 		"    AND t.accounting_for_office_id = m.accounting_for_office_id" + 
        	 		"    AND m.cashbook_year            = t.cashbook_year" + 
        	 		"    AND m.cashbook_month           = t.cashbook_month" + 
        	 		"    AND m.receipt_status           ='L'" + 
        	 		"    AND m.receipt_no               =t.receipt_no" + 
        	 		"    AND t.cr_dr_indicator          = 'DR'" + 
        	 		"    AND m.accounting_unit_id       =5" + 
        	 		"    AND m.cashbook_year            =" + txtCB_Year +
        	 		"    AND m.cashbook_month           =" + txtCB_Month +
        	 		"    )as sm" + 
        	 		"  GROUP BY accounting_unit_id," + 
        	 		"    cashbook_year," + 
        	 		"    cashbook_month" + 
        	 		"  UNION ALL" + 
        	 		"  SELECT 'Transaction' AS Descr," + 
        	 		"    accounting_unit_id," + 
        	 		"    cashbook_year  AS YEAR," + 
        	 		"    cashbook_month AS MONTH," + 
        	 		"    SUM(debit)     AS debit ," + 
        	 		"    SUM(credit)    AS credit" + 
        	 		"  FROM" + 
        	 		"    (SELECT m.accounting_unit_id," + 
        	 		"      m.cashbook_year," + 
        	 		"      m.cashbook_month," + 
        	 		"      m.total_amount AS credit," + 
        	 		"      0              AS debit" + 
        	 		"    FROM fas_payment_master m" + 
        	 		"    WHERE m.payment_status   ='L'" + 
        	 		"    AND m.accounting_unit_id =5" + 
        	 		"    AND m.cashbook_year      =" + txtCB_Year +
        	 		"    AND m.cashbook_month     =" + txtCB_Month +
        	 		"    UNION ALL" + 
        	 		"    SELECT t.accounting_unit_id," + 
        	 		"      t.cashbook_year," + 
        	 		"      t.cashbook_month," + 
        	 		"      0        AS credit," + 
        	 		"      t.amount AS debit" + 
        	 		"    FROM fas_payment_transaction t," + 
        	 		"      fas_payment_master m" + 
        	 		"    WHERE t.accounting_unit_id     = m.accounting_unit_id" + 
        	 		"    AND t.accounting_for_office_id = m.accounting_for_office_id" + 
        	 		"    AND m.cashbook_year            = t.cashbook_year" + 
        	 		"    AND m.cashbook_month           = t.cashbook_month" + 
        	 		"    AND m.payment_status           ='L'" + 
        	 		"    AND m.voucher_no               =t.voucher_no" + 
        	 		"    AND t.cr_dr_indicator          = 'DR'" + 
        	 		"    AND m.accounting_unit_id       =5" + 
        	 		"    AND m.cashbook_year            =" + txtCB_Year +
        	 		"    AND m.cashbook_month           =" + txtCB_Month +
        	 		"    UNION ALL" + 
        	 		"    SELECT t.accounting_unit_id," + 
        	 		"      t.cashbook_year," + 
        	 		"      t.cashbook_month," + 
        	 		"      t.amount AS credit," + 
        	 		"      0        AS debit" + 
        	 		"    FROM fas_payment_transaction t," + 
        	 		"      fas_payment_master m" + 
        	 		"    WHERE t.accounting_unit_id     = m.accounting_unit_id" + 
        	 		"    AND t.accounting_for_office_id = m.accounting_for_office_id" + 
        	 		"    AND m.cashbook_year            = t.cashbook_year" + 
        	 		"    AND m.cashbook_month           = t.cashbook_month" + 
        	 		"    AND m.payment_status           ='L'" + 
        	 		"    AND m.voucher_no               =t.voucher_no" + 
        	 		"    AND t.cr_dr_indicator          = 'CR'" + 
        	 		"    AND m.accounting_unit_id       =5" + 
        	 		"    AND m.cashbook_year            =" + txtCB_Year +
        	 		"    AND m.cashbook_month           =" + txtCB_Month +
        	 		"    )as sm" + 
        	 		"  GROUP BY accounting_unit_id," + 
        	 		"    cashbook_year," + 
        	 		"    cashbook_month" + 
        	 		"  UNION ALL" + 
        	 		"  SELECT 'Transaction' AS Descr," + 
        	 		"    accounting_unit_id," + 
        	 		"    cashbook_year  AS YEAR," + 
        	 		"    cashbook_month AS MONTH," + 
        	 		"    SUM(debit)     AS debit ," + 
        	 		"    SUM(credit)    AS credit" + 
        	 		"  FROM" + 
        	 		"    (SELECT m.accounting_unit_id," + 
        	 		"      m.cashbook_year," + 
        	 		"      m.cashbook_month," + 
        	 		"      t.amount AS credit," + 
        	 		"      0        AS debit" + 
        	 		"    FROM fas_journal_master m," + 
        	 		"      fas_journal_transaction t" + 
        	 		"    WHERE t.accounting_unit_id     = m.accounting_unit_id" + 
        	 		"    AND t.accounting_for_office_id = m.accounting_for_office_id" + 
        	 		"    AND m.cashbook_year            = t.cashbook_year" + 
        	 		"    AND m.cashbook_month           = t.cashbook_month" + 
        	 		"    AND m.voucher_no               = t.voucher_no" + 
        	 		"    AND m.Journal_status           ='L'" + 
        	 		"    AND t.cr_dr_indicator          = 'CR'" + 
        	 		"    AND m.CREATED_BY_MODULE not in ('SJV') " +
        	 		"    AND m.accounting_unit_id       =5" + 
        	 		"    AND m.cashbook_year            =" + txtCB_Year +
        	 		"    AND m.cashbook_month           =" + txtCB_Month +
        	 		"    UNION ALL" + 
        	 		"    SELECT m.accounting_unit_id," + 
        	 		"      m.cashbook_year," + 
        	 		"      m.cashbook_month," + 
        	 		"      0        AS credit," + 
        	 		"      t.amount AS debit" + 
        	 		"    FROM fas_journal_master m," + 
        	 		"      fas_journal_transaction t" + 
        	 		"    WHERE t.accounting_unit_id     = m.accounting_unit_id" + 
        	 		"    AND t.accounting_for_office_id = m.accounting_for_office_id" + 
        	 		"    AND m.cashbook_year            = t.cashbook_year" + 
        	 		"    AND m.cashbook_month           = t.cashbook_month" + 
        	 		"    AND m.voucher_no               = t.voucher_no" + 
        	 		"    AND m.Journal_status           ='L'" + 
        	 		"    AND t.cr_dr_indicator          = 'DR'" + 
        	 		"    AND m.CREATED_BY_MODULE not in ('SJV') " +
        	 		"    AND m.accounting_unit_id       =5" + 
        	 		"    AND m.cashbook_year            =" + txtCB_Year +
        	 		"    AND m.cashbook_month           =" + txtCB_Month +
        	 		"    )as sm" + 
        	 		"  GROUP BY accounting_unit_id," + 
        	 		"    cashbook_year," + 
        	 		"    cashbook_month" + 
        	 		"  UNION ALL" + 
//        	 		"  SELECT 'Tran' AS Descr," + 
//        	 		"    accounting_unit_id," + 
//        	 		"    cashbook_year     AS YEAR," + 
//        	 		"    cashbook_month    AS MONTH," + 
//        	 		"    SUM(total_amount) AS debit ," + 
//        	 		"    SUM(total_amount) AS credit" + 
//        	 		"  FROM fas_fund_trf_from_office" + 
//        	 		"  WHERE transfer_status  ='L'" + 
//        	 		"  AND accounting_unit_id =" + cmbAcc_UnitCode +
//        	 		"  AND cashbook_year      =" + txtCB_Year +
//        	 		"  AND cashbook_month     =" + txtCB_Month +
//        	 		"  GROUP BY accounting_unit_id," + 
//        	 		"    cashbook_year," + 
//        	 		"    cashbook_month" + 

        	 		"  SELECT 'Transaction' AS Descr," + 
        	 		"    accounting_unit_id," + 
        	 		"    cashbook_year     AS YEAR," + 
        	 		"    cashbook_month    AS MONTH," + 
        	 		"    SUM(debit) AS debit ," + 
        	 		"    SUM(credit) AS credit" + 
        	 		"  FROM " + 
        	 		"  (" + 
        	 		"  SELECT m.accounting_unit_id," + 
        	 		"      m.cashbook_year," + 
        	 		"      m.cashbook_month," + 
        	 		"      t.amount AS credit," + 
        	 		"      0        AS debit" + 
        	 		"    FROM FAS_FUND_TRF_FROM_HO_MASTER m," + 
        	 		"      FAS_FUND_TRF_FROM_HO_TRN t" + 
        	 		"    WHERE t.accounting_unit_id     = m.accounting_unit_id" + 
        	 		"    AND t.accounting_for_office_id = m.accounting_for_office_id" + 
        	 		"    AND m.cashbook_year            = t.cashbook_year" + 
        	 		"    AND m.cashbook_month           = t.cashbook_month" + 
        	 		"    AND m.voucher_no               = t.voucher_no" + 
        	 		"    AND m.TRANSFER_STATUS          ='L'" + 
        	 		"    AND m.cr_dr_indicator          = 'CR'" + 
        	 		"    AND M.ACCOUNTING_UNIT_ID       =5" +
        	 		"    AND m.cashbook_year            =" + txtCB_Year +
        	 		"    AND m.cashbook_month           =" + txtCB_Month +
        	 		"    UNION ALL" + 
        	 		"    SELECT m.accounting_unit_id," + 
        	 		"      m.cashbook_year," + 
        	 		"      m.cashbook_month," + 
        	 		"      0        AS credit," + 
        	 		"      t.amount AS debit" + 
        	 		"    FROM FAS_FUND_TRF_FROM_HO_MASTER m," + 
        	 		"       FAS_FUND_TRF_FROM_HO_TRN t" + 
        	 		"    WHERE t.accounting_unit_id     = m.accounting_unit_id" + 
        	 		"    AND t.accounting_for_office_id = m.accounting_for_office_id" + 
        	 		"    AND m.cashbook_year            = t.cashbook_year" + 
        	 		"    AND m.cashbook_month           = t.cashbook_month" + 
        	 		"    AND m.voucher_no               = t.voucher_no" + 
        	 		"    AND m.TRANSFER_STATUS          ='L'" + 
        	 		"    AND t.cr_dr_indicator          = 'DR'" + 
        	 		"    AND M.ACCOUNTING_UNIT_ID       =5" + 
        	 		"    AND M.CASHBOOK_YEAR            =" + txtCB_Year +
        	 		"    AND m.cashbook_month           =" + txtCB_Month +
        	 		"  )as sm  " + 
        	 		"  GROUP BY accounting_unit_id," + 
        	 		"    cashbook_year," + 
        	 		"    cashbook_month " +        	 		
        	 		"  UNION ALL" + 
        	 		"  SELECT 'Transaction' AS Descr," + 
        	 		"    accounting_unit_id," + 
        	 		"    cashbook_year     AS YEAR," + 
        	 		"    cashbook_month    AS MONTH," + 
        	 		"    SUM(total_amount) AS debit ," + 
        	 		"    SUM(total_amount) AS credit" + 
        	 		"  FROM FAS_FUND_RECEIPT_BY_HO" + 
        	 		"  WHERE receipt_status   ='L'" + 
        	 		"  AND accounting_unit_id =5" + 
        	 		"  AND cashbook_year      =" + txtCB_Year +
        	 		"  AND cashbook_month     =" + txtCB_Month +
        	 		"  GROUP BY accounting_unit_id," + 
        	 		"    cashbook_year," + 
        	 		"    cashbook_month" + 
        	 		"  UNION ALL" + 
        	 		"  SELECT 'Transaction' AS Descr," + 
        	 		"    accounting_unit_id," + 
        	 		"    cashbook_year     AS YEAR," + 
        	 		"    cashbook_month    AS MONTH," + 
        	 		"    SUM(total_amount) AS debit ," + 
        	 		"    SUM(total_amount) AS credit" + 
        	 		"  FROM fas_inter_bank_trf_at_HO" + 
        	 		"  WHERE transfer_status  ='L'" + 
        	 		"  AND accounting_unit_id =5" +
        	 		"  AND cashbook_year      =" + txtCB_Year +
        	 		"  AND cashbook_month     =" + txtCB_Month +
        	 		"  GROUP BY accounting_unit_id," + 
        	 		"    cashbook_year," + 
        	 		"    cashbook_month" + 
        	 		"  ) as sm " + 
        	 		"  GROUP BY descr," + 
        	 		"  accounting_unit_id," + 
        	 		"  YEAR," + 
        	 		"  MONTH" + 
        	 		"  ORDER BY accounting_unit_id," + 
        	 		"  YEAR," + 
        	 		"  MONTH," + 
        	 		"  descr ";	 
        	 System.out.println("SQL1>>>>2>>>"+sql1);
        	 
    	 }
    	  ps2 = con.prepareStatement(sql1);
    	     rs = ps2.executeQuery();
    	  
    	  
    	  
    	  
          System.out.println("after execution");
          
          int diff=0;
          int count=0;
          
          while (rs.next()) {
    	 
         xml=xml+"<accounting_unit_id>"+rs.getInt("accounting_unit_id")+"</accounting_unit_id>";
         xml=xml+"<Descr>"+rs.getString("Descr")+"</Descr>";
         xml=xml+"<year>"+rs.getInt("YEAR")+"</year>";
         xml=xml+"<month>"+rs.getInt("MONTH")+"</month>";
         xml=xml+"<debit>"+rs.getBigDecimal("debit")+"</debit>";
         xml=xml+"<credit>"+rs.getBigDecimal("credit")+"</credit>";
         xml=xml+"<diff>"+rs.getBigDecimal("diff")+"</diff>";
         count++;
       // diff=rs.getInt("diff");
         
         
          }
          if(count>0)
          {
        	  xml=xml+"<flag>success</flag>";
          }
          else
          {
        	  xml=xml+"<flag>NoRecord</flag>";
          }
          
          
//          if(diff>0)
//          {
//         	 xml=xml+"<flag>NotTally</flag>";
//          }
//          else if(diff==0)
//          {
//         	 xml=xml+"<flag>success</flag>";
//          }
//          else
//          {
//         	 xml=xml+"<flag>failure</flag>";
//          }
    		}
    	  }catch(Exception e)
          {
        	  System.out.println("Exception >>>>"+e);
          }
      }
      else if(cmd.equalsIgnoreCase("tr_tb_verify")){
    	  xml="<response><command>tr_tb_verify</command>";
    	  
    	  System.out.println("Inside tr_tb_verify!!!!!!!");
    	 
    	  String sql1="";ResultSet rs=null;
    	 
    	  try{
    	  
    		  System.out.println("cmbAcc_UnitCode>>>>"+cmbAcc_UnitCode);
    		  
    		  // HOA in TR not in TB
    			String sql11=" SELECT *" + 
    					" FROM" + 
    					"  (SELECT 'tran' AS descr," + 
    					"    accounting_unit_id," + 
    					"    YEAR," + 
    					"    MONTH," + 
    					"    HOA," + 
    					"    SUM(debit)  AS debit," + 
    					"    SUM(Credit) AS credit" + 
    					"  FROM" + 
    					"    (SELECT 'IBT' AS Descr," + 
    					"      accounting_unit_id," + 
    					"      cashbook_year        AS YEAR," + 
    					"      cashbook_month       AS MONTH," + 
    					"      dr_account_head_code AS HOA," + 
    					"      SUM(total_amount)    AS debit," + 
    					"      0                    AS credit" + 
    					"    FROM fas_inter_bank_trf_at_HO" + 
    					"    WHERE transfer_status  ='L'" + 
    					"    AND accounting_unit_id =" + cmbAcc_UnitCode +
    					"    AND cashbook_year      = " +  txtCB_Year +
    					"    AND cashbook_month     = " + txtCB_Month +
    					"    GROUP BY accounting_unit_id," + 
    					"      cashbook_year," + 
    					"      cashbook_month," + 
    					"      dr_account_head_code" + 
    					"    UNION ALL" + 
    					"    SELECT 'IBT' AS Descr," + 
    					"      accounting_unit_id," + 
    					"      cashbook_year        AS YEAR," + 
    					"      cashbook_month       AS MONTH," + 
    					"      cr_account_head_code AS HOA," + 
    					"      0                    AS debit," + 
    					"      SUM(total_amount)    AS credit" + 
    					"    FROM fas_inter_bank_trf_at_HO" + 
    					"    WHERE transfer_status  ='L'" + 
    					"    AND accounting_unit_id =" + cmbAcc_UnitCode +
    					"    AND cashbook_year      = " +  txtCB_Year +
    					"    AND cashbook_month     = " + txtCB_Month +
    					"    GROUP BY accounting_unit_id," + 
    					"      cashbook_year," + 
    					"      cashbook_month," + 
    					"      cr_account_head_code" + 
    					"    UNION ALL" + 
    					"    SELECT 'FROff' AS Descr," + 
    					"      accounting_unit_id," + 
    					"      cashbook_year        AS YEAR," + 
    					"      cashbook_month       AS MONTH," + 
    					"      dr_account_head_code AS HOA," + 
    					"      SUM(total_amount)    AS debit," + 
    					"      0                    AS credit" + 
    					"    FROM fas_fund_receipt_by_office" + 
    					"    WHERE receipt_status   ='L'" + 
    					"    AND accounting_unit_id =" + cmbAcc_UnitCode +
    					"    AND cashbook_year      = " +  txtCB_Year +
    					"    AND cashbook_month     = " + txtCB_Month +
    					"    GROUP BY accounting_unit_id," + 
    					"      cashbook_year," + 
    					"      cashbook_month," + 
    					"      dr_account_head_code" + 
    					"    UNION ALL" + 
    					"    SELECT 'FROff' AS Descr," + 
    					"      accounting_unit_id," + 
    					"      cashbook_year        AS YEAR," + 
    					"      cashbook_month       AS MONTH," + 
    					"      cr_account_head_code AS HOA," + 
    					"      0                    AS debit," + 
    					"      SUM(total_amount)    AS credit" + 
    					"    FROM fas_fund_receipt_by_office" + 
    					"    WHERE receipt_status   ='L'" + 
    					"    AND accounting_unit_id =" + cmbAcc_UnitCode +
    					"    AND cashbook_year      = " +  txtCB_Year +
    					"    AND cashbook_month     = " + txtCB_Month +
    					"    GROUP BY accounting_unit_id," + 
    					"      cashbook_year," + 
    					"      cashbook_month," + 
    					"      cr_account_head_code" + 
    					"    UNION ALL" + 
    					"    SELECT 'FTOff' AS Descr," + 
    					"      accounting_unit_id," + 
    					"      cashbook_year        AS YEAR," + 
    					"      cashbook_month       AS MONTH," + 
    					"      dr_account_head_code AS HOA," + 
    					"      SUM(total_amount)    AS debit," + 
    					"      0                    AS credit" + 
    					"    FROM Fas_fund_Trf_from_office" + 
    					"    WHERE transfer_status  ='L'" + 
    					"    AND accounting_unit_id =" + cmbAcc_UnitCode +
    					"    AND cashbook_year      = " +  txtCB_Year +
    					"    AND cashbook_month     = " + txtCB_Month +
    					"    GROUP BY accounting_unit_id," + 
    					"      cashbook_year," + 
    					"      cashbook_month," + 
    					"      dr_account_head_code" + 
    					"    UNION ALL" + 
    					"    SELECT 'FTOff' AS Descr," + 
    					"      accounting_unit_id," + 
    					"      cashbook_year        AS YEAR," + 
    					"      cashbook_month       AS MONTH," + 
    					"      cr_account_head_code AS HOA," + 
    					"      0                    AS debit," + 
    					"      SUM(total_amount)    AS credit" + 
    					"    FROM Fas_fund_Trf_from_office" + 
    					"    WHERE transfer_status  ='L'" + 
    					"    AND accounting_unit_id =" + cmbAcc_UnitCode +
    					"    AND cashbook_year      = " +  txtCB_Year +
    					"    AND cashbook_month     = " + txtCB_Month +
    					"    GROUP BY accounting_unit_id," + 
    					"      cashbook_year," + 
    					"      cashbook_month," + 
    					"      cr_account_head_code" + 
    					"    UNION ALL" + 
    					"    SELECT 'FTHO' AS Descr," + 
    					"      accounting_unit_id," + 
    					"      cashbook_year     AS YEAR," + 
    					"      cashbook_month    AS MONTH," + 
    					"      account_head_code AS HOA," + 
    					"      0                 AS debit," + 
    					"      SUM(total_amount) AS credit" + 
    					"    FROM FAS_FUND_TRF_From_ho_master" + 
    					"    WHERE transfer_status  ='L'" + 
    					"    AND accounting_unit_id =" + cmbAcc_UnitCode +
    					"    AND cashbook_year      = " +  txtCB_Year +
    					"    AND cashbook_month     = " + txtCB_Month +
    					"    AND cr_dr_indicator    ='CR'" + 
    					"    GROUP BY accounting_unit_id," + 
    					"      cashbook_year," + 
    					"      cashbook_month," + 
    					"      account_head_code" + 
    					"    UNION ALL" + 
    					"    SELECT 'FTHO' AS Descr," + 
    					"      accounting_unit_id," + 
    					"      cashbook_year     AS YEAR," + 
    					"      cashbook_month    AS MONTH," + 
    					"      account_head_code AS HOA," + 
    					"      SUM(amount)       AS debit," + 
    					"      0                 AS credit" + 
    					"    FROM FAS_FUND_TRF_From_ho_trn" + 
    					"    WHERE accounting_unit_id =" + cmbAcc_UnitCode +
    					"    AND cashbook_year        = " +  txtCB_Year +
    					"    AND cashbook_month       = " + txtCB_Month +
    					"    AND cr_dr_indicator      ='DR'" + 
    					"    AND voucher_no          IN" + 
    					"      (SELECT voucher_no" + 
    					"      FROM FAS_FUND_TRF_From_ho_master" + 
    					"      WHERE transfer_status  ='L'" + 
    					"      AND accounting_unit_id =" + cmbAcc_UnitCode +
    					"      AND cashbook_year      = " +  txtCB_Year +
    					"      AND cashbook_month     = " + txtCB_Month +
    					"      AND cr_dr_indicator    ='CR'" + 
    					"      ) " + 
    					"    GROUP BY accounting_unit_id," + 
    					"      cashbook_year," + 
    					"      cashbook_month," + 
    					"      account_head_code" + 
    					"    UNION ALL" + 
    					"    SELECT 'FRHO' AS Descr," + 
    					"      accounting_unit_id," + 
    					"      cashbook_year        AS YEAR," + 
    					"      cashbook_month       AS MONTH," + 
    					"      dr_account_head_code AS HOA," + 
    					"      SUM(total_amount)    AS debit," + 
    					"      0                    AS credit" + 
    					"    FROM fas_fund_receipt_by_HO" + 
    					"    WHERE receipt_status   ='L'" + 
    					"    AND accounting_unit_id =" + cmbAcc_UnitCode +
    					"    AND cashbook_year      = " +  txtCB_Year +
    					"    AND cashbook_month     = " + txtCB_Month +
    					"    GROUP BY accounting_unit_id," + 
    					"      cashbook_year," + 
    					"      cashbook_month," + 
    					"      dr_account_head_code" + 
    					"    UNION ALL" + 
    					"    SELECT 'FRHO' AS Descr," + 
    					"      accounting_unit_id," + 
    					"      cashbook_year        AS YEAR," + 
    					"      cashbook_month       AS MONTH," + 
    					"      cr_account_head_code AS HOA," + 
    					"      0                    AS debit," + 
    					"      SUM(total_amount)    AS credit" + 
    					"    FROM fas_fund_receipt_by_HO" + 
    					"    WHERE receipt_status   ='L'" + 
    					"    AND accounting_unit_id =" + cmbAcc_UnitCode +
    					"    AND cashbook_year      = " +  txtCB_Year +
    					"    AND cashbook_month     = " + txtCB_Month +
    					"    GROUP BY accounting_unit_id," + 
    					"      cashbook_year," + 
    					"      cashbook_month," + 
    					"      cr_account_head_code" + 
    					"    UNION ALL" + 
    					"    SELECT 'Journal' AS Descr," + 
    					"      accounting_unit_id," + 
    					"      cashbook_year     AS YEAR," + 
    					"      cashbook_month    AS MONTH," + 
    					"      account_head_code AS HOA," + 
    					"      SUM(debit)        AS debit ," + 
    					"      SUM(credit)       AS credit" + 
    					"    FROM" + 
    					"      (SELECT m.accounting_unit_id," + 
    					"        m.cashbook_year," + 
    					"        m.cashbook_month," + 
    					"        t.account_head_code ," + 
    					"        0        AS debit," + 
    					"        t.amount AS credit" + 
    					"      FROM fas_journal_master m," + 
    					"        fas_journal_transaction t" + 
    					"      WHERE t.accounting_unit_id     = m.accounting_unit_id" + 
    					"      AND t.accounting_for_office_id = m.accounting_for_office_id" + 
    					"      AND m.cashbook_year            = t.cashbook_year" + 
    					"      AND m.cashbook_month           = t.cashbook_month" + 
    					"      AND m.voucher_no               = t.voucher_no" + 
    					"      AND m.Journal_status           ='L'" + 
    					"      AND t.cr_dr_indicator          = 'CR'" + 
    					"      and m.created_by_module not in ('SJV') " +
    					"      AND m.accounting_unit_id       =" + cmbAcc_UnitCode +
    					"      AND m.cashbook_year            = " +  txtCB_Year +
    					"      AND m.cashbook_month           = " + txtCB_Month +
    					"      UNION ALL" + 
    					"      SELECT m.accounting_unit_id," + 
    					"        m.cashbook_year," + 
    					"        m.cashbook_month," + 
    					"        t.account_head_Code," + 
    					"        t.amount AS debit," + 
    					"        0        AS credit" + 
    					"      FROM fas_journal_master m," + 
    					"        fas_journal_transaction t" + 
    					"      WHERE t.accounting_unit_id     = m.accounting_unit_id" + 
    					"      AND t.accounting_for_office_id = m.accounting_for_office_id" + 
    					"      AND m.cashbook_year            = t.cashbook_year" + 
    					"      AND m.cashbook_month           = t.cashbook_month" + 
    					"      AND m.voucher_no               = t.voucher_no" + 
    					"      AND m.Journal_status           ='L'" + 
    					"      AND t.cr_dr_indicator          = 'DR'" + 
    					"      and m.created_by_module not in ('SJV') " +
    					"      AND m.accounting_unit_id       =" + cmbAcc_UnitCode +
    					"      AND m.cashbook_year            = " +  txtCB_Year +
    					"      AND m.cashbook_month           = " + txtCB_Month +
    					"      )as sm" + 
    					"    GROUP BY accounting_unit_id," + 
    					"      cashbook_year," + 
    					"      cashbook_month," + 
    					"      account_head_code" + 
    					"    UNION ALL" + 
    					"    SELECT 'Payment' AS Descr," + 
    					"      accounting_unit_id," + 
    					"      cashbook_year     AS YEAR," + 
    					"      cashbook_month    AS MONTH," + 
    					"      account_head_code AS HOA," + 
    					"      SUM(debit)        AS debit," + 
    					"      SUM(credit)       AS credit" + 
    					"    FROM" + 
    					"      (SELECT m.accounting_unit_id," + 
    					"        m.cashbook_year," + 
    					"        m.cashbook_month," + 
    					"        m.account_head_code," + 
    					"        0              AS debit," + 
    					"        m.total_amount AS credit" + 
    					"      FROM fas_payment_master m" + 
    					"      WHERE m.payment_status   ='L'" + 
    					"      AND m.accounting_unit_id =" + cmbAcc_UnitCode +
    					"      AND m.cashbook_year      = " +  txtCB_Year +
    					"      AND m.cashbook_month     =" + txtCB_Month +
    					"      UNION ALL" + 
    					"      SELECT t.accounting_unit_id," + 
    					"        t.cashbook_year," + 
    					"        t.cashbook_month," + 
    					"        t.account_head_code," + 
    					"        t.amount AS debit," + 
    					"        0        AS credit" + 
    					"      FROM fas_payment_transaction t," + 
    					"        fas_payment_master m" + 
    					"      WHERE t.accounting_unit_id     = m.accounting_unit_id" + 
    					"      AND t.accounting_for_office_id = m.accounting_for_office_id" + 
    					"      AND m.cashbook_year            = t.cashbook_year" + 
    					"      AND m.cashbook_month           = t.cashbook_month" + 
    					"      AND m.payment_status           ='L'" + 
    					"      AND m.voucher_no               =t.voucher_no" + 
    					"      AND t.cr_dr_indicator          = 'DR'" + 
    					"      AND m.accounting_unit_id       =" + cmbAcc_UnitCode +
    					"      AND m.cashbook_year            = " +  txtCB_Year +
    					"      AND m.cashbook_month           = " + txtCB_Month +
    					"      UNION ALL" + 
    					"      SELECT t.accounting_unit_id," + 
    					"        t.cashbook_year," + 
    					"        t.cashbook_month," + 
    					"        t.account_head_code," + 
    					"        0        AS debit," + 
    					"        t.amount AS credit" + 
    					"      FROM fas_payment_transaction t," + 
    					"        fas_payment_master m" + 
    					"      WHERE t.accounting_unit_id     = m.accounting_unit_id" + 
    					"      AND t.accounting_for_office_id = m.accounting_for_office_id" + 
    					"      AND m.cashbook_year            = t.cashbook_year" + 
    					"      AND m.cashbook_month           = t.cashbook_month" + 
    					"      AND m.payment_status           ='L'" + 
    					"      AND m.voucher_no               =t.voucher_no" + 
    					"      AND t.cr_dr_indicator          = 'CR'" + 
    					"      AND m.accounting_unit_id       =" + cmbAcc_UnitCode +
    					"      AND m.cashbook_year            = " +  txtCB_Year +
    					"      AND m.cashbook_month           = " + txtCB_Month +
    					"      )as sm" + 
    					"    GROUP BY accounting_unit_id," + 
    					"      cashbook_year," + 
    					"      cashbook_month," + 
    					"      account_head_code" + 
    					"    UNION ALL" + 
    					"    SELECT 'Receipt' AS Descr," + 
    					"      accounting_unit_id," + 
    					"      cashbook_year     AS YEAR," + 
    					"      cashbook_month    AS MONTH," + 
    					"      account_head_code AS HOA," + 
    					"      SUM(debit)        AS debit ," + 
    					"      SUM(credit)       AS credit" + 
    					"    FROM" + 
    					"      (SELECT m.accounting_unit_id," + 
    					"        m.cashbook_year," + 
    					"        m.cashbook_month," + 
    					"        m.account_head_code," + 
    					"        m.total_amount AS debit," + 
    					"        0              AS credit" + 
    					"      FROM fas_receipt_master m" + 
    					"      WHERE m.receipt_status   ='L'" + 
    					"      AND m.accounting_unit_id =" + cmbAcc_UnitCode +
    					"      AND m.cashbook_year      = " +  txtCB_Year +
    					"      AND m.cashbook_month     = " + txtCB_Month +
    					"      UNION ALL" + 
    					"      SELECT t.accounting_unit_id," + 
    					"        t.cashbook_year," + 
    					"        t.cashbook_month," + 
    					"        t.account_head_code," + 
    					"        0        AS debit," + 
    					"        t.amount AS credit" + 
    					"      FROM fas_receipt_transaction t," + 
    					"        fas_receipt_master m" + 
    					"      WHERE t.accounting_unit_id     = m.accounting_unit_id" + 
    					"      AND t.accounting_for_office_id = m.accounting_for_office_id" + 
    					"      AND m.cashbook_year            = t.cashbook_year" + 
    					"      AND m.cashbook_month           = t.cashbook_month" + 
    					"      AND m.receipt_status           ='L'" + 
    					"      AND m.receipt_no               =t.receipt_no" + 
    					"      AND t.cr_dr_indicator          = 'CR'" + 
    					"      AND m.accounting_unit_id       =" + cmbAcc_UnitCode +
    					"      AND m.cashbook_year            = " +  txtCB_Year +
    					"      AND m.cashbook_month           = " + txtCB_Month +
    					"      UNION ALL" + 
    					"      SELECT t.accounting_unit_id," + 
    					"        t.cashbook_year," + 
    					"        t.cashbook_month," + 
    					"        t.account_head_code," + 
    					"        t.amount AS debit," + 
    					"        0        AS credit" + 
    					"      FROM fas_receipt_transaction t," + 
    					"        fas_receipt_master m" + 
    					"      WHERE t.accounting_unit_id     = m.accounting_unit_id" + 
    					"      AND t.accounting_for_office_id = m.accounting_for_office_id" + 
    					"      AND m.cashbook_year            = t.cashbook_year" + 
    					"      AND m.cashbook_month           = t.cashbook_month" + 
    					"      AND m.receipt_status           ='L'" + 
    					"      AND m.receipt_no               =t.receipt_no" + 
    					"      AND t.cr_dr_indicator          = 'DR'" + 
    					"      AND m.accounting_unit_id       =" + cmbAcc_UnitCode +
    					"      AND m.cashbook_year            = " +  txtCB_Year +
    					"      AND m.cashbook_month           = " + txtCB_Month +
    					"      )as sm" + 
    					"    GROUP BY accounting_unit_id," + 
    					"      cashbook_year," + 
    					"      cashbook_month," + 
    					"      account_head_code" + 
    					"    )as sm" + 
    					"  GROUP BY accounting_unit_id," + 
    					"    YEAR," + 
    					"    MONTH," + 
    					"    HOA" + 
    					"  )tr" + 
    					" WHERE tr.accounting_unit_id =" +  cmbAcc_UnitCode +
    					" AND tr.year                 = " +  txtCB_Year +
    					" AND tr.month                = " + txtCB_Month +
    					" AND (tr.debit              !=0 " + 
    					" OR tr.credit            !=0)"  +
    					" AND tr.hoa NOT             IN" + 
    					"  (SELECT account_head_code" + 
    					"  FROM fas_trial_balance" + 
    					"  WHERE accounting_unit_id  =" + cmbAcc_UnitCode +
    					"  AND cashbook_year         = " +  txtCB_Year +
    					"  AND cashbook_month        = " + txtCB_Month +
    					"  AND (current_month_debit != 0" + 
    					"  OR current_month_credit  !=0 )" + 
    					"  ) ";
    			
    			System.out.println("Sql>>>1>>>>"+sql11);
    			
    			ps2 = con.prepareStatement(sql11);
    	   	      rs = ps2.executeQuery();
    	   	      int cnt=0;
    	   	   while (rs.next()) {
    	      	       cnt++;    	        
    	            }
    	            if(cnt==0)
    	            {
    	          	  xml=xml+"<flag>success</flag>";
    	            }
    	            else if(cnt>0)
    	            {
    	          	  xml=xml+"<flag>tr_difference</flag>";
    	            }
    			
    	            //HOA in TB not in TR.txt
    	            
    	            
    		String sql12="SELECT account_head_code," + 
    				"  current_month_debit," + 
    				"  current_month_credit" + 
    				" FROM fas_trial_balance f" + 
    				" WHERE accounting_unit_id     =" + cmbAcc_UnitCode +
    				" AND cashbook_year            = " +  txtCB_Year +
    				" AND cashbook_month           = " + txtCB_Month +
    				" AND (f.CURRENT_MONTH_CREDIT != 0" + 
    				" OR f.CURRENT_MONTH_DEBIT    !=0 )" + 
    				" AND f.ACCOUNT_HEAD_CODE NOT IN" + 
    				"  (SELECT hoa" + 
    				"  FROM" + 
    				"    (SELECT 'tran' AS descr," + 
    				"      accounting_unit_id," + 
    				"      YEAR," + 
    				"      MONTH," + 
    				"      HOA," + 
    				"      SUM(debit)  AS debit," + 
    				"      SUM(Credit) AS credit" + 
    				"    FROM" + 
    				"      (SELECT 'IBT' AS Descr," + 
    				"        accounting_unit_id," + 
    				"        cashbook_year        AS YEAR," + 
    				"        cashbook_month       AS MONTH," + 
    				"        dr_account_head_code AS HOA," + 
    				"        SUM(total_amount)    AS debit," + 
    				"        0                    AS credit" + 
    				"      FROM fas_inter_bank_trf_at_HO" + 
    				"      WHERE transfer_status  ='L'" + 
    				"      AND accounting_unit_id =" + cmbAcc_UnitCode +
    				"      AND cashbook_year      = " +  txtCB_Year +
    				"      AND cashbook_month     = " + txtCB_Month +
    				"      GROUP BY accounting_unit_id," + 
    				"        cashbook_year," + 
    				"        cashbook_month," + 
    				"        dr_account_head_code" + 
    				"      UNION ALL" + 
    				"      SELECT 'IBT' AS Descr," + 
    				"        accounting_unit_id," + 
    				"        cashbook_year        AS YEAR," + 
    				"        cashbook_month       AS MONTH," + 
    				"        cr_account_head_code AS HOA," + 
    				"        0                    AS debit," + 
    				"        SUM(total_amount)    AS credit" + 
    				"      FROM fas_inter_bank_trf_at_HO" + 
    				"      WHERE transfer_status  ='L'" + 
    				"      AND accounting_unit_id =" + cmbAcc_UnitCode +
    				"      AND cashbook_year      = " +  txtCB_Year +
    				"      AND cashbook_month     = " + txtCB_Month +
    				"      GROUP BY accounting_unit_id," + 
    				"        cashbook_year," + 
    				"        cashbook_month," + 
    				"        cr_account_head_code" + 
    				"      UNION ALL" + 
    				"      SELECT 'FROff' AS Descr," + 
    				"        accounting_unit_id," + 
    				"        cashbook_year        AS YEAR," + 
    				"        cashbook_month       AS MONTH," + 
    				"        dr_account_head_code AS HOA," + 
    				"        SUM(total_amount)    AS debit," + 
    				"        0                    AS credit" + 
    				"      FROM fas_fund_receipt_by_office" + 
    				"      WHERE receipt_status   ='L'" + 
    				"      AND accounting_unit_id =" + cmbAcc_UnitCode +
    				"      AND cashbook_year      = " +  txtCB_Year +
    				"      AND cashbook_month     = " + txtCB_Month +
    				"      GROUP BY accounting_unit_id," + 
    				"        cashbook_year," + 
    				"        cashbook_month," + 
    				"        dr_account_head_code" + 
    				"      UNION ALL" + 
    				"      SELECT 'FROff' AS Descr," + 
    				"        accounting_unit_id," + 
    				"        cashbook_year        AS YEAR," + 
    				"        cashbook_month       AS MONTH," + 
    				"        cr_account_head_code AS HOA," + 
    				"        0                    AS debit," + 
    				"        SUM(total_amount)    AS credit" + 
    				"      FROM fas_fund_receipt_by_office" + 
    				"      WHERE receipt_status   ='L'" + 
    				"      AND accounting_unit_id =" + cmbAcc_UnitCode +
    				"      AND cashbook_year      = " +  txtCB_Year +
    				"      AND cashbook_month     = " + txtCB_Month +
    				"      GROUP BY accounting_unit_id," + 
    				"        cashbook_year," + 
    				"        cashbook_month," + 
    				"        cr_account_head_code" + 
    				"      UNION ALL" + 
    				"      SELECT 'FTOff' AS Descr," + 
    				"        accounting_unit_id," + 
    				"        cashbook_year        AS YEAR," + 
    				"        cashbook_month       AS MONTH," + 
    				"        dr_account_head_code AS HOA," + 
    				"        SUM(total_amount)    AS debit," + 
    				"        0                    AS credit" + 
    				"      FROM Fas_fund_Trf_from_office" + 
    				"      WHERE transfer_status  ='L'" + 
    				"      AND accounting_unit_id =" + cmbAcc_UnitCode +
    				"      AND cashbook_year      = " +  txtCB_Year +
    				"      AND cashbook_month     = " + txtCB_Month +
    				"      GROUP BY accounting_unit_id," + 
    				"        cashbook_year," + 
    				"        cashbook_month," + 
    				"        dr_account_head_code" + 
    				"      UNION ALL" + 
    				"      SELECT 'FTOff' AS Descr," + 
    				"        accounting_unit_id," + 
    				"        cashbook_year        AS YEAR," + 
    				"        cashbook_month       AS MONTH," + 
    				"        cr_account_head_code AS HOA," + 
    				"        0                    AS debit," + 
    				"        SUM(total_amount)    AS credit" + 
    				"      FROM Fas_fund_Trf_from_office" + 
    				"      WHERE transfer_status  ='L'" + 
    				"      AND accounting_unit_id =" + cmbAcc_UnitCode +
    				"      AND cashbook_year      = " +  txtCB_Year +
    				"      AND cashbook_month     = " + txtCB_Month +
    				"      GROUP BY accounting_unit_id," + 
    				"        cashbook_year," + 
    				"        cashbook_month," + 
    				"        cr_account_head_code" + 
    				"      UNION ALL" + 
    				"      SELECT 'FTHO' AS Descr," + 
    				"        accounting_unit_id," + 
    				"        cashbook_year     AS YEAR," + 
    				"        cashbook_month    AS MONTH," + 
    				"        account_head_code AS HOA," + 
    				"        0                 AS debit," + 
    				"        SUM(total_amount) AS credit" + 
    				"      FROM FAS_FUND_TRF_From_ho_master" + 
    				"      WHERE transfer_status  ='L'" + 
    				"      AND accounting_unit_id =" + cmbAcc_UnitCode +
    				"      AND cashbook_year      = " +  txtCB_Year +
    				"      AND cashbook_month     = " + txtCB_Month +
    				"      AND cr_dr_indicator    ='CR'" + 
    				"      GROUP BY accounting_unit_id," + 
    				"        cashbook_year," + 
    				"        cashbook_month," + 
    				"        account_head_code" + 
    				"      UNION ALL" + 
    				"      SELECT 'FTHO' AS Descr," + 
    				"        accounting_unit_id," + 
    				"        cashbook_year     AS YEAR," + 
    				"        cashbook_month    AS MONTH," + 
    				"        account_head_code AS HOA," + 
    				"        0                 AS debit," + 
    				"        SUM(amount)       AS credit" + 
    				"      FROM FAS_FUND_TRF_From_ho_trn" + 
    				"      WHERE accounting_unit_id =" + cmbAcc_UnitCode +
    				"      AND cashbook_year        = " +  txtCB_Year +
    				"      AND cashbook_month       = " + txtCB_Month +
    				"      AND cr_dr_indicator      ='DR'" + 
    				"      AND voucher_no          IN" + 
    				"        (SELECT voucher_no" + 
    				"        FROM FAS_FUND_TRF_From_ho_master" + 
    				"        WHERE transfer_status  ='L'" + 
    				"        AND accounting_unit_id =" + cmbAcc_UnitCode +
    				"        AND cashbook_year      = " +  txtCB_Year +
    				"        AND cashbook_month     = " + txtCB_Month +
    				"        AND cr_dr_indicator    ='CR'" + 
    				"        )" + 
    				"      GROUP BY accounting_unit_id," + 
    				"        cashbook_year," + 
    				"        cashbook_month," + 
    				"        account_head_code" + 
    				"      UNION ALL" + 
    				"      SELECT 'FRHO' AS Descr," + 
    				"        accounting_unit_id," + 
    				"        cashbook_year        AS YEAR," + 
    				"        cashbook_month       AS MONTH," + 
    				"        dr_account_head_code AS HOA," + 
    				"        SUM(total_amount)    AS debit," + 
    				"        0                    AS credit" + 
    				"      FROM fas_fund_receipt_by_HO" + 
    				"      WHERE receipt_status   ='L'" + 
    				"      AND accounting_unit_id =" + cmbAcc_UnitCode +
    				"      AND cashbook_year      = " + txtCB_Year + 
    				"      AND cashbook_month     = " + txtCB_Month +
    				"      GROUP BY accounting_unit_id," + 
    				"        cashbook_year," + 
    				"        cashbook_month," + 
    				"        dr_account_head_code" + 
    				"      UNION ALL" + 
    				"      SELECT 'FRHO' AS Descr," + 
    				"        accounting_unit_id," + 
    				"        cashbook_year        AS YEAR," + 
    				"        cashbook_month       AS MONTH," + 
    				"        cr_account_head_code AS HOA," + 
    				"        0                    AS debit," + 
    				"        SUM(total_amount)    AS credit" + 
    				"      FROM fas_fund_receipt_by_HO" + 
    				"      WHERE receipt_status   ='L'" + 
    				"      AND accounting_unit_id =" + cmbAcc_UnitCode +
    				"      AND cashbook_year      = " +  txtCB_Year +
    				"      AND cashbook_month     = " + txtCB_Month +
    				"      GROUP BY accounting_unit_id," + 
    				"        cashbook_year," + 
    				"        cashbook_month," + 
    				"        cr_account_head_code" + 
    				"      UNION ALL" + 
    				"      SELECT 'Journal' AS Descr," + 
    				"        accounting_unit_id," + 
    				"        cashbook_year     AS YEAR," + 
    				"        cashbook_month    AS MONTH," + 
    				"        account_head_code AS HOA," + 
    				"        SUM(debit)        AS debit ," + 
    				"        SUM(credit)       AS credit" + 
    				"      FROM" + 
    				"        (SELECT m.accounting_unit_id," + 
    				"          m.cashbook_year," + 
    				"          m.cashbook_month," + 
    				"          t.account_head_code ," + 
    				"          0        AS debit," + 
    				"          t.amount AS credit" + 
    				"        FROM fas_journal_master m," + 
    				"          fas_journal_transaction t" + 
    				"        WHERE t.accounting_unit_id     = m.accounting_unit_id" + 
    				"        AND t.accounting_for_office_id = m.accounting_for_office_id" + 
    				"        AND m.cashbook_year            = t.cashbook_year" + 
    				"        AND m.cashbook_month           = t.cashbook_month" + 
    				"        AND m.voucher_no               = t.voucher_no" + 
    				"        AND m.Journal_status           ='L'" + 
    				"        AND t.cr_dr_indicator          = 'CR'" + 
    				"        AND m.CREATED_BY_MODULE not in ('SJV') " +
    				"        AND m.accounting_unit_id       =" + cmbAcc_UnitCode +
    				"        AND m.cashbook_year            = " +  txtCB_Year +
    				"        AND m.cashbook_month           = " + txtCB_Month +
    				"        UNION ALL" + 
    				"        SELECT m.accounting_unit_id," + 
    				"          m.cashbook_year," + 
    				"          m.cashbook_month," + 
    				"          t.account_head_Code," + 
    				"          t.amount AS debit," + 
    				"          0        AS credit" + 
    				"        FROM fas_journal_master m," + 
    				"          fas_journal_transaction t" + 
    				"        WHERE t.accounting_unit_id     = m.accounting_unit_id" + 
    				"        AND t.accounting_for_office_id = m.accounting_for_office_id" + 
    				"        AND m.cashbook_year            = t.cashbook_year" + 
    				"        AND m.cashbook_month           = t.cashbook_month" + 
    				"        AND m.voucher_no               = t.voucher_no" + 
    				"        AND m.Journal_status           ='L'" + 
    				"        AND t.cr_dr_indicator          = 'DR'" + 
    				"        AND m.CREATED_BY_MODULE not in ('SJV') " +
    				"        AND m.accounting_unit_id       =" + cmbAcc_UnitCode +
    				"        AND m.cashbook_year            = " +  txtCB_Year +
    				"        AND m.cashbook_month           = " + txtCB_Month +
    				"        )as sm" + 
    				"      GROUP BY accounting_unit_id," + 
    				"        cashbook_year," + 
    				"        cashbook_month," + 
    				"        account_head_code" + 
    				"      UNION ALL" + 
    				"      SELECT 'Payment' AS Descr," + 
    				"        accounting_unit_id," + 
    				"        cashbook_year     AS YEAR," + 
    				"        cashbook_month    AS MONTH," + 
    				"        account_head_code AS HOA," + 
    				"        SUM(debit)        AS debit," + 
    				"        SUM(credit)       AS credit" + 
    				"      FROM" + 
    				"        (SELECT m.accounting_unit_id," + 
    				"          m.cashbook_year," + 
    				"          m.cashbook_month," + 
    				"          m.account_head_code," + 
    				"          0              AS debit," + 
    				"          m.total_amount AS credit" + 
    				"        FROM fas_payment_master m" + 
    				"        WHERE m.payment_status   ='L'" + 
    				"        AND m.accounting_unit_id =" + cmbAcc_UnitCode +
    				"        AND m.cashbook_year      = " +  txtCB_Year +
    				"        AND m.cashbook_month     = " + txtCB_Month +
    				"        UNION ALL" + 
    				"        SELECT t.accounting_unit_id," + 
    				"          t.cashbook_year," + 
    				"          t.cashbook_month," + 
    				"          t.account_head_code," + 
    				"          t.amount AS debit," + 
    				"          0        AS credit" + 
    				"        FROM fas_payment_transaction t," + 
    				"          fas_payment_master m" + 
    				"        WHERE t.accounting_unit_id     = m.accounting_unit_id" + 
    				"        AND t.accounting_for_office_id = m.accounting_for_office_id" + 
    				"        AND m.cashbook_year            = t.cashbook_year" + 
    				"        AND m.cashbook_month           = t.cashbook_month" + 
    				"        AND m.payment_status           ='L'" + 
    				"        AND m.voucher_no               =t.voucher_no" + 
    				"        AND t.cr_dr_indicator          = 'DR'" + 
    				"        AND m.accounting_unit_id       =" + cmbAcc_UnitCode +
    				"        AND m.cashbook_year            = " +  txtCB_Year +
    				"        AND m.cashbook_month           = " + txtCB_Month +
    				"        UNION ALL" + 
    				"        SELECT t.accounting_unit_id," + 
    				"          t.cashbook_year," + 
    				"          t.cashbook_month," + 
    				"          t.account_head_code," + 
    				"          0        AS debit," + 
    				"          t.amount AS credit" + 
    				"        FROM fas_payment_transaction t," + 
    				"          fas_payment_master m" + 
    				"        WHERE t.accounting_unit_id     = m.accounting_unit_id" + 
    				"        AND t.accounting_for_office_id = m.accounting_for_office_id" + 
    				"        AND m.cashbook_year            = t.cashbook_year" + 
    				"        AND m.cashbook_month           = t.cashbook_month" + 
    				"        AND m.payment_status           ='L'" + 
    				"        AND m.voucher_no               =t.voucher_no" + 
    				"        AND t.cr_dr_indicator          = 'CR'" + 
    				"        AND m.accounting_unit_id       =" + cmbAcc_UnitCode +
    				"        AND m.cashbook_year            = " +  txtCB_Year +
    				"        AND m.cashbook_month           = " + txtCB_Month +
    				"        )as sm" + 
    				"      GROUP BY accounting_unit_id," + 
    				"        cashbook_year," + 
    				"        cashbook_month," + 
    				"        account_head_code" + 
    				"      UNION ALL" + 
    				"      SELECT 'Receipt' AS Descr," + 
    				"        accounting_unit_id," + 
    				"        cashbook_year     AS YEAR," + 
    				"        cashbook_month    AS MONTH," + 
    				"        account_head_code AS HOA," + 
    				"        SUM(debit)        AS debit ," + 
    				"        SUM(credit)       AS credit" + 
    				"      FROM" + 
    				"        (SELECT m.accounting_unit_id," + 
    				"          m.cashbook_year," + 
    				"          m.cashbook_month," + 
    				"          m.account_head_code," + 
    				"          m.total_amount AS debit," + 
    				"          0              AS credit" + 
    				"        FROM fas_receipt_master m" + 
    				"        WHERE m.receipt_status   ='L'" + 
    				"        AND m.accounting_unit_id =" + cmbAcc_UnitCode +
    				"        AND m.cashbook_year      = " +  txtCB_Year +
    				"        AND m.cashbook_month     = " + txtCB_Month +
    				"        UNION ALL" + 
    				"        SELECT t.accounting_unit_id," + 
    				"          t.cashbook_year," + 
    				"          t.cashbook_month," + 
    				"          t.account_head_code," + 
    				"          0        AS debit," + 
    				"          t.amount AS credit" + 
    				"        FROM fas_receipt_transaction t," + 
    				"          fas_receipt_master m" + 
    				"        WHERE t.accounting_unit_id     = m.accounting_unit_id" + 
    				"        AND t.accounting_for_office_id = m.accounting_for_office_id" + 
    				"        AND m.cashbook_year            = t.cashbook_year" + 
    				"        AND m.cashbook_month           = t.cashbook_month" + 
    				"        AND m.receipt_status           ='L'" + 
    				"        AND m.receipt_no               =t.receipt_no" + 
    				"        AND t.cr_dr_indicator          = 'CR'" + 
    				"        AND m.accounting_unit_id       =" + cmbAcc_UnitCode +
    				"        AND m.cashbook_year            = " +  txtCB_Year +
    				"        AND m.cashbook_month           = " + txtCB_Month +
    				"        UNION ALL" + 
    				"        SELECT t.accounting_unit_id," + 
    				"          t.cashbook_year," + 
    				"          t.cashbook_month," + 
    				"          t.account_head_code," + 
    				"          t.amount AS debit," + 
    				"          0        AS credit" + 
    				"        FROM fas_receipt_transaction t," + 
    				"          fas_receipt_master m" + 
    				"        WHERE t.accounting_unit_id     = m.accounting_unit_id" + 
    				"        AND t.accounting_for_office_id = m.accounting_for_office_id" + 
    				"        AND m.cashbook_year            = t.cashbook_year" + 
    				"        AND m.cashbook_month           = t.cashbook_month" + 
    				"        AND m.receipt_status           ='L'" + 
    				"        AND m.receipt_no               =t.receipt_no" + 
    				"        AND t.cr_dr_indicator          = 'DR'" + 
    				"        AND m.accounting_unit_id       =" + cmbAcc_UnitCode +
    				"        AND m.cashbook_year            = " +  txtCB_Year +
    				"        AND m.cashbook_month           = " + txtCB_Month +
    				"        )as sm" + 
    				"      GROUP BY accounting_unit_id," + 
    				"        cashbook_year," + 
    				"        cashbook_month," + 
    				"        account_head_code" + 
    				"      )as sm" + 
    				"    GROUP BY accounting_unit_id," + 
    				"      YEAR," + 
    				"      MONTH," + 
    				"      HOA" + 
    				"    )tr" + 
    				"  WHERE tr.accounting_unit_id = " + cmbAcc_UnitCode +
    				"  AND tr.year                 =" +  txtCB_Year +
    				"  AND tr.month                =" + txtCB_Month +
    				"  )";
    		System.out.println("Sql>>>>2>>>"+sql12);
    		PreparedStatement ps21 = con.prepareStatement(sql12);
	   	      rs = ps21.executeQuery();
	   	      int cnt1=0;
	   	   while (rs.next()) {
	      	       cnt1++;    	        
	            }
	            if(cnt1==0)
	            {
	          	  xml=xml+"<flag>success</flag>";
	            }
	            else if(cnt1>0)
	            {
	          	  xml=xml+"<flag>tb_difference</flag>";
	            }
	            
	            String sql13="SELECT *" + 
	            		" FROM" + 
	            		"  (SELECT tr.accounting_unit_id," + 
	            		"    tr.year," + 
	            		"    tr.month," + 
	            		"    tr.hoa," + 
	            		"    tr.debit - tb.debit AS debitdiff," + 
	            		"    tr.credit-tb.credit AS creditdiff" + 
	            		"  FROM" + 
	            		"    (SELECT 'TB' AS Descr," + 
	            		"      g.accounting_unit_id," + 
	            		"      g.cashbook_year             AS YEAR ," + 
	            		"      g.cashbook_month            AS MONTH," + 
	            		"      g.account_head_code         AS HOA," + 
	            		"      SUM(g.current_month_debit)  AS debit ," + 
	            		"      SUM(g.current_month_credit) AS credit" + 
	            		"    FROM fas_trial_balance g" + 
	            		"    WHERE g.accounting_unit_id =" + cmbAcc_UnitCode +  
	            		"    AND g.cashbook_year        = " + txtCB_Year + 
	            		"    AND g.cashbook_month       = " + txtCB_Month +
	            		"    GROUP BY g.accounting_unit_id," + 
	            		"      g.cashbook_year," + 
	            		"      g.cashbook_month," + 
	            		"      g.account_head_code" + 
	            		"    ) tb," + 
	            		"    (SELECT 'tran' AS descr," + 
	            		"      accounting_unit_id," + 
	            		"      YEAR," + 
	            		"      MONTH," + 
	            		"      HOA," + 
	            		"      SUM(debit)  AS debit," + 
	            		"      SUM(Credit) AS credit" + 
	            		"    FROM" + 
	            		"      (SELECT 'IBT' AS Descr," + 
	            		"        accounting_unit_id," + 
	            		"        cashbook_year        AS YEAR," + 
	            		"        cashbook_month       AS MONTH," + 
	            		"        dr_account_head_code AS HOA," + 
	            		"        SUM(total_amount)    AS debit," + 
	            		"        0                    AS credit" + 
	            		"      FROM fas_inter_bank_trf_at_HO" + 
	            		"      WHERE transfer_status  ='L'" + 
	            		"      AND accounting_unit_id =" + cmbAcc_UnitCode +  
	            		"      AND cashbook_year      = " + txtCB_Year + 
	            		"      AND cashbook_month     = " + txtCB_Month +
	            		"      GROUP BY accounting_unit_id," + 
	            		"        cashbook_year," + 
	            		"        cashbook_month," + 
	            		"        dr_account_head_code" + 
	            		"      UNION ALL" + 
	            		"      SELECT 'IBT' AS Descr," + 
	            		"        accounting_unit_id," + 
	            		"        cashbook_year        AS YEAR," + 
	            		"        cashbook_month       AS MONTH," + 
	            		"        cr_account_head_code AS HOA," + 
	            		"        0                    AS debit," + 
	            		"        SUM(total_amount)    AS credit" + 
	            		"      FROM fas_inter_bank_trf_at_HO" + 
	            		"      WHERE transfer_status  ='L'" + 
	            		"      AND accounting_unit_id =" + cmbAcc_UnitCode +  
	            		"      AND cashbook_year      = " +  txtCB_Year +
	            		"      AND cashbook_month     = " + txtCB_Month +
	            		"      GROUP BY accounting_unit_id," + 
	            		"        cashbook_year," + 
	            		"        cashbook_month," + 
	            		"        cr_account_head_code" + 
	            		"      UNION ALL" + 
	            		"      SELECT 'FROff' AS Descr," + 
	            		"        accounting_unit_id," + 
	            		"        cashbook_year        AS YEAR," + 
	            		"        cashbook_month       AS MONTH," + 
	            		"        dr_account_head_code AS HOA," + 
	            		"        SUM(total_amount)    AS debit," + 
	            		"        0                    AS credit" + 
	            		"      FROM fas_fund_receipt_by_office" + 
	            		"      WHERE receipt_status   ='L'" + 
	            		"      AND accounting_unit_id =" + cmbAcc_UnitCode +   
	            		"      AND cashbook_year      = " + txtCB_Year +
	            		"      AND cashbook_month     = " + txtCB_Month +
	            		"      GROUP BY accounting_unit_id," + 
	            		"        cashbook_year," + 
	            		"        cashbook_month," + 
	            		"        dr_account_head_code" + 
	            		"      UNION ALL" + 
	            		"      SELECT 'FROff' AS Descr," + 
	            		"        accounting_unit_id," + 
	            		"        cashbook_year        AS YEAR," + 
	            		"        cashbook_month       AS MONTH," + 
	            		"        cr_account_head_code AS HOA," + 
	            		"        0                    AS debit," + 
	            		"        SUM(total_amount)    AS credit" + 
	            		"      FROM fas_fund_receipt_by_office" + 
	            		"      WHERE receipt_status   ='L'" + 
	            		"      AND accounting_unit_id =" + cmbAcc_UnitCode + 
	            		"      AND cashbook_year      = " +  txtCB_Year +
	            		"      AND cashbook_month     = " +  txtCB_Month +
	            		"      GROUP BY accounting_unit_id," + 
	            		"        cashbook_year," + 
	            		"        cashbook_month," + 
	            		"        cr_account_head_code" + 
	            		"      UNION ALL" + 
	            		"      SELECT 'FTOff' AS Descr," + 
	            		"        accounting_unit_id," + 
	            		"        cashbook_year        AS YEAR," + 
	            		"        cashbook_month       AS MONTH," + 
	            		"        dr_account_head_code AS HOA," + 
	            		"        SUM(total_amount)    AS debit," + 
	            		"        0                    AS credit" + 
	            		"      FROM Fas_fund_Trf_from_office" + 
	            		"      WHERE transfer_status  ='L'" + 
	            		"      AND accounting_unit_id =" + cmbAcc_UnitCode + 
	            		"      AND cashbook_year      = " + txtCB_Year +
	            		"      AND cashbook_month     = " + txtCB_Month +
	            		"      GROUP BY accounting_unit_id," + 
	            		"        cashbook_year," + 
	            		"        cashbook_month," + 
	            		"        dr_account_head_code" + 
	            		"      UNION ALL" + 
	            		"      SELECT 'FTOff' AS Descr," + 
	            		"        accounting_unit_id," + 
	            		"        cashbook_year        AS YEAR," + 
	            		"        cashbook_month       AS MONTH," + 
	            		"        cr_account_head_code AS HOA," + 
	            		"        0                    AS debit," + 
	            		"        SUM(total_amount)    AS credit" + 
	            		"      FROM Fas_fund_Trf_from_office" + 
	            		"      WHERE transfer_status  ='L'" + 
	            		"      AND accounting_unit_id =" + cmbAcc_UnitCode +  
	            		"      AND cashbook_year      = " +  txtCB_Year +
	            		"      AND cashbook_month     = " + txtCB_Month +
	            		"      GROUP BY accounting_unit_id," + 
	            		"        cashbook_year," + 
	            		"        cashbook_month," + 
	            		"        cr_account_head_code" + 
	            		"      UNION ALL" + 
	            		"      SELECT 'FTHO' AS Descr," + 
	            		"        accounting_unit_id," + 
	            		"        cashbook_year     AS YEAR," + 
	            		"        cashbook_month    AS MONTH," + 
	            		"        account_head_code AS HOA," + 
	            		"        0                 AS debit," + 
	            		"        SUM(total_amount) AS credit" + 
	            		"      FROM FAS_FUND_TRF_From_ho_master" + 
	            		"      WHERE transfer_status  ='L'" + 
	            		"      AND accounting_unit_id =" + cmbAcc_UnitCode +   
	            		"      AND cashbook_year      = " + txtCB_Year +
	            		"      AND cashbook_month     = " + txtCB_Month +
	            		"      AND cr_dr_indicator    ='CR'" + 
	            		"      GROUP BY accounting_unit_id," + 
	            		"        cashbook_year," + 
	            		"        cashbook_month," + 
	            		"        account_head_code" + 
	            		"      UNION ALL" + 
	            		"      SELECT 'FTHO' AS Descr," + 
	            		"        accounting_unit_id," + 
	            		"        cashbook_year     AS YEAR," + 
	            		"        cashbook_month    AS MONTH," + 
	            		"        account_head_code AS HOA," + 
	            		"        SUM(amount)       AS debit," + 
	            		"        0                 AS credit" + 
	            		"      FROM FAS_FUND_TRF_From_ho_trn" + 
	            		"      WHERE accounting_unit_id =" + cmbAcc_UnitCode +   
	            		"      AND cashbook_year        = " + txtCB_Year +
	            		"      AND cashbook_month       = " + txtCB_Month +
	            		"      AND cr_dr_indicator      ='DR'" + 
	            		"      AND voucher_no          IN" + 
	            		"        (SELECT voucher_no" + 
	            		"        FROM FAS_FUND_TRF_From_ho_master" + 
	            		"        WHERE transfer_status  ='L'" + 
	            		"        AND accounting_unit_id =" + cmbAcc_UnitCode +  
	            		"        AND cashbook_year      = " + txtCB_Year + 
	            		"        AND cashbook_month     = " + txtCB_Month +
	            		"        AND cr_dr_indicator    ='CR'" + 
	            		"        )" + 
	            		"      GROUP BY accounting_unit_id," + 
	            		"        cashbook_year," + 
	            		"        cashbook_month," + 
	            		"        account_head_code" + 
	            		"      UNION ALL" + 
	            		"      SELECT 'FRHO' AS Descr," + 
	            		"        accounting_unit_id," + 
	            		"        cashbook_year        AS YEAR," + 
	            		"        cashbook_month       AS MONTH," + 
	            		"        dr_account_head_code AS HOA," + 
	            		"        SUM(total_amount)    AS debit," + 
	            		"        0                    AS credit" + 
	            		"      FROM fas_fund_receipt_by_HO" + 
	            		"      WHERE receipt_status   ='L'" + 
	            		"      AND accounting_unit_id =" + cmbAcc_UnitCode + 
	            		"      AND cashbook_year      = " +  txtCB_Year + 
	            		"      AND cashbook_month     = " + txtCB_Month +
	            		"      GROUP BY accounting_unit_id," + 
	            		"        cashbook_year," + 
	            		"        cashbook_month," + 
	            		"        dr_account_head_code" + 
	            		"      UNION ALL" + 
	            		"      SELECT 'FRHO' AS Descr," + 
	            		"        accounting_unit_id," + 
	            		"        cashbook_year        AS YEAR," + 
	            		"        cashbook_month       AS MONTH," + 
	            		"        cr_account_head_code AS HOA," + 
	            		"        0                    AS debit," + 
	            		"        SUM(total_amount)    AS credit" + 
	            		"      FROM fas_fund_receipt_by_HO" + 
	            		"      WHERE receipt_status   ='L'" + 
	            		"      AND accounting_unit_id =" + cmbAcc_UnitCode +  
	            		"      AND cashbook_year      = " + txtCB_Year + 
	            		"      AND cashbook_month     = " + txtCB_Month +
	            		"      GROUP BY accounting_unit_id," + 
	            		"        cashbook_year," + 
	            		"        cashbook_month," + 
	            		"        cr_account_head_code" + 
	            		"      UNION ALL" + 
	            		"      SELECT 'Journal' AS Descr," + 
	            		"        accounting_unit_id," + 
	            		"        cashbook_year     AS YEAR," + 
	            		"        cashbook_month    AS MONTH," + 
	            		"        account_head_code AS HOA," + 
	            		"        SUM(debit)        AS debit ," + 
	            		"        SUM(credit)       AS credit" + 
	            		"      FROM" + 
	            		"        (SELECT m.accounting_unit_id," + 
	            		"          m.cashbook_year," + 
	            		"          m.cashbook_month," + 
	            		"          t.account_head_code ," + 
	            		"          0        AS debit," + 
	            		"          t.amount AS credit" + 
	            		"        FROM fas_journal_master m," + 
	            		"          fas_journal_transaction t" + 
	            		"        WHERE t.accounting_unit_id     = m.accounting_unit_id" + 
	            		"        AND t.accounting_for_office_id = m.accounting_for_office_id" + 
	            		"        AND m.cashbook_year            = t.cashbook_year" + 
	            		"        AND m.cashbook_month           = t.cashbook_month" + 
	            		"        AND m.voucher_no               = t.voucher_no" + 
	            		"        AND m.Journal_status           ='L'" + 
	            		"        AND t.cr_dr_indicator          = 'CR'" + 
	            		"        AND m.CREATED_BY_MODULE not in ('SJV') " +
	            		"        AND m.accounting_unit_id       =" + cmbAcc_UnitCode +  
	            		"        AND m.cashbook_year            = " + txtCB_Year +
	            		"        AND m.cashbook_month           = " +  txtCB_Month +
	            		"        UNION ALL" + 
	            		"        SELECT m.accounting_unit_id," + 
	            		"          m.cashbook_year," + 
	            		"          m.cashbook_month," + 
	            		"          t.account_head_Code," + 
	            		"          t.amount AS debit," + 
	            		"          0        AS credit" + 
	            		"        FROM fas_journal_master m," + 
	            		"          fas_journal_transaction t" + 
	            		"        WHERE t.accounting_unit_id     = m.accounting_unit_id" + 
	            		"        AND t.accounting_for_office_id = m.accounting_for_office_id" + 
	            		"        AND m.cashbook_year            = t.cashbook_year" + 
	            		"        AND m.cashbook_month           = t.cashbook_month" + 
	            		"        AND m.voucher_no               = t.voucher_no" + 
	            		"        AND m.Journal_status           ='L'" + 
	            		"        AND t.cr_dr_indicator          = 'DR'" + 
	            		"    AND m.CREATED_BY_MODULE not in ('SJV') " +
	            		"        AND m.accounting_unit_id       =" + cmbAcc_UnitCode +  
	            		"        AND m.cashbook_year            = " + txtCB_Year + 
	            		"        AND m.cashbook_month           = " + txtCB_Month +
	            		"        )as sm" + 
	            		"      GROUP BY accounting_unit_id," + 
	            		"        cashbook_year," + 
	            		"        cashbook_month," + 
	            		"        account_head_code" + 
	            		"      UNION ALL" + 
	            		"      SELECT 'Payment' AS Descr," + 
	            		"        accounting_unit_id," + 
	            		"        cashbook_year     AS YEAR," + 
	            		"        cashbook_month    AS MONTH," + 
	            		"        account_head_code AS HOA," + 
	            		"        SUM(debit)        AS debit," + 
	            		"        SUM(credit)       AS credit" + 
	            		"      FROM" + 
	            		"        (SELECT m.accounting_unit_id," + 
	            		"          m.cashbook_year," + 
	            		"          m.cashbook_month," + 
	            		"          m.account_head_code," + 
	            		"          0              AS debit," + 
	            		"          m.total_amount AS credit" + 
	            		"        FROM fas_payment_master m" + 
	            		"        WHERE m.payment_status   ='L'" + 
	            		"        AND m.accounting_unit_id =" + cmbAcc_UnitCode +  
	            		"        AND m.cashbook_year      = " + txtCB_Year + 
	            		"        AND m.cashbook_month     = " + txtCB_Month +
	            		"        UNION ALL" + 
	            		"        SELECT t.accounting_unit_id," + 
	            		"          t.cashbook_year," + 
	            		"          t.cashbook_month," + 
	            		"          t.account_head_code," + 
	            		"          t.amount AS debit," + 
	            		"          0        AS credit" + 
	            		"        FROM fas_payment_transaction t," + 
	            		"          fas_payment_master m" + 
	            		"        WHERE t.accounting_unit_id     = m.accounting_unit_id" + 
	            		"        AND t.accounting_for_office_id = m.accounting_for_office_id" + 
	            		"        AND m.cashbook_year            = t.cashbook_year" + 
	            		"        AND m.cashbook_month           = t.cashbook_month" + 
	            		"        AND m.payment_status           ='L'" + 
	            		"        AND m.voucher_no               =t.voucher_no" + 
	            		"        AND t.cr_dr_indicator          = 'DR'" + 
	            		"        AND m.accounting_unit_id       =" + cmbAcc_UnitCode + 
	            		"        AND m.cashbook_year            = " +  txtCB_Year + 
	            		"        AND m.cashbook_month           = " + txtCB_Month +
	            		"        UNION ALL" + 
	            		"        SELECT t.accounting_unit_id," + 
	            		"          t.cashbook_year," + 
	            		"          t.cashbook_month," + 
	            		"          t.account_head_code," + 
	            		"          0        AS debit," + 
	            		"          t.amount AS credit" + 
	            		"        FROM fas_payment_transaction t," + 
	            		"          fas_payment_master m" + 
	            		"        WHERE t.accounting_unit_id     = m.accounting_unit_id" + 
	            		"        AND t.accounting_for_office_id = m.accounting_for_office_id" + 
	            		"        AND m.cashbook_year            = t.cashbook_year" + 
	            		"        AND m.cashbook_month           = t.cashbook_month" + 
	            		"        AND m.payment_status           ='L'" + 
	            		"        AND m.voucher_no               =t.voucher_no" + 
	            		"        AND t.cr_dr_indicator          = 'CR'" + 
	            		"        AND m.accounting_unit_id       =" + cmbAcc_UnitCode +  
	            		"        AND m.cashbook_year            = " + txtCB_Year + 
	            		"        AND m.cashbook_month           = " + txtCB_Month +
	            		"        )as sm" + 
	            		"      GROUP BY accounting_unit_id," + 
	            		"        cashbook_year," + 
	            		"        cashbook_month," + 
	            		"        account_head_code" + 
	            		"      UNION ALL" + 
	            		"      SELECT 'Receipt' AS Descr," + 
	            		"        accounting_unit_id," + 
	            		"        cashbook_year     AS YEAR," + 
	            		"        cashbook_month    AS MONTH," + 
	            		"        account_head_code AS HOA," + 
	            		"        SUM(debit)        AS debit ," + 
	            		"        SUM(credit)       AS credit" + 
	            		"      FROM" + 
	            		"        (SELECT m.accounting_unit_id," + 
	            		"          m.cashbook_year," + 
	            		"          m.cashbook_month," + 
	            		"          m.account_head_code," + 
	            		"          m.total_amount AS debit," + 
	            		"          0              AS credit" + 
	            		"        FROM fas_receipt_master m" + 
	            		"        WHERE m.receipt_status   ='L'" + 
	            		"        AND m.accounting_unit_id =" + cmbAcc_UnitCode +  
	            		"        AND m.cashbook_year      = " + txtCB_Year + 
	            		"        AND m.cashbook_month     = " + txtCB_Month +
	            		"        UNION ALL" + 
	            		"        SELECT t.accounting_unit_id," + 
	            		"          t.cashbook_year," + 
	            		"          t.cashbook_month," + 
	            		"          t.account_head_code," + 
	            		"          0        AS debit," + 
	            		"          t.amount AS credit" + 
	            		"        FROM fas_receipt_transaction t," + 
	            		"          fas_receipt_master m" + 
	            		"        WHERE t.accounting_unit_id     = m.accounting_unit_id" + 
	            		"        AND t.accounting_for_office_id = m.accounting_for_office_id" + 
	            		"        AND m.cashbook_year            = t.cashbook_year" + 
	            		"        AND m.cashbook_month           = t.cashbook_month" + 
	            		"        AND m.receipt_status           ='L'" + 
	            		"        AND m.receipt_no               =t.receipt_no" + 
	            		"        AND t.cr_dr_indicator          = 'CR'" + 
	            		"        AND m.accounting_unit_id       =" + cmbAcc_UnitCode +  
	            		"        AND m.cashbook_year            = " + txtCB_Year + 
	            		"        AND m.cashbook_month           = " + txtCB_Month +
	            		"        UNION ALL" + 
	            		"        SELECT t.accounting_unit_id," + 
	            		"          t.cashbook_year," + 
	            		"          t.cashbook_month," + 
	            		"          t.account_head_code," + 
	            		"          t.amount AS debit," + 
	            		"          0        AS credit" + 
	            		"        FROM fas_receipt_transaction t," + 
	            		"          fas_receipt_master m" + 
	            		"        WHERE t.accounting_unit_id     = m.accounting_unit_id" + 
	            		"        AND t.accounting_for_office_id = m.accounting_for_office_id" + 
	            		"        AND m.cashbook_year            = t.cashbook_year" + 
	            		"        AND m.cashbook_month           = t.cashbook_month" + 
	            		"        AND m.receipt_status           ='L'" + 
	            		"        AND m.receipt_no               =t.receipt_no" + 
	            		"        AND t.cr_dr_indicator          = 'DR'" + 
	            		"        AND m.accounting_unit_id       =" + cmbAcc_UnitCode +  
	            		"        AND m.cashbook_year            = " + txtCB_Year + 
	            		"        AND m.cashbook_month           = " + txtCB_Month +
	            		"        )as sm" + 
	            		"      GROUP BY accounting_unit_id," + 
	            		"        cashbook_year," + 
	            		"        cashbook_month," + 
	            		"        account_head_code" + 
	            		"      )as sm" + 
	            		"    GROUP BY accounting_unit_id," + 
	            		"      YEAR," + 
	            		"      MONTH," + 
	            		"      HOA" + 
	            		"    )tr" + 
	            		"  WHERE tr.accounting_unit_id = tb.accounting_unit_id" + 
	            		"  AND tr.year                 =tb.year" + 
	            		"  AND tr.month                =tb.month" + 
	            		"  AND tr.hoa                  = tb.hoa" + 
	            		"  ORDER BY hoa" + 
	            		"  )as sm" + 
	            		" WHERE debitdiff != 0" + 
	            		" OR creditdiff   !=0";
	            System.out.println("Sql>>>>>>>"+sql13);
	            PreparedStatement ps22 = con.prepareStatement(sql13);
		   	      rs = ps22.executeQuery();
		   	      int cnt2=0;
		   	   while (rs.next()) {
		      	       cnt2++;    	        
		            }
		            if(cnt2==0)
		            {
		          	  xml=xml+"<flag>success</flag>";
		            }
		            else if(cnt2>0)
		            {
		          	  xml=xml+"<flag>verify_difference</flag>";
		            }
    		
    	            
    	  }
    		 catch (Exception e) {
 	            System.out.println(e);
 	        }
      }
          
          
//          finally{
//          	try{
//          		ps1.close();
//          	}catch(Exception e){
//          		e.printStackTrace();
//          	}
//          }
          xml=xml+"</response>";
          System.out.println(">>> "+xml);
          //ps.write(xml);
          ps.println(xml);
          ps.close();
          
         
    }
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {


        /**
         * Database con
         */
    	int rsflag=1;
    	int diff_error=0;
        Connection con = null;
        Statement statement = null;
        ResultSet rst = null, results = null;
        try {
            ResourceBundle rs =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
                
            String conString = "";

            String strDriver = rs.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rs.getString("Config.DSN");
            String strhostname = rs.getString("Config.HOST_NAME");
            String strportno = rs.getString("Config.PORT_NUMBER");
            String strsid = rs.getString("Config.SID");
            String strdbusername = rs.getString("Config.USER_NAME");
            String strdbpassword = rs.getString("Config.PASSWORD");

//            conString =
//                    strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
//                    ":" + strsid.trim();
            conString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection

            

            Class.forName(strDriver.trim());
            con =
 DriverManager.getConnection(conString, strdbusername.trim(),
                             strdbpassword.trim());
            try {
                statement = con.createStatement();
                con.clearWarnings();
            } catch (SQLException e) {
                System.out.println("Exception in creating statement:" + e);
            }
        } catch (Exception e) {
            System.out.println("Exception in openeing con:" + e);
        }


        /**
         * Content Type Setting
         */

        response.setContentType(CONTENT_TYPE);


        /**
         * Session Checking
         */
        HttpSession session = request.getSession(false);
        try {

            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");

            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }


        /**
         * Variables Declaration
         */
         
        int txtCB_Year = 0, txtCB_Month = 0, cmbAcc_UnitCode = 0, OfficeCode =
            0, achcode = 0;
        String radTB_status = "";
        double cur_yr_crbal = 0;
        double cur_yr_drbal = 0;
        double Month_OB_Bal = 0;
        String Month_OB_Cr_Dr_ind = "";
        int sltypecode = 0;
        //int slcode = 0;
        Long slcode ;
        double Upto_Debit_Bal = 0;
        double Upto_Credit_Bal = 0;
        double Month_OB_Bal_new = 0;
        double CRAmount = 0;
        double DRAmount1 = 0;
        double balance = 0;
        String Month_OB_Cr_Dr_ind_new = "";
        String MONTH_CLOSING_BAL_DR_CR_IND = "";
        String NFinYear = "";
int diff_errorCheque=0;
        java.sql.Date MaxCRdate = null;
        java.sql.Date MaxDRdate = null;

//Start  3/5/2011
        
        int brs_update_count = 0;
        int jou_count=0;
        String jou_vo=" -- NIL";
        
        int pay_count=0;
        String pay_vo=" -- NIL";
        int rec_count=0;
        String rec_vo=" -- NIL";

        int tda_count=0;
        String tda_vo=" -- NIL";


        int tca_count=0;
        String tca_vo=" -- NIL";
          int payment_count=0;
        String Payment_vo=" -- NIL";

        int receipt_count=0,trial_count=0,trial_count1=0;
        String receipt_vo=" -- NIL";
         int payroll_count=0;
         String payroll_vo=" -- NIL";


        int sus_count=0;
        String sus_vo=" -- NIL",count_tally="";
        
        
        int HOA_count =0;
        String HOA_list =" -- NIL";
        
        int HOA_count_DR =0;
        String HOA_list_DR =" -- NIL";
        
        int HOA_count_NCR =0;
        String HOA_list_NCR =" -- NIL";
        
        int HOA_count_NDR =0;
        String HOA_list_NDR =" -- NIL";
        
        int k=0,counted_bank_bal=0;           
        int flagg=0,a1=0,a2=0,a3=0,a4=0,a5=0,a6=0,a7=0,a8=0,a9=0,
       		 a10=0,a11=0,a12=0;   ;    
        
        java.util.Date date_bank_bal_chk=null;
        java.util.Date passbk_date1=null;

/*end
        /**
         * Get Parameters
         */

        /** Get Accounting Unit Id */
        try {
            cmbAcc_UnitCode =
                    Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
        } catch (NumberFormatException e) {
            System.out.println("exception" + e);
        }
        //System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);

        /** Get Cash Book Year */
        txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));

        /** Get Cash Book Month */
        txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
        //System.out.println("year..." + txtCB_Year);
        //System.out.println("Month..." + txtCB_Month);

        /** Get YES or NO Status */
        radTB_status = request.getParameter("radTB_status");
      //  System.out.println("radTB_status..." + radTB_status);

        /** Get User ID */
        String userid = (String)session.getAttribute("UserId");

        /** Get Updated Time */
        long l = System.currentTimeMillis();
        Timestamp ts = new Timestamp(l);
        Boolean flag = false, flag2 = false, flag3 = false;
        
        // joan change on 24 Sep 2014
        int pvyear=0,pvmonth=0;
        if(txtCB_Month==1)
        {
        	pvyear=(txtCB_Year-1);
        	pvmonth=12;
        }
        else
        {
        		pvyear=txtCB_Year;
        		pvmonth=(txtCB_Month-1);	
        }
   
        
        try
        {
        
        int unitid=0;
        
        if(txtCB_Year >= 2018 && txtCB_Month > 3) 
     	{
     	try
        {
     		PreparedStatement ps =con.prepareStatement("select ACCOUNTING_UNIT_ID from FAS_TBFreeze_Restrict2018 where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode);
        ResultSet rs = ps.executeQuery();
        
        if (rs.next())
        {
        	unitid=rs.getInt("ACCOUNTING_UNIT_ID");
        }
        else
        {
        	unitid=0;
        }
        }
        catch(Exception e)
        {
        	System.out.println("Exception is"+e);
        }
        System.out.println("unitid===>"+unitid);
        System.out.println("cmbAcc_UnitCode===>"+cmbAcc_UnitCode);
     	
     	if(unitid==cmbAcc_UnitCode)
        {
     		sendMessage(response, "Error in Scheme Code,So TB Freeze not allowed!....",
                    "ok");
     		return;
        }
     	
     	}
        
        }
        catch(Exception e)
        {
        	System.out.println("Exception in TBFreeze Restrict validation***"+e);
        }
        
        
        try{
           	
        	
        	
        	
        	
        	
        	/*String bank_bal = "SELECT a.brs_update,  "+
				"  b.bank_bal_count, "+
				  " CASE "+
				  "   WHEN brs_update>=bank_bal_count "+
				  " THEN 'Complete' "+
				  " WHEN brs_update<bank_bal_count "+
				  " THEN 'InComplete' "+
				  " END AS count_tally "+
				  " FROM "+
				  " (SELECT COUNT(*)AS brs_update, "+
				  " ACCOUNTING_UNIT_ID "+
				  " FROM brs_bank_balance_update "+
				  " WHERE ACCOUNTING_UNIT_ID=  "+AccountUnitCode+
				  " AND PS_YEAR             =  "+pvyear+
				  " AND PS_MONTH            = "+pvmonth+
				  " GROUP BY ACCOUNTING_UNIT_ID "+
				  " )a "+
				  " INNER JOIN "+
				  " (SELECT COUNT(*)AS bank_bal_count, "+
				  " accounting_unit_id "+
				  " FROM FAS_MST_BANK_BALANCE "+
				  " WHERE ACCOUNTING_UNIT_ID= "+AccountUnitCode+
				  "  AND STATUS              ='Y' "+
				  "  GROUP BY ACCOUNTING_UNIT_ID "+
				  "  )b "+
				  " ON a.ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID";*/
        	 
        	  /* String bank_bal = "SELECT a.brs_update,  "+
       				"  b.bank_bal_count, "+
       				  " CASE "+
       				  "    WHEN brs_update=(bank_bal_count *"+pvmonth+") "+
       				  " THEN 'Complete' "+
       				//  " WHEN brs_update<(bank_bal_count *"+pvmonth+") "+
       				  " else "+
       				  "  'InComplete' "+
       				  " END AS count_tally "+
       				  " FROM "+
       				  " (SELECT COUNT(*)AS brs_update, "+
       				  " ACCOUNTING_UNIT_ID "+
       				  " FROM brs_bank_balance_update "+
       				  " WHERE ACCOUNTING_UNIT_ID=  "+cmbAcc_UnitCode+
       				  " AND PS_YEAR             =  "+pvyear+
       				  " AND PS_MONTH            <= "+pvmonth+
       				  " GROUP BY ACCOUNTING_UNIT_ID "+
       				  " )a "+
       				  " INNER JOIN "+
       				  " (SELECT COUNT(*)AS bank_bal_count, "+
       				  " accounting_unit_id "+
       				  " FROM FAS_MST_BANK_BALANCE "+
       				  " WHERE ACCOUNTING_UNIT_ID= "+cmbAcc_UnitCode+
       				  "  AND STATUS              ='Y' "+
       				  "  GROUP BY ACCOUNTING_UNIT_ID "+
       				  "  )b "+
       				  " ON a.ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID";*/
        	//Joan Added on 05 jan 2014
        	
        	int month_cnt=0;
 		   
            if(txtCB_Month==1)month_cnt=10;
            else if(txtCB_Month==2)month_cnt=11;
            else if(txtCB_Month==3)month_cnt=12;
            else if(txtCB_Month==4)month_cnt=1;
            else if(txtCB_Month==5)month_cnt=2;
            else if(txtCB_Month==6)month_cnt=3;
            else if(txtCB_Month==7)month_cnt=4;
            else if(txtCB_Month==8)month_cnt=5;
            else if(txtCB_Month==9)month_cnt=6;
            else if(txtCB_Month==10)month_cnt=7;
            else if(txtCB_Month==11)month_cnt=8;
            else if(txtCB_Month==12)month_cnt=9;
            String bank_bal="";
          /*  if(txtCB_Year>2014){
        	 bank_bal="SELECT SUM(count_tally) AS count_tally " +
        			"FROM " +
        			"  (SELECT " +
        			"    CASE " +
        			"      WHEN ( FIN.AC_OPENING_DATE < TO_DATE('01/01/"+txtCB_Year+"','dd-mm-yyyy') " +
        			"      OR FIN.AC_OPENING_DATE    IS NULL ) " +
        			"      AND BRS_UPDATE             = " +txtCB_Month+
        			"      THEN 0 " +
        			"      WHEN FIN.AC_OPENING_DATE >= TO_DATE('01/01/"+txtCB_Year+"','dd-mm-yyyy') " +
        			"      AND BRS_UPDATE            = " +
        			"        (SELECT COUNT(*) " +
        			"        FROM " +
        			"          ( SELECT rownum n  CONNECT BY level <=  " +txtCB_Month+
        			"          ) " +
        			"        WHERE n>=to_number(TO_CHAR(AC_OPENING_DATE,'mm')) " +
        			"        ) " +
        			"      THEN 0 " +
        			"      WHEN ( FIN.AC_OPENING_DATE < TO_DATE('01/01/"+txtCB_Year+"','dd-mm-yyyy') " +
        			"      OR FIN.AC_OPENING_DATE    IS NULL ) " +
        			"      AND BRS_UPDATE            <>  " +txtCB_Month+
        			"      THEN 1 " +
        			"      WHEN FIN.AC_OPENING_DATE >= TO_DATE('01/01/"+txtCB_Year+"','dd-mm-yyyy') " +
        			"      AND BRS_UPDATE           <> " +
        			"        (SELECT COUNT(*) " +
        			"        FROM " +
        			"          ( SELECT rownum n  CONNECT BY level <=  " +txtCB_Month+
        			"          ) " +
        			"        WHERE n>=to_number(TO_CHAR(AC_OPENING_DATE,'mm')) " +
        			"        ) " +
        			"      THEN 1 " +
        			"    END AS count_tally, " +
        			"    fin.bank_ac_no, " +
        			"    fin.ACCOUNTING_UNIT_ID, " +
        			"    fin.brs_update " +
        			"  FROM " +
        			"    (SELECT COUNT(*)AS brs_update, " +
        			"      u.bank_ac_no, " +
        			"      AC_OPENING_DATE, " +
        			"      u.ACCOUNTING_UNIT_ID " +
        			"    FROM brs_bank_balance_update u, " +
        			"      FAS_MST_BANK_BALANCE b " +
        			"    WHERE u.accounting_unit_id=b.accounting_unit_id " +
        			"    AND u.bank_ac_no          =b.bank_ac_no " +
        			"    AND b.status              ='Y' " +
        			"    AND u.ACCOUNTING_UNIT_ID  =  " +cmbAcc_UnitCode+
        			"    AND Cb_YEAR               =  " +txtCB_Year+
        			"    AND CB_MONTH             <=  " +txtCB_Month+
        			"    AND ( AC_OPENING_DATE     < to_date('01/01/"+txtCB_Year+"','dd-mm-yyyy') " +
        			"    OR AC_OPENING_DATE       IS NULL ) " +
        			"    GROUP BY U.BANK_AC_NO, " +
        			"      AC_OPENING_DATE, " +
        			"      U.ACCOUNTING_UNIT_ID " +
        			"    UNION " +
        			"    SELECT COUNT(brs_update) AS brs_update, " +
        			"      a.bank_ac_no, " +
        			"      a.AC_OPENING_DATE, " +
        			"      a.ACCOUNTING_UNIT_ID " +
        			"    FROM " +
        			"      (SELECT COUNT(*) AS brs_update, " +
        			"        u.bank_ac_no, " +
        			"        AC_OPENING_DATE, " +
        			"        Cb_MONTH, " +
        			"        u.ACCOUNTING_UNIT_ID " +
        			"      FROM brs_bank_balance_update u, " +
        			"        FAS_MST_BANK_BALANCE b " +
        			"      WHERE u.accounting_unit_id=b.accounting_unit_id " +
        			"      AND u.bank_ac_no          =b.bank_ac_no " +
        			"      AND B.STATUS              ='Y' " +
        			"      AND CB_YEAR               =  " +txtCB_Year+
        			"      AND CB_MONTH             IN " +
        			"        (SELECT                 * " +
        			"        FROM " +
        			"          ( SELECT rownum n  CONNECT BY level <=  " +txtCB_Month+
        			"          ) " +
        			"        WHERE n>=to_number(TO_CHAR(AC_OPENING_DATE,'mm')) " +
        			"        ) " +
        			"      AND AC_OPENING_DATE    >= TO_DATE('01/01/"+txtCB_Year+"','dd-mm-yyyy') " +
        			"      AND b.accounting_unit_id= " +cmbAcc_UnitCode+
        			"      GROUP BY U.BANK_AC_NO, " +
        			"        AC_OPENING_DATE, " +
        			"        CB_MONTH, " +
        			"        U.ACCOUNTING_UNIT_ID " +
        			"      )A " +
        			"    GROUP BY A.BANK_AC_NO, " +
        			"      A.AC_OPENING_DATE, " +
        			"      A.ACCOUNTING_UNIT_ID " +
        			"    )fin " +
        			"  )s";}
            else if(txtCB_Year==2014)
        			{
        	 bank_bal="SELECT SUM(count_tally) AS count_tally " +
        			"FROM " +
        			"  (SELECT " +
        			"    CASE " +
        			"      WHEN ( FIN.AC_OPENING_DATE < TO_DATE('01/04/"+txtCB_Year+"','dd-mm-yyyy') " +
        			"      OR FIN.AC_OPENING_DATE    IS NULL ) " +
        			"      AND BRS_UPDATE             = " +month_cnt +
        			"      THEN 0 " +
        			"      WHEN FIN.AC_OPENING_DATE >= TO_DATE('01/04/"+txtCB_Year+"','dd-mm-yyyy') " +
        			"      AND BRS_UPDATE            = " +
        			"        (SELECT COUNT(*) " +
        			"        FROM " +
        			"          ( select t.n from (SELECT rownum n  CONNECT BY level <=  " +txtCB_Month+" )t where t.n >=4"+
        			"          ) " +
        			"        WHERE n>=to_number(TO_CHAR(AC_OPENING_DATE,'mm')) " +
        			"        ) " +
        			"      THEN 0 " +
        			"      WHEN ( FIN.AC_OPENING_DATE < TO_DATE('01/04/"+txtCB_Year+"','dd-mm-yyyy') " +
        			"      OR FIN.AC_OPENING_DATE    IS NULL ) " +
        			"      AND BRS_UPDATE            <>  " +month_cnt+
        			"      THEN 1 " +
        			"      WHEN FIN.AC_OPENING_DATE >= TO_DATE('01/04/"+txtCB_Year+"','dd-mm-yyyy') " +
        			"      AND BRS_UPDATE           <> " +
        			"        (SELECT COUNT(*) " +
        			"        FROM " +
        			"          (select t.n from (SELECT rownum n  CONNECT BY level <=  " +txtCB_Month+" )t where t.n >=4"+
        			"          ) " +
        			"        WHERE n>=to_number(TO_CHAR(AC_OPENING_DATE,'mm')) " +
        			"        ) " +
        			"      THEN 1 " +
        			"    END AS count_tally, " +
        			"    fin.bank_ac_no, " +
        			"    fin.ACCOUNTING_UNIT_ID, " +
        			"    fin.brs_update " +
        			"  FROM " +
        			"    (SELECT COUNT(*)AS brs_update, " +
        			"      u.bank_ac_no, " +
        			"      AC_OPENING_DATE, " +
        			"      u.ACCOUNTING_UNIT_ID " +
        			"    FROM brs_bank_balance_update u, " +
        			"      FAS_MST_BANK_BALANCE b " +
        			"    WHERE u.accounting_unit_id=b.accounting_unit_id " +
        			"    AND u.bank_ac_no          =b.bank_ac_no " +
        			"    AND b.status              ='Y' " +
        			"    AND u.ACCOUNTING_UNIT_ID  =  " +cmbAcc_UnitCode+
        			"    AND Cb_YEAR               =  " +txtCB_Year+
        			"    AND CB_MONTH             <=  " +txtCB_Month+"  and CB_MONTH             >=  4 "+
        			"    AND ( AC_OPENING_DATE     < to_date('01/04/"+txtCB_Year+"','dd-mm-yyyy') " +
        			"    OR AC_OPENING_DATE       IS NULL ) " +
        			"    GROUP BY U.BANK_AC_NO, " +
        			"      AC_OPENING_DATE, " +
        			"      U.ACCOUNTING_UNIT_ID " +
        			"    UNION " +
        			"    SELECT COUNT(brs_update) AS brs_update, " +
        			"      a.bank_ac_no, " +
        			"      a.AC_OPENING_DATE, " +
        			"      a.ACCOUNTING_UNIT_ID " +
        			"    FROM " +
        			"      (SELECT COUNT(*) AS brs_update, " +
        			"        u.bank_ac_no, " +
        			"        AC_OPENING_DATE, " +
        			"        Cb_MONTH, " +
        			"        u.ACCOUNTING_UNIT_ID " +
        			"      FROM brs_bank_balance_update u, " +
        			"        FAS_MST_BANK_BALANCE b " +
        			"      WHERE u.accounting_unit_id=b.accounting_unit_id " +
        			"      AND u.bank_ac_no          =b.bank_ac_no " +
        			"      AND B.STATUS              ='Y' " +
        			"      AND CB_YEAR               =  " +txtCB_Year+
        			"      AND CB_MONTH             IN " +
        			"        (SELECT                 * " +
        			"        FROM " +
        			"          ( select t.n from (SELECT rownum n  CONNECT BY level <=  " +txtCB_Month+" )t where t.n >=4"+
        			"          ) " +
        			"        WHERE n>=to_number(TO_CHAR(AC_OPENING_DATE,'mm')) " +
        			"        ) " +
        			"      AND AC_OPENING_DATE    >= TO_DATE('01/04/"+txtCB_Year+"','dd-mm-yyyy') " +
        			"      AND b.accounting_unit_id= " +cmbAcc_UnitCode+
        			"      GROUP BY U.BANK_AC_NO, " +
        			"        AC_OPENING_DATE, " +
        			"        CB_MONTH, " +
        			"        U.ACCOUNTING_UNIT_ID " +
        			"      )A " +
        			"    GROUP BY A.BANK_AC_NO, " +
        			"      A.AC_OPENING_DATE, " +
        			"      A.ACCOUNTING_UNIT_ID " +
        			"    )fin " +
        			"  )s";
        			}
           	System.out.println("brsssssssssssssssssssss:::"+bank_bal);
           	PreparedStatement ps223=con.prepareStatement(bank_bal);
           	
           ResultSet rs223=ps223.executeQuery();
           	while(rs223.next())
           	{
           		System.out.println("Cbyear"+txtCB_Year+"  res : "+rs223.getString("count_tally")+" "+"AccountUnitCode : "+cmbAcc_UnitCode);
           		if(cmbAcc_UnitCode==5){
           			counted_bank_bal=0;	
           		}else{
           		if(pvyear>2013){
           			System.out.println(" Else Part .... year"+txtCB_Year+"  res : "+rs223.getString("count_tally")+" "+"AccountUnitCode : "+cmbAcc_UnitCode);
           		count_tally=rs223.getString("count_tally");
           		if(count_tally.equals("1"))
           		{
           			counted_bank_bal=rs223.getInt("brs_update");
           		}
           		else if(count_tally.equals("0"))
           		{
           			counted_bank_bal=0;
           		}
           	
           		}else{
           			counted_bank_bal=0;	
           		}
           		}
           		}*/
         	// Joan added on 11 Nov 2015
            
         /*   bank_bal="SELECT COUNT(*) as count_value " +
         		   " FROM " +
         		   "  (SELECT Bank_Ac_No, " +
         		   "    Ac_Opening_Date,Accounting_Unit_Id, " +
         		   "    CASE " +
         		   "      WHEN Ac_Opening_Date IS NULL " +
         		   "      THEN " +
         		   "        (SELECT COUNT(*) " +
         		   "        FROM brs_bank_balance_update u " +
         		   "        WHERE U.Accounting_Unit_Id =Bsal.Accounting_Unit_Id " +
         		   "        AND u.bank_ac_no           =bsal.bank_ac_no " +
         		   "        AND To_Date((Cb_Month " +
         		   "          ||'-' " +
         		   "          || Cb_Year),'mm-yyyy') BETWEEN To_Date(1 " +
         		   "          ||'-' " +
         		   "          ||"+txtCB_Year+",'mm-yyyy') " +
         		   "        AND to_date( " +txtCB_Month+
         		   "          ||'-' " +
         		   "          ||"+txtCB_Year+",'mm-yyyy') " +
         		   "        ) " +
         		   "      WHEN Ac_Opening_Date < '01-jan-"+txtCB_Year+"' " +
         		   "      THEN " +
         		   "        (SELECT COUNT(*) " +
         		   "        FROM brs_bank_balance_update u " +
         		   "        WHERE u.accounting_unit_id =bsal.accounting_unit_id " +
         		   "        AND u.bank_ac_no           =bsal.bank_ac_no " +
         		   "        AND To_Date((Cb_Month " +
         		   "          ||'-' " +
         		   "          || Cb_Year),'mm-yyyy') BETWEEN To_Date(1 " +
         		   "          ||'-' " +
         		   "          ||"+txtCB_Year+",'mm-yyyy') " +
         		   "        AND to_date( " +txtCB_Month+
         		   "          ||'-' " +
         		   "          ||"+txtCB_Year+",'mm-yyyy') " +
         		   "        ) " +
         		   "      WHEN Ac_Opening_Date >= '01-jan-"+txtCB_Year+"' " +
         		   "      THEN " +
         		   "        (SELECT COUNT(*) " +
         		   "        FROM Brs_Bank_Balance_Update u " +
         		   "        WHERE u.accounting_unit_id =bsal.accounting_unit_id " +
         		   "        AND u.bank_ac_no           =bsal.bank_ac_no " +
         		   "        AND To_Date((Cb_Month " +
         		   "          ||'-' " +
         		   "          || Cb_Year),'mm-yyyy') BETWEEN To_Date(extract(MONTH FROM Ac_Opening_Date) " +
         		   "          ||'-' " +
         		   "          ||extract(YEAR FROM Ac_Opening_Date),'mm-yyyy') " +
         		   "        AND to_date( " +txtCB_Month+
         		   "          ||'-' " +
         		   "          ||"+txtCB_Year+",'mm-yyyy') " +
         		   "        ) " +
         		   "    END AS Count_No " +
         		   "  FROM fas_mst_bank_balance bsal " +
         		   "  WHERE Accounting_Unit_Id= " +cmbAcc_UnitCode+
         		   "  AND Status              ='Y' " +
         		   "  )Xx " +
         		   " INNER JOIN " +
         		   "  (SELECT bank_ac_no, " +
         		   "    Ac_Opening_Date,Accounting_Unit_Id, " +
         		   "    CASE " +
         		   "      WHEN Ac_Opening_Date IS NULL " +
         		   "      THEN " +
         		   "        (SELECT COUNT(*) FROM " +
         		   "          (SELECT rownum n  CONNECT BY level <=  " +txtCB_Month+
         		   "          )t " +
         		   "        ) " +
         		   "      WHEN Ac_Opening_Date >= '01-jan-"+txtCB_Year+"' " +
         		   "      THEN " +
         		   "        (SELECT COUNT(*) " +
         		   "        FROM " +
         		   "          (SELECT rownum n  CONNECT BY Level <=  " +txtCB_Month+
         		   "          )T " +
         		   "        WHERE n >= to_number(TO_CHAR(Ac_Opening_Date,'mm')) " +
         		   "        ) " +
         		   "      WHEN Ac_Opening_Date < '01-jan-"+txtCB_Year+"' " +
         		   "      THEN " +
         		   "        (SELECT COUNT(*) FROM " +
         		   "          (SELECT rownum n  CONNECT BY Level <=  " +txtCB_Month+
         		   "          )T " +
         		   "        ) " +
         		   "    END AS rt " +
         		   "  FROM fas_mst_bank_balance bsal " +
         		   "  WHERE Accounting_Unit_Id= " +cmbAcc_UnitCode+
         		   "  AND Status              ='Y' " +
         		   "  ) Yy " +
         		   " ON Xx.Bank_Ac_No=Yy.Bank_Ac_No and xx.Accounting_Unit_Id=yy.Accounting_Unit_Id  " +
         		   " WHERE count_no <> rt";
            */
            //Finding last date of freezing month 30/03/2016....
            Calendar calendar = Calendar.getInstance();
            calendar.set(txtCB_Year,txtCB_Month-1,1); //------>
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                 
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            System.out.println("lastttt date**"+sdf.format(calendar.getTime()));
            
            passbk_date1=sdf.parse(sdf.format(calendar.getTime()));
          System.out.println("passbk_date1"+passbk_date1);
        	
        	try{
        	String bank_bal_check_date="SELECT COUNT(ps_date) AS cou, " +
        			"  ps_date " +
        			" FROM brs_bank_balance_update " +
        			" WHERE accounting_unit_id = " +cmbAcc_UnitCode+
        			" AND PS_YEAR              = " +txtCB_Year+
        			" AND PS_MONTH             = " +txtCB_Month+
        			" GROUP BY ps_date";
        	
        	
        	System.out.println("Query of geeting ps date  "+bank_bal_check_date);
        	PreparedStatement pschk=con.prepareStatement(bank_bal_check_date);
           	
            ResultSet rschk=pschk.executeQuery();
           // System.out.println("rschk result"+rschk);
            	while(rschk.next())
            	{
            	
            		date_bank_bal_chk=rschk.getDate("ps_date");
            		System.out.println("BRS PS Date freezed month***"+date_bank_bal_chk);
            		           	
            	}
            	
            	
           }
           catch(Exception ee)
           {
           	System.out.println("excep:::in brs_bank balance date check:::::"+ee);
           }
        
        	
        	
        	String month = "";
            if(txtCB_Month==1){
         	   month="Jan";
            }
            else if(txtCB_Month==2)
            {
         	   month="Feb";
            }
            else if(txtCB_Month==3)
            {
         	   month="Mar";
            }
            else if(txtCB_Month==4)
            {
         	   month="Apr";
            }
            else if(txtCB_Month==5)
            {
         	   month="May";
            }
            else if(txtCB_Month==6)
            {
         	   month="Jun";
            }
            else if(txtCB_Month==7){
         	   month="Jul";
            }
            else if(txtCB_Month==8){
         	   month="Aug";
            }
            else if(txtCB_Month==9){
         	   month="Sep";
            }
            else if(txtCB_Month==10){
         	   month="Oct";
            }
            else if(txtCB_Month==11){
         	   month="Nov";
            }
            else if(txtCB_Month==12){
         	   month="Dec";
            } 
        	
            Date lastDay = new Date(txtCB_Year, txtCB_Month , 0);
            int lastDayWithSlashes=lastDay.getDate();
            System.out.println("lastDayWithSlashes===>"+lastDayWithSlashes);
        	
//            if(cmbAcc_UnitCode!=5)
//            {
//              counted_bank_bal=0;
//            }
//            else{
            bank_bal="SELECT COUNT(*) AS count_value " +
         		   " FROM " +
         		   "  (SELECT Bank_Ac_No, " +
         		   "    Ac_Opening_Date, " +
         		   "    Accounting_Unit_Id, " +
         		   "    CASE " +
         		   "      WHEN Ac_Opening_Date IS NULL " +
         		   "      THEN " +
         		   "        (SELECT COUNT(*) " +
         		   "        FROM brs_bank_balance_update u " +
         		   "        WHERE U.Accounting_Unit_Id =Bsal.Accounting_Unit_Id " +
         		   "        AND u.bank_ac_no::numeric          =bsal.bank_ac_no " +
         		   "        AND To_Date((PS_MONTH " +
         		   "          ||'-' " +
         		   "          || PS_YEAR),'mm-yyyy') BETWEEN To_Date(4 " +
         		   "          ||'-' " +
         		   "          || " +txtCB_Year+",'mm-yyyy') " +
         		   "        AND to_date(  " +txtCB_Month+
         		   "          ||'-' " +
         		   "          || " +txtCB_Year+",'mm-yyyy') " +
         		   "        ) " +
         		   "      WHEN Ac_Opening_Date < '01-Apr-"+txtCB_Year+"' " +
         		   "      THEN " +
         		   "        (SELECT COUNT(*) " +
         		   "        FROM brs_bank_balance_update u " +
         		   "        WHERE u.accounting_unit_id =bsal.accounting_unit_id " +
         		   "        AND u.bank_ac_no ::numeric          =bsal.bank_ac_no " +
         		   "        AND To_Date((PS_MONTH " +
         		   "          ||'-' " +
         		   "          || PS_YEAR),'mm-yyyy') BETWEEN To_Date(4 " +
         		   "          ||'-' " +
         		   "          ||" +txtCB_Year+",'mm-yyyy') " +
         		   "        AND to_date(  " +txtCB_Month+
         		   "          ||'-' " +
         		   "          ||" +txtCB_Year+",'mm-yyyy') " +
         		   "        ) " +
         		   "      WHEN (Ac_Opening_Date >= '01-Apr-"+txtCB_Year+"') and (Ac_Opening_Date <= '"+lastDayWithSlashes+"-"+ month +"-"+txtCB_Year+"')" +
         		   "      THEN " +
         		   "        (SELECT COUNT(*) " +
         		   "        FROM Brs_Bank_Balance_Update u " +
         		   "        WHERE u.accounting_unit_id =bsal.accounting_unit_id " +
         		   "        AND u.bank_ac_no::numeric           =bsal.bank_ac_no " +
         		   "        AND To_Date((PS_MONTH " +
         		   "          ||'-' " +
         		   "          || PS_YEAR),'mm-yyyy') BETWEEN To_Date(extract(MONTH FROM Ac_Opening_Date) " +
         		   "          ||'-' " +
         		   "          ||extract(YEAR FROM Ac_Opening_Date),'mm-yyyy') " +
         		   "        AND to_date(  " +txtCB_Month+
         		   "          ||'-' " +
         		   "          ||"+txtCB_Year+",'mm-yyyy') " +
         		   
   /*  extra add for  test 30-07-2016	   		   
  "        AND To_Date((Cb_Month " +
   "          ||'-' " +
   "          || Cb_Year),'mm-yyyy') BETWEEN  to_date(  " +txtCB_Month+
   "          ||'-' " +
   "          ||"+txtCB_Year+",'mm-yyyy')  and To_Date(extract(MONTH FROM Ac_Opening_Date) " +
   "          ||'-' " +
   "          ||extract(YEAR FROM Ac_Opening_Date),'mm-yyyy') " +*/
					 
         		   
					 /*
					  BETWEEN to_date(  " +txtCB_Month+
					"          ||'-' " +
					"          ||"+txtCB_Year+",'mm-yyyy') " + and To_Date(extract(MONTH FROM Ac_Opening_Date) " +
					"          ||'-' " +
					"          ||extract(YEAR FROM Ac_Opening_Date),'mm-yyyy') " +
					 */
         		   
         		   "        ) " +
         		   "    END AS Count_No " +
         		   "  FROM Fas_Mst_Bank_Balance Bsal " +
         		   "  WHERE Accounting_Unit_Id= " +cmbAcc_UnitCode+
         		   "  AND Status              ='Y' " +
         		   "  )Xx " +
         		   " INNER JOIN " +
         		   "  (SELECT bank_ac_no, " +
         		   "    Ac_Opening_Date, " +
         		   "    Accounting_Unit_Id, " +
         		   "    CASE " +
         		   "      WHEN Ac_Opening_Date IS NULL " +
         		   "      THEN 0 " +
//         		   "        (SELECT COUNT(*) " +
//         		   "        FROM " +
//         		   "          (SELECT Rownum N  CONNECT BY Level <=  " +txtCB_Month+
//         		   "          )t " +
//         		   "        WHERE n >3 " +
//         		   "        ) " +
         		   "      WHEN Ac_Opening_Date >='01-Apr-"+txtCB_Year+"' " +
         		   
				// "      WHEN  (Ac_Opening_Date >='01-Apr-"+txtCB_Year+"' and Ac_Opening_Date <='01-dec-"+txtCB_Year+"')  " +

         		   "      THEN 3 " +
//         		   "        (SELECT COUNT(*) " +
//         		   "        FROM " +
//         		   "          (SELECT rownum n  CONNECT BY Level <=  " +txtCB_Month+
//         		   "          )T " +
//         		   "        WHERE N >= to_number(TO_CHAR(Ac_Opening_Date,'mm')) " +
//         		  // "          --and n >3 " +
//         		   "        ) " +
         		   "      WHEN Ac_Opening_Date < '01-Apr-"+txtCB_Year+"' " +
         		   "      THEN 0" +
//         		   "        (SELECT COUNT(*) " +
//         		   "        FROM " +
//         		   "          (SELECT Rownum N  CONNECT BY Level <=  " +txtCB_Month+
//         		   "          )T " +
//         		   "        WHERE n >3 " +
//         		   "        ) " +
         		   "    END AS rt " +
         		   "  FROM Fas_Mst_Bank_Balance Bsal " +
         		   "  WHERE Accounting_Unit_Id= " +cmbAcc_UnitCode+
         		   "  AND Status              ='Y' " +
         		   "  ) Yy " +
         		   " ON Xx.Bank_Ac_No         =Yy.Bank_Ac_No " +
         		   " AND Xx.Accounting_Unit_Id=Yy.Accounting_Unit_Id " +
         		   " WHERE count_no          <> rt";
//				   "INNER JOIN" +
//				   "(SELECT bank_ac_no," + 
//        		   "    Ac_Opening_Date," + 
//        		   "    Accounting_Unit_Id," + 
//        		   "    CASE" + 
//        		   "      WHEN Ac_Opening_Date IS NULL" + 
//        		   "      THEN" + 
//        		   "        (SELECT RT1 as COUNT" + 
//        		   "        FROM(" + 
//        		   "        SELECT" + 
//        		   "        CASE" + 
//        		   "        WHEN DATE_OF_OPENING IS NULL" + 
//        		   "        THEN" + 
//        		   "        (SELECT COUNT(*)" + 
//        		   "        FROM" + 
//        		   "          (SELECT Rownum N  CONNECT BY Level <= " +txtCB_Month+
//        		   "          )T" + 
//        		   "        WHERE N >3)" + 
//        		   "        WHEN DATE_OF_OPENING IS NOT NULL" + 
//        		   "        THEN" + 
//        		   "        (SELECT COUNT(*)" + 
//        		   "        FROM" + 
//        		   "          (SELECT Rownum N  CONNECT BY Level <= " +txtCB_Month+ 
//        		   "          )T" + 
//        		   "        WHERE N >= TO_NUMBER(TO_CHAR(DATE_OF_OPENING,'mm')))" + 
//        		   "        END AS rt1" + 
//        		   "        FROM FAS_MST_ACCT_UNITS" + 
//        		   "        WHERE ACCOUNTING_UNIT_ID=" +cmbAcc_UnitCode+
//        		   "        )" + 
//        		   "        )" + 
//        		   "        WHEN Ac_Opening_Date >= '01-Apr-"+txtCB_Year+"' " +
//        		   "      THEN" + 
//        		   "        (SELECT COUNT(*)" + 
//        		   "        FROM" + 
//        		   "          (SELECT rownum n  CONNECT BY Level <= " +txtCB_Month+
//        		   "          )T" + 
//        		   "        WHERE N >= TO_NUMBER(TO_CHAR(AC_OPENING_DATE,'mm'))" + 
//        		   "        )" + 
//        		   "        WHEN Ac_Opening_Date < '01-Apr-"+txtCB_Year+"' " +
//        		   "         THEN" + 
//        		   "        (SELECT RT1 as COUNT" + 
//        		   "        FROM(" + 
//        		   "        SELECT" + 
//        		   "        CASE" + 
//        		   "        WHEN DATE_OF_OPENING IS NULL" + 
//        		   "        THEN" + 
//        		   "        (SELECT COUNT(*)" + 
//        		   "        FROM" + 
//        		   "          (SELECT Rownum N  CONNECT BY Level <= " +txtCB_Month+
//        		   "          )T" + 
//        		   "        WHERE N >3)" + 
//        		   "        WHEN DATE_OF_OPENING IS NOT NULL" + 
//        		   "        THEN" + 
//        		   "        (SELECT COUNT(*)" + 
//        		   "        FROM" + 
//        		   "          (SELECT Rownum N  CONNECT BY Level <= " +txtCB_Month+
//        		   "          )T" + 
//        		   "        WHERE N >= TO_NUMBER(TO_CHAR(DATE_OF_OPENING,'mm')))" + 
//        		   "        END AS rt1" + 
//        		   "        FROM FAS_MST_ACCT_UNITS" + 
//        		   "        WHERE ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+
//        		   "        )" + 
//        		   "        )" + 
//        		   "        END AS rt" + 
//        		   "  FROM Fas_Mst_Bank_Balance Bsal" + 
//        		   "  WHERE ACCOUNTING_UNIT_ID=" +cmbAcc_UnitCode+
//        		   "  AND Status              ='Y' " +
//        		   "  ) Yy " +
//        		   " ON Xx.Bank_Ac_No         =Yy.Bank_Ac_No " +
//        		   " AND Xx.Accounting_Unit_Id=Yy.Accounting_Unit_Id " +
//        		   " WHERE count_no          <> rt";
            
           	System.out.println("brsssssssssssssssssssss:::"+bank_bal);
        	PreparedStatement ps223=con.prepareStatement(bank_bal);
        	counted_bank_bal=1;
        	
        	CallableStatement cs = con.prepareCall("{? = call fas_chk_brs (?::numeric,?::numeric,?::numeric)}");
        		cs.registerOutParameter(1, Types.NUMERIC);
        	 cs.setInt(2, cmbAcc_UnitCode);
             cs.setInt(3, txtCB_Year);
             cs.setInt(4, txtCB_Month);          
             cs.execute();
             counted_bank_bal = cs.getBigDecimal(1).intValue();  
        	
             
             
          /*  ResultSet rs223=ps223.executeQuery();
            	while(rs223.next())
            	{
            	
            			counted_bank_bal=rs223.getInt("count_value");
            		
            	
            	}
            	
//            }
           }*/
        }catch(Exception ee)
           {
           	System.out.println("excep:::in brs_bank balance:::::"+ee);
           }
        
        if(counted_bank_bal>0)
        {
        	
//        	try {
//				con.rollback();
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} 
//          	  sendMessage(response, "Bank Balance Update (BRS) is Incomplete","ok");
        	sendMessage(response, "Bank Balance Update is Incomplete","ok");
            return;	
        	
        }   
        
        
        
        else{
        	try
        	{
        		
        		  if (date_bank_bal_chk != null) {
        			  
                      if (date_bank_bal_chk.compareTo(passbk_date1) != 0) // finding brs Pass sheet date and last date of freeze month must be same.
                      {
                    	  sendMessage(response, "Please update bank balance upto last date of freezing month ", "ok");
                          return;
                      }
                      else 
                      {
                    	  brs_update_count++;
                      }
                  }else
                  {
                	  sendMessage(response, "Please update bank balance upto last date of freezing month ", "ok");
                      return;
                  }
        	}
        	catch(Exception e3)
        	{
        		System.out.println("exception in pass sheet date and last frezzed date check"+e3);
        	}
        	
        	System.out.println("Date comparing value"+brs_update_count);
        
        try {
            PreparedStatement pss =
                con.prepareStatement("select ACCOUNTING_UNIT_OFFICE_ID from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=?");
            pss.setInt(1, cmbAcc_UnitCode);
            rst = pss.executeQuery();
            if (rst.next()) {
                OfficeCode = rst.getInt("ACCOUNTING_UNIT_OFFICE_ID");
            }
        } catch (Exception e) {
            System.out.println("Err in office code selection :: " +
                               e.getMessage());
        }
        
        


        int checkcountTB=0;
               try{
              
              
              String checkTB="  select COUNT(*) as cout from FAS_TRIAL_BALANCE_STATUS    WHERE  "+                    
                " ACCOUNTING_UNIT_ID= "+cmbAcc_UnitCode;
                
                PreparedStatement ps0=con.prepareStatement(checkTB);
                ResultSet rs0=ps0.executeQuery();
                if(rs0.next()){
                checkcountTB=rs0.getInt("cout");
               
                }
               }catch (Exception e) {
        System.out.println("Exception in Checking TB"+e);
        }
                System.out.println("checkcountTB "+checkcountTB);
                
                
                
              if(checkcountTB==1){
             
              }else{
                
              //this is for checking previous month trialbalance status closed or not
            	  System.out.println("checkcountTB "+checkcountTB);
            	  
            	  int open_year=0,open_month=0;
            	  
                if(txtCB_Month>1){
         
                	

                	try {
        				PreparedStatement ps_chk=con.prepareStatement("select to_char(DATE_OF_OPENING,'yyyy') as year, to_char(DATE_OF_OPENING,'mm')as  month from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode);
        			ResultSet rs_chk=ps_chk.executeQuery();
        			if(rs_chk.next())
        			{
        			open_year=rs_chk.getInt(1)	;open_month=rs_chk.getInt(2)	;
        			}
        			System.out.println("open_year :: "+open_year);
        			System.out.println("open_month :: "+open_month);
                	} catch (SQLException e1) {
        				// TODO Auto-generated catch block
        				e1.printStackTrace();
        			}
        		 if(txtCB_Month==open_month && txtCB_Year==open_year){
        			 trial_count=1;
        		 }else{
                	try {
        		  	
        		  		int prevmonth_trial=txtCB_Month-1;
        		  		PreparedStatement ps =con.prepareStatement("  select 'X'                  \n" +
        		                        "  from                        \n" +
        		                        "    FAS_TRIAL_BALANCE_STATUS  \n" +
        		                        "  WHERE                       \n" +
        		                        "     ACCOUNTING_UNIT_ID=?     \n" +
        		                        "  AND CASHBOOK_YEAR=?      \n" +
        		                        "  AND CASHBOOK_MONTH=?");
        		      ps.setInt(1, cmbAcc_UnitCode);
        		      ps.setInt(2, txtCB_Year);
        		      ps.setInt(3, prevmonth_trial);
        		      ResultSet res = ps.executeQuery();
        		      if (res.next()) // if the row doesn't return by the query
        		      {
        		         trial_count++;
        		      }
        		      res.close();
        		      ps.close();
        		  } catch (Exception e) {
        		      System.out.println(e);
        		  }
                	}
        		  if(trial_count==0)
        	        {
        	        	 sendMessage(response, "Trial Balance Status is Not Freezed For Previous Month", "ok");
        	             return;
        	        }
                
                	
                	
                	
//                	try {
//        				PreparedStatement ps_chk=con.prepareStatement("select to_char(DATE_OF_OPENING,'yyyy') year, to_char(DATE_OF_OPENING,'mm') month from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode);
//        			ResultSet rs_chk=ps_chk.executeQuery();
//        			if(rs_chk.next())
//        			{
//        			open_year=rs_chk.getInt(1)	;open_month=rs_chk.getInt(2)	;
//        			}
//        			System.out.println("open_year :: "+open_year);
//        			System.out.println("open_month :: "+open_month);
//                	} catch (SQLException e1) {
//        				// TODO Auto-generated catch block
//        				e1.printStackTrace();
//        			}
//        		 if(txtCB_Month==open_month && txtCB_Year==open_year){
//        			 trial_count=1;
//        		 }else{
//                	
//                	
//                	
//                	
//                	try {
//         
//          int prevmonth_trial=txtCB_Month-1;
//             PreparedStatement ps =con.prepareStatement("  select 'X'                  \n" +
//                               "  from                        \n" +
//                               "    FAS_TRIAL_BALANCE_STATUS  \n" +
//                               "  WHERE                       \n" +
//                               "     ACCOUNTING_UNIT_ID=?     \n" +
//                               "  AND CASHBOOK_YEAR=?      \n" +
//                               "  AND CASHBOOK_MONTH=?");
//             ps.setInt(1, cmbAcc_UnitCode);
//             ps.setInt(2, txtCB_Year);
//             ps.setInt(3, prevmonth_trial);
//             ResultSet res = ps.executeQuery();
//             if (res.next()) // if the row doesn't return by the query
//             {
//                trial_count++;
//             }
//             res.close();
//             ps.close();
//         } catch (Exception e) {
//             System.out.println(e);
//         }
//        		 }
//       		  if(trial_count==0)
//       	        {
//       	        	 sendMessage(response, "Trial Balance Status is Not Freezed For Previous Month", "ok");
//       	             return;
//       	        }
//                	
                	
                }
                
                else
                {

        try {
             
          int prevyear_trial=txtCB_Year-1;
            PreparedStatement ps =con.prepareStatement("  select 'X'                  \n" +
                               "  from                        \n" +
                               "    FAS_TRIAL_BALANCE_STATUS  \n" +
                               "  WHERE                       \n" +
                               "     ACCOUNTING_UNIT_ID=?     \n" +
                               "  AND CASHBOOK_YEAR=?      \n" +
                               "  AND CASHBOOK_MONTH=12");
             ps.setInt(1, cmbAcc_UnitCode);
             ps.setInt(2, prevyear_trial);
             ResultSet res = ps.executeQuery();
             if (res.next()) // if the row doesn't return by the query
             {
                trial_count++;
             }
             res.close();
             ps.close();
         } catch (Exception e) {
             System.out.println(e);
         }
                }
         if(trial_count==0)
               {
                sendMessage(response, "Please Freeze Previous Month TrialBalance", "ok");
                    return;
               }
        
         
         
         
         
              }
              
        
        
/*
       
      //this is for checking previous month trialbalance status closed or not
        if(txtCB_Month>1){
		  try {
		  	
		  		int prevmonth_trial=txtCB_Month-1;
		      PreparedStatement ps =con.prepareStatement("  select 'X'                  \n" +
		                        "  from                        \n" +
		                        "    FAS_TRIAL_BALANCE_STATUS  \n" +
		                        "  WHERE                       \n" +
		                        "     ACCOUNTING_UNIT_ID=?     \n" +
		                        "  AND CASHBOOK_YEAR=?      \n" +
		                        "  AND CASHBOOK_MONTH=?");
		      ps.setInt(1, cmbAcc_UnitCode);
		      ps.setInt(2, txtCB_Year);
		      ps.setInt(3, prevmonth_trial);
		      ResultSet res = ps.executeQuery();
		      if (res.next()) // if the row doesn't return by the query
		      {
		         trial_count++;
		      }
		      res.close();
		      ps.close();
		  } catch (Exception e) {
		      System.out.println(e);
		  }
        }
        else
        {

				try {
	     		  	
	 		  		int prevyear_trial=txtCB_Year-1;
	 		     PreparedStatement ps =con.prepareStatement("  select 'X'                  \n" +
	 		                        "  from                        \n" +
	 		                        "    FAS_TRIAL_BALANCE_STATUS  \n" +
	 		                        "  WHERE                       \n" +
	 		                        "     ACCOUNTING_UNIT_ID=?     \n" +
	 		                        "  AND CASHBOOK_YEAR=?      \n" +
	 		                        "  AND CASHBOOK_MONTH=12");
	 		      ps.setInt(1, cmbAcc_UnitCode);
	 		      ps.setInt(2, prevyear_trial);
	 		      ResultSet res = ps.executeQuery();
	 		      if (res.next()) // if the row doesn't return by the query
	 		      {
	 		         trial_count++;
	 		      }
	 		      res.close();
	 		      ps.close();
	 		  } catch (Exception e) {
	 		      System.out.println(e);
	 		  }
        }
		  if(trial_count==0)
	        {
	        	 sendMessage(response, "Please Freeze Previous Month TrialBalance", "ok");
	             return;
	        }*/

        
        
        
        
        /**
         * Calculating New Cashbook Month and Year
         */
        int txtCB_Month1 = 0;
        txtCB_Month1 = txtCB_Month - 1;
      //  System.out.println("b4 cashbookmont1:" + txtCB_Month1);
        int txtCB_Year1 = 0;

        if (txtCB_Month1 == 0) {
            txtCB_Month1 = 12;
            txtCB_Year1 = txtCB_Year - 1;
        } else {
            txtCB_Month1 = txtCB_Month - 1;
            txtCB_Year1 = txtCB_Year;
        }

        //finacial year calculation

        if ((txtCB_Month >= 1 && txtCB_Month <= 3)) {
            NFinYear = (txtCB_Year - 1) + "-" + (txtCB_Year);
        } else {
            NFinYear = (txtCB_Year) + "-" + (txtCB_Year + 1);
        }

        ////////////////////////////////////////////////////


        System.out.println("cashbookmont1:" + txtCB_Month1);
        System.out.println("txtCB_Year1:" + txtCB_Year1);

        /** Check Trial Balance Closure table --'FAS_TB_CLOSURE'
         *  If Record exits in FAS_TB_CLOSURE table, You cant allow TB to Freeze
         */
        int count_1 = 0;
        try {
            PreparedStatement ps3 = null;
            ResultSet rs3 = null;
            con.setAutoCommit(false);
            ps3 =
 con.prepareStatement("select * from fas_tb_closure where cashbook_year=?  and  cashbook_month= ?  and  tb_status='Y' ");
            ps3.setInt(1, txtCB_Year);
            ps3.setInt(2, txtCB_Month);
            rs3 = ps3.executeQuery();
            System.out.println("success1***********************************************************************");
            if (rs3.next()) {
                System.out.println("3");
                count_1++;
            }
            System.out.println("count_1==>"+count_1);
            if (count_1 > 0) {
                sendMessage(response,
                            "Sorry !  You can't Freeze Trial Balance.   TB Closure has already been Frozen",
                            "ok");
                ps3.close();
                rs3.close();
                return;
            }
            ps3.close();
            rs3.close();
        } catch (Exception e) {
            System.out.println("Error in TB Closure " + e);
        }


        /**
                *  Check Wheather TB has been Frozen already or not
                */
        try {
            PreparedStatement ps = null;
            ps =
  con.prepareStatement("  select 'X'                  \n" + "  from                        \n" +
                       "    FAS_TRIAL_BALANCE_STATUS  \n" +
                       "  WHERE                       \n" +
                       "     ACCOUNTING_UNIT_ID=?     \n" +
                       "  AND CASHBOOK_YEAR=?      \n" +
                       "  AND CASHBOOK_MONTH=?");
            ps.setInt(1, cmbAcc_UnitCode);
            ps.setInt(2, txtCB_Year);
            ps.setInt(3, txtCB_Month);
            ResultSet rh = ps.executeQuery();
            if (rh.next()) // if the row doesn't return by the query
            {
                sendMessage(response, "Trial Balance Already Froze", "ok");
                return;
            }
            rh.close();
            ps.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        /** Added 17/07/2018  */
        

        /**
          * SL Part
         */
        try {
            con.setAutoCommit(false);
        } catch (Exception e) {
            System.out.println("");
        }
        try {

            /**
                               * Initially Make con as false
                               */

            PreparedStatement ps = null, ps2 = null, ps1 = null, ps7 = null;
            /**
                               * Delete All the Previous Entries in Master Table
                               */
            String sql_del =
                "delete from fas_sub_ledger_master where accounting_unit_id=? and accounting_for_office_id=?  and year=? and month=? ";
            ps = con.prepareStatement(sql_del);
            ps.setInt(1, cmbAcc_UnitCode);
            ps.setInt(2, OfficeCode);
            ps.setInt(3, txtCB_Year);
            ps.setInt(4, txtCB_Month);
            ps.executeUpdate();
           System.out.println("success2***********************************************************************");
            ps.close();


            /**
                              * Delete all the Previous entries in Transaction Table
                              */
            String sqlquery5 =
                "delete from fas_sub_ledger_transaction where accounting_unit_id=? and accounting_for_office_id=? " +
                " and year=? and month=? ";
            ps2 = con.prepareStatement(sqlquery5);
            ps2.setInt(1, cmbAcc_UnitCode);
            ps2.setInt(2, OfficeCode);
            ps2.setInt(3, txtCB_Year);
            ps2.setInt(4, txtCB_Month);
            ps2.executeUpdate();
            ps2.close();


            /**
         *  Part I
         */

            String sqlquery =
                "select account_head_code,financial_year,current_year_debit_balance,current_year_credit_balance, " +
                " current_month_debit,current_month_credit,month_closing_balance,month_closing_bal_dr_cr_ind,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,DR_UPDATE_LAST_DATE,CR_UPDATE_LAST_DATE from fas_sub_ledger_master " +
                " where accounting_unit_id=? and accounting_for_office_id=? and year=? and month=? order by account_head_code,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE";
            ps = con.prepareStatement(sqlquery);
            ps.setInt(1, cmbAcc_UnitCode);
            ps.setInt(2, OfficeCode);
            ps.setInt(3, txtCB_Year1);
            ps.setInt(4, txtCB_Month1);
            results = ps.executeQuery();
            System.out.println("success3***********************************************************************");
            while (results.next()) {
              //  System.out.println("inside while..16Apr2012*****");
                cur_yr_crbal =
                        results.getDouble("CURRENT_MONTH_CREDIT") + results.getDouble("CURRENT_YEAR_CREDIT_BALANCE");
              //  System.out.println("cur_yr_crbal ***"+cur_yr_crbal);
                cur_yr_drbal =
                        results.getDouble("CURRENT_MONTH_DEBIT") + results.getDouble("CURRENT_YEAR_DEBIT_BALANCE");
                Month_OB_Bal = results.getDouble("MONTH_CLOSING_BALANCE");
                Month_OB_Cr_Dr_ind =
                        results.getString("MONTH_CLOSING_BAL_DR_CR_IND");
                Date Dr_update_last = results.getDate("DR_UPDATE_LAST_DATE");
                Date Cr_update_last = results.getDate("CR_UPDATE_LAST_DATE");


                achcode = results.getInt("account_head_code");
              //  System.out.println("achcode ***"+achcode);
                sltypecode = results.getInt("SUB_LEDGER_TYPE_CODE");
               // System.out.println("sltypecode ***"+sltypecode);
                slcode = results.getLong("sub_ledger_code");
              //  System.out.println("slcode ***"+slcode);

             //   System.out.println("hi..1" + achcode);
              //  System.out.println("NFinYear*******************************" +                     NFinYear);

                String sqlquery1 =
                    "insert into fas_sub_ledger_master(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR,YEAR,MONTH, " +
                    "ACCOUNT_HEAD_CODE,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,PROJECT_ID,UPTO_DEBIT_BALANCE,UPTO_CREDIT_BALANCE," +
                    "CURRENT_YEAR_DEBIT_BALANCE,CURRENT_YEAR_CREDIT_BALANCE,MONTH_OPENING_BALANCE, " +
                    "MONTH_OPENING_BAL_DR_CR_IND,CURRENT_MONTH_DEBIT,CURRENT_MONTH_CREDIT,MONTH_CLOSING_BALANCE, " +
                    "MONTH_CLOSING_BAL_DR_CR_IND,DR_UPDATE_LAST_DATE,CR_UPDATE_LAST_DATE,UPDATED_BY_USER_ID," +
                    "UPDATED_DATE ) " +
                    "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                ps2 = con.prepareStatement(sqlquery1);
                ps2.setInt(1, cmbAcc_UnitCode);
                ps2.setInt(2, OfficeCode);
                ps2.setString(3, NFinYear);
                ps2.setInt(4, txtCB_Year);
                ps2.setInt(5, txtCB_Month);
                ps2.setInt(6, achcode);

                ps2.setInt(7, sltypecode);
                ps2.setLong(8, slcode);
                ps2.setInt(9, 0);
                ps2.setInt(10, 0);
                ps2.setInt(11, 0);
                ps2.setDouble(12, cur_yr_drbal);
                ps2.setDouble(13, cur_yr_crbal);
                ps2.setDouble(14, Month_OB_Bal);
                ps2.setString(15, Month_OB_Cr_Dr_ind);
                ps2.setDouble(16, 0);
                ps2.setDouble(17, 0);
                ps2.setDouble(18, Month_OB_Bal);
                ps2.setString(19, Month_OB_Cr_Dr_ind);
                ps2.setDate(20, Dr_update_last);
                ps2.setDate(21, Cr_update_last);
                ps2.setString(22, userid);
                ps2.setTimestamp(23, ts);
                ps2.executeUpdate();
               // System.out.println("success4***********************************************************************");
                ps2.close();
              //  System.out.println("hi..2");

            } //End While(results.next())

            results.close();
            ps.close();


            /**
         * Part II
         */

            String sql =
                " select distinct achcode,sltypecode,slcode from FAS_HEAD_SLTYPE_VIEW " +
                " where accounting_unit_id=? and accounting_for_office_id=? and cashbook_year=? and cashbook_month=?";
            ps1 = con.prepareStatement(sql);
            ps1.setInt(1, cmbAcc_UnitCode);
            ps1.setInt(2, OfficeCode);
            ps1.setInt(3, txtCB_Year);
            ps1.setInt(4, txtCB_Month);
            results = ps1.executeQuery();
          //  System.out.println("success5***********************************************************************");

            while (results.next()) {
            	Long sc=results.getLong("slcode");
            	// System.out.println("sc:::::"+sc);
                achcode = results.getInt("achcode");
                sltypecode = results.getInt("sltypecode");
               // slcode = results.getInt("slcode");
                
               // System.out.println("slcode::::::::::::::::::::"+slcode);
               
                
                

                System.out.println("head code" + achcode);

                String sql_qur =
                    " select  ACCOUNT_HEAD_CODE,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE " +
                    " from FAS_SUB_LEDGER_MASTER " +
                    " where accounting_unit_id=? and accounting_for_office_id=? " +
                    " and year=? and month=?" +
                    " and ACCOUNT_HEAD_CODE=? and SUB_LEDGER_TYPE_CODE=? and SUB_LEDGER_CODE=? ";

                ResultSet results2 = null;
                ps2 = con.prepareStatement(sql_qur);
                ps2.setInt(1, cmbAcc_UnitCode);
                ps2.setInt(2, OfficeCode);
                ps2.setInt(3, txtCB_Year);
                ps2.setInt(4, txtCB_Month);
                ps2.setInt(5, achcode);
                ps2.setInt(6, sltypecode);
                ps2.setLong(7, sc);
                results2 = ps2.executeQuery();
                System.out.println("success6*******%%%%%%**************************");

                if (results2.next()) {
                    System.out.println("already exists,no need to insert***");
                } else {


                    String sqlquery1 =
                        "insert into fas_sub_ledger_master(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR,YEAR,MONTH, " +
                        "ACCOUNT_HEAD_CODE,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,PROJECT_ID,UPTO_DEBIT_BALANCE,UPTO_CREDIT_BALANCE," +
                        "CURRENT_YEAR_DEBIT_BALANCE,CURRENT_YEAR_CREDIT_BALANCE,MONTH_OPENING_BALANCE, " +
                        "MONTH_OPENING_BAL_DR_CR_IND,CURRENT_MONTH_DEBIT,CURRENT_MONTH_CREDIT,MONTH_CLOSING_BALANCE, " +
                        "MONTH_CLOSING_BAL_DR_CR_IND,DR_UPDATE_LAST_DATE,CR_UPDATE_LAST_DATE,UPDATED_BY_USER_ID," +
                        "UPDATED_DATE ) " +
                        "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    ps2 = con.prepareStatement(sqlquery1);
                    ps2.setInt(1, cmbAcc_UnitCode);
                    ps2.setInt(2, OfficeCode);
                    ps2.setString(3, NFinYear);
                    ps2.setInt(4, txtCB_Year);
                    ps2.setInt(5, txtCB_Month);
                    ps2.setInt(6, achcode);
                    ps2.setInt(7, sltypecode);
                  //  System.out.println("slcode:::"+slcode);
                    ps2.setLong(8, sc);
                  //  ps2.setInt(8, slcode);
                    
                    ps2.setInt(9, 0);
                    ps2.setInt(10, 0);
                    ps2.setInt(11, 0);
                    ps2.setDouble(12, 0);
                    ps2.setDouble(13, 0);
                    ps2.setDouble(14, 0);
                    ps2.setString(15, "CR");
                    ps2.setDouble(16, 0);
                    ps2.setDouble(17, 0);
                    ps2.setDouble(18, 0);
                    ps2.setString(19, "CR");
                    ps2.setDate(20, null);
                    ps2.setDate(21, null);
                    ps2.setString(22, userid);
                    ps2.setTimestamp(23, ts);
                    ps2.executeUpdate();
                  //  System.out.println("success7***********************************************************************");
                    ps2.close();
                }
                results2.close();
                ps2.close();

            }
            results.close();
            ps1.close();


            /**
         * Part III
         */

            // Ended insertion into fas_sub_ledger_master for all head,sltype,slcode wise
            // From here i'll start to find CRAmount and DRAmount and transaction part
          //  System.out.println("be4::::::");
            String fetchSL =
                " select ACCOUNT_HEAD_CODE,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE  from fas_sub_ledger_master " +
                " where accounting_unit_id=? and accounting_for_office_id=? " +
                " and year=? and month=?";
           // System.out.println("fetchSL:::"+fetchSL);
            ps2 = con.prepareStatement(fetchSL);
            ps2.setInt(1, cmbAcc_UnitCode);
            ps2.setInt(2, OfficeCode);
            ps2.setInt(3, txtCB_Year);
            ps2.setInt(4, txtCB_Month);
            results = ps2.executeQuery();
            System.out.println("success7***********************************************************************");
            while (results.next()) /** Main While Starts Here */
            {


                /**  ONE   */
                String fetchsltype =
                    " select MONTH_OPENING_BAL_DR_CR_IND,MONTH_OPENING_BALANCE" +
                    " from fas_sub_ledger_master " +
                    " where accounting_unit_id=? and accounting_for_office_id=? " +
                    " and year=? and month=? and ACCOUNT_HEAD_CODE=? " +
                    " and SUB_LEDGER_TYPE_CODE=? and SUB_LEDGER_CODE=?";

                PreparedStatement ps3 = con.prepareStatement(fetchsltype);
                ps3.setInt(1, cmbAcc_UnitCode);
                ps3.setInt(2, OfficeCode);
                ps3.setInt(3, txtCB_Year);
                ps3.setInt(4, txtCB_Month);
                ps3.setInt(5, results.getInt("ACCOUNT_HEAD_CODE"));
                ps3.setInt(6, results.getInt("SUB_LEDGER_TYPE_CODE"));
                ps3.setLong(7, results.getLong("SUB_LEDGER_CODE"));
                ResultSet res = ps3.executeQuery();
                System.out.println("success8***********************************************************************");
                if (res.next()) {
                    Month_OB_Bal_new = res.getDouble("MONTH_OPENING_BALANCE");
                    Month_OB_Cr_Dr_ind_new =
                            res.getString("MONTH_OPENING_BAL_DR_CR_IND");
                }

                res.close(); // NEW
                ps3.close(); // NEW


                /**  TWO  */
                String sqlquery6 = "";
                sqlquery6 =
                        "select  amount1,CRdate " + " from FAS_HEAD_SLTYPE_CR_VIEW where " +
                        " accounting_unit_id=? and accounting_for_office_id=? " +
                        " and cashbook_year=? and cashbook_month=? and ACHCODE=? " +
                        " and sltypecode=? and slcode=?";

                ps7 = con.prepareStatement(sqlquery6);
                ps7.setInt(1, cmbAcc_UnitCode);
                ps7.setInt(2, OfficeCode);
                ps7.setInt(3, txtCB_Year);
                ps7.setInt(4, txtCB_Month);
                ps7.setInt(5, results.getInt("ACCOUNT_HEAD_CODE"));
                ps7.setInt(6, results.getInt("SUB_LEDGER_TYPE_CODE"));
                ps7.setLong(7, results.getLong("SUB_LEDGER_CODE"));

                ResultSet results3 = ps7.executeQuery();
                System.out.println("success9***********************************************************************");
                while (results3.next()) {

                    CRAmount = CRAmount + results3.getDouble("amount1");

                    if (MaxCRdate == null)
                        MaxCRdate = results3.getDate("CRdate");
                    if (results3.getDate("CRdate") != null) {
                        if (MaxCRdate.compareTo(results3.getDate("CRdate")) <
                            0) // finding maximum date
                        {
                            MaxCRdate = results3.getDate("CRdate");
                        }
                    }

                }
                ps7.close();
                results3.close();


                /**  THREE  */
                sqlquery6 =
                        "select  amount1,DRdate " + " from FAS_HEAD_SLTYPE_DR_VIEW where " +
                        " accounting_unit_id=? and accounting_for_office_id=? " +
                        " and cashbook_year=? and cashbook_month=? and ACHCODE=? " +
                        " and sltypecode=? and slcode=?";

                ps7 = con.prepareStatement(sqlquery6);
                ps7.setInt(1, cmbAcc_UnitCode);
                ps7.setInt(2, OfficeCode);
                ps7.setInt(3, txtCB_Year);
                ps7.setInt(4, txtCB_Month);
                ps7.setInt(5, results.getInt("ACCOUNT_HEAD_CODE"));
                ps7.setInt(6, results.getInt("SUB_LEDGER_TYPE_CODE"));
                ps7.setLong(7, results.getLong("SUB_LEDGER_CODE"));
                results3 = ps7.executeQuery();
                System.out.println("success10***********************************************************************");
                while (results3.next()) {
                    DRAmount1 = DRAmount1 + results3.getDouble("amount1");
                    if (MaxDRdate == null)
                        MaxDRdate = results3.getDate("DRdate");

                    if (results3.getDate("DRdate") != null) {
                        if (MaxDRdate.compareTo(results3.getDate("DRdate")) <
                            0) {
                            MaxDRdate = results3.getDate("DRdate");
                        }
                    }
                }

             //   System.out.println("Final Amount is:" + DRAmount1);

                ps7.close();
                results3.close();


                System.out.println("Month_OB_Cr_Dr_ind_new00" + Month_OB_Cr_Dr_ind_new);

                if (Month_OB_Cr_Dr_ind_new.equalsIgnoreCase("CR")) {
                    Upto_Credit_Bal = Month_OB_Bal_new + CRAmount;
                    balance = -(DRAmount1 - (CRAmount + Month_OB_Bal_new));
                } else {
                    Upto_Debit_Bal = Month_OB_Bal_new + DRAmount1;
                    balance = -((Month_OB_Bal_new + DRAmount1) - CRAmount);
                }

                if (balance >= 0) {
                    MONTH_CLOSING_BAL_DR_CR_IND = "CR";
                } else {
                    MONTH_CLOSING_BAL_DR_CR_IND = "DR";
                }


                /** FOUR  */

                String sqlquery7 =
                    "update fas_sub_ledger_master set CURRENT_MONTH_DEBIT=?,CURRENT_MONTH_CREDIT=?, " +
                    " UPTO_DEBIT_BALANCE=?,UPTO_CREDIT_BALANCE=?,MONTH_CLOSING_BALANCE=?,MONTH_CLOSING_BAL_DR_CR_IND=?, " +
                    " DR_UPDATE_LAST_DATE=?,CR_UPDATE_LAST_DATE=? where accounting_unit_id=? and accounting_for_office_id=? " +
                    " and year=? and month=? and account_head_code=? and  SUB_LEDGER_TYPE_CODE=? and SUB_LEDGER_CODE=? ";
                ps3 = con.prepareStatement(sqlquery7);
                ps3.setDouble(1, DRAmount1);
                ps3.setDouble(2, CRAmount);
                ps3.setDouble(3, Upto_Debit_Bal);
                ps3.setDouble(4, Upto_Credit_Bal);
                ps3.setDouble(5, Math.abs(balance));
                ps3.setString(6, MONTH_CLOSING_BAL_DR_CR_IND);
                ps3.setDate(8, MaxDRdate);
                ps3.setDate(7, MaxCRdate);
                ps3.setInt(9, cmbAcc_UnitCode);
                ps3.setInt(10, OfficeCode);
                ps3.setInt(11, txtCB_Year);
                ps3.setInt(12, txtCB_Month);
                ps3.setInt(13, results.getInt("ACCOUNT_HEAD_CODE"));
                ps3.setInt(14, results.getInt("SUB_LEDGER_TYPE_CODE"));
                ps3.setLong(15, results.getLong("SUB_LEDGER_CODE"));
                ps3.executeUpdate();
                System.out.println("success11***********************************************************************");
                ps3.close();

                CRAmount = 0;
                DRAmount1 = 0;
                Upto_Credit_Bal = 0;
                Upto_Debit_Bal = 0;
                balance = 0;
                Month_OB_Bal_new = 0;
                Month_OB_Cr_Dr_ind_new = "";
                MaxCRdate = null;
                MaxDRdate = null;


                /** FIVE  */
                // From here i've to insert TRANSACTION part
                int count =
                    0; // which is used to check wheather that particular ACHCODE and SLTYPECODE and SLCODE has transaction in the current month
                // If not having u just insert NULL for all fields in TRANSACTION table
                int SLNO = 1; // Used in transaction table to store SL_NO

                // GET CR details
                String trans =
                    " select AMOUNT,VOUTYPE,VOUNO,CRDATE,CR_JOUR_TYPE_CODE,BILL_NO,BILL_DATE,AGREEMENT_NO,PARTICULARS  " +
                    " from FAS_HEAD_SLTYPE_CR_TRN_VIEW " +
                    " where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? " +
                    " and ACHCODE=? and SLTYPECODE=? and SLCODE=? ";

                /** Closed -- XYZ */
                PreparedStatement ps_trs1 = con.prepareStatement(trans);
                ps_trs1.setInt(1, cmbAcc_UnitCode);
                ps_trs1.setInt(2, OfficeCode);
                ps_trs1.setInt(3, txtCB_Year);
                ps_trs1.setInt(4, txtCB_Month);
                ps_trs1.setInt(5, results.getInt("ACCOUNT_HEAD_CODE"));
                ps_trs1.setInt(6, results.getInt("SUB_LEDGER_TYPE_CODE"));
                ps_trs1.setLong(7, results.getLong("SUB_LEDGER_CODE"));

                ResultSet rs_trs = ps_trs1.executeQuery();
            //    System.out.println("success12***********************************************************************");
                while (rs_trs.next()) {
                    count++;
                    System.out.println("first");
                    String trs_ins =
                        "insert into FAS_SUB_LEDGER_TRANSACTION" + "(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,YEAR,MONTH,ACCOUNT_HEAD_CODE," +
                        "SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,FINANCIAL_YEAR,PROJECT_ID,CREDIT_AMOUNT,CR_VOUCHER_TYPE,CR_VOUCHER_NO," +
                        "CR_VOUCHER_DATE,CR_JOURNAL_TYPE_CODE,BILL_NO,BILL_DATE,AGREEMENT_NO,PARTICULARS,UPDATED_BY_USER_ID,UPDATED_DATE,SL_NO)" +
                        " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                    /** Closed */
                    PreparedStatement ps_CR = con.prepareStatement(trs_ins);
                    ps_CR.setInt(1, cmbAcc_UnitCode);
                    ps_CR.setInt(2, OfficeCode);
                    ps_CR.setInt(3, txtCB_Year);
                    ps_CR.setInt(4, txtCB_Month);
                    ps_CR.setInt(5, results.getInt("ACCOUNT_HEAD_CODE"));
                    ps_CR.setInt(6, results.getInt("SUB_LEDGER_TYPE_CODE"));
                    ps_CR.setLong(7, results.getLong("SUB_LEDGER_CODE"));
                    ps_CR.setString(8, NFinYear);
                    ps_CR.setInt(9, 0);
                    ps_CR.setDouble(10, rs_trs.getDouble("AMOUNT"));
                    ps_CR.setString(11, rs_trs.getString("VOUTYPE"));
                    ps_CR.setInt(12, rs_trs.getInt("VOUNO"));
                    ps_CR.setDate(13, rs_trs.getDate("CRDATE"));
                    ps_CR.setInt(14, rs_trs.getInt("CR_JOUR_TYPE_CODE"));
                    ps_CR.setString(15, rs_trs.getString("BILL_NO"));
                    ps_CR.setDate(16, rs_trs.getDate("BILL_DATE"));
                    ps_CR.setString(17, rs_trs.getString("AGREEMENT_NO"));
                    ps_CR.setString(18, rs_trs.getString("PARTICULARS"));
                    ps_CR.setString(19, userid);
                    ps_CR.setTimestamp(20, ts);
                    ps_CR.setInt(21, SLNO);
                    ps_CR.executeUpdate();
                    System.out.println("success13***********************************************************************");
                    ps_CR.close();
                    /** Closed */

                    SLNO++;

                }


                ps_trs1.close();
                /** Closed -- XYZ */


                // GET DR details
               // System.out.println("here...7");
                trans =
" select AMOUNT,VOUTYPE,VOUNO,DRDATE,DR_JOUR_TYPE_CODE,BILL_NO,BILL_DATE,AGREEMENT_NO,PARTICULARS  " +
" from FAS_HEAD_SLTYPE_DR_TRN_VIEW " +
" where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? " +
" and ACHCODE=? and SLTYPECODE=? and SLCODE=? ";


                /** Closed -- IJK */
                PreparedStatement ps_trs2 = con.prepareStatement(trans);
                ps_trs2.setInt(1, cmbAcc_UnitCode);
                ps_trs2.setInt(2, OfficeCode);
                ps_trs2.setInt(3, txtCB_Year);
                ps_trs2.setInt(4, txtCB_Month);
                ps_trs2.setInt(5, results.getInt("ACCOUNT_HEAD_CODE"));
                ps_trs2.setInt(6, results.getInt("SUB_LEDGER_TYPE_CODE"));
                ps_trs2.setLong(7, results.getLong("SUB_LEDGER_CODE"));
                rs_trs = ps_trs2.executeQuery();
              //  System.out.println("success14***********************************************************************");
                while (rs_trs.next()) {
                    count++;
                    System.out.println("second..");
                    String trs_ins =
                        "insert into FAS_SUB_LEDGER_TRANSACTION" + "(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,YEAR,MONTH,ACCOUNT_HEAD_CODE," +
                        "SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,FINANCIAL_YEAR,PROJECT_ID,DEBIT_AMOUNT,DR_VOUCHER_TYPE,DR_VOUCHER_NO," +
                        "DR_VOUCHER_DATE,DR_JOURNAL_TYPE_CODE,BILL_NO,BILL_DATE,AGREEMENT_NO,PARTICULARS,UPDATED_BY_USER_ID,UPDATED_DATE,SL_NO)" +
                        " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                    /** Closed */
                    PreparedStatement ps_CR = con.prepareStatement(trs_ins);
                    ps_CR.setInt(1, cmbAcc_UnitCode);
                    ps_CR.setInt(2, OfficeCode);
                    ps_CR.setInt(3, txtCB_Year);
                    ps_CR.setInt(4, txtCB_Month);
                    ps_CR.setInt(5, results.getInt("ACCOUNT_HEAD_CODE"));
                    ps_CR.setInt(6, results.getInt("SUB_LEDGER_TYPE_CODE"));
                    ps_CR.setLong(7, results.getLong("SUB_LEDGER_CODE"));
                    ps_CR.setString(8, NFinYear);
                    ps_CR.setInt(9, 0);
                    ps_CR.setDouble(10, rs_trs.getDouble("AMOUNT"));
                    ps_CR.setString(11, rs_trs.getString("VOUTYPE"));
                    ps_CR.setInt(12, rs_trs.getInt("VOUNO"));
                    ps_CR.setDate(13, rs_trs.getDate("DRDATE"));
                    ps_CR.setInt(14, rs_trs.getInt("DR_JOUR_TYPE_CODE"));
                    ps_CR.setString(15, rs_trs.getString("BILL_NO"));
                    ps_CR.setDate(16, rs_trs.getDate("BILL_DATE"));
                    ps_CR.setString(17, rs_trs.getString("AGREEMENT_NO"));
                    ps_CR.setString(18, rs_trs.getString("PARTICULARS"));
                    ps_CR.setString(19, userid);
                    ps_CR.setTimestamp(20, ts);
                    ps_CR.setInt(21, SLNO);
                    ps_CR.executeUpdate();
                   System.out.println("success15***********************************************************************");
                    ps_CR.close();
                    /** Closed */

                    SLNO++;
                }

                ps_trs2.close();
                /** Closed -- IJK */


                if (count ==  0) // if so, there is no transaction  for the combination of ACHCODE and SLTYPECODE and SLCODE , so just insert NULL for all fields
                {
                    String trs_ins =
                        "insert into FAS_SUB_LEDGER_TRANSACTION" + "(ACCOUNTING_UNIT_ID," +
                        "ACCOUNTING_FOR_OFFICE_ID,YEAR,MONTH,ACCOUNT_HEAD_CODE," +
                        "SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,FINANCIAL_YEAR,PROJECT_ID," +
                        "UPDATED_BY_USER_ID,UPDATED_DATE,SL_NO)" +
                        " values (?,?,?,?,?,?,?,?,?,?,?,?)";

                    /** Closed */
                    PreparedStatement ps_CR = con.prepareStatement(trs_ins);
                    ps_CR.setInt(1, cmbAcc_UnitCode);
                    ps_CR.setInt(2, OfficeCode);
                    ps_CR.setInt(3, txtCB_Year);
                    //System.out.println("txtCB_Year:"+txtCB_Year);
                    ps_CR.setInt(4, txtCB_Month);
                    //System.out.println("txtCB_Month:"+txtCB_Month);
                    ps_CR.setInt(5, results.getInt("ACCOUNT_HEAD_CODE"));
                    ps_CR.setInt(6, results.getInt("SUB_LEDGER_TYPE_CODE"));
                 //   System.out.println("not end::");
                  //  System.out.println("final::"+results.getInt("SUB_LEDGER_CODE"));
                    ps_CR.setLong(7, results.getLong("SUB_LEDGER_CODE"));
                    ps_CR.setString(8, NFinYear);
                    ps_CR.setInt(9, 0);
                    ps_CR.setString(10, userid);
                    ps_CR.setTimestamp(11, ts);
                    ps_CR.setInt(12, 1); // SL_NO =1
                    ps_CR.executeUpdate();
                    System.out.println("success16***********************************************************************");
                    ps_CR.close();
                    /** Closed */
                }

                //         Ending transaction part

            } /** End While Loop for Main  */
            flag = true;

        } catch (Exception e) {
            System.out.println("Exception in Query::::" + e.getMessage());
            try {
                con.rollback();
            } catch (SQLException e1) {
                System.out.println("catch:" + e1);
            }
            String msg = "SubLedger Posting Details Not Updated:::";
            msg = msg + "<br><br>";
            sendMessage(response, msg, "ok");
            return;
        }


        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /**
         * GL Part
         */
        if (flag) {

            try {


                statement = null;
                results = null;
                ResultSet results2 = null;
                PreparedStatement ps = null;
                PreparedStatement ps1 = null;
                PreparedStatement ps2 = null;
                PreparedStatement ps3 = null;
                PreparedStatement ps4 = null;

                PreparedStatement ps6 = null;
                PreparedStatement ps7 = null;
                ResultSet results3 = null;
                ResultSet results4 = null;
                int achead = 0;
                int acchcode = 0;
                String finyear = null;

                /**
                      * deleteing current month entries first..
                      */
                String sql_del =
                    "delete from fas_general_ledger where accounting_unit_id=? and accounting_for_office_id=?  and year=? and month=? ";
                ps = con.prepareStatement(sql_del);
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, OfficeCode);
                ps.setInt(3, txtCB_Year);
                ps.setInt(4, txtCB_Month);
                ps.executeUpdate();

                System.out.println("success17***********************************************************************");
                /**
                     * Selecting particular fields from main General Ledger Table for the Preivous Month And Year (For Example-Oct Month - Nov Month)
                     */

                String sqlquery1 =
                    "select ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR,YEAR,MONTH,ACCOUNT_HEAD_CODE, " +
                    " CURRENT_YEAR_DEBIT_BALANCE,CURRENT_YEAR_CREDIT_BALANCE,CURRENT_MONTH_DEBIT,CURRENT_MONTH_CREDIT, " +
                    " MONTH_CLOSING_BALANCE,MONTH_CLOSING_BAL_DR_CR_IND,DR_UPDATE_LAST_DATE,CR_UPDATE_LAST_DATE " +
                    " from fas_general_ledger where accounting_unit_id=? and accounting_for_office_id=? " +
                    " and year=? and month=? order by account_head_code ";
                ps = con.prepareStatement(sqlquery1);
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, OfficeCode);
                ps.setInt(3, txtCB_Year1);
                ps.setInt(4, txtCB_Month1);
                results = ps.executeQuery();
                System.out.println("success18***********************************************************************");
                while (results.next()) //Start Main While
                {
                    achead = results.getInt("ACCOUNT_HEAD_CODE");
                    //System.out.println("AccHead is:"+achead);
                    // System.out.println("count is:"+count++);
                    finyear = results.getString("FINANCIAL_YEAR");


                    if (txtCB_Month == 4) // New financial yr starting
                    {
                        String ps_Asset_liablity =
                            "select ACCOUNT_HEAD_CODE from COM_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_CODE=? and MAJOR_HEAD_CODE in('L','A')";
                        PreparedStatement ps_AL =
                            con.prepareStatement(ps_Asset_liablity);
                        ps_AL.setInt(1, achead);
                        ResultSet rs_AL = ps_AL.executeQuery();

                        if (rs_AL.next()) {
                            System.out.println("A or L heads");
                            cur_yr_crbal =
                                    results.getDouble("CURRENT_MONTH_CREDIT") +
                                    results.getDouble("CURRENT_YEAR_CREDIT_BALANCE");
                            cur_yr_drbal =
                                    results.getDouble("CURRENT_MONTH_DEBIT") +
                                    results.getDouble("CURRENT_YEAR_DEBIT_BALANCE");
                        } else {
                            System.out.println("not A or L heads");
                            cur_yr_crbal = 0;
                            cur_yr_drbal = 0;
                        }

                    } else {
                        cur_yr_crbal =
                                results.getDouble("CURRENT_MONTH_CREDIT") +
                                results.getDouble("CURRENT_YEAR_CREDIT_BALANCE");
                        cur_yr_drbal =
                                results.getDouble("CURRENT_MONTH_DEBIT") +
                                results.getDouble("CURRENT_YEAR_DEBIT_BALANCE");

                    }


                    Month_OB_Bal = results.getDouble("MONTH_CLOSING_BALANCE");
                    Month_OB_Cr_Dr_ind =
                            results.getString("MONTH_CLOSING_BAL_DR_CR_IND");
                    Date Dr_update_last =
                        results.getDate("DR_UPDATE_LAST_DATE");
                    Date Cr_update_last =
                        results.getDate("CR_UPDATE_LAST_DATE");

                    NFinYear = "";
                    if ((txtCB_Month >= 1 && txtCB_Month <= 3)) {
                        NFinYear = (txtCB_Year - 1) + "-" + (txtCB_Year);
                    } else {
                        NFinYear = (txtCB_Year) + "-" + (txtCB_Year + 1);
                    }
                    //NFinYear=
                    String sqlquery3 =
                        "insert into fas_general_ledger(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID " +
                        " ,FINANCIAL_YEAR,YEAR,MONTH,ACCOUNT_HEAD_CODE,UPTO_DEBIT_BALANCE,UPTO_CREDIT_BALANCE, " +
                        "CURRENT_YEAR_DEBIT_BALANCE,CURRENT_YEAR_CREDIT_BALANCE,MONTH_OPENING_BALANCE, " +
                        "MONTH_OPENING_BAL_DR_CR_IND,CURRENT_MONTH_DEBIT,CURRENT_MONTH_CREDIT,MONTH_CLOSING_BALANCE, " +
                        "MONTH_CLOSING_BAL_DR_CR_IND,DR_UPDATE_LAST_DATE,CR_UPDATE_LAST_DATE,UPDATED_BY_USER_ID, " +
                        "UPDATED_DATE) " +
                        "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    ps2 = con.prepareStatement(sqlquery3);
                    ps2.setInt(1, cmbAcc_UnitCode);
                    ps2.setInt(2, OfficeCode);
                    ps2.setString(3, NFinYear);
                    ps2.setInt(4, txtCB_Year);
                    ps2.setInt(5, txtCB_Month);
                    ps2.setInt(6, achead);
                    ps2.setInt(7, 0);
                    ps2.setInt(8, 0);
                    ps2.setDouble(9, cur_yr_drbal);
                    ps2.setDouble(10, cur_yr_crbal);
                    ps2.setDouble(11, Month_OB_Bal);
                    ps2.setString(12, Month_OB_Cr_Dr_ind);
                    ps2.setInt(13, 0);
                    ps2.setInt(14, 0);
                    ps2.setDouble(15, Month_OB_Bal);
                    ps2.setString(16, Month_OB_Cr_Dr_ind);
                    ps2.setDate(17, Dr_update_last);
                    ps2.setDate(18, Cr_update_last);
                    ps2.setString(19, userid);
                    ps2.setTimestamp(20, ts);
                    ps2.executeUpdate();
                    System.out.println("success19***********************************************************************");
                    ps2.close();
                    //}


                } //End Main While()

                results.close();
                ps.close();


                /**
                     * Insert Account Heads Which are not available in the previous month
                     */

                String sqlqueryselect = "";
                if (cmbAcc_UnitCode == 5) // for the case of banking section
                {
                    //System.out.println("inner if banking");
                    sqlqueryselect =
                            "select distinct achcode from  FAS_BANK_HEADS_VIEW " +
                            " where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? " +
                            " and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? ";
                } else {
                    //System.out.println("inner else other than banking unit");
                    sqlqueryselect =
                            "select distinct achcode from FAS_NON_BANK_HEADS_VIEW " +
                            " where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? " +
                            " and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? ";
                }
                ps1 = con.prepareStatement(sqlqueryselect);
                ps1.setInt(1, cmbAcc_UnitCode);
                ps1.setInt(2, OfficeCode);
                ps1.setInt(3, txtCB_Year);
                ps1.setInt(4, txtCB_Month);
                results2 = ps1.executeQuery();
                while (results2.next()) //Start While(results2.next())
                {
                    achcode = results2.getInt("achcode");
                    //  System.out.println("count inside acc is:"+ ++count1);
                    // System.out.println("Inner if accheacode is:"+achcode);

                    String sqlquery2 =
                        "select account_head_code,MONTH_OPENING_BAL_DR_CR_IND " +
                        " from fas_general_ledger where account_head_code=? and " +
                        " year=? and month=? and accounting_unit_id=? and accounting_for_office_id=? ";
                    ps2 = con.prepareStatement(sqlquery2);
                    ps2.setInt(1, achcode);
                    ps2.setInt(2, txtCB_Year);
                    ps2.setInt(3, txtCB_Month);
                    ps2.setInt(4, cmbAcc_UnitCode);
                    ps2.setInt(5, OfficeCode);
                    results3 = ps2.executeQuery();
                    if (results3.next()) //Start if(results3.next())
                    {
                        // Nothing to here
                        System.out.println("already exist" +
                                           results3.getInt("account_head_code"));
                        System.out.println(results3.getString("MONTH_OPENING_BAL_DR_CR_IND"));

                    } //End if(results3.next())
                    else {
                        NFinYear = "";
                        if ((txtCB_Month >= 1 && txtCB_Month <= 3)) {
                            NFinYear = (txtCB_Month - 1) + "-" + (txtCB_Month);
                        } else {
                            NFinYear = (txtCB_Year) + "-" + (txtCB_Year + 1);
                        }
                        System.out.println("Inner else 3 accheacode");
                        String sqlquery3 =
                            "insert into fas_general_ledger(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR," +
                            "YEAR,MONTH,ACCOUNT_HEAD_CODE,UPTO_DEBIT_BALANCE,UPTO_CREDIT_BALANCE,CURRENT_YEAR_DEBIT_BALANCE," +
                            "CURRENT_YEAR_CREDIT_BALANCE,MONTH_OPENING_BALANCE,MONTH_OPENING_BAL_DR_CR_IND,CURRENT_MONTH_DEBIT," +
                            "CURRENT_MONTH_CREDIT,MONTH_CLOSING_BALANCE,MONTH_CLOSING_BAL_DR_CR_IND,DR_UPDATE_LAST_DATE," +
                            "CR_UPDATE_LAST_DATE,UPDATED_BY_USER_ID,UPDATED_DATE) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                        ps4 = con.prepareStatement(sqlquery3);
                        ps4.setInt(1, cmbAcc_UnitCode);
                        ps4.setInt(2, OfficeCode);
                        ps4.setString(3, NFinYear);
                        ps4.setInt(4, txtCB_Year);
                        ps4.setInt(5, txtCB_Month);
                        ps4.setInt(6, achcode);
                        ps4.setDouble(7, 0);
                        ps4.setDouble(8, 0);
                        ps4.setDouble(9, 0);
                        ps4.setDouble(10, 0);
                        ps4.setDouble(11, 0);
                        ps4.setString(12, "CR");
                        ps4.setDouble(13, 0);
                        ps4.setDouble(14, 0);
                        ps4.setDouble(15, 0);
                        ps4.setString(16, "CR");
                        ps4.setDate(17, null);
                        ps4.setDate(18, null);
                        ps4.setString(19, userid);
                        ps4.setTimestamp(20, ts);
                        ps4.executeUpdate();
                        ps4.close();
                    }
                    ps2.close();
                    results3.close();
                } //End While(results2.next())
                ps1.close();
                results2.close();


                String sqlquery5 =
                    "select ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR,YEAR,MONTH,ACCOUNT_HEAD_CODE, " +
                    " CURRENT_YEAR_DEBIT_BALANCE,CURRENT_YEAR_CREDIT_BALANCE,CURRENT_MONTH_DEBIT,CURRENT_MONTH_CREDIT, " +
                    " MONTH_OPENING_BALANCE,MONTH_OPENING_BAL_DR_CR_IND " +
                    " from fas_general_ledger where accounting_unit_id=? and accounting_for_office_id=? " +
                    " and year=? and month=? order by account_head_code";
                ps1 = con.prepareStatement(sqlquery5);
                ps1.setInt(1, cmbAcc_UnitCode);
                ps1.setInt(2, OfficeCode);
                ps1.setInt(3, txtCB_Year);
                ps1.setInt(4, txtCB_Month);
                results2 = ps1.executeQuery();
                System.out.println("b4");
                while (results2.next()) //Start second Main While
                {
                    System.out.println("details.." + cmbAcc_UnitCode + "  " +
                                       OfficeCode + "  " + txtCB_Year + "  " +
                                       txtCB_Month);
                    // System.out.println("second main while...");
                    acchcode = results2.getInt("ACCOUNT_HEAD_CODE");
                    System.out.println("Inner Second While AHCode:" +
                                       acchcode);
                    System.out.println("indicator" +
                                       results2.getString("MONTH_OPENING_BAL_DR_CR_IND"));
                    Month_OB_Bal_new =
                            results2.getDouble("MONTH_OPENING_BALANCE");
                    Month_OB_Cr_Dr_ind_new =
                            results2.getString("MONTH_OPENING_BAL_DR_CR_IND");
                    // System.out.println("Month_OB_Cr_Dr_ind_new  now added"+results2.getString("MONTH_OPENING_BAL_DR_CR_IND"));
                    //Change on Date 18-Dec-2006 by B//
                    String sqlquery6 = "";


                    if (cmbAcc_UnitCode == 5) // banking section
                    {
                        System.out.println("CR banking");
                        sqlquery6 =
                                "select  amount1,CRdate " + " from FAS_BANK_CR_HEADS_VIEW where " +
                                " accounting_unit_id=? and accounting_for_office_id=? " +
                                " and cashbook_year=? and cashbook_month=? and ACHCODE=?";
                    } else {
                        System.out.println("CR office");

                        sqlquery6 =
                                "select  amount1,CRdate " + " from FAS_NON_BANK_CR_HEADS_VIEW where " +
                                " accounting_unit_id=? and accounting_for_office_id=? " +
                                " and cashbook_year=? and cashbook_month=? and ACHCODE=?";
                    }

                    //System.out.println("sqlquery6..."+sqlquery6);
                    //System.out.println("1st CR query");
                    ps7 = con.prepareStatement(sqlquery6);
                    ps7.setInt(1, cmbAcc_UnitCode);
                    ps7.setInt(2, OfficeCode);
                    ps7.setInt(3, txtCB_Year);
                    ps7.setInt(4, txtCB_Month);
                    ps7.setInt(5, acchcode);
                    results3 = ps7.executeQuery();
                    //System.out.println("Result Set is:"+results3);
                    while (results3.next()) {
                        //    System.out.println("results is results3.getDouble:"+results3.getDouble("amount1"));
                        CRAmount = CRAmount + results3.getDouble("amount1");

                        if (MaxCRdate == null)
                            MaxCRdate = results3.getDate("CRdate");
                        if (results3.getDate("CRdate") != null) {
                            if (MaxCRdate.compareTo(results3.getDate("CRdate")) <
                                0) // finding maximum date
                            {
                                MaxCRdate = results3.getDate("CRdate");
                                //         System.out.println("inside CRdate");
                            }
                        }

                    }
                    //   System.out.println("Amount is:"+CRAmount);
                    ps7.close();
                    results3.close();

                    String sql1 = "";
                    if (cmbAcc_UnitCode == 5) // banking section
                    {
                        //System.out.println("in 5");

                        sql1 =
"select  amount2,DRdate " + " from FAS_BANK_DR_HEADS_VIEW where " +
 " accounting_unit_id=? and accounting_for_office_id=? " +
 " and cashbook_year=? and cashbook_month=? and ACHCODE=?";

                        System.out.println("DR banking section");
                    } else {
                        System.out.println("DR office");
                        sql1 =
"select  amount2,DRdate " + " from FAS_NON_BANK_DR_HEADS_VIEW where " +
 " accounting_unit_id=? and accounting_for_office_id=? " +
 " and cashbook_year=? and cashbook_month=? and ACHCODE=?";
                    }

                    // System.out.println("sql1..."+sql1);
                    //System.out.println("2nd DR query");
                    ps6 = con.prepareStatement(sql1);
                    ps6.setInt(1, cmbAcc_UnitCode);
                    ps6.setInt(2, OfficeCode);
                    ps6.setInt(3, txtCB_Year);
                    ps6.setInt(4, txtCB_Month);
                    ps6.setInt(5, acchcode);
                    results4 = ps6.executeQuery();
                    while (results4.next()) {
                        //      System.out.println("Amount in Second Select getDouble(amount2)..:"+results4.getDouble("amount2"));
                        DRAmount1 = DRAmount1 + results4.getDouble("amount2");
                        //    System.out.println(MaxDRdate);
                        //  System.out.println("results4.getDate(\"DRdate\");"+results4.getDate("DRdate"));
                        if (MaxDRdate == null)
                            MaxDRdate = results4.getDate("DRdate");

                        if (results4.getDate("DRdate") != null) {
                            if (MaxDRdate.compareTo(results4.getDate("DRdate")) <
                                0) {
                                MaxDRdate = results4.getDate("DRdate");
                                //         System.out.println("inside DRdate");
                            }
                        }
                        //System.out.println("Final Amount is:"+DRAmount1);
                    }
                    System.out.println("Final Amount is:" + DRAmount1);
                    ps6.close();
                    results4.close();

                    // System.out.println("here comes 2");
                    System.out.println("Month_OB_Cr_Dr_ind_new00" +
                                       Month_OB_Cr_Dr_ind_new);
                    // System.out.println("CRAmount"+CRAmount);
                    //System.out.println("DRAmount1"+DRAmount1);
                    //System.out.println("Upto_Debit_Bal"+Upto_Debit_Bal);
                    //System.out.println("Upto_Credit_Bal"+Upto_Credit_Bal);
                    //System.out.println("balance"+balance);
                    if (Month_OB_Cr_Dr_ind_new.equalsIgnoreCase("CR")) {
                        Upto_Credit_Bal = Month_OB_Bal_new + CRAmount;
                        balance = -(DRAmount1 - (CRAmount + Month_OB_Bal_new));
                    } else {
                        Upto_Debit_Bal = Month_OB_Bal_new + DRAmount1;
                        balance = -((Month_OB_Bal_new + DRAmount1) - CRAmount);
                    }
                    //System.out.println("here comes 3");
                    if (balance >= 0) {
                        MONTH_CLOSING_BAL_DR_CR_IND = "CR";
                    } else {
                        MONTH_CLOSING_BAL_DR_CR_IND = "DR";
                    }

                    System.out.println("here comes 4");
                    String sqlquery7 =
                        "update fas_general_ledger set CURRENT_MONTH_DEBIT=?,CURRENT_MONTH_CREDIT=?, " +
                        " UPTO_DEBIT_BALANCE=?,UPTO_CREDIT_BALANCE=?,MONTH_CLOSING_BALANCE=?,MONTH_CLOSING_BAL_DR_CR_IND=?, " +
                        " DR_UPDATE_LAST_DATE=?,CR_UPDATE_LAST_DATE=? where accounting_unit_id=? and accounting_for_office_id=? " +
                        " and year=? and month=? and account_head_code=? ";
                    ps3 = con.prepareStatement(sqlquery7);
                    ps3.setDouble(1, DRAmount1);
                    ps3.setDouble(2, CRAmount);
                    ps3.setDouble(3, Upto_Debit_Bal);
                    ps3.setDouble(4, Upto_Credit_Bal);
                    ps3.setDouble(5, Math.abs(balance));
                    ps3.setString(6, MONTH_CLOSING_BAL_DR_CR_IND);
                    ps3.setDate(8, MaxDRdate);
                    ps3.setDate(7, MaxCRdate);
                    ps3.setInt(9, cmbAcc_UnitCode);
                    ps3.setInt(10, OfficeCode);
                    ps3.setInt(11, txtCB_Year);
                    ps3.setInt(12, txtCB_Month);
                    ps3.setInt(13, acchcode);
                    ps3.executeUpdate();

                    ps3.close();
                    //System.out.println("here comes 5");
                    CRAmount = 0;
                    DRAmount1 = 0;
                    Upto_Credit_Bal = 0;
                    Upto_Debit_Bal = 0;
                    balance = 0;
                    Month_OB_Bal_new = 0;
                    Month_OB_Cr_Dr_ind_new = "";
                    MaxCRdate = null;
                    MaxDRdate = null;
                } //End second Main While
                ps1.close();
                results2.close();

                System.out.println("GeneralLedger Posting Details has been Successfully Updated");
                flag2 = true;

            } catch (Exception e) {
                System.out.println("Exception in Main:" + e);
                try {
                    con.rollback();
                } catch (SQLException e1) {
                    System.out.println("catch:" + e1);
                }
                String msg = "GeneralLedger Posting Details Not Updated";
                msg = msg + "<br><br>";
                sendMessage(response, msg, "ok");
                return;

            }

        } else {
            try {
                con.rollback();
            } catch (SQLException e1) {
                System.out.println("catch:" + e1);
            }
            String msg = "SubLedger Posting Details Not Updated****";
            msg = msg + "<br><br>";
            sendMessage(response, msg, "ok");
            return;
        }


        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        /**
           *  TB Part
           *
           */

        if (flag2 == true) {
            //---------------------------------------------------------------------------------
            statement = null;
            PreparedStatement ps = null;
            PreparedStatement ps1 = null;
            PreparedStatement ps2 = null;
            CallableStatement cs = null;
            CallableStatement cs1 = null;
            /**
                    * Should not allow for Generating TB if Vouchers in Journal are not verified
                    */
            int journal_count = 0;
            try {

                String sql =
                    "" + "select                                                         \n" +
                    "  count(*) as v_count                                          \n" +
                    "from                                                           \n" +
                    "  fas_journal_master a ,                                       \n" +
                    "  fas_journal_transaction b                                    \n" +
                    "where                                                          \n" +
                    "    a.accounting_unit_id = ?                                   \n" +
                    "and a.cashbook_month= ?                                        \n" +
                    "and a.cashbook_year = ?                                        \n" +
                    "and a.accounting_unit_id=b.accounting_unit_id                  \n" +
                    "and a.accounting_for_office_id =b.accounting_for_office_id     \n" +
                    "and a.cashbook_year=b.cashbook_year                            \n" +
                    "and a.cashbook_month=b.cashbook_month                          \n" +
                    "and a.voucher_no=b.voucher_no                                  \n" +
                    "and a.JOURNAL_STATUS='L'                                       \n" +
                    "and b.VERIFIED is null and a.created_by_module in ( 'GJV', 'LJV' )   ";

                ps1 = con.prepareStatement(sql);
                ps1.setInt(1, cmbAcc_UnitCode);
                ps1.setInt(2, txtCB_Month);
                ps1.setInt(3, txtCB_Year);
                System.out.println("before execution");
                ResultSet rs = ps1.executeQuery();
                System.out.println("after execution");
                while (rs.next()) {
                    journal_count = rs.getInt("v_count");
                }

                System.out.println("journal_count--->" + journal_count);

            } catch (Exception e) {
                System.out.println(e);
            }
            
            /*
             ** Should not allow for Generating TB if Vouchers in Payment are not verified
             */
                    int pay_counted = 0;
                    try {

                        String sql =
                            "" + "select                                                         \n" +
                            "  count(*) as v_count                                          \n" +
                            "from                                                           \n" +
                            "  fas_payment_master a ,                                       \n" +
                            "  fas_payment_transaction b                                    \n" +
                            "where                                                          \n" +
                            "    a.accounting_unit_id = ?                                   \n" +
                            "and a.cashbook_month= ?                                        \n" +
                            "and a.cashbook_year = ?                                        \n" +
                            "and a.accounting_unit_id=b.accounting_unit_id                  \n" +
                            "and a.accounting_for_office_id =b.accounting_for_office_id     \n" +
                            "and a.cashbook_year=b.cashbook_year                            \n" +
                            "and a.cashbook_month=b.cashbook_month                          \n" +
                            "and a.voucher_no=b.voucher_no                                  \n" +
                            "and a.PAYMENT_STATUS='L'                                       \n" +
                            "and b.VERIFIED is null and a.created_by_module in ( 'BPF', 'BPP' )   ";

                        ps1 = con.prepareStatement(sql);
                        ps1.setInt(1, cmbAcc_UnitCode);
                        ps1.setInt(2, txtCB_Month);
                        ps1.setInt(3, txtCB_Year);
                        System.out.println("before execution");
                        ResultSet rs = ps1.executeQuery();
                        System.out.println("after execution");
                        while (rs.next()) {
                            pay_counted = rs.getInt("v_count");
                        }

                        System.out.println("pay_counted--::::::::::->" + pay_counted);

                    } catch (Exception e) {
                        System.out.println(e);
                    }

            /*
             ** Should not allow for Generating TB if Vouchers in Fund Trf at Office are not verified
             */
                    int ftOff_count = 0;
                    try {

                        String sql =
                            "" + "select                                                         \n" +
                            "  count(*) as v_count                                          \n" +
                            "from                                                           \n" +
                            "  FAS_FUND_TRF_FROM_OFFICE a                                        \n" +
                            "where                                                          \n" +
                            "    a.accounting_unit_id = ?                                   \n" +
                            "and a.cashbook_month= ?                                        \n" +
                            "and a.cashbook_year = ?                                        \n" +
                            "and a.TRANSFER_STATUS='L'                                       \n" +
                            "and a.VERIFY is null  ";

                        ps1 = con.prepareStatement(sql);
                        ps1.setInt(1, cmbAcc_UnitCode);
                        ps1.setInt(2, txtCB_Month);
                        ps1.setInt(3, txtCB_Year);
                        System.out.println("before execution");
                        ResultSet rs = ps1.executeQuery();
                        System.out.println("after execution");
                        while (rs.next()) {
                            ftOff_count = rs.getInt("v_count");
                        }

                        System.out.println("ftOff_count--->" + ftOff_count);

                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    
                    /*
                     ** Should not allow for Generating TB if Vouchers in Fund Trf at HO are not verified
                     */
                            int ftHO_count = 0;
                            try {

                                String sql =
                                    "" + "select                                                         \n" +
                                    "  count(*) as v_count                                          \n" +
                                    "from                                                           \n" +
                                    "  FAS_FUND_TRF_FROM_HO_MASTER a ,                                       \n" +
                                  "  FAS_FUND_TRF_FROM_HO_TRN b                                    \n" +
                                    "where                                                          \n" +
                                    "    a.accounting_unit_id = ?                                   \n" +
                                    "and a.cashbook_month= ?                                        \n" +
                                    "and a.cashbook_year = ?                                        \n" +
                                    "and a.accounting_unit_id=b.accounting_unit_id                  \n" +
                                    "and a.accounting_for_office_id =b.accounting_for_office_id     \n" +
                                    "and a.cashbook_year=b.cashbook_year                            \n" +
                                    "and a.cashbook_month=b.cashbook_month                          \n" +
                                    "and a.voucher_no=b.voucher_no                                  \n" +
                                    "and a.TRANSFER_STATUS='L'                                       \n" +
                                    "and b.VERIFY is null  ";

                                ps1 = con.prepareStatement(sql);
                                ps1.setInt(1, cmbAcc_UnitCode);
                                ps1.setInt(2, txtCB_Month);
                                ps1.setInt(3, txtCB_Year);
                                System.out.println("before execution");
                                ResultSet rs = ps1.executeQuery();
                                System.out.println("after execution");
                                while (rs.next()) {
                                    ftHO_count = rs.getInt("v_count");
                                }

                                System.out.println("ftHO_count--->" + ftHO_count);

                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            //---------------------------------------------------------------------------------

                            /**
                             * Should not allow for Freeze TB if 550350,550351 head of Trial Balance data not equal    in trial_balance table for the required freeze month                        */
                            BigDecimal amt_350=null;
                            BigDecimal amt_351=null;
                            int count_haed=0,unitid=0;
                            
                   ///Condition unrestricted apply by MK@31-12-2020         
                            try
                            {
                         		PreparedStatement ps11 =con.prepareStatement("select ACCOUNTING_UNIT_ID from FAS_TBFREEZE_CPS_UNRESTRICTED where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+
                         				" and cashbook_year="+txtCB_Year+" and cashbook_month="+txtCB_Month);
                         		System.out.println("select ACCOUNTING_UNIT_ID from FAS_TBFREEZE_CPS_UNRESTRICTED where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+
                         				"cashbook_year="+txtCB_Year+"cashbook_month="+txtCB_Month);
                            ResultSet rs = ps11.executeQuery();
                            
                            if (rs.next())
                            {
                            	unitid=rs.getInt("ACCOUNTING_UNIT_ID");
                            }
                            else
                            {
                            	unitid=0;
                            }
                            }
                            catch(Exception e)
                            {
                            	System.out.println("Exception is"+e);
                            }
                            System.out.println("unitid=CPS==>"+unitid);
                            System.out.println("cmbAcc_UnitCode==CPS=>"+cmbAcc_UnitCode);
                         	
                         	if(unitid!=cmbAcc_UnitCode)
                            {
                            
                            
                            try{
                            	//String sql="select rownum as id,CURRENT_MONTH_CREDIT from FAS_TRIAL_BALANCE where accounting_unit_id=?  and cashbook_month=?  and cashbook_year=? and account_head_code in (550350,550351)";
                            	String sql="SELECT SUM(kk) AS amt_550351, " +
                            			"  SUM(kjk)     AS amt_550350 " +
                            			"FROM " +
                            			"  (SELECT " +
                            			"    CASE " +
                            			"      WHEN amt1 >= 0 " +
                            			"      AND a_351 =1 " +
                            			"      THEN amt1 " +
                            			"      ELSE 0 " +
                            			"    END AS kk, " +
                            			"    CASE " +
                            			"      WHEN amt1 >= 0 " +
                            			"      AND a_351 =2 " +
                            			"      THEN amt1 " +
                            			"      ELSE 0 " +
                            			"    END AS kjk " +
                            			"  FROM " +
                            			"    (SELECT 1 AS a_351, " +
                            			"      CURRENT_MONTH_CREDIT amt1 " +
                            			"    FROM FAS_TRIAL_BALANCE " +
                            			"    WHERE accounting_unit_id=? " +
                            			"    AND cashbook_month      =? " +
                            			"    AND cashbook_year       =? " +
                            			"    AND account_head_code  IN (550351) " +
                            			"    UNION ALL " +
                            			"    SELECT 2 AS a_351, " +
                            			"      CURRENT_MONTH_CREDIT amt2 " +
                            			"    FROM FAS_TRIAL_BALANCE " +
                            			"    WHERE accounting_unit_id=? " +
                            			"    AND cashbook_month      =? " +
                            			"    AND cashbook_year       =? " +
                            			"    AND account_head_code  IN (550350) " +
                            			"    )as sm " +
                            			"  )as sm"; 
                            	System.out.println("Sql====>"+sql);
                            	ps1 = con.prepareStatement(sql);
                                 ps1.setInt(1, cmbAcc_UnitCode);
                                 ps1.setInt(2, txtCB_Month);
                                 ps1.setInt(3, txtCB_Year);
                                 ps1.setInt(4, cmbAcc_UnitCode);
                                 ps1.setInt(5, txtCB_Month);
                                 ps1.setInt(6, txtCB_Year);
                                 ResultSet rs = ps1.executeQuery();
                                 System.out.println("after execution");
                                 while (rs.next()) {
                                	/* if(rs.getInt(1)==1)
                                	 amt_350 = rs.getBigDecimal(2);
                                	 else if(rs.getInt(1)==2)
                                		 amt_351 = rs.getBigDecimal(2);*/
                                	 amt_350 = rs.getBigDecimal(2);
                                	 amt_351 = rs.getBigDecimal(1);
                                 }
                                 System.out.println(" >> "+amt_350+" >> "+amt_351);
                                /* if(cmbAcc_UnitCode==240  &&  txtCB_Year==2014 && (txtCB_Month==4 || txtCB_Month==5)) {
                                	 
                                	 
                                	 count_haed=0;
                                 }else{
                                 if(amt_350.equals(amt_351) )
                                 {
                                	 count_haed=0;
                                 }else{
                                	 count_haed=1;
                                 }
                                 }*/
                                 if(amt_350.equals(amt_351) )
                                 {
                                	 count_haed=0;
                                 }else{
                                	 count_haed=1;
                                 }
                            }
                            catch (Exception e) {
                                System.out.println("head credit amt equal");
                                e.printStackTrace();
                            }finally{
                            	
                                  try {
									ps1.close();
								} catch (SQLException e) {
								
									e.printStackTrace();
								}
                            }
                            }
                            
                            
                          //---------------------------------------------------------------------------------

                            /**
                             * Should not allow for Freeze TB if 391402,391403 head of Trial Balance data not equal    in trial_balance table for the required freeze month                        */
                            BigDecimal amt_402=null;
                            BigDecimal amt_403=null;
                            int count_haed1=0;
                            try{
                            	String sql="select sum(CURRENT_MONTH_CREDIT) h402, sum(CURRENT_MONTH_debit) h403  from FAS_TRIAL_BALANCE where accounting_unit_id=?  and cashbook_month=?  and cashbook_year=? and account_head_code in (391402,391403)";
                            	 ps1 = con.prepareStatement(sql);
                                 ps1.setInt(1, cmbAcc_UnitCode);
                                 ps1.setInt(2, txtCB_Month);
                                 ps1.setInt(3, txtCB_Year);
                                 ResultSet rs = ps1.executeQuery();
                                 System.out.println("after execution");
                                 while (rs.next()) {
                                	 if(rs.getInt(1)==1)
                                		 amt_402 = rs.getBigDecimal(1);
                                	 else if(rs.getInt(1)==2)
                                		 amt_403 = rs.getBigDecimal(2);
                                 }
                                 System.out.println(" >> "+amt_402+" >> "+amt_403);
                                if(txtCB_Year==2015){
                                	if(txtCB_Month>=7){
                                		 if(amt_402.equals(amt_403) )
                                         {
                                        	 count_haed1=0;
                                         }else{
                                        	 count_haed1=1;
                                         }	
                                	}else{
                                		 count_haed1=0;	
                                	}
                                }else if(txtCB_Year>2015){
                                 if(amt_402.equals(amt_403) )
                                 {
                                	 count_haed1=0;
                                 }else{
                                	 count_haed1=1;
                                 }
                                }else{
                                	 count_haed1=0;
                                }
                            }
                            catch (Exception e) {
                                System.out.println("head credit amt equal");
                                e.printStackTrace();
                            }finally{
                            	try{
                            		ps1.close();
                            	}catch(Exception e){
                            		e.printStackTrace();
                            	}
                            }
                            
                            
                            
                      
                            
                            
            //---------------------------------------------------------------------------------

                            
            /**
                             * Should not allow for Generating TB if Vouchers in Remittance are not verified
                             */
            int count = 0;
            try {
                String sql =
                    "select count(*) as v_count from fas_remittance   where accounting_unit_id=?  and cashbook_month=?  and cashbook_year=? and verified='N' and status is null ";
                ps1 = con.prepareStatement(sql);
                ps1.setInt(1, cmbAcc_UnitCode);
                ps1.setInt(2, txtCB_Month);
                ps1.setInt(3, txtCB_Year);
                System.out.println("before execution");
                ResultSet rs = ps1.executeQuery();
                System.out.println("after execution");
                while (rs.next()) {
                    count = rs.getInt("v_count");
                }

                System.out.println("count--->" + count);

            } catch (Exception e) {
                System.out.println(e);
            }



            //-----------------------------------------------------------Sl  Check Apr 2011-may 3d
            //--------------------------------Payment
            
             try {
            // System.out.println("enter%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
            

                 String sql =
                "select distinct a.account_head_code,a.VOUCHER_NO  from fas_payment_transaction a where accounting_unit_id=? and cashbook_month=? and cashbook_year=? and a.SUB_LEDGER_TYPE_CODE=0 and SUB_LEDGER_CODE=0 and \n" +
                 "voucher_no in(select voucher_no from fas_payment_master where  accounting_unit_id=? and cashbook_month=? and cashbook_year=? \n" +
                 "  and  created_by_module in('BPF','BPP') and payment_status='L') and a.account_head_code in(select account_head_code from com_mst_account_heads  \n" +
                 " where SUB_LEDGER_TYPE_APPLICABLE='Y' and SL_MANDATORY='Y') ";

                 ps1 = con.prepareStatement(sql);
                 
                 System.out.println("enter%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%"+sql);
                 ps1.setInt(1, cmbAcc_UnitCode);
                 ps1.setInt(2, txtCB_Month);
                 ps1.setInt(3, txtCB_Year);
                 ps1.setInt(4, cmbAcc_UnitCode);
                 ps1.setInt(5, txtCB_Month);
                 ps1.setInt(6, txtCB_Year);
                 System.out.println("before execution");
                 ResultSet rs = ps1.executeQuery();
                 System.out.println("after execution");
                 
                 while (rs.next()) {                 
                 if(flagg==0)
                 {
                     pay_vo="("+rs.getString("VOUCHER_NO")+")"+rs.getString("account_head_code");
               flagg=1;
                 }else{
                     pay_vo=pay_vo+","+""+"("+rs.getString("VOUCHER_NO")+")"+rs.getString("account_head_code");
                 }
                         System.out.println("enter%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%"+pay_vo);
                         pay_count++;
                    // }
                     //}
                 }
          

             } catch (Exception e) {
                 System.out.println(e);
             }
            //-------------------------------------------------------------------------------------------------------------Receipt
            
            try {
            System.out.println("enter*************************************************");
            
            String sql =
            "select distinct a.account_head_code,a.RECEIPT_NO  from FAS_RECEIPT_TRANSACTION a where accounting_unit_id=? and cashbook_month=? and cashbook_year=? and  a.SUB_LEDGER_TYPE_CODE=0 and SUB_LEDGER_CODE=0 and \n" +
            "RECEIPT_NO in(select RECEIPT_NO from fas_receipt_master where  accounting_unit_id=? and cashbook_month=? and cashbook_year=? \n" +
            "  and  created_by_module in('CR','BR') and RECEIPT_STATUS='L') and a.account_head_code in(select account_head_code from com_mst_account_heads  \n" +
            " where SUB_LEDGER_TYPE_APPLICABLE='Y' and SL_MANDATORY='Y') ";

            ps1 = con.prepareStatement(sql);
            
            System.out.println("enter*************************************************"+sql);
            ps1.setInt(1, cmbAcc_UnitCode);
            ps1.setInt(2, txtCB_Month);
            ps1.setInt(3, txtCB_Year);
            ps1.setInt(4, cmbAcc_UnitCode);
            ps1.setInt(5, txtCB_Month);
            ps1.setInt(6, txtCB_Year);
            System.out.println("before execution");
            ResultSet rs = ps1.executeQuery();
            System.out.println("after execution");
            while (rs.next()) {
            
                   if(a1==0)
 {
                     rec_vo="("+rs.getString("RECEIPT_NO")+")"+rs.getString("account_head_code");
                     a1=1;
 }
 else {
     rec_vo=rec_vo+","+""+"("+rs.getString("RECEIPT_NO")+")"+rs.getString("account_head_code");
     
 }
                     
                     rec_count++;
              
            
            }
             System.out.println("*************************************************"+rec_count+"String"+rec_vo);
        
       

            } catch (Exception e) {
            System.out.println(e);
            }
            
            //FAS_GL_PBSTATUS checking
            
            int PB_counted_status = 0,Count_test=0,generate_pbStatus=0;
            int bmonth=txtCB_Month-1;
            int byear=txtCB_Year-1;
            String fstatus_gl=null;
             if(txtCB_Month==1) 
            {
                System.out.println("january month in FAS_GL_PBSTATUS ");
                
                try {

                    
               	 String sql_one="Select case when(GL_PB_FREEZE_STATUS) is null then 'NF' else GL_PB_FREEZE_STATUS end As f_status, "+
						"	GL_PB_STATUS As Gen_Status, "+
               			"	count(*) as ct"+
               			"	  From Fas_Gl_Pbstatus "+
               			"	  Where Cashbook_Year   =? "+
               			"	  And Cashbook_Month=12 "+
               			"	  AND ACCOUNTING_UNIT_ID=? group by GL_PB_FREEZE_STATUS,GL_PB_STATUS";
                  
                    System.out.println("sql for jan month::::"+sql_one);
                      ps1=con.prepareStatement(sql_one);
                      ps1.setInt(1,byear);
                      ps1.setInt(2,cmbAcc_UnitCode);
                   
                    ResultSet rs = ps1.executeQuery();
                    while (rs.next()) {
                   // less_count++;
                        Count_test = rs.getInt("ct");
                        fstatus_gl=rs.getString("f_status");
                    }
                    if(Count_test>0) 
                    {
                     
                    	 if(fstatus_gl.equals("NF"))
                         {
                         	 generate_pbStatus++;
                         }
                         else
                         {
                         	generate_pbStatus=0;
                         }
                    }
                    else if(Count_test==0) 
                    {
                   	 //No Data for Any more month in GL
                   	 generate_pbStatus++;
                    }
                       rs.close();
                       ps1.close();
                    System.out.println("generate_pbStatus--::::::::::->" + generate_pbStatus);

                } catch (Exception e) {
                    System.out.println(e);
                }
                
            }
             else if(txtCB_Month==4) 
             {
            	 generate_pbStatus=0;
             }
            else if(txtCB_Month>1) 
            {
              
                try {

                    
                      String sql_one="Select case when(GL_PB_FREEZE_STATUS)is null then 'NF' else GL_PB_FREEZE_STATUS end As f_status, "+
					"	GL_PB_STATUS As Gen_Status, "+
					"	count(*) as ct"+
					"	  From FAS_GL_PBSTATUS "+
					"	  Where Cashbook_Year   =? "+
					"	  And Cashbook_Month="+bmonth+
					"	  AND ACCOUNTING_UNIT_ID=? group by GL_PB_FREEZE_STATUS,GL_PB_STATUS";
                  
                    System.out.println("sql_one for remaining month::::"+sql_one);
                      ps1=con.prepareStatement(sql_one);
                      ps1.setInt(1,txtCB_Year);
                      ps1.setInt(2,cmbAcc_UnitCode);
                   
                    ResultSet rs = ps1.executeQuery();
                    while (rs.next()) {
                   // less_count++;
                        Count_test = rs.getInt("ct");
                        fstatus_gl=rs.getString("f_status");
                    }
                    if(Count_test>0) 
                    {
                        if(fstatus_gl.equals("NF"))
                        {
                        	 generate_pbStatus++;
                        }
                        else
                        {
                        	generate_pbStatus=0;
                        }
                    }
                    else  if(Count_test==0) 
                    {
                   	 //No Data for Any more month in GL
                   	 generate_pbStatus++;
                    }
                       rs.close();
                       ps1.close();
                    System.out.println("generate_pbStatus--::::::::::->" + generate_pbStatus);

                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            
           //FAS_SL_PBSTATUS checking
             
             int Count_testsl=0,month_maxsl=0,prevmonthsl=0,generate_pbStatus_sl=0;
             int monthBef=txtCB_Month-1;
             int ybef=txtCB_Year-1;
             String fstatus=null;
              if(txtCB_Month==1) 
             {
                 System.out.println("january month in FAS_SL_PBSTATUS ");
                 
                 try {

                     
               	  String sql_one=" Select case when(Sl_Pb_Freeze_Status)is null then 'NF' else Sl_Pb_Freeze_Status end As f_status, "+
							"	Sl_Pb_Status As Gen_Status, "+
							"	count(*) as ct"+
							"	  From Fas_Sl_Pbstatus "+
							"	  Where Cashbook_Year   =? "+
							"	  And Cashbook_Month=12 "+
							"	  AND ACCOUNTING_UNIT_ID=? group by SL_PB_FREEZE_STATUS,SL_PB_STATUS";
                   
                     System.out.println("sql for month jan::::"+sql_one);
                       ps1=con.prepareStatement(sql_one);
                       ps1.setInt(1,ybef);
                       ps1.setInt(2,cmbAcc_UnitCode);
                    
                     ResultSet rs = ps1.executeQuery();
                     while (rs.next()) {
                    // less_count++;
                         Count_testsl = rs.getInt("ct");
                         fstatus=rs.getString("f_status");
                     }
                     if(Count_testsl>0) 
                     {
                    	 if(fstatus.equals("NF"))
                    	 {
                    		 generate_pbStatus_sl++;
                    	 }
                    	 else
                    	 {
                    		 generate_pbStatus_sl=0;
                    	 }
                     }
                     else  if(Count_testsl==0) 
                     {
                    	 //No Data for Any more month in SL
                   	  generate_pbStatus_sl++;
                     }
                        rs.close();
                        ps1.close();
                     System.out.println("generate_pbStatussl--::::::::::->" + generate_pbStatus_sl);

                 } catch (Exception e) {
                     System.out.println(e);
                 }
                 
             }
              //no checking for april
              else if(txtCB_Month==4) 
              {
            	  generate_pbStatus_sl=0;
              }
             else if(txtCB_Month>1) 
             {
               
                 try {

                     
                       String sql_one=" Select case when(Sl_Pb_Freeze_Status)is null then 'NF' else Sl_Pb_Freeze_Status end As f_status, "+
					"	Sl_Pb_Status As Gen_Status, "+
					"	count(*) as ct"+
					"	  From Fas_Sl_Pbstatus "+
					"	  Where Cashbook_Year   =? "+
					"	  And Cashbook_Month="+monthBef+
					"	  AND ACCOUNTING_UNIT_ID=? group by SL_PB_FREEZE_STATUS,SL_PB_STATUS";
                   
                     System.out.println("sql_one for remaining month::::"+sql_one);
                       ps1=con.prepareStatement(sql_one);
                       ps1.setInt(1,txtCB_Year);
                       ps1.setInt(2,cmbAcc_UnitCode);
                    
                     ResultSet rs = ps1.executeQuery();
                     while (rs.next()) {
                    // less_count++;
                         Count_testsl = rs.getInt("ct");
                        fstatus=rs.getString("f_status");
                        
                     }
                     if(Count_testsl>0) 
                     {
                    	 if(fstatus.equals("NF"))
                    	 {
                    		 generate_pbStatus_sl++;
                    	 }
                    	 else
                    	 {
                    		 generate_pbStatus_sl=0;
                    	 }
                     }
                     
                     else  if(Count_testsl==0) 
                     {
                    	 //No Data for Any more month in SL
                   	  generate_pbStatus_sl++;
                     }
                        rs.close();
                        ps1.close();
                     System.out.println("generate_pbStatussl--::::::::::->" + generate_pbStatus_sl);

                 } catch (Exception e) {
                     System.out.println(e);
                 }
             }
             
            //---------------------------------------------------------------------------------------------------------------  journal
            
            
            try {
            System.out.println("enter%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
            
             String sql =
            "select distinct a.account_head_code,a.VOUCHER_NO  from FAS_JOURNAL_TRANSACTION a where accounting_unit_id=? and cashbook_month=? and cashbook_year=? and  a.SUB_LEDGER_TYPE_CODE=0 and SUB_LEDGER_CODE=0 and \n" +
             "VOUCHER_NO in(select VOUCHER_NO from FAS_JOURNAL_MASTER where  accounting_unit_id=? and cashbook_month=? and cashbook_year=? \n" +
             "  and  created_by_module in('GJV','LJV') and JOURNAL_STATUS='L') and a.account_head_code in(select account_head_code from com_mst_account_heads  \n" +
             " where SUB_LEDGER_TYPE_APPLICABLE='Y' and SL_MANDATORY='Y') and a.account_head_code not in(select ACCOUNT_HEAD_CODE from FAS_APPLICABLE_SL_TYPE where SUB_LEDGER_TYPE_CODE=7) AND a.account_head_code NOT IN(390654)";

             ps1 = con.prepareStatement(sql);
             
             System.out.println("enter%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%"+sql);
             ps1.setInt(1, cmbAcc_UnitCode);
             ps1.setInt(2, txtCB_Month);
             ps1.setInt(3, txtCB_Year);
             ps1.setInt(4, cmbAcc_UnitCode);
             ps1.setInt(5, txtCB_Month);
             ps1.setInt(6, txtCB_Year);
             System.out.println("before execution");
             ResultSet rs = ps1.executeQuery();
             System.out.println("after execution");
             while (rs.next()) {
              
                 
                 
                     if(a2==0)
                     {
                     jou_vo="("+rs.getString("VOUCHER_NO")+")"+rs.getString("account_head_code");
                     a2=1;
                     }
                     else {
                         jou_vo=jou_vo+","+""+"("+rs.getString("VOUCHER_NO")+")"+rs.getString("account_head_code");
                     }
                     jou_count++;
                 }
      
            // System.out.println("journal_count--->" + journal_count);

            } catch (Exception e) {
             System.out.println(e);
            }
            
            
            
            
            
            //---------------------------------------------TrailBalace Check
            // * TDA_TCA TABLE  CHECK=======
               // * TDA/ TCA originating advice entered, but journal not posted
            
            
                try
                         {
                      
                        String sql="select  a.VOUCHER_NO,a.ORGINATING_JVR_NO from FAS_TDA_TCA_RAISED_MST a where a.accounting_unit_id=? \n "+
                        "and a.cashbook_month=? and a.cashbook_year=? and a.TDA_OR_TCA in('TDAO','TCAO') and  a.ORGINATING_JVR_NO is null and  a.ORGINATING_JVR_DATE is null and STATUS='L'";
                      
                        ps1=con.prepareStatement(sql);
                        ps1.setInt(1,cmbAcc_UnitCode);
                        ps1.setInt(2,txtCB_Month);
                        ps1.setInt(3,txtCB_Year);
                      
                        System.out.println("before execution");
                        ResultSet rs=ps1.executeQuery();
                        while(rs.next())
                        {
                        
                            if(a3==0)
                            {
                            tda_vo=rs.getString("VOUCHER_NO");
                            a3=1;
                            }
                            else {
                                tda_vo=tda_vo+","+rs.getString("VOUCHER_NO");
                            }
                            tda_count++;
                        }
                        
                        
                        
                        System.out.println("after execution");
                        
                        }
                        catch(Exception e)
                        {
                        System.out.println(e);
                        }
            
            
            
            /*
                     * TDA_TCA TABLE  CHECK===========
                     * 
                     * TDA / TCA acceptance advice entered , but journal not posted*/
                     
                      
                      
             try
                      {
                         
                      String sql= "select  a.VOUCHER_NO,a.ACCEPTING_JVR_NO from FAS_TDA_TCA_RAISED_MST a where a.accounting_unit_id=?   and a.cashbook_month=? and a.cashbook_year=? and a.TDA_OR_TCA in('TDAA','TCAA') " +
                      		" AND (a.ACCEPTING_JVR_NO   IS NULL or a.ACCEPTING_JVR_NO=0) and a.ACCEPTING_JVR_DATE is null and STATUS='L' ";
                    
                      ps1=con.prepareStatement(sql);
                          ps1.setInt(1,cmbAcc_UnitCode);
                          ps1.setInt(2,txtCB_Month);
                          ps1.setInt(3,txtCB_Year);
                      System.out.println("before execution");
                      ResultSet rs=ps1.executeQuery();
                      while(rs.next())
                      {
                          if(a4==0)
                          {
                          tca_vo=rs.getString("VOUCHER_NO");
                          a4=1;
                          }
                          else {
                              tca_vo=tca_vo+","+rs.getString("VOUCHER_NO");
                          }
                          tca_count++;
                          
                      }
               
                      System.out.println("after execution");
                      
                      }
                      catch(Exception e)
                      {
                      System.out.println(e);
                      }
            
            /*TDA head entered in payment but post TDA not done*/
                      
           
            try {
            
            
             String sql=" select  a.VOUCHER_NO from FAS_PAYMENT_TRANSACTION a where a.accounting_unit_id=?  and a.cashbook_month=? and a.cashbook_year=? and VOUCHER_NO in( select VOUCHER_NO from  FAS_PAYMENT_MASTER where accounting_unit_id=? and cashbook_month=? and cashbook_year=? and PAYMENT_STATUS='L') and a.account_head_code=? and a.PAYABLE_VOUCHER_NO=0 and PAYABLE_VOUCHER_DATE is null " ;
                    
                    
                    System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"+sql);
                     ps1=con.prepareStatement(sql);
                     ps1.setInt(1,cmbAcc_UnitCode);
                     ps1.setInt(2,txtCB_Month);
                     ps1.setInt(3,txtCB_Year);
             ps1.setInt(4,cmbAcc_UnitCode);
             ps1.setInt(5,txtCB_Month);
             ps1.setInt(6,txtCB_Year);
             ps1.setInt(7,900108);
                     System.out.println("before execution");
                     ResultSet rs=ps1.executeQuery();
                     //System.out.println("after execution");
                     while (rs.next()) {   
                         if(a5==0)
                         {
                         Payment_vo=rs.getString("VOUCHER_NO");
                         a5=1;
                         }
                         else {
                             Payment_vo=Payment_vo+","+rs.getString("VOUCHER_NO");
                         }
                         System.out.println("Payment Voucher No::::::::::::"+Payment_vo);
                         payment_count++;                    
                     }            
                     
            System.out.println("after execution");  
            }
            catch(Exception e) {
             System.out.println(e);
            }
            
            /*TCA Head entered in receipt but post TCA not done
            
            Receipt Table Check =================
              *  TCA Head entered in receipt but post TCA not done*****************/
              
              
            try
             {
               
             
             String sql= "select  a.RECEIPT_NO from fas_receipt_transaction a where a.accounting_unit_id=?  and a.cashbook_month=? and a.cashbook_year=? and a.account_head_code=? and a.RECEIPT_NO IN(SELECT RECEIPT_NO FROM FAS_RECEIPT_MASTER\n" + 
             "WHERE accounting_unit_id     =?            \n" + 
             "AND cashbook_month           =?\n" + 
             "AND cashbook_year            =?\n" + 
             "AND RECEIVABLE_VOUCHER_NO    =0\n" + 
             "AND RECEIVABLE_VOUCHER_DATE IS NULL\n" + 
             "AND RECEIPT_STATUS           ='L')\n ";
                System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"+sql);
                 ps1=con.prepareStatement(sql);
                 ps1.setInt(1,cmbAcc_UnitCode);
                 ps1.setInt(2,txtCB_Month);
                 ps1.setInt(3,txtCB_Year);
                 
                 ps1.setInt(4,901001);
                ps1.setInt(5,cmbAcc_UnitCode);
                ps1.setInt(6,txtCB_Month);
                ps1.setInt(7,txtCB_Year);
             System.out.println("before execution");
             ResultSet rs=ps1.executeQuery();
             System.out.println("after execution");
             while (rs.next()) {                      
              
              
                 if(a6==0)
                 {
                 receipt_vo=rs.getString("RECEIPT_NO");
                 a6=1;
                 }
                 else {
                     receipt_vo=receipt_vo+","+rs.getString("RECEIPT_NO");
                 }
                 receipt_count++;      
                
             } 
             //}
            }
             catch (Exception e)
             {
             System.out.println(e);
             }
            /**
                              * Trial Balance Check New On March 3
                              * TCA Head entered in payroll journal but PostTCA not done*/
                              
                            
                              try
                              {
                                
//                              String sql= "select  a.VOUCHER_NO from FAS_JOURNAL_TRANSACTION a where a.accounting_unit_id=?  and a.cashbook_month=? and a.cashbook_year=? and a.account_head_code=? and a.voucher_no in(SELECT VOUCHER_NO\n" + 
//                              "FROM FAS_JOURNAL_MASTER\n" + 
//                              "WHERE accounting_unit_id=?\n" + 
//                              "AND cashbook_month      =?\n" + 
//                              "AND cashbook_year       =? and CB_REF_TYPE is null and JOURNAL_STATUS      ='L' and JOURNAL_TYPE_CODE=54)";
                            	  
                            	  String sql="Select A.Voucher_No,A.Account_Head_Code,A.Amount "+
  								"	From Fas_Journal_Transaction A,Fas_Journal_Master b "+
                          		  "	Where A.Accounting_Unit_Id=? "+
                          		  "	And A.Cashbook_Month      =? "+
                          		  "	And A.Cashbook_Year       =? "+
                          		  "	And A.Account_Head_Code   =? "+
                          		  "	And A.Accounting_For_Office_Id=B.Accounting_For_Office_Id "+
                          		  "	And A.ACCOUNTING_UNIT_ID=B.ACCOUNTING_UNIT_ID "+
                          		  "	And A.CASHBOOK_YEAR=B.CASHBOOK_YEAR "+
                          		  "	And A.Cashbook_Month=B.Cashbook_Month "+
                          		  "	And A.Voucher_No=B.Voucher_No "+
                          		  "	And B.Accounting_Unit_Id=? "+
                          		  "	And B.Cashbook_Month      =? "+
                          		  "	  And B.Cashbook_Year       =? "+
                          		  "	  And B.Cb_Ref_Type        Is Null "+
                          		  "	  And B.Journal_Status      ='L' "+
                          		  "	  And b.Journal_Type_Code   =54";
                          	  
                            	  
                                  System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"+sql);
                              ps1=con.prepareStatement(sql);
                                  ps1.setInt(1,cmbAcc_UnitCode);
                                  ps1.setInt(2,txtCB_Month);
                                  ps1.setInt(3,txtCB_Year);
                                  ps1.setInt(4,901001);
                                  ps1.setInt(5,cmbAcc_UnitCode);
                                  ps1.setInt(6,txtCB_Month);
                                  ps1.setInt(7,txtCB_Year);
                              System.out.println("before execution");
                              ResultSet rs=ps1.executeQuery();
                              System.out.println("after execution");
                              while (rs.next()) {                    
                             
                                 
                                    
                                      if(a7==0)
                                      {   
                                      payroll_vo=rs.getString("VOUCHER_NO");
                                      a7=1;
                                      }
                                      else {
                                          payroll_vo=payroll_vo+","+rs.getString("VOUCHER_NO");
                                      }
                                  payroll_count++;                    
                                  
                             // }
                              } 
                                 System.out.println("after execution"+payroll_vo);
                            
                              }
                              catch (Exception e)
                              {
                              System.out.println(e);
                              }
                            
                             
            /*   TDA / TCA responding is pending for acceptance*/
            
            
            /**
                       * Trial Balance Check New On March 3
                       * TDA/TCA Responding is pending for acceptance*/
                       
                    
                       try
                       {
                    
                       
                         String sql= "SELECT a.VOUCHER_NO\n" + 
                         " FROM FAS_TDA_TCA_RAISED_MST a,FAS_TDA_TCA_RAISED_MST b\n" + 
                         " WHERE a.TRF_ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID \n" + 
                         " and a.ACCEPTING_SLNO=b.VOUCHER_NO \n" + 
                         " and a.accounting_unit_id =?\n" + 
                         " AND a.cashbook_month       =?\n" + 
                         " AND a.cashbook_year        =?\n" + 
                         " AND a.TDA_OR_TCA          IN('TDAO','TCAO')\n" + 
                         " AND a.ACCEPTANCE_STATUS    ='Y'\n" + 
                         " AND b.ACCEPTING_JVR_NO    IS NOT NULL and B.Accepting_Jvr_No!=0 \n" + 
                         " AND (a.RESPONDING_JVR_NO   IS NULL or a.RESPONDING_JVR_NO=0)\n" + 
                         " AND a.RESPONDING_JVR_DATE IS NULL\n" + 
                         " AND a.STATUS               ='L'";
                           System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"+sql);
                       ps2=con.prepareStatement(sql);
                           ps2.setInt(1,cmbAcc_UnitCode);
                           ps2.setInt(2,txtCB_Month);
                           ps2.setInt(3,txtCB_Year);
                  
                       System.out.println("before execution");
                       ResultSet rs1=ps2.executeQuery();
                       while(rs1.next())
                       {
                              if(a8==0)
                              {   
                              sus_vo=rs1.getString("VOUCHER_NO");
                              a8=1;
                              }
                              else {
                                  sus_vo=sus_vo+","+rs1.getString("VOUCHER_NO");
                              }
                              sus_count++;
                          
                          }
                         
                        
                       }
                       catch (Exception e)
                       {
                       System.out.println(e);
                       }
                       
                     //New check code for balance type CR of HOA sum_current_month_CR must >0
                       //for balance type DR of HOA, sum_current_month_DR musr >0
                       //for balance type null either sum_current_month_CR or sum_current_month_DR >0
                       //for balance type NetCR/NetDR closing balance CR/DR must >0
                       //implemented on 05/05/2018-----
                       
                       //case I CR=0
                       try
                       {
                    	   String sql_HOA="SELECT gl.ACCOUNT_HEAD_CODE, " +
                			   "  h.BALANCE_TYPE, " +
                			   "  gl.CURRENT_MONTH_DEBIT " +
                			   "FROM COM_MST_ACCOUNT_HEADS h, " +
                			   "  fas_general_ledger gl " +
                			   "WHERE gl.ACCOUNTING_UNIT_ID =? " +
                			   "AND gl.YEAR                 =? " +
                			   "AND gl.MONTH                =? " +
                			   "AND gl.account_head_code    =h.account_head_code " +
                			   "AND h.balance_type          ='CR' " +
                			   "AND gl.CURRENT_MONTH_DEBIT !=0 " +
                			   "ORDER BY ACCOUNT_HEAD_CODE"; 
                    	   ps2=con.prepareStatement(sql_HOA);
                           ps2.setInt(1,cmbAcc_UnitCode);
                           ps2.setInt(2,txtCB_Year);
                           ps2.setInt(3,txtCB_Month);
                           //System.out.println("before execution HOA Balance type check");
                           ResultSet rs1=ps2.executeQuery();
                           while(rs1.next())
                           {
                           //System.out.println("whileeeeeeeee");
                                  if(a9==0)
                                  {   
                                	  HOA_list=rs1.getString("ACCOUNT_HEAD_CODE");
                                  a9=1;
                                  }
                                  else {
                                	  HOA_list=HOA_list+","+rs1.getString("ACCOUNT_HEAD_CODE");
                                  }
                                  HOA_count++;
                              System.out.println("Inside New HOA CR Check code %%%"+HOA_count);
                              }
                       }
                       catch(Exception e)
                       {
                    	   System.out.println("Error in HOA balance type and amount**"+e);
                       }
                     //case II DR ==0
                       try
                       {
                    	   String sql_HOA_DR="SELECT gl.ACCOUNT_HEAD_CODE, " +
                			   "  h.BALANCE_TYPE, " +
                			   "  gl.CURRENT_MONTH_CREDIT " +
                			   "FROM COM_MST_ACCOUNT_HEADS h, " +
                			   "  fas_general_ledger gl " +
                			   "WHERE gl.ACCOUNTING_UNIT_ID =? " +
                			   "AND gl.YEAR                 =? " +
                			   "AND gl.MONTH                =? " +
                			   "AND gl.account_head_code    =h.account_head_code " +
                			   "AND h.balance_type          ='DR' " +
                			   "AND gl.CURRENT_MONTH_CREDIT !=0 " +
                			   "ORDER BY ACCOUNT_HEAD_CODE"; 
                    	   ps2=con.prepareStatement(sql_HOA_DR);
                           ps2.setInt(1,cmbAcc_UnitCode);
                           ps2.setInt(2,txtCB_Year);
                           ps2.setInt(3,txtCB_Month);
                           //System.out.println("before execution HOA Balance DR type check");
                           ResultSet rs1=ps2.executeQuery();
                           while(rs1.next())
                           {
                           //System.out.println("whileeeeeeeee");
                                  if(a10==0)
                                  {   
                                	  HOA_list_DR=rs1.getString("ACCOUNT_HEAD_CODE");
                                  a10=1;
                                  }
                                  else {
                                	  HOA_list_DR=HOA_list_DR+","+rs1.getString("ACCOUNT_HEAD_CODE");
                                  }
                                  HOA_count_DR++;
                              System.out.println("Inside New HOA CR Check code %%%"+HOA_count_DR);
                              }
                       }
                       catch(Exception e)
                       {
                    	   System.out.println("Error in HOA balance type and amount**"+e);
                       }
                     //case III NCR ==0
                       try
                       {
                    	   String sql_HOA_NCR= "SELECT gl.ACCOUNT_HEAD_CODE, " +
                               "  h.BALANCE_TYPE, " +
                               "  gl.CURRENT_MONTH_DEBIT, "+
                               "  gl.CURRENT_MONTH_CREDIT "+
                               "FROM COM_MST_ACCOUNT_HEADS h, " +
                               "  fas_general_ledger gl " +
                               "WHERE gl.ACCOUNTING_UNIT_ID       =? " +
                               "AND gl.YEAR                       =? " +
                               "AND gl.MONTH                      =? " +
                               "AND gl.account_head_code          =h.account_head_code " +
                               "AND h.balance_type                ='NCR' " +
                               "AND gl.CURRENT_MONTH_CREDIT      < gl.CURRENT_MONTH_DEBIT " +
                               "ORDER BY ACCOUNT_HEAD_CODE";
                       	   ps2=con.prepareStatement(sql_HOA_NCR);
                           ps2.setInt(1,cmbAcc_UnitCode);
                           ps2.setInt(2,txtCB_Year);
                           ps2.setInt(3,txtCB_Month);
                           //System.out.println("before execution HOA Balance DR type check");
                           ResultSet rs1=ps2.executeQuery();
                           while(rs1.next())
                           {
                           //System.out.println("whileeeeeeeee");
                                  if(a11==0)
                                  {   
                                	  HOA_list_NCR=rs1.getString("ACCOUNT_HEAD_CODE");
                                  a11=1;
                                  }
                                  else {
                                	  HOA_list_NCR=HOA_list_NCR+","+rs1.getString("ACCOUNT_HEAD_CODE");
                                  }
                                  HOA_count_NCR++;
                              System.out.println("Inside New HOA CR Check code %%%"+HOA_count_NCR);
                              }
                       }
                       catch(Exception e)
                       {
                    	   System.out.println("Error in HOA balance type and amount**"+e);
                       }
                      
                     //case iv NDR ==0
                       try
                       {
                    	   String sql_HOA_NDR= "SELECT gl.ACCOUNT_HEAD_CODE, " +
                                   "  h.BALANCE_TYPE, " +
                                   "  gl.CURRENT_MONTH_DEBIT, "+
                                   "  gl.CURRENT_MONTH_CREDIT "+
                                   "FROM COM_MST_ACCOUNT_HEADS h, " +
                                   "  fas_general_ledger gl " +
                                   "WHERE gl.ACCOUNTING_UNIT_ID       =? " +
                                   "AND gl.YEAR                       =? " +
                                   "AND gl.MONTH                      =? " +
                                   "AND gl.account_head_code          =h.account_head_code " +
                                   "AND h.balance_type                ='NDR' " +
                                   "AND gl.CURRENT_MONTH_DEBIT     <  gl.CURRENT_MONTH_CREDIT" +
                                   "  ORDER BY ACCOUNT_HEAD_CODE";
                       	   ps2=con.prepareStatement(sql_HOA_NDR);
          
                       	   ps2.setInt(1,cmbAcc_UnitCode);
                           ps2.setInt(2,txtCB_Year);
                           ps2.setInt(3,txtCB_Month);
                           //System.out.println("before execution HOA Balance DR type check");
                           ResultSet rs1=ps2.executeQuery();
                           while(rs1.next())
                           {
                           //System.out.println("whileeeeeeeee");
                                  if(a12==0)
                                  {   
                                	  HOA_list_NDR=rs1.getString("ACCOUNT_HEAD_CODE");
                                  a12=1;
                                  }
                                  else {
                                	  HOA_list_NDR=HOA_list_NDR+","+rs1.getString("ACCOUNT_HEAD_CODE");
                                  }
                                  HOA_count_NDR++;
                              System.out.println("Inside New HOA CR Check code %%%"+HOA_count_NDR);
                              }
                       }
                       catch(Exception e)
                       {
                    	   System.out.println("Error in HOA balance type and amount**"+e);
                       }
              // End of New Check function for CR/DR/NCR/NDR------
                       
                       //tda_tca_monthEnd
                       if(cmbAcc_UnitCode!=5)
                       {
                       	rsflag=0;
   			                    try{
   			                    	String td="Select Verify_Flag From Fas_Tda_Tca_Monthend WHERE ACCOUNTING_UNIT_ID    =? AND " +
   			                    			" Cashbook_Year           =? " +
   			                    			" AND CASHBOOK_MONTH::numeric=?";
   			                    	PreparedStatement ps22=con.prepareStatement(td);
   			                    	ps22.setInt(1,cmbAcc_UnitCode);
   			                    	ps22.setInt(2,txtCB_Year);
   			                    	ps22.setInt(3,txtCB_Month);
   			                    	ResultSet rs22=ps22.executeQuery();
   			                    	while(rs22.next())
   			                    	{
   			                    		rsflag++;
   			                    	}
   			                    	
   			                    }
   			                    catch(Exception ee)
   			                    {
   			                    	System.out.println("excep::::"+ee);
   			                    }
   			                  //double check for Verification of TDA/TCA Register and Transactions - Every Month  
   			                    
   			                    try{

   			                        String que="SELECT Unit_Id,\n" + 
   			                        "    Cashbook_Month,\n" + 
   			                        "    Account_Head_Code,\n" + 
   			                        "    Trn_Dr,\n" + 
   			                        "    Trn_Cr,\n" + 
   			                        "    Tda_Dr,\n" + 
   			                        "    Tda_Cr,\n" + 
   			                        "    Trn_Net,\n" + 
   			                        "    Tda_Net,\n" + 
   			                        "    (Trn_Net-tda_net)AS difference\n" + 
   			                        "  FROM\n" + 
   			                        "    (SELECT aaa.Accounting_Unit_Id AS unit_id,\n" + 
   			                        "      Aaa.Cashbook_Month,\n" + 
   			                        "      Aaa.Account_Head_Code,\n" + 
   			                        "      Aaa.Trn_Dr,\n" + 
   			                        "      Aaa.Trn_Cr,\n" + 
   			                        "      Bbb.Tda_Dr,\n" + 
   			                        "      Bbb.Tda_Cr,\n" + 
   			                        "      CASE\n" + 
   			                        "        WHEN Aaa.Trn_Dr>Aaa.Trn_Cr\n" + 
   			                        "        THEN (Aaa.Trn_Dr-Aaa.Trn_Cr)\n" + 
   			                        "        WHEN Aaa.Trn_Cr>Aaa.Trn_Dr\n" + 
   			                        "        THEN (Aaa.Trn_Cr-Aaa.Trn_Dr)\n" + 
   			                        "        ELSE 0\n" + 
   			                        "      END AS Trn_Net,\n" + 
   			                        "      CASE\n" + 
   			                        "        WHEN Bbb.Tda_Dr>Bbb.Tda_Cr\n" + 
   			                        "        THEN (Bbb.Tda_Dr-Bbb.Tda_Cr)\n" + 
   			                        "        WHEN Bbb.Tda_Cr>Bbb.Tda_Dr\n" + 
   			                        "        THEN (Bbb.Tda_Cr-Bbb.Tda_Dr)\n" + 
   			                        "        ELSE 0\n" + 
   			                        "      END AS Tda_Net\n" + 
   			                        "    FROM\n" + 
   			                        "      (SELECT Accounting_Unit_Id,\n" + 
   			                        "        Cashbook_Month,\n" + 
   			                        "        Account_Head_Code,\n" + 
   			                        "        SUM(dr_Amount)AS trn_dr,\n" + 
   			                        "        SUM(cr_amount)AS trn_cr\n" + 
   			                        "      FROM\n" + 
   			                        "        (SELECT Accounting_Unit_Id,\n" + 
   			                        "          Cashbook_Month,\n" + 
   			                        "          Account_Head_Code,\n" + 
   			                        "          SUM(dr_Amount)AS dr_Amount,\n" + 
   			                        "          SUM(cr_amount)AS cr_amount\n" + 
   			                        "        FROM\n" + 
   			                        "          (SELECT B.Cashbook_Month,\n" + 
   			                        "            B.Account_Head_Code,\n" + 
   			                        "            B.Accounting_Unit_Id,\n" + 
   			                        "            CASE\n" + 
   			                        "              WHEN B.Cr_Dr_Indicator='DR'\n" + 
   			                        "              THEN SUM(B.Amount)\n" + 
   			                        "              ELSE 0\n" + 
   			                        "            END AS dr_Amount,\n" + 
   			                        "            CASE\n" + 
   			                        "              WHEN b.Cr_Dr_Indicator='CR'\n" + 
   			                        "              THEN SUM(B.Amount)\n" + 
   			                        "              ELSE 0\n" + 
   			                        "            END AS cr_amount\n" + 
   			                        "          FROM fas_journal_master a,\n" + 
   			                        "            Fas_Journal_Transaction b\n" + 
   			                        "          WHERE A.Accounting_Unit_Id    =b.Accounting_Unit_Id\n" + 
   			                        "          AND A.Accounting_For_Office_Id=B.Accounting_For_Office_Id\n" + 
   			                        "          AND A.Cashbook_Year           =B.Cashbook_Year\n" + 
   			                        "          AND A.Cashbook_Month          =B.Cashbook_Month\n" + 
   			                        "          AND A.Voucher_No              =B.Voucher_No\n" + 
   			                        "          And A.Journal_Status          ='L'\n" + 
   			                        "          and A.Accounting_Unit_Id=" +cmbAcc_UnitCode+ 
   			                        "          AND A.Journal_Type_Code      IN (62,63,65,66)\n" + 
   			                        "          AND A.Mode_Of_Creation        ='A'\n" + 
   			                     " AND a.created_by_module       ='GJV' "+
   		                       "     AND (a.SUPPLEMENT_NO         IS NULL "+
   		                       "     OR a.SUPPLEMENT_NO            =0) "+
   			                        "          And B.Cashbook_Year           = " +txtCB_Year+ 
   			                        "          and B.Cashbook_Month=" +txtCB_Month+ 
   			                        "          AND B.Account_Head_Code      IN (900108,900109,901001,901002)\n" + 
   			                        "          GROUP BY B.Accounting_Unit_Id,\n" + 
   			                        "            B.Cashbook_Month,\n" + 
   			                        "            B.Account_Head_Code,\n" + 
   			                        "            B.Cr_Dr_Indicator\n" + 
   			                        "          UNION ALL\n" + 
   			                        "          SELECT B.Cashbook_Month,\n" + 
   			                        "            B.Account_Head_Code,\n" + 
   			                        "            B.Accounting_Unit_Id,\n" + 
   			                        "            CASE\n" + 
   			                        "              WHEN B.Cr_Dr_Indicator='DR'\n" + 
   			                        "              THEN SUM(B.Amount)\n" + 
   			                        "              ELSE 0\n" + 
   			                        "            END AS dr_Amount,\n" + 
   			                        "            CASE\n" + 
   			                        "              WHEN b.Cr_Dr_Indicator='CR'\n" + 
   			                        "              THEN SUM(B.Amount)\n" + 
   			                        "              ELSE 0\n" + 
   			                        "            END AS cr_amount\n" + 
   			                        "          FROM fas_journal_master a,\n" + 
   			                        "            Fas_Journal_Transaction b\n" + 
   			                        "          WHERE A.Accounting_Unit_Id    =b.Accounting_Unit_Id\n" + 
   			                        "          AND A.Accounting_For_Office_Id=B.Accounting_For_Office_Id\n" + 
   			                        "          AND A.Cashbook_Year           =B.Cashbook_Year\n" + 
   			                        "          AND A.Cashbook_Month          =B.Cashbook_Month\n" + 
   			                        "          AND A.Voucher_No              =B.Voucher_No\n" + 
   			                        "          And A.Journal_Status          ='L'\n" + 
   			                        "          and A.Accounting_Unit_Id=" +cmbAcc_UnitCode+ 
   			                        "          AND A.Journal_Type_Code      IN (54)\n" + 
   			                     " AND (A.SUPPLEMENT_NO         IS NULL "+
   		                       "         OR A.SUPPLEMENT_NO            =0) "+
   			                        "          And B.Cashbook_Year           = " +txtCB_Year+
   			                        "          and B.Cashbook_Month=" +txtCB_Month+
   			                        "          AND B.Account_Head_Code      IN (900108,900109,901001,901002)\n" + 
   			                        "          GROUP BY B.Accounting_Unit_Id,\n" + 
   			                        "            B.Cashbook_Month,\n" + 
   			                        "            B.Account_Head_Code,\n" + 
   			                        "            B.Cr_Dr_Indicator\n" + 
   			                        "          )as m\n" + 
   			                        "        GROUP BY Accounting_Unit_Id,\n" + 
   			                        "          Cashbook_Month,\n" + 
   			                        "          Account_Head_Code\n" + 
   			                        "        UNION ALL\n" + 
   			                        "        SELECT B.Accounting_Unit_Id,\n" + 
   			                        "          B.Cashbook_Month,\n" + 
   			                        "          B.Account_Head_Code,\n" + 
   			                        "          CASE\n" + 
   			                        "            WHEN B.Cr_Dr_Indicator='DR'\n" + 
   			                        "            THEN SUM(B.Amount)\n" + 
   			                        "            ELSE 0\n" + 
   			                        "          END AS dr_Amount,\n" + 
   			                        "          CASE\n" + 
   			                        "            WHEN b.Cr_Dr_Indicator='CR'\n" + 
   			                        "            THEN SUM(B.Amount)\n" + 
   			                        "            ELSE 0\n" + 
   			                        "          END AS cr_amount\n" + 
   			                        "        FROM FAS_PAYMENT_MASTER a,\n" + 
   			                        "          FAS_PAYMENT_TRANSACTION b\n" + 
   			                        "        WHERE A.Accounting_Unit_Id    =b.Accounting_Unit_Id\n" + 
   			                        "        AND A.Accounting_For_Office_Id=B.Accounting_For_Office_Id\n" + 
   			                        "        AND A.Cashbook_Year           =B.Cashbook_Year\n" + 
   			                        "        AND A.Cashbook_Month          =B.Cashbook_Month\n" + 
   			                        "        And A.Voucher_No              =B.Voucher_No\n" + 
   			                        "        and A.Accounting_Unit_Id=" +cmbAcc_UnitCode+ 
   			                        "        AND A.Payment_Status          ='L'\n" + 
   			                        "        And B.Cashbook_Year           = " +txtCB_Year+
   			                        "        and B.Cashbook_Month=" +txtCB_Month+
   			                        "        AND B.Account_Head_Code      IN (900108,900109,901001,901002)\n" + 
   			                        "        GROUP BY B.Accounting_Unit_Id,\n" + 
   			                        "          B.Cashbook_Month,\n" + 
   			                        "          B.Account_Head_Code,\n" + 
   			                        "          B.Cr_Dr_Indicator\n" + 
   			                        "        UNION ALL\n" + 
   			                        "        SELECT B.Accounting_Unit_Id,\n" + 
   			                        "          B.Cashbook_Month,\n" + 
   			                        "          B.Account_Head_Code,\n" + 
   			                        "          CASE\n" + 
   			                        "            WHEN B.Cr_Dr_Indicator='DR'\n" + 
   			                        "            THEN SUM(B.Amount)\n" + 
   			                        "            ELSE 0\n" + 
   			                        "          END AS dr_Amount,\n" + 
   			                        "          CASE\n" + 
   			                        "            WHEN b.Cr_Dr_Indicator='CR'\n" + 
   			                        "            THEN SUM(B.Amount)\n" + 
   			                        "            ELSE 0\n" + 
   			                        "          END AS cr_amount\n" + 
   			                        "        FROM FAS_RECEIPT_MASTER a,\n" + 
   			                        "          FAS_RECEIPT_TRANSACTION b\n" + 
   			                        "        WHERE A.Accounting_Unit_Id    =b.Accounting_Unit_Id\n" + 
   			                        "        AND A.Accounting_For_Office_Id=B.Accounting_For_Office_Id\n" + 
   			                        "        AND A.Cashbook_Year           =B.Cashbook_Year\n" + 
   			                        "        AND A.Cashbook_Month          =B.Cashbook_Month\n" + 
   			                        "        AND A.RECEIPT_NO              =B.RECEIPT_NO\n" + 
   			                        "        And A.Receipt_Status          ='L'\n" + 
   			                        "        and A.Accounting_Unit_Id=" +cmbAcc_UnitCode+ 
   			                        "        And B.Cashbook_Year           = " +txtCB_Year+
   			                        "        and B.Cashbook_Month=" +txtCB_Month+ 
   			                        "        AND B.Account_Head_Code      IN (900108,900109,901001,901002)\n" + 
   			                        "        GROUP BY B.Accounting_Unit_Id,\n" + 
   			                        "          B.Cashbook_Month,\n" + 
   			                        "          B.Account_Head_Code,\n" + 
   			                        "          B.Cr_Dr_Indicator\n" + 
   			                        "        )as m\n" + 
   			                        "      GROUP BY Accounting_Unit_Id,\n" + 
   			                        "        Cashbook_Month,\n" + 
   			                        "        Account_Head_Code\n" + 
   			                        "      ORDER BY Accounting_Unit_Id,\n" + 
   			                        "        Cashbook_Month,\n" + 
   			                        "        Account_Head_Code\n" + 
   			                        "      )aaa\n" + 
   			                       // "    LEFT OUTER JOIN\n" +
   			                     "    FULL OUTER JOIN\n" + 
   			                        "      (SELECT Accounting_Unit_Id,\n" + 
   			                        "        Cashbook_Month,\n" + 
   			                        "        Account_Head_Code,\n" + 
   			                        "        SUM(dr_Amount)AS tda_dr,\n" + 
   			                        "        SUM(cr_amount)AS tda_cr\n" + 
   			                        "      FROM\n" + 
   			                        "        (SELECT B.Accounting_Unit_Id,\n" + 
   			                        "          B.Cashbook_Month,\n" + 
   			                        "          B.Account_Head_Code,\n" + 
   			                        "          CASE\n" + 
   			                        "            WHEN B.Cr_Dr_Indicator='DR'\n" + 
   			                        "            THEN SUM(B.Amount)\n" + 
   			                        "            ELSE 0\n" + 
   			                        "          END AS dr_Amount,\n" + 
   			                        "          CASE\n" + 
   			                        "            WHEN b.Cr_Dr_Indicator='CR'\n" + 
   			                        "            THEN SUM(B.Amount)\n" + 
   			                        "            ELSE 0\n" + 
   			                        "          END AS cr_amount\n" + 
   			                        "        FROM Fas_Tda_Tca_Raised_Mst A,\n" + 
   			                        "          Fas_Tda_Tca_Raised_Trn B\n" + 
   			                        "        WHERE A.Accounting_Unit_Id    =b.Accounting_Unit_Id\n" + 
   			                        "        AND A.Accounting_For_Office_Id=B.Accounting_For_Office_Id\n" + 
   			                        "        AND A.Cashbook_Year           =B.Cashbook_Year\n" + 
   			                        "        AND A.Cashbook_Month          =B.Cashbook_Month\n" + 
   			                        "        AND A.Voucher_No              =B.Voucher_No\n" + 
   			                        "        And A.Status                  ='L'\n" + 
   			                        "        and A.Accounting_Unit_Id=" +cmbAcc_UnitCode+ 
   			                        "        And B.Cashbook_Year           = " +txtCB_Year+
   			                        "        and B.Cashbook_Month=" +txtCB_Month+
   			                     "   AND (a.SUPPLEMENT_NO         IS NULL "+
   		                       " 	      OR a.SUPPLEMENT_NO            =0) "+
   			                        "        AND B.Account_Head_Code      IN (900108,900109,901001,901002)\n" + 
   			                        "        GROUP BY B.Accounting_Unit_Id,\n" + 
   			                        "          B.Cashbook_Month,\n" + 
   			                        "          B.Account_Head_Code,\n" + 
   			                        "          B.Cr_Dr_Indicator\n" + 
   			                        "        ORDER BY B.Accounting_Unit_Id,\n" + 
   			                        "          B.Cashbook_Month,\n" + 
   			                        "          b.Account_Head_Code\n" + 
   			                        "        )as m\n" + 
   			                        "      GROUP BY Accounting_Unit_Id,\n" + 
   			                        "        Cashbook_Month,\n" + 
   			                        "        Account_Head_Code\n" + 
   			                        "      ORDER BY Accounting_Unit_Id,\n" + 
   			                        "        Cashbook_Month,\n" + 
   			                        "        Account_Head_Code\n" + 
   			                        "      )Bbb\n" + 
   			                        "    ON aaa.Accounting_Unit_Id=bbb.Accounting_Unit_Id\n" + 
   			                        "    AND Aaa.Cashbook_Month   =Bbb.Cashbook_Month\n" + 
   			                        "    AND Aaa.Account_Head_Code=Bbb.Account_Head_Code\n" + 
   			                        "    )as m where (Trn_Net-tda_net)!=0";
   			                       
   			                //   	 System.out.println("que:::"+que);
   			                                    ps = con.prepareStatement(que);
   			                                  ResultSet  result_tda = ps.executeQuery();
   			                                    while(result_tda.next())
   			                                    {
   			                                    diff_error++;	
   			                                    }
   			                    }
   			                    catch(Exception et)
   			                    {
   			                    	System.out.println("excep in tda_monthend::::"+et);
   			                    }
   			                    
   			                    
       				         }
                       
                       
                       
                       // for Cheque Memo and Payments are equal 
                       
                       try{

                    	   String que="SELECT Yy.*, " +
                          		 "  xx.voucher_no, " +
                          		 "  xx.payment_date, " +
                          		 "  Xx.Pay_Amt, " +
                          		 "  xx.pay_amt-yy.che_amt AS diff " +
                          		 "FROM " +
                          		 "  (SELECT SUM(t.amount) AS pay_amt, " +
                          		 "    T.Cheque_Dd_No, " +
                          		"  TO_CHAR(T.cheque_dd_date,'dd/mm/yy') AS cheque_dd_date, " +
                          		 "    m.Accounting_Unit_Id, " +
                          		 "    M.Cashbook_Year, " +
                          		 "    m.cashbook_month, " +
                          		 "    m.voucher_no, " +
                          		 "    to_char(m.payment_date,'dd/mm/yy') as payment_date  " +
                          		 "  FROM Fas_Payment_Master M " +
                          		 "  INNER JOIN Fas_Payment_Transaction T " +
                          		 "  ON m.accounting_for_office_id        =t.accounting_for_office_id " +
                          		 "  AND M.Accounting_Unit_Id             =T.Accounting_Unit_Id " +
                          		 "  AND M.Cashbook_Month                 =T.Cashbook_Month " +
                          		 "  AND M.Cashbook_Year                  =T.Cashbook_Year " +
                          		 "  AND m.voucher_no                     =T.voucher_no " +
                          		 "  AND (M.Voucher_No , M.Payment_Date) IN " +
                          		 "    (SELECT DISTINCT T.Pvr_No , " +
                          		 "      T.Pvr_Date " +
                          		 //"      --, " +
                          		 //"      -- t.payment_unit, " +
                          		 //"      --   t.payment_office " +
                          		 "    FROM Fas_Memo_Of_Payment_Mst M " +
                          		 "    INNER JOIN Fas_Memo_Of_Payment_Trn T " +
                          		 "    ON m.accounting_for_office_id =t.accounting_for_office_id " +
                          		 "    AND M.Accounting_Unit_Id      =T.Accounting_Unit_Id " +
                          		 "    AND M.Cashbook_Month          =T.Cashbook_Month " +
                          		 "    AND M.Cashbook_Year           =T.Cashbook_Year " +
                          		 "    AND M.Bill_No                 =T.Bill_No " +
                          		 "    AND t.payment_unit            =? " +
                          	//	 "    AND t.payment_office      =? " +
                          		 "    AND M.Cashbook_Year           =? " +
                          		 "    AND M.Cashbook_Month          =? " +
                          		 "    AND M.Status                  ='L' " +
                          		 "    ) " +
                          		 "  AND M.Payment_Status     ='L' " +
                          		 "  AND M.Accounting_Unit_Id =? " +
                          		 "  GROUP BY T.Cheque_Dd_No, " +
                          		 "    t.cheque_dd_date, " +
                          		 "    m.Accounting_Unit_Id, " +
                          		 "    M.Cashbook_Year, " +
                          		 "    m.cashbook_month, " +
                          		 "    m.voucher_no, " +
                          		 "    m.payment_date " +
                          		 "  ) Xx " +
                          		 " INNER JOIN " +
                          		 "  (SELECT SUM(cheque_amount) AS che_amt, " +
                          		 "    cheque_no, " +
                          		 "    to_char(cheque_date,'dd/mm/yy') as cheque_date , " +
                          		 "    Accounting_Unit_Id, " +
                          		 "    Cashbook_Year, " +
                          		 "    Cashbook_Month, " +
                          		 "    CHEQUE_MEMO_NO, " +
                          		 "    to_char(CHEQUE_MEMO_DATE,'dd/mm/yy') as CHEQUE_MEMO_DATE " +
                          		 "  FROM Fas_Cheque_Memo_Mst " +
                          		 "  WHERE Accounting_Unit_Id=? " +
                          	//	 "  and  ACCOUNTING_FOR_OFFICE_ID=? " +
                          		 "  AND Cashbook_Year       =? " +
                          		 "  AND Cashbook_Month      =? " +
                          		 "  AND Status              ='L' " +
                          		 "  GROUP BY cheque_no, " +
                          		 "    cheque_date, " +
                          		 "    Accounting_Unit_Id, " +
                          		 "    Cashbook_Year, " +
                          		 "    Cashbook_Month, " +
                          		 "    CHEQUE_MEMO_NO, " +
                          		 "    CHEQUE_MEMO_DATE " +
                          		 "  )Yy " +
                          		 " ON xx.Accounting_Unit_Id=yy.Accounting_Unit_Id " +
                          		 " AND Xx.Cheque_Dd_No     =Yy.Cheque_No " +
                          		 " AND Xx.Cheque_Dd_Date   =Yy.Cheque_Date " +
                          		 " and  xx.pay_amt-yy.che_amt <> 0" + 
                          		 " ORDER BY xx.Cheque_Dd_No";
		                       
		                //   	 System.out.println("que:::"+que);
		                                 PreparedStatement   ps_memo = con.prepareStatement(que);
		                              
		                                 ps_memo.setInt(1,cmbAcc_UnitCode);
		                              
		                                 ps_memo.setInt(2,txtCB_Year);
		                                 ps_memo.setInt(3,txtCB_Month);
		                                 ps_memo.setInt(4,cmbAcc_UnitCode);
		                                 ps_memo.setInt(5,cmbAcc_UnitCode);
		                               
		                                 ps_memo.setInt(6,txtCB_Year);
		                                 ps_memo.setInt(7,txtCB_Month);
		                                  ResultSet  result_memo = ps_memo.executeQuery();
		                                    while(result_memo.next())
		                                    {
		                                    diff_errorCheque++;	
		                                    }
		                    }
		                    catch(Exception et)
		                    {
		                    	System.out.println("excep in tda_monthend::::"+et);
		                    }
		                  
                       
                   	int rsflag_memo=1;

            		if((txtCB_Year==2015 && txtCB_Month> 10) || txtCB_Year > 2015){
if(cmbAcc_UnitCode != 3 || cmbAcc_UnitCode != 5 || cmbAcc_UnitCode != 6){
                		
	rsflag_memo=0;
	                    try{
	                    	String td="Select Verify_Flag From FAS_CHEQUE_MEMO_MONTHEND WHERE ACCOUNTING_UNIT_ID    =? AND " +
	                    			" Cashbook_Year           =? " +
	                    			" AND CASHBOOK_MONTH::numeric=?";
	                    	PreparedStatement ps22=con.prepareStatement(td);
	                    	ps22.setInt(1,cmbAcc_UnitCode);
	                    	ps22.setInt(2,txtCB_Year);
	                    	ps22.setInt(3,txtCB_Month);
	                    	ResultSet rs22=ps22.executeQuery();
	                    	while(rs22.next())
	                    	{
	                    		rsflag_memo++;
	                    	}
	                    	
	                    }
	                    catch(Exception ee)
	                    {
	                    	System.out.println("excep::::"+ee);
	                    }
                       
}   
				         
            		}    
            		
            		int rsflag_memo1=1;
            		if((txtCB_Year==2015 && txtCB_Month> 10) || txtCB_Year > 2015){
            			if(cmbAcc_UnitCode != 3 || cmbAcc_UnitCode != 5 || cmbAcc_UnitCode != 6){
            			                		
            				rsflag_memo1=0;
            				                    try{
            				                    	String td="Select VERIFY_FLAG From FAS_SCH_EXP_VERIFY_MONTHEND WHERE ACCOUNTING_UNIT_ID    =? AND " +
            				                    			" Cashbook_Year           =? " +
            				                    			" AND CASHBOOK_MONTH::numeric=?";
            				                    	PreparedStatement ps22=con.prepareStatement(td);
            				                    	ps22.setInt(1,cmbAcc_UnitCode);
            				                    	ps22.setInt(2,txtCB_Year);
            				                    	ps22.setInt(3,txtCB_Month);
            				                    	ResultSet rs22=ps22.executeQuery();
            				                    	while(rs22.next())
            				                    	{
            				                    		rsflag_memo1++;
            				                    	}
            				                    	
            				                    }
            				                    catch(Exception ee)
            				                    {
            				                    	System.out.println("excep::::"+ee);
            				                    }
            			                       
            			}   
            							         
            			            		}    
                       
                       
                       // for Regular march2012 and Supplement2012 and april2012
                       int verify_march=0,march_vCount=0;
                       int verify_supp=0,supp_vCount=0;
                       if(txtCB_Month==3){
                      
                       try{
 		                 String ss="SELECT COUNT(*)as tc,Difference FROM FAS_TDA_TCA_REG_MAR2012 WHERE " +
 		                 		"ACCOUNTING_UNIT_ID=? AND Cashbook_Year       =? And " +
 		                 		"Cashbook_Month      =3 GROUP BY Difference";
 		                 System.out.println("ss:"+ss);
 		                 ps1=con.prepareStatement(ss);
 		                 ps1.setInt(1,cmbAcc_UnitCode);
 		                 ps1.setInt(2,txtCB_Year);
 		           System.out.println("y nnnnnn");
 			              ResultSet rs=ps1.executeQuery();
 			              while(rs.next())
 			              {
 			                  String diff=rs.getString("Difference");
 			                  if(diff.equalsIgnoreCase("NotTally"))
 			                  {
 			                	 verify_march++;
 			                  }
 			                  march_vCount++;
 			                  System.out.println("march_vCount:::::::"+march_vCount);
 			                  
 			              }
   
        
                       }
                       catch(Exception e) {
                           System.out.println("exc in ida_tca_verify:::"+e);
                          }
                       
                       //supplement
                      
                      
                       try{
 		                 String ss11="SELECT COUNT(*)as cc,DIFFERENCE FROM FAS_TDA_TCA_SUP_2012 WHERE " +
 		                 		"ACCOUNTING_UNIT_ID=? AND Cashbook_Year       =? And " +
 		                 		"Cashbook_Month      =3 GROUP BY Difference";
 		                 ps1=con.prepareStatement(ss11);
 		                 ps1.setInt(1,cmbAcc_UnitCode);
 		                 ps1.setInt(2,txtCB_Year);
 		               //  ps1.setInt(3,txtCB_Month);
 			              ResultSet rs=ps1.executeQuery();
 			              while(rs.next())
 			              {
 			            	 String diff=rs.getString("Difference");
			                  if(diff.equalsIgnoreCase("NotTally"))
			                  {
			                	  verify_supp++;
			                  }
 			            	 // verify_supp=rs.getInt("cc");
 			                  supp_vCount++;
 			                  
 			              }
// 			              if(supp_vCount==0)
// 			              {
// 			            	  verify_supp=0;
// 			              }
 			              
        
                       }
                       catch(Exception e) {
                           System.out.println("exc in ida_tca_verify:::"+e);
                          }
        				}
                       //regular april
                       int verify_apr=0,apr_vCount=0;
                       if(txtCB_Month==4){
                       try{
 		                 String ss1="SELECT COUNT(*)as rr,DIFFERENCE FROM FAS_TDA_TCA_REG_APR2012 WHERE " +
 		                 		"ACCOUNTING_UNIT_ID=? AND Cashbook_Year       =? And " +
 		                 		"Cashbook_Month      =4 GROUP BY Difference";
 		                 ps1=con.prepareStatement(ss1);
 		                 ps1.setInt(1,cmbAcc_UnitCode);
 		                 ps1.setInt(2,txtCB_Year);
 		            //     ps1.setInt(3,txtCB_Month);
 			              ResultSet rs=ps1.executeQuery();
 			              while(rs.next())
 			              {
 			            	 String diff=rs.getString("Difference");
			                  if(diff.equalsIgnoreCase("NotTally"))
			                  {
			                	  verify_apr++;
			                  }
 			            	 // verify_apr=rs.getInt("rr");
 			            	  apr_vCount++;
 			                  
 			              }
// 			              if(apr_vCount==0)
// 			              {
// 			            	  verify_apr=0;
// 			              }
 			              
        
                       }
                       catch(Exception e) {
                           System.out.println("exc in ida_tca_verify:::"+e);
                          }
                       }
             //end    
                    
            try
            {
            	

          	  if(rsflag==0)
                {
                	con.rollback(); 
                    sendMessage(response, " Please Verify TDA/TCA Report and Then Generate TB","ok");  
                    return;
                }if(rsflag_memo==0)
            	{
                	con.rollback(); 
            		sendMessage(response, " Please Verify GPF Payment and Cheque memo and Then Generate TB ","ok"); 
                   return;
                	
                   
           	}
              if(rsflag_memo1==0)  
              {
            	  con.rollback(); 
            	  sendMessage(response, " Please Verify Scheme Expenditure and Then Generate TB ","ok"); 
                  return;  
              }
                
                
                if(diff_error>0)
                {
                	con.rollback(); 
                    sendMessage(response, " Please Once Again Verify TDA/TCA Report and Then Generate TB","ok");  
                    return;
                } // Joan Added on 2014 November 07 for check Head of current credit 550351 &550350 i equal
                if(count_haed>0){
                	con.rollback(); 
                    sendMessage(response, " CR Amount of Account Head code 550350 and 550351 are not equal","ok");  
                    return;	
                }// Joan Added on 2015 Jun 11 for check Head of current credit 391402 &391403 is equal 
                if(count_haed1>0){
                	con.rollback(); 
                    sendMessage(response, " Account Head code 391402 and 391403 are not equal","ok");  
                    return;	
                }
            	//may2012
            	if(txtCB_Year==2012 & txtCB_Month==3){
            	
            	if(march_vCount==0) 
                {
                   
                	con.rollback(); 
                    sendMessage(response, " Please Verify Regular March TDA_TCA Verify Menu under MonthEndOperations(Regular) and Then Freeze","ok");  
                    return;    
                }
                if(supp_vCount==0) 
                {
                   
                	con.rollback(); 
                    sendMessage(response, " Please Verify Supplement March TDA_TCA Verify Menu under MonthEndOperations(Regular) and Then Freeze","ok");  
                    return;    
                }
                if(apr_vCount==0) 
                {
                   
                	con.rollback(); 
                    sendMessage(response, " Please Verify Regular April TDA_TCA Menu under MonthEndOperations(Regular) and Then Freeze","ok");  
                    return;    
                }
                //not tallied
                if(verify_march>0) 
                {
                   
                	con.rollback(); 
                    sendMessage(response, " Regular March TDA_TCA is Not Tallied in Menu under MonthEndOperations(Regular)","ok");  
                    return;    
                }
                if(verify_supp>0) 
                {
                   
                	con.rollback(); 
                    sendMessage(response, " Supplement March TDA_TCA is Not Tallied in Menu under MonthEndOperations(Regular)","ok");  
                    return;    
                }
                if(verify_apr>0) 
                {
                   
                	con.rollback(); 
                    sendMessage(response, "Regular April TDA_TCA is Not Tallied in Menu under MonthEndOperations(Regular)","ok");  
                    return;    
                }
            	}
            	/*if(diff_errorCheque>0)
            	{
            		sendMessage(response, " Please Once Again Verify Cheque Memo and Payment ","ok");  
                    
                    return;
                    
            	}if(rsflag_memo==0)
            	{
            		sendMessage(response, " Please Verify Cheque Memo and Payment ","ok"); 
                   return;
                   
           	}*/
            	
            	
                 if(pay_counted>0||ftOff_count>0) 
                {
                    System.out.println("ftOff_count>>>>>>>>>>>>>>>"+ftOff_count);
                    con.rollback();
                    String rk="SUB_LEDGER_TYPE_CODE or SUB_LEDGER_CODE To Be Filled  In Payment..."+pay_vo +"\nSUB_LEDGER_TYPE_CODE or SUB_LEDGER_CODE To Be Filled In Receipt..."+rec_vo+"\nSUB_LEDGER_TYPE_CODE or SUB_LEDGER_CODE  To Be Filled  In journal"+jou_vo;
                 //   System.out.println("testttttttttttt"+rk);
                   
                    sendMessage(response,
                             " <br><br>Payment Verification is Pending."+"<br><br>Fund Transfer Verification is Pending...",
                             "ok");
                    return;
                    
                }
                if(ftHO_count>0) {
                    System.out.println("ftHO_count>>>>>>>>>>>>>>>"+ftHO_count);
                    con.rollback();
                    String rk="SUB_LEDGER_TYPE_CODE or SUB_LEDGER_CODE To Be Filled  In Payment..."+pay_vo +"\nSUB_LEDGER_TYPE_CODE or SUB_LEDGER_CODE To Be Filled In Receipt..."+rec_vo+"\nSUB_LEDGER_TYPE_CODE or SUB_LEDGER_CODE  To Be Filled  In journal"+jou_vo;
                   // System.out.println("testttttttttttt"+rk);
                    
                    sendMessage(response,
                             " <br><br>Fund Transfer Verification is Pending for HO...",
                             "ok");
                    return;
                }
            if(jou_count>0 ||pay_count>0 || rec_count>0) {
            
            
            if(tda_count>0||tca_count>0||receipt_count>0||payment_count>0||payroll_count>0)
            {
             
             
             con.rollback();
                String rk="SUB_LEDGER_TYPE_CODE or SUB_LEDGER_CODE To Be Filled  In Payment..."+pay_vo +"\nSUB_LEDGER_TYPE_CODE or SUB_LEDGER_CODE To Be Filled In " +
                "Receipt..."+rec_vo+"\nSUB_LEDGER_TYPE_CODE or SUB_LEDGER_CODE  To Be Filled  In journal"+jou_vo;
                System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+rk);
               // sendMessage(response,rk,"ok");
             sendMessage(response,
                         " SUB_LEDGER_TYPE_CODE or SUB_LEDGER_CODE  To Be Filled  In Payment..."+pay_vo +" <br><br> SUB_LEDGER_TYPE_CODE or SUB_LEDGER_CODE  To Be Filled  In " +
                         "Receipt..."+rec_vo+ 
                         "<br><br>SUB_LEDGER_TYPE_CODE or SUB_LEDGER_CODE  To Be Filled  In journal..."+jou_vo+"<br><br>TDA,TCA Originating " +
                         "Journal Posting is Pending Vr.Nos..."+tda_vo +"<br><br>TDA,TCA Accepting Journal Posting is Pending Vr.Nos.."+tca_vo+ 
                             "<br><br>Posting of TDA head in Final Payment is Pending Vr.Nos...."+Payment_vo+"<br><br>Posting of TCA head in " +
                             "Receipt is Pending Vr.Nos..."+receipt_vo+"<br><br>Posting of TCA from Payroll Journal is Pending Vr.Nos..."+payroll_vo,
                         "ok");
             return;
         
             
             
            }
            else {
                con.rollback();
                String rk="<br><br>SUB_LEDGER_TYPE_CODE or SUB_LEDGER_CODE To Be Filled  In Payment..."+pay_vo +"<br><br>SUB_LEDGER_TYPE_CODE or " +
                "SUB_LEDGER_CODE To Be " +
                "Filled In Receipt..."+rec_vo+"<br><br>SUB_LEDGER_TYPE_CODE or SUB_LEDGER_CODE  To Be Filled  In journal..."+jou_vo;
                System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+rk);
                sendMessage(response,rk,"ok");
                return;
            }
            
            }
            else if(tda_count>0||tca_count>0||receipt_count>0||payment_count>0||payroll_count>0)
            {
                con.rollback(); 
                sendMessage(response,
                            " TDA,TCA Originating Journal Posting is Pending Vr.Nos..."+tda_vo +"<br><br>TDA,TCA " +
                            "Accepting Journal Posting is Pending Vr.Nos..."+tca_vo+ 
                            "<br><br>Posting of TDA head in Final Payment is Pending Vr.Nos..."+Payment_vo+"<br><br>Posting of TCA head in Receipt is " +
                            "Pending Vr.Nos..."+receipt_vo+"<br><br>Posting of TCA from Payroll Journal is " +
                            "Pending Vr.Nos..."+payroll_vo,
                            "ok");  
                return;
                
            }
            
           
         
            //temporary block by dhanapradha
            
          /*  else if(generate_pbStatus>0)
            {
               
            	con.rollback(); 
                sendMessage(response, " PB Updation is not Done Upto the Previous month for GeneralLedger","ok");  
                return;    
            }
            else if(generate_pbStatus_sl>0) 
            {
               
            	con.rollback(); 
                sendMessage(response, " PB Updation is not Done Upto the Previous month for Sub Ledger","ok");  
                return;    
            }  */
          //added on 05/05/2018 ****
            else if(HOA_count>0)
            {
            	System.out.println("HOA_count>>>>>>>>>>>"+HOA_count);
            	con.rollback();
            	String rk="List of CR HOA in Current month Debit >0 "+HOA_list;
            	//System.out.println("Testt in HOA***"+rk);
            	sendMessage(response," <br><br>Current month Debit cant be >0..."+HOA_list+" ",
            			"ok");
            	return;
            }
            
            //DR current month
            else if(HOA_count_DR>0)
            {
            	System.out.println("HOA_count>>>>>>>>>>>"+HOA_count_DR);
            	con.rollback();
            	String rk="List of DR HOA in Current month Credit >0 "+HOA_list_DR;
            	//System.out.println("Testt in HOA DR***"+rk);
            	sendMessage(response," <br><br>Current month Credit cant be >0..."+HOA_list_DR+" ",
            			"ok");
            	return;
            }
          //NCR Net Closing balance Credit
            else if(HOA_count_NCR>0)
            {
            	System.out.println("HOA_count>>>>>>>>>>>"+HOA_count_NCR);
            	con.rollback();
            	String rk="List of NET CR HOA in Current month credit < Current month debit "+HOA_list_NCR;
            	//System.out.println("Testt in HOA***"+rk);
            	sendMessage(response," <br><br>Net CR HOA CM CR cant be < CM DR..."+HOA_list_NCR+" ",
            			"ok");
            	return;
            }
            
            //NDR Net Closing balance Debit
            else if(HOA_count_NDR>0)
            {
            	System.out.println("HOA_count>>>>>>>>>>>"+HOA_count_NDR);
            	con.rollback();
            	String rk="List of NET DR HOA in CM DR < CM CR "+HOA_list_NDR;
            	//System.out.println("Testt in HOA DR***"+rk);
            	sendMessage(response," <br><br>Net DR HOA CM DR cant be < CM CR..."+HOA_list_NDR+" ",
            			"ok");
            	return;
            }
            
            }
            catch(Exception e) {
             System.out.println("Final Output"+e);
            }          
 
            //-----------------------------------------------------------------------------------------------------------------End 3-5-2011

            try {
                // con.setAutoCommit(false);

                int err_code = -1;
                System.out.println("err:::check:" + err_code);
                System.out.println(cmbAcc_UnitCode+"::CashBookYear:"+txtCB_Year+"::CashBookMonth::"+txtCB_Month);
                
                cs = con.prepareCall("call FAS_TRIAL_BALANCE_PROC_2012(?::numeric,?::numeric,?::numeric,?,?::numeric)");
                
             //   cs =  con.prepareCall("{call  FAS_TRIAL_BALANCE_PROC(?,?,?,?,?)}");
                cs.setInt(1, cmbAcc_UnitCode);
                cs.setInt(2, txtCB_Year);
                cs.setInt(3, txtCB_Month);
                cs.setString(4, userid);
                cs.registerOutParameter(5, java.sql.Types.NUMERIC);
                cs.setInt(5, 0);
                cs.execute();
                //err_code = cs.getInt(5);
                err_code = cs.getBigDecimal(5).intValue();
                System.out.println("2 nd err" + err_code);
                if (err_code == 0 && count == 0 && journal_count == 0) {
                    System.out.println("success");
 //                   con.commit();
                    //System.out.println( con.commit());
                } else if (err_code == 16) {
                    con.rollback();
                    System.out.println("errorcode16&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
                    sendMessage(response,
                                "Vouchers are pending for Remittance in cash,bank and fund system...",
                                "ok");
                    return;
                } else if (err_code == 15) {
                    con.rollback();
                    sendMessage(response,
                                "Vouchers are pending for Remittance in bank and fund system ...",
                                "ok");
                    return;
                } else if (err_code == 14) {
                    con.rollback();
                    sendMessage(response,
                                "Vouchers are pending for Remittance in cash and fund system...",
                                "ok");
                    return;
                } else if (err_code == 13) {
                    con.rollback();
                    sendMessage(response,
                                "Vouchers are pending for Remittance in cash and bank system...",
                                "ok");
                    return;
                } else if (err_code == 12) {
                    con.rollback();
                    System.out.println("errorcode12*&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
                    sendMessage(response,
                                "Vouchers are pending for Remittance in cash system ...",
                                "ok");
                    return;
                } else if (err_code == 11) {
                    con.rollback();
                    sendMessage(response,
                                "Vouchers are pending for Remittance in bank system...",
                                "ok");
                    return;
                } else if (err_code == 10) {
                    con.rollback();
                    sendMessage(response,
                                "Vouchers are pending for Remittance in fund system...",
                                "ok");
                    return;
                } else if (err_code == 9) {
                    con.rollback();
                    sendMessage(response,
                                "You should make General Ledger Posting...",
                                "ok");
                    return;
                } 
                else if (err_code == 111) {
                    con.rollback();
                    sendMessage(response,
                                "Procedure Error...",
                                "ok");
                    return;
                } 
                else if (count > 0) {
                    con.rollback();
                    sendMessage(response,
                                " Voucher(s) are pending for Verification in Remittance...",
                                "ok");
                    return;
                } else if (journal_count > 0) {
                    con.rollback();
                    sendMessage(response,
                                " Voucher(s) are pending for Verification in Journal...",
                                "ok");
                    return;
                }

                else {
                    sendMessage(response, "Trial Balance generation failed",
                                "ok");
                    con.rollback();
                    return;
                }
                flag3 = true;
            } catch (Exception e) {
                System.out.println("Exception in Main:" + e);
                try {
                    con.rollback();
                    System.out.println("error in try");
                } catch (SQLException e1) {
                    System.out.println("catch:" + e1);
                }
                String msg = "Trial Balance Has failed to Update";
                msg = msg + "<br><br>";
                sendMessage(response, msg, "ok");
                return;

            }
        } else {
            try {
                con.rollback();
            } catch (SQLException e1) {
                System.out.println("catch:" + e1);
            }
            String msg = "GeneralLedger Posting Details Not Updated";
            msg = msg + "<br><br>";
            sendMessage(response, msg, "ok");
            return;
        }
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /**
         *  SL, GL and TB Generation Sequence Check
         */

        /* try {

              CallableStatement cs1=null;

              int result_code = -1;
              int error_code = -1;
              cs1=con.prepareCall("{call FAS_SL_GL_TB_GENERATION_CHECK(?,?,?,?,?,?,?) }");
              cs1.setInt(1,cmbAcc_UnitCode);
              cs1.setInt(2,0);
              cs1.setInt(3,txtCB_Month);
              cs1.setInt(4,txtCB_Year);
              cs1.setString(5,"TB_FREEZE");
              cs1.registerOutParameter(6,java.sql.Types.NUMERIC);
              cs1.registerOutParameter(7,java.sql.Types.NUMERIC);

              cs1.execute();

              result_code=cs1.getInt(6);
              error_code=cs1.getInt(7);

              if ( error_code == 0 ) {

                  if ( result_code == 5) {
                        sendMessage(response,"SL,GL and TB not generated for the Month "+txtCB_Month+" / " + txtCB_Year,"ok");
                        return;
                  }
                  else if ( result_code == 10) {
                       sendMessage(response,"SL and GL for the Month "+txtCB_Month+" / " + txtCB_Year,"ok");
                       return;
                  }
                  else if ( result_code == 20) {
                      sendMessage(response,"SL not Generated for the Month "+txtCB_Month+" / " + txtCB_Year,"ok");
                      return;
                  }
                  else if ( result_code == 30) {
                      sendMessage(response,"GL not Generated for the Month "+txtCB_Month+" / " + txtCB_Year,"ok");
                      return;
                  }
                  else if ( result_code == 40) {
                      sendMessage(response,"TB not Generated for the Month "+txtCB_Month+" / " + txtCB_Year,"ok");
                      return;
                  }
                  else if ( result_code == 50) {
                      sendMessage(response,"Generate TB once again","ok");
                      return;
                  }
                  else if ( result_code == -300) {
                      sendMessage(response,"Receipts have been modified after SL, GL and TB Generation. Please do posting once again","ok");
                      return;
                  }
                  else if ( result_code == -301) {
                      sendMessage(response,"Payments have been modified after SL, GL and TB Generation. Please do posting once again","ok");
                      return;
                  }
                  else if ( result_code == -302) {
                      sendMessage(response,"Journals have been modified after SL, GL and TB Generation. Please do posting once again","ok");
                      return;
                  }
                  else if ( result_code == -303) {
                      sendMessage(response,"Fund Receipts have been modified after SL, GL and TB Generation. Please do posting once again","ok");
                      return;
                  }
                  else if ( result_code == -304) {
                      sendMessage(response,"Fund Transfers have been modified after SL, GL and TB Generation. Please do posting once again","ok");
                      return;
                  }

              }

         }
         catch(Exception e ) {
             System.out.println(e);
         }
         */


        //--------------------------------------------------------------------------------------//
        System.out.println(flag3);
        if (flag3) {


            try {
                con.setAutoCommit(false);
                PreparedStatement ps = null;
                PreparedStatement ps2 = null;

                String msg = " ";
                String account_head = "   ";
                int account_head_code_exits = 0;


                /**
                         * Find out Wrong Account Heads
                         */

                ps2 = con.prepareStatement("select account_head_code from fas_trial_balance where cashbook_month = ? and cashbook_year=? and accounting_unit_id= ? and account_head_code not in ( select account_head_code from com_mst_account_heads )");
                ps2.setInt(1, txtCB_Month);
                ps2.setInt(2, txtCB_Year);
                ps2.setInt(3, cmbAcc_UnitCode);

                ResultSet res2 = ps2.executeQuery();

                int rowCount = res2.getFetchSize();
                
               // if(rowCount > 0) {
                	if(cmbAcc_UnitCode != 398 ) {
                		
                		if (res2.next()) // if the row doesn't return by the query
                        {
                            System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%1");
                            account_head =
                                    account_head + res2.getString("account_head_code");
                            account_head_code_exits++;

                        }
                	}else {
                		if(rowCount > 0) {
                			System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%1");
                            account_head =
                                    account_head + res2.getString("account_head_code");
                            account_head_code_exits++;
                		}
                		
                	}
                
                	 
            //    }

               
                
                
                System.out.println("account_head_code_exits===>"+account_head_code_exits);
                
                if (account_head_code_exits > 0) {
                    sendMessage(response,
                                "Wrong Account Head(s)" + account_head, "ok");
                    System.out.println("Wrong Account Head(s)" + account_head);
                    return;
                }


                /**Display Warning Message if TB in not generated */


                ps =
  con.prepareStatement("select count(*) as cnt from fas_trial_balance where accounting_unit_id=? and cashbook_year=? and cashbook_month=? ");
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, txtCB_Year);
                ps.setInt(3, txtCB_Month);
                ResultSet res1 = ps.executeQuery();
                if (res1.next()) // if the row doesn't return by the query
                {
                    System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%2");
                    int count = res1.getInt("cnt");
                    if (count == 0) {
                        sendMessage(response, "Trial Balance not Generated",
                                    "ok");
                        System.out.println("Trial Balance not Generated");
                        return;
                    }
                }
                /** Added 17/7/2018 */
  
                /** For Yes Condition */

                if (radTB_status.equalsIgnoreCase("Y")) {
                    System.out.println("Inside TB_status 'Y'");
                    System.out.println("..0");
                    ps =
  con.prepareStatement("select 'X' from FAS_TRIAL_BALANCE " +
                       "WHERE ACCOUNTING_UNIT_ID=? AND CASHBOOK_YEAR=? AND CASHBOOK_MONTH=?" +
                       "HAVING SUM(CURRENT_MONTH_DEBIT)!=SUM(CURRENT_MONTH_CREDIT) AND SUM(CURRENT_MONTH_DEBIT) =0 " +
                       "AND SUM(CURRENT_MONTH_CREDIT)=0");
                       
                      
                    ps.setInt(1, cmbAcc_UnitCode);
                    ps.setInt(2, txtCB_Year);
                    ps.setInt(3, txtCB_Month);
                    ResultSet res = ps.executeQuery();
                    if (res.next()) // if the row doesn't return by the query
                    {
                        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%3++++++++++");
                        sendMessage(response, "Trial Balance doesn't Tally",
                                    "ok");
                        System.out.println("Trial Balance doesn't Tally");
                        return;
                    }
                    res.close();
                    ps.close();


                    ps =
  con.prepareStatement("select TB_STATUS from FAS_TRIAL_BALANCE_STATUS where  ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=?");
                    ps.setInt(1, cmbAcc_UnitCode);
                    //ps.setInt(2,cmbOffice_code);
                    ps.setInt(2, txtCB_Year);
                    ps.setInt(3, txtCB_Month);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) // if already in TB_STATUS="N' then
                    {
                        // Mostly it never occur, but here just checking it whether already exist
                        System.out.println("..3");
                        PreparedStatement ps1 =
                            con.prepareStatement("update FAS_TRIAL_BALANCE_STATUS set TB_STATUS='Y',TB_DATE=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=? where  ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=?");
                        ps1.setTimestamp(1, ts);
                        ps1.setString(2, userid);
                        ps1.setTimestamp(3, ts);
                        ps1.setInt(4, cmbAcc_UnitCode);
                        ps1.setInt(5, txtCB_Year);
                        ps1.setInt(6, txtCB_Month);
                        ps1.executeUpdate();
                        ps1.close();
                    } else // if NOT exist in table
                    {
                        System.out.println("..4");
                        PreparedStatement ps1 =
                            con.prepareStatement("insert into FAS_TRIAL_BALANCE_STATUS(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,TB_STATUS,TB_DATE,UPDATED_BY_USER_ID,UPDATED_DATE ) values(?,?,?,?,?,?,?,?)");
                        ps1.setInt(1, cmbAcc_UnitCode);
                        ps1.setInt(2, 0);
                        ps1.setInt(3, txtCB_Year);
                        ps1.setInt(4, txtCB_Month);
                        ps1.setString(5, "Y");
                        ps1.setTimestamp(6, ts);
                        ps1.setString(7, userid);
                        ps1.setTimestamp(8, ts);
                        ps1.executeUpdate();
                        ps1.close();
                    }
                    msg =
 "Trial Balance Status has been Frozen Successfully.......";
                    ps.close();

                }
                /** For Not Condition */
                else if (radTB_status.equalsIgnoreCase("N")) // If trial balance status enabled to "N" from "Y" means
                {
                    System.out.println("Inside TB_status 'N'");

                    System.out.println("..5");
                    // Here i deleted the TB generated info from FAS_TRIAL_BALANCE_STATUS , if exist
                    ps =
  con.prepareStatement("delete from FAS_TRIAL_BALANCE_STATUS where  ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=?");
                    ps.setInt(1, cmbAcc_UnitCode);
                    ps.setInt(2, txtCB_Year);
                    ps.setInt(3, txtCB_Month);
                    ps.executeUpdate();
                    ps.close();
                    System.out.println("..6");

                    msg =
 "Trial Balance Froze Status is Removed... You have to do General Ledger Posting Again for this Accounting Unit ";
                }
                System.out.println("..7");

                
                
                
//                con.commit();
                con.setAutoCommit(true);
                System.out.println("..8");


                msg = msg + "<br><br>";
                sendMessage(response, msg, "ok");
                return;
            } catch (Exception e) {
                try {
                    con.rollback();
                } catch (SQLException e1) {
                    System.out.println("exception in rollback" + e1);
                }
                System.out.println("Exception in Trial balance " + e);
                String msg =
                    "Trial Balance has been Freezed successfully....";
                msg = msg + "<br><br>";
                sendMessage(response, msg, "ok");
                return;

            }
        } else {
            try {
                con.rollback();
            } catch (SQLException e1) {
                System.out.println("catch:" + e1);
            }
            String msg = "Trial Balance Has failed to Update";
            msg = msg + "<br><br>";
            sendMessage(response, msg, "ok");
            return;
        }
        }
    }

    private void sendMessage(HttpServletResponse response, String msg,
                             String bType) {
        try {
            String url =
                "org/Library/jsps/Messenger.jsp?message=" + msg + "&button=" +
                bType;
            response.sendRedirect(url);
            return;
        } catch (IOException e) {
        }
    }

}
