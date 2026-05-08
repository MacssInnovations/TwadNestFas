package Servlets.HR.HR1.EmployeeMaster.servlets;

import Servlets.Security.classes.UserProfile;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class Edit_DPNRelieval_serv extends HttpServlet {
    private String CONTENT_TYPE = "text/xml; charset=windows-1252";

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

        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        response.setHeader("Cache-Control", "no-cache");
        HttpSession session = request.getSession(false);
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
        int txtOffId = 0, txtEmployeeid = 0, txtRel_SLNO = 0;
        Date txtDORelieval = null;
        String rad_DORelieval = "", cmbReason = "", txtRemarks = "";
        eid = Integer.parseInt(request.getParameter("txtEmployeeid"));
        System.out.println(eid);
        strCommand = request.getParameter("Command");
        System.out.println("comm" + strCommand);
        try {
            txtOffId = Integer.parseInt(request.getParameter("txtOffId"));
        } catch (Exception e) {
        }

        if (strCommand.equalsIgnoreCase("loademp")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            int offid = 0;
            offid = Integer.parseInt(request.getParameter("offid"));
            System.out.println("emp load start");
            xml = "<response><command>loademp</command>";
            try {

                UserProfile up = null;
                up = (UserProfile)session.getAttribute("UserProfile");
                boolean flag = true;

                ps =
  con.prepareStatement("SELECT EMPLOYEE_ID FROM HRM_MST_EMPLOYEES WHERE EMPLOYEE_ID=?");
                ps.setInt(1, eid);
                rs = ps.executeQuery();
                System.out.println("load emp1");
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
                    System.out.println("load emp2");
                    if (rs.next()) {
                        OfficeId = rs.getInt("CONTROLLING_OFFICE_ID");
                    }

                    if (OfficeId != 0) {
                        sql =
 "select OFFICE_ID  from HRM_EMP_CURRENT_POSTING where employee_id=?";
                        ps = con.prepareStatement(sql);
                        ps.setInt(1, up.getEmployeeId());
                        rs = ps.executeQuery();
                        System.out.println("load emp3");
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
  con.prepareStatement("select EMPLOYEE_ID,EMPLOYEE_STATUS_ID from HRM_EMP_CURRENT_POSTING where  EMPLOYEE_ID=? ");
                    ps.setInt(1, eid);
                    rs = ps.executeQuery();
                    System.out.println("load emp4");
                    if (!rs.next()) {
                        xml = xml + "<flag>failure1</flag>";
                    } else {
                        if (rs.getString("EMPLOYEE_STATUS_ID") != null &&
                            rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("DPN")) {
                            ps =
  con.prepareStatement("select e.EMPLOYEE_ID,e.EMPLOYEE_NAME ||decode(e.EMPLOYEE_INITIAL,null,' ','.'||e.EMPLOYEE_INITIAL) as  EMPLOYEE_NAME ,c.OFFICE_ID from HRM_MST_EMPLOYEES e,HRM_EMP_CURRENT_POSTING c where  e.EMPLOYEE_ID=c.EMPLOYEE_ID AND e.EMPLOYEE_ID=?");
                            ps.setInt(1, eid);
                            // ps.setInt(2,offid);
                            rs = ps.executeQuery();
                            if (rs.next()) {
                                xml =
 xml + "<flag>success</flag><eid>" + eid + "</eid><ename>" +
   rs.getString("EMPLOYEE_NAME") + "</ename><offid>" + rs.getInt("OFFICE_ID") +
   "</offid>";
                                ps.close();
                                rs.close();

                                ///////////////////////////////////////////////////////////////////
                                System.out.println("here is ok relieval");
                                try {
                                    System.out.println("step1");
                                    //ps=con.prepareStatement("select OFFICE_ID,EMPLOYEE_ID,RELIEVAL_SLNO,DATE_OF_RELIEVAL ,RELIEVAL_FN_AN, RELIEVAL_REASON_ID, REMARKS from HRM_EMP_RELIEVAL_DETAILS where OFFICE_ID=? and EMPLOYEE_ID=? and RELIEVAL_SLNO=?");
                                    ps =
  con.prepareStatement("select OFFICE_ID,EMPLOYEE_ID,RELIEVAL_SLNO,DATE_OF_RELIEVAL ,RELIEVAL_FN_AN,  REMARKS,PROCESS_FLOW_STATUS_ID from HRM_EMP_DPN_COMPLETION where OFFICE_ID=? and EMPLOYEE_ID=? and (PROCESS_FLOW_STATUS_ID='CR' or PROCESS_FLOW_STATUS_ID='MD')");
                                    ps.setInt(1, offid);
                                    ps.setInt(2, eid);
                                    //ps.setInt(3,txtRel_SLNO);
                                    rs = ps.executeQuery();
                                    if (rs.next()) {
                                        //xml=xml+"<flag>success</flag>"+"<re_slno>"+rs.getInt("RELIEVAL_SLNO")+"</re_slno><dor>"+rs.getDate("DATE_OF_RELIEVAL")+"</dor><r_noon>"+rs.getString("RELIEVAL_FN_AN")+"</r_noon><r_rid>"+rs.getString("RELIEVAL_REASON_ID")+"</r_rid><remark>"+rs.getString("REMARKS")+"</remark>";
                                        xml =
 xml + "<re_slno>" + rs.getInt("RELIEVAL_SLNO") + "</re_slno><dor>" +
   rs.getDate("DATE_OF_RELIEVAL") + "</dor><r_noon>" +
   rs.getString("RELIEVAL_FN_AN") + "</r_noon><remark>" +
   rs.getString("REMARKS") + "</remark><pro_status>" +
   rs.getString("PROCESS_FLOW_STATUS_ID") + "</pro_status>";
                                        //  xml=xml+"<re_slno>"+rs.getInt("RELIEVAL_SLNO")+"</re_slno><dor>"+rs.getDate("DATE_OF_RELIEVAL")+"</dor><r_noon>"+rs.getString("RELIEVAL_FN_AN")+"</r_noon><r_rid>"+rs.getString("RELIEVAL_REASON_ID")+"</r_rid><remark>"+(rs.getString("REMARKS")==null?"null":rs.getString("REMARKS"))+"</remark>";


                                        txtRel_SLNO =
                                                rs.getInt("RELIEVAL_SLNO");
                                        // cmbReason=rs.getString("RELIEVAL_REASON_ID");
                                        ps.close();
                                        rs.close();
                                        System.out.println(":::cmbReson:::" +
                                                           cmbReason);
                                        System.out.println("step2");
                                        ps =
  con.prepareStatement("select c.DATE_EFFECTIVE_FROM ,f.OTHER_DEPT_OFFICE_NAME,g.OTHER_DEPT_NAME from HRM_EMP_CURRENT_POSTING c,HRM_MST_OTHER_DEPT_OFFICES f,HRM_MST_OTHER_DEPTS g where g.OTHER_DEPT_ID=c.DEPARTMENT_ID and f.OTHER_DEPT_OFFICE_ID=c.OFFICE_ID and f.OTHER_DEPT_ID=c.DEPARTMENT_ID  and    C.EMPLOYEE_ID=? ");
                                        ps.setInt(1, eid);
                                        //ps.setInt(2,offid);
                                        rs = ps.executeQuery();
                                        if (rs.next()) {

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

                                        }


                                        System.out.println("step3");
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


                                    } else {
                                        System.out.println("2nd im here");
                                        xml =
 "<response><command>loademp</command><flag>failure2</flag>";
                                    }
                                } catch (Exception e) {
                                    System.out.println("catch..HERE.in load emp." +
                                                       e);
                                    xml = xml + "<flag>failure2</flag>";
                                }
                                ///////////////////////////////////////////////////////////////////


                            }


                            else {
                                System.out.println("2nd im here");
                                xml =
 "<response><command>loademp</command><flag>failure</flag>";
                            }
                            System.out.println("im here");
                        } else {
                            //deputation checking
                            xml =
 "<response><command>loademp</command><flag>failure3</flag>";
                        }

                    }
                }
            } catch (Exception e) {
                System.out.println("catch..HERE.in load emp." + e);
                xml = xml + "<flag>failure</flag>";
            }


            xml = xml + "</response>";
            out.println(xml);
        } else if (strCommand.equalsIgnoreCase("Delete")) {

            session = request.getSession(false);
            String updatedby = (String)session.getAttribute("UserId");
            long l = System.currentTimeMillis();
            java.sql.Timestamp ts = new java.sql.Timestamp(l);

            try {

                txtRel_SLNO =
                        Integer.parseInt(request.getParameter("txtRel_SLNO"));
                txtOffId = Integer.parseInt(request.getParameter("txtOffId"));
                txtEmployeeid =
                        Integer.parseInt(request.getParameter("txtEmployeeid"));
                System.out.println("Office Id:" + txtOffId);
                System.out.println("Serial No:" + txtRel_SLNO);
                System.out.println("Employee Id:" + txtEmployeeid);
                //ps=con.prepareStatement("delete from HRM_EMP_RELIEVAL_DETAILS where OFFICE_ID=? and EMPLOYEE_ID=? and (PROCESS_FLOW_STATUS_ID='CR' or PROCESS_FLOW_STATUS_ID='MD') and RELIEVAL_SLNO=?");
                ps =
  con.prepareStatement("update HRM_EMP_DPN_COMPLETION set PROCESS_FLOW_STATUS_ID='DL',UPDATED_BY_USER_ID=?,UPDATED_DATE=?  where OFFICE_ID=? and EMPLOYEE_ID=?  and RELIEVAL_SLNO=?");

                ps.setString(1, updatedby);
                ps.setTimestamp(2, ts);

                ps.setInt(3, txtOffId);
                ps.setInt(4, txtEmployeeid);
                ps.setInt(5, txtRel_SLNO);
                ps.executeUpdate();
                ps.close();

                sendMessage(response, "Record is deleted successfully.", "ok");
            } catch (Exception e) {
                System.out.println("catch..in Delete." + e);
                sendMessage(response, "Exception in Delete due to." + e, "ok");

            }

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
        } else if (strCommand.equalsIgnoreCase("loadReason")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            try {
                txtRel_SLNO =
                        Integer.parseInt(request.getParameter("txtRel_SLNO"));
            } catch (Exception e) {
            }
            System.out.println("txt" + txtRel_SLNO);
            xml = "<response><command>loadReason</command>";
            try {
                //ps=con.prepareStatement("select OFFICE_ID,EMPLOYEE_ID,RELIEVAL_SLNO,DATE_OF_RELIEVAL ,RELIEVAL_FN_AN, RELIEVAL_REASON_ID, REMARKS from HRM_EMP_RELIEVAL_DETAILS where OFFICE_ID=? and EMPLOYEE_ID=? and RELIEVAL_SLNO=?");
                ps =
  con.prepareStatement("select OFFICE_ID,EMPLOYEE_ID,RELIEVAL_SLNO,DATE_OF_RELIEVAL ,RELIEVAL_FN_AN, RELIEVAL_REASON_ID, REMARKS,PROCESS_FLOW_STATUS_ID from HRM_EMP_RELIEVAL_DETAILS where OFFICE_ID=? and EMPLOYEE_ID=? and RELIEVAL_SLNO=?");
                ps.setInt(1, txtOffId);
                ps.setInt(2, eid);
                ps.setInt(3, txtRel_SLNO);
                rs = ps.executeQuery();
                if (rs.next()) {
                    //xml=xml+"<flag>success</flag>"+"<re_slno>"+rs.getInt("RELIEVAL_SLNO")+"</re_slno><dor>"+rs.getDate("DATE_OF_RELIEVAL")+"</dor><r_noon>"+rs.getString("RELIEVAL_FN_AN")+"</r_noon><r_rid>"+rs.getString("RELIEVAL_REASON_ID")+"</r_rid><remark>"+rs.getString("REMARKS")+"</remark>";
                    xml =
 xml + "<flag>success</flag>" + "<re_slno>" + rs.getInt("RELIEVAL_SLNO") +
   "</re_slno><dor>" + rs.getDate("DATE_OF_RELIEVAL") + "</dor><r_noon>" +
   rs.getString("RELIEVAL_FN_AN") + "</r_noon><r_rid>" +
   rs.getString("RELIEVAL_REASON_ID") + "</r_rid><remark>" +
   rs.getString("REMARKS") + "</remark><pro_status>" +
   rs.getString("PROCESS_FLOW_STATUS_ID") + "</pro_status>";
                    //  xml=xml+"<re_slno>"+rs.getInt("RELIEVAL_SLNO")+"</re_slno><dor>"+rs.getDate("DATE_OF_RELIEVAL")+"</dor><r_noon>"+rs.getString("RELIEVAL_FN_AN")+"</r_noon><r_rid>"+rs.getString("RELIEVAL_REASON_ID")+"</r_rid><remark>"+(rs.getString("REMARKS")==null?"null":rs.getString("REMARKS"))+"</remark>";


                    txtRel_SLNO = rs.getInt("RELIEVAL_SLNO");
                    cmbReason = rs.getString("RELIEVAL_REASON_ID");
                    ps.close();
                    rs.close();
                    if ((cmbReason).equalsIgnoreCase("PRO")) {
                        ps =
  con.prepareStatement("select  NEW_DESIGNATION_ID, OFFICE_TO_POSTED_ID,REPOSTING_REQD, PROMOTION_PROCEED_DATE, PROMOTION_PROCEED_NO  from HRM_EMP_RELIEVAL_PRO where OFFICE_ID=? and EMPLOYEE_ID=? and  RELIEVAL_SLNO=?");
                        ps.setInt(1, txtOffId);
                        ps.setInt(2, eid);
                        ps.setInt(3, txtRel_SLNO);
                        rs = ps.executeQuery();
                        if (rs.next()) {
                            xml =
 xml + "<post_oid>" + rs.getInt("OFFICE_TO_POSTED_ID") +
   "</post_oid><des_id>" + rs.getInt("NEW_DESIGNATION_ID") +
   "</des_id><repost_req>" + rs.getString("REPOSTING_REQD") +
   "</repost_req><t_pr_date>" + rs.getDate("PROMOTION_PROCEED_DATE") +
   "</t_pr_date><t_pr_no>" + rs.getString("PROMOTION_PROCEED_NO") +
   "</t_pr_no>";
                        }
                        int ofid = 0;
                        ofid = rs.getInt("OFFICE_TO_POSTED_ID");
                        ps2 =
 con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=?");
                        ps2.setInt(1, ofid);
                        rs2 = ps2.executeQuery();
                        if (rs2.next())
                            xml =
 xml + "<r_oname>" + rs2.getString("OFFICE_NAME") + "</r_oname>";
                        ps2.close();
                        rs2.close();
                        int des_id = 0;
                        des_id = rs.getInt("NEW_DESIGNATION_ID");
                        ps2 =
 con.prepareStatement("select DESIGNATION from HRM_MST_DESIGNATIONS where DESIGNATION_ID=?");
                        ps2.setInt(1, des_id);
                        rs2 = ps2.executeQuery();
                        if (rs2.next())
                            xml =
 xml + "<r_designame>" + rs2.getString("DESIGNATION") + "</r_designame>";
                        ps2.close();
                        rs2.close();
                    } else if ((cmbReason).equalsIgnoreCase("TRN")) {
                        ps =
  con.prepareStatement("select  TRANSFER_TO_OFFICE_ID,REPOSTING_REQD,TRANSFER_PROCEED_DATE,TRANSFER_PROCEED_NO  from HRM_EMP_RELIEVAL_TRN where OFFICE_ID=? and EMPLOYEE_ID=? and  RELIEVAL_SLNO=?");
                        ps.setInt(1, txtOffId);
                        ps.setInt(2, eid);
                        ps.setInt(3, txtRel_SLNO);
                        rs = ps.executeQuery();
                        if (rs.next()) {
                            xml =
 xml + "<t_oid>" + rs.getInt("TRANSFER_TO_OFFICE_ID") +
   "</t_oid><repost_req>" + rs.getString("REPOSTING_REQD") +
   "</repost_req><t_pr_date>" + rs.getDate("TRANSFER_PROCEED_DATE") +
   "</t_pr_date><t_pr_no>" + rs.getString("TRANSFER_PROCEED_NO") +
   "</t_pr_no>";
                        }
                        int ofid = 0;
                        ofid = rs.getInt("TRANSFER_TO_OFFICE_ID");
                        ps2 =
 con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=?");
                        ps2.setInt(1, ofid);
                        rs2 = ps2.executeQuery();
                        if (rs2.next())
                            xml =
 xml + "<r_oname>" + rs2.getString("OFFICE_NAME") + "</r_oname>";
                        ps2.close();
                        rs2.close();
                    } else if ((cmbReason).equalsIgnoreCase("DVN")) {
                        ps =
  con.prepareStatement("select  DIVERSION_TO_OFFICE_ID, DIVERSION_DATE from HRM_EMP_RELIEVAL_DVN where OFFICE_ID=? and EMPLOYEE_ID=? and  RELIEVAL_SLNO=?");
                        ps.setInt(1, txtOffId);
                        ps.setInt(2, eid);
                        ps.setInt(3, txtRel_SLNO);
                        rs = ps.executeQuery();
                        if (rs.next()) {
                            xml =
 xml + "<dvn_oid>" + rs.getInt("DIVERSION_TO_OFFICE_ID") +
   "</dvn_oid><dvn_date>" + rs.getDate("DIVERSION_DATE") + "</dvn_date>";
                        }
                        int ofid = 0;
                        ofid = rs.getInt("DIVERSION_TO_OFFICE_ID");
                        System.out.println(ofid);
                        if (ofid != 0) {
                            ps2 =
 con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=?");
                            ps2.setInt(1, ofid);
                            rs2 = ps2.executeQuery();
                            if (rs2.next())
                                xml =
 xml + "<r_oname>" + rs2.getString("OFFICE_NAME") + "</r_oname>";
                            ps2.close();
                            rs2.close();
                        } else
                            xml = xml + "<r_oname>null</r_oname>";

                    } else if ((cmbReason).equalsIgnoreCase("DPN")) {
                        ps =
  con.prepareStatement("select  OTHER_DEPT_ID, OTHER_DEPT_OFFICE_ID,DEPUTATION_PERIOD,DEPUTATION_DATE from HRM_EMP_RELIEVAL_DPN where OFFICE_ID=? and EMPLOYEE_ID=? and  RELIEVAL_SLNO=?");
                        ps.setInt(1, txtOffId);
                        ps.setInt(2, eid);
                        ps.setInt(3, txtRel_SLNO);
                        rs = ps.executeQuery();
                        if (rs.next()) {
                            xml =
 xml + "<od_id>" + rs.getString("OTHER_DEPT_ID") + "</od_id><od_oid>" +
   rs.getInt("OTHER_DEPT_OFFICE_ID") + "</od_oid><d_period>" +
   rs.getString("DEPUTATION_PERIOD") + "</d_period><d_date>" +
   rs.getDate("DEPUTATION_DATE") + "</d_date>";
                        }
                        String depid = "";
                        depid = rs.getString("OTHER_DEPT_ID");
                        ps2 =
 con.prepareStatement("select OTHER_DEPT_NAME from HRM_MST_OTHER_DEPTS where OTHER_DEPT_ID=?");
                        ps2.setString(1, depid);
                        rs2 = ps2.executeQuery();
                        if (rs2.next())
                            xml =
 xml + "<r_dname>" + rs2.getString("OTHER_DEPT_NAME") + "</r_dname>";
                        ps2.close();
                        rs2.close();
                        int dep_offid = 0;
                        dep_offid = rs.getInt("OTHER_DEPT_OFFICE_ID");
                        ps2 =
 con.prepareStatement("select OTHER_DEPT_OFFICE_NAME from HRM_MST_OTHER_DEPT_OFFICES where OTHER_DEPT_ID=? and OTHER_DEPT_OFFICE_ID=?");
                        ps2.setString(1, depid);
                        ps2.setInt(2, dep_offid);
                        rs2 = ps2.executeQuery();
                        if (rs2.next())
                            xml =
 xml + "<r_oname>" + rs2.getString("OTHER_DEPT_OFFICE_NAME") + "</r_oname>";
                        ps2.close();
                        rs2.close();
                    } else if ((cmbReason).equalsIgnoreCase("LLV")) {
                        ps =
  con.prepareStatement("select  LEAVE_TYPE_ID, PURPOSE,PERIOD_FROM,PERIOD_TO from HRM_EMP_RELIEVAL_LLV where OFFICE_ID=? and EMPLOYEE_ID=? and  RELIEVAL_SLNO=?");
                        ps.setInt(1, txtOffId);
                        ps.setInt(2, eid);
                        ps.setInt(3, txtRel_SLNO);
                        rs = ps.executeQuery();
                        if (rs.next()) {
                            xml =
 xml + "<l_tid>" + rs.getString("LEAVE_TYPE_ID") + "</l_tid><pur>" +
   rs.getString("PURPOSE") + "</pur><l_pfrom>" + rs.getDate("PERIOD_FROM") +
   "</l_pfrom><l_pto>" + rs.getDate("PERIOD_TO") + "</l_pto>";
                        }
                    }
                } else {
                    System.out.println("2nd im here");
                    xml =
 "<response><command>loadReason</command><flag>failure</flag>";
                }
            } catch (Exception e) {
                System.out.println("catch..HERE.in load emp." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            out.println(xml);
        }

        else if (strCommand.equalsIgnoreCase("Validate_loadReason")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            try {
                txtRel_SLNO =
                        Integer.parseInt(request.getParameter("txtRel_SLNO"));
            } catch (Exception e) {
            }
            System.out.println("txt" + txtRel_SLNO);
            xml = "<response><command>Validate_loadReason</command>";
            try {
                ps =
  con.prepareStatement("select OFFICE_ID,EMPLOYEE_ID,RELIEVAL_SLNO,DATE_OF_RELIEVAL ,RELIEVAL_FN_AN, RELIEVAL_REASON_ID, REMARKS,PROCESS_FLOW_STATUS_ID from HRM_EMP_RELIEVAL_DETAILS where OFFICE_ID=? and EMPLOYEE_ID=? and RELIEVAL_SLNO=?");
                ps.setInt(1, txtOffId);
                ps.setInt(2, eid);
                ps.setInt(3, txtRel_SLNO);
                rs = ps.executeQuery();
                if (rs.next()) {
                    xml =
 xml + "<flag>success</flag>" + "<re_slno>" + rs.getInt("RELIEVAL_SLNO") +
   "</re_slno><dor>" + rs.getDate("DATE_OF_RELIEVAL") + "</dor><r_noon>" +
   rs.getString("RELIEVAL_FN_AN") + "</r_noon><r_rid>" +
   rs.getString("RELIEVAL_REASON_ID") + "</r_rid><remark>" +
   rs.getString("REMARKS") + "</remark><pro_status>" +
   rs.getString("PROCESS_FLOW_STATUS_ID") + "</pro_status>";
                    //  xml=xml+"<re_slno>"+rs.getInt("RELIEVAL_SLNO")+"</re_slno><dor>"+rs.getDate("DATE_OF_RELIEVAL")+"</dor><r_noon>"+rs.getString("RELIEVAL_FN_AN")+"</r_noon><r_rid>"+rs.getString("RELIEVAL_REASON_ID")+"</r_rid><remark>"+(rs.getString("REMARKS")==null?"null":rs.getString("REMARKS"))+"</remark>";


                    txtRel_SLNO = rs.getInt("RELIEVAL_SLNO");
                    cmbReason = rs.getString("RELIEVAL_REASON_ID");
                    ps.close();
                    rs.close();
                    if ((cmbReason).equalsIgnoreCase("PRO")) {
                        ps =
  con.prepareStatement("select  NEW_DESIGNATION_ID, OFFICE_TO_POSTED_ID,REPOSTING_REQD, PROMOTION_PROCEED_DATE, PROMOTION_PROCEED_NO  from HRM_EMP_RELIEVAL_PRO where OFFICE_ID=? and EMPLOYEE_ID=? and  RELIEVAL_SLNO=?");
                        ps.setInt(1, txtOffId);
                        ps.setInt(2, eid);
                        ps.setInt(3, txtRel_SLNO);
                        rs = ps.executeQuery();
                        if (rs.next()) {
                            xml =
 xml + "<post_oid>" + rs.getInt("OFFICE_TO_POSTED_ID") +
   "</post_oid><des_id>" + rs.getInt("NEW_DESIGNATION_ID") +
   "</des_id><repost_req>" + rs.getString("REPOSTING_REQD") +
   "</repost_req><t_pr_date>" + rs.getDate("PROMOTION_PROCEED_DATE") +
   "</t_pr_date><t_pr_no>" + rs.getString("PROMOTION_PROCEED_NO") +
   "</t_pr_no>";
                        }
                        int ofid = 0;
                        ofid = rs.getInt("OFFICE_TO_POSTED_ID");
                        ps2 =
 con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=?");
                        ps2.setInt(1, ofid);
                        rs2 = ps2.executeQuery();
                        if (rs2.next())
                            xml =
 xml + "<r_oname>" + rs2.getString("OFFICE_NAME") + "</r_oname>";
                        ps2.close();
                        rs2.close();
                        int des_id = 0;
                        des_id = rs.getInt("NEW_DESIGNATION_ID");
                        ps2 =
 con.prepareStatement("select DESIGNATION from HRM_MST_DESIGNATIONS where DESIGNATION_ID=?");
                        ps2.setInt(1, des_id);
                        rs2 = ps2.executeQuery();
                        if (rs2.next())
                            xml =
 xml + "<r_designame>" + rs2.getString("DESIGNATION") + "</r_designame>";
                        ps2.close();
                        rs2.close();
                    } else if ((cmbReason).equalsIgnoreCase("TRN")) {
                        ps =
  con.prepareStatement("select  TRANSFER_TO_OFFICE_ID,REPOSTING_REQD,TRANSFER_PROCEED_DATE,TRANSFER_PROCEED_NO  from HRM_EMP_RELIEVAL_TRN where OFFICE_ID=? and EMPLOYEE_ID=? and  RELIEVAL_SLNO=?");
                        ps.setInt(1, txtOffId);
                        ps.setInt(2, eid);
                        ps.setInt(3, txtRel_SLNO);
                        rs = ps.executeQuery();
                        if (rs.next()) {
                            xml =
 xml + "<t_oid>" + rs.getInt("TRANSFER_TO_OFFICE_ID") +
   "</t_oid><repost_req>" + rs.getString("REPOSTING_REQD") +
   "</repost_req><t_pr_date>" + rs.getDate("TRANSFER_PROCEED_DATE") +
   "</t_pr_date><t_pr_no>" + rs.getString("TRANSFER_PROCEED_NO") +
   "</t_pr_no>";
                        }
                        int ofid = 0;
                        ofid = rs.getInt("TRANSFER_TO_OFFICE_ID");
                        ps2 =
 con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=?");
                        ps2.setInt(1, ofid);
                        rs2 = ps2.executeQuery();
                        if (rs2.next())
                            xml =
 xml + "<r_oname>" + rs2.getString("OFFICE_NAME") + "</r_oname>";
                        ps2.close();
                        rs2.close();
                    } else if ((cmbReason).equalsIgnoreCase("DVN")) {
                        ps =
  con.prepareStatement("select  DIVERSION_TO_OFFICE_ID, DIVERSION_DATE from HRM_EMP_RELIEVAL_DVN where OFFICE_ID=? and EMPLOYEE_ID=? and  RELIEVAL_SLNO=?");
                        ps.setInt(1, txtOffId);
                        ps.setInt(2, eid);
                        ps.setInt(3, txtRel_SLNO);
                        rs = ps.executeQuery();
                        if (rs.next()) {
                            xml =
 xml + "<dvn_oid>" + rs.getInt("DIVERSION_TO_OFFICE_ID") +
   "</dvn_oid><dvn_date>" + rs.getDate("DIVERSION_DATE") + "</dvn_date>";
                        }
                        int ofid = 0;
                        ofid = rs.getInt("DIVERSION_TO_OFFICE_ID");
                        System.out.println(ofid);
                        if (ofid != 0) {
                            ps2 =
 con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=?");
                            ps2.setInt(1, ofid);
                            rs2 = ps2.executeQuery();
                            if (rs2.next())
                                xml =
 xml + "<r_oname>" + rs2.getString("OFFICE_NAME") + "</r_oname>";
                            ps2.close();
                            rs2.close();
                        } else
                            xml = xml + "<r_oname>null</r_oname>";

                    } else if ((cmbReason).equalsIgnoreCase("DPN")) {
                        ps =
  con.prepareStatement("select  OTHER_DEPT_ID, OTHER_DEPT_OFFICE_ID,DEPUTATION_PERIOD,DEPUTATION_DATE from HRM_EMP_RELIEVAL_DPN where OFFICE_ID=? and EMPLOYEE_ID=? and  RELIEVAL_SLNO=?");
                        ps.setInt(1, txtOffId);
                        ps.setInt(2, eid);
                        ps.setInt(3, txtRel_SLNO);
                        rs = ps.executeQuery();
                        if (rs.next()) {
                            xml =
 xml + "<od_id>" + rs.getString("OTHER_DEPT_ID") + "</od_id><od_oid>" +
   rs.getInt("OTHER_DEPT_OFFICE_ID") + "</od_oid><d_period>" +
   rs.getString("DEPUTATION_PERIOD") + "</d_period><d_date>" +
   rs.getDate("DEPUTATION_DATE") + "</d_date>";
                        }
                        String depid = "";
                        depid = rs.getString("OTHER_DEPT_ID");
                        ps2 =
 con.prepareStatement("select OTHER_DEPT_NAME from HRM_MST_OTHER_DEPTS where OTHER_DEPT_ID=?");
                        ps2.setString(1, depid);
                        rs2 = ps2.executeQuery();
                        if (rs2.next())
                            xml =
 xml + "<r_dname>" + rs2.getString("OTHER_DEPT_NAME") + "</r_dname>";
                        ps2.close();
                        rs2.close();
                        int dep_offid = 0;
                        dep_offid = rs.getInt("OTHER_DEPT_OFFICE_ID");
                        ps2 =
 con.prepareStatement("select OTHER_DEPT_OFFICE_NAME from HRM_MST_OTHER_DEPT_OFFICES where OTHER_DEPT_ID=? and OTHER_DEPT_OFFICE_ID=?");
                        ps2.setString(1, depid);
                        ps2.setInt(2, dep_offid);
                        rs2 = ps2.executeQuery();
                        if (rs2.next())
                            xml =
 xml + "<r_oname>" + rs2.getString("OTHER_DEPT_OFFICE_NAME") + "</r_oname>";
                        ps2.close();
                        rs2.close();
                    } else if ((cmbReason).equalsIgnoreCase("LLV")) {
                        ps =
  con.prepareStatement("select  LEAVE_TYPE_ID, PURPOSE,PERIOD_FROM,PERIOD_TO from HRM_EMP_RELIEVAL_LLV where OFFICE_ID=? and EMPLOYEE_ID=? and  RELIEVAL_SLNO=?");
                        ps.setInt(1, txtOffId);
                        ps.setInt(2, eid);
                        ps.setInt(3, txtRel_SLNO);
                        rs = ps.executeQuery();
                        if (rs.next()) {
                            xml =
 xml + "<l_tid>" + rs.getString("LEAVE_TYPE_ID") + "</l_tid><pur>" +
   rs.getString("PURPOSE") + "</pur><l_pfrom>" + rs.getDate("PERIOD_FROM") +
   "</l_pfrom><l_pto>" + rs.getDate("PERIOD_TO") + "</l_pto>";
                        }
                    }
                } else {
                    System.out.println("2nd im here");
                    xml =
 "<response><command>loadReason</command><flag>failure</flag>";
                }
            } catch (Exception e) {
                System.out.println("catch..HERE.in load emp." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            out.println(xml);
        }


        else if (strCommand.equalsIgnoreCase("Add")) {
            String CONTENT_TYPE = "text/html; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            xml = "<response><command>Add</command>";

            //int txtOffId=0,txtEmployeeid=0,txtRel_SLNO=0;
            //Date  txtDORelieval=null;
            //String rad_DORelieval="",cmbReason="",txtRemarks="";

            session = request.getSession(false);
            String updatedby = (String)session.getAttribute("UserId");
            long l = System.currentTimeMillis();
            java.sql.Timestamp ts = new java.sql.Timestamp(l);

            String Proc_Status = "";
            System.out.println(xml);
            if (request.getParameter("_status") != null &&
                request.getParameter("_status").equalsIgnoreCase("FR")) {
                Proc_Status = "FR";
            } else {
                Proc_Status = request.getParameter("cmbStatus");
            }
            System.out.println(Proc_Status);
            txtRel_SLNO =
                    Integer.parseInt(request.getParameter("txtRel_SLNO"));
            System.out.println(xml);
            System.out.println(txtRel_SLNO);
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

            // cmbReason=request.getParameter("cmbReason");

            // cmbReason="FR";
            txtRemarks = request.getParameter("txtRemarks");
            System.out.println(txtOffId);
            System.out.println(txtEmployeeid);
            System.out.println(txtRel_SLNO);
            System.out.println(txtDORelieval);
            //System.out.println(cmbReason);
            System.out.println("from " +
                               request.getParameter("txtDORelieval"));


            /*****************************************/
            try {


                if (Proc_Status.equalsIgnoreCase("FR")) {


                    /*   remove the SR Controlling office and moved it to log table  */
                    System.out.println("step 1");
                    PreparedStatement psorg =
                        con.prepareStatement(" select EMPLOYEE_ID,CONTROLLING_OFFICE_ID,CONTROLLING_DEPARTMENT_ID, " +
                                             "   PROCESS_FLOW_STATUS_ID,CONTROLLING_DATE_FROM,UPDATED_BY_USER_ID,UPDATED_DATE " +
                                             "    from HRM_EMP_CONTROLLING_OFFICE where EMPLOYEE_ID=?");
                    psorg.setInt(1, txtEmployeeid);
                    ResultSet rsorg = psorg.executeQuery();
                    if (rsorg.next()) {
                        System.out.println("step 2");
                        PreparedStatement psins =
                            con.prepareStatement("select max(LOG_ID) sid from HRM_EMP_CONTROLLING_OFFICE_LOG where EMPLOYEE_ID =? ");
                        psins.setInt(1, txtEmployeeid);
                        ResultSet rsins = psins.executeQuery();
                        if (rsins.next()) {
                            int sid = rsins.getInt("sid");
                            if (sid > 0) {
                                sid += 1;
                            } else {
                                sid = 1;
                            }
                            System.out.println("step 3");
                            psins =
con.prepareStatement("insert into HRM_EMP_CONTROLLING_OFFICE_LOG(EMPLOYEE_ID,CONTROLLING_OFFICE_ID,CONTROLLING_DEPARTMENT_ID, " +
                     "   PROCESS_FLOW_STATUS_ID,CONTROLLING_DATE_FROM,LOG_ID,UPDATED_BY_USER_ID,UPDATED_DATE) values (?,?,?,?,?,?,?,?)");
                            psins.setInt(1, rsorg.getInt("EMPLOYEE_ID"));
                            psins.setInt(2,
                                         rsorg.getInt("CONTROLLING_OFFICE_ID"));
                            psins.setString(3,
                                            rsorg.getString("CONTROLLING_DEPARTMENT_ID"));
                            psins.setString(4,
                                            rsorg.getString("PROCESS_FLOW_STATUS_ID"));
                            psins.setDate(5,
                                          rsorg.getDate("CONTROLLING_DATE_FROM"));
                            psins.setInt(6, sid);
                            psins.setString(7,
                                            rsorg.getString("UPDATED_BY_USER_ID"));
                            psins.setTimestamp(8,
                                               rsorg.getTimestamp("UPDATED_DATE"));

                            psins.executeUpdate();
                            System.out.println("step 4");
                        }
                    }


                    System.out.println("step 5");
                    psorg =
con.prepareStatement("delete from HRM_EMP_CONTROLLING_OFFICE where EMPLOYEE_ID=? ");
                    psorg.setInt(1, txtEmployeeid);
                    psorg.executeUpdate();
                    System.out.println("step 6");


                    psorg =
con.prepareStatement("update  HRM_EMP_CONTROLLING_OFFICE_TMP set PROCESS_FLOW_STATUS_ID='MD',UPDATED_BY_USER_ID=?,UPDATED_DATE=?   where EMPLOYEE_ID=? ");
                    psorg.setString(1, updatedby);
                    psorg.setTimestamp(2, ts);
                    psorg.setInt(3, txtEmployeeid);
                    psorg.executeUpdate();
                    System.out.println("step 7");

                    psorg =
con.prepareStatement("update SEC_MST_USERS set LOGIN_ENABLED='0' where EMPLOYEE_ID=?  ");
                    psorg.setInt(1, txtEmployeeid);
                    psorg.executeUpdate();
                    System.out.println("step 8");
                    /* End of the comment:   remove the SR Controlling office and moved it to log table  */


                }

                System.out.println("txtOffId..." + txtOffId);
                System.out.println("txtRel_SLNO..." + txtRel_SLNO);
                System.out.println("txtEmployeeid..." + txtEmployeeid);
                System.out.println("txtDORelieval..." + txtDORelieval);
                System.out.println("rad_DORelieval..." + rad_DORelieval);
                System.out.println("txtRemarks..." + txtRemarks);
                System.out.println("Proc_Status..." + Proc_Status);
                System.out.println("updatedby..." + updatedby);
                System.out.println("ts.." + ts);


                System.out.println("here is ok");
                cs =
  con.prepareCall("{call HRM_EMP_DPNRELIEVAL_DPN_PROC(?,?,?,?,?,?,?,?,?,?,?)}");
                cs.setInt(1, txtOffId);
                cs.setInt(2, txtRel_SLNO);
                cs.setInt(3, txtEmployeeid);
                cs.setDate(4, txtDORelieval);
                cs.setString(5, rad_DORelieval);
                cs.setString(6, txtRemarks);
                cs.setString(8, "update");
                cs.setString(9, Proc_Status);

                cs.setString(10, updatedby);
                cs.setTimestamp(11, ts);

                cs.registerOutParameter(2, java.sql.Types.NUMERIC);
                cs.registerOutParameter(7, java.sql.Types.NUMERIC);
                cs.execute();
                System.out.println("after execution");
                txtRel_SLNO = cs.getInt(2);
                int errcode = cs.getInt(7);
                System.out.println("SQLCODE:::" + errcode);
                if (errcode != 0) {
                    xml = xml + "<flag>failure</flag>";
                } else {
                    con.commit();
                    xml =
 xml + "<flag>success</flag><relieNo>" + txtRel_SLNO + "</relieNo>";
                    sendMessage(response,
                                " The Relieval Serial Number  " + txtRel_SLNO +
                                "  has been modified successfully.", "ok");
                }
            } catch (Exception e) {
                System.out.println("Update Exception " + e);
            }
            /******************************************/


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
        }
    }
}
