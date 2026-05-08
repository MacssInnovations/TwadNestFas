package Servlets.FAS.FAS1.BillRegister.servlets;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver;
import Servlets.HR.HR1.EmployeeMaster.Reports.EnglishNumberToWords;

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
 * Servlet implementation class Bills_Token_Register_Report_serv
 */
public class Bills_Token_Register_Report_serv extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection con = null;
    public Bills_Token_Register_Report_serv() {
        super();

    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException {

		System.out.println("this is report");

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
		int emp_id=0;
		int Sanction_Amt1=0;
		java.util.Date bill_date=null;
		ArrayList ar_head=new ArrayList();
		String charamt="";
	    String SanProYN=request.getParameter("SanProYN");
	    
	    System.out.println("SanProYN:::"+SanProYN);
		int cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
		int cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));
		int txtCB_Year=Integer.parseInt(request.getParameter("txtCB_Year"));
		int txtCB_Month=Integer.parseInt(request.getParameter("txtCB_Month"));
		int billno=Integer.parseInt(request.getParameter("advnumber"));
		System.out.println("billno == "+billno);
		try
		{
			String sql="";
			/*if(SanProYN.equalsIgnoreCase("W"))
			//if dhana
				{
			 sql="SELECT m.bill_no, "+
				 " m.sanction_proc_no, "+
				" m.TOTAL_SANCTIONED_AMOUNT, "+
				"  numtochar(m.TOTAL_SANCTIONED_AMOUNT)AS charamt, "+
				" t.payable_to, "+
				" m.BILL_DATE "+
				" FROM fas_bill_register_master m,fas_bill_register_transaction t "+
				" WHERE m.ACCOUNTING_UNIT_ID=t.ACCOUNTING_UNIT_ID "+
				" and m.ACCOUNTING_UNIT_OFFICE_ID=t.ACCOUNTING_UNIT_OFFICE_ID "+
				" and m.CASHBOOK_YEAR=t.CASHBOOK_YEAR "+
				" and m.CASHBOOK_MONTH=t.CASHBOOK_MONTH "+
				" and m.BILL_NO=t.BILL_NO "+
				" and m.ACCOUNTING_UNIT_ID     = "+cmbAcc_UnitCode+
				" AND m.ACCOUNTING_UNIT_OFFICE_ID= "+cmbOffice_code+
				" AND m.CASHBOOK_YEAR            = "+txtCB_Year+
				" AND m.CASHBOOK_MONTH           =  "+txtCB_Month+
				" AND m.STATUS                   ='L' "+
				" AND m.BILL_NO                  ="+billno;
				}else if(SanProYN.equalsIgnoreCase("WO")){
					 sql=   "SELECT m.bill_no, " +
					 "  m.sanction_proc_no, " +
					 "  m.TOTAL_SANCTIONED_AMOUNT, " +
					 "  Numtochar(M.Total_Sanctioned_Amount)AS Charamt, " +
					 "  m.payable_to, " +
					 "  M.Bill_Date " +
					 " FROM Fas_Bill_Registernew M " +
					 " WHERE m.ACCOUNTING_UNIT_ID     ="+cmbAcc_UnitCode+
					 " AND m.ACCOUNTING_UNIT_OFFICE_ID= "+cmbOffice_code+
					 " AND m.CASHBOOK_YEAR            = "+txtCB_Year+
					 " AND m.CASHBOOK_MONTH           ="+txtCB_Month+
					 " AND M.Status                   ='L' " +
					 " AND m.BILL_NO                  ="+billno;
				}*/
			
			//// joe change on 10 Jul 2014
			
			
			String Sub_qry="";
			if(SanProYN.equalsIgnoreCase("W"))
			{
				Sub_qry ="  and BILL_TYPE='WSP' ";
			}else if(SanProYN.equalsIgnoreCase("WO")){
				Sub_qry ="  and BILL_TYPE='WOSP' ";
			}else
			{
				
			}
			System.out.println("Sub_qry  "+Sub_qry);
			 sql="SELECT m.bill_no, "+
			 " m.sanction_proc_no, "+
			" m.TOTAL_SANCTIONED_AMOUNT, "+
			"  (m.TOTAL_SANCTIONED_AMOUNT)::varchar AS charamt, "+
			" t.payable_to, "+
			" m.BILL_DATE "+
			" FROM fas_bill_register_master m,fas_bill_register_transaction t "+
			" WHERE m.ACCOUNTING_UNIT_ID=t.ACCOUNTING_UNIT_ID "+
			" and m.ACCOUNTING_UNIT_OFFICE_ID=t.ACCOUNTING_UNIT_OFFICE_ID "+
			" and m.CASHBOOK_YEAR=t.CASHBOOK_YEAR "+
			" and m.CASHBOOK_MONTH=t.CASHBOOK_MONTH "+
			" and m.BILL_NO=t.BILL_NO "+
			" and m.ACCOUNTING_UNIT_ID     = "+cmbAcc_UnitCode+
			" AND m.ACCOUNTING_UNIT_OFFICE_ID= "+cmbOffice_code+
			" AND m.CASHBOOK_YEAR            = "+txtCB_Year+
			" AND m.CASHBOOK_MONTH           =  "+txtCB_Month+
			" AND m.STATUS                   ='L' "+
			" and m.bill_date < '01-apr-2015' AND m.BILL_NO                  ="+billno + Sub_qry
			+" union all SELECT m.bill_no, "+
			 " m.sanction_proc_no, "+
			" m.TOTAL_SANCTIONED_AMOUNT, "+
			"  (m.TOTAL_SANCTIONED_AMOUNT)::varchar AS charamt, "+
			" t.payable_to, "+
			" m.BILL_DATE "+
			" FROM fas_bill_register_masternew m,fas_bill_register_transactionw t "+
			" WHERE m.ACCOUNTING_UNIT_ID=t.ACCOUNTING_UNIT_ID "+
			" and m.ACCOUNTING_UNIT_OFFICE_ID=t.ACCOUNTING_UNIT_OFFICE_ID "+
			" and m.CASHBOOK_YEAR=t.CASHBOOK_YEAR "+
			" and m.CASHBOOK_MONTH=t.CASHBOOK_MONTH "+
			" and m.BILL_NO=t.BILL_NO "+
			" and m.ACCOUNTING_UNIT_ID     = "+cmbAcc_UnitCode+
			" AND m.ACCOUNTING_UNIT_OFFICE_ID= "+cmbOffice_code+
			" AND m.CASHBOOK_YEAR            = "+txtCB_Year+
			" AND m.CASHBOOK_MONTH           =  "+txtCB_Month+
			" AND m.STATUS                   ='L' "+
			"  AND m.BILL_NO                  ="+billno + Sub_qry;
			
			
			
			
			System.out.println("sql "+sql);
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			if(rs.next())
			{
				Sanction_Amt1=rs.getInt("TOTAL_SANCTIONED_AMOUNT");
				emp_id=rs.getInt("payable_to");
				bill_date=rs.getDate("BILL_DATE");
				charamt=rs.getString("charamt");
				
		   	}
											
		}
		catch(Exception e)
		{
			
			System.out.println(e);
		}
	//	int sanctionid=Integer.parseInt(request.getParameter("sanctionid"));
		String sanctionid= request.getParameter("sanctionid");
		System.out.println("sanctionid == "+sanctionid);
		String head_code="";
