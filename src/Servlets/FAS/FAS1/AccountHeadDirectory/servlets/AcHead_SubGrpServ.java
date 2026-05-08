package Servlets.FAS.FAS1.AccountHeadDirectory.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.*;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class AcHead_SubGrpServ extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";


    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        System.out.println("within init config");


    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);
        Connection connection = null;
        Statement statement = null;
        ResultSet results = null;
        PreparedStatement ps = null;

        ResultSet rs1 = null;
        PreparedStatement ps1 = null;
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
            System.out.println("creating statement:" + connection);
            try {
                statement = connection.createStatement();
                connection.clearWarnings();
            } catch (SQLException e) {
                System.out.println("Exception in creating statement:" + e);
            }
        } catch (Exception e) {
            System.out.println("Exception in openeing connection:" + e);
        }
        HttpSession session = request.getSession(false);
        try {

            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                return;
            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }

        String strCommand = "";
        String xml = "";
        String strSubCode = "";
        String strSubName = "";
        response.setContentType("text/xml");
        PrintWriter pw = response.getWriter();
        response.setHeader("Cache-Control", "no-cache");
        String update_user = (String)session.getAttribute("UserId");
        long l = System.currentTimeMillis();
        Timestamp ts = new Timestamp(l);
        try {
            strCommand = request.getParameter("command");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            strSubCode = request.getParameter("txtSubGrpCode");
            strSubName = request.getParameter("txtSubGrpDesc");
        } catch (Exception e) {
            System.out.println("in getting values **** " + e);
        }

        if (strCommand.equalsIgnoreCase("Delete")) {
            xml = "<response><command>Delete</command>";
            strSubCode = request.getParameter("txtSubGrpCode");
            System.out.println(strSubCode);
            try {
                ps1 =
 connection.prepareStatement("delete from COM_MST_SUB_HEADS where SUB_HEAD_CODE=?");
                ps1.setString(1, strSubCode);
                ps1.executeUpdate();
                xml =
 xml + "<flag>success</flag><SubCode>" + strSubCode + "</SubCode>";
                ps1.close();
            } catch (Exception e) {
                System.out.println("exception in the delete" + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }

        else if (strCommand.equalsIgnoreCase("Update")) {
            xml = "<response><command>Update</command>";

            try {
                ps1 =
 connection.prepareStatement("update COM_MST_SUB_HEADS set SUB_HEAD_DESC=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=?  where SUB_HEAD_CODE=?");
                ps1.setString(4, strSubCode);
                ps1.setString(1, strSubName);
                ps1.setString(2, update_user);
                ps1.setTimestamp(3, ts);
                ps1.executeUpdate();
                xml = xml + "<flag>success</flag>";
                ps.close();
            } catch (Exception e) {
                System.out.println("exception in the update" + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }

        else if (strCommand.equalsIgnoreCase("Load")) {
            String sxml = "<response><command>Load</command>";

            try {
                String sql =
                    "SELECT STATE_CODE FROM COM_MST_STATE WHERE STATE_CODE=?";
                try {
                    ps = connection.prepareStatement(sql);
                    ps.setString(1, strSubCode);
                    results = ps.executeQuery();
                    int i = 0;
                    while (results.next()) {
                        i++;
                    }
                    if (i == 0) {
                        xml = sxml + "<flag>failure</flag>";
                    } else {

                        xml = sxml + "<flag>success</flag>";
                    }

                } catch (Exception ae) {
                    System.out.println("Exception is:the check" + ae);

                }
            } catch (Exception e) {
                System.out.println("exce ****2 vv" + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }

        else if (strCommand.equalsIgnoreCase("Add")) {
            xml = "<response><command>Add</command>";
            int code = 0;
            String code2 = "";
            int code1 = 0;

            strSubName = request.getParameter("txtSubGrpDesc");


            try {
                ps1 =
 connection.prepareStatement("select max(SUB_HEAD_CODE)as b from COM_MST_SUB_HEADS");
                rs1 = ps1.executeQuery();
                int i = 0;

                while (rs1.next()) {
                    code = rs1.getInt("b");
                    System.out.println("max of head code is*****" + code);

                    code1 = code + 1;
                    // code2=code1.toString();


                    System.out.println("after" + code1);
                    i++;
                }
                rs1.close();
                ps1.close();
                try {

                    ps =
  connection.prepareStatement("insert into COM_MST_SUB_HEADS(SUB_HEAD_CODE,SUB_HEAD_DESC,UPDATED_BY_USER_ID,UPDATED_DATE) values(?,?,?,?)");
                    ps.setInt(1, code1);
                    ps.setString(2, strSubName);
                    ps.setString(3, update_user);
                    ps.setTimestamp(4, ts);
                    ps.executeUpdate();
                    xml =
 xml + "<flag>success</flag><code>" + code1 + "</code>";
                    ps.close();
                } catch (Exception e) {
                    System.out.println("exception in the add" + e);
                    xml = xml + "<flag>failure</flag>";
                }
            }

            catch (Exception ae) {
                System.out.println("Exception is: in the insert" + ae);

            }
            xml = xml + "</response>";
        }


        System.out.println("xml is : " + xml);
        pw.write(xml);
        pw.flush();
        pw.close();

    }
}
