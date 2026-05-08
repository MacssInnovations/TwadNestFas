package Servlets.FAS.FAS1.MonthEnd.servlets;
/**
 * Newly Generate java author By siva.N
 * purpose : Unfreeze TB closusre for supplement
 * Date    :2016-07-23
 */

import java.io.IOException;
import java.io.PrintWriter;
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

public class UnFreezeTB_Closure_SJV extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {


        /**
         * Variables Declaration
         */
        Connection con = null;
        Statement statement = null;
       

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
         * Session Checking
         */

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


        /**
         * Variables Declaration
         */
        int txtCB_Year = 0, txtCB_Month = 0, cmbAcc_UnitCode = 0;
        String radTB_status = "";

        String CashBook_Year = request.getParameter("txtCB_Year");
        String CashBook_Month = request.getParameter("txtCB_Month");
        int CashbookYear = 0;
        int CashbookMonth = 0;
        /**
         * Get Parameters
         *
         */

        try {
            CashbookYear = Integer.parseInt(CashBook_Year);
            System.out.println("CashBook_Year After is:" + CashbookYear);
        } catch (Exception e) {
            System.out.println("Exception in Year:" + e);
            CashbookYear = 0;
        }
        try {
            CashbookMonth = Integer.parseInt(CashBook_Month);
            // closebalmonth= CashBookMonth-1;
            System.out.println("CashBook Month After is:" + CashbookMonth);
        } catch (Exception e) {
            System.out.println("Exception in Month:" + e);
            CashbookMonth = 0;
        }
        /** Get Supplement Number */
        int Supplement_No = 0;
        try {
            Supplement_No =
                    Integer.parseInt(request.getParameter("txtsupplement_no"));
        } catch (Exception e) {
            System.out.println(e);
        }

        /**
         *  The variables 'radTB_status' will be always 'N' because this menu is exclusive for Unfreezif TB
         */
        radTB_status = request.getParameter("radTB_status");
        System.out.println("radTB_status... Test_siva Supplement" + radTB_status);

        /**
         * Get User ID who is unfreezing TB
         */
        String userid = (String)session.getAttribute("UserId");


        /**
         * Get Time of Unfreezing TB
         */
        long l = System.currentTimeMillis();
        Timestamp ts = new Timestamp(l);

        /** Check Trial Balance Closure table --'FAS_TB_CLOSURE_SJV'
         *  If Record exits in FAS_TB_CLOSURE_SJV table, You cant allow TB to Unfreeze
         */

        int count = 0,count_gl=0,count_sl=0,count1=0,count2=0;
        
        try {
       	 PreparedStatement ps11=null;
            ResultSet rs3=null;
           con.setAutoCommit(false);
           ps11 =
           con.prepareStatement("Select supplement_no ,Cashbook_Year,CASHBOOK_MONTH From fas_SUP_tb_closure Where Cashbook_Year=? and cashbook_month=?");
           ps11.setInt(1, CashbookYear);
           ps11.setInt(2, CashbookMonth);
        //   ps11.setInt(3, cmbAcc_UnitCode);
           rs3 = ps11.executeQuery();
           if (rs3.next()) {
               System.out.println("Data is there ...");
               count1++;
           }
           if (count1 ==0) {
               sendMessage(response,
                           " No Colsure is Done for this period",
                           "ok");
               return;
           }

       } catch (Exception e) {
           System.out.println("Error in TB status ********* " + e);
       }
        
     
        try {
        	 PreparedStatement ps11=null;
             ResultSet rs3=null;
            con.setAutoCommit(false);
            int ftyear=CashbookYear+1;
            System.out.println("ftyear"+ftyear);
            ps11 =
            con.prepareStatement("Select supplement_no ,Cashbook_Year,CASHBOOK_MONTH From fas_SUP_tb_closure Where Cashbook_Year=? ");
            ps11.setInt(1, ftyear);
          //  ps11.setInt(2, CashbookMonth);
         //   ps11.setInt(3, cmbAcc_UnitCode);
            rs3 = ps11.executeQuery();
            if (rs3.next()) {
                System.out.println("333333   Cannot Unfreeze TB for this month.......   First  Unfreeze TB for the subsequent months");
                count2++;
            }
            if (count2 > 0) {
                sendMessage(response,
                            "Cannot Unfreeze Closure for this month.......   First  Unfreeze Closure for the subsequent months",
                            "ok");
                return;
            }

        } catch (Exception e) {
            System.out.println("Error in TB status ********* " + e);
        }
        
