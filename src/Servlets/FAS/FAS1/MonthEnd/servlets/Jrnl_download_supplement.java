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
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Servlet implementation class Journal_download developed by B.Sathya on 28/11/2014 *******
 */
public class Jrnl_download_supplement extends HttpServlet {
	 private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Jrnl_download_supplement() {
        super();
        // TODO Auto-generated constructor stub
    }
    public void doGet(HttpServletRequest request, 
            HttpServletResponse response) throws ServletException, IOException 
    {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~`  do get called   ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
  	
             /**
	       * Set Content Type 
	      */
	      PrintWriter out = response.getWriter();
	      response.setHeader("cache-control","no-cache");
	      String CONTENT_TYPE = "text/xml; charset=windows-1252";
	      response.setContentType(CONTENT_TYPE);
	      
	      
	      
	      /**
	       * Session Checking 
	      */
	      HttpSession session=request.getSession(false);
	      try
	      {	           
                    if(session==null)
                    {
                             System.out.println(request.getContextPath()+"/index.jsp");
                             response.sendRedirect(request.getContextPath()+"/index.jsp");
                             return;
                    }
                    System.out.println(session);
	               
	      }catch(Exception e)
	      {
                    System.out.println("Redirect Error :"+e);
	      }
	      
	     
	      /**
	       * Variables Declaration 
	      */		        
	      Connection con=null;
	      PreparedStatement ps2=null,ps1=null,ps22=null;        
	      ResultSet rs2=null,rs22=null;
	      String sql="";String sql21 = "";
	        	      
	      /**
	       * Database Connection 
	      */
	      try
	      {
                    ResourceBundle rs1=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
                    String ConnectionString="";
                    String strDriver=rs1.getString("Config.DATA_BASE_DRIVER");
                    String strdsn=rs1.getString("Config.DSN");
                    String strhostname=rs1.getString("Config.HOST_NAME");
                    String strportno=rs1.getString("Config.PORT_NUMBER");
                    String strsid=rs1.getString("Config.SID");
                    String strdbusername=rs1.getString("Config.USER_NAME");
                    String strdbpassword=rs1.getString("Config.PASSWORD");
                    ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection                    Class.forName(strDriver.trim());
                    con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
            }
            catch(Exception e)
            {
                	System.out.println("Exception in opening connection :"+e);
            }
        
       
            int count=0,AccUnitId=0,yesCount=0;
            String xml=null,cmd="",option="";          
      
            /** Get Employee ID */
            try{cmd=request.getParameter("command");}
            catch(Exception e){System.out.println(e);}
            
            try{option=request.getParameter("Option");}
            catch(Exception e){System.out.println(e);}
            
            try{AccUnitId=Integer.parseInt(request.getParameter("txtUnitId"));}
            catch(Exception e){System.out.println(e);}
            
            xml="<response>";
            if(cmd.equalsIgnoreCase("forJournalTBstatus"))
            {
            	int from_txtCB_Month=0,from_txtCB_Year=0;
            	 try{from_txtCB_Year=Integer.parseInt(request.getParameter("from_txtCB_Year"));}
                 catch(Exception e){System.out.println(e);}
            	 
            	 try{from_txtCB_Month=Integer.parseInt(request.getParameter("from_txtCB_Month"));}
                 catch(Exception e){System.out.println(e);}
                
                xml=xml+"<command>forJournalTBstatus</command>";
                /*sql21="SELECT accounting_unit_id, " +
                		"  accounting_unit_name " +
                		"FROM fas_mst_acct_units " +
                		"WHERE Accounting_Unit_Id NOT IN " +
                		"  (SELECT accounting_unit_id " +
                		"  FROM fas_trial_balance_status tbs " +
                		"  WHERE tbs.cashbook_month =  " +from_txtCB_Month +
                		"  AND tbs.cashbook_year    =  " +from_txtCB_Year +
                		"  AND tbs.tb_status        = 'Y' " +
                		"  ) " +
                		"ORDER BY accounting_unit_id";  */
                
                sql21 = "SELECT x.unitid, " +
                		"  Y.accounting_unit_name " +
                		"FROM " +
                		"  (SELECT DISTINCT M.Accounting_Unit_Id AS unitid " +
                		"  FROM Fas_Journal_Master M, " +
                		"    Fas_Journal_Transaction T " +
                		"  WHERE M.ACCOUNTING_UNIT_ID NOT IN " +
                		"    (SELECT accounting_unit_id " +
                		"    FROM FAS_TRIAL_BALANCE_STATUS_SJV tbs " +
                		"    WHERE Tbs.Cashbook_Month =  " + from_txtCB_Month +
                		"    AND tbs.cashbook_year    =  " + from_txtCB_Year +
                		"    AND tbs.tb_status        = 'Y' " +
                		"    ) " +
                		"  AND M.ACCOUNTING_UNIT_ID = T.ACCOUNTING_UNIT_ID " +
                		"  AND M.Cashbook_Year      = T.Cashbook_Year " +
                		"  AND M.Cashbook_Month     = T.Cashbook_Month " +
                		"  AND M.Voucher_No         = T.Voucher_No " +
                		"  AND M.Cashbook_Year      =  " + from_txtCB_Year +
                		"  AND M.Cashbook_Month     =  " + from_txtCB_Month +
                		"  )X " +
                		"INNER JOIN " +
                		"  (SELECT A.Accounting_Unit_Id unid, " +
                		"    A.Accounting_Unit_Name " +
                		"  FROM Fas_Mst_Acct_Units A " +
                		"  ) Y " +
                		"ON x.unitid = y.unid " +
                		"ORDER BY unitid";

               
                System.out.println(" SQL :: "+sql21);
                try
                {
                         ps22=con.prepareStatement(sql21);
                        // System.out.println(AccUnitId);
                         rs22=ps22.executeQuery();                                 
                         while(rs22.next()) 
                         {
                                 xml+= "<unit_id>"+ rs22.getInt("unitid") +"</unit_id>";	 
                                 
                                 xml+= "<unit_name><![CDATA[" + rs22.getString("ACCOUNTING_UNIT_NAME") + "]]></unit_name>";  				                		                 
                                 count++;
                         }					              
                         if(count==0)
                                 xml+="<flag>NoData</flag>";					           
                         else               
                                 xml+="<flag>success</flag>";
                                     
                }
                                
                catch(Exception e) 
                {
                         System.out.println("Exception in loadTransferUnit..."+e);
                         xml+="<flag>"+e.getMessage()+"</flag>";
                }                      
        }
            xml=xml+"</response>";
            System.out.println(xml);
            out.println(xml);
            out.close();
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("Journal supplement download in xls format");
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
         
         
        String CashBook_Year=request.getParameter("txtCB_Year");
        String CashBook_Month=request.getParameter("txtCB_Month");
        String txtUnitId=request.getParameter("txtUnitId");
        String supNo=request.getParameter("txtsupplement_no");
        
        
        
        int CashBookYear=0;
        int CashBookMonth=0,unitid=0,suppNo=0;
        String ss = "";
        String update_user=(String)session.getAttribute("UserId");
        try 
        {
          
            CashBookYear=Integer.parseInt(CashBook_Year);
            CashBookMonth=Integer.parseInt(CashBook_Month);
            unitid=Integer.parseInt(txtUnitId);
            suppNo=Integer.parseInt(supNo);
            
            
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
                            try{
                       			response.setContentType("application/vnd.ms-excel");
                                   response.setHeader ("Content-Disposition", "attachment;filename=\"JOURNAL supplement Download Data.xls\"");
                       		//String filename="c:/hello.xls" ;
                       		HSSFWorkbook hwb=new HSSFWorkbook();
                       		HSSFSheet sheet =  hwb.createSheet("new sheet");

                       		HSSFRow rowhead=   sheet.createRow((short)0);
                       		rowhead.createCell((short) 0).setCellValue("UnitId");
                       		rowhead.createCell((short) 1).setCellValue("OfficeId");
                       		rowhead.createCell((short) 2).setCellValue("CashBookYear");
                       		rowhead.createCell((short) 3).setCellValue("CashBookMonth");
                       		rowhead.createCell((short) 4).setCellValue("AccountHeadCode");
                       		rowhead.createCell((short) 5).setCellValue("VoucherNumber");
                       		rowhead.createCell((short) 6).setCellValue("VoucherDate");
                       		rowhead.createCell((short) 7).setCellValue("Amount");
                       		rowhead.createCell((short) 8).setCellValue("CRDR_Indicator");
                       		rowhead.createCell((short) 9).setCellValue("UpdatedByUserID");
                       		
                       		ServletOutputStream fileOut=null;
                           if (unitid==0)
                           {
                       		 ss="SELECT M.Accounting_Unit_Id AS Unit_Id , " +
                       		        "  M.ACCOUNTING_FOR_OFFICE_ID," +
                       				"  A.Accounting_Unit_Name, " +
                       				"  m.cashbook_year , " +
                       				"  M.Cashbook_Month , " +
                       				"  to_char(M.Voucher_Date, 'DD/MM/YYYY') as voucher_date, " +
                       				"  t.account_head_code , " +
                       				"  m.VOUCHER_NO AS voc_no , " +
                       				"  T.Amount , " +
                       				"  T.Cr_Dr_Indicator, " +
                       				"  M.UPDATED_BY_USER_ID, " +
                       				"  T.Sub_Ledger_Type_Code, " +
                       				"  (SELECT Sub_Ledger_Type_Desc " +
                       				"  FROM Com_Mst_Sl_Types " +
                       				"  WHERE Sub_Ledger_Type_Code=T.Sub_Ledger_Type_Code " +
                       				"  )AS Typedesc " +
                       				"FROM FAS_JOURNAL_MASTER m, " +
                       				"  FAS_JOURNAL_TRANSACTION t , " +
                       				"  Fas_Mst_Acct_Units A, " +
                       				"  Fas_Mst_Journal_Type B " +
                       				"WHERE m.accounting_unit_id    =t.accounting_unit_id " +
                       				"AND m.cashbook_year           = t.cashbook_year " +
                       				"AND m.cashbook_month          = t.cashbook_month " +
                       				"AND m.VOUCHER_NO              = t.VOUCHER_NO " +
                       				"AND m.JOURNAL_STATUS          ='L' " +
                       				"AND m.created_by_module       ='SJV' " +
                       				"AND m.supplement_no           = " + suppNo +
                       				"AND M.Accounting_Unit_Id      = A.Accounting_Unit_Id " +
                       				" AND M.Cashbook_Year           = " + CashBookYear +
                       				" AND m.cashbook_month          = " + CashBookMonth +
                       				" AND M.Journal_Type_Code       =B.Journal_Type_Code " +
                       				"AND m.accounting_unit_id NOT IN " +
                       				"  (SELECT accounting_unit_id " +
                       				"  FROM FAS_TRIAL_BALANCE_STATUS_SJV " +
                       				"  WHERE TB_status   ='Y' " +
                       				"  AND cashbook_year = " + CashBookYear +
                       				"  AND cashbook_month= " + CashBookMonth +
                       				"  ) " +
                       				"ORDER BY A.Accounting_Unit_Name , " +
                       				"  M.Cashbook_Year , " +
                       				"  M.Cashbook_Month , " +
                       				"  m.VOUCHER_NO";
                           }
                           else
                           {
                        	   ss = "SELECT M.Accounting_Unit_Id AS Unit_Id , " +
                          		        "  M.ACCOUNTING_FOR_OFFICE_ID," +
                           				"  A.Accounting_Unit_Name, " +
                           				"  m.cashbook_year , " +
                           				"  M.Cashbook_Month , " +
                           				"  to_char(M.Voucher_Date, 'DD/MM/YYYY') as voucher_date, " +
                           				"  t.account_head_code , " +
                           				"  m.VOUCHER_NO AS voc_no , " +
                           				"  T.Amount , " +
                           				"  T.Cr_Dr_Indicator, " +
                           				"  M.UPDATED_BY_USER_ID, " +
                           				"  T.Sub_Ledger_Type_Code, " +
                           				"  (SELECT Sub_Ledger_Type_Desc " +
                           				"  FROM Com_Mst_Sl_Types " +
                           				"  WHERE Sub_Ledger_Type_Code=T.Sub_Ledger_Type_Code " +
                           				"  )AS Typedesc " +
                           				"FROM FAS_JOURNAL_MASTER m, " +
                           				"  FAS_JOURNAL_TRANSACTION t , " +
                           				"  Fas_Mst_Acct_Units A, " +
                           				"  Fas_Mst_Journal_Type B " +
                           				"WHERE m.accounting_unit_id    =t.accounting_unit_id " +
                           				"AND m.cashbook_year           = t.cashbook_year " +
                           				"AND m.cashbook_month          = t.cashbook_month " +
                           				"AND m.VOUCHER_NO              = t.VOUCHER_NO " +
                           				"AND m.JOURNAL_STATUS          ='L' " +
                           				"AND m.created_by_module       ='SJV' " +
                           				"AND m.supplement_no           =1  " +
                           				"AND M.Accounting_Unit_Id      = A.Accounting_Unit_Id " +
                           				" AND M.Cashbook_Year           = " + CashBookYear +
                           				" AND m.cashbook_month          = " + CashBookMonth +
                           				" AND M.ACCOUNTING_UNIT_ID        = "+ unitid + 
                           				" AND M.Journal_Type_Code       =B.Journal_Type_Code " +
                           				"AND m.accounting_unit_id NOT IN " +
                           				"  (SELECT accounting_unit_id " +
                           				"  FROM FAS_TRIAL_BALANCE_STATUS_SJV " +
                           				"  WHERE TB_status   ='Y' " +
                           				"  AND cashbook_year = " + CashBookYear +
                           				"  AND cashbook_month= " + CashBookMonth +
                           				"  AND ACCOUNTING_UNIT_ID= "+ unitid + 
                           				"  ) " +
                           				"ORDER BY A.Accounting_Unit_Name , " +
                           				"  M.Cashbook_Year , " +
                           				"  M.Cashbook_Month , " +
                           				"  m.VOUCHER_NO";
                           }
                            System.out.println("ss::::"+ss);
                           PreparedStatement ps2=connection.prepareStatement(ss);
                          rs2=ps2.executeQuery();
                          int j=1;
                           while(rs2.next())
                           {
                        	  
                       		HSSFRow row=   sheet.createRow((short)j);
                       	
                       		row.createCell((short) 0).setCellValue(rs2.getInt("Unit_Id"));
                       		row.createCell((short) 1).setCellValue(rs2.getInt("ACCOUNTING_FOR_OFFICE_ID"));
                       		row.createCell((short) 2).setCellValue(rs2.getInt("cashbook_year"));
                       		row.createCell((short) 3).setCellValue(rs2.getInt("Cashbook_Month"));
                       		row.createCell((short) 4).setCellValue(rs2.getInt("account_head_code"));
                       		row.createCell((short) 5).setCellValue(rs2.getInt("voc_no"));
                       		row.createCell((short) 6).setCellValue(rs2.getString("voucher_date"));
                       		
                       		row.createCell((short) 7).setCellValue(rs2.getDouble("Amount"));
                       		row.createCell((short) 8).setCellValue(rs2.getString("Cr_Dr_Indicator"));
                       		row.createCell((short) 9).setCellValue(rs2.getString("UPDATED_BY_USER_ID"));
                       		
                       		
                       		j++;
                       	 }
                           fileOut = response.getOutputStream();
                           hwb.write(fileOut);
                      		fileOut.close();
                       		} catch ( Exception ex ) {
                       		    System.out.println(ex);

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
             String msg="Supplement Journal Record Download Failed";
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
