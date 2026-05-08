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

public class RequestStaffStrengthServlet extends HttpServlet {
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
            PreparedStatement ps2 = null;
            PreparedStatement ps3 = null;
            PreparedStatement ps4 = null;
            Statement st = null;
            Statement st1 = null;
            ResultSet results = null;
            ResultSet results2 = null;
            ResultSet rs3 = null;
            ResultSet rs4 = null;
            String prevPeriod = "";
            String offid = "";

            int officeid = 0;
            String fin_year = "";
            int postslno = 0;
            int postrankid = 0;
            String empstatusid = "";
            int sancnoofpost = 0;
            int divertoother = 0;
            int diverfromother = 0;
            String remarks = "";
            int totalstregth = 0;
            String processflowstatusid = "";
            String selPeriod = "";
            int cnt = 0;
            if (cmd.equalsIgnoreCase("copyFromPreviousPeriod")) {

                try {
                    offid = request.getParameter("officeid");
                    System.out.println("Office id si:" + offid);
                    prevPeriod = request.getParameter("prePeriod");
                    System.out.println("Previous period is:" + prevPeriod);
                    selPeriod = request.getParameter("selPeriod");
                    System.out.println("selPeriod period is:" + selPeriod);


                    ps2 =
 connection.prepareStatement("select OFFICE_ID,FIN_YEAR,POST_SLNO,POST_RANK_ID,EMPLOYMENT_STATUS_ID," +
                             " SANCTIONED_NO_OF_POSTS,DIVERSION_TO_OTHER,DIVERSION_FROM_OTHER,REMARKS,TOTALSTRENGTH,PROCESS_FLOW_STATUS_ID" +
                             " from hrm_ss_sanction_details_tmp where office_id=? and fin_year=?");
                    ps2.setString(1, offid);
                    ps2.setString(2, prevPeriod);
                    results2 = ps2.executeQuery();
                    while (results2.next()) {
                        officeid = results2.getInt("OFFICE_ID");
                        fin_year = results2.getString("FIN_YEAR");
                        postslno = results2.getInt("POST_SLNO");
                        postrankid = results2.getInt("POST_RANK_ID");
                        empstatusid =
                                results2.getString("EMPLOYMENT_STATUS_ID");
                        sancnoofpost =
                                results2.getInt("SANCTIONED_NO_OF_POSTS");
                        divertoother = results2.getInt("DIVERSION_TO_OTHER");
                        diverfromother =
                                results2.getInt("DIVERSION_FROM_OTHER");
                        remarks = results2.getString("REMARKS");
                        totalstregth = results2.getInt("TOTALSTRENGTH");
                        processflowstatusid =
                                results2.getString("PROCESS_FLOW_STATUS_ID");
                        ps3 =
 connection.prepareStatement("insert into hrm_ss_sanction_details_tmp(OFFICE_ID,FIN_YEAR,POST_SLNO,POST_RANK_ID,EMPLOYMENT_STATUS_ID,SANCTIONED_NO_OF_POSTS,DIVERSION_TO_OTHER,DIVERSION_FROM_OTHER,REMARKS,TOTALSTRENGTH,PROCESS_FLOW_STATUS_ID) values(?,?,?,?,?,?,?,?,?,?,?)");
                        ps3.setInt(1, officeid);
                        ps3.setString(2, selPeriod);
                        ps3.setInt(3, postslno);
                        ps3.setInt(4, postrankid);
                        ps3.setString(5, empstatusid);
                        ps3.setInt(6, sancnoofpost);
                        ps3.setInt(7, divertoother);
                        ps3.setInt(8, diverfromother);
                        ps3.setString(9, remarks);
                        ps3.setInt(10, totalstregth);
                        ps3.setString(11, "CR");
                        int i = ps3.executeUpdate();
                        System.out.println("Data inserted into hrm_ss_sanction_details_tmp successfully....");
                        cnt++;

                    }
                    if (cnt > 0) {
                        xml =
 xml + "<response><flag>success</flag></response>";
                        try {
                            ps4 =
 connection.prepareStatement("insert into hrm_ss_sanction_tmp(office_id,fin_year,process_flow_status_id) values(?,?,?)");
                            ps4.setString(1, offid);
                            ps4.setString(2, selPeriod);
                            ps4.setString(3, "CR");
                            int y = ps4.executeUpdate();
                            System.out.println("Updated in hrm_ss_sanction_tmp sucessfully.........");
                        } catch (Exception e) {
                            System.out.println("Failed in inserting into hrm_ss_sanction_tmp table..");
                        }


                    } else {
                        xml =
 xml + "<response><flag>failure</flag></response>";
                    }

                } catch (Exception e) {
                    System.out.println("Error in gettign previous period" + e);
                }

            } else if (cmd.equals("nil")) {
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
                        "select ss_template_id,SS_TEMPLATE_NAME from hrm_ss_templates where office_level_id in(select OFFICE_LEVEL_ID from com_mst_offices where office_id=?)";
                    ps1 = connection.prepareStatement(sql);
                    ps1.setInt(1, Office_Id);
                    results = ps1.executeQuery();
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
                    System.out.println("Exception in OfficeLevel statement:" +
                                       e);

                }

            } //EndIf(cmd.equals("OfficeLevel"))
            else if (cmd.equals("TableView")) {
                int TemplateId = 0;

                try {
                    System.out.println("hai1");
                    TemplateId =
                            Integer.parseInt(request.getParameter("TemplateId"));
                    String sql =
                        "select HRM_mst_post_ranks.post_rank_id,post_rank_name,no_of_posts,remarks,HRM_MST_SERVICE_GROUP.SERVICE_GROUP_ID,SERVICE_GROUP_NAME from HRM_SS_TEMPLATE_DETAILS,HRM_MST_POST_RANKs,HRM_MST_SERVICE_GROUP where HRM_SS_TEMPLATE_DETAILS.post_rank_id=HRM_MST_POST_RANKs.post_rank_id and HRM_MST_SERVICE_GROUP.SERVICE_GROUP_ID=HRM_MST_POST_RANKs.Service_group_id  and ss_template_id=? order by post_rank_id";
                    //String sql2="select distinct hrm_mst_post_ranks.post_rank_id,hrm_mst_post_ranks.post_rank_name,hrm_ss_template_details.no_of_posts,hrm_ss_template_details.remarks,hrm_mst_employment_status.employment_status_id,EMPLOYMENT_STATUS,HRM_MST_SERVICE_GROUP.SERVICE_GROUP_ID,SERVICE_GROUP_NAME,diversion_to_other,diversion_from_other,totalstrength from HRM_SS_TEMPLATE_DETAILS,HRM_MST_POST_RANKs,HRM_MST_SERVICE_GROUP,hrm_mst_employment_status,hrm_ss_sanction_details_tmp where HRM_SS_TEMPLATE_DETAILS.post_rank_id=HRM_MST_POST_RANKs.post_rank_id and HRM_MST_SERVICE_GROUP.SERVICE_GROUP_ID=HRM_MST_POST_RANKs.Service_group_id and hrm_mst_employment_status.employment_status_id=HRM_SS_TEMPLATE_DETAILS.EMPLOYMENT_STATUS_ID and hrm_ss_template_details.post_rank_id=hrm_ss_sanction_details_tmp.post_rank_id and ss_template_id=? order by post_rank_id";
                    PreparedStatement statement =
                        connection.prepareStatement(sql);
                    statement.setInt(1, TemplateId);
                    connection.clearWarnings();
                    try {
                        results = statement.executeQuery();
                        System.out.println();
                        try {
                            xml = "<response><flag>success</flag>";

                            while (results.next()) {
                                xml =
 xml + "<postrankid>" + results.getInt("post_rank_id") +
   "</postrankid><postrankname>" + results.getString("post_rank_name") +
   "</postrankname><noofpost>" + results.getInt("no_of_posts") +
   "</noofpost><remarks>" + results.getString("remarks") +
   "</remarks>><servicegroupid>" + results.getInt("SERVICE_GROUP_ID") +
   "</servicegroupid><servicegroupname>" +
   results.getString("SERVICE_GROUP_NAME") + "</servicegroupname>";
                                //xml=xml+"<postrankid>"+results.getInt("post_rank_id") + "</postrankid><postrankname>"+results.getString("post_rank_name")+"</postrankname><noofpost>"+results.getInt("no_of_posts")+"</noofpost><remarks>"+results.getString("remarks")+"</remarks><employmentstatusid>"+results.getString("employment_status_id")+"</employmentstatusid><employmentstatusname>"+results.getString("EMPLOYMENT_STATUS")+"</employmentstatusname><servicegroupid>"+results.getInt("SERVICE_GROUP_ID")+"</servicegroupid><servicegroupname>"+results.getString("SERVICE_GROUP_NAME")+"</servicegroupname><diversionto>"+results.getInt("diversion_to_other")+"</diversionto><diversionfrom>"+results.getInt("diversion_from_other")+"</diversionfrom><totalstrength>"+results.getInt("totalstrength")+"</totalstrength>";
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
            } //End if(cmd.equals("TableView"))
            else if (cmd.equals("ServiceGroup")) {
                int ServiceGroupId = 0;
                try {

                    ServiceGroupId =
                            Integer.parseInt(request.getParameter("ServiceGroup"));
                    // String sql="select POST_RANK_ID,POST_RANK_NAME from HRM_MST_POST_RANKS where SERVICE_GROUP_ID=? order by post_rank_name";
                    String sql2 =
                        "select distinct c.service_group_name,a.post_rank_id,a.post_rank_name,b.service_group_id from hrm_mst_post_ranks a,hrm_mst_designations b,hrm_mst_service_group c where a.post_rank_id=b.post_rank_id and b.service_group_id=c.service_group_id and c.service_group_id=? order by a.post_rank_name";

                    // PreparedStatement statement=connection.prepareStatement(sql);
                    PreparedStatement statement =
                        connection.prepareStatement(sql2);
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
            } //End if(cmd.equals("ServiceGroup"))
            else if (cmd.equals("OfficeStatus")) {

                try {

                    int Office_Id =
                        Integer.parseInt(request.getParameter("OfficeId"));
                    String finyear = request.getParameter("finyear");
                    System.out.println("fin year in office status:" + finyear);
                    System.out.println("officeid in office status:" +
                                       Office_Id);
                    String sql =
                        "select process_flow_status_id from hrm_ss_sanction_tmp where office_id=? and fin_year=?";
                    PreparedStatement statement =
                        connection.prepareStatement(sql);
                    statement.setInt(1, Office_Id);
                    statement.setString(2, finyear);
                    connection.clearWarnings();
                    try {
                        results = statement.executeQuery();
                        System.out.println();
                        try {
                            xml = "<response><flag>success</flag>";

                            if (results.next()) {
                                xml =
 xml + "<recordstatus>" + results.getString("process_flow_status_Id") +
   "</recordstatus>";
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
            } //End if(cmd.equals("OfficeStatus"))

        } catch (Exception e) {
            System.out.println("Exception in Base try:" + e);

        }

        System.out.println("Xml is:" + xml);
        out.write(xml);
        out.close();
    }
}
