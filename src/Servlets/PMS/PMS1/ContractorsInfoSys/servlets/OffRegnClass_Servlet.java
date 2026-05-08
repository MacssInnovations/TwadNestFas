package Servlets.PMS.PMS1.ContractorsInfoSys.servlets;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.PrintWriter;
import java.io.IOException;

import java.sql.Date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.sql.*;

import java.util.*;

//SERVLET TO PROCESS THE AjaxOffiRegn.js

public class OffRegnClass_Servlet extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

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
                ////System.out.println("Exception in creating statement:"+e);
            }
        } catch (Exception e) {
            ////System.out.println("Exception in openeing connection:"+e);
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
            //System.out.println("inside add");
            xml = "<response><command>Add</command>";
            String strOffice = "";
            String strRegClassId = "";
            Date strDate = null;
            int cid1 = 0;

            try {
                strOffice = request.getParameter("RegOffice");
                //System.out.println(strOffice);

                strRegClassId = request.getParameter("RegClassId");
                //System.out.println(strRegClassId);
                cid1 = Integer.parseInt(strRegClassId);

            } catch (Exception e) {
                //System.out.println("exce **** "+ e);
            }

            String sql =
                "insert into PMS_OFFICE_REGN_CLASS(OFFICE_LEVEL_ID,REGN_CLASS_ID,DATE_EFFECTIVE_FROM,LAST_UPDATED_DATE,LAST_UPDATED_BY)values(?,?,?,?,?)";
            try {
                //for date functionality
                //first date field for Date_creation field in the form
                String dateString1 = request.getParameter("Date");
                SimpleDateFormat dateFormat =
                    new SimpleDateFormat("dd/MM/yyyy");
                java.util.Date d1;
                try {
                    d1 = dateFormat.parse(dateString1);
                    dateFormat.applyPattern("yyyy-MM-dd");
                    dateString1 = dateFormat.format(d1);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                java.sql.Date date1 = java.sql.Date.valueOf(dateString1);
                //System.out.println(date1);

                java.text.SimpleDateFormat sdf =
                    new java.text.SimpleDateFormat("dd/MM/yyyy");
                String DateTo = sdf.format(date1);

                long l = System.currentTimeMillis();
                Timestamp ts = new Timestamp(l);
                ps = connection.prepareStatement(sql);

                ps.setString(1, strOffice);
                ps.setInt(2, cid1);
                ps.setDate(3, date1);

                //System.out.println("ps is:"+DateTo);
                ps.setTimestamp(4, ts);
                ps.setString(5, "test");

                ps.executeUpdate();

                xml =
 xml + "<flag>success</flag><offid>" + strOffice + "</offid><cid>" + cid1 +
   "</cid><date>" + DateTo + "</date>";
                ps.close();
            } catch (SQLException e) {
                System.out.println("yes here:" + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";

        }

        else if (strCommand.equals("Display")) {
            xml = "<response><command>Display</command>";
            try {
                //System.out.println("inside Display");
                String sql =
                    "SELECT COM_MST_OFFICE_LEVELS.OFFICE_LEVEL_ID,COM_MST_OFFICE_LEVELS.OFFICE_LEVEL_NAME,PMS_MST_CON_CLASS.REGN_CLASS_ID, PMS_MST_CON_CLASS.REGN_CLASS_DESC,PMS_OFFICE_REGN_CLASS.DATE_EFFECTIVE_FROM";
                sql =
 sql + " FROM PMS_MST_CON_CLASS,COM_MST_OFFICE_LEVELS,PMS_OFFICE_REGN_CLASS";
                sql =
 sql + " WHERE PMS_MST_CON_CLASS.REGN_CLASS_ID=PMS_OFFICE_REGN_CLASS.REGN_CLASS_ID";
                sql =
 sql + " AND COM_MST_OFFICE_LEVELS.OFFICE_LEVEL_ID=PMS_OFFICE_REGN_CLASS.OFFICE_LEVEL_ID";
                sql = sql + " ORDER BY PMS_MST_CON_CLASS.REGN_CLASS_ID";

                results = statement.executeQuery(sql);

                try {
                    xml = xml + "<flag>success</flag>";
                    while (results.next()) {
                        Date strdate = results.getDate("DATE_EFFECTIVE_FROM");
                        java.text.SimpleDateFormat sdf =
                            new java.text.SimpleDateFormat("dd/MM/yyyy");
                        String StrDate1 = sdf.format(strdate);


                        xml =
 xml + "<value><offid>" + results.getString("OFFICE_LEVEL_ID") +
   "</offid><offdesc>" + results.getString("OFFICE_LEVEL_NAME") +
   "</offdesc><cid>" + results.getInt("REGN_CLASS_ID") + "</cid><cdesc>" +
   results.getString("REGN_CLASS_DESC") + "</cdesc>";
                        xml = xml + "<date>" + StrDate1 + "</date></value>";

                    }

                } catch (SQLException e) {
                    System.out.println("Exception in resultset:" + e);
                    xml = xml + "<flag>failure</flag>";
                }

            } catch (Exception e1) {
                xml = xml + "<flag>failure</flag>";
                System.out.println("exception is********:" + e1);
            }
            xml = xml + "</response>";
        }

        else if (strCommand.equalsIgnoreCase("Delete")) {

            //System.out.println("inside delete");
            xml = "<response><command>Delete</command>";

            String strOffice = "";
            String strRegClassId2 = "";
            int cid5 = 0;
            try {
                strOffice = request.getParameter("RegOffice");
                //System.out.println(strOffice);
                strRegClassId2 = request.getParameter("RegClassId");
                //System.out.println(strRegClassId2);
                cid5 = Integer.parseInt(strRegClassId2);
            } catch (NumberFormatException e1) {

                //System.out.println(e1);

            }


            // delete the row from table
            try {
                String sql =
                    "delete from PMS_OFFICE_REGN_CLASS where OFFICE_LEVEL_ID=? and REGN_CLASS_ID=?";
                ps = connection.prepareStatement(sql);
                ps.setString(1, strOffice);
                ps.setInt(2, cid5);

                int i = ps.executeUpdate();
                // on sucess
                // build and return the xml with id
                if (i >= 1) {
                    xml =
 xml + "<flag>success</flag><offId>" + strOffice + "</offId><ClassId>" + cid5 +
   "</ClassId>";

                    connection.commit();
                } else {
                    xml = xml + "<flag>failure</flag>";
                }
            } catch (Exception e) {
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        } else if (strCommand.equalsIgnoreCase("check")) {

            xml = "<response><command>check</command>";

            String strOffice = request.getParameter("officeid");
            String strRegClassId = request.getParameter("classid");
            int cid2 = 0;
            cid2 = Integer.parseInt(strRegClassId);
            try {

                String sql =
                    "select * from PMS_OFFICE_REGN_CLASS where OFFICE_LEVEL_ID=? and REGN_CLASS_ID=? ";
                //System.out.println("after + " + connection);
                ps = connection.prepareStatement(sql);
                //System.out.println("sql:" +sql);
                //System.out.println("ps : " + ps);
                ps.setString(1, strOffice);
                //System.out.println("ps is" +strOffice);
                ps.setInt(2, cid2);
                //System.out.println("ps is" +cid2);
                ResultSet rs = ps.executeQuery();
                //System.out.println(rs);

                if (rs.next()) {

                    //System.out.println("inside if");
                    xml = xml + "<flag>success</flag>";
                    xml = xml + "<value> ";
                    xml =
 xml + "<officeid>" + rs.getString("OFFICE_LEVEL_ID") +
   "</officeid><classid>" + rs.getInt("REGN_CLASS_ID") + "</classid>";
                    xml = xml + "</value>";
                } else {
                    xml = xml + "<flag>failure</flag>";
                }

                ps.close();
            } catch (Exception e) {
                //System.out.println("exce ****2 vv"+ e.getStackTrace());
                //System.out.println("exce ****2 vv"+ e.getMessage());
                // cannot insert values
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";

        }

        System.out.println(xml);
        pw.write(xml);

        pw.flush();
        pw.close();


    }
}

