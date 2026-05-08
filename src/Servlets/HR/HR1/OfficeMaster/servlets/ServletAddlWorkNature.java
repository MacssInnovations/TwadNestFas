package Servlets.HR.HR1.OfficeMaster.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.Timestamp;

import java.text.SimpleDateFormat;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class ServletAddlWorkNature extends HttpServlet {
    private static final String CONTENT_TYPE = "text/xml";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);
        response.setHeader("cache-control", "no-cache");
        PrintWriter pw = response.getWriter();
        String xml = "<response>";
        String command = request.getParameter("command");
        int intOfficeId = 0;

        Connection connection = null;
        PreparedStatement ps = null;
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
                String OfficeId = request.getParameter("id");
                try {
                    intOfficeId = Integer.parseInt(OfficeId);
                } catch (NumberFormatException num) {
                    System.out.println("exception : " + num);
                    xml = xml + "<flag>failure</flag>";
                    command = "aa";
                }
                String EffectiveDate = request.getParameter("edate");

                java.sql.Date dtEffectiveDate = null;
                if (EffectiveDate != null) {
                    System.out.println("before converting date");
                    String dateString = EffectiveDate;
                    SimpleDateFormat dateFormat =
                        new SimpleDateFormat("dd/MM/yyyy");
                    java.util.Date d;
                    d = dateFormat.parse(dateString.trim());
                    dateFormat.applyPattern("yyyy-MM-dd");
                    dateString = dateFormat.format(d);
                    dtEffectiveDate = java.sql.Date.valueOf(dateString);
                }

                String workNatureId = request.getParameter("workNatureId");
                String remarks = request.getParameter("remarks");

                long l = System.currentTimeMillis();
                Timestamp ts = new Timestamp(l);

                if (command.equals("add")) {
                    String sqlInsert =
                        "insert into COM_OFFICE_ADDL_NATURE(OFFICE_ID,WORK_NATURE_ID,DATE_EFFECTIVE_FROM,REMARKS,UPDATED_BY_USER_ID,UPDATED_DATE) values (?,?,?,?,?,?)";
                    ps = connection.prepareStatement(sqlInsert);

                    ps.setInt(1, intOfficeId);
                    ps.setString(2, workNatureId);
                    ps.setDate(3, dtEffectiveDate);
                    ps.setString(4, remarks);
                    ps.setString(5, "testing");
                    ps.setTimestamp(6, ts);
                    int i = ps.executeUpdate();
                    if (i >= 1) {
                        xml =
 xml + "<command>add</command><flag>success</flag>";
                    } else {
                        xml =
 xml + "<command>add</command><flag>failure</flag>";
                    }
                    ps.close();

                } else if (command.equals("update")) {
                    System.out.println("inside update");
                    String sqlUpdate =
                        "update COM_OFFICE_ADDL_NATURE set DATE_EFFECTIVE_FROM=?,REMARKS=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=? where OFFICE_ID=? and WORK_NATURE_ID=?";
                    ps = connection.prepareStatement(sqlUpdate);
                    ps.setDate(1, dtEffectiveDate);
                    ps.setString(2, remarks);
                    ps.setString(3, "testing");
                    ps.setTimestamp(4, ts);
                    ps.setInt(5, intOfficeId);
                    ps.setString(6, workNatureId);
                    int i = ps.executeUpdate();
                    if (i >= 1) {
                        xml =
 xml + "<command>update</command><flag>success</flag>";
                    } else {
                        xml =
 xml + "<command>update</command><flag>failure</flag>";
                    }
                    ps.close();
                } else if (command.equals("delete")) {
                    System.out.println("inside delete");
                    String sqldelete =
                        "delete from COM_OFFICE_ADDL_NATURE where OFFICE_ID=? and WORK_NATURE_ID=?";
                    ps = connection.prepareStatement(sqldelete);
                    ps.setInt(1, intOfficeId);
                    ps.setString(2, workNatureId);
                    int i = ps.executeUpdate();
                    if (i >= 1) {
                        xml =
 xml + "<command>delete</command><flag>success</flag>";
                    } else {
                        xml =
 xml + "<command>delete</command><flag>failure</flag>";
                    }
                    ps.close();
                } else if (command.equals("list")) {
                    System.out.println("inside list");
                    String sqldelete =
                        "select COM_OFFICE_ADDL_NATURE.* , WORK_NATURE_DESC   from COM_OFFICE_ADDL_NATURE,COM_MST_WORK_NATURE where  COM_OFFICE_ADDL_NATURE.WORK_NATURE_ID=COM_MST_WORK_NATURE.WORK_NATURE_ID and  OFFICE_ID=?";
                    ps = connection.prepareStatement(sqldelete);
                    ps.setInt(1, intOfficeId);

                    ResultSet res = ps.executeQuery();
                    xml = xml + "<command>list</command><flag>success</flag>";
                    while (res.next()) {
                        java.text.SimpleDateFormat sdf =
                            new java.text.SimpleDateFormat("dd/MM/yyyy");
                        String DateToBeDisplayed =
                            sdf.format(res.getDate("DATE_EFFECTIVE_FROM"));
                        xml =
 xml + "<options><id>" + res.getInt("OFFICE_ID") + "</id>";
                        xml =
 xml + "<work>" + res.getString("WORK_NATURE_ID") + "</work>";
                        xml =
 xml + "<workdesc>" + res.getString("WORK_NATURE_DESC") + "</workdesc>";
                        xml = xml + "<edate>" + DateToBeDisplayed + "</edate>";
                        xml =
 xml + "<remarks>" + res.getString("REMARKS") + "</remarks></options>";
                    }
                }
            } catch (Exception e) {
                System.out.println("exception : " + e);
                xml =
 xml + "<command>" + command.toLowerCase() + "</command><flag>failure</flag>";
            }
            connection.close();
        } catch (Exception e) {
            System.out.println("exception : " + e);
            xml =
 xml + "<command>" + command.toLowerCase() + "</command><flag>failure</flag>";
        }

        xml = xml + "</response>";
        System.out.println("xml is : " + xml);
        pw.write(xml);
        pw.flush();
    }
}
