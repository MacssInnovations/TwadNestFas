package Servlets.FAS.FAS1.JournalSystem.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class Convert_GJV_To_LJV_SL extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }


    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {

        response.setContentType(CONTENT_TYPE);

        String CONTENT_TYPE = "text/html; charset=windows-1252";
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();


        Connection con = null;
        CallableStatement cs = null;
        PreparedStatement ps = null;

        /**
         * Session Checking
         */
        HttpSession session = request.getSession(false);
        try {

            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                return;
            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }


        /**
         * Get User ID
         */
        String update_user = (String)session.getAttribute("UserId");


        /**
         * Database Connection
         */
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
        }


        /**
         * Get Parameter Values
         */

        /** Get Accounting Unit ID */
        int cmbAcc_UnitCode =
            Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
        System.out.println("cmbAcc_UnitCode-->" + cmbAcc_UnitCode);


        /** Get Accounting Office ID */
        int cmbOffice_code =
            Integer.parseInt(request.getParameter("cmbOffice_code"));
        System.out.println("cmbOffice_code-->" + cmbOffice_code);

        /** Get Cashbook Year */
        int txtCB_Year =
            Integer.parseInt(request.getParameter("txtCashbookYear"));
        System.out.println("txtCB_Year-->" + txtCB_Year);

        /** Get Cashbook Month  */
        int txtCB_Month =
            Integer.parseInt(request.getParameter("txtCashbookMonth"));
        System.out.println("txtCB_Month-->" + txtCB_Month);

        /** Get Voucher Number  */
        int txtVoucherNo =
            Integer.parseInt(request.getParameter("txtVoucherNo"));
        System.out.println("txtVoucherNo-->" + txtVoucherNo);

        /** Get Cashbook Month  */
        int seq = Integer.parseInt(request.getParameter("seq"));
        System.out.println("seq-->" + seq);


        try {
            con.setAutoCommit(false);
        } catch (SQLException e) {
            System.out.println(e);
        }


        /**
         * Delete Previous Entries
         */

        try {
            String sql =
                "delete from FAS_CONVERT_GJV_TO_LJV_TMP where           \n" +
                " ACCOUNTING_UNIT_ID= ? and                                        \n" +
                " ACCOUNTING_FOR_OFFICE_ID = ? and                                 \n" +
                " CASHBOOK_YEAR = ? and                                            \n" +
                " CASHBOOK_MONTH = ? and                                           \n" +
                " VOUCHER_NO = ?                                                   \n";
            ps = con.prepareStatement(sql);
            ps.setInt(1, cmbAcc_UnitCode);
            ps.setInt(2, cmbOffice_code);
            ps.setInt(3, txtCB_Year);
            ps.setInt(4, txtCB_Month);
            ps.setInt(5, txtVoucherNo);
            ps.execute();
        } catch (Exception e) {
            System.out.println(e);
        }


        /**
         *  Post Centage
         */

        try {

            int i = 1;
            int error_code = 0;

            for (i = 1; i <= seq; i++) {
                cs =
  con.prepareCall("{call FAS_CONVERT_GJV_LJV_TMP_PROC( ?,?,?,?,?,?,?,?,?,?,?,?,? ) }");
                cs.setInt(1, cmbAcc_UnitCode);
                cs.setInt(2, cmbOffice_code);
                cs.setInt(3, txtCB_Month);
                cs.setInt(4, txtCB_Year);
                cs.setInt(5, txtVoucherNo);
                cs.setInt(6,
                          Integer.parseInt(request.getParameter("txtSLNo" + i)));
                cs.setInt(7,
                          Integer.parseInt(request.getParameter("txtAccountHeadCode" +
                                                                i)));
                cs.setString(8, request.getParameter("txtCRDRInd" + i));
                cs.setInt(9,
                          Integer.parseInt(request.getParameter("cmbSL_type" +
                                                                i)));
                cs.setInt(10,
                          Integer.parseInt(request.getParameter("cmbSL_Code" +
                                                                i)));
                cs.setInt(11,
                          Integer.parseInt(request.getParameter("txtAmount" +
                                                                i)));
                cs.setString(12, update_user);
                cs.registerOutParameter(13, java.sql.Types.NUMERIC);
                cs.execute();
                error_code = cs.getInt(13);
                System.out.println("Error Code ---->" + error_code);
                error_code++;

            } // End For Loop

            error_code--;


            if (error_code == 0) {
                con.commit();
                con.setAutoCommit(true);
                sendMessage(response, "Back to Sub ledger Details", "OK");
                return;
            } else {
                con.rollback();
                sendMessage(response, "Voucher Details Changes Not Saved ",
                            "OK");
            }

        } catch (SQLException e) {
            System.out.println(e);
        }

    }


    private void sendMessage(HttpServletResponse response, String msg,
                             String bType) {
        try {
            String url =
                "org/Library/jsps/MessengerOkBack.jsp?message=" + msg +
                "&button=" + bType;
            response.sendRedirect(url);
        } catch (IOException e) {
            System.out.println("Excep" + e);
        }
    }
}
