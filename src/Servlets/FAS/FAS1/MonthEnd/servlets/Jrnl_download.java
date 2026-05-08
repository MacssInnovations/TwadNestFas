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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class Jrnl_download extends HttpServlet {
	 private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Jrnl_download() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("Journal download in xls format");
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
         
         
        String CashBook_Year=request.getParameter("from_txtCB_Year");
        String CashBook_Month=request.getParameter("from_txtCB_Month");
        String txtUnitId=request.getParameter("txtUnitId");
        
        
        
        
        int CashBookYear=0;
        int CashBookMonth=0,unitid=0;
        String ss = "";
       String  Supplement_no="";
        String update_user=(String)session.getAttribute("UserId");
        try 
        {
          
            CashBookYear=Integer.parseInt(CashBook_Year);
            CashBookMonth=Integer.parseInt(CashBook_Month);
            unitid=Integer.parseInt(txtUnitId);
            
            
            
        }catch(Exception e) 
        {
            System.out.println("Exception in Converting Integer:"+e);    
        }
        //This Two Variables for calculateting cashbookmonth and year
         System.out.println("All unit "+unitid);
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
                	   String jrnltype=request.getParameter("jrnl");
            	       System.out.println("JournalType............."+jrnltype);
                	   
                	   if (jrnltype.equalsIgnoreCase("freeze")) {
                            try{
                       			response.setContentType("application/vnd.ms-excel");
                                   response.setHeader ("Content-Disposition", "attachment;filename=\"JOURNAL Download Data.xls\"");
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
                       				" FROM FAS_JOURNAL_MASTER m, " +
                       				"  FAS_JOURNAL_TRANSACTION t , " +
                       				"  Fas_Mst_Acct_Units A, " +
                       				"  Fas_Mst_Journal_Type B " +
                       				" WHERE m.accounting_unit_id    =t.accounting_unit_id " +
                       				" AND m.cashbook_year           = t.cashbook_year " +
                       				" AND m.cashbook_month          = t.cashbook_month " +
                       				" AND m.VOUCHER_NO              = t.VOUCHER_NO " +
                       				" AND m.JOURNAL_STATUS          ='L' " +
                       				" AND M.Accounting_Unit_Id      = A.Accounting_Unit_Id " +
                       				" AND M.Cashbook_Year           = " + CashBookYear +
                       				" AND m.cashbook_month          = " + CashBookMonth +
                       				" AND M.Journal_Type_Code       =B.Journal_Type_Code " +
                       				" AND m.accounting_unit_id NOT IN " +
                       				"  (SELECT accounting_unit_id " +
                       				"  FROM FAS_TRIAL_BALANCE_STATUS " +
                       				"  WHERE TB_status   ='Y' " +
                       				"  AND cashbook_year = " + CashBookYear +
                       				"  AND cashbook_month= " + CashBookMonth +
                       				"  ) " +
                       				" ORDER BY A.Accounting_Unit_Name , " +
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
                           				" FROM FAS_JOURNAL_MASTER m, " +
                           				"  FAS_JOURNAL_TRANSACTION t , " +
                           				"  Fas_Mst_Acct_Units A, " +
                           				"  Fas_Mst_Journal_Type B " +
                           				" WHERE m.accounting_unit_id    =t.accounting_unit_id " +
                           				" AND m.cashbook_year           = t.cashbook_year " +
                           				" AND m.cashbook_month          = t.cashbook_month " +
                           				" AND m.VOUCHER_NO              = t.VOUCHER_NO " +
                           				" AND m.JOURNAL_STATUS          ='L' " +
                           				" AND M.Accounting_Unit_Id      = A.Accounting_Unit_Id " +
                           				" AND M.Cashbook_Year           = " + CashBookYear +
                           				" AND m.cashbook_month          = " + CashBookMonth +
                           				" AND M.ACCOUNTING_UNIT_ID        = "+ unitid + 
                           				" AND M.Journal_Type_Code       =B.Journal_Type_Code " +
                           				" AND m.accounting_unit_id NOT IN " +
                           				"  (SELECT accounting_unit_id " +
                           				"  FROM FAS_TRIAL_BALANCE_STATUS " +
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
                        	  
                       		HSSFRow row=   sheet.createRow((int)j);
                       	
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
                	   else if (jrnltype.equalsIgnoreCase("JRNL")) {
                		                         	   
                		   String suppl_no=request.getParameter("txtSupplno");
                		   String type=request.getParameter("type");
                		   System.out.println("type"+type);
                        	   if (type.equalsIgnoreCase("Regular")) {
                        		   try{

                            	   
                         			response.setContentType("application/vnd.ms-excel");
                                     response.setHeader ("Content-Disposition", "attachment;filename=\"JOURNAL Data.xls\"");
                         		
                         		HSSFWorkbook hwb=new HSSFWorkbook();
                         		HSSFSheet sheet =  hwb.createSheet("new sheet");

                         		HSSFRow rowhead=   sheet.createRow((short)0);
                         	
                         		
                         		rowhead.createCell((short) 0).setCellValue("Unit_Id");
                         		rowhead.createCell((short) 1).setCellValue("Accounting_Unit_Name");
                         		rowhead.createCell((short) 2).setCellValue("cashbook_year");
                         		rowhead.createCell((short) 3).setCellValue("Cashbook_Month");                       	
                         		rowhead.createCell((short) 4).setCellValue("Voucher_Date");
                         		rowhead.createCell((short) 5).setCellValue("Account_Head_Code");
                         		rowhead.createCell((short) 6).setCellValue("voc_no");
                         		rowhead.createCell((short) 7).setCellValue("Amount");
                         		/*@NK on 18/05/2020 vasanthi mam asked to include cb_ref_type failed updation during payment cancellation*/
                         		rowhead.createCell((short) 8).setCellValue("CB_REF_TYPE");
                         		/*@NK on 18/05/2020*/
                         		rowhead.createCell((short) 9).setCellValue("PVR_NO");
                         		rowhead.createCell((short) 10).setCellValue("PVR_Date");
                         		rowhead.createCell((short) 11).setCellValue("Cr_Dr_Indicator");                         		
                         		rowhead.createCell((short) 12).setCellValue("sub_ledger_type_code");
                         		rowhead.createCell((short) 13).setCellValue("Typedesc");
                         		rowhead.createCell((short) 14).setCellValue("sub_ledger_code");
                         		rowhead.createCell((short) 15).setCellValue("Sltypecodedesc");
                         		rowhead.createCell((short) 16).setCellValue("Remarks");
                         		rowhead.createCell((short) 17).setCellValue("MULTIPLE_PVRS");
                         		rowhead.createCell((short) 18).setCellValue("MULTIPLE_PVR_DETAILS");
                         		rowhead.createCell((short) 19).setCellValue("MODE_OF_CREATION");
                         		rowhead.createCell((short) 20).setCellValue("OFFICE_ID");
                         		rowhead.createCell((short) 21).setCellValue("Created_By_Module");
                         		
                         		ServletOutputStream fileOut=null;
                             if (unitid==0)
                             {
                           	  
                           		  
                           		  ss="SELECT M.Accounting_Unit_Id AS Unit_Id ," +
                               		 		"  A.Accounting_Unit_Name, M.cashbook_year ,  M.Cashbook_Month ,  M.Voucher_Date,  T.Account_Head_Code ," +
                               		 		"  M.VOUCHER_NO AS voc_no ," +
                               		 		"  T.Amount ,M.CB_REF_TYPE,   T.CB_REF_NO AS PVR_NO,  T.CB_REF_DATE as PVR_Date, T.Cr_Dr_Indicator,  T.sub_ledger_type_code," +
                               		 		"  (SELECT Sub_Ledger_Type_Desc  FROM Com_Mst_Sl_Types  WHERE Sub_Ledger_Type_Code=T.Sub_Ledger_Type_Code  )AS Typedesc," +
                               		 		"  T.sub_ledger_code," +
                               		 		"  (SELECT Sl_Codename  FROM sl_type_code_name_view_td  WHERE Sl_Type=T.Sub_Ledger_Type_Code  AND Sl_Code  =T.Sub_Ledger_Code  limit 1  ) AS Sltypecodedesc," +
                               		 		"  M.Remarks ,T.MULTIPLE_PVRS,T.MULTIPLE_PVR_DETAILS,M.MODE_OF_CREATION,M.ACCOUNTING_FOR_OFFICE_ID,m.CREATED_BY_MODULE  " +
                               		 		" FROM Fas_Journal_Master M," +
                               		 		"  FAS_JOURNAL_TRANSACTION T ,  Fas_Mst_Acct_Units A,  Fas_Mst_Journal_Type B WHERE M.Accounting_Unit_Id =T.Accounting_Unit_Id " +
                               		 		" AND M.ACCOUNTING_FOR_OFFICE_ID=T.ACCOUNTING_FOR_OFFICE_ID " +
                               		 		" AND M.Cashbook_Year        = T .Cashbook_Year " +
                               		 		" AND M.Cashbook_Month       = T.Cashbook_Month " +
                               		 		" AND M.Voucher_No           = T.Voucher_No " +
                               		 		" AND M.Journal_Status       ='L' " +
                               		 		" AND M.Accounting_Unit_Id   = A.Accounting_Unit_Id " +
                               		 		" AND M.Cashbook_Year        =" + CashBookYear +
                               		 		" AND M.cashbook_month       =" + CashBookMonth +
                               		 		" AND M.Journal_Type_Code    =B.Journal_Type_Code " +
                               		 		" AND (M.SUPPLEMENT_NO    ='0' OR M.SUPPLEMENT_NO is null)" +
                               		 		//"AND T.Accounting_Unit_Id='33'" +
                               		 		"ORDER BY A.Accounting_Unit_Name ," +
                               		 		"  M.Cashbook_Year ," +
                               		 		"  M.Cashbook_Month ," +
                               		 		"  M.VOUCHER_NO";
     								
                         		 }
                             else
                             {
                           		  ss="SELECT M.Accounting_Unit_Id AS Unit_Id ," +
                              		 		"  A.Accounting_Unit_Name, M.cashbook_year ,  M.Cashbook_Month ,  M.Voucher_Date,  T.Account_Head_Code ," +
                              		 		"  M.VOUCHER_NO AS voc_no ," +
                              		 		"  T.Amount ,M.CB_REF_TYPE, T.CB_REF_NO AS PVR_NO,  T.CB_REF_DATE as PVR_Date, T.Cr_Dr_Indicator,  T.sub_ledger_type_code," +
                              		 		"  (SELECT Sub_Ledger_Type_Desc  FROM Com_Mst_Sl_Types  WHERE Sub_Ledger_Type_Code=T.Sub_Ledger_Type_Code  )AS Typedesc," +
                              		 		"  T.sub_ledger_code," +
                              		 		"  (SELECT Sl_Codename  FROM sl_type_code_name_view_td  WHERE Sl_Type=T.Sub_Ledger_Type_Code  AND Sl_Code  =T.Sub_Ledger_Code  limit 1    ) AS Sltypecodedesc," +
                              		 		"  M.Remarks ,T.MULTIPLE_PVRS,T.MULTIPLE_PVR_DETAILS,M.MODE_OF_CREATION,M.ACCOUNTING_FOR_OFFICE_ID,m.CREATED_BY_MODULE  " +
                              		 		" FROM Fas_Journal_Master M," +
                              		 		"  FAS_JOURNAL_TRANSACTION T ,  Fas_Mst_Acct_Units A,  Fas_Mst_Journal_Type B WHERE M.Accounting_Unit_Id =T.Accounting_Unit_Id " +
                              		 		" AND M.ACCOUNTING_FOR_OFFICE_ID=T.ACCOUNTING_FOR_OFFICE_ID " +
                              		 		" AND M.Cashbook_Year        = T .Cashbook_Year " +
                              		 		" AND M.Cashbook_Month       = T.Cashbook_Month " +
                              		 		" AND M.Voucher_No           = T.Voucher_No " +
                              		 		" AND M.Journal_Status       ='L' " +
                              		 		" AND M.Accounting_Unit_Id   = A.Accounting_Unit_Id " +
                              		 		" AND M.Cashbook_Year        =" + CashBookYear +
                              		 		" AND M.cashbook_month       =" + CashBookMonth +
                              		 		" AND M.Journal_Type_Code    =B.Journal_Type_Code " +
                              		 		" AND (M.SUPPLEMENT_NO    ='0' OR M.SUPPLEMENT_NO is null )" +
                              		 		" AND T.Accounting_Unit_Id="+ unitid +
                              		 		" ORDER BY A.Accounting_Unit_Name ," +
                              		 		"  M.Cashbook_Year ," +
                              		 		"  M.Cashbook_Month ," +
                              		 		"  M.VOUCHER_NO";
                             }
                              System.out.println("ss::::"+ss);
                             PreparedStatement ps2=connection.prepareStatement(ss);
                            rs2=ps2.executeQuery();
                            int j=1;
                            System.out.println("value of rows :::123"+j);
                             while(rs2.next())
                             {
                           	 // System.out.println("value of rows :::"+j);
                         		HSSFRow row=   sheet.createRow((int)j);
                         	
                         		row.createCell((short) 0).setCellValue(rs2.getInt("Unit_Id"));
                         		row.createCell((short) 1).setCellValue(rs2.getString("Accounting_Unit_Name"));
                         		row.createCell((short) 2).setCellValue(rs2.getInt("cashbook_year"));
                         		row.createCell((short) 3).setCellValue(rs2.getInt("Cashbook_Month"));                       	
                         		row.createCell((short) 4).setCellValue(rs2.getString("Voucher_Date"));
                         		row.createCell((short) 5).setCellValue(rs2.getInt("Account_Head_Code"));
                         		row.createCell((short) 6).setCellValue(rs2.getInt("voc_no"));
                         		row.createCell((short) 7).setCellValue(rs2.getDouble("Amount"));
                         		/*@NK on 18/05/2020 vasanthi mam asked to include cb_ref_type failed updation during payment cancellation*/
                         		row.createCell((short) 8).setCellValue(rs2.getString("CB_REF_TYPE"));
                         		/*@NK on 18/05/2020*/
                         		row.createCell((short) 9).setCellValue(rs2.getInt("PVR_NO"));
                         		row.createCell((short) 10).setCellValue(rs2.getString("PVR_Date"));                         		
                         		row.createCell((short) 11).setCellValue(rs2.getString("Cr_Dr_Indicator"));
                         		row.createCell((short) 12).setCellValue(rs2.getInt("sub_ledger_type_code"));
                         		row.createCell((short) 13).setCellValue(rs2.getString("Typedesc"));
                         		row.createCell((short) 14).setCellValue(rs2.getDouble("sub_ledger_code"));
                         		row.createCell((short) 15).setCellValue(rs2.getString("Sltypecodedesc"));
                         		row.createCell((short) 16).setCellValue(rs2.getString("Remarks"));
                         		row.createCell((short) 17).setCellValue(rs2.getString("MULTIPLE_PVRS"));
                         		row.createCell((short) 18).setCellValue(rs2.getString("MULTIPLE_PVR_DETAILS"));  
                         		
                         	
                           		String mode_of_creation=rs2.getString("MODE_OF_CREATION");
                         		String moc="";
                         		
                         		if (mode_of_creation.equals("M")) {
        							moc="Manual";
        						} 
                         		else if (mode_of_creation.equals("D"))
                         		{
                         			moc="DCB Journal";
                         		}
                         		else {
        							moc="Automatic";
        						}
                         		
                         		row.createCell((short) 19).setCellValue(moc);
                         		row.createCell((short) 20).setCellValue(rs2.getString("ACCOUNTING_FOR_OFFICE_ID")); 
                         		row.createCell((short) 21).setCellValue(rs2.getString("CREATED_BY_MODULE")); 
                         		
                         		j++;
                         	 }
                             System.out.println("value of rows :::"+j);
                             fileOut = response.getOutputStream();
                             hwb.write(fileOut);
                        		fileOut.close();
                         		} catch ( Exception ex ) {
                         		    System.out.println("Journal Exception :"+ex);

                         		}
                   		   
								
							} else {
								try{

	                            	   
                         			response.setContentType("application/vnd.ms-excel");
                                     response.setHeader ("Content-Disposition", "attachment;filename=\"JOURNAL Data.xls\"");
                         		
                         		HSSFWorkbook hwb=new HSSFWorkbook();
                         		HSSFSheet sheet =  hwb.createSheet("new sheet");

                         		HSSFRow rowhead=   sheet.createRow((short)0);
                         	
                         		
                         		rowhead.createCell((short) 0).setCellValue("Unit_Id");
                         		rowhead.createCell((short) 1).setCellValue("Accounting_Unit_Name");
                         		rowhead.createCell((short) 2).setCellValue("cashbook_year");
                         		rowhead.createCell((short) 3).setCellValue("Cashbook_Month");                       	
                         		rowhead.createCell((short) 4).setCellValue("Voucher_Date");
                         		rowhead.createCell((short) 5).setCellValue("Account_Head_Code");
                         		rowhead.createCell((short) 6).setCellValue("voc_no");
                         		rowhead.createCell((short) 7).setCellValue("Amount");                         		
                         		rowhead.createCell((short) 8).setCellValue("PVR_NO");
                         		rowhead.createCell((short) 9).setCellValue("PVR_Date");
                         		rowhead.createCell((short) 10).setCellValue("Cr_Dr_Indicator");
                         		rowhead.createCell((short) 11).setCellValue("sub_ledger_type_code");
                         		rowhead.createCell((short) 12).setCellValue("Typedesc");
                         		rowhead.createCell((short) 13).setCellValue("sub_ledger_code");
                         		rowhead.createCell((short) 14).setCellValue("Sltypecodedesc");
                         		rowhead.createCell((short) 15).setCellValue("Supplement_No");
                         		rowhead.createCell((short) 16).setCellValue("Remarks");
                         		rowhead.createCell((short) 17).setCellValue("MULTIPLE_PVRS");
                         		rowhead.createCell((short) 18).setCellValue("MULTIPLE_PVR_DETAILS");
                         		rowhead.createCell((short) 19).setCellValue("MODE_OF_CREATION");
                         		rowhead.createCell((short) 20).setCellValue("OFFICE_ID");
                         		rowhead.createCell((short) 21).setCellValue("Created_By_Module");
                         		
                         		
                         		
                         		ServletOutputStream fileOut=null;
                             if (unitid==0)
                             {
                            	 
     								// String suppl_no=request.getParameter("txtSupplno");
     								 System.out.println("txtSupplno..........."+suppl_no);
     								ss="SELECT M.Accounting_Unit_Id AS Unit_Id ," +
     	                      		 		"  A.Accounting_Unit_Name, M.cashbook_year ,  M.Cashbook_Month ,  M.Voucher_Date,  T.Account_Head_Code ," +
     	                      		 		"  M.VOUCHER_NO AS voc_no ," +
     	                      		 		"  T.Amount , T.CB_REF_NO AS PVR_NO,  T.CB_REF_DATE as PVR_Date, T.Cr_Dr_Indicator,  T.sub_ledger_type_code," +
     	                      		 		"  (SELECT Sub_Ledger_Type_Desc  FROM Com_Mst_Sl_Types  WHERE Sub_Ledger_Type_Code=T.Sub_Ledger_Type_Code  )AS Typedesc," +
     	                      		 		"  T.sub_ledger_code," +
     	                      		 		"  (SELECT Sl_Codename  FROM sl_type_code_name_view_td  WHERE Sl_Type=T.Sub_Ledger_Type_Code  AND Sl_Code  =T.Sub_Ledger_Code  limit 1   ) AS Sltypecodedesc,M.SUPPLEMENT_NO, " +
     	                      		 		"  M.Remarks ,T.MULTIPLE_PVRS,T.MULTIPLE_PVR_DETAILS ,M.MODE_OF_CREATION,m.ACCOUNTING_FOR_OFFICE_ID,m.CREATED_BY_MODULE " +
     	                      		 		" FROM Fas_Journal_Master M," +
     	                      		 		"  FAS_JOURNAL_TRANSACTION T ,  Fas_Mst_Acct_Units A,  Fas_Mst_Journal_Type B WHERE M.Accounting_Unit_Id =T.Accounting_Unit_Id " +
     	                      		 		" AND M.ACCOUNTING_FOR_OFFICE_ID=T.ACCOUNTING_FOR_OFFICE_ID " +
     	                      		 		" AND M.Cashbook_Year        = T .Cashbook_Year " +
     	                      		 		" AND M.Cashbook_Month       = T.Cashbook_Month " +
     	                      		 		" AND M.Voucher_No           = T.Voucher_No " +
     	                      		 		" AND M.Journal_Status       ='L' " +
     	                      		 		" AND M.Accounting_Unit_Id   = A.Accounting_Unit_Id " +
     	                      		 		" AND M.Cashbook_Year        =" + CashBookYear +
     	                      		 		" AND M.cashbook_month       =" + CashBookMonth +
     	                      		 		" AND M.Journal_Type_Code    =B.Journal_Type_Code " +
     	                      		 		" AND M.SUPPLEMENT_NO="+suppl_no +
     	                      		 		" ORDER BY A.Accounting_Unit_Name ," +
     	                      		 		"  M.Cashbook_Year ," +
     	                      		 		"  M.Cashbook_Month ," +
     	                      		 		"  M.VOUCHER_NO";

     							    
                           	                        		 
                         		 }
                             else
                             {
   								// String suppl_no=request.getParameter("txtSupplno");
     								 System.out.println("txtSupplno..........."+suppl_no);
   								
   								ss="SELECT M.Accounting_Unit_Id AS Unit_Id ," +
   		                 		 		"  A.Accounting_Unit_Name, M.cashbook_year ,  M.Cashbook_Month ,  M.Voucher_Date,  T.Account_Head_Code ," +
   		                 		 		"  M.VOUCHER_NO AS voc_no ," +
   		                 		 		"  T.Amount , T.CB_REF_NO AS PVR_NO,  T.CB_REF_DATE as PVR_Date ,  T.Cr_Dr_Indicator,  T.sub_ledger_type_code," +
   		                 		 		"  (SELECT Sub_Ledger_Type_Desc  FROM Com_Mst_Sl_Types  WHERE Sub_Ledger_Type_Code=T.Sub_Ledger_Type_Code  )AS Typedesc," +
   		                 		 		"  T.sub_ledger_code," +
   		                 		 		"  (SELECT Sl_Codename  FROM sl_type_code_name_view_td  WHERE Sl_Type=T.Sub_Ledger_Type_Code  AND Sl_Code  =T.Sub_Ledger_Code  limit 1  ) AS Sltypecodedesc, M.SUPPLEMENT_NO," +
   		                 		 		"  M.Remarks ,T.MULTIPLE_PVRS,T.MULTIPLE_PVR_DETAILS,M.MODE_OF_CREATION,m.ACCOUNTING_FOR_OFFICE_ID,m.CREATED_BY_MODULE " +
   		                 		 		" FROM Fas_Journal_Master M," +
   		                 		 		"  FAS_JOURNAL_TRANSACTION T ,  Fas_Mst_Acct_Units A,  Fas_Mst_Journal_Type B WHERE M.Accounting_Unit_Id =T.Accounting_Unit_Id " +
   		                 		 		" AND M.ACCOUNTING_FOR_OFFICE_ID=T.ACCOUNTING_FOR_OFFICE_ID " +
   		                 		 		" AND M.Cashbook_Year        = T .Cashbook_Year " +
   		                 		 		" AND M.Cashbook_Month       = T.Cashbook_Month " +
   		                 		 		" AND M.Voucher_No           = T.Voucher_No " +
   		                 		 		" AND M.Journal_Status       ='L' " +
   		                 		 		" AND M.Accounting_Unit_Id   = A.Accounting_Unit_Id " +
   		                 		 		" AND M.Cashbook_Year        =" + CashBookYear +
   		                 		 		" AND M.cashbook_month       =" + CashBookMonth +
   		                 		 		" AND M.Journal_Type_Code    =B.Journal_Type_Code " +
   		                 		 		" AND T.Accounting_Unit_Id="+ unitid +
   		                 		 		" AND M.SUPPLEMENT_NO="+suppl_no +
   		                 		 		" ORDER BY A.Accounting_Unit_Name ," +
   		                 		 		"  M.Cashbook_Year ," +
   		                 		 		"  M.Cashbook_Month ," +
   		                 		 		"  M.VOUCHER_NO";
   							
                          	   
                             }
                              System.out.println("ss::::"+ss);
                             PreparedStatement ps2=connection.prepareStatement(ss);
                            rs2=ps2.executeQuery();
                            int j=1;
                            System.out.println("value of rows :::123"+j);
                             while(rs2.next())
                             {
                           	 // System.out.println("value of rows :::"+j);
                         		HSSFRow row=   sheet.createRow((int)j);
                         	
                         		row.createCell((short) 0).setCellValue(rs2.getInt("Unit_Id"));
                         		row.createCell((short) 1).setCellValue(rs2.getString("Accounting_Unit_Name"));
                         		row.createCell((short) 2).setCellValue(rs2.getInt("cashbook_year"));
                         		row.createCell((short) 3).setCellValue(rs2.getInt("Cashbook_Month"));                       	
                         		row.createCell((short) 4).setCellValue(rs2.getString("Voucher_Date"));
                         		row.createCell((short) 5).setCellValue(rs2.getInt("Account_Head_Code"));
                         		row.createCell((short) 6).setCellValue(rs2.getInt("voc_no"));
                         		row.createCell((short) 7).setCellValue(rs2.getDouble("Amount"));
                         		row.createCell((short) 8).setCellValue(rs2.getInt("PVR_NO"));
                         		row.createCell((short) 9).setCellValue(rs2.getString("PVR_Date")); 
                         		row.createCell((short) 10).setCellValue(rs2.getString("Cr_Dr_Indicator"));
                         		row.createCell((short) 11).setCellValue(rs2.getInt("sub_ledger_type_code"));
                         		row.createCell((short) 12).setCellValue(rs2.getString("Typedesc"));
                         		row.createCell((short) 13).setCellValue(rs2.getDouble("sub_ledger_code"));
                         		row.createCell((short) 14).setCellValue(rs2.getString("Sltypecodedesc"));
                         		row.createCell((short) 15).setCellValue(rs2.getInt("Supplement_No"));
                         		row.createCell((short) 16).setCellValue(rs2.getString("Remarks"));
                         		row.createCell((short) 17).setCellValue(rs2.getString("MULTIPLE_PVRS"));
                         		row.createCell((short) 18).setCellValue(rs2.getString("MULTIPLE_PVR_DETAILS"));
                         		String mode_of_creation=rs2.getString("MODE_OF_CREATION");
                         		String moc=null;
                         		
                         		if (mode_of_creation.equals("M")) {
									moc="Manual";
								} 
                         		else if (mode_of_creation.equals("D"))
                         		{
                         			moc="DCB Journal";
                         		}
                         		else {
									moc="Automatic";
								}
                         		
                         		row.createCell((short) 19).setCellValue(moc);
                         		row.createCell((short) 20).setCellValue(rs2.getString("ACCOUNTING_FOR_OFFICE_ID"));
                         		row.createCell((short) 21).setCellValue(rs2.getString("CREATED_BY_MODULE")); 
                         		
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
                        	   
                        	   
						
					}else if (jrnltype.equalsIgnoreCase("PAYMENT")) {
						
						
						
                        try{
                   			response.setContentType("application/vnd.ms-excel");
                               response.setHeader ("Content-Disposition", "attachment;filename=\"PAYMENT DATA.xls\"");
                   		
                   		HSSFWorkbook hwb=new HSSFWorkbook();
                   		HSSFSheet sheet =  hwb.createSheet("new sheet");

                   		HSSFRow rowhead=   sheet.createRow((short)0);
                   		
                   		
                   		rowhead.createCell((short) 0).setCellValue("Unit_Id");
                   		rowhead.createCell((short) 1).setCellValue("Accounting_Unit_Name");
                   		rowhead.createCell((short) 2).setCellValue("Cashbook_year");
                   		rowhead.createCell((short) 3).setCellValue("Cashbook_Month");                       	
                   		rowhead.createCell((short) 4).setCellValue("Voc_no");
                   		rowhead.createCell((short) 5).setCellValue("Sl_No");
                   		rowhead.createCell((short) 6).setCellValue("Payment_Date");
                   		rowhead.createCell((short) 7).setCellValue("Account_Head_Code");
                   		rowhead.createCell((short) 8).setCellValue("Cr_Dr_Indicator");
                   		rowhead.createCell((short) 9).setCellValue("Account_No");
                   		rowhead.createCell((short) 10).setCellValue("Total_amount");
                   		rowhead.createCell((short) 11).setCellValue("Paid_To");
                   		rowhead.createCell((short) 12).setCellValue("Sub_ledger_type_code");
                   		rowhead.createCell((short) 13).setCellValue("Typedesc");
                   		rowhead.createCell((short) 14).setCellValue("Sub_ledger_code");
                   		rowhead.createCell((short) 15).setCellValue("Sltypecodedesc");
                   		rowhead.createCell((short) 16).setCellValue("Remarks");                   		
                   		rowhead.createCell((short) 17).setCellValue("Drhaa");
                   		rowhead.createCell((short) 18).setCellValue("Amount");
                   		rowhead.createCell((short) 19).setCellValue("Drindicator");
                   		rowhead.createCell((short) 20).setCellValue("MODE_OF_CREATION");
                   		rowhead.createCell((short) 21).setCellValue("OFFICE_ID");
                   		rowhead.createCell((short) 22).setCellValue("Cross_Ref_Type");
                   		rowhead.createCell((short) 23).setCellValue("Cross_Ref_No");
                   		rowhead.createCell((short) 24).setCellValue("Cross_Ref_Date");
                   		rowhead.createCell((short) 25).setCellValue("Cross_Ref_Sl_No");
                   		ServletOutputStream fileOut=null;
                       if (unitid==0)
                       {
                   		 /*ss="Select M.Accounting_Unit_Id As Unit_Id , A.Accounting_Unit_Name , M.cashbook_year ,  M.Cashbook_Month ,  M.voucher_no AS voc_no ,  M.Payment_Date,  M.Account_Head_Code," +
                   		 		"  M.Cr_Dr_Indicator, m.account_no ,  M.total_amount,   M.Paid_To,  T.sub_ledger_type_code," +
                   		 		" (SELECT Sub_Ledger_Type_Desc  From Com_Mst_Sl_Types  WHERE Sub_Ledger_Type_Code=T.Sub_Ledger_Type_Code   )As Typedesc," +
                   		 		"   T.sub_ledger_code," +
                   		 		"  (SELECT Sl_Codename   From  sl_type_code_name_view_td  Where Sl_Type=T.Sub_Ledger_Type_Code   AND Sl_Code  =T.Sub_Ledger_Code  And Rownum   =1  ) As Sltypecodedesc," +
                   		 		" M.Remarks,  T.Sl_No,  T.account_head_code as drhaa,  T.Amount ,  T.Cr_Dr_Indicator as drindicator From Fas_Payment_Master M,  Fas_Payment_Transaction T ,   Fas_Mst_Acct_Units A Where M.Accounting_For_Office_Id =T.Accounting_For_Office_Id " +
                   		 		" And M.Cashbook_Year        = T.Cashbook_Year " +
                   		 		" AND M.cashbook_month       = T.cashbook_month " +
                   		 		" And M.Voucher_No           = T.Voucher_No " +
                   		 		" AND M.payment_STATUS       ='L' " +
                   		 		" And M.Accounting_Unit_Id   = A.Accounting_Unit_Id " +
                   		 		" And M.Cashbook_Year        =" + CashBookYear +
                   		 		" And M.Cashbook_Month       =" + CashBookMonth +
                   		 		//" and M.account_head_code like '%07'" +
                   		 		" Order By M.Accounting_Unit_Id , " +
                   		 		"  M.Voucher_No";*/
                    	   ss="SELECT M.Accounting_Unit_Id AS Unit_Id ,  A.Accounting_Unit_Name ,  M.cashbook_year ,"
                    	   		+ "  M.Cashbook_Month ,  M.voucher_no AS voc_no ,  M.Payment_Date,  M.Account_Head_Code, M.Cr_Dr_Indicator, "
                    	   		+ "  m.account_no ,  M.total_amount,  M.Paid_To,  T.sub_ledger_type_code,  "
                    	   		+ "  (SELECT Sub_Ledger_Type_Desc   FROM Com_Mst_Sl_Types "
                    	   		+ "  WHERE Sub_Ledger_Type_Code=T.Sub_Ledger_Type_Code  )AS Typedesc,"
                    	   		+ " T.sub_ledger_code,  (SELECT Sl_Codename "
                    	   		+ "  FROM sl_type_code_name_view_td "
                    	   		+ "  WHERE Sl_Type=T.Sub_Ledger_Type_Code "
                    	   		+ "  AND Sl_Code  =T.Sub_Ledger_Code   limit 1    ) AS Sltypecodedesc, "
                    	   		+ " M.Remarks,   T.Sl_No,   T.account_head_code AS drhaa,   T.Amount ,  T.Cr_Dr_Indicator AS drindicator,M.MODE_OF_CREATION,m.Accounting_For_Office_Id, "
                    	   		+ " M.PAYABLE_VOUCHER_TYPE as Type,"
                    	   		+ " case" 
                    	   		+ "    WHEN T.PAYABLE_VOUCHER_NO is null" 
                    	   		+ "      THEN 0" 
                    	   		+ "      else T.PAYABLE_VOUCHER_NO" 
                    	   		+ "    end  as ref_no,"
                    	   		//+ " T.PAYABLE_VOUCHER_NO AS REF_NO,"
                    	   		+ " T.PAYABLE_VOUCHER_DATE as ref_date,"
                    	   		+ " T.PAYABLE_VOUCHER_SLNO as ref_sl_no "
                    	   		+ " FROM Fas_Payment_Master M, "
                    	   		+ "  Fas_Payment_Transaction T ,  Fas_Mst_Acct_Units A Where"
                    	   		+ " M.Accounting_For_Office_Id =T.Accounting_For_Office_Id "
                    	   		+ " and M.ACCOUNTING_UNIT_ID =T.ACCOUNTING_UNIT_ID "
                    	   		+ " AND M.Cashbook_Year              = T.Cashbook_Year "
                    	   		+ " AND M.cashbook_month             = T.cashbook_month "
                    	   		+ " AND M.Voucher_No                 = T.Voucher_No "
                    	   		+ " AND M.payment_STATUS             ='L'"
                    	   		+ " And M.Accounting_Unit_Id         =  A.Accounting_Unit_Id "
                    	   		+ " And T.Accounting_Unit_Id         = A.Accounting_Unit_Id "
                    	   		//+ " and T.PAYABLE_VOUCHER_NO        !=0 " commanded on 25-11-19.Vasanthi mam asked to remove the condition
                    	   		+ " And M.Cashbook_Year              =" + CashBookYear
                    	   		+ " AND M.Cashbook_Month             =" + CashBookMonth 
                    	   		+ " Order By M.Accounting_Unit_Id ,M.Voucher_No,t.sl_no";
                    	   
                    	   
                   		 }
                       else
                       {
                     	/*  ss="Select M.Accounting_Unit_Id As Unit_Id , A.Accounting_Unit_Name , M.cashbook_year ,  M.Cashbook_Month ,  M.voucher_no AS voc_no ,  M.Payment_Date,  M.Account_Head_Code," +
             		 		"  M.Cr_Dr_Indicator, m.account_no ,  M.total_amount,   M.Paid_To,  T.sub_ledger_type_code," +
             		 		" (SELECT Sub_Ledger_Type_Desc  From Com_Mst_Sl_Types  WHERE Sub_Ledger_Type_Code=T.Sub_Ledger_Type_Code   )As Typedesc," +
             		 		"   T.sub_ledger_code," +
             		 		"  (SELECT Sl_Codename   From  sl_type_code_name_view_td  Where Sl_Type=T.Sub_Ledger_Type_Code   AND Sl_Code  =T.Sub_Ledger_Code  And Rownum   =1  ) As Sltypecodedesc," +
             		 		" M.Remarks,  T.Sl_No,  T.account_head_code as drhaa,  T.Amount ,  T.Cr_Dr_Indicator as drindicator From Fas_Payment_Master M,  Fas_Payment_Transaction T ,   Fas_Mst_Acct_Units A Where M.Accounting_For_Office_Id =T.Accounting_For_Office_Id " +
             		 		" And M.Cashbook_Year        = T.Cashbook_Year " +
             		 		" AND M.cashbook_month       = T.cashbook_month " +
             		 		" And M.Voucher_No           = T.Voucher_No " +
             		 		" AND M.payment_STATUS       ='L' " +
             		 		" And M.Accounting_Unit_Id   = A.Accounting_Unit_Id " +
             		 		" And M.Cashbook_Year        =" + CashBookYear +
             		 		" And M.Cashbook_Month       =" + CashBookMonth +
             		 		//" and M.account_head_code like '%07'" +
             		 		" and t.accounting_unit_id="+ unitid +
             		 		" Order By M.Accounting_Unit_Id , " +
             		 		"  M.Voucher_No";
                     	  */
                     	  
                     	 ss="SELECT M.Accounting_Unit_Id AS Unit_Id ,  A.Accounting_Unit_Name ,  M.cashbook_year ,"
                     	   		+ "  M.Cashbook_Month ,  M.voucher_no AS voc_no ,  M.Payment_Date,  M.Account_Head_Code, M.Cr_Dr_Indicator, "
                     	   		+ "  m.account_no ,  M.total_amount,  M.Paid_To,  T.sub_ledger_type_code,  "
                     	   		+ "  (SELECT Sub_Ledger_Type_Desc   FROM Com_Mst_Sl_Types "
                     	   		+ "  WHERE Sub_Ledger_Type_Code=T.Sub_Ledger_Type_Code  )AS Typedesc,"
                     	   		+ " T.sub_ledger_code,  (SELECT Sl_Codename "
                     	   		+ "  FROM sl_type_code_name_view_td "
                     	   		+ "  WHERE Sl_Type=T.Sub_Ledger_Type_Code "
                     	   		+ "  AND Sl_Code  =T.Sub_Ledger_Code   limit 1     ) AS Sltypecodedesc, "
                     	   		+ " M.Remarks,   T.Sl_No,   T.account_head_code AS drhaa,   T.Amount ,  T.Cr_Dr_Indicator AS drindicator,M.MODE_OF_CREATION,m.Accounting_For_Office_Id, "
                     	   		+ " M.PAYABLE_VOUCHER_TYPE as Type,"
                     	   		+ " case" 
                     	   		+ "    WHEN T.PAYABLE_VOUCHER_NO is null" 
                     	   		+ "      THEN 0" 
                     	   		+ "      else T.PAYABLE_VOUCHER_NO" 
                     	   		+ "    end  as ref_no,"                     	   		
                     	   		//+ " T.PAYABLE_VOUCHER_NO AS REF_NO,"
                     	   		+ " T.PAYABLE_VOUCHER_DATE as ref_date, "
                     	   	    + " T.PAYABLE_VOUCHER_SLNO as ref_sl_no "
                     	   		+ " FROM Fas_Payment_Master M, "
                     	   		+ "  Fas_Payment_Transaction T ,  Fas_Mst_Acct_Units A Where"
                     	   		+ " M.Accounting_For_Office_Id =T.Accounting_For_Office_Id "
                     	   		+ " and M.ACCOUNTING_UNIT_ID =T.ACCOUNTING_UNIT_ID "
                     	   		+ " AND M.Cashbook_Year              = T.Cashbook_Year "
                     	   		+ " AND M.cashbook_month             = T.cashbook_month "
                     	   		+ " AND M.Voucher_No                 = T.Voucher_No "
                     	   		+ " AND M.payment_STATUS             ='L'"
                     	   		+ " And M.Accounting_Unit_Id         = A.Accounting_Unit_Id "
                     	   		+ " And T.Accounting_Unit_Id         = A.Accounting_Unit_Id "
                     	   		//+ " and T.PAYABLE_VOUCHER_NO        !=0 "    commanded on 25-11-19.Vasanthi mam asked to remove the condition
                     	   		+ " And M.Cashbook_Year              ="+ CashBookYear 
                     	   		+ " AND M.Cashbook_Month             ="+ CashBookMonth 
                     	      	+"  and t.accounting_unit_id="+ unitid 
                     	   		+ " Order By M.Accounting_Unit_Id ,M.Voucher_No,t.sl_no";
                     	   
                     	  
                     	  
                       }
                        System.out.println("ss::::"+ss);
                       PreparedStatement ps2=connection.prepareStatement(ss);
                      rs2=ps2.executeQuery();
                      int j=1;
                       while(rs2.next())
                       {
                    	  
                   		HSSFRow row=   sheet.createRow((int)j);
                   	
                   		row.createCell((short) 0).setCellValue(rs2.getInt("Unit_Id"));
                   		row.createCell((short) 1).setCellValue(rs2.getString("Accounting_Unit_Name"));
                   		row.createCell((short) 2).setCellValue(rs2.getInt("cashbook_year"));
                   		row.createCell((short) 3).setCellValue(rs2.getInt("Cashbook_Month"));                       	
                   		row.createCell((short) 4).setCellValue(rs2.getInt("voc_no"));
                   		
                   	 String currentDate =rs2.getString("Payment_Date");
                  	  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");                   
                     SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");                                      
                     Date f = sdf1.parse(currentDate);                      
                     String     tdate = sdf.format(f);                   		
                  	  System.out.println("Tdate  "+tdate);
                  	  
                  	  
                  	
                 
                 
                        row.createCell((short) 5).setCellValue(rs2.getInt("Sl_No"));
                   		row.createCell((short) 6).setCellValue(tdate);
                   		row.createCell((short) 7).setCellValue(rs2.getInt("Account_Head_Code"));
                   		row.createCell((short) 8).setCellValue(rs2.getString("Cr_Dr_Indicator"));
                   		row.createCell((short) 9).setCellValue(rs2.getDouble("Account_No"));
                   		row.createCell((short) 10).setCellValue(rs2.getDouble("Total_amount"));
                   		row.createCell((short) 11).setCellValue(rs2.getString("Paid_To"));
                   		row.createCell((short) 12).setCellValue(rs2.getInt("sub_ledger_type_code"));
                   		row.createCell((short) 13).setCellValue(rs2.getString("Typedesc"));
                   		row.createCell((short) 14).setCellValue(rs2.getInt("sub_ledger_code"));
                   		row.createCell((short) 15).setCellValue(rs2.getString("Sltypecodedesc"));
                   		row.createCell((short) 16).setCellValue(rs2.getString("Remarks"));                   		                      		
                   		
                   		row.createCell((short) 17).setCellValue(rs2.getInt("Drhaa"));
                   		row.createCell((short) 18).setCellValue(rs2.getDouble("Amount"));
                   		row.createCell((short) 19).setCellValue(rs2.getString("drindicator"));
                   		
                   		String mode_of_creation=rs2.getString("MODE_OF_CREATION");
                 		String moc="";
                 		
                 		if (mode_of_creation.equals("M")) {
							moc="Manual";
						} 
                 		else if (mode_of_creation.equals("D"))
                 		{
                 			moc="DCB Journal";
                 		}
                 		
                 		else {
							moc="Automatic";
						}
                 		
                 		row.createCell((short) 20).setCellValue(moc);
                 		row.createCell((short) 21).setCellValue(rs2.getString("Accounting_For_Office_Id"));
                 		row.createCell((short) 22).setCellValue(rs2.getString("Type"));
                 		row.createCell((short) 23).setCellValue(rs2.getString("REF_NO"));
                 		
                 		
                 		String refdate=null;
                 		if(rs2.getString("ref_date")!=null)
                 		{
                 		currentDate =rs2.getString("ref_date");
                      	sdf = new SimpleDateFormat("dd/MM/yyyy");                   
                      	sdf1 = new SimpleDateFormat("yyyy-MM-dd");                                      
                      	f = sdf1.parse(currentDate);                      
                      	refdate = sdf.format(f);      
                      	System.out.println("refdate  "+refdate);
                 		}
                 		else
                 		{
                 		refdate = null;
                 		}
                 		
                 		
                 		row.createCell((short) 24).setCellValue(refdate);
                 		row.createCell((short) 25).setCellValue(rs2.getInt("ref_sl_no")); 
                   		
                   		j++;
                   	 }
                       fileOut = response.getOutputStream();
                       hwb.write(fileOut);
                  		fileOut.close();
                   		} catch ( Exception ex ) {
                   		    System.out.println(ex);

                   		}
						
					}
          else if (jrnltype.equalsIgnoreCase("RECEIPT")) {
						
						
						
                        try{
                   			response.setContentType("application/vnd.ms-excel");
                               response.setHeader ("Content-Disposition", "attachment;filename=\"RECEIPT DATA.xls\"");
                   		
                   		HSSFWorkbook hwb=new HSSFWorkbook();
                   		HSSFSheet sheet =  hwb.createSheet("new sheet");

                   		HSSFRow rowhead=   sheet.createRow((short)0);
                   		
                   		
                   		rowhead.createCell((short) 0).setCellValue("Unit_Id");
                 		rowhead.createCell((short) 1).setCellValue("Accounting_Unit_Name");
                 		rowhead.createCell((short) 2).setCellValue("cashbook_year");
                 		rowhead.createCell((short) 3).setCellValue("Cashbook_Month");  
                 		rowhead.createCell((short) 4).setCellValue("Receipt_No");
                 		rowhead.createCell((short) 5).setCellValue("Receipt_Date");
                 		rowhead.createCell((short) 6).setCellValue("Account_Head_Code");
                 		/*@NK on 18/05/2020 vasanthi mam asked to include cb_ref_type failed updation during payment cancellation*/
                 		rowhead.createCell((short) 7).setCellValue("CB_REF_TYPE");
                 		/*@NK on 18/05/2020*/
                 		rowhead.createCell((short) 8).setCellValue("PVR_NO");
                 		rowhead.createCell((short) 9).setCellValue("PVR_Date");
                 		rowhead.createCell((short) 10).setCellValue("Cr_Dr_Indicator");                 		
                 		rowhead.createCell((short) 11).setCellValue("Account_No");
                 		rowhead.createCell((short) 12).setCellValue("Total_amount");
                 		rowhead.createCell((short) 13).setCellValue("Received_From");
                 		rowhead.createCell((short) 14).setCellValue("sub_ledger_type_code");
                 		rowhead.createCell((short) 15).setCellValue("Typedesc");
                 		rowhead.createCell((short) 16).setCellValue("sub_ledger_code");
                 		rowhead.createCell((short) 17).setCellValue("Sltypecodedesc");
                 		rowhead.createCell((short) 18).setCellValue("Remarks");
                   		rowhead.createCell((short) 19).setCellValue("Sl_No");
                   		rowhead.createCell((short) 20).setCellValue("Crhoa");  
                   		rowhead.createCell((short) 21).setCellValue("Amount");
                   		rowhead.createCell((short) 22).setCellValue("Crindicator");
                   		rowhead.createCell((short) 23).setCellValue("Mode_Of_Creation");
                   		rowhead.createCell((short) 24).setCellValue("Receipt Type ");
                   		rowhead.createCell((short) 25).setCellValue("Office id ");
                   		ServletOutputStream fileOut=null;
                       if (unitid==0)
                       {
                    	   ss="SELECT M.Accounting_Unit_Id AS Unit_Id , A.Accounting_Unit_Name,  M.cashbook_year ,"
                    	   		+ " M.CASHBOOK_MONTH , M.RECEIPT_NO AS rec_no , M.RECEIPT_DATE, "
                    	   		+ "  M.ACCOUNT_HEAD_CODE ,M.cb_ref_type, T.CB_REF_NO AS PVR_NO,  T.CB_REF_DATE as PVR_Date   ,  M.CR_DR_INDICATOR,   m.account_no, M.total_amount ,    M.received_from,   T.sub_ledger_type_code,"
                    	   		+ "  (SELECT Sub_Ledger_Type_Desc  FROM Com_Mst_Sl_Types   WHERE Sub_Ledger_Type_Code=T.Sub_Ledger_Type_Code  )AS Typedesc, "
                    	   		+ "  T.sub_ledger_code,   "
                    	   		+ " (SELECT Sl_Codename   FROM sl_type_code_name_view_td   WHERE Sl_Type=T.Sub_Ledger_Type_Code   AND Sl_Code  =T.Sub_Ledger_Code   limit 1     ) AS SLTYPECODEDESC, "
                    	   		+ "  M.REMARKS,   T.SL_NO,  T.ACCOUNT_HEAD_CODE AS CRHAA ,  T.AMOUNT, T.Cr_Dr_Indicator   AS crindicator ,M.MODE_OF_CREATION ,M.SUB_LEDGER_TYPE_CODE as rectype,M.ACCOUNTING_FOR_OFFICE_ID  "
                    	   		+ " FROM FAS_RECEIPT_MASTER M,"
                    	   		+ "  FAS_RECEIPT_TRANSACTION t  ,"
                    	   		+ "  FAS_MST_ACCT_UNITS A "
                    	   		+ " WHERE M.Accounting_Unit_Id =T.Accounting_Unit_Id "
                    	   		+ " AND M.ACCOUNTING_FOR_OFFICE_ID=T.ACCOUNTING_FOR_OFFICE_ID " 
                    	   		+ " AND M.Cashbook_Year        = T .Cashbook_Year "
                    	   		+ " AND M.CASHBOOK_MONTH       = T.CASHBOOK_MONTH "
                    	   		+ " AND M.RECEIPT_NO          = T.RECEIPT_NO "
                    	   		+ " AND M.RECEIPT_STATUS       ='L' "
                    	   		+ " AND M.Accounting_Unit_Id   = A.Accounting_Unit_Id "
                    	   		+ " AND M.Cashbook_Year        ="+ CashBookYear
                    	   		+ " AND M.CASHBOOK_MONTH       ="+ CashBookMonth
                    	   		+ " ORDER BY A.Accounting_Unit_Name ,  M.Cashbook_Year ,  M.CASHBOOK_MONTH ,  M.RECEIPT_NO";
                    	   
                    	   
                   		 }
                       else
                       {
                     
                     	  
                     	 ss= "SELECT M.Accounting_Unit_Id AS Unit_Id , A.Accounting_Unit_Name,  M.cashbook_year ,"
                    	   		+ " M.CASHBOOK_MONTH ,  M.RECEIPT_NO AS rec_no , M.RECEIPT_DATE, "
                    	   		+ "  M.ACCOUNT_HEAD_CODE ,M.cb_ref_type,T.CB_REF_NO AS PVR_NO,  T.CB_REF_DATE as PVR_Date   ,   M.CR_DR_INDICATOR,   m.account_no,   M.total_amount, T.CB_REF_NO AS PVR_NO,  T.CB_REF_DATE as PVR_Date ,   M.received_from,   T.sub_ledger_type_code,"
                    	   		+ "  (SELECT Sub_Ledger_Type_Desc  FROM Com_Mst_Sl_Types   WHERE Sub_Ledger_Type_Code=T.Sub_Ledger_Type_Code  )AS Typedesc, "
                    	   		+ "  T.sub_ledger_code,   "
                    	   		+ " (SELECT Sl_Codename   FROM sl_type_code_name_view_td   WHERE Sl_Type=T.Sub_Ledger_Type_Code   AND Sl_Code  =T.Sub_Ledger_Code  limit 1     ) AS SLTYPECODEDESC, "
                    	   		+ "  M.REMARKS,   T.SL_NO,  T.ACCOUNT_HEAD_CODE AS CRHAA , T.AMOUNT,  T.Cr_Dr_Indicator   AS crindicator , M.MODE_OF_CREATION ,M.SUB_LEDGER_TYPE_CODE as rectype,M.ACCOUNTING_FOR_OFFICE_ID  "
                    	   		+ " FROM FAS_RECEIPT_MASTER M,"
                    	   		+ "  FAS_RECEIPT_TRANSACTION t  ,"
                    	   		+ "  FAS_MST_ACCT_UNITS A "
                    	   		+ " WHERE M.Accounting_Unit_Id =T.Accounting_Unit_Id "
                    	   		+ " AND M.ACCOUNTING_FOR_OFFICE_ID=T.ACCOUNTING_FOR_OFFICE_ID " 
                    	   		+ " AND M.Cashbook_Year        = T .Cashbook_Year "
                    	   		+ " AND M.CASHBOOK_MONTH       = T.CASHBOOK_MONTH "
                    	   		+ " AND M.RECEIPT_NO          = T.RECEIPT_NO "
                    	   		+ " AND M.RECEIPT_STATUS       ='L' "
                    	   		+ " AND M.Accounting_Unit_Id   = A.Accounting_Unit_Id "
                    	   		+ " AND M.Cashbook_Year        ="+ CashBookYear
                    	   		+ " AND M.CASHBOOK_MONTH       ="+ CashBookMonth
                    	   		+ "  and t.accounting_unit_id="+ unitid 
                    	   		+ " ORDER BY A.Accounting_Unit_Name ,  M.Cashbook_Year ,  M.CASHBOOK_MONTH ,  M.RECEIPT_NO";
                    	   
                     	      
                     	   		
                     	   
                     	  
                     	  
                       }
                        System.out.println("ss::::"+ss);
                       PreparedStatement ps2=connection.prepareStatement(ss);
                      rs2=ps2.executeQuery();
                      int j=1;
                       while(rs2.next())
                       {
                    	   String rec_date=null;
                   		HSSFRow row=   sheet.createRow((int)j);
                   	
                   		
                   		row.createCell((short) 0).setCellValue(rs2.getInt("Unit_Id"));
                   		row.createCell((short) 1).setCellValue(rs2.getString("Accounting_Unit_Name"));
                   		row.createCell((short) 2).setCellValue(rs2.getInt("cashbook_year"));
                   		row.createCell((short) 3).setCellValue(rs2.getInt("Cashbook_Month"));
                   		row.createCell((short) 4).setCellValue(rs2.getInt("rec_no"));                    		

                   	  String currentDate =rs2.getString("RECEIPT_DATE");
                   	  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");                   
                      SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");                                      
                      Date f = sdf1.parse(currentDate);                      
                      String     tdate = sdf.format(f);                   		
                   	  //System.out.println("Tdate  "+tdate);
       
                   		
                   		row.createCell((short) 5).setCellValue(tdate);
                   		row.createCell((short) 6).setCellValue(rs2.getInt("Account_Head_Code"));
                   		/*@NK on 18/05/2020 vasanthi mam asked to include cb_ref_type failed updation during payment cancellation*/
                   		row.createCell((short) 7).setCellValue(rs2.getString("cb_ref_type"));
                   		/*@NK on 18/05/2020 vasanthi mam asked to include cb_ref_type failed updation during payment cancellation*/
                   		row.createCell((short) 8).setCellValue(rs2.getInt("PVR_NO"));
                 		row.createCell((short) 9).setCellValue(rs2.getString("PVR_Date")); 
                   		row.createCell((short) 10).setCellValue(rs2.getString("Cr_Dr_Indicator")); 
                   		row.createCell((short) 11).setCellValue(rs2.getDouble("Account_No"));
                   		row.createCell((short) 12).setCellValue(rs2.getDouble("Total_amount"));
                   		row.createCell((short) 13).setCellValue(rs2.getString("received_from"));
                   		row.createCell((short) 14).setCellValue(rs2.getInt("sub_ledger_type_code"));
                   		row.createCell((short) 15).setCellValue(rs2.getString("Typedesc"));
                   		row.createCell((short) 16).setCellValue(rs2.getInt("sub_ledger_code"));
                   		row.createCell((short) 17).setCellValue(rs2.getString("SLTYPECODEDESC"));
                   		row.createCell((short) 18).setCellValue(rs2.getString("Remarks"));
                   		row.createCell((short) 19).setCellValue(rs2.getInt("Sl_No"));
                   		row.createCell((short) 20).setCellValue(rs2.getInt("CRHAA"));                   		
                   		row.createCell((short) 21).setCellValue(rs2.getDouble("Amount"));
                   		row.createCell((short) 22).setCellValue(rs2.getString("crindicator"));
                   		String mode_of_creation=rs2.getString("MODE_OF_CREATION");
                 		String moc=null;
                 		String receipt=rs2.getString("rectype"); 
                     	String rec_type="";
                     	
                 		if (mode_of_creation.equals("M")) {
							moc="Manual";
						} 
                 		else {
							moc="Automatic";
						}
                 		if (receipt.equals("14")) {  
                     		if (mode_of_creation.equals("S")) { //DCB Journal in reclassify option y is selected then S flag updated so this is also Manual 
                     				                                      
    							moc="Manual";
    						} 
                 		}
                 		
                 		row.createCell((short) 23).setCellValue(moc);
                 	
                 	if (receipt.equals("14")) {                 		
                 		rec_type="DCB";
						
					} else {
						
						rec_type=" ";
					}
                 	row.createCell((short) 24).setCellValue(rec_type);
                 	row.createCell((short) 25).setCellValue(rs2.getString("ACCOUNTING_FOR_OFFICE_ID"));
                   		j++;
                   	 }
                       fileOut = response.getOutputStream();
                       hwb.write(fileOut);
                  		fileOut.close();
                   		} catch ( Exception ex ) {
                   		    System.out.println(ex);

                   		}					
												
					}
          else if (jrnltype.equalsIgnoreCase("FundTransfer")) {
				
				
				
              try{
         			response.setContentType("application/vnd.ms-excel");
                     response.setHeader ("Content-Disposition", "attachment;filename=\"FUND TRANSFER BY UNIT DATA.xls\"");
         		
         		HSSFWorkbook hwb=new HSSFWorkbook();
         		HSSFSheet sheet =  hwb.createSheet("new sheet");

         		HSSFRow rowhead=   sheet.createRow((short)0);
         		                                                                                                                                                                                                                                                    

         		
         		rowhead.createCell((short) 0).setCellValue("ACCOUNTING_UNIT_ID");
	       		rowhead.createCell((short) 1).setCellValue("ACCOUNTING_UNIT_NAME");
	       		rowhead.createCell((short) 2).setCellValue("CASHBOOK_YEAR");
	       		rowhead.createCell((short) 3).setCellValue("CASHBOOK_MONTH");
	       		rowhead.createCell((short) 4).setCellValue("Doc_No");
         		rowhead.createCell((short) 5).setCellValue("Doc_Date");  
	       		rowhead.createCell((short) 6).setCellValue("REMTYPE");
	       		rowhead.createCell((short) 7).setCellValue("REMITTANCE_TYPE");
	       		rowhead.createCell((short) 8).setCellValue("TOTAL_AMOUNT");
	       		rowhead.createCell((short) 9).setCellValue("OFFICE_ACCOUNT_NO");                 		
	       		rowhead.createCell((short) 10).setCellValue("HO_ACCOUNT_NO");
	       		rowhead.createCell((short) 11).setCellValue("DR_ACCOUNT_HEAD_CODE");
	       		rowhead.createCell((short) 12).setCellValue("CR_ACCOUNT_HEAD_CODE");
	       		rowhead.createCell((short) 13).setCellValue("CHEQUE_OR_DD");
	       		rowhead.createCell((short) 14).setCellValue("CHEQUE_DD_NO");
	       		rowhead.createCell((short) 15).setCellValue("PARTICULARS");
	       		rowhead.createCell((short) 16).setCellValue("OFFICE_ID");
	       		rowhead.createCell((short) 17).setCellValue("AUTO_STATUS");
	       		
       	/*	    rowhead.createCell((short) 14).setCellValue("Sltypecodedesc");
       		    rowhead.createCell((short) 15).setCellValue("Remarks");
         		rowhead.createCell((short) 16).setCellValue("Sl_No");
         		rowhead.createCell((short) 17).setCellValue("Crhoa");  
         		rowhead.createCell((short) 18).setCellValue("Amount");
         		rowhead.createCell((short) 19).setCellValue("Crindicator");*/
         		
         		ServletOutputStream fileOut=null;
             if (unitid==0)
             {
          	   ss="SELECT a.accounting_unit_id, b.accounting_unit_name, a.accounting_for_office_id, "
          	   		+ " a.cashbook_year, A.Cashbook_Month, a.voucher_no,"
          	   		+ "  case a.remittance_type   when 'C' then 'Collection' "
          	   		+ "   when 'U' then 'Unspent' "
          	   		+ "  when 'NS' then 'NRDWP Support interest Transfer' "
          	   		+ "  when 'NM' then 'NRDWP Main Interest Transfer' "
          	   		+ "  when 'UNM' then 'Unspent from NRDWP Main' "
          	   		+ "  when 'UNS' then 'Unspent from NRDWP Support' "
          	   		+ "  when 'UNC' then 'Unspent from NRDWP Calamity' "
          	   		+ "  when 'WQM' then 'unspent from NRDWP WQMSP' "
          	   		+ "  when 'FDW' then 'Full Deposit Work' "
          	   		+ "  When 'WATCHARGEREV' then 'WATCHARGEREV' "
          	   		+ "  When 'NONWATCHARGEREV' then 'NONWATCHARGEREV' "
          	   		+ "  When 'UIDDSMT' then 'UIDDSMT' "
          	   		+ "  When 'Security Deposit' then 'Security Deposit' "
          	   		+ "  when 'OPR-NRDWP-Calamity' then 'OPR-NRDWP-Calamity' "
          	   		+ "  When 'LB100PCNTCONTRIB' then 'LB100PCNTCONTRIB' "
          	   		+ "  When 'KFW' then 'KFW' "
          	   		+ "  When 'JICA' then 'JICA'"
          	   		+ "  When 'FieldKit' then 'FieldKit'"
          	   		+ " end as remtype,   a.remittance_type,   A.Total_Amount,   A.Office_Account_No,   a.ho_account_no, a.dr_account_head_code,   a.cr_account_head_code,   A.Cheque_Or_Dd,"
          	   		+ "  a.cheque_dd_no,   A.Particulars, (SELECT RECEIPT_NO   FROM FAS_FUND_RECEIPT_BY_HO  WHERE  TRF_VOUCHER_NO=A.VOUCHER_NO "
          	   		+ "  AND TRF_VOUCHER_DATE=A.DATE_OF_TRANSFER limit 1   )AS DOC_NO,   (SELECT RECEIPT_date   FROM FAS_FUND_RECEIPT_BY_HO  WHERE  TRF_VOUCHER_NO=A.VOUCHER_NO   AND TRF_VOUCHER_DATE=A.DATE_OF_TRANSFER   limit 1   )AS DOC_DATE, "
          	   		+ "  AUTO_STATUS "
          	   		+ " FROM Fas_Fund_Trf_From_Office A,   Fas_Mst_Acct_Units B "
          	   		+ " WHERE A.Accounting_Unit_Id    =B.Accounting_Unit_Id "
          	   		//+ " AND a.accounting_for_office_id=b.accounting_unit_office_id "
          	   		+ " AND A.TRANSFER_STATUS='L' "
          	   		+ " AND A.CASHBOOK_MONTH="+ CashBookMonth
          	   		+ " AND A.CASHBOOK_YEAR="+ CashBookYear +" order by ACCOUNTING_UNIT_ID"; 
          	   
          	   
         		 }
             else
             {
           	  
           	 ss= "SELECT a.accounting_unit_id, b.accounting_unit_name, a.accounting_for_office_id, "
           	   		+ " a.cashbook_year, A.Cashbook_Month, a.voucher_no,"
           	   		+ "  case a.remittance_type   when 'C' then 'Collection' "
           	   		+ "   when 'U' then 'Unspent' "
           	   		+ "  when 'NS' then 'NRDWP Support interest Transfer' "
           	   		+ "  when 'NM' then 'NRDWP Main Interest Transfer' "
           	   		+ "  when 'UNM' then 'Unspent from NRDWP Main' "
           	   		+ "  when 'UNS' then 'Unspent from NRDWP Support' "
           	   		+ "  when 'UNC' then 'Unspent from NRDWP Calamity' "
           	   		+ "  when 'WQM' then 'unspent from NRDWP WQMSP' "
           	   		+ "  when 'FDW' then 'Full Deposit Work' "
           	   		+ " end as remtype,   a.remittance_type,   A.Total_Amount,   A.Office_Account_No,   a.ho_account_no, a.dr_account_head_code,   a.cr_account_head_code,   A.Cheque_Or_Dd,"
           	   		+ "  a.cheque_dd_no,   A.Particulars, (SELECT RECEIPT_NO   FROM FAS_FUND_RECEIPT_BY_HO  WHERE  TRF_VOUCHER_NO=A.VOUCHER_NO "
          	   		+ "  AND TRF_VOUCHER_DATE=A.DATE_OF_TRANSFER   limit 1    )AS DOC_NO,   (SELECT RECEIPT_date   FROM FAS_FUND_RECEIPT_BY_HO  WHERE  TRF_VOUCHER_NO=A.VOUCHER_NO   AND TRF_VOUCHER_DATE=A.DATE_OF_TRANSFER    limit 1  )AS DOC_DATE,AUTO_STATUS "
           	   		+ " FROM Fas_Fund_Trf_From_Office A,   Fas_Mst_Acct_Units B "
           	   		+ " WHERE A.Accounting_Unit_Id    =B.Accounting_Unit_Id "
           	   	//	+ " AND a.accounting_for_office_id=b.accounting_unit_office_id "
	           	   	+ " AND A.TRANSFER_STATUS='L' "
	      	   		+ " AND A.CASHBOOK_MONTH="+ CashBookMonth
	      	   		+ " AND A.CASHBOOK_YEAR="+ CashBookYear
	      	   		+ " and a.accounting_unit_id="+ unitid +" order by ACCOUNTING_UNIT_ID"; 
           	 
             }
              System.out.println("ss::::"+ss);
             PreparedStatement ps2=connection.prepareStatement(ss);
            rs2=ps2.executeQuery();
            int j=1;
             while(rs2.next())
             {
          	   String rec_date=null;
         		HSSFRow row=   sheet.createRow((int)j);
         		         		
         		
         		row.createCell((short) 0).setCellValue(rs2.getInt("ACCOUNTING_UNIT_ID"));
         		row.createCell((short) 1).setCellValue(rs2.getString("ACCOUNTING_UNIT_NAME"));
         		row.createCell((short) 2).setCellValue(rs2.getInt("CASHBOOK_YEAR"));
         		row.createCell((short) 3).setCellValue(rs2.getInt("CASHBOOK_MONTH"));
         		row.createCell((short) 4).setCellValue(rs2.getInt("Doc_No"));
         		row.createCell((short) 5).setCellValue(rs2.getString("Doc_date"));    
         		row.createCell((short) 6).setCellValue(rs2.getString("REMTYPE"));                    		

	         	  /*String currentDate =rs2.getString("RECEIPT_DATE");
	         	  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");                   
	              SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");                                      
	              Date f = sdf1.parse(currentDate);                      
	              String     tdate = sdf.format(f);                   		
	         	  //System.out.println("Tdate  "+tdate);
	         	  row.createCell((short) 5).setCellValue(tdate);*/
         		
         		
         		row.createCell((short) 7).setCellValue(rs2.getString("REMITTANCE_TYPE"));
         		row.createCell((short) 8).setCellValue(rs2.getDouble("TOTAL_AMOUNT")); 
         		row.createCell((short) 9).setCellValue(rs2.getDouble("OFFICE_ACCOUNT_NO"));
         		row.createCell((short) 10).setCellValue(rs2.getDouble("HO_ACCOUNT_NO"));
         		row.createCell((short) 11).setCellValue(rs2.getInt("DR_ACCOUNT_HEAD_CODE"));
         		row.createCell((short) 12).setCellValue(rs2.getInt("CR_ACCOUNT_HEAD_CODE"));
         		row.createCell((short) 13).setCellValue(rs2.getString("CHEQUE_OR_DD"));
         		row.createCell((short) 14).setCellValue(rs2.getInt("CHEQUE_DD_NO"));
         		row.createCell((short) 15).setCellValue(rs2.getString("PARTICULARS"));
         		row.createCell((short) 16).setCellValue(rs2.getString("accounting_for_office_id"));
         		row.createCell((short) 17).setCellValue(rs2.getString("AUTO_STATUS"));
         		
         		
         		/*row.createCell((short) 15).setCellValue(rs2.getString("Remarks"));
         		row.createCell((short) 16).setCellValue(rs2.getInt("Sl_No"));
         		row.createCell((short) 17).setCellValue(rs2.getInt("CRHAA"));                   		
         		row.createCell((short) 18).setCellValue(rs2.getDouble("Amount"));
         		row.createCell((short) 19).setCellValue(rs2.getString("crindicator"));*/
         		
         		
         		j++;
         	 }
             fileOut = response.getOutputStream();
             hwb.write(fileOut);
        		fileOut.close();
         		} catch ( Exception ex ) {
         		    System.out.println(ex);

         		}
				
				
			}
                	   
          else if (jrnltype.equalsIgnoreCase("FundTransferBank")) {
				
				
				
              try{
         			response.setContentType("application/vnd.ms-excel");
                     response.setHeader ("Content-Disposition", "attachment;filename=\"FUND TRANSFER By BANK DATA.xls\"");
         		
         		HSSFWorkbook hwb=new HSSFWorkbook();
         		HSSFSheet sheet =  hwb.createSheet("new sheet");

         		HSSFRow rowhead=   sheet.createRow((short)0);
         		                                                                                                                                                                                                                                                    

         		
         		rowhead.createCell((short) 0).setCellValue("ACCOUNTING_UNIT_ID");
	       		rowhead.createCell((short) 1).setCellValue("ACCOUNTING_UNIT_NAME");
	       		rowhead.createCell((short) 2).setCellValue("OfficeId");
	       		rowhead.createCell((short) 3).setCellValue("CASHBOOK_YEAR");
	       		rowhead.createCell((short) 4).setCellValue("CASHBOOK_MONTH");  
	       		rowhead.createCell((short) 5).setCellValue("voucher_No");	       		
	       		rowhead.createCell((short) 6).setCellValue("Sl_No");
           		rowhead.createCell((short) 7).setCellValue("VOUCHERDATE,");
           		rowhead.createCell((short) 8).setCellValue("TRANSFER_TO_UNIT_ID");
           		rowhead.createCell((short) 9).setCellValue("TRANSFER_TO_OFFICE_ID");
           		rowhead.createCell((short) 10).setCellValue("HO_ACCOUNT_NO");
           		rowhead.createCell((short) 11).setCellValue("Total_amount");
           		rowhead.createCell((short) 12).setCellValue("CRHOA");
           		rowhead.createCell((short) 13).setCellValue("CRINDICATOR");
           		rowhead.createCell((short) 14).setCellValue("OFFICE_ACCOUNT_NO");
           		rowhead.createCell((short) 15).setCellValue("AMOUNT");
           		rowhead.createCell((short) 16).setCellValue("DRHOA");
           		rowhead.createCell((short) 17).setCellValue("DRINDICATOR");           		
           		rowhead.createCell((short) 18).setCellValue("CHEQUE_OR_DD");
           		rowhead.createCell((short) 19).setCellValue("CHEQUE_DD_NO");
           		rowhead.createCell((short) 20).setCellValue("CHEQUE_DD_DATE");
           		rowhead.createCell((short) 21).setCellValue("FUND_TYPE");
           		rowhead.createCell((short) 22).setCellValue("Particulars");
           		rowhead.createCell((short) 23).setCellValue("AUTO_STATUS");
           		
         		ServletOutputStream fileOut=null;
             if (unitid==0)
             {
               	 ss= "select  A.ACCOUNTING_UNIT_ID,  u.accounting_unit_name,  a.accounting_for_office_id AS OfficeId ,  a.cashbook_year,  A.Cashbook_Month,  A.VOUCHER_NO,  B.SL_NO,   A.DATE_OF_TRANSFER AS VOUCHERDATE ,   B.TRANSFER_TO_OFFICE_ID,  "
                	 		+ "( SELECT T.ACCOUNTING_UNIT_ID  FROM Fas_Mst_Acct_Units t   WHERE T.ACCOUNTING_UNIT_OFFICE_ID  =b.TRANSFER_TO_OFFICE_ID limit 1   )AS unit_id,"
                   	 		+" A.HO_ACCOUNT_NO,   A.Total_Amount,A.ACCOUNT_HEAD_CODE AS CRHOA,A.CR_DR_INDICATOR AS CRINDICATOR ,B.AMOUNT,  B.OFFICE_ACCOUNT_NO,  B.ACCOUNT_HEAD_CODE AS DRHOA,  B.CR_DR_INDICATOR AS DRINDICATOR,b.CHEQUE_OR_DD,"
                   	 		+ "  B.CHEQUE_DD_NO,  B.CHEQUE_DD_DATE,B.FUND_TYPE ,  b.Particulars,b.AUTO_STATUS"
                	 		+ " FROM FAS_FUND_TRF_FROM_HO_MASTER A,   FAS_FUND_TRF_FROM_HO_TRN B ,  Fas_Mst_Acct_Units u  "
                	 		+ " WHERE A.Accounting_Unit_Id =B.Accounting_Unit_Id  "
                	 	    + " AND A.ACCOUNTING_FOR_OFFICE_ID=B.ACCOUNTING_FOR_OFFICE_ID " 
                	 		+ " AND A.CASHBOOK_YEAR        =B.CASHBOOK_YEAR "
                	 		+ " AND A.CASHBOOK_MONTH       =B.CASHBOOK_MONTH "
                	 		+ " AND A.VOUCHER_NO           =B.VOUCHER_NO "
                	 		+ " AND A.TRANSFER_STATUS      ='L' "
                	 		+ " AND A.ACCOUNTING_UNIT_ID   =U.ACCOUNTING_UNIT_ID "
                	 		+ " AND A.CASHBOOK_MONTH       ="+ CashBookMonth
                	 		+ " AND A.CASHBOOK_YEAR        ="+ CashBookYear ;
                	 	  //  +"  and a.accounting_unit_id="+ unitid ;
          	   
         		 }
             else
             {
           	  
           	 ss= "select A.ACCOUNTING_UNIT_ID,  u.accounting_unit_name,  a.accounting_for_office_id AS OfficeId ,  a.cashbook_year,  A.Cashbook_Month,  A.VOUCHER_NO,  B.SL_NO,   A.DATE_OF_TRANSFER AS VOUCHERDATE ,   B.TRANSFER_TO_OFFICE_ID,  "
           	 		+ "( SELECT T.ACCOUNTING_UNIT_ID  FROM Fas_Mst_Acct_Units t   WHERE T.ACCOUNTING_UNIT_OFFICE_ID  =b.TRANSFER_TO_OFFICE_ID limit 1   )AS unit_id,"
           	 		+" A.HO_ACCOUNT_NO,   A.Total_Amount,A.ACCOUNT_HEAD_CODE AS CRHOA,A.CR_DR_INDICATOR AS CRINDICATOR ,B.AMOUNT,  B.OFFICE_ACCOUNT_NO,  B.ACCOUNT_HEAD_CODE AS DRHOA,  B.CR_DR_INDICATOR AS DRINDICATOR,b.CHEQUE_OR_DD,"
           	 		+ "  B.CHEQUE_DD_NO,  B.CHEQUE_DD_DATE,B.FUND_TYPE , b.Particulars,b.AUTO_STATUS"
           	 		+ " FROM FAS_FUND_TRF_FROM_HO_MASTER A,   FAS_FUND_TRF_FROM_HO_TRN B ,  Fas_Mst_Acct_Units u  "
           	 		+ " WHERE A.Accounting_Unit_Id =B.Accounting_Unit_Id  "
           	 		+ " AND A.ACCOUNTING_FOR_OFFICE_ID=B.ACCOUNTING_FOR_OFFICE_ID " 
           	 		+ " AND A.CASHBOOK_YEAR        =B.CASHBOOK_YEAR "
           	 		+ " AND A.CASHBOOK_MONTH       =B.CASHBOOK_MONTH "
           	 		+ " AND A.VOUCHER_NO           =B.VOUCHER_NO "
           	 		+ " AND A.TRANSFER_STATUS      ='L' "
           	 		+ " AND A.ACCOUNTING_UNIT_ID   =U.ACCOUNTING_UNIT_ID "
           	 		+ " AND A.CASHBOOK_MONTH       ="+ CashBookMonth
           	 		+ " AND A.CASHBOOK_YEAR        ="+ CashBookYear+""
           	 				+ " and B.TRANSFER_TO_OFFICE_ID in "
           	 				+ " (SELECT t.ACCOUNTING_UNIT_OFFICE_ID  FROM FAS_MST_ACCT_UNITS t WHERE "
           	 				+ " t.accounting_unit_id   ="+ unitid+")";
           	 	     		
           	   
             }
              System.out.println("ss::::"+ss);
             PreparedStatement ps2=connection.prepareStatement(ss);
            rs2=ps2.executeQuery();
            int j=1;
             while(rs2.next())
             {
          	   String rec_date=null;
         		HSSFRow row=   sheet.createRow((int)j);
         		          		
         		
         		row.createCell((short) 0).setCellValue(rs2.getInt("ACCOUNTING_UNIT_ID"));
         		row.createCell((short) 1).setCellValue(rs2.getString("ACCOUNTING_UNIT_NAME"));         		
         		row.createCell((short) 2).setCellValue(rs2.getInt("OfficeId"));
         		row.createCell((short) 3).setCellValue(rs2.getInt("CASHBOOK_YEAR"));
         		row.createCell((short) 4).setCellValue(rs2.getInt("CASHBOOK_MONTH"));
         		row.createCell((short) 5).setCellValue(rs2.getInt("voucher_no"));                    		
         		row.createCell((short) 6).setCellValue(rs2.getInt("SL_NO"));
         		row.createCell((short) 7).setCellValue(rs2.getString("VOUCHERDATE")); 
         		row.createCell((short) 8).setCellValue(rs2.getInt("unit_id"));
         		row.createCell((short) 9).setCellValue(rs2.getInt("TRANSFER_TO_OFFICE_ID"));
         		row.createCell((short) 10).setCellValue(rs2.getDouble("HO_ACCOUNT_NO"));
         		row.createCell((short) 11).setCellValue(rs2.getDouble("Total_amount"));
         		row.createCell((short) 12).setCellValue(rs2.getInt("CRHOA"));
         		row.createCell((short) 13).setCellValue(rs2.getString("CRINDICATOR"));
         		row.createCell((short) 14).setCellValue(rs2.getDouble("OFFICE_ACCOUNT_NO"));         		
         		row.createCell((short) 15).setCellValue(rs2.getDouble("AMOUNT"));
         		row.createCell((short) 16).setCellValue(rs2.getInt("DRHOA"));
         		row.createCell((short) 17).setCellValue(rs2.getString("DRINDICATOR"));
         		row.createCell((short) 18).setCellValue(rs2.getString("CHEQUE_OR_DD"));
         		row.createCell((short) 19).setCellValue(rs2.getInt("CHEQUE_DD_NO"));
         		row.createCell((short) 20).setCellValue(rs2.getString("CHEQUE_DD_DATE")); 
         		row.createCell((short) 21).setCellValue(rs2.getString("FUND_TYPE")); 
         		row.createCell((short) 22).setCellValue(rs2.getString("Particulars"));
         		row.createCell((short) 23).setCellValue(rs2.getString("AUTO_STATUS"));
        		/*                		
         		row.createCell((short) 18).setCellValue(rs2.getDouble("Amount"));
         		row.createCell((short) 19).setCellValue(rs2.getString("crindicator"));*/
         		
         		
         		j++;
         	 }
             fileOut = response.getOutputStream();
             hwb.write(fileOut);
        		fileOut.close();
         		} catch ( Exception ex ) {
         		    System.out.println(ex);

         		}
				
				
			}
                	   
          else if (jrnltype.equalsIgnoreCase("FundReceipt")) {
				
				
				
              try{
         			response.setContentType("application/vnd.ms-excel");
                     response.setHeader ("Content-Disposition", "attachment;filename=\"FUND RECEIPT BY UNIT DATA.xls\"");
         		
         		HSSFWorkbook hwb=new HSSFWorkbook();
         		HSSFSheet sheet =  hwb.createSheet("new sheet");

         		HSSFRow rowhead=   sheet.createRow((short)0);
         		rowhead.createCell((short) 0).setCellValue("ACCOUNTING_UNIT_ID");
	       		rowhead.createCell((short) 1).setCellValue("ACCOUNTING_UNIT_NAME");
	       		rowhead.createCell((short) 2).setCellValue("CASHBOOK_YEAR");
	       		rowhead.createCell((short) 3).setCellValue("CASHBOOK_MONTH");	       		
	       		rowhead.createCell((short) 4).setCellValue("VOUCHER_NO");
	       		rowhead.createCell((short) 5).setCellValue("VOUCHER_DATE");
	     		rowhead.createCell((short) 6).setCellValue("BANK_NAME");
	       		rowhead.createCell((short) 7).setCellValue("BRANCH_NAME");
	       		rowhead.createCell((short) 8).setCellValue("TOTAL_AMOUNT");
	       		rowhead.createCell((short) 9).setCellValue("OFFICE_ACCOUNT_NO");                 		
	       		rowhead.createCell((short) 10).setCellValue("HO_ACCOUNT_NO");
	       		rowhead.createCell((short) 11).setCellValue("DR_ACCOUNT_HEAD_CODE");
	       		rowhead.createCell((short) 12).setCellValue("CR_ACCOUNT_HEAD_CODE");
	       		rowhead.createCell((short) 13).setCellValue("CHEQUE_OR_DD");
	       		rowhead.createCell((short) 14).setCellValue("CHEQUE_DD_NO");
	       		rowhead.createCell((short) 15).setCellValue("CHEQUE_DD_DATE");
	       		rowhead.createCell((short) 16).setCellValue("CHALLAN_NO");
	       		rowhead.createCell((short) 17).setCellValue("PARTICULARS");
	       		rowhead.createCell((short) 18).setCellValue("Office_Id");
         		
         		
         		ServletOutputStream fileOut=null;
             if (unitid==0)
             {
               	 ss= " SELECT a.accounting_unit_id,   b.accounting_unit_name,  a.accounting_for_office_id,  a.cashbook_year,  A.Cashbook_Month,   A.CHALLAN_NO, "
               	 		+ "  A.RECEIPT_NO,  A.RECEIPT_DATE,M.BANK_NAME,T.BRANCH_NAME,  A.Total_Amount,   A.Office_Account_No, "
                	 		+ " a.ho_account_no,   a.dr_account_head_code,   a.cr_account_head_code,  A.Cheque_Or_Dd,   a.cheque_dd_no, A.CHEQUE_DD_DATE,   A.PARTICULARS "
                   	 		+ " FROM FAS_FUND_RECEIPT_BY_OFFICE A,  Fas_Mst_Acct_Units B, FAS_MST_BANKS m, FAS_MST_BANK_BRANCHES t  "
                   	 		+ " WHERE A.Accounting_Unit_Id    =B.Accounting_Unit_Id "
                   	 		//+ " AND A.ACCOUNTING_FOR_OFFICE_ID=B.ACCOUNTING_UNIT_OFFICE_ID"
                   	 		+ " AND A.OFFICE_BANK_ID=M.BANK_ID  "
                   	 		+ " AND A.OFFICE_BRANCH_ID=T.BRANCH_ID "
                   	 		+ "  AND M.BANK_ID=T.BANK_ID   "
                   	 		+ " AND A.RECEIPT_STATUS='L' "
                   	 		+ " AND A.CASHBOOK_MONTH="+ CashBookMonth
                   	 		+ " AND A.CASHBOOK_YEAR="+ CashBookYear ;           	   
         		 }
             else
             {
           	  
           	 ss= " SELECT a.accounting_unit_id,   b.accounting_unit_name,  a.accounting_for_office_id,  a.cashbook_year,  A.Cashbook_Month,   A.CHALLAN_NO,  A.Total_Amount,   A.Office_Account_No, "
            	 		+ " A.RECEIPT_NO,  A.RECEIPT_DATE ,M.BANK_NAME,T.BRANCH_NAME, a.ho_account_no,   a.dr_account_head_code,   a.cr_account_head_code,  A.Cheque_Or_Dd,   a.cheque_dd_no,A.CHEQUE_DD_DATE,   A.PARTICULARS "
            	 		+ " FROM FAS_FUND_RECEIPT_BY_OFFICE A,   Fas_Mst_Acct_Units B, FAS_MST_BANKS m, FAS_MST_BANK_BRANCHES t  "
            	 		+ " WHERE A.Accounting_Unit_Id    =B.Accounting_Unit_Id "
            	 	   //+ " AND A.ACCOUNTING_FOR_OFFICE_ID=B.ACCOUNTING_UNIT_OFFICE_ID  "
            	 		+ " AND A.OFFICE_BANK_ID=M.BANK_ID  "
               	 		+ " AND A.OFFICE_BRANCH_ID=T.BRANCH_ID "
               	 		+ "  AND M.BANK_ID=T.BANK_ID   "
            	 		+ " AND A.RECEIPT_STATUS='L' "
            	 		+ " AND A.CASHBOOK_MONTH="+ CashBookMonth
            	 		+ " AND A.CASHBOOK_YEAR="+ CashBookYear 
           	 	        +"  and a.accounting_unit_id="+ unitid ;       	   		
           	   
             }
              System.out.println("ss::::"+ss);
             PreparedStatement ps2=connection.prepareStatement(ss);
            rs2=ps2.executeQuery();
            int j=1;
             while(rs2.next())
             {
          	   String rec_date=null;
         		HSSFRow row=   sheet.createRow((int)j);         		          		
         		
         		row.createCell((short) 0).setCellValue(rs2.getInt("ACCOUNTING_UNIT_ID"));
         		row.createCell((short) 1).setCellValue(rs2.getString("ACCOUNTING_UNIT_NAME"));
         		row.createCell((short) 2).setCellValue(rs2.getInt("CASHBOOK_YEAR"));
         		row.createCell((short) 3).setCellValue(rs2.getInt("CASHBOOK_MONTH")); 
	       		row.createCell((short) 4).setCellValue(rs2.getInt("RECEIPT_NO"));
	       		row.createCell((short) 5).setCellValue(rs2.getString("RECEIPT_DATE"));         		         		
         		row.createCell((short) 6).setCellValue(rs2.getString("BANK_NAME"));
         		row.createCell((short) 7).setCellValue(rs2.getString("BRANCH_NAME"));
         		row.createCell((short) 8).setCellValue(rs2.getDouble("TOTAL_AMOUNT")); 
         		row.createCell((short) 9).setCellValue(rs2.getDouble("OFFICE_ACCOUNT_NO"));
         		row.createCell((short) 10).setCellValue(rs2.getDouble("HO_ACCOUNT_NO"));
         		row.createCell((short) 11).setCellValue(rs2.getInt("DR_ACCOUNT_HEAD_CODE"));
         		row.createCell((short) 12).setCellValue(rs2.getInt("CR_ACCOUNT_HEAD_CODE"));
         		row.createCell((short) 13).setCellValue(rs2.getString("CHEQUE_OR_DD"));
         		row.createCell((short) 14).setCellValue(rs2.getString("CHEQUE_DD_NO"));
         		row.createCell((short) 15).setCellValue(rs2.getString("CHEQUE_DD_DATE"));
         		row.createCell((short) 16).setCellValue(rs2.getInt("CHALLAN_NO"));
         		row.createCell((short) 17).setCellValue(rs2.getString("PARTICULARS"));
         		row.createCell((short) 18).setCellValue(rs2.getString("accounting_for_office_id"));
         		
         		j++;
         	 }
             fileOut = response.getOutputStream();
             hwb.write(fileOut);
        		fileOut.close();
         		} catch ( Exception ex ) {
         		    System.out.println(ex);

         		}
				
				
			}
          else if (jrnltype.equalsIgnoreCase("FundReceiptBank")) {
				
				
				
              try{
         			response.setContentType("application/vnd.ms-excel");
                     response.setHeader ("Content-Disposition", "attachment;filename=\"FUND RECEIPT BY BANK DATA.xls\"");
         		
         		HSSFWorkbook hwb=new HSSFWorkbook();
         		HSSFSheet sheet =  hwb.createSheet("new sheet");

         		HSSFRow rowhead=   sheet.createRow((short)0);
         		
         		rowhead.createCell((short) 0).setCellValue("ACCOUNTING_UNIT_ID");
	       		rowhead.createCell((short) 1).setCellValue("ACCOUNTING_UNIT_NAME");
	       		rowhead.createCell((short) 2).setCellValue("CASHBOOK_YEAR");
	       		rowhead.createCell((short) 3).setCellValue("CASHBOOK_MONTH");       		
	       		rowhead.createCell((short) 4).setCellValue("VOUCHER_NO");
	       		rowhead.createCell((short) 5).setCellValue("VOUCHER_DATE");	       		
	       		rowhead.createCell((short) 6).setCellValue("RECTYPE");
	       		rowhead.createCell((short) 7).setCellValue("RECEIPT_TYPE");
	       		rowhead.createCell((short) 8).setCellValue("REF_NO");
	       		rowhead.createCell((short) 9).setCellValue("REF_DATE");
	       		rowhead.createCell((short) 10).setCellValue("RECEIVED FROM THE OFFICE");  
	       		rowhead.createCell((short) 11).setCellValue("TOTAL_AMOUNT");
	       		rowhead.createCell((short) 12).setCellValue("OFFICE_ACCOUNT_NO");                 		
	       		rowhead.createCell((short) 13).setCellValue("HO_ACCOUNT_NO");
	       		rowhead.createCell((short) 14).setCellValue("DR HOA");
	       		rowhead.createCell((short) 15).setCellValue("CR HOA");
	       		rowhead.createCell((short) 16).setCellValue("CHEQUE_OR_DD");
	       		rowhead.createCell((short) 17).setCellValue("CHEQUE_DD_NO");
	       		rowhead.createCell((short) 18).setCellValue("CHEQUE_DD_DATE");
	       		rowhead.createCell((short) 19).setCellValue("CHALLAN_NO");
	       		rowhead.createCell((short) 20).setCellValue("PARTICULARS");
	       		rowhead.createCell((short) 21).setCellValue("Office_Id");
         		
         		ServletOutputStream fileOut=null;
             if (unitid==0)
             {
               	 ss= " SELECT a.accounting_unit_id,  b.accounting_unit_name,  a.accounting_for_office_id,  a.cashbook_year,  A.Cashbook_Month,   A.RECEIPT_NO,  A.RECEIPT_DATE , "
               	 		+ "  case a.RECEIPT_TYPE   when 'C' then 'Collection' "
               	 		+ "  when 'U' then 'Unspent' "
               	 		+ "  when 'NS' then 'NRDWP Support interest Transfer' "
               	 		+ "  when 'NM' then 'NRDWP Main Interest Transfer' "
               	 		+ "  when 'UNM' then 'Unspent from NRDWP Main' "
               	 		+ "  when 'UNS' then 'Unspent from NRDWP Support' "
               	 		+ "  when 'UNC' then 'Unspent from NRDWP Calamity' "
               	 		+ "  when 'WQM' then 'unspent from NRDWP WQMSP'"
               	 		+ "  WHEN 'FDW' THEN 'Full Deposit Work' "
               	 		+ "  END AS RECTYPE,A.RECEIPT_TYPE ,  A.TOTAL_AMOUNT,  A.OFFICE_ACCOUNT_NO,  A.HO_ACCOUNT_NO,  A.DR_ACCOUNT_HEAD_CODE AS DRHOA ,  A.CR_ACCOUNT_HEAD_CODE AS CRHOA ,  A.REF_NO,A.REF_DATE,   A.RECEIVED_FROM_OFFICE_ID AS OFFICE_ID,  A.CHEQUE_OR_DD,  A.CHEQUE_DD_NO,  A.CHEQUE_DD_DATE,  A.CHALLAN_NO,   A.PARTICULARS  "                   
                     	+ " FROM FAS_FUND_RECEIPT_BY_HO A,  Fas_Mst_Acct_Units B "
               	 		+ " WHERE A.Accounting_Unit_Id    =B.Accounting_Unit_Id "
               	 		//+ " AND A.ACCOUNTING_FOR_OFFICE_ID=B.ACCOUNTING_UNIT_OFFICE_ID  "
               	 		+ " AND A.RECEIPT_STATUS='L' "
               	 		+ " AND A.CASHBOOK_MONTH="+ CashBookMonth
               	 		+ " AND A.CASHBOOK_YEAR="+ CashBookYear  +" order by a.accounting_unit_id ";         	 		
                  		
         		 }
             else
             {
           	  
           	 ss= "SELECT a.accounting_unit_id,  b.accounting_unit_name,  a.accounting_for_office_id,  a.cashbook_year,  A.Cashbook_Month,   A.RECEIPT_NO,  A.RECEIPT_DATE , "
               	 		+ "  case a.RECEIPT_TYPE   when 'C' then 'Collection' "
               	 		+ "  when 'U' then 'Unspent' "
               	 		+ "  when 'NS' then 'NRDWP Support interest Transfer' "
               	 		+ "  when 'NM' then 'NRDWP Main Interest Transfer' "
               	 		+ "  when 'UNM' then 'Unspent from NRDWP Main' "
               	 		+ "  when 'UNS' then 'Unspent from NRDWP Support' "
               	 		+ "  when 'UNC' then 'Unspent from NRDWP Calamity' "
               	 		+ "  when 'WQM' then 'unspent from NRDWP WQMSP'"
               	 		+ "  WHEN 'FDW' THEN 'Full Deposit Work' "
               	 		+ "  END AS RECTYPE,A.RECEIPT_TYPE ,  A.TOTAL_AMOUNT,  A.OFFICE_ACCOUNT_NO,  A.HO_ACCOUNT_NO,  A.DR_ACCOUNT_HEAD_CODE AS CRHOA ,  A.CR_ACCOUNT_HEAD_CODE AS DRHOA ,  A.REF_NO,A.REF_DATE,   A.RECEIVED_FROM_OFFICE_ID AS OFFICE_ID,  A.CHEQUE_OR_DD,  A.CHEQUE_DD_NO,  A.CHEQUE_DD_DATE,  A.CHALLAN_NO,   A.PARTICULARS  "                   
                     	
               	 		+ " FROM FAS_FUND_RECEIPT_BY_HO A,  Fas_Mst_Acct_Units B "
               	 		+ " WHERE A.Accounting_Unit_Id    =B.Accounting_Unit_Id "
               	 		//+ " AND A.ACCOUNTING_FOR_OFFICE_ID=B.ACCOUNTING_UNIT_OFFICE_ID  "
               	 		+ " AND A.RECEIPT_STATUS='L' "
               	 		+ " AND A.CASHBOOK_MONTH="+ CashBookMonth
               	 		+ " AND A.CASHBOOK_YEAR="+ CashBookYear+" "
               	 				+ " and  A.RECEIVED_FROM_OFFICE_ID  in (SELECT A.ACCOUNTING_UNIT_OFFICE_ID  "
               	 				+ " FROM FAS_MST_ACCT_UNITS A WHERE "
               	 				+ " a.accounting_unit_id="+ unitid+")" ;
               	 	          	   		
           	   
             }
              System.out.println("ss::::"+ss);
             PreparedStatement ps2=connection.prepareStatement(ss);
            rs2=ps2.executeQuery();
            int j=1;
             while(rs2.next())
             {
            	 
          	   String rec_date=null;
         		HSSFRow row=   sheet.createRow((int)j);
         		
	       		row.createCell((short) 0).setCellValue(rs2.getInt("ACCOUNTING_UNIT_ID"));
         		row.createCell((short) 1).setCellValue(rs2.getString("ACCOUNTING_UNIT_NAME"));
         		row.createCell((short) 2).setCellValue(rs2.getInt("CASHBOOK_YEAR"));
         		row.createCell((short) 3).setCellValue(rs2.getInt("CASHBOOK_MONTH"));         		
         		row.createCell((short) 4).setCellValue(rs2.getInt("RECEIPT_NO"));
         		row.createCell((short) 5).setCellValue(rs2.getString("RECEIPT_DATE"));
         		row.createCell((short) 6).setCellValue(rs2.getString("RECTYPE"));
         		row.createCell((short) 7).setCellValue(rs2.getString("RECEIPT_TYPE"));
         		row.createCell((short) 8).setCellValue(rs2.getString("REF_NO"));
         		row.createCell((short) 9).setCellValue(rs2.getString("REF_DATE"));
         		row.createCell((short) 10).setCellValue(rs2.getInt("OFFICE_ID"));        		
         		row.createCell((short) 11).setCellValue(rs2.getDouble("TOTAL_AMOUNT")); 
         		row.createCell((short) 12).setCellValue(rs2.getDouble("OFFICE_ACCOUNT_NO"));
         		row.createCell((short) 13).setCellValue(rs2.getDouble("HO_ACCOUNT_NO"));
         		row.createCell((short) 14).setCellValue(rs2.getInt("DRHOA"));
         		row.createCell((short) 15).setCellValue(rs2.getInt("CRHOA"));
         		row.createCell((short) 16).setCellValue(rs2.getString("CHEQUE_OR_DD"));
         		row.createCell((short) 17).setCellValue(rs2.getInt("CHEQUE_DD_NO"));
         		row.createCell((short) 18).setCellValue(rs2.getString("CHEQUE_DD_DATE"));
         		row.createCell((short) 19).setCellValue(rs2.getInt("CHALLAN_NO"));
         		row.createCell((short) 20).setCellValue(rs2.getString("PARTICULARS"));
         		row.createCell((short) 21).setCellValue(rs2.getString("accounting_for_office_id"));
         		
         		j++;
         	 }
             fileOut = response.getOutputStream();
             hwb.write(fileOut);
        		fileOut.close();
         		} catch ( Exception ex ) {
         		    System.out.println(ex);

         		}
				
				
			}
                	   
          else if (jrnltype.equalsIgnoreCase("IBT")) {
				
				
				
              try{
         			response.setContentType("application/vnd.ms-excel");
                     response.setHeader ("Content-Disposition", "attachment;filename=\"IBT BY BANK DATA.xls\"");
         		
         		HSSFWorkbook hwb=new HSSFWorkbook();
         		HSSFSheet sheet =  hwb.createSheet("new sheet");

         		HSSFRow rowhead=   sheet.createRow((short)0);
         		
         		rowhead.createCell((short) 0).setCellValue("ACCOUNTING_UNIT_ID");
	       		rowhead.createCell((short) 1).setCellValue("ACCOUNTING_UNIT_NAME");
	       		rowhead.createCell((short) 2).setCellValue("CASHBOOK_YEAR");
	       		rowhead.createCell((short) 3).setCellValue("CASHBOOK_MONTH");  
	       		rowhead.createCell((short) 4).setCellValue("VOUCHER_NO");
	       		rowhead.createCell((short) 5).setCellValue("VOUCHERDATE");
	       		rowhead.createCell((short) 6).setCellValue("TOTAL_AMOUNT");
	       		rowhead.createCell((short) 7).setCellValue("FROM_ACCOUNT_NO");                 		
	       		rowhead.createCell((short) 8).setCellValue("TO_ACCOUNT_NO");
	       		rowhead.createCell((short) 9).setCellValue("DR_ACCOUNT_HEAD_CODE");
	       		rowhead.createCell((short) 10).setCellValue("CR_ACCOUNT_HEAD_CODE");
	       		rowhead.createCell((short) 11).setCellValue("CHEQUE_OR_DD");
	       		rowhead.createCell((short) 12).setCellValue("CHEQUE_DD_NO");
	       		rowhead.createCell((short) 13).setCellValue("CHEQUE_DD_DATE");
	       		rowhead.createCell((short) 14).setCellValue("PARTICULARS");
	       		rowhead.createCell((short) 15).setCellValue("Office_Id");
         		
         		ServletOutputStream fileOut=null;
             if (unitid==0)
             {
               	 ss= " SELECT a.accounting_unit_id,  b.accounting_unit_name,  a.accounting_for_office_id,  a.cashbook_year,  A.Cashbook_Month,  A.VOUCHER_NO, A.DATE_OF_TRANSFER as voucherdate,  A.TOTAL_AMOUNT,  A.FROM_ACCOUNT_NO,  A.TO_ACCOUNT_NO,"
               	 		+ "  a.dr_account_head_code,  a.cr_account_head_code,  A.Cheque_Or_Dd,  a.cheque_dd_no,A.CHEQUE_DD_DATE,  A.PARTICULARS "
               	 		+ " FROM FAS_INTER_BANK_TRF_AT_HO a,   Fas_Mst_Acct_Units B  "
               	 		+ " WHERE A.Accounting_Unit_Id    =B.Accounting_Unit_Id "
               	 	//	+ " AND A.ACCOUNTING_FOR_OFFICE_ID=B.ACCOUNTING_UNIT_OFFICE_ID  "
               	 		+ " AND A.TRANSFER_STATUS         ='L' " 
               	 		+ " AND A.CASHBOOK_MONTH="+ CashBookMonth
               	 		+ " AND A.CASHBOOK_YEAR="+ CashBookYear;
               	 	//+ " AND a.accounting_unit_id      =5 "        	 		
                  		
             }
             else
             {
           	  
            	 ss= " SELECT a.accounting_unit_id,  b.accounting_unit_name,  a.accounting_for_office_id,  a.cashbook_year,  A.Cashbook_Month,  A.VOUCHER_NO, A.DATE_OF_TRANSFER as voucherdate,  A.TOTAL_AMOUNT,  A.FROM_ACCOUNT_NO,  A.TO_ACCOUNT_NO,"
                	 		+ "  a.dr_account_head_code,  a.cr_account_head_code,  A.Cheque_Or_Dd,  a.cheque_dd_no,A.CHEQUE_DD_DATE,  A.PARTICULARS "
                	 		+ " FROM FAS_INTER_BANK_TRF_AT_HO a,   Fas_Mst_Acct_Units B  "
                	 		+ " WHERE A.Accounting_Unit_Id    =B.Accounting_Unit_Id "
                	 	//	+ " AND A.ACCOUNTING_FOR_OFFICE_ID=B.ACCOUNTING_UNIT_OFFICE_ID  "
                	 		+ " AND A.TRANSFER_STATUS         ='L' " 
                	 		+ " AND A.CASHBOOK_MONTH="+ CashBookMonth
                	 		+ " AND A.CASHBOOK_YEAR="+ CashBookYear 
                	 	    + " AND a.accounting_unit_id      ="+ unitid ;         	   		
           	   
             }
            System.out.println("ss::::"+ss);
            PreparedStatement ps2=connection.prepareStatement(ss);
            rs2=ps2.executeQuery();
            int j=1;
             while(rs2.next())
             {
          	   String rec_date=null;
         		HSSFRow row=   sheet.createRow((int)j);
         		
	       		row.createCell((short) 0).setCellValue(rs2.getInt("ACCOUNTING_UNIT_ID"));
         		row.createCell((short) 1).setCellValue(rs2.getString("ACCOUNTING_UNIT_NAME"));
         		row.createCell((short) 2).setCellValue(rs2.getInt("CASHBOOK_YEAR"));
         		row.createCell((short) 3).setCellValue(rs2.getInt("CASHBOOK_MONTH"));
         		row.createCell((short) 4).setCellValue(rs2.getInt("VOUCHER_NO"));
         		
         		 String currentDate =rs2.getString("voucherdate");
	         	  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");                   
	              SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");                                      
	              Date f = sdf1.parse(currentDate);                      
	              String     tdate = sdf.format(f);                   		
	         	  //System.out.println("Tdate  "+tdate);
	         	  
         		
         		row.createCell((short) 5).setCellValue(tdate);
         		row.createCell((short) 6).setCellValue(rs2.getDouble("TOTAL_AMOUNT")); 
         		row.createCell((short) 7).setCellValue(rs2.getDouble("FROM_ACCOUNT_NO"));
         		row.createCell((short) 8).setCellValue(rs2.getDouble("TO_ACCOUNT_NO"));
         		row.createCell((short) 9).setCellValue(rs2.getInt("DR_ACCOUNT_HEAD_CODE"));
         		row.createCell((short) 10).setCellValue(rs2.getInt("CR_ACCOUNT_HEAD_CODE"));
         		row.createCell((short) 11).setCellValue(rs2.getString("CHEQUE_OR_DD"));
         		row.createCell((short) 12).setCellValue(rs2.getInt("CHEQUE_DD_NO"));
         		
         		 String DD_DATE =rs2.getString("CHEQUE_DD_DATE");
         		 if (DD_DATE==null) {
         			row.createCell((short) 13).setCellValue(rs2.getString("CHEQUE_DD_DATE"));
				} else {
					SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");                   
		              SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");                                      
		              Date format = df1.parse(DD_DATE);                      
		              String     check_date = df.format(format);  
	         		
	         		row.createCell((short) 13).setCellValue(check_date);

				}
	         	  
         		row.createCell((short) 14).setCellValue(rs2.getString("PARTICULARS"));
         		row.createCell((short) 15).setCellValue(rs2.getString("accounting_for_office_id"));
         		j++;
         	 }
             fileOut = response.getOutputStream();
             hwb.write(fileOut);
        		fileOut.close();
         		} catch ( Exception ex ) {
         		    System.out.println(ex);

         		}
				
				
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
             String msg="Journal Record Download Failed";
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
