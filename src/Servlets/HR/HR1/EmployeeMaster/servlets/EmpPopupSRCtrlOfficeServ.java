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

public class EmpPopupSRCtrlOfficeServ extends HttpServlet {


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
        try {
            if (session == null) {
                String xml =
                    "<response><command>sessionout</command><flag>sessionout</flag></response>";
                out.println(xml);
                System.out.println(xml);
                out.close();
                return;

            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }


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


        if (strCommand.equalsIgnoreCase("Emp")) {
            xml = "<response>";

            try {
                System.out.println("sgroup::" + sgroup);

                int count = 0;

                UserProfile empProfile =
                    (UserProfile)session.getAttribute("UserProfile");

                System.out.println("user id::" + empProfile.getEmployeeId());
                int empid = empProfile.getEmployeeId();
                int oid = 0;

                ps =
  con.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?");
                ps.setInt(1, empid);
                rs = ps.executeQuery();
                if (rs.next()) {
                    oid = rs.getInt("OFFICE_ID");
                }
                rs.close();
                ps.close();

                String sql =
                    "select a.EMPLOYEE_ID,a.EMPLOYEE_NAME,a.EMPLOYEE_INITIAL, " +
                    " b.DESIGNATION,a.DATE_OF_BIRTH,a.GPF_NO   from  HRM_MST_EMPLOYEES a " +
                    " inner join HRM_EMP_CONTROLLING_OFFICE c on c.EMPLOYEE_ID =a.EMPLOYEE_ID " +
                    " inner join  HRM_EMP_CURRENT_POSTING d on d.EMPLOYEE_ID=c.EMPLOYEE_ID " +
                    " left outer join  HRM_MST_DESIGNATIONS b on b.DESIGNATION_ID=d.DESIGNATION_ID  " +
                    " where   c.CONTROLLING_OFFICE_ID =?   order by EMPLOYEE_NAME ";
                System.out.println(sql);
                ps = con.prepareStatement(sql);
                ps.setInt(1, oid);
                rs = ps.executeQuery();


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
                    xml = xml + "<gpf>" + rs.getString("GPF_NO") + "</gpf>";
                    xml = xml + "</employee>";
                    System.out.println("ok");
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
                // e.printStackTrace();

                xml = "<response><flag>failure</flag>";
            }

            xml = xml + "</response>";


        }

        out.println(xml);
        System.out.println(xml);
    }

}

