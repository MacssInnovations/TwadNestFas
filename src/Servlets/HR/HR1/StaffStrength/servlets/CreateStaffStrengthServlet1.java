package Servlets.HR.HR1.StaffStrength.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Timestamp;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class CreateStaffStrengthServlet1 extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
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
        String Template_Name = request.getParameter("txtTemplate_Name");
        String OfficeLevel = request.getParameter("cmbOfficeLevel");
        String slno[] = request.getParameterValues("sno");
        String ServiceGroup[] = request.getParameterValues("servicegroup");
        String PostRank[] = request.getParameterValues("postrank");
        String PostCategory[] = request.getParameterValues("postcategory");
        String NoPost[] = request.getParameterValues("noofpost");
        String Remarks[] = request.getParameterValues("remarks");
        int tempid = 0;
        Connection connection = null;
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

            //Creation for Integer Array
            int slno1[] = new int[slno.length];
            int servicegroup1[] = new int[ServiceGroup.length];
            int postrank1[] = new int[ServiceGroup.length];
            int nopost1[] = new int[ServiceGroup.length];
            for (int i = 0; i < ServiceGroup.length; i++) {

                servicegroup1[i] = Integer.parseInt(ServiceGroup[i]);
                postrank1[i] = Integer.parseInt(PostRank[i]);
                nopost1[i] = Integer.parseInt(NoPost[i]);


            }
            try {
                System.out.println("Add");

                connection.clearWarnings();
                connection.setAutoCommit(false);
                String sql1 =
                    "select max(SS_TEMPLATE_ID) from HRM_SS_TEMPLATES";
                PreparedStatement ps = connection.prepareStatement(sql1);
                ResultSet rs1 = ps.executeQuery();
                if (rs1.next()) {
                    tempid = rs1.getInt(1);
                    System.out.println("TempId" + tempid);
                }

                ps.close();
                tempid = tempid + 1;
                System.out.println("TempId************" + tempid);
                String sql2 =
                    "insert into HRM_SS_TEMPLATES(SS_TEMPLATE_ID,SS_TEMPLATE_NAME,OFFICE_LEVEL_ID,UPDATED_BY_USER_ID,UPDATED_DATE) values(?,?,?,?,?)";
                ps = connection.prepareStatement(sql2);
                ps.setInt(1, tempid);
                ps.setString(2, Template_Name);
                ps.setString(3, OfficeLevel);
                ps.setString(4, "unknown");
                long l = System.currentTimeMillis();
                Timestamp ts = new Timestamp(l);
                ps.setTimestamp(5, ts);
                ps.executeUpdate();
                ps.close();

                String sql =
                    "insert into HRM_SS_TEMPLATE_DETAILS(SS_TEMPLATE_ID,POST_RANK_ID,NO_OF_POSTS,REMARKS,UPDATED_BY_USER_ID,UPDATED_DATE) values(?,?,?,?,?,?)";
                PreparedStatement statement = connection.prepareStatement(sql);

                for (int j = 0; j < ServiceGroup.length; j++) {
                    statement.setInt(1, tempid);
                    statement.setInt(2, postrank1[j]);
                    //statement.setString(3,PostCategory[j]);
                    statement.setInt(3, nopost1[j]);
                    statement.setString(4, Remarks[j]);
                    statement.setString(5, "unknown");
                    statement.setTimestamp(6, ts);
                    statement.executeUpdate();

                }

                connection.commit();
                String msg =
                    "Staff Strength Details Has been Successfully Inserted.<br>Template ID  is  : " +
                    tempid;
                msg = "<br><br><p><b>" + msg + "</b></p>";
                sendMessage(response, msg, "ok");

            } catch (Exception e) {
                System.out.println("Exception in connection:" + e);
                connection.rollback();
                String msg = "<br><br><p><b>" + e + "</b></p>";
                sendMessage(response, msg, "ok");
            }

            finally {
                //connection.close();
            }


        } catch (Exception e) {
            System.out.println("Exception" + e);
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
