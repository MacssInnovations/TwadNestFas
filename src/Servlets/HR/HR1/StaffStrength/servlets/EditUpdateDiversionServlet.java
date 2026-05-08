package Servlets.HR.HR1.StaffStrength.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.*;

import java.text.SimpleDateFormat;

import java.util.ResourceBundle;


public class EditUpdateDiversionServlet extends HttpServlet {
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
        String order = request.getParameter("txtOrderId");
        int office_id = 0, fromofficeid = 0, toofficeid = 0, noofpostdiverted =
            0, postdiverted = 0, orderid = 0;
        if (office != "null") {
            office_id = Integer.parseInt(office);
        }
        if (order != "null") {
            orderid = Integer.parseInt(order);
        }
        String financialyear = request.getParameter("cmbFinancialYear");
        String fromoffice = request.getParameter("txtdiversionfromoffice");
        String tooffice = request.getParameter("txtdiversiontooffice");
        String nameofpost = request.getParameter("cmbPostRank");
        String postcategory = request.getParameter("cmbPostCategory");
        String noofpost = request.getParameter("txtPostDiverted");
        String dateofdiversion = request.getParameter("txtDoD");
        String remarks = request.getParameter("txtRemarks");
        String dateeffective = request.getParameter("txtDEDate");
        String diversionperioddate = request.getParameter("txtDPDate");
        java.sql.Date dateofdiversion1 = null;
        java.sql.Date DiversionEDate = null;
        java.sql.Date DiversionPDate = null;

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

        if (dateeffective != "") {
            String dateeff = dateeffective;
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date d;
            try {
                d = dateFormat.parse(dateeff.trim());
                dateFormat.applyPattern("yyyy-MM-dd");
                dateeff = dateFormat.format(d);
                DiversionEDate = Date.valueOf((dateeff));
                System.out.println("Diversion Effective Date After Converting:" +
                                   DiversionEDate);
            } catch (Exception e) {
                System.out.println("Exception in Date Conversion:" + e);
            }
        }

        if (diversionperioddate != "") {
            String dateperiod = diversionperioddate;
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date d;
            try {
                d = dateFormat.parse(dateperiod.trim());
                dateFormat.applyPattern("yyyy-MM-dd");
                dateperiod = dateFormat.format(d);
                DiversionPDate = Date.valueOf(dateperiod);
                System.out.println("Diversion period Date After Converting:" +
                                   DiversionPDate);

            } catch (Exception e) {
                System.out.println("Exception in Date Conversion2:" + e);
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
                "update HRM_SS_DIVERSION_ORDERS_TMP set DIVERSION_FROM_OFFICE_ID=?,DIVERSION_TO_OFFICE_ID=?,DIVERSION_POST_RANK_ID=?,NO_OF_POSTS_DIVERTED=?,DIVERSION_Order_DATE=?,REMARKS=?,PROCESS_FLOW_STATUS_ID=?, DIVERSION_EFFECTIVE_DATE=?,DIVERSION_PERIOD_UPTO=?,order_finyear=? where diversion_order_id=?";
            ps = connection.prepareStatement(sql);

            //ps.setString(1,financialyear);
            ps.setInt(1, fromofficeid);
            ps.setInt(2, toofficeid);
            ps.setInt(3, postdiverted);
            ps.setInt(4, noofpostdiverted);
            ps.setDate(5, dateofdiversion1);
            ps.setString(6, remarks);
            ps.setString(7, "MD");
            ps.setDate(8, DiversionEDate);
            ps.setDate(9, DiversionPDate);
            ps.setString(10, financialyear);
            ps.setInt(11, orderid);


            ps.executeUpdate();

            String msg =
                "Update Diversion Details Has been Successfully Updated.";
            msg = "<br><br><p><b>" + msg + "</b></p>";
            sendMessage(response, msg, "ok");
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
