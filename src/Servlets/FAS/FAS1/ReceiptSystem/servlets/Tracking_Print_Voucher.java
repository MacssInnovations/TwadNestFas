package Servlets.FAS.FAS1.ReceiptSystem.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.Date;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.Timestamp;

import java.util.Calendar;
import java.util.GregorianCalendar;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class Tracking_Print_Voucher extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {

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

        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
        String strCommand = "";
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
            ConnectionString =
                    strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
                    ":" + strsid.trim();
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

        String xml = "", cmbSubSystemType = "";
        int cmbAcc_UnitCode = 0, cmbOffice_code = 0;
        Date txtVou_date = null;
        Calendar c;

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

        cmbSubSystemType = request.getParameter("cmbSubSystemType");
        System.out.println("cmbSubSystemType.. " + cmbSubSystemType);


        try {
            String[] sd = request.getParameter("txtVou_date").split("/");
            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
            java.util.Date d = c.getTime();
            txtVou_date = new Date(d.getTime());
            System.out.println("txtVou_date " + txtVou_date);
        } catch (NumberFormatException e) {
            System.out.println("exception" + e);
        }

        if (strCommand.equalsIgnoreCase("load_Voucher_No")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);

            xml = "<response><command>load_Voucher_No</command>";
            String QueryType = "";
            if (cmbSubSystemType.equalsIgnoreCase("CR"))
                QueryType =
                        "select RECEIPT_NO as VOUCHER_NO from FAS_RECEIPT_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and RECEIPT_DATE=? and RECEIPT_TYPE='C' and CREATED_BY_MODULE='CR' and RECEIPT_STATUS!='C' ";
            else if (cmbSubSystemType.equalsIgnoreCase("BR"))
                QueryType =
                        "select RECEIPT_NO as VOUCHER_NO from FAS_RECEIPT_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and RECEIPT_DATE=? and RECEIPT_TYPE='B' and CREATED_BY_MODULE='BR' and RECEIPT_STATUS!='C' ";

            QueryType =
                    QueryType + " order by 1"; // here order by specified with column number

            System.out.println("QueryType...." + QueryType);

            try {

                ps = con.prepareStatement(QueryType);
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setDate(3, txtVou_date);
                rs = ps.executeQuery();

                int count = 0;
                while (rs.next()) {

                    xml =
 xml + "<Rec_No>" + rs.getInt("VOUCHER_NO") + "</Rec_No>";
                    count++;
                }
                if (count == 0)
                    xml = xml + "<flag>failure</flag>";
                else
                    xml = xml + "<flag>success</flag>";
                System.out.println("count  " + count);
                ps.close();
                rs.close();
            } catch (Exception e) {
                System.out.println("catch..HERE.in load VOUCHER." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);

        } else if (strCommand.equalsIgnoreCase("load_Voucher_details")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            int txtVoucher_No = 0;
            try {
                txtVoucher_No =
                        Integer.parseInt(request.getParameter("txtVoucher_No"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }

            System.out.println("txtVoucher_No " + txtVoucher_No);

            xml = "<response><command>load_Voucher_details</command>";

            String QueryType = "";
            if (cmbSubSystemType.equalsIgnoreCase("CR"))
                QueryType =
                        "select RECEIVED_FROM as com_value,trim(to_char(TOTAL_AMOUNT,'99999999999999.99'))  as amt from FAS_RECEIPT_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and RECEIPT_DATE=? and RECEIPT_NO=? and RECEIPT_TYPE='C' and CREATED_BY_MODULE='CR' and RECEIPT_STATUS!='C' ";
            else if (cmbSubSystemType.equalsIgnoreCase("BR"))
                QueryType =
                        "select RECEIVED_FROM as com_value,trim(to_char(TOTAL_AMOUNT,'99999999999999.99'))  as amt  from FAS_RECEIPT_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and RECEIPT_DATE=? and RECEIPT_NO=? and RECEIPT_TYPE='B' and CREATED_BY_MODULE='BR' and RECEIPT_STATUS!='C' ";
            System.out.println("QueryType...." + QueryType);
            try {
                ps = con.prepareStatement(QueryType);
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setDate(3, txtVou_date);
                ps.setInt(4, txtVoucher_No);
                rs = ps.executeQuery();

                int count = 0;
                if (rs.next()) {
                    xml =
 xml + "<com_value>" + rs.getString("com_value") + "</com_value>";
                    xml = xml + "<amt>" + rs.getString("amt") + "</amt>";
                    count++;
                }
                if (count == 0)
                    xml = xml + "<flag>failure</flag>";
                else
                    xml = xml + "<flag>success</flag>";
                System.out.println("count  " + count);
                ps.close();
                rs.close();
            } catch (Exception e) {
                System.out.println("catch..HERE.in load VOUCHER." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);

        }
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
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

        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;

        String strCommand = "";
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
            ConnectionString =
                    strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
                    ":" + strsid.trim();
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

        String cmbSubSystemType = "";
        int cmbAcc_UnitCode = 0, cmbOffice_code = 0;
        int txtVoucher_No = 0;
        String radPrint_Type = "";
        Date txtVou_date = null;
        Calendar c;

        String update_user = (String)session.getAttribute("UserId");


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

        cmbSubSystemType = request.getParameter("cmbSubSystemType");
        System.out.println("cmbSubSystemType.. " + cmbSubSystemType);


        try {
            String[] sd = request.getParameter("txtVou_date").split("/");
            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
            java.util.Date d = c.getTime();
            txtVou_date = new Date(d.getTime());
            System.out.println("txtVou_date " + txtVou_date);
        } catch (NumberFormatException e) {
            System.out.println("exception" + e);
        }


        try {
            txtVoucher_No =
                    Integer.parseInt(request.getParameter("txtVoucher_No"));
        } catch (NumberFormatException e) {
            System.out.println("exception" + e);
        }

        System.out.println("txtVoucher_No " + txtVoucher_No);

        radPrint_Type = request.getParameter("radPrint_Type");
        System.out.println("radPrint_Type" + radPrint_Type);

        if (strCommand.equalsIgnoreCase("Add")) {
            try {
                con.clearWarnings();
                con.setAutoCommit(false);

                int no_of_copies = 1;
                ps =
  con.prepareStatement("select max(NO_OF_COPY) as max_copies from FAS_TRACK_PRINT_RECEIPT where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and VOUCHER_DATE=? and VOUCHER_NO=?");
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setDate(3, txtVou_date);
                ps.setInt(4, txtVoucher_No);
                rs = ps.executeQuery();
                if (rs.next()) {
                    System.out.println("max_copies" + rs.getInt("max_copies"));

                    if (rs.getInt("max_copies") == 0)
                        radPrint_Type = "O";
                    else if (rs.getInt("max_copies") == 1 &&
                             radPrint_Type.equalsIgnoreCase("O")) {
                        sendMessage(response, "Original already issued...",
                                    "ok");
                        return;
                    } else
                        no_of_copies = rs.getInt("max_copies") + 1;
                }


                rs.close();
                ps.close();
                ps =
  con.prepareStatement("insert into FAS_TRACK_PRINT_RECEIPT(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,VOUCHER_DATE," +
                       "VOUCHER_NO,PRINT_TYPE,NO_OF_COPY,PRINTED_DATE,UPDATED_BY_USER_ID,UPDATED_DATE) " +
                       "values(?,?,?,?,?,?,now(),?,now())");
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setDate(3, txtVou_date);
                ps.setInt(4, txtVoucher_No);
                ps.setString(5, radPrint_Type);
                ps.setInt(6, no_of_copies);
                //ps.setDate(7,txtVou_date);
                ps.setString(7, update_user);
                //ps.setTimestamp(8,ts);
                ps.executeUpdate();
                con.commit();
                System.out.println("success inserted");
                sendMessage(response, "Tracking has done", "ok");
            } catch (Exception e) {
                try {
                    con.rollback();
                } catch (Exception e1) {
                }
                System.out.println("Errore " + e);
                sendMessage(response, "Tracking Unsuccessful", "ok");
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
            System.out.println("Excep" + e);
        }
    }
}
