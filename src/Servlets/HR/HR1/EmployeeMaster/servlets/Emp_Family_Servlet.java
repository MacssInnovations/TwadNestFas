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

public class Emp_Family_Servlet extends HttpServlet {
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
            System.out.println("emp_family details");

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
        //tablename="TEMPSR"+empid;
        //  System.out.println("Temp table Name:::"+tablename);
        int strcode = 0, slno = 0, designation = 0, officeid = 0, perflag =
            0, age = 0;
        String statusid = "", address1, address2, address3;
        Date dtfrom = null, dtto = null;
        String dtfromsession = "", dttosession = "", statusdetail =
            "", remarks = "", deptid = "";
        //String strname="";
        String xml = "";
        String fund_id, Nominee_Name, Nominee_address1, Nominee_address2, Nominee_address3, Nominee_Relationship, sql, Nominee_DOB, depend;
        String DOB, Relationship, Remarks, Member_Name, working_category =
            null, is_handicapt = null;
        int Nominee_pincode, txtsNo1;
        float Nominee_Pecentshare, sum = 0, per = 0;
        String strCommand = "", dateeffrom = "", married = "";
        Calendar c;
        int serial = 0, empid = 0;
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");


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
                String gender = "";
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
  con.prepareStatement("select EMPLOYEE_NAME||decode(EMPLOYEE_INITIAL,null,' ','.'||EMPLOYEE_INITIAL) as  EMPLOYEE_NAME,DATE_OF_BIRTH,GPF_NO,GENDER from HRM_MST_EMPLOYEES  where EMPLOYEE_ID=?");
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
                        xml =
 xml + "<gender>" + rs.getString("GENDER") + "</gender>";
                        gender = rs.getString("GENDER");
                        System.out.println("the gender is" + gender);
                    }
                    if (gender.equalsIgnoreCase("M")) {
                        ps =
  con.prepareStatement("select FAMILY_RELATIONSHIP_ID,FAMILY_RELATIONSHIP_DESC FROM HRM_MST_FAMILY_RELATIONS where FAMILY_RELATIONSHIP_DESC not in 'Husband' order by FAMILY_RELATIONSHIP_ID");
                        rs = ps.executeQuery();
                        System.out.println("first query executed");
                        while (rs.next()) {
                            xml =
 xml + "<details><family_id>" + rs.getInt("FAMILY_RELATIONSHIP_ID") +
   "</family_id><family_desc>" + rs.getString("FAMILY_RELATIONSHIP_DESC") +
   "</family_desc></details>";
                            System.out.println("the xml isssssssssssssssssssssssss" +
                                               xml);
                        }
                    }

                    else if (gender.equalsIgnoreCase("F")) {
                        ps =
  con.prepareStatement("select FAMILY_RELATIONSHIP_ID,FAMILY_RELATIONSHIP_DESC FROM HRM_MST_FAMILY_RELATIONS where FAMILY_RELATIONSHIP_DESC not in 'Wife' order by FAMILY_RELATIONSHIP_ID");
                        rs = ps.executeQuery();
                        System.out.println("first query executed");
                        while (rs.next()) {
                            xml =
 xml + "<details><family_id>" + rs.getInt("FAMILY_RELATIONSHIP_ID") +
   "</family_id><family_desc>" + rs.getString("FAMILY_RELATIONSHIP_DESC") +
   "</family_desc></details>";
                            System.out.println("the xml isssssssssssssssssssssssss" +
                                               xml);
                        }
                    }

                    rs.close();
                    ps.close();


                    xml = xml + "<flag>success</flag>";


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
            Member_Name = request.getParameter("Member_Name");
            System.out.println(Member_Name);
            Relationship = request.getParameter("Relationship");
            System.out.println(Relationship);
            depend = request.getParameter("depend");
            System.out.println(depend);
            address1 = request.getParameter("address1");
            address2 = request.getParameter("address2");
            address3 = request.getParameter("address3");
            married = request.getParameter("married");
            System.out.println("hgjhgjkhgjk" + address1 + address2 + address3);
            System.out.println("pincode" + request.getParameter("pincode"));
            pincode = Integer.parseInt(request.getParameter("pincode"));
            System.out.println("hgjhgj" + pincode);
            Remarks = request.getParameter("Remarks");
            System.out.println("remarks" + Remarks);
            DOB = request.getParameter("DOB");
            System.out.println("DOB" + DOB);
            System.out.println("Ageaaaaaaaaaaaaaaaaaaaaaaaaa" +
                               request.getParameter("age"));
            System.out.println("age=" + age);
            age = Integer.parseInt(request.getParameter("age"));
            working_category = request.getParameter("working_category");
            System.out.println("remarks" + working_category);
            is_handicapt = request.getParameter("is_handicapt");
            System.out.println("is_handicapt" + is_handicapt);
            try {
                if (perflag != 1) {
                    System.out.println("flag is true");
                    sql =
 "select max(LIST_SL_NO) as maxval from HRM_EMP_FAMILY_DETAILS where EMPLOYEE_ID=?";
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
                    System.out.println(sid + "ssssssssssssss");

                    String updatedby = (String)session.getAttribute("UserId");
                    long lng = System.currentTimeMillis();
                    Timestamp ts = new Timestamp(lng);
                    System.out.println("query add");
                    sql =
 " insert into HRM_EMP_FAMILY_DETAILS (EMPLOYEE_ID,LIST_SL_NO,FAMILY_MEMBER_NAME,DATE_OF_BIRTH,RELATIONSHIP_ID,IS_DEPENDENT,REMARKS,UPDATED_BY_USER_ID,UPDATED_DATE,ADDRESS1,ADDRESS2,ADDRESS3,PINCODE,AGE,MARRIED,SPOUSE_WORK_STATUS,HANDICAPPED) " +
   "   values (?,?,?,to_date(?,'dd/mm/yyyy'),?,?,?,?,?,?,?,?,?,?,?,?,?) ";
                    //(eid,sid,dtfrom,dtfromses,dtto,dttoses,designation,officeid,statusid,statusdetail,remarks,deptid );
                    System.out.println(sql);
                    ps = con.prepareStatement(sql);
                    ps.setInt(1, strcode);
                    ps.setInt(2, sid);
                    ps.setString(3, Member_Name);
                    ps.setString(4, DOB);
                    ps.setString(5, Relationship);
                    ps.setString(6, depend);

                    ps.setString(7, Remarks);


                    ps.setString(8, updatedby);
                    ps.setTimestamp(9, ts);
                    ps.setString(10, address1);
                    ps.setString(11, address2);
                    ps.setString(12, address3);
                    ps.setInt(13, pincode);
                    ps.setInt(14, age);
                    ps.setString(15, married);
                    ps.setString(16, working_category);
                    ps.setString(17, is_handicapt);
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
            Member_Name = request.getParameter("Member_Name");
            System.out.println("hellollllllllllllllllllllll" + Member_Name);
            Relationship = request.getParameter("Relationship");
            depend = request.getParameter("depend");
            Remarks = request.getParameter("Remarks");
            DOB = request.getParameter("DOB");
            slno = Integer.parseInt(request.getParameter("txtSNO"));
            address1 = request.getParameter("address1");
            address2 = request.getParameter("address2");
            address3 = request.getParameter("address3");
            married = request.getParameter("married");
            System.out.println(address1 + address2 + address3 + married);
            pincode = Integer.parseInt(request.getParameter("pincode"));
            System.out.println(pincode);
            System.out.println("sssssssss" + request.getParameter("age"));
            age = Integer.parseInt(request.getParameter("age"));
            working_category = request.getParameter("working_category");
            is_handicapt = request.getParameter("is_handicapt");
            System.out.println("is_handicapt" + is_handicapt);
            System.out.println("---------------------------------");
            String updatedby = (String)session.getAttribute("UserId");
            long lng = System.currentTimeMillis();
            Timestamp ts = new Timestamp(lng);


            /////////////////////////////

            try {

                sql =
 " update HRM_EMP_FAMILY_DETAILS set FAMILY_MEMBER_NAME=?,RELATIONSHIP_ID=?,DATE_OF_BIRTH=to_date(?,'dd/mm/yyyy'),IS_DEPENDENT=?,REMARKS=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=?,ADDRESS1=?,ADDRESS2=?,ADDRESS3=?,PINCODE=?,AGE=?,married=?,SPOUSE_WORK_STATUS=?,HANDICAPPED=? where EMPLOYEE_ID=? and  LIST_SL_NO=? ";

                //(eid,sid,dtfrom,dtfromses,dtto,dttoses,designation,officeid,statusid,statusdetail,remarks,deptid );
                System.out.println(sql);
                ps = con.prepareStatement(sql);
                ps.setInt(17, slno);
                ps.setString(1, Member_Name);
                ps.setString(2, Relationship);
                ps.setString(3, DOB);
                ps.setString(4, depend);
                ps.setString(5, Remarks);
                ps.setString(6, updatedby);
                ps.setTimestamp(7, ts);

                ps.setString(8, address1);
                ps.setString(9, address2);
                ps.setString(10, address3);
                ps.setInt(11, pincode);
                ps.setInt(12, age);
                ps.setString(13, married);
                ps.setString(14, working_category);
                ps.setString(15, is_handicapt);
                ps.setInt(16, strcode);

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
            xml = "<response><command>Delete</command>";

            //fund_id=request.getParameter("fund_id");
            slno = Integer.parseInt(request.getParameter("txtSNO"));
            System.out.println(slno);
            try {

                con.setAutoCommit(false);
                sql =
 " delete from HRM_EMP_FAMILY_DETAILS where EMPLOYEE_ID=? and LIST_SL_NO=?";
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
 "select list_sl_no,employee_id from hrm_emp_family_details where list_sl_no>?";
                ps = con.prepareStatement(sql);
                ps.setInt(1, slno);
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
 "update hrm_emp_family_details set list_sl_no=? where employee_id=? and list_sl_no=?";
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
 "select * from (select employee_id,list_sl_no,family_member_name,to_char(date_of_birth,'dd/mm/yyyy') as DOB,relationship_id,is_dependent as depend,remarks,age,address1,address2,address3,pincode,married,spouse_work_status,handicapped from hrm_emp_family_details where employee_id=?)a left outer join(select family_relationship_desc as relation,family_relationship_id from hrm_mst_family_relations )c on a.relationship_id=c.family_relationship_id order by list_sl_no";

                ps = con.prepareStatement(sql);
                ps.setInt(1, strcode);
                rs = ps.executeQuery();
                int serno = 0;

                while (rs.next()) {
                    xml = xml + "<servicedata>";
                    xml =
 xml + "<EMPLOYEE_ID>" + rs.getInt("employee_id") + "</EMPLOYEE_ID>";
                    xml =
 xml + "<rel>" + rs.getInt("relationship_id") + "</rel>";
                    xml =
 xml + "<LIST_SL_NO>" + rs.getInt("LIST_SL_NO") + "</LIST_SL_NO>";
                    xml =
 xml + "<MEMBER_NAME>" + rs.getString("FAMILY_MEMBER_NAME") + "</MEMBER_NAME>";
                    xml =
 xml + "<RELATIONSHIP_ID>" + rs.getString("relation") + "</RELATIONSHIP_ID>";
                    xml = xml + "<DOB>" + rs.getString("DOB") + "</DOB>";
                    xml =
 xml + "<REMARKS>" + rs.getString("REMARKS") + "</REMARKS>";
                    xml =
 xml + "<DEPEND>" + rs.getString("depend") + "</DEPEND>";
                    xml = xml + "<AGE>" + rs.getString("AGE") + "</AGE>";

                    xml =
 xml + "<ADDRESS1>" + rs.getString("ADDRESS1") + "</ADDRESS1>";
                    xml =
 xml + "<ADDRESS2>" + rs.getString("ADDRESS2") + "</ADDRESS2>";
                    xml =
 xml + "<ADDRESS3>" + rs.getString("ADDRESS3") + "</ADDRESS3>";
                    xml =
 xml + "<PINCODE>" + rs.getString("PINCODE") + "</PINCODE>";
                    xml =
 xml + "<Married>" + rs.getString("Married") + "</Married>";
                    working_category = rs.getString("spouse_work_status");
                    if (working_category == null ||
                        working_category.equalsIgnoreCase(""))
                        working_category = "-";
                    is_handicapt = rs.getString("handicapped");
                    if (is_handicapt == null ||
                        is_handicapt.equalsIgnoreCase(""))
                        is_handicapt = "-";
                    xml =
 xml + "<spouse_work_status>" + working_category + "</spouse_work_status>";
                    xml =
 xml + "<handicapped>" + is_handicapt + "</handicapped>";
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
