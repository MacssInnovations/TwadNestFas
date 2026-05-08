package Servlets.HR.HR1.EmployeeMaster.servlets;

import java.text.SimpleDateFormat;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.PrintWriter;
import java.io.IOException;

import java.sql.*;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

public class New_Validate_EmpDet_Servlet extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";


    public void init(ServletConfig config) throws ServletException {
        super.init(config);

    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
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
            System.out.println("connection is:&&&&&&&&&&&&&&&&&" +
                               ConnectionString);

        } catch (Exception e) {
            System.out.println("Exception in openeing connection:" + e);
        }

        ResultSet results1 = null;
        ResultSet rs5 = null;
        ResultSet results4 = null;
        ResultSet rs4 = null;
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps3 = null;
        PreparedStatement ps4 = null;
        PreparedStatement ps5 = null;
        PreparedStatement ps6 = null;

        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);
        try {
            if (session == null) {
                String xml =
                    "<response><command>sessionout</command><flag>sessionout</flag></response>";
                out.println(xml);
                System.out.println(xml);
                out.close();
                return;

            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }

        String StrCommand = "";
        int strOffId = 0;
        int strEmpId = 0;
        String strEmpPref = "";
        String strEmpInit = "";
        String strEmpName = "";
        int strEmpGpf = 0;
        String strDob = "";
        String strGender = "";
        int strCommunity = 0;
        int strDistrict = 0;
        int strTaluk = 0;
        String strQual = "";
        String strEmplmStatus = "";

        String strDesig = "";
        String strEmpleStatus = "";
        String strMarital = "";
        String strRemarks = "";
        String strOthers = "";
        String strOtherState = "";
        String strRecordStatus = "";
        int strDesign = 0;
        String strEmpGrade = "";
        int OfficeId = 0;

        String strEmpInitial = "";
        String strEmpPrefix = "";
        int strEmpGpfNo = 0;
        String Handicapped = "", getting = "", consolid = "";
        String probation = "", dateOfRegPro = "";
        String xml = "";
        try {
            StrCommand = request.getParameter("command");
            System.out.println(" commande" + StrCommand);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("The Exception is req paraam is:" + e);
        }

        if (StrCommand.equalsIgnoreCase("toValidate")) {
            try {

                strEmpId = Integer.parseInt(request.getParameter("EmpId"));
                System.out.println("empid" + strEmpId);
                strDob = request.getParameter("EmpDOB");
                System.out.println("empid" + strDob);
                strGender = request.getParameter("EmpGender");
                System.out.println("empid" + strGender);
                strCommunity = Integer.parseInt(request.getParameter("Comm"));
                System.out.println("empid" + strCommunity);
                strDistrict =
                        Integer.parseInt(request.getParameter("District"));
                System.out.println("empid" + strDistrict);

                strQual = request.getParameter("EmpQual");
                System.out.println("strQual" + strQual);
                strEmplmStatus = request.getParameter("EmpmStatus");
                System.out.println("strEmplmStatus" + strEmplmStatus);

                strDesig = request.getParameter("EmpDesig");
                System.out.println("strDesig" + strDesig);
                strEmpleStatus = request.getParameter("EmpeStatus");
                System.out.println("strEmpleStatus" + strEmpleStatus);
                strMarital = request.getParameter("EmpMarital");
                System.out.println("strMarital" + strMarital);
                strRemarks = request.getParameter("Remarks");
                System.out.println("strRemarks" + strRemarks);
                getting = request.getParameter("getting");
                System.out.println(getting + "getting=");
                Handicapped = request.getParameter("Handicapped");
                System.out.println("Handicapped" + Handicapped);
                consolid = request.getParameter("Consolid");
                System.out.println("Consolid" + consolid);

            } catch (Exception ae) {
                System.out.println("Error in getting vlaues" + ae);
            }

            try {
                strOffId = Integer.parseInt(request.getParameter("OffId"));
                strDesign = Integer.parseInt(request.getParameter("Design"));
                strEmpGrade = request.getParameter("Grade");

            } catch (Exception ae) {
                System.out.println("Error in getting vlaues in the office details" +
                                   ae);
            }

            try {
                strOthers = request.getParameter("Others");
                strOtherState = request.getParameter("OtherState");
                System.out.println("the value of others is " + strOthers);
            } catch (Exception ed) {
                System.out.println("Error in the other district value" + ed);
            }
            try {

                strTaluk = Integer.parseInt(request.getParameter("Taluk"));
            } catch (Exception se) {
                System.out.println("Error in the taluk" + se);
            }

            java.sql.Date date1 = null;
            java.sql.Date date2 = null;
            java.sql.Date date3 = null;


            SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
            try {
                java.util.Date d1 = dateFormat1.parse(strDob);
                dateFormat1.applyPattern("yyyy-MM-dd");
                System.out.println("date1:" + d1);
                strDob = dateFormat1.format(d1);
                System.out.println("date2:" + strDob);
                date1 = Date.valueOf(strDob);
                System.out.println("date3:" + date1);

            } catch (Exception e) {
                e.printStackTrace();

            }


            try {
                probation = request.getParameter("probation");
                System.out.println("probation" + probation + "|");
                dateOfRegPro = request.getParameter("dateOfRegPro");
                System.out.println("dateOfRegPro" + dateOfRegPro);
                if (dateOfRegPro != null) {
                    String[] sd = dateOfRegPro.split("/");
                    Calendar c =
                        new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) -
                                              1, Integer.parseInt(sd[0]));
                    java.util.Date d = c.getTime();
                    System.out.println("test is ok here :");
                    date3 = new Date(d.getTime());
                    System.out.println("New Date:" + date3);
                }

            } catch (Exception e) {
                //e.printStackTrace();
                System.out.println("Error dateOfRegPro :" + e);
                //return;
            }

            try {

                strEmpInitial = request.getParameter("EmpInitial");
                strEmpPrefix = request.getParameter("EmpPrefix");
                strEmpName = request.getParameter("EmpName");
                strEmpGpfNo = Integer.parseInt(request.getParameter("GpfNo"));

            } catch (Exception ae) {
                System.out.println("Exception in getting values for emp basic details");
            }
            xml = "<response><command>toValidate</command>";
            try {

                String updatedby = (String)session.getAttribute("UserId");
                long l = System.currentTimeMillis();
                Timestamp ts = new Timestamp(l);

                if (strDistrict != 999) {
                    try {
                        System.out.println("in the taluk of districts alone");

                        String sql =
                            "update hrm_mst_employees set Date_Of_Birth=?,Gender=?,COMMUNITY_ID=?,Native_District_Code=?,Native_Taluk_Code=?,QUALIFICATIONS=?,EMPLOYMENT_STATUS_ID=?,TWAD_ENTRY_DATE=?,JOIN_TIME_DESIG_ID=?,EMP_CURRENT_STATUS_ID=?,Marital_Status=?,Remarks=?,Other_State=?,Other_Districts=?,PROCESS_FLOW_STATUS_ID=?,REGULARISATION_PROBATION_DATE=?,APPROVED_PROBATIONER=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=?,Employee_Prefix=?,IS_CONSOLIDATE=?,EMP_WKG_GETTING_PENSION=?,IS_HANDICAPPED=?  where EMPLOYEE_ID=?";
                        ps = connection.prepareStatement(sql);

                        ps.setDate(1, date1);
                        ps.setString(2, strGender);
                        ps.setInt(3, strCommunity);
                        ps.setInt(4, strDistrict);
                        ps.setInt(5, strTaluk);
                        ps.setString(6, strQual);
                        ps.setString(7, strEmplmStatus);
                        ps.setDate(8, null);
                        ps.setString(9, strDesig);
                        ps.setString(10, strEmpleStatus);
                        ps.setString(11, strMarital);
                        ps.setString(12, strRemarks);
                        ps.setString(13, "Not Applicable");
                        ps.setString(14, "Not Applicable");
                        ps.setString(15, "FR");

                        ps.setDate(16, date3);
                        ps.setString(17, probation.trim());

                        ps.setString(18, updatedby);
                        ps.setTimestamp(19, ts);

                        if (strGender != null &&
                            strGender.equalsIgnoreCase("F"))
                            if (strEmpPrefix != null &&
                                (strEmpPrefix.equalsIgnoreCase("Mrs") ||
                                 strEmpPrefix.equalsIgnoreCase("Selvi")))
                                ps.setString(20, strEmpPrefix);
                            else
                                ps.setString(20, "Ms");
                        else if (strEmpPrefix != null &&
                                 (strEmpPrefix.equalsIgnoreCase("Mr") ||
                                  strEmpPrefix.equalsIgnoreCase("Thiru")))
                            ps.setString(20, strEmpPrefix);
                        else
                            ps.setString(20, "Mr");

                        ps.setString(21, consolid);
                        ps.setString(23, Handicapped);
                        ps.setString(22, getting);

                        ps.setInt(24, strEmpId);
                        // ps.setInt(16,strEmpId);
                        ps.executeUpdate();

                        xml = xml + "<flag>success</flag>";
                        ps.close();

                        String sqltemp =
                            "update HRM_MST_EMP_ADDL_TMP set Date_Of_Birth=?,Gender=?,COMMUNITY_ID=?,Native_District_Code=?,Native_Taluk_Code=?,QUALIFICATIONS=?,EMPLOYMENT_STATUS_ID=?,TWAD_ENTRY_DATE=?,JOIN_TIME_DESIG_ID=?,EMP_CURRENT_STATUS_ID=?,Marital_Status=?,Remarks=?,Other_State=?,Other_Districts=?,PROCESS_FLOW_STATUS_ID=?,REGULARISATION_PROBATION_DATE=?,APPROVED_PROBATIONER=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=?,Employee_Prefix=?,IS_CONSOLIDATE=?,EMP_WKG_GETTING_PENSION=?,IS_HANDICAPPED=?   where EMPLOYEE_ID=?";
                        ps = connection.prepareStatement(sqltemp);

                        ps.setDate(1, date1);
                        ps.setString(2, strGender);
                        ps.setInt(3, strCommunity);
                        ps.setInt(4, strDistrict);
                        ps.setInt(5, strTaluk);
                        ps.setString(6, strQual);
                        ps.setString(7, strEmplmStatus);
                        ps.setDate(8, null);
                        ps.setString(9, strDesig);
                        ps.setString(10, strEmpleStatus);
                        ps.setString(11, strMarital);
                        ps.setString(12, strRemarks);
                        ps.setString(13, "Not Applicable");
                        ps.setString(14, "Not Applicable");
                        ps.setString(15, "FR");
                        ps.setDate(16, date3);
                        ps.setString(17, probation.trim());

                        ps.setString(18, updatedby);
                        ps.setTimestamp(19, ts);

                        if (strGender != null &&
                            strGender.equalsIgnoreCase("F"))
                            if (strEmpPrefix != null &&
                                (strEmpPrefix.equalsIgnoreCase("Mrs") ||
                                 strEmpPrefix.equalsIgnoreCase("Selvi")))
                                ps.setString(20, strEmpPrefix);
                            else
                                ps.setString(20, "Ms");
                        else if (strEmpPrefix != null &&
                                 (strEmpPrefix.equalsIgnoreCase("Mr") ||
                                  strEmpPrefix.equalsIgnoreCase("Thiru")))
                            ps.setString(20, strEmpPrefix);
                        else
                            ps.setString(20, "Mr");
                        ps.setString(21, consolid);
                        ps.setString(23, Handicapped);
                        ps.setString(22, getting);

                        ps.setInt(24, strEmpId);
                        //ps.setInt(16,strEmpId);
                        ps.executeUpdate();

                    } catch (Exception eee) {
                        System.out.println("update for emp witth taluk" + eee);
                        xml = xml + "<flag>failure</flag>";
                    }
                } else {

                    try {


                        System.out.println("Updation of employees details when other district is selected");
                        String sql =
                            "update hrm_mst_employees set Date_Of_Birth=?,Gender=?,COMMUNITY_ID=?,Native_District_Code=?,Other_Districts=?,Other_State=?,QUALIFICATIONS=?,EMPLOYMENT_STATUS_ID=?,TWAD_ENTRY_DATE=?,JOIN_TIME_DESIG_ID=?,EMP_CURRENT_STATUS_ID=?,Marital_Status=?,Remarks=?,Native_Taluk_Code=?,PROCESS_FLOW_STATUS_ID=?,REGULARISATION_PROBATION_DATE=?,APPROVED_PROBATIONER=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=?,Employee_Prefix=?,IS_CONSOLIDATE=?,EMP_WKG_GETTING_PENSION=?,IS_HANDICAPPED=?    where EMPLOYEE_ID=?";
                        ps1 = connection.prepareStatement(sql);

                        ps1.setDate(1, date1);
                        ps1.setString(2, strGender);
                        ps1.setInt(3, strCommunity);
                        ps1.setInt(4, strDistrict);
                        ps1.setString(5, strOthers);
                        ps1.setString(6, strOtherState);
                        ps1.setString(7, strQual);
                        ps1.setString(8, strEmplmStatus);
                        ps1.setDate(9, date2);
                        ps1.setString(10, strDesig);
                        ps1.setString(11, strEmpleStatus);
                        ps1.setString(12, strMarital);
                        ps1.setString(13, strRemarks);
                        ps1.setInt(14, 0);
                        ps1.setString(15, "FR");
                        ps1.setDate(16, date3);
                        ps1.setString(17, probation.trim());

                        ps1.setString(18, updatedby);
                        ps1.setTimestamp(19, ts);

                        if (strGender != null &&
                            strGender.equalsIgnoreCase("F"))
                            if (strEmpPrefix != null &&
                                (strEmpPrefix.equalsIgnoreCase("Mrs") ||
                                 strEmpPrefix.equalsIgnoreCase("Selvi")))
                                ps1.setString(20, strEmpPrefix);
                            else
                                ps1.setString(20, "Ms");
                        else if (strEmpPrefix != null &&
                                 (strEmpPrefix.equalsIgnoreCase("Mr") ||
                                  strEmpPrefix.equalsIgnoreCase("Thiru")))
                            ps1.setString(20, strEmpPrefix);
                        else
                            ps1.setString(20, "Mr");
                        ps1.setString(21, consolid);
                        ps1.setString(23, Handicapped);
                        ps1.setString(22, getting);

                        ps1.setInt(24, strEmpId);
                        //ps1.setInt(16,strEmpId);
                        ps1.executeUpdate();
                        xml = xml + "<flag>success</flag>>";
                        ps1.close();

                        System.out.println("Updation of employees details when other district is selected");
                        String sqlttemp =
                            "update HRM_MST_EMP_ADDL_TMP set Date_Of_Birth=?,Gender=?,COMMUNITY_ID=?,Native_District_Code=?,Other_Districts=?,Other_State=?,QUALIFICATIONS=?,EMPLOYMENT_STATUS_ID=?,TWAD_ENTRY_DATE=?,JOIN_TIME_DESIG_ID=?,EMP_CURRENT_STATUS_ID=?,Marital_Status=?,Remarks=?,Native_Taluk_Code=?,PROCESS_FLOW_STATUS_ID=?,REGULARISATION_PROBATION_DATE=?,APPROVED_PROBATIONER=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=?,Employee_Prefix=?,IS_CONSOLIDATE=?,EMP_WKG_GETTING_PENSION=?,IS_HANDICAPPED=?   where EMPLOYEE_ID=?";
                        ps1 = connection.prepareStatement(sqlttemp);

                        ps1.setDate(1, date1);
                        ps1.setString(2, strGender);
                        ps1.setInt(3, strCommunity);
                        ps1.setInt(4, strDistrict);
                        ps1.setString(5, strOthers);
                        ps1.setString(6, strOtherState);
                        ps1.setString(7, strQual);
                        ps1.setString(8, strEmplmStatus);
                        ps1.setDate(9, date2);
                        ps1.setString(10, strDesig);
                        ps1.setString(11, strEmpleStatus);
                        ps1.setString(12, strMarital);
                        ps1.setString(13, strRemarks);
                        ps1.setInt(14, 0);
                        ps1.setString(15, "FR");
                        ps1.setDate(16, date3);
                        ps1.setString(17, probation.trim());

                        ps1.setString(18, updatedby);
                        ps1.setTimestamp(19, ts);

                        if (strGender != null &&
                            strGender.equalsIgnoreCase("F"))
                            if (strEmpPrefix != null &&
                                (strEmpPrefix.equalsIgnoreCase("Mrs") ||
                                 strEmpPrefix.equalsIgnoreCase("Selvi")))
                                ps1.setString(20, strEmpPrefix);
                            else
                                ps1.setString(20, "Ms");
                        else if (strEmpPrefix != null &&
                                 (strEmpPrefix.equalsIgnoreCase("Mr") ||
                                  strEmpPrefix.equalsIgnoreCase("Thiru")))
                            ps1.setString(20, strEmpPrefix);
                        else
                            ps1.setString(20, "Mr");

                        ps1.setString(21, consolid);
                        ps1.setString(23, Handicapped);
                        ps1.setString(22, getting);

                        ps1.setInt(24, strEmpId);
                        //ps.setInt(18,strEmpId);
                        //  ps1.setInt(16,strEmpId);
                        ps1.executeUpdate();

                    } catch (Exception eee) {
                        System.out.println("update for emp for other state and district" +
                                           eee);
                        xml = xml + "<flag>failure</flag>";
                    }
                }


            } catch (Exception e) {

                System.out.println("Exception in the update code" + e);
            }
            xml = xml + "</response>";
        }

        //This is for the Edit Additional Details
        else if (StrCommand.equalsIgnoreCase("ExistgBasic")) {
            System.out.println("inside the command");
            strEmpId = Integer.parseInt(request.getParameter("EmpId"));
            int SessionOfficeId =
                Integer.parseInt(request.getParameter("OfficeId"));
            xml = "<response><command>ExistgBasic</command>";
            try {
                String SROffice =
                    "select CONTROLLING_OFFICE_ID from HRM_EMP_CONTROLLING_OFFICE where employee_id=?";
                ps3 = connection.prepareStatement(SROffice);
                ps3.setInt(1, strEmpId);
                results1 = ps3.executeQuery();
                int g = 0;
                while (results1.next()) {
                    g++;

                    OfficeId = results1.getInt("CONTROLLING_OFFICE_ID");
                }
                if (g == 0) {
                    xml = xml + "<flag>NoSROffice</flag>";
                } else {

                    if (SessionOfficeId == OfficeId) {

                        System.out.println("exp id is:" + strEmpId);
                        String sql3 =
                            "select a.Employee_Name,a.Employee_Initial,a.GPF_NO, " +
                            "  a.EMPLOYEE_PREFIX,a.Date_Of_Birth,a.Gender,a.COMMUNITY_ID, " +
                            " a.Native_District_Code,a.Native_Taluk_Code,a.QUALIFICATIONS, " +
                            " a.EMPLOYMENT_STATUS_ID,a.TWAD_ENTRY_DATE,a.JOIN_TIME_DESIG_ID,a.EMP_CURRENT_STATUS_ID,a.Marital_Status,a.Remarks,a.IS_CONSOLIDATE,a.EMP_WKG_GETTING_PENSION,a.IS_HANDICAPPED " +
                            " a.Other_State,a.Other_Districts,a.PROCESS_FLOW_STATUS_ID,e.EMP_PHOTO_FILE_NAME" +
                            " from HRM_MST_EMP_ADDL_TMP a " +
                            " left outer join hrm_emp_addl_details e on e.employee_id=a.employee_id " +
                            " where a.employee_id=? ";
                        ps5 = connection.prepareStatement(sql3);
                        ps5.setInt(1, strEmpId);
                        results4 = ps5.executeQuery();
                        int i = 0;

                        System.out.println("exp id is:" + strEmpId);

                        try {

                            while (results4.next()) {
                                i++;
                                xml = xml + "<flag>success</flag>";
                                System.out.println("this is in the else of existg***********");


                                strEmpInit =
                                        results4.getString("Employee_Initial");
                                System.out.println("init is" + strEmpInit);

                                strEmpName =
                                        results4.getString("Employee_Name");
                                strEmpGpf = results4.getInt("GPF_NO");
                                strEmpPref =
                                        results4.getString("EMPLOYEE_PREFIX");
                                System.out.println("Prefix s :" + strEmpPref);

                                if (results4.getDate("Date_Of_Birth") ==
                                    null) {
                                    strDob = "0";
                                } else {
                                    String[] sd;
                                    sd =
  results4.getDate("Date_Of_Birth").toString().split("-");
                                    strDob = sd[2] + "/" + sd[1] + "/" + sd[0];

                                    System.out.println("Date is: " + strDob);
                                }
                                strGender = results4.getString("Gender");
                                if (strGender == null) {
                                    strGender = "M";
                                    System.out.println("gender is" +
                                                       strGender);
                                }

                                strCommunity = results4.getInt("COMMUNITY_ID");
                                if (strCommunity == 0) {
                                    strCommunity = 0;
                                    System.out.println("community is" +
                                                       strCommunity);
                                }

                                strDistrict =
                                        results4.getInt("Native_District_Code");
                                if (strDistrict == 0) {
                                    strDistrict = 0;
                                    System.out.println("district" +
                                                       strDistrict);
                                }

                                strTaluk =
                                        results4.getInt("Native_Taluk_Code");
                                if (strTaluk == 0) {
                                    strTaluk = 0;
                                    System.out.println("taluk is" + strTaluk);
                                }

                                strQual = results4.getString("QUALIFICATIONS");
                                if (strQual == null) {
                                    strQual = "Not Specified";
                                    System.out.println("qual is" + strQual);
                                }
                                strQual = strQual.replace("&", "and");


                                strEmplmStatus =
                                        results4.getString("EMPLOYMENT_STATUS_ID");
                                if (strEmplmStatus == null) {
                                    strEmplmStatus = "NoVal";
                                    System.out.println("emplm " +
                                                       strEmplmStatus);
                                }


                                strDesign =
                                        results4.getInt("JOIN_TIME_DESIG_ID");
                                if (strDesign == 0) {
                                    strDesign = 0;
                                    System.out.println("join desgi is" +
                                                       strDesign);
                                }


                                strEmpleStatus =
                                        results4.getString("EMP_CURRENT_STATUS_ID");
                                if (strEmpleStatus == null)
                                    strEmpleStatus = "0";


                                strMarital =
                                        results4.getString("Marital_Status");
                                if (strMarital == null)
                                    strMarital = "M";


                                strRemarks = results4.getString("Remarks");
                                if (strRemarks == null)
                                    strRemarks = "Not Specified";

                                consolid =
                                        results4.getString("IS_CONSOLIDATE");
                                Handicapped =
                                        results4.getString("IS_HANDICAPPED");
                                getting =
                                        results4.getString("EMP_WKG_GETTING_PENSION");

                                strOtherState =
                                        results4.getString("Other_State");
                                if (strOtherState == null)
                                    strOtherState = "Not Applicable";


                                strOthers =
                                        results4.getString("Other_Districts");
                                if (strOthers == null)
                                    strOthers = "Not Applicable";


                                strRecordStatus =
                                        results4.getString("Process_Flow_Status_Id");
                                if (strRecordStatus == null)
                                    strRecordStatus = "0";


                                String ImagePath =
                                    results4.getString("EMP_PHOTO_FILE_NAME");
                                System.out.println(request.getContextPath());
                                System.out.println(request.getRequestURL());
                                String p = request.getContextPath();
                                System.out.println(getServletConfig().getServletContext().getRealPath(p));
                                System.out.println(request.getServletPath());
                                System.out.println("Image of  this employee is:****************************************" +
                                                   ImagePath);

                                if (ImagePath == null)
                                    ImagePath = "sample_emp.bmp";


                                if ((strOtherState.equalsIgnoreCase("Not Applicable")) &&
                                    (strOthers.equalsIgnoreCase("Not Applicable"))) {
                                    System.out.println("..Not Applicable1");

                                    xml =
 xml + "<EmpPref>" + strEmpPref + "</EmpPref><EmpInit>" + strEmpInit +
   "</EmpInit><EmpName>" + strEmpName + "</EmpName><EmpGpf>" + strEmpGpf +
   "</EmpGpf>";
                                    xml =
 xml + "<EmpDOB>" + strDob + "</EmpDOB><EmpGender>" + strGender +
   "</EmpGender><Comm>" + strCommunity + "</Comm><District>" + strDistrict +
   "</District><Taluk>" + strTaluk + "</Taluk>";
                                    xml =
 xml + "<EmpQual>" + strQual + "</EmpQual><EmpmStatus>" + strEmplmStatus +
   "</EmpmStatus><EmpDesig>" + strDesign + "</EmpDesig><EmpeStatus>" +
   strEmpleStatus + "</EmpeStatus>";
                                    xml =
 xml + "<EmpMarital>" + strMarital + "</EmpMarital><Remarks>" + strRemarks +
   "</Remarks><ImagePath>" + ImagePath + "</ImagePath><RecordStatus>" +
   strRecordStatus + "</RecordStatus>";
                                    xml =
 xml + "<Handicapped>" + Handicapped + "</Handicapped><getting>" + getting +
   "</getting><Consolid>" + consolid + "</Consolid>";
                                    ;
                                } else {
                                    System.out.println("..Applicable1");
                                    xml =
 xml + "<EmpPref>" + strEmpPref + "</EmpPref><EmpInit>" + strEmpInit +
   "</EmpInit><EmpName>" + strEmpName + "</EmpName><EmpGpf>" + strEmpGpf +
   "</EmpGpf>";
                                    xml =
 xml + "<EmpDOB>" + strDob + "</EmpDOB><EmpGender>" + strGender +
   "</EmpGender><Comm>" + strCommunity + "</Comm><District>" + strDistrict +
   "</District>";
                                    xml =
 xml + "<EmpQual>" + strQual + "</EmpQual><EmpmStatus>" + strEmplmStatus +
   "</EmpmStatus><EmpDesig>" + strDesign + "</EmpDesig><EmpeStatus>" +
   strEmpleStatus + "</EmpeStatus>";
                                    xml =
 xml + "<EmpMarital>" + strMarital + "</EmpMarital><Remarks>" + strRemarks +
   "</Remarks><Others>" + strOthers + "</Others>";
                                    xml =
 xml + "<OtherState>" + strOtherState + "</OtherState><ImagePath>" +
   ImagePath + "</ImagePath><RecordStatus>" + strRecordStatus +
   "</RecordStatus>";
                                    xml =
 xml + "<Handicapped>" + Handicapped + "</Handicapped><getting>" + getting +
   "</getting><Consolid>" + consolid + "</Consolid>";
                                    ;
                                }
                            }
                            if (i == 0) {

                                String ForEmpId =
                                    "select Employee_Id,EMPLOYEE_PREFIX,Employee_Initial,Employee_Name,GPF_NO from HRM_MST_EMPLOYEES where employee_id=?";
                                ps3 = connection.prepareStatement(ForEmpId);
                                ps3.setInt(1, strEmpId);
                                results1 = ps3.executeQuery();
                                int E = 0;
                                while (results1.next()) {
                                    E++;
                                    xml = xml + "<flag>EmpDet</flag>";
                                    xml =
 xml + "<EmpPref>" + results1.getString("EMPLOYEE_PREFIX") +
   "</EmpPref><EmpInit>" + results1.getString("Employee_Initial") +
   "</EmpInit><EmpName>" + results1.getString("Employee_Name") +
   "</EmpName><EmpGpf>" + results1.getInt("GPF_NO") + "</EmpGpf>";
                                }
                                if (E == 0) {
                                    xml = xml + "<flag>NoEmployee</flag>";
                                } else {
                                }
                            }

                        } catch (Exception aee) {
                            System.out.println("Exception in the getting values IN get : " +
                                               aee);

                        }
                        results4.close();
                        response.setHeader("cache-control", "no-cache");

                    } else {
                        xml = xml + "<flag>DifferentSROffice</flag>";
                    }
                }
            } catch (Exception ee) {
                System.out.println("Error in the Edit Employee Form Details" +
                                   ee);
            }
            xml = xml + "</response>";
        }

        System.out.println("xml is : " + xml);
        out.write(xml);
        out.flush();
        out.close();

    }


}
