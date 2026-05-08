package Servlets.HR.HR1.OfficeMaster.servlets;

import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.text.SimpleDateFormat;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class ServletClosureOfOffice extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        response.setContentType(CONTENT_TYPE);

        String strOfficeId = "", strClosureDate = "", strReason =
            "", strRemarks = "";
        String strAttachedId[] = null, strAccountType[] =
            null, strAttachmentRemarks[] = null;

        int intOfficeId = 0;
        int intAttachedIds[] = null;

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

                //txtOffice_Id txtClosureDate cmbReason txtRemarks
                //OfficeID AccountType Remarks
                strOfficeId = request.getParameter("txtOffice_Id");
                try {
                    intOfficeId = Integer.parseInt(strOfficeId.trim());
                } catch (NumberFormatException num) {
                    sendMessage(response,
                                "invalid office id, should be numeric.", "ok");
                    return;
                }
                strClosureDate = request.getParameter("txtClosureDate");
                strReason = request.getParameter("cmbReason");
                strRemarks = request.getParameter("txtRemarks");
                // from grid
                strAttachedId = request.getParameterValues("OfficeID");
                strAccountType = request.getParameterValues("AccountType");
                strAttachmentRemarks = request.getParameterValues("Remarks");

                java.sql.Date dateOfClosure = null;
                System.out.println("before converting date");
                String dateString = strClosureDate;
                SimpleDateFormat dateFormat =
                    new SimpleDateFormat("dd/MM/yyyy");
                java.util.Date d;
                d = dateFormat.parse(dateString.trim());
                dateFormat.applyPattern("yyyy-MM-dd");
                dateString = dateFormat.format(d);
                dateOfClosure = java.sql.Date.valueOf(dateString);

                System.out.println(" closure date : " + dateOfClosure);
                System.out.println("office id: " + strOfficeId);
                System.out.println("reason : " + strReason);
                System.out.println("remarks : " + strRemarks);
                if (strReason.trim().equalsIgnoreCase("a")) {
                    for (int i = 0; i < strAttachedId.length; i++) {
                        System.out.println(" office id : " + strAttachedId[i] +
                                           " account type : " +
                                           strAccountType[i] + " remarks : " +
                                           strAttachmentRemarks[i]);
                    }
                }

                try {

                    String sqlinsertClosedOffice =
                        "insert into COM_OFFICE_CLOSURE(CLOSED_OFFICE_ID,DATE_CLOSED,OFF_CLOSURE_REASON_ID,REMARKS,UPDATED_BY_USER_ID,UPDATED_DATE) values (?,?,?,?,?,?)";

                    String sqlinsertAttachments =
                        "insert into COM_OFFICE_ATTACHED(CLOSED_OFFICE_ID,ATTACHED_OFFICE_ID,OFF_TRANSFER_ACCT_ID,REMARKS,UPDATED_BY_USER_ID,UPDATED_DATE) values (?,?,?,?,?,?)";
                    String sqlupdateOffices =
                        "update com_mst_offices set OFFICE_STATUS_ID=?,STATUS_EFFECTIVE_FROM=? where office_id=?";

                    ps = connection.prepareStatement(sqlinsertClosedOffice);

                    ps.setInt(1, intOfficeId);
                    ps.setDate(2, dateOfClosure);
                    ps.setString(3, strReason.trim());
                    ps.setString(4, strRemarks);
                    ps.setString(5, "testing");
                    long l = System.currentTimeMillis();
                    Timestamp ts = new Timestamp(l);
                    ps.setTimestamp(6, ts);

                    PreparedStatement ps1 = null;
                    PreparedStatement ps2 =
                        connection.prepareStatement(sqlupdateOffices);
                    ps2.setString(1, "CL");
                    ps2.setDate(2, dateOfClosure);
                    ps2.setInt(3, intOfficeId);

                    connection.setAutoCommit(false);
                    try {
                        ps.executeUpdate();
                        ps2.executeUpdate();
                        if (strReason.trim().equalsIgnoreCase("a")) {
                            ps1 =
 connection.prepareStatement(sqlinsertAttachments);
                            intAttachedIds = new int[strAttachedId.length];
                            int i = 0;
                            for (i = 0; i < strAttachedId.length; i++) {
                                try {
                                    intAttachedIds[i] =
                                            Integer.parseInt(strAttachedId[i]);
                                } catch (NumberFormatException num) {
                                    sendMessage(response,
                                                "invalid Attached office id, should be numeric.",
                                                "ok");
                                    return;
                                }
                            }
                            for (i = 0; i < strAttachedId.length; i++) {
                                ps1.setInt(1, intOfficeId);
                                ps1.setInt(2, intAttachedIds[i]);
                                ps1.setString(3, strAccountType[i].trim());
                                ps1.setString(4, strAttachmentRemarks[i]);
                                ps1.setString(5, "unknown");
                                ps1.setTimestamp(6, ts);
                                ps1.executeUpdate();
                            }
                            ps1.close();
                        }
                        connection.commit();
                        ps.close();
                        System.out.println("values inserted into database");
                        sendMessage(response,
                                    "Transaction : Closure of an office with id " +
                                    intOfficeId +
                                    "<br>has been commited successfully.<br>",
                                    "ok");
                    } catch (Exception e) {
                        connection.rollback();
                        System.out.println("Exception while executing batch :" +
                                           e);
                        sendMessage(response,
                                    "Exception while executing batch may be due to " +
                                    e, "ok");
                    }
                    connection.setAutoCommit(true);

                } catch (SQLException sqle) {
                    System.out.println("Exception in retriving old values :" +
                                       sqle);
                    sendMessage(response,
                                "Exception in retriving old values may be due to " +
                                sqle, "ok");
                }


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
