package Servlets.News.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.*;

import java.text.SimpleDateFormat;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class ServletEditIconDetails extends HttpServlet {
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
        System.out.println(">>>--->servlet called");


        try {
            HttpSession session = request.getSession(false);

            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");
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
//
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

        int cap_id = 0, caid = 0;
        String command = "", capt = "", cap_des = "";
        String xml = "";
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        ResultSet rs1 = null;
        ResultSet rs5 = null;

        cap_id = Integer.parseInt(request.getParameter("caption"));
        System.out.println("caption id..." + cap_id);

        command = request.getParameter("command");
        System.out.println("command..." + command);

        if (command.equalsIgnoreCase("Existg")) {
            xml = xml + "<response><command>Existg</command>";


            try {

                String sql3 =
                    "select caption_id,caption from COM_CAPTION_DETAILS where caption_id=?";

                ps1 = connection.prepareStatement(sql3);
                ps1.setInt(1, cap_id);

                rs5 = ps1.executeQuery();

                if (!rs5.next()) {
                    xml = xml + "<flag>failure</flag>";
                }

                else {

                    xml = xml + "<flag>success</flag>";
                    caid = rs1.getInt("caption_id");
                    System.out.println("caption id..." + caid);
                    capt = rs1.getString("caption");
                    System.out.println("caption..." + capt);

                    xml =
 xml + "<caption_id><![CDATA[" + rs1.getInt("caption_id") + "]]></caption_id>";
                    xml =
 xml + "<caption><![CDATA[" + rs1.getString("caption").trim() +
   "]]></caption>";


                    /*
                   else
                   {



             String sql="select caption_id,caption,BRIEF_DESC from COM_CAPTION_DETAILS where caption_id=? and PROCESS_FLOW_STATUS_ID in ('CR','MD')";
             ps=connection.prepareStatement(sql);

             ps.setInt(1,cap_id);

             rs1=ps.executeQuery();

             while(rs1.next())
             {
                 xml=xml+"<flag>success</flag>";
                 caid=rs1.getInt("caption_id");
                 System.out.println("caption id..."+caid);
                 capt=rs1.getString("caption");
                 System.out.println("caption..."+capt);
                 cap_des=rs1.getString("BRIEF_DESC");
                 System.out.println("brief desc..."+cap_des);



                xml=xml+"<caption_id><![CDATA[" +rs1.getInt("caption_id") + "]]></caption_id>";
                xml=xml+"<caption><![CDATA[" +rs1.getString("caption").trim() + "]]></caption>";
                xml=xml+"<brief_desc><![CDATA[" +rs1.getString("BRIEF_DESC").trim() + "]]></brief_desc>";


             }
                 }*/
                }

            } catch (Exception e) {
                xml = xml + "<flag>failure</flag>";
                System.out.println(e.getMessage());

            }

            xml = xml + "</response>";

            System.out.println(xml);
            //pw.println(xml);

        }


        pw.println(xml);


    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        response.setContentType(CONTENT_TYPE);
        //PrintWriter pw=response.getWriter();
        System.out.println(">>>---servlet called");
        String updatedby = "";

        try {
            HttpSession session = request.getSession(false);
            //updatedby=(String)session.getAttribute("UserId");


            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");

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

            String capt = "", brief_desc = "", attach_file = "", capt_date =
                "";
            int capt_id = 0;
            PreparedStatement ps2 = null;
            ResultSet rs2 = null;
            int cid = 0;
            String rd_Icon = "";
            int caption = 0;

            try {
                caption = Integer.parseInt(request.getParameter("cmbIcon"));
                System.out.println("caption..." + caption);

                rd_Icon = request.getParameter("rd_ico");
                System.out.println("icon..." + rd_Icon);


                HttpSession session = request.getSession(false);
                updatedby = (String)session.getAttribute("UserId");
                System.out.println("updated user id...." + updatedby);

            } catch (Exception e) {
                e.printStackTrace();
                sendMessage(response,
                            "Please Fill in all the required fields. " + e,
                            "back");
                return;
            }

            try {
                String sql1 = "";
                sql1 =
"update COM_CAPTION_DETAILS set new_icon=? where caption_id=?";


                System.out.println("query : " + sql1);
                PreparedStatement ps1 = null;


                ps1 = connection.prepareStatement(sql1);

                ps1.setString(1, rd_Icon);
                ps1.setInt(2, caption);


                System.out.println("b4 execute");
                int i = ps1.executeUpdate();

                if (i >= 1) {
                    System.out.println("inside if");


                    System.out.println("Values updated Successfully");
                    String msg = "Data has been Updated Successfully.";
                    msg = msg + "<br><br>";
                    sendMessage(response, msg, "ok");
                }


            }

            catch (Exception e) {
                sendMessage(response, "Failed to update values", "back");

            }

        } catch (Exception e) {
            System.out.println("Exception in openeing connection:" + e);
            sendMessage(response, "Failed to update values due " + e, "back");
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
