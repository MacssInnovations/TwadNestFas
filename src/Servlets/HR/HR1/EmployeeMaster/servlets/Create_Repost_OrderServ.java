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


public class Create_Repost_OrderServ extends HttpServlet {
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

            String ord_cat = request.getParameter("ord_cat");
            System.out.println("the order id is:" + ord_cat);

            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            //int offid=0;
            //offid=Integer.parseInt(request.getParameter("offid"));
            xml = "<response><command>loademp</command>";

            if (ord_cat.equalsIgnoreCase("TRN")) {
                try {
                    ps =
  con.prepareStatement("select employee_id,employee_name,designation_id,designation,office_name,reposts_required,ta_da_eligible,REASON_ID,NEW_DESIGNATION,designation,SERVICE_GROUP_ID,SERVICE_GROUP_NAME from \n" +
                       "(select employee_id,reposts_required,ta_da_eligible,NEW_DESIGNATION,REASON_ID from hrm_transfer_details)a\n" +
                       "left outer join\n" +
                       "(select employee_id as emp_id,employee_name || decode(EMPLOYEE_INITIAL,null,' ','.'||EMPLOYEE_INITIAL) as employee_name \n" +
                       "from hrm_mst_employees) b on a.employee_id=b.emp_id \n" +
                       "left outer join\n" +
                       "(select office_id,employee_id as empid, designation_id from hrm_emp_current_posting)c\n" +
                       "on a.employee_id=c.empid \n" + "left outer join\n" +
                       "(select designation_id,designation from HRM_MST_DESIGNATIONS)d on c.designation_id=d.designation_id \n" +
                       "left outer join\n" +
                       "(select office_id,office_name from com_mst_offices)e on c.office_id=e.office_id \n" +
                       "left outer join\n" +
                       "(select designation_id as desig,designation,service_group_id from hrm_mst_designations)f\n" +
                       "on a.NEW_DESIGNATION=f.desig\n" + "left outer join\n" +
                       "(select SERVICE_GROUP_ID as serviceid,SERVICE_GROUP_NAME from hrm_mst_service_group)g\n" +
                       "on f.service_group_id=g.serviceid\n" +
                       "where a.employee_id=" + eid + "");
                    //System.out.println("select employee_id,employee_name,designation_id,designation,office_name,reposts_required,ta_da_eligible from (select employee_id,reposts_required,ta_da_eligible from hrm_transfer_details)a left outer join (select employee_id as emp_id,employee_name || decode(EMPLOYEE_INITIAL,null,' ','.'||EMPLOYEE_INITIAL) as employee_name from hrm_mst_employees) b on a.employee_id=b.emp_id left outer join (select office_id,employee_id as empid, designation_id from hrm_emp_current_posting)c on a.employee_id=c.empid left outer join (select designation_id,designation from HRM_MST_DESIGNATIONS)d on c.designation_id=d.designation_id  left outer join(select office_id,office_name from com_mst_offices)e on c.office_id=e.office_id  where a.employee_id="+eid+"");
                    rs = ps.executeQuery();
                    System.out.println("query executed");
                    if (rs.next()) {
                        System.out.println("in if loop");
                        xml =
 xml + "<flag>success</flag><eid>" + eid + "</eid><ename>" +
   rs.getString("EMPLOYEE_NAME") + "</ename><desig>" +
   rs.getString("DESIGNATION") + "</desig><curr_post>" +
   rs.getString("office_name") + "</curr_post><desig_id>" +
   rs.getInt("designation_id") + "</desig_id>";
                        System.out.println("1" + xml);
                        xml =
 xml + "<repost_req>" + rs.getString("reposts_required") +
   "</repost_req><ta_da>" + rs.getString("ta_da_eligible") +
   "</ta_da><reason>" + rs.getString("REASON_ID") + "</reason><desigid>" +
   rs.getInt("NEW_DESIGNATION") + "</desigid>";
                        System.out.println("2" + xml);
                        xml =
 xml + "<desig>" + rs.getString("designation") + "</desig><serviceid>" +
   rs.getString("SERVICE_GROUP_ID") + "</serviceid><servicename>" +
   rs.getString("SERVICE_GROUP_NAME") + "</servicename>";
                        System.out.println("3" + xml);
                    } else {
                        xml = xml + "<flag>failure</flag>";
                    }
                } catch (Exception e) {
                    System.out.println("the exception in employee details is" +
                                       e.getMessage());
                }
            }

