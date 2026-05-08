package Servlets.FAS.FAS1.ContractorsInfoSys.servlets;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.PrintWriter;
import java.io.IOException;

import java.sql.*;

import java.util.*;
//
public class ServletClass extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";

    private Connection connection = null;
    private Statement statement = null;
    private ResultSet results = null;
    private PreparedStatement ps = null;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        /// opening connection to the database
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


            try {
                statement = connection.createStatement();
                connection.clearWarnings();
            } catch (SQLException e) {
                //System.out.println("Exception in creating statement:"+e);
            }
        } catch (Exception e) {
            //System.out.println("Exception in openeing connection:"+e);
        }
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        String strCommand = "";
        String xml = "";
        PrintWriter pw = response.getWriter();
        response.setContentType("text/xml");
        response.setHeader("cache-control", "no-cache");
        HttpSession session = request.getSession(false);
        String userid = (String)(session.getAttribute("userid"));
        System.out.println("servlet called");

        try {
            strCommand = request.getParameter("command");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (strCommand.equalsIgnoreCase("Add")) {
            xml = "<response><command>Add</command>";
            String strClassRegId = "";
            String strClassRegName = "";


            try {
                strClassRegId = request.getParameter("Id");
                strClassRegName = request.getParameter("Name");

            } catch (Exception e) {
                //System.out.println("exce **** "+ e);
            }


            // insert values into the table
            String sql =
                "insert into PMS_MST_CON_CLASS(Regn_Class_Id,Regn_Class_Desc,Last_Updated_Date,Last_Updated_By)values(?,?,?,?)";
            try {

                long l = System.currentTimeMillis();
                Timestamp ts = new Timestamp(l);

                ps = connection.prepareStatement(sql);
                ps.setString(1, strClassRegId);
                ps.setString(2, strClassRegName);

                ps.setTimestamp(3, ts);
                ps.setString(4, "unknown");
                ps.executeUpdate();

                xml = xml + "<flag>success</flag>";
                ps.close();
            } catch (Exception e) {
                System.out.println("The Exception is:" + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";

        } else if (strCommand.equalsIgnoreCase("Delete")) {
            // read the parameter id

            xml = "<response><command>Delete</command>";
            String ClassRegId = "";
            try {
                ClassRegId = request.getParameter("Id");
            } catch (Exception e) {
                e.printStackTrace();
            }
            // delete the row from table
            try {
                String sql =
                    "delete from PMS_MST_CON_CLASS where Regn_Class_Id=?";
                ps = connection.prepareStatement(sql);
                ps.setString(1, ClassRegId);
                int i = ps.executeUpdate();
                // on sucess
                // build and return the xml with id
                if (i >= 1) {
                    xml = xml + "<flag>success</flag>";
                    connection.commit();
                } else {
                    xml = xml + "<flag>failure</flag>";
                }
            } catch (Exception e) {
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        } else if (strCommand.equalsIgnoreCase("Update")) {
            // read the parameters

            xml = "<response><command>Update</command>";
            String ClassRegId = "";
            String strClassRegName = "";


            try {
                ClassRegId = request.getParameter("Id");
                strClassRegName = request.getParameter("Name");

            } catch (Exception e) {
                //System.out.println("exce **** "+ e);
            }

            try {
                ps =
  connection.prepareStatement("update PMS_MST_CON_CLASS set Regn_Class_Desc=?,Last_Updated_Date=?,Last_Updated_By=? where Regn_Class_Id=?");
                ps.setString(1, strClassRegName);

                long l = System.currentTimeMillis();
                Timestamp ts = new Timestamp(l);
                ps.setTimestamp(2, ts);
                // get from session
                ps.setString(3, "unknown");
                ps.setString(4, ClassRegId);
                int i = ps.executeUpdate();
                if (i >= 1) {
                    xml = xml + "<flag>success</flag>";
                    connection.commit();
                } else {
                    xml = xml + "<flag>failure</flag>";
                }
                ps.close();
            } catch (Exception e) {
                e.printStackTrace();
                xml = xml + "<flag>failure</flag>";
            }
            // on sucess
            // build and return the xml with entire values
            xml = xml + "</response>";
        } else if (strCommand.equalsIgnoreCase("Get")) {
            xml = "<response><command>Get</command>";
            String ClassRegId = "";

            try {
                ClassRegId = request.getParameter("Id");
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                ps =
  connection.prepareStatement("select * from PMS_MST_CON_CLASS where Regn_Class_Id=?");
                ps.setString(1, ClassRegId);
                results = ps.executeQuery();
                if (results.next()) {
                    xml =
 xml + "<flag>success</flag><id>" + ClassRegId + "</id><name>" +
   results.getString("Regn_Class_Desc") + "</name>";
                } else {
                    xml = xml + "<flag>failure</flag>";
                }
                results.close();
            } catch (Exception e) {
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        } else if (strCommand.equalsIgnoreCase("Verify")) {
            String id = request.getParameter("id");
            System.out.println("values : " + strCommand + "  " + id);
            String ClassRegId = "";
            try {
                ClassRegId = (id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                ps =
  connection.prepareStatement("select * from PMS_MST_CON_CLASS where Regn_Class_Id=?");
                ps.setString(1, ClassRegId);
                results = ps.executeQuery();
                if (results.next()) {
                    xml = "<message>valid</message>";
                } else {
                    xml = "<message>Invalid</message>";
                }
            } catch (Exception e) {
                xml = "<message>Invalid</message>";
            }
            pw.print(xml);
            return;
        }
        pw.write(xml);
        pw.flush();
        pw.close();
    }
}
