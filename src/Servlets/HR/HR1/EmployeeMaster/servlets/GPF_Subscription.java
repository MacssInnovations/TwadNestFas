package Servlets.HR.HR1.EmployeeMaster.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class GPF_Subscription extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";
    private static final String DOC_TYPE = null;

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

        session = request.getSession(false);
        String updatedby = (String)session.getAttribute("UserId");
        long l = System.currentTimeMillis();
        java.sql.Timestamp ts = new java.sql.Timestamp(l);
        System.out.println("got");
        if (command.equalsIgnoreCase("Get")) {
            int emp_id;
            int design = 0;
            emp_id = Integer.parseInt(request.getParameter("emp_id"));
            xml = "<response><command>get</command>";
            try {

                ps =
  con.prepareStatement("SELECT EMPLOYEE_ID FROM HRM_MST_EMPLOYEES WHERE EMPLOYEE_ID=?");
                ps.setInt(1, emp_id);
                rs = ps.executeQuery();

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

                    /*ps=con.prepareStatement("select designation from hrm_mst_designations where designation_id=?");
                        ps.setInt(1,design);
                        rs=ps.executeQuery();
                        if(rs.next()) {


                            xml=xml+"<designation>"+rs.getInt(1)+" </designation>";
                        }
                        */
                    ps =
  con.prepareStatement("select employee_id,ac_month,rel_month,type_trans,sub_amount,ac_year,rel_year,remarks,ref_amount,ref_inst_no,ref_tot_inst,arr_amount,arr_inst_no,arr_tot_inst from HRM_GPF_SUBSCRIPTION where employee_id=?");
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
 xml + "<type_trans>" + rs.getString(4) + "</type_trans>";
                        xml = xml + "<amount>" + rs.getInt(5) + "</amount>";
                        xml = xml + "<ac_year>" + rs.getInt(6) + "</ac_year>";
                        xml =
 xml + "<rel_year>" + rs.getInt(7) + "</rel_year>";
                        xml =
 xml + "<remarks>" + rs.getString(8) + "</remarks>";
                        xml =
 xml + "<ref_amount>" + rs.getInt(9) + "</ref_amount>";
                        xml = xml + "<ref_no>" + rs.getInt(10) + "</ref_no>";
                        xml =
 xml + "<ref_total>" + rs.getInt(11) + "</ref_total>";
                        xml =
 xml + "<rec_amount>" + rs.getInt(12) + "</rec_amount>";
                        xml = xml + "<rec_no>" + rs.getInt(13) + "</rec_no>";
                        xml =
 xml + "<rec_total>" + rs.getInt(14) + "</rec_total>";
                    }

                }
            }

            catch (SQLException e) {
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
            String type_trans = request.getParameter("type_trans");
            int amount = Integer.parseInt(request.getParameter("amount"));
            String remarks;
            remarks = request.getParameter("remarks");
            int ref_amount =
                Integer.parseInt(request.getParameter("ref_amount"));
            int ref_no = Integer.parseInt(request.getParameter("ref_no"));
            int ref_total =
                Integer.parseInt(request.getParameter("ref_total"));
            int rec_amount =
                Integer.parseInt(request.getParameter("rec_amount"));
            int rec_no = Integer.parseInt(request.getParameter("rec_no"));
            int rec_total =
                Integer.parseInt(request.getParameter("rec_total"));


            xml = "<response><command>Add</command>";
            try {
                System.out.println("this is add");
                ps =
  con.prepareStatement("insert into HRM_GPF_SUBSCRIPTION(office_id,accounting_unit_id,employee_id,ac_month,ac_year,rel_month,rel_year,type_trans,sub_amount,remarks,ref_amount,ref_inst_no,ref_tot_inst,arr_amount,arr_inst_no,arr_tot_inst,updated_by,updated_date) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                ps.setInt(1, office_id);
                ps.setString(2, division_id);
                ps.setInt(3, emp_id);
                ps.setInt(4, ac_month);
                ps.setInt(5, ac_year);
                ps.setInt(6, rel_month);
                ps.setInt(7, rel_year);
                ps.setString(8, type_trans);
                ps.setInt(9, amount);
                ps.setString(10, remarks);
                ps.setInt(11, ref_amount);
                ps.setInt(12, ref_no);
                ps.setInt(13, ref_total);
                ps.setInt(14, rec_amount);
                ps.setInt(15, rec_no);
                ps.setInt(16, rec_total);
                ps.setString(17, updatedby);
                ps.setTimestamp(18, ts);
                ps.executeUpdate();
                xml = xml + "<flag>success</flag>";
                xml = xml + "<emp_id>" + emp_id + "</emp_id>";
                xml = xml + "<ac_month>" + ac_month + "</ac_month>";
                xml = xml + "<ac_year>" + ac_year + "</ac_year>";
                xml = xml + "<rel_month>" + rel_month + "</rel_month>";
                xml = xml + "<rel_year>" + rel_year + "</rel_year>";
                xml = xml + "<type_trans>" + type_trans + "</type_trans>";
                xml = xml + "<amount>" + amount + "</amount>";
                xml = xml + "<remarks>" + remarks + "</remarks>";
                xml = xml + "<ref_amount>" + ref_amount + "</ref_amount>";
                xml = xml + "<ref_no>" + ref_no + "</ref_no>";
                xml = xml + "<ref_total>" + ref_total + "</ref_total>";
                xml = xml + "<rec_amount>" + rec_amount + "</rec_amount>";
                xml = xml + "<rec_no>" + rec_no + "</rec_no>";
                xml = xml + "<rec_total>" + rec_total + "</rec_total>";


            } catch (SQLException e) {
                xml = xml + "<flag>failure</flag>";
                e.printStackTrace();
            }
            xml = xml + "</response>";
        } else if (command.equalsIgnoreCase("Update")) {
            System.out.println("update");
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
            String type_trans = request.getParameter("type_trans");
            int amount = Integer.parseInt(request.getParameter("amount"));
            String remarks;
            remarks = request.getParameter("remarks");
            int ref_amount =
                Integer.parseInt(request.getParameter("ref_amount"));
            int ref_no = Integer.parseInt(request.getParameter("ref_no"));
            int ref_total =
                Integer.parseInt(request.getParameter("ref_total"));
            int rec_amount =
                Integer.parseInt(request.getParameter("rec_amount"));
            int rec_no = Integer.parseInt(request.getParameter("rec_no"));
            int rec_total =
                Integer.parseInt(request.getParameter("rec_total"));
            System.out.println("update1");

            xml = "<response><command>Update</command>";
            try {
                ps =
  con.prepareStatement("update HRM_GPF_SUBSCRIPTION set rel_month=?,rel_year=?,type_trans=?,sub_amount=?,remarks=?,ref_amount=?,ref_inst_no=?,ref_tot_inst=?,arr_amount=?,arr_inst_no=?,arr_tot_inst=?,updated_by=?,updated_date=? where employee_id=? and ac_month=? and ac_year=? and office_id=?");
                ps.setInt(1, rel_month);
                ps.setInt(2, rel_year);
                ps.setString(3, type_trans);
                ps.setDouble(4, amount);
                ps.setString(5, remarks);
                ps.setInt(6, ref_amount);
                ps.setInt(7, ref_no);
                ps.setInt(8, ref_total);
                ps.setInt(9, rec_amount);
                ps.setInt(10, rec_no);
                ps.setInt(11, rec_total);
                ps.setString(12, updatedby);
                ps.setTimestamp(13, ts);
                ps.setInt(14, emp_id);
                ps.setInt(15, ac_month);
                ps.setInt(16, ac_year);
                ps.setInt(17, office_id);
                ps.executeUpdate();

                xml = xml + "<flag>success</flag>";
                xml = xml + "<emp_id>" + emp_id + "</emp_id>";
                xml = xml + "<ac_month>" + ac_month + "</ac_month>";
                xml = xml + "<ac_year>" + ac_year + "</ac_year>";
                xml = xml + "<rel_month>" + rel_month + "</rel_month>";
                xml = xml + "<rel_year>" + rel_year + "</rel_year>";
                xml = xml + "<type_trans>" + type_trans + "</type_trans>";
                xml = xml + "<amount>" + amount + "</amount>";
                xml = xml + "<remarks>" + remarks + "</remarks>";
                xml = xml + "<ref_amount>" + ref_amount + "</ref_amount>";
                xml = xml + "<ref_no>" + ref_no + "</ref_no>";
                xml = xml + "<ref_total>" + ref_total + "</ref_total>";
                xml = xml + "<rec_amount>" + rec_amount + "</rec_amount>";
                xml = xml + "<rec_no>" + rec_no + "</rec_no>";
                xml = xml + "<rec_total>" + rec_total + "</rec_total>";
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
            int office_id =
                Integer.parseInt(request.getParameter("office_id"));


            xml = "<response><command>Delete</command>";
            try {
                ps =
  con.prepareStatement("delete from HRM_GPF_SUBSCRIPTION where employee_id=? and ac_month=? and ac_year=? and office_id=?");
                ps.setInt(1, emp_id);
                ps.setInt(2, ac_month);
                ps.setInt(3, ac_year);
                ps.setInt(4, office_id);

                ps.executeUpdate();

                ps =
  con.prepareStatement("select employee_id,ac_month,rel_month,type_trans,sub_amount,ac_year,rel_year,remarks,ref_amount,ref_inst_no,ref_tot_inst,arr_amount,arr_inst_no,arr_tot_inst from HRM_GPF_SUBSCRIPTION where employee_id=?");
                ps.setInt(1, emp_id);
                rs = ps.executeQuery();
                xml = xml + "<flag>success</flag>";

                while (rs.next()) {
                    xml = xml + "<emp_id>" + rs.getInt(1) + "</emp_id>";
                    xml = xml + "<ac_month>" + rs.getInt(2) + "</ac_month>";
                    xml = xml + "<rel_month>" + rs.getInt(3) + "</rel_month>";
                    xml =
 xml + "<type_trans>" + rs.getString(4) + "</type_trans>";
                    xml = xml + "<amount>" + rs.getInt(5) + "</amount>";
                    xml = xml + "<ac_year>" + rs.getInt(6) + "</ac_year>";
                    xml = xml + "<rel_year>" + rs.getInt(7) + "</rel_year>";
                    xml = xml + "<remarks>" + rs.getString(8) + "</remarks>";
                    xml =
 xml + "<ref_amount>" + rs.getInt(9) + "</ref_amount>";
                    xml = xml + "<ref_no>" + rs.getInt(10) + "</ref_no>";
                    xml = xml + "<ref_total>" + rs.getInt(11) + "</ref_total>";
                    xml =
 xml + "<rec_amount>" + rs.getInt(12) + "</rec_amount>";
                    xml = xml + "<rec_no>" + rs.getInt(13) + "</rec_no>";
                    xml = xml + "<rec_total>" + rs.getInt(14) + "</rec_total>";
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
