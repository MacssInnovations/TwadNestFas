package Servlets.HR.HR1.StaffStrength.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.*;

import java.text.SimpleDateFormat;

import java.util.ResourceBundle;


public class ValidateUpdateDiversionServlet extends HttpServlet {
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
            0, postdiverted = 0, orderid = 0, Hdivertedto = 0;
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
        String diverto = request.getParameter("txtdivertedto");
        String dateeffective = request.getParameter("txtDEDate");
        String diversionperioddate = request.getParameter("txtDPDate");
        System.out.println("diverto is:" + diverto);

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
            Hdivertedto = Integer.parseInt(diverto);
            System.out.println("Hdivertedto is:" + Hdivertedto);

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

            ps.setInt(1, fromofficeid);
            ps.setInt(2, toofficeid);
            ps.setInt(3, postdiverted);
            ps.setInt(4, noofpostdiverted);
            ps.setDate(5, dateofdiversion1);
            ps.setString(6, remarks);
            ps.setString(7, "FR");
            ps.setDate(8, DiversionEDate);
            ps.setDate(9, DiversionPDate);
            ps.setString(10, financialyear);
            ps.setInt(11, orderid);
            ps.executeUpdate();

            connection.commit();
            try {
                String sql1 =
                    "insert into HRM_SS_DIVERSION_ORDERS(ORDER_OFFICE_ID,DIVERSION_ORDER_ID,DIVERSION_FROM_OFFICE_ID,DIVERSION_POST_RANK_ID,NO_OF_POSTS_DIVERTED,REMARKS,PROCESS_FLOW_STATUS_ID,DIVERSION_ORDER_DATE,DIVERSION_TO_OFFICE_ID,UPDATED_BY_USER_ID,UPDATED_DATE,DIVERSION_EFFECTIVE_DATE,DIVERSION_PERIOD_UPTO,order_finyear) (select ORDER_OFFICE_ID,DIVERSION_ORDER_ID,DIVERSION_FROM_OFFICE_ID,DIVERSION_POST_RANK_ID,NO_OF_POSTS_DIVERTED,REMARKS,PROCESS_FLOW_STATUS_ID,DIVERSION_ORDER_DATE,DIVERSION_TO_OFFICE_ID,UPDATED_BY_USER_ID,UPDATED_DATE,DIVERSION_EFFECTIVE_DATE,DIVERSION_PERIOD_UPTO,order_finyear from HRM_SS_DIVERSION_ORDERS_TMP where DIVERSION_order_id=?)";
                PreparedStatement ps2 = connection.prepareStatement(sql1);
                ps2.setInt(1, orderid);
                int ii = ps2.executeUpdate();

                String sql3 =
                    "select no_of_posts_diverted from hrm_ss_diversion_orders_tmp where DIVERSION_FROM_OFFICE_ID=? and DIVERSION_POST_RANK_ID=?";
                PreparedStatement ps4 = connection.prepareStatement(sql3);
                ps4.setInt(1, fromofficeid);
                ps4.setInt(2, postdiverted);
                ResultSet res1 = ps4.executeQuery();
                int post = 0;
                int result = 0;
                while (res1.next()) {
                    post = post + res1.getInt("no_of_posts_diverted");
                    System.out.println("inner while post is:" + post);
                }
                result = post;
                /*
                      Updating diversion from other detail into sanction strength
                      */
                boolean flag = true;
                System.out.println("diverted to is:" + Hdivertedto);
                String sql2 =
                    "update hrm_ss_sanction_details set DIVERSION_TO_OTHER=? where OFFICE_ID=? and POST_RANK_ID=? and fin_year=?";
                PreparedStatement ps3 = connection.prepareStatement(sql2);
                System.out.println("diverted to result is:" + result);
                System.out.println("from id is:" + fromofficeid);
                System.out.println("postto id is:" + postdiverted);
                ps3.setInt(1, result);
                ps3.setInt(2, fromofficeid);
                ps3.setInt(3, postdiverted);
                ps3.setString(4, financialyear);
                ps3.executeUpdate();


                /*
                         Updating diversion from other detail into sanction strength
                         */

                PreparedStatement psnew =
                    connection.prepareStatement("select * from hrm_ss_sanction_details where OFFICE_ID=? and POST_RANK_ID=? and fin_year=?");
                psnew.setInt(1, toofficeid);
                psnew.setInt(2, postdiverted);
                psnew.setString(3, financialyear);
                System.out.println("aaaaaa" + toofficeid);
                System.out.println("bbbbbb" + postdiverted);
                System.out.println("cccccc" + financialyear);
                ResultSet rsnew = psnew.executeQuery();

                System.out.println("inside check..............");
                while (rsnew.next()) {
                    System.out.println("diverted to is:" + Hdivertedto);
                    String sql4 =
                        "update hrm_ss_sanction_details set DIVERSION_FROM_OTHER=? where OFFICE_ID=? and POST_RANK_ID=? and fin_year=?";
                    PreparedStatement ps5 = connection.prepareStatement(sql4);
                    System.out.println("diverted to result is 2nd:" + result);
                    System.out.println("to office id is 2nd:" + toofficeid);
                    System.out.println("postto id is 2nd:" + postdiverted);
                    ps5.setInt(1, result);
                    ps5.setInt(2, toofficeid);
                    ps5.setInt(3, postdiverted);
                    ps5.setString(4, financialyear);
                    ps5.executeUpdate();
                    flag = false;
                }
                if (flag) {
                    System.out.println("In else part.........");
                    int maxsno = 0, divtoother = 0, diverfromother =
                        0, sanctioned = 0;

                    PreparedStatement psnew1 =
                        connection.prepareStatement("select max(post_Slno) as maxslno from hrm_ss_sanction_details where OFFICE_ID=? and fin_year=?");
                    psnew1.setInt(1, toofficeid);
                    psnew1.setString(2, financialyear);
                    ResultSet rsnew1 = psnew1.executeQuery();
                    if (rsnew1.next()) {
                        maxsno = rsnew1.getInt("maxslno") + 1;
                    } else
                        maxsno = maxsno + 1;

                    System.out.println("max slno is :" + maxsno);
                    //psnew1.close();
                    // rsnew1.close();

                    String sql6 =
                        "insert into hrm_ss_sanction_details(post_Slno,OFFICE_ID,POST_RANK_ID,fin_year,SANCTIONED_NO_OF_POSTS,DIVERSION_TO_OTHER,DIVERSION_FROM_OTHER,TOTALSTRENGTH,PROCESS_FLOW_STATUS_ID) values(?,?,?,?,?,?,?,?,?)";
                    psnew1 = connection.prepareStatement(sql6);
                    psnew1.setInt(1, maxsno);
                    psnew1.setInt(2, toofficeid);
                    psnew1.setInt(3, postdiverted);
                    psnew1.setString(4, financialyear);
                    psnew1.setInt(5, sanctioned);
                    psnew1.setInt(6, divtoother);
                    psnew1.setInt(7, result);
                    psnew1.setInt(8, result);
                    psnew1.setString(9, "CR");
                    psnew1.executeUpdate();
                    System.out.println("No problem...............");

                }
                String msg =
                    "Update Diversion Details Has been Successfully Validated.";
                msg = "<br><br><p><b>" + msg + "</b></p>";
                sendMessage(response, msg, "ok");
            } catch (Exception e) {
                System.out.println("Exception in transfer main table:" + e);
                String msg = "<br><br><p><b>" + e + "</b></p>";
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
