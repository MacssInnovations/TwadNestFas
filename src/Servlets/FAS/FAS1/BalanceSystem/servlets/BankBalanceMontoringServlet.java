package Servlets.FAS.FAS1.BalanceSystem.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.text.SimpleDateFormat;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class BankBalanceMontoringServlet extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        response.setContentType(CONTENT_TYPE);
        Connection connection = null;
        Statement statement = null;
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;
        PreparedStatement ps4 = null;
        PreparedStatement ps6 = null;
        ResultSet results = null;
        ResultSet results1 = null;
        ResultSet results2 = null;
        ResultSet results3 = null;
        ResultSet results4 = null;
        ResultSet results6 = null;
        double initialAmt = 0.0;

        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
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

        //Getting Values from HTML Page
        String command = request.getParameter("Command");
        String Account_unit_Code = request.getParameter("cmbAcc_UnitCode");
        String Office_Code = request.getParameter("cmbOffice_code");
        String CashBook_Year = request.getParameter("txtCB_Year");
        String CashBook_Month = request.getParameter("txtCB_Month");
        int AccountUnitCode = 0;
        int OfficeCode = 0;
        int CashBookYear = 0;
        int CashBookMonth = 0;
        try {
            AccountUnitCode = Integer.parseInt(Account_unit_Code);
            System.out.println("Account_Unit_Code After is:" +
                               AccountUnitCode);
        } catch (Exception e) {
            System.out.println("Exception in Unit:" + e);
            AccountUnitCode = 0;
        }
        ;
        try {
            OfficeCode = Integer.parseInt(Office_Code);
            System.out.println("Office_Code After is:" + OfficeCode);
        } catch (Exception e) {
            System.out.println("Exception in Office:" + e);
            OfficeCode = 0;
        }
        try {
            CashBookYear = Integer.parseInt(CashBook_Year);
            System.out.println("CashBook_Year After is:" + CashBookYear);
        } catch (Exception e) {
            System.out.println("Exception in Year:" + e);
            CashBookYear = 0;
        }
        try {
            CashBookMonth = Integer.parseInt(CashBook_Month);
            System.out.println("CashBook Month After is:" + CashBookMonth);
        } catch (Exception e) {
            System.out.println("Exception in Month:" + e);
            CashBookMonth = 0;
        }
        System.out.println("command is:" + command);
        if (command.equalsIgnoreCase("BankName")) {
            String xml = "";
            try {
                response.setContentType("text/xml");
                int count = 0;
                String sqlquery =
                    "select distinct a.bank_id,a.bank_name from fas_mst_banks a,fas_office_bank_ac_current b where " +
                    " a.bank_id=b.bank_id and b.accounting_unit_id=?";
                ps = connection.prepareStatement(sqlquery);
                ps.setInt(1, OfficeCode);
                results = ps.executeQuery();
                xml = "<response>";
                while (results.next()) {
                    xml =
 xml + "<options><BankId>" + results.getInt("bank_id") +
   "</BankId><BranchId>" + results.getString("bank_name").trim() +
   "</BranchId></options>";
                    count++;
                }
                if (count != 0) {
                    xml =
 xml + "<flag>success</flag><command>Bank</command></response>";
                } else {
                    xml =
 xml + "<flag>failure</flag><command>Bank</command></response>";
                }
            } catch (Exception e) {
                System.out.println("Exception in AccountHead:" + e);
            }
            out.write(xml);
            System.out.println("Xml is:" + xml);
        }
        if (command.equalsIgnoreCase("Branch")) {
            String xml = "";
            int Branch = 0;
            Branch = Integer.parseInt(request.getParameter("BranchId"));
            System.out.println("BranchId is:" + Branch);
            try {
                response.setContentType("text/xml");
                int count = 0;
                String sqlquery =
                    "select branch_id,branch_name from fas_mst_bank_branches where bank_id=?";
                ps = connection.prepareStatement(sqlquery);
                ps.setInt(1, Branch);
                results = ps.executeQuery();
                xml = "<response>";
                while (results.next()) {
                    xml =
 xml + "<options><BranchId>" + results.getInt("branch_id") +
   "</BranchId><BranchName>" + results.getString("branch_name") +
   "</BranchName></options>";
                    count++;
                }
                if (count != 0) {
                    xml =
 xml + "<flag>success</flag><command>Branch</command></response>";
                } else {
                    xml =
 xml + "<flag>failure</flag><command>Branch</command></response>";
                }
            } catch (Exception e) {
                System.out.println("Exception in AccountHead:" + e);
            }
            out.write(xml);
            System.out.println("Xml is:" + xml);
        }

        if (command.equalsIgnoreCase("AccType")) {
            String xml = "";
            int Branch = 0;
            int Bank = 0;
            Branch = Integer.parseInt(request.getParameter("BranchId"));
            Bank = Integer.parseInt(request.getParameter("BankId"));

            System.out.println("BranchId is:" + Branch);
            try {
                response.setContentType("text/xml");
                int count = 0;
                String sqlquery =
                    "select distinct BANK_AC_TYPE_ID from fas_office_bank_ac_current where accounting_unit_id=? and bank_id=? " +
                    " and branch_id=? and AC_OPERATIONAL_MODE_ID=?";
                ps = connection.prepareStatement(sqlquery);
                ps.setInt(1, OfficeCode);
                ps.setInt(2, Bank);
                ps.setInt(3, Branch);
                ps.setString(4, "COL");
                results = ps.executeQuery();
                xml = "<response>";
                while (results.next()) {
                    xml =
 xml + "<options><Acctype>" + results.getString("BANK_AC_TYPE_ID") +
   "</Acctype></options>";
                    count++;
                }
                if (count != 0) {
                    xml =
 xml + "<flag>success</flag><command>AccType</command></response>";
                } else {
                    xml =
 xml + "<flag>failure</flag><command>AccType</command></response>";
                }
            } catch (Exception e) {
                System.out.println("Exception in AccountType:" + e);
            }
            out.write(xml);
            System.out.println("Xml is:" + xml);
        }

        if (command.equalsIgnoreCase("AccHead")) {
            String xml = "";
            int Branch = 0;
            int Bank = 0;
            String AcType = "";
            Branch = Integer.parseInt(request.getParameter("BranchId"));
            Bank = Integer.parseInt(request.getParameter("BankId"));
            AcType = request.getParameter("AcType");
            System.out.println("BranchId  AND ACTYPE is:" + Branch + AcType);
            try {
                response.setContentType("text/xml");
                int count = 0;
                String sqlquery =
                    "select distinct ac_head_code from fas_office_bank_ac_current where accounting_unit_id=? and bank_id=? " +
                    " and branch_id=? and AC_OPERATIONAL_MODE_ID=? and trim(BANK_AC_TYPE_ID)=?";
                ps = connection.prepareStatement(sqlquery);
                ps.setInt(1, OfficeCode);
                ps.setInt(2, Bank);
                ps.setInt(3, Branch);
                ps.setString(4, "COL");
                ps.setString(5, AcType);
                results = ps.executeQuery();
                xml = "<response>";
                while (results.next()) {
                    System.out.println("hi:");
                    System.out.println("hi:" + results.getInt("ac_head_code"));
                    xml =
 xml + "<options><Acchead>" + results.getInt("ac_head_code") +
   "</Acchead></options>";
                    count++;
                }
                if (count != 0) {
                    xml =
 xml + "<flag>success</flag><command>AccHead</command></response>";
                } else {
                    xml =
 xml + "<flag>failure</flag><command>AccHead</command></response>";
                }
            } catch (Exception e) {
                System.out.println("Exception in AccountHead:" + e);
            }
            out.write(xml);
            System.out.println("Xml is:" + xml);
        }


        if (command.equalsIgnoreCase("BankAcNo")) {
            String xml = "";
            int Branch = 0;
            int Bank = 0;
            int CollAchead = 0;
            Branch = Integer.parseInt(request.getParameter("BranchId"));
            Bank = Integer.parseInt(request.getParameter("BankId"));
            CollAchead = Integer.parseInt(request.getParameter("CollAcCode"));
            System.out.println("BranchId is:" + Branch);
            try {
                response.setContentType("text/xml");
                int count = 0;
                String sqlquery =
                    "select distinct bank_ac_no from fas_office_bank_ac_current where accounting_unit_id=? and bank_id=? " +
                    " and branch_id=? and ac_head_code=?";
                ps = connection.prepareStatement(sqlquery);
                ps.setInt(1, OfficeCode);
                ps.setInt(2, Bank);
                ps.setInt(3, Branch);
                ps.setInt(4, CollAchead);
                results = ps.executeQuery();
                xml = "<response>";
                while (results.next()) {
                    xml =
 xml + "<options><BankAcNo>" + results.getLong("bank_ac_no") +
   "</BankAcNo></options>";
                    count++;
                }
                if (count != 0) {
                    xml =
 xml + "<flag>success</flag><command>BankAcNo</command></response>";
                } else {
                    xml =
 xml + "<flag>failure</flag><command>BankAcNo</command></response>";
                }
            } catch (Exception e) {
                System.out.println("Exception in BankAcNo:" + e);
            }
            out.write(xml);
            System.out.println("Xml is:" + xml);
        }

        if (command.equalsIgnoreCase("OpeningBal")) {
            String xml = "";
            int Branch = 0;
            int Bank = 0;
            String AcType = "";
            int CollAcCode = 0;
            long BankAcNo = 0;
            Branch = Integer.parseInt(request.getParameter("BranchId"));
            Bank = Integer.parseInt(request.getParameter("BankId"));
            AcType = request.getParameter("AcType");
            CollAcCode = Integer.parseInt(request.getParameter("CollAcCode"));
            BankAcNo = Long.parseLong(request.getParameter("BankAcNo"));
            System.out.println("BranchId  AND ACTYPE is:" + Branch + AcType +
                               BankAcNo);
            try {
                response.setContentType("text/xml");
                int count = 0;
                String sqlquery =
                    "select OPENING_BALANCE from FAS_MST_BANK_BALANCE where accounting_unit_id=? and bank_id=? " +
                    " and branch_id=? and AC_OPERATIONAL_MODE_ID=? and trim(BANK_AC_TYPE_ID)=trim(?) and BANK_AC_NO=?";
                ps = connection.prepareStatement(sqlquery);
                ps.setInt(1, OfficeCode);
                ps.setInt(2, Bank);
                ps.setInt(3, Branch);
                ps.setString(4, "COL");
                ps.setString(5, AcType);
                ps.setLong(6, BankAcNo);
                results = ps.executeQuery();
                xml = "<response>";
                while (results.next()) {
                    // initialAmt=results.getDouble("INITIAL_DEPOSIT_AMT");
                    // System.out.println("initial Amount:        "+initialAmt);
                    xml =
 xml + "<options><OpeningBal>" + results.getDouble("OPENING_BALANCE") +
   "</OpeningBal></options>";
                    count++;
                }
                if (count != 0) {
                    xml =
 xml + "<flag>success</flag><command>OpeningBal</command></response>";
                } else {
                    xml =
 xml + "<flag>failure</flag><command>OpeningBal</command></response>";
                }
            } catch (Exception e) {
                System.out.println("Exception in OpeningBal:" + e);
            }
            out.write(xml);
            System.out.println("Xml is:" + xml);
        }
        if (command.equalsIgnoreCase("Collection")) {
            //System.out.println("Initial Amount***********"+initialAmt);
            String xml = "";
            int Branch = 0;
            int Bank = 0;
            String AcType = "";
            int CollAcCode = 0;
            long BankAcNo = 0;
            int AccUnit = 0;
            int AccHead = 0;
            double OpeningBal = 0;
            double CollAmount = 0;
            double PayAmount = 0;
            double RemAmount = 0;
            double ClosingBalColl = 0;
            double ClosingBalRemitt = 0;
            double DiffAmount = 0;
            double FundHoAmount = 0;
            double FundTrfAmount = 0;
            String DiffInd = "";
            java.sql.Date VerifyDate = null;
            Branch = Integer.parseInt(request.getParameter("BranchId"));
            Bank = Integer.parseInt(request.getParameter("BankId"));
            //AcType=request.getParameter("AcType").trim();
            AcType = request.getParameter("AcType");
            CollAcCode = Integer.parseInt(request.getParameter("CollAcCode"));
            BankAcNo = Long.parseLong(request.getParameter("BankAcNo"));
            AccUnit = Integer.parseInt(request.getParameter("AccUnit"));
            AccHead = Integer.parseInt(request.getParameter("AccHead"));
            String Verify_on = request.getParameter("txtverify_date");

            /*  try{
                      String sqlquery1="select INITIAL_DEPOSIT_AMT from FAS_MST_BANK_BALANCE where accounting_unit_id=? and bank_id=? " +
                      " and branch_id=? and AC_OPERATIONAL_MODE_ID=? and trim(BANK_AC_TYPE_ID)=trim(?) and BANK_AC_NO=?";
                      ps6=connection.prepareStatement(sqlquery1);
                System.out.println("office code inside:........"+OfficeCode);
                      ps6.setInt(1,OfficeCode);
                      ps6.setInt(2,Bank);
                      ps6.setInt(3,Branch);
                      ps6.setString(4,"COL");
                      ps6.setString(5,AcType);
                      ps6.setLong(6,BankAcNo);
                      results6=ps6.executeQuery();

                      while(results6.next()) {
                          initialAmt=results6.getDouble("INITIAL_DEPOSIT_AMT");
                      System.out.println("Initial :......"+initialAmt);
                      }
                System.out.println("sql is..........."+sqlquery1);
            }
            catch(Exception e) {
            System.out.println("err");
            }*/


            try {
                OpeningBal =
                        Double.parseDouble(request.getParameter("OpeningBal"));
            } catch (Exception e) {
                System.out.println("Exception in OpeningBal:" + e);
                OpeningBal = 0.0;
            }
            System.out.println("BranchId  AND ACTYPE is:" + Branch + "-" +
                               Bank + "-" + BankAcNo + "-" + AccHead + "-" +
                               AccUnit + "-" + CashBookYear + "-" +
                               CashBookMonth + "-" + OpeningBal);
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
                    //sendMessage(response,"Date of formation is not valid.<br>","back");
                }
            }
            System.out.println("Verify date is:" + VerifyDate);
            try {
                response.setContentType("text/xml");
                int count = 0;
                /* String sql1="select total_amount from fas_receipt_master where accounting_unit_id=? and cashbook_year=? " +
                " and cashbook_month=? and bank_id=? and branch_id=? and account_head_code=? and receipt_date<=? " +
                " and receipt_status!=? and ACCOUNT_NO=?";*/
                String sql1 =
                    "select total_amount,INITIAL_DEPOSIT_AMT from" + " (" +
                    " select account_no,total_amount from fas_receipt_master where accounting_unit_id=? " +
                    " and cashbook_year=? and cashbook_month=? and bank_id=? and " +
                    " branch_id=? and account_head_code=? and receipt_date<=? " +
                    " and receipt_status!=? and ACCOUNT_NO=?" + " ) x" +
                    " left join" + " (" +
                    " select  BANK_AC_NO ,INITIAL_DEPOSIT_AMT from FAS_MST_BANK_BALANCE " +
                    " where accounting_unit_id=? and bank_id=? and branch_id=? and " +
                    " AC_OPERATIONAL_MODE_ID=? and trim(BANK_AC_TYPE_ID)=trim(?) " +
                    " and BANK_AC_NO=?" + " ) y" +
                    " on x.account_no=y.BANK_AC_NO";
                ps = connection.prepareStatement(sql1);
                ps.setInt(1, AccUnit);
                ps.setInt(2, CashBookYear);
                ps.setInt(3, CashBookMonth);
                ps.setInt(4, Bank);
                ps.setInt(5, Branch);
                ps.setInt(6, AccHead);
                ps.setDate(7, VerifyDate);
                ps.setString(8, "C");
                ps.setLong(9, BankAcNo);


                ps.setInt(10, AccUnit);
                ps.setInt(11, Bank);
                ps.setInt(12, Branch);
                ps.setString(13, "COL");
                ps.setString(14, AcType);
                ps.setLong(15, BankAcNo);
                System.out.println(AccUnit);
                System.out.println(CashBookYear);
                System.out.println(CashBookMonth);
                System.out.println(Bank);
                System.out.println(Branch);
                System.out.println(AcType);
                System.out.println(AccHead);
                System.out.println(VerifyDate);
                System.out.println(BankAcNo);

                results = ps.executeQuery();
                System.out.println("b4 first");
                while (results.next()) {
                    System.out.println("a4 first");
                    CollAmount =
                            CollAmount + results.getDouble("total_amount");
                    initialAmt = results.getDouble("INITIAL_DEPOSIT_AMT");
                    System.out.println("Collection Amount is:" + CollAmount);
                    System.out.println("Initial  Amount is======:" +
                                       initialAmt);
                }
                ps.close();
                results.close();

                String sql2 =
                    "select total_amount from fas_payment_master where accounting_unit_id=? and " +
                    " cashbook_year=? and cashbook_month=? and bank_id=? and branch_id=? and " +
                    " account_head_code=? and payment_date<=? and payment_status!=? and ACCOUNT_NO=?";
                ps1 = connection.prepareStatement(sql2);
                ps1.setInt(1, AccUnit);
                ps1.setInt(2, CashBookYear);
                ps1.setInt(3, CashBookMonth);
                ps1.setInt(4, Bank);
                ps1.setInt(5, Branch);
                ps1.setInt(6, AccHead);
                ps1.setDate(7, VerifyDate);
                ps1.setString(8, "C");
                ps1.setLong(9, BankAcNo);
                results1 = ps1.executeQuery();
                System.out.println("b4 second");
                while (results1.next()) {
                    PayAmount = PayAmount + results1.getDouble("total_amount");
                    System.out.println("Payment Amount is:" + PayAmount);
                }
                ps1.close();
                results1.close();


                String sqlquery =
                    "select amount_remitted from fas_remittance where accounting_unit_id=? and " +
                    " cashbook_year=? and cashbook_month=? and remittance_date<=? ";
                ps2 = connection.prepareStatement(sqlquery);
                ps2.setInt(1, AccUnit);
                ps2.setInt(2, CashBookYear);
                ps2.setInt(3, CashBookMonth);
                ps2.setDate(4, VerifyDate);
                results2 = ps2.executeQuery();
                System.out.println("b4 third");
                while (results2.next()) {
                    RemAmount =
                            RemAmount + results2.getDouble("amount_remitted");
                    System.out.println("Remittance Amount is:" + RemAmount);
                }
                ps2.close();
                results2.close();
                String sql3 = "";
                if (OfficeCode == 5000) {
                    System.out.println("Inner Office 5000");
                    sql3 =
"select TOTAL_AMOUNT from FAS_FUND_RECEIPT_BY_HO where accounting_unit_id=? and " +
 " cashbook_year=? and cashbook_month=? and ho_bank_id=? and ho_branch_id=? and DR_ACCOUNT_HEAD_CODE=? and RECEIPT_DATE<=? and" +
 " receipt_status!=? and HO_ACCOUNT_NO=?";
                } else {
                    System.out.println("Inner Else 5000");
                    sql3 =
"select TOTAL_AMOUNT from FAS_FUND_RECEIPT_BY_OFFICE where accounting_unit_id=? and " +
 " cashbook_year=? and cashbook_month=? and office_bank_id=? and office_branch_id=? and DR_ACCOUNT_HEAD_CODE=? and RECEIPT_DATE<=? and" +
 " receipt_status!=? and OFFICE_ACCOUNT_NO=?";
                }
                ps2 = connection.prepareStatement(sql3);
                ps2.setInt(1, AccUnit);
                ps2.setInt(2, CashBookYear);
                ps2.setInt(3, CashBookMonth);
                ps2.setInt(4, Bank);
                ps2.setInt(5, Branch);
                ps2.setInt(6, AccHead);
                ps2.setDate(7, VerifyDate);
                ps2.setString(8, "C");
                ps2.setLong(9, BankAcNo);
                results2 = ps2.executeQuery();
                while (results2.next()) {
                    System.out.println("Inner While 5000");
                    FundHoAmount =
                            FundHoAmount + results2.getDouble("TOTAL_AMOUNT");
                    System.out.println("a4 is:" + CollAmount);
                }
                ps2.close();
                results2.close();


                String sql4 = "";
                if (OfficeCode == 5000) {
                    sql4 =
"select TOTAL_AMOUNT from FAS_FUND_TRF_FROM_HO_MASTER where accounting_unit_id=? and cashbook_year=? and " +
 " cashbook_month=? and HO_BANK_ID=? and ho_branch_id=? and ACCOUNT_HEAD_CODE=? and DATE_OF_TRANSFER<=? and " +
 " TRANSFER_STATUS!=? and HO_ACCOUNT_NO=?";
                } else {
                    sql4 =
"select TOTAL_AMOUNT from FAS_FUND_TRF_FROM_OFFICE where accounting_unit_id=? and cashbook_year=? and " +
 " cashbook_month=? and OFFICE_BANK_ID=? and OFFICE_BRANCH_ID=? and CR_ACCOUNT_HEAD_CODE=? and DATE_OF_TRANSFER<=? and " +
 " TRANSFER_STATUS!=? and OFFICE_ACCOUNT_NO=?";
                }

                ps2 = connection.prepareStatement(sql4);
                ps2.setInt(1, AccUnit);
                ps2.setInt(2, CashBookYear);
                ps2.setInt(3, CashBookMonth);
                ps2.setInt(4, Bank);
                ps2.setInt(5, Branch);
                ps2.setInt(6, AccHead);
                ps2.setDate(7, VerifyDate);
                ps2.setString(8, "C");
                ps2.setLong(9, BankAcNo);
                results2 = ps2.executeQuery();
                while (results2.next()) {
                    FundTrfAmount =
                            FundTrfAmount + results2.getDouble("TOTAL_AMOUNT");
                    System.out.println("a4 trf is:" + RemAmount);
                }


                /* try
                {

                String sqlquery1="select INITIAL_DEPOSIT_AMT from FAS_MST_BANK_BALANCE where accounting_unit_id=? and bank_id=? " +
                " and branch_id=? and AC_OPERATIONAL_MODE_ID=? and trim(BANK_AC_TYPE_ID)=trim(?) and BANK_AC_NO=?";
                ps6=connection.prepareStatement(sqlquery1);
                System.out.println("office code inside:........"+OfficeCode);
                ps6.setInt(1,OfficeCode);
                ps6.setInt(2,Bank);
                ps6.setInt(3,Branch);
                ps6.setString(4,"COL");
                ps6.setString(5,AcType);
                ps6.setLong(6,BankAcNo);
                results6=ps6.executeQuery();
                System.out.println("sql is..........."+sqlquery1);
                while(results6.next()) {
                    initialAmt=results6.getDouble("INITIAL_DEPOSIT_AMT");
                System.out.println("Initial :......"+initialAmt);
                }

                }
                catch(Exception e) {
                    System.out.println("err");
                }*/
                System.out.println("a4 Collamount is:" + CollAmount);
                System.out.println("a4 remitt is:" + RemAmount);
                System.out.println("a4 Payment is:" + PayAmount);
                System.out.println("a4 FundHoAmount is:" + FundHoAmount);
                System.out.println("a4 FundTRfoAmount is:" + FundTrfAmount);
                CollAmount = CollAmount + FundHoAmount;
                RemAmount = RemAmount + FundTrfAmount;
                ClosingBalColl =
                        OpeningBal + CollAmount - PayAmount + initialAmt;
                ClosingBalRemitt = OpeningBal + RemAmount - PayAmount;
                if (ClosingBalColl == ClosingBalRemitt) {
                    DiffInd = "Equal";
                } else {
                    DiffInd = "NotEqual";
                }
                DiffAmount = ClosingBalColl - ClosingBalRemitt;

                System.out.println("Closing Bal Collection:" + ClosingBalColl);
                System.out.println("Closing Bal Remittance:" +
                                   ClosingBalRemitt);
                System.out.println("Difference Amount is:" + DiffAmount);
                xml =
 "<response><flag>success</flag><command>Collection</command><CollAmount>" +
   CollAmount + "</CollAmount><RemAmount>" + RemAmount +
   "</RemAmount><PayAmount>" + PayAmount + "</PayAmount><ClosingBalColl>" +
   ClosingBalColl + "</ClosingBalColl><ClosingBalRem>" + ClosingBalRemitt +
   "</ClosingBalRem><DiffAmount>" + DiffAmount + "</DiffAmount><DiffInd>" +
   DiffInd + "</DiffInd></response>";
                out.write(xml);
                CollAmount = 0;
                RemAmount = 0;
                PayAmount = 0;
                ClosingBalColl = 0;
                ClosingBalRemitt = 0;

            } catch (Exception e) {
                System.out.println("Exception in Collection:" + e);
            }
            //out.write(xml);
            System.out.println("Xml is:" + xml);
        }


    }
}
