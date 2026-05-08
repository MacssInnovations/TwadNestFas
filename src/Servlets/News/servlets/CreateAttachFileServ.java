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


public class CreateAttachFileServ extends HttpServlet {
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

            String Command = "", xml = "", caption = "";
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

                //String sql="select caption_id,caption from com_caption_details where caption_id=?";
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
  connection.prepareStatement("select caption_id,caption from com_caption_details where caption_id=? and process_flow_status_id in ('CR','MD')");
                        ps.setInt(1, cid);
                        rs1 = ps.executeQuery();

                        while (rs1.next()) {
                            cap_id = rs1.getInt("caption_id");
                            System.out.println("caption id..." + cap_id);
                            caption = rs1.getString("caption");
                            System.out.println("caption..." + caption);
                        }

                        int att_slno = 0;
                        ps1 =
 connection.prepareStatement("select max(ATTACH_SLNO) as ATTACH_SLNO from COM_CAPTION_ATTACH where CAPTION_ID=?");
                        ps1.setInt(1, cid);
                        rs2 = ps1.executeQuery();

                        while (rs2.next()) {
                            att_slno = rs2.getInt("ATTACH_SLNO");
                            System.out.println("attach slno..." + att_slno);
                        }

                        if (att_slno == 0) {
                            att_slno = 1;
                        } else {
                            att_slno = att_slno + 1;
                        }


                        xml = xml + "<flag>Success</flag>";
                        xml =
 xml + "<caption_id>" + cap_id + "</caption_id><caption>" + caption +
   "</caption><attach_slno>" + att_slno + "</attach_slno>";
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
        response.setContentType("application/pdf");
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

        try {
            cap_id = Integer.parseInt(request.getParameter("txtEventId"));
            caption = request.getParameter("txtCaption");
            att_slno = Integer.parseInt(request.getParameter("txtatt_slno"));
            attach_file = request.getParameter("txtattachFile");

            System.out.println("caption id..." + cap_id);
            System.out.println("caption..." + caption);
            System.out.println("attach slno...." + att_slno);
            System.out.println("attach file.." + attach_file);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("attached file..." + attach_file);
        String newstr = "", relpath = "";
        String updatedby = "";


        HttpSession session = request.getSession(false);
        updatedby = (String)session.getAttribute("UserId");
        System.out.println("updated user id...." + updatedby);


        String[] arr = new String[10];
        arr = attach_file.split("/");

        System.out.println("array value is..." + arr);

        for (int i = 0; i < arr.length - 1; i++) {
            newstr = newstr + "../";
        }

        relpath = newstr + arr[arr.length - 1];
        System.out.println("New Strign is: " + relpath);


        if (relpath.equals("\"\"")) {
            relpath = null;
        } else {
            String userAgent1 = request.getHeader("User-Agent");
            System.out.println("USER AGENT IS ..............." + userAgent1);
            String userSeparator1 = "/"; // default
            if (userAgent1.indexOf("Windows") != -1) {
                System.out.println("Comes inside----------");
                userSeparator1 = "\\";
            }
            if (userAgent1.indexOf("Linux") != -1)
                userSeparator1 = "/";
            //relpath = relpath.substring(relpath.lastIndexOf(userSeparator1)+1,relpath.length()-1);
            relpath =
                    relpath.substring(relpath.lastIndexOf(userSeparator1), relpath.length());
            //out.println("The FileName is:"+fileName);


            // if(relpath.startsWith( "\""))
            relpath = relpath.substring(1);
            System.out.println("The FileName is=======:" + relpath);
            System.out.println("The FileName is=======:" + relpath.length());
            //System.out.println("The FileName is=======:"+relpath.size());
        }

        /*

                      File file=new File(getServletConfig().getServletContext().getRealPath(rs.getString("Config.PDF_VIEW"))+"/"+relpath);
                      System.out.println(file.getPath());

                 // java.io.FileOutputStream buffer = null;

                // buffer = new java.io.FileOutputStream(file);
                 // out.println(buffer);

                  //File bfile=new File();

         String path=getServletConfig().getServletContext().getRealPath("/news/pdf"+relpath);

         System.out.println("path...."+path);

              File bfile=new File(path);
              int f=(int)bfile.length();

             String ss=bfile.getPath();
             System.out.println("String path is**************"+ss);

            System.out.println("This file is:*******"+bfile);

            */
        // FileInputStream fis=null;

        // fis = new FileInputStream(bfile);

        FileInputStream fin = new FileInputStream(attach_file);
        DataInputStream dis = new DataInputStream(fin);

        PreparedStatement pst = null;
        ResultSet rst = null;
        int i = 0;

        try {
            System.out.println("inside try");
            String sql =
                "insert into COM_CAPTION_ATTACH (CAPTION_ID,FILE_CONTENT,ATTACH_SLNO,UPDATED_DATE,UPDATEDBY_USER_ID,PROCESS_FLOW_STATUS_ID,FILE_NAME) values (?,?,?,(select now() ),?,'CR',?)";
            pst = connection.prepareStatement(sql);

            pst.setInt(1, cap_id);
            pst.setBinaryStream(2, dis, dis.available());
            pst.setInt(3, att_slno);
            pst.setString(4, updatedby);
            pst.setString(5, relpath);

            i = pst.executeUpdate();

            if (i >= 1) {
                System.out.println("insertion successfull");
                String msg = "News has been created successfully.";
                msg = msg + "<br><br>";
                sendMessage(response, msg, "ok");
            } else {
                System.out.println("insertion failure");
                String msg = "Insertion Failure.";
                msg = msg + "<br><br>";
                sendMessage(response, msg, "ok");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
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
