package Servlets.FAS.FAS1.MonthEnd.servlets;

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

public class UnFreezeGL_PB extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,IOException {

        /**
         * Variables Declaration
         */
        Connection con = null;
        Statement statement = null;
        PreparedStatement ps3 = null;
        ResultSet rs3 = null;


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
        int txtCB_Year = 0, txtCB_Month = 0, cmbAcc_UnitCode = 0,counted_unfreeze=0;
        String radTB_status = "";


        /**
         * Get Accounting Unit ID
         */
        try {
            cmbAcc_UnitCode =
                    Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
        } catch (NumberFormatException e) {
            System.out.println("exception" + e);
        }
        System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);


        /**
         * Get Cashbook Month and Year
         */
        txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));
        txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
        System.out.println("year..." + txtCB_Year);
        System.out.println("Month..." + txtCB_Month);

        /**
         * Get User ID who is unfreezing TB
         */
        String userid = (String)session.getAttribute("UserId");


        /**
         * Get Time of Unfreezing TB
         */
        long l = System.currentTimeMillis();
        Timestamp ts = new Timestamp(l);


        /** Check Trial Balance Closure table --'fas_gl_pbstatus'
         *  If Record exits in fas_gl_pbstatus table, You cant allow TB to Unfreeze
         */

        int count = 0;
        try {
            con.setAutoCommit(false);
            ps3 =
 con.prepareStatement("select * from fas_gl_pbstatus where cashbook_year=?  and  cashbook_month= ? AND ACCOUNTING_UNIT_ID=?");
            ps3.setInt(1, txtCB_Year);
            ps3.setInt(2, txtCB_Month);
            ps3.setInt(3, cmbAcc_UnitCode);
            rs3 = ps3.executeQuery();
            if (rs3.next()) {
                System.out.println("3");
                count++;
            }
           ps3.close();
           rs3.close();

        } catch (Exception e) {
            System.out.println("Error in TB Closure :::::" + e);
        }
        if (count == 0) {
            sendMessage(response, "Sorry !  You can't Unfreeze PB. Please Run GL PB Updation","ok");
            return;
        }
    else{
        System.out.println("elseeeeeeee");
          try {
              con.setAutoCommit(false);
              ps3 = con.prepareStatement("select * from fas_gl_pbstatus where cashbook_year=?  and  cashbook_month= ? AND ACCOUNTING_UNIT_ID=? and  GL_PB_FREEZE_STATUS='Y'");
              ps3.setInt(1, txtCB_Year);
              ps3.setInt(2, txtCB_Month);
              ps3.setInt(3, cmbAcc_UnitCode);
              rs3 = ps3.executeQuery();
              if (rs3.next()) {
                  System.out.println("yyyyyyyyyyyyyyyyyyyy");
                  counted_unfreeze++;
                  try {
                  
                      /** Variables Declaration */
                      PreparedStatement ps = null;
                      PreparedStatement ps2 = null;
                      String msg = " ";
                  
                  
                      PreparedStatement ps1 =con.prepareStatement("update fas_gl_pbstatus set GL_PB_FREEZE_STATUS=null,GL_PB_FREEZE_DATE   =?,UPDATED_BY_USER_ID=?,UPDATED_DATE=? where  ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=?");
                      ps1.setTimestamp(1, ts);
                      ps1.setString(2, userid);
                      ps1.setTimestamp(3, ts);
                      ps1.setInt(4, cmbAcc_UnitCode);
                      ps1.setInt(5, txtCB_Year);
                      ps1.setInt(6, txtCB_Month);
                      ps1.executeUpdate();
                      ps1.close();
                    
                  System.out.println("last");
                      /** Commit the database */
                      con.commit();
                      con.setAutoCommit(true);
                      msg ="GL_PB_FREEZE_STATUS is UnFreezed...";
                      msg = msg + "<br><br>";
                      sendMessage(response, msg, "ok");
                  } catch (Exception e) {
                      try {
                          con.rollback();
                      } catch (SQLException e1) {
                          System.out.println("exception in rollback" + e1);
                      }
                      System.out.println("Exception in PB " + e);
                      String msg =
                          "Error in PB UnFreeze.......";
                      msg = msg + "<br><br>";
                      sendMessage(response, msg, "ok");
                  
                  }
              }
             

          } catch (Exception e) {
              System.out.println("Error in TB Closure " + e);
          }
          if(counted_unfreeze==0) {
              sendMessage(response, "Sorry !  You can't Unfreeze PB. PB is not Freezed","ok");
              return;
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
            System.out.println(e);
        }
    }

}

