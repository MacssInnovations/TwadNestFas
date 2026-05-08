package Servlets.HelpDesk.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.*;

import java.text.SimpleDateFormat;

import java.util.ResourceBundle;

public class ViewAllResponseServlet extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {

        System.out.println("show me");
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet res = null;
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        String xml = "";
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
        } catch (Exception e) {
            System.out.println("Exception in Connection:" + e);
        }
        xml = "<response>";
        System.out.println("xml tag si s:" + xml);
        try {
            connection.clearWarnings();


            String fromdate = request.getParameter("txtfromdate");
            String todate = request.getParameter("txttodate");
            String status = request.getParameter("cmbstatus");
            String major = request.getParameter("cmbmajor");

            java.sql.Date dateOfAttachment = null;
            System.out.println("before converting date");
            String dateString = fromdate;
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date d;
            d = dateFormat.parse(fromdate.trim());
            System.out.println("util date is:" + d);
            dateFormat.applyPattern("yyyy-MM-dd");
            dateString = dateFormat.format(d);
            dateOfAttachment = java.sql.Date.valueOf(dateString);

            java.sql.Date dateto = null;
            System.out.println("before converting date");
            String dateString1 = todate;
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date d1;
            d1 = dateFormat1.parse(todate.trim());
            dateFormat1.applyPattern("yyyy-MM-dd");
            dateString1 = dateFormat1.format(d1);
            dateto = java.sql.Date.valueOf(dateString1);

            System.out.println("fromdate" + dateOfAttachment);
            System.out.println("todate" + dateto);
            String s1 = "", s2 = "";
            if (status.equalsIgnoreCase("A")) {
                s1 = "O";
                s2 = "C";
            } else {
                s1 = status;
                s2 = status;
            }

            System.out.println("Status:" + status);
            System.out.println("s1" + s1);
            System.out.println("s2" + s2);
            System.out.println("s=" + s1 + "  " + s2);
            /* String sql="select a.issue_id,a.ISSUE_REPORTED_DATE,a.ISSUE_PRIORITY,a.ISSUE_SOLUTION,b.MAJOR_SYSTEM_ID,b.major_system_desc,c.MINOR_SYSTEM_ID,c.minor_system_desc,a.ISSUE_TITLE,a.ISSUE_DESC,a.ISSUE_STATUS,a.REPORTED_BY_USER_ID from HLP_ISSUE_REQUESTS a,sec_mst_major_systems b,sec_mst_minor_systems c where a.major_system_id=b.major_system_id and a.minor_system_id=c.minor_system_id " +
            " and (a.ISSUE_REPORTED_DATE between ? and ?) and (a.ISSUE_STATUS=? or a.ISSUE_STATUS=?)   "+
            " order by a.issue_id";
            */

            String sql =
                "select a.issue_id,a.ISSUE_REPORTED_DATE,a.ISSUE_PRIORITY,a.ISSUE_SOLUTION," +
                " b.MAJOR_SYSTEM_ID,b.major_system_desc,c.MINOR_SYSTEM_ID,c.minor_system_desc," +
                " a.ISSUE_TITLE,a.ISSUE_DESC,a.ISSUE_STATUS,a.REPORTED_BY_USER_ID, " +
                " d.employee_name||case when employee_initial is null then ' ' else '.'||employee_initial end as employee_name,d.employee_id,f.office_name " +
                " from HLP_ISSUE_REQUESTS a " +
                " inner join sec_mst_major_systems b on a.major_system_id=b.major_system_id " +
                " inner join sec_mst_minor_systems c on a.minor_system_id=c.minor_system_id" +
                " inner join sec_mst_users g on g.user_id=a.REPORTED_BY_USER_ID " +
                " inner join hrm_mst_employees d on g.employee_id=d.employee_id " +
                " left outer join hrm_emp_current_posting e on e.employee_id=d.employee_id " +
                " left outer join com_mst_offices f on f.office_id=a.office_id " +
                " where   " +
                " (a.ISSUE_REPORTED_DATE between ? and ?) and (a.ISSUE_STATUS=? or a.ISSUE_STATUS=?) and a.MAJOR_SYSTEM_ID=?   " +
                " order by a.issue_id";
            System.out.println("sql:::"+sql);
            ps = connection.prepareStatement(sql);
            ps.setDate(1, dateOfAttachment);
            ps.setDate(2, dateto);
            ps.setString(3, s1);
            ps.setString(4, s2);
            ps.setString(5, major);

            //String sql="select a.issue_id,a.ISSUE_REPORTED_DATE,a.ISSUE_PRIORITY,a.ISSUE_SOLUTION,b.MAJOR_SYSTEM_ID,b.major_system_desc,c.MINOR_SYSTEM_ID,c.minor_system_desc,a.ISSUE_TITLE,a.ISSUE_DESC,a.ISSUE_STATUS,a.REPORTED_BY_USER_ID from HLP_ISSUE_REQUESTS a,sec_mst_major_systems b,sec_mst_minor_systems c where a.major_system_id=b.major_system_id and a.minor_system_id=c.minor_system_id order by a.issue_id";
            //ps=connection.prepareStatement(sql);
            res = ps.executeQuery();

            int i = 0;
            while (res.next()) {
                java.sql.Date DateOfFormation =
                    res.getDate("ISSUE_REPORTED_DATE");
                String DateToBeDisplayed = "";
                if (DateOfFormation == null) {
                    DateToBeDisplayed = "Not Specified";
                } else {
                    try {
                        java.text.SimpleDateFormat sdf =
                            new java.text.SimpleDateFormat("dd/MM/yyyy");
                        DateToBeDisplayed = sdf.format(DateOfFormation);
                    } catch (Exception e) {
                        System.out.println("error while formatting date : " +
                                           e);
                        DateToBeDisplayed = "Not Specified";
                    }
                }
                //   xml=xml+"<Remak><![CDATA["+rs.getString("REMARKS")+"]]></Remak>";
                //response.setContentType("text/xml");
                xml =
 xml + "<options><issueid>" + res.getString("issue_id") + "</issueid><majorsystemdesc>" +
   res.getString("major_system_desc").trim() + "</majorsystemdesc><subject><![CDATA[" + res.getString("Issue_title").trim() + "]]></subject><reportdate>" +
   DateToBeDisplayed + "</reportdate><status>" +
   res.getString("issue_status") + "</status><solution><![CDATA[" +
   res.getString("issue_solution") + "]]></solution><desc><![CDATA[" +
   res.getString("issue_desc") + "]]></desc><empid>" + res.getInt("employee_id") +
   "</empid><empname>" + res.getString("employee_name") +
   "</empname><officename>" + res.getString("office_name") +
   "</officename></options>";
                i++;
            }
            if (i > 0) {
                xml = xml + "<flag>success</flag>";
            } else {
                xml = xml + "<flag>failure</flag>";
            }


        } catch (Exception e) {
            System.out.println("Exception in try :" + e);
        }
        xml = xml + "</response>";
        out.write(xml);
        System.out.println("xml is:" + xml);
        System.out.println("lastline");
        out.close();
    }
}
