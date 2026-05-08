package Servlets.HR.HR1.EmployeeMaster.Reports;

import Servlets.Security.classes.UserProfile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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


public class EmployeeProfileServSR_NoPhoto_New extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";


    public void init(ServletConfig config) throws ServletException {
        super.init(config);

    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);

        Connection connection = null;

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

            ConnectionString =
                    strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
                    ":" + strsid.trim();

            Class.forName(strDriver.trim());
            connection =
                    DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                                                strdbpassword.trim());
        } catch (Exception e) {
            System.out.println("Exception in connection..." + e);
        }


        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");

            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }
        PrintWriter out = response.getWriter();
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");
        System.out.println("'Office specific Report");
        String xml = "";
        String strCommand = "";

        try {
            strCommand = request.getParameter("Command");
            System.out.println("assign....." + strCommand);

        }

        catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }

        HttpSession session = request.getSession(false);
        ResultSet rs1 = null;
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        PreparedStatement ps = null;

        int offid1 = 0;
        int user_offid = 0;
        int emp_offid = 0;
        String off_level = "";
        String emp_statid = "";

        UserProfile up = null;
        up = (UserProfile)session.getAttribute("UserProfile");


        String profile = (String)session.getAttribute("profile");


        if (strCommand.equalsIgnoreCase("Existg")) {
            System.out.println("------------");


            int strEmpId = Integer.parseInt(request.getParameter("EmpId"));
            System.out.println("EmpId..." + strEmpId);

            xml = "<response><command>Existg</command>";
            System.out.println(xml);

            try {

                System.out.println("inside try");
                System.out.println("employee id..." + strEmpId);
                System.out.println("123");

                /*
                UserProfile up=null;
                up=(UserProfile)session.getAttribute("UserProfile");   */
                System.out.println("user login id..." + up.getEmployeeId());
                boolean flag = true;
                System.out.println("flag");


                try {
                    ps =
  connection.prepareStatement("SELECT EMPLOYEE_ID FROM HRM_MST_EMPLOYEES WHERE EMPLOYEE_ID=?");
                    ps.setInt(1, strEmpId);
                    rs = ps.executeQuery();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                if (!rs.next()) {

                    xml = xml + "<flag>failure</flag>";
                    flag = false;

                }


                else if (up.getEmployeeId() != strEmpId) {
                    System.out.println("inside these not coming");
                    int OfficeId = 0;
                    String sql = "";
                    /*
                   String sql="select CONTROLLING_OFFICE_ID from HRM_EMP_CONTROLLING_OFFICE where employee_id=?";
                   System.out.println("sql..."+sql);
                   ps=connection.prepareStatement(sql);
                   ps.setInt(1,strEmpId);*/


                    sql =
 "select office_id,employee_status_id from hrm_emp_current_posting where employee_id=?";
                    ps = connection.prepareStatement(sql);
                    ps.setInt(1, strEmpId);
                    rs = ps.executeQuery();

                    if (rs.next()) {
                        emp_offid = rs.getInt("OFFICE_ID");
                        emp_statid = rs.getString("employee_status_id");
                        System.out.println("employee office id..." +
                                           emp_offid);
                        System.out.println("employee status id..." +
                                           emp_statid);
                    }

                    if (emp_statid.equalsIgnoreCase("TRT") ||
                        emp_statid.equalsIgnoreCase("DPN")) {
                        xml = xml + "<flag>success</flag>";
                        flag = true;
                    }

                    else if (emp_statid.equalsIgnoreCase("WKG")) {

                        sql =
 "select office_id from hrm_emp_current_posting where employee_id=?";
                        ps = connection.prepareStatement(sql);
                        ps.setInt(1, up.getEmployeeId());
                        rs = ps.executeQuery();

                        if (rs.next()) {
                            user_offid = rs.getInt("OFFICE_ID");
                            System.out.println("user office id..." +
                                               user_offid);

                            sql =
 "select office_level_id from com_mst_all_offices_view where office_id=?";
                            ps = connection.prepareStatement(sql);
                            ps.setInt(1, user_offid);
                            rs = ps.executeQuery();

                            if (rs.next()) {
                                off_level = rs.getString("office_level_id");
                                System.out.println("user office level id..." +
                                                   off_level);
                            }

                            if (off_level.equalsIgnoreCase("HO") ||
                                profile.equalsIgnoreCase("others")) {
                                System.out.println("inside head office");

                                sql =
 "select distinct OFFICE_ID,off_order,office_level_id from COM_MST_ALL_OFFICES_VIEW " +
   " where office_id=?";

                                ps = connection.prepareStatement(sql);
                                ps.setInt(1, emp_offid);
                                rs = ps.executeQuery();

                                if (rs.next()) {
                                    xml = xml + "<flag>success</flag>";
                                    flag = true;
                                } else {
                                    xml = xml + "<flag>failurea</flag>";
                                    flag = false;

                                }


                            }

                            else if (off_level.equalsIgnoreCase("RN")) {
                                System.out.println("inside region");

                                sql =
 "select distinct OFFICE_ID,off_order,office_level_id,office_name from COM_MST_ALL_OFFICES_VIEW " +
   " where OFFICE_LEVEL_ID in ('RN','CL','DN','SD','AW','LB') " +
   " and region_office_id=? and office_id=?";

                                ps = connection.prepareStatement(sql);
                                ps.setInt(1, user_offid);
                                ps.setInt(2, emp_offid);
                                rs = ps.executeQuery();

                                if (rs.next()) {
                                    xml = xml + "<flag>success</flag>";
                                    flag = true;
                                } else {
                                    xml = xml + "<flag>failurea</flag>";
                                    flag = false;

                                }
                            }

                            else if (off_level.equalsIgnoreCase("CL")) {
                                System.out.println("inside circle");
                                sql =
 "select distinct OFFICE_ID,off_order,office_level_id,office_name from COM_MST_ALL_OFFICES_VIEW " +
   " where OFFICE_LEVEL_ID in ('CL','DN','SD','LB') " +
   " and circle_office_id=? and office_id=?";

                                ps = connection.prepareStatement(sql);
                                ps.setInt(1, user_offid);
                                ps.setInt(2, emp_offid);
                                rs = ps.executeQuery();

                                if (rs.next()) {
                                    xml = xml + "<flag>success</flag>";
                                    flag = true;
                                } else {
                                    xml = xml + "<flag>failurea</flag>";
                                    flag = false;

                                }
                            }

                            else if (off_level.equalsIgnoreCase("DN")) {
                                System.out.println("inside division");

                                sql =
 "select distinct OFFICE_ID,off_order,office_level_id,office_name from COM_MST_ALL_OFFICES_VIEW " +
   " where OFFICE_LEVEL_ID in ('DN','SD') " +
   " and division_office_id=? and office_id=?";

                                ps = connection.prepareStatement(sql);
                                ps.setInt(1, user_offid);
                                ps.setInt(2, emp_offid);
                                rs = ps.executeQuery();

                                if (rs.next()) {
                                    xml = xml + "<flag>success</flag>";
                                    flag = true;
                                } else {
                                    xml = xml + "<flag>failurea</flag>";
                                    flag = false;

                                }
                            }

                            else if (off_level.equalsIgnoreCase("SD")) {
                                System.out.println("inside subdivision");

                                sql =
 "select distinct OFFICE_ID,off_order,office_level_id,office_name from COM_MST_ALL_OFFICES_VIEW " +
   " where OFFICE_LEVEL_ID in ('SD') " +
   " and subdivision_office_id=? and office_id=?";

                                ps = connection.prepareStatement(sql);
                                ps.setInt(1, user_offid);
                                ps.setInt(2, emp_offid);
                                rs = ps.executeQuery();

                                if (rs.next()) {
                                    xml = xml + "<flag>success</flag>";
                                    flag = true;
                                } else {
                                    xml = xml + "<flag>failurea</flag>";
                                    flag = false;

                                }
                            }


                        }

                    }


                    //out of these
                    /*
                    sql="select OFFICE_ID  from HRM_EMP_CURRENT_POSTING where employee_id=?";
                    ps=connection.prepareStatement(sql);
                    ps.setInt(1,up.getEmployeeId());
                    rs=ps.executeQuery();

                   if(rs.next())
                    {
                       OfficeId=rs.getInt("OFFICE_ID");
                       System.out.println("User office id..."+OfficeId);
                    }
                    else
                    {

                        xml=xml+"<flag>success</flag>";
                        flag=true;
                        /*
                        xml=xml+"<flag>failureb</flag>";
                        flag=false;*/
                    //}

                    /*

                  if(OfficeId!=0)
                  {
                    /*
                    sql="select OFFICE_ID  from HRM_EMP_CURRENT_POSTING where employee_id=?";
                    ps=connection.prepareStatement(sql);
                    ps.setInt(1,up.getEmployeeId());*/
                    /*
                    sql="select CONTROLLING_OFFICE_ID from HRM_EMP_CONTROLLING_OFFICE where employee_id=?";
                    System.out.println("sql..."+sql);
                    ps=connection.prepareStatement(sql);
                    ps.setInt(1,strEmpId);

                    rs=ps.executeQuery();

                    if(rs.next())
                    {
                       offid1=rs.getInt("CONTROLLING_OFFICE_ID");
                       System.out.println("Employee controlling office id...."+offid1);

                       if(offid1==OfficeId)
                       {
                         System.out.println("inside equal office");
                        xml=xml+"<flag>success</flag>";
                        flag=true;
                       }*/
                    /*
                       else if(OfficeId==5000)
                       {
                           System.out.println("inside 5000");
                           xml=xml+"<flag>success</flag>";
                           flag=true;
                       }*/
                    /*
                       else
                       {
                           xml=xml+"<flag>success</flag>";
                           flag=true;

                          /*
                           xml=xml+"<flag>failureb</flag>";
                           flag=false;*/
                    /*
                       }
                    }
                    else
                    {
                        if(offid1==OfficeId)
                        {
                          System.out.println("inside equal office");
                         xml=xml+"<flag>success</flag>";
                         flag=true;
                        }
                        */
                    /*
                        else if(OfficeId==5000)
                         {
                           System.out.println("inside offid1");
                            xml=xml+"<flag>success</flag>";
                           flag=true;
                         }*/
                    /*
                         else
                         {
                             xml=xml+"<flag>success</flag>";
                             flag=true;
                           /*
                             xml=xml+"<flag>failureb</flag>";
                             flag=false;*/
                    /*
                         }
                    }
                }
                else
                {
                    xml=xml+"<flag>success</flag>";
                    flag=true;
                }*/
                    /*
                      if(offid1!=OfficeId)
                      {
                                               //response.sendRedirect(request.getContextPath()+"/org/Library/jsps/Messenger.jsp?message=" + "Can not see profile. Because Employee Id "+strEmpId+" is not under your Office!");
                      xml=xml+"<flag>failurea</flag>";
                      flag=false;
                      }

                      else  if(OfficeId==5000)
                       {
                         System.out.println("inside offid1");
                          xml=xml+"<flag>success</flag>";
                         flag=true;
                       }
                    */


                    /*
                 else  if(OfficeId==5000)
                  {
                    System.out.println("inside offid1");
                     xml=xml+"<flag>success</flag>";
                    flag=true;
                  }*/

                    /*   else
                       {

                          if(offid1!=OfficeId)
                          {
                                                   //response.sendRedirect(request.getContextPath()+"/org/Library/jsps/Messenger.jsp?message=" + "Can not see profile. Because Employee Id "+strEmpId+" is not under your Office!");
                         xml=xml+"<flag>failurea</flag>";
                         flag=false;
                          }
                       }*/
                }


                if (flag) {

                    System.out.println("inside flag");

                    try {


                        ps =
  connection.prepareStatement("select employee_id, employee_name, gpf_no,EMP_PHOTO_FILE_NAME," +
                              " PROCESS_FLOW_STATUS_ID from" + " ( " +
                              " select employee_id, decode(employee_initial,null,' ',employee_initial||'.')||employee_name employee_name, gpf_no from hrm_mst_employees" +
                              " where employee_id=?" + "  ) a" +
                              " left outer join" + " ( " +
                              " select employee_id as emp_id, EMP_PHOTO_FILE_NAME," +
                              " PROCESS_FLOW_STATUS_ID from HRM_EMP_ADDL_PHOTO_TMP " +
                              "  ) b " + "  on a.employee_id = b.emp_id");


                        ps.setInt(1, strEmpId);
                        rs = ps.executeQuery();


                        if (rs.next()) {
                            System.out.println("inside employee details");
                            String strEmpName = rs.getString("Employee_Name");
                            int strEmpGpf = rs.getInt("GPF_NO");
                            xml = xml + "<flag>success</flag>";
                            xml =
 xml + "<EmpName>" + strEmpName + "</EmpName><EmpGpf>" + strEmpGpf +
   "</EmpGpf>";
                            xml =
 xml + "<status>" + (rs.getString("PROCESS_FLOW_STATUS_ID")) + "</status>";
                        } else {
                            xml = xml + "<flag>failure</flag>";
                        }

                    } catch (Exception aee) {
                        System.out.println("Exception in the getting values IN get : " +
                                           aee);
                        xml = xml + "<flag>failure</flag>";
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }


        }
        xml = xml + "</response>";

        out.println(xml);
        System.out.println(xml);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        System.out.println("hello");
        Connection connection = null;
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

            ConnectionString =
                    strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
                    ":" + strsid.trim();

            Class.forName(strDriver.trim());
            connection =
                    DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                                                strdbpassword.trim());
        } catch (Exception e) {
            System.out.println("Exception in connection..." + e);
        }
        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");

            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }


        //  String s=request.getParameter("txtseloff");

        //   System.out.println(s);
        //String[] val=request.getParameterValues("sel");
        //String s=request.getParameter("txtseloff");
        /*  for(int i=0;i<val.length;i++)
                      {
                           s=s+val[i];
                          if(i<val.length-1)
                              s=s+",";

                      }*/
        //  System.out.println(s);

        // JasperDesign jasperDesign = null;
        File reportFile = null;
        // Map map=null;
        //String deptid=request.getParameter("txtDept_Id");
        // System.out.println("Dept id:"+deptid);

        String optbase = request.getParameter("cmbReportType");
        System.out.println("Option Selected:" + optbase);
        try {
            System.out.println("calling servlet");

            reportFile =
                    new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Retiirement_Employee_Profile1_SR_New_NoPhoto.jasper"));

            System.out.println(reportFile.getAbsolutePath());
            //    map=new HashMap();
            String path =
                getServletContext().getRealPath("/WEB-INF/ReportSrc") +
                File.separator;
            System.out.println("path=" + path);

            // }
            // reportFile = new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/RelievalTransferDetails.jasper"));
            if (!reportFile.exists())
                throw new JRRuntimeException("File J not found. The report design must be compiled first.");

            JasperReport jasperReport =
                (JasperReport)JRLoader.loadObject(reportFile.getPath());
            Map map = null;
            map = new HashMap();
            map.put("SUBREPORT_DIR", path);

            /*  get the values  */
            System.out.println("test1");
            int empid = Integer.parseInt(request.getParameter("txtEmpId1"));
            System.out.println("empid :" + empid);
            String ppath = request.getParameter("phopath");

            /* if(ppath!=)
               {}*/

            System.out.println("photo path in report" + ppath);
            map.put("emp_id", empid);
            map.put("ppath", ppath);

            JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, map, connection);

            String rtype = request.getParameter("cmbReportType");
            if (rtype.equalsIgnoreCase("HTML")) {
                response.setContentType("text/html");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"Employee_Profile1_SR_New.html\"");
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
                                   "attachment;filename=\"Employee_Profile1_SR_New.pdf\"");
                OutputStream out = response.getOutputStream();
                out.write(buf, 0, buf.length);
                out.close();
            } else if (rtype.equalsIgnoreCase("EXCEL")) {

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"Employee_Profile1_SR_New.xls\"");
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
                                   "attachment;filename=\"Employee_Profile1_SR_New.txt\"");

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


}