            else if (ord_cat.equalsIgnoreCase("PRO")) {
                try {
                    ps =
  con.prepareStatement("select employee_id,employee_name,designation_id,designation,office_name,REPOSTS_REQUIRED,TA_DA_ELIGIBLE from\n" +
                       "(\n" +
                       "select employee_id,REPOSTS_REQUIRED,TA_DA_ELIGIBLE from HRM_PROMOTION_DETAILS\n" +
                       ")a\n" + "left outer join\n" + "(\n" +
                       " select employee_id as emp_id,employee_name || decode(EMPLOYEE_INITIAL,null,' ','.'||EMPLOYEE_INITIAL) as employee_name   \n" +
                       "from hrm_mst_employees  \n" + ") b    \n" +
                       "on a.employee_id=b.emp_id  \n" +
                       "left outer join   \n" +
                       "(select office_id,employee_id as empid, designation_id from hrm_emp_current_posting   \n" +
                       ")c  \n" + "on a.employee_id=c.empid   \n" +
                       "left outer join  \n" + "(  \n" +
                       "select designation_id,designation from HRM_MST_DESIGNATIONS  \n" +
                       ")d\n" + "on c.designation_id=d.designation_id   \n" +
                       "left outer join   \n" + "(  \n" +
                       "select office_id,office_name from com_mst_offices  \n" +
                       ")e  \n" +
                       "on c.office_id=e.office_id  where a.employee_id=" +
                       eid + "");
                    rs = ps.executeQuery();
                    if (rs.next()) {

                        xml =
 xml + "<flag>success</flag><eid>" + eid + "</eid><ename>" +
   rs.getString("EMPLOYEE_NAME") + "</ename><desig>" +
   rs.getString("DESIGNATION") + "</desig><curr_post>" +
   rs.getString("office_name") + "</curr_post><desig_id>" +
   rs.getInt("designation_id") + "</desig_id>";
                        xml =
 xml + "<repost_req>" + rs.getString("REPOSTS_REQUIRED") +
   "</repost_req><ta_da>" + rs.getString("TA_DA_ELIGIBLE") + "</ta_da>";
                    } else {
                        xml = xml + "<flag>failure</flag>";
                    }
                } catch (Exception e) {
                    System.out.println("the exception in employee details is" +
                                       e.getMessage());
                }
            }

