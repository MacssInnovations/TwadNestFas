package Servlets.WQS.WQS1.WQTesting.Directory1.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.Timestamp;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class wqs_test_purposeServ extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    private Connection connection = null;
    private Statement statement = null;
    private ResultSet results = null;
    private PreparedStatement ps = null;


    public void init(ServletConfig config) throws ServletException {
        super.init(config);

    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter pw = response.getWriter();

        response.setContentType(CONTENT_TYPE);
        String strCommand = "";
        Calendar c;
        String xml = "", updatedby = null;
        Timestamp ts = null;
        int sid = 0, no = 0;
        String stype = "";

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
                System.out.println("Exception in creating statement:" + e);
            }
        } catch (Exception e) {
            System.out.println("Exception in openeing connection:" + e);
        }

        response.setContentType(CONTENT_TYPE);
        HttpSession session = request.getSession(false);
        session = request.getSession(false);
        updatedby = (String)session.getAttribute("UserId");
        long l = System.currentTimeMillis();
        ts = new Timestamp(l);
        System.out.println(updatedby);
        System.out.println(ts);
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");
        try {
            strCommand = request.getParameter("command");
            System.out.println("Command========================>" +
                               strCommand);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            stype = request.getParameter("stype");
        } catch (Exception e) {
            System.out.println("in getting values **** " + e);
        }
        if (strCommand.equalsIgnoreCase("chk")) {
            sid = Integer.parseInt(request.getParameter("sid"));
            xml = "<response><command>chk</command>";
            try {
                String sql =
                    "SELECT SAMPLE_ID FROM WQS_SAMPLE_TYPE WHERE SAMPLE_ID=?";
                ps = connection.prepareStatement(sql);
                ps.setInt(1, sid);
                results = ps.executeQuery();
                int i = 0;
                while (results.next()) {
                    xml =
 xml + "<sid>" + results.getInt("SAMPLE_ID") + "</sid>";
                    i++;
                    xml += "<flag>Record</flag>";
                }
                if (i == 0) {
                    xml += "<flag>Success</flag>";
                }
            } catch (Exception se) {
                System.out.println("not get" + se);
                xml = xml + "<flag>Failure</flag>";
            }
            xml += "</response>";
        } else if (strCommand.equalsIgnoreCase("Add")) {
            try {
                System.out.println("outside while");
                statement = connection.createStatement();
                results =
                        statement.executeQuery("SELECT SAMPLE_ID FROM WQS_SAMPLE_TYPE GROUP BY SAMPLE_ID HAVING SAMPLE_ID =(select max(SAMPLE_ID) from WQS_SAMPLE_TYPE)");
                while (results.next()) {
                    System.out.println("inside while");
                    no = results.getInt(1);
                }
                no = no + 1;
                System.out.println(no);
            } catch (Exception e) {
                System.out.println("Err in AutoIncrement:" + e.getMessage());
            }
            xml = "<response><command>Add</command>";
            try {
                String sql =
                    "SELECT SAMPLE_ID FROM WQS_SAMPLE_TYPE WHERE SAMPLE_ID=?";
                try {
                    ps = connection.prepareStatement(sql);
                    ps.setInt(1, no);
                    results = ps.executeQuery();
                    int i = 0;
                    while (results.next()) {
                        i++;
                    }
                    if (i == 0) {
                        sql =
 "insert into WQS_SAMPLE_TYPE(SAMPLE_ID,SAMPLE_TYPE,UPDATED_DATE,UPDATED_BY_USER_ID) VALUES(?,?,?,?)";
                        try {
                            ps = connection.prepareStatement(sql);
                            ps.setInt(1, no);
                            ps.setString(2, stype);
                            ps.setTimestamp(3, ts);
                            ps.setString(4, updatedby);
                            ps.executeUpdate();
                            xml = xml + "<code>" + no + "</code>";
                            xml = xml + "<flag>Success</flag>";
                            ps.close();
                        } catch (Exception e) {
                            System.out.println("exception in the add" + e);
                            xml = xml + "<flag>Fail</flag>";
                        }
                    } else {
                        xml = xml + "<flag>Record</flag>";
                    }
                } catch (Exception ae) {
                    System.out.println("Exception is: in the insert" + ae);

                }
            } catch (Exception e) {
                System.out.println("Exception in the select --- insert" + e);
            }
            xml = xml + "</response>";
        } else if (strCommand.equalsIgnoreCase("Update")) {
            sid = Integer.parseInt(request.getParameter("sid"));
            xml = "<response><command>Update</command>";
            String sql =
                "update WQS_SAMPLE_TYPE set SAMPLE_TYPE=?,UPDATED_DATE=?,UPDATED_BY_USER_ID=? where SAMPLE_ID=?";
            try {
                ps = connection.prepareStatement(sql);
                ps.setString(1, stype);
                ps.setTimestamp(2, ts);
                ps.setString(3, updatedby);
                ps.setInt(4, sid);

                ps.executeUpdate();
                xml = xml + "<flag>Success</flag>";
                ps.close();
            } catch (Exception e) {
                System.out.println("exception in the update" + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        } else if (strCommand.equalsIgnoreCase("Delete")) {
            sid = Integer.parseInt(request.getParameter("sid"));
            System.out.println("Inside Delete");
            xml = "<response><command>Delete</command>";
            String sql = "delete from wqs_sample_type where sample_id=?";
            try {
                ps = connection.prepareStatement(sql);
                ps.setInt(1, sid);
                ps.executeUpdate();
                xml = xml + "<flag>success</flag><sid>" + sid + "</sid>";
                ps.close();
            } catch (Exception e) {
                System.out.println("exception in the delete" + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }
        System.out.println("xml:" + xml);
        pw.println(xml);

    }
}
