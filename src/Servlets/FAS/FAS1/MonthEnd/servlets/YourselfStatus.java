package Servlets.FAS.FAS1.MonthEnd.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class YourselfStatus extends HttpServlet {
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
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
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

        int txtCB_Year = 0, txtCB_Month = 0, cmbAcc_UnitCode =
            0, cmbOffice_code = 0;
        String radTB_status = "";
        String msg = " ";
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


        /** Check Trial Balance Closure table --'FAS_TB_CLOSURE'
         *  If Record exits in FAS_TB_CLOSURE table, You cant allow TB to Freeze
         */
        int count_1 = 0;
        try {
            PreparedStatement ps3 = null;
            ResultSet rs3 = null;
            con.setAutoCommit(false);
            ps3 =
 con.prepareStatement("select * from FAS_YOURSELF_STATUS where ACCOUNTING_UNIT_ID=? and cashbook_year=?  and  cashbook_month= ?  and  YS_STATUS='Y' ");
            ps3.setInt(1, cmbAcc_UnitCode);
            ps3.setInt(2, txtCB_Year);
            ps3.setInt(3, txtCB_Month);
            rs3 = ps3.executeQuery();
            if (rs3.next()) {
                System.out.println("3");
                count_1++;
            }
            if (count_1 > 0) {
                sendMessage(response,
                            "DD details already Frozen!",
                            "ok");
                ps3.close();
                rs3.close();
                return;
            }
            ps3.close();
            rs3.close();
        } catch (Exception e) {
            System.out.println("Error in Freeze Yourself " + e);
        }


        /**
         *  SL, GL and TB Generation Sequence Check
         */
        /*
         try {

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


        /*

        try
        {
            con.setAutoCommit(false);
            PreparedStatement ps=null;
            PreparedStatement ps2=null;

            String msg=" ";
            String account_head="   ";
            int account_head_code_exits=0;


            /**
             * Find out Wrong Account Heads
             */
        /*
            ps2=con.prepareStatement("select account_head_code from fas_trial_balance where cashbook_month = ? and cashbook_year=? and accounting_unit_id= ? and account_head_code not in ( select account_head_code from com_mst_account_heads )");
            ps2.setInt(1,txtCB_Month);
            ps2.setInt(2,txtCB_Year);
            ps2.setInt(3,cmbAcc_UnitCode);

            ResultSet res2=ps2.executeQuery();
            if(res2.next())                     // if the row doesn't return by the query
            {
                  account_head=account_head+res2.getString("account_head_code");
                  account_head_code_exits ++;

            }
            if(account_head_code_exits >0 ) {
                sendMessage(response,"Wrong Account Head(s)"+account_head,"ok");
                System.out.println("Wrong Account Head(s)"+account_head);
                return;
            }



            /**Display Warning Message if TB in not generated */

        /*
            ps=con.prepareStatement("select count(*) as cnt from fas_trial_balance where accounting_unit_id=? and cashbook_year=? and cashbook_month=? ");
            ps.setInt(1,cmbAcc_UnitCode);
            ps.setInt(2,txtCB_Year);
            ps.setInt(3,txtCB_Month);
            ResultSet res1=ps.executeQuery();
            if(res1.next())                     // if the row doesn't return by the query
            {
                int count=res1.getInt("cnt");
                if(count==0)
                {
                  sendMessage(response,"Trial Balance not Generated","ok");
                  System.out.println("Trial Balance not Generated");
                  return;
                }
            }


            */
        try {

            /** For Yes Condition */

            if (radTB_status.equalsIgnoreCase("Y")) {
                /*  System.out.println("Inside YS_STATUS 'Y'");
                System.out.println("..0");
                ps=con.prepareStatement("select 'X' from FAS_TRIAL_BALANCE " +
                                        "WHERE ACCOUNTING_UNIT_ID=? AND CASHBOOK_YEAR=? AND CASHBOOK_MONTH=?" +
                                        "HAVING SUM(CURRENT_MONTH_DEBIT)!=SUM(CURRENT_MONTH_CREDIT)");
                ps.setInt(1,cmbAcc_UnitCode);
                ps.setInt(2,txtCB_Year);
                ps.setInt(3,txtCB_Month);
                ResultSet res=ps.executeQuery();
                if(res.next())                     // if the row doesn't return by the query
                {
                    sendMessage(response,"Trial Balance doesn't Tally","ok");
                    System.out.println("Trial Balance doesn't Tally");
                    return;
                }
                res.close();
                ps.close();

                */


                ps =
  con.prepareStatement("select YS_STATUS from FAS_YOURSELF_STATUS where  ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=?");
                ps.setInt(1, cmbAcc_UnitCode);
                //ps.setInt(2,cmbOffice_code);
                ps.setInt(2, txtCB_Year);
                ps.setInt(3, txtCB_Month);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) // if already in YS_STATUS="N' then
                {
                    // Mostly it never occur, but here just checking it whether already exist
                    System.out.println("..3");
                    PreparedStatement ps1 =
                        con.prepareStatement("update FAS_YOURSELF_STATUS set YS_STATUS='Y',YS_DATE=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=? where  ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=?");
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
                        con.prepareStatement("insert into FAS_YOURSELF_STATUS(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,YS_STATUS,YS_DATE,UPDATED_BY_USER_ID,UPDATED_DATE ) values(?,?,?,?,?,?,?,?)");
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
 "Yourself Status has been Frozen Successfully.......";
                ps.close();

            }
            /** For Not Condition */
            else if (radTB_status.equalsIgnoreCase("N")) // If trial balance status enabled to "N" from "Y" means
            {
                System.out.println("Inside YS_STATUS 'N'");

                System.out.println("..5");
                // Here i deleted the TB generated info from FAS_YOURSELF_STATUS , if exist
                ps =
  con.prepareStatement("delete from FAS_YOURSELF_STATUS where  ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=?");
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, txtCB_Year);
                ps.setInt(3, txtCB_Month);
                ps.executeUpdate();
                ps.close();
                System.out.println("..6");

                msg =
 "Yourself Froze Status is Removed... You have to do General Ledger Posting Again for this Accounting Unit ";
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
            msg = "Yourself Status Changeing is Unsuccessful.......";
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
