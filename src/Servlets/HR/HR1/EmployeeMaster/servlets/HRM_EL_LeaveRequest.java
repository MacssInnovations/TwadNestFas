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

public class HRM_EL_LeaveRequest extends HttpServlet {
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
        String name = "";
        int did = 0, balance = 0, count = 0, c = 0, tid = 0;
        String designation = "";
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {

            ResourceBundle rsb =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";

            String strDriver = rsb.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rsb.getString("Config.DSN");
            String strhostname = rsb.getString("Config.HOST_NAME");
            String strportno = rsb.getString("Config.PORT_NUMBER");
            String strsid = rsb.getString("Config.SID");
            String strdbusername = rsb.getString("Config.USER_NAME");
            String strdbpassword = rsb.getString("Config.PASSWORD");

            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection


            Class.forName(strDriver.trim());
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
        } catch (Exception e) {
            System.out.println("Exception in connection..." + e);
        }
        String cmd = request.getParameter("command");
        System.out.println(cmd);
        int eid = Integer.parseInt(request.getParameter("cmdid"));
        if (cmd.equalsIgnoreCase("getValue")) {
            System.out.println(eid);
            xml = "<response1><command>getValue</command>";
            try {
                ps =
  con.prepareStatement("select * from(select EMPLOYEE_ID,EMPLOYEE_NAME from HRM_MST_EMPLOYEES)a inner join(select DESIGNATION_ID,EMPLOYEE_ID from HRM_EMP_CURRENT_POSTING)b on a.EMPLOYEE_ID=b.EMPLOYEE_ID left outer join(select DESIGNATION_ID,DESIGNATION from HRM_MST_DESIGNATIONS)c on b.DESIGNATION_ID=c.DESIGNATION_ID where a.EMPLOYEE_ID=?");
                ps.setInt(1, eid);
                rs = ps.executeQuery();
                if (rs.next()) {
                    name = rs.getString("EMPLOYEE_NAME");
                    did = Integer.parseInt(rs.getString("DESIGNATION_ID"));
                    designation = rs.getString("DESIGNATION");
                    System.out.println(name);
                    xml =
 xml + "<name>" + name + "</name><did>" + did + "</did>";
                    xml =
 xml + "<designation>" + designation + "</designation>";
                    xml = xml + "<flag1>Success</flag1>";
                } else {
                    xml = xml + "<flag1>error in try</flag1>";
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
                System.out.println("err in load");
            }

        } else {
            String ltype = request.getParameter("cmdtype");
            System.out.println(ltype);
            xml = "<response1><command>balanceValue</command>";
            try {
                System.out.println("try1");
                ps =
  con.prepareStatement("select LEAVE_TYPE_ID from HRM_LEAVE_TYPES where LEAVE_TYPE_DESC_SHORT=?");
                ps.setString(1, ltype);
                rs = ps.executeQuery();
                if (rs.next()) {
                    tid = Integer.parseInt(rs.getString("LEAVE_TYPE_ID"));
                    System.out.println(tid);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            try {
                ps =
  con.prepareStatement("select CURRENT_BALANCE_NO_OF_DAYS from HRM_LEAVE_BAL_CURRENT where LEAVE_TYPE_ID=? and EMPLOYEE_ID=?");
                ps.setInt(1, tid);
                ps.setInt(2, eid);
                rs = ps.executeQuery();
                if (rs.next()) {
                    balance =
                            Integer.parseInt(rs.getString("CURRENT_BALANCE_NO_OF_DAYS"));
                    System.out.println(balance);
                    xml = xml + "<flag1>Success</flag1>";
                }
                xml = xml + "<balance>" + balance + "</balance>";
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        xml = xml + "</response1>";
        out.println(xml);
        System.out.println(xml);
        System.out.println("end of the java");
        out.close();

    }
}
