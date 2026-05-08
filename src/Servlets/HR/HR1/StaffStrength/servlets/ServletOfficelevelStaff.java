package Servlets.HR.HR1.StaffStrength.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class ServletOfficelevelStaff extends HttpServlet {
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        System.out.println("hai");
        response.setContentType("text/xml");
        response.setHeader("cache-control", "no-cache");
        PrintWriter out = response.getWriter();
        String command = request.getParameter("command");
        int found = 0;
        String xml = "";
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

            ConnectionString =
                    strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
                    ":" + strsid.trim();

            Class.forName(strDriver.trim());
            connection =
                    DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                                                strdbpassword.trim());
            try {
                String sql = "";
                String sql2 = "";
                String order = request.getParameter("Order");
                System.out.println("order id is:" + order);
                int orderid = 0;
                if (order != "null") {
                    //orderid=Integer.parseInt(order);
                }
                if (command.equals("level")) {
                    sql =
 "select office_id,office_name,Office_Address1,Office_Address2,City_Town_Name from com_mst_offices where OFFICE_LEVEL_ID='" +
   request.getParameter("level") + "'";
                    //and b.order_id="+orderid+"
                    sql2 =
"select distinct b.diversion_from_office_id,a.office_name,a.Office_Address1,a.Office_Address2,a.City_Town_Name from com_mst_offices a,HRM_SS_DIVERSION_ORDERS_TMP b where a.office_id=b.diversion_from_office_id and a.office_level_id='" +
 request.getParameter("level") + "' ";
                } else if (command.equals("type")) {
                    sql =
 "select office_id,office_name,Office_Address1,Office_Address2,City_Town_Name from com_mst_offices where OFFICE_LEVEL_ID='" +
   request.getParameter("level") + "' and PRIMARY_WORK_ID='" +
   request.getParameter("type") + "'";
                    sql2 =
"select distinct b.diversion_from_office_id,a.office_name,a.Office_Address1,a.Office_Address2,a.City_Town_Name from com_mst_offices a,HRM_SS_DIVERSION_ORDERS_TMP b where a.office_id=b.diversion_from_office_id and a.office_level_id='" +
 request.getParameter("level") + "' and a.Primary_work_id='" +
 request.getParameter("type") + "'";

                } else {
                    sql =
 "select office_id,office_name,Office_Address1,Office_Address2,City_Town_Name from com_mst_offices";
                }
                Statement statement = connection.createStatement();
                connection.clearWarnings();
                try {
                    System.out.println("sql is : " + sql2);
                    ResultSet results = statement.executeQuery(sql2);
                    try {
                        xml = "<response><flag>success</flag>";
                        while (results.next()) {
                            //xml=xml+"<options><id>"+results.getInt("office_id") + "</id><name>" + results.getString("office_name") + "</name></options>";
                            xml =
 xml + "<options><id>" + results.getInt("diversion_from_OFFICE_ID") +
   "</id><name>" + results.getString("OFFICE_NAME") +
   "</name><officeAddress1>" + results.getString("OFFICE_ADDRESS1") +
   "</officeAddress1><officeAddress2>" + results.getString("OFFICE_ADDRESS2") +
   "</officeAddress2><officeAddress3>" + results.getString("CITY_TOWN_NAME") +
   "</officeAddress3></options>";
                            found++;
                        }
                        if (found == 0)
                            xml = "<response><flag>failure</flag>";

                        xml = xml + "</response>";
                    } catch (SQLException e) {
                        xml = "<response><flag>failure</flag></response>";
                    } finally {
                        results.close();
                    }
                } catch (SQLException e) {
                    System.out.println("Exception in statement:" + e);
                    xml = "<response><flag>failure</flag></response>";
                } finally {
                    statement.close();
                }
            } catch (SQLException e) {
                System.out.println("Exception in connection:" + e);
                xml = "<response><flag>failure</flag></response>";
            } finally {
                connection.close();
            }
        } catch (Exception e) {
            System.out.println("Exception :" + e);
            xml = "<response><flag>failure</flag></response>";
        }

        System.out.println("Xml is : " + xml);

        out.println(xml);
        out.close();
    }
}
