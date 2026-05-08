package Servlets.SysAdmin.servlets;

import java.text.SimpleDateFormat;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.PrintWriter;
import java.io.IOException;

import java.sql.*;

import java.util.*;


public class Activate_User extends HttpServlet {
    private Connection connection = null;
    private Statement statement = null;
    private ResultSet results = null;
    private PreparedStatement ps = null;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        /// opening connection to the database
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

//            ConnectionString =
//                    strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
//                    ":" + strsid.trim();
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
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {

        String LoginId = "";
        int login_enabled = 0;
        String strCommand = "";
        String xml = "";
        PrintWriter pw = response.getWriter();
        response.setContentType("text/xml");
        response.setHeader("cache-control", "no-cache");
        HttpSession session = request.getSession(false);
        String userid = (String)(session.getAttribute("userid"));

        try {
            strCommand = request.getParameter("command");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (strCommand.equals("LoginUid")) {
            try {

                LoginId = request.getParameter("txtUserId");
                int profile = 0;
                ps =
  connection.prepareStatement("select USER_CATEGORY_ID from SEC_MST_USERS where USER_ID=?");
                ps.setString(1, LoginId);
                results = ps.executeQuery();
                if (results.next()) {
                    profile = results.getInt("USER_CATEGORY_ID");
                }
                if (profile == 1) {
                    ps =
  connection.prepareStatement("select EMPLOYEE_ID , LOGIN_ENABLED from SEC_MST_USERS where USER_ID=?");
                    ps.setString(1, LoginId);
                    results = ps.executeQuery();
                    int empid = 0;

                    if (results.next()) {
                        xml = "<response><command>LoginUid</command>";
                        empid = results.getInt("EMPLOYEE_ID");
                        login_enabled = results.getInt("LOGIN_ENABLED");
                        xml = xml + "<empid>" + empid + "</empid>";
                        xml =
 xml + "<login_enabled>" + login_enabled + "</login_enabled>";

                        ps =
  connection.prepareStatement("select Employee_Name||decode(employee_initial,null,' ','.'||employee_initial) Employee_Name from hrm_mst_employees where employee_id=?");
                        ps.setInt(1, empid);
                        results = ps.executeQuery();
                        if (results.next()) {
                            xml =
 xml + "<EmpName>" + results.getString("Employee_Name") + "</EmpName>";
                        }


                        xml = xml + "<flag>success</flag>";
                    } else {
                        xml =
 "<response><command>LoginUid</command><flag>NoRecord</flag>";
                    }

                } else {
                    xml = "<response><command>LoginUid</command>";
                    xml = xml + "<empid>" + 0 + "</empid>";
                    xml = xml + "<login_enabled>" + 0 + "</login_enabled>";
                    ps =
  connection.prepareStatement("select USER_NAME||decode(USER_INITIAL,null,' ','.'||USER_INITIAL) Employee_Name from SEC_MST_OTHER_USERS_PROFILE where USER_ID=?");
                    ps.setString(1, LoginId);
                    results = ps.executeQuery();
                    if (results.next()) {
                        xml =
 xml + "<EmpName>" + results.getString("Employee_Name") + "</EmpName>";
                    }
                    xml = xml + "<flag>success</flag>";

                }

            } catch (Exception e) {
                System.out.println("Exception in Login" + e);
                xml =
 "<response><command>LoginUid</command><flag>failure</flag>";
            }

        } else if (strCommand.equals("Add")) {
            try {
                xml = "<response><command>LoginAdd</command>";
                String txtLEnable = request.getParameter("txtLEnable");
                LoginId = request.getParameter("txtUserId");

                String sqladd =
                    "update SEC_MST_USERS set LOGIN_ENABLED=? where USER_ID=?";
                ps = connection.prepareStatement(sqladd);
                ps.setString(1, txtLEnable);
                ps.setString(2, LoginId);
                int j = ps.executeUpdate();

                if (j >= 1) {
                    xml = xml + "<flag>success</flag>";
                    connection.commit();
                } else {
                    System.out.println("error in updation");
                    xml = xml + "<flag>failure</flag>";
                }
            } catch (Exception e) {
                System.out.println("Error in Changing Login Enabled");
                xml =
 "<response><command>LoginAdd</command><flag>failure</flag>";
            }
        }
        xml = xml + "</response>";
        System.out.println("xml is : " + xml);
        pw.write(xml);
        pw.flush();
        pw.close();

    } //doget


}// class

