package Servlets.HR.HR1.OfficeMaster.servlets;

import java.sql.Date;

import java.text.SimpleDateFormat;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.PrintWriter;
import java.io.IOException;

import java.util.StringTokenizer;

import java.sql.*;

import java.util.*;

import Servlets.Security.classes.*;

public class NewServletOffices extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    private Connection connection = null;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        /// opening connection to the database
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter pw = response.getWriter();
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

            String strName = "", strSName = "", strHeadCode = "", strAdd1 =
                "", strAdd2 = "", strAdd3 = "", strPincode = "", strDistrict =
                "", strStd = "", phone = "", strAPhone = "", strFax =
                "", strAFax = "", strEMail = "", strAEMail = "";
            String strLevel = "", strCOffId = "", strHCode = "", strOCode =
                "", strPrimaryId = "", strDateOfFormation = "", strHRAClassId =
                "", strCCAClassId = "", strIsAccountUnit =
                "", strWingsApplicable = "", strRemarks =
                "", strDateEffectiveFrom = "";
            //String strSecondaryId[]=null;
            String userid = (String)session.getAttribute("UserId");
            System.out.println("session id is:" + userid);
            try {
                strName = request.getParameter("txtOffName");
                strSName = request.getParameter("txtShortName");
                strHeadCode = request.getParameter("cmbHeadCode");
                strAdd1 = request.getParameter("txtAdd1");
                strAdd2 = request.getParameter("txtAdd2");
                strAdd3 = request.getParameter("txtAdd3");
                strPincode = request.getParameter("txtPinCode");
                strDistrict = request.getParameter("cmbDistrict");
                strStd = request.getParameter("txtSTDCode");

                phone = request.getParameter("txtPhoneNo");
                strAPhone = request.getParameter("txtAddPhoneNo");
                strFax = request.getParameter("txtFAXNo");
                strAFax = request.getParameter("txtAddFAXNo");
                strEMail = request.getParameter("txtEMail");
                strAEMail = request.getParameter("txtAddEMail");
                strLevel = request.getParameter("cmbLevelId");
                strCOffId = request.getParameter("txtHContrllingOfficeID");

                //strOCode = request.getParameter("txtOCode");
                strPrimaryId = request.getParameter("cmbPrimaryID");
                // strSecondaryId = request.getParameterValues("cmbSecondaryID");
                strDateOfFormation = request.getParameter("txtDOF");
                //strHRAClassId = request.getParameter("cmbHRAClassID");
                //strCCAClassId = request.getParameter("cmbCCAClassID");
                //strIsAccountUnit = request.getParameter("optIAU");
                //strWingsApplicable = request.getParameter("optWA");
                strRemarks = request.getParameter("txtRemarks");
                strDateEffectiveFrom =
                        request.getParameter("txtDate_Effective_From");
                System.out.println("values retrived sucessfully");
            } catch (Exception e) {
                e.printStackTrace();
                sendMessage(response,
                            "Please Fill in all the required fields. " + e,
                            "back");
                return;
            }

            // update the database
            try {
                String sql1 = "";
                sql1 =
"insert into COM_MST_OFFICES_tmp(Office_Name,Office_Short_Name,OFFICE_HEAD_CADRE_ID,Office_Address1,Office_Address2,CITY_TOWN_NAME,Office_Pin_Code,District_Code,Office_STD_Code,Office_Phone_no,";
                sql1 =
sql1 + "Addl_Phone_Nos,Office_Fax_No,Addl_Fax_Nos,Office_Email_Id,Addl_Email_Ids,Office_Level_Id,Primary_Work_Id,Date_of_Formation,";
                sql1 =
sql1 + "Remarks,UPDATED_DATE,UPDATED_BY_USER_ID,OFFICE_STATUS_ID,STATUS_EFFECTIVE_FROM,PROCESS_FLOW_STATUS_ID) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                String sql2 =
                    "insert into COM_OFFICE_CONTROL_tmp(OFFICE_ID,CONTROLLING_OFFICE_ID,DATE_EFFECTIVE_FROM,UPDATED_BY_USER_ID,UPDATED_DATE) values (?,?,?,?,?)";
                //String sql3="insert into COM_OFFICE_ADDL_NATURE(OFFICE_ID,WORK_NATURE_ID,DATE_EFFECTIVE_FROM,REMARKS,UPDATED_BY_USER_ID,UPDATED_DATE) values (?,?,?,?,?,?)";

                System.out.println("query : " + sql1);
                PreparedStatement ps1 = null;
                ps1 = connection.prepareStatement(sql1);

                ///********* setting values for table 1

                //System.out.println("name : " + strName);
                ps1.setString(1, strName);
                //System.out.println("sname : " + strSName);
                ps1.setString(2, strSName);
                int cadreid = 0, pin = 0, discode = 0, intStd = 0;
                try {
                    cadreid = Integer.parseInt(strHeadCode);
                } catch (NumberFormatException num) {
                    System.out.println("Number format exception : " + num);
                }
                try {
                    pin = Integer.parseInt(strPincode);
                } catch (NumberFormatException num) {
                    System.out.println("Number format exception : " + num);
                }
                try {
                    discode = Integer.parseInt(strDistrict);
                } catch (NumberFormatException num) {
                    System.out.println("Number format exception : " + num);
                }

                //System.out.println("cadre : " + cadreid);
                ps1.setInt(3, cadreid);
                //System.out.println("add1 : " + strAdd1);
                ps1.setString(4, strAdd1);
                //System.out.println("add2 : " + strAdd2);
                ps1.setString(5, strAdd2);
                //System.out.println("add2 : " + strAdd3);
                ps1.setString(6, strAdd3);
                //System.out.println("pincode : " + pin);
                ps1.setInt(7, pin);
                //System.out.println("district : " + discode);
                ps1.setInt(8, discode);
                //System.out.println("stdcode : " + intStd);
                ps1.setInt(9, intStd);

                ps1.setString(10, phone);
                ps1.setString(11, strAPhone);
                ps1.setString(12, strFax);
                ps1.setString(13, strAFax);
                ps1.setString(14, strEMail);
                ps1.setString(15, strAEMail);
                ps1.setString(16, strLevel);
                //ps1.setString(17,strOCode);
                ps1.setString(17, strPrimaryId);

                java.sql.Date dateOfFormation = null;
                java.sql.Date dateOfFormation1 = null;
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

                if (strDateEffectiveFrom != "" &&
                    strDateEffectiveFrom != null) {
                    String dateString1 = strDateEffectiveFrom;
                    SimpleDateFormat dateFormat =
                        new SimpleDateFormat("dd/MM/yyyy");
                    java.util.Date d;
                    try {
                        d = dateFormat.parse(dateString1.trim());
                        dateFormat.applyPattern("yyyy-MM-dd");
                        dateString1 = dateFormat.format(d);
                        dateOfFormation1 = java.sql.Date.valueOf(dateString1);
                    } catch (Exception e) {
                        e.printStackTrace();
                        //sendMessage(response,"Date of formation is not valid.<br>","back");
                    }
                }


                //System.out.println(dateOfFormation);
                ps1.setDate(18, dateOfFormation);
                System.out.println("date inserted");

                //ps1.setString(20,strHRAClassId);
                //ps1.setString(21,strCCAClassId);
                /*if(strIsAccountUnit!=null)
              {
                if(strIsAccountUnit.equals("yes"))
                {
                  ps1.setString(22,"Y");
                }
                else
                {
                  ps1.setString(22,"N");
                }
              }
              else
              {
                ps1.setString(22,"N");
              }
              if(strWingsApplicable!=null)
              {
                if(strWingsApplicable.equals("yes"))
                {
                  ps1.setString(23,"Y");
                }
                else
                {
                  ps1.setString(23,"N");
                }
              }
              else
              {
                ps1.setString(23,"N");
              }*/

                ps1.setString(19, strRemarks);

                long l = System.currentTimeMillis();
                Timestamp ts = new Timestamp(l);
                ps1.setTimestamp(20, ts);
                System.out.println("executijng  1");
                ps1.setString(21, userid); // user id
                ps1.setString(22, "CR");
                java.sql.Date sdate =
                    new java.sql.Date(System.currentTimeMillis());
                ps1.setDate(23, dateOfFormation1);
                ps1.setString(24, "CR");
                // ******** setting values for table 2
                int office_id = 0;
                try {
                    connection.setAutoCommit(false);
                    int i = ps1.executeUpdate();
                    System.out.println("executijng  1");
                    ps1.close();
                    if (i >= 1) {
                        String query =
                            "select Office_Id from COM_MST_OFFICES_tmp where UPDATED_DATE=?";
                        try {
                            ps1 = connection.prepareStatement(query);
                            ps1.setTimestamp(1, ts);
                            ResultSet rss = ps1.executeQuery();
                            if (rss.next()) {
                                office_id = rss.getInt("Office_Id");
                            }
                            rss.close();
                        } catch (Exception e) {
                        }
                        System.out.println("query : " + sql2);
                        ps1 = connection.prepareStatement(sql2);
                        ps1.setInt(1, office_id);

                        int coid = 0;
                        System.out.println("contrilloing office : " +
                                           strCOffId);
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
                        ps1.setInt(2, coid);
                        ps1.setDate(3, dateOfFormation);
                        // ps1.setString(4,"nothing");
                        ps1.setString(4, userid);
                        ps1.setDate(5, dateOfFormation1);
                        int ii = ps1.executeUpdate();
                        System.out.println("executijng   2");
                        if (ii >= 1) {
                            ps1.close();
                            connection.commit();
                            connection.setAutoCommit(true);
                            String msg =
                                "Office has been created successfully.<br>Temporary Office ID  is  : " +
                                office_id;
                            msg = msg + "<br><br>";
                            sendMessage(response, msg, "ok");
                        } else {
                            // record not inserted
                            sendMessage(response,
                                        "Failed to insert values due to unknown reason",
                                        "back");
                            connection.rollback();
                            connection.setAutoCommit(true);
                            return;
                        }

                    } else {
                        // record not inserted
                        sendMessage(response,
                                    "Failed to insert values due to unknown reason",
                                    "back");
                        connection.setAutoCommit(true);
                        connection.rollback();
                        return;
                    }
                } catch (Exception e) {
                    // error while inserting do rolling back
                    connection.setAutoCommit(true);
                    connection.rollback();
                    sendMessage(response,
                                "Failed to insert values due to " + e, "back");
                    return;
                }

            } catch (Exception e) {
                sendMessage(response, "Failed to insert values due to " + e,
                            "back");

            } //

        } catch (Exception e) {
            System.out.println("Exception in openeing connection:" + e);
            sendMessage(response, "Failed to insert values due to " + e,
                        "back");
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
