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

public class EditStaffStrengthServlet extends HttpServlet {
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


            if (mode.equals("OfficeLevel")) {
                int ServiceGroupId = 0;
                String OfficeLevel = "";
                try {
                    System.out.println("hai1");
                    OfficeLevel = request.getParameter("OfficeLevel");
                    String sql =
                        "select SS_TEMPLATE_ID,SS_TEMPLATE_NAME from HRM_SS_TEMPLATES where OFFICE_LEVEL_ID=? order by ss_template_id";
                    PreparedStatement statement =
                        connection.prepareStatement(sql);
                    statement.setString(1, OfficeLevel);
                    connection.clearWarnings();
                    try {
                        ResultSet results = statement.executeQuery();
                        System.out.println();
                        try {
                            xml = "<response><flag>success</flag>";

                            while (results.next()) {
                                xml =
 xml + "<options><id>" + results.getInt("SS_TEMPLATE_ID") + "</id><name>" +
   results.getString("SS_TEMPLATE_NAME") + "</name></options>";
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

            if (mode.equals("TempId")) {
                int TemplateId = 0;

                try {
                    System.out.println("hai1");
                    TemplateId =
                            Integer.parseInt(request.getParameter("TemplateId"));
                    String sql =
                        "select SS_TEMPLATE_NAME from HRM_SS_TEMPLATES where SS_TEMPLATE_ID=?";
                    PreparedStatement statement =
                        connection.prepareStatement(sql);
                    statement.setInt(1, TemplateId);
                    connection.clearWarnings();
                    try {
                        ResultSet results = statement.executeQuery();
                        System.out.println();
                        try {
                            xml = "<response><flag>success</flag>";

                            if (results.next()) {
                                xml =
 xml + "<options><name>" + results.getString("SS_TEMPLATE_NAME") +
   "</name></options>";
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
            } //End if(mode.equals("TempIds"))


            else if (mode.equals("TableView")) {
                int TemplateId = 0;

                try {
                    System.out.println("hai1");
                    TemplateId =
                            Integer.parseInt(request.getParameter("TemplateId"));
                    String sql =
                        "select HRM_mst_post_ranks.post_rank_id,post_rank_name,no_of_posts,remarks,hrm_mst_employment_status.employment_status_id,EMPLOYMENT_STATUS,HRM_MST_SERVICE_GROUP.SERVICE_GROUP_ID,SERVICE_GROUP_NAME from HRM_SS_TEMPLATE_DETAILS,HRM_MST_POST_RANKs,HRM_MST_SERVICE_GROUP,hrm_mst_employment_status where HRM_SS_TEMPLATE_DETAILS.post_rank_id=HRM_MST_POST_RANKs.post_rank_id and HRM_MST_SERVICE_GROUP.SERVICE_GROUP_ID=HRM_MST_POST_RANKs.Service_group_id and hrm_mst_employment_status.employment_status_id=HRM_SS_TEMPLATE_DETAILS.EMPLOYMENT_STATUS_ID and ss_template_id=? order by post_rank_id";
                    String sql2 =
                        "select distinct a.post_rank_id,b.post_rank_name,a.no_of_posts,a.remarks,c.service_group_id,c.service_group_name from hrm_ss_template_details a,hrm_mst_post_ranks b,hrm_mst_service_group c,hrm_mst_designations e where a.post_rank_id=b.post_rank_id  and c.service_group_id=e.service_group_id and a.post_rank_id=e.post_rank_id and a.ss_template_id=? order by post_rank_id";
                    PreparedStatement statement =
                        connection.prepareStatement(sql2);
                    statement.setInt(1, TemplateId);
                    connection.clearWarnings();
                    try {
                        ResultSet results = statement.executeQuery();
                        System.out.println();
                        try {
                            xml = "<response><flag>success</flag>";

                            while (results.next()) {
                                xml =
 xml + "<postrankid>" + results.getInt("post_rank_id") +
   "</postrankid><postrankname>" + results.getString("post_rank_name") +
   "</postrankname><noofpost>" + results.getInt("no_of_posts") +
   "</noofpost><remarks>" + results.getString("remarks") +
   "</remarks><servicegroupid>" + results.getInt("SERVICE_GROUP_ID") +
   "</servicegroupid><servicegroupname>" +
   results.getString("SERVICE_GROUP_NAME") + "</servicegroupname>";
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
            } //End if(mode.equals("TableView"))
            else if (mode.equals("ServiceGroup")) {
                int ServiceGroupId = 0;
                try {

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
