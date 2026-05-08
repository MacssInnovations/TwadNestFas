package Servlets.FAS.FAS1.AccountHeadDirectory.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.*;

public class GeneralLedgerServlet_Progress_Bal extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {

        int CashBookYear = 0;
        String CashBook_Year = null;

        /** Get Cashbook Year */
        try {
            CashBook_Year = request.getParameter("txtCB_Year");
            CashBookYear = Integer.parseInt(CashBook_Year);

        } catch (Exception e) {
            System.out.println(e);
        }
        /** Get Cashbook Month */
        int CashBookMonth = 0;
        String CashBook_Month = null;

        try {
            CashBook_Month = request.getParameter("txtCB_Month");
            CashBookMonth = Integer.parseInt(CashBook_Month);
        } catch (Exception e) {
            System.out.println(e);
        }


        System.out.println(" Before Loop ");

        int i;
        int tot_month = 0;

        if (CashBookYear > 2010) {
            tot_month = 12 + CashBookMonth;
        } else {
            tot_month = CashBookMonth;
        }


        for (i = 4; i <= tot_month; i++) {

            System.out.println("Main Loop Start ----------------------------------> " +
                               i);

            /**
        * Variables Declarations
        */
            Connection connection = null;
            Statement statement = null;
            ResultSet results = null;
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
            CallableStatement cs = null;


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

//                ConnectionString =
//                        strdsn.trim() + "@" + strhostname.trim() + ":" +
//                        strportno.trim() + ":" + strsid.trim();
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
                    System.out.println(request.getContextPath() +
                                       "/index.jsp");
                    response.sendRedirect(request.getContextPath() +
                                          "/index.jsp");

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

            //  /** Get Cashbook Year */
            //  String CashBook_Year=request.getParameter("txtCB_Year");


            //  /** Get Cashbook Month */
            //  String CashBook_Month=request.getParameter("txtCB_Month");


            Statement st_ex = null;
            ResultSet rs_ex = null;

            try {
                String s =
                    " select                \n" + "       a.accounting_unit_id,     \n" +
                    "       (                         \n" +
                    "          select                 \n" +
                    "              accounting_unit_office_id                 \n" +
                    "         from fas_mst_acct_units                        \n" +
                    "         where accounting_unit_id= a.accounting_unit_id \n" +
                    "       ) as office_id                                   \n" +
                    "                                                        \n" +
                    "from                                          \n" +
                    "      fas_trial_balance_status a              \n" +
                    "where                                         \n" +
                    "    a.cashbook_year=2010                      \n" +
                    "and a.cashbook_month=4                        \n" +
                    "and a.tb_status='Y'  and a.accounting_unit_id =" +
                    Account_unit_Code + "order by a.accounting_unit_id    \n ";


                st_ex = connection.createStatement();
                rs_ex = st_ex.executeQuery(s);
                while (rs_ex.next()) {
                    Office_Code = rs_ex.getString("office_id");
                }
            } catch (Exception e) {
                System.out.println(e);
            }


            /**
         *  Variables Declarations
         */
            int AccountUnitCode = 0;
            int OfficeCode = 0;
            //int CashBookYear=0;
            //int CashBookMonth=0;
            double CRAmount = 0;
            double DRAmount1 = 0;
            double balance = 0;
            String MONTH_CLOSING_BAL_DR_CR_IND = "";
            java.sql.Date MaxCRdate = null;
            java.sql.Date MaxDRdate = null;
            int achcode = 0;
            int acchcode = 0;


            /**
         * Convert String Data into Intger Value
         */
            try {
                AccountUnitCode = Integer.parseInt(Account_unit_Code);
                OfficeCode = Integer.parseInt(Office_Code);
                //  CashBookYear=Integer.parseInt(CashBook_Year);
                //  CashBookMonth=Integer.parseInt(CashBook_Month);
            } catch (Exception e) {
                System.out.println("Exception in Converting Integer:" + e);
            }


            /**
         * This Two Variables for calculating cashbookmonth and year
         */
            int cashbookmonth1 = 0;
            cashbookmonth1 = CashBookMonth - 1;
            int cashbookyear1 = 0;

            if (cashbookmonth1 == 0) {
                cashbookmonth1 = 12;
                cashbookyear1 = CashBookYear - 1;
            } else {
                cashbookmonth1 = CashBookMonth - 1;
                cashbookyear1 = CashBookYear;
            }


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
                    achead = results.getInt("ACCOUNT_HEAD_CODE");
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
                            //    System.out.println("A or L heads");
                            cur_yr_crbal =
                                    results.getDouble("CURRENT_MONTH_CREDIT") +
                                    results.getDouble("CURRENT_YEAR_CREDIT_BALANCE");
                            cur_yr_drbal =
                                    results.getDouble("CURRENT_MONTH_DEBIT") +
                                    results.getDouble("CURRENT_YEAR_DEBIT_BALANCE");
                        } else {
                            //   System.out.println("not A or L heads");
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

                    String NFinYear = "";
                    if ((CashBookMonth >= 1 && CashBookMonth <= 3)) {
                        NFinYear = (CashBookYear - 1) + "-" + (CashBookYear);
                    } else {
                        NFinYear = (CashBookYear) + "-" + (CashBookYear + 1);
                    }

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


                } //End Main While()

