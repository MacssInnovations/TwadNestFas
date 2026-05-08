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

public class GPF_Withdrawl_Type extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";
    private static final String DOC_TYPE = null;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

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

            xml = "<response><command>get</command>";
            try {

                ps =
  con.prepareStatement("select  trim(WITHDRAW_TYPE) as type_with,WITHDRAW_TYPE_DESC from HRM_GPF_WITHDRAWAL_TYPE");
                rs = ps.executeQuery();

                xml = xml + "<flag>success</flag>";

                while (rs.next()) {
                    xml =
 xml + "<w_id>" + rs.getString("type_with") + "</w_id>";
                    xml =
 xml + "<w_desc>" + rs.getString("WITHDRAW_TYPE_DESC") + "</w_desc>";

                }

            }


            catch (SQLException e) {
                xml = xml + "<flag>failure</flag>";
                e.printStackTrace();
            }
            xml = xml + "</response>";

        } else if (command.equalsIgnoreCase("Add")) {


            String w_id, w_desc;
            w_id = request.getParameter("w_id");
            w_desc = request.getParameter("w_desc");

            xml = "<response><command>Add</command>";
            try {
                System.out.println("this is add" + w_id + " == " + w_desc);
                ps =
  con.prepareStatement("select  trim(WITHDRAW_TYPE) as type_with,WITHDRAW_TYPE_DESC from HRM_GPF_WITHDRAWAL_TYPE");
                rs = ps.executeQuery();
                int flag = 0;
                while (rs.next()) {
                    System.out.println("this is add" +
                                       rs.getString("type_with") + " == " +
                                       w_id);
                    if (rs.getString("type_with").equalsIgnoreCase(w_id)) {
                        xml = xml + "<flag>same</flag>";
                        flag = 1;
                    }
                }
                System.out.println("flag" + flag);
                if (flag == 0) {
                    ps =
  con.prepareStatement("insert into HRM_GPF_WITHDRAWAL_TYPE(WITHDRAW_TYPE,WITHDRAW_TYPE_DESC) values(?,?)");
                    ps.setString(1, w_id);
                    ps.setString(2, w_desc);
                    ps.executeUpdate();
                    xml = xml + "<flag>success</flag>";
                    xml = xml + "<w_id>" + w_id + "</w_id>";
                    xml = xml + "<w_desc>" + w_desc + "</w_desc>";
                }

            } catch (SQLException e) {
                xml = xml + "<flag>failure</flag>";
                e.printStackTrace();
            }
            xml = xml + "</response>";
        } else if (command.equalsIgnoreCase("Update")) {
            System.out.println("update");
            String w_id, w_desc;
            w_id = request.getParameter("w_id");
            w_desc = request.getParameter("w_desc");

            xml = "<response><command>Update</command>";
            try {
                ps =
  con.prepareStatement("update HRM_GPF_WITHDRAWAL_TYPE set WITHDRAW_TYPE_DESC=? where WITHDRAW_TYPE=?");
                ps.setString(1, w_desc);
                ps.setString(2, w_id);
                ps.executeUpdate();
                xml = xml + "<flag>success</flag>";
                xml = xml + "<w_id>" + w_id + "</w_id>";
                xml = xml + "<w_desc>" + w_desc + "</w_desc>";
            } catch (SQLException e) {
                xml = xml + "<flag>failure</flag>";
                e.printStackTrace();
            }
            xml = xml + "</response>";
        } else if (command.equalsIgnoreCase("Delete")) {
            System.out.println("delete");
            String w_id, w_desc;
            w_id = request.getParameter("w_id");


            xml = "<response><command>Delete</command>";
            try {
                ps =
  con.prepareStatement("delete from HRM_GPF_WITHDRAWAL_TYPE where WITHDRAW_TYPE=?");
                ps.setString(1, w_id);

                ps.executeUpdate();

                ps =
  con.prepareStatement("select WITHDRAW_TYPE,WITHDRAW_TYPE_DESC from HRM_GPF_WITHDRAWAL_TYPE");
                rs = ps.executeQuery();

                xml = xml + "<flag>success</flag>";

                while (rs.next()) {
                    xml =
 xml + "<w_id>" + rs.getString("WITHDRAW_TYPE") + "</w_id>";
                    xml =
 xml + "<w_desc>" + rs.getString("WITHDRAW_TYPE_DESC") + "</w_desc>";

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
