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
 * Servlet implementation class urban_works
 */
public class urban_works extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public urban_works() {
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
		System.out.println("urban works:::");
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
                    System.out.println("diplaying Option selected::::::"+request.getParameter("displayingOrder"));
                    if((request.getParameter("displayingOrder")==null) || request.getParameter("displayingOrder").equals("0")){
                        // else if(request.getParameter("displayingOrder").equals("OW"))
                         
                         	System.out.println("dis is nulllll");
                         	map.put("regionDes", " ");
                         }
                    
                   
                    else if(request.getParameter("displayingOrder").equalsIgnoreCase("ALL"))
                    {
                    if((request.getParameter("txtRegionId")==null) || request.getParameter("txtRegionId").equals("0"))
                    	{
                    	map.put("regionDes", " ");
                    
                    	}
                    }
                    else if(request.getParameter("displayingOrder").equalsIgnoreCase("RW")){
			                   
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
                    else if(request.getParameter("displayingOrder").equalsIgnoreCase("OW"))
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
                if(strCommand.equalsIgnoreCase("urban_all"))
                {
                	System.out.println("urban urban_all");
                	int gc=0;
                	String wk="Works - "+request.getParameter("typeid");
                	
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
            		map.put("wk", wk);
                	try{  
                	if(request.getParameter("typeid").equals("Urban"))
                	{
                		gc=109;
                	}
                	else 
                	{
                		gc=118;
                	}
                		String s = "",myQry="";                		
                		if(isSjv.equalsIgnoreCase("Regular")){
                			System.out.println("Regular");
         	               myQry="SELECT GROUP_HEAD_DESC,\n" + 
         	   	               "  SUB_HEAD_DESC,\n" + 
         	   	               "  Funds,\n" + 
         	   	               "  exp,\n" + 
         	   	               "  SDW,\n" + 
         	   	               "\n" + 
         	   	               "  (Funds+exp+SDW) AS net\n" + 
         	   	               "FROM\n" + 
         	   	               "  (SELECT b.GROUP_HEAD_DESC AS GROUP_HEAD_DESC,\n" + 
         	   	               "    b.GROUP_HEAD_CODE,\n" + 
         	   	               "    c.SUB_HEAD_DESC AS SUB_HEAD_DESC,\n" + 
         	   	               "    Sum(A.Funds)      As Funds,\n" + 
         	   	               "    SUM(a.exp)       AS exp,\n" + 
         	   	               "    SUM(a.SDW)       AS SDW\n" + 
         	   	               "   \n" + 
         	   	               "  FROM\n" + 
         	   	               "    (SELECT t.ACCOUNT_HEAD_CODE AS ACCOUNT_HEAD_CODE,\n" + 
         	   	               "      t1.GROUP_HEAD_CODE,\n" + 
         	   	               "      t1.MINOR_HEAD_CODE,\n" + 
         	   	               "      t1.account_head_code AS account_head,\n" + 
         	   	               "      CASE\n" + 
         	   	               "        WHEN (t1.LEVEL_NO='1')\n" + 
         	   	               "        THEN NVL(SUM(t.CURRENT_MONTH_DEBIT-t.CURRENT_MONTH_CREDIT),0)\n" + 
         	   	               "        Else 0\n" + 
         	   	               "      END AS funds,\n" + 
         	   	               "      CASE\n" + 
         	   	               "        WHEN (t1.LEVEL_NO='2')\n" + 
         	   	               "        THEN NVL(SUM(t.CURRENT_MONTH_DEBIT-t.CURRENT_MONTH_CREDIT),0)\n" + 
         	   	               "        Else 0\n" + 
         	   	               "      END AS exp,\n" + 
         	   	               "      CASE\n" + 
         	   	               "        WHEN (t1.LEVEL_NO='3')\n" + 
         	   	               "        THEN NVL(SUM(t.CURRENT_MONTH_DEBIT-t.CURRENT_MONTH_CREDIT),0)\n" + 
         	   	               "        Else 0\n" + 
         	   	               "      END AS SDW\n" + 
         	   	               "  \n" + 
         	   	               "    \n" + 
         	   	               "    FROM FAS_TRIAL_BALANCE t\n" + 
         	   	               "    LEFT OUTER JOIN fas_mis_exp_group_acc_hd_map t1\n" + 
         	   	               "    ON t.ACCOUNT_HEAD_CODE=t1.account_head_code\n" + 
         	   	               "    WHERE to_date(t.cashbook_month\n" + 
         	   	               "      ||'-'\n" + 
         	   	               "      || t.cashbook_year, 'mm-yyyy') BETWEEN to_date( '"+cashMonth_from+"'\n" + 
         	   	               "      || '-'\n" + 
         	   	               "      || '"+cashYear_from+"', 'mm-yyyy')\n" + 
         	   	               "    AND to_date( '"+cashMonth_to+"'\n" + 
         	   	               "      ||'-'\n" + 
         	   	               "      || '"+cashYear_to+"' , 'mm-yyyy') and (Current_Month_Debit!=0 Or Current_Month_Credit!=0) " + 
         	   	       	strQuery +
         	   	              "    AND t1.GROUP_HEAD_CODE >" +gc+ 
         	   	               "    GROUP BY t.ACCOUNT_HEAD_CODE,\n" + 
         	   	               "      t1.GROUP_HEAD_CODE,\n" + 
         	   	               "      t1.MINOR_HEAD_CODE,\n" + 
         	   	               "      t1.account_head_code,\n" + 
         	   	               "      t1.LEVEL_NO\n" + 
         	   	               "    )a\n" + 
         	   	               "  LEFT OUTER JOIN\n" + 
         	   	               "    (SELECT GROUP_HEAD_CODE,GROUP_HEAD_DESC FROM fas_mis_group_master\n" + 
         	   	               "    )b\n" + 
         	   	               "  ON a.group_head_code=b.GROUP_HEAD_CODE\n" + 
         	   	               "  LEFT OUTER JOIN\n" + 
         	   	               "    (SELECT SUB_HEAD_CODE,SUB_HEAD_DESC FROM com_mst_sub_heads\n" + 
         	   	               "    )c\n" + 
         	   	               "  ON a.minor_head_code=c.SUB_HEAD_CODE\n" + 
         	   	               "  GROUP BY b.GROUP_HEAD_DESC,\n" + 
         	   	               "    b.GROUP_HEAD_CODE,\n" + 
         	   	               "    c.SUB_HEAD_DESC\n" + 
         	   	               "  )\n" + 
         	   	               "GROUP BY GROUP_HEAD_DESC,\n" + 
         	   	               "  GROUP_HEAD_CODE,\n" + 
         	   	               "  SUB_HEAD_DESC,\n" + 
         	   	               "  Funds,\n" + 
         	   	               "  Exp,\n" + 
         	   	               "  SDW\n" + 
         	   	               "ORDER BY GROUP_HEAD_CODE";
                			sup="";
                			map.put("supplementNo",sup);
                		}else if(isSjv.equalsIgnoreCase("InclusiveSJV")){
                			System.out.println("including sjv");
         	               myQry="SELECT GROUP_HEAD_DESC,\n" + 
         	   	               "  SUB_HEAD_DESC,\n" + 
         	   	               "  funds,\n" + 
         	   	               "  exp,\n" + 
         	   	               "  SDW,\n" + 
         	   	               " \n" + 
         	   	               "  (funds+exp+SDW) AS net\n" + 
         	   	               "FROM\n" + 
         	   	               "  (SELECT b.GROUP_HEAD_DESC AS GROUP_HEAD_DESC,\n" + 
         	   	               "    b.GROUP_HEAD_CODE,\n" + 
         	   	               "    c.SUB_HEAD_DESC AS SUB_HEAD_DESC,\n" + 
         	   	               "    SUM(a.funds)      AS funds,\n" + 
         	   	               "    SUM(a.exp)       AS exp,\n" + 
         	   	               "    SUM(a.SDW)       AS SDW\n" + 
         	   	               "  \n" + 
         	   	               "  FROM\n" + 
         	   	               "    (SELECT t.ACCOUNT_HEAD_CODE AS ACCOUNT_HEAD_CODE,\n" + 
         	   	               "      t1.GROUP_HEAD_CODE,\n" + 
         	   	               "      t1.MINOR_HEAD_CODE,\n" + 
         	   	               "      t1.account_head_code AS account_head,\n" + 
         	   	               "      CASE\n" + 
         	   	               "        WHEN (t1.LEVEL_NO='1')\n" + 
         	   	               "        THEN NVL(SUM(t.CURRENT_MONTH_DEBIT-t.CURRENT_MONTH_CREDIT),0)\n" + 
         	   	               "        ELSE 0\n" + 
         	   	               "      END AS funds,\n" + 
         	   	               "      CASE\n" + 
         	   	               "        WHEN (t1.LEVEL_NO='2')\n" + 
         	   	               "        THEN NVL(SUM(t.CURRENT_MONTH_DEBIT-t.CURRENT_MONTH_CREDIT),0)\n" + 
         	   	               "        ELSE 0\n" + 
         	   	               "      END AS exp,\n" + 
         	   	               "      CASE\n" + 
         	   	               "        WHEN (t1.LEVEL_NO='3')\n" + 
         	   	               "        THEN NVL(SUM(t.CURRENT_MONTH_DEBIT-t.CURRENT_MONTH_CREDIT),0)\n" + 
         	   	               "        Else 0\n" + 
         	   	               "      END AS SDW\n" + 
         	   	               "     \n" + 
         	   	               "    FROM FAS_TRIAL_BALANCE t\n" + 
         	   	               "    LEFT OUTER JOIN fas_mis_exp_group_acc_hd_map t1\n" + 
         	   	               "    ON t.ACCOUNT_HEAD_CODE=t1.account_head_code\n" + 
         	   	               "    WHERE to_date(t.cashbook_month\n" + 
         	   	               "      ||'-'\n" + 
         	   	               "      || t.cashbook_year, 'mm-yyyy') BETWEEN to_date( '"+cashMonth_from+"'\n" + 
         	   	               "      || '-'\n" + 
         	   	               "      || '"+cashYear_from+"', 'mm-yyyy')\n" + 
         	   	               "    AND to_date( '"+cashMonth_to+"'\n" + 
         	   	               "      ||'-'\n" + 
         	   	               "      || '"+cashYear_to+"' , 'mm-yyyy') And (Current_Month_Debit!=0 Or Current_Month_Credit!=0) " + 
         	   	     
         	   	               "    AND t1.GROUP_HEAD_CODE >"+gc+ 
         	   	               "    GROUP BY t.ACCOUNT_HEAD_CODE,\n" + 
         	   	               "      t1.GROUP_HEAD_CODE,\n" + 
         	   	               "      t1.MINOR_HEAD_CODE,\n" + 
         	   	               "      t1.account_head_code,\n" + 
         	   	               "      t1.LEVEL_NO\n" + 
         	   	               "    UNION ALL\n" + 
         	   	               "    SELECT t.ACCOUNT_HEAD_CODE AS ACCOUNT_HEAD_CODE,\n" + 
         	   	               "      t1.GROUP_HEAD_CODE,\n" + 
         	   	               "      t1.MINOR_HEAD_CODE,\n" + 
         	   	               "      t1.account_head_code AS account_head,\n" + 
         	   	               "      CASE\n" + 
         	   	               "        WHEN (t1.LEVEL_NO='1')\n" + 
         	   	               "        THEN NVL(SUM(t.CURRENT_MONTH_DEBIT-t.CURRENT_MONTH_CREDIT),0)\n" + 
         	   	               "        ELSE 0\n" + 
         	   	               "      END AS funds,\n" + 
         	   	               "      CASE\n" + 
         	   	               "        WHEN (t1.LEVEL_NO='2')\n" + 
         	   	               "        THEN NVL(SUM(t.CURRENT_MONTH_DEBIT-t.CURRENT_MONTH_CREDIT),0)\n" + 
         	   	               "        Else 0\n" + 
         	   	               "      END AS exp,\n" + 
         	   	               "      CASE\n" + 
         	   	               "        WHEN (t1.LEVEL_NO='3')\n" + 
         	   	               "        THEN NVL(SUM(t.CURRENT_MONTH_DEBIT-t.CURRENT_MONTH_CREDIT),0)\n" + 
         	   	               "        ELSE 0\n" + 
         	   	               "      END AS SDW\n" + 
         	   	               "     \n" + 
         	   	               "    FROM FAS_TRIAL_BALANCE_SJV_CMP t\n" + 
         	   	               "    LEFT OUTER JOIN fas_mis_exp_group_acc_hd_map t1\n" + 
         	   	               "    ON t.ACCOUNT_HEAD_CODE=t1.account_head_code\n" + 
         	   	               "    WHERE to_date(t.cashbook_month\n" + 
         	   	               "      ||'-'\n" + 
         	   	               "      || t.cashbook_year, 'mm-yyyy') BETWEEN to_date( '"+cashMonth_from+"'\n" + 
         	   	               "      || '-'\n" + 
         	   	               "      || '"+cashYear_from+"', 'mm-yyyy')\n" + 
         	   	               "    AND to_date( '"+cashMonth_to+"'\n" + 
         	   	               "      ||'-'\n" + 
         	   	               "      || '"+cashYear_to+"' , 'mm-yyyy') And (Current_Month_Debit!=0 Or Current_Month_Credit!=0)\n" + 
         	   	               "    AND t1.GROUP_HEAD_CODE >"+gc+ 
         	   	               "    AND t.SUPPLEMENT_NO     = '1'\n" + 
         	   	               "    GROUP BY t.ACCOUNT_HEAD_CODE,\n" + 
         	   	               "      t1.GROUP_HEAD_CODE,\n" + 
         	   	               "      t1.MINOR_HEAD_CODE,\n" + 
         	   	               "      t1.account_head_code,\n" + 
         	   	               "      t1.LEVEL_NO\n" + 
         	   	               "    )a\n" + 
         	   	               "  LEFT OUTER JOIN\n" + 
         	   	               "    (SELECT GROUP_HEAD_CODE,GROUP_HEAD_DESC FROM fas_mis_group_master\n" + 
         	   	               "    )b\n" + 
         	   	               "  ON a.group_head_code=b.GROUP_HEAD_CODE\n" + 
         	   	               "  LEFT OUTER JOIN\n" + 
         	   	               "    (SELECT SUB_HEAD_CODE,SUB_HEAD_DESC FROM com_mst_sub_heads\n" + 
         	   	               "    )c\n" + 
         	   	               "  ON a.minor_head_code=c.SUB_HEAD_CODE\n" + 
         	   	               "  GROUP BY b.GROUP_HEAD_DESC,\n" + 
         	   	               "    b.GROUP_HEAD_CODE,\n" + 
         	   	               "    c.SUB_HEAD_DESC\n" + 
         	   	               "  )\n" + 
         	   	               "GROUP BY GROUP_HEAD_DESC,\n" + 
         	   	               "  GROUP_HEAD_CODE,\n" + 
         	   	               "  SUB_HEAD_DESC,\n" + 
         	   	               "  Funds,\n" + 
         	   	               "  Exp,\n" + 
         	   	               "  SDW\n" + 
         	   	               " \n" + 
         	   	               "ORDER BY GROUP_HEAD_CODE";
                			sup="For Supplement No "+supplimentNo;
                			map.put("supplementNo",sup);
                		}
                		
                		s=request.getRealPath("/org/FAS/FAS1/MIS/jaspers/URBAN_WORKS.jrxml");
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
                else if(strCommand.equalsIgnoreCase("urban_works"))
                {
                	System.out.println("urban");
                	int gc=0;
                	String wk="Works - "+request.getParameter("typeid");
                	
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
            		map.put("wk", wk);
                	try{  
                	if(request.getParameter("typeid").equals("Urban"))
                	{
                		gc=109;
                	}
                	else 
                	{
                		gc=118;
                	}
                		String s = "",myQry="";                		
                		if(isSjv.equalsIgnoreCase("Regular")){
                			System.out.println("Regular");
         	               myQry="SELECT GROUP_HEAD_DESC,\n" + 
         	   	               "  SUB_HEAD_DESC,\n" + 
         	   	               "  Funds,\n" + 
         	   	               "  exp,\n" + 
         	   	               "  SDW,\n" + 
         	   	               "\n" + 
         	   	               "  (Funds+exp+SDW) AS net\n" + 
         	   	               "FROM\n" + 
         	   	               "  (SELECT b.GROUP_HEAD_DESC AS GROUP_HEAD_DESC,\n" + 
         	   	               "    b.GROUP_HEAD_CODE,\n" + 
         	   	               "    c.SUB_HEAD_DESC AS SUB_HEAD_DESC,\n" + 
         	   	               "    Sum(A.Funds)      As Funds,\n" + 
         	   	               "    SUM(a.exp)       AS exp,\n" + 
         	   	               "    SUM(a.SDW)       AS SDW\n" + 
         	   	               "   \n" + 
         	   	               "  FROM\n" + 
         	   	               "    (SELECT t.ACCOUNT_HEAD_CODE AS ACCOUNT_HEAD_CODE,\n" + 
         	   	               "      t1.GROUP_HEAD_CODE,\n" + 
         	   	               "      t1.MINOR_HEAD_CODE,\n" + 
         	   	               "      t1.account_head_code AS account_head,\n" + 
         	   	               "      CASE\n" + 
         	   	               "        WHEN (t1.LEVEL_NO='1')\n" + 
         	   	               "        THEN NVL(SUM(t.CURRENT_MONTH_DEBIT-t.CURRENT_MONTH_CREDIT),0)\n" + 
         	   	               "        Else 0\n" + 
         	   	               "      END AS funds,\n" + 
         	   	               "      CASE\n" + 
         	   	               "        WHEN (t1.LEVEL_NO='2')\n" + 
         	   	               "        THEN NVL(SUM(t.CURRENT_MONTH_DEBIT-t.CURRENT_MONTH_CREDIT),0)\n" + 
         	   	               "        Else 0\n" + 
         	   	               "      END AS exp,\n" + 
         	   	               "      CASE\n" + 
         	   	               "        WHEN (t1.LEVEL_NO='3')\n" + 
         	   	               "        THEN NVL(SUM(t.CURRENT_MONTH_DEBIT-t.CURRENT_MONTH_CREDIT),0)\n" + 
         	   	               "        Else 0\n" + 
         	   	               "      END AS SDW\n" + 
         	   	               "  \n" + 
         	   	               "    \n" + 
         	   	               "    FROM FAS_TRIAL_BALANCE t\n" + 
         	   	               "    LEFT OUTER JOIN fas_mis_exp_group_acc_hd_map t1\n" + 
         	   	               "    ON t.ACCOUNT_HEAD_CODE=t1.account_head_code\n" + 
         	   	               "    WHERE to_date(t.cashbook_month\n" + 
         	   	               "      ||'-'\n" + 
         	   	               "      || t.cashbook_year, 'mm-yyyy') BETWEEN to_date( '"+cashMonth_from+"'\n" + 
         	   	               "      || '-'\n" + 
         	   	               "      || '"+cashYear_from+"', 'mm-yyyy')\n" + 
         	   	               "    AND to_date( '"+cashMonth_to+"'\n" + 
         	   	               "      ||'-'\n" + 
         	   	               "      || '"+cashYear_to+"' , 'mm-yyyy') And (Current_Month_Debit!=0 Or Current_Month_Credit!=0)\n" + 
         	   	             //  "    And T.Accounting_Unit_Id='178'\n" +
         	   	             	strQuery +
         	   	               "    AND t1.GROUP_HEAD_CODE >" +gc+ 
         	   	               "    GROUP BY t.ACCOUNT_HEAD_CODE,\n" + 
         	   	               "      t1.GROUP_HEAD_CODE,\n" + 
         	   	               "      t1.MINOR_HEAD_CODE,\n" + 
         	   	               "      t1.account_head_code,\n" + 
         	   	               "      t1.LEVEL_NO\n" + 
         	   	               "    )a\n" + 
         	   	               "  LEFT OUTER JOIN\n" + 
         	   	               "    (SELECT GROUP_HEAD_CODE,GROUP_HEAD_DESC FROM fas_mis_group_master\n" + 
         	   	               "    )b\n" + 
         	   	               "  ON a.group_head_code=b.GROUP_HEAD_CODE\n" + 
         	   	               "  LEFT OUTER JOIN\n" + 
         	   	               "    (SELECT SUB_HEAD_CODE,SUB_HEAD_DESC FROM com_mst_sub_heads\n" + 
         	   	               "    )c\n" + 
         	   	               "  ON a.minor_head_code=c.SUB_HEAD_CODE\n" + 
         	   	               "  GROUP BY b.GROUP_HEAD_DESC,\n" + 
         	   	               "    b.GROUP_HEAD_CODE,\n" + 
         	   	               "    c.SUB_HEAD_DESC\n" + 
         	   	               "  )\n" + 
         	   	               "GROUP BY GROUP_HEAD_DESC,\n" + 
         	   	               "  GROUP_HEAD_CODE,\n" + 
         	   	               "  SUB_HEAD_DESC,\n" + 
         	   	               "  Funds,\n" + 
         	   	               "  Exp,\n" + 
         	   	               "  SDW\n" + 
         	   	               "ORDER BY GROUP_HEAD_CODE";
                			sup="";
                			map.put("supplementNo",sup);
                		}else if(isSjv.equalsIgnoreCase("InclusiveSJV")){
                			System.out.println("including sjv");
         	               myQry="SELECT GROUP_HEAD_DESC,\n" + 
         	   	               "  SUB_HEAD_DESC,\n" + 
         	   	               "  funds,\n" + 
         	   	               "  exp,\n" + 
         	   	               "  SDW,\n" + 
         	   	               " \n" + 
         	   	               "  (funds+exp+SDW) AS net\n" + 
         	   	               "FROM\n" + 
         	   	               "  (SELECT b.GROUP_HEAD_DESC AS GROUP_HEAD_DESC,\n" + 
         	   	               "    b.GROUP_HEAD_CODE,\n" + 
         	   	               "    c.SUB_HEAD_DESC AS SUB_HEAD_DESC,\n" + 
         	   	               "    SUM(a.funds)      AS funds,\n" + 
         	   	               "    SUM(a.exp)       AS exp,\n" + 
         	   	               "    SUM(a.SDW)       AS SDW\n" + 
         	   	               "  \n" + 
         	   	               "  FROM\n" + 
         	   	               "    (SELECT t.ACCOUNT_HEAD_CODE AS ACCOUNT_HEAD_CODE,\n" + 
         	   	               "      t1.GROUP_HEAD_CODE,\n" + 
         	   	               "      t1.MINOR_HEAD_CODE,\n" + 
         	   	               "      t1.account_head_code AS account_head,\n" + 
         	   	               "      CASE\n" + 
         	   	               "        WHEN (t1.LEVEL_NO='1')\n" + 
         	   	               "        THEN NVL(SUM(t.CURRENT_MONTH_DEBIT-t.CURRENT_MONTH_CREDIT),0)\n" + 
         	   	               "        ELSE 0\n" + 
         	   	               "      END AS funds,\n" + 
         	   	               "      CASE\n" + 
         	   	               "        WHEN (t1.LEVEL_NO='2')\n" + 
         	   	               "        THEN NVL(SUM(t.CURRENT_MONTH_DEBIT-t.CURRENT_MONTH_CREDIT),0)\n" + 
         	   	               "        ELSE 0\n" + 
         	   	               "      END AS exp,\n" + 
         	   	               "      CASE\n" + 
         	   	               "        WHEN (t1.LEVEL_NO='3')\n" + 
         	   	               "        THEN NVL(SUM(t.CURRENT_MONTH_DEBIT-t.CURRENT_MONTH_CREDIT),0)\n" + 
         	   	               "        Else 0\n" + 
         	   	               "      END AS SDW\n" + 
         	   	               "     \n" + 
         	   	               "    FROM FAS_TRIAL_BALANCE t\n" + 
         	   	               "    LEFT OUTER JOIN fas_mis_exp_group_acc_hd_map t1\n" + 
         	   	               "    ON t.ACCOUNT_HEAD_CODE=t1.account_head_code\n" + 
         	   	               "    WHERE to_date(t.cashbook_month\n" + 
         	   	               "      ||'-'\n" + 
         	   	               "      || t.cashbook_year, 'mm-yyyy') BETWEEN to_date( '"+cashMonth_from+"'\n" + 
         	   	               "      || '-'\n" + 
         	   	               "      || '"+cashYear_from+"', 'mm-yyyy')\n" + 
         	   	               "    AND to_date( '"+cashMonth_to+"'\n" + 
         	   	               "      ||'-'\n" + 
         	   	               "      || '"+cashYear_to+"' , 'mm-yyyy') And (Current_Month_Debit!=0 Or Current_Month_Credit!=0)\n" + 
         	   	               strQuery+
         	   	              // "    And T.Accounting_Unit_Id='63'\n" + 
         	   	               "    AND t1.GROUP_HEAD_CODE >"+gc+ 
         	   	               "    GROUP BY t.ACCOUNT_HEAD_CODE,\n" + 
         	   	               "      t1.GROUP_HEAD_CODE,\n" + 
         	   	               "      t1.MINOR_HEAD_CODE,\n" + 
         	   	               "      t1.account_head_code,\n" + 
         	   	               "      t1.LEVEL_NO\n" + 
         	   	               "    UNION ALL\n" + 
         	   	               "    SELECT t.ACCOUNT_HEAD_CODE AS ACCOUNT_HEAD_CODE,\n" + 
         	   	               "      t1.GROUP_HEAD_CODE,\n" + 
         	   	               "      t1.MINOR_HEAD_CODE,\n" + 
         	   	               "      t1.account_head_code AS account_head,\n" + 
         	   	               "      CASE\n" + 
         	   	               "        WHEN (t1.LEVEL_NO='1')\n" + 
         	   	               "        THEN NVL(SUM(t.CURRENT_MONTH_DEBIT-t.CURRENT_MONTH_CREDIT),0)\n" + 
         	   	               "        ELSE 0\n" + 
         	   	               "      END AS funds,\n" + 
         	   	               "      CASE\n" + 
         	   	               "        WHEN (t1.LEVEL_NO='2')\n" + 
         	   	               "        THEN NVL(SUM(t.CURRENT_MONTH_DEBIT-t.CURRENT_MONTH_CREDIT),0)\n" + 
         	   	               "        Else 0\n" + 
         	   	               "      END AS exp,\n" + 
         	   	               "      CASE\n" + 
         	   	               "        WHEN (t1.LEVEL_NO='3')\n" + 
         	   	               "        THEN NVL(SUM(t.CURRENT_MONTH_DEBIT-t.CURRENT_MONTH_CREDIT),0)\n" + 
         	   	               "        ELSE 0\n" + 
         	   	               "      END AS SDW\n" + 
         	   	               "     \n" + 
         	   	               "    FROM FAS_TRIAL_BALANCE_SJV_CMP t\n" + 
         	   	               "    LEFT OUTER JOIN fas_mis_exp_group_acc_hd_map t1\n" + 
         	   	               "    ON t.ACCOUNT_HEAD_CODE=t1.account_head_code\n" + 
         	   	               "    WHERE to_date(t.cashbook_month\n" + 
         	   	               "      ||'-'\n" + 
         	   	               "      || t.cashbook_year, 'mm-yyyy') BETWEEN to_date( '"+cashMonth_from+"'\n" + 
         	   	               "      || '-'\n" + 
         	   	               "      || '"+cashYear_from+"', 'mm-yyyy')\n" + 
         	   	               "    AND to_date( '"+cashMonth_to+"'\n" + 
         	   	               "      ||'-'\n" + 
         	   	               "      || '"+cashYear_to+"' , 'mm-yyyy') And (Current_Month_Debit!=0 Or Current_Month_Credit!=0)\n" + 
         	   	               strQuery +
         	   	               "    AND t1.GROUP_HEAD_CODE >"+gc+ 
         	   	               "    AND t.SUPPLEMENT_NO     = '1'\n" + 
         	   	               "    GROUP BY t.ACCOUNT_HEAD_CODE,\n" + 
         	   	               "      t1.GROUP_HEAD_CODE,\n" + 
         	   	               "      t1.MINOR_HEAD_CODE,\n" + 
         	   	               "      t1.account_head_code,\n" + 
         	   	               "      t1.LEVEL_NO\n" + 
         	   	               "    )a\n" + 
         	   	               "  LEFT OUTER JOIN\n" + 
         	   	               "    (SELECT GROUP_HEAD_CODE,GROUP_HEAD_DESC FROM fas_mis_group_master\n" + 
         	   	               "    )b\n" + 
         	   	               "  ON a.group_head_code=b.GROUP_HEAD_CODE\n" + 
         	   	               "  LEFT OUTER JOIN\n" + 
         	   	               "    (SELECT SUB_HEAD_CODE,SUB_HEAD_DESC FROM com_mst_sub_heads\n" + 
         	   	               "    )c\n" + 
         	   	               "  ON a.minor_head_code=c.SUB_HEAD_CODE\n" + 
         	   	               "  GROUP BY b.GROUP_HEAD_DESC,\n" + 
         	   	               "    b.GROUP_HEAD_CODE,\n" + 
         	   	               "    c.SUB_HEAD_DESC\n" + 
         	   	               "  )\n" + 
         	   	               "GROUP BY GROUP_HEAD_DESC,\n" + 
         	   	               "  GROUP_HEAD_CODE,\n" + 
         	   	               "  SUB_HEAD_DESC,\n" + 
         	   	               "  Funds,\n" + 
         	   	               "  Exp,\n" + 
         	   	               "  SDW\n" + 
         	   	               " \n" + 
         	   	               "ORDER BY GROUP_HEAD_CODE";
                			sup="For Supplement No "+supplimentNo;
                			map.put("supplementNo",sup);
                		}
                		
                		s=request.getRealPath("/org/FAS/FAS1/MIS/jaspers/URBAN_WORKS.jrxml");
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
                else if(strCommand.equalsIgnoreCase("urban_all_detail"))
                {

                	System.out.println("urban_all_detail");
                	int gc=0;
                	String wk="Works - "+request.getParameter("typeid");
                	
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
            		map.put("wk", wk);
            		
                	try{  
                	if(request.getParameter("typeid").equals("Urban"))
                	{
                		gc=109;
                	}
                	else 
                	{
                		gc=118;
                	}
                		String s = "",myQry="";                		
                		if(isSjv.equalsIgnoreCase("Regular")){
                			System.out.println("Regular");
         	               myQry="SELECT GROUP_HEAD_DESC,\n" + 
         	   	               "  Sub_Head_Desc,\n" + 
         	   	               " -- Unitid,\n" + 
         	   	               "  (select U.Accounting_Unit_Name from fas_mst_acct_units u where U.Accounting_Unit_Id=unitid)as unit_name,\n" + 
         	   	               "  Funds,\n" + 
         	   	               "  exp,\n" + 
         	   	               "  SDW,\n" + 
         	   	               "  (Funds+exp+SDW) AS net\n" + 
         	   	               "FROM\n" + 
         	   	               "  (SELECT b.GROUP_HEAD_DESC AS GROUP_HEAD_DESC,\n" + 
         	   	               "    b.GROUP_HEAD_CODE,\n" + 
         	   	               "    C.Sub_Head_Desc As Sub_Head_Desc,\n" + 
         	   	               "    a.Accounting_Unit_Id as unitid,\n" + 
         	   	               "    SUM(A.Funds)    AS Funds,\n" + 
         	   	               "    SUM(a.exp)      AS exp,\n" + 
         	   	               "    SUM(a.SDW)      AS SDW\n" + 
         	   	               "  FROM\n" + 
         	   	               "    (\n" + 
         	   	               "    Select T.Account_Head_Code As Account_Head_Code,\n" + 
         	   	               "    \n" + 
         	   	               "      t1.GROUP_HEAD_CODE,\n" + 
         	   	               "      t1.MINOR_HEAD_CODE,\n" + 
         	   	               "      T1.Account_Head_Code As Account_Head,\n" + 
         	   	               "        T.Accounting_Unit_Id,\n" + 
         	   	               "      CASE\n" + 
         	   	               "        WHEN (t1.LEVEL_NO='1')\n" + 
         	   	               "        THEN NVL(SUM(t.CURRENT_MONTH_DEBIT-t.CURRENT_MONTH_CREDIT),0)\n" + 
         	   	               "        ELSE 0\n" + 
         	   	               "      END AS funds,\n" + 
         	   	               "      CASE\n" + 
         	   	               "        WHEN (t1.LEVEL_NO='2')\n" + 
         	   	               "        THEN NVL(SUM(t.CURRENT_MONTH_DEBIT-t.CURRENT_MONTH_CREDIT),0)\n" + 
         	   	               "        ELSE 0\n" + 
         	   	               "      END AS exp,\n" + 
         	   	               "      CASE\n" + 
         	   	               "        WHEN (t1.LEVEL_NO='3')\n" + 
         	   	               "        THEN NVL(SUM(t.CURRENT_MONTH_DEBIT-t.CURRENT_MONTH_CREDIT),0)\n" + 
         	   	               "        ELSE 0\n" + 
         	   	               "      END AS SDW\n" + 
         	   	               "    FROM FAS_TRIAL_BALANCE t\n" + 
         	   	               "    LEFT OUTER JOIN fas_mis_exp_group_acc_hd_map t1\n" + 
         	   	               "    ON t.ACCOUNT_HEAD_CODE=t1.account_head_code\n" + 
         	   	               "    WHERE to_date(t.cashbook_month\n" + 
         	   	               "      ||'-'\n" + 
         	   	               "      || t.cashbook_year, 'mm-yyyy') BETWEEN to_date( '"+cashMonth_from+"'\n" + 
         	   	               "      || '-'\n" + 
         	   	               "      || '"+cashYear_from+"', 'mm-yyyy')\n" + 
         	   	               "    AND to_date( '"+cashMonth_to+"'\n" + 
         	   	               "      ||'-'\n" + 
         	   	               "      || '"+cashYear_to+"' , 'mm-yyyy') And (Current_Month_Debit!=0 Or Current_Month_Credit!=0)\n" + 
         	   	       	strQuery +
         	   	               "    And T1.Group_Head_Code >"+gc+ 
         	   	               "    GROUP BY t.ACCOUNT_HEAD_CODE,T.Accounting_Unit_Id,\n" + 
         	   	               "      t1.GROUP_HEAD_CODE,\n" + 
         	   	               "      t1.MINOR_HEAD_CODE,\n" + 
         	   	               "      T1.Account_Head_Code,\n" + 
         	   	               "      t1.LEVEL_NO\n" + 
         	   	               "      \n" + 
         	   	               "      order by group_head_code,minor_head_code,T1.Account_Head_Code,T.Accounting_Unit_Id\n" + 
         	   	               "    )a\n" + 
         	   	               "  LEFT OUTER JOIN\n" + 
         	   	               "    (SELECT GROUP_HEAD_CODE,GROUP_HEAD_DESC FROM fas_mis_group_master\n" + 
         	   	               "    )b\n" + 
         	   	               "  ON a.group_head_code=b.GROUP_HEAD_CODE\n" + 
         	   	               "  LEFT OUTER JOIN\n" + 
         	   	               "    (SELECT SUB_HEAD_CODE,SUB_HEAD_DESC FROM com_mst_sub_heads\n" + 
         	   	               "    )c\n" + 
         	   	               "  ON a.minor_head_code=c.SUB_HEAD_CODE\n" + 
         	   	               "  GROUP BY b.GROUP_HEAD_DESC,\n" + 
         	   	               "    B.Group_Head_Code,\n" + 
         	   	               "    C.Sub_Head_Desc,\n" + 
         	   	               "    a.Accounting_Unit_Id\n" + 
         	   	               "  )\n" + 
         	   	               "GROUP BY GROUP_HEAD_DESC,\n" + 
         	   	               "  GROUP_HEAD_CODE,\n" + 
         	   	               "  Sub_Head_Desc,\n" + 
         	   	               "  Unitid,\n" + 
         	   	               "  Funds,\n" + 
         	   	               "  Exp,\n" + 
         	   	               "  Sdw\n" + 
         	   	               "ORDER BY GROUP_HEAD_CODE";   
                			sup="";
                			map.put("supplementNo",sup);
                		}
                		else if(isSjv.equalsIgnoreCase("InclusiveSJV")){
                			System.out.println("including sjv");
         	               myQry="SELECT GROUP_HEAD_DESC,\n" + 
         	   	               "  SUB_HEAD_DESC,\n" + 
         	   	               "  funds,\n" + 
         	   	               " (select U.Accounting_Unit_Name from fas_mst_acct_units u where U.Accounting_Unit_Id=unitid)as unit_name,\n" +
         	   	               "  exp,\n" + 
         	   	               "  SDW,\n" + 
         	   	               " \n" + 
         	   	               "  (funds+exp+SDW) AS net\n" + 
         	   	               "FROM\n" + 
         	   	               "  (SELECT b.GROUP_HEAD_DESC AS GROUP_HEAD_DESC,\n" + 
         	   	               "    b.GROUP_HEAD_CODE,\n" + 
         	   	               "    c.SUB_HEAD_DESC AS SUB_HEAD_DESC,\n" + 
         	   	               " a.Accounting_Unit_Id as unitid,\n"+
         	   	               "    SUM(a.funds)      AS funds,\n" + 
         	   	               "    SUM(a.exp)       AS exp,\n" + 
         	   	               "    SUM(a.SDW)       AS SDW\n" + 
         	   	               "  \n" + 
         	   	               "  FROM\n" + 
         	   	               "    (SELECT t.ACCOUNT_HEAD_CODE AS ACCOUNT_HEAD_CODE,\n" + 
         	   	               "      t1.GROUP_HEAD_CODE,\n" + 
         	   	               "      t1.MINOR_HEAD_CODE,\n" + 
         	   	               "      t1.account_head_code AS account_head,\n" + 
         	   	               "     T.Accounting_Unit_Id,\n"+
         	   	               "      CASE\n" + 
         	   	               "        WHEN (t1.LEVEL_NO='1')\n" + 
         	   	               "        THEN NVL(SUM(t.CURRENT_MONTH_DEBIT-t.CURRENT_MONTH_CREDIT),0)\n" + 
         	   	               "        ELSE 0\n" + 
         	   	               "      END AS funds,\n" + 
         	   	               "      CASE\n" + 
         	   	               "        WHEN (t1.LEVEL_NO='2')\n" + 
         	   	               "        THEN NVL(SUM(t.CURRENT_MONTH_DEBIT-t.CURRENT_MONTH_CREDIT),0)\n" + 
         	   	               "        ELSE 0\n" + 
         	   	               "      END AS exp,\n" + 
         	   	               "      CASE\n" + 
         	   	               "        WHEN (t1.LEVEL_NO='3')\n" + 
         	   	               "        THEN NVL(SUM(t.CURRENT_MONTH_DEBIT-t.CURRENT_MONTH_CREDIT),0)\n" + 
         	   	               "        Else 0\n" + 
         	   	               "      END AS SDW\n" + 
         	   	               "     \n" + 
         	   	               "    FROM FAS_TRIAL_BALANCE t\n" + 
         	   	               "    LEFT OUTER JOIN fas_mis_exp_group_acc_hd_map t1\n" + 
         	   	               "    ON t.ACCOUNT_HEAD_CODE=t1.account_head_code\n" + 
         	   	               "    WHERE to_date(t.cashbook_month\n" + 
         	   	               "      ||'-'\n" + 
         	   	               "      || t.cashbook_year, 'mm-yyyy') BETWEEN to_date( '"+cashMonth_from+"'\n" + 
         	   	               "      || '-'\n" + 
         	   	               "      || '"+cashYear_from+"', 'mm-yyyy')\n" + 
         	   	               "    AND to_date( '"+cashMonth_to+"'\n" + 
         	   	               "      ||'-'\n" + 
         	   	               "      || '"+cashYear_to+"' , 'mm-yyyy') And (Current_Month_Debit!=0 Or Current_Month_Credit!=0)\n" + 
         	   	       	strQuery +
         	   	               "    AND t1.GROUP_HEAD_CODE >"+gc+ 
         	   	               "    GROUP BY t.ACCOUNT_HEAD_CODE,\n" + 
         	   	               "      t1.GROUP_HEAD_CODE,\n" + 
         	   	               "      t1.MINOR_HEAD_CODE,\n" + 
         	   	               "      t1.account_head_code,   T.Accounting_Unit_Id,\n" + 
         	   	               "      t1.LEVEL_NO\n" + 
         	   	               "    UNION ALL\n" + 
         	   	               "    SELECT t.ACCOUNT_HEAD_CODE AS ACCOUNT_HEAD_CODE,\n" + 
         	   	               "      t1.GROUP_HEAD_CODE,\n" + 
         	   	               "      t1.MINOR_HEAD_CODE,\n" + 
         	   	               "      t1.account_head_code AS account_head, T.Accounting_Unit_Id,\n" + 
         	   	               "      CASE\n" + 
         	   	               "        WHEN (t1.LEVEL_NO='1')\n" + 
         	   	               "        THEN NVL(SUM(t.CURRENT_MONTH_DEBIT-t.CURRENT_MONTH_CREDIT),0)\n" + 
         	   	               "        ELSE 0\n" + 
         	   	               "      END AS funds,\n" + 
         	   	               "      CASE\n" + 
         	   	               "        WHEN (t1.LEVEL_NO='2')\n" + 
         	   	               "        THEN NVL(SUM(t.CURRENT_MONTH_DEBIT-t.CURRENT_MONTH_CREDIT),0)\n" + 
         	   	               "        Else 0\n" + 
         	   	               "      END AS exp,\n" + 
         	   	               "      CASE\n" + 
         	   	               "        WHEN (t1.LEVEL_NO='3')\n" + 
         	   	               "        THEN NVL(SUM(t.CURRENT_MONTH_DEBIT-t.CURRENT_MONTH_CREDIT),0)\n" + 
         	   	               "        ELSE 0\n" + 
         	   	               "      END AS SDW\n" + 
         	   	               "     \n" + 
         	   	               "    FROM FAS_TRIAL_BALANCE_SJV_CMP t\n" + 
         	   	               "    LEFT OUTER JOIN fas_mis_exp_group_acc_hd_map t1\n" + 
         	   	               "    ON t.ACCOUNT_HEAD_CODE=t1.account_head_code\n" + 
         	   	               "    WHERE to_date(t.cashbook_month\n" + 
         	   	               "      ||'-'\n" + 
         	   	               "      || t.cashbook_year, 'mm-yyyy') BETWEEN to_date( '"+cashMonth_from+"'\n" + 
         	   	               "      || '-'\n" + 
         	   	               "      || '"+cashYear_from+"', 'mm-yyyy')\n" + 
         	   	               "    AND to_date( '"+cashMonth_to+"'\n" + 
         	   	               "      ||'-'\n" + 
         	   	               "      || '"+cashYear_to+"' , 'mm-yyyy') And (Current_Month_Debit!=0 Or Current_Month_Credit!=0)\n" + 
         	   	       	strQuery +
         	   	               "    AND t1.GROUP_HEAD_CODE >"+gc+ 
         	   	               "    AND t.SUPPLEMENT_NO     = '1'\n" + 
         	   	               "    GROUP BY t.ACCOUNT_HEAD_CODE,\n" + 
         	   	               "      t1.GROUP_HEAD_CODE,\n" + 
         	   	               "      t1.MINOR_HEAD_CODE,\n" + 
         	   	               "      t1.account_head_code, T.Accounting_Unit_Id,\n" + 
         	   	               "      t1.LEVEL_NO\n" + 
         	   	               "    )a\n" + 
         	   	               "  LEFT OUTER JOIN\n" + 
         	   	               "    (SELECT GROUP_HEAD_CODE,GROUP_HEAD_DESC FROM fas_mis_group_master\n" + 
         	   	               "    )b\n" + 
         	   	               "  ON a.group_head_code=b.GROUP_HEAD_CODE\n" + 
         	   	               "  LEFT OUTER JOIN\n" + 
         	   	               "    (SELECT SUB_HEAD_CODE,SUB_HEAD_DESC FROM com_mst_sub_heads\n" + 
         	   	               "    )c\n" + 
         	   	               "  ON a.minor_head_code=c.SUB_HEAD_CODE\n" + 
         	   	               "  GROUP BY b.GROUP_HEAD_DESC,\n" + 
         	   	               "    b.GROUP_HEAD_CODE,\n" + 
         	   	               "    c.SUB_HEAD_DESC,  a.Accounting_Unit_Id\n" + 
         	   	               "  )\n" + 
         	   	               "GROUP BY GROUP_HEAD_DESC,\n" + 
         	   	               "  GROUP_HEAD_CODE,\n" + 
         	   	               "  SUB_HEAD_DESC, unitid,\n" + 
         	   	               "  Funds,\n" + 
         	   	               "  Exp,\n" + 
         	   	               "  SDW\n" + 
         	   	               " \n" + 
         	   	               "ORDER BY GROUP_HEAD_CODE";
                			sup="For Supplement No "+supplimentNo;
                			map.put("supplementNo",sup);
                		}
                		
                		s=request.getRealPath("/org/FAS/FAS1/MIS/jaspers/URBAN_WORKS_detail.jrxml");
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
	}

}
