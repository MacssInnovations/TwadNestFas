package Servlets.SysAdmin.servlets;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.PrintWriter;
import java.io.IOException;

import java.sql.*;

import java.util.ResourceBundle;

public class ServletRolesMenu1 extends HttpServlet {
    private Connection connection = null;
    private Statement statement = null;
    private ResultSet results = null;
    private PreparedStatement ps = null;

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
            Class.forName(strDriver.trim());
            connection =
                    DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                                                strdbpassword.trim());
            System.out.println(" connection string : " + ConnectionString);
        } catch (Exception e) {
            System.out.println("Exception in opening connection" + e);
        }
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {

        String strCommand = "";
        String xml = "";
        response.setContentType("text/xml");
        PrintWriter pw = response.getWriter();
        response.setHeader("Cache-Control", "no-cache");
        try {
            strCommand = request.getParameter("command");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (strCommand.equalsIgnoreCase("Add")) {

            xml = "<response><command>Add</command>";
            String strRId = "";
            String strMajor = "";
            String strMinor = "";
            String strSub = "";
            String strMenu = "";

            try {
                strRId = request.getParameter("RId");
                strMajor = request.getParameter("Major");
                strMinor = request.getParameter("Minor");
                strSub = request.getParameter("SubSys");
                strMenu = request.getParameter("Menu");
            } catch (Exception e) {
                System.out.println("exce **** " + e);
            }
            // insert values into the table
            String sql =
                "insert into SEC_MST_ROLE_MENUS(Role_Id,Major_System_Id,Minor_System_Id,Sub_System_Id,Menu_Id,UPDATED_BY_USER_ID,UPDATED_DATE) values(?,?,?,?,?,?,?)";

            try {

                HttpSession session = request.getSession(false);
                String updatedby = (String)session.getAttribute("UserId");
                long l = System.currentTimeMillis();
                Timestamp ts = new Timestamp(l);

                ps = connection.prepareStatement(sql);
                ps.setString(1, strRId);
                ps.setString(2, strMajor);
                ps.setString(3, strMinor);
                ps.setString(4, strSub);
                ps.setString(5, strMenu);

                ps.setString(6, updatedby);
                ps.setTimestamp(7, ts);

                ps.executeUpdate();

                xml =
 xml + "<flag>success</flag><id>" + strRId + "</id><major>" + strMajor +
   "</major><minor>" + strMinor + "</minor><sub>" + strSub + "</sub><menu>" +
   strMenu + "</menu>";
                ps.close();
            } catch (Exception e) {
                System.out.println("exce ****2 vv" + e);
                // cannot insert values
                xml = xml + "<flag>failure</flag>";
            }
            // on sucess
            xml = xml + "</response>";
            // build and return the xml with entire values
        }


        else if (strCommand.equalsIgnoreCase("Delete")) {
            // read the parameter id

            xml = "<response><command>Delete</command>";
            int strRId = 0;
            String strMajor = null;
            String strMinor = null;
            String strSub = null;
            String strMenu = null;
            try {
                strRId = Integer.parseInt(request.getParameter("RId"));
                strMajor = request.getParameter("Major");
                strMinor = request.getParameter("Minor");
                strSub = request.getParameter("SubSys");
                strMenu = request.getParameter("Menu");
                System.out.println("RoleId" + strRId);
                System.out.println("MajorId" + strMajor);
                System.out.println("MinorId" + strMinor);
                System.out.println("SubId" + strSub);
                System.out.println("MenuId" + strMenu);

            } catch (Exception e) {
                e.printStackTrace();
            }
            // delete the row from table
            try {
                String sql =
                    "delete from SEC_MST_ROLE_MENUS where Role_Id=? And Major_System_Id=? And Minor_System_Id=? And Sub_System_Id=? And Menu_Id=?";
                ps = connection.prepareStatement(sql);
                ps.setInt(1, strRId);
                ps.setString(2, strMajor);
                ps.setString(3, strMinor);
                ps.setString(4, strSub);
                ps.setString(5, strMenu);
                int i = ps.executeUpdate();
                // on sucess
                // build and return the xml with id
                if (i >= 1) {
                    xml =
 xml + "<flag>success</flag><id>" + strRId + "</id><major>" + strMajor +
   "</major><Sub>" + strSub + "</Sub><Menu>" + strMenu + "</Menu>";
                } else {
                    xml = xml + "<flag>failure</flag>";
                }
                ps.close();
            } catch (Exception e) {
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";

        }


        else if (strCommand.equalsIgnoreCase("check")) {

            xml = "<response><command>check</command>";

            String strRid = request.getParameter("RId");
            String strMajorid = request.getParameter("Major");
            String strMinorid = request.getParameter("Minor");
            String strSubid = request.getParameter("SubSys");
            String strMenuid = request.getParameter("Menu");
            int rid = 0;

            rid = Integer.parseInt(strRid);
            try {

                String sql =
                    "select * from SEC_MST_ROLE_MENUS where Role_Id=? and  Major_System_Id=? and Minor_System_Id=? and  Sub_System_Id=? and  Menu_Id=?";
                ps = connection.prepareStatement(sql);
                ps.setInt(1, rid);
                ps.setString(2, strMajorid);
                ps.setString(3, strMinorid);
                ps.setString(4, strSubid);
                ps.setString(5, strMenuid);

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {

                    xml = xml + "<flag>success</flag>";
                    xml = xml + "<value> ";
                    xml =
 xml + "<Roleid>" + rs.getInt("Role_Id") + "</Roleid><Majorid>" +
   rs.getString("Major_System_Id") + "</Majorid><Minorid>" +
   rs.getString("Minor_System_Id") + "</Minorid><Subid>" +
   rs.getString("Sub_System_Id") + "</Subid><Menuid>" +
   rs.getString("Menu_Id") + "</Menuid>";
                    xml = xml + "</value>";

                }


                else {
                    xml = xml + "<flag>failure</flag>";
                }
                rs.close();
                ps.close();

            } catch (Exception e) {
                System.out.println("exce ****2 vv" + e.getStackTrace());
                System.out.println("exce ****2 vv" + e.getMessage());
                // cannot insert values
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";

        }

        else if (strCommand.equals("Load")) {
            String sxml = "<response><command>Load</command>";
            String strRId = request.getParameter("RId");
            String strMajor = request.getParameter("Major");
            String strMinor = request.getParameter("Minor");
            String strSub = request.getParameter("Sub");
            System.out.println("strRId" + strRId);
            System.out.println("strMajor" + strMajor);
            System.out.println("strMnior" + strMinor);
            System.out.println("strSubs" + strSub);
            try {
                /*
          String sql="select sec_Mst_Role_Menus.Role_Id,sec_Mst_Role_Menus.Major_System_Id,sec_Mst_Role_Menus.Minor_System_Id,sec_Mst_Role_Menus.Sub_System_Id,sec_Mst_Role_Menus.Menu_Id,sec_Mst_Intranet_Menus.Menu_Id,sec_Mst_Intranet_Menus.Menu_Desc from sec_Mst_Role_Menus,sec_Mst_Intranet_menus where";
          sql=sql+" sec_Mst_Role_Menus.Major_System_Id=sec_Mst_Intranet_Menus.Major_System_Id And";
          sql=sql+" sec_Mst_Role_Menus.Minor_System_Id=sec_Mst_Intranet_Menus.Minor_System_Id And";
          sql=sql+" sec_Mst_Role_Menus.Sub_System_Id=sec_Mst_Intranet_Menus.Sub_System_Id And";
          sql=sql+" sec_Mst_Role_Menus.Menu_Id=sec_Mst_Intranet_Menus.Menu_Id And";
          sql=sql+" sec_Mst_Role_Menus.Role_Id=? And sec_Mst_Role_Menus.Major_System_Id=? And sec_Mst_Role_Menus.Minor_System_Id=? And";
          sql=sql+" sec_Mst_Role_Menus.Sub_System_Id=?";
        */
                String sql =
                    "select SEC_MST_ROLE_MENUS.ROLE_ID,SEC_MST_ROLE_MENUS.MAJOR_SYSTEM_ID,SEC_MST_ROLE_MENUS.MINOR_SYSTEM_ID,SEC_MST_ROLE_MENUS.SUB_SYSTEM_ID,SEC_MST_ROLE_MENUS.MENU_ID,SEC_MST_INTRANET_MENUS.MENU_ID,SEC_MST_INTRANET_MENUS.MENU_DESC from SEC_MST_ROLE_MENUS,SEC_MST_INTRANET_MENUS where";
                sql =
 sql + " SEC_MST_ROLE_MENUS.MAJOR_SYSTEM_ID=SEC_MST_INTRANET_MENUS.MAJOR_SYSTEM_ID And";
                sql =
 sql + " SEC_MST_ROLE_MENUS.MINOR_SYSTEM_ID=SEC_MST_INTRANET_MENUS.MINOR_SYSTEM_ID And";
                sql =
 sql + " SEC_MST_ROLE_MENUS.SUB_SYSTEM_ID=SEC_MST_INTRANET_MENUS.SUB_SYSTEM_ID And";
                sql =
 sql + " SEC_MST_ROLE_MENUS.MENU_ID=SEC_MST_INTRANET_MENUS.MENU_ID And";
                sql =
 sql + " SEC_MST_ROLE_MENUS.ROLE_ID=? And SEC_MST_ROLE_MENUS.MAJOR_SYSTEM_ID=? And SEC_MST_ROLE_MENUS.MINOR_SYSTEM_ID=? And";
                sql = sql + " SEC_MST_ROLE_MENUS.SUB_SYSTEM_ID=?";


                /*
        String sql="SELECT SEC_MST_ROLE_MENUS.Role_Id, SEC_MST_ROLES.Role_Name,SEC_MST_MAJOR_SYSTEMS.Major_System_Id,";
          sql=sql+" SEC_MST_MAJOR_SYSTEMS.Major_System_Desc,SEC_MST_MINOR_SYSTEMS.MINOR_System_Id,";
          sql=sql+" SEC_MST_MINOR_SYSTEMS.MINOR_System_Desc,SEC_MST_INTRANET_MENUS.Menu_Id,SEC_MST_INTRANET_MENUS.Menu_Desc,";

          sql=sql+" SEC_MST_SUBSYSTEMS.Sub_System_Id, SEC_MST_SUBSYSTEMS.Sub_System_Desc FROM  SEC_MST_ROLE_MENUS,";
          sql=sql+" SEC_MST_ROLES,SEC_MST_MAJOR_SYSTEMS,SEC_MST_MINOR_SYSTEMS,SEC_MST_INTRANET_MENUS,SEC_MST_SUBSYSTEMS WHERE  SEC_MST_ROLE_MENUS.Role_Id=? And";

          sql=sql+" SEC_MST_ROLE_MENUS.Major_System_Id=? And SEC_MST_ROLE_MENUS.Major_System_Id=SEC_MST_SUBSYSTEMS.Major_System_Id And";
           sql=sql+" SEC_MST_ROLE_MENUS.MINOR_System_Id=? And";
          sql=sql+" SEC_MST_ROLE_MENUS.Sub_System_Id=? And  SEC_MST_ROLE_MENUS.Role_Id= SEC_MST_ROLES.Role_Id And";

          sql=sql+" SEC_MST_ROLE_MENUS.Major_System_Id= SEC_MST_MAJOR_SYSTEMS.Major_System_Id And";
          sql=sql+" SEC_MST_ROLE_MENUS.MINOR_System_Id= SEC_MST_MINOR_SYSTEMS.MINOR_System_Id And";
          sql=sql+" SEC_MST_ROLE_MENUS.MINOR_System_Id= SEC_MST_SUBSYSTEMS.MINOR_System_Id And ";
          sql=sql+" SEC_MST_ROLE_MENUS.Sub_System_Id= SEC_MST_SUBSYSTEMS.Sub_System_Id And";

           sql=sql+" SEC_MST_MAJOR_SYSTEMS. Major_System_Id= SEC_MST_INTRANET_MENUS.Major_System_Id And";
           sql=sql+" SEC_MST_SUBSYSTEMS.Sub_System_Id= SEC_MST_INTRANET_MENUS.Sub_System_Id And  SEC_MST_ROLE_MENUS.Menu_Id=";

          sql=sql+" SEC_MST_INTRANET_MENUS.Menu_Id";
         */


                /*String sql="SELECT SEC_MST_ROLE_MENUS.ROLE_ID, SEC_MST_ROLE_MENUS.MAJOR_SYSTEM_ID, SEC_MST_ROLE_MENUS.MINOR_SYSTEM_ID, SEC_MST_ROLE_MENUS.SUB_SYSTEM_ID, SEC_MST_ROLE_MENUS.MENU_ID,SEC_MST_INTRANET_MENUS.MENU_DESC  FROM SEC_MST_ROLE_MENUS, SEC_MST_INTRANET_MENUS WHERE SEC_MST_ROLE_MENUS.MAJOR_SYSTEM_ID=SEC_MST_INTRANET_MENUS.MAJOR_SYSTEM_ID AND ";
          sql=sql+" SEC_MST_ROLE_MENUS.MINOR_SYSTEM_ID=SEC_MST_INTRANET_MENUS.MINOR_SYSTEM_ID AND ";
          sql=sql+" SEC_MST_ROLE_MENUS.SUB_SYSTEM_ID=SEC_MST_INTRANET_MENUS.SUB_SYSTEM_ID AND  ";
          sql=sql+" SEC_MST_ROLE_MENUS.MAJOR_SYSTEM_ID=? AND SEC_MST_ROLE_MENUS.MINOR_SYSTEM_ID=? AND SEC_MST_ROLE_MENUS.SUB_SYSTEM_ID=? AND SEC_MST_ROLE_MENUS.ROLE_ID=?";*/
                System.out.println("Inside sql");
                try {
                    ps = connection.prepareStatement(sql);
                    System.out.println("Outside sql");
                    ps.setString(1, strRId);
                    ps.setString(2, strMajor);
                    ps.setString(3, strMinor);
                    ps.setString(4, strSub);

                    results = ps.executeQuery();
                    int i = 0;
                    // int i=ps.executeUpdate();
                    /*if(!results.next()) {
       xml=xml+"<flag>NoRecord</flag>";
       }
       else
       {*/

                    while (results.next()) {
                        i++;
                        //xml=xml+"<value>";
                        xml =
 xml + "<Idcode>" + strRId + "</Idcode><Iddesc></Iddesc>";
                        xml =
 xml + "<Majorcode>" + results.getString("Major_System_Id") +
   "</Majorcode><Majordesc></Majordesc>";
                        xml =
 xml + "<Minorcode>" + results.getString("Minor_System_Id") +
   "</Minorcode><Minordesc></Minordesc>";
                        xml =
 xml + "<Subcode>" + results.getString("Sub_System_Id") +
   "</Subcode><Subdesc></Subdesc>";
                        xml =
 xml + "<Menucode>" + results.getString("Menu_Id") + "</Menucode><Menudesc>" +
   results.getString("Menu_Desc") + "</Menudesc>";
                        //xml=xml+"</value>";

                    }
                    if (i == 0) {
                        xml = sxml + "<flag>NoRecord</flag>";
                    } else {
                        xml = sxml + "<flag>success</flag>" + xml;
                    }
                    //}
                } catch (Exception ae) {
                    System.out.println("Exception isssssssssss:" + ae);
                }

            }

            catch (Exception e) {
                System.out.println("exce ****2 vv" + e);
                // cannot insert values
                xml = sxml + "<flag>failure</flag>";

            }

            xml = xml + "</response>";

        }


        //this is for the assign menu for the particular role.........
        else if (strCommand.equals("Load1")) {
            String sxml = "<response><command>Load1</command>";
            String strRId = request.getParameter("RId");
            String strMajor = request.getParameter("Major");
            String strMinor = request.getParameter("Minor");
            String strSub = request.getParameter("Sub");
//            System.out.println("strRId" + strRId);
//            System.out.println("strMajor" + strMajor);
//            System.out.println("strMnior" + strMinor);
//            System.out.println("strSubs" + strSub);
            try {

                String sql =
                    "SELECT A.ROLE_NAME,c.major_system_id,C.MAJOR_SYSTEM_DESC,d.minor_system_id,D.MINOR_SYSTEM_DESC,e.sub_system_id,E.SUB_SYSTEM_DESC,f.menu_id,F.MENU_DESC FROM SEC_MST_ROLES A " +
                    " INNER JOIN  SEC_MST_ROLE_MENUS B ON A.ROLE_ID=B.ROLE_ID" +
                    " INNER JOIN SEC_MST_MAJOR_SYSTEMS C ON B.MAJOR_SYSTEM_ID=C.MAJOR_SYSTEM_ID" +
                    " INNER JOIN SEC_MST_MINOR_SYSTEMS D ON B.MINOR_SYSTEM_ID=D.MINOR_SYSTEM_ID" +
                    " INNER JOIN SEC_MST_SUBSYSTEMS E ON B.SUB_SYSTEM_ID=E.SUB_SYSTEM_ID " +
                    " INNER JOIN SEC_MST_INTRANET_MENUS F ON B.MENU_ID=F.MENU_ID" +
                    " AND C.MAJOR_SYSTEM_ID=E.MAJOR_SYSTEM_ID" +
                    " AND D.MINOR_SYSTEM_ID=E.MINOR_SYSTEM_ID" +
                    " AND C.MAJOR_SYSTEM_ID=F.MAJOR_SYSTEM_ID" +
                    " AND D.MINOR_SYSTEM_ID=F.MINOR_SYSTEM_ID" +
                    " AND E.SUB_SYSTEM_ID=F.SUB_SYSTEM_ID" +
                    " AND B.ROLE_ID=? order by B.MENU_ID";

                try {
                    ps = connection.prepareStatement(sql);
                    ps.setString(1, strRId);
                    results = ps.executeQuery();
                    int i = 0;

                    while (results.next()) {
                        i++;

                        xml =
 xml + "<Idcode>" + strRId + "</Idcode><Iddesc>" + results.getString("Role_Name").trim() +
   "</Iddesc>";
                        xml =
 xml + "<Majorcode>" + results.getString("Major_System_Id") +
   "</Majorcode><Majordesc>" + results.getString("Major_System_Desc").trim() +
   "</Majordesc>";
                        xml =
 xml + "<Minorcode>" + results.getString("Minor_System_Id") +
   "</Minorcode><Minordesc>" + results.getString("Minor_System_Desc").trim() +
   "</Minordesc>";
                        xml =
 xml + "<Subcode>" + results.getString("Sub_System_Id") +
   "</Subcode><Subdesc>" + results.getString("Sub_System_Desc").trim() +
   "</Subdesc>";
                        xml =
 xml + "<Menucode>" + results.getString("Menu_Id") + "</Menucode><Menudesc>" +
   results.getString("Menu_Desc").trim() + "</Menudesc>";

                    }
                    if (i == 0) {
                        xml = sxml + "<flag>NoRecord</flag>";
                    } else {
                        xml = sxml + "<flag>success</flag>" + xml;
                    }
                    //}
                } catch (Exception ae) {
                    System.out.println("Exception isssssssssss:" + ae);
                }

            }

            catch (Exception e) {
                System.out.println("exce ****2 vv" + e);

                xml = sxml + "<flag>failure</flag>";

            }

            xml = xml + "</response>";

        }

        //this is to filter till major system id
        else if (strCommand.equals("Load2")) {
            String sxml = "<response><command>Load2</command>";
            String strRId = request.getParameter("RId");
            String strMajor = request.getParameter("Major");

            try {

                String sql =
                    "SELECT A.ROLE_NAME,c.major_system_id,C.MAJOR_SYSTEM_DESC,d.minor_system_id,D.MINOR_SYSTEM_DESC,e.sub_system_id,E.SUB_SYSTEM_DESC,f.menu_id,F.MENU_DESC FROM SEC_MST_ROLES A " +
                    " INNER JOIN  SEC_MST_ROLE_MENUS B ON A.ROLE_ID=B.ROLE_ID" +
                    " INNER JOIN SEC_MST_MAJOR_SYSTEMS C ON B.MAJOR_SYSTEM_ID=C.MAJOR_SYSTEM_ID" +
                    " INNER JOIN SEC_MST_MINOR_SYSTEMS D ON B.MINOR_SYSTEM_ID=D.MINOR_SYSTEM_ID" +
                    " INNER JOIN SEC_MST_SUBSYSTEMS E ON B.SUB_SYSTEM_ID=E.SUB_SYSTEM_ID " +
                    " INNER JOIN SEC_MST_INTRANET_MENUS F ON B.MENU_ID=F.MENU_ID" +
                    " AND C.MAJOR_SYSTEM_ID=E.MAJOR_SYSTEM_ID" +
                    " AND D.MINOR_SYSTEM_ID=E.MINOR_SYSTEM_ID" +
                    " AND C.MAJOR_SYSTEM_ID=F.MAJOR_SYSTEM_ID" +
                    " AND D.MINOR_SYSTEM_ID=F.MINOR_SYSTEM_ID" +
                    " AND E.SUB_SYSTEM_ID=F.SUB_SYSTEM_ID" +
                    " AND B.ROLE_ID=? AND B.MAJOR_SYSTEM_ID=? order by B.MENU_ID";

                try {
                    ps = connection.prepareStatement(sql);
                    ps.setString(1, strRId);
                    ps.setString(2, strMajor);
                    results = ps.executeQuery();
                    int i = 0;

                    while (results.next()) {
                        i++;

                        xml =
 xml + "<Idcode>" + strRId + "</Idcode><Iddesc>" + results.getString("Role_Name").trim() +
   "</Iddesc>";
                        xml =
 xml + "<Majorcode>" + results.getString("Major_System_Id") +
   "</Majorcode><Majordesc>" + results.getString("Major_System_Desc").trim() +
   "</Majordesc>";
                        xml =
 xml + "<Minorcode>" + results.getString("Minor_System_Id") +
   "</Minorcode><Minordesc>" + results.getString("Minor_System_Desc").trim() +
   "</Minordesc>";
                        xml =
 xml + "<Subcode>" + results.getString("Sub_System_Id") +
   "</Subcode><Subdesc>" + results.getString("Sub_System_Desc").trim() +
   "</Subdesc>";
                        xml =
 xml + "<Menucode>" + results.getString("Menu_Id") + "</Menucode><Menudesc>" +
   results.getString("Menu_Desc").trim() + "</Menudesc>";

                    }
                    if (i == 0) {
                        xml = sxml + "<flag>NoRecord</flag>";
                    } else {
                        xml = sxml + "<flag>success</flag>" + xml;
                    }
                    //}
                } catch (Exception ae) {
                    System.out.println("Exception isssssssssss:" + ae);
                }

            }

            catch (Exception e) {
                System.out.println("exce ****2 vv" + e);

                xml = sxml + "<flag>failure</flag>";

            }

            xml = xml + "</response>";

        }

        //this is to filter till minor system id

        else if (strCommand.equals("Load3")) {
            String sxml = "<response><command>Load3</command>";
            String strRId = request.getParameter("RId");
            String strMajor = request.getParameter("Major");
            String strMinor = request.getParameter("Minor");

            try {

                String sql =
                    "SELECT A.ROLE_NAME,c.major_system_id,C.MAJOR_SYSTEM_DESC,d.minor_system_id,D.MINOR_SYSTEM_DESC,e.sub_system_id,E.SUB_SYSTEM_DESC,f.menu_id,F.MENU_DESC FROM SEC_MST_ROLES A " +
                    " INNER JOIN  SEC_MST_ROLE_MENUS B ON A.ROLE_ID=B.ROLE_ID" +
                    " INNER JOIN SEC_MST_MAJOR_SYSTEMS C ON B.MAJOR_SYSTEM_ID=C.MAJOR_SYSTEM_ID" +
                    " INNER JOIN SEC_MST_MINOR_SYSTEMS D ON B.MINOR_SYSTEM_ID=D.MINOR_SYSTEM_ID" +
                    " INNER JOIN SEC_MST_SUBSYSTEMS E ON B.SUB_SYSTEM_ID=E.SUB_SYSTEM_ID " +
                    " INNER JOIN SEC_MST_INTRANET_MENUS F ON B.MENU_ID=F.MENU_ID" +
                    " AND C.MAJOR_SYSTEM_ID=E.MAJOR_SYSTEM_ID" +
                    " AND D.MINOR_SYSTEM_ID=E.MINOR_SYSTEM_ID" +
                    " AND C.MAJOR_SYSTEM_ID=F.MAJOR_SYSTEM_ID" +
                    " AND D.MINOR_SYSTEM_ID=F.MINOR_SYSTEM_ID" +
                    " AND E.SUB_SYSTEM_ID=F.SUB_SYSTEM_ID" +
                    " AND B.ROLE_ID=? AND B.MAJOR_SYSTEM_ID=? AND B.MINOR_SYSTEM_ID=?  order by B.MENU_ID";

                try {
                    ps = connection.prepareStatement(sql);
                    ps.setString(1, strRId);
                    ps.setString(2, strMajor);
                    ps.setString(3, strMinor);

                    results = ps.executeQuery();
                    int i = 0;

                    while (results.next()) {
                        i++;

                        xml =
 xml + "<Idcode>" + strRId + "</Idcode><Iddesc>" + results.getString("Role_Name").trim() +
   "</Iddesc>";
                        xml =
 xml + "<Majorcode>" + results.getString("Major_System_Id") +
   "</Majorcode><Majordesc>" + results.getString("Major_System_Desc").trim() +
   "</Majordesc>";
                        xml =
 xml + "<Minorcode>" + results.getString("Minor_System_Id") +
   "</Minorcode><Minordesc>" + results.getString("Minor_System_Desc").trim() +
   "</Minordesc>";
                        xml =
 xml + "<Subcode>" + results.getString("Sub_System_Id") +
   "</Subcode><Subdesc>" + results.getString("Sub_System_Desc").trim() +
   "</Subdesc>";
                        xml =
 xml + "<Menucode>" + results.getString("Menu_Id") + "</Menucode><Menudesc>" +
   results.getString("Menu_Desc").trim() + "</Menudesc>";

                    }
                    if (i == 0) {
                        xml = sxml + "<flag>NoRecord</flag>";
                    } else {
                        xml = sxml + "<flag>success</flag>" + xml;
                    }
                    //}
                } catch (Exception ae) {
                    System.out.println("Exception isssssssssss:" + ae);
                }

            }

            catch (Exception e) {
                System.out.println("exce ****2 vv" + e);

                xml = sxml + "<flag>failure</flag>";

            }

            xml = xml + "</response>";

        }

        //this is to filter till sub system id
        else if (strCommand.equals("Load4")) {
            String sxml = "<response><command>Load4</command>";
            String strRId = request.getParameter("RId");
            String strMajor = request.getParameter("Major");
            String strMinor = request.getParameter("Minor");
            String strSub = request.getParameter("Sub");
            System.out.println("strRId" + strRId);
            System.out.println("strMajor" + strMajor);
            System.out.println("strMnior" + strMinor);
            System.out.println("strSubs" + strSub);
            try {

                String sql =
                    "SELECT A.ROLE_NAME,c.major_system_id,C.MAJOR_SYSTEM_DESC,d.minor_system_id,D.MINOR_SYSTEM_DESC,e.sub_system_id,E.SUB_SYSTEM_DESC,f.menu_id,F.MENU_DESC FROM SEC_MST_ROLES A " +
                    " INNER JOIN  SEC_MST_ROLE_MENUS B ON A.ROLE_ID=B.ROLE_ID" +
                    " INNER JOIN SEC_MST_MAJOR_SYSTEMS C ON B.MAJOR_SYSTEM_ID=C.MAJOR_SYSTEM_ID" +
                    " INNER JOIN SEC_MST_MINOR_SYSTEMS D ON B.MINOR_SYSTEM_ID=D.MINOR_SYSTEM_ID" +
                    " INNER JOIN SEC_MST_SUBSYSTEMS E ON B.SUB_SYSTEM_ID=E.SUB_SYSTEM_ID " +
                    " INNER JOIN SEC_MST_INTRANET_MENUS F ON B.MENU_ID=F.MENU_ID" +
                    " AND C.MAJOR_SYSTEM_ID=E.MAJOR_SYSTEM_ID" +
                    " AND D.MINOR_SYSTEM_ID=E.MINOR_SYSTEM_ID" +
                    " AND C.MAJOR_SYSTEM_ID=F.MAJOR_SYSTEM_ID" +
                    " AND D.MINOR_SYSTEM_ID=F.MINOR_SYSTEM_ID" +
                    " AND E.SUB_SYSTEM_ID=F.SUB_SYSTEM_ID" +
                    " AND B.ROLE_ID=? and B.MAJOR_SYSTEM_ID=? AND B.MINOR_SYSTEM_ID=? AND B.SUB_SYSTEM_ID=? order by B.MENU_ID";

                try {
                    ps = connection.prepareStatement(sql);
                    ps.setString(1, strRId);
                    ps.setString(2, strMajor);
                    ps.setString(3, strMinor);
                    ps.setString(4, strSub);
                    results = ps.executeQuery();
                    int i = 0;

                    while (results.next()) {
                        i++;

                        xml =
 xml + "<Idcode>" + strRId + "</Idcode><Iddesc>" + results.getString("Role_Name").trim() +
   "</Iddesc>";
                        xml =
 xml + "<Majorcode>" + results.getString("Major_System_Id") +
   "</Majorcode><Majordesc>" + results.getString("Major_System_Desc").trim() +
   "</Majordesc>";
                        xml =
 xml + "<Minorcode>" + results.getString("Minor_System_Id") +
   "</Minorcode><Minordesc>" + results.getString("Minor_System_Desc").trim() +
   "</Minordesc>";
                        xml =
 xml + "<Subcode>" + results.getString("Sub_System_Id") +
   "</Subcode><Subdesc>" + results.getString("Sub_System_Desc").trim() +
   "</Subdesc>";
                        xml =
 xml + "<Menucode>" + results.getString("Menu_Id") + "</Menucode><Menudesc>" +
   results.getString("Menu_Desc").trim() + "</Menudesc>";

                    }
                    if (i == 0) {
                        xml = sxml + "<flag>NoRecord</flag>";
                    } else {
                        xml = sxml + "<flag>success</flag>" + xml;
                    }
                    //}
                } catch (Exception ae) {
                    System.out.println("Exception isssssssssss:" + ae);
                }

            }

            catch (Exception e) {
                System.out.println("exce ****2 vv" + e);

                xml = sxml + "<flag>failure</flag>";

            }

            xml = xml + "</response>";

        }
        System.out.println("xml is : " + xml);
        pw.println(xml);
//        pw.write(xml);
        pw.flush();
        pw.close();

    } //doget

}// class