package Servlets.HR.HR1.EmployeeMaster.Reports;

import Servlets.Security.classes.UserProfile;

import java.io.IOException;
import java.io.PrintWriter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.Timestamp;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class Role_Assigning_To_User extends HttpServlet {
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
        } catch (Exception ex) {
            String connectMsg =
                "Could not create the connection" + ex.getMessage() + " " +
                ex.getLocalizedMessage();
            System.out.println(connectMsg);
        }

        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");

            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }

        HttpSession session = request.getSession(false);
        UserProfile empProfile =
            (UserProfile)session.getAttribute("UserProfile");

        System.out.println("user id::" + empProfile.getEmployeeId());
        int empid = empProfile.getEmployeeId();
        int j = 0;
        String office_id, emp_id = null, xml = "<response>";
        int desig = 0;
        String command = "", userid, newpass, roleval = "";
        PreparedStatement ps, ps1;
        ResultSet rs, rs1, rs2;
        int cnt = 0, i, cnt1 = 0, k = 0;
        String strPassword = null;
        String[] employee = null;
        String[] role = null;

        command = request.getParameter("Command");
        System.out.println(command);
        if (command.equals("submit")) {
            desig = Integer.parseInt(request.getParameter("desigval"));
            office_id = request.getParameter("officeselected");


            System.out.println(desig + office_id);
            try {
                if (office_id.equals("alloffice")) {
                    ps =
  connection.prepareStatement("select employee_id ,office_id from hrm_emp_current_posting where designation_id=? and employee_status_id=? and office_id in(" +
                              office_id + ")");
                } else
                    ps =
  connection.prepareStatement("select employee_id ,office_id from hrm_emp_current_posting where designation_id=? and employee_status_id=? ");
                ps.setInt(1, desig);
                ps.setString(2, "WKG");
                // ps.setString(3,office_id);

                rs = ps.executeQuery();
                System.out.println("Employee id:");
                emp_id = "";
                while (rs.next()) {
                    emp_id = emp_id + rs.getInt("employee_id") + ",";
                    System.out.println(emp_id);
                    cnt++;
                }
                if (cnt > 0) {
                    xml += "<flag>Succuss</flag>";

                    employee = emp_id.split(",");
                    System.out.println(employee.length);
                    roleval = request.getParameter("roleval");
                    role = roleval.split(",");
                    System.out.println("role" + roleval);
                    try {
                        String updatedby =
                            (String)session.getAttribute("UserId");
                        long l = System.currentTimeMillis();
                        Timestamp ts = new Timestamp(l);
                        System.out.println("step1");
                        System.out.println(" updatedby ::: " + updatedby);
                        System.out.println(" ts ::: " + ts);
                        System.out.println(employee.length);
                        for (i = 0; i < employee.length; i++) {
                            System.out.println("hello");
                            System.out.println(employee[i]);

                            ps =
  connection.prepareStatement("select employee_id,user_id,user_Password,login_enabled from  sec_mst_users where employee_id=?");
                            ps.setInt(1, Integer.parseInt(employee[i]));

                            rs1 = ps.executeQuery();
                            System.out.println("Employee id:" + employee[i]);
                            while (rs1.next()) {

                                System.out.println("rs1 next");
                                System.out.println(rs1.getInt("login_enabled"));
                                if (rs1.getInt("login_enabled") == 0) {
                                    System.out.println("login");
                                    ps =
  connection.prepareStatement("update  sec_mst_users set login_enabled=? where employee_id=?");
                                    ps.setInt(1, 1);
                                    System.out.println("finish 1");
                                    ps.setInt(2,
                                              Integer.parseInt(employee[i]));
                                    ps.executeUpdate();
                                    System.out.println("finish 2");
                                }
                                ++cnt1;
                                System.out.println("updated successfully");
                            }
                            System.out.println("hello 2");
                            if (cnt1 != employee.length) {
                                userid = "twad" + employee[i];
                                newpass = "twad" + employee[i];
                                byte[] b = newpass.getBytes();

                                try {
                                    MessageDigest algorithm =
                                        MessageDigest.getInstance("MD5");
                                    algorithm.reset();
                                    algorithm.update(b);
                                    byte messageDigest[] = algorithm.digest();
                                    System.out.println("actual encrypt::" +
                                                       messageDigest);
                                    StringBuffer hexString =
                                        new StringBuffer();
                                    for (k = 0; k < messageDigest.length;
                                         k++) {
                                        hexString.append(Integer.toHexString(0xFF &
                                                                             messageDigest[k]));
                                    }

                                    strPassword = new String(hexString);
                                } catch (NoSuchAlgorithmException nsae) {
                                    System.out.println("Second MD5::" + nsae);
                                }
                                ps1 =
 connection.prepareStatement("select * from  sec_mst_users where employee_id=? ");
                                ps1.setInt(1, Integer.parseInt(employee[i]));
                                rs1 = ps1.executeQuery();
                                if (rs1.next())
                                    System.out.println("exist in sec_mst_users");
                                else {

                                    ps =
  connection.prepareStatement("insert into sec_mst_users (user_id,user_password,user_category_id,employee_id,login_enabled,updated_by_user_id,updated_date) values(?,?,?,?,?,?,?)");
                                    ps.setString(1, userid);
                                    ps.setString(2, strPassword);
                                    ps.setInt(3, 1);
                                    ps.setInt(5, 1);
                                    ps.setInt(4,
                                              Integer.parseInt(employee[i]));
                                    ps.setTimestamp(7, ts);
                                    ps.setString(6, updatedby);
                                    ps.executeUpdate();
                                    System.out.println("second check ok");
                                }

                            }

                        }

                        System.out.println("for end");


                        for (i = 0; i < employee.length; i++) {
                            for (j = 0; j < role.length; j++) {
                                System.out.println("check from list");
                                ps1 =
 connection.prepareStatement("  select * from  sec_mst_user_roles where employee_id=? and role_id=?");
                                ps1.setInt(1, Integer.parseInt(employee[i]));
                                ps1.setInt(2, Integer.parseInt(role[j]));
                                rs1 = ps1.executeQuery();
                                if (rs1.next()) {
                                    System.out.println("existing---->");
                                } else {
                                    System.out.println("insert starts");
                                    System.out.println(Integer.parseInt(employee[i]));
                                    System.out.println(Integer.parseInt(role[j]));

                                    ps =
  connection.prepareStatement("  INSERT INTO  sec_mst_user_roles(employee_id,role_id, updated_by_user_id, updated_date )VALUES (?,?,?,? )");
                                    ps.setInt(1,
                                              Integer.parseInt(employee[i]));
                                    ps.setInt(2, Integer.parseInt(role[j]));
                                    ps.setString(3, "System");
                                    ps.setTimestamp(4, ts);
                                    ps.executeUpdate();
                                }
                            }
                        }
                        xml = xml + "<iflag>success</iflag>";


                    }

                    catch (Exception e) {

                        System.out.println("catch..exception user enabling" +
                                           e);
                        xml = xml + "<iflag>failure</iflag>";
                    }

                }
            } catch (Exception e) {
                System.out.println("exception in employee fetching " + e);
                xml += "<flag>Failure</flag>";
            }

        }


        xml += "</response>";
        out.println(xml);
        System.out.println("xml :" + xml);
    }
}
