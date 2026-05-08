package Servlets.SysAdmin.servlets;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.PrintWriter;
import java.io.IOException;

import java.sql.*;

import java.util.ResourceBundle;

public class ServletRoles extends HttpServlet {
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
            System.out.println("within init try config");
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
        response.setContentType(CONTENT_TYPE);
        String strCommand = "";
        String xml = "";
        PrintWriter pw = response.getWriter();
        response.setContentType("text/xml");
        response.setHeader("cache-control", "no-cache");
        HttpSession session = request.getSession(true);
        int strRId = 0;
        String strRName = "";
        String category = "";
        int ret_code = 0;

        try {
            strCommand = request.getParameter("command");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            strRId = Integer.parseInt(request.getParameter("txtRId"));
        } catch (Exception e) {
            System.out.println("In getting roll id values " + e);
        }
        try {
            strRName = request.getParameter("txtRName");
            category = request.getParameter("category");
        } catch (Exception e) {
            System.out.println("In getting roll name values " + e);
        }
        if (strCommand.equalsIgnoreCase("Add")) {
            xml = "<response><command>Add</command>";
            try {
//                CallableStatement pstmt =
//                    connection.prepareCall("{call CREATEROLEID(?,?,?)}");
//                System.out.println(pstmt);
//                System.out.println("Category" + category);
//                pstmt.setString(1, strRName);
//                pstmt.setString(2, category);
//                pstmt.registerOutParameter(3, Types.INTEGER);
//                pstmt.executeUpdate();
//                xml = xml + "<flag>success</flag>";
//                int RoleId = pstmt.getInt(3);
//                System.out.println(RoleId);
//                xml = xml + "<RoleId>" + RoleId + "</RoleId>";
//                pstmt.close();
                
                
                
                String Major=request.getParameter("major");
                CallableStatement pstmt = 
                    connection.prepareCall("call CREATEROLEID(?,?,?::numeric,?)");
               // System.out.println(pstmt);
               // System.out.println("Category"+category);
                pstmt.setString(1, strRName);
                pstmt.setString(2, category);
                pstmt.registerOutParameter(3, Types.INTEGER);
                pstmt.setNull(3,java.sql.Types.INTEGER);
                pstmt.setString(4, Major);
                pstmt.executeUpdate();
                xml = xml + "<flag>success</flag>";
                int RoleId = pstmt.getInt(3);
              //  System.out.println(RoleId);
                xml = xml + "<RoleId>" + RoleId + "</RoleId>";
                pstmt.close();
                
                
            } catch (SQLException e) {
                ret_code = e.getErrorCode();
                System.err.println(ret_code + e.getMessage());
                xml = xml + "<flag>failure</flag>";

            }

            xml = xml + "</response>";

        }

        else if (strCommand.equalsIgnoreCase("Delete")) {
            // read the parameter id

            xml = "<response><command>Delete</command>";
            try {
                CallableStatement pstmt =
                    connection.prepareCall("call DELROLEID(?:numeric)");
                System.out.println(pstmt);

                pstmt.setInt(1, strRId);
                pstmt.executeUpdate();
                xml =
 xml + "<flag>success</flag><RoleId>" + strRId + "</RoleId>";
                pstmt.close();
            } catch (SQLException e) {
                ret_code = e.getErrorCode();
                System.err.println(ret_code + e.getMessage());
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";

        } else if (strCommand.equalsIgnoreCase("Update")) {
            System.out.println("This is within Update command");
            xml = "<response><command>Update</command>";
            try {
                CallableStatement pstmt =
                    connection.prepareCall("call UPDATEROLEID(?::numeric,?,?)");
                System.out.println(pstmt);
                pstmt.setInt(1, strRId);
                pstmt.setString(2, strRName);
                pstmt.setString(3, category);
                pstmt.executeUpdate();
                xml = xml + "<flag>success</flag>";
                pstmt.close();
            } catch (SQLException e) {
                ret_code = e.getErrorCode();
                System.err.println(ret_code + e.getMessage());
                xml = xml + "<flag>failure</flag>";

            }
            xml = xml + "</response>";
        } else if (strCommand.equalsIgnoreCase("Load")) {
            try {
                results =
                        statement.executeQuery("SELECT * from SEC_MST_ROLES order by Role_Id");
                xml = "<response><command>Load</command>";
                xml = xml + "<flag>success</flag>";
                while (results.next()) {

                    xml =
 xml + "<Role_Id>" + results.getInt("Role_Id") + "</Role_Id>";
                    xml =
 xml + "<Role_Name>" + results.getString("Role_Name") + "</Role_Name>";
                    if (results.getString("role_category") != null) {
                        if (results.getString("role_category").equalsIgnoreCase("I")) {
                            xml = xml + "<category>Individual</category>";
                        } else if (results.getString("role_category").equalsIgnoreCase("C")) {
                            xml = xml + "<category>Common</category>";
                        }

                    } else {
                        xml = xml + "<category>Notmentioned</category>";
                    }
                }
                results.close();
                response.setHeader("cache-control", "no-cache");
            } catch (Exception e1) {
                System.out.println("Exception is" + e1);
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
