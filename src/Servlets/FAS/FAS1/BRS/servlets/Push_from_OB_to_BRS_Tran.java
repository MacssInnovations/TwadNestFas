package Servlets.FAS.FAS1.BRS.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Push_from_OB_to_BRS_Tran
 */
public class Push_from_OB_to_BRS_Tran extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Push_from_OB_to_BRS_Tran() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();

		System.out.println("chk 1");
		Connection connection = null;
		Statement statement = null;
		ResultSet results = null;
		ResultSet results2;
		ResultSet rs = null,rss1=null,rss2=null;
		ResultSet rs2 = null,resq=null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		PreparedStatement ps4 = null;
		PreparedStatement pss = null,preup=null;
		PreparedStatement pss1 = null,prep=null,pss2=null;

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

		System.out.println("chk 2");

		String strCommand = "";
		String xml = "";

		HttpSession session = request.getSession(false);
		try {

			if (session == null) {
				System.out.println(request.getContextPath() + "/index.jsp");
				response.sendRedirect(request.getContextPath() + "/index.jsp");

			}
			System.out.println(session);

		} catch (Exception e) {
			System.out.println("Redirect Error :" + e);
		}
		String userid = (String) session.getAttribute("UserId");
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
			System.out.println(strCommand);
		} catch (Exception e) {
			e.printStackTrace();
		}

		long l = System.currentTimeMillis();
		Timestamp ts = new Timestamp(l);

		int cmbAcc_UnitCode = 0;
		int cmbOffice_code = 0;
		int txtCB_Year = 0;
		int txtCB_Month = 0;
		long cmbBankAccNo = 0;
		cmbAcc_UnitCode = Integer.parseInt(request
				.getParameter("cmbAcc_UnitCode"));
		cmbOffice_code = Integer.parseInt(request
				.getParameter("cmbOffice_code"));
		txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));
		txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
		cmbBankAccNo = Long.parseLong(request.getParameter("cmbBankAccNo"));
		String acctype=request.getParameter("acctype");
		int k = 0, k1 = 0, k2 = 0, k3 = 0, k4 = 0;
		if (strCommand.equalsIgnoreCase("do")) {
			xml = xml + "<response><command>do</command>";

			
			try {
				//connection.setAutoCommit(false);
				
				pss = connection.prepareStatement(" select distinct ACCOUNT_NO FROM FAS_BRS_OB_TRANSACTION WHERE ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? AND (Cashbook_Year          <"+txtCB_Year+" OR (Cashbook_Year           =? AND Cashbook_Month         <=?))");
				pss.setInt(1, cmbAcc_UnitCode);
				pss.setInt(2, cmbOffice_code);
				pss.setInt(3, txtCB_Year);
				pss.setInt(4, txtCB_Month);
				results2 =pss.executeQuery();
				if(results2.next())
				{
					//MODIFIED ON 22MAR2013 BY DHANA
					//once again check for FAS_BRS_OB_TRANSACTION is OB_PUSHED null or not.
					//if OB_PUSHED=null in FAS_BRS_OB_TRANSACTION but entry in found in 
					//FAS_BRS_TRANSACTION_NOENTRY ,then update OB_PUSHED=y in FAS_BRS_OB_TRANSACTION
					
					String nq="SELECT ACCOUNTING_UNIT_ID,to_char(REMITTANCE_DATE,'dd-mm-yyyy')as remdate,to_char(WITHDRAWAL_DATE,'dd-mm-yyyy')as withdate,VOUCHER_OR_CHALLAN_NO,CHEQUE_DD_NO,CR_AMOUNT,DR_AMOUNT,ACCOUNT_NO,OB_PUSHED FROM Fas_Brs_Ob_Transaction WHERE Accounting_Unit_Id    ="+cmbAcc_UnitCode+" AND Accounting_For_Office_Id="+cmbOffice_code+" AND (Cashbook_Year          <"+txtCB_Year+" OR (Cashbook_Year           ="+txtCB_Year+" AND Cashbook_Month         <="+txtCB_Month+")) AND Account_No              ="+cmbBankAccNo+" AND OB_PUSHED              IS NULL ORDER BY Cashbook_Year,Cashbook_Month";	
					System.out.println("nq:::"+nq);
					prep = connection.prepareStatement(nq);
					resq=prep.executeQuery();
					while(resq.next())
					{
						Float cramt=resq.getFloat("CR_AMOUNT");
						int vorchallanno=resq.getInt("VOUCHER_OR_CHALLAN_NO");
						String remdate=resq.getString("remdate");
					//	System.out.println("remdate::"+remdate);
						
						String withdate=resq.getString("withdate");
					//	System.out.println("withdate::"+withdate);
						Float dramt=resq.getFloat("DR_AMOUNT");
						int chequeno=resq.getInt("CHEQUE_DD_NO");
							if(acctype.equalsIgnoreCase("COL"))
							{
								
								pss1 = connection.prepareStatement(" select ACCOUNT_NO FROM FAS_BRS_TRANSACTION_NOENTRY WHERE ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and CASHBOOK_YEAR="+txtCB_Year+" and CASHBOOK_MONTH="+txtCB_Month+" and ACCOUNT_NO="+cmbBankAccNo+" AND REMITTANCE_DATE='"+remdate+"' AND VOUCHER_OR_CHALLAN_NO="+vorchallanno+" AND CR_AMOUNT="+cramt);
								rss1=pss1.executeQuery();
								if(rss1.next())
								{
									
									preup=connection.prepareStatement("update FAS_BRS_OB_TRANSACTION set OB_PUSHED=?,OB_PUSHED_DATE=? where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and CASHBOOK_YEAR="+txtCB_Year+" and CASHBOOK_MONTH="+txtCB_Month+" and ACCOUNT_NO="+cmbBankAccNo+" AND CR_AMOUNT="+cramt+" and VOUCHER_OR_CHALLAN_NO="+vorchallanno+" and REMITTANCE_DATE='"+remdate+"'");
									preup.setString(1,"Y");
									preup.setTimestamp(2, ts);
									int upd=preup.executeUpdate();
									
								}
							}
							else if(acctype.equalsIgnoreCase("OPR"))
							{
							//	System.out.println("operation");
								pss2 = connection.prepareStatement(" select ACCOUNT_NO FROM FAS_BRS_TRANSACTION_NOENTRY WHERE ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and CASHBOOK_YEAR="+txtCB_Year+" and CASHBOOK_MONTH="+txtCB_Month+" and ACCOUNT_NO="+cmbBankAccNo+" AND WITHDRAWAL_DATE='"+withdate+"' AND VOUCHER_OR_CHALLAN_NO="+vorchallanno+" AND DR_AMOUNT="+dramt+" and CHEQUE_DD_NO="+chequeno);
								rss2=pss2.executeQuery();
								if(rss2.next())
								{
									
									preup=connection.prepareStatement("update FAS_BRS_OB_TRANSACTION set OB_PUSHED=?,OB_PUSHED_DATE=?  WHERE ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and CASHBOOK_YEAR="+txtCB_Year+" and CASHBOOK_MONTH="+txtCB_Month+" and ACCOUNT_NO="+cmbBankAccNo+" AND WITHDRAWAL_DATE='"+withdate+"' AND VOUCHER_OR_CHALLAN_NO="+vorchallanno+" AND DR_AMOUNT="+dramt+" and CHEQUE_DD_NO="+chequeno);
									preup.setString(1,"Y");
									preup.setTimestamp(2, ts);
									int upd=preup.executeUpdate();
									
								}
							}
							//else if(acctype.equalsIgnoreCase("FDW"))
							
							
					}
				
					connection.setAutoCommit(false);
					//System.out.println("no entry starts:::::");
				ps = connection
						.prepareStatement("INSERT INTO FAS_BRS_TRANSACTION_NOENTRY   "
								+ "( ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,"
								+ "ENTRY_DATE,SL_NO,TWAD_OR_NON_TWAD,REMITTANCE_DATE,WITHDRAWAL_DATE,"
								+ "VOUCHER_OR_CHALLAN_NO,CHEQUE_DD_NO,PARTICULARS,CR_AMOUNT,DR_AMOUNT,"
								+ "ENTRY_FOUND_IN_PASSBOOK,PASSBOOK_DATE,AMOUNT_IN_PASSBOOK,DIFFERENCE,"
								+ "REASON_FOR_DIFFERENCE,FOLLOW_UP_ACTION_REQUIRED,CLEARED_BASED_ON_FOLLOWUP,"
								+ "TRANSACTION_TYPE,UPDATED_BY_USER_ID,UPDATED_DATE,ACCOUNT_NO,DOC_DATE,DOC_NO,"
								+ "DOC_TYPE,DETAILS,JOURNALIZED,ACTION_TAKEN  ) SELECT ACCOUNTING_UNIT_ID,"
								+ "ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,ENTRY_DATE,"
								+ "SL_NO,TWAD_OR_NON_TWAD,"
								+ "REMITTANCE_DATE,WITHDRAWAL_DATE,VOUCHER_OR_CHALLAN_NO,CHEQUE_DD_NO,"
								+ "PARTICULARS,CR_AMOUNT,DR_AMOUNT,ENTRY_FOUND_IN_PASSBOOK,PASSBOOK_DATE,"
								+ "AMOUNT_IN_PASSBOOK,DIFFERENCE,REASON_FOR_DIFFERENCE,FOLLOW_UP_ACTION_REQUIRED,"
								+ "CLEARED_BASED_ON_FOLLOWUP,TRANSACTION_TYPE,UPDATED_BY_USER_ID,UPDATED_DATE,"
								+ "ACCOUNT_NO,DOC_DATE,DOC_NO,DOC_TYPE,DETAILS,JOURNALIZED,ACTION_TAKEN  "
								+ "FROM FAS_BRS_OB_TRANSACTION WHERE ACCOUNTING_UNIT_ID    =? AND "
								+ "ACCOUNTING_FOR_OFFICE_ID=? And (Cashbook_Year           <? or " +
								"(Cashbook_Year="+txtCB_Year+" and Cashbook_Month<=?)) AND ACCOUNT_NO              =? and OB_PUSHED is null");
				ps.setInt(1, cmbAcc_UnitCode);
				ps.setInt(2, cmbOffice_code);
				ps.setInt(3, txtCB_Year);
				ps.setInt(4, txtCB_Month);
				ps.setLong(5, cmbBankAccNo);
				k = ps.executeUpdate();
				if(k>0)
				{
					System.out.println("k::::"+k);
					connection.setAutoCommit(true);
				}
				ps4 = connection.prepareStatement("delete from FAS_BRS_MASTER WHERE ACCOUNTING_UNIT_ID    =? "
						+ "AND ACCOUNTING_FOR_OFFICE_ID=? AND (Cashbook_Year          <"+txtCB_Year+" OR (Cashbook_Year           =? AND Cashbook_Month         <=?)) AND ACCOUNT_NO              =?   ");
				ps4.setInt(1, cmbAcc_UnitCode);
				ps4.setInt(2, cmbOffice_code);
				ps4.setInt(3, txtCB_Year);
				ps4.setInt(4, txtCB_Month);
				ps4.setLong(5, cmbBankAccNo);
				k4 = ps4.executeUpdate();
				if(k4>0)
				{
					System.out.println("k4::::"+k4);
				}
		System.out.println("delete from noemal master:");
				ps2 = connection
						.prepareStatement("INSERT INTO FAS_BRS_MASTER (ACCOUNTING_UNIT_ID,"
								+ "ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,ENTRY_DATE,"
								+ "ACCOUNT_NO,BANK_ID,BRANCH_ID,OPERATIONAL_MODE,PASSBOOK_BALANCE,"
								+ "UPDATED_BY_USER_ID,UPDATED_DATE) SELECT ACCOUNTING_UNIT_ID,"
								+ "ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,ENTRY_DATE,"
								+ "ACCOUNT_NO,BANK_ID,BRANCH_ID,OPERATIONAL_MODE,PASSBOOK_BALANCE,"
								+ "UPDATED_BY_USER_ID,UPDATED_DATE FROM FAS_BRS_OB_MASTER "
								+ "WHERE ACCOUNTING_UNIT_ID    =? AND ACCOUNTING_FOR_OFFICE_ID=? "
								+ " AND (Cashbook_Year          <"+txtCB_Year+" OR (Cashbook_Year           =? AND Cashbook_Month         <=?)) "
								+ "AND ACCOUNT_NO              =? ");
				ps2.setInt(1, cmbAcc_UnitCode);
				ps2.setInt(2, cmbOffice_code);
				ps2.setInt(3, txtCB_Year);
				ps2.setInt(4, txtCB_Month);
				ps2.setLong(5, cmbBankAccNo);
				k2 = ps2.executeUpdate();
				System.out.println("insert into master::::");
				
				ps3 = connection.prepareStatement("update FAS_BRS_OB_TRANSACTION set OB_PUSHED='Y',OB_PUSHED_DATE=? WHERE ACCOUNTING_UNIT_ID    =? "
								+ "AND ACCOUNTING_FOR_OFFICE_ID=? AND (Cashbook_Year          <"+txtCB_Year+" OR (Cashbook_Year           =? AND Cashbook_Month         <=?)) AND ACCOUNT_NO              =?   ");
				ps3.setTimestamp(1, ts);
				ps3.setInt(2, cmbAcc_UnitCode);
				ps3.setInt(3, cmbOffice_code);
				ps3.setInt(4, txtCB_Year);
				ps3.setInt(5, txtCB_Month);
				ps3.setLong(6, cmbBankAccNo);
				k3 = ps3.executeUpdate();

			//	if((k>0) && (k1>0) && (k2>0) && (k3>0))
				if((k>0) && (k2>0) && (k3>0))
				{
				xml = xml + "<flag>success</flag>";
				connection.commit();
				}else{
					connection.rollback();
					xml = xml + "<flag>fail</flag>";					
				}
				  
				}else{
					xml = xml + "<flag>NoData</flag>";	
				}
				
			} catch (Exception e) {
				try {
					connection.rollback();
				} catch (SQLException e1) {					
					e1.printStackTrace();
				}				
				e.printStackTrace();
				String sss = e.toString().substring(34, 51);											
				if(sss.equals("unique constraint"))
				{
					xml = xml + "<flag>Exist</flag>";
				}else{
					xml = xml + "<flag>fail</flag>";
				}
			}
		}
		else if (strCommand.equalsIgnoreCase("loadGrid"))
		{
			xml = xml + "<response><command>loadGrid</command>";
			int load=0;
			try{
			String sql1="SELECT ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,to_char(ENTRY_DATE,'dd-mm-yyyy') as ENTRY_DATE,SL_NO,TWAD_OR_NON_TWAD,to_char(REMITTANCE_DATE,'dd-mm-yyyy') as REMITTANCE_DATE, "+
						"to_char(WITHDRAWAL_DATE,'dd-mm-yyyy') as WITHDRAWAL_DATE,VOUCHER_OR_CHALLAN_NO,CHEQUE_DD_NO,PARTICULARS,CR_AMOUNT,DR_AMOUNT,ENTRY_FOUND_IN_PASSBOOK,to_char(PASSBOOK_DATE,'dd-mm-yyyy') as PASSBOOK_DATE, "+
				" AMOUNT_IN_PASSBOOK,DIFFERENCE,REASON_FOR_DIFFERENCE,FOLLOW_UP_ACTION_REQUIRED,CLEARED_BASED_ON_FOLLOWUP,TRANSACTION_TYPE,UPDATED_BY_USER_ID,UPDATED_DATE,ACCOUNT_NO, "+
				" 	  DOC_DATE,DOC_NO,DOC_TYPE,DETAILS,JOURNALIZED,ACTION_TAKEN "+
				" 	From Fas_Brs_Ob_Transaction "+
				" 	Where Accounting_Unit_Id    ="+cmbAcc_UnitCode+
				" 	And Accounting_For_Office_Id="+cmbOffice_code+
				" And (Cashbook_Year           <"+txtCB_Year+" or (Cashbook_Year="+txtCB_Year+" and Cashbook_Month<="+txtCB_Month+"))"+
				" 	And Account_No              ="+cmbBankAccNo+
				" 	AND OB_PUSHED              IS NULL order by Cashbook_Year,Cashbook_Month";
			System.out.println("sql1::::"+sql1);
			pss1 = connection.prepareStatement(sql1);
			
			rs =pss1.executeQuery();
				while(rs.next())
				{
					load++;
					xml = xml + "<year>"+rs.getInt("CASHBOOK_YEAR")+"</year>";
					xml = xml + "<month>"+rs.getInt("CASHBOOK_MONTH")+"</month>";
					xml = xml + "<entrydate>"+rs.getString("ENTRY_DATE")+"</entrydate>";
					xml = xml + "<twadtype>"+rs.getString("TWAD_OR_NON_TWAD")+"</twadtype>";
					xml = xml + "<remdate>"+rs.getString("REMITTANCE_DATE")+"</remdate>";
					xml = xml + "<withdrawldate>"+rs.getString("WITHDRAWAL_DATE")+"</withdrawldate>";
					xml = xml + "<vou_chall_no>"+rs.getString("VOUCHER_OR_CHALLAN_NO")+"</vou_chall_no>";
					xml = xml + "<chequeno>"+rs.getString("CHEQUE_DD_NO")+"</chequeno>";
					
					xml = xml + "<cramt>"+rs.getString("CR_AMOUNT")+"</cramt>";
					xml = xml + "<dramt>"+rs.getString("DR_AMOUNT")+"</dramt>";
					xml = xml + "<entrypass>"+rs.getString("ENTRY_FOUND_IN_PASSBOOK")+"</entrypass>";
					xml = xml + "<passdate>"+rs.getString("PASSBOOK_DATE")+"</passdate>";	
					
					xml = xml + "<amtinpass>"+rs.getString("AMOUNT_IN_PASSBOOK")+"</amtinpass>";
					xml = xml + "<difference>"+rs.getString("DIFFERENCE")+"</difference>";
				//	xml = xml + "<followup>"+rs.getInt("FOLLOW_UP_ACTION_REQUIRED")+"</followup>";
					xml = xml + "<docno>"+rs.getString("DOC_NO")+"</docno>";	
					
				}
				if(load>0)
				{
				xml = xml + "<flag>success</flag>";
				}
				else
				{
					xml = xml + "<flag>noRecord</flag>";
				}
			}
			catch(Exception ee)
			{
				System.out.println("ee::"+ee);
			}
		}
		
		xml = xml + "</response>";
		out.write(xml);
		System.out.println(xml);
	}

}
