package Servlets.HR.HR1.OfficeMaster.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class ServletOfficeTest extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";
    private Connection connection = null;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        ResultSet results = null;
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
            System.out.println("called Servlet");
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
            String xml = "";
            String strName = "", strSName = "", strHeadCode = "", strAdd1 =
                "", strAdd2 = "", strAdd3 = "", strPincode = "", strDistrict =
                "", strStd = "", phone = "", strAPhone = "", strFax =
                "", strAFax = "", strEMail = "", strAEMail = "";
            String strLevel = "", strCOffId = "", strHCode = "", strOCode =
                "", strPrimaryId = "", strDateOfFormation = "", strHRAClassId =
                "", strCCAClassId = "", strIsAccountUnit =
                "", strWingsApplicable = "", strRemarks =
                "", strDateEffectiveFrom = "", strStatus = "";
            int intHeadCode = 0, intCOffId = 0, intpincode = 0, districtcode =
                0;

            java.sql.Date DateOfFormation = null;
            String DateToBeDisplayed = "", ControllingOfficeName = "";
            int officeid = Integer.parseInt(request.getParameter("officeid"));

            try {
                String sql =
                    "select * from COM_MST_OFFICES_tmp where Office_Id=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, officeid);
                results = ps.executeQuery();
                if (results.next()) {

                    strName = results.getString("Office_Name");
                    System.out.println("OfficeName" + strName);
                    strSName = results.getString("Office_Short_Name");
                    System.out.println("OfficeShorName" + strSName);
                    try {
                        intHeadCode = results.getInt("OFFICE_HEAD_CADRE_ID");
                    } catch (NumberFormatException mfe) {
                    }

                    strLevel = results.getString("Office_Level_Id");
                    //strOCode = rs.getString("Office_Old_Code");
                    strPrimaryId = results.getString("Primary_Work_Id");
                    DateOfFormation = results.getDate("Date_of_Formation");

                    if (DateOfFormation == null) {
                        DateToBeDisplayed = "null";
                    } else {
                        try {
                            java.text.SimpleDateFormat sdf =
                                new java.text.SimpleDateFormat("dd/MM/yyyy");
                            DateToBeDisplayed = sdf.format(DateOfFormation);
                        } catch (Exception e) {
                            System.out.println("error while formatting date : " +
                                               e);
                        }
                    }
                    System.out.println("date : " + DateToBeDisplayed);

                    //strHRAClassId = rs.getString("HRA_Class_Id");
                    //strCCAClassId = rs.getString("CCA_Class_Id");
                    //strIsAccountUnit = rs.getString("Accounting_Unit");
                    //strWingsApplicable = rs.getString("Wings_Applicable");
                    strRemarks = results.getString("Remarks");
                    strStatus = results.getString("PROCESS_FLOW_STATUS_ID");
                    strAdd1 = results.getString("office_address1");
                    strAdd2 = results.getString("office_address2");
                    strAdd3 = results.getString("city_town_name");
                    intpincode = results.getInt("OFFICE_PIN_CODE");
                    districtcode = results.getInt("DISTRICT_CODE");
                    strStd = results.getString("OFFICE_STD_CODE");
                    phone = results.getString("OFFICE_PHONE_NO");
                    strAPhone = results.getString("ADDL_PHONE_NOS");
                    strFax = results.getString("OFFICE_FAX_NO");
                    strAFax = results.getString("ADDL_FAX_NOS");
                    strEMail = results.getString("OFFICE_EMAIL_ID");
                    strAEMail = results.getString("ADDL_EMAIL_IDS");
                    System.out.println("remarks:" + strRemarks);
                    //strRemarks=strRemarks.trim();
                    System.out.println("remarks:" + strRemarks);
                    results.close();
                    try {
                        String sql1 =
                            "select CONTROLLING_OFFICE_ID from COM_OFFICE_CONTROL_tmp where OFFICE_ID=?";
                        PreparedStatement ps1 =
                            connection.prepareStatement(sql1);
                        System.out.println("officeid is:" + officeid);
                        ps1.setInt(1, officeid);
                        results = ps1.executeQuery();
                        if (results.next()) {
                            intCOffId =
                                    results.getInt("Controlling_Office_Id");
                        } else {
                            intCOffId = 0;
                        }
                    } catch (NumberFormatException mfe) {
                    }

                    System.out.println("-------------------------------------");
                    System.out.println(intHeadCode);
                    System.out.println(intCOffId);
                    System.out.println(strLevel);
                    System.out.println(strOCode);
                    System.out.println(strPrimaryId);
                    System.out.println(DateOfFormation);
                    System.out.println(strHRAClassId);
                    System.out.println(strCCAClassId);
                    System.out.println(strIsAccountUnit);
                    System.out.println(strWingsApplicable);
                    System.out.println("-------------------------------------");

                    System.out.println("values retrived sucessfully");
                    xml =
 "<response><flag>success</flag><officename>" + strName +
   "</officename><officeshortname>" + strSName +
   "</officeshortname><headcode>" + intHeadCode + "</headcode><officelevel>" +
   strLevel + "</officelevel><primaryid>" + strPrimaryId +
   "</primaryid><controlofficeid>" + intCOffId + "</controlofficeid><date>" +
   DateToBeDisplayed + "</date><remarks>" + strRemarks +
   "</remarks><recordstatus>" + strStatus + "</recordstatus><address1>" +
   strAdd1 + "</address1><address2>" + strAdd2 + "</address2><city>" +
   strAdd3 + "</city><pincode>" + intpincode + "</pincode><district>" +
   districtcode + "</district><std>" + strStd + "</std><phone>" + phone +
   "</phone><addphone>" + strAPhone + "</addphone><fax>" + strFax +
   "</fax><addfax>" + strAFax + "</addfax><email>" + strEMail +
   "</email><addemail>" + strAEMail + "</addemail></response>";
                    response.setContentType(CONTENT_TYPE);
                    response.setHeader("cache-control", "no-cache");
                    System.out.println("xml is:" + xml);

                } else {
                    xml = "<response><flag>failure</flag></response>";
                }
            } catch (Exception e) {
                System.out.println("Exception in Query:" + e);
            }


            out.write(xml);

        } catch (Exception e) {
            System.out.println("Exception in Connection:" + e);
        }

        out.close();
    }
}
