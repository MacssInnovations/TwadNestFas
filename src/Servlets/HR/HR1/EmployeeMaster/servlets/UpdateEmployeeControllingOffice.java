package Servlets.HR.HR1.EmployeeMaster.servlets;

import Servlets.Security.classes.UserProfile;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.*;

import java.text.SimpleDateFormat;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;


public class UpdateEmployeeControllingOffice extends HttpServlet {
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
        // Statement statement=null;
        ResultSet results = null;
        ResultSet rs4 = null;
        //ResultSet rs3=null;
        PreparedStatement ps = null;
        //PreparedStatement ps1=null;
        PreparedStatement ps2 = null;
        // PreparedStatement ps3=null;


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
            System.out.println("Command:" + strCommand);
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (strCommand.equalsIgnoreCase("check")) {

            xml = "<response><command>check</command>";

            int strEmpName = Integer.parseInt(request.getParameter("EName"));

            try {

                String sql =
                    "select * from HRM_EMP_CONTROLLING_OFFICE_TMP where EMPLOYEE_ID=?";
                ps = connection.prepareStatement(sql);
                ps.setInt(1, strEmpName);
                ResultSet rs = ps.executeQuery();
                int j = 0;
                while (rs.next()) {

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

        else if (strCommand.equalsIgnoreCase("Add")) {

            xml = "<response><command>Add</command>";
            int strEName = 0;
            String strDeptId = "";
            int strOffName = 0;
            try {
                strEName = Integer.parseInt(request.getParameter("EName"));
                strDeptId = request.getParameter("DeptId");
                strOffName = Integer.parseInt(request.getParameter("OffName"));
            } catch (Exception e) {
                System.out.println("exce **** " + e);
            }


            String dateString1 = request.getParameter("Dtjoin");
            String strDjoin = "";
            java.sql.Date date1 = null;
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
            try {
                java.util.Date d1 = dateFormat1.parse(dateString1);
                dateFormat1.applyPattern("yyyy-MM-dd");
                strDjoin = dateFormat1.format(d1);

            } catch (Exception e) {
                //e.printStackTrace();
                System.out.println("Date parsing Error:" + e);
            }
            date1 = Date.valueOf(strDjoin);

            String updatedby = (String)session.getAttribute("UserId");
            long l = System.currentTimeMillis();
            Timestamp ts = new Timestamp(l);
            String sql =
                "insert into HRM_EMP_CONTROLLING_OFFICE_TMP(EMPLOYEE_ID,CONTROLLING_OFFICE_ID,CONTROLLING_DEPARTMENT_ID,DATE_OF_JOINING,,UPDATED_BY_USER_ID,UPDATED_DATE) values(?,?,?,?,?,?)";

            try {
                ps = connection.prepareStatement(sql);
                ps.setInt(1, strEName);
                ps.setInt(2, strOffName);
                ps.setString(3, strDeptId);
                ps.setDate(4, date1);

                ps.setString(5, updatedby);
                ps.setTimestamp(6, ts);

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

        else if (strCommand.equalsIgnoreCase("Delete")) {
            xml = "<response><command>Delete</command>";

            int strEmpName = Integer.parseInt(request.getParameter("EName"));
            try {

                String sql =
                    "Delete from HRM_EMP_CONTROLLING_OFFICE_TMP where EMPLOYEE_ID=?";
                ps = connection.prepareStatement(sql);
                ps.setInt(1, strEmpName);
                int i = ps.executeUpdate();
                if (i >= 1) {
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
            // String DeptId="";
            // int OffId=0;
            String OffName = "";
            String OffAddr1 = "";
            String OffAddr2 = "";
            String City = "";
            System.out.println(":xml:" + sxml);
            String status = "";
            try {
                session = request.getSession(false);
                UserProfile up = null;
                up = (UserProfile)session.getAttribute("UserProfile");
                boolean flag = true;
                if (up.getEmployeeId() != Integer.parseInt(strEmpName)) {
                    int OfficeId = 0;
                    String sql =
                        "select CONTROLLING_OFFICE_ID from HRM_EMP_CONTROLLING_OFFICE where employee_id=?";
                    ps = connection.prepareStatement(sql);
                    ps.setInt(1, Integer.parseInt(strEmpName));
                    ResultSet rs = ps.executeQuery();

                    if (rs.next()) {
                        OfficeId = rs.getInt("CONTROLLING_OFFICE_ID");
                    }

                    if (OfficeId != 0) {
                        sql =
 "select OFFICE_ID  from HRM_EMP_CURRENT_POSTING where employee_id=?";
                        ps = connection.prepareStatement(sql);
                        ps.setInt(1, up.getEmployeeId());
                        rs = ps.executeQuery();
                        if (rs.next()) {
                            int offid = rs.getInt("OFFICE_ID");
                            if (offid != OfficeId) {
                                if (session.getAttribute("Admin") == null ||
                                    !((String)session.getAttribute("Admin")).equalsIgnoreCase("YES")) {
                                    xml = sxml + "<flag>failure1</flag>";
                                    flag = false;
                                }
                            }
                        } else {
                            // response.sendRedirect(request.getContextPath()+"/org/Library/jsps/Messenger.jsp?message=" + "Current Posting is not available. Can not see the profile for "+strEmpId+"!");
                            xml = sxml + "<flag>failure2</flag>";
                            flag = false;
                        }

                    }

                } else {

                    // xml=sxml+"<flag>failure4</flag>";
                    // flag=false;
                }

                if (flag) {
                    String sql =
                        "SELECT A.EMPLOYEE_ID ,A.CONTROLLING_OFFICE_ID ,A.CONTROLLING_DEPARTMENT_ID ," +
                        " A.CONTROLLING_DATE_FROM ,B.EMPLOYEE_NAME,B.EMPLOYEE_PREFIX,B.EMPLOYEE_INITIAL,B.GPF_NO," +
                        " C.OFFICE_NAME,C.OFFICE_ADDRESS1,C.OFFICE_ADDRESS2,C.CITY_TOWN_NAME," +
                        " A.PROCESS_FLOW_STATUS_ID,d.DISTRICT_NAME   FROM HRM_EMP_CONTROLLING_OFFICE_TMP A  " +
                        " INNER JOIN COM_MST_OFFICES C ON C.OFFICE_ID=A.CONTROLLING_OFFICE_ID " +
                        " INNER JOIN HRM_MST_EMPLOYEES B ON B.EMPLOYEE_ID=A.EMPLOYEE_ID " +
                        " left outer join com_mst_districts d on d.DISTRICT_CODE=C.DISTRICT_CODE  " +
                        " where A.EMPLOYEE_ID=?";
                    System.out.println(sql);

                    ps = connection.prepareStatement(sql);
                    ps.setInt(1, Integer.parseInt(strEmpName));
                    ResultSet rs = ps.executeQuery();

                    int i = 0;
                    String strDob = "";
                    if (rs.next()) {

                        String strEmpPref = rs.getString("Employee_Prefix");
                        String strEmpInit = rs.getString("Employee_Initial");
                        strEmpName = rs.getString("Employee_Name");
                        strEmpName =
                                (strEmpInit != null) ? strEmpInit + "." + strEmpName :
                                strEmpName;
                        int strEmpGpf = rs.getInt("GPF_NO");
                        int strEmpId = rs.getInt("Employee_Id");

                        OffName = rs.getString("OFFICE_NAME");
                        OffAddr1 = rs.getString("OFFICE_ADDRESS1");
                        OffAddr2 = rs.getString("OFFICE_ADDRESS2");
                        City = rs.getString("CITY_TOWN_NAME");


                        System.out.println("PROCESS_FLOW_STATUS_ID:" + status);
                        Date strdt = rs.getDate("CONTROLLING_DATE_FROM");
                        if (strdt == null) {
                            strDob = "0";
                        } else {
                            String[] sd;
                            sd =
  rs.getDate("CONTROLLING_DATE_FROM").toString().split("-");
                            strDob = sd[2] + "/" + sd[1] + "/" + sd[0];
                        }
                        status = rs.getString("PROCESS_FLOW_STATUS_ID");
                        i++;
                        // if( !status.equalsIgnoreCase("FR"))
                        {

                            xml =
 xml + "<EMP_ID>" + strEmpId + "</EMP_ID><OFFICE_ID>" +
   rs.getInt("CONTROLLING_OFFICE_ID") + "</OFFICE_ID><OFFICE_NAME>" + OffName +
   "</OFFICE_NAME>";
                            xml =
 xml + "<OffAddr1>" + OffAddr1 + "</OffAddr1><OffAddr2>" + OffAddr2 +
   "</OffAddr2><City>" + City + "</City>";
                            xml =
 xml + "<District>" + rs.getString("DISTRICT_NAME") + "</District>";
                            xml =
 xml + "<EmpPref>" + strEmpPref + "</EmpPref><EmpInit>" + strEmpInit +
   "</EmpInit><EmpName>" + strEmpName + "</EmpName><EmpGpf>" + strEmpGpf +
   "</EmpGpf>";
                            xml = xml + "<JDate>" + strDob + "</JDate>";
                            xml =
 xml + "<DEPARTMENT_ID>" + rs.getString("CONTROLLING_DEPARTMENT_ID") +
   "</DEPARTMENT_ID><recordstatus>" + rs.getString("PROCESS_FLOW_STATUS_ID") +
   "</recordstatus>";
                            System.out.println("Inner xml:" + xml);
                        }
                    }
                    System.out.println("i=" + i);
                    if (i == 0) {

                        try {
                            String sql2 =
                                "select * from hrm_mst_employees where employee_id=?";
                            ps2 = connection.prepareStatement(sql2);
                            ps2.setInt(1, Integer.parseInt(strEmpName));

                            rs4 = ps2.executeQuery();
                            int j = 0;
                            while (rs4.next()) {
                                j++;
                                String strEmpPref =
                                    rs4.getString("Employee_Prefix");
                                String strEmpInit =
                                    rs4.getString("Employee_Initial");
                                strEmpName = rs4.getString("Employee_Name");
                                int strEmpGpf = rs4.getInt("GPF_NO");
                                xml = sxml + "<flag>NoRecord</flag>";
                                xml =
 xml + "<EmpPref>" + strEmpPref + "</EmpPref><EmpInit>" + strEmpInit +
   "</EmpInit><EmpName>" + strEmpName + "</EmpName><EmpGpf>" + strEmpGpf +
   "</EmpGpf>";
                            }
                            if (j == 0) {
                                xml = sxml + "<flag>NoValue</flag>";
                            }

                        } catch (Exception ae) {
                            System.out.println("Error in the second query" +
                                               ae);
                        }

                    } else {
                        System.out.println("Session Admin:" +
                                           session.getAttribute("Admin"));
                        if (session.getAttribute("Admin") != null &&
                            ((String)session.getAttribute("Admin")).equalsIgnoreCase("YES")) {
                            xml = sxml + "<flag>success</flag>" + xml;
                        } else if (status.equalsIgnoreCase("FR")) {
                            //xml=sxml+"<flag>freezed</flag>"+xml;
                            System.out.println("temp xml:" + xml);
                            xml = sxml + "<flag>freezed</flag>" + xml;
                        } else {
                            System.out.println("temp xml:" + xml);
                            xml = sxml + "<flag>success</flag>" + xml;
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Exception " + e);
                xml = sxml + "<flag>failure</flag>";
            }
            //}
            xml = xml + "</response>";

        }

        else if (strCommand.equalsIgnoreCase("Update")) {

            xml = "<response><command>Update</command>";

            int strEmpName = 0;
            int strEName = 0;
            int strOffName = 0;
            String strDeptId = "";

            try {
                strEmpName = Integer.parseInt(request.getParameter("EName"));
                System.out.println("empname value is:" + strEmpName);
                strEName = Integer.parseInt(request.getParameter("EName"));
                strDeptId = request.getParameter("DeptId");
                strOffName = Integer.parseInt(request.getParameter("OffName"));
            } catch (Exception e) {
                System.out.println("exce **** " + e);
            }

            String strDjoin = "";
            java.sql.Date date1 = null;

            try {

                String dateString1 = request.getParameter("Dtjoin");
                SimpleDateFormat dateFormat1 =
                    new SimpleDateFormat("dd/MM/yyyy");
                java.util.Date d1 = dateFormat1.parse(dateString1);
                System.out.println(dateString1);
                System.out.println(d1);
                dateFormat1.applyPattern("yyyy-MM-dd");
                strDjoin = dateFormat1.format(d1);
                date1 = Date.valueOf(strDjoin);
            } catch (Exception e) {
                //e.printStackTrace();
                System.out.println("Date parsing error:" + e);
            }

            try {
                /*                 */
                String sql =
                    "select * from HRM_EMP_CONTROLLING_OFFICE_TMP where EMPLOYEE_ID=?";
                ps = connection.prepareStatement(sql);
                ps.setInt(1, strEmpName);
                ResultSet rs = ps.executeQuery();

                /*                 */

                String updatedby = (String)session.getAttribute("UserId");
                long l = System.currentTimeMillis();
                Timestamp ts = new Timestamp(l);
                if (rs.next()) {
                    sql =
 "Update HRM_EMP_CONTROLLING_OFFICE_TMP set CONTROLLING_OFFICE_ID=?,CONTROLLING_DEPARTMENT_ID=?,process_flow_status_id=?,CONTROLLING_DATE_FROM=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=? where EMPLOYEE_ID=?";

                    ps = connection.prepareStatement(sql);
                    ps.setInt(1, strOffName);
                    ps.setString(2, strDeptId);
                    ps.setString(3, "MD");
                    ps.setDate(4, date1);

                    ps.setString(5, updatedby);
                    ps.setTimestamp(6, ts);
                    ps.setInt(7, strEName);
                    ps.executeUpdate();
                } else {
                    sql =
 "insert into HRM_EMP_CONTROLLING_OFFICE_TMP(EMPLOYEE_ID,CONTROLLING_OFFICE_ID,CONTROLLING_DEPARTMENT_ID,CONTROLLING_DATE_FROM,PROCESS_FLOW_STATUS_ID,UPDATED_BY_USER_ID,UPDATED_DATE) values(?,?,?,?,?,?,?)";
                    ps = connection.prepareStatement(sql);
                    ps.setInt(1, strEName);
                    ps.setInt(2, strOffName);
                    ps.setString(3, strDeptId);
                    ps.setDate(4, date1);
                    ps.setString(5, "CR");
                    ps.setString(6, updatedby);
                    ps.setTimestamp(7, ts);
                    ps.executeUpdate();

                }

                xml = xml + "<flag>success</flag>";
                ps.close();
            } catch (Exception e) {
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
                System.out.println("Office Name:" + strOffName);
                System.out.println("Dept Id:" + strDepId);
            } catch (Exception e) {
                System.out.println("exce **** " + e);
            }
            try {


                if (strDepId.equalsIgnoreCase("TWAD")) {
                    System.out.println("Enter into twad");
                    // String sql =  "select office_id,office_name,Office_address1,office_address2,CITY_TOWN_NAME,DISTRICT_CODE,OFFICE_PHONE_NO,ADDL_PHONE_NOS,OFFICE_EMAIL_ID,ADDL_EMAIL_IDS,OFFICE_FAX_NO,ADDL_FAX_NOS,OFFICE_STD_CODE from com_mst_offices where Office_Id=?";
                    String sql =
                        "select a.office_id,a.office_name,a.Office_address1,a.office_address2,a.CITY_TOWN_NAME,b.DISTRICT_NAME from com_mst_offices a left outer join com_mst_districts b on b.DISTRICT_CODE=a.DISTRICT_CODE where a.Office_Id=?";
                    ps = connection.prepareStatement(sql);
                    ps.setInt(1, strOffName);
                    connection.clearWarnings();
                    try {
                        results = ps.executeQuery();


                        found = 0;
                        while (results.next()) {
                            System.out.println("here is ok");
                            xml =
 xml + "<OffName>" + results.getString("OFFICE_NAME") + "</OffName>";
                            xml =
 xml + "<OffAddr1>" + results.getString("OFFICE_ADDRESS1") +
   "</OffAddr1><OffAddr2>" + results.getString("OFFICE_ADDRESS2") +
   "</OffAddr2><City>" + results.getString("CITY_TOWN_NAME") +
   "</City><District>" + results.getString("DISTRICT_NAME") + "</District>";
                            found++;
                        }
                        if (found == 0)
                            xml = xml + "<flag>failure</flag>";
                        else
                            xml = xml + "<flag>success</flag>";


                        results.close();

                    } catch (SQLException e) {
                        System.out.println("Exception in statement:" + e);
                        xml = xml + "<flag>failure</flag>";
                    }
                }
            } catch (SQLException e) {
                System.out.println("Exception in connection:" + e);
                xml =
 "<response><command>ExistgOff</command><flag>failure</flag>";
            }

            xml = xml + "</response>";

        }
        System.out.println("xml is : " + xml);
        pw.write(xml);
        pw.flush();
        pw.close();


    }
}
