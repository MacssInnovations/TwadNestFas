package Servlets.HR.HR1.StaffStrength.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.*;

import java.text.SimpleDateFormat;

import java.util.ResourceBundle;


public class DeleteUpdateDiversionServlet extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {

        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                /* response.setContentType("text/xml");
                response.setHeader("Cache-Control","no-cache");
               String xml="<response><command>session</command><flag>failure</flag><flag>Session already closed.</flag></response>";
               System.out.println(xml);
                out.println(xml);
                out.close();
                return;*/
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

        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        String office = request.getParameter("txtOffice_Id1");
        int office_id = 0, fromofficeid = 0, toofficeid = 0, noofpostdiverted =
            0, postdiverted = 0, orderid = 0;
        if (office != "null") {
            office_id = Integer.parseInt(office);
        }
        String order = request.getParameter("txtOrderId");
        System.out.println("order id is:" + order);
        if (order != "null") {
            orderid = Integer.parseInt(order);
        }
        String financialyear = request.getParameter("cmbFinancialYear");
        String fromoffice = request.getParameter("txtHdiversionfromoffice");
        String tooffice = request.getParameter("txtHdiversiontooffice");
        String nameofpost = request.getParameter("cmbPostRank");
        String postcategory = request.getParameter("cmbPostCategory");
        String noofpost = request.getParameter("txtPostDiverted");
        String dateofdiversion = request.getParameter("txtDoD");
        String remarks = request.getParameter("txtRemarks");
        java.sql.Date dateofdiversion1 = null;

        if (dateofdiversion != "") {
            String dateString = dateofdiversion;
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date d;
            try {
                d = dateFormat.parse(dateString.trim());
                dateFormat.applyPattern("yyyy-MM-dd");
                dateString = dateFormat.format(d);
                dateofdiversion1 = java.sql.Date.valueOf(dateString);
            } catch (Exception e) {
                e.printStackTrace();
                //sendMessage(response,"Date of formation is not valid.<br>","back");
            }
        }

        try {
            fromofficeid = Integer.parseInt(fromoffice);
            toofficeid = Integer.parseInt(tooffice);
            noofpostdiverted = Integer.parseInt(noofpost);
            postdiverted = Integer.parseInt(nameofpost);
        } catch (NumberFormatException e) {

        }
        System.out.println("officeId is:" + office_id);
        System.out.println("finyear is:" + financialyear);
        System.out.println("from officeId is:" + fromofficeid);
        System.out.println("to officeId is:" + toofficeid);
        System.out.println("noof post diverted:" + noofpostdiverted);
        System.out.println("post diverted:" + postdiverted);
        System.out.println("remarks is:" + remarks);
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


            String sql =
                "delete from HRM_SS_DIVERSION_ORDERS_TMP where diversion_order_id=?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, orderid);
            int ii = ps.executeUpdate();
            if (ii >= 1) {
                String msg =
                    "Update Diversion Details Has been Successfully Deleted.";
                msg = "<br><br><p><b>" + msg + "</b></p>";
                sendMessage(response, msg, "ok");
            } else {
                String msg = "Record Not Found";
                msg = "<br><br><p><b>" + msg + "</b></p>";
                sendMessage(response, msg, "ok");
            }
        } catch (Exception e) {
            System.out.println("Exception in Connection:" + e);
            String msg = "<br><br><p><b>" + e + "</b></p>";
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
