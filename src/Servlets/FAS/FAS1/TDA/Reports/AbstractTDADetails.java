package Servlets.FAS.FAS1.TDA.Reports;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

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

import Servlets.FAS.FAS1.CommonClass.FASCommon;
import Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver;

/**
 * Servlet implementation class Leave_type_Mst
 */
@SuppressWarnings("deprecation")
public class AbstractTDADetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AbstractTDADetails() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("fileType "+request.getParameter("fileType"));
		Connection connection=null;
		int cashYear = 0;
		int cashMonth = 0;
		int accountingUnitId = 0;
		int accountingOfficeId = 0;
		String tdatcaType="",tdatype="",tcatype="",fromDate="",toDate="",tdatype1="";		
		FASCommon fasCommon = new FASCommon();
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
                String output="";                
                try {
                    cashYear = Integer.parseInt(request.getParameter("txtCB_Year"));
                    cashMonth = Integer.parseInt(request.getParameter("txtCB_Month"));
                    accountingUnitId = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
                    accountingOfficeId = Integer.parseInt(request.getParameter("cmbOffice_code"));
                    tdatcaType = request.getParameter("tdaortca");
                    fromDate=fasCommon.dateConvertVar(request.getParameter("txtFrom_date"));
                    toDate=fasCommon.dateConvertVar(request.getParameter("txtTo_date"));
                    
                }catch(Exception e) {
                    System.out.println("Exception in assigning..."+e);
                }                
                System.out.println("fromDate "+fromDate);
                System.out.println("toDate "+toDate);
               /*if(tdatcaType.equalsIgnoreCase("tda")){
            	   tdatype="TDAO";
            	   tcatype="TDACB";
               }else{
            	   tdatype="TCAO";
            	   tcatype="TCACB";
               }*/
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
                	if(request.getParameter("tdaortca").equalsIgnoreCase("tda")){
                		 tdatype="TDAO";
                  	   	 tcatype="TDACB";
                  	   	 tdatype1="TDAA";
                		map.put("tda1", "TDA Originated by you");
                		map.put("tda12", "TDA Originated to you by others");
                		map.put("tda2", "TDA Originated by you and accepted by others");
                		map.put("tda3", "TDA accepted against the month by others");
                		map.put("tda8", "TDA accepted against the month by you");
                		map.put("tda4", "TDA accepted during the month by others");
                		map.put("tda9", "TDA accepted during the month by you");
                		map.put("tda5", "TDA Pending for Acceptance by others");
                		map.put("tda11", "TDA Pending for Acceptance by you");
                		map.put("tda6", "TDA originated by you and rejected by others");
                		map.put("tda7", "TDA Rejected by you originated by others");
                		map.put("caption", "TDA");
                	}else if(request.getParameter("tdaortca").equalsIgnoreCase("tca")){
                		tdatype="TCAO";
                 	   	tcatype="TCACB";
                 	   	tdatype1="TCAA";
                 	   	map.put("tda1", "TCA Originated by you");
	               		map.put("tda12", "TCA Originated to you by others");
	               		map.put("tda2", "TCA Originated by you and accepted by others");
	               		map.put("tda3", "TCA accepted against the month by others");
	               		map.put("tda8", "TCA accepted against the month by you");
	               		map.put("tda4", "TCA accepted during the month by others");
	               		map.put("tda9", "TCA accepted during the month by you");
	               		map.put("tda5", "TCA Pending for Acceptance by others");
	               		map.put("tda11", "TCA Pending for Acceptance by you");
	               		map.put("tda6", "TCA originated by you and rejected by others");
	               		map.put("tda7", "TCA Rejected by you originated by others");
	               		map.put("caption", "TCA");
                	}
                	map.put("year_from", cashYear);
            		map.put("month_from", monthlist.get(cashMonth));
            		String myQry="";
                	try{
                		String s = "";            		
		                	myQry="SELECT MAX(NVL(orgbyu,0))      AS orgbyu, " +
		                	"  MAX(NVL(orgbuo,0))           AS orgbuo, " +
		                	"  MAX(NVL(againstmonth,0))     AS againstmonth, " +
		                	"  MAX(NVL(durmont,0))          AS durmont, " +
		                	"  MAX(NVL(pendacept,0))        AS pendacept, " +
		                	"  MAX(NVL(orgrej,0))           AS orgrej, " +
		                	"  MAX(NVL(Regorg,0))           AS Regorg, " +
		                	"  MAX(NVL(Youbyoth,0))         AS Youbyoth, " +
		                	"  MAX(NVL(Againstmonthbyou,0)) AS Againstmonthbyou, " +
		                	"  MAX(NVL(Durmontbyou,0))      AS Durmontbyou, " +
		                	"  MAX(NVL(pendaceptbyou,0))    AS pendaceptbyou, " +
		                	"  b.ACCOUNTING_UNIT_NAME       AS accountingUnitName " +
		                	"FROM " +
		                	"  (SELECT COUNT(*) AS orgbyu, " +
		                	"    COUNT( " +
		                	"    CASE " +
		                	"      WHEN ACCEPTANCE_STATUS ='Y' " +
		                	"      AND ACCEPTING_SLNO    IS NOT NULL " +
		                	"      THEN 0 " +
		                	"      ELSE ACCEPTING_SLNO " +
		                	"    END) AS orgbuo, " +
		                	"    accounting_unit_id " +
		                	"  FROM fas_tda_tca_raised_mst " +
		                	"  WHERE accounting_unit_id    ='"+accountingUnitId+"' " +
		                	"  AND accounting_for_office_id='"+accountingOfficeId+"' ";
		                	if(request.getParameter("command").equalsIgnoreCase("bymonth")){
	                			myQry+=" AND cashbook_month ='"+cashMonth+"'"+
	        				   	  		" AND cashbook_year ='"+cashYear+"'";
	                			map.put("duration", "For "+monthlist.get(cashMonth)+" "+cashYear);
	        				   }else{				   				   	
	        					   myQry+=" AND VOUCHER_DATE BETWEEN ('"+fromDate+"') AND ('"+toDate+"')" ;
	        					   map.put("duration", "From "+request.getParameter("txtFrom_date")+" To "+request.getParameter("txtTo_date"));
	        				   }
		                	myQry+="  AND status           ='L' " +
		                	"  AND (tda_or_tca             ='"+tdatype+"' " +
	                		"  OR tda_or_tca               ='"+tcatype+"') " +
		                	"  GROUP BY accounting_unit_id " +
		                	"  )a " +
		                	"LEFT OUTER JOIN " +
		                	"  (SELECT accounting_unit_id,ACCOUNTING_UNIT_NAME FROM FAS_MST_ACCT_UNITS " +
		                	"  ) b " +
		                	"ON a.ACCOUNTING_UNIT_ID=b.accounting_unit_id " +
		                	"LEFT OUTER JOIN " +
		                	"  (SELECT COUNT ( " +
		                	"    CASE " +
		                	"      WHEN (EXTRACT(MONTH FROM VOUCHER_DATE)='"+cashMonth+"' " +
		                	"      AND EXTRACT(YEAR FROM VOUCHER_DATE)   ='"+cashYear+"') " +
		                	"      THEN ACCEPTING_SLNO " +
		                	"      ELSE 0 " +
		                	"    END) AS againstmonth, " +
		                	"    accounting_unit_id " +
		                	"  FROM fas_tda_tca_raised_mst " +
		                	"  WHERE accounting_unit_id            ='"+accountingUnitId+"' " +
		                	"  AND accounting_for_office_id        ='"+accountingOfficeId+"' ";		                	
		                	if(request.getParameter("command").equalsIgnoreCase("bymonth")){
	                			myQry+=" AND EXTRACT(MONTH FROM VOUCHER_DATE)='"+cashMonth+"'"+
	        				   	  		" AND EXTRACT(YEAR FROM VOUCHER_DATE) ='"+cashYear+"'";
	                			map.put("duration", "For "+monthlist.get(cashMonth)+" "+cashYear);
	        				   }else{				   				   	
	        					   myQry+=" AND VOUCHER_DATE BETWEEN ('"+fromDate+"') AND ('"+toDate+"')" ;
	        					   map.put("duration", "From "+request.getParameter("txtFrom_date")+" To "+request.getParameter("txtTo_date"));
	        				   }
		                		myQry+="  AND status  				   ='L' ";
		                	if(request.getParameter("command").equalsIgnoreCase("bymonth")){
	                			myQry+=" AND cashbook_month ='"+cashMonth+"'"+
	        				   	  		" AND cashbook_year ='"+cashYear+"'";
	                			map.put("duration", "For "+monthlist.get(cashMonth)+" "+cashYear);
	        				   }else{				   				   	
	        					   myQry+=" AND VOUCHER_DATE BETWEEN ('"+fromDate+"') AND ('"+toDate+"')" ;
	        					   map.put("duration", "From "+request.getParameter("txtFrom_date")+" To "+request.getParameter("txtTo_date"));
	        				 }
		                	myQry+=" AND (tda_or_tca             ='"+tdatype+"' " +
		                		"  OR tda_or_tca               ='"+tcatype+"') " +
		                	"  GROUP BY accounting_unit_id " +
		                	"  )c " +
		                	"ON a.accounting_unit_id=c.accounting_unit_id " +
		                	"LEFT OUTER JOIN " +
		                	"  (SELECT COUNT ( " +
		                	"    CASE " +
		                	"      WHEN (EXTRACT(MONTH FROM ACCEPTING_DATE)='"+cashMonth+"' " +
		                	"      AND EXTRACT(YEAR FROM ACCEPTING_DATE)   ='"+cashYear+"') " +
		                	"      THEN ACCEPTING_SLNO " +
		                	"      ELSE 0 " +
		                	"    END) AS durmont, " +
		                	"    accounting_unit_id " +
		                	"  FROM fas_tda_tca_raised_mst " +
		                	"  WHERE accounting_unit_id              ='"+accountingUnitId+"' " +
		                	"  AND accounting_for_office_id          ='"+accountingOfficeId+"' ";
		                	if(request.getParameter("command").equalsIgnoreCase("bymonth")){
	                			myQry+=" AND EXTRACT(MONTH FROM ACCEPTING_DATE)='"+cashMonth+"'"+
	        				   	  		" AND EXTRACT(YEAR FROM ACCEPTING_DATE) ='"+cashYear+"'";
	                			map.put("duration", "For "+monthlist.get(cashMonth)+" "+cashYear);
	        				   }else{				   				   	
	        					   myQry+=" AND VOUCHER_DATE BETWEEN ('"+fromDate+"') AND ('"+toDate+"')" ;
	        					   map.put("duration", "From "+request.getParameter("txtFrom_date")+" To "+request.getParameter("txtTo_date"));
	        				   }
		                	myQry+="  AND status                     ='L' ";
		                	if(request.getParameter("command").equalsIgnoreCase("bymonth")){
	                			myQry+=" AND cashbook_month ='"+cashMonth+"'"+
	        				   	  		" AND cashbook_year ='"+cashYear+"'";
	                			map.put("duration", "For "+monthlist.get(cashMonth)+" "+cashYear);
	        				   }else{				   				   	
	        					   myQry+=" AND VOUCHER_DATE BETWEEN ('"+fromDate+"') AND ('"+toDate+"')" ;
	        					   map.put("duration", "From "+request.getParameter("txtFrom_date")+" To "+request.getParameter("txtTo_date"));
	        				 }
		                	myQry+="  AND (tda_or_tca             ='"+tdatype+"' " +
		                		"  OR tda_or_tca               ='"+tcatype+"') " +
		                	"  GROUP BY accounting_unit_id " +
		                	"  )d " +
		                	"ON a.accounting_unit_id=d.accounting_unit_id " +
		                	"LEFT OUTER JOIN " +
		                	"  (SELECT COUNT(*) AS pendacept, " +
		                	"    accounting_unit_id " +
		                	"  FROM fas_tda_tca_raised_mst " +
		                	"  WHERE accounting_unit_id    ='"+accountingUnitId+"' " +
		                	"  AND accounting_for_office_id='"+accountingOfficeId+"' ";
		                	if(request.getParameter("command").equalsIgnoreCase("bymonth")){
	                			myQry+=" AND cashbook_month ='"+cashMonth+"'"+
	        				   	  		" AND cashbook_year ='"+cashYear+"'";
	                			map.put("duration", "For "+monthlist.get(cashMonth)+" "+cashYear);
	        				   }else{				   				   	
	        					   myQry+=" AND VOUCHER_DATE BETWEEN ('"+fromDate+"') AND ('"+toDate+"')" ;
	        					   map.put("duration", "From "+request.getParameter("txtFrom_date")+" To "+request.getParameter("txtTo_date"));
	        				 }
		                	myQry+="  AND status           ='L' " +
		                	"  AND ACCEPTANCE_STATUS      IS NULL " +
		                	"  AND (tda_or_tca             ='"+tdatype+"' " +
	                		"  OR tda_or_tca               ='"+tcatype+"') " +
		                	"  GROUP BY accounting_unit_id " +
		                	"  )e " +
		                	"ON a.accounting_unit_id=e.accounting_unit_id " +
		                	"LEFT OUTER JOIN " +
		                	"  (SELECT COUNT(*) AS orgrej, " +
		                	"    accounting_unit_id " +
		                	"  FROM fas_tda_tca_raised_mst " +
		                	"  WHERE accounting_unit_id    ='"+accountingUnitId+"' " +
		                	"  AND accounting_for_office_id='"+accountingOfficeId+"' ";
		                	if(request.getParameter("command").equalsIgnoreCase("bymonth")){
	                			myQry+=" AND cashbook_month ='"+cashMonth+"'"+
	        				   	  		" AND cashbook_year ='"+cashYear+"'";
	                			map.put("duration", "For "+monthlist.get(cashMonth)+" "+cashYear);
	        				   }else{				   				   	
	        					   myQry+=" AND VOUCHER_DATE BETWEEN ('"+fromDate+"') AND ('"+toDate+"')" ;
	        					   map.put("duration", "From "+request.getParameter("txtFrom_date")+" To "+request.getParameter("txtTo_date"));
	        				 }
		                	myQry +="  AND status          ='L' " +
		                	"  AND ACCEPTANCE_STATUS      IN('N','R') " +
		                	"  AND (tda_or_tca             ='"+tdatype+"' " +
	                		"  OR tda_or_tca               ='"+tcatype+"') " +
		                	"  GROUP BY accounting_unit_id " +
		                	"  )f " +
		                	"ON a.accounting_unit_id=f.accounting_unit_id " +
		                	"LEFT OUTER JOIN " +
		                	"  (SELECT COUNT(*) AS regorg, " +
		                	"    accounting_unit_id " +
		                	"  FROM fas_tda_tca_raised_mst " +
		                	"  WHERE TRF_ACCOUNTING_UNIT_ID ='"+accountingUnitId+"' ";
		                	if(request.getParameter("command").equalsIgnoreCase("bymonth")){
	                			myQry+=" AND cashbook_month ='"+cashMonth+"'"+
	        				   	  		" AND cashbook_year ='"+cashYear+"'";
	                			map.put("duration", "For "+monthlist.get(cashMonth)+" "+cashYear);
	        				   }else{				   				   	
	        					   myQry+=" AND VOUCHER_DATE BETWEEN ('"+fromDate+"') AND ('"+toDate+"')" ;
	        					   map.put("duration", "From "+request.getParameter("txtFrom_date")+" To "+request.getParameter("txtTo_date"));
	        				 }
		                	myQry +="  AND status           ='L' " +
		                	"  AND ACCEPTANCE_STATUS       IN('N','R') " +
		                	"  AND (tda_or_tca             ='"+tdatype+"' " +
	                		"  OR tda_or_tca               ='"+tcatype+"') " +
		                	"  GROUP BY accounting_unit_id " +
		                	"  )g " +
		                	"ON A.Accounting_Unit_Id=G.Accounting_Unit_Id " +
		                	"LEFT OUTER JOIN " +
		                	"  (SELECT COUNT(*) AS youbyoth, " +
		                	"    TRF_ACCOUNTING_UNIT_ID " +
		                	"  FROM Fas_Tda_Tca_Raised_Mst " +
		                	"  WHERE TRF_ACCOUNTING_UNIT_ID ='"+accountingUnitId+"' ";
		                	if(request.getParameter("command").equalsIgnoreCase("bymonth")){
	                			myQry+=" AND cashbook_month ='"+cashMonth+"'"+
	        				   	  		" AND cashbook_year ='"+cashYear+"'";
	                			map.put("duration", "For "+monthlist.get(cashMonth)+" "+cashYear);
	        				   }else{				   				   	
	        					   myQry+=" AND VOUCHER_DATE BETWEEN ('"+fromDate+"') AND ('"+toDate+"')" ;
	        					   map.put("duration", "From "+request.getParameter("txtFrom_date")+" To "+request.getParameter("txtTo_date"));
	        				 }
		                	myQry +="  AND Status                  ='L' " +
		                	"  AND (tda_or_tca             ='"+tdatype1+"' " +
	                		"  OR tda_or_tca               ='"+tcatype+"') " +
		                	"  GROUP BY Trf_Accounting_Unit_Id " +
		                	"  )H " +
		                	"ON A.Accounting_Unit_Id=H.Trf_Accounting_Unit_Id " +
		                	"LEFT OUTER JOIN " +
		                	"  (SELECT COUNT (*) AS againstmonthbyou, " +
		                	"    Trf_Accounting_Unit_Id " +
		                	"  FROM fas_tda_tca_raised_mst " +		                	
		                	"  WHERE TRF_ACCOUNTING_UNIT_ID ='"+accountingUnitId+"' ";
		                	if(request.getParameter("command").equalsIgnoreCase("bymonth")){
	                			myQry+=" AND EXTRACT(MONTH FROM VOUCHER_DATE)='"+cashMonth+"'"+
	        				   	  		" AND EXTRACT(YEAR FROM VOUCHER_DATE)='"+cashYear+"'";
	                			map.put("duration", "For "+monthlist.get(cashMonth)+" "+cashYear);
	        				   }else{				   				   	
	        					   myQry+=" AND VOUCHER_DATE BETWEEN ('"+fromDate+"') AND ('"+toDate+"')" ;
	        					   map.put("duration", "From "+request.getParameter("txtFrom_date")+" To "+request.getParameter("txtTo_date"));
	        				 }
		                	myQry +="  AND status                          ='L' " +
		                	"  AND cashbook_month                  ='"+cashMonth+"' " +
		                	"  AND cashbook_year                   ='"+cashYear+"' " +
		                	"  AND (tda_or_tca             ='"+tdatype1+"' " +
	                		"  OR tda_or_tca               ='"+tcatype+"') " +
		                	"  GROUP BY Trf_Accounting_Unit_Id " +
		                	"  )I " +
		                	"ON A.Accounting_Unit_Id=i.Trf_Accounting_Unit_Id " +
		                	"LEFT OUTER JOIN " +
		                	"  (SELECT COUNT (*) AS durmontbyou, " +
		                	"    Trf_Accounting_Unit_Id " +
		                	"  FROM fas_tda_tca_raised_mst " +	                	
		                	"  WHERE TRF_ACCOUNTING_UNIT_ID ='"+accountingUnitId+"' ";
		                	if(request.getParameter("command").equalsIgnoreCase("bymonth")){
	                			myQry+=" AND EXTRACT(MONTH FROM VOUCHER_DATE)='"+cashMonth+"'"+
	        				   	  		" AND EXTRACT(YEAR FROM VOUCHER_DATE)='"+cashYear+"'";
	                			map.put("duration", "For "+monthlist.get(cashMonth)+" "+cashYear);
	        				   }else{				   				   	
	        					   myQry+=" AND VOUCHER_DATE BETWEEN ('"+fromDate+"') AND ('"+toDate+"')" ;
	        					   map.put("duration", "From "+request.getParameter("txtFrom_date")+" To "+request.getParameter("txtTo_date"));
	        				 }
		                	myQry +="  AND status                          ='L' " +
		                	"  AND cashbook_month                  ='"+cashMonth+"' " +
		                	"  AND cashbook_year                   ='"+cashYear+"' " +
		                	"  AND (tda_or_tca             ='"+tdatype1+"' " +
	                		"  OR tda_or_tca               ='"+tcatype+"') " +
		                	"  GROUP BY Trf_Accounting_Unit_Id " +
		                	"  )J " +
		                	"ON A.Accounting_Unit_Id=J.Trf_Accounting_Unit_Id " +
		                	"LEFT OUTER JOIN " +
		                	"  (SELECT COUNT(*) AS pendaceptbyou, " +
		                	"    Trf_Accounting_Unit_Id " +
		                	"  FROM fas_tda_tca_raised_mst " +
		                	"  WHERE TRF_ACCOUNTING_UNIT_ID ='"+accountingUnitId+"' ";
		                	if(request.getParameter("command").equalsIgnoreCase("bymonth")){
	                			myQry+=" AND cashbook_month ='"+cashMonth+"'"+
	        				   	  		" AND cashbook_year ='"+cashYear+"'";
	                			map.put("duration", "For "+monthlist.get(cashMonth)+" "+cashYear);
	        				   }else{				   				   	
	        					   myQry+=" AND VOUCHER_DATE BETWEEN ('"+fromDate+"') AND ('"+toDate+"')" ;
	        					   map.put("duration", "From "+request.getParameter("txtFrom_date")+" To "+request.getParameter("txtTo_date"));
	        				 }
		                	myQry +="  AND status                   ='L' " +
		                	"  AND ACCEPTANCE_STATUS       IS NULL " +
		                	"  AND (tda_or_tca             ='"+tdatype1+"' " +
	                		"  OR tda_or_tca               ='"+tcatype+"') " +
		                	"  GROUP BY Trf_Accounting_Unit_Id " +
		                	"  )K " +
		                	"ON A.Accounting_Unit_Id=k.Trf_Accounting_Unit_Id " +
		                	"GROUP BY B.Accounting_Unit_Name";
                		s=request.getRealPath("/org/FAS/FAS1/Reports/TDA/jasper/Abstract_report_of_TDA_Details.jrxml");
                		//output=request.getParameter("fileType");
                		JasperDesign jasperDesign = JasperManager.loadXmlDesign(s);
                		System.out.println(myQry);
                		JRDesignQuery query=new JRDesignQuery();
                		query.setText(myQry);
                		jasperDesign.setQuery(query);
                		JasperReport jasperReport = JasperManager.compileReport(jasperDesign);
                		JasperPrint jasperPrint = JasperManager.fillReport(jasperReport, map, connection);
                		ByteArrayOutputStream bout=new ByteArrayOutputStream();
                		output="pdf";
    	            	if(output.equalsIgnoreCase("pdf")){
    			            	OutputStream os=response.getOutputStream();
    			            	response.setContentType("application/pdf");
    			            	response.setHeader ("Content-Disposition", "attachment;filename=\"Abstract_TDA_Details.pdf\"");
    			            	os.write(JasperManager.printReportToPdf(jasperPrint));
    			            	os.close();
    	            	}else if(output.equalsIgnoreCase("excel")){
    	            			response.setContentType("application/vnd.ms-excel");
    	            			response.setHeader ("Content-Disposition", "attachment;filename=\"Abstract_TDA_Details.xls\"");
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
    		            		 response.setHeader ("Content-Disposition", "attachment;filename=\"Abstract_TDA_Details.html\"");
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

