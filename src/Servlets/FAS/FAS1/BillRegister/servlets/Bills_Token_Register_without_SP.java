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
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Bills_Token_Register_without_SP
 */
public class Bills_Token_Register_without_SP extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Bills_Token_Register_without_SP() {
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

		Connection connection = null;
		Statement statement = null;
		ResultSet results = null;
		ResultSet results2;
		ResultSet rs = null;
		ResultSet rs2 = null;
		PreparedStatement ps = null;
		CallableStatement cs1=null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		int cashbookYear = 0;
		String cashbookMonth = null;
		int unitid = 0;
		String unitname = "";
		int accid = 0;

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
		// System.out.println("User Id is:" + userid);
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

			xml = "<response><command>getBillMajorType</command>";
			xml = xml + "<empid>" + empid + "</empid>";
			xml = xml + "<empName>" + empName + "</empName>";
			try {
				String su = "select BILL_MAJOR_TYPE_CODE,BILL_MAJOR_TYPE_DESC from FAS_BILL_MAJOR_TYPES where STATUS='L'  order by BILL_MAJOR_TYPE_CODE";
				ps = connection.prepareStatement(su);//and BILL_MAJOR_TYPE_CODE!=2
				rs = ps.executeQuery();
				while (rs.next()) {
					xml = xml + "<billMajorTypeCode>"
							+ rs.getInt("BILL_MAJOR_TYPE_CODE")
							+ "</billMajorTypeCode>";

					xml = xml + "<billMajorTypeDesc>"
							+ rs.getString("BILL_MAJOR_TYPE_DESC")
							+ "</billMajorTypeDesc>";
				}
				String su1 = "select b.office_id,b.office_name from (select employee_id,office_id from HRM_EMP_CURRENT_POSTING where employee_id=?)a left outer join (select office_id,office_name from COM_MST_OFFICES)b on a.office_id=b.office_id";
				ps = connection.prepareStatement(su1);
				ps.setInt(1, empid);
				rs = ps.executeQuery();
				while (rs.next()) {
					xml = xml + "<OfficeID>" + rs.getInt("OFFICE_ID")
							+ "</OfficeID>";

					xml = xml + "<OfficeName>" + rs.getString("OFFICE_NAME")
							+ "</OfficeName>";

					System.out.println(rs.getInt("OFFICE_ID"));
					System.out.println(rs.getString("OFFICE_NAME"));
				}
				xml = xml + "<flag>success</flag>";
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
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
				int c=0;
				while (rs.next()) {
					xml = xml + "<ACCOUNT_HEAD_CODE>"
							+ rs.getInt("ACCOUNT_HEAD_CODE")
							+ "</ACCOUNT_HEAD_CODE>";

					xml = xml + "<ACCOUNT_HEAD_DESC>"
							+ rs.getString("ACCOUNT_HEAD_DESC")
							+ "</ACCOUNT_HEAD_DESC>";
					c++;
				}
				if(c>0){
				xml = xml + "<flag>success</flag>";}
				else if(c==0){
					xml = xml + "<flag>failure</flag>";	
				}
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
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
					" FROM SEC_MST_OTHER_USERS_PROFILE WHERE USER_ID='"+userid+"'";
					
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

		} else if (strCommand.equalsIgnoreCase("getBillMinorType")) {
			xml = xml + "<response><command>getBillMinorType</command>";
			int cboBillMajorType = Integer.parseInt(request
					.getParameter("cboBillMajorType"));
			int i2 = 1, i3 = 0;
			try {

				String su1 = "select BILL_MINOR_TYPE_CODE,BILL_MINOR_TYPE_DESC from FAS_BILL_MINOR_TYPES_MST where BILL_MAJOR_TYPE_CODE=? and status='L' and BILL_MINOR_TYPE_CODE!=1";
				ps1 = connection.prepareStatement(su1);
				ps1.setInt(1, cboBillMajorType);
				results = ps1.executeQuery();
				if (results.next()) {
					try {

						String su = "select BILL_MINOR_TYPE_CODE,BILL_MINOR_TYPE_DESC from FAS_BILL_MINOR_TYPES_MST where BILL_MAJOR_TYPE_CODE=? and status='L' and BILL_MINOR_TYPE_CODE!=1";
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
						.prepareStatement("Select max(BILL_NO) from FAS_BILL_REGISTERNEW where BILL_MAJOR_TYPE=?");
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

				//FAS_BILL_MINOR_TYPES_MST
				
				
				String su0 = " select SUB_TYPE_APPLICABLE from FAS_BILL_MINOR_TYPES_MST where BILL_MAJOR_TYPE_CODE=? and  BILL_MINOR_TYPE_CODE=? and status='L'";
				PreparedStatement ps01 = connection.prepareStatement(su0);
				ps01.setInt(1, cboBillMajorType);
				ps01.setInt(2, cboBillMinorType);
				ResultSet results1 = ps01.executeQuery();
				System.out.println("su0 "+su0);
				if(results1.next()){
					String subapplicable=results1.getString("SUB_TYPE_APPLICABLE");
					System.out.println("subapplicable==> "+subapplicable);
				if(subapplicable.equalsIgnoreCase("Y")){
					
					xml = xml + "<billSubTypeCode_app>Y</billSubTypeCode_app>";
					
					String su1 = "select BILL_SUB_TYPE_CODE,BILL_SUB_TYPE_DESC from FAS_BILL_SUB_TYPES where BILL_MAJOR_TYPE_CODE=? and BILL_MINOR_TYPE_CODE=? and status='L'";
					ps1 = connection.prepareStatement(su1);
					ps1.setInt(1, cboBillMajorType);
					ps1.setInt(2, cboBillMinorType);
					results = ps1.executeQuery();
					if (results.next()) {
						try {

							String su = "select BILL_SUB_TYPE_CODE,BILL_SUB_TYPE_DESC from FAS_BILL_SUB_TYPES where BILL_MAJOR_TYPE_CODE=? and BILL_MINOR_TYPE_CODE=? and status='L'";
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
					
					
					
					
				}else if(subapplicable.equalsIgnoreCase("N")){
					xml = xml + "<flag>NotApply</flag>";
					xml = xml + "<billSubTypeCode_app>N</billSubTypeCode_app>";
				}
				
				
				
				
				
				
				}
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
		} 
		
		
		
		else if (strCommand.equalsIgnoreCase("checkinvoicee")) {

			xml = xml + "<response><command>checkinvoicee</command>";
			System.out.println("welocmme  ");
			
			int cboAcc_UnitCode = Integer.parseInt(request
					.getParameter("cboAcc_UnitCode"));
			int cboOffice_code = Integer.parseInt(request
					.getParameter("cmbOffice_code"));
			
			int cboBillMajorType = Integer.parseInt(request
					.getParameter("cboBillMajorType"));
			int cboBillMinorType = Integer.parseInt(request
					.getParameter("cboBillMinorType"));
			
			int cboBillSubType = Integer.parseInt(request
					.getParameter("cboBillSubType"));
			
			try {

				String ssi="select supporting_invoice from fas_sup_inv_bill_types where accounting_unit_id="+cboAcc_UnitCode+" and accounting_for_office_id="+cboOffice_code+" and bill_major_type_code= "+cboBillMajorType+
       " and bill_minor_type_code="+cboBillMinorType+" and bill_sub_type_code="+cboBillSubType+" and STATUS='L' " ;
				PreparedStatement pss=connection.prepareStatement(ssi);
				ResultSet rrss=pss.executeQuery();
				System.out.println(ssi);
				xml = xml + "<flag>success</flag>";
				if (rrss.next()) {

					//System.out.println("enter");
						//String su = "select EMPLOYEE_NAME,EMPLOYEE_INITIAL,EMPLOYEE_ID from HRM_MST_EMPLOYEES where EMPLOYEE_ID=";
						ps = connection.prepareStatement(ssi);

						rs = ps.executeQuery();
						
						while (rs.next()) {
							//System.out.println("while");
							
							String invo = rs.getString("supporting_invoice");
							
							if(invo.equalsIgnoreCase("Y")){
								xml = xml + "<invoice>compulse</invoice>";
							}else{
								xml = xml + "<invoice>option</invoice>";	
							}
							
						}
						ps.close();
						rs.close();
					
				} else {
					xml = xml + "<invoice>option</invoice>";	
				}
				
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
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
				
				String ssi="select ACCOUNT_HEAD_CODE from FAS_BILL_ACCOUNT_HEADS where  BILL_MAJOR_TYPE_CODE= "+cboBillMajorType+
			       " and BILL_MINOR_TYPE_CODE="+cboBillMinorType+" and BILL_SUB_TYPE_CODE="+cboBillSubType+" and STATUS='L' " ;
				PreparedStatement pss=connection.prepareStatement(ssi);
				ResultSet rrss=pss.executeQuery();
				System.out.println("load head "+ssi);
				xml = xml + "<flag>success</flag>";
				if (rrss.next()) {

					
						ps = connection.prepareStatement(ssi);

						rs = ps.executeQuery();
						
						while (rs.next()) {
												
							String headNo = rs.getString("ACCOUNT_HEAD_CODE");
							
							
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
		
		else if (strCommand.equalsIgnoreCase("loadPayyeedesc")) {

			xml = xml + "<response><command>loadPayyeedesc</command>";
			
			//int txtPayeeCode1 = Integer.parseInt(request.getParameter("txtPayeeCode"));
			String txtPayeeCode1 = request.getParameter("txtPayeeCode");
			int cboOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
			
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
			
			try {
                
				// ps2=con.prepareStatement("select e.EMPLOYEE_ID,e.EMPLOYEE_NAME||'.'||e.EMPLOYEE_INITIAL||'-'|| d.DESIGNATION as ENAME,c.EMPLOYEE_STATUS_ID from HRM_MST_EMPLOYEES e,HRM_EMP_CURRENT_POSTING c,HRM_MST_DESIGNATIONS d where c.DESIGNATION_ID=d.DESIGNATION_ID  and e.EMPLOYEE_ID=c.EMPLOYEE_ID and c.EMPLOYEE_ID=? and c.OFFICE_ID=? and c.EMPLOYEE_STATUS_ID='WKG' order by e.EMPLOYEE_NAME ");
				String ss1="SELECT e.EMPLOYEE_ID,\n" + 
				 "  e.EMPLOYEE_NAME\n" + 
				 "  ||'.'\n" + 
				 "  ||e.EMPLOYEE_INITIAL\n" + 
				 "  ||'-'\n" + 
				 "  || d.DESIGNATION AS ENAME,\n" + 
				 "  c.EMPLOYEE_STATUS_ID\n" + 
				 " FROM HRM_MST_EMPLOYEES e,\n" + 
				 "  HRM_EMP_CURRENT_POSTING c,\n" + 
				 "  HRM_MST_DESIGNATIONS d\n" + 
				 // Joan Change on 07 April 2015 for vasanthi mam suggestion  remove HRM_EMP_CONTROLLING_OFFICE condition
			//	 "  HRM_EMP_CONTROLLING_OFFICE f\n" + 
				 " WHERE c.DESIGNATION_ID  =d.DESIGNATION_ID\n" + 
				 " AND e.EMPLOYEE_ID       =c.EMPLOYEE_ID\n" + 
				 // Joan Change on 07 April 2015 for vasanthi mam suggestion  remove HRM_EMP_CONTROLLING_OFFICE condition
			//	 " and  c.EMPLOYEE_ID      = f.EMPLOYEE_ID\n" + 
				 " AND c.EMPLOYEE_ID       =" +txtPayeeCode1+ 
				 // Joan Change on 07 April 2015 for vasanthi mam suggestion  remove f.controlling_office_id condition
				 
			//	 " and f.controlling_office_id=" +cboOffice_code+ 
				 " AND c.EMPLOYEE_STATUS_ID='WKG'\n" + 
				 " ORDER BY e.EMPLOYEE_NAME";
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
			//System.out.println("yera "+year+"year1 "+year1);
			String splitlastyr=String.valueOf(year1);
			String ssyr1=splitlastyr.substring(2,4);
			
			String financialYear1 = (year + "-" + ssyr1);

			try {

				String su1 = "select * from COM_BUDGET_DETAILS where ACCOUNTING_UNIT_ID="+cboAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cboOffice_code+" and FINANCIAL_YEAR='"+financialYear1+"' and (FROM_ACC_HD_CODE='"+txtaccountheadcode+"' or  TO_ACC_HD_CODE='"+txtaccountheadcode+"')";

				System.out.println(cboAcc_UnitCode);
				System.out.println(cboOffice_code);
				System.out.println(financialYear1);
				 System.out.println(txtaccountheadcode);

				ps1 = connection.prepareStatement(su1);
				//ps1.setInt(1, cboAcc_UnitCode);
				//ps1.setInt(2, cboOffice_code);
				//ps1.setString(3, financialYear1);                                
				//ps1.setString(4, txtaccountheadcode);
				System.out.println(su1);
				results = ps1.executeQuery();

				if (results.next()) {

					System.out.println("enter");

					try {

						String su = "select CURRENT_YEAR_BUDGET_ALLOTTED,BUDGET_SOFAR_SPENT from COM_BUDGET_DETAILS where ACCOUNTING_UNIT_ID="+cboAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cboOffice_code+" and FINANCIAL_YEAR='"+financialYear1+"' and (FROM_ACC_HD_CODE='"+txtaccountheadcode+"' or  TO_ACC_HD_CODE='"+txtaccountheadcode+"')";
						ps = connection.prepareStatement(su);
						//ps.setInt(1, cboAcc_UnitCode);
						//ps.setInt(2, cboOffice_code);
						//ps.setString(3, financialYear1);
						//ps.setString(4, txtaccountheadcode);

						rs = ps.executeQuery();
						xml = xml + "<flag>success</flag>";
						if (rs.next()) {
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
		} else if (strCommand.equalsIgnoreCase("saveFunc")) {
			xml = xml + "<response><command>saveFunc</command>";
				
			int year1=0,year2=0;
			String cboAcc_UnitCode1 = request.getParameter("cmbAcc_UnitCode");
			int cboAcc_UnitCode = Integer.parseInt(cboAcc_UnitCode1);

			String cboOffice_code1 = request.getParameter("cmbOffice_code");
			int cmbOffice_code = Integer.parseInt(cboOffice_code1);

			/*String cboCashBook_Year1 = request.getParameter("year");
			int cboCashBook_Year = Integer.parseInt(cboCashBook_Year1);

			String cboCashBook_Month1 = request.getParameter("month");
			int cboCashBook_Month = Integer.parseInt(cboCashBook_Month1);*/

			String cboBillMajorType1 = request.getParameter("cboBillMajorType");
			int cboBillMajorType = Integer.parseInt(cboBillMajorType1);

			String cboBillMinorType1 = request.getParameter("cboBillMinorType");
			int cboBillMinorType = Integer.parseInt(cboBillMinorType1);

			String cboBillSubType1 = request.getParameter("cboBillSubType");
			int cboBillSubType = Integer.parseInt(cboBillSubType1);

			//String txtBillNo1 = request.getParameter("txtBillNo");
			int txtBillNo = 0;

			

			java.sql.Date BillDate = null;
			java.util.GregorianCalendar c2;
			String[] sd = request.getParameter("txtBillDate").split("/");
			c2 = new java.util.GregorianCalendar(Integer.parseInt(sd[2]),
					Integer.parseInt(sd[1]) - 1, Integer.parseInt(sd[0]));
			java.util.Date d = c2.getTime();
			BillDate = new Date(d.getTime());

			int cboCashBook_Year = Integer.parseInt(sd[2]);
			int cboCashBook_Month = Integer.parseInt(sd[1]);
			
			String txtManualProceedingNo1 = request
					.getParameter("txtManualProceedingNo");
			//int txtManualProceedingNo = Integer.parseInt(txtManualProceedingNo1);

			java.sql.Date ManualProceedingDate = null;
			java.util.GregorianCalendar c22;
			String[] sdd = request.getParameter("txtManualProceedingDate")
					.split("/");
			c22 = new java.util.GregorianCalendar(Integer.parseInt(sdd[2]),
					Integer.parseInt(sdd[1]) - 1, Integer.parseInt(sdd[0]));
			java.util.Date dd = c22.getTime();
			ManualProceedingDate = new Date(dd.getTime());

			java.sql.Date InvoiceReceivedDate = null;
			java.util.GregorianCalendar c5;
			String indat=request.getParameter("txtInvoiceReceivedDate");
			if((indat.equalsIgnoreCase(""))||(indat.equalsIgnoreCase(null))){
				
			}else{
				String[] sd5 = request.getParameter("txtInvoiceReceivedDate")
				.split("/");
			c5 = new java.util.GregorianCalendar(Integer.parseInt(sd5[2]),
					Integer.parseInt(sd5[1]) - 1, Integer.parseInt(sd5[0]));
			java.util.Date d5 = c5.getTime();
			InvoiceReceivedDate = new Date(d5.getTime());	
			}
			

			String txtNoofInvoices1 = request.getParameter("txtNoofInvoices");
			int txtNoofInvoices =0;
			if((txtNoofInvoices1.equalsIgnoreCase(""))||(txtNoofInvoices1.equalsIgnoreCase(null))){
				
			}else{
				txtNoofInvoices = Integer.parseInt(txtNoofInvoices1);	
			}
			

			String rdoMTC_70_Register = request.getParameter("rdoMTC_70_Register");

			
			
			String txtTotalSanctionAmount1 = request.getParameter("txtTotalSanctionAmount");
			float txtTotalSanctionAmount = Float.parseFloat(txtTotalSanctionAmount1);
			
			String txtTotalBillAmount1 = request.getParameter("txtTotalBillAmount");
			float txtTotalBillAmount = Float.parseFloat(txtTotalBillAmount1);
			
			
			String txtDeductedAmount1 = request
					.getParameter("txtDeductedAmount");
			float txtDeductedAmount = Float.parseFloat(txtDeductedAmount1);

			String txtAcc_HeadCode1 = request.getParameter("txtAcc_HeadCode");
			int txtAcc_HeadCode = Integer.parseInt(txtAcc_HeadCode1);

			int txtPayeeType = Integer.parseInt(request.getParameter("txtPayeeType"));

			String txtPayeeCode1 = request.getParameter("txtPayeeCode");
			int txtPayeeCode = Integer.parseInt(txtPayeeCode1);

			String txtEmpID_mas1 = request.getParameter("txtEmpID_mas");
			int txtEmpID_mas = Integer.parseInt(txtEmpID_mas1);

		//	String cboOffice1 = request.getParameter("cboOffice");
			//int cboOffice = Integer.parseInt(cboOffice1);

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
				 
                  int error_code = -1;
                  cs1=connection.prepareCall("call FAS_BILL_PROCEDURE_CUR(?::numeric,?::numeric,?::numeric,?::numeric) ");
                  cs1.setInt(1,cboAcc_UnitCode);
                /*  cs1.setInt(2,year1);
                  cs1.setInt(3,year2);*/
                  cs1.setInt(2,year1);
                  cs1.setInt(3,year2);
                  
                  cs1.registerOutParameter(4, java.sql.Types.NUMERIC);
                  cs1.setNull(4, java.sql.Types.NUMERIC);
                  cs1.execute();
                  //error_code = cs1.getInt(4);
                  error_code = cs1.getBigDecimal(4).intValue();
                  System.out.println(error_code);
                  if (error_code ==0)
                  {
                	  System.out.println("failure"); 
                  }
                  else
                  {
                	  System.out.println("success");
                  }
				
				txtBillNo=error_code;
				
				ps1 = connection.prepareStatement("insert into FAS_BILL_REGISTERNEW(ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,BILL_MAJOR_TYPE,BILL_MINOR_TYPE_CODE,BILL_SUB_TYPE_CODE,BILL_NO,BILL_DATE,MANUAL_PROCEEDING_NO,MANUAL_PROCEEDING_DATE,INVOICE_RECEIVED_DATE,NO_OFINVOICES,MTC70ENTRY,TOTAL_BILL_AMOUNT,ACCOUNT_HEAD_CODE,PAYEE_TYPE_CODE,PAYEE_CODE,BILL_PROCESSING_DONE_BY,REF_NO,REF_DATE,REMARKS,STATUS,BUDGET_PROVISION,BUDGET_SO_FAR_SPENT,UPDATED_BY_USERID,UPDATED_DATE,DEDUCTED_AMOUNT,TOTAL_SANCTIONED_AMOUNT,PROCEEDING_RECD_DATE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				//,TOTAL_SANCTIONED_AMOUNT
				ps1.setInt(1, cboAcc_UnitCode);
				ps1.setInt(2, cmbOffice_code);
				ps1.setInt(3, cboCashBook_Year);
				ps1.setInt(4, cboCashBook_Month);
				ps1.setInt(5, cboBillMajorType);
				ps1.setInt(6, cboBillMinorType);
				ps1.setInt(7, cboBillSubType);
				ps1.setInt(8, txtBillNo);
				ps1.setDate(9, BillDate);
				//ps1.setInt(10, txtManualProceedingNo);
				System.out.println("txtManualProceedingNo1:::"+txtManualProceedingNo1);
				ps1.setString(10, txtManualProceedingNo1);
				ps1.setDate(11, ManualProceedingDate);
				ps1.setDate(12, InvoiceReceivedDate);
				ps1.setInt(13, txtNoofInvoices);
				ps1.setString(14, rdoMTC_70_Register);
				ps1.setFloat(15, txtTotalBillAmount);
				ps1.setInt(16, txtAcc_HeadCode);
				System.out.println("txtPayeeType:::"+txtPayeeType);
				ps1.setInt(17, txtPayeeType);
				ps1.setInt(18, txtPayeeCode);
				System.out.println("txtPayeeCode:::"+txtPayeeCode);
				ps1.setInt(19, txtEmpID_mas);
				ps1.setInt(20, txtRefNo);
				ps1.setDate(21, RefDate);
				mtxtRemarks="Bill minor type –Bill Subtype-Manual proceeding no – date – total bill amount – payee type and name";
				ps1.setString(22, mtxtRemarks);
				ps1.setString(23, "L");
				ps1.setFloat(24, txtBudgetProvision);
				ps1.setFloat(25, txtBudgetSpent);
				ps1.setString(26, userid);
				ps1.setTimestamp(27, ts);
				ps1.setFloat(28, txtDeductedAmount);
				ps1.setFloat(29, txtTotalSanctionAmount);
				ps1.setDate(30, ManualProceedingDate);
				ps1.executeUpdate();
				xml = xml + "<BillNo>" + txtBillNo + "</BillNo>";
				xml = xml + "<flag>success</flag>";
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}
		}else if (strCommand.equalsIgnoreCase("EditFunc")) {

			xml = xml + "<response><command>EditFunc</command>";

			int year1 = 0, year2 = 0;
			String cboAcc_UnitCode1 = request.getParameter("cmbAcc_UnitCode");
			int cboAcc_UnitCode = Integer.parseInt(cboAcc_UnitCode1);

			String cboOffice_code1 = request.getParameter("cmbOffice_code");
			int cmbOffice_code = Integer.parseInt(cboOffice_code1);

			/*String cboCashBook_Year1 = request.getParameter("year");
			int cboCashBook_Year = Integer.parseInt(cboCashBook_Year1);

			String cboCashBook_Month1 = request.getParameter("month");
			int cboCashBook_Month = Integer.parseInt(cboCashBook_Month1);*/

			String cboBillMajorType1 = request.getParameter("cboBillMajorType");
			int cboBillMajorType = Integer.parseInt(cboBillMajorType1);

			String cboBillMinorType1 = request.getParameter("cboBillMinorType");
			int cboBillMinorType = Integer.parseInt(cboBillMinorType1);

			String cboBillSubType1 = request.getParameter("cboBillSubType");
			int cboBillSubType = Integer.parseInt(cboBillSubType1);

			// String txtBillNo1 = request.getParameter("txtBillNo");
			int txtBillNo = Integer.parseInt(request.getParameter("txtBillNo"));

			java.sql.Date BillDate = null;
			java.util.GregorianCalendar c2;
			String[] sd = request.getParameter("txtBillDate").split("/");
			c2 = new java.util.GregorianCalendar(Integer.parseInt(sd[2]),
					Integer.parseInt(sd[1]) - 1, Integer.parseInt(sd[0]));
			java.util.Date d = c2.getTime();
			BillDate = new Date(d.getTime());
			
			
			int cboCashBook_Year = Integer.parseInt(sd[2]);
			int cboCashBook_Month = Integer.parseInt(sd[1]);

			String txtManualProceedingNo1 = request
					.getParameter("txtManualProceedingNo");
			// int txtManualProceedingNo =
			// Integer.parseInt(txtManualProceedingNo1);

			java.sql.Date ManualProceedingDate = null;
			java.util.GregorianCalendar c22;
			String[] sdd = request.getParameter("txtManualProceedingDate")
					.split("/");
			c22 = new java.util.GregorianCalendar(Integer.parseInt(sdd[2]),
					Integer.parseInt(sdd[1]) - 1, Integer.parseInt(sdd[0]));
			java.util.Date dd = c22.getTime();
			ManualProceedingDate = new Date(dd.getTime());

			java.sql.Date InvoiceReceivedDate = null;
			java.util.GregorianCalendar c5;
			String indat = request.getParameter("txtInvoiceReceivedDate");
			if ((indat.equalsIgnoreCase("")) || (indat.equalsIgnoreCase(null))) {

			} else {
				String[] sd5 = request.getParameter("txtInvoiceReceivedDate")
						.split("/");
				c5 = new java.util.GregorianCalendar(Integer.parseInt(sd5[2]),
						Integer.parseInt(sd5[1]) - 1, Integer.parseInt(sd5[0]));
				java.util.Date d5 = c5.getTime();
				InvoiceReceivedDate = new Date(d5.getTime());
			}

			String txtNoofInvoices1 = request.getParameter("txtNoofInvoices");
			int txtNoofInvoices = 0;
			if ((txtNoofInvoices1.equalsIgnoreCase(""))
					|| (txtNoofInvoices1.equalsIgnoreCase(null))) {

			} else {
				txtNoofInvoices = Integer.parseInt(txtNoofInvoices1);
			}

			String rdoMTC_70_Register = request
					.getParameter("rdoMTC_70_Register");

			String txtTotalSanctionAmount1 = request
					.getParameter("txtTotalSanctionAmount");
			float txtTotalSanctionAmount = Float
					.parseFloat(txtTotalSanctionAmount1);

			String txtTotalBillAmount1 = request
					.getParameter("txtTotalBillAmount");
			float txtTotalBillAmount = Float.parseFloat(txtTotalBillAmount1);

			String txtDeductedAmount1 = request
					.getParameter("txtDeductedAmount");
			float txtDeductedAmount = Float.parseFloat(txtDeductedAmount1);

			String txtAcc_HeadCode1 = request.getParameter("txtAcc_HeadCode");
			int txtAcc_HeadCode = Integer.parseInt(txtAcc_HeadCode1);

			int txtPayeeType = Integer.parseInt(request
					.getParameter("txtPayeeType"));

			String txtPayeeCode1 = request.getParameter("txtPayeeCode");
			int txtPayeeCode = Integer.parseInt(txtPayeeCode1);

			String txtEmpID_mas1 = request.getParameter("txtEmpID_mas");
			int txtEmpID_mas = Integer.parseInt(txtEmpID_mas1);

			// String cboOffice1 = request.getParameter("cboOffice");
			// int cboOffice = Integer.parseInt(cboOffice1);

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

				if (cboCashBook_Month > 3) {
					year1 = cboCashBook_Year;
					year2 = (cboCashBook_Year + 1);
				} else {

					year1 = (cboCashBook_Year - 1);
					year2 = (cboCashBook_Year);

				}

				/*
				 * int error_code = -1; cs1=connection.prepareCall(
				 * "{call FAS_BILL_PROCEDURE_CUR(?,?,?,?) }");
				 * cs1.setInt(1,cboAcc_UnitCode); cs1.setInt(2,year1);
				 * cs1.setInt(3,year2); cs1.registerOutParameter(4,
				 * java.sql.Types.NUMERIC); cs1.execute(); error_code =
				 * cs1.getInt(4); System.out.println(error_code); if (error_code
				 * ==0) { System.out.println("failure"); } else {
				 * System.out.println("success"); }
				 * 
				 * txtBillNo=error_code;
				 */
				/*String qry = "update FAS_BILL_REGISTERNEW set " +
				// "CASHBOOK_YEAR,CASHBOOK_MONTH=?," +
						"BILL_DATE='" + BillDate + "',"
						+ "MANUAL_PROCEEDING_NO=" + txtManualProceedingNo1
						+ ",MANUAL_PROCEEDING_DATE='" + ManualProceedingDate
						+ "',INVOICE_RECEIVED_DATE='" + InvoiceReceivedDate
						+ "',NO_OFINVOICES=" + txtNoofInvoices
						+ ",MTC70ENTRY='" + rdoMTC_70_Register + "',"
						+ "TOTAL_BILL_AMOUNT=" + txtTotalBillAmount
						+ ",ACCOUNT_HEAD_CODE=" + txtAcc_HeadCode
						+ ",PAYEE_TYPE_CODE=" + txtPayeeType + ",PAYEE_CODE="
						+ txtPayeeCode + "," + "BILL_PROCESSING_DONE_BY="
						+ txtEmpID_mas + ",REF_NO=" + txtRefNo + ",REF_DATE='"
						+ RefDate + "',REMARKS='" + mtxtRemarks
						+ "',STATUS='L'," + "BUDGET_PROVISION="
						+ txtBudgetProvision + ",BUDGET_SO_FAR_SPENT="
						+ txtBudgetSpent + ",UPDATED_BY_USERID='" + userid
						+ "'" + ",DEDUCTED_AMOUNT=" + txtDeductedAmount
						+ ",TOTAL_SANCTIONED_AMOUNT=" + txtTotalSanctionAmount
						+ ",PROCEEDING_RECD_DATE='" + ManualProceedingDate
						+ "' where " + "ACCOUNTING_UNIT_ID=" + cboAcc_UnitCode
						+ " and "
						+ "ACCOUNTING_UNIT_OFFICE_ID="
						+ cmbOffice_code
						+ " and "
						+
						// "CASHBOOK_YEAR=?,CASHBOOK_MONTH=?," +
						"BILL_MAJOR_TYPE=" + cboBillMajorType + " and "
						+ "BILL_MINOR_TYPE_CODE=" + cboBillMinorType
						+ " and BILL_SUB_TYPE_CODE=" + cboBillSubType + " and "
						+ "BILL_NO=" + txtBillNo;*/

				//System.out.println("qry >> " + qry);
				ps1 = connection
						.prepareStatement("update FAS_BILL_REGISTERNEW set "							
								+ "BILL_DATE=?,MANUAL_PROCEEDING_NO=?,MANUAL_PROCEEDING_DATE=?,INVOICE_RECEIVED_DATE=?,NO_OFINVOICES=?,MTC70ENTRY=?,"
								+ "TOTAL_BILL_AMOUNT=?,ACCOUNT_HEAD_CODE=?,PAYEE_TYPE_CODE=?,PAYEE_CODE=?,"
								+ "BILL_PROCESSING_DONE_BY=?,REF_NO=?,REF_DATE=?,REMARKS=?,STATUS=?,"
								+ "BUDGET_PROVISION=?,BUDGET_SO_FAR_SPENT=?,UPDATED_BY_USERID=?,UPDATED_DATE=?"
								+ ",DEDUCTED_AMOUNT=?,TOTAL_SANCTIONED_AMOUNT=?"
								+ ",PROCEEDING_RECD_DATE=? WHERE "
								+ " ACCOUNTING_UNIT_ID=? and "
								+ " ACCOUNTING_UNIT_OFFICE_ID=? and "+							
								" BILL_MAJOR_TYPE=? and "
								+ " BILL_MINOR_TYPE_CODE=? and BILL_SUB_TYPE_CODE=? and "
								+ " BILL_NO=?");
				 ps1.setDate(1, BillDate);
				 System.out.println("txtManualProceedingNo1:::"
				 		+ txtManualProceedingNo1);
				 ps1.setString(2, txtManualProceedingNo1);
				 ps1.setDate(3, ManualProceedingDate);
				 ps1.setDate(4, InvoiceReceivedDate);
				 ps1.setInt(5, txtNoofInvoices);
				 ps1.setString(6, rdoMTC_70_Register);
				 	ps1.setFloat(7, txtTotalBillAmount);
				 	ps1.setInt(8, txtAcc_HeadCode);
				 	System.out.println("txtPayeeType:::" + txtPayeeType);
				 	ps1.setInt(9, txtPayeeType);
				 	ps1.setInt(10, txtPayeeCode);
				 System.out.println("txtPayeeCode:::" + txtPayeeCode);
				 	ps1.setInt(11, txtEmpID_mas);
				 	ps1.setInt(12, txtRefNo);
				 ps1.setDate(13, RefDate);
				 ps1.setString(14, mtxtRemarks);
				 System.out.println(":mtxtRemarks" + mtxtRemarks);
				 ps1.setString(15, "L");
				 ps1.setFloat(16, txtBudgetProvision);
				 ps1.setFloat(17, txtBudgetSpent);
				 ps1.setString(18, userid);
				 ps1.setTimestamp(19, ts);
				 ps1.setFloat(20, txtDeductedAmount);
				 ps1.setFloat(21, txtTotalSanctionAmount);
				 ps1.setDate(22, ManualProceedingDate);
				 ps1.setInt(23, cboAcc_UnitCode);
				 ps1.setInt(24, cmbOffice_code);
				 ps1.setInt(25, cboBillMajorType);
				 ps1.setInt(26, cboBillMinorType);
				 ps1.setInt(27, cboBillSubType);
				 ps1.setInt(28, txtBillNo);
				 System.out.println("pas 2 Testing >>>  " + ps1);
				int k = ps1.executeUpdate();
				System.out.println("k valur "+k);
				// xml = xml + "<BillNo>" + txtBillNo + "</BillNo>";
				if (k != 0)
					xml = xml + "<flag>success</flag>";
				else
					xml = xml + "<flag>failure</flag>";
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}

		} else if (strCommand.equalsIgnoreCase("loadGrid")) {
			xml = xml + "<response><command>loadGrid</command>";
			

			String cboAcc_UnitCode1 = request.getParameter("unitid1");
			//System.out.println("cboAcc_UnitCode1 "+cboAcc_UnitCode1);
			int cboAcc_UnitCode = Integer.parseInt(cboAcc_UnitCode1);
			

			String cboOffice_code1 = request.getParameter("officeid1");
			//System.out.println("cboOffice_code1 "+cboOffice_code1);
			int cmbOffice_code = Integer.parseInt(cboOffice_code1);
			

			int BillNo = 0;
			String BillDate = null;
			String InvoiceReceivedDate = null;
			String RefDate = null;
			String InvoiceDate = null;
			String ManualProceedingDatee = null;
			try {

				String su1 = "select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_OFFICE_ID,BILL_NO,BILL_MAJOR_TYPE from FAS_BILL_REGISTERNEW where ACCOUNTING_UNIT_ID="+cboAcc_UnitCode+" AND ACCOUNTING_UNIT_OFFICE_ID="+cmbOffice_code+" ";
				ps1 = connection.prepareStatement(su1);
				results = ps1.executeQuery();
				if (results.next()) {
					String su = "select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_OFFICE_ID,BILL_NO,PAYEE_CODE,BILL_DATE,BILL_PROCESSING_DONE_BY,ACCOUNT_HEAD_CODE,REMARKS,UPDATED_BY_USERID,UPDATED_DATE,BILL_MINOR_TYPE_CODE,BILL_MAJOR_TYPE,BILL_SUB_TYPE_CODE,decode(INVOICE_RECEIVED_DATE,null,'-',INVOICE_RECEIVED_DATE)as INVOICE_RECEIVED_DATE1," +
							"invoice_received_date,NO_OFINVOICES,PAYEE_TYPE_CODE,TOTAL_SANCTIONED_AMOUNT,TOTAL_BILL_AMOUNT,REF_NO,REF_DATE," +
							"MANUAL_PROCEEDING_NO,MANUAL_PROCEEDING_DATE,MTC70ENTRY,DEDUCTED_AMOUNT," +
							"(select PAYEE_CODENAME from PAYEE_TYPE_CODE_NAME_VIEW where PAYEE_TYPE= b.Payee_Type_Code and PAYEE_CODE  =b.Payee_Code) as paydesc," +
							"(select  bill_minor_type_desc from fas_bill_minor_types_mst t " +
							"WHERE t.BILL_MINOR_TYPE_CODE=b.BILL_MINOR_TYPE_CODE " +
							"and t.bill_major_type_code=b.bill_major_type) as bill_minor_type_desc1," +
							"(select bill_major_type_desc from FAS_BILL_MAJOR_TYPES f " +
							"where f.bill_major_type_code=b.BILL_MAJOR_TYPE) as bill_major_type_desc," +
							"(select BILL_SUB_TYPE_DESC from FAS_BILL_SUB_TYPES s " +
							"where s.BILL_MAJOR_TYPE_CODE=b.bill_major_type " +
							"and S.BILL_MINOR_TYPE_CODE = B.BILL_MINOR_TYPE_CODE " +
							"and S.BILL_SUB_TYPE_CODE = B.BILL_SUB_TYPE_CODE) as sub_type_desc from FAS_BILL_REGISTERNEW b " +
							"where ACCOUNTING_UNIT_ID="+cboAcc_UnitCode+" AND ACCOUNTING_UNIT_OFFICE_ID="+cmbOffice_code;
					
					/*"select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_OFFICE_ID,BILL_NO,PAYEE_CODE,BILL_DATE,BILL_PROCESSING_DONE_BY,ACCOUNT_HEAD_CODE,REMARKS,UPDATED_BY_USERID,UPDATED_DATE,BILL_MINOR_TYPE_CODE,BILL_MAJOR_TYPE,BILL_SUB_TYPE_CODE,decode(INVOICE_RECEIVED_DATE,null,'-',INVOICE_RECEIVED_DATE)as INVOICE_RECEIVED_DATE1," +
					"invoice_received_date,NO_OFINVOICES,PAYEE_TYPE_CODE,TOTAL_SANCTIONED_AMOUNT,TOTAL_BILL_AMOUNT,REF_NO,REF_DATE," +
					"MANUAL_PROCEEDING_NO,MANUAL_PROCEEDING_DATE,MTC70ENTRY,DEDUCTED_AMOUNT," +
					"(select EMPLOYEE_NAME from HRM_MST_EMPLOYEES where EMPLOYEE_ID=PAYEE_CODE) as paydesc," +
					"(select  bill_minor_type_desc from fas_bill_minor_types_mst t " +
					"WHERE t.BILL_MINOR_TYPE_CODE=b.BILL_MINOR_TYPE_CODE " +
					"and t.bill_major_type_code=b.bill_major_type) as bill_minor_type_desc1," +
					"(select bill_major_type_desc from FAS_BILL_MAJOR_TYPES f " +
					"where f.bill_major_type_code=b.BILL_MAJOR_TYPE) as bill_major_type_desc," +
					"(select BILL_SUB_TYPE_DESC from FAS_BILL_SUB_TYPES s " +
					"where s.BILL_MAJOR_TYPE_CODE=b.bill_major_type " +
					"and S.BILL_MINOR_TYPE_CODE = B.BILL_MINOR_TYPE_CODE " +
					"and S.BILL_SUB_TYPE_CODE = B.BILL_SUB_TYPE_CODE) as sub_type_desc from FAS_BILL_REGISTERNEW b " +
					"where ACCOUNTING_UNIT_ID="+cboAcc_UnitCode+" AND ACCOUNTING_UNIT_OFFICE_ID="+cmbOffice_code;*/
					
					System.out.println(su);
					ps = connection.prepareStatement(su);
					rs = ps.executeQuery();
					xml = xml + "<flag>success</flag>";
					while (rs.next()) {
						Date BillDate1 = rs.getDate("BILL_DATE");
						String InvoiceReceivedDate11 = rs
								.getString("INVOICE_RECEIVED_DATE1");
						Date RefDate1 = rs.getDate("REF_DATE");
						Date ManualProceedingDate1 = rs
								.getDate("MANUAL_PROCEEDING_DATE");

						String Stringdate = BillDate1.toString();
						
						String Stringdate2 = RefDate1.toString();
						String Stringdate3 = ManualProceedingDate1.toString();

						String[] ddd = Stringdate.split("-");
					
						String[] ddd2 = Stringdate2.split("-");
						String[] ddd3 = Stringdate3.split("-");

						int day = Integer.parseInt(ddd[2]);
						int month = Integer.parseInt(ddd[1]);
						int year = Integer.parseInt(ddd[0]);

						if((InvoiceReceivedDate11.equals("-"))||(InvoiceReceivedDate11.equals(null))){
					System.out.println("null ");
					InvoiceReceivedDate="-";
						}else{
							Date InvoiceReceivedDate1 = rs
							.getDate("INVOICE_RECEIVED_DATE");
							String Stringdate1 = InvoiceReceivedDate1.toString();
							String[] ddd1 = Stringdate1.split("-");
							int day1 = Integer.parseInt(ddd1[2]);
							int month1 = Integer.parseInt(ddd1[1]);
							int year1 = Integer.parseInt(ddd1[0]);
							if (month1 >= 10) {
								InvoiceReceivedDate = (day1 + "/" + month1 + "/" + year1);
							} else {
								InvoiceReceivedDate = (day1 + "/0" + month1 + "/" + year1);
							}
						}
						int day2 = Integer.parseInt(ddd2[2]);
						int month2 = Integer.parseInt(ddd2[1]);
						int year2 = Integer.parseInt(ddd2[0]);

						int day3 = Integer.parseInt(ddd3[2]);
						int month3 = Integer.parseInt(ddd3[1]);
						int year3 = Integer.parseInt(ddd3[0]);

						if (month >= 10) {
							BillDate = (day + "/" + month + "/" + year);
						} else {
							BillDate = (day + "/0" + month + "/" + year);
						}

						
						if (month2 >= 10) {
							RefDate = (day2 + "/" + month2 + "/" + year2);
						} else {
							RefDate = (day2 + "/0" + month2 + "/" + year2);
						}
						if (month3 >= 10) {
							ManualProceedingDatee = (day3 + "/" + month3 + "/" + year3);
						} else {
							ManualProceedingDatee = (day3 + "/0" + month3 + "/" + year3);
						}

						xml = xml + "<AccUnitCode>"
								+ rs.getInt("ACCOUNTING_UNIT_ID")
								+ "</AccUnitCode>";

						xml = xml + "<AccForOfficeCode>"
								+ rs.getInt("ACCOUNTING_UNIT_OFFICE_ID")
								+ "</AccForOfficeCode>";

						xml = xml + "<BillMajorType>"
								+ rs.getInt("BILL_MAJOR_TYPE")
								+ "</BillMajorType>";

						xml = xml + "<billMinorTypeCode>"
								+ rs.getInt("BILL_MINOR_TYPE_CODE")
								+ "</billMinorTypeCode>";

						xml = xml + "<billSubTypeCode>"
								+ rs.getInt("BILL_SUB_TYPE_CODE")
								+ "</billSubTypeCode>";

						xml = xml + "<BillNo>" + rs.getInt("BILL_NO")
								+ "</BillNo>";

						xml = xml + "<billDate>" + BillDate + "</billDate>";

						xml = xml + "<ManualProceedingNo>"
								+ rs.getString("MANUAL_PROCEEDING_NO")
								+ "</ManualProceedingNo>";

						xml = xml + "<ManualProceedingDatee>"
								+ ManualProceedingDatee
								+ "</ManualProceedingDatee>";

						xml = xml + "<InvoiceReceivedDate>"
								+ InvoiceReceivedDate
								+ "</InvoiceReceivedDate>";

						xml = xml + "<NoOfInvoies>"
								+ rs.getInt("NO_OFINVOICES") + "</NoOfInvoies>";

						xml = xml + "<MTC70Required>"
								+ rs.getString("MTC70ENTRY")
								+ "</MTC70Required>";
						//Lakshmi
						xml = xml + "<TotalSanctionAmount>"
						+ rs.getInt("TOTAL_SANCTIONED_AMOUNT")
						+ "</TotalSanctionAmount>";

						xml = xml + "<TotalBillAmount>"
								+ rs.getInt("TOTAL_BILL_AMOUNT")
								+ "</TotalBillAmount>";
						
						xml = xml + "<TotalDeductedAmount>"
						+ rs.getFloat("DEDUCTED_AMOUNT")
						+ "</TotalDeductedAmount>";

						xml = xml + "<AccHeadCode>"
								+ rs.getInt("ACCOUNT_HEAD_CODE")
								+ "</AccHeadCode>";

						xml = xml + "<PayeeType>"
								+ rs.getString("PAYEE_TYPE_CODE")
								+ "</PayeeType>";

						xml = xml + "<PayeeCode>" + rs.getInt("PAYEE_CODE")
								+ "</PayeeCode>";
						
						xml = xml + "<paydesc>" + rs.getString("paydesc")
						+ "</paydesc>";
						
						
						xml = xml + "<BillProcessingDoneBy>"
								+ rs.getInt("BILL_PROCESSING_DONE_BY")
								+ "</BillProcessingDoneBy>";

						xml = xml + "<RefNo>" + rs.getInt("REF_NO")
								+ "</RefNo>";

						xml = xml + "<RefDate>" + RefDate + "</RefDate>";

						xml = xml + "<Remarks>" + rs.getString("REMARKS")
								+ "</Remarks>";
						
						xml = xml + "<bill_minor_type_desc1>" + rs.getString("bill_minor_type_desc1")
						+ "</bill_minor_type_desc1>";
						
						xml = xml + "<bill_major_type_desc>" + rs.getString("bill_major_type_desc")
						+ "</bill_major_type_desc>";
						
						xml = xml + "<sub_type_desc>" + rs.getString("sub_type_desc")
						+ "</sub_type_desc>";
						
						
					}
				} else {
					xml = xml + "<flag>NoData</flag>";
				}
			} catch (Exception e) {
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
		}else if (strCommand.equalsIgnoreCase("getBillDetails")) {
			int c = 0;
			xml = xml + "<response><command>getBillDetails</command>";
			int cboAcc_UnitCode = Integer.parseInt(request
					.getParameter("cboAcc_UnitCode"));
			int cboOffice_code = Integer.parseInt(request
					.getParameter("cmbOffice_code"));
			int cboBillMajorType = Integer.parseInt(request
					.getParameter("cboBillMajorType"));
			int cboBillMinorType = Integer.parseInt(request
					.getParameter("cboBillMinorType"));
			int cboBillSubType = Integer.parseInt(request
					.getParameter("cboBillSubType"));
			int cboBillNo = Integer.parseInt(request.getParameter("cboBillNo"));
			String Qry = "SELECT Accounting_Unit_Id, "
					+ "  ACCOUNTING_UNIT_OFFICE_ID, Cashbook_Year,   Cashbook_Month,   Bill_No, "
					+ "  SANCTION_PROC_NO,   Payee_Code,   BILL_DATE,   Bill_Processing_Done_By, "
					+ "  ACCOUNT_HEAD_CODE,  Remarks,   Bill_Scrutiny_Done,   Bill_Scrutiny_By, "
					+ "  BILL_SCRUTINY_DATE,   Status,Bill_Minor_Type_Code,   BILL_MAJOR_TYPE,  Bill_Sub_Type_Code,   Proceeding_Recd_Date, "
					+ "  Invoice_Received_Date,   No_Ofinvoices,   PAYEE_TYPE_CODE,   Total_Sanctioned_Amount, "
					+ "  Total_Bill_Amount,  Mtc70entry,   REF_NO,   Ref_Date,   Drawing_Officer_Approve_Date,   TREASURY_VERIFY_DATE, "
					+ "  Dor_By_Pre_Audit,   Pre_Audit_Received_By, "
					+ "  PRE_AUDIT_BY,   Pre_Audit_Date, "
					+ "  Bill_Approved,   Date_Sent_To_Treasury_Section, "
					+ "  Pre_Audit_Remarks,   Budget_Provision, "
					+ "  Budget_So_Far_Spent,   Manual_Proceeding_No, "
					+ "  Manual_Proceeding_Date, " + "  Mtc_70_Register_Date, "
					+ "  Deducted_Amount, " + "  Pass_Order_Date, "
					+ "  Pass_Order_By, " + "  Pass_Order_Amount, "
					+ "  Drawing_Officer_Code, " + "  Reason_For_Reject, "
					+ "  PAYABLE_TO, " + "  Memo_Entry, "
					+ "  Memo_Updated_Date " + "FROM Fas_Bill_Registernew "
					+ "WHERE Accounting_Unit_Id     =? "
					+ "AND Accounting_Unit_Office_Id=? "
					+ "AND Bill_Major_Type     =? "
					+ "AND Bill_Minor_Type_Code         =? "
					+ "AND Bill_Sub_Type_Code       =? "
					+ "AND bill_no                  =?";
			try {

				ps = connection.prepareStatement(Qry);
				ps.setInt(1, cboAcc_UnitCode);
				ps.setInt(2, cboOffice_code);
				ps.setInt(3, cboBillMajorType);
				ps.setInt(4, cboBillMinorType);
				ps.setInt(5, cboBillSubType);
				ps.setInt(6, cboBillNo);
				results = ps.executeQuery();
				while (results.next()) {
					xml += "<Accounting_Unit_Id>"
							+ results.getInt("Accounting_Unit_Id")
							+ "</Accounting_Unit_Id>";
					xml += "<ACCOUNTING_UNIT_OFFICE_ID>"
							+ results.getInt("ACCOUNTING_UNIT_OFFICE_ID")
							+ "</ACCOUNTING_UNIT_OFFICE_ID>";
					xml += "<Cashbook_Year>" + results.getInt("Cashbook_Year")
							+ "</Cashbook_Year>";
					xml += "<Cashbook_Month>"
							+ results.getInt("Cashbook_Month")
							+ "</Cashbook_Month>";
					xml += "<Bill_No>" + results.getInt("Bill_No")
							+ "</Bill_No>";
					xml += "<SANCTION_PROC_NO>"
							+ results.getString("SANCTION_PROC_NO")
							+ "</SANCTION_PROC_NO>";
					xml += "<Payee_Code>" + results.getInt("Payee_Code")
							+ "</Payee_Code>";
					xml += "<BILL_DATE>" + results.getDate("BILL_DATE")
							+ "</BILL_DATE>";
					xml += "<Bill_Processing_Done_By>"
							+ results.getString("Bill_Processing_Done_By")
							+ "</Bill_Processing_Done_By>";
					xml += "<ACCOUNT_HEAD_CODE>"
							+ results.getInt("ACCOUNT_HEAD_CODE")
							+ "</ACCOUNT_HEAD_CODE>";
					xml += "<Remarks>" + results.getString("Remarks")
							+ "</Remarks>";
					xml += "<Bill_Scrutiny_Done>"
							+ results.getString("Bill_Scrutiny_Done")
							+ "</Bill_Scrutiny_Done>";
					xml += "<Bill_Scrutiny_By>"
							+ results.getString("Bill_Scrutiny_By")
							+ "</Bill_Scrutiny_By>";
					xml += "<BILL_SCRUTINY_DATE>"
							+ results.getDate("BILL_SCRUTINY_DATE")
							+ "</BILL_SCRUTINY_DATE>";
					xml += "<Status>" + results.getString("Status")
							+ "</Status>";
					xml += "<Bill_Minor_Type_Code>"
							+ results.getInt("Bill_Minor_Type_Code")
							+ "</Bill_Minor_Type_Code>";
					xml += "<BILL_MAJOR_TYPE>"
							+ results.getInt("BILL_MAJOR_TYPE")
							+ "</BILL_MAJOR_TYPE>";
					xml += "<Bill_Sub_Type_Code>"
							+ results.getInt("Bill_Sub_Type_Code")
							+ "</Bill_Sub_Type_Code>";
					xml += "<Proceeding_Recd_Date>"
							+ results.getDate("Proceeding_Recd_Date")
							+ "</Proceeding_Recd_Date>";
					xml += "<Invoice_Received_Date>"
							+ results.getDate("Invoice_Received_Date")
							+ "</Invoice_Received_Date>";
					xml += "<No_Ofinvoices>" + results.getInt("No_Ofinvoices")
							+ "</No_Ofinvoices>";
					xml += "<PAYEE_TYPE_CODE>"
							+ results.getInt("PAYEE_TYPE_CODE")
							+ "</PAYEE_TYPE_CODE>";
					xml += "<Total_Sanctioned_Amount>"
							+ results.getBigDecimal("Total_Sanctioned_Amount")
							+ "</Total_Sanctioned_Amount>";
					xml += "<Total_Bill_Amount>"
							+ results.getBigDecimal("Total_Bill_Amount")
							+ "</Total_Bill_Amount>";
					xml += "<Mtc70entry>" + results.getString("Mtc70entry")
							+ "</Mtc70entry>";
					xml += "<REF_NO>" + results.getInt("REF_NO") + "</REF_NO>";
					xml += "<Ref_Date>" + results.getDate("Ref_Date")
							+ "</Ref_Date>";
					xml += "<Drawing_Officer_Approve_Date>"
							+ results.getDate("Drawing_Officer_Approve_Date")
							+ "</Drawing_Officer_Approve_Date>";
					xml += "<TREASURY_VERIFY_DATE>"
							+ results.getDate("TREASURY_VERIFY_DATE")
							+ "</TREASURY_VERIFY_DATE>";
					xml += "<Dor_By_Pre_Audit>"
							+ results.getString("Dor_By_Pre_Audit")
							+ "</Dor_By_Pre_Audit>";
					xml += "<Pre_Audit_Received_By>"
							+ results.getString("Pre_Audit_Received_By")
							+ "</Pre_Audit_Received_By>";
					xml += "<PRE_AUDIT_BY>" + results.getInt("PRE_AUDIT_BY")
							+ "</PRE_AUDIT_BY>";
					xml += "<Pre_Audit_Date>"
							+ results.getDate("Pre_Audit_Date")
							+ "</Pre_Audit_Date>";
					xml += "<Bill_Approved>"
							+ results.getString("Bill_Approved")
							+ "</Bill_Approved>";
					xml += "<Date_Sent_To_Treasury_Section>"
							+ results.getDate("Date_Sent_To_Treasury_Section")
							+ "</Date_Sent_To_Treasury_Section>";
					xml += "<Pre_Audit_Remarks>"
							+ results.getString("Pre_Audit_Remarks")
							+ "</Pre_Audit_Remarks>";
					xml += "<Budget_Provision>"
							+ results.getString("Budget_Provision")
							+ "</Budget_Provision>";
					xml += "<Budget_So_Far_Spent>"
							+ results.getBigDecimal("Budget_So_Far_Spent")
							+ "</Budget_So_Far_Spent>";
					xml += "<Manual_Proceeding_No>"
							+ results.getString("Manual_Proceeding_No")
							+ "</Manual_Proceeding_No>";
					xml += "<Manual_Proceeding_Date>"
							+ results.getDate("Manual_Proceeding_Date")
							+ "</Manual_Proceeding_Date>";
					xml += "<Mtc_70_Register_Date>"
							+ results.getDate("Mtc_70_Register_Date")
							+ "</Mtc_70_Register_Date>";
					xml += "<Deducted_Amount>"
							+ results.getBigDecimal("Deducted_Amount")
							+ "</Deducted_Amount>";
					xml += "<Pass_Order_Date>"
							+ results.getDate("Pass_Order_Date")
							+ "</Pass_Order_Date>";
					xml += "<Pass_Order_By>"
							+ results.getString("Pass_Order_By")
							+ "</Pass_Order_By>";
					xml += "<Pass_Order_Amount>"
							+ results.getBigDecimal("Pass_Order_Amount")
							+ "</Pass_Order_Amount>";
					xml += "<Drawing_Officer_Code>"
							+ results.getString("Drawing_Officer_Code")
							+ "</Drawing_Officer_Code>";
					xml += "<Reason_For_Reject>"
							+ results.getString("Reason_For_Reject")
							+ "</Reason_For_Reject>";
					xml += "<PAYABLE_TO>" + results.getString("PAYABLE_TO")
							+ "</PAYABLE_TO>";
					xml += "<Memo_Entry>" + results.getString("Memo_Entry")
							+ "</Memo_Entry>";
					xml += "<Memo_Updated_Date>"
							+ results.getDate("Memo_Updated_Date")
							+ "</Memo_Updated_Date>";
					c++;
				}
				if (c != 0)
					xml += "<flag>success</flag>";
				else
					xml += "<flag>failure</flag>";

			} catch (Exception e) {
				xml += "<flag>failure</flag>";
				e.printStackTrace();
			}

		}else if (strCommand.equalsIgnoreCase("loadPayeeCode_Desccp")) {

			int cboAcc_UnitCode = Integer.parseInt(request
					.getParameter("cboAcc_UnitCode"));
			int cboOffice_code = Integer.parseInt(request
					.getParameter("cboOffice_code"));
			int txtPayeeType = Integer.parseInt(request
					.getParameter("txtPayeeType"));

			xml = "<response><command>loadPayeeCode_Desccp</command>";

			try {
				String su = "";
				if (txtPayeeType == 1) {
					/*
					 * dep.EMPLOYEE_ID AS payee_code , off.EMPLOYEE_NAME AS
					 * payee_codeName FROM FAS_DRAWING_OFFICER_MST dep,
					 * HRM_MST_EMPLOYEES OFF WHERE
					 * dep.EMPLOYEE_ID=off.EMPLOYEE_ID
					 */
					su = " select  dep.EMPLOYEE_ID AS payee_code , "
							+ " off.EMPLOYEE_NAME AS payee_codeName  "
							+ " FROM FAS_DRAWING_OFFICER_MST dep, "
							+ " HRM_MST_EMPLOYEES OFF "
							+ "  WHERE dep.EMPLOYEE_ID=off.EMPLOYEE_ID "
							+ "  and ACCOUNTING_UNIT_ID=" + cboAcc_UnitCode
							+ " and ACCOUNTING_FOR_OFFICE_ID= "
							+ cboOffice_code;

				} else if (txtPayeeType == 2) {
					su = "select " + "  BANK_AC_NO      AS payee_code, "
							+ " BANK_AC_TYPE_ID AS payee_codeName "
							+ " FROM FAS_MST_BANK_BALANCE "
							+ "WHERE status='Y' " + " and ACCOUNTING_UNIT_ID="
							+ cboAcc_UnitCode
							+ " and AC_OPERATIONAL_MODE_ID='OPR' ";

				} else if (txtPayeeType == 3) {

					su = "select " + " OFFICE_ID   AS payee_code, "
							+ " OFFICE_NAME AS payee_codeName "
							+ " FROM COM_MST_OFFICES where " + "   OFFICE_ID= "
							+ cboOffice_code;

				} else if (txtPayeeType == 4) {

					su = "select  " + " FIRMS_ID   AS payee_code, "
							+ " FIRMS_NAME AS payee_codeName "
							+ "  FROM COM_FIRMS_sl_MST "
							+ " where ACCOUNTING_UNIT_ID=" + cboAcc_UnitCode
							+ " and ACCOUNTING_FOR_OFFICE_ID= "
							+ cboOffice_code + " and STATUS='L'";

				} else if (txtPayeeType == 5) {
					su = " select payee_code, "
							+ " payee_codeName  "
							+ " from  "
							+ " (SELECT  "
							+ "     PPO_NO      AS payee_code, "
							+ "    to_char (PENSIONER_INITIAL "
							+ "     || ' ' "
							+ "     ||PENSIONER_NAME)     AS payee_codeName, "
							+ "     PAYMENT_OFFICE_ID "
							+ "   FROM HRM_PEN_MST_DETAILS "
							+ "   union all "
							+ "   SELECT  "
							+ "      PPO_NO      AS payee_code,  "
							+ "      to_char(FPENSIONER_INITIAL "
							+ "     || ' ' "
							+ "     ||FPENSIONER_NAME)     AS payee_codeName , "
							+ "     PAYMENT_OFFICE_ID  "
							+ "   from HR_PEN_MST_FAMILY) "
							+ "   where PAYMENT_OFFICE_ID=" + cboOffice_code;

				} else if (txtPayeeType == 6) {

					su = "  SELECT "
							+ " USER_CATEGORY_ID      AS payee_code, "
							+ " USER_NAME AS payee_codeName "
							+ " FROM SEC_MST_OTHER_USERS_PROFILE WHERE USER_ID='"
							+ userid + "'";

				} else if (txtPayeeType == 7) {
					// su="";

					// employee

				} else if (txtPayeeType == 8) {

					su = " select " + " CONTRACTOR_ID   AS payee_code, "
							+ " CONTRACTOR_NAME AS payee_codeName "
							+ "  FROM PMS_MST_CONTRACTORS_VIEW "
							+ " where OFFICE_ID=" + cboOffice_code;

				} else if (txtPayeeType == 9) {

					su = "  select SUPPLIER_ID   AS payee_code, "
							+ " SUPPLIER_NAME AS payee_codeName "
							+ " FROM COM_SUPPLIER_sl_MST "
							+ " where ACCOUNTING_UNIT_ID=" + cboAcc_UnitCode
							+ " and ACCOUNTING_FOR_OFFICE_ID= "
							+ cboOffice_code;

				} else // if(txtPayeeType==10)
				{
					su = " select  off.OTHER_DEPT_OFFICE_ALIAS_ID AS payee_code ,"
							+ " dep.OTHER_DEPT_NAME  "
							+ " || '-'  "
							+ " || off.OTHER_DEPT_OFFICE_NAME AS payee_codeName "
							+ " FROM HRM_MST_OTHER_DEPTS dep, "
							+ "   HRM_MST_OTHER_DEPT_OFFICES OFF "
							+ "  WHERE dep.OTHER_DEPT_ID=off.OTHER_DEPT_ID ";
				}/*
				 * else{ System.out.println(); }
				 */

				System.out.println("su " + su);
				ps = connection.prepareStatement(su);
				rs = ps.executeQuery();
				if (rs.next()) {
					xml = xml + "<flag>success</flag>";
					ResultSet rsss = ps.executeQuery();
					while (rsss.next()) {
						xml = xml + "<payeecode>"
								+ rsss.getString("payee_code") + "</payeecode>";

						xml = xml + "<payeecodeDesc><![CDATA["
								+ rsss.getString("payee_codeName")
								+ "]]></payeecodeDesc>";

						xml = xml + "<payeecodeDesc_Load>"
								+ rsss.getString("payee_code") + "-"
								+ rsss.getString("payee_codeName")
								+ "</payeecodeDesc_Load>";
					}

				} else {
					xml = xml + "<flag>failure</flag>";
				}

			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}
		}
  
		else if (strCommand.equalsIgnoreCase("getBillNo")) {
			int c = 0;
			xml = xml + "<response><command>getBillNo</command>";
			int cboAcc_UnitCode = Integer.parseInt(request
					.getParameter("cboAcc_UnitCode"));
			int cboOffice_code = Integer.parseInt(request
					.getParameter("cmbOffice_code"));

			int cboBillMajorType = Integer.parseInt(request
					.getParameter("cboBillMajorType"));
			int cboBillMinorType = Integer.parseInt(request
					.getParameter("cboBillMinorType"));

			int cboBillSubType = Integer.parseInt(request
					.getParameter("cboBillSubType"));
			String qry = "SELECT Bill_No " + " FROM Fas_Bill_Registernew "
					+ " WHERE Accounting_Unit_Id     =? "
					+ " AND Accounting_Unit_Office_Id=? "
					+ " AND Bill_Major_Type     =? "
					+ " AND Bill_Minor_Type_Code        =? "
					+ " AND BILL_SUB_TYPE_CODE       =?";
			try {
				ps = connection.prepareStatement(qry);
				ps.setInt(1, cboAcc_UnitCode);
				ps.setInt(2, cboOffice_code);
				ps.setInt(3, cboBillMajorType);
				ps.setInt(4, cboBillMinorType);
				ps.setInt(5, cboBillSubType);
				results = ps.executeQuery();
				while (results.next()) {
					xml += "<billno>" + results.getInt("Bill_No") + "</billno>";
					c++;
				}
				if (c != 0)
					xml = xml + "<flag>success</flag>";
				else
					xml = xml + "<flag>failure</flag>";

			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}

		}else if (strCommand.equalsIgnoreCase("Edit")) {

			xml = "<response><command>Edit</command>";
			int txtBillNo = Integer.parseInt(request.getParameter("txtBillNo"));
			int txtEmpID_mas = Integer.parseInt(request
					.getParameter("txtEmpID_mas"));
			int BillMajorType = 0, BillMinorType = 0, BillSubType = 0;

			int cboAcc_UnitCode = Integer.parseInt(request
					.getParameter("cmbAcc_UnitCode"));
			int cboOffice_code = Integer.parseInt(request
					.getParameter("cmbOffice_code"));
			String txtaccountheadcode1 =request.getParameter("txtAcc_HeadCode");
			
			int txtaccountheadcode =Integer.parseInt(txtaccountheadcode1);
           int payco=Integer.parseInt(request.getParameter("paydesc"));
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
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}

			/*try {

				String su1 = "select BILL_SUB_TYPE_CODE,BILL_SUB_TYPE_DESC from FAS_BILL_SUB_TYPES where BILL_MAJOR_TYPE_CODE=? and BILL_MINOR_TYPE_CODE=? and BILL_SUB_TYPE_CODE=? and status='L'";
				ps1 = connection.prepareStatement(su1);
				ps1.setInt(1, BillMajorType);
				ps1.setInt(2, BillMinorType);
				ps1.setInt(3, BillSubType);
				results = ps1.executeQuery();
				if (results.next()) {
					try {

						String su = "select BILL_SUB_TYPE_CODE,BILL_SUB_TYPE_DESC from FAS_BILL_SUB_TYPES where BILL_MAJOR_TYPE_CODE=? and BILL_MINOR_TYPE_CODE=? and BILL_SUB_TYPE_CODE=? and status='L'";
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
			}*/
			
			
			
			try{
				
				
				
				String su0 = " select SUB_TYPE_APPLICABLE from FAS_BILL_MINOR_TYPES_MST where BILL_MAJOR_TYPE_CODE=? and  BILL_MINOR_TYPE_CODE=? and status='L'";
				PreparedStatement ps01 = connection.prepareStatement(su0);
				ps01.setInt(1, BillMajorType);
				ps01.setInt(2, BillMinorType);
				
				ResultSet results1 = ps01.executeQuery();
			//	System.out.println("su0 "+su0);
				if(results1.next()){
					String subapplicable=results1.getString("SUB_TYPE_APPLICABLE");
					//System.out.println("subapplicable==> "+subapplicable);
				if(subapplicable.equalsIgnoreCase("Y")){
					
					xml = xml + "<billSubTypeCode_app>Y</billSubTypeCode_app>";
					
					String su1 = "select BILL_SUB_TYPE_CODE,BILL_SUB_TYPE_DESC from FAS_BILL_SUB_TYPES where BILL_MAJOR_TYPE_CODE=? and BILL_MINOR_TYPE_CODE=? and status='L'";
					ps1 = connection.prepareStatement(su1);
					ps1.setInt(1, BillMajorType);
					ps1.setInt(2, BillMinorType);
					
					results = ps1.executeQuery();
					if (results.next()) {
						//try {

							String su = "select BILL_SUB_TYPE_CODE,BILL_SUB_TYPE_DESC from FAS_BILL_SUB_TYPES where BILL_MAJOR_TYPE_CODE=? and BILL_MINOR_TYPE_CODE=? and status='L'";
							ps = connection.prepareStatement(su);
							ps.setInt(1, BillMajorType);
							ps.setInt(2, BillMinorType);
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
						/*} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
							xml = xml + "<flag>failure</flag>";
						}*/
					} else {
						xml = xml + "<flag>NoData</flag>";
					}
					
				}else if(subapplicable.equalsIgnoreCase("N")){
					xml = xml + "<flag>success</flag>";
					xml = xml + "<billSubTypeCode_app>N</billSubTypeCode_app>";
				}
				
				}
				
			}catch(Exception ee){
				
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
			try{
				 ps = connection.prepareStatement("select ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC,BALANCE_TYPE," +
				 		"SUB_LEDGER_TYPE_APPLICABLE,REMARKS,sl_mandatory from COM_MST_ACCOUNT_HEADS where USAGE_STATUS='Y' " +
				 		"and ACCOUNT_HEAD_CODE=?");
                 ps.setInt(1, txtaccountheadcode);
                ResultSet result = ps.executeQuery();
                 if (result.next()) {
                     xml =xml + "<flag4>success</flag4><hid>" + txtaccountheadcode + "</hid><hdesc>" +
					   result.getString("ACCOUNT_HEAD_DESC") + "</hdesc>"; 					  
                     
                 } else {
                     System.out.println("No record found");
                     xml = xml + "<flag4>failure</flag4>";
                 }
			}catch(Exception e){
				
			}
			try {

				//String su1 = "select EMPLOYEE_NAME,employee_initial from HRM_MST_EMPLOYEES where EMPLOYEE_ID=?";
				/*String su1 =" select he.employee_name,he.employee_initial,he.employee_id,hd.designation_short_name,hd.designation "+
				" from hrm_mst_employees he,hrm_mst_designations hd "+
				" where he.employee_id=? and hd.designation_id=(select DESIGNATION_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=? and OFFICE_ID=?)";*/
				
				String su1 =" select PAYEE_TYPE,PAYEE_CODE,PAYEE_CODENAME  from PAYEE_TYPE_CODE_NAME_VIEW "+
				" where PAYEE_CODE="+payco;
				
				ps = connection.prepareStatement(su1);
				//ps.setInt(1, payco);
				//ps.setInt(2, payco);
				//ps.setInt(3, cboOffice_code);
				rs = ps.executeQuery();
				xml = xml + "<flag>success</flag>";
				while (rs.next()) {
					xml = xml + "<paydesc>"
							//+ rs.getString("EMPLOYEE_NAME")+"."+rs.getString("employee_initial")
							//+"("+rs.getString("designation_short_name")+")"
							+ rs.getString("PAYEE_CODENAME")
							+ "</paydesc>";

					xml = xml + "<payid>" + payco + "</payid>";
				}
				ps.close();
				rs.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
			
			
			
			try {

				String su1 = "select supporting_invoice from fas_sup_inv_bill_types where accounting_unit_id="+cboAcc_UnitCode+" and accounting_for_office_id="+cboOffice_code+" and bill_major_type_code= "+BillMajorType+
       " and bill_minor_type_code="+BillMinorType+" and bill_sub_type_code="+BillSubType+" and STATUS='L' ";
				ps = connection.prepareStatement(su1);
				rs = ps.executeQuery();
				xml = xml + "<flag20>success</flag20>";
				if (rs.next()) {

					xml = xml + "<supporting_invoice>" + rs.getString("supporting_invoice")
							+ "</supporting_invoice>";	
					
				}else{
					xml = xml + "<supporting_invoice>" + "N"
					+ "</supporting_invoice>";
				}
				ps.close();
				rs.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				xml = xml + "<flag20>failure</flag20>";
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
			String ssyr=year1.substring(2,4);
			
			String financialYear1 = (year + "-" + ssyr);

			try {

				String su1 = "select * from COM_BUDGET_DETAILS " +
						"where ACCOUNTING_UNIT_ID="+cboAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cboOffice_code+
						"and FINANCIAL_YEAR='"+financialYear1+"' and " +
								"( FROM_ACC_HD_CODE="+txtaccountheadcode1+" or TO_ACC_HD_CODE="+txtaccountheadcode1+")";

				System.out.println(cboAcc_UnitCode);
				System.out.println(cboOffice_code);
				System.out.println(financialYear1);
				 System.out.println(su1);

				ps1 = connection.prepareStatement(su1);
				//ps1.setInt(1, cboAcc_UnitCode);
				//ps1.setInt(2, cboOffice_code);
				//ps1.setString(3, financialYear1);
				//ps1.setString(4, txtaccountheadcode1);
				results = ps1.executeQuery();

				if (results.next()) {

				//	System.out.println("enter");

					try {

						//String su = "select * from COM_BUDGET_DETAILS where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?";
						ps = connection.prepareStatement(su1);
						//ps.setInt(1, cboAcc_UnitCode);
						//ps.setInt(2, cboOffice_code);
						//ps.setString(3, financialYear1);
						// ps.setString(4, txtaccountheadcode1);

						rs = ps.executeQuery();
						xml = xml + "<flagg>success</flagg>";
						while (rs.next()) {
							//System.out.println("while");
							int currentYearBudgetAlloted = rs
									.getInt("CURRENT_YEAR_BUDGET_ALLOTTED");
							int budgetSoFarSpent = rs
									.getInt("BUDGET_SOFAR_SPENT");
							//int balanceAmount = (currentYearBudgetAlloted - budgetSoFarSpent);

							xml = xml + "<BudgetProvided>"
									+ currentYearBudgetAlloted
									+ "</BudgetProvided>";

							xml = xml + "<BudgetSoFarSpent>" + budgetSoFarSpent
									+ "</BudgetSoFarSpent>";

							//xml = xml + "<balanceAmount>" + balanceAmount+ "</balanceAmount>";
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
		} else if (strCommand.equalsIgnoreCase("deleted")) {

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
							.prepareStatement("update FAS_BILL_REGISTERNEW set STATUS='C' where BILL_NO=? and ACCOUNTING_UNIT_ID=? and ACCOUNTING_UNIT_OFFICE_ID=? and BILL_MAJOR_TYPE=?");
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

		}else if (strCommand.equalsIgnoreCase("IVno")) {

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

		}  
		
		else if (strCommand.equalsIgnoreCase("loadPayeeType")) {

			xml = "<response><command>loadPayeeType</command>";

		
			try {
				ps = connection
						.prepareStatement("select payee_type_code,PAYEE_TYPE_DESC  from FAS_PAYEE_TYPES_MST  where STATUS='L' and PAYEE_TYPE_CODE=7 order by payee_type_code");
				// changed on 07-12-2017 for all payee_types are loaded
				
//				ps = connection
//						.prepareStatement("select payee_type_code,PAYEE_TYPE_DESC  from FAS_PAYEE_TYPES_MST  where STATUS='L'  order by payee_type_code");
				
				

				rs2 = ps.executeQuery();
				if (rs2.next()) {
					try {
						ps1 = connection
								.prepareStatement("select payee_type_code,PAYEE_TYPE_DESC  from FAS_PAYEE_TYPES_MST  where STATUS='L' and PAYEE_TYPE_CODE=7 order by payee_type_code");
						
//						ps1 = connection
//								.prepareStatement("select payee_type_code,PAYEE_TYPE_DESC  from FAS_PAYEE_TYPES_MST  where STATUS='L' order by payee_type_code");
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

		} else if (strCommand.equalsIgnoreCase("update")) {

			xml = "<response><command>update</command>";

			String cboAcc_UnitCode1 = request.getParameter("cmbAcc_UnitCode");
			int cboAcc_UnitCode = Integer.parseInt(cboAcc_UnitCode1);

			String cboOffice_code1 = request.getParameter("cmbOffice_code");
			int cmbOffice_code = Integer.parseInt(cboOffice_code1);

			/*String cboCashBook_Year1 = request.getParameter("year");
			int cboCashBook_Year = Integer.parseInt(cboCashBook_Year1);

			String cboCashBook_Month1 = request.getParameter("month");
			int cboCashBook_Month = Integer.parseInt(cboCashBook_Month1);*/

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

			
			int cboCashBook_Year = Integer.parseInt(sd[2]);

			
			int cboCashBook_Month = Integer.parseInt(sd[1]);
			
			String txtManualProceedingNo1 = request
					.getParameter("txtManualProceedingNo");
			//int txtManualProceedingNo = Integer.parseInt(txtManualProceedingNo1);

			java.sql.Date ManualProceedingDate = null;
			java.util.GregorianCalendar c22;
			String[] sdd = request.getParameter("txtManualProceedingDate")
					.split("/");
			c22 = new java.util.GregorianCalendar(Integer.parseInt(sdd[2]),
					Integer.parseInt(sdd[1]) - 1, Integer.parseInt(sdd[0]));
			java.util.Date dd = c22.getTime();
			ManualProceedingDate = new Date(dd.getTime());

			java.sql.Date InvoiceReceivedDate = null;
			java.util.GregorianCalendar c5;
			String indat=request.getParameter("txtInvoiceReceivedDate");
			if((indat.equalsIgnoreCase(""))||(indat.equalsIgnoreCase(null))){
				
			}else{
			String[] sd5 = request.getParameter("txtInvoiceReceivedDate")
					.split("/");
			c5 = new java.util.GregorianCalendar(Integer.parseInt(sd5[2]),
					Integer.parseInt(sd5[1]) - 1, Integer.parseInt(sd5[0]));
			java.util.Date d5 = c5.getTime();
			InvoiceReceivedDate = new Date(d5.getTime());
			}
			String txtNoofInvoices1 = request.getParameter("txtNoofInvoices");
			int txtNoofInvoices =0;
			if((txtNoofInvoices1.equalsIgnoreCase(""))||(txtNoofInvoices1.equalsIgnoreCase(null))){
				
			}else{
			txtNoofInvoices = Integer.parseInt(txtNoofInvoices1);
			}
			String rdoMTC_70_Register = request
					.getParameter("rdoMTC_70_Register");
			
			String txtTotalSanctionAmount1 = request
			.getParameter("txtTotalSanctionAmount");
	float txtTotalSanctionAmount = Float.parseFloat(txtTotalSanctionAmount1);

			String txtTotalBillAmount1 = request
					.getParameter("txtTotalBillAmount");
			float txtTotalBillAmount = Float.parseFloat(txtTotalBillAmount1);

			String txtDeductedAmount1 = request
					.getParameter("txtDeductedAmount");
			float txtDeductedAmount = Float.parseFloat(txtDeductedAmount1);

			String txtAcc_HeadCode1 = request.getParameter("txtAcc_HeadCode");
			int txtAcc_HeadCode = Integer.parseInt(txtAcc_HeadCode1);

			String txtPayeeType = request.getParameter("txtPayeeType");

			String txtPayeeCode1 = request.getParameter("txtPayeeCode");
			int txtPayeeCode = Integer.parseInt(txtPayeeCode1);

			String txtEmpID_mas1 = request.getParameter("txtEmpID_mas");
			int txtEmpID_mas = Integer.parseInt(txtEmpID_mas1);

			//String cboOffice1 = request.getParameter("cboOffice");
			//int cboOffice = Integer.parseInt(cboOffice1);

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
				ps1 = connection
						.prepareStatement("update FAS_BILL_REGISTERNEW set BILL_MINOR_TYPE_CODE=?,BILL_SUB_TYPE_CODE=?,BILL_DATE=?,MANUAL_PROCEEDING_NO=?,MANUAL_PROCEEDING_DATE=?,INVOICE_RECEIVED_DATE=?,NO_OFINVOICES=?,MTC70ENTRY=?,TOTAL_BILL_AMOUNT=?,ACCOUNT_HEAD_CODE=?,PAYEE_TYPE_CODE=?,PAYEE_CODE=?,BILL_PROCESSING_DONE_BY=?,REF_NO=?,REF_DATE=?,REMARKS=?,STATUS=?,UPDATED_BY_USERID=?,UPDATED_DATE=?,BUDGET_PROVISION=?,BUDGET_SO_FAR_SPENT=?,DEDUCTED_AMOUNT=?,TOTAL_SANCTIONED_AMOUNT=?,PROCEEDING_RECD_DATE=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_UNIT_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and BILL_MAJOR_TYPE=? and BILL_NO=?");

				ps1.setInt(1, cboBillMinorType);
				ps1.setInt(2, cboBillSubType);
				ps1.setDate(3, BillDate);
				//ps1.setInt(4, txtManualProceedingNo);
				ps1.setString(4, txtManualProceedingNo1);
				ps1.setDate(5, ManualProceedingDate);
				ps1.setDate(6, InvoiceReceivedDate);
				ps1.setInt(7, txtNoofInvoices);
				ps1.setString(8, rdoMTC_70_Register);
				ps1.setFloat(9, txtTotalBillAmount);
				ps1.setInt(10, txtAcc_HeadCode);
				ps1.setString(11, txtPayeeType);
				ps1.setInt(12, txtPayeeCode);
				ps1.setInt(13, txtEmpID_mas);
				ps1.setInt(14, txtRefNo);
				ps1.setDate(15, RefDate);
				ps1.setString(16, mtxtRemarks);
				ps1.setString(17, "L");
				ps1.setString(18, userid);
				ps1.setTimestamp(19, ts);
				ps1.setFloat(20, txtBudgetProvision);
				ps1.setFloat(21, txtBudgetSpent);
				ps1.setFloat(22, txtDeductedAmount);
				ps1.setFloat(23, txtTotalSanctionAmount);
				ps1.setDate(24, ManualProceedingDate);
				ps1.setInt(25, cboAcc_UnitCode);
				ps1.setInt(26, cmbOffice_code);
				ps1.setInt(27, cboCashBook_Year);
				ps1.setInt(28, cboCashBook_Month);
				ps1.setInt(29, cboBillMajorType);
				ps1.setInt(30, txtBillNo);
				ps1.executeUpdate();
				xml = xml + "<flag>success</flag>";
			} catch (Exception e) {
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
		}

		xml = xml + "</response>";
		out.write(xml);
		System.out.println(xml);
	}
}
