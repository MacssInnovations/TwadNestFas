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

public class Annex_IIIa_Serv extends HttpServlet {
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
        ResultSet results = null;
        ResultSet results1 = null;
        ResultSet results3 = null;
        ResultSet results4 = null;
        ResultSet results5 = null;
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps6 = null;
        boolean flag = false;
        String str = "";
        String strnew = "";

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


        String from_CashBook_Year = request.getParameter("txtfrom_CB_Year");
        String from_CashBook_Month = request.getParameter("txtfrom_CB_Month");
        String to_CashBook_Year = request.getParameter("txtto_CB_Year");
        String to_CashBook_Month = request.getParameter("txtto_CB_Month");


        System.out.println("from CashBook_Year is comes here:" + from_CashBook_Year);
        System.out.println("from CashBook Month is:" + from_CashBook_Month);
        System.out.println("to CashBook_Year is:" + to_CashBook_Year);
        System.out.println("to CashBook Month is:" + to_CashBook_Month);


        String from_monthInWords = "";
        if (from_CashBook_Month.equals("1"))
            from_monthInWords = "January";
        else if (from_CashBook_Month.equals("2"))
            from_monthInWords = "February";
        else if (from_CashBook_Month.equals("3"))
            from_monthInWords = "March";
        else if (from_CashBook_Month.equals("4"))
            from_monthInWords = "April";
        else if (from_CashBook_Month.equals("5"))
            from_monthInWords = "May";
        else if (from_CashBook_Month.equals("6"))
            from_monthInWords = "June";
        else if (from_CashBook_Month.equals("7"))
            from_monthInWords = "July";
        else if (from_CashBook_Month.equals("8"))
            from_monthInWords = "August";
        else if (from_CashBook_Month.equals("9"))
            from_monthInWords = "September";
        else if (from_CashBook_Month.equals("10"))
            from_monthInWords = "October";
        else if (from_CashBook_Month.equals("11"))
            from_monthInWords = "November";
        else if (from_CashBook_Month.equals("12"))
            from_monthInWords = "December";


        String to_monthInWords = "";
        if (to_CashBook_Month.equals("1"))
            to_monthInWords = "January";
        else if (to_CashBook_Month.equals("2"))
            to_monthInWords = "February";
        else if (to_CashBook_Month.equals("3"))
            to_monthInWords = "March";
        else if (to_CashBook_Month.equals("4"))
            to_monthInWords = "April";
        else if (to_CashBook_Month.equals("5"))
            to_monthInWords = "May";
        else if (to_CashBook_Month.equals("6"))
            to_monthInWords = "June";
        else if (to_CashBook_Month.equals("7"))
            to_monthInWords = "July";
        else if (to_CashBook_Month.equals("8"))
            to_monthInWords = "August";
        else if (to_CashBook_Month.equals("9"))
            to_monthInWords = "September";
        else if (to_CashBook_Month.equals("10"))
            to_monthInWords = "October";
        else if (to_CashBook_Month.equals("11"))
            to_monthInWords = "November";
        else if (to_CashBook_Month.equals("12"))
            to_monthInWords = "December";


        String mnth="";
//        if(from_CashBook_Month==to_CashBook_Month) {
//            mnth="for the month of "+from_monthInWords;
//        }else{
//            mnth="during the period "+from_monthInWords+" to "+to_monthInWords;
//        }
        
        
         if(from_CashBook_Year.equals(to_CashBook_Year))
         {
              if(from_CashBook_Month.equals(to_CashBook_Month)) 
              {
                  mnth="For the Period Of "+from_monthInWords + "/" + from_CashBook_Year;
              }
              else {
                  mnth="During the Period "+from_monthInWords + "/" + from_CashBook_Year + " to " +to_monthInWords + "/" + to_CashBook_Year;
                  }
         }
         else 
         {
             mnth="During the Period "+ from_monthInWords + "/" + from_CashBook_Year + " to " +
                 to_monthInWords + "/" + to_CashBook_Year;
         }

        File reportFile = null;

        try {
            System.out.println("inside try...");
            reportFile =
                    new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/Annexure-III(a).jasper"));

            if (!reportFile.exists())
                throw new JRRuntimeException("File J not found. The report design must be compiled first.");
            System.out.println("from ...");
            JasperReport jasperReport =
                (JasperReport)JRLoader.loadObject(reportFile.getPath());
            Map map = null;
            map = new HashMap();
            //map.put("acc_for_off_id",Office_Code);
            map.put("from_cashbk_yr", from_CashBook_Year);
            map.put("from_cashbk_mon", from_CashBook_Month);
            map.put("to_cashbk_yr", to_CashBook_Year);
            map.put("to_cashbk_mon", to_CashBook_Month);
            map.put("from_monthInWords", mnth);
      //      map.put("to_monthInWords", to_monthInWords);


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
                // response.setHeader("content-disposition", "inline;filename=OpenActionItems.pdf");
                //response.setContentType("application/force-download");

                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"Annexure-III(a).pdf\"");
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
        /*  }
        else{
          receiptno="";
           receiptnonew=0;
            //xml=xml+"<flag>failure</flag>";
          //   reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/SalesTax_Return_report.jasper"));
        response.sendRedirect(request.getContextPath()+"/org/FAS/FAS1/Reports/ReceiptSystem/jsps/Sales_Tax_ReturnJSP.jsp?param=checknull");
         // PrintWriter out=response.getWriter();
          //out.println("<script type='text/javascript'> alert('No receipt no. for the corresponding Acc.Head')</script>");
           System.out.println("sales tax not available");
           //response.sendRedirect(request.getContextPath()+"/org/FAS/FAS1/Reports/ReceiptSystem/jsps/Sales_Tax_ReturnJSP.jsp?param=checknull");
        }*/


        //////////////////---------------------------- End -----------------
        System.out.println("here after PDF");
        //sendMessage(response,"The Trial Balance done successfully","ok");
        System.out.println("after send message");

        //response.sendRedirect(request.getContextPath()+"/org/FAS/FAS1/Reports/ReceiptSystem/jsps/Sales_Tax_ReturnJSP.jsp");

    }
}
