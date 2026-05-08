package Servlets.HR.HR1.EmployeeMaster.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.text.*;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;


public class HrmTransJoinServ extends HttpServlet {
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
        String currentoffice = "", optjoin = "";
        int currentofficecode = 0;
        //java.sql.Date strDOJ=null;
        String xml = "";
        String strCommand = "";
        String empstatus = "";

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


            strDOJ = request.getParameter("txtDOJ");

            //Date Conversion for Date
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


            strDesign = request.getParameter("comDesign");
            System.out.println("comDesign...." + strDesign);


            strpostcount = request.getParameter("comPostTow");
            System.out.println("comPostTow....." + strpostcount);

            strrem = request.getParameter("txtRemarks");
            System.out.println("txtRemarks...." + strrem);

            System.out.println(request.getQueryString());
            optjoin = request.getParameter("optjoin");
            System.out.println("optjoin...." + optjoin);

            if (optjoin != null) //&& optjoin.equalsIgnoreCase("S"))
            {
                currentofficecode =
                        Integer.parseInt(request.getParameter("currentoffice"));
                System.out.println("currentoffice...." + currentofficecode);
            }

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

            int wing = 0;
            System.out.println(request.getParameter("wing"));
            if (request.getParameter("wing") != null) {

                wing = Integer.parseInt(request.getParameter("wing"));

            }
            System.out.println("Wing =" + wing);

            try {

                System.out.println("Inside Add" + strOffcode);
                System.out.println("Inside Add" + Year1);
                System.out.println("Inside Add" + strJR);
                System.out.println("Inside Add" + strEmpId);
                System.out.println("Inside Add" + f);
                System.out.println("Inside Add" + strNoon);
                System.out.println("Inside Add" + strDesign);
                System.out.println("Inside Add" + strpostcount);
                System.out.println("Inside Add" + strrem);

                ps =
  con.prepareStatement("select EMPLOYEE_ID from HRM_EMP_JOIN_REPORTS where EMPLOYEE_ID=? and (PROCESS_FLOW_STATUS_ID='CR' or PROCESS_FLOW_STATUS_ID='MD')");
                ps.setInt(1, strEmpId);
                rs = ps.executeQuery();
                if (!rs.next()) {

                    System.out.println();
                    // "POST_COUNTED_ID,REMARKS,PROCESS_FLOW_STATUS_ID,COMPLETED_DATE,EMP_PRE_STATUS,DATE_EFFECTIVE_FROM_SESSION )" +
                        ps =
                            con.prepareStatement("INSERT INTO HRM_EMP_JOIN_REPORTS" +
                                                 "(OFFICE_ID,JOINING_YEAR,JOINING_REPORT_ID,EMPLOYEE_ID,DATE_OF_JOINING,FN_OR_AN,DESIGNATION_ID," +
                                                 "POST_COUNTED_ID,REMARKS,PROCESS_FLOW_STATUS_ID,EMP_PRE_STATUS,JOINED_SUBDIVISION,SUBDIVISION_OFFICE_ID,UPDATED_BY_USER_ID,UPDATED_DATE,OFFICE_WING_SINO )" +
                                                 "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                    ps.setInt(1, strOffcode);
                    ps.setInt(2, Year1);
                    ps.setInt(3, strJR);
                    ps.setInt(4, strEmpId);
                    ps.setDate(5, f);
                    ps.setString(6, strNoon);
                    ps.setString(7, strDesign);
                    ps.setString(8, strpostcount);
                    ps.setString(9, strrem);
                    ps.setString(10, "CR");
                    // ps.setDate(11,cdt);
                    ps.setString(11, empstatus);
                    ps.setString(12, optjoin);
                    ps.setInt(13, currentofficecode);

                    ps.setString(14, updatedby);
                    ps.setTimestamp(15, ts);

                    ps.setInt(16, wing);

                    // ps.setString(13,compsession);
                    ps.executeUpdate();
                    System.out.println("ok");
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
  con.prepareStatement("SELECT MAX(JOINING_REPORT_ID) AS b FROM HRM_EMP_JOIN_REPORTS where OFFICE_ID=? and JOINING_YEAR=?");
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
                        if (rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("WKG")) {
                            xml = xml + "<flag>failure3_1</flag>";
                        } else if (rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("SAN")) {
                            xml = xml + "<flag>failure3_2</flag>";
                        } else if (rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("DIS")) {
                            xml = xml + "<flag>failure3_3</flag>";
                        } else if (rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("VRS")) {
                            xml = xml + "<flag>failure3_4</flag>";
                        } else if (rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("DPT")) {
                            xml = xml + "<flag>failure3_5</flag>";
                        } else if (rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("DPN")) {
                            xml = xml + "<flag>failure3_6</flag>";
                        } else if (rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("STU")) {
                            xml = xml + "<flag>failure3_7</flag>";
                        } else if (rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("STT")) {
                            xml = xml + "<flag>failure3_8</flag>";
                        }

                        else if (rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("DTH")) {
                            xml = xml + "<flag>failure3_9</flag>";
                        } else if (rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("ABR")) {
                            xml = xml + "<flag>failure3_10</flag>";
                        } else {
                            rs.close();
                            ps.close();
                            ps =
  con.prepareStatement("SELECT EMPLOYEE_ID FROM HRM_EMP_JOIN_REPORTS WHERE EMPLOYEE_ID=? and (PROCESS_FLOW_STATUS_ID='CR' or PROCESS_FLOW_STATUS_ID='MD')");
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
