package Servlets.HR.HR1.EmployeeMaster.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class EmployeeNameFilter extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);


    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {

        Connection connection = null;
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
            connection =
                    DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                                                strdbpassword.trim());

        } catch (Exception e) {
            System.out.println("Exception :" + e);
        }
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        System.out.println("hai");
        response.setContentType(CONTENT_TYPE);
        response.setContentType("text/xml");

        // int OfficeId=0 ;
        String EmployeeName = request.getParameter("EmployeeName");
        System.out.println(EmployeeName);
        int found = 0;
        String xml = "";

        try {

            String sql =
                "select EMPLOYEE_ID,EMPLOYEE_NAME ||decode(EMPLOYEE_INITIAL,null,' ','.'||EMPLOYEE_INITIAL) as  EMPLOYEE_NAME  from HR_MST_EMPLOYEES where EMPLOYEE_NAME like ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, EmployeeName + "%");
            connection.clearWarnings();
            try {
                ResultSet results = statement.executeQuery();
                try {
                    xml = "<response><flag>success</flag>";
                    while (results.next()) {
                        xml =
 xml + "<options><id>" + results.getInt("EMPLOYEE_ID") + "</id><name>" +
   results.getString("EMPLOYEE_NAME") + "</name></options>";
                        found++;
                    }
                    if (found == 0)
                        xml = "<response><flag>failure</flag>";

                    xml = xml + "</response>";
                } catch (SQLException e) {
                    System.out.println(e);
                } finally {
                    results.close();
                }
            } catch (SQLException e) {
                System.out.println("Exception in statement:" + e);
            } finally {
                statement.close();
            }
        } catch (SQLException e) {
            System.out.println("Exception in connection:" + e);
        }


        System.out.println("Xml is : " + xml);

        out.println(xml);
        out.close();
    }
}
