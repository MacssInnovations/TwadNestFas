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


public class Edit_Dept_Willingness_DetailsServ extends HttpServlet {
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

        System.out.println("inside servlet...................................");

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

        String command = request.getParameter("command");
        System.out.println("command.." + command);


        String xml = "";
        if (command.equalsIgnoreCase("getDet")) {

            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            xml = "<response><command>getDet</command>";


            try {
                System.out.println("inside try loop");
                int order_id = Integer.parseInt(request.getParameter("order"));
                System.out.println("order id..." + order_id);

                int office_id =
                    Integer.parseInt(request.getParameter("offid"));
                System.out.println("office id......" + office_id);
                String sql =
                    "select WILL_EMPLOYEE_ID,employee_name,designation,office_name,WILL_DATE,DEPT_REF_LTR_NO,DEPT_REF_LTR_NO,DEPT_REF_LTR_DATE,OTHER_DEPT_ID,OTHER_DEPT_OFFICE_ID,POST_NAME_DPN_DEPT,PAY_SCALE_DPN_DEPT,REMARKS,other_dept_name,other_dept_office_name from (select OFFICE_ID,DPN_REF_ID,DEPT_REF_LTR_NO,DEPT_REF_LTR_DATE,OTHER_DEPT_ID,OTHER_DEPT_OFFICE_ID,POST_NAME_DPN_DEPT,PAY_SCALE_DPN_DEPT,REMARKS from HRM_EMP_DPN_WILLINGNESS where process_flow_status_id in ('CR','MD')) a  left outer join  (select OFFICE_ID,DPN_REF_ID,WILL_EMPLOYEE_ID,WILL_DATE	 from HRM_EMP_DPN_WILL_DETAILS where process_flow_status_id in ('CR','MD')) b on a.DPN_REF_ID=b.DPN_REF_ID and a.OFFICE_ID=b.OFFICE_ID left outer join  ( select employee_id as emp_id,employee_name || decode(EMPLOYEE_INITIAL,null,' ','.'||EMPLOYEE_INITIAL) as employee_name from hrm_mst_employees) c  on b.WILL_EMPLOYEE_ID=c.emp_id left outer join (select office_id,employee_id as empid, designation_id from hrm_emp_current_posting )d on b.WILL_EMPLOYEE_ID=d.empid left outer join (select designation_id,designation from HRM_MST_DESIGNATIONS)e on d.designation_id=e.designation_id left outer join (select office_id,office_name from com_mst_offices)f on d.office_id=f.office_id left outer join(select other_dept_id,other_dept_name from HRM_MST_OTHER_DEPTS)g on a.other_dept_id=g.other_dept_id left outer join ( select other_dept_id,other_dept_office_id,other_dept_office_name from hrm_mst_other_dept_offices )h on a.OTHER_DEPT_OFFICE_ID=h.other_dept_office_id and a.other_dept_id=h.other_dept_id where a.DPN_REF_ID=" +
                    order_id + " and a.OFFICE_ID=" + office_id + "";
                System.out.println(sql);
                ps = con.prepareStatement(sql);
                System.out.println("asdf");
                rs = ps.executeQuery();
                int i = 0;

                while (rs.next()) {

                    String ltrdate = "";
                    if (rs.getDate("DEPT_REF_LTR_DATE") != null) {
                        ltrdate =
                                new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("DEPT_REF_LTR_DATE"));
                        System.out.println("dept letter date :" +
                                           rs.getDate("DEPT_REF_LTR_DATE"));
                    } else {

                        ltrdate = "empty";
                    }

                    String willdat = "";
                    if (rs.getDate("WILL_DATE") != null) {
                        willdat =
                                new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("WILL_DATE"));
                        System.out.println("dept letter date :" +
                                           rs.getDate("WILL_DATE"));
                    } else {

                        willdat = "empty";
                    }

                    xml =
 xml + "<DEPT_REF_LTR_NO>" + rs.getString("DEPT_REF_LTR_NO") +
   "</DEPT_REF_LTR_NO><DEPT_REF_LTR_DATE>" + ltrdate + "</DEPT_REF_LTR_DATE>";
                    xml =
 xml + "<OTHER_DEPT_ID>" + rs.getString("OTHER_DEPT_ID") +
   "</OTHER_DEPT_ID><OTHER_DEPT_OFFICE_ID>" +
   rs.getInt("OTHER_DEPT_OFFICE_ID") +
   "</OTHER_DEPT_OFFICE_ID><POST_NAME_DPN_DEPT>" +
   rs.getString("POST_NAME_DPN_DEPT") + "</POST_NAME_DPN_DEPT>";
                    xml =
 xml + "<PAY_SCALE_DPN_DEPT>" + rs.getString("PAY_SCALE_DPN_DEPT") +
   "</PAY_SCALE_DPN_DEPT><REMARKS>" + rs.getString("REMARKS") +
   "</REMARKS><other_dept_name>" + rs.getString("other_dept_name") +
   "</other_dept_name>";
                    xml =
 xml + "<other_dept_office_name>" + rs.getString("other_dept_office_name") +
   "</other_dept_office_name>";


                    xml =
 xml + "<details><employee_id>" + rs.getInt("WILL_EMPLOYEE_ID") +
   "</employee_id>";
                    xml =
 xml + "<emp_name>" + rs.getString("employee_name") + "</emp_name><desig>" +
   rs.getString("designation") + "</desig><off_name>" +
   rs.getString("office_name") + "</off_name>";
                    xml =
 xml + "<WILL_DATE>" + willdat + "</WILL_DATE></details>";
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

        } else if (command.equalsIgnoreCase("deptoff")) {
            xml = xml + "<response><command>deptoff</command>";
            try {
                String otherdeptid = request.getParameter("txtdeptid");
                System.out.println("other department id" + otherdeptid);
                ps =
  con.prepareStatement("select OTHER_DEPT_ID,OTHER_DEPT_OFFICE_ID,OTHER_DEPT_OFFICE_NAME from HRM_MST_OTHER_DEPT_OFFICES where OTHER_DEPT_ID='" +
                       otherdeptid + "'");
                System.out.println(otherdeptid);
                rs = ps.executeQuery();

                int i = 0;
                while (rs.next()) {
                    System.out.println(rs.getString("OTHER_DEPT_ID"));
                    System.out.println(rs.getInt("OTHER_DEPT_OFFICE_ID"));
                    System.out.println(rs.getString("OTHER_DEPT_OFFICE_NAME"));

                    xml =
 xml + "<other_dept_id>" + rs.getString("OTHER_DEPT_ID") + "</other_dept_id>";
                    xml =
 xml + "<other_dept_off_id>" + rs.getInt("OTHER_DEPT_OFFICE_ID") +
   "</other_dept_off_id>";
                    xml =
 xml + "<other_dept_off_name>" + rs.getString("OTHER_DEPT_OFFICE_NAME") +
   "</other_dept_off_name>";
                    i++;
                }
                if (i > 0) {
                    xml = xml + "<flag>success</flag>";
                } else {
                    xml = xml + "<flag>failure</flag>";
                }

            } catch (Exception e) {
                System.out.println("Err" + e.getMessage());
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
        }

        else if (command.equalsIgnoreCase("add")) {
            xml = xml + "<response><command>add</command>";
            try {
                int office_id =
                    Integer.parseInt(request.getParameter("offid"));
                System.out.println("office id......" + office_id);
                int refid = Integer.parseInt(request.getParameter("refid"));
                System.out.println("the reference id is" + refid);
                int eid = Integer.parseInt(request.getParameter("eid"));
                System.out.println("the employee id is" + eid);
                String willingdate = request.getParameter("willingdate");
                System.out.println("willing date..." + willingdate);
                Date willdat = null;
                Calendar c;
                if (willingdate.equals(null) || willingdate.equals("")) {
                    willdat = null;
                } else {
                    String[] sd =
                        request.getParameter("willingdate").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                    java.util.Date d = c.getTime();
                    willdat = new Date(d.getTime());
                }


                ps =
  con.prepareStatement("insert into hrm_emp_dpn_will_details (OFFICE_ID,DPN_REF_ID,WILL_EMPLOYEE_ID,WILL_DATE,UPDATED_BY_USERID,UPDATED_DATE,PROCESS_FLOW_STATUS_ID) values (?,?,?,?,?,?,?)");
                ps.setInt(1, office_id);
                ps.setInt(2, refid);
                ps.setInt(3, eid);
                ps.setDate(4, willdat);
                ps.setString(5, updatedby);
                ps.setTimestamp(6, ts);
                ps.setString(7, "CR");
                int i = ps.executeUpdate();

                if (i > 0) {
                    xml = xml + "<flag>success</flag>";
                } else {
                    xml = xml + "<flag>failure</flag>";
                }

            } catch (Exception e) {
                System.out.println("Err" + e.getMessage());
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
        }

        else if (command.equalsIgnoreCase("update")) {
            xml = xml + "<response><command>update</command>";
            try {
                int office_id =
                    Integer.parseInt(request.getParameter("offid"));
                System.out.println("office id......" + office_id);
                int refid = Integer.parseInt(request.getParameter("refid"));
                System.out.println("the reference id is" + refid);
                int eid = Integer.parseInt(request.getParameter("eid"));
                System.out.println("the employee id is" + eid);
                String willingdate = request.getParameter("willingdate");
                System.out.println("willing date..." + willingdate);
                Date willdat = null;
                Calendar c;
                if (willingdate.equals(null) || willingdate.equals("")) {
                    willdat = null;
                } else {
                    String[] sd =
                        request.getParameter("willingdate").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                    java.util.Date d = c.getTime();
                    willdat = new Date(d.getTime());
                }


                ps =
  con.prepareStatement("update hrm_emp_dpn_will_details set WILL_DATE=?,UPDATED_BY_USERID=?,UPDATED_DATE=?,PROCESS_FLOW_STATUS_ID=? where OFFICE_ID=? and DPN_REF_ID=? and WILL_EMPLOYEE_ID=?");
                ps.setDate(1, willdat);
                ps.setString(2, updatedby);
                ps.setTimestamp(3, ts);
                ps.setString(4, "CR");
                ps.setInt(5, office_id);
                ps.setInt(6, refid);
                ps.setInt(7, eid);
                int i = ps.executeUpdate();

                if (i > 0) {
                    xml = xml + "<flag>success</flag>";
                } else {
                    xml = xml + "<flag>failure</flag>";
                }

            } catch (Exception e) {
                System.out.println("Err" + e.getMessage());
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
        }

        else if (command.equalsIgnoreCase("delete")) {
            xml = xml + "<response><command>delete</command>";
            try {
                int office_id =
                    Integer.parseInt(request.getParameter("offid"));
                System.out.println("office id......" + office_id);
                int refid = Integer.parseInt(request.getParameter("refid"));
                System.out.println("the reference id is" + refid);
                int eid = Integer.parseInt(request.getParameter("eid"));
                System.out.println("the employee id is" + eid);
                ps =
  con.prepareStatement("delete from hrm_emp_dpn_will_details where office_id=? and dpn_ref_id=? and WILL_EMPLOYEE_ID=?");
                ps.setInt(1, office_id);
                ps.setInt(2, refid);
                ps.setInt(3, eid);
                int i = ps.executeUpdate();

                if (i > 0) {
                    xml = xml + "<flag>success</flag>";
                } else {
                    xml = xml + "<flag>failure</flag>";
                }

            } catch (Exception e) {
                System.out.println("Err" + e.getMessage());
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
        }

        else if (command.equalsIgnoreCase("loademp")) {
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

        Calendar c;
        int tempid = 0;
        String command = request.getParameter("command");
        System.out.println("command.." + command);

        String employeid = request.getParameter("empid");
        String empid[] = employeid.split("~");
        int employeeid[] = new int[empid.length];
        for (int i = 0; i < empid.length; i++) {
            employeeid[i] = Integer.parseInt(empid[i]);
            System.out.println("Employee id is" + employeeid[i]);
        }
        Date willingnessdate = null;

        String willingdate = request.getParameter("willingdate");
        String willdate[] = willingdate.split("~");
        String willdat[] = new String[willdate.length];
        for (int i = 0; i < willdate.length; i++) {
            willdat[i] = willdate[i];
            System.out.println("willing date is" + willdat[i]);
        }

        int refid = Integer.parseInt(request.getParameter("txtPid"));
        System.out.println("the reference id is" + refid);


        int offid = Integer.parseInt(request.getParameter("offid"));
        System.out.println("the office id is" + offid);

        String ltrrefno = request.getParameter("ltrrefno");
        System.out.println("the letter reference number is" + ltrrefno);

        Date ltrdate = null;

        String ltrrefdat = request.getParameter("ltrrefdat");
        System.out.println("proc date...." + ltrrefdat);

        if (ltrrefdat.equals(null) || ltrrefdat.equals("")) {
            ltrdate = null;
        } else {
            String[] sd = request.getParameter("ltrrefdat").split("/");
            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
            java.util.Date d = c.getTime();
            ltrdate = new Date(d.getTime());
        }
        System.out.println("letter reference date is..." + ltrdate);

        String deptid = request.getParameter("deptid");
        System.out.println("the department id is" + deptid);

        int deptoff = Integer.parseInt(request.getParameter("deptoff"));
        System.out.println("the departmnet office is" + deptoff);

        String postname = request.getParameter("postname");
        System.out.println("the post name is is" + postname);

        String payscale = request.getParameter("payscale");
        System.out.println("the payscale  is" + payscale);

        String remark = request.getParameter("remark");
        System.out.println("the remark  is" + remark);


        try {

            /* Here We update The Value into HRM_EMP_DPN_WILLINGNESS*/
            String sql3 =
                "update HRM_EMP_DPN_WILLINGNESS set DEPT_REF_LTR_NO=?,DEPT_REF_LTR_DATE=?,OTHER_DEPT_ID=?,OTHER_DEPT_OFFICE_ID=?,POST_NAME_DPN_DEPT=?,PAY_SCALE_DPN_DEPT=?,REMARKS=?,UPDATED_BY_USERID=?,UPDATED_DATE=?,PROCESS_FLOW_STATUS_ID=? where DPN_REF_ID=? and office_id=?";
            ps = con.prepareStatement(sql3);

            ps.setString(1, ltrrefno);
            ps.setDate(2, ltrdate);
            ps.setString(3, deptid);
            ps.setInt(4, deptoff);
            ps.setString(5, postname);
            ps.setString(6, payscale);
            ps.setString(7, remark);
            ps.setString(8, updatedby);
            ps.setTimestamp(9, ts);
            ps.setString(10, "MD");
            ps.setInt(11, refid);
            ps.setInt(12, offid);
            ps.executeUpdate();
            ps.close();

            int emplid = 0;
            String wdate = "";

            /* Here We update The Value into HRM_EMP_DPN_WILL_DETAILS*/

            String sql =
                "update HRM_EMP_DPN_WILL_DETAILS set WILL_DATE=?,UPDATED_BY_USERID=?,UPDATED_DATE=?,PROCESS_FLOW_STATUS_ID=? where OFFICE_ID=? and DPN_REF_ID=? and WILL_EMPLOYEE_ID=?";
            PreparedStatement statement = con.prepareStatement(sql);

            /*We May Have Multiple Habitaions For The Same Proposal Id For Tat We Find The Length and Then We update The Values*/
            for (int j = 0; j < employeeid.length; j++) {
                emplid = employeeid[j];
                wdate = willdat[j];

                String[] sd = wdate.split("/");
                c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                java.util.Date d = c.getTime();
                willingnessdate = new Date(d.getTime());
                System.out.println("willingness date is..." + willingnessdate);
                statement.setDate(1, willingnessdate);
                statement.setString(2, updatedby);
                statement.setTimestamp(3, ts);
                statement.setString(4, "MD");
                statement.setInt(5, offid);
                statement.setInt(6, refid);
                statement.setInt(7, emplid);
                emplid = 0;
                wdate = "";
                statement.executeUpdate();
            }


            /* Here The Message Is Passed To The Next Page By Using The Function sendMessage()*/
            con.commit();
            String msg = "Data has been Updated Successfully";
            msg = "<br><br><p><b>" + msg + "</b></p>";
            sendMessage(response, msg, "Ok");

        }

        catch (Exception e) {
            System.out.println("Exception in connection:" + e.getMessage());
            System.out.println("error in updating");
            sendMessage(response, "Failed to insert values", "back");

        }

        finally {
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
