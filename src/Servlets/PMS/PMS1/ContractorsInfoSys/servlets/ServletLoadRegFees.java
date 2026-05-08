package Servlets.PMS.PMS1.ContractorsInfoSys.servlets;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.PrintWriter;
import java.io.IOException;

import java.sql.*;

import java.util.ResourceBundle;

public class ServletLoadRegFees extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";
    private Connection connection = null;
    private Statement statement = null;
    private ResultSet results = null;

    private PreparedStatement ps = null;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
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
        System.out.println("servlet called*******");


        try {
            strCommand = request.getParameter("command");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (strCommand.equals("Display")) {
            xml = "<response><command>Display</command>";
            try {
                System.out.println("inside Display");
                String sql =
                    "SELECT COM_MST_OFFICE_LEVELS.OFFICE_LEVEL_ID,COM_MST_OFFICE_LEVELS.OFFICE_LEVEL_NAME,PMS_MST_CON_CLASS.REGN_CLASS_ID,PMS_MST_CON_CLASS.REGN_CLASS_DESC,PMS_MST_CON_REGN_FEES.REGN_STATE_COVERAGE,";
                sql =
 sql + "PMS_MST_CON_REGN_FEES.REGN_FEES,PMS_MST_CON_REGN_FEES.DATE_EFFECTIVE_FROM";
                sql =
 sql + " FROM PMS_MST_CON_CLASS, PMS_MST_CON_REGN_FEES, COM_MST_OFFICE_LEVELS";
                sql =
 sql + " WHERE PMS_MST_CON_CLASS.REGN_CLASS_ID=PMS_MST_CON_REGN_FEES.REGN_CLASS_ID";
                sql =
 sql + " AND COM_MST_OFFICE_LEVELS.OFFICE_LEVEL_ID=PMS_MST_CON_REGN_FEES.OFFICE_LEVEL_ID";
                sql = sql + " ORDER BY PMS_MST_CON_CLASS.REGN_CLASS_ID";
                results = statement.executeQuery(sql);

                try {
                    xml = xml + "<flag>success</flag>";
                    while (results.next()) {
                        Date strdate = results.getDate("Date_effective_from");
                        java.text.SimpleDateFormat sdf =
                            new java.text.SimpleDateFormat("dd/MM/yyyy");
                        String StrDate1 = sdf.format(strdate);


                        xml =
 xml + "<value><offid>" + results.getString("OFFICE_LEVEL_ID") + "</offid>";
                        xml =
 xml + "<cid>" + results.getInt("REGN_CLASS_ID") + "</cid><cdesc>" +
   results.getString("REGN_CLASS_DESC") + "</cdesc><state>" +
   results.getString("REGN_STATE_COVERAGE") + "</state>";
                        xml =
 xml + "<fees>" + results.getInt("REGN_FEES") + "</fees><date>" + StrDate1 +
   "</date><offdesc>" + results.getString("OFFICE_LEVEL_NAME") +
   "</offdesc></value>";

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


        System.out.println(xml);
        pw.write(xml);

        pw.flush();
        pw.close();


    }
}
