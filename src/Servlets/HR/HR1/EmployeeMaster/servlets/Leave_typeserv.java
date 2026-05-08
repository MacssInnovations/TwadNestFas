package Servlets.HR.HR1.EmployeeMaster.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;


public class Leave_typeserv extends HttpServlet {
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
        String xml = "";
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {

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
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
        } catch (Exception e) {
            System.out.println("Exception in connection...." + e);
        }

        String cmd = request.getParameter("command");
        System.out.println(cmd);
        String ltype = request.getParameter("cmdltype");
        String typedesc = request.getParameter("cmdtypedesc");
        String shortdesc = request.getParameter("cmdshortdesc");
        String rm = request.getParameter("cmdrm");

        int ltype1 = Integer.parseInt(ltype);
        System.out.println("register no is" + ltype1);
        System.out.println(typedesc);
        System.out.println(shortdesc);
        System.out.println(rm);

        out.println("<html>");
        out.println("<head><title>Exserv</title></head>");
        out.println("<body>");
        if (cmd.equalsIgnoreCase("load")) {
            xml = "<response1><command>load</command>";
            try {
                ps =
  con.prepareStatement("select * from HRM_LEAVE_TYPES where LEAVE_TYPE_ID=?");
                ps.setInt(1, ltype1);
                rs = ps.executeQuery();
                while (rs.next()) {
                    xml = xml + "<flag1>Success</flag1>";
                    xml = xml + "<code>" + rs.getInt(1) + "</code>";
                    xml = xml + "<desc>" + rs.getString(2) + "</desc>";
                    xml = xml + "<sdesc>" + rs.getString(3) + "</sdesc>";
                    xml = xml + "<remarks>" + rs.getString(4) + "</remarks>";

                }
                System.out.print(xml);
                con.commit();
            } catch (SQLException e) {
                xml = xml + "<flag1>failure</flag1>";
                System.out.println("err in load");
            }
            xml = xml + "</response1>";
            out.println(xml);
            System.out.println(xml);

        } else if (cmd.equalsIgnoreCase("Add")) {

            xml = "<response1><command>Add</command>";
            try {
                ps =
  con.prepareStatement("insert into HRM_LEAVE_TYPES(LEAVE_TYPE_ID,LEAVE_TYPE_DESC,LEAVE_TYPE_DESC_SHORT,REMARKS) values(?,?,?,?)");
                ps.setInt(1, ltype1);
                ps.setString(2, typedesc);
                ps.setString(3, shortdesc);
                ps.setString(4, rm);
                ps.executeUpdate();
                xml = xml + "<flag1>Success</flag1>";
                con.commit();
            } catch (SQLException e) {
                xml = xml + "<flag1>failure</flag1>";
                System.out.println("err in add");
            }
            xml = xml + "</response1>";
            out.println(xml);
            System.out.println(xml);

        } else if (cmd.equalsIgnoreCase("Delete")) {
            xml = "<response1><command>Delete</command>";
            try {
                ps =
  con.prepareStatement("delete from HRM_LEAVE_TYPES where LEAVE_TYPE_ID=?");
                ps.setInt(1, ltype1);
                ps.executeUpdate();
                xml = xml + "<flag1>Success</flag1>";
                xml = xml + "<code>" + ltype1 + "</code>";
                con.commit();
            } catch (SQLException e) {
                xml = xml + "<flag1>failure</flag1>";
                System.out.println("err in del");
            }
            xml = xml + "</response1>";
            out.println(xml);
            System.out.println(xml);
        } else {
            xml = "<response1><command>Update</command>";
            try {
                ps =
  con.prepareStatement("Update HRM_LEAVE_TYPES set LEAVE_TYPE_ID=?,LEAVE_TYPE_DESC=?,LEAVE_TYPE_DESC_SHORT=?,REMARKS=? where LEAVE_TYPE_ID=?");
                ps.setInt(1, ltype1);
                ps.setString(2, typedesc);
                ps.setString(3, shortdesc);
                ps.setString(4, rm);
                ps.setInt(5, ltype1);
                int r = ps.executeUpdate();
                if (r >= 1)
                    System.out.println("Updated");
                else
                    System.out.println("not update");

                xml = xml + "<flag1>Success</flag1>";
                con.commit();
            } catch (SQLException e) {
                xml = xml + "<flag1>failure</flag1>";
                System.out.println("err in update");
            }
            xml = xml + "</response1>";
            out.println(xml);
            System.out.println(xml);

        }
        out.println(" <p> The servlet has received a GET. This is the reply.</p> ");
        out.println("</body></html>");
        out.close();
    }
}
