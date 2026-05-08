package Servlets.HR.HR1.OfficeMaster.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.*;

import java.text.SimpleDateFormat;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver;

public class ValidateNomenClatureChange extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";
    private Connection connection = null;
    private PreparedStatement ps = null;
    private ResultSet res = null;
    private PreparedStatement ps1 = null;
    private PreparedStatement ps2 = null;
    private PreparedStatement ps3 = null;
    private PreparedStatement ps4 = null;
    private PreparedStatement ps5 = null;
    private PreparedStatement ps6 = null;
    private ResultSet result = null;
    private ResultSet results = null;
    private ResultSet results1 = null;
    private ResultSet results2 = null;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);
        try {

            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");

            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }

        //Connection String coding
        //int conoffid[] = null;
         int conoffid = 0;
        int txtOfficeId = 0, newtxtOfficeId = 0, off_id = 0, cont_off = 0;
        String levelid = "";
        int maxslno = 0;
        int officecadre = 0;
        String status = "";
        String officeaddress1 = "";
        String officeaddress2 = "";
        String city = "";
        int pincode = 0;
        int districtcode = 0;
        String stdcode = "";
        String phoneno = "";
        String addphone = "";
        String faxno = "";
        String addfaxno = "";
        String email = "";
        String addemail = "";
        String sqlcontrol = null, trigger = null;
        int closed_office_empid = 0;
        int txtnewOfficeId = 0;
        int txtnew = 1;
        try {
        	 LoadDriver driver=new LoadDriver();
         	connection=driver.getConnection();
        } catch (Exception e) {
            System.out.println("Exception in Connection:" + e);
        }
        String userid = (String)session.getAttribute("UserId");
        System.out.println("session id is:" + userid);

        java.sql.Date dateOfNomen = null;
        java.sql.Date dateOfRelieval = null;
        java.sql.Date dateOfJoining = null;

        String officeid = request.getParameter("txtOffice_Id");
        String officename = request.getParameter("txtNewOfficeName");
        String officeshortname = request.getParameter("txtNewShortName");
        String primaryid = request.getParameter("cmbSecondaryID");

        String dateofnomen = request.getParameter("txtDateOfNomen");
        String dateofrelieval = request.getParameter("txtDateOfRelieval");
        String dateofjoining = request.getParameter("txtDateOfJoining");
        String rad_Relieval = request.getParameter("rad_Relieval");
        String rad_Joining = request.getParameter("rad_Joining");

        //String controlOfficeId[] = request.getParameterValues("txtconOfficeId");
      //   String controlOfficeId = request.getParameter("txtconOfficeId");
        String CntOfficeId = request.getParameter("txtconOfficeId");
           System.out.println("Array Office Id: " + CntOfficeId);
                           

        long l = System.currentTimeMillis();
        Timestamp ts = new Timestamp(l);
        
       // session = request.getSession(false);
      //  String updatedby = (String)session.getAttribute("UserId");
       
        int l1 = 0;int off_new=0;
