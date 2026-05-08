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
public class ExpenditureReport extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExpenditureReport() {
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
		int cashYear_from = 0;
		int cashMonth_from = 0;
		int cashYear_to = 0;
		int cashMonth_to = 0,supplimentNo=0,regionId=0,accId=0;
		String strCommand="",isSjv="",officeId="",regionDes="",strQuery="";
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
                    cashYear_from = Integer.parseInt(request.getParameter("txtCB_Year_from"));
                    cashMonth_from = Integer.parseInt(request.getParameter("txtCB_Month_from"));
                    cashYear_to = Integer.parseInt(request.getParameter("txtCB_Year_to"));
                    cashMonth_to = Integer.parseInt(request.getParameter("txtCB_Month_to"));
                    strCommand = request.getParameter("command");
                    System.out.println("strCommand:::"+strCommand);
                    isSjv = request.getParameter("reptype");
                    System.out.println("isSjv or reg::::::********************************************************:::"+isSjv);
                    if((request.getParameter("txtsupplement_no")==null) || request.getParameter("txtsupplement_no").equals("")){
                    	supplimentNo = 0;
                    }else{
                    	supplimentNo = Integer.parseInt(request.getParameter("txtsupplement_no"));
                    }
                    if((request.getParameter("displayingOrder")==null) || request.getParameter("displayingOrder").equals("0")){
                        // else if(request.getParameter("displayingOrder").equals("OW"))
                         
                         	System.out.println("dis is nulllll");
                         	map.put("regionDes", " ");
                         }
                    
