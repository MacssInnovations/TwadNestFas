package Servlets.HR.HR1.EmployeeMaster.Reports;

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


public class ListofRolesMenus extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";


    public void init(ServletConfig config) throws ServletException {
        super.init(config);


    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
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

            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection

            Class.forName(strDriver.trim());
            connection =
                    DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                                                strdbpassword.trim());
        } catch (Exception ex) {
            String connectMsg =
                "Could not create the connection" + ex.getMessage() + " " +
                ex.getLocalizedMessage();
            System.out.println(connectMsg);
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


        String cmblevel = "";
        String optview = "";
        String optdetail = "";
        String office = "";


        response.setContentType(CONTENT_TYPE);

        //JasperDesign jasperDesign = null;
        File reportFile = null;

        /*  list all user */
        try {
            System.out.println("list of all user in post:" +
                               request.getParameter("EmpSingleParam"));
            if (request.getParameter("EmpSingleParam") != null) {
                if (request.getParameter("EmpSingleParam").equalsIgnoreCase("ListOfRoles")) {
                    reportFile =
                            new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/ListofRoleMenus.jasper"));

                    if (!reportFile.exists())
                        throw new JRRuntimeException("File J not found. The report design must be compiled first.");

                    JasperReport jasperReport =
                        (JasperReport)JRLoader.loadObject(reportFile.getPath());
                    Map map = null;
                    JasperPrint jasperPrint =
                        JasperFillManager.fillReport(jasperReport, map,
                                                     connection);
                    byte buf[] =
                        JasperExportManager.exportReportToPdf(jasperPrint);
                    response.setContentType("application/pdf");
                    response.setContentLength(buf.length);
                    // response.setHeader("content-disposition", "inline;filename=OpenActionItems.pdf");
                    //response.setContentType("application/force-download");

                    //response.setHeader("Content-Disposition", "attachment;filename=\"OfficeDetail.pdf\"");
                    OutputStream out = response.getOutputStream();
                    out.write(buf, 0, buf.length);
                    out.close();
                    return;

                } else if (request.getParameter("EmpSingleParam").equalsIgnoreCase("EmpWait4Post")) {
                    reportFile =
                            new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/ListofRoleMenus.jasper"));

                    if (!reportFile.exists())
                        throw new JRRuntimeException("File J not found. The report design must be compiled first.");

                    JasperReport jasperReport =
                        (JasperReport)JRLoader.loadObject(reportFile.getPath());
                    Map map = null;
                    JasperPrint jasperPrint =
                        JasperFillManager.fillReport(jasperReport, map,
                                                     connection);
                    byte buf[] =
                        JasperExportManager.exportReportToPdf(jasperPrint);
                    response.setContentType("application/pdf");
                    response.setContentLength(buf.length);
                    // response.setHeader("content-disposition", "inline;filename=OpenActionItems.pdf");
                    //response.setContentType("application/force-download");
                    //response.setHeader("Content-Disposition", "attachment;filename=\"OfficeDetail.pdf\"");
                    OutputStream out = response.getOutputStream();
                    out.write(buf, 0, buf.length);
                    out.close();
                    return;
                }

            }

        } catch (Exception e) {
            System.out.println("Error in List of all users:" + e);
        }

        try {
            System.out.println("calling Employee Detail servlet");

            cmblevel = request.getParameter("cmbolevel");
            optview = request.getParameter("optview");
            optdetail = request.getParameter("optdetail");


            if (optview.equalsIgnoreCase("ALL") &&
                optdetail.equalsIgnoreCase("SUMMARY")) {

                if (cmblevel.equalsIgnoreCase("HO")) {
                    reportFile =
                            new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/EmployeeDetailHOWiseAbs.jasper"));
                } else if (cmblevel.equalsIgnoreCase("RN")) {
                    reportFile =
                            new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/EmployeeDetailRegionWiseAbs.jasper"));
                } else if (cmblevel.equalsIgnoreCase("CL")) {
                    reportFile =
                            new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/EmployeeDetailCircleWiseAbs.jasper"));
                } else if (cmblevel.equalsIgnoreCase("DN")) {
                    reportFile =
                            new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/EmployeeDetailDivisionWiseAbs.jasper"));
                }


            } else if (optview.equalsIgnoreCase("ALL") &&
                       optdetail.equalsIgnoreCase("DETAIL")) {

                if (cmblevel.equalsIgnoreCase("HO")) {
                    reportFile =
                            new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/EmployeeDetailHOWiseDetail.jasper"));
                } else if (cmblevel.equalsIgnoreCase("RN")) {
                    reportFile =
                            new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/EmployeeDetailRegionWiseDetail.jasper"));
                } else if (cmblevel.equalsIgnoreCase("CL")) {
                    reportFile =
                            new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/EmployeeDetailCircleWiseDetail.jasper"));
                } else if (cmblevel.equalsIgnoreCase("DN")) {
                    reportFile =
                            new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/EmployeeDetailDivisionWiseDetail.jasper"));
                }
            } else if (optview.equalsIgnoreCase("SPECIFIC") &&
                       optdetail.equalsIgnoreCase("SUMMARY")) {

                if (cmblevel.equalsIgnoreCase("RN")) {

                    reportFile =
                            new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/EmployeeDetailRegionWiseAbsSpe.jasper"));
                } else if (cmblevel.equalsIgnoreCase("CL")) {
                    System.out.println("office level::" + cmblevel);
                    reportFile =
                            new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/EmployeeDetailCircleWiseAbsSpe.jasper"));
                } else if (cmblevel.equalsIgnoreCase("DN")) {
                    System.out.println("office level::" + cmblevel);
                    reportFile =
                            new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/EmployeeDetailDivisionWiseAbsSpe.jasper"));
                }


            } else if (optview.equalsIgnoreCase("SPECIFIC") &&
                       optdetail.equalsIgnoreCase("DETAIL")) {

                if (cmblevel.equalsIgnoreCase("RN")) {
                    reportFile =
                            new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/EmployeeDetailRegionWiseDetailSpe.jasper"));
                } else if (cmblevel.equalsIgnoreCase("CL")) {
                    reportFile =
                            new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/EmployeeDetailCircleWiseDetailSpe.jasper"));
                } else if (cmblevel.equalsIgnoreCase("DN")) {
                    reportFile =
                            new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/EmployeeDetailDivisionWiseDetailSpe.jasper"));
                }
            }

            if (!reportFile.exists())
                throw new JRRuntimeException("File J not found. The report design must be compiled first.");

            JasperReport jasperReport =
                (JasperReport)JRLoader.loadObject(reportFile.getPath());


            //   JasperReport jasperReport =     JasperCompileManager.compileReport(jasperDesign);
            Map map = null;


            if (optview.equalsIgnoreCase("SPECIFIC")) {
                map = new HashMap();
                if (cmblevel.equalsIgnoreCase("RN")) {
                    office = request.getParameter("cmbregion");
                    map.put("region", Integer.parseInt(office));
                } else if (cmblevel.equalsIgnoreCase("CL")) {
                    office = request.getParameter("cmbcircle");
                    System.out.println("office id::" + office);
                    map.put("circle", Integer.parseInt(office));
                } else if (cmblevel.equalsIgnoreCase("DN")) {
                    office = request.getParameter("cmbdivision");
                    System.out.println("office id::" + office);
                    map.put("division", Integer.parseInt(office));
                }
            }

            JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, map, connection);


            String rtype = request.getParameter("cmbReportType");
            if (rtype.equalsIgnoreCase("HTML")) {
                response.setContentType("text/html");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"OfficeDetail.html\"");
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
                                   "attachment;filename=\"OfficeDetail.pdf\"");
                OutputStream out = response.getOutputStream();
                out.write(buf, 0, buf.length);
                out.close();
            } else if (rtype.equalsIgnoreCase("EXCEL")) {

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"OfficeDetail.xls\"");
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


    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
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

            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection

            Class.forName(strDriver.trim());
            connection =
                    DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                                                strdbpassword.trim());
        } catch (Exception ex) {
            String connectMsg =
                "Could not create the connection" + ex.getMessage() + " " +
                ex.getLocalizedMessage();
            System.out.println(connectMsg);
        }
        ResultSet result = null;
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
        System.out.println("argument:" +
                           request.getParameter("EmpSingleParam"));
        if (request.getParameter("EmpSingleParam") != null) {
            System.out.println("list of EmpSingleParam");
            doPost(request, response);
        }

        System.out.println("Employee Detail Report GET");
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
        String xml = "";
        String strCommand = "", optview = "";

        try {
            strCommand = request.getParameter("OLevel");
            System.out.println("Command....." + strCommand);
            optview = request.getParameter("optview");
            System.out.println("optview....." + optview);

        }

        catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }


        try {
            String sql = "";
            if (optview.equalsIgnoreCase("ALL")) {
                if (strCommand.equalsIgnoreCase("HO")) {

                    System.out.println("Command::" + strCommand);
                    sql =
 "select upper(d.OFFICE_LEVEL_ID) as OFFICE_LEVEL_ID ,upper(c.DESIGNATION)as DESIGNATION,count(a.EMPLOYEE_NAME) as count " +
   " from HRM_MST_EMPLOYEES a, HRM_EMP_CURRENT_POSTING b ,HRM_MST_DESIGNATIONS c ,COM_MST_OFFICES d  " +
   " where a.EMPLOYEE_ID = b.EMPLOYEE_ID and  c.DESIGNATION_ID = b.DESIGNATION_ID and d.OFFICE_ID = b.OFFICE_ID " +
   " and d.OFFICE_LEVEL_ID ='HO' group by d.OFFICE_LEVEL_ID,c.DESIGNATION order by  OFFICE_LEVEL_ID ,DESIGNATION";
                    xml = "<response>";
                    ps = connection.prepareStatement(sql);

                    result = ps.executeQuery();

                    if (result.next())
                        xml = xml + "<flag>success</flag>";
                    else
                        xml = xml + "<flag>failure</flag>";
                    xml = xml + "</response>";

                } else if (strCommand.equalsIgnoreCase("RN")) {

                    System.out.println("Command::" + strCommand);
                    sql =
 "select upper(a.REGION_OFFICE_NAME) REGION_OFFICE_NAME,upper(c.DESIGNATION) DESIGNATION,count(d.EMPLOYEE_NAME) count " +
   " from COM_MST_REGIONS_HVIEW a inner join HRM_EMP_CURRENT_POSTING b on b.OFFICE_ID = a.REGION_OFFICE_ID " +
   " inner join HRM_MST_DESIGNATIONS c on c.DESIGNATION_ID = b.DESIGNATION_ID " +
   " inner join HRM_MST_EMPLOYEES d on d.EMPLOYEE_ID = b.EMPLOYEE_ID group by a.REGION_OFFICE_NAME,c.DESIGNATION " +
   " order by REGION_OFFICE_NAME,DESIGNATION";
                    xml = "<response>";
                    ps = connection.prepareStatement(sql);

                    result = ps.executeQuery();

                    if (result.next())
                        xml = xml + "<flag>success</flag>";
                    else
                        xml = xml + "<flag>failure</flag>";
                    xml = xml + "</response>";

                } else if (strCommand.equalsIgnoreCase("CL")) {

                    System.out.println("Command::" + strCommand);
                    sql =
 "select b.REGION_OFFICE_NAME,a.CIRCLE_OFFICE_NAME,d.DESIGNATION,count(e.EMPLOYEE_NAME) count " +
   " from COM_MST_CIRCLES_HVIEW a inner join COM_MST_REGIONS_HVIEW b on b.REGION_OFFICE_ID=a.REGION_OFFICE_ID " +
   " inner join HRM_EMP_CURRENT_POSTING c on c.OFFICE_ID = a.CIRCLE_OFFICE_ID " +
   " inner join HRM_MST_DESIGNATIONS d on d.DESIGNATION_ID = c.DESIGNATION_ID " +
   " inner join HRM_MST_EMPLOYEES e on e.EMPLOYEE_ID = c.EMPLOYEE_ID group by b.REGION_OFFICE_NAME,a.CIRCLE_OFFICE_NAME,d.DESIGNATION " +
   " order by REGION_OFFICE_NAME,CIRCLE_OFFICE_NAME,DESIGNATION";
                    xml = "<response>";
                    ps = connection.prepareStatement(sql);

                    result = ps.executeQuery();

                    if (result.next())
                        xml = xml + "<flag>success</flag>";
                    else
                        xml = xml + "<flag>failure</flag>";
                    xml = xml + "</response>";

                } else if (strCommand.equalsIgnoreCase("DN")) {

                    System.out.println("Command::" + strCommand);
                    sql =
 "select upper(c.REGION_OFFICE_NAME) REGION_OFFICE_NAME,upper(b.CIRCLE_OFFICE_NAME) CIRCLE_OFFICE_NAME,a.DIVISION_OFFICE_NAME,upper(e.DESIGNATION) DESIGNATION,count(f.EMPLOYEE_NAME) as count " +
   " from COM_MST_DIVISIONS_HVIEW a inner join COM_MST_CIRCLES_HVIEW b on b.CIRCLE_OFFICE_ID=a.CIRCLE_OFFICE_ID " +
   " inner join COM_MST_REGIONS_HVIEW c on c.REGION_OFFICE_ID=a.REGION_OFFICE_ID " +
   " inner join HRM_EMP_CURRENT_POSTING d on d.OFFICE_ID = a.DIVISION_OFFICE_ID " +
   " inner join HRM_MST_DESIGNATIONS e on e.DESIGNATION_ID = d.DESIGNATION_ID " +
   " inner join HRM_MST_EMPLOYEES f on f.EMPLOYEE_ID = d.EMPLOYEE_ID " +
   " group by c.REGION_OFFICE_NAME,b.CIRCLE_OFFICE_NAME,a.DIVISION_OFFICE_NAME,e.DESIGNATION " +
   " order by REGION_OFFICE_NAME,CIRCLE_OFFICE_NAME,DIVISION_OFFICE_NAME,DESIGNATION";
                    xml = "<response>";
                    ps = connection.prepareStatement(sql);

                    result = ps.executeQuery();

                    if (result.next())
                        xml = xml + "<flag>success</flag>";
                    else
                        xml = xml + "<flag>failure</flag>";
                    xml = xml + "</response>";

                }
            } else if (optview.equalsIgnoreCase("SPECIFIC")) {

                int offid = Integer.parseInt(request.getParameter("office"));
                System.out.println("offid::" + offid);
                if (strCommand.equalsIgnoreCase("RN")) {

                    System.out.println("Command::" + strCommand);
                    sql =
 "select upper(a.REGION_OFFICE_NAME) REGION_OFFICE_NAME,upper(c.DESIGNATION) DESIGNATION,count(d.EMPLOYEE_NAME) count " +
   " from COM_MST_REGIONS_HVIEW a inner join HRM_EMP_CURRENT_POSTING b on b.OFFICE_ID = a.REGION_OFFICE_ID " +
   " inner join HRM_MST_DESIGNATIONS c on c.DESIGNATION_ID = b.DESIGNATION_ID " +
   " inner join HRM_MST_EMPLOYEES d on d.EMPLOYEE_ID = b.EMPLOYEE_ID " +
   " where a.REGION_OFFICE_ID = ? " +
   " group by a.REGION_OFFICE_NAME,c.DESIGNATION order by REGION_OFFICE_NAME,DESIGNATION";
                    xml = "<response>";
                    ps = connection.prepareStatement(sql);
                    ps.setInt(1, offid);
                    result = ps.executeQuery();

                    if (result.next())
                        xml = xml + "<flag>success</flag>";
                    else
                        xml = xml + "<flag>failure</flag>";
                    xml = xml + "</response>";
                    System.out.println("with in RN" + xml);

                } else if (strCommand.equalsIgnoreCase("CL")) {

                    System.out.println("Command::" + strCommand);
                    sql =
 "select b.REGION_OFFICE_NAME,a.CIRCLE_OFFICE_NAME,d.DESIGNATION,count(e.EMPLOYEE_NAME) count " +
   " from COM_MST_CIRCLES_HVIEW a inner join COM_MST_REGIONS_HVIEW b on b.REGION_OFFICE_ID=a.REGION_OFFICE_ID " +
   " inner join HRM_EMP_CURRENT_POSTING c on c.OFFICE_ID = a.CIRCLE_OFFICE_ID " +
   " inner join HRM_MST_DESIGNATIONS d on d.DESIGNATION_ID = c.DESIGNATION_ID " +
   " inner join HRM_MST_EMPLOYEES e on e.EMPLOYEE_ID = c.EMPLOYEE_ID " +
   " where a.CIRCLE_OFFICE_ID =? group by b.REGION_OFFICE_NAME,a.CIRCLE_OFFICE_NAME,d.DESIGNATION " +
   " order by REGION_OFFICE_NAME,CIRCLE_OFFICE_NAME,DESIGNATION";
                    xml = "<response>";
                    ps = connection.prepareStatement(sql);
                    ps.setInt(1, offid);
                    result = ps.executeQuery();

                    if (result.next())
                        xml = xml + "<flag>success</flag>";
                    else
                        xml = xml + "<flag>failure</flag>";
                    xml = xml + "</response>";

                } else if (strCommand.equalsIgnoreCase("DN")) {

                    System.out.println("Command::" + strCommand);
                    sql =
 "select upper(c.REGION_OFFICE_NAME) REGION_OFFICE_NAME,upper(b.CIRCLE_OFFICE_NAME) CIRCLE_OFFICE_NAME,a.DIVISION_OFFICE_NAME,upper(e.DESIGNATION) DESIGNATION,count(f.EMPLOYEE_NAME) as count " +
   " from COM_MST_DIVISIONS_HVIEW a inner join COM_MST_CIRCLES_HVIEW b on b.CIRCLE_OFFICE_ID=a.CIRCLE_OFFICE_ID " +
   " inner join COM_MST_REGIONS_HVIEW c on c.REGION_OFFICE_ID=a.REGION_OFFICE_ID " +
   " inner join HRM_EMP_CURRENT_POSTING d on d.OFFICE_ID = a.DIVISION_OFFICE_ID " +
   " inner join HRM_MST_DESIGNATIONS e on e.DESIGNATION_ID = d.DESIGNATION_ID " +
   " inner join HRM_MST_EMPLOYEES f on f.EMPLOYEE_ID = d.EMPLOYEE_ID " +
   "  where a.DIVISION_OFFICE_ID=? " +
   " group by c.REGION_OFFICE_NAME,b.CIRCLE_OFFICE_NAME,a.DIVISION_OFFICE_NAME,e.DESIGNATION " +
   " order by REGION_OFFICE_NAME,CIRCLE_OFFICE_NAME,DIVISION_OFFICE_NAME,DESIGNATION";
                    xml = "<response>";
                    ps = connection.prepareStatement(sql);
                    ps.setInt(1, offid);
                    result = ps.executeQuery();

                    if (result.next())
                        xml = xml + "<flag>success</flag>";
                    else
                        xml = xml + "<flag>failure</flag>";
                    xml = xml + "</response>";

                }

            }


        } catch (Exception e) {
            System.out.println("Error in query process:: " + e);
            xml = "<response><flag>failure</flag></response>";

        }
        System.out.println(xml);
        out.write(xml);
        out.flush();
        out.close();


    }


}
