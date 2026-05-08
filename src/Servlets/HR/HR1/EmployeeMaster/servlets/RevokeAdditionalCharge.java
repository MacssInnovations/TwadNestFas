package Servlets.HR.HR1.EmployeeMaster.servlets;

import Servlets.Security.classes.UserProfile;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class RevokeAdditionalCharge extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);
        Connection con = null;
        try {

            ResourceBundle rsb =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";

            String strDriver = rsb.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rsb.getString("Config.DSN");
            String strhostname = rsb.getString("Config.HOST_NAME");
            String strportno = rsb.getString("Config.PORT_NUMBER");
            String strsid = rsb.getString("Config.SID");
            String strdbusername = rsb.getString("Config.USER_NAME");
            String strdbpassword = rsb.getString("Config.PASSWORD");

            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection


            Class.forName(strDriver.trim());
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
        } catch (Exception e) {
            System.out.println("Exception in connection..." + e);
        }
        ResultSet rs = null;
        PreparedStatement ps = null;
        Statement st = null;

        int strcode = 0, slno = 0, designation = 0, officeid = 0, statusid = 0;
        Date dtfrom = null, dtto = null;
        String dtfromsession = "", dttosession = "", statusdetail =
            "", remarks = "", deptid = "", todate = "";
        //String strname="";
        String xml = "";
        String strCommand = "";
        Calendar c;

        PrintWriter out = response.getWriter();
        /*   HttpSession session=request.getSession(false);
            try
            {

                if(session==null)
                {
                    System.out.println(request.getContextPath()+"/index.jsp");
                    response.sendRedirect(request.getContextPath()+"/index.jsp");
                }
                System.out.println(session);

            }catch(Exception e)
            {
            System.out.println("Redirect Error :"+e);
            }*/

        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");

        try {
            strCommand = request.getParameter("Command");
            System.out.println("assign....." + strCommand);

            strcode = Integer.parseInt(request.getParameter("txtEmployeeid"));
            System.out.println("assign..... Code::" + strcode);


        }

        catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }

        if (strCommand.equalsIgnoreCase("loademp")) {
            xml = "<response><command>loademp</command>";
            try {
                HttpSession session = request.getSession(false);
                UserProfile up = null;
                up = (UserProfile)session.getAttribute("UserProfile");
                boolean flag = true;
                ps =
  con.prepareStatement("SELECT EMPLOYEE_ID FROM HRM_MST_EMPLOYEES WHERE EMPLOYEE_ID=?");
                ps.setInt(1, strcode);
                rs = ps.executeQuery();
                if (!rs.next()) {
                    xml = xml + "<flag>failure</flag>";
                    flag = false;
                } else if (up.getEmployeeId() != strcode) {
                    int OfficeId = 0;
                    String sql =
                        "select CONTROLLING_OFFICE_ID from HRM_EMP_CONTROLLING_OFFICE where employee_id=?";
                    ps = con.prepareStatement(sql);
                    ps.setInt(1, strcode);
                    rs = ps.executeQuery();

                    if (rs.next()) {
                        OfficeId = rs.getInt("CONTROLLING_OFFICE_ID");
                    }
                    System.out.println("Office Id:" + OfficeId);
                    if (OfficeId != 0) {
                        sql =
 "select OFFICE_ID  from HRM_EMP_CURRENT_POSTING where employee_id=?";
                        ps = con.prepareStatement(sql);
                        ps.setInt(1, up.getEmployeeId());
                        rs = ps.executeQuery();
                        if (rs.next()) {
                            int offid = rs.getInt("OFFICE_ID");
                            if (offid != OfficeId) {
                                System.out.println("Admin Session:" +
                                                   session.getAttribute("Admin"));
                                if (session.getAttribute("Admin") == null ||
                                    !((String)session.getAttribute("Admin")).equalsIgnoreCase("YES")) {
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
                } else {

                    //xml=xml+"<flag>failure4</flag>";
                    // flag=false;
                }

                if (flag) {

                    //con.setAutoCommit(false);
                    ps =
  con.prepareStatement("select PROCESS_FLOW_STATUS_ID from HRM_EMP_CURRENT_POSTING_TMP  where EMPLOYEE_ID=? and PROCESS_FLOW_STATUS_ID!='DL'");
                    ps.setInt(1, strcode);
                    rs = ps.executeQuery();

                    if (rs.next()) {

                        String status = rs.getString("PROCESS_FLOW_STATUS_ID");
                        if (status.equalsIgnoreCase("CR") ||
                            status.equalsIgnoreCase("MD")) {
                            xml = xml + "<flag>exists</flag>";
                        } else if (status.equalsIgnoreCase("FR")) {
                            xml = xml + "<flag>freezed</flag>";
                        }

                    } else {
                        ps =
  con.prepareStatement("select EMPLOYEE_NAME ||decode(EMPLOYEE_INITIAL,null,' ','.'||EMPLOYEE_INITIAL) as  EMPLOYEE_NAME ,GPF_NO from HRM_MST_EMPLOYEES  where EMPLOYEE_ID=?");
                        ps.setInt(1, strcode);
                        rs = ps.executeQuery();

                        if (rs.next()) {

                            xml =
 xml + "<ename>" + rs.getString("EMPLOYEE_NAME") + "</ename>";
                            xml =
 xml + "<egpf>" + rs.getLong("GPF_NO") + "</egpf>";
                            rs.close();
                            ps.close();
                            xml = xml + "<flag>success</flag>";
                        } else {
                            xml = xml + "<flag>failure</flag>";
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("catch........" + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }
        if (strCommand.equalsIgnoreCase("loadempedit")) {
            xml = "<response><command>loadempedit</command>";
            try {

                HttpSession session = request.getSession(false);
                UserProfile up = null;
                up = (UserProfile)session.getAttribute("UserProfile");

                int offceid = Integer.parseInt(request.getParameter("offid"));
                System.out.println("off ids........." + offceid);
                boolean flag = true;
                ps =
  con.prepareStatement("SELECT EMPLOYEE_ID FROM HRM_MST_EMPLOYEES WHERE EMPLOYEE_ID=?");
                ps.setInt(1, strcode);
                rs = ps.executeQuery();
                if (!rs.next()) {
                    xml = xml + "<flag>failure</flag>";
                    flag = false;
                }
                /* else  if(up.getEmployeeId()!=strcode)
                     {
                                   int OfficeId=0;
                                   String sql="select CONTROLLING_OFFICE_ID from HRM_EMP_CONTROLLING_OFFICE where employee_id=?";
                                   ps=con.prepareStatement(sql);
                                   ps.setInt(1,strcode);
                                   rs=ps.executeQuery();

                                  if(rs.next()) {
                                       OfficeId=rs.getInt("CONTROLLING_OFFICE_ID");
                                   }

                                   if(OfficeId!=0)
                                   {
                                          sql="select OFFICE_ID  from HRM_EMP_CURRENT_POSTING where employee_id=?";
                                           ps=con.prepareStatement(sql);
                                           ps.setInt(1,up.getEmployeeId());
                                           rs=ps.executeQuery();
                                            if(rs.next()) {
                                               int offid=rs.getInt("OFFICE_ID");
                                               if(offid!=OfficeId)
                                               {
                                                   System.out.println("Admin Session:"+session.getAttribute("Admin"));
                                                           if(session.getAttribute("Admin")==null || !((String)session.getAttribute("Admin")).equalsIgnoreCase("YES"))
                                                           {//response.sendRedirect(request.getContextPath()+"/org/Library/jsps/Messenger.jsp?message=" + "Can not see profile. Because Employee Id "+strEmpId+" is not under your Office!");
                                                            xml=xml+"<flag>failure1</flag>";
                                                            flag=false;
                                                           }
                                               }
                                           }
                                           else
                                           {
                                                  // response.sendRedirect(request.getContextPath()+"/org/Library/jsps/Messenger.jsp?message=" + "Current Posting is not available. Can not see the profile for "+strEmpId+"!");
                                                   xml=xml+"<flag>failure2</flag>";
                                               flag=false;
                                           }

                                   }
                                   else{
                                       //if(session.getAttribute("Admin")==null || !((String)session.getAttribute("Admin")).equalsIgnoreCase("YES"))
                                       {
                                       xml=xml+"<flag>failure3</flag>";
                                       flag=false;
                                       }
                                   }
                     }
                     else {

                       //  xml=xml+"<flag>failure4</flag>";
                        //flag=false;
                     }*/

                if (flag) {
                    //con.setAutoCommit(false);
                    System.out.println("ok1");
                    //ps=con.prepareStatement("select HRM_EMP_CURRENT_POSTING_TMP.EMPLOYEE_ID, EMPLOYEE_NAME||decode(EMPLOYEE_INITIAL,null,' ','.'||EMPLOYEE_INITIAL) as  EMPLOYEE_NAME ,GPF_NO, OFFICE_ID,HRM_EMP_CURRENT_POSTING_TMP.DESIGNATION_ID,DATE_EFFECTIVE_FROM, HRM_EMP_CURRENT_POSTING_TMP.REMARKS,OFFICE_GRADE,DEPARTMENT_ID,HRM_EMP_CURRENT_POSTING_TMP.PROCESS_FLOW_STATUS_ID, EMPLOYEE_STATUS_ID,LEAVE_TYPE_CODE,DATE_EFFECTIVE_FROM_SESSION,SERVICE_GROUP_ID from HRM_EMP_CURRENT_POSTING_TMP,HRM_MST_EMPLOYEES,HRM_MST_DESIGNATIONS where HRM_MST_DESIGNATIONS.DESIGNATION_ID=HRM_EMP_CURRENT_POSTING_TMP.DESIGNATION_ID and HRM_MST_EMPLOYEES.EMPLOYEE_ID=HRM_EMP_CURRENT_POSTING_TMP.EMPLOYEE_ID and  HRM_EMP_CURRENT_POSTING_TMP.EMPLOYEE_ID=?"); // and  (HRM_EMP_CURRENT_POSTING_TMP.PROCESS_FLOW_STATUS_ID='CR' or HRM_EMP_CURRENT_POSTING_TMP.PROCESS_FLOW_STATUS_ID='MD')" );
                    //ps=con.prepareStatement("select HRM_EMP_CURRENT_POSTING.EMPLOYEE_ID, EMPLOYEE_NAME||decode(EMPLOYEE_INITIAL,null,' ',EMPLOYEE_INITIAL) as  EMPLOYEE_NAME ,GPF_NO, OFFICE_ID,HRM_EMP_CURRENT_POSTING.DESIGNATION_ID,DATE_EFFECTIVE_FROM, HRM_EMP_CURRENT_POSTING.REMARKS,OFFICE_GRADE,DEPARTMENT_ID,HRM_EMP_CURRENT_POSTING.PROCESS_FLOW_STATUS_ID, EMPLOYEE_STATUS_ID,LEAVE_TYPE_CODE,DATE_EFFECTIVE_FROM_SESSION,SERVICE_GROUP_ID from HRM_EMP_CURRENT_POSTING,HRM_MST_EMPLOYEES,HRM_MST_DESIGNATIONS where HRM_MST_DESIGNATIONS.DESIGNATION_ID=HRM_EMP_CURRENT_POSTING.DESIGNATION_ID and HRM_MST_EMPLOYEES.EMPLOYEE_ID=HRM_EMP_CURRENT_POSTING.EMPLOYEE_ID and  HRM_EMP_CURRENT_POSTING.EMPLOYEE_ID=? and  HRM_EMP_CURRENT_POSTING.PROCESS_FLOW_STATUS_ID!='FR'" );

                    ps =
  con.prepareStatement("select a.EMPLOYEE_ID, b.EMPLOYEE_NAME||decode(b.EMPLOYEE_INITIAL,null,' ','.'||b.EMPLOYEE_INITIAL) as  EMPLOYEE_NAME , " +
                       " b.GPF_NO, a.OFFICE_ID,a.DESIGNATION_ID,a.DATE_EFFECTIVE_FROM, " +
                       " a.REMARKS,OFFICE_GRADE,DEPARTMENT_ID,a.PROCESS_FLOW_STATUS_ID, " +
                       " a.EMPLOYEE_STATUS_ID,a.LEAVE_TYPE_CODE,a.DATE_EFFECTIVE_FROM_SESSION,a.DATE_EFFECTIVE_TO_SESSION,a.CLOSURE_DATE,c.SERVICE_GROUP_ID,a.ADDL_CHARGE_STATUS	" +
                       " from HRM_EMP_ADDL_CHARGE a " +
                       " inner join HRM_MST_EMPLOYEES b on b.employee_id=a.employee_id  " +
                       " left outer join HRM_MST_DESIGNATIONS c on c.DESIGNATION_ID=a.DESIGNATION_ID " +
                       " where a.EMPLOYEE_ID=? and a.office_id=? and a.ADDL_CHARGE_STATUS not in ('CL','DL') ");
                    ps.setInt(1, strcode);
                    ps.setInt(2, offceid);
                    rs = ps.executeQuery();
                    boolean oldrec = false;
                    String status = null;
                    if (!rs.next()) {
                        //  data in temporary table is not available
                        //ps=con.prepareStatement("select HRM_EMP_CURRENT_POSTING_TMP.EMPLOYEE_ID, EMPLOYEE_NAME||decode(EMPLOYEE_INITIAL,null,' ',EMPLOYEE_INITIAL) as  EMPLOYEE_NAME ,GPF_NO, OFFICE_ID,HRM_EMP_CURRENT_POSTING_TMP.DESIGNATION_ID,DATE_EFFECTIVE_FROM, HRM_EMP_CURRENT_POSTING_TMP.REMARKS,OFFICE_GRADE,DEPARTMENT_ID,HRM_EMP_CURRENT_POSTING_TMP.PROCESS_FLOW_STATUS_ID, EMPLOYEE_STATUS_ID,LEAVE_TYPE_CODE,DATE_EFFECTIVE_FROM_SESSION,SERVICE_GROUP_ID from HRM_EMP_CURRENT_POSTING_TMP,HRM_MST_EMPLOYEES,HRM_MST_DESIGNATIONS where HRM_MST_DESIGNATIONS.DESIGNATION_ID=HRM_EMP_CURRENT_POSTING_TMP.DESIGNATION_ID and HRM_MST_EMPLOYEES.EMPLOYEE_ID=HRM_EMP_CURRENT_POSTING_TMP.EMPLOYEE_ID and  HRM_EMP_CURRENT_POSTING_TMP.EMPLOYEE_ID=? and  (HRM_EMP_CURRENT_POSTING_TMP.PROCESS_FLOW_STATUS_ID='CR' or HRM_EMP_CURRENT_POSTING_TMP.PROCESS_FLOW_STATUS_ID='MD')" );
                        ps =
  con.prepareStatement("select HRM_EMP_CURRENT_POSTING.EMPLOYEE_ID, EMPLOYEE_NAME||decode(EMPLOYEE_INITIAL,null,' ','.'||EMPLOYEE_INITIAL) as  EMPLOYEE_NAME ,GPF_NO, HRM_EMP_CURRENT_POSTING.OFFICE_ID,HRM_EMP_CURRENT_POSTING.DESIGNATION_ID,HRM_EMP_CURRENT_POSTING.DATE_EFFECTIVE_FROM, HRM_EMP_CURRENT_POSTING.REMARKS,OFFICE_GRADE,DEPARTMENT_ID,HRM_EMP_CURRENT_POSTING.PROCESS_FLOW_STATUS_ID, EMPLOYEE_STATUS_ID,LEAVE_TYPE_CODE,DATE_EFFECTIVE_FROM_SESSION,SERVICE_GROUP_ID,w.OFFICE_WING_SINO from HRM_EMP_CURRENT_WING w,HRM_EMP_CURRENT_POSTING,HRM_MST_EMPLOYEES,HRM_MST_DESIGNATIONS where HRM_MST_DESIGNATIONS.DESIGNATION_ID=HRM_EMP_CURRENT_POSTING.DESIGNATION_ID and HRM_MST_EMPLOYEES.EMPLOYEE_ID=HRM_EMP_CURRENT_POSTING.EMPLOYEE_ID and  HRM_EMP_CURRENT_POSTING.EMPLOYEE_ID=? and  HRM_EMP_CURRENT_POSTING.PROCESS_FLOW_STATUS_ID!='FR' and HRM_EMP_CURRENT_POSTING.EMPLOYEE_ID=w.EMPLOYEE_ID(+)");
                        ps.setInt(1, strcode);
                        rs = ps.executeQuery();
                        if (rs.next()) {
                            oldrec = true; // main table is available
                        } else {
                            oldrec = false; // main table is not available
                        }
                    } else {
                        oldrec = true; //  data in temporary table is available
                    }
                    if (oldrec == true) {
                        System.out.println("ok2");
                        //if(rs.next())
                        {
                            System.out.println("here 1");

                            xml =
 xml + "<EMPLOYEE_ID>" + rs.getInt("EMPLOYEE_ID") + "</EMPLOYEE_ID>";
                            xml =
 xml + "<EMPLOYEE_NAME>" + rs.getString("EMPLOYEE_NAME") + "</EMPLOYEE_NAME>";
                            xml =
 xml + "<GPF_NO>" + rs.getInt("GPF_NO") + "</GPF_NO>";
                            xml =
 xml + "<OFFICE_ID>" + rs.getInt("OFFICE_ID") + "</OFFICE_ID>";
                            xml =
 xml + "<DESIGNATION_ID>" + rs.getInt("DESIGNATION_ID") + "</DESIGNATION_ID>";
                            xml =
 xml + "<addlstatus>" + rs.getString("ADDL_CHARGE_STATUS") + "</addlstatus>";
                            //    xml=xml+"<closure_date>"+rs.getString("CLOSURE_DATE")+"</closure_date>";
                            xml =
 xml + "<session_to>" + rs.getString("DATE_EFFECTIVE_TO_SESSION") +
   "</session_to>";
                            System.out.println("here 2");
                            if (rs.getDate("DATE_EFFECTIVE_FROM") != null) {
                                String[] sd =
                                    rs.getDate("DATE_EFFECTIVE_FROM").toString().split("-");
                                String od = sd[2] + "/" + sd[1] + "/" + sd[0];
                                xml =
 xml + "<DATE_EFFECTIVE_FROM>" + od + "</DATE_EFFECTIVE_FROM>";
                            } else {
                                xml =
 xml + "<DATE_EFFECTIVE_FROM>" + rs.getDate("DATE_EFFECTIVE_FROM") +
   "</DATE_EFFECTIVE_FROM>";

                            }
                            System.out.println("here 2");
                            if (rs.getDate("CLOSURE_DATE") != null) {
                                String[] sd =
                                    rs.getDate("CLOSURE_DATE").toString().split("-");
                                String od = sd[2] + "/" + sd[1] + "/" + sd[0];
                                xml =
 xml + "<CLOSURE_DATE>" + od + "</CLOSURE_DATE>";
                            } else {
                                xml =
 xml + "<CLOSURE_DATE>" + rs.getDate("CLOSURE_DATE") + "</CLOSURE_DATE>";

                            }
                            System.out.println("here 3");
                            xml =
 xml + "<REMARKS>" + rs.getString("REMARKS") + "</REMARKS>";
                            xml =
 xml + "<OFFICE_GRADE>" + rs.getString("OFFICE_GRADE") + "</OFFICE_GRADE>";
                            xml =
 xml + "<DEPARTMENT_ID>" + rs.getString("DEPARTMENT_ID") + "</DEPARTMENT_ID>";
                            xml =
 xml + "<PROCESS_FLOW_STATUS_ID>" + rs.getString("PROCESS_FLOW_STATUS_ID") +
   "</PROCESS_FLOW_STATUS_ID>";
                            xml =
 xml + "<EMPLOYEE_STATUS_ID>" + rs.getString("EMPLOYEE_STATUS_ID") +
   "</EMPLOYEE_STATUS_ID>";
                            xml =
 xml + "<LEAVE_TYPE>" + rs.getString("LEAVE_TYPE_CODE") + "</LEAVE_TYPE>";
                            xml =
 xml + "<DATE_EFFECTIVE_FROM_SESSION>" + rs.getString("DATE_EFFECTIVE_FROM_SESSION") +
   "</DATE_EFFECTIVE_FROM_SESSION>";
                            xml =
 xml + "<DEPARTMENT_ID>" + rs.getString("DEPARTMENT_ID") + "</DEPARTMENT_ID>";
                            xml =
 xml + "<SERVICE_GROUP_ID>" + rs.getInt("SERVICE_GROUP_ID") +
   "</SERVICE_GROUP_ID>";

                            status = rs.getString("PROCESS_FLOW_STATUS_ID");

                            int wing = 0;
                            System.out.println("here 4");
                            /*    if(rs.getString("OFFICE_WING_SINO")!=null) {
                                                wing=Integer.parseInt(rs.getString("OFFICE_WING_SINO"));
                                            }*/
                            xml = xml + "<WING>" + wing + "</WING>";
                            System.out.println("here 5");
                            /* if(status!=null && status.equalsIgnoreCase("FR"))
                                             {
                                                        xml=xml+"<flag>freezed</flag>";
                                             }
                                             else {
                                                 {
                                                            xml=xml+"<flag>success</flag>";
                                                 }
                                             }*/
                            System.out.println("here is ok1");
                            if (rs.getString("EMPLOYEE_STATUS_ID") != null &&
                                rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("STU")) {

                                /*   sql="select CONTROLLING_OFFICE_ID from HRM_EMP_CONTROLLING_OFFICE where employee_id=?";
                                                  ps=con.prepareStatement(sql);
                                                  ps.setInt(1,strcode);
                                                  rs=ps.executeQuery();
                                                  int OfficeId=0;
                                                  if(rs.next()) {
                                                      OfficeId=rs.getInt("CONTROLLING_OFFICE_ID");
                                                  }*/

                                System.out.println("ok123");
                                ps =
  con.prepareStatement("select  max(JOINING_REPORT_ID)  rep_id,INSTITUTION_NAME,INSTITUTION_LOCATION,COURSE_NAME from HRM_EMP_STU_JOIN_REPORTS where EMPLOYEE_ID=? and (PROCESS_FLOW_STATUS_ID='CR' or PROCESS_FLOW_STATUS_ID='MD' or PROCESS_FLOW_STATUS_ID='FR') group by INSTITUTION_NAME,INSTITUTION_LOCATION,COURSE_NAME");
                                ps.setInt(1, strcode);
                                //ps.setInt(2,OfficeId);
                                rs = ps.executeQuery();
                                if (rs.next()) {
                                    xml =
 xml + "<INSTITUTION_NAME>" + rs.getString("INSTITUTION_NAME") +
   "</INSTITUTION_NAME>";
                                    xml =
 xml + "<INSTITUTION_LOCATION>" + rs.getString("INSTITUTION_LOCATION") +
   "</INSTITUTION_LOCATION>";
                                    xml =
 xml + "<COURSE_NAME>" + rs.getString("COURSE_NAME") + "</COURSE_NAME>";
                                }
                            }
                            System.out.println("here is ok2");

                            xml = xml + "<flag>success</flag>";
                            System.out.println("test");
                            if (session.getAttribute("Admin") != null &&
                                ((String)session.getAttribute("Admin")).equalsIgnoreCase("YES")) {
                                xml = xml + "<admin>YES</admin>";
                            } else {
                                xml = xml + "<admin>NO</admin>";
                            }


                        }
                        /*   else
                                        {
                                            ps=con.prepareStatement("select PROCESS_FLOW_STATUS_ID from HRM_EMP_CURRENT_POSTING_TMP  where EMPLOYEE_ID=? and PROCESS_FLOW_STATUS_ID='FR'" );
                                            ps.setInt(1,strcode);
                                            rs=ps.executeQuery();

                                            if(rs.next()) {

                                                   String status=rs.getString("PROCESS_FLOW_STATUS_ID");
                                                    xml=xml+"<flag>freezed</flag>";

                                            }
                                            else
                                            {
                                                    xml=xml+"<flag>failure</flag>";
                                            }
                                        }
                                        */
                    } else { // there is no old record is available so we have to create a new record

                        //  checking for freezed record
                        /* ps=con.prepareStatement("select PROCESS_FLOW_STATUS_ID from HRM_EMP_CURRENT_POSTING_TMP  where EMPLOYEE_ID=? and PROCESS_FLOW_STATUS_ID='FR'" );
                                 ps.setInt(1,strcode);
                                 rs=ps.executeQuery();

                                 if(rs.next()) {

                                        //String status=rs.getString("PROCESS_FLOW_STATUS_ID");
                                         xml=xml+"<flag>freezed</flag>";

                                 }
                                 else */
                        {
                            //xml=xml+"<flag>failure</flag>";
                            // load the employee basic information to crate a new record
                            ps =
  con.prepareStatement("select EMPLOYEE_NAME||decode(EMPLOYEE_INITIAL,null,' ','.'||EMPLOYEE_INITIAL) as  EMPLOYEE_NAME ,GPF_NO from HRM_MST_EMPLOYEES  where EMPLOYEE_ID=?");
                            ps.setInt(1, strcode);
                            rs = ps.executeQuery();

                            if (rs.next()) {

                                xml =
 xml + "<ename>" + rs.getString("EMPLOYEE_NAME") + "</ename>";
                                xml =
 xml + "<egpf>" + rs.getLong("GPF_NO") + "</egpf>";
                                rs.close();
                                ps.close();
                                xml = xml + "<flag>success1</flag>";
                            } else {
                                xml = xml + "<flag>failure</flag>";
                            }

                        }
                    }
                }

            } catch (Exception e) {
                System.out.println("catch........" + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }

        if (strCommand.equalsIgnoreCase("loadOffices")) {
            int cnt = 0;
            xml = "<response><command>loadOffices</command>";
            try {

                HttpSession session = request.getSession(false);
                UserProfile up = null;
                up = (UserProfile)session.getAttribute("UserProfile");
                boolean flag = true;
                ps =
  con.prepareStatement("select distinct office_id,office_name,processflow from" +
                       " (" +
                       " SELECT office_id ,PROCESS_FLOW_STATUS_ID as processflow FROM hrm_emp_addl_charge WHERE EMPLOYEE_ID=? and PROCESS_FLOW_STATUS_ID not in('DL') and ADDL_CHARGE_STATUS not in ('CL','DL')  " +
                       " )a" + " left outer join " + " (" +
                       " select office_id as offid,office_name from com_mst_offices" +
                       " )b" + " on a.office_id=b.offid");
                ps.setInt(1, strcode);
                rs = ps.executeQuery();
                System.out.println("hello");
                // xml=xml+"<options>";
                int FR = 0;
                System.out.println("coming here--->");
                while (rs.next()) {
                    xml =
 xml + "<offid>" + rs.getInt("office_id") + "</offid>";
                    xml =
 xml + "<offname>" + rs.getString("office_name") + "</offname>";

                    if (rs.getString("processflow").equals("FR"))
                        FR = 1;
                    cnt++;
                }
                System.out.println("hellpo here");
                //  xml=xml+"</options>";

                if (cnt > 0) {
                    if (FR == 1)
                        xml = xml + "<processflow>Yes</processflow>";
                    else
                        xml = xml + "<processflow>No</processflow>";
                    xml = xml + "<flag>success</flag>";
                    System.out.println("failure------->");
                } else {

                    xml = xml + "<flag>failure</flag>";
                    // flag=false;
                }


            } catch (Exception e) {
                System.out.println("catch........" + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }
        if (strCommand.equalsIgnoreCase("loadempvalid")) {
            xml = "<response><command>loadempvalid</command>";
            try {

                HttpSession session = request.getSession(false);
                UserProfile up = null;
                up = (UserProfile)session.getAttribute("UserProfile");
                boolean flag = true;
                ps =
  con.prepareStatement("SELECT EMPLOYEE_ID FROM HRM_MST_EMPLOYEES WHERE EMPLOYEE_ID=?");
                ps.setInt(1, strcode);
                rs = ps.executeQuery();
                if (!rs.next()) {
                    xml = xml + "<flag>failure</flag>";
                    flag = false;
                } else if (up.getEmployeeId() != strcode) {
                    int OfficeId = 0;
                    String sql =
                        "select CONTROLLING_OFFICE_ID from HRM_EMP_CONTROLLING_OFFICE where employee_id=?";
                    ps = con.prepareStatement(sql);
                    ps.setInt(1, strcode);
                    rs = ps.executeQuery();

                    if (rs.next()) {
                        OfficeId = rs.getInt("CONTROLLING_OFFICE_ID");
                    }

                    if (OfficeId != 0) {
                        sql =
 "select OFFICE_ID  from HRM_EMP_CURRENT_POSTING where employee_id=?";
                        ps = con.prepareStatement(sql);
                        ps.setInt(1, up.getEmployeeId());
                        rs = ps.executeQuery();
                        if (rs.next()) {
                            int offid = rs.getInt("OFFICE_ID");
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
                        // if(session.getAttribute("Admin")==null || !((String)session.getAttribute("Admin")).equalsIgnoreCase("YES"))
                        {
                            xml = xml + "<flag>failure3</flag>";
                            flag = false;
                        }
                    }
                } else {

                    //  xml=xml+"<flag>failure4</flag>";
                    //flag=false;
                }

                if (flag) {
                    //con.setAutoCommit(false);
                    System.out.println("ok1");
                    ps =
  con.prepareStatement("select HRM_EMP_CURRENT_POSTING_TMP.EMPLOYEE_ID, EMPLOYEE_NAME||decode(EMPLOYEE_INITIAL,null,' ','.'||EMPLOYEE_INITIAL) as  EMPLOYEE_NAME ,GPF_NO, OFFICE_ID,HRM_EMP_CURRENT_POSTING_TMP.DESIGNATION_ID,DATE_EFFECTIVE_FROM, HRM_EMP_CURRENT_POSTING_TMP.REMARKS,OFFICE_GRADE,DEPARTMENT_ID,HRM_EMP_CURRENT_POSTING_TMP.PROCESS_FLOW_STATUS_ID, EMPLOYEE_STATUS_ID,LEAVE_TYPE_CODE,DATE_EFFECTIVE_FROM_SESSION,SERVICE_GROUP_ID,OFFICE_WING_SINO from HRM_EMP_CURRENT_POSTING_TMP,HRM_MST_EMPLOYEES,HRM_MST_DESIGNATIONS where HRM_MST_DESIGNATIONS.DESIGNATION_ID=HRM_EMP_CURRENT_POSTING_TMP.DESIGNATION_ID and HRM_MST_EMPLOYEES.EMPLOYEE_ID=HRM_EMP_CURRENT_POSTING_TMP.EMPLOYEE_ID and  HRM_EMP_CURRENT_POSTING_TMP.EMPLOYEE_ID=?"); // and  (HRM_EMP_CURRENT_POSTING_TMP.PROCESS_FLOW_STATUS_ID='CR' or HRM_EMP_CURRENT_POSTING_TMP.PROCESS_FLOW_STATUS_ID='MD')" );
                    //ps=con.prepareStatement("select HRM_EMP_CURRENT_POSTING.EMPLOYEE_ID, EMPLOYEE_NAME||decode(EMPLOYEE_INITIAL,null,' ',EMPLOYEE_INITIAL) as  EMPLOYEE_NAME ,GPF_NO, OFFICE_ID,HRM_EMP_CURRENT_POSTING.DESIGNATION_ID,DATE_EFFECTIVE_FROM, HRM_EMP_CURRENT_POSTING.REMARKS,OFFICE_GRADE,DEPARTMENT_ID,HRM_EMP_CURRENT_POSTING.PROCESS_FLOW_STATUS_ID, EMPLOYEE_STATUS_ID,LEAVE_TYPE_CODE,DATE_EFFECTIVE_FROM_SESSION,SERVICE_GROUP_ID from HRM_EMP_CURRENT_POSTING,HRM_MST_EMPLOYEES,HRM_MST_DESIGNATIONS where HRM_MST_DESIGNATIONS.DESIGNATION_ID=HRM_EMP_CURRENT_POSTING.DESIGNATION_ID and HRM_MST_EMPLOYEES.EMPLOYEE_ID=HRM_EMP_CURRENT_POSTING.EMPLOYEE_ID and  HRM_EMP_CURRENT_POSTING.EMPLOYEE_ID=? and  HRM_EMP_CURRENT_POSTING.PROCESS_FLOW_STATUS_ID!='FR'" );
                    ps.setInt(1, strcode);
                    rs = ps.executeQuery();
                    boolean oldrec = false;
                    if (!rs.next()) {
                        //  data in temporary table is not available
                        //ps=con.prepareStatement("select HRM_EMP_CURRENT_POSTING_TMP.EMPLOYEE_ID, EMPLOYEE_NAME||decode(EMPLOYEE_INITIAL,null,' ',EMPLOYEE_INITIAL) as  EMPLOYEE_NAME ,GPF_NO, OFFICE_ID,HRM_EMP_CURRENT_POSTING_TMP.DESIGNATION_ID,DATE_EFFECTIVE_FROM, HRM_EMP_CURRENT_POSTING_TMP.REMARKS,OFFICE_GRADE,DEPARTMENT_ID,HRM_EMP_CURRENT_POSTING_TMP.PROCESS_FLOW_STATUS_ID, EMPLOYEE_STATUS_ID,LEAVE_TYPE_CODE,DATE_EFFECTIVE_FROM_SESSION,SERVICE_GROUP_ID from HRM_EMP_CURRENT_POSTING_TMP,HRM_MST_EMPLOYEES,HRM_MST_DESIGNATIONS where HRM_MST_DESIGNATIONS.DESIGNATION_ID=HRM_EMP_CURRENT_POSTING_TMP.DESIGNATION_ID and HRM_MST_EMPLOYEES.EMPLOYEE_ID=HRM_EMP_CURRENT_POSTING_TMP.EMPLOYEE_ID and  HRM_EMP_CURRENT_POSTING_TMP.EMPLOYEE_ID=? and  (HRM_EMP_CURRENT_POSTING_TMP.PROCESS_FLOW_STATUS_ID='CR' or HRM_EMP_CURRENT_POSTING_TMP.PROCESS_FLOW_STATUS_ID='MD')" );
                        //  checking for freezed record
                        /*  ps=con.prepareStatement("select PROCESS_FLOW_STATUS_ID from HRM_EMP_CURRENT_POSTING_TMP  where EMPLOYEE_ID=? and PROCESS_FLOW_STATUS_ID='FR'" );
                                  ps.setInt(1,strcode);
                                  rs=ps.executeQuery();

                                  if(rs.next()) {

                                         String status=rs.getString("PROCESS_FLOW_STATUS_ID");
                                          xml=xml+"<flag>freezed</flag>";

                                  }
                                  else */
                        {
                            xml = xml + "<flag>norecord</flag>";
                        }
                    } else {
                        oldrec = true; //  data in temporary table is available
                    }
                    if (oldrec == true) {
                        System.out.println("ok2");
                        //if(rs.next())
                        {


                            xml =
 xml + "<EMPLOYEE_ID>" + rs.getInt("EMPLOYEE_ID") + "</EMPLOYEE_ID>";
                            xml =
 xml + "<EMPLOYEE_NAME>" + rs.getString("EMPLOYEE_NAME") + "</EMPLOYEE_NAME>";
                            xml =
 xml + "<GPF_NO>" + rs.getInt("GPF_NO") + "</GPF_NO>";
                            xml =
 xml + "<OFFICE_ID>" + rs.getInt("OFFICE_ID") + "</OFFICE_ID>";
                            xml =
 xml + "<DESIGNATION_ID>" + rs.getInt("DESIGNATION_ID") + "</DESIGNATION_ID>";

                            if (rs.getDate("DATE_EFFECTIVE_FROM") != null) {
                                String[] sd =
                                    rs.getDate("DATE_EFFECTIVE_FROM").toString().split("-");
                                String od = sd[2] + "/" + sd[1] + "/" + sd[0];
                                xml =
 xml + "<DATE_EFFECTIVE_FROM>" + od + "</DATE_EFFECTIVE_FROM>";
                            } else {
                                xml =
 xml + "<DATE_EFFECTIVE_FROM>" + rs.getDate("DATE_EFFECTIVE_FROM") +
   "</DATE_EFFECTIVE_FROM>";

                            }

                            xml =
 xml + "<REMARKS>" + rs.getString("REMARKS") + "</REMARKS>";
                            xml =
 xml + "<OFFICE_GRADE>" + rs.getString("OFFICE_GRADE") + "</OFFICE_GRADE>";
                            xml =
 xml + "<DEPARTMENT_ID>" + rs.getString("DEPARTMENT_ID") + "</DEPARTMENT_ID>";
                            xml =
 xml + "<PROCESS_FLOW_STATUS_ID>" + rs.getString("PROCESS_FLOW_STATUS_ID") +
   "</PROCESS_FLOW_STATUS_ID>";
                            xml =
 xml + "<EMPLOYEE_STATUS_ID>" + rs.getString("EMPLOYEE_STATUS_ID") +
   "</EMPLOYEE_STATUS_ID>";
                            xml =
 xml + "<LEAVE_TYPE>" + rs.getString("LEAVE_TYPE_CODE") + "</LEAVE_TYPE>";
                            xml =
 xml + "<DATE_EFFECTIVE_FROM_SESSION>" + rs.getString("DATE_EFFECTIVE_FROM_SESSION") +
   "</DATE_EFFECTIVE_FROM_SESSION>";
                            xml =
 xml + "<DEPARTMENT_ID>" + rs.getString("DEPARTMENT_ID") + "</DEPARTMENT_ID>";
                            xml =
 xml + "<SERVICE_GROUP_ID>" + rs.getInt("SERVICE_GROUP_ID") +
   "</SERVICE_GROUP_ID>";

                            // rs.close();
                            //  ps.close();

                            int wing = 0;

                            if (rs.getString("OFFICE_WING_SINO") != null) {
                                wing =
Integer.parseInt(rs.getString("OFFICE_WING_SINO"));
                            }
                            xml = xml + "<WING>" + wing + "</WING>";

                            System.out.println("here is ok1");
                            if (rs.getString("EMPLOYEE_STATUS_ID") != null &&
                                rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("STU")) {

                                /*   sql="select CONTROLLING_OFFICE_ID from HRM_EMP_CONTROLLING_OFFICE where employee_id=?";
                                                  ps=con.prepareStatement(sql);
                                                  ps.setInt(1,strcode);
                                                  rs=ps.executeQuery();
                                                  int OfficeId=0;
                                                  if(rs.next()) {
                                                      OfficeId=rs.getInt("CONTROLLING_OFFICE_ID");
                                                  }*/

                                System.out.println("ok123");
                                ps =
  con.prepareStatement("select  max(JOINING_REPORT_ID)  rep_id,INSTITUTION_NAME,INSTITUTION_LOCATION,COURSE_NAME from HRM_EMP_STU_JOIN_REPORTS where EMPLOYEE_ID=? and (PROCESS_FLOW_STATUS_ID='CR' or PROCESS_FLOW_STATUS_ID='MD' or PROCESS_FLOW_STATUS_ID='FR') group by INSTITUTION_NAME,INSTITUTION_LOCATION,COURSE_NAME");
                                ps.setInt(1, strcode);
                                //ps.setInt(2,OfficeId);
                                rs = ps.executeQuery();
                                if (rs.next()) {
                                    xml =
 xml + "<INSTITUTION_NAME>" + rs.getString("INSTITUTION_NAME") +
   "</INSTITUTION_NAME>";
                                    xml =
 xml + "<INSTITUTION_LOCATION>" + rs.getString("INSTITUTION_LOCATION") +
   "</INSTITUTION_LOCATION>";
                                    xml =
 xml + "<COURSE_NAME>" + rs.getString("COURSE_NAME") + "</COURSE_NAME>";
                                }
                            }
                            System.out.println("here is ok2");


                            xml = xml + "<flag>success</flag>";

                            System.out.println("test");
                            if (session.getAttribute("Admin") != null &&
                                ((String)session.getAttribute("Admin")).equalsIgnoreCase("YES")) {
                                xml = xml + "<admin>YES</admin>";
                            } else {
                                xml = xml + "<admin>NO</admin>";
                            }

                        }
                        /*   else
                                        {
                                            ps=con.prepareStatement("select PROCESS_FLOW_STATUS_ID from HRM_EMP_CURRENT_POSTING_TMP  where EMPLOYEE_ID=? and PROCESS_FLOW_STATUS_ID='FR'" );
                                            ps.setInt(1,strcode);
                                            rs=ps.executeQuery();

                                            if(rs.next()) {

                                                   String status=rs.getString("PROCESS_FLOW_STATUS_ID");
                                                    xml=xml+"<flag>freezed</flag>";

                                            }
                                            else
                                            {
                                                    xml=xml+"<flag>failure</flag>";
                                            }
                                        }
                                        */
                    }

                }

            } catch (Exception e) {
                System.out.println("catch........" + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }


        if (strCommand.equalsIgnoreCase("loadempview")) {
            xml = "<response><command>loadempview</command>";
            try {

                HttpSession session = request.getSession(false);
                UserProfile up = null;
                up = (UserProfile)session.getAttribute("UserProfile");
                boolean flag = true;
                ps =
  con.prepareStatement("SELECT EMPLOYEE_ID FROM HRM_MST_EMPLOYEES WHERE EMPLOYEE_ID=?");
                ps.setInt(1, strcode);
                rs = ps.executeQuery();
                if (!rs.next()) {
                    xml = xml + "<flag>failure</flag>";
                    flag = false;
                } else if (up.getEmployeeId() != strcode) {
                    int OfficeId = 0;
                    String sql =
                        "select CONTROLLING_OFFICE_ID from HRM_EMP_CONTROLLING_OFFICE where employee_id=?";
                    ps = con.prepareStatement(sql);
                    ps.setInt(1, strcode);
                    rs = ps.executeQuery();

                    if (rs.next()) {
                        OfficeId = rs.getInt("CONTROLLING_OFFICE_ID");
                    }

                    if (OfficeId != 0) {
                        sql =
 "select OFFICE_ID  from HRM_EMP_CURRENT_POSTING where employee_id=?";
                        ps = con.prepareStatement(sql);
                        ps.setInt(1, up.getEmployeeId());
                        rs = ps.executeQuery();
                        if (rs.next()) {
                            int offid = rs.getInt("OFFICE_ID");
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
                        // if(session.getAttribute("Admin")==null || !((String)session.getAttribute("Admin")).equalsIgnoreCase("YES"))
                        {
                            xml = xml + "<flag>failure3</flag>";
                            flag = false;
                        }
                    }
                } else {

                    //  xml=xml+"<flag>failure4</flag>";
                    //flag=false;
                }

                if (flag) {
                    //con.setAutoCommit(false);
                    System.out.println("ok1");
                    ps =
  con.prepareStatement("select HRM_EMP_CURRENT_POSTING.EMPLOYEE_ID, EMPLOYEE_NAME||decode(EMPLOYEE_INITIAL,null,' ','.'||EMPLOYEE_INITIAL) as  EMPLOYEE_NAME ,GPF_NO, OFFICE_ID,HRM_EMP_CURRENT_POSTING.DESIGNATION_ID,DATE_EFFECTIVE_FROM, HRM_EMP_CURRENT_POSTING.REMARKS,OFFICE_GRADE,DEPARTMENT_ID,HRM_EMP_CURRENT_POSTING.PROCESS_FLOW_STATUS_ID, EMPLOYEE_STATUS_ID,LEAVE_TYPE_CODE,DATE_EFFECTIVE_FROM_SESSION,SERVICE_GROUP_ID from HRM_EMP_CURRENT_POSTING,HRM_MST_EMPLOYEES,HRM_MST_DESIGNATIONS where HRM_MST_DESIGNATIONS.DESIGNATION_ID=HRM_EMP_CURRENT_POSTING.DESIGNATION_ID and HRM_MST_EMPLOYEES.EMPLOYEE_ID=HRM_EMP_CURRENT_POSTING.EMPLOYEE_ID and  HRM_EMP_CURRENT_POSTING.EMPLOYEE_ID=? and HRM_EMP_CURRENT_POSTING.PROCESS_FLOW_STATUS_ID='FR'"); // and  (HRM_EMP_CURRENT_POSTING_TMP.PROCESS_FLOW_STATUS_ID='CR' or HRM_EMP_CURRENT_POSTING_TMP.PROCESS_FLOW_STATUS_ID='MD')" );
                    //ps=con.prepareStatement("select HRM_EMP_CURRENT_POSTING.EMPLOYEE_ID, EMPLOYEE_NAME||decode(EMPLOYEE_INITIAL,null,' ',EMPLOYEE_INITIAL) as  EMPLOYEE_NAME ,GPF_NO, OFFICE_ID,HRM_EMP_CURRENT_POSTING.DESIGNATION_ID,DATE_EFFECTIVE_FROM, HRM_EMP_CURRENT_POSTING.REMARKS,OFFICE_GRADE,DEPARTMENT_ID,HRM_EMP_CURRENT_POSTING.PROCESS_FLOW_STATUS_ID, EMPLOYEE_STATUS_ID,LEAVE_TYPE_CODE,DATE_EFFECTIVE_FROM_SESSION,SERVICE_GROUP_ID from HRM_EMP_CURRENT_POSTING,HRM_MST_EMPLOYEES,HRM_MST_DESIGNATIONS where HRM_MST_DESIGNATIONS.DESIGNATION_ID=HRM_EMP_CURRENT_POSTING.DESIGNATION_ID and HRM_MST_EMPLOYEES.EMPLOYEE_ID=HRM_EMP_CURRENT_POSTING.EMPLOYEE_ID and  HRM_EMP_CURRENT_POSTING.EMPLOYEE_ID=? and  HRM_EMP_CURRENT_POSTING.PROCESS_FLOW_STATUS_ID!='FR'" );
                    ps.setInt(1, strcode);
                    rs = ps.executeQuery();
                    boolean oldrec = false;
                    if (!rs.next()) {
                        xml = xml + "<flag>norecord</flag>";

                    } else {
                        oldrec = true; //  data in temporary table is available
                    }
                    if (oldrec == true) {
                        System.out.println("ok2");
                        //if(rs.next())
                        {


                            xml =
 xml + "<EMPLOYEE_ID>" + rs.getInt("EMPLOYEE_ID") + "</EMPLOYEE_ID>";
                            xml =
 xml + "<EMPLOYEE_NAME>" + rs.getString("EMPLOYEE_NAME") + "</EMPLOYEE_NAME>";
                            xml =
 xml + "<GPF_NO>" + rs.getInt("GPF_NO") + "</GPF_NO>";
                            xml =
 xml + "<OFFICE_ID>" + rs.getInt("OFFICE_ID") + "</OFFICE_ID>";
                            xml =
 xml + "<DESIGNATION_ID>" + rs.getInt("DESIGNATION_ID") + "</DESIGNATION_ID>";

                            if (rs.getDate("DATE_EFFECTIVE_FROM") != null) {
                                String[] sd =
                                    rs.getDate("DATE_EFFECTIVE_FROM").toString().split("-");
                                String od = sd[2] + "/" + sd[1] + "/" + sd[0];
                                xml =
 xml + "<DATE_EFFECTIVE_FROM>" + od + "</DATE_EFFECTIVE_FROM>";
                            } else {
                                xml =
 xml + "<DATE_EFFECTIVE_FROM>" + rs.getDate("DATE_EFFECTIVE_FROM") +
   "</DATE_EFFECTIVE_FROM>";

                            }

                            xml =
 xml + "<REMARKS>" + rs.getString("REMARKS") + "</REMARKS>";
                            xml =
 xml + "<OFFICE_GRADE>" + rs.getString("OFFICE_GRADE") + "</OFFICE_GRADE>";
                            xml =
 xml + "<DEPARTMENT_ID>" + rs.getString("DEPARTMENT_ID") + "</DEPARTMENT_ID>";
                            xml =
 xml + "<PROCESS_FLOW_STATUS_ID>" + rs.getString("PROCESS_FLOW_STATUS_ID") +
   "</PROCESS_FLOW_STATUS_ID>";
                            xml =
 xml + "<EMPLOYEE_STATUS_ID>" + rs.getString("EMPLOYEE_STATUS_ID") +
   "</EMPLOYEE_STATUS_ID>";
                            xml =
 xml + "<LEAVE_TYPE>" + rs.getString("LEAVE_TYPE_CODE") + "</LEAVE_TYPE>";
                            xml =
 xml + "<DATE_EFFECTIVE_FROM_SESSION>" + rs.getString("DATE_EFFECTIVE_FROM_SESSION") +
   "</DATE_EFFECTIVE_FROM_SESSION>";
                            xml =
 xml + "<DEPARTMENT_ID>" + rs.getString("DEPARTMENT_ID") + "</DEPARTMENT_ID>";
                            xml =
 xml + "<SERVICE_GROUP_ID>" + rs.getInt("SERVICE_GROUP_ID") +
   "</SERVICE_GROUP_ID>";

                            rs.close();
                            ps.close();

                            /*    wing  */


                            int wing = 0;
                            ps =
  con.prepareStatement("select OFFICE_WING_SINO from HRM_EMP_CURRENT_WING where employee_id=?");
                            ps.setInt(1, strcode);
                            rs = ps.executeQuery();
                            if (rs.next()) {
                                if (rs.getString("OFFICE_WING_SINO") != null) {
                                    wing =
Integer.parseInt(rs.getString("OFFICE_WING_SINO"));
                                }
                            }
                            xml = xml + "<WING>" + wing + "</WING>";

                            /* wing  */


                            xml = xml + "<flag>success</flag>";
                        }

                    }

                }

            } catch (Exception e) {
                System.out.println("catch........" + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }


        if (strCommand.equalsIgnoreCase("loadempdelete")) {
            xml = "<response><command>loadempdelete</command>";
            try {

                HttpSession session = request.getSession(false);
                UserProfile up = null;
                up = (UserProfile)session.getAttribute("UserProfile");
                boolean flag = true;
                ps =
  con.prepareStatement("SELECT EMPLOYEE_ID FROM HRM_MST_EMPLOYEES WHERE EMPLOYEE_ID=?");
                ps.setInt(1, strcode);
                rs = ps.executeQuery();
                if (!rs.next()) {
                    xml = xml + "<flag>failure</flag>";
                    flag = false;
                } else if (up.getEmployeeId() != strcode) {
                    int OfficeId = 0;
                    String sql =
                        "select CONTROLLING_OFFICE_ID from HRM_EMP_CONTROLLING_OFFICE where employee_id=?";
                    ps = con.prepareStatement(sql);
                    ps.setInt(1, strcode);
                    rs = ps.executeQuery();

                    if (rs.next()) {
                        OfficeId = rs.getInt("CONTROLLING_OFFICE_ID");
                    }

                    if (OfficeId != 0) {
                        sql =
 "select OFFICE_ID  from HRM_EMP_CURRENT_POSTING where employee_id=?";
                        ps = con.prepareStatement(sql);
                        ps.setInt(1, up.getEmployeeId());
                        rs = ps.executeQuery();
                        if (rs.next()) {
                            int offid = rs.getInt("OFFICE_ID");
                            if (offid != OfficeId) {
                                //response.sendRedirect(request.getContextPath()+"/org/Library/jsps/Messenger.jsp?message=" + "Can not see profile. Because Employee Id "+strEmpId+" is not under your Office!");
                                xml = xml + "<flag>failure1</flag>";
                                flag = false;
                            }
                        } else {
                            // response.sendRedirect(request.getContextPath()+"/org/Library/jsps/Messenger.jsp?message=" + "Current Posting is not available. Can not see the profile for "+strEmpId+"!");
                            xml = xml + "<flag>failure2</flag>";
                            flag = false;
                        }

                    } else {
                        xml = xml + "<flag>failure3</flag>";
                        flag = false;
                    }
                } else {

                    xml = xml + "<flag>failure4</flag>";
                    flag = false;
                }

                if (flag) {
                    //con.setAutoCommit(false);
                    System.out.println("ok1");
                    ps =
  con.prepareStatement("select HRM_EMP_CURRENT_POSTING_TMP.EMPLOYEE_ID, EMPLOYEE_NAME||decode(EMPLOYEE_INITIAL,null,' ','.'||EMPLOYEE_INITIAL) as  EMPLOYEE_NAME ,GPF_NO, OFFICE_ID,DESIGNATION as DESIGNATION_ID,DATE_EFFECTIVE_FROM, HRM_EMP_CURRENT_POSTING_TMP.REMARKS,OFFICE_GRADE,DEPARTMENT_ID,HRM_EMP_CURRENT_POSTING_TMP.PROCESS_FLOW_STATUS_ID,CUR_POST_REASON_DESC, EMPLOYEE_STATUS_ID,LEAVE_TYPE_CODE,DATE_EFFECTIVE_FROM_SESSION,SERVICE_GROUP_ID from HRM_EMP_CURRENT_POSTING_TMP,HRM_MST_EMPLOYEES,HRM_MST_DESIGNATIONS,HRM_MST_CUR_POST_STATUS where HRM_MST_CUR_POST_STATUS.CUR_POST_REASON_ID=HRM_EMP_CURRENT_POSTING_TMP.EMPLOYEE_STATUS_ID and  HRM_MST_DESIGNATIONS.DESIGNATION_ID=HRM_EMP_CURRENT_POSTING_TMP.DESIGNATION_ID and HRM_MST_EMPLOYEES.EMPLOYEE_ID=HRM_EMP_CURRENT_POSTING_TMP.EMPLOYEE_ID and  HRM_EMP_CURRENT_POSTING_TMP.EMPLOYEE_ID=? and  (HRM_EMP_CURRENT_POSTING_TMP.PROCESS_FLOW_STATUS_ID='CR' or HRM_EMP_CURRENT_POSTING_TMP.PROCESS_FLOW_STATUS_ID='MD')");
                    ps.setInt(1, strcode);
                    rs = ps.executeQuery();
                    System.out.println("ok2");
                    if (rs.next()) {


                        xml =
 xml + "<EMPLOYEE_ID>" + rs.getInt("EMPLOYEE_ID") + "</EMPLOYEE_ID>";
                        xml =
 xml + "<EMPLOYEE_NAME>" + rs.getString("EMPLOYEE_NAME") + "</EMPLOYEE_NAME>";
                        xml =
 xml + "<GPF_NO>" + rs.getInt("GPF_NO") + "</GPF_NO>";
                        xml =
 xml + "<OFFICE_ID>" + rs.getInt("OFFICE_ID") + "</OFFICE_ID>";
                        xml =
 xml + "<DESIGNATION_ID>" + rs.getString("DESIGNATION_ID") +
   "</DESIGNATION_ID>";

                        if (rs.getDate("DATE_EFFECTIVE_FROM") != null) {
                            String[] sd =
                                rs.getDate("DATE_EFFECTIVE_FROM").toString().split("-");
                            String od = sd[2] + "/" + sd[1] + "/" + sd[0];
                            xml =
 xml + "<DATE_EFFECTIVE_FROM>" + od + "</DATE_EFFECTIVE_FROM>";
                        } else {
                            xml =
 xml + "<DATE_EFFECTIVE_FROM>" + rs.getDate("DATE_EFFECTIVE_FROM") +
   "</DATE_EFFECTIVE_FROM>";

                        }

                        xml =
 xml + "<REMARKS>" + rs.getString("REMARKS") + "</REMARKS>";
                        xml =
 xml + "<OFFICE_GRADE>" + rs.getString("OFFICE_GRADE") + "</OFFICE_GRADE>";
                        xml =
 xml + "<DEPARTMENT_ID>" + rs.getString("DEPARTMENT_ID") + "</DEPARTMENT_ID>";
                        xml =
 xml + "<PROCESS_FLOW_STATUS_ID>" + rs.getString("PROCESS_FLOW_STATUS_ID") +
   "</PROCESS_FLOW_STATUS_ID>";
                        xml =
 xml + "<EMPLOYEE_STATUS_ID>" + rs.getString("EMPLOYEE_STATUS_ID") +
   "</EMPLOYEE_STATUS_ID>";
                        xml =
 xml + "<CUR_POST_REASON_DESC>" + rs.getString("CUR_POST_REASON_DESC") +
   "</CUR_POST_REASON_DESC>";
                        xml =
 xml + "<LEAVE_TYPE>" + rs.getString("LEAVE_TYPE_CODE") + "</LEAVE_TYPE>";
                        xml =
 xml + "<DATE_EFFECTIVE_FROM_SESSION>" + rs.getString("DATE_EFFECTIVE_FROM_SESSION") +
   "</DATE_EFFECTIVE_FROM_SESSION>";
                        xml =
 xml + "<DEPARTMENT_ID>" + rs.getString("DEPARTMENT_ID") + "</DEPARTMENT_ID>";
                        xml =
 xml + "<SERVICE_GROUP_ID>" + rs.getInt("SERVICE_GROUP_ID") +
   "</SERVICE_GROUP_ID>";

                        rs.close();
                        ps.close();
                        xml = xml + "<flag>success</flag>";
                    } else {
                        ps =
  con.prepareStatement("select PROCESS_FLOW_STATUS_ID from HRM_EMP_CURRENT_POSTING_TMP  where EMPLOYEE_ID=? and PROCESS_FLOW_STATUS_ID='FR'");
                        ps.setInt(1, strcode);
                        rs = ps.executeQuery();

                        if (rs.next()) {

                            String status =
                                rs.getString("PROCESS_FLOW_STATUS_ID");
                            xml = xml + "<flag>freezed</flag>";

                        } else {
                            xml = xml + "<flag>failure</flag>";
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("catch........" + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        } else if (strCommand.equalsIgnoreCase("SerGroup")) {
            xml = "<response><command>SerGroup</command>";
            try {
                String strdes = request.getParameter("cmbdes");
                int des = Integer.parseInt(strdes);

                ps =
  con.prepareStatement("select SERVICE_GROUP_ID from HRM_MST_DESIGNATIONS  where DESIGNATION_ID=?");
                ps.setInt(1, des);
                rs = ps.executeQuery();
                if (rs.next()) {
                    xml =
 xml + "<flag>success</flag><sid>" + rs.getInt(1) + "</sid>";
                } else {
                    xml = xml + "<flag>failure</flag>";
                }

            }

            catch (Exception e) {

                System.out.println("catch........" + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";


        }

        ///////////////////////////////////////////////
        out.println(xml);
        System.out.println(xml);
        out.close();

    }


    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        response.setContentType(CONTENT_TYPE);
        Connection con = null;
        try {

            ResourceBundle rsb =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";

            String strDriver = rsb.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rsb.getString("Config.DSN");
            String strhostname = rsb.getString("Config.HOST_NAME");
            String strportno = rsb.getString("Config.PORT_NUMBER");
            String strsid = rsb.getString("Config.SID");
            String strdbusername = rsb.getString("Config.USER_NAME");
            String strdbpassword = rsb.getString("Config.PASSWORD");

            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection


            Class.forName(strDriver.trim());
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
        } catch (Exception e) {
            System.out.println("Exception in connection..." + e);
        }
        ResultSet rs = null;
        PreparedStatement ps = null;
        Statement st = null;

        int strcode = 0, slno = 0, designation = 0, officeid = 0;
        String statusid = "", leavetype = "";
        Date dtfrom = null, dtto = null;
        String dtfromsession = "", dttosession = "", statusdetail =
            "", remarks = "", deptid = "", officegrade = "";
        //String strname="";
        String xml = "";
        String strCommand = "";
        Calendar c;

        PrintWriter out = response.getWriter();
        /*   HttpSession session=request.getSession(false);
            try
            {

                if(session==null)
                {
                    System.out.println(request.getContextPath()+"/index.jsp");
                    response.sendRedirect(request.getContextPath()+"/index.jsp");
                }
                System.out.println(session);

            }catch(Exception e)
            {
            System.out.println("Redirect Error :"+e);
            }*/

        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");

        try {
            strCommand = request.getParameter("Command");
            System.out.println("assign....." + strCommand);

            strcode = Integer.parseInt(request.getParameter("txtEmployeeid"));
            System.out.println("assign..... Code::" + strcode);

            if (!strCommand.equalsIgnoreCase("Delete")) {
                officegrade = request.getParameter("Office_Grade");
                System.out.println("Office_Grade:" + officegrade);

                statusid = request.getParameter("cmbstatus");
                System.out.println("cmbstatus:" + statusid);

                designation =
                        Integer.parseInt(request.getParameter("cmbDesignation"));
                System.out.println("cmbDesignation:" + designation);

            } else {
                statusid = request.getParameter("txtstatus");
                System.out.println("cmbstatus:" + statusid);
            }


        }

        catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }

        if (strCommand.equalsIgnoreCase("Add")) {
            System.out.println("Command :" + strCommand);
            try {
                java.util.Date dt =
                    new java.util.Date(System.currentTimeMillis());
                java.sql.Timestamp sqldt =
                    new java.sql.Timestamp(dt.getTime());
                System.out.println("Time Stamp:" + sqldt);


                if (statusid.equalsIgnoreCase("WKG")) {

                    deptid = request.getParameter("txtDept_Id_work");
                    System.out.println("txtDept_Id_work:" + deptid);

                    officeid =
                            Integer.parseInt(request.getParameter("txtOffice_Id"));
                    System.out.println("txtOffice_Id:" + officeid);


                    String[] sd =
                        request.getParameter("txtWorkDateFrom").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                    java.util.Date d = c.getTime();
                    dtfrom = new Date(d.getTime());

                    dtfromsession = request.getParameter("optWorkDateFrom");

                    System.out.println("From Date:" + dtfrom + " " +
                                       dtfromsession);

                    remarks = request.getParameter("txtworkremark");
                    System.out.println("txtworkremark:" + remarks);

                    ps =
  con.prepareStatement("insert into HRM_EMP_CURRENT_POSTING_TMP" +
                       "(EMPLOYEE_ID,OFFICE_ID,DESIGNATION_ID,DATE_EFFECTIVE_FROM,REMARKS," +
                       "OFFICE_GRADE,DEPARTMENT_ID,PROCESS_FLOW_STATUS_ID,EMPLOYEE_STATUS_ID," +
                       "LEAVE_TYPE_CODE,DATE_EFFECTIVE_FROM_SESSION,UPDATED_DATE)" +
                       " values(?,?,?,?,?,?,?,?,?,?,?,?)");
                    ps.setInt(1, strcode);
                    ps.setInt(2, officeid);
                    ps.setInt(3, designation);
                    ps.setDate(4, dtfrom);
                    ps.setString(5, remarks);
                    ps.setString(6, officegrade);
                    ps.setString(7, deptid);
                    ps.setString(8, "CR");
                    ps.setString(9, statusid);
                    ps.setString(10, null);
                    ps.setString(11, dtfromsession);
                    ps.setTimestamp(12, sqldt);


                    ps.executeUpdate();

                    sendMessage(response, "Record is Saved Successfully",
                                "ok");

                } else if (statusid.equalsIgnoreCase("STU")) {

                    String[] sd =
                        request.getParameter("txtStudyDateFrom").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                    java.util.Date d = c.getTime();
                    dtfrom = new Date(d.getTime());

                    dtfromsession = request.getParameter("optStudyDateFrom");

                    System.out.println("From Date:" + dtfrom + " " +
                                       dtfromsession);

                    remarks = request.getParameter("txtstudyremark");
                    System.out.println("txtstudyremark:" + remarks);

                    ps =
  con.prepareStatement("insert into HRM_EMP_CURRENT_POSTING_TMP" +
                       "(EMPLOYEE_ID,DATE_EFFECTIVE_FROM,REMARKS," +
                       "OFFICE_GRADE,PROCESS_FLOW_STATUS_ID,EMPLOYEE_STATUS_ID," +
                       "DATE_EFFECTIVE_FROM_SESSION,UPDATED_DATE)" +
                       " values(?,?,?,?,?,?,?,?)");
                    ps.setInt(1, strcode);
                    ps.setDate(2, dtfrom);
                    ps.setString(3, remarks);
                    ps.setString(4, officegrade);
                    ps.setString(5, "CR");
                    ps.setString(6, statusid);
                    ps.setString(7, dtfromsession);
                    ps.setTimestamp(8, sqldt);

                    ps.executeUpdate();

                    sendMessage(response, "Record is Saved Successfully",
                                "ok");

                }


                else if (statusid.equalsIgnoreCase("LLV")) {

                    leavetype = request.getParameter("cmbleavetype");
                    System.out.println("cmbleavetype:" + leavetype);

                    String[] sd =
                        request.getParameter("txtDateFrom").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                    java.util.Date d = c.getTime();
                    dtfrom = new Date(d.getTime());

                    dtfromsession = request.getParameter("optDateFrom");

                    System.out.println("From Date:" + dtfrom + " " +
                                       dtfromsession);

                    remarks = request.getParameter("txtleaveremark");
                    System.out.println("txtleaveremark:" + remarks);

                    ps =
  con.prepareStatement("insert into HRM_EMP_CURRENT_POSTING_TMP" +
                       "(EMPLOYEE_ID,DESIGNATION_ID,DATE_EFFECTIVE_FROM,REMARKS," +
                       "OFFICE_GRADE,PROCESS_FLOW_STATUS_ID,EMPLOYEE_STATUS_ID," +
                       "LEAVE_TYPE_CODE,DATE_EFFECTIVE_FROM_SESSION,UPDATED_DATE)" +
                       " values(?,?,?,?,?,?,?,?,?,?)");
                    ps.setInt(1, strcode);
                    ps.setInt(2, designation);
                    ps.setDate(3, dtfrom);
                    ps.setString(4, remarks);
                    ps.setString(5, officegrade);
                    ps.setString(6, "CR");
                    ps.setString(7, statusid);
                    ps.setString(8, leavetype);
                    ps.setString(9, dtfromsession);
                    ps.setTimestamp(10, sqldt);

                    ps.executeUpdate();

                    sendMessage(response, "Record is Saved Successfully",
                                "ok");

                } else if (statusid.equalsIgnoreCase("SUS")) {
                    String[] sd =
                        request.getParameter("txtSusFrom").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                    java.util.Date d = c.getTime();
                    dtfrom = new Date(d.getTime());

                    dtfromsession = request.getParameter("optSusFrom");

                    System.out.println("From Date:" + dtfrom + " " +
                                       dtfromsession);

                    remarks = request.getParameter("txtsusreson");
                    System.out.println("txtsusreson:" + remarks);

                    ps =
  con.prepareStatement("insert into HRM_EMP_CURRENT_POSTING_TMP" +
                       "(EMPLOYEE_ID,DESIGNATION_ID,DATE_EFFECTIVE_FROM,REMARKS," +
                       "OFFICE_GRADE,PROCESS_FLOW_STATUS_ID,EMPLOYEE_STATUS_ID," +
                       "DATE_EFFECTIVE_FROM_SESSION,UPDATED_DATE)" +
                       " values(?,?,?,?,?,?,?,?,?)");
                    ps.setInt(1, strcode);
                    ps.setInt(2, designation);
                    ps.setDate(3, dtfrom);
                    ps.setString(4, remarks);
                    ps.setString(5, officegrade);
                    ps.setString(6, "CR");
                    ps.setString(7, statusid);
                    ps.setString(8, dtfromsession);
                    ps.setTimestamp(9, sqldt);

                    ps.executeUpdate();

                    sendMessage(response, "Record is Saved Successfully",
                                "ok");
                }

                /*   TRANSIT  */
                else if (statusid.equalsIgnoreCase("TRT")) {
                    String[] sd =
                        request.getParameter("txtTrtFrom").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                    java.util.Date d = c.getTime();
                    dtfrom = new Date(d.getTime());

                    dtfromsession = request.getParameter("optTrtFrom");

                    System.out.println("From Date:" + dtfrom + " " +
                                       dtfromsession);

                    remarks = request.getParameter("txtTrtreson");
                    System.out.println("txtTrtreson:" + remarks);

                    ps =
  con.prepareStatement("insert into HRM_EMP_CURRENT_POSTING_TMP" +
                       "(EMPLOYEE_ID,DESIGNATION_ID,DATE_EFFECTIVE_FROM,REMARKS," +
                       "OFFICE_GRADE,PROCESS_FLOW_STATUS_ID,EMPLOYEE_STATUS_ID," +
                       "DATE_EFFECTIVE_FROM_SESSION,UPDATED_DATE)" +
                       " values(?,?,?,?,?,?,?,?,?)");
                    ps.setInt(1, strcode);
                    ps.setInt(2, designation);
                    ps.setDate(3, dtfrom);
                    ps.setString(4, remarks);
                    ps.setString(5, officegrade);
                    ps.setString(6, "CR");
                    ps.setString(7, statusid);
                    ps.setString(8, dtfromsession);
                    ps.setTimestamp(9, sqldt);

                    ps.executeUpdate();

                    sendMessage(response, "Record is Saved Successfully",
                                "ok");
                }

                /* UNAUTHERIZED LEAVE   */
                else if (statusid.equalsIgnoreCase("UAL")) {
                    String[] sd =
                        request.getParameter("txtUalFrom").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                    java.util.Date d = c.getTime();
                    dtfrom = new Date(d.getTime());

                    dtfromsession = request.getParameter("optUalFrom");

                    System.out.println("From Date:" + dtfrom + " " +
                                       dtfromsession);

                    remarks = request.getParameter("txtUalreson");
                    System.out.println("txtUalreson:" + remarks);

                    ps =
  con.prepareStatement("insert into HRM_EMP_CURRENT_POSTING_TMP" +
                       "(EMPLOYEE_ID,DESIGNATION_ID,DATE_EFFECTIVE_FROM,REMARKS," +
                       "OFFICE_GRADE,PROCESS_FLOW_STATUS_ID,EMPLOYEE_STATUS_ID," +
                       "DATE_EFFECTIVE_FROM_SESSION,UPDATED_DATE)" +
                       " values(?,?,?,?,?,?,?,?,?)");
                    ps.setInt(1, strcode);
                    ps.setInt(2, designation);
                    ps.setDate(3, dtfrom);
                    ps.setString(4, remarks);
                    ps.setString(5, officegrade);
                    ps.setString(6, "CR");
                    ps.setString(7, statusid);
                    ps.setString(8, dtfromsession);
                    ps.setTimestamp(9, sqldt);

                    ps.executeUpdate();

                    sendMessage(response, "Record is Saved Successfully",
                                "ok");
                }

                /*  COMPULSARY LEAVE */
                else if (statusid.equalsIgnoreCase("CMW")) {
                    String[] sd =
                        request.getParameter("txtCmwFrom").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                    java.util.Date d = c.getTime();
                    dtfrom = new Date(d.getTime());

                    dtfromsession = request.getParameter("optCmwFrom");

                    System.out.println("From Date:" + dtfrom + " " +
                                       dtfromsession);

                    remarks = request.getParameter("txtCmwreson");
                    System.out.println("txtCmwreson:" + remarks);

                    ps =
  con.prepareStatement("insert into HRM_EMP_CURRENT_POSTING_TMP" +
                       "(EMPLOYEE_ID,DESIGNATION_ID,DATE_EFFECTIVE_FROM,REMARKS," +
                       "OFFICE_GRADE,PROCESS_FLOW_STATUS_ID,EMPLOYEE_STATUS_ID," +
                       "DATE_EFFECTIVE_FROM_SESSION,UPDATED_DATE)" +
                       " values(?,?,?,?,?,?,?,?,?)");
                    ps.setInt(1, strcode);
                    ps.setInt(2, designation);
                    ps.setDate(3, dtfrom);
                    ps.setString(4, remarks);
                    ps.setString(5, officegrade);
                    ps.setString(6, "CR");
                    ps.setString(7, statusid);
                    ps.setString(8, dtfromsession);
                    ps.setTimestamp(9, sqldt);

                    ps.executeUpdate();

                    sendMessage(response, "Record is Saved Successfully",
                                "ok");
                }


                else if (statusid.equalsIgnoreCase("ABS")) {
                    String[] sd =
                        request.getParameter("txtAbsFrom").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                    java.util.Date d = c.getTime();
                    dtfrom = new Date(d.getTime());

                    dtfromsession = request.getParameter("optAbsFrom");

                    System.out.println("From Date:" + dtfrom + " " +
                                       dtfromsession);

                    remarks = request.getParameter("txtabsremark");
                    System.out.println("txtabsremark:" + remarks);

                    ps =
  con.prepareStatement("insert into HRM_EMP_CURRENT_POSTING_TMP" +
                       "(EMPLOYEE_ID,DESIGNATION_ID,DATE_EFFECTIVE_FROM,REMARKS," +
                       "OFFICE_GRADE,PROCESS_FLOW_STATUS_ID,EMPLOYEE_STATUS_ID," +
                       "DATE_EFFECTIVE_FROM_SESSION,UPDATED_DATE)" +
                       " values(?,?,?,?,?,?,?,?,?)");
                    ps.setInt(1, strcode);
                    ps.setInt(2, designation);
                    ps.setDate(3, dtfrom);
                    ps.setString(4, remarks);
                    ps.setString(5, officegrade);
                    ps.setString(6, "CR");
                    ps.setString(7, statusid);
                    ps.setString(8, dtfromsession);
                    ps.setTimestamp(9, sqldt);

                    ps.executeUpdate();

                    sendMessage(response, "Record is Saved Successfully",
                                "ok");

                }

            } catch (Exception e) {
                String err = "Record is not Saved. " + e.getMessage();
                sendMessage(response, err, "ok");

            }

        }
        ////////////////
        if (strCommand.equalsIgnoreCase("Update")) {
            System.out.println("Command :" + strCommand);

            try {
                HttpSession session = request.getSession(false);
                String updatedby = (String)session.getAttribute("UserId");
                long l = System.currentTimeMillis();
                Timestamp ts = new Timestamp(l);

                java.util.Date dt =
                    new java.util.Date(System.currentTimeMillis());
                java.sql.Timestamp sqldt =
                    new java.sql.Timestamp(dt.getTime());
                System.out.println("Time Stamp:" + sqldt);
                //  if(statusid.equalsIgnoreCase("WKG"))            {

                deptid = request.getParameter("txtDept_Id_work");
                System.out.println("txtDept_Id_work:" + deptid);

                officeid = Integer.parseInt(request.getParameter("offid"));
                System.out.println("txtOffice_Id:" + officeid);

                int wing = 0;
                /*    if(request.getParameter("cmbWing")!=null) {

                            wing=Integer.parseInt(request.getParameter("cmbWing"));

                        }*/

                String[] sd =
                    request.getParameter("txtWorkDateFrom").split("/");
                c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                java.util.Date d = c.getTime();
                dtfrom = new Date(d.getTime());

                dtfromsession = request.getParameter("optWorkDateFrom");

                System.out.println("From Date:" + dtfrom + " " +
                                   dtfromsession);

                remarks = request.getParameter("txtworkremark");
                System.out.println("txtworkremark:" + remarks);

                con.setAutoCommit(false);


                /*  ps=con.prepareStatement("delete from HRM_EMP_ADDL_CHARGE " +
                         " where  EMPLOYEE_ID=? and office_id=? and ( PROCESS_FLOW_STATUS_ID!='DL') ");
                         ps.setInt(1,strcode);
                         ps.setInt(2,officeid);
                         ps.executeUpdate();*/


                ps =
  con.prepareStatement("update HRM_EMP_ADDL_CHARGE" + " set DESIGNATION_ID=?,DATE_EFFECTIVE_FROM=?,REMARKS=?," +
                       "OFFICE_GRADE=?,DEPARTMENT_ID=?,PROCESS_FLOW_STATUS_ID=?,EMPLOYEE_STATUS_ID=?," +
                       "LEAVE_TYPE_CODE=?,DATE_EFFECTIVE_FROM_SESSION=?,UPDATED_DATE=?,UPDATED_BY_USER_ID=? where employee_id=? and office_id=?");


                ps.setInt(1, designation);
                ps.setDate(2, dtfrom);
                ps.setString(3, remarks);
                ps.setString(4, officegrade);
                ps.setString(5, deptid);
                ps.setString(6, "MD");
                ps.setString(7, statusid);
                ps.setString(8, null);
                ps.setString(9, dtfromsession);
                ps.setTimestamp(10, sqldt);
                ps.setString(11, updatedby);
                // ps.setInt(12,wing);
                ps.setInt(12, strcode);
                ps.setInt(13, officeid);

                int y = ps.executeUpdate();
                con.commit();
                sendMessage(response, "Record is Updated Successfully", "ok");

                //  }

                if (statusid.equalsIgnoreCase("STU")) {

                    String[] sd1 =
                        request.getParameter("txtStudyDateFrom").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd1[2]), Integer.parseInt(sd1[1]) -
                         1, Integer.parseInt(sd1[0]));
                    java.util.Date d1 = c.getTime();
                    dtfrom = new Date(d1.getTime());

                    dtfromsession = request.getParameter("optStudyDateFrom");

                    System.out.println("From Date:" + dtfrom + " " +
                                       dtfromsession);

                    remarks = request.getParameter("txtstudyremark");
                    System.out.println("txtstudyremark:" + remarks);

                    con.setAutoCommit(false);


                    ps =
  con.prepareStatement("delete from HRM_EMP_CURRENT_POSTING_TMP " +
                       " where  EMPLOYEE_ID=? and (PROCESS_FLOW_STATUS_ID!='DL') ");
                    ps.setInt(1, strcode);
                    ps.executeUpdate();

                    ps =
  con.prepareStatement("insert into HRM_EMP_CURRENT_POSTING_TMP" +
                       "(EMPLOYEE_ID,DATE_EFFECTIVE_FROM,REMARKS," +
                       "OFFICE_GRADE,PROCESS_FLOW_STATUS_ID,EMPLOYEE_STATUS_ID," +
                       "DATE_EFFECTIVE_FROM_SESSION,UPDATED_DATE,UPDATED_BY_USER_ID,DESIGNATION_ID)" +
                       " values(?,?,?,?,?,?,?,?,?,?)");
                    ps.setInt(1, strcode);
                    ps.setDate(2, dtfrom);
                    ps.setString(3, remarks);
                    ps.setString(4, officegrade);
                    ps.setString(5, "MD");
                    ps.setString(6, statusid);
                    ps.setString(7, dtfromsession);
                    ps.setTimestamp(8, sqldt);
                    ps.setString(9, updatedby);

                    ps.setInt(10, designation);
                    ps.executeUpdate();

                    System.out.println("designation:" + designation);

                    System.out.println("current posting is ok");
                    String sql =
                        "select CONTROLLING_OFFICE_ID from HRM_EMP_CONTROLLING_OFFICE where employee_id=?";
                    ps = con.prepareStatement(sql);
                    ps.setInt(1, strcode);
                    rs = ps.executeQuery();
                    int OfficeId = 0;
                    if (rs.next()) {
                        OfficeId = rs.getInt("CONTROLLING_OFFICE_ID");
                    }
                    System.out.println("Office Id:" + OfficeId);
                    System.out.println("date :" +
                                       request.getParameter("txtStudyDateFrom"));
                    sd = request.getParameter("txtStudyDateFrom").split("/");
                    int Year1 = Integer.parseInt(sd[2]);
                    ps =
  con.prepareStatement("SELECT MAX(JOINING_REPORT_ID) AS b FROM HRM_EMP_STU_JOIN_REPORTS where OFFICE_ID=? and JOINING_YEAR=?");
                    ps.setInt(1, OfficeId);
                    ps.setInt(2, Year1);
                    rs = ps.executeQuery();
                    int b = 0;
                    if (rs.next()) {
                        b = rs.getInt("b") + 1;
                        System.out.println("b is " + b);

                    }

                    ps =
  con.prepareStatement("select max(JOINING_REPORT_ID) repid,employee_id,JOINING_YEAR from HRM_EMP_STU_JOIN_REPORTS where EMPLOYEE_ID=? and (PROCESS_FLOW_STATUS_ID='CR' or PROCESS_FLOW_STATUS_ID='MD') group by employee_id,JOINING_YEAR");
                    ps.setInt(1, strcode);
                    rs = ps.executeQuery();
                    if (!rs.next()) {


                        ps =
  con.prepareStatement("INSERT INTO HRM_EMP_STU_JOIN_REPORTS" +
                       "(OFFICE_ID,JOINING_YEAR,JOINING_REPORT_ID,EMPLOYEE_ID,STUDIES_ACTUAL_JOIN_DATE,SESSION_AN_FN," +
                       "STUDIES_JOIN_REMARKS,PROCESS_FLOW_STATUS_ID,INSTITUTION_NAME,INSTITUTION_LOCATION, COURSE_NAME ,SR_CONTROLLING_OFFICE_ID,UPDATED_BY_USER_ID,UPDATED_DATE)" +
                       "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                        ps.setInt(1, OfficeId);
                        ps.setInt(2, Year1);
                        ps.setInt(3, b);
                        ps.setInt(4, strcode);
                        ps.setDate(5, dtfrom);
                        ps.setString(6, dtfromsession);
                        ps.setString(7, remarks);
                        ps.setString(8, "MD");

                        String txtInstName =
                            request.getParameter("txtInst_Name");
                        String txtInstLocation =
                            request.getParameter("txtInst_Location");
                        String txtCourseName =
                            request.getParameter("txtCourse_Name");


                        ps.setString(9, txtInstName);
                        ps.setString(10, txtInstLocation);
                        ps.setString(11, txtCourseName);
                        ps.setInt(12, OfficeId);

                        ps.setString(13, updatedby);
                        ps.setTimestamp(14, ts);

                        ps.executeUpdate();
                        xml = xml + "<flag>success</flag>";
                    } else {
                        // xml=xml+"<flag>failure1</flag>";
                        int repid = rs.getInt("repid");
                        int year2 = rs.getInt("JOINING_YEAR");

                        ps =
  con.prepareStatement("update HRM_EMP_STU_JOIN_REPORTS " +
                       "set OFFICE_ID=?,JOINING_YEAR=?,JOINING_REPORT_ID=?,EMPLOYEE_ID=?,STUDIES_ACTUAL_JOIN_DATE=?," +
                       " SESSION_AN_FN=?,STUDIES_JOIN_REMARKS=?,PROCESS_FLOW_STATUS_ID=?,INSTITUTION_NAME=?,INSTITUTION_LOCATION=?," +
                       " COURSE_NAME=? ,SR_CONTROLLING_OFFICE_ID=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=? " +
                       " where employee_id=? and OFFICE_ID=? and JOINING_REPORT_ID=? and JOINING_YEAR=?");
                        ps.setInt(1, OfficeId);
                        ps.setInt(2, Year1);
                        ps.setInt(3, repid);
                        ps.setInt(4, strcode);
                        ps.setDate(5, dtfrom);
                        ps.setString(6, dtfromsession);
                        ps.setString(7, remarks);
                        ps.setString(8, "MD");

                        String txtInstName =
                            request.getParameter("txtInst_Name");
                        String txtInstLocation =
                            request.getParameter("txtInst_Location");
                        String txtCourseName =
                            request.getParameter("txtCourse_Name");

                        ps.setString(9, txtInstName);
                        ps.setString(10, txtInstLocation);
                        ps.setString(11, txtCourseName);
                        ps.setInt(12, OfficeId);

                        ps.setString(13, updatedby);
                        ps.setTimestamp(14, ts);

                        ps.setInt(15, strcode);
                        ps.setInt(16, OfficeId);
                        ps.setInt(17, repid);
                        ps.setInt(18, year2);

                        ps.executeUpdate();
                        xml = xml + "<flag>success</flag>";


                    }


                    con.commit();
                    sendMessage(response, "Record is Updated Successfully",
                                "ok");

                }


                else if (statusid.equalsIgnoreCase("DPN")) {

                    deptid = request.getParameter("txtDept_Id");
                    System.out.println("txtDept_Id:" + deptid);

                    officeid =
                            Integer.parseInt(request.getParameter("txtOthOffice_Id"));
                    System.out.println("txtOthOffice_Id:" + officeid);

                    String[] sd2 =
                        request.getParameter("txtDepDateFrom").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd2[2]), Integer.parseInt(sd2[1]) -
                         1, Integer.parseInt(sd2[0]));
                    java.util.Date d2 = c.getTime();
                    dtfrom = new Date(d2.getTime());

                    dtfromsession = request.getParameter("optDepDateFrom");

                    System.out.println("From Date:" + dtfrom + " " +
                                       dtfromsession);

                    remarks = request.getParameter("txtDepremark");
                    System.out.println("txtDepremark:" + remarks);

                    con.setAutoCommit(false);
                    ps =
  con.prepareStatement("delete from HRM_EMP_CURRENT_POSTING_TMP " +
                       " where  EMPLOYEE_ID=? and (PROCESS_FLOW_STATUS_ID!='DL') ");
                    ps.setInt(1, strcode);
                    ps.executeUpdate();

                    ps =
  con.prepareStatement("insert into HRM_EMP_CURRENT_POSTING_TMP" +
                       "(EMPLOYEE_ID,OFFICE_ID,DESIGNATION_ID,DATE_EFFECTIVE_FROM,REMARKS," +
                       "OFFICE_GRADE,DEPARTMENT_ID,PROCESS_FLOW_STATUS_ID,EMPLOYEE_STATUS_ID," +
                       "LEAVE_TYPE_CODE,DATE_EFFECTIVE_FROM_SESSION,UPDATED_DATE,UPDATED_BY_USER_ID)" +
                       " values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
                    ps.setInt(1, strcode);
                    ps.setInt(2, officeid);
                    ps.setInt(3, designation);
                    ps.setDate(4, dtfrom);
                    ps.setString(5, remarks);
                    ps.setString(6, officegrade);
                    ps.setString(7, deptid);
                    ps.setString(8, "MD");
                    ps.setString(9, statusid);
                    ps.setString(10, null);
                    ps.setString(11, dtfromsession);
                    ps.setTimestamp(12, sqldt);
                    ps.setString(13, updatedby);

                    ps.executeUpdate();
                    con.commit();
                    sendMessage(response, "Record is Updated Successfully",
                                "ok");

                } else if (statusid.equalsIgnoreCase("LLV")) {

                    leavetype = request.getParameter("cmbleavetype");
                    System.out.println("cmbleavetype:" + leavetype);

                    String[] sd3 =
                        request.getParameter("txtDateFrom").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd3[2]), Integer.parseInt(sd3[1]) -
                         1, Integer.parseInt(sd3[0]));
                    java.util.Date d3 = c.getTime();
                    dtfrom = new Date(d3.getTime());

                    dtfromsession = request.getParameter("optDateFrom");

                    System.out.println("From Date:" + dtfrom + " " +
                                       dtfromsession);

                    remarks = request.getParameter("txtleaveremark");
                    System.out.println("txtleaveremark:" + remarks);

                    con.setAutoCommit(false);
                    ps =
  con.prepareStatement("delete from HRM_EMP_CURRENT_POSTING_TMP " +
                       " where  EMPLOYEE_ID=? and (PROCESS_FLOW_STATUS_ID!='DL') ");
                    ps.setInt(1, strcode);
                    ps.executeUpdate();

                    ps =
  con.prepareStatement("insert into HRM_EMP_CURRENT_POSTING_TMP" +
                       "(EMPLOYEE_ID,DESIGNATION_ID,DATE_EFFECTIVE_FROM,REMARKS," +
                       "OFFICE_GRADE,PROCESS_FLOW_STATUS_ID,EMPLOYEE_STATUS_ID," +
                       "LEAVE_TYPE_CODE,DATE_EFFECTIVE_FROM_SESSION,UPDATED_DATE,UPDATED_BY_USER_ID)" +
                       " values(?,?,?,?,?,?,?,?,?,?,?)");
                    ps.setInt(1, strcode);
                    ps.setInt(2, designation);
                    ps.setDate(3, dtfrom);
                    ps.setString(4, remarks);
                    ps.setString(5, officegrade);
                    ps.setString(6, "MD");
                    ps.setString(7, statusid);
                    ps.setString(8, leavetype);
                    ps.setString(9, dtfromsession);
                    ps.setTimestamp(10, sqldt);
                    ps.setString(11, updatedby);

                    ps.executeUpdate();
                    con.commit();
                    sendMessage(response, "Record is Updated Successfully",
                                "ok");

                } else if (statusid.equalsIgnoreCase("SUS")) {
                    String[] sd4 =
                        request.getParameter("txtSusFrom").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd4[2]), Integer.parseInt(sd4[1]) -
                         1, Integer.parseInt(sd4[0]));
                    java.util.Date d4 = c.getTime();
                    dtfrom = new Date(d4.getTime());

                    dtfromsession = request.getParameter("optSusFrom");

                    System.out.println("From Date:" + dtfrom + " " +
                                       dtfromsession);

                    remarks = request.getParameter("txtsusreson");
                    System.out.println("txtsusreson:" + remarks);

                    con.setAutoCommit(false);
                    ps =
  con.prepareStatement("delete from HRM_EMP_CURRENT_POSTING_TMP " +
                       " where  EMPLOYEE_ID=? and (PROCESS_FLOW_STATUS_ID!='DL') ");
                    ps.setInt(1, strcode);
                    ps.executeUpdate();

                    ps =
  con.prepareStatement("insert into HRM_EMP_CURRENT_POSTING_TMP" +
                       "(EMPLOYEE_ID,DESIGNATION_ID,DATE_EFFECTIVE_FROM,REMARKS," +
                       "OFFICE_GRADE,PROCESS_FLOW_STATUS_ID,EMPLOYEE_STATUS_ID," +
                       "DATE_EFFECTIVE_FROM_SESSION,UPDATED_DATE,UPDATED_BY_USER_ID)" +
                       " values(?,?,?,?,?,?,?,?,?,?)");
                    ps.setInt(1, strcode);
                    ps.setInt(2, designation);
                    ps.setDate(3, dtfrom);
                    ps.setString(4, remarks);
                    ps.setString(5, officegrade);
                    ps.setString(6, "MD");
                    ps.setString(7, statusid);
                    ps.setString(8, dtfromsession);
                    ps.setTimestamp(9, sqldt);
                    ps.setString(10, updatedby);

                    ps.executeUpdate();
                    con.commit();
                    sendMessage(response, "Record is Updated Successfully",
                                "ok");
                }

                /* TRANSIT */
                else if (statusid.equalsIgnoreCase("TRT")) {
                    String[] sd5 =
                        request.getParameter("txtTrtFrom").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd5[2]), Integer.parseInt(sd5[1]) -
                         1, Integer.parseInt(sd5[0]));
                    java.util.Date d5 = c.getTime();
                    dtfrom = new Date(d5.getTime());

                    dtfromsession = request.getParameter("optTrtFrom");

                    System.out.println("From Date:" + dtfrom + " " +
                                       dtfromsession);

                    remarks = request.getParameter("txtTrtreson");
                    System.out.println("txtTrtreson:" + remarks);

                    con.setAutoCommit(false);
                    ps =
  con.prepareStatement("delete from HRM_EMP_CURRENT_POSTING_TMP " +
                       " where  EMPLOYEE_ID=? and (PROCESS_FLOW_STATUS_ID!='DL') ");
                    ps.setInt(1, strcode);
                    ps.executeUpdate();

                    ps =
  con.prepareStatement("insert into HRM_EMP_CURRENT_POSTING_TMP" +
                       "(EMPLOYEE_ID,DESIGNATION_ID,DATE_EFFECTIVE_FROM,REMARKS," +
                       "OFFICE_GRADE,PROCESS_FLOW_STATUS_ID,EMPLOYEE_STATUS_ID," +
                       "DATE_EFFECTIVE_FROM_SESSION,UPDATED_DATE,UPDATED_BY_USER_ID)" +
                       " values(?,?,?,?,?,?,?,?,?,?)");
                    ps.setInt(1, strcode);
                    ps.setInt(2, designation);
                    ps.setDate(3, dtfrom);
                    ps.setString(4, remarks);
                    ps.setString(5, officegrade);
                    ps.setString(6, "MD");
                    ps.setString(7, statusid);
                    ps.setString(8, dtfromsession);
                    ps.setTimestamp(9, sqldt);
                    ps.setString(10, updatedby);

                    ps.executeUpdate();
                    con.commit();
                    sendMessage(response, "Record is Updated Successfully",
                                "ok");
                }

                /* UNAUTHERIZED LEAVE  */
                else if (statusid.equalsIgnoreCase("UAL")) {
                    String[] sd6 =
                        request.getParameter("txtUalFrom").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd6[2]), Integer.parseInt(sd6[1]) -
                         1, Integer.parseInt(sd6[0]));
                    java.util.Date d6 = c.getTime();
                    dtfrom = new Date(d6.getTime());

                    dtfromsession = request.getParameter("optUalFrom");

                    System.out.println("From Date:" + dtfrom + " " +
                                       dtfromsession);

                    remarks = request.getParameter("txtUalreson");
                    System.out.println("txtUalreson:" + remarks);

                    con.setAutoCommit(false);
                    ps =
  con.prepareStatement("delete from HRM_EMP_CURRENT_POSTING_TMP " +
                       " where  EMPLOYEE_ID=? and (PROCESS_FLOW_STATUS_ID!='DL') ");
                    ps.setInt(1, strcode);
                    ps.executeUpdate();

                    ps =
  con.prepareStatement("insert into HRM_EMP_CURRENT_POSTING_TMP" +
                       "(EMPLOYEE_ID,DESIGNATION_ID,DATE_EFFECTIVE_FROM,REMARKS," +
                       "OFFICE_GRADE,PROCESS_FLOW_STATUS_ID,EMPLOYEE_STATUS_ID," +
                       "DATE_EFFECTIVE_FROM_SESSION,UPDATED_DATE,UPDATED_BY_USER_ID)" +
                       " values(?,?,?,?,?,?,?,?,?,?)");
                    ps.setInt(1, strcode);
                    ps.setInt(2, designation);
                    ps.setDate(3, dtfrom);
                    ps.setString(4, remarks);
                    ps.setString(5, officegrade);
                    ps.setString(6, "MD");
                    ps.setString(7, statusid);
                    ps.setString(8, dtfromsession);
                    ps.setTimestamp(9, sqldt);
                    ps.setString(10, updatedby);

                    ps.executeUpdate();
                    con.commit();
                    sendMessage(response, "Record is Updated Successfully",
                                "ok");
                }

                /* COMPULSARY WAIT */
                else if (statusid.equalsIgnoreCase("CMW")) {
                    String[] sd7 =
                        request.getParameter("txtCmwFrom").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd7[2]), Integer.parseInt(sd7[1]) -
                         1, Integer.parseInt(sd7[0]));
                    java.util.Date d7 = c.getTime();
                    dtfrom = new Date(d7.getTime());

                    dtfromsession = request.getParameter("optCmwFrom");

                    System.out.println("From Date:" + dtfrom + " " +
                                       dtfromsession);

                    remarks = request.getParameter("txtCmwreson");
                    System.out.println("txtCmwreson:" + remarks);

                    con.setAutoCommit(false);
                    ps =
  con.prepareStatement("delete from HRM_EMP_CURRENT_POSTING_TMP " +
                       " where  EMPLOYEE_ID=? and (PROCESS_FLOW_STATUS_ID!='DL') ");
                    ps.setInt(1, strcode);
                    ps.executeUpdate();

                    ps =
  con.prepareStatement("insert into HRM_EMP_CURRENT_POSTING_TMP" +
                       "(EMPLOYEE_ID,DESIGNATION_ID,DATE_EFFECTIVE_FROM,REMARKS," +
                       "OFFICE_GRADE,PROCESS_FLOW_STATUS_ID,EMPLOYEE_STATUS_ID," +
                       "DATE_EFFECTIVE_FROM_SESSION,UPDATED_DATE,UPDATED_BY_USER_ID)" +
                       " values(?,?,?,?,?,?,?,?,?,?)");
                    ps.setInt(1, strcode);
                    ps.setInt(2, designation);
                    ps.setDate(3, dtfrom);
                    ps.setString(4, remarks);
                    ps.setString(5, officegrade);
                    ps.setString(6, "MD");
                    ps.setString(7, statusid);
                    ps.setString(8, dtfromsession);
                    ps.setTimestamp(9, sqldt);
                    ps.setString(10, updatedby);

                    ps.executeUpdate();
                    con.commit();
                    sendMessage(response, "Record is Updated Successfully",
                                "ok");
                }


                else if (statusid.equalsIgnoreCase("ABS")) {
                    String[] sd8 =
                        request.getParameter("txtAbsFrom").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd8[2]), Integer.parseInt(sd8[1]) -
                         1, Integer.parseInt(sd8[0]));
                    java.util.Date d8 = c.getTime();
                    dtfrom = new Date(d8.getTime());

                    dtfromsession = request.getParameter("optAbsFrom");

                    System.out.println("From Date:" + dtfrom + " " +
                                       dtfromsession);

                    remarks = request.getParameter("txtabsremark");
                    System.out.println("txtabsremark:" + remarks);

                    con.setAutoCommit(false);
                    ps =
  con.prepareStatement("delete from HRM_EMP_CURRENT_POSTING_TMP " +
                       " where  EMPLOYEE_ID=? and (PROCESS_FLOW_STATUS_ID!='DL') ");
                    ps.setInt(1, strcode);
                    ps.executeUpdate();

                    ps =
  con.prepareStatement("insert into HRM_EMP_CURRENT_POSTING_TMP" +
                       "(EMPLOYEE_ID,DESIGNATION_ID,DATE_EFFECTIVE_FROM,REMARKS," +
                       "OFFICE_GRADE,PROCESS_FLOW_STATUS_ID,EMPLOYEE_STATUS_ID," +
                       "DATE_EFFECTIVE_FROM_SESSION,UPDATED_DATE,UPDATED_BY_USER_ID)" +
                       " values(?,?,?,?,?,?,?,?,?,?)");
                    ps.setInt(1, strcode);
                    ps.setInt(2, designation);
                    ps.setDate(3, dtfrom);
                    ps.setString(4, remarks);
                    ps.setString(5, officegrade);
                    ps.setString(6, "MD");
                    ps.setString(7, statusid);
                    ps.setString(8, dtfromsession);
                    ps.setTimestamp(9, sqldt);
                    ps.setString(10, updatedby);

                    ps.executeUpdate();

                    con.commit();


                    sendMessage(response, "Record is Updated Successfully",
                                "ok");

                }

            } catch (Exception e) {
                String err = "Record is not Updated. " + e.getMessage();
                try {
                    con.rollback();
                } catch (Exception e1) {
                    System.out.println(e1);
                }
                sendMessage(response, err, "ok");

            } finally {
                try {
                    con.setAutoCommit(true);
                } catch (Exception e1) {
                    System.out.println(e1);
                }
            }

        }

        if (strCommand.equalsIgnoreCase("Validate")) {
            System.out.println("Command :" + strCommand);

            try {
                HttpSession session = request.getSession(false);
                String updatedby = (String)session.getAttribute("UserId");
                long l = System.currentTimeMillis();
                Timestamp ts = new Timestamp(l);

                java.util.Date dt =
                    new java.util.Date(System.currentTimeMillis());
                java.sql.Timestamp sqldt =
                    new java.sql.Timestamp(dt.getTime());
                System.out.println("Time Stamp:" + sqldt);
                //  if(statusid.equalsIgnoreCase("WKG"))            {

                deptid = request.getParameter("txtDept_Id_work");
                System.out.println("txtDept_Id_work:" + deptid);

                officeid = Integer.parseInt(request.getParameter("offid"));
                System.out.println("txtOffice_Id:" + officeid);

                int wing = 0;
                /*    if(request.getParameter("cmbWing")!=null) {

                           wing=Integer.parseInt(request.getParameter("cmbWing"));

                       }*/

                String[] sd =
                    request.getParameter("txtWorkDateFrom").split("/");
                c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                java.util.Date d = c.getTime();
                dtfrom = new Date(d.getTime());

                dtfromsession = request.getParameter("optWorkDateFrom");

                System.out.println("From Date:" + dtfrom + " " +
                                   dtfromsession);

                remarks = request.getParameter("txtworkremark");
                System.out.println("txtworkremark:" + remarks);
                dttosession = request.getParameter("optclosuredate");
                sd = request.getParameter("txtclosuredate").split("/");
                c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                d = c.getTime();
                dtto = new Date(d.getTime());
                String todate = request.getParameter("txtclosuredate");
                // String remarks1=request.getParameter("revokeremarks");

                con.setAutoCommit(false);


                /*  ps=con.prepareStatement("delete from HRM_EMP_ADDL_CHARGE " +
                        " where  EMPLOYEE_ID=? and office_id=? and ( PROCESS_FLOW_STATUS_ID!='DL') ");
                        ps.setInt(1,strcode);
                        ps.setInt(2,officeid);
                        ps.executeUpdate();*/


                ps =
  con.prepareStatement("update HRM_EMP_ADDL_CHARGE" + " set DESIGNATION_ID=?,DATE_EFFECTIVE_FROM=?,REMARKS=?," +
                       "OFFICE_GRADE=?,DEPARTMENT_ID=?,PROCESS_FLOW_STATUS_ID=?,EMPLOYEE_STATUS_ID=?," +
                       "LEAVE_TYPE_CODE=?,DATE_EFFECTIVE_FROM_SESSION=?,UPDATED_DATE=?,UPDATED_BY_USER_ID=?,CLOSURE_DATE=?,ADDL_CHARGE_STATUS=?,DATE_EFFECTIVE_TO_SESSION=? where employee_id=? and office_id=?");


                ps.setInt(1, designation);
                ps.setDate(2, dtfrom);
                ps.setString(3, remarks);
                ps.setString(4, officegrade);
                ps.setString(5, deptid);
                ps.setString(6, "FR");
                ps.setString(7, statusid);
                ps.setString(8, null);
                ps.setString(9, dtfromsession);
                ps.setTimestamp(10, sqldt);
                ps.setString(11, updatedby);
                ps.setDate(12, dtto);
                ps.setString(13, "CL");
                ps.setString(14, dttosession);
                // ps.setInt(12,wing);
                ps.setInt(15, strcode);
                ps.setInt(16, officeid);

                int y = ps.executeUpdate();
                con.commit();
                sendMessage(response, "Record is Revoked Successfully", "ok");

                //  }

                if (statusid.equalsIgnoreCase("STU")) {

                    String[] sd1 =
                        request.getParameter("txtStudyDateFrom").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd1[2]), Integer.parseInt(sd1[1]) -
                         1, Integer.parseInt(sd1[0]));
                    java.util.Date d1 = c.getTime();
                    dtfrom = new Date(d1.getTime());

                    dtfromsession = request.getParameter("optStudyDateFrom");

                    System.out.println("From Date:" + dtfrom + " " +
                                       dtfromsession);

                    remarks = request.getParameter("txtstudyremark");
                    System.out.println("txtstudyremark:" + remarks);

                    con.setAutoCommit(false);


                    ps =
  con.prepareStatement("delete from HRM_EMP_CURRENT_POSTING_TMP " +
                       " where  EMPLOYEE_ID=? and (PROCESS_FLOW_STATUS_ID!='DL') ");
                    ps.setInt(1, strcode);
                    ps.executeUpdate();

                    ps =
  con.prepareStatement("insert into HRM_EMP_CURRENT_POSTING_TMP" +
                       "(EMPLOYEE_ID,DATE_EFFECTIVE_FROM,REMARKS," +
                       "OFFICE_GRADE,PROCESS_FLOW_STATUS_ID,EMPLOYEE_STATUS_ID," +
                       "DATE_EFFECTIVE_FROM_SESSION,UPDATED_DATE,UPDATED_BY_USER_ID,DESIGNATION_ID)" +
                       " values(?,?,?,?,?,?,?,?,?,?)");
                    ps.setInt(1, strcode);
                    ps.setDate(2, dtfrom);
                    ps.setString(3, remarks);
                    ps.setString(4, officegrade);
                    ps.setString(5, "MD");
                    ps.setString(6, statusid);
                    ps.setString(7, dtfromsession);
                    ps.setTimestamp(8, sqldt);
                    ps.setString(9, updatedby);

                    ps.setInt(10, designation);
                    ps.executeUpdate();

                    System.out.println("designation:" + designation);

                    System.out.println("current posting is ok");
                    String sql =
                        "select CONTROLLING_OFFICE_ID from HRM_EMP_CONTROLLING_OFFICE where employee_id=?";
                    ps = con.prepareStatement(sql);
                    ps.setInt(1, strcode);
                    rs = ps.executeQuery();
                    int OfficeId = 0;
                    if (rs.next()) {
                        OfficeId = rs.getInt("CONTROLLING_OFFICE_ID");
                    }
                    System.out.println("Office Id:" + OfficeId);
                    System.out.println("date :" +
                                       request.getParameter("txtStudyDateFrom"));
                    sd = request.getParameter("txtStudyDateFrom").split("/");
                    int Year1 = Integer.parseInt(sd[2]);
                    ps =
  con.prepareStatement("SELECT MAX(JOINING_REPORT_ID) AS b FROM HRM_EMP_STU_JOIN_REPORTS where OFFICE_ID=? and JOINING_YEAR=?");
                    ps.setInt(1, OfficeId);
                    ps.setInt(2, Year1);
                    rs = ps.executeQuery();
                    int b = 0;
                    if (rs.next()) {
                        b = rs.getInt("b") + 1;
                        System.out.println("b is " + b);

                    }

                    ps =
  con.prepareStatement("select max(JOINING_REPORT_ID) repid,employee_id,JOINING_YEAR from HRM_EMP_STU_JOIN_REPORTS where EMPLOYEE_ID=? and (PROCESS_FLOW_STATUS_ID='CR' or PROCESS_FLOW_STATUS_ID='MD') group by employee_id,JOINING_YEAR");
                    ps.setInt(1, strcode);
                    rs = ps.executeQuery();
                    if (!rs.next()) {


                        ps =
  con.prepareStatement("INSERT INTO HRM_EMP_STU_JOIN_REPORTS" +
                       "(OFFICE_ID,JOINING_YEAR,JOINING_REPORT_ID,EMPLOYEE_ID,STUDIES_ACTUAL_JOIN_DATE,SESSION_AN_FN," +
                       "STUDIES_JOIN_REMARKS,PROCESS_FLOW_STATUS_ID,INSTITUTION_NAME,INSTITUTION_LOCATION, COURSE_NAME ,SR_CONTROLLING_OFFICE_ID,UPDATED_BY_USER_ID,UPDATED_DATE)" +
                       "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                        ps.setInt(1, OfficeId);
                        ps.setInt(2, Year1);
                        ps.setInt(3, b);
                        ps.setInt(4, strcode);
                        ps.setDate(5, dtfrom);
                        ps.setString(6, dtfromsession);
                        ps.setString(7, remarks);
                        ps.setString(8, "MD");

                        String txtInstName =
                            request.getParameter("txtInst_Name");
                        String txtInstLocation =
                            request.getParameter("txtInst_Location");
                        String txtCourseName =
                            request.getParameter("txtCourse_Name");


                        ps.setString(9, txtInstName);
                        ps.setString(10, txtInstLocation);
                        ps.setString(11, txtCourseName);
                        ps.setInt(12, OfficeId);

                        ps.setString(13, updatedby);
                        ps.setTimestamp(14, ts);

                        ps.executeUpdate();
                        xml = xml + "<flag>success</flag>";
                    } else {
                        // xml=xml+"<flag>failure1</flag>";
                        int repid = rs.getInt("repid");
                        int year2 = rs.getInt("JOINING_YEAR");

                        ps =
  con.prepareStatement("update HRM_EMP_STU_JOIN_REPORTS " +
                       "set OFFICE_ID=?,JOINING_YEAR=?,JOINING_REPORT_ID=?,EMPLOYEE_ID=?,STUDIES_ACTUAL_JOIN_DATE=?," +
                       " SESSION_AN_FN=?,STUDIES_JOIN_REMARKS=?,PROCESS_FLOW_STATUS_ID=?,INSTITUTION_NAME=?,INSTITUTION_LOCATION=?," +
                       " COURSE_NAME=? ,SR_CONTROLLING_OFFICE_ID=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=? " +
                       " where employee_id=? and OFFICE_ID=? and JOINING_REPORT_ID=? and JOINING_YEAR=?");
                        ps.setInt(1, OfficeId);
                        ps.setInt(2, Year1);
                        ps.setInt(3, repid);
                        ps.setInt(4, strcode);
                        ps.setDate(5, dtfrom);
                        ps.setString(6, dtfromsession);
                        ps.setString(7, remarks);
                        ps.setString(8, "MD");

                        String txtInstName =
                            request.getParameter("txtInst_Name");
                        String txtInstLocation =
                            request.getParameter("txtInst_Location");
                        String txtCourseName =
                            request.getParameter("txtCourse_Name");

                        ps.setString(9, txtInstName);
                        ps.setString(10, txtInstLocation);
                        ps.setString(11, txtCourseName);
                        ps.setInt(12, OfficeId);

                        ps.setString(13, updatedby);
                        ps.setTimestamp(14, ts);

                        ps.setInt(15, strcode);
                        ps.setInt(16, OfficeId);
                        ps.setInt(17, repid);
                        ps.setInt(18, year2);

                        ps.executeUpdate();
                        xml = xml + "<flag>success</flag>";


                    }


                    con.commit();
                    sendMessage(response, "Record is Updated Successfully",
                                "ok");

                }


                else if (statusid.equalsIgnoreCase("DPN")) {

                    deptid = request.getParameter("txtDept_Id");
                    System.out.println("txtDept_Id:" + deptid);

                    officeid =
                            Integer.parseInt(request.getParameter("txtOthOffice_Id"));
                    System.out.println("txtOthOffice_Id:" + officeid);

                    String[] sd2 =
                        request.getParameter("txtDepDateFrom").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd2[2]), Integer.parseInt(sd2[1]) -
                         1, Integer.parseInt(sd2[0]));
                    java.util.Date d2 = c.getTime();
                    dtfrom = new Date(d2.getTime());

                    dtfromsession = request.getParameter("optDepDateFrom");

                    System.out.println("From Date:" + dtfrom + " " +
                                       dtfromsession);

                    remarks = request.getParameter("txtDepremark");
                    System.out.println("txtDepremark:" + remarks);

                    con.setAutoCommit(false);
                    ps =
  con.prepareStatement("delete from HRM_EMP_CURRENT_POSTING_TMP " +
                       " where  EMPLOYEE_ID=? and (PROCESS_FLOW_STATUS_ID!='DL') ");
                    ps.setInt(1, strcode);
                    ps.executeUpdate();

                    ps =
  con.prepareStatement("insert into HRM_EMP_CURRENT_POSTING_TMP" +
                       "(EMPLOYEE_ID,OFFICE_ID,DESIGNATION_ID,DATE_EFFECTIVE_FROM,REMARKS," +
                       "OFFICE_GRADE,DEPARTMENT_ID,PROCESS_FLOW_STATUS_ID,EMPLOYEE_STATUS_ID," +
                       "LEAVE_TYPE_CODE,DATE_EFFECTIVE_FROM_SESSION,UPDATED_DATE,UPDATED_BY_USER_ID)" +
                       " values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
                    ps.setInt(1, strcode);
                    ps.setInt(2, officeid);
                    ps.setInt(3, designation);
                    ps.setDate(4, dtfrom);
                    ps.setString(5, remarks);
                    ps.setString(6, officegrade);
                    ps.setString(7, deptid);
                    ps.setString(8, "MD");
                    ps.setString(9, statusid);
                    ps.setString(10, null);
                    ps.setString(11, dtfromsession);
                    ps.setTimestamp(12, sqldt);
                    ps.setString(13, updatedby);

                    ps.executeUpdate();
                    con.commit();
                    sendMessage(response, "Record is Updated Successfully",
                                "ok");

                } else if (statusid.equalsIgnoreCase("LLV")) {

                    leavetype = request.getParameter("cmbleavetype");
                    System.out.println("cmbleavetype:" + leavetype);

                    String[] sd3 =
                        request.getParameter("txtDateFrom").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd3[2]), Integer.parseInt(sd3[1]) -
                         1, Integer.parseInt(sd3[0]));
                    java.util.Date d3 = c.getTime();
                    dtfrom = new Date(d3.getTime());

                    dtfromsession = request.getParameter("optDateFrom");

                    System.out.println("From Date:" + dtfrom + " " +
                                       dtfromsession);

                    remarks = request.getParameter("txtleaveremark");
                    System.out.println("txtleaveremark:" + remarks);

                    con.setAutoCommit(false);
                    ps =
  con.prepareStatement("delete from HRM_EMP_CURRENT_POSTING_TMP " +
                       " where  EMPLOYEE_ID=? and (PROCESS_FLOW_STATUS_ID!='DL') ");
                    ps.setInt(1, strcode);
                    ps.executeUpdate();

                    ps =
  con.prepareStatement("insert into HRM_EMP_CURRENT_POSTING_TMP" +
                       "(EMPLOYEE_ID,DESIGNATION_ID,DATE_EFFECTIVE_FROM,REMARKS," +
                       "OFFICE_GRADE,PROCESS_FLOW_STATUS_ID,EMPLOYEE_STATUS_ID," +
                       "LEAVE_TYPE_CODE,DATE_EFFECTIVE_FROM_SESSION,UPDATED_DATE,UPDATED_BY_USER_ID)" +
                       " values(?,?,?,?,?,?,?,?,?,?,?)");
                    ps.setInt(1, strcode);
                    ps.setInt(2, designation);
                    ps.setDate(3, dtfrom);
                    ps.setString(4, remarks);
                    ps.setString(5, officegrade);
                    ps.setString(6, "MD");
                    ps.setString(7, statusid);
                    ps.setString(8, leavetype);
                    ps.setString(9, dtfromsession);
                    ps.setTimestamp(10, sqldt);
                    ps.setString(11, updatedby);

                    ps.executeUpdate();
                    con.commit();
                    sendMessage(response, "Record is Updated Successfully",
                                "ok");

                } else if (statusid.equalsIgnoreCase("SUS")) {
                    String[] sd4 =
                        request.getParameter("txtSusFrom").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd4[2]), Integer.parseInt(sd4[1]) -
                         1, Integer.parseInt(sd4[0]));
                    java.util.Date d4 = c.getTime();
                    dtfrom = new Date(d4.getTime());

                    dtfromsession = request.getParameter("optSusFrom");

                    System.out.println("From Date:" + dtfrom + " " +
                                       dtfromsession);

                    remarks = request.getParameter("txtsusreson");
                    System.out.println("txtsusreson:" + remarks);

                    con.setAutoCommit(false);
                    ps =
  con.prepareStatement("delete from HRM_EMP_CURRENT_POSTING_TMP " +
                       " where  EMPLOYEE_ID=? and (PROCESS_FLOW_STATUS_ID!='DL') ");
                    ps.setInt(1, strcode);
                    ps.executeUpdate();

                    ps =
  con.prepareStatement("insert into HRM_EMP_CURRENT_POSTING_TMP" +
                       "(EMPLOYEE_ID,DESIGNATION_ID,DATE_EFFECTIVE_FROM,REMARKS," +
                       "OFFICE_GRADE,PROCESS_FLOW_STATUS_ID,EMPLOYEE_STATUS_ID," +
                       "DATE_EFFECTIVE_FROM_SESSION,UPDATED_DATE,UPDATED_BY_USER_ID)" +
                       " values(?,?,?,?,?,?,?,?,?,?)");
                    ps.setInt(1, strcode);
                    ps.setInt(2, designation);
                    ps.setDate(3, dtfrom);
                    ps.setString(4, remarks);
                    ps.setString(5, officegrade);
                    ps.setString(6, "MD");
                    ps.setString(7, statusid);
                    ps.setString(8, dtfromsession);
                    ps.setTimestamp(9, sqldt);
                    ps.setString(10, updatedby);

                    ps.executeUpdate();
                    con.commit();
                    sendMessage(response, "Record is Updated Successfully",
                                "ok");
                }

                /* TRANSIT */
                else if (statusid.equalsIgnoreCase("TRT")) {
                    String[] sd5 =
                        request.getParameter("txtTrtFrom").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd5[2]), Integer.parseInt(sd5[1]) -
                         1, Integer.parseInt(sd5[0]));
                    java.util.Date d5 = c.getTime();
                    dtfrom = new Date(d5.getTime());

                    dtfromsession = request.getParameter("optTrtFrom");

                    System.out.println("From Date:" + dtfrom + " " +
                                       dtfromsession);

                    remarks = request.getParameter("txtTrtreson");
                    System.out.println("txtTrtreson:" + remarks);

                    con.setAutoCommit(false);
                    ps =
  con.prepareStatement("delete from HRM_EMP_CURRENT_POSTING_TMP " +
                       " where  EMPLOYEE_ID=? and (PROCESS_FLOW_STATUS_ID!='DL') ");
                    ps.setInt(1, strcode);
                    ps.executeUpdate();

                    ps =
  con.prepareStatement("insert into HRM_EMP_CURRENT_POSTING_TMP" +
                       "(EMPLOYEE_ID,DESIGNATION_ID,DATE_EFFECTIVE_FROM,REMARKS," +
                       "OFFICE_GRADE,PROCESS_FLOW_STATUS_ID,EMPLOYEE_STATUS_ID," +
                       "DATE_EFFECTIVE_FROM_SESSION,UPDATED_DATE,UPDATED_BY_USER_ID)" +
                       " values(?,?,?,?,?,?,?,?,?,?)");
                    ps.setInt(1, strcode);
                    ps.setInt(2, designation);
                    ps.setDate(3, dtfrom);
                    ps.setString(4, remarks);
                    ps.setString(5, officegrade);
                    ps.setString(6, "MD");
                    ps.setString(7, statusid);
                    ps.setString(8, dtfromsession);
                    ps.setTimestamp(9, sqldt);
                    ps.setString(10, updatedby);

                    ps.executeUpdate();
                    con.commit();
                    sendMessage(response, "Record is Updated Successfully",
                                "ok");
                }

                /* UNAUTHERIZED LEAVE  */
                else if (statusid.equalsIgnoreCase("UAL")) {
                    String[] sd6 =
                        request.getParameter("txtUalFrom").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd6[2]), Integer.parseInt(sd6[1]) -
                         1, Integer.parseInt(sd6[0]));
                    java.util.Date d6 = c.getTime();
                    dtfrom = new Date(d6.getTime());

                    dtfromsession = request.getParameter("optUalFrom");

                    System.out.println("From Date:" + dtfrom + " " +
                                       dtfromsession);

                    remarks = request.getParameter("txtUalreson");
                    System.out.println("txtUalreson:" + remarks);

                    con.setAutoCommit(false);
                    ps =
  con.prepareStatement("delete from HRM_EMP_CURRENT_POSTING_TMP " +
                       " where  EMPLOYEE_ID=? and (PROCESS_FLOW_STATUS_ID!='DL') ");
                    ps.setInt(1, strcode);
                    ps.executeUpdate();

                    ps =
  con.prepareStatement("insert into HRM_EMP_CURRENT_POSTING_TMP" +
                       "(EMPLOYEE_ID,DESIGNATION_ID,DATE_EFFECTIVE_FROM,REMARKS," +
                       "OFFICE_GRADE,PROCESS_FLOW_STATUS_ID,EMPLOYEE_STATUS_ID," +
                       "DATE_EFFECTIVE_FROM_SESSION,UPDATED_DATE,UPDATED_BY_USER_ID)" +
                       " values(?,?,?,?,?,?,?,?,?,?)");
                    ps.setInt(1, strcode);
                    ps.setInt(2, designation);
                    ps.setDate(3, dtfrom);
                    ps.setString(4, remarks);
                    ps.setString(5, officegrade);
                    ps.setString(6, "MD");
                    ps.setString(7, statusid);
                    ps.setString(8, dtfromsession);
                    ps.setTimestamp(9, sqldt);
                    ps.setString(10, updatedby);

                    ps.executeUpdate();
                    con.commit();
                    sendMessage(response, "Record is Updated Successfully",
                                "ok");
                }

                /* COMPULSARY WAIT */
                else if (statusid.equalsIgnoreCase("CMW")) {
                    String[] sd7 =
                        request.getParameter("txtCmwFrom").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd7[2]), Integer.parseInt(sd7[1]) -
                         1, Integer.parseInt(sd7[0]));
                    java.util.Date d7 = c.getTime();
                    dtfrom = new Date(d7.getTime());

                    dtfromsession = request.getParameter("optCmwFrom");

                    System.out.println("From Date:" + dtfrom + " " +
                                       dtfromsession);

                    remarks = request.getParameter("txtCmwreson");
                    System.out.println("txtCmwreson:" + remarks);

                    con.setAutoCommit(false);
                    ps =
  con.prepareStatement("delete from HRM_EMP_CURRENT_POSTING_TMP " +
                       " where  EMPLOYEE_ID=? and (PROCESS_FLOW_STATUS_ID!='DL') ");
                    ps.setInt(1, strcode);
                    ps.executeUpdate();

                    ps =
  con.prepareStatement("insert into HRM_EMP_CURRENT_POSTING_TMP" +
                       "(EMPLOYEE_ID,DESIGNATION_ID,DATE_EFFECTIVE_FROM,REMARKS," +
                       "OFFICE_GRADE,PROCESS_FLOW_STATUS_ID,EMPLOYEE_STATUS_ID," +
                       "DATE_EFFECTIVE_FROM_SESSION,UPDATED_DATE,UPDATED_BY_USER_ID)" +
                       " values(?,?,?,?,?,?,?,?,?,?)");
                    ps.setInt(1, strcode);
                    ps.setInt(2, designation);
                    ps.setDate(3, dtfrom);
                    ps.setString(4, remarks);
                    ps.setString(5, officegrade);
                    ps.setString(6, "MD");
                    ps.setString(7, statusid);
                    ps.setString(8, dtfromsession);
                    ps.setTimestamp(9, sqldt);
                    ps.setString(10, updatedby);

                    ps.executeUpdate();
                    con.commit();
                    sendMessage(response, "Record is Updated Successfully",
                                "ok");
                }


                else if (statusid.equalsIgnoreCase("ABS")) {
                    String[] sd8 =
                        request.getParameter("txtAbsFrom").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd8[2]), Integer.parseInt(sd8[1]) -
                         1, Integer.parseInt(sd8[0]));
                    java.util.Date d8 = c.getTime();
                    dtfrom = new Date(d8.getTime());

                    dtfromsession = request.getParameter("optAbsFrom");

                    System.out.println("From Date:" + dtfrom + " " +
                                       dtfromsession);

                    remarks = request.getParameter("txtabsremark");
                    System.out.println("txtabsremark:" + remarks);

                    con.setAutoCommit(false);
                    ps =
  con.prepareStatement("delete from HRM_EMP_CURRENT_POSTING_TMP " +
                       " where  EMPLOYEE_ID=? and (PROCESS_FLOW_STATUS_ID!='DL') ");
                    ps.setInt(1, strcode);
                    ps.executeUpdate();

                    ps =
  con.prepareStatement("insert into HRM_EMP_CURRENT_POSTING_TMP" +
                       "(EMPLOYEE_ID,DESIGNATION_ID,DATE_EFFECTIVE_FROM,REMARKS," +
                       "OFFICE_GRADE,PROCESS_FLOW_STATUS_ID,EMPLOYEE_STATUS_ID," +
                       "DATE_EFFECTIVE_FROM_SESSION,UPDATED_DATE,UPDATED_BY_USER_ID)" +
                       " values(?,?,?,?,?,?,?,?,?,?)");
                    ps.setInt(1, strcode);
                    ps.setInt(2, designation);
                    ps.setDate(3, dtfrom);
                    ps.setString(4, remarks);
                    ps.setString(5, officegrade);
                    ps.setString(6, "MD");
                    ps.setString(7, statusid);
                    ps.setString(8, dtfromsession);
                    ps.setTimestamp(9, sqldt);
                    ps.setString(10, updatedby);

                    ps.executeUpdate();

                    con.commit();


                    sendMessage(response, "Record is Updated Successfully",
                                "ok");

                }

            } catch (Exception e) {
                String err = "Record is not Updated. " + e.getMessage();
                try {
                    con.rollback();
                } catch (Exception e1) {
                    System.out.println(e1);
                }
                sendMessage(response, err, "ok");

            } finally {
                try {
                    con.setAutoCommit(true);
                } catch (Exception e1) {
                    System.out.println(e1);
                }
            }

        }


        if (strCommand.equalsIgnoreCase("Delete")) {
            System.out.println("Command :" + strCommand);

            try {
                HttpSession session = request.getSession(false);
                String updatedby = (String)session.getAttribute("UserId");
                long l = System.currentTimeMillis();
                Timestamp ts = new Timestamp(l);

                java.util.Date dt =
                    new java.util.Date(System.currentTimeMillis());
                java.sql.Timestamp sqldt =
                    new java.sql.Timestamp(dt.getTime());
                System.out.println("Time Stamp:" + sqldt);
                //  if(statusid.equalsIgnoreCase("WKG"))            {

                deptid = request.getParameter("txtDept_Id_work");
                System.out.println("txtDept_Id_work:" + deptid);

                officeid = Integer.parseInt(request.getParameter("offid"));
                System.out.println("txtOffice_Id:" + officeid);

                int wing = 0;
                /*    if(request.getParameter("cmbWing")!=null) {

                           wing=Integer.parseInt(request.getParameter("cmbWing"));

                       }*/

                String[] sd =
                    request.getParameter("txtWorkDateFrom").split("/");
                c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                java.util.Date d = c.getTime();
                dtfrom = new Date(d.getTime());

                dtfromsession = request.getParameter("optWorkDateFrom");

                System.out.println("From Date:" + dtfrom + " " +
                                   dtfromsession);

                remarks = request.getParameter("txtworkremark");
                System.out.println("txtworkremark:" + remarks);

                con.setAutoCommit(false);


                ps =
  con.prepareStatement("delete from HRM_EMP_ADDL_CHARGE " + " where  EMPLOYEE_ID=? and office_id=? and ( PROCESS_FLOW_STATUS_ID!='DL') ");
                ps.setInt(1, strcode);
                ps.setInt(2, officeid);
                ps.executeUpdate();


                ps =
  con.prepareStatement("insert into HRM_EMP_ADDL_CHARGE_TMP" +
                       "(EMPLOYEE_ID,OFFICE_ID,DESIGNATION_ID,DATE_EFFECTIVE_FROM,REMARKS," +
                       "OFFICE_GRADE,DEPARTMENT_ID,PROCESS_FLOW_STATUS_ID,EMPLOYEE_STATUS_ID," +
                       "LEAVE_TYPE_CODE,DATE_EFFECTIVE_FROM_SESSION,UPDATED_DATE,UPDATED_BY_USER_ID,OFFICE_WING_SINO)" +
                       " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                ps.setInt(1, strcode);
                ps.setInt(2, officeid);
                ps.setInt(3, designation);
                ps.setDate(4, dtfrom);
                ps.setString(5, remarks);
                ps.setString(6, officegrade);
                ps.setString(7, deptid);
                ps.setString(8, "DL");
                ps.setString(9, statusid);
                ps.setString(10, null);
                ps.setString(11, dtfromsession);
                ps.setTimestamp(12, sqldt);
                ps.setString(13, updatedby);
                ps.setInt(14, wing);
                ps.executeUpdate();
                con.commit();
                sendMessage(response, "Record is Deleted Successfully", "ok");

            } catch (Exception e) {
                String err = "Record is not Deleted. " + e.getMessage();
                sendMessage(response, err, "ok");
            }


            if (strCommand.equalsIgnoreCase("Validateold")) {
                System.out.println("Command :" + strCommand);

                try {
                    HttpSession session = request.getSession(false);
                    String updatedby = (String)session.getAttribute("UserId");
                    long l = System.currentTimeMillis();
                    Timestamp ts = new Timestamp(l);

                    java.util.Date dt =
                        new java.util.Date(System.currentTimeMillis());
                    java.sql.Timestamp sqldt =
                        new java.sql.Timestamp(dt.getTime());
                    System.out.println("Time Stamp:" + sqldt);
                    //  if(statusid.equalsIgnoreCase("WKG"))            {

                    deptid = request.getParameter("txtDept_Id_work");
                    System.out.println("txtDept_Id_work:" + deptid);

                    officeid = Integer.parseInt(request.getParameter("offid"));
                    System.out.println("txtOffice_Id:" + officeid);

                    int wing = 0;
                    /*    if(request.getParameter("cmbWing")!=null) {

                               wing=Integer.parseInt(request.getParameter("cmbWing"));

                           }*/

                    String[] sd =
                        request.getParameter("txtWorkDateFrom").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                    java.util.Date d = c.getTime();
                    dtfrom = new Date(d.getTime());

                    dtfromsession = request.getParameter("optWorkDateFrom");

                    System.out.println("From Date:" + dtfrom + " " +
                                       dtfromsession);

                    remarks = request.getParameter("txtworkremark");
                    System.out.println("txtworkremark:" + remarks);

                    con.setAutoCommit(false);


                    /*  ps=con.prepareStatement("delete from HRM_EMP_ADDL_CHARGE " +
                            " where  EMPLOYEE_ID=? and office_id=? and ( PROCESS_FLOW_STATUS_ID!='DL') ");
                            ps.setInt(1,strcode);
                            ps.setInt(2,officeid);
                            ps.executeUpdate();*/


                    ps =
  con.prepareStatement("update HRM_EMP_ADDL_CHARGE" + " set DESIGNATION_ID=?,DATE_EFFECTIVE_FROM=?,REMARKS=?," +
                       "OFFICE_GRADE=?,DEPARTMENT_ID=?,PROCESS_FLOW_STATUS_ID=?,EMPLOYEE_STATUS_ID=?," +
                       "LEAVE_TYPE_CODE=?,DATE_EFFECTIVE_FROM_SESSION=?,UPDATED_DATE=?,UPDATED_BY_USER_ID=? where employee_id=? and office_id=?");


                    ps.setInt(1, designation);
                    ps.setDate(2, dtfrom);
                    ps.setString(3, remarks);
                    ps.setString(4, officegrade);
                    ps.setString(5, deptid);
                    ps.setString(6, "FR");
                    ps.setString(7, statusid);
                    ps.setString(8, null);
                    ps.setString(9, dtfromsession);
                    ps.setTimestamp(10, sqldt);
                    ps.setString(11, updatedby);
                    // ps.setInt(12,wing);
                    ps.setInt(12, strcode);
                    ps.setInt(13, officeid);

                    int y = ps.executeUpdate();
                    con.commit();
                    sendMessage(response, "Record is Validated Successfully",
                                "ok");

                    //  }

                } catch (Exception e) {
                    String err = "Record is not Validated. " + e.getMessage();
                    try {
                        con.rollback();
                    } catch (Exception e1) {
                        System.out.println(e1);
                    }
                    sendMessage(response, err, "ok");

                } finally {
                    try {
                        con.setAutoCommit(true);
                    } catch (Exception e1) {
                        System.out.println(e1);
                    }
                }

            }


        }


        ///////////////
    }


    private void sendMessage(HttpServletResponse response, String msg,
                             String bType) {
        try {
            String url =
                "org/Library/jsps/Messenger_addlcharge_update.jsp?message=" +
                msg + "&button=" + bType;
            response.sendRedirect(url);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}


