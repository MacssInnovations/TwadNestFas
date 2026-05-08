package Servlets.FAS.FAS1.BRS.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class BRS_Reset_All_Transactions
 */
public class BRS_Reset_All_Transactions extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
	private static final String CONTENT_TYPE1 = "text/xml; charset=windows-1252";
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BRS_Reset_All_Transactions() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/**
		 * Session Checking
		 */
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
		PreparedStatement ps111 = null;
		response.setContentType(CONTENT_TYPE1);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		String xml = "";
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

		
		/* Get Parameters */
		int cmbAcc_UnitCode = 0;
		long cmbBankAccNo = 0;
		String strCommand = "";
		
		/* Get Accounting Unit ID */
		try {
			cmbAcc_UnitCode = Integer.parseInt(request
					.getParameter("cmbAcc_UnitCode"));
		} catch (Exception e) {
			System.out.println("Error Not Getting Accounitng Unit ID --> " + e);
		}
		String  s1 = request.getParameter("cmbBankAccNo");
		System.out.println("Account No string :::"+s1);
		try {
			
			cmbBankAccNo =Long.parseLong(request
					.getParameter("cmbBankAccNo"));
			//return Long.valueOf(line).longValue();
			System.out.println("Account No :::"+cmbBankAccNo);
		} catch (Exception e) {
			System.out.println("Error Not Getting Account No --> " + e);
		}
		try {
			strCommand = request.getParameter("command");
			System.out.println(strCommand);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Account No ***** selected ****"+cmbBankAccNo);
		
		if (strCommand.equalsIgnoreCase("ListAllBRS")) {
			response.setContentType(CONTENT_TYPE1);
			xml = xml + "<response><command>"+strCommand+"</command>";	
		try{
		System.out.println("calling BRS_Reset_All_Transactions DoGET Method servlet::::::::::"+cmbAcc_UnitCode);
		String sql1 ="SELECT Auid, " +
				"  Startmonth, " +
				"  startyear, " +
				"  Account_No, " +
				"  trim(A.ACCOUNTING_UNIT_NAME) as unitname " +
				"FROM " +
				"  (SELECT Accounting_Unit_Id AS Auid, " +
				"    Cashbook_Year            AS Startmonth, " +
				"    Cashbook_Month           AS startyear, " +
				"    Account_No " +
				"FROM Brs_Start_Month_And_Year bsmy " +
				"WHERE Accounting_Unit_Id = ? "  +
				"  AND Account_No           = ?    " +
				"  )Bb, " +
				"  Fas_Mst_Acct_Units a " +
				"WHERE Bb.Auid = a.accounting_unit_id ";
		ps111= con.prepareStatement(sql1);
		ps111.setInt(1,cmbAcc_UnitCode);
		ps111.setLong(2,cmbBankAccNo);
		ResultSet results222 = ps111.executeQuery();
		System.out.println("after query execution");
		
		if(results222.next())
		{
			xml = xml + "<flag>success</flag>";
			xml = xml + "<accunitid>" + results222.getInt("Auid") + "</accunitid>";
			xml = xml + "<accountno>" + results222.getLong("Account_No") + "</accountno>";
			xml = xml + "<startmonth>" + results222.getInt("Startmonth") + "</startmonth>";
			xml = xml + "<startyear>" + results222.getInt("startyear") + "</startyear>";
			xml = xml + "<accunitname>" + results222.getString("unitname") + "</accunitname>";
			
		}
		else
		{
			xml = xml + "<flag>failure</flag>";
			System.out.println("No records");
		}
		}
		catch (Exception e) {
			System.out.println("Error in listing the details"+e);
			xml = xml + "<flag>failure</flag>";
			e.printStackTrace();
		}
		xml = xml + "</response>";
		out.write(xml);
		System.out.println(xml);
		}	
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/**
		 * Session Checking
		 */
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
		PreparedStatement ps1,ps2,ps3,ps4,ps5,ps6,ps7,ps8,ps9,ps10,ps11,ps12,ps13,ps14,ps15,ps16,ps17,ps18,ps19,ps20 = null;
		PreparedStatement ps21,ps22,ps23,ps24,ps25,ps26,ps27,ps28,ps29,ps30 = null;
		response.setContentType(CONTENT_TYPE);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();

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

		

		/* Get Parameters */
		int cmbAcc_UnitCode = 0;
		long cmbBankAccNo = 0;
		
		/* Get Accounting Unit ID */
		try {
			cmbAcc_UnitCode = Integer.parseInt(request
					.getParameter("cmbAcc_UnitCode"));
		} catch (Exception e) {
			System.out.println("Error Not Getting Accounitng Unit ID in doPost--> " + e);
		}
		try {
			cmbBankAccNo =Long.parseLong(request
					.getParameter("cmbBankAccNo"));
		} catch (Exception e) {
			System.out.println("Error Not Getting Account No in doPost--> " + e);
		}

		int flag_count=0 , flag_count11= 0;
		int brs_OB_master_status_flag =0;
		int start_month_flag = 0;
		int brs_OB_master_status_rec = 0;
		int brs_OB_master_rec = 0;
		int brs_OB_master_flag = 0;
		int brs_OB_trn_flag = 0;
		int brs_OB_master_status_NT_rec =0 ; int brs_OB_master_status_NT_flag =0;
		int brs_OB_master_NT_rec = 0 ;int brs_OB_master_NT_flag =0;int brs_OB_trn_NT_flag =0 ;
		int brs_OB_rec = 0;int brs_OB_flag =0 ;
		int brs_OB_mon_closure_rec = 0;int brs_OB_mon_colsure_flag =0 ;
		int brs_master_rec =0 ;int brs_master_flag =0 ;int brs_trn_flag =0;int brs_trn_noentry_flag=0;
		int brs_part1_rec =0;int brs_part1_flag =0;int brs_part2A_rec =0;int brs_part2A_flag=0;
		int brs_part2b_rec =0 ;int brs_part2b_flag= 0;
		int brs_part2C_rec =0 ;int brs_part2C_flag =0;
		int success_flag = 0;
		
		System.out.println("calling BRS_Reset_All_Transactions servlet::::::::::"+cmbAcc_UnitCode);
		try
		{
		 ps2= con
				.prepareStatement("Select count(*) From Brs_Start_Month_And_Year where ACCOUNTING_UNIT_ID =? and ACCOUNT_NO = ?");
				ps2.setInt(1,cmbAcc_UnitCode );
				ps2.setLong(2, cmbBankAccNo);
		ResultSet results2 = ps2.executeQuery();
		if (results2.next()) {
			flag_count = results2.getInt(1);
			System.out.println("No of Records in Brs_Start_Month_And_Year for this unit :: "+flag_count);
		}
		
		if(flag_count >= 1)
		{
			// Delete all BRS Transactions Records only if records exists in the start month year table........ 
			ps3 = con
						.prepareStatement("delete From Brs_Start_Month_And_Year where ACCOUNTING_UNIT_ID =? and ACCOUNT_NO = ?");
			 ps3.setInt(1,cmbAcc_UnitCode);
			 ps3.setLong(2, cmbBankAccNo);
			 start_month_flag = ps3.executeUpdate();
			System.out.println("This is the  value if the records in the Brs_Start_Month_And_Year deleted succeesful "+start_month_flag);
			if(start_month_flag >= 1)
				success_flag = 1;
			
			
			//DELETING ALL THE BRS DATA FROM THEIR RELEVANT TABLES******************
			
			//#1  Deleting records from FAS_BRS_OB_STATUS ................
			try
			{
			ps4= con.prepareStatement("select count(*) from FAS_BRS_OB_STATUS where accounting_unit_id = ? and  ACCOUNT_NO=?");
			ps4.setInt(1, cmbAcc_UnitCode);
			ps4.setLong(2, cmbBankAccNo);
			ResultSet results4= ps4.executeQuery();
			if(results4.next())
			brs_OB_master_status_rec = results4.getInt(1);
			
			if(brs_OB_master_status_rec >0 )
			{
					ps5= con.prepareStatement("delete from FAS_BRS_OB_STATUS where accounting_unit_id = ? and ACCOUNT_NO=?");
					ps5.setInt(1, cmbAcc_UnitCode);
					ps5.setLong(2, cmbBankAccNo);
					brs_OB_master_status_flag = ps5.executeUpdate();
					System.out.println("The Data in the FAS_BRS_OB_STATUS deleted succesfully......."+brs_OB_master_status_flag);
					if(brs_OB_master_status_flag >= 1)
						success_flag = 1;
					
			}
			}
			catch(SQLException e)
			{
				System.out.println("The Error generated is 111111111 ::"+e);
			}
			
			//#2 Deleting record in the brs_OB_master and brs_ob_transaction table................
			
			try
			{
				
			ps6 = con.prepareStatement("select count(*) from FAS_BRS_OB_MASTER where accounting_unit_id = ? and ACCOUNT_NO=?");
			ps6.setInt(1, cmbAcc_UnitCode);
			ps6.setLong(2, cmbBankAccNo);
			ResultSet results6= ps6.executeQuery();
			if(results6.next())
			brs_OB_master_rec = results6.getInt(1);
			
			if (brs_OB_master_rec > 0 )
			{
				ps7= con.prepareStatement("delete from FAS_BRS_OB_MASTER where accounting_unit_id = ? and  ACCOUNT_NO=?");
				ps7.setInt(1, cmbAcc_UnitCode);
				ps7.setLong(2, cmbBankAccNo);
				brs_OB_master_flag = ps7.executeUpdate();
				
				System.out.println("The Data in the FAS_BRS_OB_MASTER deleted succesfully......."+brs_OB_master_flag);
				
				ps8= con.prepareStatement("delete from FAS_BRS_OB_TRANSACTION where accounting_unit_id = ? and ACCOUNT_NO=?");
				ps8.setInt(1, cmbAcc_UnitCode);
				ps8.setLong(2, cmbBankAccNo);
				brs_OB_trn_flag = ps8.executeUpdate();
				
				System.out.println("The Data in the FAS_BRS_OB_TRANSACTION deleted succesfully......."+brs_OB_trn_flag);
				if(brs_OB_master_flag >= 1 || brs_OB_trn_flag>=1 )
					success_flag = 1;
				
			}
			}
			catch(SQLException e)
			{
				System.out.println("The Error generated is 222222222::"+e);
			}
			//#3 Deleting record in the brs_ob_status_NT table................
			try
			{
				ps9= con.prepareStatement("select count(*) from FAS_BRS_OB_STATUS_NT where accounting_unit_id = ? and ACCOUNT_NO=?");
				ps9.setInt(1, cmbAcc_UnitCode);
				ps9.setLong(2, cmbBankAccNo);
				ResultSet results9= ps9.executeQuery();
				if(results9.next())
				brs_OB_master_status_NT_rec = results9.getInt(1);
				
				if(brs_OB_master_status_NT_rec >0 )
				{
						ps10= con.prepareStatement("delete from FAS_BRS_OB_STATUS_NT where accounting_unit_id = ? and ACCOUNT_NO=?");
						ps10.setInt(1, cmbAcc_UnitCode);
						ps10.setLong(2, cmbBankAccNo);
						brs_OB_master_status_NT_flag = ps10.executeUpdate();
						System.out.println("The Data in the FAS_BRS_OB_STATUS_NT deleted succesfully......."+brs_OB_master_status_NT_flag);
						if(brs_OB_master_status_NT_flag >= 1  )
							success_flag = 1;	
				}
				}
				catch(SQLException e)
				{
					System.out.println("The Error generated is 333333 ::"+e);
				}
			//#4 Deleting The BRS_OB_MASTER_NT and BRS_OB_NT_TRANSACTION Tables ........
			try
			{
				System.out.println("T++++++++++++++++++++++++ he Data in the FAS_BRS_OB_TRANSACTION_NT and FAS_BRS_OB_TRANSACTION_NT deleted succesfully");
			ps11 = con.prepareStatement("select count(*) from FAS_BRS_OB_MASTER_NT where accounting_unit_id = ? and ACCOUNT_NO=?");
			ps11.setInt(1, cmbAcc_UnitCode);
			ps11.setLong(2, cmbBankAccNo);
			ResultSet results11= ps11.executeQuery();
			if(results11.next())
				brs_OB_master_NT_rec = results11.getInt(1);
		
			if (brs_OB_master_NT_rec > 0 )
			{
			
		
			ps12= con.prepareStatement("delete from FAS_BRS_OB_MASTER_NT where accounting_unit_id = ? and  ACCOUNT_NO=?");
				ps12.setInt(1, cmbAcc_UnitCode);
				ps12.setLong(2, cmbBankAccNo);
				brs_OB_master_NT_flag = ps12.executeUpdate();
				
				System.out.println("The Data in the FAS_BRS_OB_MASTER_NT deleted succesfully......."+brs_OB_master_NT_flag);
				
				ps13= con.prepareStatement("delete from FAS_BRS_OB_TRANSACTION_NT where accounting_unit_id = ? and ACCOUNT_NO=?");
				ps13.setInt(1, cmbAcc_UnitCode);
				ps13.setLong(2, cmbBankAccNo);
				brs_OB_trn_NT_flag = ps13.executeUpdate();
				
				System.out.println("The Data in the FAS_BRS_OB_TRANSACTION_NT deleted succesfully......."+brs_OB_trn_NT_flag);
				if(brs_OB_master_NT_flag >= 1 ||  brs_OB_trn_NT_flag>=1)
					success_flag = 1;	
			}
			}
			catch(SQLException e)
			{
				System.out.println("The Error generated is 44444444::"+e);
			}
			//#5 deleting records in fas_brs_ob table .......
		
			try
			{
				ps14= con.prepareStatement("select count(*) from FAS_BRS_OB where accounting_unit_id = ? and ACCOUNT_NO=?");
				ps14.setInt(1, cmbAcc_UnitCode);
				ps14.setLong(2, cmbBankAccNo);
				ResultSet results14= ps14.executeQuery();
				if(results14.next())
				brs_OB_rec = results14.getInt(1);
				
				if(brs_OB_rec >0 )
				{
						ps15= con.prepareStatement("delete from FAS_BRS_OB where accounting_unit_id = ? and ACCOUNT_NO=?");
						ps15.setInt(1, cmbAcc_UnitCode);
						ps15.setLong(2, cmbBankAccNo);
						brs_OB_flag = ps15.executeUpdate();
						System.out.println("The Data in the FAS_BRS_OB deleted succesfully......."+brs_OB_flag);
						if(brs_OB_flag >= 1  )
							success_flag = 1;		
				}
				}
				catch(SQLException e)
				{
					System.out.println("The Error generated is 55555555 ::"+e);
				}
			//#6 deleting records in FAS_BRS_MONTHLY_CLOSURE .......
			try
			{
				ps16= con.prepareStatement("select count(*) from FAS_BRS_MONTHLY_CLOSURE where accounting_unit_id = ? and ACCOUNT_NO=?");
				ps16.setInt(1, cmbAcc_UnitCode);
				ps16.setLong(2, cmbBankAccNo);
				ResultSet results16= ps16.executeQuery();
				if(results16.next())
				brs_OB_mon_closure_rec = results16.getInt(1);
				
				if(brs_OB_mon_closure_rec >0 )
				{
						ps17= con.prepareStatement("delete from FAS_BRS_MONTHLY_CLOSURE where accounting_unit_id = ? and ACCOUNT_NO=?");
						ps17.setInt(1, cmbAcc_UnitCode);
						ps17.setLong(2, cmbBankAccNo);
						brs_OB_mon_colsure_flag = ps17.executeUpdate();
						System.out.println("The Data in the FAS_BRS_MONTHLY_CLOSURE deleted succesfully......."+brs_OB_mon_colsure_flag);
						if(brs_OB_mon_colsure_flag >= 1  )
							success_flag = 1;			
				}
				}
				catch(SQLException e)
				{
					System.out.println("The Error generated is 66666666 ::"+e);
				}
			//#7 deleting records in  FAS_BRS_MASTER,FAS_BRS_TRANSACTION,FAS_BRS_TRANSACTION_NOENTRY.......
			try
			{
				ps18= con.prepareStatement("select count(*) from FAS_BRS_MASTER where accounting_unit_id = ? and ACCOUNT_NO=?");
				ps18.setInt(1, cmbAcc_UnitCode);
				ps18.setLong(2, cmbBankAccNo);
				ResultSet results18= ps18.executeQuery();
				if(results18.next())
				brs_master_rec = results18.getInt(1);
				
				if(brs_master_rec >0 )
				{
						ps19= con.prepareStatement("delete from FAS_BRS_MASTER where accounting_unit_id = ? and ACCOUNT_NO=?");
						ps19.setInt(1, cmbAcc_UnitCode);
						ps19.setLong(2, cmbBankAccNo);
						brs_master_flag = ps19.executeUpdate();
						System.out.println("The Data in the FAS_BRS_MASTER deleted succesfully......."+brs_master_flag);
						
						ps20= con.prepareStatement("delete from FAS_BRS_TRANSACTION where accounting_unit_id = ? and ACCOUNT_NO=?");
						ps20.setInt(1, cmbAcc_UnitCode);
						ps20.setLong(2, cmbBankAccNo);
						brs_trn_flag = ps20.executeUpdate();
						
						System.out.println("The Data in the FAS_BRS_TRANSACTION deleted succesfully......."+brs_trn_flag);
						
						ps21= con.prepareStatement("delete from FAS_BRS_TRANSACTION_NOENTRY where accounting_unit_id = ? and ACCOUNT_NO=?");
						ps21.setInt(1, cmbAcc_UnitCode);
						ps21.setLong(2, cmbBankAccNo);
						brs_trn_noentry_flag = ps21.executeUpdate();
						
						System.out.println("The Data in the FAS_BRS_TRANSACTION_NOENTRY deleted succesfully......."+brs_trn_noentry_flag);
						if(brs_master_flag >= 1  || brs_trn_flag >=1 || brs_trn_noentry_flag >= 1 )
							success_flag = 1;		
				}
				}
				catch(SQLException e)
				{
					System.out.println("The Error generated is 7777777 ::"+e);
				}
		
		      //#8  Deleting records in FAS_BRS_PART1,FAS_BRS_PART_2A,Fas_Brs_Part_2b,FAS_BRS_PART_2C ..................
			try
			{
				ps22= con.prepareStatement("select count(*) from FAS_BRS_PART1 where ACCOUNTING_UNIT_ID = ? and ACCOUNT_NO=?");
				ps22.setInt(1, cmbAcc_UnitCode);
				ps22.setLong(2, cmbBankAccNo);
				ResultSet results22= ps22.executeQuery();
				if(results22.next())
				brs_part1_rec = results22.getInt(1);
				
				if(brs_part1_rec >0 )
				{
						ps23= con.prepareStatement("delete from FAS_BRS_PART1 where accounting_unit_id = ? and ACCOUNT_NO=?");
						ps23.setInt(1, cmbAcc_UnitCode);
						ps23.setLong(2, cmbBankAccNo);
						brs_part1_flag = ps23.executeUpdate();
						System.out.println("The Data in the FAS_BRS_PART1 deleted succesfully......."+brs_part1_flag);
						if(brs_part1_flag >= 1  )
							success_flag = 1;			
				}
				}
				catch(SQLException e)
				{
					System.out.println("The Error generated is 88888888 ::"+e);
				}
			try
			{
				ps26= con.prepareStatement("select count(*) from FAS_BRS_PART_2A where accounting_unit_id = ? and ACCOUNT_NO=?");
				ps26.setInt(1, cmbAcc_UnitCode);
				ps26.setLong(2, cmbBankAccNo);
				ResultSet results26= ps26.executeQuery();
				if(results26.next())
				brs_part2A_rec = results26.getInt(1);
				
				if(brs_part2A_rec >0 )
				{
						ps27= con.prepareStatement("delete from FAS_BRS_PART_2A where accounting_unit_id = ? and ACCOUNT_NO=?");
						ps27.setInt(1, cmbAcc_UnitCode);
						ps27.setLong(2, cmbBankAccNo);
						brs_part2A_flag = ps27.executeUpdate();
						System.out.println("The Data in the FAS_BRS_PART_2A deleted succesfully......."+brs_part2A_flag);
						if(brs_part2A_flag >= 1  )
							success_flag = 1;		
				}
				}
				catch(SQLException e)
				{
					System.out.println("The Error generated is 999999999 ::"+e);
				}
			try
			{
				ps24= con.prepareStatement("select count(*) from Fas_Brs_Part_2b where accounting_unit_id = ? and ACCOUNT_NO=?");
				ps24.setInt(1, cmbAcc_UnitCode);
				ps24.setLong(2, cmbBankAccNo);
				ResultSet results24= ps24.executeQuery();
				if(results24.next())
				brs_part2b_rec = results24.getInt(1);
				
				if(brs_part2b_rec >0 )
				{
						ps25= con.prepareStatement("delete from Fas_Brs_Part_2b where accounting_unit_id = ? and ACCOUNT_NO=?");
						ps25.setInt(1, cmbAcc_UnitCode);
						ps25.setLong(2, cmbBankAccNo);
						brs_part2b_flag = ps25.executeUpdate();
						System.out.println("The Data in the Fas_Brs_Part_2b deleted succesfully......."+brs_part2b_flag);
						if(brs_part2b_flag >= 1  )
							success_flag = 1;		
				}
				}
				catch(SQLException e)
				{
					System.out.println("The Error generated is AAAAAAAAAAA  ::"+e);
				}
			try
			{
				ps28= con.prepareStatement("select count(*) from FAS_BRS_PART_2C where accounting_unit_id = ? and ACCOUNT_NO=?");
				ps28.setInt(1, cmbAcc_UnitCode);
				ps28.setLong(2, cmbBankAccNo);
				ResultSet results26= ps28.executeQuery();
				if(results26.next())
				brs_part2C_rec = results26.getInt(1);
				
				if(brs_part2C_rec >0 )
				{
						ps29= con.prepareStatement("delete from FAS_BRS_PART_2C where accounting_unit_id = ? and ACCOUNT_NO=?");
						ps29.setInt(1, cmbAcc_UnitCode);
						ps29.setLong(2, cmbBankAccNo);
						brs_part2C_flag = ps29.executeUpdate();
						System.out.println("The Data in the FAS_BRS_PART_2C deleted succesfully......."+brs_part2C_flag);
						if(brs_part2C_flag >= 1  )
							success_flag = 1;	
				}
				}
				catch(SQLException e)
				{
					System.out.println("The Error generated is BBBBBBBBB  ::"+e);
				}
			
			// Checking if all flag variable's value >0 then send a message to screen that successful deletion of all tables..................
			
			if( success_flag == 1 )
			{
				System.out.println("Data in All the BRS Transactions are deleted successfully*******");
			    sendMessage(response, "All BRS Transactions Data are deleted successfully........", "ok");
				
			}
			
			
		}
		else
		{
			if( success_flag == 0 )
			{
			System.out.println("Thers is no data for this unit&&&&&&&&&&&&");
		    sendMessage(response, "Unable to delete since no BRS entry for this unit", "ok");
			}
		}
		}
		catch(SQLException e){
			System.out.println("Exception arised ::"+e);
		}
	}

	private void sendMessage(HttpServletResponse response, String msg,
			String bType) {
		try {
			String url = "org/Library/jsps/MessengerOkBack.jsp?message=" + msg
					+ "&button=" + bType;
			response.sendRedirect(url);
		} catch (IOException e) {
		}
	}
}
