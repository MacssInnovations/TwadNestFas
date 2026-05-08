package Servlets.HR.HR1.OfficeMaster.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.sql.Statement;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class ServletCadreTest extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";
    private Connection connection = null;
    private PreparedStatement ps = null;
    private ResultSet results = null;
    private ResultSet results1 = null;
    private PreparedStatement ps1 = null;
    private Statement statement = null;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        ResultSet results = null;
        /*try
        {
            HttpSession session=request.getSession(false);
            if(session==null)
            {
                System.out.println(request.getContextPath()+"/index.jsp");
                response.sendRedirect(request.getContextPath()+"/index.jsp");

            }
            System.out.println(session);

        }catch(Exception e)
        {
        System.out.println("Redirect Error :"+e);
        }*/

        try {
            System.out.println("called Servlet");
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
            String xml = "";

            String officelevel = request.getParameter("level");
            int levelhierachy = 0;
            int levelid = 0;
            String levelid1 = null;
            String levelname = null;
            try {

                String sql =
                    "select HIERARCHICAL_SEQUENCE from COM_MST_OFFICE_LEVELS where OFFICE_LEVEL_ID=?";
                ps = connection.prepareStatement(sql);
                ps.setString(1, officelevel);
                results = ps.executeQuery();
                if (results.next()) {
                    levelhierachy = results.getInt("HIERARCHICAL_SEQUENCE");

                }
                levelid = levelhierachy / 10;

            } catch (Exception e) {
                System.out.println("Exception in Query:" + e);
            }

            try {

                String sql1 =
                    "select OFFICE_LEVEL_ID,OFFICE_LEVEL_NAME from com_mst_office_levels where HIERARCHICAL_SEQUENCE<=? and HIERARCHICAL_SEQUENCE not like ?";
                //results1=statement.executeQuery("select OFFICE_LEVEL_ID,OFFICE_LEVEL_NAME from com_mst_office_levels where HIERARCHICAL_SEQUENCE<"+levelid+" and HIERARCHICAL_SEQUENCE not like '"+levelhierachy+"%'");
                ps1 = connection.prepareStatement(sql1);
                System.out.println("level id is:" + levelid);
                System.out.println("levelhierarchy is:" + levelhierachy);
                ps1.setInt(2, levelid);
                ps1.setInt(1, levelhierachy);
                results1 = ps1.executeQuery();
                System.out.println("results is:" + results1);
                xml = "<response><flag>success</flag>";
                while (results1.next()) {
                    System.out.println("inside while");
                    levelid1 = results1.getString("office_level_id");
                    levelname = results1.getString("office_level_name");
                    System.out.println("level id is:" + levelid1);
                    System.out.println("level name is:" + levelname);
                    try {
                        String sql2 =
                            "select a.cadre_id,a.cadre_name from hrm_mst_cadre a,com_mst_office_levels b where a.cadre_id=b.office_head_cadre_id and b.office_level_id=?";
                        PreparedStatement ps2 =
                            connection.prepareStatement(sql2);
                        ps2.setString(1, levelid1);
                        ResultSet results3 = ps2.executeQuery();
                        System.out.println("results3 is:" + results3);
                        while (results3.next()) {
                            response.setContentType(CONTENT_TYPE);
                            response.setContentType("text/xml");
                            //response.setHeader("cache-control","no-cache");
                            out = response.getWriter();
                            xml =
 xml + "<options><cadreid>" + results3.getInt("cadre_id") +
   "</cadreid><cadrename>" + results3.getString("cadre_name").trim() +
   "</cadrename></options>";
                            System.out.println("cadre id is:" +
                                               results3.getInt("cadre_id"));
                            System.out.println("cadre name is:" +
                                               results3.getString("cadre_name"));


                        }
                        int cadreid = 0;
                        String cadrename = null;
                        String sql4 =
                            "select a.cadre_id,a.cadre_name from hrm_mst_cadre a,com_mst_office_levels b where a.cadre_id=b.office_head_cadre_id and b.office_level_id=?";
                        PreparedStatement ps5 =
                            connection.prepareStatement(sql4);
                        ps5.setString(1, officelevel);
                        ResultSet res = ps5.executeQuery();
                        if (res.next()) {
                            cadreid = res.getInt("cadre_id");
                            cadrename = res.getString("cadre_name");
                        }
                        xml =
 xml + "<id>" + cadreid + "</id><name>" + cadrename + "</name>";

                    } catch (Exception e) {
                        System.out.println("Exception in inside cadre:" + e);
                    }
                }
                xml = xml + "</response>";
                System.out.println("xml is:" + xml);
                out.write(xml);
            } catch (Exception e) {
                System.out.println("Exception in Office Level:" + e);
            }

            //out.write(xml);

        } catch (Exception e) {
            System.out.println("Exception in Connection:" + e);
        }

        // out.close();
    }
}
