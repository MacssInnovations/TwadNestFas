package Servlets.FAS.FAS1.MonthEnd.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class FreezNilTB extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {


        /**
         * Database Connection
         */

        Connection con = null;
        Statement statement = null;
        ResultSet rst = null, results = null;
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
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
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
        int slcode = 0;
        double Upto_Debit_Bal = 0;
        double Upto_Credit_Bal = 0;
        double Month_OB_Bal_new = 0;
        double CRAmount = 0;
        double DRAmount1 = 0;
        double balance = 0;
        String Month_OB_Cr_Dr_ind_new = "";
        String MONTH_CLOSING_BAL_DR_CR_IND = "";
        String NFinYear = "";

        java.sql.Date MaxCRdate = null;
        java.sql.Date MaxDRdate = null;

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
        System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);

        /** Get Cash Book Year */
        txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));

        /** Get Cash Book Month */
        txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
        System.out.println("year..." + txtCB_Year);
        System.out.println("Month..." + txtCB_Month);

        /** Get YES or NO Status */
        radTB_status = request.getParameter("radTB_status");
        System.out.println("radTB_status..." + radTB_status);

        /** Get User ID */
        String userid = (String)session.getAttribute("UserId");

        /** Get Updated Time */
        long l = System.currentTimeMillis();
        Timestamp ts = new Timestamp(l);
        Boolean flag = false, flag2 = false, flag3 = true;
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


        /**
         * Calculating New Cashbook Month and Year
         */
        int cashbookmonth1 = 0;
        cashbookmonth1 = txtCB_Month - 1;
        System.out.println("b4 cashbookmont1:" + cashbookmonth1);
        int cashbookyear1 = 0;

        if (cashbookmonth1 == 0) {
            cashbookmonth1 = 12;
            cashbookyear1 = txtCB_Year - 1;
        } else {
            cashbookmonth1 = txtCB_Month - 1;
            cashbookyear1 = txtCB_Year;
        }

        //finacial year calculation

        if ((txtCB_Month >= 1 && txtCB_Month <= 3)) {
            NFinYear = (txtCB_Year - 1) + "-" + (txtCB_Year);
        } else {
            NFinYear = (txtCB_Year) + "-" + (txtCB_Year + 1);
        }

        ////////////////////////////////////////////////////
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

        System.out.println("cashbookmont1:" + cashbookmonth1);
        System.out.println("cashbookyear1:" + cashbookyear1);

        /** Check Trial Balance Closure table --'FAS_TB_CLOSURE'
         *  If Record exits in FAS_TB_CLOSURE table, You cant allow TB to Freeze
         */
        int count_1 = 0;
        try {

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


        try {
            con.setAutoCommit(false);
        } catch (Exception e) {
            System.out.println("");
        }


        if (flag3) {


            try {
                con.setAutoCommit(false);

                String msg = " ";
                String account_head = "   ";
                int account_head_code_exits = 0;


                /** For Yes Condition */

                if (radTB_status.equalsIgnoreCase("Y")) {

                    System.out.println("Inside TB_status 'Y'");
                    System.out.println("..0");
                    ps =
  con.prepareStatement("select 'X' from FAS_TRIAL_BALANCE " +
                       "WHERE ACCOUNTING_UNIT_ID=? AND CASHBOOK_YEAR=? AND CASHBOOK_MONTH=?" +
                       "HAVING SUM(CURRENT_MONTH_DEBIT)!=SUM(CURRENT_MONTH_CREDIT) AND SUM(CURRENT_MONTH_DEBIT) >0 " +
                       "AND SUM(CURRENT_MONTH_CREDIT)>0");
                    ps.setInt(1, cmbAcc_UnitCode);
                    ps.setInt(2, txtCB_Year);
                    ps.setInt(3, txtCB_Month);
                    ResultSet res = ps.executeQuery();
                    if (res.next()) // if the row doesn't return by the query
                    {
                        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%3");
                        sendMessage(response, "Trial Balance doesn't Tally",
                                    "ok");
                        System.out.println("Trial Balance doesn't Tally");
                        return;
                    }
                    res.close();
                    ps.close();


                    ps =
  con.prepareStatement("select NIL_TB_STATUS from FAS_TRIAL_BALANCE_STATUS where  ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=?");
                    ps.setInt(1, cmbAcc_UnitCode);
                    //ps.setInt(2,cmbOffice_code);
                    ps.setInt(2, txtCB_Year);
                    ps.setInt(3, txtCB_Month);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) // if already in TB_STATUS="N' then
                    {
                        // Mostly it never occur, but here just checking it whether already exist
                        System.out.println("..3");
                        ps1 =
 con.prepareStatement("update FAS_TRIAL_BALANCE_STATUS set NIL_TB_STATUS='Y',TB_DATE=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=? where  ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=?");
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
                        ps1 =
 con.prepareStatement("insert into FAS_TRIAL_BALANCE_STATUS(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,NIL_TB_STATUS,TB_DATE,UPDATED_BY_USER_ID,UPDATED_DATE ) values(?,?,?,?,?,?,?,?)");
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
 "Nil Trial Balance Status has been Frozen Successfully.......";
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

                con.commit();
                con.setAutoCommit(true);
                System.out.println("..8");


                msg = msg + "<br><br>";
                sendMessage(response, msg, "ok");
            } catch (Exception e) {
                try {
                    con.rollback();
                } catch (SQLException e1) {
                    System.out.println("exception in rollback" + e1);
                }
                System.out.println("Exception in Trial balance " + e);
                String msg =
                    " Nil Trial Balance Status Changing is Unsuccessful.......";
                msg = msg + "<br><br>";
                sendMessage(response, msg, "ok");

            }
        } else {
            try {
                con.rollback();
            } catch (SQLException e1) {
                System.out.println("catch:" + e1);
            }
            String msg = " Nil Trial Balance has failed to Update";
            msg = msg + "<br><br>";
            sendMessage(response, msg, "ok");
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
        }
    }

}
