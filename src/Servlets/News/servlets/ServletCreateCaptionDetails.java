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

public class ServletCreateCaptionDetails extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";
    private Connection connection = null;


    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter pw = response.getWriter();
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

            try {
                capt = request.getParameter("txtCaption");
                System.out.println("caption..." + capt);
                brief_desc = request.getParameter("txtDesc");
                System.out.println("brief description..." + brief_desc);
                capt_date = request.getParameter("txtEventDate_h");
                System.out.println("caption date..." + capt_date);
                //attach_file=request.getParameter("txtattachFile");
                //System.out.println("attach file..."+attach_file);

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
                String sql2 =
                    "select max(CAPTION_ID) as CAPTION_ID from COM_CAPTION_DETAILS";
                ps2 = connection.prepareStatement(sql2);
                rs2 = ps2.executeQuery();

                while (rs2.next()) {
                    cid = rs2.getInt("CAPTION_ID");
                    System.out.println("max caption id..." + cid);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }


            try {
                String sql1 = "";
                sql1 =
"insert into COM_CAPTION_DETAILS (CAPTION,BRIEF_DESC,UPDATEDBY_USER_ID,UPDATED_DATE,PROCESS_FLOW_STATUS_ID,NEW_ICON) values (?,?,?,(select now() ),'CR','Y')";


                System.out.println("query : " + sql1);
                PreparedStatement ps1 = null;


                ps1 = connection.prepareStatement(sql1);

                ps1.setString(1, capt);
                ps1.setString(2, brief_desc);
                ps1.setString(3, updatedby);


                System.out.println("b4 execute");
                int i = ps1.executeUpdate();

                if (i >= 1) {
                    System.out.println("inside if");


                    System.out.println("values inserted successfully");
                    String msg =
                        "News has been created successfully. Caption id: " +
                        (cid + 1);
                    msg = msg + "<br><br>";
                    sendMessage(response, msg, "ok");
                }


            }

            catch (Exception e) {
                sendMessage(response,
                            "Failed to insert values due to duplication of id",
                            "back");

            }

        } catch (Exception e) {
            System.out.println("Exception in openeing connection:" + e);
            sendMessage(response, "Failed to insert values due " + e, "back");
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
