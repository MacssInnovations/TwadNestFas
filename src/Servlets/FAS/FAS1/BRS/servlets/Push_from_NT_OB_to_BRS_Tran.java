package Servlets.FAS.FAS1.BRS.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
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
 * Servlet implementation class Push_from_NT_OB_to_BRS_Tran
 */
public class Push_from_NT_OB_to_BRS_Tran extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Push_from_NT_OB_to_BRS_Tran() {
        super();
      
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();

		System.out.println("chk 1");
		Connection connection = null;
		Statement statement = null;
		ResultSet results = null,resqa=null;
		ResultSet results2,ress=null;
		ResultSet rs = null,rss1=null,rss2=null;
		ResultSet rs2 = null,resq=null;
		PreparedStatement ps = null,prepa=null;
		PreparedStatement ps1 = null,pres=null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		PreparedStatement ps4 = null;
		PreparedStatement ps5 = null;
		PreparedStatement ps6 = null;
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
		String update_user = (String) session.getAttribute("UserId");
		long l = System.currentTimeMillis();
		Timestamp ts = new Timestamp(l);

		int cmbAcc_UnitCode = 0;
		int cmbOffice_code = 0;
		int txtCB_Year = 0;
		int txtCB_Month = 0;
		long cmbBankAccNo = 0;
		int bid=0,branchid=0;
		String oprmode="";
		int ntyear=0,ntmonth=0;
		
