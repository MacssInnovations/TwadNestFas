package Servlets.FAS.FAS1.ReceiptSystem.Reports;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

public class LJV_Pending_for_Payment extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
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

        String opt = "";
        Connection connection = null;
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
            System.out.println("calling LJV Pending for Payment servlet");

            int txtCB_Year =
                Integer.parseInt(request.getParameter("txtCB_Year"));
            int txtCB_Month =
                Integer.parseInt(request.getParameter("txtCB_Month"));
            String fromdate = request.getParameter("txtfromdate");
            String todate = request.getParameter("txttodate");
            String rtype = request.getParameter("txtoption");
            String cmbAcc_UnitCode = request.getParameter("cmbAcc_UnitCode");
            String cmbOffice_code = request.getParameter("cmbOffice_code");
            String cmbdoctype = request.getParameter("cmbdoctype");

            System.out.println("cmbdoctype is:" + cmbdoctype);
            System.out.println("accounting unit id is:" + cmbAcc_UnitCode);
            System.out.println("office code is:" + cmbOffice_code);
            int accountingunit = Integer.parseInt(cmbAcc_UnitCode);
            int accountingoffice = Integer.parseInt(cmbOffice_code);
            //Date Conversion for Date
            java.util.Date d = null;
            java.util.Date d1 = null;
            System.out.println("here i come");
            //Date Conversion for Date
            if (!fromdate.equalsIgnoreCase("") &&
                !todate.equalsIgnoreCase("")) {
                java.sql.Date dateOfAttachment = null;
                System.out.println("before converting date");
                String dateString = fromdate;
                SimpleDateFormat dateFormat =
                    new SimpleDateFormat("dd/MM/yyyy");

                d = dateFormat.parse(fromdate.trim());
                System.out.println("util date is:" + d);
                dateFormat.applyPattern("yyyy-MM-dd");
                dateString = dateFormat.format(d);
                dateOfAttachment = java.sql.Date.valueOf(dateString);

                java.sql.Date dateto = null;
                System.out.println("before converting date");
                String dateString1 = todate;
                SimpleDateFormat dateFormat1 =
                    new SimpleDateFormat("dd/MM/yyyy");

                d1 = dateFormat1.parse(todate.trim());
                dateFormat1.applyPattern("yyyy-MM-dd");
                dateString1 = dateFormat1.format(d1);
                dateto = java.sql.Date.valueOf(dateString1);

                System.out.println("fromdate" + dateOfAttachment);
                System.out.println("todate" + dateto);
                
                System.out.println("fromdate  :::: dddd" + d);
                System.out.println("todate ::::: d1d1d1" + d1);
            }
            String monthInWords = "";
            if (txtCB_Month == 1)
                monthInWords = "January";
            else if (txtCB_Month == 2)
                monthInWords = "February";
            else if (txtCB_Month == 3)
                monthInWords = "March";
            else if (txtCB_Month == 4)
                monthInWords = "April";
            else if (txtCB_Month == 5)
                monthInWords = "May";
            else if (txtCB_Month == 6)
                monthInWords = "June";
            else if (txtCB_Month == 7)
                monthInWords = "July";
            else if (txtCB_Month == 8)
                monthInWords = "August";
            else if (txtCB_Month == 9)
                monthInWords = "September";
            else if (txtCB_Month == 10)
                monthInWords = "October";
            else if (txtCB_Month == 11)
                monthInWords = "November";
            else if (txtCB_Month == 12)
                monthInWords = "December";


            System.out.println("fromdate" + fromdate);
            System.out.println("todate" + todate);
            if (fromdate.equalsIgnoreCase("") ||
                todate.equalsIgnoreCase("")) // if any date is empty , then list based on cash book month
            {
            	 reportFile =
                         new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/JournalSystem/jasper/LJV_Pending_for_Payment.jasper"));
                 System.out.println("/org/FAS/FAS1/Reports/JournalSystem/jasper/LJV_Pending_for_Payment.jasper");
            } else // if date is not empty , then list based on date also
            {
            	 reportFile =
                         new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/JournalSystem/jasper/LJV_Pending_for_Payment_Period.jasper"));
                 System.out.println("/org/FAS/FAS1/Reports/JournalSystem/jasper/LJV_Pending_for_Payment_Period.jasper");
            }


            if (!reportFile.exists())
                throw new JRRuntimeException("File J not found. The report design must be compiled first.");

            JasperReport jasperReport =
                (JasperReport)JRLoader.loadObject(reportFile.getPath());


           
            //   JasperReport jasperReport =     JasperCompileManager.compileReport(jasperDesign);
            Map map = new HashMap();

            String acc_unit_name = "";
            String acc_unit_office_name = "";
            PreparedStatement ps =
                connection.prepareStatement("select ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=?");
            ps.setInt(1, accountingoffice);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                acc_unit_name = rs.getString("ACCOUNTING_UNIT_NAME");
            ps.close();
            rs.close();

            ps =
  connection.prepareStatement("select OFFICE_NAME from com_mst_offices where OFFICE_ID=?");
            ps.setInt(1, accountingoffice);
            rs = ps.executeQuery();
            if (rs.next())
                acc_unit_office_name = rs.getString("OFFICE_NAME");
            ps.close();
            rs.close();

            //map.put("accountingunitName", acc_unit_name);
           // map.put("accountofficeName", acc_unit_office_name);
            map.put("accountingunitid", accountingunit);
            map.put("accountofficeid", accountingoffice);
            map.put("txtCB_Year", txtCB_Year);
            map.put("txtCB_Month", txtCB_Month);
            map.put("txtfrom", fromdate);
            map.put("txtto", todate);
            map.put("monthInWords", monthInWords);

            JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, map, connection);

            System.out.println("opt::******" + map);

            if (rtype.equalsIgnoreCase("HTML")) {
                response.setContentType("text/html");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"LJV_Pending_for_Payment.html\"");
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
                                   "attachment;filename=\"LJV_Pending_for_Payment.pdf\"");
                OutputStream out = response.getOutputStream();
                out.write(buf, 0, buf.length);
                out.close();
            } else if (rtype.equalsIgnoreCase("EXCEL")) {

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"LJV_Pending_for_Payment.xls\"");
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
                                   "attachment;filename=\"LJV_Pending_for_Payment.txt\"");

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
