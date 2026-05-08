package Servlets.FAS.FAS1.AccountHeadDirectory.servlets;

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

public class Acc_Head_Dir_List_InUse extends HttpServlet {
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
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
            //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

        }
        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                return;
            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }
        String strCommand = "", major_id = "", xml = "";
        System.out.println("from here");
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
  con.prepareStatement("select MAJOR_HEAD_CODE,MINOR_HEAD_CODE,MINOR_HEAD_DESC from  COM_MST_MINOR_HEADS where MAJOR_HEAD_CODE=?");
                ps.setString(1, major_id);
                rs = ps.executeQuery();
                xml = xml + "<flag>success</flag>";
                while (rs.next()) {
                    xml =
 xml + "<Maj_id>" + rs.getString("MAJOR_HEAD_CODE") + "</Maj_id>";
                    xml =
 xml + "<Min_id>" + rs.getInt("MINOR_HEAD_CODE") + "</Min_id>";
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
        PreparedStatement ps2 = null;
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
        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");

            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }
        System.out.println("servlet called");
        String CONTENT_TYPE = "text/xml; charset=windows-1252";
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        String strType = "", xml = "<response>";
        try {
            strType = request.getParameter("Command");
        } catch (Exception e) {
            e.printStackTrace();
        }

        String sql = "";
        if (strType.equals("StartingAlphabets")) 
        {
            String alphaUpperCase =
                request.getParameter("Alphabet").toUpperCase();
            String alphaLowerCase =
                request.getParameter("Alphabet").toLowerCase();
            String usage = request.getParameter("usagestatus"); 
            if(usage.equalsIgnoreCase("InUse"))
            {
                            sql =
                 "select ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC,MAJOR_HEAD_CODE,MINOR_HEAD_CODE,BALANCE_TYPE from COM_MST_ACCOUNT_HEADS where USAGE_STATUS='Y' and ACCOUNT_HEAD_DESC like '" +
                   alphaUpperCase + "%' or ACCOUNT_HEAD_DESC like '" + alphaLowerCase +
                   "%' order by ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC";
            }
            else if(usage.equalsIgnoreCase("NotInUse")) 
            {
                sql =
                "select ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC,MAJOR_HEAD_CODE,MINOR_HEAD_CODE,BALANCE_TYPE from COM_MST_ACCOUNT_HEADS where USAGE_STATUS='N' and ACCOUNT_HEAD_DESC like '" +
                alphaUpperCase + "%' or ACCOUNT_HEAD_DESC like '" + alphaLowerCase +
                "%' order by ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC";
            }
        }

        else if (strType.equals("StartingDigit")) 
        {
            String digit = request.getParameter("Digit").trim();
            String usage = request.getParameter("usagestatus"); 
            if(usage.equalsIgnoreCase("InUse"))
            {
                                sql =
                     "select ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC,MAJOR_HEAD_CODE,MINOR_HEAD_CODE,BALANCE_TYPE from COM_MST_ACCOUNT_HEADS where USAGE_STATUS='Y' and ACCOUNT_HEAD_CODE like '" +
                       digit + "%' order by ACCOUNT_HEAD_CODE";
            }
            else if(usage.equalsIgnoreCase("NotInUse")) 
            {
                sql =
                "select ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC,MAJOR_HEAD_CODE,MINOR_HEAD_CODE,BALANCE_TYPE from COM_MST_ACCOUNT_HEADS where USAGE_STATUS='N' and ACCOUNT_HEAD_CODE like '" +
                digit + "%' order by ACCOUNT_HEAD_CODE";
            }

        } else if (strType.equals("MajorMinor")) 
        {
            String major = request.getParameter("MajorGroup");
            String minor = request.getParameter("MinorGroup").trim();
           String usage="InUse";
        //    String usage = request.getParameter("usagestatus");
           /* System.out.println("major**********"+major);
            System.out.println("minor**********"+minor);
            System.out.println("Usage status *******"+usage);*/
            if(usage.equalsIgnoreCase("InUse"))
            {
                    if (major.equals("All") && minor.equals("All"))  {
                        System.out.println("from here");
                        sql =
         "select ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC,MAJOR_HEAD_CODE,MINOR_HEAD_CODE,BALANCE_TYPE from COM_MST_ACCOUNT_HEADS where USAGE_STATUS='Y'  order by ACCOUNT_HEAD_CODE";
                    } else if (!major.equals("All") && minor.equals("All")) {
                        sql =
         "select ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC,MAJOR_HEAD_CODE,MINOR_HEAD_CODE,BALANCE_TYPE from COM_MST_ACCOUNT_HEADS where USAGE_STATUS='Y' and MAJOR_HEAD_CODE='" +
           major + "'  order by ACCOUNT_HEAD_CODE";
                    } else if (major.equals("All") && !minor.equals("All")) {
                        sql =
         "select ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC,MAJOR_HEAD_CODE,MINOR_HEAD_CODE,BALANCE_TYPE from COM_MST_ACCOUNT_HEADS where USAGE_STATUS='Y' and MINOR_HEAD_CODE='" +
           minor + "'  order by ACCOUNT_HEAD_CODE";
                    } else {
                        sql =
         "select ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC,MAJOR_HEAD_CODE,MINOR_HEAD_CODE,BALANCE_TYPE from COM_MST_ACCOUNT_HEADS where USAGE_STATUS='Y' and MAJOR_HEAD_CODE='" +
           major + "' and MINOR_HEAD_CODE='" + minor + "'  order by ACCOUNT_HEAD_CODE";
                    }
        }
        else if(usage.equalsIgnoreCase("NotInUse")) 
        {
                if (major.equals("All") && minor.equals("All"))  {
                    System.out.println("from here");
                    sql =
                "select ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC,MAJOR_HEAD_CODE,MINOR_HEAD_CODE,BALANCE_TYPE from COM_MST_ACCOUNT_HEADS where USAGE_STATUS='N'  order by ACCOUNT_HEAD_CODE";
                } else if (!major.equals("All") && minor.equals("All")) {
                    sql =
                "select ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC,MAJOR_HEAD_CODE,MINOR_HEAD_CODE,BALANCE_TYPE from COM_MST_ACCOUNT_HEADS where USAGE_STATUS='N' and MAJOR_HEAD_CODE='" +
                major + "'  order by ACCOUNT_HEAD_CODE";
                } else if (major.equals("All") && !minor.equals("All")) {
                    sql =
                "select ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC,MAJOR_HEAD_CODE,MINOR_HEAD_CODE,BALANCE_TYPE from COM_MST_ACCOUNT_HEADS where USAGE_STATUS='N' and MINOR_HEAD_CODE='" +
                minor + "'  order by ACCOUNT_HEAD_CODE";
                } else {
                    sql =
                "select ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC,MAJOR_HEAD_CODE,MINOR_HEAD_CODE,BALANCE_TYPE from COM_MST_ACCOUNT_HEADS where USAGE_STATUS='N' and MAJOR_HEAD_CODE='" +
                major + "' and MINOR_HEAD_CODE='" + minor + "'  order by ACCOUNT_HEAD_CODE";
                }
        }
      }
        else if (strType.equals("Range")) {
            String Upper = request.getParameter("Upper").trim();
            String Lower = request.getParameter("Lower").trim();
            String usage = request.getParameter("usagestatus");
            if(usage.equalsIgnoreCase("InUse"))
            {
                            sql =
                 "select ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC,MAJOR_HEAD_CODE,MINOR_HEAD_CODE,'N' as BALANCE_TYPE from COM_MST_ACCOUNT_HEADS where USAGE_STATUS='Y' and ACCOUNT_HEAD_CODE<='" +
                   Lower + "' and ACCOUNT_HEAD_CODE>='" + Upper +
                   "'  order by ACCOUNT_HEAD_CODE";
            }
            else if(usage.equalsIgnoreCase("NotInUse")) 
            {
                sql =
                "select ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC,MAJOR_HEAD_CODE,MINOR_HEAD_CODE,BALANCE_TYPE from COM_MST_ACCOUNT_HEADS where USAGE_STATUS='N' and ACCOUNT_HEAD_CODE<='" +
                Lower + "' and ACCOUNT_HEAD_CODE>='" + Upper +
                "'  order by ACCOUNT_HEAD_CODE";
            }
        }

        try {
            int No = 0;
            stmt = con.createStatement();
            System.out.println("executing : " + sql);
            PreparedStatement ps3;
            rs = stmt.executeQuery(sql); // Query executed here
            xml =
 xml + "<flag>success</flag><command>" + strType + "</command>";
            System.out.println("yes error");
            while (rs.next()) {
                xml = xml + "<AHCode_leng>";
                xml =
 xml + "<AHCode>" + rs.getInt("ACCOUNT_HEAD_CODE") + "</AHCode>";
                xml =
 xml + "<AHDesc>" + rs.getString("ACCOUNT_HEAD_DESC") + "</AHDesc>";
                //System.out.println("........major.."+rs.getString("MAJOR_HEAD_CODE"));
                //System.out.println("........minor.."+rs.getString("MINOR_HEAD_CODE"));
                ps2 =
 con.prepareStatement("select MAJOR_HEAD_DESC from COM_MST_MAJOR_HEADS where MAJOR_HEAD_CODE=?");
                ps2.setString(1, rs.getString("MAJOR_HEAD_CODE"));
                ResultSet rs1 = ps2.executeQuery();
                if (rs1.next()) {
                    //
                    xml =
 xml + "<Maj_id>" + rs1.getString("MAJOR_HEAD_DESC") + "</Maj_id>";

                    //System.out.println("hai .. 1"+rs1.getString("MAJOR_HEAD_DESC"));
                    ps2.close();
                    rs1.close();
                }
                ps3 =
 con.prepareStatement("select MINOR_HEAD_DESC from COM_MST_MINOR_HEADS where MAJOR_HEAD_CODE=? and  MINOR_HEAD_CODE=? ");
                ps3.setString(1, rs.getString("MAJOR_HEAD_CODE"));
                ps3.setInt(2, rs.getInt("MINOR_HEAD_CODE"));
                rs1 = ps3.executeQuery();
                if (rs1.next()) {
                    //
                    xml =
 xml + "<Min_id>" + rs1.getString("MINOR_HEAD_DESC") + "</Min_id>";
                    //System.out.println("hai .. 2"+rs1.getString("MINOR_HEAD_DESC"));
                    ps3.close();
                    rs1.close();
                }
                xml =
 xml + "<Bal_type>" + rs.getString("BALANCE_TYPE") + "</Bal_type>";
                //System.out.println("hai .. 3"+rs.getString("BALANCE_TYPE"));
                xml = xml + "</AHCode_leng>";
                ++No;
                System.out.println(No);
            }
            System.out.println(No);
            if (No == 0) {
                xml =
 "<response><command>" + strType + "</command><flag>failure</flag><Type>strType</Type>";
            }
        } catch (SQLException sqle) {
            System.out.println("error while fetching data " + sqle);
            xml =
 "<response><command>" + strType + "</command><flag>failure</flag><Type>strType</Type>";
        }
        xml = xml + "</response>";
        out.println(xml);
        System.out.println(xml);
    }
}
