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

public class EmployeeProfileServ_old extends HttpServlet {
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


        if (strCommand.equalsIgnoreCase("Existg")) {
            System.out.println("------------");
            int strEmpId = Integer.parseInt(request.getParameter("EmpId"));
            System.out.println("EmpId" + strEmpId);
            xml = "<response><command>Existg</command>";
            try {

                ps =
  connection.prepareStatement("select decode(employee_initial,null,' ',employee_initial||'.')||Employee_Name Employee_Name,GPF_NO from HRM_MST_EMPLOYEES  where   Employee_Id=?");
                ps.setInt(1, strEmpId);
                rs = ps.executeQuery();
                if (rs.next()) {
                    String strEmpName = rs.getString("Employee_Name");
                    int strEmpGpf = rs.getInt("GPF_NO");
                    xml = xml + "<flag>success</flag>";
                    xml =
 xml + "<EmpName>" + strEmpName + "</EmpName><EmpGpf>" + strEmpGpf +
   "</EmpGpf>";
                } else {
                    xml = xml + "<flag>failure</flag>";
                }

            } catch (Exception aee) {
                System.out.println("Exception in the getting values IN get : " +
                                   aee);
                xml = xml + "<flag>failure</flag>";
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
        //String deptid=request.getParameter("txtDept_Id");
        // System.out.println("Dept id:"+deptid);

        String optbase = request.getParameter("optselect");
        System.out.println("Option Selected:" + optbase);
        try {
            System.out.println("calling servlet");

            reportFile =
                    new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Employee_Profile.jasper"));


            // }
            // reportFile = new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/RelievalTransferDetails.jasper"));
            if (!reportFile.exists())
                throw new JRRuntimeException("File J not found. The report design must be compiled first.");

            JasperReport jasperReport =
                (JasperReport)JRLoader.loadObject(reportFile.getPath());
            Map map = null;
            map = new HashMap();

            /*  get the values  */
            System.out.println("test1");
            int empid = Integer.parseInt(request.getParameter("txtEmpId1"));
            System.out.println("empid :" + empid);
            map.put("emp_id", empid);

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

