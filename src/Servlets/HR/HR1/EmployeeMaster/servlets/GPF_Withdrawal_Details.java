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


public class GPF_Withdrawal_Details extends HttpServlet {
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

        session = request.getSession(false);
        String updatedby = (String)session.getAttribute("UserId");
        long l = System.currentTimeMillis();
        java.sql.Timestamp ts = new java.sql.Timestamp(l);


        if (command.equalsIgnoreCase("Add")) {

            int office_id =
                Integer.parseInt(request.getParameter("office_id"));
            int Acc_unit_id;
            Acc_unit_id =
                    Integer.parseInt(request.getParameter("Acc_unit_id"));
            int emp_id = Integer.parseInt(request.getParameter("emp_id"));
            int ac_month = Integer.parseInt(request.getParameter("ac_month"));
            int ac_year = Integer.parseInt(request.getParameter("ac_year"));
            int rel_month =
                Integer.parseInt(request.getParameter("rel_month"));
            int rel_year = Integer.parseInt(request.getParameter("rel_year"));
            String type_withdraw = request.getParameter("type_withdraw");
            Double amount = Double.parseDouble(request.getParameter("amount"));
            String date_trans = request.getParameter("date_trans");
            String remarks;
            remarks = request.getParameter("remarks");
            String with_desc = request.getParameter("with_desc");

            xml = "<response><command>Add</command>";
            try {
                ps =
  con.prepareStatement("insert into HRM_GPF_Withdrawal(office_id,Acc_unit_id,employee_id,ac_month,ac_year,rel_month,rel_year,WITHDRAW_type,DATE_OF_PAYMENT,remarks,WITHDRWAL_AMOUNT,updated_by,updated_date) values(?,?,?,?,?,?,?,?,to_date(?,'dd/mm/yyyy'),?,to_char(?,'999999999.99'),?,?)");
                ps.setInt(1, office_id);
                ps.setInt(2, Acc_unit_id);
                ps.setInt(3, emp_id);
                ps.setInt(4, ac_month);
                ps.setInt(5, ac_year);
                ps.setInt(6, rel_month);
                ps.setInt(7, rel_year);
                ps.setString(8, type_withdraw);
                ps.setString(9, date_trans);
                ps.setString(10, remarks);
                ps.setDouble(11, amount);


                ps.setString(12, updatedby);
                ps.setTimestamp(13, ts);
                ps.executeUpdate();
                xml = xml + "<flag>success</flag>";
                xml = xml + "<emp_id>" + emp_id + "</emp_id>";
                xml = xml + "<ac_month>" + ac_month + "</ac_month>";
                xml = xml + "<ac_year>" + ac_year + "</ac_year>";
                xml = xml + "<rel_month>" + rel_month + "</rel_month>";
                xml = xml + "<rel_year>" + rel_year + "</rel_year>";
                xml = xml + "<type_trans>" + type_withdraw + "</type_trans>";
                xml = xml + "<amount>" + amount + "</amount>";
                xml = xml + "<date_trans>" + date_trans + "</date_trans>";
                xml = xml + "<remarks>" + remarks + "</remarks>";
                xml = xml + "<with_desc>" + with_desc + "</with_desc>";
            } catch (SQLException e) {
                xml = xml + "<flag>failure</flag>";
                e.printStackTrace();
            }

        } else if (command.equalsIgnoreCase("Update")) {
            int office_id =
                Integer.parseInt(request.getParameter("office_id"));
            int Acc_unit_id;
            Acc_unit_id =
                    Integer.parseInt(request.getParameter("Acc_unit_id"));
            int emp_id = Integer.parseInt(request.getParameter("emp_id"));
            int ac_month = Integer.parseInt(request.getParameter("ac_month"));
            int ac_year = Integer.parseInt(request.getParameter("ac_year"));
            int rel_month =
                Integer.parseInt(request.getParameter("rel_month"));
            System.out.println("rel_month" + rel_month);
            int rel_year = Integer.parseInt(request.getParameter("rel_year"));
            String type_withdraw = request.getParameter("type_withdraw");
            System.out.println("type_withdraw" + type_withdraw);
            // String type_trans=request.getParameter("type_trans");
            Double amount = Double.parseDouble(request.getParameter("amount"));
            String date_trans = request.getParameter("date_trans");
            String with_desc = request.getParameter("with_desc");
            System.out.println("with_desc" + with_desc);
            String remarks;
            remarks = request.getParameter("remarks");
            System.out.println("update");
            xml = "<response><command>Update</command>";
            try {
                ps =
  con.prepareStatement("update HRM_GPF_Withdrawal set acc_unit_id=?,rel_month=?,rel_year=?,WITHDRAW_type=?,DATE_OF_PAYMENT=to_date(?,'dd/mm/yyyy'),WITHDRWAL_AMOUNT=?,remarks=?,updated_by=?,updated_date=? where employee_id=? and ac_month=? and ac_year=?");


                ps.setInt(1, Acc_unit_id);
                ps.setInt(2, rel_month);
                ps.setInt(3, rel_year);
                ps.setString(4, type_withdraw);
                ps.setString(5, date_trans);
                ps.setDouble(6, amount);
                ps.setString(7, remarks);


                ps.setString(8, updatedby);
                ps.setTimestamp(9, ts);
                ps.setInt(10, emp_id);
                ps.setInt(11, ac_month);
                ps.setInt(12, ac_year);

                ps.executeUpdate();
                System.out.println(emp_id + "Successfully...");
                xml = xml + "<flag>success</flag>";
                xml = xml + "<emp_id>" + emp_id + "</emp_id>";
                xml = xml + "<ac_month>" + ac_month + "</ac_month>";
                xml = xml + "<ac_year>" + ac_year + "</ac_year>";
                xml = xml + "<rel_month>" + rel_month + "</rel_month>";
                xml = xml + "<rel_year>" + rel_year + "</rel_year>";
                xml =
 xml + "<type_withdraw>" + type_withdraw + "</type_withdraw>";
                xml = xml + "<amount>" + amount + "</amount>";
                xml = xml + "<date_trans>" + date_trans + "</date_trans>";
                xml = xml + "<remarks>" + remarks + "</remarks>";
                xml = xml + "<with_desc>" + with_desc + "</with_desc>";
            } catch (SQLException e) {
                xml = xml + "<flag>failure</flag>";
                e.printStackTrace();
            }

        } else if (command.equalsIgnoreCase("Delete")) {
            System.out.println("delete");
            int emp_id = Integer.parseInt(request.getParameter("emp_id"));
            int ac_month = Integer.parseInt(request.getParameter("ac_month"));
            int ac_year = Integer.parseInt(request.getParameter("ac_year"));


            xml = "<response><command>Delete</command>";
            try {
                ps =
  con.prepareStatement("delete from HRM_GPF_Withdrawal where employee_id=? and ac_month=? and ac_year=? ");
                ps.setInt(1, emp_id);
                ps.setInt(2, ac_month);
                ps.setInt(3, ac_year);

                ps.executeUpdate();

                ps =
  con.prepareStatement("select * from \n" + "(\n" + "select EMPLOYEE_ID,AC_MONTH,AC_YEAR,trim(WITHDRAW_type) as WITHDRAW_type,WITHDRWAL_AMOUNT,\n" +
                       "to_char(DATE_OF_PAYMENT,'dd/mm/yyyy') as date_of_payment,REMARKS,REL_MONTH,REL_YEAR \n" +
                       "from hrm_gpf_withdrawal where employee_id=?\n" +
                       ")a\n" + "left outer join\n" + "(\n" +
                       "select  trim(WITHDRAW_TYPE) as type_with,WITHDRAW_TYPE_DESC from HRM_GPF_WITHDRAWAL_TYPE \n" +
                       ")b\n" + "on a.WITHDRAW_TYPE=b.type_with");
                ps.setInt(1, emp_id);
                rs = ps.executeQuery();
                xml = xml + "<flag>success</flag>";

                while (rs.next()) {
                    xml =
 xml + "<emp_id>" + rs.getInt("EMPLOYEE_ID") + "</emp_id>";
                    xml =
 xml + "<ac_month>" + rs.getInt("AC_MONTH") + "</ac_month>";
                    xml =
 xml + "<ac_year>" + rs.getInt("AC_YEAR") + "</ac_year>";
                    xml =
 xml + "<type_withdraw>" + rs.getString("WITHDRAW_TYPE") + "</type_withdraw>";
                    xml =
 xml + "<amount>" + rs.getDouble("WITHDRWAL_AMOUNT") + "</amount>";
                    xml =
 xml + "<date_trans>" + rs.getString("DATE_OF_PAYMENT") + "</date_trans>";
                    xml =
 xml + "<rel_month>" + rs.getInt("REL_MONTH") + "</rel_month>";
                    xml =
 xml + "<rel_year>" + rs.getInt("REL_YEAR") + "</rel_year>";

                    xml =
 xml + "<remarks>" + rs.getString("REMARKS") + "</remarks>";
                    xml =
 xml + "<with_desc>" + rs.getString("WITHDRAW_TYPE_DESC") + "</with_desc>";
                }


            } catch (SQLException e) {
                xml = xml + "<flag>failure</flag>";
                e.printStackTrace();
            }
        } else if (command.equalsIgnoreCase("loademp")) {
            int emp_id = Integer.parseInt(request.getParameter("emp_id"));
            System.out.println("hi" + emp_id);
            xml = "<response><command>emp</command>";
            try {
                System.out.println("try");
                ps =
  con.prepareStatement("SELECT EMPLOYEE_ID FROM HRM_MST_EMPLOYEES WHERE EMPLOYEE_ID=?");
                ps.setInt(1, emp_id);
                rs = ps.executeQuery();
                System.out.println("hi123");
                if (!rs.next()) {
                    System.out.println("if");
                    xml = xml + "<flag>failure</flag>";
                } else {
                    System.out.println("else");
                    ps =
  con.prepareStatement("SELECT e.EMPLOYEE_NAME,to_char(e.DATE_OF_BIRTH,'dd/mm/yyyy'),e.GPF_NO,f.DESIGNATION FROM HRM_MST_EMPLOYEES e,HRM_EMP_CURRENT_POSTING d,HRM_MST_DESIGNATIONS f WHERE e.EMPLOYEE_ID=d.EMPLOYEE_ID AND d.DESIGNATION_ID=f.DESIGNATION_ID AND e.EMPLOYEE_ID=?");
                    ps.setInt(1, emp_id);
                    rs = ps.executeQuery();
                    if (rs.next()) {
                        System.out.println("if rs");
                        xml =
 xml + "<emp_name>" + rs.getString(1) + "</emp_name>";
                        System.out.println("rs.getString(1)" +
                                           rs.getString(1));
                        xml =
 xml + "<date_of_birth>" + rs.getString(2) + "</date_of_birth>";
                        xml = xml + "<gpf_no>" + rs.getInt(3) + "</gpf_no>";
                        xml =
 xml + "<designation>" + rs.getString(4) + "</designation>";
                        System.out.println("rs.getString(4)" +
                                           rs.getString(4));
                    }

                    /*ps=con.prepareStatement("select designation from hrm_mst_designations where designation_id=?");
                        ps.setInt(1,design);
                        rs=ps.executeQuery();
                        if(rs.next()) {


                            xml=xml+"<designation>"+rs.getInt(1)+" </designation>";
                        }
                        */
                    ps =
  con.prepareStatement("select * from \n" + "(\n" + "select EMPLOYEE_ID,AC_MONTH,AC_YEAR,trim(WITHDRAW_type) as WITHDRAW_type,WITHDRWAL_AMOUNT,\n" +
                       "to_char(DATE_OF_PAYMENT,'dd/mm/yyyy') as date_of_payment,REMARKS,REL_MONTH,REL_YEAR \n" +
                       "from hrm_gpf_withdrawal where employee_id=?\n" +
                       ")a\n" + "left outer join\n" + "(\n" +
                       "select  trim(WITHDRAW_TYPE) as type_with,WITHDRAW_TYPE_DESC from HRM_GPF_WITHDRAWAL_TYPE \n" +
                       ")b\n" + "on a.WITHDRAW_TYPE=b.type_with");
                    ps.setInt(1, emp_id);
                    rs = ps.executeQuery();
                    xml = xml + "<flag>success</flag>";
                    System.out.println("sucess");
                    while (rs.next()) {
                        System.out.println("while begins");
                        xml =
 xml + "<emp_id>" + rs.getInt("EMPLOYEE_ID") + "</emp_id>";
                        xml =
 xml + "<ac_month>" + rs.getInt("AC_MONTH") + "</ac_month>";
                        xml =
 xml + "<ac_year>" + rs.getInt("AC_YEAR") + "</ac_year>";
                        xml =
 xml + "<type_withdraw>" + rs.getString("WITHDRAW_TYPE") + "</type_withdraw>";
                        xml =
 xml + "<amount>" + rs.getDouble("WITHDRWAL_AMOUNT") + "</amount>";
                        xml =
 xml + "<date_trans>" + rs.getString("DATE_OF_PAYMENT") + "</date_trans>";
                        xml =
 xml + "<rel_month>" + rs.getInt("REL_MONTH") + "</rel_month>";
                        xml =
 xml + "<rel_year>" + rs.getInt("REL_YEAR") + "</rel_year>";

                        xml =
 xml + "<remarks>" + rs.getString("REMARKS") + "</remarks>";
                        xml =
 xml + "<with_desc>" + rs.getString("WITHDRAW_TYPE_DESC") + "</with_desc>";
                        System.out.println("while end");
                    }
                    System.out.println("loop end");
                }
            } catch (SQLException e) {
                xml = xml + "<flag>failure</flag>";
                e.printStackTrace();
            }


        }

        xml = xml + "</response>";


        System.out.println(xml);

        out.println(xml);
        out.close();
    }

}


