package Servlets.HR.HR1.Masters.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.*;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

import Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver;

public class MinorServlet extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";
    private Connection connection = null;
    private Statement statement = null;
    private ResultSet results = null;
    private PreparedStatement ps = null;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        System.out.println("*************in init creating statement*********:");

        try {
        	LoadDriver driver=new LoadDriver();
        	connection=driver.getConnection();

            try {
                statement = connection.createStatement();
                connection.clearWarnings();
            } catch (SQLException e) {
                System.out.println("*************Exception in creating statement*********:" +
                                   e);
            }
        } catch (Exception e) {
            System.out.println("________Exception in opening connection:_______________" +
                               e);
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
        try {
            strCommand = request.getParameter("command");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (strCommand.equalsIgnoreCase("Load")) {

            String sxml = "<response><command>Load</command>";

            String strMajorId = request.getParameter("MajorId");

            try {

                String sql =
                    "SELECT * FROM SEC_MST_MINOR_SYSTEMS WHERE MAJOR_SYSTEM_ID=? order by minor_system_id";
                ps = connection.prepareStatement(sql);
                ps.setString(1, strMajorId);
                results = ps.executeQuery();
                int i = 0;

                while (results.next()) {
                    i++;
                    xml =
 xml + "<MAJOR_ID>" + strMajorId + "</MAJOR_ID><MINOR_ID>" +
   results.getString("MINOR_SYSTEM_ID").replace("&", "and") +
   "</MINOR_ID><MINOR_DESC>" +
   results.getString("MINOR_SYSTEM_DESC").replace("&", "and") +
   "</MINOR_DESC><MINOR_SDESC>" +
   results.getString("MINOR_SYSTEM_SHORT_DESC").replace("&", "and") +
   "</MINOR_SDESC>";
   xml=xml+"<listseq>"+ results.getInt("LIST_SEQ_NO")+"</listseq>";
                }
                if (i == 0) {
                    xml = sxml + "<flag>NoRecord</flag>";
                } else {
                    xml = sxml + "<flag>success</flag>" + xml;
                }


            }

            catch (Exception e) {
                System.out.println("ExceptioN IN THE load:" + e);
                xml = sxml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";

        }

        else if (strCommand.equalsIgnoreCase("Delete")) {

            xml = "<response><command>Delete</command>";

            String strMajor = null;
            String strMinor = null;
            String strMinordesc = null;
            String strMinorsdesc = null;

            try {
                strMajor = request.getParameter("MajorId");
                strMinor = request.getParameter("MinorId");
                strMinordesc = request.getParameter("MinorDesc");
                strMinorsdesc = request.getParameter("MinorSDesc");


            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                String sql =
                    "delete from SEC_MST_MINOR_SYSTEMS where MAJOR_SYSTEM_ID=? And MINOR_SYSTEM_ID=?  AND MINOR_SYSTEM_DESC=? AND MINOR_SYSTEM_SHORT_DESC=?";
                ps = connection.prepareStatement(sql);
                ps.setString(1, strMajor);
                ps.setString(2, strMinor);
                ps.setString(3, strMinordesc);
                ps.setString(4, strMinorsdesc);


                int i = ps.executeUpdate();

                xml =
 xml + "<flag>success</flag><Minorid>" + strMinor + "</Minorid>";

            } catch (Exception e) {
                System.out.println("delete exception is " + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";

        }

        else if (strCommand.equalsIgnoreCase("Update")) {

            xml = "<response><command>Update</command>";

            String strMajorid = null;
            String strMinorid = null;
            String strMinordesc = null;
            String strMinorsdesc = null;
            int listseq=0;
            try {
                strMajorid = request.getParameter("MajorId");
                strMinorid = request.getParameter("MinorId");
                strMinordesc = request.getParameter("MinorDesc");
                strMinorsdesc = request.getParameter("MinorSDesc");
                listseq=Integer.parseInt(request.getParameter("listseq"));

            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                String sql =
                    "UPDATE SEC_MST_MINOR_SYSTEMS SET MINOR_SYSTEM_DESC=?,MINOR_SYSTEM_SHORT_DESC=?,list_seq_no=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=?,IP_ADDRESS=? where MAJOR_SYSTEM_ID=? AND MINOR_SYSTEM_ID=?";
                ps = connection.prepareStatement(sql);
                ps.setString(7, strMajorid);
                ps.setString(8, strMinorid);
                ps.setString(1, strMinordesc);
                ps.setString(2, strMinorsdesc);
                ps.setInt(3, listseq);
                ps.setString(4,updatedby);
                ps.setTimestamp(5,ts);
                ps.setString(6,Remote_host);

                int i = ps.executeUpdate();

                xml = xml + "<flag>success</flag>";
              

            } catch (Exception e) {
                System.out.println("UPDATE exception is " + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";

        }

        else if (strCommand.equals("check")) {
            String strMajorid = null;
            String strMinorid = null;
            String strMinordesc = null;
            String strMinorsdesc = null;
            String sxml = "<response><command>check</command>";
            strMajorid = request.getParameter("MajorId");
            strMinorid = request.getParameter("MinorId");
            strMinordesc = request.getParameter("MinorDesc");
            strMinorsdesc = request.getParameter("MinorSDesc");
            try {

                String sql =
                    "SELECT * FROM SEC_MST_MINOR_SYSTEMS WHERE MAJOR_SYSTEM_ID=? AND MINOR_SYSTEM_ID=? AND MINOR_SYSTEM_DESC=? AND MINOR_SYSTEM_SHORT_DESC=?";
                try {
                    ps = connection.prepareStatement(sql);
                    ps.setString(1, strMajorid);
                    ps.setString(2, strMinorid);
                    ps.setString(3, strMinordesc);
                    ps.setString(4, strMinorsdesc);


                    results = ps.executeQuery();
                    int i = 0;
                    while (results.next()) {
                        i++;

                        xml =
 xml + "<MAJOR_ID>" + strMajorid + "</MAJOR_ID><MINOR_ID>" +
   results.getString("MINOR_SYSTEM_ID") + "</MINOR_ID><MINOR_DESC>" +
   results.getString("MINOR_SYSTEM_DESC") + "</MINOR_DESC><MINOR_SDESC>" +
   results.getString("MINOR_SYSTEM_SHORT_DESC") + "</MINOR_SDESC>";

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

        }

        else if (strCommand.equals("Add")) {
            String strMajorid = null;
            String strMinorid = null;
            String strMinordesc = null;
            String strMinorsdesc = null;
            int listseq=0;

            xml = "<response><command>Add</command>";
            strMajorid = request.getParameter("MajorId");
            strMinorid = request.getParameter("MinorId").toUpperCase();
            strMinordesc = request.getParameter("MinorDesc");
            strMinorsdesc = request.getParameter("MinorSDesc");
            listseq=Integer.parseInt(request.getParameter("listseq"));
            try {

                String sql =
                    "INSERT INTO SEC_MST_MINOR_SYSTEMS(MAJOR_SYSTEM_ID,MINOR_SYSTEM_ID,MINOR_SYSTEM_DESC,MINOR_SYSTEM_SHORT_DESC,LIST_SEQ_NO,UPDATED_BY_USER_ID,UPDATED_DATE,IP_ADDRESS) VALUES(?,?,?,?,?,?,?,?)";
                try {
                    ps = connection.prepareStatement(sql);
                    ps.setString(1, strMajorid);
                    ps.setString(2, strMinorid);
                    ps.setString(3, strMinordesc);
                    ps.setString(4, strMinorsdesc);
                    ps.setInt(5,listseq );
                    ps.setString(6,updatedby);
                    ps.setTimestamp(7,ts);
                    ps.setString(8,Remote_host);

                    ps.executeUpdate();
                    xml = xml + "<flag>success</flag>";
               
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

        System.out.println("xml is : " + xml);
        pw.write(xml);
        pw.flush();
        pw.close();
    }
}
