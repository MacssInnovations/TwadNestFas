package Servlets.FAS.FAS1.NRDWP.servlets;

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
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class nrdwp_Freeze
 */
public class Nrdwp_Account_Change extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Nrdwp_Account_Change() {
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
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;

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
		//System.out.println("User Id is:" + userid);
		try {
			//System.out.println("chk 3");
			if (session == null) {
				//System.out.println(request.getContextPath() + "/index.jsp");
				response.sendRedirect(request.getContextPath() + "/index.jsp");

			}
			System.out.println(session);

		} catch (Exception e) {
			System.out.println("Redirect Error :" + e);
		}

		try {
			strCommand = request.getParameter("Command");
			//System.out.println("strCommand:-" + strCommand);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Servlets.Security.classes.UserProfile empProfile = (Servlets.Security.classes.UserProfile) session
				.getAttribute("UserProfile");
		int empid = empProfile.getEmployeeId();
		String empName = empProfile.getEmployeeName();
		long l = System.currentTimeMillis();
		Timestamp ts = new Timestamp(l);

		//Connection con=null;
		ResultSet rs=null;
			/* Get Parameters */
			int qcheck1 = 0;
			int cmbAcc_UnitCode = 0;
			int cmbOffice_code = 0;
			//String FinancialYear = null;
			int txtCB_Month=0,txtCB_Year=0;
			String cmbBankAccNo="",cmbDocType="",cmbBankAccMode="";
			try {
				cmbAcc_UnitCode = Integer.parseInt(request
						.getParameter("cmbAcc_UnitCode"));
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
				txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));
			} catch (Exception e) {
				System.out.println("Error Not Getting txtCB_Year  -->" + e);
			}

			try {
				txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
			} catch (Exception e) {
				System.out.println("Error Not Getting txtCB_Month  -->" + e);
			}
			try {
				cmbDocType = request.getParameter("cmbDocType");
			} catch (Exception e) {
				System.out.println("Error Not Getting cmbDocType  -->" + e);
			}
			try {
				cmbBankAccNo = request.getParameter("cmbBankAccNo").split("/")[0];
				cmbBankAccMode = request.getParameter("cmbBankAccNo").split("/")[1];
				
			} catch (Exception e) {
				System.out.println("Error Not Getting cmbBankAccNo  -->" + e);
			}

			System.out.println("cmbBankAccMode "+cmbBankAccMode);

			System.out.println("cmbBankAccNo "+cmbBankAccNo);
		System.out.println("strCommand "+strCommand);
			int count=0;
			if (strCommand.equalsIgnoreCase("check_TB")) {
	          
	          //  response.setContentType(CONTENT_TYPE);
	            Calendar c;
	           // String xml = "";
	            //Date txtCrea_date = null;
	           
	            //System.out.println("check_TB if condi");
	            xml = "<response><command>check_TB</command>";


	            try {
	            	 ps = connection.prepareStatement("select TB_STATUS from FAS_TRIAL_BALANCE_STATUS where ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=?  and CASHBOOK_MONTH=? ");
	                //joe changes
	            	//ps = connection.prepareStatement("select TB_STATUS from FAS_TRIAL_BALANCE_STATUS_cp where ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=?  and CASHBOOK_MONTH=? ");
	                System.out.println("select TB_STATUS from FAS_TRIAL_BALANCE_STATUS where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and CASHBOOK_YEAR="+txtCB_Year+"  and CASHBOOK_MONTH="+txtCB_Month);
	                ps.setInt(1, cmbAcc_UnitCode);
	                //ps.setInt(2,cmbOffice_code);
	                ps.setInt(2, txtCB_Year);
	                ps.setInt(3, txtCB_Month);
	                rs = ps.executeQuery();
	               // System.out.println("afeter query");
	                if (rs.next()) {
	                	 System.out.println("afeter query"+rs.getString("TB_STATUS"));
	                    if (rs.getString("TB_STATUS").equalsIgnoreCase("N"))
	                       
	                        xml = xml + "<flag>success</flag>";
	                    else
	                        xml = xml + "<flag>failure</flag>";
	                } else
	                    xml = xml + "<flag>success</flag>";

	            } catch (Exception e) {
	                System.out.println("catch..HERE.in TB " + e);
	                xml = xml + "<flag>failure</flag>";
	            }
	         
	        }else  if(strCommand.equalsIgnoreCase("loadAccountNo"))
            {
	           // System.out.println("loadAccountNo********");
	            		String sql =
	            	  	"       select *             													\n"+   						
	            	  	"		from 																	\n"+	
	            	  	"		(																		\n"+	
	            	  	"			select																\n"+
	            	  	"				bank_id,														\n"+
	            	  	"				BRANCH_ID,														\n"+
	            	  	"				bank_ac_no, 													\n"+
	            	  	"				AC_OPERATIONAL_MODE_ID,                                         \n"+
	            	  	"				trim(AC_OPERATIONAL_MODE_ID)||'-'||trim(bank_ac_no) as acc_no			    \n"+  
	            	  	"			from																\n"+
	            	  	"				fas_mst_bank_balance												\n"+
	            	  	"			where																\n"+		
	            	  	"				accounting_unit_id = ?  and AC_OPERATIONAL_MODE_ID like 'OPR-NRDWP%' and status='Y'  										\n"+
	            	  	"		)X																		\n"+			
	            	  	"		left outer join															\n"+
	            	  	"		(																		\n"+		 
	            	  	"				select bank_id as y_bank_id ,trim(BANK_SHORT_NAME) as y_bank_name from fas_bank_list	\n"+	
	            	  	"		)Y																		\n"+
	            	  	"    on 																		\n"+
	            	  	"      X.bank_id  = Y.y_bank_id													\n"+
	            	  	"    left outer join 															\n"+
	            	  	"    (																			\n"+
	            	  	"      select  BANK_ID as z_bank_id, BRANCH_ID as z_BRANCH_ID ,trim(BRANCH_NAME) as z_BRANCH_NAME from fas_mst_bank_branches	\n"+                   
	            	  	"    )Z                                    										\n"+
	            	  	"	 on  																		\n"+
	            	  	"      X.bank_id  = Z.z_bank_id  and											\n"+ 
	            	  	"      X.BRANCH_ID = Z.z_branch_id	order by bank_id,bank_ac_no,AC_OPERATIONAL_MODE_ID	\n"+
	            	  	" 																			      ";
	            		
	              System.out.println("sql:::"+sql);
			            try
			            {
				             PreparedStatement ps2=connection.prepareStatement(sql);
				              ps2.setInt(1,cmbAcc_UnitCode);
				             ResultSet rs2=ps2.executeQuery();
				           
				              
				              xml=xml+"<response><command>loadAccountNo</command>";
				              
				              /** Count How many Records are available  */
				              while (rs2.next()) 
				              {
				                 xml=xml+ "<acc_no>"+ rs2.getString("bank_ac_no") +"</acc_no>";	 
				                 xml=xml+ "<bank_id>"+ rs2.getString("bank_id") +"</bank_id>";  
				                 xml=xml+ "<branch_id>"+ rs2.getString("branch_id") +"</branch_id>";                 
				                 xml=xml+ "<opr_mode>"+ rs2.getString("AC_OPERATIONAL_MODE_ID") +"</opr_mode>";                 
				                // xml=xml+ "<acc_desc>"+ rs2.getString("acc_no") +"-"+ rs2.getString("y_bank_name") +"</acc_desc>";
				                 xml=xml+ "<acc_desc>"+ rs2.getString("y_bank_name")+"-"+rs2.getString("bank_ac_no")+"-"+rs2.getString("AC_OPERATIONAL_MODE_ID") +"</acc_desc>";
				                 xml=xml+ "<bank_name>"+ rs2.getString("y_bank_name") +"</bank_name>";      			                 
				                 xml=xml+ "<branch_name>"+ rs2.getString("z_BRANCH_NAME") +"</branch_name>";
				                 count++;
				              }
				              
				              if(count==0) {
				                  xml=xml+"<flag>NoData</flag>";
				              }
				              else{                
				                  xml=xml+"<flag>success</flag>";
				              }              
			           }
				       catch(Exception e) 
				       {
				              System.out.println("Exception in assigning..."+e);
				              xml=xml+"<flag>failure</flag>";
				       }
	         }
	        else  if(strCommand.equals("LoadData"))
	        { 
	        	int count1=0;
	    	//System.out.println("\n*************\n LoadData \n**************\n");
	        xml="<response><command>LoadData</command>";
	        try 
	        {
	        	String qry="";
	     
	        	if(cmbDocType.equalsIgnoreCase("R")){
	        		 qry="SELECT RECEIPT_NO as docNo, "+
	        			" ACCOUNTING_UNIT_ID as unitid, "+
						  "  ACCOUNTING_FOR_OFFICE_ID as offid, "+
						  "  CASHBOOK_YEAR AS yr,  "+
						  "  CASHBOOK_MONTH AS mnth,  "+
						  "  ACCOUNT_NO AS accno,  "+
						  "  'R' AS doctypp,  "+
						  " REMARKS, "+
						 "   to_char(RECEIPT_DATE,'DD/MM/YYYY') as DocDate, "+
						 " RECEIPT_TYPE as DocType, "+
						 " ACCOUNT_HEAD_CODE AS oldAcHead,' ' as oldAcHeadCr,' 'AS newAcHeadCr,  "+
						  " TOTAL_AMOUNT as Amt, "+
						 "  CR_DR_INDICATOR , "+
						 " (SELECT AC_HEAD_CODE "+
	        		 "  FROM FAS_OFFICE_BANK_AC_CURRENT f "+
	        		 "  WHERE f.ACCOUNTING_UNIT_ID=m.ACCOUNTING_UNIT_ID "+
	        		 "   AND f.MODULE_ID           ='MF004' "+
	        		 "   AND f.BANK_AC_NO          =m.account_no "+
	        		 " AND f.STATUS='Y' "+
	        		 "   )AS newAcHead "+
	        		 " FROM FAS_RECEIPT_MASTER m "+
	        		 " 	WHERE ACCOUNTING_UNIT_ID    ="+cmbAcc_UnitCode+
	        		 " 	AND ACCOUNTING_FOR_OFFICE_ID= "+cmbOffice_code+
	        		 " 	AND CASHBOOK_YEAR           = "+txtCB_Year+
	        		 " 	AND CASHBOOK_MONTH          = "+txtCB_Month+
	        		 " 	AND ACCOUNT_NO              = '"+cmbBankAccNo+"'" +
	        		 		" AND RECEIPT_STATUS='L'";
								         
	        	}else if(cmbDocType.equalsIgnoreCase("P")){
	        		
	        		qry="SELECT VOUCHER_NO as docNo, "+
	        		
	        		" ACCOUNTING_UNIT_ID as unitid, "+
					  "  ACCOUNTING_FOR_OFFICE_ID as offid, "+
					  "  CASHBOOK_YEAR AS yr,  "+
					  "  CASHBOOK_MONTH AS mnth,  "+
					  "  ACCOUNT_NO AS accno,  "+
					  "  'P' AS doctypp,  "+
					  "REMARKS , "+
				  "   to_char(PAYMENT_DATE,'DD/MM/YYYY') as DocDate, "+
				  "  PAYMENT_TYPE as DocType, "+
				  "  ACCOUNT_HEAD_CODE AS oldAcHead, ' ' as oldAcHeadCr,' 'AS newAcHeadCr,  "+
				  "  TOTAL_AMOUNT as Amt, "+
				  "  CR_DR_INDICATOR , "+
				  "  (SELECT AC_HEAD_CODE  "+
				  " FROM FAS_OFFICE_BANK_AC_CURRENT f  "+
				  "  WHERE f.ACCOUNTING_UNIT_ID=m.ACCOUNTING_UNIT_ID  "+
				  "  AND f.MODULE_ID           ='MF005'  "+
				  "  AND f.BANK_AC_NO          =m.account_no  "+
				  " AND f.STATUS='Y' "+
				  "  )AS newAcHead  "+
				  " FROM FAS_PAYMENT_MASTER m  "+
				  " WHERE ACCOUNTING_UNIT_ID    ="+cmbAcc_UnitCode+
				  " AND ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+
				  " AND CASHBOOK_YEAR           ="+txtCB_Year+
				  " AND CASHBOOK_MONTH          ="+txtCB_Month+
				  " AND ACCOUNT_NO              ='"+cmbBankAccNo+"'" +
				  		" AND PAYMENT_STATUS='L'";
					        	}
	        	else if(cmbDocType.equalsIgnoreCase("FR")){
					        		
					        		try{
					        			String Acc_Change="SELECT m.OFFICE_ACCOUNT_NO AS accno, " +
					        			"  t.OFFICE_ACCOUNT_NO accno1,m.trf_voucher_no as vou_no  " +
					        			" FROM FAS_FUND_RECEIPT_BY_OFFICE m " +
					        			" INNER JOIN FAS_FUND_TRF_FROM_HO_TRN t " +
					        			" ON m.ACCOUNTING_FOR_OFFICE_ID =t.transfer_to_office_id " +
					        			" AND m.CASHBOOK_YEAR           =t.CASHBOOK_YEAR " +
					        			" AND m.CASHBOOK_MONTH          =t.CASHBOOK_MONTH " +
					        			" AND m.trf_voucher_no          =t.voucher_no " +
					        			" AND m.ACCOUNTING_UNIT_ID      = " +cmbAcc_UnitCode+
					        			" AND m.ACCOUNTING_FOR_OFFICE_ID= " +cmbOffice_code+
					        			" AND m.CASHBOOK_YEAR           = " +txtCB_Year+
					        			" AND m.CASHBOOK_MONTH          = " +txtCB_Month+
					        			" AND m.RECEIPT_STATUS='L' "+
					        			" AND (m.OFFICE_ACCOUNT_NO      ='"+cmbBankAccNo+"' " +
					        			" OR t.office_account_no        ='"+cmbBankAccNo+"')";
					        		PreparedStatement ps_Acc=connection.prepareStatement(Acc_Change);
					        		ResultSet rs_Acc=ps_Acc.executeQuery();
					        		int kk=0;
					        			while(rs_Acc.next())
					        			{
					        				if(!rs_Acc.getString("accno").equalsIgnoreCase(rs_Acc.getString("accno1")))
					        				{
					        				String update_Acc="UPDATE FAS_FUND_RECEIPT_BY_OFFICE m " +
					        				" SET m.OFFICE_ACCOUNT_NO       ='"+rs_Acc.getString("accno1")+"' " +
					        				" WHERE m.trf_voucher_no        = " +rs_Acc.getInt("vou_no")+
					        				" AND m.ACCOUNTING_UNIT_ID      = " +cmbAcc_UnitCode+
						        			" AND m.ACCOUNTING_FOR_OFFICE_ID= " +cmbOffice_code+
						        			" AND m.CASHBOOK_YEAR           = " +txtCB_Year+
						        			" AND m.CASHBOOK_MONTH          = " +txtCB_Month+
						        			" AND m.RECEIPT_STATUS='L' ";
	System.out.println("update_Acc >> "+update_Acc);
	try{
	PreparedStatement Acc_update=connection.prepareStatement(update_Acc);
	kk=Acc_update.executeUpdate();
	if(kk==0){
		sendMessage(response,"Can't update Account No  ","ok");
	}else{
	System.out.println("update_Acc >> Exception ");
	}
	}catch (Exception e) {
		System.out.println("update_Acc >> Exception ");
		e.printStackTrace();
	}
					        				}
					        			}
					        			
					        		}catch (Exception e) {
										e.printStackTrace();
									}
					        		
					        		
					        		
					        		
					        		
					        		
int newCrhead=0;
					        		
					        		if(cmbBankAccMode.equalsIgnoreCase("OPR-NRDWP-Support")){
					        			newCrhead=820661;
					        		}else if(cmbBankAccMode.equalsIgnoreCase("OPR-NRDWP-Main")){
					        			newCrhead=822151;
					        		}
					        		//if(cmbAcc_UnitCode!=5){
					        		System.out.println("coming hereee");
					        			qry="SELECT RECEIPT_NO          AS docNo, " +
					        			"  ACCOUNTING_UNIT_ID       AS unitid, " +
					        			"  ACCOUNTING_FOR_OFFICE_ID AS offid, " +
					        			"  CASHBOOK_YEAR            AS yr, " +
					        			"  CASHBOOK_MONTH           AS mnth, " +
					        			"  OFFICE_ACCOUNT_NO        AS accno, " +
					        			"  'FR'                     AS doctypp, " +
					        			"  PARTICULARS as REMARKS, " +
					        			"  TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') AS DocDate, " +
					        			"  '-'                                AS DocType, " +
					        			"  Dr_ACCOUNT_HEAD_CODE               AS oldAcHead, " +
					        			"  Cr_ACCOUNT_HEAD_CODE               AS oldAcHeadCr," +
					        			"  TOTAL_AMOUNT                       AS Amt, " +
					        			"  'DR' CR_DR_INDICATOR , " +
					        			"  (SELECT AC_HEAD_CODE " +
					        			"  FROM FAS_OFFICE_BANK_AC_CURRENT f " +
					        			"  WHERE f.ACCOUNTING_UNIT_ID=m.ACCOUNTING_UNIT_ID " +
					        			"  AND f.MODULE_ID           ='MF009' " +
					        			"  AND f.BANK_AC_NO          =m.OFFICE_ACCOUNT_NO " +
					        			" AND f.STATUS='Y' "+
					        			" AND ROWNUM <= 1 "+
					        			"  )AS newAcHead,  '"+newCrhead+"'             AS newAcHeadCr " +
					        			"FROM FAS_FUND_RECEIPT_BY_OFFICE m " +
					        			" WHERE ACCOUNTING_UNIT_ID    ="+cmbAcc_UnitCode+
					        			" AND ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+
					        			" AND CASHBOOK_YEAR          ="+txtCB_Year+
					        			" AND CASHBOOK_MONTH         ="+txtCB_Month+
					        			" AND OFFICE_ACCOUNT_NO       ='"+cmbBankAccNo+"'" +
					        					" and RECEIPT_STATUS='L'";
					        		/*}else{
					        			qry="SELECT RECEIPT_NO          AS docNo, " +
					        			"  ACCOUNTING_UNIT_ID       AS unitid, " +
					        			"  ACCOUNTING_FOR_OFFICE_ID AS offid, " +
					        			"  CASHBOOK_YEAR            AS yr, " +
					        			"  CASHBOOK_MONTH           AS mnth, " +
					        			"  HO_ACCOUNT_NO        AS accno, " +
					        			"  'FR'                     AS doctypp, " +
					        			"  PARTICULARS as REMARKS, " +
					        			"  TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') AS DocDate, " +
					        			"  RECEIPT_TYPE                                AS DocType, " +
					        			"  Cr_ACCOUNT_HEAD_CODE               AS oldAcHead, " +
					        			"  TOTAL_AMOUNT                       AS Amt, " +
					        			"  'CR' CR_DR_INDICATOR , " +
					        			"  (SELECT AC_HEAD_CODE " +
					        			"  FROM FAS_OFFICE_BANK_AC_CURRENT f " +
					        			"  WHERE f.ACCOUNTING_UNIT_ID=m.ACCOUNTING_UNIT_ID " +
					        			"  AND f.MODULE_ID           ='MF009' " +
					        			"  AND f.BANK_AC_NO          =m.HO_ACCOUNT_NO " +
					        			"  )AS newAcHead " +
					        			"FROM FAS_FUND_RECEIPT_BY_HO m " +
					        			" WHERE ACCOUNTING_UNIT_ID    ="+cmbAcc_UnitCode+
					        			" AND ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+
					        			" AND CASHBOOK_YEAR          ="+txtCB_Year+
					        			" AND CASHBOOK_MONTH         ="+txtCB_Month+
					        			" AND HO_ACCOUNT_NO       ='"+cmbBankAccNo+"'";
					        		}*/
					        		
					        	}else if(cmbDocType.equalsIgnoreCase("FT")){
					        		qry ="SELECT VOUCHER_NO                        AS docNo, " +
					        	"  ACCOUNTING_UNIT_ID                     AS unitid, " +
					        	"  ACCOUNTING_FOR_OFFICE_ID               AS offid, " +
					        	"  CASHBOOK_YEAR                          AS yr, " +
					        	"  CASHBOOK_MONTH                         AS mnth, " +
					        	"  OFFICE_ACCOUNT_NO                      AS accno, " +
					        	"  'FT'                                   AS doctypp, " +
					        	"  PARTICULARS                            AS REMARKS, " +
					        	"  TO_CHAR(DATE_OF_TRANSFER,'DD/MM/YYYY') AS DocDate, " +
					        	"  REMITTANCE_TYPE                        AS DocType, " +
					        	"  Dr_ACCOUNT_HEAD_CODE                   AS oldAcHead,' ' as oldAcHeadCr,' 'AS newAcHeadCr,  " +
					        	"  TOTAL_AMOUNT                           AS Amt, " +
					        	"  'DR' CR_DR_INDICATOR , " +
					        	"  (SELECT AC_HEAD_CODE " +
					        	"  FROM FAS_OFFICE_BANK_AC_CURRENT f " +
					        	"  WHERE f.ACCOUNTING_UNIT_ID=m.ACCOUNTING_UNIT_ID " +
					        	"  AND f.MODULE_ID           ='MF015' " +
					        	"  AND f.BANK_AC_NO          =m.OFFICE_ACCOUNT_NO " +
					        	 " AND f.STATUS='Y' "+
					        	"  )AS newAcHead " +
					        	"FROM FAS_FUND_TRF_FROM_OFFICE m " +
					        	" WHERE ACCOUNTING_UNIT_ID    ="+cmbAcc_UnitCode+
			        			" AND ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+
			        			" AND CASHBOOK_YEAR          ="+txtCB_Year+
			        			" AND CASHBOOK_MONTH         ="+txtCB_Month+
			        			" AND TRANSFER_STATUS='L' "+
			        			" AND OFFICE_ACCOUNT_NO       ='"+cmbBankAccNo+"'";
					        	}else if(cmbDocType.equalsIgnoreCase("IBT")){
					        		qry="SELECT VOUCHER_NO          AS docNo, " +
					        		"  ACCOUNTING_UNIT_ID       AS unitid, " +
					        		"  ACCOUNTING_FOR_OFFICE_ID AS offid, " +
					        		"  CASHBOOK_YEAR            AS yr, " +
					        		"  CASHBOOK_MONTH           AS mnth, " +
					        		"  '"+cmbBankAccNo+"' AS accno, " +
					        		"  'IBT'                                  AS doctypp, " +
					        		"  PARTICULARS                            AS REMARKS, " +
					        		"  TO_CHAR(DATE_OF_TRANSFER,'DD/MM/YYYY') AS DocDate, " +
					        		"  ''                                     AS DocType, " +
					        		"  Dr_ACCOUNT_HEAD_CODE                   AS oldAcHead,' ' as oldAcHeadCr,' 'AS newAcHeadCr,  " +
					        		"  TOTAL_AMOUNT                           AS Amt, " +
					        		"  'DR' CR_DR_INDICATOR, " +
					        		"  (SELECT AC_HEAD_CODE " +
					        		"  FROM FAS_OFFICE_BANK_AC_CURRENT f " +
					        		"  WHERE f.ACCOUNTING_UNIT_ID=m.ACCOUNTING_UNIT_ID " +
					        		"  AND f.MODULE_ID           ='MF010' " +
					        		"  AND f.BANK_AC_NO          ='"+cmbBankAccNo+"'" +
					        		"  AND f.CR_DR_TYPE='CR' "+
					        		 " AND f.STATUS='Y' "+
					        		"  )AS newAcHead " +
					        		" FROM FAS_INTER_BANK_TRF_AT_HO m " +
					        		" WHERE ACCOUNTING_UNIT_ID    = " +cmbAcc_UnitCode+
					        		" AND ACCOUNTING_FOR_OFFICE_ID= " +cmbOffice_code+
					        		" AND CASHBOOK_YEAR           = " +txtCB_Year+
					        		" AND CASHBOOK_MONTH          = " +txtCB_Month+
					        		" AND TRANSFER_STATUS='L' "+
					        		" AND (FROM_ACCOUNT_NO        ='"+cmbBankAccNo+"'"+
					        		" OR TO_ACCOUNT_NO            ='"+cmbBankAccNo+"')";
					        	}
	        	  
	         System.out.println(qry);
	        ResultSet result = statement.executeQuery(qry);
	         
	       try
	         {  
	        	 xml=xml+"<flag>success</flag>";
	        	 String valExists = "No";
	             while(result.next())
	             { 
	            	 
	           
	            	 
	            	 valExists = "Yes";
	            	
	                 
	            	 xml += "<docNo>" + result.getInt("docNo") + "</docNo>";
	            	
	            	 xml=xml+"<DocDate>" + result.getString("DocDate").trim() + "</DocDate>";
	                 xml=xml+"<DocType>" + result.getString("DocType") + "</DocType>";
	                 xml=xml+"<oldAcHead>" + result.getInt("oldAcHead") + "</oldAcHead>";
	                 xml=xml+"<Amt>" + result.getInt("Amt") + "</Amt>";
	                 xml=xml+"<CR_DR_INDICATOR>" + result.getString("CR_DR_INDICATOR") +"</CR_DR_INDICATOR>";
	                 xml=xml+"<newAcHead>" + result.getInt("newAcHead") + "</newAcHead>";
	                 xml=xml+"<oldAcHeadCr>" + result.getString("oldAcHeadCr") + "</oldAcHeadCr>";
	                 xml=xml+"<newAcHeadCr>" + result.getString("newAcHeadCr") + "</newAcHeadCr>";
	                 xml=xml+"<unitid>" + result.getInt("unitid") + "</unitid>";
	                 xml=xml+"<offid>" + result.getInt("offid") + "</offid>";
	                 xml=xml+"<yr>" + result.getInt("yr") +"</yr>";
	                 xml=xml+"<mnth>" + result.getInt("mnth") + "</mnth>";
	                 xml=xml+"<accno>" + result.getString("accno") + "</accno>";
	                 xml=xml+"<doctypp>" + result.getString("doctypp") + "</doctypp>";
	                 xml=xml+"<REMARKS><![CDATA[" + result.getString("REMARKS") + "]]></REMARKS>";
	              	// xml =xml+ "<PARTICULARS><![CDATA[" + PARTICULARS + "]]></PARTICULARS>";
	               //  xml=xml+"<issues_yr_value>" + result.getInt("ISSUES_YR_CR_VALUE") + "</issues_yr_value>";
	                 
	            	               
	            	 count1++;
	             }

	             xml =xml+ "<exists>"+valExists+"</exists>";
	             xml =xml+ "<count>"+count1+"</count>";
	         }catch(Exception e)
	         {
	        	 System.out.println("Exception in getting values from DB - Go GET: " + e);
	         }
	        }
	        catch(Exception e1)
	        {
	        	System.out.println("Exception is in Get ---> "+e1);
	        	xml=xml+"<flag>failure</flag>";
	        }
	       // xml=xml+"</response>";
	    } 
			
		xml = xml + "</response>";
		out.write(xml);
		System.out.println(xml);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {



		
		Connection connection=null;
        Statement statement=null;
        ResultSet result=null,rss1=null,rss3=null;
        PreparedStatement ps1=null,ps3=null,ps_ft1=null;
        try
        {
    	   ResourceBundle rs=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
           String ConnectionString="";

           String strDriver=rs.getString("Config.DATA_BASE_DRIVER");
           String strdsn=rs.getString("Config.DSN");
           String strhostname=rs.getString("Config.HOST_NAME");
           String strportno=rs.getString("Config.PORT_NUMBER");
           String strsid=rs.getString("Config.SID");
           String strdbusername=rs.getString("Config.USER_NAME");
           String strdbpassword=rs.getString("Config.PASSWORD");
              
           ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
           Class.forName(strDriver.trim());
           connection=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
           try
           {
        	   statement=connection.createStatement();
               connection.clearWarnings();
           }
           catch(SQLException e)
           {
               System.out.println("Exception in creating statement:"+e);
           }
        }
        catch(Exception e)
        {
           System.out.println("Exception in openeing connection:"+e);
        }
        response.setContentType(CONTENT_TYPE);
        String strCommand = ""; 
        int unit_id = 0;
        int office_id = 0;
	   	String cmbBankAccNo = null;
	   	String cmbDocType=null;
	   	
        HttpSession session=request.getSession(false);
        try
        {
	        if(session==null)
	        {
	            System.out.println(request.getContextPath()+"/index.jsp");
	            response.sendRedirect(request.getContextPath()+"/index.jsp");
	        }
	        System.out.println(session);
        }catch(Exception e)
        {
        	System.out.println("Redirect Error :"+e);
        }
               
        String userid=(String)session.getAttribute("UserId");
        //System.out.println("Session id is:"+userid);
        response.setContentType("text/xml");
        PrintWriter pw=response.getWriter();    
        response.setHeader("Cache-Control","no-cache");
        long l=System.currentTimeMillis();
        Timestamp ts=new Timestamp(l);
        try
        {
        	strCommand = request.getParameter("command");     
        	System.out.println("strCommand : post " + strCommand);
        }
        catch(Exception e)
        {
          e.printStackTrace();
        }
        int up=0;
        int txtCB_Month=0;
        int txtCB_Year=0;
        
        
        try{
       
           if(strCommand.equalsIgnoreCase("updatechange")){
        	 
        	   connection.setAutoCommit(false);
        	   try
               {
               	unit_id = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
               }
               catch(Exception e)
               { 
                   System.out.println("IGNORABLE Exception getting 'accounting_unit_id' parameter ===> " + e);
               }        
               
               try
               {
               	office_id = Integer.parseInt(request.getParameter("cmbOffice_code"));
               }
               catch(Exception e)
               { 
                   System.out.println("IGNORABLE Exception getting 'accounting_unit_office_id' parameter ===> " + e);
               } 
               try
               {
            	   txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));
            	   txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
               }
               catch(Exception e)
               { 
                   System.out.println("IGNORABLE Exception getting 'txtCB_Year' parameter ===> " + e);
               } 
               
               try
               {
            	   cmbBankAccNo =request.getParameter("cmbBankAccNo");
            	   cmbDocType =request.getParameter("cmbDocType");
            	   
               }
               catch(Exception e)
               { 
                   System.out.println("IGNORABLE Exception getting 'financial_year' parameter ===> " + e);
               } 
             
               int RecordCount = 0;

				try {
					RecordCount = Integer.parseInt(request
							.getParameter("RecordCount"));
				} catch (Exception e) {
					System.out
							.println("Error Getting Total Number of Records in TWAD Transaction ");
				}

			

				System.out.println(" RecordCount "+RecordCount);

				
				
				
				 
			
				String doc_no1[] = new String[RecordCount];
				String doc_date1[] = new String[RecordCount];
				String doc_type1[] = new String[RecordCount];
				String oldAcHead1[] = new String[RecordCount];
				String Amt1[] = new String[RecordCount];
				String CR_DR_INDICATOR1[] = new String[RecordCount];
				String newAcHead1[] = new String[RecordCount];
				String verify_select1[] = new String[RecordCount];
				String verify_select_status1[] = new String[RecordCount];
				String newAcHeadCr1[] = new String[RecordCount];
				String oldAcHeadCr1[] = new String[RecordCount];
				String unitid1[] = new String[RecordCount];
				String offid1[] = new String[RecordCount];
				String yr1[] = new String[RecordCount];
				String mnth1[] = new String[RecordCount];
				String accno1[] = new String[RecordCount];
				String doctypp1[] = new String[RecordCount];
				
				
				
				
				
				String doc_type2 = null;
				int doc_no2 = 0;
				Date doc_date2 = null;
				int oldAcHead2=0;
				int Amt2=0;
				String CR_DR_INDICATOR2="";
				int newAcHead2=0;
				
				String verify_select2 = null;
				String verify_select_status2 = null;
				int unitid2=0;
				int offid2=0;
				int yr2=0;
				int mnth2=0;
				int oldAcHeadCr2=0;
				int newAcHeadCr2=0;
				String accno2 = null;
				String doctypp2 = "";
				
				
				String sd1[] = new String[10];
				java.util.Date d1 = null;
				Calendar c1;

				String sd[] = new String[10];
				java.util.Date d = null;
				
				Calendar c;
				int rowcheck=0;
				int rowuncheck=0;
				try {
					for (int k = 0; k < RecordCount; k++) {

						
						try {
							doc_date1[k] = request.getParameter("DocDate" + k);

							if (!doc_date1[k].equalsIgnoreCase("")) {
								sd = doc_date1[k].split("/");

								c = new GregorianCalendar(
										Integer.parseInt(sd[2]),
										Integer.parseInt(sd[1]) - 1,
										Integer.parseInt(sd[0]));
								d = c.getTime();
								doc_date2 = new Date(d.getTime());
							}
						} catch (Exception e) {
							System.out
									.println("Error Converting doc Date -->"
											+ e);
						}

						
						try {
							doc_no1[k] = request.getParameter("docNo" + k);
							doc_no2 = Integer.parseInt(doc_no1[k]);
						} catch (Exception e) {
							System.out.println(e);
						}
						
						try {
							oldAcHead1[k] = request.getParameter("oldAcHead" + k);
							oldAcHead2 = Integer.parseInt(oldAcHead1[k]);
							System.out.println("oldAcHead"+request.getParameter("oldAcHead" + k));
						} catch (Exception e) {
							System.out.println(e);
						}
						
						try {
							newAcHead1[k] = request.getParameter("newAcHead" + k);
							newAcHead2 = Integer.parseInt(newAcHead1[k]);
							System.out.println("newAcHead"+request.getParameter("newAcHead" + k));
						} catch (Exception e) {
							System.out.println(e);
						}
						
						
						
						try {
							newAcHeadCr1[k] = request.getParameter("newAcHeadCr" + k);
							newAcHeadCr2 = Integer.parseInt(newAcHeadCr1[k]);
							System.out.println("newAcHeadCr"+request.getParameter("newAcHeadCr" + k));
						} catch (Exception e) {
							System.out.println(e);
						}
						
						
						
						try {
							oldAcHeadCr1[k] = request.getParameter("oldAcHeadCr" + k);
							oldAcHeadCr2 = Integer.parseInt(oldAcHeadCr1[k]);
							System.out.println("oldAcHeadCr"+request.getParameter("oldAcHeadCr" + k));
						} catch (Exception e) {
							System.out.println(e);
						}
						try {
							Amt1[k] = request.getParameter("Amt" + k);
							Amt2 = Integer.parseInt(Amt1[k]);
						} catch (Exception e) {
							System.out.println(e);
						}

						try {
							verify_select1[k] = request.getParameter("verify_select"+ k);
							verify_select2 = verify_select1[k];
							System.out.println("verify_select2-->"+ verify_select2);
							if (verify_select2 != null) {
								if (verify_select2.equals("CHECKED")) {

									verify_select2="C";
									
								} else {
									verify_select2 = "NA";
								}
							} else if (verify_select2 == "") {
								verify_select2 = "NA";
							} else if (verify_select2 == null) {
								verify_select2 = "NA";
							} else {
								verify_select2 = "NA";
							}
						} catch (Exception e) {
							System.out.println(e);
						}
						
						try {
							verify_select_status1[k] = request.getParameter("verify_select_status"+ k);
							verify_select_status2 = verify_select_status1[k];
							System.out.println("verify_select_status2-->"+ verify_select_status2);
							if (verify_select_status2 != null) {
								if (verify_select_status2.equals("Y")) {

								} else {
									verify_select_status2 = "NA";
								}
							} else if (verify_select_status2 == "") {
								verify_select_status2 = "NA";
							} else if (verify_select_status2 == null) {
								verify_select_status2 = "NA";
							} else {
								verify_select_status2 = "NA";
							}
						} catch (Exception e) {
							System.out.println(e);
						}
						
						
						try {
							CR_DR_INDICATOR1[k] = request.getParameter("CR_DR_INDICATOR" + k);
							CR_DR_INDICATOR2 = CR_DR_INDICATOR1[k];
						} catch (Exception e) {
							System.out.println(e);
						}
						
						try {
							unitid1[k] = request.getParameter("unitid" + k);
							unitid2 = Integer.parseInt(unitid1[k]);
						} catch (Exception e) {
							System.out.println(e);
						}
						
						try {
							offid1[k] = request.getParameter("offid" + k);
							offid2 = Integer.parseInt(offid1[k]);
						} catch (Exception e) {
							System.out.println(e);
						}
						
						try {
							yr1[k] = request.getParameter("yr" + k);
							yr2 = Integer.parseInt(yr1[k]);
						} catch (Exception e) {
							System.out.println(e);
						}
						
						try {
							mnth1[k] = request.getParameter("mnth" + k);
							mnth2 = Integer.parseInt(mnth1[k]);
						} catch (Exception e) {
							System.out.println(e);
						}
						
						try {
							accno1[k] = request.getParameter("accno" + k);
							accno2 = accno1[k];
						} catch (Exception e) {
							System.out.println(e);
						}
						
						try {
							doctypp1[k] = request.getParameter("doctypp" + k);
							doctypp2 = doctypp1[k];
						} catch (Exception e) {
							System.out.println(e);
						}
						
						try {
							doc_type1[k] = request.getParameter("DocType" + k);
							doc_type2 = doc_type1[k];
						} catch (Exception e) {
							System.out.println(e);
						}
					/*	
						System.out.println("unit id "+unit_id);
						System.out.println("office  "+office_id);
						System.out.println("month "+txtCB_Month);
						System.out.println("year "+txtCB_Year);
						
						System.out.println("aaccc "+cmbBankAccNo);
						System.out.println("type "+cmbDocType);
						System.out.println("doc_type2 "+doc_type2);
						System.out.println("doc_no2 "+doc_no2);
						System.out.println("doc_date2 "+doc_date2);
						System.out.println("oldAcHead2 "+oldAcHead2);
						System.out.println("Amt2 "+Amt2);
						System.out.println("CR_DR_INDICATOR2 "+CR_DR_INDICATOR2);
						System.out.println("newAcHead2 "+newAcHead2);
						System.out.println("verify_select2 "+verify_select2);
						System.out.println("verify_select_status2 "+verify_select_status2);
						System.out.println("unitid2 "+unitid2);
						System.out.println("offid2 "+offid2);
						System.out.println("yr2 "+yr2);
						System.out.println("mnth2 "+mnth2);
						System.out.println("accno2 "+accno2);
						System.out.println("doctypp2 "+doctypp2);*/
						
						
						
						//if ((verify_select_status2.equals("C"))||(verify_select2.equals("C"))) {
							
						if ((verify_select2.equals("C"))) {

							System.out.println("update newAcHead2 "+verify_select2+"--"+newAcHead2);
							//update 
							if(newAcHead2!=0){
								
							/*}
							else{
							connection.rollback();	
							sendMessage(response,"Record Not Insert  ","ok");
							
							}*/
							if(doctypp2.equalsIgnoreCase("P")){
								System.out.println("payamnet ");
								String sqlupdate="update FAS_PAYMENT_MASTER set ACCOUNT_HEAD_CODE=?" +	
								" where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? " +
								"and PAYMENT_DATE=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? " +
								"and PAYMENT_TYPE=? and VOUCHER_NO=? and ACCOUNT_NO=? " +
								"and CR_DR_INDICATOR=? and TOTAL_AMOUNT=?";        		            			   
	   					PreparedStatement ps = connection.prepareStatement(sqlupdate);
	   					
	   					
	   					ps.setInt(1,newAcHead2); 
	   					ps.setInt(2,unit_id);
	   					ps.setInt(3,office_id);
	   					ps.setDate(4,doc_date2);
	   					ps.setInt(5,txtCB_Year);
	   					ps.setInt(6,txtCB_Month);
	   					ps.setString(7,doc_type2);
	   					ps.setInt(8,doc_no2);
	   					ps.setString(9,accno2); 
	   					ps.setString(10,CR_DR_INDICATOR2);
	   					ps.setInt(11,Amt2);
	   					up=ps.executeUpdate();
	   					//System.out.println("sqlupdate-->"+sqlupdate);
	   					System.out.println("up ..."+up);
	   					rowcheck++;
							}else if(doctypp2.equalsIgnoreCase("R")){
								System.out.println("FAS_RECEIPT_MASTER  ");
								String sqlupdate="update FAS_RECEIPT_MASTER set ACCOUNT_HEAD_CODE=?" +	
								" where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? " +
								"and RECEIPT_DATE=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? " +
								"and RECEIPT_TYPE=? and RECEIPT_NO=? and ACCOUNT_NO=? " +
								"and CR_DR_INDICATOR=? and TOTAL_AMOUNT=?";        		            			   
	   					PreparedStatement ps = connection.prepareStatement(sqlupdate);
	   					
	   					
	   					ps.setInt(1,newAcHead2); 
	   					ps.setInt(2,unit_id);
	   					ps.setInt(3,office_id);
	   					ps.setDate(4,doc_date2);
	   					ps.setInt(5,txtCB_Year);
	   					ps.setInt(6,txtCB_Month);
	   					ps.setString(7,doc_type2);
	   					ps.setInt(8,doc_no2);
	   					ps.setString(9,accno2); 
	   					ps.setString(10,CR_DR_INDICATOR2);
	   					ps.setInt(11,Amt2);
	   					up=ps.executeUpdate();
	   					//System.out.println("up ..."+up);
	   					rowcheck++;
							}else if(doctypp2.equalsIgnoreCase("FR")){
						//if(unit_id!=5){

								System.out.println("FAS_FUND_RECEIPT_BY_OFFICE  ");
								String sqlupdate="UPDATE FAS_FUND_RECEIPT_BY_OFFICE " +
								" SET Dr_ACCOUNT_HEAD_CODE    =?,Cr_ACCOUNT_HEAD_CODE=? " +
								" WHERE ACCOUNTING_UNIT_ID    =? " +
								" AND ACCOUNTING_FOR_OFFICE_ID=? " +
								" AND RECEIPT_DATE            =? " +
								" AND CASHBOOK_YEAR           =? " +
								" AND CASHBOOK_MONTH          =? " +
								" AND RECEIPT_NO              =? " +
								" AND OFFICE_ACCOUNT_NO       =? " +
								" AND TOTAL_AMOUNT            =? and Dr_ACCOUNT_HEAD_CODE=?  and Cr_ACCOUNT_HEAD_CODE=?" ; 		            			   
	   				
	   				 PreparedStatement ps_rf=connection.prepareStatement(sqlupdate);
	   				ps_rf.setInt(1,newAcHead2); System.out.println("newAcHead2"+newAcHead2);
	   				ps_rf.setInt(2,newAcHeadCr2);  System.out.println("newAcHeadCr2"+newAcHeadCr2);
	   				ps_rf.setInt(3,unit_id);
	   				ps_rf.setInt(4,office_id);
	   				ps_rf.setDate(5,doc_date2);
	   				ps_rf.setInt(6,txtCB_Year);
	   				ps_rf.setInt(7,txtCB_Month);
	   				ps_rf.setInt(8,doc_no2);
	   				ps_rf.setString(9,accno2); 
	   				ps_rf.setInt(10,Amt2);
	   				ps_rf.setInt(11,oldAcHead2); System.out.println("oldAcHead2"+oldAcHead2);
	   				ps_rf.setInt(12,oldAcHeadCr2);System.out.println("oldAcHeadCr2"+oldAcHeadCr2);
						int kk=ps_rf.executeUpdate();
	   					System.out.println(sqlupdate +"  upkk ..."+kk);
	   					up=kk;
	   					rowcheck++;
						/*	}else{


								System.out.println("FAS_FUND_RECEIPT_BY_OFFICE  ");
								String sqlupdate="UPDATE FAS_FUND_RECEIPT_BY_HO " +
								" SET cr_ACCOUNT_HEAD_CODE    =? " +
								" WHERE ACCOUNTING_UNIT_ID    =? " +
								" AND ACCOUNTING_FOR_OFFICE_ID=? " +
								" AND RECEIPT_DATE            =? " +
								" AND CASHBOOK_YEAR           =? " +
								" AND CASHBOOK_MONTH          =? " +
								" AND RECEIPT_NO              =? " +
								" AND HO_ACCOUNT_NO       =? " +
								" AND TOTAL_AMOUNT            =? and cr_ACCOUNT_HEAD_CODE=?" +
								" AND RECEIPT_TYPE              =? " ; 		            			   
	   				
								 ps_ft1=connection.prepareStatement(sqlupdate);
								ps_ft1.setInt(1,newAcHead2); 
								ps_ft1.setInt(2,unit_id);
								ps_ft1.setInt(3,office_id);
								ps_ft1.setDate(4,doc_date2);
								ps_ft1.setInt(5,txtCB_Year);
								ps_ft1.setInt(6,txtCB_Month);
								ps_ft1.setInt(7,doc_no2);
								ps_ft1.setString(8,accno2); 
								ps_ft1.setInt(9,Amt2);
								ps_ft1.setInt(10,oldAcHead2);
								ps_ft1.setString(11,doc_type2);
						int kk=ps_ft1.executeUpdate();
	   					System.out.println(sqlupdate +"  upkk ..."+kk);
	   					up=kk;
	   					rowcheck++;
						
							
				        	
							}*/
							
				        	}else if(doctypp2.equalsIgnoreCase("FT")){
				        		String sqlupdate="UPDATE FAS_FUND_TRF_FROM_OFFICE " +
								" SET Dr_ACCOUNT_HEAD_CODE    =? " +
								" WHERE ACCOUNTING_UNIT_ID    =? " +
								" AND ACCOUNTING_FOR_OFFICE_ID=? " +
								" AND DATE_OF_TRANSFER            =? " +
								" AND CASHBOOK_YEAR           =? " +
								" AND CASHBOOK_MONTH          =? " +
								" AND VOUCHER_NO              =? " +
								" AND OFFICE_ACCOUNT_NO       =? " +
								" AND TOTAL_AMOUNT            =? and Dr_ACCOUNT_HEAD_CODE=?" ;

				   				 PreparedStatement ps_rf=connection.prepareStatement(sqlupdate);
				   				ps_rf.setInt(1,newAcHead2); 
				   				ps_rf.setInt(2,unit_id);
				   				ps_rf.setInt(3,office_id);
				   				ps_rf.setDate(4,doc_date2);
				   				ps_rf.setInt(5,txtCB_Year);
				   				ps_rf.setInt(6,txtCB_Month);
				   				ps_rf.setInt(7,doc_no2);
				   				ps_rf.setString(8,accno2); 
				   				ps_rf.setInt(9,Amt2);
				   				ps_rf.setInt(10,oldAcHead2);
									int kk=ps_rf.executeUpdate();
				   					System.out.println(sqlupdate +"  upkk ..."+kk);
				   					up=kk;
				   					rowcheck++;
				        	
				        	
				        	}else if(doctypp2.equalsIgnoreCase("IBT")){
				        	
				        		String sqlupdate=    "UPDATE FAS_INTER_BANK_TRF_AT_HO " +
				        		" SET Dr_ACCOUNT_HEAD_CODE    =? " +
				        		" WHERE ACCOUNTING_UNIT_ID    =? " +
				        		" AND ACCOUNTING_FOR_OFFICE_ID=? " +
				        		" AND DATE_OF_TRANSFER        =? " +
				        		" AND CASHBOOK_YEAR           =? " +
				        		" AND CASHBOOK_MONTH          =? " +
				        		" AND VOUCHER_NO              =? " +
				        		" AND ( FROM_ACCOUNT_NO       =? " +
				        		" OR TO_ACCOUNT_NO            =? ) " +
				        		" AND TOTAL_AMOUNT            =? " +
				        		" AND Dr_ACCOUNT_HEAD_CODE    =?";

				   				 PreparedStatement ps_rf=connection.prepareStatement(sqlupdate);
				   				ps_rf.setInt(1,newAcHead2); 
				   				ps_rf.setInt(2,unit_id);
				   				ps_rf.setInt(3,office_id);
				   				ps_rf.setDate(4,doc_date2);
				   				ps_rf.setInt(5,txtCB_Year);
				   				ps_rf.setInt(6,txtCB_Month);
				   				ps_rf.setInt(7,doc_no2);
				   				ps_rf.setString(8,accno2); 
				   				ps_rf.setString(9,accno2); 
				   				ps_rf.setInt(10,Amt2);
				   				ps_rf.setInt(11,oldAcHead2);
									int kk=ps_rf.executeUpdate();
				   					System.out.println(sqlupdate +"  upkk ..."+kk);
				   					up=kk;
				   					rowcheck++;
				        		
				        	}
							
		   					
		   					if(up>0)
							{
								connection.commit();
								//sendMessage(response,"Records Updated successfully ","ok");
							}
							else
							{
								//System.out.println(" inside    else  ");
								connection.rollback();	
								sendMessage(response,"Record Not Insert  ","ok");
							} 
						}
						else{
						connection.rollback();	
						sendMessage(response,"Record Not Insert  ","ok");
						
						}
						}
						else{
							System.out.println("not update  ");
							//not change
							rowuncheck++;
						}
						
						
						
						
					}//for close
					System.out.println("rowcheck   rowun check"+rowcheck+"--"+rowuncheck);
					int cc=rowcheck+rowuncheck;
					if(RecordCount==(cc)){
						System.out.println("RecordCount cc "+RecordCount+"---"+cc);
						
						connection.commit();
						sendMessage(response,"Records Updated successfully ","ok");
					}else{
						connection.rollback();	
						sendMessage(response,"Record Not Update  ","ok");
						//System.out.println("else RecordCount "+RecordCount+"  -->  "+cc);
					}
					
					
				}//try close
				catch (Exception e) {
					System.out.println("eeee "+e);
				}
           }
        }
           catch (Exception e) {
				System.out.println("Main try  "+e);
			}
             
             
	}
				 private void sendMessage(HttpServletResponse response, String msg, String bType) {
			try {
			String url =
			"org/Library/jsps/Messenger.jsp?message=" + msg + "&button=" +bType;
			response.sendRedirect(url);
			} catch (IOException e) {
			System.out.println(e);
			}
			}
	/*private void sendMessage(HttpServletResponse response,String msg,String bType)
	 {
	 	try
	 	{
	 		String url="org/Library/jsps/MessengerOkBack.jsp?message=" + msg + "&button=" + bType;
	 		response.sendRedirect(url);
	 	}
	 	catch(Exception e)
	 	{
	 		System.out.println("error in messenger"+e);
	 	}
  }*/

}
