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


public class Edit_Dept_Panel_DetailsServ extends HttpServlet {
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
                    "select dept_ref_ltr_no,dept_ref_ltr_date,other_dept_id,other_dept_name,        \n" +
                    "other_dept_office_id,other_dept_office_name,post_name_dpn_dept,pay_scale_dpn_dept,remarks,will_employee_id,will_employee_id||' ' ||employee_name as empdet from  \n" +
                    "(\n" +
                    "select office_id,dpn_ref_id,dept_ref_ltr_no,dept_ref_ltr_date,other_dept_id,other_dept_office_id,        \n" +
                    "post_name_dpn_dept,pay_scale_dpn_dept,remarks,process_flow_status_id from hrm_emp_dpn_willingness        \n" +
                    "where process_flow_status_id='FR'        \n" +
                    ")a       \n" + "left outer join        \n" + "(      \n" +
                    "select office_id as off_id,dpn_ref_id,will_employee_id,process_flow_status_id    \n" +
                    "from HRM_EMP_DPN_WILL_DETAILS  where process_flow_status_id='FR'    \n" +
                    ")b      \n" +
                    "on a.office_id=b.off_id and a.dpn_ref_id=b.dpn_ref_id    \n" +
                    "left outer join    \n" + "(        \n" +
                    "select other_dept_id as dept_id,other_dept_name from hrm_mst_other_depts        \n" +
                    ")c        \n" + "on a.other_dept_id=c.dept_id        \n" +
                    "left outer join        \n" + "(        \n" +
                    "select other_dept_id as dept_id1, other_dept_office_id as dept_off_id, other_dept_office_name         \n" +
                    "from hrm_mst_other_dept_offices        \n" +
                    ")d       \n" +
                    "on a.other_dept_id=d.dept_id1 and a.other_dept_office_id=d.dept_off_id        \n" +
                    "left outer join  \n" +
                    "( select employee_id as emp_id,employee_name || decode(EMPLOYEE_INITIAL,null,' ','.'||EMPLOYEE_INITIAL) as employee_name   \n" +
                    "from hrm_mst_employees  \n" + ") e   \n" +
                    "on b.WILL_EMPLOYEE_ID=e.emp_id  \n" +
                    "left outer join   \n" +
                    "(select office_id,employee_id as empid, designation_id from hrm_emp_current_posting   \n" +
                    ")f\n" + "on b.WILL_EMPLOYEE_ID=f.empid   \n" +
                    "where a.dpn_ref_id=" + order_id + " and a.office_id=" +
                    office_id + "";
                System.out.println(sql);
                ps = con.prepareStatement(sql);
                System.out.println("asdf");
                rs = ps.executeQuery();
                int i = 0;

                while (rs.next()) {

                    String ltrdate = "";
                    if (rs.getDate("dept_ref_ltr_date") != null) {
                        ltrdate =
                                new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("dept_ref_ltr_date"));
                        System.out.println("dept letter date :" +
                                           rs.getDate("dept_ref_ltr_date"));
                    } else {

                        ltrdate = "-";
                    }
                    String empdet = "";
                    if (rs.getString("empdet") != null) {
                        empdet = rs.getString("empdet");
                        System.out.println("the employee details is" + empdet);
                    } else {
                        empdet = "-";
                    }

                    System.out.println("the employee details is" + empdet);
                    int empid = 0;
                    if (rs.getString("will_employee_id") != null) {
                        empid =
Integer.parseInt(rs.getString("will_employee_id"));
                        System.out.println("the employee id details is" +
                                           empid);
                    } else {
                        empid = 0;
                    }


                    xml =
 xml + "<DEPT_REF_LTR_NO>" + rs.getString("dept_ref_ltr_no") +
   "</DEPT_REF_LTR_NO><DEPT_REF_LTR_DATE>" + ltrdate + "</DEPT_REF_LTR_DATE>";
                    System.out.println("1" + xml);
                    xml =
 xml + "<other_dept_name>" + rs.getString("other_dept_name") +
   "</other_dept_name><other_dept_office_name>" +
   rs.getString("other_dept_office_name") + "</other_dept_office_name>";
                    System.out.println("2" + xml);
                    xml =
 xml + "<post_name_dpn_dept>" + rs.getString("post_name_dpn_dept") +
   "</post_name_dpn_dept><pay_scale_dpn_dept>" +
   rs.getString("pay_scale_dpn_dept") + "</pay_scale_dpn_dept><remarks>" +
   rs.getString("remarks") + "</remarks>";
                    System.out.println("3" + xml);
                    xml =
 xml + "<details><empid>" + empdet + "</empid><will_employee_id>" + empid +
   "</will_employee_id></details>";
                    System.out.println("4" + xml);
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
                String remark = request.getParameter("remark");
                System.out.println("remark..." + remark);


