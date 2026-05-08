package Servlets.FAS.FAS1.MonthEnd.servlets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver;


public class SubLedgerMainFormListServlet_CB extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);


    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {

        Connection con = null;


        ResultSet rss = null;
        PreparedStatement pss = null;

        try {

            ResourceBundle rb =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";

            String strDriver = rb.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rb.getString("Config.DSN");
            String strhostname = rb.getString("Config.HOST_NAME");
            String strportno = rb.getString("Config.PORT_NUMBER");
            String strsid = rb.getString("Config.SID");
            String strdbusername = rb.getString("Config.USER_NAME");
            String strdbpassword = rb.getString("Config.PASSWORD");

            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection

            Class.forName(strDriver.trim());
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
        } catch (Exception e) {
            System.out.println("Exception in connection...." + e);
        }

        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();


        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");

            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }


        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;
        ResultSet rs = null;
        ResultSet rs2 = null;
        ResultSet rs3 = null;
        PreparedStatement ps1 = null;
        ResultSet rs1 = null;


        int acOffId = 0;
        int AccId = 0;
        int OffCode = 0;
        String FinanYr = "";
        int SL_TYPE = 0;
        int SL_CODE = 0;
        String finanYR = "";
        String UptoCR = "";
        String UptoDB = "";
        String currCR = "";
        String currDB = "";
        String MONTH_OPENING_BALANCE = "";
        String MONTH_OPENING_BAL_DR_CR_IND = "";
        String CURRENT_MONTH_DEBIT = "";
        String CURRENT_MONTH_CREDIT = "";
        String MONTH_CLOSING_BALANCE = "";
        String MONTH_CLOSING_BAL_DR_CR_IND = "";
        String DateToBeDisplayed = "";
        String DateToBeDisplayed1 = "";


        int Major_id = 0;

        int Minor_id = 0;
        int sub1_id = 0;
        int sub2_id = 0;
        int CashbookYear = 0;
        int CashbookMonth = 0;
        int Sl_Type = 0;
        int Type_Code = 0;
        String SLDesc = "";
        String SLCodeDesc = "";
        int straccountHeadCode = 0;
        String AcHeadName = "";
        String strCommand = "";
        String xml = "";
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");


        try {
            System.out.println("..........................servlet opening balnacelist started.............");
            strCommand = request.getParameter("command");
            System.out.println("assign....." + strCommand);

        } catch (Exception ae) {
            System.out.println("first exception...." + ae);
        }

        try {
            try {
                straccountHeadCode =
                        Integer.parseInt(request.getParameter("accountHeadcode"));
            } catch (Exception e) {
                System.out.println("Exception in checkcode");
            }
        } catch (Exception ae) {
            System.out.println("first exception...." + ae);
        }

        try {
            AccId = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
        } catch (Exception que) {
            System.out.println("Exception in assigning values in add command...." +
                               que);
        }


        try {
            OffCode = Integer.parseInt(request.getParameter("comOffCode"));
            System.out.println(OffCode);
        } catch (Exception que) {
            System.out.println("Exception in assigning values in add command...." +
                               que);
        }

        //Change Date 30-Nov-2006 B//
        try {
            CashbookYear =
                    Integer.parseInt(request.getParameter("CashbookYear"));
            System.out.println(CashbookYear);
        } catch (Exception que) {
            System.out.println("Exception in assigning values in CashbookYear...." +
                               que);
        }
        try {
            CashbookMonth =
                    Integer.parseInt(request.getParameter("CashbookMonth"));
            System.out.println(CashbookMonth);
        } catch (Exception que) {
            System.out.println("Exception in assigning values in CashbookMonth...." +
                               que);
        }

        //End Change Date 30-Nov-2006 B//

        try {
            SL_TYPE = Integer.parseInt(request.getParameter("SL_TYPE"));
            System.out.println("SL_TYPE" + SL_TYPE);
        } catch (Exception que) {
            System.out.println("Exception in assigning values in SL_TYPE...." +
                               que);
        }
        try {
            SL_CODE = Integer.parseInt(request.getParameter("SL_CODE"));
            System.out.println("SL_CODE" + SL_CODE);
        } catch (Exception que) {
            System.out.println("Exception in assigning values in SL_CODE...." +
                               que);
        }
        FinanYr = request.getParameter("txtFinanYr");
        //    SLDesc=request.getParameter("cmbMas_SL_Type");
        //     System.out.println("cmbMas_SL_Type"+SLDesc);
        //     SLCodeDesc=request.getParameter("cmbMas_SL_Code");
        System.out.println("values list unit ,office, year");
        System.out.println(AccId);
        System.out.println(OffCode);
        System.out.println(FinanYr);

        if (strCommand.equalsIgnoreCase("fetch")) {
            xml = "<response><command>fetch</command>";
            //String sql="insert into TEST_STATE values(?,?)";

            // Sl_Type=Integer.parseInt(request.getParameter("sltypeval"));

            //  Type_Code=Integer.parseInt(request.getParameter("slcodeval"));
            //     System.out.println("sl type is "+Sl_Type+"sl type cod is ...."+Type_Code);
            System.out.println("inside fetch command");
            try {
                pss =
 con.prepareStatement("select SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,ACCOUNTING_UNIT_ID ,ACCOUNTING_FOR_OFFICE_ID ,FINANCIAL_YEAR ,ACCOUNT_HEAD_CODE,MONTH_CLOSING_BALANCE,MONTH_CLOSING_BAL_DR_CR_IND  from FAS_SUB_LEDGER_MASTER_CB where ACCOUNTING_UNIT_ID=? and  ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=? and year=? and month=?");
                // pss.setInt(1,straccountHeadCode);
                pss.setInt(1, AccId);
                pss.setInt(2, OffCode);
                pss.setString(3, FinanYr);
                pss.setInt(4, CashbookYear);
                pss.setInt(5, CashbookMonth);

                rss = pss.executeQuery();
                while (rss.next()) {
                    acOffId = rss.getInt("ACCOUNTING_FOR_OFFICE_ID");
                    System.out.println("Asset type code is*****************" +
                                       acOffId);

                    straccountHeadCode = rss.getInt("ACCOUNT_HEAD_CODE");

                    finanYR = rss.getString("FINANCIAL_YEAR");
                    System.out.println("Asset type code is*****************" +
                                       finanYR);
                    SL_TYPE = rss.getInt("SUB_LEDGER_TYPE_CODE");
                    System.out.println("SLTYPEis*****************" + SL_TYPE);
                    SL_CODE = rss.getInt("SUB_LEDGER_CODE");
                    System.out.println("SLCodeis*****************" + SL_CODE);
                    try {
                        ps1 =
 con.prepareStatement("select ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_CODE=?");
                        ps1.setInt(1, straccountHeadCode);
                        rs1 = ps1.executeQuery();
                        if (rs1.next()) {
                            AcHeadName = rs1.getString("ACCOUNT_HEAD_DESC");
                        }
                        ps1.close();
                        //rs1.close();
                    } catch (Exception que) {
                        System.out.println("exception in fetching the account head name....." +
                                           que);
                    }


                    try {
                        ps2 =
 con.prepareStatement("select SUB_LEDGER_TYPE_DESC from COM_MST_SL_TYPES where SUB_LEDGER_TYPE_CODE=?");
                        ps2.setInt(1, SL_TYPE);
                        rs2 = ps2.executeQuery();
                        if (rs2.next()) {
                            SLDesc = rs2.getString("SUB_LEDGER_TYPE_DESC");
                        }
                        ps2.close();
                        //rs1.close();
                    } catch (Exception que) {
                        System.out.println("exception in fetching the account subledger type....." +
                                           que);
                    }
                    try {
                        ps3 =
 con.prepareStatement("select SL_CODENAME from SL_TYPE_CODE_NAME_VIEW where SL_TYPE=? and SL_CODE=?");
                        ps3.setInt(1, SL_TYPE);
                        ps3.setInt(2, SL_CODE);
                        rs3 = ps3.executeQuery();
                        if (rs3.next()) {
                            SLCodeDesc = rs3.getString("SL_CODENAME");
                        }
                        ps3.close();
                        //rs1.close();
                    } catch (Exception que) {
                        System.out.println("exception in fetching the account subledger code....." +
                                           que);
                    }

                    MONTH_CLOSING_BALANCE =
                            rss.getString("MONTH_CLOSING_BALANCE");
                    System.out.println("CloseBal:" + MONTH_CLOSING_BALANCE);
                    MONTH_CLOSING_BAL_DR_CR_IND =
                            rss.getString("MONTH_CLOSING_BAL_DR_CR_IND");
                    System.out.println("CloseBalInd:" +
                                       MONTH_CLOSING_BAL_DR_CR_IND);

                }

                xml =
 xml + "<flag>success</flag><straccountHeadCode>" + straccountHeadCode +
   "</straccountHeadCode><AcHeadName>" + AcHeadName +
   "</AcHeadName><finanYR>" + finanYR + "</finanYR><acOffId>" + acOffId +
   "</acOffId><SL_TYPE_CODE>" + SL_TYPE + "</SL_TYPE_CODE><SL_TYPE>" + SLDesc +
   "</SL_TYPE><SL_CODE>" + SL_CODE + "</SL_CODE><SL_CODE_DESC>" + SLCodeDesc +
   "</SL_CODE_DESC><CloseBal>" + MONTH_CLOSING_BALANCE +
   "</CloseBal><CloseBalInd>" + MONTH_CLOSING_BAL_DR_CR_IND + "</CloseBalInd>";


            } catch (Exception que) {
                xml = xml + "<flag>failure</flag>";
                System.out.println("exception in fetching fas_opening balance details");
            }
            xml = xml + "</response>";
        }


        System.out.println("xml is:" + xml);
        out.write(xml);
        out.flush();
        out.close();

    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection connection=null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String queryStr="";
		String strCommand="report";
        try{
        	LoadDriver driver = new LoadDriver();
  			connection = driver.getConnection();                           
        }catch(Exception e){
            System.out.println("Exception in connection..."+e);
        }   
        try{
        	HttpSession session=request.getSession(false);
            if(session==null){
            	response.sendRedirect(request.getContextPath()+"/index.jsp");
              }                  
            }catch(Exception e){
                System.out.println("Redirect Error :"+e);
             }                
            int year = Integer.parseInt(request.getParameter("year"));
        	int month = Integer.parseInt(request.getParameter("txtCB_Month"));
        	int accUnitId = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
        	int officeId = Integer.parseInt(request.getParameter("cmbOffice_code"));
            int slType=0;
            int slCode=0;
        	int accHead =0;
        	if(request.getParameter("cmbAcHeadCode")!=null && !request.getParameter("cmbAcHeadCode").equals("")){
        		accHead = Integer.parseInt(request.getParameter("cmbAcHeadCode"));
        	}
        	if(request.getParameter("cmbMas_SL_type")!=null && !request.getParameter("cmbMas_SL_type").equals("")){
        		slType = Integer.parseInt(request.getParameter("cmbMas_SL_type"));
        	}
        	if(request.getParameter("cmbMas_SL_Code")!=null && !request.getParameter("cmbMas_SL_Code").equals("")){
        		slCode = Integer.parseInt(request.getParameter("cmbMas_SL_Code"));
        	}
        	System.out.println("slCode "+slCode);
        	if(accHead!=0){
            	queryStr = "and a.ACCOUNT_HEAD_CODE='"+accHead+"'";
            }
            if((accHead!=0)&&(slType!=0)){
            	queryStr = "and a.ACCOUNT_HEAD_CODE='"+accHead+"' and a.SUB_LEDGER_TYPE_CODE='"+slType+"'";
            }
            if((accHead!=0)&&(slType!=0)&&(slCode!=0)){
            	queryStr = "and a.ACCOUNT_HEAD_CODE='"+accHead+"' and a.SUB_LEDGER_TYPE_CODE='"+slType+"' and a.SUB_LEDGER_CODE='"+slCode+"'";
            }
                if(strCommand.equalsIgnoreCase("report")){
                	try{
                		Map map = new HashMap();
                    	Map<Integer,String> monthlist = new HashMap<Integer,String>();
                    	monthlist.put(1,"January");
                    	monthlist.put(2,"February");
                    	monthlist.put(3,"March");
                    	monthlist.put(4,"April");
                    	monthlist.put(5,"May");
                    	monthlist.put(6,"June");
                    	monthlist.put(7,"July");
                    	monthlist.put(8,"August");
                    	monthlist.put(9,"September");
                    	monthlist.put(10,"October");
                    	monthlist.put(11,"November");
                    	monthlist.put(12,"December");
                    	map.put("year", year);
                		map.put("month", month);
                		map.put("monthInWord", monthlist.get(month));
                		String sql="SELECT a.ACCOUNTING_UNIT_NAME AS ACCOUNTING_UNIT_NAME, " +
                		"  b.OFFICE_NAME AS OFFICE_NAME " +
                		"FROM " +
                		"  (SELECT ACCOUNTING_UNIT_ID, " +
                		"    ACCOUNTING_UNIT_NAME, " +
                		"    ACCOUNTING_UNIT_OFFICE_ID " +
                		"  FROM fas_mst_acct_units " +
                		"  WHERE ACCOUNTING_UNIT_ID=? " +
                		"  )a " +
                		"LEFT OUTER JOIN " +
                		"  ( SELECT OFFICE_ID,OFFICE_NAME FROM com_mst_offices WHERE OFFICE_ID=? " +
                		"  )b " +
                		"ON a.ACCOUNTING_UNIT_OFFICE_ID=b.OFFICE_ID";
                		preparedStatement = connection.prepareStatement(sql);
                		preparedStatement.setInt(1, accUnitId);
                		preparedStatement.setInt(2, officeId);
                		resultSet = preparedStatement.executeQuery();
                		if(resultSet.next()){
                			map.put("ACCOUNTING_UNIT_NAME", resultSet.getString("ACCOUNTING_UNIT_NAME"));
                			map.put("OFFICE_NAME", resultSet.getString("OFFICE_NAME"));
                		}
                		String s="",myQry="";
                		myQry=" select 									   "+		
                        "	  acc_head, 							   "+
                        "	  SUB_LEDGER_TYPE_CODE,					   "+
                        "	  SUB_LEDGER_CODE,						   "+
                        "	  MONTH_CLOSING_BALANCE,				   "+
                        "	  MONTH_CLOSING_BAL_DR_CR_IND,             "+
                        "	  ACCOUNT_HEAD_DESC,					   "+
                        "	  SUB_LEDGER_TYPE_DESC,					   "+
                        "	  SL_CODENAME,							   "+
                        "	  freeze_status							   "+
                        "	from									 "+
                        "	(											"+
                        "	select										"+ 
                        "	   a.ACCOUNT_HEAD_CODE as acc_head,			"+
                        "	   a.SUB_LEDGER_TYPE_CODE,					"+
                        "	   a.SUB_LEDGER_CODE,						"+
                        "	   a.MONTH_CLOSING_BALANCE,					"+
                        "	   a.MONTH_CLOSING_BAL_DR_CR_IND,			"+
                        "	   b.ACCOUNT_HEAD_DESC,						"+
                        "	   c.SUB_LEDGER_TYPE_DESC,					"+
                        "	   d.SL_CODENAME 							"+
                        "	from 										"+
                        "	   FAS_SUB_LEDGER_MASTER_CB a,				"+
                        "      COM_MST_ACCOUNT_HEADS b,					"+
                        "	   COM_MST_SL_TYPES c,						"+
                        "	   SL_TYPE_CODE_NAME_VIEW d					"+ 
                        "	where 										"+
                        "	      a.ACCOUNT_HEAD_CODE=b.ACCOUNT_HEAD_CODE			"+ 
                        "	  and a.SUB_LEDGER_TYPE_CODE=c.SUB_LEDGER_TYPE_CODE		"+ 
                        "	  and a.SUB_LEDGER_CODE=d.SL_CODE 						"+
                        "	  and c.SUB_LEDGER_TYPE_CODE=d.SL_TYPE					"+             	 
                        "	  and ACCOUNTING_UNIT_ID='"+accUnitId+"'				"+
                        "	  and YEAR='"+year+"'									"+
                        "	  and month='"+month+"'									"+
                        		queryStr +
                        "	),														"+
                        "	(														"+
                        "	    select count(sl_status) as freeze_status from fas_sl_cb_status			"+
                        "	    where accounting_unit_id='"+accUnitId+"'								"+
                        "	    and cashbook_month='"+month+"'											"+ 
                        "	    and cashbook_year='"+year+"'											"+
                        "	    and sl_status='Y'														"+ 
                        "	)Order By Acc_Head, Sub_Ledger_Type_Code,SUB_LEDGER_CODE  ";
                		s=request.getRealPath("/org/FAS/FAS1/MonthEnd/reports/SubLedger.jrxml");
                		String output="pdf";
                		JasperDesign jasperDesign = JasperManager.loadXmlDesign(s);
                		System.out.println(myQry);
                		JRDesignQuery query=new JRDesignQuery();
                		query.setText(myQry);
                		jasperDesign.setQuery(query);
                		JasperReport jasperReport = JasperManager.compileReport(jasperDesign);
                		JasperPrint jasperPrint = JasperManager.fillReport(jasperReport, map, connection);
                		ByteArrayOutputStream bout=new ByteArrayOutputStream();
    	            	if(output.equalsIgnoreCase("pdf")){
    			            	OutputStream os=response.getOutputStream();
    			            	response.setContentType("application/pdf");
    			            	response.setHeader ("Content-Disposition", "attachment;filename=\"SubLedger.pdf\"");
    			            	os.write(JasperManager.printReportToPdf(jasperPrint));
    			            	os.close();
    	            	}else if(output.equalsIgnoreCase("excel")){
    	            			response.setContentType("application/vnd.ms-excel");
    	            			response.setHeader ("Content-Disposition", "attachment;filename=\"SubLedger.xls\"");
    	            			OutputStream os=response.getOutputStream();
    	            			JRXlsExporter exporterXLS = new JRXlsExporter(); 
    	            			exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
    	            			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, bout);
    	            			exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
    	            			exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE);
    	            			exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
    	            			exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
    	            			exporterXLS.exportReport();
    	            		    byte[] buf=bout.toByteArray();
    	            		    os.write(buf);
    	            			os.close();
    	            		}else if(output.equalsIgnoreCase("html")){            		
    		            		 response.setContentType("text/html");
    		            		 response.setHeader ("Content-Disposition", "attachment;filename=\"SubLedger.html\"");
    		            		 PrintWriter out = response.getWriter();
    		            		 JRHtmlExporter exporter = new JRHtmlExporter();            		
    		            		 exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,  false);
    		            		 exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
    		            		 exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
    		            		 exporter.exportReport();
    		            		 out.flush();
    		            		 out.close();
    	            		}
                	}
                	catch(Exception e){
                		e.printStackTrace();
                	}	
                }
	}


}

