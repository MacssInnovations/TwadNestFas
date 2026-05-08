package Servlets.SysAdmin.servlets;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.PrintWriter;
import java.io.IOException;

import java.sql.*;

import java.util.*;

public class ServletRolesMenu extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    private Connection connection = null;
    private Statement statement = null;
    private ResultSet results = null;
    private ResultSet results1 = null;
    private PreparedStatement ps = null;
    private PreparedStatement ps1 = null;


    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        /// opening connection to the database
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
            System.out.println(" connection string in Assigning Menus: " +
                               ConnectionString);
            Class.forName(strDriver.trim());
            connection =
                    DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                                                strdbpassword.trim());

        } catch (Exception e) {
            System.out.println("Exception in opening connection" + e);
        }
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        String strCommand = "";
        String xml = "";
        String xml1 = "";
        PrintWriter pw = response.getWriter();
        response.setContentType("text/xml");
        response.setHeader("cache-control", "no-cache");
        HttpSession session = request.getSession(true);
        String userid = (String)(session.getAttribute("userid"));
        // System.out.println("servlet called");

        String str = "";
        try {
            str = request.getParameter("Type");

            //  System.out.println(str);

        }

        catch (Exception e) {
            e.printStackTrace();
        }
        /*  if(str.equals("role"))
    {
    try
    {
      String strmajor=request.getParameter("RID");

    String sql="select MAJOR_SYSTEM_ID,Intranet_Major_System_Desc from SECURITY_MST_INTRANET_MAJOR_SYSTEM where Role_Id=?";
    ps=connection.prepareStatement(sql);
    ps.setString(1,strmajor);
    results=ps.executeQuery();
    while(results.next())
    {
      String temp=results.getString("Intranet_Major_System_Id");
      xml=xml+"<option><desc>"+results.getString("Intranet_Major_System_Desc")+"</desc><id>"+temp+"</id></option>";
    }

    }

    catch(Exception e)
    {
    System.out.println(e);
    }
    }*/
        if (str.equals("major")) {
            try {
                String strmajor = request.getParameter("First");
                //System.out.println(strmajor);

                //System.out.println("inside query loop");

                String sql =
                    "select MINOR_SYSTEM_ID,MINOR_SYSTEM_DESC from SEC_MST_MINOR_SYSTEMS where MAJOR_SYSTEM_ID=?";
              System.out.println("sql:::"+sql);
                ps = connection.prepareStatement(sql);
                ps.setString(1, strmajor);
                results = ps.executeQuery();
                while (results.next()) {
                    String temp = results.getString("MINOR_SYSTEM_ID");
                    xml =
 xml + "<option><desc>" + results.getString("MINOR_SYSTEM_DESC") +
   "</desc><id>" + temp + "</id></option>";
                }

            }

            catch (Exception e) {
                System.out.println(e);
            }
        }

        else if (str.equals("minor")) {
            try {
                String strmajor = request.getParameter("First");
                String strminor = request.getParameter("Second");
                //System.out.println(strmajor);

                //System.out.println("inside query loop");

                String sql =
                    "select SUB_SYSTEM_ID,SUB_SYSTEM_DESC from SEC_MST_SUBSYSTEMS where MAJOR_SYSTEM_ID=? and MINOR_SYSTEM_ID=?";
                ps = connection.prepareStatement(sql);
                ps.setString(1, strmajor);
                ps.setString(2, strminor);
                results = ps.executeQuery();
                while (results.next()) {
                    String temp = results.getString("SUB_SYSTEM_ID");
                    xml =
 xml + "<option><desc>" + results.getString("SUB_SYSTEM_DESC") +
   "</desc><id>" + temp + "</id></option>";
                }

            }

            catch (Exception e) {
                System.out.println(e);
            }
        }

        else if (str.equals("SubSystem")) {
            try {
                String strRoleId = request.getParameter("RoleId");
                String strMajor = request.getParameter("First");
                String strMinor = request.getParameter("Second");
                String strSub = request.getParameter("Third");

                String sql =
                    "select MENU_ID,MENU_DESC from SEC_MST_INTRANET_MENUS where MAJOR_SYSTEM_ID=? And MINOR_SYSTEM_ID=? And SUB_SYSTEM_ID=? AND Menu_Id not in(select sec_Mst_Intranet_Menus.Menu_Id from sec_Mst_Role_Menus,sec_Mst_Intranet_menus where";

                sql =
 sql + " sec_Mst_Role_Menus.Major_System_Id=sec_Mst_Intranet_Menus.Major_System_Id And";
                sql =
 sql + " sec_Mst_Role_Menus.Minor_System_Id=sec_Mst_Intranet_Menus.Minor_System_Id And";
                sql =
 sql + " sec_Mst_Role_Menus.Sub_System_Id=sec_Mst_Intranet_Menus.Sub_System_Id And";
                sql =
 sql + " sec_Mst_Role_Menus.Menu_Id=sec_Mst_Intranet_Menus.Menu_Id And";
                sql =
 sql + " sec_Mst_Role_Menus.Role_Id=? And sec_Mst_Role_Menus.Major_System_Id=? And sec_Mst_Role_Menus.Minor_System_Id=?";
                sql = sql + " And sec_Mst_Role_Menus.Sub_System_Id=?)";

                //String sql="select Intranet_Menu_Id,Intranet_Menu_Desc from SECURITY_MST_INTRANET_MENUS where Intranet_Sub_System_Id not in(select Intranet_Sub_System_Id from SECURITY_MST_ROLE_MENUS where Intranet_Sub_System_Id=?)";
                //String sql="select MENU_ID,MENU_DESC from SEC_MST_INTRANET_MENUS where MAJOR_SYSTEM_ID=? And MINOR_SYSTEM_ID=? And SUB_SYSTEM_ID=? ";
                ps = connection.prepareStatement(sql);
                ps.setString(1, strMajor);
                ps.setString(2, strMinor);
                ps.setString(3, strSub);
                ps.setInt(4, Integer.parseInt(strRoleId));
                ps.setString(5, strMajor);
                ps.setString(6, strMinor);
                ps.setString(7, strSub);
                results = ps.executeQuery();

                while (results.next()) {
                    String temp1 = results.getString("MENU_ID");
                    xml =
 xml + "<option><desc>" + results.getString("MENU_DESC") + "</desc><id>" +
   temp1 + "</id></option>";
                }

            } catch (Exception e) {
                System.out.println(e);
            }

        }

        pw.write("<select>" + xml + "</select>");
        System.out.println("xml is" + xml);

    }

}


