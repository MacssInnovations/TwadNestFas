package Servlets.FAS.FAS1.ReceiptSystem.Reports;

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

public class PrintVoucherServ extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";
    Connection connection = null;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
     //********************* joan Modification  ******************

       // doGet(request, response);

        try {

            System.out.println("Show");
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
        java.util.Date d = null;
        java.util.Date d1 = null;
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
            String cmd = request.getParameter("selection");
            System.out.println("command" + cmd);

            String mon = request.getParameter("txtCB_Monthwise");
            int intmon = Integer.parseInt(mon);
            System.out.println("Int Month" + intmon);
            String yea = request.getParameter("txtCB_Yearwise");
            int intyea = Integer.parseInt(yea);
            System.out.println("Int Month" + intyea);


            System.out.println("calling servlet");
            String month = request.getParameter("txtCB_Month");
            int cbmonth = Integer.parseInt(month);
            System.out.println(cbmonth);
            String year = request.getParameter("txtCB_Year");
            int cbyear = Integer.parseInt(year);
            System.out.println(cbyear);
            String rtype = request.getParameter("txtoption");
           // String cmbAcc_UnitCode = request.getParameter("cmbAcc_UnitCode");
           // String cmbOffice_code = request.getParameter("cmbOffice_code");
            String cmbdoctype = request.getParameter("cmbdoctype");
            String cbtype = request.getParameter("cbtype");
            System.out.println("cmbdoctype is:" + cmbdoctype);
            System.out.println("cbtype is:" + cbtype);
            String cmbdoctype_desc="";
            if(cmbdoctype.equalsIgnoreCase("REC"))cmbdoctype_desc="Receipt";
            else if(cmbdoctype.equalsIgnoreCase("PAY"))cmbdoctype_desc="Payment";
            else if(cmbdoctype.equalsIgnoreCase("JOU"))cmbdoctype_desc="Journal";
            else if(cmbdoctype.equalsIgnoreCase("FR"))cmbdoctype_desc="Fund Receipt";
            else if(cmbdoctype.equalsIgnoreCase("FT"))cmbdoctype_desc="Fund Transfer";
          //  System.out.println("accounting unit id is:" + cmbAcc_UnitCode);
          //  System.out.println("office code is:" + cmbOffice_code);
           // int accountingunit = Integer.parseInt(cmbAcc_UnitCode);
            //int accountingoffice = Integer.parseInt(cmbOffice_code);

            String from = request.getParameter("fromid");
            String to = request.getParameter("toid");
            int fromid = 0, toid = 0;

            if (cmd.equals("voucherwise")) {
                fromid = Integer.parseInt(from);
                System.out.println(fromid);
                toid = Integer.parseInt(to);
                System.out.println(toid);
            } else {
                fromid = 0;
                toid = 0;
            }


            if (cmbdoctype.equalsIgnoreCase("REC")) {
                System.out.println("REC");
                if (cmd.equals("voucherwise")) {
                    System.out.println("CR");
                    if (cbtype.equalsIgnoreCase("CR"))
                        reportFile =
                                new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/ListOfReceipts_Cash_All.jasper"));
                    else
                        reportFile =
                                new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/ListOfReceipts_Bank_All.jasper"));
                } else if (cmd.equals("monthwise")) {
                    if (cbtype.equalsIgnoreCase("CR"))
                        reportFile =
                                new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/ListOfReceipts_Cash_month.jasper"));
                    else
                        reportFile =
                                new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/ListOfReceipts_Bank_month.jasper"));
                } else if (cmd.equals("datewise")) {

                    String fromdate = request.getParameter("txtfromdate");
                    String todate = request.getParameter("txttodate");
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
                    //java.util.Date d1;
                    d1 = dateFormat1.parse(todate.trim());
                    dateFormat1.applyPattern("yyyy-MM-dd");
                    dateString1 = dateFormat1.format(d1);
                    dateto = java.sql.Date.valueOf(dateString1);

                    System.out.println("fromdate" + dateOfAttachment);
                    System.out.println("todate" + dateto);
                    if (cbtype.equalsIgnoreCase("CR"))
                        reportFile =
                                new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/ListOfReceipts_Cash_date_All.jasper"));
                    else
                        reportFile =
                                new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/ListOfReceipts_Bank_date_All.jasper"));
                }


            } else if (cmbdoctype.equalsIgnoreCase("PAY"))

            {
                if (cbtype.equalsIgnoreCase("BPP")) {
                    System.out.println("check");
                    if (cmd.equals("voucherwise")) {
                        reportFile =
                                new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/PaymentSystem/jasper/Bank_Payment_PendingBills_Rep_PB_All.jasper"));
                    } else if (cmd.equals("monthwise")) {
                        System.out.println("monBP");
                        reportFile =
                                new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/PaymentSystem/jasper/Bank_Payment_PendingBills_Rep_PB_month_All.jasper"));
                    } else if (cmd.equals("datewise")) {

                        String fromdate = request.getParameter("txtfromdate");
                        String todate = request.getParameter("txttodate");
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
                        //java.util.Date d1;
                        d1 = dateFormat1.parse(todate.trim());
                        dateFormat1.applyPattern("yyyy-MM-dd");
                        dateString1 = dateFormat1.format(d1);
                        dateto = java.sql.Date.valueOf(dateString1);

                        System.out.println("fromdate" + dateOfAttachment);
                        System.out.println("todate" + dateto);
                        reportFile =
                                new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/PaymentSystem/jasper/Bank_Payment_PendingBills_Rep_PB_date_All.jasper"));
                    }
                }
                if (cbtype.equalsIgnoreCase("BPF") ||
                    (cbtype.equalsIgnoreCase("NP"))) {
                    if (cmd.equals("voucherwise")) {
                        reportFile =
                                new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/PaymentSystem/jasper/Bank_Payment_Report_BPF_All.jasper"));
                    } else if (cmd.equals("monthwise")) {
                        reportFile =
                                new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/PaymentSystem/jasper/Bank_Payment_Report_BPF_month_All.jasper"));
                    } else if (cmd.equals("datewise")) {

                        String fromdate = request.getParameter("txtfromdate");
                        String todate = request.getParameter("txttodate");
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
                        //java.util.Date d1;
                        d1 = dateFormat1.parse(todate.trim());
                        dateFormat1.applyPattern("yyyy-MM-dd");
                        dateString1 = dateFormat1.format(d1);
                        dateto = java.sql.Date.valueOf(dateString1);

                        System.out.println("fromdate" + dateOfAttachment);
                        System.out.println("todate" + dateto);
                        reportFile =
                                new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/PaymentSystem/jasper/Bank_Payment_Report_BPF_date_All.jasper"));
                    }
                }

            }
            //System.out.println("cmbdocType is:"+cmbdoctype);
            else if (cmbdoctype.equalsIgnoreCase("JOU")) {
                if (cbtype.equalsIgnoreCase("LJV") ||
                    (cbtype.equalsIgnoreCase("GJV")) ||
                    (cbtype.equalsIgnoreCase("SJV"))) {
                    if (cmd.equals("voucherwise")) {
                        reportFile =
                                new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/JournalSystem/jasper/Journal_All.jasper"));
                    } else if (cmd.equals("monthwise")) {
                        reportFile =
                                new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/JournalSystem/jasper/Journal_month_All.jasper"));
                    } else if (cmd.equals("datewise")) {
                        String fromdate = request.getParameter("txtfromdate");
                        String todate = request.getParameter("txttodate");
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
                        //java.util.Date d1;
                        d1 = dateFormat1.parse(todate.trim());
                        dateFormat1.applyPattern("yyyy-MM-dd");
                        dateString1 = dateFormat1.format(d1);
                        dateto = java.sql.Date.valueOf(dateString1);

                        System.out.println("fromdate" + dateOfAttachment);
                        System.out.println("todate" + dateto);
                        reportFile =
                                new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/JournalSystem/jasper/Journal_date_All.jasper"));
                    }
                }


            }

            
            

            if (!reportFile.exists())
                throw new JRRuntimeException("File J not found. The report design must be compiled first.");

            JasperReport jasperReport =
                (JasperReport)JRLoader.loadObject(reportFile.getPath());

            //opt=request.getParameter("txtoption");
            //System.out.println("opt::" + opt);
            //   JasperReport jasperReport =     JasperCompileManager.compileReport(jasperDesign);
           System.out.println("Report File >> "+reportFile);
            Map map = new HashMap();


            //map.put("acc_unit_id", accountingunit);
          //  map.put("off_id", accountingoffice);
            map.put("yr", cbyear);
            map.put("mon", cbmonth);
            map.put("recType", cbtype);
            System.out.println("fromid ::: " + fromid);
            map.put("recNofrom", fromid);
            System.out.println("toid ::: " + toid);
            map.put("recNoto", toid);
            map.put("intmonth", intmon);
            map.put("intyear", intyea);
            map.put("fromdate", d);
            map.put("todate", d1);
            map.put("imagepath",getServletContext().getRealPath("/images/twademblem.gif"));
            // map.put("cbtype",cmbdoctype);
            JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, map, connection);


            if (rtype.equalsIgnoreCase("HTML")) {
                response.setContentType("text/html");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"List of "+cmbdoctype_desc+".html\"");
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
                                   "attachment;filename=\"List of "+cmbdoctype_desc+".pdf\"");
                OutputStream out = response.getOutputStream();
                out.write(buf, 0, buf.length);
                out.close();
            } else if (rtype.equalsIgnoreCase("EXCEL")) {

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"List Of "+cmbdoctype_desc+".xls\"");
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
                                   "attachment;filename=\"List of "+cmbdoctype_desc+".txt\"");

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

    public void doPost(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        try {

            System.out.println("Show");
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
        String opt = "",cb_qry="";
        java.util.Date d = null;
        java.util.Date d1 = null;
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
String reportName="";
        try {
            String cmd = request.getParameter("selection");
            System.out.println("command" + cmd);

            String mon = request.getParameter("txtCB_Monthwise");
            int intmon = Integer.parseInt(mon);
            System.out.println("Int Month" + intmon);
            String yea = request.getParameter("txtCB_Yearwise");
            int intyea = Integer.parseInt(yea);
            System.out.println("Int Month" + intyea);


            System.out.println("calling servlet");
            String month = request.getParameter("txtCB_Month");
            String hid = request.getParameter("hid");
            int cbmonth = Integer.parseInt(month);
            System.out.println(cbmonth);
            String year = request.getParameter("txtCB_Year");
            int cbyear = Integer.parseInt(year);
            System.out.println(cbyear);
            String rtype = request.getParameter("txtoption");
            String cmbAcc_UnitCode = request.getParameter("cmbAcc_UnitCode");
            String cmbOffice_code = request.getParameter("cmbOffice_code");
            String cmbdoctype = request.getParameter("cmbdoctype");
            String cbtype = request.getParameter("cbtype");
            System.out.println("cmbdoctype is:" + cmbdoctype);
            System.out.println("cbtype is:" + cbtype);
            System.out.println("accounting unit id is:" + cmbAcc_UnitCode);
            System.out.println("office code is:" + cmbOffice_code);
            int accountingunit = Integer.parseInt(cmbAcc_UnitCode);
            int accountingoffice = Integer.parseInt(cmbOffice_code);

            String from = request.getParameter("fromid");
            String to = request.getParameter("toid");
            int fromid = 0, toid = 0;

            if (cmd.equals("voucherwise")) {
                fromid = Integer.parseInt(from);
                System.out.println(fromid);
                toid = Integer.parseInt(to);
                System.out.println(toid);
            } else {
                fromid = 0;
                toid = 0;
            }


            if (cmbdoctype.equalsIgnoreCase("REC")) {
            	if(hid.equalsIgnoreCase("NO")){
                System.out.println("REC");
                if(cbtype.equalsIgnoreCase("ALL"))
            	{
            		cb_qry="	AND recMas.CREATED_BY_MODULE in ('CR','BR')";
            	}else{
            		cb_qry="	AND recMas.CREATED_BY_MODULE='"+cbtype+"'";
            	}
                if (cmd.equals("voucherwise")) {
                	
                	
                    System.out.println("CR");
                    if (cbtype.equalsIgnoreCase("CR"))
                        reportFile =
                                new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/ListOfReceipts_Cash.jasper"));
                    else
                        reportFile =
                                new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/ListOfReceipts_Bank.jasper"));
                } else if (cmd.equals("monthwise")) {
                    if (cbtype.equalsIgnoreCase("CR"))
                        reportFile =
                                new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/ListOfReceipts_Cash_month.jasper"));
                    else
                        reportFile =
                                new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/ListOfReceipts_Bank_month.jasper"));
                } else if (cmd.equals("datewise")) {

                    String fromdate = request.getParameter("txtfromdate");
                    String todate = request.getParameter("txttodate");
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
                    //java.util.Date d1;
                    d1 = dateFormat1.parse(todate.trim());
                    dateFormat1.applyPattern("yyyy-MM-dd");
                    dateString1 = dateFormat1.format(d1);
                    dateto = java.sql.Date.valueOf(dateString1);

                    System.out.println("fromdate" + dateOfAttachment);
                    System.out.println("todate" + dateto);
                    if (cbtype.equalsIgnoreCase("CR"))
                        reportFile =
                                new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/ListOfReceipts_Cash_date.jasper"));
                    else
                        reportFile =
                                new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/ListOfReceipts_Bank_date.jasper"));
                }
            	}else{

                    System.out.println("REC Sl TYPE YES");
                    if(cbtype.equalsIgnoreCase("ALL"))
                	{
                		cb_qry="	AND recMas.CREATED_BY_MODULE in ('CR','BR')";
                	}else{
                		cb_qry="	AND recMas.CREATED_BY_MODULE='"+cbtype+"'";
                	}
                    if (cmd.equals("voucherwise")) {
                    	
                    	
                        System.out.println("CR");
                        if (cbtype.equalsIgnoreCase("CR"))
                            reportFile =
                                    new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/ListOfReceipts_CashSl.jasper"));
                        else
                            reportFile =
                                    new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/ListOfReceipts_BankSl.jasper"));
                    } else if (cmd.equals("monthwise")) {
                        if (cbtype.equalsIgnoreCase("CR"))
                            reportFile =
                                    new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/ListOfReceipts_Cash_monthSl.jasper"));
                        else
                            reportFile =
                                    new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/ListOfReceipts_Bank_monthSl.jasper"));
                    } else if (cmd.equals("datewise")) {

                        String fromdate = request.getParameter("txtfromdate");
                        String todate = request.getParameter("txttodate");
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
                        //java.util.Date d1;
                        d1 = dateFormat1.parse(todate.trim());
                        dateFormat1.applyPattern("yyyy-MM-dd");
                        dateString1 = dateFormat1.format(d1);
                        dateto = java.sql.Date.valueOf(dateString1);

                        System.out.println("fromdate" + dateOfAttachment);
                        System.out.println("todate" + dateto);
                        if (cbtype.equalsIgnoreCase("CR"))
                            reportFile =
                                    new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/ListOfReceipts_Cash_dateSl.jasper"));
                        else
                            reportFile =
                                    new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/ListOfReceipts_Bank_dateSl.jasper"));
                    }


                
            	}
            	reportName="ListOfReceipt";
            } else if (cmbdoctype.equalsIgnoreCase("PAY")){
            	if(hid.equalsIgnoreCase("NO"))
            {
                if (cbtype.equalsIgnoreCase("BPP")) {
                	cb_qry=cbtype;
                    System.out.println("check");
                    if (cmd.equals("voucherwise")) {
                        reportFile =
                                new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/PaymentSystem/jasper/Bank_Payment_PendingBills_Rep_PB.jasper"));
                    } else if (cmd.equals("monthwise")) {
                        System.out.println("monBP");
                        reportFile =
                                new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/PaymentSystem/jasper/Bank_Payment_PendingBills_Rep_PB_month.jasper"));
                    } else if (cmd.equals("datewise")) {

                        String fromdate = request.getParameter("txtfromdate");
                        String todate = request.getParameter("txttodate");
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
                        //java.util.Date d1;
                        d1 = dateFormat1.parse(todate.trim());
                        dateFormat1.applyPattern("yyyy-MM-dd");
                        dateString1 = dateFormat1.format(d1);
                        dateto = java.sql.Date.valueOf(dateString1);

                        System.out.println("fromdate" + dateOfAttachment);
                        System.out.println("todate" + dateto);
                        reportFile =
                                new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/PaymentSystem/jasper/Bank_Payment_PendingBills_Rep_PB_date.jasper"));
                    }
                }
                if (cbtype.equalsIgnoreCase("BPF") ||
                    (cbtype.equalsIgnoreCase("NP"))||(cbtype.equalsIgnoreCase("ALL")) ) 
                
                {
                	if(cbtype.equalsIgnoreCase("ALL"))
                	{
                		cb_qry="	and payMas.CREATED_BY_MODULE in ('BPF','NP','BPP')";
                	}else{
                		cb_qry="	and payMas.CREATED_BY_MODULE='"+cbtype+"'";
                	}
                    if (cmd.equals("voucherwise")) {
                        reportFile =
                                new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/PaymentSystem/jasper/Bank_Payment_Report_BPF.jasper"));
                    } else if (cmd.equals("monthwise")) {
                        reportFile =
                                new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/PaymentSystem/jasper/Bank_Payment_Report_BPF_month.jasper"));
                    } else if (cmd.equals("datewise")) {

                        String fromdate = request.getParameter("txtfromdate");
                        String todate = request.getParameter("txttodate");
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
                        //java.util.Date d1;
                        d1 = dateFormat1.parse(todate.trim());
                        dateFormat1.applyPattern("yyyy-MM-dd");
                        dateString1 = dateFormat1.format(d1);
                        dateto = java.sql.Date.valueOf(dateString1);

                        System.out.println("fromdate" + dateOfAttachment);
                        System.out.println("todate" + dateto);
                        reportFile =
                                new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/PaymentSystem/jasper/Bank_Payment_Report_BPF_date.jasper"));
                    }
                }
            }else{
                System.out.println("check ELSE PART SL CODE ... ");
                if (cbtype.equalsIgnoreCase("BPP")) {
                	cb_qry=cbtype;
                    System.out.println("check");
                    if (cmd.equals("voucherwise")) {
                        reportFile =
                                new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/PaymentSystem/jasper/Bank_Payment_PendingBills_Rep_PBSl.jasper"));
                    } else if (cmd.equals("monthwise")) {
                        System.out.println("monBP");
                        reportFile =
                                new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/PaymentSystem/jasper/Bank_Payment_PendingBills_Rep_PB_monthSl.jasper"));
                    } else if (cmd.equals("datewise")) {

                        String fromdate = request.getParameter("txtfromdate");
                        String todate = request.getParameter("txttodate");
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
                        //java.util.Date d1;
                        d1 = dateFormat1.parse(todate.trim());
                        dateFormat1.applyPattern("yyyy-MM-dd");
                        dateString1 = dateFormat1.format(d1);
                        dateto = java.sql.Date.valueOf(dateString1);

                        System.out.println("fromdate" + dateOfAttachment);
                        System.out.println("todate" + dateto);
                        reportFile =
                                new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/PaymentSystem/jasper/Bank_Payment_PendingBills_Rep_PB_dateSl.jasper"));
                    }
                }
                if (cbtype.equalsIgnoreCase("BPF") ||
                    (cbtype.equalsIgnoreCase("NP"))||(cbtype.equalsIgnoreCase("ALL")) ) 
                
                {
                	if(cbtype.equalsIgnoreCase("ALL"))
                	{
                		cb_qry="	and payMas.CREATED_BY_MODULE in ('BPF','NP','BPP')";
                	}else{
                		cb_qry="	and payMas.CREATED_BY_MODULE='"+cbtype+"'";
                	}
                    if (cmd.equals("voucherwise")) {
                        reportFile =
                                new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/PaymentSystem/jasper/Bank_Payment_Report_BPFSl.jasper"));
                    } else if (cmd.equals("monthwise")) {
                        reportFile =
                                new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/PaymentSystem/jasper/Bank_Payment_Report_BPF_monthSl.jasper"));
                    } else if (cmd.equals("datewise")) {

                        String fromdate = request.getParameter("txtfromdate");
                        String todate = request.getParameter("txttodate");
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
                        //java.util.Date d1;
                        d1 = dateFormat1.parse(todate.trim());
                        dateFormat1.applyPattern("yyyy-MM-dd");
                        dateString1 = dateFormat1.format(d1);
                        dateto = java.sql.Date.valueOf(dateString1);

                        System.out.println("fromdate" + dateOfAttachment);
                        System.out.println("todate" + dateto);
                        reportFile =
                                new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/PaymentSystem/jasper/Bank_Payment_Report_BPF_dateSl.jasper"));
                    }
                }
            }	reportName="ListOfPayment";
            }
            //System.out.println("cmbdocType is:"+cmbdoctype);
            else if (cmbdoctype.equalsIgnoreCase("JOU")) {
            	if(hid.equalsIgnoreCase("NO")){
                if (cbtype.equalsIgnoreCase("LJV") ||
                    (cbtype.equalsIgnoreCase("GJV")) ||
                    (cbtype.equalsIgnoreCase("SJV")) ||(cbtype.equalsIgnoreCase("ALL")))  {
                	
                	if(cbtype.equalsIgnoreCase("ALL"))
                	{
                		cb_qry="and JourMas.CREATED_BY_MODULE in ('LJV','GJV','SJV')";
                	}else{
                		cb_qry="and JourMas.CREATED_BY_MODULE='"+cbtype+"'";
                	}
                    if (cmd.equals("voucherwise")) {
                        reportFile =
                                new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/JournalSystem/jasper/Journal.jasper"));
                    } else if (cmd.equals("monthwise")) {
                        reportFile =
                                new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/JournalSystem/jasper/Journal_month.jasper"));
                    } else if (cmd.equals("datewise")) {
                        String fromdate = request.getParameter("txtfromdate");
                        String todate = request.getParameter("txttodate");
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
                        //java.util.Date d1;
                        d1 = dateFormat1.parse(todate.trim());
                        dateFormat1.applyPattern("yyyy-MM-dd");
                        dateString1 = dateFormat1.format(d1);
                        dateto = java.sql.Date.valueOf(dateString1);

                        System.out.println("fromdate" + dateOfAttachment);
                        System.out.println("todate" + dateto);
                        reportFile =
                                new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/JournalSystem/jasper/Journal_date.jasper"));
                    }
                }
            }else{
            	  System.out.println("Sl Else PArt Journal ... ");
                if (cbtype.equalsIgnoreCase("LJV") ||
                    (cbtype.equalsIgnoreCase("GJV")) ||
                    (cbtype.equalsIgnoreCase("SJV")) ||(cbtype.equalsIgnoreCase("ALL")))  {
                	
                	if(cbtype.equalsIgnoreCase("ALL"))
                	{
                		cb_qry="and JourMas.CREATED_BY_MODULE in ('LJV','GJV','SJV')";
                	}else{
                		cb_qry="and JourMas.CREATED_BY_MODULE='"+cbtype+"'";
                	}
                    if (cmd.equals("voucherwise")) {
                        reportFile =
                                new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/JournalSystem/jasper/JournalSl.jasper"));
                    } else if (cmd.equals("monthwise")) {
                        reportFile =
                                new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/JournalSystem/jasper/Journal_monthSl.jasper"));
                    } else if (cmd.equals("datewise")) {
                        String fromdate = request.getParameter("txtfromdate");
                        String todate = request.getParameter("txttodate");
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
                        //java.util.Date d1;
                        d1 = dateFormat1.parse(todate.trim());
                        dateFormat1.applyPattern("yyyy-MM-dd");
                        dateString1 = dateFormat1.format(d1);
                        dateto = java.sql.Date.valueOf(dateString1);

                        System.out.println("fromdate" + dateOfAttachment);
                        System.out.println("todate" + dateto);
                        reportFile =
                                new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/JournalSystem/jasper/Journal_dateSl.jasper"));
                    }
                }
            
            }
            	reportName="ListOfJournal";
            }
            if (cmbdoctype.equalsIgnoreCase("FR")) {
            	if(hid.equalsIgnoreCase("NO")){
                System.out.println("REC");
//                if(cbtype.equalsIgnoreCase("ALL"))
//            	{
//            		cb_qry="	AND recMas.CREATED_BY_MODULE in ('CR','BR')";
//            	}else{
//            		cb_qry="	AND recMas.CREATED_BY_MODULE='"+cbtype+"'";
//            	}
                if (cmd.equals("voucherwise")) {
                	
                	
                    System.out.println("FR Office");
                    if (cbtype.equalsIgnoreCase("Office"))
                        reportFile =
                                new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/ListOfFundReceipts_ByOffice.jasper"));
                    else
                        reportFile =
                                new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/ListOfFundReceipts_ByHO.jasper"));
                } else if (cmd.equals("monthwise")) {
                    if (cbtype.equalsIgnoreCase("Office"))
                        reportFile =
                                new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/ListOfFundReceipts_ByOffice_Month.jasper"));
                    else
                        reportFile =
                                new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/ListOfFundReceipts_ByHO_Month.jasper"));
                } else if (cmd.equals("datewise")) {

                    String fromdate = request.getParameter("txtfromdate");
                    String todate = request.getParameter("txttodate");
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
                    //java.util.Date d1;
                    d1 = dateFormat1.parse(todate.trim());
                    dateFormat1.applyPattern("yyyy-MM-dd");
                    dateString1 = dateFormat1.format(d1);
                    dateto = java.sql.Date.valueOf(dateString1);

                    System.out.println("fromdate" + dateOfAttachment);
                    System.out.println("todate" + dateto);
                    if (cbtype.equalsIgnoreCase("CR"))
                        reportFile =
                                new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/ListOfReceipts_Cash_date.jasper"));
                    else
                        reportFile =
                                new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/ListOfReceipts_Bank_date.jasper"));
                }
            	}else{

                    System.out.println("REC Sl TYPE YES");
                    if(cbtype.equalsIgnoreCase("ALL"))
                	{
                		cb_qry="	AND recMas.CREATED_BY_MODULE in ('CR','BR')";
                	}else{
                		cb_qry="	AND recMas.CREATED_BY_MODULE='"+cbtype+"'";
                	}
                    if (cmd.equals("voucherwise")) {
                    	
                    	
                        System.out.println("CR");
                        if (cbtype.equalsIgnoreCase("CR"))
                            reportFile =
                                    new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/ListOfReceipts_CashSl.jasper"));
                        else
                            reportFile =
                                    new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/ListOfReceipts_BankSl.jasper"));
                    } else if (cmd.equals("monthwise")) {
                        if (cbtype.equalsIgnoreCase("CR"))
                            reportFile =
                                    new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/ListOfReceipts_Cash_monthSl.jasper"));
                        else
                            reportFile =
                                    new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/ListOfReceipts_Bank_monthSl.jasper"));
                    } else if (cmd.equals("datewise")) {

                        String fromdate = request.getParameter("txtfromdate");
                        String todate = request.getParameter("txttodate");
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
                        //java.util.Date d1;
                        d1 = dateFormat1.parse(todate.trim());
                        dateFormat1.applyPattern("yyyy-MM-dd");
                        dateString1 = dateFormat1.format(d1);
                        dateto = java.sql.Date.valueOf(dateString1);

                        System.out.println("fromdate" + dateOfAttachment);
                        System.out.println("todate" + dateto);
                        if (cbtype.equalsIgnoreCase("CR"))
                            reportFile =
                                    new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/ListOfReceipts_Cash_dateSl.jasper"));
                        else
                            reportFile =
                                    new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/ListOfReceipts_Bank_dateSl.jasper"));
                    }


                
            	}
            	reportName="ListOfFundReceipt";
            }

            if (!reportFile.exists())
                throw new JRRuntimeException("File J not found. The report design must be compiled first.");

            JasperReport jasperReport =
                (JasperReport)JRLoader.loadObject(reportFile.getPath());

            //opt=request.getParameter("txtoption");
            //System.out.println("opt::" + opt);
            //   JasperReport jasperReport =     JasperCompileManager.compileReport(jasperDesign);
           System.out.println("Report File >> "+reportFile);
            Map map = new HashMap();


            map.put("acc_unit_id", accountingunit);
            map.put("off_id", accountingoffice);
            map.put("yr", cbyear);
            map.put("mon", cbmonth);
            map.put("recType", cb_qry);
         //   map.put("recType", cbtype);
            System.out.println("fromid ::: " + fromid);
            map.put("recNofrom", fromid);
            System.out.println("toid ::: " + toid);
            map.put("recNoto", toid);
            map.put("intmonth", intmon);
            map.put("intyear", intyea);
            map.put("fromdate", d);
            map.put("todate", d1);
            map.put("imagepath",getServletContext().getRealPath("/images/twademblem.gif"));
            // map.put("cbtype",cmbdoctype);
            System.out.println("cb_qry     "+map);
            JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, map, connection);


            if (rtype.equalsIgnoreCase("HTML")) {
                response.setContentType("text/html");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\""+reportName+".html\"");
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
                                   "attachment;filename=\"ListOfReceipts_Cash.pdf\"");
                OutputStream out = response.getOutputStream();
                out.write(buf, 0, buf.length);
                out.close();
            } else if (rtype.equalsIgnoreCase("EXCEL")) {

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"ListOfReceipt.xls\"");
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
