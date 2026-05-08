package Servlets.HR.HR1.EmployeeMaster.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.Timestamp;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class UserCategoryServ extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";
    private Connection con = null;
    private ResultSet rs = null;
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

            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection


            Class.forName(strDriver.trim());
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
        } catch (Exception e) {
            System.out.println("Exception in connection..." + e);
        }
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);

        int strcode = 0;
        String strname = "";
        String xml = "";
        String strCommand = "";

        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();

        try {

            strCommand = request.getParameter("Command");
            System.out.println("assign....." + strCommand);

            strname = request.getParameter("txtusercategory");
            System.out.println("txtusercategory...." + strname);
            strcode =
                    Integer.parseInt(request.getParameter("txtusercategoryid"));
            System.out.println("txtusercategoryid....." + strcode);
        }

        catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }
        if (strCommand.equalsIgnoreCase("Add")) {
            xml = "<response><command>Add</command>";
            //String sql="insert into TEST_STATE values(?,?)";
            try {
                System.out.println("adddddddd::" + strcode + " " + strname);

                String sql =
                    "select max(USER_CATEGORY_ID) as maxval from  SEC_MST_USER_CATEGORY";
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();
                int sid = 0;
                if (rs.next()) {
                    sid = rs.getInt("maxval");

                    if (sid > 0)
                        sid = sid + 1;
                    else
                        sid = 1;
                }

                System.out.println("sid:" + sid);
                HttpSession session = request.getSession(false);
                String updatedby = (String)session.getAttribute("UserId");
                long lng = System.currentTimeMillis();
                Timestamp ts = new Timestamp(lng);

                sql =
 "insert into  SEC_MST_USER_CATEGORY(USER_CATEGORY_ID,USER_CATEGORY_DESC,UPDATED_BY_USER_ID,UPDATED_DATE) values(?,?,?,?)";
                ps = con.prepareStatement(sql);
                ps.setInt(1, sid);
                ps.setString(2, strname);
                ps.setString(3, updatedby);
                ps.setTimestamp(4, ts);
                ps.executeUpdate();

                xml = xml + "<flag>success</flag><genid>" + sid + "</genid>";
                System.out.println("test ok");
            } catch (Exception e) {
                System.out.println("catch........" + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }


        else if (strCommand.equalsIgnoreCase("novali")) {
            String sxml = "<response><command>novali</command>";
            try {
                ps =
  con.prepareStatement("select USER_CATEGORY_ID from SEC_MST_USER_CATEGORY where USER_CATEGORY_ID=?");
                ps.setInt(1, strcode);
                rs = ps.executeQuery();
                int i = 0;
                while (rs.next()) {
                    i++;
                }
                if (i == 0) {
                    xml = sxml + "<flag>success</flag>";
                } else {
                    xml = sxml + "<flag>failure</flag>";
                }
            } catch (Exception e) {
                System.out.println("catch...." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }


        else if (strCommand.equalsIgnoreCase("novalitype")) {
            String sxml = "<response><command>novalitype</command>";
            try {
                ps =
  con.prepareStatement("select USER_CATEGORY_DESC from SEC_MST_USER_CATEGORY where USER_CATEGORY_DESC=?");
                ps.setString(1, strname);
                rs = ps.executeQuery();
                int i = 0;
                while (rs.next()) {
                    i++;
                }
                if (i == 0) {
                    xml = sxml + "<flag>success</flag>";
                } else {
                    xml = sxml + "<flag>failure</flag>";
                }
            } catch (Exception e) {
                System.out.println("catch...." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }


        else if (strCommand.equalsIgnoreCase("Delete")) {
            xml = "<response><command>Delete</command>";
            try {
                ps =
  con.prepareStatement("delete from SEC_MST_USER_CATEGORY where USER_CATEGORY_ID=?");
                ps.setInt(1, strcode);
                ps.executeUpdate();
                xml = xml + "<flag>success</flag><scd>" + strcode + "</scd>";
            } catch (Exception e) {
                System.out.println("catch...." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }

        else if (strCommand.equalsIgnoreCase("Update")) {
            xml = "<response><command>Update</command>";

            try {
                System.out.println("update::" + strcode);
                HttpSession session = request.getSession(false);
                String updatedby = (String)session.getAttribute("UserId");
                long lng = System.currentTimeMillis();
                Timestamp ts = new Timestamp(lng);
                ps =
  con.prepareStatement("update SEC_MST_USER_CATEGORY set USER_CATEGORY_DESC=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=? where USER_CATEGORY_ID=?");
                ps.setString(1, strname);

                ps.setString(2, updatedby);
                ps.setTimestamp(3, ts);

                ps.setInt(4, strcode);
                ps.executeUpdate();
                xml = xml + "<flag>success</flag>";
            } catch (Exception e) {
                System.out.println("catch...." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }

        System.out.println("xml is:" + xml);
        out.write(xml);
        out.flush();
        out.close();
    }
}
