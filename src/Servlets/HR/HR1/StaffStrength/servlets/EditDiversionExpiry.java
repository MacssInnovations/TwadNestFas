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

public class EditDiversionExpiry extends HttpServlet {
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

                    //  String sql="select office_name,office_address1,office_address2,city_town_name,district_code from com_mst_offices where Office_Id=?";
                    String sql =
                        "select a.office_name,a.office_address1,a.office_address2,a.city_town_name,a.Office_pin_code,c.Work_Nature_Desc,b.Office_Level_Name, d.district_name from com_mst_offices a left outer join COM_MST_OFFICE_LEVELS b on a.office_level_id=b.office_level_id left outer join COM_MST_WORK_NATURE c on a.primary_work_id=c.work_nature_id left outer join com_mst_districts d on d.district_code=a.district_code where office_id=?";

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
   "</address><district>" + results.getString("district_name") +
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

            else if (cmd.equals("PostRank")) {

                try {
                    connection.clearWarnings();
                    int Office_Id =
                        Integer.parseInt(request.getParameter("OfficeId"));
                    //String finyear=request.getParameter("FinYear");

                    System.out.println("servlet  **** OfficeLevel in OfficeId:" +
                                       Office_Id);
                    //System.out.println("servlet  **** OfficeLevel in FinYear:"+finyear);
                    String sql =
                        "select a.post_rank_id,a.post_rank_name from hrm_mst_post_ranks a,HRM_SS_DIVERSION_ORDERS_TMP b where a.post_rank_id=b.DIVERSION_POST_RANK_ID  and b.DIVERSION_FROM_OFFICE_ID=? ";
                    String sql2 =
                        "select a.post_rank_id,a.post_rank_name,b.EMPLOYMENT_STATUS_ID,c.employment_status from hrm_mst_post_ranks a,hrm_ss_sanction_details b,hrm_mst_employment_status c where a.post_rank_id=b.post_rank_id and b.employment_status_id=c.employment_status_id and b.office_id=? and b.fin_year=? order by post_rank_id";
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
                    int postrank =
                        Integer.parseInt(request.getParameter("PostRank"));
                    //System.out.println("post rank is:"+postrank);
                    String sql =
                        "select SANCTIONED_NO_OF_POSTS,DIVERSION_TO_OTHER,DIVERSION_FROM_OTHER from hrm_ss_sanction_details where OFFICE_ID=? and post_rank_id=? and fin_year=?";
                    PreparedStatement statement =
                        connection.prepareStatement(sql);
                    //statement.setString(1,finyear);
                    statement.setInt(1, Office_Id);
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
            else if (cmd.equals("OfficeDetails")) {

                try {
                    System.out.println("Order id is:" +
                                       request.getParameter("OrderId"));
                    int order_Id =
                        Integer.parseInt(request.getParameter("OrderId"));
                    //String finyear=request.getParameter("FinYear");
                    String sql =
                        "select DIVERSION_FROM_OFFICE_ID,DIVERSION_TO_OFFICE_ID,NO_OF_POSTS_DIVERTED,DIVERSION_ORDER_DATE,REMARKS,process_flow_status_id,DIVERSION_POST_RANK_ID,DIVERSION_EFFECTIVE_DATE,DIVERSION_PERIOD_UPTO from HRM_SS_DIVERSION_ORDERS where DIVERSION_ORDER_ID=? and process_flow_status_id in 'FR' and (diversion_expired!='Y' or diversion_expired is null)";

                    PreparedStatement statement =
                        connection.prepareStatement(sql);
                    statement.setInt(1, order_Id);
                    //statement.setString(2,finyear);
                    connection.clearWarnings();
                    try {
                        results = statement.executeQuery();
                        System.out.println();
                        try {
                            xml = "<response><flag>success</flag>";

                            if (results.next()) {
                                //Converting sql date to user date format
                                java.sql.Date DateOfFormation =
                                    results.getDate("DIVERSION_ORDER_DATE");
                                String DateToBeDisplayed = "";
                                if (DateOfFormation == null) {
                                    DateToBeDisplayed = "Not Specified";
                                } else {
                                    try {
                                        java.text.SimpleDateFormat sdf =
                                            new java.text.SimpleDateFormat("dd/MM/yyyy");
                                        DateToBeDisplayed =
                                                sdf.format(DateOfFormation);
                                    } catch (Exception e) {
                                        System.out.println("error while formatting date : " +
                                                           e);
                                        DateToBeDisplayed = "Not Specified";
                                    }
                                }

                                java.sql.Date DateOfFormation1 =
                                    results.getDate("DIVERSION_EFFECTIVE_DATE");
                                String DateToBeDisplayed1 = "";
                                if (DateOfFormation1 == null) {
                                    DateToBeDisplayed1 = "Not Specified";
                                } else {
                                    try {
                                        java.text.SimpleDateFormat sdf =
                                            new java.text.SimpleDateFormat("dd/MM/yyyy");
                                        DateToBeDisplayed1 =
                                                sdf.format(DateOfFormation1);
                                    } catch (Exception e) {
                                        System.out.println("error while formatting date : " +
                                                           e);
                                        DateToBeDisplayed1 = "Not Specified";
                                    }
                                }

                                java.sql.Date DateOfFormation2 =
                                    results.getDate("DIVERSION_PERIOD_UPTO");
                                String DateToBeDisplayed2 = "";
                                if (DateOfFormation2 == null) {
                                    DateToBeDisplayed2 = "Not Specified";
                                } else {
                                    try {
                                        java.text.SimpleDateFormat sdf =
                                            new java.text.SimpleDateFormat("dd/MM/yyyy");
                                        DateToBeDisplayed2 =
                                                sdf.format(DateOfFormation2);
                                    } catch (Exception e) {
                                        System.out.println("error while formatting date : " +
                                                           e);
                                        DateToBeDisplayed2 = "Not Specified";
                                    }
                                }


                                xml =
 xml + "<diversionfrom>" + results.getInt("diversion_from_office_id") +
   "</diversionfrom><diversionto>" + results.getInt("diversion_to_office_id") +
   "</diversionto><noofpost>" + results.getInt("NO_OF_POSTS_DIVERTED") +
   "</noofpost><remarks>" + results.getString("remarks") +
   "</remarks><recordstatus>" + results.getString("process_flow_status_id") +
   "</recordstatus><date>" + DateToBeDisplayed + "</date><postrankid>" +
   results.getInt("DIVERSION_POST_RANK_ID") + "</postrankid><dateeffective>" +
   DateToBeDisplayed1 + "</dateeffective><dateperiod>" + DateToBeDisplayed2 +
   "</dateperiod>";
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
            else if (cmd.equals("DiversionDetails")) {
                int order_Id =
                    Integer.parseInt(request.getParameter("OrderId"));

                try {
                    System.out.println("to");

                    String sql =
                        "select POST_SLNO,HRM_mst_post_ranks.post_rank_id,post_rank_name,POSTS_DIVERSION_TO,REMARKS_DIVERSION_TO,POSTS_DIVERSION_FROM,REMARKS_DIVERSION_FROM,hrm_mst_employment_status.employment_status_id,EMPLOYMENT_STATUS,HRM_MST_SERVICE_GROUP.SERVICE_GROUP_ID,SERVICE_GROUP_NAME from HRM_SS_DIVERSIONS,HRM_MST_POST_RANKs,HRM_MST_SERVICE_GROUP,hrm_mst_employment_status where HRM_SS_DIVERSIONS.post_rank_id=HRM_MST_POST_RANKs.post_rank_id and HRM_MST_SERVICE_GROUP.SERVICE_GROUP_ID=HRM_MST_POST_RANKs.Service_group_id and hrm_mst_employment_status.employment_status_id=HRM_SS_DIVERSIONS.EMPLOYMENT_STATUS_ID and OFFICE_ID=?";
                    String sql1 =
                        "select diversion_ext_slno,diversion_ext_order_date,extended_upto,remarks from hrm_ss_diversion_ext_order where diversion_order_id=? order by diversion_ext_slno";
                    PreparedStatement statement =
                        connection.prepareStatement(sql1);
                    statement.setInt(1, order_Id);
                    connection.clearWarnings();
                    try {
                        results = statement.executeQuery();
                        System.out.println();
                        try {
                            xml = "<response><flag>success</flag>";

                            while (results.next()) {

                                //Converting sql date to user date format
                                java.sql.Date DateOfFormation =
                                    results.getDate("diversion_ext_order_date");
                                String DateToBeDisplayed = "";
                                if (DateOfFormation == null) {
                                    DateToBeDisplayed = "Not Specified";
                                } else {
                                    try {
                                        java.text.SimpleDateFormat sdf =
                                            new java.text.SimpleDateFormat("dd/MM/yyyy");
                                        DateToBeDisplayed =
                                                sdf.format(DateOfFormation);
                                    } catch (Exception e) {
                                        System.out.println("error while formatting date : " +
                                                           e);
                                        DateToBeDisplayed = "Not Specified";
                                    }
                                }


                                java.sql.Date DateOfFormation1 =
                                    results.getDate("extended_upto");
                                String DateToBeDisplayed1 = "";
                                if (DateOfFormation1 == null) {
                                    DateToBeDisplayed1 = "Not Specified";
                                } else {
                                    try {
                                        java.text.SimpleDateFormat sdf =
                                            new java.text.SimpleDateFormat("dd/MM/yyyy");
                                        DateToBeDisplayed1 =
                                                sdf.format(DateOfFormation1);
                                    } catch (Exception e) {
                                        System.out.println("error while formatting date : " +
                                                           e);
                                        DateToBeDisplayed1 = "Not Specified";
                                    }
                                }


                                xml =
 xml + "<options><slno>" + results.getInt("diversion_ext_slno") +
   "</slno><extorderdate>" + DateToBeDisplayed + "</extorderdate><extupto>" +
   DateToBeDisplayed1 + "</extupto><remarks>" + results.getString("remarks") +
   "</remarks></options>";
                                found++;
                            }
                            if (found == 0)
                                xml = "<response><flag>failure</flag>";

                            xml = xml + "</response>";
                        } catch (SQLException e) {
                            System.out.println("Exception in Query DiversionDetails" +
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
            } //End if(cmd.equals("diversiondetails"))

            else if (cmd.equals("SlNO")) {
                int slno = 0;

                try {
                    System.out.println("to");
                    int order_Id =
                        Integer.parseInt(request.getParameter("OrderId"));
                    System.out.println("order id  is" + order_Id);
                    String sql =
                        "select EXPIRY_ORDER_ID from hrm_ss_diversion_expiry_tmp where DIVERSION_ORDER_ID=? order by expiry_order_id";
                    PreparedStatement statement =
                        connection.prepareStatement(sql);
                    statement.setInt(1, order_Id);
                    connection.clearWarnings();
                    try {
                        results = statement.executeQuery();
                        System.out.println();
                        try {
                            xml = "<response><flag>success</flag>";
                            int slno1 = 0;

                            while (results.next()) {

                                xml =
 xml + "<slno>" + results.getInt("expiry_order_id") + "</slno>";
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

            else if (cmd.equals("Status")) {
                int slno = 0;

                try {
                    System.out.println("to");
                    int order_Id =
                        Integer.parseInt(request.getParameter("OrderId"));
                    System.out.println("order id  is" + order_Id);
                    String sql =
                        "select process_flow_status_id from hrm_ss_diversion_expiry_tmp where DIVERSION_ORDER_ID=?";
                    PreparedStatement statement =
                        connection.prepareStatement(sql);
                    statement.setInt(1, order_Id);
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
            } //End if(cmd.equals("Status"))

            else if (cmd.equals("Expiry")) {
                int slno = 0;

                try {
                    System.out.println("to" + request.getParameter("SlNo"));
                    int slnoid =
                        Integer.parseInt(request.getParameter("SlNo"));
                    System.out.println("slno id in expiry is" + slnoid);
                    String sql =
                        "select DIVERSION_EXPIRED,EXPIRY_DATE,REMARKS from hrm_ss_diversion_expiry_tmp where EXPIRY_ORDER_ID=? order by expiry_order_id";
                    PreparedStatement statement =
                        connection.prepareStatement(sql);
                    statement.setInt(1, slnoid);
                    connection.clearWarnings();
                    try {
                        results = statement.executeQuery();
                        System.out.println();
                        try {
                            xml = "<response><flag>success</flag>";
                            int slno1 = 0;

                            if (results.next()) {
                                //Converting sql date to user date format
                                java.sql.Date DateOfFormation =
                                    results.getDate("EXPIRY_DATE");
                                String DateToBeDisplayed = "";
                                if (DateOfFormation == null) {
                                    DateToBeDisplayed = "Not Specified";
                                } else {
                                    try {
                                        java.text.SimpleDateFormat sdf =
                                            new java.text.SimpleDateFormat("dd/MM/yyyy");
                                        DateToBeDisplayed =
                                                sdf.format(DateOfFormation);
                                    } catch (Exception e) {
                                        System.out.println("error while formatting date : " +
                                                           e);
                                        DateToBeDisplayed = "Not Specified";
                                    }
                                }


                                xml =
 xml + "<diversionexpired>" + results.getString("DIVERSION_EXPIRED") +
   "</diversionexpired><expdate>" + DateToBeDisplayed + "</expdate><remarks>" +
   results.getString("REMARKS") + "</remarks>";
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
            } //End if(cmd.equals("Expiry"))

        } catch (Exception e) {
            System.out.println("Exception in Base try:" + e);

        }

        System.out.println("Xml is:" + xml);
        out.write(xml);
        out.close();
    }
}
