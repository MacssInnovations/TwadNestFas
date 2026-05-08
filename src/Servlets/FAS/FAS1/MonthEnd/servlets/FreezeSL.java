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

public class FreezeSL extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
System.out.println("freeze sllllllllllllllllllllllllllllllll");

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

        int txtCB_Year = 0, txtCB_Month = 0, cmbAcc_UnitCode =
            0, cmbOffice_code = 0, acchead = 0;
        String radSL_STATUS = "";

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
        radSL_STATUS = request.getParameter("radTB_status");
        System.out.println("radSL_STATUS..." + radSL_STATUS);

        /** Get User ID */
        String userid = (String)session.getAttribute("UserId");

        /** Get Updated Time */
        long l = System.currentTimeMillis();
        Timestamp ts = new Timestamp(l);
        /**Get Account Head */
        /*    try {
            PreparedStatement pss=null;
            pss=con.prepareStatement(" select ACCOUNT_HEAD_CODE from FAS_GENERAL_LEDGER_CB ");
            pss.setInt(1,acchead);
            rss=ps1.executeQuery();

        }        catch(IOException e)

        ResultSet res1=ps.executeQuery();
        */
        try {
            //con.setAutoCommit(false);
            //PreparedStatement ps=null;
            String msg = " ";

            PreparedStatement ps1 =
                con.prepareStatement("insert into FAS_SL_UPTO_DATA_STATUS(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,SL_STATUS,SL_DATE,UPDATED_BY_USER_ID,UPDATED_DATE ) values(?,?,?,?,?,?,?,?)");
            ps1.setInt(1, cmbAcc_UnitCode);
            ps1.setInt(2, 0);
            ps1.setInt(3, txtCB_Year);
            ps1.setInt(4, txtCB_Month);
            ps1.setString(5, "Y");
            ps1.setTimestamp(6, ts);
            ps1.setString(7, userid);
            ps1.setTimestamp(8, ts);

            int i = ps1.executeUpdate();

            System.out.println("value of i...::::::::::::::::::::::::" + i);

            if (i > 0) {
                msg =
 "SubLedger Closing Balance Status has been Frozen Successfully.......";
                msg = msg + "<br><br>";
                sendMessage(response, msg, "ok");
            } else {
                msg = "Failure in Insertion";
                msg = msg + "<br><br>";
                sendMessage(response, msg, "ok");
            }


            // ps1.close();


        } catch (Exception e) {

            System.out.println(e.getMessage());

            /*
            try
            {
                con.rollback();
            }catch(SQLException e1)
            {
                System.out.println("exception in rollback"+e1);
            }
            System.out.println("Exception in Trial balance "+e);
            String msg="SubLedger Closing Balance Status Changeing is Unsuccessful.......";
            msg=msg+"<br><br>";
            sendMessage(response,msg,"ok");
            */
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
