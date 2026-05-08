package Servlets.HR.HR1.EmployeeMaster.servlets;

import Servlets.Security.classes.UserProfile;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.*;
import java.sql.Date;

import java.text.SimpleDateFormat;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class Create_Relieval_ReportServ extends HttpServlet {
    private String CONTENT_TYPE = "text/xml; charset=windows-1252";
    private static final String DOC_TYPE = null;


    public void init(ServletConfig config) throws ServletException {
        super.init(config);


    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        Connection con = null;
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
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
        } catch (Exception e) {
            System.out.println("Exception in openeing connection :" + e);
            //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

        }
        ResultSet rs = null, rs1 = null, rs2 = null;
        CallableStatement cs = null;
        PreparedStatement ps = null, ps1 = null, ps2 = null;
        String xml = "";
        HttpSession session = request.getSession(false);


        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        response.setHeader("Cache-Control", "no-cache");
        try {

            if (session == null) {
                xml =
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

        String strCommand = "";
        Calendar c;
        String ename = "", desig = "";
        int eid = 0;
        try {
            eid = Integer.parseInt(request.getParameter("txtEmployeeid"));
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println(eid);
        strCommand = request.getParameter("Command");
        System.out.println(strCommand);

        if (strCommand.equalsIgnoreCase("loademp")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            int offid = 0;
            offid = Integer.parseInt(request.getParameter("offid"));
            xml = "<response><command>loademp</command>";
            try {

                UserProfile up = null;
                up = (UserProfile)session.getAttribute("UserProfile");
                boolean flag = true;
                ps =
  con.prepareStatement("SELECT EMPLOYEE_ID FROM HRM_MST_EMPLOYEES WHERE EMPLOYEE_ID=?");
                ps.setInt(1, eid);
                rs = ps.executeQuery();
                if (!rs.next()) {
                    xml = xml + "<flag>failure</flag>";
                    flag = false;
                } else if (up.getEmployeeId() != eid) {
                    int OfficeId = 0;
                    String sql =
                        "select CONTROLLING_OFFICE_ID from HRM_EMP_CONTROLLING_OFFICE where employee_id=?";
                    ps = con.prepareStatement(sql);
                    ps.setInt(1, eid);
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
                            int offid1 = rs.getInt("OFFICE_ID");
                            if (offid1 != OfficeId) {
                                //response.sendRedirect(request.getContextPath()+"/org/Library/jsps/Messenger.jsp?message=" + "Can not see profile. Because Employee Id "+strEmpId+" is not under your Office!");
                                xml = xml + "<flag>failurea</flag>";
                                flag = false;
                            }
                        } else {
                            // response.sendRedirect(request.getContextPath()+"/org/Library/jsps/Messenger.jsp?message=" + "Current Posting is not available. Can not see the profile for "+strEmpId+"!");
                            xml = xml + "<flag>failureb</flag>";
                            flag = false;
                        }

                    } else {
                        xml = xml + "<flag>failurec</flag>";
                        flag = false;
                    }
                } else {

                    // xml=xml+"<flag>failured</flag>";
                    // flag=false;
                }

                if (flag) {
                    ps =
  con.prepareStatement("select EMPLOYEE_STATUS_ID from HRM_EMP_CURRENT_POSTING where  EMPLOYEE_ID=? ");
                    ps.setInt(1, eid);
                    rs = ps.executeQuery();
                    if (!rs.next()) {
                        xml = xml + "<flag>failure1</flag>";
                    } else {
                        if (rs.getString("EMPLOYEE_STATUS_ID") != null &&
                            rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("DPN")) {

                            ps =
  con.prepareStatement("select EMPLOYEE_ID from HRM_EMP_DPN_COMPLETION where  EMPLOYEE_ID=? and (PROCESS_FLOW_STATUS_ID='CR' or PROCESS_FLOW_STATUS_ID='MD')");
                            ps.setInt(1, eid);
                            rs = ps.executeQuery();
                            if (rs.next()) {
                                xml = xml + "<flag>failure2</flag>";
                            } else {

                                // ps=con.prepareStatement("select e.EMPLOYEE_ID,e.EMPLOYEE_NAME,d.DESIGNATION,c.DATE_EFFECTIVE_FROM ,f.OTHER_DEPT_OFFICE_NAME,g.OTHER_DEPT_NAME from HRM_MST_EMPLOYEES e,HRM_EMP_CURRENT_POSTING c,HRM_MST_DESIGNATIONS d,HRM_MST_OTHER_DEPT_OFFICES f,HRM_MST_OTHER_DEPTS g where g.OTHER_DEPT_ID=c.DEPARTMENT_ID and f.OTHER_DEPT_OFFICE_ID=c.OFFICE_ID and f.OTHER_DEPT_ID=c.DEPARTMENT_ID  and  c.DESIGNATION_ID=d.DESIGNATION_ID and e.EMPLOYEE_ID=c.EMPLOYEE_ID AND e.EMPLOYEE_ID=? ");
                                ps =
  con.prepareStatement("select e.EMPLOYEE_ID,e.EMPLOYEE_NAME ||decode(e.EMPLOYEE_INITIAL,null,' ','.'||e.EMPLOYEE_INITIAL) as  EMPLOYEE_NAME ,c.DATE_EFFECTIVE_FROM ,f.OTHER_DEPT_OFFICE_NAME,g.OTHER_DEPT_NAME from HRM_MST_EMPLOYEES e," +
                       " HRM_EMP_CURRENT_POSTING c,HRM_MST_OTHER_DEPT_OFFICES f,HRM_MST_OTHER_DEPTS g where g.OTHER_DEPT_ID=c.DEPARTMENT_ID and f.OTHER_DEPT_OFFICE_ID=c.OFFICE_ID and f.OTHER_DEPT_ID=c.DEPARTMENT_ID  and   e.EMPLOYEE_ID=c.EMPLOYEE_ID AND e.EMPLOYEE_ID=? ");
                                ps.setInt(1, eid);
                                //ps.setInt(2,offid);
                                rs = ps.executeQuery();
                                if (rs.next()) {


                                    xml =
 xml + "<flag>success</flag><eid>" + eid + "</eid><ename>" +
   rs.getString("EMPLOYEE_NAME") + "</ename>";

                                    String otherdeptoffice =
                                        "", otherdeptname = "";
                                    if (rs.getString("OTHER_DEPT_OFFICE_NAME") !=
                                        null) {
                                        otherdeptoffice =
                                                rs.getString("OTHER_DEPT_OFFICE_NAME");
                                    }
                                    //System.out.println("here is ok");

                                    if (rs.getString("OTHER_DEPT_NAME") !=
                                        null) {
                                        otherdeptname =
                                                rs.getString("OTHER_DEPT_NAME");
                                    }

                                    xml =
 xml + "<otherdeptname>" + otherdeptname +
   "</otherdeptname><otherdeptoffice>" + otherdeptoffice +
   "</otherdeptoffice>";

                                    /***************  16-08-2006    ***********************/
                                    ps =
  con.prepareStatement("select DATE_EFFECTIVE_FROM,DATE_EFFECTIVE_FROM_SESSION  from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?");
                                    ps.setInt(1, eid);
                                    rs = ps.executeQuery();
                                    String maxfromdate = "";
                                    String maxsession = "";
                                    if (rs.next()) {
                                        if (rs.getDate("DATE_EFFECTIVE_FROM") !=
                                            null) {
                                            maxfromdate =
                                                    new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("DATE_EFFECTIVE_FROM"));
                                            System.out.println("max from date :" +
                                                               rs.getDate("DATE_EFFECTIVE_FROM"));
                                        }

                                        if (rs.getString("DATE_EFFECTIVE_FROM_SESSION") !=
                                            null) {
                                            maxsession =
                                                    rs.getString("DATE_EFFECTIVE_FROM_SESSION");
                                        } else {
                                            maxsession = "FN";
                                        }
                                    }
                                    xml =
 xml + "<maxfromdate>" + maxfromdate + "</maxfromdate><maxsession>" +
   maxsession + "</maxsession>";


                                    /********************************************************/

                                } else
                                    xml = xml + "<flag>failure</flag>";

                            }
                        } else {
                            xml = xml + "<flag>failure3</flag>";
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("catch..HERE.in load emp." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            out.println(xml);

        } else if (strCommand.equalsIgnoreCase("office")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            xml = "<response><command>office</command>";
            try {
                int oid = 0;
                String oname = "";
                try {
                    oid = Integer.parseInt(request.getParameter("oid"));
                } catch (Exception e) {
                    System.out.println(e);
                }
                ps2 =
 con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=?");
                ps2.setInt(1, oid);
                rs2 = ps2.executeQuery();
                if (rs2.next())
                    xml =
 xml + "<flag>success</flag><oid>" + oid + "</oid><oname>" +
   rs2.getString("OFFICE_NAME") + "</oname>";
                else
                    xml = xml + "<flag>failure</flag><oid>" + oid + "</oid>";
                ps2.close();
                rs2.close();
            } catch (Exception e) {
                System.out.println("catch..HERE.in load office." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            out.println(xml);
        } else if (strCommand.equalsIgnoreCase("desig")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            xml = "<response><command>desig</command>";
            try {
                int desigid = 0;
                String designame = "";
                try {
                    desigid =
                            Integer.parseInt(request.getParameter("desigid"));
                } catch (Exception e) {
                    System.out.println(e);
                }
                ps2 =
 con.prepareStatement("select DESIGNATION from HRM_MST_DESIGNATIONS where DESIGNATION_ID=?");
                ps2.setInt(1, desigid);
                rs2 = ps2.executeQuery();
                if (rs2.next())
                    xml =
 xml + "<flag>success</flag><designame>" + rs2.getString("DESIGNATION") +
   "</designame>";
                else
                    xml = xml + "<flag>failure</flag>";
                ps2.close();
                rs2.close();
            } catch (Exception e) {
                System.out.println("catch..HERE.in load office." + e);
                xml = xml + "<flag>failure</flag>";

            }
            xml = xml + "</response>";
            out.println(xml);
        } else if (strCommand.equalsIgnoreCase("dept")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            xml = "<response><command>dept</command>";
            try {
                int oid = 0;
                String oname = "", deptid = "";
                try {
                    oid = Integer.parseInt(request.getParameter("oid"));
                } catch (Exception e) {
                    System.out.println(e);
                }
                deptid = request.getParameter("deptid");
                ps2 =
 con.prepareStatement("select OTHER_DEPT_NAME from HRM_MST_OTHER_DEPTS where OTHER_DEPT_ID=?");
                ps2.setString(1, deptid);
                rs2 = ps2.executeQuery();
                if (rs2.next())
                    xml =
 xml + "<flag>success</flag><dname>" + rs2.getString("OTHER_DEPT_NAME") +
   "</dname>";
                else
                    xml = xml + "<flag>failure</flag><err>did</err>";
                ps2.close();
                rs2.close();
                ps2 =
 con.prepareStatement("select OTHER_DEPT_OFFICE_NAME from HRM_MST_OTHER_DEPT_OFFICES where OTHER_DEPT_ID=? and OTHER_DEPT_OFFICE_ID=?");
                ps2.setString(1, deptid);
                ps2.setInt(2, oid);
                rs2 = ps2.executeQuery();
                if (rs2.next())
                    xml =
 xml + "<oname>" + rs2.getString("OTHER_DEPT_OFFICE_NAME") + "</oname>";
                else
                    xml = xml + "<flag>failure</flag><err1>ofid</err1>";
                ps2.close();
                rs2.close();

            } catch (Exception e) {
                System.out.println("catch..HERE.in load office." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            out.println(xml);
        } else if (strCommand.equalsIgnoreCase("Add")) {
            String CONTENT_TYPE = "text/html; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            xml = "<response><command>Add</command>";


            session = request.getSession(false);
            String updatedby = (String)session.getAttribute("UserId");
            long l = System.currentTimeMillis();
            Timestamp ts = new Timestamp(l);


            int txtOffId = 0, txtEmployeeid = 0, txtRel_SLNO = 0;
            Date txtDORelieval = null;
            String rad_DORelieval = "", cmbReason = "", txtRemarks =
                "", Proc_Status = "";
            System.out.println(xml);
            Proc_Status = request.getParameter("cmbStatus");
            System.out.println(Proc_Status);
            // strCommand=request.getParameter("from here");
            txtOffId = Integer.parseInt(request.getParameter("txtOffId"));
            txtEmployeeid =
                    Integer.parseInt(request.getParameter("txtEmployeeid"));

            String[] sd = request.getParameter("txtDORelieval").split("/");
            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
            java.util.Date d = c.getTime();
            txtDORelieval = new Date(d.getTime());

            rad_DORelieval = request.getParameter("rad_DORelieval");

            //cmbReason=request.getParameter("cmbReason");
            txtRemarks = request.getParameter("txtRemarks");
            System.out.println(txtOffId);
            System.out.println(txtEmployeeid);
            System.out.println(txtRel_SLNO);
            System.out.println(txtDORelieval);
            //System.out.println(cmbReason);
            System.out.println("from " +
                               request.getParameter("txtDORelieval"));
            try {
                ps =
  con.prepareStatement("select EMPLOYEE_ID from HRM_EMP_DPN_COMPLETION where  EMPLOYEE_ID=? and (PROCESS_FLOW_STATUS_ID='CR' or PROCESS_FLOW_STATUS_ID='MD')");
                ps.setInt(1, txtEmployeeid);
                rs = ps.executeQuery();
                System.out.println("step1");
                if (rs.next()) {
                    xml = xml + "<flag>failure2</flag>";
                    System.out.println("Failure 2");
                    sendMessage(response,
                                "This Employee already has an unfrezeed Record. \n Insertinga new Reilval is not possible",
                                "ok");
                } else {

                    // System.out.println(cmbReason);
                    String txtD_ODep_Id = "";
                    int i = 0;
                    /* try{txtD_ODep_Id=request.getParameter("txtD_ODep_Id");}catch(Exception e){System.out.println(e);}
                 int txtD_ODep_OffId=0;
                 try{txtD_ODep_OffId=Integer.parseInt(request.getParameter("txtD_ODep_OffId"));}catch(Exception e){System.out.println(e);}
                 String txtD_Period=request.getParameter("txtD_Period");
                 Date txtD_Date=null;
                String s=null;
                s=request.getParameter("txtD_Date");
                System.out.println("s value"+s);
                if(!s.equals(""))
                {
                 String sdD[]=request.getParameter("txtD_Date").split("/");
                 c=new GregorianCalendar(Integer.parseInt(sdD[2]),Integer.parseInt(sdD[1])-1,Integer.parseInt(sdD[0]));
                 d=c.getTime();
                 txtD_Date=new Date(d.getTime());
                }
                */
                    try {
                        System.out.println("inside query");


                        cs =
  con.prepareCall("{call HRM_EMP_DPNRELIEVAL_DPN_PROC(?,?,?,?,?,?,?,?,?,?,?)}");
                        cs.setInt(1, txtOffId);
                        cs.setInt(2, txtRel_SLNO);
                        cs.setInt(3, txtEmployeeid);
                        cs.setDate(4, txtDORelieval);
                        cs.setString(5, rad_DORelieval);
                        cs.setString(6, txtRemarks);
                        cs.setString(8, "insert");
                        cs.setString(9, Proc_Status);
                        cs.setString(10, updatedby);
                        cs.setTimestamp(11, ts);


                        cs.registerOutParameter(2, java.sql.Types.NUMERIC);
                        cs.registerOutParameter(7, java.sql.Types.NUMERIC);
                        cs.execute();
                        System.out.println("hello");
                        txtRel_SLNO = cs.getInt(2);
                        int errcode = cs.getInt(7);
                        System.out.println("SQLCODE:::" + errcode);
                        if (errcode != 0) {
                            xml = xml + "<flag>failure</flag>";
                        } else {
                            xml =
 xml + "<flag>success</flag><relieNo>" + txtRel_SLNO + "</relieNo>";
                            sendMessage(response,
                                        " The Relieval Serial Number " +
                                        txtRel_SLNO +
                                        " has been created successfully.",
                                        "ok");
                        }
                    } catch (Exception e) {
                        System.out.println("insert exception  :" + e);
                        sendMessage(response,
                                    "Exception in insertion due to." + e,
                                    "ok");
                        xml = xml + "<flag>failure</flag>";
                    }


                }
            } catch (Exception e) {
                xml = xml + "<flag>failure</flag>";
                System.out.println("Error :" + e);
            }
            xml = xml + "</response>";

        }
        System.out.println(xml);
        //out.println(xml);
    }

    private void sendMessage(HttpServletResponse response, String msg,
                             String bType) {
        try {
            String url =
                "org/Library/jsps/Messenger.jsp?message=" + msg + "&button=" +
                bType;
            response.sendRedirect(url);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
