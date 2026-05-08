package Servlets.HR.HR1.Masters.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.*;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

import Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.Loader;

public class MajorServlet1 extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";
    private Connection connection = null;
    private Statement statement = null;
    private ResultSet results = null;
    private PreparedStatement ps = null;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        System.out.println("within init config");

        try {
        	LoadDriver driver=new LoadDriver();
        	connection=driver.getConnection();
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
        response.setContentType("text/xml");
        PrintWriter pw = response.getWriter();
        response.setHeader("Cache-Control", "no-cache");
        HttpSession session = request.getSession(false);
        try {
          //  HttpSession session = request.getSession(false);
            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");

            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }
        
    	String userid = (String) session.getAttribute("UserId");
		System.out.println("session id is:" + userid);
		String updatedby = (String) session.getAttribute("UserId");
		String Remote_host="";
		Remote_host=request.getRemoteHost();
		System.out.println("Remost host:"+Remote_host);
		java.sql.Timestamp ts = null;
		long l = System.currentTimeMillis();
		ts = new Timestamp(l);
        try {
            strCommand = request.getParameter("command");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (strCommand.equalsIgnoreCase("Load")) {

            String sxml = "<response><command>Load</command>";
            String strmajid = "";
            String strmajdesc = "";
            String strmajsdesc = "";


            try {
                strmajid = request.getParameter("MajorId");
            } catch (Exception e) {
                System.out.println("before Load try **** " + e);
            }
            String sql =
                "select  MAJOR_SYSTEM_ID,MAJOR_SYSTEM_DESC,MAJOR_SYSTEM_SHORT_DESC from SEC_MST_MAJOR_SYSTEMS where MAJOR_SYSTEM_ID=?";

            try {

                ps = connection.prepareStatement(sql);
                ps.setString(1, strmajid);
                int i = 0;
                results = ps.executeQuery();
                while (results.next()) {
                    i++;
                    xml =
 xml + "<majid>" + results.getString("MAJOR_SYSTEM_ID").replace("&", "and") +
   "</majid><majordesc>" +
   results.getString("MAJOR_SYSTEM_DESC").replace("&", "and") +
   "</majordesc><majsdesc>" +
   results.getString("MAJOR_SYSTEM_SHORT_DESC").replace("&", "and") +
   "</majsdesc>";
                }
                if (i == 0) {
                    xml = sxml + "<flag>NoRecord</flag>";
                } else {
                    xml = sxml + "<flag>success</flag>" + xml;
                }
            } catch (Exception e) {
                System.out.println("exception in the check" + e);
                xml = sxml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }

        else if (strCommand.equalsIgnoreCase("Add")) {

            xml = "<response><command>Add</command>";
            String strmajid = "";
            String strmajdesc = "";
            String strmajsdesc = "";
            try {
                strmajid = request.getParameter("MajorId");
                strmajid = strmajid.toUpperCase();
                System.out.println(strmajid);

                strmajdesc = request.getParameter("MajorDesc");
                strmajsdesc = request.getParameter("MajorSDesc");

            } catch (Exception e) {
                System.out.println("before insert try **** " + e);
            }
            String sql =
                "insert into SEC_MST_MAJOR_SYSTEMS(MAJOR_SYSTEM_ID,MAJOR_SYSTEM_DESC,MAJOR_SYSTEM_SHORT_DESC,UPDATED_BY_USER_ID,UPDATED_DATE,IP_ADDRESS) values(?,?,?,?,?,?)";
            System.out.println("sql is " + sql);
            try {
                System.out.println("before sql");
                ps = connection.prepareStatement(sql);
                System.out.println("before sql1");
                ps.setString(1, strmajid);
                System.out.println("before sql2");
                ps.setString(2, strmajdesc);
                System.out.println("before sql3");
                ps.setString(3, strmajsdesc);
                System.out.println("before sql4");
                ps.setString(4,updatedby);
                ps.setTimestamp(5, ts);
                ps.setString(6,Remote_host);
                ps.executeUpdate();
                System.out.println("before sql5");
                xml = xml + "<flag>success</flag>";
                //xml=xml+"<flag>success</flag><majid>" +results.getString("MAJOR_SYSTEM_ID") + "</majid><majordesc>" + results.getString("MAJOR_SYSTEM_DESC")  +"</majordesc><majsdesc>" + results.getString("MAJOR_SYSTEM_SHORT_DESC")  + "</majsdesc>";
                System.out.println("after sql");
                ps.close();
            }

            catch (Exception e) {
                System.out.println("exception in the add" + e);
                xml = xml + "<flag>failure</flag>";
            }

            xml = xml + "</response>";
        } else if (strCommand.equalsIgnoreCase("Delete")) {

            xml = "<response><command>Delete</command>";
            String strmajid = "";
            String strmajdesc = "";
            String strmajsdesc = "";
            try {
                strmajid = request.getParameter("MajorId");
                strmajdesc = request.getParameter("MajorDesc");
                strmajsdesc = request.getParameter("MajorSDesc");
            } catch (Exception e) {
                System.out.println("before delete try **** " + e);
            }
            String sql =
                "delete from SEC_MST_MAJOR_SYSTEMS where MAJOR_SYSTEM_ID=?"; // AND MAJOR_SYSTEM_DESC=? AND MAJOR_SYSTEM_SHORT_DESC=?";

            try {

                ps = connection.prepareStatement(sql);
                ps.setString(1, strmajid);
                //ps.setString(2,strmajdesc);
                //ps.setString(3,strmajsdesc);
                ps.executeUpdate();
                xml =
 xml + "<flag>success</flag>"; //<majid>" +strmajid + "</majid><majordesc>" + strmajdesc  +"</majordesc><majsdesc>" + strmajsdesc  + "</majsdesc>";
                ps.close();
            } catch (Exception e) {
                System.out.println("exception in the delete" + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }

        else if (strCommand.equalsIgnoreCase("Update")) {

            xml = "<response><command>Update</command>";
            String strmajid = "";
            String strmajdesc = "";
            String strmajsdesc = "";


            try {
                strmajid = request.getParameter("MajorId");
                strmajdesc = request.getParameter("MajorDesc");
                strmajsdesc = request.getParameter("MajorSDesc");

            } catch (Exception e) {
                System.out.println("before update try **** " + e);
            }
            String sql =
                "update SEC_MST_MAJOR_SYSTEMS set MAJOR_SYSTEM_DESC=?,MAJOR_SYSTEM_SHORT_DESC=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=?,IP_ADDRESS=? where MAJOR_SYSTEM_ID=?";

            try {

                ps = connection.prepareStatement(sql);
                ps.setString(6, strmajid);
                ps.setString(1, strmajdesc);
                ps.setString(2, strmajsdesc);
                ps.setString(3,updatedby);
               ps.setTimestamp(4,ts);
               ps.setString(5,Remote_host);
                ps.executeUpdate();
                xml =
 xml + "<flag>success</flag>"; //<majid>" +strmajid + "</majid><majordesc>" + strmajdesc  +"</majordesc><majsdesc>" + strmajsdesc  + "</majsdesc>";
                ps.close();
            } catch (Exception e) {
                System.out.println("exception in the update" + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        } else if (strCommand.equalsIgnoreCase("check")) {

            String sxml = "<response><command>check</command>";
            String strmajid = "";
            String strmajdesc = "";
            String strmajsdesc = "";


            try {
                strmajid = request.getParameter("MajorId");
                strmajdesc = request.getParameter("MajorDesc");
                strmajsdesc = request.getParameter("MajorSDesc");
            } catch (Exception e) {
                System.out.println("before Load try **** " + e);
            }
            String sql =
                "select  MAJOR_SYSTEM_ID,MAJOR_SYSTEM_DESC,MAJOR_SYSTEM_SHORT_DESC from SEC_MST_MAJOR_SYSTEMS where MAJOR_SYSTEM_ID=? AND MAJOR_SYSTEM_DESC=? AND MAJOR_SYSTEM_SHORT_DESC=?";

            try {

                ps = connection.prepareStatement(sql);
                ps.setString(1, strmajid);
                ps.setString(2, strmajdesc);
                ps.setString(3, strmajsdesc);
                int i = 0;
                results = ps.executeQuery();
                while (results.next()) {
                    i++;
                    xml =
 xml + "<majid>" + results.getString("MAJOR_SYSTEM_ID") +
   "</majid><majordesc>" + results.getString("MAJOR_SYSTEM_DESC") +
   "</majordesc><majsdesc>" + results.getString("MAJOR_SYSTEM_SHORT_DESC") +
   "</majsdesc>";
                }
                if (i == 0) {
                    xml = sxml + "<flag>NoRecord</flag>";
                } else {
                    xml = sxml + "<flag>success</flag>" + xml;
                }
            } catch (Exception e) {
                System.out.println("exception in the check" + e);
                xml = sxml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }

        System.out.println("xml is : " + xml);
        pw.write(xml);
        pw.flush();
        pw.close();
    }
}
