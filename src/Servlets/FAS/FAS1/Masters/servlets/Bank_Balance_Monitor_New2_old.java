package Servlets.FAS.FAS1.Masters.servlets;

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

public class Bank_Balance_Monitor_New2_old extends HttpServlet {
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
        ResultSet rs1 = null;
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
        String Account_unit_Code = request.getParameter("AccUnit");
        System.out.println("Account_unit_Code" + Account_unit_Code);

        String Office_Code = request.getParameter("cmbOffice_code");
        System.out.println("Office_Code" + Office_Code);
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
            System.out.println("BranchId:" + Branch);
            Bank = Integer.parseInt(request.getParameter("BankId"));
            System.out.println("Bank:" + Bank);
            //AcType=request.getParameter("AcType").trim();
            AcType = request.getParameter("txtBankAcc_type");
            System.out.println("AcType:" + AcType);
            BankAcNo = Long.parseLong(request.getParameter("BankAcNo"));
            System.out.println("BankAcNo:" + BankAcNo);
            AccUnit = Integer.parseInt(request.getParameter("AccUnit"));
            System.out.println("AccUnit:" + AccUnit);
            //  AccHead=Integer.parseInt(request.getParameter("AccHead"));
            // System.out.println("AccHead:"+AccHead);
            String Verify_on = request.getParameter("txtverify_date");
            System.out.println("Verify_on:" + Verify_on);

            try {
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

                if (rs1.next()) {
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
            } catch (Exception e) {
                System.out.println("Finding Branch failed due to exception" +
                                   e);
                xml = xml + "<flag>failure</flag>";
            }
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
                    " branch_id=? and account_head_code=? and  receipt_date<=? " +
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
                    "  account_head_code=? and payment_date<=? and payment_status!=? and ACCOUNT_NO=?";
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
