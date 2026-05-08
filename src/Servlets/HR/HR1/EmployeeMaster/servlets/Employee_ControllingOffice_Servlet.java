package Servlets.HR.HR1.EmployeeMaster.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.*;

import java.text.SimpleDateFormat;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;


public class Employee_ControllingOffice_Servlet extends HttpServlet {
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
                    "select * from HRM_EMP_CONTROLLING_OFFICE where EMPLOYEE_ID=?";
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
            String sql =
                "insert into HRM_EMP_CONTROLLING_OFFICE(EMPLOYEE_ID,CONTROLLING_OFFICE_ID,CONTROLLING_DEPARTMENT_ID,DATE_OF_JOINING) values(?,?,?,?)";

            String dateString1 = request.getParameter("Dtjoin");
            String strDjoin = "";
            java.sql.Date date1 = null;
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
            try {
                java.util.Date d1 = dateFormat1.parse(dateString1);
                dateFormat1.applyPattern("yyyy-MM-dd");
                strDjoin = dateFormat1.format(d1);

            } catch (Exception e) {
                e.printStackTrace();
            }
            date1 = Date.valueOf(strDjoin);
            try {
                ps = connection.prepareStatement(sql);
                ps.setInt(1, strEName);
                ps.setInt(2, strOffName);
                ps.setString(3, strDeptId);
                ps.setDate(4, date1);
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
                    "Delete from HRM_EMP_CONTROLLING_OFFICE where EMPLOYEE_ID=?";
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

            /* try
              {

                 String sql3="Select OFFICE_ID,DEPARTMENT_ID FROM HRM_EMP_CURRENT_POSTING WHERE EMPLOYEE_ID=?";
                 ps3=connection.prepareStatement(sql3);
                  ps3.setInt(1,Integer.parseInt(strEmpName));
                  rs3=ps3.executeQuery();
                  if(rs3.next())
                  {
                   DeptId=rs3.getString("DEPARTMENT_ID");
                   OffId=rs3.getInt("OFFICE_ID");
                       try {

                         if (DeptId.equals("TWAD"))
                         {
                               String sql =
                                   "select office_id,office_name,Office_address1,office_address2,CITY_TOWN_NAME,DISTRICT_CODE,OFFICE_PHONE_NO,ADDL_PHONE_NOS,OFFICE_EMAIL_ID,ADDL_EMAIL_IDS,OFFICE_FAX_NO,ADDL_FAX_NOS,OFFICE_STD_CODE from com_mst_offices where Office_Id=?";
                               PreparedStatement statement2 =
                                   connection.prepareStatement(sql);
                               statement2.setInt(1, OffId);
                               connection.clearWarnings();

                                   ResultSet results12 = statement2.executeQuery();
                                   try {
                                      if (results12.next()) {

                                           X
                                          if(OffName==null)
                                             OffName="No values";
                                          else
                                             OffName=OffName;
                                          if(OffAddr1==null)
                                             OffAddr1="No values";
                                          else
                                             OffAddr1=OffAddr1;
                                          if(OffAddr2==null)
                                             OffAddr2="No values";
                                          else
                                             OffAddr2=OffAddr2;
                                          if(City==null)
                                             City="No values";
                                          else
                                             City=City;

                                      }

                                   } catch (Exception e) {System.out.println("not in the result set of twad office" + e);

                                   } finally {
                                       results12.close();
                                   }
                           }
                        } catch (SQLException e) {
                           System.out.println("Exception in connection:" + e);
                       }
                  }
              }
              catch(Exception ee){System.out.println("Selection of office name " + ee);}
              */
            // if (DeptId.equals("TWAD")) {
            try {

                String sql =
                    "SELECT A.EMPLOYEE_ID,A.CONTROLLING_OFFICE_ID,A.CONTROLLING_DEPARTMENT_ID,A.DATE_OF_JOINING,B.EMPLOYEE_NAME,B.EMPLOYEE_PREFIX,B.EMPLOYEE_INITIAL,B.GPF_NO,C.OFFICE_NAME,C.OFFICE_ADDRESS1,C.OFFICE_ADDRESS2,C.CITY_TOWN_NAME " +
                    " FROM HRM_EMP_CONTROLLING_OFFICE A " +
                    " INNER JOIN COM_MST_OFFICES C ON C.OFFICE_ID=A.CONTROLLING_OFFICE_ID " +
                    " INNER JOIN HRM_MST_EMPLOYEES B ON B.EMPLOYEE_ID=A.EMPLOYEE_ID AND A.EMPLOYEE_ID=? ";


                ps = connection.prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(strEmpName));
                ResultSet rs = ps.executeQuery();

                int i = 0;
                String strDob = "";
                if (rs.next()) {

                    String strEmpPref = rs.getString("Employee_Prefix");
                    String strEmpInit = rs.getString("Employee_Initial");
                    strEmpName = rs.getString("Employee_Name");
                    int strEmpGpf = rs.getInt("GPF_NO");
                    int strEmpId = rs.getInt("Employee_Id");

                    OffName = rs.getString("OFFICE_NAME");
                    OffAddr1 = rs.getString("OFFICE_ADDRESS1");
                    OffAddr2 = rs.getString("OFFICE_ADDRESS2");
                    City = rs.getString("CITY_TOWN_NAME");


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
                    xml =
 xml + "<EMP_ID>" + strEmpId + "</EMP_ID><OFFICE_ID>" +
   rs.getInt("CONTROLLING_OFFICE_ID") + "</OFFICE_ID><OFFICE_NAME>" + OffName +
   "</OFFICE_NAME>";
                    xml =
 xml + "<OffAddr1>" + OffAddr1 + "</OffAddr1><OffAddr2>" + OffAddr2 +
   "</OffAddr2><City>" + City + "</City>";
                    xml =
 xml + "<EmpPref>" + strEmpPref + "</EmpPref><EmpInit>" + strEmpInit +
   "</EmpInit><EmpName>" + strEmpName + "</EmpName><EmpGpf>" + strEmpGpf +
   "</EmpGpf>";
                    xml = xml + "<JDate>" + strDob + "</JDate>";
                    xml =
 xml + "<DEPARTMENT_ID>" + rs.getString("CONTROLLING_DEPARTMENT_ID") +
   "</DEPARTMENT_ID>";
                }

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
                        System.out.println("Error in the second query" + ae);
                    }

                } else {
                    xml = sxml + "<flag>success</flag>" + xml;
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

            /*   String strDjoin="";
                  java.sql.Date date1=null;

                  try
                  {

                      String  dateString1= request.getParameter("Dtjoin");
                      SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
                      java.util.Date d1=dateFormat1.parse(dateString1);
                      System.out.println(dateString1);
                      System.out.println(d1);
                      dateFormat1.applyPattern("yyyy-MM-dd");
                      strDjoin=dateFormat1.format(d1);
                      date1=Date.valueOf(strDjoin);
                  }catch(Exception e)
                  {
                      e.printStackTrace();
                  }*/

            try {
                /*                 */
                String sql =
                    "select * from HRM_EMP_CONTROLLING_OFFICE where EMPLOYEE_ID=?";
                ps = connection.prepareStatement(sql);
                ps.setInt(1, strEmpName);
                ResultSet rs = ps.executeQuery();


                /*                 */
                if (rs.next()) {
                    sql =
 "Update HRM_EMP_CONTROLLING_OFFICE set CONTROLLING_OFFICE_ID=?,CONTROLLING_DEPARTMENT_ID=? where EMPLOYEE_ID=?";

                    ps = connection.prepareStatement(sql);
                    ps.setInt(1, strOffName);
                    ps.setString(2, strDeptId);
                    //ps.setDate(3,date1);
                    ps.setInt(3, strEName);
                    ps.executeUpdate();
                } else {

                    sql =
 "insert into HRM_EMP_CONTROLLING_OFFICE(EMPLOYEE_ID,CONTROLLING_OFFICE_ID,CONTROLLING_DEPARTMENT_ID) values(?,?,?)";
                    ps = connection.prepareStatement(sql);
                    ps.setInt(1, strEName);
                    ps.setInt(2, strOffName);
                    ps.setString(3, strDeptId);
                    //ps.setDate(4,date1);
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
                    String sql =
                        "select office_id,office_name,Office_address1,office_address2,CITY_TOWN_NAME,DISTRICT_CODE,OFFICE_PHONE_NO,ADDL_PHONE_NOS,OFFICE_EMAIL_ID,ADDL_EMAIL_IDS,OFFICE_FAX_NO,ADDL_FAX_NOS,OFFICE_STD_CODE from com_mst_offices where Office_Id=?";
                    ps = connection.prepareStatement(sql);
                    ps.setInt(1, strOffName);
                    connection.clearWarnings();
                    try {
                        results = ps.executeQuery();

                        xml = xml + "<flag>success</flag>";
                        found = 0;
                        while (results.next()) {
                            System.out.println("here is ok");
                            xml =
 xml + "<OffName>" + results.getString("OFFICE_NAME") + "</OffName>";
                            xml =
 xml + "<OffAddr1>" + results.getString("OFFICE_ADDRESS1") +
   "</OffAddr1><OffAddr2>" + results.getString("OFFICE_ADDRESS2") +
   "</OffAddr2><City>" + results.getString("CITY_TOWN_NAME") + "</City>";
                            found++;
                        }
                        if (found == 0)
                            xml = xml + "<flag>failure</flag>";


                        results.close();

                    } catch (SQLException e) {
                        System.out.println("Exception in statement:" + e);
                        xml = xml + "<flag>failure</flag>";
                    }
                }
            } catch (SQLException e) {
                System.out.println("Exception in connection:" + e);
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
