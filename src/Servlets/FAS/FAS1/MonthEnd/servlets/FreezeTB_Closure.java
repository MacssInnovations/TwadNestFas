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

public class FreezeTB_Closure extends HttpServlet {
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
        int trial_count=0;
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
            System.out.println("CashBook Month After is:" + CashbookMonth);
        } catch (Exception e) {
            System.out.println("Exception in Month:" + e);
            CashbookMonth = 0;
        }


        /** Get User ID */
        String userid = (String)session.getAttribute("UserId");

        /** Get Updated Time */
        long l = System.currentTimeMillis();
        Timestamp ts = new Timestamp(l);
      
        
        if(CashbookMonth>1){
  		  try {
  		  	
  		  		int prevmonth_trial=CashbookMonth-1;
  		      PreparedStatement ps =con.prepareStatement("  select 'X'                  \n" +
  		                        "  from                        \n" +
  		                        "    FAS_TB_CLOSURE  \n" +
  		                        "  WHERE                       \n" +
  		                      //  "     ACCOUNTING_UNIT_ID=?     \n" +
  		                        "  CASHBOOK_YEAR=?      \n" +
  		                        "  AND CASHBOOK_MONTH=?");
  		      //ps.setInt(1, cmbAcc_UnitCode);
  		      ps.setInt(1, CashbookYear);
  		      ps.setInt(2, prevmonth_trial);
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
          else
          {

  				try {
  	     		  	
  	 		  		int prevyear_trial=CashbookYear-1;
  	 		     PreparedStatement ps =con.prepareStatement("  select 'X'                  \n" +
  	 		                        "  from                        \n" +
  	 		                        "    FAS_TB_CLOSURE  \n" +
  	 		                        "  WHERE                       \n" +
  	 		                       // "     ACCOUNTING_UNIT_ID=?     \n" +
  	 		                        " CASHBOOK_YEAR=?      \n" +
  	 		                        "  AND CASHBOOK_MONTH=12");
  	 		    //  ps.setInt(1, cmbAcc_UnitCode);
  	 		      ps.setInt(1, prevyear_trial);
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
  	        	 sendMessage(response, "Please Freeze Previous Month ", "ok");
  	             return;
  	        }

         /**
           * Calculating New Cashbook Month and Year
           *//*
          int txtCB_Month1 = 0;
          txtCB_Month1 = txtCB_Month - 1;
          System.out.println("b4 cashbookmont1:" + txtCB_Month1);
          int txtCB_Year1 = 0;

          if (txtCB_Month1 == 0) {
              txtCB_Month1 = 12;
              txtCB_Year1 = txtCB_Year - 1;
          } else {
              txtCB_Month1 = txtCB_Month - 1;
              txtCB_Year1 = txtCB_Year;
          }

          //finacial year calculation

          if ((txtCB_Month >= 1 && txtCB_Month <= 3)) {
              NFinYear = (txtCB_Year - 1) + "-" + (txtCB_Year);
          } else {
              NFinYear = (txtCB_Year) + "-" + (txtCB_Year + 1);
          }

          ////////////////////////////////////////////////////


          System.out.println("cashbookmont1:" + txtCB_Month1);
          System.out.println("txtCB_Year1:" + txtCB_Year1);

          /** Check Trial Balance Closure table --'FAS_TB_CLOSURE'
           *  If Record exits in FAS_TB_CLOSURE table, You cant allow TB to Freeze
           */
     /*     int count_1 = 0;
          try {
              PreparedStatement ps3 = null;
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
          */

        /**
         * Check Whether TB already froze or not
         */
        try {
            PreparedStatement ps = null;
            PreparedStatement pss = null;
            String msg = " ";
            ps =
            con.prepareStatement("select TB_STATUS from FAS_TB_CLOSURE where CASHBOOK_YEAR=? and CASHBOOK_MONTH=?");
            ps.setInt(1, CashbookYear);
            ps.setInt(2, CashbookMonth);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                msg = "Trial Balance Closure has been Frozen Already ";
                sendMessage(response, msg, "ok");
                return;
            } else {


                /**
                     * Insert one record into fas_tb_closure
                     */
                try {
                    PreparedStatement ps2 =
                        con.prepareStatement("insert into FAS_TB_CLOSURE(CASHBOOK_YEAR,CASHBOOK_MONTH,TB_STATUS,TB_DATE,UPDATED_BY_USER_ID,UPDATED_DATE ) values(?,?,?,?,?,?)");
                    ps2.setInt(1, CashbookYear);
                    ps2.setInt(2, CashbookMonth);
                    ps2.setString(3, "Y");
                    ps2.setTimestamp(4, ts);
                    ps2.setString(5, userid);
                    ps2.setTimestamp(6, ts);
                    ps2.executeUpdate();
                    ps2.close();
                } catch (Exception e) {
                    System.out.println("exception in fas_tb_clousure" + e);
                }


                /**
                     * Insert one record in fas_tb_closure_log table
                     */
                try {

                    PreparedStatement ps3 =
                        con.prepareStatement("insert into FAS_TB_CLOSURE_LOG(CASHBOOK_YEAR,CASHBOOK_MONTH,TB_STATUS,TB_FREEZE_DATE,UPDATED_BY_USER_ID,UPDATED_DATE ) values(?,?,?,?,?,?)");
                    ps3.setInt(1, CashbookYear);
                    ps3.setInt(2, CashbookMonth);
                    ps3.setString(3, "Y");
                    ps3.setTimestamp(4, ts);
                    ps3.setString(5, userid);
                    ps3.setTimestamp(6, ts);
                    ps3.executeUpdate();
                    ps3.close();
                } catch (Exception e) {
                    System.out.println("exception in fas_tb_closure_log" + e);
                }


            }

            ps.close();


            con.commit();
            con.setAutoCommit(true);

            msg = "Trial Balance Closure has been Frozen Successfully.......";
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
                "Trial Balance Status Changeing is Unsuccessful.......";
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
            System.out.println(e);
        }
    }

}
