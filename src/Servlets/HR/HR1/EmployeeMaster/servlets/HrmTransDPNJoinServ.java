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

public class HrmTransDPNJoinServ extends HttpServlet {
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


        /*var OffCode=document.HrmTransJoinForm.txtOffId.value;
           var OffName=document.HrmTransJoinForm.txtOffName.value;
           var OffAddr=document.HrmTransJoinForm.txtOffAdd.value;
           var EmpId=document.HrmTransJoinForm.txtEmpId.value;
           var EmpName=document.HrmTransJoinForm.txtEmpName.value;
           var DOB=document.HrmTransJoinForm.txtDOB.value;
           var GPFNO=document.HrmTransJoinForm.txtGPFNO.value;
           var JRId=document.HrmTransJoinForm.txtJRId.value;
           var DOJ=document.HrmTransJoinForm.txtDOJ.value;
           var design=document.HrmTransJoinForm.Comdesig.value;
           var postcount=document.HrmTransJoinForm.ComposCount.value;
           var rem=document.HrmTransJoinForm.txtRemarks.value;*/
        // java.sql.Date t = java.sql.Date.valueOf( fromDate );

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
        String txtDepOffId = "", txtDepId = "";
        String sr_cntrl_offid = "";
        int Year1 = 0;
        int b = 0;
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");


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

            System.out.println("query:" + request.getQueryString());
            txtDepId = request.getParameter("txtDepId");
            System.out.println("txtDepId...." + txtDepId);

            txtDepOffId = request.getParameter("txtDepOffId");
            System.out.println("txtDepOffId...." + txtDepOffId);

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


            /*  if(empstatus!=null && !empstatus.equalsIgnoreCase("WKG")) {
                     String strDOC=request.getParameter("compdate");
                     dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                     d = dateFormat.parse(strDOC.trim());
                     dateFormat.applyPattern("yyyy-MM-dd");
                     dateString = dateFormat.format(d);
                     cdt = java.sql.Date.valueOf(dateString);
                }*/

            //f=java.sql.Date.valueOf(strDOJ);
            System.out.println("txtDOJ....." + f);
            //f = Date.valueOf(strDOJ);

            //  strDesign=request.getParameter("comDesign");
            //  System.out.println("comDesign...."+strDesign);

            //  strpostcount=request.getParameter("comPostTow");
            //  System.out.println("comPostTow....."+strpostcount);

            strrem = request.getParameter("txtRemarks");
            System.out.println("txtRemarks...." + strrem);

