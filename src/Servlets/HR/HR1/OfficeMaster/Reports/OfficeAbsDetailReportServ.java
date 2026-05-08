package Servlets.HR.HR1.OfficeMaster.Reports;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.*;

import java.util.*;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;


public class OfficeAbsDetailReportServ extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";


    public void init(ServletConfig config) throws ServletException {
        super.init(config);

    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        doGet(request, response);
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


        String selstr = "";
        // String selspestr="";
        String sel = "";
        String opt = "";
        response.setContentType(CONTENT_TYPE);

        //JasperDesign jasperDesign = null;
        File reportFile = null;

        try {
            System.out.println("calling servlet");

            opt = request.getParameter("optoffdetail");
            if (opt.equalsIgnoreCase("ALLABS")) {
                reportFile =
                        new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/OfficeAbstract.jasper"));

            } else if (opt.equalsIgnoreCase("REGIONABS")) {
                reportFile =
                        new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/OfficeRegionwiseAbstract.jasper"));
            } else if (opt.equalsIgnoreCase("REGIONDETAIL")) {
                reportFile =
                        new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/OfficeRegionwiseDetail.jasper"));
            } else if (opt.equalsIgnoreCase("SEL")) {
                selstr = request.getParameter("optsel");
                if (selstr.equalsIgnoreCase("ABS")) {
                    sel = request.getParameter("cmblevel");
                    if (sel.equalsIgnoreCase("CL"))
                        reportFile =
                                new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/OfficeCirclewiseAbstract.jasper"));
                    else if (sel.equalsIgnoreCase("DN"))
                        reportFile =
                                new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/OfficeDivisionAbstract.jasper"));
                    else if (sel.equalsIgnoreCase("SD"))
                        reportFile =
                                new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/OfficeSubDivisionAbstract.jasper"));
                    else if (sel.equalsIgnoreCase("LB"))
                        reportFile =
                                new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/OfficeLabAbstract.jasper"));
                    else if (sel.equalsIgnoreCase("AW"))
                        reportFile =
                                new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/OfficeAuditWingAbstract.jasper"));


                } else if (selstr.equalsIgnoreCase("DETAILS")) {
                    sel = request.getParameter("cmblevel");
                    if (sel.equalsIgnoreCase("CL"))
                        reportFile =
                                new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/OfficeCirclewiseDetails.jasper"));
                    else if (sel.equalsIgnoreCase("DN"))
                        reportFile =
                                new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/OfficeDivisionDetail.jasper"));
                    else if (sel.equalsIgnoreCase("SD"))
                        reportFile =
                                new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/OfficeSubDivisionDetail.jasper"));
                    else if (sel.equalsIgnoreCase("LB"))
                        reportFile =
                                new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/OfficeLabDetail.jasper"));
                    else if (sel.equalsIgnoreCase("AW"))
                        reportFile =
                                new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/OfficeAuditWingDetail.jasper"));

                }
            }

            //////////

            else if (opt.equalsIgnoreCase("SPECIFIC")) {
                System.out.println("specific office report");

                sel = request.getParameter("cmbolevel");
                System.out.println("sel ... " + sel);
                if (sel.equalsIgnoreCase("RN"))
                    reportFile =
                            new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/OfficeRegionwiseDetailSpe.jasper"));
                else if (sel.equalsIgnoreCase("CL"))
                    reportFile =
                            new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/OfficeCirclewiseDetailsSpe.jasper"));
                else if (sel.equalsIgnoreCase("DN"))
                    reportFile =
                            new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/OfficeDivisionDetailSpe.jasper"));
                else if (sel.equalsIgnoreCase("SD"))
                    reportFile =
                            new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/OfficeSubDivisionDetailSpe.jasper"));
                else if (sel.equalsIgnoreCase("HO"))
                    reportFile =
                            new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/OfficeHeadOfficeDetailsSpe.jasper"));


            }

            if (!reportFile.exists())
                throw new JRRuntimeException("File J not found. The report design must be compiled first.");

            JasperReport jasperReport =
                (JasperReport)JRLoader.loadObject(reportFile.getPath());

            System.out.println("opt::" + opt);
            //   JasperReport jasperReport =     JasperCompileManager.compileReport(jasperDesign);
            Map map = null;
            if (opt.equalsIgnoreCase("SPECIFIC")) {
                if (sel.equalsIgnoreCase("HO")) {
                    map = null;
                } else {
                    map = new HashMap();
                    sel = request.getParameter("cmbolevel");
                    if (sel.equalsIgnoreCase("RN"))
                        opt = request.getParameter("cmbregion");
                    else if (sel.equalsIgnoreCase("CL"))
                        opt = request.getParameter("cmbcircle");
                    else if (sel.equalsIgnoreCase("DN"))
                        opt = request.getParameter("cmbdivision");
                    else if (sel.equalsIgnoreCase("SD"))
                        opt = request.getParameter("cmbsubdiv");

                    map.put("officelevelid", Integer.parseInt(opt));
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
}
