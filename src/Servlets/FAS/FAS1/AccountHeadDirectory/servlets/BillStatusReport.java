package Servlets.FAS.FAS1.AccountHeadDirectory.servlets;

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

public class BillStatusReport extends HttpServlet {
    private static final String CONTENT_TYPE = null;

	public void init(ServletConfig config) throws ServletException {
        super.init(config);

    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
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
        } catch (Exception e) {
            System.out.println("Exception in openeing connection :" + e);
            //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

        }
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
        String strCommand = "", major_id = "", xml = "",minor_id = "";
        System.out.println("from here");
        try {
            strCommand = request.getParameter("Command");
        } catch (Exception e) {
            System.out.println("In assinging command value in doPost");

        }
        System.out.println();
        if (strCommand.equalsIgnoreCase("loadMinor")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            PrintWriter out = response.getWriter();
            System.out.println("inside minorload....");
            major_id = request.getParameter("txtMajor_id");
            System.out.println("major_id"+major_id);
            xml = "<response><command>loadMinor</command>";
            try {
                System.out.println("inside try....");
                ps =
  con.prepareStatement("select BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,BILL_MINOR_TYPE_DESC from  FAS_BILL_MINOR_TYPES_MST where BILL_MAJOR_TYPE_CODE::varchar=?");
                ps.setString(1, major_id);
                rs = ps.executeQuery();
                xml = xml + "<flag>success</flag>";
                while (rs.next()) {
                    xml =
 xml + "<Maj_id>" + rs.getString("BILL_MAJOR_TYPE_CODE") + "</Maj_id>";
                    xml =
 xml + "<Min_id>" + rs.getInt("BILL_MINOR_TYPE_CODE") + "</Min_id>";
                    xml =
 xml + "<Min_desc>" + rs.getString("BILL_MINOR_TYPE_DESC") + "</Min_desc>";
                }
            } catch (Exception e) {
                System.out.println("catch..in..loadMinor::" + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
        }                
                
                
                
                
                
                
              
         if(strCommand.equalsIgnoreCase("loadSub")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            PrintWriter out = response.getWriter();
            System.out.println("inside sub code....");
            minor_id = request.getParameter("txtMinor_id");
            
            xml = "<response><command>loadSub</command>";
            try {
                System.out.println("inside try....");
                ps =
  con.prepareStatement("select BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,BILL_SUB_TYPE_CODE,BILL_SUB_TYPE_DESC from  FAS_BILL_SUB_TYPES where BILL_MINOR_TYPE_CODE::varchar=?");
                System.out.println("select BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,BILL_SUB_TYPE_CODE,BILL_SUB_TYPE_DESC from  FAS_BILL_SUB_TYPES where BILL_MINOR_TYPE_CODE=?");
                
                
                ps.setString(1, minor_id);
                rs = ps.executeQuery();
                xml = xml + "<flag>success</flag>";
                while (rs.next()) {
                    xml =
 xml + "<Maj_id>" + rs.getString("BILL_MAJOR_TYPE_CODE") + "</Maj_id>";
                    xml =
 xml + "<Min_id>" + rs.getInt("BILL_MINOR_TYPE_CODE") + "</Min_id>";
                    
                    xml =
    xml + "<Sub_id>" + rs.getInt("BILL_SUB_TYPE_CODE") + "</Sub_id>";
                    		                      
                    
                    xml =
 xml + "<Min_desc>" + rs.getString("BILL_SUB_TYPE_DESC") + "</Min_desc>";
                }
            } catch (Exception e) {
                System.out.println("catch..in..loadSub::" + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
        }
    
    
    
    
    
    
    
    
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {

        /**
         * Variables Declarations
         */
        Connection connection = null;
        Statement statement = null;
        String fromdate="";
        String todate="";
        String command1="";
        /**
         * Database Connection
         */
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

//            ConnectionString =
//                    strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
//                    ":" + strsid.trim();
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


        /**
         * Set Content Type
         */
        response.setContentType(CONTENT_TYPE);


        /**
         *   Session Checking
         */
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

        int cmbAcc_UnitCode   =
                Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
System.out.print(cmbAcc_UnitCode);

    	int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));

        
        int CashBook_Year =
            Integer.parseInt(request.getParameter("txtCB_Year"));
        int CashBook_Month =
            Integer.parseInt(request.getParameter("txtCB_Month"));
         fromdate = request.getParameter("txtfromdate");
         todate = request.getParameter("txttodate");
         System.out.println("CashBook_Year---" + CashBook_Year);
         //System.out.println("CashBook_Month--" + CashBook_Month);
         
         System.out.println("fromdate --" + fromdate);
         System.out.println("todate --" + todate);
         
        String Major_Grp = request.getParameter("Major_Grp");
        String Minor_Grp = request.getParameter("Minor_Grp");
        String sub_type = request.getParameter("sub_type");
     
        String particular = request.getParameter("month_year");
        System.out.println("particular --" + particular);	
        
    
       
        String monthInWords = "";
        if (CashBook_Month == 1)
            monthInWords = "January";
        else if (CashBook_Month == 2)
            monthInWords = "February";
        else if (CashBook_Month == 3)
            monthInWords = "March";
        else if (CashBook_Month == 4)
            monthInWords = "April";
        else if (CashBook_Month == 5)
            monthInWords = "May";
        else if (CashBook_Month == 6)
            monthInWords = "June";
        else if (CashBook_Month == 7)
            monthInWords = "July";
        else if (CashBook_Month == 8)
            monthInWords = "August";
        else if (CashBook_Month == 9)
            monthInWords = "September";
        else if (CashBook_Month == 10)
            monthInWords = "October";
        else if (CashBook_Month == 11)
            monthInWords = "November";
        else if (CashBook_Month == 12)
            monthInWords = "December";
        System.out.println("CashBook_Month..." + CashBook_Month);


        File reportFile = null;
        try {

            if (particular.equalsIgnoreCase("particular_cb")) {
               System.out.println("abs--->");
                reportFile =
                        new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/VoucherMonitor/BillStatusReport1.jasper"));
           
                System.out.println("reportFile"+reportFile);}
            else if(particular.equalsIgnoreCase("more_cb")){
                	reportFile =
                            new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/VoucherMonitor/BillStatusReport1.jasper"));
           	
                }

            if (!reportFile.exists())
                throw new JRRuntimeException("File J not found. The report design must be compiled first.");
            JasperReport jasperReport =
                (JasperReport)JRLoader.loadObject(reportFile.getPath());
            Map map = null;
            map = new HashMap();
            map.put("UnitId", cmbAcc_UnitCode);
            map.put("OfceId", cmbOffice_code);
            map.put("month", CashBook_Month);
            map.put("year", CashBook_Year);
            map.put("monthwords", monthInWords);
            
			map.put("fromdate",fromdate );
            map.put("todate", todate);
            /*map.put("FromDate",Major_Grp);
            map.put("ToDate", Minor_Grp);
            map.put("FromDate",sub_type );*/
            
            
            
          
            JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, map, connection);

            String rtype = "PDF";

            if (rtype.equalsIgnoreCase("PDF")) {

                System.out.println(rtype + "...inside PDF");

                byte buf[] =
                    JasperExportManager.exportReportToPdf(jasperPrint);
                response.setContentType("application/pdf");
                response.setContentLength(buf.length);
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"BillReport.pdf\"");
                OutputStream out = response.getOutputStream();
                out.write(buf, 0, buf.length);
                out.close();
            }


        } catch (Exception ex) {
            String connectMsg =
                "Could not create the report " + ex.getMessage();
            String con_err = "Could not create the report " + ex;
            System.out.println(con_err);
            System.out.println(connectMsg);

        }


    }
}
