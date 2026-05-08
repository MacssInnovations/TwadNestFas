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

public class NewServletUpdateOfficeDetails extends HttpServlet {
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
            String strAdd1 = "", strAdd2 = "", strAdd3 = "", strStd =
                "", phone = "", strAPhone = "", strFax = "", strAFax =
                "", strEMail = "", strAEMail = "";
            int intHeadCode = 0, intCOffId = 0, intpincode = 0, districtcode =
                0;
            String district = "", pincode = "";
            PreparedStatement ps = null;
            try {
                connection.clearWarnings();

                strOId = request.getParameter("txtOffice_Id");
                strName = request.getParameter("txtOff_Name");
                strSName = request.getParameter("txtShortName");
                strLevel = request.getParameter("cmbLevelId");
                strCOffId = request.getParameter("txtHContrllingOfficeID");
                strHeadedBy = request.getParameter("cmbHeadCode");
                //strOCode = request.getParameter("txtOCode");
                strPrimaryId = request.getParameter("cmbPrimaryID");
                System.out.println("primaryId:" + strPrimaryId);
                if (strPrimaryId == null) {
                    strPrimaryId = "";
                }
                strDateOfFormation = request.getParameter("txtDOF");
                //strHRAClassId = request.getParameter("cmbHRAClassID");
                //strCCAClassId = request.getParameter("cmbCCAClassID");
                //strIsAccountUnit = request.getParameter("optIAU");
                //strWingsApplicable = request.getParameter("optWA");
                strRemarks = request.getParameter("txtRemarks");
                strRemarks = strRemarks.trim();
                System.out.println("values retrieval was success");
                System.out.println("OfficeId:" + strOId);
                System.out.println("OfficeName:" + strName);
                System.out.println("OfficeShrName:" + strSName);
                System.out.println("ControlOfficeId:" + strCOffId);
                System.out.println("OfficeHead:" + strHeadedBy);
                System.out.println("primaryId:" + strPrimaryId);
                System.out.println("dateofformation:" + strDateOfFormation);
                System.out.println("Remarks:" + strRemarks);
                strAdd1 = request.getParameter("txtAdd1");
                strAdd2 = request.getParameter("txtAdd2");
                strAdd3 = request.getParameter("txtAdd3");
                district = request.getParameter("cmbDistrict");
                pincode = request.getParameter("txtPinCode").trim();
                System.out.println("pincode is:" + pincode);
                if (district != "null") {
                    districtcode = Integer.parseInt(district);
                }
                try {
                    intpincode = Integer.parseInt(pincode);
                } catch (NumberFormatException num) {
                    System.out.println("NumberFormatException:" + num);
                }
                strStd = request.getParameter("txtSTDCode");
                phone = request.getParameter("txtPhoneNo");
                strAPhone = request.getParameter("txtAddPhoneNo");
                strFax = request.getParameter("txtFAXNo");
                strAFax = request.getParameter("txtAddFAXNo");
                strEMail = request.getParameter("txtEMail");
                strAEMail = request.getParameter("txtAddEMail");
                System.out.println("address1:" + strAdd1);
                System.out.println("address2:" + strAdd2);
                System.out.println("address3:" + strAdd3);
                System.out.println("district:" + districtcode);
                System.out.println("pincode:" + intpincode);
                System.out.println("std:" + strStd);
                System.out.println("phone:" + phone);
                System.out.println("addphone:" + strAPhone);
                System.out.println("fax:" + strFax);
                System.out.println("addfax:" + strAFax);
                System.out.println("email:" + strEMail);
                System.out.println("addemail:" + strAEMail);
                String sql = "";

                sql =
 "update COM_MST_OFFICES_tmp set Office_Name=?,Office_Short_Name=?,OFFICE_HEAD_CADRE_ID=?,";
                sql =
 sql + "Office_Level_Id=?,Primary_Work_Id=?,Date_of_Formation=?,Remarks=?,Updated_Date=?,UPDATED_BY_USER_ID=?,PROCESS_FLOW_STATUS_ID=?,OFFICE_ADDRESS1=?,OFFICE_ADDRESS2=?,CITY_TOWN_NAME=?,OFFICE_PIN_CODE=?,DISTRICT_CODE=?,OFFICE_STD_CODE=?,OFFICE_PHONE_NO=?,ADDL_PHONE_NOS=?,OFFICE_FAX_NO=?,ADDL_FAX_NOS=?,OFFICE_EMAIL_ID=?,ADDL_EMAIL_IDS=?";
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
                //ps.setString(5,strOCode);
                ps.setString(5, strPrimaryId);

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
                ps.setDate(6, dateOfFormation);
                System.out.println("date inserted");

                /* ps.setString(8,strHRAClassId);
               ps.setString(9,strCCAClassId);
               if(strIsAccountUnit!=null)
               {
                   if(strIsAccountUnit.equals("yes"))
                   {
                     ps.setString(10,"Y");
                   }
                   else
                   {
                     ps.setString(10,"N");
                   }
               }
               if(strWingsApplicable!=null)
               {
                   if(strWingsApplicable.equals("yes"))
                   {
                     ps.setString(11,"Y");
                   }
                   else
                   {
                     ps.setString(11,"N");
                   }
               }*/

                ps.setString(7, strRemarks);

                long l = System.currentTimeMillis();
                Timestamp ts = new Timestamp(l);
                ps.setTimestamp(8, ts);
                ps.setString(9, "unknown");
                ps.setString(10, "MD");
                ps.setString(11, strAdd1);
                ps.setString(12, strAdd2);
                ps.setString(13, strAdd3);
                ps.setInt(14, intpincode);
                ps.setInt(15, districtcode);
                ps.setString(16, strStd);
                ps.setString(17, phone);
                ps.setString(18, strAPhone);
                ps.setString(19, strFax);
                ps.setString(20, strAFax);
                ps.setString(21, strEMail);
                ps.setString(22, strAEMail);
                Integer ii = Integer.parseInt(strOId);
                ps.setInt(23, ii.intValue());

                // setting controlling table value

                String sql2 =
                    "update COM_OFFICE_CONTROL_tmp set CONTROLLING_OFFICE_ID=?,DATE_EFFECTIVE_FROM=?,REMARKS=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=? where Office_Id=?";
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
                        "Details Related to the Temporary Office with Id : " +
                        ii.intValue();
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
