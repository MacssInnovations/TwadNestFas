package Servlets.FAS.FAS1.AccountHeadDirectory.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.sql.Statement;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class Acc_Head_Dir_Servlet extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";
    private static final String DOC_TYPE = null;


    public void init(ServletConfig config) throws ServletException {
        super.init(config);

    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        Connection con = null;

        ResultSet rs = null;
        Statement stmt = null;
        PreparedStatement ps = null, ps2 = null;
        try {

            ResourceBundle rs1 =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";

            String strDriver = rs1.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rs1.getString("Config.DSN");
            String strhostname = rs1.getString("Config.HOST_NAME");
            String strportno = rs1.getString("Config.PORT_NUMBER");
            String strsid = rs1.getString("Config.SID");
            String strdbusername = rs1.getString("Config.USER_NAME");
            String strdbpassword = rs1.getString("Config.PASSWORD");
//            ConnectionString =
//                    strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
//                    ":" + strsid.trim();
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
            Class.forName(strDriver.trim());
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
        } catch (Exception e) {
            System.out.println("Exception in openeing connection :" + e);
            //               sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

        }


        String strCommand = "", major_id = "", xml = "";
        try {
            strCommand = request.getParameter("Command");
        } catch (Exception e) {
            System.out.println("In assinging command value in doPost");

        }
        System.out.println();
        if (strCommand.equalsIgnoreCase("loadMinor")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            PrintWriter out = response.getWriter();
            System.out.println("inside minorload....");
            major_id = request.getParameter("txtMajor_id");
            xml = "<response><command>loadMinor</command>";
            try {
                System.out.println("inside try....");
                ps =
  con.prepareStatement("select * from  FAS_MST_MINOR_HEADS where MAJOR_HEAD_CODE=?");
                ps.setString(1, major_id);
                rs = ps.executeQuery();
                xml = xml + "<flag>success</flag>";
                while (rs.next()) {
                    xml =
 xml + "<Maj_id>" + rs.getString("MAJOR_HEAD_CODE") + "</Maj_id>";
                    xml =
 xml + "<Min_id>" + rs.getString("MINOR_HEAD_CODE") + "</Min_id>";
                    xml =
 xml + "<Min_desc>" + rs.getString("MINOR_HEAD_DESC") + "</Min_desc>";
                }
            } catch (Exception e) {
                System.out.println("catch..in..loadMinor::" + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
        }
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        Connection con = null;

        ResultSet rs = null;
        Statement stmt = null;
        PreparedStatement ps = null, ps2 = null;
        try {

            ResourceBundle rs1 =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";

            String strDriver = rs1.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rs1.getString("Config.DSN");
            String strhostname = rs1.getString("Config.HOST_NAME");
            String strportno = rs1.getString("Config.PORT_NUMBER");
            String strsid = rs1.getString("Config.SID");
            String strdbusername = rs1.getString("Config.USER_NAME");
            String strdbpassword = rs1.getString("Config.PASSWORD");
//            ConnectionString =
//                    strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
//                    ":" + strsid.trim();
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
            Class.forName(strDriver.trim());
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
        } catch (Exception e) {
            System.out.println("Exception in openeing connection :" + e);
            //               sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

        }
        System.out.println("servlet called");
        String strType = "";
        try {
            strType = request.getParameter("Type");
        } catch (Exception e) {
            e.printStackTrace();
        }

        String sql = "";
        if (strType.equals("StartingAlphabets")) {
            String alphaUpperCase =
                request.getParameter("Alphabet").toUpperCase();
            String alphaLowerCase =
                request.getParameter("Alphabet").toLowerCase();
            sql =
 "select * from FAS_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_DESC like '" +
   alphaUpperCase + "%' or ACCOUNT_HEAD_DESC like '" + alphaLowerCase + "%'";
        }

        else if (strType.equals("StartingDigit")) {
            String digit = request.getParameter("Digit");
            sql =
 "select * from FAS_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_CODE like '" + digit +
   "%'";

        } else if (strType.equals("MajorMinor")) {
            String major = request.getParameter("MajorGroup");
            String minor = request.getParameter("MinorGroup");
            if (major.equals("All") && minor.equals("All")) {
                sql = "select * from FAS_MST_ACCOUNT_HEADS";
            } else if (!major.equals("All") && minor.equals("All")) {
                sql =
 "select * from FAS_MST_ACCOUNT_HEADS where MAJOR_HEAD_CODE='" + major + "'";
            } else if (major.equals("All") && !minor.equals("All")) {
                sql =
 "select * from FAS_MST_ACCOUNT_HEADS where MINOR_HEAD_CODE='" + minor + "'";
            } else {
                sql =
 "select * from FAS_MST_ACCOUNT_HEADS where MAJOR_HEAD_CODE='" + major +
   "' and MINOR_HEAD_CODE='" + minor + "'";
            }
        } else if (strType.equals("Range")) {
            String Upper = request.getParameter("Upper");
            String Lower = request.getParameter("Lower");
            sql =
 "select * from FAS_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_CODE<='" + Lower +
   "' and ACCOUNT_HEAD_CODE>='" + Upper + "'";
        }

        try {
            response.setContentType(CONTENT_TYPE);
            PrintWriter out = response.getWriter();
            int serialNo = 0;
            stmt = con.createStatement();
            System.out.println("executing : " + sql);
            ResultSet results = stmt.executeQuery(sql);
            out.println("<html>");
            out.println("<head><title>ListServlet</title></head>");
            out.println("<script language=\"javascript\" src=\"" +
                        request.getContextPath() +
                        "/org/FAS/FAS1/AccountHeadDirectory/scripts/UpdateRecords.js\"></script>");
            out.println("<body bgcolor='rgb(255,255,225)'>");
            //out.println("<body bgcolor='Yellow'");
            out.println("<form><table border=\"1\" width=\"100%\">");
            out.println("<th>SI.No.</th><th>A/c Code</th><th>A/c Head</th><th>Major Group</th><th>Minor Group</th><th>Balance Type");
            //out.println("</th><th>Show SL Types</th><th>Delete ?</th><th>Edit ?</th><tbody id=\"tblList\">");
            out.println("</th><tbody id=\"tblList\">");
            while (results.next()) {
                String ahc = results.getString("ACCOUNT_HEAD_CODE");
                out.println("<tr id='" + ahc + "'><td>" + (++serialNo) +
                            "</td>");
                out.println("<td>" + ahc + "</td>");
                out.println("<td>" + results.getString("ACCOUNT_HEAD_DESC") +
                            "</td>");
                out.println("<td>" + results.getString("MAJOR_HEAD_CODE") +
                            "</td>");
                out.println("<td>" + results.getString("MINOR_HEAD_CODE") +
                            "</td>");
                out.println("<td>" + results.getString("Balance_type") +
                            "</td>");
                //out.println("<td><input type=\"checkbox\" id=\"ch1\"  onclick=\"showSubLedgerTypes('" + ahc + "')\"></td>");
                //out.println("<td><input type=\"checkbox\" id=\"ch2\"  onclick=\"deleteAccountHeadCode('" + ahc + "')\"></td>");
                //out.println("<td><input type=\"checkbox\" id=\"ch3\"  onclick=\"EditAccountHeadCode('" + ahc + "')\"></td></tr>");
            }
            out.println("</tbody></table>");
            if (serialNo == 0) {
                // no records found
                out.println("<center><b>Result : No Records Found</b></center>");
            }
            out.println("</form></body></html>");
            out.close();
        } catch (SQLException sqle) {
            System.out.println("error while fetching data " + sqle);
        }

    }
}