                results.close();
                ps.close();


                /**
             * Insert Account Heads Which are not available in the previous month
             */

                String sqlqueryselect = "";
                if (AccountUnitCode == 5) // for the case of banking section
                {

                    sqlqueryselect =
                            "select distinct achcode from  FAS_BANK_HEADS_VIEW " +
                            " where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? " +
                            " and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? ";
                } else {

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
                while (results2.next()) {
                    achcode = results2.getInt("achcode");

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

                    } else {
                        String NFinYear = "";
                        if ((CashBookMonth >= 1 && CashBookMonth <= 3)) {
                            NFinYear =
                                    (CashBookYear - 1) + "-" + (CashBookYear);
                        } else {
                            NFinYear =
                                    (CashBookYear) + "-" + (CashBookYear + 1);
                        }

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

                while (results2.next()) //Start second Main While
                {
                    acchcode = results2.getInt("ACCOUNT_HEAD_CODE");
                    Month_OB_Bal_new =
                            results2.getDouble("MONTH_OPENING_BALANCE");
                    Month_OB_Cr_Dr_ind_new =
                            results2.getString("MONTH_OPENING_BAL_DR_CR_IND");
                    String sqlquery6 = "";


                    if (AccountUnitCode == 5) {

                        sqlquery6 =
                                "select  amount1,CRdate " + " from FAS_BANK_CR_HEADS_VIEW where " +
                                " accounting_unit_id=? and accounting_for_office_id=? " +
                                " and cashbook_year=? and cashbook_month=? and ACHCODE=?";
                    } else {
                        sqlquery6 =
                                "select  amount1,CRdate " + " from FAS_NON_BANK_CR_HEADS_VIEW where " +
                                " accounting_unit_id=? and accounting_for_office_id=? " +
                                " and cashbook_year=? and cashbook_month=? and ACHCODE=?";
                    }

                    ps7 = connection.prepareStatement(sqlquery6);
                    ps7.setInt(1, AccountUnitCode);
                    ps7.setInt(2, OfficeCode);
                    ps7.setInt(3, CashBookYear);
                    ps7.setInt(4, CashBookMonth);
                    ps7.setInt(5, acchcode);
                    results3 = ps7.executeQuery();

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

                    String sql1 = "";
                    if (AccountUnitCode == 5) // banking section
                    {
                        sql1 =
"select  amount2,DRdate " + " from FAS_BANK_DR_HEADS_VIEW where " +
 " accounting_unit_id=? and accounting_for_office_id=? " +
 " and cashbook_year=? and cashbook_month=? and ACHCODE=?";


                    } else {

                        sql1 =
"select  amount2,DRdate " + " from FAS_NON_BANK_DR_HEADS_VIEW where " +
 " accounting_unit_id=? and accounting_for_office_id=? " +
 " and cashbook_year=? and cashbook_month=? and ACHCODE=?";
                    }

                    ps6 = connection.prepareStatement(sql1);
                    ps6.setInt(1, AccountUnitCode);
                    ps6.setInt(2, OfficeCode);
                    ps6.setInt(3, CashBookYear);
                    ps6.setInt(4, CashBookMonth);
                    ps6.setInt(5, acchcode);
                    results4 = ps6.executeQuery();
                    while (results4.next()) {

                        DRAmount1 = DRAmount1 + results4.getDouble("amount2");
                        if (MaxDRdate == null)
                            MaxDRdate = results4.getDate("DRdate");

                        if (results4.getDate("DRdate") != null) {
                            if (MaxDRdate.compareTo(results4.getDate("DRdate")) <
                                0) {
                                MaxDRdate = results4.getDate("DRdate");

                            }
                        }

                    }

                    ps6.close();
                    results4.close();

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
                    ps3.executeUpdate();

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
                } //End second Main While


                /**
             * Procedure Calling
             */


                if (i == 4) {

                    System.out.println("Progressive Balance Calling Start  ----------------------------------> " +
                                       i);

                    cs =
//  connection.prepareCall("{call FAS_PROGRESSIVE_GL_POSTING (?,?,?,?,?,?,?) }");
//                    cs.setInt(1, AccountUnitCode);
//                    cs.setInt(2, OfficeCode);
//                    cs.setInt(3, CashBookYear);
//                    cs.setInt(4, CashBookMonth);
//                    cs.setString(5, "one");
//                    cs.setString(6, userid);
//                    cs.registerOutParameter(7, java.sql.Types.NUMERIC);
//                    cs.execute();
//                    int errcode = cs.getInt(7);
  connection.prepareCall("call FAS_PROGRESSIVE_GL_POSTING (?::numeric,?::numeric,?::numeric,?::numeric,?,?,?::numeric) ");
                    cs.setInt(1, AccountUnitCode);
                    cs.setInt(2, OfficeCode);
                    cs.setInt(3, CashBookYear);
                    cs.setInt(4, CashBookMonth);
                    cs.setString(5, "one");
                    cs.setString(6, userid);
                    cs.registerOutParameter(7, java.sql.Types.NUMERIC);
                    cs.setNull(7,java.sql.Types.NUMERIC);
                    cs.execute();
                    //int error_code = cs.getInt(7);
                    int errcode = cs.getBigDecimal(7).intValue();            
                    System.out.println("Error Code --->" + errcode);

                    if (errcode == 0) {

                        sqlquery5 =
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

                        while (results2.next()) //Start second Main While
                        {
                            acchcode = results2.getInt("ACCOUNT_HEAD_CODE");
                            Month_OB_Bal_new =
                                    results2.getDouble("MONTH_OPENING_BALANCE");
                            Month_OB_Cr_Dr_ind_new =
                                    results2.getString("MONTH_OPENING_BAL_DR_CR_IND");
                            String sqlquery6 = "";


                            if (AccountUnitCode == 5) // banking section
                            {

                                sqlquery6 =
                                        "select  amount1,CRdate " + " from FAS_BANK_CR_HEADS_VIEW where " +
                                        " accounting_unit_id=? and accounting_for_office_id=? " +
                                        " and cashbook_year=? and cashbook_month=? and ACHCODE=?";
                            } else {


                                sqlquery6 =
                                        "select  amount1,CRdate " + " from FAS_NON_BANK_CR_HEADS_VIEW where " +
                                        " accounting_unit_id=? and accounting_for_office_id=? " +
                                        " and cashbook_year=? and cashbook_month=? and ACHCODE=?";
                            }

                            ps7 = connection.prepareStatement(sqlquery6);
                            ps7.setInt(1, AccountUnitCode);
                            ps7.setInt(2, OfficeCode);
                            ps7.setInt(3, CashBookYear);
                            ps7.setInt(4, CashBookMonth);
                            ps7.setInt(5, acchcode);
                            results3 = ps7.executeQuery();

                            while (results3.next()) {

                                CRAmount =
                                        CRAmount + results3.getDouble("amount1");

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

                            String sql1 = "";
                            if (AccountUnitCode == 5) // banking section
                            {


                                sql1 =
"select  amount2,DRdate " + " from FAS_BANK_DR_HEADS_VIEW where " +
 " accounting_unit_id=? and accounting_for_office_id=? " +
 " and cashbook_year=? and cashbook_month=? and ACHCODE=?";


                            } else {

                                sql1 =
"select  amount2,DRdate " + " from FAS_NON_BANK_DR_HEADS_VIEW where " +
 " accounting_unit_id=? and accounting_for_office_id=? " +
 " and cashbook_year=? and cashbook_month=? and ACHCODE=?";
                            }

                            ps6 = connection.prepareStatement(sql1);
                            ps6.setInt(1, AccountUnitCode);
                            ps6.setInt(2, OfficeCode);
                            ps6.setInt(3, CashBookYear);
                            ps6.setInt(4, CashBookMonth);
                            ps6.setInt(5, acchcode);
                            results4 = ps6.executeQuery();
                            while (results4.next()) {
                                DRAmount1 =
                                        DRAmount1 + results4.getDouble("amount2");
                                if (MaxDRdate == null)
                                    MaxDRdate = results4.getDate("DRdate");
                                if (results4.getDate("DRdate") != null) {
                                    if (MaxDRdate.compareTo(results4.getDate("DRdate")) <
                                        0) {
                                        MaxDRdate = results4.getDate("DRdate");

                                    }
                                }

                            }

                            ps6.close();
                            results4.close();

                            if (Month_OB_Cr_Dr_ind_new.equalsIgnoreCase("CR")) {
                                Upto_Credit_Bal = Month_OB_Bal_new + CRAmount;
                                balance =
                                        -(DRAmount1 - (CRAmount + Month_OB_Bal_new));
                            } else {
                                Upto_Debit_Bal = Month_OB_Bal_new + DRAmount1;
                                balance =
                                        -((Month_OB_Bal_new + DRAmount1) - CRAmount);
                            }

                            if (balance >= 0) {
                                MONTH_CLOSING_BAL_DR_CR_IND = "CR";
                            } else {
                                MONTH_CLOSING_BAL_DR_CR_IND = "DR";
                            }


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
                            ps3.executeUpdate();

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


                        } //End second Main While

                        cs =
  connection.prepareCall("call FAS_PROGRESSIVE_GL_POSTING (?::numeric,?::numeric,?::numeric,?::numeric,?,?,?::numeric) ");
                        cs.setInt(1, AccountUnitCode);
                        cs.setInt(2, OfficeCode);
                        cs.setInt(3, CashBookYear);
                        cs.setInt(4, CashBookMonth);
                        cs.setString(5, "two");
                        cs.setString(6, userid);
                        cs.registerOutParameter(7, java.sql.Types.NUMERIC);
                        cs.setNull(7,java.sql.Types.NUMERIC);
                        cs.execute();
                        //int error_code = cs.getInt(7);
                        int error_code = cs.getBigDecimal(7).intValue();
                        System.out.println("Error Code --->" + error_code);
                        if (error_code == 0) {

                            /**
                       *  Automatic Updation of Trial Balance
                       */
                            /*
                      int err_code=2;
                      System.out.println("err"+err_code);

                      cs=connection.prepareCall("{call FAS_TRIAL_BALANCE_PROC(?,?,?,?,?)}");
                      cs.setInt(1,AccountUnitCode);
                      cs.setInt(2,CashBookYear);
                      cs.setInt(3,CashBookMonth);
                      cs.setString(4,userid);
                      cs.registerOutParameter(5,java.sql.Types.NUMERIC);
                      cs.execute();
                      err_code=cs.getInt(5);
                      System.out.println("2 nd err"+err_code);
                      if(err_code == 0)
                       {
                         System.out.println("success");
                         connection.commit();
                       }
                      else if(err_code==16)
                       {
                         connection.rollback();
                         sendMessage(response,"Vouchers are pending for Remittance in cash,bank and fund system...","ok");
                         return;
                       }
                      else if(err_code==15)
                       {
                         connection.rollback();
                         sendMessage(response,"Vouchers are pending for Remittance in bank and fund system ...","ok");
                         return;
                       }
                      else if(err_code==14)
                       {
                         connection.rollback();
                         sendMessage(response,"Vouchers are pending for Remittance in cash and fund system...","ok");
                         return;
                       }
                      else if(err_code==13)
                       {
                         connection.rollback();
                         sendMessage(response,"Vouchers are pending for Remittance in cash and bank system...","ok");
                         return;
                       }
                      else if(err_code==12)
                       {
                         connection.rollback();
                         sendMessage(response,"Vouchers are pending for Remittance in cash system ...","ok");
                         return;
                       }
                      else if(err_code==11)
                       {
                         connection.rollback();
                         sendMessage(response,"Vouchers are pending for Remittance in bank system...","ok");
                         return;
                       }
                      else if(err_code==10)
                       {
                         connection.rollback();
                         sendMessage(response,"Vouchers are pending for Remittance in fund system...","ok");
                         return;
                       }
                      else if(err_code==9)
                       {
                         connection.rollback();
                         sendMessage(response,"You should make General Ledger Posting...","ok");
                         return;
                       }
                       else
                       {
                           sendMessage(response,"Automatic Updation of Trial Balance failed","ok");
                           connection.rollback();
                           return;
                       }
                         */
                        } else {
                            try {
                                connection.rollback();
                            } catch (SQLException e1) {
                                System.out.println("catch:" + e1);
                            }
                            String msg =
                                "GeneralLedger Posting Details Not Updated";
                            msg = msg + "<br><br>";
                            sendMessage(response, msg, "ok");
                        }

                    }


                    System.out.println("Progressive Balance calling End  ----------------------------------> " +
                                       i);

                } //  if (CashBookMonth == 4 )


                System.out.println(" Trial Balance Start  ----------------------------------> " +
                                   i);


                /**
              *  Automatic Updation of Trial Balance
              */

                /*  int err_code=2;
             System.out.println("err"+err_code);

             cs=connection.prepareCall("{call FAS_TRIAL_BALANCE_PROC(?,?,?,?,?)}");
             cs.setInt(1,AccountUnitCode);
             cs.setInt(2,CashBookYear);
             cs.setInt(3,CashBookMonth);
             cs.setString(4,userid);
             cs.registerOutParameter(5,java.sql.Types.NUMERIC);
             cs.execute();
             err_code=cs.getInt(5);
             System.out.println("2 nd err"+err_code);
             if(err_code == 0)
              {
                System.out.println("success");
                connection.commit();
              }
             else if(err_code==16)
              {
                connection.rollback();
                sendMessage(response,"Vouchers are pending for Remittance in cash,bank and fund system...","ok");
                return;
              }
             else if(err_code==15)
              {
                connection.rollback();
                sendMessage(response,"Vouchers are pending for Remittance in bank and fund system ...","ok");
                return;
              }
             else if(err_code==14)
              {
                connection.rollback();
                sendMessage(response,"Vouchers are pending for Remittance in cash and fund system...","ok");
                return;
              }
             else if(err_code==13)
              {
                connection.rollback();
                sendMessage(response,"Vouchers are pending for Remittance in cash and bank system...","ok");
                return;
              }
             else if(err_code==12)
              {
                connection.rollback();
                sendMessage(response,"Vouchers are pending for Remittance in cash system ...","ok");
                return;
              }
             else if(err_code==11)
              {
                connection.rollback();
                sendMessage(response,"Vouchers are pending for Remittance in bank system...","ok");
                return;
              }
             else if(err_code==10)
              {
                connection.rollback();
                sendMessage(response,"Vouchers are pending for Remittance in fund system...","ok");
                return;
              }
             else if(err_code==9)
              {
                connection.rollback();
                sendMessage(response,"You should make General Ledger Posting...","ok");
                return;
              }
              else
              {
                  sendMessage(response,"Automatic Updation of Trial Balance failed","ok");
                  connection.rollback();
                  return;
              }

            System.out.println(" Trial Balance End  ----------------------------------> "+ i );

           */
                ps1.close();
                results2.close();
                connection.commit();


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

            System.out.println("Main Loop End ----------------------------------> " +
                               i);

        } // End of Main For Loop  for( i=4;i<=CashBookMonth;i++)


        String msg =
            "GeneralLedger Posting Details has been Successfully Updated";
        msg = msg + "<br><br>";
        sendMessage(response, msg, "ok");


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

