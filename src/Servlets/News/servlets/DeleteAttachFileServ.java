package Servlets.News.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.*;

import java.util.*;

import java.text.SimpleDateFormat;

import java.util.ResourceBundle;

import java.io.File;
import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class DeleteAttachFileServ extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";
    private Connection connection = null;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter pw = response.getWriter();

        System.out.println("inside servlet");

        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                /* response.setContentType("text/xml");
                      response.setHeader("Cache-Control","no-cache");
                     String xml="<response><command>session</command><flag>failure</flag><flag>Session already closed.</flag></response>";
                     System.out.println(xml);
                      out.println(xml);
                      out.close();
                      return;*/
            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }

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

            String Command = "", xml = "", caption = "", fil = "";
            int cid = 0, cap_id = 0;
            PreparedStatement ps = null;
            PreparedStatement ps1 = null;
            ResultSet rs1 = null;
            ResultSet rs2 = null;
            PreparedStatement ps5 = null;
            ResultSet rs5 = null;


            try {
                Command = request.getParameter("Command");
                System.out.println("command..." + Command);
                cid = Integer.parseInt(request.getParameter("CapId"));
                System.out.println("caption id..." + cid);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }


            if (Command.equalsIgnoreCase("Existg")) {
                xml = "<response><command>Existg</command>";
                xml = xml + "<flag>Success</flag>";


                try {

                    String sql3 =
                        "select caption_id from COM_CAPTION_DETAILS where caption_id=?";

                    ps5 = connection.prepareStatement(sql3);
                    ps5.setInt(1, cid);

                    rs5 = ps5.executeQuery();

                    if (!rs5.next()) {
                        xml = xml + "<flag>Failure</flag>";
                    }

                    else {


                        ps =
  connection.prepareStatement("select caption_id,caption from com_caption_details where caption_id=?");
                        ps.setInt(1, cid);
                        rs1 = ps.executeQuery();

                        while (rs1.next()) {
                            cap_id = rs1.getInt("caption_id");
                            System.out.println("caption id..." + cap_id);
                            caption = rs1.getString("caption");
                            System.out.println("caption..." + caption);

                            xml =
 xml + "<caption_id>" + cap_id + "</caption_id><caption>" + caption +
   "</caption>";
                        }

                        int att_slno = 0;
                        ps1 =
 connection.prepareStatement("select ATTACH_SLNO,FILE_NAME from COM_CAPTION_ATTACH where CAPTION_ID=? and PROCESS_FLOW_STATUS_ID='CR'");
                        ps1.setInt(1, cid);
                        rs2 = ps1.executeQuery();

                        while (rs2.next()) {
                            att_slno = rs2.getInt("ATTACH_SLNO");
                            System.out.println("attach slno..." + att_slno);
                            fil = rs2.getString("FILE_NAME");
                            System.out.println("file name..." + fil);

                            xml =
 xml + "<details><attach_slno>" + att_slno + "</attach_slno><file_title>" +
   fil + "</file_title></details>";

                        }


                        /*
                     xml=xml+"<flag>Success</flag>";
                     xml=xml+"<caption_id>"+cap_id+"</caption_id><caption>"+caption+"</caption>";
                     xml=xml+"<attach_slno>"+att_slno+"</attach_slno><file_title>"+fil+"</file_title>";
                     */
                    }
                } catch (Exception e) {
                    xml = xml + "<flag>Failure</flag>";
                    System.out.println(e.getMessage());
                }

                xml = xml + "</response>";
                pw.println(xml);
                System.out.println(xml);


            }


        } catch (Exception e) {
            System.out.println("Exception in openeing connection:" + e);
            //sendMessage(response,"Failed to insert values due to " + e,"back");
        }


    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {

        System.out.println("inside post method");

        //response.setContentType(CONTENT_TYPE);
        //response.setContentType("application/pdf");
        PrintWriter out = response.getWriter();

        System.out.println("inside servlet 2");

        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                /* response.setContentType("text/xml");
                       response.setHeader("Cache-Control","no-cache");
                      String xml="<response><command>session</command><flag>failure</flag><flag>Session already closed.</flag></response>";
                      System.out.println(xml);
                       out.println(xml);
                       out.close();
                       return;*/
            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }

        ResourceBundle rs = null;

        try {
            rs = ResourceBundle.getBundle("Servlets.Security.servlets.Config");
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
            System.out.println(e.getMessage());
        }

        String caption = "", attach_file = "";
        int cap_id = 0, att_slno = 0;

        PreparedStatement ps3 = null;
        ResultSet rs3 = null;
        String command = "";
        String selectedAtt_no = "";

        command = request.getParameter("Command");
        System.out.println("command is..." + command);

        if (command.equalsIgnoreCase("DelFile")) {


            cap_id = Integer.parseInt(request.getParameter("CapId"));
            System.out.println("caption id..." + cap_id);

            selectedAtt_no = request.getParameter("selected");
            System.out.println("selected attach no..." + selectedAtt_no);

            String strselatt[] = selectedAtt_no.split(",");

            String newstr = "", relpath = "";
            String updatedby = "";
            PreparedStatement pst = null;
            ResultSet rst = null;
            int j = 0;


            HttpSession session = request.getSession(false);
            updatedby = (String)session.getAttribute("UserId");
            System.out.println("updated user id...." + updatedby);


            for (int i = 0; i < strselatt.length; i++) {
                System.out.println("attach slno..." + strselatt[i]);

                int attch_slno = Integer.parseInt(strselatt[i]);

                System.out.println("attached slno..1.." + attch_slno);

                try {
                    System.out.println("inside try");

                    String sql =
                        "update COM_CAPTION_ATTACH set UPDATEDBY_USER_ID=?,UPDATED_DATE=(select now() ),PROCESS_FLOW_STATUS_ID='DL' where CAPTION_ID=? and ATTACH_SLNO=?";
                    pst = connection.prepareStatement(sql);

                    pst.setString(1, updatedby);
                    pst.setInt(2, cap_id);
                    pst.setInt(3, attch_slno);

                    j = pst.executeUpdate();

                    if (j >= 1) {
                        System.out.println("deletion successfull");
                        String msg =
                            "Attachment has been Deleted Successfully.";
                        msg = msg + "<br><br>";
                        sendMessage(response, msg, "ok");
                    } else {
                        System.out.println("insertion failure");
                        String msg = "Deletion Failure.";
                        msg = msg + "<br><br>";
                        sendMessage(response, msg, "ok");
                    }

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }


        }


    }

    private void sendMessage(HttpServletResponse response, String msg,
                             String bType) {
        try {
            String url =
                "org/Library/jsps/Messenger.jsp?message=" + msg + "&button=" +
                bType;
            response.sendRedirect(url);
        } catch (IOException e) {
        }
    }
}
