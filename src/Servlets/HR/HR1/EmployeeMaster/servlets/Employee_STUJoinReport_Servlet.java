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

public class Employee_STUJoinReport_Servlet extends HttpServlet {
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

            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection


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


        if (strCommand.equalsIgnoreCase("Delete")) {
            xml = "<response><command>Delete</command>";

            int strEmpName = 0;
            int JYear = 0;
            int OffId = 0;
            int JoinId = 0;

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
                    "Update HRM_EMP_DPN_JOIN_REPORTS set PROCESS_FLOW_STATUS_ID='DL' where EMPLOYEE_ID=? and JOINING_REPORT_ID=? and OFFICE_ID=? and JOINING_YEAR=?";

                ps = connection.prepareStatement(sql);

                ps.setInt(1, strEmpName);
                ps.setInt(2, JoinId);
                ps.setInt(3, OffId);
                ps.setInt(4, JYear);
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

                        System.out.println("emp name1" + strEmpName);
                        String sql =
                            "SELECT A.EMPLOYEE_NAME ||decode(a.EMPLOYEE_INITIAL,null,' ','.'||a.EMPLOYEE_INITIAL) as  EMPLOYEE_NAME ,A.GPF_NO,A.DATE_OF_BIRTH,B.JOINING_REPORT_ID, " +
                            "  B.STUDIES_ACTUAL_JOIN_DATE, B.SESSION_AN_FN,B.SR_CONTROLLING_OFFICE_ID, " +
                            "  B.STUDIES_JOIN_REMARKS,B.INSTITUTION_NAME,B.INSTITUTION_LOCATION,B.COURSE_NAME FROM HRM_MST_EMPLOYEES A " +
                            "  INNER JOIN HRM_EMP_STU_JOIN_REPORTS B ON B.EMPLOYEE_ID=A.EMPLOYEE_ID " +
                            "  WHERE A.EMPLOYEE_ID=? and (B.PROCESS_FLOW_STATUS_ID='CR' or B.PROCESS_FLOW_STATUS_ID='MD') ";
                        ps = connection.prepareStatement(sql);
                        ps.setInt(1, Integer.parseInt(strEmpName));
                        ResultSet rs = ps.executeQuery();

                        int i = 0;
                        String strDob = "";
                        while (rs.next()) {
                            System.out.println("emp name2" + strEmpName);

                            String strEName = rs.getString("Employee_Name");
                            int strEmpGpf = rs.getInt("GPF_NO");

                            int JoinId = rs.getInt("JOINING_REPORT_ID");
                            String strNoon = rs.getString("SESSION_AN_FN");
                            System.out.println("here is ok1");
                            String strRemarks =
                                rs.getString("STUDIES_JOIN_REMARKS");

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

                            Date strjdt =
                                rs.getDate("STUDIES_ACTUAL_JOIN_DATE");
                            if (strjdt == null) {
                                strJoindt = "0";
                            } else {
                                String[] sd1;
                                sd1 =
 rs.getDate("STUDIES_ACTUAL_JOIN_DATE").toString().split("-");
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


                            String inst_name = "", inst_location =
                                "", course_name = "";
                            if (rs.getString("INSTITUTION_NAME") != null) {
                                inst_name = rs.getString("INSTITUTION_NAME");
                            } else {
                                inst_name = "null";
                            }
                            System.out.println("here is ok");

                            if (rs.getString("INSTITUTION_LOCATION") != null) {
                                inst_location =
                                        rs.getString("INSTITUTION_LOCATION");
                            } else {
                                inst_location = "null";
                            }

                            if (rs.getString("COURSE_NAME") != null) {
                                course_name = rs.getString("COURSE_NAME");
                            } else {
                                course_name = "null";
                            }

                            xml =
 xml + "<inst_name>" + inst_name + "</inst_name><inst_location>" +
   inst_location + "</inst_location><course_name>" + course_name +
   "</course_name>";


                            int sr_ctrl_office_id =
                                rs.getInt("SR_CONTROLLING_OFFICE_ID");
                            xml =
 xml + "<sr_ctrl_office_id>" + sr_ctrl_office_id + "</sr_ctrl_office_id>";

                            try {
                                /***** 16-08-2006  **/
                                // ps=connection.prepareStatement("select max(date_to) as maxtodate from HRM_EMP_SERVICE_DATA where EMPLOYEE_ID=?");
                                ps =
  connection.prepareStatement("select DATE_EFFECTIVE_FROM as maxtodate from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?");
                                ps.setInt(1, Integer.parseInt(strEmpName));
                                rs = ps.executeQuery();
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
                                xml =
 xml + "<maxtodate>" + maxtodate + "</maxtodate>";

                            } catch (Exception se) {
                                System.out.println("error in serv grp" + se);
                            }

                            /*******/

                            // }

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
            String txtInstName = "", txtInstLocation = "", txtCourseName = "";
            String sr_cntrl_offid = "";
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

                    txtInstName = request.getParameter("txtInstName");
                    txtInstLocation = request.getParameter("txtInstLocation");
                    txtCourseName = request.getParameter("txtCourseName");


                    sr_cntrl_offid = request.getParameter("sr_cntrl_offid");
                    System.out.println("sr_cntrl_offid...." + sr_cntrl_offid);
                } catch (Exception e) {
                    System.out.println("exce **** " + e);
                }

                int srctrloffid = 0;

                try {
                    srctrloffid = Integer.parseInt(sr_cntrl_offid);
                } catch (Exception e) {
                    System.out.println(e);
                }

                // String sql="Update HRM_EMP_DPN_JOIN_REPORTS set FN_OR_AN=?,DESIGNATION_ID=?,POST_COUNTED_ID=?,REMARKS=?,PROCESS_FLOW_STATUS_ID=? where EMPLOYEE_ID=? and JOINING_REPORT_ID=? and OFFICE_ID=? and JOINING_YEAR=?";
                String sql =
                    "Update HRM_EMP_STU_JOIN_REPORTS set SESSION_AN_FN=?,STUDIES_JOIN_REMARKS=?,PROCESS_FLOW_STATUS_ID=?, " +
                    " INSTITUTION_NAME=?,INSTITUTION_LOCATION=?,COURSE_NAME=?,SR_CONTROLLING_OFFICE_ID=? " +
                    " where EMPLOYEE_ID=? and JOINING_REPORT_ID=? and OFFICE_ID=? and JOINING_YEAR=?";

                ps = connection.prepareStatement(sql);
                ps.setString(1, radVal);
                ps.setString(2, strRemarks);
                ps.setString(3, "MD");

                ps.setString(4, txtInstName);
                ps.setString(5, txtInstLocation);
                ps.setString(6, txtCourseName);
                ps.setInt(7, srctrloffid);

                ps.setInt(8, strEmpName);
                ps.setInt(9, JoinId);
                ps.setInt(10, OffId);
                ps.setInt(11, JYear);
                ps.executeUpdate();
                xml = xml + "<flag>success</flag>";
                ps.close();
            } catch (Exception e) {
                System.out.println("exception in the insertion" + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }


        System.out.println("xml is : " + xml);
        pw.write(xml);
        pw.flush();
        pw.close();


    }
}
