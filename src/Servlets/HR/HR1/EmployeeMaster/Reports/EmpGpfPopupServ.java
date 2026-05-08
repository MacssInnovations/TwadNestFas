package Servlets.HR.HR1.EmployeeMaster.Reports;


import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class EmpGpfPopupServ extends HttpServlet {


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
            ConnectionString =
                    strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
                    ":" + strsid.trim();
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
            //sgroup=Integer.parseInt(request.getParameter("cmbsgroup"));
        }

        catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }

        if (strCommand.equalsIgnoreCase("Emp")) {
            xml = "<response>";

            try {
                //  System.out.println("sgroup::"+sgroup);
                String empname = request.getParameter("txtEmpName").trim();
                // String strgroup=request.getParameter("cmbsgroup").trim();
                //String strdes=request.getParameter("cmbdes").trim();
                int count = 0;
                int group = 0, des = 0;
                System.out.println("empname::" + empname.length());

                if ((empname != null && empname.length() != 0)) {
                    System.out.println("block1");
                    //group=Integer.parseInt(strgroup);
                    //des=Integer.parseInt(strdes);
                    /* String sql="select HRM_MST_EMPLOYEES.EMPLOYEE_ID,HRM_MST_EMPLOYEES.EMPLOYEE_NAME,HRM_MST_DESIGNATIONS.DESIGNATION,HRM_MST_EMPLOYEES.DATE_OF_BIRTH,HRM_MST_EMPLOYEES.GPF_NO ";
                    sql=sql+" from HRM_MST_EMPLOYEES, HRM_MST_DESIGNATIONS, HRM_MST_SERVICE_GROUP, HRM_EMP_CURRENT_POSTING ";
                    sql=sql+" where HRM_MST_DESIGNATIONS.SERVICE_GROUP_ID=HRM_MST_SERVICE_GROUP.SERVICE_GROUP_ID and ";
                    sql=sql+" HRM_EMP_CURRENT_POSTING.DESIGNATION_ID=HRM_MST_DESIGNATIONS.DESIGNATION_ID and ";
                    sql=sql+" HRM_MST_EMPLOYEES.EMPLOYEE_ID=HRM_EMP_CURRENT_POSTING.EMPLOYEE_ID";
                    */
                    String sql =
                        "select gpfno,emp_name,desig,dob,initial1,emp_name  from emp_cat where emp_name like ? order by EMP_NAME";


                    ps = con.prepareStatement(sql);


                    ps.setString(1, (empname + "%"));
                    rs = ps.executeQuery();

                    xml = xml + "<flag>success</flag>";
                    while (rs.next()) {

                        // xml=xml+"<option><id>"+rs.getInt("DESIGNATION_ID")+"</id><name>"+rs.getString("DESIGNATION")+"</name></option>";
                        xml = xml + "<employee>";
                        xml =
 xml + "<empid>" + rs.getInt("gpfno") + "</empid>";
                        xml =
 xml + "<empname>" + rs.getString("EMP_NAME") + "</empname>";
                        xml =
 xml + "<initial>" + rs.getString("INITIAL1") + "</initial>";

                        if (rs.getDate("dob") != null) {
                            String[] sd =
                                rs.getDate("dob").toString().split("-");
                            String od = sd[2] + "/" + sd[1] + "/" + sd[0];
                            xml = xml + "<dob>" + od + "</dob>";
                        } else {
                            xml = xml + "<dob>" + rs.getDate("dob") + "</dob>";
                        }
                        xml =
 xml + "<gpf>" + rs.getString("GPF_NO") + "</gpf>";
                        xml = xml + "</employee>";

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
                String sql =
                    "select a.EMPLOYEE_ID,a.EMPLOYEE_NAME,a.EMPLOYEE_INITIAL, " +
                    " b.DESIGNATION,a.DATE_OF_BIRTH,a.GPF_NO   from HRM_MST_EMPLOYEES a " +
                    "left outer join  HRM_EMP_CURRENT_POSTING d on a.EMPLOYEE_ID=d.EMPLOYEE_ID " +
                    " left outer join  HRM_MST_DESIGNATIONS b on d.DESIGNATION_ID=b.DESIGNATION_ID  " +
                    " left outer join  HRM_MST_SERVICE_GROUP c on b.SERVICE_GROUP_ID=c.SERVICE_GROUP_ID  " +
                    " where   a.GPF_NO = ?  order by EMPLOYEE_NAME";
                System.out.println(sql);
                ps = con.prepareStatement(sql);
                ps.setInt(1, gpfno);

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

