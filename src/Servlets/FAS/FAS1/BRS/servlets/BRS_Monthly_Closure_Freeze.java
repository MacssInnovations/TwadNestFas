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
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class BRS_Monthly_Closure_Freeze
 */
public class BRS_Monthly_Closure_Freeze extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BRS_Monthly_Closure_Freeze() {
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
		int sk=0,sk_ob=0;
		Double col_part1=0.0,col_part2a=0.0,col_part2b=0.0;
		Double cramt=0.0,dramt=0.0;
		Connection connection = null;
		Statement statement = null;				
		ResultSet rs = null,rs_one=null;		
		PreparedStatement ps = null;
		PreparedStatement ps1 = null,ps2=null,ps3=null,pss=null;		

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
                    int counted=0;
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
			int part1=0,part2a=0,part2b=0,part2c=0;
			int upd=0;
			String s4=null;
			String opr_col=null;
			String Mode_val="";
			long oprAccno=0,collecAccno=0,FDWAccno=0;
			int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
			int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
			int txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));
			int txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
		    long cmbBankAccNo =Long.parseLong(request.getParameter("cmbBankAccNo"));
		    try{
				String ac="SELECT bank_ac_no,AC_OPERATIONAL_MODE_ID,case when Ac_Operational_Mode_Id like '%OPR%' then 'OPR' else Ac_Operational_Mode_Id end as MODE_1 From Fas_Mst_Bank_Balance WHERE accounting_unit_id = "+cmbAcc_UnitCode+" and status='Y' and bank_ac_no="+cmbBankAccNo+" order by AC_OPERATIONAL_MODE_ID";
				pss=connection.prepareStatement(ac);
				ResultSet rss=pss.executeQuery();
				while(rss.next())
				{
					opr_col=rss.getString("AC_OPERATIONAL_MODE_ID");//opr
					Mode_val=rss.getString("MODE_1");
					
					if(opr_col.equals("COL"))
					{
						oprAccno=rss.getLong("bank_ac_no");
					}
					else if(opr_col.equals("OPR"))
					{
						oprAccno=rss.getLong("bank_ac_no");
					}
					else if(opr_col.equals("FDW"))
					{
						oprAccno=rss.getLong("bank_ac_no");
						
					}else{
						oprAccno=rss.getLong("bank_ac_no");
					}
				}
			}
			catch (Exception e) {
				System.out.println("Error Not Bank Account opr or col -->" + e);
			}
			
			try{
					//if(opr_col.equals("COL"))
				System.out.println("Mode_val     "+Mode_val);
				if(!Mode_val.equals("OPR"))
					{
						s4="Select Decode(Count1,Null,0,Count1)As Count1 " +
								"from (SELECT COUNT(*) AS count1,Accounting_Unit_Id\n" + 
			            " FROM FAS_BRS_PART1\n" + 
			            " WHERE Accounting_Unit_Id    = " +cmbAcc_UnitCode+
			            " AND Accounting_For_Office_Id=" +cmbOffice_code+ 
			            " AND Pass_Sheet_Year         =" +txtCB_Year+ 
			            " And Pass_Sheet_Month        =" +txtCB_Month+ 
			            " And Account_No              ="+oprAccno+" Group by Accounting_Unit_Id\n" +
			            
			            " )" ;
			           
						System.out.println("s4:"+s4);
						PreparedStatement ps_one=connection.prepareStatement(s4);
						rs_one=ps_one.executeQuery();
						while(rs_one.next())
						{
							part1=rs_one.getInt("Count1");
							
						}
			           rs_one.close();
			           ps_one.close();
					}
					else if(!Mode_val.equals("COL"))
					{
						s4="SELECT DECODE(A.Count_Two,NULL,0,A.Count_Two)AS Count_Two,"+
				  " DECODE(C.Count2,NULL,0,C.Count2)           AS Count2, "+
						" DECODE(d.count3,NULL,0,d.count3)           AS count3 "+
						" FROM "+
						" (SELECT COUNT(*) AS count_two, "+
						" Accounting_Unit_Id "+
						" FROM FAS_BRS_PART_2A "+
						" WHERE Accounting_Unit_Id    =  "+cmbAcc_UnitCode+
						" AND Accounting_For_Office_Id= "+cmbOffice_code+
						" AND Pass_Sheet_Year         = "+txtCB_Year+
						" AND Pass_Sheet_Month        = "+txtCB_Month+
						" AND Account_No              = "+oprAccno+
						" GROUP BY Accounting_Unit_Id "+
						" )a "+
						" FULL OUTER JOIN "+
						" (SELECT COUNT(*) AS count2, "+
						" Accounting_Unit_Id "+
						" FROM FAS_BRS_PART_2B "+
						" WHERE Accounting_Unit_Id    =  "+cmbAcc_UnitCode+
						" AND Accounting_For_Office_Id= "+cmbOffice_code+
						" AND Pass_Sheet_Year         = "+txtCB_Year+
						" AND Pass_Sheet_Month        = "+txtCB_Month+
						" AND Account_No              = "+oprAccno+
						" GROUP BY Accounting_Unit_Id "+
						" )C "+
						" ON A.Accounting_Unit_Id=C.Accounting_Unit_Id "+
						" FULL OUTER JOIN "+
						"  (SELECT COUNT(*) AS count3, "+
						"   Accounting_Unit_Id "+
						"  FROM FAS_BRS_PART_2C "+
						"  WHERE Accounting_Unit_Id    =  "+cmbAcc_UnitCode+
						"  AND Accounting_For_Office_Id= "+cmbOffice_code+
						"  AND Pass_Sheet_Year         = "+txtCB_Year+
						"  AND Pass_Sheet_Month        ="+txtCB_Month+
						" AND Account_No              = "+oprAccno+
						"   GROUP BY Accounting_Unit_Id "+
						"  )D "+
						" ON A.Accounting_Unit_Id=d.Accounting_Unit_Id";
						System.out.println("s4:"+s4);
						PreparedStatement ps_one=connection.prepareStatement(s4);
						rs_one=ps_one.executeQuery();
						while(rs_one.next())
						{
							part2a=rs_one.getInt("Count_Two");
							part2b=rs_one.getInt("Count2");
							// Temp Comment for 2c Report button not visible
							part2c=rs_one.getInt("count3");
						//	part2c=1;
						}
					     rs_one.close();
				           ps_one.close();
					}
				
				
if(!Mode_val.equalsIgnoreCase("COL")){

						s4="SELECT DECODE(A.Count_Two,NULL,0,A.Count_Two)AS Count_Two,"+
				  " DECODE(C.Count2,NULL,0,C.Count2)           AS Count2, "+
						" DECODE(d.count3,NULL,0,d.count3)           AS count3 "+
						" FROM "+
						" (SELECT COUNT(*) AS count_two, "+
						" Accounting_Unit_Id "+
						" FROM FAS_BRS_PART_2A "+
						" WHERE Accounting_Unit_Id    =  "+cmbAcc_UnitCode+
						" AND Accounting_For_Office_Id= "+cmbOffice_code+
						" AND Pass_Sheet_Year         = "+txtCB_Year+
						" AND Pass_Sheet_Month        = "+txtCB_Month+
						" AND Account_No              = "+oprAccno+
						" GROUP BY Accounting_Unit_Id "+
						" )a "+
						" FULL OUTER JOIN "+
						" (SELECT COUNT(*) AS count2, "+
						" Accounting_Unit_Id "+
						" FROM FAS_BRS_PART_2B "+
						" WHERE Accounting_Unit_Id    =  "+cmbAcc_UnitCode+
						" AND Accounting_For_Office_Id= "+cmbOffice_code+
						" AND Pass_Sheet_Year         = "+txtCB_Year+
						" AND Pass_Sheet_Month        = "+txtCB_Month+
						" AND Account_No              = "+oprAccno+
						" GROUP BY Accounting_Unit_Id "+
						" )C "+
						" ON A.Accounting_Unit_Id=C.Accounting_Unit_Id "+
						" FULL OUTER JOIN "+
						"  (SELECT COUNT(*) AS count3, "+
						"   Accounting_Unit_Id "+
						"  FROM FAS_BRS_PART_2C "+
						"  WHERE Accounting_Unit_Id    =  "+cmbAcc_UnitCode+
						"  AND Accounting_For_Office_Id= "+cmbOffice_code+
						"  AND Pass_Sheet_Year         = "+txtCB_Year+
						"  AND Pass_Sheet_Month        ="+txtCB_Month+
						" AND Account_No              = "+oprAccno+
						"   GROUP BY Accounting_Unit_Id "+
						"  )D "+
						" ON A.Accounting_Unit_Id=d.Accounting_Unit_Id";
						System.out.println("s4:"+s4);
						PreparedStatement ps_one=connection.prepareStatement(s4);
						rs_one=ps_one.executeQuery();
						while(rs_one.next())
						{
							part2a=rs_one.getInt("Count_Two");
							part2b=rs_one.getInt("Count2");
							// Temp Comment for 2c Report button not visible
							part2c=rs_one.getInt("count3");
							//part2c=1;
						}
					     rs_one.close();
				           ps_one.close();
					}
					
					
				
				}
				 catch(Exception e) 
			       {
			              System.out.println("Exception in onload..."+e);
			              xml=xml+"<flag>failure</flag>";
			       }
				 /*//Lakshmi 16April2014
				 if(opr_col.equals("COL") && part1==0)
				 {
						  xml=xml+"<proceed>stop</proceed>";
						  xml=xml+"<part1>"+part1+"</part1>";
						  xml=xml+"<part>part1</part>";
						  xml = xml + "<flag>failure</flag>";
					
				 }
				 else if((!opr_col.equals("COL")) && part2a==0)
				 {
					 
								  xml=xml+"<proceed>stop</proceed>";
								  xml=xml+"<part2a>"+part2a+"</part2a>";
								  xml=xml+"<part>part2a</part>";
								  xml = xml + "<flag>failure</flag>";
				 }
				 else if((!opr_col.equals("COL")) && part2b==0)
				 {
					 
								  xml=xml+"<proceed>stop</proceed>";
								  xml=xml+"<part2b>"+part2b+"</part2b>";
								  xml=xml+"<part>part2b</part>";
								  xml = xml + "<flag>failure</flag>";
				 }
				 else if((!opr_col.equals("COL")) && part2c==0)
				 {
					 xml=xml+"<proceed>stop</proceed>";
					 xml=xml+"<part2c>"+part2c+"</part2c>";
					 xml=xml+"<part>part2c</part>";
					 xml = xml + "<flag>failure</flag>";
				 }
					*/	
			
			//
			/* *joan changed on 08 oCt 2015 
			 */
			
			
			
		
			 if(!Mode_val.equals("OPR") && part1==0)
			 {
					  xml=xml+"<proceed>stop</proceed>";
					  xml=xml+"<part1>"+part1+"</part1>";
					  xml=xml+"<part>part1</part>";
					  xml = xml + "<flag>failure</flag>";
				
			 }
			 else if((!Mode_val.equals("COL")) && part2a==0)
			 {
				 
							  xml=xml+"<proceed>stop</proceed>";
							  xml=xml+"<part2a>"+part2a+"</part2a>";
							  xml=xml+"<part>part2a</part>";
							  xml = xml + "<flag>failure</flag>";
			 }
			 else if((!Mode_val.equals("COL")) && part2b==0)
			 {
				 
							  xml=xml+"<proceed>stop</proceed>";
							  xml=xml+"<part2b>"+part2b+"</part2b>";
							  xml=xml+"<part>part2b</part>";
							  xml = xml + "<flag>failure</flag>";
			 }
			 else if((!Mode_val.equals("COL")) && part2c==0)
			 {
				 xml=xml+"<proceed>stop</proceed>";
				 xml=xml+"<part2c>"+part2c+"</part2c>";
				 xml=xml+"<part>part2c</part>";
				 xml = xml + "<flag>failure</flag>";
			 }
			
				/*  else if((!opr_col.equalsIgnoreCase("OPR")) && (!opr_col.equalsIgnoreCase("COL")) && part2a==0){
				
						 xml=xml+"<proceed>stop</proceed>";
						 xml=xml+"<part2a>"+part2a+"</part2a>";
						 xml=xml+"<part>part2a</part>"; 
				
				 }else if((!opr_col.equalsIgnoreCase("OPR")) && (!opr_col.equalsIgnoreCase("COL")) && part2b==0){
					
							 xml=xml+"<proceed>stop</proceed>";
							 xml=xml+"<part2b>"+part2b+"</part2b>";
							 xml=xml+"<part>part2b</part>"; 
					
						}else if((!opr_col.equalsIgnoreCase("OPR")) && (!opr_col.equalsIgnoreCase("COL")) && part2c==0){
						
								 xml=xml+"<proceed>stop</proceed>";
								 xml=xml+"<part2c>"+part2c+"</part2c>";
								 xml=xml+"<part>part2c</part>"; 
							
						 }*/
				else
				{
					  xml=xml+"<proceed>start</proceed>";
				
					  
					//  counted=1;            
                    try{
                        ps1 = connection.prepareStatement("select * from FAS_BRS_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and ACCOUNT_NO=?");
                        ps1.setInt(1, cmbAcc_UnitCode);
                        ps1.setInt(2, cmbOffice_code);
                        ps1.setInt(3, txtCB_Year);
                        ps1.setInt(4, txtCB_Month);
                        ps1.setLong(5, cmbBankAccNo);
                        rs = ps1.executeQuery(); 
                        while(rs.next()) {
                            counted++;
                        }
                        ps1.close();
                        rs.close();
                    }
                    catch(Exception ee) {
                        System.out.println("excep in FAS_BRS_MASTER:::"+ee);
                    }
                    if(counted==0) {
                        xml = xml + "<flag>NoRecord</flag>";
                    }
                    else{
			try {
System.out.println("select ACCOUNTING_UNIT_ID from FAS_BRS_MONTHLY_CLOSURE where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and CASHBOOK_YEAR="+txtCB_Year+" and CASHBOOK_MONTH="+txtCB_Month+" and ACCOUNT_NO="+cmbBankAccNo);
				ps1 = connection.prepareStatement("select ACCOUNTING_UNIT_ID from FAS_BRS_MONTHLY_CLOSURE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and ACCOUNT_NO=?");
				ps1.setInt(1, cmbAcc_UnitCode);
				ps1.setInt(2, cmbOffice_code);
				ps1.setInt(3, txtCB_Year);
				ps1.setInt(4, txtCB_Month);
                ps1.setLong(5, cmbBankAccNo);
				rs = ps1.executeQuery();
				if (rs.next()) {
					System.out.println("Aleardy in table....");
					xml = xml + "<flag>Exist</flag>";
				} else {
					ps = connection.prepareStatement("insert into FAS_BRS_MONTHLY_CLOSURE(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,STATUS,UPDATED_BY_USER_ID,UPDATED_DATE,FREEZED_DATE,ACCOUNT_NO) values (?,?,?,?,?,?,?,?,?)");
					ps.setInt(1, cmbAcc_UnitCode);
					ps.setInt(2, cmbOffice_code);
					ps.setInt(3, txtCB_Year);
					ps.setInt(4, txtCB_Month);	
					ps.setString(5, "Y");	
					ps.setString(6, userid);
					ps.setTimestamp(7, ts);	
					ps.setTimestamp(8, ts);
                    ps.setLong(9, cmbBankAccNo);
					int k = ps.executeUpdate();
					System.out.println("K==>insert brs  "+k);
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
                                                    ps2.setLong(6, cmbBankAccNo);
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
                                                    ps2.setLong(6, cmbBankAccNo);
                                                    upd = ps2.executeUpdate();
                                                    if(upd>0) {
                                                        xml = xml + "<flag>success</flag>";
                                                    }
                                                  
                                                }
                                                
                                          
System.out.println("***********************************************   upd>0     "+upd);
                                            	if(upd>0)
                                            	{
                                            		String mode_id="";
                                            		int bank_id=0,branch_id=0;;
                                            		String qu="select decode(part1,null,0,part1)as part1,decode(part2a,null,0,part2a) as part2a, "+
												" decode(part2b,null,0,part2b) as part2b from "+
                                            			" (SELECT S5 AS part1, "+
                                            			" Accounting_Unit_Id "+
                                            			"  FROM FAS_BRS_PART1  "+
                                            			"   WHERE Accounting_Unit_Id    =  "+cmbAcc_UnitCode+
                                            			"   AND Accounting_For_Office_Id=  "+cmbOffice_code+
                                            			"   AND Pass_Sheet_Year         = "+txtCB_Year+
                                            			"   AND Pass_Sheet_Month        = "+txtCB_Month+
                                            			"   and account_no="+cmbBankAccNo+")a "+
                                            			"  full join "+
                                            			"   (SELECT S5 AS part2a, "+
                                            			"     Accounting_Unit_Id "+
                                            			"  FROM FAS_BRS_PART_2A  "+
                                            			"   WHERE Accounting_Unit_Id    =  "+cmbAcc_UnitCode+
                                            			"  AND Accounting_For_Office_Id=  "+cmbOffice_code+
                                            			"  AND Pass_Sheet_Year         = "+txtCB_Year+
                                            			"  AND Pass_Sheet_Month        = "+txtCB_Month+
                                            			"  and account_no="+cmbBankAccNo+""+
                                            			" )b "+
                                            			"  on a.Accounting_Unit_Id=b.Accounting_Unit_Id "+
                                            			"  full join "+
                                            			"  (SELECT S5 AS part2b, "+
                                            			"   Accounting_Unit_Id "+
                                            			"  FROM fas_brs_part_2b  "+
                                            			"  WHERE Accounting_Unit_Id    =  "+cmbAcc_UnitCode+
                                            			"  AND Accounting_For_Office_Id=  "+cmbOffice_code+
                                            			"  AND Pass_Sheet_Year         = "+txtCB_Year+
                                            			" AND Pass_Sheet_Month        = "+txtCB_Month+
                                            			"  and account_no="+cmbBankAccNo+")c "+
                                            			"  on b.Accounting_Unit_Id=c.Accounting_Unit_Id";
                                            		System.out.println("qu:::"+qu);
                                            		PreparedStatement prs=connection.prepareStatement(qu);
                                            		ResultSet res=prs.executeQuery();
                                            		if(res.next())
                                            		{
                                            			col_part1=res.getDouble("Part1");
                                            			col_part2a=res.getDouble("Part2a");
                                            			col_part2b=res.getDouble("Part2b");
                                            		}
                                            		
                                            		try{
                                        				String ac="SELECT bank_ac_no,AC_OPERATIONAL_MODE_ID,bank_id,branch_id From Fas_Mst_Bank_Balance WHERE accounting_unit_id = "+cmbAcc_UnitCode+" and status='Y' and bank_ac_no="+cmbBankAccNo;
                                        				pss=connection.prepareStatement(ac);
                                        				ResultSet rss=pss.executeQuery();
                                        				if(rss.next())
                                        				{
                                        					mode_id=rss.getString("AC_OPERATIONAL_MODE_ID");//opr
                                        					bank_id=rss.getInt("bank_id");//bank_id
                                        					branch_id=rss.getInt("branch_id");//branch_id
                                        				}
                                        			}
                                        			catch (Exception e) {
                                        				System.out.println("Error in mode_id -->" + e);
                                        			}
                                            		
                                            		if(txtCB_Month==12){
                                            			System.out.println("december:::");
                                                    	int yr=txtCB_Year+1;
                                                    	
                                                    	 PreparedStatement psta=connection.prepareStatement("delete from FAS_BRS_OB where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and CASHBOOK_YEAR="+yr+" and CASHBOOK_MONTH=1 and ACCOUNT_NO="+cmbBankAccNo);
                      				            	   		psta.executeUpdate();
                                                    	
                                                    	ps3=connection.prepareStatement("insert into FAS_BRS_OB (ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,OB_TYPE,ACCOUNT_NO,OB_PART1,OB_PART2A,OB_PART2B,UPDATED_BY_USER_ID,UPDATED_DATE,BANK_ID,BRANCH_ID) values (?,?,?,?,?,?,?,?,?,?,?,?,?)");
                                                    	ps3.setInt(1, cmbAcc_UnitCode);
                                                    	ps3.setInt(2, cmbOffice_code);
                                                    	ps3.setInt(3, yr);
                                                    	ps3.setInt(4,1);//month
                                                    	ps3.setString(5,"T");
                                                    	ps3.setLong(6, cmbBankAccNo);
                                                    	
                                                   /* 	if(mode_id.equals("COL"))
                                                    	{
                                                    		ps3.setDouble(7,col_part1);
                                                        	ps3.setDouble(8,0);
                                                        	ps3.setDouble(9,0);
                                                    	}
                                                    	else
                                                    	{
                                                    		ps3.setDouble(7,0);
                                                        	ps3.setDouble(8,col_part2a);
                                                        	ps3.setDouble(9,col_part2b);
                                                    	}*/
                                                    	ps3.setDouble(7,col_part1);
                                                    	ps3.setDouble(8,col_part2a);
                                                    	ps3.setDouble(9,col_part2b);
                                                    	ps3.setString(10, userid);
                                                    	ps3.setTimestamp(11, ts);
                                                    	ps3.setInt(12,bank_id);
                                                    	ps3.setInt(13,branch_id);
                                                    	sk_ob=ps3.executeUpdate();
                                                    	
                                                    	}
                                                    	else
                                                    	{
                                                    		System.out.println("enter into:other month:");
                                                    		int mn=txtCB_Month+1;
                                                    		
                                                    		 PreparedStatement psta=connection.prepareStatement("delete from FAS_BRS_OB where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and CASHBOOK_YEAR="+txtCB_Year+" and CASHBOOK_MONTH="+mn+" and ACCOUNT_NO="+cmbBankAccNo);
                       				            	   		psta.executeUpdate();
                                                    		
                                                        	ps3=connection.prepareStatement("insert into FAS_BRS_OB (ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,OB_TYPE,ACCOUNT_NO,OB_PART1,OB_PART2A,OB_PART2B,UPDATED_BY_USER_ID,UPDATED_DATE,BANK_ID,BRANCH_ID) values (?,?,?,?,?,?,?,?,?,?,?,?,?)");
                                                        	ps3.setInt(1, cmbAcc_UnitCode);
                                                        	ps3.setInt(2, cmbOffice_code);
                                                        	ps3.setInt(3, txtCB_Year);
                                                        	ps3.setInt(4,mn);//month
                                                        	ps3.setString(5,"T");
                                                        	ps3.setLong(6, cmbBankAccNo);
                                                        /*// 
                                                         * 	changed on 05 Nov 2015 for vasanthi mam guide
                                                         * 
                                                        	if(!Mode_val.equalsIgnoreCase("OPR"))
                                                        	{
                                                        		ps3.setDouble(7,col_part1);
                                                            	ps3.setDouble(8,0);
                                                            	ps3.setDouble(9,0);
                                                        	}
                                                        	else
                                                        	{
                                                        		ps3.setDouble(7,0);
                                                            	ps3.setDouble(8,col_part2a);
                                                            	ps3.setDouble(9,col_part2b);
                                                        	}	*/
                                                        	ps3.setDouble(7,col_part1);
                                                        	ps3.setDouble(8,col_part2a);
                                                        	ps3.setDouble(9,col_part2b);
                                                        	System.out
																	.println(col_part1+"  >> "+col_part2a+" >> "+col_part2b);
                                                        	ps3.setString(10, userid);
                                                        	ps3.setTimestamp(11, ts);
                                                        	ps3.setInt(12,bank_id);
                                                        	System.out
															.println(bank_id+"  bank_id ");
                                                        	ps3.setInt(13,branch_id);
                                                        	System.out
															.println(branch_id+"  branch_id ");
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
		}
		}		
		xml = xml + "</response>";
		out.write(xml);
		System.out.println(xml);		
	}

}
