package Servlets.News.servlets;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.*;

import java.text.SimpleDateFormat;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;


public class ServletRePublishCaptionDetails implements Filter {
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";
    private Connection connection = null;

    private FilterConfig _filterConfig = null;

    public void init(FilterConfig filterConfig) throws ServletException {
        _filterConfig = filterConfig;
    }

    public void destroy() {
        _filterConfig = null;
    }


    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain arg2) throws IOException,
                                                  ServletException {
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)res;
        response.setContentType(CONTENT_TYPE);
        PrintWriter pw = response.getWriter();
        System.out.println(">>>---servlet called");
        String updatedby = "";


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

            //ConnectionString =                strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" + strsid.trim();
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
            boolean flag = false;


            try {
                String sql2 = "";
                ResultSet rs1 = null;

                int nid = 0;
                String news_desc = "", turl = "", xml = "", icon = "";

                sql2 =
"select caption_id,caption,new_icon from COM_CAPTION_DETAILS where PROCESS_FLOW_STATUS_ID='PB' order by caption_id desc";

                System.out.println("query : " + sql2);
                PreparedStatement ps3 = null;
                ps3 = connection.prepareStatement(sql2);
                rs1 = ps3.executeQuery();

                xml = "<?xml version=\"1.0\" encoding=\"windows-1252\" ?>";
                xml = xml + "<News>";


                while (rs1.next()) {
                    nid = rs1.getInt("CAPTION_ID");
                    System.out.println("news id..." + nid);
                    news_desc = rs1.getString("CAPTION");
                    System.out.println("news desc..." + news_desc);
                    icon = rs1.getString("new_icon");
                    System.out.println("icon..." + icon);


                    xml = xml + "<Item><capId>" + nid + "</capId>";
                    xml = xml + "<caption>" + news_desc + "</caption>";
                    xml = xml + "<icon>" + icon + "</icon>";
                    xml = xml + "</Item>";
                    //xml=xml+"<url>"+turl+"</url></item>";
                    flag = true;

                }

                xml = xml + "</News>";

                if (flag == true) {
                    System.out.println("inside if");

                    System.out.println("values inserted successfully");
                    String msg = "News has been RePublished Successfully.";
                    msg = msg + "<br><br>";
                    //  sendMessage(response,msg,"ok");
                }

                String matter[] = { xml };
                System.out.println("matter..." + matter);
                try {
                    /*	String path=  request.getContextPath()+rs.getString("Config.NEWS_FILE");
                                    FileWriter writer=new FileWriter(path+"news.xml");*/
                    FileWriter writer =
                        new FileWriter(request.getRealPath("news.xml"));

                    System.out.println("inside file");

                    for (int j = 0; j < matter.length; j++) {


                        pw = new PrintWriter(writer);
                        pw.println(matter[j]);

                    }
                    pw.close();
                    writer.close();
                    response.sendRedirect(request.getContextPath() +
                                          "/index.jsp");

                } catch (Exception e) {

                    e.printStackTrace();
                }


            }

            catch (Exception e) {
                //  sendMessage(response,"Failed to RePublish values due to " + e,"back");

            }


        } catch (Exception e) {
            System.out.println("Exception in openeing connection:" + e);
            // sendMessage(response,"Failed to RePublish values due "+e,"back");
        }


    }


}


