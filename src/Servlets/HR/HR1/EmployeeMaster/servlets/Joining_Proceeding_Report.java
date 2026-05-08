package Servlets.HR.HR1.EmployeeMaster.servlets;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

public class Joining_Proceeding_Report extends HttpServlet {
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
        //Statement statement=null;
        ResultSet results = null;
        ResultSet rs4 = null;
        ResultSet rs3 = null;
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        // PreparedStatement ps3=null;
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

                String sql =
                    "delete from HRM_EMP_JOIN_REPORTS  where EMPLOYEE_ID=? and JOINING_REPORT_ID=? and OFFICE_ID=? and JOINING_YEAR=?";

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
        }

        else if (strCommand.equalsIgnoreCase("Load")) {

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
                        /* String sql="SELECT A.EMPLOYEE_NAME ||decode(a.EMPLOYEE_INITIAL,null,' ','.'||a.EMPLOYEE_INITIAL) as  EMPLOYEE_NAME ,A.GPF_NO,A.DATE_OF_BIRTH,B.JOINING_REPORT_ID, " +
                                        " B.DATE_OF_JOINING, B.FN_OR_AN, B.DESIGNATION_ID, B.POST_COUNTED_ID, " +
                                        " B.REMARKS,B.PROCESS_FLOW_STATUS_ID,B.COMPLETED_DATE,B.EMP_PRE_STATUS,B.DATE_EFFECTIVE_FROM_SESSION,B.JOINED_SUBDIVISION,B.SUBDIVISION_OFFICE_ID,B.OFFICE_WING_SINO FROM HRM_MST_EMPLOYEES A " +
                                        " INNER JOIN HRM_EMP_JOIN_REPORTS B ON B.EMPLOYEE_ID=A.EMPLOYEE_ID " +
                                         " WHERE A.EMPLOYEE_ID=? and (B.PROCESS_FLOW_STATUS_ID='CR' or B.PROCESS_FLOW_STATUS_ID='MD') ";*/


                        String sql =
                            "select a.employee_id,a.employee_name ||decode(a.EMPLOYEE_INITIAL,null,' ','.'||a.EMPLOYEE_INITIAL) as  EMPLOYEE_NAME,a.date_of_birth,a.gpf_no," +
                            " c.cadre_id,d.cadre_name,d.cadre_short_name,e.JOINING_REPORT_ID,e.DATE_OF_JOINING,e.FN_OR_AN,e.DESIGNATION_ID,e.POST_COUNTED_ID,e.REMARKS," +
                            " e.COMPLETED_DATE,e.EMP_PRE_STATUS,e.DATE_EFFECTIVE_FROM_SESSION,e.JOINED_SUBDIVISION,e.SUBDIVISION_OFFICE_ID," +
                            " e.OFFICE_WING_SINO,e.PROCESS_FLOW_STATUS_ID,f.office_name,e.OFFICE_GRADE from" +
                            " (" +
                            " select employee_id,employee_name,EMPLOYEE_INITIAL,date_of_birth,gpf_no from hrm_mst_employees" +
                            " where employee_id=?" + " ) a" +
                            " left outer join" + " (" +
                            " select employee_id,JOINING_REPORT_ID,DATE_OF_JOINING,FN_OR_AN,DESIGNATION_ID,POST_COUNTED_ID,REMARKS,COMPLETED_DATE,EMP_PRE_STATUS," +
                            " OFFICE_GRADE,DATE_EFFECTIVE_FROM_SESSION,JOINED_SUBDIVISION,SUBDIVISION_OFFICE_ID,OFFICE_WING_SINO,PROCESS_FLOW_STATUS_ID,office_id from HRM_EMP_JOIN_REPORTS" +
                            " WHERE (PROCESS_FLOW_STATUS_ID='CR' or PROCESS_FLOW_STATUS_ID='MD')" +
                            " ) e" + " on a.EMPLOYEE_ID=e.EMPLOYEE_ID" +
                            " left outer join" + " (" +
                            "  select office_id,office_name from com_mst_offices" +
                            " ) f" + " on e.office_id=f.office_id" +
                            " left outer join" + " (" +
                            " select employee_id ,designation_id from hrm_emp_current_posting" +
                            " ) b" + " on e.employee_id=b.employee_id" +
                            " left outer join" + " (" +
                            " select designation_id,cadre_id from hrm_mst_designations" +
                            " ) c" + " on b.designation_id=c.designation_id" +
                            " left outer join" + " (" +
                            " select cadre_id,cadre_name,cadre_short_name from hrm_mst_cadre" +
                            " ) d" + " on c.cadre_id=d.cadre_id";


                        ps = connection.prepareStatement(sql);
                        ps.setInt(1, Integer.parseInt(strEmpName));
                        ResultSet rs = ps.executeQuery();

                        int i = 0;
                        String strDob = "";
                        if (rs.next()) {
                            System.out.println("emp name" + strEmpName);

                            String strEName = rs.getString("Employee_Name");
                            int strEmpGpf = rs.getInt("GPF_NO");

                            String cadre = rs.getString("CADRE_NAME");
                            System.out.println("cadre" + cadre);

                            String off_name = rs.getString("OFFICE_NAME");
                            System.out.println(off_name);


                            int JoinId = rs.getInt("JOINING_REPORT_ID");
                            String strNoon = rs.getString("FN_OR_AN");

                            int DesigId = rs.getInt("DESIGNATION_ID");

                            String off_grade = rs.getString("OFFICE_GRADE");

                            int PostId = rs.getInt("POST_COUNTED_ID");

                            String strRemarks = rs.getString("REMARKS");

                            int wing = rs.getInt("OFFICE_WING_SINO");

                            String strProcId =
                                rs.getString("PROCESS_FLOW_STATUS_ID");
                            System.out.println("proceess flow id is$$$$$" +
                                               strProcId);
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
   "</JoinId><Cadre>" + cadre + "</Cadre>";
                            xml =
 xml + "<Noon>" + strNoon + "</Noon>" + "<DesigId>" + DesigId +
   "</DesigId><Grade>" + off_grade + "</Grade><Off_Name>" + off_name +
   "</Off_Name>";
                            xml =
 xml + "<JDate>" + strJoindt + "</JDate><PostId>" + PostId +
   "</PostId><Remarks>" + strRemarks + "</Remarks><ProcId>" + strProcId +
   "</ProcId>";
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
                            System.out.println("dest" + DesigId);
                            try {
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
                                    //ps=connection.prepareStatement("select max(date_to) as maxtodate from HRM_EMP_SERVICE_DATA where EMPLOYEE_ID=?");
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
                            xml = sxml + "<flag>failure2</flag>" + xml;
                        } else {
                            System.out.println("EmpStatus in joining form:" +
                                               empstatus);
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


            session = request.getSession(false);
            String updatedby = (String)session.getAttribute("UserId");
            long l = System.currentTimeMillis();
            Timestamp ts = new Timestamp(l);

            int strEmpName = 0;
            int strDesigId = 0;
            int PostId = 0;
            String strRemarks = "", empstatus = "", compsession =
                "", strgrade = "";
            int JYear = 0;
            int OffId = 0;
            int JoinId = 0;
            String strProceId = "";
            String radVal = "";
            Date dtjoin = null;
            java.util.Date d = null;
            java.sql.Date cdt = null;
            String optjoin = "";
            int currentofficecode = 0;
            int wing = 0;
            try {
                try {
                    System.out.println("join Validate");

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
                    strProceId = request.getParameter("comProcFlowId");
                    strgrade = request.getParameter("txtgrade");

                    System.out.println("strgrade" + strgrade);

                    Calendar c;
                    String[] sd =
                        request.getParameter("txtjoindate").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                    d = c.getTime();
                    dtjoin = new Date(d.getTime());

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


                } catch (Exception e) {
                    System.out.println("exce **** " + e);
                }
                boolean flag = false;
                connection.setAutoCommit(false);
                System.out.println(strProceId);
                if (strProceId.equalsIgnoreCase("FR")) {
                    String sql =
                        "select EMPLOYEE_STATUS_ID  from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?";

                    ps = connection.prepareStatement(sql);
                    ps.setInt(1, strEmpName);
                    results = ps.executeQuery();
                    if (!results.next()) {
                        xml =
 xml + "<flag>failure</flag><flag>This employee does not have any current post.So Cann't Freezed.</flag>";
                        flag = true;
                    } else {
                        String currentStatus =
                            results.getString("EMPLOYEE_STATUS_ID");
                        if (currentStatus.equalsIgnoreCase("WKG")) {
                            xml =
 xml + "<flag>failure</flag><flag>Given employee has been working status.Can not post aganin.</flag>";
                            flag = true;
                        }

                    }


                }
                if (!flag) {

                    int officeid = OffId;
                    PreparedStatement psorg =
                        connection.prepareStatement("select DESIGNATION_SHORT_NAME from HRM_MST_DESIGNATIONS where DESIGNATION_ID=?");
                    psorg.setInt(1, strDesigId);
                    ResultSet rsorg = psorg.executeQuery();
                    if (rsorg.next()) {
                        String desc =
                            rsorg.getString("DESIGNATION_SHORT_NAME");
                        System.out.println("Office Id::" + OffId);

                        if (desc != null &&
                            (desc.equalsIgnoreCase("EE") || desc.equalsIgnoreCase("CE") ||
                             desc.equalsIgnoreCase("CE"))) {

                            if (desc.equalsIgnoreCase("EE")) {
                                System.out.println("EE");
                                PreparedStatement psl =
                                    connection.prepareStatement("select OFFICE_LEVEL_ID from COM_MST_OFFICES where OFFICE_ID=?");
                                psl.setInt(1, OffId);
                                ResultSet rsl = psl.executeQuery();
                                if (rsl.next()) {
                                    String level =
                                        rsl.getString("OFFICE_LEVEL_ID");
                                    if (level.equalsIgnoreCase("HO")) {
                                        System.out.println("HO");
                                        officeid = OffId;
                                    } else if (level.equalsIgnoreCase("RN")) {
                                        System.out.println("RN");
                                        officeid = OffId;
                                    } else if (level.equalsIgnoreCase("CL")) {
                                        System.out.println("CL");
                                        PreparedStatement psc =
                                            connection.prepareStatement("select CONTROLLING_OFFICE_ID from  COM_OFFICE_CONTROL where OFFICE_ID=?");
                                        psc.setInt(1, OffId);
                                        ResultSet rsc = psc.executeQuery();
                                        if (rsc.next()) {
                                            officeid =
                                                    rsc.getInt("CONTROLLING_OFFICE_ID");
                                        }
                                    } else if (level.equalsIgnoreCase("DN")) {
                                        System.out.println("DN");
                                        PreparedStatement psd =
                                            connection.prepareStatement("select CONTROLLING_OFFICE_ID from COM_OFFICE_CONTROL where OFFICE_ID=?");
                                        psd.setInt(1, OffId);
                                        ResultSet rsd = psd.executeQuery();
                                        if (rsd.next()) {
                                            int officecl =
                                                rsd.getInt("CONTROLLING_OFFICE_ID");
                                            PreparedStatement psc =
                                                connection.prepareStatement("select CONTROLLING_OFFICE_ID from COM_OFFICE_CONTROL where OFFICE_ID=?");
                                            psc.setInt(1, officecl);
                                            ResultSet rsc = psc.executeQuery();
                                            if (rsc.next()) {
                                                officeid =
                                                        rsc.getInt("CONTROLLING_OFFICE_ID");
                                            }

                                        }
                                    }


                                }

                            } //EE
                            else if (desc.equalsIgnoreCase("CE") ||
                                     desc.equalsIgnoreCase("SE")) {
                                //officeid=5000;
                                System.out.println("CE or SE");
                                PreparedStatement psd =
                                    connection.prepareStatement("select OFFICE_ID from COM_MST_OFFICES where OFFICE_LEVEL_ID='HO'");
                                ResultSet rsd = psd.executeQuery();
                                if (rsd.next()) {
                                    officeid = rsd.getInt("OFFICE_ID");
                                }
                            } // CE  ||  SE
                            else {
                                officeid = OffId;
                            }
                        }
                    }

                    try {
                        String offlevel = null;
                        int sdofficeid = 0;
                        System.out.println("office id  is:======" + OffId);
                        PreparedStatement psnew =
                            connection.prepareStatement("select office_level_id from com_mst_offices where office_id=?");
                        psnew.setInt(1, OffId);
                        ResultSet rsnew = psnew.executeQuery();
                        if (rsnew.next()) {
                            offlevel = rsnew.getString("office_level_id");
                        }
                        if (offlevel.equalsIgnoreCase("SD")) {
                            System.out.println("Sub division controllign office id");
                            PreparedStatement psnew1 =
                                connection.prepareStatement("select controlling_office_id from com_office_control where office_id=?");
                            psnew1.setInt(1, OffId);
                            ResultSet rsnew1 = psnew1.executeQuery();
                            if (rsnew1.next()) {
                                sdofficeid =
                                        rsnew1.getInt("controlling_office_id");
                            }

                            officeid = sdofficeid;
                            System.out.println("Sub division controlling ofice id is :" +
                                               officeid);
                        }

                    } catch (Exception e) {
                        System.out.println("Subdivision check error" + e);
                    }
                    System.out.println("Sub division controlling ofice id is outsid :" +
                                       officeid);


                    System.out.println("here is ok1");
                    // Delete the SR Controllling office record if exist
                    psorg =
connection.prepareStatement("delete from HRM_EMP_CONTROLLING_OFFICE where EMPLOYEE_ID=?");
                    psorg.setInt(1, strEmpName);
                    psorg.executeUpdate();
                    System.out.println("here is ok2");
                    // insert SR Controlling Office Record
                    psorg =
connection.prepareStatement("insert into  HRM_EMP_CONTROLLING_OFFICE(EMPLOYEE_ID,CONTROLLING_OFFICE_ID,CONTROLLING_DEPARTMENT_ID,PROCESS_FLOW_STATUS_ID,CONTROLLING_DATE_FROM,UPDATED_BY_USER_ID,UPDATED_DATE) " +
                            " values (?,?,?,?,?,?,?)");
                    psorg.setInt(1, strEmpName);
                    psorg.setInt(2, officeid);
                    psorg.setString(3, "TWAD");
                    psorg.setString(4, "FR");
                    java.sql.Date dt =
                        new java.sql.Date(new java.util.Date(System.currentTimeMillis()).getTime());
                    psorg.setDate(5, dt);
                    psorg.setString(6, updatedby);
                    psorg.setTimestamp(7, ts);
                    psorg.executeUpdate();
                    System.out.println("here is ok3");

                    System.out.println("ctrl office :" + officeid);
                    psorg =
connection.prepareStatement("delete from HRM_EMP_CONTROLLING_OFFICE_TMP where EMPLOYEE_ID=?");
                    psorg.setInt(1, strEmpName);
                    psorg.executeUpdate();
                    System.out.println("here is ok4");

                    psorg =
connection.prepareStatement("insert into  HRM_EMP_CONTROLLING_OFFICE_TMP(EMPLOYEE_ID,CONTROLLING_OFFICE_ID,CONTROLLING_DEPARTMENT_ID,PROCESS_FLOW_STATUS_ID,CONTROLLING_DATE_FROM,UPDATED_BY_USER_ID,UPDATED_DATE) " +
                            " values (?,?,?,?,?,?,?)");
                    psorg.setInt(1, strEmpName);
                    psorg.setInt(2, officeid);
                    psorg.setString(3, "TWAD");
                    psorg.setString(4, "FR");
                    dt =
  new java.sql.Date(new java.util.Date(System.currentTimeMillis()).getTime());
                    psorg.setDate(5, dt);

                    psorg.setString(6, updatedby);
                    psorg.setTimestamp(7, ts);
                    psorg.executeUpdate();
                    System.out.println("here is ok5");

                    ps =
  connection.prepareStatement("delete from HRM_EMP_CURRENT_WING where EMPLOYEE_ID=? ");
                    ps.setInt(1, strEmpName);
                    ps.executeUpdate();
                    System.out.println("test1");

                    java.sql.Date dt1 =
                        new java.sql.Date(System.currentTimeMillis());
                    ps =
  connection.prepareStatement("insert into HRM_EMP_CURRENT_WING(EMPLOYEE_ID,OFFICE_ID,OFFICE_WING_SINO,DATE_EFFECTIVE_FROM,UPDATED_BY_USER_ID,UPDATED_DATE) values(?,?,?,?,?,?)");

                    ps.setInt(1, strEmpName);
                    ps.setInt(2, OffId);
                    ps.setInt(3, wing);
                    ps.setDate(4, dtjoin);
                    ps.setString(5, updatedby);
                    ps.setTimestamp(6, ts);
                    ps.executeUpdate();
                    System.out.println("test2");

                    CallableStatement cs = null;
                    System.out.println("proc started");
                    cs =
  connection.prepareCall("{call HRM_EMP_JOIN_REPORT_UPT(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

                    cs.setInt(1, OffId);
                    cs.setInt(2, JYear);
                    cs.setInt(3, JoinId);
                    cs.setInt(4, strEmpName);
                    cs.setDate(5, dtjoin);
                    cs.setString(6, radVal);
                    cs.setInt(7, strDesigId);
                    cs.setInt(8, PostId);
                    cs.setString(9, strRemarks);
                    cs.setString(10, strProceId);
                    cs.registerOutParameter(11, java.sql.Types.NUMERIC);
                    //cs.setDate(12,cdt);
                    cs.setString(12, empstatus);
                    cs.setString(13, optjoin);
                    cs.setInt(14, currentofficecode);
                    //cs.setString(14,compsession);
                    cs.setString(15, updatedby);
                    cs.setTimestamp(16, ts);
                    cs.setInt(17, wing);
                    //cs.setDate(11,dtjoin);
                    cs.execute();
                    System.out.println("proc ended");
                    int errcode = cs.getInt(11);
                    System.out.println("SQLCODE:::" + errcode);
                    if (errcode != 0) {
                        xml = xml + "<flag>failure</flag>";
                    } else {
                        xml = xml + "<flag>success</flag>";
                    }
                    ps.close();
                }
                connection.commit();


            } catch (Exception e) {
                System.out.println("exception in the insertion " + e);
                xml = xml + "<flag>failure</flag>";
                try {
                    connection.rollback();
                } catch (Exception e1) {
                    System.out.println(e);
                }
            } finally {
                try {
                    connection.setAutoCommit(true);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            xml = xml + "</response>";
        }

        else if (strCommand.equalsIgnoreCase("Load1")) {

            String sxml = "<response><command>Load</command>";
            String strJoindt = "";
            String strEmpName = request.getParameter("EName");
            System.out.println("emp name" + strEmpName);
            try {

                String sql =
                    "select a.employee_id,a.employee_name ||decode(a.EMPLOYEE_INITIAL,null,' ','.'||a.EMPLOYEE_INITIAL) as  EMPLOYEE_NAME,a.date_of_birth,a.gpf_no," +
                    " c.cadre_id,d.cadre_name,d.cadre_short_name,e.JOINING_REPORT_ID,e.DATE_OF_JOINING,e.FN_OR_AN,e.DESIGNATION_ID,e.POST_COUNTED_ID,e.REMARKS," +
                    " e.COMPLETED_DATE,e.EMP_PRE_STATUS,e.DATE_EFFECTIVE_FROM_SESSION,e.JOINED_SUBDIVISION,e.SUBDIVISION_OFFICE_ID," +
                    " e.OFFICE_WING_SINO,e.PROCESS_FLOW_STATUS_ID,f.office_name,e.OFFICE_GRADE from" +
                    " (" +
                    " select employee_id,employee_name,EMPLOYEE_INITIAL,date_of_birth,gpf_no from hrm_mst_employees" +
                    " where employee_id=?" + " ) a" + " left outer join" +
                    " (" +
                    " select employee_id,JOINING_REPORT_ID,DATE_OF_JOINING,FN_OR_AN,DESIGNATION_ID,POST_COUNTED_ID,REMARKS,COMPLETED_DATE,EMP_PRE_STATUS," +
                    " OFFICE_GRADE,DATE_EFFECTIVE_FROM_SESSION,JOINED_SUBDIVISION,SUBDIVISION_OFFICE_ID,OFFICE_WING_SINO,PROCESS_FLOW_STATUS_ID,office_id from HRM_EMP_JOIN_REPORTS" +
                    " WHERE (PROCESS_FLOW_STATUS_ID='CR' or PROCESS_FLOW_STATUS_ID='MD')" +
                    " ) e" + " on a.EMPLOYEE_ID=e.EMPLOYEE_ID" +
                    " left outer join" + " (" +
                    "  select office_id,office_name from com_mst_offices" +
                    " ) f" + " on e.office_id=f.office_id" +
                    " left outer join" + " (" +
                    " select employee_id ,designation_id from hrm_emp_current_posting" +
                    " ) b" + " on e.employee_id=b.employee_id" +
                    " left outer join" + " (" +
                    " select designation_id,cadre_id from hrm_mst_designations" +
                    " ) c" + " on b.designation_id=c.designation_id" +
                    " left outer join" + " (" +
                    " select cadre_id,cadre_name,cadre_short_name from hrm_mst_cadre" +
                    " ) d" + " on c.cadre_id=d.cadre_id";


                ps = connection.prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(strEmpName));
                ResultSet rs = ps.executeQuery();

                int i = 0;
                String strDob = "";
                if (rs.next()) {
                    System.out.println("emp name" + strEmpName);

                    String strEName = rs.getString("Employee_Name");
                    int strEmpGpf = rs.getInt("GPF_NO");

                    String cadre = rs.getString("CADRE_NAME");
                    System.out.println("cadre" + cadre);

                    String off_name = rs.getString("OFFICE_NAME");
                    System.out.println(off_name);

                    int JoinId = rs.getInt("JOINING_REPORT_ID");
                    String strNoon = rs.getString("FN_OR_AN");

                    int DesigId = rs.getInt("DESIGNATION_ID");

                    String off_grade = rs.getString("OFFICE_GRADE");

                    int PostId = rs.getInt("POST_COUNTED_ID");

                    String strRemarks = rs.getString("REMARKS");
                    String strProcId = rs.getString("PROCESS_FLOW_STATUS_ID");
                    i++;
                    Date strdt = rs.getDate("DATE_OF_BIRTH");
                    if (strdt == null) {
                        strDob = "0";
                    } else {
                        String[] sd;
                        sd = rs.getDate("DATE_OF_BIRTH").toString().split("-");
                        strDob = sd[2] + "/" + sd[1] + "/" + sd[0];
                    }

                    Date strjdt = rs.getDate("DATE_OF_JOINING");
                    if (strjdt == null) {
                        strJoindt = "0";
                    } else {
                        String[] sd1;
                        sd1 =
 rs.getDate("DATE_OF_JOINING").toString().split("-");
                        strJoindt = sd1[2] + "/" + sd1[1] + "/" + sd1[0];
                    }

                    xml =
 xml + "<Emp_Id>" + strEmpName + "</Emp_Id>" + "<EmpName>" + strEName +
   "</EmpName><EmpGpf>" + strEmpGpf + "</EmpGpf>";
                    xml =
 xml + "<Dtofbirth>" + strDob + "</Dtofbirth>" + "<JoinId>" + JoinId +
   "</JoinId><Cadre>" + cadre + "</Cadre>";
                    xml =
 xml + "<Noon>" + strNoon + "</Noon>" + "<DesigId>" + DesigId +
   "</DesigId><Grade>" + off_grade + "</Grade><Off_Name>" + off_name +
   "</Off_Name>";
                    xml =
 xml + "<JDate>" + strJoindt + "</JDate><PostId>" + PostId +
   "</PostId><Remarks>" + strRemarks + "</Remarks><ProcId>" + strProcId +
   "</ProcId>";
                    System.out.println("dest" + DesigId);
                    try {
                        String sql3 =
                            "select a.service_group_id from hrm_mst_service_group a inner join hrm_mst_designations b on b.service_group_id=a.service_group_id where b.designation_id=?";

                        ps1 = connection.prepareStatement(sql3);
                        ps1.setInt(1, DesigId);
                        rs3 = ps1.executeQuery();
                        while (rs3.next()) {
                            int servgrp = rs3.getInt("SERVICE_GROUP_ID");
                            System.out.println("serv" + servgrp);
                            xml =
 xml + "<ServGroup>" + servgrp + "</ServGroup>";
                        }
                    } catch (Exception se) {
                        System.out.println("error in serv grp" + se);
                    }
                }

                if (i == 0) {

                    try {
                        String sql2 =
                            "select * from hrm_mst_employees where employee_id=?";
                        ps2 = connection.prepareStatement(sql2);
                        ps2.setInt(1, Integer.parseInt(strEmpName));

                        rs4 = ps2.executeQuery();
                        int j = 0;
                        while (rs4.next()) {
                            j++;
                            String strEmpPref =
                                rs4.getString("Employee_Prefix");
                            String strEmpInit =
                                rs4.getString("Employee_Initial");
                            strEmpName = rs4.getString("Employee_Name");
                            int strEmpGpf = rs4.getInt("GPF_NO");
                            Date strdt = rs4.getDate("DATE_OF_BIRTH");
                            if (strdt == null) {
                                strDob = "0";
                            } else {
                                String[] sd;
                                sd =
  rs4.getDate("DATE_OF_BIRTH").toString().split("-");
                                strDob = sd[2] + "/" + sd[1] + "/" + sd[0];
                            }
                            xml = sxml + "<flag>NoRecord</flag>";
                            xml =
 xml + "<EmpPref>" + strEmpPref + "</EmpPref><EmpInit>" + strEmpInit +
   "</EmpInit><EmpName>" + strEmpName + "</EmpName><EmpGpf>" + strEmpGpf +
   "</EmpGpf><Dtofbirth>" + strDob + "</Dtofbirth>";
                        }
                        if (j == 0) {
                            xml = sxml + "<flag>NoValue</flag>";
                        }

                    } catch (Exception ae) {
                        System.out.println("Error in the second query" + ae);
                    }

                } else {
                    xml = sxml + "<flag>success</flag>" + xml;
                }

            } catch (Exception e) {
                System.out.println("Exception " + e);
                xml = sxml + "<flag>failure</flag>";
            }
            //}
            xml = xml + "</response>";

        }

        else if (strCommand.equalsIgnoreCase("LoadEmp")) {
            System.out.println("hello.");
            xml = "<response><command>LoadEmp</command>";
            // String strJoindt="";
            String strEmpName = request.getParameter("EName");
            System.out.println("emp name" + strEmpName);
            try {
                ps =
  connection.prepareStatement("SELECT EMPLOYEE_ID FROM HRM_EMP_CURRENT_POSTING WHERE EMPLOYEE_ID=?");
                ps.setInt(1, Integer.parseInt(strEmpName));
                results = ps.executeQuery();
                if (results.next()) {
                    xml = xml + "<flag>failure1</flag>";
                } else {
                    results.close();
                    ps.close();


                    String sql =
                        "SELECT A.EMPLOYEE_NAME ||decode(a.EMPLOYEE_INITIAL,null,' ','.'||a.EMPLOYEE_INITIAL) as  EMPLOYEE_NAME ,A.GPF_NO,A.DATE_OF_BIRTH FROM HRM_MST_EMPLOYEES A " +
                        " WHERE A.EMPLOYEE_ID=? ";

                    ps = connection.prepareStatement(sql);
                    ps.setInt(1, Integer.parseInt(strEmpName));
                    ResultSet rs = ps.executeQuery();

                    // int i=0;
                    String strDob = "";
                    if (rs.next()) {
                        xml = xml + "<flag>success</flag>";
                        System.out.println("emp name" + strEmpName);

                        String strEName = rs.getString("Employee_Name");
                        int strEmpGpf = rs.getInt("GPF_NO");

                        Date strdt = rs.getDate("DATE_OF_BIRTH");
                        if (strdt == null) {
                            strDob = "0";
                        } else {
                            String[] sd;
                            sd =
  rs.getDate("DATE_OF_BIRTH").toString().split("-");
                            strDob = sd[2] + "/" + sd[1] + "/" + sd[0];
                        }


                        xml =
 xml + "<Emp_Id>" + strEmpName + "</Emp_Id>" + "<EmpName>" + strEName +
   "</EmpName><EmpGpf>" + strEmpGpf + "</EmpGpf>";
                        xml = xml + "<Dtofbirth>" + strDob + "</Dtofbirth>";

                    } else {
                        xml =
 "<response><command>LoadEmp</command>" + "<flag>NoValue</flag>";
                    }
                }


            } catch (Exception e) {
                System.out.println("Exception " + e);
                xml = xml + "<flag>NoValue</flag>";
            }
            //}
            xml = xml + "</response>";

        } else if (strCommand.equalsIgnoreCase("LoadSer")) {
            System.out.println("load service ");
            String sxml = "<response><command>LoadSer</command>";
            String strJoindt = "";
            String strEmpName = request.getParameter("EName");
            System.out.println("emp name" + strEmpName);
            String strserid = request.getParameter("txtser");
            System.out.println("service id" + strserid);
            try {

                String sql =
                    "SELECT A.EMPLOYEE_NAME ||decode(a.EMPLOYEE_INITIAL,null,' ','.'||a.EMPLOYEE_INITIAL) as  EMPLOYEE_NAME ,A.GPF_NO,A.DATE_OF_BIRTH,B.JOINING_REPORT_ID, " +
                    " B.DATE_OF_JOINING, B.FN_OR_AN, B.DESIGNATION_ID, B.POST_COUNTED_ID, " +
                    " B.REMARKS,B.PROCESS_FLOW_STATUS_ID,B.OFFICE_GRADE FROM HRM_MST_EMPLOYEES A " +
                    " INNER JOIN HRM_EMP_JOIN_REPORTS B ON B.EMPLOYEE_ID=A.EMPLOYEE_ID " +
                    " WHERE A.EMPLOYEE_ID=? and B.JOINING_REPORT_ID=? ";

                ps = connection.prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(strEmpName));
                ps.setInt(2, Integer.parseInt(strserid));
                ResultSet rs = ps.executeQuery();

                int i = 0;
                String strDob = "";
                if (rs.next()) {
                    System.out.println("emp name" + strEmpName);

                    String strEName = rs.getString("Employee_Name");
                    int strEmpGpf = rs.getInt("GPF_NO");

                    int JoinId = rs.getInt("JOINING_REPORT_ID");
                    String strNoon = rs.getString("FN_OR_AN");

                    int DesigId = rs.getInt("DESIGNATION_ID");

                    String off_grade = rs.getString("OFFICE_GRADE");

                    int PostId = rs.getInt("POST_COUNTED_ID");

                    String strRemarks = rs.getString("REMARKS");
                    String strProcId = rs.getString("PROCESS_FLOW_STATUS_ID");
                    i++;
                    Date strdt = rs.getDate("DATE_OF_BIRTH");
                    if (strdt == null) {
                        strDob = "0";
                    } else {
                        String[] sd;
                        sd = rs.getDate("DATE_OF_BIRTH").toString().split("-");
                        strDob = sd[2] + "/" + sd[1] + "/" + sd[0];
                    }

                    Date strjdt = rs.getDate("DATE_OF_JOINING");
                    if (strjdt == null) {
                        strJoindt = "0";
                    } else {
                        String[] sd1;
                        sd1 =
 rs.getDate("DATE_OF_JOINING").toString().split("-");
                        strJoindt = sd1[2] + "/" + sd1[1] + "/" + sd1[0];
                    }

                    xml =
 xml + "<Emp_Id>" + strEmpName + "</Emp_Id>" + "<EmpName>" + strEName +
   "</EmpName><EmpGpf>" + strEmpGpf + "</EmpGpf>";
                    xml =
 xml + "<Dtofbirth>" + strDob + "</Dtofbirth>" + "<JoinId>" + JoinId +
   "</JoinId>";
                    xml =
 xml + "<Noon>" + strNoon + "</Noon>" + "<DesigId>" + DesigId +
   "</DesigId><Grade>" + off_grade + "</Grade>";
                    xml =
 xml + "<JDate>" + strJoindt + "</JDate><PostId>" + PostId +
   "</PostId><Remarks>" + strRemarks + "</Remarks><ProcId>" + strProcId +
   "</ProcId>";
                    System.out.println("dest" + DesigId);
                    try {
                        String sql3 =
                            "select a.service_group_id from hrm_mst_service_group a inner join hrm_mst_designations b on b.service_group_id=a.service_group_id where b.designation_id=?";

                        ps1 = connection.prepareStatement(sql3);
                        ps1.setInt(1, DesigId);
                        rs3 = ps1.executeQuery();
                        while (rs3.next()) {
                            int servgrp = rs3.getInt("SERVICE_GROUP_ID");
                            System.out.println("serv" + servgrp);
                            xml =
 xml + "<ServGroup>" + servgrp + "</ServGroup>";
                        }
                    } catch (Exception se) {
                        System.out.println("error in serv grp" + se);
                    }
                }

                if (i == 0) {

                    try {
                        String sql2 =
                            "select * from hrm_mst_employees where employee_id=?";
                        ps2 = connection.prepareStatement(sql2);
                        ps2.setInt(1, Integer.parseInt(strEmpName));

                        rs4 = ps2.executeQuery();
                        int j = 0;
                        while (rs4.next()) {
                            j++;
                            String strEmpPref =
                                rs4.getString("Employee_Prefix");
                            String strEmpInit =
                                rs4.getString("Employee_Initial");
                            strEmpName = rs4.getString("Employee_Name");
                            int strEmpGpf = rs4.getInt("GPF_NO");
                            Date strdt = rs4.getDate("DATE_OF_BIRTH");
                            if (strdt == null) {
                                strDob = "0";
                            } else {
                                String[] sd;
                                sd =
  rs4.getDate("DATE_OF_BIRTH").toString().split("-");
                                strDob = sd[2] + "/" + sd[1] + "/" + sd[0];
                            }
                            xml = sxml + "<flag>NoRecord</flag>";
                            xml =
 xml + "<EmpPref>" + strEmpPref + "</EmpPref><EmpInit>" + strEmpInit +
   "</EmpInit><EmpName>" + strEmpName + "</EmpName><EmpGpf>" + strEmpGpf +
   "</EmpGpf><Dtofbirth>" + strDob + "</Dtofbirth>";
                        }
                        if (j == 0) {
                            xml = sxml + "<flag>NoValue</flag>";
                        }

                    } catch (Exception ae) {
                        System.out.println("Error in the second query" + ae);
                    }

                } else {
                    xml = sxml + "<flag>success</flag>" + xml;
                }

            } catch (Exception e) {
                System.out.println("Exception " + e);
                xml = sxml + "<flag>failure</flag>";
            }
            //}
            xml = xml + "</response>";

        }


        System.out.println("xml is : " + xml);
        pw.write(xml);
        pw.flush();
        pw.close();


    }


    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        System.out.println("Report Generation");
        Connection con = null;
        String Proc_Status = "";
        //System.out.println(xml);
        int txtJRId = 0, txtOffId = 0, txtEmployeeid = 0;

        /* if(request.getParameter("_status")!=null && request.getParameter("_status").equalsIgnoreCase("FR"))
            {
            Proc_Status="FR";
            }
            else
            {
            //Proc_Status=request.getParameter("cmbStatus");
             Proc_Status="CR";
            }
            System.out.println(Proc_Status);*/
        txtJRId = Integer.parseInt(request.getParameter("txtJRId"));
        //System.out.println(xml);
        System.out.println(txtJRId);
        // strCommand=request.getParameter("from here");
        txtOffId = Integer.parseInt(request.getParameter("txtOffId"));
        txtEmployeeid = Integer.parseInt(request.getParameter("txtEmpId1"));

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
        File reportFile = null;
        //String deptid=request.getParameter("txtDept_Id");
        // System.out.println("Dept id:"+deptid);

        //String optbase=request.getParameter("optselect");
        //System.out.println("Option Selected:"+optbase);
        try {
            System.out.println("calling servlet");
            reportFile =
                    new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Joining_Proceeding_Report.jasper"));

            if (!reportFile.exists())
                throw new JRRuntimeException("File J not found. The report design must be compiled first.");

            JasperReport jasperReport =
                (JasperReport)JRLoader.loadObject(reportFile.getPath());
            Map map = null;
            map = new HashMap();
            map.put("jrid", txtJRId);
            map.put("offid", txtOffId);
            JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, map, con);

            String rtype = request.getParameter("RType");
            rtype = "PDF";
            if (rtype.equalsIgnoreCase("HTML")) {
                response.setContentType("text/html");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"ListOfDesignation.html\"");
                PrintWriter out = response.getWriter();
                JRHtmlExporter exporter = new JRHtmlExporter();
                // File f=new File(getServletContext().getRealPath("/WEB-INF/Report/"));
                //  exporter.setParameter(JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR,true);
                //  exporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR_NAME,getServletContext().getRealPath("/WEB-INF/Report/"));
                //  exporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR,f);
                exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,
                                      false);
                exporter.setParameter(JRExporterParameter.JASPER_PRINT,
                                      jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
                exporter.exportReport();
                out.flush();
                out.close();
            } else if (rtype.equalsIgnoreCase("PDF")) {
                byte buf[] =
                    JasperExportManager.exportReportToPdf(jasperPrint);
                response.setContentType("application/pdf");
                response.setContentLength(buf.length);
                // response.setHeader("content-disposition", "inline;filename=OpenActionItems.pdf");
                //response.setContentType("application/force-download");

                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"ListOfDesignation.pdf\"");
                OutputStream outs = response.getOutputStream();
                outs.write(buf, 0, buf.length);
                outs.close();
            } else if (rtype.equalsIgnoreCase("EXCEL")) {

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"ListOfDesignation.xls\"");
                JRXlsExporter exporterXLS = new JRXlsExporter();
                exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT,
                                         jasperPrint);

                ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
                exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,
                                         xlsReport);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
                                         Boolean.FALSE);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE,
                                         Boolean.TRUE);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,
                                         Boolean.FALSE);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
                                         Boolean.TRUE);
                exporterXLS.exportReport();
                byte[] bytes;
                bytes = xlsReport.toByteArray();
                ServletOutputStream ouputStream = response.getOutputStream();
                ouputStream.write(bytes, 0, bytes.length);
                ouputStream.flush();
                ouputStream.close();

            } else if (rtype.equalsIgnoreCase("TXT")) {

                response.setContentType("text/plain");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"ListOfDesignation.txt\"");

                JRTextExporter exporter = new JRTextExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT,
                                      jasperPrint);
                ByteArrayOutputStream txtReport = new ByteArrayOutputStream();
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
                                      txtReport);
                exporter.setParameter(JRTextExporterParameter.CHARACTER_WIDTH,
                                      new Integer(200));
                exporter.setParameter(JRTextExporterParameter.CHARACTER_HEIGHT,
                                      new Integer(50));
                exporter.exportReport();

                byte[] bytes;
                bytes = txtReport.toByteArray();
                ServletOutputStream ouputStream = response.getOutputStream();
                ouputStream.write(bytes, 0, bytes.length);
                ouputStream.flush();
                ouputStream.close();

            }


        } catch (Exception ex) {
            String connectMsg =
                "Could not create the report " + ex.getMessage() + " " +
                ex.getLocalizedMessage();
            System.out.println(connectMsg);
        }

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
