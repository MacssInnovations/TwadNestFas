package Servlets.HR.HR1.EmployeeMaster.Reports;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;

import java.text.SimpleDateFormat;

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
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

public class RetirementList1_Serv1_all extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";
    Connection connection = null;

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
        String selspestr = "";
        String sel = "";
        String opt = "";
        String desigval = "", newdesigval = "";
        response.setContentType(CONTENT_TYPE);
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
        JasperDesign jasperDesign = null;
        File reportFile = null;

        try {
            System.out.println("calling servlet");

            String fromdate = request.getParameter("txtfromdate");
            String todate = request.getParameter("txttodate");
            String deslevel = request.getParameter("designationlevel");

            System.out.println(fromdate);
            System.out.println(todate);
            System.out.println("des_level..." + deslevel);

            //Date Conversion for Date
            java.sql.Date dateOfAttachment = null;
            System.out.println("before converting date");
            String dateString = fromdate;
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date d;
            d = dateFormat.parse(fromdate.trim());
            System.out.println("util date is:" + d);
            dateFormat.applyPattern("yyyy-MM-dd");
            dateString = dateFormat.format(d);
            dateOfAttachment = java.sql.Date.valueOf(dateString);

            java.sql.Date dateto = null;
            System.out.println("before converting date");
            String dateString1 = todate;
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date d1;
            d1 = dateFormat1.parse(todate.trim());
            dateFormat1.applyPattern("yyyy-MM-dd");
            dateString1 = dateFormat1.format(d1);
            dateto = java.sql.Date.valueOf(dateString1);

            System.out.println("fromdate" + dateOfAttachment);
            System.out.println("todate" + dateto);

            String optbase = request.getParameter("optselect");
            System.out.println("optbase Option Selected:" + optbase);

            String optbase1 = request.getParameter("optselectgrp");
            System.out.println("optbase1 Option Selected:" + optbase1);

            Map map = new HashMap();
            boolean flag = true;

            System.out.println("before all");
            if (optbase1.equalsIgnoreCase("RadAl")) {
                System.out.println("inside all");
                reportFile =
                        new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/retirementlist2_all.jasper"));
                System.out.println("after" + reportFile);
                map.put("txtfrom", d);
                map.put("txtto", d1);
                flag = false;
            }

            int destRank = 0;
            if (flag) {
                if (optbase.equalsIgnoreCase("Dest")) {
                    //destRank=Integer.parseInt(request.getParameter("cmbDesignation"));
                    String testval1[] =
                        (request.getParameterValues("chkdesig"));
                    for (int i = 0; i < testval1.length; i++) {
                        desigval = desigval + testval1[i] + ",";
                        System.out.println("Test values:-----" + testval1[i]);
                    }
                    newdesigval = desigval.substring(0, desigval.length() - 1);
                    System.out.println("new desgi values:" + newdesigval);
                    reportFile =
                            new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/retirementlist1_all.jasper"));
                    System.out.println("after" + reportFile);
                    map.put("txtfrom", d);
                    map.put("txtto", d1);
                    map.put("des_id", newdesigval);
                } else if (optbase.equalsIgnoreCase("Rank")) {
                    //destRank=Integer.parseInt(request.getParameter("cmbRank"));
                    String testval1[] =
                        (request.getParameterValues("chkrank"));
                    for (int i = 0; i < testval1.length; i++) {
                        desigval = desigval + testval1[i] + ",";
                        System.out.println("Test values1:-----" + testval1[i]);
                    }
                    newdesigval = desigval.substring(0, desigval.length() - 1);
                    System.out.println("new rank values:" + newdesigval);

                    reportFile =
                            new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/retirementlist1_Rank_all.jasper"));
                    System.out.println("after" + reportFile);
                    map.put("txtfrom", d);
                    map.put("txtto", d1);
                    map.put("rank_id", newdesigval);


                } else if (optbase.equalsIgnoreCase("Cadr")) {
                    //destRank=Integer.parseInt(request.getParameter("cmbCadre"));
                    String testval1[] =
                        (request.getParameterValues("chkcadre"));
                    for (int i = 0; i < testval1.length; i++) {
                        desigval = desigval + testval1[i] + ",";
                        System.out.println("Test values2:-----" + testval1[i]);
                    }
                    newdesigval = desigval.substring(0, desigval.length() - 1);
                    System.out.println("new cadre values:" + newdesigval);

                    reportFile =
                            new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/retirementlist1_Cadre_all.jasper"));
                    System.out.println("after" + reportFile);
                    map.put("txtfrom", d);
                    map.put("txtto", d1);
                    map.put("cadre_id", newdesigval);
                }
            }
            /*
                String optbase2=request.getParameter("optselectgrp");
                System.out.println(optbase2);
                */


            /* if(optbase.equalsIgnoreCase("Dest"))
                {
                reportFile = new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/retirementlist1.jasper"));
                System.out.println("after"+reportFile);
                    map.put("txtfrom",d);
                    map.put("txtto",d1);
                    map.put("des_id",newdesigval);
                }*/


            if (!reportFile.exists())
                throw new JRRuntimeException("File J not found. The report design must be compiled first.");

            JasperReport jasperReport =
                (JasperReport)JRLoader.loadObject(reportFile.getPath());


            System.out.println("opt::" + opt);
            //   JasperReport jasperReport =     JasperCompileManager.compileReport(jasperDesign);
            /*
            map.put("txtfrom",d);
            map.put("txtto",d1);
            map.put("des_id",newdesigval);
            */
            System.out.println("check_1");

            JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, map, connection);

            String rtype = request.getParameter("optoutputtype");
            System.out.println("rtype..." + rtype);

            if (rtype.equalsIgnoreCase("HTML")) {
                System.out.println("inside html");
                response.setContentType("text/html");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"Retirementlist_all.html\"");
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
                System.out.println("inside pdf");
                byte buf[] =
                    JasperExportManager.exportReportToPdf(jasperPrint);
                response.setContentType("application/pdf");
                response.setContentLength(buf.length);
                // response.setHeader("content-disposition", "inline;filename=OpenActionItems.pdf");
                //response.setContentType("application/force-download");

                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"Retirementlist_all.pdf\"");
                OutputStream out = response.getOutputStream();
                out.write(buf, 0, buf.length);
                out.close();
            } else if (rtype.equalsIgnoreCase("EXCEL")) {

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"Retirementlist_all.xls\"");
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
                                   "attachment;filename=\"Retirementlist.txt\"");

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
