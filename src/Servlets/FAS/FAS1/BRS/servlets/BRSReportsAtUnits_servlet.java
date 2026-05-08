package Servlets.FAS.FAS1.BRS.servlets;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
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
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * Servlet implementation class BRSReport_Part2B
 */
public class BRSReportsAtUnits_servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BRSReportsAtUnits_servlet() {
        super();
       
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
System.out.println("welcome   JAva");
		/**
		 * Session Checking
		 */
String CONTENT_TYPE = "text/xml; charset=windows-1252";
		HttpSession session = request.getSession(false);
		try {

			if (session == null) {

				System.out.println(request.getContextPath() + "/index.jsp");
				response.sendRedirect(request.getContextPath() + "/index.jsp");
				return;
			}
			System.out.println(session);

		} catch (Exception e) {
			System.out.println("Redirect Error :" + e);
		}

		/**
		 * Variables Declaration
		 */

		Connection con = null;
		Connection con5 = null;
		Connection con6 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
	
		response.setContentType(CONTENT_TYPE);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();

		String strCommand = "",xml="";
		int cmbAcc_UnitCode =0,count=0;
		/**
		 * Database Connection
		 */

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

		}

		/**
		 * Get Command Parameter
		 */
		try {
			strCommand = request.getParameter("Command");
			 cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
			System.out.println("assign..here command..." + strCommand);
			System.out.println("assign..here cmbAcc_UnitCode..." + cmbAcc_UnitCode);
		} catch (Exception e) {
			System.out.println("Exception in assigning..." + e);
		}	
		

		
		String getbankAccNo="SELECT DISTINCT bankShow,BANK_AC_NO FROM (select b.BANK_AC_NO,  AC_OPERATIONAL_MODE_ID||'-'||c.BANK_SHORT_NAME || '-' || b.BANK_AC_TYPE_ID ||'-'|| b.BANK_AC_NO  as bankShow from FAS_MST_BANK_BALANCE b,FAS_MST_BANKS c "+
                //" where b.BANK_ID=c.BANK_ID and b.ACCOUNTING_UNIT_ID=? and b.STATUS='Y'";
                   " where b.BANK_ID=c.BANK_ID and b.ACCOUNTING_UNIT_ID=? and  b.STATUS='Y')";
																		     
		
   System.out.println("sql:"+getbankAccNo);
        try
        {
              ps2=con.prepareStatement(getbankAccNo);
              ps2.setInt(1,cmbAcc_UnitCode);
              rs2=ps2.executeQuery();
           
              
              xml="<response><command>LoadBankAccountNumber</command>";
              
              /** Count How many Records are available  */
              while (rs2.next()) 
              {
                 xml=xml+ "<acc_no>"+ rs2.getString("BANK_AC_NO") +"</acc_no>";	 
                 xml=xml+ "<acc_desc>"+ rs2.getString("bankShow")+"</acc_desc>";
                
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

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("servvvvvvvvvvvvvvvvv:::::");
                Connection con = null;
                String BANK_NAME=null,type_one=null;
                Double amount=0.00;
                String y_bank_name="",z_BRANCH_NAME="";
	    BigDecimal ii  = null;
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
			 try {
		int cboAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
		int cboOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
		int cboCashBook_Year = Integer.parseInt(request.getParameter("cboCashBook_Year"));
		int cboCashBook_Month = Integer.parseInt(request.getParameter("cboCashBook_Month"));
		long cmbBankAccNo =Long.parseLong(request.getParameter("txtBankAccountNo"));
                String trnType = request.getParameter("trnType");
                
		    String bkid[] = request.getParameter("bkid").split("-");
                System.out.println("bname::::"+bkid[0]);
		    String txtFrom_date = request.getParameter("txtFrom_date");
		    String txtTo_date = request.getParameter("txtTo_date");
		    try {
		    PreparedStatement ps=null;
		    ResultSet rs=null;
		    
		 //   ps=con.prepareStatement("select BANK_NAME from FAS_MST_BANKS where BANK_SHORT_NAME='"+bkid[0]+"'");
		    ps=con.prepareStatement("SELECT DISTINCT C.BANK_NAME " +
		    "FROM FAS_OFFICE_BANK_AC_CURRENT B, " +
		    "  FAS_MST_BANKS C " +
		    "WHERE b.BANK_ID         =c.BANK_ID " +
		    "AND b.ACCOUNTING_UNIT_ID=" +cboAcc_UnitCode+
		    "AND MODULE_ID          IN('MF004','MF005') " +
		    "AND b.STATUS            ='Y' " +
		    "AND b.BANK_AC_NO        ="+cmbBankAccNo);
  rs=ps.executeQuery();
		    if(rs.next())
		         BANK_NAME=rs.getString("BANK_NAME");
		    
		    
		    }
		    catch (SQLException e) {
		        System.out.println("SQL Exception -->"+e);
		    }
		    
		    try {
				 
			    PreparedStatement ps=null;
			    ResultSet rs=null;
			    
			    ps=con.prepareStatement("SELECT * "+
					"	FROM "+
			    		"	  (SELECT bank_id, "+
			    		"	BRANCH_ID, "+
			    		"	bank_ac_no, "+
			    		"	case when trim(AC_OPERATIONAL_MODE_ID) like 'COL' then 'Collection'"
			    		+ "  when  trim(AC_OPERATIONAL_MODE_ID) like 'OPR' then 'Operation' "
			    		+ " else AC_OPERATIONAL_MODE_ID end as AC_OPERATIONAL_MODE_ID1 , "+
			    		"	trim(AC_OPERATIONAL_MODE_ID) "+
			    		"	||'-' "+
			    		"	||trim(bank_ac_no) AS acc_no "+
			    		"	From Fas_mst_Bank_Balance "+
			    		"	WHERE accounting_unit_id  = ? "+
			    		"	AND AC_OPERATIONAL_MODE_ID='COL' "+
			    		"	)X "+
			    		"	LEFT OUTER JOIN "+
			    		"	(SELECT bank_id   AS y_bank_id ,trim(bank_name) AS y_bank_name FROM fas_bank_list "+
						"  )Y "+
			    		" ON X.bank_id = Y.y_bank_id "+
			    		" LEFT OUTER JOIN "+
			    		"  (SELECT BANK_ID     AS z_bank_id,BRANCH_ID         AS z_BRANCH_ID , "+
						"    trim(BRANCH_NAME) AS z_BRANCH_NAME FROM fas_mst_bank_branches "+
						"  )Z "+
						" ON X.bank_id    = Z.z_bank_id "+
						" AND X.BRANCH_ID = Z.z_branch_id");
			    ps.setInt(1,cboAcc_UnitCode);
			    rs=ps.executeQuery();
			    if(rs.next())
			    	y_bank_name="Bank Name :  "+rs.getString("y_bank_name");
			    if(rs.getString("z_BRANCH_NAME")==null)
			    {
			    	z_BRANCH_NAME="Branch Name :  -";
			    }
			    else{
			    z_BRANCH_NAME=rs.getString("z_BRANCH_NAME")+"-"+rs.getString("AC_OPERATIONAL_MODE_ID1");
			    }
			   
			    }
			    catch (SQLException e) {
			        System.out.println("SQL Exception -->"+e);
			    }
                    if(bkid[1].equals("SB")) {
                        type_one="Savings";
                    }
                    else {
                        type_one="Current";
                    }
                
                System.out.println("trnType:::"+trnType);
                System.out.println("cmbBankAccNo:::"+cmbBankAccNo);
	   
	   
            System.out.println("amount::::"+amount);
		String month = null;
		if(cboCashBook_Month == 1){
			month = "Jan";
		}else if(cboCashBook_Month == 2){
			month = "Feb";
		}else if(cboCashBook_Month == 3){
			month = "Mar";
		}else if(cboCashBook_Month == 4){
			month = "Apr";
		}else if(cboCashBook_Month == 5){
			month = "May";
		}else if(cboCashBook_Month == 6){
			month = "Jun";
		}else if(cboCashBook_Month == 7){
			month = "Jul";
		}else if(cboCashBook_Month == 8){
			month = "Aug";
		}else if(cboCashBook_Month == 9){
			month = "Sep";
		}else if(cboCashBook_Month == 10){
			month = "Oct";
		}else if(cboCashBook_Month == 11){
			month = "Nov";
		}else if(cboCashBook_Month == 12){
			month = "Dec";
		}
		    File reportFile = null;
		    String sortType=request.getParameter("sortType");
		    if(sortType.equalsIgnoreCase("Trn_Type")){
	    if(trnType.equals("s")) {
	        System.out.println("calling servlet...");
	        if (!txtFrom_date.equalsIgnoreCase("") && !txtTo_date.equalsIgnoreCase("")) 
                    {
                    System.out.println("dsateeeeeeeeeeee");
                        reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/BRS/jaspers/BRS_Reports_for_units_all_date.jasper"));
                    }
                    else{
                    	System.out.println("report called here:::");
	        reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/BRS/jaspers/BRS_Reports_for_units_all.jasper"));
                    }
	       
	    }
	    else {
	        if (!txtFrom_date.equalsIgnoreCase("") && !txtTo_date.equalsIgnoreCase("")) 
                    {
                    System.out.println("single date & any one trn type::::");
	                reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/BRS/jaspers/BRS_Reports_for_units_single_date.jasper"));
	            }
                    else{
	        System.out.println("calling servlet...");
	        reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/BRS/jaspers/BRS_Reports_for_units_single.jasper"));
                    }
	    }
		    }else if(sortType.equalsIgnoreCase("Pass_Date")){

			    if(trnType.equals("s")) {
			        System.out.println("calling servlet...");
			        if (!txtFrom_date.equalsIgnoreCase("") && !txtTo_date.equalsIgnoreCase("")) 
		                    {
		                    System.out.println("dsateeeeeeeeeeee");
		                        reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/BRS/jaspers/NewBrs_Report/BRS_Reports_for_units_all_date.jasper"));
		                    }
		                    else{
		                    	System.out.println("report called here:::");
			        reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/BRS/jaspers/NewBrs_Report/BRS_Reports_for_units_all.jasper"));
		                    }
			       
			    }
			    else {
			        if (!txtFrom_date.equalsIgnoreCase("") && !txtTo_date.equalsIgnoreCase("")) 
		                    {
		                    System.out.println("single date & any one trn type::::");
			                reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/BRS/jaspers/NewBrs_Report/BRS_Reports_for_units_single_date.jasper"));
			            }
		                    else{
			        System.out.println("calling servlet...");
			        reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/BRS/jaspers/NewBrs_Report/BRS_Reports_for_units_single.jasper"));
		                    }
			    }
				    
		    }
			if (!reportFile.exists())
				throw new JRRuntimeException(
						"File J not found. The report design must be compiled first.");

			JasperReport jasperReport = (JasperReport) JRLoader
					.loadObject(reportFile.getPath());
			Map map = null;
			map = new HashMap();
		    if (!txtFrom_date.equalsIgnoreCase("") && !txtTo_date.equalsIgnoreCase("")) 
		        {
                            map.put("UnitId", cboAcc_UnitCode);
                            map.put("fromDate", txtFrom_date);
                            map.put("toDate", txtTo_date);
                            map.put("trnType", trnType);
                            map.put("cmbBankAccNo", cmbBankAccNo);
                            map.put("BankName", BANK_NAME);
                            map.put("acType", type_one);
                            map.put("z_BRANCH_NAME", z_BRANCH_NAME);
                            map.put("monthword", month);
                            
                            System.out.println("fromDate"+txtFrom_date);
                            
                            System.out.println("toDate..."+txtTo_date);
                            
                        }
                        else{
			map.put("UnitId", cboAcc_UnitCode);
			map.put("cbyear", cboCashBook_Year);
			map.put("cbmonth", cboCashBook_Month);
			map.put("trnType", trnType);
			map.put("cmbBankAccNo", cmbBankAccNo);
            map.put("BankName", BANK_NAME);
	        map.put("acType", type_one);
	        map.put("z_BRANCH_NAME", z_BRANCH_NAME);
	        map.put("monthword", month);
                        }
                       
		    
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					jasperReport, map, con);
			System.out.println("upto map "+reportFile);
			System.out.println("upto map "+map);
			String rtype = "PDF";// request.getParameter("cmbReportType");
			System.out.println(rtype);
			if (rtype.equalsIgnoreCase("HTML")) {
				response.setContentType("text/html");
				response.setHeader("Content-Disposition",
						"attachment;filename=\"BRSReports.html\"");
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
						"attachment;filename=\"BRSReports.pdf\"");
				OutputStream out = response.getOutputStream();
				out.write(buf, 0, buf.length);
				out.close();
			} else if (rtype.equalsIgnoreCase("EXCEL")) {

				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Disposition",
						"attachment;filename=\"BRSReports.xls\"");
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
						"attachment;filename=\"BRS.txt\"");

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

	}

}
