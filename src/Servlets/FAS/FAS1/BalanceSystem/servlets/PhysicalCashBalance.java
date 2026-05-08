package Servlets.FAS.FAS1.BalanceSystem.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.*;

import java.text.SimpleDateFormat;

public class PhysicalCashBalance extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        Connection connection = null;
        Statement statement = null;
        PreparedStatement ps = null;
        ResultSet results = null;
        PreparedStatement ps1 = null;
        ResultSet results1 = null;
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
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
        String userid = (String)session.getAttribute("UserId");
        System.out.println("session id is:" + userid);
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

        //Getting Values from HTML Page
        String command = request.getParameter("Command");
        String Account_unit_Code = request.getParameter("cmbAcc_UnitCode");
        String Office_Code = request.getParameter("cmbOffice_code");
        String CashBook_Year = request.getParameter("txtCB_Year");
        String CashBook_Month = request.getParameter("txtCB_Month");
        String Cash_Verify = request.getParameter("txtcash_verification");
        String Person_Charge = request.getParameter("txtperson_charge");
        String Cash_Verified = request.getParameter("txtcash_verified");
        String Verify_on = request.getParameter("txtverify_date");
        String Purpose = request.getParameter("cmbpurpose");
        String txtcomputed_verifi =
            request.getParameter("txtcomputed_verifi1");
        String Remarks = request.getParameter("txtRemarks");
        int AccountUnitCode = 0;
        int OfficeCode = 0;
        int CashBookYear = 0;
        int CashBookMonth = 0;
        double CashVerify = 0;
        int PersonCharge = 0;
        int CashVerified = 0;
        double ComputedVerifi = 0;

        java.sql.Date VerifyDate = null;

        try {
            AccountUnitCode = Integer.parseInt(Account_unit_Code);
            System.out.println("Account_Unit_Code After is:" +
                               AccountUnitCode);
        } catch (Exception e) {
            System.out.println("Exception in Unit:" + e);
            AccountUnitCode = 0;
        }
        ;
        try {
            OfficeCode = Integer.parseInt(Office_Code);
            System.out.println("Office_Code After is:" + OfficeCode);
        } catch (Exception e) {
            System.out.println("Exception in Office:" + e);
            OfficeCode = 0;
        }
        try {
            CashBookYear = Integer.parseInt(CashBook_Year);
            System.out.println("CashBook_Year After is:" + CashBookYear);
        } catch (Exception e) {
            System.out.println("Exception in Year:" + e);
            CashBookYear = 0;
        }
        try {
            CashBookMonth = Integer.parseInt(CashBook_Month);
            System.out.println("CashBook Month After is:" + CashBookMonth);
        } catch (Exception e) {
            System.out.println("Exception in Month:" + e);
            CashBookMonth = 0;
        }
        try {
            CashVerify = Double.parseDouble(Cash_Verify);
            System.out.println("cash Verify:" + CashVerify);
        } catch (Exception e) {
            System.out.println("Exception in CashVerify:" + e);
            CashVerify = 0;
        }
        try {
            if (Person_Charge != null) {
                PersonCharge = Integer.parseInt(Person_Charge);
                System.out.println("Person Charge:" + PersonCharge);
            }
        } catch (Exception e) {
            System.out.println("Exception in PersonCharge:" + e);
            PersonCharge = 0;
        }
        try {
            if (Cash_Verified != null) {
                CashVerified = Integer.parseInt(Cash_Verified);
                System.out.println("CashVerified:" + CashVerified);
            }
        } catch (Exception e) {
            System.out.println("Exception in CashVerified:" + e);
            CashVerified = 0;
        }
        try {
            ComputedVerifi = Double.parseDouble(txtcomputed_verifi);
            System.out.println("ComputedVerifi:" + ComputedVerifi);

        } catch (Exception e) {
            System.out.println("Exception in Converting Integer:" + e);
        }
        //Date Validation

        if (Verify_on != "" && Verify_on != null) {
            String dateString = Verify_on;
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date d;
            try {
                d = dateFormat.parse(dateString.trim());
                dateFormat.applyPattern("yyyy-MM-dd");
                dateString = dateFormat.format(d);
                VerifyDate = java.sql.Date.valueOf(dateString);
            } catch (Exception e) {
                e.printStackTrace();
                //sendMessage(response,"Date of formation is not valid.<br>","back");
            }
        }

        if (command.equalsIgnoreCase("Add")) {
            try {
                String sqlquery =
                    "insert into physical_cash_balance_details(accounting_unit_id,accounting_for_office_id,cashbook_year,cashbook_month,PHY_CASH_BAL_TIME_VERIFY,EMP_INCHARGE_TIME_VERIFY,VERIFIED_BY_ID,VERIFIED_ON,PURPOSE_OF_VERIFY,COMP_CASH_BAL_TIME_VERIFY,REMARKS,UPDATED_BY_USER_ID,UPDATED_DATE) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
                ps = connection.prepareStatement(sqlquery);
                ps.setInt(1, AccountUnitCode);
                ps.setInt(2, OfficeCode);
                ps.setInt(3, CashBookYear);
                ps.setInt(4, CashBookMonth);
                ps.setDouble(5, CashVerify);
                ps.setInt(6, PersonCharge);
                ps.setInt(7, CashVerified);
                ps.setDate(8, VerifyDate);
                ps.setString(9, Purpose);
                ps.setDouble(10, ComputedVerifi);
                ps.setString(11, Remarks);
                ps.setString(12, userid);
                long ls = System.currentTimeMillis();
                Timestamp ts = new Timestamp(ls);
                ps.setTimestamp(13, ts);
                ps.executeUpdate();

                String msg = "Physical Balance Has been Successfully Inserted";
                msg = msg + "<br><br>";
                sendMessage(response, msg, "ok");
                ps.close();
            } catch (Exception e) {
                System.out.println("Exception in Main:" + e);
                String msg = "Physical Balance Has not been Inserted:" + e;
                msg = msg + "<br><br>";
                sendMessage(response, msg, "ok");
                try {
                    connection.rollback();
                    ps.close();
                } catch (SQLException e1) {
                    System.out.println("catch:" + e1);
                }


            }
        }
        if (command.equalsIgnoreCase("Computed")) {
            response.setContentType("text/xml");
            int year = Integer.parseInt(request.getParameter("Year"));
            int month = Integer.parseInt(request.getParameter("Month"));
            int unit = Integer.parseInt(request.getParameter("Unit"));
            int office = Integer.parseInt(request.getParameter("Office"));
            double totalamount = 0;
            double ramount = 0;
            double pamount = 0;
            String xml = "";
            int count = 0;
            System.out.println("Verify Date is:" + VerifyDate);
            try {
                xml = "<response>";
                ps =
  connection.prepareStatement("select sum(total_amount) as total_amount from fas_receipt_master where receipt_type=? and cashbook_year=? and cashbook_month=? and accounting_unit_id=? and accounting_for_office_id=? and receipt_status=? and RECEIPT_DATE<=? and REMITTANCE_STATUS=?");
                ps.setString(1, "C");
                ps.setInt(2, year);
                ps.setInt(3, month);
                ps.setInt(4, unit);
                ps.setInt(5, office);
                ps.setString(6, "L");
                ps.setDate(7, VerifyDate);
                ps.setString(8, "N");
                results = ps.executeQuery();
                while (results.next()) {
                    ramount = ramount + results.getDouble("total_amount");
                    System.out.println("Amount r is:" + ramount);
                    count++;
                }

                ps1 =
 connection.prepareStatement("select sum(TOTAL_AMOUNT) as total_amount from FAS_PAYMENT_MASTER where PAYMENT_TYPE=? and accounting_unit_id=? " +
                             " and cashbook_year=? and cashbook_month=? and accounting_for_office_id=? and " +
                             " payment_status=? and PAYMENT_DATE<=?");
                ps1.setString(1, "C");
                ps1.setInt(2, unit);
                ps1.setInt(3, year);
                ps1.setInt(4, month);
                ps1.setInt(5, office);
                ps1.setString(6, "L");
                ps1.setDate(7, VerifyDate);
                results1 = ps1.executeQuery();
                while (results1.next()) {
                    pamount = pamount + results1.getDouble("total_amount");
                    System.out.println("Amount p is:" + pamount);
                    count++;
                }
                System.out.println("ramount is:" + ramount);
                System.out.println("pamount is:" + pamount);
                totalamount = (ramount - pamount);
                xml = xml + "<Amount>" + totalamount + "</Amount>";
                if (count != 0) {
                    xml = xml + "<flag>success</flag></response>";
                } else {
                    xml = xml + "<flag>failure</flag></response>";

                }
                ramount = 0;
                pamount = 0;
                totalamount = 0;
            } catch (Exception e) {
                xml = "<response><flag>failure</flag></response>";
                System.out.println("Exception in Computed:" + e);
            }
            out.write(xml);
            System.out.println("xml is:" + xml);

        }

        out.close();
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
