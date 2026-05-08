package Servlets.FAS.FAS1.ReceiptSystem.Reports;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.sql.Timestamp;

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

public class MIS_Level3_AbstractReport_Serv extends HttpServlet {
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
        System.out.println("Welcome to leve2 grouping");
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            ResourceBundle rst =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";

            String strDriver = rst.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rst.getString("Config.DSN");
            String strhostname = rst.getString("Config.HOST_NAME");
            String strportno = rst.getString("Config.PORT_NUMBER");
            String strsid = rst.getString("Config.SID");
            String strdbusername = rst.getString("Config.USER_NAME");
            String strdbpassword = rst.getString("Config.PASSWORD");

            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection

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
            System.out.println("Exception in openeing connection:" + e);
        }
        response.setHeader("cache-control", "no-cache");
        String cmd = request.getParameter("command");
        String major_head = request.getParameter("major_head");
        String level2_id = request.getParameter("level2_id");
        String xml = "<response>";
        int cnt = 0;
        if (cmd.equalsIgnoreCase("loadMajorHead")) {
            xml = xml + "<command>" + cmd + "</command>";
            try {
                rs =
  st.executeQuery("select major_head_code,major_head_desc from com_mst_major_heads");
                while (rs.next()) {
                    cnt++;
                    xml = xml + "<count>";
                    xml =
 xml + "<major_head_code>" + rs.getString("major_head_code") +
   "</major_head_code>";
                    xml =
 xml + "<major_head_desc>" + rs.getString("major_head_desc") +
   "</major_head_desc>";
                    xml = xml + "</count>";
                }
                if (cnt > 0)
                    xml = xml + "<flag>Success</flag>";
                else
                    xml = xml + "<flag>Failure</flag>";
            } catch (Exception e) {
                System.out.println("Err in loadMajorHead:" + e.getMessage());
            }
        } else if (cmd.equalsIgnoreCase("loadLevel2")) {
            xml = xml + "<command>" + cmd + "</command>";
            try {
                rs =
  st.executeQuery("select grouping_level2_id,level2_group_desc from mis_grouping_level2 where major_head_code='" +
                  major_head + "' and process_flag not in('DELETED')");
                while (rs.next()) {
                    cnt++;
                    xml = xml + "<count>";
                    xml =
 xml + "<level2_head_code>" + rs.getString("grouping_level2_id") +
   "</level2_head_code>";
                    xml =
 xml + "<level2_head_desc>" + rs.getString("level2_group_desc") +
   "</level2_head_desc>";
                    xml = xml + "</count>";
                }
                if (cnt > 0)
                    xml = xml + "<flag>Success</flag>";
                else
                    xml = xml + "<flag>Failure</flag>";
            } catch (Exception e) {
                System.out.println("Err in loadMajorHead:" + e.getMessage());
            }
        } else if (cmd.equalsIgnoreCase("loadLevel3")) {
            xml = xml + "<command>" + cmd + "</command>";
            try {
                rs =
  st.executeQuery("select grouping_level3_id,level3_group_desc from mis_grouping_level3 where level2_id='" +
                  level2_id + "' and process_flag not in('DELETED')");
                while (rs.next()) {
                    cnt++;
                    xml = xml + "<count>";
                    xml =
 xml + "<level3_head_code>" + rs.getString("grouping_level3_id") +
   "</level3_head_code>";
                    xml =
 xml + "<level3_head_desc>" + rs.getString("level3_group_desc") +
   "</level3_head_desc>";
                    xml = xml + "</count>";
                }
                if (cnt > 0)
                    xml = xml + "<flag>Success</flag>";
                else
                    xml = xml + "<flag>Failure</flag>";
            } catch (Exception e) {
                System.out.println("Err in loadMajorHead:" + e.getMessage());
            }
        }
        xml = xml + "</response>";
        System.out.println("xml:" + xml);
        out.println(xml);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        response.setContentType(CONTENT_TYPE);
        System.out.println("welcome to post method");
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
        String major_head = request.getParameter("major_head");
        int level2 = Integer.parseInt(request.getParameter("level2"));
        int level3 = Integer.parseInt(request.getParameter("level3"));
        String period = request.getParameter("period");
        System.out.println("Period:" + period);
        String CashBook_Year = request.getParameter("year");
        String heading = request.getParameter("heading");
        System.out.println("CashBook_Year is:" + CashBook_Year);

        String current_from_date = "", current_to_date =
            "", previous_from_date = "", previous_to_date = "", clmn_heading1 =
            "", clmn_heading2 = "";
        if (period.equals("annual")) {
            String year[] = CashBook_Year.split("-");
            current_from_date = "4-" + year[0];
            current_to_date = "3-" + year[1];
            previous_from_date = "4-" + (Integer.parseInt(year[0]) - 1);
            previous_to_date = "3-" + (Integer.parseInt(year[1]) - 1);
            clmn_heading1 =
                    (Integer.parseInt(year[0]) - 1) + "-" + (Integer.parseInt(year[1]) -
                                                             1);
            clmn_heading2 = CashBook_Year;
        } else {
            int CashBook_Month =
                Integer.parseInt(request.getParameter("month"));
            System.out.println("CashBook Month is:" + CashBook_Month);
            int Previous_CashBook_Month = CashBook_Month - 1;
            String to_monthInWords = "";
            if (CashBook_Month == 1)
                to_monthInWords = "January";
            else if (CashBook_Month == 2)
                to_monthInWords = "February";
            else if (CashBook_Month == 3)
                to_monthInWords = "March";
            else if (CashBook_Month == 4)
                to_monthInWords = "April";
            else if (CashBook_Month == 5)
                to_monthInWords = "May";
            else if (CashBook_Month == 6)
                to_monthInWords = "June";
            else if (CashBook_Month == 7)
                to_monthInWords = "July";
            else if (CashBook_Month == 8)
                to_monthInWords = "August";
            else if (CashBook_Month == 9)
                to_monthInWords = "September";
            else if (CashBook_Month == 10)
                to_monthInWords = "October";
            else if (CashBook_Month == 11)
                to_monthInWords = "November";
            else if (CashBook_Month == 12)
                to_monthInWords = "December";

            String previousMonthInWords = "";
            if (Previous_CashBook_Month == 1)
                previousMonthInWords = "January";
            else if (Previous_CashBook_Month == 2)
                previousMonthInWords = "February";
            else if (Previous_CashBook_Month == 3)
                previousMonthInWords = "March";
            else if (Previous_CashBook_Month == 4)
                previousMonthInWords = "April";
            else if (Previous_CashBook_Month == 5)
                previousMonthInWords = "May";
            else if (Previous_CashBook_Month == 6)
                previousMonthInWords = "June";
            else if (Previous_CashBook_Month == 7)
                previousMonthInWords = "July";
            else if (Previous_CashBook_Month == 8)
                previousMonthInWords = "August";
            else if (Previous_CashBook_Month == 9)
                previousMonthInWords = "September";
            else if (Previous_CashBook_Month == 10)
                previousMonthInWords = "October";
            else if (Previous_CashBook_Month == 11)
                previousMonthInWords = "November";
            else if (Previous_CashBook_Month == 12)
                previousMonthInWords = "December";

            current_from_date = CashBook_Month + "-" + CashBook_Year;
            current_to_date = current_from_date;
            if (CashBook_Month == 1) {
                previous_from_date =
                        12 + "-" + (Integer.parseInt(CashBook_Year) - 1);
                clmn_heading1 =
                        "December" + "-" + (Integer.parseInt(CashBook_Year) -
                                            1);
            } else {
                previous_from_date =
                        (CashBook_Month - 1) + "-" + CashBook_Year;
                clmn_heading1 = previousMonthInWords + " " + CashBook_Year;
            }
            previous_to_date = previous_from_date;
            clmn_heading2 = to_monthInWords + " " + CashBook_Year;
        }

        Map map = null;
        map = new HashMap();
        File reportFile = null;
        try {
            System.out.println("inside try...");
            reportFile =
                    new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/MIS_Level2_Grouping.jasper"));

            if (!reportFile.exists())
                throw new JRRuntimeException("File J not found. The report design must be compiled first.");
            System.out.println("from ...");
            JasperReport jasperReport =
                (JasperReport)JRLoader.loadObject(reportFile.getPath());
            map.put("major_head_code", major_head);
            map.put("level2_code", level2);
            map.put("level3_code", level3);
            map.put("current_from_date", current_from_date);
            map.put("current_to_date", current_to_date);
            map.put("previous_from_date", previous_from_date);
            map.put("previous_to_date", previous_to_date);
            map.put("heading", heading);
            map.put("clmn_heading1", clmn_heading1);
            map.put("clmn_heading2", clmn_heading2);
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
                                   "attachment;filename=\"Level2_Grouping.pdf\"");
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
