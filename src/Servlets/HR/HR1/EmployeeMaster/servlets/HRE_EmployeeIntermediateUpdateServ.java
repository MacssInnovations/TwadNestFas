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

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class HRE_EmployeeIntermediateUpdateServ extends HttpServlet {


    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {

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

        PrintWriter out = response.getWriter();
        String tablename = "";

        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");

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


        int l = session.getId().length();
        l = l - 10;

        tablename = "TEMPSR_" + session.getId().substring(l);
        //tablename="TEMPSR"+empid;
        System.out.println("Temp table Name:::" + tablename);
        int strcode = 0, slno = 0, designation = 0, officeid = 0;
        String statusid = "";
        Date dtfrom = null, dtto = null;
        String dtfromsession = "", dttosession = "", statusdetail =
            "", remarks = "", deptid = "";
        //String strname="";
        String xml = "";
        String strCommand = "";
        Calendar c;


        try {
            strCommand = request.getParameter("Command");
            System.out.println("assign....." + strCommand);

            if (strCommand.equalsIgnoreCase("loademp") ||
                strCommand.equalsIgnoreCase("test") ||
                strCommand.equalsIgnoreCase("LastDate")) {
                strcode =
                        Integer.parseInt(request.getParameter("txtEmployeeid"));
                System.out.println("assign..... Code::" + strcode);
            } else if (strCommand.equalsIgnoreCase("Add") ||
                       strCommand.equalsIgnoreCase("Update")) {

                if (strCommand.equalsIgnoreCase("Update")) {
                    slno = Integer.parseInt(request.getParameter("txtSNo"));
                    System.out.println("slno::" + slno);
                }

                strcode =
                        Integer.parseInt(request.getParameter("txtEmployeeid"));
                dtfromsession = request.getParameter("optDateFrom");
                dttosession = request.getParameter("optDateTo");
                String[] sd = request.getParameter("txtDateFrom").split("/");
                c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                java.util.Date d = c.getTime();
                dtfrom = new Date(d.getTime());
                sd = request.getParameter("txtDateTo").split("/");
                c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                d = c.getTime();
                dtto = new Date(d.getTime());
                statusid = request.getParameter("cmbStatus");
                statusdetail = request.getParameter("txtDetail");
                remarks = request.getParameter("txtRemark");


                System.out.println("assign..... dt from session::" +
                                   dtfromsession);
                System.out.println("assign..... dt to session::" +
                                   dttosession);
                System.out.println("assign..... dt from::" + dtfrom);
                System.out.println("assign..... dt to::" + dtto);


            } else if (strCommand.equalsIgnoreCase("Delete")) {
                slno = Integer.parseInt(request.getParameter("txtSNo"));
                strcode =
                        Integer.parseInt(request.getParameter("txtEmployeeid"));
            }


        }

        catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }

        if (strCommand.equalsIgnoreCase("loademp")) {
            xml = "<response><command>loademp</command>";
            try {
                //con.setAutoCommit(false);
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

                    //xml=xml+"<flag>failure4</flag>";
                    //flag=false;
                }

                if (flag) {
                    ps =
  con.prepareStatement("select EMPLOYEE_NAME||decode(EMPLOYEE_INITIAL,null,' ','.'||EMPLOYEE_INITIAL) as  EMPLOYEE_NAME,DATE_OF_BIRTH,GPF_NO from HRM_MST_EMPLOYEES  where EMPLOYEE_ID=?");
                    ps.setInt(1, strcode);
                    rs = ps.executeQuery();

                    if (rs.next()) {

                        xml =
 xml + "<ename>" + rs.getString("EMPLOYEE_NAME") + "</ename>";
                        if (rs.getDate("DATE_OF_BIRTH") != null) {
                            String[] sd =
                                rs.getDate("DATE_OF_BIRTH").toString().split("-");
                            String od = sd[2] + "/" + sd[1] + "/" + sd[0];
                            xml = xml + "<edob>" + od + "</edob>";
                        } else {
                            xml = xml + "<edob>-</edob>";
                        }
                        xml =
 xml + "<egpf>" + rs.getLong("GPF_NO") + "</egpf>";
                        rs.close();
                        ps.close();

                        String sql = "";
                        if (request.getParameter("param") == null) {
                            try {
                                sql = "drop table " + tablename;
                                ps = con.prepareStatement(sql);
                                ps.executeUpdate();
                                System.out.println("table droped");
                            } catch (Exception e) {
                                System.out.println("drop table error:" + e);
                            }
                            sql =
 "create  TABLE  " + tablename + " as (select * from  HRM_EMP_SERVICE_DATA where EMPLOYEE_ID=" +
   strcode + " )";
                            System.out.println(sql);
                            System.out.println("Temporary Table Name1:" +
                                               tablename);
                            st = con.createStatement();
                            //ps.setInt(1,strcode);
                            st.execute(sql);
                            System.out.println("temporary table created");
                        }


                        sql =
 "select * from ( " + " select a.SERVICE_LIST_SLNO,a.DATE_FROM,a.DATE_FROM_SESSION,a.DATE_TO,a.DATE_TO_SESSION, " +
   " a.EMPLOYEE_STATUS_ID,a.STATUS_DETAIL,a.REMARKS, " +
   " d.EMPLOYEE_STATUS_DESC " + " from " + tablename + "  a" +
   " left outer join HRM_MST_EMPLOYEE_STATUS d on   d.EMPLOYEE_STATUS_ID=a.EMPLOYEE_STATUS_ID   " +
   " where a.EMPLOYEE_ID=? and (a.EMPLOYEE_STATUS_ID = 'DPT' or a.EMPLOYEE_STATUS_ID ='TRT' or a.EMPLOYEE_STATUS_ID ='LLV' or a.EMPLOYEE_STATUS_ID ='TRL'  or a.EMPLOYEE_STATUS_ID ='TCL') and ( a.PROCESS_FLOW_STATUS_ID !='FR' or  a.PROCESS_FLOW_STATUS_ID is null  )  " +
   ") order by DATE_FROM";

                        System.out.println(sql);
                        ps = con.prepareStatement(sql);
                        ps.setInt(1, strcode);
                        rs = ps.executeQuery();
                        int serno = 0;
                        while (rs.next()) {

                            xml = xml + "<servicedata>";
                            xml = xml + "<SLNO>" + (++serno) + "</SLNO>";
                            xml =
 xml + "<SERVICE_LIST_SLNO>" + rs.getInt("SERVICE_LIST_SLNO") +
   "</SERVICE_LIST_SLNO>";
                            if (rs.getDate("DATE_FROM") != null) {
                                String[] sd =
                                    rs.getDate("DATE_FROM").toString().split("-");
                                String od = sd[2] + "/" + sd[1] + "/" + sd[0];
                                xml =
 xml + "<DATE_FROM>" + od + "</DATE_FROM>";
                            } else {
                                xml =
 xml + "<DATE_FROM>" + rs.getDate("DATE_FROM") + "</DATE_FROM>";

                            }
                            xml =
 xml + "<DATE_FROM_SESSION>" + rs.getString("DATE_FROM_SESSION") +
   "</DATE_FROM_SESSION>";
                            if (rs.getDate("DATE_TO") != null) {
                                String[] sd =
                                    rs.getDate("DATE_TO").toString().split("-");
                                String od = sd[2] + "/" + sd[1] + "/" + sd[0];
                                xml = xml + "<DATE_TO>" + od + "</DATE_TO>";
                            } else {
                                xml =
 xml + "<DATE_TO>" + rs.getDate("DATE_TO") + "</DATE_TO>";
                            }
                            xml =
 xml + "<DATE_TO_SESSION>" + rs.getString("DATE_TO_SESSION") +
   "</DATE_TO_SESSION>";
                            xml =
 xml + "<EMPLOYEE_STATUS_ID>" + rs.getString("EMPLOYEE_STATUS_ID") +
   "</EMPLOYEE_STATUS_ID>";
                            xml =
 xml + "<STATUS_DETAIL>" + rs.getString("STATUS_DETAIL") + "</STATUS_DETAIL>";
                            xml =
 xml + "<REMARKS>" + rs.getString("REMARKS") + "</REMARKS>";
                            xml =
 xml + "<EMPLOYEE_STATUS_DESC>" + rs.getString("EMPLOYEE_STATUS_DESC") +
   "</EMPLOYEE_STATUS_DESC>";

                            xml = xml + "</servicedata>";


                        }
                        rs.close();
                        ps.close();

                        xml = xml + "<flag>success</flag>";
                    }

                } else {
                    xml = xml + "<flag>failure</flag>";
                }


            }

            catch (Exception e) {

                System.out.println("catch........" + e);
                xml = xml + "<flag>failure</flag>";
            } finally {


            }
            xml = xml + "</response>";


        } else if (strCommand.equalsIgnoreCase("test_test")) {
            xml = "<response><command>test</command>";
            try {


                ps =
  con.prepareStatement("select * from HRM_MST_EMPLOYEES  where EMPLOYEE_ID=?");
                ps.setInt(1, strcode);
                rs = ps.executeQuery();
                if (rs.next()) {
                    xml = xml + "<flag>success</flag>";
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

        else if (strCommand.equalsIgnoreCase("Add")) {
            xml = "<response><command>Add</command>";
            boolean flag = true;
            try {
                String fromses = request.getParameter("optDateFrom");
                if (fromses.equalsIgnoreCase("FN"))
                    fromses = "AM";
                else
                    fromses = "PM";

                String strdtfrom =
                    request.getParameter("txtDateFrom") + " " + fromses;

                String sql =
                    "select EMPLOYEE_ID from " + tablename + " where to_date('" +
                    strdtfrom + "','DD/MM/YYYY AM')  >=" +
                    "  to_date(to_char(DATE_FROM,'DD/MM/YYYY')||' '||decode(DATE_FROM_SESSION,'FN','AM','AN','PM'),'DD/MM/YYYY AM') " +
                    " and to_date('" + strdtfrom +
                    "','DD/MM/YYYY AM') < to_date(to_char(DATE_TO,'DD/MM/YYYY')||' '||decode(DATE_To_SESSION,'FN','AM','AN','PM'),'DD/MM/YYYY AM') and EMPLOYEE_ID=?   and DATE_TO is not null";

                System.out.println(sql);
                ps = con.prepareStatement(sql);
                ps.setInt(1, strcode);
                rs = ps.executeQuery();
                if (rs.next()) {
                    xml = xml + "<flag>failure1</flag>";
                    flag = false;

                } else {

                    String toses = request.getParameter("optDateTo");
                    if (toses.equalsIgnoreCase("FN"))
                        toses = "AM";
                    else
                        toses = "PM";
                    String strdtto =
                        request.getParameter("txtDateTo") + " " + toses;
                    sql =
 "select EMPLOYEE_ID from " + tablename + " where to_date('" + strdtto +
   "','DD/MM/YYYY AM') > " +
   "  to_date(to_char(DATE_FROM,'DD/MM/YYYY')||' '||decode(DATE_FROM_SESSION,'FN','AM','AN','PM'),'DD/MM/YYYY AM') " +
   " and to_date('" + strdtto +
   "','DD/MM/YYYY AM') <= to_date(to_char(DATE_TO,'DD/MM/YYYY')||' '||decode(DATE_To_SESSION,'FN','AM','AN','PM'),'DD/MM/YYYY AM') and EMPLOYEE_ID=?   and DATE_TO is not null";

                    System.out.println("to date query:" + sql);
                    ps = con.prepareStatement(sql);
                    ps.setInt(1, strcode);
                    rs = ps.executeQuery();
                    if (rs.next()) {
                        xml = xml + "<flag>failure2</flag>";
                        flag = false;

                    } else {
                        fromses = request.getParameter("optDateFrom");
                        if (fromses.equalsIgnoreCase("FN"))
                            fromses = "AM";
                        else
                            fromses = "PM";
                        strdtfrom =
                                request.getParameter("txtDateFrom") + " " + fromses;

                        toses = request.getParameter("optDateTo");
                        if (toses.equalsIgnoreCase("FN"))
                            toses = "AM";
                        else
                            toses = "PM";
                        strdtto =
                                request.getParameter("txtDateTo") + " " + toses;

                        sql =
 " select EMPLOYEE_ID from " + tablename + " where " + " ((  to_date('" +
   strdtfrom +
   "','DD/MM/YYYY AM') < to_date(to_char(DATE_FROM,'DD/MM/YYYY')||' '||decode(DATE_FROM_SESSION,'FN','AM','AN','PM'),'DD/MM/YYYY AM')  " +
   "  and " + " to_date('" + strdtto +
   "','DD/MM/YYYY AM') > to_date(to_char(DATE_FROM,'DD/MM/YYYY')||' '||decode(DATE_FROM_SESSION,'FN','AM','AN','PM'),'DD/MM/YYYY AM')" +
   " )or ( to_date('" + strdtfrom +
   "','DD/MM/YYYY AM') < to_date(to_char(DATE_TO,'DD/MM/YYYY')||' '||decode(DATE_To_SESSION,'FN','AM','AN','PM'),'DD/MM/YYYY AM')  " +
   "   and " + " to_date('" + strdtto +
   "','DD/MM/YYYY AM') >  to_date(to_char(DATE_TO,'DD/MM/YYYY')||' '||decode(DATE_To_SESSION,'FN','AM','AN','PM'),'DD/MM/YYYY AM')  " +
   " )) and EMPLOYEE_ID=? and DATE_TO is not null";
                        System.out.println("to date query:" + sql);
                        ps = con.prepareStatement(sql);
                        System.out.println("here is testing");
                        ps.setInt(1, strcode);
                        rs = ps.executeQuery();
                        if (rs.next()) {
                            xml = xml + "<flag>failure3</flag>";
                            flag = false;

                        }

                    }

                }
                if (flag == true) {

                    /////////////////////////////
                    System.out.println("flag is true");
                    sql =
 "select max(SERVICE_LIST_SLNO) as maxval from " + tablename +
   " where EMPLOYEE_ID = ?";
                    ps = con.prepareStatement(sql);
                    ps.setInt(1, strcode);
                    rs = ps.executeQuery();
                    int sid = 0;
                    if (rs.next()) {
                        sid = rs.getInt("maxval");

                        if (sid > 0)
                            sid = sid + 1;
                        else
                            sid = 1;
                    }


                    sql =
 " insert into " + tablename + "(EMPLOYEE_ID,SERVICE_LIST_SLNO,DATE_FROM,DATE_FROM_SESSION,DATE_TO," +
   " DATE_TO_SESSION,DESIGNATION_ID, OFFICE_ID, EMPLOYEE_STATUS_ID, STATUS_DETAIL, REMARKS,OFFICE_DEPT_ID ) " +
   "   values (?,?,?,?,?,?,?,?,?,?,?,?) ";
                    //(eid,sid,dtfrom,dtfromses,dtto,dttoses,designation,officeid,statusid,statusdetail,remarks,deptid );
                    System.out.println(sql);
                    ps = con.prepareStatement(sql);
                    ps.setInt(1, strcode);
                    ps.setInt(2, sid);
                    ps.setDate(3, dtfrom);
                    ps.setString(4, dtfromsession);
                    ps.setDate(5, dtto);
                    ps.setString(6, dttosession);

                    ps.setInt(7, designation);
                    ps.setInt(8, officeid);
                    ps.setString(9, statusid);
                    ps.setString(10, statusdetail);
                    ps.setString(11, remarks);
                    ps.setString(12, deptid);
                    ps.executeUpdate();
                    ps.close();


                    sql =
 "select * from ( " + " select a.SERVICE_LIST_SLNO,a.DATE_FROM,a.DATE_FROM_SESSION,a.DATE_TO,a.DATE_TO_SESSION,a.DESIGNATION_ID,a.OFFICE_ID, " +
   " a.EMPLOYEE_STATUS_ID,a.STATUS_DETAIL,a.REMARKS, " +
   " b.DESIGNATION,c.OFFICE_NAME,d.EMPLOYEE_STATUS_DESC, a.OFFICE_DEPT_ID " +
   " from " + tablename + "  a" +
   " left outer join HRM_MST_DESIGNATIONS b on b.DESIGNATION_ID=a.DESIGNATION_ID  " +
   " left outer join COM_MST_OFFICES c on c.OFFICE_ID=a.OFFICE_ID " +
   " left outer join HRM_MST_EMPLOYEE_STATUS d on   d.EMPLOYEE_STATUS_ID=a.EMPLOYEE_STATUS_ID   " +
   " where a.EMPLOYEE_ID=? and a.OFFICE_DEPT_ID ='TWAD' " + " union all " +
   " select a.SERVICE_LIST_SLNO,a.DATE_FROM,a.DATE_FROM_SESSION, " +
   " a.DATE_TO,a.DATE_TO_SESSION,a.DESIGNATION_ID, " +
   " a.OFFICE_ID, a.EMPLOYEE_STATUS_ID, a.STATUS_DETAIL,a.REMARKS, b.DESIGNATION, c.OTHER_DEPT_OFFICE_NAME as OFFICE_NAME, d.EMPLOYEE_STATUS_DESC, a.OFFICE_DEPT_ID  " +
   " from " + tablename + "  a " +
   " left outer join HRM_MST_DESIGNATIONS b on b.DESIGNATION_ID=a.DESIGNATION_ID " +
   " left outer join HRM_MST_OTHER_DEPT_OFFICES c on c.OTHER_DEPT_OFFICE_ID=a.OFFICE_ID " +
   " left outer join HRM_MST_EMPLOYEE_STATUS d on  d.EMPLOYEE_STATUS_ID=a.EMPLOYEE_STATUS_ID " +
   "where a.EMPLOYEE_ID=?  and a.OFFICE_DEPT_ID <> 'TWAD' " +
   ") order by DATE_FROM";

                    ps = con.prepareStatement(sql);
                    ps.setInt(1, strcode);
                    ps.setInt(2, strcode);
                    rs = ps.executeQuery();
                    int serno = 0;
                    while (rs.next()) {
                        System.out.println(rs.getInt("DESIGNATION_ID") + "->" +
                                           rs.getString("DESIGNATION") + ";" +
                                           rs.getInt("OFFICE_ID") + "->" +
                                           rs.getString("OFFICE_NAME"));
                        xml = xml + "<servicedata>";
                        xml = xml + "<SLNO>" + (++serno) + "</SLNO>";
                        xml =
 xml + "<SERVICE_LIST_SLNO>" + rs.getInt("SERVICE_LIST_SLNO") +
   "</SERVICE_LIST_SLNO>";
                        if (rs.getDate("DATE_FROM") != null) {
                            String[] sd =
                                rs.getDate("DATE_FROM").toString().split("-");
                            String od = sd[2] + "/" + sd[1] + "/" + sd[0];
                            xml = xml + "<DATE_FROM>" + od + "</DATE_FROM>";
                        } else {
                            xml =
 xml + "<DATE_FROM>" + rs.getDate("DATE_FROM") + "</DATE_FROM>";

                        }
                        xml =
 xml + "<DATE_FROM_SESSION>" + rs.getString("DATE_FROM_SESSION") +
   "</DATE_FROM_SESSION>";
                        if (rs.getDate("DATE_TO") != null) {
                            String[] sd =
                                rs.getDate("DATE_TO").toString().split("-");
                            String od = sd[2] + "/" + sd[1] + "/" + sd[0];
                            xml = xml + "<DATE_TO>" + od + "</DATE_TO>";
                        } else {
                            xml =
 xml + "<DATE_TO>" + rs.getDate("DATE_TO") + "</DATE_TO>";
                        }
                        xml =
 xml + "<DATE_TO_SESSION>" + rs.getString("DATE_TO_SESSION") +
   "</DATE_TO_SESSION>";
                        xml =
 xml + " <DESIGNATION_ID>" + rs.getInt("DESIGNATION_ID") + "</DESIGNATION_ID>";
                        xml =
 xml + "<OFFICE_ID>" + rs.getInt("OFFICE_ID") + "</OFFICE_ID>";
                        xml =
 xml + "<EMPLOYEE_STATUS_ID>" + rs.getString("EMPLOYEE_STATUS_ID") +
   "</EMPLOYEE_STATUS_ID>";
                        xml =
 xml + "<STATUS_DETAIL>" + rs.getString("STATUS_DETAIL") + "</STATUS_DETAIL>";
                        xml =
 xml + "<REMARKS>" + rs.getString("REMARKS") + "</REMARKS>";

                        xml =
 xml + "<DESIGNATION>" + rs.getString("DESIGNATION") + "</DESIGNATION>";
                        xml =
 xml + "<OFFICE_NAME>" + rs.getString("OFFICE_NAME") + "</OFFICE_NAME>";
                        xml =
 xml + "<EMPLOYEE_STATUS_DESC>" + rs.getString("EMPLOYEE_STATUS_DESC") +
   "</EMPLOYEE_STATUS_DESC>";
                        xml =
 xml + "<OFFICE_DEPT_ID>" + rs.getString("OFFICE_DEPT_ID") +
   "</OFFICE_DEPT_ID>";

                        xml = xml + "</servicedata>";


                    }
                    rs.close();
                    ps.close();


                    /////////////////////////

                    xml =
 xml + "<flag>success</flag><genid>" + sid + "</genid>";

                }


            }

            catch (Exception e) {

                System.out.println("catch........" + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";


        }

        else if (strCommand.equalsIgnoreCase("Update")) {
            xml = "<response><command>Update</command>";
            boolean flag = true;
            try {


                System.out.println("update is started");
                String dtfromsessionOrg =
                    request.getParameter("optDateFromOrg");
                String dttosessionOrg = request.getParameter("optDateToOrg");
                String[] sd = request.getParameter("txtDateToOrg").split("/");
                c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                java.util.Date d = c.getTime();
                Date dttoOrg = new Date(d.getTime());
                String statusidOrg = request.getParameter("cmbStatusOrg");
                String statusdetailOrg = request.getParameter("txtDetailOrg");
                String remarksOrg = request.getParameter("txtRemarkOrg");

                String updatedby = (String)session.getAttribute("UserId");
                long ln = System.currentTimeMillis();
                Timestamp ts = new Timestamp(ln);

                long diff = dttoOrg.getTime() - dtto.getTime();
                long fullday = (1000L * 60L * 60L * 24L);
                long day = (diff / fullday);
                if (day == 0) {


                    PreparedStatement cs =
                        con.prepareStatement("update " + tablename +
                                             " set DATE_FROM=?,DATE_FROM_SESSION=?,DATE_TO=?," +
                                             "DATE_TO_SESSION=?,EMPLOYEE_STATUS_ID=?, STATUS_DETAIL=?, REMARKS=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=? " +
                                             "   where EMPLOYEE_ID=? and SERVICE_LIST_SLNO=?");

                    cs.setDate(1, dtfrom);
                    cs.setString(2, dtfromsession);
                    cs.setDate(3, dtto);
                    cs.setString(4, dttosession);
                    cs.setString(5, statusid);
                    cs.setString(6, statusdetail);
                    cs.setString(7, remarks);

                    cs.setString(8, updatedby);
                    cs.setTimestamp(9, ts);
                    cs.setInt(10, strcode);
                    cs.setInt(11, slno);
                    cs.execute();

                    System.out.println("test1");

                } else {
                    try {
                        con.setAutoCommit(false);
                        PreparedStatement cs =
                            con.prepareStatement("update " + tablename +
                                                 " set DATE_FROM=?,DATE_FROM_SESSION=?,DATE_TO=?," +
                                                 "DATE_TO_SESSION=?,EMPLOYEE_STATUS_ID=?, STATUS_DETAIL=?, REMARKS=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=? " +
                                                 "   where EMPLOYEE_ID=? and SERVICE_LIST_SLNO=?");

                        cs.setDate(1, dtfrom);
                        cs.setString(2, dtfromsession);
                        cs.setDate(3, dtto);
                        cs.setString(4, dttosession);
                        cs.setString(5, statusid);
                        cs.setString(6, statusdetail);
                        cs.setString(7, remarks);
                        cs.setString(8, updatedby);
                        cs.setTimestamp(9, ts);
                        cs.setInt(10, strcode);
                        cs.setInt(11, slno);
                        cs.execute();
                        System.out.println("test2");
                        String sql =
                            "select max(SERVICE_LIST_SLNO) as maxval from " +
                            tablename + " where EMPLOYEE_ID = ?";
                        ps = con.prepareStatement(sql);
                        ps.setInt(1, strcode);
                        rs = ps.executeQuery();
                        int sid = 0;
                        if (rs.next()) {
                            sid = rs.getInt("maxval");

                            if (sid > 0)
                                sid = sid + 1;
                            else
                                sid = 1;
                        }

                        //generate next date
                        long from = 0;
                        from = dtto.getTime() + fullday;
                        Date fdate = null;
                        String fses = null;
                        if (dttosession.equalsIgnoreCase("FN")) {
                            from = dtto.getTime();
                            fdate = new Date(from);
                            fses = "AN";
                        } else {
                            from = dtto.getTime() + fullday;
                            fdate = new Date(from);
                            fses = "FN";
                        }

                        System.out.println("here is ok");
                        cs =
  con.prepareStatement("insert into " + tablename + "(DATE_FROM,DATE_FROM_SESSION,DATE_TO," +
                       "DATE_TO_SESSION,EMPLOYEE_STATUS_ID, STATUS_DETAIL, REMARKS,EMPLOYEE_ID,SERVICE_LIST_SLNO," +
                       "UPDATED_BY_USER_ID,UPDATED_DATE )" +
                       " values(?,?,?,?,?,?,?,?,?,?,?)");

                        cs.setDate(1, fdate);
                        cs.setString(2, fses);
                        cs.setDate(3, dttoOrg);
                        cs.setString(4, dttosessionOrg);
                        cs.setString(5, statusidOrg);
                        cs.setString(6, statusdetailOrg);
                        cs.setString(7, remarksOrg);
                        cs.setInt(8, strcode);
                        cs.setInt(9, sid);

                        cs.setString(10, updatedby);
                        cs.setTimestamp(11, ts);
                        cs.execute();
                        System.out.println("test3");
                        con.commit();
                    } catch (Exception e) {
                        System.out.println("Error in transaction " + e);
                        xml = xml + "<flag>failure</flag>";
                        try {
                            con.rollback();
                        } catch (Exception e1) {
                            System.out.println(e1);
                        }
                    } finally {
                        try {
                            con.setAutoCommit(true);
                        } catch (Exception e1) {
                            System.out.println(e1);
                        }
                    }

                }


                String sql =
                    "select * from ( " + " select a.SERVICE_LIST_SLNO,a.DATE_FROM,a.DATE_FROM_SESSION,a.DATE_TO,a.DATE_TO_SESSION, " +
                    " a.EMPLOYEE_STATUS_ID,a.STATUS_DETAIL,a.REMARKS, " +
                    " d.EMPLOYEE_STATUS_DESC " + " from " + tablename + "  a" +
                    " left outer join HRM_MST_EMPLOYEE_STATUS d on   d.EMPLOYEE_STATUS_ID=a.EMPLOYEE_STATUS_ID   " +
                    " where a.EMPLOYEE_ID=? and (a.EMPLOYEE_STATUS_ID = 'DPT' or a.EMPLOYEE_STATUS_ID ='TRT' or a.EMPLOYEE_STATUS_ID ='LLV'  or a.EMPLOYEE_STATUS_ID ='TCL') and ( a.PROCESS_FLOW_STATUS_ID !='FR' or  a.PROCESS_FLOW_STATUS_ID is null  )  " +
                    ") order by DATE_FROM";

                System.out.println(sql);
                ps = con.prepareStatement(sql);
                ps.setInt(1, strcode);
                rs = ps.executeQuery();
                int serno = 0;
                while (rs.next()) {

                    xml = xml + "<servicedata>";
                    xml = xml + "<SLNO>" + (++serno) + "</SLNO>";
                    xml =
 xml + "<SERVICE_LIST_SLNO>" + rs.getInt("SERVICE_LIST_SLNO") +
   "</SERVICE_LIST_SLNO>";
                    if (rs.getDate("DATE_FROM") != null) {
                        sd = rs.getDate("DATE_FROM").toString().split("-");
                        String od = sd[2] + "/" + sd[1] + "/" + sd[0];
                        xml = xml + "<DATE_FROM>" + od + "</DATE_FROM>";
                    } else {
                        xml =
 xml + "<DATE_FROM>" + rs.getDate("DATE_FROM") + "</DATE_FROM>";

                    }
                    xml =
 xml + "<DATE_FROM_SESSION>" + rs.getString("DATE_FROM_SESSION") +
   "</DATE_FROM_SESSION>";
                    if (rs.getDate("DATE_TO") != null) {
                        sd = rs.getDate("DATE_TO").toString().split("-");
                        String od = sd[2] + "/" + sd[1] + "/" + sd[0];
                        xml = xml + "<DATE_TO>" + od + "</DATE_TO>";
                    } else {
                        xml =
 xml + "<DATE_TO>" + rs.getDate("DATE_TO") + "</DATE_TO>";
                    }
                    xml =
 xml + "<DATE_TO_SESSION>" + rs.getString("DATE_TO_SESSION") +
   "</DATE_TO_SESSION>";
                    xml =
 xml + "<EMPLOYEE_STATUS_ID>" + rs.getString("EMPLOYEE_STATUS_ID") +
   "</EMPLOYEE_STATUS_ID>";
                    xml =
 xml + "<STATUS_DETAIL>" + rs.getString("STATUS_DETAIL") + "</STATUS_DETAIL>";
                    xml =
 xml + "<REMARKS>" + rs.getString("REMARKS") + "</REMARKS>";
                    xml =
 xml + "<EMPLOYEE_STATUS_DESC>" + rs.getString("EMPLOYEE_STATUS_DESC") +
   "</EMPLOYEE_STATUS_DESC>";

                    xml = xml + "</servicedata>";
                }
                xml = xml + "<flag>success</flag>";

            }

            catch (Exception e) {

                System.out.println("catch........" + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";


        }


        else if (strCommand.equalsIgnoreCase("Delete")) {
            xml = "<response><command>Delete</command>";
            try {


                PreparedStatement cs =
                    con.prepareCall(" delete from " + tablename +
                                    " where EMPLOYEE_ID=? and SERVICE_LIST_SLNO=?");
                cs.setInt(1, strcode);
                cs.setInt(2, slno);
                cs.execute();
                // xml=xml+"<flag>success</flag>";
                cs.close();


                // strcode=Integer.parseInt(request.getParameter("txtEmployeeid"));
                // System.out.println("assign..... Emp id Code::"+strcode);

                String sql =
                    "select * from ( " + " select a.SERVICE_LIST_SLNO,a.DATE_FROM,a.DATE_FROM_SESSION,a.DATE_TO,a.DATE_TO_SESSION,a.DESIGNATION_ID,a.OFFICE_ID, " +
                    " a.EMPLOYEE_STATUS_ID,a.STATUS_DETAIL,a.REMARKS, " +
                    " b.DESIGNATION,c.OFFICE_NAME,d.EMPLOYEE_STATUS_DESC, a.OFFICE_DEPT_ID " +
                    " from " + tablename + "  a" +
                    " left outer join HRM_MST_DESIGNATIONS b on b.DESIGNATION_ID=a.DESIGNATION_ID  " +
                    " left outer join COM_MST_OFFICES c on c.OFFICE_ID=a.OFFICE_ID " +
                    " left outer join HRM_MST_EMPLOYEE_STATUS d on   d.EMPLOYEE_STATUS_ID=a.EMPLOYEE_STATUS_ID   " +
                    " where a.EMPLOYEE_ID=? and a.OFFICE_DEPT_ID ='TWAD' " +
                    " union all " +
                    " select a.SERVICE_LIST_SLNO,a.DATE_FROM,a.DATE_FROM_SESSION, " +
                    " a.DATE_TO,a.DATE_TO_SESSION,a.DESIGNATION_ID, " +
                    " a.OFFICE_ID, a.EMPLOYEE_STATUS_ID, a.STATUS_DETAIL,a.REMARKS, b.DESIGNATION, c.OTHER_DEPT_OFFICE_NAME as OFFICE_NAME, d.EMPLOYEE_STATUS_DESC, a.OFFICE_DEPT_ID  " +
                    " from " + tablename + "  a " +
                    " left outer join HRM_MST_DESIGNATIONS b on b.DESIGNATION_ID=a.DESIGNATION_ID " +
                    " left outer join HRM_MST_OTHER_DEPT_OFFICES c on c.OTHER_DEPT_OFFICE_ID=a.OFFICE_ID " +
                    " left outer join HRM_MST_EMPLOYEE_STATUS d on  d.EMPLOYEE_STATUS_ID=a.EMPLOYEE_STATUS_ID " +
                    "where a.EMPLOYEE_ID=?  and a.OFFICE_DEPT_ID <> 'TWAD' " +
                    ") order by DATE_FROM";

                ps = con.prepareStatement(sql);
                ps.setInt(1, strcode);
                ps.setInt(2, strcode);
                rs = ps.executeQuery();
                int serno = 0;
                while (rs.next()) {
                    System.out.println(rs.getInt("DESIGNATION_ID") + "->" +
                                       rs.getString("DESIGNATION") + ";" +
                                       rs.getInt("OFFICE_ID") + "->" +
                                       rs.getString("OFFICE_NAME"));
                    xml = xml + "<servicedata>";
                    xml = xml + "<SLNO>" + (++serno) + "</SLNO>";
                    xml =
 xml + "<SERVICE_LIST_SLNO>" + rs.getInt("SERVICE_LIST_SLNO") +
   "</SERVICE_LIST_SLNO>";
                    if (rs.getDate("DATE_FROM") != null) {
                        String[] sd =
                            rs.getDate("DATE_FROM").toString().split("-");
                        String od = sd[2] + "/" + sd[1] + "/" + sd[0];
                        xml = xml + "<DATE_FROM>" + od + "</DATE_FROM>";
                    } else {
                        xml =
 xml + "<DATE_FROM>" + rs.getDate("DATE_FROM") + "</DATE_FROM>";

                    }
                    xml =
 xml + "<DATE_FROM_SESSION>" + rs.getString("DATE_FROM_SESSION") +
   "</DATE_FROM_SESSION>";
                    if (rs.getDate("DATE_TO") != null) {
                        String[] sd =
                            rs.getDate("DATE_TO").toString().split("-");
                        String od = sd[2] + "/" + sd[1] + "/" + sd[0];
                        xml = xml + "<DATE_TO>" + od + "</DATE_TO>";
                    } else {
                        xml =
 xml + "<DATE_TO>" + rs.getDate("DATE_TO") + "</DATE_TO>";
                    }
                    xml =
 xml + "<DATE_TO_SESSION>" + rs.getString("DATE_TO_SESSION") +
   "</DATE_TO_SESSION>";
                    xml =
 xml + " <DESIGNATION_ID>" + rs.getInt("DESIGNATION_ID") + "</DESIGNATION_ID>";
                    xml =
 xml + "<OFFICE_ID>" + rs.getInt("OFFICE_ID") + "</OFFICE_ID>";
                    xml =
 xml + "<EMPLOYEE_STATUS_ID>" + rs.getString("EMPLOYEE_STATUS_ID") +
   "</EMPLOYEE_STATUS_ID>";
                    xml =
 xml + "<STATUS_DETAIL>" + rs.getString("STATUS_DETAIL") + "</STATUS_DETAIL>";
                    xml =
 xml + "<REMARKS>" + rs.getString("REMARKS") + "</REMARKS>";

                    xml =
 xml + "<DESIGNATION>" + rs.getString("DESIGNATION") + "</DESIGNATION>";
                    xml =
 xml + "<OFFICE_NAME>" + rs.getString("OFFICE_NAME") + "</OFFICE_NAME>";
                    xml =
 xml + "<EMPLOYEE_STATUS_DESC>" + rs.getString("EMPLOYEE_STATUS_DESC") +
   "</EMPLOYEE_STATUS_DESC>";
                    xml =
 xml + "<OFFICE_DEPT_ID>" + rs.getString("OFFICE_DEPT_ID") +
   "</OFFICE_DEPT_ID>";

                    xml = xml + "</servicedata>";


                }
                rs.close();
                ps.close();

                xml = xml + "<flag>success</flag>";

            }


            catch (Exception e) {

                System.out.println("catch........" + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";


        } else if (strCommand.equalsIgnoreCase("LastDate")) {
            xml = "<response><command>LastDate</command>";
            try {
                strcode =
                        Integer.parseInt(request.getParameter("txtEmployeeid"));
                System.out.println("assign..... Emp id Code::" + strcode);

                PreparedStatement cs =
                    con.prepareCall("select DATE_TO,DATE_TO_SESSION from " +
                                    tablename +
                                    " where EMPLOYEE_ID=? and DATE_TO=(select max(DATE_TO) from  " +
                                    tablename + " where EMPLOYEE_ID=? )");
                cs.setInt(1, strcode);
                cs.setInt(2, strcode);

                rs = cs.executeQuery();
                if (rs.next()) {
                    Date dt = rs.getDate("DATE_TO");
                    String ses = rs.getString("DATE_TO_SESSION");
                    String strdt = dt.toString();
                    System.out.println("Date is :" + strdt);
                    String[] sp = strdt.split("-");
                    System.out.println(sp[1]);
                    System.out.println("To Session :" + ses);
                    String nextses = "";
                    if (ses.equalsIgnoreCase("AN")) {
                        c =
   new GregorianCalendar(Integer.parseInt(sp[0]), Integer.parseInt(sp[1]) - 1,
                         Integer.parseInt(sp[2]) + 1);
                        nextses = "FN";
                    } else {
                        c =
   new GregorianCalendar(Integer.parseInt(sp[0]), Integer.parseInt(sp[1]) - 1,
                         Integer.parseInt(sp[2]));
                        nextses = "AN";
                    }
                    //java.util.Date d=c.getTime();
                    System.out.println("after conversion:" +
                                       new SimpleDateFormat("dd/MM/yyyy").format(c.getTime()));
                    String nextdate =
                        new SimpleDateFormat("dd/MM/yyyy").format(c.getTime());
                    xml =
 xml + "<nextdate>" + nextdate + "</nextdate><nextsession>" + nextses +
   "</nextsession>";
                    xml = xml + "<flag>success</flag>";
                } else {
                    xml = xml + "<flag>failure</flag>";
                }
                rs.close();
                cs.close();

            }


            catch (Exception e) {

                System.out.println("catch........" + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";


        }

        else if (strCommand.equalsIgnoreCase("Interval")) {
            xml = "<response><command>Interval</command>";
            //String strinterval="";
            boolean flag = false;
            try {
                strcode =
                        Integer.parseInt(request.getParameter("txtEmployeeid"));
                System.out.println("assign..... Emp id Code::" + strcode);
                System.out.println("Temporary Table Name2:" + tablename);
                PreparedStatement cs =
                    con.prepareCall("select SERVICE_LIST_SLNO,DATE_FROM,DATE_FROM_SESSION,DATE_TO,DATE_TO_SESSION," +
                                    "DESIGNATION_ID,OFFICE_ID,EMPLOYEE_STATUS_ID  from " +
                                    tablename + " where   EMPLOYEE_ID=? ");
                cs.setInt(1, strcode);
                rs = cs.executeQuery();
                String valstr = null;
                // valstr="<table cellspacing=\"2\" cellpadding=\"3\" border=\"1\" width=\"80%\" align=\"center\" class=\"tdH\">";
                valstr =
                        "<tr><td colspan='3' class='tdH'  align='center'>SR Validation Result for Employee Id - " +
                        strcode + "</td></tr>";
                String missing = "";
                while (rs.next()) {
                    String rec = "";
                    int i = 0;
                    if (rs.getDate("DATE_FROM") == null) {
                        rec +=
"<tr><td>&nbsp;</td><td align='right'>" + (++i) +
 ")</td><td>From Date Empty</td></tr>";
                    }
                    if (rs.getString("DATE_FROM_SESSION") == null) {
                        rec +=
"<tr><td>&nbsp;</td><td  align='right'>" + (++i) +
 ")</td><td>From Date Session Empty</td></tr>";
                    }
                    if (rs.getDate("DATE_TO") == null) {
                        rec +=
"<tr><td>&nbsp;</td><td  align='right'>" + (++i) +
 ")</td><td>To Date Empty</td></tr>";
                    }
                    if (rs.getString("DATE_TO_SESSION") == null) {
                        rec +=
"<tr><td>&nbsp;</td><td  align='right'>" + (++i) +
 ")</td><td>To Date Session Empty</td></tr>";
                    }
                    if (!(rs.getString("EMPLOYEE_STATUS_ID") != null &&
                          (rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("TRT") ||
                           rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("DPT")))) {
                        if (rs.getInt("DESIGNATION_ID") == 0) {
                            rec +=
"<tr><td>&nbsp;</td><td  align='right'>" + (++i) +
 ")</td><td>Designation Entry Empty</td></tr>";
                        }
                        if (rs.getInt("OFFICE_ID") == 0) {
                            rec +=
"<tr><td>&nbsp;</td><td  align='right'>" + (++i) +
 ")</td><td>Office Entry Empty</td></tr>";
                        }
                    }
                    if (rs.getString("EMPLOYEE_STATUS_ID") == null) {
                        rec +=
"<tr><td>&nbsp;</td><td  align='right'>" + (++i) +
 ")</td><td>Employee Status Empty</td></tr>";
                    }

                    if (!rec.equals("")) {

                        missing +=
                                "<tr><td colspan=3 class='table'><b>Service Record " +
                                rs.getInt("SERVICE_LIST_SLNO") +
                                "</b></td></tr>";
                        missing += rec;
                    }


                }

                if (!missing.equals("")) {
                    flag = true;
                    valstr +=
                            "<tr><td colspan=3 class='tdH'  align='center'>SR Missing Entries</td></tr>";
                    valstr += missing;
                }

                {

                    cs =
  con.prepareCall("select SERVICE_LIST_SLNO,DATE_FROM,DATE_FROM_SESSION, DATE_TO,DATE_TO_SESSION from " +
                  tablename +
                  " where EMPLOYEE_ID=? and DATE_TO is not null order by DATE_FROM");
                    cs.setInt(1, strcode);
                    rs = cs.executeQuery();
                    int count = 0;
                    java.util.Date fdate = null;
                    java.util.Date tdate = null;
                    missing = "";
                    int srl1 = 0, srl2 = 0;
                    if (rs.next()) {
                        fdate = rs.getDate("DATE_FROM");
                        // String fses=rs.getString("DATE_FROM_SESSION");
                        tdate = rs.getDate("DATE_TO");
                        srl1 = rs.getInt("SERVICE_LIST_SLNO");
                        //String tses=rs.getString("DATE_TO_SESSION");

                    }
                    while (rs.next()) {
                        java.util.Date cfdate = rs.getDate("DATE_FROM");
                        // String fses=rs.getString("DATE_FROM_SESSION");
                        java.util.Date ctdate = rs.getDate("DATE_TO");
                        srl2 = rs.getInt("SERVICE_LIST_SLNO");
                        System.out.println("srl2:" + srl2);
                        //  String tses=rs.getString("DATE_TO_SESSION");

                        //System.out.println("Date : From :"+fdate+"    To:"+tdate);

                        long diff = cfdate.getTime() - tdate.getTime();
                        //System.out.println("Day Difference:"+(diff / (1000L*60L*60L*24L)) + " days.");
                        long fullday = (1000L * 60L * 60L * 24L);
                        long day = (diff / fullday);
                        if (day > 1) {
                            long from = tdate.getTime() + fullday;
                            String strfrom =
                                new SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date(from));
                            long to = cfdate.getTime() - fullday;
                            String strto =
                                new SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date(to));
                            System.out.println("Interval :" + strfrom + "   " +
                                               strto);
                            missing +=
                                    "<tr><td>" + srl1 + " - " + srl2 + "</td><td>" +
                                    strfrom + "</td><td>" + strto +
                                    "</td></tr>";
                            //xml=xml+"<interval>"+strfrom+"   "+strto+"</interval>";
                            //strinterval=strinterval+strfrom+" "+strto+" ";

                            count++;
                        }

                        fdate = cfdate;
                        tdate = ctdate;
                        srl1 = rs.getInt("SERVICE_LIST_SLNO");
                        System.out.println("srl1:" + srl1);

                    }
                    if (count > 0) {
                        // xml=xml+"<flag>success</flag>";
                        //System.out.println("Interval:|"+strinterval+"|");
                        //xml=xml+"<interval>"+strinterval+"</interval>";
                        flag = true;
                        valstr +=
                                "<tr><td colspan=\"3\" class='tdH' align='center'>SR Interval</td></tr>";
                        valstr +=
                                "<tr><td class='tdH'>S.No Between</td><td class='tdH'>From</td><td class='tdH'>To</td></tr>";
                        valstr += missing;


                    }
                    if (flag == true) {
                        xml = xml + "<flag>success</flag>";
                        //valstr+="</table>";
                        session.setAttribute("interval", valstr);
                    } else {
                        xml = xml + "<flag>failure</flag>";
                        valstr +=
                                "<tr><td colspan=\"3\" class='table'>There is no Interval/Empty field</td></tr>";
                        valstr += "</table>";
                    }
                    rs.close();
                    cs.close();
                }
            }


            catch (Exception e) {
                System.out.println("catch........" + e);
                xml = xml + "<flag>failure1</flag>";
            }
            xml = xml + "</response>";


        }

        else if (strCommand.equalsIgnoreCase("reload")) {
            xml = "<response><command>reload</command>";
            try {
                strcode =
                        Integer.parseInt(request.getParameter("txtEmployeeid"));
                System.out.println("assign..... Emp id Code::" + strcode);

                String sql =
                    "select * from ( " + " select a.SERVICE_LIST_SLNO,a.DATE_FROM,a.DATE_FROM_SESSION,a.DATE_TO,a.DATE_TO_SESSION,a.DESIGNATION_ID,a.OFFICE_ID, " +
                    " a.EMPLOYEE_STATUS_ID,a.STATUS_DETAIL,a.REMARKS, " +
                    " b.DESIGNATION,c.OFFICE_NAME,d.EMPLOYEE_STATUS_DESC, a.OFFICE_DEPT_ID " +
                    " from " + tablename + "  a" +
                    " left outer join HRM_MST_DESIGNATIONS b on b.DESIGNATION_ID=a.DESIGNATION_ID  " +
                    " left outer join COM_MST_OFFICES c on c.OFFICE_ID=a.OFFICE_ID " +
                    " left outer join HRM_MST_EMPLOYEE_STATUS d on   d.EMPLOYEE_STATUS_ID=a.EMPLOYEE_STATUS_ID   " +
                    " where a.EMPLOYEE_ID=? and a.OFFICE_DEPT_ID ='TWAD' " +
                    " union all " +
                    " select a.SERVICE_LIST_SLNO,a.DATE_FROM,a.DATE_FROM_SESSION, " +
                    " a.DATE_TO,a.DATE_TO_SESSION,a.DESIGNATION_ID, " +
                    " a.OFFICE_ID, a.EMPLOYEE_STATUS_ID, a.STATUS_DETAIL,a.REMARKS, b.DESIGNATION, c.OTHER_DEPT_OFFICE_NAME as OFFICE_NAME, d.EMPLOYEE_STATUS_DESC, a.OFFICE_DEPT_ID  " +
                    " from " + tablename + "  a " +
                    " left outer join HRM_MST_DESIGNATIONS b on b.DESIGNATION_ID=a.DESIGNATION_ID " +
                    " left outer join HRM_MST_OTHER_DEPT_OFFICES c on c.OTHER_DEPT_OFFICE_ID=a.OFFICE_ID " +
                    " left outer join HRM_MST_EMPLOYEE_STATUS d on  d.EMPLOYEE_STATUS_ID=a.EMPLOYEE_STATUS_ID " +
                    "where a.EMPLOYEE_ID=?  and a.OFFICE_DEPT_ID <> 'TWAD' " +
                    ") order by DATE_FROM";

                ps = con.prepareStatement(sql);
                ps.setInt(1, strcode);
                ps.setInt(2, strcode);
                rs = ps.executeQuery();
                int serno = 0;
                while (rs.next()) {
                    System.out.println(rs.getInt("DESIGNATION_ID") + "->" +
                                       rs.getString("DESIGNATION") + ";" +
                                       rs.getInt("OFFICE_ID") + "->" +
                                       rs.getString("OFFICE_NAME"));
                    xml = xml + "<servicedata>";
                    xml = xml + "<SLNO>" + (++serno) + "</SLNO>";
                    xml =
 xml + "<SERVICE_LIST_SLNO>" + rs.getInt("SERVICE_LIST_SLNO") +
   "</SERVICE_LIST_SLNO>";
                    if (rs.getDate("DATE_FROM") != null) {
                        String[] sd =
                            rs.getDate("DATE_FROM").toString().split("-");
                        String od = sd[2] + "/" + sd[1] + "/" + sd[0];
                        xml = xml + "<DATE_FROM>" + od + "</DATE_FROM>";
                    } else {
                        xml =
 xml + "<DATE_FROM>" + rs.getDate("DATE_FROM") + "</DATE_FROM>";

                    }
                    xml =
 xml + "<DATE_FROM_SESSION>" + rs.getString("DATE_FROM_SESSION") +
   "</DATE_FROM_SESSION>";
                    if (rs.getDate("DATE_TO") != null) {
                        String[] sd =
                            rs.getDate("DATE_TO").toString().split("-");
                        String od = sd[2] + "/" + sd[1] + "/" + sd[0];
                        xml = xml + "<DATE_TO>" + od + "</DATE_TO>";
                    } else {
                        xml =
 xml + "<DATE_TO>" + rs.getDate("DATE_TO") + "</DATE_TO>";
                    }
                    xml =
 xml + "<DATE_TO_SESSION>" + rs.getString("DATE_TO_SESSION") +
   "</DATE_TO_SESSION>";
                    xml =
 xml + "<DESIGNATION_ID>" + rs.getInt("DESIGNATION_ID") + "</DESIGNATION_ID>";
                    xml =
 xml + "<OFFICE_ID>" + rs.getInt("OFFICE_ID") + "</OFFICE_ID>";
                    xml =
 xml + "<EMPLOYEE_STATUS_ID>" + rs.getString("EMPLOYEE_STATUS_ID") +
   "</EMPLOYEE_STATUS_ID>";
                    xml =
 xml + "<STATUS_DETAIL>" + rs.getString("STATUS_DETAIL") + "</STATUS_DETAIL>";
                    xml =
 xml + "<REMARKS>" + rs.getString("REMARKS") + "</REMARKS>";

                    xml =
 xml + "<DESIGNATION>" + rs.getString("DESIGNATION") + "</DESIGNATION>";
                    xml =
 xml + "<OFFICE_NAME>" + rs.getString("OFFICE_NAME") + "</OFFICE_NAME>";
                    xml =
 xml + "<EMPLOYEE_STATUS_DESC>" + rs.getString("EMPLOYEE_STATUS_DESC") +
   "</EMPLOYEE_STATUS_DESC>";
                    xml =
 xml + "<OFFICE_DEPT_ID>" + rs.getString("OFFICE_DEPT_ID") +
   "</OFFICE_DEPT_ID>";

                    xml = xml + "</servicedata>";


                }
                rs.close();
                ps.close();

                xml = xml + "<flag>success</flag>";

            }


            catch (Exception e) {
                System.out.println("reload catch........" + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";


        }

        else if (strCommand.equalsIgnoreCase("Submit")) {
            xml = "<response><command>Submit</command>";

            try {
                strcode =
                        Integer.parseInt(request.getParameter("txtEmployeeid"));
                System.out.println("assign..... Emp id Code::" + strcode);

                con.setAutoCommit(false);
                PreparedStatement cs =
                    con.prepareStatement("delete from HRM_EMP_SERVICE_DATA  where EMPLOYEE_ID=?");
                cs.setInt(1, strcode);
                cs.execute();
                cs.close();

                System.out.println(tablename);
                //cs=con.prepareStatement("select * from "+tablename+" order by DATE_FROM");
                String sql =
                    "select * from " + tablename + " order by DATE_FROM";
                st = con.createStatement();
                //cs.setString(1,tablename);
                System.out.println(sql);
                rs = st.executeQuery(sql);
                System.out.println("temp table selected");
                int seq = 0;

                while (rs.next()) {
                    PreparedStatement ps1 =
                        con.prepareStatement("insert into  HRM_EMP_SERVICE_DATA(EMPLOYEE_ID, SERVICE_LIST_SLNO, DATE_FROM, DATE_FROM_SESSION," +
                                             "DATE_TO, DATE_TO_SESSION, DESIGNATION_ID, OFFICE_ID, EMPLOYEE_STATUS_ID, STATUS_DETAIL, REMARKS, OFFICE_DEPT_ID,PROCESS_FLOW_STATUS_ID,UPDATED_BY_USER_ID,UPDATED_DATE ) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                    ps1.setInt(1, rs.getInt("EMPLOYEE_ID"));
                    seq = seq + 1;
                    ps1.setInt(2, seq);
                    ps1.setDate(3, rs.getDate("DATE_FROM"));
                    ps1.setString(4, rs.getString("DATE_FROM_SESSION"));
                    ps1.setDate(5, rs.getDate("DATE_TO"));
                    ps1.setString(6, rs.getString("DATE_TO_SESSION"));
                    ps1.setInt(7, rs.getInt("DESIGNATION_ID"));
                    ps1.setInt(8, rs.getInt("OFFICE_ID"));
                    ps1.setString(9, rs.getString("EMPLOYEE_STATUS_ID"));
                    ps1.setString(10,
                                  rs.getString("STATUS_DETAIL") == null ? "" :
                                  rs.getString("STATUS_DETAIL"));
                    ps1.setString(11,
                                  rs.getString("REMARKS") == null ? "" : rs.getString("REMARKS"));
                    ps1.setString(12, rs.getString("OFFICE_DEPT_ID"));
                    ps1.setString(13, rs.getString("PROCESS_FLOW_STATUS_ID"));

                    ps1.setString(14, rs.getString("UPDATED_BY_USER_ID"));
                    ps1.setDate(15, rs.getDate("UPDATED_DATE"));
                    System.out.println("service no:" +
                                       rs.getInt("SERVICE_LIST_SLNO"));
                    System.out.println("emp id::" + rs.getInt("EMPLOYEE_ID"));
                    System.out.println("from date::" +
                                       rs.getDate("DATE_FROM"));
                    ps1.executeUpdate();
                    ps1.close();


                }
                System.out.println("seq:" + seq);

                rs.close();
                st.close();

                cs = con.prepareStatement("drop table " + tablename);
                //cs.setString(1,tablename);
                cs.execute();
                cs.close();

                con.commit();
                xml = xml + "<flag>success</flag>";


            }


            catch (Exception e) {
                System.out.println("catch........" + e);

                try {
                    con.rollback();
                } catch (Exception e1) {
                    System.out.println(e1);
                }
                xml = xml + "<flag>failure</flag>";

            } finally {

                try {
                    con.setAutoCommit(true);
                } catch (Exception e1) {
                    System.out.println(e1);
                }
            }
            xml = xml + "</response>";


        }


        out.println(xml);
        System.out.println(xml);

    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {

        doGet(request, response);
    }
}


