package Servlets.HR.HR1.EmployeeMaster.servlets;

import Servlets.Security.classes.UserProfile;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class Validate_Employee_PhotoServ_old extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";


    public void init(ServletConfig config) throws ServletException {
        super.init(config);

    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        System.out.println("show");
        ResourceBundle rs = null;
        Connection connection = null;
        try {
            rs = ResourceBundle.getBundle("Servlets.Security.servlets.Config");
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
            connection =
                    DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                                                strdbpassword.trim());
            System.out.println("connection is:&&&&&&&&&&&&&&&&&" +
                               ConnectionString);

        } catch (Exception e) {
            System.out.println("Exception in openeing connection:" + e);
        }

        ResultSet results1 = null;
        PreparedStatement ps = null;
        ResultSet res1 = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps3 = null;
        ResultSet res = null;

        int oid = 0;
        String deptid = "";
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();


        HttpSession session = request.getSession(false);
        try {
            if (session == null) {
                String xml =
                    "<response><command>sessionout</command><flag>sessionout</flag></response>";
                out.println(xml);
                System.out.println(xml);
                out.close();
                return;

            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }

        session = request.getSession(false);
        UserProfile empProfile =
            (UserProfile)session.getAttribute("UserProfile");

        System.out.println("user id::" + empProfile.getEmployeeId());
        int empid = empProfile.getEmployeeId();

        try {
            ps1 =
 connection.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?");
            ps1.setInt(1, empid);
            res1 = ps1.executeQuery();
            if (res1.next()) {
                oid = res1.getInt("OFFICE_ID");
                //deptid=res1.getString("DEPARTMENT_ID");

            }
            res1.close();
            ps1.close();
            System.out.println("office id:" + oid);
            //System.out.println("dept id:"+deptid);

        } catch (Exception e) {
            System.out.println("Error in getting office id");
        }


        String StrCommand = "";

        int strEmpId = 0;
        boolean flag = true;
        int office_id = 0;

        String xml = "";
        try {
            StrCommand = request.getParameter("command");
            System.out.println(" commande" + StrCommand);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("The Exception is req paraam is:" + e);
        }
        if (StrCommand.equalsIgnoreCase("ExistgBasic")) {

            strEmpId = Integer.parseInt(request.getParameter("EmpId"));
            System.out.println(strEmpId);
            // int SessionOfficeId=Integer.parseInt(request.getParameter("OfficeId"));
            xml = "<response><command>ExistgBasic</command>";

            try {
                System.out.println("2");
                System.out.println("file Path::" + request.getContextPath() +
                                   rs.getString("Config.EMPLOYEE_PHOTOS_PATH_VIEW"));
                String path =
                    request.getContextPath() + rs.getString("Config.EMPLOYEE_PHOTOS_PATH_VIEW");
                if (flag) {
                    ps =
  connection.prepareStatement("select PROCESS_FLOW_STATUS_ID from HRM_EMP_ADDL_PHOTO_TMP  where EMPLOYEE_ID=? and PROCESS_FLOW_STATUS_ID!='DL'");
                    ps.setInt(1, strEmpId);
                    res = ps.executeQuery();

                    if (res.next()) {

                        String status =
                            res.getString("PROCESS_FLOW_STATUS_ID");
                        if (status.equalsIgnoreCase("CR") ||
                            status.equalsIgnoreCase("MD")) {
                            xml = xml + "<flag>exists</flag>";

                            String ForEmpId =
                                "select controlling_office_id,employee_id, employee_prefix, employee_initial, " +
                                " employee_name, gpf_no, EMP_PHOTO_FILE_NAME" +
                                " from " + " (" +
                                " select employee_id, employee_prefix, employee_initial, employee_name, gpf_no" +
                                " from hrm_mst_employees" +
                                " where employee_id=?" + " ) a" +
                                " left outer join" + " (" +
                                " select employee_id as emp_id, EMP_PHOTO_FILE_NAME  from HRM_EMP_ADDL_PHOTO_TMP" +
                                " ) b " + " on a.employee_id = b.emp_id" +
                                " left join " + " (" +
                                " select employee_id as emp_id2, controlling_office_id from hrm_emp_controlling_office" +
                                " )c" + " on a.employee_id=c.emp_id2";

                            ps3 = connection.prepareStatement(ForEmpId);
                            ps3.setInt(1, strEmpId);
                            results1 = ps3.executeQuery();
                            int E = 0;
                            while (results1.next()) {

                                office_id =
                                        results1.getInt("controlling_office_id");


                                System.out.println("Photooooooooooo" +
                                                   results1.getString("EMP_PHOTO_FILE_NAME"));

                                xml = xml + "<flag>EmpDet</flag>";
                                xml =
 xml + "<EmpPref>" + results1.getString("EMPLOYEE_PREFIX") +
   "</EmpPref><EmpInit>" + results1.getString("Employee_Initial") +
   "</EmpInit><EmpName>" + results1.getString("Employee_Name") +
   "</EmpName><EmpGpf>" + results1.getInt("GPF_NO") + "</EmpGpf>";
                                xml =
 xml + "<EmpPhoto>" + (path + results1.getString("EMP_PHOTO_FILE_NAME")) +
   "</EmpPhoto>";
                                xml =
 xml + "<Office_id>" + results1.getInt("controlling_office_id") +
   "</Office_id>";

                                if (oid == office_id) {
                                    xml += "<flag>ok</flag>";
                                } else {
                                    xml += "<flag>cancel</flag>";
                                }
                                E++;
                            }
                            if (E == 0) {
                                xml = xml + "<flag>NoEmployee</flag>";
                            }

                        } else if (status.equalsIgnoreCase("FR")) {
                            xml = xml + "<flag>freezed</flag>";
                            String ForEmpId =
                                "select controlling_office_id,employee_id, employee_prefix, employee_initial, " +
                                " employee_name, gpf_no, EMP_PHOTO_FILE_NAME" +
                                " from " + " (" +
                                " select employee_id, employee_prefix, employee_initial, employee_name, gpf_no" +
                                " from hrm_mst_employees" +
                                " where employee_id=?" + " ) a" +
                                " left outer join" + " (" +
                                " select employee_id as emp_id, EMP_PHOTO_FILE_NAME  from HRM_EMP_ADDL_PHOTO_TMP" +
                                " ) b " + " on a.employee_id = b.emp_id" +
                                " left join " + " (" +
                                " select employee_id as emp_id2, controlling_office_id from hrm_emp_controlling_office" +
                                " )c" + " on a.employee_id=c.emp_id2";
                            ps3 = connection.prepareStatement(ForEmpId);
                            ps3.setInt(1, strEmpId);
                            results1 = ps3.executeQuery();
                            int E = 0;
                            while (results1.next()) {
                                office_id =
                                        results1.getInt("controlling_office_id");

                                System.out.println("Photooooooooooo" +
                                                   results1.getString("EMP_PHOTO_FILE_NAME"));

                                xml = xml + "<flag>EmpDet</flag>";
                                xml =
 xml + "<EmpPref>" + results1.getString("EMPLOYEE_PREFIX") +
   "</EmpPref><EmpInit>" + results1.getString("Employee_Initial") +
   "</EmpInit><EmpName>" + results1.getString("Employee_Name") +
   "</EmpName><EmpGpf>" + results1.getInt("GPF_NO") + "</EmpGpf><EmpPhoto>" +
   (path + results1.getString("EMP_PHOTO_FILE_NAME")) + "</EmpPhoto>";
                                xml =
 xml + "<Office_id>" + results1.getInt("controlling_office_id") +
   "</Office_id>";

                                if (oid == office_id) {
                                    xml += "<flag>ok</flag>";
                                } else {
                                    xml += "<flag>cancel</flag>";
                                }
                                E++;
                            }
                            if (E == 0) {
                                xml = xml + "<flag>NoEmployee</flag>";
                            }
                        }

                    }

                    else {

                        String ForEmpId =
                            "select employee_id, employee_prefix, employee_initial, " +
                            " employee_name, gpf_no, EMP_PHOTO_FILE_NAME" +
                            " from " + " (" +
                            " select employee_id, employee_prefix, employee_initial, employee_name, gpf_no" +
                            " from hrm_mst_employees" +
                            " where employee_id=?" + " ) a" +
                            " left outer join" + " (" +
                            " select employee_id as emp_id, EMP_PHOTO_FILE_NAME  from HRM_EMP_ADDL_PHOTO_TMP" +
                            " ) b " + " on a.employee_id = b.emp_id";
                        ps3 = connection.prepareStatement(ForEmpId);
                        ps3.setInt(1, strEmpId);
                        results1 = ps3.executeQuery();
                        int E = 0;
                        while (results1.next()) {

                            //System.out.println("Photooooooooooo"+results1.getString("EMP_PHOTO_FILE_NAME"));

                            xml = xml + "<flag>EmpDet</flag>";
                            xml =
 xml + "<EmpPref>" + results1.getString("EMPLOYEE_PREFIX") +
   "</EmpPref><EmpInit>" + results1.getString("Employee_Initial") +
   "</EmpInit><EmpName>" + results1.getString("Employee_Name") +
   "</EmpName><EmpGpf>" + results1.getInt("GPF_NO") + "</EmpGpf><EmpPhoto>" +
   (path + results1.getString("EMP_PHOTO_FILE_NAME")) + "</EmpPhoto>";
                            E++;
                        }
                        if (E == 0) {
                            xml = xml + "<flag>NoEmployee</flag>";
                        }

                    }

                }

            }

            catch (Exception ee) {
                System.out.println("Error in the Edit Employee Form Details" +
                                   ee);
            }
            xml = xml + "</response>";
        }

        System.out.println("xml is : " + xml);
        out.write(xml);
        out.flush();
        out.close();

    }
}
