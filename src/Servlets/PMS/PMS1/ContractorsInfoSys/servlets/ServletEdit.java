package Servlets.PMS.PMS1.ContractorsInfoSys.servlets;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.PrintWriter;
import java.io.IOException;

import java.sql.*;
import java.sql.Date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ResourceBundle;


public class ServletEdit extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";
    private Connection connection = null;
    private Statement statement = null;
    private ResultSet results = null;
    private PreparedStatement ps = null;


    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        // opening connection to the database
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
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();

        response.setHeader("Cache-control", "no-cache");
        System.out.println("servlet called");
        String sql = "";
        int strContId = 0;
        try {
            strContId =
                    Integer.parseInt(request.getParameter("ContractorEdit"));
            System.out.println(strContId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        sql =
 "SELECT PMS_MST_CONTR.Contractor_Id,Contractor_Name,Company_Name,Address1,Address2,Address3,Pin_Code,Phone_No,Cell_No,Email_Id,District_Code,";
        sql =
 sql + "Office_Id,Regn_Year,Regn_SlNo,Regn_Class_Id,Entire_State,Date_of_Regn,Regn_Fees_Receipt_No,Regn_Fees FROM PMS_MST_CONT_REGN, PMS_MST_CONTR";
        sql =
 sql + " WHERE PMS_MST_CONT_REGN.Contractor_Id=PMS_MST_CONTR.Contractor_Id And PMS_MST_CONTR.Contractor_Id=?";
        String xml = "";
        try {
            System.out.println("sql executing:" + sql);
            System.out.println("connection is open : " + connection);
            ps = connection.prepareStatement(sql);
            ps.setInt(1, strContId);
            System.out.println("ps is" + strContId);
            results = ps.executeQuery();
            System.out.println("results" + results);
            while (results.next()) {
                System.out.println("inside while");
                Date strdate = results.getDate("Date_of_Regn");
                java.text.SimpleDateFormat sdf =
                    new java.text.SimpleDateFormat("dd/MM/yyyy");
                String StrDate1 = sdf.format(strdate);
                xml =
 xml + "<cid>" + strContId + "</cid><cname>" + results.getString("Contractor_Name") +
   "</cname><comname>" + results.getString("Company_Name") +
   "</comname><addr1>" + results.getString("Address1") + "</addr1>";
                xml =
 xml + "<addr2>" + results.getString("Address2") + "</addr2><addr3>" +
   results.getString("Address3") + "</addr3><pincode>" +
   results.getInt("Pin_Code") + "</pincode>";
                xml =
 xml + "<phone>" + results.getInt("Phone_No") + "</phone><cellno>" +
   results.getInt("Cell_No") + "</cellno><email>" +
   results.getString("Email_Id") + "</email><district>" +
   results.getInt("District_Code") + "</district><offid>" +
   results.getInt("Office_Id") + "</offid>";
                xml =
 xml + "<year>" + results.getInt("Regn_Year") + "</year><regno>" +
   results.getInt("Regn_SlNo") + "</regno><class>" +
   results.getInt("Regn_Class_Id") + "</class><state>" +
   results.getString("Entire_State") + "</state><dateofreg>" + StrDate1 +
   "</dateofreg><refno>" + results.getInt("Regn_Fees_Receipt_No") + "</refno>";
                xml =
 xml + "<regfees>" + results.getInt("Regn_Fees") + "</regfees>";

            }


            out.write("<select>" + xml + "</select>");
            System.out.println("returning" + strContId);
            System.out.println("xml is" + xml);

        } catch (Exception e1) {
            System.out.println("Exception is*****" + e1);
        }


    }


}


