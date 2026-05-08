package Servlets.PMS.PMS1.ContractorsInfoSys.servlets;

import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.Date;

import java.util.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.io.PrintWriter;
import java.io.IOException;

import java.sql.*;

public class ListContractor extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";
    private Connection connection = null;
    private Statement statement = null;
    private ResultSet results = null;
    private PreparedStatement ps = null;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
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

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {

        String strType = "";
        int regno = 0;
        java.sql.Date date1;
        String name = "";
        String sql1 = "";
        String sql2 = "";
        String xml = "";

        PrintWriter pw = response.getWriter();
        response.setContentType("text/xml");
        response.setHeader("cache-control", "no-cache");

        System.out.println("servlet called");

        try {
            strType = request.getParameter("Type");
            System.out.println(strType);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (strType.equals("RegnNo")) {
            System.out.println("inside regnno");
            xml = "<response><command>RegnNo</command>";
            regno = Integer.parseInt(request.getParameter("value"));
            System.out.println(regno);
            try {
                sql1 =
"SELECT Regn_SlNo,Date_of_Regn,Contractor_Name,Address1,Regn_Class_Id,PMS_MST_CONTR.Contractor_Id ";
                sql1 =
sql1 + "FROM PMS_MST_CONT_REGN, PMS_MST_CONTR WHERE PMS_MST_CONT_REGN.Contractor_Id=PMS_MST_CONTR.Contractor_Id And Regn_SlNo=?";
                ps = connection.prepareStatement(sql1);
                System.out.println("Executing sql:" + sql1);
                ps.setInt(1, regno);
                System.out.println("ps is:" + regno);
                ResultSet rs = ps.executeQuery();
                xml = xml + "<flag>success</flag>";

                while (rs.next()) {

                    //Date strdate=results.getDate("Date_of_Regn");
                    //java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("dd/MM/yyyy");
                    //String StrDate1=sdf.format(strdate);

                    xml = xml + "<value>";
                    xml = xml + "<regslno>" + regno + "</regslno>";
                    xml =
 xml + "<date>" + rs.getDate("Date_of_Regn") + "</date>";
                    xml =
 xml + "<cname>" + rs.getString("Contractor_Name") + "</cname>";
                    xml =
 xml + "<addr>" + rs.getString("Address1") + "</addr>";
                    xml =
 xml + "<class>" + rs.getInt("Regn_Class_Id") + "</class>";
                    xml =
 xml + "<cid>" + rs.getInt("Contractor_Id") + "</cid>";
                    xml = xml + "</value>";
                }
                ps.close();
            }

            catch (Exception e) {
                System.out.println("exce ****2 vv" + e);
                // cannot insert values
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";

        }


        else if (strType.equals("RegnDate")) {
            System.out.println("inside date");
            xml = "<response><command>RegnDate</command>";

            String date = request.getParameter("date");
            System.out.println(date);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date d1;
            try {
                d1 = dateFormat.parse(date);
                dateFormat.applyPattern("yyyy-MM-dd");
                date = dateFormat.format(d1);
            } catch (Exception e) {
                e.printStackTrace();
            }

            date1 = java.sql.Date.valueOf(date);

            try {

                sql1 =
"SELECT Regn_SlNo,Date_of_Regn,Contractor_Name,Address1,Regn_Class_Id,PMS_MST_CONTR.Contractor_Id ";
                sql1 =
sql1 + "FROM PMS_MST_CONT_REGN, PMS_MST_CONTR WHERE PMS_MST_CONT_REGN.Contractor_Id=PMS_MST_CONTR.Contractor_Id And Date_of_Regn=?";

                ps = connection.prepareStatement(sql1);
                System.out.println("Executing sql:" + sql1);
                ps.setDate(1, date1);
                System.out.println("ps is:" + date1);
                ResultSet rs = ps.executeQuery();
                xml = xml + "<flag>success</flag>";


                while (rs.next()) {

                    xml = xml + "<value>";
                    xml =
 xml + "<regslno>" + rs.getInt("Regn_SlNo") + "</regslno>";
                    xml = xml + "<date>" + date + "</date>";
                    xml =
 xml + "<cname>" + rs.getString("Contractor_Name") + "</cname>";
                    xml =
 xml + "<addr>" + rs.getString("Address1") + "</addr>";
                    xml =
 xml + "<class>" + rs.getInt("Regn_Class_Id") + "</class>";
                    xml =
 xml + "<cid>" + rs.getInt("Contractor_Id") + "</cid>";
                    xml = xml + "</value>";
                }
                ps.close();
            }

            catch (Exception e) {
                System.out.println("exce ****2 vv" + e);
                // cannot insert values
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";


        }


        else if (strType.equals("CName")) {
            System.out.println("inside name");
            xml = "<response><command>CName</command>";

            name = request.getParameter("name");
            System.out.println("name : " + name);
            try {
                sql1 =
"SELECT Regn_SlNo,Contractor_Name, Date_of_Regn, Address1, Regn_Class_Id, PMS_MST_CONTR.Contractor_Id";
                sql1 = sql1 + " FROM PMS_MST_CONT_REGN, PMS_MST_CONTR";
                sql1 =
sql1 + " WHERE PMS_MST_CONT_REGN.Contractor_Id=PMS_MST_CONTR.Contractor_Id and contractor_name like '" +
 name + "%'";
                ps = connection.prepareStatement(sql1);

                System.out.println("Executing sql:" + sql1);
                System.out.println("ps is:" + name);
                ResultSet rs = ps.executeQuery();

                xml = xml + "<flag>success</flag>";
                while (rs.next()) {
                    //Date date=results.getDate("Date_of_Regn");
                    //System.out.println(date);
                    //java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("dd/MM/yyyy");
                    //String StrDate1=sdf.format(date);

                    xml = xml + "<value>";
                    xml =
 xml + "<regslno>" + rs.getInt("Regn_SlNo") + "</regslno>";
                    xml =
 xml + "<date>" + rs.getDate("Date_of_Regn") + "</date>";
                    xml =
 xml + "<cname>" + rs.getString("Contractor_Name") + "</cname>";
                    xml =
 xml + "<addr>" + rs.getString("Address1") + "</addr>";
                    xml =
 xml + "<class>" + rs.getInt("Regn_Class_Id") + "</class>";
                    xml =
 xml + "<cid>" + rs.getInt("Contractor_Id") + "</cid>";
                    xml = xml + "</value>";
                }
                ps.close();
            }

            catch (Exception e) {
                System.out.println("exce ****2 vv" + e);
                // cannot insert values
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
