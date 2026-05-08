package Servlets.FAS.FAS1.AccountHeadDirectory.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.*;

public class GeneralLedgerServlet extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {

        /**
        * Variables Declarations
        * 
        */
        Connection connection = null;
        Statement statement = null;
        ResultSet results = null;
        ResultSet results2 = null,rss6=null;
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;
        PreparedStatement ps4 = null,pss6=null;

        PreparedStatement ps6 = null;
        PreparedStatement ps7 = null,pss=null,pss1=null;
        ResultSet results3 = null;
        ResultSet results4 = null,rss=null,rss1=null;
        CallableStatement cs1 = null;
       
        /**
         * Database Connection
         */
        try {
            ResourceBundle rs =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";

            String strDriver = rs.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rs.getString("Config.DSN");
            String strhostname = rs.getString("Config.HOST_NAME");
            String strportno = rs.getString("Config.PORT_NUMBER");
            String strsid = rs.getString("Config.SID");
            String strdbusername = rs.getString("Config.USER_NAME");
            String strdbpassword = rs.getString("Config.PASSWORD");

//            ConnectionString =
//                    strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
//                    ":" + strsid.trim();
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
            
            Class.forName(strDriver.trim());
            connection =
                    DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                                                strdbpassword.trim());
            try {
                statement = connection.createStatement();
                connection.clearWarnings();
            } catch (SQLException e) {
                System.out.println("Exception in creating statement:" + e);
            }
        } catch (Exception e) {
            System.out.println("Exception in openeing connection:" + e);
        }

        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);


        /**
         * Session Checking
         */
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
         * Get Employee ID
         */
        String userid = (String)session.getAttribute("UserId");
        System.out.println("session id is:" + userid);


        /**
         * Get Parameters
         */

        /** Get Accounting Unit ID */
        String Account_unit_Code = request.getParameter("cmbAcc_UnitCode");

        /** Get Accounting Office ID */
        String Office_Code = request.getParameter("cmbOffice_code");

        /** Get Cashbook Year */
        String CashBook_Year = request.getParameter("txtCB_Year");

        /** Get Cashbook Month */
        String CashBook_Month = request.getParameter("txtCB_Month");


        /**
         *  Variables Declarations
         */
        int AccountUnitCode = 0,vb=0;
        int OfficeCode = 0;
        int CashBookYear = 0;
        int CashBookMonth = 0;
        double CRAmount = 0;
        double DRAmount1 = 0;
        double balance = 0;
        String MONTH_CLOSING_BAL_DR_CR_IND = "";
        java.sql.Date MaxCRdate = null;
        java.sql.Date MaxDRdate = null;
        int achcode = 0;
        int acchcode = 0,trial_count=0;


        /**
         * Convert String Data into Intger Value
         */
        try {
            AccountUnitCode = Integer.parseInt(Account_unit_Code);
            OfficeCode = Integer.parseInt(Office_Code);
            CashBookYear = Integer.parseInt(CashBook_Year);
            CashBookMonth = Integer.parseInt(CashBook_Month);
        } catch (Exception e) {
            System.out.println("Exception in Converting Integer:" + e);
        }


        //-------------------------------------------------------------------------------------------------------
        /**
         * SL, GL and TB Generation Sequence Check
         */

       
        //-------------------------------------------------------------------------------------------------------
        
        
        
        int checkcountTB=0;
        try{
        
       
       String checkTB="  select COUNT(*) as cout from FAS_TRIAL_BALANCE_STATUS    WHERE  "+                    
         " ACCOUNTING_UNIT_ID= "+AccountUnitCode;
         
         PreparedStatement ps0=connection.prepareStatement(checkTB);
         ResultSet rs0=ps0.executeQuery();
         if(rs0.next()){
          checkcountTB=rs0.getInt("cout");
         
         }
         
        }catch (Exception e) {
  System.out.println("Exception in Checking TB"+e);
  }
         
       
       if(checkcountTB==0){
      trial_count++; 
       }
       else{
        /**
               *  Check Wheather TB has been Frozen already or not
               */
        try {

            ps =
  connection.prepareStatement("  select 'X'                  \n" +
                              "  from                        \n" +
                              "    FAS_TRIAL_BALANCE_STATUS  \n" +
                              "  WHERE                       \n" +
                              "     ACCOUNTING_UNIT_ID=?     \n" +
                              "  AND CASHBOOK_YEAR=?      \n" +
                              "  AND CASHBOOK_MONTH=?");
            ps.setInt(1, AccountUnitCode);
            ps.setInt(2, CashBookYear);
            ps.setInt(3, CashBookMonth);
            ResultSet res = ps.executeQuery();
            if (res.next()) // if the row doesn't return by the query
            {
                sendMessage(response, "Trial Balance Already Froze", "ok");
                return;
            }
            res.close();
            ps.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        if(AccountUnitCode!=5){
        //this is for checking previous month trialbalance status closed or not
        	int open_year=0,open_month=0;
    	if(CashBookMonth>1){
//		        try {
//		        	
//		        		int prevmonth_trial=CashBookMonth-1;
//		            ps =
//		  connection.prepareStatement("  select 'X'                  \n" +
//		                              "  from                        \n" +
//		                              "    FAS_TRIAL_BALANCE_STATUS  \n" +
//		                              "  WHERE                       \n" +
//		                              "     ACCOUNTING_UNIT_ID=?     \n" +
//		                              "  AND CASHBOOK_YEAR=?      \n" +
//		                              "  AND CASHBOOK_MONTH=?");
//		            ps.setInt(1, AccountUnitCode);
//		            ps.setInt(2, CashBookYear);
//		            ps.setInt(3, prevmonth_trial);
//		            ResultSet res = ps.executeQuery();
//		            if (res.next()) // if the row doesn't return by the query
//		            {
//		               trial_count++;
//		            }
//		            res.close();
//		            ps.close();
//		        } catch (Exception e) {
//		            System.out.println(e);
//		        }
    		
    		
    		

        	try {
				PreparedStatement ps_chk=connection.prepareStatement("select to_char(DATE_OF_OPENING,'yyyy') as year, to_char(DATE_OF_OPENING,'mm')as month from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID="+AccountUnitCode);
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
		 if(CashBookMonth==open_month && CashBookYear==open_year){
			 trial_count=1;
		 }else{
        	try {
		  	
		  		int prevmonth_trial=CashBookMonth-1;
		      ps =connection.prepareStatement("  select 'X'                  \n" +
		                        "  from                        \n" +
		                        "    FAS_TRIAL_BALANCE_STATUS  \n" +
		                        "  WHERE                       \n" +
		                        "     ACCOUNTING_UNIT_ID=?     \n" +
		                        "  AND CASHBOOK_YEAR=?      \n" +
		                        "  AND CASHBOOK_MONTH=?");
		      ps.setInt(1, AccountUnitCode);
		      ps.setInt(2, CashBookYear);
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
        
    		
    		
    		
    	}
    	else
    	{
    		
    		 try {
      		  	
  		  		int prevyear_trial=CashBookYear-1;
  		      ps =connection.prepareStatement("  select 'X'                  \n" +
  		                        "  from                        \n" +
  		                        "    FAS_TRIAL_BALANCE_STATUS  \n" +
  		                        "  WHERE                       \n" +
  		                        "     ACCOUNTING_UNIT_ID=?     \n" +
  		                        "  AND CASHBOOK_YEAR=?      \n" +
  		                        "  AND CASHBOOK_MONTH=12");
  		      ps.setInt(1, AccountUnitCode);
  		      ps.setInt(2, prevyear_trial);
  		    //  ps.setInt(3, prevmonth_trial);
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
    }
    	else
    	  {
    		  trial_count++;
    	  }
        
        
        
    }
        if(trial_count==0)
        {
        	 sendMessage(response, "Trial Balance Status is Not Freezed For Previous Month", "ok");
             return;
        }
        else{
        
        //-----------------------------------------------------------------------------------------------------

        /**
         * This Two Variables for calculateting cashbookmonth and year
         */
        int cashbookmonth1 = 0;
        cashbookmonth1 = CashBookMonth - 1;
        System.out.println("b4 cashbookmont1:" + cashbookmonth1);
        int cashbookyear1 = 0;

        if (cashbookmonth1 == 0) {
            cashbookmonth1 = 12;
            cashbookyear1 = CashBookYear - 1;
        } else {
            cashbookmonth1 = CashBookMonth - 1;
            cashbookyear1 = CashBookYear;
        }


        System.out.println("cashbookmont1:" + cashbookmonth1);
        System.out.println("cashbookyear1:" + cashbookyear1);


        /**
          * Variables Declarations
          */
        int achead = 0;
        String finyear = "";
        double cur_yr_crbal = 0;
        double cur_yr_drbal = 0;
        double Month_OB_Bal = 0;
        String Month_OB_Cr_Dr_ind = "";
        double Upto_Debit_Bal = 0;
        double Upto_Credit_Bal = 0;
        double Month_OB_Bal_new = 0;
        String Month_OB_Cr_Dr_ind_new = "";


        try {

            connection.setAutoCommit(false);

            /**
              * deleteing current month entries first..
              */
            String sql_del =
                "delete from fas_general_ledger where accounting_unit_id=? and accounting_for_office_id=?  and year=? and month=? ";
            ps = connection.prepareStatement(sql_del);
            ps.setInt(1, AccountUnitCode);
            ps.setInt(2, OfficeCode);
            ps.setInt(3, CashBookYear);
            ps.setInt(4, CashBookMonth);
            ps.executeUpdate();


            /**
             * Selecting particular fields from main General Ledger Table for the Preivous Month And Year (For Example-Oct Month - Nov Month)
             */

            String sqlquery1 =
                "select ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR,YEAR,MONTH,ACCOUNT_HEAD_CODE, " +
                " CURRENT_YEAR_DEBIT_BALANCE,CURRENT_YEAR_CREDIT_BALANCE,CURRENT_MONTH_DEBIT,CURRENT_MONTH_CREDIT, " +
                " MONTH_CLOSING_BALANCE,MONTH_CLOSING_BAL_DR_CR_IND,DR_UPDATE_LAST_DATE,CR_UPDATE_LAST_DATE " +
                " from fas_general_ledger where accounting_unit_id=? and accounting_for_office_id=? " +
                " and year=? and month=? order by account_head_code ";
            ps = connection.prepareStatement(sqlquery1);
            ps.setInt(1, AccountUnitCode);
            ps.setInt(2, OfficeCode);
            ps.setInt(3, cashbookyear1);
            ps.setInt(4, cashbookmonth1);
            results = ps.executeQuery();

            while (results.next()) //Start Main While
            {
            	System.out.println("comes here::::::::");
                achead = results.getInt("ACCOUNT_HEAD_CODE");
                //System.out.println("AccHead is:"+achead);
                // System.out.println("count is:"+count++);
                finyear = results.getString("FINANCIAL_YEAR");


                if (CashBookMonth == 4) // New financial yr starting
                {
                    String ps_Asset_liablity =
                        "select ACCOUNT_HEAD_CODE from COM_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_CODE=? and MAJOR_HEAD_CODE in('L','A')";
                    PreparedStatement ps_AL =
                        connection.prepareStatement(ps_Asset_liablity);
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
                            results.getDouble("CURRENT_MONTH_CREDIT") + results.getDouble("CURRENT_YEAR_CREDIT_BALANCE");
                    cur_yr_drbal =
                            results.getDouble("CURRENT_MONTH_DEBIT") + results.getDouble("CURRENT_YEAR_DEBIT_BALANCE");

                }


                Month_OB_Bal = results.getDouble("MONTH_CLOSING_BALANCE");
                Month_OB_Cr_Dr_ind =
                        results.getString("MONTH_CLOSING_BAL_DR_CR_IND");
                Date Dr_update_last = results.getDate("DR_UPDATE_LAST_DATE");
                Date Cr_update_last = results.getDate("CR_UPDATE_LAST_DATE");

                String NFinYear = "";
                if ((CashBookMonth >= 1 && CashBookMonth <= 3)) {
                    NFinYear = (CashBookYear - 1) + "-" + (CashBookYear);
                } else {
                    NFinYear = (CashBookYear) + "-" + (CashBookYear + 1);
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
                ps2 = connection.prepareStatement(sqlquery3);
                ps2.setInt(1, AccountUnitCode);
                ps2.setInt(2, OfficeCode);
                ps2.setString(3, NFinYear);
                ps2.setInt(4, CashBookYear);
                ps2.setInt(5, CashBookMonth);
                System.out.println("achead::"+achead);
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
                long l = System.currentTimeMillis();
                Timestamp ts = new Timestamp(l);
                ps2.setTimestamp(20, ts);
                ps2.executeUpdate();
                ps2.close();
                //}


            } //End Main While()

            results.close();
            ps.close();


            /**
             * Insert Account Heads Which are not available in the previous month
             */

            String sqlqueryselect = "";
            if (AccountUnitCode == 5) // for the case of banking section
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
            ps1 = connection.prepareStatement(sqlqueryselect);
            ps1.setInt(1, AccountUnitCode);
            ps1.setInt(2, OfficeCode);
            ps1.setInt(3, CashBookYear);
            ps1.setInt(4, CashBookMonth);
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
                ps2 = connection.prepareStatement(sqlquery2);
                ps2.setInt(1, achcode);
                ps2.setInt(2, CashBookYear);
                ps2.setInt(3, CashBookMonth);
                ps2.setInt(4, AccountUnitCode);
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
                    String NFinYear = "";
                    if ((CashBookMonth >= 1 && CashBookMonth <= 3)) {
                        NFinYear = (CashBookYear - 1) + "-" + (CashBookYear);
                    } else {
                        NFinYear = (CashBookYear) + "-" + (CashBookYear + 1);
                    }
                    System.out.println("Inner else 3 accheacode");
                    String sqlquery3 =
                        "insert into fas_general_ledger(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR," +
                        "YEAR,MONTH,ACCOUNT_HEAD_CODE,UPTO_DEBIT_BALANCE,UPTO_CREDIT_BALANCE,CURRENT_YEAR_DEBIT_BALANCE," +
                        "CURRENT_YEAR_CREDIT_BALANCE,MONTH_OPENING_BALANCE,MONTH_OPENING_BAL_DR_CR_IND,CURRENT_MONTH_DEBIT," +
                        "CURRENT_MONTH_CREDIT,MONTH_CLOSING_BALANCE,MONTH_CLOSING_BAL_DR_CR_IND,DR_UPDATE_LAST_DATE," +
                        "CR_UPDATE_LAST_DATE,UPDATED_BY_USER_ID,UPDATED_DATE) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    ps4 = connection.prepareStatement(sqlquery3);
                    ps4.setInt(1, AccountUnitCode);
                    ps4.setInt(2, OfficeCode);
                    ps4.setString(3, NFinYear);
                    ps4.setInt(4, CashBookYear);
                    ps4.setInt(5, CashBookMonth);
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
                    long l = System.currentTimeMillis();
                    Timestamp ts = new Timestamp(l);
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
            ps1 = connection.prepareStatement(sqlquery5);
            ps1.setInt(1, AccountUnitCode);
            ps1.setInt(2, OfficeCode);
            ps1.setInt(3, CashBookYear);
            ps1.setInt(4, CashBookMonth);
            results2 = ps1.executeQuery();
            System.out.println("b4");
            while (results2.next()) //Start second Main While
            {
                System.out.println("details.." + AccountUnitCode + "  " +
                                   OfficeCode + "  " + CashBookYear + "  " +
                                   CashBookMonth);
                // System.out.println("second main while...");
                acchcode = results2.getInt("ACCOUNT_HEAD_CODE");
                System.out.println("Inner Second While AHCode:" + acchcode);
                System.out.println("indicator" +
                                   results2.getString("MONTH_OPENING_BAL_DR_CR_IND"));
                Month_OB_Bal_new = results2.getDouble("MONTH_OPENING_BALANCE");
                Month_OB_Cr_Dr_ind_new =
                        results2.getString("MONTH_OPENING_BAL_DR_CR_IND");
                // System.out.println("Month_OB_Cr_Dr_ind_new  now added"+results2.getString("MONTH_OPENING_BAL_DR_CR_IND"));
                //Change on Date 18-Dec-2006 by B//
                String sqlquery6 = "";


                if (AccountUnitCode == 5) // banking section
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
                ps7 = connection.prepareStatement(sqlquery6);
                ps7.setInt(1, AccountUnitCode);
                ps7.setInt(2, OfficeCode);
                ps7.setInt(3, CashBookYear);
                ps7.setInt(4, CashBookMonth);
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
                if (AccountUnitCode == 5) // banking section
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
                ps6 = connection.prepareStatement(sql1);
                ps6.setInt(1, AccountUnitCode);
                ps6.setInt(2, OfficeCode);
                ps6.setInt(3, CashBookYear);
                ps6.setInt(4, CashBookMonth);
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
                ps3 = connection.prepareStatement(sqlquery7);
                ps3.setDouble(1, DRAmount1);
                ps3.setDouble(2, CRAmount);
                ps3.setDouble(3, Upto_Debit_Bal);
                ps3.setDouble(4, Upto_Credit_Bal);
                ps3.setDouble(5, Math.abs(balance));
                ps3.setString(6, MONTH_CLOSING_BAL_DR_CR_IND);
                ps3.setDate(8, MaxDRdate);
                ps3.setDate(7, MaxCRdate);
                ps3.setInt(9, AccountUnitCode);
                ps3.setInt(10, OfficeCode);
                ps3.setInt(11, CashBookYear);
                ps3.setInt(12, CashBookMonth);
                ps3.setInt(13, acchcode);
                System.out.println("acchcode::::"+acchcode);
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
            connection.commit();

            // changes on 28may2012
            if(CashBookMonth==4)
            {
            	String NFinYear = "";
                if ((CashBookMonth >= 1 && CashBookMonth <= 3)) {
                    NFinYear = (CashBookYear - 1) + "-" + (CashBookYear);
                } else {
                    NFinYear = (CashBookYear) + "-" + (CashBookYear + 1);
                }
            	
         	   int prevmonth=CashBookMonth-1;
         	   //here if the account head is not in 3rd month,then if it is in fas_general_ledger_cb 
         	   //then directly add into General_ledger table
         	 System.out.println("CashBookMonth::::for non heads:"+CashBookMonth);
         	 String testSql="SELECT ACCOUNT_HEAD_CODE,MONTH_CLOSING_BALANCE,MONTH_CLOSING_BAL_DR_CR_IND "+
 					" FROM FAS_GENERAL_LEDGER_CB "+
         		 " 					Where Accounting_Unit_Id    ="+AccountUnitCode+
         		 " And Accounting_For_Office_Id= "+OfficeCode+
         		 " And Year= "+CashBookYear+
         		 " and MONTH="+prevmonth;
         	 System.out.println("testSql:::"+testSql);
         	 pss = connection.prepareStatement(testSql);
         	 rss=pss.executeQuery();
         	 while(rss.next())
         	 {
         		 int count_normal=0;
         		 int accHeadCode=rss.getInt("ACCOUNT_HEAD_CODE");
         		 System.out.println("accHeadCode:::"+accHeadCode);
         		 String test1="SELECT ACCOUNT_HEAD_CODE "+
 					" FROM FAS_GENERAL_LEDGER "+
      		 " Where Accounting_Unit_Id    ="+AccountUnitCode+
      		 " And Accounting_For_Office_Id= "+OfficeCode+
      		 " And Year= "+CashBookYear+
      		 " and MONTH="+prevmonth+" and ACCOUNT_HEAD_CODE="+accHeadCode;
		      	 System.out.println("test1:::"+test1);
		      	pss1 = connection.prepareStatement(test1);
		    	 rss1=pss1.executeQuery();
	         	while(rss1.next())
	         	{
	         		count_normal++;
	         	}
	         	if(count_normal==0)
	         	{
	         		
	         		//check whether ct_month acchead is already inserted
	         		//added on 20jun2012
	         		 String hhh="SELECT ACCOUNT_HEAD_CODE "+
	      					" FROM FAS_GENERAL_LEDGER "+
	           		 " Where Accounting_Unit_Id    ="+AccountUnitCode+
	           		 " And Accounting_For_Office_Id= "+OfficeCode+
	           		 " And Year= "+CashBookYear+
	           		 " and MONTH="+CashBookMonth+" and ACCOUNT_HEAD_CODE="+accHeadCode;
	         		 System.out.println("hhh:"+hhh);
	         		pss6 = connection.prepareStatement(hhh);
	         		rss6=pss6.executeQuery();
	         		while(rss6.next())
	         		{
	         			vb++;
	         		}
	         		if(vb==0){
	         		//insert 
			         		String non_heads =
			                    "insert into fas_general_ledger(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID " +
			                    " ,FINANCIAL_YEAR,YEAR,MONTH,ACCOUNT_HEAD_CODE,UPTO_DEBIT_BALANCE,UPTO_CREDIT_BALANCE, " +
			                    "CURRENT_YEAR_DEBIT_BALANCE,CURRENT_YEAR_CREDIT_BALANCE,MONTH_OPENING_BALANCE, " +
			                    "MONTH_OPENING_BAL_DR_CR_IND,CURRENT_MONTH_DEBIT,CURRENT_MONTH_CREDIT,MONTH_CLOSING_BALANCE, " +
			                    "MONTH_CLOSING_BAL_DR_CR_IND,UPDATED_BY_USER_ID, " +
			                    "UPDATED_DATE) " +
			                    "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			                ps2 = connection.prepareStatement(non_heads);
			                ps2.setInt(1, AccountUnitCode);
			                ps2.setInt(2, OfficeCode);
			                ps2.setString(3, NFinYear);
			                ps2.setInt(4, CashBookYear);
			                ps2.setInt(5, CashBookMonth);
			                ps2.setInt(6, accHeadCode);
			                ps2.setInt(7, 0);
			                ps2.setInt(8, 0);
			                ps2.setDouble(9, 0);
			                ps2.setDouble(10, 0);
			                ps2.setDouble(11, rss.getDouble("MONTH_CLOSING_BALANCE"));
			                ps2.setString(12, rss.getString("MONTH_CLOSING_BAL_DR_CR_IND"));
			                ps2.setInt(13, 0);
			                ps2.setInt(14, 0);
			                ps2.setDouble(15, rss.getDouble("MONTH_CLOSING_BALANCE"));
			                ps2.setString(16, rss.getString("MONTH_CLOSING_BAL_DR_CR_IND"));
		//	                ps2.setDate(17, Dr_update_last);
		//	                ps2.setDate(18, Cr_update_last);
			                ps2.setString(17, userid);
			                long l = System.currentTimeMillis();
			                Timestamp ts = new Timestamp(l);
			                ps2.setTimestamp(18, ts);
			              int k=ps2.executeUpdate();
	              
	              
			              ps2.close();
	         		}
	         	}
         		 
         	 }
         	 rss.close();
         	 pss.close();
         	 connection.commit();
         	 
         	 
            }
            
            String msg =
                "GeneralLedger Posting Details has been Successfully Updated";
            msg = msg + "<br><br>";
            sendMessage(response, msg, "ok");
            //out.write("Generated");
            //out.write("<a href='sample.jsp'>View </a>");

        } catch (Exception e) {
            System.out.println("Exception in Main:" + e);
            try {
                connection.rollback();
            } catch (SQLException e1) {
                System.out.println("catch:" + e1);
            }
            String msg = "GeneralLedger Posting Details Not Updated";
            msg = msg + "<br><br>";
            sendMessage(response, msg, "ok");

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
        } catch (IOException e) {
            System.out.println("Eror in send message");
        }
    }

}