        try {
       	 PreparedStatement ps11=null;
            ResultSet rs3=null;
           con.setAutoCommit(false);
           int sno=0;
          
           String query="select max(supplement_no) suppl_no ,Cashbook_Year,CASHBOOK_MONTH From fas_SUP_tb_closure Where Cashbook_Year=? and cashbook_month=? group by cashbook_year,cashbook_month";
           System.out.println("query +:"+query);
           ps11 =
           con.prepareStatement(query);
           ps11.setInt(1, CashbookYear);
           ps11.setInt(2, CashbookMonth);
        //   ps11.setInt(3, cmbAcc_UnitCode);
           rs3 = ps11.executeQuery();
           if (rs3.next()) {
              sno=rs3.getInt("suppl_no");
              System.out.println("sno"+sno+"supplement_no"+Supplement_No);
             count1++;
             if(sno==Supplement_No){
           }else{
        	   sendMessage(response,
                       "Unfreeze Closure for this month supplement_no"+sno+"",
                       "ok");
           return;
        	   
        	   }
           }
          
       } catch (Exception e) {
           System.out.println("Error in TB status ********* " + e);
       }
        
        
        try {
            /** Set Auotcommit to be false */
            con.setAutoCommit(false);

            /** Variables Declaration */
            PreparedStatement ps = null;
            PreparedStatement ps2 = null;
            PreparedStatement ps3 = null;
            //  PreparedStatement ps5=null;
            //  PreparedStatement ps6=null;
            PreparedStatement ps4 = null;
            PreparedStatement pss = null;
            String msg = " ";

            /** Parameter Checking */
            if (radTB_status.equalsIgnoreCase("Y")) {
                System.out.println("Inside TB_status 'Y'");

                ps =
           con.prepareStatement("select TB_STATUS from FAS_SUP_TB_CLOSURE where CASHBOOK_YEAR=? and CASHBOOK_MONTH=?");

                ps.setInt(1, CashbookYear);
                ps.setInt(2, CashbookMonth);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {

                    System.out.println("..3");
                    PreparedStatement ps1 =
                        con.prepareStatement("update FAS_SUP_TB_CLOSURE set TB_STATUS='Y',SUPPLEMENT_NO=?,TB_DATE=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=? where CASHBOOK_YEAR=? and CASHBOOK_MONTH=?");
                    ps1.setInt(1, Supplement_No);
                    ps1.setTimestamp(2, ts);
                    ps1.setString(3, userid);
                    ps1.setTimestamp(4, ts);
                    ps1.setInt(5, CashbookYear);
                    ps1.setInt(6, CashbookMonth);
                    ps1.executeUpdate();
                    ps1.close();
                }
              
                ps.close();

                pss =
            con.prepareStatement("select SUP_TB_STATUS from FAS_SUP_TB_CLOSURE_LOG where CASHBOOK_YEAR=? and CASHBOOK_MONTH=?");

                pss.setInt(1, CashbookYear);
                pss.setInt(2, CashbookMonth);
                ResultSet rs2 = pss.executeQuery();
                if (rs2.next()) {
                    System.out.println("..3.........");
                    PreparedStatement ps1 =
                        con.prepareStatement("update FAS_SUP_TB_CLOSURE_LOG  set SUP_TB_STATUS='Y',SUPPLEMENT_NO=?,TB_FREEZE_DATE=?,TB_UNFREEZE_DATE=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=? where CASHBOOK_YEAR=? and CASHBOOK_MONTH=?");
                    ps1.setInt(1, Supplement_No);
                    ps1.setTimestamp(2, ts);                   
                    ps1.setTimestamp(3, null);
                    ps1.setString(4,userid);
                    ps1.setTimestamp(5, ts);
                    ps1.setInt(6, CashbookYear);
                    ps1.setInt(7, CashbookMonth);
                    ps1.executeUpdate();
                    ps1.close();
                }
               pss.close();
            }


            else if (radTB_status.equalsIgnoreCase("N")) // If trial balance status enabled to "N" from "Y" means
            {
                System.out.println("Inside TB_status 'N'");

                System.out.println("..5");
                // Here i deleted the TB generated info from FAS_TRIAL_BALANCE_STATUS , if exist
                ps =
       con.prepareStatement("delete from FAS_SUP_TB_CLOSURE where CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and SUPPLEMENT_NO=?");
                ps.setInt(1, CashbookYear);
                ps.setInt(2, CashbookMonth);
                ps.setInt(3, Supplement_No);
                ps.executeUpdate();
                ps.close();
                
                 ps4 =
                        con.prepareStatement("update FAS_SUP_TB_CLOSURE_LOG  set SUP_TB_STATUS='Y',SUP_TB_UNFREEZE_DATE=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=? where CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and SUPPLEMENT_NO=?");
               
                ps4.setTimestamp(1, ts);
                ps4.setString(2, userid);
                ps4.setTimestamp(3, ts);
                ps4.setInt(4, CashbookYear);
                ps4.setInt(5, CashbookMonth);
                ps4.setInt(6, Supplement_No);
                ps4.executeUpdate();
                ps4.close();
         /*       ps4 =
      con.prepareStatement("insert into FAS_SUP_TB_CLOSURE_LOG ( CASHBOOK_YEAR,  CASHBOOK_MONTH , SUP_TB_STATUS, SUP_TB_UNFREEZE_DATE ,  UPDATED_BY_USER_ID , UPDATED_DATE,SUPPLEMENT_NO ) values(?,?,?,?,?,?,?)");
                ps4.setInt(1, CashbookYear);
                ps4.setInt(2, CashbookMonth);
                ps4.setString(3, "Y");
                ps4.setTimestamp(4, ts);
                ps4.setString(5, userid);
                ps4.setTimestamp(6, ts);
                ps4.setInt(7, Supplement_No);
                ps4.executeUpdate();
                ps4.close();*/


                msg = "Supplement Closure Freeze Status is Removed... ";
            }


            /** Commit the database */
            con.commit();
            con.setAutoCommit(true);


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
                "Supplement Closure Status Changeing is Unsuccessful.......";
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
