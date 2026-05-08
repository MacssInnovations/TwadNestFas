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

public class ListofEmp_YearExp_Serv extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs1 = null;

        System.out.println("inside servlet");

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

        System.out.println("inside servlet");

        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-store");

        String xml = "";
        String strCommand = "";
        int service = 0;

        strCommand = request.getParameter("command");
        service = Integer.parseInt(request.getParameter("cmbsgroup"));

        System.out.println("command.." + strCommand);
        System.out.println("service.." + service);

        xml = "<response>";

        if (strCommand.equalsIgnoreCase("Desig")) {

            int count = 0;

            try {
                ps =
  connection.prepareStatement("select distinct designation_id,designation,service_group_id from hrm_mst_designations" +
                              " where service_group_id=?" +
                              " order by designation");

                ps.setInt(1, service);
                rs1 = ps.executeQuery();
                xml = xml + "<flag>success</flag>";
                while (rs1.next()) {

                    xml =
 xml + "<option><id>" + rs1.getInt("designation_id") + "</id><name>" +
   rs1.getString("designation") + "</name></option>";
                    count++;
                }

                if (count == 0) {
                    xml = xml + "<flag>failure</flag>";
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                xml = xml + "<flag>failure</flag>";
            }
        }

        else if (strCommand.equalsIgnoreCase("Cadre")) {
            int count = 0;

            try {

                String sql =
                    "select distinct cadre_id,cadre_name from ( select cadre_id,service_group_id from hrm_mst_designations where service_group_id=? ) a left outer join ( select cadre_id as cad_id,cadre_name from hrm_mst_cadre ) b on a.cadre_id=b.cad_id order by cadre_name";
                ps = connection.prepareStatement(sql);
                ps.setInt(1, service);
                rs1 = ps.executeQuery();
                xml = xml + "<flag>success</flag>";
                while (rs1.next()) {

                    xml =
 xml + "<option><id>" + rs1.getInt("cadre_id") + "</id><name>" +
   rs1.getString("cadre_name") + "</name></option>";
                    count++;
                }

                if (count == 0) {
                    xml = xml + "<flag>failure</flag>";
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                xml = xml + "<flag>failure</flag>";
            }
        }


        xml = xml + "</response>";
        out.println(xml);
        System.out.println(xml);

        out.close();
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        response.setContentType(CONTENT_TYPE);
        // PrintWriter out = response.getWriter();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs1 = null;


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

        String option = request.getParameter("optselect");
        System.out.println("option selected.." + option);

        File reportFile = null;

        Map map = null;
        map = new HashMap();

        if (option.equalsIgnoreCase("Desig")) {
            int des = Integer.parseInt(request.getParameter("cmbDesignation"));
            System.out.println("designation selected.." + des);

            reportFile =
                    new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Listof_Employees_Desig.jasper"));
            map.put("desig", des);
        }

        else if (option.equalsIgnoreCase("Cadre")) {
            int service_gp =
                Integer.parseInt(request.getParameter("cmbsgroup1"));
            int cad = Integer.parseInt(request.getParameter("cmbCadre"));
            System.out.println("cadre..." + cad);
            String desig_cad = "";
            String cad_name = "";

            try {
                ps =
  connection.prepareStatement("select designation_id,cadre_name from ( select designation_id,cadre_id from hrm_mst_designations where cadre_id=? and SERVICE_GROUP_ID=? ) a left outer join ( select cadre_id as cad_id,cadre_name from hrm_mst_cadre) b on a.cadre_id=b.cad_id");
                ps.setInt(1, cad);
                ps.setInt(2, service_gp);
                rs1 = ps.executeQuery();
                int j = 0;
                while (rs1.next()) {
                    cad_name = rs1.getString("cadre_name");
                    desig_cad += rs1.getInt("designation_id") + ",";
                    j++;

                }

                System.out.println("cad desig id..." + desig_cad);
                if (j != 0) {
                    desig_cad = desig_cad.substring(0, desig_cad.length() - 1);
                }
                System.out.println("final.." + desig_cad);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            reportFile =
                    new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Listof_Employees_Cadre.jasper"));
            map.put("cadre", desig_cad);
            map.put("name", cad_name);

        }


        if (!reportFile.exists())
            throw new JRRuntimeException("File J not found. The report design must be compiled first.");

        try {
            JasperReport jasperReport =
                (JasperReport)JRLoader.loadObject(reportFile.getPath());


            JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, map, connection);

            String rtype = request.getParameter("cmbReportType");
            System.out.println("option selected.." + rtype);

            if (rtype.equalsIgnoreCase("HTML")) {
                response.setContentType("text/html");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"ListofEmployees_Senioritywise.html\"");
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
            }

            else if (rtype.equalsIgnoreCase("PDF")) {
                byte buf[] =
                    JasperExportManager.exportReportToPdf(jasperPrint);
                response.setContentType("application/pdf");
                response.setContentLength(buf.length);
                // response.setHeader("content-disposition", "inline;filename=OpenActionItems.pdf");
                //response.setContentType("application/force-download");

                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"ListofEmployees_Senioritywise.pdf\"");
                OutputStream out1 = response.getOutputStream();
                out1.write(buf, 0, buf.length);
                out1.close();
            }

            else if (rtype.equalsIgnoreCase("EXCEL")) {

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"ListofEmployees_Senioritywise.xls\"");
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
                                   "attachment;filename=\"ListofEmployees_Senioritywise.txt\"");

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
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        //out.close();
    }
}
