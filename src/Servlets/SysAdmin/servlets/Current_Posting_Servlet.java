package Servlets.SysAdmin.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.*;

import java.text.SimpleDateFormat;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class Current_Posting_Servlet extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";
    private Connection connection = null;
    private Statement statement = null;
    private ResultSet results = null;
    private ResultSet rs4 = null;
    private ResultSet rs3 = null;
    private PreparedStatement ps = null;
    private PreparedStatement ps1 = null;
    private PreparedStatement ps2 = null;
    private PreparedStatement ps3 = null;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
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
            connection =
                    DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                                                strdbpassword.trim());
            System.out.println("Connection String" + connection);
            try {
                statement = connection.createStatement();
                connection.clearWarnings();
            } catch (SQLException e) {
                System.out.println("*************Exception in creating statement*********:" +
                                   e);
            }
        } catch (Exception e) {
            System.out.println("________Exception in opening connection:_______________" +
                               e);
        }
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);
        String strCommand = "";
        String xml = "";
        response.setContentType("text/xml");
        PrintWriter pw = response.getWriter();
        response.setHeader("Cache-Control", "no-cache");
        try {
            strCommand = request.getParameter("command");
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (strCommand.equalsIgnoreCase("check")) {

            xml = "<response><command>check</command>";

            int strEmpName = Integer.parseInt(request.getParameter("EName"));

            try {

                String sql =
                    "select * from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?";
                ps = connection.prepareStatement(sql);
                ps.setInt(1, strEmpName);
                ResultSet rs = ps.executeQuery();
                int j = 0;
                while (rs.next()) {

                    // xml=xml+"<flag>success</flag>";
                    // xml=xml+"<EMPLOYEE_ID>"+rs.getInt("EMPLOYEE_ID")+"</EMPLOYEE_ID><DESIGNATION_ID>"+rs.getInt("DESIGNATION_ID")+"</DESIGNATION_ID><OFFICE_ID>"+rs.getInt("OFFICE_ID")+"</OFFICE_ID><DATE_OF_JOINING>"+rs.getString("DATE_OF_JOINING")+"</DATE_OF_JOINING><REMARKS>"+rs.getString("REMARKS")+"</REMARKS>";
                    j++;
                }
                if (j == 0) {
                    xml = xml + "<flag>failure</flag>";
                } else {
                    xml = xml + "<flag>success</flag>";
                }

                rs.close();
                ps.close();

            } catch (Exception e) {
                System.out.println("Exception of the e.getStackTrace" + e);
                System.out.println("Exception of the e.getMessage()" + e);

            }
            xml = xml + "</response>";

        }

        else if (strCommand.equalsIgnoreCase("Grant")) {

            xml = "<response><command>Grant</command>";
            int strEName = 0;
            int strDesi = 0;
            int strOffName = 0;
            String strremarks = "";

            try {
                strEName = Integer.parseInt(request.getParameter("EName"));
                strDesi = Integer.parseInt(request.getParameter("Desig"));
                strOffName = Integer.parseInt(request.getParameter("OffName"));
                strremarks = request.getParameter("Remarks");
            } catch (Exception e) {
                System.out.println("exce **** " + e);
            }
            // insert values into the table
            String sql =
                "insert into HRM_EMP_CURRENT_POSTING(EMPLOYEE_ID,OFFICE_ID,DESIGNATION_ID,DATE_OF_JOINING,REMARKS) values(?,?,?,?,?)";
            String dateString1 = request.getParameter("Dtjoin");
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date d1;
            try {
                d1 = dateFormat.parse(dateString1);
                dateFormat.applyPattern("yyyy-MM-dd");
                dateString1 = dateFormat.format(d1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            java.sql.Date date1 = java.sql.Date.valueOf(dateString1);
            System.out.println("date is" + date1);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String strdat1 = sdf.format(date1);

            try {

                ps = connection.prepareStatement(sql);
                ps.setInt(1, strEName);
                ps.setInt(2, strDesi);
                ps.setInt(3, strOffName);
                ps.setString(4, strdat1);
                ps.setString(5, strremarks);
                ps.executeUpdate();

                ps.close();
            }

            catch (Exception e) {
                System.out.println("exception in the insertion" + e);

            }
            try {

                String sql2 =
                    "select EMPLOYEE_NAME,OFFICE_NAME,DESIGNATION FROM HRM_MST_EMPLOYEES,COM_MST_OFFICES,HRM_MST_DESIGNATIONS WHERE EMPLOYEE_ID=? AND OFFICE_ID=? AND DESIGNATION_ID=?";
                ps1 = connection.prepareStatement(sql2);
                ps1.setInt(1, strEName);
                ps1.setInt(2, strOffName);
                ps1.setInt(3, strDesi);
                results = ps1.executeQuery();

                while (results.next()) {

                    String empid = results.getString("EMPLOYEE_NAME");
                    String offid = results.getString("OFFICE_NAME");
                    String Desig = results.getString("DESIGNATION");
                    xml = xml + "<flag>success</flag>";
                    xml =
 xml + "<EmpName>" + empid + "</EmpName>" + "<OffName>" + offid +
   "</OffName><Desig>" + Desig + "</Desig>";
                }


            }

            catch (Exception e) {
                System.out.println("exception in the selection" + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }

        else if (strCommand.equalsIgnoreCase("Delete")) {

            xml = "<response><command>Delete</command>";

            int strEmpName = Integer.parseInt(request.getParameter("EName"));
            System.out.println("empname value is:" + strEmpName);
            try {

                String sql =
                    "Delete from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?";
                System.out.println("sql execution is:" + sql);
                ps = connection.prepareStatement(sql);
                ps.setInt(1, strEmpName);
                int i = ps.executeUpdate();

                if (i >= 1) {
                    System.out.println("within in while");
                    xml = xml + "<flag>success</flag>";
                    xml =
 xml + "<EMPLOYEE_ID>" + strEmpName + "</EMPLOYEE_ID>";
                }

                ps.close();

            } catch (Exception e) {
                System.out.println("Exception of the e.getStackTrace" +
                                   e.getStackTrace());
                System.out.println("Exception of the e.getMessage()" +
                                   e.getMessage());
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";

        }

        else if (strCommand.equalsIgnoreCase("Load")) {

            String sxml = "<response><command>Load</command>";

            String strEmpName = request.getParameter("EName");
            System.out.println("strEmpName" + strEmpName);
            try {

                String sql =
                    "SELECT HRM_EMP_CURRENT_POSTING.EMPLOYEE_ID,HRM_EMP_CURRENT_POSTING.OFFICE_ID,HRM_EMP_CURRENT_POSTING.DATE_OF_JOINING,HRM_EMP_CURRENT_POSTING.REMARKS, HRM_MST_EMPLOYEES.EMPLOYEE_NAME,HRM_EMP_CURRENT_POSTING.DESIGNATION_ID, HRM_MST_DESIGNATIONS.DESIGNATION, COM_MST_OFFICES.OFFICE_NAME,     " +
                    " HRM_MST_DESIGNATIONS.SERVICE_GROUP_ID" +
                    " FROM HRM_EMP_CURRENT_POSTING, HRM_MST_EMPLOYEES, HRM_MST_DESIGNATIONS, COM_MST_OFFICES    " +
                    " WHERE HRM_EMP_CURRENT_POSTING.EMPLOYEE_ID=HRM_MST_EMPLOYEES.EMPLOYEE_ID and   " +
                    " HRM_EMP_CURRENT_POSTING.DESIGNATION_ID=HRM_MST_DESIGNATIONS.DESIGNATION_ID and  " +
                    " HRM_EMP_CURRENT_POSTING.OFFICE_ID=COM_MST_OFFICES.OFFICE_ID   and " +
                    " HRM_EMP_CURRENT_POSTING.EMPLOYEE_ID=? ";
                System.out.println("before sql");
                System.out.println("connection" + connection);
                ps = connection.prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(strEmpName));
                System.out.println("after sql");
                ResultSet rs = ps.executeQuery();

                int i = 0;
                String strDob = "";
                while (rs.next()) {
                    i++;
                    Date strdt = rs.getDate("DATE_OF_JOINING");
                    if (strdt == null) {
                        strDob = "0";
                    } else {
                        String[] sd;
                        sd =
  rs.getDate("DATE_OF_JOINING").toString().split("-");
                        strDob = sd[2] + "/" + sd[1] + "/" + sd[0];
                    }
                    String strem = rs.getString("REMARKS");
                    if (strem == null) {
                        strem = "Not Specified";
                    } else {
                        strem = strem;
                    }

                    xml =
 xml + "<EMP_ID>" + strEmpName + "</EMP_ID><EMPLOYEE_NAME>" +
   rs.getString("EMPLOYEE_NAME") + "</EMPLOYEE_NAME><DESIGNATION>" +
   rs.getString("DESIGNATION") + "</DESIGNATION><OFFICE_ID>" +
   rs.getInt("OFFICE_ID") + "</OFFICE_ID><OFFICE_NAME>" +
   rs.getString("OFFICE_NAME") + "</OFFICE_NAME>";
                    xml =
 xml + "<JDate>" + strDob + "</JDate><Remarks>" + strem +
   "</Remarks><ServGroup>" + rs.getInt("SERVICE_GROUP_ID") +
   "</ServGroup><DesignId>" + rs.getInt("DESIGNATION_ID") + "</DesignId>";
                }

                if (i == 0) {

                    try {
                        String sql2 =
                            "select EMPLOYEE_NAME from hrm_mst_employees where employee_id=?";
                        ps2 = connection.prepareStatement(sql2);
                        ps2.setInt(1, Integer.parseInt(strEmpName));

                        rs4 = ps2.executeQuery();
                        System.out.println("after sql in the second query");
                        int j = 0;
                        while (rs4.next()) {
                            j++;
                            xml = sxml + "<flag>NoRecord</flag>";
                            xml =
 xml + "<EMPLOYEE_NAME>" + rs4.getString("EMPLOYEE_NAME") + "</EMPLOYEE_NAME>";

                        }
                        if (j == 0) {
                            xml = sxml + "<flag>NoValue</flag>";
                        }

                    } catch (Exception ae) {
                        System.out.println("Error in the second query" + ae);
                    }

                } else {
                    xml = sxml + "<flag>success</flag>" + xml;
                }

            } catch (Exception e) {
                System.out.println("Exception " + e);
                //System.out.println("Exception "+ e);
                xml = sxml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";

        }

        else if (strCommand.equalsIgnoreCase("Update")) {

            xml = "<response><command>Update</command>";

            int strEmpName = Integer.parseInt(request.getParameter("EName"));
            System.out.println("empname value is:" + strEmpName);
            int strEName = 0;
            int strDesi = 0;
            int strOffName = 0;
            String strremarks = "";
            try {


                try {
                    strEName = Integer.parseInt(request.getParameter("EName"));
                    strDesi = Integer.parseInt(request.getParameter("Desig"));
                    strOffName =
                            Integer.parseInt(request.getParameter("OffName"));
                    strremarks = request.getParameter("Remarks");
                } catch (Exception e) {
                    System.out.println("exce **** " + e);
                }
                String dateString1 = request.getParameter("Dtjoin");
                SimpleDateFormat dateFormat =
                    new SimpleDateFormat("dd/MM/yyyy");
                java.util.Date d1;
                try {
                    d1 = dateFormat.parse(dateString1);
                    dateFormat.applyPattern("yyyy-MM-dd");
                    dateString1 = dateFormat.format(d1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                java.sql.Date date1 = java.sql.Date.valueOf(dateString1);
                System.out.println("date is" + date1);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String strdat1 = sdf.format(date1);

                String sql =
                    "Update HRM_EMP_CURRENT_POSTING set DESIGNATION_ID=?,OFFICE_ID=?,DATE_OF_JOINING=?,REMARKS=? where EMPLOYEE_ID=?";

                ps = connection.prepareStatement(sql);


                ps.setInt(5, strEName);
                ps.setInt(1, strDesi);
                ps.setInt(2, strOffName);
                ps.setDate(3, date1);
                ps.setString(4, strremarks);
                ps.executeUpdate();
                ps.close();

            }


            catch (Exception e) {
                System.out.println("exception in the insertion" + e);

            }
            try {

                String sql2 =
                    "select EMPLOYEE_NAME,OFFICE_NAME,DESIGNATION FROM HRM_MST_EMPLOYEES,COM_MST_OFFICES,HRM_MST_DESIGNATIONS WHERE EMPLOYEE_ID=? AND OFFICE_ID=? AND DESIGNATION_ID=?";
                ps1 = connection.prepareStatement(sql2);
                ps1.setInt(1, strEName);
                ps1.setInt(2, strOffName);
                ps1.setInt(3, strDesi);
                results = ps1.executeQuery();

                while (results.next()) {

                    String empid = results.getString("EMPLOYEE_NAME");
                    String offid = results.getString("OFFICE_NAME");
                    String Desig = results.getString("DESIGNATION");
                    xml = xml + "<flag>success</flag>";
                    xml =
 xml + "<EmpName>" + empid + "</EmpName>" + "<OffName>" + offid +
   "</OffName><Desig>" + Desig + "</Desig>";
                }


            }

            catch (Exception e) {
                System.out.println("exception in the selection" + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }

        else if (strCommand.equalsIgnoreCase("ExistgOff")) {
            xml = "<response><command>ExistgOff</command>";
            int strOffName = 0;
            try {
                strOffName = Integer.parseInt(request.getParameter("OffName"));
            } catch (Exception e) {
                System.out.println("exce **** " + e);
            }
            try {

                String sql3 =
                    "SELECT OFFICE_NAME FROM COM_MST_OFFICES WHERE OFFICE_ID=?";
                ps3 = connection.prepareStatement(sql3);
                ps3.setInt(1, strOffName);
                rs3 = ps3.executeQuery();
                System.out.println("offname" + strOffName);
                int i = 0;
                String offid = "";
                while (rs3.next()) {
                    i++;
                    offid = rs3.getString("OFFICE_NAME");
                }

                if (i == 0) {
                    xml = xml + "<flag>NoValue</flag>";

                } else {
                    //System.out.println("offname" + strOffName);
                    xml = xml + "<flag>success</flag>";
                    xml = xml + "<OffName>" + offid + "</OffName>";
                }

            } catch (Exception ee) {
                System.out.println("error in the office fetching" + ee);
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
