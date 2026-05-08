package Servlets.WQS.WQS1.WQTesting.Reports.servlets;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;

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
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

public class WQS_Monthly_ReportServ extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                return;
            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }
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
//            ConnectionString =
//                    strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
//                    ":" + strsid.trim();
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
            Class.forName(strDriver.trim());
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
            st = con.createStatement();
        } catch (Exception e) {
            System.out.println("Exception in opening connection :" + e);
            //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

        }

        int txtCB_Year = 0, txtCB_Month = 0;
        int month_from = 4, current_day_val = 0, previous_day_val =
            0, txtCB_prev_Month = 0;
        String from_date = "", to_date = "";
        String prev_month = "", prev_month_inwords = "";

        txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));
        txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
        System.out.println("year..." + txtCB_Year);
        System.out.println("Month..." + txtCB_Month);
        try {
            rs =
  st.executeQuery("SELECT extract(day from LAST_DAY(to_date('" + txtCB_Month +
                  "','MM'))) as lastday ");
            if (rs.next()) {
                current_day_val = rs.getInt("lastday");
            }
            rs.close();

            txtCB_prev_Month = txtCB_Month - 1;
            if (txtCB_prev_Month == 0)
                txtCB_prev_Month = 12;
            rs =
  st.executeQuery("SELECT extract(day from LAST_DAY(to_date('" + txtCB_prev_Month +
                  "','MM'))) as lastday ");
            if (rs.next()) {
                previous_day_val = rs.getInt("lastday");
            }


        } catch (Exception e) {
            current_day_val = 28;
            previous_day_val = 28;
            System.out.println("Err in day selection :::::::" +
                               e.getMessage());
        }

        System.out.println("Total days in selected  month:::" +
                           current_day_val);


        String fin_yr = "";
        if ((txtCB_Month - 1) > 3) {
            from_date = 1 + "/" + month_from + "/" + txtCB_Year;
            to_date =
                    previous_day_val + "/" + (txtCB_Month - 1) + "/" + txtCB_Year;
        } else if ((txtCB_Month - 1) <= 3 && (txtCB_Month - 1) >= 1) {
            from_date = 1 + "/" + month_from + "/" + (txtCB_Year - 1);
            to_date =
                    previous_day_val + "/" + (txtCB_Month - 1) + "/" + txtCB_Year;
        } else {
            from_date = 1 + "/" + month_from + "/" + (txtCB_Year - 1);
            to_date = previous_day_val + "/" + 12 + "/" + txtCB_Year;
        }


        File reportFile = null;
        try {
            System.out.println("calling servlet...");
            reportFile =
                    new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_Monthly_Report.jasper"));
            if (!reportFile.exists())
                throw new JRRuntimeException("File J not found. The report design must be compiled first.");
            System.out.println("reportFile: " + reportFile);
            JasperReport jasperReport =
                (JasperReport)JRLoader.loadObject(reportFile.getPath());
            Map map = null;
            map = new HashMap();

            String monthInWords = "";
            prev_month_inwords = "";
            if (txtCB_Month == 1) {
                monthInWords = "January";
                prev_month_inwords = "December";
            } else if (txtCB_Month == 2) {
                monthInWords = "February";
                prev_month_inwords = "January";
            } else if (txtCB_Month == 3) {
                monthInWords = "March";
                prev_month_inwords = "February";
            } else if (txtCB_Month == 4) {
                monthInWords = "April";
                prev_month_inwords = "March";
            } else if (txtCB_Month == 5) {
                monthInWords = "May";
                prev_month_inwords = "April";
            } else if (txtCB_Month == 6) {
                monthInWords = "June";
                prev_month_inwords = "May";
            } else if (txtCB_Month == 7) {
                monthInWords = "July";
                prev_month_inwords = "June";
            } else if (txtCB_Month == 8) {
                monthInWords = "August";
                prev_month_inwords = "July";
            } else if (txtCB_Month == 9) {
                monthInWords = "September";
                prev_month_inwords = "August";
            } else if (txtCB_Month == 10) {
                monthInWords = "October";
                prev_month_inwords = "September";
            } else if (txtCB_Month == 11) {
                monthInWords = "November";
                prev_month_inwords = "October";
            } else if (txtCB_Month == 12) {
                monthInWords = "December";
                prev_month_inwords = "November";
            }


            if (txtCB_Month > 3) {
                fin_yr = txtCB_Year + "-" + (txtCB_Year + 1);
                if ((txtCB_Month - 1) ==
                    3) // if txtCB_Month/current month is 4 then its preevious month is 3. 3 is the month of previous finanacial year
                    prev_month =
                            "April " + (txtCB_Year - 1) + " to " + prev_month_inwords +
                            " " + txtCB_Year;
                else
                    prev_month =
                            "April " + txtCB_Year + " to " + prev_month_inwords +
                            " " + (txtCB_Year);
            } else {
                fin_yr = (txtCB_Year - 1) + "-" + txtCB_Year;
                if ((txtCB_Month - 1) == 0)
                    prev_month =
                            "April " + (txtCB_Year - 1) + " to " + prev_month_inwords +
                            " " + (txtCB_Year - 1);
                else
                    prev_month =
                            "April " + (txtCB_Year - 1) + " to " + prev_month_inwords +
                            " " + txtCB_Year;
            }


            map.put("yr", txtCB_Year);
            System.out.println("yr:::" + txtCB_Year);
            map.put("mon", txtCB_Month);
            System.out.println("mon:::" + txtCB_Month);
            System.out.println("date_from:::" + from_date);
            map.put("date_from", from_date);
            System.out.println("to_date:::" + to_date);
            map.put("date_to", to_date);
            map.put("monthInWords", monthInWords + " " + txtCB_Year);
            map.put("fin_yr", fin_yr);
            System.out.println("fin_yr: " + fin_yr);
            map.put("prev_month", prev_month);
            System.out.println("prev_month: " + prev_month);

            // map.put("vouType",txtCreat_By_Module);
            JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, map, con);
            System.out.println("upto");

            byte buf[] = JasperExportManager.exportReportToPdf(jasperPrint);
            response.setContentType("application/pdf");
            response.setContentLength(buf.length);
            response.setHeader("Content-Disposition",
                               "attachment;filename=\"MonthlyReport.pdf\"");
            OutputStream out = response.getOutputStream();
            out.write(buf, 0, buf.length);
            out.close();
        } catch (Exception ex) {
            String connectMsg =
                "Could not create the report " + ex.getMessage() + " uu " +
                ex.getLocalizedMessage();
            System.out.println(connectMsg);
        }

    }
}
