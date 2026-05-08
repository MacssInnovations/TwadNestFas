package Servlets.News.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.*;

import java.util.ResourceBundle;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.FileInputStream;

import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.io.FilenameUtils;

import java.lang.Exception;

import java.util.Iterator;

public class UploadAttachFileServ extends HttpServlet {

    private Connection connection = null;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {

        String CONTENT_TYPE = "text/xml; charset=windows-1252";
        response.setContentType(CONTENT_TYPE);
        PrintWriter pw = response.getWriter();

        System.out.println("inside servlet1");

        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                /*
				 * response.setContentType("text/xml");
				 * response.setHeader("Cache-Control","no-cache"); Stringxml=
				 * "<response><command>session</command><flag>failure</flag><flag>Session already closed.</flag></response>"
				 * ; System.out.println(xml); out.println(xml); out.close();
				 * return;
				 */
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
            String pfs = "";

            if (Command.equalsIgnoreCase("Existg")) {
                xml = "<response><command>Existg</command>";

                // String
                // sql="select caption_id,caption from com_caption_details where caption_id=?";
                try {

                    String sql3 =
                        "select caption_id,process_flow_status_id from COM_CAPTION_DETAILS where caption_id=?";

                    ps5 = connection.prepareStatement(sql3);
                    ps5.setInt(1, cid);

                    rs5 = ps5.executeQuery();

                    if (!rs5.next()) {
                        xml = xml + "<flag>Failure</flag>";
                    }

                    while (rs5.next()) {
                        pfs = rs5.getString("process_flow_status_id");
                    }

                    if (pfs.equalsIgnoreCase("PB")) {
                        xml = xml + "<flag>Failure1</flag>";
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
                        HttpSession session = request.getSession(false);

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

                        /*
						 * request.setAttribute("capid",cap_id);
						 * request.setAttribute("attslno",att_slno);
						 */
                        // System.out.println(
                        // "0000000000"+request.getAttribute("capid"));
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
            // sendMessage(response,"Failed to insert values due to " +
            // e,"back");
        }

    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {

        System.out.println("inside post method");

        // response.setContentType(CONTENT_TYPE);
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        System.out.println("inside servlet 2");

        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");
            }
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

            ConnectionString =
                    strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
                    ":" + strsid.trim();
            Class.forName(strDriver.trim());
            connection =
                    DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                                                strdbpassword.trim());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        String caption = "", attach_file = "";
        int cap_id = 0, att_slno = 0;
        String fileName = "", newfile = "";

        PreparedStatement ps3 = null;
        ResultSet rs3 = null;

        System.out.println("Event id:::" + request.getParameter("txtEventId"));
        cap_id = Integer.parseInt(request.getParameter("txtEventId"));
        System.out.println("cap id..." + cap_id);
        caption = request.getParameter("txtCaption");
        System.out.println("caption..." + caption);
        att_slno = Integer.parseInt(request.getParameter("txtatt_slno"));
        System.out.println("att_slno..." + att_slno);
        attach_file = request.getParameter("txtattachFile");
        System.out.println("attach_file..." + attach_file);


        System.out.println("attached file..." + attach_file);
        String newstr = "", relpath = "";
        String updatedby = "";

        HttpSession session = request.getSession(false);
        updatedby = (String)session.getAttribute("UserId");
        System.out.println("updated user id...." + updatedby);

        if (ServletFileUpload.isMultipartContent(request)) { // -- ONE

            ServletFileUpload servletFileUpload =
                new ServletFileUpload(new DiskFileItemFactory());

            try { // -- TWO

                FileItem fileItem = null;

                java.util.List fileItemsList =
                    servletFileUpload.parseRequest(request);
                System.out.println("Get the List");
                String optionalFileName = "";

                Iterator it = fileItemsList.iterator();
                String val = "";

                while (it.hasNext()) {

                    FileItem fileItemTemp = (FileItem)it.next();
                    System.out.println("The Field Name = " +
                                       fileItemTemp.getFieldName());

                    if (fileItemTemp.isFormField()) {

                        if (fileItemTemp.getFieldName().equals("filename")) {
                            System.out.println("inside ");
                            optionalFileName = fileItemTemp.getString();
                        }
                    } else
                        fileItem = fileItemTemp;
                }

                if (fileItem != null) { // -- THREE

                    fileName = fileItem.getName();
                    System.out.println("The Content Type = " +
                                       fileItem.getContentType());
                    System.out.println("The File Name = " + fileName);
                    System.out.println("The File Length = " +
                                       fileItem.getSize());

                    if (fileItem.getSize() > 0) { // -- FOUR

                        if (optionalFileName.trim().equals(""))
                            fileName = FilenameUtils.getName(fileName);
                        else
                            fileName = optionalFileName;

                        // fileName="C:\\Documents and Settings\\admin.MEPZ\\My Documents\\About TWAD.doc";

                        String userAgent = request.getHeader("User-Agent");
                        System.out.println("The User Agent ========== " +
                                           userAgent);
                        String userSeparator = "/";

                        if (userAgent.indexOf("Windows") != -1)
                            userSeparator = "\\";
                        if (userAgent.indexOf("Linux") != -1)
                            userSeparator = "/";
                        System.out.println("The File Separator === " +
                                           userSeparator);
                        rs =
  ResourceBundle.getBundle("Servlets.Security.servlets.Config");

                        File f =
                            new File(getServletConfig().getServletContext().getRealPath(rs.getString("Config.PDF_PATH_VIEW")) +
                                     userSeparator + fileName);

                        // File f=new File(f);
                        System.out.println("after file" + f);

                        String path = f.getPath();

                        File saveTo = new File(path);
                        try {
                            fileItem.write(saveTo);
                        } catch (Exception e) {
                            out.println("File is not Writed...");
                            out.println(e);
                        }

                        // File f=new
                        // File("C:\\Documents and Settings\\admin.MEPZ\\My Documents\\About TWAD.doc");
                        FileInputStream fin = new FileInputStream(f);
                        DataInputStream dis = new DataInputStream(fin);

                        PreparedStatement pst = null;
                        ResultSet rst = null;
                        int i = 0;

                        try {

                            System.out.println("inside try");
                            String sql =
                                "insert into COM_CAPTION_ATTACH (CAPTION_ID,FILE_CONTENT,ATTACH_SLNO,UPDATED_DATE,UPDATEDBY_USER_ID,PROCESS_FLOW_STATUS_ID,FILE_NAME) values (?,?,?,(select now() ),?,'CR',?)";
                            pst = connection.prepareStatement(sql);
                            System.out.println("cap_id-->" + cap_id);
                            System.out.println("dis.available()-->" +
                                               dis.available());
                            System.out.println("att_slno-->" + att_slno);
                            System.out.println("updatedby-->" + updatedby);
                            System.out.println("fileName-->" + fileName);

                            pst.setInt(1, cap_id);
                            pst.setBinaryStream(2, dis, (int)dis.available());
                            pst.setInt(3, att_slno);
                            pst.setString(4, updatedby);
                            pst.setString(5, fileName);

                            i = pst.executeUpdate();

                            if (i >= 1) {
                                System.out.println("insertion successfull");
                                String msg =
                                    "News has been created successfully.";
                                msg = msg + "<br><br>";
                                sendMessage(response, msg, "ok");
                            } else {
                                System.out.println("insertion failure");
                                String msg = "Insertion Failure.";
                                msg = msg + "<br><br>";
                                sendMessage(response, msg, "ok");
                            }

                            /*
							 * CallableStatement cs=null;
							 * cs=connection.prepareCall
							 * ("{call FILE_UPLOAD_NEWS(?,?,?,?,?,?)}") ;
							 * cs.setInt(1,cap_id); cs.setInt(2,att_slno);
							 * cs.setBinaryStream(3,dis);
							 * cs.setString(4,updatedby);
							 * cs.setString(5,fileName);
							 * cs.registerOutParameter(
							 * 6,java.sql.Types.NUMERIC); cs.execute(); int
							 * errcode=cs.getInt(6);
							 * System.out.println("SQLCODE--->>>>"+errcode);
							 */

                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }

                    } // -- FOUR

                } // -- THREE

            } // -- TWO
            catch (Exception e) {
                System.out.println(e);
                out.println(e);
            }
        } // -- ONE

    } // -- doPost End

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
