package Servlets.HR.HR1.EmployeeMaster.servlets;

import Servlets.Security.classes.UserProfile;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class EmpDeputationRelievalPopupServ extends HttpServlet {

    public void init(ServletConfig config) throws ServletException {
        super.init(config);

    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        Connection con = null;
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
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection

            Class.forName(strDriver.trim());
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
        } catch (Exception e) {
            System.out.println("Exception in connection..." + e);
        }
        ResultSet rs = null;
        PreparedStatement ps = null;
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);
        /*try
    {
           if(session==null)
            {
                   String xml="<response><command>sessionout</command><flag>sessionout</flag></response>";
                    out.println(xml);
                    System.out.println(xml);
                    out.close();
                    return;

                }
                System.out.println(session);

    }catch(Exception e)
    {
            System.out.println("Redirect Error :"+e);
    }
    */


        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");

        String xml = "";
        String strCommand = "";
        int sgroup = 0;
        try {
            strCommand = request.getParameter("Command");
            System.out.println("assign....." + strCommand);
            sgroup = Integer.parseInt(request.getParameter("cmbsgroup"));
        }

        catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }


        if (strCommand.equalsIgnoreCase("SGroup")) {
            xml = "<response>";
            try {
                System.out.println("sgroup::" + sgroup);
                ps =
  con.prepareStatement("select DESIGNATION_ID,DESIGNATION from HRM_MST_DESIGNATIONS  where SERVICE_GROUP_ID=? order by DESIGNATION");
                ps.setInt(1, sgroup);
                rs = ps.executeQuery();
                int count = 0;
                xml = xml + "<flag>success</flag>";
                while (rs.next()) {

                    xml =
 xml + "<option><id>" + rs.getInt("DESIGNATION_ID") + "</id><name>" +
   rs.getString("DESIGNATION") + "</name></option>";
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


        } else if (strCommand.equalsIgnoreCase("Emp")) {
            xml = "<response>";

            try {
                System.out.println("sgroup::" + sgroup);
                String empname = request.getParameter("txtEmpName").trim();
                String strgroup = request.getParameter("cmbsgroup").trim();
                String strdes = request.getParameter("cmbdes").trim();
                int count = 0;
                int group = 0, des = 0;
                System.out.println("empname::" + empname.length());
                System.out.println("cmbsgroup::" + strgroup);
                System.out.println("strdes::" + strdes);
                group = Integer.parseInt(strgroup);
                des = Integer.parseInt(strdes);
                System.out.println("Group integer:" + group);
                System.out.println("Designation interger:" + des);


                /* ps=con.prepareStatement("select  from HRM_EMP_CONTROLLING_OFFICE where EMPLOYEE_ID=?" );
             ps.setInt(1,sgroup);
             rs=ps.executeQuery();*/

                UserProfile up = null;
                up = (UserProfile)session.getAttribute("UserProfile");

                String sql1 =
                    "select OFFICE_ID  from HRM_EMP_CURRENT_POSTING where employee_id=?";
                ps = con.prepareStatement(sql1);
                ps.setInt(1, up.getEmployeeId());
                rs = ps.executeQuery();
                int control_office_id = 0;
                if (rs.next()) {
                    control_office_id = rs.getInt("OFFICE_ID");
                }

                if ((empname != null && empname.length() != 0) && group != 0 &&
                    des != 0) {
                    System.out.println("block1");

                    String sql =
                        "select HRM_MST_EMPLOYEES.EMPLOYEE_ID,HRM_MST_EMPLOYEES.EMPLOYEE_NAME,HRM_MST_EMPLOYEES.EMPLOYEE_INITIAL,HRM_MST_DESIGNATIONS.DESIGNATION,HRM_MST_EMPLOYEES.DATE_OF_BIRTH,HRM_MST_EMPLOYEES.GPF_NO ";
                    sql =
 sql + " from HRM_MST_EMPLOYEES, HRM_MST_DESIGNATIONS, HRM_MST_SERVICE_GROUP, HRM_EMP_CURRENT_POSTING ,HRM_EMP_CONTROLLING_OFFICE ";
                    sql =
 sql + " where HRM_MST_DESIGNATIONS.SERVICE_GROUP_ID=HRM_MST_SERVICE_GROUP.SERVICE_GROUP_ID and ";
                    sql =
 sql + " HRM_EMP_CURRENT_POSTING.DESIGNATION_ID=HRM_MST_DESIGNATIONS.DESIGNATION_ID and ";
                    sql =
 sql + " HRM_EMP_CURRENT_POSTING.EMPLOYEE_STATUS_ID  in ('DPN') and ";
                    sql =
 sql + " HRM_MST_EMPLOYEES.EMPLOYEE_ID=HRM_EMP_CURRENT_POSTING.EMPLOYEE_ID and ";
                    sql =
 sql + "  HRM_MST_EMPLOYEES.EMPLOYEE_ID=HRM_EMP_CONTROLLING_OFFICE.EMPLOYEE_ID and ";
                    sql =
 sql + "  HRM_MST_SERVICE_GROUP.SERVICE_GROUP_ID=? and ";
                    sql =
 sql + "  HRM_MST_DESIGNATIONS.DESIGNATION_ID =? and ";
                    sql =
 sql + " upper(HRM_MST_EMPLOYEES.EMPLOYEE_NAME) like ? and";
                    sql =
 sql + " HRM_EMP_CONTROLLING_OFFICE.CONTROLLING_OFFICE_ID =? order by EMPLOYEE_NAME";


                    ps = con.prepareStatement(sql);
                    ps.setInt(1, group);
                    ps.setInt(2, des);
                    ps.setString(3, (empname + "%").toUpperCase());
                    ps.setInt(4, control_office_id);
                    rs = ps.executeQuery();

                    xml = xml + "<flag>success</flag>";
                    while (rs.next()) {

                        // xml=xml+"<option><id>"+rs.getInt("DESIGNATION_ID")+"</id><name>"+rs.getString("DESIGNATION")+"</name></option>";
                        xml = xml + "<employee>";
                        xml =
 xml + "<empid>" + rs.getInt("EMPLOYEE_ID") + "</empid>";
                        xml =
 xml + "<empname>" + rs.getString("EMPLOYEE_NAME") + "</empname>";
                        xml =
 xml + "<initial>" + rs.getString("EMPLOYEE_INITIAL") + "</initial>";
                        xml =
 xml + "<designation>" + rs.getString("DESIGNATION") + "</designation>";
                        if (rs.getDate("DATE_OF_BIRTH") != null) {
                            String[] sd =
                                rs.getDate("DATE_OF_BIRTH").toString().split("-");
                            String od = sd[2] + "/" + sd[1] + "/" + sd[0];
                            xml = xml + "<dob>" + od + "</dob>";
                        } else {
                            xml =
 xml + "<dob>" + rs.getDate("DATE_OF_BIRTH") + "</dob>";
                        }
                        xml =
 xml + "<gpf>" + rs.getString("GPF_NO") + "</gpf>";
                        xml = xml + "</employee>";

                        count++;
                    }
                } else if ((empname == null || empname.length() == 0) &&
                           group != 0 && des != 0) {
                    System.out.println("block2");
                    // group=Integer.parseInt(strgroup);
                    // des=Integer.parseInt(strdes);

                    String sql =
                        "select HRM_MST_EMPLOYEES.EMPLOYEE_ID,HRM_MST_EMPLOYEES.EMPLOYEE_NAME,HRM_MST_EMPLOYEES.EMPLOYEE_INITIAL,HRM_MST_DESIGNATIONS.DESIGNATION,HRM_MST_EMPLOYEES.DATE_OF_BIRTH,HRM_MST_EMPLOYEES.GPF_NO ";
                    sql =
 sql + " from HRM_MST_EMPLOYEES, HRM_MST_DESIGNATIONS, HRM_MST_SERVICE_GROUP, HRM_EMP_CURRENT_POSTING,HRM_EMP_CONTROLLING_OFFICE ";
                    sql =
 sql + " where HRM_MST_DESIGNATIONS.SERVICE_GROUP_ID=HRM_MST_SERVICE_GROUP.SERVICE_GROUP_ID and ";
                    sql =
 sql + " HRM_EMP_CURRENT_POSTING.DESIGNATION_ID=HRM_MST_DESIGNATIONS.DESIGNATION_ID and ";
                    sql =
 sql + "  HRM_EMP_CURRENT_POSTING.EMPLOYEE_STATUS_ID  in ('DPN') and ";
                    sql =
 sql + " HRM_MST_EMPLOYEES.EMPLOYEE_ID=HRM_EMP_CURRENT_POSTING.EMPLOYEE_ID and ";
                    sql =
 sql + "  HRM_MST_EMPLOYEES.EMPLOYEE_ID=HRM_EMP_CONTROLLING_OFFICE.EMPLOYEE_ID and ";
                    sql =
 sql + "  HRM_MST_SERVICE_GROUP.SERVICE_GROUP_ID=? and ";
                    sql =
 sql + "  HRM_MST_DESIGNATIONS.DESIGNATION_ID =?   and ";
                    sql =
 sql + " HRM_EMP_CONTROLLING_OFFICE.CONTROLLING_OFFICE_ID =? order by EMPLOYEE_NAME";
                    //sql=sql+" HRM_MST_EMPLOYEES.EMPLOYEE_NAME like ?";
                    System.out.println("here is ok1");
                    ps = con.prepareStatement(sql);
                    ps.setInt(1, group);
                    ps.setInt(2, des);
                    ps.setInt(3, control_office_id);
                    //ps.setString(3,(empname+"%").toUpperCase());
                    rs = ps.executeQuery();
                    System.out.println("here is ok2");
                    xml = xml + "<flag>success</flag>";
                    while (rs.next()) {

                        // xml=xml+"<option><id>"+rs.getInt("DESIGNATION_ID")+"</id><name>"+rs.getString("DESIGNATION")+"</name></option>";
                        xml = xml + "<employee>";
                        xml =
 xml + "<empid>" + rs.getInt("EMPLOYEE_ID") + "</empid>";
                        xml =
 xml + "<empname>" + rs.getString("EMPLOYEE_NAME") + "</empname>";
                        xml =
 xml + "<initial>" + rs.getString("EMPLOYEE_INITIAL") + "</initial>";
                        xml =
 xml + "<designation>" + rs.getString("DESIGNATION") + "</designation>";
                        if (rs.getDate("DATE_OF_BIRTH") != null) {
                            String[] sd =
                                rs.getDate("DATE_OF_BIRTH").toString().split("-");
                            String od = sd[2] + "/" + sd[1] + "/" + sd[0];
                            xml = xml + "<dob>" + od + "</dob>";
                        } else {
                            xml =
 xml + "<dob>" + rs.getDate("DATE_OF_BIRTH") + "</dob>";
                        }

                        xml =
 xml + "<gpf>" + rs.getString("GPF_NO") + "</gpf>";
                        xml = xml + "</employee>";

                        count++;
                    }
                    System.out.println("here is ok3");
                } else if ((empname == null || empname.length() == 0) &&
                           group != 0 && des == 0) {
                    System.out.println("block3");
                    // group=Integer.parseInt(strgroup);
                    // des=Integer.parseInt(strdes);

                    String sql =
                        "select HRM_MST_EMPLOYEES.EMPLOYEE_ID,HRM_MST_EMPLOYEES.EMPLOYEE_NAME,HRM_MST_EMPLOYEES.EMPLOYEE_INITIAL,HRM_MST_DESIGNATIONS.DESIGNATION,HRM_MST_EMPLOYEES.DATE_OF_BIRTH,HRM_MST_EMPLOYEES.GPF_NO ";
                    sql =
 sql + " from HRM_MST_EMPLOYEES, HRM_MST_DESIGNATIONS, HRM_MST_SERVICE_GROUP, HRM_EMP_CURRENT_POSTING,HRM_EMP_CONTROLLING_OFFICE ";
                    sql =
 sql + " where HRM_MST_DESIGNATIONS.SERVICE_GROUP_ID=HRM_MST_SERVICE_GROUP.SERVICE_GROUP_ID and ";
                    sql =
 sql + " HRM_EMP_CURRENT_POSTING.DESIGNATION_ID=HRM_MST_DESIGNATIONS.DESIGNATION_ID and ";
                    sql =
 sql + " HRM_MST_EMPLOYEES.EMPLOYEE_ID=HRM_EMP_CONTROLLING_OFFICE.EMPLOYEE_ID and ";
                    sql =
 sql + "  HRM_EMP_CURRENT_POSTING.EMPLOYEE_STATUS_ID in ('DPN') and ";
                    sql =
 sql + " HRM_MST_EMPLOYEES.EMPLOYEE_ID=HRM_EMP_CURRENT_POSTING.EMPLOYEE_ID and ";
                    sql =
 sql + "  HRM_MST_SERVICE_GROUP.SERVICE_GROUP_ID=? and ";
                    sql =
 sql + " HRM_EMP_CONTROLLING_OFFICE.CONTROLLING_OFFICE_ID =? order by EMPLOYEE_NAME";
                    // sql=sql+"  HRM_MST_DESIGNATIONS.DESIGNATION_ID =?  " ;
                    //sql=sql+" HRM_MST_EMPLOYEES.EMPLOYEE_NAME like ?";

                    ps = con.prepareStatement(sql);
                    ps.setInt(1, group);
                    ps.setInt(2, control_office_id);
                    // ps.setInt(2,des);
                    // ps.setString(3,(empname+"%").toUpperCase());
                    rs = ps.executeQuery();

                    xml = xml + "<flag>success</flag>";
                    while (rs.next()) {

                        // xml=xml+"<option><id>"+rs.getInt("DESIGNATION_ID")+"</id><name>"+rs.getString("DESIGNATION")+"</name></option>";
                        xml = xml + "<employee>";
                        xml =
 xml + "<empid>" + rs.getInt("EMPLOYEE_ID") + "</empid>";
                        xml =
 xml + "<empname>" + rs.getString("EMPLOYEE_NAME") + "</empname>";
                        xml =
 xml + "<initial>" + rs.getString("EMPLOYEE_INITIAL") + "</initial>";
                        xml =
 xml + "<designation>" + rs.getString("DESIGNATION") + "</designation>";
                        if (rs.getDate("DATE_OF_BIRTH") != null) {
                            String[] sd =
                                rs.getDate("DATE_OF_BIRTH").toString().split("-");
                            String od = sd[2] + "/" + sd[1] + "/" + sd[0];
                            xml = xml + "<dob>" + od + "</dob>";
                        } else {
                            xml =
 xml + "<dob>" + rs.getDate("DATE_OF_BIRTH") + "</dob>";
                        }
                        xml =
 xml + "<gpf>" + rs.getString("GPF_NO") + "</gpf>";
                        xml = xml + "</employee>";

                        count++;
                    }
                } else if ((empname != null && empname.length() != 0) &&
                           group == 0 && des == 0) {
                    System.out.println("group4");
                    // group=Integer.parseInt(strgroup);
                    // des=Integer.parseInt(strdes);

                    String sql =

                        // " left outer join  HRM_MST_SERVICE_GROUP c on b.SERVICE_GROUP_ID=c.SERVICE_GROUP_ID  " +
                        "select a.EMPLOYEE_ID,a.EMPLOYEE_NAME,a.EMPLOYEE_INITIAL, " +
                        " b.DESIGNATION,a.DATE_OF_BIRTH,a.GPF_NO   from HRM_MST_EMPLOYEES a " +
                        " inner join HRM_EMP_CONTROLLING_OFFICE e on e.CONTROLLING_OFFICE_ID=?" +
                        "inner join  HRM_EMP_CURRENT_POSTING d on a.EMPLOYEE_ID=d.EMPLOYEE_ID and EMPLOYEE_STATUS_ID in ('DPN') and d.EMPLOYEE_ID=e.EMPLOYEE_ID " +
                        " left outer join  HRM_MST_DESIGNATIONS b on d.DESIGNATION_ID=b.DESIGNATION_ID  " +
                        " where   upper(a.EMPLOYEE_NAME) like ?    order by EMPLOYEE_NAME";
                    System.out.println(sql);
                    ps = con.prepareStatement(sql);
                    // ps.setInt(1,group);
                    // ps.setInt(2,des);
                    ps.setInt(1, control_office_id);
                    ps.setString(2, (empname + "%").toUpperCase());
                    System.out.println("empname:::::" +
                                       (empname + "%").toUpperCase());
                    rs = ps.executeQuery();

                    xml = xml + "<flag>success</flag>";
                    while (rs.next()) {

                        // xml=xml+"<option><id>"+rs.getInt("DESIGNATION_ID")+"</id><name>"+rs.getString("DESIGNATION")+"</name></option>";
                        xml = xml + "<employee>";
                        xml =
 xml + "<empid>" + rs.getInt("EMPLOYEE_ID") + "</empid>";
                        xml =
 xml + "<empname>" + rs.getString("EMPLOYEE_NAME") + "</empname>";
                        xml =
 xml + "<initial>" + rs.getString("EMPLOYEE_INITIAL") + "</initial>";
                        xml =
 xml + "<designation>" + rs.getString("DESIGNATION") + "</designation>";
                        if (rs.getDate("DATE_OF_BIRTH") != null) {
                            String[] sd =
                                rs.getDate("DATE_OF_BIRTH").toString().split("-");
                            String od = sd[2] + "/" + sd[1] + "/" + sd[0];
                            xml = xml + "<dob>" + od + "</dob>";
                        } else {
                            xml =
 xml + "<dob>" + rs.getDate("DATE_OF_BIRTH") + "</dob>";
                        }
                        xml =
 xml + "<gpf>" + rs.getString("GPF_NO") + "</gpf>";
                        xml = xml + "</employee>";
                        System.out.println("ok");
                        count++;
                    }
                } else if ((empname != null && empname.length() != 0) &&
                           group != 0 && des == 0) {
                    System.out.println("group4");
                    // group=Integer.parseInt(strgroup);
                    //des=Integer.parseInt(strdes);

                    String sql =
                        "select HRM_MST_EMPLOYEES.EMPLOYEE_ID,HRM_MST_EMPLOYEES.EMPLOYEE_NAME,HRM_MST_EMPLOYEES.EMPLOYEE_INITIAL,HRM_MST_DESIGNATIONS.DESIGNATION,HRM_MST_EMPLOYEES.DATE_OF_BIRTH,HRM_MST_EMPLOYEES.GPF_NO ";
                    sql =
 sql + " from HRM_MST_EMPLOYEES, HRM_MST_DESIGNATIONS, HRM_MST_SERVICE_GROUP, HRM_EMP_CURRENT_POSTING,HRM_EMP_CONTROLLING_OFFICE ";
                    sql =
 sql + " where HRM_MST_DESIGNATIONS.SERVICE_GROUP_ID=HRM_MST_SERVICE_GROUP.SERVICE_GROUP_ID and ";
                    sql =
 sql + " HRM_EMP_CURRENT_POSTING.DESIGNATION_ID=HRM_MST_DESIGNATIONS.DESIGNATION_ID and ";
                    sql =
 sql + "  HRM_EMP_CURRENT_POSTING.EMPLOYEE_STATUS_ID in ('DPN') and ";
                    sql =
 sql + " HRM_MST_EMPLOYEES.EMPLOYEE_ID=HRM_EMP_CURRENT_POSTING.EMPLOYEE_ID and ";
                    sql =
 sql + "  HRM_MST_EMPLOYEES.EMPLOYEE_ID=HRM_EMP_CONTROLLING_OFFICE.EMPLOYEE_ID and ";
                    sql =
 sql + "  HRM_MST_SERVICE_GROUP.SERVICE_GROUP_ID=? and  ";
                    // sql=sql+"  HRM_MST_DESIGNATIONS.DESIGNATION_ID =?  " ;
                    sql =
 sql + " upper(HRM_MST_EMPLOYEES.EMPLOYEE_NAME) like ?  and ";
                    sql =
 sql + " HRM_EMP_CONTROLLING_OFFICE.CONTROLLING_OFFICE_ID =? order by EMPLOYEE_NAME";
                    System.out.println(sql);
                    ps = con.prepareStatement(sql);
                    ps.setInt(1, group);
                    // ps.setInt(2,des);
                    ps.setString(2, (empname + "%").toUpperCase());
                    ps.setInt(3, control_office_id);
                    rs = ps.executeQuery();

                    xml = xml + "<flag>success</flag>";
                    while (rs.next()) {

                        // xml=xml+"<option><id>"+rs.getInt("DESIGNATION_ID")+"</id><name>"+rs.getString("DESIGNATION")+"</name></option>";
                        xml = xml + "<employee>";
                        xml =
 xml + "<empid>" + rs.getInt("EMPLOYEE_ID") + "</empid>";
                        xml =
 xml + "<empname>" + rs.getString("EMPLOYEE_NAME") + "</empname>";
                        xml =
 xml + "<initial>" + rs.getString("EMPLOYEE_INITIAL") + "</initial>";
                        xml =
 xml + "<designation>" + rs.getString("DESIGNATION") + "</designation>";
                        if (rs.getDate("DATE_OF_BIRTH") != null) {
                            String[] sd =
                                rs.getDate("DATE_OF_BIRTH").toString().split("-");
                            String od = sd[2] + "/" + sd[1] + "/" + sd[0];
                            xml = xml + "<dob>" + od + "</dob>";
                        } else {
                            xml =
 xml + "<dob>" + rs.getDate("DATE_OF_BIRTH") + "</dob>";
                        }
                        xml =
 xml + "<gpf>" + rs.getString("GPF_NO") + "</gpf>";
                        xml = xml + "</employee>";
                        System.out.println("ok");
                        count++;
                    }
                }
                System.out.println("count::" + count);
                if (count == 0)
                    xml = "<response><flag>failure</flag>";


            }

            catch (Exception e) {

                System.out.println("catch........" + e);
                // e.printStackTrace();

                xml = "<response><flag>failure</flag>";
            }

            xml = xml + "</response>";


        } else if (strCommand.equalsIgnoreCase("GPF")) {
            xml = "<response>";
            System.out.println("group gpf");
            System.out.println("gpf no from request::" +
                               request.getParameter("txtgpf"));
            int gpfno = Integer.parseInt(request.getParameter("txtgpf"));


            try {


                UserProfile up = null;
                up = (UserProfile)session.getAttribute("UserProfile");

                String sql1 =
                    "select OFFICE_ID  from HRM_EMP_CURRENT_POSTING where employee_id=?";
                ps = con.prepareStatement(sql1);
                ps.setInt(1, up.getEmployeeId());
                rs = ps.executeQuery();
                int control_office_id = 0;
                if (rs.next()) {
                    control_office_id = rs.getInt("OFFICE_ID");
                }

                String sql =
                    "select a.EMPLOYEE_ID,a.EMPLOYEE_NAME,a.EMPLOYEE_INITIAL, " +
                    " b.DESIGNATION,a.DATE_OF_BIRTH,a.GPF_NO   from HRM_MST_EMPLOYEES a " +
                    " inner join HRM_EMP_CONTROLLING_OFFICE e on e.CONTROLLING_OFFICE_ID=?" +
                    " inner join  HRM_EMP_CURRENT_POSTING d on a.EMPLOYEE_ID=d.EMPLOYEE_ID and EMPLOYEE_STATUS_ID in ('DPN') and d.EMPLOYEE_ID=e.EMPLOYEE_ID " +
                    " left outer join  HRM_MST_DESIGNATIONS b on d.DESIGNATION_ID=b.DESIGNATION_ID  " +
                    " left outer join  HRM_MST_SERVICE_GROUP c on b.SERVICE_GROUP_ID=c.SERVICE_GROUP_ID  " +
                    " where   a.GPF_NO = ?  order by EMPLOYEE_NAME ";


                System.out.println(sql);
                ps = con.prepareStatement(sql);
                ps.setInt(1, control_office_id);
                ps.setInt(2, gpfno);

                System.out.println("Gpf No:" + gpfno);
                rs = ps.executeQuery();

                xml = xml + "<flag>success</flag>";
                if (rs.next()) {

                    // xml=xml+"<option><id>"+rs.getInt("DESIGNATION_ID")+"</id><name>"+rs.getString("DESIGNATION")+"</name></option>";
                    xml = xml + "<employee>";
                    xml =
 xml + "<empid>" + rs.getInt("EMPLOYEE_ID") + "</empid>";
                    xml =
 xml + "<empname>" + rs.getString("EMPLOYEE_NAME") + "</empname>";
                    xml =
 xml + "<initial>" + rs.getString("EMPLOYEE_INITIAL") + "</initial>";
                    xml =
 xml + "<designation>" + rs.getString("DESIGNATION") + "</designation>";
                    if (rs.getDate("DATE_OF_BIRTH") != null) {
                        String[] sd =
                            rs.getDate("DATE_OF_BIRTH").toString().split("-");
                        String od = sd[2] + "/" + sd[1] + "/" + sd[0];
                        xml = xml + "<dob>" + od + "</dob>";
                    } else {
                        xml =
 xml + "<dob>" + rs.getDate("DATE_OF_BIRTH") + "</dob>";
                    }
                    xml = xml + "<gpf>" + rs.getString("GPF_NO") + "</gpf>";
                    xml = xml + "</employee>";
                    System.out.println("ok");

                } else {
                    xml = "<response><flag>failure</flag>";
                }
            } catch (Exception e) {

                System.out.println("catch........" + e);
                // e.printStackTrace();

                xml = "<response><flag>failure</flag>";
            }

            xml = xml + "</response>";
        }
        out.println(xml);
        System.out.println(xml);
    }

}
