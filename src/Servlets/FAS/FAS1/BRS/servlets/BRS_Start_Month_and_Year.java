package Servlets.FAS.FAS1.BRS.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
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
 * Servlet implementation class BRS_Start_Month_and_Year
 */
public class BRS_Start_Month_and_Year extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BRS_Start_Month_and_Year() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();

		System.out.println("chk 1");
		Connection connection = null;
		Statement statement = null; 
		ResultSet results = null,resob=null,resob1=null;
		ResultSet results2;
		ResultSet rs = null,res=null;
		ResultSet rs2 = null,rs3=null;
		PreparedStatement ps = null,prep=null;
		PreparedStatement ps1 = null,psob=null,psob1=null;
		PreparedStatement ps2 = null,ps3=null,pss=null;
                String status_f="",tb_status="";
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
			xml = xml + "<response><command>Add</command>";

			int cmbAcc_UnitCode = Integer.parseInt(request
					.getParameter("cmbAcc_UnitCode"));
			int cmbOffice_code = Integer.parseInt(request
					.getParameter("cmbOffice_code"));
			int txtCB_Year = Integer.parseInt(request
					.getParameter("txtCB_Year"));
			int txtCB_Month = Integer.parseInt(request
					.getParameter("txtCB_Month"));
			long cmbBankAccNo=Long.parseLong(request.getParameter("cmbBankAccNo"));
			
			try {
				ps = connection.prepareStatement("SELECT ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID from BRS_START_MONTH_AND_YEAR where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and account_no=?");
				ps.setInt(1, cmbAcc_UnitCode);
				ps.setInt(2, cmbOffice_code);
				ps.setLong(3, cmbBankAccNo);
				
				rs = ps.executeQuery();
				if(rs.next())
				{
					xml = xml + "<flag>Exist</flag>";
				}else
				{
				ps1 = connection.prepareStatement("insert into BRS_START_MONTH_AND_YEAR(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,UPDATED_BY_USER_ID,UPDATED_DATE,ACCOUNT_NO) values(?,?,?,?,?,?,?)");
				ps1.setInt(1, cmbAcc_UnitCode);
				ps1.setInt(2, cmbOffice_code);
				ps1.setInt(3, txtCB_Year);
				ps1.setInt(4, txtCB_Month);
				ps1.setString(5, userid);
				ps1.setTimestamp(6, ts);
				ps1.setLong(7, cmbBankAccNo);
				ps1.executeUpdate();
				xml = xml + "<flag>success</flag>";
				}
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}
		}
		else if (strCommand.equalsIgnoreCase("LoadMonthYear_start")) {
			int notallowed=0;
			xml = xml + "<response><command>LoadMonthYear_start</command>";
			int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
			int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
			long cmbBankAccNo=Long.parseLong(request.getParameter("cmbBankAccNo"));
			try{
				prep=connection.prepareStatement("SELECT CASHBOOK_MONTH FROM fas_brs_monthly_closure WHERE ACCOUNTING_UNIT_ID= " +cmbAcc_UnitCode+
						" AND STATUS              ='Y' and ACCOUNT_NO="+cmbBankAccNo);
				System.out.println("SELECT CASHBOOK_MONTH FROM fas_brs_monthly_closure WHERE ACCOUNTING_UNIT_ID= " +cmbAcc_UnitCode+
						" AND STATUS              ='Y' and ACCOUNT_NO="+cmbBankAccNo);
				res=prep.executeQuery();
				while(res.next())
				{
					notallowed++;
				}
			}
			catch(Exception ee)
			{
				System.out.println(ee);
			}
			if(notallowed==0)
			{
			try {
				ps = connection.prepareStatement("SELECT CASHBOOK_YEAR,CASHBOOK_MONTH from BRS_START_MONTH_AND_YEAR where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and ACCOUNT_NO=?");
				ps.setInt(1, cmbAcc_UnitCode);
				ps.setInt(2, cmbOffice_code);
				ps.setLong(3,cmbBankAccNo);
				rs = ps.executeQuery();				
				if(rs.next())
				{
					xml = xml + "<CB_Year>" + rs.getInt("CASHBOOK_YEAR") + "</CB_Year>";
			        xml = xml + "<CB_Month>" + rs.getInt("CASHBOOK_MONTH")	+ "</CB_Month>";
			        xml = xml + "<flag>success</flag>";
				}else
				{
					xml = xml + "<flag>NoData</flag>";
				}
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}
		  }
			else
			{
				xml = xml + "<flag>NotAllowed</flag>";	
			}
		}
		else if (strCommand.equalsIgnoreCase("LoadMonthYear_start1")) {
			
			xml = xml + "<response><command>LoadMonthYear_start</command>";
			int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
			int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
			long cmbBankAccNo=Long.parseLong(request.getParameter("cmbBankAccNo"));
			
			try {
				ps = connection.prepareStatement("SELECT CASHBOOK_YEAR,CASHBOOK_MONTH from BRS_START_MONTH_AND_YEAR where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and ACCOUNT_NO=?");
				ps.setInt(1, cmbAcc_UnitCode);
				ps.setInt(2, cmbOffice_code);
				ps.setLong(3,cmbBankAccNo);
				rs = ps.executeQuery();				
				if(rs.next())
				{
					xml = xml + "<CB_Year>" + rs.getInt("CASHBOOK_YEAR") + "</CB_Year>";
			        xml = xml + "<CB_Month>" + rs.getInt("CASHBOOK_MONTH")	+ "</CB_Month>";
			        xml = xml + "<flag>success</flag>";
				}else
				{
					xml = xml + "<flag>NoData</flag>";
				}
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}
		 
			
		}
		else if (strCommand.equalsIgnoreCase("LoadMonthYear")) {
			
			xml = xml + "<response><command>LoadMonthYear</command>";
			int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
			int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
			long cmbBankAccNo = Long.parseLong(request.getParameter("cmbBankAccNo"));
			try {
				String s2="SELECT CASHBOOK_YEAR,CASHBOOK_MONTH from BRS_START_MONTH_AND_YEAR where " +
				" ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and " +
				" account_no="+cmbBankAccNo;
				System.out.println(s2);
				ps = connection.prepareStatement(s2);
				//ps.setInt(1, cmbAcc_UnitCode);
				//ps.setInt(2, cmbOffice_code);
				rs = ps.executeQuery();				
				if(rs.next())
				{
					xml = xml + "<CB_Year>" + rs.getInt("CASHBOOK_YEAR") + "</CB_Year>";
			        xml = xml + "<CB_Month>" + rs.getInt("CASHBOOK_MONTH")	+ "</CB_Month>";
			        xml = xml + "<flag>success</flag>";
				}else
				{
					xml = xml + "<flag>NoData</flag>";
				}
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}
		  
			
		}
		//oly for unit
		else if (strCommand.equalsIgnoreCase("LoadMonthYear_unit")) {
			xml = xml + "<response><command>LoadMonthYear_unit</command>";
			int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
			long cmbBankAccNo = Long.parseLong(request.getParameter("cmbBankAccNo"));
			try {
				ps = connection.prepareStatement("SELECT CASHBOOK_YEAR,CASHBOOK_MONTH from BRS_START_MONTH_AND_YEAR where ACCOUNTING_UNIT_ID=? and account_no=?");
				ps.setInt(1, cmbAcc_UnitCode);
				ps.setLong(2, cmbBankAccNo);
				rs = ps.executeQuery();				
				if(rs.next())
				{
					xml = xml + "<CB_Year>" + rs.getInt("CASHBOOK_YEAR") + "</CB_Year>";
			        xml = xml + "<CB_Month>" + rs.getInt("CASHBOOK_MONTH")	+ "</CB_Month>";
			        xml = xml + "<flag>success</flag>";
				}else
				{
					xml = xml + "<flag>NoData</flag>";
				}
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}
		} 
		else if (strCommand.equalsIgnoreCase("LoadMonthYear_again")) {
			//int counter=0;
			xml = xml + "<response><command>LoadMonthYear_again</command>";
			int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
			int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
			int txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));
			
			try {
				ps = connection.prepareStatement("SELECT CASHBOOK_YEAR,CASHBOOK_MONTH from BRS_START_MONTH_AND_YEAR where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=?");
				ps.setInt(1, cmbAcc_UnitCode);
				ps.setInt(2, cmbOffice_code);
				//ps.setInt(3, txtCB_Year);
				
				rs = ps.executeQuery();				
				if(rs.next())
				{
					
					xml = xml + "<CB_Year>" + rs.getInt("CASHBOOK_YEAR") + "</CB_Year>";
			        xml = xml + "<CB_Month>" + rs.getInt("CASHBOOK_MONTH")	+ "</CB_Month>";
			        xml = xml + "<flag>success</flag>";
				}
				
				else
				{
					xml = xml + "<flag>NoData</flag>";
				}
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}
			
		}
		else if (strCommand.equalsIgnoreCase("LoadMonth_again")) {
			int counter=0;
			xml = xml + "<response><command>LoadMonth_again</command>";
			int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
			int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
			long cmbBankAccNo = Long.parseLong(request.getParameter("cmbBankAccNo"));
			
			try {
				ps = connection.prepareStatement("SELECT CASHBOOK_YEAR,CASHBOOK_MONTH from BRS_START_MONTH_AND_YEAR where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and account_no=?");
				ps.setInt(1, cmbAcc_UnitCode);
				ps.setInt(2, cmbOffice_code);
				ps.setLong(3, cmbBankAccNo);
				
				rs = ps.executeQuery();				
				if(rs.next())
				{
					
					xml = xml + "<CB_Year>" + rs.getInt("CASHBOOK_YEAR") + "</CB_Year>";
			        xml = xml + "<CB_Month>" + rs.getInt("CASHBOOK_MONTH")	+ "</CB_Month>";
			        xml = xml + "<flag>success</flag>";
				}
				
				else
				{
					xml = xml + "<flag>NoData</flag>";
				}
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}
			
		}
	    else if (strCommand.equalsIgnoreCase("countCheck")) {
           int count_test=0;
	                            xml = xml + "<response><command>countCheck</command>";
	                            int cmbAcc_UnitCode = Integer.parseInt(request
	                                            .getParameter("cmbAcc_UnitCode"));
	                            int cmbOffice_code = Integer.parseInt(request
	                                            .getParameter("cmbOffice_code"));
	                       
	                            try {
	                                    ps = connection.prepareStatement("SELECT CASHBOOK_YEAR,CASHBOOK_MONTH from BRS_START_MONTH_AND_YEAR where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? ");
	                                    ps.setInt(1, cmbAcc_UnitCode);
	                                    ps.setInt(2, cmbOffice_code);
                                           
	                                    rs = ps.executeQuery();                         
	                                    while(rs.next())
	                                    {
	                                        count_test++;
	                                        xml = xml + "<CB_Year>" + rs.getInt("CASHBOOK_YEAR") + "</CB_Year>";
	                                        xml = xml + "<CB_Month>" + rs.getInt("CASHBOOK_MONTH")  + "</CB_Month>";
                                          
	                                    }
	                               
                                            if(count_test==0) {
                                                xml = xml + "<flag>failure</flag>";
                                                xml = xml + "<flag_test>No record</flag_test>";
                                            }
	                               
                                            else
	                                    {
	                                        xml = xml + "<flag>success</flag>";
	                                        xml = xml + "<flag_test>Proceed</flag_test>";
	                                    }
                                            System.out.println("xml:::"+xml);
	                            } catch (Exception e) {
	                                    xml = xml + "<flag>failure</flag>";
	                                    e.printStackTrace();
	                            }
	                    }
             /*    else if(strCommand.equalsIgnoreCase("TBCheck")) {
                     int count_test=0;
                     xml = xml + "<response><command>TBCheck</command>";
                     int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
                     int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
                     int txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));
                     int txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
                     
                     try {
                            // ps = connection.prepareStatement("SELECT STATUS from FAS_BRS_MONTHLY_CLOSURE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? ");
                             ps = connection.prepareStatement("SELECT TB_STATUS,\n" + 
                             "    ACCOUNTING_UNIT_ID,\n" + 
                             "    ACCOUNTING_FOR_OFFICE_ID,\n" + 
                             "    CASHBOOK_YEAR,\n" + 
                             "    CASHBOOK_MONTH\n" + 
                             "  FROM FAS_TRIAL_BALANCE_STATUS\n" + 
                             "  WHERE ACCOUNTING_UNIT_ID =?\n" + 
                             "  AND CASHBOOK_YEAR        = ?\n" + 
                             "  AND CASHBOOK_MONTH       = ?");
                             ps.setInt(1, cmbAcc_UnitCode);
                             ps.setInt(2, txtCB_Year);
                             ps.setInt(3, txtCB_Month);
                             rs = ps.executeQuery(); 
                       
                             while(rs.next())
                             {
                                 xml = xml + "<flag>success</flag>";
                                 count_test++;
                                 
                              }
                        
                             if(count_test==0) {
                                 xml = xml + "<flag>failure</flag>";//freeze trial balance and then proceed
                                
                                
                             }
                        
                             System.out.println("xml:::"+xml);
                     } catch (Exception e) {
                             xml = xml + "<flag>failure</flag>";
                             e.printStackTrace();
                     }
                 }  */
                 
	    else if(strCommand.equalsIgnoreCase("closeBRS")) {
	        int count_test=0,freezed=0;
	        xml = xml + "<response><command>closeBRS</command>";
	        int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
	        int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
	        int txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));
	        int txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
	        long cmbBankAccNo = Long.parseLong(request.getParameter("cmbBankAccNo"));
	        
	        try {
	               
                ps2 = connection.prepareStatement("Select Ob_Status,NIL_OB_STATUS,Account_No From Fas_Brs_Ob_Status Where Accounting_Unit_Id    =? and Account_No=?");
                ps2.setInt(1, cmbAcc_UnitCode);
                ps2.setLong(2, cmbBankAccNo);
                    
                rs2 = ps2.executeQuery(); 
                if(rs2.next()) {
                	
                	xml = xml + "<freezedstatus>Freezed</freezedstatus>";
                   freezed++;
                }
                else {
                    xml = xml + "<freezedstatus>notFreezed</freezedstatus>";
                }
                
             
			    } catch (Exception e) {
			            xml = xml + "<flag>failure</flag>";
			            e.printStackTrace();
			    }
	        if(freezed>0){
	        try {
	               
	                    ps2 = connection.prepareStatement("SELECT STATUS from FAS_BRS_MONTHLY_CLOSURE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and ACCOUNT_NO=?");
	                    ps2.setInt(1, cmbAcc_UnitCode);
	                    ps2.setInt(2, cmbOffice_code);
	                    ps2.setInt(3, txtCB_Year);
	                    ps2.setInt(4, txtCB_Month);
                        ps2.setLong(5, cmbBankAccNo);
                            
	                    rs2 = ps2.executeQuery(); 
	                    if(rs2.next()) {
	                    
	                        xml = xml + "<flag>closeBRS</flag>";
	                    }
	                    else {
	                    	int count1=0;
	                        xml = xml + "<flag>proceed</flag>";
	                        
	                     String q1 ="select decode(a.Ob_Status,null,'no',a.Ob_Status)normal_ob,decode(b.Ob_Status,null,'no',b.Ob_Status)ob_nt from "+
											" (SELECT Ob_Status, "+
	                    		 "   NIL_OB_STATUS, "+
	                    		 " 		  Account_No "+
	                    		 " 		FROM Fas_Brs_Ob_Status "+
	                    		 " 		WHERE Accounting_Unit_Id ="+cmbAcc_UnitCode+
	                    		 " 		AND Account_No           = "+cmbBankAccNo+
	                    		 " 		AND Ob_Status            ='Y')a "+
	                    		 " 		full outer join "+
	                    		 " 		(SELECT Ob_Status, "+
	                    		 " 		  NIL_OB_STATUS, "+
	                    		 " 		  Account_No "+
	                    		 " 		FROM FAS_BRS_OB_STATUS_NT "+
	                    		 " 		WHERE Accounting_Unit_Id = "+cmbAcc_UnitCode+
	                    		 " 		AND Account_No           = "+cmbBankAccNo+
	                    		 " 		AND OB_STATUS            ='Y')b "+
	                    		 " 		on a.Account_No=b.Account_No";
	                     System.out.println(q1);
	                     psob = connection.prepareStatement(q1);
	                        
	                        resob=psob.executeQuery(); 
	                        if(resob.next())
	                        {
	                        	String normal_ob=resob.getString("normal_ob");
	                        	String ob_nt=resob.getString("ob_nt");
	                        	if(normal_ob.equals("Y"))
	                        	{
	                        		System.out.println("one 1:::");
			                        	String s5="SELECT count(*)as ct FROM FAS_BRS_OB_TRANSACTION WHERE ACCOUNTING_UNIT_ID    ="+cmbAcc_UnitCode+" AND ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" AND ACCOUNT_NO="+cmbBankAccNo+" and OB_PUSHED is null and TWAD_OR_NON_TWAD='T' ";
			                        	System.out.println(s5); 
			                        	psob1 = connection.prepareStatement(s5);
			                        	 resob1=psob1.executeQuery();
			                        	 if(resob1.next())
			                        	 {
			                        		count1=resob1.getInt("ct");
			                        		if(count1>0)
			                        		 xml = xml + "<obpushed>notpushed</obpushed>";
			                        		else
			                        			 xml = xml + "<obpushed>pushed</obpushed>"; 	
			                        	 }
			                        	 else
			                        	 {
			                        		 xml = xml + "<obpushed>pushed</obpushed>"; 
			                        	 }
	                             }
	                        	else if(ob_nt.equals("Y"))
	                        	{
	                        		System.out.println("TWO 2:::");
	                        		
	                        		String s5="SELECT count(*)as ct FROM FAS_BRS_OB_TRANSACTION_NT WHERE ACCOUNTING_UNIT_ID    ="+cmbAcc_UnitCode+" AND ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" AND ACCOUNT_NO="+cmbBankAccNo+" and OB_PUSHED is null and TWAD_OR_NON_TWAD='NT' ";
		                        	 psob1 = connection.prepareStatement(s5);
		                        	 resob1=psob1.executeQuery();
		                        	 if(resob1.next())
		                        	 {
		                        		count1=resob1.getInt("ct");
		                        		System.out.println("NT");
		                        		if(count1>0)
			                        		 xml = xml + "<obpushed>notpushed</obpushed>";
			                        		else
			                        			 xml = xml + "<obpushed>pushed</obpushed>"; 	
		                        	 }
		                        	 else
		                        	 {
		                        		 xml = xml + "<obpushed>pushed</obpushed>"; 
		                        	 }
	                        	}
	                        	else
	                        	{
	                        		 xml = xml + "<obpushed>notpushed</obpushed>";
	                        	}
	                        }
	                        else
	                        {
	                        	xml = xml + "<obpushed>nil</obpushed>"; 	
	                        }
	                    }
	                    
	             //      System.out.println("xml:::"+xml);
	        } catch (Exception e) {
	                xml = xml + "<flag>failure</flag>";
	                e.printStackTrace();
	        }
	    }
	    }
            else if(strCommand.equalsIgnoreCase("passBal")) {
            //	System.out.println("enter");
                int count_test=0,inc=0;
                String mode_id="";
                int tbmonth=0,tbyear=0;
                BigDecimal payAmt,ftamt_t,totalfinal_t;
                double receiptAmt=0.0,ftamt=0.0,totalfinal=0.0;
                xml = xml + "<response><command>passBal</command>";
                int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
                int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
                int txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));
                int txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
                long cmbBankAccNo = Long.parseLong(request.getParameter("cmbBankAccNo"));
                System.out.println("cmbBankAccNo::"+cmbBankAccNo);
                
                try{
    				String ac="SELECT bank_ac_no,AC_OPERATIONAL_MODE_ID From Fas_Mst_Bank_Balance WHERE accounting_unit_id = "+cmbAcc_UnitCode+" and status='Y' and bank_ac_no="+cmbBankAccNo;
    				pss=connection.prepareStatement(ac);
    				ResultSet rss=pss.executeQuery();
    				while(rss.next())
    				{
    					mode_id=rss.getString("AC_OPERATIONAL_MODE_ID");//opr
    				}
    			}
    			catch (Exception e) {
    				System.out.println("Error in mode_id -->" + e);
    			}
    			if(mode_id.equals("COL")){
                //if(cmbBankAccNo==6722){
	                try {
	                       if(txtCB_Month==1)
	                       {
	                    	   tbmonth=12;
	                    	   tbyear=txtCB_Year-1;
	                       }
	                       else
	                       {
	                    	   tbmonth=txtCB_Month-1;
	                    	   tbyear=txtCB_Year-1;
	                       }
	                            ps2 = connection.prepareStatement("Select Sum(Col)as rec From  "+
							    "(Select Sum(Total_Amount) As Col From Fas_Receipt_Master Where Account_No             ="+cmbBankAccNo+
								" And Receipt_Status           ='L' And Created_By_Module       In ('BR','CR')  "+
								" AND ACCOUNTING_UNIT_ID       ="+cmbAcc_UnitCode+" AND ACCOUNTING_FOR_OFFICE_ID ="+cmbOffice_code+" AND Cashbook_Year            ="+tbyear+
								" And Cashbook_Month           =  "+tbmonth+
								" Union All  "+
								" Select Passbook_Balance As Col  "+
								" From Fas_Brs_Master Where Accounting_Unit_Id    ="+cmbAcc_UnitCode+" And Accounting_For_Office_Id="+cmbOffice_code+
								" And Cashbook_Year           ="+tbyear+" And Cashbook_Month          ="+tbmonth+" AND ACCOUNT_NO              ="+cmbBankAccNo+")");
	                           
	                            
	                            rs2 = ps2.executeQuery(); 
	                            if(rs2.next()) {
	                            	System.out.println("if condition");
	                            	xml = xml + "<flag>yesPass</flag>";
	                            
	                            	receiptAmt=rs2.getDouble("rec");
	                            	System.out.println("if receiptAmt"+receiptAmt);
	                            	ps3=connection.prepareStatement("Select sum(Total_Amount)as ft From Fas_Fund_Trf_From_Office Where Accounting_Unit_Id    ="+cmbAcc_UnitCode+" And Accounting_For_Office_Id="+cmbOffice_code+
	                            				" And Cashbook_Year           ="+tbyear+" And Cashbook_Month          ="+tbmonth+" And Office_Account_No       ="+cmbBankAccNo+" and TRANSFER_STATUS='L' ");
	                            	rs3=ps3.executeQuery();
	                            	if(rs3.next())
	                            	{
	                            		System.out.println("if second");
	                            		ftamt=rs3.getDouble("ft");
	                            	}
	                            	totalfinal=receiptAmt-ftamt;
	                            	System.out.println("if totalfinal::"+totalfinal);
	                            	xml = xml + "<passBalance>"+totalfinal+"</passBalance>";
	                            }
	                            else {
	                                xml = xml + "<flag>noPass</flag>";
	                            }
	                            
	                           System.out.println("xml:::"+xml);
	                } 
	                catch (Exception e) 
	                {
	                        xml = xml + "<flag>failure</flag>";
	                        e.printStackTrace();
	                }
                }
                //4181
                else
                {
                	if(txtCB_Month==1)
                    {
                 	   tbmonth=12;
                 	   tbyear=txtCB_Year-1;
                    }
                    else
                    {
                 	   tbmonth=txtCB_Month-1;
                 	   //tbyear=txtCB_Year-1;
                 	  tbyear=txtCB_Year;
                    }
                	try {
	                       
                        String ss="SELECT decode(SUM(Col),null,0,SUM(Col))AS pay\n" + 
                        " FROM\n" + 
                        "  (SELECT SUM(Total_Amount) AS Col\n" + 
                        "  From Fas_Payment_Master\n" + 
                        "  WHERE Account_No             =" + cmbBankAccNo+
                        "  And Payment_Status           ='L'\n" + 
                        "  AND Created_By_Module       IN ('BPP','BPF','SC')\n" + 
                        "  AND ACCOUNTING_UNIT_ID       =" +cmbAcc_UnitCode+ 
                        "  AND ACCOUNTING_FOR_OFFICE_ID =" +cmbOffice_code+ 
                        "  AND Cashbook_Year            =" +tbyear+ 
                        "  AND Cashbook_Month           ="+tbmonth+ 
                        "  UNION ALL\n" + 
                        "  SELECT Passbook_Balance AS Col\n" + 
                        "  FROM Fas_Brs_Master\n" + 
                        "  WHERE Accounting_Unit_Id    =" +cmbAcc_UnitCode+ 
                        "  AND Accounting_For_Office_Id=" +cmbOffice_code+ 
                        "  AND Cashbook_Year           =" +tbyear+
                        "  AND Cashbook_Month          ="+tbmonth+ 
                        "  And Account_No              =" +cmbBankAccNo+ 
                        "  Union All\n" + 
                        "   SELECT SUM(Total_Amount)as col\n" + 
                        " FROM FAS_FUND_RECEIPT_BY_OFFICE\n" + 
                        " WHERE Accounting_Unit_Id    =" +cmbAcc_UnitCode+ 
                        " AND Accounting_For_Office_Id=" +cmbOffice_code+ 
                        " AND Cashbook_Year           =" +tbyear+
                        " AND Cashbook_Month          ="+tbmonth+ 
                        " And Office_Account_No       =" +cmbBankAccNo+ 
                        " AND RECEIPT_STATUS         ='L'\n" + 
                        "  )";
                      //  System.out.println("ss:::"+ss);
                        ps2 = connection.prepareStatement(ss);
                        rs2 = ps2.executeQuery(); 
                        if(rs2.next()) {
                        	
                        	payAmt=rs2.getBigDecimal("pay");
                        	String hh="SELECT DECODE(SUM(Total_Amount),NULL,0,SUM(Total_Amount)) AS ttl_amt\n" + 
      	   	               " FROM FAS_FUND_TRF_FROM_OFFICE\n" + 
     	   	               " WHERE Accounting_Unit_Id    =" +cmbAcc_UnitCode+ 
     	   	               " AND Accounting_For_Office_Id=" +cmbOffice_code+ 
     	   	               " AND Cashbook_Year           =" +tbyear+ 
     	   	               " AND Cashbook_Month          =" +tbmonth+ 
     	   	               " AND OFFICE_ACCOUNT_NO       =" +cmbBankAccNo+" AND TRANSFER_STATUS         ='L'";
         	           //   System.out.println("hh::"+hh);
                        	ps3=connection.prepareStatement(hh);
                        	rs3=ps3.executeQuery();
                        	if(rs3.next())
                        	{
                        		inc++;
                        		ftamt=rs3.getDouble("ttl_amt");
                        		
                        	}
                        	if(ftamt!=0)
                        	{
                        		xml = xml + "<flag>yesPass</flag>";
                        	totalfinal_t=payAmt.subtract(new BigDecimal(ftamt));
                        		
                        	}
                        	
                        	else
                        	{
                        		xml = xml + "<flag>yesPass</flag>";
                        		totalfinal_t=payAmt;
                        	}
                        	
                        	xml = xml + "<passBalance>"+totalfinal_t+"</passBalance>";
                        }
                        else {
                            xml = xml + "<flag>noPass</flag>";
                        }
                        
                       System.out.println("xml:::"+xml);
			            } 
			            catch (Exception e) 
			            {
			                    xml = xml + "<flag>failure</flag>";
			                    e.printStackTrace();
			            }
                }
                
            }
		
            else if(strCommand.equalsIgnoreCase("recinMaster")) {
                xml = xml + "<response><command>recinMaster</command>";
                int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
                int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
                int txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));
                int txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
                long accno = Long.parseLong(request.getParameter("cmbBankAccNo"));
                int count_status=0;
                try {
                        ps = connection.prepareStatement("SELECT PASSBOOK_BALANCE as pb FROM fas_brs_master WHERE ACCOUNTING_UNIT_ID    =? " +
                        		"AND ACCOUNTING_FOR_OFFICE_ID=? " +
                        		"AND CASHBOOK_YEAR           =? AND CASHBOOK_MONTH          =? AND ACCOUNT_NO              =?");
                        ps.setInt(1, cmbAcc_UnitCode);
                        ps.setInt(2, cmbOffice_code);
                        ps.setInt(3, txtCB_Year);
                        ps.setInt(4, txtCB_Month);
                        ps.setLong(5, accno);
                        rs = ps.executeQuery();                         
                        if(rs.next())
                        {
                        	xml = xml + "<pb>"+rs.getString("pb")+"</pb>";   
                        	xml = xml + "<flag>success</flag>";
                            count_status++;
                        
                        }
                        if(count_status==0) {
                            xml = xml + "<flag>NoData</flag>";
                        }
                        
                } catch (Exception e) {
                        xml = xml + "<flag>failure</flag>";
                        e.printStackTrace();
                }
            }
		//brs new menu on 26feb2014
            else if(strCommand.equalsIgnoreCase("getbankbal")) {
           

                xml = xml + "<response><command>getbankbal</command>";
                int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
                long cmbBankAccNo = Long.parseLong(request.getParameter("cmbBankAccNo"));
                int txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));
                int txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
                int count_status=0;
                try {
                        ps = connection.prepareStatement("SELECT PB_BALANCE FROM BRS_BANK_BALANCE_UPDATE WHERE ACCOUNTING_UNIT_ID =? AND BANK_AC_NO           =? AND PS_YEAR              =? AND PS_MONTH             =?");
                        ps.setInt(1, cmbAcc_UnitCode);
                        ps.setLong(2, cmbBankAccNo);
                        ps.setInt(3, txtCB_Year);
                        ps.setInt(4, txtCB_Month);
                        rs = ps.executeQuery();                         
                        if(rs.next())
                        {
                        	xml = xml + "<pb>"+rs.getString("PB_BALANCE")+"</pb>";         
                        xml = xml + "<flag>success</flag>";
                            count_status++;
                        
                        }
                        if(count_status==0) {
                            xml = xml + "<flag>NoData</flag>";
                        }
                        
                } catch (Exception e) {
                        xml = xml + "<flag>failure</flag>";
                        e.printStackTrace();
                }
            
            }
                else if(strCommand.equalsIgnoreCase("checkStatus")) {
                    xml = xml + "<response><command>checkStatus</command>";
                    int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
                    int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
                    int txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));
                    int txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
                    int count_status=0;
                    try {
                            ps = connection.prepareStatement("SELECT TB_STATUS,\n" + 
                            "  ACCOUNTING_UNIT_ID,\n" + 
                            "  ACCOUNTING_FOR_OFFICE_ID,\n" + 
                            "  CASHBOOK_YEAR,\n" + 
                            "  CASHBOOK_MONTH\n" + 
                            " FROM FAS_TRIAL_BALANCE_STATUS\n" + 
                            " WHERE ACCOUNTING_UNIT_ID =?\n" + 
                            " AND CASHBOOK_YEAR        =?\n" + 
                            " AND CASHBOOK_MONTH       = ?");
                            ps.setInt(1, cmbAcc_UnitCode);
                            ps.setInt(2, txtCB_Year);
                            ps.setInt(3, txtCB_Month);
                            rs = ps.executeQuery();                         
                            if(rs.next())
                            {
                                   
                            xml = xml + "<flag>success</flag>";
                                count_status++;
                            
                            }
                            if(count_status==0) {
                                xml = xml + "<flag>NoData</flag>";
                            }
                            
                    } catch (Exception e) {
                            xml = xml + "<flag>failure</flag>";
                            e.printStackTrace();
                    }
                }
                if (strCommand.equalsIgnoreCase("Update")) {
			xml = xml + "<response><command>Update</command>";

			int cmbAcc_UnitCode = Integer.parseInt(request
					.getParameter("cmbAcc_UnitCode"));
			int cmbOffice_code = Integer.parseInt(request
					.getParameter("cmbOffice_code"));
			int txtCB_Year = Integer.parseInt(request
					.getParameter("txtCB_Year"));
			int txtCB_Month = Integer.parseInt(request
					.getParameter("txtCB_Month"));
			
			try {
				ps = connection.prepareStatement("SELECT ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID from BRS_START_MONTH_AND_YEAR where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=?");
				ps.setInt(1, cmbAcc_UnitCode);
				ps.setInt(2, cmbOffice_code);
				rs = ps.executeQuery();
				if(rs.next())
				{
					ps1 = connection.prepareStatement("update BRS_START_MONTH_AND_YEAR set CASHBOOK_YEAR=?,CASHBOOK_MONTH=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? ");					
					ps1.setInt(1, txtCB_Year);
					ps1.setInt(2, txtCB_Month);
					ps1.setString(3, userid);
					ps1.setTimestamp(4, ts);
					ps1.setInt(5, cmbAcc_UnitCode);
					ps1.setInt(6, cmbOffice_code);
					ps1.executeUpdate();
					xml = xml + "<flag>success</flag>";
					
				}else
				{
					xml = xml + "<flag>NoData</flag>";
				}
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}
		}
		xml = xml + "</response>";
		out.write(xml);
		System.out.println(xml);
	}

}
