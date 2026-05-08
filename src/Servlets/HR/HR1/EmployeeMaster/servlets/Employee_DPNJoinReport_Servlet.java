package Servlets.HR.HR1.EmployeeMaster.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.text.SimpleDateFormat;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class Employee_DPNJoinReport_Servlet extends HttpServlet {
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

            ConnectionString =
                    strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
                    ":" + strsid.trim();

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

        System.out.println("command..." + strCommand);

        if (strCommand.equalsIgnoreCase("Delete")) {
            xml = "<response><command>Delete</command>";

            int strEmpName = 0;
            int JYear = 0;
            int OffId = 0;
            int JoinId = 0;


            session = request.getSession(false);
            String updatedby = (String)session.getAttribute("UserId");
            long l = System.currentTimeMillis();
            java.sql.Timestamp ts = new java.sql.Timestamp(l);

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

                //String sql="delete from HRM_EMP_DPN_JOIN_REPORTS  where EMPLOYEE_ID=? and JOINING_REPORT_ID=? and OFFICE_ID=? and JOINING_YEAR=?";
                String sql =
                    "Update HRM_EMP_DPN_JOIN_REPORTS set PROCESS_FLOW_STATUS_ID='DL',UPDATED_BY_USER_ID=?,UPDATED_DATE=? where EMPLOYEE_ID=? and JOINING_REPORT_ID=? and OFFICE_ID=? and JOINING_YEAR=?";

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
        } else if (strCommand.equalsIgnoreCase("Load")) {

            String sxml = "<response><command>Load</command>";
            String strJoindt = "", strCompdt = "", empstatus =
                "", compsession = "";
            String strEmpName = request.getParameter("EName");
            System.out.println("emp name" + strEmpName);
            try {

                ps =
  connection.prepareStatement("SELECT EMPLOYEE_ID FROM HRM_MST_EMPLOYEES WHERE EMPLOYEE_ID=?");
                ps.setInt(1, Integer.parseInt(strEmpName));
                results = ps.executeQuery();
                if (!results.next()) {
                    xml = sxml + "<flag>failure</flag>";
                } else {
                    ps =
  connection.prepareStatement("SELECT EMPLOYEE_ID FROM HRM_EMP_CURRENT_POSTING WHERE EMPLOYEE_ID=?");
                    ps.setInt(1, Integer.parseInt(strEmpName));
                    results = ps.executeQuery();
                    if (!results.next()) {
                        xml = sxml + "<flag>failure1</flag>";
                    } else {
                        results.close();
                        ps.close();

                        System.out.println("emp name" + strEmpName);
                        System.out.println("test1");
                        String sql =
                            "SELECT A.EMPLOYEE_NAME ||decode(a.EMPLOYEE_INITIAL,null,' ','.'||a.EMPLOYEE_INITIAL) as  EMPLOYEE_NAME ,A.GPF_NO,A.DATE_OF_BIRTH,B.JOINING_REPORT_ID, " +
                            " B.DATE_OF_JOINING, B.FN_OR_AN,B.OTHER_DEPT_ID,B.OTHER_DEPT_OFFICE_ID,B.SR_CONTROLLING_OFFICE_ID, " +
                            " B.REMARKS FROM HRM_MST_EMPLOYEES A  " +
                            " INNER JOIN HRM_EMP_DPN_JOIN_REPORTS B ON B.EMPLOYEE_ID=A.EMPLOYEE_ID " +
                            " WHERE A.EMPLOYEE_ID=? and (B.PROCESS_FLOW_STATUS_ID='CR' or B.PROCESS_FLOW_STATUS_ID='MD') ";

                        ps = connection.prepareStatement(sql);
                        ps.setInt(1, Integer.parseInt(strEmpName));
                        ResultSet rs = ps.executeQuery();
                        System.out.println("test2");
                        int i = 0;
                        String strDob = "";
                        while (rs.next()) {
                            System.out.println("emp name3" + strEmpName);

                            String strEName = rs.getString("Employee_Name");
                            int strEmpGpf = rs.getInt("GPF_NO");

                            int JoinId = rs.getInt("JOINING_REPORT_ID");
                            String strNoon = rs.getString("FN_OR_AN");

                            //int DesigId=rs.getInt("DESIGNATION_ID");
                            //int PostId=rs.getInt("POST_COUNTED_ID");

                            String strRemarks = rs.getString("REMARKS");

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


                            xml =
 xml + "<Emp_Id>" + strEmpName + "</Emp_Id>" + "<EmpName>" + strEName +
   "</EmpName><EmpGpf>" + strEmpGpf + "</EmpGpf>";
                            xml =
 xml + "<Dtofbirth>" + strDob + "</Dtofbirth>" + "<JoinId>" + JoinId +
   "</JoinId>";
                            xml =
 xml + "<Noon>" + strNoon + "</Noon>"; //"+"<DesigId>"+DesigId+"</DesigId>";
                            xml =
 xml + "<JDate>" + strJoindt + "</JDate>"; //<PostId>"+PostId+"</PostId>";
                            xml =
 xml + "<Remarks>" + strRemarks + "</Remarks>";
                            //  xml=xml+"<workingstatus>"+empstatus+"</workingstatus><completeddate>"+strCompdt+"</completeddate>";


                            String other_dept_id =
                                rs.getString("OTHER_DEPT_ID");
                            int other_office_id =
                                rs.getInt("OTHER_DEPT_OFFICE_ID");
                            xml =
 xml + "<other_dept_id>" + other_dept_id + "</other_dept_id><other_office_id>" +
   other_office_id + "</other_office_id>";
                            System.out.println("test3.5");
                            int sr_ctrl_office_id =
                                rs.getInt("SR_CONTROLLING_OFFICE_ID");
                            xml =
 xml + "<sr_ctrl_office_id>" + sr_ctrl_office_id + "</sr_ctrl_office_id>";
                            // System.out.println("dest" +DesigId);
                            try {

                                System.out.println("test3");
                                /***** 16-08-2006  **/
                                // ps=connection.prepareStatement("select max(date_to) as maxtodate from HRM_EMP_SERVICE_DATA where EMPLOYEE_ID=?");
                                ps =
  connection.prepareStatement("select DATE_EFFECTIVE_FROM as maxtodate from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?");
                                ps.setInt(1, Integer.parseInt(strEmpName));
                                rs = ps.executeQuery();
                                System.out.println("test4");
                                String maxtodate = "";
                                if (rs.next()) {
                                    if (rs.getDate("maxtodate") != null) {
                                        maxtodate =
                                                new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("maxtodate"));
                                        System.out.println("max to date :" +
                                                           rs.getDate("maxtodate"));
                                    } else {

                                        maxtodate = "empty";
                                    }
                                }
                                System.out.println("test5");
                                xml =
 xml + "<maxtodate>" + maxtodate + "</maxtodate>";

                                //ps=connection.prepareStatement("select d.DESIGNATION,c.DATE_EFFECTIVE_FROM ,f.OTHER_DEPT_OFFICE_NAME,g.OTHER_DEPT_NAME from HRM_EMP_CURRENT_POSTING c,HRM_MST_DESIGNATIONS d,HRM_MST_OTHER_DEPT_OFFICES f,HRM_MST_OTHER_DEPTS g where g.OTHER_DEPT_ID=c.DEPARTMENT_ID and f.OTHER_DEPT_OFFICE_ID=c.OFFICE_ID and f.OTHER_DEPT_ID=c.DEPARTMENT_ID  and  c.DESIGNATION_ID=d.DESIGNATION_ID and      C.EMPLOYEE_ID=? ");
                                System.out.println("test7");
                                ps =
  connection.prepareStatement("select  OTHER_DEPT_OFFICE_NAME from  HRM_MST_OTHER_DEPT_OFFICES where OTHER_DEPT_ID=? and OTHER_DEPT_OFFICE_ID	=? ");
                                ps.setString(1, other_dept_id);
                                ps.setInt(2, other_office_id);
                                //ps.setInt(2,offid);
                                rs = ps.executeQuery();
                                System.out.println("test6");
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

                                    xml =
 xml + "<otherdeptoffice>" + otherdeptoffice + "</otherdeptoffice>";

                                }

                                ps =
  connection.prepareStatement("select f.OTHER_DEPT_OFFICE_ID,f.OTHER_DEPT_OFFICE_NAME " +
                              " from HRM_MST_OTHER_DEPT_OFFICES f where f.OTHER_DEPT_ID=?");

                                ps.setString(1, other_dept_id);
                                rs = ps.executeQuery();
                                while (rs.next()) {
                                    xml =
 xml + "<option><id>" + rs.getInt("OTHER_DEPT_OFFICE_ID") + "</id><name>" +
   rs.getString("OTHER_DEPT_OFFICE_NAME") + "</name></option>";
                                }


                                // }
                            } catch (Exception se) {
                                System.out.println("error in serv grp" + se);
                            }
                        }
                        System.out.println("value of i:" + i);
                        if (i == 0) {

                            xml = sxml + "<flag>failure2</flag>" + xml;
                        } else {
                            // System.out.println("EmpStatus in deputation form:"+empstatus);
                            // if(empstatus!=null && !empstatus.equalsIgnoreCase("DPT"))
                            //     xml=sxml+"<flag>failure3</flag>";
                            // else
                            xml = sxml + "<flag>success</flag>" + xml;
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Exception " + e);
                xml = sxml + "<flag>failure</flag>";
            }
            //}
            xml = xml + "</response>";

        }


        else if (strCommand.equalsIgnoreCase("Update")) {

            xml = "<response><command>Update</command>";

            int strEmpName = 0;
            int strDesigId = 0;
            int PostId = 0;
            String strRemarks = "", empstatus = "", compsession = "";
            int JYear = 0;
            int OffId = 0;
            int JoinId = 0;
            String radVal = "";
            java.util.Date d = null;
            java.sql.Date cdt = null;
            String txtDepOffId = "", txtDepId = "";
            String sr_cntrl_offid = "";

            session = request.getSession(false);
            String updatedby = (String)session.getAttribute("UserId");
            long l = System.currentTimeMillis();
            java.sql.Timestamp ts = new java.sql.Timestamp(l);

            try {
                try {
                    strEmpName =
                            Integer.parseInt(request.getParameter("txtEmpId"));
                    OffId = Integer.parseInt(request.getParameter("txtOffId"));
                    //strDesigId =Integer.parseInt(request.getParameter("cmbdes"));
                    //PostId = Integer.parseInt(request.getParameter("comPostTow"));
                    JYear = Integer.parseInt(request.getParameter("txtDOJ"));
                    JoinId = Integer.parseInt(request.getParameter("JoinId"));
                    radVal = request.getParameter("radFNAN");
                    strRemarks = request.getParameter("txtRemarks");
                    empstatus = request.getParameter("empstatus");
                    System.out.println("empstatus..." + empstatus);

                    System.out.println("query:" + request.getQueryString());
                    txtDepId = request.getParameter("txtDepId");
                    System.out.println("txtDepId...." + txtDepId);

                    txtDepOffId = request.getParameter("txtDepOffId");
                    System.out.println("txtDepOffId...." + txtDepOffId);

                    sr_cntrl_offid = request.getParameter("sr_cntrl_offid");
                    System.out.println("sr_cntrl_offid...." + sr_cntrl_offid);

                } catch (Exception e) {
                    System.out.println("exce **** " + e);
                }

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


                // String sql="Update HRM_EMP_DPN_JOIN_REPORTS set FN_OR_AN=?,DESIGNATION_ID=?,POST_COUNTED_ID=?,REMARKS=?,PROCESS_FLOW_STATUS_ID=? where EMPLOYEE_ID=? and JOINING_REPORT_ID=? and OFFICE_ID=? and JOINING_YEAR=?";
                String sql =
                    "Update HRM_EMP_DPN_JOIN_REPORTS set FN_OR_AN=?,REMARKS=?,PROCESS_FLOW_STATUS_ID=?," +
                    " OTHER_DEPT_ID=?,OTHER_DEPT_OFFICE_ID=?,SR_CONTROLLING_OFFICE_ID=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=?" +
                    " where EMPLOYEE_ID=? and JOINING_REPORT_ID=? and OFFICE_ID=? and JOINING_YEAR=?";
                System.out.println("sql is..." + sql);
                ps = connection.prepareStatement(sql);
                System.out.println("after execution");
                ps.setString(1, radVal);
                ps.setString(2, strRemarks);
                ps.setString(3, "MD");


                ps.setString(4, txtDepId);
                ps.setInt(5, ooffid);
                ps.setInt(6, srctrloffid);

                ps.setString(7, updatedby);
                ps.setTimestamp(8, ts);

                ps.setInt(9, strEmpName);
                ps.setInt(10, JoinId);
                ps.setInt(11, OffId);
                ps.setInt(12, JYear);
                ps.executeUpdate();
                xml = xml + "<flag>success</flag>";
                ps.close();
            } catch (Exception e) {
                System.out.println("exception in the insertion" + e);
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
                ps1 =
 connection.prepareStatement("select f.OTHER_DEPT_OFFICE_ID,f.OTHER_DEPT_OFFICE_NAME " +
                             " from HRM_MST_OTHER_DEPT_OFFICES f where f.OTHER_DEPT_ID=?");

                ps1.setString(1, dep_id);
                rs3 = ps1.executeQuery();
                while (rs3.next()) {
                    xml =
 xml + "<option><id>" + rs3.getInt("OTHER_DEPT_OFFICE_ID") + "</id><name>" +
   rs3.getString("OTHER_DEPT_OFFICE_NAME") + "</name></option>";
                }

                c++;
                if (c == 0) {
                    xml = xml + "<flag>failure</flag>";
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            xml = xml + "</response>";

        }


        System.out.println("xml is : " + xml);
        pw.write(xml);
        pw.flush();
        pw.close();


    }
}
