package Servlets.FAS.FAS1.ReceiptSystem.Reports;

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

import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

public class Misc_Rep_AnnexureIV_Serv extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {

        Connection connection = null;
        Statement statement = null;
        ResultSet res = null, res1 = null;
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

        int from_CashBook_Year =
            Integer.parseInt(request.getParameter("txtfrom_CB_Year"));
        int from_CashBook_Month =
            Integer.parseInt(request.getParameter("txtfrom_CB_Month"));
        int to_CashBook_Year =
            Integer.parseInt(request.getParameter("txtto_CB_Year"));
        int to_CashBook_Month =
            Integer.parseInt(request.getParameter("txtto_CB_Month"));

        System.out.println("from CashBook_Year is:" + from_CashBook_Year);
        System.out.println("from CashBook Month is:" + from_CashBook_Month);
        System.out.println("to CashBook_Year is:" + to_CashBook_Year);
        System.out.println("to CashBook Month is:" + to_CashBook_Month);

        String from_monthInWords = "";
        if (from_CashBook_Month == 1)
            from_monthInWords = "January";
        else if (from_CashBook_Month == 2)
            from_monthInWords = "February";
        else if (from_CashBook_Month == 3)
            from_monthInWords = "March";
        else if (from_CashBook_Month == 4)
            from_monthInWords = "April";
        else if (from_CashBook_Month == 5)
            from_monthInWords = "May";
        else if (from_CashBook_Month == 6)
            from_monthInWords = "June";
        else if (from_CashBook_Month == 7)
            from_monthInWords = "July";
        else if (from_CashBook_Month == 8)
            from_monthInWords = "August";
        else if (from_CashBook_Month == 9)
            from_monthInWords = "September";
        else if (from_CashBook_Month == 10)
            from_monthInWords = "October";
        else if (from_CashBook_Month == 11)
            from_monthInWords = "November";
        else if (from_CashBook_Month == 12)
            from_monthInWords = "December";


        String to_monthInWords = "";
        if (to_CashBook_Month == 1)
            to_monthInWords = "January";
        else if (to_CashBook_Month == 2)
            to_monthInWords = "February";
        else if (to_CashBook_Month == 3)
            to_monthInWords = "March";
        else if (to_CashBook_Month == 4)
            to_monthInWords = "April";
        else if (to_CashBook_Month == 5)
            to_monthInWords = "May";
        else if (to_CashBook_Month == 6)
            to_monthInWords = "June";
        else if (to_CashBook_Month == 7)
            to_monthInWords = "July";
        else if (to_CashBook_Month == 8)
            to_monthInWords = "August";
        else if (to_CashBook_Month == 9)
            to_monthInWords = "September";
        else if (to_CashBook_Month == 10)
            to_monthInWords = "October";
        else if (to_CashBook_Month == 11)
            to_monthInWords = "November";
        else if (to_CashBook_Month == 12)
            to_monthInWords = "December";

//        String mnth="";
//        if(from_CashBook_Month==to_CashBook_Month) {
//            mnth="for the month of "+from_monthInWords;
//        }else{
//            mnth="during the period "+from_monthInWords+" to "+to_monthInWords;
//        }

System.out.println("be4 monthhhhhhhhhhhhhhhh");

 String mnth="";
 if(from_CashBook_Year==to_CashBook_Year) {
 
 if(from_CashBook_Month==to_CashBook_Month) {
 
     mnth="for the month of "+from_monthInWords+" "+to_CashBook_Year;
 }else{
     mnth="during the period "+from_monthInWords+" to "+to_monthInWords+" "+to_CashBook_Year;
 }
 }else{            
         mnth="during the period "+from_monthInWords+" "+from_CashBook_Year+" to "+to_monthInWords+" "+to_CashBook_Year;            
 }             
        
         System.out.println("month..."+mnth);
        Map map = null;
        map = new HashMap();
        File reportFile = null;


        try {
            System.out.println("inside try...");
            reportFile =
                    new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/FAS_Annexure-IV.jasper"));

            if (!reportFile.exists())
                throw new JRRuntimeException("File J not found. The report design must be compiled first.");
            System.out.println("from ...");
            JasperReport jasperReport =
                (JasperReport)JRLoader.loadObject(reportFile.getPath());
            String monthInWords =
                from_monthInWords + "/" + from_CashBook_Year + " to " +
                to_monthInWords + "/" + to_CashBook_Year;
System.out.println("monthInWords::::::::::::"+monthInWords);
            map.put("from_cashbk_yr", from_CashBook_Year);
            map.put("from_cashbk_mon", from_CashBook_Month);
            map.put("to_cashbk_yr", to_CashBook_Year);
            map.put("to_cashbk_mon", to_CashBook_Month);
            map.put("monthInWords", monthInWords);
            map.put("mnth", mnth);
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
                                   "attachment;filename=\"Annexure IV.pdf\"");
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
