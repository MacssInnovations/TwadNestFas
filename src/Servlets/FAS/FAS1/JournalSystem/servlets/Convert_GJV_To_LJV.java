package Servlets.FAS.FAS1.JournalSystem.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class Convert_GJV_To_LJV extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }


    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {

        /**
         * Set Content Type
         */
        response.setContentType(CONTENT_TYPE);
        String CONTENT_TYPE = "text/html; charset=windows-1252";
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();

        /**
         * Variables Declaration
         */
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
        int txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));
        System.out.println("txtCB_Year-->" + txtCB_Year);

        /** Get Cashbook Month  */
        int txtCB_Month =
            Integer.parseInt(request.getParameter("txtCB_Month"));
        System.out.println("txtCB_Month-->" + txtCB_Month);


        /**
         * Make Connection as false
         */
        try {
            con.setAutoCommit(false);
        } catch (SQLException e) {
            System.out.println(e);
        }


        /**
         * Get Parameter Values
         */
        String sel[] = request.getParameterValues("sel");
        String voucher_no[] = request.getParameterValues("voucher_no");
        String voucher_date[] = request.getParameterValues("voucher_date");
        String Old_Journal_Type[] = request.getParameterValues("Journal_Type");
        String New_Journal_Type[] =
            request.getParameterValues("cmbCategoryCode");
        String Remak[] = request.getParameterValues("Remak");


        /**
         * Variables Declaration
         */
        String[] sd = null;
        Calendar c = null;
        java.util.Date d = null;
        Date txtVoucherDate = null;
        int errcode = 0;
        int empty_check = 0;
        int jour_sl_check = 0;

        System.out.println("voucher Lenght--" + voucher_no.length);


        for (int i = 0; i < voucher_no.length; i++) {

            for (int j = 0; j < sel.length; j++) {

                System.out.println(" i == j " + voucher_no[i] + "  " + sel[j]);

                if (voucher_no[i].equalsIgnoreCase(sel[j])) {
                    System.out.println("values -->" + sel[j]);
                    System.out.println("Voucher_no -->" + voucher_no[i]);
                    System.out.println("voucher_date[] -->" + voucher_date[i]);
                    System.out.println("Old_Journal_Type[]-->" +
                                       Old_Journal_Type[i]);
                    System.out.println("New_Journal_Type[]-->" +
                                       New_Journal_Type[i]);
                    System.out.println("Remarks-->" + Remak[i]);


                    /** Get Challan Date */
                    if (!voucher_date[i].equalsIgnoreCase("")) {
                        sd = voucher_date[i].split("/");
                        c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                        d = c.getTime();
                        txtVoucherDate = new Date(d.getTime());

                        System.out.println("txtVoucherDate-->" +
                                           txtVoucherDate);
                    }

                    try {
                        cs =
  con.prepareCall("{call FAS_CONVERT_GJV_TO_LJV(?,?,?,?,?,?,?,?,?,?,?,?,?) }");
                        cs.setInt(1, cmbAcc_UnitCode);
                        cs.setInt(2, cmbOffice_code);
                        cs.setInt(3, txtCB_Year);
                        cs.setInt(4, txtCB_Month);
                        cs.setInt(5, Integer.parseInt(voucher_no[i]));
                        cs.setDate(6, txtVoucherDate);
                        cs.setInt(7, Integer.parseInt(Old_Journal_Type[i]));
                        cs.setInt(8, Integer.parseInt(New_Journal_Type[i]));
                        cs.setString(9, Remak[i]);
                        cs.setString(10, update_user);
                        cs.registerOutParameter(11, java.sql.Types.NUMERIC);
                        cs.registerOutParameter(12, java.sql.Types.NUMERIC);
                        cs.registerOutParameter(13, java.sql.Types.NUMERIC);
                        System.out.println("java.sql.Types.NUMERIC :: "+java.sql.Types.NUMERIC);
                        cs.execute();
                        errcode = cs.getInt(11);
                        empty_check = cs.getInt(12);
                        jour_sl_check = cs.getInt(13);

                        System.out.println("empty_check---->" + empty_check);
                        System.out.println("jour_sl_check----->" +
                                           jour_sl_check);


                        if (empty_check == 143) {
                            sendMessage(response,
                                        "Conversion Failed. Sub Ledger Type and Sub Ledger Code are not available ",
                                        "OK");
                            return;
                        }

                        if (jour_sl_check == 144) {
                            sendMessage(response,
                                        "Conversion Failed. One of Sub Ledger Type with Credit Indicator in detail part and New Journal Type Code in general part Should be equal",
                                        "OK");
                            return;
                        }
                        System.out.println("Error Code ---> " + errcode);
                        errcode++;
                        System.out.println("Error After Code ---> " + errcode);
                    } catch (Exception e) {
                        System.out.println(e);
                    }

                }
            }
        }


        errcode--;
        if (errcode == 0) {
            try {
                con.commit();
                con.setAutoCommit(true);
                sendMessage(response,
                            "Conversion Done Successfully. Do SubLedger Posting Progressively for correct balance updation ",
                            "OK");
            } catch (SQLException e) {
                System.out.println(e);
            }
            return;
        } else {
            sendMessage(response, "Conversion Failed ", "OK");
            return;
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
