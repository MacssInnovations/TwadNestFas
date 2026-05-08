package Servlets.HR.HR1.Masters.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.*;

import javax.servlet.*;
import javax.servlet.http.*;

import java.util.*;

public class EduSpec_Servlet extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";
    private Connection connection = null;
    private Statement statement = null;
    private ResultSet results = null;
    private PreparedStatement ps = null;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        System.out.println("inside servlet calling");
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

    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {


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

        response.setContentType(CONTENT_TYPE);
        String strCommand = "";
        int ret_code = 0;
        String xml = "";
        int strSpecId = 0;
        String strSpecDesc = "";
        response.setContentType("text/xml");
        PrintWriter pw = response.getWriter();
        response.setHeader("Cache-Control", "no-cache");
        try {
            strCommand = request.getParameter("command");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {

            strSpecDesc = request.getParameter("SpecDesc");
            // strSpecId= Integer.parseInt(request.getParameter("SpecId"));
        } catch (Exception e) {
            System.out.println("in getting values desc**** " + e);
        }
        try {

            // strSpecDesc = request.getParameter("SpecDesc");
            strSpecId = Integer.parseInt(request.getParameter("SpecId"));
        } catch (Exception e) {
            System.out.println("in getting values id**** " + e);
        }

        if (strCommand.equalsIgnoreCase("Delete")) {
            xml = "<response><command>Delete</command>";
            try {
                CallableStatement pstmt =
                    connection.prepareCall("{call EDUSPECDEL(?)}");
                System.out.println(pstmt);

                pstmt.setInt(1, strSpecId);
                pstmt.executeUpdate();
                xml = xml + "<flag>success</flag>";
                pstmt.close();
            } catch (SQLException e) {
                ret_code = e.getErrorCode();
                System.err.println(ret_code + e.getMessage());
                xml = xml + "<flag>failure</flag>";
            }

            xml = xml + "</response>";
        }

        else if (strCommand.equalsIgnoreCase("Update")) {
            xml = "<response><command>Update</command>";
            try {
                CallableStatement pstmt =
                    connection.prepareCall("{call EDUSPECUPDATE(?,?)}");
                System.out.println(pstmt);
                pstmt.setInt(1, strSpecId);
                pstmt.setString(2, strSpecDesc);
                pstmt.executeUpdate();
                xml = xml + "<flag>success</flag>";
                pstmt.close();
            } catch (SQLException e) {
                ret_code = e.getErrorCode();
                System.err.println(ret_code + e.getMessage());
                xml = xml + "<flag>failure</flag>";

            }

            xml = xml + "</response>";
        }

        else if (strCommand.equalsIgnoreCase("Add")) {
            xml = "<response><command>Add</command>";
            try {
                CallableStatement pstmt =
                    connection.prepareCall("{call EDUSPECID(?,?)}");
                System.out.println(pstmt);
                pstmt.setString(1, strSpecDesc);
                pstmt.registerOutParameter(2, Types.INTEGER);
                pstmt.executeUpdate();
                xml = xml + "<flag>success</flag>";
                int Specid = pstmt.getInt(2);
                System.out.println(Specid);
                xml = xml + "<SpecId>" + Specid + "</SpecId>";
                pstmt.close();
            } catch (SQLException e) {
                ret_code = e.getErrorCode();
                System.err.println(ret_code + e.getMessage());
                xml = xml + "<flag>failure</flag>";

            }

            xml = xml + "</response>";
        }

        System.out.println("xml is : " + xml);
        pw.write(xml);
        pw.flush();
        pw.close();

    }
}
