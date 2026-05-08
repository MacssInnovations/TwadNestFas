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

public class WQS_ItemDetails_Repserv extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        String CONTENT_TYPE = "text/xml";
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        String xml = null, itemcode = null, itemdesc = null;
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
                stmt = con.createStatement();
                con.clearWarnings();
            } catch (SQLException e) {
                System.out.println("Exception in creating statement:" + e);
            }
        } catch (Exception e) {
            System.out.println("Exception in opening connection:" + e);
        }
        String cmd = request.getParameter("command");
        String cat = request.getParameter("cat");
        xml = "<response>";
        if (cmd.equalsIgnoreCase("changeCat")) {
            xml = xml + "<command>" + cmd + "</command>";
            if (cat.equalsIgnoreCase("chemical")) {
                try {
                    ps =
  con.prepareStatement("select * from(select distinct ITEM_CODE,MAJOR_CATEGORY_CODE from WQS_MST_INV_SUP_ITEM)a inner join(select MAJOR_CATEGORY_CODE,MAJOR_CATEGORY_DESC from WQS_MST_INV_CATEGORY)b on a.MAJOR_CATEGORY_CODE=b.MAJOR_CATEGORY_CODE inner join(select CHEMICAL_CODE,CHE_SPECIFICATION from WQS_MST_INV_CHEMICAL)c on a.ITEM_CODE=c.CHEMICAL_CODE where b.MAJOR_CATEGORY_DESC=?");
                    ps.setString(1, cat);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        xml = xml + "<count>";
                        itemcode = rs.getString("ITEM_CODE");
                        itemdesc = rs.getString("CHE_SPECIFICATION");
                        System.out.println(itemcode + "  " + itemdesc);
                        xml =
 xml + "<itemcode>" + itemcode + "</itemcode><itemdesc>" + itemdesc +
   "</itemdesc>";
                        xml = xml + "</count>";
                    }
                    xml = xml + "<flag>Success</flag>";
                } catch (Exception e) {
                    System.out.println("Err in chemical:" + e.getMessage());
                }
            } else if (cat.equalsIgnoreCase("glassware")) {
                try {
                    ps =
  con.prepareStatement("select * from(select distinct ITEM_CODE,MAJOR_CATEGORY_CODE from WQS_MST_INV_SUP_ITEM)a inner join(select MAJOR_CATEGORY_CODE,MAJOR_CATEGORY_DESC from WQS_MST_INV_CATEGORY)b on a.MAJOR_CATEGORY_CODE=b.MAJOR_CATEGORY_CODE inner join(select GLASSWARE_CODE,GLASSWARE_SPEC from WQS_MST_INV_GLASSWARE)c on a.ITEM_CODE=c.GLASSWARE_CODE where b.MAJOR_CATEGORY_DESC=?");
                    ps.setString(1, cat);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        xml = xml + "<count>";
                        itemcode = rs.getString("ITEM_CODE");
                        itemdesc = rs.getString("GLASSWARE_SPEC");
                        System.out.println(itemcode + "  " + itemdesc);
                        xml =
 xml + "<itemcode>" + itemcode + "</itemcode><itemdesc>" + itemdesc +
   "</itemdesc>";
                        xml = xml + "</count>";
                    }
                    xml = xml + "<flag>Success</flag>";
                } catch (Exception e) {
                    System.out.println("Err in chemical:" + e.getMessage());
                }
            } else if (cat.equalsIgnoreCase("Miscellaneous")) {
                try {
                    ps =
  con.prepareStatement("select * from(select distinct ITEM_CODE,MAJOR_CATEGORY_CODE from WQS_MST_INV_SUP_ITEM)a inner join(select MAJOR_CATEGORY_CODE,MAJOR_CATEGORY_DESC from WQS_MST_INV_CATEGORY)b on a.MAJOR_CATEGORY_CODE=b.MAJOR_CATEGORY_CODE inner join(select MISCELLANEOUS_CODE,MISCELLANEOUS_SPEC from WQS_MST_INV_MISCELLANEOUS)c on a.ITEM_CODE=c.MISCELLANEOUS_CODE where b.MAJOR_CATEGORY_DESC=?");
                    ps.setString(1, cat);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        xml = xml + "<count>";
                        itemcode = rs.getString("ITEM_CODE");
                        itemdesc = rs.getString("MISCELLANEOUS_SPEC");
                        System.out.println(itemcode + "  " + itemdesc);
                        xml =
 xml + "<itemcode>" + itemcode + "</itemcode><itemdesc>" + itemdesc +
   "</itemdesc>";
                        xml = xml + "</count>";
                    }
                    xml = xml + "<flag>Success</flag>";
                } catch (Exception e) {
                    System.out.println("Err in chemical:" + e.getMessage());
                }
            } else {
                try {
                    ps =
  con.prepareStatement("select * from(select distinct ITEM_CODE,MAJOR_CATEGORY_CODE from WQS_MST_INV_SUP_ITEM)a inner join(select MAJOR_CATEGORY_CODE,MAJOR_CATEGORY_DESC from WQS_MST_INV_CATEGORY)b on a.MAJOR_CATEGORY_CODE=b.MAJOR_CATEGORY_CODE inner join(select INST_CATEGORY,INST_CATEGORY_SPEC from WQS_MST_INV_INSTRUMENT)c on a.ITEM_CODE=c.INST_CATEGORY where b.MAJOR_CATEGORY_DESC=?");
                    ps.setString(1, cat);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        xml = xml + "<count>";
                        itemcode = rs.getString("ITEM_CODE");
                        itemdesc = rs.getString("INST_CATEGORY_SPEC");
                        System.out.println(itemcode + "  " + itemdesc);
                        xml =
 xml + "<itemcode>" + itemcode + "</itemcode><itemdesc>" + itemdesc +
   "</itemdesc>";
                        xml = xml + "</count>";
                    }
                    xml = xml + "<flag>Success</flag>";
                } catch (Exception e) {
                    System.out.println("Err in chemical:" + e.getMessage());
                }
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
            System.out.println("calling servlet");
            reportFile =
                    new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_ItemDetailsRep.jasper"));
            System.out.println("hhhh");

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

            String[] icode = request.getParameter("item").split("--");
            String item = icode[0];
            map.put("item", item);
            jasperPrint = JasperFillManager.fillReport(jasperReport, map, con);

            String rtype = request.getParameter("cmbReportType");
            if (rtype.equalsIgnoreCase("HTML")) {
                response.setContentType("text/html");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"ItemDetailsReport.html\"");
                PrintWriter out = response.getWriter();
                JRHtmlExporter exporter = new JRHtmlExporter();
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
                                   "attachment;filename=\"ItemDetailsReport.pdf\"");
                OutputStream out = response.getOutputStream();
                out.write(buf, 0, buf.length);
                out.close();
            } else if (rtype.equalsIgnoreCase("EXCEL")) {
                System.out.println("test1");
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"ItemDetailsReport.xls\"");
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
                                   "attachment;filename=\"ItemDetailsReport.txt\"");

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
