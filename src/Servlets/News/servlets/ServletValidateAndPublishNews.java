package Servlets.News.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import java.text.SimpleDateFormat;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class ServletValidateAndPublishNews extends HttpServlet {
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

            String str_event_id = "", str_event_desc = "", str_event_venue =
                "", str_event_date = "", str_target_url =
                "", str_event_status = "", trimed_url = "";
            int event_id = 0, event_status = 0;
            //String strSecondaryId[]=null;
            try {
                str_event_id = request.getParameter("txtEventId");
                str_event_desc = request.getParameter("txtEventDesc");
                //str_event_venue = request.getParameter("txtEventVenue");
                //str_event_date = request.getParameter("txtEventDate");
                //strDateOfFormation = request.getParameter("txtEventDate");
                str_target_url = request.getParameter("txtTargetURL");
                str_event_status = request.getParameter("cmbStatus");
                //strDateEffectiveFrom=request.getParameter("txtDate_Effective_From");
                System.out.println("values retrived sucessfully   :" +
                                   str_event_id + "  " + str_event_desc +
                                   "  " + str_event_venue + "  " +
                                   str_target_url + "ssss   " +
                                   str_event_status);
            } catch (Exception e) {
                e.printStackTrace();
                sendMessage(response,
                            "Please Fill in all the required fields. " + e,
                            "back");
                return;
            }

            // update the database
            try {
                String sql1 = "";
                // update COM_OFFICE_CONTROL set CONTROLLING_OFFICE_ID=?,DATE_EFFECTIVE_FROM=?,REMARKS=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=? where Office_Id=?";
                sql1 =
"update com_mst_news set NEWS_DESC=?,NEWS_DATE=(SELECT now() ),TARGET_URL=?,NEWS_STATUS_ID=? where NEWS_ID=?";

                System.out.println("query : " + sql1);
                PreparedStatement ps1 = null;
                ps1 = connection.prepareStatement(sql1);

                try {
                    event_id = Integer.parseInt(str_event_id);
                } catch (NumberFormatException num) {
                    System.out.println("Number format exception : " + num);
                }


                //System.out.println("cadre : " + cadreid);

                System.out.println("eveid: " + event_id);
                ps1.setString(1, str_event_desc);
                System.out.println("desc " + str_event_desc);
                //ps1.setString(2,str_event_venue);
                //System.out.println("venue " + str_event_venue);
                /*
                      java.sql.Date dateOfEvent=null;
                      System.out.println("before inserting date :"+str_event_date);
                      if(str_event_date!="" && str_event_date!=null )
                      {
                          String dateString = str_event_date;
                          SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                          java.util.Date d;
                          try {
                            d = dateFormat.parse(dateString.trim());
                            dateFormat.applyPattern("yyyy-MM-dd");
                            dateString = dateFormat.format(d);
                            dateOfEvent = java.sql.Date.valueOf(dateString);
                          } catch (Exception e) {
                            e.printStackTrace();
                            sendMessage(response,"Date of Event is not valid.<br>","back");
                          }
                      }
                      System.out.println("date"+dateOfEvent);
                     ps1.setDate(2,dateOfEvent);
                      System.out.println("url"+str_target_url);
                    */

                if (str_target_url.startsWith("http://")) {
                    trimed_url = str_target_url.replace("http://", "");
                    System.out.println(" trimed url is :" + trimed_url);
                    System.out.println("------------contains HTTP ");
                    ps1.setString(2, trimed_url);
                } else {
                    ps1.setString(2, str_target_url);
                    System.out.println(" --------NOT contains HTTP ");
                }


                // ps1.setString(2,str_target_url);
                System.out.println("id" + event_id);


                ps1.setString(3, str_event_status);
                System.out.println("id satus" + str_event_status);


                ps1.setInt(4, event_id);
                System.out.println("b4 execute");
                int i = ps1.executeUpdate();
                // ps1.close();
                if (i >= 1) {
                    System.out.println("values inserted successfully");
                    String msg = "News has been Published Successfully.";
                    msg = msg + "<br><br>";
                    sendMessage(response, msg, "ok");
                }


            }

            catch (Exception e) {
                sendMessage(response,
                            "Failed to insert values due to the Duplication of ID" +
                            e, "back");

            }

        } catch (Exception e) {
            System.out.println("Exception in openeing connection:" + e);
            sendMessage(response, "Failed to insert values due to " + e,
                        "back");
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
