package Servlets.FAS.FAS1.ReceiptSystem.Reports;

import java.io.ByteArrayOutputStream;
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
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * Servlet implementation class A20Receipt_Assets
 */
public class A20Receipt_Assets extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252"; 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public A20Receipt_Assets() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
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
	        int unit_id = 0;
	        int office_id = 0;
	        int assetmajor=0;
	        String financial_year = null;

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
	     
	        try
	        {
	        	unit_id = Integer.parseInt(request.getParameter("unit_id"));
	        	office_id = Integer.parseInt(request.getParameter("office_id"));
	        	financial_year = request.getParameter("financial_year");
	        	assetmajor = Integer.parseInt(request.getParameter("assetmajor"));
	        	
	        /*	System.out.println("accounting_unit_id : " + unit_id);
	        	System.out.println("accounting_unit_office_id : " + office_id);
	        	System.out.println("financial_year : " + financial_year);
	        	System.out.println("assetmajor : " + assetmajor);*/
	        	
	        }
	        catch(Exception e)
	        { 
	            System.out.println("IGNORABLE Exception getting values from jsp " + e);
	        }         
	      
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
	        //	System.out.println("A20Recepitt ");
	        	  String cmbAcc_UnitCode=request.getParameter("cmbAcc_UnitCode");
		            String cmbOffice_code=request.getParameter("cmbOffice_code");
		            String financialyear=request.getParameter("cmbFinancialYear");
	            String from_txtCB_Year=request.getParameter("from_txtCB_Year");
	            String from_txtCB_Month=request.getParameter("from_txtCB_Month");
	            String to_txtCB_Year=request.getParameter("to_txtCB_Year");
	            String to_txtCB_Month=request.getParameter("to_txtCB_Month");
	             int assetmajor= Integer.parseInt(request.getParameter("cmbmajorasset"));
	           
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
	            
	            String qry="select a.particulars, " +
