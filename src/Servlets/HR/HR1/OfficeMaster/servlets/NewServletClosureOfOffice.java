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

public class NewServletClosureOfOffice extends HttpServlet {
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
        HttpSession session = request.getSession(false);
        try {

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
        String userid = (String)session.getAttribute("UserId");
        System.out.println("session id is:" + userid);
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

                int office_unit = 0;
                try {
                    office_unit =
                            Integer.parseInt(request.getParameter("acc_unit_id"));
                    System.out.println("accounting unit id..." + office_unit);
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

                /*   by arun
                    int office_unit=Integer.parseInt(request.getParameter("acc_unit_id"));
                    System.out.println("accounting unit id..."+office_unit);
                    */
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
                    /*if(strReason.trim().equalsIgnoreCase("a"))
                    {
                       for(int i=0;i<strAttachedId.length;i++) {
                           System.out.println(" office id : " + strAttachedId[i] + " account type : " + strAccountType[i] + " remarks : " + strAttachmentRemarks[i]);
                       }
                    }*/

                    try {
                        String sql =
                            "select * from com_office_closure where CLOSED_OFFICE_ID=?";
                        ps = connection.prepareStatement(sql);
                        ps.setInt(1, intOfficeId);
                        ResultSet results = ps.executeQuery();
                        //System.out.println("inner select");
                        if (results.next()) {
                            //System.out.println("inner results");
                            String sql1 =
                                "update COM_OFFICE_CLOSURE set DATE_CLOSED=?,REMARKS=?,PROCESS_FLOW_STATUS_ID=?,ACCT_TRF_UNIT_ID=? where CLOSED_OFFICE_ID=?";
                            PreparedStatement ps1 =
                                connection.prepareStatement(sql1);
                            ps1.setDate(1, dateOfClosure);
                            ps1.setString(2, strRemarks);
                            ps1.setString(3, "CR");
                            ps1.setInt(4, office_unit);
                            ps1.setInt(5, intOfficeId);
                            ps1.executeUpdate();

                            String sql6 =
                                "update com_office_closure set Date_Closed=?,remarks=?,process_flow_status_id=?,ACCT_TRF_UNIT_ID=? where Closed_office_id=?";
                            PreparedStatement ps6 =
                                connection.prepareStatement(sql6);
                            for (int b = 0; b < conoffid.length; b++) {

                                ps6.setDate(1, dateOfClosure);
                                ps6.setString(2, strRemarks);
                                ps6.setString(3, "CR");
                                ps6.setInt(4, office_unit);
                                ps6.setInt(5, conoffid[b]);
                                ps6.executeUpdate();
                            }
                            sendMessage(response,
                                        "Transaction : Closure of an office with id " +
                                        intOfficeId +
                                        "<br>has been commited successfully.<br>",
                                        "ok");
                        } else {


                            // System.out.println("inner else before sql");
                            // String sqlinsertAttachments="insert into COM_OFFICE_ATTACHED(CLOSED_OFFICE_ID,ATTACHED_OFFICE_ID,OFF_TRANSFER_ACCT_ID,REMARKS,UPDATED_BY_USER_ID,UPDATED_DATE) values (?,?,?,?,?,?)";

                            String sqlinsertClosedOffice =
                                "insert into COM_OFFICE_CLOSURE(CLOSED_OFFICE_ID,DATE_CLOSED,REMARKS,UPDATED_BY_USER_ID,UPDATED_DATE,PROCESS_FLOW_STATUS_ID,ACCT_TRF_UNIT_ID) values (?,?,?,?,?,?,?)";
                            ps =
  connection.prepareStatement(sqlinsertClosedOffice);

                            ps.setInt(1, intOfficeId);
                            ps.setDate(2, dateOfClosure);
                            //ps.setString(3,strReason.trim());
                            ps.setString(3, strRemarks);
                            ps.setString(4, userid);
                            long l = System.currentTimeMillis();
                            Timestamp ts = new Timestamp(l);
                            ps.setTimestamp(5, ts);
                            ps.setString(6, "CR");
                            ps.setInt(7, office_unit);

                            /* String sqlupdateOffices="update com_mst_offices set OFFICE_STATUS_ID=?,STATUS_EFFECTIVE_FROM=? where office_id=?";
                           PreparedStatement ps2=connection.prepareStatement(sqlupdateOffices);
                           ps2.setString(1,"CL");
                           ps2.setDate(2,dateOfClosure);
                           ps2.setInt(3,intOfficeId);*/
                            //System.out.println("inner else before sql");
                            PreparedStatement ps1 = null;
                            String sqlcontrol =
                                "insert into COM_OFFICE_CLOSURE(CLOSED_OFFICE_ID,DATE_CLOSED,REMARKS,UPDATED_BY_USER_ID,UPDATED_DATE,PROCESS_FLOW_STATUS_ID,ACCT_TRF_UNIT_ID) values (?,?,?,?,?,?,?)";
                            ps1 = connection.prepareStatement(sqlcontrol);
                            /*for(int i=0;i<conoffid.length;i++)
                           {

                                ps1.setInt(1,conoffid[i]);
                                ps1.setDate(2,dateOfClosure);
                                ps1.setString(3,strRemarks);
                                ps1.setString(4,"test");
                                ps1.setTimestamp(5,ts);
                                ps1.setString(6,"CR");

                           }*/
                            //System.out.println("inner else after sql");
                            PreparedStatement ps3 = null;
                            /*String sqlcontrolupdate="update com_mst_offices set OFFICE_STATUS_ID=?,STATUS_EFFECTIVE_FROM=? where office_id=?";
                           ps3=connection.prepareStatement(sqlcontrolupdate);
                           for(int j=0;j<conoffid.length;j++)
                           {
                           ps3.setString(1,"CL");
                           ps3.setDate(2,dateOfClosure);
                           ps3.setInt(3,conoffid[j]);

                           }*/
                            connection.setAutoCommit(false);
                            //System.out.println("inner else after update before com_mst");
                            try {
                                ps.executeUpdate();
                                //ps2.executeUpdate();
                                System.out.println("inner else after update after com_mst sqlinsert" +
                                                   conoffid.length);
                                for (int k = 0; k < conoffid.length; k++) {
                                    System.out.println("inner for before update before com_mst sqlinsert");
                                    ps1.setInt(1, conoffid[k]);
                                    ps1.setDate(2, dateOfClosure);
                                    ps1.setString(3, strRemarks);
                                    ps1.setString(4, userid);
                                    ps1.setTimestamp(5, ts);
                                    ps1.setString(6, "CR");
                                    ps1.setInt(7, office_unit);
                                    ps1.executeUpdate();
                                    // ps3.executeUpdate();
                                    System.out.println("inner for after update after com_mst sqlinsert");
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
                        }
                        connection.setAutoCommit(true);

                    } catch (SQLException sqle) {
                        System.out.println("Exception in retriving old values :" +
                                           sqle);
                        sendMessage(response,
                                    "Exception in retriving old values may be due to " +
                                    sqle, "ok");
                    }

                } else {

                    try {
                        System.out.println("in else" + intOfficeId);
                        String sql =
                            "select * from com_office_closure where CLOSED_OFFICE_ID=?";
                        ps = connection.prepareStatement(sql);
                        ps.setInt(1, intOfficeId);
                        ResultSet results = ps.executeQuery();
                        if (results.next()) {
                            String sql1 =
                                "update COM_OFFICE_CLOSURE set DATE_CLOSED=?,REMARKS=?,PROCESS_FLOW_STATUS_ID=?,ACCT_TRF_UNIT_ID=? where CLOSED_OFFICE_ID=?";
                            PreparedStatement ps1 =
                                connection.prepareStatement(sql1);
                            ps1.setDate(1, dateOfClosure);
                            ps1.setString(2, strRemarks);
                            ps1.setString(3, "CR");
                            ps1.setInt(4, office_unit);
                            ps1.setInt(5, intOfficeId);
                            ps1.executeUpdate();
                            sendMessage(response,
                                        "Transaction : Closure of an office with id " +
                                        intOfficeId +
                                        "<br>has been commited successfully.<br>",
                                        "ok");
                        } else {
                            System.out.println("in else inner");
                            //String sqlinsertAttachments="insert into COM_OFFICE_ATTACHED(CLOSED_OFFICE_ID,ATTACHED_OFFICE_ID,OFF_TRANSFER_ACCT_ID,REMARKS,UPDATED_BY_USER_ID,UPDATED_DATE) values (?,?,?,?,?,?)";
                            String sqlinsertClosedOffice =
                                "insert into COM_OFFICE_CLOSURE(CLOSED_OFFICE_ID,DATE_CLOSED,REMARKS,UPDATED_BY_USER_ID,UPDATED_DATE,PROCESS_FLOW_STATUS_ID,ACCT_TRF_UNIT_ID) values (?,?,?,?,?,?,?)";
                            ps =
  connection.prepareStatement(sqlinsertClosedOffice);

                            ps.setInt(1, intOfficeId);
                            ps.setDate(2, dateOfClosure);
                            //ps.setString(3,strReason.trim());
                            ps.setString(3, strRemarks);
                            ps.setString(4, userid);
                            long l = System.currentTimeMillis();
                            Timestamp ts = new Timestamp(l);
                            ps.setTimestamp(5, ts);
                            ps.setString(6, "CR");
                            ps.setInt(7, office_unit);

                            /*String sqlupdateOffices="update com_mst_offices set OFFICE_STATUS_ID=?,STATUS_EFFECTIVE_FROM=? where office_id=?";
                    PreparedStatement ps2=connection.prepareStatement(sqlupdateOffices);
                    ps2.setString(1,"CL");
                    ps2.setDate(2,dateOfClosure);
                    ps2.setInt(3,intOfficeId);*/
                            connection.setAutoCommit(false);
                            try {
                                System.out.println("in try before" +
                                                   intOfficeId);
                                ps.executeUpdate();
                                System.out.println("in try after");
                                sendMessage(response,
                                            "Transaction : Closure of an office with id " +
                                            intOfficeId +
                                            "<br>has been commited successfully.<br>",
                                            "ok");
                                //ps2.executeUpdate();

                            } catch (Exception e) {
                                System.out.println("Exception in Catch Block" +
                                                   e);
                                connection.rollback();
                            }
                            connection.commit();
                            //connection.setAutoCommit(true);


                        }


                    } catch (Exception e) {
                        System.out.println("Exception in Else Part" + e);
                    }


                }

            } catch (Exception e) {
                System.out.println("Exception in retriving values :" +
                                   e.getMessage());
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
