package Servlets.FAS.FAS1.TPA.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class TPA_Audit_Verification
 */
public class TPA_Audit_Verification extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";    
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TPA_Audit_Verification() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection con = null;

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
			System.out.println("Exception in connection...." + e);
		}
		ResultSet rs = null, rs1 = null, rs2 = null, rs4 = null;
		CallableStatement cs = null;
		PreparedStatement ps = null, ps1 = null, ps2 = null;
		String xml = "";

		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		response.setHeader("Cache-Control", "no-cache");
		HttpSession session = request.getSession(false);
		try {
			if (session == null) {
				xml = "<response><command>sessionout</command><flag>sessionout</flag></response>";
				out.println(xml);
				System.out.println(xml);
				out.close();
				return;

			}
			// System.out.println(session);

		} catch (Exception e) {
			// System.out.println("Redirect Error :"+e);
		}
		System.out.println("java");
		String command;
		command = request.getParameter("command");

		session = request.getSession(false);
		String updatedby = (String) session.getAttribute("UserId");
		long l = System.currentTimeMillis();
		java.sql.Timestamp ts = new java.sql.Timestamp(l);
		System.out.println("got");
		System.out.println("command" + command);
		String CONTENT_TYPE = "text/xml; charset=windows-1252";
		response.setContentType(CONTENT_TYPE);
		DecimalFormat df=new DecimalFormat("#0.00");
		if (command.equalsIgnoreCase("get")) {
			System.out.println("gettttttttttttttt");
			// String xml="";
			int cmbAcc_UnitCode = 0, cmbOffice_code = 0;
			int cmbSL_type = 0;
			int addtional_field_value = 0;
			int y = 0,z=0,xx=0,yy=0;
			xml = "<response><command>" + command + "</command>";

			try {
				cmbAcc_UnitCode = Integer.parseInt(request
						.getParameter("cmbAcc_UnitCode"));
			} catch (Exception e) {
				System.out.println("error get acc unit code");
			}
			try {
				cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
			} catch (Exception e) {
				System.out.println("error get office id");
			}

			String option=request.getParameter("searchby");
			String reason="";
			if(option.equalsIgnoreCase("yearmonth")){

				String status_type=null;
				int cashMonth = Integer.parseInt(request.getParameter("cashmonth"));
				int cashYear = Integer.parseInt(request.getParameter("cashyear"));
				String type=request.getParameter("proformatype");
				
				if(type.equals("TPAOC"))
				{
				try {
					
					
					ps = con.prepareStatement("select VOUCHER_NO,to_char(VOUCHER_DATE,'dd/MM/yyyy') as VOUCHER_DATE,REASON_FOR_TRANSFER,PARTICULARS,VERIFY,to_char(VERIFIED_DATE,'dd/MM/yyyy')as VERIFIED_DATE from FAS_TPA_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and REASON_FOR_TRANSFER='Others' and STATUS='L' and VERIFY='Y' and (AUDIT_VERIFY is null OR AUDIT_VERIFY='N')");
					ps.setInt(1, cmbAcc_UnitCode);
					ps.setInt(2, cmbOffice_code);
					ps.setInt(3, cashYear);
					ps.setInt(4, cashMonth);
					
					rs = ps.executeQuery();

					xml = xml + "<unitid>" +cmbAcc_UnitCode+ "</unitid>";
					xml = xml + "<officeid>" + cmbOffice_code+ "</officeid>";
					xml = xml + "<cashyear>" + cashYear+ "</cashyear>";
					xml = xml + "<cashmonth>" +cashMonth+ "</cashmonth>";

					while (rs.next()) {
						int voucherNo=rs.getInt("VOUCHER_NO");
						xml = xml + "<vno>" +voucherNo + "</vno>";
						xml = xml + "<vdate>" + rs.getString("VOUCHER_DATE")+ "</vdate>";
						xml = xml + "<reason>" + rs.getString("REASON_FOR_TRANSFER")+ "</reason>";
						xml = xml + "<particular>" + rs.getString("PARTICULARS")+ "</particular>";
						xml = xml + "<verify>" + rs.getString("VERIFY")+ "</verify>";
						xml = xml + "<verified_date>" + rs.getString("VERIFIED_DATE")+ "</verified_date>";
						ps1 = con.prepareStatement("select  amount from FAS_TPA_TRANSACTION where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=? and ACCOUNT_HEAD_CODE in (620101,900301) and sl_no=1");
						ps1.setInt(1, cmbAcc_UnitCode);
						ps1.setInt(2, cmbOffice_code);
						ps1.setInt(3, cashYear);
						ps1.setInt(4, cashMonth);
						ps1.setInt(5, voucherNo);
						rs1=ps1.executeQuery();
						rs1.next();
						xml = xml + "<amount>" +df.format(rs1.getBigDecimal("amount"))+ "</amount>";

						xx++;
					}
					ps.close();
					rs.close();
					
					System.out.println("be4444444444");
					//kjlhkjhkj
					
					ps2 = con.prepareStatement("select count(*) as total from FAS_TPA_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and REASON_FOR_TRANSFER!='Others' and STATUS='L' and VERIFY='Y' and (AUDIT_VERIFY is null OR AUDIT_VERIFY='N')");
					
					System.out.println("select count(*) as total from FAS_TPA_MASTER where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and CASHBOOK_YEAR="+cashYear+" and CASHBOOK_MONTH="+cashMonth+" and REASON_FOR_TRANSFER!='Others' and STATUS='L' and VERIFY='Y' and (AUDIT_VERIFY is null OR AUDIT_VERIFY='N')");
					ps2.setInt(1, cmbAcc_UnitCode);
					ps2.setInt(2, cmbOffice_code);
					ps2.setInt(3, cashYear);
					ps2.setInt(4, cashMonth);
					

					rs2 = ps2.executeQuery();
					if(rs2.next())
					{
						yy++;
						System.out.println("TOTAL=1=>"+rs2.getInt("total"));

						if(rs2.getInt("total")>0)
						{
							ps = con.prepareStatement("select VOUCHER_NO,to_char(VOUCHER_DATE,'dd/MM/yyyy') as VOUCHER_DATE,REASON_FOR_TRANSFER,PARTICULARS,VERIFY,to_char(VERIFIED_DATE,'dd/MM/yyyy')as VERIFIED_DATE from FAS_TPA_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and TPA_TYPE='TPAOC' and REASON_FOR_TRANSFER!='Others' and STATUS='L' and VERIFY='Y' and (AUDIT_VERIFY is null OR AUDIT_VERIFY='N')");
							ps.setInt(1, cmbAcc_UnitCode);
							ps.setInt(2, cmbOffice_code);
							ps.setInt(3, cashYear);
							ps.setInt(4, cashMonth);
							rs = ps.executeQuery();
							while (rs.next()) {
								int voucherNo=rs.getInt("VOUCHER_NO");
								xml = xml + "<vno>" +voucherNo + "</vno>";
								xml = xml + "<vdate>" + rs.getString("VOUCHER_DATE")+ "</vdate>";
								xml = xml + "<reason>" + rs.getString("REASON_FOR_TRANSFER")+ "</reason>";
								xml = xml + "<particular>" + rs.getString("PARTICULARS")+ "</particular>";
								xml = xml + "<verify>" + rs.getString("VERIFY")+ "</verify>";
								xml = xml + "<verified_date>" + rs.getString("VERIFIED_DATE")+ "</verified_date>";
								ps1 = con.prepareStatement("select  amount from FAS_TPA_TRANSACTION where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=? and ACCOUNT_HEAD_CODE in (620101,900301) and sl_no=1");
								ps1.setInt(1, cmbAcc_UnitCode);
								ps1.setInt(2, cmbOffice_code);
								ps1.setInt(3, cashYear);
								ps1.setInt(4, cashMonth);
								ps1.setInt(5, voucherNo);
								rs1=ps1.executeQuery();
								rs1.next();
								xml = xml + "<amount>" +df.format(rs1.getBigDecimal("amount"))+ "</amount>";

								y++;
							}
							ps.close();
							rs.close();
							
						}else if(rs2.getInt("total")==0)
						{
							yy=0;
						}
							
						
						
					}
					/*xml = xml + "<unitid>" +cmbAcc_UnitCode+ "</unitid>";
					xml = xml + "<officeid>" + cmbOffice_code+ "</officeid>";
					xml = xml + "<cashyear>" + cashYear+ "</cashyear>";
					xml = xml + "<cashmonth>" +cashMonth+ "</cashmonth>";*/

					
					System.out.println("Y"+y);
					System.out.println("YY"+yy);
					/*if (y != 0 && z!=0) {
						xml = xml + "<flag>success</flag>";
					} else if(y!=0 && z==0)
					{
						xml = xml + "<flag>Credit</flag>";
					}else if(y==0 && z!=0)
					{
						xml = xml + "<flag>Debit</flag>";
					}else
						xml = xml + "<flag>failure</flag>";*/
					
					if(yy>0 && y != 0 )
					{
						xml = xml + "<flag>success</flag>";
					}else if(xx>0 && yy>0)
					{
						xml = xml + "<flag>successotherswithcreditdebit</flag>";
					}else if(xx>0 && yy==0)
					{
						xml = xml + "<flag>successothers</flag>";
					}else if( yy>0){
						xml = xml + "<flag>onlyonecr</flag>";
					}else{
						xml = xml + "<flag>failure</flag>";
					}
					

					//ps.close();
					//rs.close();
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("catch..HERE.in load supplier." + e);
					
					xml = xml + "<flag>failure</flag>";
				}
				
				
				}
				else if(type.equals("TPAOD"))
				{
					try {
						
						
						ps = con.prepareStatement("select VOUCHER_NO,to_char(VOUCHER_DATE,'dd/MM/yyyy') as VOUCHER_DATE,REASON_FOR_TRANSFER,PARTICULARS,VERIFY,to_char(VERIFIED_DATE,'dd/MM/yyyy')as VERIFIED_DATE from FAS_TPA_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and REASON_FOR_TRANSFER='Others' and STATUS='L' and VERIFY='Y' and (AUDIT_VERIFY is null OR AUDIT_VERIFY='N')");
						ps.setInt(1, cmbAcc_UnitCode);
						ps.setInt(2, cmbOffice_code);
						ps.setInt(3, cashYear);
						ps.setInt(4, cashMonth);
						
						rs = ps.executeQuery();

						xml = xml + "<unitid>" +cmbAcc_UnitCode+ "</unitid>";
						xml = xml + "<officeid>" + cmbOffice_code+ "</officeid>";
						xml = xml + "<cashyear>" + cashYear+ "</cashyear>";
						xml = xml + "<cashmonth>" +cashMonth+ "</cashmonth>";

						while (rs.next()) {
							int voucherNo=rs.getInt("VOUCHER_NO");
							xml = xml + "<vno>" +voucherNo + "</vno>";
							xml = xml + "<vdate>" + rs.getString("VOUCHER_DATE")+ "</vdate>";
							xml = xml + "<reason>" + rs.getString("REASON_FOR_TRANSFER")+ "</reason>";
							xml = xml + "<particular>" + rs.getString("PARTICULARS")+ "</particular>";
							xml = xml + "<verify>" + rs.getString("VERIFY")+ "</verify>";
							xml = xml + "<verified_date>" + rs.getString("VERIFIED_DATE")+ "</verified_date>";
							ps1 = con.prepareStatement("select  amount from FAS_TPA_TRANSACTION where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=? and ACCOUNT_HEAD_CODE in (620101,900301) and sl_no=1");
							ps1.setInt(1, cmbAcc_UnitCode);
							ps1.setInt(2, cmbOffice_code);
							ps1.setInt(3, cashYear);
							ps1.setInt(4, cashMonth);
							ps1.setInt(5, voucherNo);
							rs1=ps1.executeQuery();
							rs1.next();
							xml = xml + "<amount>" +df.format(rs1.getBigDecimal("amount"))+ "</amount>";

							xx++;
						}
						ps.close();
						rs.close();
						
						System.out.println("be4444444444");
						//kjlhkjhkj
						
						ps2 = con.prepareStatement("select count(*) as total from FAS_TPA_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and REASON_FOR_TRANSFER!='Others' and STATUS='L' and VERIFY='Y' and (AUDIT_VERIFY is null OR AUDIT_VERIFY='N')");
						
						System.out.println("select count(*) as total from FAS_TPA_MASTER where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and CASHBOOK_YEAR="+cashYear+" and CASHBOOK_MONTH="+cashMonth+" and REASON_FOR_TRANSFER!='Others' and STATUS='L' and VERIFY='Y' and (AUDIT_VERIFY is null OR AUDIT_VERIFY='N')");
						ps2.setInt(1, cmbAcc_UnitCode);
						ps2.setInt(2, cmbOffice_code);
						ps2.setInt(3, cashYear);
						ps2.setInt(4, cashMonth);
						

						rs2 = ps2.executeQuery();
						if(rs2.next())
						{
							yy++;
							System.out.println("TOTAL=1=>"+rs2.getInt("total"));

							if(rs2.getInt("total")>0)
							{
															
								ps = con.prepareStatement("select VOUCHER_NO,to_char(VOUCHER_DATE,'dd/MM/yyyy') as VOUCHER_DATE,REASON_FOR_TRANSFER,PARTICULARS,VERIFY,to_char(VERIFIED_DATE,'dd/MM/yyyy')as VERIFIED_DATE from FAS_TPA_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and TPA_TYPE='TPAOD' and REASON_FOR_TRANSFER!='Others' and STATUS='L' and VERIFY='Y' and (AUDIT_VERIFY is null OR AUDIT_VERIFY='N')");
								ps.setInt(1, cmbAcc_UnitCode);
								ps.setInt(2, cmbOffice_code);
								ps.setInt(3, cashYear);
								ps.setInt(4, cashMonth);
								

								rs = ps.executeQuery();
								while (rs.next()) {
									int voucherNo=rs.getInt("VOUCHER_NO");
									xml = xml + "<vno>" +voucherNo + "</vno>";
									xml = xml + "<vdate>" + rs.getString("VOUCHER_DATE")+ "</vdate>";
									xml = xml + "<reason>" + rs.getString("REASON_FOR_TRANSFER")+ "</reason>";
									xml = xml + "<particular>" + rs.getString("PARTICULARS")+ "</particular>";
									xml = xml + "<verify>" + rs.getString("VERIFY")+ "</verify>";
									xml = xml + "<verified_date>" + rs.getString("VERIFIED_DATE")+ "</verified_date>";
									ps1 = con.prepareStatement("select  amount from FAS_TPA_TRANSACTION where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=? and ACCOUNT_HEAD_CODE in (620101,900301) and sl_no=1");
									ps1.setInt(1, cmbAcc_UnitCode);
									ps1.setInt(2, cmbOffice_code);
									ps1.setInt(3, cashYear);
									ps1.setInt(4, cashMonth);
									ps1.setInt(5, voucherNo);
									rs1=ps1.executeQuery();
									rs1.next();
									xml = xml + "<amount>" +df.format(rs1.getBigDecimal("amount"))+ "</amount>";

									z++;
								}
								
							}else if(rs2.getInt("total")==0)
							{
								yy=0;
							}
								
							
							
						}
						/*xml = xml + "<unitid>" +cmbAcc_UnitCode+ "</unitid>";
						xml = xml + "<officeid>" + cmbOffice_code+ "</officeid>";
						xml = xml + "<cashyear>" + cashYear+ "</cashyear>";
						xml = xml + "<cashmonth>" +cashMonth+ "</cashmonth>";*/

						
						System.out.println("z"+z);
						System.out.println("YY"+yy);
						/*if (y != 0 && z!=0) {
							xml = xml + "<flag>success</flag>";
						} else if(y!=0 && z==0)
						{
							xml = xml + "<flag>Credit</flag>";
						}else if(y==0 && z!=0)
						{
							xml = xml + "<flag>Debit</flag>";
						}else
							xml = xml + "<flag>failure</flag>";*/
						
						if(yy>0 && z!=0)
						{
							xml = xml + "<flag>success</flag>";
						}else if(xx>0 && yy>0)
						{
							xml = xml + "<flag>successotherswithcreditdebit</flag>";
						}else if(xx>0 && yy==0)
						{
							xml = xml + "<flag>successothers</flag>";
						}else if( yy>0){
							xml = xml + "<flag>onlyonedr</flag>";
						}else{
							xml = xml + "<flag>failure</flag>";
						}
						

						//ps.close();
						//rs.close();
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("catch..HERE.in load supplier." + e);
						
						xml = xml + "<flag>failure</flag>";
					}
					
				}
				else if(type.equals("All"))
				{
					try {
						
						
						ps = con.prepareStatement("select VOUCHER_NO,to_char(VOUCHER_DATE,'dd/MM/yyyy') as VOUCHER_DATE,REASON_FOR_TRANSFER,PARTICULARS,VERIFY,to_char(VERIFIED_DATE,'dd/MM/yyyy')as VERIFIED_DATE from FAS_TPA_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and REASON_FOR_TRANSFER='Others' and STATUS='L' and VERIFY='Y' and (AUDIT_VERIFY is null OR AUDIT_VERIFY='N')");
						ps.setInt(1, cmbAcc_UnitCode);
						ps.setInt(2, cmbOffice_code);
						ps.setInt(3, cashYear);
						ps.setInt(4, cashMonth);
						
						rs = ps.executeQuery();

						xml = xml + "<unitid>" +cmbAcc_UnitCode+ "</unitid>";
						xml = xml + "<officeid>" + cmbOffice_code+ "</officeid>";
						xml = xml + "<cashyear>" + cashYear+ "</cashyear>";
						xml = xml + "<cashmonth>" +cashMonth+ "</cashmonth>";

						while (rs.next()) {
							int voucherNo=rs.getInt("VOUCHER_NO");
							xml = xml + "<vno>" +voucherNo + "</vno>";
							xml = xml + "<vdate>" + rs.getString("VOUCHER_DATE")+ "</vdate>";
							xml = xml + "<reason>" + rs.getString("REASON_FOR_TRANSFER")+ "</reason>";
							xml = xml + "<particular>" + rs.getString("PARTICULARS")+ "</particular>";
							xml = xml + "<verify>" + rs.getString("VERIFY")+ "</verify>";
							xml = xml + "<verified_date>" + rs.getString("VERIFIED_DATE")+ "</verified_date>";
							ps1 = con.prepareStatement("select  amount from FAS_TPA_TRANSACTION where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=? and ACCOUNT_HEAD_CODE in (620101,900301) and sl_no=1");
							ps1.setInt(1, cmbAcc_UnitCode);
							ps1.setInt(2, cmbOffice_code);
							ps1.setInt(3, cashYear);
							ps1.setInt(4, cashMonth);
							ps1.setInt(5, voucherNo);
							rs1=ps1.executeQuery();
							rs1.next();
							xml = xml + "<amount>" +df.format(rs1.getBigDecimal("amount"))+ "</amount>";

							xx++;
						}
						ps.close();
						rs.close();
						
						System.out.println("be4444444444");
						//kjlhkjhkj
						
						ps2 = con.prepareStatement("select count(*) as total from FAS_TPA_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and REASON_FOR_TRANSFER!='Others' and STATUS='L' and VERIFY='Y'  and (AUDIT_VERIFY is null OR AUDIT_VERIFY='N')");
						
						System.out.println("select count(*) as total from FAS_TPA_MASTER where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and CASHBOOK_YEAR="+cashYear+" and CASHBOOK_MONTH="+cashMonth+" and REASON_FOR_TRANSFER!='Others' and STATUS='L' and VERIFY='Y' and (AUDIT_VERIFY is null OR AUDIT_VERIFY='N')");
						ps2.setInt(1, cmbAcc_UnitCode);
						ps2.setInt(2, cmbOffice_code);
						ps2.setInt(3, cashYear);
						ps2.setInt(4, cashMonth);
						

						rs2 = ps2.executeQuery();
						if(rs2.next())
						{
							yy++;
							System.out.println("TOTAL=1=>"+rs2.getInt("total"));

							if(rs2.getInt("total")>0)
							{
								ps = con.prepareStatement("select VOUCHER_NO,to_char(VOUCHER_DATE,'dd/MM/yyyy') as VOUCHER_DATE,REASON_FOR_TRANSFER,PARTICULARS,VERIFY,to_char(VERIFIED_DATE,'dd/MM/yyyy')as VERIFIED_DATE from FAS_TPA_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and TPA_TYPE='TPAOC' and REASON_FOR_TRANSFER!='Others' and STATUS='L' and VERIFY='Y' and (AUDIT_VERIFY is null OR AUDIT_VERIFY='N')");
								ps.setInt(1, cmbAcc_UnitCode);
								ps.setInt(2, cmbOffice_code);
								ps.setInt(3, cashYear);
								ps.setInt(4, cashMonth);
								rs = ps.executeQuery();
								while (rs.next()) {
									int voucherNo=rs.getInt("VOUCHER_NO");
									xml = xml + "<vno>" +voucherNo + "</vno>";
									xml = xml + "<vdate>" + rs.getString("VOUCHER_DATE")+ "</vdate>";
									xml = xml + "<reason>" + rs.getString("REASON_FOR_TRANSFER")+ "</reason>";
									xml = xml + "<particular>" + rs.getString("PARTICULARS")+ "</particular>";
									xml = xml + "<verify>" + rs.getString("VERIFY")+ "</verify>";
									xml = xml + "<verified_date>" + rs.getString("VERIFIED_DATE")+ "</verified_date>";
									ps1 = con.prepareStatement("select  amount from FAS_TPA_TRANSACTION where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=? and ACCOUNT_HEAD_CODE in (620101,900301) and sl_no=1");
									ps1.setInt(1, cmbAcc_UnitCode);
									ps1.setInt(2, cmbOffice_code);
									ps1.setInt(3, cashYear);
									ps1.setInt(4, cashMonth);
									ps1.setInt(5, voucherNo);
									rs1=ps1.executeQuery();
									rs1.next();
									xml = xml + "<amount>" +df.format(rs1.getBigDecimal("amount"))+ "</amount>";

									y++;
								}
								ps.close();
								rs.close();
								
								ps = con.prepareStatement("select VOUCHER_NO,to_char(VOUCHER_DATE,'dd/MM/yyyy') as VOUCHER_DATE,REASON_FOR_TRANSFER,PARTICULARS,VERIFY,to_char(VERIFIED_DATE,'dd/MM/yyyy')as VERIFIED_DATE from FAS_TPA_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and TPA_TYPE='TPAOD' and REASON_FOR_TRANSFER!='Others' and STATUS='L' and VERIFY='Y' and (AUDIT_VERIFY is null OR AUDIT_VERIFY='N')");
								ps.setInt(1, cmbAcc_UnitCode);
								ps.setInt(2, cmbOffice_code);
								ps.setInt(3, cashYear);
								ps.setInt(4, cashMonth);
								

								rs = ps.executeQuery();
								while (rs.next()) {
									int voucherNo=rs.getInt("VOUCHER_NO");
									xml = xml + "<vno>" +voucherNo + "</vno>";
									xml = xml + "<vdate>" + rs.getString("VOUCHER_DATE")+ "</vdate>";
									xml = xml + "<reason>" + rs.getString("REASON_FOR_TRANSFER")+ "</reason>";
									xml = xml + "<particular>" + rs.getString("PARTICULARS")+ "</particular>";
									xml = xml + "<verify>" + rs.getString("VERIFY")+ "</verify>";
									xml = xml + "<verified_date>" + rs.getString("VERIFIED_DATE")+ "</verified_date>";
									ps1 = con.prepareStatement("select  amount from FAS_TPA_TRANSACTION where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=? and ACCOUNT_HEAD_CODE in (620101,900301) and sl_no=1");
									ps1.setInt(1, cmbAcc_UnitCode);
									ps1.setInt(2, cmbOffice_code);
									ps1.setInt(3, cashYear);
									ps1.setInt(4, cashMonth);
									ps1.setInt(5, voucherNo);
									rs1=ps1.executeQuery();
									rs1.next();
									xml = xml + "<amount>" +df.format(rs1.getBigDecimal("amount"))+ "</amount>";

									z++;
								}
								
							}else if(rs2.getInt("total")==0)
							{
								yy=0;
							}
								
							
							
						}
						/*xml = xml + "<unitid>" +cmbAcc_UnitCode+ "</unitid>";
						xml = xml + "<officeid>" + cmbOffice_code+ "</officeid>";
						xml = xml + "<cashyear>" + cashYear+ "</cashyear>";
						xml = xml + "<cashmonth>" +cashMonth+ "</cashmonth>";*/

						
						System.out.println("Y"+y);
						System.out.println("z"+z);
						System.out.println("YY"+yy);
						/*if (y != 0 && z!=0) {
							xml = xml + "<flag>success</flag>";
						} else if(y!=0 && z==0)
						{
							xml = xml + "<flag>Credit</flag>";
						}else if(y==0 && z!=0)
						{
							xml = xml + "<flag>Debit</flag>";
						}else
							xml = xml + "<flag>failure</flag>";*/
						
						if(yy>0)
						{
							xml = xml + "<flag>success</flag>";
						}else if(xx>0 && yy>0)
						{
							xml = xml + "<flag>successotherswithcreditdebit</flag>";
						}else if(xx>0 && yy==0)
						{
							xml = xml + "<flag>successothers</flag>";
						}
						else{
							xml = xml + "<flag>failure</flag>";
						}
						

						//ps.close();
						//rs.close();
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("catch..HERE.in load supplier." + e);
						
						xml = xml + "<flag>failure</flag>";
					}
					
				}

				xml = xml + "</response>";
				System.out.println(xml);
				out.println(xml);

			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);


		/* Variables Declaration */ 
		Connection con = null;      
		CallableStatement cs= null;
		PreparedStatement ps=null,ps1=null;

		/* Session Checking */
		HttpSession session = request.getSession(false);       


		/* Database Connection */
		try {
			ResourceBundle rs1 = ResourceBundle.getBundle("Servlets.Security.servlets.Config");
			String ConnectionString = "";
			String strDriver = rs1.getString("Config.DATA_BASE_DRIVER");
			String strdsn = rs1.getString("Config.DSN");
			String strhostname = rs1.getString("Config.HOST_NAME");
			String strportno = rs1.getString("Config.PORT_NUMBER");
			String strsid = rs1.getString("Config.SID");
			String strdbusername = rs1.getString("Config.USER_NAME");
			String strdbpassword = rs1.getString("Config.PASSWORD");
			ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection			Class.forName(strDriver.trim());
			con = DriverManager.getConnection(ConnectionString, strdbusername.trim(),strdbpassword.trim());
		} catch (Exception e) {
			System.out.println("Exception in opening connection :" + e);
			sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

		}


		/* Variables Declaration */ 


		int unitId=0,officeId=0;
		int cashbookYear=0;
		int cashbookMonth=0;







		String chckparameter_Voucher_no[]=null;
		String vou_No[]=null;  
		String advice_No[] =null;

		String unit_Id[]=null; // ( Head Office Account Number plus its corresponding Bank and Branch ID //

				Calendar c;

				int err_code=0;


				/* Get HO Accounting Unit ID */
				unitId=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
				System.out.println(unitId);


				/* Get HO Office ID */
				officeId=Integer.parseInt(request.getParameter("cmbOffice_code"));
				System.out.println(officeId);





				/* Get User ID */ 
				String update_user=(String)session.getAttribute("UserId");



				try{cashbookYear=Integer.parseInt(request.getParameter("txtCB_Year"));}
				catch(NumberFormatException e){System.out.println("exception"+e );}
				System.out.println("cashbookYear "+cashbookYear);

				try{cashbookMonth=Integer.parseInt(request.getParameter("txtCB_Month"));}
				catch(NumberFormatException e){System.out.println("exception"+e );}
				System.out.println("cashbookMonth "+cashbookMonth);

				String verifyDate=request.getParameter("txtCreate_Date");

				//----------------------------------------------------------------------------------------------//
				/* Get voucher Number from Selected Check Box */         


				/* Get Office Accounting Unit ID */          
				try {
					vou_No = request.getParameterValues("chckparameter");  
				}
				catch (Exception e) {
					System.out.println("Error getting Accounting Unit ID "+e);
				}



				/* Get Voucher Number */         


				try
				{
					//  con.clearWarnings();
					// con.setAutoCommit(false);


					/** Initially set the status as false */

					int k=0;


					/** Iterate selected Records and make status true if it matches */
					for(k=0;k<vou_No.length;k++)
					{
						System.out.print("Voucher No"+vou_No[k]);
						ps=con.prepareStatement("update FAS_TPA_MASTER set AUDIT_VERIFY='Y' ,AUDIT_VERIFIED_BY=?,AUDIT_VERIFIED_DATE=to_date(?,'dd/MM/yyyy') where CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=? and ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=?");

						ps.setString(1, update_user);
						ps.setString(2, verifyDate);
						ps.setInt(3, cashbookYear);
						ps.setInt(4, cashbookMonth);
						ps.setInt(5, Integer.parseInt(vou_No[k]));
						ps.setInt(6, unitId);
						ps.setInt(7, officeId); 
						ps.executeUpdate();


					} 	
					// con.setAutoCommit(true);

					sendMessage(response,"Audit Verification Completed Successfully ","ok");
				}
				catch(Exception e) 
				{
					e.printStackTrace();
					System.out.println("the exception in update is"+e.getMessage());    
					try{con.rollback();}catch(SQLException sqle){System.out.println("Excep"+sqle);}           
				}         
				finally
				{
					try{con.setAutoCommit(true);}
					catch(SQLException sqle)
					{System.out.println("Excep"+sqle);}
				}    
	}
	private void sendMessage(HttpServletResponse response,String msg,String bType)
	{
		try
		{
			String url="org/Library/jsps/MessengerOkBack.jsp?message=" + msg + "&button=" + bType;
			response.sendRedirect(url);
		}
		catch(Exception e)
		{
			System.out.println("error in messenger"+e);
		}
	}

}
