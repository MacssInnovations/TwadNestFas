package Servlets.PMS.PMS1.DCB.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class dis_servlet extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);
        Connection con = null;
        ResultSet result = null;
        System.out.println("Welcome to servlet");
        String strCommand = "";
        String xml = "";
        //int s_no=0;
        int dis_code = 0;
        String dis_name = "";
        PrintWriter pw = response.getWriter();
        response.setHeader("Cache-Control", "no-cache");
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            con =
 DriverManager.getConnection("jdbc:oracle:thin:@10.163.0.58:1521:twadnest",
                             "pmstwad", "pmstwad123");
        }

        catch (ClassNotFoundException e) {
            System.out.println("Connection err");
        }

        catch (SQLException e) {
            System.out.println("Sql err");
        }


        try {
            strCommand = request.getParameter("command");
            System.out.println("strCommand : " + strCommand);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            dis_code = Integer.parseInt(request.getParameter("dis_code"));
            System.out.println("dis_code is : " + dis_code);
        } catch (Exception e) {
            System.out.println("Exception fetching dis_code ===> " + e);
        }


        try {
            dis_name = request.getParameter("dis_name");
            System.out.println("dis_name is  : " + dis_name);
        } catch (Exception e) {
            System.out.println("Exception fetching dis_name===> " + e);
        }


        if (strCommand.equalsIgnoreCase("Get")) {
            xml = "<response><command>Get</command>";
            System.out.println("\n*************\nGet\n**************\n");

            // result = null;
            try {
                String sqlGet =
                    "select DISTRICT_CODE,DISTRICT_NAME from COM_MST_DISTRICTS";
                PreparedStatement ps = con.prepareStatement(sqlGet);
                result = ps.executeQuery();
                xml = xml + "<flag>success</flag>";

                while (result.next()) {
                    xml =
 xml + "<dis_code>" + result.getInt("DISTRICT_CODE") + "</dis_code>";
                    xml =
 xml + "<dis_name>" + result.getString("DISTRICT_NAME") + "</dis_name>";
                }
            } catch (Exception e1) {
                System.out.println("Exception in Getting records ===> " + e1);
                xml = xml + "<flag>failure</flag>";
            }
        }


        xml = xml + "</response>";
        System.out.println("xml is : " + xml);
        pw.write(xml);
        pw.flush();
        pw.close();

    }
}

