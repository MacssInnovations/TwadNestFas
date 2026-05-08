package Servlets.FAS.FAS1.ContractorsInfoSys.servlets;


import javax.servlet.*;
import javax.servlet.http.*;

import java.io.PrintWriter;
import java.io.IOException;

import java.sql.Date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.sql.*;

import java.util.*;

public class Servlet_MstRegFees extends HttpServlet {
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
            System.out.println("inside add");
            xml = "<response><command>Add</command>";
            String strOffice = "";
            String strRegClassId = "";
            String strState = "";
            int strRegFees = 0;
            Date strDate = null;
            int cid1 = 0;

            try {
                strOffice = request.getParameter("RegOffice");
                System.out.println(strOffice);

                strRegClassId = request.getParameter("RegClassId");
                System.out.println(strRegClassId);

                strState = request.getParameter("RegState");
                System.out.println(strState);
                strRegFees = Integer.parseInt(request.getParameter("RegFees"));
                System.out.println(strRegFees);
                //strDate = request.getParameter("Date");

                /*String query="select Regn_Class_Id from PROJECT_MST_CONTRACTOR_CLASS where Regn_Class_Desc='"+strRegClassId+"'";
         ps=connection.prepareStatement(query);
           results=ps.executeQuery();
          while(results.next())
          {
            cid1=results.getInt("Regn_Class_Id");
            System.out.println(cid1);
          }*/
                cid1 = Integer.parseInt(strRegClassId);

            } catch (Exception e) {
                //System.out.println("exce **** "+ e);
            }


