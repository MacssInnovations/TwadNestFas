package Servlets.FAS.FAS1.TPA.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class TPA_Acceptance_List
 */
public class TPA_Acceptance_List extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";     
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TPA_Acceptance_List() {
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
			
			// String xml="";
			int cmbAcc_UnitCode = 0, cmbOffice_code = 0;
			int cmbSL_type = 0;
			int addtional_field_value = 0;
			int y = 0;
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
			
			if(option.equalsIgnoreCase("yearmonth")){
				
			int cashMonth = Integer.parseInt(request.getParameter("cashmonth"));
			int cashYear = Integer.parseInt(request.getParameter("cashyear"));
			String status=request.getParameter("status");
			String type=request.getParameter("proformatype");
			String part1="";
			
			System.out.println("type===>"+type);
			
			//pending for acceptance
			if(type.equals("TPAAC1") ||type.equals("TPAAD1"))
			{
				if(type.equals("TPAAC1"))
				{
					type="TPAOC";
					part1="and ACCEPTING_SLNO is null";
				}
				else
				{
					type="TPAOD";
					part1="and ACCEPTING_SLNO is null";	
				}
			
			
				try {
					
					String ss="select VOUCHER_NO,to_char(VOUCHER_DATE,'dd/MM/yyyy') as VOUCHER_DATE," +
							"t.Accounting_Unit_Id,(select accounting_unit_name from fas_mst_acct_units u where u.accounting_unit_id=t.Accounting_Unit_Id )as orgUnitName," +
							"Cashbook_Year,Cashbook_Month,REASON_FOR_TRANSFER,PARTICULARS from FAS_TPA_MASTER t where TRF_ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and " +
							"CASHBOOK_YEAR="+cashYear+" and CASHBOOK_MONTH="+cashMonth+" and TPA_TYPE='"+type+"' and STATUS='"+status+"' "+part1;
					System.out.println("ss:::::"+ss);
					
					ps = con.prepareStatement(ss);
	
					rs = ps.executeQuery();
				
					xml = xml + "<unitid>" +cmbAcc_UnitCode+ "</unitid>";
					xml = xml + "<officeid>" + cmbOffice_code+ "</officeid>";
					xml = xml + "<cashyear>" + cashYear+ "</cashyear>";
					xml = xml + "<cashmonth>" +cashMonth+ "</cashmonth>";
					
					while (rs.next()) {
						
						int voucherNo=rs.getInt("VOUCHER_NO");
						xml = xml + "<vno>" +voucherNo + "</vno>";
						xml = xml + "<oUnitname>" +rs.getString("orgUnitName") + "</oUnitname>";
						xml = xml + "<vdate>" + rs.getString("VOUCHER_DATE")+ "</vdate>";
						xml = xml + "<reason>" + rs.getString("REASON_FOR_TRANSFER")+ "</reason>";
						xml = xml + "<particular>" + rs.getString("PARTICULARS")+ "</particular>";
						ps1 = con.prepareStatement("select amount from FAS_TPA_TRANSACTION where ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=? and SL_NO=1");
						ps1.setInt(1, rs.getInt("Accounting_Unit_Id"));
						ps1.setInt(2, rs.getInt("Cashbook_Year"));
						ps1.setInt(3, rs.getInt("Cashbook_Month"));
						ps1.setInt(4, voucherNo);
						rs1=ps1.executeQuery();
						rs1.next();
						xml = xml + "<amount>" +df.format(rs1.getBigDecimal("amount"))+ "</amount>";
								
						y++;
					}
					if (y != 0) {
						xml = xml + "<flag>success</flag>";
					} else
						xml = xml + "<flag>failure</flag>";
	
					ps.close();
					rs.close();
				} catch (Exception e) {
					System.out.println("catch..HERE.in load supplier." + e);
					xml = xml + "<flag>failure</flag>";
				}
			}
			else if(type.equals("TPAAC2") ||type.equals("TPAAD2"))
			{
				if(type.equals("TPAAC2"))
				{
					type="TPAOC";
					part1="and ACCEPTING_SLNO is not null";
				}
				else
				{
					type="TPAOD";
					part1="and ACCEPTING_SLNO is not null";	
				}
				try {
					
					String ss="select VOUCHER_NO,to_char(VOUCHER_DATE,'dd/MM/yyyy') as VOUCHER_DATE," +
							"t.Accounting_Unit_Id,(select accounting_unit_name from fas_mst_acct_units u where u.accounting_unit_id=t.Accounting_Unit_Id )as orgUnitName," +
							" Accepting_Slno,to_char(ACCEPTING_DATE,'dd/MM/yyyy') as ACCEPTING_DATE,Cashbook_Year,Cashbook_Month,REASON_FOR_TRANSFER,PARTICULARS from FAS_TPA_MASTER t where TRF_ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and " +
							"CASHBOOK_YEAR="+cashYear+" and CASHBOOK_MONTH="+cashMonth+" and TPA_TYPE='"+type+"' and STATUS='"+status+"' "+part1;
					System.out.println("ss:::::"+ss);
					
					ps = con.prepareStatement(ss);

					rs = ps.executeQuery();
				
					xml = xml + "<unitid>" +cmbAcc_UnitCode+ "</unitid>";
					xml = xml + "<officeid>" + cmbOffice_code+ "</officeid>";
					xml = xml + "<cashyear>" + cashYear+ "</cashyear>";
					xml = xml + "<cashmonth>" +cashMonth+ "</cashmonth>";
					
					while (rs.next()) {
						
						int voucherNo=rs.getInt("VOUCHER_NO");
						xml = xml + "<vno>" +voucherNo + "</vno>";
						xml = xml + "<oUnitname>" +rs.getString("orgUnitName") + "</oUnitname>";
						xml = xml + "<vdate>" + rs.getString("VOUCHER_DATE")+ "</vdate>";
						
						xml = xml + "<accSlNo>" +rs.getInt("Accepting_Slno") + "</accSlNo>";
						xml = xml + "<accDate>" + rs.getString("ACCEPTING_DATE")+ "</accDate>";
						xml = xml + "<reason>" + rs.getString("REASON_FOR_TRANSFER")+ "</reason>";
						xml = xml + "<particular>" + rs.getString("PARTICULARS")+ "</particular>";
						ps1 = con.prepareStatement("select amount from FAS_TPA_TRANSACTION where ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=? and SL_NO=1");
						ps1.setInt(1, rs.getInt("Accounting_Unit_Id"));
						ps1.setInt(2, rs.getInt("Cashbook_Year"));
						ps1.setInt(3, rs.getInt("Cashbook_Month"));
						ps1.setInt(4, voucherNo);
						rs1=ps1.executeQuery();
						rs1.next();
						xml = xml + "<amount>" +df.format(rs1.getBigDecimal("amount"))+ "</amount>";
								
						y++;
					}
					if (y != 0) {
						xml = xml + "<flag>success</flag>";
					} else
						xml = xml + "<flag>failure</flag>";

					ps.close();
					rs.close();
				} catch (Exception e) {
					System.out.println("catch..HERE.in load supplier." + e);
					xml = xml + "<flag>failure</flag>";
				}
			}	
			else if(type.equals("TPAAC3") ||type.equals("TPAAD3"))
			{
				if(type.equals("TPAAC3"))
				{
					type="TPAOC";
					part1="and ACCEPTING_SLNO is not null";
				}
				else
				{
					type="TPAOD";
					part1="and ACCEPTING_SLNO is not null";	
				}
				try {
					
					String ss="select VOUCHER_NO,to_char(VOUCHER_DATE,'dd/MM/yyyy') as VOUCHER_DATE," +
							"t.Accounting_Unit_Id,(select accounting_unit_name from fas_mst_acct_units u where u.accounting_unit_id=t.Accounting_Unit_Id )as orgUnitName," +
							" Accepting_Slno,to_char(ACCEPTING_DATE,'dd/MM/yyyy') as ACCEPTING_DATE,Cashbook_Year,Cashbook_Month,REASON_FOR_TRANSFER,PARTICULARS from FAS_TPA_MASTER t where TRF_ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and " +
							"extract(year from ACCEPTING_DATE)="+cashYear+" and extract(month from ACCEPTING_DATE)="+cashMonth+" and TPA_TYPE='"+type+"' and STATUS='"+status+"' "+part1;
					System.out.println("ss:::::"+ss);
					
					ps = con.prepareStatement(ss);

					rs = ps.executeQuery();
				
					xml = xml + "<unitid>" +cmbAcc_UnitCode+ "</unitid>";
					xml = xml + "<officeid>" + cmbOffice_code+ "</officeid>";
					xml = xml + "<cashyear>" + cashYear+ "</cashyear>";
					xml = xml + "<cashmonth>" +cashMonth+ "</cashmonth>";
					
					while (rs.next()) {
						
						int voucherNo=rs.getInt("VOUCHER_NO");
						xml = xml + "<vno>" +voucherNo + "</vno>";
						xml = xml + "<oUnitname>" +rs.getString("orgUnitName") + "</oUnitname>";
						xml = xml + "<vdate>" + rs.getString("VOUCHER_DATE")+ "</vdate>";
						
						xml = xml + "<accSlNo>" +rs.getInt("Accepting_Slno") + "</accSlNo>";
						xml = xml + "<accDate>" + rs.getString("ACCEPTING_DATE")+ "</accDate>";
						xml = xml + "<reason>" + rs.getString("REASON_FOR_TRANSFER")+ "</reason>";
						xml = xml + "<particular>" + rs.getString("PARTICULARS")+ "</particular>";
						ps1 = con.prepareStatement("select amount from FAS_TPA_TRANSACTION where ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=? and SL_NO=1");
						ps1.setInt(1, rs.getInt("Accounting_Unit_Id"));
						ps1.setInt(2, rs.getInt("Cashbook_Year"));
						ps1.setInt(3, rs.getInt("Cashbook_Month"));
						ps1.setInt(4, voucherNo);
						rs1=ps1.executeQuery();
						rs1.next();
						xml = xml + "<amount>" +df.format(rs1.getBigDecimal("amount"))+ "</amount>";
								
						y++;
					}
					if (y != 0) {
						xml = xml + "<flag>success</flag>";
					} else
						xml = xml + "<flag>failure</flag>";

					ps.close();
					rs.close();
				} catch (Exception e) {
					System.out.println("catch..HERE.in load supplier." + e);
					xml = xml + "<flag>failure</flag>";
				}
			}
			xml = xml + "</response>";
			 System.out.println("xml::::"+xml);
			out.println(xml);
		
		}else if(option.equalsIgnoreCase("date"))
				{
			String fromDate = request.getParameter("fromdate");
			String toDate = request.getParameter("todate");
			int cashMonth = Integer.parseInt(request.getParameter("cashmonth"));
			int cashYear = Integer.parseInt(request.getParameter("cashyear"));
			String status=request.getParameter("status");
			String type=request.getParameter("proformatype");
			try {
				ps = con.prepareStatement("select VOUCHER_NO,to_char(VOUCHER_DATE,'dd/MM/yyyy') as VOUCHER_DATE,REASON_FOR_TRANSFER,PARTICULARS from FAS_TPA_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and (VOUCHER_DATE between to_date(?,'dd/MM/yyyy') and to_date(?,'dd/MM/yyyy')) and TPA_TYPE=? and STATUS=?");
				ps.setInt(1, cmbAcc_UnitCode);
				ps.setInt(2, cmbOffice_code);
				ps.setInt(3, cashYear);
				ps.setInt(4, cashMonth);
				ps.setString(5, fromDate);
				ps.setString(6, toDate);
				ps.setString(7, type);
				ps.setString(8, status);
				
				rs = ps.executeQuery();
				
				xml = xml + "<unitid>" +cmbAcc_UnitCode+ "</unitid>";
				xml = xml + "<officeid>" + cmbOffice_code+ "</officeid>";
				xml = xml + "<cashyear>" + cashYear+ "</cashyear>";
				xml = xml + "<cashmonth>" +cashMonth+ "</cashmonth>";
				
				while (rs.next()) {
					int voucherNo=rs.getInt("VOUCHER_NO");
					xml = xml + "<vno>" +voucherNo + "</vno>";
					xml = xml + "<vdate>" + rs.getString("VOUCHER_DATE")+ "</vdate>";
				//	xml = xml + "<reason>" + rs.getString("REASON_FOR_TRANSFER")+ "</reason>";
					xml = xml + "<particular>" + rs.getString("PARTICULARS")+ "</particular>";
					ps1 = con.prepareStatement("select sum(AMOUNT) as amount from FAS_TPA_TRANSACTION where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=? and ACCOUNT_HEAD_CODE in (620101,900301)");
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
				if (y != 0) {
					xml = xml + "<flag>success</flag>";
				} else
					xml = xml + "<flag>failure</flag>";

				ps.close();
				rs.close();
			} catch (Exception e) {
				System.out.println("catch..HERE.in load supplier." + e);
				xml = xml + "<flag>failure</flag>";
			}
				
			xml = xml + "</response>";
			// System.out.println(xml);
			out.println(xml);
		}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