" (select accounting_unit_name from fas_mst_acct_units c where c.accounting_unit_office_id=b.accounting_for_office_id) as unitname," +
" (select accounting_unit_name from fas_mst_acct_units c1 where c1.accounting_unit_office_id=b.received_from_office)as receivedoffice," +
" (select ASSET_MAJOR_CLASS_DESC from FAS_MST_ASSETS_CLASS c2 where c2.ASSET_MAJOR_CLASS_CODE=b.asset_major_class_code)as assetsClass," +
"  a.open_bal_qty," +
" a.opening_bal_value," +
" case when(b.cashbook_month='1') then nvl(b.received_qty,0) else 0 end as janqty, " +
" case when(b.cashbook_month='1') then nvl(b.received_value,0) else 0 end as janvalue, " +
"       case when(b.cashbook_month='2') then nvl(b.received_qty,0) else 0 end as febqty, " +
"        case when(b.cashbook_month='2') then nvl(b.received_value,0) else 0 end as febvalue, " +
"                   case when(b.cashbook_month='3') then nvl(b.received_qty,0) else 0 end as marqty, " +
"                    case when(b.cashbook_month='3') then nvl(b.received_value,0) else 0 end as marvalue," + 
"                    CASE WHEN(b.CASHBOOK_MONTH='4') THEN NVL(b.received_qty,0) ELSE 0 END AS aprqty, " +
"                     case when(b.cashbook_month='4') then nvl(b.received_value,0) else 0 end as aprvalue, " +
"                     case when(b.cashbook_month='5') then nvl(b.received_qty,0) else 0 end as mayqty, " +
"                     case when(b.cashbook_month='5') then nvl(b.received_value,0) else 0 end as mayvalue, " +
"                     case when(b.cashbook_month='6') then nvl(b.received_qty,0) else 0 end as junqty, " +
"                     case when(b.cashbook_month='6') then nvl(b.received_value,0) else 0 end as junvalue, " +
"                     case when(b.cashbook_month='7') then nvl(b.received_qty,0) else 0 end as julqty, " +
"                     case when(b.cashbook_month='7') then nvl(b.received_value,0) else 0 end as julvalue, " +
"                     case when(b.cashbook_month='8') then nvl(b.received_qty,0) else 0 end as augqty, " +
"                     case when(b.cashbook_month='8') then nvl(b.received_value,0) else 0 end as augvalue, " +
"                     case when(b.cashbook_month='9') then nvl(b.received_qty,0) else 0 end as sepqty, " +
"                      case when(b.cashbook_month='9') then nvl(b.received_value,0) else 0 end as sepvalue, " +
"                   case when(b.cashbook_month='10') then nvl(b.received_qty,0) else 0 end as octqty, " +
"                     case when(b.cashbook_month='10') then nvl(b.received_value,0) else 0 end as octvalue, " +
"                     case when(b.cashbook_month='11') then nvl(b.received_qty,0) else 0 end as novqty, " +
"                     case when(b.cashbook_month='11') then nvl(b.received_value,0) else 0 end as novvalue, " +
"                     case when(b.cashbook_month='12') then nvl(b.received_qty,0) else 0 end as decqty, " +
"                     CASE WHEN(b.CASHBOOK_MONTH='12') THEN NVL(b.received_value,0) ELSE 0 END AS decvalue " +
" from fas_assets_receipt b inner join fas_a52_register a" +
" on a.accounting_unit_office_id=b.accounting_for_office_id" +
" and a.asset_code=b.asset_code" +
" and a.asset_major_class_code=b.asset_major_class_code" +
" and a.financial_year=b.financial_year" +
"  where  b.accounting_unit_id= " +accountingunit +
" and b.ACCOUNTING_FOR_OFFICE_ID= " +accountingoffice+
" and b.FINANCIAL_YEAR='" +financialyear+"'"+
" and b.asset_major_class_code=" +assetmajor+
" and to_date(b.cashbook_month ||'-'|| b.cashbook_year, 'mm-yyyy') BETWEEN to_date( '"+monthfrom+"' || '-'|| '"+yearfrom+"', 'mm-yyyy')" +
"  AND to_date( '"+monthto+"'||'-'|| '"+yearto+"' , 'mm-yyyy')";
	            
	           // System.out.println("qry  "+qry);
	            reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/Schedules/A20/A20ReceiptsOfAssets.jasper")); 
	                 
	            if (!reportFile.exists())
	            throw new JRRuntimeException("File J not found. The report design must be compiled first.");
	            
	            JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
	       /*     System.out.println("accountingunitid"+accountingunit);
	            System.out.println("accountingoffice"+accountingoffice);
	            System.out.println("monthfrom"+monthfrom);
	            System.out.println("yearfrom"+yearfrom);*/
	            Map map=new HashMap();
	            
	            map.put("from_cashbookyear", yearfrom);
              //  map.put("from_cashbookmonth", monthfrom);
                map.put("to_cashbookyear", yearto);
               // map.put("to_cashbookmonth", monthto);
               // map.put("accountingunitid", AccountUnitCode);
                map.put("from_monthvalue", monthMap.get(monthfrom));
                map.put("to_monthvalue", monthMap.get(monthto));
             //  map.put("Accounting_unit_id",accountingunit);
  	           // map.put("Accounting_forofficeid",accountingoffice);
  	            map.put("qry",qry);
  	           
  	            
  	         System.out.println("map values"+map);
	            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, connection);
	           
	          /*  if (rtype.equalsIgnoreCase("HTML"))   
	            {
	            	
	                        response.setContentType("text/html");
	                        response.setHeader ("Content-Disposition", "attachment;filename=\"A20ReceiptReport.html\"");
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
	            	*/
	            System.out.println("pdf");
	                        byte buf[]=JasperExportManager.exportReportToPdf(jasperPrint);
	                        response.setContentType("application/pdf");
	                        response.setContentLength(buf.length);
	                                         
	                        response.setHeader ("Content-Disposition", "attachment;filename=\"A20ReceiptReport.pdf\"");
	                        OutputStream out = response.getOutputStream();
	                        out.write(buf, 0, buf.length);
	                        out.close();
	            /*}
	            else  if (rtype.equalsIgnoreCase("EXCEL"))   
	            {
	    
	            	
	                    	response.setContentType("application/vnd.ms-excel");
		                    response.setHeader ("Content-Disposition", "attachment;filename=\"A20ReceiptReport.xls\"");
		                    JRXlsExporter exporterXLS = new JRXlsExporter(); 
		                    exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint); 
	                     
	                        ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
	                        exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,xlsReport); 
	                        exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE); 
	                        exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE); 
		                    exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE); 
		                    exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE); 
		                    exporterXLS.exportReport(); 
		                    byte []bytes;
		                    bytes = xlsReport.toByteArray();
		                    ServletOutputStream ouputStream = response.getOutputStream();
		                    ouputStream.write(bytes, 0, bytes.length);
		                    ouputStream.flush();
		                    ouputStream.close();

	            }
	            else if (rtype.equalsIgnoreCase("TXT"))   
	            {
	            	
	            
			                response.setContentType("text/plain");
			                response.setHeader ("Content-Disposition", "attachment;filename=\"A20ReceiptReport.txt\"");
			                     
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

	            }*/
	     
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