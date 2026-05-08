package Servlets.FAS.FAS1.MonthEnd.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.*;

public class SubLedgerMasterServlet_MonthEnd_SJV extends HttpServlet {
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
        Connection connection = null;
        Statement statement = null;
        PreparedStatement ps = null;
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
         * Get User ID
         */
        String userid = (String)session.getAttribute("UserId");
        System.out.println("session id is:" + userid);


        /**
         * Get Parameters
         */
        String Account_unit_Code = request.getParameter("cmbAcc_UnitCode");
        String Office_Code = request.getParameter("cmbOffice_code");
        String CashBook_Year = request.getParameter("txtCB_Year");
        String CashBook_Month = request.getParameter("txtCB_Month");
        String supplement_no = request.getParameter("txtsupplement_no");


        /**
         * Variables Declarations
         */
        int AccountUnitCode = 0;
        int OfficeCode = 0;
        int CashBookYear = 0;
        int CashBookMonth = 0;
        int supp_no = 0;


        /**
         * Convert String data into integer
         */
        try {
            AccountUnitCode = Integer.parseInt(Account_unit_Code);
            OfficeCode = Integer.parseInt(Office_Code);
            CashBookYear = Integer.parseInt(CashBook_Year);
            CashBookMonth = Integer.parseInt(CashBook_Month);
            supp_no = Integer.parseInt(supplement_no);
            System.out.println("AccountUnitCode:::::"+AccountUnitCode);
            System.out.println("OfficeCode:::::"+OfficeCode);
            System.out.println("CashBookYear:::::"+CashBookYear);
            System.out.println("CashBookMonth:::::"+CashBookMonth);
            System.out.println("supp_no:::::"+supp_no);
        } catch (Exception e) {
            System.out.println("Exception in Converting Integer:" + e);
        }

        // changes on 27apr2012 by dhana
        try {

            ps =connection.prepareStatement("  select 'X'                  \n" +
                              "  from                        \n" +
                              "    FAS_TRIAL_BALANCE_STATUS_SJV  \n" +
                              "  WHERE                       \n" +
                              "     ACCOUNTING_UNIT_ID=?     \n" +
                              "  AND CASHBOOK_YEAR=?      \n" +
                              "  AND CASHBOOK_MONTH=? and SUPPLEMENT_NO=?");
            ps.setInt(1, AccountUnitCode);
            ps.setInt(2, CashBookYear);
            ps.setInt(3, CashBookMonth);
            ps.setInt(4, supp_no);
            ResultSet res = ps.executeQuery();
            if (res.next()) // if the row doesn't return by the query
            {
                sendMessage(response, "Trial Balance Already Froze", "ok");
                return;
            }
            res.close();
            ps.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        

        /**
         * Calling Procedure
         */
        CallableStatement cs = null;
        String msg = "";
        try {
            cs =
  connection.prepareCall(" call FAS_SL_SJV_POSTING_NEW ( ?::numeric,?::numeric,?::numeric,?::numeric,?,?::numeric,?::numeric ) ");
            cs.setInt(1, AccountUnitCode);
            cs.setInt(2, OfficeCode);
            cs.setInt(3, CashBookYear);
            cs.setInt(4, CashBookMonth);
            cs.setString(5, userid);
            cs.setInt(6, supp_no);
            cs.registerOutParameter(7, java.sql.Types.NUMERIC);
            cs.setInt(7, 0);
           // cs.registerOutParameter(8, java.sql.Types.VARCHAR);
            cs.execute();
            //int errcode = cs.getInt(7);
           
            int errcode = cs.getBigDecimal(7).intValue();
          //  String errStr=cs.getString(8);
            System.out.println("Error Code --->" + errcode);
        //    System.out.println("errStr Code --->" + errStr);
            if (errcode != 0) {
                msg = "Sub Ledger Posting for SJV  Failed ";
                msg = msg + "<br><br>";
                sendMessage(response, msg, "ok");
            } else {
                msg = "Sub Ledger Posting for SJV Generated Successfully !  ";
                msg = msg + "<br><br>";
                sendMessage(response, msg, "ok");
            }

        } catch (Exception e) {
            System.out.println(e);
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
            System.out.println("Eror in send message");
        }
    }

}


