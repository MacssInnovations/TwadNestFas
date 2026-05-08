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

import Servlets.FAS.FAS1.CivilBills.servlets.LoadDriver;

/**
 * Servlet implementation class Civil_Budget_Additional_Re_appropriation
 */
public class Civil_Budget_Additional_Re_appropriation extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Civil_Budget_Additional_Re_appropriation() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();

		Connection connection = null;
		Statement statement = null;
		ResultSet results = null;
		ResultSet rs = null,rss1=null,results_one=null;
		ResultSet rs2 = null;
		PreparedStatement ps = null;
		PreparedStatement preparedStatement1 = null;
		PreparedStatement ps1 = null,ps4=null;

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
		if (strCommand.equalsIgnoreCase("getStatementName")) {
			//System.out.println("inside getStatementName   menthod...");
			xml = "<response><command>getStatementName</command>";
			try {
				ps = connection
						.prepareStatement("select STATEMENT_NO,STATEMENT_DESC from FAS_STATEMENT_MASTER order by STATEMENT_NO");
				results = ps.executeQuery();
				while (results.next()) {
					xml = xml + "<STATEMENT_NO>"
							+ results.getString("STATEMENT_NO")
							+ "</STATEMENT_NO>";
					xml = xml + "<STATEMENT_DESC>"
							+ results.getString("STATEMENT_DESC")+" ( "+results.getString("STATEMENT_NO")+" )"
							+ "</STATEMENT_DESC>";
				}

				xml = xml + "<flag>success</flag>";
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}
		}else if (strCommand.equalsIgnoreCase("callHead")) 
		{
			
			int count = 0;
			xml = "<response><command>callHead</command>";
			int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));			
			int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
			
			String fyear =request.getParameter("cmbFinancialYear");
			System.out.println("fyear"+fyear);
			int cmbStatementName = Integer.parseInt(request.getParameter("cmbStatementName"));
			System.out.println("cmbStatementName"+cmbStatementName);
			int statementGp = Integer.parseInt(request.getParameter("statementGp"));
			System.out.println("statementGp"+statementGp);
			String sql = "";
			
			try {
				sql="Select Case When Group_Type='H' Then From_Acc_Hd_Code||'' "+ 
			" When Group_Type='G' Then From_Acc_Hd_Code||' to '||To_Acc_Hd_Code  "+
			" End As Range_Of_Heads "+
			" From Fas_Statement_Acc_Hd_Mapping Where Statement_No="+cmbStatementName+" And " +
			" Statement_Group_No="+statementGp+" order by From_Acc_Hd_Code";
				System.out.println(sql);
				ps1 = connection.prepareStatement(sql);
				
				results = ps1.executeQuery();
				while(results.next()){
					xml = xml + "<Range_Of_Heads>"+ results.getString("Range_Of_Heads")+ "</Range_Of_Heads>";
					count++;
				}
				if(count>0){
					xml += "<flag>success</flag>";
					
					
				}
				else{
					xml += "<flag>failure</flag>";
				}
			} catch (Exception e) {
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
		}
		else if (strCommand.equalsIgnoreCase("groupch")) 
		{

			int count = 0;
			xml = "<response><command>groupch</command>";
			String statementno = request.getParameter("statement");			
			
			String sql = "";
			try {
				sql="Select Statement_Group_No,STATEMENT_GROUP_DESC from FAS_STATEMENT_GROUP_MASTER where STATEMENT_NAME='"+statementno+"' order by Statement_Group_No";
				System.out.println(sql);
				ps1 = connection.prepareStatement(sql);
				
				results = ps1.executeQuery();
				while(results.next()){
					xml = xml + "<gp_no>"+ results.getInt("Statement_Group_No")+ "</gp_no>";
					xml = xml + "<gp_desc>"+ results.getString("STATEMENT_GROUP_DESC")+ "</gp_desc>";
					count++;
				}
				if(count>0){
					xml += "<flag>success</flag>";
				}else{
					xml += "<flag>failure</flag>";
				}
			} catch (Exception e) {
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
		}
		
		
		else if (strCommand.equalsIgnoreCase("load_table")) {
			int count = 0,r_count=0;
			int cmbOffice_code = Integer.parseInt(request.getParameter("txtRegionId"));
            String cmbFinancialYear = request.getParameter("cmbFinancialYear");
			int cmbStatementName = Integer.parseInt(request.getParameter("cmbStatementName"));
			int y1 = Integer.parseInt(request.getParameter("y1"));
			int y2 = Integer.parseInt(request.getParameter("y2"));
			
			int statementGp = Integer.parseInt(request.getParameter("statementGp"));
			 String checkGrp = request.getParameter("checkGrp");
			 int head_code = Integer.parseInt(request.getParameter("head_code"));
				String condn="";
			 if(checkGrp.equalsIgnoreCase("H")){
				 condn="";
			 }else{
				 condn=""; 
			 }
					

			xml = "<response><command>load_table</command>";
			System.out.println("in civil budget additional Reappro---"+"cmbOffice_code"+cmbOffice_code+"cmbFinancialYear"+cmbFinancialYear+"head_code"+head_code+"cmbStatementName"+cmbStatementName+"statementGp"+statementGp);
			try{
				String hq1="";
									
								hq1="SELECT a.OFFICE_ID, "+
					 " a.OFFICE_NAME, "+
					 "  b.ACCOUNTING_FOR_OFFICE_ID, "+
					  "  b.ADDL_BUDGET_REQ,b.ACCOUNT_HEAD_CODE,b.Statement_No,b.Statement_Group_No, "+
					  "  (SELECT f1.STATEMENT_DESC FROM FAS_STATEMENT_MASTER f1 WHERE f1.Statement_No=b.STATEMENT_NO )AS statementName, "+
					  "  (SELECT f2.STATEMENT_GROUP_DESC FROM FAS_STATEMENT_GROUP_MASTER f2  "+
							  "  WHERE f2.STATEMENT_NAME=b.STATEMENT_NO AND f2.STATEMENT_GROUP_NO=b.STATEMENT_GROUP_NO) AS StatementGroup, "+ 
					  "   c.amt_sofar , d.alloted_amt"+ // b.AMOUNT_ALLOTTED as alloted_amt 
					  " FROM "+
					" (SELECT OFFICE_ID, "+
							  "   OFFICE_NAME "+
					    "  FROM COM_MST_ALL_OFFICES_VIEW1 "+
					  "  WHERE REGION_OFFICE_ID= "+cmbOffice_code+
					  "  )a "+
					  " right OUTER JOIN "+   ///right 
					" (SELECT r.ACCOUNTING_FOR_OFFICE_ID, "+
							  "  (SELECT o.Office_Name "+
					    		"  FROM COM_MST_ALL_OFFICES_VIEW o "+
					    "  WHERE o.Office_Id=r.Accounting_For_Office_Id "+
					    "  )AS Office_Name, "+
					    "  ACCOUNT_HEAD_CODE, "+
					    "  AMOUNT_ALLOTTED, "+
					    "   ADDL_BUDGET_REQ,Statement_No,Statement_Group_No "+
					    " FROM FAS_ADDTIONAL_BUDGET_REQ r "+
					  "  WHERE Statement_No    = "+cmbStatementName+
					  " AND Statement_Group_No= "+statementGp+
					  "  AND Financial_Year    ='"+cmbFinancialYear+"' "+
					    	"   AND Region_Office_Id  =  "+cmbOffice_code+
					    "   and ACCOUNT_HEAD_CODE= "+head_code+
					    "  )b "+
					  " ON a.OFFICE_ID=b.ACCOUNTING_FOR_OFFICE_ID "+
					  " left outer join "+
					  " (select AMOUNT as alloted_amt , "+
					 " ACCOUNTING_FOR_OFFICE_ID "+
					  " from FAS_STATEMENT_BY_REGION  "+
					  " where STATEMENT_NO= "+cmbStatementName+
					  " and  STATEMENT_GROUP_NO= "+statementGp+
					  " and FINANCIAL_YEAR='"+cmbFinancialYear+"' "+
					 " and ACCOUNTING_FOR_OFFICE_ID= "+cmbOffice_code+
					  " and ACCOUNT_HEAD_CODE='"+head_code+"')d "+
					 " on a.OFFICE_ID=d.ACCOUNTING_FOR_OFFICE_ID "+
					" left OUTER JOIN "+
					"  (SELECT (cre-deb) as amt_sofar, "+
							  "   unitid, "+
					    "  (SELECT u.ACCOUNTING_UNIT_OFFICE_ID "+
					    		"  FROM FAS_MST_ACCT_UNITS u "+
					    "  WHERE u.ACCOUNTING_UNIT_ID=unitid "+
					    "  )AS officeid, "+
					    "   ACCOUNT_HEAD_CODE "+
					    " FROM "+
					  " (SELECT SUM(CURRENT_MONTH_DEBIT) deb, "+
					    		"   SUM(CURRENT_MONTH_CREDIT) cre, "+
					      "  accounting_unit_id AS unitid, "+
					      "    ACCOUNT_HEAD_CODE "+
					      "  FROM FAS_TRIAL_BALANCE "+
					    "  WHERE To_Date((Cashbook_Month "+
					    		"  ||'-' "+
					      "  || Cashbook_Year),'mm-yyyy') BETWEEN To_Date(4 "+  //finyear april to dec
					    		  " 		  ||'-' "+
					      "  ||"+y1+",'mm-yyyy') "+
					    " AND to_date(12 "+
					    		" ||'-' "+
					      "   ||"+y1+",'mm-yyyy') "+
					    " AND ACCOUNT_HEAD_CODE = "+head_code+
					     "  GROUP BY accounting_unit_id, "+
					    "    ACCOUNT_HEAD_CODE "+
					      "   ) "+
					  "  )c "+
					  " ON a.OFFICE_ID          =c.officeid "+
					" AND b.ACCOUNT_HEAD_CODE =c.ACCOUNT_HEAD_CODE";	
				
				
			System.out.println("load table  "+hq1);
			ps1 = connection.prepareStatement(hq1);				
			rs2=ps1.executeQuery();
			while(rs2.next())
			{
				
				count++;
				xml += "<st_name>"+rs2.getString("statementName")+"</st_name>";
				xml += "<st_Group>"+rs2.getString("StatementGroup")+"</st_Group>";
				xml += "<st_name_no>"+rs2.getString("STATEMENT_NO")+"</st_name_no>";
				xml += "<st_Group_no>"+rs2.getString("STATEMENT_GROUP_NO")+"</st_Group_no>";
				xml += "<office_name>"+rs2.getString("OFFICE_NAME")+"</office_name>";
				xml += "<officeid>"+rs2.getInt("OFFICE_ID")+"</officeid>";
				xml += "<amt_alloted>"+rs2.getInt("alloted_amt")+"</amt_alloted>";
				xml += "<amt_exp_sofar>"+rs2.getInt("amt_sofar")+"</amt_exp_sofar>";
				xml += "<balance>"+(rs2.getInt("alloted_amt")-rs2.getInt("amt_sofar"))+"</balance>";
				
				xml += "<amt_req>"+rs2.getInt("ADDL_BUDGET_REQ")+"</amt_req>";
				
				xml += "<headofaccount>"+rs2.getString("ACCOUNT_HEAD_CODE")+"</headofaccount>";

				
			}
			xml += "<count>"+count+"</count>";
			System.out.println("Count inn java "+count);
			}
			catch (Exception e) {
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
			
		}
	
		
		xml = xml + "</response>";
		out.write(xml);
		System.out.println(xml);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		Connection connection = null;
		LoadDriver load = new LoadDriver();
		response.setContentType(CONTENT_TYPE);
		//PrintWriter out = response.getWriter();
		connection = load.getConnection();
		PreparedStatement preparedStatement = null,ps=null,ps1=null;
		ResultSet rss=null;
		String strCommand = "";
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
		
		long l = System.currentTimeMillis();
		Timestamp ts = new Timestamp(l);
		
		
		if(strCommand.equalsIgnoreCase("add")){
			int count = 0,from_code=0,to_code=0;
			
			int stategroupNo = Integer.parseInt(request.getParameter("statementGp"));
			String statementNo = request.getParameter("cmbStatementName").trim();
			String financialYear = request.getParameter("cmbFinancialYear");	
			int officeId = Integer.parseInt(request.getParameter("txtRegionId"));
		//	double amountallocate=Double.parseDouble(request.getParameter("hoamountinrs"));
			String groupId=request.getParameter("groupId");
			String head_code = request.getParameter("head_code");
			int RecordCount = Integer.parseInt(request.getParameter("RecordCount"));
			//int additionalBudget = Integer.parseInt(request.getParameter("budgetrequired"));
		//	String reason = request.getParameter("txtReason");
			//System.out.println("Head code"+head_code);
			if(groupId.equalsIgnoreCase("H")){
				if(head_code.length()>6)
				{
					
					String[] from_to=head_code.split(" to ");
					from_code=Integer.parseInt(from_to[0]);
					to_code=Integer.parseInt(from_to[1]);
				}
				else
				{
					//System.out.println("else lenght single");
					from_code=Integer.parseInt(head_code);
					to_code=Integer.parseInt(head_code);
				}		
				
			}
			else
			{
				head_code="0";
				from_code=0;
				to_code=0;
				
			}
			
 
			
			String Head_of_Account[] = new String[RecordCount];
			String office_allot_amt[] = new String[RecordCount];
			String budget_req[] = new String[RecordCount];
			String amtexpsofar[] = new String[RecordCount];
			String balance[] = new String[RecordCount];
			String amt_allotted[] = new String[RecordCount];
			String st_group[] = new String[RecordCount];
			String st_group_no[] = new String[RecordCount];
			String officeid[] = new String[RecordCount];
			String officename[] = new String[RecordCount];
			
			
			
			
			/* Variables Declaration */
			int Head_of_Account2 = 0;
			int office_allot_amt1=0;
			int budget_req1=0;
			int amtexpsofar1=0;
			int balance1=0;
			int amt_allotted1=0;
			String st_group1="";
			int st_group_no1=0;
			int officeid1=0;
			String officename1="";
			
			try{

			System.out.println("RecordCount "+RecordCount);
			int updatrow=0,kk=0;
				for (int k = 0; k < RecordCount; k++) {

					/* Head of Account */
					
						Head_of_Account[k] = request.getParameter("head_acct" + k);
						if (Head_of_Account[k] != null) {
							if (Head_of_Account[k].equals("")) {
								Head_of_Account2 = 0;
							} else {
								Head_of_Account2 = Integer.parseInt(Head_of_Account[k]);
							}
						} else {
							Head_of_Account2 = 0;
						}
				
	
						office_allot_amt[k] = request.getParameter("office_allot_amt" + k);
						if (office_allot_amt[k] != null) {
							if (office_allot_amt[k].equals("")) {
								office_allot_amt1 = 0;
							} else {
								office_allot_amt1 = Integer.parseInt(office_allot_amt[k]);
							}
						} else {
							office_allot_amt1 = 0;
						}
						
						budget_req[k] = request.getParameter("budget_req" + k);
						if (budget_req[k] != null) {
							if (budget_req[k].equals("")) {
								budget_req1 = 0;
							} else {
								budget_req1 = Integer.parseInt(budget_req[k]);
							}
						} else {
							budget_req1 = 0;
						}
						
						
						
						amtexpsofar[k] = request.getParameter("amtexpsofar" + k);
						if (amtexpsofar[k] != null) {
							if (amtexpsofar[k].equals("")) {
								amtexpsofar1 = 0;
							} else {
								amtexpsofar1 = Integer.parseInt(amtexpsofar[k]);
							}
						} else {
							amtexpsofar1 = 0;
						}
						
						balance[k] = request.getParameter("balance" + k);
						if (balance[k] != null) {
							if (balance[k].equals("")) {
								balance1 = 0;
							} else {
								balance1 = Integer.parseInt(balance[k]);
							}
						} else {
							balance1 = 0;
						}
						
						amt_allotted[k] = request.getParameter("amt_allotted" + k);
						if (amt_allotted[k] != null) {
							if (amt_allotted[k].equals("")) {
								amt_allotted1 = 0;
							} else {
								amt_allotted1 = Integer.parseInt(amt_allotted[k]);
							}
						} else {
							amt_allotted1 = 0;
						}
						
						officeid[k] = request.getParameter("officeid" + k);
						if (officeid[k] != null) {
							if (officeid[k].equals("")) {
								officeid1 = 0;
							} else {
								officeid1 = Integer.parseInt(officeid[k]);
							}
						} else {
							officeid1 = 0;
						}
					/*System.out.println("off allt " +office_allot_amt1);
					System.out.println("ts   "+ts);
					System.out.println("officeid1  "+officeid1);
					System.out.println("financialYear "+financialYear);
					System.out.println("statementNo "+statementNo);
					System.out.println("stategroupNo "+stategroupNo);
					System.out.println(" amt_allotted1 "+ amt_allotted1);
					System.out.println("budget_req1 "+budget_req1);
					System.out.println("Head_of_Account2 "+Head_of_Account2);		*/	
					
					ps = connection.prepareStatement("update FAS_ADDTIONAL_BUDGET_REQ set ADDL_BUDGET_ALLOTTED=?,ADDL_BUDGET_ALLOTTED_DATE=? where ACCOUNTING_FOR_OFFICE_ID=? AND FINANCIAL_YEAR=? AND STATEMENT_NO=? AND STATEMENT_GROUP_NO=? AND ADDL_BUDGET_REQ=? AND ACCOUNT_HEAD_CODE=?");//AND AMOUNT_ALLOTTED=? 
				
					ps.setInt(1, office_allot_amt1);
					ps.setTimestamp(2, ts);
					ps.setInt(3, officeid1);
					ps.setString(4, financialYear);
					ps.setString(5, statementNo);
					ps.setInt(6, stategroupNo);
					ps.setInt(7, budget_req1);
					ps.setInt(8, Head_of_Account2);
					//ps.setInt(9, amt_allotted1);
					kk = ps.executeUpdate();
					
					updatrow++;
					System.out.println(" kk  updatrow "+kk+"   "+updatrow);
				}
			//if (kk>0) 
				if((RecordCount==updatrow)&&(kk>0))
			{
					System.out.println("ifff  ");
				connection.commit();
				sendMessage(response,"Records Saved Successfully ..... ", "ok","Civil_Budget_Additional_Re_appropriation.jsp");
			}
			else
			{
				connection.rollback();
				sendMessage(response,"Records Not Inserted . ","ok", "Civil_Budget_Additional_Re_appropriation.jsp");	
			}
				}catch(Exception ee){
					System.out.println("Excepp"+ee);
				}
							
			/*try{
				int regionId=0;
				String getRegionid="select REGION_OFFICE_ID from COM_MST_ALL_OFFICES_VIEW where OFFICE_ID="+officeId;
				ps=connection.prepareStatement(getRegionid);
				System.out.println("get redio "+getRegionid);
				rss=ps.executeQuery();
				while(rss.next()){
					regionId=rss.getInt("REGION_OFFICE_ID");
				}
		     String check="select 'X' from FAS_ADDTIONAL_BUDGET_REQ where STATEMENT_NO=? and STATEMENT_GROUP_NO=? and FINANCIAL_YEAR=? and ACCOUNTING_FOR_OFFICE_ID=? and ACCOUNT_HEAD_CODE=?";
				ps1=connection.prepareStatement(check);
				ps1.setString(1, statementNo);
				ps1.setInt(2, stategroupNo);
				ps1.setString(3, financialYear);
				ps1.setInt(4, officeId);
				ps1.setString(5, head_code);
				int cc=ps1.executeUpdate();
				if(cc>0){
					connection.rollback();	
					sendMessage(response,"Record Already Inserted ","ok");
				}else{
					//AMOUNT_ALLOTTED_OFFICE

					try {						
						
						preparedStatement = connection.prepareStatement("insert into FAS_ADDTIONAL_BUDGET_REQ(STATEMENT_NO,STATEMENT_GROUP_NO,FINANCIAL_YEAR,ACCOUNTING_FOR_OFFICE_ID,ACCOUNT_HEAD_CODE,AMOUNT_ALLOTTED,UPDATED_BY_USERID,UPDATED_DATE,FROM_ACC_HD_CODE,TO_ACC_HD_CODE,REGION_OFFICE_ID,ADDL_BUDGET_REQ,REASON)values(?,?,?,?,?,?,?,SYSTIMESTAMP,?,?,?,?,?)");
						preparedStatement.setString(1, statementNo);
						preparedStatement.setInt(2, stategroupNo);
						preparedStatement.setString(3, financialYear);
						preparedStatement.setInt(4, officeId);
						preparedStatement.setString(5, head_code);
						preparedStatement.setDouble(6,amountallocate);
						preparedStatement.setString(7, userid);
						preparedStatement.setInt(8, from_code);
						preparedStatement.setInt(9, to_code);
						preparedStatement.setInt(10, regionId);
						preparedStatement.setInt(11, additionalBudget);
						preparedStatement.setString(12, reason);						
						count = preparedStatement.executeUpdate();
						System.out.println("count:-addd method civil budget additional---" + count);
						
						
					} catch (Exception e) {
						
						connection.rollback();
                         sendMessage(response,"Failed to Add Data","ok");  
					}
				
						
          
          if(count>0)
			{
				connection.commit();
				sendMessage(response,"Record Inserted successfully ","ok");
			}
			else
			{
				connection.rollback();	
				sendMessage(response,"Record Not Inserted  ","ok");
			}
				}
				
				
				
				
				
				
				
			}
			catch(Exception ee)
			{
				System.out.println("ee in grid:::"+ee);
			}*/
			
			
			
		}
		
		
		
	}
/*	 private void sendMessage(HttpServletResponse response,String msg,String bType)
	    {
	        try
	        {
	        	System.out.println("message...."+msg+"button type "+bType);
	                  String url="org/Library/jsps/MessengerOkBack.jsp?message=" +msg+ "&button=" + bType;
	                  response.sendRedirect(url);
	        }
	        catch(IOException e)
	        {
	        }
	    }*/
	private void sendMessage(HttpServletResponse response, String msg,
			String bType, String jsp) {
		try {
			String url = "org/FAS/FAS1/CivilBudget/jsps/MessengerOkBack.jsp?message="
					+ msg + "&button=" + bType + "&jspname=" + jsp;
			response.sendRedirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}