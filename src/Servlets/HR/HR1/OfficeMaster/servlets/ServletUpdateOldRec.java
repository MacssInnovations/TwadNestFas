package Servlets.HR.HR1.OfficeMaster.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.text.SimpleDateFormat;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class ServletUpdateOldRec extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        response.setContentType(CONTENT_TYPE);
        System.out.println("******servlet called****");
        int strExistingId = 0, strClosedId = 0;
        String strDateOfHandover = "", strRemarks = "";

        int intOfficeId = 0;

        Connection connection = null;
        PreparedStatement ps = null;
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


            try {

                strExistingId =
                        Integer.parseInt(request.getParameter("txtOffice_Id"));
                System.out.println("************  first ID :" + strExistingId);
                /*    strClosedId = Integer.parseInt(request.getParameter("txtAttachedOfficeID"));
                   strDateOfHandover = request.getParameter("txt_DateHandover");
                   strRemarks = request.getParameter("txt_Remark");
                   System.out.println("************  :"+strExistingId);
                    java.sql.Date dateOfConversion=null;
                    System.out.println("before converting date");
                    String dateString = strDateOfHandover;
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    java.util.Date d;
                    d = dateFormat.parse(dateString.trim());
                    dateFormat.applyPattern("yyyy-MM-dd");
                    dateString = dateFormat.format(d);
                    dateOfConversion = java.sql.Date.valueOf(dateString);

                    System.out.println("converted date "+dateOfConversion);

                   strRemarks=request.getParameter("txtRemarks");
                   */
                String slno[] = request.getParameterValues("sno");
                //System.out.println(slno);
                int slno1[] = new int[slno.length];
                System.out.println("^^^" + slno1);

                String ClosedId[] = request.getParameterValues("Office_Id");
                String ClosedName[] =
                    request.getParameterValues("Office_Name");
                //String ClosedDate[]=request.getParameterValues("Closure_Date");
                String HandOverDate[] =
                    request.getParameterValues("Handover_Date");
                String Remark[] = request.getParameterValues("Remark");
                // String naturework[]=request.getParameterValues("naturework");
                //String email[]=request.getParameterValues("email");

                int officeid[] = new int[slno.length];
                for (int k = 0; k < slno.length; k++) {
                    slno1[k] = Integer.parseInt(slno[k]);
                    officeid[k] = Integer.parseInt(ClosedId[k]);
                    System.out.println("slno is" + slno[k]);
                    System.out.println("second id is  :" + officeid[k]);

                }


                //Convert into Sql Date
                java.sql.Date date1[] = new java.sql.Date[slno.length];
                java.util.Date d1 = null;
                //System.out.println("datecreated length"+datecreated.length);

                for (int i = 0; i < slno.length; i++) {
                    SimpleDateFormat dateFormat1 =
                        new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        //System.out.println("inside date");
                        d1 = dateFormat1.parse(HandOverDate[i]);
                        dateFormat1.applyPattern("yyyy-MM-dd");
                        //System.out.println("inside date1");
                        HandOverDate[i] = dateFormat1.format(d1);
                        System.out.println("inside date2:" + HandOverDate[i]);
                        date1[i] = Date.valueOf(HandOverDate[i]);
                        System.out.println("date is   :" + date1[i]);

                    } catch (Exception e) {
                        System.out.println("Exception in Date Converted" + e);
                    }
                }
                try {
                    System.out.println("Add");
                    String sql =
                        "insert into COM_OFFICE_CLOSED_RECORDS(OFFICE_ID,OLD_REC_OFFICE_ID,DATE_EFFECTIVE_FROM,REMARKS) values(?,?,?,?)";
                    PreparedStatement statement =
                        connection.prepareStatement(sql);
                    connection.clearWarnings();
                    connection.setAutoCommit(false);
                    for (int j = 0; j < slno.length; j++) {
                        statement.setInt(1, strExistingId);
                        statement.setInt(2, officeid[j]);
                        statement.setDate(3, date1[j]);
                        statement.setString(4, Remark[j]);

                        statement.executeUpdate();

                    }
                    connection.commit();


                } catch (SQLException e) {
                    System.out.println("Exception in connection:" + e);
                    String msg =
                        "Record With This Given ID Already Exist <br>";
                    msg = "<br><br><p><b>" + msg + "</b></p>";
                    sendMessage(response, msg, "ok");


                    connection.rollback();
                } finally {
                    //connection.close();
                }
                String msg =
                    "Records Has been Moved Successfully To This Office ID.<br>Office ID  is  : " +
                    strExistingId;
                msg = "<br><br><p><b>" + msg + "</b></p>";
                sendMessage(response, msg, "ok");


            } catch (Exception e) {
                System.out.println("Exception in retriving values :" + e);
                sendMessage(response,
                            "Exception in retriving values may be due to " + e,
                            "back");
            }
            connection.close();
        } catch (Exception e) {
            System.out.println("Exception in openeing connection:" + e);
            sendMessage(response,
                        "probably Failed to Establish connection to the database server.. due to " +
                        e, "ok");
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
