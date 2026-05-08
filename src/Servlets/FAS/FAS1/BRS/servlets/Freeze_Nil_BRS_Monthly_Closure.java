package Servlets.FAS.FAS1.BRS.servlets;

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
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Freeze_Nil_BRS_Monthly_Closure
 */
public class Freeze_Nil_BRS_Monthly_Closure extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
    public Freeze_Nil_BRS_Monthly_Closure() {
        super();     
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		Connection connection = null;
		Statement statement = null;				
		ResultSet rs = null,rs1=null,results=null,rs2=null;		
		PreparedStatement ps = null;
		PreparedStatement ps1 = null,ps2=null,ps3=null,pss=null,ps4=null;		
		Double col_part1=0.0,col_part2a=0.0,col_part2b=0.0;
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
			System.out.println(session);

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
			System.out.println(strCommand);
		} catch (Exception e) {
			e.printStackTrace();
		}

		long l = System.currentTimeMillis();
		Timestamp ts = new Timestamp(l);
		
		if (strCommand.equalsIgnoreCase("loadBankDetails")) {
			
				int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
			int count=0;
			xml = "<response><command>bankDetailsLoad</command>";
			
			try {
				ps = connection.prepareStatement("select f2.BANK_ID,(select (f1.BANK_SHORT_NAME)as BANK_NAME from FAS_MST_BANKS f1 where f2.BANK_ID=f1.BANK_ID)as BankName," +
						"f2.BRANCH_ID,f2.BANK_AC_NO,f2.BANK_AC_TYPE_ID,f2.AC_OPERATIONAL_MODE_ID from FAS_MST_BANK_BALANCE f2 where f2.ACCOUNTING_UNIT_ID=? and f2.STATUS='Y'");
				ps.setInt(1,cmbAcc_UnitCode);
				results = ps.executeQuery();
				while (results.next()) {
					xml = xml + "<bank_Detail>"+
						    // results.getInt("BANK_ID")+" - "+
							 // results.getInt("BRANCH_ID")+" - "+
					          results.getString("BankName")+" - "+
							  results.getString("BANK_AC_NO").trim()+" - "+
							 // results.getString("BANK_AC_TYPE_ID")+" - "+
							  results.getString("AC_OPERATIONAL_MODE_ID")
							  
							+ "</bank_Detail>";
					//xml=xml+"<bankNo>"+ results.getString("BANK_AC_NO").trim()+"</bankNo>";
					count++;
				
				}
				xml = xml + "<count>"+count+"</count>";
				if(count==0){
					xml = xml + "<flag>failure</flag>";
				}else{
					xml = xml + "<flag>success</flag>";
					//System.out.println("count"+count);
				
				}
								
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}
			
		} 
		else if (strCommand.equalsIgnoreCase("Add")) {
			xml = xml + "<response><command>Add</command>";
			int upd=0,up1=0,up2=0,counted=0,sk_ob=0;
			int part1=0,part2a=0,part2b=0,part2c=0;
			long part1ob=0,part2aob=0,part2bob=0;
			String s4=null,s1=null;
			ResultSet rs_one=null;
			ResultSet rs_s1=null;
			int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
			int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
			int txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));
			int txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
		    String cmbBankAccNo = request.getParameter("cmbBankAccNo");
		 //   StringTokenizer strt=new StringTokenizer(cmbBankAccNo ,"-");
		    String branchid=null,bankid=null,bankname=null,bankaccno=null,actype=null,oprmode=null;
		    long bankacno=0;
		    //long collecAccno=0,oprAccno=0,FDWAccno=0;
		  /*  while(strt.hasMoreTokens()){
		    	//bankid=strt.nextToken().trim();
		    	//branchid=strt.nextToken().trim(); 
		    	bankname=strt.nextToken().trim();
		    
		    	//actype=strt.nextToken().trim();
		    	oprmode=strt.nextToken().trim();
		    }
		    */
		    bankaccno=cmbBankAccNo.split("-")[1].trim();
		    System.out.println("bankacno >> "+bankacno);
		  bankacno=Long.parseLong(bankaccno);
		   
		   /* if(oprmode.equals("COL"))
			{
				collecAccno=Long.parseLong(bankaccno);
			}
			else if(oprmode.equals("OPR"))
			{
				oprAccno=Long.parseLong(bankaccno);
			}
			else if(oprmode.equals("FDW"))
			{
				FDWAccno=Long.parseLong(bankaccno);
			}*/
		    int newyr=0,newmnth=0;

		try {

			ps1 = connection.prepareStatement("select ACCOUNTING_UNIT_ID from FAS_BRS_MONTHLY_CLOSURE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and ACCOUNT_NO=?");
			ps1.setInt(1, cmbAcc_UnitCode);
			ps1.setInt(2, cmbOffice_code);
			ps1.setInt(3, txtCB_Year);
			ps1.setInt(4, txtCB_Month);
            ps1.setLong(5, bankacno);
			rs = ps1.executeQuery();
			if (rs.next()) {
				xml = xml + "<flag>Exist</flag>";
			} else {
				ps = connection.prepareStatement("insert into FAS_BRS_MONTHLY_CLOSURE(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,NIL_BRS_FREEZE,UPDATED_BY_USER_ID,UPDATED_DATE,FREEZED_DATE,ACCOUNT_NO) values (?,?,?,?,?,?,?,?,?)");
				ps.setInt(1, cmbAcc_UnitCode);
				ps.setInt(2, cmbOffice_code);
				ps.setInt(3, txtCB_Year);
				ps.setInt(4, txtCB_Month);	
				ps.setString(5, "Y");	
				ps.setString(6, userid);
				ps.setTimestamp(7, ts);	
				ps.setTimestamp(8, ts);
                ps.setLong(9, bankacno);
				int k = ps.executeUpdate();
				if (k > 0) {
					
                                            if(txtCB_Month==12) {
                                                int ttyear=txtCB_Year+1;
                                                System.out.println("ttyear::::::::"+ttyear);
                                                ps2 = connection.prepareStatement("update BRS_START_MONTH_AND_YEAR set CASHBOOK_YEAR=?,CASHBOOK_MONTH=?,UPDATED_DATE=?  where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and ACCOUNT_NO=?");
                                                ps2.setInt(1, ttyear);
                                                ps2.setInt(2, 1);
                                                ps2.setTimestamp(3, ts);
                                                ps2.setInt(4, cmbAcc_UnitCode);
                                                ps2.setInt(5, cmbOffice_code);
                                                ps2.setLong(6, bankacno);
                                                upd = ps2.executeUpdate();
                                                if(upd>0) {
                                                    xml = xml + "<flag>success</flag>";
                                                }
                                            }
                                            else {
                                                System.out.println("else:::");
                                                int tt=txtCB_Month+1;
                                                System.out.println("tt::::::::"+tt);
                                                ps2 = connection.prepareStatement("update BRS_START_MONTH_AND_YEAR set CASHBOOK_MONTH=?,UPDATED_DATE=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and ACCOUNT_NO=?");
                                                ps2.setInt(1, tt);
                                                ps2.setTimestamp(2, ts);
                                                ps2.setInt(3, cmbAcc_UnitCode);
                                                ps2.setInt(4, cmbOffice_code);
                                                ps2.setInt(5, txtCB_Year);
                                                ps2.setLong(6, bankacno);
                                                upd = ps2.executeUpdate();
                                                if(upd>0) {
                                                    xml = xml + "<flag>success</flag>";
                                                }
                                              
                                            }
                                            
                                  	if(upd>0)
                                        	{
                                        		String mode_id="";
                                        		 try{
                                       	 		  
                                       	 		  
                                       	 			 newyr=txtCB_Year;
                                       	 			  newmnth=txtCB_Month;
                                       	 		
                                       			 		 s1=" select ob_part1, "+
                                       			 		" ob_part2a,"+
                                       			 		" ob_part2b from fas_brs_ob  \n" + 
                                       		     " WHERE Accounting_Unit_Id    = " +cmbAcc_UnitCode+
                                       		     " AND Accounting_For_Office_Id=" +cmbOffice_code+ 
                                       		     " AND cashbook_year         =" +newyr+ 
                                       		     " And cashbook_month        =" +newmnth+ 
                                       		     " And Account_No              ="+bankacno;
                                       		System.out.println("s1:"+s1);
                                       		PreparedStatement ps_s1=connection.prepareStatement(s1);
                                       		rs_s1=ps_s1.executeQuery();
                                       		while(rs_s1.next())
                                       		{
                                       			part1ob=rs_s1.getLong("OB_PART1");
                                       			part2aob=rs_s1.getLong("OB_PART2A");
                                       			part2bob=rs_s1.getLong("OB_PART2B");
                                       			
                                       		}
                                       	
                                       	 				
                                       	 	  }catch(Exception e){
                                       	 		  System.out.println("Getting part values from fas_brs_ob"+e);
                                       	 	  }
                                        		
                                        		try{
                                    				String ac="SELECT bank_ac_no,AC_OPERATIONAL_MODE_ID From Fas_Mst_Bank_Balance WHERE accounting_unit_id = "+cmbAcc_UnitCode+" and status='Y' and bank_ac_no="+bankacno;
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
                                        		
                                        		if(txtCB_Month==12){
                                        			System.out.println("december:::");
                                                	int yr=txtCB_Year+1;
                                                	ps3=connection.prepareStatement("insert into FAS_BRS_OB (ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,OB_TYPE,ACCOUNT_NO,OB_PART1,OB_PART2A,OB_PART2B,UPDATED_BY_USER_ID,UPDATED_DATE) values (?,?,?,?,?,?,?,?,?,?,?)");
                                                	ps3.setInt(1, cmbAcc_UnitCode);
                                                	ps3.setInt(2, cmbOffice_code);
                                                	ps3.setInt(3, yr);
                                                	ps3.setInt(4,1);//month
                                                	ps3.setString(5,"T");
                                                	ps3.setLong(6, bankacno);
                                                	if(mode_id.equals("COL"))
                                                	{
                                                		ps3.setDouble(7,part1ob);
                                                    	ps3.setDouble(8,0);
                                                    	ps3.setDouble(9,0);
                                                	}
                                                	else
                                                	{
                                                		ps3.setDouble(7,0);
                                                    	ps3.setDouble(8,part2aob);
                                                    	ps3.setDouble(9,part2bob);
                                                	}
                                                	ps3.setString(10, userid);
                                                	ps3.setTimestamp(11, ts);
                                                	sk_ob=ps3.executeUpdate();
                                                	
                                                	}
                                                	else
                                                	{
                                                		System.out.println("enter into:other month:");
                                                		int mn=txtCB_Month+1;
                                                    	ps3=connection.prepareStatement("insert into FAS_BRS_OB (ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,OB_TYPE,ACCOUNT_NO,OB_PART1,OB_PART2A,OB_PART2B,UPDATED_BY_USER_ID,UPDATED_DATE) values (?,?,?,?,?,?,?,?,?,?,?)");
                                                    	ps3.setInt(1, cmbAcc_UnitCode);
                                                    	ps3.setInt(2, cmbOffice_code);
                                                    	ps3.setInt(3, txtCB_Year);
                                                    	ps3.setInt(4,mn);//month
                                                    	ps3.setString(5,"T");
                                                    	ps3.setLong(6, bankacno);
                                                    	//if(cmbBankAccNo==6722)
                                                    	if(mode_id.equals("COL"))
                                                    	{
                                                    		ps3.setDouble(7,part1ob);
                                                        	ps3.setDouble(8,0);
                                                        	ps3.setDouble(9,0);
                                                    	}
                                                    	else
                                                    	{
                                                    		ps3.setDouble(7,0);
                                                        	ps3.setDouble(8,part2aob);
                                                        	ps3.setDouble(9,part2bob);
                                                    	}
                                                    	ps3.setString(10, userid);
                                                    	ps3.setTimestamp(11, ts);
                                                    	sk_ob=ps3.executeUpdate();
                                                    	
                                                	}
                                        		
                                        		System.out.println("sk_ob::::"+sk_ob);
                                            	connection.commit();
                                        	}
                                            
                                            
                                            
                                            
				} else {
					xml = xml + "<flag>failure</flag>";
				}

			}
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

}
	

