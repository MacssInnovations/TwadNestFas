package Servlets.HR.HR1.OfficeMaster.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Statement;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class ServletLoadOfficeDet extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {

        System.out.println("servlet called*********");
        response.setContentType(CONTENT_TYPE);
        boolean ErrorOccured = false;
        String ErrorMessage = "";
        int IntID = 0;
        String cmd = "";
        try {
            IntID = Integer.parseInt(request.getParameter("ID"));
            cmd = request.getParameter("command");
            System.out.println("command value is---------------" + cmd);
        } catch (NumberFormatException nfe) {
            ErrorMessage = "Invalid Office ID(Should be Numeric)";
        }

        if (!ErrorOccured) {

            Connection connection = null;
            PreparedStatement ps = null;
            PreparedStatement ps1 = null;
            Statement st = null;
            Statement st1 = null;
            ResultSet results = null;
            ResultSet results1 = null;
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
                System.out.println("cmd is   :" + cmd);
                if (cmd.equals("retrive")) {


                    System.out.println("------------------inside-------------------");
                    System.out.println("-----------" + IntID);
                    try {
                        connection.clearWarnings();
                        String sql =
                            "select a.office_name,b.closed_office_id,b.date_closed from com_mst_offices a,com_office_closure b where a.office_id=b.closed_office_id and a.office_id=?";

                        // st=connection.createStatement();
                        ps = connection.prepareStatement(sql);
                        ps.setInt(1, IntID);
                        System.out.println(" sql is :" + sql + IntID);
                        results = ps.executeQuery();
                        System.out.println(" sql is :" + sql);
                        if (results.next()) {
                            System.out.println(" sql is :" + sql);
                            String xml =
                                "<response><flag>success</flag><name>" +
                                results.getString("Office_Name") +
                                "</name><id>" +
                                results.getInt("closed_office_id") +
                                "</id><date>" +
                                results.getDate("date_closed") + "</date>";
                            xml = xml + "</response>";

                            response.setHeader("cache-control", "no-cache");
                            PrintWriter out = response.getWriter();
                            out.write(xml);
                            System.out.println("xml is : " + xml);
                            //out.close();
                            results.close();
                            ps.close();
                            //     connection.close();
                            return;
                        } else {
                            ErrorOccured = true;
                            ErrorMessage = "Invalid ID,Record not found.";
                        }
                        results.close();
                        ps.close();
                        // connection.close();
                    } catch (SQLException e) {
                        System.out.println("Exception in creating statement:" +
                                           e);
                        ErrorOccured = true;
                        ErrorMessage = e.getMessage();
                    }


                }


                else if (cmd.equals("ClosedOffice")) {


                    System.out.println("------------------inside-------------------");
                    System.out.println("-----------" + IntID);
                    try {
                        connection.clearWarnings();
                        String sql =
                            "select c.OFFICE_NAME,a.CLOSED_OFFICE_ID,a.DATE_CLOSED  from COM_OFFICE_closure a,com_office_closed_records b,com_mst_offices c where a.CLOSED_OFFICE_ID=b.OLD_REC_OFFICE_ID and c.OFFICE_ID=b.OFFICE_ID and c.OFFICE_ID=?";

                        // st=connection.createStatement();
                        ps = connection.prepareStatement(sql);
                        ps.setInt(1, IntID);
                        System.out.println(" sql is :" + sql + IntID);
                        results = ps.executeQuery();


                        System.out.println(" sql is :" + sql);
                        /*   if(results.next()) {
                             System.out.println(" sql is :"+sql);
                             String xml="<response><flag>success</flag><name>" + results.getString("Office_Name") + "</name><id>"+results.getInt("closed_office_id")+"</id><date>"+results.getDate("date_closed")+"</date>";
                             xml=xml+"</response>";
                        */
                        String xml = "<response><flag>success</flag>";
                        while (results.next()) {
                            xml +=
"<name>" + results.getString("Office_Name") + "</name><id>" +
 results.getInt("closed_office_id") + "</id><date>" +
 results.getDate("date_closed") + "</date>";
                        }
                        xml = xml + "</response>";
                        response.setHeader("cache-control", "no-cache");
                        PrintWriter out = response.getWriter();
                        out.write(xml);
                        System.out.println("xml is : " + xml);
                        //out.close();
                        results.close();
                        ps.close();
                        //     connection.close();
                        return;
                        /* }
                         else {
                             ErrorOccured=true;
                             ErrorMessage="Invalid ID,Record not found.";
                         }*/
                        //results.close();
                        //ps.close();
                        // connection.close();
                    } catch (SQLException e) {
                        System.out.println("Exception in creating statement:" +
                                           e);
                        ErrorOccured = true;
                        ErrorMessage = e.getMessage();
                    }


                }

                else if (cmd.equals("InitGrid")) {


                    System.out.println("------------------inside------GRID------------");
                    System.out.println("-----------" + IntID);
                    try {
                        connection.clearWarnings();
                        //String sql="select c.OFFICE_NAME,a.CLOSED_OFFICE_ID,a.DATE_CLOSED  from COM_OFFICE_closure a,com_office_closed_records b,com_mst_offices c where a.CLOSED_OFFICE_ID=b.OLD_REC_OFFICE_ID and c.OFFICE_ID=b.OFFICE_ID and c.OFFICE_ID=?";
                        String sql =
                            "select c.OFFICE_NAME,a.CLOSED_OFFICE_ID,a.DATE_CLOSED,b.DATE_EFFECTIVE_FROM,b.REMARKS from COM_OFFICE_closure a,com_office_closed_records b,com_mst_offices c where a.CLOSED_OFFICE_ID=b.OLD_REC_OFFICE_ID and c.OFFICE_ID=b.OFFICE_ID and c.OFFICE_ID=?";
                        // st=connection.createStatement();
                        ps = connection.prepareStatement(sql);
                        ps.setInt(1, IntID);
                        System.out.println(" sql is :" + sql + IntID);
                        results = ps.executeQuery();


                        System.out.println(" sql is :" + sql);
                        /*   if(results.next()) {
                                 System.out.println(" sql is :"+sql);
                                 String xml="<response><flag>success</flag><name>" + results.getString("Office_Name") + "</name><id>"+results.getInt("closed_office_id")+"</id><date>"+results.getDate("date_closed")+"</date>";
                                 xml=xml+"</response>";
                            */
                        String xml = "<response><flag>success</flag>";
                        while (results.next()) {

                            //String date_closed=;
                            //String date_effective=;
                            java.sql.Date DateOfFormation = results.getDate(3);
                            String DateToBeDisplayed = "";
                            if (DateOfFormation == null) {
                                DateToBeDisplayed = "Not Specified";
                            } else {
                                try {
                                    java.text.SimpleDateFormat sdf =
                                        new java.text.SimpleDateFormat("dd/MM/yyyy");
                                    DateToBeDisplayed =
                                            sdf.format(DateOfFormation);
                                } catch (Exception e) {
                                    System.out.println("error while formatting date : " +
                                                       e);
                                    DateToBeDisplayed = "Not Specified";
                                }
                            }
                            System.out.println("first Date-------->>>>  " +
                                               DateToBeDisplayed);

                            java.sql.Date DateOfFormation1 =
                                results.getDate(4);
                            String DateToBeDisplayed1 = "";
                            if (DateOfFormation1 == null) {
                                DateToBeDisplayed1 = "Not Specified";
                            } else {
                                try {
                                    java.text.SimpleDateFormat sdf1 =
                                        new java.text.SimpleDateFormat("dd/MM/yyyy");
                                    DateToBeDisplayed1 =
                                            sdf1.format(DateOfFormation1);
                                } catch (Exception e) {
                                    System.out.println("error while formatting date : " +
                                                       e);
                                    DateToBeDisplayed1 = "Not Specified";
                                }
                            }

                            System.out.println("first Date-------->>>>  " +
                                               DateToBeDisplayed1);


                            // xml+="<name>"+results.getString("Office_Name") + "</name><id>"+results.getInt("closed_office_id")+"</id><date>"+results.getDate("date_closed")+"</date>";
                            xml +=
"<name>" + results.getString("Office_Name") + "</name><id>" +
 results.getInt("closed_office_id") + "</id><date>" + DateToBeDisplayed +
 "</date><date2>" + DateToBeDisplayed1 + "</date2><remrk>" +
 results.getString("REMARKS") + "</remrk>";
                        }
                        xml = xml + "</response>";
                        response.setHeader("cache-control", "no-cache");
                        PrintWriter out = response.getWriter();
                        out.write(xml);
                        System.out.println("xml is : " + xml);
                        //out.close();
                        results.close();
                        ps.close();
                        //     connection.close();
                        return;
                        /* }
                             else {
                                 ErrorOccured=true;
                                 ErrorMessage="Invalid ID,Record not found.";
                             }*/
                        //results.close();
                        //ps.close();
                        // connection.close();
                    } catch (SQLException e) {
                        System.out.println("Exception in creating statement:" +
                                           e);
                        ErrorOccured = true;
                        ErrorMessage = e.getMessage();
                    }


                }

                else if (cmd.equals("InitGridForReq")) {


                    System.out.println("------------------inside--req----GRID------------");
                    System.out.println("-----------" + IntID);
                    try {
                        connection.clearWarnings();

                        String sql =
                            "select SECONDARY_WORK_NATURE_ID,REMARKS from com_office_new_addl_works where NEW_OFFICE_REQUEST_ID=?";
                        // st=connection.createStatement();
                        ps = connection.prepareStatement(sql);
                        ps.setInt(1, IntID);
                        System.out.println(" sql is :" + sql + IntID);
                        results = ps.executeQuery();

                        String xml = "<response><flag>success</flag>";
                        while (results.next()) {
                            System.out.println("---------entered---------------");
                            //String date_closed=;
                            //String date_effective=;

                            // xml+="<name>"+results.getString("Office_Name") + "</name><id>"+results.getInt("closed_office_id")+"</id><date>"+results.getDate("date_closed")+"</date>";
                            // xml+="<name>"+results.getString("SECONDARY_WORK_NATURE_ID") + "</name><id>"+results.getInt("closed_office_id")+"</id><date>"+DateToBeDisplayed+"</date><date2>"+DateToBeDisplayed1+"</date2><remrk>"+results.getString("REMARKS")+"</remrk>";
                            xml +=
"<SecondaryWork>" + results.getString("SECONDARY_WORK_NATURE_ID") +
 "</SecondaryWork><remrk>" + results.getString("REMARKS") + "</remrk>";
                            System.out.println("the XML is -----:" + xml);


                        }

                        System.out.println("first part retrival---------------------");

                        String sql1 =
                            "select  NEW_OFFICE_NAME,NEW_OFFICE_SHORT_NAME,NEW_OFFICE_LEVEL_ID,PRIMARY_WORK_NATURE_ID,DATE_OF_REQUEST,REMARKS from com_office_new_requests where NEW_OFFICE_REQUEST_ID=?";
                        ps1 = connection.prepareStatement(sql1);
                        ps1.setInt(1, IntID);
                        System.out.println(" sql is :" + sql + IntID);
                        results1 = ps1.executeQuery();
                        while (results1.next()) {
                            System.out.println("---------entered----second sql----------");

                            xml +=
"<Office_Name>" + results1.getString("NEW_OFFICE_NAME") +
 "</Office_Name><Short_Name>" + results1.getString("NEW_OFFICE_SHORT_NAME") +
 "</Short_Name><Level_Id>" + results1.getString("NEW_OFFICE_LEVEL_ID") +
 "</Level_Id><Primary_Nature>" + results1.getString("PRIMARY_WORK_NATURE_ID") +
 "</Primary_Nature><date>" + results1.getDate("DATE_OF_REQUEST") +
 "</date><rem>" + results1.getString("REMARKS") + "</rem>";


                        }


                        System.out.println("the SEcond  XML is -----:" + xml);


                        xml = xml + "</response>";
                        response.setHeader("cache-control", "no-cache");
                        PrintWriter out = response.getWriter();
                        out.write(xml);
                        System.out.println("xml is : " + xml);
                        //out.close();
                        results.close();
                        ps.close();
                        //     connection.close();
                        return;

                    } catch (SQLException e) {
                        System.out.println("Exception in creating statement:" +
                                           e);
                        ErrorOccured = true;
                        ErrorMessage = e.getMessage();
                    }


                } else if (cmd.equals("nil")) {
                    try {
                        connection.clearWarnings();

                        System.out.println("servlet  **** CL___>>>>>>>     :");

                        //String sql="select office_name,office_address1,office_address2,city_town_name,district_code,Work_Nature_Desc,Office_Level_Name from com_mst_offices,COM_MST_OFFICE_LEVELS,COM_MST_WORK_NATURE  where com_mst_offices.Office_Level_Id=COM_MST_OFFICE_LEVELS.Office_Level_Id and com_mst_offices.Primary_Work_Id=COM_MST_WORK_NATURE.Work_Nature_Id and Office_Id=?";

                        String sql =
                            "select office_name,office_address1,office_address2,city_town_name,district_code from com_mst_offices where Office_Id=?";

                        ps = connection.prepareStatement(sql);
                        ps.setInt(1, IntID);
                        results = ps.executeQuery();
                        if (results.next()) {
                            /* String xml="<response><flag>success</flag><name>" + results.getString("Office_Name") + "</name><address1>"+results.getString("office_address1")+"</address1><address2>"+results.getString("office_address2")+"</address2>";
                            xml=xml + "<address>" + results.getString("CITY_TOWN_NAME") +"</address><district>"+results.getInt("district_code")+"</district><city>"+results.getString("city_town_name");
                            xml=xml+"</city><level>" + results.getString("Office_Level_Name") + "</level><type>" + results.getString("Work_Nature_Desc") + "</type></response>";
                            */

                            String xml =
                                "<response><flag>success</flag><name>" +
                                results.getString("Office_Name") +
                                "</name><address1>" +
                                results.getString("office_address1") +
                                "</address1><address2>" +
                                results.getString("office_address2") +
                                "</address2>";
                            xml =
 xml + "<address>" + results.getString("CITY_TOWN_NAME") +
   "</address><district>" + results.getInt("district_code") +
   "</district><city>" + results.getString("city_town_name");
                            xml =
 xml + "</city><level></level><type></type></response>";


                            response.setContentType(CONTENT_TYPE);
                            response.setHeader("cache-control", "no-cache");
                            PrintWriter out = response.getWriter();
                            out.write(xml);
                            System.out.println("xml is : " + xml);
                            out.close();
                            results.close();
                            ps.close();
                            connection.close();
                            return;
                        } else {
                            ErrorOccured = true;
                            ErrorMessage = "Invalid ID,Record not found.";
                        }
                        results.close();
                        ps.close();
                        connection.close();
                    } catch (SQLException e) {
                        System.out.println("Exception in creating statement:" +
                                           e);
                        ErrorOccured = true;
                        ErrorMessage = e.getMessage();
                    }


                }


                else {
                    try {
                        connection.clearWarnings();

                        // System.out.println("servlet  **** CL___>>>>>>>     :");

                        String sql =
                            "select office_name,office_address1,office_address2,city_town_name,district_code,Work_Nature_Desc,Office_Level_Name from com_mst_offices,COM_MST_OFFICE_LEVELS,COM_MST_WORK_NATURE  where com_mst_offices.Office_Level_Id=COM_MST_OFFICE_LEVELS.Office_Level_Id and com_mst_offices.Primary_Work_Id=COM_MST_WORK_NATURE.Work_Nature_Id and Office_Id=?";
                        ps = connection.prepareStatement(sql);
                        ps.setInt(1, IntID);
                        results = ps.executeQuery();
                        if (results.next()) {
                            String xml =
                                "<response><flag>success</flag><name>" +
                                results.getString("Office_Name") +
                                "</name><address1>" +
                                results.getString("office_address1") +
                                "</address1><address2>" +
                                results.getString("office_address2") +
                                "</address2>";
                            xml =
 xml + "<address>" + results.getString("CITY_TOWN_NAME") +
   "</address><district>" + results.getInt("district_code") +
   "</district><city>" + results.getString("city_town_name");
                            xml =
 xml + "</city><level>" + results.getString("Office_Level_Name") +
   "</level><type>" + results.getString("Work_Nature_Desc") +
   "</type></response>";
                            response.setContentType(CONTENT_TYPE);
                            response.setHeader("cache-control", "no-cache");
                            PrintWriter out = response.getWriter();
                            out.write(xml);
                            System.out.println("xml is : " + xml);
                            out.close();
                            results.close();
                            ps.close();
                            connection.close();
                            return;
                        } else {
                            ErrorOccured = true;
                            ErrorMessage = "Invalid ID,Record not found.";
                        }
                        results.close();
                        ps.close();
                        connection.close();
                    } catch (SQLException e) {
                        System.out.println("Exception in creating statement:" +
                                           e);
                        ErrorOccured = true;
                        ErrorMessage = e.getMessage();
                    }


                }
                //--------
            } catch (Exception e) {
                System.out.println("Exception in openeing connection:" + e);
                ErrorOccured = true;
                ErrorMessage = e.getMessage();
            }


        }
        String xml =
            "<response><flag>failed</flag><message>" + ErrorMessage + "</message></response>";
        response.setContentType(CONTENT_TYPE);
        response.setHeader("cache-control", "no-cache");
        PrintWriter out = response.getWriter();

        System.out.println("xml is : " + xml);
        out.write(xml);
        out.close();
    }
}


