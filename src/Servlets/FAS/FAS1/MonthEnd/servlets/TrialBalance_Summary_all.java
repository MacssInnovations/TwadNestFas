package Servlets.FAS.FAS1.MonthEnd.servlets;

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
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

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

/**
 * Servlet implementation class TrialBalance_Summary_all
 */
public class TrialBalance_Summary_all extends HttpServlet {
	  private static final String CONTENT_TYPE =
		        "text/html; charset=windows-1252";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TrialBalance_Summary_all() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

System.out.println("hai this is for all units");

        /**
         * Variables Declaration
         */
        Connection connection = null;
        Statement statement = null;
        CallableStatement cs = null;


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
         * Session Checking
         */
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


        /**
        *  Get Parameters
        */

        /** Get User ID */
        String userid = (String)session.getAttribute("UserId");

       

        /** Get From Cashbook Year */
        String from_CashBook_Year = request.getParameter("from_txtCB_Year");

        /** Get From Cashbook Month */
        String from_CashBook_Month = request.getParameter("from_txtCB_Month");

        /** Get To Cashbook Year */
        String to_CashBook_Year = request.getParameter("to_txtCB_Year");

        /** Get To Cashbook Month */
        String to_CashBook_Month = request.getParameter("to_txtCB_Month");
        
        /** Get To command */
        String command = request.getParameter("command");
        System.out.println("Command obtained is :::::::::"+command);
        
