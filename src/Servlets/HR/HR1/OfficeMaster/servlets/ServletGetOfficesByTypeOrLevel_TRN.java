package Servlets.HR.HR1.OfficeMaster.servlets;

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

public class ServletGetOfficesByTypeOrLevel_TRN extends HttpServlet {
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

            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection

            Class.forName(strDriver.trim());
            connection =
                    DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                                                strdbpassword.trim());

            try {
                String sql = "";
                int offid = Integer.parseInt(request.getParameter("offid"));
                System.out.println("the office id is......." + offid);
                if ((command.equals("level")) &&
                    (request.getParameter("level").equalsIgnoreCase("CL"))) {
                    sql =
 "select CIRCLE_OFFICE_ID,CIRCLE_OFFICE_NAME from COM_MST_CIRCLES_HVIEW where REGION_OFFICE_ID=" +
   offid + "";
                    System.out.println("the sql is and i have selected circle " +
                                       sql);
                    Statement statement = connection.createStatement();
                    ResultSet results = statement.executeQuery(sql);
                    System.out.println("query executed");
                    xml = "<response><flag>success</flag>";
                    while (results.next()) {
                        //xml=xml+"<options><id>"+results.getInt("office_id") + "</id><name>" + results.getString("office_name") + "</name></options>";
                        xml =
 xml + "<options><id>" + results.getInt("CIRCLE_OFFICE_ID") + "</id><name>" +
   results.getString("CIRCLE_OFFICE_NAME") + "</name></options>";
                        found++;
                    }
                    if (found == 0)
                        xml = "<response><flag>failure</flag>";

                    xml = xml + "</response>";

                }

                else if ((command.equals("level")) &&
                         (request.getParameter("level").equalsIgnoreCase("RN"))) {
                    sql =
 "select REGION_OFFICE_ID,REGION_OFFICE_NAME from COM_MST_REGIONS_HVIEW ";
                    System.out.println("the sql is and i have selected circle " +
                                       sql);
                    Statement statement = connection.createStatement();
                    ResultSet results = statement.executeQuery(sql);
                    System.out.println("query executed");
                    xml = "<response><flag>success</flag>";
                    while (results.next()) {
                        //xml=xml+"<options><id>"+results.getInt("office_id") + "</id><name>" + results.getString("office_name") + "</name></options>";
                        xml =
 xml + "<options><id>" + results.getInt("REGION_OFFICE_ID") + "</id><name>" +
   results.getString("REGION_OFFICE_NAME") + "</name></options>";
                        found++;
                    }
                    if (found == 0)
                        xml = "<response><flag>failure</flag>";

                    xml = xml + "</response>";

                }

                else if ((command.equals("level")) &&
                         (request.getParameter("level").equalsIgnoreCase("AW"))) {
                    sql =
 "select AUDITWING_OFFICE_ID,AUDITWING_OFFICE_NAME from COM_MST_AUDIT_WING_HVIEW where REGION_OFFICE_ID=" +
   offid + "";
                    System.out.println("the sql is and i have selected circle " +
                                       sql);
                    Statement statement = connection.createStatement();
                    ResultSet results = statement.executeQuery(sql);
                    System.out.println("query executed");
                    xml = "<response><flag>success</flag>";
                    while (results.next()) {
                        //xml=xml+"<options><id>"+results.getInt("office_id") + "</id><name>" + results.getString("office_name") + "</name></options>";
                        xml =
 xml + "<options><id>" + results.getInt("AUDITWING_OFFICE_ID") +
   "</id><name>" + results.getString("AUDITWING_OFFICE_NAME") +
   "</name></options>";
                        found++;
                    }
                    if (found == 0)
                        xml = "<response><flag>failure</flag>";

                    xml = xml + "</response>";

                }

                else if ((command.equals("level")) &&
                         (request.getParameter("level").equalsIgnoreCase("LB"))) {
                    sql =
 "select LAB_OFFICE_ID,LAB_OFFICE_NAME from COM_MST_LABS_HVIEW where REGION_OFFICE_ID=" +
   offid + "";
                    System.out.println("the sql is and i have selected circle " +
                                       sql);
                    Statement statement = connection.createStatement();
                    ResultSet results = statement.executeQuery(sql);
                    System.out.println("query executed");
                    xml = "<response><flag>success</flag>";
                    while (results.next()) {
                        //xml=xml+"<options><id>"+results.getInt("office_id") + "</id><name>" + results.getString("office_name") + "</name></options>";
                        xml =
 xml + "<options><id>" + results.getInt("LAB_OFFICE_ID") + "</id><name>" +
   results.getString("LAB_OFFICE_NAME") + "</name></options>";
                        found++;
                    }
                    if (found == 0)
                        xml = "<response><flag>failure</flag>";

                    xml = xml + "</response>";

                }

                else if ((command.equals("type")) &&
                         (request.getParameter("level").equalsIgnoreCase("DN"))) {
                    sql =
 "select DIVISION_OFFICE_ID,DIVISION_OFFICE_NAME from\n" + "(\n" +
   "select office_id,office_short_name,PRIMARY_WORK_ID from com_mst_offices \n" +
   "where OFFICE_LEVEL_ID='" + request.getParameter("level") +
   "' and PRIMARY_WORK_ID='" + request.getParameter("type") + "' \n" +
   "and office_status_id not in('CL','RD','NC') \n" + ")a\n" +
   "left outer join\n" + "(\n" +
   "select DIVISION_OFFICE_ID,DIVISION_OFFICE_NAME,REGION_OFFICE_ID from COM_MST_DIVISIONS_HVIEW   \n" +
   ") b\n" + "on a.office_id=b.division_office_id where b.region_office_id=" +
   offid + "";
                    System.out.println("the sql is and i have selected circle " +
                                       sql);
                    Statement statement = connection.createStatement();
                    ResultSet results = statement.executeQuery(sql);
                    System.out.println("query executed");
                    xml = "<response><flag>success</flag>";
                    while (results.next()) {
                        //xml=xml+"<options><id>"+results.getInt("office_id") + "</id><name>" + results.getString("office_name") + "</name></options>";
                        xml =
 xml + "<options><id>" + results.getInt("DIVISION_OFFICE_ID") + "</id><name>" +
   results.getString("DIVISION_OFFICE_NAME") + "</name></options>";
                        found++;
                    }
                    if (found == 0)
                        xml = "<response><flag>failure</flag>";

                    xml = xml + "</response>";
                }

                else if ((command.equals("type")) &&
                         (request.getParameter("level").equalsIgnoreCase("SD"))) {
                    sql =
 "select SUBDIVISION_OFFICE_ID,SUBDIVISION_OFFICE_NAME from\n" + "(\n" +
   "select office_id,office_short_name,PRIMARY_WORK_ID from com_mst_offices \n" +
   "where OFFICE_LEVEL_ID='" + request.getParameter("level") +
   "' and PRIMARY_WORK_ID='" + request.getParameter("type") + "' \n" +
   "and office_status_id not in('CL','RD','NC') \n" + ")a\n" +
   "left outer join\n" + "(\n" +
   "select SUBDIVISION_OFFICE_ID,SUBDIVISION_OFFICE_NAME,REGION_OFFICE_ID from COM_MST_SUBDIVISIONS_HVIEW   \n" +
   ") b\n" +
   "on a.office_id=b.SUBDIVISION_OFFICE_ID where b.region_office_id=" + offid +
   "";
                    System.out.println("the sql is and i have selected circle " +
                                       sql);
                    Statement statement = connection.createStatement();
                    ResultSet results = statement.executeQuery(sql);
                    System.out.println("query executed");
                    xml = "<response><flag>success</flag>";
                    while (results.next()) {
                        //xml=xml+"<options><id>"+results.getInt("office_id") + "</id><name>" + results.getString("office_name") + "</name></options>";
                        xml =
 xml + "<options><id>" + results.getInt("SUBDIVISION_OFFICE_ID") +
   "</id><name>" + results.getString("SUBDIVISION_OFFICE_NAME") +
   "</name></options>";
                        found++;
                    }
                    if (found == 0)
                        xml = "<response><flag>failure</flag>";

                    xml = xml + "</response>";
                }

            } catch (Exception e) {
                System.out.println("Exception :" + e.getMessage());

            }

            /*   try
                    {
                        String sql="";
                        int offid=Integer.parseInt(request.getParameter("offid"));
                        System.out.println("the office id is.........................."+offid);
                        if(command.equals("level")) {
                            sql="select office_id,office_short_name,Office_Address1,Office_Address2,City_Town_Name from com_mst_offices where OFFICE_LEVEL_ID='" + request.getParameter("level") + "' and office_status_id not in('CL','RD','NC')  ";
                        }
                        else if(command.equals("type")) {
                            sql="select office_id,office_short_name,Office_Address1,Office_Address2,City_Town_Name from com_mst_offices where OFFICE_LEVEL_ID='" + request.getParameter("level") + "' and PRIMARY_WORK_ID='" + request.getParameter("type") + "' and office_status_id not in('CL','RD','NC') and office_id="+offid+"";
                        }
                        else {
                            sql="select office_id,office_short_name,Office_Address1,Office_Address2,City_Town_Name from com_mst_offices where office_id="+offid+"";
                        }
                      Statement statement=connection.createStatement();
                      connection.clearWarnings();
                      try
                      {
                        System.out.println("sql is : " + sql);
                        ResultSet results=statement.executeQuery(sql);
                        try
                        {
                            xml="<response><flag>success</flag>";
                            while(results.next()) {
                                //xml=xml+"<options><id>"+results.getInt("office_id") + "</id><name>" + results.getString("office_name") + "</name></options>";
                                 xml=xml+"<options><id>"+results.getInt("OFFICE_ID") + "</id><name>" + results.getString("OFFICE_short_NAME") + "</name><officeAddress1>"+results.getString("OFFICE_ADDRESS1")+ "</officeAddress1><officeAddress2>"+results.getString("OFFICE_ADDRESS2")+"</officeAddress2><officeAddress3>"+results.getString("CITY_TOWN_NAME")+"</officeAddress3></options>";
                                found++;
                            }
                            if(found==0)
                                xml="<response><flag>failure</flag>";

                            xml=xml+"</response>";
                        }
                        catch(SQLException e)
                        {
                            xml="<response><flag>failure</flag></response>";
                        }
                        finally
                        {
                          results.close();
                        }
                      }
                      catch(SQLException e)
                      {
                        System.out.println("Exception in statement:"+e);
                          xml="<response><flag>failure</flag></response>";
                      }
                      finally
                        {
                          statement.close();
                        }
                    }
                    catch(SQLException e)
                    {
                      System.out.println("Exception in connection:"+e);
                        xml="<response><flag>failure</flag></response>";
                    }
                    finally
                    {
                       connection.close();
                    }*/
        } catch (Exception e) {
            System.out.println("Exception :" + e);
            xml = "<response><flag>failure</flag></response>";
        }

        System.out.println("Xml is : " + xml);

        out.println(xml);
        out.close();
    }
}
