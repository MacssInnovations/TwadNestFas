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

public class ServletConvertOffice extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        response.setContentType(CONTENT_TYPE);

        String strName = "", oldName = "", strOId = "", oldPrimaryId = "";
        String strPrimaryId = "", strDateOfConversion = "", strRemarks = "";

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

                strOId = request.getParameter("txtOffice_Id");
                strName = request.getParameter("txtNewOfficeName");
                strPrimaryId = request.getParameter("cmbPrimaryID");
                strDateOfConversion = request.getParameter("txtDOC");

                java.sql.Date dateOfConversion = null;
                System.out.println("before converting date");
                String dateString = strDateOfConversion;
                SimpleDateFormat dateFormat =
                    new SimpleDateFormat("dd/MM/yyyy");
                java.util.Date d;
                d = dateFormat.parse(dateString.trim());
                dateFormat.applyPattern("yyyy-MM-dd");
                dateString = dateFormat.format(d);
                dateOfConversion = java.sql.Date.valueOf(dateString);

                System.out.println(dateOfConversion);

                strRemarks = request.getParameter("txtRemarks");
                Integer i1 = Integer.parseInt(strOId);
                intOfficeId = i1.intValue();

                try {
                    ps =
  connection.prepareStatement("select office_name,Primary_Work_Id from com_mst_offices where office_id=?");
                    ps.setInt(1, intOfficeId);
                    ResultSet result = ps.executeQuery();
                    if (result.next()) {
                        oldName = result.getString("office_name");
                        oldPrimaryId = result.getString("Primary_Work_Id");
                        result.close();
                        ps.close();
                        System.out.println("values retrieval was success");
                        ps =
  connection.prepareStatement("select max(CONVERSION_SINO) as cseq from COM_OFFICE_CONVERSION where office_id=?");
                        ps.setInt(1, intOfficeId);
                        result = ps.executeQuery();
                        result.next();
                        System.out.println("before getting seq");
                        int cseq = result.getInt("cseq");
                        System.out.println(" values of sequence : " + cseq);
                        String sqlinsert =
                            "insert into COM_OFFICE_CONVERSION(OFFICE_ID,CONVERSION_SINO,DATE_CONVERTED,OLD_NAME,OLD_PRIMARY_WORK_NATURE_ID,REMARKS,UPDATED_BY_USER_ID,UPDATED_DATE)values(?,?,?,?,?,?,?,?)";
                        String sqlUpdate =
                            "update com_mst_offices set PRIMARY_WORK_ID=?,OFFICE_STATUS_ID=?,STATUS_EFFECTIVE_FROM=? where office_id=?";

                        ps = connection.prepareStatement(sqlinsert);

                        ps.setInt(1, intOfficeId);
                        ps.setInt(2, cseq + 1);
                        ps.setDate(3, dateOfConversion);
                        ps.setString(4, oldName);
                        ps.setString(5, oldPrimaryId);
                        ps.setString(6, strRemarks);
                        ps.setString(7, "testing");
                        long l = System.currentTimeMillis();
                        Timestamp ts = new Timestamp(l);
                        ps.setTimestamp(8, ts);

                        PreparedStatement ps1 =
                            connection.prepareStatement(sqlUpdate);
                        ps1.setString(1, strPrimaryId);
                        ps1.setString(2, "CN");
                        ps1.setDate(3, dateOfConversion);
                        ps1.setInt(4, intOfficeId);

                        connection.setAutoCommit(false);
                        try {
                            ps.executeUpdate();
                            ps1.executeUpdate();
                            connection.commit();
                            ps1.close();
                            System.out.println("values inserted into database");
                            sendMessage(response,
                                        "Transaction : Converting the office with id " +
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

                    } else {
                        System.out.println("invalid office id ");
                        sendMessage(response, "invalid office id", "back");
                    }

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
