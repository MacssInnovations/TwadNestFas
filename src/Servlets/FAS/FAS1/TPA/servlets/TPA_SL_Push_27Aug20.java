package Servlets.FAS.FAS1.TPA.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Servlets.FAS.FAS1.ReceiptSystem.servlets.SL_TYPE_CODE_NAME_GENERAL;

/**
 * Servlet implementation class TPA_SL_Push
 */
public class TPA_SL_Push_27Aug20 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String CONTENT_TYPE = "text/xml; charset=windows-1252";  
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TPA_SL_Push_27Aug20() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		/**
		 * Set Content Type 
		 */
		PrintWriter out = response.getWriter();
		response.setHeader("cache-control","no-cache");
		  String CONTENT_TYPE = "text/xml; charset=windows-1252";
		response.setContentType(CONTENT_TYPE);

		

		/**
		 * Session Checking 
		 */
		HttpSession session=request.getSession(false);
		try
		{	           
			if(session==null)
			{
				System.out.println(request.getContextPath()+"/index.jsp");
				response.sendRedirect(request.getContextPath()+"/index.jsp");
				return;
			}
			System.out.println(session);

		}catch(Exception e)
		{
			System.out.println("Redirect Error :"+e);
		}


		/**
		 * Variables Declaration 
		 */		        
		Connection con=null;
		PreparedStatement ps2=null,ps=null,ps1=null;        
		ResultSet rs2=null,rs=null,rs3=null;
		String sql=null;

		/**
		 * Database Connection 
		 */
		try
		{
			ResourceBundle rs1=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
			String ConnectionString="";
			String strDriver=rs1.getString("Config.DATA_BASE_DRIVER");
			String strdsn=rs1.getString("Config.DSN");
			String strhostname=rs1.getString("Config.HOST_NAME");
			String strportno=rs1.getString("Config.PORT_NUMBER");
			String strsid=rs1.getString("Config.SID");
			String strdbusername=rs1.getString("Config.USER_NAME");
			String strdbpassword=rs1.getString("Config.PASSWORD");
			ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection			Class.forName(strDriver.trim());
			con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
		}
		catch(Exception e)
		{
			System.out.println("Exception in opening connection :"+e);
		}


		int count=0,AccUnitId=0,officeCode=0,subLedgerType=0,secCount=0;
		String xml=null,cmd="",option=null,tpa_type=null;          
		 String txtCrea_date = "";
		 int txtCash_Month_hid = 0, txtCash_year = 0;
		 Calendar c;
		/** Get Employee ID */
		try{cmd=request.getParameter("command");}
		catch(Exception e){System.out.println(e);}
		System.out.println("cmd :: "+cmd);



		try{AccUnitId=Integer.parseInt(request.getParameter("unitid"));}
		catch(Exception e){System.out.println(e);}
		System.out.println("AccUnitId :: "+AccUnitId);

		try{subLedgerType=Integer.parseInt(request.getParameter("sltype"));}
		catch(Exception e){System.out.println(e);}
		System.out.println("subLedgerType :: "+subLedgerType);
		
//		String[] sd = request.getParameter("txtCreate_Date").split("/");
//		System.out.println("SL_Move date is "+request.getParameter("txtCreate_Date"));
//        c = new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
//                     Integer.parseInt(sd[0]));
//        java.util.Date d = c.getTime();
//        txtCrea_date = new Date(d.getTime());
		
//		txtCrea_date=request.getParameter("txtCreate_Date");
//
//        System.out.println("txtCrea_date " + txtCrea_date);

