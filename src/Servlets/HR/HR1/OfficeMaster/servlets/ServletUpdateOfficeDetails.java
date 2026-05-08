package Servlets.HR.HR1.OfficeMaster.servlets;

import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.text.SimpleDateFormat;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class ServletUpdateOfficeDetails extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        response.setContentType(CONTENT_TYPE);
        Connection connection = null;
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

            String strName = "", strSName = "", strCCAClassId = "", strCOffId =
                "", strHeadedBy = "", strOId = "";
            String strLevel = "", strOCode = "", strPrimaryId =
                "", strDateOfFormation = "", strHRAClassId =
                "", strIsAccountUnit = "", strWingsApplicable =
                "", strRemarks = "";

            int intHeadCode = 0, intCOffId = 0;

            PreparedStatement ps = null;
            try {
                connection.clearWarnings();

                strOId = request.getParameter("txtOffice_Id");
                strName = request.getParameter("txtOffName");
                strSName = request.getParameter("txtShortName");
                strLevel = request.getParameter("cmbLevelId");
                strCOffId = request.getParameter("txtContrllingOfficeID");
                strHeadedBy = request.getParameter("cmbHeadCode");
                strOCode = request.getParameter("txtOCode");
                strPrimaryId = request.getParameter("cmbPrimaryID");
                strDateOfFormation = request.getParameter("txtDOF");
                strHRAClassId = request.getParameter("cmbHRAClassID");
                strCCAClassId = request.getParameter("cmbCCAClassID");
                strIsAccountUnit = request.getParameter("optIAU");
                strWingsApplicable = request.getParameter("optWA");
                strRemarks = request.getParameter("txtRemarks");

                System.out.println("values retrieval was success");

                String sql = "";

                sql =
 "update COM_MST_OFFICES set Office_Name=?,Office_Short_Name=?,OFFICE_HEAD_CADRE_ID=?,";
                sql =
 sql + "Office_Level_Id=?,Office_Old_Code=?,Primary_Work_Id=?,Date_of_Formation=?,HRA_Class_Id=?,CCA_Class_Id=?,Accounting_Unit=?,Wings_Applicable=?,Remarks=?,Updated_Date=?,UPDATED_BY_USER_ID=?";
                sql = sql + " where Office_Id=?";

                ps = connection.prepareStatement(sql);

                ps.setString(1, strName);
                ps.setString(2, strSName);
                try {
                    intHeadCode = Integer.parseInt(strHeadedBy);
                } catch (NumberFormatException num) {
                }

                ps.setInt(3, intHeadCode);
                ps.setString(4, strLevel);
                ps.setString(5, strOCode);
                ps.setString(6, strPrimaryId);

                java.sql.Date dateOfFormation = null;
                System.out.println("before inserting date");
                if (strDateOfFormation != "" && strDateOfFormation != null) {
                    String dateString = strDateOfFormation;
                    SimpleDateFormat dateFormat =
                        new SimpleDateFormat("dd/MM/yyyy");
                    java.util.Date d;
                    try {
                        d = dateFormat.parse(dateString.trim());
                        dateFormat.applyPattern("yyyy-MM-dd");
                        dateString = dateFormat.format(d);
                        dateOfFormation = java.sql.Date.valueOf(dateString);
                    } catch (Exception e) {
                        e.printStackTrace();
                        //sendMessage(response,"Date of formation is not valid.<br>","back");
                    }
                }

                //System.out.println(dateOfFormation);
                ps.setDate(7, dateOfFormation);
                System.out.println("date inserted");

                ps.setString(8, strHRAClassId);
                ps.setString(9, strCCAClassId);
                if (strIsAccountUnit != null) {
                    if (strIsAccountUnit.equals("yes")) {
                        ps.setString(10, "Y");
                    } else {
                        ps.setString(10, "N");
                    }
                }
                if (strWingsApplicable != null) {
                    if (strWingsApplicable.equals("yes")) {
                        ps.setString(11, "Y");
                    } else {
                        ps.setString(11, "N");
                    }
                }

                ps.setString(12, strRemarks);

                long l = System.currentTimeMillis();
                Timestamp ts = new Timestamp(l);
                ps.setTimestamp(13, ts);
                ps.setString(14, "unknown");
                Integer ii = Integer.parseInt(strOId);
                ps.setInt(15, ii.intValue());

                // setting controlling table value

                String sql2 =
                    "update COM_OFFICE_CONTROL set CONTROLLING_OFFICE_ID=?,DATE_EFFECTIVE_FROM=?,REMARKS=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=? where Office_Id=?";
                PreparedStatement ps1 = null;
                System.out.println("query : " + sql2);
                ps1 = connection.prepareStatement(sql2);

                int coid = 0;
                if (strCOffId.equals("default") ||
                    strCOffId.equals("Head Office"))
                    coid = 1;
                else {
                    try {
                        coid = Integer.parseInt(strCOffId);
                    } catch (NumberFormatException nfe) {
                        nfe.printStackTrace();
                        sendMessage(response,
                                    "Invalid controlling Office id.<br>",
                                    "back");
                        System.out.println("invalid controlling office id");
                        return;
                    }
                }
                ps1.setInt(1, coid);
                ps1.setDate(2, dateOfFormation);
                ps1.setString(3, "nothing");
                ps1.setString(4, "unknown");
                ps1.setTimestamp(5, ts);
                ps1.setInt(6, ii.intValue());
                int i1 = ps1.executeUpdate();

                try {
                    connection.setAutoCommit(false);
                    ps.executeUpdate();
                    ps1.executeUpdate();
                    connection.commit();
                    connection.setAutoCommit(true);
                    String msg =
                        "Details Related to the Office with Id : " + ii.intValue();
                    msg = msg + "<br>has been updated successfully.<br>";
                    sendMessage(response, msg, "ok");
                } catch (Exception e) {
                    connection.rollback();
                    System.out.println("transaction :" + e);
                    sendMessage(response,
                                "Failed to Update the database .. due to " + e,
                                "ok");
                }

            } catch (SQLException e) {
                System.out.println("Exception in creating statement:" + e);
                sendMessage(response,
                            "Invalid data entered may be due to " + e, "back");
            }

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
