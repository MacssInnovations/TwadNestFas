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

public class EditServletClosureOfOffice extends HttpServlet {
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
        int conoffid[] = null;

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

                int acct_unit_id = 0;
                try {
                    acct_unit_id =
                            Integer.parseInt(request.getParameter("acc_unit_id"));
                    System.out.println("accounting unit id" + acct_unit_id);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }


                strClosureDate = request.getParameter("txtClosureDate");
                //strReason=request.getParameter("cmbReason");
                strRemarks = request.getParameter("txtRemarks");
                System.out.println("servlet inner");
                String controlOfficeId[] =
                    request.getParameterValues("officeid");
                System.out.println("control" + controlOfficeId);
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
                //System.out.println("reason : " + strReason);
                System.out.println("remarks : " + strRemarks);


                if (controlOfficeId != null) {
                    conoffid = new int[controlOfficeId.length];
                    System.out.println("controllength" +
                                       controlOfficeId.length);
                    for (int l = 0; l < controlOfficeId.length; l++) {
                        System.out.println("inner for" + controlOfficeId[l]);

                        conoffid[l] = Integer.parseInt(controlOfficeId[l]);

                    }

                    System.out.println("after");
                    System.out.println(" closure date : " + dateOfClosure);
                    System.out.println("office id: " + strOfficeId);
                    //System.out.println("reason : " + strReason);
                    System.out.println("remarks : " + strRemarks);


                    try {
                        String sql1 =
                            "update COM_OFFICE_CLOSURE set DATE_CLOSED=?,REMARKS=?,PROCESS_FLOW_STATUS_ID=?,ACCT_TRF_UNIT_ID=? where CLOSED_OFFICE_ID=?";
                        PreparedStatement ps1 =
                            connection.prepareStatement(sql1);
                        ps1.setDate(1, dateOfClosure);
                        ps1.setString(2, strRemarks);
                        ps1.setString(3, "MD");
                        ps1.setInt(4, acct_unit_id);
                        ps1.setInt(5, intOfficeId);
                        ps1.executeUpdate();


                        String sqlcontrol =
                            "update COM_OFFICE_CLOSURE set DATE_CLOSED=?,REMARKS=?,PROCESS_FLOW_STATUS_ID=?,ACCT_TRF_UNIT_ID=? where CLOSED_OFFICE_ID=?";
                        PreparedStatement ps2 =
                            connection.prepareStatement(sqlcontrol);
                        for (int i = 0; i < conoffid.length; i++) {

                            ps2.setDate(1, dateOfClosure);
                            ps2.setString(2, strRemarks);
                            ps2.setString(3, "MD");
                            ps2.setInt(4, acct_unit_id);
                            ps2.setInt(5, conoffid[i]);
                            ps2.executeUpdate();

                        }
                        sendMessage(response,
                                    "Transaction : Closure of an office with id " +
                                    intOfficeId +
                                    "<br>has been successfully Updated.<br>",
                                    "ok");
                        /* String sqlcontrolupdate="update com_mst_offices set OFFICE_STATUS_ID=?,STATUS_EFFECTIVE_FROM=? where office_id=?";
                                          ps3=connection.prepareStatement(sqlcontrolupdate);
                                          for(int j=0;j<conoffid.length;j++)
                                          {
                                          ps3.setString(1,"CL");
                                          ps3.setDate(2,dateOfClosure);
                                          ps3.setInt(3,conoffid[j]);
                                          connection.setAutoCommit(false);
                                          }*/


                    } catch (SQLException sqle) {
                        System.out.println("Exception in retriving old values :" +
                                           sqle);
                        sendMessage(response,
                                    "Exception in retriving old values may be due to " +
                                    sqle, "ok");
                    }

                } else {

                    String sql =
                        "update COM_OFFICE_CLOSURE set DATE_CLOSED=?,REMARKS=?,PROCESS_FLOW_STATUS_ID=?,ACCT_TRF_UNIT_ID=? where CLOSED_OFFICE_ID=?";
                    PreparedStatement ps3 = connection.prepareStatement(sql);
                    ps3.setDate(1, dateOfClosure);
                    ps3.setString(2, strRemarks);
                    ps3.setString(3, "MD");
                    ps3.setInt(4, acct_unit_id);
                    ps3.setInt(5, intOfficeId);
                    ps3.executeUpdate();
                    sendMessage(response,
                                "Transaction : Closure of an office with id " +
                                intOfficeId +
                                "<br>has been successfully Updated.<br>",
                                "ok");

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
