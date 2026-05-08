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

public class NewRedeploymentOffice extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";
    static int j = 10;

    public void init(ServletConfig config) throws ServletException {
        System.out.println("\nFILE: NewRedeploymentOffice.java\n");
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        response.setContentType(CONTENT_TYPE);

        String strName = "", oldName = "", strOId = "", oldPrimaryId = "";
        String strPrimaryId = "", strDateOfRedeployment =
            "", strDateOfRelieval = "", strDateOfJoining = "", strRemarks =
            "", strNewOfficeName = "", strNewShortName = "";

        int txtOfficeId = 0, newtxtOfficeId = 0;
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
        PreparedStatement ps2 = null;
        ResultSet result = null;
        ResultSet results = null;
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

                strOId = request.getParameter("txtOffice_Id");
                newtxtOfficeId =
                        Integer.parseInt(request.getParameter("txtAttachedOfficeID"));
                strNewOfficeName = request.getParameter("txtNewOfficeName");
                strNewShortName =
                        request.getParameter("txtNewOfficeShortName");
                strPrimaryId = request.getParameter("cmbSecondaryID");

                strDateOfRedeployment =
                        request.getParameter("txtDateOfRedepolyment");
                strDateOfRelieval = request.getParameter("txtDateOfRelieval");
                strDateOfJoining = request.getParameter("txtDateOfJoining");
                String rad_Relieval = request.getParameter("rad_Relieval");
                String rad_Joining = request.getParameter("rad_Joining");

                strRemarks = request.getParameter("txtRemarks");
                String newofficeid = request.getParameter("txtnewOffice");

                int acct_unit_id;
                acct_unit_id = 0;
                try {
                    acct_unit_id =
                            Integer.parseInt(request.getParameter("acc_unit_id"));
                } catch (Exception e) {
                    System.out.println("Warning - acct_unit_id may be NULL ------ " +
                                       e);
                }
                System.out.println("accounting unit id" + acct_unit_id);
                int newid = 0;
                if (newofficeid != null) {
                    newid = Integer.parseInt(newofficeid);
                }
                System.out.println("newofficeid" + newofficeid);
                //Getting Previous Date for Date Of Redeployment
                /*
                     String od=strDateOfRedeployment;
                     // System.out.println("");
                     String [] s=od.split("/");
                     System.out.println("s is"+s[0]);
                     System.out.println("s is"+s[1]);
                     System.out.println("s is"+s[2]);

                     java.util.Calendar g=new java.util.GregorianCalendar(Integer.parseInt(s[2]),Integer.parseInt(s[1])-1,Integer.parseInt(s[0])-1);
                     java.util.Date d1=g.getTime();
                     java.sql.Date sd=new java.sql.Date(d1.getTime());


                     System.out.println(sd);

                    long l=System.currentTimeMillis();
                    Timestamp ts=new Timestamp(l);
                    java.sql.Date dateOfRedeployment=null;
                    System.out.println("before converting date");
                    String dateString = strDateOfRedeployment;
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    java.util.Date d;
                    d = dateFormat.parse(dateString.trim());
                    dateFormat.applyPattern("yyyy-MM-dd");
                    dateString = dateFormat.format(d);
                    dateOfRedeployment = java.sql.Date.valueOf(dateString);

                    System.out.println(dateOfRedeployment);
                   */

                String od = strDateOfRedeployment;
                String od2 = strDateOfRelieval;
                String od3 = strDateOfJoining;

                String[] s1 = od.split("/");
                System.out.println("s1 is" + s1[0]);
                System.out.println("s1 is" + s1[1]);
                System.out.println("s1 is" + s1[2]);

                String[] s2 = od2.split("/");
                System.out.println("s2 is" + s2[0]);
                System.out.println("s2 is" + s2[1]);
                System.out.println("s2 is" + s2[2]);

                String[] s3 = od3.split("/");
                System.out.println("s3 is" + s3[0]);
                System.out.println("s3 is" + s3[1]);
                System.out.println("s3 is" + s3[2]);

                java.util.Calendar g =
                    new java.util.GregorianCalendar(Integer.parseInt(s1[2]),
                                                    Integer.parseInt(s1[1]) -
                                                    1,
                                                    Integer.parseInt(s1[0]) -
                                                    1);
                java.util.Calendar g2 =
                    new java.util.GregorianCalendar(Integer.parseInt(s2[2]),
                                                    Integer.parseInt(s2[1]) -
                                                    1,
                                                    Integer.parseInt(s2[0]) -
                                                    1);
                java.util.Calendar g3 =
                    new java.util.GregorianCalendar(Integer.parseInt(s3[2]),
                                                    Integer.parseInt(s3[1]) -
                                                    1,
                                                    Integer.parseInt(s3[0]) -
                                                    1);

                java.util.Date d1 = g.getTime();
                java.util.Date d2 = g2.getTime();
                java.util.Date d3 = g3.getTime();

                java.sql.Date sd1 = new java.sql.Date(d1.getTime());
                java.sql.Date sd2 = new java.sql.Date(d2.getTime());
                java.sql.Date sd3 = new java.sql.Date(d3.getTime());

                System.out.println(sd1);
                System.out.println(sd2);
                System.out.println(sd3);

                long l = System.currentTimeMillis();
                Timestamp ts = new Timestamp(l);

                java.sql.Date dateOfRedeployment = null;
                java.sql.Date dateOfRelieval = null;
                java.sql.Date dateOfJoining = null;

                System.out.println("before converting date");

                String dateString = strDateOfRedeployment;
                String dateString2 = strDateOfRelieval;
                String dateString3 = strDateOfJoining;

                SimpleDateFormat dateFormat =
                    new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat dateFormat2 =
                    new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat dateFormat3 =
                    new SimpleDateFormat("dd/MM/yyyy");

                java.util.Date d4, d5, d6;
                d4 = dateFormat.parse(dateString.trim());
                d5 = dateFormat2.parse(dateString2.trim());
                d6 = dateFormat3.parse(dateString3.trim());

                dateFormat.applyPattern("yyyy-MM-dd");
                dateFormat2.applyPattern("yyyy-MM-dd");
                dateFormat3.applyPattern("yyyy-MM-dd");

                dateString = dateFormat.format(d4);
                dateString2 = dateFormat.format(d5);
                dateString3 = dateFormat.format(d6);

                dateOfRedeployment = java.sql.Date.valueOf(dateString);
                dateOfRelieval = java.sql.Date.valueOf(dateString2);
                dateOfJoining = java.sql.Date.valueOf(dateString3);

                System.out.println("Date Of Nomen: " + dateOfRedeployment);
                System.out.println("Date Of Relieval: " + dateOfRelieval);
                System.out.println("Date Of Joining: " + dateOfJoining);
                System.out.println("Relieval Session: " + rad_Relieval);
                System.out.println("Joining Session: " + rad_Joining);

                Integer i1 = Integer.parseInt(strOId);
                txtOfficeId = i1.intValue();

                try {
                    String status = "";
                    String levelid = "";
                    int maxslno = 0;
                    int officecadre = 0;
                    java.sql.Date dateeffective = null;

                    System.out.println("\n\nCHECKING.. txtOfficeId: " +
                                       txtOfficeId + "\n\n");
                    connection.setAutoCommit(false);
                    String sql =
                        "select OFFICE_STATUS_ID,DATE_OF_FORMATION,OFFICE_LEVEL_ID,OFFICE_HEAD_CADRE_ID from com_mst_offices where office_id=?";
                    ps = connection.prepareStatement(sql);
                    ps.setInt(1, txtOfficeId);
                    ResultSet result1 = ps.executeQuery();
                    if (result1.next()) {
                        status = result1.getString("Office_Status_Id");
                        dateeffective = result1.getDate("DATE_OF_FORMATION");
                        levelid = result1.getString("OFFICE_LEVEL_ID");
                        officecadre = result1.getInt("OFFICE_HEAD_CADRE_ID");
                        System.out.println("office status is:" + status);
                        System.out.println("date of formation:" +
                                           dateeffective);
                        System.out.println("office cadre:" + officecadre);
                    }

                    if (status == null) {
                        status = "CR";
                    } else {

                    }

                    String sqlInsert =
                        "insert into COM_OFFICE_REDEPLOYMENTS(REDEPLOYED_OFFICE_ID,NEW_OFFICE_ID,NEW_OFFICE_NAME,NEW_PRIMARY_WORK_ID,NEW_CONTROLLING_OFFICE_ID,REDEPLOYMENT_DATE,REMARKS,UPDATED_BY_USER_ID,UPDATED_DATE,PROCESS_FLOW_STATUS_ID,NEW_SHORT_NAME,ACCT_TRF_UNIT_ID,EMP_RELIEVAL_DATE,EMP_JOIN_DATE,EMP_RELIEVAL_SESSION,EMP_JOIN_SESSION) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    ps2 = connection.prepareStatement(sqlInsert);
                    System.out.println("After office status is:" + status);
                    System.out.println("After date of formation:" +
                                       dateeffective);
                    ps2.setInt(1, txtOfficeId);
                    ps2.setInt(2, newid);
                    ps2.setString(3, strNewOfficeName);
                    ps2.setString(4, strPrimaryId);
                    ps2.setInt(5, newtxtOfficeId);
                    ps2.setDate(6, dateOfRedeployment);
                    ps2.setString(7, strRemarks);
                    ps2.setString(8, userid);
                    ps2.setTimestamp(9, ts);
                    ps2.setString(10, "CR");
                    ps2.setString(11, strNewShortName);
                    ps2.setInt(12, acct_unit_id);
                    ps2.setDate(13, dateOfRelieval);
                    ps2.setDate(14, dateOfJoining);
                    ps2.setString(15, rad_Relieval);
                    ps2.setString(16, rad_Joining);

                    ps2.executeUpdate();

                    System.out.println("after update");
                    /*ps=connection.prepareStatement("select office_name,Primary_Work_Id from com_mst_offices where office_id=?");
                        ps.setInt(1,txtOfficeId);
                        result=ps.executeQuery();
                        if(result.next())
                        {
                            oldName=result.getString("office_name");
                            oldPrimaryId=result.getString("Primary_Work_Id");
                            result.close();
                            ps.close();
                            System.out.println("values retrieval was success");
                            ps=connection.prepareStatement("select max(CONVERSION_SINO) as cseq from COM_OFFICE_CONVERSION where office_id=?");
                            ps.setInt(1,txtOfficeId);
                            result=ps.executeQuery();
                            result.next();
                            System.out.println("before getting seq");
                            int cseq=result.getInt("cseq");
                            System.out.println(" values of sequence : " + cseq );
                            String sqlinsert="insert into COM_OFFICE_CONVERSION(OFFICE_ID,CONVERSION_SINO,DATE_CONVERTED,OLD_NAME,OLD_PRIMARY_WORK_NATURE_ID,REMARKS,UPDATED_BY_USER_ID,UPDATED_DATE)values(?,?,?,?,?,?,?,?)";
                            String sqlUpdate="update com_mst_offices set PRIMARY_WORK_ID=?,OFFICE_STATUS_ID=?,STATUS_EFFECTIVE_FROM=? where office_id=?";

                            ps=connection.prepareStatement(sqlinsert);

                            ps.setInt(1,txtOfficeId);
                            ps.setInt(2,cseq+1);
                            ps.setDate(3,dateOfRedeployment);
                            ps.setString(4,oldName);
                            ps.setString(5,oldPrimaryId);
                            ps.setString(6,strRemarks);
                            ps.setString(7,"testing");
                            long l=System.currentTimeMillis();
                            Timestamp ts=new Timestamp(l);
                            ps.setTimestamp(8,ts);

                            PreparedStatement ps1=connection.prepareStatement(sqlUpdate);
                            ps1.setString(1,strPrimaryId);
                            ps1.setString(2,"CN");
                            ps1.setDate(3,dateOfRedeployment);
                            ps1.setInt(4,txtOfficeId);

                            connection.setAutoCommit(false);
                            try
                            {
                                ps.executeUpdate();
                                ps1.executeUpdate();
                                connection.commit();
                                ps1.close();
                                System.out.println("values inserted into database");
                                sendMessage(response,"Transaction : Converting the office with id " + txtOfficeId + "<br>has been commited successfully.<br>","ok");
                            }
                            catch(Exception e)
                            {
                                connection.rollback();
                                System.out.println("Exception while executing batch :"+e);
                                sendMessage(response,"Exception while executing batch may be due to " + e,"ok");
                            }
                            connection.setAutoCommit(true);

                        }
                        else {
                            System.out.println("invalid office id ");
                            sendMessage(response,"invalid office id","back");
                        }*/
                    String msg = "Redepolyment has been Created Successfully:";
                    msg = msg + "<br><br>";
                    sendMessage(response, msg, "ok");

                } catch (SQLException sqle) {
                    connection.rollback();
                    System.out.println("Exception in retriving old values :");
                    sqle.printStackTrace();
                    //sendMessage(response,"Exception in retriving old values may be due to " + sqle,"ok");
                }
                /*finally {
                       connection.setAutoCommit(true);
                       connection.commit();
                   }*/

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