                ps =
  con.prepareStatement("insert into HRM_EMP_DPN_PANEL_DETAILS (OFFICE_ID,EMPANEL_PROCEEDING_ID,EMPLOYEE_ID,REMARKS,UPDATED_BY_USERID,UPDATED_DATE,PROCESS_FLOW_STATUS_ID) values (?,?,?,?,?,?,?)");
                ps.setInt(1, office_id);
                ps.setInt(2, refid);
                ps.setInt(3, eid);
                ps.setString(4, remark);
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
                xml = xml + "<flag>failure</flag>";
                System.out.println("Err" + e.getMessage());
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
                String remark = request.getParameter("remark");
                System.out.println("remark..." + remark);


                ps =
  con.prepareStatement("update HRM_EMP_DPN_PANEL_DETAILS set REMARKS=?,UPDATED_BY_USERID=?,UPDATED_DATE=?,PROCESS_FLOW_STATUS_ID=? where OFFICE_ID=? and EMPANEL_PROCEEDING_ID=? and EMPLOYEE_ID=?");
                ps.setString(1, remark);
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
  con.prepareStatement("delete from HRM_EMP_DPN_PANEL_DETAILS where office_id=? and EMPANEL_PROCEEDING_ID=? and EMPLOYEE_ID=?");
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

