package Servlets.HR.HR1.StaffStrength.Reports;


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

public class OfficeStaffStrengthDesigServlet extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";
    private Connection connection = null;
    private ResultSet rs = null;
    private PreparedStatement ps = null;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
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
        } catch (Exception e) {
            System.out.println("Exception in connection..." + e);
        }
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);

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

                ps =
  connection.prepareStatement("select a.OFFICE_ID,a.OFFICE_NAME,b.OFFICE_LEVEL_NAME,a.OFFICE_ADDRESS1||','||a.OFFICE_ADDRESS2||','||a.CITY_TOWN_NAME ADDRESS " +
                              " from COM_MST_OFFICES a inner join COM_MST_OFFICE_LEVELS b on b.OFFICE_LEVEL_ID = a.OFFICE_LEVEL_ID " +
                              "where a.OFFICE_LEVEL_ID = ? order by OFFICE_NAME");
                ps.setString(1, olevel);
                rs = ps.executeQuery();
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

                ps =
  connection.prepareStatement("select a.OFFICE_ID,a.OFFICE_NAME,b.OFFICE_LEVEL_NAME,a.OFFICE_ADDRESS1||','||a.OFFICE_ADDRESS2||','||a.CITY_TOWN_NAME ADDRESS " +
                              " from COM_MST_OFFICES a inner join COM_MST_OFFICE_LEVELS b on b.OFFICE_LEVEL_ID = a.OFFICE_LEVEL_ID " +
                              " where a.OFFICE_LEVEL_ID = ? and a.PRIMARY_WORK_ID = ? order by OFFICE_NAME");
                ps.setString(1, olevel);
                ps.setString(2, own);
                rs = ps.executeQuery();
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
        String fin = request.getParameter("cmbFinancialYear");
        //int office=Integer.parseInt(s);
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
                    new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/StaffStrengthDesignationReport.jasper"));
            if (!reportFile.exists())
                throw new JRRuntimeException("File J not found. The report design must be compiled first.");

            JasperReport jasperReport =
                (JasperReport)JRLoader.loadObject(reportFile.getPath());
            Map map = null;
            map = new HashMap();
            map.put("txtofficeid", s);
            System.out.println("finyear is" + fin);
            map.put("finyear", fin);
            // map.put("offids",5001);
            JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, map, connection);

            String rtype = request.getParameter("cmbReportType");
            if (rtype.equalsIgnoreCase("HTML")) {
                response.setContentType("text/html");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"StaffStrengthDetail.html\"");
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
                                   "attachment;filename=\"StaffStrengthDetail.pdf\"");
                OutputStream out = response.getOutputStream();
                out.write(buf, 0, buf.length);
                out.close();
            } else if (rtype.equalsIgnoreCase("EXCEL")) {

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"StaffStrengthDetail.xls\"");
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
