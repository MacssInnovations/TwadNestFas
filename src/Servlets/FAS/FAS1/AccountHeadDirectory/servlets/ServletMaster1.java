package Servlets.FAS.FAS1.AccountHeadDirectory.servlets;

import java.lang.IllegalArgumentException;

import java.sql.Date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.PrintWriter;
import java.io.IOException;

import java.sql.*;

import java.lang.IllegalArgumentException.*;

import java.util.ResourceBundle;


public class ServletMaster1 extends HttpServlet {

    private Connection connection = null;
    private Statement statement = null;
    private ResultSet results = null;
    //private PreparedStatement ps=null;
    private PreparedStatement ps1 = null;


    public void init(ServletConfig config) throws ServletException {
        super.init(config);


        // opening connection to the database
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

//            ConnectionString =
//                    strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
//                    ":" + strsid.trim();
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection


            Class.forName(strDriver.trim());
            connection =
                    DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                                                strdbpassword.trim());

            try {
                statement = connection.createStatement();
                connection.clearWarnings();
            } catch (SQLException e) {
                System.out.println("Exception in creating statement:" + e);
            }
        } catch (Exception e) {
            System.out.println("Exception in openeing connection:" + e);
        }

    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {

        PrintWriter pw = response.getWriter();
        response.setContentType("text/html");
        response.setHeader("cache-control", "no-cache");
        HttpSession session = request.getSession(true);


        String command = "";
        System.out.println("servlet calledddddddddddddd");
        String offcode = request.getParameter("offcode");
        String accting_code_unit =
            request.getParameter("accounting_unit_code");
        long l = System.currentTimeMillis();
        Timestamp ts = new Timestamp(l);


        try {


            try {
                String supid[] = request.getParameterValues("HSupId");
                String supname[] = request.getParameterValues("HName");
                String supaddr[] = request.getParameterValues("HSupAddr");
                String supphone[] = request.getParameterValues("HSupPhone");
                String supfax[] = request.getParameterValues("HSupFax");
                String supemail[] = request.getParameterValues("HSupEmail");


                PreparedStatement ps1 =
                    connection.prepareStatement("insert into FAS_SUPPLIER_SUB_LEDGER values(?,?,?,?,?,?,?,?,?,?)");
                System.out.println("length :" + supid.length);
                for (int i = 0; i < supid.length; i++) {
                    System.out.println("value " + supid[i]);
                }

                for (int i = 0; i < supid.length; i++) {
                    System.out.println("inside for");
                    ps1.setString(1, offcode);
                    ps1.setString(2, accting_code_unit);
                    ps1.setString(3, supid[i]);
                    ps1.setString(4, supname[i]);
                    ps1.setString(5, supaddr[i]);
                    ps1.setString(6, supphone[i]);
                    ps1.setString(7, supfax[i]);
                    ps1.setString(8, supemail[i]);
                    ps1.setTimestamp(9, ts);
                    ps1.setInt(10, 0);


                    ps1.executeUpdate();
                }
                ps1.close();


                System.out.println("success");
                pw.write("<html>");
                pw.write("<body>");
                pw.write("Record inserted successfully</body></html>");
            } catch (IllegalArgumentException s) {
                System.out.println("IAE:" + s);

                pw.write("<html>");
                pw.write("<body>");
                pw.write("Sorry Record Not Inserted</body></html>");

            }
        }


        catch (Exception e) {
            System.out.println("exce ****2 " + e);
            pw.write("<html>");
            pw.write("<body>");
            pw.write("Sorry Record Not Inserted</body></html>");


        }


    }
}
