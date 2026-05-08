package Servlets.HelpDesk.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.DriverManager;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.*;


public class ServletResponseDetails_New extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";


    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
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
//
//            ConnectionString =
//                    strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
//                    ":" + strsid.trim();
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection

            Class.forName(strDriver.trim());
            connection =
                    DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                                                strdbpassword.trim());

        } catch (Exception e) {
            System.out.println("Exception in openeing connection:" + e);
        }

        int issue = 0;
        try {
            issue = Integer.parseInt(request.getParameter("IssueId"));
            System.out.println("issue id is" + issue);
        } catch (Exception e) {
            System.out.println("Number Format Exception" + e);
        }

        try {
            String sql1 =
                "select a.ISSUE_REPORTED_DATE,a.last_Updated_date,a.ISSUE_PRIORITY,a.ISSUE_SOLUTION,b.MAJOR_SYSTEM_ID,b.major_system_desc,c.MINOR_SYSTEM_ID,c.minor_system_desc,a.ISSUE_TITLE,a.ISSUE_DESC,a.ISSUE_STATUS,a.REPORTED_BY_USER_ID,a.office_id from HLP_ISSUE_REQUESTS a,sec_mst_major_systems b,sec_mst_minor_systems c where a.major_system_id=b.major_system_id and a.minor_system_id=c.minor_system_id and a.issue_id=?";
            ps = connection.prepareStatement(sql1);
            ps.setInt(1, issue);
            res = ps.executeQuery();
            System.out.println("resultset is" + res);

            if (res.next()) {
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

                java.sql.Date DateOfFormation1 =
                    res.getDate("LAST_UPDATED_DATE");
                String DateToBeDisplayed1 = "";
                if (DateOfFormation == null) {
                    DateToBeDisplayed1 = "null";
                } else {
                    try {
                        java.text.SimpleDateFormat sdf =
                            new java.text.SimpleDateFormat("dd/MM/yyyy");
                        DateToBeDisplayed1 = sdf.format(DateOfFormation1);
                    } catch (Exception e) {
                        System.out.println("error while formatting date : " +
                                           e);
                        DateToBeDisplayed1 = "null";
                    }
                }
                System.out.println("inner");
                response.setContentType("text/xml");
                xml =
 "<response><flag>success</flag><reportdate>" + DateToBeDisplayed +
   "</reportdate><lastdate>" + DateToBeDisplayed1 +
   "</lastdate><issuepriority>" + res.getString("issue_priority") +
   "</issuepriority><majorsystem>" + res.getString("major_system_id") +
   "</majorsystem><majorsystemdesc>" + res.getString("major_system_desc") +
   "</majorsystemdesc><minorsystemid>" + res.getString("minor_system_id") +
   "</minorsystemid><minorsystem>" + res.getString("minor_system_desc") +
   "</minorsystem><issuetitle>" + res.getString("issue_title") +
   "</issuetitle><issuedesc>" + res.getString("issue_desc") +
   "</issuedesc><issuestatus>" + res.getString("issue_status") +
   "</issuestatus><userid>" + res.getString("REPORTED_BY_USER_ID") +
   "</userid><solution>" + res.getString("issue_solution") + "</solution>";


                int officeid = res.getInt("office_id");
                String userid = res.getString("REPORTED_BY_USER_ID");
                String officename = "", empname = "";
                int Emp_Id = 0;
                try {
                    ps =
  connection.prepareStatement("select employee_id from sec_mst_users where user_id=?");
                    ps.setString(1, userid);
                    res = ps.executeQuery();
                    if (res.next()) {
                        Emp_Id = res.getInt("employee_id");
                        System.out.println("employee_id:" + Emp_Id);
                    }


                    ps =
  connection.prepareStatement("select employee_name||case when employee_initial is null then ' ' else '.'||employee_initial end as employee_name from hrm_mst_employees where employee_id=?");
                    ps.setInt(1, Emp_Id);
                    res = ps.executeQuery();
                    if (res.next()) {
                        empname = res.getString("employee_name");
                        System.out.println("emp name:" + empname);
                    }

                    /*  ps=connection.prepareStatement("select a.office_name from com_mst_offices a,hrm_emp_current_posting b where a.office_id=b.office_id and  b.employee_id=?");
                           ps.setInt(1,Emp_Id);
                           res=ps.executeQuery();
                           if(res.next())
                           {
                               officename=res.getString("office_name");
                               System.out.println("office name:"+officename);
                           }*/
                    ps =
  connection.prepareStatement("select a.office_name from com_mst_offices a where a.office_id=?");
                    ps.setInt(1, officeid);
                    res = ps.executeQuery();
                    if (res.next()) {
                        officename = res.getString("office_name");
                        System.out.println("office name:" + officename);
                    }
                } catch (Exception e) {
                    System.out.println("Error in Office Name:" + e);
                }
                xml =
 xml + "<empname>" + empname + "</empname><officename>" + officename +
   "</officename></response>";


            } else {
                response.setContentType("text/xml");
                xml = "<response><flag>failure</flag></response>";
            }

        } catch (Exception e) {
            System.out.println("Exception in try " + e);
        }
        out.write(xml);
        System.out.println("the xml is" + xml);
        out.close();
    }
}
