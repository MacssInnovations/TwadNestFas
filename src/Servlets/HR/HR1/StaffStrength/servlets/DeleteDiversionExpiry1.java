package Servlets.HR.HR1.StaffStrength.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.text.SimpleDateFormat;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class DeleteDiversionExpiry1 extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        response.setContentType(CONTENT_TYPE);


        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");

            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }
        Connection connection = null;
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        ResultSet results = null;
        ResultSet res = null;

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

            ConnectionString =
                    strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
                    ":" + strsid.trim();

            Class.forName(strDriver.trim());
            connection =
                    DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                                                strdbpassword.trim());
        } catch (Exception e) {
            System.out.println("Exception in Connection:" + e);
        }


        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        String office = request.getParameter("txtOffice_Id1");
        System.out.println("Office id is:" + office);
        int office_id = 0, fromofficeid = 0, toofficeid = 0, noofpostdiverted =
            0, postdiverted = 0, orderid = 0, slno1 = 0;
        ;
        if (office != "null") {
            office_id = Integer.parseInt(office);
            System.out.println("after conver off is:" + office_id);
        }

        String order = request.getParameter("txtOrderId");
        System.out.println("order id is:" + order);
        if (order != "null") {
            orderid = Integer.parseInt(order);
            System.out.println("after conv order is:" + orderid);
        }

        String slno = request.getParameter("txtExpOrderId1");
        System.out.println("slno is:" + slno);
        String txtExDate = request.getParameter("txtExOrderDate");
        String txtoptExpired = request.getParameter("optExpired");
        String remarks = request.getParameter("txtExRemarks");
        System.out.println("exdate is:" + txtExDate);

        System.out.println("remarks is:" + remarks);
        java.sql.Date extensiondate = null;
        java.sql.Date exupto = null;
        if (slno != "null") {
            slno1 = Integer.parseInt(slno);
            System.out.println("after slno is:" + slno1);
        }
        if (txtExDate != "") {
            String dateString = txtExDate;
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date d;
            try {
                d = dateFormat.parse(dateString.trim());
                dateFormat.applyPattern("yyyy-MM-dd");
                dateString = dateFormat.format(d);
                extensiondate = java.sql.Date.valueOf(dateString);
                System.out.println("Extension Date After Converting:" +
                                   extensiondate);
            } catch (Exception e) {
                e.printStackTrace();
                //sendMessage(response,"Date of formation is not valid.<br>","back");
            }
        }


        try {
            try {
                connection.setAutoCommit(false);
                String sql =
                    "delete from hrm_ss_diversion_expiry_tmp where EXPIRY_ORDER_ID=?";
                ps = connection.prepareStatement(sql);
                System.out.println("after slno is:" + slno1);
                System.out.println("exdate is:" + txtExDate);

                System.out.println("remarks is:" + remarks);
                ps.setInt(1, slno1);
                ps.executeUpdate();
                connection.commit();
                String msg =
                    "Diversion Expiry Details Has been Successfully Deleted. ";
                msg = "<br><br><p><b>" + msg + "</b></p>";
                sendMessage(response, msg, "ok");

            } catch (SQLException e) {
                System.out.println("Exception in Query:" + e);
                connection.rollback();
                String msg = "<br></br>" + e + "</br>";
                sendMessage(response, msg, "ok");
            }

        } catch (Exception e) {

            System.out.println("Exception in Main try:" + e);
            String msg = "<br>" + e + "</br>";
            sendMessage(response, msg, "ok");
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
