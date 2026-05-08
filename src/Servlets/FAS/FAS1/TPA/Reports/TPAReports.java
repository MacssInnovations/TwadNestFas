package Servlets.FAS.FAS1.TPA.Reports;

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
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

public class TPAReports extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
    Connection connection = null;

   
    public TPAReports() {
        super();
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try
	    {
			System.out.println("this is starting::::::::::");
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
		
	      //  Connection con=
	    String opt="",orgUnitName="",hcode="",heading="",heading_one="";
	    response.setContentType(CONTENT_TYPE);
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

	        //ConnectionString =strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +":" + strsid.trim();
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
		int cmbOffice_code=0,cashYear=0,cashMonth=0,off_id=0;
		String type_prf=null,txtoption=null,officeCode=null,tpaType="",type_query="";
		  String qry="",sub_qry="";   String monthInWords="",sub_heading="";
	//	int cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
	    try 
	    {
	        System.out.println("inside servlet>>>>>superrrrrrrrrrrrr>>>>>..");
	        
	    

	        
	        if(request.getParameter("month_year").equalsIgnoreCase("particular_cb"))
	        {
	        	
	        	
	        	 try{cashYear=Integer.parseInt(request.getParameter("txtCB_Year"));}
	             catch(NumberFormatException e){System.out.println("exception"+e );}
	            
	             cashMonth=Integer.parseInt(request.getParameter("txtCB_Month"));
	             
	             
	             txtoption=request.getParameter("txtoption");//PDF OR EXCEL
	             System.out.println("cashYear"+cashYear);
	             System.out.println("cashMonth"+cashMonth);
	             System.out.println("type_prf"+type_prf);
	             System.out.println("txtoption"+txtoption);
	                
	     
	        if(cashMonth==1)
	            monthInWords="January";
	        else if(cashMonth==2)
	            monthInWords="February";
	        else if(cashMonth==3)
	            monthInWords="March";
	        else if(cashMonth==4)
	            monthInWords="April";
	        else if(cashMonth==5)
	            monthInWords="May";
	        else if(cashMonth==6)
	            monthInWords="June";
	        else if(cashMonth==7)
	            monthInWords="July";
	        else if(cashMonth==8)
	            monthInWords="August";
	        else if(cashMonth==9)
	            monthInWords="September";
	        else if(cashMonth==10)
	            monthInWords="October";
	        else if(cashMonth==11)
	            monthInWords="November";
	        else if(cashMonth==12)
	            monthInWords="December";
	        heading="Unit with TPA without TB Freeze For "+monthInWords+" - "+cashYear; 
	        sub_qry= " AND M.Cashbook_Month          = " +cashMonth+
	        " AND M.Cashbook_Year           = " +cashYear;
	        }else  if(request.getParameter("month_year").equalsIgnoreCase("more_cb"))
	        {
	        	int txtCB_Month_to=0,txtCB_Year_from=0,txtCB_Year_to=0,txtCB_Month_from=0;
	        	
	       	 try{txtCB_Year_from=Integer.parseInt(request.getParameter("txtCB_Year_from"));}
	            catch(NumberFormatException e){System.out.println("exception"+e );}
	            
	            try{txtCB_Year_to=Integer.parseInt(request.getParameter("txtCB_Year_to"));}
	            catch(NumberFormatException e){System.out.println("exception"+e );}
	           
	            txtCB_Month_from=Integer.parseInt(request.getParameter("txtCB_Month_from"));
	            txtCB_Month_to=Integer.parseInt(request.getParameter("txtCB_Month_to"));
	            txtoption=request.getParameter("txtoption");//PDF OR EXCEL
	            
	        
	            if(txtCB_Month_from==1)
	                monthInWords="January";
	            else if(txtCB_Month_from==2)
	                monthInWords="February";
	            else if(txtCB_Month_from==3)
	                monthInWords="March";
	            else if(txtCB_Month_from==4)
	                monthInWords="April";
	            else if(txtCB_Month_from==5)
	                monthInWords="May";
	            else if(txtCB_Month_from==6)
	                monthInWords="June";
	            else if(txtCB_Month_from==7)
	                monthInWords="July";
	            else if(txtCB_Month_from==8)
	                monthInWords="August";
	            else if(txtCB_Month_from==9)
	                monthInWords="September";
	            else if(txtCB_Month_from==10)
	                monthInWords="October";
	            else if(txtCB_Month_from==11)
	                monthInWords="November";
	            else if(txtCB_Month_from==12)
	                monthInWords="December";
	            
	            String monthInWords_to="";
	            if(txtCB_Month_to==1)
	         	   monthInWords_to="January";
	            else if(txtCB_Month_to==2)
	         	   monthInWords_to="February";
	            else if(txtCB_Month_to==3)
	         	   monthInWords_to="March";
	            else if(txtCB_Month_to==4)
	         	   monthInWords_to="April";
	            else if(txtCB_Month_to==5)
	         	   monthInWords_to="May";
	            else if(txtCB_Month_to==6)
	         	   monthInWords_to="June";
	            else if(txtCB_Month_to==7)
	         	   monthInWords_to="July";
	            else if(txtCB_Month_to==8)
	         	   monthInWords_to="August";
	            else if(txtCB_Month_to==9)
	         	   monthInWords_to="September";
	            else if(txtCB_Month_to==10)
	         	   monthInWords_to="October";
	            else if(txtCB_Month_to==11)
	         	   monthInWords_to="November";
	            else if(txtCB_Month_to==12)
	         	   monthInWords_to="December";
	            
	            String from_to="From "+monthInWords+" "+txtCB_Year_from+" to "+monthInWords_to+" "+txtCB_Year_to;
	              
	           heading="Unit with TPA without TB Freeze For The Period From "+monthInWords+" "+txtCB_Year_from+" to "+monthInWords_to+" "+txtCB_Year_to;
	            System.out.println("from_to:::"+from_to);
	            sub_qry=" AND To_Date((m.Cashbook_Month ||'-' || m.Cashbook_Year),'mm-yyyy') BETWEEN To_Date("+txtCB_Month_from+" ||'-' ||"+txtCB_Year_from+",'mm-yyyy') AND to_date("+txtCB_Month_to+" ||'-' ||"+txtCB_Year_to+",'mm-yyyy')" ;
	        }
	        tpaType=request.getParameter("txtTPA");
	        if(tpaType.equalsIgnoreCase("Accept")){
	        	sub_heading="TPA Originated & Accepted";
	        	type_query=" and m.acceptance_status ='Y'";
	        }else if(tpaType.equalsIgnoreCase("NotAccept")) {
	        	sub_heading="TPA Originated & Not Accepted";
	        	type_query=" and m.acceptance_status is null";
	        }else if(tpaType.equalsIgnoreCase("TBNotAccept")) {
	        	sub_heading="TPA Originated & Accepted but TB not Freezed ";
	        	type_query=" and m.acceptance_status  ='Y'";
	        }else{
	        	System.out.println("Error ... ");
	        }
	        
	        qry="SELECT M.Accounting_Unit_Id, " +
	        "  unit.accounting_unit_name, " +
	        "  M.Accounting_For_Office_Id, " +
	        "  Office.office_name, " +
	        "  M.Cashbook_Month, " +
	        "  M.Cashbook_Year, " +
	        "  M.Voucher_No, " +
	        "  M.Tpa_Type, " +
	        "  M.Voucher_Date, " +
	        "  M.Reason_For_Transfer, " +
	        "  M.Particulars, " +
	        "  M.Acceptance_Status, " +
	        "  M.Orgin_Voucher_No, " +
	        "  M.Orgin_Voucher_Date, " +
	        "  M.Accept_Voucher_No, " +
	        "  M.Accept_Voucher_Date, " +
	        " (SELECT ACCOUNTING_UNIT_NAME " +
	        " FROM FAS_MST_ACCT_UNITS unit " +
	        " WHERE Unit.Accounting_Unit_Id=m.ACCOUNTING_UNIT_ID " +
	        " ) " +
	        " AS " +
	        "  Orgname, m.Trf_Accounting_Unit_Id, " +
	        "  (SELECT ACCOUNTING_UNIT_NAME " +
	        "  FROM FAS_MST_ACCT_UNITS unit " +
	        "  WHERE Unit.Accounting_Unit_Id=m.Trf_Accounting_Unit_Id " +
	        "  ) " +
	        " AS " +
	        "  acceptedName,M.Orgin_Voucher_No,M.Orgin_Voucher_Date,M.Accept_Voucher_No,m.ACCEPT_VOUCHER_DATE,"+
	        "  m.status,m.ORG_ACCOUNTING_UNIT_ID, " +
	        "  t.account_head_code, head.account_head_desc," +
	        "  t.cr_dr_indicator, " +
	        "  T.Sub_Ledger_Type_Code,sl.sub_ledger_type_desc, " +
	        "  t.sub_ledger_code,vi.sl_codename, " +
	        "  t.amount, case when T.Cr_Dr_Indicator='CR' then t.amount else 0 end CR_amt,"+
            " case when T.Cr_Dr_Indicator='DR' then t.amount else 0 end DR_amt," +
	        "  t.sl_no " +
	        " FROM Fas_Tpa_Master M " +
	        " INNER JOIN Fas_Tpa_Transaction T " +
	        " ON M.Accounting_Unit_Id       =T.Accounting_Unit_Id " +
	        " AND M.Accounting_For_Office_Id=T.Accounting_For_Office_Id " +
	        " AND M.Cashbook_Month          =T.Cashbook_Month " +
	        " AND M.Cashbook_Year           =T.Cashbook_Year " +
	        " AND M.Voucher_No              =T.Voucher_No " +
	        " INNER JOIN Fas_Mst_Acct_Units Unit " +
	        " ON M.Accounting_Unit_Id=Unit.Accounting_Unit_Id " +
	        " INNER JOIN Com_Mst_Offices Office " +
	        " ON M.Accounting_For_Office_Id=Office.Office_Id " +
	        " Inner Join Com_Mst_Account_Heads Head" +
	        " on t.account_head_code=head.account_head_code" +
	        " Inner Join Com_Mst_Sl_Types Sl" +
	        " on T.Sub_Ledger_Type_Code=sl.sub_ledger_type_code" +
	        " Inner Join Sl_Type_Code_Name_View Vi On t.sub_ledger_type_code=vi.sl_type and t.sub_ledger_code=vi.sl_code" +
	        //" AND M.Accounting_Unit_Id = "+cmbAcc_UnitCode+" AND M.Status                 ='L' " +
	        "  AND M.Status                 ='L' " +
	    sub_qry+type_query+
	        " AND M.Accounting_Unit_Id NOT IN " +
	        "  (SELECT DISTINCT s.Accounting_Unit_Id " +
	        "  FROM FAS_TRIAL_BALANCE_STATUS s " +
	        "  WHERE M.Accounting_Unit_Id    =s.Accounting_Unit_Id " +
	        "  AND M.Accounting_For_Office_Id=s.Accounting_For_Office_Id " +
	        "  AND M.Cashbook_Month          =S.Cashbook_Month " +
	        "  AND M.Cashbook_Year           =S.Cashbook_Year " +
	        "  AND S.Tb_Status               ='Y' " +
	        "  ) order by M.Voucher_No,t.sl_no ";
	        System.out.println("query >>>> "+qry);
	        reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/TPA/jasper/TPA_Report/UnitTPAwithout_TBFreeze_rep.jasper"));
	        
	        
	        
	         System.out.println("reportFile"+reportFile);   
	        if (!reportFile.exists())
	        throw new JRRuntimeException("File J not found. The report design must be compiled first.");
	        
	        JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
	       System.out.println("jasperReport"+jasperReport);
	        Map map=new HashMap();
		             map.put("monthinwords",monthInWords);
		           map.put("qry", qry);
		             map.put("heading",heading);
                    map.put("sub_heading", sub_heading);
		             JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, connection);    
	        
	        System.out.println("jasperPrint:"+jasperPrint);
	        
	            if (txtoption.equalsIgnoreCase("PDF"))   
	            {
	            		System.out.println("PDF:::::::::::");
	                    byte buf[]=JasperExportManager.exportReportToPdf(jasperPrint);
	                    response.setContentType("application/pdf");
	                    response.setContentLength(buf.length);
	                    response.setHeader ("Content-Disposition", "attachment;filename=\"TPA_OFFICEWISE_ALL.pdf\"");
	                    OutputStream out = response.getOutputStream();
	                    out.write(buf, 0, buf.length);
	                    out.close();
	        	}
		        else if (txtoption.equalsIgnoreCase("EXCEL"))   
		        {

	                	response.setContentType("application/vnd.ms-excel");
	                    response.setHeader ("Content-Disposition", "attachment;filename=\"TPA_OFFICEWISE_ALL.xls\"");
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
	           
	    }catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	try
    {
		System.out.println("this is starting::::::::::");
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
	
      //  Connection con=
    String opt="",orgUnitName="",hcode="",heading="",heading_one="";
    response.setContentType(CONTENT_TYPE);
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

        //ConnectionString =strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +":" + strsid.trim();
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
	int cmbOffice_code=0,cashYear=0,cashMonth=0,off_id=0;
	String type_prf=null,txtoption=null,officeCode="";
	
    try 
    {
        System.out.println("inside servlet>>>>>superrrrrrrrrrrrr>>>>>..");
        
        String displayOrder=request.getParameter("displayingOrder");

        
        if(request.getParameter("month_year").equalsIgnoreCase("particular_cb"))
        {
        	
        	
        	 try{cashYear=Integer.parseInt(request.getParameter("txtCB_Year"));}
             catch(NumberFormatException e){System.out.println("exception"+e );}
            
             cashMonth=Integer.parseInt(request.getParameter("txtCB_Month"));
             type_prf=request.getParameter("proformatype");
             if(type_prf.equalsIgnoreCase("TPAOC"))
             {
            	 type_prf=" mst.STATUS='L' and mst.TPA_TYPE='TPAOC' and trn.CR_DR_INDICATOR='CR' and trn.ACCOUNT_HEAD_CODE like '%620101%'"; 
             }
             if(type_prf.equalsIgnoreCase("TPAOD"))
             {
            	 type_prf=" mst.STATUS='L' and mst.TPA_TYPE='TPAOD' and trn.CR_DR_INDICATOR='DR' and trn.ACCOUNT_HEAD_CODE like '%900301%'"; 
             }if(type_prf.equalsIgnoreCase("TPALL"))
             {
            	 type_prf=" mst.STATUS='L' and mst.TPA_TYPE in('TPAOD','TPAOC') and ACCOUNT_HEAD_CODE  in(620101,900301) and CR_DR_INDICATOR in('CR','DR')";
             }
             
            
             txtoption=request.getParameter("txtoption");//PDF OR EXCEL
             System.out.println("cashYear"+cashYear);
             System.out.println("cashMonth"+cashMonth);
             System.out.println("type_prf"+type_prf);
             System.out.println("txtoption"+txtoption);
                
        String monthInWords="";
        if(cashMonth==1)
            monthInWords="January";
        else if(cashMonth==2)
            monthInWords="February";
        else if(cashMonth==3)
            monthInWords="March";
        else if(cashMonth==4)
            monthInWords="April";
        else if(cashMonth==5)
            monthInWords="May";
        else if(cashMonth==6)
            monthInWords="June";
        else if(cashMonth==7)
            monthInWords="July";
        else if(cashMonth==8)
            monthInWords="August";
        else if(cashMonth==9)
            monthInWords="September";
        else if(cashMonth==10)
            monthInWords="October";
        else if(cashMonth==11)
            monthInWords="November";
        else if(cashMonth==12)
            monthInWords="December";
        heading="TPA Register For "+monthInWords+" - "+cashYear; 
        if(displayOrder.equalsIgnoreCase("RW") ||displayOrder.equalsIgnoreCase("OW"))
        {
        	
        	if(displayOrder.equalsIgnoreCase("RW"))
            {
            	cmbOffice_code=Integer.parseInt(request.getParameter("txtRegionId"));
            	
            
        	
        	 try {
        	        PreparedStatement ps=null;
        	        ResultSet rs=null;
        	       
        	        ps=connection.prepareStatement("select ACCOUNTING_UNIT_OFFICE_ID from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=?");
        	        ps.setInt(1,cmbOffice_code);
        	        rs=ps.executeQuery();
        	        if(rs.next()) {
        	            off_id=rs.getInt("ACCOUNTING_UNIT_OFFICE_ID");
        	        }
        	        }
        	        catch (SQLException e) {
        	            System.out.println("SQL Exception -->"+e);
        	        }
        	System.out.println("chesk value of type"+type_prf);
        	
	        
	        	
	        	 reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/TPA/jasper/TPA_Report/TPA_List_All_rw.jasper"));
	        	 System.out.println("TCA_List_All_rw");
	        
       
       
         System.out.println("reportFile"+reportFile);   
        if (!reportFile.exists())
        throw new JRRuntimeException("File J not found. The report design must be compiled first.");
        
        JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
       System.out.println("jasperReport"+jasperReport);
        Map map=new HashMap();
       System.out.println("displayOrder:for :::"+displayOrder);
        System.out.println("off_id:::"+off_id);
        		map.put("cashyear",cashYear);     
	        	 map.put("cashmonth",cashMonth);
	              map.put("type_prf", type_prf); 
	             map.put("monthinwords",monthInWords);
	             map.put("officeCode",cmbOffice_code);
	             map.put("heading",heading);
        	
        	
        
       
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, connection);    
        
        System.out.println("jasperPrint:"+jasperPrint);
        
            if (txtoption.equalsIgnoreCase("PDF"))   
            {
            		System.out.println("PDF:::::::::::");
                    byte buf[]=JasperExportManager.exportReportToPdf(jasperPrint);
                    response.setContentType("application/pdf");
                    response.setContentLength(buf.length);
                    response.setHeader ("Content-Disposition", "attachment;filename=\"TPA_REGIONWISE_ALL.pdf\"");
                    OutputStream out = response.getOutputStream();
                    out.write(buf, 0, buf.length);
                    out.close();
        	}
	        else if (txtoption.equalsIgnoreCase("EXCEL"))   
	        {

                	response.setContentType("application/vnd.ms-excel");
                    response.setHeader ("Content-Disposition", "attachment;filename=\"TPA_REGIONWISE_ALL.xls\"");
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
        
          }
        	else if(displayOrder.equalsIgnoreCase("OW"))
            {
            	cmbOffice_code=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            	try {
        	        PreparedStatement ps=null;
        	        ResultSet rs=null;
        	       
        	        ps=connection.prepareStatement("select ACCOUNTING_UNIT_OFFICE_ID from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=?");
        	        ps.setInt(1,cmbOffice_code);
        	        rs=ps.executeQuery();
        	        if(rs.next()) {
        	            off_id=rs.getInt("ACCOUNTING_UNIT_OFFICE_ID");
        	        }
        	        }
        	        catch (SQLException e) {
        	            System.out.println("SQL Exception -->"+e);
        	        }
        	
        
	        	 reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/TPA/jasper/TPA_Report/TPA_List_All_rw_ow.jasper"));
	        
       
       
         System.out.println("reportFile"+reportFile);   
        if (!reportFile.exists())
        throw new JRRuntimeException("File J not found. The report design must be compiled first.");
        
        JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
       System.out.println("jasperReport"+jasperReport);
        Map map=new HashMap();
       System.out.println("displayOrder:for :::"+displayOrder);
        System.out.println("off_id:::"+off_id);
        		map.put("cashyear",cashYear);     
	        	 map.put("cashmonth",cashMonth);
	        	  map.put("type_prf", type_prf); 
	             map.put("monthinwords",monthInWords);
	             map.put("officeCode",off_id);
	             map.put("heading",heading);
	         	
        	
        	
        
       
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, connection);    
        
        System.out.println("jasperPrint:"+jasperPrint);
        
            if (txtoption.equalsIgnoreCase("PDF"))   
            {
            		System.out.println("PDF:::::::::::");
                    byte buf[]=JasperExportManager.exportReportToPdf(jasperPrint);
                    response.setContentType("application/pdf");
                    response.setContentLength(buf.length);
                    response.setHeader ("Content-Disposition", "attachment;filename=\"TPA_OFFICEWISE_ALL.pdf\"");
                    OutputStream out = response.getOutputStream();
                    out.write(buf, 0, buf.length);
                    out.close();
        	}
	        else if (txtoption.equalsIgnoreCase("EXCEL"))   
	        {

                	response.setContentType("application/vnd.ms-excel");
                    response.setHeader ("Content-Disposition", "attachment;filename=\"TPA_OFFICEWISE_ALL.xls\"");
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
            }
        
        }
        else{
        	System.out.println("all starts:");
		      
		        	 reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/TPA/jasper/TPA_Report/TPA_List_All.jasper"));
		        
		        System.out.println("reportFile for office"+reportFile);   
		        if (!reportFile.exists())
		        throw new JRRuntimeException("File J not found. The report design must be compiled first.");
		        
		        JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
		      
		        System.out.println("cashMonth::"+cashMonth);
		        System.out.println("cashYear::"+cashYear);
		        
		        Map map=new HashMap();
		       
		        map.put("cashyear",cashYear);     
	        	 map.put("cashmonth",cashMonth);
	        	 map.put("type_prf", type_prf); 
	             map.put("monthinwords",monthInWords);
	             map.put("heading",heading);
	         	
	       System.out.println("map::::"+map);
	       
	        
		        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, connection);            
	            if (txtoption.equalsIgnoreCase("PDF"))   
	            {
	            		System.out.println("PDF:::::::::::");
	                    byte buf[]=JasperExportManager.exportReportToPdf(jasperPrint);
	                    response.setContentType("application/pdf");
	                    response.setContentLength(buf.length);
	                    response.setHeader ("Content-Disposition", "attachment;filename=\"TPA_ALL.pdf\"");
	                    OutputStream out = response.getOutputStream();
	                    out.write(buf, 0, buf.length);
	                    out.close();
	        	}
		        else      if (txtoption.equalsIgnoreCase("EXCEL"))   
		        {

	                	response.setContentType("application/vnd.ms-excel");
	                    response.setHeader ("Content-Disposition", "attachment;filename=\"TPA_ALL.xls\"");
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
        }
        
    }
        else  if(request.getParameter("month_year").equalsIgnoreCase("more_cb"))
        {
        	int txtCB_Month_to=0,txtCB_Year_from=0,txtCB_Year_to=0,txtCB_Month_from=0;
        	
       	 try{txtCB_Year_from=Integer.parseInt(request.getParameter("txtCB_Year_from"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            
            try{txtCB_Year_to=Integer.parseInt(request.getParameter("txtCB_Year_to"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
           
            txtCB_Month_from=Integer.parseInt(request.getParameter("txtCB_Month_from"));
            txtCB_Month_to=Integer.parseInt(request.getParameter("txtCB_Month_to"));
            
            type_prf=request.getParameter("proformatype");
            if(type_prf.equalsIgnoreCase("TPAOC"))
            {
           	 type_prf=" mst.STATUS='L' and mst.TPA_TYPE='TPAOC' and trn.CR_DR_INDICATOR='CR' and trn.ACCOUNT_HEAD_CODE like '%620101%'"; 
            }
            if(type_prf.equalsIgnoreCase("TPAOD"))
            {
           	 type_prf=" mst.STATUS='L' and mst.TPA_TYPE='TPAOD' and trn.CR_DR_INDICATOR='DR' and trn.ACCOUNT_HEAD_CODE like '%900301%'"; 
            }
            if(type_prf.equalsIgnoreCase("TPALL"))
            {
            type_prf=" mst.STATUS='L' and mst.TPA_TYPE in('TPAOD','TPAOC') and ACCOUNT_HEAD_CODE  in(620101,900301) and CR_DR_INDICATOR in('CR','DR')";
            }
            txtoption=request.getParameter("txtoption");//PDF OR EXCEL
            
               
       String monthInWords="";
       if(txtCB_Month_from==1)
           monthInWords="January";
       else if(txtCB_Month_from==2)
           monthInWords="February";
       else if(txtCB_Month_from==3)
           monthInWords="March";
       else if(txtCB_Month_from==4)
           monthInWords="April";
       else if(txtCB_Month_from==5)
           monthInWords="May";
       else if(txtCB_Month_from==6)
           monthInWords="June";
       else if(txtCB_Month_from==7)
           monthInWords="July";
       else if(txtCB_Month_from==8)
           monthInWords="August";
       else if(txtCB_Month_from==9)
           monthInWords="September";
       else if(txtCB_Month_from==10)
           monthInWords="October";
       else if(txtCB_Month_from==11)
           monthInWords="November";
       else if(txtCB_Month_from==12)
           monthInWords="December";
       
       String monthInWords_to="";
       if(txtCB_Month_to==1)
    	   monthInWords_to="January";
       else if(txtCB_Month_to==2)
    	   monthInWords_to="February";
       else if(txtCB_Month_to==3)
    	   monthInWords_to="March";
       else if(txtCB_Month_to==4)
    	   monthInWords_to="April";
       else if(txtCB_Month_to==5)
    	   monthInWords_to="May";
       else if(txtCB_Month_to==6)
    	   monthInWords_to="June";
       else if(txtCB_Month_to==7)
    	   monthInWords_to="July";
       else if(txtCB_Month_to==8)
    	   monthInWords_to="August";
       else if(txtCB_Month_to==9)
    	   monthInWords_to="September";
       else if(txtCB_Month_to==10)
    	   monthInWords_to="October";
       else if(txtCB_Month_to==11)
    	   monthInWords_to="November";
       else if(txtCB_Month_to==12)
    	   monthInWords_to="December";
       
       String from_to="From "+monthInWords+" "+txtCB_Year_from+" to "+monthInWords_to+" "+txtCB_Year_to;
         
      heading="TPA Register For The Period From "+monthInWords+" "+txtCB_Year_from+" to "+monthInWords_to+" "+txtCB_Year_to;
       System.out.println("from_to:::"+from_to);
       if(displayOrder.equalsIgnoreCase("RW") ||displayOrder.equalsIgnoreCase("OW"))
       {
       	
       	if(displayOrder.equalsIgnoreCase("RW"))
           {
           	cmbOffice_code=Integer.parseInt(request.getParameter("txtRegionId"));
           	
          
       	
       	 try {
       	        PreparedStatement ps=null;
       	        ResultSet rs=null;
       	       
       	        ps=connection.prepareStatement("select ACCOUNTING_UNIT_OFFICE_ID from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=?");
       	        ps.setInt(1,cmbOffice_code);
       	        rs=ps.executeQuery();
       	        if(rs.next()) {
       	            off_id=rs.getInt("ACCOUNTING_UNIT_OFFICE_ID");
       	        }
       	        }
       	        catch (SQLException e) {
       	            System.out.println("SQL Exception -->"+e);
       	        }
       	
      
	        	 reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/TPA/jasper/TPA_Report/All_TPA_List_rw_year.jasper"));
	        
      
      
        System.out.println("reportFile"+reportFile);   
       if (!reportFile.exists())
       throw new JRRuntimeException("File J not found. The report design must be compiled first.");
       
       JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
      System.out.println("jasperReport"+jasperReport);
       Map map=new HashMap();
      System.out.println("displayOrder:for :::"+displayOrder);
       System.out.println("off_id:::"+off_id);
       System.out.println("cmbOffice_code for region:::"+cmbOffice_code);
       			 map.put("cashyear_from",txtCB_Year_from);     
	        	 map.put("cashyear_to",txtCB_Year_to);
	        	 
	        	 map.put("cashmonth_from",txtCB_Month_from);     
	        	 map.put("cashmonth_to",txtCB_Month_to);
	        	 map.put("type_prf", type_prf); 
	             map.put("from_to",from_to);
	             map.put("officeCode",cmbOffice_code);
	             map.put("heading",heading);
	         	
	             JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, connection);    
	             
	             System.out.println("jasperPrint:"+jasperPrint);
	             
	                 if (txtoption.equalsIgnoreCase("PDF"))   
	                 {
	                 		System.out.println("PDF:::::::::::");
	                         byte buf[]=JasperExportManager.exportReportToPdf(jasperPrint);
	                         response.setContentType("application/pdf");
	                         response.setContentLength(buf.length);
	                         response.setHeader ("Content-Disposition", "attachment;filename=\"TPA_ALL_date.pdf\"");
	                         OutputStream out = response.getOutputStream();
	                         out.write(buf, 0, buf.length);
	                         out.close();
	             	}
	      	        else      if (txtoption.equalsIgnoreCase("EXCEL"))   
	      	        {

	                     	response.setContentType("application/vnd.ms-excel");
	                         response.setHeader ("Content-Disposition", "attachment;filename=\"TPA_ALL_date.xls\"");
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
       	
       }
        else if(displayOrder.equalsIgnoreCase("OW"))
        {
        	cmbOffice_code=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
        	try {
       	        PreparedStatement ps=null;
       	        ResultSet rs=null;
       	       
       	        ps=connection.prepareStatement("select ACCOUNTING_UNIT_OFFICE_ID from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=?");
       	        ps.setInt(1,cmbOffice_code);
       	        rs=ps.executeQuery();
       	        if(rs.next()) {
       	            off_id=rs.getInt("ACCOUNTING_UNIT_OFFICE_ID");
       	        }
       	        }
       	        catch (SQLException e) {
       	            System.out.println("SQL Exception -->"+e);
       	        }
       	
       	
	        	 reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/TPA/jasper/TPA_Report/All_TPA_List_Ow_year.jasper"));
	        
      
      
        System.out.println("reportFile"+reportFile);   
       if (!reportFile.exists())
       throw new JRRuntimeException("File J not found. The report design must be compiled first.");
       
       JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
      System.out.println("jasperReport"+jasperReport);
       Map map=new HashMap();
      System.out.println("displayOrder:for :::"+displayOrder);
       System.out.println("off_id:::"+off_id);
      
			 map.put("cashyear_from",txtCB_Year_from);     
        	 map.put("cashyear_to",txtCB_Year_to);
        	 map.put("type_prf", type_prf); 
        	 map.put("cashmonth_from",txtCB_Month_from);     
        	 map.put("cashmonth_to",txtCB_Month_to);
        	  map.put("heading",heading);
          	
	             map.put("from_to",from_to);
	             map.put("officeCode",off_id);
	             JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, connection);    
	             
	             System.out.println("jasperPrint:"+jasperPrint);
	             
	                 if (txtoption.equalsIgnoreCase("PDF"))   
	                 {
	                 		System.out.println("PDF:::::::::::");
	                         byte buf[]=JasperExportManager.exportReportToPdf(jasperPrint);
	                         response.setContentType("application/pdf");
	                         response.setContentLength(buf.length);
	                         response.setHeader ("Content-Disposition", "attachment;filename=\"TPA_ALL_date.pdf\"");
	                         OutputStream out = response.getOutputStream();
	                         out.write(buf, 0, buf.length);
	                         out.close();
	             	}
	      	        else      if (txtoption.equalsIgnoreCase("EXCEL"))   
	      	        {

	                     	response.setContentType("application/vnd.ms-excel");
	                         response.setHeader ("Content-Disposition", "attachment;filename=\"TPA_ALL_date.xls\"");
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
        }
      
       
       }
       else{
       	System.out.println("all starts from datewise:");
		       
		        	 reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/TPA/jasper/TPA_Report/All_TPA_List_year.jasper"));
		        
		        System.out.println("reportFile for office"+reportFile);   
		        if (!reportFile.exists())
		        throw new JRRuntimeException("File J not found. The report design must be compiled first.");
		        
		        JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
		      
		        System.out.println("cashMonth::"+cashMonth);
		        System.out.println("cashYear::"+cashYear);
		        
		        Map map=new HashMap();
		       
      			 map.put("cashyear_from",txtCB_Year_from);     
	        	 map.put("cashyear_to",txtCB_Year_to);
	        	 
	        	 map.put("cashmonth_from",txtCB_Month_from);     
	        	 map.put("cashmonth_to",txtCB_Month_to);
	        	 map.put("type_prf", type_prf); 
	             map.put("from_to",from_to);
	             map.put("heading",heading);
	         	
	       System.out.println("map::::"+map);
	       
	        
		        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, connection);            
	            if (txtoption.equalsIgnoreCase("PDF"))   
	            {
	            		System.out.println("PDF:::::::::::");
	                    byte buf[]=JasperExportManager.exportReportToPdf(jasperPrint);
	                    response.setContentType("application/pdf");
	                    response.setContentLength(buf.length);
	                    response.setHeader ("Content-Disposition", "attachment;filename=\"TPA_ALL_date.pdf\"");
	                    OutputStream out = response.getOutputStream();
	                    out.write(buf, 0, buf.length);
	                    out.close();
	        	}
		        else      if (txtoption.equalsIgnoreCase("EXCEL"))   
		        {

	                	response.setContentType("application/vnd.ms-excel");
	                    response.setHeader ("Content-Disposition", "attachment;filename=\"TPA_ALL_date.xls\"");
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
       }
        }
 
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