package Servlets.FAS.FAS1.Queries.servlets;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import java.text.SimpleDateFormat;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;


public class FundTransFfrom_HO extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }


    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        response.setContentType(CONTENT_TYPE);
        Connection connection = null;
        Statement statement = null;
        java.util.Date d = null;
        java.util.Date d1 = null;
        String param = "";
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
            try {

            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
            }


        } catch (Exception e) {
            System.out.println("Exception in openeing connection:" + e);
        }

        response.setContentType(CONTENT_TYPE);
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
        String userid = (String)session.getAttribute("UserId");
        System.out.println("session id is:" + userid);
        String command = request.getParameter("Command");
        String accunit = request.getParameter("cmbAcc_UnitCode");
        String offcode = request.getParameter("cmbOffice_code");
        //  String rectype=request.getParameter("cmbStatus");
        String month = request.getParameter("txtCB_Month");
        String year = request.getParameter("txtCB_Year");
        String fromdate = request.getParameter("txtFrom_date");
        String todate = request.getParameter("txtTo_date");
        String bankid = "";
        int cmbBankId = 0;
        try {
            bankid = request.getParameter("cmbBankId");
            cmbBankId = Integer.parseInt(bankid);
            System.out.println(cmbBankId);
        } catch (Exception e) {
            System.out.println("cmbBankId :" + e);
        }
        /*   if(rectype.equals('U'))
            param="Unspent";
        else
            param="Collection";
        */
        int AccountUnitCode = Integer.parseInt(accunit);
        int OfficeCode = Integer.parseInt(offcode);
        int CashBookYear = Integer.parseInt(year);
        int CashBookMonth = Integer.parseInt(month);
        String monthInWords = "";
        if (CashBookMonth == 1)
            monthInWords = "January";
        else if (CashBookMonth == 2)
            monthInWords = "February";
        else if (CashBookMonth == 3)
            monthInWords = "March";
        else if (CashBookMonth == 4)
            monthInWords = "April";
        else if (CashBookMonth == 5)
            monthInWords = "May";
        else if (CashBookMonth == 6)
            monthInWords = "June";
        else if (CashBookMonth == 7)
            monthInWords = "July";
        else if (CashBookMonth == 8)
            monthInWords = "August";
        else if (CashBookMonth == 9)
            monthInWords = "September";
        else if (CashBookMonth == 10)
            monthInWords = "October";
        else if (CashBookMonth == 11)
            monthInWords = "November";
        else if (CashBookMonth == 12)
            monthInWords = "December";
        System.out.println("CashBookMonth..." + CashBookMonth);
        System.out.println("CashBookYear..." + CashBookYear);
        System.out.println("AccountUnitCode" + AccountUnitCode);
        System.out.println("OfficeCode" + OfficeCode);
        System.out.println(monthInWords);


        File reportFile = null;
        try {
            if (command.equals("searchByMonth")) {
                reportFile =
                        new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/Queries/jasper/FundsTransReport_C.jasper"));
            } else if (command.equals("searchByDate")) {
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
                        new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/Queries/jasper/FundsTransReport_HO.jasper"));
            }
            if (!reportFile.exists())
                throw new JRRuntimeException("File J not found. The report design must be compiled first.");
            System.out.println("from ...");
            JasperReport jasperReport =
                (JasperReport)JRLoader.loadObject(reportFile.getPath());
            Map map = null;
            map = new HashMap();
            map.put("cashbookyear", CashBookYear);
            map.put("cashbookmonth", CashBookMonth);
            map.put("accountingunitid", AccountUnitCode);
            map.put("accountofficeid", OfficeCode);
            map.put("monthvalue", monthInWords);
            map.put("fromdate", d);
            map.put("todate", d1);
            map.put("bankid", cmbBankId);
            // map.put("param",param);

            System.out.println("from .............");

            JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, map, connection);

            System.out.println("upto");
            String rtype = "PDF"; // request.getParameter("cmbReportType");
            System.out.println(rtype);

            if (rtype.equalsIgnoreCase("PDF")) {
                System.out.println(rtype + "...inside PDF");
                byte buf[] =
                    JasperExportManager.exportReportToPdf(jasperPrint);
                response.setContentType("application/pdf");
                response.setContentLength(buf.length);
                //response.setHeader ("Content-Disposition", "attachment;filename=\"Challan.pdf\"");
                OutputStream out = response.getOutputStream();
                out.write(buf, 0, buf.length);
                out.close();
            }
        } catch (Exception ex) {
            String connectMsg =
                "Could not create the report " + ex.getMessage(); //+ " uu " +  ex.getLocalizedMessage();
            System.out.println(connectMsg);
            //sendMessage(response,"The Challan Report Creation failed","ok");
        }
        //////////////////---------------------------- End -----------------
        System.out.println("here after PDF");
        //sendMessage(response,"The Trial Balance done successfully","ok");
        System.out.println("after send message");


    }

}


