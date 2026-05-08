package Servlets.FAS.FAS1.FundReceiptSystem.servlets;

import Servlets.FAS.FAS1.CommonControls.servlets.Com_CashBook1;

import Servlets.Security.classes.UserProfile;

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
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class Fund_Receipt_Create_byOffice extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {


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
         * Variables Declaration
         */

        Connection con = null;
        ResultSet rs = null;
        CallableStatement cs = null;
        CallableStatement cs1 = null;
        PreparedStatement ps = null;
        String Remittance_Type = null;
        String xml = "";
        String strCommand = "";


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
         * Get Command Parameter
         */
        try {

            strCommand = request.getParameter("Command");
            System.out.println("assign..here command..." + strCommand);
        }

        catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }


        /**
         * If Command Paramter is 'ADD'
         */
        if (strCommand.equalsIgnoreCase("Add")) {

            /**
            * Set Content Type
            */
            String CONTENT_TYPE = "text/html; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            xml = "<response><command>Add</command>";

            /**
            * Variables Declaration
            */
            Calendar c;
            int cmbAcc_UnitCode = 0, cmbOffice_code = 0, txtCash_Month_hid =
                0, txtCash_year = 0, txtVoucher_No = 0;
            int txtCash_Acc_code = 0, txtBankId = 0, txtBranchId = 0;
            long txtBankAccountNo = 0;

            double txtAmount = 0;
            String txtRemarks = "";
            Date txtCrea_date = null, txtReferenceDate = null;
            String txtReferenceNo = "";

            /**
            * Get User ID
            */
            String update_user = (String)session.getAttribute("UserId");
            int txtCreditAccCode = 0, txtSubBankId = 0, txtSubBranchId = 0;

            long txtSubBankAccountNo = 0;
            String txtCheque_DD = "", txtCheque_DD_NO = "";
            Date txtCheque_DD_date = null;

            long l = System.currentTimeMillis();
            Timestamp ts = new Timestamp(l);


            /**
            *  Get Accounting Unit ID
            */
            try {
                txtCash_Acc_code =
                        Integer.parseInt(request.getParameter("txtCash_Acc_code"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtCash_Acc_code " + txtCash_Acc_code);


            /**
            *
            */
            try {
                txtBankId =
                        Integer.parseInt(request.getParameter("txtBankId"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtBankId " + txtBankId);

            try {
                txtBranchId =
                        Integer.parseInt(request.getParameter("txtBranchId"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtBranchId " + txtBranchId);

            try {
                txtBankAccountNo =
                        Long.parseLong(request.getParameter("txtBankAccountNo"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtBankAccountNo " + txtBankAccountNo);

            /*
          try{txtSub_Office_code=Integer.parseInt(request.getParameter("txtSub_Office_code"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            System.out.println("txtSub_Office_code "+txtSub_Office_code);
          */

            try {
                txtCreditAccCode =
                        Integer.parseInt(request.getParameter("txtCreditAccCode"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtCreditAccCode " + txtCreditAccCode);

            try {
                txtSubBankId =
                        Integer.parseInt(request.getParameter("txtSubBankId"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtSubBankId " + txtSubBankId);

            try {
                txtSubBranchId =
                        Integer.parseInt(request.getParameter("txtSubBranchId"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtSubBranchId " + txtSubBranchId);

            try {
                txtSubBankAccountNo =
                        Long.parseLong(request.getParameter("txtSubBankAccountNo"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtSubBankAccountNo " + txtSubBankAccountNo);


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
                txtVoucher_No =
                        Integer.parseInt(request.getParameter("txtVoucher_No"));
            } catch (Exception e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtVoucher_No " + txtVoucher_No);

            try {
                txtAmount =
                        Double.parseDouble(request.getParameter("txtAmount"));
            } catch (Exception e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtAmount " + txtAmount);

            txtRemarks = request.getParameter("txtRemarks");
            System.out.println("txtRemarks " + txtRemarks);


            /*
            radRecType=request.getParameter("radRecType");
            System.out.println("radRecType "+radRecType);
           */

            txtReferenceNo = request.getParameter("txtReferenceNo");
            System.out.println("txtReferenceNo " + txtReferenceNo);

            if (!request.getParameter("txtReferenceDate").equalsIgnoreCase("")) {
                sd = request.getParameter("txtReferenceDate").split("/");
                c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                d = c.getTime();
                txtReferenceDate = new Date(d.getTime());
            }
            System.out.println("txtReferenceDate " + txtReferenceDate);


            // DD and Cheque Option

            try {
                txtCheque_DD = request.getParameter("txtCheque_DD");
                Remittance_Type = txtCheque_DD;
            } catch (Exception e) {
                System.out.println("Failed to get option such as cheque or dd or ecs ");
            }

            // Get DD / Cheque Number

            try {
                txtCheque_DD_NO = request.getParameter("txtCheque_DD_NO");

            } catch (Exception e) {
                System.out.println("Failed to get Cheque or DD Number");
            }

            // Get DD / Cheque Date

            try {
                sd = request.getParameter("txtCheque_DD_date").split("/");
                c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                d = c.getTime();
                txtCheque_DD_date = new Date(d.getTime());
                System.out.println("txtCheque_DD_date " + txtCheque_DD_date);
            } catch (Exception e) {
                System.out.println("Failed to Get Cheque or DD Date ");
            }


            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

            /** Get Receipt Creation Date */
            String Receipt_Creation_Date =
                request.getParameter("txtCrea_date");

            /** Call Com_CashBook Servlet for Calculating Cash Book Month and Year */
            Com_CashBook1 cb = new Com_CashBook1();

            /** Assign Cashbook Year and Month to year_month Variable */
            String year_month = cb.cb_date(Receipt_Creation_Date).toString();

            /** Split Cash Book Year and Month */
            String[] ym = year_month.split("/");

            /** Assign Year and Month */
            txtCash_year = Integer.parseInt(ym[0]);
            txtCash_Month_hid = Integer.parseInt(ym[1]);

            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

            try {
                con.clearWarnings();
                con.setAutoCommit(false);
                System.out.println("inside proc");

                cs =
  con.prepareCall("call FAS_FUND_REC_BYOFFICE_PROC(?::numeric,?::numeric,?,?::numeric,?::numeric,?::numeric,?::numeric,?::numeric,?::numeric,?::numeric,?,?::numeric,?,?,?::numeric,?::numeric,?::numeric,?::numeric,?,?,?,?,?::numeric,?,?)");
                cs.setInt(1, cmbAcc_UnitCode);
                cs.setInt(2, cmbOffice_code);
                cs.setDate(3, txtCrea_date);
                cs.setInt(4, txtCash_year);
                cs.setInt(5, txtCash_Month_hid);
                cs.setInt(6, txtVoucher_No);
                cs.setInt(7, txtCash_Acc_code);
                cs.setInt(8, txtBankId);
                cs.setInt(9, txtBranchId);
                cs.setLong(10, txtBankAccountNo);
                cs.setString(11, txtRemarks);
                cs.setDouble(12, txtAmount);
                cs.setString(13, txtReferenceNo);
                cs.setDate(14, txtReferenceDate);
                cs.setInt(15, txtCreditAccCode);
                cs.setInt(16, txtSubBankId);
                cs.setInt(17, txtSubBranchId);
                cs.setLong(18, txtSubBankAccountNo);
                cs.setString(19, txtCheque_DD);
                cs.setString(20, txtCheque_DD_NO);
                cs.setDate(21, txtCheque_DD_date);
                cs.setString(22, "insert");
                cs.registerOutParameter(23, java.sql.Types.NUMERIC);
                cs.registerOutParameter(6, java.sql.Types.NUMERIC);
                cs.setNull(23, java.sql.Types.NUMERIC);
                cs.setNull(6, java.sql.Types.NUMERIC);
                cs.setString(24, update_user);
                cs.setTimestamp(25, ts);
                cs.execute();
//                txtVoucher_No = cs.getInt(6);
//                int errcode = cs.getInt(23);
                txtVoucher_No = cs.getBigDecimal(6).intValue();
                int errcode = cs.getBigDecimal(23).intValue();
                
                System.out.println("SQLCODE:::" + errcode);
                if (errcode != 0) {
                    System.out.println("redirect");
                    sendMessage(response,
                                "The Fund Receipt Transaction failed ", "ok");
                    return;
                } else {

                    /**
                        * Auto Generation of Bank Remittance for ECS Transaction
                        */

                    System.out.println("Remittance_Type----><--" +
                                       Remittance_Type);
                    int Verified_Authority = 0;


                    if (Remittance_Type.equalsIgnoreCase("E")) {
                        UserProfile empProfile =
                            (UserProfile)session.getAttribute("UserProfile");
                        Verified_Authority = empProfile.getEmployeeId();
                        System.out.println("Verified_Authority::" +
                                           Verified_Authority);

                        System.out.println("inside E ");

                        cs1 =
// con.prepareCall("{call FAS_ECS_REMITTANCE_PROC(?,?,?,?,?,?,?,?,?,?)}");
//
//                        cs1.setInt(1, cmbAcc_UnitCode);
//                        cs1.setInt(2, cmbOffice_code);
//                        cs1.setInt(3, txtCash_year);
//                        cs1.setInt(4, txtCash_Month_hid);
//                        cs1.setDate(5, txtCrea_date);
//                        cs1.setString(6, "F-OFF");
//                        cs1.setDouble(7, txtAmount);
//                        cs1.setInt(8, Verified_Authority);
//                        cs1.setString(9, update_user);
//                        cs1.registerOutParameter(10, java.sql.Types.NUMERIC);
//                        cs1.execute();
//                        int err_code = cs1.getInt(10);
                        		con.prepareCall("call FAS_ECS_REMITTANCE_PROC(?::numeric,?::numeric,?::numeric,?::numeric,?,?,?::numeric,?::numeric,?,?)");
                        cs1.setInt(1, cmbAcc_UnitCode);
                        cs1.setInt(2, cmbOffice_code);
                        cs1.setInt(3, txtCash_year);
                        cs1.setInt(4, txtCash_Month_hid);
                        cs1.setDate(5, txtCrea_date);
                        cs1.setString(6, "B");
                        cs1.setDouble(7, txtAmount);
                        cs1.setInt(8, Verified_Authority);
                        cs1.setString(9, update_user);
                        cs1.registerOutParameter(10, java.sql.Types.VARCHAR);
                        cs1.setNull(10, java.sql.Types.VARCHAR);
                        cs1.execute();
                        //int err_code = cs1.getInt(10);
                        String err_code = cs1.getString(10);
                        if (err_code.equals("0")) {
                            con.rollback();
                            sendMessage(response,
                                        "The Fund Receipt Creation Failed ",
                                        "ok");
                            return;
                        }

                    }
                    System.out.println("b4 commit");
                    con.commit();
                    sendMessage(response,
                                "The Fund Receipt Transaction Voucher Number '" +
                                txtVoucher_No +
                                "' has been Created Successfully ", "ok");

                }

            } catch (Exception e) {
                try {
                    con.rollback();
                } catch (SQLException sqle) {
                    System.out.println("excepton in rollback");
                }
                sendMessage(response, "The Fund Receipt TransactionFailed ",
                            "ok");
                System.out.println("Exception occur due to " + e);
            } finally {
                System.out.println("done");
                try {
                    con.setAutoCommit(true);
                } catch (SQLException sqle) {
                    System.out.println("exception in autocommit");
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
            System.out.println("error in send message");
        }
    }


}
