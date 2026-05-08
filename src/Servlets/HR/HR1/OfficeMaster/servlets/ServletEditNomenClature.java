package Servlets.HR.HR1.OfficeMaster.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.DriverManager;
import java.sql.*;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class ServletEditNomenClature extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";
    private Connection connection = null;
    private PreparedStatement ps = null;
    private ResultSet res = null;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        /*try
        {
            HttpSession session=request.getSession(false);
            if(session==null)
            {
                System.out.println(request.getContextPath()+"/index.jsp");
                response.sendRedirect(request.getContextPath()+"/index.jsp");

            }
            System.out.println(session);

        }catch(Exception e)
        {
        System.out.println("Redirect Error :"+e);
        }*/

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

            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection

            Class.forName(strDriver.trim());
            connection =
                    DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                                                strdbpassword.trim());
        } catch (Exception e) {
            System.out.println("Exception in Connection:" + e);
        }
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();

        int IntID = 0;
        try {
            IntID = Integer.parseInt(request.getParameter("OfficeId"));
        } catch (NumberFormatException nfe) {
            System.out.println("NumberFormatException :" + nfe);
        }
        String xml = "";

        try {
            String sql =
                "select new_office_name,NEW_SHORT_NAME,NEW_PRIMARY_WORK_ID,date_of_nomenclature_change,EMP_RELIEVAL_DATE,EMP_JOIN_DATE,EMP_RELIEVAL_SESSION,EMP_JOIN_SESSION,process_flow_status_id from com_office_nomenclature where office_id=?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, IntID);
            res = ps.executeQuery();
            if (res.next()) {

                java.sql.Date DateOfFormation =
                    res.getDate("date_of_nomenclature_change");
                String DateToBeDisplayed = "";
                if (DateOfFormation == null) {
                    DateToBeDisplayed = "";
                } else {
                    try {
                        java.text.SimpleDateFormat sdf =
                            new java.text.SimpleDateFormat("dd/MM/yyyy");
                        DateToBeDisplayed = sdf.format(DateOfFormation);
                    } catch (Exception e) {
                        System.out.println("error while formatting date : " +
                                           e);
                        DateToBeDisplayed = "Not Specified";
                    }
                }


                java.sql.Date DateOfRelieval =
                    res.getDate("EMP_RELIEVAL_DATE");
                String RelievalDate = "";
                if (DateOfRelieval == null) {
                    RelievalDate = "";
                } else {
                    try {
                        java.text.SimpleDateFormat sdf =
                            new java.text.SimpleDateFormat("dd/MM/yyyy");
                        RelievalDate = sdf.format(DateOfRelieval);
                    } catch (Exception e) {
                        System.out.println("error while formatting date : " +
                                           e);
                        RelievalDate = "Not Specified";
                    }
                }


                java.sql.Date DateOfJoining = res.getDate("EMP_JOIN_DATE");
                String JoiningDate = "";
                if (DateOfJoining == null) {
                    JoiningDate = "";
                } else {
                    try {
                        java.text.SimpleDateFormat sdf =
                            new java.text.SimpleDateFormat("dd/MM/yyyy");
                        JoiningDate = sdf.format(DateOfJoining);
                    } catch (Exception e) {
                        System.out.println("error while formatting date : " +
                                           e);
                        JoiningDate = "Not Specified";
                    }
                }


                response.setContentType("text/xml");
                xml =
 "<response><flag>success</flag><date>" + DateToBeDisplayed +
   "</date><dateRelieval>" + RelievalDate + "</dateRelieval><dateJoining>" +
   JoiningDate + "</dateJoining><relievalSession>" +
   res.getString("EMP_RELIEVAL_SESSION") + "</relievalSession><joinSession>" +
   res.getString("EMP_JOIN_SESSION") + "</joinSession><officename>" +
   res.getString("new_office_name") + "</officename><shortname>" +
   res.getString("NEW_SHORT_NAME") + "</shortname><primaryid>" +
   res.getString("NEW_PRIMARY_WORK_ID") + "</primaryid><recordstatus>" +
   res.getString("PROCESS_FLOW_STATUS_ID") + "</recordstatus></response>";

                out.write(xml);
                System.out.println("xml is:" + xml);
            } else {
                response.setContentType("text/xml");
                xml = "<response><flag>failure</flag></response>";
                out.write(xml);
                System.out.println("xml is:" + xml);
            }
        } catch (Exception e) {
            System.out.println("Exception in Select:" + e);
        }
        out.close();
    }
}
