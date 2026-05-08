package Servlets.HR.HR1.EmployeeMaster.servlets;


import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class EmpBasicPopupServ extends HttpServlet {


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
        try {
            strCommand = request.getParameter("Command");
        } catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }

        System.out.println("command:" + strCommand);
        if (strCommand.equalsIgnoreCase("Emp")) {
            xml = "<response>";

            try {
                String empname = request.getParameter("txtEmpName").trim();
                int count = 0;
                System.out.println("empname::" + empname.length());
                System.out.println("block1");
                String sql =
                    "select HRM_MST_EMPLOYEES_BASIC_TMP.EMPLOYEE_ID,HRM_MST_EMPLOYEES_BASIC_TMP.EMPLOYEE_NAME,HRM_MST_EMPLOYEES_BASIC_TMP.EMPLOYEE_INITIAL,HRM_MST_EMPLOYEES_BASIC_TMP.GPF_NO ";
                sql = sql + " from HRM_MST_EMPLOYEES_BASIC_TMP ";
                sql =
 sql + " where  upper(HRM_MST_EMPLOYEES_BASIC_TMP.EMPLOYEE_NAME) like ? and (PROCESS_FLOW_STATUS_ID='CR' or PROCESS_FLOW_STATUS_ID ='MD' ) order by EMPLOYEE_NAME";


                ps = con.prepareStatement(sql);
                ps.setString(1, (empname + "%").toUpperCase());
                rs = ps.executeQuery();

                xml = xml + "<flag>success</flag>";
                while (rs.next()) {

                    // xml=xml+"<option><id>"+rs.getInt("DESIGNATION_ID")+"</id><name>"+rs.getString("DESIGNATION")+"</name></option>";
                    xml = xml + "<employee>";
                    xml =
 xml + "<empid>" + rs.getInt("EMPLOYEE_ID") + "</empid>";
                    xml =
 xml + "<empname>" + rs.getString("EMPLOYEE_NAME") + (rs.getString("EMPLOYEE_INITIAL") !=
                                                      null ?
                                                      "." + rs.getString("EMPLOYEE_INITIAL").toUpperCase() :
                                                      " ") + "</empname>";
                    xml = xml + "<gpf>" + rs.getString("GPF_NO") + "</gpf>";
                    xml = xml + "</employee>";

                    count++;
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
                    " a.GPF_NO   from HRM_MST_EMPLOYEES_BASIC_TMP a " +
                    " where   a.GPF_NO = ? and (a.PROCESS_FLOW_STATUS_ID='CR' or a.PROCESS_FLOW_STATUS_ID ='MD' )  order by EMPLOYEE_NAME";
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
 xml + "<empname>" + rs.getString("EMPLOYEE_NAME") + (rs.getString("EMPLOYEE_INITIAL") !=
                                                      null ?
                                                      "." + rs.getString("EMPLOYEE_INITIAL").toUpperCase() :
                                                      " ") + "</empname>";
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
