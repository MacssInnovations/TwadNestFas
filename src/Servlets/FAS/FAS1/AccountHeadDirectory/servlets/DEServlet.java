package Servlets.FAS.FAS1.AccountHeadDirectory.servlets;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.PrintWriter;
import java.io.IOException;

import java.sql.*;

public class DEServlet extends HttpServlet {

    private Connection connection = null;
    private Statement statement = null;
    private ResultSet results = null;
    private PreparedStatement ps = null;


    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            connection = DriverManager.getConnection("Jdbc:Odbc:fas");
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

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        String strCommand = "";
        String xml = "";
        PrintWriter pw = response.getWriter();
        response.setContentType("text/xml");
        response.setHeader("cache-control", "no-cache");
        HttpSession session = request.getSession(true);


        System.out.println("servlet calledddddddddddddd");
        try {
            strCommand = request.getParameter("command");
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (strCommand.equalsIgnoreCase("Delete")) {
            System.out.println("Delete");
            xml = "<response><command>Delete</command>";
            // int strSubCode=0;
            int strAHCode = 0;
            try {
                //strSubCode = Integer.parseInt(request.getParameter("SLCode"));

                strAHCode = Integer.parseInt(request.getParameter("AHCode"));
                System.out.println(strAHCode);
            }

            catch (Exception e) {
                e.printStackTrace();
            }
            try {
                String sql =
                    "delete from FAS_ACCOUNT_HEAD_MASTER where  ACCOUNT_HEAD_CODE=?";
                ps = connection.prepareStatement(sql);

                //ps.setInt(1,strSubCode);
                ps.setInt(1, strAHCode);
                int i = ps.executeUpdate();
                // on sucess
                // build and return the xml with id
                if (i >= 1) {
                    xml =
 xml + "<flag>success</flag><AHCode>" + strAHCode + "</AHCode>";
                } else {
                    xml = xml + "<flag>failure</flag>";
                }
            } catch (Exception e) {
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }

        System.out.println("xml is : " + xml);

        pw.write(xml);
        pw.flush();
        pw.close();


    }
}


