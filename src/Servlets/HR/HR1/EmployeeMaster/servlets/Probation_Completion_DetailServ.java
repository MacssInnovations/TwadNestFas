package Servlets.HR.HR1.EmployeeMaster.servlets;

import Servlets.Security.classes.UserProfile;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.*;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;


public class Probation_Completion_DetailServ extends HttpServlet {
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
        ResultSet rs1 = null;
        ResultSet rs3 = null;
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        //PreparedStatement ps2=null;
        PreparedStatement ps3 = null;
        PreparedStatement ps4 = null;
        ResultSet rs4 = null;


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

        else if (strCommand.equalsIgnoreCase("loadCadre")) {
            int emp = Integer.parseInt(request.getParameter("txteid"));
            System.out.println("employee id..." + emp);


            try {
                /*
              String sql="select distinct b.cadre_id, c.cadre_name from hrm_emp_service_data a " +
                         " left outer join hrm_mst_designations b on b.designation_id=a.designation_id " +
                         " left outer join hrm_mst_cadre c on c.cadre_id=b.cadre_id " +
                         " where a.employee_id=?";
                     */

                String sql =
                    "select distinct b.cadre_id, c.cadre_name from hrm_emp_service_data a " +
                    " left outer join hrm_mst_designations b on b.designation_id=a.designation_id " +
                    " left outer join hrm_mst_cadre c on c.cadre_id=b.cadre_id " +
                    " where a.employee_id=? and b.cadre_id is not null " +
                    " union " +
                    " select distinct b.cadre_id,c.cadre_name from hrm_emp_current_posting a" +
                    " left outer join hrm_mst_designations b on b.designation_id=a.designation_id " +
                    " left outer join hrm_mst_cadre c on c.cadre_id=b.cadre_id " +
                    " where a.employee_id=? and b.cadre_id is not null";
                System.out.println(sql);

                ps1 = connection.prepareStatement(sql);
                ps1.setInt(1, emp);
                ps1.setInt(2, emp);

                rs1 = ps1.executeQuery();


                int id = 0;
                String name = "";
                while (rs1.next()) {
                    id = rs1.getInt("cadre_id");
                    name = rs1.getString("cadre_name");

                    System.out.println("id..." + id);
                    System.out.println("name..." + name);
                    xml =
 xml + "<option><cadre_id>" + id + "</cadre_id><cadre>" + name +
   "</cadre></option>";

                }

                xml = "<select>" + xml + "</select>";

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        else if (strCommand.equalsIgnoreCase("Add")) {

            xml = "<response><command>Add</command>";
            int strEName = 0, cad_id = 0;
            String prob_date = "", pro_no = "", proce_date = "", remarks = "";

            Calendar c;
            Date probation_date = null;
            Date proceed_date = null;
            try {
                strEName = Integer.parseInt(request.getParameter("EName"));
                cad_id = Integer.parseInt(request.getParameter("Cadr"));
                prob_date = request.getParameter("Probdat");

                if (prob_date.equals(null) || prob_date.equals("")) {
                    probation_date = null;
                } else {
                    String[] sd = request.getParameter("Probdat").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                    java.util.Date d = c.getTime();
                    probation_date = new Date(d.getTime());
                }

                pro_no = request.getParameter("Pro_no");

                proce_date = request.getParameter("Prodat");

                if (proce_date.equals(null) || proce_date.equals("")) {
                    proce_date = null;
                } else {
                    String[] sd1 = request.getParameter("Prodat").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd1[2]), Integer.parseInt(sd1[1]) -
                         1, Integer.parseInt(sd1[0]));
                    java.util.Date d = c.getTime();
                    proceed_date = new Date(d.getTime());
                }

                remarks = request.getParameter("Rem");

                System.out.println("strEName..." + strEName);
                System.out.println("cad_id..." + cad_id);
                System.out.println("probation_date...." + probation_date);
                System.out.println("proceed_date..." + proceed_date);
            } catch (Exception e) {
                System.out.println("exce **** " + e.getMessage());
            }

            String updatedby = (String)session.getAttribute("UserId");
            long l = System.currentTimeMillis();
            Timestamp ts = new Timestamp(l);

            String sql =
                "insert into HRM_EMP_PROBATION_DETAILS(EMPLOYEE_ID,CADRE_ID,PROBATION_DATE,PROCEEDING_NO,PROCEEDING_DATE,REMARKS,UPDATED_BY_USER_ID,UPDATED_DATE) values(?,?,?,?,?,?,?,?)";

            try {
                ps = connection.prepareStatement(sql);
                ps.setInt(1, strEName);
                ps.setInt(2, cad_id);
                ps.setDate(3, probation_date);
                ps.setString(4, pro_no);
                ps.setDate(5, proceed_date);
                ps.setString(6, remarks);
                ps.setString(7, updatedby);
                ps.setTimestamp(8, ts);

                int i = ps.executeUpdate();

                if (i > 0) {
                    xml = xml + "<flag>success</flag>";
                } else {
                    xml = xml + "<flag>failure</flag>";
                }

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
            System.out.println("employee_id..." + strEmpName);
            int cad_id = Integer.parseInt(request.getParameter("cadr_id"));
            System.out.println("cadre id..." + cad_id);
            try {

                String sql =
                    "Delete from HRM_EMP_PROBATION_DETAILS where EMPLOYEE_ID=? and CADRE_ID=?";
                ps = connection.prepareStatement(sql);
                ps.setInt(1, strEmpName);
                ps.setInt(2, cad_id);
                int i = ps.executeUpdate();
                if (i >= 1) {
                    xml = xml + "<flag>success</flag>";
                    //xml=xml+"<EMPLOYEE_ID>"+strEmpName+"</EMPLOYEE_ID>";
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


            System.out.println(":xml:" + sxml);

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

                            xml = sxml + "<flag>failure2</flag>";
                            flag = false;
                        }

                    }

                } else {


                }

                if (flag) {


                    String sql =
                        "select a.employee_id,a.employee_name,a.employee_initial,a.gpf_no,b.designation_id,c.designation " +
                        " from hrm_mst_employees a " +
                        " left outer join hrm_emp_current_posting b on b.employee_id=a.employee_id " +
                        " left outer join hrm_mst_designations c on c.designation_id=b.designation_id " +
                        " where a.employee_id=?";

                    System.out.println(sql);

                    ps = connection.prepareStatement(sql);
                    ps.setInt(1, Integer.parseInt(strEmpName));
                    ResultSet rs = ps.executeQuery();

                    int i = 0;
                    //String strDob="";
                    if (rs.next()) {
                        System.out.println("inside if stmt");
                        //String strEmpPref=rs.getString("Employee_Prefix");
                        String strEmpInit = rs.getString("Employee_Initial");
                        System.out.println("strEmpInit..." + strEmpInit);
                        String strEmpName1 = rs.getString("Employee_Name");
                        System.out.println("strEmpName..." + strEmpName1);
                        strEmpName1 =
                                (strEmpInit != null) ? strEmpInit + "." + strEmpName1 :
                                strEmpName1;
                        System.out.println("strEmpName..." + strEmpName1);
                        int strEmpGpf = rs.getInt("GPF_NO");
                        System.out.println("strEmpGpf..." + strEmpGpf);
                        int strEmpId = rs.getInt("Employee_Id");
                        System.out.println("strEmpId..." + strEmpId);


                        int desig_id = rs.getInt("designation_id");
                        System.out.println("designation id..." + desig_id);

                        String desig = rs.getString("designation");
                        System.out.println("designation..." + desig);


                        // if( !status.equalsIgnoreCase("FR"))

                        xml = sxml + "<flag>success</flag>";
                        xml = xml + "<EMP_ID>" + strEmpId + "</EMP_ID>";
                        xml =
 xml + "<EmpInit>" + strEmpInit + "</EmpInit><EmpName>" + strEmpName1 +
   "</EmpName><EmpGpf>" + strEmpGpf + "</EmpGpf>";
                        xml =
 xml + "<desig_id>" + desig_id + "</desig_id><desig>" + desig + "</desig>";
                        System.out.println("Inner xml:" + xml);

                    }
                    System.out.println("i=" + i);

                    System.out.println("emp_id..." +
                                       Integer.parseInt(strEmpName));

                    try {


                        String sql5 =
                            "select a.employee_id,a.cadre_id,b.cadre_name,a.probation_date,a.proceeding_no,a.proceeding_date,a.remarks " +
                            " from HRM_EMP_PROBATION_DETAILS a " +
                            " left outer join hrm_mst_cadre b on a.cadre_id=b.cadre_id where employee_id=?";


                        ps3 = connection.prepareStatement(sql5);

                        ps3.setInt(1, Integer.parseInt(strEmpName));

                        rs3 = ps3.executeQuery();

                        int k = 0;


                        while (rs3.next()) {
                            String DoP = "";
                            String PD = "";

                            if (rs3.getDate("probation_date") != null) {
                                String[] dop =
                                    rs3.getDate("probation_date").toString().split("-");
                                DoP = dop[2] + "/" + dop[1] + "/" + dop[0];
                            } else {
                                DoP = "";
                            }

                            if (rs3.getDate("proceeding_date") != null) {
                                String[] dp =
                                    rs3.getDate("proceeding_date").toString().split("-");
                                PD = dp[2] + "/" + dp[1] + "/" + dp[0];
                            } else {
                                PD = "";
                            }

                            xml =
 xml + "<details><cad_id>" + rs3.getInt("cadre_id") + "</cad_id><cad_name>" +
   rs3.getString("cadre_name") + "</cad_name><prob_date>" + DoP +
   "</prob_date>";
                            xml =
 xml + "<proceed_no>" + rs3.getString("proceeding_no") +
   "</proceed_no><proceed_date>" + PD + "</proceed_date><remarks>" +
   rs3.getString("remarks") + "</remarks></details>";
                            k++;
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }


                }
            } catch (Exception e) {
                System.out.println("Exception " + e);
                xml = sxml + "<flag>failure</flag>";
            }
            //}
            xml = xml + "</response>";
            System.out.println("xml..." + xml);

        }