//        System.out.println("b4 getting month and year");
//        try {
//            txtCash_year = Integer.parseInt(sd[2]);
//        } catch (Exception e) {
//            System.out.println("exception" + e);
//        }
//        System.out.println("txtCash_year " + txtCash_year);
//
//        try {
//            txtCash_Month_hid = Integer.parseInt(sd[1]);
//        } catch (Exception e) {
//            System.out.println("exception" + e);
//        }
//        System.out.println("txtCash_Month_hid " + txtCash_Month_hid);
		
		
		
        try{txtCash_year=Integer.parseInt(request.getParameter("txtCB_Year"));}
        catch(NumberFormatException e){System.out.println("exception"+e );}
        System.out.println("cashbookyear "+txtCash_year);
        
        try{txtCash_Month_hid=Integer.parseInt(request.getParameter("txtCB_Month"));}
        catch(NumberFormatException e){System.out.println("exception"+e );}
        System.out.println("cashbookmonth "+txtCash_Month_hid);



		xml="<response>";
		if(cmd.equalsIgnoreCase("get"))   
		{
			xml=xml+"<command>get</command>";
			try
			{
				
				//changed on 16-feb-18
//				sql="select voucher_no,to_char(VOUCHER_DATE,'dd/MM/yyyy') as VOUCHER_DATE,accounting_unit_id,accounting_for_office_id from fas_tpa_master where trf_accounting_unit_id=? and acceptance_status is null and verify='Y' "
//						+ " and status='L' AND accounting_unit_id     IN (SELECT accounting_unit_id FROM FAS_TPA_STATUS where CASHBOOK_MONTH="+txtCash_Month_hid+" and CASHBOOK_YEAR="+txtCash_year+") order by voucher_no";

				sql="SELECT * FROM(  (SELECT VOUCHER_NO,  TO_CHAR(VOUCHER_DATE,'dd/MM/yyyy') AS VOUCHER_DATE,  ACCOUNTING_UNIT_ID,"+
						" ACCOUNTING_FOR_OFFICE_ID FROM FAS_TPA_MASTER WHERE TRF_ACCOUNTING_UNIT_ID=? AND ACCEPTANCE_STATUS      IS NULL " +
						" AND VERIFY ='Y' AND status ='L' AND ACCOUNTING_UNIT_ID     IN  (SELECT accounting_unit_id  FROM FAS_TPA_STATUS "+
						" WHERE CASHBOOK_MONTH="+txtCash_Month_hid+"  AND CASHBOOK_YEAR   ="+txtCash_year+"  )  )A  LEFT OUTER JOIN  " +
					    " (SELECT ACCOUNTING_UNIT_ID,TO_CHAR(TPA_FREEZE_DATE,'dd/MM/yyyy') AS TPA_FREEZE_DATE ,TPA_VR_NO FROM FAS_TPA_STATUS WHERE CASHBOOK_MONTH = "+txtCash_Month_hid +
					    " AND CASHBOOK_YEAR="+txtCash_year+")B ON A.ACCOUNTING_UNIT_ID=B.ACCOUNTING_UNIT_ID AND a.VOUCHER_NO=b.TPA_VR_NO)";
				

				System.out.println("SQL ::: "+sql);
				ps2=con.prepareStatement(sql);
				ps2.setInt(1,AccUnitId);

				rs2=ps2.executeQuery();                                 
				while(rs2.next()) 
				{
						System.out.println("while");
					int voucherNo=rs2.getInt("VOUCHER_NO");
					int sub_UnitId=rs2.getInt("accounting_unit_id");
					int sub_officeId=rs2.getInt("accounting_for_office_id");
					String voucherDate=rs2.getString("VOUCHER_DATE");
					String TPA_FREEZE_DATE=rs2.getString("TPA_FREEZE_DATE");
					ps=con.prepareStatement("select distinct SUB_LEDGER_CODE,SUB_LEDGER_TYPE_CODE,AMOUNT from fas_tpa_transaction where accounting_unit_id=? and accounting_for_office_id=? and sub_ledger_type_code=? and voucher_no=? and SL_CODE_PUSHED is null order by SUB_LEDGER_CODE") ;
					ps.setInt(1, sub_UnitId);
					ps.setInt(2, sub_officeId);
					ps.setInt(3, subLedgerType);
					ps.setInt(4, voucherNo);

					
					
						

					rs=ps.executeQuery();
					while(rs.next()){
						xml+="<sub_unitid>"+sub_UnitId +"</sub_unitid>";	
						xml+="<sub_officeid>"+ sub_officeId +"</sub_officeid>";	
						xml+="<voucherdate>"+voucherDate+"</voucherdate>";
						xml+="<voucherno>"+ voucherNo +"</voucherno>";	
						xml+="<amount>"+rs.getInt("AMOUNT")+"</amount>";	
						xml+="<TPA_FREEZE_DATE>"+TPA_FREEZE_DATE+"</TPA_FREEZE_DATE>";
						
						
						if(rs.getInt("SUB_LEDGER_TYPE_CODE")!=0 && rs.getInt("SUB_LEDGER_CODE")!=0)
						{
							xml+="<subledger_code>"+ rs.getInt("SUB_LEDGER_CODE") +"</subledger_code>";	
							SL_TYPE_CODE_NAME_GENERAL obj_gen=new SL_TYPE_CODE_NAME_GENERAL();
							ResultSet rs_get=obj_gen.getResult_General(sub_UnitId,sub_officeId,rs.getInt("SUB_LEDGER_TYPE_CODE"),rs.getInt("SUB_LEDGER_CODE"),0);
							String slcheck="";
							if(rs_get!=null)
							{

								while(rs_get.next())
								{	
									//System.out.println("I am in outside subledger"+rs.getInt("SUB_LEDGER_TYPE_CODE")+"----"+rs.getInt("SUB_LEDGER_CODE"));
									if(rs_get.getInt("cid")==rs.getInt("SUB_LEDGER_CODE") )
									{

										slcheck=rs_get.getString("cname");
										xml+= "<sub_ledger_desc>"+ slcheck +"</sub_ledger_desc>"; 
										System.out.println("I am in subledger");
									}
								}
								rs_get.close();
								if(slcheck=="")
								{
									xml+= "<sub_ledger_desc>--</sub_ledger_desc>";  
								}
							}
							else
							{
								System.out.println("null result set");
								xml+= "<sub_ledger_desc>--</sub_ledger_desc>";  
							}
						}
						else
							xml+= "<sub_ledger_desc>--</sub_ledger_desc>";  

						secCount++;
					}
					count++;
				}					              
				if(count==0)
				{
					xml+="<flag>NoFreezeData</flag>";		
				}
				else if(secCount==0)          
				{
					xml+="<flag>NoSubledger</flag>";
				}
				else
				{
					xml+="<flag>success</flag>";
				}





				

			}
			catch(Exception e) 
			{
				System.out.println("Exception in load_Voucher_No..."+e);
				xml+="<flag>"+e.getMessage()+"</flag>";
			}                      
		}
		if(cmd.equalsIgnoreCase("getOriUnit"))   
		{
//            try{txtCash_year=Integer.parseInt(request.getParameter("txtCB_Year"));}
//			catch(NumberFormatException e){System.out.println("exception"+e );}
//			System.out.println("cashbookyear "+txtCash_year);
//
//			try{txtCash_Month_hid=Integer.parseInt(request.getParameter("txtCB_Month"));}
//			catch(NumberFormatException e){System.out.println("exception"+e );}
//				System.out.println("cashbookmonth "+txtCash_Month_hid);
				
				xml=xml+"<command>getOriUnit</command>";
				
				try
                {

		              
	                System.out.println("SELECT * FROM(  (SELECT VOUCHER_NO,  TO_CHAR(VOUCHER_DATE,'dd/MM/yyyy') AS VOUCHER_DATE,  ACCOUNTING_UNIT_ID,"+
	        				" ACCOUNTING_FOR_OFFICE_ID,(SELECT COM.OFFICE_NAME FROM COM_MST_OFFICES com WHERE COM.OFFICE_ID=ACCOUNTING_FOR_OFFICE_ID )as oname FROM FAS_TPA_MASTER WHERE TRF_ACCOUNTING_UNIT_ID=? AND ACCEPTANCE_STATUS      IS NULL " +
	        				" AND VERIFY ='Y' AND status ='L' AND ACCOUNTING_UNIT_ID     IN  (SELECT accounting_unit_id  FROM FAS_TPA_STATUS "+
	        				" WHERE CASHBOOK_MONTH="+txtCash_Month_hid+"  AND CASHBOOK_YEAR   ="+txtCash_year+"  )  )A  LEFT OUTER JOIN  " +
	        			    " (SELECT ACCOUNTING_UNIT_ID,TO_CHAR(TPA_FREEZE_DATE,'dd/MM/yyyy') AS TPA_FREEZE_DATE ,TPA_VR_NO FROM FAS_TPA_STATUS WHERE CASHBOOK_MONTH = "+txtCash_Month_hid +
	        			    " AND CASHBOOK_YEAR="+txtCash_year+")B ON A.ACCOUNTING_UNIT_ID=B.ACCOUNTING_UNIT_ID AND a.VOUCHER_NO=b.TPA_VR_NO)");
					
				ps1=con.prepareStatement("SELECT * FROM(  (SELECT VOUCHER_NO,  TO_CHAR(VOUCHER_DATE,'dd/MM/yyyy') AS VOUCHER_DATE,  ACCOUNTING_UNIT_ID,"+
				" ACCOUNTING_FOR_OFFICE_ID,(SELECT COM.OFFICE_NAME FROM COM_MST_OFFICES com WHERE COM.OFFICE_ID=ACCOUNTING_FOR_OFFICE_ID ) as oname FROM FAS_TPA_MASTER WHERE TRF_ACCOUNTING_UNIT_ID=? AND ACCEPTANCE_STATUS      IS NULL " +
				" AND VERIFY ='Y' AND status ='L' AND ACCOUNTING_UNIT_ID     IN  (SELECT accounting_unit_id  FROM FAS_TPA_STATUS "+
				" WHERE CASHBOOK_MONTH="+txtCash_Month_hid+"  AND CASHBOOK_YEAR   ="+txtCash_year+"  )  )A  LEFT OUTER JOIN  " +
			    " (SELECT ACCOUNTING_UNIT_ID,TO_CHAR(TPA_FREEZE_DATE,'dd/MM/yyyy') AS TPA_FREEZE_DATE ,TPA_VR_NO FROM FAS_TPA_STATUS WHERE CASHBOOK_MONTH = "+txtCash_Month_hid +
			    " AND CASHBOOK_YEAR="+txtCash_year+")B ON A.ACCOUNTING_UNIT_ID=B.ACCOUNTING_UNIT_ID AND a.VOUCHER_NO=b.TPA_VR_NO)");
	                

					/*ps1=con.prepareStatement("SELECT * FROM(  (SELECT VOUCHER_NO,  TO_CHAR(VOUCHER_DATE,'dd/MM/yyyy') AS VOUCHER_DATE,  ACCOUNTING_UNIT_ID,"+
					" ACCOUNTING_FOR_OFFICE_ID,(SELECT COM.OFFICE_NAME FROM COM_MST_OFFICES com WHERE COM.OFFICE_ID=ACCOUNTING_FOR_OFFICE_ID ) as oname FROM FAS_TPA_MASTER WHERE TRF_ACCOUNTING_UNIT_ID=? " +
					"  AND status ='L' AND ACCOUNTING_UNIT_ID     IN  (SELECT accounting_unit_id  FROM FAS_TPA_STATUS "+
					" WHERE CASHBOOK_MONTH="+txtCash_Month_hid+"  AND CASHBOOK_YEAR   ="+txtCash_year+"  )  )A  LEFT OUTER JOIN  " +
				    " (SELECT ACCOUNTING_UNIT_ID,TO_CHAR(TPA_FREEZE_DATE,'dd/MM/yyyy') AS TPA_FREEZE_DATE ,TPA_VR_NO FROM FAS_TPA_STATUS WHERE CASHBOOK_MONTH = "+txtCash_Month_hid +
				    " AND CASHBOOK_YEAR="+txtCash_year+")B ON A.ACCOUNTING_UNIT_ID=B.ACCOUNTING_UNIT_ID AND a.VOUCHER_NO=b.TPA_VR_NO)");*/
	                
				ps1.setInt(1,AccUnitId);       
                rs3=ps1.executeQuery();
                    while(rs3.next())
                    {
                    	System.out.println("inside while loop");
                    	xml+= "<ori_unit_offc>"+ rs3.getInt("ACCOUNTING_FOR_OFFICE_ID") +"</ori_unit_offc>"; 
                    	xml+= "<ori_unit_offc_name>"+ rs3.getString("oname") +"</ori_unit_offc_name>"; 
                    	count++;
                    }
                    if(count>0)
                    {
                    	xml+="<flag>success</flag>";
                    }
                    else
                    {
                    	xml+="<flag>failure</flag>";
                    }
                }
                    catch(Exception e)
                    {
                    System.out.println("Exception in Reason combo..."+e);
                    }
                      
		}

		xml=xml+"</response>";
		System.out.println("xml :: "+xml);
		out.println(xml);
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/**
		 * Session Checking 
		 */
		HttpSession session=request.getSession(false);
		try
		{	           
			if(session==null)
			{
				System.out.println(request.getContextPath()+"/index.jsp");
				response.sendRedirect(request.getContextPath()+"/index.jsp");
				return;
			}
			System.out.println(session);

		}catch(Exception e)
		{
			System.out.println("Redirect Error :"+e);
		}


		/**
		 * Variables Declaration 
		 */		        
		Connection con=null;
		PreparedStatement ps2=null,ps=null;        
		ResultSet rs2=null,rs=null;
		String cmd = "";
		/**
		 * Database Connection 
		 */
		try
		{
			ResourceBundle rs1=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
			String ConnectionString="";
			String strDriver=rs1.getString("Config.DATA_BASE_DRIVER");
			String strdsn=rs1.getString("Config.DSN");
			String strhostname=rs1.getString("Config.HOST_NAME");
			String strportno=rs1.getString("Config.PORT_NUMBER");
			String strsid=rs1.getString("Config.SID");
			String strdbusername=rs1.getString("Config.USER_NAME");
			String strdbpassword=rs1.getString("Config.PASSWORD");
			ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection			Class.forName(strDriver.trim());
			con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
		}
		catch(Exception e)
		{
			System.out.println("Exception in opening connection :"+e);
		}
		
		try {
			cmd = request.getParameter("Command");
			System.out.println("assign..here command..." + cmd);

		}

		catch (Exception e) {
			System.out.println("Exception in assigning..." + e);
		}
		
		
