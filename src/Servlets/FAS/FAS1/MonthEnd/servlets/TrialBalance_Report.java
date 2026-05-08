package Servlets.FAS.FAS1.MonthEnd.servlets;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.math.MathContext;

import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

import Servlets.FAS.FAS1.CommonClass.ExcelConverter;

import java.sql.*;

import java.util.HashMap;
import java.util.Map;

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

public class TrialBalance_Report extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
    
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
     
    }

    public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
    {
        Connection connection=null;
        Statement statement=null;
        CallableStatement cs=null;
        ResultSet rs2=null;
        int kk=0;
        
        try
                  {
                       ResourceBundle rs=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
                         String ConnectionString="";
                        
                         String strDriver=rs.getString("Config.DATA_BASE_DRIVER");
                         String strdsn=rs.getString("Config.DSN");
                         String strhostname=rs.getString("Config.HOST_NAME");
                         String strportno=rs.getString("Config.PORT_NUMBER");
                         String strsid=rs.getString("Config.SID");
                         String strdbusername=rs.getString("Config.USER_NAME");
                         String strdbpassword=rs.getString("Config.PASSWORD");
                           
                         ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
                          Class.forName(strDriver.trim());
                          connection=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
              try
              {
                statement=connection.createStatement();
                connection.clearWarnings();
              }
              catch(SQLException e)
              {
                  System.out.println("Exception in creating statement:"+e);
              }          
           }
          catch(Exception e)
          {
             System.out.println("Exception in openeing connection:"+e);
          }
        
        response.setContentType(CONTENT_TYPE);
       // PrintWriter out = response.getWriter();
        HttpSession session=request.getSession(false);
        try
        {
            
            if(session==null)
            {
                System.out.println(request.getContextPath()+"/index.jsp");
                response.sendRedirect(request.getContextPath()+"/index.jsp");
               
            }
            System.out.println(session);
                
        }catch(Exception e)
        {
        System.out.println("Redirect Error :"+e);
        }
        
        String userid=(String)session.getAttribute("UserId");
         
         
        String Account_unit_Code=request.getParameter("cmbAcc_UnitCode");
        String CashBook_Year=request.getParameter("txtCB_Year");
        String CashBook_Month=request.getParameter("txtCB_Month");
        
        
        
        
        int AccountUnitCode=0;
        int OfficeCode=0;
        int CashBookYear=0;
        int CashBookMonth=0;
      
        String update_user=(String)session.getAttribute("UserId");
        try 
        {
            AccountUnitCode=Integer.parseInt(Account_unit_Code);
          //  OfficeCode=Integer.parseInt(Office_Code);
            CashBookYear=Integer.parseInt(CashBook_Year);
            CashBookMonth=Integer.parseInt(CashBook_Month);
          
            
            
        }catch(Exception e) 
        {
            System.out.println("Exception in Converting Integer:"+e);    
        }
        //This Two Variables for calculateting cashbookmonth and year
         
        String monthInWords="";
        if(CashBookMonth==1)
            monthInWords="January";
        else if(CashBookMonth==2)
            monthInWords="February";
        else if(CashBookMonth==3)
            monthInWords="March";
        else if(CashBookMonth==4)
            monthInWords="April";
        else if(CashBookMonth==5)
            monthInWords="May";
        else if(CashBookMonth==6)
            monthInWords="June";
        else if(CashBookMonth==7)
            monthInWords="July";
        else if(CashBookMonth==8)
            monthInWords="August";
        else if(CashBookMonth==9)
            monthInWords="September";
        else if(CashBookMonth==10)
            monthInWords="October";
        else if(CashBookMonth==11)
            monthInWords="November";
        else if(CashBookMonth==12)
            monthInWords="December";
            
            
         try 
         {
        	
         
                   File reportFile=null;
                   try
                   {
                           System.out.println("calling servlet11...");
                           reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/TrialBalance_Report.jasper"));
                          
                           if (!reportFile.exists())
                           throw new JRRuntimeException("File J not found. The report design must be compiled first.");
                               System.out.println("from22 ...");
                           JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
                           Map map=null;
                           map = new HashMap();
                           
                               map.put("from_cashbookyear",CashBookYear);
                               map.put("from_cashbookmonth",CashBookMonth);
                               
                               map.put("to_cashbookyear",CashBookYear);
                               map.put("to_cashbookmonth",CashBookMonth);
                               
                               map.put("accountingunitid",AccountUnitCode);
                               
                               map.put("from_monthvalue",monthInWords);
                               map.put("to_monthvalue",monthInWords);
                               
                                                          
                               JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map,connection);
                               
                               System.out.println("upto TB REPORT "+map);
                           String rtype="PDF";
                         //  String rtype= request.getParameter("txtoption");
                           System.out.println(rtype);
                           if (rtype.equalsIgnoreCase("HTML"))   
                           {
                                       response.setContentType("text/html");
                               
                                       response.setHeader ("Content-Disposition", "attachment;filename=\"Challan.html\"");
                                       PrintWriter out = response.getWriter();
                                       JRHtmlExporter exporter = new JRHtmlExporter();
                                       exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,  false);
                                       exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                                       exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
                                       exporter.exportReport();
                                       out.flush();
                                       out.close();
                           }
                           else if (rtype.equalsIgnoreCase("PDF"))   
                           {       System.out.println(rtype+"...inside PDF");
                                       byte buf[] = 
                                         JasperExportManager.exportReportToPdf(jasperPrint);
                                       response.setContentType("application/pdf");
                                       response.setContentLength(buf.length);
                                       response.setHeader ("Content-Disposition", "attachment;filename=\"Challan1.pdf\"");
                                       OutputStream out = response.getOutputStream();
                                       out.write(buf, 0, buf.length);
                                       out.close();
                           }
                       
                           else if(rtype.equalsIgnoreCase("EXCEL"))
                   		{
                        	   
                        	   
                        	   
                        	   try{
                       			response.setContentType("application/vnd.ms-excel");
                                   response.setHeader ("Content-Disposition", "attachment;filename=\"TBReport.xls\"");
                       		//String filename="c:/hello.xls" ;
                       		HSSFWorkbook hwb=new HSSFWorkbook();
                       		HSSFSheet sheet =  hwb.createSheet("new sheet");

                       		HSSFRow rowhead=   sheet.createRow((short)0);
                       		rowhead.createCell((short) 0).setCellValue("Account Head and Description");
                       		rowhead.createCell((short) 1).setCellValue("Opening Balance Debit");
                       		rowhead.createCell((short) 2).setCellValue("Opening Balance Credit");
                       		rowhead.createCell((short) 3).setCellValue("For the Month Debit");
                       		rowhead.createCell((short) 4).setCellValue("For the Month Credit");
                       		rowhead.createCell((short) 5).setCellValue("Closing Balance Debit");
                       		rowhead.createCell((short) 6).setCellValue("Closing Balance Credit");
                       		ServletOutputStream fileOut=null;
                            String ss="SELECT a.accounting_unit_id,\n" + 
                                    "  a.account_head_code,\n" + 
                                    "  a.debit_opening_balance,\n" + 
                                    "  a.credit_opening_balance,\n" + 
                                    "  a.current_month_debit,\n" + 
                                    "  a.current_month_credit,\n" + 
                                    "  a.debit_closing_balance,\n" + 
                                    "  a.credit_closing_balance,\n" + 
                                    "  b.account_head_desc," +
                                    " a.account_head_code ||' and  '|| B.Account_Head_Desc as headDesc,\n" + 
                                    "  d.accounting_unit_name,\n" + 
                                    "  TO_CHAR(x) AS x,\n" + 
                                    "  TO_CHAR(y) AS y\n" + 
                                    "FROM fas_trial_balance a,\n" + 
                                    "  com_mst_account_heads b,\n" + 
                                    "  fas_mst_acct_units d,\n" + 
                                    "  (SELECT SUM(current_month_debit) AS x,\n" + 
                                    "    SUM(current_month_credit)      AS y\n" + 
                                    "  FROM fas_trial_balance_cmp\n" + 
                                    "  WHERE cashbook_month BETWEEN ("+CashBookMonth+") AND ("+CashBookMonth+")\n" + 
                                    "  AND cashbook_year BETWEEN ("+CashBookYear+") AND ("+CashBookYear+")\n" + 
                                    "  AND accounting_unit_id=("+AccountUnitCode+")\n" + 
                                    "  )\n" + 
                                    "WHERE a.account_head_code=b.account_head_code\n" + 
                                    "AND a.accounting_unit_id =d.accounting_unit_id\n" + 
                                    "AND a.cashbook_month BETWEEN ("+CashBookMonth+") AND ("+CashBookMonth+")\n" + 
                                    "AND a.cashbook_year BETWEEN ("+CashBookYear+") AND ("+CashBookYear+")\n" + 
                                    "AND a.accounting_unit_id=("+AccountUnitCode+")\n" + 
                                    "ORDER BY a.account_head_code";
                           PreparedStatement ps2=connection.prepareStatement(ss);
                          rs2=ps2.executeQuery();
                          int j=1;
                           while(rs2.next())
                           {
                        	  
                       		HSSFRow row=   sheet.createRow((short)j);
                       	
                       		row.createCell((short) 0).setCellValue(rs2.getInt("account_head_code")+"  ("+rs2.getString("account_head_desc")+")");
                       		row.createCell((short) 1).setCellValue(rs2.getInt("debit_opening_balance"));
                       		row.createCell((short) 2).setCellValue(rs2.getInt("credit_opening_balance"));
                       		row.createCell((short) 3).setCellValue(rs2.getInt("current_month_debit"));
                       		row.createCell((short) 4).setCellValue(rs2.getInt("current_month_credit"));
                       		row.createCell((short) 5).setCellValue(rs2.getInt("debit_closing_balance"));
                       		row.createCell((short) 6).setCellValue(rs2.getInt("credit_closing_balance"));
                       		
                       		
                       		
                       		 kk++;
                       		 j++;
                       		
                       	 }
                           fileOut = response.getOutputStream();
                           hwb.write(fileOut);
                      		fileOut.close();
                       		} catch ( Exception ex ) {
                       		    System.out.println(ex);

                       		}

                   		}
                           
                          /* else if (rtype.equalsIgnoreCase("EXCEL"))
                           {
                        	  response.setContentType("application/vnd.ms-excel");
			                    response.setHeader ("Content-Disposition", "attachment;filename=\"Challan.xls\"");
			                    JRXlsExporter exporterXLS = new JRXlsExporter(); 
			                    exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint); 
			                 
			                    ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
			                    exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,xlsReport); 
			                    exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE); 
			                    exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE); 
			                    exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE); 
			                    exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE); 
			                    exporterXLS.exportReport(); 
			                    byte []bytes;
			                    bytes = xlsReport.toByteArray();
			                    ServletOutputStream ouputStream = response.getOutputStream();
			                    ouputStream.write(bytes, 0, bytes.length);
			                    ouputStream.flush();
			                    ouputStream.close();
                           }  */
                           else if (rtype.equalsIgnoreCase("TXT"))   
                           {
                           
                                   response.setContentType("text/plain");
                                   response.setHeader ("Content-Disposition", "attachment;filename=\"Challan.txt\"");
                                    
                               JRTextExporter exporter = new JRTextExporter();
                               exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                               ByteArrayOutputStream txtReport = new ByteArrayOutputStream();
                               exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,txtReport); 
                               exporter.setParameter(JRTextExporterParameter.CHARACTER_WIDTH, new Integer(200));
                               exporter.setParameter(JRTextExporterParameter.CHARACTER_HEIGHT, new Integer(50));
                               exporter.exportReport(); 
                               
                                   byte []bytes;
                                   bytes = txtReport.toByteArray();
                                   ServletOutputStream ouputStream = response.getOutputStream();
                                   ouputStream.write(bytes, 0, bytes.length);
                                   ouputStream.flush();
                                   ouputStream.close();
                           
                           }
                   
                   } 
                   catch (Exception ex) 
                   {
                   String connectMsg = "Could not create the report " + ex.getMessage();//+ " uu " +  ex.getLocalizedMessage();
                   System.out.println(connectMsg);
                   //sendMessage(response,"The Challan Report Creation failed","ok");
                    sendMessage(response,"Unable to display the PDF file","ok");
                   }
                  //////////////////---------------------------- End -----------------
                  System.out.println("here after PDF");
                  //sendMessage(response,"The Trial Balance done successfully","ok");  
                  System.out.println("after send message");
                 
                 
                 
             
         }catch(Exception e) {
             System.out.println("Exception in Main:"+e);
             try{connection.rollback();}catch(SQLException e1){System.out.println("catch:"+e1);}
             String msg="Trial Balance Has failed to Update";
              msg=msg+"<br><br>";
              sendMessage(response,msg,"ok");
              
         }
    }
    
     
    private void sendMessage(HttpServletResponse response,String msg,String bType)
    {
        try
        {
            String url="org/Library/jsps/Messenger.jsp?message=" + msg + "&button=" + bType;
            response.sendRedirect(url);          
        }
        catch(IOException e)
        {
        System.out.println("ERROR");
        }
    }  
 
    
}