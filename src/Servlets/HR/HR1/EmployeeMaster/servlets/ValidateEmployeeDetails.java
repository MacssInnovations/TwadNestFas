package Servlets.HR.HR1.EmployeeMaster.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class ValidateEmployeeDetails extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";


    public void init(ServletConfig config) throws ServletException {
        super.init(config);

    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {

        Connection connection = null;
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
            connection =
                    DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                                                strdbpassword.trim());

        } catch (Exception e) {
            System.out.println("Exception in openeing connection:" + e);
        }
        Statement statement = null;
        ResultSet results = null;
        //Statement statement1=null;
        ResultSet results1 = null;
        PreparedStatement ps = null;
        System.out.println("SERVLET CALELD");
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


        //Declare The Variables
        String strEmpPref = "";
        String strEmpInit = "";
        String strEmpName = "";
        int strEmpGpf = 0;
        String StrCommand = "";
        int strEmpId = 0;
        int hstrEmpId = 0;
        String xml = "";
        try {
            StrCommand = request.getParameter("command");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("The Exception is req paraam is:" + e);
        }
        response.setHeader("cache-control", "no-cache");
        if (StrCommand.equalsIgnoreCase("Existg")) {
            System.out.println("------------");
            strEmpId = Integer.parseInt(request.getParameter("EmpId"));
            System.out.println("EmpId" + strEmpId);
            xml = "<response><command>Existg</command>";
            try {
                statement = connection.createStatement();
                results =
                        statement.executeQuery("select EMPLOYEE_ID from HRM_MST_EMPLOYEES where   Employee_Id=" +
                                               strEmpId);
                if (results.next()) {

                    statement = connection.createStatement();
                    results =
                            statement.executeQuery("select Employee_Initial,Employee_Name,GPF_NO from HRM_MST_EMPLOYEES where  Employee_Id=" +
                                                   strEmpId);
                    try {

                        int i = 0;
                        while (results.next()) {
                            i++;
                            strEmpInit = results.getString("Employee_Initial");
                            strEmpName = results.getString("Employee_Name");
                            if (strEmpInit != null)
                                strEmpName = strEmpInit + "." + strEmpName;
                            strEmpGpf = results.getInt("GPF_NO");

                        }
                        if (i == 0) {
                            System.out.println("inner failure");
                            xml = xml + "<flag>failure</flag>";
                        } else {

                            xml = xml + "<flag>success</flag>";
                            xml =
 xml + "<EmpName>" + strEmpName + "</EmpName><EmpGpf>" + strEmpGpf +
   "</EmpGpf>";

                            /*ADDITIOANAL DETAILS*/
                            ps =
  connection.prepareStatement("select PROCESS_FLOW_STATUS_ID from HRM_MST_EMP_ADDL_TMP where EMPLOYEE_ID=?  and PROCESS_FLOW_STATUS_ID!='DL'");
                            ps.setInt(1, strEmpId);
                            results = ps.executeQuery();

                            if (results.next()) {
                                if (results.getString("PROCESS_FLOW_STATUS_ID") !=
                                    null &&
                                    results.getString("PROCESS_FLOW_STATUS_ID").equalsIgnoreCase("FR"))
                                    xml =
 xml + "<AddAvail>YES</AddAvail><AddValid>YES</AddValid>";
                                else
                                    xml =
 xml + "<AddAvail>YES</AddAvail><AddValid>NO</AddValid>";


                            } else {
                                xml =
 xml + "<AddAvail>NO</AddAvail><AddValid>NO</AddValid>";
                            }

                            /*SR CONTROLLING OFFICE DETAILS*/
                            ps =
  connection.prepareStatement("select PROCESS_FLOW_STATUS_ID from HRM_EMP_CONTROLLING_OFFICE_TMP where EMPLOYEE_ID=?  and PROCESS_FLOW_STATUS_ID!='DL'");
                            ps.setInt(1, strEmpId);
                            results = ps.executeQuery();

                            if (results.next()) {
                                if (results.getString("PROCESS_FLOW_STATUS_ID") !=
                                    null &&
                                    results.getString("PROCESS_FLOW_STATUS_ID").equalsIgnoreCase("FR"))
                                    xml =
 xml + "<SRCAvail>YES</SRCAvail><SRCValid>YES</SRCValid>";
                                else
                                    xml =
 xml + "<SRCAvail>YES</SRCAvail><SRCValid>NO</SRCValid>";


                            } else {
                                xml =
 xml + "<SRCAvail>NO</SRCAvail><SRCValid>NO</SRCValid>";
                            }

                            /*CURRENT POSTING DETAILS*/
                            ps =
  connection.prepareStatement("select PROCESS_FLOW_STATUS_ID from HRM_EMP_CURRENT_POSTING_TMP where EMPLOYEE_ID=? and PROCESS_FLOW_STATUS_ID!='DL'");
                            ps.setInt(1, strEmpId);
                            results = ps.executeQuery();

                            if (results.next()) {
                                if (results.getString("PROCESS_FLOW_STATUS_ID") !=
                                    null &&
                                    results.getString("PROCESS_FLOW_STATUS_ID").equalsIgnoreCase("FR"))
                                    xml =
 xml + "<CPAvail>YES</CPAvail><CPValid>YES</CPValid>";
                                else
                                    xml =
 xml + "<CPAvail>YES</CPAvail><CPValid>NO</CPValid>";
                            } else {
                                xml =
 xml + "<CPAvail>NO</CPAvail><CPValid>NO</CPValid>";
                            }


                            /*SR DETAILS*/
                            ps =
  connection.prepareStatement("select EMPLOYEE_ID from HRM_EMP_SERVICE_DATA where EMPLOYEE_ID=? ");
                            ps.setInt(1, strEmpId);
                            results = ps.executeQuery();

                            if (results.next()) {

                                ps =
  connection.prepareStatement("select COUNT(*) as cnt FROM HRM_EMP_SERVICE_DATA " +
                              " where EMPLOYEE_ID=? AND (PROCESS_FLOW_STATUS_ID!='FR' or  PROCESS_FLOW_STATUS_ID is null)");
                                ps.setInt(1, strEmpId);
                                results = ps.executeQuery();

                                results.next();
                                int cnt = results.getInt("cnt");

                                if (cnt == 0)
                                    xml =
 xml + "<SRDAvail>YES</SRDAvail><SRDValid>YES</SRDValid>";
                                else
                                    xml =
 xml + "<SRDAvail>YES</SRDAvail><SRDValid>NO</SRDValid>";


                            } else {
                                xml =
 xml + "<SRDAvail>NO</SRDAvail><SRDValid>NO</SRDValid>";
                            }


                        }

                    } catch (Exception aee) {
                        System.out.println("Exception in the getting values IN get : " +
                                           aee);

                    }
                    results.close();
                    response.setHeader("cache-control", "no-cache");
                } else {
                    System.out.println("outer failure");
                    xml = xml + "<flag>failure</flag>";
                }
            } catch (Exception e1) {
                System.out.println("Exception is in Get" + e1);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";

        }

        System.out.println("xml is : " + xml);
        out.write(xml);
        out.flush();
        out.close();

    }


}