        if (command.equalsIgnoreCase("getProcedDet")) {

            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            xml = "<response><command>getProcedDet</command>";
            int ord_id = 0;
            int panel_id = 0;
            int office_id = 0;
            try {
                System.out.println("inside try loop.................");
                try {
                    ord_id = Integer.parseInt(request.getParameter("ord_id"));
                    System.out.println("order id..." + ord_id);
                } catch (Exception e) {
                    System.out.println("The error for order id is" +
                                       e.getMessage());
                }
                try {
                    panel_id =
                            Integer.parseInt(request.getParameter("panel_id"));
                    System.out.println("panel id..." + panel_id);
                } catch (Exception e) {
                    System.out.println("The error for panel id is" +
                                       e.getMessage());
                }

                try {
                    office_id =
                            Integer.parseInt(request.getParameter("offid"));
                    System.out.println("office id......" + office_id);
                } catch (Exception e) {
                    System.out.println("The error for panel id is" +
                                       e.getMessage());
                }
                String sql =
                    "select EMPANEL_PROCEEDING_ID,EMPANEL_PROCEEDING_NO,EMPANEL_PROCEEDING_DATE,EMPLOYEE_ID,REMARKS,employee_name,designation,office_name from  \n" +
                    " (  \n" +
                    " select office_id,dpn_ref_id,empanel_proceeding_id,EMPANEL_PROCEEDING_NO,EMPANEL_PROCEEDING_DATE,\n" +
                    " process_flow_status_id from hrm_emp_dpn_panel where process_flow_status_id in('CR','MD')\n" +
                    " )a\n" + " left outer join\n" + " (\n" +
                    " select OFFICE_ID as offid,EMPANEL_PROCEEDING_ID as empanel,EMPLOYEE_ID,REMARKS,process_flow_status_id \n" +
                    " from HRM_EMP_DPN_PANEL_DETAILS where process_flow_status_id in('CR','MD')\n" +
                    " )b \n" +
                    " on a.office_id=b.offid and a.empanel_proceeding_id=b.empanel\n" +
                    " left outer join   \n" +
                    " ( select employee_id as emp_id,employee_name || decode(EMPLOYEE_INITIAL,null,' ','.'||EMPLOYEE_INITIAL) as employee_name   \n" +
                    " from hrm_mst_employees  \n" + " ) c    \n" +
                    " on b.EMPLOYEE_ID=c.emp_id  \n" +
                    " left outer join   \n" +
                    " (select office_id,employee_id as empid, designation_id from hrm_emp_current_posting   \n" +
                    " )d  \n" + " on b.EMPLOYEE_ID=d.empid   \n" +
                    " left outer join  \n" + " (  \n" +
                    " select designation_id,designation from HRM_MST_DESIGNATIONS  \n" +
                    " )e  \n" + " on d.designation_id=e.designation_id   \n" +
                    " left outer join   \n" + " (  \n" +
                    " select office_id,office_name from com_mst_offices  \n" +
                    " )f  \n" + " on d.office_id=f.office_id  \n" +
                    " where a.dpn_ref_id=" + ord_id + " and a.office_id=" +
                    office_id + " and a.empanel_proceeding_id=" + panel_id +
                    "";
                System.out.println(sql);
                ps = con.prepareStatement(sql);
                System.out.println("asdf");
                rs = ps.executeQuery();
                int i = 0;

                while (rs.next()) {

                    String empdt = "";
                    if (rs.getDate("EMPANEL_PROCEEDING_DATE") != null) {
                        empdt =
new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("EMPANEL_PROCEEDING_DATE"));
                        System.out.println("dept letter date :" +
                                           rs.getDate("EMPANEL_PROCEEDING_DATE"));
                    } else {

                        empdt = "-";
                    }
                    String rem = "";
                    if (rs.getString("REMARKS") != null) {
                        rem = rs.getString("REMARKS");
                    } else {
                        rem = "-";
                    }


                    xml =
 xml + "<EMPANEL_PROCEEDING_NO>" + rs.getString("EMPANEL_PROCEEDING_NO") +
   "</EMPANEL_PROCEEDING_NO><EMPANEL_PROCEEDING_DATE>" + empdt +
   "</EMPANEL_PROCEEDING_DATE>";
                    System.out.println("1" + xml);
                    xml =
 xml + "<details><employeeid>" + rs.getInt("EMPLOYEE_ID") +
   "</employeeid><employee_name>" + rs.getString("employee_name") +
   "</employee_name><designation>" + rs.getString("designation") +
   "</designation><office_name>" + rs.getString("office_name") +
   "</office_name><REMARKS>" + rem + "</REMARKS></details>";
                    System.out.println("4" + xml);
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

        if (command.equalsIgnoreCase("ProceedDetails")) {

            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            xml = "<response><command>ProceedDetails</command>";
            int ord_id = 0;
            int panel_id = 0;
            int office_id = 0;
            try {
                System.out.println("inside try loop.................");
                try {
                    ord_id = Integer.parseInt(request.getParameter("ord_id"));
                    System.out.println("order id..." + ord_id);
                } catch (Exception e) {
                    System.out.println("The error for order id is" +
                                       e.getMessage());
                }

                try {
                    office_id =
                            Integer.parseInt(request.getParameter("offid"));
                    System.out.println("office id......" + office_id);
                } catch (Exception e) {
                    System.out.println("The error for panel id is" +
                                       e.getMessage());
                }
                String sql =
                    "select empanel_proceeding_id from        \n" + "(        \n" +
                    "select office_id,dpn_ref_id,empanel_proceeding_id,process_flow_status_id from hrm_emp_dpn_panel where process_flow_status_id in('CR','MD')      \n" +
                    ")a      \n" + "left outer join      \n" + "(      \n" +
                    "select office_id,dpn_ref_id,process_flow_status_id from hrm_emp_dpn_willingness        \n" +
                    "where process_flow_status_id='FR'        \n" +
                    ")b       \n" +
                    "on a.office_id=b.office_id and a.dpn_ref_id=b.dpn_ref_id      \n" +
                    "\n" + "where b.dpn_ref_id=" + ord_id +
                    " and b.office_id=" + office_id + "";
                System.out.println(sql);
                ps = con.prepareStatement(sql);
                System.out.println("asdf");
                rs = ps.executeQuery();
                int i = 0;

                while (rs.next()) {

                    xml =
 xml + "<details><empanelid>" + rs.getInt("empanel_proceeding_id") +
   "</empanelid></details>";
                    System.out.println("4" + xml);
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

        else if (command.equalsIgnoreCase("employee")) {


            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            //int offid=0;
            //offid=Integer.parseInt(request.getParameter("offid"));
            xml = "<response><command>employee</command>";
            try {
                System.out.println("inside try loop");
                int empid = Integer.parseInt(request.getParameter("empid"));
                System.out.println("empid..." + empid);

                String sql =
                    "select employee_id,employee_name,designation,office_name from\n" +
                    "( select employee_id ,employee_name || decode(EMPLOYEE_INITIAL,null,' ','.'||EMPLOYEE_INITIAL) as employee_name \n" +
                    "from hrm_mst_employees\n" + ") a \n" +
                    "left outer join \n" +
                    "(select office_id,employee_id as empid, designation_id from hrm_emp_current_posting \n" +
                    ")b\n" + "on a.employee_id=b.empid \n" +
                    "left outer join\n" + "(\n" +
                    "select designation_id,designation from HRM_MST_DESIGNATIONS\n" +
                    ")c\n" + "on b.designation_id=c.designation_id \n" +
                    "left outer join \n" + "(\n" +
                    "select office_id,office_name from com_mst_offices\n" +
                    ")d\n" + "on b.office_id=d.office_id\n" +
                    "where a.employee_id=" + empid + "";
                System.out.println(sql);
                ps = con.prepareStatement(sql);
                System.out.println("asdf");
                rs = ps.executeQuery();
                int i = 0;

                while (rs.next()) {


                    xml =
 xml + "<employee_id>" + rs.getInt("employee_id") + "</employee_id><employee_name>" +
   rs.getString("employee_name") + "</employee_name>";
                    System.out.println("1" + xml);
                    xml =
 xml + "<designation>" + rs.getString("designation") + "</designation><office_name>" +
   rs.getString("office_name") + "</office_name>";
                    System.out.println("2" + xml);
                    i++;
                }

                if (i == 0) {
                    xml = xml + "<flag>failurea</flag>";
                } else {
                    xml = xml + "<flag>success</flag>";
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

        String remarks = request.getParameter("remarks");
        String rem[] = remarks.split("~");
        String remark[] = new String[rem.length];
        for (int i = 0; i < rem.length; i++) {
            remark[i] = rem[i];
            System.out.println("remark is" + remark[i]);
        }


        int offid = Integer.parseInt(request.getParameter("offid"));
        System.out.println("the office id is" + offid);

        int pid = Integer.parseInt(request.getParameter("pid"));
        System.out.println("the refence id is" + pid);

        String dptrefid = request.getParameter("dptrefid");
        System.out.println("the deputation reference number is" + dptrefid);

        Date empanel = null;

        String empdat = request.getParameter("empdat");
        System.out.println("empanel  date...." + empdat);

        if (empdat.equals(null) || empdat.equals("")) {
            empanel = null;
        } else {
            String[] sd = request.getParameter("empdat").split("/");
            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
            java.util.Date d = c.getTime();
            empanel = new Date(d.getTime());
        }
        System.out.println("empanel reference date is..." + empanel);

        String empno = request.getParameter("empno");
        System.out.println("the empanel id is" + empno);

        String dpnrem = request.getParameter("dpnrem");
        System.out.println("the deputation remark is" + dpnrem);


        try {


            /* Here We Insert The Value into HRM_EMP_DPN_WILLINGNESS*/
            String sql3 =
                "update hrm_emp_dpn_panel set empanel_proceeding_no=?,empanel_proceeding_date=?,remarks=?,UPDATED_BY_USERID=?,UPDATED_DATE=?,PROCESS_FLOW_STATUS_ID=? where OFFICE_ID=? and empanel_proceeding_id=? and dpn_ref_id=?";
            ps = con.prepareStatement(sql3);


            ps.setString(1, empno);
            ps.setDate(2, empanel);
            ps.setString(3, dpnrem);
            ps.setString(4, updatedby);
            ps.setTimestamp(5, ts);
            ps.setString(6, "MD");
            ps.setInt(7, offid);
            ps.setInt(8, pid);
            ps.setString(9, dptrefid);
            ps.executeUpdate();
            ps.close();

            int emplid = 0;
            String empremarks = "";

            /* Here We Insert The Value into HRM_EMP_DPN_WILL_DETAILS*/

            String sql =
                "update hrm_emp_dpn_panel_details set remarks=?,UPDATED_BY_USERID=?,UPDATED_DATE=?,PROCESS_FLOW_STATUS_ID=? where OFFICE_ID=? and empanel_proceeding_id=? and employee_id=?";
            PreparedStatement statement = con.prepareStatement(sql);

            /*We May Have Multiple Habitaions For The Same Proposal Id For Tat We Find The Length and Then We Insert The Values*/
            for (int j = 0; j < employeeid.length; j++) {
                emplid = employeeid[j];
                empremarks = remark[j];


                statement.setString(1, empremarks);
                statement.setString(2, updatedby);
                statement.setTimestamp(3, ts);
                statement.setString(4, "MD");
                statement.setInt(5, offid);
                statement.setInt(6, pid);
                statement.setInt(7, emplid);
                emplid = 0;
                empremarks = "";

                statement.executeUpdate();
            }


            /* Here The Message Is Passed To The Next Page By Using The Function sendMessage()*/
            con.commit();
            String msg = "Data has been Updated Successfully.";
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