            // insert values into the table
            String sql =
                "insert into PMS_MST_CON_REGN_FEES(Office_Level_Id,Regn_Class_Id,REGN_STATE_COVERAGE,Regn_Fees,Date_effective_from,Last_Updated_Date,Last_Updated_By)values(?,?,?,?,?,?,?)";
            try { //for date functionality
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
                System.out.println(date1);

                java.text.SimpleDateFormat sdf =
                    new java.text.SimpleDateFormat("dd/MM/yyyy");
                String DateTo = sdf.format(date1);

                long l = System.currentTimeMillis();
                Timestamp ts = new Timestamp(l);
                System.out.println(ts);

                ps = connection.prepareStatement(sql);
                System.out.println(sql);
                ps.setString(1, strOffice);
                System.out.println(strOffice);
                ps.setInt(2, cid1);

                System.out.println("ps is:" + cid1);
                ps.setString(3, strState);
                System.out.println("ps is:" + strState);
                ps.setInt(4, strRegFees);
                System.out.println("ps is:" + strRegFees);
                ps.setDate(5, date1);

                System.out.println("ps is:" + DateTo);
                ps.setTimestamp(6, ts);
                ps.setString(7, "test");

                ps.executeUpdate();

                xml = xml + "<flag>success</flag>";
                ps.close();
            } catch (SQLException e) {


                System.out.println("yes here:" + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";

        }

        else if (strCommand.equalsIgnoreCase("check")) {

            xml = "<response><command>check</command>";

            String strOffice = request.getParameter("officeid");
            String strRegClassId = request.getParameter("classid");
            String strState = request.getParameter("state");

            //int fees=Integer.parseInt(request.getParameter("regfees"));
            int cid2 = 0;
            /*System.out.println("values : " + strCommand + "  " + fees);
      System.out.println("after");*/
            cid2 = Integer.parseInt(strRegClassId);
            try {

                String sql =
                    "select * from PMS_MST_CON_REGN_FEES where Office_Level_Id=? and Regn_Class_Id=? and REGN_STATE_COVERAGE=?";
                System.out.println("after + " + connection);
                ps = connection.prepareStatement(sql);
                System.out.println("sql:" + sql);
                System.out.println("ps : " + ps);
                ps.setString(1, strOffice);
                System.out.println("ps is" + strOffice);
                ps.setInt(2, cid2);
                System.out.println("ps is" + cid2);
                ps.setString(3, strState);
                System.out.println("ps is" + strState);
                //ps.setInt(4,fees);
                ResultSet rs = ps.executeQuery();
                System.out.println(rs);
                if (rs.next()) {

                    System.out.println("inside if");
                    xml = xml + "<flag>success</flag>";
                    xml = xml + "<value> ";
                    xml =
 xml + "<officeid>" + rs.getString("Office_Level_Id") +
   "</officeid><classid>" + rs.getInt("Regn_Class_Id") + "</classid><state>" +
   rs.getString("REGN_STATE_COVERAGE") + "</state>";
                    xml = xml + "</value>";
                } else {
                    xml = xml + "<flag>failure</flag>";
                }
                ps.close();
            } catch (Exception e) {
                System.out.println("exce ****2 vv" + e.getStackTrace());
                System.out.println("exce ****2 vv" + e.getMessage());
                // cannot insert values
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";

        }

        else if (strCommand.equalsIgnoreCase("Get")) {
            System.out.println("inside get");
            xml = "<response><command>Get</command>";

            try {
                String strOffice = request.getParameter("officeid");
                System.out.println(strOffice);
                String strRegClassId1 = request.getParameter("classid");
                System.out.println(strRegClassId1);
                String strState = request.getParameter("state");
                System.out.println(strState);

                int cid3 = 0;
                cid3 = Integer.parseInt(strRegClassId1);


                ps =
  connection.prepareStatement("select * from PMS_MST_CON_REGN_FEES where Office_Level_Id=? and Regn_Class_Id=? and REGN_STATE_COVERAGE=?");
                ps.setString(1, strOffice);
                ps.setInt(2, cid3);
                ps.setString(3, strState);
                results = ps.executeQuery();
                if (results.next()) {
                    Date strdate = results.getDate("Date_effective_from");
                    java.text.SimpleDateFormat sdf =
                        new java.text.SimpleDateFormat("dd/MM/yyyy");
                    String StrDate1 = sdf.format(strdate);
                    xml =
 xml + "<flag>success</flag><officeid>" + results.getString("Office_Level_Id") +
   "</officeid><classid>" + results.getInt("Regn_Class_Id") +
   "</classid><state>" + results.getString("REGN_STATE_COVERAGE") +
   "</state><fees>" + results.getInt("Regn_Fees") + "</fees><date>" +
   StrDate1 + "</date>";
                } else {
                    xml = xml + "<flag>failure</flag>";
                }
                results.close();
            } catch (Exception e) {
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }

        else if (strCommand.equalsIgnoreCase("Update")) {
            // read the parameters
            System.out.println("i am inside update");
            xml = "<response><command>Update</command>";
            String strOffice = "";
            String strRegClassId = "";
            String strState = "";
            int strRegFees = 0;
            Date strDate = null;
            int cid4 = 0;

            try {
                System.out.println("inside try catch");
                strOffice = request.getParameter("RegOffice");
                System.out.println(strOffice);

                strRegClassId = request.getParameter("RegClassId");
                System.out.println(strRegClassId);

                strState = request.getParameter("RegState");
                System.out.println(strState);
                strRegFees = Integer.parseInt(request.getParameter("RegFees"));
                System.out.println(strRegFees);
                cid4 = Integer.parseInt(strRegClassId);
            } catch (Exception e) {
                //System.out.println("exce **** "+ e);
            }


            try {
                System.out.println("inside try catch");
                String sql =
                    "update PMS_MST_CON_REGN_FEES set Regn_Fees=?,Date_effective_from=?,Last_Updated_Date=?,Last_Updated_By=? where Office_Level_Id=? and Regn_Class_Id=? and REGN_STATE_COVERAGE=?";
                //ps=connection.prepareStatement(sql);


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
                System.out.println(date1);

                //java.text.SimpleDateFormat sdf= new java.text.SimpleDateFormat("dd/MM/yyyy");
                ///String DateTo= sdf.format(date1);

                long l = System.currentTimeMillis();
                Timestamp ts = new Timestamp(l);
                System.out.println(ts);

                ps = connection.prepareStatement(sql);
                System.out.println(sql);

                ps.setInt(1, strRegFees);
                System.out.println("ps is:" + strRegFees);
                ps.setDate(2, date1);

                System.out.println("ps is:" + date1);
                ps.setTimestamp(3, ts);
                ps.setString(4, "test");
                ps.setString(5, strOffice);
                ps.setInt(6, cid4);
                ps.setString(7, strState);
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
        }

        else if (strCommand.equalsIgnoreCase("Delete")) {

            System.out.println("inside delete");
            xml = "<response><command>Delete</command>";

            String strOffice = "";
            String strRegClassId2 = "";
            String strState = "";
            int cid5 = 0;
            try {
                strOffice = request.getParameter("RegOffice");
                System.out.println(strOffice);
                strRegClassId2 = request.getParameter("RegClassId");
                System.out.println(strRegClassId2);
                strState = request.getParameter("RegState");
                System.out.println(strState);

                cid5 = Integer.parseInt(strRegClassId2);
            } catch (NumberFormatException e1) {

                System.out.println(e1);

            }


            // delete the row from table
            try {
                String sql =
                    "delete from PMS_MST_CON_REGN_FEES where Office_Level_Id=? and Regn_Class_Id=? and REGN_STATE_COVERAGE=?";
                ps = connection.prepareStatement(sql);
                ps.setString(1, strOffice);
                ps.setInt(2, cid5);
                ps.setString(3, strState);
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
        }

        else if (strCommand.equalsIgnoreCase("Load")) {


            try {
                String strOffice = request.getParameter("RegOffice");
                System.out.println(strOffice);
                String sql =
                    "SELECT PMS_MST_CON_CLASS.REGN_CLASS_ID, PMS_MST_CON_CLASS.REGN_CLASS_DESC, PMS_OFFICE_REGN_CLASS.OFFICE_LEVEL_ID";
                sql = sql + " FROM PMS_MST_CON_CLASS, PMS_OFFICE_REGN_CLASS";
                sql =
 sql + " WHERE PMS_MST_CON_CLASS.REGN_CLASS_ID=PMS_OFFICE_REGN_CLASS.REGN_CLASS_ID AND PMS_OFFICE_REGN_CLASS.OFFICE_LEVEL_ID=? ORDER BY PMS_MST_CON_CLASS.REGN_CLASS_ID";
                ps = connection.prepareStatement(sql);
                ps.setString(1, strOffice);

                results = ps.executeQuery();
                while (results.next()) {
                    xml =
 xml + "<option><id>" + results.getInt("REGN_CLASS_ID") + "</id><desc>" +
   results.getString("Regn_Class_Desc") + "</desc></option>";
                }

                results.close();
            } catch (Exception e) {
                System.out.println(e);
                xml = xml + "<flag>failure</flag>";

            }

            xml = "<select>" + xml + "</select>";

        }


        System.out.println("xml is" + xml);
        pw.write(xml);

        pw.flush();
        pw.close();

    }
}
