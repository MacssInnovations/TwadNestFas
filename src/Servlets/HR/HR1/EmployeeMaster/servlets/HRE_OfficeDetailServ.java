package Servlets.HR.HR1.EmployeeMaster.servlets;


import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class HRE_OfficeDetailServ extends HttpServlet {
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
            System.out.println("Exception :" + e);
        }
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

        //String strCriteria = "";
        int Office_Id = 0;
        Office_Id = Integer.parseInt(request.getParameter("OfficeId"));
        int found = 0;
        String xml = "";

        try {


            String deptid = "";
            deptid = request.getParameter("txtDept_Id");
            System.out.println("My dept id::" +
                               request.getParameter("txtDept_Id"));
            if (deptid.equals("TWAD")) {
                System.out.println("TWAD---->");
                String sql =
                    "select a.office_status_id,a.office_id,a.office_name,a.Office_address1,a.office_address2,a.CITY_TOWN_NAME,a.DISTRICT_CODE,a.OFFICE_PHONE_NO,a.ADDL_PHONE_NOS,a.OFFICE_EMAIL_ID,a.ADDL_EMAIL_IDS,a.OFFICE_FAX_NO,a.ADDL_FAX_NOS,a.OFFICE_STD_CODE,d.DISTRICT_NAME,a.office_level_id from com_mst_offices a left outer join com_mst_districts d on d.DISTRICT_CODE=a.DISTRICT_CODE  where a.Office_Id=?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, Office_Id);
                connection.clearWarnings();
                try {
                    ResultSet results = statement.executeQuery();
                    try {
                        xml = "<response><flag>success</flag>";
                        found = 0;
                        while (results.next()) {
                            //xml=xml+"<options><id>"+results.getInt("office_id") + "</id><name>" + results.getString("office_name") + "</name></options>";
                            xml =
 xml + "<options><id>" + results.getInt("OFFICE_ID") + "</id><name>" +
   results.getString("OFFICE_NAME") + "</name><officeAddress1>" +
   results.getString("OFFICE_ADDRESS1") + "</officeAddress1><officeAddress2>" +
   results.getString("OFFICE_ADDRESS2") + "</officeAddress2><officeAddress3>" +
   results.getString("CITY_TOWN_NAME") + "</officeAddress3><District>" +
   results.getInt("DISTRICT_CODE") + "</District><district_name>" +
   results.getString("DISTRICT_NAME") + "</district_name><PhoneNo>" +
   results.getString("OFFICE_PHONE_NO") + "</PhoneNo><AddPhone>" +
   results.getString("ADDL_PHONE_NOS") + "</AddPhone><EmailId>" +
   results.getString("OFFICE_EMAIL_ID") + "</EmailId><AddEmail>" +
   results.getString("ADDL_EMAIL_IDS") + "</AddEmail><FaxNo>" +
   results.getString("OFFICE_FAX_NO") + "</FaxNo><AddFaxNo>" +
   results.getString("ADDL_FAX_NOS") + "</AddFaxNo><StdCode>" +
   results.getString("OFFICE_STD_CODE") + "</StdCode><OfficeStatusId>" +
   results.getString("office_status_id") + "</OfficeStatusId></options>";


                            if (results.getString("office_level_id") != null &&
                                results.getString("office_level_id").equalsIgnoreCase("HO")) {
                                xml = xml + "<WING>yes</WING>";

                                String sql1 =
                                    "select OFFICE_WING_SINO,WING_NAME from COM_OFFICE_WINGS  where Office_Id=?";
                                PreparedStatement statement1 =
                                    connection.prepareStatement(sql1);
                                statement1.setInt(1, Office_Id);
                                ResultSet results1 = statement1.executeQuery();
                                while (results1.next()) {
                                    xml =
 xml + "<wingoptions><wingid>" + results1.getInt("OFFICE_WING_SINO") +
   "</wingid><wingname>" + results1.getString("WING_NAME") +
   "</wingname></wingoptions>";

                                }

                            } else {
                                xml = xml + "<WING>no</WING>";
                            }


                            try {
                                int desigid =
                                    Integer.parseInt(request.getParameter("disigid"));
                                System.out.println("desigantion id issssssss:" +
                                                   desigid);
                                boolean flag = true;
                                PreparedStatement ps =
                                    connection.prepareStatement("SELECT * FROM HRM_EMP_CURRENT_POSTING WHERE office_id=? and designation_id=? ");
                                //ps.setInt(1,empid);
                                ps.setInt(1, Office_Id);
                                ps.setInt(2, desigid);
                                ResultSet rs = ps.executeQuery();
                                if (rs.next()) {
                                    xml = xml + "<xists>yes</xists>";
                                    //flag=false;
                                } else {
                                    xml = xml + "<xists>no</xists>";
                                }

                            } catch (Exception e) {
                                System.out.println("catch........" + e);
                                //xml=xml+"<flag></flag>";
                            }


                            found++;
                        }
                        if (found == 0)
                            xml = "<response><flag>failure</flag>";

                        xml = xml + "</response>";
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

            else {
                System.out.println("else---->");
                String sql =
                    "select OTHER_DEPT_OFFICE_ID,OTHER_DEPT_OFFICE_NAME,ADDRESS1,ADDRESS2,CITY_TOWN from HRM_MST_OTHER_DEPT_OFFICES where OTHER_DEPT_OFFICE_ID=? and OTHER_DEPT_ID=?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, Office_Id);
                statement.setString(2, deptid);
                connection.clearWarnings();
                try {
                    ResultSet results = statement.executeQuery();
                    try {
                        xml = "<response><flag>success</flag>";
                        found = 0;
                        while (results.next()) {
                            //xml=xml+"<options><id>"+results.getInt("office_id") + "</id><name>" + results.getString("office_name") + "</name></options>";
                            xml =
 xml + "<options><id>" + results.getInt("OTHER_DEPT_OFFICE_ID") +
   "</id><name>" + results.getString("OTHER_DEPT_OFFICE_NAME") +
   "</name><officeAddress1>" + results.getString("ADDRESS1") +
   "</officeAddress1><officeAddress2>" + results.getString("ADDRESS2") +
   "</officeAddress2><officeAddress3>" + results.getString("CITY_TOWN") +
   "</officeAddress3></options>";
                            found++;
                        }
                        if (found == 0)
                            xml = "<response><flag>failure</flag>";

                        xml = xml + "</response>";
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


        System.out.println("Xml is : " + xml);
        response.setContentType("text/xml");
        response.setHeader("cache-control", "no-cache");


        out.println(xml);
        out.close();
    }
}
