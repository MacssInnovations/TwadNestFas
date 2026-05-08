package Servlets.HR.HR1.EmployeeMaster.Reports;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.Timestamp;

import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class New_Role_Assigning extends HttpServlet {


    public void init(ServletConfig config) throws ServletException {
        super.init(config);

    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        Connection con = null, con1 = null;

        try {
            System.out.println("New role assinging----------------------->");
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
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());

        } catch (Exception e) {
            System.out.println("Exception in connection..." + e);
        }
        ResultSet rs = null, rs1 = null;
        PreparedStatement ps = null, ps1 = null;
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);


        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");

        String xml = "";

        String strCommand = "";
        int Desig_id = 0;
        int sgroup = 0;
        try {
            strCommand = request.getParameter("Command");
            System.out.println("assign....." + strCommand);
            //   Desig_id=request.getParameter("desigval");
            //  System.out.println("Desig_id"+Desig_id);
            //sgroup=Integer.parseInt(request.getParameter("cmbsgroup"));
        }

        catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }


        if (strCommand.equalsIgnoreCase("role")) {
            xml = "<response>";
            try {
                System.out.println("strCommand" + strCommand);
                Desig_id = Integer.parseInt(request.getParameter("desigval"));
                ps =
  con.prepareStatement("select role_id,role_name from sec_mst_roles where role_id \n" +
                       "not in(select role_id from  SEC_MST_ROLE_TEMPLATE where designation_id =" +
                       Desig_id + ") order by role_name\n");

                int role_id = 0;
                rs = ps.executeQuery();
                int count = 0;
                xml += "<flag>";

                xml = xml + "success</flag>";


                while (rs.next()) {
                    count++;
                    xml = xml + "<roles>";
                    xml =
 xml + "<role_id>" + rs.getInt("role_id") + "</role_id>";
                    xml =
 xml + "<role_name>" + rs.getString("role_name") + "</role_name>";
                    xml = xml + "</roles>";
                }
                if (count > 0)

                {
                    xml += "<flag>";
                    xml = xml + "success</flag>";
                } else
                    xml += "failure</flag>";
            }

            catch (Exception e) {
                xml = xml + "<flag>failure</flag>";
                System.out.println("catch........1" + e);

            }

            int cnt = 0;
            try {
                ps =
  con.prepareStatement("select a.role_id as urole_id,b.role_name as urole_name from  SEC_MST_ROLE_TEMPLATE a, sec_mst_roles b where a.designation_id =" +
                       Desig_id + " and  b.role_id=a.role_id\n" +
                       "order by b.role_name ");
                System.out.println("ps--->" + ps);
                rs1 = ps.executeQuery();


                while (rs1.next()) {
                    xml = xml + "<uroles>";
                    xml =
 xml + "<urole_id>" + rs1.getInt("urole_id") + "</urole_id>";
                    xml =
 xml + "<urole_name>" + rs1.getString("urole_name") + "</urole_name>";
                    xml = xml + "</uroles>";
                    cnt++;
                }
                if (cnt > 0)
                    xml = xml + "<uflag>success</uflag>";
                else
                    xml = xml + "<uflag>failure</uflag>";
            } catch (Exception e) {
                xml = xml + "<uflag>failure</uflag>";
                System.out.println("error 2" + e);
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
            System.out.println(xml);


        }
        if (strCommand.equalsIgnoreCase("Desig")) {
            xml = "<response>";
            try {
                sgroup = Integer.parseInt(request.getParameter("cmbsgroup"));
                System.out.println("Desig" + strCommand);
                System.out.println("sgroup::" + sgroup);
                ps =
  con.prepareStatement("select DESIGNATION_ID,DESIGNATION from HRM_MST_DESIGNATIONS  where SERVICE_GROUP_ID=? order by DESIGNATION");
                System.out.println("ps--->" + ps);
                ps.setInt(1, sgroup);
                rs = ps.executeQuery();
                System.out.println("sql");
                int count = 0;

                while (rs.next()) {
                    xml = xml + "<option>";
                    xml =
 xml + "<desig_id>" + rs.getInt("DESIGNATION_ID") + "</desig_id>";
                    xml =
 xml + "<desig_name>" + rs.getString("DESIGNATION") + "</desig_name>";
                    xml = xml + "</option>";
                    count++;
                }


                if (count > 0) {
                    xml = xml + "<flag>success</flag></response>";
                }


            } catch (Exception e) {

                System.out.println("catch........" + e);
                xml = "<response><flag>failure</flag></response>";
            }
            System.out.println(xml);
            out.println(xml);
        } else if (strCommand.equalsIgnoreCase("submit")) {
            System.out.println("inside submit");
            String desigval = null, roleval = null, sql = null, sql1 = null;
            String[] desig = null;
            String[] role = null;
            int flag = 0;
            int i, j;
            System.out.println("hello");

            try {
                String updatedby = (String)session.getAttribute("UserId");
                long l = System.currentTimeMillis();
                Timestamp ts = new Timestamp(l);
                System.out.println("step1");
                System.out.println(" updatedby ::: " + updatedby);
                System.out.println(" ts ::: " + ts);
                desigval = request.getParameter("desigval");
                roleval = request.getParameter("roleval");
                System.out.println(desigval + roleval);
                //   desig=desigval.split(",");
                role = roleval.split(",");
                //  System.out.println("desig"+desig);
                System.out.println("role" + role);
                sql1 =
"delete from SEC_MST_ROLE_TEMPLATE where designation_id=? ";
                System.out.println("sql::" + sql1);
                ps1 = con.prepareStatement(sql1);
                ps1.setInt(1, Integer.parseInt(desigval));
                ps1.executeUpdate();
                System.out.println(role.length);
                for (j = 0; j < role.length; j++) {

                    sql =
 "insert into SEC_MST_ROLE_TEMPLATE (designation_id,role_id,updated_date,updated_by_user_id) values(?,?,?,?)";
                    System.out.println(sql);
                    ps = con.prepareStatement(sql);
                    ps.setInt(1, Integer.parseInt(desigval));
                    ps.setInt(2, Integer.parseInt(role[j]));
                    ps.setTimestamp(3, ts);
                    ps.setString(4, updatedby);
                    ps.executeUpdate();

                }

                xml = "<response><flag>success</flag></response>";
            }

            catch (Exception e) {
                System.out.println(e);
                xml = "<response><flag>fail</flag></response>";
            }
            System.out.println(xml);
            out.println(xml);
        }
    }

}
