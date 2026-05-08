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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Grant_Closed_Office
 */
public class Revoke_Closed_Office extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String CONTENT_TYPE =
        "application/xml; charset=windows-1252";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Revoke_Closed_Office() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request,
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
        System.out.println("command" + command);
        if (command.equalsIgnoreCase("Get")) {
            xml = "<response><command>get</command>";
            try {
                System.out.println("try");


                ps =
  con.prepareStatement("select office_id,accounting_unit_id,user_id,to_char(date_allowed_upto,'dd/mm/yyyy'),enabled from HRM_MST_GRANT_CLOSED_OFFICE");

                rs = ps.executeQuery();
                xml = xml + "<flag>success</flag>";

                while (rs.next()) {
                    xml = xml + "<office_id>" + rs.getInt(1) + "</office_id>";
                    xml = xml + "<unit>" + rs.getInt(2) + "</unit>";
                    xml = xml + "<userid>" + rs.getString(3) + "</userid>";

                    xml = xml + "<date>" + rs.getString(4) + "</date>";

                    xml = xml + "<enabled>" + rs.getString(5) + "</enabled>";

                }


            } catch (SQLException e) {
                xml = xml + "<flag>failure</flag>";
                e.printStackTrace();
            }
            xml = xml + "</response>";
            // System.out.println(xml);
        } else if (command.equalsIgnoreCase("Add")) {

            int officeId = Integer.parseInt(request.getParameter("officeid"));
            int accountingUnit =
                Integer.parseInt(request.getParameter("unit").trim());
            String userId = request.getParameter("userid");
            String date = request.getParameter("date");

            xml = "<response><command>Add</command>";
            try {
                ps =
  con.prepareStatement("insert into HRM_MST_GRANT_CLOSED_OFFICE(office_id,accounting_unit_id,user_id,enabled,date_allowed_upto,updated_by,updated_date) values(?,?,?,?,to_date(?,'dd/mm/yyyy'),?,?)");
                ps.setInt(1, officeId);
                ps.setInt(2, accountingUnit);
                ps.setString(3, userId);
                ps.setString(4, "Y");
                ps.setString(5, date);
                ps.setString(6, updatedby);
                ps.setTimestamp(7, ts);
                ps.executeUpdate();

                ps =
  con.prepareStatement("select office_id,accounting_unit_id,user_id,to_char(date_allowed_upto,'dd/mm/yyyy'),authorized_state,enabled from HRM_MST_GRANT_CLOSED_OFFICE");

                rs = ps.executeQuery();
                xml = xml + "<flag>success</flag>";

                while (rs.next()) {
                    xml = xml + "<office_id>" + rs.getInt(1) + "</office_id>";
                    xml = xml + "<unit>" + rs.getInt(2) + "</unit>";
                    xml = xml + "<userid>" + rs.getString(3) + "</userid>";

                    xml = xml + "<date>" + rs.getString(4) + "</date>";
                    xml = xml + "<sno>" + rs.getInt(5) + "</sno>";
                    xml = xml + "<enabled>" + rs.getString(6) + "</enabled>";
                }

            } catch (SQLException e) {
                xml = xml + "<flag>failure</flag>";
                e.printStackTrace();
            }
            xml = xml + "</response>";
        } else if (command.equalsIgnoreCase("Update")) {


            int officeId = Integer.parseInt(request.getParameter("officeid"));
            int accountingUnit =
                Integer.parseInt(request.getParameter("unit").trim());
            String userId = request.getParameter("userid");
            String date = request.getParameter("date");
            String accessStatus = request.getParameter("access_status");
            xml = "<response><command>Update</command>";
            try {
                ps =
  con.prepareStatement("update HRM_MST_GRANT_CLOSED_OFFICE set accounting_unit_id=?,date_allowed_upto=to_date(?,'dd/mm/yyyy'),enabled=?,updated_by=?,updated_date=? where office_id=? and user_id=?");


                ps.setInt(1, accountingUnit);

                ps.setString(2, date);
                ps.setString(3, accessStatus);
                ps.setString(4, updatedby);
                ps.setTimestamp(5, ts);
                ps.setInt(6, officeId);

                ps.setString(7, userId);
                ps.executeUpdate();


                xml = xml + "<flag>success</flag>";
                xml = xml + "<office_id>" + officeId + "</office_id>";
                xml = xml + "<unit>" + accountingUnit + "</unit>";
                xml = xml + "<userid>" + userId + "</userid>";
                xml = xml + "<enabled>" + accessStatus + "</enabled>";
                xml = xml + "<date>" + date + "</date>";

            } catch (SQLException e) {
                xml = xml + "<flag>failure</flag>";
                e.printStackTrace();
            }
            xml = xml + "</response>";
            //aded on 08Mar2012 for changing the old office id as null for revoking
         
            String  userid1=request.getParameter("userid");
           // System.out.println("userId***********"+userid1);
           // System.out.println("userId length ***********"+userid1.length());
            String emp_id=userid1.substring(4,userid1.length());
            //System.out.println("Getting the Emp id from user id"+emp_id);
            try
            {
            	 
            	ps1=con.prepareStatement("update HRM_EMP_CURRENT_POSTING set OLD_OFFICE_ID=null where  " +
            	 		" EMPLOYEE_ID=? ");
            	 ps1.setString(1,emp_id);
            	 ps1.executeUpdate();

            }
            catch(Exception e)
            {
            	System.out.println("Exception when updating the null to old office id in hrm_emp_current_posting::::"+e);
            	
            }
        } else if (command.equalsIgnoreCase("Delete")) {
            System.out.println("delete");

            int sno = Integer.parseInt(request.getParameter("sno").trim());

            xml = "<response><command>Delete</command>";
            try {
                ps =
  con.prepareStatement("delete from HRM_MST_GRANT_CLOSED_OFFICE where authorized_state=?");
                ps.setInt(1, sno);

                ps.executeUpdate();

                ps =
  con.prepareStatement("select office_id,accounting_unit_id,user_id,to_char(date_allowed_upto,'dd/mm/yyyy'),authorized_state,enabled from HRM_MST_GRANT_CLOSED_OFFICE");

                rs = ps.executeQuery();
                xml = xml + "<flag>success</flag>";

                while (rs.next()) {
                    xml = xml + "<office_id>" + rs.getInt(1) + "</office_id>";
                    xml = xml + "<unit>" + rs.getInt(2) + "</unit>";
                    xml = xml + "<userid>" + rs.getString(3) + "</userid>";

                    xml = xml + "<date>" + rs.getString(4) + "</date>";
                    xml = xml + "<sno>" + rs.getInt(5) + "</sno>";
                    xml = xml + "<enabled>" + rs.getString(6) + "</enabled>";

                }

            } catch (SQLException e) {
                xml = xml + "<flag>failure</flag>";
                e.printStackTrace();
            }
            xml = xml + "</response>";
        } else if (command.equalsIgnoreCase("unit")) {
            int officeId;

            officeId =
                    Integer.parseInt(request.getParameter("officeid").trim());
            xml = "<response><command>unit</command>";
            try {

                ps =
  con.prepareStatement("select a.OFFICE_NAME,a.OFFICE_ADDRESS1,a.OFFICE_ADDRESS2,a.DISTRICT_CODE,a.CITY_TOWN_NAME,a.OFFICE_LEVEL_ID,a.PRIMARY_WORK_ID,b.DISTRICT_NAME,c.OFFICE_LEVEL_NAME from COM_MST_OFFICES a " +
                       " left outer join com_mst_districts b on b.DISTRICT_CODE= a.DISTRICT_CODE " +
                       " inner join com_mst_office_levels c on  c.office_level_id=a.office_level_id " +
                       " where OFFICE_ID=?");
                ps.setInt(1, officeId);
                rs = ps.executeQuery();
                xml = xml + "<flag>success</flag>";
                if (rs.next()) {

                    xml =
 xml + "<office_level>" + rs.getString("OFFICE_LEVEL_NAME") +
   "</office_level>";
                    xml =
 xml + "<office_address>" + rs.getString("OFFICE_ADDRESS1") +
   "</office_address>";
                    xml =
 xml + "<office_address1>" + rs.getString("OFFICE_ADDRESS2") +
   "</office_address1>";
                    if (rs.getString("CITY_TOWN_NAME") != null) {
                        xml =
 xml + "<office_city>" + rs.getString("CITY_TOWN_NAME") + "</office_city>";
                    } else {
                        xml = xml + "<office_city>city</office_city>";
                    }
                    if (rs.getString("DISTRICT_NAME") != null) {
                        xml =
 xml + "<office_district>" + rs.getString("DISTRICT_NAME") +
   "</office_district>";
                    } else {
                        xml = xml + "<office_district>dist</office_district>";
                    }
                }

                ps =
  con.prepareStatement("select accounting_unit_id from com_mst_closed_offices_view where office_id=?");
                ps.setInt(1, officeId);
                rs = ps.executeQuery();
                if (rs.next()) {
                    xml =
 xml + "<office_unit>" + rs.getInt("accounting_unit_id") + "</office_unit>";
                }

            } catch (SQLException e) {
                xml = xml + "<flag>failure</flag>";
                e.printStackTrace();
            }
            xml = xml + "</response>";
            System.out.print(xml);

        }
        out.println(xml);
        out.close();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException,
                                                               IOException {
        // TODO Auto-generated method stub
    }

}
