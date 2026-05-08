package Servlets.FAS.FAS1.MIS.servlets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
public class ExpenditureReport_jrnl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExpenditureReport_jrnl() {
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
		System.out.println("dopost::");
		Connection connection=null;
		int cashYear = 0;
		int cashMonth= 0,accId=0;
		String strCommand="",isSjv="",officeId="",regionDes="",strQuery="";
		int regionId=0;
		String unitname="",offname="",sup="";
		Map map = new HashMap();
		map.put("regionDes", " ");
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
                    cashYear = Integer.parseInt(request.getParameter("txtCB_Year"));
                    cashMonth = Integer.parseInt(request.getParameter("txtCB_Month"));
                   
                    strCommand = request.getParameter("command");
                    System.out.println("strCommand:::"+strCommand);
                   
                    
                    
					if((request.getParameter("displayingOrder")==null) || request.getParameter("displayingOrder").equals("0")){
                        // else if(request.getParameter("displayingOrder").equals("OW"))
                         	System.out.println("dis is nulllll");
                         	map.put("regionDes", " ");
                         }
                    else if(request.getParameter("displayingOrder").equals("UW"))
                    {
                    	System.out.println("unit wise:");
                    	officeId = request.getParameter("cmbAcc_UnitCode");
                    	
                    	regionId = Integer.parseInt(officeId);
                    	System.out.println("regionId:::"+regionId);
                    	String sql = "SELECT ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME FROM FAS_MST_ACCT_UNITS WHERE ACCOUNTING_UNIT_ID=?";
                    	PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    	preparedStatement.setInt(1,regionId);
                    	ResultSet resultSet = preparedStatement.executeQuery();
                    	if(resultSet.next()){
                    		unitname = resultSet.getString("ACCOUNTING_UNIT_NAME");
                    	}
                    	System.out.println("unitname:::"+unitname);
                    	map.put("regionDes", unitname);
                    	strQuery = " AND t.ACCOUNTING_UNIT_ID='"+regionId+"' ";
                    }
                   
                }catch(Exception e) {
                	System.out.println("exception::::");
                	e.printStackTrace();
                }
               if(strCommand.equalsIgnoreCase("individual")){
                	System.out.println("indi");
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
                	map.put("year", cashYear);
                	map.put("month", monthlist.get(cashMonth));
            		
                	try{
                		String s = "",myQry="";   
                		myQry="SELECT GROUP_HEAD_DESC, " +
                    		"  SUB_HEAD_DESC, " +
                    		"  ACCOUNT_HEAD_CODE, " +
                    		"  ACCOUNT_HEAD_DESC, " +
                    		"  reg, " +
                    		"  sc, " +
                    		"  st, " +
                    		"  mino, " +
                    		"  other, " +
                    		"  (reg+sc+st+mino+other) AS net " +
                    		"FROM " +
                    		"  (SELECT b.GROUP_HEAD_DESC AS GROUP_HEAD_DESC,b.GROUP_HEAD_CODE, " +
                    		"    c.SUB_HEAD_DESC         AS SUB_HEAD_DESC, " +
                    		"    a.ACCOUNT_HEAD_CODE     AS ACCOUNT_HEAD_CODE, " +
                    		"    d.ACCOUNT_HEAD_DESC     AS ACCOUNT_HEAD_DESC, " +
                    		"    SUM(a.reg)              AS reg, " +
                    		"    SUM(a.sc)               AS sc, " +
                    		"    SUM(a.st)               AS st, " +
                    		"    SUM(a.mino)             AS mino, " +
                    		"    SUM(a.other)            AS other " +
                    		"  FROM " +
                    		"    (SELECT t.ACCOUNT_HEAD_CODE AS ACCOUNT_HEAD_CODE, " +
                    		"      t1.GROUP_HEAD_CODE, " +
                    		"      t1.MINOR_HEAD_CODE, " +
                    		"      t1.account_head_code AS account_head, " +
                    		"      CASE " +
                    		"        WHEN (t1.LEVEL_NO='1') " +
                    		"        THEN NVL(SUM(t.amount),0) " +
                    		"        ELSE 0 " +
                    		"      END AS reg, " +
                    		"      CASE " +
                    		"        WHEN (t1.LEVEL_NO='2') " +
                    		"        THEN NVL(SUM(t.amount),0) " +
                    		"        ELSE 0 " +
                    		"      END AS sc, " +
                    		"      CASE " +
                    		"        WHEN (t1.LEVEL_NO='3') " +
                    		"        THEN NVL(SUM(t.amount),0) " +
                    		"        ELSE 0 " +
                    		"      END AS st, " +
                    		"      CASE " +
                    		"        WHEN (t1.LEVEL_NO='4') " +
                    		"        THEN NVL(SUM(t.amount),0) " +
                    		"        ELSE 0 " +
                    		"      END AS mino, " +
                    		"      CASE " +
                    		"        WHEN (t1.LEVEL_NO='5') " +
                    		"        THEN NVL(SUM(t.amount),0) " +
                    		"        ELSE 0 " +
                    		"      END AS other " +
                    		"    FROM fas_journal_master m,fas_journal_transaction t " +
                    		"    LEFT OUTER JOIN fas_mis_exp_group_acc_hd_map t1 " +
                    		"    ON t.ACCOUNT_HEAD_CODE=t1.account_head_code " +
                    		"    WHERE m.accounting_unit_id =t.accounting_unit_id "+
                    		"	and m.accounting_for_office_id = t.accounting_for_office_id	"+
                    		"	and m.cashbook_year = t.cashbook_year	"+
                    		"	and m.cashbook_month = t.cashbook_month	"+ 
                    		"	and m.voucher_no = t.voucher_no	"+
                    		"	and m.journal_status = 'L'	"+ 
                    		"	and t.cr_dr_indicator = 'DR'	"+ 
                    		"   and m.cashbook_year = 	'"+ cashYear +"'		"+
                    		"	and m.cashbook_month =   '"+ cashMonth +"'			  " +
                    		strQuery +
                    		"	AND t1.GROUP_HEAD_CODE>=94 "+
                    		"    GROUP BY t.ACCOUNT_HEAD_CODE, " +
                    		"      t1.GROUP_HEAD_CODE, " +
                    		"      t1.MINOR_HEAD_CODE, " +
                    		"      t1.account_head_code, " +
                    		"      t1.LEVEL_NO " +
                    		"    )a " +
                    		"  LEFT OUTER JOIN " +
                    		"    (SELECT GROUP_HEAD_CODE,GROUP_HEAD_DESC FROM fas_mis_group_master " +
                    		"    )b " +
                    		"  ON a.group_head_code=b.GROUP_HEAD_CODE " +
                    		"  LEFT OUTER JOIN " +
                    		"    (SELECT SUB_HEAD_CODE,SUB_HEAD_DESC FROM com_mst_sub_heads " +
                    		"    )c " +
                    		"  ON a.minor_head_code=c.SUB_HEAD_CODE " +
                    		"  LEFT OUTER JOIN " +
                    		"    ( SELECT ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC FROM COM_MST_ACCOUNT_HEADS " +
                    		"    )d " +
                    		"  ON a.ACCOUNT_HEAD_CODE=d.ACCOUNT_HEAD_CODE " +
                    		"  GROUP BY b.GROUP_HEAD_DESC,b.GROUP_HEAD_CODE, " +
                    		"    c.SUB_HEAD_DESC, " +
                    		"    a.ACCOUNT_HEAD_CODE, " +
                    		"    d.ACCOUNT_HEAD_DESC " +
                    		"  ) " +
                    		"GROUP BY GROUP_HEAD_DESC,GROUP_HEAD_CODE, " +
                    		"  SUB_HEAD_DESC, " +
                    		"  reg, " +
                    		"  sc, " +
                    		"  st, " +
                    		"  mino, " +
                    		"  other, " +
                    		"  ACCOUNT_HEAD_CODE, " +
                    		"  ACCOUNT_HEAD_DESC " +
                    		"ORDER BY GROUP_HEAD_CODE,SUB_HEAD_DESC,ACCOUNT_HEAD_CODE";
                		
                		s=request.getRealPath("/org/FAS/FAS1/MIS/jaspers/ExpenditureIndividual_jrnl.jrxml");
                		output=request.getParameter("fileType");
                		JasperDesign jasperDesign = JasperManager.loadXmlDesign(s);
                		System.out.println("sql:"+myQry);
                		JRDesignQuery query=new JRDesignQuery();
                		query.setText(myQry);
                		jasperDesign.setQuery(query);
                		JasperReport jasperReport = JasperManager.compileReport(jasperDesign);
                		JasperPrint jasperPrint = JasperManager.fillReport(jasperReport, map, connection);
                		ByteArrayOutputStream bout=new ByteArrayOutputStream();
    	            	if(output.equalsIgnoreCase("pdf")){
    			            	OutputStream os=response.getOutputStream();
    			            	response.setContentType("application/pdf");
    			            	response.setHeader ("Content-Disposition", "attachment;filename=\"Summary_Report_journal.pdf\"");
    			            	os.write(JasperManager.printReportToPdf(jasperPrint));
    			            	os.close();
    	            	}else if(output.equalsIgnoreCase("excel")){
    	            			response.setContentType("application/vnd.ms-excel");
    	            			response.setHeader ("Content-Disposition", "attachment;filename=\"Summary_Report_Journal.xls\"");
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
    		            		 response.setHeader ("Content-Disposition", "attachment;filename=\"Summary_Report_Journal.html\"");
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
               //for summary report-officewise journal
               else if(strCommand.equalsIgnoreCase("summary")){
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
               	map.put("year", cashYear);
           		map.put("month", monthlist.get(cashMonth));
               	try{  
               	
               		String s = "",myQry="";                		
               		myQry="SELECT GROUP_HEAD_DESC, " +
                   		"  SUB_HEAD_DESC, " +
                   		"  reg, " +
                   		"  sc, " +
                   		"  st, " +
                   		"  mino, " +
                   		"  other, " +
                   		"  (reg+sc+st+mino+other) AS net " +
                   		" FROM " +
                   		"  (SELECT b.GROUP_HEAD_DESC AS GROUP_HEAD_DESC,b.GROUP_HEAD_CODE, " +
                   		"    c.SUB_HEAD_DESC         AS SUB_HEAD_DESC, " +
                   		"    SUM(a.reg)              AS reg, " +
                   		"    SUM(a.sc)               AS sc, " +
                   		"    SUM(a.st)               AS st, " +
                   		"    SUM(a.mino)             AS mino, " +
                   		"    SUM(a.other)            AS other " +
                   		"  FROM " +
                   		"    (SELECT t.ACCOUNT_HEAD_CODE AS ACCOUNT_HEAD_CODE, " +
                   		"      t1.GROUP_HEAD_CODE, " +
                   		"      t1.MINOR_HEAD_CODE, " +
                   		"      t1.account_head_code AS account_head, " +
                   		"		CASE	"+
                   		"        WHEN (t1.LEVEL_NO='1') " +
                		"        THEN NVL(SUM(t.amount),0) " +
                		"        ELSE 0 " +
                		"      END AS reg, " +
                		"      CASE " +
                		"        WHEN (t1.LEVEL_NO='2') " +
                		"        THEN NVL(SUM(t.amount),0) " +
                		"        ELSE 0 " +
                		"      END AS sc, " +
                		"      CASE " +
                		"        WHEN (t1.LEVEL_NO='3') " +
                		"        THEN NVL(SUM(t.amount),0) " +
                		"        ELSE 0 " +
                		"      END AS st, " +
                		"      CASE " +
                		"        WHEN (t1.LEVEL_NO='4') " +
                		"        THEN NVL(SUM(t.amount),0) " +
                		"        ELSE 0 " +
                		"      END AS mino, " +
                		"      CASE " +
                		"        WHEN (t1.LEVEL_NO='5') " +
                		"        THEN NVL(SUM(t.amount),0) " +
                		"        ELSE 0 " +
                		"      END AS other " +
                		"    FROM fas_journal_master m,fas_journal_transaction t " +
                   		"    LEFT OUTER JOIN fas_mis_exp_group_acc_hd_map t1 " +
                   		"    ON t.ACCOUNT_HEAD_CODE=t1.account_head_code " +
                   		"    WHERE  m.accounting_unit_id =t.accounting_unit_id "+
                    		"	and m.accounting_for_office_id = t.accounting_for_office_id	"+
                    		"	and m.cashbook_year = t.cashbook_year	"+
                    		"	and m.cashbook_month = t.cashbook_month	"+ 
                    		"	and m.voucher_no = t.voucher_no	"+
                    		"	and m.journal_status = 'L'	"+ 
                    		"	and t.cr_dr_indicator = 'DR'	"+ 
                    		"   and m.cashbook_year = 	'"+ cashYear +"'		"+
                    		"	and m.cashbook_month =   '"+ cashMonth +"'			  " +
                   			strQuery +
                   		"	AND t1.GROUP_HEAD_CODE>=94 "+
                   		"    GROUP BY t.ACCOUNT_HEAD_CODE, " +
                   		"      t1.GROUP_HEAD_CODE, " +
                   		"      t1.MINOR_HEAD_CODE, " +
                   		"      t1.account_head_code, " +
                   		"      t1.LEVEL_NO " +
                   		"    )a " +
                   		"  LEFT OUTER JOIN " +
                   		"    (SELECT GROUP_HEAD_CODE,GROUP_HEAD_DESC FROM fas_mis_group_master " +
                   		"    )b " +
                   		"  ON a.group_head_code=b.GROUP_HEAD_CODE " +
                   		"  LEFT OUTER JOIN " +
                   		"    (SELECT SUB_HEAD_CODE,SUB_HEAD_DESC FROM com_mst_sub_heads " +
                   		"    )c " +
                   		"  ON a.minor_head_code=c.SUB_HEAD_CODE " +
                   		"  GROUP BY b.GROUP_HEAD_DESC,b.GROUP_HEAD_CODE," +
                   		"    c.SUB_HEAD_DESC " +
                   		"  ) " +
                   		"GROUP BY GROUP_HEAD_DESC,GROUP_HEAD_CODE, " +
                   		"  SUB_HEAD_DESC, " +
                   		"  reg, " +
                   		"  sc, " +
                   		"  st, " +
                   		"  mino, " +
                   		"  other " +
                   		"ORDER BY GROUP_HEAD_CODE";
               		
               		s=request.getRealPath("/org/FAS/FAS1/MIS/jaspers/ExpenditureSummary_jrnl.jrxml");
               		output=request.getParameter("fileType");
               		JasperDesign jasperDesign = JasperManager.loadXmlDesign(s);
               		System.out.println("que:::"+myQry);
               		JRDesignQuery query=new JRDesignQuery();
               		query.setText(myQry);
               		jasperDesign.setQuery(query);
               		JasperReport jasperReport = JasperManager.compileReport(jasperDesign);
               		JasperPrint jasperPrint = JasperManager.fillReport(jasperReport, map, connection);
               		ByteArrayOutputStream bout=new ByteArrayOutputStream();
   	            	if(output.equalsIgnoreCase("pdf")){
   			            	OutputStream os=response.getOutputStream();
   			            	response.setContentType("application/pdf");
   			            	response.setHeader ("Content-Disposition", "attachment;filename=\"Summary_Report_Journal.pdf\"");
   			            	os.write(JasperManager.printReportToPdf(jasperPrint));
   			            	os.close();
   	            	}else if(output.equalsIgnoreCase("excel")){
   	            			response.setContentType("application/vnd.ms-excel");
   	            			response.setHeader ("Content-Disposition", "attachment;filename=\"Summary_Report_Journal.xls\"");
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
   		            		 response.setHeader ("Content-Disposition", "attachment;filename=\"Summary_Report_Journal.html\"");
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

