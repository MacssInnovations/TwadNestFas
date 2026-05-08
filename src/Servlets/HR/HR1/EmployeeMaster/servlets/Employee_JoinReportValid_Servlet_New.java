package Servlets.HR.HR1.EmployeeMaster.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.*;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class Employee_JoinReportValid_Servlet_New extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";


    public void init(ServletConfig config) throws ServletException {
        super.init(config);

    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);
        Connection connection = null;
        try {
            ResourceBundle rs1 =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";

            String strDriver = rs1.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rs1.getString("Config.DSN");
            String strhostname = rs1.getString("Config.HOST_NAME");
            String strportno = rs1.getString("Config.PORT_NUMBER");
            String strsid = rs1.getString("Config.SID");
            String strdbusername = rs1.getString("Config.USER_NAME");
            String strdbpassword = rs1.getString("Config.PASSWORD");

            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection


            Class.forName(strDriver.trim());
            connection =
                    DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                                                strdbpassword.trim());
            System.out.println("Connection String " + connection);

        } catch (Exception e) {
            System.out.println("________Exception in opening connection:_______________" +
                               e);
        }
        //Statement statement=null;
        ResultSet results = null;
        ResultSet rs4 = null;
        ResultSet rs3 = null;
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        // PreparedStatement ps3=null;
        String strCommand = "";
        String xml = "";
        response.setContentType("text/xml");
        PrintWriter pw = response.getWriter();
        response.setHeader("Cache-Control", "no-cache");
        HttpSession session = request.getSession(false);
        try {
            if (session == null) {
                xml =
 "<response><command>sessionout</command><flag>sessionout</flag></response>";
                pw.println(xml);
                System.out.println(xml);
                pw.close();
                return;

            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }

        try {
            strCommand = request.getParameter("command");
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (strCommand.equalsIgnoreCase("Delete")) {
            xml = "<response><command>Delete</command>";

            int strEmpName = 0;
            int JYear = 0;
            int OffId = 0;
            int JoinId = 0;

            try {
                try {
                    strEmpName =
                            Integer.parseInt(request.getParameter("txtEmpId"));
                    System.out.println(strEmpName);
                    OffId = Integer.parseInt(request.getParameter("txtOffId"));
                    System.out.println(OffId);
                    JYear = Integer.parseInt(request.getParameter("txtDOJ"));
                    System.out.println(JYear);
                    JoinId = Integer.parseInt(request.getParameter("JoinId"));
                    System.out.println(JoinId);

                } catch (Exception e) {
                    System.out.println("exce **** " + e);
                }

                String sql =
                    "delete from HRM_EMP_JOIN_REPORTS  where EMPLOYEE_ID=? and JOINING_REPORT_ID=? and OFFICE_ID=? and JOINING_YEAR=?";

                ps = connection.prepareStatement(sql);

                ps.setInt(1, strEmpName);
                ps.setInt(2, JoinId);
                ps.setInt(3, OffId);
                ps.setInt(4, JYear);
                ps.executeUpdate();
                xml = xml + "<flag>success</flag>";
                ps.close();


            } catch (Exception e) {
                System.out.println("Exception of the e.getStackTrace" +
                                   e.getStackTrace());
                System.out.println("Exception of the e.getMessage()" +
                                   e.getMessage());
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }

        else if (strCommand.equalsIgnoreCase("Load")) {

            //String sxml="<response><command>Load</command>";  sxml
            xml = "<response><command>Load</command>";
            String strJoindt = "", strCompdt = "", empstatus =
                "", compsession = "";
            String strEmpName = request.getParameter("EName");
            System.out.println("emp name" + strEmpName);
            int yes = 0;
            int yes1 = 0;
            try {

                ps =
  connection.prepareStatement("SELECT EMPLOYEE_ID FROM HRM_MST_EMPLOYEES WHERE EMPLOYEE_ID=?");
                ps.setInt(1, Integer.parseInt(strEmpName));
                results = ps.executeQuery();
                if (!results.next()) {
                    yes = 1;
                    //xml=sxml+"<flag>failure</flag>";    sxml
                    xml = xml + "<flag>failure</flag>";
                }
                ps =
  connection.prepareStatement("select * from HRM_EMP_JOIN_REPORTS where employee_id=?");
                ps.setInt(1, Integer.parseInt(strEmpName));
                results = ps.executeQuery();
                if (!results.next()) {
                    yes1 = 1;
                    //xml=sxml+"<flag>failure</flag>";    sxml
                    xml = xml + "<flag>nojoinreport</flag>";
                }
                if (yes == 0 && yes1 == 0)

                {
                    String sql =
                        "select a.employee_id,a.employee_name ||decode(a.EMPLOYEE_INITIAL,null,' ','.'||a.EMPLOYEE_INITIAL) as  EMPLOYEE_NAME,a.date_of_birth,a.gpf_no, " +
                        "c.cadre_id,d.cadre_name,d.cadre_short_name,e.JOINING_REPORT_ID,e.DATE_OF_JOINING,e.FN_OR_AN,e.DESIGNATION_ID,e.POST_COUNTED_ID,e.REMARKS, " +
                        "e.COMPLETED_DATE,e.EMP_PRE_STATUS,e.DATE_EFFECTIVE_FROM_SESSION,e.JOINED_SUBDIVISION,e.SUBDIVISION_OFFICE_ID, " +
                        "e.OFFICE_WING_SINO,e.PROCESS_FLOW_STATUS_ID," +
                        "(case when e.DEPARTMENT_ID='TWAD' then (select office_name from com_mst_offices where office_id=e.JOINING_OFFICE_ID)" +
                        "else (select other_dept_office_name from hrm_mst_other_dept_offices where other_dept_id=e.DEPARTMENT_ID and other_dept_office_id=e.JOINING_OFFICE_ID)" +
                        "end" + ")as OFFICE_NAME,e.DEPARTMENT_ID," +
                        "e.OFFICE_GRADE,e.JOINING_OFFICE_ID from " + "( " +
                        "select employee_id,employee_name,EMPLOYEE_INITIAL,date_of_birth,gpf_no from hrm_mst_employees " +
                        "where employee_id=? " + ") a " + "left outer join " +
                        "( " +
                        "select employee_id,JOINING_REPORT_ID,JOINING_OFFICE_ID,DATE_OF_JOINING,FN_OR_AN,DESIGNATION_ID,POST_COUNTED_ID,REMARKS,COMPLETED_DATE,EMP_PRE_STATUS, " +
                        "OFFICE_GRADE,DATE_EFFECTIVE_FROM_SESSION,JOINED_SUBDIVISION,SUBDIVISION_OFFICE_ID,OFFICE_WING_SINO,PROCESS_FLOW_STATUS_ID,office_id,DEPARTMENT_ID from HRM_EMP_JOIN_REPORTS " +
                        "WHERE (PROCESS_FLOW_STATUS_ID='CR' or PROCESS_FLOW_STATUS_ID='MD') " +
                        ") e " + "on a.EMPLOYEE_ID=e.EMPLOYEE_ID " +
                        "left outer join " + "( " +
                        "select employee_id ,designation_id from hrm_emp_current_posting " +
                        ") b " + "on e.employee_id=b.employee_id " +
                        "left outer join " + "( " +
                        "select designation_id,cadre_id from hrm_mst_designations " +
                        ") c " + "on b.designation_id=c.designation_id " +
                        "left outer join " + "( " +
                        "select cadre_id,cadre_name,cadre_short_name from hrm_mst_cadre " +
                        ") d " + "on c.cadre_id=d.cadre_id";

                    System.out.println("sql:" + sql);
                    ps = connection.prepareStatement(sql);
                    ps.setInt(1, Integer.parseInt(strEmpName));
                    ResultSet rs = ps.executeQuery();

                    int i = 0;
                    String strDob = "";
                    if (rs.next()) {
                        System.out.println("emp name" + strEmpName);

                        String strEName = rs.getString("Employee_Name");
                        int strEmpGpf = rs.getInt("GPF_NO");

                        String cadre = rs.getString("CADRE_NAME");
                        System.out.println("cadre" + cadre);

                        String off_name = rs.getString("OFFICE_NAME");
                        System.out.println(off_name);
                        String dept_id = rs.getString("DEPARTMENT_ID");
                        System.out.println(dept_id);


                        int JoinId = rs.getInt("JOINING_REPORT_ID");
                        String strNoon = rs.getString("FN_OR_AN");

                        int DesigId = rs.getInt("DESIGNATION_ID");

                        String off_grade = rs.getString("OFFICE_GRADE");

                        int PostId = rs.getInt("POST_COUNTED_ID");

                        String strRemarks = rs.getString("REMARKS");

                        int wing = rs.getInt("OFFICE_WING_SINO");

                        String strProcId =
                            rs.getString("PROCESS_FLOW_STATUS_ID");
                        System.out.println("proceess flow id is$$$$$" +
                                           strProcId);
                        i++;
                        Date strdt = rs.getDate("DATE_OF_BIRTH");
                        if (strdt == null) {
                            strDob = "0";
                        } else {
                            String[] sd;
                            sd =
  rs.getDate("DATE_OF_BIRTH").toString().split("-");
                            strDob = sd[2] + "/" + sd[1] + "/" + sd[0];
                        }

                        Date strjdt = rs.getDate("DATE_OF_JOINING");
                        if (strjdt == null) {
                            strJoindt = "0";
                        } else {
                            String[] sd1;
                            sd1 =
 rs.getDate("DATE_OF_JOINING").toString().split("-");
                            strJoindt = sd1[2] + "/" + sd1[1] + "/" + sd1[0];
                        }

                        Date strcdt = rs.getDate("COMPLETED_DATE");
                        if (strcdt == null) {
                            strCompdt = "0";
                        } else {
                            String[] sd1;
                            sd1 =
 rs.getDate("COMPLETED_DATE").toString().split("-");
                            strCompdt = sd1[2] + "/" + sd1[1] + "/" + sd1[0];
                        }
                        empstatus = rs.getString("EMP_PRE_STATUS");
                        compsession =
                                rs.getString("DATE_EFFECTIVE_FROM_SESSION");

                        xml = xml + "<flag>success</flag>";

                        xml =
 xml + "<Emp_Id>" + strEmpName + "</Emp_Id>" + "<EmpName>" + strEName +
   "</EmpName><EmpGpf>" + strEmpGpf + "</EmpGpf>";
                        xml =
 xml + "<Dtofbirth>" + strDob + "</Dtofbirth>" + "<JoinId>" + JoinId +
   "</JoinId><Cadre>" + cadre + "</Cadre>";
                        xml =
 xml + "<Noon>" + strNoon + "</Noon>" + "<DesigId>" + DesigId +
   "</DesigId><Grade>" + off_grade + "</Grade><Off_Name>" + off_name +
   "</Off_Name><dept_id>" + dept_id + "</dept_id>";
                        xml =
 xml + "<JDate>" + strJoindt + "</JDate><PostId>" + PostId +
   "</PostId><Remarks>" + strRemarks + "</Remarks><ProcId>" + strProcId +
   "</ProcId>";
                        xml =
 xml + "<workingstatus>" + empstatus + "</workingstatus><completeddate>" +
   strCompdt + "</completeddate>";
                        xml =
 xml + "<compsession>" + compsession + "</compsession>";

                        xml = xml + "<wing>" + wing + "</wing>";

                        String optjoin = rs.getString("JOINED_SUBDIVISION");
                        int subdivoffid = rs.getInt("JOINING_OFFICE_ID");

                        xml = xml + "<optjoin>" + optjoin + "</optjoin>";
                        xml =
 xml + "<subdivoffid>" + subdivoffid + "</subdivoffid>";
                        System.out.println("dest" + DesigId);
                        try {
                            String sql3 =
                                "select a.service_group_id from hrm_mst_service_group a inner join hrm_mst_designations b on b.service_group_id=a.service_group_id where b.designation_id=?";

                            ps1 = connection.prepareStatement(sql3);
                            ps1.setInt(1, DesigId);
                            rs3 = ps1.executeQuery();
                            while (rs3.next()) {
                                int servgrp = rs3.getInt("SERVICE_GROUP_ID");
                                System.out.println("serv" + servgrp);
                                xml =
 xml + "<ServGroup>" + servgrp + "</ServGroup>";

                                /***** 16-08-2006  **/
                                //ps=connection.prepareStatement("select max(date_to) as maxtodate from HRM_EMP_SERVICE_DATA where EMPLOYEE_ID=?");
                                ps =
  connection.prepareStatement("select DATE_EFFECTIVE_FROM as maxtodate from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?");
                                ps.setInt(1, Integer.parseInt(strEmpName));
                                rs = ps.executeQuery();
                                String maxtodate = "";
                                if (rs.next()) {
                                    if (rs.getDate("maxtodate") != null) {
                                        maxtodate =
                                                new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("maxtodate"));
                                        System.out.println("max to date :" +
                                                           rs.getDate("maxtodate"));
                                    } else {

                                        maxtodate = "empty";
                                    }
                                }
                                xml =
 xml + "<maxtodate>" + maxtodate + "</maxtodate>";

                                /*******/


                            }
                        } catch (Exception se) {
                            System.out.println("error in serv grp" + se);
                        }
                    }

                    if (i == 0) {

                        xml = xml + "<flag>failure2</flag>" + xml;
                    }
                    /*
                          else {
                              System.out.println("EmpStatus in joining form:"+empstatus);
                              if(empstatus!=null && empstatus.equalsIgnoreCase("DPT"))
                                  xml=sxml+"<flag>failure3</flag>";
                              else
                                  xml=sxml+"<flag>success</flag>"+xml;
                          }*/
                }

            } catch (Exception e) {
                System.out.println("Exception " + e);
                //xml=sxml+"<flag>failure</flag>";     sxml
                xml = xml + "<flag>failure</flag>";
            }
            //}
            xml = xml + "</response>";

        }

        else if (strCommand.equalsIgnoreCase("Update")) {

            xml = "<response><command>Update</command>";


            session = request.getSession(false);
            String updatedby = (String)session.getAttribute("UserId");
            long l = System.currentTimeMillis();
            Timestamp ts = new Timestamp(l);

            int strEmpName = 0;
            int strDesigId = 0;
            int PostId = 0;
            String strRemarks = "", empstatus = "", compsession =
                "", strgrade = "";
            int JYear = 0;
            int OffId = 0;
            int JoinId = 0;
            String strProceId = "";
            String radVal = "";
            Date dtjoin = null;
            java.util.Date d = null;
            java.sql.Date cdt = null;
            String optjoin = "", dept_id = "", employee_status_id = "";
            int currentofficecode = 0;
            int wing = 0;
            try {
                try {
                    System.out.println("join Validate");

                    strEmpName =
                            Integer.parseInt(request.getParameter("txtEmpId"));
                    OffId = Integer.parseInt(request.getParameter("txtOffId"));
                    dept_id = request.getParameter("dept_id");
                    if (dept_id.equalsIgnoreCase("TWAD"))
                        employee_status_id = "WKG";
                    else {
                        employee_status_id = "DPN";
                    }
                    strDesigId =
                            Integer.parseInt(request.getParameter("cmbdes"));
                    PostId =
                            Integer.parseInt(request.getParameter("comPostTow"));
                    JYear = Integer.parseInt(request.getParameter("txtDOJ"));
                    JoinId = Integer.parseInt(request.getParameter("JoinId"));
                    radVal = request.getParameter("radFNAN");
                    strRemarks = request.getParameter("txtRemarks");
                    strProceId = request.getParameter("comProcFlowId");
                    strgrade = request.getParameter("txtgrade");

                    System.out.println("strgrade" + strgrade);

                    Calendar c;
                    String[] sd =
                        request.getParameter("txtjoindate").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                    d = c.getTime();
                    dtjoin = new Date(d.getTime());

                    empstatus = request.getParameter("empstatus");
                    System.out.println("empstatus..." + empstatus);

                    System.out.println(request.getQueryString());
                    optjoin = request.getParameter("optjoin");
                    System.out.println("optjoin...." + optjoin);

                    if (optjoin != null) //&& optjoin.equalsIgnoreCase("S"))
                    {
                        currentofficecode =
                                Integer.parseInt(request.getParameter("currentoffice"));
                        System.out.println("currentoffice...." +
                                           currentofficecode);
                    }


                    System.out.println(request.getParameter("wing"));
                    if (request.getParameter("wing") != null) {
                        wing = Integer.parseInt(request.getParameter("wing"));
                    }
                    System.out.println("Wing =" + wing);

                    /*  if(empstatus!=null && !empstatus.equalsIgnoreCase("WKG")) {
                              String strDOC=request.getParameter("compdate");
                              SimpleDateFormat  dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                              d = dateFormat.parse(strDOC.trim());
                              dateFormat.applyPattern("yyyy-MM-dd");
                              String dateString = dateFormat.format(d);
                              cdt = java.sql.Date.valueOf(dateString);


                          }*/

                    // compsession=request.getParameter("compsession");
                    //  System.out.println("compsession..."+compsession);

                } catch (Exception e) {
                    System.out.println("exce **** " + e);
                }
                boolean flag = false;
                connection.setAutoCommit(false);
                System.out.println(strProceId);

                if (strProceId.equalsIgnoreCase("FR")) {

                    String sql =
                        "select EMPLOYEE_STATUS_ID  from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?";

                    ps = connection.prepareStatement(sql);
                    ps.setInt(1, strEmpName);
                    results = ps.executeQuery();
                    if (!results.next()) {
                        System.out.println("inside result of current posting");

                        PreparedStatement ps5 = null;
                        ResultSet rs5 = null;

                        ps5 =
 connection.prepareStatement("insert into  HRM_EMP_CURRENT_POSTING(EMPLOYEE_ID,OFFICE_ID,DESIGNATION_ID," +
                             " DATE_EFFECTIVE_FROM,REMARKS,DEPARTMENT_ID,EMPLOYEE_STATUS_ID,DATE_EFFECTIVE_FROM_SESSION,PROCESS_FLOW_STATUS_ID,OFFICE_GRADE,UPDATED_BY_USER_ID,UPDATED_DATE )" +
                             " values(?,?,?,?,?,?,?,?,?,?,?,?)");

                        ps5.setInt(1, strEmpName);
                        ps5.setInt(2, OffId);
                        ps5.setInt(3, strDesigId);
                        ps5.setDate(4, dtjoin);
                        ps5.setString(5, strRemarks);
                        ps5.setString(6, dept_id);
                        ps5.setString(7, employee_status_id);
                        ps5.setString(8, radVal);
                        ps5.setString(9, "MD");
                        ps5.setString(10, strgrade);
                        ps5.setString(11, updatedby);
                        ps5.setTimestamp(12, ts);

                        int k = ps5.executeUpdate();

                        if (k > 0) {
                            System.out.println("success");
                            flag = false;
                        } else {
                            System.out.println("failure");
                        }

                        /*
                            xml=xml+"<flag>failure</flag><flag>This employee does not have any current post.So Cann't Freezed.</flag>";
                            flag=true;*/
                    } else {
                        String currentStatus =
                            results.getString("EMPLOYEE_STATUS_ID");
                        if (currentStatus.equalsIgnoreCase("WKG")) {
                            xml =
 xml + "<flag>failure</flag><flag>Given employee has been working status.Can not post aganin.</flag>";
                            flag = true;
                        }

                    }
                }
                if (!flag) {
                    System.out.println("inside flag");
                    int officeid = OffId;
                    try {
                        String offlevel = null;
                        int sdofficeid = 0;
                        System.out.println("office id  is:======" + OffId);
                        System.out.println("currentofficecode========" +
                                           currentofficecode);

                        PreparedStatement psnew =
                            connection.prepareStatement("select office_level_id from com_mst_offices where office_id=?");
                        //psnew.setInt(1,OffId);
                        psnew.setInt(1, currentofficecode);
                        ResultSet rsnew = psnew.executeQuery();
                        if (rsnew.next()) {
                            offlevel = rsnew.getString("office_level_id");
                        }
                        if ((offlevel.equalsIgnoreCase("SD")) ||
                            (offlevel.equalsIgnoreCase("LB")) ||
                            (offlevel.equalsIgnoreCase("AW"))) {
                            System.out.println("Sub division controllign office id");
                            PreparedStatement psnew1 =
                                connection.prepareStatement("select controlling_office_id from com_office_control where office_id=?");
                            //psnew1.setInt(1,OffId);
                            psnew1.setInt(1, currentofficecode);
                            ResultSet rsnew1 = psnew1.executeQuery();
                            if (rsnew1.next()) {
                                sdofficeid =
                                        rsnew1.getInt("controlling_office_id");
                            }

                            officeid = sdofficeid;
                            System.out.println("Sub division controlling ofice id is :" +
                                               officeid);
                        } else if ((offlevel.equalsIgnoreCase("HO")) ||
                                   (offlevel.equalsIgnoreCase("RN")) ||
                                   (offlevel.equalsIgnoreCase("CL")) ||
                                   (offlevel.equalsIgnoreCase("DN"))) {
                            officeid = currentofficecode;
                        }

                    } catch (Exception e) {
                        System.out.println("Subdivision check error" + e);
                    }
                    System.out.println("Sub division controlling ofice id is outsid :" +
                                       officeid);


                    ///inserted here

                    PreparedStatement psorg =
                        connection.prepareStatement("select DESIGNATION_SHORT_NAME from HRM_MST_DESIGNATIONS where DESIGNATION_ID=?");
                    psorg.setInt(1, strDesigId);
                    ResultSet rsorg = psorg.executeQuery();
                    if (rsorg.next()) {
                        String desc =
                            rsorg.getString("DESIGNATION_SHORT_NAME");
                        System.out.println("Office Id::" + OffId);

                        if (desc != null &&
                            (desc.equalsIgnoreCase("EE") || desc.equalsIgnoreCase("CE") ||
                             desc.equalsIgnoreCase("SE") ||
                             desc.equalsIgnoreCase("DSE"))) {

                            if (desc.equalsIgnoreCase("EE") ||
                                desc.equalsIgnoreCase("DSE")) {
                                System.out.println("EE");
                                PreparedStatement psl =
                                    connection.prepareStatement("select OFFICE_LEVEL_ID from COM_MST_OFFICES where OFFICE_ID=?");
                                //psl.setInt(1,OffId);
                                psl.setInt(1, currentofficecode);
                                ResultSet rsl = psl.executeQuery();
                                if (rsl.next()) {
                                    String level =
                                        rsl.getString("OFFICE_LEVEL_ID");
                                    if (level.equalsIgnoreCase("HO")) {
                                        System.out.println("HO");
                                        officeid = OffId;
                                    } else if (level.equalsIgnoreCase("RN")) {
                                        System.out.println("RN");
                                        officeid = OffId;
                                    } else if (level.equalsIgnoreCase("CL")) {
                                        System.out.println("CL");
                                        PreparedStatement psc =
                                            connection.prepareStatement("select CONTROLLING_OFFICE_ID from  COM_OFFICE_CONTROL where OFFICE_ID=?");
                                        //psc.setInt(1,OffId);
                                        psc.setInt(1, currentofficecode);
                                        ResultSet rsc = psc.executeQuery();
                                        if (rsc.next()) {
                                            officeid =
                                                    rsc.getInt("CONTROLLING_OFFICE_ID");
                                        }
                                    } else if (level.equalsIgnoreCase("DN")) {
                                        System.out.println("DN");
                                        PreparedStatement psd =
                                            connection.prepareStatement("select CONTROLLING_OFFICE_ID from COM_OFFICE_CONTROL where OFFICE_ID=?");
                                        //psd.setInt(1,OffId);
                                        psd.setInt(1, currentofficecode);
                                        ResultSet rsd = psd.executeQuery();
                                        if (rsd.next()) {
                                            int officecl =
                                                rsd.getInt("CONTROLLING_OFFICE_ID");
                                            PreparedStatement psc =
                                                connection.prepareStatement("select CONTROLLING_OFFICE_ID from COM_OFFICE_CONTROL where OFFICE_ID=?");
                                            psc.setInt(1, officecl);
                                            ResultSet rsc = psc.executeQuery();
                                            if (rsc.next()) {
                                                officeid =
                                                        rsc.getInt("CONTROLLING_OFFICE_ID");
                                            }

                                        }
                                    }


                                }

                            } //EE
                            else if (desc.equalsIgnoreCase("CE") ||
                                     desc.equalsIgnoreCase("SE")) {
                                //officeid=5000;
                                System.out.println("CE or SE");
                                PreparedStatement psd =
                                    connection.prepareStatement("select OFFICE_ID from COM_MST_OFFICES where OFFICE_LEVEL_ID='HO'");
                                ResultSet rsd = psd.executeQuery();
                                if (rsd.next()) {
                                    officeid = rsd.getInt("OFFICE_ID");
                                }
                            } // CE  ||  SE
                            /*
                          else {
                              officeid=OffId;
                          }
                          */
                        }
                    }


                    System.out.println("here is ok1");
                    // Delete the SR Controllling office record if exist
                    psorg =
connection.prepareStatement("delete from HRM_EMP_CONTROLLING_OFFICE where EMPLOYEE_ID=?");
                    psorg.setInt(1, strEmpName);
                    psorg.executeUpdate();
                    System.out.println("here is ok2");


                    // insert SR Controlling Office Record
                    psorg =
connection.prepareStatement("insert into  HRM_EMP_CONTROLLING_OFFICE(EMPLOYEE_ID,CONTROLLING_OFFICE_ID,CONTROLLING_DEPARTMENT_ID,PROCESS_FLOW_STATUS_ID,CONTROLLING_DATE_FROM,UPDATED_BY_USER_ID,UPDATED_DATE) " +
                            " values (?,?,?,?,?,?,?)");
                    psorg.setInt(1, strEmpName);
                    psorg.setInt(2, officeid);
                    //psorg.setInt(2,con_offid);
                    psorg.setString(3, "TWAD");
                    psorg.setString(4, "FR");
                    java.sql.Date dt =
                        new java.sql.Date(new java.util.Date(System.currentTimeMillis()).getTime());
                    psorg.setDate(5, dt);
                    psorg.setString(6, updatedby);
                    psorg.setTimestamp(7, ts);
                    psorg.executeUpdate();
                    System.out.println("here is ok3");

                    System.out.println("ctrl office :" + officeid);
                    psorg =
connection.prepareStatement("delete from HRM_EMP_CONTROLLING_OFFICE_TMP where EMPLOYEE_ID=?");
                    psorg.setInt(1, strEmpName);
                    psorg.executeUpdate();
                    System.out.println("here is ok4");

                    psorg =
connection.prepareStatement("insert into  HRM_EMP_CONTROLLING_OFFICE_TMP(EMPLOYEE_ID,CONTROLLING_OFFICE_ID,CONTROLLING_DEPARTMENT_ID,PROCESS_FLOW_STATUS_ID,CONTROLLING_DATE_FROM,UPDATED_BY_USER_ID,UPDATED_DATE) " +
                            " values (?,?,?,?,?,?,?)");
                    psorg.setInt(1, strEmpName);
                    psorg.setInt(2, officeid);
                    // psorg.setInt(2,con_offid);
                    psorg.setString(3, "TWAD");
                    psorg.setString(4, "FR");
                    dt =
  new java.sql.Date(new java.util.Date(System.currentTimeMillis()).getTime());
                    psorg.setDate(5, dt);

                    psorg.setString(6, updatedby);
                    psorg.setTimestamp(7, ts);
                    psorg.executeUpdate();
                    System.out.println("here is ok5");
                    /*psorg=connection.prepareStatement("update  HRM_EMP_CONTROLLING_OFFICE_TMP set PROCESS_FLOW_STATUS_ID='FR'   where EMPLOYEE_ID=?");
                psorg.setInt(1,strEmpName);
                psorg.executeUpdate();
               */
                    /*   psorg=connection.prepareStatement("update SEC_MST_USERS set LOGIN_ENABLED='1' where EMPLOYEE_ID=?  ");
                    psorg.setInt(1,strEmpName);
                    psorg.executeUpdate();
                    System.out.println("step 8");*/


                    /*    update current wing */
                    ps =
  connection.prepareStatement("delete from HRM_EMP_CURRENT_WING where EMPLOYEE_ID=? ");
                    ps.setInt(1, strEmpName);
                    ps.executeUpdate();
                    System.out.println("test1");

                    java.sql.Date dt1 =
                        new java.sql.Date(System.currentTimeMillis());
                    ps =
  connection.prepareStatement("insert into HRM_EMP_CURRENT_WING(EMPLOYEE_ID,OFFICE_ID,OFFICE_WING_SINO,DATE_EFFECTIVE_FROM,UPDATED_BY_USER_ID,UPDATED_DATE) values(?,?,?,?,?,?)");

                    ps.setInt(1, strEmpName);
                    ps.setInt(2, OffId);
                    ps.setInt(3, wing);
                    ps.setDate(4, dtjoin);
                    ps.setString(5, updatedby);
                    ps.setTimestamp(6, ts);
                    ps.executeUpdate();
                    System.out.println("test2");

                    CallableStatement cs = null;
                    System.out.println("proc started");
                    cs =
  connection.prepareCall("{call HRM_EMP_JOIN_REPORT_UPT(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

                    //cs.setInt(1,OffId);
                    cs.setInt(1, JYear);
                    cs.setInt(2, JoinId);
                    cs.setInt(3, strEmpName);
                    cs.setDate(4, dtjoin);
                    cs.setString(5, radVal);
                    cs.setInt(6, strDesigId);
                    cs.setInt(7, PostId);
                    cs.setString(8, strRemarks);
                    cs.setString(9, strProceId);
                    cs.registerOutParameter(10, java.sql.Types.NUMERIC);
                    //cs.setDate(12,cdt);
                    cs.setString(11, empstatus);
                    cs.setString(12, optjoin);
                    cs.setInt(13, currentofficecode);
                    //cs.setString(14,compsession);
                    cs.setString(14, updatedby);
                    cs.setTimestamp(15, ts);
                    cs.setInt(16, wing);
                    cs.setString(17, dept_id);
                    cs.setString(18, employee_status_id);
                    cs.execute();
                    System.out.println("proc ended");
                    int errcode = cs.getInt(10);
                    System.out.println("SQLCODE:::" + errcode);
                    if (errcode != 0) {
                        xml = xml + "<flag>failure</flag>";
                    } else {
                        xml = xml + "<flag>success</flag>";
                    }
                    ps.close();
                }
                connection.commit();


            } catch (Exception e) {
                System.out.println("exception in the insertion " + e);
                xml = xml + "<flag>failure</flag>";
                try {
                    connection.rollback();
                } catch (Exception e1) {
                    System.out.println(e);
                }
            } finally {
                try {
                    connection.setAutoCommit(true);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            xml = xml + "</response>";
        }

        else if (strCommand.equalsIgnoreCase("Load1")) {

            String sxml = "<response><command>Load</command>";
            String strJoindt = "";
            String strEmpName = request.getParameter("EName");
            System.out.println("emp name" + strEmpName);
            try {

                /* String sql="SELECT A.EMPLOYEE_NAME ||decode(a.EMPLOYEE_INITIAL,null,' ','.'||a.EMPLOYEE_INITIAL) as  EMPLOYEE_NAME ,A.GPF_NO,A.DATE_OF_BIRTH,B.JOINING_REPORT_ID, " +
                                 " B.DATE_OF_JOINING, B.FN_OR_AN, B.DESIGNATION_ID, B.POST_COUNTED_ID, " +
                                 " B.REMARKS,B.PROCESS_FLOW_STATUS_ID FROM HRM_MST_EMPLOYEES A " +
                                 " INNER JOIN HRM_EMP_JOIN_REPORTS B ON B.EMPLOYEE_ID=A.EMPLOYEE_ID " +
                                  " WHERE A.EMPLOYEE_ID=? ";*/

                String sql =
                    "select a.employee_id,a.employee_name ||decode(a.EMPLOYEE_INITIAL,null,' ','.'||a.EMPLOYEE_INITIAL) as  EMPLOYEE_NAME,a.date_of_birth,a.gpf_no," +
                    " c.cadre_id,d.cadre_name,d.cadre_short_name,e.JOINING_REPORT_ID,e.DATE_OF_JOINING,e.FN_OR_AN,e.DESIGNATION_ID,e.POST_COUNTED_ID,e.REMARKS," +
                    " e.COMPLETED_DATE,e.EMP_PRE_STATUS,e.DATE_EFFECTIVE_FROM_SESSION,e.JOINED_SUBDIVISION,e.SUBDIVISION_OFFICE_ID," +
                    " e.OFFICE_WING_SINO,e.PROCESS_FLOW_STATUS_ID,f.office_name,e.OFFICE_GRADE,e.JOINING_OFFICE_ID from" +
                    " (" +
                    " select employee_id,employee_name,EMPLOYEE_INITIAL,date_of_birth,gpf_no from hrm_mst_employees" +
                    " where employee_id=?" + " ) a" + " left outer join" +
                    " (" +
                    " select employee_id,JOINING_REPORT_ID,JOINING_OFFICE_ID,DATE_OF_JOINING,FN_OR_AN,DESIGNATION_ID,POST_COUNTED_ID,REMARKS,COMPLETED_DATE,EMP_PRE_STATUS," +
                    " OFFICE_GRADE,DATE_EFFECTIVE_FROM_SESSION,JOINED_SUBDIVISION,SUBDIVISION_OFFICE_ID,OFFICE_WING_SINO,PROCESS_FLOW_STATUS_ID,office_id from HRM_EMP_JOIN_REPORTS" +
                    " WHERE (PROCESS_FLOW_STATUS_ID='CR' or PROCESS_FLOW_STATUS_ID='MD')" +
                    " ) e" + " on a.EMPLOYEE_ID=e.EMPLOYEE_ID" +
                    " left outer join" + " (" +
                    "  select office_id,office_name from com_mst_offices" +
                    " ) f" + " on e.JOINING_OFFICE_ID=f.office_id" +
                    " left outer join" + " (" +
                    " select employee_id ,designation_id from hrm_emp_current_posting" +
                    " ) b" + " on e.employee_id=b.employee_id" +
                    " left outer join" + " (" +
                    " select designation_id,cadre_id from hrm_mst_designations" +
                    " ) c" + " on b.designation_id=c.designation_id" +
                    " left outer join" + " (" +
                    " select cadre_id,cadre_name,cadre_short_name from hrm_mst_cadre" +
                    " ) d" + " on c.cadre_id=d.cadre_id";


                ps = connection.prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(strEmpName));
                ResultSet rs = ps.executeQuery();

                int i = 0;
                String strDob = "";
                if (rs.next()) {
                    System.out.println("emp name" + strEmpName);

                    String strEName = rs.getString("Employee_Name");
                    int strEmpGpf = rs.getInt("GPF_NO");

                    String cadre = rs.getString("CADRE_NAME");
                    System.out.println("cadre" + cadre);

                    String off_name = rs.getString("OFFICE_NAME");
                    System.out.println(off_name);

                    int JoinId = rs.getInt("JOINING_REPORT_ID");
                    String strNoon = rs.getString("FN_OR_AN");

                    int DesigId = rs.getInt("DESIGNATION_ID");

                    String off_grade = rs.getString("OFFICE_GRADE");

                    int PostId = rs.getInt("POST_COUNTED_ID");

                    String strRemarks = rs.getString("REMARKS");
                    String strProcId = rs.getString("PROCESS_FLOW_STATUS_ID");
                    i++;
                    Date strdt = rs.getDate("DATE_OF_BIRTH");
                    if (strdt == null) {
                        strDob = "0";
                    } else {
                        String[] sd;
                        sd = rs.getDate("DATE_OF_BIRTH").toString().split("-");
                        strDob = sd[2] + "/" + sd[1] + "/" + sd[0];
                    }

                    Date strjdt = rs.getDate("DATE_OF_JOINING");
                    if (strjdt == null) {
                        strJoindt = "0";
                    } else {
                        String[] sd1;
                        sd1 =
 rs.getDate("DATE_OF_JOINING").toString().split("-");
                        strJoindt = sd1[2] + "/" + sd1[1] + "/" + sd1[0];
                    }

                    xml =
 xml + "<Emp_Id>" + strEmpName + "</Emp_Id>" + "<EmpName>" + strEName +
   "</EmpName><EmpGpf>" + strEmpGpf + "</EmpGpf>";
                    xml =
 xml + "<Dtofbirth>" + strDob + "</Dtofbirth>" + "<JoinId>" + JoinId +
   "</JoinId><Cadre>" + cadre + "</Cadre>";
                    xml =
 xml + "<Noon>" + strNoon + "</Noon>" + "<DesigId>" + DesigId +
   "</DesigId><Grade>" + off_grade + "</Grade><Off_Name>" + off_name +
   "</Off_Name>";
                    xml =
 xml + "<JDate>" + strJoindt + "</JDate><PostId>" + PostId +
   "</PostId><Remarks>" + strRemarks + "</Remarks><ProcId>" + strProcId +
   "</ProcId>";
                    System.out.println("dest" + DesigId);
                    try {
                        String sql3 =
                            "select a.service_group_id from hrm_mst_service_group a inner join hrm_mst_designations b on b.service_group_id=a.service_group_id where b.designation_id=?";

                        ps1 = connection.prepareStatement(sql3);
                        ps1.setInt(1, DesigId);
                        rs3 = ps1.executeQuery();
                        while (rs3.next()) {
                            int servgrp = rs3.getInt("SERVICE_GROUP_ID");
                            System.out.println("serv" + servgrp);
                            xml =
 xml + "<ServGroup>" + servgrp + "</ServGroup>";
                        }
                    } catch (Exception se) {
                        System.out.println("error in serv grp" + se);
                    }
                }

                if (i == 0) {

                    try {
                        String sql2 =
                            "select * from hrm_mst_employees where employee_id=?";
                        ps2 = connection.prepareStatement(sql2);
                        ps2.setInt(1, Integer.parseInt(strEmpName));

                        rs4 = ps2.executeQuery();
                        int j = 0;
                        while (rs4.next()) {
                            j++;
                            String strEmpPref =
                                rs4.getString("Employee_Prefix");
                            String strEmpInit =
                                rs4.getString("Employee_Initial");
                            strEmpName = rs4.getString("Employee_Name");
                            int strEmpGpf = rs4.getInt("GPF_NO");
                            Date strdt = rs4.getDate("DATE_OF_BIRTH");
                            if (strdt == null) {
                                strDob = "0";
                            } else {
                                String[] sd;
                                sd =
  rs4.getDate("DATE_OF_BIRTH").toString().split("-");
                                strDob = sd[2] + "/" + sd[1] + "/" + sd[0];
                            }
                            xml = sxml + "<flag>NoRecord</flag>";
                            xml =
 xml + "<EmpPref>" + strEmpPref + "</EmpPref><EmpInit>" + strEmpInit +
   "</EmpInit><EmpName>" + strEmpName + "</EmpName><EmpGpf>" + strEmpGpf +
   "</EmpGpf><Dtofbirth>" + strDob + "</Dtofbirth>";
                        }
                        if (j == 0) {
                            xml = sxml + "<flag>NoValue</flag>";
                        }

                    } catch (Exception ae) {
                        System.out.println("Error in the second query" + ae);
                    }

                } else {
                    xml = sxml + "<flag>success</flag>" + xml;
                }

            } catch (Exception e) {
                System.out.println("Exception " + e);
                xml = sxml + "<flag>failure</flag>";
            }
            //}
            xml = xml + "</response>";

        }

        else if (strCommand.equalsIgnoreCase("LoadEmp")) {
            System.out.println("hello.");
            xml = "<response><command>LoadEmp</command>";
            // String strJoindt="";
            String strEmpName = request.getParameter("EName");
            System.out.println("emp name" + strEmpName);
            try {
                ps =
  connection.prepareStatement("SELECT EMPLOYEE_ID FROM HRM_EMP_CURRENT_POSTING WHERE EMPLOYEE_ID=?");
                ps.setInt(1, Integer.parseInt(strEmpName));
                results = ps.executeQuery();
                if (results.next()) {
                    xml = xml + "<flag>failure1</flag>";
                } else {
                    results.close();
                    ps.close();


                    String sql =
                        "SELECT A.EMPLOYEE_NAME ||decode(a.EMPLOYEE_INITIAL,null,' ','.'||a.EMPLOYEE_INITIAL) as  EMPLOYEE_NAME ,A.GPF_NO,A.DATE_OF_BIRTH FROM HRM_MST_EMPLOYEES A " +
                        " WHERE A.EMPLOYEE_ID=? ";

                    ps = connection.prepareStatement(sql);
                    ps.setInt(1, Integer.parseInt(strEmpName));
                    ResultSet rs = ps.executeQuery();

                    // int i=0;
                    String strDob = "";
                    if (rs.next()) {
                        xml = xml + "<flag>success</flag>";
                        System.out.println("emp name" + strEmpName);

                        String strEName = rs.getString("Employee_Name");
                        int strEmpGpf = rs.getInt("GPF_NO");

                        Date strdt = rs.getDate("DATE_OF_BIRTH");
                        if (strdt == null) {
                            strDob = "0";
                        } else {
                            String[] sd;
                            sd =
  rs.getDate("DATE_OF_BIRTH").toString().split("-");
                            strDob = sd[2] + "/" + sd[1] + "/" + sd[0];
                        }


                        xml =
 xml + "<Emp_Id>" + strEmpName + "</Emp_Id>" + "<EmpName>" + strEName +
   "</EmpName><EmpGpf>" + strEmpGpf + "</EmpGpf>";
                        xml = xml + "<Dtofbirth>" + strDob + "</Dtofbirth>";

                    } else {
                        xml =
 "<response><command>LoadEmp</command>" + "<flag>NoValue</flag>";
                    }
                }


            } catch (Exception e) {
                System.out.println("Exception " + e);
                xml = xml + "<flag>NoValue</flag>";
            }
            //}
            xml = xml + "</response>";

        } else if (strCommand.equalsIgnoreCase("LoadSer")) {
            System.out.println("load service ");
            String sxml = "<response><command>LoadSer</command>";
            String strJoindt = "";
            String strEmpName = request.getParameter("EName");
            System.out.println("emp name" + strEmpName);
            String strserid = request.getParameter("txtser");
            System.out.println("service id" + strserid);
            try {

                String sql =
                    "SELECT A.EMPLOYEE_NAME ||decode(a.EMPLOYEE_INITIAL,null,' ','.'||a.EMPLOYEE_INITIAL) as  EMPLOYEE_NAME ,A.GPF_NO,A.DATE_OF_BIRTH,B.JOINING_REPORT_ID, " +
                    " B.DATE_OF_JOINING, B.FN_OR_AN, B.DESIGNATION_ID, B.POST_COUNTED_ID, " +
                    " B.REMARKS,B.PROCESS_FLOW_STATUS_ID,B.OFFICE_GRADE FROM HRM_MST_EMPLOYEES A " +
                    " INNER JOIN HRM_EMP_JOIN_REPORTS B ON B.EMPLOYEE_ID=A.EMPLOYEE_ID " +
                    " WHERE A.EMPLOYEE_ID=? and B.JOINING_REPORT_ID=? ";

                ps = connection.prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(strEmpName));
                ps.setInt(2, Integer.parseInt(strserid));
                ResultSet rs = ps.executeQuery();

                int i = 0;
                String strDob = "";
                if (rs.next()) {
                    System.out.println("emp name" + strEmpName);

                    String strEName = rs.getString("Employee_Name");
                    int strEmpGpf = rs.getInt("GPF_NO");

                    int JoinId = rs.getInt("JOINING_REPORT_ID");
                    String strNoon = rs.getString("FN_OR_AN");

                    int DesigId = rs.getInt("DESIGNATION_ID");

                    String off_grade = rs.getString("OFFICE_GRADE");

                    int PostId = rs.getInt("POST_COUNTED_ID");

                    String strRemarks = rs.getString("REMARKS");
                    String strProcId = rs.getString("PROCESS_FLOW_STATUS_ID");
                    i++;
                    Date strdt = rs.getDate("DATE_OF_BIRTH");
                    if (strdt == null) {
                        strDob = "0";
                    } else {
                        String[] sd;
                        sd = rs.getDate("DATE_OF_BIRTH").toString().split("-");
                        strDob = sd[2] + "/" + sd[1] + "/" + sd[0];
                    }

                    Date strjdt = rs.getDate("DATE_OF_JOINING");
                    if (strjdt == null) {
                        strJoindt = "0";
                    } else {
                        String[] sd1;
                        sd1 =
 rs.getDate("DATE_OF_JOINING").toString().split("-");
                        strJoindt = sd1[2] + "/" + sd1[1] + "/" + sd1[0];
                    }

                    xml =
 xml + "<Emp_Id>" + strEmpName + "</Emp_Id>" + "<EmpName>" + strEName +
   "</EmpName><EmpGpf>" + strEmpGpf + "</EmpGpf>";
                    xml =
 xml + "<Dtofbirth>" + strDob + "</Dtofbirth>" + "<JoinId>" + JoinId +
   "</JoinId>";
                    xml =
 xml + "<Noon>" + strNoon + "</Noon>" + "<DesigId>" + DesigId +
   "</DesigId><Grade>" + off_grade + "</Grade>";
                    xml =
 xml + "<JDate>" + strJoindt + "</JDate><PostId>" + PostId +
   "</PostId><Remarks>" + strRemarks + "</Remarks><ProcId>" + strProcId +
   "</ProcId>";
                    System.out.println("dest" + DesigId);
                    try {
                        String sql3 =
                            "select a.service_group_id from hrm_mst_service_group a inner join hrm_mst_designations b on b.service_group_id=a.service_group_id where b.designation_id=?";

                        ps1 = connection.prepareStatement(sql3);
                        ps1.setInt(1, DesigId);
                        rs3 = ps1.executeQuery();
                        while (rs3.next()) {
                            int servgrp = rs3.getInt("SERVICE_GROUP_ID");
                            System.out.println("serv" + servgrp);
                            xml =
 xml + "<ServGroup>" + servgrp + "</ServGroup>";
                        }
                    } catch (Exception se) {
                        System.out.println("error in serv grp" + se);
                    }
                }

                if (i == 0) {

                    try {
                        String sql2 =
                            "select * from hrm_mst_employees where employee_id=?";
                        ps2 = connection.prepareStatement(sql2);
                        ps2.setInt(1, Integer.parseInt(strEmpName));

                        rs4 = ps2.executeQuery();
                        int j = 0;
                        while (rs4.next()) {
                            j++;
                            String strEmpPref =
                                rs4.getString("Employee_Prefix");
                            String strEmpInit =
                                rs4.getString("Employee_Initial");
                            strEmpName = rs4.getString("Employee_Name");
                            int strEmpGpf = rs4.getInt("GPF_NO");
                            Date strdt = rs4.getDate("DATE_OF_BIRTH");
                            if (strdt == null) {
                                strDob = "0";
                            } else {
                                String[] sd;
                                sd =
  rs4.getDate("DATE_OF_BIRTH").toString().split("-");
                                strDob = sd[2] + "/" + sd[1] + "/" + sd[0];
                            }
                            xml = sxml + "<flag>NoRecord</flag>";
                            xml =
 xml + "<EmpPref>" + strEmpPref + "</EmpPref><EmpInit>" + strEmpInit +
   "</EmpInit><EmpName>" + strEmpName + "</EmpName><EmpGpf>" + strEmpGpf +
   "</EmpGpf><Dtofbirth>" + strDob + "</Dtofbirth>";
                        }
                        if (j == 0) {
                            xml = sxml + "<flag>NoValue</flag>";
                        }

                    } catch (Exception ae) {
                        System.out.println("Error in the second query" + ae);
                    }

                } else {
                    xml = sxml + "<flag>success</flag>" + xml;
                }

            } catch (Exception e) {
                System.out.println("Exception " + e);
                xml = sxml + "<flag>failure</flag>";
            }
            //}
            xml = xml + "</response>";

        }


        System.out.println("xml is : " + xml);
        pw.write(xml);
        pw.flush();
        pw.close();


    }
}
