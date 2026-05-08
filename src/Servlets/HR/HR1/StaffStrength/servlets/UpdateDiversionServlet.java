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

public class UpdateDiversionServlet extends HttpServlet {
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
            String finyr = null;
            if (cmd.equals("nil")) {
                try {
                    IntID = Integer.parseInt(request.getParameter("ID"));
                    finyr = request.getParameter("financialyear");
                    System.out.println("financial year is :" + finyr);
                    System.out.println("command value is---------------" +
                                       cmd);
                } catch (Exception e) {
                    System.out.println("Exception in Number Format" + e);
                }
                try {
                    connection.clearWarnings();

                    System.out.println("servlet  **** CL___>>>>>>>     :");

                    //String sql="select office_name,office_address1,office_address2,city_town_name,district_code,Work_Nature_Desc,Office_Level_Name from com_mst_offices,COM_MST_OFFICE_LEVELS,COM_MST_WORK_NATURE  where com_mst_offices.Office_Level_Id=COM_MST_OFFICE_LEVELS.Office_Level_Id and com_mst_offices.Primary_Work_Id=COM_MST_WORK_NATURE.Work_Nature_Id and Office_Id=?";

                    //String sql="select office_name,office_address1,office_address2,city_town_name,district_code from com_mst_offices where Office_Id=?";
                    String sql =
                        "select distinct a.office_id,a.office_short_name,a.office_address1,a.office_address2,a.city_town_name,a.Office_pin_code," +
                        " c.Work_Nature_Desc,b.Office_Level_Name, d.district_name,e.process_flow_status_id from com_mst_offices a " +
                        " left outer join COM_MST_OFFICE_LEVELS b on a.office_level_id=b.office_level_id " +
                        " left outer join COM_MST_WORK_NATURE c on a.primary_work_id=c.work_nature_id " +
                        " left outer join com_mst_districts d on d.district_code=a.district_code " +
                        " left outer join hrm_ss_sanction_details e on a.office_id=e.office_id " +
                        " where office_id=? and fin_year=?";

                    ps = connection.prepareStatement(sql);
                    ps.setInt(1, IntID);
                    ps.setString(2, finyr);
                    results = ps.executeQuery();
                    if (results.next()) {
                        /* String xml="<response><flag>success</flag><name>" + results.getString("Office_Name") + "</name><address1>"+results.getString("office_address1")+"</address1><address2>"+results.getString("office_address2")+"</address2>";
                            xml=xml + "<address>" + results.getString("CITY_TOWN_NAME") +"</address><district>"+results.getInt("district_code")+"</district><city>"+results.getString("city_town_name");
                            xml=xml+"</city><level>" + results.getString("Office_Level_Name") + "</level><type>" + results.getString("Work_Nature_Desc") + "</type></response>";
                            */
                        String address1 = results.getString("office_address1");
                        if (address1 == null)
                            address1 = "null";
                        String address2 = results.getString("office_address2");
                        if (address2 == null)
                            address2 = "null";
                        String dname = results.getString("district_name");
                        if (dname == null)
                            dname = "null";
                        String city = results.getString("city_town_name");
                        if (city == null)
                            city = "null";
                        xml =
 "<response><flag>success</flag><name>" + results.getString("Office_short_Name") +
   "</name><address1>" + address1 + "</address1><address2>" + address2 +
   "</address2>";
                        xml =
 xml + "<district>" + dname + "</district><city>" + city;
                        xml = xml + "</city><level></level><type></type>";
                        xml =
 xml + "<pflowstatus>" + results.getString("process_flow_status_id") +
   "</pflowstatus>";
                        xml = xml + "</response>";

                    } else {
                        xml = "<response><flag>failure</flag></response>";
                    }
                    ps.close();

                } catch (SQLException e) {
                    System.out.println("Exception in creating statement:" + e);

                }

            }

