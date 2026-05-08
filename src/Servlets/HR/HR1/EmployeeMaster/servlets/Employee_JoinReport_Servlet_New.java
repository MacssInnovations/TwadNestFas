package Servlets.HR.HR1.EmployeeMaster.servlets;

import Servlets.Security.classes.UserProfile;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.*;

import java.text.SimpleDateFormat;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class Employee_JoinReport_Servlet_New extends HttpServlet {
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
        // Statement statement=null;
        ResultSet results = null;
        ResultSet rs4 = null;
        ResultSet rs3 = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        //PreparedStatement ps3=null;

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

            session = request.getSession(false);
            String updatedby = (String)session.getAttribute("UserId");
            long l = System.currentTimeMillis();
            Timestamp ts = new Timestamp(l);


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
                //String sql="Update HRM_EMP_JOIN_REPORTS set PROCESS_FLOW_STATUS_ID='DL',UPDATED_BY_USER_ID=?,UPDATED_DATE=? where EMPLOYEE_ID=? and JOINING_REPORT_ID=? and OFFICE_ID=? and JOINING_YEAR=?";

                ps = connection.prepareStatement(sql);


                ps.setString(1, updatedby);
                ps.setTimestamp(2, ts);

                ps.setInt(3, strEmpName);
                ps.setInt(4, JoinId);
                ps.setInt(5, OffId);
                ps.setInt(6, JYear);
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
            System.out.println("Loading");
            //String sxml="<response><command>Load</command>";       sxml
            xml = "<response><command>Load</command>";
            int user_emp = 0;
            boolean flag = true;

            String strJoindt = "", strCompdt = "", empstatus =
                "", compsession = "";
            String strEmpName = request.getParameter("EName");
            System.out.println("emp name1..." + strEmpName);
            try {

                session = request.getSession(false);
                UserProfile up = null;
                up = (UserProfile)session.getAttribute("UserProfile");
                user_emp = up.getEmployeeId();
                System.out.println("user emp" + user_emp);

                System.out.println("Admin Session:" +
                                   session.getAttribute("Admin"));

                ps =
  connection.prepareStatement("SELECT EMPLOYEE_ID FROM HRM_MST_EMPLOYEES WHERE EMPLOYEE_ID=?");
                ps.setInt(1, user_emp);
                rs = ps.executeQuery();
                if (!rs.next()) {
                    xml = xml + "<flag>failure</flag>";
                    flag = false;
                }

                else if (up.getEmployeeId() !=
                         (Integer.parseInt(strEmpName))) {
                    int OfficeId = 0;
                    String sql =
                        "select CONTROLLING_OFFICE_ID from HRM_EMP_CONTROLLING_OFFICE where employee_id=?";
                    ps = connection.prepareStatement(sql);
                    ps.setInt(1, user_emp);
                    rs = ps.executeQuery();

                    if (rs.next()) {
                        OfficeId = rs.getInt("CONTROLLING_OFFICE_ID");
                        System.out.println("user controlling OfficeId" +
                                           OfficeId);
                    }

                    if (OfficeId != 0) {
                        sql =
 "select OFFICE_ID  from HRM_EMP_CURRENT_POSTING where employee_id=?";
                        ps = connection.prepareStatement(sql);
                        ps.setInt(1, up.getEmployeeId());
                        rs = ps.executeQuery();
                        if (rs.next()) {
                            int offid = rs.getInt("OFFICE_ID");
                            System.out.println("user office id" + offid);

                            if (offid != OfficeId) {
                                System.out.println("Admin Session:" +
                                                   session.getAttribute("Admin"));
                                if (session.getAttribute("Admin") == null ||
                                    !((String)session.getAttribute("Admin")).equalsIgnoreCase("YES")) { //response.sendRedirect(request.getContextPath()+"/org/Library/jsps/Messenger.jsp?message=" + "Can not see profile. Because Employee Id "+strEmpId+" is not under your Office!");
                                    xml = xml + "<flag>failure1</flag>";
                                    flag = false;
                                }
                            }
                        } else {
                            // response.sendRedirect(request.getContextPath()+"/org/Library/jsps/Messenger.jsp?message=" + "Current Posting is not available. Can not see the profile for "+strEmpId+"!");
                            xml = xml + "<flag>failure2</flag>";
                            flag = false;
                        }

                    } else {
                        //if(session.getAttribute("Admin")==null || !((String)session.getAttribute("Admin")).equalsIgnoreCase("YES"))
                        {
                            xml = xml + "<flag>failure3</flag>";
                            flag = false;
                        }
                    }
                }


                if (flag) {


                    ps =
  connection.prepareStatement("SELECT EMPLOYEE_ID FROM HRM_MST_EMPLOYEES WHERE EMPLOYEE_ID=?");
                    ps.setInt(1, Integer.parseInt(strEmpName));
                    results = ps.executeQuery();
                    if (!results.next()) {
                        //xml=sxml+"<flag>failure</flag>";             sxml
                        xml = xml + "<flag>failure</flag>";
                    } else {
                        ps =
  connection.prepareStatement("SELECT EMPLOYEE_ID FROM HRM_EMP_CURRENT_POSTING WHERE EMPLOYEE_ID=?");
                        ps.setInt(1, Integer.parseInt(strEmpName));
                        results = ps.executeQuery();

                        if (!results.next()) {
                            // xml=sxml+"<flag>failure1</flag>";

                            //else {
                            //results.close();
                            //ps.close();

                            System.out.println("emp name2" + strEmpName);
                            /* String sql="SELECT A.EMPLOYEE_NAME ||decode(a.EMPLOYEE_INITIAL,null,' ','.'||a.EMPLOYEE_INITIAL) as  EMPLOYEE_NAME,A.GPF_NO,A.DATE_OF_BIRTH,B.JOINING_REPORT_ID, " +
                                            " B.DATE_OF_JOINING, B.FN_OR_AN, B.DESIGNATION_ID, B.POST_COUNTED_ID, " +
                                            " B.REMARKS,B.COMPLETED_DATE,B.EMP_PRE_STATUS,B.DATE_EFFECTIVE_FROM_SESSION,B.JOINED_SUBDIVISION,B.SUBDIVISION_OFFICE_ID,B.OFFICE_WING_SINO FROM HRM_MST_EMPLOYEES A " +
                                            " INNER JOIN HRM_EMP_JOIN_REPORTS B ON B.EMPLOYEE_ID=A.EMPLOYEE_ID " +
                                             " WHERE A.EMPLOYEE_ID=? and (B.PROCESS_FLOW_STATUS_ID='CR' or B.PROCESS_FLOW_STATUS_ID='MD') ";*/

                            String sql =
                                "select a.employee_id,a.employee_name ||decode(a.EMPLOYEE_INITIAL,null,' ','.'||a.EMPLOYEE_INITIAL) as  EMPLOYEE_NAME,a.date_of_birth,a.gpf_no," +
                                " c.cadre_id,d.cadre_name,d.cadre_short_name,e.JOINING_REPORT_ID,e.DATE_OF_JOINING,e.FN_OR_AN,e.DESIGNATION_ID,e.POST_COUNTED_ID,e.REMARKS," +
                                " e.COMPLETED_DATE,e.EMP_PRE_STATUS,e.DATE_EFFECTIVE_FROM_SESSION,e.JOINED_SUBDIVISION,e.SUBDIVISION_OFFICE_ID," +
                                " e.OFFICE_WING_SINO,e.PROCESS_FLOW_STATUS_ID," +
                                "(case when e.DEPARTMENT_ID='TWAD' then (select office_name from com_mst_offices where office_id=e.JOINING_OFFICE_ID)" +
                                "else (select other_dept_office_name from hrm_mst_other_dept_offices where other_dept_id=e.DEPARTMENT_ID and other_dept_office_id=e.JOINING_OFFICE_ID)" +
                                "end" + ")as OFFICE_NAME,e.DEPARTMENT_ID," +
                                "e.OFFICE_GRADE,e.JOINING_YEAR,e.JOINING_OFFICE_ID from" +
                                " (" +
                                " select employee_id,employee_name,EMPLOYEE_INITIAL,date_of_birth,gpf_no from hrm_mst_employees" +
                                " where employee_id=?" + " ) a" +
                                " left outer join" + " (" +
                                " select employee_id,JOINING_REPORT_ID,JOINING_YEAR,JOINING_OFFICE_ID,DATE_OF_JOINING,FN_OR_AN,DESIGNATION_ID,POST_COUNTED_ID,REMARKS,COMPLETED_DATE,EMP_PRE_STATUS," +
                                " OFFICE_GRADE,DATE_EFFECTIVE_FROM_SESSION,JOINED_SUBDIVISION,SUBDIVISION_OFFICE_ID,OFFICE_WING_SINO,PROCESS_FLOW_STATUS_ID,office_id,DEPARTMENT_ID from HRM_EMP_JOIN_REPORTS" +
                                " WHERE (PROCESS_FLOW_STATUS_ID='CR' or PROCESS_FLOW_STATUS_ID='MD')" +
                                " ) e" + " on a.EMPLOYEE_ID=e.EMPLOYEE_ID" +
                                " left outer join" + " (" +
                                " select employee_id ,designation_id from hrm_emp_current_posting" +
                                " ) b" + " on e.employee_id=b.employee_id" +
                                " left outer join" + " (" +
                                " select designation_id,cadre_id from hrm_mst_designations" +
                                " ) c" +
                                " on b.designation_id=c.designation_id" +
                                " left outer join" + " (" +
                                " select cadre_id,cadre_name,cadre_short_name from hrm_mst_cadre" +
                                " ) d" + " on c.cadre_id=d.cadre_id";
                            System.out.println("sql=======>" + sql);
                            ps = connection.prepareStatement(sql);
                            ps.setInt(1, Integer.parseInt(strEmpName));
                            rs = ps.executeQuery();

                            int i = 0;
                            String strDob = "";
                            int join_year = 0;

                            while (rs.next()) {
                                System.out.println("emp name3" + strEmpName);

                                String strEName =
                                    rs.getString("Employee_Name");
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

                                String off_grade =
                                    rs.getString("OFFICE_GRADE");

                                int PostId = rs.getInt("POST_COUNTED_ID");


                                String strRemarks = rs.getString("REMARKS");
                                int wing = rs.getInt("OFFICE_WING_SINO");
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
                                    strJoindt =
                                            sd1[2] + "/" + sd1[1] + "/" + sd1[0];
                                }

                                Date strcdt = rs.getDate("COMPLETED_DATE");
                                if (strcdt == null) {
                                    strCompdt = "0";
                                } else {
                                    String[] sd1;
                                    sd1 =
 rs.getDate("COMPLETED_DATE").toString().split("-");
                                    strCompdt =
                                            sd1[2] + "/" + sd1[1] + "/" + sd1[0];
                                }
                                empstatus = rs.getString("EMP_PRE_STATUS");
                                compsession =
                                        rs.getString("DATE_EFFECTIVE_FROM_SESSION");

                                xml = xml + "<flag>success</flag>";

                                if (session.getAttribute("Admin") != null &&
                                    ((String)session.getAttribute("Admin")).equalsIgnoreCase("YES")) {
                                    xml = xml + "<admin>YES</admin>";
                                } else {
                                    xml = xml + "<admin>NO</admin>";
                                }

                                join_year = rs.getInt("JOINING_YEAR");
                                System.out.println("Joining year..." +
                                                   join_year);

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
   "</PostId><Remarks>" + strRemarks + "</Remarks>";
                                xml =
 xml + "<workingstatus>" + empstatus + "</workingstatus><completeddate>" +
   strCompdt + "</completeddate>";
                                xml =
 xml + "<compsession>" + compsession + "</compsession>";

                                xml = xml + "<wing>" + wing + "</wing>";
                                xml =
 xml + "<join_year>" + join_year + "</join_year>";


                                String optjoin =
                                    rs.getString("JOINED_SUBDIVISION");
                                int subdivoffid =
                                    rs.getInt("JOINING_OFFICE_ID");

                                xml =
 xml + "<optjoin>" + optjoin + "</optjoin>";
                                xml =
 xml + "<subdivoffid>" + subdivoffid + "</subdivoffid>";


                                try {
                                    System.out.println("dest" + DesigId);
                                    String sql3 =
                                        "select a.service_group_id from hrm_mst_service_group a inner join hrm_mst_designations b on b.service_group_id=a.service_group_id where b.designation_id=?";

                                    ps1 = connection.prepareStatement(sql3);
                                    ps1.setInt(1, DesigId);
                                    rs3 = ps1.executeQuery();
                                    while (rs3.next()) {

                                        int servgrp =
                                            rs3.getInt("SERVICE_GROUP_ID");
                                        System.out.println("serv" + servgrp);
                                        xml =
 xml + "<ServGroup>" + servgrp + "</ServGroup>";


                                        /***** 16-08-2006  **/
                                        // ps=connection.prepareStatement("select max(date_to) as maxtodate from HRM_EMP_SERVICE_DATA where EMPLOYEE_ID=?");
                                        ps =
  connection.prepareStatement("select DATE_EFFECTIVE_FROM as maxtodate from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?");
                                        ps.setInt(1,
                                                  Integer.parseInt(strEmpName));
                                        rs = ps.executeQuery();
                                        String maxtodate = "";
                                        if (rs.next()) {
                                            if (rs.getDate("maxtodate") !=
                                                null) {
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
                                    System.out.println("error in serv grp" +
                                                       se);
                                }
                            }
                            System.out.println("value of i:" + i);
                            if (i == 0) {

                                /*

                              try
                              {
                                  String sql2="select * from hrm_mst_employees where employee_id=?";
                                  ps2=connection.prepareStatement(sql2);
                                  ps2.setInt(1,Integer.parseInt(strEmpName));

                                   rs4=ps2.executeQuery();
                                  int j=0;
                                  while(rs4.next())
                                  {
                                     j++;
                                      String strEmpPref=rs4.getString("Employee_Prefix");
                                      String strEmpInit=rs4.getString("Employee_Initial");
                                        strEmpName=rs4.getString("Employee_Name");
                                       int strEmpGpf=rs4.getInt("GPF_NO");
                                      Date strdt=rs4.getDate("DATE_OF_BIRTH");
                                      if(strdt==null) {
                                          strDob="0";
                                      }
                                      else
                                      {
                                      String[] sd;
                                      sd=rs4.getDate("DATE_OF_BIRTH").toString().split("-");
                                      strDob=sd[2]+"/"+sd[1]+"/"+sd[0];
                                      }
                                      xml=sxml+"<flag>NoRecord</flag>";
                                      xml=xml+"<EmpPref>"+strEmpPref+"</EmpPref><EmpInit>"+strEmpInit+"</EmpInit><EmpName>"+strEmpName+"</EmpName><EmpGpf>"+strEmpGpf+"</EmpGpf><Dtofbirth>"+strDob+"</Dtofbirth>";
                                  }
                                  if(j==0)
                                  {
                                      xml=sxml+"<flag>NoValue</flag>";
                                  }

                              }
                              catch(Exception ae){System.out.println("Error in the second query" + ae);}
                              */
                                //xml=sxml+"<flag>failure2</flag>";  sxml
                                xml = xml + "<flag>failure2</flag>";
                            }
                            /*
                              else {
                                  System.out.println("EmpStatus in twad joining form:"+empstatus+"|");
                                  if(empstatus!=null && empstatus.equalsIgnoreCase("DPT"))
                                      xml=sxml+"<flag>failure3</flag>";
                                  else
                                      xml=sxml+"<flag>success</flag>"+xml;
                              }*/


                        } else {
                            results.close();
                            ps.close();

                            System.out.println("emp name2" + strEmpName);
                            /* String sql="SELECT A.EMPLOYEE_NAME ||decode(a.EMPLOYEE_INITIAL,null,' ','.'||a.EMPLOYEE_INITIAL) as  EMPLOYEE_NAME,A.GPF_NO,A.DATE_OF_BIRTH,B.JOINING_REPORT_ID, " +
                                        " B.DATE_OF_JOINING, B.FN_OR_AN, B.DESIGNATION_ID, B.POST_COUNTED_ID, " +
                                        " B.REMARKS,B.COMPLETED_DATE,B.EMP_PRE_STATUS,B.DATE_EFFECTIVE_FROM_SESSION,B.JOINED_SUBDIVISION,B.SUBDIVISION_OFFICE_ID,B.OFFICE_WING_SINO FROM HRM_MST_EMPLOYEES A " +
                                        " INNER JOIN HRM_EMP_JOIN_REPORTS B ON B.EMPLOYEE_ID=A.EMPLOYEE_ID " +
                                         " WHERE A.EMPLOYEE_ID=? and (B.PROCESS_FLOW_STATUS_ID='CR' or B.PROCESS_FLOW_STATUS_ID='MD') ";*/

                            String sql =
                                "select a.employee_id,a.employee_name ||decode(a.EMPLOYEE_INITIAL,null,' ','.'||a.EMPLOYEE_INITIAL) as  EMPLOYEE_NAME,a.date_of_birth,a.gpf_no,\n" +
                                " c.cadre_id,d.cadre_name,d.cadre_short_name,e.JOINING_REPORT_ID,e.DATE_OF_JOINING,e.FN_OR_AN,e.DESIGNATION_ID,e.POST_COUNTED_ID,e.REMARKS,\n" +
                                " e.COMPLETED_DATE,e.EMP_PRE_STATUS,e.DATE_EFFECTIVE_FROM_SESSION,e.JOINED_SUBDIVISION,e.SUBDIVISION_OFFICE_ID,\n" +
                                " e.OFFICE_WING_SINO,e.PROCESS_FLOW_STATUS_ID,\n" +
                                "(case when e.DEPARTMENT_ID='TWAD' then (select office_name from com_mst_offices where office_id=e.JOINING_OFFICE_ID)\n" +
                                "else (select other_dept_office_name from hrm_mst_other_dept_offices where other_dept_id=e.DEPARTMENT_ID and other_dept_office_id=e.JOINING_OFFICE_ID)\n" +
                                "end\n" +
                                ")as OFFICE_NAME,e.DEPARTMENT_ID,\n" +
                                "e.OFFICE_GRADE,e.JOINING_YEAR,e.JOINING_OFFICE_ID from\n" +
                                " (\n" +
                                " select employee_id,employee_name,EMPLOYEE_INITIAL,date_of_birth,gpf_no from hrm_mst_employees\n" +
                                " where employee_id=?\n" + " ) a\n" +
                                " left outer join\n" + " (\n" +
                                " select employee_id,JOINING_REPORT_ID,JOINING_YEAR,JOINING_OFFICE_ID,DATE_OF_JOINING,FN_OR_AN,DESIGNATION_ID,POST_COUNTED_ID,REMARKS,COMPLETED_DATE,EMP_PRE_STATUS,\n" +
                                " OFFICE_GRADE,DATE_EFFECTIVE_FROM_SESSION,JOINED_SUBDIVISION,SUBDIVISION_OFFICE_ID,OFFICE_WING_SINO,PROCESS_FLOW_STATUS_ID,office_id,DEPARTMENT_ID from HRM_EMP_JOIN_REPORTS\n" +
                                " WHERE (PROCESS_FLOW_STATUS_ID='CR' or PROCESS_FLOW_STATUS_ID='MD')\n" +
                                " ) e\n" +
                                " on a.EMPLOYEE_ID=e.EMPLOYEE_ID\n" +
                                " left outer join\n" + " (\n" +
                                " select employee_id ,designation_id from hrm_emp_current_posting\n" +
                                " ) b\n" +
                                " on e.employee_id=b.employee_id\n" +
                                " left outer join\n" + " (\n" +
                                " select designation_id,cadre_id from hrm_mst_designations\n" +
                                " ) c\n" +
                                " on b.designation_id=c.designation_id\n" +
                                " left outer join\n" + " (\n" +
                                " select cadre_id,cadre_name,cadre_short_name from hrm_mst_cadre\n" +
                                " ) d\n" + " on c.cadre_id=d.cadre_id\n";
                            System.out.println("sql==================>" + sql);
                            ps = connection.prepareStatement(sql);
                            ps.setInt(1, Integer.parseInt(strEmpName));
                            rs = ps.executeQuery();

                            int i = 0;
                            String strDob = "";
                            int join_year = 0;
                            while (rs.next()) {
                                System.out.println("emp name3" + strEmpName);

                                String strEName =
                                    rs.getString("Employee_Name");
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

                                String off_grade =
                                    rs.getString("OFFICE_GRADE");

                                int PostId = rs.getInt("POST_COUNTED_ID");


                                String strRemarks = rs.getString("REMARKS");
                                int wing = rs.getInt("OFFICE_WING_SINO");
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
                                    strJoindt =
                                            sd1[2] + "/" + sd1[1] + "/" + sd1[0];
                                }

                                Date strcdt = rs.getDate("COMPLETED_DATE");
                                if (strcdt == null) {
                                    strCompdt = "0";
                                } else {
                                    String[] sd1;
                                    sd1 =
 rs.getDate("COMPLETED_DATE").toString().split("-");
                                    strCompdt =
                                            sd1[2] + "/" + sd1[1] + "/" + sd1[0];
                                }
                                empstatus = rs.getString("EMP_PRE_STATUS");
                                compsession =
                                        rs.getString("DATE_EFFECTIVE_FROM_SESSION");

                                xml = xml + "<flag>success</flag>";

                                if (session.getAttribute("Admin") != null &&
                                    ((String)session.getAttribute("Admin")).equalsIgnoreCase("YES")) {
                                    xml = xml + "<admin>YES</admin>";
                                } else {
                                    xml = xml + "<admin>NO</admin>";
                                }

                                join_year = rs.getInt("JOINING_YEAR");
                                System.out.println("Joining year..." +
                                                   join_year);

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
   "</PostId><Remarks>" + strRemarks + "</Remarks>";
                                xml =
 xml + "<workingstatus>" + empstatus + "</workingstatus><completeddate>" +
   strCompdt + "</completeddate>";
                                xml =
 xml + "<compsession>" + compsession + "</compsession>";

                                xml = xml + "<wing>" + wing + "</wing>";
                                xml =
 xml + "<join_year>" + join_year + "</join_year>";


                                String optjoin =
                                    rs.getString("JOINED_SUBDIVISION");
                                int subdivoffid =
                                    rs.getInt("SUBDIVISION_OFFICE_ID");

                                xml =
 xml + "<optjoin>" + optjoin + "</optjoin>";
                                xml =
 xml + "<subdivoffid>" + subdivoffid + "</subdivoffid>";


                                try {
                                    System.out.println("dest" + DesigId);
                                    String sql3 =
                                        "select a.service_group_id from hrm_mst_service_group a inner join hrm_mst_designations b on b.service_group_id=a.service_group_id where b.designation_id=?";

                                    ps1 = connection.prepareStatement(sql3);
                                    ps1.setInt(1, DesigId);
                                    rs3 = ps1.executeQuery();
                                    while (rs3.next()) {

                                        int servgrp =
                                            rs3.getInt("SERVICE_GROUP_ID");
                                        System.out.println("serv" + servgrp);
                                        xml =
 xml + "<ServGroup>" + servgrp + "</ServGroup>";


                                        /***** 16-08-2006  **/
                                        // ps=connection.prepareStatement("select max(date_to) as maxtodate from HRM_EMP_SERVICE_DATA where EMPLOYEE_ID=?");
                                        ps =
  connection.prepareStatement("select DATE_EFFECTIVE_FROM as maxtodate from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?");
                                        ps.setInt(1,
                                                  Integer.parseInt(strEmpName));
                                        rs = ps.executeQuery();
                                        String maxtodate = "";
                                        if (rs.next()) {
                                            if (rs.getDate("maxtodate") !=
                                                null) {
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
                                    System.out.println("error in serv grp" +
                                                       se);
                                }
                            }
                            System.out.println("value of i:" + i);
                            if (i == 0) {

                                /*

                          try
                          {
                              String sql2="select * from hrm_mst_employees where employee_id=?";
                              ps2=connection.prepareStatement(sql2);
                              ps2.setInt(1,Integer.parseInt(strEmpName));

                               rs4=ps2.executeQuery();
                              int j=0;
                              while(rs4.next())
                              {
                                 j++;
                                  String strEmpPref=rs4.getString("Employee_Prefix");
                                  String strEmpInit=rs4.getString("Employee_Initial");
                                    strEmpName=rs4.getString("Employee_Name");
                                   int strEmpGpf=rs4.getInt("GPF_NO");
                                  Date strdt=rs4.getDate("DATE_OF_BIRTH");
                                  if(strdt==null) {
                                      strDob="0";
                                  }
                                  else
                                  {
                                  String[] sd;
                                  sd=rs4.getDate("DATE_OF_BIRTH").toString().split("-");
                                  strDob=sd[2]+"/"+sd[1]+"/"+sd[0];
                                  }
                                  xml=sxml+"<flag>NoRecord</flag>";
                                  xml=xml+"<EmpPref>"+strEmpPref+"</EmpPref><EmpInit>"+strEmpInit+"</EmpInit><EmpName>"+strEmpName+"</EmpName><EmpGpf>"+strEmpGpf+"</EmpGpf><Dtofbirth>"+strDob+"</Dtofbirth>";
                              }
                              if(j==0)
                              {
                                  xml=sxml+"<flag>NoValue</flag>";
                              }

                          }
                          catch(Exception ae){System.out.println("Error in the second query" + ae);}
                          */
                                // xml=sxml+"<flag>failure2</flag>";       sxml
                                xml = xml + "<flag>failure2</flag>";
                            }
                            /*
                          else {
                              System.out.println("EmpStatus in twad joining form:"+empstatus+"|");
                              if(empstatus!=null && empstatus.equalsIgnoreCase("DPT"))
                                  xml=sxml+"<flag>failure3</flag>";
                              else
                                  xml=sxml+"<flag>success</flag>"+xml;
                          }*/

                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Exception " + e);
                // xml=sxml+"<flag>failure</flag>";       sxml
                xml = xml + "<flag>failure</flag>";
            }
            //}
            xml = xml + "</response>";

        }


        else if (strCommand.equalsIgnoreCase("Update")) {

            System.out.println("inside update");

            xml = "<response><command>Update</command>";

            int strEmpName = 0;
            int strDesigId = 0;
            int PostId = 0;
            String strRemarks = "", empstatus = "", compsession = "", doj =
                "", strgrade = "";
            int JYear = 0;
            int OffId = 0;
            int JoinId = 0;
            String radVal = "";
            java.util.Date d = null;
            java.sql.Date cdt = null;
            String optjoin = "", dept_id = "";
            int currentofficecode = 0;
            java.sql.Date f = null;
            int jyear = 0;

            session = request.getSession(false);
            String updatedby = (String)session.getAttribute("UserId");
            long l = System.currentTimeMillis();
            Timestamp ts = new Timestamp(l);
            int wing = 0;
            try {
                try {


                    strEmpName =
                            Integer.parseInt(request.getParameter("txtEmpId"));
                    OffId = Integer.parseInt(request.getParameter("txtOffId"));
                    strDesigId =
                            Integer.parseInt(request.getParameter("cmbdes"));
                    PostId =
                            Integer.parseInt(request.getParameter("comPostTow"));
                    JYear =
Integer.parseInt(request.getParameter("txtjoin_yr")); //////see this
                    JoinId = Integer.parseInt(request.getParameter("JoinId"));
                    radVal = request.getParameter("radFNAN");
                    dept_id = request.getParameter("dept_id");
                    strRemarks = request.getParameter("txtRemarks");
                    doj = request.getParameter("txtDOJ");
                    strgrade = request.getParameter("txtgrade");

                    System.out.println("before converting date");
                    String dateString = doj;
                    SimpleDateFormat dateFormat =
                        new SimpleDateFormat("dd/MM/yyyy");
                    java.util.Date dat;
                    dat = dateFormat.parse(doj.trim());
                    dateFormat.applyPattern("yyyy-MM-dd");
                    dateString = dateFormat.format(dat);
                    f = java.sql.Date.valueOf(dateString);

                    jyear = Integer.parseInt(request.getParameter("jyear"));


                    System.out.println("strEmpName" + strEmpName);
                    System.out.println("OffId" + OffId);
                    System.out.println("strDesigId" + strDesigId);
                    System.out.println("PostId" + PostId);
                    System.out.println("JYear" + JYear);
                    System.out.println("JoinId" + JoinId);
                    System.out.println("radVal" + radVal);
                    System.out.println("strRemarks" + strRemarks);
                    System.out.println("doj" + f);
                    System.out.println("strgrade" + strgrade);
                    System.out.println("join year..." + jyear);

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


                    /* if(empstatus!=null && !empstatus.equalsIgnoreCase("WKG")) {
                              String strDOC=request.getParameter("compdate");
                              SimpleDateFormat  dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                              d = dateFormat.parse(strDOC.trim());
                              dateFormat.applyPattern("yyyy-MM-dd");
                              String dateString = dateFormat.format(d);
                              cdt = java.sql.Date.valueOf(dateString);
                          }*/

                    // compsession=request.getParameter("compsession");
                    //System.out.println("compsession...."+compsession);

                } catch (Exception e) {
                    System.out.println("exce **** " + e);
                }


                /* String sql="Update HRM_EMP_JOIN_REPORTS set FN_OR_AN=?,DESIGNATION_ID=?,POST_COUNTED_ID=?," +
                " REMARKS=?,PROCESS_FLOW_STATUS_ID=?,JOINED_SUBDIVISION=?,SUBDIVISION_OFFICE_ID=?,UPDATED_BY_USER_ID=?," +
                " UPDATED_DATE=?,OFFICE_WING_SINO=? where EMPLOYEE_ID=? and JOINING_REPORT_ID=? and" +
                " OFFICE_ID=? and JOINING_YEAR=?";

                ps=connection.prepareStatement(sql);
                  ps.setString(1,radVal);
                  ps.setInt(2,strDesigId);
                  ps.setInt(3,PostId);
                  ps.setString(4,strRemarks);
                  ps.setString(5,"MD");
                //  ps.setDate(6,cdt);
                //  ps.setString(7,compsession);
                 ps.setString(6,optjoin);
                 ps.setInt(7,currentofficecode);

                  ps.setString(8,updatedby);
                  ps.setTimestamp(9,ts);

                  ps.setInt(10,wing);

                  ps.setInt(11,strEmpName);
                  ps.setInt(12,JoinId);
                  ps.setInt(13,OffId);
                  ps.setInt(14,JYear);
                  */
                int i = 0;

                /*

                  String sql="update hrm_emp_join_reports set office_id=?,date_of_joining=?," +
                  " fn_or_an=?,designation_id=?,post_counted_id=?,remarks=?,process_flow_status_id=?," +
                  " joined_subdivision=?,subdivision_office_id=?,updated_by_user_id=?," +
                  " updated_date=?,office_grade=?,JOINING_OFFICE_ID=? where employee_id=? and joining_report_id=? and joining_year=?";

                  ps=connection.prepareStatement(sql);

                  ps.setInt(1,OffId);
                  ps.setDate(2,f);
                  ps.setString(3,radVal);
                  ps.setInt(4,strDesigId);
                  ps.setInt(5,PostId);
                  ps.setString(6,strRemarks);
                  ps.setString(7,"MD");
                  ps.setString(8,optjoin);
                  ps.setInt(9,currentofficecode);
                  ps.setString(10,updatedby);
                  ps.setTimestamp(11,ts);
                  ps.setString(12,strgrade);
                  ps.setInt(13,currentofficecode);
                  ps.setInt(14,strEmpName);
                  ps.setInt(15,JoinId);
                  ps.setInt(16,JYear);
                  */

                String sql =
                    "update hrm_emp_join_reports set date_of_joining=?," +
                    " fn_or_an=?,designation_id=?,post_counted_id=?,remarks=?,process_flow_status_id=?," +
                    " joined_subdivision=?,subdivision_office_id=?,updated_by_user_id=?," +
                    " updated_date=?,office_grade=?,JOINING_OFFICE_ID=?,joining_year=?,department_id=? where employee_id=? and joining_report_id=?";

                ps = connection.prepareStatement(sql);

                //ps.setInt(1,OffId);
                ps.setDate(1, f);
                ps.setString(2, radVal);
                ps.setInt(3, strDesigId);
                ps.setInt(4, PostId);
                ps.setString(5, strRemarks);
                ps.setString(6, "MD");
                ps.setString(7, optjoin);
                ps.setInt(8, currentofficecode);
                ps.setString(9, updatedby);
                ps.setTimestamp(10, ts);
                ps.setString(11, strgrade);
                ps.setInt(12, currentofficecode);
                ps.setInt(13, JYear);
                ps.setString(14, dept_id);
                ps.setInt(15, strEmpName);
                ps.setInt(16, JoinId);


                i = ps.executeUpdate();
                System.out.println(i);
                xml = xml + "<flag>success</flag>";
                ps.close();
            } catch (Exception e) {
                System.out.println("exception in the updation" +
                                   e.getMessage());
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }


        System.out.println("xml is : " + xml);
        pw.write(xml);
        pw.flush();
        pw.close();


    }
}
