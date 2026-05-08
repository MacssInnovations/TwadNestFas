package Servlets.FAS.FAS1.BRS.servlets;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
/**
 * Servlet implementation class BRSReport_Part2A
 */
public class ClearanceReport extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClearanceReport() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    PrintWriter out = response.getWriter();
	     response.setHeader("cache-control","no-cache");
	      String CONTENT_TYPE = "text/xml; charset=windows-1252";
	      response.setContentType(CONTENT_TYPE);
	    Connection con = null;
	    
	    try {
	            HttpSession session = request.getSession(false);
	            if (session == null) {
	                    System.out.println(request.getContextPath() + "/index.jsp");
	                    response.sendRedirect(request.getContextPath() + "/index.jsp");
	                    return;
	            }
	            System.out.println(session);

	    } catch (Exception e) {
	            System.out.println("Redirect Error :" + e);
	    }
	    try {
	            ResourceBundle rs1 = ResourceBundle
	                            .getBundle("Servlets.Security.servlets.Config");
	            String ConnectionString = "";

	            String strDriver = rs1.getString("Config.DATA_BASE_DRIVER");
	            String strdsn = rs1.getString("Config.DSN");
	            String strhostname = rs1.getString("Config.HOST_NAME");
	            String strportno = rs1.getString("Config.PORT_NUMBER");
	            String strsid = rs1.getString("Config.SID");
	            String strdbusername = rs1.getString("Config.USER_NAME");
	            String strdbpassword = rs1.getString("Config.PASSWORD");
	            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection

	            Class.forName(strDriver.trim());
	            con = DriverManager.getConnection(ConnectionString, strdbusername
	                            .trim(), strdbpassword.trim());
	    } catch (Exception e) {
	            System.out.println("Exception in opening connection :" + e);
	            // sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

	    } 
	    HttpSession session=request.getSession(false);
        try
        {
            
            if(session==null)
            {
                System.out.println(request.getContextPath()+"/index.jsp");
                response.sendRedirect(request.getContextPath()+"/index.jsp");
                return;
            }
            System.out.println(session);
                
        }catch(Exception e)
        {
        System.out.println("Redirect Error :"+e);
        } 
	    String update_user=(String)session.getAttribute("UserId");
        long l=System.currentTimeMillis();
        Timestamp ts=new Timestamp(l);
	    PreparedStatement ps2=null,ps=null,ps1=null;
	    ResultSet rs2=null,rs=null,rs1=null;
	    String cmd="",xml=null;
	    int count=0 ,cashbook_year=0, cashbook_month=0;
	    int cmbAcc_UnitCode=0 ,cmbAcc_officecode=0;
	    try
	    {
	    cmd=request.getParameter("command");
	    cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
	    cmbAcc_officecode = Integer.parseInt(request.getParameter("cmbAcc_officecode"));	    
	    
	    cashbook_year = Integer.parseInt(request.getParameter("cashbook_yr"));
	    cashbook_month = Integer.parseInt(request.getParameter("cashbook_mn"));
	    
	    }catch (Exception e) {
	            e.printStackTrace();
	    }
	    System.out.println("cmd---------"+cmd);
	    
	
	    
	    
	    if(cmd.equalsIgnoreCase("LoadBankAccountNumber"))
	    {
	         /*   String sql =	"SELECT a.ACCOUNT_NO " +
	            		"FROM FAS_BRS_CLEARANCE a, " +
	            		"  FAS_BRS_TRANSACTION b " +
	            		"WHERE a.accounting_unit_id    =b.accounting_unit_id " +
	            		"AND a.accounting_for_office_id=b.accounting_for_office_id";*/
	                                                                                                
	            String sql="   select *      "+       													
		" from 		   "+  															
		" (				   "+  														
		" 	select		   "+  														
		" 		bank_id,	   "+  													
		" 		BRANCH_ID,	   "+  													
		" 		bank_ac_no,    "+  													
		" 		AC_OPERATIONAL_MODE_ID,       "+                                       
		" 		trim(AC_OPERATIONAL_MODE_ID)||'/'||(bank_ac_no) as acc_no	   "+  		    
		" 	from	   "+  															
		" 		fas_mst_bank_balance	   "+  											
		" 	where	   "+  															
		" 		accounting_unit_id = ? and status='Y'  			   "+  							
		" )X	   "+  																	
		" left outer join		   "+  													
		" (		   "+  																
		" 		select bank_id as y_bank_id ,trim(BANK_SHORT_NAME) as y_bank_name from fas_bank_list	   "+  
		" )Y			   "+  															
   "  on 				   "+  														
   "    X.bank_id  = Y.y_bank_id	   "+  												
  "   left outer join 	   "+  														
  "   (		   "+  																	
  "     select  BANK_ID as z_bank_id, BRANCH_ID as z_BRANCH_ID ,trim(BRANCH_NAME) as z_BRANCH_NAME from fas_mst_bank_branches	   "+  
  "   )Z        "+                                 										
	"  on  		   "+  																
  "     X.bank_id  = Z.z_bank_id  and	   "+  										
  "     X.BRANCH_ID = Z.z_branch_id	order by bank_id,bank_ac_no,AC_OPERATIONAL_MODE_ID" ;                                                                                                                                                         
	            
	     System.out.println("sql:::"+sql);
	    
	        try
	        {
	                  ps2=con.prepareStatement(sql);
	                 ps2.setInt(1, cmbAcc_UnitCode);
	                  rs2=ps2.executeQuery();
	               
	                  System.out.print("haiiiiiiiiiiiiiiiiiiiiiiiiiii");
	                  xml="<response><command>LoadBankAccountNumber</command>";
	                  
	                  /** Count How many Records are available  */
	                 while(rs2.next()) 
	                  {
	                	  System.out.print("mmmmmmmmmm");
	                    xml=xml+ "<acc_no>"+ rs2.getString("acc_no") +"</acc_no>";  
	                     
	                    xml=xml+ "<bank_id>"+ rs2.getString("bank_id") +"</bank_id>";  
	                     xml=xml+ "<branch_id>"+ rs2.getString("branch_id") +"</branch_id>";                 
	                     xml=xml+ "<opr_mode>"+ rs2.getString("AC_OPERATIONAL_MODE_ID") +"</opr_mode>";                 
	                     xml=xml+ "<acc_desc>"+ rs2.getString("acc_no") +"-"+ rs2.getString("y_bank_name") +"</acc_desc>";
	                     xml=xml+ "<bank_name>"+ rs2.getString("y_bank_name") +"</bank_name>";                                           
	                     xml=xml+ "<branch_name>"+ rs2.getString("z_BRANCH_NAME") +"</branch_name>";
	                     count++;
	                  }
	                  
	                  if(count==0) {
	                      xml=xml+"<flag>NoData</flag>";
	                  }
	                  else{                
	                      xml=xml+"<flag>success</flag>";
	                  }              
	       }
	           catch(Exception e) 
	           {
	                  System.out.println("Exception in assigning..."+e);
	                  xml=xml+"<flag>failure</flag>";
	           }
	           xml = xml + "</response>";
	                    System.out.println(xml);
	                    out.println(xml);
	    }
	    if(cmd.equalsIgnoreCase("LoadBankAccountNo"))
	    {
	         	                                                                                                
	            String sql="  SELECT * FROM    (SELECT bank_id, BRANCH_ID, "
	            		+ " Account_No,  OPERATIONAL_MODE,    trim(OPERATIONAL_MODE)    ||'-'    ||Trim(ACCOUNT_NO) As Acc_No "
	            		+ "  From Fas_Brs_Master Where "
	            		+ " Accounting_Unit_Id =?  "
	            		+ "  And  Cashbook_Year=?  "
	            		+ "  and cashbook_month=?   )X "
	            		+ " LEFT OUTER JOIN "
	            		+ " (SELECT bank_id         AS y_bank_id , "
	            		+ " trim(BANK_SHORT_NAME) AS y_bank_name "
	            		+ " FROM fas_bank_list  )Y "
	            		+ " ON X.bank_id = Y.y_bank_id "
	            		+ " LEFT OUTER JOIN "
	            		+ "  (SELECT BANK_ID     AS z_bank_id, "
	            		+ "  BRANCH_ID         AS z_BRANCH_ID , "
	            		+ " trim(BRANCH_NAME) AS z_BRANCH_NAME  "
	            		+ "  FROM fas_mst_bank_branches  )Z "
	            		+ " ON X.Bank_Id    = Z.Z_Bank_Id "
	            		+ " AND X.BRANCH_ID = Z.z_branch_id  "
	            		+ " ORDER BY bank_id,  Account_No,  OPERATIONAL_MODE " ;                                                                                                                                                         
	            
	     System.out.println("sql:::"+sql);
	    
	        try
	        {
	        	System.out.println("cmbAcc_UnitCode="+cmbAcc_UnitCode+"cashbook_year="+cashbook_year+"cashbook_month="+cashbook_month);
	                  ps2=con.prepareStatement(sql);
	                 ps2.setInt(1, cmbAcc_UnitCode);
	                 ps2.setInt(2, cashbook_year);
	                 ps2.setInt(3, cashbook_month);
	                  rs2=ps2.executeQuery();
	               
	                  System.out.print("haiiiiiiiiiiiiiiiiiiiiiiiiiii");
	                  xml="<response><command>LoadBankAccountNo</command>";
	                  
	                  /** Count How many Records are available  */
	                 while(rs2.next()) 
	                  {
	                	  System.out.print("mmmmmmmmmm");
	                    xml=xml+ "<acc_no>"+ rs2.getString("Acc_No") +"</acc_no>";  
	                     
	                    xml=xml+ "<bank_id>"+ rs2.getString("bank_id") +"</bank_id>";  
	                     xml=xml+ "<branch_id>"+ rs2.getString("branch_id") +"</branch_id>";                 
	                     xml=xml+ "<opr_mode>"+ rs2.getString("OPERATIONAL_MODE") +"</opr_mode>";                 
	                     xml=xml+ "<acc_desc>"+ rs2.getString("Acc_No") +"-"+ rs2.getString("y_bank_name") +"</acc_desc>";
	                     xml=xml+ "<bank_name>"+ rs2.getString("y_bank_name") +"</bank_name>";                                           
	                     xml=xml+ "<branch_name>"+ rs2.getString("z_BRANCH_NAME") +"</branch_name>";
	                     count++;
	                  }
	                  
	                  if(count==0) {
	                      xml=xml+"<flag>NoData</flag>";
	                  }
	                  else{                
	                      xml=xml+"<flag>success</flag>";
	                  }              
	       }
	           catch(Exception e) 
	           {
	                  System.out.println("Exception in assigning..."+e);
	                  xml=xml+"<flag>failure</flag>";
	           }
	           xml = xml + "</response>";
	                    System.out.println(xml);
	                    out.println(xml);
	    }
	    if(cmd.equalsIgnoreCase("LoadBankAccountNo1"))
	    {
	         	                                                                                                
	          /*  String sql="  SELECT * FROM    (SELECT bank_id, BRANCH_ID, "
	            		+ " Account_No,  OPERATIONAL_MODE,    trim(OPERATIONAL_MODE)    ||'-'    ||Trim(ACCOUNT_NO) As Acc_No "
	            		+ "  From Fas_Brs_Master Where "
	            		+ " Accounting_Unit_Id =?  "
	            		+ "  And  Cashbook_Year=?  "
	            		+ "  and cashbook_month=?   )X "
	            		+ " LEFT OUTER JOIN "
	            		+ " (SELECT bank_id         AS y_bank_id , "
	            		+ " trim(BANK_SHORT_NAME) AS y_bank_name "
	            		+ " FROM fas_bank_list  )Y "
	            		+ " ON X.bank_id = Y.y_bank_id "
	            		+ " LEFT OUTER JOIN "
	            		+ "  (SELECT BANK_ID     AS z_bank_id, "
	            		+ "  BRANCH_ID         AS z_BRANCH_ID , "
	            		+ " trim(BRANCH_NAME) AS z_BRANCH_NAME  "
	            		+ "  FROM fas_mst_bank_branches  )Z "
	            		+ " ON X.Bank_Id    = Z.Z_Bank_Id "
	            		+ " AND X.BRANCH_ID = Z.z_branch_id  "
	            		+ " ORDER BY bank_id,  Account_No,  OPERATIONAL_MODE " ;        */    
	    	
	    	 String sql=" SELECT distinct (trim(account_no)) as Account_no  "
	    	 		+ " FROM fas_brs_transaction_noentry "
	    	 		+ " WHERE accounting_unit_id    =?"
	    	 		+ " AND accounting_for_office_id=?"
	    	 		+ " AND cashbook_year           =?"
	    	 		+ " AND cashbook_month          =?";
	            
	     System.out.println("sql:::"+sql);
	    
	        try
	        {
	        	System.out.println("cmbAcc_UnitCode="+cmbAcc_UnitCode+"cashbook_year="+cashbook_year+"cashbook_month="+cashbook_month);
	                  ps2=con.prepareStatement(sql);
	                 ps2.setInt(1, cmbAcc_UnitCode);
	                 ps2.setInt(2,cmbAcc_officecode);
	                 ps2.setInt(3, cashbook_year);
	                 ps2.setInt(4, cashbook_month);
	                  rs2=ps2.executeQuery();
	               
	                  System.out.print("haiiiiiiiiiiiiiiiiiiiiiiiiiii");
	                  xml="<response><command>LoadBankAccountNo1</command>";
	                  
	                  /** Count How many Records are available  */
	                 while(rs2.next()) 
	                  {
	                	  System.out.print("mmmmmmmmmm");
	                    xml=xml+ "<acc_no>"+ rs2.getString("Account_no") +"</acc_no>";  
	                     
	                   /* xml=xml+ "<bank_id>"+ rs2.getString("bank_id") +"</bank_id>";  
	                     xml=xml+ "<branch_id>"+ rs2.getString("branch_id") +"</branch_id>";                 
	                     xml=xml+ "<opr_mode>"+ rs2.getString("OPERATIONAL_MODE") +"</opr_mode>";                 
	                     xml=xml+ "<acc_desc>"+ rs2.getString("Acc_No") +"-"+ rs2.getString("y_bank_name") +"</acc_desc>";
	                     xml=xml+ "<bank_name>"+ rs2.getString("y_bank_name") +"</bank_name>";                                           
	                     xml=xml+ "<branch_name>"+ rs2.getString("z_BRANCH_NAME") +"</branch_name>";*/
	                     count++;
	                  }
	                  
	                  if(count==0) {
	                      xml=xml+"<flag>NoData</flag>";
	                  }
	                  else{                
	                      xml=xml+"<flag>success</flag>";
	                  }              
	       }
	           catch(Exception e) 
	           {
	                  System.out.println("Exception in assigning..."+e);
	                  xml=xml+"<flag>failure</flag>";
	           }
	           xml = xml + "</response>";
	                    System.out.println(xml);
	                    out.println(xml);
	    }
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Connection con = null;
		PreparedStatement ppee4=null;
		ResultSet re4=null;
        Double four_bAmount=0.00,four_cAmount=0.00,excess_db=0.00,bank_credit=0.00,four_e4=0.00,d=0.00,five_a1=0.00,five_a1_ct_month=0.00;
	    BigDecimal ii  = null,i2=null,i3=null,bank_credit_bdecimal=null,i4_four=null,i2_five=null,i2_five_ct_month=null;
	    BigDecimal total_i=null;
	    String UnitName=null;
        String totalyear="",mode_id="";
          int count_test=0,exce_test=0,count_tesst=0,i4_excfour=0;
              int bank_id=0,branch_id=0,nw_fv=0,nw_fv_current=0;
             
              System.out.println("BRS download in xls format");
             
              Statement statement=null;
              CallableStatement cs=null;
              ResultSet rs2=null;
              int kk=0;
              String part1="";
		try {
			HttpSession session = request.getSession(false);
			if (session == null) {
				System.out.println(request.getContextPath() + "/index.jsp");
				response.sendRedirect(request.getContextPath() + "/index.jsp");
				return;
			}
			System.out.println(session);

		} catch (Exception e) {
			System.out.println("Redirect Error :" + e);
		}
		try {
			ResourceBundle rs1 = ResourceBundle
					.getBundle("Servlets.Security.servlets.Config");
			String ConnectionString = "";

			String strDriver = rs1.getString("Config.DATA_BASE_DRIVER");
			String strdsn = rs1.getString("Config.DSN");
			String strhostname = rs1.getString("Config.HOST_NAME");
			String strportno = rs1.getString("Config.PORT_NUMBER");
			String strsid = rs1.getString("Config.SID");
			String strdbusername = rs1.getString("Config.USER_NAME");
			String strdbpassword = rs1.getString("Config.PASSWORD");
		    ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection

			Class.forName(strDriver.trim());
			con = DriverManager.getConnection(ConnectionString, strdbusername
					.trim(), strdbpassword.trim());
		} catch (Exception e) {
			System.out.println("Exception in opening connection :" + e);
			// sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

		}
		
		HttpSession session=request.getSession(false);
        try
        {
            
            if(session==null)
            {
                System.out.println(request.getContextPath()+"/index.jsp");
                response.sendRedirect(request.getContextPath()+"/index.jsp");
                return;
            }
            System.out.println(session);
                
        }catch(Exception e)
        {
        System.out.println("Redirect Error :"+e);
        } 
		 String update_user=(String)session.getAttribute("UserId");
         long l=System.currentTimeMillis();
         Timestamp ts=new Timestamp(l);	
         String ss = "";
		
		
		int cboAcc_UnitCode = Integer.parseInt(request
				.getParameter("cmbAcc_UnitCode"));
		int cboOffice_code = Integer.parseInt(request
				.getParameter("cmbOffice_code"));
		int cboCashBook_Year = Integer.parseInt(request
				.getParameter("txtCB_Year"));
		int cboCashBook_Month = Integer.parseInt(request
				.getParameter("txtCB_Month"));
		
		/*String  last_date_one =request
				.getParameter("cmbBankAccNo");
	    System.out.println("last_date_one::"+last_date_one);
	    String[] splto=last_date_one.split("/");
	    
	    String part1 = splto[1]; 
	    System.out.println(part1);
		long cmbBankAccNo =Long.parseLong(part1);
		System.out.println("hhhh");
		 System.out.println("hhhh"+cmbBankAccNo);*/
	    
	    
	    
	   
		String cmd=request.getParameter("command");

		String month = null;
		if(cboCashBook_Month == 1){
			month = "January";
		}else if(cboCashBook_Month == 2){
			month = "Febrary";
		}else if(cboCashBook_Month == 3){
			month = "March";
		}else if(cboCashBook_Month == 4){
			month = "April";
		}else if(cboCashBook_Month == 5){
			month = "May";
		}else if(cboCashBook_Month == 6){
			month = "June";
		}else if(cboCashBook_Month == 7){
			month = "July";
		}else if(cboCashBook_Month == 8){
			month = "August";
		}else if(cboCashBook_Month == 9){
			month = "September";
		}else if(cboCashBook_Month == 10){
			month = "October";
		}else if(cboCashBook_Month == 11){
			month = "November";
		}else if(cboCashBook_Month == 12){
			month = "December";
		}
		File reportFile=null;
		int report = 0;
		Map map = null;
		
		
		
		
		try {
			System.out.println("calling servlet...");
                        if(cmd.equalsIgnoreCase("printFunc"))
                        {
                        	String  last_date_one =request
                    				.getParameter("cmbBankAccNo");
                    	    System.out.println("last_date_one::"+last_date_one);
                    	    String[] splto=last_date_one.split("/");
                    	    
                    	     part1 = splto[1]; 
                    	    System.out.println(part1);
                    		long cmbBankAccNo =Long.parseLong(part1);
                    		System.out.println("hhhh");
                    		 System.out.println("hhhh"+cmbBankAccNo);
                            reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/BRS/jaspers/ClearanceReport.jasper"));
                        }
                        
                       /* else  if(type_one.equalsIgnoreCase("onlyNT"))
                        {
                        	reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/BRS/jaspers/Breakup_Report_for_Part1_onlyNT.jasper"));
                        }
		    else  if(type_one.equalsIgnoreCase("onlyT"))
		    {
		    	reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/BRS/jaspers/Breakup_Report_for_Part1_onlyT.jasper"));
		    }*/
			if (!reportFile.exists())
				throw new JRRuntimeException(
						"File J not found. The report design must be compiled first.");

			JasperReport jasperReport = (JasperReport) JRLoader
					.loadObject(reportFile.getPath());
			
			map = new HashMap();

			map.put("UnitId", cboAcc_UnitCode);
			map.put("OfficeId", cboOffice_code);
			map.put("cbyear", cboCashBook_Year);
			map.put("cbmonth", cboCashBook_Month);
			map.put("accNo", part1);
			map.put("month", month);
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					jasperReport, map, con);
			System.out.println("upto");
			String rtype = "PDF";// request.getParameter("cmbReportType");
			System.out.println(rtype);
			if (rtype.equalsIgnoreCase("HTML")) {
				response.setContentType("text/html");
				response.setHeader("Content-Disposition",
						"attachment;filename=\"Receipt.html\"");
				PrintWriter out = response.getWriter();
				JRHtmlExporter exporter = new JRHtmlExporter();
				// File f=new
				// File(getServletContext().getRealPath("/WEB-INF/Report/"));
				// exporter.setParameter(JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR,true);
				// exporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR_NAME,getServletContext().getRealPath("/WEB-INF/Report/"));
				// exporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR,f);
				exporter
						.setParameter(
								JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,
								false);
				exporter.setParameter(JRExporterParameter.JASPER_PRINT,
						jasperPrint);
				exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
				exporter.exportReport();
				out.flush();
				out.close();
			} else if (rtype.equalsIgnoreCase("PDF")) {
				System.out.println(rtype);
				byte buf[] = JasperExportManager.exportReportToPdf(jasperPrint);
				System.out.println("Length  " + buf.length);
				response.setContentType("application/pdf");
				response.setContentLength(buf.length);
				// response.setHeader("content-disposition",
				// "inline;filename=OpenActionItems.pdf");
				// response.setContentType("application/force-download");

				response.setHeader("Content-Disposition",
						"attachment;filename=\"Receipt.pdf\"");
				OutputStream out = response.getOutputStream();
				out.write(buf, 0, buf.length);
				out.close();
			} else if (rtype.equalsIgnoreCase("EXCEL")) {

				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Disposition",
						"attachment;filename=\"Receipt.xls\"");
				JRXlsExporter exporterXLS = new JRXlsExporter();
				exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT,
						jasperPrint);

				ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
				exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,
						xlsReport);
				exporterXLS.setParameter(
						JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
						Boolean.FALSE);
				exporterXLS.setParameter(
						JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE,
						Boolean.TRUE);
				exporterXLS.setParameter(
						JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,
						Boolean.FALSE);
				exporterXLS
						.setParameter(
								JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
								Boolean.TRUE);
				exporterXLS.exportReport();
				byte[] bytes;
				bytes = xlsReport.toByteArray();
				ServletOutputStream ouputStream = response.getOutputStream();
				ouputStream.write(bytes, 0, bytes.length);
				ouputStream.flush();
				ouputStream.close();

			} else if (rtype.equalsIgnoreCase("TXT")) {

				response.setContentType("text/plain");
				response.setHeader("Content-Disposition",
						"attachment;filename=\"Receipt.txt\"");

				JRTextExporter exporter = new JRTextExporter();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT,
						jasperPrint);
				ByteArrayOutputStream txtReport = new ByteArrayOutputStream();
				exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
						txtReport);
				exporter.setParameter(JRTextExporterParameter.CHARACTER_WIDTH,
						new Integer(200));
				exporter.setParameter(JRTextExporterParameter.CHARACTER_HEIGHT,
						new Integer(50));
				exporter.exportReport();

				byte[] bytes;
				bytes = txtReport.toByteArray();
				ServletOutputStream ouputStream = response.getOutputStream();
				ouputStream.write(bytes, 0, bytes.length);
				ouputStream.flush();
				ouputStream.close();

			}
		} catch (Exception ex) {
			String connectMsg = "Could not create the report "
					+ ex.getMessage() + " uu " + ex.getLocalizedMessage();
			System.out.println(connectMsg);
		}
		
 if(cmd.equalsIgnoreCase("printFunc1")){
	 try{
		 
		 String  last_date =request
					.getParameter("cmbBankAccNo");
			System.out.println("last_date::"+last_date);
			String[] splto1=last_date.split("/");

				    
				    String part2 = splto1[1]; 
				    System.out.println(part2);
					long cmbBankAccNo1 =Long.parseLong(part2);
					System.out.println("hhhh");
					 System.out.println("hhhh"+cmbBankAccNo1);
					 
			response.setContentType("application/vnd.ms-excel");
           response.setHeader ("Content-Disposition", "attachment;filename=\"Download BRS Data.xls\"");
		//String filename="c:/hello.xls" ;
		HSSFWorkbook hwb=new HSSFWorkbook();
		HSSFSheet sheet =  hwb.createSheet("new sheet");

		HSSFRow rowhead=   sheet.createRow((short)0);
		
		rowhead.createCell((short) 0).setCellValue("UNIT_ID");
		rowhead.createCell((short) 1).setCellValue("OFFICE_ID");
		rowhead.createCell((short) 2).setCellValue("CASHBOOKYEAR");
		rowhead.createCell((short) 3).setCellValue("CASHBOOKMONTH");
		rowhead.createCell((short) 4).setCellValue("ENTRY_DATE");
		rowhead.createCell((short) 5).setCellValue("SL_NO");		
		rowhead.createCell((short) 6).setCellValue("TWAD_OR_NON_TWAD");
		rowhead.createCell((short) 7).setCellValue("REMITTANCE_DATE");
		rowhead.createCell((short) 8).setCellValue("WITHDRAWAL_DATE");		
		rowhead.createCell((short) 9).setCellValue("VOUCHER_OR_CHALLAN_NO");		
		rowhead.createCell((short) 10).setCellValue("PARTICULARS");
		rowhead.createCell((short) 11).setCellValue("CR_AMOUNT");
		rowhead.createCell((short) 12).setCellValue("DR_AMOUNT");
		rowhead.createCell((short) 13).setCellValue("ENTRY_FOUND_IN_PASSBOOK");
		rowhead.createCell((short) 14).setCellValue("PASSBOOK_DATE");
		rowhead.createCell((short) 15).setCellValue("AMOUNT_IN_PASSBOOK");
		rowhead.createCell((short) 16).setCellValue("DIFFERENCE");
		rowhead.createCell((short) 17).setCellValue("REASON_FOR_DIFFERENCE");
		rowhead.createCell((short) 18).setCellValue("FOLLOW_UP_ACTION_REQUIRED");
		rowhead.createCell((short) 19).setCellValue("CLEARED_BASED_ON_FOLLOWUP");
		rowhead.createCell((short) 20).setCellValue("TRANSACTION_TYPE");
		rowhead.createCell((short) 21).setCellValue("UPDATED_BY_USER_ID");
		rowhead.createCell((short) 22).setCellValue("UPDATED_DATE");
		rowhead.createCell((short) 23).setCellValue("ACCOUNT_NO");
		rowhead.createCell((short) 24).setCellValue("DOC_DATE");
		rowhead.createCell((short) 25).setCellValue("DOC_NO");
		rowhead.createCell((short) 26).setCellValue("DOC_TYPE");
		rowhead.createCell((short) 27).setCellValue("DETAILS");
		rowhead.createCell((short) 28).setCellValue("JOURNALIZED");
		rowhead.createCell((short) 29).setCellValue("ACTION_TAKEN");
		rowhead.createCell((short) 30).setCellValue("CLEARENCE_REF_TYPE");
		rowhead.createCell((short) 31).setCellValue("CLEARENCE_DATE");
		rowhead.createCell((short) 32).setCellValue("AMOUNT_IN_PASSBOOK");		
		rowhead.createCell((short) 33).setCellValue("IS_IT_CLEARING_ENTRY");
		rowhead.createCell((short) 34).setCellValue("PASS_BOOK_MONTH");
		rowhead.createCell((short) 35).setCellValue("PASS_BOOK_YEAR");
		
	
		
		ServletOutputStream fileOut=null;

	   ss = "select t.* "
	   		+ " from fas_brs_master m, fas_brs_transaction  t "
	   		+ "  Where M.Accounting_For_Office_Id =T.Accounting_For_Office_Id "
	   		+ " and M.ACCOUNTING_UNIT_ID =T.ACCOUNTING_UNIT_ID "
	   		+ " And M.Cashbook_Year              = T.Cashbook_Year "
	   		+ " And M.Cashbook_Month             = T.Cashbook_Month "
	   		+ " And M.Cashbook_Year              = " + cboCashBook_Year 
	   		+ " And M.Cashbook_Month             =  " + cboCashBook_Month 
	   		+ " and M.ACCOUNTING_UNIT_ID="+ cboAcc_UnitCode 
	   		+ " and M.Accounting_For_Office_Id ="+cboOffice_code 
	   		+ " and m.account_no="+cmbBankAccNo1;

    System.out.println("ss::::"+ss);
   PreparedStatement ps2=con.prepareStatement(ss);
  rs2=ps2.executeQuery();
  int j=1;
   while(rs2.next())
   {
	   //System.out.println("value of rows :::"+j);
		//HSSFRow row=   sheet.createRow((int)j);
		
		HSSFRow row=   sheet.createRow((int)j);
		
		/* System.out.println("value of rows :::"+rs2.getInt("ACCOUNTING_UNIT_ID")+"1"
		+rs2.getInt("ACCOUNTING_FOR_OFFICE_ID")+"2"+rs2.getInt("cashbook_year")
		+"3"+rs2.getInt("Cashbook_Month")+"4"+rs2.getDate("ENTRY_DATE")+"5"+rs2.getInt("SL_NO")+"6"
		+rs2.getString("PARTICULARS")+"7"+rs2.getString("TWAD_OR_NON_TWAD")
		+"8"+rs2.getDate("REMITTANCE_DATE")
		+"9"
		+rs2.getDate("WITHDRAWAL_DATE")
		+rs2.getInt("VOUCHER_OR_CHALLAN_NO")
		+rs2.getFloat("CR_AMOUNT")
		+rs2.getFloat("DR_AMOUNT")
		+rs2.getString("ENTRY_FOUND_IN_PASSBOOK")
		+rs2.getDate("PASSBOOK_DATE")
		+rs2.getDouble("AMOUNT_IN_PASSBOOK")
		+rs2.getFloat("DIFFERENCE")
		+rs2.getString("REASON_FOR_DIFFERENCE")
		+rs2.getString("FOLLOW_UP_ACTION_REQUIRED")
		+rs2.getString("CLEARED_BASED_ON_FOLLOWUP")
		+rs2.getString("TRANSACTION_TYPE")
		+rs2.getString("UPDATED_BY_USER_ID")+
		rs2.getDate("UPDATED_DATE")+rs2.getDouble("ACCOUNT_NO")+rs2.getDate("DOC_DATE")+rs2.getInt("DOC_NO")
		+rs2.getString("DOC_TYPE")+
		rs2.getString("DETAILS")
		+rs2.getString("JOURNALIZED")+
		rs2.getString("ACTION_TAKEN")+
		rs2.getString("CLEARENCE_REF_TYPE")+
		rs2.getDate("CLEARENCE_DATE")+
		rs2.getDouble("AMOUNT_IN_PASSBOOK")+
		rs2.getString("IS_IT_CLEARING_ENTRY")+
		rs2.getInt("PASS_BOOK_MONTH")+
		rs2.getDouble("PASS_BOOK_YEAR"));*/
	/*
		row.createCell((short) 0).setCellValue(rs2.getInt("Unit_Id"));
		row.createCell((short) 1).setCellValue(rs2.getInt("cashbook_year"));
		row.createCell((short) 2).setCellValue(rs2.getInt("Cashbook_Month"));
		row.createCell((short) 3).setCellValue(rs2.getString("pass_book_balance_type"));
		row.createCell((short) 4).setCellValue(rs2.getDouble("Account_No"));
		row.createCell((short) 5).setCellValue(rs2.getInt("passbook_balance"));
		row.createCell((short) 6).setCellValue(rs2.getString("Sl_No"));
		
		row.createCell((short) 7).setCellValue(rs2.getDouble("AMOUNT_IN_PASSBOOK"));*/
		
		row.createCell((short) 0).setCellValue(rs2.getInt("ACCOUNTING_UNIT_ID"));
		row.createCell((short) 1).setCellValue(rs2.getInt("ACCOUNTING_FOR_OFFICE_ID"));
		row.createCell((short) 2).setCellValue(rs2.getInt("cashbook_year"));
		row.createCell((short) 3).setCellValue(rs2.getInt("Cashbook_Month"));
		row.createCell((short) 4).setCellValue(rs2.getString("ENTRY_DATE"));
		row.createCell((short) 5).setCellValue(rs2.getInt("SL_NO"));		
		row.createCell((short) 6).setCellValue(rs2.getString("TWAD_OR_NON_TWAD"));
		if (rs2.getDate("REMITTANCE_DATE")==null) {
			row.createCell((short) 7).setCellValue("");
		} else {
			row.createCell((short) 7).setCellValue(rs2.getString("REMITTANCE_DATE"));
		}
		
		if (rs2.getDate("WITHDRAWAL_DATE")==null) {
			row.createCell((short) 8).setCellValue("");
		} else {
			row.createCell((short) 8).setCellValue(rs2.getString("WITHDRAWAL_DATE"));
		}
				
		row.createCell((short) 9).setCellValue(rs2.getInt("VOUCHER_OR_CHALLAN_NO"));		
		
		
		if (rs2.getString("PARTICULARS")==null) {
			row.createCell((short) 10).setCellValue("");
		} else {
			row.createCell((short) 10).setCellValue(rs2.getString("PARTICULARS"));
		}
		row.createCell((short) 11).setCellValue(rs2.getFloat("CR_AMOUNT"));
		row.createCell((short) 12).setCellValue(rs2.getFloat("DR_AMOUNT"));
		row.createCell((short) 13).setCellValue(rs2.getString("ENTRY_FOUND_IN_PASSBOOK"));
		row.createCell((short) 14).setCellValue(rs2.getString("PASSBOOK_DATE"));
		row.createCell((short) 15).setCellValue(rs2.getDouble("AMOUNT_IN_PASSBOOK"));
		row.createCell((short) 16).setCellValue(rs2.getFloat("DIFFERENCE"));
		
		if (rs2.getString("REASON_FOR_DIFFERENCE")==null) {
			//System.out.println("Entered");
			row.createCell((short) 17).setCellValue("");
		} else {
			row.createCell((short) 17).setCellValue(rs2.getString("REASON_FOR_DIFFERENCE"));
		}
		
		
		row.createCell((short) 18).setCellValue(rs2.getString("FOLLOW_UP_ACTION_REQUIRED"));
		
		if (rs2.getString("CLEARED_BASED_ON_FOLLOWUP")==null) {
			row.createCell((short) 19).setCellValue(" ");
		} else {
			row.createCell((short) 19).setCellValue(rs2.getString("CLEARED_BASED_ON_FOLLOWUP"));
		}
		
		if (rs2.getString("TRANSACTION_TYPE")==null) {
			row.createCell((short) 20).setCellValue("");
		} else {
			row.createCell((short) 20).setCellValue(rs2.getString("TRANSACTION_TYPE"));
		}
		
		
		
		row.createCell((short) 21).setCellValue(rs2.getString("UPDATED_BY_USER_ID"));
		
		row.createCell((short) 22).setCellValue(rs2.getString("UPDATED_DATE"));
		row.createCell((short) 23).setCellValue(rs2.getDouble("ACCOUNT_NO"));
		row.createCell((short) 24).setCellValue(rs2.getString("DOC_DATE"));
		row.createCell((short) 25).setCellValue(rs2.getInt("DOC_NO"));
		row.createCell((short) 26).setCellValue(rs2.getString("DOC_TYPE"));
		
		if (rs2.getString("DETAILS")==null) {
			row.createCell((short) 27).setCellValue("");
		} else {
			row.createCell((short) 27).setCellValue(rs2.getString("DETAILS"));
		}
		
		if (rs2.getString("JOURNALIZED")==null) {
			row.createCell((short) 28).setCellValue("");
		} else {
			row.createCell((short) 28).setCellValue(rs2.getString("JOURNALIZED"));
		}
		
		
		if (rs2.getString("ACTION_TAKEN")==null) {
			row.createCell((short) 29).setCellValue("");
		} else {
			row.createCell((short) 29).setCellValue(rs2.getString("ACTION_TAKEN"));
		}
		
		
		if (rs2.getString("CLEARENCE_REF_TYPE")==null) {
			row.createCell((short) 30).setCellValue("");
		} else {
			row.createCell((short) 30).setCellValue(rs2.getString("CLEARENCE_REF_TYPE"));
		}
		
		
		if (rs2.getDate("CLEARENCE_DATE")==null) {
			row.createCell((short) 32).setCellValue("");
		} else {
			row.createCell((short) 32).setCellValue(rs2.getString("CLEARENCE_DATE"));
		}
		
		
		if (rs2.getString("IS_IT_CLEARING_ENTRY")==null) {
			row.createCell((short) 33).setCellValue("");
		} else {
			row.createCell((short) 33).setCellValue(rs2.getString("IS_IT_CLEARING_ENTRY"));
		}
		
		
		
		
		
			
		row.createCell((short) 33).setCellValue(rs2.getString("IS_IT_CLEARING_ENTRY"));
		row.createCell((short) 34).setCellValue(rs2.getInt("PASS_BOOK_MONTH"));
		row.createCell((short) 35).setCellValue(rs2.getDouble("PASS_BOOK_YEAR"));
		
				
		j++;
	 }
   fileOut = response.getOutputStream();
   hwb.write(fileOut);
		fileOut.close();
		} catch ( Exception ex ) {
		    System.out.println("Exeception in BRS    "+ex);

		}
 }
 if(cmd.equalsIgnoreCase("printFunc2")){
	 try{
		 
		 String  last_date =request
					.getParameter("cmbBankAccNo");
			System.out.println("last_date2::"+last_date);
			String[] splto1=last_date.split("/");

				    
				    String part2 = splto1[1]; 
				    System.out.println(part2);
					long cmbBankAccNo1 =Long.parseLong(part2);
					System.out.println("hhhh");
					 System.out.println("hhhh"+cmbBankAccNo1);
			 System.out.println("SQL2");		 
			
			response.setContentType("application/vnd.ms-excel");
           response.setHeader ("Content-Disposition", "attachment;filename=\"Download BRS Data.xls\"");
		//String filename="c:/hello.xls" ;
		HSSFWorkbook hwb=new HSSFWorkbook();
		HSSFSheet sheet =  hwb.createSheet("new sheet");

		HSSFRow rowhead=   sheet.createRow((short)0);
		 System.out.println("SQL1");
		 
		rowhead.createCell((short) 0).setCellValue("UNIT_ID");
		rowhead.createCell((short) 1).setCellValue("OFFICE_ID");
		rowhead.createCell((short) 2).setCellValue("CASHBOOKYEAR");
		rowhead.createCell((short) 3).setCellValue("CASHBOOKMONTH");
		rowhead.createCell((short) 4).setCellValue("ENTRY_DATE");
		rowhead.createCell((short) 5).setCellValue("SL_NO");		
		rowhead.createCell((short) 6).setCellValue("TWAD_OR_NON_TWAD");
		rowhead.createCell((short) 7).setCellValue("REMITTANCE_DATE");
		rowhead.createCell((short) 8).setCellValue("WITHDRAWAL_DATE");		
		rowhead.createCell((short) 9).setCellValue("VOUCHER_OR_CHALLAN_NO");	
		rowhead.createCell((short) 10).setCellValue("CHEQUE_DD_NO");
		rowhead.createCell((short) 11).setCellValue("PARTICULARS");
		rowhead.createCell((short) 12).setCellValue("CR_AMOUNT");
		rowhead.createCell((short) 13).setCellValue("DR_AMOUNT");
		rowhead.createCell((short) 14).setCellValue("ENTRY_FOUND_IN_PASSBOOK");
		rowhead.createCell((short) 15).setCellValue("PASSBOOK_DATE");
		rowhead.createCell((short) 16).setCellValue("AMOUNT_IN_PASSBOOK");
		rowhead.createCell((short) 17).setCellValue("DIFFERENCE");
		rowhead.createCell((short) 18).setCellValue("REASON_FOR_DIFFERENCE");
		rowhead.createCell((short) 19).setCellValue("FOLLOW_UP_ACTION_REQUIRED");
		rowhead.createCell((short) 20).setCellValue("CLEARED_BASED_ON_FOLLOWUP");
		rowhead.createCell((short) 21).setCellValue("TRANSACTION_TYPE");
		rowhead.createCell((short) 22).setCellValue("UPDATED_BY_USER_ID");
		rowhead.createCell((short) 23).setCellValue("UPDATED_DATE");
		rowhead.createCell((short) 24).setCellValue("ACCOUNT_NO");
		rowhead.createCell((short) 25).setCellValue("DOC_DATE");
		rowhead.createCell((short) 26).setCellValue("DOC_NO");
		rowhead.createCell((short) 27).setCellValue("DOC_TYPE");
		rowhead.createCell((short) 28).setCellValue("DETAILS");
		rowhead.createCell((short) 29).setCellValue("ACTION_TAKEN");
		rowhead.createCell((short) 30).setCellValue("JOURNALIZED");
		
		
		ServletOutputStream fileOut=null;

	  
	   System.out.println("SQL");
	   ss="SELECT * "
	   		+ " FROM Fas_Brs_Transaction_Noentry "
	   		+ " WHERE Cashbook_Month        ="+cboCashBook_Month
	   		+ " AND Cashbook_Year           ="+cboCashBook_Year
	   		+ " AND Accounting_Unit_Id      ="+cboAcc_UnitCode  
	   		+ " AND Accounting_For_Office_Id="+cboOffice_code 
	   		+ " AND Account_No              ="+cmbBankAccNo1;

    System.out.println("ss::::"+ss);
   PreparedStatement ps2=con.prepareStatement(ss);
  rs2=ps2.executeQuery();
  int j=1;
   while(rs2.next())
   {
	  // System.out.println("value of rows :::"+j);
		//HSSFRow row=   sheet.createRow((int)j);
		
		HSSFRow row=   sheet.createRow((int)j);
		
		/* System.out.println("value of rows :::"+rs2.getInt("ACCOUNTING_UNIT_ID")+"1"
		+rs2.getInt("ACCOUNTING_FOR_OFFICE_ID")+"2"+rs2.getInt("cashbook_year")
		+"3"+rs2.getInt("Cashbook_Month")+"4"+rs2.getDate("ENTRY_DATE")+"5"+rs2.getInt("SL_NO")+"6"
		+rs2.getString("PARTICULARS")+"7"+rs2.getString("TWAD_OR_NON_TWAD")
		+"8"+rs2.getDate("REMITTANCE_DATE")
		+"9"
		+rs2.getDate("WITHDRAWAL_DATE")
		+rs2.getInt("VOUCHER_OR_CHALLAN_NO")
		+rs2.getFloat("CR_AMOUNT")
		+rs2.getFloat("DR_AMOUNT")
		+rs2.getString("ENTRY_FOUND_IN_PASSBOOK")
		+rs2.getDate("PASSBOOK_DATE")
		+rs2.getDouble("AMOUNT_IN_PASSBOOK")
		+rs2.getFloat("DIFFERENCE")
		+rs2.getString("REASON_FOR_DIFFERENCE")
		+rs2.getString("FOLLOW_UP_ACTION_REQUIRED")
		+rs2.getString("CLEARED_BASED_ON_FOLLOWUP")
		+rs2.getString("TRANSACTION_TYPE")
		+rs2.getString("UPDATED_BY_USER_ID")+
		rs2.getDate("UPDATED_DATE")+rs2.getDouble("ACCOUNT_NO")+rs2.getDate("DOC_DATE")+rs2.getInt("DOC_NO")
		+rs2.getString("DOC_TYPE")+
		rs2.getString("DETAILS")
		+rs2.getString("JOURNALIZED")+
		rs2.getString("ACTION_TAKEN"));*/
		
	/*
		row.createCell((short) 0).setCellValue(rs2.getInt("Unit_Id"));
		row.createCell((short) 1).setCellValue(rs2.getInt("cashbook_year"));
		row.createCell((short) 2).setCellValue(rs2.getInt("Cashbook_Month"));
		row.createCell((short) 3).setCellValue(rs2.getString("pass_book_balance_type"));
		row.createCell((short) 4).setCellValue(rs2.getDouble("Account_No"));
		row.createCell((short) 5).setCellValue(rs2.getInt("passbook_balance"));
		row.createCell((short) 6).setCellValue(rs2.getString("Sl_No"));
		
		row.createCell((short) 7).setCellValue(rs2.getDouble("AMOUNT_IN_PASSBOOK"));*/
		
		row.createCell((short) 0).setCellValue(rs2.getInt("ACCOUNTING_UNIT_ID"));
		row.createCell((short) 1).setCellValue(rs2.getInt("ACCOUNTING_FOR_OFFICE_ID"));
		row.createCell((short) 2).setCellValue(rs2.getInt("cashbook_year"));
		row.createCell((short) 3).setCellValue(rs2.getInt("Cashbook_Month"));
		row.createCell((short) 4).setCellValue(rs2.getString("ENTRY_DATE"));
		row.createCell((short) 5).setCellValue(rs2.getInt("SL_NO"));		
		row.createCell((short) 6).setCellValue(rs2.getString("TWAD_OR_NON_TWAD"));
		if (rs2.getDate("REMITTANCE_DATE")==null) {
			row.createCell((short) 7).setCellValue("");
		} else {
			row.createCell((short) 7).setCellValue(rs2.getString("REMITTANCE_DATE"));
		}
		
		if (rs2.getDate("WITHDRAWAL_DATE")==null) {
			row.createCell((short) 8).setCellValue("");
		} else {
			row.createCell((short) 8).setCellValue(rs2.getString("WITHDRAWAL_DATE"));
		}
				
		row.createCell((short) 9).setCellValue(rs2.getInt("VOUCHER_OR_CHALLAN_NO"));		
		
		row.createCell((short) 10).setCellValue(rs2.getString("CHEQUE_DD_NO"));
		
		if (rs2.getString("PARTICULARS")==null) {
			row.createCell((short) 11).setCellValue("");
		} else {
			row.createCell((short) 11).setCellValue(rs2.getString("PARTICULARS"));
		}
		row.createCell((short) 12).setCellValue(rs2.getFloat("CR_AMOUNT"));
		row.createCell((short) 13).setCellValue(rs2.getFloat("DR_AMOUNT"));
		row.createCell((short) 14).setCellValue(rs2.getString("ENTRY_FOUND_IN_PASSBOOK"));
		
		
		if (rs2.getDate("PASSBOOK_DATE")==null) {
			row.createCell((short) 15).setCellValue("");
		} else {
			row.createCell((short) 15).setCellValue(rs2.getString("PASSBOOK_DATE"));
		}
		
		
		row.createCell((short) 16).setCellValue(rs2.getDouble("AMOUNT_IN_PASSBOOK"));
		row.createCell((short) 17).setCellValue(rs2.getFloat("DIFFERENCE"));
		
		if (rs2.getString("REASON_FOR_DIFFERENCE")==null) {
			//System.out.println("Entered");
			row.createCell((short) 18).setCellValue("");
		} else {
			row.createCell((short) 18).setCellValue(rs2.getString("REASON_FOR_DIFFERENCE"));
		}
		
		
		row.createCell((short) 19).setCellValue(rs2.getString("FOLLOW_UP_ACTION_REQUIRED"));
		
		if (rs2.getString("CLEARED_BASED_ON_FOLLOWUP")==null) {
			row.createCell((short) 20).setCellValue(" ");
		} else {
			row.createCell((short) 20).setCellValue(rs2.getString("CLEARED_BASED_ON_FOLLOWUP"));
		}
		
		if (rs2.getString("TRANSACTION_TYPE")==null) {
			row.createCell((short) 21).setCellValue("");
		} else {
			row.createCell((short) 21).setCellValue(rs2.getString("TRANSACTION_TYPE"));
		}
		
		
		
		row.createCell((short) 22).setCellValue(rs2.getString("UPDATED_BY_USER_ID"));
		
		row.createCell((short) 23).setCellValue(rs2.getString("UPDATED_DATE"));
		row.createCell((short) 24).setCellValue(rs2.getDouble("ACCOUNT_NO"));
		row.createCell((short) 25).setCellValue(rs2.getString("DOC_DATE"));
		row.createCell((short) 26).setCellValue(rs2.getInt("DOC_NO"));
		row.createCell((short) 27).setCellValue(rs2.getString("DOC_TYPE"));
		
		if (rs2.getString("DETAILS")==null) {
			row.createCell((short) 28).setCellValue("");
		} else {
			row.createCell((short) 28).setCellValue(rs2.getString("DETAILS"));
		}
		
		
		if (rs2.getString("ACTION_TAKEN")==null) {
			row.createCell((short) 29).setCellValue("");
		} else {
			row.createCell((short) 29).setCellValue(rs2.getString("ACTION_TAKEN"));
		}
		
		if (rs2.getString("JOURNALIZED")==null) {
			row.createCell((short) 30).setCellValue("");
		} else {
			row.createCell((short) 30).setCellValue(rs2.getString("JOURNALIZED"));
		}
				
		j++;
	 }
   fileOut = response.getOutputStream();
   hwb.write(fileOut);
		fileOut.close();
		} catch ( Exception ex ) {
		    System.out.println("Exeception in BRS    "+ex);

		}
 }

	}

}
		/*if(cmd.equalsIgnoreCase("printFunc")){
			
			try{
			reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/BRS/jaspers/ClearanceReport.jasper"));
			System.out.println(reportFile);
			
			
			if (!reportFile.exists())
				throw new JRRuntimeException(
						"File J not found. The report design must be compiled first.");

			JasperReport jasperReport = (JasperReport) JRLoader
					.loadObject(reportFile.getPath());
			
			System.out.println(jasperReport);
			
			map.put("UnitId", cboAcc_UnitCode);
			map.put("OfficeId", cboOffice_code);
			map.put("cbyear", cboCashBook_Year);
			map.put("cbmonth", cboCashBook_Month);
			//map.put("accNo", cmbBankAccNo);
			map.put("month", month);
            
            
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					jasperReport, map, con);
			System.out.println("upto");
			String rtype = "PDF";// request.getParameter("cmbReportType");
			System.out.println(rtype);
			if (rtype.equalsIgnoreCase("PDF")) {
				System.out.println(rtype);
				byte buf[] = JasperExportManager.exportReportToPdf(jasperPrint);
				System.out.println("Length  " + buf.length);
				response.setContentType("application/pdf");
				response.setContentLength(buf.length);
				

				response.setHeader("Content-Disposition",
						"attachment;filename=\"BRS_Part2A.pdf\"");
				OutputStream out = response.getOutputStream();
				out.write(buf, 0, buf.length);
				out.close();
			}
			}
			catch (Exception ex) {
				String connectMsg = "Could not create the report "+ ex.getMessage() + " uu " + ex.getLocalizedMessage();
				System.out.println(connectMsg);
			}
			}
	}
}   

*/