            //  compsession=request.getParameter("compsession");
            //  System.out.println("compsession...."+compsession);


        }

        catch (Exception e) {
            System.out.println("Exception in second assigning..." + e);
        }


        if (strCommand.equalsIgnoreCase("Add")) {
            xml = "<response><command>Add</command>";
            //String sql="insert into TEST_STATE values(?,?)";
            // int id=0;

            session = request.getSession(false);
            String updatedby = (String)session.getAttribute("UserId");
            long l = System.currentTimeMillis();
            java.sql.Timestamp ts = new java.sql.Timestamp(l);

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
                int ooffid = 0, srctrloffid = 0;
                try {
                    ooffid = Integer.parseInt(txtDepOffId);
                } catch (Exception e) {
                    System.out.println(e);
                }

                try {
                    srctrloffid = Integer.parseInt(sr_cntrl_offid);
                } catch (Exception e) {
                    System.out.println(e);
                }

                ps =
  con.prepareStatement("select EMPLOYEE_ID from HRM_EMP_DPN_JOIN_REPORTS where EMPLOYEE_ID=? and (PROCESS_FLOW_STATUS_ID='CR' or PROCESS_FLOW_STATUS_ID='MD')");
                ps.setInt(1, strEmpId);
                rs = ps.executeQuery();
                if (!rs.next()) {

                    System.out.println("");
                    //"DESIGNATION_ID,POST_COUNTED_ID," +
                        ps =
                            con.prepareStatement("INSERT INTO HRM_EMP_DPN_JOIN_REPORTS" +
                                                 "(OFFICE_ID,JOINING_YEAR,JOINING_REPORT_ID,EMPLOYEE_ID,DATE_OF_JOINING,FN_OR_AN," +
                                                 "REMARKS,PROCESS_FLOW_STATUS_ID,OTHER_DEPT_ID,OTHER_DEPT_OFFICE_ID,SR_CONTROLLING_OFFICE_ID,UPDATED_BY_USER_ID,UPDATED_DATE )" +
                                                 "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)");
                    ps.setInt(1, strOffcode);
                    ps.setInt(2, Year1);
                    ps.setInt(3, strJR);
                    ps.setInt(4, strEmpId);
                    ps.setDate(5, f);
                    ps.setString(6, strNoon);
                    ps.setString(7, strrem);
                    ps.setString(8, "CR");
                    ps.setString(9, txtDepId);
                    ps.setInt(10, ooffid);
                    ps.setInt(11, srctrloffid);

                    ps.setString(12, updatedby);
                    ps.setTimestamp(13, ts);

                    // ps.setDate(11,cdt);
                    // ps.setString(9,empstatus);
                    // ps.setString(13,compsession);
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

        /*cs=con.prepareCall("{call HRM_TRANS_JOIN_PROC(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}") ;

                     cs.setInt(1,strOffcode);
                     cs.setString(2,strOffName);
                     cs.setString(3,strOffAddr);
                     cs.setInt(4,strEmpId);
                     cs.setString(5,strEmpName);
                     cs.setString(6,strDOB);
                     cs.setInt(7,strGPFNO);
                    cs.setInt(8,strJRId);
                    cs.setString(9,strDOJ);
                    cs.setString(10,strDesign);
                    cs.setString(11,strpostcount);
                    cs.setString(12,strrem);
                    cs.setString(13,"insert");
                    cs.registerOutParameter(14,java.sql.Types.NUMERIC);
                    cs.registerOutParameter(15,java.sql.Types.NUMERIC);
                    cs.execute();
                    int errcode=cs.getInt(14);
                    id=cs.getInt(1);
                    System.out.println(id);
                    System.out.println("SQLCODE:::"+errcode);
                    if(errcode!=0)
                    {
                     xml=xml+"<num>"+id+"</num><duplicate>no</duplicate><flag>failure</flag>";
                     }
                     else
                     xml=xml+"<num>"+id+"</num><duplicate>no</duplicate><flag>success</flag>";
                     }
                     catch(Exception e)
                     {
                     System.out.println("insert exception fg :"+e);
                     xml=xml+"<num>"+id+"</num><duplicate>no</duplicate><flag>failure</flag>";
                     }

                     xml=xml+"</response>";
                     }*/
        /*if(strCommand.equalsIgnoreCase("inc")) {
                  System.out.println("hai");
                      String sxml="<response><command>inc</command>";
                      try {
                          ps=con.prepareStatement("SELECT MAX(D) FROM HRM_TRANS_JOIN_REPORT");

                          rs=ps.executeQuery();
                          //System.out.println("rs.."+rs.getInt(1));
                          int i=0;
                          while(rs.next()) {
                          i=rs.getInt(1);

                          }
                          if(i==0)
                          {
                          i=1;
                          System.out.println("i...."+i);
                          xml=sxml+"<flag>first</flag><i>"+i+"</i>";
                          }
                          else
                          {
                              i=i+1;
                              System.out.println("i.."+i);
                              xml=sxml+"<flag>incremented</flag><i>"+i+"</i>";
                          }
                      }
                      catch(Exception e) {
                          System.out.println("catch...."+e);
                          xml=xml+"<flag>failure</flag>";
                      }
                      xml=xml+"</response>";
                      }*/


        else if (strCommand.equalsIgnoreCase("dispDesign")) {
            System.out.println("Insiden Year " + Year1);
            String sxml = "<response><command>dispDesign</command>";

            try {
                ps =
  con.prepareStatement("SELECT MAX(JOINING_REPORT_ID) AS b FROM HRM_EMP_DPN_JOIN_REPORTS where OFFICE_ID=? and JOINING_YEAR=?");
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
  con.prepareStatement("SELECT EMPLOYEE_NAME ||decode(EMPLOYEE_INITIAL,null,' ','.'||EMPLOYEE_INITIAL) as  EMPLOYEE_NAME,DATE_OF_BIRTH,GPF_NO FROM HRM_MST_EMPLOYEES WHERE EMPLOYEE_ID=?");

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

                ////////////////////////
                /*
                 PreparedStatement cs=con.prepareStatement("select DATE_TO,DATE_TO_SESSION from HRM_EMP_SERVICE_DATA where EMPLOYEE_ID=? and DATE_TO=(select max(DATE_TO) from  HRM_EMP_SERVICE_DATA where EMPLOYEE_ID=? )" );
                 cs.setInt(1,strEmpId);
                 cs.setInt(2,strEmpId);
                 Calendar c=null;
                 rs=cs.executeQuery();
                 if(rs.next()) {
                       Date dt=rs.getDate("DATE_TO");
                       String ses=rs.getString("DATE_TO_SESSION");
                       String strdt=dt.toString();
                       System.out.println("Date is :"+strdt);
                       String[] sp=strdt.split("-");
                       System.out.println(sp[1]);
                       System.out.println("session:"+ses);
                       String nextses="";
                       if(ses.equalsIgnoreCase("AN"))
                       {
                           c=new GregorianCalendar(Integer.parseInt(sp[0]),Integer.parseInt(sp[1])-1,Integer.parseInt(sp[2])+1);
                           nextses="FN";
                       }
                       else
                       {
                           c=new GregorianCalendar(Integer.parseInt(sp[0]),Integer.parseInt(sp[1])-1,Integer.parseInt(sp[2]));
                           nextses="AN";
                       }
                     //java.util.Date d=c.getTime();
                     System.out.println("after conversion:"+new SimpleDateFormat("dd/MM/yyyy").format(c.getTime()));
                     String nextdate=new SimpleDateFormat("dd/MM/yyyy").format(c.getTime());
                     xml=xml+"<nextdate>"+nextdate+"</nextdate><nextsession>"+nextses+"</nextsession>";
                     xml=xml+"<flag>success</flag>";
                 }
                 */

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

                        else if (rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("STU")) {
                            xml = xml + "<flag>failure3_6</flag>";
                        }
                        /* else    if(rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("STT")) {
                                xml=xml+"<flag>failure3_7</flag>";
                        }*/
                        else if ((!rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("DPT")) &&
                                 (!rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("TRT"))) {

                            xml = xml + "<flag>failure3_5</flag>";
                        }

                        else {
                            rs.close();
                            ps.close();
                            ps =
  con.prepareStatement("SELECT EMPLOYEE_ID FROM HRM_EMP_DPN_JOIN_REPORTS WHERE EMPLOYEE_ID=? and (PROCESS_FLOW_STATUS_ID='CR' or PROCESS_FLOW_STATUS_ID='MD')");
                            ps.setInt(1, strEId);
                            rs = ps.executeQuery();
                            if (rs.next()) {
                                xml = xml + "<flag>failure2</flag>";
                                // System.out.println("hai");
                            } else {


                                rs.close();
                                ps.close();

                                ps =
  con.prepareStatement("SELECT EMPLOYEE_ID,EMPLOYEE_NAME ||decode(EMPLOYEE_INITIAL,null,' ','.'||EMPLOYEE_INITIAL) as  EMPLOYEE_NAME,DATE_OF_BIRTH,GPF_NO FROM HRM_MST_EMPLOYEES WHERE EMPLOYEE_ID=?");

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
                                    String other_dept_id = "";
                                    // ps=con.prepareStatement("select d.DESIGNATION,c.DATE_EFFECTIVE_FROM ,f.OTHER_DEPT_OFFICE_NAME,g.OTHER_DEPT_NAME from HRM_EMP_CURRENT_POSTING c,HRM_MST_DESIGNATIONS d,HRM_MST_OTHER_DEPT_OFFICES f,HRM_MST_OTHER_DEPTS g where g.OTHER_DEPT_ID=c.DEPARTMENT_ID and f.OTHER_DEPT_OFFICE_ID=c.OFFICE_ID and f.OTHER_DEPT_ID=c.DEPARTMENT_ID  and  c.DESIGNATION_ID=d.DESIGNATION_ID and      C.EMPLOYEE_ID=? ");
                                    ps =
  con.prepareStatement("select f.OTHER_DEPT_OFFICE_ID,f.OTHER_DEPT_OFFICE_NAME,g.OTHER_DEPT_ID,g.OTHER_DEPT_NAME " +
                       " from HRM_EMP_RELIEVAL_DPN c  left outer join HRM_MST_OTHER_DEPT_OFFICES f on f.OTHER_DEPT_OFFICE_ID=c.OTHER_DEPT_OFFICE_ID and f.OTHER_DEPT_ID=c.OTHER_DEPT_ID " +
                       " inner join HRM_MST_OTHER_DEPTS g on g.OTHER_DEPT_ID = c.OTHER_DEPT_ID and  c.EMPLOYEE_ID=? " +
                       " where c.RELIEVAL_SLNO = (select max(x.RELIEVAL_SLNO) from HRM_EMP_RELIEVAL_DETAILS x where  " +
                       " x.PROCESS_FLOW_STATUS_ID ='FR' and x.EMPLOYEE_ID=?)");
                                    ps.setInt(1, strEId);
                                    ps.setInt(2, strEId);
                                    //ps.setInt(2,offid);
                                    rs = ps.executeQuery();
                                    if (rs.next()) {

                                        String otherdeptoffice =
                                            "", otherdeptname = "";
                                        if (rs.getString("OTHER_DEPT_OFFICE_NAME") !=
                                            null) {
                                            otherdeptoffice =
                                                    rs.getString("OTHER_DEPT_OFFICE_NAME");
                                        } else {
                                            otherdeptoffice = "null";
                                        }
                                        //System.out.println("here is ok");

                                        if (rs.getString("OTHER_DEPT_NAME") !=
                                            null) {
                                            otherdeptname =
                                                    rs.getString("OTHER_DEPT_NAME");
                                        }

                                        other_dept_id =
                                                rs.getString("OTHER_DEPT_ID");
                                        int other_office_id =
                                            rs.getInt("OTHER_DEPT_OFFICE_ID");

                                        xml =
 xml + "<otherdeptname>" + otherdeptname +
   "</otherdeptname><otherdeptoffice>" + otherdeptoffice +
   "</otherdeptoffice>";
                                        xml =
 xml + "<other_dept_id>" + other_dept_id +
   "</other_dept_id><other_office_id>" + other_office_id +
   "</other_office_id>";

                                    }

                                    ps =
  con.prepareStatement("select f.OTHER_DEPT_OFFICE_ID,f.OTHER_DEPT_OFFICE_NAME " +
                       " from HRM_MST_OTHER_DEPT_OFFICES f where f.OTHER_DEPT_ID=?");

                                    ps.setString(1, other_dept_id);
                                    rs = ps.executeQuery();
                                    while (rs.next()) {
                                        xml =
 xml + "<option><id>" + rs.getInt("OTHER_DEPT_OFFICE_ID") + "</id><name>" +
   rs.getString("OTHER_DEPT_OFFICE_NAME") + "</name></option>";
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


        else if (strCommand.equalsIgnoreCase("depName")) {
            String dep_id = "";
            int c = 0;

            dep_id = request.getParameter("txtDepId");
            System.out.println("selected department id..." + dep_id);

            xml = "<response><command>depName</command><flag>success</flag>";

            try {
                ps =
  con.prepareStatement("select f.OTHER_DEPT_OFFICE_ID,f.OTHER_DEPT_OFFICE_NAME " +
                       " from HRM_MST_OTHER_DEPT_OFFICES f where f.OTHER_DEPT_ID=?");

                ps.setString(1, dep_id);
                rs = ps.executeQuery();
                while (rs.next()) {
                    xml =
 xml + "<option><id>" + rs.getInt("OTHER_DEPT_OFFICE_ID") + "</id><name>" +
   rs.getString("OTHER_DEPT_OFFICE_NAME") + "</name></option>";
                }

                c++;
                if (c == 0) {
                    xml = xml + "<flag>failure</flag>";
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            xml = xml + "</response>";

        } else if (strCommand.equalsIgnoreCase("LoadSROffice")) {
            int Office_Id = 0;
            Office_Id = Integer.parseInt(request.getParameter("OfficeId"));
            int found = 0;
            xml = "";
            int closed = 0, off_lev = 0;
            try {


                String deptid = "";
                deptid = request.getParameter("txtDept_Id");
                System.out.println("My dept id::" +
                                   request.getParameter("txtDept_Id"));
                if (deptid.equals("TWAD")) {
                    System.out.println("TWAD---->");
                    String sql =
                        "select a.office_status_id,a.office_id,a.office_name,a.Office_address1,a.office_address2,a.CITY_TOWN_NAME,a.DISTRICT_CODE,a.OFFICE_PHONE_NO,a.ADDL_PHONE_NOS,a.OFFICE_EMAIL_ID,a.ADDL_EMAIL_IDS,a.OFFICE_FAX_NO,a.ADDL_FAX_NOS,a.OFFICE_STD_CODE,d.DISTRICT_NAME,a.office_level_id from com_mst_offices a left outer join com_mst_districts d on d.DISTRICT_CODE=a.DISTRICT_CODE  where a.Office_Id=?";
                    PreparedStatement statement = con.prepareStatement(sql);
                    statement.setInt(1, Office_Id);
                    con.clearWarnings();

                    ResultSet results = statement.executeQuery();

                    xml = "<response><flag>success</flag>";
                    found = 0;
                    if (results.next()) {
                        if (results.getString("office_status_id").equals("CR")) {
                            closed = 1;

                        }
                        if ((results.getString("office_level_id").equals("HO")) ||
                            (results.getString("office_level_id").equals("RN")) ||
                            (results.getString("office_level_id").equals("CL")) ||
                            (results.getString("office_level_id").equals("DN"))) {
                            off_lev = 1;
                        }
                        xml =
 xml + "<id>" + results.getInt("OFFICE_ID") + "</id><name>" +
   results.getString("OFFICE_NAME") + "</name><officeAddress1>" +
   results.getString("OFFICE_ADDRESS1") + "</officeAddress1><officeAddress2>" +
   results.getString("OFFICE_ADDRESS2") + "</officeAddress2><officeAddress3>" +
   results.getString("CITY_TOWN_NAME") + "</officeAddress3><District>" +
   results.getInt("DISTRICT_CODE") + "</District><district_name>" +
   results.getString("DISTRICT_NAME") + "</district_name><PhoneNo>" +
   results.getString("OFFICE_PHONE_NO") + "</PhoneNo><AddPhone>" +
   results.getString("ADDL_PHONE_NOS") + "</AddPhone><EmailId>" +
   results.getString("OFFICE_EMAIL_ID") + "</EmailId><AddEmail>" +
   results.getString("ADDL_EMAIL_IDS") + "</AddEmail><FaxNo>" +
   results.getString("OFFICE_FAX_NO") + "</FaxNo><AddFaxNo>" +
   results.getString("ADDL_FAX_NOS") + "</AddFaxNo><StdCode>" +
   results.getString("OFFICE_STD_CODE") + "</StdCode><OfficeStatusId>" +
   results.getString("office_status_id") + "</OfficeStatusId>";
                        System.out.println("xml in while" + xml);


                        if (closed == 0) {
                            xml = xml + "<off_close>yes</off_close>";

                        } else
                            xml = xml + "<off_close>no</off_close>";

                        if (off_lev == 0) {
                            xml = xml + "<off_lev>invalid</off_lev>";

                        } else
                            xml = xml + "<off_lev>valid</off_lev>";
                        System.out.println("xml after else--->");
                    }
                }
                xml = xml + "</response>";
            }

            catch (Exception e1) {
                System.out.println("Exception 4 2nd try" + e1);
            }

        }
        System.out.println("xml is:" + xml);
        out.write(xml);
        out.flush();
        out.close();

    }


    //System.out.println("xml is:"+xml);
    //out.write(xml);
    //out.flush();
    //out.close();
}

