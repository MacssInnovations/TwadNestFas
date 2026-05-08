package Servlets.FAS.FAS1.BillScrutiny.servlets;

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
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Servlets.FAS.FAS1.CivilBills.servlets.SL_TYPE_CODE_NAME;

/**
 * Servlet implementation class Bill_Scrutiny_Details
 */
public class Bill_Scrutiny_Details extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252"; 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Bill_Scrutiny_Details() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		


		
		Connection connection=null;
        Statement statement=null;
        ResultSet result=null,rs1=null,rss3=null;
      //  PreparedStatement ps1=null,ps3=null;
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
        int assetmajor=0;
	   	String financial_year = null;

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
        int up=0;    
        String userid=(String)session.getAttribute("UserId");
        System.out.println("Session id is:"+userid);
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
        Servlets.Security.classes.UserProfile empProfile = (Servlets.Security.classes.UserProfile) session
		.getAttribute("UserProfile");
        int empid = empProfile.getEmployeeId();
		String empName = empProfile.getEmployeeName();
		
        
        try{
        	  int ched=0,unched=0,up_billtable=0;
           if(strCommand.equalsIgnoreCase("addRecords")){
        	 
        	   connection.setAutoCommit(false);

   			int AccUnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
   			int AccOfficeCode = Integer.parseInt(request.getParameter("cmbOffice_code"));
   			int CashBookYear = Integer.parseInt(request.getParameter("cboCashBook_Year"));
   			int CashBookMonth = Integer.parseInt(request.getParameter("cboCashBook_Month"));
   			
   			int BillNo = Integer.parseInt(request.getParameter("cboBillNo"));
   			String withorwithout =request.getParameter("withorwithout");
   			String rdoScrutinyDone =request.getParameter("rdoScrutinyDone");
   			int DeductedAmount = Integer.parseInt(request.getParameter("txtDeductedAmount"));
			int NetAmount = Integer.parseInt(request.getParameter("txtNetAmount"));
   			
   			java.sql.Date BillScrunityDate = null;
			java.util.GregorianCalendar c3;
			String[] sd3 = request.getParameter("txtScrutinyDate").split("/");
			c3 = new java.util.GregorianCalendar(Integer.parseInt(sd3[2]),
					Integer.parseInt(sd3[1]) - 1, Integer.parseInt(sd3[0]));
			java.util.Date d3 = c3.getTime();
			BillScrunityDate = new Date(d3.getTime());
   			
			
			
			java.sql.Date BillDate = null;
			java.util.GregorianCalendar c4;
			String[] sd4 = request.getParameter("txtBillDate").split("/");
			c4 = new java.util.GregorianCalendar(Integer.parseInt(sd4[2]),
					Integer.parseInt(sd4[1]) - 1, Integer.parseInt(sd4[0]));
			java.util.Date d4 = c4.getTime();
			BillDate = new Date(d4.getTime());
			String sub_q = "",sub_main="";
			
			

			if (Integer.parseInt(sd4[2]) > 2014) {
				if (Integer.parseInt(sd4[2]) == 2015 && Integer.parseInt(sd4[1]) <= 3) {
					sub_q = " FAS_BILL_REGISTER_MASTER "; 
					 sub_main=" Fas_Bill_Register_Master M, "+
								" 	  Fas_Bill_Register_Transaction T ";
				} else {
					 sub_q = " FAS_BILL_REGISTER_MASTERNEW "; 
					 sub_main=" Fas_Bill_Register_MasterNEW M, "+
					" 	  Fas_Bill_Register_Transactionw T ";
				}
			} else {
				sub_q = " FAS_BILL_REGISTER_MASTER "; 
				 sub_main=" Fas_Bill_Register_Master M, "+
							" 	  Fas_Bill_Register_Transaction T ";
			}
			/*if(Integer.parseInt(sd4[2])>2014 && Integer.parseInt(sd4[1])>3)
			{
				 sub_q = " FAS_BILL_REGISTER_MASTERNEW "; 
				 sub_main=" Fas_Bill_Register_MasterNEW M, "+
				" 	  Fas_Bill_Register_Transactionw T ";
			}else{
				sub_q = " FAS_BILL_REGISTER_MASTER "; 
				 sub_main=" Fas_Bill_Register_Master M, "+
							" 	  Fas_Bill_Register_Transaction T ";
			}*/
        	//String[] sno=request.getParameterValues("sno");
        	//String[] cmbAcc_UnitCode=request.getParameterValues("cmbAcc_UnitCode");
        //	String[] cmbOffice_code=request.getParameterValues("cmbOffice_code");
        	String[] checkdes1=request.getParameterValues("checkdes1");
        	String[] manda1=request.getParameterValues("manda1");
        	String[] noappp1=request.getParameterValues("noappp1");
        	String[] checkcodee=request.getParameterValues("checkcodee");
        	 String verify_select_status[]=request.getParameterValues("verify_select_status");
      
        	
        	int ss=manda1.length;
        	int cc=0;
          //  System.out.println("total.length =="+ss);
        	for(int ii=0;ii<ss;ii++){
        	//	System.out.println("------------------"+ii+"-----------------");    
                 
        		//  int cmbAcc_UnitCode1=Integer.parseInt(cmbAcc_UnitCode[ii]);
                //  int cmbOffice_code1=Integer.parseInt(cmbOffice_code[ii]);
                  int checkcodee1=Integer.parseInt(checkcodee[ii]);
                  String checkdes11=checkdes1[ii];
                  String manda11=manda1[ii];   	
                  String noappp11=noappp1[ii];
                 // System.out.println("checkcodee1 "+checkcodee1);
                  try 
                  {      			     			   
		//	String sqlselect="select 'x' from FAS_BILL_SCRUTINY_DETAILS where ACCOUNTING_UNIT_ID="+AccUnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+AccOfficeCode+" and CHECK_CODE="+checkcodee1+" and BILL_NO="+BillNo;        		            			   
			String sqlselect="SELECT 'x' " +
					" FROM FAS_BILL_SCRUTINY_DETAILS sc, " +sub_q
					+" re " +
					" WHERE SC.ACCOUNTING_FOR_OFFICE_ID =RE.ACCOUNTING_UNIT_OFFICE_ID " +
					" AND SC.ACCOUNTING_UNIT_ID         =RE.ACCOUNTING_UNIT_ID " +
					" AND SC.BILL_NO                    = RE.BILL_NO " +
					" AND sc.ACCOUNTING_UNIT_ID         = " +AccUnitCode+
					" AND SC.ACCOUNTING_FOR_OFFICE_ID   = " +AccOfficeCode+
					" AND SC.CHECK_CODE                 = " +checkcodee1+
					" AND SC.BILL_NO                    = " +BillNo+
					" AND SC.BILL_SCRUTINY_DATE         =RE.BILL_SCRUTINY_DATE " +
					" AND RE.BILL_DATE                  =?";
                	  PreparedStatement ps4 = connection.prepareStatement(sqlselect); 
			ps4.setDate(1, BillDate);System.out.println("BillDate   >>> "+BillDate);
			rs1=ps4.executeQuery();
			if(rs1.next()){
				//System.out.println("inside if ");
				connection.rollback();
				sendMessage(response,"2/Already Having Data","ok");  
			}else{
				 try 
                 { 
			    //System.out.println("indise else ");  			   
					 if((verify_select_status[ii].equals("CHECKED")))
                     {   			   
						String sqlinsert="insert into FAS_BILL_SCRUTINY_DETAILS(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CHECK_CODE,BILL_NO,UPDATED_BY_USERID,UPDATED_DATE,BILL_SCRUTINY_DATE) values(?,?,?,?,?,?,?)";        		            			   
						PreparedStatement ps2 = connection.prepareStatement(sqlinsert); 
						ps2.setInt(1,AccUnitCode);
						ps2.setInt(2,AccOfficeCode);
						ps2.setInt(3,checkcodee1);
						ps2.setInt(4,BillNo);
						ps2.setString(5,userid);
						ps2.setTimestamp(6,ts);
						ps2.setDate(7, BillScrunityDate);	
						try{
						up=ps2.executeUpdate();
						}catch(Exception e){
							System.out.println("1 part");
							e.printStackTrace();
						}
						System.out.println("up  "+up+"    withorwithout  >> "+withorwithout);
						if(up>0)
						{
							if(withorwithout.equals("WithoutSanction"))
							{
								ps2 = connection.prepareStatement("update FAS_BILL_REGISTERNEW set BILL_SCRUTINY_DONE=?,BILL_SCRUTINY_BY=?," +
										" BILL_SCRUTINY_DATE=?,UPDATED_BY_USERID=?,UPDATED_DATE=?,DEDUCTED_AMOUNT=?,NET_AMOUNT=? where ACCOUNTING_UNIT_ID=?" +
										" and ACCOUNTING_UNIT_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and BILL_NO=?");
							}
							else if(withorwithout.equals("WithoutSanctionGPF")){
								ps2 = connection.prepareStatement("update "+sub_q+" set BILL_SCRUTINY_DONE=?,BILL_SCRUTINY_BY=?," +
										" BILL_SCRUTINY_DATE=?,UPDATED_BY_USERID=?,UPDATED_DATE=?,DEDUCTED_AMOUNT=?,NET_AMOUNT=? where ACCOUNTING_UNIT_ID=?" +
										" and ACCOUNTING_UNIT_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and BILL_NO=? and BILL_TYPE='WOSP'");
							}
							else
							{
								ps2 = connection.prepareStatement("update  "+sub_q+"  set BILL_SCRUTINY_DONE=?,BILL_SCRUTINY_BY=?," +
										" BILL_SCRUTINY_DATE=?,UPDATED_BY_USERID=?,UPDATED_DATE=?,DEDUCTED_AMOUNT=?,NET_AMOUNT=? where ACCOUNTING_UNIT_ID=?" +
										" and ACCOUNTING_UNIT_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and BILL_NO=? and BILL_TYPE='WSP'");
							}
							
							ps2.setString(1, rdoScrutinyDone);
							ps2.setInt(2, empid);
							ps2.setDate(3, BillScrunityDate);	
							ps2.setInt(4, empid);
							ps2.setTimestamp(5, ts);
							ps2.setInt(6, DeductedAmount);
							ps2.setInt(7, NetAmount);
							ps2.setInt(8, AccUnitCode);
							ps2.setInt(9, AccOfficeCode);
							ps2.setInt(10, CashBookYear);
							ps2.setInt(11, CashBookMonth);
							ps2.setInt(12, BillNo);				
							try{up_billtable=ps2.executeUpdate();}catch(Exception e){
								e.printStackTrace();
								System.out.println("2 part");
							}
							
							
						}
						
						if(up_billtable>0)
						{
							
							ps2 = connection.prepareStatement("update FAS_MEMO_OF_PAYMENT_MST set BILL_SCRUTINY='Y' where ACCOUNTING_UNIT_ID=" +AccUnitCode+" and " +
									"ACCOUNTING_FOR_OFFICE_ID="+AccOfficeCode+" and CASHBOOK_YEAR="+CashBookYear+" and CASHBOOK_MONTH="+CashBookMonth+" and BILL_NO="+BillNo+" and STATUS='L'");
						try{
						  ched=ps2.executeUpdate();
						}catch(Exception e){
							System.out.println("3 part");
							e.printStackTrace();
						}
						}
						else
						{
							ched=0;
						}
                     }else{
                  	  
                  	   unched++;  
                     }
				} catch (Exception e) {
				System.out.println("exception......in update calll send message "+e);
				}
							}
				
				
				} catch (Exception e) {
				System.out.println("exception......in update calll send message "+e);
				
				
				}
        		            		  
 				      
                }
        	
        	 int fina=ss-ched;
          
              if(ched>0){
				connection.commit();
				sendMessage(response,"1/Records inserted successfully ","ok");
			}
			else
			{
				
				connection.rollback();	
				sendMessage(response,"2/Record Not Insert  ","ok");
			}
  
        	}
        	
        }catch(Exception ss){
        	System.out.println("SQL exception   "+ss);
        	
        }
		
		
	}
	private void sendMessage(HttpServletResponse response,String msg,String bType)
	 {
	 	try
	 	{
	 		String url="org/Library/jsps/MessengerOkBackNEW.jsp?message=" + msg + "&button=" + bType;
	 		response.sendRedirect(url);
	 	}
	 	catch(Exception e)
	 	{
	 		System.out.println("error in messenger"+e);
	 	}
  }
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// TODO Auto-generated method stub
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
		int memo=0;
		if (strCommand.equalsIgnoreCase("gett")) {			
			xml = xml + "<response><command>gett</command>";

			String cboAcc_UnitCode1 = request.getParameter("cmbAcc_UnitCode");
			int cboAcc_UnitCode = Integer.parseInt(cboAcc_UnitCode1);

			String cboOffice_code1 = request.getParameter("cmbOffice_code");
			int cboOffice_code = Integer.parseInt(cboOffice_code1);

			String cboCashBook_Year1 = request.getParameter("cboCashBook_Year");
			int cboCashBook_Year = Integer.parseInt(cboCashBook_Year1);

			String cboCashBook_Month1 = request.getParameter("cboCashBook_Month");
			int cboCashBook_Month = Integer.parseInt(cboCashBook_Month1);
			int count=0;
			String sub_q = "",sub_main="";
		/*	if(cboCashBook_Year>2014 && cboCashBook_Month>3)
			{
				 sub_q = " FAS_BILL_REGISTER_MASTERNEW "; 
				 sub_main=" Fas_Bill_Register_MasterNEW M, "+
				" 	  Fas_Bill_Register_Transactionw T ";
			}else{
				sub_q = " FAS_BILL_REGISTER_MASTER "; 
				 sub_main=" Fas_Bill_Register_Master M, "+
							" 	  Fas_Bill_Register_Transaction T ";
			}*/
			if(cboCashBook_Year>2014){
			if (cboCashBook_Year == 2015 && cboCashBook_Month <= 3) {
				sub_q = " FAS_BILL_REGISTER_MASTER "; 
				 sub_main=" Fas_Bill_Register_Master M, "+
							" 	  Fas_Bill_Register_Transaction T ";
			}else{
				 sub_q = " FAS_BILL_REGISTER_MASTERNEW "; 
				 sub_main=" Fas_Bill_Register_MasterNEW M, "+
				" 	  Fas_Bill_Register_Transactionw T ";
			}}else{
				sub_q = " FAS_BILL_REGISTER_MASTER "; 
				 sub_main=" Fas_Bill_Register_Master M, "+
							" 	  Fas_Bill_Register_Transaction T ";
			}
			try {
				
				String su = "Select Bill_No From fas_memo_of_payment_mst Where Accounting_Unit_Id     ="+cboAcc_UnitCode+" And accounting_for_office_id="+cboOffice_code+" And " +
						" Cashbook_Year            ="+cboCashBook_Year+" And Cashbook_Month           ="+cboCashBook_Month+" and (bill_scrutiny is  null or bill_scrutiny = '')  " ;
							//	" AND (BILL_SCRUTINY_DONE!      ='Y' OR BILL_SCRUTINY_DONE       IS NULL OR BILL_SCRUTINY_DONE        ='N')";				
				ps = connection.prepareStatement(su);
				System.out.println("su::::"+su);
				rs = ps.executeQuery();
				
				while(rs.next())
				{
					int bill_no_in_memo=rs.getInt("Bill_No");
					System.out.println("bill_no_in_memo:"+bill_no_in_memo);
					
					try {
						String su1 = "SELECT Bill_No FROM "+sub_q+" WHERE Accounting_Unit_Id     ="+cboAcc_UnitCode+" AND ACCOUNTING_UNIT_OFFICE_ID= "+cboOffice_code+
									 " AND Cashbook_Year ="+cboCashBook_Year+" AND Cashbook_Month           ="+cboCashBook_Month+" and STATUS='L' and Bill_No="+bill_no_in_memo+" and (BILL_TYPE                ='WSP' or BILL_TYPE       is null)  and MEMO_ENTRY='Y' and BILL_SCRUTINY_DONE is null"; 
											
						System.out.println("su1::with comp/sanc::"+su1);
						ps1 = connection.prepareStatement(su1);
						rs1 = ps1.executeQuery();						
						if(rs1.next()) {
						
							xml = xml + "<billNo>"+ rs1.getInt("BILL_NO")+ "</billNo>";
							xml = xml + "<with_without>"+"WithSanction" + "</with_without>";
							memo=memo+1;
						System.out.println("memo 11 "+memo);
						}
						else
						{
							
							
							
							String su2 = "SELECT Bill_No FROM  "+sub_q+"  WHERE Accounting_Unit_Id     ="+cboAcc_UnitCode+" AND ACCOUNTING_UNIT_OFFICE_ID= "+cboOffice_code+
							 " AND Cashbook_Year ="+cboCashBook_Year+" AND Cashbook_Month           ="+cboCashBook_Month+" and STATUS='L' " +
							 		"and Bill_No="+bill_no_in_memo+
							 		" and (BILL_TYPE                ='WOSP' ) and MEMO_ENTRY='Y' and (BILL_SCRUTINY_DONE is null or BILL_SCRUTINY_DONE = '')"; 
									
				System.out.println("su1::without comp/sanc::"+su2);
					PreparedStatement ps22 = connection.prepareStatement(su2);
						ResultSet rs22 = ps22.executeQuery();
						if(rs22.next()) {
						//	memo++;
							xml = xml + "<billNo>"+ rs22.getInt("BILL_NO")+ "</billNo>";
							xml = xml + "<with_without>"+"WithoutSanctionGPF" + "</with_without>";
							memo=memo+1;
							System.out.println("memo 22 "+memo);
						}else{
							String su3 = "SELECT Bill_No FROM FAS_BILL_REGISTERNEW WHERE Accounting_Unit_Id     ="+cboAcc_UnitCode+" AND ACCOUNTING_UNIT_OFFICE_ID= "+cboOffice_code+
							 " AND Cashbook_Year ="+cboCashBook_Year+" AND Cashbook_Month           ="+cboCashBook_Month+" and STATUS='L' and Bill_No="+bill_no_in_memo+" and MEMO_ENTRY='Y' and BILL_SCRUTINY_DONE is null"; 
									
				System.out.println("su1::without comp/sanc::"+su3);
					PreparedStatement ps33 = connection.prepareStatement(su3);
						ResultSet rs33 = ps33.executeQuery();
						if(rs33.next()) {
							//memo++;
							xml = xml + "<billNo>"+ rs33.getInt("BILL_NO")+ "</billNo>";
							xml = xml + "<with_without>"+"WithoutSanction" + "</with_without>";
							memo=memo+1;
							System.out.println("memo 33 "+memo);
						}
						}
				
				
						}
						System.out.println("memo Final **  "+memo);
						/*if(memo == 0)
						{
							xml = xml + "<memo>nodata</memo>";
						}
						else
						{
							xml = xml + "<memo>yes</memo>";
						}*/
					
						ps1.close();
						rs1.close();
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					count++;
				}
				if(memo==0){
					System.out.println("ooo Final **  "+memo);
					xml=xml+"<memo>nodata</memo>";
				}else if(memo != 0){
					System.out.println("<> ooo Final **  "+memo);
					xml=xml+"<memo>yes</memo>";
				}
				
				if(count>0)
				{
					xml = xml + "<flag>success</flag>";
				}
				else
				{
					xml = xml + "<flag>failure</flag>";
				}
			ps.close();
				rs.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}else if (strCommand.equalsIgnoreCase("loadDetails")) {

			xml = xml + "<response><command>loadDetails</command>";
			
//			int AccUnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
//			int AccOfficeCode = Integer.parseInt(request.getParameter("cmbOffice_code"));
			
			
			String mandate=null;
			String noapp=null;
			String checkdesc=null;
			int checkcode=0;
			//int subLedgerTypeCode=0;
			//int subLedgerCode=0;
			int ii=0;
			try {
				String su1 = "SELECT CHECK_CODE,CHECK_DESC,"+
				  " mandate, "+
				  " not_applicable "+
				" FROM FAS_COM_BILL_SCRUTINY_CHKLST "+
				//" WHERE ACCOUNTING_UNIT_ID    = "+AccUnitCode+
			//	" AND ACCOUNTING_FOR_OFFICE_ID= "+AccOfficeCode+
				" WHERE STATUS                  ='L'	";		
				ps1 = connection.prepareStatement(su1);
				results = ps1.executeQuery();	
				//System.out.println("su1--->"+su1);
				if(results.next())
				{
					xml = xml + "<flag>success</flag>";
			try {
				String su = "SELECT CHECK_CODE,check_desc,"+
				  " mandate, "+
				  " not_applicable "+
				" FROM FAS_COM_BILL_SCRUTINY_CHKLST "+
				" WHERE STATUS                  ='L'	";				
				ps = connection.prepareStatement(su);
				//System.out.println("sql------->"+su);
				rs = ps.executeQuery();			
				int cc=0;
				while (rs.next()) {
					
					
					xml = xml + "<slno1>"+ (ii++)+ "</slno1>";
					
					xml = xml + "<mandate>"+ rs.getString("mandate")+ "</mandate>";
					xml = xml + "<noapp>"+ rs.getString("not_applicable")+ "</noapp>";
					xml = xml + "<checkdesc>"+ rs.getString("check_desc")+ "</checkdesc>";
					xml = xml + "<checkcode>"+ rs.getInt("CHECK_CODE")+ "</checkcode>";
					cc++;
				}
				xml = xml + "<slno>"+ cc+ "</slno>";
				ps.close();
				rs.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

				}
				else{
					xml = xml + "<flag>NoData</flag>";
				}
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
		}
		else if (strCommand.equalsIgnoreCase("getDetails")) {

			xml = xml + "<response><command>getDetails</command>";
			
			int AccUnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
			int AccOfficeCode = Integer.parseInt(request.getParameter("cmbOffice_code"));
			int CashBookYear = Integer.parseInt(request.getParameter("cboCashBook_Year"));
			int CashBookMonth = Integer.parseInt(request.getParameter("cboCashBook_Month"));
			int BillNo = Integer.parseInt(request.getParameter("cboBillNo"));
		//	String withorwithout =request.getParameter("withorwithout");
			
			String billDate=null;
			String proceedingDate=null;
			int AccHeadCode=0;
			int subLedgerTypeCode=0;
			int subLedgerCode=0;
			String sub_q = "",sub_main="";
			/*if(CashBookYear>2014 && CashBookMonth>3)
			{
				 sub_q = " FAS_BILL_REGISTER_MASTERNEW "; 
				 sub_main=" Fas_Bill_Register_MasterNEW M, "+
				" 	  Fas_Bill_Register_Transactionw T ";
			}else{
				sub_q = " FAS_BILL_REGISTER_MASTER "; 
				 sub_main=" Fas_Bill_Register_Master M, "+
							" 	  Fas_Bill_Register_Transaction T ";
			}*/
			if(CashBookYear>2014){
				if (CashBookYear == 2015 && CashBookMonth <= 3) {
					sub_q = " FAS_BILL_REGISTER_MASTER "; 
					 sub_main=" Fas_Bill_Register_Master M, "+
								" 	  Fas_Bill_Register_Transaction T ";
				}else{
					 sub_q = " FAS_BILL_REGISTER_MASTERNEW "; 
					 sub_main=" Fas_Bill_Register_MasterNEW M, "+
					" 	  Fas_Bill_Register_Transactionw T ";
				}
			}else{
					sub_q = " FAS_BILL_REGISTER_MASTER "; 
					 sub_main=" Fas_Bill_Register_Master M, "+
								" 	  Fas_Bill_Register_Transaction T ";
				}
			try {
				
			try {
				String su = "SELECT M.Bill_Date, "+
					"  t.SL_NO, "+
					" t.AMOUNT,"+
			" (select sum(tt.AMOUNT) from FAS_MEMO_OF_PAYMENT_TRN tt where"+
			"				  tt.Accounting_Unit_Id      ="+AccUnitCode+
			"				AND  tt.ACCOUNTING_FOR_OFFICE_ID= "+AccOfficeCode+
			"				AND  tt.CASHBOOK_YEAR           ="+CashBookYear+
			"				AND  TT.CASHBOOK_MONTH          ="+CashBookMonth+
			"			AND  tt.BILL_NO                 ="+BillNo+" ) as AMOUNT_cp, "
					+ "(SELECT Case When "
					+ "Bill_Type='WOSP' Then 'WithoutSanctionGPF' When Bill_Type='WSP' Then "
					+ " 'WithSanction'  else '--' end as Bill_Type FROM FAS_BILL_REGISTER_MASTERNEW "
					+ " WHERE Accounting_Unit_Id     ="+AccUnitCode
					+ " AND ACCOUNTING_UNIT_OFFICE_ID= "+AccOfficeCode
					+ " AND Cashbook_Year            ="+CashBookYear
					+ " AND Cashbook_Month           ="+CashBookMonth
					+ " AND STATUS                   ='L'"
					+ " AND Bill_No                  ="+BillNo
					+ " And Memo_Entry               ='Y' "
					+ " And Bill_Scrutiny_Done      Is Null) "
					+ " as bill_type,"
					+ "m.amount as sancamt, "+
					" m.SANCTION_PROCEEDING_NO, "+
					" 	  M.SANCTION_PROCEEDING_DATE, "+
					" 	  t.ACCOUNT_HEAD_CODE, "+
					" 	  (SELECT H.Account_Head_Desc "+
					" 	  FROM Com_Mst_Account_Heads h "+
					" 	  WHERE H.Account_Head_Code=t.ACCOUNT_HEAD_CODE "+
					" 	  )AS head_desc, "+
					" 	  t.SUB_LEDGER_TYPE_CODE, "+
					" 	  (SELECT S.Sub_Ledger_Type_Desc "+
					" 	  FROM com_mst_sl_types s "+
					" 	  WHERE S.Sub_Ledger_Type_Code=t.SUB_LEDGER_TYPE_CODE "+
					" 	  )AS type_desc, "+
					" 	  t.SUB_LEDGER_CODE, "+
					" (SELECT PAYEE_CODENAME FROM PAYEE_TYPE_CODE_NAME_VIEW " +
					" WHERE PAYEE_TYPE= t.SUB_LEDGER_TYPE_CODE and PAYEE_CODE=t.SUB_LEDGER_CODE "+
					" 	  )AS code_desc, "+
					" 	  m.Remarks, "+
					" 		  m.BILL_MAJOR_TYPE_CODE, "+
					" 	  (SELECT bill_major_type_desc "+
					" 	  FROM FAS_BILL_MAJOR_TYPES j "+
					" 	  WHERE j.bill_major_type_code=m.BILL_MAJOR_TYPE_CODE "+
					" 	  )AS major_desc,t.cr_dr_indicator "+
					" 	FROM FAS_MEMO_OF_PAYMENT_MST m,FAS_MEMO_OF_PAYMENT_TRN t "+
					" 	WHERE M.Accounting_Unit_Id     =T.Accounting_Unit_Id "+
					" 	AND M.ACCOUNTING_FOR_OFFICE_ID=T.ACCOUNTING_FOR_OFFICE_ID "+
					" 	AND M.Cashbook_Year            =T.Cashbook_Year "+
					" 	AND M.Cashbook_Month           =T.Cashbook_Month "+
					" 	AND m.BILL_NO                  =t.BILL_NO "+
					" 	and M.Accounting_Unit_Id       = "+AccUnitCode+
					" 	AND M.ACCOUNTING_FOR_OFFICE_ID= "+AccOfficeCode+
					" 	AND M.Cashbook_Year            = "+CashBookYear+
					" 	AND M.Cashbook_Month           = "+CashBookMonth+
					" 	AND m.BILL_NO                  ="+BillNo;
				System.out.println("su::::"+su);
				ps = connection.prepareStatement(su);
				
				rs = ps.executeQuery();			
				
				while (rs.next()) {
					xml = xml + "<flag>success</flag>";
					Date billDate1=rs.getDate("Bill_Date");
					Date proceedingDate1=rs.getDate("SANCTION_PROCEEDING_DATE");
					
					String Stringdate = billDate1.toString();
					String Stringdate1 = proceedingDate1.toString();
					
					String[] ddd = Stringdate.split("-");
					String[] ddd1 = Stringdate1.split("-");
										
					int day =Integer.parseInt(ddd[2]);
					int month =Integer.parseInt(ddd[1]);
					int year = Integer.parseInt(ddd[0]);
					
					int day1 =Integer.parseInt(ddd1[2]);
					int month1 =Integer.parseInt(ddd1[1]);
					int year1 = Integer.parseInt(ddd1[0]);
					
					if(month>=10)
			        {
						billDate=(day+"/"+month+"/"+year);
			        }
			        else
			        {
			        	billDate=(day+"/0"+month+"/"+year);	
			        }
					
					if(month1>=10)
			        {
						proceedingDate=(day1+"/"+month1+"/"+year1);
			        }
			        else
			        {
			        	proceedingDate=(day1+"/0"+month1+"/"+year1);	
			        }
					AccHeadCode=rs.getInt("ACCOUNT_HEAD_CODE");
					subLedgerTypeCode=rs.getInt("SUB_LEDGER_TYPE_CODE");
					subLedgerCode=rs.getInt("SUB_LEDGER_CODE");
					
					System.out.println("bill_type"+rs.getString("bill_type"));
					
					xml = xml + "<billDate>"+ billDate+ "</billDate>";
					xml = xml + "<AMOUNT_cp>"+ rs.getInt("AMOUNT_cp")+ "</AMOUNT_cp>";
					xml = xml + "<billAmount>"+ rs.getInt("AMOUNT")+ "</billAmount>";
					xml = xml + "<bill_type>"+ rs.getString("bill_type")+ "</bill_type>";
					xml = xml + "<totalsancamt>"+ rs.getInt("sancamt")+ "</totalsancamt>";
					xml = xml + "<proceedingNo>"+ rs.getString("SANCTION_PROCEEDING_NO")+ "</proceedingNo>";
					xml = xml + "<proceedingDate>"+ proceedingDate+ "</proceedingDate>";
					xml = xml + "<AccHeadCode>"+ AccHeadCode+ "</AccHeadCode>";
					xml = xml + "<subLedgerTypeCode>"+ subLedgerTypeCode+ "</subLedgerTypeCode>";
					xml = xml + "<subLedgerTypeCodeDesc>"+ rs.getString("type_desc")+ "</subLedgerTypeCodeDesc>";
					xml = xml + "<subLedgerCode>"+ subLedgerCode+ "</subLedgerCode>";
					xml = xml + "<code_desc>"+rs.getString("code_desc")+"</code_desc>";
					xml = xml + "<remarks>"+ rs.getString("REMARKS")+ "</remarks>";
					xml = xml + "<AccHeadCodeDesc>"+ rs.getString("head_desc")+ "</AccHeadCodeDesc>";
					
					xml = xml + "<major_desc>"+ rs.getString("major_desc")+ "</major_desc>";
					xml = xml + "<indicator>"+ rs.getString("cr_dr_indicator")+ "</indicator>";
					/*
					System.out.println(subLedgerTypeCode);
					System.out.println(subLedgerCode);
					try {
						
					SL_TYPE_CODE_NAME slTypeCode= new SL_TYPE_CODE_NAME();
					ResultSet rs_get = slTypeCode.getResult(AccUnitCode, AccOfficeCode, subLedgerTypeCode, subLedgerCode);
					while(rs_get.next())
					{
						System.out.println("ttt:");
					int ii = rs_get.getInt(1);
					String ss = rs_get.getString(2);
					
					xml = xml + "<subLedgerCodeDesc1>"
					+ ss
					+ "</subLedgerCodeDesc1>";
					
					}
								
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}	*/
					
				}

				ps.close();
				rs.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			
				
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
		}
		/*else if (strCommand.equalsIgnoreCase("saveFunc")) {
			xml = xml + "<response><command>saveFunc</command>";
			
			int AccUnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
			int AccOfficeCode = Integer.parseInt(request.getParameter("cmbOffice_code"));
			int CashBookYear = Integer.parseInt(request.getParameter("cboCashBook_Year"));
			int CashBookMonth = Integer.parseInt(request.getParameter("cboCashBook_Month"));
			int BillNo = Integer.parseInt(request.getParameter("cboBillNo"));
			System.out.println("BillNo---------BillNo--------->"+BillNo);
			int DeductedAmount = Integer.parseInt(request.getParameter("DeductedAmount"));
			int NetAmount = Integer.parseInt(request.getParameter("NetAmount"));
			
			String rdoScrutinyDone=request.getParameter("BillScrunityDone");
			System.out.println("rdoScrutinyDone------------rdoScrutinyDone--------->>>"+rdoScrutinyDone);
			
			java.sql.Date BillScrunityDate = null;
			java.util.GregorianCalendar c3;
			String[] sd3 = request.getParameter("BillScrunityDate").split("/");
			c3 = new java.util.GregorianCalendar(Integer.parseInt(sd3[2]),
					Integer.parseInt(sd3[1]) - 1, Integer.parseInt(sd3[0]));
			java.util.Date d3 = c3.getTime();
			BillScrunityDate = new Date(d3.getTime());
			
						
			try {
				ps2 = connection.prepareStatement("update FAS_BILL_REGISTER_MASTER set BILL_SCRUTINY_DONE=?,BILL_SCRUTINY_BY=?,BILL_SCRUTINY_DATE=?,UPDATED_BY_USERID=?,UPDATED_DATE=?,DEDUCTED_AMOUNT=?,NET_AMOUNT=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_UNIT_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and BILL_NO=?");
				ps2.setString(1, rdoScrutinyDone);
				ps2.setInt(2, empid);
				ps2.setDate(3, BillScrunityDate);	
				ps2.setInt(4, empid);
				ps2.setTimestamp(5, ts);
				ps2.setInt(6, DeductedAmount);
				ps2.setInt(7, NetAmount);
				ps2.setInt(8, AccUnitCode);
				ps2.setInt(9, AccOfficeCode);
				ps2.setInt(10, CashBookYear);
				ps2.setInt(11, CashBookMonth);
				ps2.setInt(12, BillNo);				
				ps2.executeUpdate();
				
				xml = xml + "<flag>success</flag>";
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}
		}*/
		xml = xml + "</response>";
		out.write(xml);
		System.out.println(xml);
	}

}