//		if (cmd.equalsIgnoreCase("Add")) {
			
			
			System.out.println("Inside Add");
			String CONTENT_TYPE = "text/html; charset=windows-1252";
			response.setContentType(CONTENT_TYPE);
			
		int cmbAcc_UnitCode=0,cmbOffice_code=0,slType=0,subUnitId=0,subOfficeId=0,voucherNo=0;
		String update_user=(String)session.getAttribute("UserId");
		long l=System.currentTimeMillis();
		Timestamp ts=new Timestamp(l);                      
		int newSLCode=0;
		String chckparameter_Voucher_no[]=null; 
		try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
		catch(NumberFormatException e){System.out.println("exception"+e );}
		System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);

		try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
		catch(NumberFormatException e){System.out.println("exception"+e );}
		System.out.println("cmbOffice_code "+cmbOffice_code);

		
		
		
		String slPushDate=request.getParameter("txtCreate_Date");

		try{slType=Integer.parseInt(request.getParameter("cmbMas_SL_type"));}
		catch(NumberFormatException e){System.out.println("exception"+e );}
		System.out.println("slType "+slType);

		try{subUnitId=Integer.parseInt(request.getParameter("subunitid"));}
		catch(NumberFormatException e){System.out.println("exception"+e );}
		System.out.println("subUnitId "+subUnitId);

		try{subOfficeId=Integer.parseInt(request.getParameter("subofficeid"));}
		catch(NumberFormatException e){System.out.println("exception"+e );}
		System.out.println("subOfficeId "+subOfficeId);

		try{voucherNo=Integer.parseInt(request.getParameter("voucherno"));}
		catch(NumberFormatException e){System.out.println("exception"+e );}
		System.out.println("voucherNo "+voucherNo);

		String voucherDate=request.getParameter("voucherdate"); 

		chckparameter_Voucher_no = request.getParameterValues("chckparameter");  
		try{
			
				String SQL="";
			if(slType==1)
			{
				SQL="select max(SUPPLIER_ID) as total from COM_SUPPLIER_SL_MST";	
			}else if(slType==2)
			{
				SQL="select max(FIRMS_ID) as total from COM_FIRMS_SL_MST";	
			}else if(slType==3)
			{
				SQL="select max(ASSET_CODE) as total from COM_MST_ASSETS_SL";	
			}else if(slType==5)
			{
				SQL="select max(OFFICE_ID) as total from COM_MST_OFFICES";	
			}else if(slType==9)
			{
				SQL="select max(OTHER_DEPT_OFFICE_ID) as total from HRM_MST_OTHER_DEPT_OFFICES";	
			}else if(slType==10)
			{
				SQL="select max(PROJECT_ID) as total from PMS_MST_PROJECTS_VIEW";	
			}else if(slType==11)
			{
				SQL="select max(CONTRACTOR_ID) as total from PMS_CONT_REQUEST_REGN";	
			}else if(slType==13)
			{
				SQL="select max(BENEFICIARY_ID) as total from pms_mst_beneficiary";	
			}
			else if(slType==15)
			{
				SQL="select max(ACCOUNTING_UNIT_ID) as total from FAS_MST_ACCT_UNITS";	
			}

			ps=con.prepareStatement(SQL);
			rs=ps.executeQuery();
			rs.next();
			newSLCode=rs.getInt("total");
			newSLCode++;
			con.clearWarnings();
			con.setAutoCommit(false);
			for(int i=0;i<chckparameter_Voucher_no.length;i++)
			{
				System.out.println("chckparameter_Voucher_no[i]"+chckparameter_Voucher_no[i]);
				String voucherslcode[]=chckparameter_Voucher_no[i].split(",");
				
				//int slCode=Integer.parseInt(chckparameter_Voucher_no[i]);
				int slCode=Integer.parseInt(voucherslcode[0]);
				voucherNo=Integer.parseInt(voucherslcode[1]);
				System.out.println("subcode"+slCode);
				ps=con.prepareStatement("update FAS_TPA_TRANSACTION set SL_CODE_PUSHED='Y' , SL_PUSHED_DATE=to_date(?,'dd/MM/yyyy') where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and VOUCHER_NO=? and SUB_LEDGER_TYPE_CODE=? and SUB_LEDGER_CODE=?");
				ps.setString(1, slPushDate);
				ps.setInt(2, subUnitId);
				ps.setInt(3, subOfficeId);
				ps.setInt(4, voucherNo);
				ps.setInt(5, slType);
				ps.setInt(6, slCode);
			    ps.executeUpdate();
				ps.close();
				System.out.println("subcode"+slType);
				if(slType==1){
					ps=con.prepareStatement("select count(*) as cnt from COM_SUPPLIER_SL_MST where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and OLD_SL_CODE=?");
					ps.setInt(1, cmbAcc_UnitCode);
					ps.setInt(2, cmbOffice_code);
					ps.setInt(3, slCode);
					rs=ps.executeQuery();
					rs.next();

					if(rs.getInt("cnt")<1)
					{
						ps.close();
						rs.close();
						ps=con.prepareStatement("select * from COM_SUPPLIER_SL_MST where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and  SUPPLIER_ID=?");
						ps.setInt(1, subUnitId);
						ps.setInt(2, subOfficeId);
						ps.setInt(3, slCode);
						rs=ps.executeQuery();
						if(rs.next())
						{
							System.out.println("in insert Block");
							String sQL="insert into COM_SUPPLIER_SL_MST(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,SUPPLIER_ID,SUPPLIER_ALIAS_ID,SUPPLIER_NAME,SUPPLIER_ADDRESS,SUPPLIER_PHONE, \n" +
							"SUPPLIER_FAX,SUPPLIER_EMAIL_ID,DATE_OF_REGISTRATION,DATE_OF_LAST_SUPPLY,UPDATED_BY_USER_ID,UPDATED_DATE,SUPPLIER_CITY,SUPPLIER_ADDRESS1,PINCODE,OLD_OFFICE_ID, \n" +
							"OLD_SL_CODE,DATE_OF_TRANSFER,TPA_VOUCHER_NO,TPA_VOUCHER_DATE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,to_date(?,'dd/MM/yyyy'),?,to_date(?,'dd/MM/yyyy'))";

							ps2=con.prepareStatement(sQL);
							ps2.setInt(1, cmbAcc_UnitCode);
							ps2.setInt(2, cmbOffice_code);
							ps2.setInt(3, newSLCode);
							ps2.setInt(4, rs.getInt("SUPPLIER_ALIAS_ID"));
							ps2.setString(5, rs.getString("SUPPLIER_NAME"));
							ps2.setString(6, rs.getString("SUPPLIER_ADDRESS"));
							ps2.setString(7, rs.getString("SUPPLIER_PHONE"));
							ps2.setString(8, rs.getString("SUPPLIER_FAX"));
							ps2.setString(9, rs.getString("SUPPLIER_EMAIL_ID"));
							ps2.setDate(10, rs.getDate("DATE_OF_REGISTRATION"));
							ps2.setDate(11, rs.getDate("DATE_OF_LAST_SUPPLY"));
							ps2.setString(12,update_user);
							ps2.setTimestamp(13, ts);
							ps2.setString(14, rs.getString("SUPPLIER_CITY"));
							ps2.setString(15, rs.getString("SUPPLIER_ADDRESS1"));
							ps2.setString(16, rs.getString("PINCODE"));
							ps2.setInt(17, subOfficeId);
							ps2.setInt(18, slCode);
							ps2.setString(19, slPushDate);
							ps2.setInt(20, voucherNo);
							ps2.setString(21, voucherDate);
							ps2.execute();
							ps2.close();
							newSLCode++;
						}
					}
				}

				if(slType==11){
					
					System.out.println("select count(*) as cnt from PMS_CONT_REQUEST_REGN where OFFICE_ID="+cmbOffice_code+" and OLD_SL_CODE="+slCode+" and OLD_OFFICE_ID="+subOfficeId);
					ps=con.prepareStatement("select count(*) as cnt from PMS_CONT_REQUEST_REGN where OFFICE_ID=? and OLD_SL_CODE=? and OLD_OFFICE_ID=?");
					ps.setInt(1, cmbOffice_code);
					ps.setInt(2, slCode);
					ps.setInt(3, subOfficeId);
					System.out.println("subcode55555555"+slType);
					rs=ps.executeQuery();
					rs.next();
					System.out.println("subcode uiiiiiiinnnnnnnnnnnnnnnnnnnn"+slType+"<<<<"+rs.getInt("cnt"));
					if(rs.getInt("cnt")<1)
					{
						System.out.println("subcode uiiiiiiinnnnnnnnnnnnnnnnnnnn"+slType+""+rs.getInt("cnt"));
						ps.close();
						rs.close();
					
						System.out.println("select OFFICE_ID,REGN_YEAR,REGN_SLNO,to_char(DATE_OF_REGN,'dd/MM/yyyy') as DATE_OF_REGN,CONTRACTOR_NAME,ADDRESS, \n" +
							"PHONE,EMAIL,REGN_CLASS_ID,REGN_STATE_COVERAGE,to_char(REGN_VALID_UPTO,'dd/MM/yyyy') as REGN_VALID_UPTO,REGN_ALIAS_CODE,JURISDICTION from PMS_CONT_REQUEST_REGN where CONTRACTOR_ID="+slCode);
						ps=con.prepareStatement("select OFFICE_ID,REGN_YEAR,REGN_SLNO,to_char(DATE_OF_REGN,'dd/MM/yyyy') as DATE_OF_REGN,CONTRACTOR_NAME,ADDRESS, \n" +
							"PHONE,EMAIL,REGN_CLASS_ID,REGN_STATE_COVERAGE,to_char(REGN_VALID_UPTO,'dd/MM/yyyy') as REGN_VALID_UPTO,REGN_ALIAS_CODE,JURISDICTION from PMS_CONT_REQUEST_REGN where CONTRACTOR_ID=?");
						ps.setInt(1, slCode);
//						ps.setInt(2, voucherNo);
//						ps.setInt(3, cmbOffice_code);
						rs=ps.executeQuery();

						System.out.println("enter the Contractor at 222"+slType ); 
						if(rs.next())
						{
							System.out.println("enter the Contractor at 333"+slType ); 
							System.out.println("in insert Block");
							String sQL="insert into PMS_CONT_REQUEST_REGN(OFFICE_ID,REGN_YEAR,REGN_SLNO,DATE_OF_REGN,CONTRACTOR_ID,CONTRACTOR_NAME,ADDRESS, \n" +
							"PHONE,EMAIL,REGN_CLASS_ID,REGN_STATE_COVERAGE,REGN_VALID_UPTO,REGN_ALIAS_CODE,UPDATED_BY_USER_ID,UPDATED_DATE,JURISDICTION,OLD_OFFICE_ID, \n" +
							"OLD_SL_CODE,DATE_OF_TRANSFER,TPA_VOUCHER_NO,TPA_VOUCHER_DATE,status) values(?,?,?,to_date(?,'dd/MM/yyyy'),?,?,?,?,?,?,?,to_date(?,'dd/MM/yyyy'),?,?,?,?,?,?,to_date(?,'dd/MM/yyyy'),?,to_date(?,'dd/MM/yyyy'),'L')";

							 ps2=con.prepareStatement(sQL);
							ps2.setInt(1, cmbOffice_code);
							System.out.println("cmbOffice_code==>"+cmbOffice_code);
							ps2.setInt(2, rs.getInt("REGN_YEAR"));
							System.out.println("REGN_YEAR==>"+rs.getInt("REGN_YEAR"));
							ps2.setInt(3, rs.getInt("REGN_SLNO"));
							System.out.println("REGN_SLNO==>"+rs.getInt("REGN_SLNO"));
							ps2.setString(4, rs.getString("DATE_OF_REGN"));
							System.out.println("DATE_OF_REGN==>"+rs.getString("DATE_OF_REGN"));
							ps2.setInt(5, newSLCode);
							System.out.println("newSLCode==>"+newSLCode);
							ps2.setString(6, rs.getString("CONTRACTOR_NAME"));
							System.out.println("CONTRACTOR_NAME==>"+rs.getString("CONTRACTOR_NAME"));
							ps2.setString(7, rs.getString("ADDRESS"));
							System.out.println("ADDRESS==>"+rs.getString("ADDRESS"));
							ps2.setString(8, rs.getString("PHONE"));
							System.out.println("PHONE==>"+rs.getString("PHONE"));
							ps2.setString(9, rs.getString("EMAIL"));
							System.out.println("EMAIL==>"+rs.getString("EMAIL"));
							ps2.setInt(10, rs.getInt("REGN_CLASS_ID"));
							System.out.println("REGN_CLASS_ID==>"+rs.getInt("REGN_CLASS_ID"));
							ps2.setString(11, rs.getString("REGN_STATE_COVERAGE"));
							System.out.println("REGN_STATE_COVERAGE==>"+rs.getString("REGN_STATE_COVERAGE"));
							ps2.setString(12, rs.getString("REGN_VALID_UPTO"));
							System.out.println("REGN_VALID_UPTO==>"+rs.getString("REGN_VALID_UPTO"));
							ps2.setString(13, rs.getString("REGN_ALIAS_CODE"));
							System.out.println("REGN_ALIAS_CODE==>"+rs.getString("REGN_ALIAS_CODE"));
							ps2.setString(14,update_user);
							ps2.setTimestamp(15, ts);
							ps2.setString(16, rs.getString("JURISDICTION"));	
							System.out.println("JURISDICTION==>"+rs.getString("JURISDICTION"));
							ps2.setInt(17, subOfficeId);
							System.out.println("subOfficeId==>"+subOfficeId);
							ps2.setInt(18, slCode);
							System.out.println("slCode==>"+slCode);
							ps2.setString(19, slPushDate);
							System.out.println("slPushDate==>"+slPushDate);
							ps2.setInt(20, voucherNo);
							System.out.println("voucherNo==>"+voucherNo);
							ps2.setString(21, voucherDate);
							System.out.println("voucherDate==>"+voucherDate);
							ps2.execute();
							ps2.close();
							newSLCode++;
						}
					}
				}
				else if(slType==2){
					ps=con.prepareStatement("select count(*) as cnt from COM_FIRMS_SL_MST where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and OLD_SL_CODE=?");
					ps.setInt(1, cmbAcc_UnitCode);
					ps.setInt(2, cmbOffice_code);
					ps.setInt(3, slCode);

					rs=ps.executeQuery();

					rs.next();
					if(rs.getInt("cnt")<1)
					{
						ps.close();
						rs.close();
					
						ps=con.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FIRMS_ID,FIRMS_ALIAS_ID,FIRMS_NAME,FIRMS_ADDRESS,FIRMS_PHONE, \n" +
						"FIRMS_FAX,FIRMS_EMAIL_ID,to_char(DATE_OF_REGISTRATION,'dd/MM/yyyy') as DATE_OF_REGISTRATION,to_char(DATE_OF_LAST_SUPPLY,'dd/MM/yyyy') as DATE_OF_LAST_SUPPLY,UPDATED_BY_USER_ID,UPDATED_DATE,FIRMS_ADDRESS1,FIRMS_CITY,PINCODE from COM_FIRMS_SL_MST where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FIRMS_ID=?");
						ps.setInt(1, subUnitId);
						ps.setInt(2, subOfficeId);
						ps.setInt(3, slCode);
						rs=ps.executeQuery();


						if(rs.next())
						{
							String sQL="insert into COM_FIRMS_SL_MST(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FIRMS_ID,FIRMS_ALIAS_ID,FIRMS_NAME,FIRMS_ADDRESS,FIRMS_PHONE, \n" +
							"FIRMS_FAX,FIRMS_EMAIL_ID,DATE_OF_REGISTRATION,DATE_OF_LAST_SUPPLY,UPDATED_BY_USER_ID,UPDATED_DATE,FIRMS_ADDRESS1,FIRMS_CITY,PINCODE,OLD_OFFICE_ID, \n" +
							"OLD_SL_CODE,DATE_OF_TRANSFER,TPA_VOUCHER_NO,TPA_VOUCHER_DATE) values(?,?,?,?,?,?,?,?,?,to_date(?,'dd/MM/yyyy'),to_date(?,'dd/MM/yyyy'),?,?,?,?,?,?,?,to_date(?,'dd/MM/yyyy'),?,to_date(?,'dd/MM/yyyy'))";
							ps2=con.prepareStatement(sQL);
							System.out.println("cmbAcc_UnitCode==>"+cmbAcc_UnitCode);
							ps2.setInt(1, cmbAcc_UnitCode);
							System.out.println("cmbOffice_code==>"+cmbOffice_code);
							ps2.setInt(2, cmbOffice_code);
							System.out.println("newSLCode==>"+newSLCode);
							ps2.setInt(3, newSLCode);
							System.out.println("FIRMS_ALIAS_ID==>"+rs.getString("FIRMS_ALIAS_ID"));
							ps2.setString(4, rs.getString("FIRMS_ALIAS_ID"));
							System.out.println("FIRMS_NAME==>"+rs.getString("FIRMS_NAME"));
							ps2.setString(5, rs.getString("FIRMS_NAME"));
							System.out.println("FIRMS_ADDRESS==>"+rs.getString("FIRMS_ADDRESS"));
							ps2.setString(6, rs.getString("FIRMS_ADDRESS"));
							System.out.println("FIRMS_PHONE==>"+rs.getString("FIRMS_PHONE"));
							ps2.setString(7, rs.getString("FIRMS_PHONE"));
							System.out.println("FIRMS_FAX==>"+rs.getString("FIRMS_FAX"));
							ps2.setString(8, rs.getString("FIRMS_FAX"));
							System.out.println("FIRMS_EMAIL_ID==>"+rs.getString("FIRMS_EMAIL_ID"));
							ps2.setString(9, rs.getString("FIRMS_EMAIL_ID"));
							System.out.println("DATE_OF_REGISTRATION==>"+rs.getString("DATE_OF_REGISTRATION"));
							ps2.setString(10, rs.getString("DATE_OF_REGISTRATION"));
							System.out.println("DATE_OF_LAST_SUPPLY==>"+rs.getString("DATE_OF_LAST_SUPPLY"));
							ps2.setString(11, rs.getString("DATE_OF_LAST_SUPPLY"));
							System.out.println("update_user==>"+update_user);
							ps2.setString(12, update_user);
							System.out.println("ts==>"+ts);
							ps2.setTimestamp(13, ts);
							System.out.println("FIRMS_ADDRESS1==>"+rs.getString("FIRMS_ADDRESS1"));
							ps2.setString(14, rs.getString("FIRMS_ADDRESS1"));
							System.out.println("FIRMS_CITY==>"+rs.getString("FIRMS_CITY"));
							ps2.setString(15, rs.getString("FIRMS_CITY"));
							System.out.println("PINCODE==>"+rs.getString("PINCODE"));
							ps2.setInt(16, rs.getInt("PINCODE"));
							System.out.println("subOfficeId==>"+subOfficeId);
							ps2.setInt(17, subOfficeId);
							System.out.println("slCode==>"+slCode);
							ps2.setInt(18, slCode);
							System.out.println("slPushDate==>"+slPushDate);
							ps2.setString(19, slPushDate);
							System.out.println("voucherNo==>"+voucherNo);
							ps2.setInt(20, voucherNo);
							System.out.println("voucherDate==>"+voucherDate);
							ps2.setString(21, voucherDate);
							ps2.execute();
							ps2.close();
							newSLCode++;
						}
					}
				}else if(slType==10){
					ps=con.prepareStatement("select count(*) as cnt from PMS_MST_PROJECTS_VIEW where OFFICE_ID=? and OLD_SL_CODE=?");
					ps.setInt(1, cmbOffice_code);
					ps.setInt(2, slCode);

					rs=ps.executeQuery();
					rs.next();

					if(rs.getInt("cnt")<1)
					{
						ps.close();
						rs.close();
						ps=con.prepareStatement("select * from PMS_MST_PROJECTS_VIEW where OFFICE_ID=? and  PROJECT_ID=?");
						ps.setInt(1, subOfficeId);
						ps.setInt(2, slCode);
						rs=ps.executeQuery();


						if(rs.next())
						{
							System.out.println("in insert Block");


							String sQL="insert into PMS_MST_PROJECTS_VIEW(OFFICE_ID,PROJECT_ID,PROJECT_NAME,PROJECT_ALIAS_CODE,COMPONENT_NAME,SCH_AREA_LEVEL_ID,SCH_STATUS_ID, \n" +
							"SCH_TYPE_ID,SCH_SNO,SCH_NAME,COMP_SNO,COMP_DESC,PROJ_OR_COMP,UPDATED_BY_USER_ID,UPDATED_DATE,OLD_OFFICE_ID, \n" +
							"OLD_SL_CODE,DATE_OF_TRANSFER,TPA_VOUCHER_NO,TPA_VOUCHER_DATE,STATUS) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,to_date(?,'dd/MM/yyyy'),?,to_date(?,'dd/MM/yyyy'),'L')";

							ps2=con.prepareStatement(sQL);
							ps2.setInt(1, cmbOffice_code);
							ps2.setInt(2, newSLCode);
							ps2.setString(3, rs.getString("PROJECT_NAME"));
							ps2.setString(4, rs.getString("PROJECT_ALIAS_CODE"));
							ps2.setString(5, rs.getString("COMPONENT_NAME"));
							ps2.setInt(6, rs.getInt("SCH_AREA_LEVEL_ID"));
							ps2.setInt(7, rs.getInt("SCH_STATUS_ID"));
							ps2.setInt(8, rs.getInt("SCH_TYPE_ID"));
							ps2.setInt(9, rs.getInt("SCH_SNO"));
							ps2.setString(10, rs.getString("SCH_NAME"));
							ps2.setInt(11, rs.getInt("COMP_SNO"));
							ps2.setString(12, rs.getString("COMP_DESC"));
							ps2.setString(13, rs.getString("PROJ_OR_COMP"));
							ps2.setString(14,update_user);
							ps2.setTimestamp(15, ts);
							ps2.setInt(16, subOfficeId);
							ps2.setInt(17, slCode);
							ps2.setString(18, slPushDate);
							ps2.setInt(19, voucherNo);
							ps2.setString(20, voucherDate);
							ps2.execute();
							ps2.close();
							newSLCode++;
						}
					}
				}else if(slType==3){
					ps=con.prepareStatement("select count(*) as cnt from COM_MST_ASSETS_SL where ACCOUNTING_UNIT_ID=? and and ACCOUNTING_FOR_OFFICE_ID=? OLD_SL_CODE=?");
					ps.setInt(1, cmbAcc_UnitCode);
					ps.setInt(2, cmbOffice_code);
					ps.setInt(3, slCode);

					rs=ps.executeQuery();
					rs.next();

					if(rs.getInt("cnt")<1)
					{
						ps.close();
						rs.close();
						ps=con.prepareStatement("select * from COM_MST_ASSETS_SL where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and  ASSET_CODE=?");
						ps.setInt(1, subUnitId);
						ps.setInt(2, subOfficeId);
						ps.setInt(3, slCode);
						rs=ps.executeQuery();


						if(rs.next())
						{
							System.out.println("in insert Block");
							String sQL="insert into COM_MST_ASSETS_SL(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,to_char(AS_ON_DATE,'dd/MM/yyyy') as AS_ON_DATE,FINANCIAL_YEAR,ASSET_TYPE_CODE,ASSET_CLASS_CODE,ASSET_CODE, \n" +
							"ALIAS_CODE,OWNER_CODE,DONATING_AGENCY_NAME,ASSET_DESCRIPTION,YEAR_OF_PURCHASE,MONTH_OF_PURCHASE,FUEL_TYPE_USED,LOCATION_CODE_OF_VEHICLE_INUSE,ORIGINAL_COST,CURRENT_VALUE,REMARKS,UPDATED_BY_USER_ID,UPDATED_DATE,DEPRECIATION_RATE,OLD_OFFICE_ID, \n" +
							"OLD_SL_CODE,DATE_OF_TRANSFER,TPA_VOUCHER_NO,TPA_VOUCHER_DATE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,to_date(?,'dd/MM/yyyy'),?,to_date(?,'dd/MM/yyyy'))";

							ps2=con.prepareStatement(sQL);
							ps.setInt(1, cmbAcc_UnitCode);
							ps.setInt(2, cmbOffice_code);
							ps2.setString(3, rs.getString("AS_ON_DATE"));
							ps2.setString(4, rs.getString("FINANCIAL_YEAR"));
							ps2.setString(5, rs.getString("ASSET_TYPE_CODE"));
							ps2.setString(6, rs.getString("ASSET_CLASS_CODE"));
							ps2.setInt(7, newSLCode);
							ps2.setString(8, rs.getString("ALIAS_CODE"));
							ps2.setString(9, rs.getString("OWNER_CODE"));
							ps2.setString(10, rs.getString("DONATING_AGENCY_NAME"));
							ps2.setString(11, rs.getString("ASSET_DESCRIPTION"));
							ps2.setInt(12, rs.getInt("YEAR_OF_PURCHASE"));
							ps2.setInt(13, rs.getInt("MONTH_OF_PURCHASE"));
							ps2.setString(14, rs.getString("FUEL_TYPE_USED"));
							ps2.setInt(15, rs.getInt("LOCATION_CODE_OF_VEHICLE_INUSE"));
							ps2.setDouble(16, rs.getDouble("ORIGINAL_COST"));
							ps2.setDouble(17, rs.getDouble("CURRENT_VALUE"));
							ps2.setString(18, rs.getString("REMARKS"));
							ps2.setString(19,update_user);
							ps2.setTimestamp(20, ts);
							ps2.setDouble(21, rs.getDouble("DEPRECIATION_RATE"));
							ps2.setInt(22, subOfficeId);
							ps2.setInt(23, slCode);
							ps2.setString(24, slPushDate);
							ps2.setInt(25, voucherNo);
							ps2.setString(26, voucherDate);
							ps2.execute();
							ps2.close();
							newSLCode++;
						}
					}
				} /*else if (slType==9) {

				
				ps=con.prepareStatement("select count(*) as cnt from HRM_MST_OTHER_DEPT_OFFICES" select count(*) as cnt from PMS_CONT_REQUEST_REGN where OFFICE_ID=? and OLD_SL_CODE=?");
				ps.setInt(1, cmbOffice_code);
				ps.setInt(2, slCode);
				System.out.println("subcode55555555"+slType);
				rs=ps.executeQuery();
				rs.next();
				System.out.println("subcode uiiiiiiinnnnnnnnnnnnnnnnnnnn"+slType+"<<<<"+rs.getInt("cnt"));
			   
			   System.out.println("subcode uiiiiiiinnnnnnnnnnnnnnnnnnnn"+slType+""+rs.getInt("cnt"));
					ps.close();
					rs.close();
				
					ps=con.prepareStatement("select OFFICE_ID,REGN_YEAR,REGN_SLNO,to_char(DATE_OF_REGN,'dd/MM/yyyy') as DATE_OF_REGN,CONTRACTOR_NAME,ADDRESS, \n" +
						"PHONE,EMAIL,REGN_CLASS_ID,REGN_STATE_COVERAGE,to_char(REGN_VALID_UPTO,'dd/MM/yyyy') as REGN_VALID_UPTO,REGN_ALIAS_CODE,JURISDICTION from PMS_CONT_REQUEST_REGN where CONTRACTOR_ID=?");
					ps.setInt(1, slCode);
					rs=ps.executeQuery();

					System.out.println("enter the Contractor at 222"+slType ); 
					if(rs.next())
					{
						System.out.println("enter the Contractor at 333"+slType ); 
						System.out.println("in insert Block");
						String sQL="insert into PMS_CONT_REQUEST_REGN(OFFICE_ID,REGN_YEAR,REGN_SLNO,DATE_OF_REGN,CONTRACTOR_ID,CONTRACTOR_NAME,ADDRESS, \n" +
						"PHONE,EMAIL,REGN_CLASS_ID,REGN_STATE_COVERAGE,REGN_VALID_UPTO,REGN_ALIAS_CODE,UPDATED_BY_USER_ID,UPDATED_DATE,JURISDICTION,OLD_OFFICE_ID, \n" +
						"OLD_SL_CODE,DATE_OF_TRANSFER,TPA_VOUCHER_NO,TPA_VOUCHER_DATE,status) values(?,?,?,to_date(?,'dd/MM/yyyy'),?,?,?,?,?,?,?,to_date(?,'dd/MM/yyyy'),?,?,?,?,?,?,to_date(?,'dd/MM/yyyy'),?,to_date(?,'dd/MM/yyyy'),'L')";

						ps2=con.prepareStatement(sQL);
						ps2.setInt(1, cmbOffice_code);
						ps2.setInt(2, rs.getInt("REGN_YEAR"));
						ps2.setInt(3, rs.getInt("REGN_SLNO"));
						ps2.setString(4, rs.getString("DATE_OF_REGN"));
						ps2.setInt(5, newSLCode);
						ps2.setString(6, rs.getString("CONTRACTOR_NAME"));
						ps2.setString(7, rs.getString("ADDRESS"));
						ps2.setString(8, rs.getString("PHONE"));
						ps2.setString(9, rs.getString("EMAIL"));
						ps2.setInt(10, rs.getInt("REGN_CLASS_ID"));
						ps2.setString(11, rs.getString("REGN_STATE_COVERAGE"));
						ps2.setString(12, rs.getString("REGN_VALID_UPTO"));
						ps2.setString(13, rs.getString("REGN_ALIAS_CODE"));
						ps2.setString(14,update_user);
						ps2.setTimestamp(15, ts);
						ps2.setString(16, rs.getString("JURISDICTION"));				
						ps2.setInt(17, subOfficeId);
						ps2.setInt(18, slCode);
						ps2.setString(19, slPushDate);
						ps2.setInt(20, voucherNo);
						ps2.setString(21, voucherDate);
						ps2.execute();
						ps2.close();
						newSLCode++;
					}
				
			
			}*/

		}

		 sendMessage(response,"Subledgers Moved Successfully ","ok");

	}catch(Exception e){System.out.println(e);e.printStackTrace();
	try {
		con.rollback();
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	}
	try{
		con.commit();
	}catch(Exception e){System.out.println(e);}
}
 private void sendMessage(HttpServletResponse response,String msg,String bType)
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
 }
}
