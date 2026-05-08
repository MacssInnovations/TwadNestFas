package Servlets.HR.HR1.Masters.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.*;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class PostRank_Servlet extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";


    public void init(ServletConfig config) throws ServletException {
        super.init(config);


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

        Connection connection = null;
        Statement statement = null;
        ResultSet results = null;
        PreparedStatement ps = null;

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

            try {
                statement = connection.createStatement();
                connection.clearWarnings();
            } catch (SQLException e) {
                System.out.println("Exception in creating statement:" + e);
            }
        } catch (Exception e) {
            System.out.println("Exception in openeing connection:" + e);
        }

        response.setContentType(CONTENT_TYPE);
        String strCommand = "";
        String xml = "";
        int ret_code = 0;
        int strPRId = 0;
        String strPRDesc = "";
        String strPRSDesc = "";
        int strPRORDseq = 0;
        response.setContentType("text/xml");
        PrintWriter pw = response.getWriter();
        response.setHeader("Cache-Control", "no-cache");
        try {
            strCommand = request.getParameter("command");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {

            strPRDesc = request.getParameter("PRDesc");
            strPRSDesc = request.getParameter("PRSDesc");
            strPRId = Integer.parseInt(request.getParameter("PRId"));
            strPRORDseq = Integer.parseInt(request.getParameter("PRORDseq"));

            System.out.println(strPRDesc);
            System.out.println(strPRSDesc);
            System.out.println(strPRORDseq);
            System.out.println(strPRId);

        } catch (Exception e) {
            System.out.println("in getting values **** " + e);
        }

        if (strCommand.equalsIgnoreCase("Delete")) {
            xml = "<response><command>Delete</command>";
            try {
                CallableStatement pstmt =
                    connection.prepareCall("{call POSTRDEL(?)}");
                //System.out.println(pstmt);

                pstmt.setInt(1, strPRId);
                pstmt.executeUpdate();
                xml = xml + "<flag>success</flag><PRId>" + strPRId + "</PRId>";
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
                System.out.println("test1");
                CallableStatement pstmt =
                    connection.prepareCall("{call POSTRUPDATE(?,?,?,?)}");
                //System.out.println(pstmt);
                pstmt.setInt(1, strPRId);
                pstmt.setString(2, strPRDesc);
                pstmt.setString(3, strPRSDesc);
                pstmt.setInt(4, strPRORDseq);
                System.out.println("test2");
                pstmt.executeUpdate();
                System.out.println("test3");
                xml = xml + "<flag>success</flag>";
                pstmt.close();
            } catch (SQLException e) {
                ret_code = e.getErrorCode();
                System.err.println(ret_code + e.getMessage());
                xml = xml + "<flag>failure</flag>";

            } catch (Exception e) {
                System.out.println("Error:" + e);
            }

            xml = xml + "</response>";
        }

        else if (strCommand.equalsIgnoreCase("Add")) {
            xml = "<response><command>Add</command>";
            try {
                CallableStatement pstmt =
                    connection.prepareCall("{call POSTRID(?,?,?,?)}");
                System.out.println(pstmt);
                pstmt.setString(1, strPRDesc);
                pstmt.setString(2, strPRSDesc);
                pstmt.setInt(3, strPRORDseq);
                pstmt.registerOutParameter(4, Types.INTEGER);

                System.out.println(strPRDesc);
                System.out.println(strPRSDesc);
                System.out.println(strPRORDseq);

                pstmt.executeUpdate();
                xml = xml + "<flag>success</flag>";
                int PRId = pstmt.getInt(4);
                System.out.println(PRId);
                xml = xml + "<PRId>" + PRId + "</PRId>";
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
