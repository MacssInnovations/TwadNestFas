package Servlets.FAS.FAS1.AccountHeadDirectory.servlets;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.PrintWriter;
import java.io.IOException;

import java.sql.*;

public class ComboServlet2 extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);
        System.out.println("combo servlet2 called");
        PrintWriter out = response.getWriter();
        String str = null;
        try {
            str = request.getParameter("sltype");

        } catch (Exception e) {
            e.printStackTrace();
        }
        String s = "";
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            Connection con = DriverManager.getConnection("jdbc:odbc:fas");

            String query = "select * from " + str;
            System.out.println("query" + query);
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = null;

            rs = ps.executeQuery();
            while (rs.next()) {
                s = s + "<option><code>" + rs.getString(1) + "</code>";
                s = s + "<desc>" + rs.getString(2) + "</desc></option>";
            }

            response.setHeader("Cache-control", "no-cache");
            out.write("<select>" + s + "</select>");
            System.out.println("returning" + str);
            System.out.println("xml is" + s);


        } catch (Exception e1) {
            System.out.println("Exception is" + e1);
        }
    }
}
