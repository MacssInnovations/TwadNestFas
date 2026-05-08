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

public class ValidateRedeploymentServlet extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";
    static int j = 10;

    public void init(ServletConfig config) throws ServletException {
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
        int closed_office_empid = 0;
        int txtOfficeId = 0, newtxtOfficeId = 0;
        int conoffid[] = null;
        String trigger = null;
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
        String userid = (String)session.getAttribute("UserId");
        System.out.println("session id is:" + userid);
        Connection connection = null;
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps4 = null;
        ResultSet result = null;
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

                System.out.println("Relieval Session: " + rad_Relieval);
                System.out.println("Joining Session: " + rad_Joining);

                strRemarks = request.getParameter("txtRemarks");
                String newofficeid = request.getParameter("txtnewOffice");
                String processflow = request.getParameter("cmbRecordStatus");
                String controlOfficeId[] =
                    request.getParameterValues("officeid");
                System.out.println("control" + controlOfficeId);
                long l = System.currentTimeMillis();
                Timestamp ts = new Timestamp(l);

                java.sql.Date dateOfRedeployment = null;
                System.out.println("before converting Redeployment date");
                String dateString = strDateOfRedeployment;
                SimpleDateFormat dateFormat =
                    new SimpleDateFormat("dd/MM/yyyy");
                java.util.Date d1;
                d1 = dateFormat.parse(dateString.trim());
                dateFormat.applyPattern("yyyy-MM-dd");
                dateString = dateFormat.format(d1);
                dateOfRedeployment = java.sql.Date.valueOf(dateString);
                System.out.println(dateOfRedeployment);

                java.sql.Date dateOfRelieval = null;
                System.out.println("before converting Relieval date");
                String dateString2 = strDateOfRelieval;
                SimpleDateFormat dateFormat2 =
                    new SimpleDateFormat("dd/MM/yyyy");
                java.util.Date d2;
                d2 = dateFormat2.parse(dateString2.trim());
                dateFormat2.applyPattern("yyyy-MM-dd");
                dateString2 = dateFormat2.format(d2);
                dateOfRelieval = java.sql.Date.valueOf(dateString);
                System.out.println(dateOfRelieval);

                java.sql.Date dateOfJoining = null;
                System.out.println("before converting Joining date");
                String dateString3 = strDateOfJoining;
                SimpleDateFormat dateFormat3 =
                    new SimpleDateFormat("dd/MM/yyyy");
                java.util.Date d3;
                d3 = dateFormat3.parse(dateString3.trim());
                dateFormat3.applyPattern("yyyy-MM-dd");
                dateString3 = dateFormat3.format(d3);
                dateOfJoining = java.sql.Date.valueOf(dateString3);
                System.out.println(dateOfJoining);

                int acct_unit_id;
                acct_unit_id = 0;
                try {
                    acct_unit_id =
                            Integer.parseInt(request.getParameter("acc_unit_id"));
                } catch (Exception e) {
                    System.out.println("Warning - acct_unit_id may be NULL ------ " +
                                       e);
                }
                System.out.println("accounting unit id: " + acct_unit_id);

                int newid = 0;
                if (newofficeid != null) {
                    newid = Integer.parseInt(newofficeid);
                }
                System.out.println("newofficeid" + newofficeid);
                //Getting Previous Date for Date Of Redeployment
                String od = strDateOfRedeployment;
                // System.out.println("");
                String[] s = od.split("/");
                System.out.println("s is" + s[0]);
                System.out.println("s is" + s[1]);
                System.out.println("s is" + s[2]);

                java.util.Calendar g =
                    new java.util.GregorianCalendar(Integer.parseInt(s[2]),
                                                    Integer.parseInt(s[1]) - 1,
                                                    Integer.parseInt(s[0]) -
                                                    1);
                java.util.Date d10 = g.getTime();
                java.sql.Date sd = new java.sql.Date(d10.getTime());
                System.out.println(sd);

                //Getting Previous Date for Date Of Relieval
                String od2 = strDateOfRelieval;
                // System.out.println("");
                String[] s2 = od2.split("/");
                System.out.println("s2 is" + s2[0]);
                System.out.println("s2 is" + s2[1]);
                System.out.println("s2 is" + s2[2]);

                java.util.Calendar g2 =
                    new java.util.GregorianCalendar(Integer.parseInt(s2[2]),
                                                    Integer.parseInt(s[1]) - 1,
                                                    Integer.parseInt(s2[0]) -
                                                    1);
                java.util.Date d20 = g2.getTime();
                java.sql.Date sd2 = new java.sql.Date(d20.getTime());
                System.out.println(sd2);

                //Getting Previous Date for Date Of Joining
                String od3 = strDateOfJoining;
                // System.out.println("");
                String[] s3 = od3.split("/");
                System.out.println("s3 is" + s3[0]);
                System.out.println("s3 is" + s3[1]);
                System.out.println("s3 is" + s3[2]);

                java.util.Calendar g3 =
                    new java.util.GregorianCalendar(Integer.parseInt(s3[2]),
                                                    Integer.parseInt(s3[1]) -
                                                    1,
                                                    Integer.parseInt(s3[0]) -
                                                    1);
                java.util.Date d30 = g3.getTime();
                java.sql.Date sd3 = new java.sql.Date(d30.getTime());
                System.out.println(sd3);

                Integer i1 = Integer.parseInt(strOId);
                txtOfficeId = i1.intValue();

                try {
                    String status = "";
                    String levelid = "";
                    String officeaddress1 = "";
                    String officeaddress2 = "";
                    String city = "";
                    int districtcode = 0;
                    int maxslno = 0;
                    int officecadre = 0;
                    java.sql.Date dateeffective = null;

                    connection.setAutoCommit(false);

                    String sql =
                        "select OFFICE_STATUS_ID,DATE_OF_FORMATION,OFFICE_LEVEL_ID,OFFICE_HEAD_CADRE_ID,office_address1,office_address2,city_town_name,district_code from com_mst_offices where office_id=?";
                    ps = connection.prepareStatement(sql);
                    ps.setInt(1, txtOfficeId);
                    ResultSet result1 = ps.executeQuery();
                    if (result1.next()) {
                        status = result1.getString("Office_Status_Id");
                        dateeffective = result1.getDate("Date_of_formation");
                        levelid = result1.getString("OFFICE_LEVEL_ID");
                        officecadre = result1.getInt("OFFICE_HEAD_CADRE_ID");
                        officeaddress1 = result1.getString("office_address1");
                        officeaddress2 = result1.getString("office_address2");
                        city = result1.getString("city_town_name");
                        districtcode = result1.getInt("district_code");
                        System.out.println("office status is:" + status);
                        System.out.println("date of formation:" +
                                           dateeffective);
                        System.out.println("office cadre:" + officecadre);
                    }

                    if (status == null) {
                        status = "CR";
                    } else {

                    }

                    String sql8 =
                        "select max(OFFICE_STATUS_SINO) from com_office_status_log";
                    ps = connection.prepareStatement(sql8);
                    results = ps.executeQuery();
                    if (results.next()) {
                        maxslno = results.getInt(1);
                        maxslno = maxslno + 1;
                    }

                   /* String sqlInsert =
                        "insert into com_office_status_log(OFFICE_ID,OFFICE_STATUS_SINO,OFFICE_STATUS_ID,STATUS_EFFECTIVE_FROM,STATUS_EFFECTIVE_TO,UPDATED_BY_USER_ID) values(?,?,?,?,?,?)";
                    ps2 = connection.prepareStatement(sqlInsert);
                    System.out.println("After office status is:" + status);
                    System.out.println("txtxOffficeid: " + txtOfficeId);
                    System.out.println("maxslno :" + maxslno);
                    System.out.println("dateeffective is:" + dateeffective);
                    System.out.println("sd is:" + sd);
                    System.out.println("userid is:" + userid);
                  
                    ps2.setInt(1, txtOfficeId);
                    ps2.setInt(2, maxslno);
                    ps2.setString(3, status);
                    ps2.setDate(4, dateeffective);
                    ps2.setDate(5, sd);
                    ps2.setString(6, userid);
                    ps2.executeUpdate();*/
                  
                    System.out.println("here is ok1");
                    String sqlInsert =
                            "select employee_id, employee_status_id from hrm_emp_current_posting where office_id=? and employee_status_id=?";
                    ps2 = connection.prepareStatement(sqlInsert);
                    ps2.setInt(1, txtOfficeId);
                    ps2.setString(2, "WKG");
                    results = ps2.executeQuery();

                    if (results.next()) {
                        System.out.println("if------>");
                        closed_office_empid = results.getInt("employee_id");
                    }
                    System.out.println("closed_office_empid" +
                                       closed_office_empid);


                    String sql3 =
                        "update com_mst_offices set Office_status_id=?,STATUS_EFFECTIVE_From=? where office_id=?";
                    ps = connection.prepareStatement(sql3);
                    ps.setString(1, "RD");
                    ps.setDate(2, dateOfRedeployment);
                    ps.setInt(3, txtOfficeId);
                    ps.executeUpdate();
                    ps.close();

                    System.out.println("before");


                    //String sql5="insert into com_mst_offices(OFFICE_NAME,OFFICE_SHORT_NAME,OFFICE_LEVEL_ID,PRIMARY_WORK_ID,Remarks,DATE_OF_FORMATION,OFFICE_HEAD_CADRE_ID,office_address1,office_address2,city_town_name,district_code) values(?,?,?,?,?,?,?,?,?,?,?)";
                    String sql5 =
                        "insert into com_mst_offices(OFFICE_NAME,OFFICE_SHORT_NAME,OFFICE_LEVEL_ID,PRIMARY_WORK_ID,Remarks,DATE_OF_FORMATION,OFFICE_HEAD_CADRE_ID,office_address1,office_address2,city_town_name,district_code,OFFICE_STATUS_ID) values(?,?,?,?,?,?,?,?,?,?,?,?)";
                    //System.out.println("before New Office id is:"+txtnew);
                    ps = connection.prepareStatement(sql5);
                    System.out.println("Office Level  Id is:" + levelid);
                    //ps.setInt(1,txtnew);
                    //System.out.println("After new Office Id is:"+txtnew);

                    ps.setString(1, strNewOfficeName);
                    ps.setString(2, strNewShortName);
                    ps.setString(3, levelid);
                    ps.setString(4, strPrimaryId);
                    ps.setString(5, strRemarks);
                    ps.setDate(6, dateOfRedeployment);
                    ps.setInt(7, officecadre);
                    ps.setString(8, officeaddress1);
                    ps.setString(9, officeaddress2);
                    ps.setString(10, city);
                    ps.setInt(11, districtcode);
                    ps.setString(12, "CR");
                    ps.executeUpdate();
                    ps.close();

                    String sql4 =
                        "select max(office_id) as maxofficeid from com_mst_offices";
                    int txtnewOfficeId = 0;
                    int txtnew = 0;
                    ps = connection.prepareStatement(sql4);
                    result = ps.executeQuery();
                    if (result.next()) {
                        txtnewOfficeId = result.getInt("maxofficeid");
                        txtnew = txtnewOfficeId;
                        System.out.println("new Office Id is:" + txtnew);

                    }


                    String sql6 =
                        "insert into com_office_control(OFFICE_ID,CONTROLLING_OFFICE_ID,DATE_EFFECTIVE_FROM) values(?,?,?)";
                    ps = connection.prepareStatement(sql6);
                    ps.setInt(1, txtnew);
                    ps.setInt(2, newtxtOfficeId);
                    ps.setDate(3, dateOfRedeployment);
                    ps.executeUpdate();
                    ps.close();

                    String sql10 =
                        "insert into com_office_closure(CLOSED_OFFICE_ID,DATE_CLOSED,REMARKS,PROCESS_FLOW_STATUS_ID) values(?,?,?,?)";
                    ps = connection.prepareStatement(sql10);
                    ps.setInt(1, txtOfficeId);
                    ps.setDate(2, dateOfRedeployment);
                    ps.setString(3, strRemarks);
                    ps.setString(4, "FR");
                    ps.executeUpdate();
                    ps.close();

                    String sqlUpdate =
                        "update COM_OFFICE_REDEPLOYMENTS set NEW_OFFICE_NAME=?,NEW_SHORT_NAME=?,NEW_PRIMARY_WORK_ID=?,NEW_CONTROLLING_OFFICE_ID=?,REDEPLOYMENT_DATE=?,REMARKS=?,PROCESS_FLOW_STATUS_ID=?,NEW_OFFICE_ID=?,ACCT_TRF_UNIT_ID=?, EMP_RELIEVAL_DATE=?, EMP_JOIN_DATE=?, EMP_RELIEVAL_SESSION=?, EMP_JOIN_SESSION=? where REDEPLOYED_OFFICE_ID=?";
                    ps = connection.prepareStatement(sqlUpdate);
                    ps.setString(1, strNewOfficeName);
                    ps.setString(2, strNewShortName);
                    ps.setString(3, strPrimaryId);
                    ps.setInt(4, newtxtOfficeId);
                    ps.setDate(5, dateOfRedeployment);
                    ps.setString(6, strRemarks);
                    ps.setString(7, "FR");
                    ps.setInt(8, txtnew);
                    ps.setInt(9, acct_unit_id);
                    ps.setDate(10, dateOfRelieval);
                    ps.setDate(11, dateOfJoining);
                    ps.setString(12, rad_Relieval);
                    ps.setString(13, rad_Joining);
                    ps.setInt(14, txtOfficeId);
                    ps.executeUpdate();
                    ps.close();


                    //Change date on 28-oct-2006//


                    if (controlOfficeId.length>0) {
                        conoffid = new int[controlOfficeId.length];
                        System.out.println("controllength" +
                                           controlOfficeId.length);
                        for (int l1 = 0; l1 < controlOfficeId.length; l1++) {
                            System.out.println("inner for" +
                                               controlOfficeId[l1]);

                            conoffid[l1] =
                                    Integer.parseInt(controlOfficeId[l1]);

                        }
                        try {
                            /*String sql10="update com_mst_offices set Office_status_id=?,STATUS_EFFECTIVE_From=? where office_id=?";
                                    ps=connection.prepareStatement(sql10);
                                    for(int m=0;m<controlOfficeId.length;m++)
                                    {
                                    //System.out.println("control officeid inner update is:"+conoffid[m]);
                                    ps.setString(1,"RD");
                                    ps.setDate(2,dateOfRedeployment);
                                    ps.setInt(3,conoffid[m]);
                                       // System.out.println("control officeid inner update b4 execute is:"+conoffid[m]);
                                    ps.executeUpdate();
                                        //System.out.println("control officeid inner update a4 execute is:"+conoffid[m]);
                                    //ps.close();

                                    String sql11="insert into com_office_closure(CLOSED_OFFICE_ID,DATE_CLOSED,REMARKS,PROCESS_FLOW_STATUS_ID) values(?,?,?,?)";
                                    ps4=connection.prepareStatement(sql11);
                                    //System.out.println("control officeid inner insert is:"+conoffid[m]);
                                    ps4.setInt(1,conoffid[m]);
                                    ps4.setDate(2,dateOfRedeployment);
                                    ps4.setString(3,strRemarks);
                                    ps4.setString(4,"FR");
                                    //System.out.println("control officeid inner insert b4 execute is:"+conoffid[m]);
                                    ps4.executeUpdate();
                                    //System.out.println("control officeid inner insert a4 execute is:"+conoffid[m]);
                                   // ps.close();

                                    } */

                            /*int controlid[]=new int[controlOfficeId.length];
                                     System.out.println("b4 select controlling Office ");

                                     String sql9="select controlling_office_id from com_office_control where office_id=?";
                                     ps=connection.prepareStatement(sql9);
                                     System.out.println("after select");
                                     for(int m=0;m<controlOfficeId.length;m++)
                                     {
                                     System.out.println("inner for conoffid"+conoffid[m]);
                                     ps.setInt(1,conoffid[m]);
                                     ResultSet res=ps.executeQuery();
                                     System.out.println("after query:"+conoffid[m]);
                                        if(res.next())
                                        {
                                            System.out.println("inner if result"+res.getInt("CONTROLLING_OFFICE_ID"));

                                                controlid[m]=res.getInt("CONTROLLING_OFFICE_ID");
                                                System.out.println("control id is:"+controlid[m]);
                                        }
                                         System.out.println("after if res control id is:"+controlid[m]);

                                        String sql11="insert into com_office_control_log(office_id,controlling_office_id,CONTROL_SINO,UPDATED_BY_USER_ID) values(?,?,?,?)";
                                        PreparedStatement ps7=connection.prepareStatement(sql11);
                                        System.out.println("inner insert control log");
                                        ps2.setInt(1,conoffid[m]);
                                        ps2.setInt(2,controlid[m]);
                                        ps2.setInt(3,m);
                                        ps2.setString(4,userid);
                                        ps2.executeUpdate();


                                     }*/


                            String sql12 =
                                "update com_office_control set controlling_office_id=? where office_id=?";
                            PreparedStatement ps6 =
                                connection.prepareStatement(sql12);
                            for (int l2 = 0; l2 < controlOfficeId.length;
                                 l2++) {
                                System.out.println("b4 inner update com_office_control");
                                System.out.println("inner for control id is:" +
                                                   conoffid[l2]);
                                ps6.setInt(1, txtnew);
                                ps6.setInt(2, conoffid[l2]);
                                ps6.executeUpdate();
                                System.out.println("after inner update com_office_control");
                            }

                        } catch (Exception e) {
                            System.out.println("Exception in Control Office:" +
                                               e);
                            connection.rollback();
                        }

                    }

                    connection.commit();
                    System.out.println("before code-------------->");
                    sqlInsert =
                            "select employee_id, employee_status_id from hrm_emp_current_posting where employee_id=? and employee_status_id=?";
                    ps2 = connection.prepareStatement(sqlInsert);
                    ps2.setInt(1, closed_office_empid);
                    ps2.setString(2, "TRT");
                    results = ps2.executeQuery();

                    if (results.next()) {
                        System.out.println("trigger called successfully");
                        trigger = "Trigger called successfully";
                    } else {
                        System.out.println("trigger doesn't called");
                        trigger = "Trigger doesn't called";
                    }


                    String msg =
                        "Redepolyment has been Successfully Validated:Then, New Office is Created:Id is" +
                        txtnew;
                    msg = msg + "<br><br>" + trigger;
                    connection.commit();
                    sendMessage(response, msg, "ok");

                } catch (SQLException sqle) {
                    connection.rollback();
                    System.out.println("Exception in retriving old values :" +
                                       sqle);
                    connection.rollback();
                    sendMessage(response,
                                "Exception in retriving old values may be due to " +
                                sqle, "ok");
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
