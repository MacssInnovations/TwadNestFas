package Servlets.FAS.FAS1.ReceiptSystem.Reports;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
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


public class View_Receipt_count_Region extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        HttpSession session = request.getSession(false);
        try {

            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");

            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }

        //String param=request.getParameter("txtRegionId");

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
            System.out.println("calling servlet");
            System.out.println("here i come");
            int txtCB_Year =
                Integer.parseInt(request.getParameter("txtCB_Year"));
            int txtCB_Month =
                Integer.parseInt(request.getParameter("txtCB_Month"));
            String fromdate = request.getParameter("txtfromdate");
            String todate = request.getParameter("txttodate");
            String rtype = request.getParameter("txtoption");
            java.util.Date d = null;
            java.util.Date d1 = null;

            String cmd = request.getParameter("Command");

            //System.out.println("here i come");
            //Date Conversion for Date
            if (cmd.equalsIgnoreCase("region")) {
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
                }
            }
            //String cmbOffice_code=request.getParameter("cmbOffice_code");
            String cmbdoctype = request.getParameter("cmbdoctype");

            System.out.println("cmbdoctype is:" + cmbdoctype);
            int txtRegionId =
                Integer.parseInt(request.getParameter("txtRegionId"));
            System.out.println("Region id is:" + txtRegionId);
            String offname = null;
            try {
                PreparedStatement psnew =
                    connection.prepareStatement("select office_name from com_mst_offices where office_id=?");
                psnew.setInt(1, txtRegionId);
                ResultSet rsnew = psnew.executeQuery();
                while (rsnew.next()) {
                    offname = rsnew.getString("office_name");
                }

            } catch (Exception e) {
                System.out.println("error in getting region name");
            }
            System.out.println("office name is " + offname);
            //Date Conversion for Date
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


            if (cmd.equalsIgnoreCase("trial")) {
                System.out.println("here coming");
                if (txtRegionId == 5000)
                    reportFile =
                            new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/VoucherMonitor/TrialBalnce_report_HO.jasper"));
                else if (txtRegionId == 101)
                    reportFile =
                            new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/TB_Generated_Offices_AllRegion.jasper"));
                else
                    reportFile =
                            new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/VoucherMonitor/oneTrialBalnce_report_region.jasper"));
            } else {
                if (fromdate.equalsIgnoreCase("") ||
                    todate.equalsIgnoreCase("")) // if any date is empty , then list based on cash book month
                {
                    if (txtRegionId == 5000)
                        reportFile =
                                new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/VoucherMonitor/ViewReceipt_Count_WithoutDate_HO.jasper"));
                    else
                        reportFile =
                                new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/VoucherMonitor/ViewReceipt_Count_WithoutDate_Region.jasper"));
                } else // if date is not empty , then list based on date also
                {
                    if (txtRegionId == 5000)
                        reportFile =
                                new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/VoucherMonitor/ViewReceipt_Count_WithDate_HO.jasper"));
                    else
                        reportFile =
                                new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/VoucherMonitor/ViewReceipt_Count_WithDate_Region.jasper"));
                }
            }

            System.out.println("reportFile:" + reportFile);
            if (!reportFile.exists())
                throw new JRRuntimeException("File J not found. The report design must be compiled first.");

            JasperReport jasperReport =
                (JasperReport)JRLoader.loadObject(reportFile.getPath());


            System.out.println("opt::" + opt);
            //   JasperReport jasperReport =     JasperCompileManager.compileReport(jasperDesign);
            Map map = new HashMap();

            /*  String acc_unit_name="";
            String acc_unit_office_name="";
            PreparedStatement ps=connection.prepareStatement("select ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=?");
            ps.setInt(1,accountingoffice);
            ResultSet rs=ps.executeQuery();
            if(rs.next())
                acc_unit_name=rs.getString("ACCOUNTING_UNIT_NAME");
            ps.close();
            rs.close();

            ps=connection.prepareStatement("select OFFICE_NAME from com_mst_offices where OFFICE_ID=?");
            ps.setInt(1,accountingoffice);
            rs=ps.executeQuery();
            if(rs.next())
                acc_unit_office_name=rs.getString("OFFICE_NAME");
            ps.close();
            rs.close();

            map.put("accountingunitName",acc_unit_name);
            map.put("accountofficeName",acc_unit_office_name);
            map.put("accountingunitid",accountingunit);
            map.put("accountofficeid",accountingoffice); */
            PreparedStatement ps1=null;
            int CR_ENTRIES=0;
            int DR_ENTRIES=0;
            if (!cmd.equalsIgnoreCase("trial")) {
            if (fromdate.equalsIgnoreCase("") ||
                    todate.equalsIgnoreCase("")) // if any date is empty , then list based on cash book month
                {
            	 ps1 =connection.prepareStatement("select aa.*,bb.DR_ENTRIES from (SELECT sum(TOTAMT) AS CR_ENTRIES,\n"+
            		" ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID FROM (SELECT COUNT(B.VOUCHER_NO)  AS TOTAL_COUNT,\n"+
            		" SUM(B.AMOUNT) AS TOTAMT,A.ACCOUNTING_UNIT_ID AS ACCOUNTING_UNIT_ID, A.ACCOUNTING_FOR_OFFICE_ID as ACCOUNTING_FOR_OFFICE_ID \n"+
            		" FROM FAS_PAYMENT_MASTER a,FAS_PAYMENT_TRANSACTION B WHERE A.ACCOUNTING_UNIT_ID=B.ACCOUNTING_UNIT_ID \n"+
            		" AND A.ACCOUNTING_FOR_OFFICE_ID=B.ACCOUNTING_FOR_OFFICE_ID AND A.CASHBOOK_YEAR  =B.CASHBOOK_YEAR \n"+
            		" AND A.CASHBOOK_MONTH =B.CASHBOOK_MONTH AND A.VOUCHER_NO =B.VOUCHER_NO \n " +
            		" AND B.ACCOUNTING_FOR_OFFICE_ID="+txtRegionId+"AND B.CASHBOOK_YEAR ="+txtCB_Year+"AND B.CASHBOOK_MONTH="+txtCB_Month+"\n " +
            		" AND B.CR_DR_INDICATOR='CR' AND a.PAYMENT_STATUS!='C' GROUP BY A.ACCOUNTING_UNIT_ID, A.ACCOUNTING_FOR_OFFICE_ID ) as opt1 GROUP BY ACCOUNTING_UNIT_ID,\n" +
            		" ACCOUNTING_FOR_OFFICE_ID)aa "  +   "\n " +       		
            		" LEFT OUTER JOIN (SELECT sum(TOTAMT) AS DR_ENTRIES,ACCOUNTING_UNIT_ID FROM (SELECT COUNT(B.RECEIPT_NO)    AS TOTAL_COUNT,"+"\n " +
            		" SUM(B.AMOUNT) AS TOTAMT,A.ACCOUNTING_UNIT_ID AS ACCOUNTING_UNIT_ID,A.ACCOUNTING_FOR_OFFICE_ID  as ACCOUNTING_FOR_OFFICE_ID"+"\n " +
            		" FROM FAS_RECEIPT_MASTER A,FAS_RECEIPT_TRANSACTION B WHERE A.ACCOUNTING_UNIT_ID    =B.ACCOUNTING_UNIT_ID " +"\n " +
            		" AND A.ACCOUNTING_FOR_OFFICE_ID=B.ACCOUNTING_FOR_OFFICE_ID AND A.CASHBOOK_YEAR =B.CASHBOOK_YEAR AND A.CASHBOOK_MONTH =B.CASHBOOK_MONTH" +"\n " +
            		" AND A.RECEIPT_NO =B.RECEIPT_NO AND B.ACCOUNTING_FOR_OFFICE_ID="+txtRegionId+  "\n " +
            		" AND B.CASHBOOK_YEAR ="+txtCB_Year+"AND B.CASHBOOK_MONTH="+txtCB_Month+"AND B.CR_DR_INDICATOR         ='DR'"+"\n " +
            		" AND A.RECEIPT_STATUS !='C' GROUP BY A.ACCOUNTING_UNIT_ID,A.ACCOUNTING_FOR_OFFICE_ID) as opt2 GROUP BY ACCOUNTING_UNIT_ID,\n"+
            		" ACCOUNTING_FOR_OFFICE_ID)bb on aa.ACCOUNTING_UNIT_ID=bb.ACCOUNTING_UNIT_ID");
           
            System.out.println("select aa.*,bb.DR_ENTRIES from (SELECT sum(TOTAMT) AS CR_ENTRIES,\n"+
            		" ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID FROM (SELECT COUNT(B.VOUCHER_NO)  AS TOTAL_COUNT,\n"+
            		" SUM(B.AMOUNT) AS TOTAMT,A.ACCOUNTING_UNIT_ID AS ACCOUNTING_UNIT_ID, A.ACCOUNTING_FOR_OFFICE_ID as ACCOUNTING_FOR_OFFICE_ID \n"+
            		" FROM FAS_PAYMENT_MASTER a,FAS_PAYMENT_TRANSACTION B WHERE A.ACCOUNTING_UNIT_ID=B.ACCOUNTING_UNIT_ID \n"+
            		" AND A.ACCOUNTING_FOR_OFFICE_ID=B.ACCOUNTING_FOR_OFFICE_ID AND A.CASHBOOK_YEAR  =B.CASHBOOK_YEAR \n"+
            		" AND A.CASHBOOK_MONTH =B.CASHBOOK_MONTH AND A.VOUCHER_NO =B.VOUCHER_NO \n " +
            		" AND B.ACCOUNTING_FOR_OFFICE_ID="+txtRegionId+"AND B.CASHBOOK_YEAR ="+txtCB_Year+"AND B.CASHBOOK_MONTH="+txtCB_Month+"\n " +
            		" AND B.CR_DR_INDICATOR='CR' AND a.PAYMENT_STATUS!='C' GROUP BY A.ACCOUNTING_UNIT_ID, A.ACCOUNTING_FOR_OFFICE_ID ) GROUP BY ACCOUNTING_UNIT_ID,\n" +
            		" ACCOUNTING_FOR_OFFICE_ID)aa "  +   "\n " +       		
            		" LEFT OUTER JOIN (SELECT sum(TOTAMT) AS DR_ENTRIES,ACCOUNTING_UNIT_ID FROM (SELECT COUNT(B.RECEIPT_NO)    AS TOTAL_COUNT,"+"\n " +
            		" SUM(B.AMOUNT) AS TOTAMT,A.ACCOUNTING_UNIT_ID AS ACCOUNTING_UNIT_ID,A.ACCOUNTING_FOR_OFFICE_ID  as ACCOUNTING_FOR_OFFICE_ID"+"\n " +
            		" FROM FAS_RECEIPT_MASTER A,FAS_RECEIPT_TRANSACTION B WHERE A.ACCOUNTING_UNIT_ID    =B.ACCOUNTING_UNIT_ID " +"\n " +
            		" AND A.ACCOUNTING_FOR_OFFICE_ID=B.ACCOUNTING_FOR_OFFICE_ID AND A.CASHBOOK_YEAR =B.CASHBOOK_YEAR AND A.CASHBOOK_MONTH =B.CASHBOOK_MONTH" +"\n " +
            		" AND A.RECEIPT_NO =B.RECEIPT_NO AND B.ACCOUNTING_FOR_OFFICE_ID="+txtRegionId+  "\n " +
            		" AND B.CASHBOOK_YEAR ="+txtCB_Year+"AND B.CASHBOOK_MONTH="+txtCB_Month+"AND B.CR_DR_INDICATOR         ='DR'"+"\n " +
            		" AND A.RECEIPT_STATUS! ='C' GROUP BY A.ACCOUNTING_UNIT_ID,A.ACCOUNTING_FOR_OFFICE_ID)GROUP BY ACCOUNTING_UNIT_ID,\n"+
            		" ACCOUNTING_FOR_OFFICE_ID)bb on aa.ACCOUNTING_UNIT_ID=bb.ACCOUNTING_UNIT_ID");
           
                }
            else
            {
            	 ps1 =connection.prepareStatement("select aa.*,bb.DR_ENTRIES from (SELECT sum(TOTAMT) AS CR_ENTRIES,\n"+
                		" ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID FROM (SELECT COUNT(B.VOUCHER_NO)  AS TOTAL_COUNT,\n"+
                		" SUM(B.AMOUNT) AS TOTAMT,A.ACCOUNTING_UNIT_ID AS ACCOUNTING_UNIT_ID, A.ACCOUNTING_FOR_OFFICE_ID as ACCOUNTING_FOR_OFFICE_ID \n"+
                		" FROM FAS_PAYMENT_MASTER a,FAS_PAYMENT_TRANSACTION B WHERE A.ACCOUNTING_UNIT_ID=B.ACCOUNTING_UNIT_ID \n"+
                		" AND A.ACCOUNTING_FOR_OFFICE_ID=B.ACCOUNTING_FOR_OFFICE_ID AND A.CASHBOOK_YEAR  =B.CASHBOOK_YEAR \n"+
                		" AND A.CASHBOOK_MONTH =B.CASHBOOK_MONTH AND A.VOUCHER_NO =B.VOUCHER_NO \n " +
                		" AND B.ACCOUNTING_FOR_OFFICE_ID="+txtRegionId+" AND PAYMENT_DATE BETWEEN to_date('"+fromdate+"','DD/MM/YYYY') AND to_date('"+todate+"','DD/MM/YYYY')" + "\n" +
                		" AND B.CR_DR_INDICATOR='CR' AND a.PAYMENT_STATUS!='C' GROUP BY A.ACCOUNTING_UNIT_ID, A.ACCOUNTING_FOR_OFFICE_ID ) as opt1 GROUP BY ACCOUNTING_UNIT_ID,\n" +
                		" ACCOUNTING_FOR_OFFICE_ID)aa "  +   "\n " +       		
                		" LEFT OUTER JOIN (SELECT sum(TOTAMT) AS DR_ENTRIES,ACCOUNTING_UNIT_ID FROM (SELECT COUNT(B.RECEIPT_NO)    AS TOTAL_COUNT,"+"\n " +
                		" SUM(B.AMOUNT) AS TOTAMT,A.ACCOUNTING_UNIT_ID AS ACCOUNTING_UNIT_ID,A.ACCOUNTING_FOR_OFFICE_ID  as ACCOUNTING_FOR_OFFICE_ID"+"\n " +
                		" FROM FAS_RECEIPT_MASTER A,FAS_RECEIPT_TRANSACTION B WHERE A.ACCOUNTING_UNIT_ID    =B.ACCOUNTING_UNIT_ID " +"\n " +
                		" AND A.ACCOUNTING_FOR_OFFICE_ID=B.ACCOUNTING_FOR_OFFICE_ID AND A.CASHBOOK_YEAR =B.CASHBOOK_YEAR AND A.CASHBOOK_MONTH =B.CASHBOOK_MONTH" +"\n " +
                		" AND A.RECEIPT_NO =B.RECEIPT_NO AND B.ACCOUNTING_FOR_OFFICE_ID="+txtRegionId+  "\n " +
                		" AND RECEIPT_DATE  BETWEEN to_date('"+fromdate+"','DD/MM/YYYY') AND to_date('"+todate+"','DD/MM/YYYY')  AND B.CR_DR_INDICATOR         ='DR'\n" +
                		" AND A.RECEIPT_STATUS! ='C' GROUP BY A.ACCOUNTING_UNIT_ID,A.ACCOUNTING_FOR_OFFICE_ID) as opt2 GROUP BY ACCOUNTING_UNIT_ID,\n"+
                		" ACCOUNTING_FOR_OFFICE_ID)bb on aa.ACCOUNTING_UNIT_ID=bb.ACCOUNTING_UNIT_ID");	
            
            System.out.println("select aa.*,bb.DR_ENTRIES from (SELECT sum(TOTAMT) AS CR_ENTRIES,\n"+
                		" ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID FROM (SELECT COUNT(B.VOUCHER_NO)  AS TOTAL_COUNT,\n"+
                		" SUM(B.AMOUNT) AS TOTAMT,A.ACCOUNTING_UNIT_ID AS ACCOUNTING_UNIT_ID, A.ACCOUNTING_FOR_OFFICE_ID as ACCOUNTING_FOR_OFFICE_ID \n"+
                		" FROM FAS_PAYMENT_MASTER a,FAS_PAYMENT_TRANSACTION B WHERE A.ACCOUNTING_UNIT_ID=B.ACCOUNTING_UNIT_ID \n"+
                		" AND A.ACCOUNTING_FOR_OFFICE_ID=B.ACCOUNTING_FOR_OFFICE_ID AND A.CASHBOOK_YEAR  =B.CASHBOOK_YEAR \n"+
                		" AND A.CASHBOOK_MONTH =B.CASHBOOK_MONTH AND A.VOUCHER_NO =B.VOUCHER_NO \n " +
                		" AND B.ACCOUNTING_FOR_OFFICE_ID="+txtRegionId+" AND PAYMENT_DATE BETWEEN to_date('"+fromdate+"','DD/MM/YYYY') AND to_date('"+todate+"','DD/MM/YYYY')" + "\n" +
                		" AND B.CR_DR_INDICATOR='CR' AND a.PAYMENT_STATUS!='C' GROUP BY A.ACCOUNTING_UNIT_ID, A.ACCOUNTING_FOR_OFFICE_ID ) GROUP BY ACCOUNTING_UNIT_ID,\n" +
                		" ACCOUNTING_FOR_OFFICE_ID)aa "  +   "\n " +       		
                		" LEFT OUTER JOIN (SELECT sum(TOTAMT) AS DR_ENTRIES,ACCOUNTING_UNIT_ID FROM (SELECT COUNT(B.RECEIPT_NO)    AS TOTAL_COUNT,"+"\n " +
                		" SUM(B.AMOUNT) AS TOTAMT,A.ACCOUNTING_UNIT_ID AS ACCOUNTING_UNIT_ID,A.ACCOUNTING_FOR_OFFICE_ID  as ACCOUNTING_FOR_OFFICE_ID"+"\n " +
                		" FROM FAS_RECEIPT_MASTER A,FAS_RECEIPT_TRANSACTION B WHERE A.ACCOUNTING_UNIT_ID    =B.ACCOUNTING_UNIT_ID " +"\n " +
                		" AND A.ACCOUNTING_FOR_OFFICE_ID=B.ACCOUNTING_FOR_OFFICE_ID AND A.CASHBOOK_YEAR =B.CASHBOOK_YEAR AND A.CASHBOOK_MONTH =B.CASHBOOK_MONTH" +"\n " +
                		" AND A.RECEIPT_NO =B.RECEIPT_NO AND B.ACCOUNTING_FOR_OFFICE_ID="+txtRegionId+  "\n " +
                		" AND RECEIPT_DATE  BETWEEN to_date('"+fromdate+"','DD/MM/YYYY') AND to_date('"+todate+"','DD/MM/YYYY') AND B.CR_DR_INDICATOR         ='DR'\n" +
                		" AND A.RECEIPT_STATUS! ='C' GROUP BY A.ACCOUNTING_UNIT_ID,A.ACCOUNTING_FOR_OFFICE_ID)GROUP BY ACCOUNTING_UNIT_ID,\n"+
                		" ACCOUNTING_FOR_OFFICE_ID)bb on aa.ACCOUNTING_UNIT_ID=bb.ACCOUNTING_UNIT_ID");
            
            
            
            }
            
            ResultSet rs1 = ps1.executeQuery();
            if(rs1.next())
            {
            	CR_ENTRIES = rs1.getInt("CR_ENTRIES");
                DR_ENTRIES = rs1.getInt("DR_ENTRIES");
            }
            ps1.close();
            rs1.close();
            
            }
            
            System.out.println("cashbook_month..." + txtCB_Month);
            System.out.println("cashbook_year..." + txtCB_Year);
            System.out.println("month value..." + monthInWords);

            map.put("txtRegionId", txtRegionId);
            map.put("txtCB_Year", txtCB_Year);
            map.put("txtCB_Month", txtCB_Month);
            map.put("txtfrom", d);
            map.put("txtto", d1);
            map.put("CR_ENTRIES", CR_ENTRIES);
            map.put("DR_ENTRIES", DR_ENTRIES);
            map.put("monthInWords", monthInWords);
            map.put("officename", offname);

            JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, map, connection);


            if (rtype.equalsIgnoreCase("HTML")) {
                response.setContentType("text/html");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"ViewReceipt_Count.html\"");
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
                                   "attachment;filename=\"ViewReceipt_Count.pdf\"");
                OutputStream out = response.getOutputStream();
                out.write(buf, 0, buf.length);
                out.close();
            } else if (rtype.equalsIgnoreCase("EXCEL")) {

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"ViewReceipt_Count.xls\"");
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
                                   "attachment;filename=\"ViewReceipt_Count.txt\"");

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
