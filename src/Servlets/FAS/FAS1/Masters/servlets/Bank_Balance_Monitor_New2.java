package Servlets.FAS.FAS1.Masters.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.math.BigDecimal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class Bank_Balance_Monitor_New2 extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {

        response.setContentType(CONTENT_TYPE);


        /**
         * Variables Declaration
         */
        Connection connection = null;
        Statement statement = null;
        PreparedStatement ps = null;
        ResultSet rs1 = null;

        PreparedStatement ps11 = null;
        ResultSet rs11 = null;

        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();


        /** Session Checking */
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


        String userid = (String)session.getAttribute("UserId");
        System.out.println("session id is:" + userid);


        /** Database Connection */
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


        /** Get Command Parameter */
        String command = request.getParameter("Command");

        /** Accounting Unit id */
        String Account_unit_Code = request.getParameter("AccUnit");
        System.out.println("Account_unit_Code" + Account_unit_Code);

        /** Accounting Office ID */
        String Office_Code = request.getParameter("cmbOffice_code");
        System.out.println("Office_Code" + Office_Code);

        /** Get Cashbook Month and Year */
        String CashBook_Year = request.getParameter("txtCB_Year");
        String CashBook_Month = request.getParameter("txtCB_Month");


        int AccountUnitCode = 0;
        int OfficeCode = 0;
        int CashBookYear = 0;
        int closebalmonth = 0;
        int CashBookMonth = 0;
        NumberFormat fmt = NumberFormat.getNumberInstance();
        fmt.setMinimumFractionDigits(2);


        /** Convert String Data into Integer Data -- Accounting Unit ID */
        try {
            AccountUnitCode = Integer.parseInt(Account_unit_Code);
        } catch (Exception e) {
            System.out.println("Exception in Unit:" + e);
            AccountUnitCode = 0;
        }

        /** Convert String Data into Integer Data -- Cashbook Year */
        try {
            CashBookYear = Integer.parseInt(CashBook_Year);
            System.out.println("CashBook_Year After is:" + CashBookYear);
        } catch (Exception e) {
            System.out.println("Exception in Year:" + e);
            CashBookYear = 0;
        }

        /** Convert String Data into Integer Data -- Cashbook Month */
        try {
            CashBookMonth = Integer.parseInt(CashBook_Month);
            System.out.println("CashBook Month After is:" + CashBookMonth);
        } catch (Exception e) {
            System.out.println("Exception in Month:" + e);
            CashBookMonth = 0;
        }


        try {
            closebalmonth = CashBookMonth - 1;
            System.out.println("CashBook Month After is:" + closebalmonth);
        } catch (Exception e) {
            System.out.println("Exception in closebalmonth:" + e);
            CashBookMonth = 0;
        }


        if (command.equalsIgnoreCase("Collection")) {

            String xml = "";
            int Branch = 0;
            int Bank = 0;
            String AcType = "";
            long BankAcNo = 0;
            int AccUnit = 0;
            int AccHead = 0;
            String OpeningBal = "";
            String Oprmode = "";
            java.sql.Date VerifyDate = null;

            /** Get Branck ID */
            Branch = Integer.parseInt(request.getParameter("BranchId"));
            System.out.println("BranchId:" + Branch);

            /** Get Bank ID */
            Bank = Integer.parseInt(request.getParameter("BankId"));
            System.out.println("Bank:" + Bank);

            /** Get Operation Mode */
            Oprmode = request.getParameter("txtOperation_mode");
            System.out.println("Oprmode:" + Oprmode);

            /** Get Bank Account Type */
            AcType = request.getParameter("txtBankAcc_type");
            System.out.println("AcType:" + AcType);

            /** Get Bank Account Number */
            BankAcNo = Long.parseLong(request.getParameter("BankAcNo"));
            System.out.println("BankAcNo:" + BankAcNo);

            /** Get Accounting Unit Id */
            AccUnit = Integer.parseInt(request.getParameter("AccUnit"));
            System.out.println("AccUnit:" + AccUnit);

            /** Get Date */
            String Verify_on = request.getParameter("txtverify_date");
            System.out.println("Verify_on:" + Verify_on);


            /** Find Office Code */
            try {
                ps =
  connection.prepareStatement("select ACCOUNTING_UNIT_OFFICE_ID from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=?");
                ps.setInt(1, AccUnit);
                rs1 = ps.executeQuery();
                while (rs1.next()) {
                    OfficeCode = rs1.getInt("ACCOUNTING_UNIT_OFFICE_ID");
                }
            } catch (Exception e) {
                System.out.println("Finding officecode failed due to exception" +
                                   e);
            }


            try {


                /** Find Corresponding Account Head Code towards Bank Account Number */

                ps =
  connection.prepareStatement("select distinct " + " N.AC_HEAD_CODE" +
                              " from " +
                              " FAS_MST_BANK_BALANCE bb,FAS_MST_BANKS bk,FAS_MST_BANK_BRANCHES br,FAS_MST_BANK_AC_TYPES t," +
                              " FAS_MST_AC_OPER_MODES m,fas_office_bank_ac_current N " +
                              " where bb.ACCOUNTING_UNIT_ID=? and bb.BANK_AC_NO=? and " +
                              " t.ACCOUNT_TYPE_ID=bb.BANK_AC_TYPE_ID and bb.AC_OPERATIONAL_MODE_ID=m.AC_OPERATIONAL_MODE_ID " +
                              " and bb.BANK_ID=br.BANK_ID and bb.BRANCH_ID=br.BRANCH_ID" +
                              " and bb.BANK_ID=bk.BANK_ID AND N.ACCOUNTING_UNIT_ID=? AND bb.bank_id=N.bank_id AND" +
                              " bb.branch_id=N.branch_id AND" +
                              " bb.BANK_AC_TYPE_ID= N.BANK_AC_TYPE_ID and bb.BANK_AC_NO=? and bk.bank_id=N.bank_id");
                ps.setInt(1, AccUnit);
                ps.setLong(2, BankAcNo);
                ps.setInt(3, AccUnit);
                ps.setLong(4, BankAcNo);
                rs1 = ps.executeQuery();

                System.out.println("BankAcNo----->>>" + BankAcNo);


                while (rs1.next()) {
                    try {
                        if (rs1.getInt("AC_HEAD_CODE") == 0) {
                            AccHead = 0;
                        } else {
                            AccHead = rs1.getInt("AC_HEAD_CODE");
                            System.out.println("AC_HEAD_CODE" + AccHead);
                        }
                    } catch (Exception e) {
                        System.out.println("Error in getting date values");
                    }
                }


                System.out.println("BranchId  AND ACTYPE is:" + Branch + "-" +
                                   Bank + "-" + BankAcNo + "-" + AccHead +
                                   "-" + AccUnit + "-" + CashBookYear + "-" +
                                   CashBookMonth + "-" + OpeningBal);


                /** Checking Data format */
                if (Verify_on != "" && Verify_on != null) {
                    String dateString = Verify_on;
                    SimpleDateFormat dateFormat =
                        new SimpleDateFormat("dd/MM/yyyy");
                    java.util.Date d;

                    try {
                        d = dateFormat.parse(dateString.trim());
                        dateFormat.applyPattern("yyyy-MM-dd");
                        dateString = dateFormat.format(d);
                        VerifyDate = java.sql.Date.valueOf(dateString);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                String sql ="";

                System.out.println("Verify date is:" + VerifyDate);


                /** Collection Account */
                if (Oprmode.equalsIgnoreCase("COL")) {

                    System.out.println("This is Collection Details Amount ------------------>>");

                    try {
                         sql =
                            "  " + "select                         \n" +
                            "  ob_bal,                      \n" +
                            "  cr_amt,                      \n" +
                            "  br_amt,                      \n" +
                            "  fr_amt,                      \n" +
                            "  remittance,                  \n" +
                            "  withdrawals,                 \n" +
                            "  trim(to_char(cb_cashbook,'9999999999999999.99')) as cb_cashbook,           \n" +
                            "  trim(to_char(cb_remittance,'9999999999999999.99')) as cb_remittance,       \n" +
                            "  ( cb_cashbook - cb_remittance ) as final_amt                         \n" +
                            "from                                                                   \n" +
                            "(                                                                      \n" +
                            " select                                                                \n" +
                            "  trim(to_char(ob_bal,'9999999999999999.99')) as ob_bal,               \n" +
                            "  trim(to_char(cr_amt,'9999999999999999.99')) as cr_amt,               \n" +
                            "  trim(to_char(br_amt,'9999999999999999.99')) as br_amt,               \n" +
                            "  fr_amt,                                                              \n" +
                            "  trim(to_char(remittance,'9999999999999999.99')) as remittance,       \n" +
                            "  trim(to_char(withdrawals,'9999999999999999.99')) as withdrawals,     \n" +
                            "  (( ob_bal + cr_amt +  br_amt) - withdrawals ) as cb_cashbook,        \n" +
                            "  ( ob_bal + ( remittance - withdrawals )) as cb_remittance            \n" +
                            "from                                                                   \n" +
                            "(                                                                      \n" +
                            "                                                                       \n" +
                            "select                                         \n" +
                            "   sum(ob_bal) as ob_bal,                      \n" +
                            "   sum(cr_amt) as cr_amt,                      \n" +
                            "   sum(br_amt) as br_amt,                      \n" +
                            "   0.00 as fr_amt,                             \n" +
                            "   sum(rem_c_b_amt) as remittance ,            \n" +
                            "   sum(ft_frmoffice_amt) as withdrawals        \n" +
                            "                                               \n" +
                            "from                                           \n" +
                            "(                                              \n" +
                            "                                               \n" +
                            "  -- month opening balance from GL             \n" +
                            /*"  select                                       \n" +
                            "     month_opening_balance as ob_bal,          \n" +
                            "     0 as cr_amt,                              \n" +
                            "     0 as br_amt ,                             \n" +
                            "     0 as rem_c_b_amt,                         \n" +
                            "     0 as ft_frmoffice_amt                     \n" +
                            "  from                                         \n" +
                            "    fas_general_ledger                         \n" +
                            "  where                                        \n" +
                            "      accounting_unit_id = ?                   \n" +
                            "  and month=?                                  \n" +
                            "  and year= ?                                  \n" +
                            "  and account_head_code = 820701               \n" +*/
                            
                            "	SELECT PB_BALANCE AS ob_bal,                \n" +
                            "     0 as cr_amt,                              \n" +
                            "     0 as br_amt ,                             \n" +
                            "     0 as rem_c_b_amt,                         \n" +
                            "     0 as ft_frmoffice_amt                     \n" +
                            "   FROM BRS_BANK_BALANCE_UPDATE                \n" +
                            "   WHERE ACCOUNTING_UNIT_ID= ?                 \n" +
                            "   AND PS_MONTH              = ?               \n" +
                            "   AND PS_YEAR              =?                 \n" +
                            "   AND BANK_AC_NO   = ?                        \n" +
                            "                                               \n" +
                            "  union all                                    \n" +
                            "                                               \n" +
                            "  -- Cash Receipt                              \n" +
                            "  select                                       \n" +
                            "     0 as ob_bal,                              \n" +
                            "     sum(total_amount) as cr_amt,              \n" +
                            "     0 as br_amt,                              \n" +
                            "     0 as rem_c_b_amt,                         \n" +
                            "     0 as ft_frmoffice_amt                     \n" +
                            "                                               \n" +
                            "  from                                         \n" +
                            "      fas_receipt_master                       \n" +
                            "  where                                        \n" +
                            "      accounting_unit_id=?                     \n" +
                            "  and cashbook_month= ?                        \n" +
                            "  and cashbook_year= ?                         \n" +
                            "  and created_by_module = 'CR'                 \n" +
                            "  and receipt_status='L'                       \n" +
                            "  and RECEIPT_DATE <=?                           \n" +
                            "                                               \n" +
                            "  union all                                    \n" +
                            "                                               \n" +
                            "-- Bank Receipt                                \n" +
                            "  select                                       \n" +
                            "      0 as ob_bal,                             \n" +
                            "      0 as cr_amt ,                            \n" +
                            "      sum(total_amount) as br_amt,             \n" +
                            "      0 as rem_c_b_amt,                        \n" +
                            "      0 as ft_frmoffice_amt                    \n" +
                            "  from                                         \n" +
                            "       fas_receipt_master                      \n" +
                            "  where                                        \n" +
                            "      accounting_unit_id= ?                    \n" +
                            "  and cashbook_month= ?                        \n" +
                            "  and cashbook_year=?                          \n" +
                            "  and created_by_module = 'BR'                 \n" +
                            "  and receipt_status='L'                       \n" +
                            "  and RECEIPT_DATE <=?                           \n" +
                            "                                               \n" +
                            "                                               \n" +
                            "  union all                                    \n" +
                            "                                               \n" +
                            "-- Remittance ( Cash and Bank )                \n" +
                            "  select                                       \n" +
                            "      0 as ob_bal,                             \n" +
                            "      0 as cr_amt ,                            \n" +
                            "      0 as br_amt,                             \n" +
                            "      sum(AMOUNT_REMITTED) as rem_c_b_amt,     \n" +
                            "      0 as ft_frmoffice_amt                    \n" +
                            "  from fas_remittance                          \n" +
                            "  where accounting_unit_id= ?                  \n" +
                            "  and cashbook_month= ?                        \n" +
                            "  and cashbook_year=?                          \n" +
                            "  and status is null                           \n" +
                            "  and remittance_type in ('C', 'B')            \n" +
                            "  and REMITTANCE_DATE <= ?                       \n" +
                            "                                               \n" +
                            "  union all                                    \n" +
                            "                                               \n" +
                            "-- withdrawals                                 \n" +
                            "  select                                       \n" +
                            "     0 as ob_bal,                              \n" +
                            "     0 as cr_amt,                              \n" +
                            "     0 as br_amt ,                             \n" +
                            "     0 as rem_c_b_amt,                         \n" +
                            "     sum(total_amount) as ft_frmoffice_amt     \n" +
                            "  from                                         \n" +
                            "       fas_fund_trf_from_office                \n" +
                            "  where                                        \n" +
                            "      accounting_unit_id = ?                   \n" +
                            "  and cashbook_month= ?                        \n" +
                            "  and cashbook_year= ?                         \n" +
                            "  and transfer_status='L'                      \n" +
                            "  and remittance_type='C'                      \n" +
                            "  and DATE_OF_TRANSFER <= ?                     \n" +
                            "  )                                            \n" +
                            " )                                             \n" +
                            ")                                                ";

                        ps11 = connection.prepareStatement(sql);

                        ps11.setInt(1, AccUnit);
                        ps11.setInt(2, CashBookMonth);
                        ps11.setInt(3, CashBookYear);
                        ps11.setLong(4, BankAcNo);
                        
                        
                        ps11.setInt(5, AccUnit);
                        ps11.setInt(6, CashBookMonth);
                        ps11.setInt(7, CashBookYear);
                        ps11.setDate(8, VerifyDate);

                        ps11.setInt(9, AccUnit);
                        ps11.setInt(10, CashBookMonth);
                        ps11.setInt(11, CashBookYear);
                        ps11.setDate(12, VerifyDate);

                        ps11.setInt(13, AccUnit);
                        ps11.setInt(14, CashBookMonth);
                        ps11.setInt(15, CashBookYear);
                        ps11.setDate(16, VerifyDate);

                        ps11.setInt(17, AccUnit);
                        ps11.setInt(18, CashBookMonth);
                        ps11.setInt(19, CashBookYear);
                        ps11.setDate(20, VerifyDate);

                        rs11 = ps11.executeQuery();
System.out.println(Oprmode+"   sql :: "+sql);
                        xml =
 "<response><flag>success</flag><command>Collection</command>";

                        while (rs11.next()) {
                            xml =
 xml + "<OPEN_BAL>" + rs11.getString("ob_bal") + "</OPEN_BAL>" +
   "<CollAmount_cr>" + rs11.getString("cr_amt") + "</CollAmount_cr>" +
   "<CollAmount_br>" + rs11.getString("br_amt") + "</CollAmount_br>" +
   "<CollAmount_fr>" + rs11.getString("fr_amt") + "</CollAmount_fr>" +
   "<RemAmount>" + rs11.getString("remittance") + "</RemAmount>" +
   "<PayAmount>" + rs11.getString("withdrawals") + "</PayAmount>" +
   "<ClosingBalColl>" + rs11.getString("cb_cashbook") + "</ClosingBalColl>" +
   "<ClosingBalRem>" + rs11.getString("cb_remittance") + "</ClosingBalRem>" +
   "<DiffAmount>" + rs11.getString("final_amt") + "</DiffAmount>";

                        }
                        xml = xml + "</response>";
                        out.write(xml);

                    } catch (Exception e) {
                        System.out.println("Exception in Collection:" + e);
                    }
                    System.out.println(xml);

                }


                /** Operation Account */
                else if (Oprmode.equalsIgnoreCase("OPR")) {

                    System.out.println("This is Operation Details Amount ------------------>>");

                    try {
                         sql =
                            " " + "select                                                      \n" +
                            "  ob_bal,                                                   \n" +
                            "  0 as cr_amt,                                              \n" +
                            "  0 as br_amt,                                              \n" +
                            "  fr_amt,                                                   \n" +
                            "  remittance,                                               \n" +
                            "  withdraw_pay,                                             \n" +
                            "  withdraw_ft,                                              \n" +
                            "  ( withdraw_pay + withdraw_ft )  as withdraw,                " +
                            "  trim(to_char(cb_cashbook,'9999999999999999.99')) as cb_cashbook,       \n" +
                            "  trim(to_char(cb_remittance,'9999999999999999.99')) as cb_remittance,   \n" +
                            "  ( cb_cashbook - cb_remittance ) as final_amt                           \n" +
                            "from                                                                     \n" +
                            "(                                                                        \n" +
                            "                                                                         \n" +
                            " select                                                                  \n" +
                            "  trim(to_char(ob_bal,'9999999999999999.99')) as ob_bal,                 \n" +
                            "  trim(to_char(fr_amt,'9999999999999999.99')) as fr_amt,                 \n" +
                            "  trim(to_char(remittance,'9999999999999999.99')) as remittance,         \n" +
                            "  trim(to_char(withdraw_pay,'9999999999999999.99')) as withdraw_pay ,    \n" +
                            "  trim(to_char(withdraw_ft,'9999999999999999.99')) as withdraw_ft,       \n" +
                            "  (( ob_bal + fr_amt ) - (withdraw_pay + withdraw_ft )) as cb_cashbook,  \n" +
                            "  (( ob_bal + remittance) - (withdraw_pay + withdraw_ft )) as cb_remittance  \n" +
                            "                                                                          \n" +
                            "from                                                                      \n" +
                            "(                                                                         \n" +
                            "                                                                          \n" +
                            "select                                                                    \n" +
                            "   sum(ob_bal) as ob_bal,                                                 \n" +
                            "   sum(fr_amt) as fr_amt,                                                 \n" +
                            "   sum(remittance) as remittance,                                         \n" +
                            "   sum(withdraw_pay) as withdraw_pay,                                     \n" +
                            "   sum(withdraw_ft) as withdraw_ft                                        \n" +
                            "                                                                          \n" +
                            "from                                                                      \n" +
                            "(                                                                         \n" +
                            "                                                                          \n" +
                            "  -- month opening balance from GL      \n" +
                           /* "  select                                \n" +
                            "     month_opening_balance as ob_bal,   \n" +
                            "     0 as fr_amt,                       \n" +
                            "     0 as remittance,                   \n" +
                            "     0 as withdraw_pay,                 \n" +
                            "     0 as withdraw_ft                   \n" +
                            "  from                                  \n" +
                            "    fas_general_ledger                  \n" +
                            "  where                                 \n" +
                            "      accounting_unit_id=?              \n" +
                            "  and month=?                           \n" +
                            "  and year=?                            \n" +
                            "  and account_head_code = 820702        \n" +*/
                            "  select                                \n" +
                            "     PB_BALANCE as ob_bal,   \n" +
                            "     0 as fr_amt,                       \n" +
                            "     0 as remittance,                   \n" +
                            "     0 as withdraw_pay,                 \n" +
                            "     0 as withdraw_ft                   \n" +
                            "  from                                  \n" +
                            "    BRS_BANK_BALANCE_UPDATE             \n" +
                            "  where                                 \n" +
                            "      accounting_unit_id=?              \n" +
                            "  and PS_MONTH=?                        \n" +
                            "  and PS_YEAR=?                         \n" +
                            "  and BANK_AC_NO =?                     \n" +
                            
                            "                                        \n" +
                            "  union all                             \n" +
                            "                                        \n" +
                            "-- fund receipt by office               \n" +
                            "  select                                \n" +
                            "     0 as ob_bal,                       \n" +
                            "     sum(total_amount) as fr_amt,       \n" +
                            "     0 as remittance,                   \n" +
                            "     0 as withdraw_pay,                 \n" +
                            "     0 as withdraw_ft                   \n" +
                            "  from                                  \n" +
                            "    fas_fund_receipt_by_office          \n" +
                            "  where                                 \n" +
                            "      accounting_unit_id=?              \n" +
                            "  and cashbook_month=?                  \n" +
                            "  and cashbook_year= ?                  \n" +
                            "  and receipt_status='L'                \n" +
                            "  and RECEIPT_DATE <= ?                 \n" +
                            "                                          " +
                            "  union all                             \n" +
                            "                                        \n" +
                            "-- Remittance ( fund )                  \n" +
                            "  select                                \n" +
                            "     0 as ob_bal,                       \n" +
                            "     0 as fr_amt,                       \n" +
                            "     sum(AMOUNT_REMITTED) as remittance,\n" +
                            "     0 as withdraw_pay,                 \n" +
                            "     0 as withdraw_ft                   \n" +
                            "  from fas_remittance                   \n" +
                            " where                                  \n" +
                            "      accounting_unit_id = ?            \n" +
                            "  and cashbook_month = ?                \n" +
                            "  and cashbook_year = ?                 \n" +
                            "  and status is null                    \n" +
                            "  and remittance_type in ('F')          \n" +
                            "  and REMITTANCE_DATE <= ?              \n" +
                            "                                        \n" +
                            "  union all                             \n" +
                            "                                        \n" +
                            "  -- withdrawals                        \n" +
                            "  select                                \n" +
                            "     0 as ob_bal,                       \n" +
                            "     0 as fr_amt,                       \n" +
                            "     0 as remittance,                   \n" +
                            "     sum(total_amount) as withdraw_pay, \n" +
                            "     0 as withdraw_ft                   \n" +
                            "  from                                  \n" +
                            "       fas_payment_master               \n" +
                            "  where                                 \n" +
                            "      accounting_unit_id = ?            \n" +
                            "  and cashbook_month = ?                \n" +
                            "  and cashbook_year=  ?                 \n" +
                            "  and PAYMENT_STATUS='L'                \n" +
                            "  and created_by_module in ('BPP', 'BPF')   \n" +
                            "  and PAYMENT_DATE <=?                  \n" +
                            "                                          " +
                            " union all                              \n" +
                            "                                        \n" +
                            " select                                 \n" +
                            "     0 as ob_bal,                       \n" +
                            "     0 as fr_amt,                       \n" +
                            "     0 as remittance,                   \n" +
                            "     0 as withdraw_pay,                 \n" +
                            "     sum(total_amount) as withdraw_ft   \n" +
                            " from                                   \n" +
                            "    fas_fund_trf_from_office            \n" +
                            " where                                  \n" +
                            "      accounting_unit_id= ?             \n" +
                            "  and cashbook_month = ?                \n" +
                            "  and cashbook_year=?                   \n" +
                            "  and remittance_type='U'               \n" +
                            "  and transfer_status='L'               \n" +
                            "  and DATE_OF_TRANSFER <= ?             \n" +
                            "  )                                     \n" +
                            " )                                      \n" +
                            ")                                           ";

                        ps11 = connection.prepareStatement(sql);

                        ps11.setInt(1, AccUnit);
                        ps11.setInt(2, CashBookMonth);
                        ps11.setInt(3, CashBookYear);
                        ps11.setLong(4, BankAcNo);

                        ps11.setInt(5, AccUnit);
                        ps11.setInt(6, CashBookMonth);
                        ps11.setInt(7, CashBookYear);
                        ps11.setDate(8, VerifyDate);

                        ps11.setInt(9, AccUnit);
                        ps11.setInt(10, CashBookMonth);
                        ps11.setInt(11, CashBookYear);
                        ps11.setDate(12, VerifyDate);

                        ps11.setInt(13, AccUnit);
                        ps11.setInt(14, CashBookMonth);
                        ps11.setInt(15, CashBookYear);
                        ps11.setDate(16, VerifyDate);

                        ps11.setInt(17, AccUnit);
                        ps11.setInt(18, CashBookMonth);
                        ps11.setInt(19, CashBookYear);
                        ps11.setDate(20, VerifyDate);

                        rs11 = ps11.executeQuery();
                        System.out.println(Oprmode+"   sql :: "+sql);

                        xml =
 "<response><flag>success</flag><command>Collection</command>";

                        while (rs11.next()) {
                            xml =
 xml + "<OPEN_BAL>" + rs11.getString("ob_bal") + "</OPEN_BAL>" +
   "<CollAmount_cr>" + rs11.getString("cr_amt") + "</CollAmount_cr>" +
   "<CollAmount_br>" + rs11.getString("br_amt") + "</CollAmount_br>" +
   "<CollAmount_fr>" + rs11.getString("fr_amt") + "</CollAmount_fr>" +
   "<RemAmount>" + rs11.getString("remittance") + "</RemAmount>" +
   "<PayAmount>" + rs11.getString("withdraw") + "</PayAmount>" +
   "<ClosingBalColl>" + rs11.getString("cb_cashbook") + "</ClosingBalColl>" +
   "<ClosingBalRem>" + rs11.getString("cb_remittance") + "</ClosingBalRem>" +
   "<DiffAmount>" + rs11.getString("final_amt") + "</DiffAmount>";
                        }
                        xml = xml + "</response>";
                        out.write(xml);

                    } catch (Exception e) {
                        System.out.println("Exception in Collection:" + e);
                    }
                    System.out.println(xml);


                }
                else if (Oprmode.equalsIgnoreCase("OPR-NRDWP-Support")||Oprmode.equalsIgnoreCase("FDW")
                		|| Oprmode.equalsIgnoreCase("SCH") || Oprmode.equalsIgnoreCase("RBI") || Oprmode.equalsIgnoreCase("OPR-NRDWP-Main")
                		|| Oprmode.equalsIgnoreCase("OPR-NRDWP-Calamity") || Oprmode.equalsIgnoreCase("NRDWP-WQM-SP")) {

                    System.out.println("This is Operation Details Amount ------------------>>");

                    try {
                         sql =
                            " " + "select                                                      \n" +
                            "  ob_bal,                                                   \n" +
                            "  0 as cr_amt,                                              \n" +
                            "  0 as br_amt,                                              \n" +
                            "  fr_amt,                                                   \n" +
                            "  remittance,                                               \n" +
                            "  withdraw_pay,                                             \n" +
                            "  withdraw_ft,                                              \n" +
                            "  ( withdraw_pay + withdraw_ft )  as withdraw,                " +
                            "  trim(to_char(cb_cashbook,'9999999999999999.99')) as cb_cashbook,       \n" +
                            "  trim(to_char(cb_remittance,'9999999999999999.99')) as cb_remittance,   \n" +
                            "  ( cb_cashbook - cb_remittance ) as final_amt                           \n" +
                            "from                                                                     \n" +
                            "(                                                                        \n" +
                            "                                                                         \n" +
                            " select                                                                  \n" +
                            "  trim(to_char(ob_bal,'9999999999999999.99')) as ob_bal,                 \n" +
                            "  trim(to_char(fr_amt,'9999999999999999.99')) as fr_amt,                 \n" +
                            "  trim(to_char(remittance,'9999999999999999.99')) as remittance,         \n" +
                            "  trim(to_char(withdraw_pay,'9999999999999999.99')) as withdraw_pay ,    \n" +
                            "  trim(to_char(withdraw_ft,'9999999999999999.99')) as withdraw_ft,       \n" +
                            "  (( ob_bal + fr_amt ) - (withdraw_pay + withdraw_ft )) as cb_cashbook,  \n" +
                            "  (( ob_bal + remittance) - (withdraw_pay + withdraw_ft )) as cb_remittance  \n" +
                            "                                                                          \n" +
                            "from                                                                      \n" +
                            "(                                                                         \n" +
                            "                                                                          \n" +
                            "select                                                                    \n" +
                            "   sum(ob_bal) as ob_bal,                                                 \n" +
                            "   sum(fr_amt) as fr_amt,                                                 \n" +
                            "   sum(remittance) as remittance,                                         \n" +
                            "   sum(withdraw_pay) as withdraw_pay,                                     \n" +
                            "   sum(withdraw_ft) as withdraw_ft                                        \n" +
                            "                                                                          \n" +
                            "from                                                                      \n" +
                            "(                                                                         \n" +
                            "                                                                          \n" +
                            "  -- month opening balance from GL      \n" +
                           /* "  select                                \n" +
                            "     month_opening_balance as ob_bal,   \n" +
                            "     0 as fr_amt,                       \n" +
                            "     0 as remittance,                   \n" +
                            "     0 as withdraw_pay,                 \n" +
                            "     0 as withdraw_ft                   \n" +
                            "  from                                  \n" +
                            "    fas_general_ledger                  \n" +
                            "  where                                 \n" +
                            "      accounting_unit_id=?              \n" +
                            "  and month=?                           \n" +
                            "  and year=?                            \n" +
                            "  and account_head_code = 820702        \n" +*/
                            "  select                                \n" +
                            "     PB_BALANCE as ob_bal,   \n" +
                            "     0 as fr_amt,                       \n" +
                            "     0 as remittance,                   \n" +
                            "     0 as withdraw_pay,                 \n" +
                            "     0 as withdraw_ft                   \n" +
                            "  from                                  \n" +
                            "    BRS_BANK_BALANCE_UPDATE             \n" +
                            "  where                                 \n" +
                            "      accounting_unit_id=?              \n" +
                            "  and PS_MONTH=?                        \n" +
                            "  and PS_YEAR=?                         \n" +
                            "  and BANK_AC_NO =?                     \n" +
                            
                            "                                        \n" +
                            "  union all                             \n" +
                            "                                        \n" +
                            "-- fund receipt by office               \n" +
                            "  select                                \n" +
                            "     0 as ob_bal,                       \n" +
                            "     sum(total_amount) as fr_amt,       \n" +
                            "     0 as remittance,                   \n" +
                            "     0 as withdraw_pay,                 \n" +
                            "     0 as withdraw_ft                   \n" +
                            "  from                                  \n" +
                            "    fas_fund_receipt_by_office          \n" +
                            "  where                                 \n" +
                            "      accounting_unit_id=?              \n" +
                            "  and cashbook_month=?                  \n" +
                            "  and cashbook_year= ?                  \n" +
                            "  and receipt_status='L'                \n" +
                            "  and RECEIPT_DATE <= ?                 \n" +
                            "                                          " +
                            "  union all                             \n" +
                            "                                        \n" +
                            "-- Remittance ( fund )                  \n" +
                            "  select                                \n" +
                            "     0 as ob_bal,                       \n" +
                            "     0 as fr_amt,                       \n" +
                            "     sum(AMOUNT_REMITTED) as remittance,\n" +
                            "     0 as withdraw_pay,                 \n" +
                            "     0 as withdraw_ft                   \n" +
                            "  from fas_remittance                   \n" +
                            " where                                  \n" +
                            "      accounting_unit_id = ?            \n" +
                            "  and cashbook_month = ?                \n" +
                            "  and cashbook_year = ?                 \n" +
                            "  and status is null                    \n" +
                            "  and remittance_type in ('F')          \n" +
                            "  and REMITTANCE_DATE <= ?              \n" +
                            "                                        \n" +
                            "  union all                             \n" +
                            "                                        \n" +
                            "  -- withdrawals                        \n" +
                            "  select                                \n" +
                            "     0 as ob_bal,                       \n" +
                            "     0 as fr_amt,                       \n" +
                            "     0 as remittance,                   \n" +
                            "     sum(total_amount) as withdraw_pay, \n" +
                            "     0 as withdraw_ft                   \n" +
                            "  from                                  \n" +
                            "       fas_payment_master               \n" +
                            "  where                                 \n" +
                            "      accounting_unit_id = ?            \n" +
                            "  and cashbook_month = ?                \n" +
                            "  and cashbook_year=  ?                 \n" +
                            "  and PAYMENT_STATUS='L'                \n" +
                            "  and created_by_module in ('BPP', 'BPF')   \n" +
                            "  and PAYMENT_DATE <=?                  \n" +
                            "                                          " +
                            " union all                              \n" +
                            "                                        \n" +
                            " select                                 \n" +
                            "     0 as ob_bal,                       \n" +
                            "     0 as fr_amt,                       \n" +
                            "     0 as remittance,                   \n" +
                            "     0 as withdraw_pay,                 \n" +
                            "     sum(total_amount) as withdraw_ft   \n" +
                            " from                                   \n" +
                            "    fas_fund_trf_from_office            \n" +
                            " where                                  \n" +
                            "      accounting_unit_id= ?             \n" +
                            "  and cashbook_month = ?                \n" +
                            "  and cashbook_year=?                   \n" +
                            "  and remittance_type='U'               \n" +
                            "  and transfer_status='L'               \n" +
                            "  and DATE_OF_TRANSFER <= ?             \n" +
                            "  )                                     \n" +
                            " )                                      \n" +
                            ")                                           ";

                        ps11 = connection.prepareStatement(sql);

                        ps11.setInt(1, AccUnit);
                        ps11.setInt(2, CashBookMonth);
                        ps11.setInt(3, CashBookYear);
                        ps11.setLong(4, BankAcNo);

                        ps11.setInt(5, AccUnit);
                        ps11.setInt(6, CashBookMonth);
                        ps11.setInt(7, CashBookYear);
                        ps11.setDate(8, VerifyDate);

                        ps11.setInt(9, AccUnit);
                        ps11.setInt(10, CashBookMonth);
                        ps11.setInt(11, CashBookYear);
                        ps11.setDate(12, VerifyDate);

                        ps11.setInt(13, AccUnit);
                        ps11.setInt(14, CashBookMonth);
                        ps11.setInt(15, CashBookYear);
                        ps11.setDate(16, VerifyDate);

                        ps11.setInt(17, AccUnit);
                        ps11.setInt(18, CashBookMonth);
                        ps11.setInt(19, CashBookYear);
                        ps11.setDate(20, VerifyDate);

                        rs11 = ps11.executeQuery();
                        System.out.println(Oprmode+"   sql :: "+sql);

                        xml =
 "<response><flag>success</flag><command>Collection</command>";

                        while (rs11.next()) {
                            xml =
 xml + "<OPEN_BAL>" + rs11.getString("ob_bal") + "</OPEN_BAL>" +
   "<CollAmount_cr>" + rs11.getString("cr_amt") + "</CollAmount_cr>" +
   "<CollAmount_br>" + rs11.getString("br_amt") + "</CollAmount_br>" +
   "<CollAmount_fr>" + rs11.getString("fr_amt") + "</CollAmount_fr>" +
   "<RemAmount>" + rs11.getString("remittance") + "</RemAmount>" +
   "<PayAmount>" + rs11.getString("withdraw") + "</PayAmount>" +
   "<ClosingBalColl>" + rs11.getString("cb_cashbook") + "</ClosingBalColl>" +
   "<ClosingBalRem>" + rs11.getString("cb_remittance") + "</ClosingBalRem>" +
   "<DiffAmount>" + rs11.getString("final_amt") + "</DiffAmount>";
                        }
                        xml = xml + "</response>";
                        out.write(xml);

                    } catch (Exception e) {
                        System.out.println("Exception in Collection:" + e);
                    }
                    System.out.println(xml);


                }

            } catch (Exception e) {
                System.out.println("Finding Bank Balance Monitoring failed due to exception" +
                                   e);
                xml = xml + "<flag>failure</flag>";
            }


        }

    }

}
