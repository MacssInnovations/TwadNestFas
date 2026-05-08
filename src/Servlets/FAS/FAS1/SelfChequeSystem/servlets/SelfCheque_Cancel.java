package Servlets.FAS.FAS1.SelfChequeSystem.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;

import java.sql.DriverManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Timestamp;

import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class SelfCheque_Cancel extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        String strCommand = "";
        Connection con = null;
        ResultSet rs = null;
        CallableStatement cs = null, cs1 = null;
        PreparedStatement ps = null;

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
            //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

        }

        try {

            strCommand = request.getParameter("Command");
            System.out.println("assign..here command..." + strCommand);
        }

        catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }

        if (strCommand.equalsIgnoreCase("Add")) {
            Calendar c;
            int txtAcc_HeadCode = 0, cmbAcc_UnitCode = 0, cmbOffice_code =
                0, txtCash_Month_hid = 0, txtCash_year = 0, txtReceipt_No = 0;
            Date txtCrea_date = null;

            String update_user = (String)session.getAttribute("UserId");
            long l = System.currentTimeMillis();
            Timestamp ts = new Timestamp(l);

            
            Enumeration<String> parameterNames = request.getParameterNames();

            while (parameterNames.hasMoreElements()) {
                String paramName = parameterNames.nextElement();
                String paramValue = request.getParameter(paramName);
                System.out.println(paramName + " = " + paramValue);
            }

            try {
                cmbAcc_UnitCode =
                        Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);

            try {
                cmbOffice_code =
                        Integer.parseInt(request.getParameter("cmbOffice_code"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("cmbOffice_code " + cmbOffice_code);

            String[] sd = request.getParameter("txtCrea_date").split("/");
            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
            java.util.Date d = c.getTime();
            txtCrea_date = new Date(d.getTime());
            System.out.println("txtCrea_date " + txtCrea_date);

            try {
                txtReceipt_No =
                        Integer.parseInt(request.getParameter("txtReceipt_No"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtReceipt_No " + txtReceipt_No);

            String sql_del =
                "update FAS_RECEIPT_MASTER set RECEIPT_STATUS='C',UPDATED_BY_USER_ID=?,UPDATED_DATE=? where ACCOUNTING_UNIT_ID=? and " +
                " ACCOUNTING_FOR_OFFICE_ID=? and RECEIPT_DATE=? and RECEIPT_NO=?  ";
            try {
                con.clearWarnings();
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql_del);
                ps.setString(1, update_user);
                ps.setTimestamp(2, ts);
                ps.setInt(3, cmbAcc_UnitCode);
                ps.setInt(4, cmbOffice_code);
                ps.setDate(5, txtCrea_date);
                ps.setInt(6, txtReceipt_No);
                ps.executeUpdate();
                ps.close();

                String sql_qur =
                    "select CASHBOOK_YEAR,CASHBOOK_MONTH " + "from FAS_RECEIPT_MASTER " +
                    "where ACCOUNTING_UNIT_ID=? and " +
                    " ACCOUNTING_FOR_OFFICE_ID=? and RECEIPT_DATE=? and RECEIPT_NO=?  ";
                ps = con.prepareStatement(sql_qur);
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setDate(3, txtCrea_date);
                ps.setInt(4, txtReceipt_No);
                rs = ps.executeQuery();
                if (rs.next()) {
                    txtCash_year = rs.getInt("CASHBOOK_YEAR");
                    txtCash_Month_hid = rs.getInt("CASHBOOK_MONTH");
                }
                rs.close();
                ps.close();


                // here CASHBOOK_YEAR,CASHBOOK_MONTH are same in FAS_RECEIPT_MASTER,FAS_PAYMENT_MASTER,FAS_ACQ_ROLL_DETAILS, So same variable used for all updations
                String paywise_vouNo_Master_Grid[] =
                    request.getParameterValues("paywise_vouNo");

                int txtpayment_No = 0;
                for (int i = 0; i < paywise_vouNo_Master_Grid.length; i++) {
                    txtpayment_No =
                            Integer.parseInt(paywise_vouNo_Master_Grid[i]);
                    sql_del =
                            "update FAS_PAYMENT_MASTER set PAYMENT_STATUS='C',UPDATED_BY_USER_ID=?,UPDATED_DATE=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=?" +
                            "and PAYMENT_DATE=? and VOUCHER_NO=?  ";

                    ps = con.prepareStatement(sql_del);
                    ps.setString(1, update_user);
                    ps.setTimestamp(2, ts);
                    ps.setInt(3, cmbAcc_UnitCode);
                    ps.setInt(4, cmbOffice_code);
                    ps.setDate(5, txtCrea_date);
                    ps.setInt(6, txtpayment_No);
                    ps.executeUpdate();

                }
                ps.close();

                String Grid_Acq_vouNo[] =
                    request.getParameterValues("Acq_vouNo");

                if (Grid_Acq_vouNo != null) {
                    for (int i = 0; i < Grid_Acq_vouNo.length; i++) {

                        txtpayment_No = Integer.parseInt(Grid_Acq_vouNo[i]);
                        sql_del =
                                "update FAS_ACQ_ROLL_DETAILS set VOUCHER_STATUS	='C',UPDATED_BY_USER_ID=?,UPDATED_DATE=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=?" +
                                "and CASHBOOK_YEAR=?  and CASHBOOK_MONTH=?  and VOUCHER_NO=?  ";
                        ps = con.prepareStatement(sql_del);
                        ps.setString(1, update_user);
                        ps.setTimestamp(2, ts);
                        ps.setInt(3, cmbAcc_UnitCode);
                        ps.setInt(4, cmbOffice_code);
                        ps.setInt(5, txtCash_year);
                        ps.setInt(6, txtCash_Month_hid);
                        ps.setInt(7, txtpayment_No);
                        ps.executeUpdate();

                    }
                }
                ps.close();
                System.out.println("after acq");
                String txtReferNO_edit = "", txtRemak_edit = "", txtRefdate =
                    ""; // for cross reference
                Date txtReferDate_edit = null;
                String radAuth_MC = "";
                int txtAuth_By = 0;
                /*
                                  try{txtAuth_By=Integer.parseInt(request.getParameter("txtAuth_By"));}
                                 catch(Exception e){System.out.println("exception"+e );}
                                 */
                System.out.println("txtAuth_By  " + txtAuth_By);
                System.out.println("txtReferNO_edit  " + txtReferNO_edit);
                System.out.println("txtRemak_edit  " + txtRemak_edit);
                System.out.println("txtReferDate_edit " + txtReferDate_edit);

                cs1 =
 con.prepareCall("call FAS_CROSS_REFERENCE_PROC(?::NUMERIC,?,?,?,?,?,?,?,?,?,?::VARCHAR,?,?,?,?,?)");
                cs1.setInt(1, cmbAcc_UnitCode);
                cs1.setInt(2, txtCash_year);
                cs1.setInt(3, txtCash_Month_hid);
                cs1.setInt(4, txtReceipt_No);
                cs1.setInt(5, cmbOffice_code);
                cs1.setDate(6, txtCrea_date);
                cs1.setString(7, "SC");
                cs1.setString(8, txtReferNO_edit);
                cs1.setDate(9, txtReferDate_edit);
                cs1.setString(10, txtRemak_edit);
                cs1.setInt(11, txtAuth_By);
                cs1.setString(12, "insert");
                cs1.registerOutParameter(13, java.sql.Types.NUMERIC);
                cs1.setNull(13,java.sql.Types.NUMERIC);
                cs1.setString(14, update_user);
                cs1.setTimestamp(15, ts);
                cs1.setString(16, radAuth_MC);
                cs1.execute(); // insertion into cross reference table
                int errcode = cs1.getBigDecimal(13).intValue();
                
                System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);
                System.out.println("cmbOffice_code " + cmbOffice_code);
                System.out.println("txtCrea_date " + txtCrea_date);
                System.out.println("txtCash_year " + txtCash_year);
                System.out.println("txtCash_Month_hid " + txtCash_Month_hid);
                System.out.println("SQLCODE:::" + errcode);
                if (errcode != 0) {
                    con.rollback();
                    sendMessage(response,
                                "The Bank Receipt Cancellation Failed ", "ok");
                    return;
                }
                con.commit();
                sendMessage(response,
                            "The Self Cheque cancelled Successfully ", "ok");
            } catch (Exception e) {
                try {
                    con.rollback();
                } catch (SQLException sqle) {
                    System.out.println("Excep" + sqle);
                }
                sendMessage(response, "The Self Cheque Cancellation Failed ",
                            "ok");
                System.out.println("Exception occur due to " + e);
            } finally {
                System.out.println("done");
                try {
                    con.setAutoCommit(true);
                } catch (SQLException sqle) {
                    System.out.println("Excep" + sqle);
                }
            }
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
            System.out.println("error in send msg");
        }
    }
}
