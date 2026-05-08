package Servlets.PMS.PMS1.ContractorsInfoSys.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.*;

import javax.servlet.*;
import javax.servlet.http.*;

import java.util.*;

public class ClassServlet extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";
    private Connection connection = null;
    private Statement statement = null;
    private ResultSet results = null;
    private ResultSet results1 = null;
    private PreparedStatement ps = null;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
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

//            ConnectionString =
//                    strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
//                    ":" + strsid.trim();
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection

            Class.forName(strDriver.trim());
            connection =
                    DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                                                strdbpassword.trim());

            try {
                statement = connection.createStatement();
                connection.clearWarnings();
            } catch (SQLException e) {
                System.out.println("Exception in creating statement:" + e);
            }
        } catch (Exception e) {
            System.out.println("Exception in openeing connection:" + e);
        }

    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);
        String strCommand = "";
        String xml = "";
        int strClassId = 0;
        String strClassName = "";
        response.setContentType("text/xml");
        PrintWriter pw = response.getWriter();
        response.setHeader("Cache-Control", "no-cache");
        try {
            strCommand = request.getParameter("command");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            strClassId = Integer.parseInt(request.getParameter("ClassId"));
            strClassName = request.getParameter("ClassName");
        } catch (Exception e) {
            System.out.println("in getting values **** " + e);
        }

        if (strCommand.equalsIgnoreCase("Delete")) {
            xml = "<response><command>Delete</command>";
            String sql = "delete from PMS_MST_CON_CLASS where REGN_CLASS_ID=?";
            try {
                ps = connection.prepareStatement(sql);
                ps.setInt(1, strClassId);
                ps.executeUpdate();
                xml =
 xml + "<flag>success</flag><ClassId>" + strClassId + "</ClassId>";
                ps.close();
            } catch (Exception e) {
                System.out.println("exception in the delete" + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }

        else if (strCommand.equalsIgnoreCase("Update")) {
            xml = "<response><command>Update</command>";
            String sql =
                "update PMS_MST_CON_CLASS set REGN_CLASS_DESC=? where REGN_CLASS_ID=?";
            try {
                ps = connection.prepareStatement(sql);
                ps.setInt(2, strClassId);
                ps.setString(1, strClassName);
                ps.executeUpdate();
                xml = xml + "<flag>success</flag>";
                ps.close();
            } catch (Exception e) {
                System.out.println("exception in the update" + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }

        else if (strCommand.equalsIgnoreCase("Add")) {
            xml = "<response><command>Add</command>";
            String sql =
                "insert into PMS_MST_CON_CLASS(REGN_CLASS_ID,REGN_CLASS_DESC) values(?,?)";
            try {
                ps = connection.prepareStatement(sql);
                ps.setInt(1, strClassId);
                ps.setString(2, strClassName);
                ps.executeUpdate();
                xml = xml + "<flag>success</flag>";
                ps.close();
            } catch (Exception e) {
                System.out.println("exception in the add" + e);
                xml = xml + "<flag>failure</flag>";
            }

            xml = xml + "</response>";
        }

        else if (strCommand.equals("check")) {
            String sxml = "<response><command>check</command>";
            try {
                String sql =
                    "SELECT * FROM PMS_MST_CON_CLASS WHERE REGN_CLASS_ID=?";
                try {
                    ps = connection.prepareStatement(sql);
                    ps.setInt(1, strClassId);
                    results = ps.executeQuery();
                    int i = 0;
                    while (results.next()) {
                        i++;
                    }
                    if (i == 0) {
                        xml = sxml + "<flag>failure</flag>";
                    } else {
                        xml = sxml + "<flag>success</flag>" + xml;
                    }
                } catch (Exception ae) {
                    System.out.println("Exception is:the check" + ae);

                }
            } catch (Exception e) {
                System.out.println("exce ****2 vv" + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }
        /*else if(strCommand.equals("Load"))
              {
              xml="<response><command>Load</command>";
                  try {
                          results1 = statement.executeQuery("SELECT REGN_CLASS_ID FROM PMS_MST_CON_CLASS GROUP BY REGN_CLASS_ID HAVING REGN_CLASS_ID =(select max(REGN_CLASS_ID) from PMS_MST_CON_CLASS)");
                             while(results1.next())
                           {
                              int RegClass=results1.getInt("REGN_CLASS_ID");
                                RegClass=RegClass+1;
                               xml=xml+"<flag>success</flag>";
                               xml=xml+"<ClassId>" + RegClass + "</ClassId>";
                           }
                           results.close();
                           response.setHeader("cache-control","no-cache");
                      }
                      catch(Exception e1)
                      {             System.out.println("Exception is in Load"+e1);
                          xml=xml+"<flag>failure</flag>";
                      }
                  xml=xml+"</response>";
              } */

        System.out.println("xml is : " + xml);
        pw.write(xml);
        pw.flush();
        pw.close();

    }
}
