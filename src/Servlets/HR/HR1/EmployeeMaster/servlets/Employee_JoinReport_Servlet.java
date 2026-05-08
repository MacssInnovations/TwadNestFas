package Servlets.HR.HR1.EmployeeMaster.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.*;

import java.text.SimpleDateFormat;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class Employee_JoinReport_Servlet extends HttpServlet {
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

            session = request.getSession(false);
            String updatedby = (String)session.getAttribute("UserId");
            long l = System.currentTimeMillis();
            Timestamp ts = new Timestamp(l);


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

                //String sql="delete from HRM_EMP_JOIN_REPORTS  where EMPLOYEE_ID=? and JOINING_REPORT_ID=? and OFFICE_ID=? and JOINING_YEAR=?";
                String sql =
                    "Update HRM_EMP_JOIN_REPORTS set PROCESS_FLOW_STATUS_ID='DL',UPDATED_BY_USER_ID=?,UPDATED_DATE=? where EMPLOYEE_ID=? and JOINING_REPORT_ID=? and OFFICE_ID=? and JOINING_YEAR=?";

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
            System.out.println("Loading");
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
                        String sql =
                            "SELECT A.EMPLOYEE_NAME ||decode(a.EMPLOYEE_INITIAL,null,' ','.'||a.EMPLOYEE_INITIAL) as  EMPLOYEE_NAME,A.GPF_NO,A.DATE_OF_BIRTH,B.JOINING_REPORT_ID, " +
                            " B.DATE_OF_JOINING, B.FN_OR_AN, B.DESIGNATION_ID, B.POST_COUNTED_ID, " +
                            " B.REMARKS,B.COMPLETED_DATE,B.EMP_PRE_STATUS,B.DATE_EFFECTIVE_FROM_SESSION,B.JOINED_SUBDIVISION,B.SUBDIVISION_OFFICE_ID,B.OFFICE_WING_SINO FROM HRM_MST_EMPLOYEES A " +
                            " INNER JOIN HRM_EMP_JOIN_REPORTS B ON B.EMPLOYEE_ID=A.EMPLOYEE_ID " +
                            " WHERE A.EMPLOYEE_ID=? and (B.PROCESS_FLOW_STATUS_ID='CR' or B.PROCESS_FLOW_STATUS_ID='MD') ";

                        ps = connection.prepareStatement(sql);
                        ps.setInt(1, Integer.parseInt(strEmpName));
                        ResultSet rs = ps.executeQuery();

                        int i = 0;
                        String strDob = "";
                        while (rs.next()) {
                            System.out.println("emp name" + strEmpName);

                            String strEName = rs.getString("Employee_Name");
                            int strEmpGpf = rs.getInt("GPF_NO");

                            int JoinId = rs.getInt("JOINING_REPORT_ID");
                            String strNoon = rs.getString("FN_OR_AN");

                            int DesigId = rs.getInt("DESIGNATION_ID");
                            int PostId = rs.getInt("POST_COUNTED_ID");


                            String strRemarks = rs.getString("REMARKS");
                            int wing = rs.getInt("OFFICE_WING_SINO");
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

                            Date strcdt = rs.getDate("COMPLETED_DATE");
                            if (strcdt == null) {
                                strCompdt = "0";
                            } else {
                                String[] sd1;
                                sd1 =
 rs.getDate("COMPLETED_DATE").toString().split("-");
                                strCompdt =
                                        sd1[2] + "/" + sd1[1] + "/" + sd1[0];
                            }
                            empstatus = rs.getString("EMP_PRE_STATUS");
                            compsession =
                                    rs.getString("DATE_EFFECTIVE_FROM_SESSION");

                            xml =
 xml + "<Emp_Id>" + strEmpName + "</Emp_Id>" + "<EmpName>" + strEName +
   "</EmpName><EmpGpf>" + strEmpGpf + "</EmpGpf>";
                            xml =
 xml + "<Dtofbirth>" + strDob + "</Dtofbirth>" + "<JoinId>" + JoinId +
   "</JoinId>";
                            xml =
 xml + "<Noon>" + strNoon + "</Noon>" + "<DesigId>" + DesigId + "</DesigId>";
                            xml =
 xml + "<JDate>" + strJoindt + "</JDate><PostId>" + PostId +
   "</PostId><Remarks>" + strRemarks + "</Remarks>";
                            xml =
 xml + "<workingstatus>" + empstatus + "</workingstatus><completeddate>" +
   strCompdt + "</completeddate>";
                            xml =
 xml + "<compsession>" + compsession + "</compsession>";

                            xml = xml + "<wing>" + wing + "</wing>";

                            String optjoin =
                                rs.getString("JOINED_SUBDIVISION");
                            int subdivoffid =
                                rs.getInt("SUBDIVISION_OFFICE_ID");

                            xml = xml + "<optjoin>" + optjoin + "</optjoin>";
                            xml =
 xml + "<subdivoffid>" + subdivoffid + "</subdivoffid>";


                            try {
                                System.out.println("dest" + DesigId);
                                String sql3 =
                                    "select a.service_group_id from hrm_mst_service_group a inner join hrm_mst_designations b on b.service_group_id=a.service_group_id where b.designation_id=?";

                                ps1 = connection.prepareStatement(sql3);
                                ps1.setInt(1, DesigId);
                                rs3 = ps1.executeQuery();
                                while (rs3.next()) {

                                    int servgrp =
                                        rs3.getInt("SERVICE_GROUP_ID");
                                    System.out.println("serv" + servgrp);
                                    xml =
 xml + "<ServGroup>" + servgrp + "</ServGroup>";


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

                                    /*******/

                                }
                            } catch (Exception se) {
                                System.out.println("error in serv grp" + se);
                            }
                        }
                        System.out.println("value of i:" + i);
                        if (i == 0) {

                            /*

                          try
                          {
                              String sql2="select * from hrm_mst_employees where employee_id=?";
                              ps2=connection.prepareStatement(sql2);
                              ps2.setInt(1,Integer.parseInt(strEmpName));

                               rs4=ps2.executeQuery();
                              int j=0;
                              while(rs4.next())
                              {
                                 j++;
                                  String strEmpPref=rs4.getString("Employee_Prefix");
                                  String strEmpInit=rs4.getString("Employee_Initial");
                                    strEmpName=rs4.getString("Employee_Name");
                                   int strEmpGpf=rs4.getInt("GPF_NO");
                                  Date strdt=rs4.getDate("DATE_OF_BIRTH");
                                  if(strdt==null) {
                                      strDob="0";
                                  }
                                  else
                                  {
                                  String[] sd;
                                  sd=rs4.getDate("DATE_OF_BIRTH").toString().split("-");
                                  strDob=sd[2]+"/"+sd[1]+"/"+sd[0];
                                  }
                                  xml=sxml+"<flag>NoRecord</flag>";
                                  xml=xml+"<EmpPref>"+strEmpPref+"</EmpPref><EmpInit>"+strEmpInit+"</EmpInit><EmpName>"+strEmpName+"</EmpName><EmpGpf>"+strEmpGpf+"</EmpGpf><Dtofbirth>"+strDob+"</Dtofbirth>";
                              }
                              if(j==0)
                              {
                                  xml=sxml+"<flag>NoValue</flag>";
                              }

                          }
                          catch(Exception ae){System.out.println("Error in the second query" + ae);}
                          */
                            xml = sxml + "<flag>failure2</flag>";
                        } else {
                            System.out.println("EmpStatus in twad joining form:" +
                                               empstatus + "|");
                            if (empstatus != null &&
                                empstatus.equalsIgnoreCase("DPT"))
                                xml = sxml + "<flag>failure3</flag>";
                            else
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
            String optjoin = "";
            int currentofficecode = 0;

            session = request.getSession(false);
            String updatedby = (String)session.getAttribute("UserId");
            long l = System.currentTimeMillis();
            Timestamp ts = new Timestamp(l);
            int wing = 0;
            try {
                try {
                    strEmpName =
                            Integer.parseInt(request.getParameter("txtEmpId"));
                    OffId = Integer.parseInt(request.getParameter("txtOffId"));
                    strDesigId =
                            Integer.parseInt(request.getParameter("cmbdes"));
                    PostId =
                            Integer.parseInt(request.getParameter("comPostTow"));
                    JYear = Integer.parseInt(request.getParameter("txtDOJ"));
                    JoinId = Integer.parseInt(request.getParameter("JoinId"));
                    radVal = request.getParameter("radFNAN");
                    strRemarks = request.getParameter("txtRemarks");

                    empstatus = request.getParameter("empstatus");
                    System.out.println("empstatus..." + empstatus);

                    System.out.println(request.getQueryString());
                    optjoin = request.getParameter("optjoin");
                    System.out.println("optjoin...." + optjoin);

                    if (optjoin != null) //&& optjoin.equalsIgnoreCase("S"))
                    {
                        currentofficecode =
                                Integer.parseInt(request.getParameter("currentoffice"));
                        System.out.println("currentoffice...." +
                                           currentofficecode);
                    }

                    System.out.println(request.getParameter("wing"));
                    if (request.getParameter("wing") != null) {
                        wing = Integer.parseInt(request.getParameter("wing"));
                    }
                    System.out.println("Wing =" + wing);


                    /* if(empstatus!=null && !empstatus.equalsIgnoreCase("WKG")) {
                              String strDOC=request.getParameter("compdate");
                              SimpleDateFormat  dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                              d = dateFormat.parse(strDOC.trim());
                              dateFormat.applyPattern("yyyy-MM-dd");
                              String dateString = dateFormat.format(d);
                              cdt = java.sql.Date.valueOf(dateString);
                          }*/

                    // compsession=request.getParameter("compsession");
                    //System.out.println("compsession...."+compsession);

                } catch (Exception e) {
                    System.out.println("exce **** " + e);
                }


                String sql =
                    "Update HRM_EMP_JOIN_REPORTS set FN_OR_AN=?,DESIGNATION_ID=?,POST_COUNTED_ID=?,REMARKS=?,PROCESS_FLOW_STATUS_ID=?,JOINED_SUBDIVISION=?,SUBDIVISION_OFFICE_ID=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=?,OFFICE_WING_SINO=? where EMPLOYEE_ID=? and JOINING_REPORT_ID=? and OFFICE_ID=? and JOINING_YEAR=?";

                ps = connection.prepareStatement(sql);
                ps.setString(1, radVal);
                ps.setInt(2, strDesigId);
                ps.setInt(3, PostId);
                ps.setString(4, strRemarks);
                ps.setString(5, "MD");
                //  ps.setDate(6,cdt);
                //  ps.setString(7,compsession);
                ps.setString(6, optjoin);
                ps.setInt(7, currentofficecode);

                ps.setString(8, updatedby);
                ps.setTimestamp(9, ts);

                ps.setInt(10, wing);

                ps.setInt(11, strEmpName);
                ps.setInt(12, JoinId);
                ps.setInt(13, OffId);
                ps.setInt(14, JYear);

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
