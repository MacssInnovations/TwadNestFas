package Servlets.HR.HR1.OfficeMaster.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class EditServletRedeploymentDetails extends HttpServlet {
    private static final String CONTENT_TYPE = "text/xml";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
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
        System.out.println("Edit Redeployment");
        boolean ErrorOccured = false;
        String ErrorMessage = "";

        int IntID = 0;
        try {
            IntID = Integer.parseInt(request.getParameter("OfficeId"));
        } catch (NumberFormatException nfe) {
            ErrorMessage = "Invalid Office ID(Should be Numeric)";
        }

        if (!ErrorOccured) {

            Connection connection = null;
            PreparedStatement ps = null;
            ResultSet results = null;

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
                    connection.clearWarnings();
                    //String sql="select CONTROLLING_OFFICE_ID,DATE_EFFECTIVE_FROm from com_office_control where OFFICE_ID=?";
                    //String sql="select com_office_control.controlling_office_id,date_effective_from,com_mst_offices.PRIMARY_WORK_ID from com_office_control,com_mst_offices where com_mst_offices.office_id=com_office_control.office_id and com_mst_offices.office_id=?";
                    String sql =
                        "select NEW_OFFICE_NAME,NEW_SHORT_NAME,NEW_PRIMARY_WORK_ID,NEW_CONTROLLING_OFFICE_ID,REDEPLOYMENT_DATE,EMP_RELIEVAL_DATE,EMP_JOIN_DATE,EMP_RELIEVAL_SESSION,EMP_JOIN_SESSION,REMARKS,PROCESS_FLOW_STATUS_ID,ACCT_TRF_UNIT_ID from COM_OFFICE_REDEPLOYMENTS where REDEPLOYED_OFFICE_ID=?";
                    ps = connection.prepareStatement(sql);
                    ps.setInt(1, IntID);
                    results = ps.executeQuery();
                    if (results.next()) {
                        java.sql.Date DateOfFormation =
                            results.getDate("REDEPLOYMENT_DATE");
                        String DateToBeDisplayed = "";
                        if (DateOfFormation == null) {
                            DateToBeDisplayed = "null";
                        } else {
                            try {
                                java.text.SimpleDateFormat sdf =
                                    new java.text.SimpleDateFormat("dd/MM/yyyy");
                                DateToBeDisplayed =
                                        sdf.format(DateOfFormation);
                            } catch (Exception e) {
                                System.out.println("error while formatting date : " +
                                                   e);
                                DateToBeDisplayed = "Not Specified";
                            }
                        }


                        java.sql.Date DateOfRelieval =
                            results.getDate("EMP_RELIEVAL_DATE");
                        String RelievalDate = "";
                        if (DateOfRelieval == null) {
                            RelievalDate = "null";
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


                        java.sql.Date DateOfJoining =
                            results.getDate("EMP_JOIN_DATE");
                        String JoiningDate = "";
                        if (DateOfJoining == null) {
                            JoiningDate = "null";
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
                        System.out.println("\n\nCHECKING..1\n\n");
                        String xml =
                            "<response><flag>success</flag><newname>" +
                            results.getString("NEW_OFFICE_NAME") +
                            "</newname><shortname>" +
                            results.getString("NEW_SHORT_NAME") +
                            "</shortname><dateRedeploy>" + DateToBeDisplayed +
                            "</dateRedeploy><dateRelieval>" + RelievalDate +
                            "</dateRelieval><dateJoining>" + JoiningDate +
                            "</dateJoining><relievalSession>" +
                            results.getString("EMP_RELIEVAL_SESSION") +
                            "</relievalSession><joinSession>" +
                            results.getString("EMP_JOIN_SESSION") +
                            "</joinSession><primaryid>" +
                            results.getString("NEW_PRIMARY_WORK_ID") +
                            "</primaryid><newcontrolid>" +
                            results.getInt("NEW_CONTROLLING_OFFICE_ID") +
                            "</newcontrolid><remarks>" +
                            results.getString("Remarks") +
                            "</remarks><recordstatus>" +
                            results.getString("PROCESS_FLOW_STATUS_ID") +
                            "</recordstatus><acct_unit_id>" +
                            results.getInt("ACCT_TRF_UNIT_ID") +
                            "</acct_unit_id></response>";

                        System.out.println("\n\nCHECKING..2\n\n");

                        response.setContentType(CONTENT_TYPE);
                        response.setHeader("cache-control", "no-cache");
                        PrintWriter out = response.getWriter();
                        out.write(xml);
                        System.out.println("CHECKING.. xml is : " + xml);
                        out.close();
                        results.close();
                        ps.close();
                        return;
                    } else {
                        ErrorOccured = true;
                        ErrorMessage = "Invalid ID,Record not found.";
                    }
                    results.close();
                    ps.close();
                    //connection.close();

                } catch (SQLException e) {
                    System.out.println("Exception in creating statement:" + e);
                    ErrorOccured = true;
                    ErrorMessage = e.getMessage();
                }


            } catch (Exception e) {
                System.out.println("Exception in openeing connection:" + e);
                ErrorOccured = true;
                ErrorMessage = e.getMessage();
            }

        }
        String xml =
            "<response><flag>failed</flag><message>" + ErrorMessage + "</message></response>";
        response.setContentType(CONTENT_TYPE);
        response.setHeader("cache-control", "no-cache");
        PrintWriter out = response.getWriter();
        out.write(xml);
        System.out.println("xml is : " + xml);
        out.close();
    }
}
