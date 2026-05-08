package Servlets.FAS.FAS1.MIS.servlets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
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

import Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver;

/**
 * Servlet implementation class Leave_type_Mst
 */
@SuppressWarnings("deprecation")
public class MISFundProgressReport extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MISFundProgressReport() {
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
		System.out.println("fileType "+request.getParameter("fileType"));
		Connection connection=null;
		int cashYear_from = 0;
		int cashMonth_from = 0;
		int cashYear_to = 0;
		int cashMonth_to = 0;
		String strCommand="";
		String urbanrural="";
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
                    output=request.getParameter("fileType");
                    cashYear_from = Integer.parseInt(request.getParameter("txtCB_Year_from"));
                    cashMonth_from = Integer.parseInt(request.getParameter("txtCB_Month_from"));
                    cashYear_to = Integer.parseInt(request.getParameter("txtCB_Year_to"));
                    cashMonth_to = Integer.parseInt(request.getParameter("txtCB_Month_to"));
                    strCommand = request.getParameter("command");
                    urbanrural = request.getParameter("urbanrural");
                }catch(Exception e) {
                    System.out.println("Exception in assigning..."+e);
                }
                if(strCommand.equalsIgnoreCase("report")){
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
                	map.put("year_from", cashYear_from);
                	map.put("year_to", cashYear_to);
            		map.put("month_from", monthlist.get(cashMonth_from));
            		map.put("month_to", monthlist.get(cashMonth_to));
                	try{
                		String s = "",myQry="";            		
                		myQry="SELECT b.SCH_MAIN_CATEGORY_DESC AS SCH_MAIN_CATEGORY_DESC, " +
                		"  c.SCH_SUB_CATEGORY_DESC       AS SCH_SUB_CATEGORY_DESC, " +
                		"  a.ACCOUNT_HEAD_CODE           AS ACCOUNT_HEAD_CODE, " +
                		"  a.fund                        AS fund, " +
                		"  d.expendfm                    AS expendfm, " +
                		"  a.expend                      AS expend " +
                		"FROM " +
                		"  (SELECT t.ACCOUNT_HEAD_CODE AS ACCOUNT_HEAD_CODE, " +
                		"    t1.SCH_MAIN_CATEGORY_CODE, " +
                		"    t1.SCH_SUB_CATEGORY_CODE, " +
                		"    t1.account_head_code AS account_head, " +
                		"    CASE " +
                		"      WHEN (t1.FUND_OR_EXP='f') " +
                		"      THEN NVL(SUM(t.DEBIT_CLOSING_BALANCE)+SUM(t.CREDIT_CLOSING_BALANCE),0) " +
                		"      ELSE 0 " +
                		"    END AS fund, " +
                		"    CASE " +
                		"      WHEN (t1.FUND_OR_EXP='e') " +
                		"      THEN NVL(SUM(t.CURRENT_MONTH_CREDIT)+SUM(t.CURRENT_MONTH_DEBIT),0) " +
                		"      ELSE 0 " +
                		"    END AS expend " +
                		"  FROM FAS_TRIAL_BALANCE t " +
                		"  LEFT OUTER JOIN FAS_WE_FUND_AC_HEAD_MAP_TRN t1 " +
                		"  ON t.ACCOUNT_HEAD_CODE=t1.account_head_code " +
                		"  WHERE " +
                		"  to_date((t.Cashbook_month ||'-'||t.Cashbook_Year),'mm-yyyy') BETWEEN to_date("+cashMonth_from+"||'-'||"+cashYear_from+",'mm-yyyy')AND to_date("+cashMonth_to+" ||'-' ||"+cashYear_to+",'mm-yyyy')" +
                	//	"t.CASHBOOK_YEAR BETWEEN ('"+cashYear_from+"') AND ('"+cashYear_to+"') " +
                	//	"  AND t.CASHBOOK_MONTH BETWEEN ('"+cashMonth_from+"') AND ('"+cashMonth_to+"') " +
                		"  GROUP BY t.ACCOUNT_HEAD_CODE, " +
                		"    t1.fund_or_exp, " +
                		"    t1.SCH_MAIN_CATEGORY_CODE, " +
                		"    t1.SCH_SUB_CATEGORY_CODE, " +
                		"    t1.account_head_code " +
                		"  HAVING COUNT(FUND_OR_EXP)>1 " +
                		"  )a " +
                		"LEFT OUTER JOIN " +
                		"  (SELECT SCH_MAIN_CATEGORY_CODE, " +
                		"    SCH_MAIN_CATEGORY_DESC " +
                		"  FROM FAS_WE_SCH_MAIN_CATEGORY " +
                		"  )b " +
                		"ON a.SCH_MAIN_CATEGORY_CODE=b.SCH_MAIN_CATEGORY_CODE " +
                		"LEFT OUTER JOIN " +
                		"  (SELECT SCH_MAIN_CATEGORY_CODE, " +
                		"    SCH_SUB_CATEGORY_CODE, " +
                		"    SCH_SUB_CATEGORY_DESC " +
                		"  FROM FAS_WE_SCH_SUB_CATEGORY " +
                		"  )c " +
                		"ON a.SCH_MAIN_CATEGORY_CODE=c.SCH_MAIN_CATEGORY_CODE " +
                		"AND a.SCH_SUB_CATEGORY_CODE=c.SCH_SUB_CATEGORY_CODE " +
                		"LEFT OUTER JOIN " +
                		"  (SELECT s.ACCOUNT_HEAD_CODE AS accounthead, " +
                		"    v.expendfm                AS expendfm " +
                		"  FROM " +
                		"    (SELECT ACCOUNT_HEAD_CODE " +
                		"    FROM FAS_WE_FUND_AC_HEAD_MAP_TRN " +
                		"    WHERE FUND_OR_EXP='e' " +
                		"    )s " +
                		"  LEFT OUTER JOIN " +
                		"    (SELECT ACCOUNT_HEAD_CODE, " +
                		"      SUM(CURRENT_MONTH_CREDIT)+SUM(CURRENT_MONTH_DEBIT) AS expendfm " +
                		"    FROM FAS_TRIAL_BALANCE " +
                		"    WHERE CASHBOOK_MONTH ='"+cashMonth_from+"' " +
                		"    GROUP BY ACCOUNT_HEAD_CODE " +
                		"    )v " +
                		"  ON s.ACCOUNT_HEAD_CODE=v.ACCOUNT_HEAD_CODE " +
                		"  )d ON a.account_head  =d.accounthead " +
                		"ORDER BY a.SCH_MAIN_CATEGORY_CODE";
                		
                		System.out.println("myQry:::"+myQry);
                		
                		s=request.getRealPath("/org/FAS/FAS1/MIS/jaspers/Fin_Progress_Report.jrxml");
                		output=request.getParameter("fileType");
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
    			            	response.setHeader ("Content-Disposition", "attachment;filename=\"Fin_Progress_Report.pdf\"");
    			            	os.write(JasperManager.printReportToPdf(jasperPrint));
    			            	os.close();
    	            	}else if(output.equalsIgnoreCase("excel")){
    	            			response.setContentType("application/vnd.ms-excel");
    	            			response.setHeader ("Content-Disposition", "attachment;filename=\"Fin_Progress_Report.xls\"");
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
    		            		 response.setHeader ("Content-Disposition", "attachment;filename=\"Fin_Progress_Report.html\"");
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
                }else if(strCommand.equalsIgnoreCase("finReport")){
                	String s = "",myQry="";
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
                	map.put("year_from", cashYear_from);
                	map.put("year_to", cashYear_to);
            		map.put("month_from", monthlist.get(cashMonth_from));
            		map.put("month_to", monthlist.get(cashMonth_to));
                	//Map<String, String> map = new HashMap<String, String>();
            		if(urbanrural.equalsIgnoreCase("R")){
            			try{ 
            				map.put("reporttype", "Rural");
                    		myQry="SELECT DISTINCT t.REGION_OFFICE_ID AS REGION_OFFICE_ID, " +
                    		"  t.officename                     AS regionname, " +
                    		"  t.DISTRICT_CODE                  AS DISTRICT_CODE, " +
                    		"  t.DISTRICT_NAME                  AS DISTRICT_NAME, " +
                    		"  t.OFFICE_ID                      AS OFFICE_ID, " +
                    		"  t.ACCOUNTING_UNIT_ID             AS ACCOUNTING_UNIT_ID, " +
                    		"  t.ACCOUNTING_UNIT_NAME           AS ACCOUNTING_UNIT_NAME, " +
                    		"  MAX( " +
                    		"  CASE " +
                    		"    WHEN t.ARWSP IS NOT NULL " +
                    		"    THEN t.ARWSP " +
                    		"  END ) AS arwsp, " +
                    		"  MAX( " +
                    		"  CASE " +
                    		"    WHEN t.ARWSP_SC IS NOT NULL " +
                    		"    THEN t.ARWSP_SC " +
                    		"  END ) AS arwspscst, " +
                    		"  MAX( " +
                    		"  CASE " +
                    		"    WHEN t.MNP IS NOT NULL " +
                    		"    THEN t.MNP " +
                    		"  END ) AS mnp, " +
                    		"  MAX( " +
                    		"  CASE " +
                    		"    WHEN t.MNP_SC IS NOT NULL " +
                    		"    THEN t.MNP_SC " +
                    		"  END ) AS mnpscst, " +
                    		"  MAX( " +
                    		"  CASE " +
                    		"    WHEN t.TNR IS NOT NULL " +
                    		"    THEN t.TNR " +
                    		"  END ) AS tnrwss, " +
                    		"  MAX( " +
                    		"  CASE " +
                    		"    WHEN t.NA IS NOT NULL " +
                    		"    THEN t.NA " +
                    		"  END ) AS nabard " +
                    		"FROM " +
                    		"  (SELECT DISTINCT q.OFFICE_NAME, " +
                    		"    q.OFFICE_ID, " +
                    		"    q.DISTRICT_NAME, " +
                    		"    ( " +
                    		"    CASE " +
                    		"      WHEN q.ARWSP != '0' " +
                    		"      THEN q.ARWSP " +
                    		"      ELSE 0 " +
                    		"    END ) AS ARWSP, " +
                    		"    ( " +
                    		"    CASE " +
                    		"      WHEN q.ARWSP_SC != '0' " +
                    		"      THEN q.ARWSP_SC " +
                    		"      ELSE 0 " +
                    		"    END ) AS ARWSP_SC, " +
                    		"    ( " +
                    		"    CASE " +
                    		"      WHEN q.MNP != '0' " +
                    		"      THEN q.MNP " +
                    		"      ELSE 0 " +
                    		"    END ) AS MNP, " +
                    		"    ( " +
                    		"    CASE " +
                    		"      WHEN q.MNP_SC != '0' " +
                    		"      THEN q.MNP_SC " +
                    		"      ELSE 0 " +
                    		"    END ) AS MNP_SC, " +
                    		"    ( " +
                    		"    CASE " +
                    		"      WHEN q.TNR != '0' " +
                    		"      THEN q.TNR " +
                    		"      ELSE 0 " +
                    		"    END ) AS TNR, " +
                    		"    ( " +
                    		"    CASE " +
                    		"      WHEN q.NA != '0' " +
                    		"      THEN q.NA " +
                    		"      ELSE 0 " +
                    		"    END ) AS NA, " +
                    		"    q.ACCOUNT_HEAD_CODE, " +
                    		"    q.officename, " +
                    		"    q.REGION_OFFICE_ID, " +
                    		"    q.DISTRICT_CODE, " +
                    		"    q.ACCOUNTING_UNIT_ID, " +
                    		"    q.ACCOUNTING_UNIT_NAME " +
                    		"  FROM " +
                    		"    ( SELECT DISTINCT B.OFFICE_NAME, " +
                    		"      B.OFFICE_ID, " +
                    		"      B.DISTRICT_NAME, " +
                    		"      B.DISTRICT_CODE, " +
                    		"      B.ARWSP, " +
                    		"      B.ARWSP_SC, " +
                    		"      B.MNP, " +
                    		"      B.MNP_SC, " +
                    		"      B.TNR, " +
                    		"      B.NA, " +
                    		"      B.ACCOUNT_HEAD_CODE, " +
                    		"      B.officename, " +
                    		"      B.REGION_OFFICE_ID, " +
                    		"      B.ACCOUNTING_UNIT_ID, " +
                    		"      B.ACCOUNTING_UNIT_NAME " +
                    		"    FROM " +
                    		"      (SELECT D.DISTRICT_CODE, " +
                    		"        DISTRICT_NAME, " +
                    		"        F.OFFICE_NAME, " +
                    		"        F.OFFICE_ID, " +
                    		"        org.officename, " +
                    		"        V.REGION_OFFICE_ID , " +
                    		"        ( " +
                    		"        CASE " +
                    		"          WHEN P.FAS_WE_FUND_PROG_CODE='1' " +
                    		"          AND P.FAS_WE_FUND_TYPE      ='R' " +
                    		"          THEN SUM (T.CREDIT_CLOSING_BALANCE) " +
                    		"          ELSE 0 " +
                    		"        END) AS ARWSP , " +
                    		"        ( " +
                    		"        CASE " +
                    		"          WHEN P.FAS_WE_FUND_PROG_CODE='2' " +
                    		"          AND P.FAS_WE_FUND_TYPE      ='R' " +
                    		"          THEN SUM (T.CREDIT_CLOSING_BALANCE) " +
                    		"          ELSE 0 " +
                    		"        END) AS ARWSP_SC , " +
                    		"        ( " +
                    		"        CASE " +
                    		"          WHEN P.FAS_WE_FUND_PROG_CODE='3' " +
                    		"          AND P.FAS_WE_FUND_TYPE      ='R' " +
                    		"          THEN SUM (T.CREDIT_CLOSING_BALANCE) " +
                    		"          ELSE 0 " +
                    		"        END) AS MNP , " +
                    		"        ( " +
                    		"        CASE " +
                    		"          WHEN P.FAS_WE_FUND_PROG_CODE='4' " +
                    		"          AND P.FAS_WE_FUND_TYPE      ='R' " +
                    		"          THEN SUM (T.CREDIT_CLOSING_BALANCE) " +
                    		"          ELSE 0 " +
                    		"        END) AS MNP_SC , " +
                    		"        ( " +
                    		"        CASE " +
                    		"          WHEN P.FAS_WE_FUND_PROG_CODE='5' " +
                    		"          AND P.FAS_WE_FUND_TYPE      ='R' " +
                    		"          THEN SUM (T.CREDIT_CLOSING_BALANCE) " +
                    		"          ELSE 0 " +
                    		"        END) AS TNR , " +
                    		"        ( " +
                    		"        CASE " +
                    		"          WHEN P.FAS_WE_FUND_PROG_CODE='6' " +
                    		"          AND P.FAS_WE_FUND_TYPE      ='R' " +
                    		"          THEN SUM (T.CREDIT_CLOSING_BALANCE) " +
                    		"          ELSE 0 " +
                    		"        END) AS NA, " +
                    		"        T.ACCOUNT_HEAD_CODE, " +
                    		"        U.ACCOUNTING_UNIT_ID, " +
                    		"        U.ACCOUNTING_UNIT_NAME " +
                    		"      FROM COM_MST_DISTRICTS D " +
                    		"      INNER JOIN COM_MST_OFFICES F " +
                    		"      ON D.DISTRICT_CODE=F.DISTRICT_CODE " +
                    		"      INNER JOIN COM_MST_ALL_OFFICES_VIEW V " +
                    		"      ON V.OFFICE_ID=F.OFFICE_ID " +
                    		"      INNER JOIN FAS_MST_ACCT_UNITS U " +
                    		"      ON U.ACCOUNTING_UNIT_OFFICE_ID = F.OFFICE_ID " +
                    		"      INNER JOIN FAS_TRIAL_BALANCE T " +
                    		"      ON T.ACCOUNTING_UNIT_ID       =U.ACCOUNTING_UNIT_ID " +
                    		//"      AND T.ACCOUNTING_FOR_OFFICE_ID=U.ACCOUNTING_UNIT_OFFICE_ID " +
                    		"      INNER JOIN FAS_WE_FUND_PROG_AC_MAP M " +
                    		"      ON M.ACCOUNT_HEAD_CODE=T.ACCOUNT_HEAD_CODE " +
                    		"      INNER JOIN FAS_WE_FUND_PROG_MST P " +
                    		"      ON P.FAS_WE_FUND_PROG_CODE=M.FAS_WE_FUND_PROG_CODE " +
                    		"      INNER JOIN " +
                    		"        (SELECT OFFICE_ID, OFFICE_NAME AS officename FROM com_mst_offices " +
                    		"        ) org " +
                    		"      ON V.REGION_OFFICE_ID    =org.OFFICE_ID " +
                    		"      WHERE V.OFFICE_LEVEL_ID IN('RN','DN') " +
                    		"      AND F.PRIMARY_WORK_ID   IN ('RL') " +
                    		"    and to_date((Cashbook_month ||'-'||Cashbook_Year),'mm-yyyy') BETWEEN to_date("+cashMonth_from+"||'-'||"+cashYear_from+",'mm-yyyy')AND to_date("+cashMonth_to+" ||'-' ||"+cashYear_to+",'mm-yyyy')" +
                    	//	"      AND cashbook_year BETWEEN('"+cashYear_from+"') AND ('"+cashYear_to+"') " +
                    	//	"      AND cashbook_month BETWEEN('"+cashMonth_from+"') AND ('"+cashMonth_to+"') " +
                    		"      GROUP BY D.DISTRICT_CODE, " +
                    		"        DISTRICT_NAME, " +
                    		"        F.OFFICE_NAME, " +
                    		"        F.OFFICE_ID, " +
                    		"        org.officename, " +
                    		"        V.REGION_OFFICE_ID, " +
                    		"        T.ACCOUNT_HEAD_CODE, " +
                    		"        U.ACCOUNTING_UNIT_ID, " +
                    		"        U.ACCOUNTING_UNIT_NAME, " +
                    		"        P.FAS_WE_FUND_PROG_CODE, " +
                    		"        P.FAS_WE_FUND_TYPE " +
                    		"      ORDER BY F.OFFICE_ID " +
                    		"      ) B " +
                    		"    ORDER BY REGION_OFFICE_ID " +
                    		"    ) q " +
                    		"  )t " +
                    		"GROUP BY t.REGION_OFFICE_ID, " +
                    		"  t.officename, " +
                    		"  t.DISTRICT_CODE, " +
                    		"  t.DISTRICT_NAME, " +
                    		"  t.OFFICE_ID, " +
                    		"  t.ACCOUNTING_UNIT_ID, " +
                    		"  t.ACCOUNTING_UNIT_NAME " +
                    		"ORDER BY REGION_OFFICE_ID";                		
                    	
                    		System.out.println("myQry:::"+myQry);
                    		
                    		s=request.getRealPath("/org/FAS/FAS1/MIS/jaspers/Financial_Progress_Report.jrxml");
                    		output=request.getParameter("fileType");
                    		JasperDesign jasperDesign = JasperManager.loadXmlDesign(s);
                    		JRDesignQuery query=new JRDesignQuery();
                    		query.setText(myQry);
                    		jasperDesign.setQuery(query);
                    		JasperReport jasperReport = JasperManager.compileReport(jasperDesign);
                    		JasperPrint jasperPrint = JasperManager.fillReport(jasperReport, map, connection);
                    		//JasperPrint jasperPrint = JasperManager.fillReport(jasperReport, list, connection);
                    		ByteArrayOutputStream bout=new ByteArrayOutputStream();
        	            	if(output.equalsIgnoreCase("pdf")){
        			            	OutputStream os=response.getOutputStream();
        			            	response.setContentType("application/pdf");
        			            	response.setHeader ("Content-Disposition", "attachment;filename=\"Financial_Progress_Report.pdf\"");
        			            	os.write(JasperManager.printReportToPdf(jasperPrint));
        			            	os.close();
        	            	}else if(output.equalsIgnoreCase("excel")){
        	            			response.setContentType("application/vnd.ms-excel");
        	            			response.setHeader ("Content-Disposition", "attachment;filename=\"Financial_Progress_Report.xls\"");
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
        		            		 response.setHeader ("Content-Disposition", "attachment;filename=\"Financial_Progress_Report.html\"");
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
            		}else if(urbanrural.equalsIgnoreCase("U")){
            			try{
            				map.put("reporttype", "Urban");
                    		myQry="SELECT DISTINCT t.REGION_OFFICE_ID AS REGION_OFFICE_ID, " +
                    		"  t.officename                     AS regionname, " +
                    		"  t.DISTRICT_CODE                  AS DISTRICT_CODE, " +
                    		"  t.DISTRICT_NAME                  AS DISTRICT_NAME, " +
                    		"  t.OFFICE_ID                      AS OFFICE_ID, " +
                    		"  t.ACCOUNTING_UNIT_ID             AS ACCOUNTING_UNIT_ID, " +
                    		"  t.ACCOUNTING_UNIT_NAME           AS ACCOUNTING_UNIT_NAME, " +
                    		"  MAX( " +
                    		"  CASE " +
                    		"    WHEN t.ARWSP IS NOT NULL " +
                    		"    THEN t.ARWSP " +
                    		"  END ) AS arwsp, " +
                    		"  MAX( " +
                    		"  CASE " +
                    		"    WHEN t.ARWSP_SC IS NOT NULL " +
                    		"    THEN t.ARWSP_SC " +
                    		"  END ) AS arwspscst, " +
                    		"  MAX( " +
                    		"  CASE " +
                    		"    WHEN t.MNP IS NOT NULL " +
                    		"    THEN t.MNP " +
                    		"  END ) AS mnp, " +
                    		"  MAX( " +
                    		"  CASE " +
                    		"    WHEN t.MNP_SC IS NOT NULL " +
                    		"    THEN t.MNP_SC " +
                    		"  END ) AS mnpscst, " +
                    		"  MAX( " +
                    		"  CASE " +
                    		"    WHEN t.TNR IS NOT NULL " +
                    		"    THEN t.TNR " +
                    		"  END ) AS tnrwss, " +
                    		"  MAX( " +
                    		"  CASE " +
                    		"    WHEN t.NA IS NOT NULL " +
                    		"    THEN t.NA " +
                    		"  END ) AS nabard " +
                    		"FROM " +
                    		"  (SELECT DISTINCT q.OFFICE_NAME, " +
                    		"    q.OFFICE_ID, " +
                    		"    q.DISTRICT_NAME, " +
                    		"    ( " +
                    		"    CASE " +
                    		"      WHEN q.ARWSP != '0' " +
                    		"      THEN q.ARWSP " +
                    		"      ELSE 0 " +
                    		"    END ) AS ARWSP, " +
                    		"    ( " +
                    		"    CASE " +
                    		"      WHEN q.ARWSP_SC != '0' " +
                    		"      THEN q.ARWSP_SC " +
                    		"      ELSE 0 " +
                    		"    END ) AS ARWSP_SC, " +
                    		"    ( " +
                    		"    CASE " +
                    		"      WHEN q.MNP != '0' " +
                    		"      THEN q.MNP " +
                    		"      ELSE 0 " +
                    		"    END ) AS MNP, " +
                    		"    ( " +
                    		"    CASE " +
                    		"      WHEN q.MNP_SC != '0' " +
                    		"      THEN q.MNP_SC " +
                    		"      ELSE 0 " +
                    		"    END ) AS MNP_SC, " +
                    		"    ( " +
                    		"    CASE " +
                    		"      WHEN q.TNR != '0' " +
                    		"      THEN q.TNR " +
                    		"      ELSE 0 " +
                    		"    END ) AS TNR, " +
                    		"    ( " +
                    		"    CASE " +
                    		"      WHEN q.NA != '0' " +
                    		"      THEN q.NA " +
                    		"      ELSE 0 " +
                    		"    END ) AS NA, " +
                    		"    q.ACCOUNT_HEAD_CODE, " +
                    		"    q.officename, " +
                    		"    q.REGION_OFFICE_ID, " +
                    		"    q.DISTRICT_CODE, " +
                    		"    q.ACCOUNTING_UNIT_ID, " +
                    		"    q.ACCOUNTING_UNIT_NAME " +
                    		"  FROM " +
                    		"    ( SELECT DISTINCT B.OFFICE_NAME, " +
                    		"      B.OFFICE_ID, " +
                    		"      B.DISTRICT_NAME, " +
                    		"      B.DISTRICT_CODE, " +
                    		"      B.ARWSP, " +
                    		"      B.ARWSP_SC, " +
                    		"      B.MNP, " +
                    		"      B.MNP_SC, " +
                    		"      (B.ARWSP+B.ARWSP_SC+B.MNP+B.MNP_SC) AS TNR, " +
                    		"      B.NA, " +
                    		"      B.ACCOUNT_HEAD_CODE, " +
                    		"      B.officename, " +
                    		"      B.REGION_OFFICE_ID, " +
                    		"      B.ACCOUNTING_UNIT_ID, " +
                    		"      B.ACCOUNTING_UNIT_NAME " +
                    		"    FROM " +
                    		"      (SELECT D.DISTRICT_CODE, " +
                    		"        DISTRICT_NAME, " +
                    		"        F.OFFICE_NAME, " +
                    		"        F.OFFICE_ID, " +
                    		"        org.officename, " +
                    		"        V.REGION_OFFICE_ID , " +
                    		"        ( " +
                    		"        CASE " +
                    		"          WHEN P.FAS_WE_FUND_PROG_CODE='1' " +
                    		"          AND P.FAS_WE_FUND_TYPE      ='U' " +
                    		"          THEN SUM (T.CREDIT_CLOSING_BALANCE) " +
                    		"          ELSE 0 " +
                    		"        END) AS ARWSP , " +
                    		"        ( " +
                    		"        CASE " +
                    		"          WHEN P.FAS_WE_FUND_PROG_CODE='2' " +
                    		"          AND P.FAS_WE_FUND_TYPE      ='U' " +
                    		"          THEN SUM (T.CREDIT_CLOSING_BALANCE) " +
                    		"          ELSE 0 " +
                    		"        END) AS ARWSP_SC , " +
                    		"        ( " +
                    		"        CASE " +
                    		"          WHEN P.FAS_WE_FUND_PROG_CODE='3' " +
                    		"          AND P.FAS_WE_FUND_TYPE      ='U' " +
                    		"          THEN SUM (T.CREDIT_CLOSING_BALANCE) " +
                    		"          ELSE 0 " +
                    		"        END) AS MNP , " +
                    		"        ( " +
                    		"        CASE " +
                    		"          WHEN P.FAS_WE_FUND_PROG_CODE='4' " +
                    		"          AND P.FAS_WE_FUND_TYPE      ='U' " +
                    		"          THEN SUM (T.CREDIT_CLOSING_BALANCE) " +
                    		"          ELSE 0 " +
                    		"        END) AS MNP_SC , " +
                    		"        ( " +
                    		"        CASE " +
                    		"          WHEN P.FAS_WE_FUND_PROG_CODE='6' " +
                    		"          AND P.FAS_WE_FUND_TYPE      ='U' " +
                    		"          THEN SUM (T.CREDIT_CLOSING_BALANCE) " +
                    		"          ELSE 0 " +
                    		"        END) AS NA, " +
                    		"        T.ACCOUNT_HEAD_CODE, " +
                    		"        U.ACCOUNTING_UNIT_ID, " +
                    		"        U.ACCOUNTING_UNIT_NAME " +
                    		"      FROM COM_MST_DISTRICTS D " +
                    		"      INNER JOIN COM_MST_OFFICES F " +
                    		"      ON D.DISTRICT_CODE=F.DISTRICT_CODE " +
                    		"      INNER JOIN COM_MST_ALL_OFFICES_VIEW V " +
                    		"      ON V.OFFICE_ID=F.OFFICE_ID " +
                    		"      INNER JOIN FAS_MST_ACCT_UNITS U " +
                    		"      ON U.ACCOUNTING_UNIT_OFFICE_ID = F.OFFICE_ID " +
                    		"      INNER JOIN FAS_TRIAL_BALANCE T " +
                    		"      ON T.ACCOUNTING_UNIT_ID       =U.ACCOUNTING_UNIT_ID " +
                    		//"      AND T.ACCOUNTING_FOR_OFFICE_ID=U.ACCOUNTING_UNIT_OFFICE_ID " +
                    		"      INNER JOIN FAS_WE_FUND_PROG_AC_MAP M " +
                    		"      ON M.ACCOUNT_HEAD_CODE=T.ACCOUNT_HEAD_CODE " +
                    		"      INNER JOIN FAS_WE_FUND_PROG_MST P " +
                    		"      ON P.FAS_WE_FUND_PROG_CODE=M.FAS_WE_FUND_PROG_CODE " +
                    		"      INNER JOIN " +
                    		"        (SELECT OFFICE_ID, OFFICE_NAME AS officename FROM com_mst_offices " +
                    		"        ) org " +
                    		"      ON V.REGION_OFFICE_ID        =org.OFFICE_ID " +
                    		"      WHERE V.OFFICE_LEVEL_ID IN('RN','DN') " +
                    		"      AND F.PRIMARY_WORK_ID IN ('UR') " +
                    		"    and to_date((Cashbook_month ||'-'||Cashbook_Year),'mm-yyyy') BETWEEN to_date("+cashMonth_from+"||'-'||"+cashYear_from+",'mm-yyyy')AND to_date("+cashMonth_to+" ||'-' ||"+cashYear_to+",'mm-yyyy')" +
                    	//	"      AND cashbook_year BETWEEN('"+cashYear_from+"') AND ('"+cashYear_to+"') " +
                    	//	"      AND cashbook_month BETWEEN('"+cashMonth_from+"') AND ('"+cashMonth_to+"') " +
                    		"      GROUP BY D.DISTRICT_CODE, " +
                    		"        DISTRICT_NAME, " +
                    		"        F.OFFICE_NAME, " +
                    		"        F.OFFICE_ID, " +
                    		"        org.officename, " +
                    		"        V.REGION_OFFICE_ID, " +
                    		"        T.ACCOUNT_HEAD_CODE, " +
                    		"        U.ACCOUNTING_UNIT_ID, " +
                    		"        U.ACCOUNTING_UNIT_NAME, " +
                    		"        P.FAS_WE_FUND_PROG_CODE, " +
                    		"        P.FAS_WE_FUND_TYPE " +
                    		"      ORDER BY F.OFFICE_ID " +
                    		"      ) B " +
                    		"    ORDER BY REGION_OFFICE_ID " +
                    		"    ) q " +
                    		"  )t " +
                    		"GROUP BY t.REGION_OFFICE_ID, " +
                    		"  t.officename, " +
                    		"  t.DISTRICT_CODE, " +
                    		"  t.DISTRICT_NAME, " +
                    		"  t.OFFICE_ID, " +
                    		"  t.ACCOUNTING_UNIT_ID, " +
                    		"  t.ACCOUNTING_UNIT_NAME " +
                    		"ORDER BY REGION_OFFICE_ID";
                    		s=request.getRealPath("/org/FAS/FAS1/MIS/jaspers/Financial_Progress_Report.jrxml");
                    		output=request.getParameter("fileType");
                    		JasperDesign jasperDesign = JasperManager.loadXmlDesign(s);
                    		JRDesignQuery query=new JRDesignQuery();
                    		query.setText(myQry);
                    		jasperDesign.setQuery(query);
                    		JasperReport jasperReport = JasperManager.compileReport(jasperDesign);
                    		JasperPrint jasperPrint = JasperManager.fillReport(jasperReport, map, connection);
                    		//JasperPrint jasperPrint = JasperManager.fillReport(jasperReport, list, connection);
                    		ByteArrayOutputStream bout=new ByteArrayOutputStream();
        	            	if(output.equalsIgnoreCase("pdf")){
        			            	OutputStream os=response.getOutputStream();
        			            	response.setContentType("application/pdf");
        			            	response.setHeader ("Content-Disposition", "attachment;filename=\"Financial_Progress_Report.pdf\"");
        			            	os.write(JasperManager.printReportToPdf(jasperPrint));
        			            	os.close();
        	            	}else if(output.equalsIgnoreCase("excel")){
        	            			response.setContentType("application/vnd.ms-excel");
        	            			response.setHeader ("Content-Disposition", "attachment;filename=\"Financial_Progress_Report.xls\"");
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
        		            		 response.setHeader ("Content-Disposition", "attachment;filename=\"Financial_Progress_Report.html\"");
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

}