        /** Variables Declaration */
        int AccountUnitCode = 0;
        int OfficeCode = 0;
        int from_CashBookYear = 0;
        int from_CashBookMonth = 0;
        int to_CashBookYear = 0;
        int to_CashBookMonth = 0;
        if(command.equalsIgnoreCase("normal_tb"))
        {

        /** Convert String data into Integer Data */
        try {
           
            from_CashBookYear = Integer.parseInt(from_CashBook_Year);
            from_CashBookMonth = Integer.parseInt(from_CashBook_Month);
            to_CashBookYear = Integer.parseInt(to_CashBook_Year);
            to_CashBookMonth = Integer.parseInt(to_CashBook_Month);


            System.out.println("Account_Unit_Code After is:" +
                               AccountUnitCode);
            System.out.println("Office_Code After is:" + OfficeCode);
            System.out.println("CashBook_Year After is:" + from_CashBookYear);
            System.out.println("CashBook Month After is:" +
                               from_CashBookMonth);


        } catch (Exception e) {
            System.out.println("Exception in Converting Integer:" + e);
        }


        //This Two Variables for calculateting cashbook month and year

        String from_monthInWords = "";
        if (from_CashBookMonth == 1)
            from_monthInWords = "January";
        else if (from_CashBookMonth == 2)
            from_monthInWords = "February";
        else if (from_CashBookMonth == 3)
            from_monthInWords = "March";
        else if (from_CashBookMonth == 4)
            from_monthInWords = "April";
        else if (from_CashBookMonth == 5)
            from_monthInWords = "May";
        else if (from_CashBookMonth == 6)
            from_monthInWords = "June";
        else if (from_CashBookMonth == 7)
            from_monthInWords = "July";
        else if (from_CashBookMonth == 8)
            from_monthInWords = "August";
        else if (from_CashBookMonth == 9)
            from_monthInWords = "September";
        else if (from_CashBookMonth == 10)
            from_monthInWords = "October";
        else if (from_CashBookMonth == 11)
            from_monthInWords = "November";
        else if (from_CashBookMonth == 12)
            from_monthInWords = "December";

        String to_monthInWords = "";
        if (to_CashBookMonth == 1)
            to_monthInWords = "January";
        else if (to_CashBookMonth == 2)
            to_monthInWords = "February";
        else if (to_CashBookMonth == 3)
            to_monthInWords = "March";
        else if (to_CashBookMonth == 4)
            to_monthInWords = "April";
        else if (to_CashBookMonth == 5)
            to_monthInWords = "May";
        else if (to_CashBookMonth == 6)
            to_monthInWords = "June";
        else if (to_CashBookMonth == 7)
            to_monthInWords = "July";
        else if (to_CashBookMonth == 8)
            to_monthInWords = "August";
        else if (to_CashBookMonth == 9)
            to_monthInWords = "September";
        else if (to_CashBookMonth == 10)
            to_monthInWords = "October";
        else if (to_CashBookMonth == 11)
            to_monthInWords = "November";
        else if (to_CashBookMonth == 12)
            to_monthInWords = "December";


        /**
         *  Report Generation
         */

        try {
 
            File reportFile = null;
            try {
               
                	System.out.println("normal");
                reportFile =new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/GPF_Sum_Abs/TrialBalance_Summary_all_units.jasper"));
               
                
                if (!reportFile.exists())
                    throw new JRRuntimeException("File J not found. The report design must be compiled first.");
                System.out.println("from ...");
                JasperReport jasperReport =
                    (JasperReport)JRLoader.loadObject(reportFile.getPath());
                Map map = null;
                map = new HashMap();
                map.put("from_cashbookyear", from_CashBookYear);
                map.put("from_cashbookmonth", from_CashBookMonth);
                map.put("to_cashbookyear", to_CashBookYear);
                map.put("to_cashbookmonth", to_CashBookMonth);
                map.put("from_monthvalue", from_monthInWords);
                map.put("to_monthvalue", to_monthInWords);
                System.out.println("from .............");

                JasperPrint jasperPrint =
                    JasperFillManager.fillReport(jasperReport, map,
                                                 connection);

                System.out.println("upto");
                String rtype = "PDF"; // request.getParameter("cmbReportType");
                System.out.println(rtype);
                if (rtype.equalsIgnoreCase("HTML")) {
                    response.setContentType("text/html");

                    response.setHeader("Content-Disposition",
                                       "attachment;filename=\"Challan.html\"");
                    PrintWriter out = response.getWriter();
                    JRHtmlExporter exporter = new JRHtmlExporter();
                    exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,
                                          false);
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT,
                                          jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_WRITER,
                                          out);
                    exporter.exportReport();
                    out.flush();
                    out.close();
                } else if (rtype.equalsIgnoreCase("PDF")) {
                    System.out.println(rtype + "...inside PDF");
                    byte buf[] =
                        JasperExportManager.exportReportToPdf(jasperPrint);
                    response.setContentType("application/pdf");
                    response.setContentLength(buf.length);
                    //response.setHeader ("Content-Disposition", "attachment;filename=\"Challan.pdf\"");
                    OutputStream out = response.getOutputStream();
                    out.write(buf, 0, buf.length);
                    out.close();
                } else if (rtype.equalsIgnoreCase("EXCEL")) {

                    response.setContentType("application/vnd.ms-excel");
                    response.setHeader("Content-Disposition",
                                       "attachment;filename=\"Challan.xls\"");
                    JRXlsExporter exporterXLS = new JRXlsExporter();
                    exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT,
                                             jasperPrint);

                    ByteArrayOutputStream xlsReport =
                        new ByteArrayOutputStream();
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
                    ServletOutputStream ouputStream =
                        response.getOutputStream();
                    ouputStream.write(bytes, 0, bytes.length);
                    ouputStream.flush();
                    ouputStream.close();

                } else if (rtype.equalsIgnoreCase("TXT")) {

                    response.setContentType("text/plain");
                    response.setHeader("Content-Disposition",
                                       "attachment;filename=\"Challan.txt\"");

                    JRTextExporter exporter = new JRTextExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT,
                                          jasperPrint);
                    ByteArrayOutputStream txtReport =
                        new ByteArrayOutputStream();
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
                                          txtReport);
                    exporter.setParameter(JRTextExporterParameter.CHARACTER_WIDTH,
                                          new Integer(200));
                    exporter.setParameter(JRTextExporterParameter.CHARACTER_HEIGHT,
                                          new Integer(50));
                    exporter.exportReport();

                    byte[] bytes;
                    bytes = txtReport.toByteArray();
                    ServletOutputStream ouputStream =
                        response.getOutputStream();
                    ouputStream.write(bytes, 0, bytes.length);
                    ouputStream.flush();
                    ouputStream.close();

                }

            } catch (Exception ex) {
                String connectMsg =
                    "Could not create the report " + ex.getMessage(); //+ " uu " +  ex.getLocalizedMessage();
                System.out.println(connectMsg);
                //sendMessage(response,"The Challan Report Creation failed","ok");
                sendMessage(response, "Unable to display the PDF file", "ok");
            }
            //////////////////---------------------------- End -----------------
            System.out.println("here after PDF");
            //sendMessage(response,"The Trial Balance done successfully","ok");
            System.out.println("after send message");


        } catch (Exception e) {
            System.out.println("Exception in Main:" + e);
            try {
                connection.rollback();
            } catch (SQLException e1) {
                System.out.println("catch:" + e1);
            }
            String msg = "Trial Balance Has failed to Update";
            msg = msg + "<br><br>";
            sendMessage(response, msg, "ok");

        }
        }
        else if(command.equalsIgnoreCase("inc_SJV"))
        {


            /** Convert String data into Integer Data */
            try {
               
                from_CashBookYear = Integer.parseInt(from_CashBook_Year);
                from_CashBookMonth = Integer.parseInt(from_CashBook_Month);
                to_CashBookYear = Integer.parseInt(to_CashBook_Year);
                to_CashBookMonth = Integer.parseInt(to_CashBook_Month);
               

                System.out.println("Account_Unit_Code After is:" +
                                   AccountUnitCode);
                System.out.println("Office_Code After is:" + OfficeCode);
                System.out.println("CashBook_Year After is:" + from_CashBookYear);
                System.out.println("CashBook Month After is:" +
                                   from_CashBookMonth);


            } catch (Exception e) {
                System.out.println("Exception in Converting Integer:" + e);
            }


            //This Two Variables for calculateting cashbook month and year

            String from_monthInWords = "";
            if (from_CashBookMonth == 1)
                from_monthInWords = "January";
            else if (from_CashBookMonth == 2)
                from_monthInWords = "February";
            else if (from_CashBookMonth == 3)
                from_monthInWords = "March";
            else if (from_CashBookMonth == 4)
                from_monthInWords = "April";
            else if (from_CashBookMonth == 5)
                from_monthInWords = "May";
            else if (from_CashBookMonth == 6)
                from_monthInWords = "June";
            else if (from_CashBookMonth == 7)
                from_monthInWords = "July";
            else if (from_CashBookMonth == 8)
                from_monthInWords = "August";
            else if (from_CashBookMonth == 9)
                from_monthInWords = "September";
            else if (from_CashBookMonth == 10)
                from_monthInWords = "October";
            else if (from_CashBookMonth == 11)
                from_monthInWords = "November";
            else if (from_CashBookMonth == 12)
                from_monthInWords = "December";

            String to_monthInWords = "";
            if (to_CashBookMonth == 1)
                to_monthInWords = "January";
            else if (to_CashBookMonth == 2)
                to_monthInWords = "February";
            else if (to_CashBookMonth == 3)
                to_monthInWords = "March";
            else if (to_CashBookMonth == 4)
                to_monthInWords = "April";
            else if (to_CashBookMonth == 5)
                to_monthInWords = "May";
            else if (to_CashBookMonth == 6)
                to_monthInWords = "June";
            else if (to_CashBookMonth == 7)
                to_monthInWords = "July";
            else if (to_CashBookMonth == 8)
                to_monthInWords = "August";
            else if (to_CashBookMonth == 9)
                to_monthInWords = "September";
            else if (to_CashBookMonth == 10)
                to_monthInWords = "October";
            else if (to_CashBookMonth == 11)
                to_monthInWords = "November";
            else if (to_CashBookMonth == 12)
                to_monthInWords = "December";


            /**
             *  Report Generation
             */

            try {
     
                
            	File reportFile = null;
                try {
                    
                	String rtype = request.getParameter("txtoption");
                    System.out.println("rtype>>>>"+rtype);
                    if (!rtype.equalsIgnoreCase("EXCEL")) {
                    	System.out.println("normal");
                    reportFile =new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/GPF_Sum_Abs/TrialBalance_Summary_all_units_including_SJV.jasper"));
                    }else{
                	
                    	try{
                    	response.setContentType("application/vnd.ms-excel");
                        response.setHeader ("Content-Disposition", "attachment;filename=\"TB Data.xls\"");
            		
            		HSSFWorkbook hwb=new HSSFWorkbook();
            		HSSFSheet sheet =  hwb.createSheet("new sheet");

            		HSSFRow rowhead=   sheet.createRow((short)0);
            		
            		rowhead.createCell((short) 0).setCellValue("Account Head");
            		rowhead.createCell((short) 1).setCellValue("Account_Head_Desc");
             		rowhead.createCell((short) 2).setCellValue("Debit");
             		rowhead.createCell((short) 3).setCellValue("Credit");
             		rowhead.createCell((short) 4).setCellValue("Net(DR-CR)");                       	
             		ServletOutputStream fileOut=null;
             		
             		String sql="SELECT Account_Head_Code,\n" + 
             				"  Account_Head_Desc,\n" + 
             				"  Debit_Opening_Balance,\n" + 
             				"  Credit_Opening_Balance,\n" + 
             				"  Current_Month_Debit,\n" + 
             				"  Current_Month_Credit,\n" + 
             				"  (Current_Month_Debit-Current_Month_Credit) as Net_Amt,"+
             				"  Total_Debit,\n" + 
             				"  Total_Credit,\n" + 
             				"  (Total_Debit-Total_Credit) As Net,\n" + 
             				"  Case When Total_Debit>Total_Credit Then (Total_Debit-Total_Credit) Else 0 End As Debitnet,\n" + 
             				"  Case When Total_Credit>Total_Debit Then (Total_Credit-Total_Debit) Else 0 End As Creditnet\n" + 
             				"FROM\n" + 
             				"(Select Account_Head_Code,\n" + 
             				"    Account_Head_Desc,\n" + 
             				"    Sum(Debit_Opening_Balance)as Debit_Opening_Balance,\n" + 
             				"    Sum(Credit_Opening_Balance)as Credit_Opening_Balance,\n" + 
             				"    Sum(Current_Month_Debit)as Current_Month_Debit,\n" + 
             				"    Sum(Current_Month_Credit)as Current_Month_Credit,\n" + 
             				"    Sum(Total_Debit)As Total_Debit,\n" + 
             				"    sum(Total_Credit) as Total_Credit\n" + 
             				"    from\n" + 
             				"(SELECT Account_Head_Code,\n" + 
             				"    Account_Head_Desc,\n" + 
             				"    dob AS Debit_Opening_Balance,\n" + 
             				"    cob AS Credit_Opening_Balance,\n" + 
             				"    Current_Month_Debit,\n" + 
             				"    Current_Month_Credit,\n" + 
             				"    (Dob+Current_Month_Debit)  AS Total_Debit,\n" + 
             				"    (Cob+Current_Month_Credit) As Total_Credit\n" + 
             				"  FROM\n" + 
             				"(SELECT C.Account_Head_Code,\n" + 
             				"      (SELECT Account_Head_Desc\n" + 
             				"      FROM Com_Mst_Account_Heads H\n" + 
             				"      WHERE H.Account_Head_Code=C.Account_Head_Code\n" + 
             				"      )                          AS Account_Head_Desc,\n" + 
             				"      SUM(Debit_Opening_Balance) AS dob,\n" + 
             				"      SUM(Credit_Opening_Balance)AS cob,\n" + 
             				"      SUM(Current_Month_Debit)   AS Current_Month_Debit,\n" + 
             				"      SUM(Current_Month_Credit)  AS Current_Month_Credit\n" + 
             				"    FROM Fas_Trial_Balance c\n" + 
             				"    WHERE To_Date((Cashbook_Month\n" + 
             				"      ||'-'\n" + 
             				"      || Cashbook_Year),'mm-yyyy') BETWEEN To_Date("+from_CashBookMonth+"\n" + 
             				"      ||'-'\n" + 
             				"      ||"+from_CashBookYear+",'mm-yyyy')\n" + 
             				"    AND to_date("+to_CashBookMonth+"\n" + 
             				"      ||'-'\n" + 
             				"      ||"+to_CashBookYear+",'mm-yyyy')\n" + 
             				"    Group By Account_Head_Code\n" + 
             				"   \n" + 
             				"    union all\n" + 
             				"    SELECT C.Account_Head_Code,\n" + 
             				"      (SELECT Account_Head_Desc\n" + 
             				"      FROM Com_Mst_Account_Heads H\n" + 
             				"      WHERE H.Account_Head_Code=C.Account_Head_Code\n" + 
             				"      )                          AS Account_Head_Desc,\n" + 
             				"      SUM(Debit_Opening_Balance) AS dob,\n" + 
             				"      SUM(Credit_Opening_Balance)AS cob,\n" + 
             				"      SUM(Current_Month_Debit)   AS Current_Month_Debit,\n" + 
             				"      Sum(Current_Month_Credit)  As Current_Month_Credit\n" + 
             				"    From Fas_Trial_Balance_Supplement C\n" + 
             				"    Where Cashbook_Year="+to_CashBookYear+" And Cashbook_Month="+to_CashBookMonth+"\n" + 
             				"    Group By Account_Head_Code))\n" + 
             				"    Group By Account_Head_Code,Account_Head_Desc\n" + 
             				"     Order By Account_Head_Code)";
             		System.out.println("ss::::"+sql);
                    PreparedStatement ps2=connection.prepareStatement(sql);
                    ResultSet rs2=ps2.executeQuery();
                   int j=1;
                   System.out.println("value of rows :::123"+j);
                    while(rs2.next())
                    {
                    	HSSFRow row=   sheet.createRow((int)j);
                     	
                 		row.createCell((short) 0).setCellValue(rs2.getInt("Account_Head_Code"));
                 		row.createCell((short) 1).setCellValue(rs2.getString("Account_Head_Desc"));
                 		row.createCell((short) 2).setCellValue(rs2.getDouble("Current_Month_Debit"));
                 		row.createCell((short) 3).setCellValue(rs2.getDouble("Current_Month_Credit"));                       	
                 		row.createCell((short) 4).setCellValue(rs2.getDouble("Net_Amt"));	
                 		j++;
                    }
                    System.out.println("value of rows :::"+j);
                    fileOut = response.getOutputStream();
                    hwb.write(fileOut);
               		fileOut.close();
                		} catch ( Exception ex ) {
                		    System.out.println("Journal Exception :"+ex);

                		}
                  }
                    
                    if (!reportFile.exists())
                        throw new JRRuntimeException("File J not found. The report design must be compiled first.");
                    System.out.println("from ...");
                    JasperReport jasperReport =
                        (JasperReport)JRLoader.loadObject(reportFile.getPath());
                    Map map = null;
                    map = new HashMap();
                    map.put("from_cashbookyear", from_CashBookYear);
                    map.put("from_cashbookmonth", from_CashBookMonth);
                    map.put("to_cashbookyear", to_CashBookYear);
                    map.put("to_cashbookmonth", to_CashBookMonth);
                    map.put("from_monthvalue", from_monthInWords);
                    map.put("to_monthvalue", to_monthInWords);
                    System.out.println("from .............");

                    JasperPrint jasperPrint =
                        JasperFillManager.fillReport(jasperReport, map,
                                                     connection);

                    System.out.println("upto");
                    
                    //String rtype = "PDF"; // request.getParameter("cmbReportType");
                    
                    if (rtype.equalsIgnoreCase("HTML")) {
                        response.setContentType("text/html");

                        response.setHeader("Content-Disposition",
                                           "attachment;filename=\"Challan.html\"");
                        PrintWriter out = response.getWriter();
                        JRHtmlExporter exporter = new JRHtmlExporter();
                        exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,
                                              false);
                        exporter.setParameter(JRExporterParameter.JASPER_PRINT,
                                              jasperPrint);
                        exporter.setParameter(JRExporterParameter.OUTPUT_WRITER,
                                              out);
                        exporter.exportReport();
                        out.flush();
                        out.close();
                    } else if (rtype.equalsIgnoreCase("PDF")) {
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
//                    else if (rtype.equalsIgnoreCase("EXCEL")) {
//
//                    	System.out.println("Inside rtype");
//                        response.setContentType("application/vnd.ms-excel");
//                        response.setHeader("Content-Disposition",
//                                "attachment;filename=\"Challan.xls\"");
//                        System.out.println("Inside******************1*****************");
//                        
//                        
//                        
//                        JRXlsExporter exporterXLS = new JRXlsExporter();
//                        exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT,
//                                                 jasperPrint);
//                        
//                        System.out.println("Inside******************2*****************");
//
//                        ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
//                        System.out.println("Inside******************3*****************");
//                        
//                        exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,
//                                                 xlsReport);
//                        System.out.println("Inside******************4*****************");
//                        
//                        exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
//                                                 Boolean.TRUE);
//                        System.out.println("Inside******************5*****************");
//                        
//                        exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE,
//                                                 Boolean.TRUE);
//                        System.out.println("Inside******************6*****************");
//                        
//                        exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,
//                                                 Boolean.FALSE);
//                        System.out.println("Inside******************7*****************");
//                        
//                        exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
//                                                 Boolean.TRUE);
//                        System.out.println("Inside*****************8******************");
//                        exporterXLS.exportReport();
//                        System.out.println("Inside*****************9******************");
//                        byte[] bytes;
//                        System.out.println("Inside*****************10******************");
//                        bytes = xlsReport.toByteArray();
//                        System.out.println("Inside*****************11******************");
//                        ServletOutputStream ouputStream = response.getOutputStream();
//                        System.out.println("Inside*****************12******************");
//                        ouputStream.write(bytes, 0, bytes.length);
//                        System.out.println("Inside*****************13******************");
//                        ouputStream.flush();
//                        System.out.println("Inside*****************14******************");
//                        ouputStream.close();
//                        System.out.println("Inside*****************15******************");
//
//                    }
                    else if (rtype.equalsIgnoreCase("TXT")) {

                        response.setContentType("text/plain");
                        response.setHeader("Content-Disposition",
                                           "attachment;filename=\"Challan.txt\"");

                        JRTextExporter exporter = new JRTextExporter();
                        exporter.setParameter(JRExporterParameter.JASPER_PRINT,
                                              jasperPrint);
                        ByteArrayOutputStream txtReport =
                            new ByteArrayOutputStream();
                        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
                                              txtReport);
                        exporter.setParameter(JRTextExporterParameter.CHARACTER_WIDTH,
                                              new Integer(200));
                        exporter.setParameter(JRTextExporterParameter.CHARACTER_HEIGHT,
                                              new Integer(50));
                        exporter.exportReport();

                        byte[] bytes;
                        bytes = txtReport.toByteArray();
                        ServletOutputStream ouputStream =
                            response.getOutputStream();
                        ouputStream.write(bytes, 0, bytes.length);
                        ouputStream.flush();
                        ouputStream.close();

                    }

                } catch (Exception ex) {
                    String connectMsg =
                        "Could not create the report " + ex.getMessage(); //+ " uu " +  ex.getLocalizedMessage();
                    System.out.println(connectMsg);
                    //sendMessage(response,"The Challan Report Creation failed","ok");
                    sendMessage(response, "Unable to display the PDF file", "ok");
                }
                //////////////////---------------------------- End -----------------
                System.out.println("here after PDF");
                //sendMessage(response,"The Trial Balance done successfully","ok");
                System.out.println("after send message");


            } catch (Exception e) {
                System.out.println("Exception in Main:" + e);
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    System.out.println("catch:" + e1);
                }
                String msg = "Trial Balance Has failed to Update";
                msg = msg + "<br><br>";
                sendMessage(response, msg, "ok");

            }
        }
        else if (command.equalsIgnoreCase("sup_tb"))
        {
        	System.out.println("supp starts:::::");
        	/** Convert String data into Integer Data */
            try {
               
                from_CashBookYear = Integer.parseInt(from_CashBook_Year);
                from_CashBookMonth = Integer.parseInt(from_CashBook_Month);
                //to_CashBookYear = Integer.parseInt(to_CashBook_Year);
               // to_CashBookMonth = Integer.parseInt(to_CashBook_Month);


                System.out.println("Account_Unit_Code After is:" +
                                   AccountUnitCode);
                System.out.println("Office_Code After is:" + OfficeCode);
                System.out.println("CashBook_Year After is:" + from_CashBookYear);
                System.out.println("CashBook Month After is:" +
                                   from_CashBookMonth);


            } catch (Exception e) {
                System.out.println("Exception in Converting Integer:" + e);
            }
            int supNo = Integer.parseInt(request.getParameter("supNo"));
            System.out.println("supNo:::"+supNo);
            
            String from_monthInWords = "";
            
            if (from_CashBookMonth == 3)
                from_monthInWords = "March";
            
            //This Two Variables for calculateting cashbook month and year
/*
            String from_monthInWords = "";
            if (from_CashBookMonth == 1)
                from_monthInWords = "January";
            else if (from_CashBookMonth == 2)
                from_monthInWords = "February";
            else if (from_CashBookMonth == 3)
                from_monthInWords = "March";
            else if (from_CashBookMonth == 4)
                from_monthInWords = "April";
            else if (from_CashBookMonth == 5)
                from_monthInWords = "May";
            else if (from_CashBookMonth == 6)
                from_monthInWords = "June";
            else if (from_CashBookMonth == 7)
                from_monthInWords = "July";
            else if (from_CashBookMonth == 8)
                from_monthInWords = "August";
            else if (from_CashBookMonth == 9)
                from_monthInWords = "September";
            else if (from_CashBookMonth == 10)
                from_monthInWords = "October";
            else if (from_CashBookMonth == 11)
                from_monthInWords = "November";
            else if (from_CashBookMonth == 12)
                from_monthInWords = "December";

            String to_monthInWords = "";
            if (to_CashBookMonth == 1)
                to_monthInWords = "January";
            else if (to_CashBookMonth == 2)
                to_monthInWords = "February";
            else if (to_CashBookMonth == 3)
                to_monthInWords = "March";
            else if (to_CashBookMonth == 4)
                to_monthInWords = "April";
            else if (to_CashBookMonth == 5)
                to_monthInWords = "May";
            else if (to_CashBookMonth == 6)
                to_monthInWords = "June";
            else if (to_CashBookMonth == 7)
                to_monthInWords = "July";
            else if (to_CashBookMonth == 8)
                to_monthInWords = "August";
            else if (to_CashBookMonth == 9)
                to_monthInWords = "September";
            else if (to_CashBookMonth == 10)
                to_monthInWords = "October";
            else if (to_CashBookMonth == 11)
                to_monthInWords = "November";
            else if (to_CashBookMonth == 12)
                to_monthInWords = "December";
*/

            /**
             *  Report Generation
             */

            try {
     
                File reportFile = null;
                try {
                   
                    
                    reportFile =new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/GPF_Sum_Abs/supplement_tb/TrialBalance_Summary_all_units_supplement.jasper"));
                   
                    
                    if (!reportFile.exists())
                        throw new JRRuntimeException("File J not found. The report design must be compiled first.");
                    System.out.println("from ...");
                    JasperReport jasperReport =
                        (JasperReport)JRLoader.loadObject(reportFile.getPath());
                    Map map = null;
                    map = new HashMap();
                    map.put("from_cashbookyear", from_CashBookYear);
                    map.put("from_cashbookmonth", from_CashBookMonth);
                    //map.put("to_cashbookyear", to_CashBookYear);
                    //map.put("to_cashbookmonth", to_CashBookMonth);
                    map.put("from_monthvalue", from_monthInWords);
                    //map.put("to_monthvalue", to_monthInWords);
                    map.put("SupNo", supNo);
                    
                    System.out.println("from .............");

                    JasperPrint jasperPrint =
                        JasperFillManager.fillReport(jasperReport, map,
                                                     connection);

                    System.out.println("upto");
                    String rtype = "PDF"; // request.getParameter("cmbReportType");
                    System.out.println(rtype);
                    if (rtype.equalsIgnoreCase("HTML")) {
                        response.setContentType("text/html");

                        response.setHeader("Content-Disposition",
                                           "attachment;filename=\"Challan.html\"");
                        PrintWriter out = response.getWriter();
                        JRHtmlExporter exporter = new JRHtmlExporter();
                        exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,
                                              false);
                        exporter.setParameter(JRExporterParameter.JASPER_PRINT,
                                              jasperPrint);
                        exporter.setParameter(JRExporterParameter.OUTPUT_WRITER,
                                              out);
                        exporter.exportReport();
                        out.flush();
                        out.close();
                    } else if (rtype.equalsIgnoreCase("PDF")) {
                        System.out.println(rtype + "...inside PDF");
                        byte buf[] =
                            JasperExportManager.exportReportToPdf(jasperPrint);
                        response.setContentType("application/pdf");
                        response.setContentLength(buf.length);
                        //response.setHeader ("Content-Disposition", "attachment;filename=\"Challan.pdf\"");
                        OutputStream out = response.getOutputStream();
                        out.write(buf, 0, buf.length);
                        out.close();
                    } else if (rtype.equalsIgnoreCase("EXCEL")) {

                        response.setContentType("application/vnd.ms-excel");
                        response.setHeader("Content-Disposition",
                                           "attachment;filename=\"Challan.xls\"");
                        JRXlsExporter exporterXLS = new JRXlsExporter();
                        exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT,
                                                 jasperPrint);

                        ByteArrayOutputStream xlsReport =
                            new ByteArrayOutputStream();
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
                        ServletOutputStream ouputStream =
                            response.getOutputStream();
                        ouputStream.write(bytes, 0, bytes.length);
                        ouputStream.flush();
                        ouputStream.close();

                    } else if (rtype.equalsIgnoreCase("TXT")) {

                        response.setContentType("text/plain");
                        response.setHeader("Content-Disposition",
                                           "attachment;filename=\"Challan.txt\"");

                        JRTextExporter exporter = new JRTextExporter();
                        exporter.setParameter(JRExporterParameter.JASPER_PRINT,
                                              jasperPrint);
                        ByteArrayOutputStream txtReport =
                            new ByteArrayOutputStream();
                        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
                                              txtReport);
                        exporter.setParameter(JRTextExporterParameter.CHARACTER_WIDTH,
                                              new Integer(200));
                        exporter.setParameter(JRTextExporterParameter.CHARACTER_HEIGHT,
                                              new Integer(50));
                        exporter.exportReport();

                        byte[] bytes;
                        bytes = txtReport.toByteArray();
                        ServletOutputStream ouputStream =
                            response.getOutputStream();
                        ouputStream.write(bytes, 0, bytes.length);
                        ouputStream.flush();
                        ouputStream.close();

                    }

                } catch (Exception ex) {
                    String connectMsg =
                        "Could not create the report " + ex.getMessage(); //+ " uu " +  ex.getLocalizedMessage();
                    System.out.println(connectMsg);
                    //sendMessage(response,"The Challan Report Creation failed","ok");
                    sendMessage(response, "Unable to display the PDF file", "ok");
                }
                //////////////////---------------------------- End -----------------
                System.out.println("here after PDF");
                //sendMessage(response,"The Trial Balance done successfully","ok");
                System.out.println("after send message");


            } catch (Exception e) {
                System.out.println("Exception in Main:" + e);
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    System.out.println("catch:" + e1);
                }
                String msg = "Trial Balance Has failed to Update";
                msg = msg + "<br><br>";
                sendMessage(response, msg, "ok");

            }
            	
        }
	}
	  private void sendMessage(HttpServletResponse response, String msg,
			              String bType) {
			try {
			String url =
			 "org/Library/jsps/Messenger.jsp?message=" + msg + "&button=" +
			 bType;
			//response.sendRedirect(url);
			} catch (Exception e) {
			System.out.println("ERROR");
			}
			}
}
