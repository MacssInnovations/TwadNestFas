package Servlets.SysAdmin.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.*;

import java.util.ResourceBundle;

public class CreateNewUser_EmpName extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";
    private PreparedStatement ps1 = null;
    private PreparedStatement ps2 = null;
    private Connection connection = null;
    private ResultSet results = null;
    private ResultSet results1 = null;
    private Statement statement = null;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);

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

//            ConnectionString =
//                    strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
//                    ":" + strsid.trim();
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection

            Class.forName(strDriver.trim());
            connection =
                    DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                                                strdbpassword.trim());
            System.out.println("this is emp name servlet of creat new user");

            try {
                statement = connection.createStatement();
                connection.clearWarnings();
            } catch (SQLException e) {
                //System.out.println("Exception in creating statement:"+e);
            }
        } catch (Exception e) {
            //System.out.println("Exception in openeing connection:"+e);
        }

    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        String strCommand = "";
        String xml = "";
        int EmployeeId = 0;
        PrintWriter pw = response.getWriter();
        response.setContentType("text/xml");
        response.setHeader("cache-control", "no-cache");
        // HttpSession session=request.getSession(false);

        try {
            strCommand = request.getParameter("command");
            System.out.println("command is" + strCommand);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("before ");
        if (strCommand.equalsIgnoreCase("EmpName")) {
            xml = "<response><command>EmpName</command>";
            EmployeeId = Integer.parseInt(request.getParameter("EmpId"));
            System.out.println("emp name is  " + EmployeeId);
            try {

                System.out.println("after ");
                ps1 =
 connection.prepareStatement("Select employee_id from SEC_MST_USERS where employee_id=?");
                ps1.setInt(1, EmployeeId);
                results = ps1.executeQuery();

                if (results.next()) {
                    xml = xml + "<flag>exist</flag>";
                } else {

                    ps2 =
 connection.prepareStatement("Select EMPLOYEE_NAME||decode(employee_initial,null,' ','.'||employee_initial) EMPLOYEE_NAME from  hrm_mst_employees where employee_id=?");
                    ps2.setInt(1, EmployeeId);
                    results1 = ps2.executeQuery();
                    if (results1.next()) {
                        xml =
 xml + "<flag>success</flag><EmpName>" + results1.getString("EMPLOYEE_NAME") +
   "</EmpName>";
                    } else {
                        xml = xml + "<flag>failure</flag>";
                    }
                }

                /*  System.out.println("after ");
           ps1=connection.prepareStatement("Select a.EMPLOYEE_NAME from SEC_MST_USERS b inner join hrm_mst_employees a on a.employee_id=b.employee_id and b.employee_id=?");
             ps1.setInt(1,EmployeeId);
             results=ps1.executeQuery();

             if(results.next()) {
                 xml=xml+"<flag>success</flag><EmpName>"+results.getString("EMPLOYEE_NAME")+"</EmpName>";
             }
             else {

                 ps2=connection.prepareStatement("Select EMPLOYEE_NAME from  hrm_mst_employees where employee_id=?");
                   ps2.setInt(1,EmployeeId);
                   results1=ps2.executeQuery();
                   if(results1.next())
                   {
                 xml=xml+"<flag>failure</flag><EmpName>"+results1.getString("EMPLOYEE_NAME")+"</EmpName>";
                   }
             }
             */

            } catch (Exception ae) {
                xml = xml + "<flag>failure</flag>";
                System.out.println("Error in the employee fetching " + ae);
            }

            xml = xml + "</response>";

        }

        System.out.println("xml is : " + xml);
        pw.write(xml);
        pw.flush();
        pw.close();


    }
}
