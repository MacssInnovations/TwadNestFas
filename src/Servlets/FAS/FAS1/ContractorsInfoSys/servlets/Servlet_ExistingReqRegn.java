package Servlets.FAS.FAS1.ContractorsInfoSys.servlets;

import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.Date;

import java.util.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.io.PrintWriter;
import java.io.IOException;

import java.sql.*;

public class Servlet_ExistingReqRegn extends HttpServlet {
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
        String strCommand = "";
        int rsno = 0;
        java.sql.Date date1;
        String name = "";
        String sql1 = "";
        String sql2 = "";
        String xml = "";

        PrintWriter pw = response.getWriter();
        response.setContentType("text/xml");
        response.setHeader("cache-control", "no-cache");

        System.out.println("servlet called for selection");

        try {
            strType = request.getParameter("Type");
            System.out.println(strType);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (strType.equals("ReqSeqNo")) {
            System.out.println("inside ReqSeqNo");
            xml = "<response><command>ReqSeqNo</command>";
            rsno = Integer.parseInt(request.getParameter("value"));
            System.out.println(rsno);
            try {
                sql1 =
"SELECT REQUEST_SEQ_NO,DATE_OF_REGN,CONTRACTOR_NAME,ADDRESS1,PMS_CONT_REQUEST_REGN.REGN_CLASS_ID,PMS_MST_CON_CLASS.REGN_CLASS_DESC,PMS_MST_CONTR.CONTRACTOR_ID ";
                sql1 =
sql1 + "FROM PMS_CONT_REQUEST_REGN, PMS_MST_CONTR, PMS_MST_CON_CLASS";
                sql1 =
sql1 + " WHERE PMS_MST_CON_CLASS.REGN_CLASS_ID=PMS_CONT_REQUEST_REGN.REGN_CLASS_ID AND PMS_CONT_REQUEST_REGN.CONTRACTOR_ID=PMS_MST_CONTR.CONTRACTOR_ID And REQUEST_SEQ_NO=?";
                ps = connection.prepareStatement(sql1);
                System.out.println("Executing sql:" + sql1);
                ps.setInt(1, rsno);
                //System.out.println("ps is:"+rsno);
                ResultSet rs = ps.executeQuery();

                //int i=0;

                while (rs.next()) {

                    //Date strdate=results.getDate("DATE_OF_REGN");
                    //java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("dd/MM/yyyy");
                    //String StrDate1=sdf.format(strdate);
                    xml = xml + "<flag>success</flag>";
                    xml = xml + "<value>";
                    xml = xml + "<rsno>" + rsno + "</rsno>";
                    xml =
 xml + "<date>" + rs.getDate("DATE_OF_REGN") + "</date>";
                    xml =
 xml + "<cname>" + rs.getString("CONTRACTOR_NAME") + "</cname>";
                    xml =
 xml + "<addr>" + rs.getString("ADDRESS1") + "</addr>";
                    xml =
 xml + "<class>" + rs.getString("REGN_CLASS_DESC") + "</class>";
                    xml =
 xml + "<cid>" + rs.getInt("CONTRACTOR_ID") + "</cid>";
                    xml = xml + "</value>";
                }

                ps.close();
            }

            catch (Exception e) {
                System.out.println("exce ****2 vv" + e);
                // cannot insert values
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "<flag>failure</flag>";
            xml = xml + "</response>";

        }


        else if (strType.equals("RegnDate")) {
            // System.out.println("inside date");
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
"SELECT REQUEST_SEQ_NO,DATE_OF_REGN,CONTRACTOR_NAME,ADDRESS1,PMS_CONT_REQUEST_REGN.REGN_CLASS_ID,PMS_MST_CON_CLASS.REGN_CLASS_DESC,PMS_MST_CONTR.CONTRACTOR_ID ";
                sql1 =
sql1 + "FROM PMS_CONT_REQUEST_REGN, PMS_MST_CONTR,PMS_MST_CON_CLASS";
                sql1 =
sql1 + " WHERE PMS_MST_CON_CLASS.REGN_CLASS_ID=PMS_CONT_REQUEST_REGN.REGN_CLASS_ID AND PMS_CONT_REQUEST_REGN.CONTRACTOR_ID=PMS_MST_CONTR.CONTRACTOR_ID And DATE_OF_REGN=? ORDER BY PMS_MST_CONTR.CONTRACTOR_ID";

                ps = connection.prepareStatement(sql1);
                System.out.println("Executing sql:" + sql1);
                ps.setDate(1, date1);
                //System.out.println("ps is:"+date1);
                ResultSet rs = ps.executeQuery();


                while (rs.next()) {

                    xml = xml + "<flag>success</flag>";
                    xml = xml + "<value>";
                    xml =
 xml + "<rsno>" + rs.getInt("REQUEST_SEQ_NO") + "</rsno>";
                    xml = xml + "<date>" + date + "</date>";
                    xml =
 xml + "<cname>" + rs.getString("CONTRACTOR_NAME") + "</cname>";
                    xml =
 xml + "<addr>" + rs.getString("ADDRESS1") + "</addr>";
                    xml =
 xml + "<class>" + rs.getString("REGN_CLASS_DESC") + "</class>";
                    xml =
 xml + "<cid>" + rs.getInt("CONTRACTOR_ID") + "</cid>";
                    xml = xml + "</value>";
                }

            }

            catch (Exception e) {
                //System.out.println("exce ****2 vv"+ e);
                // cannot insert values
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "<flag>failure</flag>";
            xml = xml + "</response>";


        }


        else if (strType.equals("CName")) {
            System.out.println("inside name");
            xml = "<response><command>CName</command>";

            name = request.getParameter("name");
            //System.out.println("name : " + name);
            try {
                sql1 =
"SELECT REQUEST_SEQ_NO,CONTRACTOR_NAME, DATE_OF_REGN, ADDRESS1,PMS_CONT_REQUEST_REGN.REGN_CLASS_ID,PMS_MST_CON_CLASS.REGN_CLASS_DESC, PMS_MST_CONTR.CONTRACTOR_ID";
                sql1 =
sql1 + " FROM PMS_CONT_REQUEST_REGN, PMS_MST_CONTR,PMS_MST_CON_CLASS";
                sql1 =
sql1 + " WHERE PMS_MST_CON_CLASS.REGN_CLASS_ID=PMS_CONT_REQUEST_REGN.REGN_CLASS_ID AND PMS_CONT_REQUEST_REGN.CONTRACTOR_ID=PMS_MST_CONTR.CONTRACTOR_ID and CONTRACTOR_NAME like '" +
 name + "%' ORDER BY PMS_MST_CONTR.CONTRACTOR_ID";
                ps = connection.prepareStatement(sql1);

                // System.out.println("Executing sql:"+sql1);
                //System.out.println("ps is:"+name);
                ResultSet rs = ps.executeQuery();


                while (rs.next()) {
                    //Date date=results.getDate("DATE_OF_REGN");
                    //System.out.println(date);
                    //java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("dd/MM/yyyy");
                    //String StrDate1=sdf.format(date);
                    xml = xml + "<flag>success</flag>";
                    xml = xml + "<value>";
                    xml =
 xml + "<rsno>" + rs.getInt("REQUEST_SEQ_NO") + "</rsno>";
                    xml =
 xml + "<date>" + rs.getDate("DATE_OF_REGN") + "</date>";
                    xml =
 xml + "<cname>" + rs.getString("CONTRACTOR_NAME") + "</cname>";
                    xml =
 xml + "<addr>" + rs.getString("ADDRESS1") + "</addr>";
                    xml =
 xml + "<class>" + rs.getString("REGN_CLASS_DESC") + "</class>";
                    xml =
 xml + "<cid>" + rs.getInt("CONTRACTOR_ID") + "</cid>";
                    xml = xml + "</value>";
                }

                ps.close();
            }

            catch (Exception e) {
                //System.out.println("exce ****2 vv"+ e);
                // cannot insert values
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "<flag>failure</flag>";
            xml = xml + "</response>";


        }


        //System.out.println("xml is : " + xml);

        pw.write(xml);
        pw.flush();
        pw.close();

    }


}
