package Servlets.HR.HR1.EmployeeMaster.Reports;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.*;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * Servlet implementation class ListOfEmp_YearExp_Office
 */
public class Office_Wise_Gender extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Office_Wise_Gender() {
        super();
        // TODO Auto-generated constructor stub
    }


    private void doProcess(HttpServletRequest request,
                           HttpServletResponse response) throws ServletException,
                                                                IOException {

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs1 = null;
        String FinOp = "";
        String output = "";
        Map map = null;
        File reportFile = null;
        try {
            ResourceBundle rb1 =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";

            String strDriver = rb1.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rb1.getString("Config.DSN");
            String strhostname = rb1.getString("Config.HOST_NAME");
            String strportno = rb1.getString("Config.PORT_NUMBER");
            String strsid = rb1.getString("Config.SID");
            String strdbusername = rb1.getString("Config.USER_NAME");
            String strdbpassword = rb1.getString("Config.PASSWORD");
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
            Class.forName(strDriver.trim());
            connection =
                    DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                                                strdbpassword.trim());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            HttpSession session = request.getSession(false);

            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");
            }

            System.out.println(session);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        String office = request.getParameter("OffType");
        output = request.getParameter("cmbReportType");
        //System.out.println("LOffice.."+office);

        String option[] = request.getParameterValues("select1");
        //System.out.println("Sk \n option selected.."+option.length);
        for (int i = 0; i < option.length; i++) {
            if (option[i].equals("WKG")) {
                FinOp += "'";
                FinOp += option[i];
                FinOp += "'";
                if (i + 1 < option.length)
                    FinOp += ", ";
            }
            if (option[i].equals("TRT")) {
                FinOp += "'";
                FinOp += option[i];
                FinOp += "'";
                if (i + 1 < option.length)
                    FinOp += ", ";
            }
            if (option[i].equals("DPN")) {
                FinOp += "'";
                FinOp += option[i];
                FinOp += "'";
            }
            //System.out.println(option[i]);System.out.println("Fin2y "+FinOp);
        }
        //System.out.println("Fin "+FinOp);
        if (office.equals("All")) {
            String Query =
                "select nvl(ss.offl, ''), nvl(ss.Male_count,0), nvl(ss1.Female_count,0) from ( " +
                "select d.OFFICE_LEVEL_NAME offl ,count(*) Male_count from (( " +
                "SELECT employee_id,gender FROM hrm_mst_employees   WHERE gender='M' )a left outer join( " +
                "SELECT employee_id, office_id FROM hrm_emp_current_posting  where employee_status_id in(" +
                FinOp + ") " +
                ")b on a.employee_id=b.employee_id left outer join( " +
                "select office_level_id, office_id from com_mst_offices where office_id in(select office_id FROM hrm_emp_current_posting where employee_status_id in(" +
                FinOp + ") and OFFICE_LEVEL_ID not in('SD','LB','AW')) " +
                ")c on c.office_id=b.office_id " + "left outer join " + "( " +
                "select office_level_id,OFFICE_LEVEL_NAME from COM_MST_OFFICE_LEVELS " +
                ")d on c.office_level_id=d.office_level_id ) " +
                "where  c.office_level_id is not null " +
                "group by d.OFFICE_LEVEL_NAME" + ")ss left outer join (" +
                "select d.OFFICE_LEVEL_NAME offl ,count(*) Female_count from((" +
                "SELECT employee_id,gender FROM hrm_mst_employees   WHERE gender='F')a left outer join " +
                "( SELECT employee_id, office_id FROM hrm_emp_current_posting  where employee_status_id in(" +
                FinOp + ")" + ")b on a.employee_id=b.employee_id " +
                "left outer join(" +
                "select office_level_id, office_id from com_mst_offices where office_id in(select office_id FROM hrm_emp_current_posting where employee_status_id in(" +
                FinOp + ") and OFFICE_LEVEL_ID not in('SD','LB','AW'))" +
                ")c on c.office_id=b.office_id " +
                "left outer join(select office_level_id,OFFICE_LEVEL_NAME from COM_MST_OFFICE_LEVELS " +
                ")d on c.office_level_id=d.office_level_id) where  c.office_level_id is not null " +
                "group by d.OFFICE_LEVEL_NAME )ss1 on ss1.offl=ss.offl";

            System.out.println("Before");
            System.out.println(Query);
            try {
                map = new HashMap();
                reportFile =
                        new File(getServletContext().getRealPath("/Reports/Office_Wise_Gender.jasper"));
                System.out.println("reportFile" + reportFile);
                JasperReport jasperReport =
                    (JasperReport)JRLoader.loadObject(reportFile.getPath());
                map.put("FinOp", FinOp);
                JasperPrint jasperPrint =
                    JasperFillManager.fillReport(jasperReport, map,
                                                 connection);


                if (!reportFile.exists())
                    throw new JRRuntimeException("File J not found. The report design must be compiled first.");

                if (output.equalsIgnoreCase("HTML")) {
                    response.setContentType("text/html");
                    response.setHeader("Content-Disposition",
                                       "attachment;filename=\"Designation_Summary_report.html\"");
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
                    exporter.setParameter(JRExporterParameter.OUTPUT_WRITER,
                                          out);
                    exporter.exportReport();
                    out.flush();
                    out.close();
                } else if (output.equalsIgnoreCase("PDF")) {
                    System.out.println("pdf generated");

                    byte buf[] =
                        JasperExportManager.exportReportToPdf(jasperPrint);
                    response.setContentType("application/pdf");
                    response.setContentLength(buf.length);
                    // response.setHeader("content-disposition", "inline;filename=OpenActionItems.pdf");
                    //response.setContentType("application/force-download");

                    response.setHeader("Content-Disposition",
                                       "attachment;filename=\"Designation_Summary_report.pdf\"");
                    OutputStream out = response.getOutputStream();
                    out.write(buf, 0, buf.length);
                    out.close();
                } else if (output.equalsIgnoreCase("EXCEL")) {

                    response.setContentType("application/vnd.ms-excel");
                    response.setHeader("Content-Disposition",
                                       "attachment;filename=\"Designation_Summary_report.xls\"");
                    JRXlsExporter exporterXLS = new JRXlsExporter();
                    exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT,
                                             jasperPrint);

                    ByteArrayOutputStream xlsReport =
                        new ByteArrayOutputStream();
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
                    ServletOutputStream ouputStream =
                        response.getOutputStream();
                    ouputStream.write(bytes, 0, bytes.length);
                    ouputStream.flush();
                    ouputStream.close();

                } else if (output.equalsIgnoreCase("TXT")) {

                    response.setContentType("text/plain");
                    response.setHeader("Content-Disposition",
                                       "attachment;filename=\"Designation_Summary_report.txt\"");

                    JRTextExporter exporter = new JRTextExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT,
                                          jasperPrint);
                    ByteArrayOutputStream txtReport =
                        new ByteArrayOutputStream();
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
                                          txtReport);
                    exporter.setParameter(JRTextExporterParameter.CHARACTER_WIDTH,
                                          new Integer(200));
                    exporter.setParameter(JRTextExporterParameter.CHARACTER_HEIGHT,
                                          new Integer(50));
                    exporter.exportReport();

                    byte[] bytes;
                    bytes = txtReport.toByteArray();
                    ServletOutputStream ouputStream =
                        response.getOutputStream();
                    ouputStream.write(bytes, 0, bytes.length);
                    ouputStream.flush();
                    ouputStream.close();


                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("After");

        } else {

            String sep = request.getParameter("officeselected");

            String alloffice = request.getParameter("allofficeid");

            //System.out.println("Region selected.."+alloffice);
            // System.out.println("Office selected1.."+sep);
            String Query =
                "select nvl(ss.offl, ''), nvl(ss.offname, ''), nvl(ss.Male_count,0), nvl(ss1.Female_count,0) from " +
                "( " +
                "select  c.office_id offl, c.OFFICE_NAME offname ,count(*) Male_count from " +
                "(( SELECT employee_id,gender FROM hrm_mst_employees WHERE gender='M'" +
                ")a left outer join( " +
                "SELECT employee_id, office_id FROM hrm_emp_current_posting  where employee_status_id in(" +
                FinOp + ") and office_id in(" + sep + ")" +
                ")b on a.employee_id=b.employee_id " + "left outer join( " +
                "select office_level_id, office_id,OFFICE_NAME from com_mst_offices where office_id in(select office_id FROM hrm_emp_current_posting where employee_status_id in(" +
                FinOp + ") and office_id in(" + sep +
                ") and OFFICE_LEVEL_ID not in('SD','LB','AW'))" +
                ")c on c.office_id=b.office_id " +
                "left outer join( select office_level_id,OFFICE_LEVEL_NAME from COM_MST_OFFICE_LEVELS" +
                ")d on c.office_level_id=d.office_level_id) " +
                "where  c.office_level_id is not null " +
                "group by  c.office_id, c.OFFICE_NAME " +
                ")ss left outer join ( " +
                "select  c.office_id offl, c.OFFICE_NAME  offname,count(*) Female_count from " +
                "(( " +
                "SELECT employee_id,gender FROM hrm_mst_employees   WHERE gender='F'" +
                ")a left outer join( " +
                "SELECT employee_id, office_id FROM hrm_emp_current_posting  where employee_status_id in(" +
                FinOp + ") and office_id in(" + sep + ")" +
                ")b on a.employee_id=b.employee_id " + "left outer join( " +
                "select office_level_id, office_id,OFFICE_NAME from com_mst_offices where office_id in(select office_id FROM hrm_emp_current_posting where employee_status_id in(" +
                FinOp + ") and office_id in(" + sep +
                ") and OFFICE_LEVEL_ID not in('SD','LB','AW'))" +
                ")c on c.office_id=b.office_id " + "left outer join( " +
                "select office_level_id,OFFICE_LEVEL_NAME from COM_MST_OFFICE_LEVELS " +
                ")d on c.office_level_id=d.office_level_id ) where  c.office_level_id is not null " +
                "group by  c.office_id, c.OFFICE_NAME " +
                ")ss1 on ss1. offname=ss. offname";

            String s =
                request.getRealPath("/Reports/Office_Wise_Gender(Specific).jasper");
            System.out.println("The Servlet Path---> " + s);
            System.out.println("Before");
            System.out.println(Query);
            try {
                map = new HashMap();
                //   System.out.println("Designation / Rank :"+destRank);
                //System.out.println("Office Selected:"+officeselected);


                // else if(optbase.equalsIgnoreCase("Dest"))

                reportFile =
                        new File(getServletContext().getRealPath("/Reports/Office_Wise_Gender(Specific).jasper"));
                System.out.println("reportFile" + reportFile);
                JasperReport jasperReport =
                    (JasperReport)JRLoader.loadObject(reportFile.getPath());


                map.put("FinOp", FinOp);
                map.put("sep", sep);

                JasperPrint jasperPrint =
                    JasperFillManager.fillReport(jasperReport, map,
                                                 connection);


                if (!reportFile.exists())
                    throw new JRRuntimeException("File J not found. The report design must be compiled first.");


                if (output.equalsIgnoreCase("HTML")) {
                    response.setContentType("text/html");
                    response.setHeader("Content-Disposition",
                                       "attachment;filename=\"Designation_Detail_report.html\"");
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
                    exporter.setParameter(JRExporterParameter.OUTPUT_WRITER,
                                          out);
                    exporter.exportReport();
                    out.flush();
                    out.close();
                } else if (output.equalsIgnoreCase("PDF")) {
                    System.out.println("pdf generated");

                    byte buf[] =
                        JasperExportManager.exportReportToPdf(jasperPrint);
                    response.setContentType("application/pdf");
                    response.setContentLength(buf.length);
                    // response.setHeader("content-disposition", "inline;filename=OpenActionItems.pdf");
                    //response.setContentType("application/force-download");

                    response.setHeader("Content-Disposition",
                                       "attachment;filename=\"Designation_Detail_report.pdf\"");
                    OutputStream out = response.getOutputStream();
                    out.write(buf, 0, buf.length);
                    out.close();
                } else if (output.equalsIgnoreCase("EXCEL")) {

                    response.setContentType("application/vnd.ms-excel");
                    response.setHeader("Content-Disposition",
                                       "attachment;filename=\"Designation_Detail_report.xls\"");
                    JRXlsExporter exporterXLS = new JRXlsExporter();
                    exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT,
                                             jasperPrint);

                    ByteArrayOutputStream xlsReport =
                        new ByteArrayOutputStream();
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
                    ServletOutputStream ouputStream =
                        response.getOutputStream();
                    ouputStream.write(bytes, 0, bytes.length);
                    ouputStream.flush();
                    ouputStream.close();

                } else if (output.equalsIgnoreCase("TXT")) {

                    response.setContentType("text/plain");
                    response.setHeader("Content-Disposition",
                                       "attachment;filename=\"Designation_Detail_report.txt\"");

                    JRTextExporter exporter = new JRTextExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT,
                                          jasperPrint);
                    ByteArrayOutputStream txtReport =
                        new ByteArrayOutputStream();
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
                                          txtReport);
                    exporter.setParameter(JRTextExporterParameter.CHARACTER_WIDTH,
                                          new Integer(200));
                    exporter.setParameter(JRTextExporterParameter.CHARACTER_HEIGHT,
                                          new Integer(50));
                    exporter.exportReport();

                    byte[] bytes;
                    bytes = txtReport.toByteArray();
                    ServletOutputStream ouputStream =
                        response.getOutputStream();
                    ouputStream.write(bytes, 0, bytes.length);
                    ouputStream.flush();
                    ouputStream.close();

                }


            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("After");
        }

    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException,
                                                              IOException {

        doProcess(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException,
                                                               IOException {

        doProcess(request, response);
    }

}
