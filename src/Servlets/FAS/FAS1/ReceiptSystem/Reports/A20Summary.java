package Servlets.FAS.FAS1.ReceiptSystem.Reports;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * Servlet implementation class A20Summary
 */
public class A20Summary extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";  

    public A20Summary() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 Connection connection=null;
	        Statement statement=null;
	        ResultSet result=null,result1=null;
	       // ResultSet result1=null;   
	   	    PreparedStatement ps=null;
	      
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
	        String strCommand = ""; 
	        String xml="";
	 /*       int unit_id = 0;
	        int office_id = 0;
	        int assetmajor=0;
	        String financial_year = null;*/

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
	        System.out.println("Session id is:"+userid);
	        response.setContentType("text/xml");
	        PrintWriter pw=response.getWriter();    
	        response.setHeader("Cache-Control","no-cache");
	        long l=System.currentTimeMillis();
	        Timestamp ts=new Timestamp(l);
	        try
	        {
	        	strCommand = request.getParameter("command");     
	        	System.out.println("strCommand : " + strCommand);
	        }
	        catch(Exception e)
	        {
	          e.printStackTrace();
	        }
	     
	      /*  try
	        {
	        	unit_id = Integer.parseInt(request.getParameter("unit_id"));
	        	office_id = Integer.parseInt(request.getParameter("office_id"));
	        	financial_year = request.getParameter("financial_year");
	        	assetmajor = Integer.parseInt(request.getParameter("assetmajor"));
	System.out.println("accounting_unit_id : " + unit_id);
	        	System.out.println("accounting_unit_office_id : " + office_id);
	        	System.out.println("financial_year : " + financial_year);
	        	System.out.println("assetmajor : " + assetmajor);
	        	
	        }
	        catch(Exception e)
	        { 
	            System.out.println("IGNORABLE Exception getting values from jsp " + e);
	        }*/         
	      
	     if(strCommand.equals("loadMajor"))
	        { 
	        	//System.out.println("\n*************\nloadMajor\n**************\n");
	            xml="<response><command>loadMajor</command>";
	            try 
	            {
	            	String selectQuery="select ASSET_MAJOR_CLASS_CODE,ASSET_MAJOR_CLASS_DESC from FAS_MST_ASSETS_CLASS order by ASSET_MAJOR_CLASS_CODE";
	             
	             ps=connection.prepareStatement(selectQuery);
	 			result=ps.executeQuery();	
	             try
	             {
	            	 xml=xml+"<flag>success</flag>";
	            	 String valExists = "No";
	                 while(result.next())
	                 { 
	                	 valExists = "Yes";
	                	 xml += "<ASSET_MAJOR_CLASS_CODE>" + result.getInt("ASSET_MAJOR_CLASS_CODE") + "</ASSET_MAJOR_CLASS_CODE>";
	                	 xml += "<ASSET_MAJOR_CLASS_DESC><![CDATA[" + result.getString("ASSET_MAJOR_CLASS_DESC") + "]]></ASSET_MAJOR_CLASS_DESC>";
	                 }

	                 xml += "<exists>"+valExists+"</exists>";
	             }catch(Exception e)
	             {
	            	 System.out.println("Exception in getting values from ASSET_MAJOR_CLASS_CODE: " + e);
	             }
	             
	             result.close();

	            }
	            catch(Exception e1)
	            {
	            	System.out.println("Exception is in Get"+e1);
	            	xml=xml+"<flag>failure</flag>";
	            }
	            xml=xml+"</response>";
	        }
	     
	        
	       System.out.println("xml is : " + xml);
	        pw.write(xml);
	        pw.flush();
	        pw.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 Connection connection = null;

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
	        String opt="";
	        //response.setContentType(CONTENT_TYPE);
	        try 
	        {
	        	ResourceBundle rs = ResourceBundle.getBundle("Servlets.Security.servlets.Config");
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
	            connection =DriverManager.getConnection(ConnectionString, strdbusername.trim(),strdbpassword.trim());
	        } catch (Exception ex) {
	            String connectMsg ="Could not create the connection" + ex.getMessage() + " " + 
	            ex.getLocalizedMessage();
	            System.out.println(connectMsg);
	        }
	        JasperDesign jasperDesign = null;
	        File reportFile=null;
	        
	        try 
	        {
	        	System.out.println("A20Summary  ");
	        	  String cmbAcc_UnitCode=request.getParameter("cmbAcc_UnitCode");
		            String cmbOffice_code=request.getParameter("cmbOffice_code");
		            String financialyear=request.getParameter("cmbFinancialYear");
	            String from_txtCB_Year=request.getParameter("from_txtCB_Year");
	            String from_txtCB_Month=request.getParameter("from_txtCB_Month");
	            String to_txtCB_Year=request.getParameter("to_txtCB_Year");
	            String to_txtCB_Month=request.getParameter("to_txtCB_Month");
	            
	           String displayorder=request.getParameter("displayingOrder");
	           
	            //String rtype= request.getParameter("txtoption");
	          
	          
	          
	            int accountingunit=Integer.parseInt(cmbAcc_UnitCode);
	            int accountingoffice=Integer.parseInt(cmbOffice_code);
	                
	            int yearfrom=Integer.parseInt(from_txtCB_Year);
	            int monthfrom=Integer.parseInt(from_txtCB_Month);
	            int yearto=Integer.parseInt(to_txtCB_Year);
	            int monthto=Integer.parseInt(to_txtCB_Month);
	            Map<Integer,String> monthMap = new LinkedHashMap<Integer, String>();
	            monthMap.put(1, "January");
	            monthMap.put(2, "February");
	            monthMap.put(3, "March");
	            monthMap.put(4, "April");
	            monthMap.put(5, "May");
	            monthMap.put(6, "June");
	            monthMap.put(7, "July");
	            monthMap.put(8, "August");
	            monthMap.put(9, "September");
	            monthMap.put(10, "October");
	            monthMap.put(11, "November");
	            monthMap.put(12, "December");
	            int assetmajor=0;
	            if(displayorder.equalsIgnoreCase("ALL")){
	            	   reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/Schedules/A20/A20SummaryAll.jasper"));
	            }else if(displayorder.equalsIgnoreCase("MA")){
	            	assetmajor= Integer.parseInt(request.getParameter("cmbmajorasset"));
	            	   reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/Schedules/A20/A20Summary1.jasper"));
	            }
	          /*  String qry="select "+
" (select accounting_unit_name from fas_mst_acct_units c where c.accounting_unit_office_id="+accountingoffice+" ) as unitname,"+
"  (select ASSET_MAJOR_CLASS_DESC from FAS_MST_ASSETS_CLASS c2 where c2.ASSET_MAJOR_CLASS_CODE="+ assetmajor+")as assetsClass,"+
" (b1.recjanqty+b1.recfebqty+b1.recmarqty+b1.recaprqty+b1.recmayqty+b1.recjunqty+b1.recjulqty+b1.recaugqty+b1.recsepqty+b1.recoctqty+b1.recnovqty+b1.recdecqty) as receiptqtyy,"+
" b3.*,(b1.recjanvalue+b1.recfebvalue+b1.recmarvalue+b1.recaprvalue+b1.recmayvalue+b1.recjunvalue+b1.recjulvalue+b1.recaugvalue+b1.recsepvalue+b1.recoctvalue+b1.recnovvalue+b1.recdecvalue) as receiptvaluee,"+
" (b2.issjanqty+b2.issfebqty+b2.issmarqty+b2.issaprqty+b2.issmayqty+b2.issjunqty+b2.issjulqty+b2.issaugqty+b2.isssepqty+b2.issoctqty+b2.issnovqty+b2.issdecqty)as issuedqty,"+
" (b2.issjanvalue+b2.issfebvalue+b2.issmarvalue+b2.issaprvalue+b2.issmayvalue+b2.issjunvalue+b2.issjulvalue+b2.issaugvalue+b2.isssepvalue+b2.issoctvalue+b2.issnovvalue+b2.issdecvalue) as issuedvalue, "+
" b1.*,b2.* "+
" from  "+
" (select accounting_for_office_id,asset_major_class_code,accounting_unit_id,financial_year,"+

" sum( case     when(b.cashbook_month='1')    then b.received_qty    else 0 end) as recjanqty,"+
" sum(  case    when(b.cashbook_month='1')    then b.received_value   else 0  end) as recjanvalue,"+
" sum(  case    when(b.cashbook_month='2')    then b.received_qty    else 0  end) as recfebqty,"+
"   sum(case    when(b.cashbook_month='2')    then b.received_value    else 0  end) as recfebvalue,"+
"  sum( case    when(b.cashbook_month='3')    then b.received_qty    else 0  end) as recmarqty,"+
"   sum(case    when(b.cashbook_month='3')    then b.received_value  else 0  end) as recmarvalue, "+
"  sum( case    when(b.cashbook_month='4')    then b.received_qty    else 0  end )as recaprqty,"+
"   sum(case    when(b.cashbook_month='4')    then b.received_value   else 0  end) as recaprvalue,"+
"  sum( case    when(b.cashbook_month='5')    then b.received_qty   else 0  end )as recmayqty, "+
"  sum( case    when(b.cashbook_month='5')    then b.received_value    else 0  end )as recmayvalue,"+
" sum( case    when(b.cashbook_month='6')    then b.received_qty    else 0  end )as recjunqty,"+
" sum( case    when(b.cashbook_month='6')    then b.received_value    else 0  end )as recjunvalue,"+
" sum( case    when(b.cashbook_month='7')    then b.received_qty    else 0  end )as recjulqty,"+
" sum( case    when(b.cashbook_month='7')    then b.received_value   else 0  end) as recjulvalue,"+
" sum( case    when(b.cashbook_month='8')    then b.received_qty    else 0  end) as recaugqty,"+
" sum( case    when(b.cashbook_month='8')    then b.received_value    else 0  end )as recaugvalue,"+
" sum( case    when(b.cashbook_month='9')    then b.received_qty    else 0  end) as recsepqty,"+
" sum( case    when(b.cashbook_month='9')    then b.received_value   else 0  end )as recsepvalue,"+
" sum(case    when(b.cashbook_month='10')    then b.received_qty    else 0  end )as recoctqty,"+
" sum( case    when(b.cashbook_month='10')    then b.received_value    else 0  end )as recoctvalue,"+
" sum( case    when(b.cashbook_month='11')    then b.received_qty   else 0  end) as recnovqty,"+
" sum( case    when(b.cashbook_month='11')    then b.received_value   else 0  end) as recnovvalue,"+
" sum( case    when(b.cashbook_month='12')    then b.received_qty    else 0  end )as recdecqty,"+
" sum( CASE    when(b.cashbook_month='12')    THEN b.received_value    ELSE 0  END) AS recdecvalue"+
" FROM fas_assets_receipt b "+
" where  b.accounting_unit_id= "+accountingunit+
" and b.ACCOUNTING_FOR_OFFICE_ID="+accountingoffice+
" and b.FINANCIAL_YEAR='"+financialyear+"'"+
" and b.asset_major_class_code="+assetmajor+
" AND to_date(b.cashbook_month  ||'-'  || b.cashbook_year, 'mm-yyyy') BETWEEN to_date( '"+monthfrom+"' || '-'|| '"+yearfrom+"', 'mm-yyyy')"+
" and to_date( '"+monthto+"'  ||'-'  || '"+yearto+"' , 'mm-yyyy') "+
" group by b.asset_major_class_code,b.accounting_for_office_id,b.accounting_unit_id,b.financial_year)b1"+
" left outer join"+
" (select accounting_for_office_id,asset_major_class_code,accounting_unit_id,financial_year,"+
" sum(case     when(b.cashbook_month='1')    then b.qty_issued     else 0 end )as issjanqty,"+
" sum(  case    when(b.cashbook_month='1')    then b.value_issued    else 0  end) as issjanvalue,"+
"  sum( case    when(b.cashbook_month='2')    then b.qty_issued    else 0  end) as issfebqty,"+
"  sum( case    when(b.cashbook_month='2')    then b.value_issued    else 0  end )as issfebvalue,"+
"  sum( case    when(b.cashbook_month='3')    then b.qty_issued    else 0  end )as issmarqty,"+
"  sum( case    when(b.cashbook_month='3')    then b.value_issued    else 0  end) as issmarvalue, "+
"  sum( case    when(b.cashbook_month='4')    then b.qty_issued    else 0  end )as issaprqty,"+
"  sum( case    when(b.cashbook_month='4')    then b.value_issued    else 0  end) as issaprvalue,"+
"  sum( case    when(b.cashbook_month='5')    then b.qty_issued    else 0  end) as issmayqty, "+
"  sum(case    when(b.cashbook_month='5')    then b.value_issued   else 0  end )as issmayvalue,"+
" sum( case    when(b.cashbook_month='6')    then b.qty_issued  else 0  end) as issjunqty,"+
" sum( case    when(b.cashbook_month='6')    then b.value_issued    else 0  end )as issjunvalue,"+
"  sum(case    when(b.cashbook_month='7')    then b.qty_issued    else 0  end )as issjulqty,"+
" sum( case    when(b.cashbook_month='7')    then b.value_issued    else 0  end) as issjulvalue,"+
" sum( case    when(b.cashbook_month='8')    then b.qty_issued    else 0  end )as issaugqty,"+
"  sum(case    when(b.cashbook_month='8')    then b.value_issued   else 0  end) as issaugvalue,"+
"  sum( case    when(b.cashbook_month='9')    then b.qty_issued    else 0  end )as isssepqty,"+
"  sum( case    when(b.cashbook_month='9')    then b.value_issued    else 0  end) as isssepvalue,"+
"  sum( case    when(b.cashbook_month='10')    then b.qty_issued    else 0  end )as issoctqty,"+
"  sum( case    when(b.cashbook_month='10')    then b.value_issued    else 0  end) as issoctvalue,"+
" sum( case    when(b.cashbook_month='11')    then b.qty_issued    else 0  end )as issnovqty,"+
"  sum(case    when(b.cashbook_month='11')    then b.value_issued   else 0  end )as issnovvalue,"+
" sum( case    when(b.cashbook_month='12')    then b.qty_issued    else 0  end) as issdecqty,"+
" sum( CASE    when(b.cashbook_month='12')    THEN b.VALUE_ISSUED    ELSE 0  END) AS issdecvalue"+
" FROM FAS_ISSUEOF_ASSETS b "+
" where  b.accounting_unit_id="+accountingunit+
" and b.ACCOUNTING_FOR_OFFICE_ID="+accountingoffice+
" and b.FINANCIAL_YEAR='"+financialyear+"'"+
" and b.asset_major_class_code="+assetmajor+
" AND to_date(b.cashbook_month ||'-' || b.cashbook_year, 'mm-yyyy') BETWEEN to_date( '"+monthfrom+"' || '-'|| '"+yearfrom+"', 'mm-yyyy')"+
" and to_date( '"+monthto+"'  ||'-'  || '"+yearto+"' , 'mm-yyyy') "+
" group by accounting_for_office_id, asset_major_class_code, accounting_unit_id, financial_year, asset_major_class_code) b2"+
" on b1.accounting_unit_id=b2.accounting_unit_id"+
" and b1.accounting_for_office_id=b2.accounting_for_office_id"+
" and b1.financial_year=b2.financial_year"+
" and b1.asset_major_class_code=b2.asset_major_class_code"+
" left outer join"+
" (select a1.financial_year,a1.asset_major_class_code,a1.accounting_unit_office_id,"+
" sum(open_bal_qty) as open_bal_qty,sum(opening_bal_value)as opening_bal_value "+
" from fas_a52_register a1 "+
" where a1.accounting_unit_office_id="+accountingoffice+
" and a1.financial_year='"+financialyear+"'"+
" and a1.asset_major_class_code="+assetmajor+
" group by a1.financial_year,a1.asset_major_class_code,a1.accounting_unit_office_id ) b3 "+
" on b1.accounting_for_office_id=b3.accounting_unit_office_id"+
" and  b1.financial_year= b3.financial_year"+
" and b1.asset_major_class_code=b3.asset_major_class_code";*/
	            	
	   String qry="select "+
	   "  (open_bal_qty+receiptqtyy-issuedqty)as closing_bal_qty,"+
	   "  (open_bal_value+receiptvaluee-issuedvalue)as closing_bal_val,"+
	   "  open_bal_qty,open_bal_value,issuedqty,issuedvalue,receiptqtyy ,receiptvaluee,"+
	   "  unitname,"+
	  // "  --(select accounting_unit_name from fas_mst_acct_units c where c.accounting_unit_office_id=b.accounting_for_office_id) as unitname,"+
	   "  (select ASSET_MAJOR_CLASS_DESC from FAS_MST_ASSETS_CLASS c2 where c2.ASSET_MAJOR_CLASS_CODE="+assetmajor+")as assetsClass"+
	   "  from "+
	   "  (select sum(open_bal_qty) as open_bal_qty,sum(opening_bal_value) as open_bal_value,b1.unitName,"+
	   "  b1.issuesqty as issuedqty,b1.issuesvalue as issuedvalue,b2.receiptqty as receiptqtyy,b2.receiptvalue as receiptvaluee"+
	   "  from (select b.accounting_for_office_id,b.accounting_unit_id,b.financial_year,b.asset_major_class_code,cashbook_year,"+
	   "  sum(b.qty_issued) as issuesqty,"+
	   "  sum(b.value_issued) as issuesvalue,"+
	   "  f.accounting_unit_name as unitname"+
	   "   from fas_issueof_assets b"+
	   "   join fas_mst_acct_units f on b.accounting_for_office_id=f.accounting_unit_office_id "+
	 //  "  --group by b.accounting_for_office_id, b.accounting_unit_id, b.financial_year, b.asset_major_class_code, cashbook_year, f.accounting_unit_name"+ 
	   "  where to_date(b.cashbook_month ||'-'|| b.cashbook_year, 'mm-yyyy') between to_date( '"+monthfrom+"' || '-'|| '"+yearfrom+"', 'mm-yyyy')"+
	   "   and  to_date( '"+monthto+"'  ||'-'  || '"+yearto+"', 'mm-yyyy')"+
	   "   and b.asset_major_class_code="+assetmajor+
	   "   and b.accounting_for_office_id="+accountingoffice+
	   "   and b.accounting_unit_id="+accountingunit+
	   "   and b.financial_year='"+financialyear+"'"+
	   "  group by b.accounting_for_office_id, b.accounting_unit_id, b.financial_year, b.asset_major_class_code, cashbook_year, f.accounting_unit_name) b1"+ 
 "  left outer join"+
 "   (select accounting_for_office_id,accounting_unit_id,financial_year,b.asset_major_class_code,cashbook_year,"+
 "  sum(b.received_qty) as receiptqty,"+
"  sum(b.received_value) as receiptvalue"+
"  from FAS_ASSETS_RECEIPT b "+
 "  where to_date(b.cashbook_month ||'-'|| b.cashbook_year, 'mm-yyyy') between to_date( '"+monthfrom+"' || '-'|| '"+yearfrom+"', 'mm-yyyy')"+
 "   and to_date( '"+monthto+"'  ||'-'  || '"+yearto+"', 'mm-yyyy')"+
 "   and asset_major_class_code="+assetmajor+
  "   and accounting_for_office_id="+accountingoffice+
  "   and accounting_unit_id="+accountingunit+
  "   and financial_year='"+financialyear+"'"+
  "  group by accounting_for_office_id, accounting_unit_id, financial_year, b.asset_major_class_code, cashbook_year)b2"+
 "   on b2.accounting_for_office_id=b1.accounting_for_office_id"+
 "   and b2.asset_major_class_code=b1.asset_major_class_code"+
 "   and b2.financial_year=b1.financial_year "+
 "   inner join fas_a52_register a"+
 "   on a.accounting_unit_office_id=b1.accounting_for_office_id"+
 "   and a.asset_major_class_code=b1.asset_major_class_code"+
 "   and a.financial_year=b1.financial_year "+
"  group by b1.unitName, b1.issuesqty, b1.issuesvalue, b2.receiptqty, b2.receiptvalue)";
	            
	          //  System.out.println("qry  "+qry);
	            
	           
	          
	                 
	            if (!reportFile.exists())
	            throw new JRRuntimeException("File J not found. The report design must be compiled first.");
	            
	            JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
                System.out.println("accountingunitid"+accountingunit);
	            System.out.println("accountingoffice"+accountingoffice);
	            System.out.println("monthfrom"+monthfrom);
	            System.out.println("yearfrom"+yearfrom);
	            Map map=new HashMap();   
	            map.put("from_cashbookyear", yearfrom);
                map.put("to_cashbookyear", yearto);
                map.put("from_monthvalue", monthMap.get(monthfrom));
                map.put("to_monthvalue", monthMap.get(monthto));
                map.put("unit",accountingunit);
                map.put("office",accountingoffice);
                map.put("assetmajor",assetmajor);
                map.put("financialyear",financialyear);
                map.put("monthfrom",monthfrom);
                map.put("monthto",monthto);
                
                
  	            //map.put("qry",qry);
  	           
  	            
  	         System.out.println("map values"+map);
	            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, connection);
	           
	         
	          System.out.println("pdf");
	                        byte buf[]=JasperExportManager.exportReportToPdf(jasperPrint);
	                        response.setContentType("application/pdf");
	                        response.setContentLength(buf.length);
	                                         
	                        response.setHeader ("Content-Disposition", "attachment;filename=\"A20SummaryReport.pdf\"");
	                        OutputStream out = response.getOutputStream();
	                        out.write(buf, 0, buf.length);
	                        out.close();
	            
	     
	        } 
	        catch (Exception ex) 
	        {
	            String connectMsg = 
	            "Could not create the report " + ex.getMessage() + " " + 
	            ex.getLocalizedMessage();
	            System.out.println(connectMsg);
	        }
	        
	}

}
