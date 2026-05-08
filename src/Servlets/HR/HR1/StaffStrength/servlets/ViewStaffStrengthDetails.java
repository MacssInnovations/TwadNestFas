package Servlets.HR.HR1.StaffStrength.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Statement;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class ViewStaffStrengthDetails extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
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
        response.setHeader("cache-control", "no-cache");
        response.setContentType("text/xml");
        int IntID = 0;
        int found = 0;
        String cmd = "";
        String xml = "";
        cmd = request.getParameter("command");
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
            System.out.println("cmd is   :" + cmd);
            PreparedStatement ps = null;
            PreparedStatement ps1 = null;
            Statement st = null;
            Statement st1 = null;
            ResultSet results = null;
            ResultSet results1 = null;

            if (cmd.equals("nil")) {
                try {
                    IntID = Integer.parseInt(request.getParameter("ID"));

                    System.out.println("command value is---------------" +
                                       cmd);
                } catch (Exception e) {
                    System.out.println("Exception in Number Format" + e);
                }
                try {
                    connection.clearWarnings();

                    System.out.println("servlet  **** CL___>>>>>>>     :");

                    //String sql="select office_name,office_address1,office_address2,city_town_name,district_code,Work_Nature_Desc,Office_Level_Name from com_mst_offices,COM_MST_OFFICE_LEVELS,COM_MST_WORK_NATURE  where com_mst_offices.Office_Level_Id=COM_MST_OFFICE_LEVELS.Office_Level_Id and com_mst_offices.Primary_Work_Id=COM_MST_WORK_NATURE.Work_Nature_Id and Office_Id=?";

                    String sql =
                        "select office_name,office_address1,office_address2,city_town_name,district_code from com_mst_offices where Office_Id=?";

                    ps = connection.prepareStatement(sql);
                    ps.setInt(1, IntID);
                    results = ps.executeQuery();
                    if (results.next()) {
                        /* String xml="<response><flag>success</flag><name>" + results.getString("Office_Name") + "</name><address1>"+results.getString("office_address1")+"</address1><address2>"+results.getString("office_address2")+"</address2>";
                            xml=xml + "<address>" + results.getString("CITY_TOWN_NAME") +"</address><district>"+results.getInt("district_code")+"</district><city>"+results.getString("city_town_name");
                            xml=xml+"</city><level>" + results.getString("Office_Level_Name") + "</level><type>" + results.getString("Work_Nature_Desc") + "</type></response>";
                            */

                        xml =
 "<response><flag>success</flag><name>" + results.getString("Office_Name") +
   "</name><address1>" + results.getString("office_address1") +
   "</address1><address2>" + results.getString("office_address2") +
   "</address2>";
                        xml =
 xml + "<address>" + results.getString("CITY_TOWN_NAME") +
   "</address><district>" + results.getInt("district_code") +
   "</district><city>" + results.getString("city_town_name");
                        xml =
 xml + "</city><level></level><type></type></response>";

                    } else {
                        xml = "<response><flag>failure</flag></response>";
                    }
                    ps.close();

                } catch (SQLException e) {
                    System.out.println("Exception in creating statement:" + e);

                }

            }

            else if (cmd.equals("OfficeLevel")) {

                try {
                    connection.clearWarnings();
                    int Office_Id =
                        Integer.parseInt(request.getParameter("OfficeId"));
                    System.out.println("servlet  **** OfficeLevel in OfficeId:" +
                                       Office_Id);
                    String sql =
                        "select FIN_YEAR,REMARKS from HRM_SS_sanction where office_id=?";
                    ps1 = connection.prepareStatement(sql);
                    ps1.setInt(1, Office_Id);
                    results = ps1.executeQuery();
                    try {
                        xml = "<response><flag>success</flag>";

                        if (results.next()) {

                            xml =
 xml + "<options><finyear>" + results.getString("FIN_YEAR") +
   "</finyear><remarks>" + results.getString("REMARKS") +
   "</remarks></options>";
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
                    System.out.println("Exception in OfficeLevel statement:" +
                                       e);

                }

            } //EndIf(cmd.equals("OfficeLevel"))
            else if (cmd.equals("TableView")) {
                int TemplateId = 0;

                try {
                    System.out.println("hai1" +
                                       request.getParameter("TemplateId"));
                    TemplateId =
                            Integer.parseInt(request.getParameter("TemplateId"));
                    String finyear = request.getParameter("finyear");
                    String sql =
                        "select HRM_mst_post_ranks.post_rank_id,post_rank_name,SANCTIONED_NO_OF_POSTS,remarks,HRM_MST_SERVICE_GROUP.SERVICE_GROUP_ID,SERVICE_GROUP_NAME,diversion_to_other,diversion_from_other,totalstrength,process_flow_status_id from HRM_SS_SANCTION_details,HRM_MST_POST_RANKs,HRM_MST_SERVICE_GROUP where HRM_SS_SANCTION_details.post_rank_id=HRM_MST_POST_RANKs.post_rank_id and HRM_MST_SERVICE_GROUP.SERVICE_GROUP_ID=HRM_MST_POST_RANKs.Service_group_id  and OFFICE_ID=? and fin_year=? order by post_rank_id";
                    PreparedStatement statement =
                        connection.prepareStatement(sql);
                    PreparedStatement ps2 =
                        connection.prepareStatement("select remarks from hrm_ss_sanction_tmp where fin_year=? and office_id=?");
                    ps2.setString(1, finyear);
                    ps2.setInt(2, TemplateId);
                    statement.setInt(1, TemplateId);
                    statement.setString(2, finyear);
                    connection.clearWarnings();
                    try {
                        results = statement.executeQuery();
                        ResultSet res2 = ps2.executeQuery();
                        System.out.println();
                        try {
                            xml = "<response><flag>success</flag>";

                            while (results.next()) {
                                xml =
 xml + "<postrankid>" + results.getInt("post_rank_id") +
   "</postrankid><postrankname>" + results.getString("post_rank_name") +
   "</postrankname><noofpost>" + results.getInt("SANCTIONED_NO_OF_POSTS") +
   "</noofpost><remarks>" + results.getString("remarks") +
   "</remarks><servicegroupid>" + results.getInt("SERVICE_GROUP_ID") +
   "</servicegroupid><servicegroupname>" +
   results.getString("SERVICE_GROUP_NAME") +
   "</servicegroupname><diversionto>" + results.getInt("DIVERSION_TO_OTHER") +
   "</diversionto><diversionfrom>" + results.getInt("DIVERSION_FROM_OTHER") +
   "</diversionfrom><totalstrength>" + results.getInt("TOTALSTRENGTH") +
   "</totalstrength><recordstatus>" +
   results.getString("process_flow_status_id") + "</recordstatus>";
                                if (res2.next()) {
                                    xml =
 xml + "<remarksabove>" + res2.getString("remarks") + "</remarksabove>";
                                }

                                found++;
                            }
                            if (found == 0)
                                xml = "<response><flag>failure</flag>";

                            xml = xml + "</response>";
                        } catch (SQLException e) {
                            System.out.println("Exception in Query TableView" +
                                               e);
                        } finally {
                            results.close();
                        }
                    } catch (SQLException e) {
                        System.out.println("Exception in statement TableView:" +
                                           e);
                    } finally {
                        statement.close();
                    }
                } catch (SQLException e) {
                    System.out.println("Exception in connection TableView:" +
                                       e);
                } finally {
                    //connection.close();
                }
            } //End if(cmd.equals("TableView"))
            else if (cmd.equals("ServiceGroup")) {
                int ServiceGroupId = 0;
                try {

                    ServiceGroupId =
                            Integer.parseInt(request.getParameter("ServiceGroup"));
                    String sql =
                        "select POST_RANK_ID,POST_RANK_NAME from HRM_MST_POST_RANKS where SERVICE_GROUP_ID=? order by post_rank_name";
                    PreparedStatement statement =
                        connection.prepareStatement(sql);
                    statement.setInt(1, ServiceGroupId);
                    connection.clearWarnings();
                    try {
                        results = statement.executeQuery();
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
                            System.out.println("Exception in QueryServiceGroup" +
                                               e);
                        } finally {
                            results.close();
                        }
                    } catch (SQLException e) {
                        System.out.println("Exception in statementServiceGroup:" +
                                           e);
                    } finally {
                        statement.close();
                    }
                } catch (SQLException e) {
                    System.out.println("Exception in connectionServiceGroup:" +
                                       e);
                } finally {
                    //connection.close();
                }
            } //End if(cmd.equals("ServiceGroup"))
            else if (cmd.equals("TableViewTo")) {
                int OfficeTableTo = 0;

                try {
                    System.out.println("to");
                    OfficeTableTo =
                            Integer.parseInt(request.getParameter("OfficeTableTo"));
                    String sql =
                        "select POST_SLNO,HRM_mst_post_ranks.post_rank_id,post_rank_name,POSTS_DIVERSION_TO,REMARKS_DIVERSION_TO,POSTS_DIVERSION_FROM,REMARKS_DIVERSION_FROM,hrm_mst_employment_status.employment_status_id,EMPLOYMENT_STATUS,HRM_MST_SERVICE_GROUP.SERVICE_GROUP_ID,SERVICE_GROUP_NAME from HRM_SS_DIVERSIONS,HRM_MST_POST_RANKs,HRM_MST_SERVICE_GROUP,hrm_mst_employment_status where HRM_SS_DIVERSIONS.post_rank_id=HRM_MST_POST_RANKs.post_rank_id and HRM_MST_SERVICE_GROUP.SERVICE_GROUP_ID=HRM_MST_POST_RANKs.Service_group_id and hrm_mst_employment_status.employment_status_id=HRM_SS_DIVERSIONS.EMPLOYMENT_STATUS_ID and OFFICE_ID=?";
                    PreparedStatement statement =
                        connection.prepareStatement(sql);
                    statement.setInt(1, OfficeTableTo);
                    connection.clearWarnings();
                    try {
                        results = statement.executeQuery();
                        System.out.println();
                        try {
                            xml = "<response><flag>success</flag>";

                            while (results.next()) {
                                xml =
 xml + "<slno>" + results.getInt("POST_SLNO") + "</slno><postrankid>" +
   results.getInt("post_rank_id") + "</postrankid><postrankname>" +
   results.getString("post_rank_name") + "</postrankname><noofpost>" +
   results.getInt("POSTS_DIVERSION_TO") + "</noofpost><remarks>" +
   results.getString("REMARKS_DIVERSION_TO") + "</remarks><noofpostfrom>" +
   results.getInt("POSTS_DIVERSION_FROM") + "</noofpostfrom><remarksfrom>" +
   results.getString("REMARKS_DIVERSION_FROM") +
   "</remarksfrom><employmentstatusid>" +
   results.getString("employment_status_id") +
   "</employmentstatusid><employmentstatusname>" +
   results.getString("EMPLOYMENT_STATUS") +
   "</employmentstatusname><servicegroupid>" +
   results.getInt("SERVICE_GROUP_ID") + "</servicegroupid><servicegroupname>" +
   results.getString("SERVICE_GROUP_NAME") + "</servicegroupname>";
                                found++;
                            }
                            if (found == 0)
                                xml = "<response><flag>failure</flag>";

                            xml = xml + "</response>";
                        } catch (SQLException e) {
                            System.out.println("Exception in Query TableViewTo" +
                                               e);
                        } finally {
                            results.close();
                        }
                    } catch (SQLException e) {
                        System.out.println("Exception in statement TableViewTo:" +
                                           e);
                    } finally {
                        statement.close();
                    }
                } catch (SQLException e) {
                    System.out.println("Exception in connection TableViewTo:" +
                                       e);
                } finally {
                    //connection.close();
                }
            } //End if(cmd.equals("TableViewTo"))

            else if (cmd.equals("SlNo")) {
                int slno = 0;

                try {
                    System.out.println("to");
                    slno = Integer.parseInt(request.getParameter("slno"));
                    System.out.println("slno is" + slno);
                    String sql =
                        "select max(POST_SLNO) from HRM_SS_DIVERSIONS where OFFICE_ID=?";
                    PreparedStatement statement =
                        connection.prepareStatement(sql);
                    statement.setInt(1, slno);
                    connection.clearWarnings();
                    try {
                        results = statement.executeQuery();
                        System.out.println();
                        try {
                            xml = "<response><flag>success</flag>";

                            if (results.next()) {
                                xml =
 xml + "<slno>" + results.getInt(1) + "</slno>";
                                found++;
                            }
                            if (found == 0)
                                xml = "<response><flag>failure</flag>";

                            xml = xml + "</response>";
                        } catch (SQLException e) {
                            System.out.println("Exception in Query SlNo" + e);
                        } finally {
                            results.close();
                        }
                    } catch (SQLException e) {
                        System.out.println("Exception in statement SlNo:" + e);
                    } finally {
                        statement.close();
                    }
                } catch (SQLException e) {
                    System.out.println("Exception in connection SlNo:" + e);
                } finally {
                    //connection.close();
                }
            } //End if(cmd.equals("SlNo"))

        } catch (Exception e) {
            System.out.println("Exception in Base try:" + e);

        }

        System.out.println("Xml is:" + xml);
        out.write(xml);
        out.close();
    }
}
