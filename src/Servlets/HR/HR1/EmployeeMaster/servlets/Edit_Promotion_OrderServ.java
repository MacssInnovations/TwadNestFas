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


public class Edit_Promotion_OrderServ extends HttpServlet {
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

                    } else if ((OfficeId == 0)) {
                        System.out.println("welcome");
                        flag = true;
                    } else {
                        xml = xml + "<flag>failurec</flag>";
                        flag = false;
                    }
                } else {


                }

                if (flag) {
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
  con.prepareStatement("select employee_id,date_of_birth,employee_name,designation,office_name,office_id,DESIGNATION_ID,employee_status_id from  \n" +
                       "(  \n" +
                       "select employee_id,date_of_birth,employee_name||decode(employee_initial,null,' ','.'||employee_initial) as employee_name from  \n" +
                       "hrm_mst_employees  \n" + ")a  \n" +
                       "left outer join  \n" + "(  \n" +
                       "select employee_id as empid,office_id,designation_id,employee_status_id from hrm_emp_current_posting  \n" +
                       "where employee_status_id in ('WKG','TRT')\n" +
                       ")b  \n" + "on a.employee_id=b.empid  \n" +
                       "left outer join  \n" + "(  \n" +
                       "select office_id as offid,office_name from com_mst_offices  \n" +
                       ")c  \n" + "on b.office_id=c.offid  \n" +
                       "left outer join  \n" + "(  \n" +
                       "select DESIGNATION_ID as desig,designation from hrm_mst_designations  \n" +
                       ")d  \n" +
                       "on b.DESIGNATION_ID=d.desig where b.empid=?");
                                ps.setInt(1, eid);
                                //ps.setInt(2,offid);
                                rs = ps.executeQuery();
                                if (rs.next()) {

                                    String dob = "";
                                    String current = "";
                                    int ofid = 0;
                                    if (rs.getDate("date_of_birth") != null) {
                                        dob =
 new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("date_of_birth"));
                                        System.out.println("date of birth:" +
                                                           rs.getDate("date_of_birth"));
                                    } else {
                                        dob = "-";
                                    }
                                    if (rs.getString("office_name") != null) {
                                        current = rs.getString("office_name");
                                    } else {
                                        current = "-";
                                    }
                                    if (rs.getString("office_id") != null) {
                                        ofid =
Integer.parseInt(rs.getString("office_id"));
                                    } else {
                                        ofid = 0;
                                    }
                                    xml =
 xml + "<flag>success</flag><eid>" + eid + "</eid><ename>" +
   rs.getString("EMPLOYEE_NAME") + "</ename><desig>" +
   rs.getString("DESIGNATION") + "</desig><curr_post>" + current +
   "</curr_post><desig_id>" + rs.getInt("designation_id") + "</desig_id>";
                                    xml =
 xml + "<dob>" + dob + "</dob><office_id>" + ofid + "</office_id>";
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
            System.out.println("inside office...");
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            xml = "<response><command>office</command>";
            System.out.println(xml);
            try {
                int oid = 0;
                String oname = "";
                try {
                    oid = Integer.parseInt(request.getParameter("oid"));
                } catch (Exception e) {
                    System.out.println(e);
                }

                System.out.println("office id..." + oid);

                ps =
  con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=?");
                ps.setInt(1, oid);
                rs = ps.executeQuery();
                if (rs.next())
                    xml =
 xml + "<flag>success</flag><oid>" + oid + "</oid><oname>" +
   rs.getString("OFFICE_NAME") + "</oname>";
                else
                    xml = xml + "<flag>failure</flag><oid>" + oid + "</oid>";

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
                    "select PROMOTION_ISSUE_OFFICE_ID, PROMOTION_ORDER_ID, EMPLOYEE_ID, NEW_DESIGNATION, POSTED, DEPT_ID, TO_WHICH_OFFICE_ID, " +
                    " REPOSTS_REQUIRED, TA_DA_ELIGIBLE, PROCEEDING_DATE, PROCEEDING_NO, PROCEED_SUBJECT, PROCEED_REFERENCE, ADDL_PARA_1, " +
                    " ADDL_PARA_2, COPY_TO, PRESIDING_OFFICER, PRESIDING_OFFICER_DESIGNATION, SUFFIX, INDIVIDUALLY_ADDRESSED, PREFIX, " +
                    " employee_name, designation, dept_id, off_name from " +
                    " ( " +
                    " select PROMOTION_ISSUE_OFFICE_ID, PROMOTION_ORDER_ID, EMPLOYEE_ID, NEW_DESIGNATION, POSTED, DEPT_ID, " +
                    " TO_WHICH_OFFICE_ID, REPOSTS_REQUIRED, TA_DA_ELIGIBLE, PROCESS_FLOW_STATUS_ID from HRM_PROMOTION_DETAILS " +
                    " where PROCESS_FLOW_STATUS_ID in ('CR','MD') " + " ) a " +
                    " left outer join " + " ( " +
                    " select PROMOTION_ISSUE_OFFICE_ID as pro_office_id, PROMOTION_ORDER_ID as pro_order_id, PROCEEDING_DATE, PROCEEDING_NO, " +
                    " PROCEED_SUBJECT, PROCEED_REFERENCE, ADDL_PARA_1, ADDL_PARA_2, COPY_TO, PRESIDING_OFFICER, " +
                    " PRESIDING_OFFICER_DESIGNATION, PROCESS_FLOW_STATUS_ID, SUFFIX, INDIVIDUALLY_ADDRESSED, PREFIX " +
                    " from HRM_PROMOTION_ORDERS where PROCESS_FLOW_STATUS_ID in ('CR','MD') " +
                    " ) b " +
                    " on a.PROMOTION_ISSUE_OFFICE_ID=b.pro_office_id and a.PROMOTION_ORDER_ID=b.pro_order_id " +
                    " left outer join " + " ( " +
                    " select employee_id as emp_id,employee_name || '.' || employee_initial as employee_name from " +
                    " hrm_mst_employees " + " ) c " +
                    " on a.employee_id=c.emp_id " + " left outer join " +
                    " ( " +
                    " select designation_id as desig_id, designation from hrm_mst_designations " +
                    " ) d " + " on a.new_designation=d.desig_id " +
                    " left outer join " + " ( " +
                    " select office_id as off_id, office_name as off_name, 'TWAD' as dept_id from com_mst_offices " +
                    " where office_status_id not in ('CL','RD','NC') " +
                    " union " +
                    " select other_dept_office_id as off_id,other_dept_office_name as off_name,other_dept_id as dept_id " +
                    " from hrm_mst_other_dept_offices " + " ) e " +
                    " on a.DEPT_ID=e.dept_id and a.TO_WHICH_OFFICE_ID=e.off_id " +
                    " where a.PROMOTION_ISSUE_OFFICE_ID=? and a.PROMOTION_ORDER_ID=?";
                ps = con.prepareStatement(sql);
                ps.setInt(1, office_id);
                ps.setInt(2, order_id);

                rs = ps.executeQuery();
                int i = 0;

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
   rs.getString("PRESIDING_OFFICER_DESIGNATION") +
   "</pres_off_des><ind_addr>" + rs.getString("INDIVIDUALLY_ADDRESSED") +
   "</ind_addr>";
                    xml =
 xml + "<prefix>" + rs.getString("PREFIX") + "</prefix><suffix>" +
   rs.getString("SUFFIX") + "</suffix>";
                    xml =
 xml + "<details><employee_id>" + rs.getInt("EMPLOYEE_ID") +
   "</employee_id><pro_office_id>" + rs.getInt("TO_WHICH_OFFICE_ID") +
   "</pro_office_id><desig_id>" + rs.getInt("NEW_DESIGNATION") + "</desig_id>";
                    xml =
 xml + "<emp_name>" + rs.getString("employee_name") + "</emp_name><desig>" +
   rs.getString("designation") + "</desig><off_name>" +
   rs.getString("off_name") + "</off_name>";
                    xml =
 xml + "<repost_req>" + rs.getString("REPOSTS_REQUIRED") +
   "</repost_req><dep_name>" + rs.getString("dept_id") + "</dep_name>";
                    xml =
 xml + "<ta_da>" + rs.getString("TA_DA_ELIGIBLE") + "</ta_da><posting>" +
   rs.getString("POSTED") + "</posting></details>";
                    //xml=xml+"<reason>"+rs.getInt("REASON_ID")+"</reason><reas>"+rs.getString("REASON")+"</reas><ta_da>"+rs.getString("TA_DA_ELIGIBLE")+"</ta_da><dpn_desig>"+rs.getString("dpn_designation")+"</dpn_desig></details>";
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

        else if (command.equalsIgnoreCase("Add")) {
            System.out.println("inside add...");
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            xml = "<response><command>add</command>";
            System.out.println(xml);


            try {

                int empid = 0, off_id = 0, order = 0, new_desig = 0, ofid =
                    0, reas_id = 0;
                String repost = "", ta_da = "", deprtm = "", reas =
                    "", dpn_desig = "", post = "";

                empid =
Integer.parseInt(request.getParameter("txtEmployeeid"));

                if (request.getParameter("offid") == null) {
                    off_id = 0;
                } else {
                    off_id = Integer.parseInt(request.getParameter("offid"));
                }

                new_desig =
                        Integer.parseInt(request.getParameter("new_desig"));

                post = request.getParameter("posting");


                deprtm = request.getParameter("department");

                ofid = Integer.parseInt(request.getParameter("post_offid"));


                repost = request.getParameter("repost");

                ta_da = request.getParameter("ta_da");

                order = Integer.parseInt(request.getParameter("order"));

                //trf_offid=Integer.parseInt(request.getParameter("offid"));

                System.out.println("order id..." + order);
                System.out.println("employee id.." + empid);
                System.out.println("office id..." + off_id);
                System.out.println("department..." + deprtm);
                System.out.println("department office id..." + ofid);
                System.out.println("posting..." + post);
                //System.out.println("reason..."+reas);
                System.out.println("new designation..." + new_desig);
                //System.out.println("deputation designation..."+dpn_desig);
                System.out.println("reposting req..." + repost);
                System.out.println("ta_da..." + ta_da);


                String sql =
                    "insert into HRM_PROMOTION_DETAILS (PROMOTION_ISSUE_OFFICE_ID,PROMOTION_ORDER_ID,EMPLOYEE_ID,NEW_DESIGNATION,POSTED,DEPT_ID,TO_WHICH_OFFICE_ID,REPOSTS_REQUIRED,TA_DA_ELIGIBLE,PROCESS_FLOW_STATUS_ID,UPDATED_BY_USER_ID,UPDATED_DATE) values (?,?,?,?,?,?,?,?,?,?,?,?)";

                ps = con.prepareStatement(sql);
                ps.setInt(1, off_id);
                ps.setInt(2, order);
                ps.setInt(3, empid);
                ps.setInt(4, new_desig);
                ps.setString(5, post);
                ps.setString(6, deprtm);
                ps.setInt(7, ofid);
                ps.setString(8, repost);
                ps.setString(9, ta_da);
                ps.setString(10, "CR");
                ps.setString(11, updatedby);
                ps.setTimestamp(12, ts);


                int z = ps.executeUpdate();

                if (z > 0) {
                    xml = xml + "<flag>success</flag>";
                } else {
                    xml = xml + "<flag>failure</flag>";
                }

                System.out.println(xml);
                //ps.close();
                //rs.close();
            } catch (Exception e) {
                System.out.println("catch..HERE.in load office." +
                                   e.getMessage());
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            out.println(xml);
        }

        else if (command.equalsIgnoreCase("Update")) {
            System.out.println("inside update...");
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            xml = "<response><command>update</command>";
            System.out.println(xml);
            try {

                int empid = 0, off_id = 0, order = 0, new_desig = 0, ofid =
                    0, reas_id = 0;
                String repost = "", ta_da = "", deprtm = "", reas =
                    "", dpn_desig = "", post = "";

                empid =
Integer.parseInt(request.getParameter("txtEmployeeid"));

                if (request.getParameter("offid") == null) {
                    off_id = 0;
                } else {
                    off_id = Integer.parseInt(request.getParameter("offid"));
                }

                new_desig =
                        Integer.parseInt(request.getParameter("new_desig"));

                post = request.getParameter("posting");


                deprtm = request.getParameter("department");

                ofid = Integer.parseInt(request.getParameter("post_offid"));


                repost = request.getParameter("repost");

                ta_da = request.getParameter("ta_da");

                order = Integer.parseInt(request.getParameter("order"));

                //trf_offid=Integer.parseInt(request.getParameter("offid"));

                System.out.println("order id..." + order);
                System.out.println("employee id.." + empid);
                System.out.println("office id..." + off_id);
                System.out.println("department..." + deprtm);
                System.out.println("department office id..." + ofid);
                System.out.println("posting..." + post);
                //System.out.println("reason..."+reas);
                System.out.println("new designation..." + new_desig);
                //System.out.println("deputation designation..."+dpn_desig);
                System.out.println("reposting req..." + repost);
                System.out.println("ta_da..." + ta_da);


                String sql =
                    "update HRM_PROMOTION_DETAILS set NEW_DESIGNATION=?,POSTED=?,DEPT_ID=?,TO_WHICH_OFFICE_ID=?,REPOSTS_REQUIRED=?,TA_DA_ELIGIBLE=?,PROCESS_FLOW_STATUS_ID=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=? where PROMOTION_ISSUE_OFFICE_ID=? and EMPLOYEE_ID=? and PROMOTION_ORDER_ID=?";

                ps = con.prepareStatement(sql);
                ps.setInt(1, new_desig);
                ps.setString(2, post);
                ps.setString(3, deprtm);
                ps.setInt(4, ofid);
                ps.setString(5, repost);
                ps.setString(6, ta_da);
                ps.setString(7, "MD");
                ps.setString(8, updatedby);
                ps.setTimestamp(9, ts);
                ps.setInt(10, off_id);
                ps.setInt(11, empid);
                ps.setInt(12, order);


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
                    "delete from HRM_PROMOTION_DETAILS where PROMOTION_ISSUE_OFFICE_ID=? and EMPLOYEE_ID=? and PROMOTION_ORDER_ID=?";

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

        else if (command.equalsIgnoreCase("Deptid")) {
            System.out.println("inside department id");

            xml = xml + "<response><command>Department</command>";
            System.out.println("xml..." + xml);

            try {
                String depart = request.getParameter("dept_id");
                System.out.println("department id..." + depart);

                int off_id =
                    Integer.parseInt(request.getParameter("office_id"));
                System.out.println("office id..." + off_id);

                ps =
  con.prepareStatement("select other_dept_office_name from HRM_MST_OTHER_DEPT_OFFICES where OTHER_DEPT_ID=? and OTHER_DEPT_OFFICE_ID=?");
                ps.setString(1, depart);
                ps.setInt(2, off_id);

                rs = ps.executeQuery();

                if (rs.next()) {
                    String off_name = rs.getString("other_dept_office_name");
                    System.out.println("office name...." + off_name);

                    xml =
 xml + "<flag>success</flag><office_name>" + off_name + "</office_name>";
                } else {
                    xml = xml + "<flag>failure</flag>";
                }
            } catch (Exception e) {
                xml = xml + "<flag>failure</flag>";
                System.out.println(e.getMessage());
            }

            xml = xml + "</response>";
            out.println(xml);

            System.out.println("xml..." + xml);
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
            "", ref = "", add_par1 = "", add_par2 = "", cop_to = "", prefx =
            "", sufx = "", ind_addr = "";
        Date proc_date = null;
        Calendar c;

        /*
        String[] sd=request.getParameter("txtDORelieval").split("/");
        c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
        java.util.Date d=c.getTime();
        txtDORelieval=new Date(d.getTime());*/

        String command = request.getParameter("Command");
        System.out.println("command.." + command);

        proc_id = Integer.parseInt(request.getParameter("txtPid"));
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

        prefx = request.getParameter("txtPref");
        pres_officer = request.getParameter("txtPO");
        sufx = request.getParameter("txtSuf");
        pres_desig = request.getParameter("txtPOD");
        subj = request.getParameter("txtSub");
        ref = request.getParameter("txtRef");
        add_par1 = request.getParameter("txtAdd1");
        add_par2 = request.getParameter("txtAdd2");
        cop_to = request.getParameter("txtcopy");
        iss_off_id = Integer.parseInt(request.getParameter("txtOffId"));
        ind_addr = request.getParameter("rad_indi");

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
        System.out.println("prefix..." + prefx);
        System.out.println("suffix..." + sufx);
        System.out.println("indiv addr..." + ind_addr);


        if (command.equalsIgnoreCase("Edit")) {

            try {
                String sql =
                    "update HRM_PROMOTION_ORDERS set PROCEEDING_DATE=?,PROCEEDING_NO=?,PROCEED_SUBJECT=?,PROCEED_REFERENCE=?,ADDL_PARA_1=?,ADDL_PARA_2=?,COPY_TO=?,PRESIDING_OFFICER=?,PRESIDING_OFFICER_DESIGNATION=?,PROCESS_FLOW_STATUS_ID=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=?,SUFFIX=?,PREFIX=?,INDIVIDUALLY_ADDRESSED=? where PROMOTION_ISSUE_OFFICE_ID=? and PROMOTION_ORDER_ID=?";

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
                ps.setString(13, sufx);
                ps.setString(14, prefx);
                ps.setString(15, ind_addr);
                ps.setInt(16, iss_off_id);
                ps.setInt(17, proc_id);


                int a = ps.executeUpdate();

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
