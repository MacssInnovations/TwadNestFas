package Servlets.HR.HR1.EmployeeMaster.servlets;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.PrintWriter;
import java.io.IOException;

import java.sql.*;

import java.util.ResourceBundle;

public class View_Employee_Servlet extends HttpServlet {
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
            System.out.println("connection is: from the view emp servlet" +
                               ConnectionString);

        } catch (Exception e) {
            System.out.println("Exception in openeing connection:" + e);
        }
        //Statement statement=null;
        //Statement statement1=null;
        //Statement statement2=null;
        ResultSet results = null;
        ResultSet rs1 = null;
        ResultSet rs2 = null;
        ResultSet rs3 = null;
        ResultSet results4 = null;
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;
        //PreparedStatement ps4=null;
        PreparedStatement ps5 = null;
        //PreparedStatement ps6=null;
        //PreparedStatement ps7=null;
        //PreparedStatement ps8=null;

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


        System.out.println("servlet called");
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
        String strDofJoin = "";
        int strCDesign = 0;
        int strEmpleStatus = 0;
        String strMarital = "";
        String strRemarks = "";
        String strOthers = "";
        String strOtherState = "";
        // String strRecordStatus="";

        int strDesign = 0;
        String strOffGrade = "";
        String strOffName = "";
        String OffAddr1 = "";
        String OffAddr2 = "";
        String City = "";
        int strCServId = 0;
        String CDeptId = "";

        String xml = "";
        // String sxml="";
        try {
            StrCommand = request.getParameter("command");
            System.out.println(" commande" + StrCommand);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("The Exception is req paraam is:" + e);
        }

        if (StrCommand.equalsIgnoreCase("Existg")) {

            strEmpId = Integer.parseInt(request.getParameter("EmpId"));
            int SessionOfficeId =
                Integer.parseInt(request.getParameter("OfficeId"));

            //office id is fetched from the current posting based on emp id and office id is checked whether it is twad office or not

            String DeptId = "";
            int OffId = 0;
            //String OffName="";
            String OffDName = "";
            int OfficeId = 0;
            xml = "<response><command>Existg</command>";
            try {
                String sql9 =
                    " Select EMPLOYEE_ID from HRM_MST_EMPLOYEES where EMPLOYEE_ID=?";
                ps1 = connection.prepareStatement(sql9);
                ps1.setInt(1, strEmpId);
                rs1 = ps1.executeQuery();
                int t = 0;
                while (rs1.next()) {
                    t++;
                }
                if (t == 0) {
                    xml = xml + "<flag>NoEmp</flag>";
                } else {
                    String sql4 =
                        "Select * FROM HRM_EMP_CURRENT_POSTING WHERE EMPLOYEE_ID=?";
                    ps2 = connection.prepareStatement(sql4);
                    ps2.setInt(1, strEmpId);
                    rs2 = ps2.executeQuery();
                    int s = 0;
                    while (rs2.next()) {
                        s++;
                        OfficeId = rs2.getInt("OFFICE_ID");
                    }
                    if (s == 0) {
                        xml = xml + "<flag>NoRecord</flag>";
                        try {
                            System.out.println("emp id belongs to TWAD office");
                            String sql3 =
                                "select a.Employee_Name,a.Employee_Initial,a.GPF_NO, " +
                                "  a.EMPLOYEE_PREFIX,a.Date_Of_Birth,a.Gender,a.COMMUNITY_ID, " +
                                " a.Native_District_Code,a.Native_Taluk_Code,a.QUALIFICATIONS, " +
                                " a.EMPLOYMENT_STATUS_ID,a.TWAD_ENTRY_DATE,a.JOIN_TIME_DESIG_ID,a.EMP_CURRENT_STATUS_ID,a.Marital_Status,a.Remarks, " +
                                " a.Other_State,a.Other_Districts,e.EMP_PHOTO_FILE_NAME " +
                                " from hrm_mst_employees a " +
                                " inner join hrm_emp_addl_details e on e.employee_id=a.employee_id " +
                                " where a.employee_id=? ";

                            ps = connection.prepareStatement(sql3);
                            ps.setInt(1, strEmpId);
                            results = ps.executeQuery();
                            //int i=0;

                            System.out.println("exp id is:" + strEmpId);

                            try {

                                if (results.next()) {
                                    // i++;

                                    System.out.println("this is in the else of existg***********");


                                    strEmpInit =
                                            results.getString("Employee_Initial");
                                    System.out.println("init is" + strEmpInit);

                                    strEmpName =
                                            results.getString("Employee_Name");
                                    strEmpGpf = results.getInt("GPF_NO");
                                    strEmpPref =
                                            results.getString("EMPLOYEE_PREFIX");
                                    System.out.println("Prefix s :" +
                                                       strEmpPref);

                                    if (results.getDate("Date_Of_Birth") ==
                                        null) {
                                        strDob = "0";
                                    } else {
                                        String[] sd;
                                        sd =
  results.getDate("Date_Of_Birth").toString().split("-");
                                        strDob =
                                                sd[2] + "/" + sd[1] + "/" + sd[0];

                                        System.out.println("Date is: " +
                                                           strDob);
                                    }
                                    strGender = results.getString("Gender");
                                    if (strGender == null) {
                                        strGender = "M";
                                        System.out.println("gender is" +
                                                           strGender);
                                    }

                                    strCommunity =
                                            results.getInt("COMMUNITY_ID");
                                    if (strCommunity == 0) {
                                        strCommunity = 0;
                                        System.out.println("community is" +
                                                           strCommunity);
                                    }

                                    strDistrict =
                                            results.getInt("Native_District_Code");
                                    if (strDistrict == 0) {
                                        strDistrict = 0;
                                        System.out.println("district" +
                                                           strDistrict);
                                    }

                                    strTaluk =
                                            results.getInt("Native_Taluk_Code");
                                    if (strTaluk == 0) {
                                        strTaluk = 0;
                                        System.out.println("taluk is" +
                                                           strTaluk);
                                    }

                                    strQual =
                                            results.getString("QUALIFICATIONS");
                                    if (strQual == null) {
                                        strQual = "Not Specified";
                                        System.out.println("qual is" +
                                                           strQual);
                                    }


                                    strEmplmStatus =
                                            results.getString("EMPLOYMENT_STATUS_ID");
                                    if (strEmplmStatus == null) {
                                        strEmplmStatus = "NoVal";
                                        System.out.println("emplm " +
                                                           strEmplmStatus);
                                    }


                                    Date sd2 =
                                        results.getDate("TWAD_ENTRY_DATE");
                                    if (sd2 == null) {
                                        strDofJoin = "0";
                                        System.out.println("date of join" +
                                                           strDofJoin);
                                    } else {
                                        String[] sd1;
                                        sd1 =
 results.getDate("TWAD_ENTRY_DATE").toString().split("-");
                                        strDofJoin =
                                                sd1[2] + "/" + sd1[1] + "/" +
                                                sd1[0];

                                    }
                                    strDesign =
                                            results.getInt("JOIN_TIME_DESIG_ID");
                                    if (strDesign == 0) {
                                        strDesign = 0;
                                        System.out.println("join desgi is" +
                                                           strDesign);
                                    }


                                    strEmpleStatus =
                                            results.getInt("EMP_CURRENT_STATUS_ID");
                                    if (strEmpleStatus == 0)
                                        strEmpleStatus = 0;

                                    strMarital =
                                            results.getString("Marital_Status");
                                    if (strMarital == null)
                                        strMarital = "M";


                                    strRemarks = results.getString("Remarks");
                                    if (strRemarks == null)
                                        strRemarks = "Not Specified";

                                    strOtherState =
                                            results.getString("Other_State");
                                    if (strOtherState == null)
                                        strOtherState = "Not Applicable";

                                    strOthers =
                                            results.getString("Other_Districts");
                                    if (strOthers == null)
                                        strOthers = "Not Applicable";


                                    String ImagePath =
                                        results.getString("EMP_PHOTO_FILE_NAME");
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
   "</EmpmStatus><DJoin>" + strDofJoin + "</DJoin><EmpDesig>" + strDesign +
   "</EmpDesig><EmpeStatus>" + strEmpleStatus + "</EmpeStatus>";
                                        xml =
 xml + "<EmpMarital>" + strMarital + "</EmpMarital><Remarks>" + strRemarks +
   "</Remarks><ImagePath>" + ImagePath + "</ImagePath>";
                                    } else {
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
   "</EmpmStatus><DJoin>" + strDofJoin + "</DJoin><EmpDesig>" + strDesign +
   "</EmpDesig><EmpeStatus>" + strEmpleStatus + "</EmpeStatus>";
                                        xml =
 xml + "<EmpMarital>" + strMarital + "</EmpMarital><Remarks>" + strRemarks +
   "</Remarks><Others>" + strOthers + "</Others>";
                                        xml =
 xml + "<OtherState>" + strOtherState + "</OtherState><ImagePath>" +
   ImagePath + "</ImagePath>";
                                    }
                                }

                            } catch (Exception aee) {
                                System.out.println("Exception in the getting values IN get : " +
                                                   aee);

                            }
                            results.close();
                            response.setHeader("cache-control", "no-cache");

                        } catch (Exception e1) {
                            System.out.println("Exception is in Get*******" +
                                               e1);

                        }
                    } else {

                        System.out.println("Employee id ************* in else part" +
                                           strEmpId);

                        if (SessionOfficeId == OfficeId) {
                            try {
                                System.out.println("Employee id ************* in else part*********" +
                                                   strEmpId);
                                String sql3 =
                                    "Select OFFICE_ID,DEPARTMENT_ID FROM HRM_EMP_CURRENT_POSTING WHERE EMPLOYEE_ID=?";
                                ps3 = connection.prepareStatement(sql3);
                                ps3.setInt(1, strEmpId);
                                rs3 = ps3.executeQuery();
                                //int found=0;
                                xml = xml + "<flag>success</flag>";
                                if (rs3.next()) {
                                    DeptId = rs3.getString("DEPARTMENT_ID");
                                    OffId = rs3.getInt("OFFICE_ID");
                                    System.out.println("Dept id is:**************" +
                                                       DeptId);
                                    try {

                                        if (DeptId.equals("TWAD")) {
                                            System.out.println("Dept id is:" +
                                                               DeptId);
                                            String sql =
                                                "select office_id,office_name,Office_address1,office_address2,CITY_TOWN_NAME,DISTRICT_CODE,OFFICE_PHONE_NO,ADDL_PHONE_NOS,OFFICE_EMAIL_ID,ADDL_EMAIL_IDS,OFFICE_FAX_NO,ADDL_FAX_NOS,OFFICE_STD_CODE from com_mst_offices where Office_Id=?";
                                            PreparedStatement pres =
                                                connection.prepareStatement(sql);
                                            pres.setInt(1, OffId);
                                            connection.clearWarnings();


                                            ResultSet results12 =
                                                pres.executeQuery();
                                            try {
                                                if (results12.next()) {

                                                    strOffId =
                                                            results12.getInt("office_id");
                                                    strOffName =
                                                            results12.getString("OFFICE_NAME");
                                                    System.out.println("offname in twad is*****************************" +
                                                                       strOffId);
                                                    OffAddr1 =
                                                            results12.getString("Office_address1");
                                                    OffAddr2 =
                                                            results12.getString("Office_address2");
                                                    City =
results12.getString("CITY_TOWN_NAME");
                                                    if (City == null) {
                                                        City = "Null";
                                                    }


                                                    xml =
 xml + "<OFFICE_ID>" + strOffId + "</OFFICE_ID><OFFICE_NAME>" + strOffName +
   "</OFFICE_NAME>";
                                                    xml =
 xml + "<OffAddr1>" + OffAddr1 + "</OffAddr1><OffAddr2>" + OffAddr2 +
   "</OffAddr2><City>" + City + "</City>";
                                                }

                                            } catch (Exception e) {
                                                System.out.println("not in the result set of twad office" +
                                                                   e);

                                            } finally {
                                                results12.close();
                                            }
                                        }

                                        else {
                                            String sql =
                                                "select OTHER_DEPT_OFFICE_ID,OTHER_DEPT_OFFICE_NAME,ADDRESS1,ADDRESS2,CITY_TOWN from HRM_MST_OTHER_DEPT_OFFICES where OTHER_DEPT_OFFICE_ID=? and OTHER_DEPT_ID=?";
                                            PreparedStatement statement3 =
                                                connection.prepareStatement(sql);
                                            statement3.setInt(1, OffId);
                                            statement3.setString(2, DeptId);
                                            connection.clearWarnings();
                                            try {
                                                ResultSet results13 =
                                                    statement3.executeQuery();
                                                if (results13.next()) {
                                                    OffDName =
                                                            results13.getString("OTHER_DEPT_OFFICE_NAME");
                                                    strOffId =
                                                            results13.getInt("OTHER_DEPT_OFFICE_ID");
                                                    OffAddr1 =
                                                            results13.getString("ADDRESS1");
                                                    OffAddr2 =
                                                            results13.getString("ADDRESS2");
                                                    City =
results13.getString("CITY_TOWN");
                                                    if (City == null) {
                                                        City = "Null";
                                                    }


                                                    if (OffAddr1 == null) {
                                                        OffAddr1 = "Null";
                                                    }

                                                    if (OffAddr2 == null) {
                                                        OffAddr2 = "Null";
                                                    }


                                                    xml =
 xml + "<OFFICE_ID>" + strOffId + "</OFFICE_ID><OFFICE_NAME>" + OffDName +
   "</OFFICE_NAME>";
                                                    xml =
 xml + "<OffAddr1>" + OffAddr1 + "</OffAddr1><OffAddr2>" + OffAddr2 +
   "</OffAddr2><City>" + City + "</City>";
                                                }

                                            } catch (SQLException e) {
                                                System.out.println("Exception in statement:" +
                                                                   e);
                                            } finally {
                                                statement3.close();
                                            }
                                        }
                                    } catch (SQLException e) {
                                        System.out.println("Exception in connection:" +
                                                           e);
                                    }


                                }
                            } catch (Exception ee) {
                                System.out.println("Selection of office name " +
                                                   ee);
                            }


                            //now based on the office id the values are being fetched from the employee table


                            try {
                                System.out.println("emp id belongs to TWAD office");
                                String sql3 =

                                    "select a.Employee_Name,a.Employee_Initial,a.GPF_NO, " +
                                    "  a.EMPLOYEE_PREFIX,a.Date_Of_Birth,a.Gender,a.COMMUNITY_ID, " +
                                    " a.Native_District_Code,a.Native_Taluk_Code,a.QUALIFICATIONS, " +
                                    " a.EMPLOYMENT_STATUS_ID,a.TWAD_ENTRY_DATE,a.JOIN_TIME_DESIG_ID,a.EMP_CURRENT_STATUS_ID,a.Marital_Status,a.Remarks, " +
                                    " a.Other_State,a.Other_Districts, e.EMP_PHOTO_FILE_NAME, " +
                                    " b.designation_id,b.office_grade,d.service_group_id,b.DEPARTMENT_ID " +
                                    " from hrm_mst_employees a " +
                                    " inner join hrm_emp_current_posting b on b.employee_id=a.employee_id " +
                                    " inner join hrm_mst_designations c on c.designation_id=b.designation_id " +
                                    " inner join hrm_mst_service_group d on d.service_group_id=c.service_group_id and a.employee_id=? " +
                                    " left outer join hrm_emp_addl_details e on e.employee_id=a.employee_id ";

                                ps5 = connection.prepareStatement(sql3);
                                ps5.setInt(1, strEmpId);
                                results4 = ps5.executeQuery();
                                int i = 0;

                                System.out.println("exp id is:" + strEmpId);

                                try {

                                    if (results4.next()) {
                                        i++;

                                        System.out.println("this is in the else of existg***********");


                                        strEmpInit =
                                                results4.getString("Employee_Initial");
                                        System.out.println("init is" +
                                                           strEmpInit);

                                        strEmpName =
                                                results4.getString("Employee_Name");
                                        strEmpGpf = results4.getInt("GPF_NO");
                                        strEmpPref =
                                                results4.getString("EMPLOYEE_PREFIX");
                                        System.out.println("Prefix s :" +
                                                           strEmpPref);

                                        if (results4.getDate("Date_Of_Birth") ==
                                            null) {
                                            strDob = "0";
                                        } else {
                                            String[] sd;
                                            sd =
  results4.getDate("Date_Of_Birth").toString().split("-");
                                            strDob =
                                                    sd[2] + "/" + sd[1] + "/" +
                                                    sd[0];

                                            System.out.println("Date is: " +
                                                               strDob);
                                        }
                                        strGender =
                                                results4.getString("Gender");
                                        if (strGender == null) {
                                            strGender = "M";
                                            System.out.println("gender is" +
                                                               strGender);
                                        }

                                        strCommunity =
                                                results4.getInt("COMMUNITY_ID");
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
                                            System.out.println("taluk is" +
                                                               strTaluk);
                                        }

                                        strQual =
                                                results4.getString("QUALIFICATIONS");
                                        if (strQual == null) {
                                            strQual = "Not Specified";
                                            System.out.println("qual is" +
                                                               strQual);
                                        }


                                        strEmplmStatus =
                                                results4.getString("EMPLOYMENT_STATUS_ID");
                                        if (strEmplmStatus == null) {
                                            strEmplmStatus = "NoVal";
                                            System.out.println("emplm " +
                                                               strEmplmStatus);
                                        }


                                        Date sd2 =
                                            results4.getDate("TWAD_ENTRY_DATE");
                                        if (sd2 == null) {
                                            strDofJoin = "0";
                                            System.out.println("date of join" +
                                                               strDofJoin);
                                        } else {
                                            String[] sd1;
                                            sd1 =
 results4.getDate("TWAD_ENTRY_DATE").toString().split("-");
                                            strDofJoin =
                                                    sd1[2] + "/" + sd1[1] +
                                                    "/" + sd1[0];

                                        }
                                        strDesign =
                                                results4.getInt("JOIN_TIME_DESIG_ID");
                                        if (strDesign == 0) {
                                            strDesign = 0;
                                            System.out.println("join desgi is" +
                                                               strDesign);
                                        }


                                        strEmpleStatus =
                                                results4.getInt("EMP_CURRENT_STATUS_ID");
                                        if (strEmpleStatus == 0)
                                            strEmpleStatus = 0;


                                        strMarital =
                                                results4.getString("Marital_Status");
                                        if (strMarital == null)
                                            strMarital = "M";


                                        strRemarks =
                                                results4.getString("Remarks");
                                        if (strRemarks == null)
                                            strRemarks = "Not Specified";


                                        strOtherState =
                                                results4.getString("Other_State");
                                        if (strOtherState == null)
                                            strOtherState = "Not Applicable";


                                        strOthers =
                                                results4.getString("Other_Districts");
                                        if (strOthers == null)
                                            strOthers = "Not Applicable";


                                        //values from the current posting table

                                        strCDesign =
                                                results4.getInt("designation_id");
                                        if (strCDesign == 0)
                                            strCDesign = 0;


                                        strCServId =
                                                results4.getInt("service_group_id");
                                        if (strCServId == 0)
                                            strCServId = 0;


                                        strOffGrade =
                                                results4.getString("office_grade");
                                        if (strOffGrade == null)
                                            strOffGrade = "Normal";


                                        CDeptId =
                                                results4.getString("DEPARTMENT_ID");
                                        if (CDeptId == null)
                                            CDeptId = "TWAD";

                                        String ImagePath =
                                            results4.getString("EMP_PHOTO_FILE_NAME");
                                        System.out.println(request.getContextPath());
                                        System.out.println(request.getRequestURL());
                                        String p = request.getContextPath();
                                        System.out.println(getServletConfig().getServletContext().getRealPath(p));

                                        System.out.println(request.getServletPath());

                                        if (ImagePath == null)
                                            ImagePath = "sample_emp.bmp";


                                        if ((strOtherState.equalsIgnoreCase("Not Applicable")) &&
                                            (strOthers.equalsIgnoreCase("Not Applicable"))) {
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
   "</EmpmStatus><DJoin>" + strDofJoin + "</DJoin><EmpDesig>" + strDesign +
   "</EmpDesig><EmpeStatus>" + strEmpleStatus + "</EmpeStatus>";
                                            xml =
 xml + "<EmpMarital>" + strMarital + "</EmpMarital><Remarks>" + strRemarks +
   "</Remarks><ImagePath>" + ImagePath + "</ImagePath>";
                                            xml =
 xml + "<DEPARTMENT_ID>" + CDeptId + "</DEPARTMENT_ID><Grade>" + strOffGrade +
   "</Grade><ServGroup>" + strCServId + "</ServGroup><DesignId>" + strCDesign +
   "</DesignId>";
                                        } else {
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
   "</EmpmStatus><DJoin>" + strDofJoin + "</DJoin><EmpDesig>" + strDesign +
   "</EmpDesig><EmpeStatus>" + strEmpleStatus + "</EmpeStatus>";
                                            xml =
 xml + "<EmpMarital>" + strMarital + "</EmpMarital><Remarks>" + strRemarks +
   "</Remarks><Others>" + strOthers + "</Others>";
                                            xml =
 xml + "<OtherState>" + strOtherState + "</OtherState><ImagePath>" +
   ImagePath + "</ImagePath>";
                                            xml =
 xml + "<DEPARTMENT_ID>" + CDeptId + "</DEPARTMENT_ID><Grade>" + strOffGrade +
   "</Grade><ServGroup>" + strCServId + "</ServGroup><DesignId>" + strCDesign +
   "</DesignId>";
                                        }
                                    }
                                    if (i == 0) {
                                        xml = xml + "<flag>NoValue</flag>";
                                    }
                                } catch (Exception aee) {
                                    System.out.println("Exception in the getting values IN get : " +
                                                       aee);

                                }
                                results4.close();
                                response.setHeader("cache-control",
                                                   "no-cache");

                            } catch (Exception e1) {
                                System.out.println("Exception is in Get*******" +
                                                   e1);
                                xml = xml + "<flag>failure</flag>";
                            }

                        } else {
                            System.out.println("no matching found in the current posting");
                            xml = xml + "<flag>NoCurrentPost</flag>";
                        }
                    }


                }

            } catch (Exception ae) {
                System.out.println("error in the current posting emp id" + ae);
            }

            xml = xml + "</response>";


        }


        System.out.println("xml is : " + xml);
        out.write(xml);
        out.flush();
        out.close();

    }


}