                    else if(request.getParameter("displayingOrder").equals("ALL"))
                    {
                    if((request.getParameter("txtRegionId")==null) || request.getParameter("txtRegionId").equals("0"))
                    	{
                    	map.put("regionDes", " ");
                    
                    	}
                    }
                    else if(request.getParameter("displayingOrder").equals("RW")){
			                   
			                    	officeId = request.getParameter("txtRegionId");
			                    	System.out.println("officeId "+officeId);
			                    	String[] offIdDesc = officeId.split("//");
			                    	System.out.println("offIdDesc[0] "+offIdDesc[0]);
			                    	System.out.println("offIdDesc[1] "+offIdDesc[1]);
			                    	regionId = Integer.parseInt(offIdDesc[0]);
			                    	regionDes = offIdDesc[1];
			                    	
			                    	PreparedStatement preparedStatement1 = connection.prepareStatement("Select Office_Name,Office_Id From Com_Mst_Offices Where Office_Id=?");
			                    	preparedStatement1.setInt(1,regionId);
			                    	ResultSet rrr = preparedStatement1.executeQuery();
			                    	if(rrr.next()){
			                    		offname = rrr.getString("Office_Name");
			                    	}
			                    	
			                    	
			                    	map.put("regionDes", offname);
			                    /*	String sql = "SELECT ACCOUNTING_UNIT_ID FROM FAS_MST_ACCT_UNITS WHERE ACCOUNTING_UNIT_OFFICE_ID=?";
			                    	PreparedStatement preparedStatement = connection.prepareStatement(sql);
			                    	preparedStatement.setInt(1,regionId);
			                    	ResultSet resultSet = preparedStatement.executeQuery();
			                    	if(resultSet.next()){
			                    		accId = resultSet.getInt("ACCOUNTING_UNIT_ID");
			                    		strQuery = " AND t.ACCOUNTING_UNIT_ID='"+accId+"' ";
			                    	}  */
			                    	strQuery=" AND t.ACCOUNTING_UNIT_ID in(SELECT Accounting_Unit_Id "+
											 "   FROM Fas_Mst_Acct_Units "+
											 "  WHERE Accounting_Unit_Office_Id IN "+
											 "   (SELECT Office_Id"+
											 "  FROM Com_Mst_All_Offices_View "+
											 " Where Region_Office_Id ="+regionId+ "))";
			                   
                    		}
                    else if(request.getParameter("displayingOrder").equals("OW"))
                    {
                    	System.out.println("office wise:");
                    	officeId = request.getParameter("offId");
                    	
                    	regionId = Integer.parseInt(officeId);
                    	
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
                if(strCommand.equalsIgnoreCase("summary")){
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
            		map.put("Unit_name", unitname);
                	try{  
                	
                		String s = "",myQry="";                		
                		if(isSjv.equalsIgnoreCase("Regular")){
                			System.out.println("Regular");
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
                    		"      CASE " +
                    		"        WHEN (t1.LEVEL_NO='1') " +
                    		"        THEN NVL(SUM(t.CURRENT_MONTH_DEBIT-t.CURRENT_MONTH_CREDIT),0) " +
                    		"        ELSE 0 " +
                    		"      END AS reg, " +
                    		"      CASE " +
                    		"        WHEN (t1.LEVEL_NO='2') " +
                    		"        THEN NVL(SUM(t.CURRENT_MONTH_DEBIT-t.CURRENT_MONTH_CREDIT),0) " +
                    		"        ELSE 0 " +
                    		"      END AS sc, " +
                    		"      CASE " +
                    		"        WHEN (t1.LEVEL_NO='3') " +
                    		"        THEN NVL(SUM(t.CURRENT_MONTH_DEBIT-t.CURRENT_MONTH_CREDIT),0) " +
                    		"        ELSE 0 " +
                    		"      END AS st, " +
                    		"      CASE " +
                    		"        WHEN (t1.LEVEL_NO='4') " +
                    		"        THEN NVL(SUM(t.CURRENT_MONTH_DEBIT-t.CURRENT_MONTH_CREDIT),0) " +
                    		"        ELSE 0 " +
                    		"      END AS mino, " +
                    		"      CASE " +
                    		"        WHEN (t1.LEVEL_NO='5') " +
                    		"        THEN NVL(SUM(t.CURRENT_MONTH_DEBIT-t.CURRENT_MONTH_CREDIT),0) " +
                    		"        ELSE 0 " +
                    		"      END AS other " +
                    		"    FROM FAS_TRIAL_BALANCE t " +
                    		"    LEFT OUTER JOIN fas_mis_exp_group_acc_hd_map t1 " +
                    		"    ON t.ACCOUNT_HEAD_CODE=t1.account_head_code " +
                    		"    WHERE to_date(t.cashbook_month " +
                    		"      ||'-' " +
                    		"      || t.cashbook_year, 'mm-yyyy') BETWEEN to_date( '"+cashMonth_from+"' " +
                    		"      || '-' " +
                    		"      || '"+cashYear_from+"', 'mm-yyyy') " +
                    		"    AND to_date( '"+cashMonth_to+"' " +
                    		"      ||'-' " +
                    		"      || '"+cashYear_to+"' , 'mm-yyyy') " +
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
                			sup="";
                			map.put("supplementNo",sup);
                		}else if(isSjv.equalsIgnoreCase("InclusiveSJV")){
                			System.out.println("including sjv");
                			myQry = "SELECT GROUP_HEAD_DESC, " +
                			"  SUB_HEAD_DESC, " +
                			"  reg, " +
                			"  sc, " +
                			"  st, " +
                			"  mino, " +
                			"  other, " +
                			"  (reg+sc+st+mino+other) AS net " +
                			"FROM " +
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
                			"      CASE " +
                			"        WHEN (t1.LEVEL_NO='1') " +
                			"        THEN NVL(SUM(t.CURRENT_MONTH_DEBIT-t.CURRENT_MONTH_CREDIT),0) " +
                			"        ELSE 0 " +
                			"      END AS reg, " +
                			"      CASE " +
                			"        WHEN (t1.LEVEL_NO='2') " +
                			"        THEN NVL(SUM(t.CURRENT_MONTH_DEBIT-t.CURRENT_MONTH_CREDIT),0) " +
                			"        ELSE 0 " +
                			"      END AS sc, " +
                			"      CASE " +
                			"        WHEN (t1.LEVEL_NO='3') " +
                			"        THEN NVL(SUM(t.CURRENT_MONTH_DEBIT-t.CURRENT_MONTH_CREDIT),0) " +
                			"        ELSE 0 " +
                			"      END AS st, " +
                			"      CASE " +
                			"        WHEN (t1.LEVEL_NO='4') " +
                			"        THEN NVL(SUM(t.CURRENT_MONTH_DEBIT-t.CURRENT_MONTH_CREDIT),0) " +
                			"        ELSE 0 " +
                			"      END AS mino, " +
                			"      CASE " +
                			"        WHEN (t1.LEVEL_NO='5') " +
                			"        THEN NVL(SUM(t.CURRENT_MONTH_DEBIT-t.CURRENT_MONTH_CREDIT),0) " +
                			"        ELSE 0 " +
                			"      END AS other " +
                			"    FROM FAS_TRIAL_BALANCE t " +
                			"    LEFT OUTER JOIN fas_mis_exp_group_acc_hd_map t1 " +
                			"    ON t.ACCOUNT_HEAD_CODE=t1.account_head_code " +
                			"    WHERE to_date(t.cashbook_month " +
                    		"      ||'-' " +
                    		"      || t.cashbook_year, 'mm-yyyy') BETWEEN to_date( '"+cashMonth_from+"' " +
                    		"      || '-' " +
                    		"      || '"+cashYear_from+"', 'mm-yyyy') " +
                    		"    AND to_date( '"+cashMonth_to+"' " +
                    		"      ||'-' " +
                    		"      || '"+cashYear_to+"' , 'mm-yyyy') " +
                    			strQuery +
                			"    AND t1.GROUP_HEAD_CODE>=94 " +
                			"    GROUP BY t.ACCOUNT_HEAD_CODE, " +
                			"      t1.GROUP_HEAD_CODE, " +
                			"      t1.MINOR_HEAD_CODE, " +
                			"      t1.account_head_code, " +
                			"      t1.LEVEL_NO " +
                			"    UNION ALL " +
                			"    SELECT t.ACCOUNT_HEAD_CODE AS ACCOUNT_HEAD_CODE, " +
                			"      t1.GROUP_HEAD_CODE, " +
                			"      t1.MINOR_HEAD_CODE, " +
                			"      t1.account_head_code AS account_head, " +
                			"      CASE " +
                			"        WHEN (t1.LEVEL_NO='1') " +
                			"        THEN NVL(SUM(t.CURRENT_MONTH_DEBIT-t.CURRENT_MONTH_CREDIT),0) " +
                			"        ELSE 0 " +
                			"      END AS reg, " +
                			"      CASE " +
                			"        WHEN (t1.LEVEL_NO='2') " +
                			"        THEN NVL(SUM(t.CURRENT_MONTH_DEBIT-t.CURRENT_MONTH_CREDIT),0) " +
                			"        ELSE 0 " +
                			"      END AS sc, " +
                			"      CASE " +
                			"        WHEN (t1.LEVEL_NO='3') " +
                			"        THEN NVL(SUM(t.CURRENT_MONTH_DEBIT-t.CURRENT_MONTH_CREDIT),0) " +
                			"        ELSE 0 " +
                			"      END AS st, " +
                			"      CASE " +
                			"        WHEN (t1.LEVEL_NO='4') " +
                			"        THEN NVL(SUM(t.CURRENT_MONTH_DEBIT-t.CURRENT_MONTH_CREDIT),0) " +
                			"        ELSE 0 " +
                			"      END AS mino, " +
                			"      CASE " +
                			"        WHEN (t1.LEVEL_NO='5') " +
                			"        THEN NVL(SUM(t.CURRENT_MONTH_DEBIT-t.CURRENT_MONTH_CREDIT),0) " +
                			"        ELSE 0 " +
                			"      END AS other " +
                			"    FROM FAS_TRIAL_BALANCE_SJV_CMP t " +
                			"    LEFT OUTER JOIN fas_mis_exp_group_acc_hd_map t1 " +
                			"    ON t.ACCOUNT_HEAD_CODE=t1.account_head_code " +
                			"    WHERE to_date(t.cashbook_month " +
                    		"      ||'-' " +
                    		"      || t.cashbook_year, 'mm-yyyy') BETWEEN to_date( '"+cashMonth_from+"' " +
                    		"      || '-' " +
                    		"      || '"+cashYear_from+"', 'mm-yyyy') " +
                    		"    AND to_date( '"+cashMonth_to+"' " +
                    		"      ||'-' " +
                    		"      || '"+cashYear_to+"' , 'mm-yyyy') " +strQuery +
                			"    AND t1.GROUP_HEAD_CODE>=94 " +
                			"    AND t.SUPPLEMENT_NO= '"+supplimentNo+"'"+
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
                			"  GROUP BY b.GROUP_HEAD_DESC,b.GROUP_HEAD_CODE, " +
                			"    c.SUB_HEAD_DESC " +
                			"  ) " +
                			"GROUP BY GROUP_HEAD_DESC,GROUP_HEAD_CODE, " +
                			"  SUB_HEAD_DESC, " +
                			"  reg, " +
                			"  sc, " +
                			"  st, " +
                			"  mino, " +
                			"  other " +
                			"ORDER BY GROUP_HEAD_CODE" ;
                			sup="For Supplement No "+supplimentNo;
                			map.put("supplementNo",sup);
                		}
                		
                		s=request.getRealPath("/org/FAS/FAS1/MIS/jaspers/ExpenditureSummary.jrxml");
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
    			            	response.setHeader ("Content-Disposition", "attachment;filename=\"Summary_Report.pdf\"");
    			            	os.write(JasperManager.printReportToPdf(jasperPrint));
    			            	os.close();
    	            	}else if(output.equalsIgnoreCase("excel")){
    	            			response.setContentType("application/vnd.ms-excel");
    	            			response.setHeader ("Content-Disposition", "attachment;filename=\"Summary_Report.xls\"");
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
    		            		 response.setHeader ("Content-Disposition", "attachment;filename=\"Summary_Report.html\"");
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
                else if(strCommand.equalsIgnoreCase("individual")){
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
                	map.put("year_from", cashYear_from);
                	map.put("year_to", cashYear_to);
            		map.put("month_from", monthlist.get(cashMonth_from));
            		map.put("month_to", monthlist.get(cashMonth_to));
                	try{
                		String s = "",myQry="";   
                		if(isSjv.equalsIgnoreCase("Regular")) {
                			System.out.println("individual reg::;");
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
                    		"        THEN NVL(SUM(t.CURRENT_MONTH_DEBIT-t.CURRENT_MONTH_CREDIT),0) " +
                    		"        ELSE 0 " +
                    		"      END AS reg, " +
                    		"      CASE " +
                    		"        WHEN (t1.LEVEL_NO='2') " +
                    		"        THEN NVL(SUM(t.CURRENT_MONTH_DEBIT-t.CURRENT_MONTH_CREDIT),0) " +
                    		"        ELSE 0 " +
                    		"      END AS sc, " +
                    		"      CASE " +
                    		"        WHEN (t1.LEVEL_NO='3') " +
                    		"        THEN NVL(SUM(t.CURRENT_MONTH_DEBIT-t.CURRENT_MONTH_CREDIT),0) " +
                    		"        ELSE 0 " +
                    		"      END AS st, " +
                    		"      CASE " +
                    		"        WHEN (t1.LEVEL_NO='4') " +
                    		"        THEN NVL(SUM(t.CURRENT_MONTH_DEBIT-t.CURRENT_MONTH_CREDIT),0) " +
                    		"        ELSE 0 " +
                    		"      END AS mino, " +
                    		"      CASE " +
                    		"        WHEN (t1.LEVEL_NO='5') " +
                    		"        THEN NVL(SUM(t.CURRENT_MONTH_DEBIT-t.CURRENT_MONTH_CREDIT),0) " +
                    		"        ELSE 0 " +
                    		"      END AS other " +
                    		"    FROM FAS_TRIAL_BALANCE t " +
                    		"    LEFT OUTER JOIN fas_mis_exp_group_acc_hd_map t1 " +
                    		"    ON t.ACCOUNT_HEAD_CODE=t1.account_head_code " +
                    		"    WHERE to_date(t.cashbook_month " +
                    		"      ||'-' " +
                    		"      || t.cashbook_year, 'mm-yyyy') BETWEEN to_date( '"+cashMonth_from+"' " +
                    		"      || '-' " +
                    		"      || '"+cashYear_from+"', 'mm-yyyy') " +
                    		"    AND to_date( '"+cashMonth_to+"' " +
                    		"      ||'-' " +
                    		"      || '"+cashYear_to+"' , 'mm-yyyy') " +
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
                			sup="";
                			map.put("supplementNo",sup);
                		}else if(isSjv.equalsIgnoreCase("InclusiveSJV")){
                			System.out.println("individual InclusiveSJV::;");
                			myQry = "SELECT GROUP_HEAD_DESC, " +
                			"  SUB_HEAD_DESC, " +
                			"  ACCOUNT_HEAD_CODE, " +
                			"  ACCOUNT_HEAD_DESC, " +
                			"  reg, " +
                			"  sc, " +
                			"  st, " +
                			"  mino, " +
                			"  other, " +
                			"  (reg+sc+st+mino+other) AS net " +
                			" FROM " +
                			"  (SELECT b.GROUP_HEAD_DESC AS GROUP_HEAD_DESC, b.GROUP_HEAD_CODE, " +
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
                			"        THEN NVL(SUM(t.CURRENT_MONTH_DEBIT-t.CURRENT_MONTH_CREDIT),0) " +
                			"        ELSE 0 " +
                			"      END AS reg, " +
                			"      CASE " +
                			"        WHEN (t1.LEVEL_NO='2') " +
                			"        THEN NVL(SUM(t.CURRENT_MONTH_DEBIT-t.CURRENT_MONTH_CREDIT),0) " +
                			"        ELSE 0 " +
                			"      END AS sc, " +
                			"      CASE " +
                			"        WHEN (t1.LEVEL_NO='3') " +
                			"        THEN NVL(SUM(t.CURRENT_MONTH_DEBIT-t.CURRENT_MONTH_CREDIT),0) " +
                			"        ELSE 0 " +
                			"      END AS st, " +
                			"      CASE " +
                			"        WHEN (t1.LEVEL_NO='4') " +
                			"        THEN NVL(SUM(t.CURRENT_MONTH_DEBIT-t.CURRENT_MONTH_CREDIT),0) " +
                			"        ELSE 0 " +
                			"      END AS mino, " +
                			"      CASE " +
                			"        WHEN (t1.LEVEL_NO='5') " +
                			"        THEN NVL(SUM(t.CURRENT_MONTH_DEBIT-t.CURRENT_MONTH_CREDIT),0) " +
                			"        ELSE 0 " +
                			"      END AS other " +
                			"    FROM FAS_TRIAL_BALANCE t " +
                			"    LEFT OUTER JOIN fas_mis_exp_group_acc_hd_map t1 " +
                			"    ON t.ACCOUNT_HEAD_CODE=t1.account_head_code " +
                			"    WHERE to_date(t.cashbook_month " +
                    		"      ||'-' " +
                    		"      || t.cashbook_year, 'mm-yyyy') BETWEEN to_date( '"+cashMonth_from+"' " +
                    		"      || '-' " +
                    		"      || '"+cashYear_from+"', 'mm-yyyy') " +
                    		"    AND to_date( '"+cashMonth_to+"' " +
                    		"      ||'-' " +
                    		"      || '"+cashYear_to+"' , 'mm-yyyy') " +
                    			strQuery +
                			"    AND t1.GROUP_HEAD_CODE>=94 " +
                			"    GROUP BY t.ACCOUNT_HEAD_CODE, " +
                			"      t1.GROUP_HEAD_CODE, " +
                			"      t1.MINOR_HEAD_CODE, " +
                			"      t1.account_head_code, " +
                			"      t1.LEVEL_NO " +
                			"    UNION ALL " +
                			"    SELECT t.ACCOUNT_HEAD_CODE AS ACCOUNT_HEAD_CODE, " +
                			"      t1.GROUP_HEAD_CODE, " +
                			"      t1.MINOR_HEAD_CODE, " +
                			"      t1.account_head_code AS account_head, " +
                			"      CASE " +
                			"        WHEN (t1.LEVEL_NO='1') " +
                			"        THEN NVL(SUM(t.CURRENT_MONTH_DEBIT-t.CURRENT_MONTH_CREDIT),0) " +
                			"        ELSE 0 " +
                			"      END AS reg, " +
                			"      CASE " +
                			"        WHEN (t1.LEVEL_NO='2') " +
                			"        THEN NVL(SUM(t.CURRENT_MONTH_DEBIT-t.CURRENT_MONTH_CREDIT),0) " +
                			"        ELSE 0 " +
                			"      END AS sc, " +
                			"      CASE " +
                			"        WHEN (t1.LEVEL_NO='3') " +
                			"        THEN NVL(SUM(t.CURRENT_MONTH_DEBIT-t.CURRENT_MONTH_CREDIT),0) " +
                			"        ELSE 0 " +
                			"      END AS st, " +
                			"      CASE " +
                			"        WHEN (t1.LEVEL_NO='4') " +
                			"        THEN NVL(SUM(t.CURRENT_MONTH_DEBIT-t.CURRENT_MONTH_CREDIT),0) " +
                			"        ELSE 0 " +
                			"      END AS mino, " +
                			"      CASE " +
                			"        WHEN (t1.LEVEL_NO='5') " +
                			"        THEN NVL(SUM(t.CURRENT_MONTH_DEBIT-t.CURRENT_MONTH_CREDIT),0) " +
                			"        ELSE 0 " +
                			"      END AS other " +
                			"    FROM FAS_TRIAL_BALANCE_SJV_CMP t " +
                			"    LEFT OUTER JOIN fas_mis_exp_group_acc_hd_map t1 " +
                			"    ON t.ACCOUNT_HEAD_CODE=t1.account_head_code " +
                			"    WHERE to_date(t.cashbook_month " +
                    		"      ||'-' " +
                    		"      || t.cashbook_year, 'mm-yyyy') BETWEEN to_date( '"+cashMonth_from+"' " +
                    		"      || '-' " +
                    		"      || '"+cashYear_from+"', 'mm-yyyy') " +
                    		"    AND to_date( '"+cashMonth_to+"' " +
                    		"      ||'-' " +
                    		"      || '"+cashYear_to+"' , 'mm-yyyy') " +
                    			strQuery +
                			"    AND t1.GROUP_HEAD_CODE>=94 " +
                			"    AND t.SUPPLEMENT_NO= '"+supplimentNo+"'"+
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
                			"ORDER BY GROUP_HEAD_CODE, " +
                			"  SUB_HEAD_DESC, " +
                			"  ACCOUNT_HEAD_CODE";
                			sup="For Supplement No "+supplimentNo;
                			map.put("supplementNo",sup);
                		}
                		
                		s=request.getRealPath("/org/FAS/FAS1/MIS/jaspers/ExpenditureIndividual.jrxml");
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
    			            	response.setHeader ("Content-Disposition", "attachment;filename=\"Summary_Report.pdf\"");
    			            	os.write(JasperManager.printReportToPdf(jasperPrint));
    			            	os.close();
    	            	}else if(output.equalsIgnoreCase("excel")){
    	            			response.setContentType("application/vnd.ms-excel");
    	            			response.setHeader ("Content-Disposition", "attachment;filename=\"Summary_Report.xls\"");
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
    		            		 response.setHeader ("Content-Disposition", "attachment;filename=\"Summary_Report.html\"");
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
                }else if(strCommand.equalsIgnoreCase("annualSummary")){
                	System.out.println("annualSummary sssssssssssssssssssssssssssssssssssss");
                	String majorHead = request.getParameter("majorGroupCode");
                	String desc="";
                	if(majorHead.equals("L"))
                	{
                		desc="Liabilities";
                	}
                	else if(majorHead.equals("I"))
                	{
                		desc="Income";
                	}
                	else if(majorHead.equals("A"))
                	{
                		desc="Assets";
                	}
                	else if(majorHead.equals("E"))
                	{
                		desc="Expenditure";
                	}
                	else if(majorHead.equals("O"))
                	{
                		desc="Others";
                	}
                	String head="";
                	if(isSjv.equalsIgnoreCase("Regular"))
                	{
                		head=desc+" Details for the Financial year "+cashYear_from+"-"+cashYear_to;
                	}
                	else
                	{
                		head=desc+" Details for the Financial year "+cashYear_from+"-"+cashYear_to+" (Including SJV)";	
                	}
                	
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
            		map.put("heading_one",head);
            		String MGCode="";
            		int minorGroupCode = Integer.parseInt(request.getParameter("minorGroupCode"));
            		if(minorGroupCode==0)
            		{
            			MGCode="";
            		}
            		else{
            		MGCode=" AND t1.GROUP_HEAD_CODE='"+minorGroupCode+"'";
            		}
            		
            		
                	try{                		
                		String s = "",myQry="";                		
                		if(isSjv.equalsIgnoreCase("Regular")){
                			myQry="SELECT GROUP_HEAD_DESC, " +
                			"  SUB_HEAD_DESC, " +
                			"  currentdebit, " +
                			"  currentcredit, " +
                			"  (currentcredit-currentdebit) AS net " +
                			" FROM " +
                			"  (SELECT b.GROUP_HEAD_DESC AS GROUP_HEAD_DESC, " +
                			"    c.SUB_HEAD_DESC         AS SUB_HEAD_DESC, " +
                			"    a.currentdebit          AS currentdebit, " +
                			"    a.currentcredit         AS currentcredit " +
                			"  FROM " +
                			"    (SELECT t.ACCOUNT_HEAD_CODE AS ACCOUNT_HEAD_CODE, " +
                			"      t1.GROUP_HEAD_CODE, " +
                			"      t1.MINOR_HEAD_CODE, " +
                			"      t1.account_head_code               AS account_head, " +
                			"      NVL(SUM(t.CURRENT_MONTH_DEBIT),0)  AS currentdebit, " +
                			"      NVL(SUM(t.CURRENT_MONTH_CREDIT),0) AS currentcredit " +
                			"    FROM FAS_TRIAL_BALANCE t " +
                			"    LEFT OUTER JOIN FAS_MIS_EXP_GROUP_ACC_HD_MAP t1 " +
                			"    ON t.ACCOUNT_HEAD_CODE=t1.account_head_code " +
                			"    WHERE to_date(t.cashbook_month " +
                    		"      ||'-' " +
                    		"      || t.cashbook_year, 'mm-yyyy') BETWEEN to_date( '"+cashMonth_from+"' " +
                    		"      || '-' " +
                    		"      || '"+cashYear_from+"', 'mm-yyyy') " +
                    		"    AND to_date( '"+cashMonth_to+"' " +
                    		"      ||'-' " +
                    		"      || '"+cashYear_to+"' , 'mm-yyyy') " +
                			//"    AND t1.GROUP_HEAD_CODE>=94 " +
                			"	 AND t1.MAJOR_HEAD_CODE='"+majorHead+"' " +MGCode+
                		//	"	 AND t1.GROUP_HEAD_CODE='"+minorGroupCode+"'" +
                			"    GROUP BY t.ACCOUNT_HEAD_CODE, " +
                			"      t1.GROUP_HEAD_CODE, " +
                			"      t1.MINOR_HEAD_CODE, " +
                			"      t1.account_head_code " +
                			"    )a " +
                			"  LEFT OUTER JOIN " +
                			"    (SELECT GROUP_HEAD_CODE,GROUP_HEAD_DESC FROM fas_mis_group_master " +
                			"    )b " +
                			"  ON a.group_head_code=b.GROUP_HEAD_CODE " +
                			"  LEFT OUTER JOIN " +
                			"    (SELECT MINOR_HEAD_CODE as SUB_HEAD_CODE,MINOR_HEAD_DESC as SUB_HEAD_DESC FROM COM_MST_MINOR_HEADS " +
                			"    )c " +
                			"  ON a.minor_head_code=c.SUB_HEAD_CODE " +
                			"  GROUP BY b.GROUP_HEAD_DESC, " +
                			"    c.SUB_HEAD_DESC, " +
                			"    a.currentdebit, " +
                			"    a.currentcredit " +
                			"  ) " +
                			" GROUP BY GROUP_HEAD_DESC, " +
                			"  SUB_HEAD_DESC, " +
                			"  currentdebit, " +
                			"  currentcredit " +
                			"ORDER BY GROUP_HEAD_DESC";
                		}else if(isSjv.equalsIgnoreCase("InclusiveSJV")){
                			myQry = "SELECT GROUP_HEAD_DESC, " +
                			"  SUB_HEAD_DESC, " +
                			"  currentdebit, " +
                			"  currentcredit, " +
                			"  (currentcredit-currentdebit) AS net " +
                			" FROM " +
                			"  (SELECT b.GROUP_HEAD_DESC AS GROUP_HEAD_DESC, " +
                			"    c.SUB_HEAD_DESC         AS SUB_HEAD_DESC, " +
                			"    a.currentdebit          AS currentdebit, " +
                			"    a.currentcredit         AS currentcredit " +
                			"  FROM " +
                			" (Select Account_Head_Code,Group_Head_Code,Minor_Head_Code, " +
                			" 	    Account_Head,Sum(Currentdebit) As Currentdebit, " +
                			" 	    sum(Currentcredit) as Currentcredit " +
                			" 	    from " +
                			" 	    (SELECT T.Account_Head_Code AS Account_Head_Code, " +
                			" 	      T1.Group_Head_Code, " +
                			" 	      T1.Minor_Head_Code, " +
                			" 	      T1.Account_Head_Code               AS Account_Head, " +
                			" 	      NVL(SUM(T.Current_Month_Debit),0)  AS Currentdebit, " +
                			" 	      NVL(SUM(T.Current_Month_Credit),0) AS Currentcredit " +
                			" 	    FROM Fas_Trial_Balance T " +
                			" 	    LEFT OUTER JOIN Fas_Mis_Exp_Group_Acc_Hd_Map T1 " +
                			" 	    ON T.Account_Head_Code=T1.Account_Head_Code " +
                			" 	    WHERE To_Date(T.Cashbook_Month " +
                			" 	      ||'-' " +
                			" 	      || T.Cashbook_Year, 'mm-yyyy') BETWEEN To_Date( '"+cashMonth_from+"' " +
                			" 	      || '-' " +
                			" 	      || '"+cashYear_from+"', 'mm-yyyy') " +
                			" 	    AND To_Date( '"+cashMonth_to+"' " +
                			" 	      ||'-' " +
                			" 	      || '"+cashYear_to+"' , 'mm-yyyy') " +
                			" 	    AND T1.Major_Head_Code='"+majorHead+"'"+MGCode+
                		//	" 	    AND T1.Group_Head_Code='"+minorGroupCode+"'"+
                			" 	    GROUP BY T.Account_Head_Code, " +
                			" 	      T1.Group_Head_Code, " +
                			" 	      T1.Minor_Head_Code, " +
                			" 	      T1.Account_Head_Code " +
                			" 	      Union All " +
                			" 	      SELECT T.Account_Head_Code AS Account_Head_Code, " +
                			" 	      T1.Group_Head_Code, " +
                			" 	      T1.Minor_Head_Code, " +
                			" 	      T1.Account_Head_Code               AS Account_Head, " +
                			" 	      NVL(SUM(T.Current_Month_Debit),0)  AS Currentdebit, " +
                			" 	      Nvl(Sum(T.Current_Month_Credit),0) As Currentcredit " +
                			" 	    FROM Fas_Trial_Balance_Sjv_Cmp T " +
                			" 	    LEFT OUTER JOIN Fas_Mis_Exp_Group_Acc_Hd_Map T1 " +
                			" 	    ON T.Account_Head_Code=T1.Account_Head_Code " +
                			" 	    WHERE To_Date(T.Cashbook_Month " +
                			" 	      ||'-' " +
                			" 	      || T.Cashbook_Year, 'mm-yyyy') BETWEEN To_Date( '"+cashMonth_from+"'"+
                			" 	      || '-' " +
                			" 	      || '"+cashYear_from+"', 'mm-yyyy') " +
                			" 	    AND To_Date( '"+cashMonth_to+"' " +
                			" 	      ||'-' " +
                			" 	      || '"+cashYear_to+"' , 'mm-yyyy') " +
                			" 	    AND T1.Major_Head_Code='"+majorHead+"'"+MGCode+
                		//	" 	    And T1.Group_Head_Code='"+minorGroupCode+"'"+
                			" 	    and T.Supplement_No<= " +supplimentNo+
                			" 	    GROUP BY T.Account_Head_Code, " +
                			" 	      T1.Group_Head_Code, " +
                			" 	      T1.Minor_Head_Code, " +
                			" 	      T1.Account_Head_Code) " +
                			" 	      Group By Account_Head_Code,Group_Head_Code,Minor_Head_Code,Account_Head " +
                			" 	    )a " +
                			"  LEFT OUTER JOIN " +
                			"    (SELECT GROUP_HEAD_CODE,GROUP_HEAD_DESC FROM fas_mis_group_master " +
                			"    )b " +
                			"  ON a.group_head_code=b.GROUP_HEAD_CODE " +
                			"  LEFT OUTER JOIN " +
                			"    (SELECT MINOR_HEAD_CODE as SUB_HEAD_CODE,MINOR_HEAD_DESC as SUB_HEAD_DESC FROM COM_MST_MINOR_HEADS " +
                			"    )c " +
                			"  ON a.minor_head_code=c.SUB_HEAD_CODE " +
                			"  GROUP BY b.GROUP_HEAD_DESC, " +
                			"    c.SUB_HEAD_DESC, " +
                			"    a.currentdebit, " +
                			"    a.currentcredit " +
                			"  ) " +
                			" GROUP BY GROUP_HEAD_DESC, " +
                			"  SUB_HEAD_DESC, " +
                			"  currentdebit, " +
                			"  currentcredit " +
                			" ORDER BY GROUP_HEAD_DESC";
                		}
                		
                		s=request.getRealPath("/org/FAS/FAS1/MIS/jaspers/AnnualSummary.jrxml");
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
    			            	response.setHeader ("Content-Disposition", "attachment;filename=\"Summary_Report.pdf\"");
    			            	os.write(JasperManager.printReportToPdf(jasperPrint));
    			            	os.close();
    	            	}else if(output.equalsIgnoreCase("excel")){
    	            			response.setContentType("application/vnd.ms-excel");
    	            			response.setHeader ("Content-Disposition", "attachment;filename=\"Summary_Report.xls\"");
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
    		            		 response.setHeader ("Content-Disposition", "attachment;filename=\"Summary_Report.html\"");
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
                }else if(strCommand.equalsIgnoreCase("annualindividual")){
                	System.out.println("opt 3:::");
                	String majorHead = request.getParameter("majorGroupCode");
                	String desc="";
                	if(majorHead.equals("L"))
                	{
                		desc="Liabilities";
                	}
                	else if(majorHead.equals("I"))
                	{
                		desc="Income";
                	}
                	else if(majorHead.equals("A"))
                	{
                		desc="Assets";
                	}
                	else if(majorHead.equals("E"))
                	{
                		desc="Expenditure";
                	}
                	else if(majorHead.equals("O"))
                	{
                		desc="Others";
                	}
                	String head="";
                	if(isSjv.equalsIgnoreCase("Regular"))
                	{
                		head=desc+" Details for the Financial year "+cashYear_from+"-"+cashYear_to;
                	}
                	else
                	{
                		head=desc+" Details for the Financial year "+cashYear_from+"-"+cashYear_to+" (Including SJV)";	
                	}
                	
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
            		map.put("heading_one",head);
            		String MGCode="";
            	//	System.out.println("dhana::::"+request.getParameter("minorGroupCode"));
            		int minorGroupCode = Integer.parseInt(request.getParameter("minorGroupCode"));
            		if(minorGroupCode==0)
            		{
            			MGCode="";
            		}
            		else{
            		MGCode=" AND t1.GROUP_HEAD_CODE='"+minorGroupCode+"'";
            		}
                	try{
                		String s = "",myQry="";   
                		if(isSjv.equalsIgnoreCase("Regular")) {
                			myQry="SELECT GROUP_HEAD_DESC, " +
                			"  SUB_HEAD_DESC, " +
                			"  ACCOUNT_HEAD_CODE, " +
                			"  ACCOUNT_HEAD_DESC, " +
                			"  currentdebit, " +
                			"  currentcredit, " +
                			"  (currentdebit-currentcredit) AS net " +
                			"FROM " +
                			"  (SELECT b.GROUP_HEAD_DESC AS GROUP_HEAD_DESC, " +
                			"    c.SUB_HEAD_DESC         AS SUB_HEAD_DESC, " +
                			"    a.ACCOUNT_HEAD_CODE     AS ACCOUNT_HEAD_CODE, " +
                			"    d.ACCOUNT_HEAD_DESC     AS ACCOUNT_HEAD_DESC, " +
                			"    a.currentdebit          AS currentdebit, " +
                			"    a.currentcredit         AS currentcredit " +
                			"  FROM " +
                			"    (SELECT t.ACCOUNT_HEAD_CODE AS ACCOUNT_HEAD_CODE, " +
                			"      t1.GROUP_HEAD_CODE, " +
                			"      t1.MINOR_HEAD_CODE, " +
                			"      t1.account_head_code               AS account_head, " +
                			"      NVL(SUM(t.CURRENT_MONTH_DEBIT),0)  AS currentdebit, " +
                			"      NVL(SUM(t.CURRENT_MONTH_CREDIT),0) AS currentcredit " +
                			"    FROM FAS_TRIAL_BALANCE t " +
                			"    LEFT OUTER JOIN FAS_MIS_EXP_GROUP_ACC_HD_MAP t1 " +
                			"    ON t.ACCOUNT_HEAD_CODE=t1.account_head_code " +
                			"    WHERE to_date(t.cashbook_month " +
                    		"      ||'-' " +
                    		"      || t.cashbook_year, 'mm-yyyy') BETWEEN to_date( '"+cashMonth_from+"' " +
                    		"      || '-' " +
                    		"      || '"+cashYear_from+"', 'mm-yyyy') " +
                    		"    AND to_date( '"+cashMonth_to+"' " +
                    		"      ||'-' " +
                    		"      || '"+cashYear_to+"' , 'mm-yyyy') " +
                    		"	 AND t1.MAJOR_HEAD_CODE='"+majorHead+"'" +MGCode+
                			//"	 AND t1.GROUP_HEAD_CODE='"+minorGroupCode+"'" +
                			//"    AND t1.GROUP_HEAD_CODE>=94 " +
                			"    GROUP BY t.ACCOUNT_HEAD_CODE, " +
                			"      t1.GROUP_HEAD_CODE, " +
                			"      t1.MINOR_HEAD_CODE, " +
                			"      t1.account_head_code " +
                			"    )a " +
                			"  LEFT OUTER JOIN " +
                			"    (SELECT GROUP_HEAD_CODE,GROUP_HEAD_DESC FROM fas_mis_group_master " +
                			"    )b " +
                			"  ON a.group_head_code=b.GROUP_HEAD_CODE " +
                			"  LEFT OUTER JOIN " +
                			"    (SELECT MINOR_HEAD_CODE as SUB_HEAD_CODE,MINOR_HEAD_DESC as SUB_HEAD_DESC FROM COM_MST_MINOR_HEADS " +
                			"    )c " +
                			"  ON a.minor_head_code=c.SUB_HEAD_CODE " +
                			"  LEFT OUTER JOIN " +
                			"    ( SELECT ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC FROM COM_MST_ACCOUNT_HEADS " +
                			"    )d " +
                			"  ON a.ACCOUNT_HEAD_CODE=d.ACCOUNT_HEAD_CODE " +
                			"  ) " +
                			"ORDER BY GROUP_HEAD_DESC, " +
                			"  SUB_HEAD_DESC, " +
                			"  ACCOUNT_HEAD_CODE" ;
                		}else if(isSjv.equalsIgnoreCase("InclusiveSJV")){
                			System.out.println("InclusiveSJV:");
                			myQry="SELECT GROUP_HEAD_DESC, " +
                			"  SUB_HEAD_DESC, " +
                			"  ACCOUNT_HEAD_CODE, " +
                			"  ACCOUNT_HEAD_DESC, " +
                			"  currentdebit, " +
                			"  currentcredit, " +
                			"  (currentdebit-currentcredit) AS net " +
                			"FROM " +
                			"("+
                			"SELECT b.GROUP_HEAD_DESC AS GROUP_HEAD_DESC, " +
                			"    c.SUB_HEAD_DESC         AS SUB_HEAD_DESC, " +
                			"    a.ACCOUNT_HEAD_CODE     AS ACCOUNT_HEAD_CODE, " +
                			"    d.ACCOUNT_HEAD_DESC     AS ACCOUNT_HEAD_DESC, " +
                			"    a.currentdebit          AS currentdebit, " +
                			"    a.currentcredit         AS currentcredit " +
                			"  FROM " +
                			"  (" +
                			" Select Account_Head_Code,Group_Head_Code,Minor_Head_Code,Account_Head, "+
                			"   sum(currentdebit)as currentdebit,sum(currentcredit)as currentcredit"+
                			"  from"+
                			"    (SELECT t.ACCOUNT_HEAD_CODE AS ACCOUNT_HEAD_CODE, " +
                			"      t1.GROUP_HEAD_CODE, " +
                			"      t1.MINOR_HEAD_CODE, " +
                			"      t1.account_head_code               AS account_head, " +
                			"      NVL(SUM(t.CURRENT_MONTH_DEBIT),0)  AS currentdebit, " +
                			"      NVL(SUM(t.CURRENT_MONTH_CREDIT),0) AS currentcredit " +
                			"    FROM FAS_TRIAL_BALANCE t " +
                			"    LEFT OUTER JOIN FAS_MIS_EXP_GROUP_ACC_HD_MAP t1 " +
                			"    ON t.ACCOUNT_HEAD_CODE=t1.account_head_code " +
                			"    WHERE to_date(t.cashbook_month " +
                    		"      ||'-' " +
                    		"      || t.cashbook_year, 'mm-yyyy') BETWEEN to_date( '"+cashMonth_from+"' " +
                    		"      || '-' " +
                    		"      || '"+cashYear_from+"', 'mm-yyyy') " +
                    		"    AND to_date( '"+cashMonth_to+"' " +
                    		"      ||'-' " +
                    		"      || '"+cashYear_to+"' , 'mm-yyyy') " +
                    		"	 AND t1.MAJOR_HEAD_CODE='"+majorHead+"'" +MGCode+
                			//"	 AND t1.GROUP_HEAD_CODE='"+minorGroupCode+"'" +
                			//"    AND t1.GROUP_HEAD_CODE>=94 " +
                			"    GROUP BY t.ACCOUNT_HEAD_CODE, " +
                			"      t1.GROUP_HEAD_CODE, " +
                			"      t1.MINOR_HEAD_CODE, " +
                			"      t1.account_head_code " +
                			" union all "+
			                  "      SELECT t.ACCOUNT_HEAD_CODE AS ACCOUNT_HEAD_CODE,\n" + 
				              "      t1.GROUP_HEAD_CODE,\n" + 
				              "      t1.MINOR_HEAD_CODE,\n" + 
				              "      t1.account_head_code               AS account_head,\n" + 
				              "      NVL(SUM(t.CURRENT_MONTH_DEBIT),0)  AS currentdebit,\n" + 
				              "      Nvl(Sum(T.Current_Month_Credit),0) As Currentcredit\n" + 
				              "    FROM FAS_TRIAL_BALANCE_SJV_CMP t\n" + 
				              "    LEFT OUTER JOIN FAS_MIS_EXP_GROUP_ACC_HD_MAP t1\n" + 
				              "    ON t.ACCOUNT_HEAD_CODE=t1.account_head_code\n" + 
				              "    WHERE to_date(t.cashbook_month\n" + 
				              "      ||'-'\n" + 
				              "      || t.cashbook_year, 'mm-yyyy') BETWEEN to_date( '"+cashMonth_from+"'" +
				              "      || '-'\n" + 
				              "      || '"+cashYear_from+"', 'mm-yyyy')\n" + 
				              "    AND to_date( '"+cashMonth_to+"'\n" + 
				              "      ||'-'\n" + 
				              "      || '"+cashYear_to+"' , 'mm-yyyy')\n" + 
				              "    AND t1.MAJOR_HEAD_CODE='"+majorHead+"'\n" + MGCode+
				             // "    AND t1.GROUP_HEAD_CODE='"+minorGroupCode+"'\n" + 
				              "    GROUP BY t.ACCOUNT_HEAD_CODE,\n" + 
				              "      t1.GROUP_HEAD_CODE,\n" + 
				              "      T1.Minor_Head_Code,\n" + 
				              "      T1.Account_Head_Code" +
			                  ") group by  Account_Head_Code,Group_Head_Code,Minor_Head_Code,Account_Head "+
                			"    )a " +
                			"  LEFT OUTER JOIN " +
                			"    (SELECT GROUP_HEAD_CODE,GROUP_HEAD_DESC FROM fas_mis_group_master " +
                			"    )b " +
                			"  ON a.group_head_code=b.GROUP_HEAD_CODE " +
                			"  LEFT OUTER JOIN " +
                			"    (SELECT MINOR_HEAD_CODE as SUB_HEAD_CODE,MINOR_HEAD_DESC as SUB_HEAD_DESC FROM COM_MST_MINOR_HEADS " +
                			"    )c " +
                			"  ON a.minor_head_code=c.SUB_HEAD_CODE " +
                			"  LEFT OUTER JOIN " +
                			"    ( SELECT ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC FROM COM_MST_ACCOUNT_HEADS " +
                			"    )d " +
                			"  ON a.ACCOUNT_HEAD_CODE=d.ACCOUNT_HEAD_CODE " +
                			"  ) " +
                			"ORDER BY GROUP_HEAD_DESC, " +
                			"  SUB_HEAD_DESC, " +
                			"  ACCOUNT_HEAD_CODE" ;
                		}
                		
                		s=request.getRealPath("/org/FAS/FAS1/MIS/jaspers/AnnualIndividual.jrxml");
                		output=request.getParameter("fileType");
                		JasperDesign jasperDesign = JasperManager.loadXmlDesign(s);
                		System.out.println("3::::"+myQry);
                		JRDesignQuery query=new JRDesignQuery();
                		query.setText(myQry);
                		jasperDesign.setQuery(query);
                		JasperReport jasperReport = JasperManager.compileReport(jasperDesign);
                		JasperPrint jasperPrint = JasperManager.fillReport(jasperReport, map, connection);
                		ByteArrayOutputStream bout=new ByteArrayOutputStream();
    	            	if(output.equalsIgnoreCase("pdf")){
    			            	OutputStream os=response.getOutputStream();
    			            	response.setContentType("application/pdf");
    			            	response.setHeader ("Content-Disposition", "attachment;filename=\"Summary_Report.pdf\"");
    			            	os.write(JasperManager.printReportToPdf(jasperPrint));
    			            	os.close();
    	            	}else if(output.equalsIgnoreCase("excel")){
    	            			response.setContentType("application/vnd.ms-excel");
    	            			response.setHeader ("Content-Disposition", "attachment;filename=\"Summary_Report.xls\"");
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
    		            		 response.setHeader ("Content-Disposition", "attachment;filename=\"Summary_Report.html\"");
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
