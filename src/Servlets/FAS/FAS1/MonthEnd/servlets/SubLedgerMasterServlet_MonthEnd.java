package Servlets.FAS.FAS1.MonthEnd.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class SubLedgerMasterServlet_MonthEnd extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {

        /**
         * Set Content Type
         */
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();


        /**
         * Connection Declaration
         */
        Connection con = null;

        /**
         * Database Connection
         */
        try {
            ResourceBundle rs1 =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";

            String strDriver = rs1.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rs1.getString("Config.DSN");
            String strhostname = rs1.getString("Config.HOST_NAME");
            String strportno = rs1.getString("Config.PORT_NUMBER");
            String strsid = rs1.getString("Config.SID");
            String strdbusername = rs1.getString("Config.USER_NAME");
            String strdbpassword = rs1.getString("Config.PASSWORD");
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
            Class.forName(strDriver.trim());
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
        } catch (Exception e) {
            System.out.println("Exception in opening connection :" + e);
        }


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
         * Get User ID
         */
        String userid = (String)session.getAttribute("UserId");
        System.out.println("session id is:" + userid);


        /**
         * Variables Declaration
         */
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps7 = null;
        ResultSet results = null;

        int AccountUnitCode = 0;
        int OfficeCode = 0;
        int CashBookYear = 0;
        int CashBookMonth = 0;
        int cmbMas_SL_type = 0;
        int achcode = 0,trial_count=0;
        double CRAmount = 0;
        double DRAmount1 = 0;
        double balance = 0;
        String MONTH_CLOSING_BAL_DR_CR_IND = "";
        java.sql.Date MaxCRdate = null;
        java.sql.Date MaxDRdate = null;

        
        /** Get Accounting Unit ID */
        String Account_unit_Code = request.getParameter("cmbAcc_UnitCode");
        /** Get Office ID */
        String Office_Code = request.getParameter("cmbOffice_code");
        /** Get Cashbook year */
        String CashBook_Year = request.getParameter("txtCB_Year");
        /** Get Cashbook Month */
        String CashBook_Month = request.getParameter("txtCB_Month");


        System.out.println("Account_Unit_Code is:" + Account_unit_Code);
        System.out.println("Office_Code is:" + Office_Code);
        System.out.println("CashBook_Year is:" + CashBook_Year);
        System.out.println("CashBook Month is   hi:" + CashBook_Month);


        /**
         * Convert String Type Parameters into Corresponding Data Types
         */
        try {
            AccountUnitCode = Integer.parseInt(Account_unit_Code);
            OfficeCode = Integer.parseInt(Office_Code);
            CashBookYear = Integer.parseInt(CashBook_Year);
            CashBookMonth = Integer.parseInt(CashBook_Month);

            System.out.println("Account_Unit_Code After is:" +
                               AccountUnitCode);
            System.out.println("Office_Code After is:" + OfficeCode);
            System.out.println("CashBook_Year After is:" + CashBookYear);
            System.out.println("CashBook Month After is:" + CashBookMonth);
            System.out.println("cmbMas_SL_type is...........:" +
                               cmbMas_SL_type);
            ;

        } catch (Exception e) {
            System.out.println("Exception in Converting Integer:" + e);
        }

        /**
         *  Check Wheather TB has been Frozen already or not
         */
        
        
        
        int checkcountTB=0;
        try{
        
       
       String checkTB="  select COUNT(*) as cout from FAS_TRIAL_BALANCE_STATUS    WHERE  "+                    
         " ACCOUNTING_UNIT_ID= "+AccountUnitCode;
         
         PreparedStatement ps0=con.prepareStatement(checkTB);
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
        //dhana changes on 19mar2012
  try {

      ps =con.prepareStatement("  select 'X'                  \n" +
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
          sendMessage(response, "Trial Balance Already Froze For SL", "ok");
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
		  	
			  try {
					PreparedStatement ps_chk=con.prepareStatement("select to_char(DATE_OF_OPENING,'yyyy')as year, to_char(DATE_OF_OPENING,'mm')as month from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID="+AccountUnitCode);
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
		      ps =con.prepareStatement("  select 'X'                  \n" +
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
  	 		      ps =con.prepareStatement("  select 'X'                  \n" +
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
        

        /**
         * Calculating New Cashbook Month and Year
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
         * Variables Declaration
         */
        double cur_yr_crbal = 0;
        double cur_yr_drbal = 0;
        double Month_OB_Bal = 0;
        String Month_OB_Cr_Dr_ind = "";
        int sltypecode = 0;
        Long slcode;
        double Upto_Debit_Bal = 0;
        double Upto_Credit_Bal = 0;
        double Month_OB_Bal_new = 0;
        String Month_OB_Cr_Dr_ind_new = "";
        String NFinYear = "";

        /**
         * Calculate Financial year from cashbook Month and cashbook Year
         */
//        if ((CashBookMonth <= 1 && CashBookMonth >= 3)) {
//            NFinYear = (CashBookYear - 1) + "-" + (CashBookYear);
//        } else {
//            NFinYear = (CashBookYear) + "-" + (CashBookYear + 1);
//        }
//        
        if (CashBookMonth > 3) {
        	NFinYear = (CashBookYear ) + "-" + (CashBookYear+1);
        }else {
        	NFinYear = (CashBookYear-1 ) + "-" + (CashBookYear);
        }

        /**
         * Get Current Data and Time
         */
        long l = System.currentTimeMillis();
        Timestamp ts = new Timestamp(l);


        /**
         * Actual Procedure Starts here  ----------------------------------------- Begin
         */
        try {
        		System.out.println("comes here::::::");
           
            con.setAutoCommit(false);

         System.out.println("AccountUnitCode:"+AccountUnitCode+":OfficeCode:"+OfficeCode+":CashBookYear"+CashBookYear+"::"+CashBookMonth);
            String sql_del ="delete from fas_sub_ledger_master where accounting_unit_id=? and accounting_for_office_id=?  and year=? and month=? ";
         // System.out.println("sql_del  111 "+sql_del);
            ps = con.prepareStatement(sql_del);
            ps.setInt(1, AccountUnitCode);
            ps.setInt(2, OfficeCode);
            ps.setInt(3, CashBookYear);
            ps.setInt(4, CashBookMonth);
            System.out.println("sql_del 2222 ");
            int one=ps.executeUpdate();
            System.out.println("welc om e ");
            ps.close();
            System.out.println("be22222:::"+one);

            /**
                             * Delete all the Previous entries in Transaction Table
                             */
            String sqlquery5 =
                "delete from fas_sub_ledger_transaction where accounting_unit_id=? and accounting_for_office_id=? " +
                " and year=? and month=? ";
          
            ps2 = con.prepareStatement(sqlquery5);
            ps2.setInt(1, AccountUnitCode);
            ps2.setInt(2, OfficeCode);
            ps2.setInt(3, CashBookYear);
            ps2.setInt(4, CashBookMonth);
            int two=ps2.executeUpdate();
            ps2.close();
            System.out.println("be333333:::"+two);

            /**
 *  Part I
 */
            
            
            
            System.out.println("");

            String sqlquery =
                "select account_head_code,financial_year,current_year_debit_balance,current_year_credit_balance, " +
                " current_month_debit,current_month_credit,month_closing_balance,month_closing_bal_dr_cr_ind,"+
                 "SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,DR_UPDATE_LAST_DATE,CR_UPDATE_LAST_DATE"+
                " from fas_sub_ledger_master " +
                " where accounting_unit_id=? and accounting_for_office_id=? and year=? and month=?"+
                " order by account_head_code,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE";
            ps = con.prepareStatement(sqlquery);
            ps.setInt(1, AccountUnitCode);
            ps.setInt(2, OfficeCode);
            ps.setInt(3, cashbookyear1);
            ps.setInt(4, cashbookmonth1);
            results = ps.executeQuery();
            while (results.next()) {
                System.out.println("inside while..");
                cur_yr_crbal =
                        results.getDouble("CURRENT_MONTH_CREDIT") + results.getDouble("CURRENT_YEAR_CREDIT_BALANCE");
                cur_yr_drbal =
                        results.getDouble("CURRENT_MONTH_DEBIT") + results.getDouble("CURRENT_YEAR_DEBIT_BALANCE");
                Month_OB_Bal = results.getDouble("MONTH_CLOSING_BALANCE");
                Month_OB_Cr_Dr_ind =
                        results.getString("MONTH_CLOSING_BAL_DR_CR_IND");
                Date Dr_update_last = results.getDate("DR_UPDATE_LAST_DATE");
                Date Cr_update_last = results.getDate("CR_UPDATE_LAST_DATE");


                achcode = results.getInt("account_head_code");
                sltypecode = results.getInt("SUB_LEDGER_TYPE_CODE");
                slcode = results.getLong("sub_ledger_code");


                System.out.println("hi..1" + achcode);

                String sqlquery1 =
                    "insert into fas_sub_ledger_master(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR,YEAR,MONTH, " +
                    "ACCOUNT_HEAD_CODE,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,PROJECT_ID,UPTO_DEBIT_BALANCE,UPTO_CREDIT_BALANCE," +
                    "CURRENT_YEAR_DEBIT_BALANCE,CURRENT_YEAR_CREDIT_BALANCE,MONTH_OPENING_BALANCE, " +
                    "MONTH_OPENING_BAL_DR_CR_IND,CURRENT_MONTH_DEBIT,CURRENT_MONTH_CREDIT,MONTH_CLOSING_BALANCE, " +
                    "MONTH_CLOSING_BAL_DR_CR_IND,DR_UPDATE_LAST_DATE,CR_UPDATE_LAST_DATE,UPDATED_BY_USER_ID," +
                    "UPDATED_DATE ) " +
                    "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                System.out.println("qry fas_sub_ledger_master tab insetr "+sqlquery1);
                try{
                ps2 = con.prepareStatement(sqlquery1);
                ps2.setInt(1, AccountUnitCode);
                ps2.setInt(2, OfficeCode);
                ps2.setString(3, NFinYear);
                ps2.setInt(4, CashBookYear);
                ps2.setInt(5, CashBookMonth);
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
                ps2.close();
                System.out.println("hi..2");
                }catch (Exception e){
                	System.out.println("First Insert fas_sub_ledger_master table .... ");
				e.printStackTrace();
				}
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
            ps1.setInt(1, AccountUnitCode);
            ps1.setInt(2, OfficeCode);
            ps1.setInt(3, CashBookYear);
            ps1.setInt(4, CashBookMonth);
            results = ps1.executeQuery();

            while (results.next()) {
                achcode = results.getInt("achcode");
                sltypecode = results.getInt("sltypecode");
                slcode = results.getLong("slcode");

                System.out.println("head code" + achcode);

                String sql_qur =
                    " select  ACCOUNT_HEAD_CODE,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE " +
                    " from FAS_SUB_LEDGER_MASTER " +
                    " where accounting_unit_id=? and accounting_for_office_id=? " +
                    " and year=? and month=?" +
                    " and ACCOUNT_HEAD_CODE=? and SUB_LEDGER_TYPE_CODE=? and SUB_LEDGER_CODE=? ";

                ResultSet results2 = null;
                ps2 = con.prepareStatement(sql_qur);
                ps2.setInt(1, AccountUnitCode);
                ps2.setInt(2, OfficeCode);
                ps2.setInt(3, CashBookYear);
                ps2.setInt(4, CashBookMonth);
                ps2.setInt(5, achcode);
                ps2.setInt(6, sltypecode);
                ps2.setLong(7, slcode);
                results2 = ps2.executeQuery();

                if (results2.next()) {
                    System.out.println("already exists,no need to insert");
                } else {


                    String sqlquery1 =
                        "insert into fas_sub_ledger_master(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR,YEAR,MONTH, " +
                        "ACCOUNT_HEAD_CODE,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,PROJECT_ID,UPTO_DEBIT_BALANCE,UPTO_CREDIT_BALANCE," +
                        "CURRENT_YEAR_DEBIT_BALANCE,CURRENT_YEAR_CREDIT_BALANCE,MONTH_OPENING_BALANCE, " +
                        "MONTH_OPENING_BAL_DR_CR_IND,CURRENT_MONTH_DEBIT,CURRENT_MONTH_CREDIT,MONTH_CLOSING_BALANCE, " +
                        "MONTH_CLOSING_BAL_DR_CR_IND,DR_UPDATE_LAST_DATE,CR_UPDATE_LAST_DATE,UPDATED_BY_USER_ID," +
                        "UPDATED_DATE ) " +
                        "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                try{
                	System.out.println("sqlquery1 >> "+sqlquery1);
                    ps2 = con.prepareStatement(sqlquery1);
                    ps2.setInt(1, AccountUnitCode);
                    ps2.setInt(2, OfficeCode);
                    ps2.setString(3, NFinYear);
                    ps2.setInt(4, CashBookYear);
                    ps2.setInt(5, CashBookMonth);
                    ps2.setInt(6, achcode);
                    ps2.setInt(7, sltypecode);
                    ps2.setLong(8, slcode);
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
                    ps2.close();
                }catch (Exception e) {
System.out.println("2 inseert master table");
e.printStackTrace();
                }
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

            String fetchSL =
                " select ACCOUNT_HEAD_CODE,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE  from fas_sub_ledger_master " +
                " where accounting_unit_id=? and accounting_for_office_id=? " +
                " and year=? and month=?";
            ps2 = con.prepareStatement(fetchSL);
            ps2.setInt(1, AccountUnitCode);
            ps2.setInt(2, OfficeCode);
            ps2.setInt(3, CashBookYear);
            ps2.setInt(4, CashBookMonth);
            results = ps2.executeQuery();

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
                ps3.setInt(1, AccountUnitCode);
                ps3.setInt(2, OfficeCode);
                ps3.setInt(3, CashBookYear);
                ps3.setInt(4, CashBookMonth);
                ps3.setInt(5, results.getInt("ACCOUNT_HEAD_CODE"));
                ps3.setInt(6, results.getInt("SUB_LEDGER_TYPE_CODE"));
                ps3.setLong(7, results.getLong("SUB_LEDGER_CODE"));
                ResultSet res = ps3.executeQuery();

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
                ps7.setInt(1, AccountUnitCode);
                ps7.setInt(2, OfficeCode);
                ps7.setInt(3, CashBookYear);
                ps7.setInt(4, CashBookMonth);
                ps7.setInt(5, results.getInt("ACCOUNT_HEAD_CODE"));
                ps7.setInt(6, results.getInt("SUB_LEDGER_TYPE_CODE"));
                ps7.setLong(7, results.getLong("SUB_LEDGER_CODE"));

                ResultSet results3 = ps7.executeQuery();

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
                ps7.setInt(1, AccountUnitCode);
                ps7.setInt(2, OfficeCode);
                ps7.setInt(3, CashBookYear);
                ps7.setInt(4, CashBookMonth);
                ps7.setInt(5, results.getInt("ACCOUNT_HEAD_CODE"));
                ps7.setInt(6, results.getInt("SUB_LEDGER_TYPE_CODE"));
                ps7.setLong(7, results.getLong("SUB_LEDGER_CODE"));
                results3 = ps7.executeQuery();

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

                System.out.println("Final Amount is:" + DRAmount1);

                ps7.close();
                results3.close();


                System.out.println("Month_OB_Cr_Dr_ind_new00" +
                                   Month_OB_Cr_Dr_ind_new);

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
            try{
            	//System.out.println("sqlquery7 >>>"+sqlquery7);
                ps3 = con.prepareStatement(sqlquery7);
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
                ps3.setInt(13, results.getInt("ACCOUNT_HEAD_CODE"));
                ps3.setInt(14, results.getInt("SUB_LEDGER_TYPE_CODE"));
                ps3.setLong(15, results.getLong("SUB_LEDGER_CODE"));
                ps3.executeUpdate();
            }catch (Exception e) {
			System.out.println("update master >>");
			e.printStackTrace();
            }
            
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
                ps_trs1.setInt(1, AccountUnitCode);
                ps_trs1.setInt(2, OfficeCode);
                ps_trs1.setInt(3, CashBookYear);
                ps_trs1.setInt(4, CashBookMonth);
                ps_trs1.setInt(5, results.getInt("ACCOUNT_HEAD_CODE"));
                ps_trs1.setInt(6, results.getInt("SUB_LEDGER_TYPE_CODE"));
                ps_trs1.setLong(7, results.getLong("SUB_LEDGER_CODE"));

                ResultSet rs_trs = ps_trs1.executeQuery();

                while (rs_trs.next()) {
                    count++;
                    System.out.println("first");
                    String trs_ins =
                        "insert into FAS_SUB_LEDGER_TRANSACTION" + "(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,YEAR,MONTH,ACCOUNT_HEAD_CODE," +
                        "SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,FINANCIAL_YEAR,PROJECT_ID,CREDIT_AMOUNT,CR_VOUCHER_TYPE,CR_VOUCHER_NO," +
                        "CR_VOUCHER_DATE,CR_JOURNAL_TYPE_CODE,BILL_NO,BILL_DATE,AGREEMENT_NO,PARTICULARS,UPDATED_BY_USER_ID,UPDATED_DATE,SL_NO)" +
                        " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                    /** Closed */
                    try{
                    	System.out.println("trs_ins"+trs_ins);
                    
                    PreparedStatement ps_CR = con.prepareStatement(trs_ins);
                    ps_CR.setInt(1, AccountUnitCode);
                    ps_CR.setInt(2, OfficeCode);
                    ps_CR.setInt(3, CashBookYear);
                    ps_CR.setInt(4, CashBookMonth);
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
                    ps_CR.close();
                    /** Closed */

                    SLNO++;

                }catch (Exception e) {
System.out.println("trn_iserrt");
e.printStackTrace();
                }
                    }


                ps_trs1.close();
                /** Closed -- XYZ */


                // GET DR details
                System.out.println("here...7");
                trans =
" select AMOUNT,VOUTYPE,VOUNO,DRDATE,DR_JOUR_TYPE_CODE,BILL_NO,BILL_DATE,AGREEMENT_NO,PARTICULARS  " +
" from FAS_HEAD_SLTYPE_DR_TRN_VIEW " +
" where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? " +
" and ACHCODE=? and SLTYPECODE=? and SLCODE=? ";


                /** Closed -- IJK */
                PreparedStatement ps_trs2 = con.prepareStatement(trans);
                ps_trs2.setInt(1, AccountUnitCode);
                ps_trs2.setInt(2, OfficeCode);
                ps_trs2.setInt(3, CashBookYear);
                ps_trs2.setInt(4, CashBookMonth);
                ps_trs2.setInt(5, results.getInt("ACCOUNT_HEAD_CODE"));
                ps_trs2.setInt(6, results.getInt("SUB_LEDGER_TYPE_CODE"));
                ps_trs2.setLong(7, results.getLong("SUB_LEDGER_CODE"));
                rs_trs = ps_trs2.executeQuery();

                while (rs_trs.next()) {
                    count++;
                    System.out.println("second..");
                    String trs_ins =
                        "insert into FAS_SUB_LEDGER_TRANSACTION" + "(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,YEAR,MONTH,ACCOUNT_HEAD_CODE," +
                        "SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,FINANCIAL_YEAR,PROJECT_ID,DEBIT_AMOUNT,DR_VOUCHER_TYPE,DR_VOUCHER_NO," +
                        "DR_VOUCHER_DATE,DR_JOURNAL_TYPE_CODE,BILL_NO,BILL_DATE,AGREEMENT_NO,PARTICULARS,UPDATED_BY_USER_ID,UPDATED_DATE,SL_NO)" +
                        " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                    /** Closed */
                    try{
                    	
                    PreparedStatement ps_CR = con.prepareStatement(trs_ins);
                    ps_CR.setInt(1, AccountUnitCode);
                    ps_CR.setInt(2, OfficeCode);
                    ps_CR.setInt(3, CashBookYear);
                    ps_CR.setInt(4, CashBookMonth);
                    ps_CR.setInt(5, results.getInt("ACCOUNT_HEAD_CODE"));
                    ps_CR.setInt(6, results.getInt("SUB_LEDGER_TYPE_CODE"));
                    ps_CR.setLong(7, results.getLong("SUB_LEDGER_CODE"));
                    ps_CR.setString(8, NFinYear);
                    ps_CR.setInt(9, 0);
                    ps_CR.setDouble(10, rs_trs.getDouble("AMOUNT"));
                    ps_CR.setString(11, rs_trs.getString("VOUTYPE"));
                    ps_CR.setInt(12, rs_trs.getInt("VOUNO"));
                    ps_CR.setDate(13, rs_trs.getDate("DRDATE"));
                   // ps_CR.setString(14, rs_trs.getString("DR_JOUR_TYPE_CODE"));
                    ps_CR.setInt(14, rs_trs.getInt("DR_JOUR_TYPE_CODE") );
                   
                    ps_CR.setString(15, rs_trs.getString("BILL_NO"));
                    ps_CR.setDate(16, rs_trs.getDate("BILL_DATE"));
                    ps_CR.setString(17, rs_trs.getString("AGREEMENT_NO"));
                    ps_CR.setString(18, rs_trs.getString("PARTICULARS"));
                    ps_CR.setString(19, userid);
                    ps_CR.setTimestamp(20, ts);
                    ps_CR.setInt(21, SLNO);
                    ps_CR.executeUpdate();
                    ps_CR.close();
                    }catch (Exception e) {
						System.out.println("2nd insert trn");
						e.printStackTrace();
					}
                
                    /** Closed */

                    SLNO++;
                }

                ps_trs2.close();
                /** Closed -- IJK */
System.out.println("count >>> "+count);

                if (count ==0) // if so, there is no transaction  for the combination of ACHCODE and SLTYPECODE and SLCODE , so just insert NULL for all fields
                {
                    String trs_ins =
                        "insert into FAS_SUB_LEDGER_TRANSACTION" + "(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,YEAR,MONTH,ACCOUNT_HEAD_CODE," +
                        "SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,FINANCIAL_YEAR,PROJECT_ID,UPDATED_BY_USER_ID,UPDATED_DATE,SL_NO)" +
                        " values (?,?,?,?,?,?,?,?,?,?,?,?)";

                    /** Closed */
                    try{
                    PreparedStatement ps_CR = con.prepareStatement(trs_ins);
                    ps_CR.setInt(1, AccountUnitCode);
                    ps_CR.setInt(2, OfficeCode);
                    ps_CR.setInt(3, CashBookYear);
                    ps_CR.setInt(4, CashBookMonth);
                    ps_CR.setInt(5, results.getInt("ACCOUNT_HEAD_CODE"));
                    ps_CR.setInt(6, results.getInt("SUB_LEDGER_TYPE_CODE"));
                    ps_CR.setLong(7, results.getLong("SUB_LEDGER_CODE"));
                    ps_CR.setString(8, NFinYear);
                    ps_CR.setInt(9, 0);
                    ps_CR.setString(10, userid);
                    ps_CR.setTimestamp(11, ts);
                    ps_CR.setInt(12, 1); // SL_NO =1
                    ps_CR.executeUpdate();
                    ps_CR.close();
                    }catch (Exception e) {
                    	System.out.println("last insert >> ");
e.printStackTrace();
                    }
                    /** Closed */
                }

                //         Ending transaction part

            } /** End While Loop for Main  */
            ps.close();
            results.close();
            con.commit();

            String msg =
                "SubLedger Posting Details has been Successfully Updated";
            msg = msg + "<br><br>";
            sendMessage(response, msg, "ok");

        } catch (Exception e) {
            System.out.println("Exception in Query:" + e);
            try {
                con.rollback();
            } catch (SQLException e1) {
                System.out.println("catch:" + e1);
            }
            String msg = "SubLedger Posting Details Not Updated";
            msg = msg + "<br><br>";
            sendMessage(response, msg, "ok");
        }

        /**
         * Actual Procedure Ends here  ----------------------------------------- End
         */
    }
        out.close();

    }

    private void sendMessage(HttpServletResponse response, String msg,
                             String bType) {
        try {
            String url =
                "org/Library/jsps/Messenger.jsp?message=" + msg + "&button=" +
                bType;
            response.sendRedirect(url);
           // return;
        } catch (IOException e) {
            System.out.println("ERROR");
        }
    }
}
