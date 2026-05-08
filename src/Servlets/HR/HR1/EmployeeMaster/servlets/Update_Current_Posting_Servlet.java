package Servlets.HR.HR1.EmployeeMaster.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.*;

import java.text.SimpleDateFormat;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class Update_Current_Posting_Servlet extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";


    public void init(ServletConfig config) throws ServletException {
        super.init(config);

    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);

        Connection connection = null;
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

            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection


            Class.forName(strDriver.trim());
            connection =
                    DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                                                strdbpassword.trim());

        } catch (Exception e) {
            System.out.println("________Exception in opening connection:_______________" +
                               e);
        }
        Statement statement = null;
        ResultSet results = null;
        ResultSet rs4 = null;
        ResultSet rs3 = null;
        PreparedStatement ps = null;
        // PreparedStatement ps1=null;
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;
        String strCommand = "";
        String xml = "";
        response.setContentType("text/xml");
        PrintWriter pw = response.getWriter();
        response.setHeader("Cache-Control", "no-cache");
        HttpSession session = request.getSession(false);
        try {
            if (session == null) {
                xml =
 "<response><command>sessionout</command><flag>sessionout</flag></response>";
                pw.println(xml);
                System.out.println(xml);
                pw.close();
                return;

            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }

        try {
            strCommand = request.getParameter("command");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (strCommand.equalsIgnoreCase("Load")) {

            String sxml = "<response><command>Load</command>";

            String strEmpName = request.getParameter("EName");
            System.out.println("strEmpName" + strEmpName);
            String DeptId = "";
            int OffId = 0;
            String OffName = "";
            String OffDName = "";
            String strRecordStatus = "";
            try {

                String sql3 =
                    "Select OFFICE_ID,DEPARTMENT_ID FROM HRM_EMP_CURRENT_POSTING WHERE EMPLOYEE_ID=?";
                ps3 = connection.prepareStatement(sql3);
                ps3.setInt(1, Integer.parseInt(strEmpName));
                rs3 = ps3.executeQuery();
                //int found=0;
                if (rs3.next()) {
                    DeptId = rs3.getString("DEPARTMENT_ID");
                    OffId = rs3.getInt("OFFICE_ID");
                    System.out.println("Dept id is:" + DeptId);
                    try {

                        if (DeptId.equals("TWAD")) {
                            System.out.println("Dept id is:" + DeptId);
                            String sql =
                                "select office_id,office_name,Office_address1,office_address2,CITY_TOWN_NAME,DISTRICT_CODE,OFFICE_PHONE_NO,ADDL_PHONE_NOS,OFFICE_EMAIL_ID,ADDL_EMAIL_IDS,OFFICE_FAX_NO,ADDL_FAX_NOS,OFFICE_STD_CODE from com_mst_offices where Office_Id=?";
                            PreparedStatement statement2 =
                                connection.prepareStatement(sql);
                            statement2.setInt(1, OffId);
                            connection.clearWarnings();

                            ResultSet results12 = statement2.executeQuery();
                            try {
                                if (results12.next()) {

                                    OffName =
                                            results12.getString("OFFICE_NAME");
                                    System.out.println("Twad Office Name is2" +
                                                       OffName);
                                }

                            } catch (Exception e) {
                                System.out.println("not in the result set of twad office" +
                                                   e);

                            } finally {
                                results12.close();
                            }
                        }

                        else {
                            String sql =
                                "select OTHER_DEPT_OFFICE_ID,OTHER_DEPT_OFFICE_NAME,ADDRESS1,ADDRESS2,CITY_TOWN from HRM_MST_OTHER_DEPT_OFFICES where OTHER_DEPT_OFFICE_ID=? and OTHER_DEPT_ID=?";
                            PreparedStatement statement3 =
                                connection.prepareStatement(sql);
                            statement3.setInt(1, OffId);
                            statement3.setString(2, DeptId);
                            connection.clearWarnings();
                            try {
                                ResultSet results13 =
                                    statement3.executeQuery();
                                if (results13.next()) {
                                    OffDName =
                                            results13.getString("OTHER_DEPT_OFFICE_NAME");
                                    System.out.println("other off is:" +
                                                       OffDName);
                                }

                            } catch (SQLException e) {
                                System.out.println("Exception in statement:" +
                                                   e);
                            } finally {
                                statement3.close();
                            }
                        }
                    } catch (SQLException e) {
                        System.out.println("Exception in connection:" + e);
                    }


                }
            } catch (Exception ee) {
                System.out.println("Selection of office name " + ee);
            }

            if (DeptId.equals("TWAD")) {
                try {

                    String sql =

                        "SELECT HRM_EMP_CURRENT_POSTING.EMPLOYEE_ID,HRM_EMP_CURRENT_POSTING.DEPARTMENT_ID,HRM_EMP_CURRENT_POSTING.OFFICE_GRADE,HRM_EMP_CURRENT_POSTING.OFFICE_ID,HRM_EMP_CURRENT_POSTING.DATE_OF_JOINING,HRM_EMP_CURRENT_POSTING.REMARKS,HRM_EMP_CURRENT_POSTING.Process_Flow_Status_Id, HRM_MST_EMPLOYEES.EMPLOYEE_NAME,HRM_EMP_CURRENT_POSTING.DESIGNATION_ID, HRM_MST_DESIGNATIONS.DESIGNATION, COM_MST_OFFICES.OFFICE_NAME,     " +
                        " HRM_MST_DESIGNATIONS.SERVICE_GROUP_ID" +
                        " FROM HRM_EMP_CURRENT_POSTING, HRM_MST_EMPLOYEES, HRM_MST_DESIGNATIONS, COM_MST_OFFICES    " +
                        " WHERE HRM_EMP_CURRENT_POSTING.EMPLOYEE_ID=HRM_MST_EMPLOYEES.EMPLOYEE_ID and   " +
                        " HRM_EMP_CURRENT_POSTING.DESIGNATION_ID=HRM_MST_DESIGNATIONS.DESIGNATION_ID and  " +
                        " HRM_EMP_CURRENT_POSTING.EMPLOYEE_ID=? ";
                    System.out.println("before sql");
                    //System.out.println("connection"+connection);
                    ps = connection.prepareStatement(sql);
                    ps.setInt(1, Integer.parseInt(strEmpName));
                    System.out.println("after sql");
                    ResultSet rs = ps.executeQuery();

                    int i = 0;
                    String strDob = "";
                    if (rs.next()) {
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
                            strem = "No value";
                        }


                        strRecordStatus =
                                rs.getString("Process_Flow_Status_Id");
                        if (strRecordStatus == null)
                            strRecordStatus = "0";


                        System.out.println("Twad Office Name is3" + OffName);
                        xml =
 xml + "<EMP_ID>" + strEmpName + "</EMP_ID><EMPLOYEE_NAME>" +
   rs.getString("EMPLOYEE_NAME") + "</EMPLOYEE_NAME><DESIGNATION>" +
   rs.getString("DESIGNATION") + "</DESIGNATION><OFFICE_ID>" +
   rs.getInt("OFFICE_ID") + "</OFFICE_ID><OFFICE_NAME>" + OffName +
   "</OFFICE_NAME>";
                        xml =
 xml + "<JDate>" + strDob + "</JDate><Remarks>" + strem +
   "</Remarks><ServGroup>" + rs.getInt("SERVICE_GROUP_ID") +
   "</ServGroup><DesignId>" + rs.getInt("DESIGNATION_ID") + "</DesignId>";
                        xml =
 xml + "<DEPARTMENT_ID>" + rs.getString("DEPARTMENT_ID") +
   "</DEPARTMENT_ID><Grade>" + rs.getString("OFFICE_GRADE") +
   "</Grade><RecordStatus>" + strRecordStatus + "</RecordStatus>";
                        System.out.println("Twad Office Name is4" + OffName);
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
                            System.out.println("Error in the second query" +
                                               ae);
                        }

                    } else {
                        xml = sxml + "<flag>success</flag>" + xml;
                    }

                } catch (Exception e) {
                    System.out.println("Exception " + e);
                    //System.out.println("Exception "+ e);
                    xml = sxml + "<flag>failure</flag>";
                }
            } else {
                try {

                    String sql =

                        "SELECT HRM_EMP_CURRENT_POSTING.EMPLOYEE_ID,HRM_EMP_CURRENT_POSTING.DEPARTMENT_ID,HRM_EMP_CURRENT_POSTING.OFFICE_GRADE,HRM_EMP_CURRENT_POSTING.OFFICE_ID,HRM_EMP_CURRENT_POSTING.DATE_OF_JOINING,HRM_EMP_CURRENT_POSTING.REMARKS,HRM_EMP_CURRENT_POSTING.Process_Flow_Status_Id, HRM_MST_EMPLOYEES.EMPLOYEE_NAME,HRM_EMP_CURRENT_POSTING.DESIGNATION_ID, HRM_MST_DESIGNATIONS.DESIGNATION, COM_MST_OFFICES.OFFICE_NAME,     " +
                        " HRM_MST_DESIGNATIONS.SERVICE_GROUP_ID" +
                        " FROM HRM_EMP_CURRENT_POSTING, HRM_MST_EMPLOYEES, HRM_MST_DESIGNATIONS, COM_MST_OFFICES    " +
                        " WHERE HRM_EMP_CURRENT_POSTING.EMPLOYEE_ID=HRM_MST_EMPLOYEES.EMPLOYEE_ID and   " +
                        " HRM_EMP_CURRENT_POSTING.DESIGNATION_ID=HRM_MST_DESIGNATIONS.DESIGNATION_ID and  " +
                        " HRM_EMP_CURRENT_POSTING.EMPLOYEE_ID=? ";
                    System.out.println("before sql");
                    System.out.println("connection" + connection);
                    ps = connection.prepareStatement(sql);
                    ps.setInt(1, Integer.parseInt(strEmpName));
                    System.out.println("after sql");
                    ResultSet rs = ps.executeQuery();

                    int i = 0;
                    String strDob = "";
                    if (rs.next()) {
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
                        }


                        strRecordStatus =
                                rs.getString("Process_Flow_Status_Id");
                        if (strRecordStatus == null)
                            strRecordStatus = "0";


                        xml =
 xml + "<EMP_ID>" + strEmpName + "</EMP_ID><EMPLOYEE_NAME>" +
   rs.getString("EMPLOYEE_NAME") + "</EMPLOYEE_NAME><DESIGNATION>" +
   rs.getString("DESIGNATION") + "</DESIGNATION><OFFICE_ID>" +
   rs.getInt("OFFICE_ID") + "</OFFICE_ID><OFFICE_NAME>" + OffDName +
   "</OFFICE_NAME>";
                        xml =
 xml + "<JDate>" + strDob + "</JDate><Remarks>" + strem +
   "</Remarks><ServGroup>" + rs.getInt("SERVICE_GROUP_ID") +
   "</ServGroup><DesignId>" + rs.getInt("DESIGNATION_ID") + "</DesignId>";
                        xml =
 xml + "<DEPARTMENT_ID>" + rs.getString("DEPARTMENT_ID") +
   "</DEPARTMENT_ID><Grade>" + rs.getString("OFFICE_GRADE") +
   "</Grade><RecordStatus>" + strRecordStatus + "</RecordStatus>";
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
                            System.out.println("Error in the second query" +
                                               ae);
                        }

                    } else {
                        xml = sxml + "<flag>success</flag>" + xml;
                    }

                } catch (Exception e) {
                    System.out.println("Exception " + e);
                    //System.out.println("Exception "+ e);
                    xml = sxml + "<flag>failure</flag>";
                }
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
            String strDeptId = "";
            String strGrade = "";
            String strRecordStatus = "";
            try {


                try {
                    strEName = Integer.parseInt(request.getParameter("EName"));
                    strDesi = Integer.parseInt(request.getParameter("Desig"));
                    strOffName =
                            Integer.parseInt(request.getParameter("OffName"));
                    strremarks = request.getParameter("Remarks");
                    strDeptId = request.getParameter("DeptId");
                    strGrade = request.getParameter("Grade");
                    strRecordStatus = request.getParameter("RecordStatus");

                    System.out.println("dep id is" + strGrade);
                } catch (Exception e) {
                    System.out.println("exce **** " + e);
                }

                String strDjoin = "";
                java.sql.Date date1 = null;
                // java.sql.Date date2=null;

                String dateString1 = request.getParameter("Dtjoin");
                SimpleDateFormat dateFormat1 =
                    new SimpleDateFormat("dd/MM/yyyy");
                try {
                    java.util.Date d1 = dateFormat1.parse(dateString1);
                    dateFormat1.applyPattern("yyyy-MM-dd");
                    strDjoin = dateFormat1.format(d1);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                date1 = Date.valueOf(strDjoin);


                String sql =
                    "Update HRM_EMP_CURRENT_POSTING set DESIGNATION_ID=?,OFFICE_ID=?,DATE_OF_JOINING=?,REMARKS=?,DEPARTMENT_ID=?,OFFICE_GRADE=?,Process_Flow_Status_Id=? where EMPLOYEE_ID=?";

                ps = connection.prepareStatement(sql);


                ps.setInt(8, strEName);
                ps.setInt(1, strDesi);
                ps.setInt(2, strOffName);
                ps.setDate(3, date1);
                ps.setString(4, strremarks);
                ps.setString(5, strDeptId);
                ps.setString(6, strGrade);
                ps.setString(7, strRecordStatus);

                ps.executeUpdate();
                xml = xml + "<flag>success</flag>";
                ps.close();

            }


            catch (Exception e) {
                System.out.println("exception in the insertion" + e);
                xml = xml + "<flag>failure</flag>";

            }
            xml = xml + "</response>";
        }

        else if (strCommand.equalsIgnoreCase("ExistgOff")) {
            xml = "<response><command>ExistgOff</command>";
            int strOffName = 0;
            String strDepId = "";
            int found = 0;
            try {
                strOffName = Integer.parseInt(request.getParameter("OffName"));
                strDepId = request.getParameter("DeptId");
            } catch (Exception e) {
                System.out.println("exce **** " + e);
            }
            try {


                if (strDepId.equals("TWAD")) {
                    String sql =
                        "select office_id,office_name,Office_address1,office_address2,CITY_TOWN_NAME,DISTRICT_CODE,OFFICE_PHONE_NO,ADDL_PHONE_NOS,OFFICE_EMAIL_ID,ADDL_EMAIL_IDS,OFFICE_FAX_NO,ADDL_FAX_NOS,OFFICE_STD_CODE from com_mst_offices where Office_Id=?";
                    PreparedStatement pres = connection.prepareStatement(sql);
                    pres.setInt(1, strOffName);
                    connection.clearWarnings();
                    try {
                        results = pres.executeQuery();
                        try {
                            xml = xml + "<flag>success</flag>";
                            found = 0;
                            while (results.next()) {

                                xml =
 xml + "<OffName>" + results.getString("OFFICE_NAME") + "</OffName>";
                                found++;
                            }
                            if (found == 0)
                                xml = xml + "<flag>failure</flag>";


                        } catch (Exception e) {
                            System.out.println(e);
                        } finally {
                            results.close();
                        }
                    } catch (SQLException e) {
                        System.out.println("Exception in statement:" + e);
                    } finally {
                        statement.close();
                    }
                }

                else {
                    String sql =
                        "select OTHER_DEPT_OFFICE_ID,OTHER_DEPT_OFFICE_NAME,ADDRESS1,ADDRESS2,CITY_TOWN from HRM_MST_OTHER_DEPT_OFFICES where OTHER_DEPT_OFFICE_ID=? and OTHER_DEPT_ID=?";
                    PreparedStatement pres = connection.prepareStatement(sql);
                    pres.setInt(1, strOffName);
                    pres.setString(2, strDepId);
                    connection.clearWarnings();
                    try {
                        results = pres.executeQuery();
                        try {
                            xml = xml + "<flag>success</flag>";
                            found = 0;
                            while (results.next()) {
                                xml =
 xml + "<OffName>" + results.getString("OTHER_DEPT_OFFICE_NAME") +
   "</OffName>";
                                found++;
                            }
                            if (found == 0)
                                xml = xml + "<flag>failure</flag>";


                        } catch (SQLException e) {
                            System.out.println(e);
                        } finally {
                            results.close();
                        }
                    } catch (SQLException e) {
                        System.out.println("Exception in statement:" + e);
                    } finally {
                        statement.close();
                    }
                }


            } catch (SQLException e) {
                System.out.println("Exception in connection:" + e);
            }

            xml = xml + "</response>";

        }


        System.out.println("xml is : " + xml);
        pw.write(xml);
        pw.flush();
        pw.close();


    }
}
