package Servlets.HR.HR1.EmployeeMaster.servlets;

import Servlets.Security.classes.UserProfile;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.*;

import java.text.SimpleDateFormat;

import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import java.util.Calendar;


public class Edit_Repost_OrderServ extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();

        System.out.println("inside servlet");

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        PreparedStatement ps5 = null;
        ResultSet rs5 = null;
        try {

            ResourceBundle rb =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";

            String strDriver = rb.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rb.getString("Config.DSN");
            String strhostname = rb.getString("Config.HOST_NAME");
            String strportno = rb.getString("Config.PORT_NUMBER");
            String strsid = rb.getString("Config.SID");
            String strdbusername = rb.getString("Config.USER_NAME");
            String strdbpassword = rb.getString("Config.PASSWORD");

            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection


            Class.forName(strDriver.trim());
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
        } catch (Exception e) {
            System.out.println("Exception in connection...." + e);
        }

        HttpSession session = request.getSession(false);

        try {

            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");

            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }

        String updatedby = (String)session.getAttribute("UserId");
        System.out.println("user id..." + updatedby);

        long l = System.currentTimeMillis();
        java.sql.Timestamp ts = new java.sql.Timestamp(l);

        String command = request.getParameter("Command");
        System.out.println("command.." + command);


        String xml = "";


        if (command.equalsIgnoreCase("loademp")) {
            int eid = Integer.parseInt(request.getParameter("txtEmployeeid"));
            System.out.println("employee id.." + eid);

            int offid = Integer.parseInt(request.getParameter("offid"));
            System.out.println("office id.." + offid);

            int pro_id = Integer.parseInt(request.getParameter("pro_id"));
            System.out.println("proceeding id..." + pro_id);

            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            //int offid=0;
            //offid=Integer.parseInt(request.getParameter("offid"));
            xml = "<response><command>loademp</command>";
            try {

                UserProfile up = null;
                up = (UserProfile)session.getAttribute("UserProfile");
                boolean flag = true;
                ps =
  con.prepareStatement("SELECT EMPLOYEE_ID FROM HRM_MST_EMPLOYEES WHERE EMPLOYEE_ID=?");
                ps.setInt(1, eid);
                rs = ps.executeQuery();
                if (!rs.next()) {
                    xml = xml + "<flag>failure</flag>";
                    flag = false;
                } else if (up.getEmployeeId() != eid) {
                    int OfficeId = 0;
                    String sql =
                        "select CONTROLLING_OFFICE_ID from HRM_EMP_CONTROLLING_OFFICE where employee_id=?";
                    ps = con.prepareStatement(sql);
                    ps.setInt(1, eid);
                    rs = ps.executeQuery();

                    if (rs.next()) {
                        OfficeId = rs.getInt("CONTROLLING_OFFICE_ID");
                    }

                    if (OfficeId != 0) {
                        sql =
 "select OFFICE_ID  from HRM_EMP_CURRENT_POSTING where employee_id=?";
                        ps = con.prepareStatement(sql);
                        ps.setInt(1, up.getEmployeeId());
                        rs = ps.executeQuery();
                        if (rs.next()) {
                            int offid1 = rs.getInt("OFFICE_ID");
                            if (offid1 != OfficeId) {

                                //xml=xml+"<flag>failurea</flag>";
                                flag = true;
                            }
                        } else {

                            xml = xml + "<flag>failureb</flag>";
                            flag = false;
                        }

                    } else {
                        xml = xml + "<flag>failurec</flag>";
                        flag = false;
                    }
                } else {


                }

                if (flag) {

                    ps5 =
 con.prepareStatement("select employee_id from HRM_REPOST_DETAILS where REPOST_ISSUE_OFFICE_ID=? and REPOST_ORDER_ID=? and employee_id=?");

                    ps5.setInt(1, offid);
                    ps5.setInt(2, pro_id);
                    ps5.setInt(3, eid);

                    rs5 = ps5.executeQuery();

                    if (rs5.next()) {
                        xml = xml + "<flag>failure5</flag>";
                    } else {
                        //xml=xml+"<flag>failure</flag>";


                        ps =
  con.prepareStatement("select EMPLOYEE_STATUS_ID from HRM_EMP_CURRENT_POSTING where  EMPLOYEE_ID=? and employee_status_id not in ('VRS','SAN','DTH')");
                        ps.setInt(1, eid);
                        rs = ps.executeQuery();
                        if (!rs.next()) {
                            xml = xml + "<flag>failure1</flag>";
                        } else {
                            System.out.println("inside employee status id");

                            if (rs.getString("EMPLOYEE_STATUS_ID") != null) {

                                ps =
  con.prepareStatement("select EMPLOYEE_ID from HRM_EMP_RELIEVAL_DETAILS where  EMPLOYEE_ID=? and (PROCESS_FLOW_STATUS_ID='CR' or PROCESS_FLOW_STATUS_ID='MD')");
                                ps.setInt(1, eid);
                                rs = ps.executeQuery();
                                if (rs.next()) {
                                    xml = xml + "<flag>failure2</flag>";
                                } else {

                                    ps =
  con.prepareStatement("select e.EMPLOYEE_ID,e.date_of_birth,e.EMPLOYEE_NAME ||decode(e.EMPLOYEE_INITIAL,null,' ','.'||e.EMPLOYEE_INITIAL) as  EMPLOYEE_NAME ,d.DESIGNATION,f.office_name,c.designation_id from HRM_MST_EMPLOYEES e,HRM_EMP_CURRENT_POSTING c,HRM_MST_DESIGNATIONS d,com_mst_offices f where c.DESIGNATION_ID=d.DESIGNATION_ID and e.EMPLOYEE_ID=c.EMPLOYEE_ID AND c.office_id=f.office_id and e.EMPLOYEE_ID=? ");
                                    ps.setInt(1, eid);
                                    //ps.setInt(2,offid);
                                    rs = ps.executeQuery();
                                    if (rs.next()) {


                                        xml =
 xml + "<flag>success</flag><eid>" + eid + "</eid><ename>" +
   rs.getString("EMPLOYEE_NAME") + "</ename><desig>" +
   rs.getString("DESIGNATION") + "</desig><curr_post>" +
   rs.getString("office_name") + "</curr_post><desig_id>" +
   rs.getInt("designation_id") + "</desig_id>";
                                        xml =
 xml + "<dob>" + (rs.getDate("date_of_birth")).getYear() + "</dob>";


                                        /***************  16-08-2007    ***********************/


                                        ps =
  con.prepareStatement("select DATE_EFFECTIVE_FROM,DATE_EFFECTIVE_FROM_SESSION  from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?");

                                        ps.setInt(1, eid);

                                        rs = ps.executeQuery();
                                        String maxfromdate = "";
                                        String maxsession = "";
                                        if (rs.next()) {
                                            if (rs.getDate("DATE_EFFECTIVE_FROM") !=
                                                null) {
                                                maxfromdate =
                                                        new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("DATE_EFFECTIVE_FROM"));
                                                System.out.println("max from date :" +
                                                                   rs.getDate("DATE_EFFECTIVE_FROM"));
                                            } else {

                                                maxfromdate = "empty";
                                            }
                                            if (rs.getString("DATE_EFFECTIVE_FROM_SESSION") !=
                                                null) {
                                                maxsession =
                                                        rs.getString("DATE_EFFECTIVE_FROM_SESSION");
                                            } else {
                                                maxsession = "FN";
                                            }
                                        }
                                        xml =
 xml + "<maxfromdate>" + maxfromdate + "</maxfromdate><maxsession>" +
   maxsession + "</maxsession>";


                                        /********************************************************/

                                    } else
                                        xml = xml + "<flag>failure</flag>";

                                }
                            }


                        }
                    }
                } else {
                    xml = xml + "<flag>failure3</flag>";
                }
            } catch (Exception e) {
                System.out.println("catch..HERE.in load emp." + e);
                xml = xml + "<flag>failure</flag>";
            }


            xml = xml + "</response>";
            out.println(xml);

        }


        else if (command.equalsIgnoreCase("editloademp")) {
            int eid = Integer.parseInt(request.getParameter("txtEmployeeid"));
            System.out.println("employee id.." + eid);

            int offid = Integer.parseInt(request.getParameter("offid"));
            System.out.println("office id.." + offid);

            int pro_id = Integer.parseInt(request.getParameter("pro_id"));
            System.out.println("proceeding id..." + pro_id);

            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            //int offid=0;
            //offid=Integer.parseInt(request.getParameter("offid"));
            xml = "<response><command>editloademp</command>";
            try {

                UserProfile up = null;
                up = (UserProfile)session.getAttribute("UserProfile");
                boolean flag = true;
                ps =
  con.prepareStatement("SELECT EMPLOYEE_ID FROM HRM_MST_EMPLOYEES WHERE EMPLOYEE_ID=?");
                ps.setInt(1, eid);
                rs = ps.executeQuery();
                if (!rs.next()) {
                    xml = xml + "<flag>failure</flag>";
                    flag = false;
                } else if (up.getEmployeeId() != eid) {
                    int OfficeId = 0;
                    String sql =
                        "select CONTROLLING_OFFICE_ID from HRM_EMP_CONTROLLING_OFFICE where employee_id=?";
                    ps = con.prepareStatement(sql);
                    ps.setInt(1, eid);
                    rs = ps.executeQuery();

                    if (rs.next()) {
                        OfficeId = rs.getInt("CONTROLLING_OFFICE_ID");
                    }

                    if (OfficeId != 0) {
                        sql =
 "select OFFICE_ID  from HRM_EMP_CURRENT_POSTING where employee_id=?";
                        ps = con.prepareStatement(sql);
                        ps.setInt(1, up.getEmployeeId());
                        rs = ps.executeQuery();
                        if (rs.next()) {
                            int offid1 = rs.getInt("OFFICE_ID");
                            if (offid1 != OfficeId) {

                                //xml=xml+"<flag>failurea</flag>";
                                flag = true;
                            }
                        } else {

                            xml = xml + "<flag>failureb</flag>";
                            flag = false;
                        }

                    } else {
                        xml = xml + "<flag>failurec</flag>";
                        flag = false;
                    }
                } else {


                }

                if (flag) {
                    /*
                    ps5=con.prepareStatement("select employee_id from HRM_REPOST_DETAILS where REPOST_ISSUE_OFFICE_ID=? and REPOST_ORDER_ID=? and employee_id=?");

                    ps5.setInt(1,offid);
                    ps5.setInt(2,pro_id);
                    ps5.setInt(3,eid);

                    rs5=ps5.executeQuery();

                    if(rs5.next())
                    {
                        xml=xml+"<flag>failure5</flag>";
                    }*/
                    //else
                    //{
                    //xml=xml+"<flag>failure</flag>";


                    ps =
  con.prepareStatement("select EMPLOYEE_STATUS_ID from HRM_EMP_CURRENT_POSTING where  EMPLOYEE_ID=? and employee_status_id not in ('VRS','SAN','DTH')");
                    ps.setInt(1, eid);
                    rs = ps.executeQuery();
                    if (!rs.next()) {
                        xml = xml + "<flag>failure1</flag>";
                    } else {
                        System.out.println("inside employee status id");

                        if (rs.getString("EMPLOYEE_STATUS_ID") != null) {

                            ps =
  con.prepareStatement("select EMPLOYEE_ID from HRM_EMP_RELIEVAL_DETAILS where  EMPLOYEE_ID=? and (PROCESS_FLOW_STATUS_ID='CR' or PROCESS_FLOW_STATUS_ID='MD')");
                            ps.setInt(1, eid);
                            rs = ps.executeQuery();
                            if (rs.next()) {
                                xml = xml + "<flag>failure2</flag>";
                            } else {

                                ps =
  con.prepareStatement("select e.EMPLOYEE_ID,e.date_of_birth,e.EMPLOYEE_NAME ||decode(e.EMPLOYEE_INITIAL,null,' ','.'||e.EMPLOYEE_INITIAL) as  EMPLOYEE_NAME ,d.DESIGNATION,f.office_name,c.designation_id from HRM_MST_EMPLOYEES e,HRM_EMP_CURRENT_POSTING c,HRM_MST_DESIGNATIONS d,com_mst_offices f where c.DESIGNATION_ID=d.DESIGNATION_ID and e.EMPLOYEE_ID=c.EMPLOYEE_ID AND c.office_id=f.office_id and e.EMPLOYEE_ID=? ");
                                ps.setInt(1, eid);
                                //ps.setInt(2,offid);
                                rs = ps.executeQuery();
                                if (rs.next()) {


                                    xml =
 xml + "<flag>success</flag><eid>" + eid + "</eid><ename>" +
   rs.getString("EMPLOYEE_NAME") + "</ename><desig>" +
   rs.getString("DESIGNATION") + "</desig><curr_post>" +
   rs.getString("office_name") + "</curr_post><desig_id>" +
   rs.getInt("designation_id") + "</desig_id>";
                                    xml =
 xml + "<dob>" + (rs.getDate("date_of_birth")).getYear() + "</dob>";


                                    /***************  16-08-2007    ***********************/


                                    ps =
  con.prepareStatement("select DATE_EFFECTIVE_FROM,DATE_EFFECTIVE_FROM_SESSION  from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?");

                                    ps.setInt(1, eid);

                                    rs = ps.executeQuery();
                                    String maxfromdate = "";
                                    String maxsession = "";
                                    if (rs.next()) {
                                        if (rs.getDate("DATE_EFFECTIVE_FROM") !=
                                            null) {
                                            maxfromdate =
                                                    new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("DATE_EFFECTIVE_FROM"));
                                            System.out.println("max from date :" +
                                                               rs.getDate("DATE_EFFECTIVE_FROM"));
                                        } else {

                                            maxfromdate = "empty";
                                        }
                                        if (rs.getString("DATE_EFFECTIVE_FROM_SESSION") !=
                                            null) {
                                            maxsession =
                                                    rs.getString("DATE_EFFECTIVE_FROM_SESSION");
                                        } else {
                                            maxsession = "FN";
                                        }
                                    }
                                    xml =
 xml + "<maxfromdate>" + maxfromdate + "</maxfromdate><maxsession>" +
   maxsession + "</maxsession>";


                                    /********************************************************/

                                } else
                                    xml = xml + "<flag>failure</flag>";

                            }
                        }


                    }
                    //}
                } else {
                    xml = xml + "<flag>failure3</flag>";
                }
            } catch (Exception e) {
                System.out.println("catch..HERE.in load emp." + e);
                xml = xml + "<flag>failure</flag>";
            }


            xml = xml + "</response>";
            out.println(xml);

        }

        else if (command.equalsIgnoreCase("office")) {
            String offlevel = "";
            int oid = 0;
            String oname = "";
            int loginid = 0;

            System.out.println("inside office...");
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            xml = "<response><command>office</command>";
            System.out.println(xml);

            try {

                try {
                    oid = Integer.parseInt(request.getParameter("oid"));
                    loginid =
                            Integer.parseInt(request.getParameter("loginid"));
                    System.out.println("the login id is" + loginid);
                } catch (Exception e) {
                    System.out.println(e);
                }

                System.out.println("office id..." + oid);


                ps =
  con.prepareStatement("select office_level_id from com_mst_all_offices_view where office_id=?");
                ps.setInt(1, loginid);
                rs = ps.executeQuery();
                while (rs.next()) {
                    offlevel = rs.getString("office_level_id");
                    System.out.println("the office level id is" + offlevel);
                }
            } catch (Exception e) {
                System.out.println("catch..HERE.in load office." + e);
                xml = xml + "<flag>failure</flag>";
            }
            System.out.println("the office level is..................." +
                               offlevel);
            if (offlevel.equalsIgnoreCase("HO")) {
                try {

                    System.out.println("inside head office");
                    ps =
  con.prepareStatement("select OFFICE_NAME from com_mst_offices where OFFICE_ID=?");
                    ps.setInt(1, loginid);
                    rs = ps.executeQuery();
                    if (rs.next()) {
                        xml =
 xml + "<flag>success</flag><oid>" + oid + "</oid><oname>" +
   rs.getString("OFFICE_NAME") + "</oname>";
                    } else
                        xml =
 xml + "<flag>failure</flag><oid>" + oid + "</oid>";
                } catch (Exception e) {
                    System.out.println("the exceptin in region office load " +
                                       e.getMessage());
                }
            } else if (offlevel.equalsIgnoreCase("RN")) {
                try {

                    System.out.println("inside region");
                    ps =
  con.prepareStatement("select office_id,OFFICE_NAME,office_level_id from com_mst_all_offices_view where office_level_id in ('RN','AW','CR','LB','DN','SD') and REGION_OFFICE_ID=" +
                       loginid + " and office_id not in(" + loginid + ")");

                    rs = ps.executeQuery();
                    System.out.println("query executed");
                    int i = 0, count = 0;
                    String name = "";
                    while (rs.next()) {
                        i++;
                        if (rs.getInt("office_id") == oid) {
                            count++;
                        }
                        System.out.println("i:" + i);
                        name = rs.getString("OFFICE_NAME");
                    }
                    if (count > 0) {
                        xml = xml + "<flag>success</flag>";
                        xml =
 xml + "<oid>" + oid + "</oid><oname>" + name + "</oname>";
                    } else
                        xml =
 xml + "<flag>failure</flag><oid>" + oid + "</oid>";
                } catch (Exception e) {
                    System.out.println("the exceptin in region office load " +
                                       e.getMessage());
                }
            } else if (offlevel.equalsIgnoreCase("CL")) {
                try {

                    System.out.println("inside circle");
                    ps =
  con.prepareStatement("select office_id,OFFICE_NAME,office_level_id from com_mst_all_offices_view where office_level_id in ('CR','LB','DN','SD') and CIRCLE_OFFICE_ID=" +
                       loginid + " and office_id not in(" + loginid + ")");
                    rs = ps.executeQuery();
                    int i = 0, count = 0;
                    String name = "";
                    while (rs.next()) {
                        i++;
                        if (rs.getInt("office_id") == oid) {
                            count++;
                        }
                        System.out.println("i:" + i);
                        name = rs.getString("OFFICE_NAME");
                    }
                    if (count > 0) {
                        xml = xml + "<flag>success</flag>";
                        xml =
 xml + "<oid>" + oid + "</oid><oname>" + name + "</oname>";
                    } else
                        xml =
 xml + "<flag>failure</flag><oid>" + oid + "</oid>";
                } catch (Exception e) {
                    System.out.println("the exceptin in region office load " +
                                       e.getMessage());
                }
            }

            else if (offlevel.equalsIgnoreCase("AW")) {
                try {

                    System.out.println("inside audit wing");
                    ps =
  con.prepareStatement("select office_id,OFFICE_NAME,office_level_id from com_mst_all_offices_view where office_level_id in ('AW') and AUDITWING_OFFICE_ID=" +
                       loginid + " and office_id not in(" + loginid + ")");
                    ps.setInt(1, loginid);
                    rs = ps.executeQuery();
                    int i = 0, count = 0;
                    String name = "";
                    while (rs.next()) {
                        i++;
                        if (rs.getInt("office_id") == oid) {
                            count++;
                        }
                        System.out.println("i:" + i);
                        name = rs.getString("OFFICE_NAME");
                    }
                    if (count > 0) {
                        xml = xml + "<flag>success</flag>";
                        xml =
 xml + "<oid>" + oid + "</oid><oname>" + name + "</oname>";
                    } else
                        xml =
 xml + "<flag>failure</flag><oid>" + oid + "</oid>";
                } catch (Exception e) {
                    System.out.println("the exceptin in region office load " +
                                       e.getMessage());
                }
            }

            else if (offlevel.equalsIgnoreCase("DN")) {
                try {

                    System.out.println("inside division");
                    ps =
  con.prepareStatement("select office_id,OFFICE_NAME,office_level_id from com_mst_all_offices_view where office_level_id in ('DN','SD') and DIVISION_OFFICE_ID=" +
                       loginid + " and office_id not in(" + loginid + ")");
                    ps.setInt(1, loginid);
                    rs = ps.executeQuery();
                    int i = 0, count = 0;
                    String name = "";
                    while (rs.next()) {
                        i++;
                        if (rs.getInt("office_id") == oid) {
                            count++;
                        }
                        System.out.println("i:" + i);
                        name = rs.getString("OFFICE_NAME");
                    }
                    if (count > 0) {
                        xml = xml + "<flag>success</flag>";
                        xml =
 xml + "<oid>" + oid + "</oid><oname>" + name + "</oname>";
                    } else
                        xml =
 xml + "<flag>failure</flag><oid>" + oid + "</oid>";
                } catch (Exception e) {
                    System.out.println("the exceptin in region office load " +
                                       e.getMessage());
                }
            }

            else if (offlevel.equalsIgnoreCase("SD")) {
                try {

                    System.out.println("inside sub division");
                    ps =
  con.prepareStatement("select office_id,OFFICE_NAME,office_level_id from com_mst_all_offices_view where office_level_id in ('SD') and SUBDIVISION_OFFICE_ID=" +
                       loginid + " and office_id not in(" + loginid + ")");
                    ps.setInt(1, loginid);
                    rs = ps.executeQuery();
                    int i = 0, count = 0;
                    String name = "";
                    while (rs.next()) {
                        i++;
                        if (rs.getInt("office_id") == oid) {
                            count++;
                        }
                        System.out.println("i:" + i);
                        name = rs.getString("OFFICE_NAME");
                    }
                    if (count > 0) {
                        xml = xml + "<flag>success</flag>";
                        xml =
 xml + "<oid>" + oid + "</oid><oname>" + name + "</oname>";
                    } else
                        xml =
 xml + "<flag>failure</flag><oid>" + oid + "</oid>";
                } catch (Exception e) {
                    System.out.println("the exceptin in region office load " +
                                       e.getMessage());
                }
            }

            else if (offlevel.equalsIgnoreCase("LB")) {
                try {

                    System.out.println("inside lab");
                    ps =
  con.prepareStatement("select office_id,OFFICE_NAME,office_level_id from com_mst_all_offices_view where office_level_id in ('LB') and LAB_OFFICE_ID=" +
                       loginid + " and office_id not in(" + loginid + ")");
                    ps.setInt(1, loginid);
                    rs = ps.executeQuery();
                    int i = 0, count = 0;
                    String name = "";
                    while (rs.next()) {
                        i++;
                        if (rs.getInt("office_id") == oid) {
                            count++;
                        }
                        System.out.println("i:" + i);
                        name = rs.getString("OFFICE_NAME");
                    }
                    if (count > 0) {
                        xml = xml + "<flag>success</flag>";
                        xml =
 xml + "<oid>" + oid + "</oid><oname>" + name + "</oname>";
                    } else
                        xml =
 xml + "<flag>failure</flag><oid>" + oid + "</oid>";
                }

                catch (Exception e) {
                    System.out.println("the exceptin in region office load " +
                                       e.getMessage());
                }
            }

            System.out.println(xml);


            xml = xml + "</response>";
            out.println(xml);
        }

        else if (command.equalsIgnoreCase("getDet")) {

            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            xml = "<response><command>getDet</command>";

            int order_id = Integer.parseInt(request.getParameter("order"));
            System.out.println("order id..." + order_id);

            int office_id = Integer.parseInt(request.getParameter("offid"));
            System.out.println("office id..." + office_id);


            try {
                String sql =
                    "select REPOST_ISSUE_OFFICE_ID, REPOST_ORDER_ID, EMPLOYEE_ID, employee_name, TO_WHICH_OFFICE_ID, office_name,  \n" +
                    "REPOSTS_REQUIRED, TA_DA_ELIGIBLE, PROCEEDING_DATE, CATEGORY_ORDER_ID, CATEGORY_ID, PROCEEDING_NO, PROCEED_SUBJECT, PROCEED_REFERENCE, ADDL_PARA_1,  \n" +
                    "ADDL_PARA_2, COPY_TO, PRESIDING_OFFICER, PRESIDING_OFFICER_DESIGNATION, SUFFIX, INDIVIDUALLY_ADDRESSED, PREFIX, SIGNED_PO,\n" +
                    "JOINING_POST_ID,DESIGNATION,SERVICE_GROUP_ID, SERVICE_GROUP_NAME from  \n" +
                    "(  \n" +
                    "select REPOST_ISSUE_OFFICE_ID, REPOST_ORDER_ID, EMPLOYEE_ID, TO_WHICH_OFFICE_ID, REPOSTS_REQUIRED,  \n" +
                    "PROCESS_FLOW_STATUS_ID, TA_DA_ELIGIBLE,JOINING_POST_ID from HRM_REPOST_DETAILS  \n" +
                    "where PROCESS_FLOW_STATUS_ID in ('CR','MD')  \n" +
                    ") a  \n" + "left outer join  \n" + "(  \n" +
                    "select REPOST_ISSUE_OFFICE_ID as rpt_office_id, REPOST_ORDER_ID as rpt_order_id, PROCEEDING_DATE,  \n" +
                    "CATEGORY_ORDER_ID, CATEGORY_ID, PROCEEDING_NO, PROCEED_SUBJECT, PROCEED_REFERENCE, ADDL_PARA_1,  \n" +
                    "ADDL_PARA_2, COPY_TO, PRESIDING_OFFICER, PRESIDING_OFFICER_DESIGNATION, PROCESS_FLOW_STATUS_ID,  \n" +
                    "SUFFIX, INDIVIDUALLY_ADDRESSED, PREFIX, SIGNED_PO from HRM_REPOST_ORDERS  \n" +
                    "where PROCESS_FLOW_STATUS_ID in ('CR','MD')  \n" +
                    ") b  \n" +
                    "on a.REPOST_ISSUE_OFFICE_ID=b.rpt_office_id and a.REPOST_ORDER_ID=b.rpt_order_id  \n" +
                    "left outer join  \n" + "(  \n" +
                    "select employee_id as emp_id,employee_name || '.' || employee_initial as employee_name from  \n" +
                    "hrm_mst_employees  \n" + ") c  \n" +
                    "on a.EMPLOYEE_ID=c.emp_id  \n" + "left outer join  \n" +
                    "(  \n" +
                    "select office_id as off_id,office_name from com_mst_offices  \n" +
                    "where office_status_id not in ('CL','RD','NC')  \n" +
                    ") e  \n" + "on a.TO_WHICH_OFFICE_ID=e.off_id \n" +
                    "left outer join\n" + "(\n" +
                    "select DESIGNATION_ID,DESIGNATION,SERVICE_GROUP_ID from HRM_MST_DESIGNATIONS\n" +
                    ")f\n" + "on a.JOINING_POST_ID=f.DESIGNATION_ID\n" +
                    "left outer join\n" + "(\n" +
                    "select SERVICE_GROUP_ID as servid,SERVICE_GROUP_NAME from hrm_mst_service_group\n" +
                    ")g\n" + "on f.SERVICE_GROUP_ID=g.servid\n" +
                    "where a.REPOST_ISSUE_OFFICE_ID=? and a.REPOST_ORDER_ID=?";
                ps = con.prepareStatement(sql);
                ps.setInt(1, office_id);
                ps.setInt(2, order_id);

                rs = ps.executeQuery();
                int i = 0;
                PreparedStatement psa = null;
                ResultSet rsa = null;


                while (rs.next()) {
                    //xml=xml+"<flag>success</flag>";
                    xml =
 xml + "<proceed_no>" + rs.getString("PROCEEDING_NO") +
   "</proceed_no><proceed_date>" + rs.getDate("PROCEEDING_DATE") +
   "</proceed_date>";
                    xml =
 xml + "<pro_subj>" + rs.getString("PROCEED_SUBJECT") +
   "</pro_subj><pro_ref>" + rs.getString("PROCEED_REFERENCE") + "</pro_ref>";
                    xml =
 xml + "<add_par1>" + rs.getString("ADDL_PARA_1") + "</add_par1><add_para2>" +
   rs.getString("ADDL_PARA_2") + "</add_para2><copy_to>" +
   rs.getString("COPY_TO") + "</copy_to>";
                    xml =
 xml + "<pres_off>" + rs.getString("PRESIDING_OFFICER") +
   "</pres_off><pres_off_des>" +
   rs.getString("PRESIDING_OFFICER_DESIGNATION") + "</pres_off_des>";
                    xml =
 xml + "<suffix>" + rs.getString("SUFFIX") + "</suffix><sign_po>" +
   rs.getString("SIGNED_PO") + "</sign_po><ind_addr>" +
   rs.getString("INDIVIDUALLY_ADDRESSED") + "</ind_addr><prefix>" +
   rs.getString("PREFIX") + "</prefix>";

                    String categ = rs.getString("CATEGORY_ID");
                    int c_ord = rs.getInt("CATEGORY_ORDER_ID");

                    xml =
 xml + "<cat_order_id>" + c_ord + "</cat_order_id><cat_id>" + categ +
   "</cat_id>";

                    try {
                        if (categ.equals("TRN")) {
                            psa =
 con.prepareStatement("select PROCEEDING_NO,PROCEEDING_DATE from HRM_TRANSFER_ORDERS where TRANSFER_ORDER_ID=?");
                            psa.setInt(1, c_ord);
                            rsa = psa.executeQuery();

                            if (rsa.next()) {
                                xml =
 xml + "<cat_proc_no>" + rsa.getString("PROCEEDING_NO") +
   "</cat_proc_no><cat_proc_date>" + rsa.getDate("PROCEEDING_DATE") +
   "</cat_proc_date>";
                            } else {
                                xml = xml + "<flag>failureaa</flag>";
                            }

                        } else if (categ.equals("PRO")) {
                            psa =
 con.prepareStatement("select PROCEEDING_NO,PROCEEDING_DATE from HRM_PROMOTION_ORDERS where PROMOTION_ORDER_ID=?");
                            psa.setInt(1, c_ord);
                            rsa = psa.executeQuery();

                            if (rsa.next()) {
                                xml =
 xml + "<cat_proc_no>" + rsa.getString("PROCEEDING_NO") +
   "</cat_proc_no><cat_proc_date>" + rsa.getDate("PROCEEDING_DATE") +
   "</cat_proc_date>";
                            } else {
                                xml = xml + "<flag>failureaa</flag>";
                            }
                        } else {
                            psa =
 con.prepareStatement("select PROCEEDING_NO,PROCEEDING_DATE from HRM_REPOST_ORDERS where REPOST_ORDER_ID=?");
                            psa.setInt(1, c_ord);
                            rsa = psa.executeQuery();

                            if (rsa.next()) {
                                xml =
 xml + "<cat_proc_no>" + rsa.getString("PROCEEDING_NO") +
   "</cat_proc_no><cat_proc_date>" + rsa.getDate("PROCEEDING_DATE") +
   "</cat_proc_date>";
                            } else {
                                xml = xml + "<flag>failureaa</flag>";
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    } finally {
                        psa.close();
                        rsa.close();
                    }


                    xml =
 xml + "<details><cat_id>" + categ + "</cat_id><employee_id>" +
   rs.getInt("EMPLOYEE_ID") + "</employee_id><trans_office_id>" +
   rs.getInt("TO_WHICH_OFFICE_ID") + "</trans_office_id>";
                    xml =
 xml + "<emp_name>" + rs.getString("employee_name") + "</emp_name><off_name>" +
   rs.getString("office_name") + "</off_name>";
                    xml =
 xml + "<repost_req>" + rs.getString("REPOSTS_REQUIRED") + "</repost_req>";
                    xml =
 xml + "<ta_da>" + rs.getString("TA_DA_ELIGIBLE") + "</ta_da><ser_grp_id>" +
   rs.getString("SERVICE_GROUP_ID") + "</ser_grp_id><desig_id>" +
   rs.getInt("JOINING_POST_ID") + "</desig_id></details>";
                    i++;
                }

                if (i == 0) {
                    xml = xml + "<flag>failurea</flag>";
                } else {
                    xml = xml + "<flag>success</flag>";
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);

        }

        else if (command.equalsIgnoreCase("Update")) {
            System.out.println("inside update...");
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            xml = "<response><command>update</command>";
            System.out.println(xml);
            try {
                int empid = 0, off_id = 0, trf_offid = 0, order = 0, desig = 0;
                String repost = "", ta_da = "";

                empid =
Integer.parseInt(request.getParameter("txtEmployeeid"));
                off_id = Integer.parseInt(request.getParameter("offid"));
                trf_offid = Integer.parseInt(request.getParameter("trfoff"));
                repost = request.getParameter("repost");
                order = Integer.parseInt(request.getParameter("order"));
                ta_da = request.getParameter("req_trans");
                //desig=Integer.parseInt(request.getParameter("desig_id"));
                desig = Integer.parseInt(request.getParameter("desig"));
                System.out.println("employee id.." + empid);
                System.out.println("office id..." + off_id);
                System.out.println("transfer office id.." + trf_offid);
                System.out.println("repost..." + repost);
                System.out.println("order id..." + order);
                System.out.println("desig id.." + desig);

                String sql =
                    "update HRM_REPOST_DETAILS set TO_WHICH_OFFICE_ID=?,REPOSTS_REQUIRED=?,TA_DA_ELIGIBLE=?,PROCESS_FLOW_STATUS_ID=?,JOINING_POST_ID=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=? where REPOST_ISSUE_OFFICE_ID=? and EMPLOYEE_ID=? and REPOST_ORDER_ID=?";

                ps = con.prepareStatement(sql);
                ps.setInt(1, trf_offid);
                ps.setString(2, repost);
                ps.setString(3, ta_da);
                ps.setString(4, "MD");
                ps.setInt(5, desig);
                ps.setString(6, updatedby);
                ps.setTimestamp(7, ts);

                ps.setInt(8, off_id);
                ps.setInt(9, empid);
                ps.setInt(10, order);

                int z = ps.executeUpdate();

                if (z > 0) {
                    xml = xml + "<flag>success</flag>";
                } else {
                    xml = xml + "<flag>failure</flag>";
                }

                System.out.println(xml);
                ps.close();
                rs.close();
            } catch (Exception e) {
                System.out.println("catch..HERE.in load office." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            out.println(xml);
        }

        else if (command.equalsIgnoreCase("Add")) {
            System.out.println("inside add...");
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            xml = "<response><command>add</command>";
            System.out.println(xml);
            try {
                int empid = 0, off_id = 0, trf_offid = 0, order = 0, desig = 0;
                String repost = "", reason = "", ta_da = "", ind_proc = "";

                empid =
Integer.parseInt(request.getParameter("txtEmployeeid"));
                off_id = Integer.parseInt(request.getParameter("offid"));
                trf_offid = Integer.parseInt(request.getParameter("trfoff"));
                repost = request.getParameter("repost");
                order = Integer.parseInt(request.getParameter("order"));
                //desig=Integer.parseInt(request.getParameter("desig_id"));

                //reason=request.getParameter("reason_id");
                ta_da = request.getParameter("req_trans");
                //ind_proc=request.getParameter("indi_addr");
                desig = Integer.parseInt(request.getParameter("desig"));
                System.out.println("employee id.." + empid);
                System.out.println("office id..." + off_id);
                System.out.println("transfer office id.." + trf_offid);
                System.out.println("repost..." + repost);
                System.out.println("order id..." + order);
                System.out.println("desig id.." + desig);

                System.out.println("reason.." + reason);
                System.out.println("ta_da..." + ta_da);
                System.out.println("ind_proc...." + ind_proc);

                //String sql="update hrm_transfer_details set to_which_office_id=?,reposts_required=?,NEW_DESIGNATION=?,PROCESS_FLOW_STATUS_ID=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=? where transfer_issue_office_id=? and employee_id=? and transfer_order_id=?";

                String sql =
                    "insert into HRM_REPOST_DETAILS (REPOST_ISSUE_OFFICE_ID,REPOST_ORDER_ID,EMPLOYEE_ID,TO_WHICH_OFFICE_ID,REPOSTS_REQUIRED,PROCESS_FLOW_STATUS_ID,UPDATED_BY_USER_ID,UPDATED_DATE,TA_DA_ELIGIBLE,JOINING_POST_ID) values (?,?,?,?,?,?,?,?,?,?)";

                ps = con.prepareStatement(sql);
                ps.setInt(1, off_id);
                ps.setInt(2, order);
                ps.setInt(3, empid);
                ps.setInt(4, trf_offid);
                ps.setString(5, repost);
                //ps.setInt(6,desig);
                ps.setString(6, "CR");
                ps.setString(7, updatedby);
                ps.setTimestamp(8, ts);
                //ps.setString(10,reason);
                ps.setString(9, ta_da);
                ps.setInt(10, desig);

                int z = ps.executeUpdate();

                if (z > 0) {
                    xml = xml + "<flag>success</flag>";
                } else {
                    xml = xml + "<flag>failure</flag>";
                }

                System.out.println(xml);
                ps.close();
                rs.close();
            } catch (Exception e) {
                System.out.println("catch..HERE.in load office." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            out.println(xml);
        }
        /*
        else if(command.equalsIgnoreCase("ProcId"))
        {
            System.out.println("inside proced id...");
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            xml="<response><command>ProcId</command>";

            int off=0,ord_no=0;
            off=Integer.parseInt(request.getParameter("txtoffid"));
            System.out.println("office id..."+off);

            System.out.println(xml);
            try {

                String sql="select max(REPOST_ORDER_ID) as REPOST_ORDER_ID from HRM_REPOST_DETAILS where REPOST_ISSUE_OFFICE_ID=?";

                ps=con.prepareStatement(sql);
                ps.setInt(1,off);

                rs=ps.executeQuery();

                while(rs.next())
                {

                    ord_no=rs.getInt("REPOST_ORDER_ID");
                    System.out.println("order id..."+ord_no);
                }

                if(ord_no==0)
                {
                    ord_no=1;
                }
                else
                {
                    ord_no=ord_no+1;
                }

                xml=xml+"<flag>success</flag>";
                xml=xml+"<order_no>"+ord_no+"</order_no>";

               System.out.println(xml);
                ps.close();
                rs.close();
            }
            catch(Exception e)
            {
                System.out.println("catch..HERE.in load office."+e);
                xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
            out.println(xml);
        }
        */

        else if (command.equalsIgnoreCase("Delete")) {
            System.out.println("inside delete...");
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            xml = "<response><command>delete</command>";
            System.out.println(xml);
            try {
                int empid = 0, off_id = 0, order = 0;


                empid =
Integer.parseInt(request.getParameter("txtEmployeeid"));
                off_id = Integer.parseInt(request.getParameter("offid"));
                order = Integer.parseInt(request.getParameter("order"));

                System.out.println("employee id.." + empid);
                System.out.println("office id..." + off_id);
                System.out.println("order id..." + order);

                String sql =
                    "delete from HRM_REPOST_DETAILS where REPOST_ISSUE_OFFICE_ID=? and EMPLOYEE_ID=? and REPOST_ORDER_ID=?";

                ps = con.prepareStatement(sql);
                ps.setInt(1, off_id);
                ps.setInt(2, empid);
                ps.setInt(3, order);

                int z = ps.executeUpdate();

                if (z > 0) {
                    xml = xml + "<flag>success</flag>";
                } else {
                    xml = xml + "<flag>failure</flag>";
                }

                System.out.println(xml);
                ps.close();
                rs.close();
            } catch (Exception e) {
                System.out.println("catch..HERE.in load office." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            out.println(xml);
        }

        else if (command.equalsIgnoreCase("Desig")) {

            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            xml = "<response><command>Desig</command>";

            int desig_id =
                Integer.parseInt(request.getParameter("txtDesigid"));
            System.out.println("desig_id..." + desig_id);


            try {

                ps =
  con.prepareStatement("select DESIGNATION from HRM_MST_DESIGNATIONS where DESIGNATION_ID=?");
                ps.setInt(1, desig_id);
                rs = ps.executeQuery();


                if (rs.next()) {
                    xml =
 xml + "<flag>success</flag><designame>" + rs.getString("DESIGNATION") +
   "</designame>";
                } else {
                    xml = xml + "<flag>failure</flag>";
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);

        }

        else if (command.equalsIgnoreCase("SerGroup")) {
            System.out.println("inside service group");
            xml = "<response><command>SerGroup</command>";
            System.out.println(xml);
            try {
                String strdes = request.getParameter("cmbdes");
                int des = Integer.parseInt(strdes);

                System.out.println("id.." + des);

                ps =
  con.prepareStatement("select SERVICE_GROUP_ID from HRM_MST_DESIGNATIONS  where DESIGNATION_ID=?");
                ps.setInt(1, des);
                rs = ps.executeQuery();
                if (rs.next()) {
                    xml =
 xml + "<flag>success</flag><sid>" + rs.getInt(1) + "</sid>";
                    System.out.println(xml);
                } else {
                    xml = xml + "<flag>failure</flag>";
                }

            }

            catch (Exception e) {

                System.out.println("catch........" + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);


        }

        else if (command.equalsIgnoreCase("TRN")) {
            System.out.println("inside transfer");
            xml = "<response>";
            PreparedStatement ps4 = null;
            ResultSet rs4 = null;
            try {

                ps4 =
 con.prepareStatement("select distinct proceeding_no,transfer_order_id from HRM_TRANSFER_ORDERS where process_flow_status_id='FR' order by transfer_order_id desc");
                rs4 = ps4.executeQuery();
                int count = 0;
                xml = xml + "<flag>success</flag>";

                while (rs4.next()) {
                    xml =
 xml + "<option><id>" + rs4.getInt("transfer_order_id") + "</id><name>" +
   rs4.getString("proceeding_no") + "</name></option>";
                    count++;
                }
                /*
              while(rs.next())
              {
                xml=xml+"<option><id>"+rs.getInt("DESIGNATION_ID")+"</id><name>"+rs.getString("DESIGNATION")+"</name></option>";
                count++;
              }*/
                System.out.println("count::" + count);

                if (count == 0)
                    xml = "<response><flag>failure</flag>";
                else
                    xml = xml + "<flag>success</flag>";

            }

            catch (Exception e) {

                System.out.println("catch........" + e);
                xml = "<response><flag>failure</flag>";
            }

            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);


        }

        else if (command.equalsIgnoreCase("PRO")) {
            System.out.println("inside promotion");
            xml = "<response>";
            PreparedStatement ps6 = null;
            ResultSet rs6 = null;
            try {

                ps6 =
 con.prepareStatement("select distinct proceeding_no,promotion_order_id from HRM_PROMOTION_ORDERS where process_flow_status_id='FR' order by promotion_order_id desc");
                rs6 = ps6.executeQuery();
                int count = 0;
                xml = xml + "<flag>success</flag>";

                while (rs6.next()) {
                    xml =
 xml + "<option><id>" + rs6.getInt("promotion_order_id") + "</id><name>" +
   rs6.getString("proceeding_no") + "</name></option>";
                    count++;
                }

                System.out.println("count::" + count);

                if (count == 0)
                    xml = "<response><flag>failure</flag>";
                else
                    xml = xml + "<flag>success</flag>";

            }

            catch (Exception e) {

                System.out.println("catch........" + e);
                xml = "<response><flag>failure</flag>";
            }

            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);


        }

        else if (command.equalsIgnoreCase("RPT")) {

            System.out.println("inside repost");
            xml = "<response>";
            PreparedStatement ps7 = null;
            ResultSet rs7 = null;
            try {

                ps7 =
 con.prepareStatement("select distinct proceeding_no,repost_order_id from HRM_REPOST_ORDERS where process_flow_status_id='FR' order by repost_order_id desc");
                rs7 = ps7.executeQuery();
                int count = 0;
                xml = xml + "<flag>success</flag>";

                while (rs7.next()) {
                    xml =
 xml + "<option><id>" + rs7.getInt("repost_order_id") + "</id><name>" +
   rs7.getString("proceeding_no") + "</name></option>";
                    count++;
                }

                System.out.println("count::" + count);

                if (count == 0)
                    xml = "<response><flag>failure</flag>";
                else
                    xml = xml + "<flag>success</flag>";

            }

            catch (Exception e) {

                System.out.println("catch........" + e);
                xml = "<response><flag>failure</flag>";
            }

            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);


        }

        else if (command.equalsIgnoreCase("getDt")) {

            System.out.println("inside get detail");

            String cat = request.getParameter("category");
            int orde = Integer.parseInt(request.getParameter("pid"));

            System.out.println("category..." + cat);
            System.out.println("orde...." + orde);


            xml = "<response>";
            PreparedStatement ps8 = null;
            ResultSet rs8 = null;
            try {
                if (cat.equals("TRN")) {
                    ps8 =
 con.prepareStatement("select distinct proceeding_date,employee_id from " +
                      " ( " +
                      " select proceeding_date,transfer_order_id,transfer_issue_office_id from hrm_transfer_orders " +
                      " where process_flow_status_id='FR' " + " ) a " +
                      " left outer join " + " ( " +
                      " select employee_id,transfer_order_id as ord_id,transfer_issue_office_id as trf_office_id " +
                      " from hrm_transfer_details " +
                      " where process_flow_status_id='FR' " + " ) b " +
                      " on a.transfer_order_id=b.ord_id and a.transfer_issue_office_id=b.trf_office_id " +
                      " where a.transfer_order_id=?");
                }

                else if (cat.equals("PRO")) {
                    ps8 =
 con.prepareStatement("select distinct proceeding_date,employee_id from " +
                      " ( " +
                      " select proceeding_date,promotion_order_id,promotion_issue_office_id from hrm_promotion_orders " +
                      " where process_flow_status_id='FR' " + " ) a " +
                      " left outer join " + " ( " +
                      " select employee_id,promotion_order_id as ord_id,promotion_issue_office_id as pro_office_id " +
                      " from hrm_promotion_details " +
                      " where process_flow_status_id='FR' " + " ) b " +
                      " on a.promotion_order_id=b.ord_id and a.promotion_issue_office_id=b.pro_office_id " +
                      " where a.promotion_order_id=?");
                }

                else {
                    ps8 =
 con.prepareStatement("select distinct proceeding_date,employee_id from " +
                      " ( " +
                      " select proceeding_date,repost_order_id,repost_issue_office_id from hrm_repost_orders " +
                      " where process_flow_status_id='FR' " + " ) a " +
                      " left outer join " + " ( " +
                      " select employee_id,repost_order_id as ord_id,repost_issue_office_id as rpst_office_id " +
                      " from hrm_repost_details " +
                      " where process_flow_status_id='FR' " + " ) b " +
                      " on a.repost_order_id=b.ord_id and a.repost_issue_office_id=b.rpst_office_id " +
                      " where a.repost_order_id=?");
                }

                ps8.setInt(1, orde);
                rs8 = ps8.executeQuery();
                int i = 0;


                while (rs8.next()) {
                    xml = xml + "<flag>success</flag>";
                    xml =
 xml + "<pro_date>" + rs8.getDate("proceeding_date") + "</pro_date>";
                    xml =
 xml + "<option><employee_id>" + rs8.getInt("employee_id") +
   "</employee_id></option>";
                    i++;
                }

                System.out.println("i::" + i);

                if (i == 0) {
                    xml = xml + "<flag>failurea</flag>";
                } else {
                    xml = xml + "<flag>success</flag>";
                }

            }

            catch (Exception e) {

                System.out.println("catch........" + e);
                xml = "<response><flag>failure</flag>";
            }

            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);


        }


        out.close();
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        response.setContentType(CONTENT_TYPE);

        PrintWriter out = response.getWriter();

        System.out.println("inside post");

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            ResourceBundle rb =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";

            String strDriver = rb.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rb.getString("Config.DSN");
            String strhostname = rb.getString("Config.HOST_NAME");
            String strportno = rb.getString("Config.PORT_NUMBER");
            String strsid = rb.getString("Config.SID");
            String strdbusername = rb.getString("Config.USER_NAME");
            String strdbpassword = rb.getString("Config.PASSWORD");

            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection


            Class.forName(strDriver.trim());
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
        } catch (Exception e) {
            System.out.println("Exception in connection...." + e);
        }

        HttpSession session = request.getSession(false);

        try {

            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");

            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }

        String updatedby = (String)session.getAttribute("UserId");
        System.out.println("user id..." + updatedby);

        long l = System.currentTimeMillis();
        java.sql.Timestamp ts = new java.sql.Timestamp(l);

        System.out.println("ts.." + ts);

        int proc_id = 0, iss_off_id = 0;
        String proc_no = "", pres_officer = "", pres_desig = "", subj =
            "", ref = "", add_par1 = "", add_par2 = "", cop_to = "", suffix =
            "", sign = "", prefix = "", indiv_addr = "", ref_category =
            "", ref_proceeding = "";
        Date proc_date = null;
        Calendar c;

        /*
        String[] sd=request.getParameter("txtDORelieval").split("/");
        c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
        java.util.Date d=c.getTime();
        txtDORelieval=new Date(d.getTime());*/

        String command = request.getParameter("Command");
        System.out.println("command.." + command);

        iss_off_id = Integer.parseInt(request.getParameter("txtOffId"));
        System.out.println("office id.." + iss_off_id);

        int ord_id = Integer.parseInt(request.getParameter("txtPid"));
        System.out.println("order id..." + ord_id);

        proc_no = request.getParameter("txtRno");
        System.out.println("proceed reference no....." + proc_no);

        String frm_proc_dat = request.getParameter("txtPDat");
        System.out.println("proc date...." + frm_proc_dat);

        if (frm_proc_dat.equals(null) || frm_proc_dat.equals("")) {
            proc_date = null;
        } else {
            String[] sd = request.getParameter("txtPDat").split("/");
            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
            java.util.Date d = c.getTime();
            proc_date = new Date(d.getTime());
        }

        System.out.println("proc date..." + proc_date);

        prefix = request.getParameter("txtPref");
        System.out.println("prefix...." + prefix);

        pres_officer = request.getParameter("txtPO");
        System.out.println("presiding officer..." + pres_officer);

        suffix = request.getParameter("txtSuf");
        System.out.println("suffix..." + suffix);

        pres_desig = request.getParameter("txtPOD");
        System.out.println("presid officer designation..." + pres_desig);

        subj = request.getParameter("txtSub");
        System.out.println("subject..." + subj);

        ref = request.getParameter("txtRef");
        System.out.println("refrence..." + ref);

        add_par1 = request.getParameter("txtAdd1");
        System.out.println("addl para 1..." + add_par1);

        add_par2 = request.getParameter("txtAdd2");
        System.out.println("addl para 2..." + add_par2);

        cop_to = request.getParameter("txtcopy");
        System.out.println("copy to..." + cop_to);

        indiv_addr = request.getParameter("rad_indi");
        System.out.println("indivi addre..." + indiv_addr);

        /*
        ref_category=request.getParameter("rad_ROC");
        ref_proceeding=request.getParameter("txtCatPro");
        */


        if (command.equalsIgnoreCase("Edit")) {

            try {
                String sql =
                    "UPDATE HRM_REPOST_ORDERS SET PROCEEDING_DATE=?,PROCEEDING_NO=?,PROCEED_SUBJECT=?," +
                    " PROCEED_REFERENCE=?,ADDL_PARA_1=?,ADDL_PARA_2=?,COPY_TO=?,PRESIDING_OFFICER=?," +
                    " PRESIDING_OFFICER_DESIGNATION=?,PROCESS_FLOW_STATUS_ID=?,UPDATED_BY_USER_ID=?," +
                    " UPDATED_DATE=?,SUFFIX=?,PREFIX=?,INDIVIDUALLY_ADDRESSED=?" +
                    " WHERE REPOST_ISSUE_OFFICE_ID=? and REPOST_ORDER_ID=?";


                System.out.println("sql..." + sql);
                ps = con.prepareStatement(sql);
                ps.setDate(1, proc_date);
                ps.setString(2, proc_no);
                ps.setString(3, subj);
                ps.setString(4, ref);
                ps.setString(5, add_par1);
                ps.setString(6, add_par2);
                ps.setString(7, cop_to);
                ps.setString(8, pres_officer);
                ps.setString(9, pres_desig);
                ps.setString(10, "MD");
                ps.setString(11, updatedby);
                ps.setTimestamp(12, ts);
                ps.setString(13, suffix);
                ps.setString(14, prefix);
                ps.setString(15, indiv_addr);
                ps.setInt(16, iss_off_id);
                ps.setInt(17, ord_id);


                int a = ps.executeUpdate();

                System.out.println("value of a..." + a);

                if (a > 0) {
                    String msg = "Data has been Updated Successfully.";
                    msg = msg + "<br><br>";

                    sendMessage(response, msg, "ok");
                } else {
                    System.out.println("error in updating");
                    sendMessage(response, "Failed to insert values", "back");
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
                sendMessage(response, "Failed to insert values", "back");
            }
        }

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
