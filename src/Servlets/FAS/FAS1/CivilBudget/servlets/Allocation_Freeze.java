package Servlets.FAS.FAS1.CivilBudget.servlets;

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
 * Servlet implementation class Allocation_Freeze
 */
public class Allocation_Freeze extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Allocation_Freeze() {
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
		System.out.println("test servlet");
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();

		Connection connection = null;
		Statement statement = null;
		ResultSet results = null;
		ResultSet results2;
		ResultSet rs = null,r_chk=null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		PreparedStatement ps = null,p_chk=null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		int cashbookYear = 0;
		String cashbookMonth = null;
		int unitid = 0;
		String unitname = "";
		int accid = 0;
		int cl_no=0;
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
			System.out.println("strCommand:-" + strCommand);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Servlets.Security.classes.UserProfile empProfile = (Servlets.Security.classes.UserProfile) session
				.getAttribute("UserProfile");
		int empid = empProfile.getEmployeeId();
		String empName = empProfile.getEmployeeName();
		long l = System.currentTimeMillis();
		Timestamp ts = new Timestamp(l);

		if (strCommand.equalsIgnoreCase("save")) {
			System.out.println("strCommand ssve method:-" + strCommand);
			xml = "<response><command>save</command>";

			/* Get Parameters */
			int qcheck1 = 0;
			int cmbAcc_UnitCode = 0;
			int cmbOffice_code = 0;
			String FinancialYear = null;
			String cmbFreezeType = null;
			String cmbFormat_Name = null;
			/* Get Accounting Unit ID */
			try {
				cmbAcc_UnitCode = Integer.parseInt(request
						.getParameter("cmbAcc_UnitCode"));
			} catch (Exception e) {
				System.out.println("Error Not Getting Accounitng Unit ID --> "
						+ e);
			}

			/* Get Accounting for Office ID */
			try {
				cmbOffice_code = Integer.parseInt(request
						.getParameter("cmbOffice_code"));

			} catch (Exception e) {
				System.out
						.println("Error Not Getting Accounting for Office Id --> "
								+ e);
			}

			/* Get FinancialYear */
			try {
				FinancialYear = request.getParameter("cmbFinancialYear");
			} catch (Exception e) {
				System.out.println("Error Not Getting Financial Year -->" + e);
			}

			/* Get Format_Name */
			try {
				cmbFormat_Name = request.getParameter("cmbFormat_Name");
			} catch (Exception e) {
				System.out.println("Error Not Getting cmbFormat_Name -->" + e);
			}

			/* Get FreezeType */
			try {
				cmbFreezeType = request.getParameter("cmbFreezeType");
			} catch (Exception e) {
				System.out.println("Error Not Getting FreezeType -->" + e);
			}

			try {
				ps = connection
						.prepareStatement("select FINANCIAL_YEAR from FAS_BUDGET_CLOSURE_ALLOCATION where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=? and STATEMENT_NAME=?");
				ps.setInt(1, cmbAcc_UnitCode);
				ps.setInt(2, cmbOffice_code);
				ps.setString(3, FinancialYear);
				ps.setString(4, cmbFormat_Name);
				results = ps.executeQuery();
				if (results.next()) {
					xml = xml + "<flag>Exist</flag>";
				} else {
					ps1 = connection
							.prepareStatement("insert into FAS_BUDGET_CLOSURE_ALLOCATION(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR,STATEMENT_NAME,UPDATED_BY_USERID,UPDATED_DATE) values(?,?,?,?,?,?)");
					ps1.setInt(1, cmbAcc_UnitCode);
					ps1.setInt(2, cmbOffice_code);
					ps1.setString(3, FinancialYear);
					//ps1.setString(4, "YES");
					//ps1.setString(5, "NO");
					ps1.setString(4, cmbFormat_Name);
					//ps1.setString(7, "L");
					ps1.setString(5, userid);
					ps1.setTimestamp(6, ts);
					qcheck1 = ps1.executeUpdate();
					if (qcheck1 > 0) {
						xml = xml + "<flag>success</flag>";
					}
				}

			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}
			xml = xml + "</response>";
			out.write(xml);
			System.out.println(xml);
		}
		else if (strCommand.equalsIgnoreCase("save_HO")) {
			xml = "<response><command>save_HO</command>";

			/* Get Parameters */
			int qcheck1 = 0;
			int cmbAcc_UnitCode = 0;
			int cmbOffice_code = 0;
			String FinancialYear = null;
			//String cmbFreezeType = null;
			String cmbFormat_Name = null;
			/* Get Accounting Unit ID */
			try {
				cmbAcc_UnitCode = Integer.parseInt(request
						.getParameter("cmbAcc_UnitCode"));
			} catch (Exception e) {
				System.out.println("Error Not Getting Accounitng Unit ID --> "
						+ e);
			}

			/* Get Accounting for Office ID */
			try {
				cmbOffice_code = Integer.parseInt(request
						.getParameter("cmbOffice_code"));

			} catch (Exception e) {
				System.out
						.println("Error Not Getting Accounting for Office Id --> "
								+ e);
			}

			/* Get FinancialYear */
			try {
				FinancialYear = request.getParameter("cmbFinancialYear");
			} catch (Exception e) {
				System.out.println("Error Not Getting Financial Year -->" + e);
			}

			/* Get Format_Name */
			try {
				cmbFormat_Name = request.getParameter("cmbFormat_Name");
			} catch (Exception e) {
				System.out.println("Error Not Getting cmbFormat_Name -->" + e);
			}


			try {
				ps = connection
						.prepareStatement("select FINANCIAL_YEAR from FAS_BUDGET_ALLOCATION_HO_CLOSE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=? and STATEMENT_NAME=?");
				ps.setInt(1, cmbAcc_UnitCode);
				ps.setInt(2, cmbOffice_code);
				ps.setString(3, FinancialYear);
				ps.setString(4, cmbFormat_Name);
				results = ps.executeQuery();
				if (results.next()) {
					xml = xml + "<flag>Exist</flag>";
				} else {
					ps1 = connection
							.prepareStatement("insert into FAS_BUDGET_ALLOCATION_HO_CLOSE(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR,STATEMENT_NAME,UPDATED_BY_USERID,UPDATED_DATE) values(?,?,?,?,?,?)");
					ps1.setInt(1, cmbAcc_UnitCode);
					ps1.setInt(2, cmbOffice_code);
					ps1.setString(3, FinancialYear);
					ps1.setString(4, cmbFormat_Name);
					ps1.setString(5, userid);
					ps1.setTimestamp(6, ts);
					qcheck1 = ps1.executeUpdate();
					System.out.println("qcheck"+qcheck1);
					if (qcheck1 > 0) {
						xml = xml + "<flag>success</flag>";
					}
				}

			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}
			xml = xml + "</response>";
			out.write(xml);
			System.out.println(xml);
		}else if(strCommand.equalsIgnoreCase("Push_HO")){
System.out.println("test ............................ ");
			xml = "<response><command>Push_HO</command>";
			String qry="",Sel_qry="";
			/* Get Parameters */
			int qcheck=0,qcheck1 = 0;
			int cmbAcc_UnitCode = 0;
			int cmbOffice_code = 0;
			String FinancialYear = null;
			//String cmbFreezeType = null;
			int cmbFormat_Name = 0;
			/* Get Accounting Unit ID */
			try {
				cmbAcc_UnitCode = Integer.parseInt(request
						.getParameter("cmbAcc_UnitCode"));
			} catch (Exception e) {
				System.out.println("Error Not Getting Accounitng Unit ID --> "
						+ e);
			}

			/* Get Accounting for Office ID */
			try {
				cmbOffice_code = Integer.parseInt(request
						.getParameter("cmbOffice_code"));

			} catch (Exception e) {
				System.out
						.println("Error Not Getting Accounting for Office Id --> "
								+ e);
			}

			/* Get FinancialYear */
			try {
				FinancialYear = request.getParameter("cmbFinancialYear");
			} catch (Exception e) {
				System.out.println("Error Not Getting Financial Year -->" + e);
			}

			/* Get Format_Name */
			try {
				cmbFormat_Name = Integer.parseInt(request.getParameter("cmbFormat_Name"));
			} catch (Exception e) {
				System.out.println("Error Not Getting cmbFormat_Name -->" + e);
			}
System.out.println(cmbOffice_code+"   "+FinancialYear+"  "+cmbFormat_Name);

			try {
				try{
				String Sel_qry1="SELECT COUNT(1) as cno " +
//" FROM COM_BUDGET_DETAILS_cp1 " +
" FROM COM_BUDGET_DETAILS " +
" WHERE accounting_for_office_id= "+cmbOffice_code+
" AND financial_year            ='"+FinancialYear+"'"+
" AND statement_no              = "+cmbFormat_Name;
				 Sel_qry="SELECT COUNT(1) as cno " +
				// " FROM COM_BUDGET_DETAILS_cp1 " +
				 " FROM COM_BUDGET_DETAILS " +
				 " WHERE accounting_for_office_id= ? "+
				 " AND financial_year            =? "+
				 " AND statement_no              =? ";
				System.out.println(" >>> "+Sel_qry1);
				 PreparedStatement ps_sts=connection.prepareStatement(Sel_qry1);
			/*	ps_sts.setInt(1, cmbOffice_code);
				ps_sts.setString(2, FinancialYear);
				ps_sts.setInt(3, cmbFormat_Name);*/
				ResultSet rs_set=ps_sts.executeQuery();
				System.out.println("result set >>> "+rs_set);
				while(rs_set.next()){
					qcheck=rs_set.getInt("cno");
					System.out.println("qcheck >>> "+qcheck);
				}
				}catch (Exception e) {
					e.printStackTrace();
				}
				if(qcheck>0){
					xml+="<flag>Exist</flag>";
				}else if(qcheck==0) {
			
					int regionid=0;
					
					String regCheck=" select REGION_OFFICE_ID from COM_MST_ALL_OFFICES_VIEW where OFFICE_ID="+cmbOffice_code;
					PreparedStatement pss=connection.prepareStatement(regCheck);
					ResultSet rss=pss.executeQuery();
					if(rss.next()){
						regionid=rss.getInt("REGION_OFFICE_ID");
					}
					
					
				String p_chkQry="SELECT COUNT(1) AS Clos_no " +
					" FROM FAS_BUDGET_CLOSURE_ALLOCATION " +
					" WHERE " +
					//"ACCOUNTING_UNIT_ID    = " +cmbAcc_UnitCode+
					//" AND " +
					"accounting_for_office_id=  " +regionid+
					" AND financial_year          = '" +FinancialYear+"'"+
					" AND STATEMENT_NAME          = "+cmbFormat_Name;
				System.out.println(p_chkQry);
				 p_chk=connection.prepareStatement(p_chkQry);
				 r_chk=p_chk.executeQuery();
				while(r_chk.next())
				{
					cl_no=r_chk.getInt("Clos_no");
				}
				System.out.println("closure no .... "+cl_no);
					if(cl_no>0)
					{					
				 qry="INSERT " +
				//"INTO COM_BUDGET_DETAILS_cp1 " +
				 "INTO COM_BUDGET_DETAILS " +
				"  ( " +
				"    ACCOUNTING_UNIT_ID, " +
				"    ACCOUNTING_FOR_OFFICE_ID, " +
				"    FINANCIAL_YEAR, " +
				"    ACCOUNT_HEAD_CODE, " +
				"    PREVIOUS_YEAR_EXPENDITURE, " +
				"    PREVIOUS_YEAR_REVISED_ESTIMATE, " +
				"    CURRENT_YEAR_BUDGET_ESTIMATE, " +
				"    CURRENT_YEAR_REVISED_ESTIMATE, " +
				"    NEXT_YEAR_ESTIMATE, " +
				"    REF_NO, " +
				"    REF_DATE, " +
				"    REMARKS, " +
				"    UPDATED_BY_USER_ID, " +
				"    UPDATED_DATE, " +
				"    CURRENT_YEAR_BUDGET_ALLOTTED, " +
				"    BUDGET_SOFAR_SPENT, " +
				"    ALLOCATION_TYPE, " +
				"    FROM_ACC_HD_CODE, " +
				"    TO_ACC_HD_CODE," +
				" STATEMENT_GROUP_NO, "+
                " STATEMENT_NO " +
				"  ) " +
				"SELECT ? AS ACCOUNTING_UNIT_ID, " +
				"  s.accounting_for_office_id, " +
				"  s.financial_year, " +
				"  s.account_head_code, " +
				"  0                           AS PREVIOUS_YEAR_EXPENDITURE, " +
				"  0                           AS PREVIOUS_YEAR_REVISED_ESTIMATE, " +
				"  0                           AS CURRENT_YEAR_BUDGET_ESTIMATE, " +
				"  0                           AS CURRENT_YEAR_REVISED_ESTIMATE, " +
				"  0                           AS NEXT_YEAR_ESTIMATE, " +
				"  0                           AS REF_NO, " +
				"  to_date(now(),'dd-mm-yy') AS REF_DATE, " +
				"  '' REMARKS, " +
				" ? AS UPDATED_BY_USER_ID, ? as UPDATED_DATE, " +
				//"  s.AMOUNT, " +
				"  Case When S.Unit_Allocation='R' Then S.Amount "+
                " When S.Unit_Allocation='T' Then S.Amount *1000 "+
                "  when S.Unit_Allocation='L' Then S.Amount*100000 else 0 end as Amount, " +
				"  '' BUDGET_SOFAR_SPENT, " +
				"  s.ALLOCATION_TYPE, " +
				"  s.FROM_ACC_HD_CODE, " +
				"  s.TO_ACC_HD_CODE ,s.STATEMENT_GROUP_NO," +
                "  s.STATEMENT_NO " +
				" FROM fas_statement_by_region s " +
				"WHERE  " +
				" s.accounting_for_office_id=? " +
				"AND s.financial_year          =? and s.statement_no          =?";
				System.out.println(qry);
				ps = connection
						.prepareStatement(qry);
				ps.setInt(1, cmbAcc_UnitCode);
				ps.setString(2, userid);
				ps.setTimestamp(3, ts);
				ps.setInt(4, cmbOffice_code);
				ps.setString(5, FinancialYear);
				ps.setInt(6, cmbFormat_Name);
			
					qcheck1 = ps.executeUpdate();
				System.out.println("qcheck"+qcheck1);
					if (qcheck1 > 0) {
						xml = xml + "<flag>success</flag>";
					}
				
				}
					else{
						xml = xml + "<flag>failure_1</flag>";
					}
			} 
			}
			catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}
			xml = xml + "</response>";
			out.write(xml);
			System.out.println(xml);
		
		}
	}
}
