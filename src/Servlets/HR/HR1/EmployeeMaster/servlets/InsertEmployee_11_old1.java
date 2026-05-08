package Servlets.HR.HR1.EmployeeMaster.servlets;

import java.text.SimpleDateFormat;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.PrintWriter;
import java.io.IOException;

import java.sql.*;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

public class InsertEmployee_11_old1 extends HttpServlet {
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
        PreparedStatement ps3 = null;
        ResultSet res = null;


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


        String StrCommand = "";

        int strEmpId = 0;
        boolean flag = true;

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
            int SessionOfficeId =
                Integer.parseInt(request.getParameter("OfficeId"));
            xml = "<response><command>ExistgBasic</command>";

            try {
                System.out.println("1");
                // System.out.println("file Path::"+request.getContextPath()+rs.getString("Config.EMPLOYEE_PHOTOS_PATH_VIEW"));
                // String path=request.getContextPath()+rs.getString("Config.EMPLOYEE_PHOTOS_PATH_VIEW");
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
                                "select a.Employee_Id,a.EMPLOYEE_PREFIX,a.Employee_Initial,a.Employee_Name,a.GPF_NO from HRM_MST_EMPLOYEES a where  a.employee_id=?";
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
   "</EmpName><EmpGpf>" + results1.getInt("GPF_NO") + "</EmpGpf>";
                                E++;
                            }
                            if (E == 0) {
                                xml = xml + "<flag>NoEmployee</flag>";
                            }

                        } else if (status.equalsIgnoreCase("FR")) {
                            xml = xml + "<flag>freezed</flag>";
                        }

                    }

                    else {

                        String ForEmpId =
                            "select a.Employee_Id,a.EMPLOYEE_PREFIX,a.Employee_Initial,a.Employee_Name,a.GPF_NO from HRM_MST_EMPLOYEES a where  a.employee_id=?";
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
   "</EmpName><EmpGpf>" + results1.getInt("GPF_NO") + "</EmpGpf>";
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
