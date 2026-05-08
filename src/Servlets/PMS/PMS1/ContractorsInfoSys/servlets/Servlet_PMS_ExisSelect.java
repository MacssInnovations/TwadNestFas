package Servlets.PMS.PMS1.ContractorsInfoSys.servlets;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.PrintWriter;
import java.io.IOException;

import java.sql.*;

import java.util.ResourceBundle;

public class Servlet_PMS_ExisSelect extends HttpServlet {
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
        response.setContentType("text/xml");
        PrintWriter out = response.getWriter();
        response.setHeader("Cache-control", "no-cache");
        System.out.println("servlet called on selecting the Checkbox details");
        String sql = "";
        int strContId = 0;
        String xml = "";
        try {
            strContId =
                    Integer.parseInt(request.getParameter("ContractorEdit"));
            System.out.println(strContId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        sql =
 "SELECT PMS_MST_CONTR.CONTRACTOR_ID,CONTRACTOR_NAME,COMPANY_NAME,ADDRESS1,ADDRESS2,ADDRESS3,PIN_CODE,PHONE_NO,CELL_NO,EMAIL_ID,DISTRICT_CODE,";
        sql =
 sql + "OFFICE_ID,OFFICE_NAME,REGN_YEAR,REQUEST_SEQ_NO,PMS_CONT_REQUEST_REGN.OFFICE_LEVEL_ID,COM_MST_OFFICE_LEVELS.OFFICE_LEVEL_NAME,REGN_STATUS_ID,PMS_CONT_REQUEST_REGN.REGN_CLASS_ID,PMS_MST_CON_CLASS.REGN_CLASS_DESC,";
        sql =
 sql + "REGN_STATE_COVERAGE,DATE_OF_REGN,RENEWAL_DUE_ON,REF_FILE_NO,REGN_FEES,PMS_CONT_REQUEST_REGN.REMARKS";
        sql =
 sql + " FROM PMS_CONT_REQUEST_REGN, PMS_MST_CONTR,COM_MST_OFFICE_LEVELS, PMS_MST_CON_CLASS";
        sql =
 sql + " WHERE PMS_CONT_REQUEST_REGN.REGN_CLASS_ID=PMS_MST_CON_CLASS.REGN_CLASS_ID AND PMS_CONT_REQUEST_REGN.OFFICE_LEVEL_ID=COM_MST_OFFICE_LEVELS.OFFICE_LEVEL_ID AND PMS_CONT_REQUEST_REGN.CONTRACTOR_ID=PMS_MST_CONTR.CONTRACTOR_ID AND PMS_MST_CONTR.CONTRACTOR_ID=?";

        try {
            //System.out.println("sql executing:" +sql);
            //System.out.println("connection is open : "  + connection);
            ps = connection.prepareStatement(sql);
            ps.setInt(1, strContId);
            System.out.println("ps is" + strContId);
            results = ps.executeQuery();
            //System.out.println("results" + results);
            while (results.next()) {
                //System.out.println("inside while");
                Date strdate = results.getDate("DATE_OF_REGN");
                java.text.SimpleDateFormat sdf =
                    new java.text.SimpleDateFormat("dd/MM/yyyy");
                String StrDate1 = sdf.format(strdate);
                System.out.println(StrDate1);
                Date strdate1 = results.getDate("RENEWAL_DUE_ON");
                java.text.SimpleDateFormat sdf1 =
                    new java.text.SimpleDateFormat("dd/MM/yyyy");
                String StrDate2 = sdf1.format(strdate1);
                System.out.println(StrDate2);

                String rem = results.getString("REMARKS");

                if (rem == null) {
                    rem = "UnDefined Record Found";
                } else {
                    rem = rem;
                }

                //xml=xml+"<flag>success</flag>";
                xml =
 xml + "<cid>" + strContId + "</cid><cname>" + results.getString("CONTRACTOR_NAME") +
   "</cname><comname>" + results.getString("COMPANY_NAME") +
   "</comname><addr1>" + results.getString("ADDRESS1") + "</addr1>";
                xml =
 xml + "<addr2>" + results.getString("ADDRESS2") + "</addr2><addr3>" +
   results.getString("ADDRESS3") + "</addr3><pincode>" +
   results.getInt("PIN_CODE") + "</pincode>";
                xml =
 xml + "<phone>" + results.getInt("PHONE_NO") + "</phone><cellno>" +
   results.getInt("CELL_NO") + "</cellno><email>" +
   results.getString("EMAIL_ID") + "</email><district>" +
   results.getInt("DISTRICT_CODE") + "</district><offid>" +
   results.getInt("OFFICE_ID") + "</offid><offname>" +
   results.getString("OFFICE_NAME") + "</offname>";
                xml =
 xml + "<year>" + results.getInt("REGN_YEAR") + "</year><reqno>" +
   results.getInt("REQUEST_SEQ_NO") + "</reqno><offlevel>" +
   results.getString("OFFICE_LEVEL_ID") + "</offlevel><offlevdesc>" +
   results.getString("OFFICE_LEVEL_NAME") + "</offlevdesc><status>" +
   results.getString("REGN_STATUS_ID") + "</status>";
                xml =
 xml + "<classid>" + results.getInt("REGN_CLASS_ID") + "</classid><classdes>" +
   results.getString("REGN_CLASS_DESC") + "</classdes>";
                xml =
 xml + "<state>" + results.getString("REGN_STATE_COVERAGE") +
   "</state><dateofreg>" + StrDate1 + "</dateofreg><renew>" + StrDate2 +
   "</renew><refno>" + results.getInt("REF_FILE_NO") + "</refno>";
                xml =
 xml + "<regfees>" + results.getInt("REGN_FEES") + "</regfees><remarks>" +
   rem + "</remarks>";

            }


            out.write("<select>" + xml + "</select>");
            //System.out.println("returning"+strContId);
            System.out.println("xml is" + xml);

        } catch (Exception e1) {
            System.out.println("Exception is*****" + e1);

        }

    }
}
