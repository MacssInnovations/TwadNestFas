package Servlets.HR.HR1.EmployeeMaster.servlets;

import Servlets.Security.classes.UserProfile;

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

public class InsertEmployee5_New extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";


    public void init(ServletConfig config) throws ServletException {
        super.init(config);

    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {

        Connection connection = null;
        Statement statement = null;
        ResultSet results = null;
        //Statement statement1=null;
        ResultSet results1 = null;
        PreparedStatement ps = null;
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


        StrCommand = request.getParameter("command");
        System.out.println(StrCommand);

        if (StrCommand.equalsIgnoreCase("Existg")) {

            strEmpId = Integer.parseInt(request.getParameter("EmpId"));
            System.out.println("EmpId" + strEmpId);

            xml = "<response><command>Existg</command>";
            try {

                String sql =
                    "select Employee_Initial,Employee_Prefix,Employee_Name,GPF_NO from HRM_MST_EMPLOYEES where Employee_Id=" +
                    strEmpId;
                ps = connection.prepareStatement(sql);
                System.out.println(sql);
                //results = statement.executeQuery(sql);
                results = ps.executeQuery();

                if (results.next()) {
                    int i = 0;
                    i++;
                    strEmpPref = results.getString("Employee_Prefix");


                    if (strEmpPref == null)
                        strEmpPref = "null";
                    else

                        strEmpInit = results.getString("Employee_Initial");
                    strEmpName = results.getString("Employee_Name");
                    strEmpGpf = results.getInt("GPF_NO");

                    System.out.println(strEmpId);
                    System.out.println(strEmpPref);
                    System.out.println(strEmpInit);
                    System.out.println(strEmpName);
                    System.out.println(strEmpGpf);

                    xml = xml + "<flag>success</flag>";
                    xml =
 xml + "<EmpId>" + strEmpId + "</EmpId><EmpPref>" + strEmpPref +
   "</EmpPref><EmpInit>" + strEmpInit + "</EmpInit><EmpName>" + strEmpName +
   "</EmpName><EmpGpf>" + strEmpGpf + "</EmpGpf>";
                    results.close();

                }

                else {

                    xml = xml + "<flag>failure</flag>";
                }
            }

            catch (Exception e1) {
                System.out.println("Exception is in Get" + e1);
                xml = xml + "<flag>failure</flag>";
            }

            xml = xml + "</response>";
            System.out.println(xml);

        }

        else if (StrCommand.equalsIgnoreCase("Verify")) {
            int strEmpGpfno = 0;

            try {
                hstrEmpId = Integer.parseInt(request.getParameter("EmpId"));
                strEmpPref = request.getParameter("EmpPref");
                strEmpInit = request.getParameter("EmpInit");
                strEmpName = request.getParameter("EmpName");
                strEmpGpfno = Integer.parseInt(request.getParameter("EmpGpf"));

                System.out.println(hstrEmpId);
                System.out.println(strEmpPref);
                System.out.println(strEmpInit);
                System.out.println(strEmpName);
                System.out.println(strEmpGpfno);

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("The Exception is req paraam is II :" + e);
            }


            try {

                xml = "<response><command>Verify</command>";
                int i = 0;

                try {

                    connection.setAutoCommit(false);
                    String updatedby = (String)session.getAttribute("UserId");
                    long l = System.currentTimeMillis();
                    Timestamp ts = new Timestamp(l);

                    String sql =
                        "INSERT INTO HRM_MST_EMPLOYEES(EMPLOYEE_ID ,EMPLOYEE_NAME ,EMPLOYEE_INITIAL ," +
                        " EMPLOYEE_PREFIX ,GPF_NO ,UPDATED_DATE,PROCESS_FLOW_STATUS_ID,UPDATED_BY_USER_ID) VALUES(?,?,?,?,?,?,?,?)";

                    ps = connection.prepareStatement(sql);
                    ps.setInt(1, hstrEmpId);
                    ps.setString(2, strEmpName);
                    ps.setString(3, strEmpInit);
                    ps.setString(4, strEmpPref);
                    ps.setInt(5, strEmpGpfno);
                    ps.setTimestamp(6, ts);
                    ps.setString(7, "CR");
                    ps.setString(8, updatedby);

                    ps.executeUpdate();


                    System.out.println("test1");
                    connection.commit();
                    xml = xml + "<flag>success</flag>";


                }

                catch (Exception ee) {
                    connection.rollback();
                    xml = xml + "<flag>failure</flag>";
                    System.out.println("to check GPF" + ee);
                }

                finally {
                    connection.setAutoCommit(true);
                }
            } catch (Exception ee) {

                System.out.println("Error in insertion  is:" + ee);
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

