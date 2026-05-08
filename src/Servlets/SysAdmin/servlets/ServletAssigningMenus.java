package Servlets.SysAdmin.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.*;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class ServletAssigningMenus extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";
    private PreparedStatement ps = null;
    private ResultSet results = null;
    private Connection connection = null;

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


        } catch (Exception e) {
            System.out.println("Exception in Bundle");
        }
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();


        String strCommand = "";
        String xml = "";
        PrintWriter pw = response.getWriter();
        response.setContentType("text/xml");
        response.setHeader("cache-control", "no-cache");
        HttpSession session = request.getSession(false);
        // String userid=(String)(session.getAttribute("userid"));

        try {
            strCommand = request.getParameter("command");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (strCommand.equalsIgnoreCase("Major")) {
            xml = "<response><command>Major</command>";

            String strMajorName = "";
            boolean sound = false;
            try {

                strMajorName = request.getParameter("txtMajorId");
            } catch (Exception e) {
                System.out.println("exce **** " + e);
            }


            // insert values into the table

            String sql =
                "Select MINOR_SYSTEM_ID,MINOR_SYSTEM_SHORT_DESC from SEC_MST_MINOR_SYSTEMS where MAJOR_SYSTEM_ID=?";

            try {
                System.out.println("inside the try of insert");

                ps = connection.prepareStatement(sql);
                ps.setString(1, strMajorName);

                results = ps.executeQuery();
                while (results.next()) {
                    System.out.println("before xml");
                    xml =
 xml + "<option><MinorId>" + results.getString("MINOR_SYSTEM_ID") +
   "</MinorId><MinorName>" + results.getString("MINOR_SYSTEM_SHORT_DESC") +
   "</MinorName></option>";
                    System.out.println("after xml" + xml);
                }


            } catch (Exception e) {
                System.out.println("exce ****2 vv" + e);
                // cannot insert values
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";

        }


        else if (strCommand.equalsIgnoreCase("Minor")) {
            xml = "<response><command>Minor</command>";

            String strMajorName = "";
            String strMinorName = "";
            boolean sound = false;
            try {

                strMajorName = request.getParameter("txtMajorId");
                strMinorName = request.getParameter("txtMinorId");
            } catch (Exception e) {
                System.out.println("exce **** " + e);
            }


            // insert values into the table

            String sql =
                "Select SUB_SYSTEM_ID,SUB_SYSTEM_SHORT_DESC  from SEC_MST_SUBSYSTEMS where MAJOR_SYSTEM_ID=? and MINOR_SYSTEM_ID=?";

            try {
                System.out.println("inside the try of insert");

                ps = connection.prepareStatement(sql);
                ps.setString(1, strMajorName);
                ps.setString(2, strMinorName);
                results = ps.executeQuery();
                while (results.next()) {
                    System.out.println("before xml");
                    xml =
 xml + "<option><SubSysId>" + results.getString("SUB_SYSTEM_ID") +
   "</SubSysId><SubSysName>" + results.getString("SUB_SYSTEM_SHORT_DESC") +
   "</SubSysName></option>";
                    System.out.println("after xml" + xml);
                }


            } catch (Exception e) {
                System.out.println("exce ****2 vv" + e);
                // cannot insert values
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";

        }

        /*else if(strCommand.equalsIgnoreCase("Delete"))
             {
                   // read the parameter id

                   xml="<response><command>Delete</command>";
                   int strRId=0;
                   String strRName="";
                   try
                   {
                     strRId = Integer.parseInt(request.getParameter("txtRId"));

                   }
                   catch(Exception e)
                   {
                     e.printStackTrace();
                   }

             // delete the row from table
                   try
                   {
                       String sql="delete from SEC_MST_ROLES where Role_Id=?";

                       ps=connection.prepareStatement(sql);
                       ps.setInt(1,strRId);
                       int j=ps.executeUpdate();
                       // on sucess
                       // build and return the xml with id
                       if( j>=1)
                       {
                           xml=xml+"<flag>success</flag><RoleId>"+strRId+"</RoleId>";
                           connection.commit();
                       }
                       else
                       {
                           xml=xml+"<flag>failure</flag>";
                       }
                   }
                   catch(Exception e)
                   {
                       xml=xml+"<flag>failure</flag>";
                   }
                   xml=xml+"</response>";

             }
          else if(strCommand.equalsIgnoreCase("Update"))
          {
                System.out.println("This is within Update command");
                xml="<response><command>Update</command>";
                  int strRId=0;
                  String strRName="";
                  try
                  {
                    strRId = Integer.parseInt(request.getParameter("txtRId"));
                    System.out.println(strRId);
                    strRName=request.getParameter("txtRName");
                      System.out.println(strRName);
                  }
                  catch(Exception e)
                  {
                    e.printStackTrace();
                  }
          try
          {  System.out.println("This is within Update command in the ps try");
              ps=connection.prepareStatement("update SEC_MST_ROLES set Role_Name=? where Role_Id=?");

              ps.setString(1,strRName);
              ps.setInt(2,strRId);

          int i=ps.executeUpdate();
          if(i>=1)
            {
                xml=xml+"<flag>success</flag>";
                connection.commit();
            }
            else
            {
                xml=xml+"<flag>failure</flag>";
            }
          }catch(Exception e)
          {
          xml=xml+"<flag>failure</flag>";
          e.printStackTrace();
          }
          // on sucess
          // build and return the xml with entire values
          xml=xml+"</response>";
          }*/
        System.out.println("xml is" + xml);
        pw.write(xml);
        pw.flush();
        pw.close();


    }
}
