package Servlets.HR.HR1.StaffStrength.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class CreateStaffStrengthServlet extends HttpServlet {
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
        response.setContentType("text/xml");
        String mode = request.getParameter("command");
        System.out.println("mode is:" + mode);
        String xml = "";
        int found = 0;
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


            if (mode.equals("ServiceGroup")) {
                int ServiceGroupId = 0;
                try {
                    System.out.println("hai1");
                    ServiceGroupId =
                            Integer.parseInt(request.getParameter("ServiceGroup"));
                    String sql =
                        "select POST_RANK_ID,POST_RANK_NAME from HRM_MST_POST_RANKS where SERVICE_GROUP_ID=? order by post_rank_name";
                    String sql2 =
                        "select distinct c.service_group_name,a.post_rank_id,a.post_rank_name,b.service_group_id from hrm_mst_post_ranks a,hrm_mst_designations b,hrm_mst_service_group c where a.post_rank_id=b.post_rank_id and b.service_group_id=c.service_group_id and c.service_group_id=? order by a.post_rank_name";
                    PreparedStatement statement =
                        connection.prepareStatement(sql2);
                    statement.setInt(1, ServiceGroupId);
                    connection.clearWarnings();
                    try {
                        ResultSet results = statement.executeQuery();
                        System.out.println();
                        try {
                            xml = "<response><flag>success</flag>";
                            String PostRank = request.getParameter("PostRank");
                            if (PostRank != null) {
                                xml =
 xml + "<PostRank>" + PostRank + "</PostRank>";
                            }
                            while (results.next()) {
                                xml =
 xml + "<options><id>" + results.getInt("POST_RANK_ID") + "</id><name>" +
   results.getString("POST_RANK_NAME") + "</name></options>";
                                found++;
                            }
                            if (found == 0)
                                xml = "<response><flag>failure</flag>";

                            xml = xml + "</response>";
                        } catch (SQLException e) {
                            System.out.println("Exception in Query" + e);
                        } finally {
                            results.close();
                        }
                    } catch (SQLException e) {
                        System.out.println("Exception in statement:" + e);
                    } finally {
                        statement.close();
                    }
                } catch (SQLException e) {
                    System.out.println("Exception in connection:" + e);
                } finally {
                    //connection.close();
                }
            } //End if(mode.equals("ServiceGroup"))


        } catch (Exception e) {
            System.out.println("Exception in Connection" + e);
        }


        System.out.println("Xml is : " + xml);

        response.setHeader("cache-control", "no-cache");
        out.write(xml);
        out.close();
    }
}
