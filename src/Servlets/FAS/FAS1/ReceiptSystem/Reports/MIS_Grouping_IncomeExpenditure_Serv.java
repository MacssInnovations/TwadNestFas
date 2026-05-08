package Servlets.FAS.FAS1.ReceiptSystem.Reports;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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

public class MIS_Grouping_IncomeExpenditure_Serv extends HttpServlet {
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
                statement = connection.createStatement();
                connection.clearWarnings();
            } catch (SQLException e) {
                System.out.println("Exception in creating statement:" + e);
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
        int CashBook_Year =
            Integer.parseInt(request.getParameter("txtCB_Year"));
        int CashBook_Month =
            Integer.parseInt(request.getParameter("txtCB_Month"));
        System.out.println("CashBook_Year is:" + CashBook_Year);
        System.out.println("CashBook Month is:" + CashBook_Month);

        System.out.println("month..." + CashBook_Month);
        String upto_current_date_from = "", upto_previous_month_from =
            "", upto_previous_year_from = "";
        String upto_current_date_to = "", upto_previous_month_to =
            "", upto_previous_year_to = "";
        upto_current_date_from = "4-";
        upto_previous_month_from = "4-";
        upto_previous_year_from = "4-";
        if (CashBook_Month > 3 && CashBook_Month <= 12) {
            upto_current_date_from += CashBook_Year;
            upto_previous_year_from += CashBook_Year - 1;
        } else {
            upto_current_date_from += CashBook_Year - 1;
            upto_previous_year_from += CashBook_Year - 2;
        }
        if ((CashBook_Month - 1) > 3 && CashBook_Month <= 12)
            upto_previous_month_from += CashBook_Year;
        else
            upto_previous_month_from += CashBook_Year - 1;
        upto_current_date_to = CashBook_Month + "-" + CashBook_Year;
        upto_previous_year_to = CashBook_Month + "-" + (CashBook_Year - 1);
        if ((CashBook_Month - 1) >= 1)
            upto_previous_month_to +=
                    (CashBook_Month - 1) + "-" + CashBook_Year;
        else
            upto_previous_month_to += "12-" + (CashBook_Year - 1);
        System.out.println("upto_current_date_from=" + upto_current_date_from +
                           "    upto_current_date_to=" + upto_current_date_to);
        System.out.println("upto_previous_month_from=" +
                           upto_previous_month_from +
                           "    upto_previous_month_to=" +
                           upto_previous_month_to);
        System.out.println("upto_previous_year_from=" +
                           upto_previous_year_from +
                           "    upto_previous_year_to=" +
                           upto_previous_year_to);
        String major_category = "'I','E'";
        System.out.println("major_category:" + major_category);
        Map map = null;
        map = new HashMap();
        File reportFile = null;
        try {
            System.out.println("inside try...");
            reportFile =
                    new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/MIS_Income_Expenditure.jasper"));

            if (!reportFile.exists())
                throw new JRRuntimeException("File J not found. The report design must be compiled first.");
            System.out.println("reportFile .." + reportFile);
            System.out.println("------------");
            JasperReport jasperReport =
                (JasperReport)JRLoader.loadObject(reportFile.getPath());
            System.out.println("----------------current date starting------------------");
            System.out.println("upto_current_date_from=" +
                               upto_current_date_from +
                               "    upto_current_date_to=" +
                               upto_current_date_to);
            map.put("previous_month_from", upto_previous_month_from);
            System.out.println("upto_current_date_from=" +
                               upto_current_date_from +
                               "    upto_current_date_to=" +
                               upto_current_date_to);
            map.put("previous_month_to", upto_previous_month_to);
            System.out.println("----------------current date end------------------");
            System.out.println("----------------previous month starting------------------");
            System.out.println("upto_previous_month_from=" +
                               upto_previous_month_from +
                               "    upto_previous_month_to=" +
                               upto_previous_month_to);
            map.put("current_date_from", upto_current_date_from);
            System.out.println("upto_previous_month_from=" +
                               upto_previous_month_from +
                               "    upto_previous_month_to=" +
                               upto_previous_month_to);
            map.put("current_date_to", upto_current_date_to);
            System.out.println("----------------previous month ending------------------");
            System.out.println("----------------previous year starting------------------");
            System.out.println("upto_previous_year_from=" +
                               upto_previous_year_from +
                               "    upto_previous_year_to=" +
                               upto_previous_year_to);
            map.put("previous_year_from", upto_previous_year_from);
            System.out.println("upto_previous_year_from=" +
                               upto_previous_year_from +
                               "    upto_previous_year_to=" +
                               upto_previous_year_to);
            map.put("previous_year_to", upto_previous_year_to);
            System.out.println("----------------previous month ending------------------");
            System.out.println("major_category:" + major_category);
            map.put("major_head_code", major_category);


            JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, map, connection);

            System.out.println("upto");
            String rtype = "PDF";
            System.out.println(rtype);

            if (rtype.equalsIgnoreCase("PDF")) {
                System.out.println(rtype + "...inside PDF");
                byte buf[] =
                    JasperExportManager.exportReportToPdf(jasperPrint);
                response.setContentType("application/pdf");
                response.setContentLength(buf.length);
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"Income and Expenditure.pdf\"");
                OutputStream out = response.getOutputStream();
                out.write(buf, 0, buf.length);
                out.close();
            }


        } catch (Exception ex) {
            String connectMsg =
                "Could not create the report " + ex.getMessage(); //+ " uu " +  ex.getLocalizedMessage();
            System.out.println(connectMsg);
        }
        System.out.println("here after PDF");
        System.out.println("after send message");


    }
}