        else if (strCommand.equalsIgnoreCase("Update")) {

            xml = "<response><command>Update</command>";

            int strEmpName = 0;
            int cad_id = 0;
            String prob_date = "";
            String proc_no = "";
            String proc_date = "";
            String remarks = "";
            Date probation_date = null;
            Date proceeding_date = null;
            Calendar c = null;

            try {
                strEmpName = Integer.parseInt(request.getParameter("EName"));
                System.out.println("empname value is:" + strEmpName);

                cad_id = Integer.parseInt(request.getParameter("Cadr"));
                System.out.println("cadre id..." + cad_id);

                prob_date = request.getParameter("Probdat");
                System.out.println("prob date..." + prob_date);

                if (prob_date.equals(null) || prob_date.equals("")) {
                    probation_date = null;
                } else {
                    String[] sd = request.getParameter("Probdat").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                    java.util.Date d = c.getTime();
                    probation_date = new Date(d.getTime());
                }

                System.out.println("probation date..." + probation_date);

                proc_no = request.getParameter("Pro_no");
                System.out.println("proceed no..." + proc_no);

                proc_date = request.getParameter("Prodat");
                System.out.println("proc date..." + proc_date);

                if (proc_date.equals(null) || proc_date.equals("")) {
                    proceeding_date = null;
                } else {
                    String[] sd = request.getParameter("Prodat").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                    java.util.Date d = c.getTime();
                    proceeding_date = new Date(d.getTime());
                }

                System.out.println("proceeding date..." + proceeding_date);

                remarks = request.getParameter("Rem");
                System.out.println("remarks..." + remarks);
            } catch (Exception e) {
                System.out.println("exce **** " + e);
            }


            try {


                String updatedby = (String)session.getAttribute("UserId");
                long l = System.currentTimeMillis();
                Timestamp ts = new Timestamp(l);


                ps4 =
 connection.prepareStatement("update HRM_EMP_PROBATION_DETAILS set PROBATION_DATE=?,PROCEEDING_NO=?,PROCEEDING_DATE=?,REMARKS=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=? where EMPLOYEE_ID=? and CADRE_ID=?");

                ps4.setDate(1, probation_date);
                ps4.setString(2, proc_no);
                ps4.setDate(3, proceeding_date);
                ps4.setString(4, remarks);
                ps4.setString(5, updatedby);
                ps4.setTimestamp(6, ts);
                ps4.setInt(7, strEmpName);
                ps4.setInt(8, cad_id);

                int j = ps4.executeUpdate();

                if (j > 0) {
                    xml = xml + "<flag>success</flag>";
                } else {
                    xml = xml + "<flag>failure</flag>";
                }


                //xml=xml+"<flag>success</flag>";
                ps.close();
            } catch (Exception e) {
                System.out.println("exception in the updating" +
                                   e.getMessage());
                xml = xml + "<flag>failure1</flag>";
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
