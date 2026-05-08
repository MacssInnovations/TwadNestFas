package Servlets.WQS.WQS1.Inventory.Reports.servlets;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import java.util.Calendar;
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

public class WQS_SupplierItem_Repserv extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        String CONTENT_TYPE = "text/xml";
        response.setContentType(CONTENT_TYPE);
        System.out.println("Welcome to servlet");
        PrintWriter out = response.getWriter();
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        String xml = null, catcode = null, catdesc = null;
        int cnt = 0;

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

            ConnectionString =
                    strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
                    ":" + strsid.trim();
            Class.forName(strDriver.trim());
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
            try {
                st = con.createStatement();
                con.clearWarnings();
            } catch (SQLException e) {
                System.out.println("Exception in creating statement:" + e);
            }
        } catch (Exception e) {
            System.out.println("Exception in opening connection:" + e);
        }
        String cmd = request.getParameter("command");
        int supcode = Integer.parseInt(request.getParameter("sup"));
        xml = "<response>";
        if (cmd.equalsIgnoreCase("changeSupplier")) {
            xml = xml + "<command>" + cmd + "</command>";
            try {
                System.out.println("inside try");
                ps =
  con.prepareStatement("select * from(select distinct(MAJOR_CATEGORY_CODE) from WQS_MST_INV_SUP_ITEM where SUPPLIER_CODE=?)a inner join(select MAJOR_CATEGORY_CODE,MAJOR_CATEGORY_DESC from WQS_MST_INV_CATEGORY)b on a.MAJOR_CATEGORY_CODE=b.MAJOR_CATEGORY_CODE");
                ps.setInt(1, supcode);
                rs = ps.executeQuery();
                while (rs.next()) {
                    xml = xml + "<count>";
                    catcode = rs.getString("MAJOR_CATEGORY_CODE");
                    catdesc = rs.getString("MAJOR_CATEGORY_DESC");
                    xml =
 xml + "<catcode>" + catcode + "</catcode><catdesc>" + catdesc + "</catdesc>";
                    xml = xml + "</count>";
                    cnt = cnt + 1;
                }
                if (cnt > 0) {
                    System.out.println("Success");
                    xml = xml + "<flag>Success</flag>";
                } else {
                    System.out.println("Failure");
                    xml = xml + "<flag>Failure</flag>";
                }
            } catch (Exception e) {
                System.out.println("Err in changesupplier:" + e.getMessage());
            }
        }
        xml = xml + "</response>";
        System.out.println(xml);
        out.println(xml);
        out.close();
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        System.out.println("Welcome to servlet");
        Connection con = null;
        Statement stmt = null;
        JasperPrint jasperPrint = null;

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

            ConnectionString =
                    strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
                    ":" + strsid.trim();
            Class.forName(strDriver.trim());
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
            try {
                stmt = con.createStatement();
                con.clearWarnings();
            } catch (SQLException e) {
                System.out.println("Exception in creating statement:" + e);
            }
        } catch (Exception e) {
            System.out.println("Exception in opening connection:" + e);
        }
        JasperDesign jasperDesign = null;
        File reportFile = null;
        try {
            String[] cat = request.getParameter("cat").split("--");
            String catcode = cat[1];
            System.out.println(catcode);
            if (catcode.equalsIgnoreCase("chemical")) {
                System.out.println("calling servlet");
                reportFile =
                        new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_SupplierItem_ChemRep.jasper"));
                System.out.println("after path");

                if (!reportFile.exists()) {
                    System.out.println("does not exsist");
                    throw new JRRuntimeException("File J not found. The report design must be compiled first.");
                }
                System.out.println(JRLoader.loadObject(reportFile.getPath()));
                JasperReport jasperReport =
                    (JasperReport)JRLoader.loadObject(reportFile.getPath());
                Map map = null;
                map = new HashMap();
                System.out.println("map:" + map);
                System.out.println("hai");

                String[] sid = request.getParameter("sid").split("--");
                String scode = sid[0];
                System.out.println("Supplier Code:" + scode);

                map.put("scode", scode);
                jasperPrint =
                        JasperFillManager.fillReport(jasperReport, map, con);
            } else if (catcode.equalsIgnoreCase("instrument")) {
                System.out.println("calling servlet");
                reportFile =
                        new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_SupplierItem_InsRep.jasper"));
                System.out.println("after path");

                if (!reportFile.exists()) {
                    System.out.println("does not exsist");
                    throw new JRRuntimeException("File J not found. The report design must be compiled first.");
                }
                System.out.println(JRLoader.loadObject(reportFile.getPath()));
                JasperReport jasperReport =
                    (JasperReport)JRLoader.loadObject(reportFile.getPath());
                Map map = null;
                map = new HashMap();
                System.out.println("map:" + map);
                System.out.println("hai");

                String[] sid = request.getParameter("sid").split("--");
                String scode = sid[0];
                System.out.println("Supplier Code:" + scode);

                map.put("scode", scode);
                jasperPrint =
                        JasperFillManager.fillReport(jasperReport, map, con);
            } else if (catcode.equalsIgnoreCase("glassware")) {
                System.out.println("calling servlet");
                reportFile =
                        new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_SupplierItem_GlasRep.jasper"));
                System.out.println("after path");

                if (!reportFile.exists()) {
                    System.out.println("does not exsist");
                    throw new JRRuntimeException("File J not found. The report design must be compiled first.");
                }
                System.out.println(JRLoader.loadObject(reportFile.getPath()));
                JasperReport jasperReport =
                    (JasperReport)JRLoader.loadObject(reportFile.getPath());
                Map map = null;
                map = new HashMap();
                System.out.println("map:" + map);
                System.out.println("hai");

                String[] sid = request.getParameter("sid").split("--");
                String scode = sid[0];
                System.out.println("Supplier Code:" + scode);

                map.put("scode", scode);
                jasperPrint =
                        JasperFillManager.fillReport(jasperReport, map, con);
            } else {
                System.out.println("calling servlet");
                reportFile =
                        new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_SupplierItem_MiscRep.jasper"));
                System.out.println("after path");

                if (!reportFile.exists()) {
                    System.out.println("does not exsist");
                    throw new JRRuntimeException("File J not found. The report design must be compiled first.");
                }
                System.out.println(JRLoader.loadObject(reportFile.getPath()));
                JasperReport jasperReport =
                    (JasperReport)JRLoader.loadObject(reportFile.getPath());
                Map map = null;
                map = new HashMap();
                System.out.println("map:" + map);
                System.out.println("hai");

                String[] sid = request.getParameter("sid").split("--");
                String scode = sid[0];
                System.out.println("Supplier Code:" + scode);

                map.put("scode", scode);
                jasperPrint =
                        JasperFillManager.fillReport(jasperReport, map, con);
            }
            String rtype = request.getParameter("cmbReportType");
            if (rtype.equalsIgnoreCase("HTML")) {
                response.setContentType("text/html");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"SupplierItemReport.html\"");
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
                                   "attachment;filename=\"SupplierItemReport.pdf\"");
                OutputStream out = response.getOutputStream();
                out.write(buf, 0, buf.length);
                out.close();
            } else if (rtype.equalsIgnoreCase("EXCEL")) {
                System.out.println("test1");
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"SupplierItemReport.xls\"");
                JRXlsExporter exporterXLS = new JRXlsExporter();
                exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT,
                                         jasperPrint);
                System.out.println("test2");
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
                System.out.println("test3");
                byte[] bytes;
                bytes = xlsReport.toByteArray();
                ServletOutputStream ouputStream = response.getOutputStream();
                ouputStream.write(bytes, 0, bytes.length);
                System.out.println("test4");
                ouputStream.flush();
                ouputStream.close();
                System.out.println("test5");
            } else if (rtype.equalsIgnoreCase("TXT")) {

                response.setContentType("text/plain");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"SupplierItemReport.txt\"");

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
