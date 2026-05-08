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
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Civil_Budget_Additional_Freeze
 */
public class Civil_Budget_Additional_Freeze extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252"; 

    public Civil_Budget_Additional_Freeze() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();

		Connection connection = null;
		Statement statement = null;
		ResultSet results = null;
		ResultSet results2;
		ResultSet rs = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null,ps3=null;
		/*int cashbookYear = 0;
		String cashbookMonth = null;
		int unitid = 0;
		String unitname = "";
		int accid = 0;*/

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
			//System.out.println("chk 3");
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
			//System.out.println("strCommand ssve method:-" + strCommand);
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
				cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
			} catch (Exception e) {
				System.out.println("Error Not Getting Accounitng Unit ID --> "+ e);
			}

			/* Get Accounting for Office ID */
			try {
				cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));

			} catch (Exception e) {
				System.out.println("Error Not Getting Accounting for Office Id --> "+ e);
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
						.prepareStatement("select FINANCIAL_YEAR from FAS_ADDITIONAL_FREEZE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=? and STATEMENT_NO=?");
				ps.setInt(1, cmbAcc_UnitCode);
				ps.setInt(2, cmbOffice_code);
				ps.setString(3, FinancialYear);
				ps.setString(4, cmbFormat_Name);
				results = ps.executeQuery();
				if (results.next()) {
					xml = xml + "<flag>Exist</flag>";
				} else {
					ps1 = connection
							.prepareStatement("insert into FAS_ADDITIONAL_FREEZE(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR,STATEMENT_NO,UPDATED_BY_USERID,UPDATED_DATE,STATUS) values(?,?,?,?,?,?,?)");
					ps1.setInt(1, cmbAcc_UnitCode);
					ps1.setInt(2, cmbOffice_code);
					ps1.setString(3, FinancialYear);
					ps1.setString(4, cmbFormat_Name);
					ps1.setString(5, userid);
					ps1.setTimestamp(6, ts);
					ps1.setString(7, "Y");		
					qcheck1 = ps1.executeUpdate();
					if (qcheck1 > 0) {
						xml = xml + "<flag>success</flag>";
					}else{
						xml = xml + "<flag>NotInsert</flag>";
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
		else if (strCommand.equalsIgnoreCase("save_consolidate")) {
			xml = "<response><command>save_consolidate</command>";

			/* Get Parameters */
			int qcheck1 = 0;
			int cmbAcc_UnitCode = 0;
			int cmbOffice_code = 0;
			String FinancialYear = null;
			String cmbFreezeType = null;
			String cmbFormat_Name = null;
			Vector<Integer> Freezeoff=new Vector<Integer>();
			Vector<Integer> unFreezeoff=new Vector<Integer>();
			/* Get Accounting Unit ID */
			try {
				cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
			} catch (Exception e) {
				System.out.println("Error Not Getting Accounitng Unit ID --> "+ e);
			}

			/* Get Accounting for Office ID */
			try {
				cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));

			} catch (Exception e) {
				System.out.println("Error Not Getting Accounting for Office Id --> "+ e);
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
				ps = connection.prepareStatement("select FINANCIAL_YEAR from FAS_ADDITIONAL_CONSOLID_FREEZE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=? and STATEMENT_NO=?");
				ps.setInt(1, cmbAcc_UnitCode);
				ps.setInt(2, cmbOffice_code);
				ps.setString(3, FinancialYear);
				ps.setString(4, cmbFormat_Name);
				results = ps.executeQuery();
				if (results.next()) {
					xml = xml + "<flag>Exist</flag>";
				} else {
					int offices=0;
					String checkoffice="select accounting_for_office_id from fas_addtional_budget_req where region_office_id="+cmbOffice_code+" and financial_year='"+FinancialYear+"' and STATEMENT_NO='"+cmbFormat_Name+"'";
					ps2=connection.prepareStatement(checkoffice);
					System.out.println("checkoffice  "+checkoffice);
					rs=ps2.executeQuery();
					while(rs.next()){
						offices=rs.getInt("accounting_for_office_id");
						String checkinfreeze="select STATUS,ACCOUNTING_FOR_OFFICE_ID from FAS_ADDITIONAL_FREEZE where ACCOUNTING_FOR_OFFICE_ID="+offices+" and financial_year='"+FinancialYear+"' and STATEMENT_NO='"+cmbFormat_Name+"'";
						ps3=connection.prepareStatement(checkinfreeze);
						System.out.println("checkinfreeze  "+checkinfreeze);
						int cc=ps3.executeUpdate();
						if(cc>0){
							int freeoff=0;
							rs1=ps3.executeQuery();
							while(rs1.next()){
							freeoff=rs1.getInt("ACCOUNTING_FOR_OFFICE_ID");
							if(!Freezeoff.contains(freeoff))
							{
								Freezeoff.add(freeoff);
							}
							}
						}else{
							if(!unFreezeoff.contains(offices))
							{
								unFreezeoff.add(offices);
							}
						}
					}
					System.out.println("unFreezeoff  "+unFreezeoff);
					System.out.println("Freezeoff  "+Freezeoff);
					//if((unFreezeoff.isEmpty())||(unFreezeoff.size()==1)){
					if(unFreezeoff.isEmpty()){
				ps1 = connection.prepareStatement("insert into FAS_ADDITIONAL_CONSOLID_FREEZE(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR,STATEMENT_NO,UPDATED_BY_USERID,UPDATED_DATE,STATUS) values(?,?,?,?,?,?,?)");
				ps1.setInt(1, cmbAcc_UnitCode);
				ps1.setInt(2, cmbOffice_code);
				ps1.setString(3, FinancialYear);
				ps1.setString(4, cmbFormat_Name);
				ps1.setString(5, userid);
				ps1.setTimestamp(6, ts);
				ps1.setString(7, "Y");
				qcheck1 = ps1.executeUpdate();
				System.out.println("qcheck1 "+qcheck1);
				if (qcheck1 > 0) {
					xml = xml + "<flag>success</flag>";
				}else{
					xml = xml + "<flag>NotInsert</flag>";
				}
					}else{
						xml = xml + "<flag>someoffice</flag>";
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
		
		else if (strCommand.equalsIgnoreCase("DeleteFn")) {
			xml = "<response><command>DeleteFn</command>";

			/* Get Parameters */
			int qcheck1 = 0;
			int cmbAcc_UnitCode = 0;
			int cmbOffice_code = 0;
			String FinancialYear = null;
			String cmbFreezeType = null;
			String cmbFormat_Name = null;
			Vector<Integer> Freezeoff=new Vector<Integer>();
			Vector<Integer> unFreezeoff=new Vector<Integer>();
			/* Get Accounting Unit ID */
			try {
				cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
			} catch (Exception e) {
				System.out.println("Error Not Getting Accounitng Unit ID --> "+ e);
			}

			/* Get Accounting for Office ID */
			try {
				cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));

			} catch (Exception e) {
				System.out.println("Error Not Getting Accounting for Office Id --> "+ e);
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
				ps = connection.prepareStatement("select REGION_OFFICE_ID from COM_MST_ALL_OFFICES_VIEW where OFFICE_ID=?");
				ps.setInt(1, cmbOffice_code);
				results = ps.executeQuery();
				while(results.next()) {
					int off=results.getInt("REGION_OFFICE_ID");
					String checkunfr="select 'X' from FAS_ADDITIONAL_CONSOLID_FREEZE where ACCOUNTING_FOR_OFFICE_ID="+off+" and financial_year='"+FinancialYear+"' and STATEMENT_NO='"+cmbFormat_Name+"'";
					ps2=connection.prepareStatement(checkunfr);
					int cc=ps2.executeUpdate();
					if(cc>0){
						xml = xml + "<status>RegionFreezed</status>";
					}else{
						xml = xml + "<status>RegionNotFreezed</status>";
						String deleteunfree="delete from FAS_ADDITIONAL_FREEZE where ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and FINANCIAL_YEAR='"+FinancialYear+"' and STATEMENT_NO='"+cmbFormat_Name+"'";
						ps3=connection.prepareStatement(deleteunfree);
						int cc1=ps3.executeUpdate();
						if(cc1>0){
							xml = xml + "<flag>Deleted</flag>";
						}else{
							xml = xml + "<flag>NotDeleted</flag>";
						}
					}	
				} /*else {
					int offices=0;
					String checkoffice="select accounting_for_office_id from fas_addtional_budget_req where region_office_id="+cmbOffice_code+" and financial_year='"+FinancialYear+"' and STATEMENT_NO='"+cmbFormat_Name+"'";
					ps2=connection.prepareStatement(checkoffice);
					System.out.println("checkoffice  "+checkoffice);
					rs=ps2.executeQuery();
					while(rs.next()){
						offices=rs.getInt("accounting_for_office_id");
						String checkinfreeze="select STATUS,ACCOUNTING_FOR_OFFICE_ID from FAS_ADDITIONAL_FREEZE where ACCOUNTING_FOR_OFFICE_ID="+offices+" and financial_year='"+FinancialYear+"' and STATEMENT_NO='"+cmbFormat_Name+"'";
						ps3=connection.prepareStatement(checkinfreeze);
						System.out.println("checkinfreeze  "+checkinfreeze);
						int cc=ps3.executeUpdate();
						if(cc>0){
							int freeoff=0;
							rs1=ps3.executeQuery();
							while(rs1.next()){
							freeoff=rs1.getInt("ACCOUNTING_FOR_OFFICE_ID");
							if(!Freezeoff.contains(freeoff))
							{
								Freezeoff.add(freeoff);
							}
							}
						}else{
							if(!unFreezeoff.contains(offices))
							{
								unFreezeoff.add(offices);
							}
						}
					}
					System.out.println("unFreezeoff  "+unFreezeoff);
					System.out.println("Freezeoff  "+Freezeoff);
					//if((unFreezeoff.isEmpty())||(unFreezeoff.size()==1)){
					if(unFreezeoff.isEmpty()){
				ps1 = connection.prepareStatement("insert into FAS_ADDITIONAL_CONSOLID_FREEZE(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR,STATEMENT_NO,UPDATED_BY_USERID,UPDATED_DATE,STATUS) values(?,?,?,?,?,?,?)");
				ps1.setInt(1, cmbAcc_UnitCode);
				ps1.setInt(2, cmbOffice_code);
				ps1.setString(3, FinancialYear);
				ps1.setString(4, cmbFormat_Name);
				ps1.setString(5, userid);
				ps1.setTimestamp(6, ts);
				ps1.setString(7, "Y");
				qcheck1 = ps1.executeUpdate();
				System.out.println("qcheck1 "+qcheck1);
				if (qcheck1 > 0) {
					xml = xml + "<flag>success</flag>";
				}else{
					xml = xml + "<flag>NotInsert</flag>";
				}
					}else{
						xml = xml + "<flag>someoffice</flag>";
					}
						
				}*/

			} catch (Exception e) {
				xml = xml + "<status>failure</status>";
				e.printStackTrace();
			}
			xml = xml + "</response>";
			out.write(xml);
			System.out.println(xml);
		}
		
		
		else if (strCommand.equalsIgnoreCase("delete_consolidate")) {
			System.out.println("strCommand delete_consolidate method:-" + strCommand);
			xml = "<response><command>delete_consolidate</command>";

			/* Get Parameters */
			int cmbAcc_UnitCode = 0;
			int cmbOffice_code = 0;
			String FinancialYear = null;
			String cmbFreezeType = null;
			String cmbFormat_Name = null;
			/* Get Accounting Unit ID */
			try {
				cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
			} catch (Exception e) {
				System.out.println("Error Not Getting Accounitng Unit ID --> "+ e);
			}

			try {
				cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));

			} catch (Exception e) {
				System.out.println("Error Not Getting Accounting for Office Id --> "+ e);
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
				ps = connection.prepareStatement("delete from FAS_ADDITIONAL_CONSOLID_FREEZE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=? and STATEMENT_NO=?");
				ps.setInt(1, cmbAcc_UnitCode);
				ps.setInt(2, cmbOffice_code);
				ps.setString(3, FinancialYear);
				ps.setString(4, cmbFormat_Name);
				int cc= ps.executeUpdate();
				if(cc>0){
					xml = xml + "<flag>deleted</flag>";
				}else{
					xml = xml + "<flag>NotDelete</flag>";
				}			

			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}
			xml = xml + "</response>";
			out.write(xml);
			System.out.println(xml);
		}
		
		
		
	}

}