		cmbAcc_UnitCode = Integer.parseInt(request
				.getParameter("cmbAcc_UnitCode"));
		System.out.println("cmbAcc_UnitCode===>"+cmbAcc_UnitCode);
		cmbOffice_code = Integer.parseInt(request
				.getParameter("cmbOffice_code"));
		System.out.println("cmbOffice_code===>"+cmbOffice_code);
		txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));
		txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
		cmbBankAccNo = Long.parseLong(request.getParameter("cmbBankAccNo"));
		String acctype=request.getParameter("acctype");
		int k = 0, k1 = 0, k2 = 0, k3 = 0, k4 = 0,k5=0,k6=0;
		if (strCommand.equalsIgnoreCase("do")) {
			xml = xml + "<response><command>do</command>";

			
			try {
				connection.setAutoCommit(false);
				
				String nq="SELECT ACCOUNTING_UNIT_ID,cashbook_year,cashbook_month,to_char(REMITTANCE_DATE,'dd-mm-yyyy')as remdate,to_char(WITHDRAWAL_DATE,'dd-mm-yyyy')as withdate,VOUCHER_OR_CHALLAN_NO,CHEQUE_DD_NO,CR_AMOUNT,DR_AMOUNT,ACCOUNT_NO,OB_PUSHED FROM FAS_BRS_OB_TRANSACTION_NT " +
						"WHERE Accounting_Unit_Id    ="+cmbAcc_UnitCode+" AND Accounting_For_Office_Id="+cmbOffice_code+" AND (Cashbook_Year          <"+txtCB_Year+" OR (Cashbook_Year           ="+txtCB_Year+" AND Cashbook_Month         <="+txtCB_Month+")) AND " +
								"Account_No              ="+cmbBankAccNo+" AND OB_PUSHED              IS NULL ORDER BY Cashbook_Year,Cashbook_Month";
				prep = connection.prepareStatement(nq);
				resq=prep.executeQuery();
				while(resq.next())
				{
					//record exist in brs master table
					ntyear=resq.getInt("cashbook_year");
					ntmonth=resq.getInt("cashbook_month");
					
					String mas="SELECT * FROM FAS_BRS_MASTER WHERE ACCOUNTING_UNIT_ID    ="+cmbAcc_UnitCode+" AND ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" AND CASHBOOK_YEAR           ="+ntyear+" AND CASHBOOK_MONTH          ="+ntmonth+" AND ACCOUNT_NO="+cmbBankAccNo;
					prepa=connection.prepareStatement(mas);
					resqa=prepa.executeQuery();
					if(resqa.next())
					{
						ps = connection.prepareStatement("INSERT INTO FAS_BRS_TRANSACTION "
								+ "(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,"
								+ "ENTRY_DATE,SL_NO,TWAD_OR_NON_TWAD,REMITTANCE_DATE,WITHDRAWAL_DATE,"
								+ "VOUCHER_OR_CHALLAN_NO,CHEQUE_DD_NO,PARTICULARS,CR_AMOUNT,DR_AMOUNT,"
								+ "ENTRY_FOUND_IN_PASSBOOK,PASSBOOK_DATE,AMOUNT_IN_PASSBOOK,DIFFERENCE,"
								+ "FOLLOW_UP_ACTION_REQUIRED,CLEARED_BASED_ON_FOLLOWUP,"
								+ "TRANSACTION_TYPE,UPDATED_BY_USER_ID,UPDATED_DATE,ACCOUNT_NO,DOC_DATE,DOC_NO,"
								+ "DOC_TYPE,DETAILS,JOURNALIZED,ACTION_TAKEN) SELECT ACCOUNTING_UNIT_ID,"
								+ "ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,ENTRY_DATE,"
								+ "SL_NO,TWAD_OR_NON_TWAD,"
								+ "REMITTANCE_DATE,WITHDRAWAL_DATE,VOUCHER_OR_CHALLAN_NO,CHEQUE_DD_NO,"
								+ "PARTICULARS,CR_AMOUNT,DR_AMOUNT,ENTRY_FOUND_IN_PASSBOOK,PASSBOOK_DATE,"
								+ "AMOUNT_IN_PASSBOOK,DIFFERENCE,FOLLOW_UP_ACTION_REQUIRED,"
								+ "CLEARED_BASED_ON_FOLLOWUP,TRANSACTION_TYPE,UPDATED_BY_USER_ID,UPDATED_DATE,"
								+ "ACCOUNT_NO,DOC_DATE,DOC_NO,DOC_TYPE,DETAILS,JOURNALIZED,ACTION_TAKEN  "
								+ "FROM FAS_BRS_OB_TRANSACTION_NT WHERE ACCOUNTING_UNIT_ID    =? AND "
								+ "ACCOUNTING_FOR_OFFICE_ID=? And (Cashbook_Year           <? or " +
								"(Cashbook_Year="+txtCB_Year+" and Cashbook_Month<=?)) AND ACCOUNT_NO              =? and OB_PUSHED is null");
								ps.setInt(1, cmbAcc_UnitCode);
								ps.setInt(2, cmbOffice_code);
								ps.setInt(3, txtCB_Year);
								ps.setInt(4, txtCB_Month);
								ps.setLong(5, cmbBankAccNo);
								k = ps.executeUpdate();
								
						System.out.println("k:::"+k);
						if(k>0)
						{
							k2=1;
						}
					}
					else
					{
						//fetching bankid,branchid,opr_code
						String hq="SELECT bank_id,BRANCH_ID,bank_ac_no,AC_OPERATIONAL_MODE_ID,trim(AC_OPERATIONAL_MODE_ID) ||'-'||trim(bank_ac_no) AS acc_no " +
								" FROM fas_mst_bank_balance WHERE accounting_unit_id = "+cmbAcc_UnitCode+" AND status               ='Y' and bank_ac_no="+cmbBankAccNo;
						pres=connection.prepareStatement(hq);
						ress=pres.executeQuery();
						if(ress.next())
						{
							bid=ress.getInt("bank_id");
							branchid=ress.getInt("BRANCH_ID");
						    oprmode=ress.getString("AC_OPERATIONAL_MODE_ID");
						}
						
						ps2 = connection.prepareStatement("INSERT INTO FAS_BRS_MASTER (ACCOUNTING_UNIT_ID,"
								+ "ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,ENTRY_DATE,"
								+ "ACCOUNT_NO,BANK_ID,BRANCH_ID,OPERATIONAL_MODE,PASSBOOK_BALANCE,"
								+ "UPDATED_BY_USER_ID,UPDATED_DATE) values (?,?,?,?,?,?,?,?,?,?,?,?)");
							ps2.setInt(1, cmbAcc_UnitCode);
							ps2.setInt(2, cmbOffice_code);
							ps2.setInt(3, ntyear);//txtCB_Year
							ps2.setInt(4, ntmonth);//txtCB_Month
							ps2.setTimestamp(5, ts);
							ps2.setLong(6, cmbBankAccNo);
							ps2.setInt(7,bid);
							ps2.setInt(8,branchid);
							ps2.setString(9,oprmode);
							ps2.setInt(10,0);
							ps2.setString(11, update_user);
							ps2.setTimestamp(12, ts);
							
							k2 = ps2.executeUpdate();
						if(k2>0)
						{
						  ps = connection.prepareStatement("INSERT INTO FAS_BRS_TRANSACTION "
								+ "(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,"
								+ "ENTRY_DATE,SL_NO,TWAD_OR_NON_TWAD,REMITTANCE_DATE,WITHDRAWAL_DATE,"
								+ "VOUCHER_OR_CHALLAN_NO,CHEQUE_DD_NO,PARTICULARS,CR_AMOUNT,DR_AMOUNT,"
								+ "ENTRY_FOUND_IN_PASSBOOK,PASSBOOK_DATE,AMOUNT_IN_PASSBOOK,DIFFERENCE,"
								+ "FOLLOW_UP_ACTION_REQUIRED,CLEARED_BASED_ON_FOLLOWUP,"
								+ "TRANSACTION_TYPE,UPDATED_BY_USER_ID,UPDATED_DATE,ACCOUNT_NO,DOC_DATE,DOC_NO,"
								+ "DOC_TYPE,DETAILS,JOURNALIZED,ACTION_TAKEN) SELECT ACCOUNTING_UNIT_ID,"
								+ "ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,ENTRY_DATE,"
								+ "SL_NO,TWAD_OR_NON_TWAD,"
								+ "REMITTANCE_DATE,WITHDRAWAL_DATE,VOUCHER_OR_CHALLAN_NO,CHEQUE_DD_NO,"
								+ "PARTICULARS,CR_AMOUNT,DR_AMOUNT,ENTRY_FOUND_IN_PASSBOOK,PASSBOOK_DATE,"
								+ "AMOUNT_IN_PASSBOOK,DIFFERENCE,FOLLOW_UP_ACTION_REQUIRED,"
								+ "CLEARED_BASED_ON_FOLLOWUP,TRANSACTION_TYPE,UPDATED_BY_USER_ID,UPDATED_DATE,"
								+ "ACCOUNT_NO,DOC_DATE,DOC_NO,DOC_TYPE,DETAILS,JOURNALIZED,ACTION_TAKEN  "
								+ "FROM FAS_BRS_OB_TRANSACTION_NT WHERE ACCOUNTING_UNIT_ID    =? AND "
								+ "ACCOUNTING_FOR_OFFICE_ID=? And (Cashbook_Year           <? or " +
								"(Cashbook_Year="+txtCB_Year+" and Cashbook_Month<=?)) AND ACCOUNT_NO              =? and OB_PUSHED is null");
								ps.setInt(1, cmbAcc_UnitCode);
								ps.setInt(2, cmbOffice_code);
								ps.setInt(3, txtCB_Year);
								ps.setInt(4, txtCB_Month);
								ps.setLong(5, cmbBankAccNo);
								k = ps.executeUpdate();
								
						}
						else
						{
							k2=0;
						}
					}
					
				
				
				ps3 = connection.prepareStatement("update FAS_BRS_OB_TRANSACTION_NT set OB_PUSHED='Y',OB_PUSHED_DATE=? WHERE ACCOUNTING_UNIT_ID    =? "
								+ "AND ACCOUNTING_FOR_OFFICE_ID=? AND (Cashbook_Year          <"+txtCB_Year+" OR (Cashbook_Year           =? AND Cashbook_Month         <=?)) AND ACCOUNT_NO              =? AND OB_PUSHED              IS NULL ");
				ps3.setTimestamp(1, ts);
				ps3.setInt(2, cmbAcc_UnitCode);
				ps3.setInt(3, cmbOffice_code);
				ps3.setInt(4, txtCB_Year);
				ps3.setInt(5, txtCB_Month);
				ps3.setLong(6, cmbBankAccNo);
				k3 = ps3.executeUpdate();

			
				if((k>0) && (k2>0) && (k3>0))
				{
				xml = xml + "<flag>success</flag>";
				connection.setAutoCommit(true);
				connection.commit();
				}else{
					connection.rollback();
					xml = xml + "<flag>fail</flag>";					
				}
				  
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
		
		
		if (strCommand.equalsIgnoreCase("do1")) {
			xml = xml + "<response><command>do1</command>";

			
			try {
				connection.setAutoCommit(false);
				
				
										
					String mas="SELECT * FROM FAS_BRS_MASTER WHERE ACCOUNTING_UNIT_ID    ="+cmbAcc_UnitCode+" AND ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" AND CASHBOOK_YEAR           ="+txtCB_Year+" AND CASHBOOK_MONTH          ="+txtCB_Month+" AND ACCOUNT_NO="+cmbBankAccNo;
					
					System.out.println("mas===>"+mas);
					
					prepa=connection.prepareStatement(mas);
					resqa=prepa.executeQuery();
					if(!resqa.next())
					{
						
						
						ps2 = connection.prepareStatement("INSERT INTO FAS_BRS_MASTER (select ACCOUNTING_UNIT_ID,"
								+ "ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,ENTRY_DATE,"
								+ "ACCOUNT_NO,BANK_ID,BRANCH_ID,OPERATIONAL_MODE,PASSBOOK_BALANCE,"
								+ "UPDATED_BY_USER_ID,UPDATED_DATE,PASS_BOOK_BALANCE_TYPE,PASS_BOOK_YEAR,PASS_BOOK_MONTH FROM FAS_BRS_MASTER_PBT WHERE ACCOUNTING_UNIT_ID    ="+cmbAcc_UnitCode+" AND "
								+ "ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" And (Cashbook_Year           ="+txtCB_Year
								+ "OR (Cashbook_Year="+txtCB_Year+" and Cashbook_Month<="+txtCB_Month+")) AND ACCOUNT_NO              ="+cmbBankAccNo+" and PUSHED is null)");
//						ps.setInt(1, cmbAcc_UnitCode);
//						ps.setInt(2, cmbOffice_code);
//						ps.setInt(3, txtCB_Year);
//						ps.setInt(4, txtCB_Month);
//						ps.setLong(5, cmbBankAccNo);
							
							k2 = ps2.executeUpdate();
							k2++;
					}
					else
					{
						xml = xml + "<flag>alreadyExistsinBRS</flag>";
					}
					
							System.out.println("K2 values==>"+k2);
						if(k2>0)
						{
						  ps = connection.prepareStatement("INSERT INTO FAS_BRS_TRANSACTION "
								+ "(SELECT ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,"
								+ "ENTRY_DATE,SL_NO,TWAD_OR_NON_TWAD,REMITTANCE_DATE,WITHDRAWAL_DATE,"
								+ "VOUCHER_OR_CHALLAN_NO,CHEQUE_DD_NO,PARTICULARS,CR_AMOUNT,DR_AMOUNT,"
								+ "ENTRY_FOUND_IN_PASSBOOK,PASSBOOK_DATE,AMOUNT_IN_PASSBOOK,DIFFERENCE,REASON_FOR_DIFFERENCE,"
								+ "FOLLOW_UP_ACTION_REQUIRED,CLEARED_BASED_ON_FOLLOWUP,"
								+ "TRANSACTION_TYPE,UPDATED_BY_USER_ID,UPDATED_DATE,ACCOUNT_NO,DOC_DATE,DOC_NO,"
								+ "DOC_TYPE,DETAILS,JOURNALIZED,ACTION_TAKEN, CLEARENCE_REF_TYPE,  CLEARENCE_DATE, "
								+ "IS_IT_CLEARING_ENTRY, PASS_BOOK_MONTH, PASS_BOOK_YEAR "
								+ "FROM FAS_BRS_TRANSACTION_PBT WHERE ACCOUNTING_UNIT_ID    ="+cmbAcc_UnitCode+" AND "
								+ "ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" And (Cashbook_Year           ="+txtCB_Year
								+ "OR (Cashbook_Year="+txtCB_Year+" and Cashbook_Month<="+txtCB_Month+")) AND ACCOUNT_NO              ="+cmbBankAccNo+" and PUSHED is null)");
//								ps.setInt(1, cmbAcc_UnitCode);
//								ps.setInt(2, cmbOffice_code);
//								ps.setInt(3, txtCB_Year);
//								ps.setInt(4, txtCB_Month);
//								ps.setLong(5, cmbBankAccNo);
								
								k = ps.executeUpdate();
								k++;
								
						}
						else
						{
							k2=0;
						}
					
					
					pss2 = connection.prepareStatement(" select ACCOUNT_NO FROM FAS_BRS_TRANSACTION_NOENTRY WHERE ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and CASHBOOK_YEAR="+txtCB_Year+" and CASHBOOK_MONTH="+txtCB_Month+" and ACCOUNT_NO="+cmbBankAccNo);
					System.out.println("select ACCOUNT_NO FROM FAS_BRS_TRANSACTION_NOENTRY WHERE ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and CASHBOOK_YEAR="+txtCB_Year+" and CASHBOOK_MONTH="+txtCB_Month+" and ACCOUNT_NO="+cmbBankAccNo);
					rss2=pss2.executeQuery();
					if(!rss2.next())
					{
						ps = connection
								.prepareStatement("INSERT INTO FAS_BRS_TRANSACTION_NOENTRY   "
										+ "(SELECT ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,"
										+ "ENTRY_DATE,SL_NO,TWAD_OR_NON_TWAD,REMITTANCE_DATE,WITHDRAWAL_DATE,"
										+ "VOUCHER_OR_CHALLAN_NO,CHEQUE_DD_NO,PARTICULARS,CR_AMOUNT,DR_AMOUNT,"
										+ "ENTRY_FOUND_IN_PASSBOOK,PASSBOOK_DATE,AMOUNT_IN_PASSBOOK,DIFFERENCE,"
										+ "REASON_FOR_DIFFERENCE,FOLLOW_UP_ACTION_REQUIRED,CLEARED_BASED_ON_FOLLOWUP,"
										+ "TRANSACTION_TYPE,UPDATED_BY_USER_ID,UPDATED_DATE,ACCOUNT_NO,DOC_DATE,DOC_NO,"
										+ "DOC_TYPE,DETAILS,ACTION_TAKEN,JOURNALIZED "
										+ "FROM FAS_BRS_TRANSACTION_NOENTRYPBT WHERE ACCOUNTING_UNIT_ID    ="+cmbAcc_UnitCode+" AND "
										+ "ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" And (Cashbook_Year           ="+txtCB_Year
										+ "OR (Cashbook_Year="+txtCB_Year+" and Cashbook_Month<="+txtCB_Month+")) AND ACCOUNT_NO              ="+cmbBankAccNo+" and PUSHED is null)");
//						ps.setInt(1, cmbAcc_UnitCode);
//						ps.setInt(2, cmbOffice_code);
//						ps.setInt(3, txtCB_Year);
//						ps.setInt(4, txtCB_Month);
//						ps.setLong(5, cmbBankAccNo);
						
						k3 = ps.executeUpdate();
						k3++;
					}
					else
					{
						xml = xml + "<flag>alreadyExistsinBRSNoEntry</flag>";
					}
						if(k3>0)
						{
							System.out.println("k3::::"+k3);
							connection.setAutoCommit(true);
						}
						else
						{
							k3=0;
						}
					
				
					
					ps3 = connection.prepareStatement("update FAS_BRS_MASTER_PBT set PUSHED='Y',PUSHED_DATE=? WHERE ACCOUNTING_UNIT_ID    =? "
							+ "AND ACCOUNTING_FOR_OFFICE_ID=? AND (Cashbook_Year          <"+txtCB_Year+" OR (Cashbook_Year           =? AND Cashbook_Month         <=?)) AND ACCOUNT_NO              =? AND PUSHED              IS NULL ");
			ps3.setTimestamp(1, ts);
			ps3.setInt(2, cmbAcc_UnitCode);
			ps3.setInt(3, cmbOffice_code);
			ps3.setInt(4, txtCB_Year);
			ps3.setInt(5, txtCB_Month);
			ps3.setLong(6, cmbBankAccNo);
			k4 = ps3.executeUpdate();
			
			
			
			ps5 = connection.prepareStatement("update FAS_BRS_TRANSACTION_PBT set PUSHED='Y',PUSHED_DATE=? WHERE ACCOUNTING_UNIT_ID    =? "
					+ "AND ACCOUNTING_FOR_OFFICE_ID=? AND (Cashbook_Year          <"+txtCB_Year+" OR (Cashbook_Year           =? AND Cashbook_Month         <=?)) AND ACCOUNT_NO              =? AND PUSHED              IS NULL ");
	ps5.setTimestamp(1, ts);
	ps5.setInt(2, cmbAcc_UnitCode);
	ps5.setInt(3, cmbOffice_code);
	ps5.setInt(4, txtCB_Year);
	ps5.setInt(5, txtCB_Month);
	ps5.setLong(6, cmbBankAccNo);
	 k5 = ps5.executeUpdate();
	
	ps6 = connection.prepareStatement("update FAS_BRS_TRANSACTION_NOENTRYPBT set PUSHED='Y',PUSHED_DATE=? WHERE ACCOUNTING_UNIT_ID    =? "
			+ "AND ACCOUNTING_FOR_OFFICE_ID=? AND (Cashbook_Year          <"+txtCB_Year+" OR (Cashbook_Year           =? AND Cashbook_Month         <=?)) AND ACCOUNT_NO              =? AND PUSHED              IS NULL ");
			ps6.setTimestamp(1, ts);
			ps6.setInt(2, cmbAcc_UnitCode);
			ps6.setInt(3, cmbOffice_code);
			ps6.setInt(4, txtCB_Year);
			ps6.setInt(5, txtCB_Month);
			ps6.setLong(6, cmbBankAccNo);
			k6 = ps6.executeUpdate();

			System.out.println("k==>"+k);
			System.out.println("k2==>"+k2);
			System.out.println("k3==>"+k3);
			System.out.println("k4==>"+k4);
			System.out.println("k5==>"+k5);
			System.out.println("k6==>"+k6);
			
			
				if((k>0) && (k2>0) && (k3>0) && (k4>0) && (k5>0) && (k6>0) )
				{
				xml = xml + "<flag>success</flag>";
				connection.setAutoCommit(true);
				connection.commit();
				}else{
					connection.rollback();
					xml = xml + "<flag>fail</flag>";					
				}
				  
				
				
			
				
			} catch (Exception e) {
				try {
					connection.rollback();
				} catch (SQLException e1) {					
					e1.printStackTrace();
				}				
				e.printStackTrace();
//				String sss = e.toString().substring(34, 51);											
//				if(sss.equals("unique constraint"))
//				{
//					xml = xml + "<flag>Exist</flag>";
//				}else{
//					xml = xml + "<flag>fail</flag>";
//				}
			}
		}
		
		
		else if (strCommand.equalsIgnoreCase("loadGrid"))
		{
			xml = xml + "<response><command>loadGrid</command>";
			int load=0;
			try{
			String sql1="SELECT ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,to_char(ENTRY_DATE,'dd-mm-yyyy') as ENTRY_DATE,SL_NO,TWAD_OR_NON_TWAD,to_char(REMITTANCE_DATE,'dd-mm-yyyy') as REMITTANCE_DATE, "+
						" to_char(WITHDRAWAL_DATE,'dd-mm-yyyy') as WITHDRAWAL_DATE,VOUCHER_OR_CHALLAN_NO,CHEQUE_DD_NO,PARTICULARS,CR_AMOUNT,DR_AMOUNT,ENTRY_FOUND_IN_PASSBOOK,to_char(PASSBOOK_DATE,'dd-mm-yyyy') as PASSBOOK_DATE, "+
				" AMOUNT_IN_PASSBOOK,DIFFERENCE,FOLLOW_UP_ACTION_REQUIRED,CLEARED_BASED_ON_FOLLOWUP,TRANSACTION_TYPE,UPDATED_BY_USER_ID,UPDATED_DATE,ACCOUNT_NO, "+
				" 	  DOC_DATE,DOC_NO,DOC_TYPE,DETAILS,JOURNALIZED,ACTION_TAKEN "+
				" 	From FAS_BRS_OB_TRANSACTION_NT "+
				" 	Where Accounting_Unit_Id    ="+cmbAcc_UnitCode+
				" 	And Accounting_For_Office_Id="+cmbOffice_code+
				"   And (Cashbook_Year           <"+txtCB_Year+" or (Cashbook_Year="+txtCB_Year+" and Cashbook_Month<="+txtCB_Month+"))"+
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
