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

public class SubLedgerMasterServlet1 extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        Connection con = null;
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
            //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

        }


        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;
        PreparedStatement ps4 = null;
        PreparedStatement ps5 = null;
        PreparedStatement ps6 = null;
        PreparedStatement ps7 = null;
        PreparedStatement ps8 = null;
        PreparedStatement ps9 = null;
        ResultSet results = null;
        ResultSet rs = null;
        ResultSet rs3 = null;
        ResultSet rs4 = null;
        ResultSet rs5 = null;
        ResultSet rs6 = null;
        ResultSet rs7 = null;
        ResultSet rs8 = null;
        ResultSet rs9 = null;

        String Account_unit_Code = request.getParameter("cmbAcc_UnitCode");
        String Office_Code = request.getParameter("cmbOffice_code");
        String CashBook_Year = request.getParameter("txtCB_Year");
        String CashBook_Month = request.getParameter("txtCB_Month");
        String SlTypeCode = request.getParameter("cmbMas_SL_type");

        System.out.println("Account_Unit_Code is:" + Account_unit_Code);
        System.out.println("Office_Code is:" + Office_Code);
        System.out.println("CashBook_Year is:" + CashBook_Year);
        System.out.println("CashBook Month is:" + CashBook_Month);
        System.out.println("SlTypeCode is:" + SlTypeCode);

        int AccountUnitCode = 0;
        int OfficeCode = 0;
        int CashBookYear = 0;
        int CashBookMonth = 0;
        int cmbMas_SL_type = 0;
        double monthclosing = 0;
        double monthclosing1 = 0;
        double month_ob = 0;
        double CRAmount = 0;
        double DRAmount1 = 0;
        double balance = 0;
        String MONTH_CLOSING_BAL_DR_CR_IND = "";
        String month_opening_bal_dr_cr_ind = "";
        java.sql.Date ReceiptDate = null;
        java.sql.Date VoucherDate = null;
        //Getting Queries declartion variable
        int accounthead = 0;
        String ind = "";
        int recpno = 0;
        double amt = 0;
        String doctype = "";
        int type1 = 0;
        Date date1 = null;
        int scode = 0;
        String ind1 = "";
        int recpno1 = 0;
        double amt1 = 0;
        String doctype1 = "";
        int type2 = 0;
        Date date2 = null;
        int scode1 = 0;
        double cramt = 0;
        double dramt = 0;

        try {
            AccountUnitCode = Integer.parseInt(Account_unit_Code);
            OfficeCode = Integer.parseInt(Office_Code);
            CashBookYear = Integer.parseInt(CashBook_Year);
            CashBookMonth = Integer.parseInt(CashBook_Month);
            cmbMas_SL_type = Integer.parseInt(SlTypeCode);

            System.out.println("Account_Unit_Code After is:" +
                               AccountUnitCode);
            System.out.println("Office_Code After is:" + OfficeCode);
            System.out.println("CashBook_Year After is:" + CashBookYear);
            System.out.println("CashBook Month After is:" + CashBookMonth);
            System.out.println("cmbMas_SL_type is:" + cmbMas_SL_type);
            ;

        } catch (Exception e) {
            System.out.println("Exception in Converting Integer:" + e);
        }
        //This Two Variables for calculateting cashbookmonth and year
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
        String finyear = "";
        double cur_yr_crbal = 0;
        double cur_yr_drbal = 0;
        double Month_OB_Bal = 0;
        String Month_OB_Cr_Dr_ind = "";
        int achead = 0;
        int slcode = 0;
        double Upto_Debit_Bal = 0;
        double Upto_Credit_Bal = 0;
        int payvoucherno = 0;
        String billno = "";
        String agreementno = "";
        String particulars = "";
        java.sql.Date billdate = null;
        java.sql.Date payvoucherdate = null;
        try {
            con.setAutoCommit(false);
            String sqlquery =
                "select account_head_code,financial_year,current_year_debit_balance,current_year_credit_balance, " +
                " current_month_debit,current_month_credit,month_closing_balance,month_closing_bal_dr_cr_ind,SUB_LEDGER_CODE from fas_sub_ledger_master " +
                " where accounting_unit_id=? and accounting_for_office_id=? and year=? and month=? and sub_ledger_type_code=?";
            ps = con.prepareStatement(sqlquery);
            ps.setInt(1, AccountUnitCode);
            ps.setInt(2, OfficeCode);
            ps.setInt(3, cashbookyear1);
            ps.setInt(4, cashbookmonth1);
            ps.setInt(5, cmbMas_SL_type);
            results = ps.executeQuery();
            while (results.next()) {
                finyear = results.getString("FINANCIAL_YEAR");
                cur_yr_crbal =
                        results.getDouble("CURRENT_MONTH_CREDIT") + results.getDouble("CURRENT_YEAR_CREDIT_BALANCE");
                cur_yr_drbal =
                        results.getDouble("CURRENT_MONTH_DEBIT") + results.getDouble("CURRENT_YEAR_DEBIT_BALANCE");
                Month_OB_Bal = results.getDouble("MONTH_CLOSING_BALANCE");
                Month_OB_Cr_Dr_ind =
                        results.getString("MONTH_CLOSING_BAL_DR_CR_IND");
                System.out.println("Finyear is:" + finyear);
                achead = results.getInt("account_head_code");
                slcode = results.getInt("sub_ledger_code");


                String sqlquery2 =
                    "select account_head_code,sub_ledger_type_code,sub_ledger_code from fas_sub_ledger_master_tmp where " +
                    " accounting_unit_id=? and accounting_for_office_id=? and year=? and month=? and account_head_code=? and " +
                    "sub_ledger_type_code=? and sub_ledger_code=?";
                ps1 = con.prepareStatement(sqlquery2);
                ps1.setInt(1, AccountUnitCode);
                ps1.setInt(2, OfficeCode);
                ps1.setInt(3, CashBookYear);
                ps1.setInt(4, CashBookMonth);
                ps1.setInt(5, achead);
                ps1.setInt(6, cmbMas_SL_type);
                ps1.setInt(7, slcode);
                rs = ps1.executeQuery();
                if (rs.next()) {
                    System.out.println("Inner If");
                    String sqlquery3 =
                        "update fas_sub_ledger_master_tmp set MONTH_OPENING_BALANCE=?,MONTH_OPENING_BAL_DR_CR_IND=?,CURRENT_YEAR_DEBIT_BALANCE=?, " +
                        " CURRENT_YEAR_CREDIT_BALANCE=? where accounting_unit_id=? and accounting_for_office_id=? and year=? and month=? and account_head_code=? " +
                        " and sub_ledger_type_code=? and sub_ledger_code=?";
                    ps3 = con.prepareStatement(sqlquery3);
                    ps3.setDouble(1, Month_OB_Bal);
                    ps3.setString(2, Month_OB_Cr_Dr_ind);
                    ps3.setDouble(3, cur_yr_drbal);
                    ps3.setDouble(4, cur_yr_crbal);
                    ps3.setInt(5, AccountUnitCode);
                    ps3.setInt(6, OfficeCode);
                    ps3.setInt(7, CashBookYear);
                    ps3.setInt(8, CashBookMonth);
                    ps3.setInt(9, achead);
                    ps3.setInt(10, cmbMas_SL_type);
                    ps3.setInt(11, slcode);
                    ps3.executeUpdate();
                    ps3.close();

                } else {
                    System.out.println("Inner Else");
                    String NFinYear = "";
                    if ((CashBookMonth >= 1 && CashBookMonth <= 3)) {
                        NFinYear = (CashBookYear - 1) + "-" + (CashBookYear);
                    } else {
                        NFinYear = (CashBookYear) + "-" + (CashBookYear + 1);
                    }

                    String sqlquery1 =
                        "insert into fas_sub_ledger_master_tmp(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR,YEAR,MONTH, " +
                        "ACCOUNT_HEAD_CODE,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,PROJECT_ID,MONTH_OPENING_BALANCE, " +
                        "MONTH_OPENING_BAL_DR_CR_IND,CURRENT_MONTH_DEBIT,CURRENT_MONTH_CREDIT,MONTH_CLOSING_BALANCE, " +
                        "MONTH_CLOSING_BAL_DR_CR_IND,UPTO_DEBIT_BALANCE,UPTO_CREDIT_BALANCE,CURRENT_YEAR_DEBIT_BALANCE, " +
                        "CURRENT_YEAR_CREDIT_BALANCE,DR_UPDATE_LAST_DATE,CR_UPDATE_LAST_DATE,UPDATED_BY_USER_ID,UPDATED_DATE ) values" +
                        " (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    ps2 = con.prepareStatement(sqlquery1);
                    ps2.setInt(1, AccountUnitCode);
                    ps2.setInt(2, OfficeCode);
                    ps2.setString(3, NFinYear);
                    ps2.setInt(4, CashBookYear);
                    ps2.setInt(5, CashBookMonth);
                    ps2.setInt(6, achead);
                    ps2.setInt(7, cmbMas_SL_type);
                    ps2.setInt(8, slcode);
                    ps2.setInt(9, 0);
                    ps2.setDouble(10, Month_OB_Bal);
                    ps2.setString(11, Month_OB_Cr_Dr_ind);
                    ps2.setInt(12, 0);
                    ps2.setInt(13, 0);
                    ps2.setInt(14, 0);
                    ps2.setString(15, null);
                    ps2.setDouble(16, 0);
                    ps2.setDouble(17, 0);
                    ps2.setDouble(18, cur_yr_drbal);
                    ps2.setDouble(19, cur_yr_crbal);
                    ps2.setDate(20, null);
                    ps2.setDate(21, null);
                    ps2.setString(22, "x");
                    long l = System.currentTimeMillis();
                    Timestamp ts = new Timestamp(l);
                    ps2.setTimestamp(23, ts);
                    ps2.executeUpdate();
                    ps2.close();
                }
                ps1.close();
                rs.close();
            } //End While(results.next())

            //delete from tmp where accu,off,year=cashbookyear month,subledgertype=form
            String sqlquery5 =
                "delete from fas_sub_ledger_trn_tmp where accounting_unit_id=? and accounting_for_office_id=? " +
                " and year=? and month=? and sub_ledger_type_code=?";
            ps2 = con.prepareStatement(sqlquery5);
            ps2.setInt(1, AccountUnitCode);
            ps2.setInt(2, OfficeCode);
            ps2.setInt(3, CashBookYear);
            ps2.setInt(4, CashBookMonth);
            ps2.setInt(5, cmbMas_SL_type);
            ps2.executeUpdate();
            ps2.close();


            //Query for Selecting Account Head Code for the particular accountingunitid,cashbook_year,
            //and cashbook_month,accounting_for_office_id,sub_ledger_type_code in the tables fas_receipt_transaction
            //fas_payment_transaction and fas_journal_transaction(Example for Sub_ledger_type Code is Contractor(11-Code))
            //con.setAutoCommit(false);
            String sql =
                "select distinct acheadcode from ( " + "select account_head_code as acheadcode from fas_receipt_transaction where cr_dr_indicator='CR' " +
                "and cashbook_year=? and cashbook_month=? and accounting_unit_id=? and accounting_for_office_id=? " +
                "and sub_ledger_type_code=? " + " union " +
                "select account_head_code as acheadcode from fas_journal_transaction where cr_dr_indicator='CR'" +
                "and cashbook_year=? and cashbook_month=? and accounting_unit_id=? and accounting_for_office_id=? " +
                "and sub_ledger_type_code=? " + " union " +
                "select account_head_code as acheadcode from fas_payment_transaction where cr_dr_indicator='DR'" +
                "and cashbook_year=? and cashbook_month=? and accounting_unit_id=? and accounting_for_office_id=? " +
                "and sub_ledger_type_code=? " + " union " +
                "select account_head_code as acheadcode from fas_journal_transaction where cr_dr_indicator='DR' " +
                "and cashbook_year=? and cashbook_month=? and accounting_unit_id=? and accounting_for_office_id=? " +
                "and sub_ledger_type_code=? " + ")";
            ps = con.prepareStatement(sql);
            ps.setInt(1, CashBookYear);
            ps.setInt(2, CashBookMonth);
            ps.setInt(3, AccountUnitCode);
            ps.setInt(4, OfficeCode);
            ps.setInt(5, cmbMas_SL_type);
            ps.setInt(6, CashBookYear);
            ps.setInt(7, CashBookMonth);
            ps.setInt(8, AccountUnitCode);
            ps.setInt(9, OfficeCode);
            ps.setInt(10, cmbMas_SL_type);
            ps.setInt(11, CashBookYear);
            ps.setInt(12, CashBookMonth);
            ps.setInt(13, AccountUnitCode);
            ps.setInt(14, OfficeCode);
            ps.setInt(15, cmbMas_SL_type);
            ps.setInt(16, CashBookYear);
            ps.setInt(17, CashBookMonth);
            ps.setInt(18, AccountUnitCode);
            ps.setInt(19, OfficeCode);
            ps.setInt(20, cmbMas_SL_type);
            results = ps.executeQuery();

            while (results.next()) //Main While
            {
                System.out.println("Inner While");
                accounthead = results.getInt("acheadcode");
                System.out.println("Inner Main While Value for Account Head is:" +
                                   accounthead);

                String sql1 =
                    "select ind,recpno,amt,doctype,type1,date1,scode,billno,billdate,agreementno,particulars from (" +
                    "select a.cr_dr_indicator as ind,a.receipt_no as recpno,a.amount as amt,'R' as doctype,null as type1, " +
                    "b.receipt_date as date1,a.sub_ledger_code as scode,'' as billno,null as billdate,'' as agreementno,A.PARTICULARS as particulars from fas_receipt_transaction a, " +
                    "fas_receipt_master b where a.accounting_unit_id=b.accounting_unit_id and " +
                    "a.cashbook_year=b.cashbook_year and a.cashbook_month=b.cashbook_month and " +
                    "a.receipt_no=b.receipt_no and a.accounting_for_office_id=b.ACCOUNTING_FOR_OFFICE_ID " +
                    "and a.accounting_unit_id=? and a.accounting_for_office_id=? and a.cashbook_year=? " +
                    "and a.cashbook_month=? and a.cr_dr_indicator='CR' and a.account_head_code=? and " +
                    "a.sub_ledger_type_code=? " + " union " +
                    "select a.cr_dr_indicator as ind,a.voucher_no as recpno,a.amount as amt,'J' as doctype, " +
                    "b.journal_type_code as type1,b.voucher_date as date1,a.sub_ledger_code as scode,a.bill_no as billno, " +
                    "a.bill_date as billdate, a.agreement_no as agreementno,A.PARTICULARS as particulars from " +
                    "fas_journal_transaction a,fas_journal_master b where a.accounting_unit_id=b.accounting_unit_id " +
                    "and a.cashbook_year=b.cashbook_year and a.cashbook_month=b.cashbook_month and " +
                    "a.voucher_no=b.voucher_no and a.accounting_for_office_id=b.ACCOUNTING_FOR_OFFICE_ID " +
                    "and a.accounting_unit_id=? and a.accounting_for_office_id=? " +
                    "and a.cashbook_year=? and a.cashbook_month=? and a.cr_dr_indicator='CR' " +
                    "and a.account_head_code=? and a.sub_ledger_type_code=? " +
                    ") ";

                ps1 = con.prepareStatement(sql1);
                ps1.setInt(1, AccountUnitCode);
                ps1.setInt(2, OfficeCode);
                ps1.setInt(3, CashBookYear);
                ps1.setInt(4, CashBookMonth);
                ps1.setInt(5, accounthead);
                ps1.setInt(6, cmbMas_SL_type);
                ps1.setInt(7, AccountUnitCode);
                ps1.setInt(8, OfficeCode);
                ps1.setInt(9, CashBookYear);
                ps1.setInt(10, CashBookMonth);
                ps1.setInt(11, accounthead);
                ps1.setInt(12, cmbMas_SL_type);
                rs = ps1.executeQuery();
                while (rs.next()) //Second While Starting
                {
                    ind = rs.getString("ind");
                    recpno = rs.getInt("recpno");
                    amt = rs.getDouble("amt");
                    doctype = rs.getString("doctype");
                    type1 = rs.getInt("type1");
                    date1 = rs.getDate("date1");
                    scode = rs.getInt("scode");
                    billno = rs.getString("billno");
                    billdate = rs.getDate("billdate");
                    agreementno = rs.getString("agreementno");
                    particulars = rs.getString("particulars");
                    String NFinYear = "";
                    if ((CashBookMonth >= 1 && CashBookMonth <= 3)) {
                        NFinYear = (CashBookYear - 1) + "-" + (CashBookYear);
                    } else {
                        NFinYear = (CashBookYear) + "-" + (CashBookYear + 1);
                    }
                    /*System.out.println("Inner Second While indi is:"+ind);
                                                System.out.println("Inner Second While recepno is:"+recpno);
                                                System.out.println("Inner Second While amt is:"+amt);
                                                System.out.println("Inner Second While doctype is:"+doctype);
                                                System.out.println("Inner Second While type1 is:"+type1);
                                                System.out.println("Inner Second While date1 is:"+date1);
                                                System.out.println("Inner Second While scode is:"+scode);*/

                    String sql11 =
                        "insert into fas_sub_ledger_trn_tmp(accounting_unit_id,accounting_for_office_id,year," +
                        " month,account_head_code,sub_ledger_type_code,sub_ledger_code,CREDIT_AMOUNT,CR_VOUCHER_TYPE,CR_VOUCHER_NO," +
                        " CR_VOUCHER_DATE,CR_JOURNAL_TYPE_CODE,FINANCIAL_YEAR,BILL_NO,BILL_DATE,AGREEMENT_NO,PARTICULARS) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    ps3 = con.prepareStatement(sql11);
                    ps3.setInt(1, AccountUnitCode);
                    ps3.setInt(2, OfficeCode);
                    ps3.setInt(3, CashBookYear);
                    ps3.setInt(4, CashBookMonth);
                    ps3.setInt(5, accounthead);
                    ps3.setInt(6, cmbMas_SL_type);
                    ps3.setInt(7, scode);
                    //ps3.setString(8,ind);
                    ps3.setDouble(8, amt);
                    ps3.setString(9, doctype);
                    ps3.setInt(10, recpno);
                    ps3.setDate(11, date1);
                    ps3.setInt(12, type1);
                    ps3.setString(13, NFinYear);
                    ps3.setString(14, billno);
                    ps3.setDate(15, billdate);
                    ps3.setString(16, agreementno);
                    ps3.setString(17, particulars);
                    int ii = ps3.executeUpdate();
                    System.out.println("First update is:" + ii);


                } //End While For Second
                ps3.close();
                rs.close();
                String sql2 =
                    "select ind1,recpno1,amt1,doctype1,type2,date2,scode1,payvoucherno,payvoucherdate from ( " +
                    "select a.cr_dr_indicator as ind1,a.voucher_no as recpno1,a.amount as amt1,'P' as doctype1,null as type2, " +
                    "b.payment_date as date2,a.sub_ledger_code as scode1,a.PAYABLE_VOUCHER_NO as payvoucherno,a.PAYABLE_VOUCHER_DATE as payvoucherdate " +
                    " from fas_payment_transaction a, " +
                    "fas_payment_master b where a.accounting_unit_id=b.accounting_unit_id and a.cashbook_year=b.cashbook_year and " +
                    " a.cashbook_month=b.cashbook_month and a.voucher_no=b.voucher_no and a.accounting_for_office_id=b.ACCOUNTING_FOR_OFFICE_ID and " +
                    " a.accounting_unit_id=? and a.accounting_for_office_id=? and a.cashbook_year=? and a.cashbook_month=? and " +
                    "a.account_head_code=? and a.sub_ledger_type_code=? " +
                    ")";
                /*
                                        " union " +
                                        "select a.cr_dr_indicator as ind1,a.voucher_no as recpno1,a.amount as amt1,'J' as doctype1, " +
                                        "b.journal_type_code as type2,b.voucher_date as date2,a.sub_ledger_code as scode1 from " +
                                        "fas_journal_transaction a,fas_journal_master b where a.accounting_unit_id=b.accounting_unit_id " +
                                        "and a.cashbook_year=b.cashbook_year and a.cashbook_month=b.cashbook_month and " +
                                        "a.voucher_no=b.voucher_no and a.accounting_for_office_id=b.ACCOUNTING_FOR_OFFICE_ID " +
                                        "and a.accounting_unit_id=? and a.accounting_for_office_id=? and a.cashbook_year=? " +
                                        "and a.cashbook_month=? and a.cr_dr_indicator='DR' and a.account_head_code=? " +
                                        "and a.sub_ledger_type_code=? " +
                                        ") ";*/

                ps2 = con.prepareStatement(sql2);
                ps2.setInt(1, AccountUnitCode);
                ps2.setInt(2, OfficeCode);
                ps2.setInt(3, CashBookYear);
                ps2.setInt(4, CashBookMonth);
                ps2.setInt(5, accounthead);
                ps2.setInt(6, cmbMas_SL_type);
                /*ps2.setInt(7,AccountUnitCode);
                                        ps2.setInt(8,OfficeCode);
                                        ps2.setInt(9,CashBookYear);
                                        ps2.setInt(10,CashBookMonth);
                                        ps2.setInt(11,accounthead);
                                        ps2.setInt(12,cmbMas_SL_type);*/
                rs3 = ps2.executeQuery();
                System.out.println("hai" + rs3);
                while (rs3.next()) //Third While Starting
                {
                    System.out.println("inside while rs3");
                    ind1 = rs3.getString("ind1");
                    recpno1 = rs3.getInt("recpno1");
                    amt1 = rs3.getDouble("amt1");
                    doctype1 = rs3.getString("doctype1");
                    type2 = rs3.getInt("type2");
                    date2 = rs3.getDate("date2");
                    scode1 = rs3.getInt("scode1");
                    payvoucherno = rs3.getInt("payvoucherno");
                    payvoucherdate = rs3.getDate("payvoucherdate");
                    /*System.out.println("Inner Third While indi is:"+ind1);
                                            System.out.println("Inner Third While recepno is:"+recpno1);
                                            System.out.println("Inner Third While amt is:"+amt1);
                                            System.out.println("Inner Third While doctype is:"+doctype1);
                                            System.out.println("Inner Third While type1 is:"+type2);
                                            System.out.println("Inner Third While date1 is:"+date2);
                                            System.out.println("Inner Third While scode is:"+scode1);*/

                    String sql21 =
                        "update fas_sub_ledger_trn_tmp set debit_amount=?,dr_voucher_type=?,dr_voucher_no=?,dr_voucher_date=? " +
                        " ,dr_journal_type_code=? where accounting_unit_id=? and accounting_for_office_id=? and financial_year=? and" +
                        " year=? and month=? and account_head_code=? and sub_ledger_type_code=? and sub_ledger_code=? and cr_voucher_no=? and" +
                        " cr_voucher_date=?";
                    String NFinYear = "";
                    if ((CashBookMonth >= 1 && CashBookMonth <= 3)) {
                        NFinYear = (CashBookYear - 1) + "-" + (CashBookYear);
                    } else {
                        NFinYear = (CashBookYear) + "-" + (CashBookYear + 1);
                    }
                    /*String sql21="insert into fas_sub_ledger_trn_tmp(accounting_unit_id,accounting_for_office_id, " +
                                            " year,month,account_head_code,sub_ledger_type_code,sub_ledger_code,DEBIT_AMOUNT, " +
                                            "DR_VOUCHER_TYPE,DR_VOUCHER_NO,DR_VOUCHER_DATE,DR_JOURNAL_TYPE_CODE) values(?,?,?,?,?,?,?,?,?,?,?,?)";*/
                    ps4 = con.prepareStatement(sql21);

                    ps4.setInt(6, AccountUnitCode);
                    ps4.setInt(7, OfficeCode);
                    ps4.setString(8, NFinYear);
                    ps4.setInt(9, CashBookYear);
                    ps4.setInt(10, CashBookMonth);
                    ps4.setInt(11, accounthead);
                    ps4.setInt(12, cmbMas_SL_type);
                    ps4.setInt(13, scode1);
                    ps4.setInt(14, payvoucherno);
                    ps4.setDate(15, payvoucherdate);
                    //ps4.setString(8,ind1);
                    ps4.setDouble(1, amt1);
                    ps4.setString(2, doctype1);
                    ps4.setInt(3, recpno1);
                    ps4.setDate(4, date2);
                    ps4.setInt(5, type2);
                    int ii = ps4.executeUpdate();
                    System.out.println("Second Update is:" + ii);
                    ps4.close();

                } //End While For Third
                System.out.println("b3 sql3");

                rs3.close();
                System.out.println("b31 sql3");
                String sql3 =
                    "select sum(credit_amount) as amount1,sub_ledger_code,max(cr_voucher_date) as vou_date from fas_sub_ledger_trn_tmp where  " +
                    " account_head_code=? and accounting_for_office_id=? and year=? and month=? and sub_ledger_type_code=? " +
                    "and accounting_unit_id=? group by sub_ledger_code,account_head_code";
                ps5 = con.prepareStatement(sql3);
                //ps5.setString(1,"CR");
                ps5.setInt(1, accounthead);
                ps5.setInt(2, OfficeCode);
                ps5.setInt(3, CashBookYear);
                ps5.setInt(4, CashBookMonth);
                ps5.setInt(5, cmbMas_SL_type);
                ps5.setInt(6, AccountUnitCode);

                rs4 = ps5.executeQuery();
                while (rs4.next()) { //Start While(rs4.next())
                    System.out.println("rs4:" + rs4.getInt("sub_ledger_code"));
                    //CRAmount=CRAmount+rs4.getDouble("amount1");
                    CRAmount = rs4.getDouble("amount1");
                    int scode4 = rs4.getInt("sub_ledger_code");
                    String sql6 =
                        "update fas_sub_ledger_master_tmp set current_month_credit=?,CR_UPDATE_LAST_DATE=? where accounting_unit_id=? " +
                        "and accounting_for_office_id=? and year=? and month=? and sub_ledger_type_code=? " +
                        "and account_head_code=? and sub_ledger_code=?";
                    ps7 = con.prepareStatement(sql6);
                    ps7.setDouble(1, CRAmount);
                    ps7.setDate(2, rs4.getDate("vou_date"));
                    ps7.setInt(3, AccountUnitCode);
                    ps7.setInt(4, OfficeCode);
                    ps7.setInt(5, CashBookYear);
                    ps7.setInt(6, CashBookMonth);
                    ps7.setInt(7, cmbMas_SL_type);
                    ps7.setInt(8, accounthead);
                    ps7.setInt(9, scode4);
                    int ij = ps7.executeUpdate();
                    System.out.println("fas master update is..1:" + ij);

                    System.out.println("amt:" + CRAmount);
                } //End While(rs4.next())
                System.out.println("value is:" + CRAmount);
                //CRAmount=0;
                ps7.close();
                rs4.close();
                String sql4 =
                    "select sum(debit_amount) as amount2,sub_ledger_code,max(dr_voucher_date) as vou_date from fas_sub_ledger_trn_tmp where  " +
                    "account_head_code=? and accounting_for_office_id=? and year=? and month=? and sub_ledger_type_code=? " +
                    "and accounting_unit_id=? group by sub_ledger_code,account_head_code";
                ps6 = con.prepareStatement(sql4);
                //ps6.setString(1,"DR");
                ps6.setInt(1, accounthead);
                ps6.setInt(2, OfficeCode);
                ps6.setInt(3, CashBookYear);
                ps6.setInt(4, CashBookMonth);
                ps6.setInt(5, cmbMas_SL_type);
                ps6.setInt(6, AccountUnitCode);

                rs5 = ps6.executeQuery();
                while (rs5.next()) { //Start While(rs5.next())
                    System.out.println("rs5:" + rs5.getInt("sub_ledger_code"));
                    //DRAmount1=DRAmount1+rs5.getDouble("amount2");
                    DRAmount1 = rs5.getDouble("amount2");
                    int scode5 = rs5.getInt("sub_ledger_code");
                    String sql7 =
                        "update fas_sub_ledger_master_tmp set current_month_debit=?,DR_UPDATE_LAST_DATE=? where accounting_unit_id=? " +
                        "and accounting_for_office_id=? and year=? and month=? and sub_ledger_type_code=? " +
                        "and account_head_code=? and sub_ledger_code=?";
                    ps8 = con.prepareStatement(sql7);
                    ps8.setDouble(1, DRAmount1);
                    ps8.setDate(2, rs5.getDate("vou_date"));
                    ps8.setInt(3, AccountUnitCode);
                    ps8.setInt(4, OfficeCode);
                    ps8.setInt(5, CashBookYear);
                    ps8.setInt(6, CashBookMonth);
                    ps8.setInt(7, cmbMas_SL_type);
                    ps8.setInt(8, accounthead);
                    ps8.setInt(9, scode5);
                    int ik = ps8.executeUpdate();
                    ps8.close();
                    System.out.println("fas master second update is...2:" +
                                       ik);
                    System.out.println("amt dr :" + DRAmount1);
                } //End While(rs5.next())
                System.out.println("value in DRAmount :" + DRAmount1);
                //DRAmount1=0;

                rs5.close();


                String sql8 =
                    "select current_month_credit,current_month_debit,sub_ledger_code from fas_sub_ledger_master_tmp where " +
                    "accounting_unit_id=? and accounting_for_office_id=? and year=? and month=? and account_head_code=? " +
                    "and sub_ledger_type_code=?  ";
                ps9 = con.prepareStatement(sql8);
                ps9.setInt(1, AccountUnitCode);
                ps9.setInt(2, OfficeCode);
                ps9.setInt(3, CashBookYear);
                ps9.setInt(4, CashBookMonth);
                ps9.setInt(5, accounthead);
                ps9.setInt(6, cmbMas_SL_type);
                rs6 = ps9.executeQuery();
                while (rs6.next()) //Start while(rs6.next())
                {
                    cramt = rs6.getDouble("current_month_credit");
                    dramt = rs6.getDouble("current_month_debit");
                    System.out.println("sql 8 is cr:" + cramt);
                    System.out.println("sql 8 is dr:" + dramt);

                    String sqlquery10 =
                        "select MONTH_OPENING_BAL_DR_CR_IND,MONTH_OPENING_BALANCE from fas_sub_ledger_master_tmp where " +
                        "accounting_unit_id=? and accounting_for_office_id=? and year=? and month=? and account_head_code=? " +
                        "and sub_ledger_type_code=? and sub_ledger_code=? ";
                    ps7 = con.prepareStatement(sqlquery10);
                    ps7.setInt(1, AccountUnitCode);
                    ps7.setInt(2, OfficeCode);
                    ps7.setInt(3, CashBookYear);
                    ps7.setInt(4, CashBookMonth);
                    ps7.setInt(5, accounthead);
                    ps7.setInt(6, cmbMas_SL_type);
                    ps7.setInt(7, rs6.getInt("sub_ledger_code"));
                    rs5 = ps7.executeQuery();
                    if (rs5.next()) {
                        if ((rs5.getString("MONTH_OPENING_BAL_DR_CR_IND").trim()).equalsIgnoreCase("CR")) {
                            Upto_Credit_Bal =
                                    rs5.getDouble("MONTH_OPENING_BALANCE") +
                                    cramt;
                            balance =
                                    -(dramt - (cramt + rs5.getDouble("MONTH_OPENING_BALANCE")));
                        } else {
                            Upto_Debit_Bal =
                                    rs5.getDouble("MONTH_OPENING_BALANCE") +
                                    dramt;
                            balance =
                                    -((rs5.getDouble("MONTH_OPENING_BALANCE") +
                                       dramt) - cramt);
                        }

                        if (balance >= 0) {
                            MONTH_CLOSING_BAL_DR_CR_IND = "CR";
                        } else {
                            MONTH_CLOSING_BAL_DR_CR_IND = "DR";
                        }
                    }
                    String sqlquery9 =
                        "update fas_sub_ledger_master_tmp set UPTO_DEBIT_BALANCE=?,UPTO_CREDIT_BALANCE=?, " +
                        " MONTH_CLOSING_BALANCE=?,MONTH_CLOSING_BAL_DR_CR_IND=? where accounting_unit_id=? and " +
                        "accounting_for_office_id=? and year=? and month=? and account_head_code=? and sub_ledger_type_code=? " +
                        "and sub_ledger_code=?";
                    ps8 = con.prepareStatement(sqlquery9);
                    ps8.setDouble(1, Upto_Debit_Bal);
                    ps8.setDouble(2, Upto_Credit_Bal);
                    ps8.setDouble(3, Math.abs(balance));
                    ps8.setString(4, MONTH_CLOSING_BAL_DR_CR_IND);
                    ps8.setInt(5, AccountUnitCode);
                    ps8.setInt(6, OfficeCode);
                    ps8.setInt(7, CashBookYear);
                    ps8.setInt(8, CashBookMonth);
                    ps8.setInt(9, accounthead);
                    ps8.setInt(10, cmbMas_SL_type);
                    ps8.setInt(11, rs6.getInt("sub_ledger_code"));
                    ps8.executeUpdate();
                    Upto_Credit_Bal = 0;
                    Upto_Debit_Bal = 0;

                } //End While(rs6.next())
                ps8.close();
                ps9.close();
                rs5.close();
                rs6.close();

            } //End While Loop for Main
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
        out.close();
    }

    private void sendMessage(HttpServletResponse response, String msg,
                             String bType) {
        try {
            String url =
                "org/Library/jsps/Messenger.jsp?message=" + msg + "&button=" +
                bType;
            response.sendRedirect(url);
        } catch (IOException e) {
        }
    }
}
