package Servlets.FAS.FAS1.Masters.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class OtherDepartments extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);
        System.out.println("inside servlet..........");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>OtherDepartments</title></head>");
        out.println("<body>");

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String xml = "";


        try {
            String txtDept_id = request.getParameter("txtDept_id");
            //String txtDept_id=request.getParameter("txtDistrict_id");
            System.out.println(txtDept_id);


            ResourceBundle rs1 =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";

            String strDriver = rs1.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rs1.getString("Config.DSN");
            String strhostname = rs1.getString("Config.HOST_NAME");
            String strportno = rs1.getString("Config.PORT_NUMBER");
            String strsid = rs1.getString("Config.SID");
            String strdbusername = rs1.getString("Config.USER_NAME");
            String strdbpassword = rs1.getString("Config.PASSWORD");

            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection


            Class.forName(strDriver.trim());
            connection =
                    DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                                                strdbpassword.trim());


            System.out.println("connected");
            ps =
  connection.prepareStatement("select other_dept_id,other_dept_office_id,other_dept_office_name from hrm_mst_other_dept_offices where other_dept_id=?");
            ps.setString(1, txtDept_id);
            rs = ps.executeQuery();
            xml = "<response><command>loadOffices</command>";
            xml = xml + "<flag>Success</flag>";

            while (rs.next()) {
                /*String s=rs.getString(1);
                    System.out.println(s);
                    System.out.println(rs.getString(2));
                    System.out.println(rs.getString(3));*/

                xml = xml + "<dept_id>" + rs.getString(1) + "</dept_id>";
                xml = xml + "<office_id>" + rs.getString(2) + "</office_id>";
                xml =
 xml + "<office_name>" + rs.getString(3) + "</office_name>";


            }
            System.out.println("AAAA");


        } catch (Exception e) {
            xml = xml + "<flag>Failure</flag>";
            //xml=xml+"<result>fail</result>";
            System.out.println("Error in getting values");
        }
        xml = xml + "</response>";
        //out.println("<p>The servlet has received a GET. This is the reply.</p>");
        out.println(xml);
        System.out.println(xml);
        out.println("</body></html>");
        out.close();
    }
}
