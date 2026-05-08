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
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class TestsPassed extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        Connection con = null;
        try {
            System.out.println("emp Test details");

            ResourceBundle rsb =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";
            System.out.println("dddddddddd");
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

        String tablename = "";

        HttpSession session = request.getSession(false);
        /*  try
        {
                if(session==null)
                {
                        String xml="<response><command>sessionout</command><flag>sessionout</flag></response>";
                        out.println(xml);
                        System.out.println(xml);
                        out.close();
                        return;

                    }
                    System.out.println(session);

        }catch(Exception e)
        {
                System.out.println("Redirect Error :"+e);
        }
        */
        //HttpSession session=request.getSession(false);
        // UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");

        //  System.out.println("user id::"+empProfile.getEmployeeId());
        //int empid=empProfile.getEmployeeId();
        // System.out.println("session identifier:"+session.hashCode());
        int l = session.getId().length();
        l = l - 10;
        int addflag = 0, pincode;

        tablename = "TEMPSR_" + session.getId().substring(l);
        int strcode = 0, slno = 0, perflag = 0;

        String xml = "", sql = "";

        String strCommand = "", TestCode = "";
        Calendar c;
        int serial = 0, empid = 0;
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");
        String DateExam, EffectDate;


        strCommand = request.getParameter("Command").trim();
        System.out.println("assign....." + strCommand);
        strcode = Integer.parseInt(request.getParameter("txtEmployeeid"));

        /* if(strCommand.equalsIgnoreCase("loademp") || strCommand.equalsIgnoreCase("loadempview") || strCommand.equalsIgnoreCase("test") || strCommand.equalsIgnoreCase("LastDate"))
           {
               strcode=Integer.parseInt(request.getParameter("txtEmployeeid"));
               //System.out.println("assign..... Code::"+strcode);
           }
           else if(strCommand.equalsIgnoreCase("Add") || strCommand.equalsIgnoreCase("Update") )
           {

               if(strCommand.equalsIgnoreCase("Update")) {
                   slno=Integer.parseInt(request.getParameter("txtSNo"));
                   System.out.println("txtSNo"+slno);
               }

               strcode=Integer.parseInt(request.getParameter("txtEmployeeid"));


           }*/


        if (strCommand.equalsIgnoreCase("loademp")) {
            System.out.println("load emp 1");
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
                    sql =
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
                                // xml=xml+"<flag>failure1</flag>";
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
                        // xml=xml+"<flag>failure3</flag>";
                        // flag=false;
                        if (session.getAttribute("Admin") == null ||
                            !((String)session.getAttribute("Admin")).equalsIgnoreCase("YES")) {
                            xml = xml + "<flag>failure3</flag>";
                            flag = false;
                        }
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


        }


        else if (strCommand.equalsIgnoreCase("Add")) {
            xml = "<response><command>Add</command>";
            boolean flag = true;

            System.out.println("ADD");
            DateExam = request.getParameter("DateExam");
            System.out.println(DateExam);
            EffectDate = request.getParameter("EffectDate");
            System.out.println(EffectDate);
            TestCode = request.getParameter("TestCode");
            System.out.println(TestCode);
            try {
                if (perflag != 1) {
                    System.out.println("flag is true");
                    sql =
 "select max(LIST_SL_NO) as maxval from HRM_EMP_Tests_passed where EMPLOYEE_ID=?";
                    ps = con.prepareStatement(sql);
                    ps.setInt(1, strcode);
                    rs = ps.executeQuery();
                    int sid = 0;
                    System.out.println("asdddsds");
                    if (rs.next()) {
                        sid = rs.getInt("maxval");

                        if (sid > 0)
                            sid = sid + 1;
                        else
                            sid = 1;
                    }
                    System.out.println("List sl no" + sid);
                    System.out.println(sid + "ssssssssssssss");
                    System.out.println("Employee id" + strcode);
                    String updatedby = (String)session.getAttribute("UserId");
                    long lng = System.currentTimeMillis();
                    Timestamp ts = new Timestamp(lng);
                    System.out.println("query add");
                    sql =
 " insert into HRM_EMP_Tests_Passed(EMPLOYEE_ID,LIST_SL_NO,Test_Code,Date_of_exam,Effect_from_date,UPDATED_BY_USER_ID,UPDATED_DATE) " +
   "   values (?,?,?,to_date(?,'dd/mm/yyyy'),to_date(?,'dd/mm/yyyy'),?,?) ";
                    //(eid,sid,dtfrom,dtfromses,dtto,dttoses,designation,officeid,statusid,statusdetail,remarks,deptid );
                    System.out.println(sql);
                    ps = con.prepareStatement(sql);
                    ps.setInt(1, strcode);
                    ps.setInt(2, sid);
                    ps.setString(3, TestCode);
                    ps.setString(4, DateExam);
                    ps.setString(5, EffectDate);
                    ps.setString(6, updatedby);
                    ps.setTimestamp(7, ts);
                    ps.executeUpdate();
                    ps.close();
                    System.out.println("added successfully");
                    /* java.util.Date date = new java.util.Date();
                    int day = date.getDate()+1;
                    int month = date.getMonth()+1;
                    int year = date.getYear()+1900;
                     String dateeffrom=(day+'/'+month+'/'+year);
                     System.out.println(day+"fffffffffffffffffffffffffffffffffff");*/
                    xml =
 xml + "<flag>success</flag><servicedata><genid>" + sid +
   "</genid></servicedata>";
                }
            }

            catch (Exception e) {

                System.out.println("catch........" + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";


        }

        else if (strCommand.equalsIgnoreCase("Update")) {
            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaa");
            xml = "<response><command>Update</command>";
            boolean flag = true;
            DateExam = request.getParameter("DateExam");
            System.out.println(DateExam);
            EffectDate = request.getParameter("EffectDate");
            System.out.println(EffectDate);
            TestCode = request.getParameter("TestCode");
            System.out.println(TestCode);
            int sid = Integer.parseInt(request.getParameter("Slno"));
            System.out.println("---------------------------------");
            String updatedby = (String)session.getAttribute("UserId");
            long lng = System.currentTimeMillis();
            Timestamp ts = new Timestamp(lng);


            /////////////////////////////

            try {

                sql =
 " update HRM_EMP_Tests_Passed set Test_Code =?,Date_of_exam=to_date(?,'dd/mm/yyyy'),Effect_from_Date=to_date(?,'dd/mm/yyyy'),UPDATED_BY_USER_ID=?,UPDATED_DATE=? where EMPLOYEE_ID=? and  LIST_SL_NO=? ";

                //(eid,sid,dtfrom,dtfromses,dtto,dttoses,designation,officeid,statusid,statusdetail,remarks,deptid );
                System.out.println(sql);
                ps = con.prepareStatement(sql);
                ps.setInt(6, strcode);
                ps.setInt(7, sid);
                ps.setString(1, TestCode);
                ps.setString(2, DateExam);
                ps.setString(3, EffectDate);
                ps.setString(4, updatedby);
                ps.setTimestamp(5, ts);

                ps.executeUpdate();
                ps.close();
                System.out.println("Updated successfully");
                xml = xml + "<flag>success</flag>";
                con.commit();

            }

            catch (Exception e) {

                System.out.println("catch........" + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";


        }


        else if (strCommand.equalsIgnoreCase("Delete")) {
            System.out.println("inside delete");
            xml = "<response><command>Delete</command>";

            //fund_id=request.getParameter("fund_id");
            slno = Integer.parseInt(request.getParameter("Slno"));
            System.out.println("slno:" + slno);
            try {

                con.setAutoCommit(false);
                sql =
 " delete from HRM_EMP_Tests_Passed where EMPLOYEE_ID=? and LIST_SL_NO=?";
                ps = con.prepareStatement(sql);
                ps.setInt(1, strcode);
                ps.setInt(2, slno);
                int x = ps.executeUpdate();
                if (x > 0)
                    con.commit();
                else
                    System.out.println("Record not update");
                // xml=xml+"<flag>success</flag>";
                ps.close();
                sql =
 "select list_sl_no,employee_id from hrm_emp_Tests_passed where list_sl_no>? and EMPLOYEE_ID=?  ";
                ps = con.prepareStatement(sql);
                ps.setInt(1, slno);
                ps.setInt(2, strcode);
                rs = ps.executeQuery();
                con.commit();
                int list = 0;
                while (rs.next()) {
                    serial = rs.getInt("list_sl_no");
                    list = serial;
                    System.out.println("serial" + serial);
                    serial = serial - 1;
                    System.out.println("serial1" + serial);
                    empid = rs.getInt("employee_id");
                    sql =
 "update hrm_emp_Tests_passed set list_sl_no=? where employee_id=? and list_sl_no=?";
                    ps = con.prepareStatement(sql);
                    ps.setInt(1, serial);
                    ps.setInt(2, empid);
                    ps.setInt(3, list);
                    ps.executeUpdate();
                    con.commit();

                }

                // strcode=Integer.parseInt(request.getParameter("txtEmployeeid"));
                // System.out.println("assign..... Emp id Code::"+strcode);
                xml = xml + "<flag>success</flag>";
                System.out.println("Deleted successfully");
            }


            catch (Exception e) {

                System.out.println("catch........" + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";


        }

        else if (strCommand.equalsIgnoreCase("Load")) {
            xml = "<response><command>Load</command>";
            try {
                strcode =
                        Integer.parseInt(request.getParameter("txtEmployeeid"));
                System.out.println("assign..... Emp id Code::" + strcode);

                sql =
 "select * from\n" + "(\n" + "select * from \n" + "(\n" + "select employee_id,test_code as testid,to_char(date_of_exam,'dd/mm/yyyy') as dateexam,\n" +
   "to_char(effect_from_date,'dd/mm/yyyy') as effectdate,List_sl_no \n" +
   "from hrm_emp_tests_passed\n" + ")a\n" + "left outer join\n" + "(\n" +
   "select test_code ,test_Name from hrm_mst_DEPT_test\n" + ")b\n" +
   "on b.test_code=a.testid\n" + "\n" +
   ")where employee_id=? order by list_sl_no";

                ps = con.prepareStatement(sql);
                ps.setInt(1, strcode);
                rs = ps.executeQuery();
                int serno = 0;

                while (rs.next()) {
                    xml = xml + "<servicedata>";
                    xml =
 xml + "<EMPLOYEE_ID>" + rs.getInt("employee_id") + "</EMPLOYEE_ID>";
                    xml =
 xml + "<test>" + rs.getString("test_code") + "</test>";

                    xml =
 xml + "<dateexam>" + rs.getString("dateexam") + "</dateexam>";
                    xml =
 xml + "<effectdate>" + rs.getString("effectdate") + "</effectdate>";
                    xml =
 xml + "<listno>" + rs.getInt("list_sl_no") + "</listno>";
                    xml =
 xml + "<testname>" + rs.getString("test_name") + "</testname>";

                    System.out.println(rs.getInt("list_sl_no") +
                                       "no_of records------------------>");

                    xml = xml + "<flag>success</flag></servicedata>";

                    System.out.println(xml);
                }
                rs.close();
                ps.close();


            }


            catch (Exception e) {

                System.out.println("reload catch........" + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";


        }


        out.println(xml);
        System.out.println(xml);


    }
}
