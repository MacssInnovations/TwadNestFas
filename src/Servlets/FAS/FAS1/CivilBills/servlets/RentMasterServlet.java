package Servlets.FAS.FAS1.CivilBills.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Servlets.Security.classes.UserProfile.*;

/**
 * Servlet implementation class RentMasterServlet
 */
public class RentMasterServlet extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RentMasterServlet() {
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
		System.out.println("enter servlet do get method");
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();

		System.out.println("chk 1");
		Connection connection = null;
		Statement statement = null;
		ResultSet results = null;
		ResultSet results2;
		ResultSet rs = null;
		ResultSet rs2 = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;

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
		PrintWriter pw=response.getWriter();
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
			System.out.println(strCommand);
		} catch (Exception e) {
			e.printStackTrace();
		}

		long l = System.currentTimeMillis();
		Timestamp ts = new Timestamp(l);
		 if (strCommand.equalsIgnoreCase("check")) {

			try {
				String txtOffice_Id_1 = request.getParameter("txtOffice_Id") != null ? request
						.getParameter("txtOffice_Id").trim() : "";

				String sql ="select OFFICE_NAME from COM_MST_OFFICES where  OFFICE_ID='"+txtOffice_Id_1 + "'";
				// System.out.println(sql);
				 rs = null;
				 statement = connection.createStatement();
				rs = statement.executeQuery(sql);
				if (rs.next()) {
					xml = "<response> <status>success</status> <command>existing</command>"+
						 "<officename>"+rs.getString("OFFICE_NAME")+"</officename>";
				} else {
					xml = "<response><status>success</status><command>Notexisting</command>";
				}
			} catch (Exception e) {
				xml = "<response><flag>failure</flag>";
				e.printStackTrace();
			}
		}
		 xml=xml+"</response>";
         System.out.println("xml is : " + xml);
         pw.write(xml);
         pw.flush();
         pw.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// TODO Auto-generated method stub

		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();

		System.out.println("chk 1");
		Connection connection = null;
		Statement statement = null;
		ResultSet results = null;
		ResultSet results2=null;
		ResultSet results1=null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;

		String cboAccountingUnit1 = null;
		String cboAccountingForOffice1 = null;
		String txtOwnersCode1 = null;
		int cmbAcc_UnitCode;
		int cmbOffice_code;
		int txtOwnersCode;
		String txtOwnersName = null;
		String txtRentAgreementPeriodFrom = null;
		String txtRentAgreementPeriodTo = null;
		String txtAdvancePaid1 = null;
		int txtAdvancePaid = 0;
		String txtAdvancePaidDate = null;
		String txtRentValueasonDate = null;
		String txtRentValue1 = null;
		int txtRentValue = 0;
		String cboRentalPaymentOption = null;
		String txtTDSDeductionifany1 = null;
		int txtTDSDeductionifany = 0;
		String mtxtRemarks = null;
		//addeed on Dec 2012
		int txtAnnualAmounttoIT = 0;
		int txtITPercentage = 0;
		String radioITExempted = null;
		int officeId = 0;
		String txtemp_office = null;
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
			System.out.println(strCommand);
		} catch (Exception e) {
			e.printStackTrace();
		}

		long l = System.currentTimeMillis();
		Timestamp ts = new Timestamp(l);

		if (strCommand.equalsIgnoreCase("Add")) {

			xml = "<response><command>add</command>";
			cboAccountingUnit1 = request.getParameter("cmbAcc_UnitCode");
			cmbAcc_UnitCode = Integer.parseInt(cboAccountingUnit1);
			cboAccountingForOffice1 = request
					.getParameter("cmbOffice_code");
			cmbOffice_code = Integer.parseInt(cboAccountingForOffice1);
			txtOwnersCode1 = request.getParameter("txtOwnersCode");
			txtOwnersCode = Integer.parseInt(txtOwnersCode1);
			txtOwnersName = request.getParameter("txtOwnersName");
			txtRentAgreementPeriodFrom = request
					.getParameter("txtRentAgreementPeriodFrom");
			txtRentAgreementPeriodTo = request
					.getParameter("txtRentAgreementPeriodTo");
			txtAdvancePaidDate = request.getParameter("txtAdvancePaidDate");
			txtAdvancePaid1 = request.getParameter("txtAdvancePaid");
			txtAdvancePaid = Integer.parseInt(txtAdvancePaid1);
			txtRentValueasonDate = request.getParameter("txtRentValueasonDate");
			 
			txtAnnualAmounttoIT = Integer.parseInt(request.getParameter("txtAnnualAmounttoIT"));
			txtITPercentage = Integer.parseInt(request.getParameter("txtITPercentage"));;
			radioITExempted = request.getParameter("radioITExempted");
			officeId = Integer.parseInt(request.getParameter("officeId"));
			txtemp_office = request.getParameter("txtemp_office");
			

			java.sql.Date dateFrom = null;
			java.sql.Date dateTo = null;
			java.sql.Date PaidDate = null;
			java.sql.Date RentValueasonDate = null;

			java.util.GregorianCalendar c;
            String[] sd=request.getParameter("txtRentAgreementPeriodFrom").split("/");
            c=new java.util.GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
            java.util.Date d=c.getTime();
            dateFrom=new Date(d.getTime());
            
            String[] sd1=request.getParameter("txtRentAgreementPeriodTo").split("/");
            c=new java.util.GregorianCalendar(Integer.parseInt(sd1[2]),Integer.parseInt(sd1[1])-1,Integer.parseInt(sd1[0]));
            java.util.Date d1=c.getTime();
            dateTo=new Date(d1.getTime());
            
            String[] sd2=request.getParameter("txtAdvancePaidDate").split("/");
            c=new java.util.GregorianCalendar(Integer.parseInt(sd2[2]),Integer.parseInt(sd2[1])-1,Integer.parseInt(sd2[0]));
            java.util.Date d2=c.getTime();
            PaidDate=new Date(d2.getTime());
            
            String[] sd3=request.getParameter("txtRentValueasonDate").split("/");
            c=new java.util.GregorianCalendar(Integer.parseInt(sd3[2]),Integer.parseInt(sd3[1])-1,Integer.parseInt(sd3[0]));
            java.util.Date d3=c.getTime();
            RentValueasonDate=new Date(d3.getTime());

			txtRentValue1 = request.getParameter("txtRentValue");
			txtRentValue = Integer.parseInt(txtRentValue1);
			int cboPaymentMonth11 = 0;
			int cboPaymentMonth12 = 0;
			int cboPaymentMonth13 = 0;
			int cboPaymentMonth14 = 0;
			
			cboRentalPaymentOption = request
					.getParameter("cboRentalPaymentOption");
			if(cboRentalPaymentOption.equals("Q"))
			{
				cboPaymentMonth11 = Integer.parseInt(request.getParameter("cboPaymentMonth11"));
				cboPaymentMonth12 = Integer.parseInt(request.getParameter("cboPaymentMonth12"));
				cboPaymentMonth13 = Integer.parseInt(request.getParameter("cboPaymentMonth13"));
				cboPaymentMonth14 = Integer.parseInt(request.getParameter("cboPaymentMonth14"));
			}
			else if(cboRentalPaymentOption.equals("H"))
			{
				cboPaymentMonth11 = Integer.parseInt(request.getParameter("cboPaymentMonth11"));
				cboPaymentMonth12 = Integer.parseInt(request.getParameter("cboPaymentMonth12"));
			}
			else if(cboRentalPaymentOption.equals("A"))
			{
				cboPaymentMonth11 = Integer.parseInt(request.getParameter("cboPaymentMonth11"));
			}
			txtTDSDeductionifany1 = request
					.getParameter("txtTDSDeductionifany");
			txtTDSDeductionifany = Integer.parseInt(txtTDSDeductionifany1);
			mtxtRemarks = request.getParameter("mtxtRemarks");
			try {
				ps = connection
						.prepareStatement("insert into FAS_RENT_MASTER(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,OWNER_CODE,OWNER_NAME,AGREEMENT_FROM_DATE,AGREEMENT_TO_DATE,ADVANCE_PAID,ADVANCE_PAID_DATE,RENT_AS_ON_DATE,RENT_VALUE,RENTAL_OPTION,TDS_DEDUCTED,REMARKS,UPDATED_BY_USERID,UPDATED_DATE,PAYMENT_MONTH1,PAYMENT_MONTH2,PAYMENT_MONTH3,PAYMENT_MONTH4,ANNUALAMOUNT_TO_IT,IT_PERCENTAGE,WHETHER_IT_EXEMPTED,OFFICE_ID,OFFICE_NAME) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				ps.setInt(1, cmbAcc_UnitCode);
				ps.setInt(2, cmbOffice_code);
				ps.setInt(3, txtOwnersCode);
				ps.setString(4, txtOwnersName);
				ps.setDate(5, dateFrom);
				ps.setDate(6, dateTo);
				ps.setInt(7, txtAdvancePaid);
				ps.setDate(8, PaidDate);
				ps.setDate(9, RentValueasonDate);
				ps.setInt(10, txtRentValue);
				ps.setString(11, cboRentalPaymentOption);
				ps.setInt(12, txtTDSDeductionifany);
				ps.setString(13, mtxtRemarks);
				ps.setString(14, userid);
				ps.setTimestamp(15, ts);
				ps.setInt(16, cboPaymentMonth11);
				ps.setInt(17, cboPaymentMonth12);
				ps.setInt(18, cboPaymentMonth13);
				ps.setInt(19, cboPaymentMonth14);
				
				ps.setInt(20, txtAnnualAmounttoIT);
				ps.setInt(21, txtITPercentage);
				ps.setString(22, radioITExempted);
				ps.setInt(23, officeId);
				ps.setString(24, txtemp_office);


				ps.executeUpdate();
				xml = xml + "<flag>success</flag>";
				xml = xml + "<AccountingUnit>" + cmbAcc_UnitCode
						+ "</AccountingUnit>";
				xml = xml + "<AccountingForOffice>" + cmbOffice_code
						+ "</AccountingForOffice>";
				xml = xml + "<OwnersCode>" + txtOwnersCode + "</OwnersCode>";
				xml = xml + "<OwnersName>" + txtOwnersName + "</OwnersName>";
				xml = xml + "<RentAgreementPeriodFrom>"
						+ txtRentAgreementPeriodFrom
						+ "</RentAgreementPeriodFrom>";
				xml = xml + "<RentAgreementPeriodTo>"
						+ txtRentAgreementPeriodTo + "</RentAgreementPeriodTo>";
				xml = xml + "<AdvancePaid>" + txtAdvancePaid + "</AdvancePaid>";
				xml = xml + "<AdvancePaidDate>" + txtAdvancePaidDate
						+ "</AdvancePaidDate>";
				xml = xml + "<RentValueasonDate>" + txtRentValueasonDate
						+ "</RentValueasonDate>";
				xml = xml + "<RentValue>" + txtRentValue + "</RentValue>";
				xml = xml + "<RentalPaymentOption>" + cboRentalPaymentOption
						+ "</RentalPaymentOption>";								
				xml = xml + "<cboPaymentMonth11>" + cboPaymentMonth11
				+ "</cboPaymentMonth11>";				
				xml = xml + "<cboPaymentMonth12>" + cboPaymentMonth12
				+ "</cboPaymentMonth12>";				
				xml = xml + "<cboPaymentMonth13>" + cboPaymentMonth13
				+ "</cboPaymentMonth13>";				
				xml = xml + "<cboPaymentMonth14>" + cboPaymentMonth14
				+ "</cboPaymentMonth14>";							
				xml = xml + "<TDSDeductionifany>" + txtTDSDeductionifany
						+ "</TDSDeductionifany>";
				//added Dec 2012				
				xml = xml + "<AnnualAmount_toIT>" + txtAnnualAmounttoIT
				+ "</AnnualAmount_toIT>";
				xml = xml + "<ITPercentage>" + txtITPercentage
				+ "</ITPercentage>";
				xml = xml + "<ITExemptionIfAny>" + radioITExempted
				+ "</ITExemptionIfAny>";
				xml = xml + "<officeid>" + officeId
				+ "</officeid>";
				xml = xml + "<officename>" + txtemp_office
				+ "</officename>";
				
				if(mtxtRemarks.equals(""))
				{
					xml = xml + "<Remarks>nil</Remarks>";
				}
				else
				{
				xml = xml + "<Remarks>" + mtxtRemarks + "</Remarks>";
				}

			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}

			int i = 1, i1 = 0;
			try {
				ps1 = connection
						.prepareStatement("Select max(OWNER_CODE) from FAS_RENT_MASTER");
				results2 = ps1.executeQuery();
				xml = xml + "<flag1>success1</flag1>";

				if (results2.next()) {
					i1 = results2.getInt(1);
					System.out.println("count:-----------" + i1);
					i = i + i1;

				} else {
					i = i;
				}

				System.out.println("iiiiiiiii--------" + i);
				xml = xml + "<ownerCode>" + i + "</ownerCode>";

			} catch (Exception e) {
				xml = xml + "<flag1>failure1</flag1>";
				e.printStackTrace();

			}

		}

		if (strCommand.equalsIgnoreCase("gett1")) {

			System.out.println("command test:-"
					+ request.getParameter("command"));
			xml = xml + "<response><command>gett1</command>";
			int i = 1, i1 = 0;

			try {
				ps1 = connection
						.prepareStatement("Select max(OWNER_CODE) from FAS_RENT_MASTER");
				results2 = ps1.executeQuery();
				xml = xml + "<flag1>success1</flag1>";

				if (results2.next()) {
					i1 = results2.getInt(1);
					i = i + i1;

				} else {
					i = i;
				}
				xml = xml + "<ownerCode>" + i + "</ownerCode>";

			} catch (Exception e) {
				xml = xml + "<flag1>failure1</flag1>";
				e.printStackTrace();
			}
		}
		if(strCommand.equalsIgnoreCase("getname")){
			
			xml = xml + "<response><command>getname</command>";
			
			cmbAcc_UnitCode = Integer.parseInt(request
					.getParameter("cmbAcc_UnitCode"));
			System.out.println("cmbAcc_UnitCode===>"+cmbAcc_UnitCode);
			
			cmbOffice_code = Integer.parseInt(request
					.getParameter("cmbOffice_code"));
			
			System.out.println("cmbOffice_code===>"+cmbOffice_code);
			

			try {
				ps1 = connection
						.prepareStatement("select TYPE_ID,TYPE_NAME from FAS_SL_TYPES_USER_DEFINED where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=?");
				ps1.setInt(1, cmbAcc_UnitCode);
				ps1.setInt(2, cmbOffice_code);
				
				results2 = ps1.executeQuery();
				xml = xml + "<flag1>success1</flag1>";

				while (results2.next()) {
					
					xml = xml + "<ownerCode>" + results2.getString("TYPE_ID") + "</ownerCode>";
					xml = xml + "<ownerName>" + results2.getString("TYPE_NAME")+ "</ownerName>";
				} 
				

			} catch (Exception e) {
				xml = xml + "<flag1>failure1</flag1>";
				e.printStackTrace();

			}
		}
		
		
		if (strCommand.equalsIgnoreCase("gett")) {

//			xml = xml + "<response><command>gett</command>";
//			int i = 1, i1 = 0;
//
//			try {
//				ps1 = connection
//						.prepareStatement("Select max(OWNER_CODE) from FAS_RENT_MASTER");
//				results2 = ps1.executeQuery();
//				xml = xml + "<flag1>success1</flag1>";
//
//				if (results2.next()) {
//					i1 = results2.getInt(1);
//					i = i + i1;
//
//				} else {
//					i = i;
//				}
//				xml = xml + "<ownerCode>" + i + "</ownerCode>";
//
//			} catch (Exception e) {
//				xml = xml + "<flag1>failure1</flag1>";
//				e.printStackTrace();
//
//			}
//			String RentAgreementPeriodFrom=null;
//			String RentAgreementPeriodTo=null;
//			String AdvancePaidDate=null;
//			String RentValueasonDate=null;
//			try {
//				ps = connection
//						.prepareStatement("select * from FAS_RENT_MASTER order by OWNER_CODE");
//				results = ps.executeQuery();
//				xml = xml + "<flag>success</flag>";
//				while (results.next()) {
//
//					Date RentAgreementPeriodFrom1=results.getDate("AGREEMENT_FROM_DATE");
//					Date RentAgreementPeriodTo1=results.getDate("AGREEMENT_TO_DATE");
//					Date AdvancePaidDate1=results.getDate("ADVANCE_PAID_DATE");
//					Date RentValueasonDate1=results.getDate("RENT_AS_ON_DATE");
//					
//					String Stringdate = RentAgreementPeriodFrom1.toString();
//					String Stringdate1 = RentAgreementPeriodTo1.toString();
//					String Stringdate2 = AdvancePaidDate1.toString();
//					String Stringdate3 = RentValueasonDate1.toString();
//					
//					String[] ddd = Stringdate.split("-");
//					String[] ddd1 = Stringdate1.split("-");
//					String[] ddd2 = Stringdate2.split("-");
//					String[] ddd3 = Stringdate3.split("-");
//										
//					int day =Integer.parseInt(ddd[2]);
//					int month =Integer.parseInt(ddd[1]);
//					int year = Integer.parseInt(ddd[0]);
//					
//					int day1 =Integer.parseInt(ddd1[2]);
//					int month1 =Integer.parseInt(ddd1[1]);
//					int year1 = Integer.parseInt(ddd1[0]);
//					
//					int day2 =Integer.parseInt(ddd2[2]);
//					int month2 =Integer.parseInt(ddd2[1]);
//					int year2 = Integer.parseInt(ddd2[0]);
//					
//					int day3 =Integer.parseInt(ddd3[2]);
//					int month3 =Integer.parseInt(ddd3[1]);
//					int year3 = Integer.parseInt(ddd3[0]);
//					
//					if(month>=10)
//			        {
//						RentAgreementPeriodFrom=(day+"/"+month+"/"+year);
//			        }
//			        else
//			        {
//			        	RentAgreementPeriodFrom=(day+"/0"+month+"/"+year);	
//			        }
//					
//					if(month1>=10)
//			        {
//						RentAgreementPeriodTo=(day1+"/"+month1+"/"+year1);
//			        }
//			        else
//			        {
//			        	RentAgreementPeriodTo=(day1+"/0"+month1+"/"+year1);	
//			        }
//					if(month2>=10)
//			        {
//						AdvancePaidDate=(day2+"/"+month2+"/"+year2);
//			        }
//			        else
//			        {
//			        	AdvancePaidDate=(day2+"/0"+month2+"/"+year2);	
//			        }
//					
//					if(month3>=10)
//			        {
//						RentValueasonDate=(day3+"/"+month3+"/"+year3);
//			        }
//			        else
//			        {
//			        	RentValueasonDate=(day3+"/0"+month3+"/"+year3);	
//			        }
//					xml = xml + "<AccountingUnit>"
//							+ results.getInt("ACCOUNTING_UNIT_ID")
//							+ "</AccountingUnit>";
//					xml = xml + "<AccountingForOffice>"
//							+ results.getInt("ACCOUNTING_FOR_OFFICE_ID")
//							+ "</AccountingForOffice>";
//					xml = xml + "<OwnersCode>" + results.getInt("OWNER_CODE")
//							+ "</OwnersCode>";
//					xml = xml + "<OwnersName>"
//							+ results.getString("OWNER_NAME") + "</OwnersName>";
//					xml = xml + "<RentAgreementPeriodFrom>"
//							+ RentAgreementPeriodFrom
//							+ "</RentAgreementPeriodFrom>";					
//					xml = xml + "<RentAgreementPeriodTo>"
//							+ RentAgreementPeriodTo
//							+ "</RentAgreementPeriodTo>";
//					xml = xml + "<AdvancePaid>"
//							+ results.getInt("ADVANCE_PAID") + "</AdvancePaid>";
//					xml = xml + "<AdvancePaidDate>"
//							+ AdvancePaidDate
//							+ "</AdvancePaidDate>";
//					xml = xml + "<RentValueasonDate>"
//							+ RentValueasonDate
//							+ "</RentValueasonDate>";
//					xml = xml + "<RentValue>" + results.getInt("RENT_VALUE")
//							+ "</RentValue>";
//					xml = xml + "<RentalPaymentOption>"
//							+ results.getString("RENTAL_OPTION")
//							+ "</RentalPaymentOption>";
//					
//					xml = xml + "<cboPaymentMonth11>" + results.getInt("PAYMENT_MONTH1")
//					+ "</cboPaymentMonth11>";				
//					xml = xml + "<cboPaymentMonth12>" + results.getInt("PAYMENT_MONTH2")
//					+ "</cboPaymentMonth12>";				
//					xml = xml + "<cboPaymentMonth13>" + results.getInt("PAYMENT_MONTH3")
//					+ "</cboPaymentMonth13>";				
//					xml = xml + "<cboPaymentMonth14>" + results.getInt("PAYMENT_MONTH4")
//					+ "</cboPaymentMonth14>";
//					
//					xml = xml + "<TDSDeductionifany>"
//							+ results.getInt("TDS_DEDUCTED")
//							+ "</TDSDeductionifany>";
//					// added Dec 2012 Getting new fields added
//					xml = xml + "<AnnualAmount_toIT>" + results.getInt("ANNUALAMOUNT_TO_IT")
//					+ "</AnnualAmount_toIT>";
//					xml = xml + "<ITPercentage>" + results.getInt("IT_PERCENTAGE")
//					+ "</ITPercentage>";
//					xml = xml + "<ITExemptionIfAny>" + results.getString("WHETHER_IT_EXEMPTED")
//					+ "</ITExemptionIfAny>";
//					xml = xml + "<officeid>" + results.getInt("OFFICE_ID")
//					+ "</officeid>";
//					xml = xml + "<officename>" + results.getString("OFFICE_NAME")
//					+ "</officename>";
//					
//					if(results.getString("REMARKS").equalsIgnoreCase(null))
//					{
//						xml = xml + "<Remarks>nil</Remarks>";
//					}
//					else
//					{
//						xml = xml + "<Remarks>" + results.getString("REMARKS")
//							+ "</Remarks>";
//					}
//				}
//
//			} catch (Exception e) {
//				xml = xml + "<flag>failure</flag>";
//				e.printStackTrace();
//			}
//
//		Servlets.Security.classes.UserProfile empProfile = (Servlets.Security.classes.UserProfile) session
//					.getAttribute("UserProfile");
//
//			int empid = empProfile.getEmployeeId();
//
//			int oid = 0;
//			String oname = "", FAS_SU = "";
//			try {
//
//				ps = connection
//						.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?");
//				ps.setInt(1, empid);
//				results = ps.executeQuery();
//				if (results.next()) {
//					oid = results.getInt("OFFICE_ID");
//				}
//				results.close();
//				ps.close();
//			} catch (Exception e) {
//				System.out.println(e);
//			}
//		
//			try {
//				ps1 = connection.prepareStatement("Select OFFICE_ADDRESS1 from COM_MST_OFFICES where OFFICE_ID=?");
//				ps1.setInt(1, oid);
//		results2 = ps1.executeQuery();
//		xml = xml + "<flag1>success5</flag1>";
//
//		while(results2.next()) {
//			String address = results2.getString("OFFICE_ADDRESS1");
//			xml = xml + "<address>" + address + "</address>";	
//		}				
//	} catch (Exception e) {
//		xml = xml + "<flag1>failure1</flag1>";
//		e.printStackTrace();
//
//	}			
			
			// code changed on 05-12-2017
			
			xml = xml + "<response><command>gett</command>";
			int i = 1, i1 = 0;
			
			
			
			
//			cmbAcc_UnitCode = Integer.parseInt(request
//					.getParameter("cmbAcc_UnitCode"));
//			System.out.println("cmbAcc_UnitCode===>"+cmbAcc_UnitCode);
//			
//			cmbOffice_code = Integer.parseInt(request
//					.getParameter("cmbOffice_code"));
//			
//			System.out.println("cmbOffice_code===>"+cmbOffice_code);
//			
//
//			try {
//				System.out.println("select TYPE_ID,TYPE_NAME from FAS_SL_TYPES_USER_DEFINED where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code);
//				ps1 = connection.prepareStatement("select TYPE_ID,TYPE_NAME from FAS_SL_TYPES_USER_DEFINED where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code);
//				
//			    results2 = ps1.executeQuery();
//				
//
//				while(results2.next()) {
//					
//					xml = xml + "<ownerCode>" + results2.getInt("TYPE_ID") + "</ownerCode>";
//					xml = xml + "<ownerName>" + results2.getString("TYPE_NAME")+ "</ownerName>";
//					xml = xml + "<flag1>success1</flag1>";
//				} 
//				
//				
//			} catch (Exception e) {
//				xml = xml + "<flag1>failure1</flag1>";
//				e.printStackTrace();
//
//			}
			String RentAgreementPeriodFrom=null;
			String RentAgreementPeriodTo=null;
			String AdvancePaidDate=null;
			String RentValueasonDate=null;
			try {
				ps = connection
						.prepareStatement("select * from FAS_RENT_MASTER order by OWNER_CODE");
				results = ps.executeQuery();
				xml = xml + "<flag>success</flag>";
				while (results.next()) {

					Date RentAgreementPeriodFrom1=results.getDate("AGREEMENT_FROM_DATE");
					Date RentAgreementPeriodTo1=results.getDate("AGREEMENT_TO_DATE");
					Date AdvancePaidDate1=results.getDate("ADVANCE_PAID_DATE");
					Date RentValueasonDate1=results.getDate("RENT_AS_ON_DATE");
					
					String Stringdate = RentAgreementPeriodFrom1.toString();
					String Stringdate1 = RentAgreementPeriodTo1.toString();
					String Stringdate2 = AdvancePaidDate1.toString();
					String Stringdate3 = RentValueasonDate1.toString();
					
					String[] ddd = Stringdate.split("-");
					String[] ddd1 = Stringdate1.split("-");
					String[] ddd2 = Stringdate2.split("-");
					String[] ddd3 = Stringdate3.split("-");
										
					int day =Integer.parseInt(ddd[2]);
					int month =Integer.parseInt(ddd[1]);
					int year = Integer.parseInt(ddd[0]);
					
					int day1 =Integer.parseInt(ddd1[2]);
					int month1 =Integer.parseInt(ddd1[1]);
					int year1 = Integer.parseInt(ddd1[0]);
					
					int day2 =Integer.parseInt(ddd2[2]);
					int month2 =Integer.parseInt(ddd2[1]);
					int year2 = Integer.parseInt(ddd2[0]);
					
					int day3 =Integer.parseInt(ddd3[2]);
					int month3 =Integer.parseInt(ddd3[1]);
					int year3 = Integer.parseInt(ddd3[0]);
					
					if(month>=10)
			        {
						RentAgreementPeriodFrom=(day+"/"+month+"/"+year);
			        }
			        else
			        {
			        	RentAgreementPeriodFrom=(day+"/0"+month+"/"+year);	
			        }
					
					if(month1>=10)
			        {
						RentAgreementPeriodTo=(day1+"/"+month1+"/"+year1);
			        }
			        else
			        {
			        	RentAgreementPeriodTo=(day1+"/0"+month1+"/"+year1);	
			        }
					if(month2>=10)
			        {
						AdvancePaidDate=(day2+"/"+month2+"/"+year2);
			        }
			        else
			        {
			        	AdvancePaidDate=(day2+"/0"+month2+"/"+year2);	
			        }
					
					if(month3>=10)
			        {
						RentValueasonDate=(day3+"/"+month3+"/"+year3);
			        }
			        else
			        {
			        	RentValueasonDate=(day3+"/0"+month3+"/"+year3);	
			        }
					xml = xml + "<AccountingUnit>"
							+ results.getInt("ACCOUNTING_UNIT_ID")
							+ "</AccountingUnit>";
					xml = xml + "<AccountingForOffice>"
							+ results.getInt("ACCOUNTING_FOR_OFFICE_ID")
							+ "</AccountingForOffice>";
					xml = xml + "<OwnersCode>" + results.getInt("OWNER_CODE")
							+ "</OwnersCode>";
					xml = xml + "<OwnersName>"
							+ results.getString("OWNER_NAME") + "</OwnersName>";
					xml = xml + "<RentAgreementPeriodFrom>"
							+ RentAgreementPeriodFrom
							+ "</RentAgreementPeriodFrom>";					
					xml = xml + "<RentAgreementPeriodTo>"
							+ RentAgreementPeriodTo
							+ "</RentAgreementPeriodTo>";
					xml = xml + "<AdvancePaid>"
							+ results.getInt("ADVANCE_PAID") + "</AdvancePaid>";
					xml = xml + "<AdvancePaidDate>"
							+ AdvancePaidDate
							+ "</AdvancePaidDate>";
					xml = xml + "<RentValueasonDate>"
							+ RentValueasonDate
							+ "</RentValueasonDate>";
					xml = xml + "<RentValue>" + results.getInt("RENT_VALUE")
							+ "</RentValue>";
					xml = xml + "<RentalPaymentOption>"
							+ results.getString("RENTAL_OPTION")
							+ "</RentalPaymentOption>";
					
					xml = xml + "<cboPaymentMonth11>" + results.getInt("PAYMENT_MONTH1")
					+ "</cboPaymentMonth11>";				
					xml = xml + "<cboPaymentMonth12>" + results.getInt("PAYMENT_MONTH2")
					+ "</cboPaymentMonth12>";				
					xml = xml + "<cboPaymentMonth13>" + results.getInt("PAYMENT_MONTH3")
					+ "</cboPaymentMonth13>";				
					xml = xml + "<cboPaymentMonth14>" + results.getInt("PAYMENT_MONTH4")
					+ "</cboPaymentMonth14>";
					
					xml = xml + "<TDSDeductionifany>"
							+ results.getInt("TDS_DEDUCTED")
							+ "</TDSDeductionifany>";
					// added Dec 2012 Getting new fields added
					xml = xml + "<AnnualAmount_toIT>" + results.getInt("ANNUALAMOUNT_TO_IT")
					+ "</AnnualAmount_toIT>";
					xml = xml + "<ITPercentage>" + results.getInt("IT_PERCENTAGE")
					+ "</ITPercentage>";
					xml = xml + "<ITExemptionIfAny>" + results.getString("WHETHER_IT_EXEMPTED")
					+ "</ITExemptionIfAny>";
					xml = xml + "<officeid>" + results.getInt("OFFICE_ID")
					+ "</officeid>";
					xml = xml + "<officename>" + results.getString("OFFICE_NAME")
					+ "</officename>";
					
					if(results.getString("REMARKS").equalsIgnoreCase(null))
					{
						xml = xml + "<Remarks>nil</Remarks>";
					}
					else
					{
						xml = xml + "<Remarks>" + results.getString("REMARKS")
							+ "</Remarks>";
					}
				}

			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}

		Servlets.Security.classes.UserProfile empProfile = (Servlets.Security.classes.UserProfile) session
					.getAttribute("UserProfile");

			int empid = empProfile.getEmployeeId();

			int oid = 0;
			String oname = "", FAS_SU = "";
			try {

				ps = connection
						.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?");
				ps.setInt(1, empid);
				results = ps.executeQuery();
				if (results.next()) {
					oid = results.getInt("OFFICE_ID");
				}
				results.close();
				ps.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		
			try {
				ps1 = connection.prepareStatement("Select OFFICE_ADDRESS1 from COM_MST_OFFICES where OFFICE_ID=?");
				ps1.setInt(1, oid);
		results2 = ps1.executeQuery();
		xml = xml + "<flag1>success5</flag1>";

		while(results2.next()) {
			String address = results2.getString("OFFICE_ADDRESS1");
			xml = xml + "<address>" + address + "</address>";	
		}				
	} catch (Exception e) {
		xml = xml + "<flag1>failure1</flag1>";
		e.printStackTrace();

	}		
			
			
			
			
		} else if (strCommand.equalsIgnoreCase("deleted")) {

			xml = "<response><command>deleted</command>";
			String OwnersCode = request.getParameter("OwnersCode");
			try {
				ps = connection
						.prepareStatement("delete from FAS_RENT_MASTER where OWNER_CODE=?");
				ps.setString(1, OwnersCode);
				ps.executeUpdate();
				xml = xml + "<flag>success</flag>";
				xml = xml + "<id>" + OwnersCode + "</id>";
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
			}

			int i = 1, i1 = 0;
			try {
				ps1 = connection
						.prepareStatement("Select max(OWNER_CODE) from FAS_RENT_MASTER");
				results2 = ps1.executeQuery();
				xml = xml + "<flag1>success1</flag1>";

				if (results2.next()) {
					i1 = results2.getInt(1);
					System.out.println("count:-----------" + i1);
					i = i + i1;

				} else {
					i = i;
				}

				System.out.println("iiiiiiiii--------" + i);
				xml = xml + "<ownerCode>" + i + "</ownerCode>";

			} catch (Exception e) {
				xml = xml + "<flag1>failure1</flag1>";
				e.printStackTrace();

			}

		} else if (strCommand.equalsIgnoreCase("update")) {

			xml = "<response><command>update</command>";
			cboAccountingUnit1 = request.getParameter("cmbAcc_UnitCode");
			cmbAcc_UnitCode = Integer.parseInt(cboAccountingUnit1);
			cboAccountingForOffice1 = request
					.getParameter("cmbOffice_code");
			cmbOffice_code = Integer.parseInt(cboAccountingForOffice1);
			txtOwnersCode1 = request.getParameter("txtOwnersCode");
			txtOwnersCode = Integer.parseInt(txtOwnersCode1);
			txtOwnersName = request.getParameter("txtOwnersName");
			txtRentAgreementPeriodFrom = request
					.getParameter("txtRentAgreementPeriodFrom");

			System.out.println(txtRentAgreementPeriodFrom);

			txtRentAgreementPeriodTo = request
					.getParameter("txtRentAgreementPeriodTo");
			txtAdvancePaidDate = request.getParameter("txtAdvancePaidDate");
			txtAdvancePaid1 = request.getParameter("txtAdvancePaid");
			txtAdvancePaid = Integer.parseInt(txtAdvancePaid1);
			txtRentValueasonDate = request.getParameter("txtRentValueasonDate");
			SimpleDateFormat formate = new SimpleDateFormat("dd/mm/yyyy");

			java.sql.Date dateFrom = null;
			java.sql.Date dateTo = null;
			java.sql.Date PaidDate = null;
			java.sql.Date RentValueasonDate = null;

			java.util.GregorianCalendar c;
            String[] sd=request.getParameter("txtRentAgreementPeriodFrom").split("/");
            c=new java.util.GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
            java.util.Date d=c.getTime();
            dateFrom=new Date(d.getTime());
            
            String[] sd1=request.getParameter("txtRentAgreementPeriodTo").split("/");
            c=new java.util.GregorianCalendar(Integer.parseInt(sd1[2]),Integer.parseInt(sd1[1])-1,Integer.parseInt(sd1[0]));
            java.util.Date d1=c.getTime();
            dateTo=new Date(d1.getTime());
            
            String[] sd2=request.getParameter("txtAdvancePaidDate").split("/");
            c=new java.util.GregorianCalendar(Integer.parseInt(sd2[2]),Integer.parseInt(sd2[1])-1,Integer.parseInt(sd2[0]));
            java.util.Date d2=c.getTime();
            PaidDate=new Date(d2.getTime());
            
            String[] sd3=request.getParameter("txtRentValueasonDate").split("/");
            c=new java.util.GregorianCalendar(Integer.parseInt(sd3[2]),Integer.parseInt(sd3[1])-1,Integer.parseInt(sd3[0]));
            java.util.Date d3=c.getTime();
            RentValueasonDate=new Date(d3.getTime());

			txtRentValue1 = request.getParameter("txtRentValue");
			txtRentValue = Integer.parseInt(txtRentValue1);
			int cboPaymentMonth11 = 0;
			int cboPaymentMonth12 = 0;
			int cboPaymentMonth13 = 0;
			int cboPaymentMonth14 = 0;
			
			cboRentalPaymentOption = request
					.getParameter("cboRentalPaymentOption");
			if(cboRentalPaymentOption.equals("Q"))
			{
				cboPaymentMonth11 = Integer.parseInt(request.getParameter("cboPaymentMonth11"));
				cboPaymentMonth12 = Integer.parseInt(request.getParameter("cboPaymentMonth12"));
				cboPaymentMonth13 = Integer.parseInt(request.getParameter("cboPaymentMonth13"));
				cboPaymentMonth14 = Integer.parseInt(request.getParameter("cboPaymentMonth14"));
			}
			else if(cboRentalPaymentOption.equals("H"))
			{
				cboPaymentMonth11 = Integer.parseInt(request.getParameter("cboPaymentMonth11"));
				cboPaymentMonth12 = Integer.parseInt(request.getParameter("cboPaymentMonth12"));
			}
			else if(cboRentalPaymentOption.equals("A"))
			{
				cboPaymentMonth11 = Integer.parseInt(request.getParameter("cboPaymentMonth11"));
			}
			txtTDSDeductionifany1 = request
					.getParameter("txtTDSDeductionifany");
			txtTDSDeductionifany = Integer.parseInt(txtTDSDeductionifany1);
			mtxtRemarks = request.getParameter("mtxtRemarks");
			
			System.out.println("cboRentalPaymentOption"+cboRentalPaymentOption);
			System.out.println("___________________________________________________________________________________");
			System.out.println(cboPaymentMonth11);
			System.out.println(cboPaymentMonth12);
			System.out.println(cboPaymentMonth13);
			System.out.println(cboPaymentMonth14);
			System.out.println("___________________________________________________________________________________");
			
			txtTDSDeductionifany1 = request
					.getParameter("txtTDSDeductionifany");
			
			txtAnnualAmounttoIT = Integer.parseInt(request.getParameter("txtAnnualAmounttoIT"));
			txtITPercentage = Integer.parseInt(request.getParameter("txtITPercentage"));;
			radioITExempted = request.getParameter("radioITExempted");
			officeId = Integer.parseInt(request.getParameter("officeId"));
			txtemp_office = request.getParameter("txtemp_office");
			
			txtTDSDeductionifany = Integer.parseInt(txtTDSDeductionifany1);
			mtxtRemarks = request.getParameter("mtxtRemarks");
			Date date = new Date(00 - 00 - 0000);
			System.out.println(date);

			try {
				ps = connection
						.prepareStatement("update FAS_RENT_MASTER set ACCOUNTING_UNIT_ID=?,ACCOUNTING_FOR_OFFICE_ID=?,OWNER_NAME=?,AGREEMENT_FROM_DATE=?,AGREEMENT_TO_DATE=?,ADVANCE_PAID=?,ADVANCE_PAID_DATE=?,RENT_AS_ON_DATE=?,RENT_VALUE=?,RENTAL_OPTION=?,TDS_DEDUCTED=?,REMARKS=?,UPDATED_BY_USERID=?,UPDATED_DATE=?,PAYMENT_MONTH1=?,PAYMENT_MONTH2=?,PAYMENT_MONTH3=?,PAYMENT_MONTH4=?,ANNUALAMOUNT_TO_IT=?,IT_PERCENTAGE=?,WHETHER_IT_EXEMPTED=?,OFFICE_ID=?,OFFICE_NAME=? where OWNER_CODE=?");
				ps.setInt(1, cmbAcc_UnitCode);
				ps.setInt(2, cmbOffice_code);
				ps.setString(3, txtOwnersName);
				ps.setDate(4, dateFrom);
				ps.setDate(5, dateTo);
				ps.setInt(6, txtAdvancePaid);
				ps.setDate(7, PaidDate);
				ps.setDate(8, RentValueasonDate);
				ps.setInt(9, txtRentValue);
				ps.setString(10, cboRentalPaymentOption);
				ps.setInt(11, txtTDSDeductionifany);
				ps.setString(12, mtxtRemarks);
				ps.setString(13, userid);
				ps.setTimestamp(14, ts);				
				ps.setInt(15, cboPaymentMonth11);
				ps.setInt(16, cboPaymentMonth12);
				ps.setInt(17, cboPaymentMonth13);
				ps.setInt(18, cboPaymentMonth14);
				
				ps.setInt(19, txtAnnualAmounttoIT);
				ps.setInt(20, txtITPercentage);
				ps.setString(21, radioITExempted);
				ps.setInt(22, officeId);
				ps.setString(23, txtemp_office);
				ps.setInt(24, txtOwnersCode);
				ps.executeUpdate();

				xml = xml + "<flag>success</flag>";
				xml = xml + "<AccountingUnit>" + cmbAcc_UnitCode
						+ "</AccountingUnit>";
				xml = xml + "<AccountingForOffice>" + cmbOffice_code
						+ "</AccountingForOffice>";
				xml = xml + "<OwnersCode>" + txtOwnersCode + "</OwnersCode>";
				xml = xml + "<OwnersName>" + txtOwnersName + "</OwnersName>";
				xml = xml + "<RentAgreementPeriodFrom>" + txtRentAgreementPeriodFrom
						+ "</RentAgreementPeriodFrom>";
				xml = xml + "<RentAgreementPeriodTo>" + txtRentAgreementPeriodTo
						+ "</RentAgreementPeriodTo>";
				xml = xml + "<AdvancePaid>" + txtAdvancePaid + "</AdvancePaid>";
				xml = xml + "<AdvancePaidDate>" + txtAdvancePaidDate
						+ "</AdvancePaidDate>";
				xml = xml + "<RentValueasonDate>" + txtRentValueasonDate
						+ "</RentValueasonDate>";
				xml = xml + "<RentValue>" + txtRentValue + "</RentValue>";
				xml = xml + "<RentalPaymentOption>" + cboRentalPaymentOption
						+ "</RentalPaymentOption>";
				xml = xml + "<cboPaymentMonth11>" + cboPaymentMonth11
				+ "</cboPaymentMonth11>";				
				xml = xml + "<cboPaymentMonth12>" + cboPaymentMonth12
				+ "</cboPaymentMonth12>";				
				xml = xml + "<cboPaymentMonth13>" + cboPaymentMonth13
				+ "</cboPaymentMonth13>";				
				xml = xml + "<cboPaymentMonth14>" + cboPaymentMonth14
				+ "</cboPaymentMonth14>";		
				xml = xml + "<TDSDeductionifany>" + txtTDSDeductionifany
						+ "</TDSDeductionifany>";
				
				//added on dec 2012
				xml = xml + "<AnnualAmount_toIT>" + txtAnnualAmounttoIT
				+ "</AnnualAmount_toIT>";
				xml = xml + "<ITPercentage>" + txtITPercentage
				+ "</ITPercentage>";
				xml = xml + "<ITExemptionIfAny>" + radioITExempted
				+ "</ITExemptionIfAny>";
				xml = xml + "<officeid>" + officeId
				+ "</officeid>";
				xml = xml + "<officename>" + txtemp_office
				+ "</officename>";
				
				if(mtxtRemarks.equals(""))
				{
					xml = xml + "<Remarks>nil</Remarks>";
				}
				else
				{
				xml = xml + "<Remarks>" + mtxtRemarks + "</Remarks>";
				}

			} catch (Exception e) {
				System.out.println("exception in update is" + e);

			}
			int i = 1, i1 = 0;
			try {
				ps1 = connection
						.prepareStatement("Select max(OWNER_CODE) from FAS_RENT_MASTER");
				results2 = ps1.executeQuery();
				xml = xml + "<flag1>success1</flag1>";

				if (results2.next()) {
					i1 = results2.getInt(1);
					System.out.println("count:-----------" + i1);
					i = i + i1;

				} else {
					i = i;
				}

				System.out.println("iiiiiiiii--------" + i);
				xml = xml + "<ownerCode>" + i + "</ownerCode>";

			} catch (Exception e) {
				xml = xml + "<flag1>failure1</flag1>";
				e.printStackTrace();

			}

		}
		xml = xml + "</response>";
		out.write(xml);
		System.out.println(xml);
	}

}
