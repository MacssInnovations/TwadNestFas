package Servlets.PMS.PMS1.ContractorsInfoSys.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.Timestamp;

import java.text.SimpleDateFormat;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class NewReqRegn_Servlet_EDIT_old extends HttpServlet {

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {

        Connection connection = null;
        Statement statement = null;
        PreparedStatement ps = null;

        // opening connection to the database
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


        PrintWriter pw = response.getWriter();
        response.setContentType("text/html");
        response.setHeader("cache-control", "no-cache");
        HttpSession session = request.getSession(false);

        System.out.println("servlet called for insertion and updation");


        //Declaring the Variables
        int txtOffID = 0, txtResNo = 0, txtClass = 0, year = 0;
        int txtContID = 0; // changes...
        //java.sql.Date  txtDate=null,txtDate_Upto=null;
        String txtContName = "", txtadd = "", txtPhone = "", txtEmail =
            "", State = "", txtAlias = "";
        try {

            try {
                txtContID =
                        Integer.parseInt(request.getParameter("txtContID")); // ..

            } catch (Exception e) {
                System.out.println("exception in getting txtContID");
            }
            System.out.println("txtContID:" + txtContID);

            txtContName = request.getParameter("txtContName");
            System.out.println("txtContName:" + txtContName);

            txtadd = request.getParameter("txtadd");
            System.out.println("txtadd:" + txtadd);

            txtPhone = request.getParameter("txtPhone");
            System.out.println("txtPhone:" + txtPhone);

            txtEmail = request.getParameter("txtEmail");
            System.out.println("txtEmail:" + txtEmail);

            State = request.getParameter("State");
            System.out.println("State:" + State);

            txtAlias = request.getParameter("txtAlias");
            System.out.println("txtAlias:" + txtAlias);

            String regdate = request.getParameter("txtDate");
            String[] str = regdate.split("/");
            year = Integer.parseInt(str[2]);
            System.out.println("Year :" + year);

            txtOffID = Integer.parseInt(request.getParameter("txtOffID"));
            System.out.println("txtOffID:" + txtOffID);

            txtResNo = Integer.parseInt(request.getParameter("txtResNo"));
            System.out.println("txtResNo:" + txtResNo);

            txtClass = Integer.parseInt(request.getParameter("txtClass"));
            System.out.println("txtClass:" + txtClass);


        } catch (Exception e) {
            System.out.println("exce **** " + e);
        }

        //first date field for Date_creation field in the form
        String dateString1 = request.getParameter("txtDate");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date d1;
        try {
            d1 = dateFormat.parse(dateString1);
            dateFormat.applyPattern("yyyy-MM-dd");
            dateString1 = dateFormat.format(d1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        java.sql.Date date1 = java.sql.Date.valueOf(dateString1);
        System.out.println(date1);

        java.text.SimpleDateFormat sdf =
            new java.text.SimpleDateFormat("dd/MM/yyyy");
        String DateTo = sdf.format(date1);
        System.out.println(DateTo);


        //Second Date field for Renewal due on
        String dateString2 = request.getParameter("txtDate_Upto");
        System.out.println(dateString2);
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date d2;
        try {
            d2 = dateFormat1.parse(dateString2);
            dateFormat1.applyPattern("yyyy-MM-dd");
            dateString2 = dateFormat1.format(d2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        java.sql.Date date2 = java.sql.Date.valueOf(dateString2);
        System.out.println(date2);
        java.text.SimpleDateFormat sdf1 =
            new java.text.SimpleDateFormat("dd/MM/yyyy");
        String DateTo1 = sdf1.format(date2);
        System.out.println(DateTo1);

        try {

            String sql =
                "update PMS_CONT_REQUEST_REGN  set " + "DATE_OF_REGN=?,CONTRACTOR_NAME=?,ADDRESS=?,PHONE=?,EMAIL=?,REGN_CLASS_ID=?," +
                "REGN_STATE_COVERAGE=?,REGN_VALID_UPTO=?,REGN_ALIAS_CODE=?,UPDATED_BY_USER_ID=?," +
                "UPDATED_DATE=?,CONTRACTOR_ID=? where OFFICE_ID=? and REGN_YEAR=? and REGN_SLNO=? ";
            System.out.println(sql);
            try {

                session = request.getSession(false);
                String updatedby = (String)session.getAttribute("UserId");
                long l = System.currentTimeMillis();
                Timestamp ts = new Timestamp(l);

                ps = connection.prepareStatement(sql);

                ps.setDate(1, date1);
                ps.setString(2, txtContName);
                ps.setString(3, txtadd);
                ps.setString(4, txtPhone);
                ps.setString(5, txtEmail);
                ps.setInt(6, txtClass);
                ps.setString(7, State);
                System.out.println("date2......." + date2);
                ps.setDate(8, date2);
                ps.setString(9, txtAlias);
                ps.setString(10, updatedby);
                ps.setTimestamp(11, ts);
                ps.setInt(12, txtContID);
                ps.setInt(13, txtOffID);
                ps.setInt(14, year);
                ps.setInt(15, txtResNo);
                ps.executeUpdate();
                System.out.println("success");


                pw.write("<html>");
                pw.write("<head></head>");
                pw.write("<body>");
                pw.write("<table border=\"1\" width=\"92%\" bgcolor=\"yellow\" align=\"center\"><tr>");
                pw.write("<td><center><b>Record Updated Successfully</center></b></td></tr></table>");

                pw.print("<center><input type=\"button\" onClick=\"window.open(\'" +
                         request.getContextPath() +
                         "/org/PMS/PMS1/ContractorsInfoSys/jsps/PMS_ReqNewRegn_EDIT.jsp\',\'_parent\','');\" value=\"Back\"/>");
                pw.write("<input type='button' value='Exit' onClick=window.close();></center>");

                pw.write("</body></html>");
            }


            catch (Exception s) {
                System.out.println("Exception:" + s);


                pw.write("<html>");
                pw.write("<head></head>");
                pw.write("<body>");
                pw.write("<table border=\"1\" width=\"92%\" bgcolor=\"yellow\" align=\"center\"><tr>");
                pw.write("<td><center><b>Sorry Record not inserted</center></b></td></tr></table>");
                pw.print("<center><input type=\"button\" onClick=\"history.go(-1)\" value=\"Back\"/>");
                pw.write("<input type='button' value='Exit' onClick=window.close();></center>");

                pw.write("</body></html>");

            }

        } catch (Exception e3) {
            System.out.println("exce ****2 " + e3);

            pw.write("<html>");
            pw.write("<head></head>");
            pw.write("<body>");
            pw.write("<table border=\"1\" width=\"92%\" bgcolor=\"yellow\" align=\"center\"><tr>");
            pw.write("<td><center><b>Sorry Record not Updated</center></b></td></tr></table>");
            pw.print("<center><input type=\"button\" onClick=\"history.go(-1)\" value=\"Back\"/>");
            pw.write("<input type='button' value='Exit' onClick=window.close();></center>");

            pw.write("</body></html>");

        }


        pw.close();

    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        System.out.println("hi..");
    }
}
