package Servlets.FAS.FAS1.BillRegister.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
		System.out.println("this is post::::");
		Connection connection = null;
		Statement statement = null;
		CallableStatement cs1,jur_cs=null;
		ResultSet results = null;
		ResultSet results2;
		ResultSet rs = null,resl=null,resl_set=null;
		ResultSet rs2 = null,rs_one=null;
		PreparedStatement ps = null,ps_one=null,ps_jrl=null;
		PreparedStatement ps1 = null,prep=null,prep_sta=null;
		PreparedStatement ps2 = null;
		int cashbookYear = 0;
		String cashbookMonth = null;
		int unitid = 0,counts=0;
		String unitname = "",jour_tran="",jou_bill_no="";
		int accid = 0;
		 Calendar c;
		 Date refDate=null;   int S_NO=0,count_jrl=0;

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
		 String update_user=(String)session.getAttribute("UserId");
			//String userid = (String) session.getAttribute("UserId");                
         int errcode=0;
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

		if (strCommand.equalsIgnoreCase("getBillMajorType")) {

			xml = xml + "<response><command>getBillMajorType</command>";
			xml = xml + "<empid>" + empid + "</empid>";
			xml = xml + "<empName>" + empName + "</empName>";
			
			try {
				String su = "select BILL_MAJOR_TYPE_CODE,BILL_MAJOR_TYPE_DESC from FAS_BILL_MAJOR_TYPES WHERE status='L' and BILL_MAJOR_TYPE_CODE=2 order by bill_major_type_code";
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
				
				String su1 = "SELECT b.office_id, "+
							 " b.office_name,a.empname "+
							 " FROM "+
							 " (SELECT p.employee_id,(select e.employee_initial||'.'||e.employee_name from hrm_mst_employees e where e.employee_id=p.employee_id)as empname, "+
					"    office_id "+
					" 	  FROM HRM_EMP_CURRENT_POSTING p "+
					" 	  WHERE employee_id= "+empid+
					" 	  )a "+
					" 	LEFT OUTER JOIN "+
					" 	  (SELECT office_id,office_name FROM COM_MST_OFFICES "+
					" 	  )b "+
					" 	ON a.office_id=b.office_id";
				System.out.println("su1:::"+su1);
				ps = connection.prepareStatement(su1);
				//ps.setInt(1, empid);
				rs = ps.executeQuery();
				while (rs.next()) {
					xml = xml + "<OfficeID>" + rs.getInt("OFFICE_ID")+ "</OfficeID>";
					xml = xml + "<OfficeName>" + rs.getString("OFFICE_NAME")+ "</OfficeName>";
					xml = xml + "<empid>" +empid+ "</empid>";
					xml = xml + "<empname>" + rs.getString("empname")+ "</empname>";
					System.out.println(rs.getInt("OFFICE_ID"));
					System.out.println(rs.getString("OFFICE_NAME"));
				}
				xml = xml + "<flag>success</flag>";
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}
		} 
		else if (strCommand.equalsIgnoreCase("getempname")) {

			xml = xml + "<response><command>getempname</command>";
			int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
			int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
			try {
				
				String su1="select m.EMPLOYEE_ID, (select EMPLOYEE_NAME ||' '||EMPLOYEE_INITIAL as emp from HRM_MST_EMPLOYEES s " +
						"where s.EMPLOYEE_ID=m.EMPLOYEE_ID)as empname,(SELECT o.office_name "+
					 " FROM com_mst_offices o "+
					"  WHERE o.office_id= "+
					"   (SELECT OFFICE_ID "+
					"   FROM hrm_emp_current_posting g "+
					"   WHERE g.employee_id=m.EMPLOYEE_ID "+
					"   ) "+
					" )officename "+
						" from FAS_DRAWING_OFFICER_MST m where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and m.status='L'";
				
				System.out.println("su1:::"+su1);
				ps = connection.prepareStatement(su1);
				//ps.setInt(1, empid);
				rs = ps.executeQuery();
				if (rs.next()) {
					xml = xml + "<empid>" + rs.getInt("EMPLOYEE_ID")+ "</empid>";
					xml = xml + "<empname>" + rs.getString("empname")+ "</empname>";
					xml = xml + "<officename>" + rs.getString("officename")+ "</officename>";
					
				}
				xml = xml + "<flag>success</flag>";
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}
		}
		else if (strCommand.equalsIgnoreCase("getempname_off")) {

			xml = xml + "<response><command>getempname_off</command>";
			int txtEmpID_mas = Integer.parseInt(request.getParameter("txtEmpID_mas"));
			int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
			try {
				
				String su1="SELECT b.office_id, "+
						  " b.office_name,a.empname "+
					" FROM "+
					"   (SELECT p.employee_id,(select e.employee_initial||'.'||e.employee_name from hrm_mst_employees e where e.employee_id=p.employee_id)as empname, "+
					"     office_id "+
					"   FROM HRM_EMP_CURRENT_POSTING p "+
					"   WHERE employee_id= "+txtEmpID_mas+
					"   and OFFICE_ID= "+cmbOffice_code+"   )a "+
					" LEFT OUTER JOIN "+
					"   (SELECT office_id,office_name FROM COM_MST_OFFICES "+
					"   )b "+
					" ON a.office_id=b.office_id";
				
				System.out.println("su1:::"+su1);
				ps = connection.prepareStatement(su1);
				//ps.setInt(1, empid);
				rs = ps.executeQuery();
				if (rs.next()) {
					xml = xml + "<office_id>" + rs.getInt("office_id")+ "</office_id>";
					xml = xml + "<officename>" + rs.getString("office_name")+ "</officename>";
					xml = xml + "<empid>" + txtEmpID_mas+ "</empid>";
					xml = xml + "<empname>" + rs.getString("empname")+ "</empname>";
					xml = xml + "<flag>success</flag>";
				}
				else
				{
					xml = xml + "<flag>failure</flag>";
				}
				
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}
		}
		else if (strCommand.equalsIgnoreCase("getOffice")) {
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
					prep=connection.prepareStatement("select EMPLOYEE_NAME,EMPLOYEE_ID from HRM_MST_EMPLOYEES where EMPLOYEE_ID=?");
					prep.setInt(1, txtEmpID_mas);
					ResultSet res=prep.executeQuery();
					while(res.next())
					{
						xml = xml + "<EMPLOYEE_ID>" + res.getInt("EMPLOYEE_ID")+ "</EMPLOYEE_ID>";
							xml = xml + "<EMPLOYEE_NAME>" + res.getString("EMPLOYEE_NAME")+ "</EMPLOYEE_NAME>";
					}
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

		}
		//loadHead
		
		else if (strCommand.equalsIgnoreCase("loadHead")) {
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
				"   WHERE Bill_Major_Type_Code="+cboBillMajorType+"   AND Bill_Minor_Type_Code  ="+cboBillMinorType+"   AND BILL_SUB_TYPE_CODE    ="+cboBillSubType+" and STATUS='L')";
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
			

		}
		//load budget
		
		/*else if (strCommand.equalsIgnoreCase("budgetProv")) {
			xml = xml + "<response><command>budgetProv</command>";
			int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
			int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
			int txtAcc_HeadCode = Integer.parseInt(request.getParameter("txtAcc_HeadCode"));
			int sCount=0;
			try {

				String su1 = "Select Current_Year_Budget_Allotted,BUDGET_SOFAR_SPENT,(CURRENT_YEAR_BUDGET_ALLOTTED-BUDGET_SOFAR_SPENT)as total_bud From Com_Budget_Details Where Accounting_Unit_Id="+cmbAcc_UnitCode+" and Accounting_For_Office_Id="+cmbOffice_code+" and ACCOUNT_HEAD_CODE="+txtAcc_HeadCode;
				ps1 = connection.prepareStatement(su1);
				results = ps1.executeQuery();
				while(results.next()) {
					sCount++;
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
			

		}*/
		//acc desc
		else if (strCommand.equalsIgnoreCase("getAccDesc")) {
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
			

		}
		//getProceeding no
		else if (strCommand.equalsIgnoreCase("getProceedingNo")) {
			xml = xml + "<response><command>getProceedingNo</command>";
			
			int cboBillMajorType = Integer.parseInt(request.getParameter("cboBillMajorType"));
			int cboBillMinorType = Integer.parseInt(request.getParameter("cboBillMinorType"));
			int cboBillSubType = Integer.parseInt(request.getParameter("cboBillSubType"));
			int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
			
			int Proceeding_Year = Integer.parseInt(request.getParameter("Proceeding_Year"));
			int Proceeding_Month = Integer.parseInt(request.getParameter("Proceeding_Month"));
			int sCount=0;
			try {
				String su1 ="";
///Modify joe Nov 13
				if(cboBillMajorType==2){
					if(cboBillMinorType==2){
						if(cboBillSubType==1){
							System.out.println("comes here:::");
				 su1 = "SELECT distinct HRMS_SANCTION_ID,Sanction_Proc_No From SLS_SANCTIONS_BILLS_LINK_MST1 Where "
						+ " Bill_Major_Type_Code ="
						+ cboBillMajorType
						+ " And Bill_Minor_Type_Code   ="
						+ cboBillMinorType
						+ " AND "
						+ " BILL_SUB_TYPE_CODE     ="
						+ cboBillSubType
						+ " And sanction_proc_office_id="
						+ cmbOffice_code
						+ " And Hrms_Sanction_Id Not In (Select SANCTION_PROC_NO "
						+ " From Fas_Bill_Register_Master  where ACCOUNTING_UNIT_OFFICE_ID="
						+ cmbOffice_code
						+ " and STATUS='L') and extract (year from sanction_proc_date)="
						+ Proceeding_Year
						+ " and extract (month from sanction_proc_date)="
						+ Proceeding_Month + " order by HRMS_SANCTION_ID";
						}
					}else{

						 su1 = "SELECT distinct HRMS_SANCTION_ID,Sanction_Proc_No From Hrm_Sanctions_Bills_Link_Mst Where "
								+ " Bill_Major_Type_Code ="
								+ cboBillMajorType
								+ " And Bill_Minor_Type_Code   ="
								+ cboBillMinorType
								+ " AND "
								+ " BILL_SUB_TYPE_CODE     ="
								+ cboBillSubType
								//+ " And sanction_proc_office_id="
								+ " And OFFICE_ID="
								
								+ cmbOffice_code
								+ " And Hrms_Sanction_Id::varchar Not In (Select SANCTION_PROC_NO "
								+ " From Fas_Bill_Register_Master  where ACCOUNTING_UNIT_OFFICE_ID="
								+ cmbOffice_code
								+ " and STATUS='L') and extract (year from sanction_proc_date)="
								+ Proceeding_Year
								+ " and extract (month from sanction_proc_date)="
								+ Proceeding_Month + " order by HRMS_SANCTION_ID";
					
					}
				}else{
					
					System.out.println("Error :::");
				}
				System.out.println("su1:::"+su1);
				ps1 = connection.prepareStatement(su1);
				//ps1.setInt(1, cboBillMajorType);
				results = ps1.executeQuery();
				while(results.next()) {
					sCount++;
					
						xml = xml + "<procNo>"+results.getString("Sanction_Proc_No")+"</procNo>";
						xml = xml + "<sanc_id>"+results.getString("HRMS_SANCTION_ID")+"</sanc_id>";
					 
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
			

		}
		//details
		else if (strCommand.equalsIgnoreCase("getProceedingDetails")) {
			
			xml = xml + "<response><command>getProceedingDetails</command>";
			int cboBillSubType = Integer.parseInt(request.getParameter("cboBillSubType"));
			int proValue = Integer.parseInt(request.getParameter("proValue"));
			int majortype = Integer.parseInt(request.getParameter("cboBillMajorType"));
			int minortype = Integer.parseInt(request.getParameter("cboBillMinorType"));
			int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
			int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
			
			String procno=request.getParameter("txtProceedingNo");
			System.out.println("Pro no === "+procno);
			int prod=0,minor2=0;
			int count=0;
			int notgpf=0;
			String su1="";
			try {
				if(majortype==2)
				{
					if(minortype==1)
					{
						minor2=0;
						notgpf=0;
						su1="SELECT * " +
						" FROM " +
						"  (SELECT HRMS_SANCTION_ID, " +
						"    EMPLOYEE_ID, " +
						"    SANCTION_PROC_DATE, " +
						"    SANCTIONED_AMOUNT, " +
						"    EMPLOYEE_NAME, " +
						"    PAYMENT_HEAD_OF_AC, " +
						"    ACCOUNT_HEAD_DESC, " +
						"    ACCOUNT_HEAD_CODE, " +
						"    PAYMENT_AMOUNT , " +
						"    PROCESS_FLOW_ID,Sanction_Proc_Office_Id,(select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=Sanction_Proc_Office_Id)as off_name " +
						"  FROM " +
						"    (SELECT a.EMPLOYEE_ID, " +
						"      TO_CHAR(SANCTION_PROC_DATE,'dd/mm/yyyy')AS SANCTION_PROC_DATE, " +
						"      a.SANCTIONED_AMOUNT, " +
						"      a.HRMS_SANCTION_ID, " +
						"      a.PROCESS_FLOW_ID, " +
						"      b.EMPLOYEE_NAME, " +
						"      c.PAYMENT_HEAD_OF_AC , " +
						"      c.PAYMENT_AMOUNT,a.SANCTION_PROC_OFFICE_ID " +
						"    FROM HRM_SANCTIONS_BILLS_LINK_MST a " +
						"    INNER JOIN HRM_MST_EMPLOYEES b " +
						"    ON a.EMPLOYEE_ID =b.EMPLOYEE_ID " +
						"    INNER JOIN HRM_SANCTIONS_BILLS_LINK_TRN c " +
						"    ON a.HRMS_SANCTION_ID   =c.hrms_sanction_id and a.EMPLOYEE_ID=c.EMPLOYEE_ID " +
						"    WHERE a.HRMS_SANCTION_ID= " +proValue+
						"    ) mst " +
						"  LEFT OUTER JOIN " +
						"    ( SELECT ACCOUNT_HEAD_CODE , ACCOUNT_HEAD_DESC FROM COM_MST_ACCOUNT_HEADS " +
						"    ) acc " +
						"  ON acc.ACCOUNT_HEAD_CODE=mst.PAYMENT_HEAD_OF_AC " +
						"  ) ccc " ;
						
						
						
						
						
					}else if(minortype==2){ 
						Float total_amt=0.0f;
						String empname="";
						su1="SELECT * " +
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
						"    PROCESS_FLOW_ID, " +
						"    Sanction_Proc_Office_Id, " +
						"    (SELECT OFFICE_NAME " +
						"    FROM COM_MST_OFFICES " +
						"    WHERE OFFICE_ID=Sanction_Proc_Office_Id " +
						"    )AS off_name " +
						"  FROM " +
						"    (SELECT a.EMPLOYEE_ID, " +
						"      TO_CHAR(SANCTION_PROC_DATE,'dd/mm/yyyy')AS SANCTION_PROC_DATE, " +
						"      a.SANCTIONED_AMOUNT, " +
						"      a.HRMS_SANCTION_ID, " +
						"      a.PROCESS_FLOW_ID, " +
						"      b.EMPLOYEE_NAME, " +
						"      c.PAYMENT_HEAD_OF_AC AS PAYMENT_HEAD_OF_AC , " +
						"      c.PAYMENT_AMOUNT     AS PAYMENT_AMOUNT, " +
						"      a.SANCTION_PROC_OFFICE_ID " +
						"    FROM SLS_SANCTIONS_BILLS_LINK_MST1 a " +
						"    INNER JOIN HRM_MST_EMPLOYEES b " +
						"    ON a.EMPLOYEE_ID =b.EMPLOYEE_ID " +
						"    INNER JOIN SLS_SANCTIONS_BILLS_LINK_TRN1 c " +
						"    ON a.HRMS_SANCTION_ID   =c.HRMS_SANCTION_ID and a.EMPLOYEE_ID=c.EMPLOYEE_ID " +
						"    WHERE a.HRMS_SANCTION_ID= " +proValue+
						"    ) mst " +
						"  LEFT OUTER JOIN " +
						"    (SELECT ACCOUNT_HEAD_CODE , ACCOUNT_HEAD_DESC FROM COM_MST_ACCOUNT_HEADS " +
						"    ) acc " +
						"  ON acc.ACCOUNT_HEAD_CODE=mst.PAYMENT_HEAD_OF_AC " +
						"  ) ccc" ;
						
						String s1="SELECT 7 AS emp,'Employees' as emp_des, "+
						 " e.EMPLOYEE_ID as id_one, "+
							"  (select o.employee_name||'-'||o.employee_initial as empna from hrm_mst_employees o where o.EMPLOYEE_ID=e.EMPLOYEE_ID)as empname, "+
							" SANCTIONED_AMOUNT, "+
							" 550310 AS head_code, "+
							" (select h.account_head_desc from com_mst_account_heads h where h.account_head_code=550310)as headdes "+
							" FROM SLS_SANCTIONS_BILLS_LINK_MST1 e "+
							" WHERE HRMS_SANCTION_ID      = "+proValue+
							" AND ACCOUNTING_UNIT_ID      = "+cmbAcc_UnitCode+
							" AND ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code;
            	  System.out.println(s1);
						prep_sta=connection.prepareStatement(s1);
            	   resl_set=prep_sta.executeQuery();
            	   while(resl_set.next())
            	   {
            		   total_amt=total_amt+resl_set.getFloat("SANCTIONED_AMOUNT");
            		   xml = xml + "<EMPLOYEE_ID>"+resl_set.getInt("id_one")+"</EMPLOYEE_ID>";
   					xml = xml + "<EMPLOYEE_NAME>"+resl_set.getString("empname")+"</EMPLOYEE_NAME>";
   					xml = xml + "<ACCOUNT_HEAD_CODE>"+resl_set.getInt("head_code")+"</ACCOUNT_HEAD_CODE>";
   					xml = xml + "<ACCOUNT_HEAD_DESC>"+resl_set.getString("headdes")+"</ACCOUNT_HEAD_DESC>";	
   					xml = xml + "<PAYMENT_AMOUNT>"+resl_set.getInt("SANCTIONED_AMOUNT")+"</PAYMENT_AMOUNT>";
            		minor2++;
            	   }
            	   xml = xml + "<total_amt>"+total_amt+"</total_amt>";	
            	   
            	   System.out.println("xml::::"+xml);
					}
				}
				else{
					minor2=0;
					notgpf++;
				su1="SELECT * " +
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
						"    PROCESS_FLOW_ID,Sanction_Proc_Office_Id,(select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=Sanction_Proc_Office_Id)as off_name " +
						"  FROM " +
						"    (SELECT a.EMPLOYEE_ID, " +
						"      TO_CHAR(SANCTION_PROC_DATE,'dd/mm/yyyy')AS SANCTION_PROC_DATE, " +
						"      a.SANCTIONED_AMOUNT, " +
						"      a.HRMS_SANCTION_ID, " +
						"      a.PROCESS_FLOW_ID, " +
						"      b.EMPLOYEE_NAME, " +
						"      c.PAYMENT_HEAD_OF_AC , " +
						"      c.PAYMENT_AMOUNT,a.SANCTION_PROC_OFFICE_ID " +
						"    FROM HRM_SANCTIONS_BILLS_LINK_MST a " +
						"    INNER JOIN HRM_MST_EMPLOYEES b " +
						"    ON a.EMPLOYEE_ID =b.EMPLOYEE_ID " +
						"    INNER JOIN HRM_SANCTIONS_BILLS_LINK_TRN c " +
						"    ON a.HRMS_SANCTION_ID   =c.hrms_sanction_id " +
						"    WHERE a.HRMS_SANCTION_ID= " +proValue+
						"    ) mst " +
						"  LEFT OUTER JOIN " +
						"    ( SELECT ACCOUNT_HEAD_CODE , ACCOUNT_HEAD_DESC FROM COM_MST_ACCOUNT_HEADS " +
						"    ) acc " +
						"  ON acc.ACCOUNT_HEAD_CODE=mst.PAYMENT_HEAD_OF_AC " +
						"  ) ccc " +
						"LEFT OUTER JOIN " +
						"  (SELECT ACCOUNT_HEAD_CODE,ACCOUNTING_FOR_OFFICE_ID, " +
						"    CURRENT_YEAR_BUDGET_ALLOTTED , " +
						"    BUDGET_SOFAR_SPENT, " +
						"    REF_NO, " +
						"    TO_CHAR(REF_DATE,'dd/mm/yyyy')AS REF_DATE " +
						"  FROM COM_BUDGET_DETAILS " +
						"  ) bud " +
						"ON bud.ACCOUNT_HEAD_CODE=ccc.ACCOUNT_HEAD_CODE and bud.ACCOUNTING_FOR_OFFICE_ID=ccc.Sanction_Proc_Office_Id";
				}
				System.out.println(su1);
				ps1 = connection.prepareStatement(su1);
			//	ps1.setInt(1, proValue);
				results = ps1.executeQuery();
				System.out.println("Results   === 111 ");
				while(results.next()) {
					prod++;
						xml = xml + "<PROCESS_FLOW_ID>"+results.getString("PROCESS_FLOW_ID")+"</PROCESS_FLOW_ID>";
						xml = xml + "<empid>"+results.getInt("EMPLOYEE_ID")+"</empid>";	
						xml = xml + "<procdate>"+results.getString("SANCTION_PROC_DATE")+"</procdate>";
						xml = xml + "<sanAmt>"+results.getString("Sanctioned_Amount")+"</sanAmt>";
						
						
						xml = xml + "<PAYMENT_HEAD_OF_AC>"+results.getString("PAYMENT_HEAD_OF_AC")+"</PAYMENT_HEAD_OF_AC>";
						
						if(minor2==0){
							xml = xml + "<EMPLOYEE_ID>"+results.getInt("EMPLOYEE_ID")+"</EMPLOYEE_ID>";
							xml = xml + "<EMPLOYEE_NAME>"+results.getString("EMPLOYEE_NAME")+"</EMPLOYEE_NAME>";
						xml = xml + "<ACCOUNT_HEAD_CODE>"+results.getInt("ACCOUNT_HEAD_CODE")+"</ACCOUNT_HEAD_CODE>";
						xml = xml + "<ACCOUNT_HEAD_DESC>"+results.getString("ACCOUNT_HEAD_DESC")+"</ACCOUNT_HEAD_DESC>";
						xml = xml + "<PAYMENT_AMOUNT>"+results.getInt("PAYMENT_AMOUNT")+"</PAYMENT_AMOUNT>";
						}
						if(notgpf!=0){
							xml = xml + "<gpf>"+"notgpf"+"</gpf>";
						xml = xml + "<CURRENT_YEAR_BUDGET_ALLOTTED>"+results.getString("CURRENT_YEAR_BUDGET_ALLOTTED")+"</CURRENT_YEAR_BUDGET_ALLOTTED>";
						xml = xml + "<BUDGET_SOFAR_SPENT>"+results.getString("BUDGET_SOFAR_SPENT")+"</BUDGET_SOFAR_SPENT>";
						xml = xml + "<REF_NO>"+results.getString("REF_NO")+"</REF_NO>";
						xml = xml + "<REF_DATE>"+results.getString("REF_DATE")+"</REF_DATE>";
						
						}
						else
						{
							xml = xml + "<gpf>"+"gpf"+"</gpf>";
						}
			
						
						xml = xml + "<HRMS_SANCTION_ID>"+results.getString("HRMS_SANCTION_ID")+"</HRMS_SANCTION_ID>";
						xml = xml + "<Office_Id>"+results.getInt("Sanction_Proc_Office_Id")+"</Office_Id>";
						xml = xml + "<off_name>"+results.getString("off_name")+"</off_name>";
				} 
				xml = xml + "<Pro_no>"+procno+"</Pro_no>";
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
		}
		
		else if (strCommand.equalsIgnoreCase("getBillMinorType")) {
			xml = xml + "<response><command>getBillMinorType</command>";
			int cboBillMajorType = Integer.parseInt(request
					.getParameter("cboBillMajorType"));
			int i2 = 1, i3 = 0;
			try {

				String su1 = "select BILL_MINOR_TYPE_CODE,BILL_MINOR_TYPE_DESC from FAS_BILL_MINOR_TYPES_MST where BILL_MAJOR_TYPE_CODE=? and BILL_MINOR_TYPE_CODE=1 and status='L'";
				ps1 = connection.prepareStatement(su1);
				ps1.setInt(1, cboBillMajorType);
				results = ps1.executeQuery();
				if (results.next()) {
					try {

						String su = "select BILL_MINOR_TYPE_CODE,BILL_MINOR_TYPE_DESC from FAS_BILL_MINOR_TYPES_MST where BILL_MAJOR_TYPE_CODE=? and BILL_MINOR_TYPE_CODE=1 and status='L'";
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
						.prepareStatement("Select max(BILL_NO) from FAS_BILL_REGISTER_MASTER where BILL_MAJOR_TYPE=?");
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

		} else if (strCommand.equalsIgnoreCase("getBillsubType")) {

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
		} 
		//Load loadPayeeCode_Desc
		else if (strCommand.equalsIgnoreCase("loadPayeeCode_Desc")) {

			int cboAcc_UnitCode = Integer.parseInt(request
					.getParameter("cboAcc_UnitCode"));
			int cboOffice_code = Integer.parseInt(request
					.getParameter("cboOffice_code"));
			int txtPayeeType = Integer.parseInt(request
					.getParameter("txtPayeeType"));
			
			
			xml = "<response><command>loadPayeeCode_Desc</command>";
			
			try {
				String su ="";
				if(txtPayeeType==1){
					/* dep.EMPLOYEE_ID AS payee_code ,
					    off.EMPLOYEE_NAME AS payee_codeName
					  FROM FAS_DRAWING_OFFICER_MST dep,
					    HRM_MST_EMPLOYEES OFF
					  WHERE dep.EMPLOYEE_ID=off.EMPLOYEE_ID*/
					su=" select  dep.EMPLOYEE_ID AS payee_code , "+
					    " off.EMPLOYEE_NAME AS payee_codeName  "+
					 " FROM FAS_DRAWING_OFFICER_MST dep, "+
					   " HRM_MST_EMPLOYEES OFF "+
					"  WHERE dep.EMPLOYEE_ID=off.EMPLOYEE_ID "+
					"  and ACCOUNTING_UNIT_ID="+cboAcc_UnitCode+
					" and ACCOUNTING_FOR_OFFICE_ID= "+cboOffice_code;
					
					
				}else if(txtPayeeType==2){
					su ="select "+
			  "  BANK_AC_NO      AS payee_code, "+		
			  " BANK_AC_TYPE_ID AS payee_codeName "+
			  " FROM FAS_MST_BANK_BALANCE "+
			  "WHERE status='Y' "+
			           " and ACCOUNTING_UNIT_ID="+cboAcc_UnitCode+
			           " and AC_OPERATIONAL_MODE_ID='OPR' ";
					
				}else if(txtPayeeType==3){
					
				su ="select "+
			   " OFFICE_ID   AS payee_code, "+
			   " OFFICE_NAME AS payee_codeName "+
			  " FROM COM_MST_OFFICES where "+
			  "   OFFICE_ID= "+cboOffice_code;
					
				
				}else if(txtPayeeType==4){
					
					su="select  "+
						   " FIRMS_ID   AS payee_code, "+
						   " FIRMS_NAME AS payee_codeName "+
						"  FROM COM_FIRMS_sl_MST "+
						" where ACCOUNTING_UNIT_ID="+cboAcc_UnitCode+
						" and ACCOUNTING_FOR_OFFICE_ID= "+cboOffice_code+
						" and STATUS='L'"	;	
					
				}else if(txtPayeeType==5){
					su=" select payee_code, "+
						" payee_codeName  "+
						" from  "+
						" (SELECT  "+
					"     PPO_NO      AS payee_code, "+
					"    to_char (PENSIONER_INITIAL "+
					"     || ' ' "+
					"     ||PENSIONER_NAME)     AS payee_codeName, "+
					"     PAYMENT_OFFICE_ID "+
					"   FROM HRM_PEN_MST_DETAILS "+
					"   union all "+
					"   SELECT  "+
					"      PPO_NO      AS payee_code,  "+ 
						     "      to_char(FPENSIONER_INITIAL "+
					"     || ' ' "+
					"     ||FPENSIONER_NAME)     AS payee_codeName , "+
					"     PAYMENT_OFFICE_ID  "+
					"   from HR_PEN_MST_FAMILY) "+
					"   where PAYMENT_OFFICE_ID="+cboOffice_code;
				
				}else if(txtPayeeType==6){
					
					su="  SELECT "+
					" USER_CATEGORY_ID      AS payee_code, "+
					" USER_NAME AS payee_codeName "+
					" FROM SEC_MST_OTHER_USERS_PROFILE WHERE USER_ID='"+update_user+"'";
					
				}else if(txtPayeeType==7){
					//su="";
					
					//employee
					
					
				}else if(txtPayeeType==8){
					
					su=" SELECT CONTRACTOR_ID AS payee_code, "+
					" CONTRACTOR_NAME AS payee_codeName "+
					" FROM PMS_CONT_REQUEST_REGN where OFFICE_ID="+cboOffice_code;
					
				}else if(txtPayeeType==9){
					
					su="  select SUPPLIER_ID   AS payee_code, "+
				   " SUPPLIER_NAME AS payee_codeName "+
				 " FROM COM_SUPPLIER_sl_MST "+
				 " where ACCOUNTING_UNIT_ID="+cboAcc_UnitCode+
				 " and ACCOUNTING_FOR_OFFICE_ID= "+cboOffice_code;
					
					
				}
				
				else //if(txtPayeeType==10)
				{
					su=" select  off.OTHER_DEPT_OFFICE_ALIAS_ID AS payee_code ,"+
					   " dep.OTHER_DEPT_NAME  "+
					   " || '-'  "+
					   " || off.OTHER_DEPT_OFFICE_NAME AS payee_codeName "+
					 " FROM HRM_MST_OTHER_DEPTS dep, "+
					 "   HRM_MST_OTHER_DEPT_OFFICES OFF "+
					"  WHERE dep.OTHER_DEPT_ID=off.OTHER_DEPT_ID ";
				}
				
				
				System.out.println("su "+su);
				ps = connection.prepareStatement(su);
				rs = ps.executeQuery();
				if(rs.next()){
					xml = xml + "<flag>success</flag>";
					   ResultSet rsss=ps.executeQuery();
						while (rsss.next()) {
							xml = xml + "<payeecode>"
									+ rsss.getString("payee_code")
									+ "</payeecode>";

							xml = xml + "<payeecodeDesc>"
									+ rsss.getString("payee_codeName")
									+ "</payeecodeDesc>";
							
							xml = xml + "<payeecodeDesc_Load>"
							+rsss.getString("payee_code") +"-"+ rsss.getString("payee_codeName")
							+ "</payeecodeDesc_Load>";
						}
					
				}else{
					xml = xml + "<flag>failure</flag>";
				}
			
				
				
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}
		}
		else if (strCommand.equalsIgnoreCase("loadPayyeedesc")) {

			xml = xml + "<response><command>loadPayyeedesc</command>";
			
			//int txtPayeeCode1 = Integer.parseInt(request.getParameter("txtPayeeCode"));
			String txtPayeeCode1 = request.getParameter("txtPayeeCode");
			int cboOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
			int maj = Integer.parseInt(request.getParameter("maj"));
			int min = Integer.parseInt(request.getParameter("min"));
			int subb = Integer.parseInt(request.getParameter("subb"));
			
			System.out.println("txtPayeeCode1 "+txtPayeeCode1);
			/*try {
				int desid=0;
				String ssi="select employee_id,OFFICE_ID,DESIGNATION_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID="+txtPayeeCode1+" and OFFICE_ID="+cboOffice_code;
				PreparedStatement pss=connection.prepareStatement(ssi);
				ResultSet rrss=pss.executeQuery();
				System.out.println(pss);
				if(rrss.next()){
					desid=rrss.getInt("DESIGNATION_ID");
					xml = xml + "<check>Having</check>";
				
				//String su1 = "select EMPLOYEE_NAME,EMPLOYEE_INITIAL,EMPLOYEE_ID from HRM_MST_EMPLOYEES where EMPLOYEE_ID="+txtPayeeCode1;

				
				String su1="select he.employee_name,he.employee_initial,he.employee_id,hd.designation_short_name,hd.designation from hrm_mst_employees he,hrm_mst_designations hd "+
				" where he.employee_id="+txtPayeeCode1+" and hd.designation_id= "+desid;
				ps1 = connection.prepareStatement(su1);
				//System.out.println(su1);
				results = ps1.executeQuery();

				if (results.next()) {

					System.out.println("enter");

					try {
						
						//String su = "select EMPLOYEE_NAME,EMPLOYEE_INITIAL,EMPLOYEE_ID from HRM_MST_EMPLOYEES where EMPLOYEE_ID="+txtPayeeCode1;
						String su = "select he.employee_name,he.employee_initial,he.employee_id,hd.designation_short_name,hd.designation from hrm_mst_employees he,hrm_mst_designations hd "+
						" where he.employee_id="+txtPayeeCode1+" and hd.designation_id= "+desid;
						ps = connection.prepareStatement(su);

						rs = ps.executeQuery();
						xml = xml + "<flag>success</flag>";
						while (rs.next()) {
							//System.out.println("while");
							
							String EMPLOYEE_NAME1 = rs.getString("EMPLOYEE_NAME");
							String EMPLOYEE_INITIAL1 = rs.getString("EMPLOYEE_INITIAL");
							String desgn=rs.getString("designation_short_name");
							int EMPLOYEE_ID = rs.getInt("EMPLOYEE_ID");

							xml = xml + "<EMPLOYEE_NAME>"
									+ EMPLOYEE_NAME1+"."+EMPLOYEE_INITIAL1+"("+desgn+")"
									+ "</EMPLOYEE_NAME>";

							xml = xml + "<EMPLOYEE_INITIAL>" + EMPLOYEE_INITIAL1
									+ "</EMPLOYEE_INITIAL>";

							xml = xml + "<EMPLOYEE_ID>" + EMPLOYEE_ID
									+ "</EMPLOYEE_ID>";
							xml = xml + "<desgn>" + desgn
							+ "</desgn>";
							
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
				}else{
					xml = xml + "<check>NoHaving</check>";
					
				}
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}*/
			String wrkqry="";
			String ss1="";
			if(maj==2 && min==1 && subb==8){
				
				ss1=" SELECT e.EMPLOYEE_ID, "+
				"  e.EMPLOYEE_NAME "+
				" ||'.'  "+
				" ||e.EMPLOYEE_INITIAL AS ENAME,  "+
				
				"   c.EMPLOYEE_STATUS_ID  "+
				"  FROM HRM_MST_EMPLOYEES e,  "+
				"  HRM_EMP_CURRENT_POSTING c  "+
				  
				"  WHERE  "+
				"  e.EMPLOYEE_ID       =c.EMPLOYEE_ID "+
				
				"  AND c.EMPLOYEE_ID       =  "+ txtPayeeCode1+  
				
				"  AND c.EMPLOYEE_STATUS_ID in ('SAN','VRS','DTH')  "+
				//"  (c.EMPLOYEE_STATUS_ID='SAN' or c.EMPLOYEE_STATUS_ID='VRS' or c.EMPLOYEE_STATUS_ID='DTH')  "+
				"  ORDER BY e.EMPLOYEE_NAME ";
				 
				//wrkqry= " AND (c.EMPLOYEE_STATUS_ID='SAN' or c.EMPLOYEE_STATUS_ID='VRS' or c.EMPLOYEE_STATUS_ID='DTH')\n" ;
			}else{
				//wrkqry= " AND c.EMPLOYEE_STATUS_ID='WKG'\n" ;
				ss1="SELECT e.EMPLOYEE_ID,\n" + 
				 "  e.EMPLOYEE_NAME\n" + 
				 "  ||'.'\n" + 
				 "  ||e.EMPLOYEE_INITIAL\n" + 
				 "  ||'-'\n" + 
				 "  || d.DESIGNATION AS ENAME,\n" + 
				 "  c.EMPLOYEE_STATUS_ID\n" + 
				 " FROM HRM_MST_EMPLOYEES e,\n" + 
				 "  HRM_EMP_CURRENT_POSTING c,\n" + 
				 "  HRM_MST_DESIGNATIONS d\n" + 
			//	 "  HRM_EMP_CONTROLLING_OFFICE f\n" + 
				 " WHERE c.DESIGNATION_ID  =d.DESIGNATION_ID\n" + 
				 " AND e.EMPLOYEE_ID       =c.EMPLOYEE_ID\n" + 
			//	 " and  c.EMPLOYEE_ID      = f.EMPLOYEE_ID\n" + 
				 " AND c.EMPLOYEE_ID       =" +txtPayeeCode1+ 
				 // Joan change on 07 April 2015
			//	 " and f.controlling_office_id=" +cboOffice_code+ 
				// " AND c.EMPLOYEE_STATUS_ID='WKG'\n" + 
				 " ORDER BY e.EMPLOYEE_NAME";
			}
			try {
				//HRM_EMP_CONTROLLING_OFFICE 
				// ps2=con.prepareStatement("select e.EMPLOYEE_ID,e.EMPLOYEE_NAME||'.'||e.EMPLOYEE_INITIAL||'-'|| d.DESIGNATION as ENAME,c.EMPLOYEE_STATUS_ID from HRM_MST_EMPLOYEES e,HRM_EMP_CURRENT_POSTING c,HRM_MST_DESIGNATIONS d where c.DESIGNATION_ID=d.DESIGNATION_ID  and e.EMPLOYEE_ID=c.EMPLOYEE_ID and c.EMPLOYEE_ID=? and c.OFFICE_ID=? and c.EMPLOYEE_STATUS_ID='WKG' order by e.EMPLOYEE_NAME ");
				/*String ss1="SELECT e.EMPLOYEE_ID,\n" + 
				 "  e.EMPLOYEE_NAME\n" + 
				 "  ||'.'\n" + 
				 "  ||e.EMPLOYEE_INITIAL\n" + 
				 "  ||'-'\n" + 
				 "  || d.DESIGNATION AS ENAME,\n" + 
				 "  c.EMPLOYEE_STATUS_ID\n" + 
				 " FROM HRM_MST_EMPLOYEES e,\n" + 
				 "  HRM_EMP_CURRENT_POSTING c,\n" + 
				 "  HRM_MST_DESIGNATIONS d,\n" + 
				 "  HRM_EMP_CONTROLLING_OFFICE f\n" + 
				 " WHERE c.DESIGNATION_ID  =d.DESIGNATION_ID\n" + 
				 " AND e.EMPLOYEE_ID       =c.EMPLOYEE_ID\n" + 
				 " and  c.EMPLOYEE_ID      = f.EMPLOYEE_ID\n" + 
				 " AND c.EMPLOYEE_ID       =" +txtPayeeCode1+ 
				 " and f.controlling_office_id=" +cboOffice_code+ wrkqry+
				// " AND c.EMPLOYEE_STATUS_ID='WKG'\n" + 
				 " ORDER BY e.EMPLOYEE_NAME";*/
				 ps2=connection.prepareStatement(ss1);
				 System.out.println("ss1 0 "+ss1);
				// ps2.setString(1,txtPayeeCode1);
				// ps2.setInt(2,cboOffice_code);
				 rs2=ps2.executeQuery();
				 
                 if(rs2.next())
                 {
                      	 xml=xml+"<cid>"+rs2.getInt("EMPLOYEE_ID")+"</cid><cname>"+rs2.getString("ENAME")+"</cname>";
                      	 xml=xml+"<state>"+rs2.getString("EMPLOYEE_STATUS_ID")+"</state>";
                      	 xml=xml+"<flag>success</flag>";
                 }
                 else
                      	 xml=xml+"<flag>failure</flag>";
         
                 ps2.close();
                 rs2.close();
    }
    catch(Exception e)
    {
    			 System.out.println("catch..HERE.in load emp cod in else part."+e);
    			 xml=xml+"<flag>failure</flag>";
    }
			
			
		}
		else if (strCommand.equalsIgnoreCase("loadPayeeType")) {

			xml = "<response><command>loadPayeeType</command>";

		
			try {
				ps = connection
						.prepareStatement("select payee_type_code,PAYEE_TYPE_DESC  from FAS_PAYEE_TYPES_MST  where STATUS='L' order by payee_type_code");
				

				rs2 = ps.executeQuery();
				if (rs2.next()) {
					try {
						ps1 = connection
								.prepareStatement("select payee_type_code,PAYEE_TYPE_DESC  from FAS_PAYEE_TYPES_MST  where STATUS='L' order by payee_type_code");
						

						results2 = ps1.executeQuery();
						xml = xml + "<flag1>success1</flag1>";

						while (results2.next()) {
							xml = xml + "<payee_type_code>"
									+ results2.getInt("payee_type_code")
									+ "</payee_type_code>";
							xml = xml + "<PAYEE_TYPE_DESC>"
							+ results2.getString("PAYEE_TYPE_DESC")
							+ "</PAYEE_TYPE_DESC>";
							
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

		} else if (strCommand.equalsIgnoreCase("InvoiceDetails")) {

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

		}
		else if (strCommand.equalsIgnoreCase("loadAccDesc")) {

			String txtaccountheadcode = request.getParameter("txtAcc_HeadCode");
			
			xml = "<response><command>loadAccDesc</command>";
			
			try {
				String su = "select ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_CODE='"+txtaccountheadcode+"'";
				ps = connection.prepareStatement(su);
				rs = ps.executeQuery();
				while (rs.next()) {
					xml = xml + "<ACCOUNT_HEAD_CODE>"
							+ rs.getInt("ACCOUNT_HEAD_CODE")
							+ "</ACCOUNT_HEAD_CODE>";

					xml = xml + "<ACCOUNT_HEAD_DESC>"
							+ rs.getString("ACCOUNT_HEAD_DESC")
							+ "</ACCOUNT_HEAD_DESC>";
				}
				
				xml = xml + "<flag>success</flag>";
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}
		}
		else if (strCommand.equalsIgnoreCase("loadAccHead")) {

			xml = xml + "<response><command>loadAccHead</command>";
		//	System.out.println("welocmme loadhead ");
			
		
			
			int cboBillMajorType = Integer.parseInt(request
					.getParameter("cboBillMajorType"));
			int cboBillMinorType = Integer.parseInt(request
					.getParameter("cboBillMinorType"));
			
			int cboBillSubType = Integer.parseInt(request
					.getParameter("cboBillSubType"));
			
			String finanical_yr = request.getParameter("finanical_yr");
			
			//int acchead = Integer.parseInt(request.getParameter("cboBillSubType"));
			
			try {

				/*String ssi="select ACCOUNT_HEAD_CODE from FAS_BILL_ACCOUNT_HEADS where FINANCIAL_YEAR ='"+finanical_yr+"' and BILL_MAJOR_TYPE_CODE= "+cboBillMajorType+
       " and BILL_MINOR_TYPE_CODE="+cboBillMinorType+" and BILL_SUB_TYPE_CODE="+cboBillSubType+" and STATUS='L' " ;*/
				
				/*String ssi="select ACCOUNT_HEAD_CODE from FAS_BILL_ACCOUNT_HEADS where  BILL_MAJOR_TYPE_CODE= "+cboBillMajorType+
			       " and BILL_MINOR_TYPE_CODE="+cboBillMinorType+" and BILL_SUB_TYPE_CODE="+cboBillSubType+" and STATUS='L' " ;
				*/
				String ssi="select AC_HEAD_CODE from HRM_GPF_DEBIT_TYPES where  BILL_MAJOR_TYPE_CODE= "+cboBillMajorType+
			       " and BILL_MINOR_TYPE_CODE="+cboBillMinorType+" and BILL_SUB_TYPE_CODE="+cboBillSubType ;
				
				
				PreparedStatement pss=connection.prepareStatement(ssi);
				ResultSet rrss=pss.executeQuery();
				System.out.println("load head "+ssi);
				xml = xml + "<flag>success</flag>";
				if (rrss.next()) {

					
						ps = connection.prepareStatement(ssi);

						rs = ps.executeQuery();
						
						while (rs.next()) {
												
							String headNo = rs.getString("AC_HEAD_CODE");
							
							
								xml = xml + "<accHead>"+headNo+"</accHead>";
							
							
						}
						ps.close();
						rs.close();
					
				} else {
					xml = xml + "<flag>Nohead</flag>";
				}
				
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
		}
		else if (strCommand.equalsIgnoreCase("calculateBudget")) {

			xml = xml + "<response><command>calculateBudget</command>";
			int cboAcc_UnitCode = Integer.parseInt(request
					.getParameter("cboAcc_UnitCode"));
			int cboOffice_code = Integer.parseInt(request
					.getParameter("cboOffice_code"));
			String txtaccountheadcode = request
					.getParameter("txtaccountheadcode");

			String year = request.getParameter("year");
			String year1 = request.getParameter("year1");
			
			String yr1=year1.substring(2, 4);
			String financialYear1 = (year + "-" + yr1);

			try {

				//String su1 = "select * from COM_BUDGET_DETAILS where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?";
			//	String su1 = "select * from COM_BUDGET_DETAILS where ACCOUNTING_UNIT_ID="+cboAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cboOffice_code+" and FINANCIAL_YEAR='"+financialYear1+"' and (FROM_ACC_HD_CODE='"+txtaccountheadcode+"' or  TO_ACC_HD_CODE='"+txtaccountheadcode+"')";
				
				String su1 = "select * from COM_BUDGET_DETAILS where ACCOUNTING_UNIT_ID="+cboAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cboOffice_code+" and FINANCIAL_YEAR='"+financialYear1+"'";
				System.out.println(cboAcc_UnitCode+" su1---"+su1);
				System.out.println(cboOffice_code);
				System.out.println(financialYear1);
				// System.out.println(txtaccountheadcode);

				ps1 = connection.prepareStatement(su1);
				/*ps1.setInt(1, cboAcc_UnitCode);
				ps1.setInt(2, cboOffice_code);
				ps1.setString(3, financialYear1);*/
				// ps1.setString(4, txtaccountheadcode);
				results = ps1.executeQuery();

				if (results.next()) {

					//System.out.println("enter");

					try {

					//	String su = "select * from COM_BUDGET_DETAILS where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?";
						//String su = "select CURRENT_YEAR_BUDGET_ALLOTTED,BUDGET_SOFAR_SPENT from COM_BUDGET_DETAILS where ACCOUNTING_UNIT_ID="+cboAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cboOffice_code+" and FINANCIAL_YEAR='"+financialYear1+"' and (FROM_ACC_HD_CODE='"+txtaccountheadcode+"' or  TO_ACC_HD_CODE='"+txtaccountheadcode+"')";
						String su = "select CURRENT_YEAR_BUDGET_ALLOTTED,BUDGET_SOFAR_SPENT from COM_BUDGET_DETAILS where ACCOUNTING_UNIT_ID="+cboAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cboOffice_code+" and FINANCIAL_YEAR='"+financialYear1+"' ";
						ps = connection.prepareStatement(su);
						/*ps.setInt(1, cboAcc_UnitCode);
						ps.setInt(2, cboOffice_code);
						ps.setString(3, financialYear1);*/
						// ps.setString(4, txtaccountheadcode);

						rs = ps.executeQuery();
						xml = xml + "<flag>success</flag>";
						while (rs.next()) {
							//System.out.println("while");
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
		} 
		else if (strCommand.equalsIgnoreCase("checkInvoice")) {
			xml = xml + "<response><command>checkInvoice</command>";
			
			int cboAcc_UnitCode = Integer.parseInt(request
					.getParameter("cboAcc_UnitCode"));
			int cboOffice_code = Integer.parseInt(request
					.getParameter("cboOffice_code"));
			int cboBillMajorType = Integer.parseInt(request
					.getParameter("cboBillMajorType"));
			int cboBillMinorType = Integer.parseInt(request
					.getParameter("cboBillMinorType")); 
			String cboBillSubType1 = request.getParameter("cboBillSubType");
			int cboBillSubType = Integer.parseInt(cboBillSubType1); 
			
			try {
				String suppp="";
				String su1 = "select SUPPORTING_INVOICE from fas_sup_inv_bill_types where accounting_unit_id="+cboAcc_UnitCode+
			" and accounting_for_office_id="+cboOffice_code+
			" and bill_major_type_code="+cboBillMajorType+
			" and bill_minor_type_code="+cboBillMinorType+
			" and bill_sub_type_code="+cboBillSubType+
			" and STATUS='L'";
				ps = connection.prepareStatement(su1);
				rs = ps.executeQuery();
				xml = xml + "<flag>success</flag>";
				while (rs.next()) {

					suppp=rs.getString("SUPPORTING_INVOICE");
							

					//xml = xml + "<OfficeName>" + rs.getString("OFFICE_NAME")
						//	+ "</OfficeName>";

				}
				ps.close();
				rs.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}

		} 
		else if(strCommand.equalsIgnoreCase("AddNew")) {

			xml = xml + "<response><command>AddNew</command>";
			int cboCashBook_Year=0,cboCashBook_Month=0;
			int txtBillNo =0;
			String qry_Str="",qry_str1="";
			   String sub_qry="",sub_tabMas="",sub_tabTrn="";
			String cboAcc_UnitCode1 = request.getParameter("cmbAcc_UnitCode");
			int cboAcc_UnitCode = Integer.parseInt(cboAcc_UnitCode1);

			String cboOffice_code1 = request.getParameter("cmbOffice_code");
			int cmbOffice_code = Integer.parseInt(cboOffice_code1);


			String cboBillMajorType1 = request.getParameter("cboBillMajorType");
			int cboBillMajorType = Integer.parseInt(cboBillMajorType1);

			String cboBillMinorType1 = request.getParameter("cboBillMinorType");
			int cboBillMinorType = Integer.parseInt(cboBillMinorType1);
          //  String hid_sanc=request.getParameter("hid_sanc");
			String cboBillSubType1 = request.getParameter("cboBillSubType");
			int cboBillSubType = Integer.parseInt(cboBillSubType1);

			String txtBillNo1 = request.getParameter("txtBillNo");
			txtBillNo = Integer.parseInt(txtBillNo1);
			System.out.println("txtBillNo::::"+txtBillNo);
			//xml = xml + "<BillNo>" + txtBillNo + "</BillNo>";  

			java.sql.Date BillDate = null;
			java.sql.Date ProceedingDate = null;
			java.util.GregorianCalendar c2;
			String[] sd = request.getParameter("txtBillDate").split("/");
			c2 = new java.util.GregorianCalendar(Integer.parseInt(sd[2]),
					Integer.parseInt(sd[1]) - 1, Integer.parseInt(sd[0]));
			java.util.Date d = c2.getTime();
			BillDate = new Date(d.getTime());
			ProceedingDate = new Date(d.getTime());

			   try{cboCashBook_Year=Integer.parseInt(sd[2]);}
	             catch(Exception e){System.out.println("exception"+e );}
	             System.out.println("txtCash_year "+cboCashBook_Year);
	             
	             try{cboCashBook_Month=Integer.parseInt(sd[1]);}
	             catch(Exception e){System.out.println("exception"+e );}
	             System.out.println("txtCash_Month_hid "+cboCashBook_Month);
			
//			String txtProceedingNo1 = request
//					.getParameter("txtManualProceedingNo");
			//int txtProceedingNo = Integer.parseInt(txtProceedingNo1);
			String txtProceedingNo1=request.getParameter("txtProceedingNo");
//			java.sql.Date ProceedingDate1 = null;
//			String ProceedingDate = null;
//			java.util.GregorianCalendar c22;
//			String[] sdd = request.getParameter("txtManualProceedingDate").split("/");
		
//			ProceedingDate=request.getParameter("txtManualProceedingDate");
//			c22 = new java.util.GregorianCalendar(Integer.parseInt(sdd[2]),
//					Integer.parseInt(sdd[1]) - 1, Integer.parseInt(sdd[0]));
//			java.util.Date dd = c22.getTime();
//			ProceedingDate = new Date(dd.getTime());
		/*	
			String sanc_id = request.getParameter("sanc_id");
			int sanc_one = Integer.parseInt(sanc_id);
			*/
			//
//	//		java.sql.Date ProceedingDate = null;
//			java.util.GregorianCalendar c22;
//			String[] sdd1 = request.getParameter("txtManualProceedingDate").split("/");
//			c22 = new java.util.GregorianCalendar(Integer.parseInt(sdd1[2]),
//					Integer.parseInt(sdd1[1]) - 1, Integer.parseInt(sdd1[0]));
//			java.util.Date d1 = c22.getTime();
//	//		ProceedingDate = new Date(d1.getTime());
//			
			//

			
		//	int txtNoofInvoices = Integer.parseInt(request.getParameter("txtNoofInvoices"));
			
			
		//	String txtInvoiceReceivedDate = request.getParameter("txtInvoiceReceivedDate");
			
			
			//String txtDeductedAmount = request.getParameter("txtDeductedAmount");
			
			String rdoMTC_70_Register = request
					.getParameter("rdoMTC_70_Register");

			String txtTotalBillAmount1 = request.getParameter("txtTotalBillAmount");
			//System.out.println("txtTotalBillAmount1 "+txtTotalBillAmount1);
			
			float txtTotalBillAmount = Float.parseFloat(txtTotalBillAmount1);
                        
                        String txtTotalSanctionedAmount1 = request.getParameter("txtTotalSanctionAmount");
                       // System.out.println("txtTotalSanctionedAmount1 "+txtTotalSanctionedAmount1);
         //               float txtTotalSanctionedAmount = Float.parseFloat(txtTotalSanctionedAmount1);
                        float txtTotalSanctionedAmount = txtTotalBillAmount;
                        
                        
                       // System.out.println("txtTotalSanctionedAmount  "+txtTotalSanctionedAmount);
                        
//			String txtAcc_HeadCode1 = request.getParameter("txtAcc_HeadCode");
//			int txtAcc_HeadCode = Integer.parseInt(txtAcc_HeadCode1);

		/*	int txtPayeeType =Integer.parseInt(request.getParameter("txtPayeeType"));

			String txtPayeeCode1 = request.getParameter("txtPayeeCode");
			String txtp[]=txtPayeeCode1.split("-");
			int txtPayeeCode =Integer.parseInt(txtp[0]);
                        System.out.println("code:::::"+txtPayeeCode);
                        */
         //   String txtPayableTo = request.getParameter("txtPayableTo");
                     //   String txtPayableTo1[] = request.getParameter("txtPayableTo").split("-");
                    //    int txtPayableTo = Integer.parseInt(txtPayableTo1[0]);

                        String txtEmpID_mas1 = request.getParameter("txtEmpID_mas");
			int txtEmpID_mas = Integer.parseInt(txtEmpID_mas1);

			//String cboOffice1 = request.getParameter("cboOffice");
			//int cboOffice = Integer.parseInt(cboOffice1);
			
			 int fin_year_from=0,fin_year_to=0;
             Date txtref_date=null;
             //////////////////////Financial year calculation/////////////////////////////////
             if(cboCashBook_Month>3)
             {
            	 	  fin_year_from=cboCashBook_Year;
            	 	  fin_year_to=cboCashBook_Year+1;
             }
             else
             {
            	 	  fin_year_from=cboCashBook_Year-1;
            	 	  fin_year_to=cboCashBook_Year;
             }
			try
            {
				int year1=0,year2=0;

				if(cboCashBook_Month>3)
				{
					year1=cboCashBook_Year;
					year2=(cboCashBook_Year+1);
				}
				else
				{
					
						year1=(cboCashBook_Year-1);
						year2=(cboCashBook_Year);
					
				}
				
                  int error_code = -1,bill_cnt=0;
               
                /*  if(cboBillMajorType==2 && cboBillMinorType==2 && cboBillSubType==1){
                	  sub_qry="  select nvl(max(BILL_NO),0) as cno from SLS_SANCTIONS_BILLS_LINK_MST1";
                	  PreparedStatement ps_cnt=connection.prepareStatement(sub_qry);
                	  ResultSet rs_cnt=ps_cnt.executeQuery();
                	  while(rs_cnt.next()){
                		  bill_cnt=rs_cnt.getInt("cno");
                	  }
                	  txtBillNo=bill_cnt+1;
                	  qry_Str=" SLS_SANCTIONS_BILLS_LINK_MST1 ";
                	  qry_str1=" SLS_SANCTIONS_BILLS_LINK_TRN ";
                  }
                  else{
                	  */
                  
              	if(cboCashBook_Year>2014){
					
              		System.out.println("inside cb_year2014");
              		/*if(cboCashBook_Month>3){
						sub_tabMas=" FAS_BILL_REGISTER_MASTERNEW ";
						sub_tabTrn=" FAS_BILL_REGISTER_TRANSACTIONW ";
						  cs1=connection.prepareCall("{call FAS_BILL_PROCEDURE_CURNEW(?,?,?,?) }");
					}else{
						sub_tabMas=" FAS_BILL_REGISTER_MASTER ";
						sub_tabTrn=" FAS_BILL_REGISTER_TRANSACTION ";
						System.out.println("NOW HERE CALLLING    ...... ");
						  cs1=connection.prepareCall("{call FAS_BILL_PROCEDURE_CUR(?,?,?,?) }");
					}*/
              		if(cboCashBook_Year==2015 && cboCashBook_Month<=3){
              			System.out.println("inside cb_year2015");
              			sub_tabMas=" FAS_BILL_REGISTER_MASTER ";
						sub_tabTrn=" FAS_BILL_REGISTER_TRANSACTION ";
						System.out.println("NOW HERE CALLLING    ...... ");
						  cs1=connection.prepareCall("call FAS_BILL_PROCEDURE_CUR(?::NUMERIC,?::NUMERIC,?::NUMERIC,?::NUMERIC) ");
						  
					}else{
						
						System.out.println("inside cb_year2015 else part");
						
						
						sub_tabMas=" FAS_BILL_REGISTER_MASTERNEW ";
						sub_tabTrn=" FAS_BILL_REGISTER_TRANSACTIONW ";
						  cs1=connection.prepareCall("call FAS_BILL_PROCEDURE_CURNEW(?::NUMERIC,?::NUMERIC,?::NUMERIC,?::NUMERIC) ");
					}
				}else{
					
					System.out.println("inside cb_year>2014");
					sub_tabMas=" FAS_BILL_REGISTER_MASTER ";
				    sub_tabTrn=" FAS_BILL_REGISTER_TRANSACTION ";
					  cs1=connection.prepareCall("call FAS_BILL_PROCEDURE_CUR(?::NUMERIC,?::NUMERIC,?::NUMERIC,?::NUMERIC) ");
				}
				
                  
                  
                 // cs1=connection.prepareCall("{call FAS_BILL_PROCEDURE_CUR(?,?,?,?) }");
                  cs1.setInt(1,cboAcc_UnitCode);
                  cs1.setInt(2,year1);
                  cs1.setInt(3,year2);
                  cs1.registerOutParameter(4, java.sql.Types.NUMERIC);
                  cs1.setInt(4, 0);
                  cs1.execute();
                  //error_code = cs1.getInt(4);
                  error_code = cs1.getBigDecimal(4).intValue();
                  System.out.println("bill no==>"+error_code);
                  if (error_code ==0)
                  {
                	  System.out.println("failure"); 
                  }
                  else
                  {
                	  System.out.println("success");
                  }
				
				txtBillNo=error_code;
				// qry_Str=" FAS_BILL_REGISTER_MASTER ";
               //  qry_str1=" FAS_BILL_REGISTER_TRANSACTION ";
            
            //      } 
            }
            catch(Exception e){System.out.println("exception"+e );
            
            }
            

			String mtxtRemarks = request.getParameter("mtxtRemarks");
			
			//System.out.println("qry_str ......  "+qry_Str+"    qry_str1...    "+qry_str1);
			
			System.out.println("sub_tabMas>>>>"+sub_tabMas);

			try {                    
				connection.setAutoCommit(false);
				ps1 = connection.prepareStatement("insert into   "+sub_tabMas+
						"(ACCOUNTING_UNIT_ID," + 
						"ACCOUNTING_UNIT_OFFICE_ID," + 
						"CASHBOOK_YEAR," + 
						"CASHBOOK_MONTH," + 
						"BILL_NO," + 
						"SANCTION_PROC_NO," + 
					//	"PAYEE_CODE," + 
						"BILL_DATE," + 
						"BILL_PROCESSING_DONE_BY," + 
						"REMARKS," + 
						"STATUS," + 
						"UPDATED_BY_USERID," + 
						"UPDATED_DATE," + 
						"BILL_MINOR_TYPE_CODE," + 
						"BILL_MAJOR_TYPE," + 
						"BILL_SUB_TYPE_CODE," + 
						"PROCEEDING_RECD_DATE," + 
					//	"PAYEE_TYPE_CODE," + 
						"TOTAL_SANCTIONED_AMOUNT, " + 
						"TOTAL_BILL_AMOUNT," + 
						"MTC70ENTRY," + 
						//"PAYABLE_TO, " + 
						"BILL_TYPE " + 
						") " + 
						" values(?,?,?,?,?,?,?::date,?,?,?,?,?::date,?,?,?,?::date,?,?,?,?)");
				ps1.setInt(1, cboAcc_UnitCode);
				ps1.setInt(2, cmbOffice_code);
				ps1.setInt(3, cboCashBook_Year);
				ps1.setInt(4, cboCashBook_Month);
				ps1.setInt(5, txtBillNo);
				//ps1.setString(6, hid_sanc);
				ps1.setString(6, txtProceedingNo1);
			//	ps1.setInt(7, txtPayeeCode);
				ps1.setDate(7, BillDate);
				ps1.setInt(8, txtEmpID_mas);
				ps1.setString(9, mtxtRemarks);
				ps1.setString(10, "L");
				ps1.setString(11, update_user);
				ps1.setTimestamp(12, ts);
				ps1.setInt(13, cboBillMinorType);
				ps1.setInt(14, cboBillMajorType);
				ps1.setInt(15, cboBillSubType);
				ps1.setDate(16, ProceedingDate);
//				ps1.setString(16, ProceedingDate);
				//ps1.setInt(18, txtPayeeType);
				ps1.setFloat(17, txtTotalSanctionedAmount);
				ps1.setFloat(18, txtTotalBillAmount);
				ps1.setString(19, rdoMTC_70_Register);
				//ps1.setString(20, txtPayableTo);
				ps1.setString(20, "WOSP");
			//	ps1.setInt(21, txtNoofInvoices);
				
				
				int errorcode=ps1.executeUpdate();
                System.out.println("sql insert:::::::"+errorcode);
                
                if(errorcode>0)
                {
                	
                	
               /*      H_code.name="H_code";

                     Budget_Provision.name="Budget_Provision";

                     Budget_Spent.name="Budget_Spent";
                      ref_no.name="ref_no";
                   ref_date.name="ref_date";           
                     particular.name="particular";*/
                  
                	 //String slno_code[]=request.getParameterValues("slno");
                     String Grid_H_code[]=request.getParameter("H_code").split(",");
                    /* String Grid_b_alloted[]=request.getParameterValues("Budget_Provision");
                     String Grid_budget_spent[]=request.getParameterValues("Budget_Spent");                          
                     String Grid_ref_no[]=request.getParameterValues("ref_no");
                     String Grid_ref_date[]=request.getParameterValues("ref_date");  */ 
                     
                     String Grid_pay_type[]=request.getParameter("pay_type").split(",");
                     String Grid_pay_code[]=request.getParameter("pay_code").split(",");
                     String pay_amt[]=request.getParameter("pay_amt").split(",");
                     
                   //  String pay_amt[]=request.getParameterValues("pay_amt");
                	try
                    {
                		
                		
                         int acchead=0;
                         
                		int i=0;
                		int ct_n=0;
                		int SL_NO=0,txtAcc_HeadCode=0;
                		     /*  if(cboBillMajorType==2 && cboBillMinorType==2 && cboBillSubType==1){
                            	 
                		    	   String sql="insert into FAS_BILL_REGISTER_TRANSACTION (ACCOUNTING_UNIT_ID, ACCOUNTING_UNIT_OFFICE_ID, CASHBOOK_YEAR, CASHBOOK_MONTH, BILL_NO, SL_NO, ACCOUNT_HEAD_CODE, BILL_MAJOR_TYPE, BILL_MINOR_TYPE_CODE, BILL_SUB_TYPE_CODE,PAYEE_TYPE_CODE," +
                           		" UPDATED_BY_USERID, UPDATED_DATE,BUDGET_PROVISION,BUDGET_SO_FAR_SPENT,PAYABLE_TO,REF_NO,REF_DATE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)" ;                          
                           ps=connection.prepareStatement(sql);
                                   for(int m=0;m<Grid_H_code.length;m++) 
                                   { 
                                	   acchead=Integer.parseInt(Grid_H_code[m]);
                                	   sd = Grid_ref_date[m].split("/");
                                       c =
              new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                                    Integer.parseInt(sd[0]));
                                       d = c.getTime();
                                       txtref_date = new Date(d.getTime());
                            		SL_NO++;
                                   	ps.setInt(1, cboAcc_UnitCode);
                                   	ps.setInt(2, cmbOffice_code);
                                   	ps.setInt(3, cboCashBook_Year);
                                   	ps.setInt(4, cboCashBook_Month);
                                   	ps.setInt(5, txtBillNo);
                                   	ps.setInt(6,SL_NO);
                                   	// ps.setInt(7,550310);
                                    ps.setInt(7,550310);
                                    ps.setInt(8, cboBillMajorType);
                       				ps.setInt(9, cboBillMinorType);
                       				ps.setInt(10, cboBillSubType);
                       				ps.setInt(11,7);
                       				
                   				    ps.setString(12,update_user);
           	                        ps.setTimestamp(13,ts);
           	                	
           	        				ps.setFloat(14, 0);
           	        				ps.setFloat(15, 0);
           	        				ps.setInt(16,Integer.parseInt(Grid_pay_code[m]));
           	        				
           	        				//ps.setFloat(17,Integer.parseInt(pay_amt[m]));
           	        				ps.setInt(17, Integer.parseInt(Grid_ref_no[m]));
                    				ps.setDate(18, txtref_date);
                    				
           	        				 i=ps.executeUpdate(); 
           	        			if(i>0)
           	        			{
           	        				ct_n++;
           	        			}
           	        				// System.out.println("i:::"+i);
           	        		
                                   }
                                     if(ct_n==SL_NO)
                                     {
                                    	 counts++;
                                     }
                                   
                                
                    }else{*/
                    	//System.out.println("not else");
                		
                		
                		System.out.println("sub_tabTrn>>>>"+sub_tabTrn);
                    	 String sql="insert into "+sub_tabTrn+" (ACCOUNTING_UNIT_ID, ACCOUNTING_UNIT_OFFICE_ID, CASHBOOK_YEAR, CASHBOOK_MONTH, BILL_NO, SL_NO, ACCOUNT_HEAD_CODE, BILL_MAJOR_TYPE, BILL_MINOR_TYPE_CODE, BILL_SUB_TYPE_CODE,PAYEE_TYPE_CODE," +
                 		" UPDATED_BY_USERID, UPDATED_DATE,BUDGET_PROVISION,BUDGET_SO_FAR_SPENT,PAYABLE_TO,AMOUNT) values(?,?,?,?,?,?,?,?,?,?,?,?,?::date,?,?,?,?)" ;                          
                 ps=connection.prepareStatement(sql);
                 System.out.println("sql_SP_GPF"+sql);
                                for(int k=0;k<Grid_H_code.length;k++) 
                                {  
                                	/* sd = Grid_ref_date[k].split("/");
                                     c =
            new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                                  Integer.parseInt(sd[0]));
                                     d = c.getTime();
                                     txtref_date = new Date(d.getTime());*/
                                	System.out.println("len else:::"+Grid_H_code.length);
                                	 SL_NO++;
                                	ps.setInt(1, cboAcc_UnitCode);
                                	ps.setInt(2, cmbOffice_code);
                                	ps.setInt(3, cboCashBook_Year);
                                	ps.setInt(4, cboCashBook_Month);
                                	ps.setInt(5, txtBillNo);
                                	ps.setInt(6,SL_NO);
                                //	System.out.println("acc:::"+Integer.parseInt(Grid_H_code[k]));
                            	    txtAcc_HeadCode=Integer.parseInt(Grid_H_code[k]);
                                    ps.setInt(7,txtAcc_HeadCode);
                                    ps.setInt(8, cboBillMajorType);
                    				ps.setInt(9, cboBillMinorType);
                    				ps.setInt(10, cboBillSubType);
                    				ps.setInt(11,Integer.parseInt(Grid_pay_type[k]));
                    				System.out.println("Grid_pay_type >> "+Integer.parseInt(Grid_pay_type[k]));
                				    ps.setString(12,update_user);
        	                        ps.setTimestamp(13,ts);
        	                        
        	                        ps.setFloat(14, 0);
           	        				ps.setFloat(15, 0);
        	        				
        	        				ps.setInt(16,Integer.parseInt(Grid_pay_code[k]));
        	        				float pay_amount=Float.parseFloat(pay_amt[k]);
        	        		
        	        				ps.setFloat(17,pay_amount);
        	        				//ps.setInt(17, Integer.parseInt(Grid_ref_no[k]));
                    				//ps.setDate(18, txtref_date);
        	        				System.out.println("final value 1 SP_GPF"+cboAcc_UnitCode+"s2 "+cmbOffice_code+"s3   "+cboCashBook_Year+"  s4  "+cboCashBook_Month+"  s5  "+txtBillNo+"  s6  "+SL_NO+"s7"+txtAcc_HeadCode+"  s8  "+cboBillMajorType+"   s9  "+cboBillMinorType+"  s10  "+cboBillSubType+"   s11   "+Integer.parseInt(Grid_pay_type[k])+"  s12   "+update_user+"   s13  "+ts+"   s14   "+0+"  s15   "+0+"   s16   "+Integer.parseInt(Grid_pay_code[k])+"  s17   "+pay_amount);
        	        				 i=ps.executeUpdate(); 
        	        				 System.out.println("i::::"+i);
                                     if(i>0)
                                    	 counts++;
                                }
                   // }    
                               System.out.println("countsiva+"+counts);
                      }
                      catch(Exception e)
                      {
                                  e.getStackTrace();
                                //  System.out.println("Err in value setting for insertion:::"+e.getMessage());
                                  connection.rollback();
                                  xml = xml + "<flag>failure</flag>";
                                 // sendMessage(response,"Error in Insertion ","ok", "Bills_Token_Register_without_SP_GPF.jsp");
                      }
                      ps.close();
                      System.out.println("Length:  "+counts+" "+Grid_H_code.length);
                      System.out.println(cboBillMajorType+cboBillMinorType+cboBillSubType);
                      System.out.println(counts);
                       if(cboBillMajorType==2 && cboBillMinorType==2 && cboBillSubType==1){
                      if(counts>0)
                      {

                          System.out.println("b4 commit");
//            	  connection.commit();
            	  xml = xml + "<flag>success</flag>";
            	/*  sendMessage(response,"Records Inserted Successfully ............ ",
							"ok", "Bills_Token_Register_without_SP_GPF.jsp");*/
              
                      }
                      else
                      {
                    	  xml = xml + "<flag>failure</flag>";
                    	 // sendMessage(response,"Error in Insertion ","ok", "Bills_Token_Register_without_SP_GPF.jsp");
                      }
                      }else{
                      if(counts==Grid_H_code.length)
                      {
                                  System.out.println("b4 commit");
//                    	  connection.commit();
                    	 xml = xml + "<flag>success</flag>";
                    	/*  sendMessage(response,"Records Inserted Successfully ............ ",
  								"ok", "Bills_Token_Register_without_SP_GPF.jsp");*/
                      }
                      else
                      {
                    	  xml = xml + "<flag>failure</flag>";
                    	 // sendMessage(response,"Error in Insertion ","ok", "Bills_Token_Register_without_SP_GPF.jsp");
                      }
              }
                      
                }
                                
                                
				//xml = xml + "<flag>success</flag>";
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}
		
		}
		else if (strCommand.equalsIgnoreCase("saveFunc")) {
			xml = xml + "<response><command>saveFunc</command>";
			int cboCashBook_Year=0,cboCashBook_Month=0;
			int txtBillNo =0;
			String qry_Str="",qry_str1="";
			String cboAcc_UnitCode1 = request.getParameter("cmbAcc_UnitCode");
			int cboAcc_UnitCode = Integer.parseInt(cboAcc_UnitCode1);

			String cboOffice_code1 = request.getParameter("cmbOffice_code");
			int cmbOffice_code = Integer.parseInt(cboOffice_code1);


			String cboBillMajorType1 = request.getParameter("cboBillMajorType");
			int cboBillMajorType = Integer.parseInt(cboBillMajorType1);

			String cboBillMinorType1 = request.getParameter("cboBillMinorType");
			int cboBillMinorType = Integer.parseInt(cboBillMinorType1);
          //  String hid_sanc=request.getParameter("hid_sanc");
			String cboBillSubType1 = request.getParameter("cboBillSubType");
			int cboBillSubType = Integer.parseInt(cboBillSubType1);

			String txtBillNo1 = request.getParameter("txtBillNo");
			txtBillNo = Integer.parseInt(txtBillNo1);
			System.out.println("txtBillNo::::"+txtBillNo);
			//xml = xml + "<BillNo>" + txtBillNo + "</BillNo>";  

			java.sql.Date BillDate = null;
			java.util.GregorianCalendar c2;
			String[] sd = request.getParameter("txtBillDate").split("/");
			c2 = new java.util.GregorianCalendar(Integer.parseInt(sd[2]),
					Integer.parseInt(sd[1]) - 1, Integer.parseInt(sd[0]));
			java.util.Date d = c2.getTime();
			BillDate = new Date(d.getTime());

			   try{cboCashBook_Year=Integer.parseInt(sd[2]);}
	             catch(Exception e){System.out.println("exception"+e );}
	           //  System.out.println("txtCash_year "+txtCash_year);
	             
	             try{cboCashBook_Month=Integer.parseInt(sd[1]);}
	             catch(Exception e){System.out.println("exception"+e );}
	           //  System.out.println("txtCash_Month_hid "+txtCash_Month_hid);
			
			String txtProceedingNo1 = request
					.getParameter("txtManualProceedingNo");
			//int txtProceedingNo = Integer.parseInt(txtProceedingNo1);

			java.sql.Date ProceedingDate = null;
			java.util.GregorianCalendar c22;
			String[] sdd = request.getParameter("txtManualProceedingDate")
					.split("/");
			c22 = new java.util.GregorianCalendar(Integer.parseInt(sdd[2]),
					Integer.parseInt(sdd[1]) - 1, Integer.parseInt(sdd[0]));
			java.util.Date dd = c22.getTime();
			ProceedingDate = new Date(dd.getTime());
		/*	
			String sanc_id = request.getParameter("sanc_id");
			int sanc_one = Integer.parseInt(sanc_id);
			*/
			

			
		//	int txtNoofInvoices = Integer.parseInt(request.getParameter("txtNoofInvoices"));
			
			
		//	String txtInvoiceReceivedDate = request.getParameter("txtInvoiceReceivedDate");
			
			
			//String txtDeductedAmount = request.getParameter("txtDeductedAmount");
			
			String rdoMTC_70_Register = request
					.getParameter("rdoMTC_70_Register");

			String txtTotalBillAmount1 = request.getParameter("txtTotalBillAmount");
			//System.out.println("txtTotalBillAmount1 "+txtTotalBillAmount1);
			
			float txtTotalBillAmount = Float.parseFloat(txtTotalBillAmount1);
                        
                        String txtTotalSanctionedAmount1 = request.getParameter("txtTotalSanctionAmount");
                       // System.out.println("txtTotalSanctionedAmount1 "+txtTotalSanctionedAmount1);
                        float txtTotalSanctionedAmount = Float.parseFloat(txtTotalSanctionedAmount1);
                        
                       // System.out.println("txtTotalSanctionedAmount  "+txtTotalSanctionedAmount);
                        
//			String txtAcc_HeadCode1 = request.getParameter("txtAcc_HeadCode");
//			int txtAcc_HeadCode = Integer.parseInt(txtAcc_HeadCode1);

		/*	int txtPayeeType =Integer.parseInt(request.getParameter("txtPayeeType"));

			String txtPayeeCode1 = request.getParameter("txtPayeeCode");
			String txtp[]=txtPayeeCode1.split("-");
			int txtPayeeCode =Integer.parseInt(txtp[0]);
                        System.out.println("code:::::"+txtPayeeCode);
                        */
         //   String txtPayableTo = request.getParameter("txtPayableTo");
                     //   String txtPayableTo1[] = request.getParameter("txtPayableTo").split("-");
                    //    int txtPayableTo = Integer.parseInt(txtPayableTo1[0]);

                        String txtEmpID_mas1 = request.getParameter("txtEmpID_mas");
			int txtEmpID_mas = Integer.parseInt(txtEmpID_mas1);

			String cboOffice1 = request.getParameter("cboOffice");
			//int cboOffice = Integer.parseInt(cboOffice1);
			
			 int fin_year_from=0,fin_year_to=0;
             Date txtref_date=null;
             //////////////////////Financial year calculation/////////////////////////////////
             if(cboCashBook_Month>3)
             {
            	 	  fin_year_from=cboCashBook_Year;
            	 	  fin_year_to=cboCashBook_Year+1;
             }
             else
             {
            	 	  fin_year_from=cboCashBook_Year-1;
            	 	  fin_year_to=cboCashBook_Year;
             }
			try
            {
				int year1=0,year2=0;

				if(cboCashBook_Month>3)
				{
					year1=cboCashBook_Year;
					year2=(cboCashBook_Year+1);
				}
				else
				{
					
						year1=(cboCashBook_Year-1);
						year2=(cboCashBook_Year);
					
				}
				
                  int error_code = -1,bill_cnt=0;
                  String sub_qry="";
                /*  if(cboBillMajorType==2 && cboBillMinorType==2 && cboBillSubType==1){
                	  sub_qry="  select nvl(max(BILL_NO),0) as cno from SLS_SANCTIONS_BILLS_LINK_MST1";
                	  PreparedStatement ps_cnt=connection.prepareStatement(sub_qry);
                	  ResultSet rs_cnt=ps_cnt.executeQuery();
                	  while(rs_cnt.next()){
                		  bill_cnt=rs_cnt.getInt("cno");
                	  }
                	  txtBillNo=bill_cnt+1;
                	  qry_Str=" SLS_SANCTIONS_BILLS_LINK_MST1 ";
                	  qry_str1=" SLS_SANCTIONS_BILLS_LINK_TRN ";
                  }
                  else{
                	  */
                  cs1=connection.prepareCall("call FAS_BILL_PROCEDURE_CUR(?::NUMERIC,?::NUMERIC,?::NUMERIC,?) ");
                  cs1.setInt(1,cboAcc_UnitCode);
                  cs1.setInt(2,year1);
                  cs1.setInt(3,year2);
                  cs1.registerOutParameter(4, java.sql.Types.NUMERIC);
                  cs1.setInt(4,java.sql.Types.NUMERIC);
                  cs1.execute();
                  error_code = cs1.getInt(4);
                  System.out.println("bill no==>"+error_code);
                  if (error_code ==0)
                  {
                	  System.out.println("failure"); 
                  }
                  else
                  {
                	  System.out.println("success");
                  }
				
				txtBillNo=error_code;
				// qry_Str=" FAS_BILL_REGISTER_MASTER ";
               //  qry_str1=" FAS_BILL_REGISTER_TRANSACTION ";
            
            //      } 
            }
            catch(Exception e){System.out.println("exception"+e );
            
            }
            

			String mtxtRemarks = request.getParameter("mtxtRemarks");
			
			//System.out.println("qry_str ......  "+qry_Str+"    qry_str1...    "+qry_str1);

			try {                    
				connection.setAutoCommit(false);
				ps1 = connection.prepareStatement("insert into  FAS_BILL_REGISTER_MASTER "+
						"(ACCOUNTING_UNIT_ID," + 
						"ACCOUNTING_UNIT_OFFICE_ID," + 
						"CASHBOOK_YEAR," + 
						"CASHBOOK_MONTH," + 
						"BILL_NO," + 
						"SANCTION_PROC_NO," + 
					//	"PAYEE_CODE," + 
						"BILL_DATE," + 
						"BILL_PROCESSING_DONE_BY," + 
						"REMARKS," + 
						"STATUS," + 
						"UPDATED_BY_USERID," + 
						"UPDATED_DATE," + 
						"BILL_MINOR_TYPE_CODE," + 
						"BILL_MAJOR_TYPE," + 
						"BILL_SUB_TYPE_CODE," + 
						"PROCEEDING_RECD_DATE," + 
					//	"PAYEE_TYPE_CODE," + 
						"TOTAL_SANCTIONED_AMOUNT, " + 
						"TOTAL_BILL_AMOUNT," + 
						"MTC70ENTRY," + 
						//"PAYABLE_TO, " + 
						"BILL_TYPE " + 
						") " + 
						" values(?,?,?,?,?,?,?::date,?,?,?,?,?,?,?,?,?::date,?,?,?,?)");
				ps1.setInt(1, cboAcc_UnitCode);
				ps1.setInt(2, cmbOffice_code);
				ps1.setInt(3, cboCashBook_Year);
				ps1.setInt(4, cboCashBook_Month);
				ps1.setInt(5, txtBillNo);
				//ps1.setString(6, hid_sanc);
				ps1.setString(6, txtProceedingNo1);
			//	ps1.setInt(7, txtPayeeCode);
				ps1.setDate(7, BillDate);
				ps1.setInt(8, txtEmpID_mas);
				ps1.setString(9, mtxtRemarks);
				ps1.setString(10, "L");
				ps1.setString(11, update_user);
				ps1.setTimestamp(12, ts);
				ps1.setInt(13, cboBillMinorType);
				ps1.setInt(14, cboBillMajorType);
				ps1.setInt(15, cboBillSubType);
				ps1.setDate(16, ProceedingDate);
				//ps1.setInt(18, txtPayeeType);
				ps1.setFloat(17, txtTotalSanctionedAmount);
				ps1.setFloat(18, txtTotalBillAmount);
				ps1.setString(19, rdoMTC_70_Register);
				//ps1.setString(20, txtPayableTo);
				ps1.setString(20, "WOSP");
			//	ps1.setInt(21, txtNoofInvoices);
				
				
				int errorcode=ps1.executeUpdate();
                System.out.println("sql insert:::::::"+errorcode);
                
                if(errorcode>0)
                {
                	
                	
               /*      H_code.name="H_code";

                     Budget_Provision.name="Budget_Provision";

                     Budget_Spent.name="Budget_Spent";
                      ref_no.name="ref_no";
                   ref_date.name="ref_date";           
                     particular.name="particular";*/
                  
                	 //String slno_code[]=request.getParameterValues("slno");
                     String Grid_H_code[]=request.getParameterValues("H_code");
                    /* String Grid_b_alloted[]=request.getParameterValues("Budget_Provision");
                     String Grid_budget_spent[]=request.getParameterValues("Budget_Spent");                          
                     String Grid_ref_no[]=request.getParameterValues("ref_no");
                     String Grid_ref_date[]=request.getParameterValues("ref_date");  */ 
                     
                     String Grid_pay_type[]=request.getParameterValues("pay_type");
                     String Grid_pay_code[]=request.getParameterValues("pay_code");
                     String pay_amt[]=request.getParameterValues("pay_amt");
                     
                   //  String pay_amt[]=request.getParameterValues("pay_amt");
                	try
                    {
                		
                		
                         int acchead=0;
                         
                		int i=0;
                		int ct_n=0;
                		int SL_NO=0,txtAcc_HeadCode=0;
                		     /*  if(cboBillMajorType==2 && cboBillMinorType==2 && cboBillSubType==1){
                            	 
                		    	   String sql="insert into FAS_BILL_REGISTER_TRANSACTION (ACCOUNTING_UNIT_ID, ACCOUNTING_UNIT_OFFICE_ID, CASHBOOK_YEAR, CASHBOOK_MONTH, BILL_NO, SL_NO, ACCOUNT_HEAD_CODE, BILL_MAJOR_TYPE, BILL_MINOR_TYPE_CODE, BILL_SUB_TYPE_CODE,PAYEE_TYPE_CODE," +
                           		" UPDATED_BY_USERID, UPDATED_DATE,BUDGET_PROVISION,BUDGET_SO_FAR_SPENT,PAYABLE_TO,REF_NO,REF_DATE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)" ;                          
                           ps=connection.prepareStatement(sql);
                                   for(int m=0;m<Grid_H_code.length;m++) 
                                   { 
                                	   acchead=Integer.parseInt(Grid_H_code[m]);
                                	   sd = Grid_ref_date[m].split("/");
                                       c =
              new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                                    Integer.parseInt(sd[0]));
                                       d = c.getTime();
                                       txtref_date = new Date(d.getTime());
                            		SL_NO++;
                                   	ps.setInt(1, cboAcc_UnitCode);
                                   	ps.setInt(2, cmbOffice_code);
                                   	ps.setInt(3, cboCashBook_Year);
                                   	ps.setInt(4, cboCashBook_Month);
                                   	ps.setInt(5, txtBillNo);
                                   	ps.setInt(6,SL_NO);
                                   	// ps.setInt(7,550310);
                                    ps.setInt(7,550310);
                                    ps.setInt(8, cboBillMajorType);
                       				ps.setInt(9, cboBillMinorType);
                       				ps.setInt(10, cboBillSubType);
                       				ps.setInt(11,7);
                       				
                   				    ps.setString(12,update_user);
           	                        ps.setTimestamp(13,ts);
           	                	
           	        				ps.setFloat(14, 0);
           	        				ps.setFloat(15, 0);
           	        				ps.setInt(16,Integer.parseInt(Grid_pay_code[m]));
           	        				
           	        				//ps.setFloat(17,Integer.parseInt(pay_amt[m]));
           	        				ps.setInt(17, Integer.parseInt(Grid_ref_no[m]));
                    				ps.setDate(18, txtref_date);
                    				
           	        				 i=ps.executeUpdate(); 
           	        			if(i>0)
           	        			{
           	        				ct_n++;
           	        			}
           	        				// System.out.println("i:::"+i);
           	        		
                                   }
                                     if(ct_n==SL_NO)
                                     {
                                    	 counts++;
                                     }
                                   
                                
                    }else{*/
                    	//System.out.println("not else");
                    	 String sql="insert into FAS_BILL_REGISTER_TRANSACTION (ACCOUNTING_UNIT_ID, ACCOUNTING_UNIT_OFFICE_ID, CASHBOOK_YEAR, CASHBOOK_MONTH, BILL_NO, SL_NO, ACCOUNT_HEAD_CODE, BILL_MAJOR_TYPE, BILL_MINOR_TYPE_CODE, BILL_SUB_TYPE_CODE,PAYEE_TYPE_CODE," +
                 		" UPDATED_BY_USERID, UPDATED_DATE,BUDGET_PROVISION,BUDGET_SO_FAR_SPENT,PAYABLE_TO,AMOUNT) values(?,?,?,?,?,?,?,?,?,?,?,?,?::date,?,?,?,?)" ;                          
                 ps=connection.prepareStatement(sql);
                                for(int k=0;k<Grid_H_code.length;k++) 
                                {  
                                	/* sd = Grid_ref_date[k].split("/");
                                     c =
            new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                                  Integer.parseInt(sd[0]));
                                     d = c.getTime();
                                     txtref_date = new Date(d.getTime());*/
                                	System.out.println("len else:::"+Grid_H_code.length);
                                	 SL_NO++;
                                	ps.setInt(1, cboAcc_UnitCode);
                                	ps.setInt(2, cmbOffice_code);
                                	ps.setInt(3, cboCashBook_Year);
                                	ps.setInt(4, cboCashBook_Month);
                                	ps.setInt(5, txtBillNo);
                                	ps.setInt(6,SL_NO);
                                //	System.out.println("acc:::"+Integer.parseInt(Grid_H_code[k]));
                            	    txtAcc_HeadCode=Integer.parseInt(Grid_H_code[k]);
                                    ps.setInt(7,txtAcc_HeadCode);
                                    ps.setInt(8, cboBillMajorType);
                    				ps.setInt(9, cboBillMinorType);
                    				ps.setInt(10, cboBillSubType);
                    				ps.setInt(11,Integer.parseInt(Grid_pay_type[k]));
                    				
                				    ps.setString(12,update_user);
        	                        ps.setTimestamp(13,ts);
        	                        
        	                        ps.setFloat(14, 0);
           	        				ps.setFloat(15, 0);
        	        				
        	        				ps.setInt(16,Integer.parseInt(Grid_pay_code[k]));
        	        				float pay_amount=Float.parseFloat(pay_amt[k]);
        	        		
        	        				ps.setFloat(17,pay_amount);
        	        				//ps.setInt(17, Integer.parseInt(Grid_ref_no[k]));
                    				//ps.setDate(18, txtref_date);
        	        				
        	        				 i=ps.executeUpdate(); 
        	        				 System.out.println("i::::"+i);
                                     if(i>0)
                                    	 counts++;
                                }
                   // }    
                               
                      }
                      catch(Exception e)
                      {
                                  e.getStackTrace();
                                //  System.out.println("Err in value setting for insertion:::"+e.getMessage());
                                  connection.rollback();
                                  sendMessage(response,"Error in Insertion ","ok", "Bills_Token_Register_without_SP_GPF.jsp");
                      }
                      ps.close();
                      System.out.println("Length:  "+counts+" "+Grid_H_code.length);
                      System.out.println(cboBillMajorType+cboBillMinorType+cboBillSubType);
                      System.out.println(counts);
                       if(cboBillMajorType==2 && cboBillMinorType==2 && cboBillSubType==1){
                      if(counts>0)
                      {

                          System.out.println("b4 commit");
//            	  connection.commit();
            	 // xml = xml + "<flag>success</flag>";
            	  sendMessage(response,"Records Inserted Successfully ............ ",
							"ok", "Bills_Token_Register_without_SP_GPF.jsp");
              
                      }
                      else
                      {
                    	  sendMessage(response,"Error in Insertion ","ok", "Bills_Token_Register_without_SP_GPF.jsp");
                      }
                      }else{
                      if(counts==Grid_H_code.length)
                      {
                                  System.out.println("b4 commit");
//                    	  connection.commit();
                    	 // xml = xml + "<flag>success</flag>";
                    	  sendMessage(response,"Records Inserted Successfully ............ ",
  								"ok", "Bills_Token_Register_without_SP_GPF.jsp");
                      }
                      else
                      {
                    	  sendMessage(response,"Error in Insertion ","ok", "Bills_Token_Register_without_SP_GPF.jsp");
                      }
              }
                      
                }
                                
                                
				//xml = xml + "<flag>success</flag>";
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}
		} 
			
		
		
		
		else if (strCommand.equalsIgnoreCase("loadGrid")) {
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
		} else if (strCommand.equalsIgnoreCase("Edit")) {

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
			
		} /*else if (strCommand.equalsIgnoreCase("deleted")) {

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

		}*/ else if (strCommand.equalsIgnoreCase("IVno")) {

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

		} else if (strCommand.equalsIgnoreCase("InvoiceDetails")) {

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

		} /*else if (strCommand.equalsIgnoreCase("update")) {

			xml = "<response><command>update</command>";

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

//		    String txtPayeeType = request.getParameter("txtPayeeType");
//
//		    String txtPayeeCode1 = request.getParameter("txtPayeeCode");
//		    int txtPayeeCode = Integer.parseInt(txtPayeeCode1);
		    
		    String txtPayableTo1 = request.getParameter("txtPayableTo");
		    int txtPayableTo = Integer.parseInt(txtPayableTo1);

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
				ps1 = connection.prepareStatement("update FAS_BILL_REGISTERNEW set BILL_MINOR_TYPE_CODE=?,BILL_SUB_TYPE_CODE=?,BILL_DATE=?,SANCTION_PROC_NO=?,PROCEEDING_RECD_DATE=?,MTC70ENTRY=?,TOTAL_SANCTIONED_AMOUNT=?,TOTAL_BILL_AMOUNT=?,ACCOUNT_HEAD_CODE=?,PAYABLE_TO=?,BILL_PROCESSING_DONE_BY=?,REF_NO=?,REF_DATE=?,REMARKS=?,STATUS=?,UPDATED_BY_USERID=?,UPDATED_DATE=?,BUDGET_PROVISION=?,BUDGET_SO_FAR_SPENT=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_UNIT_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and BILL_MAJOR_TYPE=? and BILL_NO=?");

				ps1.setInt(1, cboBillMinorType);
				ps1.setInt(2, cboBillSubType);
				ps1.setDate(3, BillDate);
				ps1.setInt(4, txtProceedingNo);
				ps1.setDate(5, ProceedingDate);
				ps1.setString(6, rdoMTC_70_Register);
				ps1.setFloat(7, txtTotalSanctionedAmount);
				ps1.setFloat(8, txtTotalBillAmount);
				ps1.setInt(9, txtAcc_HeadCode);
//                ps1.setString(10, txtPayeeType);
//				ps1.setInt(11, txtPayeeCode);
				ps1.setInt(10, txtPayableTo);
				ps1.setInt(11, txtEmpID_mas);
				ps1.setInt(12, txtRefNo);
				ps1.setDate(13, RefDate);
				ps1.setString(14, mtxtRemarks);
				ps1.setString(15, "L");
				ps1.setString(16, update_user);
				ps1.setTimestamp(17, ts);
				ps1.setFloat(18, txtBudgetProvision);
				ps1.setFloat(19, txtBudgetSpent);
                                
				ps1.setInt(20, cboAcc_UnitCode);
				ps1.setInt(21, cmbOffice_code);
				ps1.setInt(22, cboCashBook_Year);
				ps1.setInt(23, cboCashBook_Month);
				ps1.setInt(24, cboBillMajorType);
				ps1.setInt(25, txtBillNo);
				ps1.executeUpdate();
				xml = xml + "<flag>success</flag>";
			} catch (Exception e) {
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
		}*/

		xml = xml + "</response>";
		out.write(xml);
		System.out.println(xml);
	}
		private void sendMessage(HttpServletResponse response, String msg,
				String bType, String jsp) {
			try {
				String url = "org/FAS/FAS1/BillRegister/jsps/MessengerOkBack.jsp?message="
						+ msg + "&button=" + bType + "&jspname=" + jsp;
				response.sendRedirect(url);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		private static java.sql.Date getCurrentDate() {
			java.util.Date today = new java.util.Date();
			return new java.sql.Date(today.getTime());
		}
}