//        if (controlOfficeId != null) {
//            conoffid = new int[controlOfficeId.length];
//            System.out.println("controllength" + controlOfficeId.length);
//            for (l1 = 0; l1 < controlOfficeId.length; l1++) {
//                System.out.println("inner for" + controlOfficeId[l1]);
//
//                conoffid[l1] = Integer.parseInt(controlOfficeId[l1]);
//
//            }
//        }
    //conoffid = Integer.parseInt(controlOfficeId);
        conoffid = Integer.parseInt(CntOfficeId);
           int office_id = 0;
        if (officeid != "null") {
            office_id = Integer.parseInt(officeid);
        }
        try {

            System.out.println("before converting date");

            String dateString = dateofnomen;
            String dateString2 = dateofrelieval;
            String dateString3 = dateofjoining;

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat dateFormat3 = new SimpleDateFormat("dd/MM/yyyy");

            java.util.Date d;
            java.util.Date d2;
            java.util.Date d3;

            d = dateFormat.parse(dateString.trim());
            d2 = dateFormat2.parse(dateString2.trim());
            d3 = dateFormat3.parse(dateString3.trim());

            dateFormat.applyPattern("yyyy-MM-dd");
            dateFormat2.applyPattern("yyyy-MM-dd");
            dateFormat3.applyPattern("yyyy-MM-dd");

            dateString = dateFormat.format(d);
            dateString2 = dateFormat2.format(d2);
            dateString3 = dateFormat3.format(d3);

            dateOfNomen = java.sql.Date.valueOf(dateString);
            dateOfRelieval = java.sql.Date.valueOf(dateString2);
            dateOfJoining = java.sql.Date.valueOf(dateString3);

            System.out.println("Date Of Nomen: " + dateOfNomen);
            System.out.println("Date Of Relieval: " + dateOfRelieval);
            System.out.println("Date Of Joining: " + dateOfJoining);
            System.out.println("Relieval Session: " + rad_Relieval);
            System.out.println("Joining Session: " + rad_Joining);

        } catch (Exception e) {
            System.out.println("Exception in Date:" + e);
        }
        try {
            try {
                connection.setAutoCommit(false);

                String sql1 ="select OFFICE_STATUS_ID,OFFICE_LEVEL_ID,OFFICE_HEAD_CADRE_ID,office_address1,office_address2,city_town_name,district_code,OFFICE_PIN_CODE,OFFICE_STD_CODE,OFFICE_PHONE_NO,ADDL_PHONE_NOS,OFFICE_FAX_NO,ADDL_FAX_NOS,OFFICE_EMAIL_ID,ADDL_EMAIL_IDS from com_mst_offices where office_id=?";
                ps = connection.prepareStatement(sql1);
                ps.setInt(1, office_id);
                ResultSet result1 = ps.executeQuery();
                if (result1.next()) {
                    status = result1.getString("Office_Status_Id");
                    levelid = result1.getString("OFFICE_LEVEL_ID");
                    officecadre = result1.getInt("OFFICE_HEAD_CADRE_ID");
                    officeaddress1 = result1.getString("OFFICE_ADDRESS1");
                    officeaddress2 = result1.getString("OFFICE_ADDRESS2");
                    city = result1.getString("CITY_TOWN_NAME");
                    pincode = result1.getInt("OFFICE_PIN_CODE");
                    districtcode = result1.getInt("DISTRICT_CODE");
                    stdcode = result1.getString("OFFICE_STD_CODE");
                    phoneno = result1.getString("OFFICE_PHONE_NO");
                    addphone = result1.getString("ADDL_PHONE_NOS");
                    faxno = result1.getString("OFFICE_FAX_NO");
                    addfaxno = result1.getString("ADDL_FAX_NOS");
                    email = result1.getString("OFFICE_EMAIL_ID");
                    addemail = result1.getString("ADDL_EMAIL_IDS");

                    System.out.println("office cadre:" + officecadre);
                }

                if (status == null) {
                    status = "CR";
                } else {

                }
                String sqlupdate = "update com_mst_offices set office_status_id=? where office_id=?";
                ps2 = connection.prepareStatement(sqlupdate);
               ps2.setString(1, "NC");
                ps2.setInt(2, office_id);
                System.out.println(sqlupdate);
                
                ps2.executeUpdate();
                System.out.println("office update over");
                      
                ps2.close();

                long l3 = System.currentTimeMillis();
                Timestamp ts1 = new Timestamp(l3);
//              
                //String sql5="insert into com_mst_offices(OFFICE_NAME,OFFICE_SHORT_NAME,OFFICE_LEVEL_ID,PRIMARY_WORK_ID,DATE_OF_FORMATION,OFFICE_HEAD_CADRE_ID,OFFICE_ADDRESS1,OFFICE_ADDRESS2,CITY_TOWN_NAME,OFFICE_PIN_CODE,DISTRICT_CODE,OFFICE_STD_CODE,OFFICE_PHONE_NO,ADDL_PHONE_NOS,OFFICE_FAX_NO,ADDL_FAX_NOS,OFFICE_EMAIL_ID,ADDL_EMAIL_IDS) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                String sql5 =
                    "insert into com_mst_offices(OFFICE_NAME,OFFICE_SHORT_NAME,OFFICE_LEVEL_ID,PRIMARY_WORK_ID,DATE_OF_FORMATION,OFFICE_HEAD_CADRE_ID,OFFICE_ADDRESS1,OFFICE_ADDRESS2,CITY_TOWN_NAME,OFFICE_PIN_CODE,DISTRICT_CODE,OFFICE_STD_CODE,OFFICE_PHONE_NO,ADDL_PHONE_NOS,OFFICE_FAX_NO,ADDL_FAX_NOS,OFFICE_EMAIL_ID,ADDL_EMAIL_IDS,OFFICE_STATUS_ID,OFFICE_OLD_CODE,UPDATED_DATE,UPDATED_BY_USER_ID) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            System.out.println("before New Office id is:"+txtnew);
                ps3 = connection.prepareStatement(sql5);
               System.out.println("Office Level  Id is:"+levelid);
                
                System.out.println("After new Office Id is:"+txtnew);
                //ps.setInt(1, 0);
               // ps3.setInt(1,off_new);
                ps3.setString(1, officename);
                ps3.setString(2, officeshortname);
                ps3.setString(3, levelid);
                ps3.setString(4, primaryid);
                ps3.setDate(5, dateOfNomen);
                ps3.setInt(6, officecadre);
                ps3.setString(7, officeaddress1);
                ps3.setString(8, officeaddress2);
                ps3.setString(9, city);
                ps3.setInt(10, pincode);
                ps3.setInt(11, districtcode);
                ps3.setString(12, stdcode);
                ps3.setString(13, phoneno);
                ps3.setString(14, addphone);
                ps3.setString(15, faxno);
                ps3.setString(16, addfaxno);
                ps3.setString(17, email);
                ps3.setString(18, addemail);
                ps3.setString(19, "CR");
                ps3.setInt(20, office_id);
                ps3.setTimestamp(21, ts1);
                ps3.setString(22,userid);
                ps3.executeUpdate();
                ps3.close();

//                  String sql4="select max(office_id) as maxofficeid from com_mst_offices";
//           
//            ps=connection.prepareStatement(sql4);
//            result=ps.executeQuery();
//            if(result.next()){
//                txtnewOfficeId=result.getInt("maxofficeid");
//                off_id=txtnewOfficeId+1;
//                System.out.println("new Office Id is:"+off_id);
//
//            }
                System.out.println("after inserting into com_mst_offices");

                String sql7 ="select office_id from com_mst_offices where OFFICE_OLD_CODE=?::varchar";
                ps4 = connection.prepareStatement(sql7);
                ps4.setInt(1, office_id);
                results1 = ps4.executeQuery();

                if (results1.next()) {
                    off_id = results1.getInt("office_id");
                    System.out.println("office id from office table..." +
                                       off_id);
                }
                ps4.close();
                System.out.println("after getting new office id from office table");

                String sql11 = "update COM_OFFICE_NOMENCLATURE set new_office_id=? where office_id=?";
                ps5 = connection.prepareStatement(sql11);

                ps5.setInt(1, off_id);
                ps5.setInt(2, office_id);

                ps5.executeUpdate();
                ps5.close();

                System.out.println("after updating in nomenclature");


                String sql40 =
                    "select new_office_id from COM_OFFICE_NOMENCLATURE where office_id=?";


                ps = connection.prepareStatement(sql40);
                ps.setInt(1, office_id);
                result = ps.executeQuery();
                if (result.next()) {
                    txtnewOfficeId = result.getInt("new_office_id");
                    txtnew = txtnewOfficeId;
                    System.out.println("new Office Id is:" + txtnew);

                }
                System.out.println("after getting new ofice id from nomenclature table");

                String sqlselect1 =
                    "select controlling_office_id from com_office_control where office_id=?";
                ps6 = connection.prepareStatement(sqlselect1);
                ps6.setInt(1, office_id);

                results2 = ps6.executeQuery();

                if (results2.next()) {
                    cont_off = results2.getInt("controlling_office_id");
                }
                System.out.println("old office controlling office id..." +
                                   cont_off);


                String sqlselect =
                    "select controlling_office_id from com_office_control where office_id=?";
                ps1 = connection.prepareStatement(sqlselect);
                //ps1.setInt(1,office_id);  this is done by me
                ps1.setInt(1, txtnew);
                results = ps1.executeQuery();
                if (results.next()) {
                    newtxtOfficeId = results.getInt("controlling_office_id");
                    System.out.println("new controlling office id ----->" +
                                       newtxtOfficeId);
                    String sql6 =
                        "update com_office_control set OFFICE_ID=?,CONTROLLING_OFFICE_ID=?,DATE_EFFECTIVE_FROM=? where OFFICE_ID=?";
                    ps = connection.prepareStatement(sql6);
                    ps.setInt(1, txtnew);
                    ps.setInt(2, newtxtOfficeId);
                    ps.setDate(3, dateOfNomen);
                    ps.setInt(4, txtnew);
                    //ps.setInt(4,office_id);  ///this is done by me
                    int a = ps.executeUpdate();
                    System.out.println("a is:" + a);
                } else {
                    String sql6 =
                        "insert into com_office_control(OFFICE_ID,CONTROLLING_OFFICE_ID,DATE_EFFECTIVE_FROM) values(?,?,?)";
                    // String sql6="update com_office_control set OFFICE_ID=?,CONTROLLING_OFFICE_ID=?,DATE_EFFECTIVE_FROM=? where OFFICE_ID=?";
                    ps = connection.prepareStatement(sql6);
                    ps.setInt(1, txtnew);
                    ps.setInt(2, cont_off);
                    ps.setDate(3, dateOfNomen);
                    ps.executeUpdate();
                }
                ps.close();

                System.out.println("here is ok1");
                sqlcontrol =
                        "select employee_id, employee_status_id from hrm_emp_current_posting where office_id=? and employee_status_id=?";
                ps2 = connection.prepareStatement(sqlcontrol);
                ps2.setInt(1, office_id);
                System.out.println("office id" + office_id);
                ps2.setString(2, "WKG");
                results2 = ps2.executeQuery();

                if (results2.next()) {
                    System.out.println("if------>");
                    closed_office_empid = results2.getInt("employee_id");
                }
                System.out.println("closed_office_empid---------->" +
                                   closed_office_empid);


                /* check null for controllOfficeId  23/10/06 */
               // System.out.println("16/3/2011---------->"+controlOfficeId );
                System.out.println("16/3/2011---------->"+ CntOfficeId );
                     // if (controlOfficeId.length >0) {
              //  if (controlOfficeId != null) {
                        if (CntOfficeId != null) {
                          	System.out.println("16/3/2011------end>" );
                    //int controlid[] = new int[controlOfficeId.length];
               //      int controlid = Integer.parseInt(controlOfficeId);
                        int controlid = Integer.parseInt(CntOfficeId);
                                  System.out.println("b4 select controlling Office "+controlid);

                    String sql9 =
                        "select office_id from com_office_control where controlling_office_id=?";
                    ps = connection.prepareStatement(sql9);
                    System.out.println("after select");
                   /* for (int m = 0; m < controlOfficeId.length; m++) {
                        System.out.println("inner for conoffid" + conoffid[m]);
                        ps.setInt(1, conoffid[m]);
                        ResultSet res = ps.executeQuery();
                        System.out.println("after query:" + conoffid[m]);
                        if (res.next()) {
                            System.out.println("inner if result" +
                                               res.getInt("CONTROLLING_OFFICE_ID"));

                            controlid[m] = res.getInt("CONTROLLING_OFFICE_ID");
                            System.out.println("control id is:" +
                                               controlid[m]);
                        }
                        System.out.println("after if res control id is:" +
                                           controlid[m]);

                        String sql10 =
                            "insert into com_office_control_log(office_id,controlling_office_id,CONTROL_SINO,UPDATED_BY_USER_ID) values(?,?,?,?)";
                        PreparedStatement ps2 =
                            connection.prepareStatement(sql10);
                        System.out.println("inner insert control log");
                        ps2.setInt(1, conoffid[m]);
                        ps2.setInt(2, controlid[m]);
                        ps2.setInt(3, m);
                        ps2.setString(4, userid);
                        ps2.executeUpdate();


                    }
                    //System.out.println("control office length is:"+controlOfficeId.length);
                    String sql8 =
                        "update com_office_control set controlling_office_id=? where office_id=?";
                    PreparedStatement ps4 = connection.prepareStatement(sql8);
                    for (int l2 = 0; l2 < controlOfficeId.length; l2++) {
                        System.out.println("b4 inner update com_office_control");
                        System.out.println("inner for control id is:" +
                                           conoffid[l2]);
                        ps4.setInt(1, txtnew);
                        ps4.setInt(2, conoffid[l2]);
                        ps4.executeUpdate();
                        System.out.println("after inner update com_office_control");
                    }
                }*/
                
                
                
                
                
                
                
                
                
                    //for (int m = 0; m < controlOfficeId.length; m++) {
                        System.out.println("inner for conoffid" + office_id);
                        ps.setInt(1, office_id);
                        ResultSet res = ps.executeQuery();
                    
                        int underoff=0;
                     while (res.next()) {
                            System.out.println("inner if result---->" +
                                               res.getInt("OFFICE_ID"));
                            
                            underoff=res.getInt("OFFICE_ID");
                            String sql6 =
                                "update com_office_control set CONTROLLING_OFFICE_ID=?,DATE_EFFECTIVE_FROM=?, remarks=? where OFFICE_ID=?";
                            ps = connection.prepareStatement(sql6);
                            ps.setInt(1, txtnew);
                            ps.setInt(4, underoff);
                            ps.setDate(2, dateOfNomen);
                            ps.setString(3, "from form");
                            ps.executeUpdate();
                             sql6 =
                                "update HRM_EMP_CONTROLLING_OFFICE set CONTROLLING_OFFICE_ID=? , CONTROLLING_DATE_FROM= ? where CONTROLLING_OFFICE_ID=?";
                            ps = connection.prepareStatement(sql6);
                            ps.setInt(1, txtnew);
                            ps.setInt(3, office_id);
                            ps.setDate(2,dateOfNomen );
                            ps.executeUpdate();
                            System.out.println("controlling office id is:"+txtnew);
                        }
                        System.out.println("after if res control id is:" +
                                           controlid);

                  //      String sql10 =
                  //          "insert into com_office_control_log(office_id,controlling_office_id,CONTROL_SINO,UPDATED_BY_USER_ID) values(?,?,?,?)";
                        String sql10 =
                            "insert into com_office_control_log(office_id,controlling_office_id,UPDATED_BY_USER_ID) values(?,?,?)";
                              PreparedStatement ps2 =
                            connection.prepareStatement(sql10);
                        System.out.println("inner insert control log");
                        ps2.setInt(1, conoffid);
                        ps2.setInt(2, controlid);
                   //     ps2.setInt(3, 0);
                        ps2.setString(3, userid);
                        ps2.executeUpdate();


                   // }
                    //System.out.println("control office length is:"+controlOfficeId.length);
                   /* String sql8 =
                        "update com_office_control set controlling_office_id=? where office_id=?";
                    PreparedStatement ps4 = connection.prepareStatement(sql8);
                    //for (int l2 = 0; l2 < controlOfficeId.length; l2++) {
                        System.out.println("b4 inner update com_office_control");
                        System.out.println("inner for control id is:" +
                                           conoffid);
                        ps4.setInt(1, txtnew);
                        ps4.setInt(2, conoffid);
                        ps4.executeUpdate();
                        System.out.println("after inner update com_office_control");*/
                    //}  */
                    }
              
                
                
                
                
                
                
                


                String sql = "update COM_OFFICE_NOMENCLATURE set new_office_name=?,NEW_SHORT_NAME=?,NEW_PRIMARY_WORK_ID=?,DATE_OF_NOMENCLATURE_CHANGE=?,PROCESS_FLOW_STATUS_ID=?, new_office_id=?, UPDATED_DATE=?, EMP_RELIEVAL_DATE=?, EMP_JOIN_DATE=?, EMP_RELIEVAL_SESSION=?, EMP_JOIN_SESSION=? where office_id=?";
                ps = connection.prepareStatement(sql);

                ps.setString(1, officename);
                ps.setString(2, officeshortname);
                ps.setString(3, primaryid);
                ps.setDate(4, dateOfNomen);
                ps.setString(5, "FR");
                ps.setInt(6, txtnew);
                ps.setTimestamp(7, ts);
                ps.setDate(8, dateOfRelieval);
                ps.setDate(9, dateOfJoining);
                ps.setString(10, rad_Relieval);
                ps.setString(11, rad_Relieval);
                ps.setInt(12, office_id);
                int ii = ps.executeUpdate();
                connection.commit();
                
                CallableStatement callableStatement = null;
		      
				 String sql3 = "call hrm_relieve_empon_off_nc (?,?,?,?,?,?,?)";
        			callableStatement = connection.prepareCall(sql3);          
        			 callableStatement.setInt(1,office_id);
	                   callableStatement.setInt(2,off_id);
	                   callableStatement.setDate(3, dateOfNomen);
	                   callableStatement.setDate(4, dateOfRelieval);
	                   callableStatement.setDate(5, dateOfJoining);
	                   callableStatement.setString(6, rad_Relieval);
	                   callableStatement.setString(7, rad_Joining);
	                   callableStatement.execute();
		                    System.out.println("after execution..........");

		        
		                    
                sqlcontrol =
                           "select employee_id, employee_status_id from hrm_emp_current_posting where  employee_status_id=? and employee_id=? and office_id=?";
                System.out.println(sqlcontrol);
                ps2 = connection.prepareStatement(sqlcontrol);
                ps2.setString(1, "WKG");
                ps2.setInt(2, closed_office_empid);
                ps2.setInt(3, txtnew);
                res = ps2.executeQuery();
                System.out.println(sqlcontrol);
                
                
                
                if (res.next()) {
                    trigger = "Trigger called Successfully ";

                } else {
                    System.out.println("trigger didnt call");
                    trigger = "Trigger doesn't call ";
                }

                String msg =
                    "NomenClature has been Validate Successfully:Then, New Office is Created:Id is" +
                    txtnew;
                msg = msg + "<br><br>" + trigger;
                connection.commit();
                sendMessage(response, msg, "ok");

            } catch (SQLException e) {

                System.out.println("Exception in Insert:678" + e);
                connection.rollback();
                sendMessage(response, "Error due to " + e, "ok");
            }
        } catch (Exception e) {
            System.out.println("Exception in Main try:" + e);
            sendMessage(response, "Error : " + e, "ok");
        }

        out.close();
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
