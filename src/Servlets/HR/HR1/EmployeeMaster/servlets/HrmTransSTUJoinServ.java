package Servlets.HR.HR1.EmployeeMaster.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.text.SimpleDateFormat;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class HrmTransSTUJoinServ extends HttpServlet {
    private static final String CONTENT_TYPE = "text/xml";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);

    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);
        Connection con = null;
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
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
        } catch (Exception e) {
            System.out.println("Exception in connection..." + e);
        }
        ResultSet rs = null;
        //ResultSet rs1=null;
        //CallableStatement cs=null;

        PreparedStatement ps = null;
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


        java.sql.Date f = null;
        java.sql.Date cdt = null;
        int strOffcode = 0;
        int strEmpId = 0;
        int strEId = 0;
        int strJR = 0;
        String strNoon = "";
        String strDOJ = "";
        String strDesign = "";
        String strpostcount = "";
        String strrem = "", compsession = "";
        //java.sql.Date strDOJ=null;
        String xml = "";
        String strCommand = "";
        String empstatus = "";
        String sr_cntrl_offid = "";
        int Year1 = 0;
        int b = 0;
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");

        System.out.println("hai");

        try {

            /*strCommand=request.getParameter("Command");
            System.out.println("assign....."+strCommand);*/


            System.out.println("hai");

            strEId = Integer.parseInt(request.getParameter("comEmpId"));
            System.out.println("emp id...." + strEId);
        } catch (Exception e) {
            System.out.println("Exception in first assigning..." + e);
        }

        try {

            System.out.println("Year is....." + request.getParameter("JYear"));
            Year1 = Integer.parseInt(request.getParameter("JYear"));

        }

        catch (Exception e) {
            System.out.println("in third.." + e);
        }
        try {

            strCommand = request.getParameter("Command");
            System.out.println("assign....." + strCommand);

            strOffcode = Integer.parseInt(request.getParameter("txtOffId"));
            System.out.println("txtOffId....." + strOffcode);

            strEmpId = Integer.parseInt(request.getParameter("txtEmpId"));
            System.out.println("txtEmpId...." + strEmpId);


            strJR = Integer.parseInt(request.getParameter("txtJR"));
            System.out.println("txtJR...." + strJR);


            strNoon = request.getParameter("radFNAN");
            System.out.println("radFNAN..." + strNoon);

            empstatus = request.getParameter("empstatus");
            System.out.println("empstatus..." + empstatus);

            sr_cntrl_offid = request.getParameter("sr_cntrl_offid");
            System.out.println("sr_cntrl_offid...." + sr_cntrl_offid);

            strDOJ = request.getParameter("txtDOJ");

            // Date Conversion for Date
            // java.sql.Date f=null;
            System.out.println("before converting date");
            String dateString = strDOJ;
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date d;
            d = dateFormat.parse(strDOJ.trim());
            dateFormat.applyPattern("yyyy-MM-dd");
            dateString = dateFormat.format(d);
            f = java.sql.Date.valueOf(dateString);

            //f=java.sql.Date.valueOf(strDOJ);
            System.out.println("txtDOJ....." + f);
            //f = Date.valueOf(strDOJ);

            strrem = request.getParameter("txtRemarks");
            System.out.println("txtRemarks...." + strrem);


        }

        catch (Exception e) {
            System.out.println("Exception in second assigning..." + e);
        }


        if (strCommand.equalsIgnoreCase("Add")) {
            xml = "<response><command>Add</command>";
            //String sql="insert into TEST_STATE values(?,?)";
            // int id=0;
            try {

                System.out.println("Inside Add" + strOffcode);
                System.out.println("Inside Add" + Year1);
                System.out.println("Inside Add" + strJR);
                System.out.println("Inside Add" + strEmpId);
                System.out.println("Inside Add" + f);
                System.out.println("Inside Add" + strNoon);
                //System.out.println("Inside Add"+strDesign);
                //System.out.println("Inside Add"+strpostcount);
                System.out.println("Inside Add" + strrem);

                String txtInstName = request.getParameter("txtInstName");
                String txtInstLocation =
                    request.getParameter("txtInstLocation");
                String txtCourseName = request.getParameter("txtCourseName");

                int srctrloffid = 0;

                session = request.getSession(false);
                String updatedby = (String)session.getAttribute("UserId");
                long l = System.currentTimeMillis();
                java.sql.Timestamp ts = new java.sql.Timestamp(l);

                try {
                    srctrloffid = Integer.parseInt(sr_cntrl_offid);
                } catch (Exception e) {
                    System.out.println(e);
                }

                ps =
  con.prepareStatement("select EMPLOYEE_ID from HRM_EMP_STU_JOIN_REPORTS where EMPLOYEE_ID=? and (PROCESS_FLOW_STATUS_ID='CR' or PROCESS_FLOW_STATUS_ID='MD')");
                ps.setInt(1, strEmpId);
                rs = ps.executeQuery();
                if (!rs.next()) {

                    System.out.println();
                    ps =
  con.prepareStatement("INSERT INTO HRM_EMP_STU_JOIN_REPORTS" +
                       "(OFFICE_ID,JOINING_YEAR,JOINING_REPORT_ID,EMPLOYEE_ID,STUDIES_ACTUAL_JOIN_DATE,SESSION_AN_FN," +
                       "STUDIES_JOIN_REMARKS,PROCESS_FLOW_STATUS_ID,INSTITUTION_NAME,INSTITUTION_LOCATION, COURSE_NAME	,SR_CONTROLLING_OFFICE_ID,UPDATED_BY_USER_ID,UPDATED_DATE)" +
                       "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                    ps.setInt(1, strOffcode);
                    ps.setInt(2, Year1);
                    ps.setInt(3, strJR);
                    ps.setInt(4, strEmpId);
                    ps.setDate(5, f);
                    ps.setString(6, strNoon);
                    ps.setString(7, strrem);
                    ps.setString(8, "CR");

                    ps.setString(9, txtInstName);
                    ps.setString(10, txtInstLocation);
                    ps.setString(11, txtCourseName);
                    ps.setInt(12, srctrloffid);

                    ps.setString(13, updatedby);
                    ps.setTimestamp(14, ts);

                    ps.executeUpdate();
                    xml = xml + "<flag>success</flag>";
                } else {
                    xml = xml + "<flag>failure1</flag>";
                }
            }

            catch (Exception e) {

                System.out.println("Exception in inserting emp details........" +
                                   e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }


        else if (strCommand.equalsIgnoreCase("dispDesign")) {
            System.out.println("Insiden Year " + Year1);
            String sxml = "<response><command>dispDesign</command>";

            try {
                ps =
  con.prepareStatement("SELECT MAX(JOINING_REPORT_ID) AS b FROM HRM_EMP_STU_JOIN_REPORTS where OFFICE_ID=? and JOINING_YEAR=?");
                ps.setInt(1, strOffcode);
                ps.setInt(2, Year1);

                rs = ps.executeQuery();
                if (rs.next()) {
                    b = rs.getInt("b");
                    System.out.println("b is " + b);

                }
                int j = b;
                System.out.println("b is" + j);

                /*int i=0;
                System.out.println("hai");
                if(rs.next()) {
                j=rs.getInt("JOIN_REPORT_ID");
                i=i+1;
                System.out.println(".."+i);
                }*/
                if (j == 0) {
                    j = 1;
                    System.out.println("i...." + j);
                    xml = sxml + "<flag>success</flag><j>" + j + "</j>";
                } else {
                    j = j + 1;
                    System.out.println("i.." + j);
                    xml = sxml + "<flag>success</flag><j>" + j + "</j>";
                }
            } catch (Exception e) {
                System.out.println("catch in x...." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }

        else if (strCommand.equalsIgnoreCase("disp")) {

            xml = "<response><command>disp</command>";
            //String sql="insert into TEST_STATE values(?,?)";
            try {

                ps =
  con.prepareStatement("SELECT EMPLOYEE_NAME ||decode(EMPLOYEE_INITIAL,null,' ','.'||EMPLOYEE_INITIAL) as  EMPLOYEE_NAME ,DATE_OF_BIRTH,GPF_NO FROM HRM_MST_EMPLOYEES WHERE EMPLOYEE_ID=?");

                ps.setInt(1, strEmpId);

                rs = ps.executeQuery();

                while (rs.next()) {

                    //java.sql.Date DateOfFormation = rs.getDate("DATE_OF_BIRTH");
                    String[] sd =
                        rs.getDate("DATE_OF_BIRTH").toString().split("-");
                    String od = sd[2] + "/" + sd[1] + "/" + sd[0];
                    String name = rs.getString("EMPLOYEE_NAME");
                    System.out.println(name);
                    // String dob=rs.getString("DATE_OF_BIRTH");
                    System.out.println("od::" + od);
                    int gpfno = rs.getInt("GPF_NO");
                    System.out.println(gpfno);
                    xml =
 xml + "<flag>success</flag><ename>" + name + "</ename><dob>" + od +
   "</dob><gpfno>" + gpfno + "</gpfno>";
                }

                ///////////////////////
            } catch (Exception e) {

                System.out.println("Exception in displaying emp details........" +
                                   e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }


        else if (strCommand.equalsIgnoreCase("dispEmp")) {

            xml = "<response><command>dispEmp</command>";
            //String sql="insert into TEST_STATE values(?,?)";
            try {
                System.out.println("disp emp");
                System.out.println("emp id:" + strEId);
                ps =
  con.prepareStatement("SELECT EMPLOYEE_ID FROM HRM_MST_EMPLOYEES WHERE EMPLOYEE_ID=?");
                ps.setInt(1, strEId);
                rs = ps.executeQuery();
                if (!rs.next()) {
                    xml = xml + "<flag>failure</flag>";
                } else {
                    ps =
  con.prepareStatement("SELECT EMPLOYEE_STATUS_ID  FROM HRM_EMP_CURRENT_POSTING  WHERE EMPLOYEE_ID=?");
                    ps.setInt(1, strEId);
                    rs = ps.executeQuery();
                    if (!rs.next()) {
                        xml = xml + "<flag>failure1</flag>";
                    } else {
                        System.out.println(rs.getString("EMPLOYEE_STATUS_ID"));
                        if (rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("WKG")) {
                            xml = xml + "<flag>failure3_1</flag>";
                        } else if (rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("SAN")) {
                            xml = xml + "<flag>failure3_2</flag>";
                        } else if (rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("DIS")) {
                            xml = xml + "<flag>failure3_3</flag>";
                        } else if (rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("VRS")) {
                            xml = xml + "<flag>failure3_4</flag>";
                        }

                        else if (rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("DPN")) {
                            xml = xml + "<flag>failure3_6</flag>";
                        }
                        /* else    if(rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("STT")) {
                                xml=xml+"<flag>failure3_7</flag>";
                        }*/
                        else if (!rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("STT")) {

                            xml = xml + "<flag>failure3_5</flag>";
                        } else {
                            rs.close();
                            ps.close();
                            ps =
  con.prepareStatement("SELECT EMPLOYEE_ID FROM HRM_EMP_STU_JOIN_REPORTS WHERE EMPLOYEE_ID=? and (PROCESS_FLOW_STATUS_ID='CR' or PROCESS_FLOW_STATUS_ID='MD')");
                            ps.setInt(1, strEId);
                            rs = ps.executeQuery();
                            if (rs.next()) {
                                xml = xml + "<flag>failure2</flag>";
                                // System.out.println("hai");
                            } else {


                                rs.close();
                                ps.close();

                                ps =
  con.prepareStatement("SELECT EMPLOYEE_ID,EMPLOYEE_NAME ||decode(EMPLOYEE_INITIAL,null,' ','.'||EMPLOYEE_INITIAL) as  EMPLOYEE_NAME ,DATE_OF_BIRTH,GPF_NO FROM HRM_MST_EMPLOYEES WHERE EMPLOYEE_ID=?");

                                ps.setInt(1, strEId);

                                rs = ps.executeQuery();
                                int c = 0;
                                while (rs.next()) {

                                    java.sql.Date DateOfFormation =
                                        rs.getDate("DATE_OF_BIRTH");
                                    String DateToBeDisplayed = "";
                                    if (DateOfFormation == null) {
                                        DateToBeDisplayed = "Not Specified";
                                    } else {
                                        try {
                                            java.text.SimpleDateFormat sdf =
                                                new java.text.SimpleDateFormat("dd/MM/yyyy");
                                            DateToBeDisplayed =
                                                    sdf.format(DateOfFormation);
                                        } catch (Exception e) {
                                            System.out.println("error while formatting date : " +
                                                               e);
                                            DateToBeDisplayed =
                                                    "Not Specified";
                                        }
                                    }
                                    String id = rs.getString("EMPLOYEE_ID");
                                    System.out.println(id);
                                    String name =
                                        rs.getString("EMPLOYEE_NAME");
                                    System.out.println(name);
                                    //String dob=rs.getString("DATE_OF_BIRTH");
                                    //System.out.println(dob);
                                    int gpfno = rs.getInt("GPF_NO");
                                    System.out.println(gpfno);

                                    //ps=con.prepareStatement("select max(date_to) as maxtodate from HRM_EMP_SERVICE_DATA where EMPLOYEE_ID=?");
                                    ps =
  con.prepareStatement("select DATE_EFFECTIVE_FROM as maxtodate,DATE_EFFECTIVE_FROM_SESSION  from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?");

                                    ps.setInt(1, strEId);

                                    rs = ps.executeQuery();
                                    String maxtodate = "";
                                    String maxsession = "";
                                    if (rs.next()) {
                                        if (rs.getDate("maxtodate") != null) {
                                            maxtodate =
                                                    new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("maxtodate"));
                                            System.out.println("max to date :" +
                                                               rs.getDate("maxtodate"));
                                        } else {

                                            maxtodate = "empty";
                                        }
                                        if (rs.getString("DATE_EFFECTIVE_FROM_SESSION") !=
                                            null) {
                                            maxsession =
                                                    rs.getString("DATE_EFFECTIVE_FROM_SESSION");
                                        } else {
                                            maxsession = "FN";
                                        }
                                    }

                                    ps =
  con.prepareStatement("select EMPLOYEE_STATUS_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?");
                                    ps.setInt(1, strEId);
                                    rs = ps.executeQuery();

                                    if (rs.next()) {
                                        if (rs.getString("EMPLOYEE_STATUS_ID") !=
                                            null) {
                                            empstatus =
                                                    rs.getString("EMPLOYEE_STATUS_ID");
                                        } else {

                                            empstatus = "WKG";
                                        }
                                    }

                                    xml =
 xml + "<flag>success</flag><eid>" + id + "</eid><dob>" + DateToBeDisplayed +
   "</dob><gpfno>" + gpfno + "</gpfno><name>" + name + "</name><maxtodate>" +
   maxtodate + "</maxtodate><workingstatus>" + empstatus +
   "</workingstatus><maxsession>" + maxsession + "</maxsession>";

                                    // ps=con.prepareStatement("select d.DESIGNATION,c.DATE_EFFECTIVE_FROM ,f.OTHER_DEPT_OFFICE_NAME,g.OTHER_DEPT_NAME from HRM_EMP_CURRENT_POSTING c,HRM_MST_DESIGNATIONS d,HRM_MST_OTHER_DEPT_OFFICES f,HRM_MST_OTHER_DEPTS g where g.OTHER_DEPT_ID=c.DEPARTMENT_ID and f.OTHER_DEPT_OFFICE_ID=c.OFFICE_ID and f.OTHER_DEPT_ID=c.DEPARTMENT_ID  and  c.DESIGNATION_ID=d.DESIGNATION_ID and      C.EMPLOYEE_ID=? ");
                                    ps =
  con.prepareStatement("select a.INSTITUTION_NAME,a.INSTITUTION_LOCATION,a.COURSE_NAME from HRM_EMP_RELIEVAL_STU a " +
                       " where     a.EMPLOYEE_ID=? AND A.RELIEVAL_SLNO = (select max(c.RELIEVAL_SLNO) from HRM_EMP_RELIEVAL_DETAILS c where c.employee_id=? and  c.PROCESS_FLOW_STATUS_ID ='FR')");
                                    ps.setInt(1, strEId);
                                    ps.setInt(2, strEId);
                                    //ps.setInt(2,offid);
                                    rs = ps.executeQuery();
                                    if (rs.next()) {

                                        String inst_name = "", inst_location =
                                            "", course_name = "";
                                        if (rs.getString("INSTITUTION_NAME") !=
                                            null) {
                                            inst_name =
                                                    rs.getString("INSTITUTION_NAME");
                                        } else {
                                            inst_name = "null";
                                        }
                                        //System.out.println("here is ok");

                                        if (rs.getString("INSTITUTION_LOCATION") !=
                                            null) {
                                            inst_location =
                                                    rs.getString("INSTITUTION_LOCATION");
                                        } else {
                                            inst_location = "null";
                                        }

                                        if (rs.getString("COURSE_NAME") !=
                                            null) {
                                            course_name =
                                                    rs.getString("COURSE_NAME");
                                        } else {
                                            course_name = "null";
                                        }

                                        xml =
 xml + "<inst_name>" + inst_name + "</inst_name><inst_location>" +
   inst_location + "</inst_location><course_name>" + course_name +
   "</course_name>";

                                    }


                                    c++;
                                }
                                if (c == 0) {
                                    xml = xml + "<flag>failure</flag>";
                                }
                            }


                        }

                    }

                }
            } catch (Exception e) {

                System.out.println("Exception in displaying emp details........" +
                                   e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }


        System.out.println("xml is:" + xml);
        out.write(xml);
        out.flush();
        out.close();
    }
}