//		int Sanction_Amt1=Integer.parseInt(request.getParameter("txtTotalSanctionedAmount"));
		Sanction_Amt1=Math.abs(Sanction_Amt1);
		System.out.println("Sanction_Amt1 == "+Sanction_Amt1);
		String Sanction_Amt="( "+EnglishNumberToWords.convert(Sanction_Amt1)+" Only)";
		//int emp_id=Integer.parseInt(request.getParameter("txtPayeeCode"));
//		head_code=request.getParameter("acc_code");
//		System.out.println("HEAD CODE == "+head_code);
		
//		String bill_date=request.getParameter("txtBillDate");
//		System.out.println("bill_date == "+bill_date);
		
	
		
		int cash_year=Integer.parseInt(request.getParameter("txtCB_Year"));
		System.out.println("cash_year == "+cash_year);
		String cash_month1=request.getParameter("txtCB_Month");
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
		 ps =connection.prepareStatement("SELECT " 
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
 		String qs ="";
 		if(SanProYN.equalsIgnoreCase("W")){
 		 qs ="SELECT * " 
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
     				+"  WHERE employee_id = "+emp_id+"  )a " 
     				+"LEFT OUTER JOIN " 
     				+"  (SELECT employee_id AS pay_emp_id, " 
     				+"    pay_element_value " 
     				+"  FROM HRM_PAY_EMP_PAYFIX_TRN " 
     				+"  WHERE pay_element_id='E01' " 
     				+"  AND pay_fixation_id = " 
     				+"    (SELECT MAX(pay_fixation_id) " 
     				+"    FROM hrm_pay_emp_payfix_trn " 
     				+"    WHERE employee_id = "+emp_id+"    ) " 
     				+"  )b " 
     				+"ON b.pay_emp_id=a.employee_id " 
     				+"LEFT OUTER JOIN " 
     				+"  (SELECT HRMS_SANCTION_ID AS SANCTION_ID, " 
     				+"    PAYMENT_HEAD_OF_AC ||'/'||(select ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS h where h.ACCOUNT_HEAD_CODE=PAYMENT_HEAD_OF_AC)as PAYMENT_HEAD_OF_AC, " 
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
     				+"ON a.employee_id=d.eid";
		}
		else if(SanProYN.equalsIgnoreCase("WO")){
				qs = "SELECT DISTINCT m.account_head_code, " +
						"    m.account_head_code ||'/'||(select h.ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS h where h.ACCOUNT_HEAD_CODE=m.account_head_code)as PAYMENT_HEAD_OF_AC"
						+ " FROM fas_bill_register_transaction m,fas_bill_register_master t "
						+ " WHERE t.accounting_unit_id=m.accounting_unit_id and t.cashbook_month=m.cashbook_month and  t.cashbook_year =m.cashbook_year and m.bill_no=t.bill_no and m.accounting_unit_id = " + cmbAcc_UnitCode
						+ " AND m.bill_no              =" + billno
						+ " AND m.cashbook_year        = " + txtCB_Year
						+ " and t.BILL_DATE < '01-APR-15'  AND m.cashbook_month       =" + txtCB_Month+
						" UNION ALL SELECT DISTINCT m.account_head_code, " +
						"    m.account_head_code ||'/'||(select h.ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS h where h.ACCOUNT_HEAD_CODE=m.account_head_code)as PAYMENT_HEAD_OF_AC"
						+ " FROM fas_bill_register_transactionw m , fas_bill_register_masternew t "
						+ " WHERE t.accounting_unit_id=m.accounting_unit_id and t.cashbook_month=m.cashbook_month and  t.cashbook_year =m.cashbook_year and m.bill_no=t.bill_no and m.accounting_unit_id = " + cmbAcc_UnitCode
						+ " AND m.bill_no              =" + billno
						+ " AND m.cashbook_year        = " + txtCB_Year
						+ " AND m.cashbook_month       =" + txtCB_Month;;
		}
			 		System.out.println("qs"+qs);
			 		int cnt=0;
			 		
			 		ps =connection.prepareStatement(qs);
                     
                       results=ps.executeQuery();
                       while(results.next())
                       {
                    	  
                    	   acc_code=results.getString("PAYMENT_HEAD_OF_AC");
                    	   ar_head.add(acc_code);
                       }
                       System.out.println(":ar::"+ar_head);
                      
    String acc_head_code=acc_code.substring(0,acc_code.length()-1);
                       
                    //   String acc_head_code=acc_code.substring(0,acc_code.length());
  //  System.out.println("ACCCCCCCCCCCCCCC HEAD   CODE  ==="+acc_head_code);
 		}
		catch(Exception e)
		{
		System.out.println("");
		}
			
			try
			{
			
			 Map map=new HashMap();
			if(SanProYN.equalsIgnoreCase("W")){
			 reportFile1 = new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/TTC_FORM.jasper"));	
			}	else if(SanProYN.equalsIgnoreCase("WO")){
			
				
				reportFile1 = new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/TTC_FORM_WOSP.jasper"));
			} if (!reportFile1.exists())
		      {
		         
		          throw new JRRuntimeException("File J not found. The report design must be compiled first.");
		      }
		      System.out.println(JRLoader.loadObject(reportFile1.getPath()));
		     JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile1.getPath());
	  	 	      System.out.println("Report File:"+reportFile1); 
	  	  
	  		  System.out.println("Sanction_Amt..............:"+Sanction_Amt);
	  	 	   String rep=getServletContext().getRealPath("/WEB-INF/ReportSrc/");
	  	 		  rep=rep+"/";
	  	 		System.out.println("rep:"+rep);
	  	 		map.put("unit_id", cmbAcc_UnitCode);
				map.put("month", txtCB_Month);
	  	 	    map.put("SUBREPORT_DIR", rep);
	  	 	    map.put("emp_id", emp_id);
	  	 	    map.put("acc_head_code", ar_head);
		  	 	map.put("voucher_no",billno);
		  	 	map.put("year", bill_date);
		  	 	map.put("CASHBOOK_MONTH", cash_month);
		  	 	map.put("CASHBOOK_YEAR", cash_year);
		  	 //	map.put("emp_id", emp_id);
		  	 	map.put("Sanction_Amt", charamt);
		  	 	map.put("sanctionid", sanctionid);
	  	 	
		  	 	System.out.println("map : "+map);
	  	 	      jasperPrint1 = JasperFillManager.fillReport(jasperReport, map, connection);
	  	 	      String rtype= request.getParameter("cmbReportType");
	  	 	      rtype="PDF";
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
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException {

	    String CONTENT_TYPE = "text/xml; charset=windows-1252";
	    response.setContentType(CONTENT_TYPE); 
		 System.out.println("Welcome Servlet");
		 PrintWriter out = response.getWriter();
		    
		 
		 	PreparedStatement ps=null;
		 	ResultSet rs=null;
			String cmd=request.getParameter("command");
		    String xml="";
		    int count=0;
		    int cmbAcc_UnitCode = 0,cmbOffice_code=0;
		    
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
				con = DriverManager.getConnection(ConnectionString,
						strdbusername.trim(), strdbpassword.trim());
				
			} catch (Exception e) {
				System.out.println("Exception in openeing connection:" + e);
			}
			try
		    {
		         cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
		    } 
		    catch (Exception e) 
		    {
		        System.out.println("Exception to catch cmbAcc_UnitCode ");
		    }
		    try
		    {
		        cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
		    }
		    catch (Exception e) 
		    {
		        System.out.println("Exception to catch cmbOffice_code ");
		    } 
		    String SanProYN=request.getParameter("SanProYN");
		    System.out.println("SanProYN:::"+SanProYN);
		    System.out.println("cmbOffice_code:::"+cmbOffice_code);
		    int year=Integer.parseInt(request.getParameter("txtCB_Year"));
		    System.out.println("year:::"+year);
			int month=Integer.parseInt(request.getParameter("txtCB_Month"));
			System.out.println("month:::"+month);
								
			 if(cmd.equalsIgnoreCase("loadBillNo"))
			{
				xml="<response><command>loadBillNo</command>";				
				try
							{
					String sql="";
				/*	if(SanProYN.equalsIgnoreCase("W")){
						
								 sql="SELECT bill_no,sanction_proc_no," +
								 " (select m.SANCTION_PROC_NO as id from HRM_SANCTIONS_BILLS_LINK_MST m " +
								 " where TO_CHAR(m.HRMS_SANCTION_ID)=b.SANCTION_PROC_NO)as sancid ,TOTAL_BILL_AMOUNT,PAYABLE_TO "+
										" FROM fas_bill_register_master b WHERE ACCOUNTING_UNIT_ID     ="+cmbAcc_UnitCode+" AND " +
										" ACCOUNTING_UNIT_OFFICE_ID="+cmbOffice_code+" AND CASHBOOK_YEAR            ="+year+" AND CASHBOOK_MONTH           = " +month+
										" AND STATUS                   ='L'";
					}else if(SanProYN.equalsIgnoreCase("WO")){
						 sql="SELECT bill_no,sanction_proc_no,TOTAL_BILL_AMOUNT,PAYABLE_TO," +
						 " (select m.SANCTION_PROC_NO as id from HRM_SANCTIONS_BILLS_LINK_MST m " +
						 " where TO_CHAR(m.HRMS_SANCTION_ID)=b.SANCTION_PROC_NO)as sancid "+
								" FROM Fas_Bill_Registernew b WHERE ACCOUNTING_UNIT_ID     ="+cmbAcc_UnitCode+" AND " +
								" ACCOUNTING_UNIT_OFFICE_ID="+cmbOffice_code+" AND CASHBOOK_YEAR            ="+year+" AND CASHBOOK_MONTH           = " +month+
								" AND STATUS                   ='L'";
					}*/
					
					
					
					///// joe change on 10 july 2014
					String Query="";
					if(SanProYN.equalsIgnoreCase("W"))
						{
							Query= "  and BILL_TYPE='WSP' ";
						}else 	if(SanProYN.equalsIgnoreCase("WO")){
							Query= "  and BILL_TYPE='WOSP' ";
						}else
						{
							
						}
					
				sql = "SELECT bill_no,sanction_proc_no,"
						+ " (select m.SANCTION_PROC_NO as id from HRM_SANCTIONS_BILLS_LINK_MST m "
						+ " where m.HRMS_SANCTION_ID=b.SANCTION_PROC_NO::numeric)as sancid ,TOTAL_BILL_AMOUNT,PAYABLE_TO "
						+ " FROM fas_bill_register_master b WHERE ACCOUNTING_UNIT_ID     ="
						+ cmbAcc_UnitCode + " AND "
						+ " ACCOUNTING_UNIT_OFFICE_ID=" + cmbOffice_code
						+ " AND CASHBOOK_YEAR            =" + year
						+ " AND CASHBOOK_MONTH           = " + month
						+ " and bill_date < '01-Apr-15' AND STATUS                   ='L'" + Query+
						" union all  SELECT bill_no,sanction_proc_no,"
						+ " (select m.SANCTION_PROC_NO as id from HRM_SANCTIONS_BILLS_LINK_MST m "
						+ " where m.HRMS_SANCTION_ID=b.SANCTION_PROC_NO::numeric)as sancid ,TOTAL_BILL_AMOUNT,PAYABLE_TO "
						+ " FROM fas_bill_register_masternew b WHERE ACCOUNTING_UNIT_ID     ="
						+ cmbAcc_UnitCode + " AND "
						+ " ACCOUNTING_UNIT_OFFICE_ID=" + cmbOffice_code
						+ " AND CASHBOOK_YEAR            =" + year
						+ " AND CASHBOOK_MONTH           = " + month
						+ "  AND STATUS                   ='L'" + Query;

System.out.println("sql "+sql);
String  pay_to="";
								ps=con.prepareStatement(sql);
								rs=ps.executeQuery();
								while(rs.next())
								{
									
									if(rs.getString("PAYABLE_TO")==null || rs.getString("PAYABLE_TO")=="0")pay_to="";
									else 
										pay_to="--"+rs.getString("PAYABLE_TO");
									System.out.println("pay_to"+pay_to);
									xml=xml+"<bill_no>"+rs.getInt("bill_no")+"</bill_no>";	
									xml=xml+"<sancNo>"+rs.getString("sanction_proc_no")+"</sancNo>";
									xml=xml+"<sancid>"+rs.getString("sancid")+"</sancid>";
									xml=xml+"<amtName>"+"Rs."+rs.getString("TOTAL_BILL_AMOUNT")+pay_to+"</amtName>";
									
								    count++;
							   	}
								if(count>0)
									xml = xml + "<flag>success</flag>";	
								else
									{
										xml = xml + "<flag>Nodata</flag>";
									}	
								rs.close();
								ps.close();
							}
							catch(Exception e)
							{
								xml = xml + "<flag>failure</flag>";
								System.out.println(e);
							}
			
				xml = xml + "</response>";
				System.out.println(xml);			
			}	
			 else if(cmd.equalsIgnoreCase("loadsancNo"))
			 {
				 int billno=Integer.parseInt(request.getParameter("billno"));
				
					xml="<response><command>loadsancNo</command>";				
					try
								{
						String sql="",Query="";
					/*	if(SanProYN.equalsIgnoreCase("W")){			
						 sql="SELECT sanction_proc_no,TOTAL_BILL_AMOUNT,PAYABLE_TO FROM fas_bill_register_master WHERE ACCOUNTING_UNIT_ID     ="+cmbAcc_UnitCode+" AND " +
											" ACCOUNTING_UNIT_OFFICE_ID="+cmbOffice_code+" AND CASHBOOK_YEAR            ="+year+" AND CASHBOOK_MONTH           = " +month+
											" AND STATUS                   ='L' and BILL_NO="+billno+" AND BILL_TYPE                ='WSP'" ;
								
						}else if(SanProYN.equalsIgnoreCase("WO")){			
							 sql="SELECT sanction_proc_no,TOTAL_BILL_AMOUNT,PAYABLE_TO FROM fas_bill_register_master WHERE ACCOUNTING_UNIT_ID     ="+cmbAcc_UnitCode+" AND " +
										" ACCOUNTING_UNIT_OFFICE_ID="+cmbOffice_code+" AND CASHBOOK_YEAR            ="+year+" AND CASHBOOK_MONTH           = " +month+
										" AND STATUS                   ='L' and BILL_NO="+billno+" AND BILL_TYPE                ='WOSP' ";
							
					}
					
						*/
						/*if(SanProYN.equalsIgnoreCase("WO")){
							 sql="SELECT sanction_proc_no,TOTAL_BILL_AMOUNT,PAYABLE_TO FROM Fas_Bill_Registernew WHERE ACCOUNTING_UNIT_ID     ="+cmbAcc_UnitCode+" AND " +
								" ACCOUNTING_UNIT_OFFICE_ID="+cmbOffice_code+" AND CASHBOOK_YEAR            ="+year+" AND CASHBOOK_MONTH           = " +month+
								" AND STATUS                   ='L' and BILL_NO="+billno;
						}*/
						if(SanProYN.equalsIgnoreCase("W"))
						{
							Query= "  and BILL_TYPE='WSP' and bill_no="+billno;
						}else 	if(SanProYN.equalsIgnoreCase("WO")){
							Query= "  and BILL_TYPE='WOSP' and bill_no="+billno;
						}else
						{
							
						}
					
					 sql="SELECT bill_no,sanction_proc_no," +
					 " (select m.SANCTION_PROC_NO as id from HRM_SANCTIONS_BILLS_LINK_MST m " +
					 " where m.HRMS_SANCTION_ID=b.SANCTION_PROC_NO::numeric)as sancid ,TOTAL_BILL_AMOUNT,PAYABLE_TO "+
							" FROM fas_bill_register_master b WHERE ACCOUNTING_UNIT_ID     ="+cmbAcc_UnitCode+" AND " +
							" ACCOUNTING_UNIT_OFFICE_ID="+cmbOffice_code+" AND CASHBOOK_YEAR            ="+year+" AND CASHBOOK_MONTH           = " +month+
							" and bill_date  < '01-Apr-15' AND STATUS                   ='L'"+Query+
							
							" union all SELECT bill_no,sanction_proc_no," +
							 " (select m.SANCTION_PROC_NO as id from HRM_SANCTIONS_BILLS_LINK_MST m " +
							 " where m.HRMS_SANCTION_ID=b.SANCTION_PROC_NO::numeric)as sancid ,TOTAL_BILL_AMOUNT,PAYABLE_TO "+
									" FROM fas_bill_register_masternew b WHERE ACCOUNTING_UNIT_ID     ="+cmbAcc_UnitCode+" AND " +
									" ACCOUNTING_UNIT_OFFICE_ID="+cmbOffice_code+" AND CASHBOOK_YEAR            ="+year+" AND CASHBOOK_MONTH           = " +month+
									" AND STATUS                   ='L'"+Query
							;
							
System.out.println("sql "+sql);
String  pay_to="";
								ps=con.prepareStatement(sql);
								rs=ps.executeQuery();
								while(rs.next())
								{
									
									if(rs.getString("PAYABLE_TO")==null || rs.getString("PAYABLE_TO")=="0")pay_to="";
									else 
										pay_to="--"+rs.getString("PAYABLE_TO");
									System.out.println("pay_to"+pay_to);
									xml=xml+"<bill_no>"+rs.getInt("bill_no")+"</bill_no>";	
									xml=xml+"<sancNo>"+rs.getString("sanction_proc_no")+"</sancNo>";
									xml=xml+"<sancid>"+rs.getString("sancid")+"</sancid>";
									xml=xml+"<amtName>"+"Rs."+rs.getString("TOTAL_BILL_AMOUNT")+pay_to+"</amtName>";
									
								    count++;
							   	}
								if(count>0)
									xml = xml + "<flag>success</flag>";	
								else
									{
										xml = xml + "<flag>Nodata</flag>";
									}	
								rs.close();
								ps.close();
							}
							catch(Exception e)
							{
								xml = xml + "<flag>failure</flag>";
								System.out.println(e);
							}

					//	System.out.println("sql "+sql);		
						//ps=con.prepareStatement(sql);
								//	rs=ps.executeQuery();
								//	while(rs.next())
								//	{
								//		xml=xml+"<sancNo>"+rs.getInt("sanction_proc_no")+"</sancNo>";
								//		xml=xml+"<amtName>"+"Rs."+rs.getString("TOTAL_BILL_AMOUNT")+"--"+rs.getString("PAYABLE_TO")+"</amtName>";
								//	    count++;
								 //  	}
								//	if(count>0)
								//		xml = xml + "<flag>success</flag>";	
								//	else
								//		{
								//			xml = xml + "<flag>Nodata</flag>";
									//	}								
								//}
							//	catch(Exception e)
							//	{
								//	xml = xml + "<flag>failure</flag>";
								//	System.out.println(e);
							//	}

					xml = xml + "</response>";
					System.out.println(xml);			
				 
			 }
			out.write(xml);
		    out.close();
	
	}

	

}
