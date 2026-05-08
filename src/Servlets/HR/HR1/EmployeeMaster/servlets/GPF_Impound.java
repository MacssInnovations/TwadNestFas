package Servlets.HR.HR1.EmployeeMaster.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;

import java.text.*;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class GPF_Impound extends HttpServlet {
    private static final String CONTENT_TYPE =
        "application/xml; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    /**
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {

        Connection con = null;
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
            System.out.println("Exception in openeing connection :" + e);
            //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

        }
        ResultSet rs = null, rs1 = null, rs2 = null;
        CallableStatement cs = null;
        PreparedStatement ps = null, ps1 = null, ps2 = null;
        String xml = "";


        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        response.setHeader("Cache-Control", "no-cache");
        HttpSession session = request.getSession(false);
        try {
            if (session == null) {
                xml =
 "<response><command>sessionout</command><flag>sessionout</flag></response>";
                out.println(xml);
                System.out.println(xml);
                out.close();
                return;

            }
            //System.out.println(session);

        } catch (Exception e) {
            //System.out.println("Redirect Error :"+e);
        }
        System.out.println("java");
        String command;
        command = request.getParameter("command");
        DecimalFormat df = new DecimalFormat("#.00");
        session = request.getSession(false);
        String updatedby = (String)session.getAttribute("UserId");
        long l = System.currentTimeMillis();
        java.sql.Timestamp ts = new java.sql.Timestamp(l);

        if (command.equalsIgnoreCase("Get")) {
            xml = "<response><command>get</command>";
            try {
                System.out.println("try");
                int emp_id;
                emp_id = Integer.parseInt(request.getParameter("emp_id"));
                ps =
  con.prepareStatement("SELECT EMPLOYEE_ID FROM HRM_MST_EMPLOYEES WHERE EMPLOYEE_ID=?");
                ps.setInt(1, emp_id);
                rs = ps.executeQuery();
                System.out.println("hi123");
                if (!rs.next()) {
                    xml = xml + "<flag>failure</flag>";
                } else {

                    ps =
  con.prepareStatement("SELECT e.EMPLOYEE_NAME,to_char(e.DATE_OF_BIRTH,'dd/mm/yyyy'),e.GPF_NO,f.DESIGNATION FROM HRM_MST_EMPLOYEES e,HRM_EMP_CURRENT_POSTING d,HRM_MST_DESIGNATIONS f WHERE e.EMPLOYEE_ID=d.EMPLOYEE_ID AND d.DESIGNATION_ID=f.DESIGNATION_ID AND e.EMPLOYEE_ID=?");
                    ps.setInt(1, emp_id);
                    rs = ps.executeQuery();
                    if (rs.next()) {

                        xml =
 xml + "<emp_name>" + rs.getString(1) + " </emp_name>";
                        xml =
 xml + "<date_of_birth>" + rs.getString(2) + " </date_of_birth>";
                        xml = xml + "<gpf_no>" + rs.getInt(3) + " </gpf_no>";
                        xml =
 xml + "<designation>" + rs.getString(4) + " </designation>";
                    }

                    ps =
  con.prepareStatement("select employee_id,ac_month,rel_month,impound_type,type_trans,impound_amount,to_char(date_of_trans,'dd/mm/yyyy'),ac_year,rel_year,remarks from HRM_GPF_Impound_disb where employee_id=?");
                    ps.setInt(1, emp_id);
                    rs = ps.executeQuery();
                    xml = xml + "<flag>success</flag>";

                    while (rs.next()) {
                        xml = xml + "<emp_id>" + rs.getInt(1) + "</emp_id>";
                        xml =
 xml + "<ac_month>" + rs.getInt(2) + "</ac_month>";
                        xml =
 xml + "<rel_month>" + rs.getInt(3) + "</rel_month>";
                        xml =
 xml + "<impound_type>" + rs.getString(4) + "</impound_type>";
                        xml =
 xml + "<type_trans>" + rs.getString(5) + "</type_trans>";
                        xml = xml + "<amount>" + rs.getInt(6) + "</amount>";
                        xml =
 xml + "<date_trans>" + rs.getString(7) + "</date_trans>";
                        xml = xml + "<ac_year>" + rs.getInt(8) + "</ac_year>";
                        xml =
 xml + "<rel_year>" + rs.getInt(9) + "</rel_year>";
                        xml =
 xml + "<remarks>" + rs.getString(10) + "</remarks>";
                    }

                }
            } catch (SQLException e) {
                xml = xml + "<flag>failure</flag>";
                e.printStackTrace();
            }
            xml = xml + "</response>";
            // System.out.println(xml);
        } else if (command.equalsIgnoreCase("Add")) {

            int office_id =
                Integer.parseInt(request.getParameter("office_id"));
            String division_id;
            division_id = request.getParameter("division_id");
            int emp_id = Integer.parseInt(request.getParameter("emp_id"));
            int ac_month = Integer.parseInt(request.getParameter("ac_month"));
            int ac_year = Integer.parseInt(request.getParameter("ac_year"));
            int rel_month =
                Integer.parseInt(request.getParameter("rel_month"));
            int rel_year = Integer.parseInt(request.getParameter("rel_year"));
            String impound_type;
            impound_type = request.getParameter("impound_type");

            String type_trans = request.getParameter("type_trans");
            int amount = Integer.parseInt(request.getParameter("amount"));
            //Double d=new Double(df.format(amount));

            String date_trans = request.getParameter("date_trans");
            String remarks;
            remarks = request.getParameter("remarks");

            xml = "<response><command>Add</command>";
            try {
                ps =
  con.prepareStatement("insert into HRM_GPF_Impound_disb(office_id,accounting_unit_id,employee_id,ac_month,ac_year,rel_month,rel_year,type_trans,impound_amount,date_of_trans,remarks,impound_type,updated_by,updated_date) values(?,?,?,?,?,?,?,?,?,to_date(?,'dd/mm/yyyy'),?,?,?,?)");
                ps.setInt(1, office_id);
                ps.setString(2, division_id);
                ps.setInt(3, emp_id);
                ps.setInt(4, ac_month);
                ps.setInt(5, ac_year);
                ps.setInt(6, rel_month);
                ps.setInt(7, rel_year);
                ps.setString(8, type_trans);
                ps.setInt(9, amount);
                ps.setString(10, date_trans);
                ps.setString(11, remarks);
                ps.setString(12, impound_type);
                ps.setString(13, updatedby);
                ps.setTimestamp(14, ts);
                ps.executeUpdate();
                xml = xml + "<flag>success</flag>";
                xml = xml + "<emp_id>" + emp_id + "</emp_id>";
                xml = xml + "<ac_month>" + ac_month + "</ac_month>";
                xml = xml + "<ac_year>" + ac_year + "</ac_year>";
                xml = xml + "<rel_month>" + rel_month + "</rel_month>";
                xml = xml + "<rel_year>" + rel_year + "</rel_year>";
                xml = xml + "<type_trans>" + type_trans + "</type_trans>";
                xml =
 xml + "<impound_type>" + impound_type + "</impound_type>";
                xml = xml + "<amount>" + amount + "</amount>";
                xml = xml + "<date_trans>" + date_trans + "</date_trans>";
                xml = xml + "<remarks>" + remarks + "</remarks>";

            } catch (SQLException e) {
                xml = xml + "<flag>failure</flag>";
                e.printStackTrace();
            }
            xml = xml + "</response>";
        } else if (command.equalsIgnoreCase("Update")) {
            int office_id =
                Integer.parseInt(request.getParameter("office_id"));
            String division_id;
            division_id = request.getParameter("division_id");
            int emp_id = Integer.parseInt(request.getParameter("emp_id"));
            int ac_month = Integer.parseInt(request.getParameter("ac_month"));
            int ac_year = Integer.parseInt(request.getParameter("ac_year"));
            int rel_month =
                Integer.parseInt(request.getParameter("rel_month"));
            int rel_year = Integer.parseInt(request.getParameter("rel_year"));
            String impound_type;
            impound_type = request.getParameter("impound_type");

            String type_trans = request.getParameter("type_trans");
            int amount = Integer.parseInt(request.getParameter("amount"));
            String date_trans = request.getParameter("date_trans");
            String remarks;
            remarks = request.getParameter("remarks");
            System.out.println("update");
            xml = "<response><command>Update</command>";
            try {
                ps =
  con.prepareStatement("update HRM_GPF_Impound_disb set rel_month=?,rel_year=?,type_trans=?,impound_amount=?,date_of_trans=to_date(?,'dd/mm/yyyy'),remarks=?,updated_by=?,updated_date=? where employee_id=? and ac_month=? and ac_year=? and impound_type=?");

                ps.setInt(1, rel_month);
                ps.setInt(2, rel_year);
                ps.setString(3, type_trans);
                ps.setInt(4, amount);
                ps.setString(5, date_trans);
                ps.setString(6, remarks);
                ps.setString(7, updatedby);
                ps.setTimestamp(8, ts);
                ps.setInt(9, emp_id);
                ps.setInt(10, ac_month);
                ps.setInt(11, ac_year);
                ps.setString(12, impound_type);
                ps.executeUpdate();
                System.out.println(emp_id);
                xml = xml + "<flag>success</flag>";
                xml = xml + "<emp_id>" + emp_id + "</emp_id>";
                xml = xml + "<ac_month>" + ac_month + "</ac_month>";
                xml = xml + "<ac_year>" + ac_year + "</ac_year>";
                xml = xml + "<rel_month>" + rel_month + "</rel_month>";
                xml = xml + "<rel_year>" + rel_year + "</rel_year>";
                xml = xml + "<type_trans>" + type_trans + "</type_trans>";
                xml =
 xml + "<impound_type>" + impound_type + "</impound_type>";
                xml = xml + "<amount>" + amount + "</amount>";
                xml = xml + "<date_trans>" + date_trans + "</date_trans>";
                xml = xml + "<remarks>" + remarks + "</remarks>";
            } catch (SQLException e) {
                xml = xml + "<flag>failure</flag>";
                e.printStackTrace();
            }
            xml = xml + "</response>";
        } else if (command.equalsIgnoreCase("Delete")) {
            System.out.println("delete");
            int emp_id = Integer.parseInt(request.getParameter("emp_id"));
            int ac_month = Integer.parseInt(request.getParameter("ac_month"));
            int ac_year = Integer.parseInt(request.getParameter("ac_year"));
            String impound_type;
            impound_type = request.getParameter("impound_type");
            System.out.println(emp_id + "  " + ac_month + " " + ac_year + " " +
                               impound_type);

            xml = "<response><command>Delete</command>";
            try {
                ps =
  con.prepareStatement("delete from HRM_GPF_Impound_disb where employee_id=? and ac_month=? and ac_year=? and impound_type=?");
                ps.setInt(1, emp_id);
                ps.setInt(2, ac_month);
                ps.setInt(3, ac_year);
                ps.setString(4, impound_type);
                ps.executeUpdate();
                System.out.println("came");
                ps =
  con.prepareStatement("select employee_id,ac_month,rel_month,impound_type,type_trans,impound_amount,to_char(date_of_trans,'dd/mm/yyyy'),ac_year,rel_year,remarks from HRM_GPF_Impound_disb where employee_id=?");
                ps.setInt(1, emp_id);
                rs = ps.executeQuery();
                xml = xml + "<flag>success</flag>";

                while (rs.next()) {
                    xml = xml + "<emp_id>" + rs.getInt(1) + "</emp_id>";
                    xml = xml + "<ac_month>" + rs.getInt(2) + "</ac_month>";
                    xml = xml + "<rel_month>" + rs.getInt(3) + "</rel_month>";
                    xml =
 xml + "<impound_type>" + rs.getString(4) + "</impound_type>";
                    xml =
 xml + "<type_trans>" + rs.getString(5) + "</type_trans>";
                    xml = xml + "<amount>" + rs.getInt(6) + "</amount>";
                    xml =
 xml + "<date_trans>" + rs.getString(7) + "</date_trans>";
                    xml = xml + "<ac_year>" + rs.getInt(8) + "</ac_year>";
                    xml = xml + "<rel_year>" + rs.getInt(9) + "</rel_year>";
                    xml = xml + "<remarks>" + rs.getString(10) + "</remarks>";
                }

            } catch (SQLException e) {
                xml = xml + "<flag>failure</flag>";
                e.printStackTrace();
            }
            xml = xml + "</response>";
        } else if (command.equalsIgnoreCase("unit")) {
            String unit_name;
            System.out.println("unit");
            int office_id = 0;
            unit_name = request.getParameter("unit_name");
            xml = "<response><command>unit</command>";
            try {

                ps =
  con.prepareStatement("select accounting_unit_id from fas_mst_acct_units where accounting_unit_name=?");
                ps.setString(1, unit_name);
                rs = ps.executeQuery();
                xml = xml + "<flag>success</flag>";
                if (rs.next()) {
                    xml = xml + "<unit_id>" + rs.getInt(1) + "</unit_id>";

                }


            } catch (SQLException e) {
                xml = xml + "<flag>failure</flag>";
                e.printStackTrace();
            }
            xml = xml + "</response>";
        }
        System.out.println(xml);
        out.println(xml);
        out.close();


    }


}
