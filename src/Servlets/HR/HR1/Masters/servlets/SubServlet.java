package Servlets.HR.HR1.Masters.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver;

public class SubServlet extends HttpServlet {
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

    	 HttpSession session = request.getSession(false);
        try {
           
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
		
        System.out.println("servlet called");
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

        if (strCommand.equals("major")) {
            try {
                String strmajor = request.getParameter("First");
                String sql =
                    "select MINOR_SYSTEM_ID,MINOR_SYSTEM_DESC from SEC_MST_MINOR_SYSTEMS where MAJOR_SYSTEM_ID=?";
                ps = connection.prepareStatement(sql);
                ps.setString(1, strmajor);
                results = ps.executeQuery();

                while (results.next()) {
                    String temp = results.getString("MINOR_SYSTEM_ID");

                    xml =
 xml + "<option><desc>" + results.getString("MINOR_SYSTEM_DESC") +
   "</desc><id>" + temp + "</id></option>";

                }
                xml = "<select>" + xml + "</select>";
            }

            catch (Exception e) {
                System.out.println(e);
            }
        }

        else if (strCommand.equals("Load")) {
            String sxml = "<response><command>Load</command>";
            String strMajor = request.getParameter("Major");
            String strMinor = request.getParameter("Minor");

            try {

                String sql =
                    "SELECT SUB_SYSTEM_ID,SUB_SYSTEM_DESC,SUB_SYSTEM_SHORT_DESC,LIST_SEQ_NO FROM SEC_MST_SUBSYSTEMS WHERE MAJOR_SYSTEM_ID=? AND MINOR_SYSTEM_ID=? order by SUB_SYSTEM_ID";
                try {
                    ps = connection.prepareStatement(sql);
                    ps.setString(1, strMajor);
                    ps.setString(2, strMinor);


                    results = ps.executeQuery();
                    int i = 0;

                    while (results.next()) {
                        i++;
                        xml =
 xml + "<Subcode>" + results.getString("SUB_SYSTEM_ID") +
   "</Subcode><Subdesc>" + results.getString("SUB_SYSTEM_DESC") + "</Subdesc>";
                        xml =
 xml + "<SubSDesc>" + results.getString("SUB_SYSTEM_SHORT_DESC").replace("&",
                                                                         "and") +
   "</SubSDesc>";
                        xml =xml+ "<listseq>" + results.getInt("LIST_SEQ_NO")+"</listseq>";

                    }
                    if (i == 0) {
                        xml = sxml + "<flag>NoRecord</flag>";
                    } else {
                        xml = sxml + "<flag>success</flag>" + xml;
                    }
                } catch (Exception ae) {
                    System.out.println("Exception isssssssssss select query:" +
                                       ae);
                }
                results.close();
                ps.close();

            }


            catch (Exception e) {
                System.out.println("exce ****2 vv in the load" + e);
                xml = sxml + "<flag>failure</flag>";

            }

            xml = xml + "</response>";

        }

        else if (strCommand.equalsIgnoreCase("Delete")) {

            xml = "<response><command>Delete</command>";

            String strMajor = null;
            String strMinor = null;
            String strSubid = null;
            String strSubdesc = null;
            String strSubsdesc = null;

            try {
                strMajor = request.getParameter("Major");
                strMinor = request.getParameter("Minor");
                strSubid = request.getParameter("Subid");
                strSubdesc = request.getParameter("SubDesc");
                strSubsdesc = request.getParameter("SubSDesc");


            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                String sql =
                    "delete from SEC_MST_SUBSYSTEMS where MAJOR_SYSTEM_ID=? And MINOR_SYSTEM_ID=? And SUB_SYSTEM_ID=? AND SUB_SYSTEM_DESC=? AND SUB_SYSTEM_SHORT_DESC=?";
                ps = connection.prepareStatement(sql);
                ps.setString(1, strMajor);
                ps.setString(2, strMinor);
                ps.setString(3, strSubid);
                ps.setString(4, strSubdesc);
                ps.setString(5, strSubsdesc);


                int i = ps.executeUpdate();

                xml =
 xml + "<flag>success</flag>><Subid>" + strSubid + "</Subid>";


            } catch (Exception e) {
                System.out.println("delete exception is " + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";

        }

        else if (strCommand.equals("Add")) {
            String strMajor = null;
            String strMinor = null;
            String strSub = null;
            String strSubid = null;
            String strSubdesc = null;
            String strSubsdesc = null;
            int listseq=0;

            xml = "<response><command>Add</command>";
            strMajor = request.getParameter("Major");
            strMinor = request.getParameter("Minor");
            strSubid = request.getParameter("Subid");
            strSubdesc = request.getParameter("SubDesc");
            strSubsdesc = request.getParameter("SubSDesc");
            listseq=Integer.parseInt(request.getParameter("listseq"));
            try {

                String sql =
                    "INSERT INTO SEC_MST_SUBSYSTEMS(MAJOR_SYSTEM_ID,MINOR_SYSTEM_ID,SUB_SYSTEM_ID,SUB_SYSTEM_DESC,SUB_SYSTEM_SHORT_DESC,LIST_SEQ_NO,UPDATED_BY_USER_ID,UPDATED_DATE,IP_ADDRESS) VALUES(?,?,?,?,?,?,?,?,?)";
                try {
                    ps = connection.prepareStatement(sql);
                    ps.setString(1, strMajor);
                    ps.setString(2, strMinor);
                    ps.setString(3, strSubid);
                    ps.setString(4, strSubdesc);
                    ps.setString(5, strSubsdesc);
                    ps.setInt(6, listseq);
                    ps.setString(7,updatedby);
                    ps.setTimestamp(8,ts);
                    ps.setString(9, Remote_host);
                    ps.executeUpdate();
                    xml = xml + "<flag>success</flag>";
                    xml =
 xml + "<Subcode>" + results.getString("SUB_SYSTEM_ID") +
   "</Subcode><Subdesc>" + results.getString("SUB_SYSTEM_DESC") + "</Subdesc>";
                    xml =
 xml + "<SubSDesc>" + results.getString("SUB_SYSTEM_SHORT_DESC") +
   "</SubSDesc>";
                } catch (Exception ae) {
                    System.out.println("Exception isssssssssss:" + ae);
                }
            }


            catch (Exception e) {
                System.out.println("exce ****2 vv in the insert" + e);
                xml = xml + "<flag>failure</flag>";

            }

            xml = xml + "</response>";

        }

        else if (strCommand.equals("check")) {
            String strMajor = null;
            String strMinor = null;
            String strSub = null;
            String strSubid = null;
            String strSubdesc = null;
            String strSubsdesc = null;
            String sxml = "<response><command>check</command>";
            strMajor = request.getParameter("Major");
            strMinor = request.getParameter("Minor");
            strSubid = request.getParameter("Subid");
            strSubdesc = request.getParameter("SubDesc");
            strSubsdesc = request.getParameter("SubSDesc");
            try {

                String sql =
                    "SELECT * FROM SEC_MST_SUBSYSTEMS WHERE MAJOR_SYSTEM_ID=? AND MINOR_SYSTEM_ID=? AND SUB_SYSTEM_ID=? AND SUB_SYSTEM_DESC=? AND SUB_SYSTEM_SHORT_DESC=?";
                try {
                    ps = connection.prepareStatement(sql);
                    ps.setString(1, strMajor);
                    ps.setString(2, strMinor);
                    ps.setString(3, strSubid);
                    ps.setString(4, strSubdesc);
                    ps.setString(5, strSubsdesc);


                    results = ps.executeQuery();
                    int i = 0;
                    while (results.next()) {
                        i++;

                        xml =
 xml + "<Subcode>" + results.getString("SUB_SYSTEM_ID") +
   "</Subcode><Subdesc>" + results.getString("SUB_SYSTEM_DESC") + "</Subdesc>";
                        xml =
 xml + "<SubSDesc>" + results.getString("SUB_SYSTEM_SHORT_DESC") +
   "</SubSDesc>";

                    }
                    if (i == 0) {
                        xml = sxml + "<flag>failure</flag>";
                    } else {

                        xml = sxml + "<flag>success</flag>" + xml;
                    }

                } catch (Exception ae) {
                    System.out.println("Exception isssssssssss:the check" +
                                       ae);

                }


            }

            catch (Exception e) {
                System.out.println("exce ****2 vv" + e);
                xml = xml + "<flag>failure</flag>";

            }

            xml = xml + "</response>";

        } else if (strCommand.equalsIgnoreCase("Update")) {

            xml = "<response><command>Update</command>";

            String strMajor = null;
            String strMinor = null;
            String strSubid = null;
            String strSubdesc = null;
            String strSubsdesc = null;
           int listseq=0;
            try {
                strMajor = request.getParameter("Major");
                strMinor = request.getParameter("Minor");
                strSubid = request.getParameter("Subid");
                strSubdesc = request.getParameter("SubDesc");
                strSubsdesc = request.getParameter("SubSDesc");
                listseq=Integer.parseInt(request.getParameter("listseq"));
                System.out.println("listseq"+listseq);

            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                String sql =
                    "UPDATE SEC_MST_SUBSYSTEMS SET SUB_SYSTEM_DESC=?,SUB_SYSTEM_SHORT_DESC=? ,list_seq_no=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=?, IP_ADDRESS=? where MAJOR_SYSTEM_ID=? And MINOR_SYSTEM_ID=? AND SUB_SYSTEM_ID=? ";
                ps = connection.prepareStatement(sql);
                ps.setString(7, strMajor);
                ps.setString(8, strMinor);
                ps.setString(9, strSubid);
                ps.setString(1, strSubdesc);
                ps.setString(2, strSubsdesc);
                ps.setInt(3,listseq);
                ps.setString(4,updatedby);
                ps.setTimestamp(5,ts);
                ps.setString(6, Remote_host);
                int i = ps.executeUpdate();

                xml = xml + "<flag>success</flag>";
               


            } catch (Exception e) {
                System.out.println("UPDATE exception is " + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";

        }


        System.out.println("xml is : " + xml);
        pw.write(xml);
        pw.flush();
        pw.close();


    }
}
