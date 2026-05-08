package Servlets.HR.HR1.Masters.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.*;

import java.util.*;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class EmpStatus_Servlet extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";
    private Connection connection = null;
    private Statement statement = null;
    private ResultSet results = null;
    private PreparedStatement ps = null;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        System.out.println("within init config");

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
        String xml = "";
        int ret_code = 0;
        int strEmpId = 0;
        String strEmpDesc = "";
        response.setContentType("text/xml");
        PrintWriter pw = response.getWriter();
        response.setHeader("Cache-Control", "no-cache");
        try {
            strCommand = request.getParameter("command");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {

            strEmpDesc = request.getParameter("EmpDesc");
            strEmpId = Integer.parseInt(request.getParameter("EmpId"));
        } catch (Exception e) {
            System.out.println("in getting values **** " + e);
        }

        if (strCommand.equalsIgnoreCase("Delete")) {
            xml = "<response><command>Delete</command>";
            try {
                CallableStatement pstmt =
                    connection.prepareCall("{call EMPDEL(?)}");
                System.out.println(pstmt);

                pstmt.setInt(1, strEmpId);
                pstmt.executeUpdate();
                xml =
 xml + "<flag>success</flag><EmpId>" + strEmpId + "</EmpId>";
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
                    connection.prepareCall("{call EMPUPDATE(?,?)}");
                System.out.println(pstmt);
                pstmt.setInt(1, strEmpId);
                pstmt.setString(2, strEmpDesc);

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
                    connection.prepareCall("{call EMPID(?,?)}");
                System.out.println(pstmt);
                pstmt.setString(1, strEmpDesc);
                pstmt.registerOutParameter(2, Types.INTEGER);
                pstmt.executeUpdate();
                xml = xml + "<flag>success</flag>";
                int EmpId = pstmt.getInt(2);
                System.out.println(EmpId);
                xml = xml + "<EmpId>" + EmpId + "</EmpId>";
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
