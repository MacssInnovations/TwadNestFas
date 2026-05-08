package Servlets.HR.HR1.EmployeeMaster.servlets;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class AutoRelievalOnRetirementServ extends HttpServlet {
    private static final String CONTENT_TYPE = "text/xml; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();

        Connection con = null;
        ResultSet resultset = null;
        PreparedStatement ps = null;
        ResultSet resultset1 = null;
        PreparedStatement ps1 = null;
        ResultSet rsnew = null;
        PreparedStatement psnew = null;
        CallableStatement cs = null;
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
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
        } catch (Exception e) {
            System.out.println("Exception in connection..." + e);
        }
        int cnt = 0;
        String command = request.getParameter("Command");
        String curYear = request.getParameter("curYear");
        int curYearInt = Integer.parseInt(curYear);
        String curMonth = request.getParameter("curMonth");
        int curMonthInt = Integer.parseInt(curMonth);
        System.out.println("year is" + curYearInt + "Month is :" +
                           curMonthInt);

        //String selectedEmpid=request.getParameter("selected");
        // System.out.println("");
        String xml = "<response>";
        if (command.equalsIgnoreCase("loadEmployee")) {
            xml = xml + "<command>loadEmployee</command>";
            try {
                System.out.println("before query");
                ps =
  con.prepareStatement("select * from" + " (" + " select EMPLOYEE_ID,EMPLOYEE_NAME,DATE_OF_BIRTH,RETIREDATE,retmonth,retyear,office_id,employee_status_desc," +
                       " office_name,employee_status_id from" + " (" +
                       " select EMPLOYEE_ID,EMPLOYEE_NAME,DATE_OF_BIRTH,RETIREDATE,retyear,office_name,to_number(extract(month from RETIREDATE)) as retmonth from ALLRETIREMENTVIEW" +
                       " )a" + " left outer join" + " (" +
                       " select employee_status_id, employee_id as empid,office_id from hrm_emp_current_posting " +
                       " )b" + " on a.employee_id=b.empid" +
                       " left outer join " + " (" +
                       " select employee_status_id as empstatusid,employee_status_desc from hrm_mst_employee_status" +
                       " )c" + " on b.employee_status_id=c.empstatusid" +
                       " )" + " where retmonth=? and retyear=?");
                ps.setInt(1, curMonthInt);
                ps.setInt(2, curYearInt);
                System.out.println("before executing query");
                resultset = ps.executeQuery();
                System.out.println("After executing query");

                while (resultset.next()) {
                    //System.out.println("inside resultset");
                    int empid = resultset.getInt("EMPLOYEE_ID");
                    xml = xml + "<details>";
                    xml =
 xml + "<empid>" + resultset.getInt("EMPLOYEE_ID") + "</empid>";
                    xml =
 xml + "<empname>" + resultset.getString("EMPLOYEE_NAME") + "</empname>";
                    xml =
 xml + "<empstatus>" + resultset.getString("employee_status_desc") +
   "</empstatus>";
                    xml =
 xml + "<empoffice>" + ((resultset.getString("office_name") == "null") ? " " :
                        resultset.getString("office_name")) + "</empoffice>";
                    xml =
 xml + "<empdob>" + resultset.getDate("DATE_OF_BIRTH") + "</empdob>";
                    xml =
 xml + "<empdor>" + resultset.getDate("RETIREDATE") + "</empdor>";
                    xml =
 xml + "<empofficeid>" + resultset.getInt("office_id") + "</empofficeid>";

                    try {
                        ps1 =
 con.prepareStatement("select employee_status_id from hrm_emp_current_posting where employee_id=?");
                        ps1.setInt(1, empid);
                        resultset1 = ps1.executeQuery();

                        if (resultset1.next()) {
                            String empstatid =
                                resultset1.getString("employee_status_id");
                            if (empstatid.equals("VRS") ||
                                empstatid.equals("DTH") ||
                                empstatid.equals("SAN")) {
                                xml = xml + "<relieved>yes</relieved>";
                            } else {
                                xml = xml + "<relieved>no</relieved>";
                            }
                        } else {
                            xml = xml + "<relieved>no</relieved>";
                        }
                    } catch (Exception e) {
                        System.out.println("Exception in relieval checking " +
                                           e);
                    }

                    xml = xml + "</details>";
                    cnt++;
                }
                if (cnt != 0) {
                    xml = xml + "<flag>success</flag>";
                } else {
                    xml = xml + "<flag>failure</flag>";
                }
            } catch (Exception e) {
                System.out.println("Error in getting the employee details " +
                                   e);
            }
            xml = xml + "</response>";
            System.out.println("xml is :" + xml);
            out.println(xml);
        }

        if (command.equalsIgnoreCase("relieveEmployee")) {
            // xml=xml+"<command>relieveEmployee</command>";

            String selectedEmpid = request.getParameter("selected");
            System.out.println("selected employee id is" + selectedEmpid);
            String strselempid[] = selectedEmpid.split(",");
            java.sql.Date retdate = null;
            //System.out.println();
            //int empidInt[]=new int[strselempid.length];
            for (int i = 0; i < strselempid.length; i++) {
                System.out.println("employee ids :" + strselempid[i]);
                int empidInt = Integer.parseInt(strselempid[i]);
                System.out.println("employee ids in integer :" + empidInt);
                try {
                    psnew =
con.prepareStatement("select retiredate from ALLRETIREMENTVIEW where employee_id=?");
                    psnew.setInt(1, empidInt);
                    rsnew = psnew.executeQuery();
                    while (rsnew.next()) {
                        retdate = rsnew.getDate("retiredate");
                        System.out.println("Retirement Date of Employee:" +
                                           retdate);
                    }

                } catch (Exception e) {
                    System.out.println("exception in getting retierement date" +
                                       e);
                }
                try {
                    System.out.println("inside procedure");
                    System.out.println("before query");
                    cs =
  con.prepareCall("{call hrm_auto_relieve_on_retire(?,?)}");
                    cs.setInt(1, empidInt);
                    cs.setDate(2, retdate);

                    cs.execute();
                    sendMessage(response,
                                " The Employees has been Relieved successfully.",
                                "ok");
                    System.out.println("After executing query");
                    // response.sendRedirect("");

                } catch (Exception e) {
                    System.out.println("Error in getting the employee details " +
                                       e);
                }
            }
            // xml=xml+"</response>";
            // System.out.println("xml is :"+xml);
            // out.println(xml);
        }

        out.close();
    }

    private void sendMessage(HttpServletResponse response, String msg,
                             String bType) {
        try {
            String url =
                "org/Library/jsps/Messenger.jsp?message=" + msg + "&button=" +
                bType;
            response.sendRedirect(url);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

}
