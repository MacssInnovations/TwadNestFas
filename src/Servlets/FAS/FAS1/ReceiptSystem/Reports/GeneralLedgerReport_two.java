/**
 *                            All Months + All A/C Head + single accounting unit with single office + 
 *                            single accounting unit with multiple offices 
 *                            modified by sathya on 24Apr2012
 */

package Servlets.FAS.FAS1.ReceiptSystem.Reports;

import java.awt.print.PrinterJob;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.*;

import java.util.*;

import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.JasperReport;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;

import java.text.SimpleDateFormat;

import javax.print.DocFlavor;

import javax.print.PrintService;

import net.sf.jasperreports.engine.JasperPrintManager;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

import javax.print.attribute.PrintServiceAttributeSet;

import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.JobName;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.PrinterName;

import Servlets.FAS.FAS1.CommonClass.ExcelConverter;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;

import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;


public class GeneralLedgerReport_two extends HttpServlet {
    private static final String CONTENT_TYPE ="text/html; charset=windows-1252";
   

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException 
    {
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
     System.out.println("<<<<<<<<<<<<<<<<<<GeneralLedgerReport_two>>>>>>>>>>>>>>>>>"); 
    	String sql = "";
      
      /**
       *  Session Checking 
       */
       
       try
        {
            HttpSession session=request.getSession(false);
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
      
           
        
        
      /**
       *  Variables Declaration 
       */
        
        Connection con=null;
        Connection con1=null;
        Connection con2=null;
        Connection con3=null;
        Connection con4=null;
        Connection con5=null;
        Connection con6=null;
        
        PreparedStatement ps1=null;
        PreparedStatement ps2=null,ps=null;
        PreparedStatement ps3=null;
        PreparedStatement ps4=null;
        PreparedStatement ps5=null;
      //  PreparedStatement ps=null;
     //   ResultSet rs=null;
        
        ResultSet rs2=null,rs1=null;
        ResultSet rs4=null;
        
        response.setContentType(CONTENT_TYPE);
      
      
      
      
      
 // PART I:     
      
         
        /**
         * Retrieving Configuration Values 
         */
        
        try {


            ResourceBundle rs =ResourceBundle.getBundle("Servlets.Security.servlets.Config");
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
            con =DriverManager.getConnection(ConnectionString, strdbusername.trim(), strdbpassword.trim());
            con1 =DriverManager.getConnection(ConnectionString, strdbusername.trim(), strdbpassword.trim());
            con2 =DriverManager.getConnection(ConnectionString, strdbusername.trim(), strdbpassword.trim());
            con3 =DriverManager.getConnection(ConnectionString, strdbusername.trim(), strdbpassword.trim());
            con4 =DriverManager.getConnection(ConnectionString, strdbusername.trim(), strdbpassword.trim());
            con5 =DriverManager.getConnection(ConnectionString, strdbusername.trim(), strdbpassword.trim());
            con6 =DriverManager.getConnection(ConnectionString, strdbusername.trim(), strdbpassword.trim());
    
         }
         catch (Exception ex) 
         {
            String connectMsg = " PART I:  Could not create the connection" + ex.getMessage() + " " + ex.getLocalizedMessage();
            System.out.println(connectMsg);
         }
      
      
      
      
      
// PART II:           
        
 
       /**
        *  Getting Parameters 
        */
        
        JasperDesign jasperDesign = null;
        File reportFile=null;
        String alloffice="";
        try {
                System.out.println("calling servlet GeneralLedgerReport_two for all AccountHeads betwen months**********");
                
                
                
                /** Get From Cash Book Month and Year */
                String txtCB_Year_from=request.getParameter("txtCB_Year_from");
                String txtCB_Month_from=request.getParameter("txtCB_Month_from");
                
                /** Get To Cash Book Month and Year */
                String txtCB_Year_to=request.getParameter("txtCB_Year_to");
                String txtCB_Month_to=request.getParameter("txtCB_Month_to");
                
                /** Find whether to display all or specific account heads */
                String Specifictype=request.getParameter("SpecificAHC");
             String month_year=request.getParameter("month_year");
             System.out.println("month_year********"+month_year);
             
                /** Find Whether report should be either html or text or pdf */
                String rtype= request.getParameter("txtoption");
                
                
                /** Get Accounting Unit Id */
                String cmbAcc_UnitCode=request.getParameter("cmbAcc_UnitCode");
                
                /** Get Accounting Office id */
                String cmbOffice_code=request.getParameter("cmbOffice_code");
                
                /** Get Account Head */ 
                String cmbAccHeadCode=request.getParameter("txtAcc_HeadCode");
                
                /** Getting the all office option */
                try
               {
                   alloffice=request.getParameter("all_office"); 
                  System.out.println("alloffice********"+alloffice);
                }
                catch(Exception e)
               {
             	   System.out.println("error while getting the all office option"+e);
               }
                
                
                System.out.println("cmbAccHeadCode is:"+cmbAccHeadCode);
                System.out.println("accounting unit id is:"+cmbAcc_UnitCode);
                System.out.println("office code is:"+cmbOffice_code);
                System.out.println("Specific Type Value is:"+Specifictype);
                System.out.println("alloffice:"+alloffice); 
                               
            /** Convert Acciunting Unid Id and Office Id from String to Integer */ 
             int accountingunit=Integer.parseInt(cmbAcc_UnitCode);
             int accountingoffice=Integer.parseInt(cmbOffice_code);
             int accountheadcode=0;
             
             
            if(cmbAccHeadCode!=null)
             {
                 try 
                 { 
                    accountheadcode=Integer.parseInt(cmbAccHeadCode);
                   }
                 catch(Exception e)
                  {
                     accountheadcode=0;
                   }
              }
             
            
            /** Convert From Cash Book Month and Year from String to Integer */    
            int year_from=Integer.parseInt(txtCB_Year_from);
            int month_from=Integer.parseInt(txtCB_Month_from);
            
            /** Convert To Cash Book Month and Year from String to Integer */
            int year_to=Integer.parseInt(txtCB_Year_to);
            int month_to=Integer.parseInt(txtCB_Month_to);
                     
            /** Convert months in numbers to words for from cash book month */
            String monthInWords_from="";
            if(month_from==1)
            monthInWords_from="January";
            else if(month_from==2)
            monthInWords_from="February";
            else if(month_from==3)
            monthInWords_from="March";
            else if(month_from==4)
            monthInWords_from="April";
            else if(month_from==5)
            monthInWords_from="May";
            else if(month_from==6)
            monthInWords_from="June";
            else if(month_from==7)
            monthInWords_from="July";
            else if(month_from==8)
            monthInWords_from="August";
            else if(month_from==9)
            monthInWords_from="September";
            else if(month_from==10)
            monthInWords_from="October";
            else if(month_from==11)
            monthInWords_from="November";
            else if(month_from==12)
            monthInWords_from="December"; 

            /** Convert months in numbers to words for To Cash Book Month */
            String monthInWords_to="";
            if(month_to==1)
            monthInWords_to="January";
            else if(month_to==2)
            monthInWords_to="February";
            else if(month_to==3)
            monthInWords_to="March";
            else if(month_to==4)
            monthInWords_to="April";
            else if(month_to==5)
            monthInWords_to="May";
            else if(month_to==6)
            monthInWords_to="June";
            else if(month_to==7)
            monthInWords_to="July";
            else if(month_to==8)
            monthInWords_to="August";
            else if(month_to==9)
            monthInWords_to="September";
            else if(month_to==10)
            monthInWords_to="October";
            else if(month_to==11)
            monthInWords_to="November";
            else if(month_to==12)
            monthInWords_to="December"; 

           
           
  
// PART III:                     

                
            /**
             *  Truncate the table fas_general_ledger_report_tmp before inserting data into it 
             */
              
              try {
                  
                  ps2=con.prepareStatement("truncate table FAS_GENERAL_LEDGER_REPORT_TMP");
                  ps2.executeUpdate();                 
              }
              catch (Exception e) {
                  System.out.println(" PART III:  Can not truncate table fas_general_ledger_report_tmp "+e);
              }
              
             // new procedure for single unit more office called if All Office Option is chosen  
              
             CallableStatement cs=null,cs1=null;
             int code=0;
             String penName="";
             System.out.println("alloffice:::"+alloffice);
            if(alloffice==null)
             {   
            	
            	//cs=con.prepareCall("{call FAS_GL_ALLMONTH_ALLACCHEAD(?,?,?,?,?,?,?)}");   
            		cs=con.prepareCall("call FAS_GL_ALLMONTH_ALLACCHEAD_NEW(?::numeric,?::numeric,?::numeric,?::numeric,?::numeric,?::numeric,?::numeric)");  
                    System.out.println("FAS_GL_ALLMONTH_ALLACCHEAD_NEW");
            		
		             cs.setInt(1,accountingunit);
		             cs.setInt(2,accountingoffice);
		             cs.setInt(3,year_from);
		             cs.setInt(4,year_to);            
		             cs.setInt(5,month_from);            
		             cs.setInt(6,month_to);                         
		             cs.registerOutParameter(7,java.sql.Types.NUMERIC);
		             cs.setNull(7, java.sql.Types.NUMERIC);
		             System.out.println("before proc call");
		             cs.execute();
		             System.out.println("after proc call");
		             //int errcode=cs.getInt(7);
		             int errcode = cs.getBigDecimal(7).intValue();
		             
		             
		             if(errcode==0)
		             {
		            	 try {
		                     String ss="Select T.SUB_LEDGER_CODE,P.Pensioner_Name From Fas_General_Ledger_Report_Tmp T inner join Hrm_Pen_Mst_Details p on t.SUB_LEDGER_TYPE_CODE=18 and T.Sub_Ledger_Code=p.PPO_NO";
		                   System.out.println("qu for pen:::"+ss);
		                     ps=con.prepareStatement(ss);
		                    rs1=ps.executeQuery();
		                     while(rs1.next()) 
				                     {
		                    	 System.out.println("comes here for pensioner:");
				                         code=rs1.getInt("SUB_LEDGER_CODE");
				                         penName=rs1.getString("Pensioner_Name");
				                         
				                         
				                        PreparedStatement oo=con.prepareStatement("update FAS_GENERAL_LEDGER_REPORT_TMP set SUBTYPE_CODEDESC=? where SUB_LEDGER_TYPE_CODE=18 and SUB_LEDGER_CODE=?"); 
				                         oo.setString(1,penName);
				                         oo.setInt(2,code);
				                         int tes=oo.executeUpdate();
				                         
				                     }
		                     }
		                     catch (SQLException e) {
		                         System.out.println("SQL Exception in unitname -->"+e);
		                     } 
		             }
		             
		             System.out.println("General Ledger Error Code --->"+errcode);   
             }  
            else if(alloffice.equalsIgnoreCase("all_office"))
            {
           	 	System.out.println("If all office option is chosen call the procedure FAS_GL_ALLMONTH_ALLACCHEAD_SAT");
		          //   cs1=con.prepareCall("{call FAS_GL_ALLMONTH_ALLACCHEAD_SAT(?,?,?,?,?,?)}");             
           	   
           	// cs1=con.prepareCall("{call FAS_GL_ALLMONTH_ALLHEAD_ONE(?,?,?,?,?,?)}")%;
           	 	cs1=con.prepareCall("call FAS_GL_ALLMONTH_ALLHEAD_ONENEW(?::numeric,?::numeric,?::numeric,?::numeric,?::numeric,?::numeric)");
           	 System.out.println("FAS_GL_ALLMONTH_ALLHEAD_ONENEW");
		             cs1.setInt(1,accountingunit);
		             //cs1.setInt(2,accountingoffice);
		             cs1.setInt(2,year_from);
		             cs1.setInt(3,year_to);            
		             cs1.setInt(4,month_from);            
		             cs1.setInt(5,month_to);                         
		             cs1.registerOutParameter(6,java.sql.Types.NUMERIC);
		             cs1.setNull(6, java.sql.Types.NUMERIC);
		           //  System.out.println("before proc call FAS_GL_ALLMONTH_ALLACCHEAD_SAT");
		             cs1.execute();
		          //   System.out.println("after proc call FAS_GL_ALLMONTH_ALLACCHEAD_SAT");
		             //int errcode1=cs1.getInt(6);
		                int errcode1 = cs1.getBigDecimal(6).intValue();

		             
		          //   System.out.println("General Ledger Error Code from FAS_GL_ALLMONTH_ALLACCHEAD_SAT--->"+errcode1);
		             
		             if(!month_year.equalsIgnoreCase("more_cb_monthwise")) 
		            		 {
		            		 	if(Specifictype.equalsIgnoreCase("All"))
		            		 			{
		            		 				System.out.println("coming here for All AccHeads & more month option chosen using the procedure FAS_GL_ALLMONTH_ALLACCHEAD_SAT");
							               	 reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/GeneralLedger_UnitWiseModule/GeneralLedgerReportAll_banking_main_from_to_moreofficesingleunit.jasper"));
							            	 System.out.println("reportFile====>"+reportFile);
							               	 
							               	 if (!reportFile.exists())
							            		 throw new JRRuntimeException("File J not found. The report design must be compiled first.");
					
								              System.out.println("1 sss");  
								              JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
								              System.out.println("2 sss");
								              Map map=new HashMap();
								              
								              map.put("accountingunitid",accountingunit);
						//		              map.put("accountofficeid",accountingoffice);
								              map.put("cashbookyear_from",year_from);
								              map.put("cashbookyear_to",year_to);
								              map.put("monthvalue_from",monthInWords_from);
								              map.put("monthvalue_to",monthInWords_to);
								              								              
								             System.out.println("3 sss");
								             JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, con6);
								             System.out.println("4 sss");
		                              }
		            		 } 
           }
            
             /**
             * Report Calling 
             */
             
                   System.out.println("inside main ********** GeneralLedgerReport_two");
                   if(cmbAccHeadCode!=null)
                   {
                       try 
                       { 
                          accountheadcode=Integer.parseInt(cmbAccHeadCode);
                         }
                       catch(Exception e)
                        {
                           accountheadcode=0;
                         }
                    }
                   System.out.println("accountheadcode if selected:::::::"+accountheadcode); 
             String orgUnitName="";String ctxpath="";
             try {
             
             ps=con.prepareStatement("select ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=?");
             ps.setInt(1,accountingunit);
             rs1=ps.executeQuery();
             if(rs1.next()) {
                 orgUnitName=rs1.getString("ACCOUNTING_UNIT_NAME");
             }
             }
             catch (SQLException e) {
                 System.out.println("SQL Exception in unitname -->"+e);
             }
                    
             
             if(Specifictype.equalsIgnoreCase("All"))         
                    {
                       System.out.println("all");
                       if(month_year.equalsIgnoreCase("more_cb_monthwise")) 
                       {
                       
                       System.out.println("third option:::");
                           String path = getServletContext().getRealPath("/WEB-INF/subReport/GeneralLedgerReportAll_banking_main_from_to_monthwise.jasper");
                        System.out.println("path ::::"+path);
                           ctxpath = path.substring(0, path.lastIndexOf("GeneralLedgerReportAll_banking_main_from_to_monthwise.jasper"));
                        System.out.println("last::::"+ctxpath);
                        
                           Map map=new HashMap();
                           
                           map.put("accountingunitid",accountingunit);
                           map.put("accountofficeid",accountingoffice);
                           map.put("cashbookyear_from",year_from);
                           map.put("cashbookyear_to",year_to);
                           map.put("monthvalue_from",monthInWords_from);
                           map.put("monthvalue_to",monthInWords_to);
                           map.put("SUBREPORT_DIR", ctxpath);
                           map.put("orgUnitName",orgUnitName);
                        
                           JasperPrint jasperPrint = JasperFillManager.fillReport(path, map, con6);
                           
                           OutputStream outuputStream_1n = response.getOutputStream();
                           JRExporter exporter = null;
                           response.setContentType("application/pdf");
                           response.setHeader("Content-Disposition","attachment; filename=\"subledger_partyWise_bk.pdf\"");
                           exporter = new JRPdfExporter();
                           exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
                           exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outuputStream_1n);
                           exporter.exportReport();
                           outuputStream_1n.close();
                        
                       }
                      
                       else
                       {
                               if(accountingunit==5)
                               {
                                  reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/GeneralLedger_UnitWiseModule/GeneralLedgerReportAll_banking_main_from_to.jasper")); 
                               }
                               else
                               {
                            	   System.out.println("here itsssssssssssssssssss");
                            	   reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/GeneralLedger_UnitWiseModule/GeneralLedgerReportAll_banking_main_from_to_moreofficesingleunit.jasper"));
                            	   System.out.println("reportFile==>"+reportFile);
                               
                               }
                       
                               sql="SELECT a.CR_DR_INDICATOR, " +
                               "  a.RECEIPT_NO, " +
                               "  a.AMOUNT, " +
                               "  a.CR_AMOUNT, " +
                               "  a.DR_AMOUNT, " +
                               "  a.TYPE, " +
                               "  TO_CHAR(a.DATE_G,'DD-MM-YYYY') AS gl_date , " +
                               "  a.PARTICULARS, " +
                               "  a.MONTH_OPENING_BALANCE, " +
                               "  a.MONTH_OPENING_CR_DR_IND, " +
                               "  a.ACCOUNT_HEAD_CODE, " +
                               "  a.MONTH, " +
                               "  a.DOCTYPE, " +
                               "  a.YEAR, " +
                               "  a.ACCOUNTING_UNIT_ID, " +
                               "  a.ACCOUNTING_FOR_OFFICE_ID, " +
                               "  a.CASHBOOK_YEAR, " +
                               "  a.CASHBOOK_MONTH , " +
                               "  a.MONTH_IN_WORDS, " +
                               "  accunit.accounting_unit_name, " +
                               "  office.office_name, " +
                               "  acc.account_head_desc " +
                               "FROM FAS_GENERAL_LEDGER_REPORT_TMP a , " +
                               "  FAS_MST_ACCT_UNITS accunit, " +
                               "  COM_MST_OFFICES office , " +
                               "  COM_MST_ACCOUNT_HEADS acc " +
                               "WHERE a.ACCOUNTING_UNIT_ID    ='"+accountingunit+"' " +
                               "AND a.ACCOUNTING_FOR_OFFICE_ID='"+accountingoffice+"' " +
                               "AND office.office_id          ='"+accountingoffice+"' " +
                               "AND accunit.accounting_unit_id='"+accountingunit+"' " +
                               "AND acc.account_head_code     = a.account_head_code " +
                               "ORDER BY a.cashbook_year, " +
                               "  a.cashbook_month , " +
                               "  a.ACCOUNT_HEAD_CODE";
                       }
                    }
                   else if(Specifictype.equalsIgnoreCase("Specific"))         
                    {
                       System.out.println("Single Head between month****");
                    	if(accountingunit==5)
                           reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/GeneralLedger_UnitWiseModule/GeneralLedgerReport_banking_main_from_to.jasper")); 
                       else
                           reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/GeneralLedger_UnitWiseModule/GeneralLedgerReport_banking_main_from_to.jasper"));
                 	   System.out.println("reportFile==>"+reportFile);

                    	sql="SELECT a.CR_DR_INDICATOR, " +
                       "  a.RECEIPT_NO, " +
                       "  a.AMOUNT, " +
                       "  a.CR_AMOUNT, " +
                       "  a.DR_AMOUNT, " +
                       "  a.TYPE, " +
                       "  TO_CHAR(a.DATE_G,'DD-MM-YYYY') AS gl_date , " +
                       "  a.PARTICULARS, " +
                       "  a.MONTH_OPENING_BALANCE, " +
                       "  a.MONTH_OPENING_CR_DR_IND, " +
                       "  a.ACCOUNT_HEAD_CODE, " +
                       "  a.MONTH, " +
                       "  a.DOCTYPE, " +
                       "  a.YEAR, " +
                       "  a.ACCOUNTING_UNIT_ID, " +
                       "  a.ACCOUNTING_FOR_OFFICE_ID, " +
                       "  a.CASHBOOK_YEAR, " +
                       "  a.CASHBOOK_MONTH , " +
                       "  a.MONTH_IN_WORDS, " +
                       "  accunit.accounting_unit_name, " +
                       "  office.office_name, " +
                       "  acc.account_head_desc " +
                       "FROM FAS_GENERAL_LEDGER_REPORT_TMP a , " +
                       "  FAS_MST_ACCT_UNITS accunit, " +
                       "  COM_MST_OFFICES office , " +
                       "  COM_MST_ACCOUNT_HEADS acc " +
                       "WHERE a.ACCOUNTING_UNIT_ID    ='"+accountingunit+"' " +
                       "AND a.ACCOUNTING_FOR_OFFICE_ID='"+accountingoffice+"' " +
                       "AND office.office_id          ='"+accountingoffice+"' " +
                       "AND accunit.accounting_unit_id='"+accountingunit+"' " +
                       "AND acc.account_head_code     = a.account_head_code " +
                       "ORDER BY a.CASHBOOK_MONTH";
                    }
                    //***************changes done by Sathya 25Apr2012 ********************
                    
             
          
            if(!month_year.equalsIgnoreCase("more_cb_monthwise")) 
            {
             if (!reportFile.exists())
             throw new JRRuntimeException("File J not found. The report design must be compiled first.");

             System.out.println("1");  
             JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
             System.out.println("2");
             Map map=new HashMap();
             
             map.put("accountingunitid",accountingunit);
             map.put("accountofficeid",accountingoffice);
             map.put("cashbookyear_from",year_from);
             map.put("cashbookyear_to",year_to);
             map.put("monthvalue_from",monthInWords_from);
             map.put("monthvalue_to",monthInWords_to);
             map.put("orgUnitName",orgUnitName);
             
            System.out.println("3");
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, con6);
            System.out.println("4");
            
             if (rtype.equalsIgnoreCase("HTML"))
             {
                       response.setContentType("text/html");
                       response.setHeader ("Content-Disposition", "attachment;filename=\"GeneralLedgerReport.html\"");
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
             {
             
                 System.out.println("5");
                 byte buf[] = JasperExportManager.exportReportToPdf(jasperPrint);
                 System.out.println("6");                 
                 response.setContentType("application/pdf");
                 System.out.println("7");
                 response.setContentLength(buf.length);
                 System.out.println("7");
                 response.setHeader ("Content-Disposition", "attachment;filename=\"GeneralLedgerReport.pdf\"");
                 System.out.println("8");
                 OutputStream out = response.getOutputStream();
                 System.out.println("9");
                 out.write(buf, 0, buf.length);
                 System.out.println("10");
                 out.close();
             }
             else if (rtype.equalsIgnoreCase("EXCEL"))
             {
             
                    response.setContentType("application/vnd.ms-excel");
                    response.setHeader ("Content-Disposition", "attachment;filename=\"GeneralLedgerReport.csv\"");
                    ExcelConverter excel = new ExcelConverter();
          		   	//String path = getServletContext().getRealPath("/WEB-INF/Excel/SubLedgerReport.csv");
          		   	String path = "c:\\GeneralLedgerReport.csv";
          		   	excel.writeInCsvFormat(sql,path);

             }
             else if (rtype.equalsIgnoreCase("TXT"))
             {
             
               response.setContentType("text/plain");
               response.setHeader ("Content-Disposition", "attachment;filename=\"OfficeDetail.txt\"");
                    
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
             
           }  //dhana
                
         }
         catch (Exception ex)
         {
            String connectMsg ="Could not create the report " + ex.getMessage() + " " + 
            ex.getLocalizedMessage();
            System.out.println(connectMsg);
        }

    }
}