            else if (ord_cat.equalsIgnoreCase("RPT")) {
                try {
                    ps =
  con.prepareStatement("select employee_id,employee_name,designation_id,designation,office_name,REPOSTS_REQUIRED,TA_DA_ELIGIBLE from\n" +
                       "(\n" +
                       "select employee_id,REPOSTS_REQUIRED,TA_DA_ELIGIBLE from HRM_REPOST_DETAILS\n" +
                       ")a\n" + "left outer join\n" + "(\n" +
                       " select employee_id as emp_id,employee_name || decode(EMPLOYEE_INITIAL,null,' ','.'||EMPLOYEE_INITIAL) as employee_name   \n" +
                       "from hrm_mst_employees  \n" + ") b    \n" +
                       "on a.employee_id=b.emp_id  \n" +
                       "left outer join   \n" +
                       "(select office_id,employee_id as empid, designation_id from hrm_emp_current_posting   \n" +
                       ")c  \n" + "on a.employee_id=c.empid   \n" +
                       "left outer join  \n" + "(  \n" +
                       "select designation_id,designation from HRM_MST_DESIGNATIONS  \n" +
                       ")d\n" + "on c.designation_id=d.designation_id   \n" +
                       "left outer join   \n" + "(  \n" +
                       "select office_id,office_name from com_mst_offices  \n" +
                       ")e  \n" +
                       "on c.office_id=e.office_id  where a.employee_id=" +
                       eid + "");
                    rs = ps.executeQuery();
                    if (rs.next()) {

                        xml =
 xml + "<flag>success</flag><eid>" + eid + "</eid><ename>" +
   rs.getString("EMPLOYEE_NAME") + "</ename><desig>" +
   rs.getString("DESIGNATION") + "</desig><curr_post>" +
   rs.getString("office_name") + "</curr_post><desig_id>" +
   rs.getInt("designation_id") + "</desig_id>";
                        xml =
 xml + "<repost_req>" + rs.getString("REPOSTS_REQUIRED") +
   "</repost_req><ta_da>" + rs.getString("TA_DA_ELIGIBLE") + "</ta_da>";
                    } else {
                        xml = xml + "<flag>failure</flag>";
                    }
                } catch (Exception e) {
                    System.out.println("the exception in employee details is" +
                                       e.getMessage());
                }
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
  con.prepareStatement("select office_id,OFFICE_NAME,office_level_id from com_mst_all_offices_view where office_level_id in ('RN','AW','CL','LB','DN','SD') and REGION_OFFICE_ID=" +
                       loginid + " and office_id not in(" + loginid + ")");

                    rs = ps.executeQuery();
                    System.out.println("query executed");
                    int i = 0, count = 0;
                    String name = "";
                    while (rs.next()) {
                        i++;
                        if (rs.getInt("office_id") == oid) {
                            count++;
                            name = rs.getString("OFFICE_NAME");
                        }
                        System.out.println("i:" + i);

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
  con.prepareStatement("select office_id,OFFICE_NAME,office_level_id from com_mst_all_offices_view where office_level_id in ('CL','LB','DN','SD') and CIRCLE_OFFICE_ID=" +
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
                    "select employee_id,to_which_office_id,proceeding_no,proceeding_date,reposts_required,new_designation," +
                    " employee_name,designation,office_name,PRESIDING_OFFICER,PRESIDING_OFFICER_DESIGNATION,PROCEED_SUBJECT,PROCEED_REFERENCE,ADDL_PARA_1,ADDL_PARA_2,COPY_TO from " +
                    " ( " +
                    " select transfer_issue_office_id,transfer_order_id,employee_id,to_which_office_id,new_designation," +
                    " reposts_required,process_flow_status_id from hrm_transfer_details where process_flow_status_id in ('CR','MD') " +
                    " ) a" + " left outer join" + " ( " +
                    " select transfer_issue_office_id as trf_issue_offid,transfer_order_id as trf_ord_id,proceeding_no, " +
                    " proceeding_date,PROCEED_SUBJECT,PROCEED_REFERENCE,ADDL_PARA_1,ADDL_PARA_2,COPY_TO,PRESIDING_OFFICER,PRESIDING_OFFICER_DESIGNATION,PROCESS_FLOW_STATUS_ID from hrm_transfer_orders where process_flow_status_id in ('CR','MD')" +
                    " ) b " +
                    " on a.transfer_issue_office_id=b.trf_issue_offid and a.transfer_order_id=b.trf_ord_id " +
                    " left outer join " + " ( " +
                    " select employee_id as emp_id,employee_name || '.' || employee_initial as employee_name from " +
                    " hrm_mst_employees" + " ) c " +
                    " on a.employee_id=c.emp_id " + " left outer join " +
                    " ( " +
                    " select designation_id as desig_id, designation from hrm_mst_designations " +
                    " ) d " + " on a.new_designation=d.desig_id " +
                    "  left outer join " + " ( " +
                    " select office_id as off_id,office_name from com_mst_offices " +
                    " where office_status_id not in ('CL','RD','NC') " +
                    " ) e " + " on a.to_which_office_id=e.off_id " +
                    " where a.transfer_issue_office_id=? and a.transfer_order_id=?";
                ps = con.prepareStatement(sql);
                ps.setInt(1, office_id);
                ps.setInt(2, order_id);

                rs = ps.executeQuery();
                int i = 0;

                while (rs.next()) {
                    //xml=xml+"<flag>success</flag>";
                    xml =
 xml + "<proceed_no>" + rs.getString("proceeding_no") +
   "</proceed_no><proceed_date>" + rs.getDate("proceeding_date") +
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
 xml + "<details><employee_id>" + rs.getInt("employee_id") +
   "</employee_id><trans_office_id>" + rs.getInt("to_which_office_id") +
   "</trans_office_id><desig_id>" + rs.getInt("new_designation") +
   "</desig_id>";
                    xml =
 xml + "<emp_name>" + rs.getString("employee_name") + "</emp_name><desig>" +
   rs.getString("designation") + "</desig><off_name>" +
   rs.getString("office_name") + "</off_name>";
                    xml =
 xml + "<repost_req>" + rs.getString("reposts_required") +
   "</repost_req></details>";
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
                String repost = "";

                empid =
Integer.parseInt(request.getParameter("txtEmployeeid"));
                off_id = Integer.parseInt(request.getParameter("offid"));
                trf_offid = Integer.parseInt(request.getParameter("trfoff"));
                repost = request.getParameter("repost");
                order = Integer.parseInt(request.getParameter("order"));
                desig = Integer.parseInt(request.getParameter("desig_id"));

                System.out.println("employee id.." + empid);
                System.out.println("office id..." + off_id);
                System.out.println("transfer office id.." + trf_offid);
                System.out.println("repost..." + repost);
                System.out.println("order id..." + order);
                System.out.println("desig id.." + desig);

                String sql =
                    "update hrm_transfer_details set to_which_office_id=?,reposts_required=?,NEW_DESIGNATION=?,PROCESS_FLOW_STATUS_ID=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=? where transfer_issue_office_id=? and employee_id=? and transfer_order_id=?";

                ps = con.prepareStatement(sql);
                ps.setInt(1, trf_offid);
                ps.setString(2, repost);
                ps.setInt(3, desig);
                ps.setString(4, "MD");
                ps.setString(5, updatedby);
                ps.setTimestamp(6, ts);
                ps.setInt(7, off_id);
                ps.setInt(8, empid);
                ps.setInt(9, order);

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
                System.out.println("the designation is" + desig);

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

        else if (command.equalsIgnoreCase("ProcId")) {
            System.out.println("inside proced id...");
            /*String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);*/
            xml = "<response><command>ProcId</command>";

            int off = 0, ord_no = 0;
            off = Integer.parseInt(request.getParameter("txtoffid"));
            System.out.println("office id..." + off);

            System.out.println(xml);
            try {

                String sql =
                    "select max(REPOST_ORDER_ID) as REPOST_ORDER_ID from HRM_REPOST_DETAILS where REPOST_ISSUE_OFFICE_ID=?";

                ps = con.prepareStatement(sql);
                ps.setInt(1, off);

                rs = ps.executeQuery();

                while (rs.next()) {

                    ord_no = rs.getInt("REPOST_ORDER_ID");
                    System.out.println("order id..." + ord_no);
                }

                if (ord_no == 0) {
                    ord_no = 1;
                } else {
                    ord_no = ord_no + 1;
                }

                xml = xml + "<flag>success</flag>";
                xml = xml + "<order_no>" + ord_no + "</order_no>";

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
                    "delete from hrm_transfer_details where transfer_issue_office_id=? and employee_id=? and transfer_order_id=?";

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
            int off_id = Integer.parseInt(request.getParameter("off_id"));
            System.out.println("the office id is" + off_id);
            try {

                ps4 =
 con.prepareStatement("select distinct proceeding_no,transfer_order_id,to_which_office_id from\n" +
                      "(\n" +
                      "select distinct proceeding_no,TRANSFER_ISSUE_OFFICE_ID,transfer_order_id from HRM_TRANSFER_ORDERS \n" +
                      "where process_flow_status_id='FR' order by transfer_order_id desc\n" +
                      ")a\n" + "left outer join\n" +
                      "(select transfer_order_id as transid,TRANSFER_ISSUE_OFFICE_ID,to_which_office_id\n" +
                      "from hrm_transfer_details where process_flow_status_id='FR' )b\n" +
                      "on a.transfer_order_id=b.transid and a.TRANSFER_ISSUE_OFFICE_ID=b.TRANSFER_ISSUE_OFFICE_ID\n" +
                      "where b.to_which_office_id=" + off_id + "");
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
            int off_id = Integer.parseInt(request.getParameter("off_id"));
            System.out.println("the office id is" + off_id);
            try {

                ps6 =
 con.prepareStatement("select distinct proceeding_no,promotion_order_id,TO_WHICH_OFFICE_ID from\n" +
                      "(\n" +
                      "select distinct proceeding_no,PROMOTION_ISSUE_OFFICE_ID,promotion_order_id from HRM_PROMOTION_ORDERS\n" +
                      "where process_flow_status_id='FR' order by promotion_order_id desc\n" +
                      ")a\n" + "left outer join\n" + "(\n" +
                      "select PROMOTION_ISSUE_OFFICE_ID,PROMOTION_ORDER_ID as promid,TO_WHICH_OFFICE_ID from \n" +
                      "HRM_PROMOTION_DETAILS where process_flow_status_id='FR' \n" +
                      ")b\n" +
                      "on a.PROMOTION_ISSUE_OFFICE_ID=b.PROMOTION_ISSUE_OFFICE_ID and a.promotion_order_id=b.promid\n" +
                      "where b.to_which_office_id=" + off_id + "");
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
            int off_id = Integer.parseInt(request.getParameter("off_id"));
            System.out.println("the office id is" + off_id);
            try {

                ps7 =
 con.prepareStatement("select distinct proceeding_no,repost_order_id,TO_WHICH_OFFICE_ID from\n" +
                      "(\n" +
                      "select distinct proceeding_no,repost_order_id,REPOST_ISSUE_OFFICE_ID from HRM_REPOST_ORDERS \n" +
                      "where process_flow_status_id='FR' order by repost_order_id desc\n" +
                      ")a\n" + "left outer join\n" + "(\n" +
                      "select REPOST_ISSUE_OFFICE_ID,REPOST_ORDER_ID as repostid,TO_WHICH_OFFICE_ID from hrm_repost_details\n" +
                      ")b\n" +
                      "on a.REPOST_ISSUE_OFFICE_ID=b.REPOST_ISSUE_OFFICE_ID and a.repost_order_id=b.repostid\n" +
                      "where b.TO_WHICH_OFFICE_ID=" + off_id + "");
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
            int off_id = Integer.parseInt(request.getParameter("off_id"));
            System.out.println("the office id is" + off_id);
            System.out.println("category..." + cat);
            System.out.println("orde...." + orde);


            xml = "<response>";
            PreparedStatement ps8 = null;
            ResultSet rs8 = null;
            try {
                if (cat.equals("TRN")) {
                    ps8 =
 con.prepareStatement("select distinct proceeding_date,employee_id from   \n" +
                      "(   \n" +
                      "select proceeding_date,transfer_order_id,transfer_issue_office_id from hrm_transfer_orders   \n" +
                      "where process_flow_status_id='FR'   \n" + ") a   \n" +
                      "left outer join   \n" + "(   \n" +
                      "select employee_id,transfer_order_id as ord_id,transfer_issue_office_id as trf_office_id,TO_WHICH_OFFICE_ID   \n" +
                      "from hrm_transfer_details   \n" +
                      "where process_flow_status_id='FR'   \n" + ") b   \n" +
                      "on a.transfer_order_id=b.ord_id and a.transfer_issue_office_id=b.trf_office_id  where a.transfer_order_id=? and b.TO_WHICH_OFFICE_ID=?");
                }

                else if (cat.equals("PRO")) {
                    ps8 =
 con.prepareStatement("select distinct proceeding_date,employee_id from   \n" +
                      "(   \n" +
                      "select proceeding_date,promotion_order_id,promotion_issue_office_id from hrm_promotion_orders   \n" +
                      "where process_flow_status_id='FR'   \n" + ") a   \n" +
                      "left outer join   \n" + "(   \n" +
                      "select employee_id,promotion_order_id as ord_id,promotion_issue_office_id as pro_office_id,\n" +
                      "TO_WHICH_OFFICE_ID from hrm_promotion_details   \n" +
                      "where process_flow_status_id='FR'   \n" + ") b   \n" +
                      "on a.promotion_order_id=b.ord_id and a.promotion_issue_office_id=b.pro_office_id   \n" +
                      "where a.promotion_order_id=? and b.TO_WHICH_OFFICE_ID=?");
                }

                else {
                    ps8 =
 con.prepareStatement("select distinct proceeding_date,employee_id from   \n" +
                      "(   \n" +
                      "select proceeding_date,repost_order_id,repost_issue_office_id from hrm_repost_orders   \n" +
                      "where process_flow_status_id='FR'   \n" + ") a   \n" +
                      "left outer join   \n" + "(   \n" +
                      "select employee_id,repost_order_id as ord_id,repost_issue_office_id as rpst_office_id,\n" +
                      "TO_WHICH_OFFICE_ID from hrm_repost_details   \n" +
                      "where process_flow_status_id='FR'   \n" + ") b   \n" +
                      "on a.repost_order_id=b.ord_id and a.repost_issue_office_id=b.rpst_office_id   \n" +
                      "where a.repost_order_id=? and b.TO_WHICH_OFFICE_ID=?");
                }

                ps8.setInt(1, orde);
                ps8.setInt(2, off_id);
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

        int proc_id = 0, iss_off_id = 0, ref_proceeding = 0;
        String proc_no = "", pres_officer = "", pres_desig = "", subj =
            "", ref = "", add_par1 = "", add_par2 = "", cop_to = "", suffix =
            "", sign = "", prefix = "", indiv_addr = "", ref_category = "";
        Date proc_date = null;
        Calendar c;

        /*
        String[] sd=request.getParameter("txtDORelieval").split("/");
        c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
        java.util.Date d=c.getTime();
        txtDORelieval=new Date(d.getTime());*/

        String command = request.getParameter("Command");
        System.out.println("command.." + command);


        //proc_id=Integer.parseInt(request.getParameter("txtPid"));
        proc_no = request.getParameter("txtRno");

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

        ref_category = request.getParameter("rad_ROC");
        ref_proceeding =
                Integer.parseInt(request.getParameter("cmbProceeding"));


        pres_officer = request.getParameter("txtPO");
        pres_desig = request.getParameter("txtPOD");
        subj = request.getParameter("txtSub");
        ref = request.getParameter("txtRef");
        add_par1 = request.getParameter("txtAdd1");
        add_par2 = request.getParameter("txtAdd2");
        cop_to = request.getParameter("txtcopy");
        iss_off_id = Integer.parseInt(request.getParameter("txtOffId"));
        suffix = request.getParameter("txtSuf");
        sign = request.getParameter("rad_sig");
        prefix = request.getParameter("txtPref");
        indiv_addr = request.getParameter("rad_indi");

        System.out.println("ref category..." + ref_category);
        System.out.println("ref_proceeding..." + ref_proceeding);
        System.out.println("proc_id..." + proc_id);
        System.out.println("proc_no..." + proc_no);
        System.out.println("proc_date" + proc_date);
        System.out.println("pres_officer..." + pres_officer);
        System.out.println("pres_desig..." + pres_desig);
        System.out.println("subj..." + subj);
        System.out.println("ref..." + ref);
        System.out.println("add_par1..." + add_par1);
        System.out.println("add_par2..." + add_par2);
        System.out.println("cop_to..." + cop_to);
        System.out.println("office id..." + iss_off_id);
        System.out.println("suffix..." + suffix);
        System.out.println("sign..." + sign);
        System.out.println("prefix..." + prefix);
        System.out.println("indiv_addr..." + indiv_addr);

        int ord_id = Integer.parseInt(request.getParameter("txthPid"));
        System.out.println("order id..." + ord_id);

        if (command.equalsIgnoreCase("Insert")) {

            try {
                String sql =
                    "insert into HRM_REPOST_ORDERS (REPOST_ISSUE_OFFICE_ID,REPOST_ORDER_ID,PROCEEDING_DATE,PROCEEDING_NO,PROCEED_SUBJECT,PROCEED_REFERENCE,ADDL_PARA_1,ADDL_PARA_2,COPY_TO,PRESIDING_OFFICER,PRESIDING_OFFICER_DESIGNATION,PROCESS_FLOW_STATUS_ID,UPDATED_BY_USER_ID,UPDATED_DATE,SUFFIX,PREFIX,INDIVIDUALLY_ADDRESSED,CATEGORY_ORDER_ID,CATEGORY_ID) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                ps = con.prepareStatement(sql);

                ps.setInt(1, iss_off_id);
                ps.setInt(2, ord_id);
                ps.setDate(3, proc_date);
                ps.setString(4, proc_no);
                ps.setString(5, subj);
                ps.setString(6, ref);
                ps.setString(7, add_par1);
                ps.setString(8, add_par2);
                ps.setString(9, cop_to);
                ps.setString(10, pres_officer);
                ps.setString(11, pres_desig);
                ps.setString(12, "CR");
                ps.setString(13, updatedby);
                ps.setTimestamp(14, ts);
                ps.setString(15, suffix);
                ps.setString(16, prefix);
                ps.setString(17, indiv_addr);
                ps.setInt(18, ref_proceeding);
                ps.setString(19, ref_category);

                int a = ps.executeUpdate();

                if (a > 0) {
                    String msg =
                        "Data has been insertd Successfully. Order Id : " +
                        ord_id;
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
