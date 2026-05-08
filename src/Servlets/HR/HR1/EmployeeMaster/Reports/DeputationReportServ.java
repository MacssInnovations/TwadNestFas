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

public class DeputationReportServ extends HttpServlet {
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
        ResultSet rs = null;
        PreparedStatement ps = null;
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
        System.out.println("'Office specific Reporn");
        String xml = "";
        String strCommand = "";

        try {
            strCommand = request.getParameter("Command");
            System.out.println("assign....." + strCommand);

        }

        catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }


        if (strCommand.equalsIgnoreCase("level")) {
            xml = "<response>";
            String olevel = request.getParameter("level");
            try {


                /*  to get the office level  */
                HttpSession session = request.getSession(false);
                UserProfile empProfile =
                    (UserProfile)session.getAttribute("UserProfile");

                System.out.println("user id::" + empProfile.getEmployeeId());
                int empid = empProfile.getEmployeeId();
                int oid = 0;
                String deptid = "", offlevel = "";

                try {

                    ps =
  connection.prepareStatement("select b.OFFICE_LEVEL_ID,a.office_id from hrm_emp_current_posting a " +
                              " inner join com_mst_offices b on b.office_id=a.office_id " +
                              " where a.employee_id=?");
                    ps.setInt(1, empid);
                    ResultSet results = ps.executeQuery();
                    if (results.next()) {
                        offlevel = results.getString("OFFICE_LEVEL_ID");
                        oid = results.getInt("office_id");
                    }
                    results.close();
                    ps.close();
                    /* other office  */
                    String profile = (String)session.getAttribute("profile");
                    if (profile.equalsIgnoreCase("other")) {
                        offlevel = "HO";
                        ps =
  connection.prepareStatement("select office_id from com_mst_offices where  office_level_id=? ");
                        ps.setString(1, offlevel);
                        results = ps.executeQuery();
                        if (results.next()) {
                            oid = results.getInt("office_id");
                        }
                    }
                    /* other office  */
                    System.out.println("office id:" + oid);
                    System.out.println("dept id:" + deptid);
                    System.out.println("offlevel:"+offlevel);

                } catch (Exception e) {
                    System.out.println(e);
                }

                /*  to get the office level  */

                /* find the specific Office */


                try {
                    if (offlevel.equalsIgnoreCase("HO")) {
                        ps =
  connection.prepareStatement("select a.OFFICE_ID,a.OFFICE_NAME,b.OFFICE_LEVEL_NAME,a.OFFICE_ADDRESS1||','||a.OFFICE_ADDRESS2||','||a.CITY_TOWN_NAME ADDRESS " +
                              " from COM_MST_OFFICES a inner join COM_MST_OFFICE_LEVELS b on b.OFFICE_LEVEL_ID = a.OFFICE_LEVEL_ID " +
                              "where a.OFFICE_LEVEL_ID = ? and a.OFFICE_STATUS_ID='CR'order by OFFICE_NAME");
                        ps.setString(1, olevel);
                        rs = ps.executeQuery();
                    } else if (offlevel.equalsIgnoreCase("RN")) {

                        if (olevel.equalsIgnoreCase("RN")) {
                            ps =
  connection.prepareStatement("select a.OFFICE_ID,a.OFFICE_NAME,b.OFFICE_LEVEL_NAME,a.OFFICE_ADDRESS1||','||a.OFFICE_ADDRESS2||','||a.CITY_TOWN_NAME ADDRESS " +
                              " from COM_MST_OFFICES a inner join COM_MST_OFFICE_LEVELS b on b.OFFICE_LEVEL_ID = a.OFFICE_LEVEL_ID " +
                              "where a.OFFICE_LEVEL_ID = ? and a.OFFICE_ID=? order by OFFICE_NAME");
                            ps.setString(1, olevel);
                            ps.setInt(2, oid);
                            rs = ps.executeQuery();
                        } else if (olevel.equalsIgnoreCase("CL")) {
                            ps =
  connection.prepareStatement("select a.OFFICE_ID,a.OFFICE_NAME,b.OFFICE_LEVEL_NAME,a.OFFICE_ADDRESS1||','||a.OFFICE_ADDRESS2||','||a.CITY_TOWN_NAME ADDRESS " +
                              " from COM_MST_OFFICES a inner join COM_MST_OFFICE_LEVELS b on b.OFFICE_LEVEL_ID = a.OFFICE_LEVEL_ID " +
                              "where a.OFFICE_LEVEL_ID = ? and a.OFFICE_ID in (select office_id from com_office_control where controlling_office_id =?)  order by OFFICE_NAME");
                            ps.setString(1, olevel);
                            ps.setInt(2, oid);
                            rs = ps.executeQuery();
                        } else if (olevel.equalsIgnoreCase("DN")) {
                            ps =
  connection.prepareStatement("select a.OFFICE_ID,a.OFFICE_NAME,b.OFFICE_LEVEL_NAME,a.OFFICE_ADDRESS1||','||a.OFFICE_ADDRESS2||','||a.CITY_TOWN_NAME ADDRESS " +
                              " from COM_MST_OFFICES a inner join COM_MST_OFFICE_LEVELS b on b.OFFICE_LEVEL_ID = a.OFFICE_LEVEL_ID " +
                              "where a.OFFICE_LEVEL_ID = ? and a.OFFICE_ID in (select office_id from com_office_control where controlling_office_id in (select office_id from com_office_control where controlling_office_id =?)) order by OFFICE_NAME");
                            ps.setString(1, olevel);
                            ps.setInt(2, oid);
                            rs = ps.executeQuery();
                        }

                    } else if (offlevel.equalsIgnoreCase("CL")) {
                        if (olevel.equalsIgnoreCase("CL")) {
                            ps =
  connection.prepareStatement("select a.OFFICE_ID,a.OFFICE_NAME,b.OFFICE_LEVEL_NAME,a.OFFICE_ADDRESS1||','||a.OFFICE_ADDRESS2||','||a.CITY_TOWN_NAME ADDRESS " +
                              " from COM_MST_OFFICES a inner join COM_MST_OFFICE_LEVELS b on b.OFFICE_LEVEL_ID = a.OFFICE_LEVEL_ID " +
                              "where a.OFFICE_LEVEL_ID = ? and a.OFFICE_ID=? order by OFFICE_NAME");
                            ps.setString(1, olevel);
                            ps.setInt(2, oid);
                            rs = ps.executeQuery();
                        } else if (olevel.equalsIgnoreCase("DN")) {
                            ps =
  connection.prepareStatement("select a.OFFICE_ID,a.OFFICE_NAME,b.OFFICE_LEVEL_NAME,a.OFFICE_ADDRESS1||','||a.OFFICE_ADDRESS2||','||a.CITY_TOWN_NAME ADDRESS " +
                              " from COM_MST_OFFICES a inner join COM_MST_OFFICE_LEVELS b on b.OFFICE_LEVEL_ID = a.OFFICE_LEVEL_ID " +
                              "where a.OFFICE_LEVEL_ID = ? and a.OFFICE_ID in (select office_id from com_office_control where controlling_office_id =?) order by OFFICE_NAME");
                            ps.setString(1, olevel);
                            ps.setInt(2, oid);
                            rs = ps.executeQuery();
                        }
                    } else if (offlevel.equalsIgnoreCase("DN")) {
                        if (olevel.equalsIgnoreCase("DN")) {
                            ps =
  connection.prepareStatement("select a.OFFICE_ID,a.OFFICE_NAME,b.OFFICE_LEVEL_NAME,a.OFFICE_ADDRESS1||','||a.OFFICE_ADDRESS2||','||a.CITY_TOWN_NAME ADDRESS " +
                              " from COM_MST_OFFICES a inner join COM_MST_OFFICE_LEVELS b on b.OFFICE_LEVEL_ID = a.OFFICE_LEVEL_ID " +
                              "where a.OFFICE_LEVEL_ID = ? and a.OFFICE_ID=? order by OFFICE_NAME");
                            ps.setString(1, olevel);
                            ps.setInt(2, oid);
                            rs = ps.executeQuery();
                        }
                    }
                } catch (Exception e) {
                    System.out.println("error in find specific region :" + e);
                }


                System.out.println("region office Id:" + oid);

                /* end of find the specific office  region*/


                /* ps=connection.prepareStatement("select a.OFFICE_ID,a.OFFICE_NAME,b.OFFICE_LEVEL_NAME,a.OFFICE_ADDRESS1||','||a.OFFICE_ADDRESS2||','||a.CITY_TOWN_NAME ADDRESS "+
                " from COM_MST_OFFICES a inner join COM_MST_OFFICE_LEVELS b on b.OFFICE_LEVEL_ID = a.OFFICE_LEVEL_ID " +
                "where a.OFFICE_LEVEL_ID = ? order by OFFICE_NAME" );
                ps.setString(1,olevel);
                rs=ps.executeQuery();
            */
                int count = 0;

                while (rs.next()) {
                    xml = xml + "<office>";
                    xml =
 xml + "<offid>" + rs.getInt("OFFICE_ID") + "</offid>";
                    xml =
 xml + "<offname>" + rs.getString("OFFICE_NAME") + "</offname>";
                    xml =
 xml + "<offlevel>" + rs.getString("OFFICE_LEVEL_NAME") + "</offlevel>";
                    xml =
 xml + "<offaddress>" + rs.getString("ADDRESS") + "</offaddress>";
                    xml = xml + "</office>";
                    count++;

                }
                if (count == 0) {
                    xml = "<response><flag>failure</flag>";
                } else {
                    xml = xml + "<flag>success</flag>";
                }

            }

            catch (Exception e) {

                System.out.println("catch........" + e);
                // e.printStackTrace();

                xml = "<response><flag>failure</flag>";
            }

            xml = xml + "</response>";


        } else if (strCommand.equalsIgnoreCase("own")) {
            xml = "<response>";
            String olevel = request.getParameter("level");
            String own = request.getParameter("own");
            try {


                /*  to get the office level  */
                HttpSession session = request.getSession(false);
                UserProfile empProfile =
                    (UserProfile)session.getAttribute("UserProfile");

                System.out.println("user id::" + empProfile.getEmployeeId());
                int empid = empProfile.getEmployeeId();
                int oid = 0;
                String deptid = "", offlevel = "";

                try {

                    ps =
  connection.prepareStatement("select b.OFFICE_LEVEL_ID,a.office_id from hrm_emp_current_posting a " +
                              " inner join com_mst_offices b on b.office_id=a.office_id " +
                              " where a.employee_id=?");
                    ps.setInt(1, empid);
                    ResultSet results = ps.executeQuery();
                    if (results.next()) {
                        offlevel = results.getString("OFFICE_LEVEL_ID");
                        oid = results.getInt("office_id");
                    }
                    results.close();
                    ps.close();
                    /* other office  */
                    String profile = (String)session.getAttribute("profile");
                    if (profile.equalsIgnoreCase("other")) {
                        offlevel = "HO";
                        ps =
  connection.prepareStatement("select office_id from com_mst_offices where  office_level_id=? ");
                        ps.setString(1, offlevel);
                        results = ps.executeQuery();
                        if (results.next()) {
                            oid = results.getInt("office_id");
                        }
                    }
                    /* other office  */
                    System.out.println("office id:" + oid);
                    System.out.println("dept id:" + deptid);

                } catch (Exception e) {
                    System.out.println(e);
                }

                /*  to get the office level  */

                /* find the specific Office */


                try {
                    if (offlevel.equalsIgnoreCase("HO")) {
                        ps =
  connection.prepareStatement("select a.OFFICE_ID,a.OFFICE_NAME,b.OFFICE_LEVEL_NAME,a.OFFICE_ADDRESS1||','||a.OFFICE_ADDRESS2||','||a.CITY_TOWN_NAME ADDRESS " +
                              " from COM_MST_OFFICES a inner join COM_MST_OFFICE_LEVELS b on b.OFFICE_LEVEL_ID = a.OFFICE_LEVEL_ID " +
                              "where a.OFFICE_LEVEL_ID = ? and a.PRIMARY_WORK_ID = ?  order by OFFICE_NAME");
                        ps.setString(1, olevel);
                        ps.setString(2, own);
                        rs = ps.executeQuery();
                    } else if (offlevel.equalsIgnoreCase("RN")) {

                        if (olevel.equalsIgnoreCase("RN")) {
                            ps =
  connection.prepareStatement("select a.OFFICE_ID,a.OFFICE_NAME,b.OFFICE_LEVEL_NAME,a.OFFICE_ADDRESS1||','||a.OFFICE_ADDRESS2||','||a.CITY_TOWN_NAME ADDRESS " +
                              " from COM_MST_OFFICES a inner join COM_MST_OFFICE_LEVELS b on b.OFFICE_LEVEL_ID = a.OFFICE_LEVEL_ID " +
                              "where a.OFFICE_LEVEL_ID = ? and a.OFFICE_ID=? and a.PRIMARY_WORK_ID = ? order by OFFICE_NAME");
                            ps.setString(1, olevel);
                            ps.setInt(2, oid);
                            ps.setString(3, own);
                            rs = ps.executeQuery();
                        } else if (olevel.equalsIgnoreCase("CL")) {
                            ps =
  connection.prepareStatement("select a.OFFICE_ID,a.OFFICE_NAME,b.OFFICE_LEVEL_NAME,a.OFFICE_ADDRESS1||','||a.OFFICE_ADDRESS2||','||a.CITY_TOWN_NAME ADDRESS " +
                              " from COM_MST_OFFICES a inner join COM_MST_OFFICE_LEVELS b on b.OFFICE_LEVEL_ID = a.OFFICE_LEVEL_ID " +
                              "where a.OFFICE_LEVEL_ID = ? and a.OFFICE_ID in (select office_id from com_office_control where controlling_office_id =?)  " +
                              " and a.PRIMARY_WORK_ID = ? order by OFFICE_NAME");
                            ps.setString(1, olevel);
                            ps.setInt(2, oid);
                            ps.setString(3, own);
                            rs = ps.executeQuery();
                        } else if (olevel.equalsIgnoreCase("DN")) {
                            ps =
  connection.prepareStatement("select a.OFFICE_ID,a.OFFICE_NAME,b.OFFICE_LEVEL_NAME,a.OFFICE_ADDRESS1||','||a.OFFICE_ADDRESS2||','||a.CITY_TOWN_NAME ADDRESS " +
                              " from COM_MST_OFFICES a inner join COM_MST_OFFICE_LEVELS b on b.OFFICE_LEVEL_ID = a.OFFICE_LEVEL_ID " +
                              "where a.OFFICE_LEVEL_ID = ? and a.OFFICE_ID in (select office_id from com_office_control where controlling_office_id in (select office_id from com_office_control where controlling_office_id =?)) " +
                              " and a.PRIMARY_WORK_ID = ? order by OFFICE_NAME");
                            ps.setString(1, olevel);
                            ps.setInt(2, oid);
                            ps.setString(3, own);
                            rs = ps.executeQuery();
                        }

                    } else if (offlevel.equalsIgnoreCase("CL")) {
                        if (olevel.equalsIgnoreCase("CL")) {
                            ps =
  connection.prepareStatement("select a.OFFICE_ID,a.OFFICE_NAME,b.OFFICE_LEVEL_NAME,a.OFFICE_ADDRESS1||','||a.OFFICE_ADDRESS2||','||a.CITY_TOWN_NAME ADDRESS " +
                              " from COM_MST_OFFICES a inner join COM_MST_OFFICE_LEVELS b on b.OFFICE_LEVEL_ID = a.OFFICE_LEVEL_ID " +
                              "where a.OFFICE_LEVEL_ID = ? and a.OFFICE_ID=? and a.PRIMARY_WORK_ID = ? order by OFFICE_NAME");
                            ps.setString(1, olevel);
                            ps.setInt(2, oid);
                            ps.setString(3, own);
                            rs = ps.executeQuery();
                        } else if (olevel.equalsIgnoreCase("DN")) {
                            ps =
  connection.prepareStatement("select a.OFFICE_ID,a.OFFICE_NAME,b.OFFICE_LEVEL_NAME,a.OFFICE_ADDRESS1||','||a.OFFICE_ADDRESS2||','||a.CITY_TOWN_NAME ADDRESS " +
                              " from COM_MST_OFFICES a inner join COM_MST_OFFICE_LEVELS b on b.OFFICE_LEVEL_ID = a.OFFICE_LEVEL_ID " +
                              "where a.OFFICE_LEVEL_ID = ? and a.OFFICE_ID in (select office_id from com_office_control where controlling_office_id =?)" +
                              " and a.PRIMARY_WORK_ID = ?  order by OFFICE_NAME");
                            ps.setString(1, olevel);
                            ps.setInt(2, oid);
                            ps.setString(3, own);
                            rs = ps.executeQuery();
                        }
                    } else if (offlevel.equalsIgnoreCase("DN")) {
                        if (olevel.equalsIgnoreCase("DN")) {
                            ps =
  connection.prepareStatement("select a.OFFICE_ID,a.OFFICE_NAME,b.OFFICE_LEVEL_NAME,a.OFFICE_ADDRESS1||','||a.OFFICE_ADDRESS2||','||a.CITY_TOWN_NAME ADDRESS " +
                              " from COM_MST_OFFICES a inner join COM_MST_OFFICE_LEVELS b on b.OFFICE_LEVEL_ID = a.OFFICE_LEVEL_ID " +
                              "where a.OFFICE_LEVEL_ID = ? and a.OFFICE_ID=? and a.PRIMARY_WORK_ID = ?  order by OFFICE_NAME");
                            ps.setString(1, olevel);
                            ps.setInt(2, oid);
                            ps.setString(3, own);
                            rs = ps.executeQuery();
                        }
                    }
                } catch (Exception e) {
                    System.out.println("error in find specific region :" + e);
                }


                System.out.println("region office Id:" + oid);

                /* end of find the specific office  region*/


                /* ps=connection.prepareStatement("select a.OFFICE_ID,a.OFFICE_NAME,b.OFFICE_LEVEL_NAME,a.OFFICE_ADDRESS1||','||a.OFFICE_ADDRESS2||','||a.CITY_TOWN_NAME ADDRESS "+
                " from COM_MST_OFFICES a inner join COM_MST_OFFICE_LEVELS b on b.OFFICE_LEVEL_ID = a.OFFICE_LEVEL_ID " +
                " where a.OFFICE_LEVEL_ID = ? and a.PRIMARY_WORK_ID = ? order by OFFICE_NAME" );

                ps.setString(1,olevel);
                ps.setString(2,own);
                rs=ps.executeQuery();

                */
                int count = 0;

                while (rs.next()) {
                    xml = xml + "<office>";
                    xml =
 xml + "<offid>" + rs.getInt("OFFICE_ID") + "</offid>";
                    xml =
 xml + "<offname>" + rs.getString("OFFICE_NAME") + "</offname>";
                    xml =
 xml + "<offlevel>" + rs.getString("OFFICE_LEVEL_NAME") + "</offlevel>";
                    xml =
 xml + "<offaddress>" + rs.getString("ADDRESS") + "</offaddress>";
                    xml = xml + "</office>";
                    count++;

                }
                if (count == 0) {
                    xml = "<response><flag>failure</flag>";
                } else {
                    xml = xml + "<flag>success</flag>";
                }

            }

            catch (Exception e) {

                System.out.println("catch........" + e);
                // e.printStackTrace();

                xml = "<response><flag>failure</flag>";
            }

            xml = xml + "</response>";


        }

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

        //String[] val=request.getParameterValues("sel");
        String s = request.getParameter("txtseloff");
        /*  for(int i=0;i<val.length;i++)
                      {
                           s=s+val[i];
                          if(i<val.length-1)
                              s=s+",";

                      }*/
        System.out.println(s);

        // JasperDesign jasperDesign = null;
        File reportFile = null;
        try {
            System.out.println("calling servlet");
            reportFile =
                    new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/DeputationdetailReport.jasper"));
            if (!reportFile.exists())
                throw new JRRuntimeException("File J not found. The report design must be compiled first.");

            JasperReport jasperReport =
                (JasperReport)JRLoader.loadObject(reportFile.getPath());
            Map map = null;
            map = new HashMap();
            map.put("txtofficeid", s);
            int mon = Integer.parseInt(request.getParameter("cmbmonth"));
            int year = Integer.parseInt(request.getParameter("cmbyear"));
            map.put("mon", mon);
            map.put("yea", year);

            // map.put("offids",5001);
            JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, map, connection);

            String rtype = request.getParameter("cmbReportType");
            if (rtype.equalsIgnoreCase("HTML")) {
                response.setContentType("text/html");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"OfficeSpecificDetail.html\"");
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
                                   "attachment;filename=\"OfficeSpecificDetail.pdf\"");
                OutputStream out = response.getOutputStream();
                out.write(buf, 0, buf.length);
                out.close();
            } else if (rtype.equalsIgnoreCase("EXCEL")) {

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"OfficeSpecificDetail.xls\"");
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
                                   "attachment;filename=\"OfficeDetail.txt\"");

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
