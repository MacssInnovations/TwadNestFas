package Servlets.HR.HR1.EmployeeMaster.servlets;

//package Servlets.FAS.FAS1.BillRegister.servlets;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
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

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

import Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver;
import Servlets.HR.HR1.EmployeeMaster.Reports.EnglishNumberToWords;
//import Servlets.HR.HR1.EmployeeMaster.Reports.EnglishNumberToWords;
/**
 * Servlet implementation class Bills_Token_Register_with_SP
 */
public class Bills_Token_Register_with_SP extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Bills_Token_Register_with_SP() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		

		Connection connection = null;
		Statement statement = null;
		ResultSet results = null;
		ResultSet results2;
		ResultSet rs = null;
		ResultSet rs2 = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		int cashbookYear = 0;
		String cashbookMonth = null;
		int unitid = 0;
		String unitname = "";
		int accid = 0;
		
		String head_code="";
		int Sanction_Amt1=Integer.parseInt(request.getParameter("txtTotalSanctionedAmount"));
		Sanction_Amt1=Math.abs(Sanction_Amt1);
		System.out.println("Sanction_Amt1 == "+Sanction_Amt1);
		String Sanction_Amt="( "+EnglishNumberToWords.convert(Sanction_Amt1)+" Only)";
		int emp_id=Integer.parseInt(request.getParameter("txtPayeeCode"));
		head_code=request.getParameter("acc_code");
		System.out.println("HEAD CODE == "+head_code);
		int Vocucher_No=Integer.parseInt(request.getParameter("txtBillNo"));
		System.out.println("Vocucher_No == "+Vocucher_No);
		String bill_date=request.getParameter("txtBillDate");
		System.out.println("bill_date == "+bill_date);
		int cash_year=Integer.parseInt(request.getParameter("cash_year"));
		System.out.println("cash_year == "+cash_year);
		String cash_month1=request.getParameter("cash_month");
		System.out.println("cash_month == "+cash_month1);
		String cash_month="";
		if(cash_month1.equalsIgnoreCase("1")){
			cash_month="JAN";
		}
		if(cash_month1.equalsIgnoreCase("2")){
			cash_month="FEB";
		}
		if(cash_month1.equalsIgnoreCase("3")){
			 cash_month="MAR";
		}
		if(cash_month1.equalsIgnoreCase("4")){
			 cash_month="APR";
		}
		if(cash_month1.equalsIgnoreCase("5")){
			 cash_month="MAY";
		}
		if(cash_month1.equalsIgnoreCase("6")){
			 cash_month="JUN";
		}
		if(cash_month1.equalsIgnoreCase("7")){
			 cash_month="JUL";
		}
		if(cash_month1.equalsIgnoreCase("8")){
			 cash_month="AUG";
		}
		if(cash_month1.equalsIgnoreCase("9")){
			 cash_month="SEP";
		}
		if(cash_month1.equalsIgnoreCase("10")){
			 cash_month="OCT";
		}
		if(cash_month1.equalsIgnoreCase("11")){
			 cash_month="NOV";
		}
		if(cash_month1.equalsIgnoreCase("12")){
			 cash_month="DEC";
		}
		
		JasperPrint jasperPrint1=null;
  	 	File reportFile1=null;

		try {
			LoadDriver driver=new LoadDriver();
	           connection=driver.getConnection();
			try {
				statement = connection.createStatement();
				connection.clearWarnings();
			} catch (SQLException e) {
				System.out.println("Exception in creating statement:" + e);
			}
		} catch (Exception e) {
			System.out.println("Exception in openeing connection:" + e);
		}
		String strCommand = "";
		String xml = "";

		HttpSession session = request.getSession(false);

		try {

			if (session == null) {
				System.out.println(request.getContextPath() + "/index.jsp");
				response.sendRedirect(request.getContextPath() + "/index.jsp");

			}

		} catch (Exception e) {
			System.out.println("Redirect Error :" + e);
		}
		String userid = (String) session.getAttribute("UserId");
		 System.out.println("User Id is:" + userid);
		 
		
		try {
			System.out.println("chk 3");
			if (session == null) {
				System.out.println(request.getContextPath() + "/index.jsp");
				response.sendRedirect(request.getContextPath() + "/index.jsp");

			}
			System.out.println(session);

		} catch (Exception e) {
			System.out.println("Redirect Error :" + e);
		}

		try {
			strCommand = request.getParameter("command");
			System.out.println("strCommand ******* ");
			System.out.println("strCommand:-" + strCommand);
			 session =request.getSession(false);
			 String updatedby=(String)session.getAttribute("UserId");
			 System.out.println("updated by ===  "+updatedby);
			 
			 long l1=System.currentTimeMillis();
			 java.sql.Timestamp ts1=new java.sql.Timestamp(l1);
			 System.out.println("current time   ===  "+ts1);
			 
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		Servlets.Security.classes.UserProfile empProfile = (Servlets.Security.classes.UserProfile) session
				.getAttribute("UserProfile");
		int empid = empProfile.getEmployeeId();
		System.out.println("EMP ID ====  "+empid);
		String empName = empProfile.getEmployeeName();
		System.out.println("empName  ====  "+empName);
		long l = System.currentTimeMillis();
		System.out.println("currentTimeMillis  ====  "+l);
		Timestamp ts = new Timestamp(l);
		System.out.println("Timestamp  ====  "+ts);
		String District="",emp_name="";
		String acc_code=null;
		int c_year=0,c_month=0,bill_no=0,yr=0;
		
		try
		{
		 ps =
     		connection.prepareStatement("SELECT " 
												+"  office_id, " 
												+"  office_name, " 
												+"  office_level_id, " 
												+"  district_name " 
												+"FROM " 
												+"  ( " 
												+"    SELECT " 
												+"      office_id, " 
												+"      office_name, " 
												+"      office_level_id, " 
												+"      district_code " 
												+"    FROM " 
												+"      com_mst_offices " 
												+"    WHERE " 
												+"      office_id IN " 
												+"      ( " 
												+"        SELECT " 
												+"          office_id " 
												+"        FROM " 
												+"          hrm_emp_current_posting " 
												+"        WHERE " 
												+"          employee_id=? " 
												+"      ) " 
												+"  ) " 
												+"  a " 
												+"LEFT OUTER JOIN " 
												+"  ( " 
												+"    SELECT " 
												+"      DISTRICT_CODE AS d_code, " 
												+"      district_name " 
												+"    FROM " 
												+"      COM_MST_DISTRICTS " 
												+"  ) " 
												+"  b " 
												+"ON " 
												+"  b.d_code=a.district_code");
                       ps.setInt(1, empid);
    

//  ps.setInt(2, cmbAcc_UnitCode);
 rs = ps.executeQuery();
 		if(rs.next())
		 {
 			District=rs.getString("district_name");
		 
		 }
 		ps =
     		connection.prepareStatement("SELECT * " 
     				+"FROM " 
     				+"  (SELECT HRMS_SANCTION_ID, " 
     				+"    BILL_MAJOR_TYPE_CODE, " 
     				+"    BILL_MINOR_TYPE_CODE, " 
     				+"    BILL_SUB_TYPE_CODE, " 
     				+"    EMPLOYEE_ID, " 
     				+"    GPF_NO, " 
     				+"    DESIGNATION_ID, " 
     				+"    OFFICE_ID, " 
     				+"    SANCTION_PROC_NO, " 
     				+"    SANCTION_PROC_DATE, " 
     				+"    SANCTIONED_AMOUNT, " 
     				+"    REMARKS1, " 
     				+"    ACCOUNTING_UNIT_ID, " 
     				+"    ACCOUNTING_FOR_OFFICE_ID, " 
     				+"    CASHBOOK_YEAR, " 
     				+"    CASHBOOK_MONTH, " 
     				+"    VOUCHER_NO, " 
     				+"    REMARKS2, " 
     				+"    PROCESS_FLOW_ID, " 
     				+"    SANCTION_PROC_OFFICE_ID " 
     				+"  FROM HRM_SANCTIONS_BILLS_LINK_MST " 
     				+"  WHERE employee_id =? " 
     				+"  )a " 
     				+"LEFT OUTER JOIN " 
     				+"  (SELECT employee_id AS pay_emp_id, " 
     				+"    pay_element_value " 
     				+"  FROM HRM_PAY_EMP_PAYFIX_TRN " 
     				+"  WHERE pay_element_id='E01' " 
     				+"  AND pay_fixation_id = " 
     				+"    (SELECT MAX(pay_fixation_id) " 
     				+"    FROM hrm_pay_emp_payfix_trn " 
     				+"    WHERE employee_id =? " 
     				+"    ) " 
     				+"  )b " 
     				+"ON b.pay_emp_id=a.employee_id " 
     				+"LEFT OUTER JOIN " 
     				+"  (SELECT HRMS_SANCTION_ID AS SANCTION_ID, " 
     				+"    PAYMENT_HEAD_OF_AC, " 
     				+"    PAYMENT_TOWARDS, " 
     				+"    PAYMENT_AMOUNT, " 
     				+"    REMARKS, " 
     				+"    UPDATED_BY_USER_ID, " 
     				+"    UPDATED_DATE, " 
     				+"    PROCESS_FLOW_ID " 
     				+"  FROM HRM_SANCTIONS_BILLS_LINK_TRN " 
     				+"  )c " 
     				+"ON c.SANCTION_ID=a.HRMS_SANCTION_ID " 
     				+"LEFT OUTER JOIN " 
     				+"  (SELECT employee_name, employee_id AS eid FROM hrm_mst_employees " 
     				+"  )d " 
     				+"ON a.employee_id=d.eid"
);
                       ps.setInt(1, empid);
                       ps.setInt(1,empid);
                       results=ps.executeQuery();
                       while(results.next())
                       {
                    	   acc_code=results.getString("PAYMENT_HEAD_OF_AC")+",";
                       }
                  
    String acc_head_code=acc_code.substring(0,acc_code.length()-1);
    System.out.println("ACCCCCCCCCCCCCCC HEAD   CODE  ==="+head_code);
 		}
		catch(Exception e)
		{
		System.out.println("");
		}
			
			// System.out.println("does not exsist files.....");
			try
			{
				
				
			
			 Map map=new HashMap();
			// map=null;
			
			// map.put(empname,employee_name);
			 reportFile1 = new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/TTC_FORM.jasper"));	
			 
			 
			 if (!reportFile1.exists())
		      {
		          System.out.println("does not exsist");
		          throw new JRRuntimeException("File J not found. The report design must be compiled first.");
		      }
		      System.out.println(JRLoader.loadObject(reportFile1.getPath()));
		     JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile1.getPath());
	  	 	      System.out.println("Report File:"+reportFile1); 
	  	  
	  		  System.out.println("Sanction_Amt..............:"+Sanction_Amt);
	  	 	   String rep=getServletContext().getRealPath("/WEB-INF/ReportSrc/");
	  	 		  rep=rep+"/";
	  	 		System.out.println("rep:"+rep);
	  	 	    map.put("SUBREPORT_DIR", rep);
	  	 	    map.put("emp_id", emp_id);
	  	 	 map.put("acc_head_code", head_code);
	  	 	map.put("voucher_no", Vocucher_No);
	  	 	map.put("year", bill_date);
	  	 	map.put("CASHBOOK_MONTH", cash_month);
	  	 	map.put("CASHBOOK_YEAR", cash_year);
	  	 	map.put("emp_id", emp_id);
	  	 	map.put("Sanction_Amt", Sanction_Amt);
	  	 	      jasperPrint1 = JasperFillManager.fillReport(jasperReport, map, connection);
	  	 	      String rtype= request.getParameter("cmbReportType");
	  	 	      //rtype="TXT";
	  	 	      System.out.println("report type=" + rtype);
	  	 	       if (rtype.equalsIgnoreCase("HTML"))   
	  	 	       {
	  	 	    	PrintWriter out = response.getWriter();

	  	 	               response.setContentType("text/html");
	  	 	               response.setHeader ("Content-Disposition", "attachment;filename=\"leave_sanction_proceed_details.html\"");
	  	 	               // out = response.getWriter();
	  	 	               JRHtmlExporter exporter = new JRHtmlExporter();
	  	 	               exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,  false);
	  	 	               exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint1);
	  	 	               exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
	  	 	               exporter.exportReport();
	  	 	               out.flush();
	  	 	               out.close();
	  	 	       }
	  	 	 
	  	 	       else if (rtype.equalsIgnoreCase("PDF"))   
	  	 	       {	
	  	 	    	   /**System.out.println("*********"+rtype+"***********");
	  	 	           byte buf[] = JasperExportManager.exportReportToPdf(jasperPrint1);
	  	 	           response.setContentType("application/pdf");
	  	 	           response.setContentLength(buf.length);
	  	 	        System.out.println("*********"+rtype+"***********"+buf.length);
	  	 	           response.setHeader ("Content-Disposition", "attachment;filename=\"Bills Token Register Form.pdf\"");
	  	 	           OutputStream  out1 = response.getOutputStream();
	  	 	        System.out.println("*********out1***********"+out1);
	  	 	           out1.write(buf, 0, buf.length);
	  	 	        System.out.println("*********out1***********"+buf.length);
	  	 	           out1.close();
	  	 	           **/
	  	 	    	   
	  	 	           
	  	             	System.out.println("inside pdf");
	  	              byte buf[] = JasperExportManager.exportReportToPdf(jasperPrint1);
	  	 	           response.setContentType("application/pdf");
	  	 	           response.setContentLength(buf.length);
	  	 	        System.out.println("*********"+rtype+"***********"+buf.length);
	  	 	           response.setHeader ("Content-Disposition", "attachment;filename=\"Bills Token Register Form.pdf\"");
	  	 	           OutputStream  out1 = response.getOutputStream();
	  	 	        System.out.println("*********out1***********"+out1);
	  	 	           out1.write(buf, 0, buf.length);
	  	 	        System.out.println("*********out1***********"+buf.length);
	  	 	           out1.close();
	  	             
	  	 	          
	  	 	       }
	  	 	       else if (rtype.equalsIgnoreCase("EXCEL"))   
	  	 	       {
	  	 	                response.setContentType("application/vnd.ms-excel");
	  	 	                response.setHeader ("Content-Disposition", "attachment;filename=\"leave_sanction_proceed_details.xls\"");
	  	 	                JRXlsExporter exporterXLS = new JRXlsExporter(); 
	  	 	                exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint1); 
	  	 	                ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
	  	 	                exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,xlsReport); 
	  	 	                exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE); 
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
	  	 	    catch (Exception ex) 
	  	 	   {
	  	 	       String connectMsg = 
	  	 	       "Could not create the report " + ex.getMessage() + " " + 
	  	 	       ex.getLocalizedMessage();
	  	 	       System.out.println(connectMsg);
	  	 	  }
		    
		
		
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		

		Connection connection = null;
		Statement statement = null;
		ResultSet results = null;
		ResultSet results2;
		ResultSet rs = null;
		ResultSet rs2 = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		int cashbookYear = 0;
		String cashbookMonth = null;
		int unitid = 0;
		String unitname = "";
		int accid = 0;
		
		
		JasperPrint jasperPrint1=null;
  	 	File reportFile1=null;

		try {
			ResourceBundle rsb = ResourceBundle
					.getBundle("Servlets.Security.servlets.Config");
			String ConnectionString = "";

			String strDriver = rsb.getString("Config.DATA_BASE_DRIVER");
			String strdsn = rsb.getString("Config.DSN");
			String strhostname = rsb.getString("Config.HOST_NAME");
			String strportno = rsb.getString("Config.PORT_NUMBER");
			String strsid = rsb.getString("Config.SID");
			String strdbusername = rsb.getString("Config.USER_NAME");
			String strdbpassword = rsb.getString("Config.PASSWORD");

		    ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection


			Class.forName(strDriver.trim());
			connection = DriverManager.getConnection(ConnectionString,
					strdbusername.trim(), strdbpassword.trim());
			try {
				statement = connection.createStatement();
				connection.clearWarnings();
			} catch (SQLException e) {
				System.out.println("Exception in creating statement:" + e);
			}
		} catch (Exception e) {
			System.out.println("Exception in openeing connection:" + e);
		}
		String strCommand = "";
		String xml = "";

		HttpSession session = request.getSession(false);

		try {

			if (session == null) {
				System.out.println(request.getContextPath() + "/index.jsp");
				response.sendRedirect(request.getContextPath() + "/index.jsp");

			}

		} catch (Exception e) {
			System.out.println("Redirect Error :" + e);
		}
		String userid = (String) session.getAttribute("UserId");
		// System.out.println("User Id is:" + userid);
		try {
			System.out.println("chk 3");
			if (session == null) {
				System.out.println(request.getContextPath() + "/index.jsp");
				response.sendRedirect(request.getContextPath() + "/index.jsp");

			}
			System.out.println(session);

		} catch (Exception e) {
			System.out.println("Redirect Error :" + e);
		}

		try {
			strCommand = request.getParameter("command");
			System.out.println("strCommand:-------" + strCommand);
			System.out.println("*******" + strCommand);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		 session =request.getSession(false);
		 String updatedby=(String)session.getAttribute("UserId");
		 System.out.println("updated by ===  "+updatedby);
		 
		 long l1=System.currentTimeMillis();
		 java.sql.Timestamp ts1=new java.sql.Timestamp(l1);
		 System.out.println("current time   ===  "+ts1);
		

		Servlets.Security.classes.UserProfile empProfile = (Servlets.Security.classes.UserProfile) session
				.getAttribute("UserProfile");
		int empid = empProfile.getEmployeeId();
		String empName = empProfile.getEmployeeName();
		long l = System.currentTimeMillis();
		Timestamp ts = new Timestamp(l);
		System.out.println("inside");    
		
		if (strCommand.equalsIgnoreCase("LoadUnitWise_Office")) {
			System.out.println("inside");    
	            //added by sathya on 11May2012
			PrintWriter out = response.getWriter();
	            String CONTENT_TYPE = "text/xml; charset=windows-1252";
	            response.setContentType(CONTENT_TYPE);
	       
	            xml = "<response><command>" + strCommand + "</command>";

	            int cmbAcc_UnitCode = 0;
	            try {
	                cmbAcc_UnitCode =
	                        Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
	                 
	            } catch (Exception e) {
	                System.out.println("Exception to catch account head ");
	            }
	            try {
	            //    if( (employee_id==8569) || (employee_id==8696) )
	                
	                
	                    ps =
	                    		connection.prepareStatement("select ACCOUNTING_FOR_OFFICE_ID,b.OFFICE_NAME from FAS_MST_ACCT_UNIT_OFFICES a,COM_MST_OFFICES b " +
	                        "where a.ACCOUNTING_FOR_OFFICE_ID=b.OFFICE_ID  and a.ACCOUNTING_UNIT_ID=? ");
	                                      ps.setInt(1, cmbAcc_UnitCode);
	                   
	               
	              //  ps.setInt(2, cmbAcc_UnitCode);
	                rs = ps.executeQuery();
	                int cnt = 0;

	                while (rs.next()) {
	                    xml =
	 xml + "<offid>" + rs.getInt("ACCOUNTING_FOR_OFFICE_ID") + "</offid>";
	                    xml =
	 xml + "<offname>" + rs.getString("OFFICE_NAME") + "</offname>";
	                    cnt++;
	                }
	               
	                if (cnt != 0)
	                    xml = xml + "<flag>success</flag>";
	                else
	                    xml = xml + "<flag>failure</flag>";
	            } catch (Exception e) {
	                System.out.println("catch..HERE.in load head code." + e);
	                xml = xml + "<flag>failure</flag>";
	            }
	            xml = xml + "</response>";
	            System.out.println(xml);
	            out.write(xml);
	        }
		
	
		
		
		

		else if (strCommand.equalsIgnoreCase("getBillMajorType")) {
			PrintWriter out = response.getWriter();

			xml = xml + "<response><command>getBillMajorType</command>";
			xml = xml + "<empid>" + empid + "</empid>";
			xml = xml + "<empName>" + empName + "</empName>";
			try {
				String su = "select BILL_MAJOR_TYPE_CODE,BILL_MAJOR_TYPE_DESC from FAS_BILL_MAJOR_TYPES WHERE status='L' order by bill_major_type_code";
				ps = connection.prepareStatement(su);
				rs = ps.executeQuery();
				while (rs.next()) {
					xml = xml + "<billMajorTypeCode>"
							+ rs.getInt("BILL_MAJOR_TYPE_CODE")
							+ "</billMajorTypeCode>";

					xml = xml + "<billMajorTypeDesc>"
							+ rs.getString("BILL_MAJOR_TYPE_DESC")
							+ "</billMajorTypeDesc>";
				}
				String su1 = "select b.office_id,b.office_name from (select employee_id,office_id from HRM_EMP_CURRENT_POSTING where employee_id=?)a left outer join (select office_id,office_name from COM_MST_OFFICES)b on a.office_id=b.office_id";
				ps = connection.prepareStatement(su1);
				ps.setInt(1, empid);
				rs = ps.executeQuery();
				while (rs.next()) {
					xml = xml + "<OfficeID>" + rs.getInt("OFFICE_ID")
							+ "</OfficeID>";

					xml = xml + "<OfficeName>" + rs.getString("OFFICE_NAME")
							+ "</OfficeName>";

					System.out.println(rs.getInt("OFFICE_ID"));
					System.out.println(rs.getString("OFFICE_NAME"));
				}
				xml = xml + "<flag>success</flag>";
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}
			xml = xml + "</response>";
			out.write(xml);
		} else if (strCommand.equalsIgnoreCase("getOffice")) {
			PrintWriter out = response.getWriter();
			xml = xml + "<response><command>getOffice</command>";
			int txtEmpID_mas = Integer.parseInt(request
					.getParameter("txtEmpID_mas"));
			try {

				String su1 = "select b.office_id,b.office_name from (select employee_id,office_id from HRM_EMP_CURRENT_POSTING where employee_id=?)a left outer join (select office_id,office_name from COM_MST_OFFICES)b on a.office_id=b.office_id";
				ps = connection.prepareStatement(su1);
				ps.setInt(1, txtEmpID_mas);
				rs = ps.executeQuery();
				xml = xml + "<flag>success</flag>";
				while (rs.next()) {

					xml = xml + "<OfficeID>" + rs.getInt("OFFICE_ID")
							+ "</OfficeID>";

					xml = xml + "<OfficeName>" + rs.getString("OFFICE_NAME")
							+ "</OfficeName>";

					System.out.println(rs.getInt("OFFICE_ID"));
					System.out.println(rs.getString("OFFICE_NAME"));
				}
				ps.close();
				rs.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
			xml = xml + "</response>";
			out.write(xml);
		}
		//loadHead
		
		else if (strCommand.equalsIgnoreCase("loadHead")) {
			PrintWriter out = response.getWriter();
			xml = xml + "<response><command>loadHead</command>";
			int cboBillMajorType = Integer.parseInt(request.getParameter("cboBillMajorType"));
			int cboBillMinorType = Integer.parseInt(request.getParameter("cboBillMinorType"));
			int cboBillSubType = Integer.parseInt(request.getParameter("cboBillSubType"));
			int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
			int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
			int sCount=0;
			try {

				//String su1 = "SELECT ACCOUNT_HEAD_CODE FROM FAS_BILL_ACCOUNT_HEADS WHERE BILL_MAJOR_TYPE_CODE="+cboBillMajorType+" And Bill_Minor_Type_Code  ="+cboBillMinorType+" AND BILL_SUB_TYPE_CODE="+cboBillSubType;
			String su1="SELECT ACCOUNT_HEAD_CODE, "+
					"  Current_Year_Budget_Allotted,"+
					" (select account_head_desc from Com_Mst_Account_Heads h where H.Account_Head_Code=a.Account_Head_Code)as head_desc, "+
				"   Budget_Sofar_Spent,"+
				"   (Current_Year_Budget_Allotted-Budget_Sofar_Spent)AS Total_Bud"+
				" FROM Com_Budget_Details a"+
				" WHERE Accounting_Unit_Id    ="+cmbAcc_UnitCode+
				" AND Accounting_For_Office_Id="+cmbOffice_code+
				" AND Account_Head_Code      IN"+
				"   (SELECT ACCOUNT_HEAD_CODE"+
				"   FROM Fas_Bill_Account_Heads"+
				"   WHERE Bill_Major_Type_Code="+cboBillMajorType+"   AND Bill_Minor_Type_Code  ="+cboBillMinorType+"   AND BILL_SUB_TYPE_CODE    ="+cboBillSubType+"  )";
				ps1 = connection.prepareStatement(su1);
				//ps1.setInt(1, cboBillMajorType);
				results = ps1.executeQuery();
				while(results.next()) {
					sCount++;
						xml = xml + "<acchead>"+results.getString("ACCOUNT_HEAD_CODE")+"</acchead>";
						xml = xml + "<head_desc>"+results.getString("head_desc")+"</head_desc>";
						xml = xml + "<budgetAllo>"+results.getString("Current_Year_Budget_Allotted")+"</budgetAllo>";
						xml = xml + "<spent>"+results.getString("BUDGET_SOFAR_SPENT")+"</spent>";
						xml = xml + "<finttl>"+results.getString("total_bud")+"</finttl>";
				} 
				if(sCount>0)
				{
					xml = xml + "<flag>success</flag>";
				}
				else {
					xml = xml + "<flag>NoData</flag>";
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
			
			xml = xml + "</response>";
			out.write(xml);
		}
		//load budget
		
		else if (strCommand.equalsIgnoreCase("budgetProv")) {
			PrintWriter out = response.getWriter();
			xml = xml + "<response><command>budgetProv</command>";
			int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
			int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
			int txtAcc_HeadCode = Integer.parseInt(request.getParameter("txtAcc_HeadCode"));
			int sCount=0;
			try {

				String su1 = "Select Current_Year_Budget_Allotted,BUDGET_SOFAR_SPENT,(CURRENT_YEAR_BUDGET_ALLOTTED-BUDGET_SOFAR_SPENT)as total_bud,REF_NO,TO_CHAR(REF_DATE,'dd/mm/yyyy')AS REF_DATE  From Com_Budget_Details Where Accounting_Unit_Id="+cmbAcc_UnitCode+" and Accounting_For_Office_Id="+cmbOffice_code+" and ACCOUNT_HEAD_CODE="+txtAcc_HeadCode;
				ps1 = connection.prepareStatement(su1);
				results = ps1.executeQuery();
				while(results.next()) {
					sCount++;
						xml = xml + "<budgetAllo>"+results.getString("Current_Year_Budget_Allotted")+"</budgetAllo>";
						xml = xml + "<spent>"+results.getString("BUDGET_SOFAR_SPENT")+"</spent>";
						xml = xml + "<finttl>"+results.getString("total_bud")+"</finttl>";
						xml = xml + "<REF_NO>"+results.getString("REF_NO")+"</REF_NO>";
						xml = xml + "<REF_DATE>"+results.getString("REF_DATE")+"</REF_DATE>";
				} 
				if(sCount>0)
				{
					xml = xml + "<flag>success</flag>";
				}
				else {
					xml = xml + "<flag>NoData</flag>";
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
			xml = xml + "</response>";
			out.write(xml);

		}
		//acc desc
		else if (strCommand.equalsIgnoreCase("getAccDesc")) {
			PrintWriter out = response.getWriter();
			xml = xml + "<response><command>getAccDesc</command>";
			
			int txtAcc_HeadCode = Integer.parseInt(request.getParameter("txtAcc_HeadCode"));
			int sCount=0;
			try {

				String su1 = "SELECT ACCOUNT_HEAD_DESC FROM COM_MST_ACCOUNT_HEADS WHERE ACCOUNT_HEAD_CODE="+txtAcc_HeadCode;
				ps1 = connection.prepareStatement(su1);
				results = ps1.executeQuery();
				if(results.next()) {
					sCount++;
						xml = xml + "<headdesc>"+results.getString("ACCOUNT_HEAD_DESC")+"</headdesc>";
				} 
				if(sCount>0)
				{
					xml = xml + "<flag>success</flag>";
				}
				else {
					xml = xml + "<flag>NoData</flag>";
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
			
			xml = xml + "</response>";
			out.write(xml);
		}
		//getProceeding no
		
		
		
		
		else if (strCommand.equalsIgnoreCase("getProceedingNo")) {
			PrintWriter out = response.getWriter();
			xml = xml + "<response><command>getProceedingNo</command>";
			int cboBillMajorType = Integer.parseInt(request.getParameter("cboBillMajorType"));
			int cboBillMinorType = Integer.parseInt(request.getParameter("cboBillMinorType"));
			int cboBillSubType = Integer.parseInt(request.getParameter("cboBillSubType"));
			//int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
			//int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
			int sCount=0;
			try {

				String su1 = 
						"SELECT BILL_SUB_TYPE_CODE, " +
								"  Sanction_Proc_No " +
								"FROM HRM_SANCTIONS_BILLS_LINK_MST " +
								"WHERE BILL_MAJOR_TYPE_CODE =? " +
								"AND BILL_MINOR_TYPE_CODE   =? " +
								"AND BILL_SUB_TYPE_CODE     =?"  ;
				ps1 = connection.prepareStatement(su1);
				ps1.setInt(1, cboBillMajorType);
				ps1.setInt(2, cboBillMinorType);
				ps1.setInt(3, cboBillSubType);
				results = ps1.executeQuery();
				while(results.next()) {
					sCount++;
					
						xml = xml + "<procNo>"+results.getString("Sanction_Proc_No")+"</procNo>";
						xml = xml + "<BILL_SUB_TYPE_CODE>"+results.getString("BILL_SUB_TYPE_CODE")+"</BILL_SUB_TYPE_CODE>";
//						xml = xml + "<BILL_MAJOR_TYPE_CODE>"+results.getString("BILL_MAJOR_TYPE_CODE")+"</BILL_MAJOR_TYPE_CODE>";
//						xml = xml + "<BILL_MINOR_TYPE_CODE>"+results.getString("BILL_MINOR_TYPE_CODE")+"</BILL_MINOR_TYPE_CODE>";
						
					 
				} 
				System.out.println("scount  === "+sCount);
				if(sCount>0)
				{
					xml = xml + "<flag>success</flag>";
				}
				else {
					xml = xml + "<flag>NoData</flag>";
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
			
			xml = xml + "</response>";
			out.write(xml);
		}
		
		else if(strCommand.equalsIgnoreCase("Acc_Head_Code"))
		{
			PrintWriter out = response.getWriter();
			xml = xml + "<response><command>Acc_Head_Code</command>";
				int count=0;
			try {
				
				String su1="SELECT ACCOUNT_HEAD_CODE FROM com_mst_account_heads"
;

//				String su1 = "SELECT Sanction_Proc_No, "+
//							 "to_char(SANCTION_PROC_DATE,'dd/mm/yyyy')as SANCTION_PROC_DATE, "+
//						"  Sanctioned_Amount, "+
//						"   A.GPF_NO, "+
//						"   (select EMPLOYEE_INITIAL||'-'||EMPLOYEE_NAME from HRM_MST_EMPLOYEES b where b.GPF_NO=a.GPF_NO)as gpfName,  "+
//						"   Sanctioned_Amount, "+
//						"   A.Employee_Id, "+
//						"   (Select Employee_Initial||'-'||Employee_Name From Hrm_Mst_Employees S Where S.Employee_Id=A.Employee_Id)As Empname, "+
//						"   A.Office_Id, "+
//						"  (select c.OFFICE_NAME from COM_MST_OFFICES c where c.OFFICE_ID=a.OFFICE_ID)as offName "+
//						" From Hrm_Gpf_Withdrawal_Sanction a "+
//						" Where Withdrawal_Type In (Select Withdraw_Type From Hrm_Gpf_Withdrawal_Type Where Wd_Alias_Code="+cboBillSubType+
//						"   ) "+
//						"   And SANCTION_PROC_ID='"+procno+"'";
				ps1 = connection.prepareStatement(su1);
				//ps1.setString(1, procno);
				results = ps1.executeQuery();
				System.out.println("Results   === ");
				while(results.next()) {
					count++;
						//xml = xml + "<procNo>"+results.getString("Sanction_Proc_No")+"</procNo>";
					xml = xml + "<ACCOUNT_HEAD_CODE>"+results.getInt("ACCOUNT_HEAD_CODE")+"</ACCOUNT_HEAD_CODE>";	
				
				} 
				if(count>0)
				{
					xml = xml + "<count>"+count+"</count>";
					xml = xml + "<flag>success</flag>";
				}
				else {
					xml = xml + "<flag>NoData</flag>";
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
			
			xml = xml + "</response>";
			out.write(xml);
		}
		//details
		else if (strCommand.equalsIgnoreCase("getProceedingDetails")) {
			PrintWriter out = response.getWriter();
			xml = xml + "<response><command>getProceedingDetails</command>";
			int cboBillSubType = Integer.parseInt(request.getParameter("cboBillSubType"));
			String procno=request.getParameter("txtProceedingNo");
			System.out.println("Pro no === "+procno);
			int prod=0;
			int count=0;
			try {
				
				String su1="SELECT * " +
						"FROM " +
						"  (SELECT HRMS_SANCTION_ID, " +
						"    EMPLOYEE_ID, " +
						"    SANCTION_PROC_DATE, " +
						"    SANCTIONED_AMOUNT, " +
						"    EMPLOYEE_NAME, " +
						"    PAYMENT_HEAD_OF_AC, " +
						"    ACCOUNT_HEAD_DESC, " +
						"    ACCOUNT_HEAD_CODE, " +
						"    PAYMENT_AMOUNT , " +
						"    PROCESS_FLOW_ID " +
						"  FROM " +
						"    (SELECT a.EMPLOYEE_ID, " +
						"      TO_CHAR(SANCTION_PROC_DATE,'dd/mm/yyyy')AS SANCTION_PROC_DATE, " +
						"      a.SANCTIONED_AMOUNT, " +
						"      a.HRMS_SANCTION_ID, " +
						"      a.PROCESS_FLOW_ID, " +
						"      b.EMPLOYEE_NAME, " +
						"      c.PAYMENT_HEAD_OF_AC , " +
						"      c.PAYMENT_AMOUNT " +
						"    FROM HRM_SANCTIONS_BILLS_LINK_MST a " +
						"    INNER JOIN HRM_MST_EMPLOYEES b " +
						"    ON a.EMPLOYEE_ID =b.EMPLOYEE_ID " +
						"    INNER JOIN HRM_SANCTIONS_BILLS_LINK_TRN c " +
						"    ON a.HRMS_SANCTION_ID   =c.hrms_sanction_id " +
						"    WHERE a.SANCTION_PROC_NO=? " +
						"    ) mst " +
						"  LEFT OUTER JOIN " +
						"    ( SELECT ACCOUNT_HEAD_CODE , ACCOUNT_HEAD_DESC FROM COM_MST_ACCOUNT_HEADS " +
						"    ) acc " +
						"  ON acc.ACCOUNT_HEAD_CODE=mst.PAYMENT_HEAD_OF_AC " +
						"  ) ccc " +
						"LEFT OUTER JOIN " +
						"  (SELECT ACCOUNT_HEAD_CODE, " +
						"    CURRENT_YEAR_BUDGET_ALLOTTED , " +
						"    BUDGET_SOFAR_SPENT, " +
						"    REF_NO, " +
						"    TO_CHAR(REF_DATE,'dd/mm/yyyy')AS REF_DATE " +
						"  FROM COM_BUDGET_DETAILS " +
						"  ) bud " +
						"ON bud.ACCOUNT_HEAD_CODE=ccc.ACCOUNT_HEAD_CODE"

;

//				String su1 = "SELECT Sanction_Proc_No, "+
//							 "to_char(SANCTION_PROC_DATE,'dd/mm/yyyy')as SANCTION_PROC_DATE, "+
//						"  Sanctioned_Amount, "+
//						"   A.GPF_NO, "+
//						"   (select EMPLOYEE_INITIAL||'-'||EMPLOYEE_NAME from HRM_MST_EMPLOYEES b where b.GPF_NO=a.GPF_NO)as gpfName,  "+
//						"   Sanctioned_Amount, "+
//						"   A.Employee_Id, "+
//						"   (Select Employee_Initial||'-'||Employee_Name From Hrm_Mst_Employees S Where S.Employee_Id=A.Employee_Id)As Empname, "+
//						"   A.Office_Id, "+
//						"  (select c.OFFICE_NAME from COM_MST_OFFICES c where c.OFFICE_ID=a.OFFICE_ID)as offName "+
//						" From Hrm_Gpf_Withdrawal_Sanction a "+
//						" Where Withdrawal_Type In (Select Withdraw_Type From Hrm_Gpf_Withdrawal_Type Where Wd_Alias_Code="+cboBillSubType+
//						"   ) "+
//						"   And SANCTION_PROC_ID='"+procno+"'";
				ps1 = connection.prepareStatement(su1);
				ps1.setString(1, procno);
				results = ps1.executeQuery();
				System.out.println("Results   === ");
				while(results.next()) {
					prod++;
						xml = xml + "<PROCESS_FLOW_ID>"+results.getString("PROCESS_FLOW_ID")+"</PROCESS_FLOW_ID>";
					xml = xml + "<empid>"+results.getInt("EMPLOYEE_ID")+"</empid>";	
					xml = xml + "<procdate>"+results.getString("SANCTION_PROC_DATE")+"</procdate>";
						xml = xml + "<sanAmt>"+results.getString("Sanctioned_Amount")+"</sanAmt>";
						xml = xml + "<EMPLOYEE_NAME>"+results.getString("EMPLOYEE_NAME")+"</EMPLOYEE_NAME>";
						
						xml = xml + "<PAYMENT_HEAD_OF_AC>"+results.getString("PAYMENT_HEAD_OF_AC")+"</PAYMENT_HEAD_OF_AC>";
						//xml = xml + "<gpfno>"+results.getString("GPF_NO")+"-"+results.getString("ACCOUNT_HEAD_DESC")+"</gpfno>";
						
						xml = xml + "<ACCOUNT_HEAD_DESC>"+results.getString("ACCOUNT_HEAD_DESC")+"</ACCOUNT_HEAD_DESC>";
						//xml = xml + "<offname>"+results.getString("offName")+"</offname>";
						xml = xml + "<CURRENT_YEAR_BUDGET_ALLOTTED>"+results.getString("CURRENT_YEAR_BUDGET_ALLOTTED")+"</CURRENT_YEAR_BUDGET_ALLOTTED>";
						xml = xml + "<BUDGET_SOFAR_SPENT>"+results.getString("BUDGET_SOFAR_SPENT")+"</BUDGET_SOFAR_SPENT>";
						xml = xml + "<REF_NO>"+results.getString("REF_NO")+"</REF_NO>";
						xml = xml + "<REF_DATE>"+results.getString("REF_DATE")+"</REF_DATE>";
						xml = xml + "<PAYMENT_AMOUNT>"+results.getInt("PAYMENT_AMOUNT")+"</PAYMENT_AMOUNT>";
						xml = xml + "<HRMS_SANCTION_ID>"+results.getString("HRMS_SANCTION_ID")+"</HRMS_SANCTION_ID>";
				} 
				
				xml = xml + "<count>"+prod+"</count>";
				
				if(prod>0)
				{
					xml = xml + "<flag>success</flag>";
				}
				else {
					xml = xml + "<flag>NoData</flag>";
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
			
			xml = xml + "</response>";
			out.write(xml);
		}
		
		else if (strCommand.equalsIgnoreCase("getBillMinorType")) {
			PrintWriter out = response.getWriter();
			xml = xml + "<response><command>getBillMinorType</command>";
			int cboBillMajorType = Integer.parseInt(request
					.getParameter("cboBillMajorType"));
			int i2 = 1, i3 = 0;
			try {

				String su1 = "select BILL_MINOR_TYPE_CODE,BILL_MINOR_TYPE_DESC from FAS_BILL_MINOR_TYPES_MST where BILL_MAJOR_TYPE_CODE=? and status='L'";
				ps1 = connection.prepareStatement(su1);
				ps1.setInt(1, cboBillMajorType);
				results = ps1.executeQuery();
				if (results.next()) {
					try {

						String su = "select BILL_MINOR_TYPE_CODE,BILL_MINOR_TYPE_DESC from FAS_BILL_MINOR_TYPES_MST where BILL_MAJOR_TYPE_CODE=? and status='L'";
						ps = connection.prepareStatement(su);
						ps.setInt(1, cboBillMajorType);
						rs = ps.executeQuery();
						while (rs.next()) {

							xml = xml + "<billMinorTypeCode>"
									+ rs.getInt("BILL_MINOR_TYPE_CODE")
									+ "</billMinorTypeCode>";

							xml = xml + "<billMinorTypeDesc>"
									+ rs.getString("BILL_MINOR_TYPE_DESC")
									+ "</billMinorTypeDesc>";
						}
						xml = xml + "<flag>success</flag>";
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						xml = xml + "<flag>failure</flag>";
					}
				} else {
					xml = xml + "<flag>NoData</flag>";
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
			try {
				ps1 = connection
						.prepareStatement("Select max(BILL_NO) from FAS_BILL_REGISTERNEW where BILL_MAJOR_TYPE=?");
				ps1.setInt(1, cboBillMajorType);
				results2 = ps1.executeQuery();
				if (results2.next()) {
					i3 = results2.getInt(1);
					i2 = i2 + i3;

				} else {
					i2 = i2;
				}
				xml = xml + "<flag1>success</flag1>";
				xml = xml + "<BillNo>" + i2 + "</BillNo>";
			} catch (Exception e) {
				e.printStackTrace();
				xml = xml + "<flag1>failure</flag1>";
			}
			
			xml = xml + "</response>";
			out.write(xml);

		} else if (strCommand.equalsIgnoreCase("getBillsubType")) {
			PrintWriter out = response.getWriter();

			xml = xml + "<response><command>getBillsubType</command>";
			int cboBillMajorType = Integer.parseInt(request
					.getParameter("cboBillMajorType"));
			int cboBillMinorType = Integer.parseInt(request
					.getParameter("cboBillMinorType"));
			try {

				String su1 = "select BILL_SUB_TYPE_CODE,BILL_SUB_TYPE_DESC from FAS_BILL_SUB_TYPES where BILL_MAJOR_TYPE_CODE=? and BILL_MINOR_TYPE_CODE=? and status='L'";
				ps1 = connection.prepareStatement(su1);
				ps1.setInt(1, cboBillMajorType);
				ps1.setInt(2, cboBillMinorType);
				results = ps1.executeQuery();
				if (results.next()) {
					try {

						String su = "select BILL_SUB_TYPE_CODE,BILL_SUB_TYPE_DESC from FAS_BILL_SUB_TYPES where BILL_MAJOR_TYPE_CODE=? and BILL_MINOR_TYPE_CODE=? and status='L' ";
						ps = connection.prepareStatement(su);
						ps.setInt(1, cboBillMajorType);
						ps.setInt(2, cboBillMinorType);
						rs = ps.executeQuery();
						xml = xml + "<flag>success</flag>";
						while (rs.next()) {

							xml = xml + "<billSubTypeCode>"
									+ rs.getInt("BILL_SUB_TYPE_CODE")
									+ "</billSubTypeCode>";

							xml = xml + "<billsubTypeDesc>"
									+ rs.getString("BILL_SUB_TYPE_DESC")
									+ "</billsubTypeDesc>";
						}
						ps.close();
						rs.close();
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						xml = xml + "<flag>failure</flag>";
					}
				} else {
					xml = xml + "<flag>NoData</flag>";
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
			xml = xml + "</response>";
			out.write(xml);
		} else if (strCommand.equalsIgnoreCase("calculateBudget")) {
			PrintWriter out = response.getWriter();

			xml = xml + "<response><command>calculateBudget</command>";
			int cboAcc_UnitCode = Integer.parseInt(request
					.getParameter("cboAcc_UnitCode"));
			int cboOffice_code = Integer.parseInt(request
					.getParameter("cboOffice_code"));
			String txtaccountheadcode = request
					.getParameter("txtaccountheadcode");

			String year = request.getParameter("year");
			String year1 = request.getParameter("year1");
			String financialYear1 = (year + "-" + year1);

			try {

				String su1 = "select * from COM_BUDGET_DETAILS where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?";

				System.out.println(cboAcc_UnitCode);
				System.out.println(cboOffice_code);
				System.out.println(financialYear1);
				// System.out.println(txtaccountheadcode);

				ps1 = connection.prepareStatement(su1);
				ps1.setInt(1, cboAcc_UnitCode);
				ps1.setInt(2, cboOffice_code);
				ps1.setString(3, financialYear1);
				// ps1.setString(4, txtaccountheadcode);
				results = ps1.executeQuery();

				if (results.next()) {

					System.out.println("enter");

					try {

						String su = "select * from COM_BUDGET_DETAILS where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?";
						ps = connection.prepareStatement(su);
						ps.setInt(1, cboAcc_UnitCode);
						ps.setInt(2, cboOffice_code);
						ps.setString(3, financialYear1);
						// ps.setString(4, txtaccountheadcode);

						rs = ps.executeQuery();
						xml = xml + "<flag>success</flag>";
						while (rs.next()) {
							System.out.println("while");
							int currentYearBudgetAlloted = rs
									.getInt("CURRENT_YEAR_BUDGET_ALLOTTED");
							int budgetSoFarSpent = rs
									.getInt("BUDGET_SOFAR_SPENT");
							int balanceAmount = (currentYearBudgetAlloted - budgetSoFarSpent);

							xml = xml + "<BudgetProvided>"
									+ currentYearBudgetAlloted
									+ "</BudgetProvided>";

							xml = xml + "<BudgetSoFarSpent>" + budgetSoFarSpent
									+ "</BudgetSoFarSpent>";

							xml = xml + "<balanceAmount>" + balanceAmount
									+ "</balanceAmount>";
						}
						ps.close();
						rs.close();
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						xml = xml + "<flag>failure</flag>";
					}
				} else {
					xml = xml + "<flag>NoData</flag>";
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
			xml = xml + "</response>";
			out.write(xml);
		} else if (strCommand.equalsIgnoreCase("saveFunc")) {
			PrintWriter out = response.getWriter();
			xml = xml + "<response><command>saveFunc</command>";

			String cboAcc_UnitCode1 = request.getParameter("cmbAcc_UnitCode");
			int cboAcc_UnitCode = Integer.parseInt(cboAcc_UnitCode1);

			String cboOffice_code1 = request.getParameter("cmbOffice_code");
			int cmbOffice_code = Integer.parseInt(cboOffice_code1);

			String cboCashBook_Year1 = request.getParameter("year");
			int cboCashBook_Year = Integer.parseInt(cboCashBook_Year1);

			String cboCashBook_Month1 = request.getParameter("month");
			int cboCashBook_Month = Integer.parseInt(cboCashBook_Month1);

			String cboBillMajorType1 = request.getParameter("cboBillMajorType");
			int cboBillMajorType = Integer.parseInt(cboBillMajorType1);

			String cboBillMinorType1 = request.getParameter("cboBillMinorType");
			int cboBillMinorType = Integer.parseInt(cboBillMinorType1);

			String cboBillSubType1 = request.getParameter("cboBillSubType");
			int cboBillSubType = Integer.parseInt(cboBillSubType1);

			String txtBillNo1 = request.getParameter("txtBillNo");
			int txtBillNo = Integer.parseInt(txtBillNo1);

			xml = xml + "<BillNo>" + txtBillNo + "</BillNo>";

			java.sql.Date BillDate = null;
			java.util.GregorianCalendar c2;
			String[] sd = request.getParameter("txtBillDate").split("/");
			c2 = new java.util.GregorianCalendar(Integer.parseInt(sd[2]),
					Integer.parseInt(sd[1]) - 1, Integer.parseInt(sd[0]));
			java.util.Date d = c2.getTime();
			BillDate = new Date(d.getTime());

			String txtProceedingNo1 = request
					.getParameter("txtProceedingNo");
			int txtProceedingNo = Integer
					.parseInt(txtProceedingNo1);

			java.sql.Date ProceedingDate = null;
			java.util.GregorianCalendar c22;
			String[] sdd = request.getParameter("txtProceedingDate")
					.split("/");
			c22 = new java.util.GregorianCalendar(Integer.parseInt(sdd[2]),
					Integer.parseInt(sdd[1]) - 1, Integer.parseInt(sdd[0]));
			java.util.Date dd = c22.getTime();
			ProceedingDate = new Date(dd.getTime());
			
			String rdoMTC_70_Register = request
					.getParameter("rdoMTC_70_Register");

			String txtTotalBillAmount1 = request
					.getParameter("txtTotalBillAmount");
			float txtTotalBillAmount = Float.parseFloat(txtTotalBillAmount1);
                        
                        String txtTotalSanctionedAmount1 = request
                                        .getParameter("txtTotalSanctionedAmount");
                        float txtTotalSanctionedAmount = Float.parseFloat(txtTotalSanctionedAmount1);

			String txtAcc_HeadCode1 = request.getParameter("txtAcc_HeadCode");
			int txtAcc_HeadCode = Integer.parseInt(txtAcc_HeadCode1);

			int txtPayeeType =Integer.parseInt(request.getParameter("txtPayeeType"));

			String txtPayeeCode1 = request.getParameter("txtPayeeCode");
			String txtp[]=txtPayeeCode1.split("-");
			int txtPayeeCode =Integer.parseInt(txtp[0]);
                        System.out.println("code:::::"+txtPayeeCode);
                        String txtPayableTo1[] = request.getParameter("txtPayableTo").split("-");
                        int txtPayableTo = Integer.parseInt(txtPayableTo1[0]);

                        String txtEmpID_mas1 = request.getParameter("txtEmpID_mas");
			int txtEmpID_mas = Integer.parseInt(txtEmpID_mas1);

			String cboOffice1 = request.getParameter("cboOffice");
			int cboOffice = Integer.parseInt(cboOffice1);

			float txtBudgetProvision = Float.parseFloat(request
					.getParameter("txtBudgetProvision"));

			float txtBudgetSpent = Float.parseFloat(request
					.getParameter("txtBudgetSpent"));

			String txtRefNo1 = request.getParameter("txtRefNo");
			int txtRefNo = Integer.parseInt(txtRefNo1);

			java.sql.Date RefDate = null;
			java.util.GregorianCalendar cc8;
			String[] sdc8 = request.getParameter("txtRefDate").split("/");
			cc8 = new java.util.GregorianCalendar(Integer.parseInt(sdc8[2]),
					Integer.parseInt(sdc8[1]) - 1, Integer.parseInt(sdc8[0]));
			java.util.Date dc8 = cc8.getTime();
			RefDate = new Date(dc8.getTime());

			String mtxtRemarks = request.getParameter("mtxtRemarks");

			try {

				ps1 = connection.prepareStatement("insert into FAS_BILL_REGISTERNEW " + 
						"(ACCOUNTING_UNIT_ID," + 
						"ACCOUNTING_UNIT_OFFICE_ID," + 
						"CASHBOOK_YEAR," + 
						"CASHBOOK_MONTH," + 
						"BILL_NO," + 
						"SANCTION_PROC_NO," + 
						"PAYEE_CODE," + 
						"BILL_DATE," + 
						"BILL_PROCESSING_DONE_BY," + 
						"ACCOUNT_HEAD_CODE," + 
						"REMARKS," + 
						"STATUS," + 
						"UPDATED_BY_USERID," + 
						"UPDATED_DATE," + 
						"BILL_MINOR_TYPE_CODE," + 
						"BILL_MAJOR_TYPE," + 
						"BILL_SUB_TYPE_CODE," + 
						"PROCEEDING_RECD_DATE," + 
						"PAYEE_TYPE_CODE," + 
						"TOTAL_SANCTIONED_AMOUNT, " + 
						"TOTAL_BILL_AMOUNT," + 
						"MTC70ENTRY," + 
						"REF_NO," + 
						"REF_DATE," + 
						"BUDGET_PROVISION," + 
						"BUDGET_SO_FAR_SPENT," + 
						"PAYABLE_TO " + 
						") " + 
						" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				ps1.setInt(1, cboAcc_UnitCode);
				ps1.setInt(2, cmbOffice_code);
				ps1.setInt(3, cboCashBook_Year);
				ps1.setInt(4, cboCashBook_Month);
				ps1.setInt(5, txtBillNo);
				ps1.setInt(6, txtProceedingNo);
				ps1.setInt(7, txtPayeeCode);
				ps1.setDate(8, BillDate);
				ps1.setInt(9, txtEmpID_mas);
				ps1.setInt(10, txtAcc_HeadCode);
				ps1.setString(11, mtxtRemarks);
				ps1.setString(12, "L");
				ps1.setString(13, userid);
				ps1.setTimestamp(14, ts);
				ps1.setFloat(15, cboBillMinorType);
				ps1.setInt(16, cboBillMajorType);
				ps1.setInt(17, cboBillSubType);
				ps1.setDate(18, ProceedingDate);
				ps1.setInt(19, txtPayeeType);
				ps1.setFloat(20, txtTotalSanctionedAmount);
				ps1.setFloat(21, txtTotalBillAmount);
				ps1.setString(22, rdoMTC_70_Register);
				ps1.setInt(23, txtRefNo);
				ps1.setDate(24, RefDate);
				ps1.setFloat(25, txtBudgetProvision);
				ps1.setFloat(26, txtBudgetSpent);
				ps1.setInt(27, txtPayableTo);
				
				int errorcode=ps1.executeUpdate();
                                System.out.println("sql insert:::::::"+txtPayableTo);
				xml = xml + "<flag>success</flag>";
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}
			xml = xml + "</response>";
			out.write(xml);
		} else if (strCommand.equalsIgnoreCase("loadGrid")) {
			PrintWriter out = response.getWriter();
			xml = xml + "<response><command>loadGrid</command>";
			int BillNo = 0;
			String BillDate = null;
			String RefDate = null;
			String ProceedingDate = null;
			int unitcode= Integer.parseInt(request.getParameter("unit"));
			try {

				String su1 = "select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_OFFICE_ID,BILL_NO,BILL_MAJOR_TYPE from FAS_BILL_REGISTERNEW";
				ps1 = connection.prepareStatement(su1);
				results = ps1.executeQuery();
				if (results.next()) {
					String su = "SELECT ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_OFFICE_ID,BILL_NO, "+
								"  Payee_Code, "+
						"   (select EMPLOYEE_INITIAL||'-'||EMPLOYEE_NAME from HRM_MST_EMPLOYEES b where b.GPF_NO=a.Payee_Code)as gpfName, "+
						" 	  BILL_DATE, "+
						" 	  SANCTION_PROC_NO," +
						"     (SELECT Sanction_Proc_No FROM Hrm_Gpf_Withdrawal_Sanction s where s.Sanction_Proc_Id=a.SANCTION_PROC_NO)as proc_id, "+
						" 	  Bill_Processing_Done_By, "+
						" 	  (SELECT  e.EMPLOYEE_NAME||'.'||e.EMPLOYEE_INITIAL AS ENAME From Hrm_Mst_Employees E WHERE E.Employee_Id=a.Bill_Processing_Done_By)as done_by, "+
						" 	  ACCOUNT_HEAD_CODE, "+
						" 	  REMARKS, "+
						" 	  Bill_Major_Type, "+
						" 	  (select s.BILL_MAJOR_TYPE_DESC from FAS_BILL_MAJOR_TYPES s where s.BILL_MAJOR_TYPE_CODE=a.Bill_Major_Type)as major_desc, "+
						" 	   Bill_Minor_Type_Code, "+
						" 	   (select s.BILL_MINOR_TYPE_DESC from FAS_BILL_MINOR_TYPES_MST s where s.BILL_MAJOR_TYPE_CODE=a.Bill_Major_Type and S.Bill_Minor_Type_Code=a.Bill_Minor_Type_Code)as minor_desc, "+
						" 	  Bill_Sub_Type_Code, "+
						" 	  (select s.BILL_SUB_TYPE_DESC from FAS_BILL_SUB_TYPES s where s.BILL_MAJOR_TYPE_CODE=a.Bill_Major_Type and S.Bill_Minor_Type_Code=a.Bill_Minor_Type_Code and s.BILL_SUB_TYPE_CODE=a.Bill_Sub_Type_Code)as sub_desc, "+
						" 	  PROCEEDING_RECD_DATE, "+
						" 	  PAYEE_TYPE_CODE, "+
						" 	  TOTAL_SANCTIONED_AMOUNT, "+
						" 	  TOTAL_BILL_AMOUNT, "+
						" 	  MTC70ENTRY,REF_NO,REF_DATE, "+
						" BUDGET_PROVISION,Budget_So_Far_Spent,Payable_To, "+
						" 		  (SELECT  e.EMPLOYEE_NAME||'.'||e.EMPLOYEE_INITIAL AS ENAME From Hrm_Mst_Employees E WHERE E.Employee_Id=a.Payable_To)as payable_desc "+
						" 	FROM FAS_BILL_REGISTERNEW a where ACCOUNTING_UNIT_ID="+unitcode;
					ps = connection.prepareStatement(su);
					rs = ps.executeQuery();
					xml = xml + "<flag>success</flag>";
					while (rs.next()) {
						Date BillDate1 = rs.getDate("BILL_DATE");
						Date RefDate1 = rs.getDate("REF_DATE");
						Date ProceedingDate1 = rs.getDate("PROCEEDING_RECD_DATE");

						String Stringdate = BillDate1.toString();
						String Stringdate1 = RefDate1.toString();
						String Stringdate2 = ProceedingDate1.toString();

						String[] ddd = Stringdate.split("-");
						String[] ddd1 = Stringdate1.split("-");
						String[] ddd2 = Stringdate2.split("-");

						int day = Integer.parseInt(ddd[2]);
						int month = Integer.parseInt(ddd[1]);
						int year = Integer.parseInt(ddd[0]);

						int day1 = Integer.parseInt(ddd1[2]);
						int month1 = Integer.parseInt(ddd1[1]);
						int year1 = Integer.parseInt(ddd1[0]);

						int day2 = Integer.parseInt(ddd2[2]);
						int month2 = Integer.parseInt(ddd2[1]);
						int year2 = Integer.parseInt(ddd2[0]);

						if (month >= 10) {
							BillDate = (day + "/" + month + "/" + year);
						} else {
							BillDate = (day + "/0" + month + "/" + year);
						}

						if (month1 >= 10) {
							RefDate = (day1 + "/" + month1 + "/" + year1);
						} else {
							RefDate = (day1 + "/0" + month1 + "/" + year1);
						}
						if (month2 >= 10) {
							ProceedingDate = (day2 + "/" + month2 + "/" + year2);
						} else {
							ProceedingDate = (day2 + "/0" + month2 + "/" + year2);
						}

						xml = xml + "<AccUnitCode>"
								+ rs.getInt("ACCOUNTING_UNIT_ID")
								+ "</AccUnitCode>";

						xml = xml + "<AccForOfficeCode>"
								+ rs.getInt("ACCOUNTING_UNIT_OFFICE_ID")
								+ "</AccForOfficeCode>";

						xml = xml + "<BillMajorType>"+ rs.getInt("BILL_MAJOR_TYPE")+ "</BillMajorType>";
						xml = xml + "<major_desc>"+ rs.getString("major_desc")+ "</major_desc>";
						xml = xml + "<billMinorTypeCode>"+ rs.getInt("BILL_MINOR_TYPE_CODE")+ "</billMinorTypeCode>";
						xml = xml + "<minor_desc>"+ rs.getString("minor_desc")+ "</minor_desc>";
						xml = xml + "<billSubTypeCode>"+ rs.getInt("BILL_SUB_TYPE_CODE")+ "</billSubTypeCode>";
						xml = xml + "<sub_desc>"+ rs.getString("sub_desc")+ "</sub_desc>";
						xml = xml + "<BillNo>" + rs.getInt("BILL_NO")+ "</BillNo>";

						xml = xml + "<billDate>" + BillDate + "</billDate>";

						xml = xml + "<SanctionProceedingNo>"+ rs.getInt("SANCTION_PROC_NO")+ "</SanctionProceedingNo>";
						xml = xml + "<proc_id>"+ rs.getString("proc_id")+ "</proc_id>";
						
						xml = xml + "<SanctionProceedingDatee>"+ ProceedingDate+ "</SanctionProceedingDatee>";

						xml = xml + "<BillProcessingDoneBy>"+ rs.getInt("BILL_PROCESSING_DONE_BY")+ "</BillProcessingDoneBy>";

						xml = xml + "<MTC70Required>"
								+ rs.getString("MTC70ENTRY")
								+ "</MTC70Required>";

						xml = xml + "<TotalBillAmount>"
								+ rs.getInt("TOTAL_BILL_AMOUNT")
								+ "</TotalBillAmount>";
						
						xml = xml + "<TotalSanctionedAmount>"
						+ rs.getFloat("TOTAL_SANCTIONED_AMOUNT")
						+ "</TotalSanctionedAmount>";

						xml = xml + "<AccHeadCode>"
								+ rs.getInt("ACCOUNT_HEAD_CODE")
								+ "</AccHeadCode>";

						xml = xml + "<PayeeType>"+ rs.getString("PAYEE_TYPE_CODE")+ "</PayeeType>";
						xml = xml + "<Payeedesc>"+ rs.getString("gpfName")+ "</Payeedesc>";

						xml = xml + "<PayeeCode>" + rs.getInt("PAYEE_CODE")+ "</PayeeCode>";
						                                                           
                        xml = xml + "<BudgetProvision>" + rs.getFloat("BUDGET_PROVISION")
                                        + "</BudgetProvision>";
                                        
                        xml = xml + "<BudgetSofarSpent>" + rs.getFloat("BUDGET_SO_FAR_SPENT")+ "</BudgetSofarSpent>";
                                        
                        xml = xml + "<PayableTo>" + rs.getInt("PAYABLE_TO")+ "</PayableTo>";
                        xml = xml + "<payable_desc>" + rs.getString("payable_desc")+ "</payable_desc>";
					   
						xml = xml + "<RefNo>" + rs.getInt("REF_NO")
								+ "</RefNo>";

						xml = xml + "<RefDate>" + RefDate + "</RefDate>";

						xml = xml + "<Remarks>" + rs.getString("REMARKS")+ "</Remarks>";
					}
				} else {
					xml = xml + "<flag>NoData</flag>";
				}
			} catch (Exception e) {
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
			xml = xml + "</response>";
			out.write(xml);
		} else if (strCommand.equalsIgnoreCase("Edit")) {
			PrintWriter out = response.getWriter();

			xml = "<response><command>Edit</command>";
			int txtBillNo = Integer.parseInt(request.getParameter("txtBillNo"));
			int txtEmpID_mas = Integer.parseInt(request
					.getParameter("txtEmpID_mas"));
			int BillMajorType = 0, BillMinorType = 0, BillSubType = 0;

			int cboAcc_UnitCode = Integer.parseInt(request
					.getParameter("cmbAcc_UnitCode"));
			int cboOffice_code = Integer.parseInt(request
					.getParameter("cmbOffice_code"));
			String txtaccountheadcode = request.getParameter("txtAcc_HeadCode");
			int procNo =Integer.parseInt(request.getParameter("procNo"));
			try {
				ps1 = connection
						.prepareStatement("Select BILL_MAJOR_TYPE,BILL_MINOR_TYPE_CODE,BILL_SUB_TYPE_CODE from FAS_BILL_REGISTERNEW where BILL_NO=?");
				ps1.setInt(1, txtBillNo);
				results2 = ps1.executeQuery();
				xml = xml + "<flag1>success1</flag1>";
				while (results2.next()) {
					BillMajorType = results2.getInt("BILL_MAJOR_TYPE");
					BillMinorType = results2.getInt("BILL_MINOR_TYPE_CODE");
					BillSubType = results2.getInt("BILL_SUB_TYPE_CODE");
				}
				ps1.close();
				results2.close();
			} catch (Exception e) {
				xml = xml + "<flag1>failure1</flag1>";
				e.printStackTrace();

			}
			try {

				String su1 = "select BILL_MINOR_TYPE_CODE,BILL_MINOR_TYPE_DESC from FAS_BILL_MINOR_TYPES_MST where BILL_MAJOR_TYPE_CODE=? and BILL_MINOR_TYPE_CODE=? and status='L'";
				ps1 = connection.prepareStatement(su1);
				ps1.setInt(1, BillMajorType);
				ps1.setInt(2, BillMinorType);
				results = ps1.executeQuery();
				if (results.next()) {
					try {

						String su = "select BILL_MINOR_TYPE_CODE,BILL_MINOR_TYPE_DESC from FAS_BILL_MINOR_TYPES_MST where BILL_MAJOR_TYPE_CODE=? and BILL_MINOR_TYPE_CODE=? and status='L'";
						ps = connection.prepareStatement(su);
						ps.setInt(1, BillMajorType);
						ps.setInt(2, BillMinorType);
						rs = ps.executeQuery();
						xml = xml + "<flag>success</flag>";
						while (rs.next()) {

							xml = xml + "<billMinorTypeCode>"
									+ rs.getInt("BILL_MINOR_TYPE_CODE")
									+ "</billMinorTypeCode>";

							xml = xml + "<billMinorTypeDesc>"
									+ rs.getString("BILL_MINOR_TYPE_DESC")
									+ "</billMinorTypeDesc>";
						}
						ps.close();
						rs.close();
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						xml = xml + "<flag>failure</flag>";
					}
				} else {
					xml = xml + "<flag>NoData</flag>";
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}

			try {

				String su1 = "select BILL_SUB_TYPE_CODE,BILL_SUB_TYPE_DESC from FAS_BILL_SUB_TYPES where BILL_MAJOR_TYPE_CODE=? and BILL_MINOR_TYPE_CODE=? and BILL_SUB_TYPE_CODE=?";
				ps1 = connection.prepareStatement(su1);
				ps1.setInt(1, BillMajorType);
				ps1.setInt(2, BillMinorType);
				ps1.setInt(3, BillSubType);
				results = ps1.executeQuery();
				if (results.next()) {
					try {

						String su = "select BILL_SUB_TYPE_CODE,BILL_SUB_TYPE_DESC from FAS_BILL_SUB_TYPES where BILL_MAJOR_TYPE_CODE=? and BILL_MINOR_TYPE_CODE=? and BILL_SUB_TYPE_CODE=?";
						ps = connection.prepareStatement(su);
						ps.setInt(1, BillMajorType);
						ps.setInt(2, BillMinorType);
						ps.setInt(3, BillSubType);
						rs = ps.executeQuery();
						xml = xml + "<flag>success</flag>";
						while (rs.next()) {

							xml = xml + "<billSubTypeCode>"
									+ rs.getInt("BILL_SUB_TYPE_CODE")
									+ "</billSubTypeCode>";

							xml = xml + "<billsubTypeDesc>"
									+ rs.getString("BILL_SUB_TYPE_DESC")
									+ "</billsubTypeDesc>";
						}
						ps.close();
						rs.close();
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						xml = xml + "<flag>failure</flag>";
					}
				} else {
					xml = xml + "<flag>NoData</flag>";
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
			try {

				String su1 = "select EMPLOYEE_NAME from HRM_MST_EMPLOYEES where EMPLOYEE_ID=?";
				ps = connection.prepareStatement(su1);
				ps.setInt(1, txtEmpID_mas);
				rs = ps.executeQuery();
				xml = xml + "<flag>success</flag>";
				while (rs.next()) {
					xml = xml + "<BillProcessingDoneBy>"
							+ rs.getString("EMPLOYEE_NAME")
							+ "</BillProcessingDoneBy>";

					xml = xml + "<empid>" + txtEmpID_mas + "</empid>";
				}
				ps.close();
				rs.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
			try {

				String su1 = "select b.office_id,b.office_name from (select employee_id,office_id from HRM_EMP_CURRENT_POSTING where employee_id=?)a left outer join (select office_id,office_name from COM_MST_OFFICES)b on a.office_id=b.office_id";
				ps = connection.prepareStatement(su1);
				ps.setInt(1, txtEmpID_mas);
				rs = ps.executeQuery();
				xml = xml + "<flag2>success</flag2>";
				while (rs.next()) {

					xml = xml + "<OfficeID>" + rs.getInt("OFFICE_ID")
							+ "</OfficeID>";

					xml = xml + "<OfficeName>" + rs.getString("OFFICE_NAME")
							+ "</OfficeName>";

					System.out.println(rs.getInt("OFFICE_ID"));
					System.out.println(rs.getString("OFFICE_NAME"));
				}
				ps.close();
				rs.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				xml = xml + "<flag2>failure</flag2>";
			}
			String year = request.getParameter("year");
			String year1 = request.getParameter("year1");
			String financialYear1 = (year + "-" + year1);

			try {

				String su1 = "select * from COM_BUDGET_DETAILS where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?";

				System.out.println(cboAcc_UnitCode);
				System.out.println(cboOffice_code);
				System.out.println(financialYear1);
				// System.out.println(txtaccountheadcode);

				ps1 = connection.prepareStatement(su1);
				ps1.setInt(1, cboAcc_UnitCode);
				ps1.setInt(2, cboOffice_code);
				ps1.setString(3, financialYear1);
				// ps1.setString(4, txtaccountheadcode);
				results = ps1.executeQuery();

				if (results.next()) {

					System.out.println("enter");

					try {

						String su = "select * from COM_BUDGET_DETAILS where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?";
						ps = connection.prepareStatement(su);
						ps.setInt(1, cboAcc_UnitCode);
						ps.setInt(2, cboOffice_code);
						ps.setString(3, financialYear1);
						// ps.setString(4, txtaccountheadcode);

						rs = ps.executeQuery();
						xml = xml + "<flagg>success</flagg>";
						while (rs.next()) {
							System.out.println("while");
							int currentYearBudgetAlloted = rs
									.getInt("CURRENT_YEAR_BUDGET_ALLOTTED");
							int budgetSoFarSpent = rs
									.getInt("BUDGET_SOFAR_SPENT");
							int balanceAmount = (currentYearBudgetAlloted - budgetSoFarSpent);

							xml = xml + "<BudgetProvided>"
									+ currentYearBudgetAlloted
									+ "</BudgetProvided>";

							xml = xml + "<BudgetSoFarSpent>" + budgetSoFarSpent
									+ "</BudgetSoFarSpent>";

							xml = xml + "<balanceAmount>" + balanceAmount
									+ "</balanceAmount>";
						}
						ps.close();
						rs.close();
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						xml = xml + "<flagg>failure</flagg>";
					}
				} else {
					xml = xml + "<flagg>NoData</flagg>";
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				xml = xml + "<flagg>failure</flagg>";
			}
			//load sanc proc no
			try {

				String su1 = "SELECT SANCTION_PROC_NO,SANCTION_PROC_ID From Hrm_Gpf_Withdrawal_Sanction Where Accounting_Unit_Id="+cboAcc_UnitCode+" And Office_Id           ="+cboOffice_code+" AND SANCTION_PROC_ID    ="+procNo;
				ps = connection.prepareStatement(su1);
				rs = ps.executeQuery();
				xml = xml + "<flag_no>success</flag_no>";
				while (rs.next()) {
					xml = xml + "<sancno>"+ rs.getString("SANCTION_PROC_NO")+ "</sancno>";
					xml = xml + "<sancid>"+ rs.getInt("SANCTION_PROC_ID")+ "</sancid>";
				}
				ps.close();
				rs.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				xml = xml + "<flag_no>failure</flag_no>";
			}
			xml = xml + "</response>";
			out.write(xml);
			
		} else if (strCommand.equalsIgnoreCase("deleted")) {
			PrintWriter out = response.getWriter();
			xml = "<response><command>deleted</command>";
			int txtBillNo = Integer.parseInt(request.getParameter("txtBillNo"));

			int cboBillMajorType = Integer.parseInt(request
					.getParameter("cboBillMajorType"));

			String cboAcc_UnitCode1 = request.getParameter("cmbAcc_UnitCode");
			int cboAcc_UnitCode = Integer.parseInt(cboAcc_UnitCode1);

			String cboOffice_code1 = request.getParameter("cmbOffice_code");
			int cmbOffice_code = Integer.parseInt(cboOffice_code1);

			try {
				ps1 = connection
						.prepareStatement("Select * from FAS_BILL_REGISTERNEW where BILL_NO=? and ACCOUNTING_UNIT_ID=? and ACCOUNTING_UNIT_OFFICE_ID=? and BILL_MAJOR_TYPE=?");
				ps1.setInt(1, txtBillNo);
				ps1.setInt(2, cboAcc_UnitCode);
				ps1.setInt(3, cmbOffice_code);
				ps1.setInt(4, cboBillMajorType);
				rs = ps1.executeQuery();
				if (rs.next()) {
					ps = connection
							.prepareStatement("delete from FAS_BILL_REGISTERNEW where BILL_NO=? and ACCOUNTING_UNIT_ID=? and ACCOUNTING_UNIT_OFFICE_ID=? and BILL_MAJOR_TYPE=?");
					ps.setInt(1, txtBillNo);
					ps.setInt(2, cboAcc_UnitCode);
					ps.setInt(3, cmbOffice_code);
					ps.setInt(4, cboBillMajorType);
					ps.executeUpdate();
					xml = xml + "<flag>success</flag>";
				} else {
					xml = xml + "<flag>NoData</flag>";
				}

			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
			}

			xml = xml + "</response>";
			out.write(xml);
		} else if (strCommand.equalsIgnoreCase("IVno")) {
			PrintWriter out = response.getWriter();
			xml = "<response><command>IVno</command>";

			String cboAcc_UnitCode1 = request.getParameter("cmbAcc_UnitCode");
			int cboAcc_UnitCode = Integer.parseInt(cboAcc_UnitCode1);

			String cboOffice_code1 = request.getParameter("cmbOffice_code");
			int cmbOffice_code = Integer.parseInt(cboOffice_code1);

			String month1 = request.getParameter("month");
			int month = Integer.parseInt(month1);

			String year1 = request.getParameter("year");
			int year = Integer.parseInt(year1);

			try {
				ps = connection
						.prepareStatement("Select INVOICE_NO from FAS_INVOICE_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=?");
				ps.setInt(1, cboAcc_UnitCode);
				ps.setInt(2, cmbOffice_code);
				ps.setInt(3, year);
				ps.setInt(4, month);

				rs2 = ps.executeQuery();
				if (rs2.next()) {
					try {
						ps1 = connection
								.prepareStatement("Select INVOICE_NO from FAS_INVOICE_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=?");
						ps1.setInt(1, cboAcc_UnitCode);
						ps1.setInt(2, cmbOffice_code);
						ps1.setInt(3, year);
						ps1.setInt(4, month);

						results2 = ps1.executeQuery();
						xml = xml + "<flag1>success1</flag1>";

						while (results2.next()) {
							xml = xml + "<InvoiceNo>"
									+ results2.getInt("INVOICE_NO")
									+ "</InvoiceNo>";
						}

					} catch (Exception e) {
						xml = xml + "<flag1>failure1</flag1>";
						e.printStackTrace();

					}
				} else {
					xml = xml + "<flag1>NoData</flag1>";
				}
			} catch (Exception e) {
				xml = xml + "<flag1>failure1</flag1>";
				e.printStackTrace();

			}
			xml = xml + "</response>";
			out.write(xml);
		} else if (strCommand.equalsIgnoreCase("InvoiceDetails")) {
			PrintWriter out = response.getWriter();
			xml = "<response><command>InvoiceDetails</command>";

			String cboAcc_UnitCode1 = request.getParameter("cmbAcc_UnitCode");
			int cboAcc_UnitCode = Integer.parseInt(cboAcc_UnitCode1);

			String cboOffice_code1 = request.getParameter("cmbOffice_code");
			int cmbOffice_code = Integer.parseInt(cboOffice_code1);

			String month1 = request.getParameter("month");
			int month = Integer.parseInt(month1);

			String year1 = request.getParameter("year");
			int year = Integer.parseInt(year1);

			String txtIfSelectfromList1 = request
					.getParameter("txtIfSelectfromList");
			int txtIfSelectfromList = Integer.parseInt(txtIfSelectfromList1);

			String InvoiveDate = null;
			try {
				ps = connection
						.prepareStatement("Select INVOICE_DATE,INVOICE_AMOUNT from FAS_INVOICE_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and INVOICE_NO=?");
				ps.setInt(1, cboAcc_UnitCode);
				ps.setInt(2, cmbOffice_code);
				ps.setInt(3, year);
				ps.setInt(4, month);
				ps.setInt(5, txtIfSelectfromList);
				rs2 = ps.executeQuery();
				if (rs2.next()) {
					try {
						ps1 = connection
								.prepareStatement("Select INVOICE_DATE,INVOICE_AMOUNT from FAS_INVOICE_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and INVOICE_NO=?");
						ps1.setInt(1, cboAcc_UnitCode);
						ps1.setInt(2, cmbOffice_code);
						ps1.setInt(3, year);
						ps1.setInt(4, month);
						ps1.setInt(5, txtIfSelectfromList);
						results2 = ps1.executeQuery();
						xml = xml + "<flag1>success1</flag1>";

						while (results2.next()) {
							Date InvoiveDate1 = results2
									.getDate("INVOICE_DATE");

							String Stringdate = InvoiveDate1.toString();

							String[] ddd = Stringdate.split("-");

							int day = Integer.parseInt(ddd[2]);
							int month11 = Integer.parseInt(ddd[1]);
							int year11 = Integer.parseInt(ddd[0]);

							if (month11 >= 10) {
								InvoiveDate = (day + "/" + month11 + "/" + year11);
							} else {
								InvoiveDate = (day + "/0" + month11 + "/" + year11);
							}

							xml = xml + "<InvoiceDate>" + InvoiveDate
									+ "</InvoiceDate>";
							xml = xml + "<InvoiceAmount>"
									+ results2.getInt("INVOICE_AMOUNT")
									+ "</InvoiceAmount>";
						}

					} catch (Exception e) {
						xml = xml + "<flag1>failure1</flag1>";
						e.printStackTrace();

					}
				} else {
					xml = xml + "<flag1>NoData</flag1>";
				}
			} catch (Exception e) {
				xml = xml + "<flag1>failure1</flag1>";
				e.printStackTrace();

			}
			xml = xml + "</response>";
			out.write(xml);
		}else if (strCommand.equalsIgnoreCase("update")) {
			System.out.println("update");
            PrintWriter out = response.getWriter();
            xml = "<response><command>update</command>";

            String cboAcc_UnitCode1 = request.getParameter("cmbAcc_UnitCode");
            int cboAcc_UnitCode = Integer.parseInt(cboAcc_UnitCode1);

            String cboOffice_code1 = request.getParameter("cmbOffice_code");
            int cmbOffice_code = Integer.parseInt(cboOffice_code1);

            String cboCashBook_Year1 = request.getParameter("year");
            int cboCashBook_Year = Integer.parseInt(cboCashBook_Year1);

            String cboCashBook_Month1 = request.getParameter("month");
            int cboCashBook_Month = Integer.parseInt(cboCashBook_Month1);
System.out.println("cash book month === "+cboCashBook_Month);
            String cboBillMajorType1 = request.getParameter("cboBillMajorType");
            int cboBillMajorType = Integer.parseInt(cboBillMajorType1);

            String cboBillMinorType1 = request.getParameter("cboBillMinorType");
            int cboBillMinorType = Integer.parseInt(cboBillMinorType1);

            String cboBillSubType1 = request.getParameter("cboBillSubType");
            int cboBillSubType = Integer.parseInt(cboBillSubType1);

            String txtBillNo1 = request.getParameter("txtBillNo");
            int txtBillNo = Integer.parseInt(txtBillNo1);
            
            int sanction_id=Integer.parseInt(request.getParameter("sanction_id"));
           // xml = xml + "<BillNo>" + txtBillNo + "</BillNo>";

            java.sql.Date BillDate = null;
            java.util.GregorianCalendar c2;
            String[] sd = request.getParameter("txtBillDate").split("/");
            c2 = new java.util.GregorianCalendar(Integer.parseInt(sd[2]),
                    Integer.parseInt(sd[1]) - 1, Integer.parseInt(sd[0]));
            java.util.Date d = c2.getTime();
            BillDate = new Date(d.getTime());

            String txtProceedingNo = request
                            .getParameter("txtProceedingNo");
          

            java.sql.Date ProceedingDate = null;
            java.util.GregorianCalendar c22;
            String[] sdd = request.getParameter("txtProceedingDate")
                            .split("/");
            c22 = new java.util.GregorianCalendar(Integer.parseInt(sdd[2]),
                            Integer.parseInt(sdd[1]) - 1, Integer.parseInt(sdd[0]));
            java.util.Date dd = c22.getTime();
            ProceedingDate = new Date(dd.getTime());
           
            String rdoMTC_70_Register = request
                            .getParameter("rdoMTC_70_Register");

            String txtTotalBillAmount1 = request
                            .getParameter("txtTotalBillAmount");
            float txtTotalBillAmount = Float.parseFloat(txtTotalBillAmount1);
           
            String txtTotalSanctionedAmount1 = request
                            .getParameter("txtTotalSanctionedAmount");
            float txtTotalSanctionedAmount = Float.parseFloat(txtTotalSanctionedAmount1);

            String txtAcc_HeadCode1 = request.getParameter("txtAcc_HeadCode");
            int txtAcc_HeadCode = Integer.parseInt(txtAcc_HeadCode1);

            String txtPayeeType = request.getParameter("txtPayeeType");

            String txtPayeeCode1 = request.getParameter("txtPayeeCode");
            int txtPayeeCode = Integer.parseInt(txtPayeeCode1);
            
            
            
           
            String txtPayableTo1 = request.getParameter("txtPayableTo");
            //int txtPayableTo = Integer.parseInt(txtPayableTo1);

            String txtEmpID_mas1 = request.getParameter("txtEmpID_mas");
           // int txtEmpID_mas = Integer.parseInt(txtEmpID_mas1);

            String cboOffice1 = request.getParameter("cboOffice");
           // int cboOffice = Integer.parseInt(cboOffice1);

           
            

            String mtxtRemarks = request.getParameter("mtxtRemarks");
            String PROCESS_FLOW_ID="";
            
            try{
            	
            	String sq="SELECT PROCESS_FLOW_ID FROM hrm_sanctions_bills_link_mst WHERE gpf_no=?";
            	ps=connection.prepareStatement(sq);
            	ps.setInt(1,txtPayeeCode);
            	rs=ps.executeQuery();
            	while(rs.next())
            	{
            		PROCESS_FLOW_ID=rs.getString("PROCESS_FLOW_ID");
            	}
            	
            }catch (Exception e) {
                e.printStackTrace();
            }
            
            if(PROCESS_FLOW_ID.equalsIgnoreCase("MD"))
            {
            	xml = xml + "<Updated>Already Updated</Updated>";
            }
            else
            {
            	xml = xml + "<Updated>Already not Updated</Updated>";

            try {
            	String sql="UPDATE HRM_SANCTIONS_BILLS_LINK_MST " +
            			"SET ACCOUNTING_UNIT_ID    =?, " +
            			"  ACCOUNTING_FOR_OFFICE_ID=?, " +
            			"  CASHBOOK_YEAR           =?, " +
            			"  CASHBOOK_MONTH          =?, " +
            			"  VOUCHER_NO              =?, " +
            			"  REMARKS2                =?, " +
            			"  UPDATED_BY_USER_ID      =?, " +
            			"  UPDATED_DATE            =?, " +
            			"  PROCESS_FLOW_ID         ='MD' " +
            			"WHERE HRMS_SANCTION_ID    =? " +
           		    	"AND GPF_NO                =? " + 
            			"AND SANCTION_PROC_NO      =?" 
      ;
                ps1 = connection
                        .prepareStatement(sql);

       
                               System.out.println("acc unit code = "+cboAcc_UnitCode);
                               System.out.println("cmbOffice_code = "+cmbOffice_code);
                               System.out.println("cboCashBook_Year = "+cboCashBook_Year);
                               System.out.println("cboCashBook_Month = "+cboCashBook_Month);
                               System.out.println("txtBillNo = "+txtBillNo);
                            //   System.out.println("mtxtRemarks = "+mtxtRemarks);
                               System.out.println("UPDATED_BY_USER_ID = "+updatedby);
                               System.out.println("UPDATED_DATE = "+ts1);
                               System.out.println("sanction_id = "+sanction_id);
                               System.out.println("txtPayeeCode = "+txtPayeeCode);
                               System.out.println("txtProceedingNo = "+txtProceedingNo);
                ps1.setInt(1, cboAcc_UnitCode);
                ps1.setInt(2, cmbOffice_code);
                ps1.setInt(3, cboCashBook_Year);
                ps1.setInt(4, cboCashBook_Month);
           
                ps1.setInt(5, txtBillNo);
                ps1.setString(6, mtxtRemarks);
                ps1.setString(7, updatedby);
                ps1.setTimestamp(8, ts1);
                ps1.setInt(9, sanction_id);
                ps1.setInt(10, txtPayeeCode);
                ps1.setString(11, txtProceedingNo);
                ps1.executeUpdate();
                ps1.close();
                
                
                
                ps2 = connection
                        .prepareStatement("update HRM_SANCTIONS_BILLS_LINK_trn set  ACCOUNTING_UNIT_ID=?,UPDATED_BY_USER_ID      =?,UPDATED_DATE            =?,PROCESS_FLOW_ID         ='MD'   WHERE HRMS_SANCTION_ID   = ? AND BILL_MAJOR_TYPE_CODE = ? AND BILL_MINOR_TYPE_CODE = ?  AND BILL_SUB_TYPE_CODE   = ? AND EMPLOYEE_ID          = ?");


       
                               
                ps2.setInt(1, cboAcc_UnitCode);
                ps2.setString(2, updatedby);
                ps2.setTimestamp(3, ts1);
                ps2.setInt(4, sanction_id);
                ps2.setInt(5, cboBillMajorType);
                ps2.setInt(6, cboBillMinorType);
           
                ps2.setInt(7, cboBillSubType);
                ps2.setInt(8, txtPayeeCode);
                ps2.executeUpdate();
                ps2.close();
                xml = xml + "<flag>success</flag>";
                
            } catch (Exception e) {
                e.printStackTrace();
                xml = xml + "<flag>failure</flag>";
            }
            }
            xml = xml + "</response>";
            out.write(xml);
        }

       
        System.out.println(xml);
    }
}