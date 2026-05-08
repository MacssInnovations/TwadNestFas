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

public class Emp_Nomination_Servlet extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";


    public void init(ServletConfig config) throws ServletException {
        super.init(config);


    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {

        Connection con = null;
        try {
            System.out.println("emp_nomination");

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
        int sid = 0;
        HttpSession session = request.getSession(false);
        int l = session.getId().length();
        l = l - 10;
        int addflag = 0;
        tablename = "TEMPSR_" + session.getId().substring(l);
        //tablename="TEMPSR"+empid;
        //  System.out.println("Temp table Name:::"+tablename);
        int strcode = 0, slno = 0, designation = 0, officeid = 0, perflag = 0;
        String statusid = "";
        Date dtfrom = null, dtto = null;
        String dtfromsession = "", dttosession = "", statusdetail =
            "", remarks = "", deptid = "";
        //String strname="";
        String xml = "", relationship_id = "";
        String fund_id =
            "1", Nominee_Name, Nominee_address1, Nominee_address2, Nominee_address3, Nominee_Relationship, sql, Nominee_DOB;
        int Nominee_pincode, txtsNo1, serial, empid = 0, Nominee_Age = 0;
        float Nominee_Pecentshare, sum = 0, per = 0;
        String strCommand = "", dateeffrom = "", nominee;
        Calendar c;
        int cfund = 0, fund, pf = 0, gf = 0, inf = 0;
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");


        strCommand = request.getParameter("Command").trim();
        System.out.println("assign....." + strCommand);
        strcode = Integer.parseInt(request.getParameter("txtEmployeeid"));


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

                        fund_id = request.getParameter("fund_id");
                        try {
                            ps =
  con.prepareStatement("select family_member_name as member_name,list_sl_no from hrm_emp_family_details where employee_id=?");
                            /*"and family_member_name not in"+
                                                      "(select nominee_name from hrm_emp_nomination_details where fund_id=?)");*/
                            ps.setInt(1, strcode);
                            //ps.setString(2,fund_id);
                            rs = ps.executeQuery();
                            xml = xml + "<select>";
                            while (rs.next()) {
                                xml = xml + "<option>";
                                xml =
 xml + "<member>" + rs.getString("member_name") + "</member>";
                                System.out.println("member_name" +
                                                   rs.getString("member_name"));
                                xml = xml + "</option>";

                            }
                            xml = xml + "</select>";

                            rs.close();
                            ps.close();
                        } catch (Exception e) {
                            System.out.println("Exception combo " + e);
                        }
                        try {
                            sql =
 "select * from HRM_EMP_NOMINATIONS where EMPLOYEE_ID=? and FUND_ID=?";
                            ps = con.prepareStatement(sql);
                            ps.setInt(1, strcode);
                            ps.setString(2, fund_id);
                            rs = ps.executeQuery();
                            // ps.close();
                            while (rs.next()) {
                                addflag = 1;
                                System.out.println("already exists");
                            }
                            if (addflag != 1)
                                xml = xml + "<exist>not</exist>";
                            else
                                xml = xml + "<exist>exist</exist>";

                        } catch (Exception e) {
                            System.out.println(e + "error in exists");
                        }
                        try {
                            ps =
  con.prepareStatement("select *  from (select fund_id,count(fund_id) as cfund from hrm_emp_nominations where employee_id=? group by fund_id order by fund_id )a left outer join(select benefit_fund_name,benefit_fund_id from hrm_mst_benefit_funds )b on b.benefit_fund_id=a.fund_id");

                            ps.setInt(1, strcode);
                            //ps.setString(2,fund_id);
                            rs = ps.executeQuery();
                            while (rs.next()) {
                                System.out.println("fund count");
                                fund = rs.getInt("fund_id");
                                cfund = rs.getInt("cfund");
                                if (fund == 1)
                                    pf = cfund;
                                else if (fund == 2)
                                    gf = cfund;
                                else if (fund == 3)
                                    inf = cfund;
                            }
                            xml =
 xml + "<pf>" + pf + "</pf>" + "<gf>" + gf + "</gf><inf>" + inf + "</inf>";

                        } catch (Exception e) {
                            System.out.println("Exception funds" + e);
                        }

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


        else if (strCommand.equalsIgnoreCase("Submit")) {
            xml = "<response><command>Submit</command>";
            boolean flag = true;
            System.out.println("Submit----------------->");
            fund_id = request.getParameter("fund_id");
            System.out.println("Fund Id = " + fund_id);
            String record = request.getParameter("record");
            System.out.println("The Record = " + record);
            dateeffrom = request.getParameter("nomindate");
            String records[] = record.split("~");
            System.out.println("Record Length = " + records.length);
            for (int i = 0; i < records.length; i++) {
                String values[] = records[i].split(",");

                sid = Integer.parseInt(values[0]);
                System.out.println("The SId = " + sid);
                Nominee_Name = values[1];
                System.out.println("Name = " + Nominee_Name);
                Nominee_address1 = values[2];
                Nominee_address2 = values[3];
                Nominee_address3 = values[4];
                System.out.println("Address = " + Nominee_address1 + "," +
                                   Nominee_address2 + "," + Nominee_address3);
                Nominee_pincode = Integer.parseInt(values[5]);
                System.out.println("Nominee_pincode = " + Nominee_pincode);
                Nominee_DOB = values[6];
                System.out.println("Nominee_DOB = " + Nominee_DOB);
                Nominee_Age = Integer.parseInt(values[7]);
                System.out.println("Nominee_Age = " + Nominee_Age);
                Nominee_Relationship = values[8];
                System.out.println("Nominee_Relationship = " +
                                   Nominee_Relationship);
                relationship_id = values[9];
                System.out.println("relationship_id = " + relationship_id);
                Nominee_Pecentshare = Float.parseFloat(values[10]);
                System.out.println("Nominee_Pecentshare = " +
                                   Nominee_Pecentshare);
                System.out.println(fund_id + Nominee_Name + Nominee_Name +
                                   Nominee_address1 + Nominee_address2 +
                                   Nominee_address3 + Nominee_pincode +
                                   Nominee_Relationship);


                /*  Nominee_Name=request.getParameter("Nominee_Name");
                     Nominee_address1=request.getParameter("Nominee_address1");
                     Nominee_address2=request.getParameter("Nominee_address2");
                     Nominee_address3=request.getParameter("Nominee_address3");
                     Nominee_pincode=Integer.parseInt(request.getParameter("Nominee_pincode"));
                     Nominee_Relationship=request.getParameter("Nominee_Relationship");
                     Nominee_DOB=request.getParameter("Nominee_DOB");
                     Nominee_Age=Integer.parseInt(request.getParameter("Nominee_Age"));
                     Nominee_Pecentshare=Float.parseFloat(request.getParameter("Nominee_Pecentshare"));
                     relationship_id=request.getParameter("relation_id");*/

                /*   try
                     {

                             sql="select sum(PERCENTAGE_SHARE) as sumofper from HRM_EMP_NOMINATION_DETAILS where Employee_id=? and fund_id=?";
                             ps=con.prepareStatement(sql);
                             ps.setInt(1,strcode);
                             ps.setString(2,fund_id);
                             rs = ps.executeQuery();
                             if(rs.next()) {
                                sum=rs.getFloat("sumofper");
                                System.out.println(sum);
                                        if (sum>100)
                                           perflag=1;
                                         sum=sum+Nominee_Pecentshare;
                                         System.out.println("The share = "+sum);
                                         if(sum>100)
                                         perflag=1;


                             }
                     }catch(Exception e) {
                         System.out.println("sum exception "+e);
                         e.printStackTrace();
                     }*/

                try {


                    String updatedby = (String)session.getAttribute("UserId");
                    long lng = System.currentTimeMillis();
                    Timestamp ts = new Timestamp(lng);
                    System.out.println("query add");
                    sql =
 " insert into HRM_EMP_NOMINATION_DETAILS (EMPLOYEE_ID,FUND_ID,LIST_SL_NO,NOMINEE_NAME,NOMINEE_ADDRESS1,NOMINEE_ADDRESS2,NOMINEE_ADDRESS3,NOMINEE_PINCODE,NOMINEE_RELATIONSHIP_DESC,NOMINEE_DOB,PERCENTAGE_SHARE,UPDATED_BY_USER_ID,UPDATED_DATE,NOMINEE_AGE,NOMINEE_RELATIONSHIP_ID,PROCESS_FLOW_STATUS_ID ) " +
   "   values (?,?,?,?,?,?,?,?,?,to_date(?,'dd/mm/yyyy'),?,?,?,?,?,?) ";
                    //(eid,sid,dtfrom,dtfromses,dtto,dttoses,designation,officeid,statusid,statusdetail,remarks,deptid );
                    System.out.println(sql);
                    ps = con.prepareStatement(sql);
                    ps.setInt(1, strcode);
                    ps.setString(2, fund_id);
                    ps.setInt(3, sid);
                    ps.setString(4, Nominee_Name);
                    ps.setString(5, Nominee_address1);
                    ps.setString(6, Nominee_address2);
                    ps.setString(7, Nominee_address3);


                    ps.setInt(8, Nominee_pincode);
                    ps.setString(9, Nominee_Relationship);
                    ps.setString(10, Nominee_DOB);
                    ps.setFloat(11, Nominee_Pecentshare);


                    ps.setString(12, updatedby);
                    ps.setTimestamp(13, ts);
                    ps.setInt(14, Nominee_Age);
                    ps.setString(15, relationship_id);
                    ps.setString(16, "CR");
                    ps.executeUpdate();
                    ps.close();
                    /* java.util.Date date = new java.util.Date();
                             int day = date.getDate()+1;
                             int month = date.getMonth()+1;
                             int year = date.getYear()+1900;
                              String dateeffrom=(day+'/'+month+'/'+year);
                              System.out.println(day+"fffffffffffffffffffffffffffffffffff");*/
                    strcode =
                            Integer.parseInt(request.getParameter("txtEmployeeid"));
                    System.out.println(strcode +
                                       "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                    sql =
 "select to_char(now(),'dd/mm/yyyy')as now()1 ";
                    ps = con.prepareStatement(sql);
                    rs = ps.executeQuery();
                    while (rs.next()) {

                        System.out.println(dateeffrom);
                        addflag = 0;
                        //  ps.close();
                        sql =
 "select * from HRM_EMP_NOMINATIONS where EMPLOYEE_ID=? and FUND_ID=?";
                        ps = con.prepareStatement(sql);
                        ps.setInt(1, strcode);
                        ps.setString(2, fund_id);
                        rs = ps.executeQuery();
                        // ps.close();
                        while (rs.next()) {
                            addflag = 1;
                            System.out.println("already exists");
                        }
                        if (addflag != 1) {
                            System.out.println("veronica");

                            sql =
 " insert into HRM_EMP_NOMINATIONS (EMPLOYEE_ID,FUND_ID,DATE_EFFECT_FROM,UPDATED_BY_USER_ID,UPDATED_DATE,PROCESS_FLOW_STATUS_ID ) " +
   "   values (?,?,to_date(?,'dd/mm/yyyy'),?,?,?) ";

                            ps = con.prepareStatement(sql);
                            ps.setInt(1, strcode);
                            ps.setString(2, fund_id);
                            ps.setString(3, dateeffrom);
                            ps.setString(4, updatedby);
                            ps.setTimestamp(5, ts);
                            ps.setString(6, "CR");
                            ps.executeUpdate();
                            ps.close();
                            System.out.println("aloysius");
                            System.out.println("ADDED successfully");

                        }
                    }
                    xml =
 xml + "<servicedata><flag>success</flag><genid>" + sid +
   "</genid><EMPLOYEE_ID>" + strcode + "</EMPLOYEE_ID><FUND_ID>" + fund_id +
   "</FUND_ID><LIST_SL_NO>" + sid + "</LIST_SL_NO><NOMINEE_NAME>" +
   Nominee_Name + "</NOMINEE_NAME><NOMINEE_ADDRESS1>" + Nominee_address1 +
   "</NOMINEE_ADDRESS1><NOMINEE_ADDRESS2>" + Nominee_address2 +
   "</NOMINEE_ADDRESS2><NOMINEE_ADDRESS3>" + Nominee_address2 +
   "</NOMINEE_ADDRESS3><RELATIONSHIP_ID>" + Nominee_Relationship +
   "</RELATIONSHIP_ID><NOMINEE_DOB>" + Nominee_DOB +
   "</NOMINEE_DOB><PERCENTAGE_SHARE>" + Nominee_Pecentshare +
   "</PERCENTAGE_SHARE><NOMINEE_PINCODE>" + Nominee_pincode +
   "</NOMINEE_PINCODE></servicedata>";


                }

                catch (Exception e) {

                    System.out.println("catch........" + e);
                    xml = xml + "<flag>failure</flag>";
                }

            }
            /*}
                 else {
                     String values[] = record.split(",");

                     sid=Integer.parseInt(values[0]);
                     System.out.println("The SId = "+sid);
                     Nominee_Name=values[1];
                     System.out.println("Name = "+Nominee_Name);
                     Nominee_address1=values[2];
                     Nominee_address2=values[3];
                     Nominee_address3=values[4];
                     System.out.println("Address = "+Nominee_address1+","+Nominee_address2+","+Nominee_address3);
                     Nominee_pincode=Integer.parseInt(values[5]);
                     System.out.println("Nominee_pincode = "+Nominee_pincode);
                     Nominee_DOB=values[6];
                     System.out.println("Nominee_DOB = "+Nominee_DOB);
                     Nominee_Age=Integer.parseInt(values[7]);
                     System.out.println("Nominee_Age = "+Nominee_Age);
                     Nominee_Relationship=values[8];
                     System.out.println("Nominee_Relationship = "+Nominee_Relationship);
                     relationship_id=values[9];
                     System.out.println("relationship_id = "+relationship_id);
                     Nominee_Pecentshare=Float.parseFloat(values[10]);
                     System.out.println("Nominee_Pecentshare = "+Nominee_Pecentshare);
                     System.out.println(fund_id+Nominee_Name+Nominee_Name+ Nominee_address1+ Nominee_address2+ Nominee_address3+Nominee_pincode+Nominee_Relationship);



                     try
                     {


                     String updatedby=(String)session.getAttribute("UserId");
                     long lng=System.currentTimeMillis();
                     Timestamp ts=new Timestamp(lng);
                     System.out.println("query add");
                     sql=" insert into HRM_EMP_NOMINATION_DETAILS (EMPLOYEE_ID,FUND_ID,LIST_SL_NO,NOMINEE_NAME,NOMINEE_ADDRESS1,NOMINEE_ADDRESS2,NOMINEE_ADDRESS3,NOMINEE_PINCODE,NOMINEE_RELATIONSHIP_DESC,NOMINEE_DOB,PERCENTAGE_SHARE,UPDATED_BY_USER_ID,UPDATED_DATE,NOMINEE_AGE,NOMINEE_RELATIONSHIP_ID ) "+
                     "   values (?,?,?,?,?,?,?,?,?,to_date(?,'dd/mm/yyyy'),?,?,?,?,?) ";
                     //(eid,sid,dtfrom,dtfromses,dtto,dttoses,designation,officeid,statusid,statusdetail,remarks,deptid );
                     System.out.println(sql);
                     ps=con.prepareStatement(sql);
                     ps.setInt(1,strcode);
                     ps.setString(2,fund_id);
                     ps.setInt(3,sid);
                     ps.setString(4,Nominee_Name);
                     ps.setString(5,Nominee_address1);
                     ps.setString(6,Nominee_address2);
                     ps.setString(7,Nominee_address3);


                     ps.setInt(8, Nominee_pincode);
                     ps.setString(9, Nominee_Relationship);
                     ps.setString(10, Nominee_DOB);
                     ps.setFloat(11,Nominee_Pecentshare);


                     ps.setString(12,updatedby);
                     ps.setTimestamp(13,ts);
                     ps.setInt(14,Nominee_Age);
                     ps.setString(15,relationship_id);
                     ps.executeUpdate();
                     ps.close();
                     /* java.util.Date date = new java.util.Date();
                     int day = date.getDate()+1;
                     int month = date.getMonth()+1;
                     int year = date.getYear()+1900;
                     String dateeffrom=(day+'/'+month+'/'+year);
                     System.out.println(day+"fffffffffffffffffffffffffffffffffff");
                     strcode=Integer.parseInt(request.getParameter("txtEmployeeid"));
                     System.out.println(strcode+"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                     sql="select to_char(now(),'dd/mm/yyyy')as now()1 ";
                     ps=con.prepareStatement(sql);
                     rs=ps.executeQuery();
                     while(rs.next()) {
                     dateeffrom=rs.getString("now()1");
                     System.out.println(dateeffrom);

                     //  ps.close();
                     sql="select * from HRM_EMP_NOMINATIONS where EMPLOYEE_ID=? and FUND_ID=?";
                             ps=con.prepareStatement(sql);
                             ps.setInt(1,strcode);
                             ps.setString(2,fund_id);
                            rs=ps.executeQuery();
                            // ps.close();
                             while(rs.next()) {
                                 addflag=1;
                                 System.out.println("already exists");
                             }
                             if (addflag!=1)
                             {
                      System.out.println("veronica");

                     sql=" insert into HRM_EMP_NOMINATIONS (EMPLOYEE_ID,FUND_ID,DATE_EFFECT_FROM,UPDATED_BY_USER_ID,UPDATED_DATE ) "+
                      "   values (?,?,to_date(?,'dd/mm/yyyy'),?,?) ";

                     ps=con.prepareStatement(sql);
                     ps.setInt(1,strcode);
                     ps.setString(2,fund_id);
                     ps.setString(3, dateeffrom);
                     ps.setString(4,updatedby);
                     ps.setTimestamp(5,ts);
                     ps.executeUpdate();
                     ps.close();
                     System.out.println("aloysius");
                     System.out.println("ADDED successfully");

                             }     }
                     xml=xml+"<servicedata><flag>success</flag><genid>"+sid+"</genid><EMPLOYEE_ID>"+strcode+"</EMPLOYEE_ID><FUND_ID>"+fund_id+"</FUND_ID><LIST_SL_NO>"+sid+"</LIST_SL_NO><NOMINEE_NAME>"+Nominee_Name+"</NOMINEE_NAME><NOMINEE_ADDRESS1>"+Nominee_address1+"</NOMINEE_ADDRESS1><NOMINEE_ADDRESS2>"+Nominee_address2+"</NOMINEE_ADDRESS2><NOMINEE_ADDRESS3>"+Nominee_address2+"</NOMINEE_ADDRESS3><RELATIONSHIP_ID>"+Nominee_Relationship+"</RELATIONSHIP_ID><NOMINEE_DOB>"+Nominee_DOB+"</NOMINEE_DOB><PERCENTAGE_SHARE>"+Nominee_Pecentshare+"</PERCENTAGE_SHARE><NOMINEE_PINCODE>"+Nominee_pincode+"</NOMINEE_PINCODE></servicedata>";






                     }

                     catch(Exception e) {

                     System.out.println("catch........"+e);
                     xml=xml+"<flag>failure</flag>";
                     }

                }*/


            xml = xml + "</response>";


        }

        else if (strCommand.equalsIgnoreCase("Update")) {
            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaa");
            xml = "<response><command>Update</command>";
            boolean flag = true;
            fund_id = request.getParameter("fund_id");
            slno = Integer.parseInt(request.getParameter("txtSNO"));
            System.out.println(slno);

            Nominee_Name = request.getParameter("Nominee_Name");
            System.out.println(Nominee_Name);
            Nominee_address1 = request.getParameter("Nominee_address1");
            System.out.println(Nominee_address1);
            Nominee_address2 = request.getParameter("Nominee_address2");
            System.out.println(Nominee_address2);
            Nominee_address3 = request.getParameter("Nominee_address3");
            System.out.println(Nominee_address3);
            Nominee_pincode =
                    Integer.parseInt(request.getParameter("Nominee_pincode"));
            System.out.println(Nominee_pincode);
            Nominee_Relationship =
                    request.getParameter("Nominee_Relationship");
            System.out.println(Nominee_Relationship);
            Nominee_DOB = request.getParameter("Nominee_DOB");
            System.out.println(Nominee_DOB);
            relationship_id = request.getParameter("relation_id");
            Nominee_Pecentshare =
                    Float.parseFloat(request.getParameter("Nominee_Pecentshare"));
            System.out.println("Nominee_Pecentshare" + Nominee_Pecentshare);
            System.out.println("AGE is ------------" +
                               request.getParameter("Nominee_Age"));
            Nominee_Age =
                    Integer.parseInt(request.getParameter("Nominee_Age"));
            System.out.println("Nominee_Age" + Nominee_Age);

            System.out.println("---------------------------------");
            String updatedby = (String)session.getAttribute("UserId");
            long lng = System.currentTimeMillis();
            Timestamp ts = new Timestamp(lng);

            try {


                /////////////////////////////
                sql =
 "select percentage_share from hrm_emp_nomination_details  where employee_id=? and fund_id=? and list_sl_no=?";
                ps = con.prepareStatement(sql);
                ps.setInt(1, strcode);
                ps.setString(2, fund_id);
                ps.setInt(3, slno);
                rs = ps.executeQuery();
                if (rs.next()) {
                    per = rs.getFloat("percentage_share");
                }

                sql =
 "select sum(PERCENTAGE_SHARE) as sumofper from HRM_EMP_NOMINATION_DETAILS where Employee_id=? and fund_id=?";
                ps = con.prepareStatement(sql);
                ps.setInt(1, strcode);
                ps.setString(2, fund_id);
                rs = ps.executeQuery();
                if (rs.next()) {
                    sum = rs.getFloat("sumofper");
                    System.out.println(sum);

                    sum = sum - per;
                    sum = sum + Nominee_Pecentshare;
                    System.out.println("The share = " + sum);
                    if (sum > 100)
                        perflag = 1;


                }
            } catch (Exception e) {
                System.out.println("sum exception " + e);
                e.printStackTrace();
            }

            try {
                if (perflag != 1) {
                    sql =
 " update HRM_EMP_NOMINATION_DETAILS set NOMINEE_NAME=?,NOMINEE_ADDRESS1=?,NOMINEE_ADDRESS2=?,NOMINEE_ADDRESS3=?,NOMINEE_PINCODE=?,NOMINEE_RELATIONSHIP_DESC=?,NOMINEE_DOB=to_date(?,'dd/mm/yyyy'),PERCENTAGE_SHARE=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=?,NOMINEE_AGE=?,NOMINEE_RELATIONSHIP_ID=? where EMPLOYEE_ID=? and FUND_ID=? and  LIST_SL_NO=? ";

                    //(eid,sid,dtfrom,dtfromses,dtto,dttoses,designation,officeid,statusid,statusdetail,remarks,deptid );
                    System.out.println(sql);
                    ps = con.prepareStatement(sql);
                    ps.setString(14, fund_id);
                    ps.setInt(15, slno);
                    ps.setString(1, Nominee_Name);
                    ps.setString(2, Nominee_address1);
                    ps.setString(3, Nominee_address2);
                    ps.setString(4, Nominee_address3);
                    ps.setInt(5, Nominee_pincode);
                    ps.setString(6, Nominee_Relationship);
                    ps.setString(7, Nominee_DOB);
                    ps.setFloat(8, Nominee_Pecentshare);

                    ps.setString(9, updatedby);
                    ps.setInt(11, Nominee_Age);
                    ps.setString(12, relationship_id);
                    ps.setTimestamp(10, ts);
                    ps.setInt(13, strcode);

                    ps.executeUpdate();
                    ps.close();
                    System.out.println("Updated successfully");
                    xml = xml + "<flag>success</flag>";
                } else {
                    System.out.println("Share value is not correct");
                    xml = xml + "<flag>failure1</flag>";
                }

            }

            catch (Exception e) {

                System.out.println("catch........" + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";


        } else if (strCommand.equalsIgnoreCase("loadnominee")) {
            xml = "<response><command>loadnominee</command>";
            try {
                strcode =
                        Integer.parseInt(request.getParameter("txtEmployeeid"));
                nominee = request.getParameter("nominee");
                System.out.println("assign..... Emp id Code::" + strcode);

                sql =
 "select * from (select employee_id,list_sl_no,family_member_name,to_char(date_of_birth,'dd/mm/yyyy') as DOB,relationship_id ,is_dependent as depend,remarks,age,address1,address2,address3,pincode from hrm_emp_family_details )a left outer join (select family_relationship_id,family_relationship_desc as relation_desc from hrm_mst_family_relations)b on a.relationship_id=b.family_relationship_id where a.employee_id=?  and a.family_member_name=?";

                ps = con.prepareStatement(sql);
                ps.setInt(1, strcode);
                ps.setString(2, nominee);
                rs = ps.executeQuery();
                int serno = 0;

                if (rs.next()) {
                    xml = xml + "<servicedata>";
                    xml =
 xml + "<EMPLOYEE_ID>" + rs.getInt("employee_id") + "</EMPLOYEE_ID>";
                    xml =
 xml + "<LIST_SL_NO>" + rs.getInt("LIST_SL_NO") + "</LIST_SL_NO>";
                    xml =
 xml + "<MEMBER_NAME>" + rs.getString("FAMILY_MEMBER_NAME") + "</MEMBER_NAME>";
                    xml =
 xml + "<RELATIONSHIP_ID>" + rs.getString("relation_desc") +
   "</RELATIONSHIP_ID>";
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

        else if (strCommand.equalsIgnoreCase("Delete")) {
            xml = "<response><command>Delete</command>";

            fund_id = request.getParameter("fund_id");
            slno = Integer.parseInt(request.getParameter("txtSNO"));
            System.out.println(slno);
            try {


                sql =
 " delete from HRM_EMP_NOMINATION_DETAILS where EMPLOYEE_ID=? and FUND_ID=? and LIST_SL_NO=?";
                ps = con.prepareStatement(sql);
                ps.setInt(1, strcode);
                ps.setString(2, fund_id);
                ps.setInt(3, slno);
                ps.executeUpdate();
                // xml=xml+"<flag>success</flag>";
                ps.close();
                con.commit();
                sql =
 "select list_sl_no,employee_id from hrm_emp_nomination_details where list_sl_no>? and fund_id=?";
                ps = con.prepareStatement(sql);
                ps.setInt(1, slno);
                ps.setString(2, fund_id);
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
 "update hrm_emp_nomination_details set list_sl_no=? where employee_id=? and list_sl_no=? and fund_id=?";
                    ps = con.prepareStatement(sql);
                    ps.setInt(1, serial);
                    ps.setInt(2, empid);
                    ps.setString(4, fund_id);
                    ps.setInt(3, list);
                    ps.executeUpdate();
                    con.commit();

                }


                // strcode=Integer.parseInt(request.getParameter("txtEmployeeid"));
                // System.out.println("assign..... Emp id Code::"+strcode);
                xml = xml + "<flag>success</flag>";

            }


            catch (Exception e) {

                System.out.println("catch........" + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";


        }

        else if (strCommand.equalsIgnoreCase("Load")) {
            xml = "<response><command>Load</command>";
            strcode = Integer.parseInt(request.getParameter("txtEmployeeid"));
            fund_id = request.getParameter("fund_id");
            System.out.println("kkkkkkkkkk" + fund_id);
            System.out.println("assign..... Emp id Code::" + strcode);

            try {

                sql =
 "select * from(select employee_id,fund_id,list_sl_no,nominee_name,Nominee_address1,nominee_address2,nominee_age,nominee_address3,nominee_pincode,Nominee_relationship_id,to_char(nominee_dob,'dd/mm/yyyy') as DOB,nominee_relationship_desc as rel_desc,percentage_share from hrm_emp_nomination_details  )a left outer join(select benefit_fund_name as fund,benefit_fund_id from hrm_mst_benefit_funds)b on b.benefit_fund_id=a.fund_id where employee_id=? and a.fund_id=? order by list_sl_no,fund_id";
                ps = con.prepareStatement(sql);
                ps.setInt(1, strcode);
                ps.setString(2, fund_id);
                rs = ps.executeQuery();
                int serno = 0;
                while (rs.next()) {
                    System.out.println("recorded");
                    xml =
 xml + "<servicedata><FUND_ID>" + rs.getString("fund") + "</FUND_ID>";
                    xml =
 xml + "<EMPLOYEE_ID>" + rs.getInt("employee_id") + "</EMPLOYEE_ID>";
                    xml = xml + "<fund>" + rs.getInt("fund_id") + "</fund>";
                    xml =
 xml + "<rel>" + rs.getString("Nominee_relationship_id") + "</rel>";
                    System.out.println(rs.getString("Nominee_relationship_id") +
                                       rs.getInt("LIST_SL_NO"));
                    xml =
 xml + "<LIST_SL_NO>" + rs.getInt("LIST_SL_NO") + "</LIST_SL_NO>";
                    xml =
 xml + "<NOMINEE_NAME>" + rs.getString("NOMINEE_NAME") + "</NOMINEE_NAME>";
                    xml =
 xml + "<NOMINEE_ADDRESS1>" + rs.getString("NOMINEE_ADDRESS1") +
   "</NOMINEE_ADDRESS1>";
                    xml =
 xml + "<NOMINEE_ADDRESS2>" + rs.getString("NOMINEE_ADDRESS2") +
   "</NOMINEE_ADDRESS2>";
                    xml =
 xml + "<NOMINEE_ADDRESS3>" + rs.getString("NOMINEE_ADDRESS3") +
   "</NOMINEE_ADDRESS3>";
                    xml =
 xml + "<RELATIONSHIP_ID>" + rs.getString("rel_desc") + "</RELATIONSHIP_ID>";
                    xml =
 xml + "<NOMINEE_DOB>" + rs.getString("DOB") + "</NOMINEE_DOB>";
                    xml =
 xml + "<PERCENTAGE_SHARE>" + rs.getFloat("PERCENTAGE_SHARE") +
   "</PERCENTAGE_SHARE>";
                    xml =
 xml + "<NOMINEE_PINCODE>" + rs.getInt("NOMINEE_PINCODE") +
   "</NOMINEE_PINCODE>";
                    xml =
 xml + "<NOMINEE_AGE>" + rs.getInt("NOMINEE_AGE") + "</NOMINEE_AGE>";

                    xml = xml + "</servicedata>";
                    System.out.println("nominee" + xml);
                }

                sql =
 "select sum(PERCENTAGE_SHARE) as sumofper from HRM_EMP_NOMINATION_DETAILS where Employee_id=? and fund_id=?";
                ps = con.prepareStatement(sql);
                ps.setInt(1, strcode);
                ps.setString(2, fund_id);
                rs = ps.executeQuery();
                if (rs.next()) {
                    sum = rs.getFloat("sumofper");
                    System.out.println("summmmmmmmmmmmmmmmmmmmmmmm" + sum);
                }
                xml =
 xml + "<total>" + rs.getFloat("sumofper") + "</total>"; // System.out.println(xml);
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


        out.println(xml);
        System.out.println(xml);
    }

}