            else if (cmd.equals("nill")) {
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

                    //String sql="select office_name,office_address1,office_address2,city_town_name,district_code from com_mst_offices where Office_Id=?";
                    String sql =
                        "select distinct a.office_id,a.office_short_name,a.office_address1,a.office_address2,a.city_town_name,a.Office_pin_code," +
                        " c.Work_Nature_Desc,b.Office_Level_Name, d.district_name,e.process_flow_status_id from com_mst_offices a " +
                        " left outer join COM_MST_OFFICE_LEVELS b on a.office_level_id=b.office_level_id " +
                        " left outer join COM_MST_WORK_NATURE c on a.primary_work_id=c.work_nature_id " +
                        " left outer join com_mst_districts d on d.district_code=a.district_code " +
                        " left outer join hrm_ss_sanction_details e on a.office_id=e.office_id " +
                        " where office_id=?";

                    ps = connection.prepareStatement(sql);
                    ps.setInt(1, IntID);
                    results = ps.executeQuery();
                    if (results.next()) {
                        /* String xml="<response><flag>success</flag><name>" + results.getString("Office_Name") + "</name><address1>"+results.getString("office_address1")+"</address1><address2>"+results.getString("office_address2")+"</address2>";
                             xml=xml + "<address>" + results.getString("CITY_TOWN_NAME") +"</address><district>"+results.getInt("district_code")+"</district><city>"+results.getString("city_town_name");
                             xml=xml+"</city><level>" + results.getString("Office_Level_Name") + "</level><type>" + results.getString("Work_Nature_Desc") + "</type></response>";
                             */

                        xml =
 "<response><flag>success</flag><name>" + results.getString("Office_short_Name") +
   "</name><address1>" + results.getString("office_address1") +
   "</address1><address2>" + results.getString("office_address2") +
   "</address2>";
                        xml =
 xml + "<address>" + results.getString("CITY_TOWN_NAME") +
   "</address><district>" + results.getString("district_name") +
   "</district><city>" + results.getString("city_town_name");
                        xml = xml + "</city><level></level><type></type>";
                        xml =
 xml + "<pflowstatus>" + results.getString("process_flow_status_id") +
   "</pflowstatus>";
                        xml = xml + "</response>";

                    } else {
                        xml = "<response><flag>failure</flag></response>";
                    }
                    ps.close();

                } catch (SQLException e) {
                    System.out.println("Exception in creating statement:" + e);

                }

            } else if (cmd.equals("PostRank")) {

                try {
                    connection.clearWarnings();
                    int Office_Id =
                        Integer.parseInt(request.getParameter("OfficeId"));
                    //String finyear=request.getParameter("FinYear");
                    System.out.println("servlet  **** OfficeLevel in OfficeId:" +
                                       Office_Id);
                    //System.out.println("servlet  **** OfficeLevel in FinYear:"+finyear);
                    String sql =
                        "select distinct a.post_rank_id,a.post_rank_name from hrm_mst_post_ranks a,hrm_ss_sanction_details b where a.post_rank_id=b.post_rank_id and b.office_id=?  order by post_rank_name";
                    //String sql2="select a.post_rank_id,a.post_rank_name,a.EMPLOYMENT_STATUS_ID,c.employment_status from hrm_mst_post_ranks a,hrm_ss_sanction_details b,hrm_mst_employment_status c where a.post_rank_id=b.post_rank_id and a.employment_status_id=c.employment_status_id and b.office_id=5007 and b.fin_year='2006-2007'";
                    ps1 = connection.prepareStatement(sql);
                    ps1.setInt(1, Office_Id);
                    //ps1.setString(2,finyear);
                    results = ps1.executeQuery();
                    try {
                        xml = "<response><flag>success</flag>";

                        while (results.next()) {

                            xml =
 xml + "<options><rankid>" + results.getInt("post_rank_id") +
   "</rankid><rankname>" + results.getString("post_rank_name") +
   "</rankname></options>";
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

            } //EndIf(cmd.equals("PostRank"))
            else if (cmd.equals("SanctionPost")) {


                try {
                    System.out.println("hai1");
                    int Office_Id =
                        Integer.parseInt(request.getParameter("OfficeId"));
                    String finyear = request.getParameter("FinYear");
                    System.out.println("finyear is:" + finyear);
                    //String postcategory=request.getParameter("PostCategory");
                    int postrank =
                        Integer.parseInt(request.getParameter("PostRank"));
                    String sql =
                        "select SANCTIONED_NO_OF_POSTS,DIVERSION_TO_OTHER,DIVERSION_FROM_OTHER from hrm_ss_sanction_details where  OFFICE_ID=? and post_rank_id=? and fin_year=?";
                    PreparedStatement statement =
                        connection.prepareStatement(sql);
                    //statement.setString(1,finyear);
                    statement.setInt(1, Office_Id);
                    //statement.setString(3,postcategory);
                    statement.setInt(2, postrank);
                    statement.setString(3, finyear);
                    String sql2 =
                        "select sum(no_of_posts_diverted) as result from hrm_ss_diversion_orders where diversion_from_office_id=? and diversion_post_rank_id=?";
                    PreparedStatement ps2 = connection.prepareStatement(sql2);
                    ps2.setInt(1, Office_Id);
                    ps2.setInt(2, postrank);
                    ResultSet res = null;
                    int divertedto = 0;
                    connection.clearWarnings();
                    try {
                        results = statement.executeQuery();
                        res = ps2.executeQuery();
                        System.out.println();
                        try {
                            xml = "<response><flag>success</flag>";

                            if (results.next()) {
                                if (res.next()) {
                                    divertedto = res.getInt("result");
                                    System.out.println("divertedto is:" +
                                                       divertedto);
                                    xml =
 xml + "<sanctionpost>" + results.getInt("sanctioned_no_of_posts") +
   "</sanctionpost><divertedto>" + divertedto + "</divertedto><divertedfrom>" +
   results.getInt("DIVERSION_FROM_OTHER") + "</divertedfrom>";
                                    found++;
                                }
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
            } //End if(cmd.equals("SanctionPost"))
            else if (cmd.equals("OfficeStatus")) {

                try {

                    int Office_Id =
                        Integer.parseInt(request.getParameter("OfficeId"));
                    int tooffice =
                        Integer.parseInt(request.getParameter("ToOffice"));
                    int postrank =
                        Integer.parseInt(request.getParameter("PostRank"));
                    String sql =
                        "select process_flow_status_id from HRM_SS_DIVERSION_ORDERS_TMP where DIVERSION_FROM_OFFICE_ID=? and DIVERSION_TO_OFFICE_ID=? and DIVERSION_POST_RANK_ID=?";
                    PreparedStatement statement =
                        connection.prepareStatement(sql);
                    statement.setInt(1, Office_Id);
                    statement.setInt(2, tooffice);
                    statement.setInt(3, postrank);
                    connection.clearWarnings();
                    try {
                        results = statement.executeQuery();
                        System.out.println();
                        try {
                            xml = "<response><flag>success</flag>";

                            if (results.next()) {
                                xml =
 xml + "<recordstatus>" + results.getString("process_flow_status_id") +
   "</recordstatus>";
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
            } //End if(cmd.equals("OfficeStatus"))
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
