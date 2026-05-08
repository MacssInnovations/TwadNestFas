package Servlets.FAS.FAS1.BRS.servlets;

import java.io.IOException;

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
 * Servlet implementation class brs_unFreeze_servlet
 */
public class BRS_UnFreezeMonthly_closure extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";
       
    
    public BRS_UnFreezeMonthly_closure() {
        super();
       
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
        {
	//System.out.println("tttttttttttttttttttttttttttttttttt");
	    /**
	     * Variables Declaration
	     */
	    Connection con = null;
	    Statement statement = null;
	    PreparedStatement ps3 = null,ps4=null;
	    ResultSet rs3 = null,rs4=null,rs5;
            int count_one=0;
            Date fDate=null;   int check=0,check1=0;
	    /**
	     * Database Connection
	     */
	    try {
	        ResourceBundle rs =
	            ResourceBundle.getBundle("Servlets.Security.servlets.Config");
	        String ConnectionString = "";

	        String strDriver = rs.getString("Config.DATA_BASE_DRIVER");
	        String strdsn = rs.getString("Config.DSN");
	        String strhostname = rs.getString("Config.HOST_NAME");
	        String strportno = rs.getString("Config.PORT_NUMBER");
	        String strsid = rs.getString("Config.SID");
	        String strdbusername = rs.getString("Config.USER_NAME");
	        String strdbpassword = rs.getString("Config.PASSWORD");
	        ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection

	        Class.forName(strDriver.trim());
	        con =
	    DriverManager.getConnection(ConnectionString, strdbusername.trim(),
	                         strdbpassword.trim());
	        try {
	            statement = con.createStatement();
	            con.clearWarnings();
	        } catch (SQLException e) {
	            System.out.println("Exception in creating statement:" + e);
	        }
	    } catch (Exception e) {
	        System.out.println("Exception in openeing con:" + e);
	    }


	    /**
	     * Session Checking
	     */

	    response.setContentType(CONTENT_TYPE);
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


	    /**
	     * Variables Declaration
	     */
	    int txtCB_Year = 0, txtCB_Month = 0, cmbAcc_UnitCode = 0,cmbOffice_code=0,newyr=0,newmnth=0,count2=0;
        
	    String radTB_status = "",cmbBankAccNo="";


	    /**
	     * Get Accounting Unit ID
	     */
	    try {
	        cmbAcc_UnitCode =
	                Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
	      //  cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));
	        
	        
	    } catch (NumberFormatException e) {
	        System.out.println("exception" + e);
	    }
	    System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);


	    txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));
	    txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
	    System.out.println("year..." + txtCB_Year);
	    System.out.println("Month..." + txtCB_Month);
	    cmbBankAccNo = request.getParameter("cmbBankAccNo");
	    System.out.println("cmbBankAccNo..." + cmbBankAccNo);
	   // Long bankacno=Long.parseLong(cmbBankAccNo);
	    String userid = (String)session.getAttribute("UserId");


	    
	    long l = System.currentTimeMillis();
	    Timestamp ts = new Timestamp(l);
	    
	    
	    try {
          	 
            if(txtCB_Month==12){
            	newyr=txtCB_Year+1;
            	newmnth=1;
            }else{
            	newyr=txtCB_Year;
            	newmnth=txtCB_Month+1;
            }
            System.out.println("newyr==>"+newyr);
            System.out.println("newmnth==>"+newmnth);
            
            
        	PreparedStatement ps=null;
            con.setAutoCommit(false);
            ps =con.prepareStatement("Select 'X' From FAS_BRS_MONTHLY_CLOSURE Where cashbook_year>=? and cashbook_month>=? and ACCOUNTING_UNIT_ID=? and ACCOUNT_NO=? ");
            ps.setInt(1, newyr);
            ps.setInt(2, newmnth);
            ps.setInt(3, cmbAcc_UnitCode);
            ps.setString(4, cmbBankAccNo);
            
            rs3 = ps.executeQuery();
            if (rs3.next()) {
                System.out.println("next month is unfreezed ,First unfreeze that  ");
                count2++;
            }
            if (count2 > 0) {
                sendMessage(response,
                            "Cannot Unfreeze BRS for this month.......   First  Unfreeze BRS for the subsequent months ",
                            "ok");
                return;
            }

        } catch (Exception e) {
            System.out.println("Error in unfreeze status ********* " + e);
        }

	    
            try{
                ps4 = con.prepareStatement("select * from FAS_BRS_MONTHLY_CLOSURE where cashbook_year=?  and  cashbook_month= ? and ACCOUNTING_UNIT_ID=? and ACCOUNT_NO=?");
                ps4.setInt(1, txtCB_Year);
                ps4.setInt(2, txtCB_Month);
                ps4.setInt(3, cmbAcc_UnitCode);
                ps4.setString(4, cmbBankAccNo);System.out.println("cmbBankAccNo  "+cmbBankAccNo);
                rs4 = ps4.executeQuery();
             //   System.out.println("success1***********************************************************************");
                if (rs4.next()) {
                    System.out.println("FAS_BRS_MONTHLY_CLOSURE have records,unfreeze brs first::: ");
                    fDate=rs4.getDate("FREEZED_DATE");
                    System.out.println("FAS_BRS_MONTHLY_CLOSURE have records,unfreeze brs first::: "+fDate);
                    count_one++;
                }
                
            }
            catch(Exception eee) {
                System.out.println("exception::::::::"+eee);
            }
            
            System.out.println("count_one===>"+count_one);
            if(count_one==0) {
                sendMessage(response, "Sorry !  You can't unFreeze BRS . BRS Monthly Closure is not Freezed","ok");
                return;
            }
            else
            {
	    int count_1 = 0;
	   
	    int part1=0,part2a=0,part2b=0,part2c=0;
	    
	    
	    try {

	        /** Variables Declaration */
	        PreparedStatement ps = null;
	        PreparedStatement ps1=null,ps2 = null;
	        String msg = " ";
	        con.setAutoCommit(false);

	        ps = con.prepareStatement("delete from FAS_BRS_MONTHLY_CLOSURE where  ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and ACCOUNT_NO=? ");
	        ps.setInt(1, cmbAcc_UnitCode);
	        ps.setInt(2, txtCB_Year);
	        ps.setInt(3, txtCB_Month);
	        ps.setString(4, cmbBankAccNo);
	       int kk= ps.executeUpdate();
	       System.out.println("kk::::"+kk);   
               if(kk>0) {
                   ps.close();
                   ps2 = con.prepareStatement("insert into FAS_BRS_MONTHLY_CLOSURE_LOG ( ACCOUNTING_UNIT_ID, ACCOUNTING_FOR_OFFICE_ID, CASHBOOK_YEAR,  CASHBOOK_MONTH , BRS_STATUS, BRS_UNFREEZE_DATE,  UPDATED_BY_USER_ID , UPDATED_DATE,ACCOUNT_NO,BRS_FREEZE_DATE ) values(?,?,?,?,?,?,?,?,?,?)");
                   
                   ps2.setInt(1, cmbAcc_UnitCode);
                   ps2.setInt(2, 0);
                   ps2.setInt(3, txtCB_Year);
                   ps2.setInt(4, txtCB_Month);
                   ps2.setString(5, "Y");
                   ps2.setTimestamp(6, ts);
                   ps2.setString(7, userid);
                   ps2.setTimestamp(8, ts);
                   ps2.setString(9, cmbBankAccNo);
                   System.out.println("fDate::::"+fDate);
                   ps2.setDate(10, fDate);
                   
                   
                  int ss= ps2.executeUpdate();
                  System.out.println("ss::::"+ss);   
               if(ss>0){
            	   Long acono=Long.parseLong(cmbBankAccNo);
            	   int upd=0;
            	 /*  if(txtCB_Month==1) {
            		  
                       int ttyear=txtCB_Month;
                       System.out.println("ttyear::::::::"+ttyear);
                       ps2 = con.prepareStatement("update BRS_START_MONTH_AND_YEAR set CASHBOOK_YEAR=?,CASHBOOK_MONTH=?,UPDATED_DATE=?  where ACCOUNTING_UNIT_ID=? and ACCOUNT_NO=?");
                       ps2.setInt(1, txtCB_Year);
                       ps2.setInt(2, txtCB_Month);
                       ps2.setTimestamp(3, ts);
                       ps2.setInt(4, cmbAcc_UnitCode);
                      // ps2.setInt(5, cmbOffice_code);
                       ps2.setLong(5, acono);
                       upd = ps2.executeUpdate();
                       if(upd>0) {
                          // xml = xml + "<flag>success</flag>";
                    	   System.out.println("update in BRS_START_MONTH_AND_YEAR");
                       }else{
                    	   System.out.println("if---else ---not update------update in BRS_START_MONTH_AND_YEAR");
                       }
                   }*/
                   //else {
            	   int up_month=0,up_year=0;
            	   if(txtCB_Month==1)
            	   {
            		   up_month=12;
            			 up_year=	txtCB_Year-1;  
            	   }else{
            		   up_month=txtCB_Month-1;
    				   up_year=	txtCB_Year;   
            	   }
                       System.out.println("else test Month :::"+up_month);
                       System.out.println("else test YEar :::"+up_year);
                       int tt=txtCB_Month;
                       int pre_yr=0,pre_mon=0;
                       
                       System.out.println("tt::::::::"+tt);
                   //    String sqqq="update BRS_START_MONTH_AND_YEAR set CASHBOOK_MONTH="+tt+",UPDATED_DATE="+ts+" where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and CASHBOOK_YEAR="+txtCB_Year+" and ACCOUNT_NO="+acono;
                       
                       ps1=con.prepareStatement("SELECT CASHBOOK_YEAR as pre_yr,\n" + 
                       		"  CASHBOOK_MONTH as pre_mon\n" + 
                       		"FROM BRS_START_MONTH_AND_YEAR\n" + 
                       		"WHERE ACCOUNTING_UNIT_ID    =?\n" + 
                       		"and ACCOUNT_NO=?");
                       ps1.setInt(1, cmbAcc_UnitCode);
                       ps1.setLong(2, acono);
                       rs5 = ps1.executeQuery();
                       if(rs5.next())
                       {
                    	   pre_yr=rs5.getInt("pre_yr");
                    	   pre_mon=rs5.getInt("pre_mon");
                    	   
                    	   System.out.println("previous year===>"+pre_yr);
                    	   System.out.println("previous Month===>"+pre_mon);
                    	   
                    	   PreparedStatement ps_BRobCol=con.prepareStatement("delete from FAS_BRS_PART1 where ACCOUNTING_UNIT_ID=?  and PASS_SHEET_YEAR=? and PASS_SHEET_MONTH=? and ACCOUNT_NO=?");
                    	   ps_BRobCol.setInt(1, cmbAcc_UnitCode);
                    	//   ps_BRob1.setInt(2, cmbOffice_code);
                    	   ps_BRobCol.setInt(2, pre_yr);
                    	   ps_BRobCol.setInt(3, pre_mon);
                    	   ps_BRobCol.setLong(4, acono);
                    	   
                    	   PreparedStatement ps_BRob1=con.prepareStatement("delete from FAS_BRS_PART_2A where ACCOUNTING_UNIT_ID=?  and PASS_SHEET_YEAR=? and PASS_SHEET_MONTH=? and ACCOUNT_NO=?");
                    	   ps_BRob1.setInt(1, cmbAcc_UnitCode);
                    	//   ps_BRob1.setInt(2, cmbOffice_code);
                    	   ps_BRob1.setInt(2, pre_yr);
                    	   ps_BRob1.setInt(3, pre_mon);
                    	   ps_BRob1.setLong(4, acono);
                    	   
                    	   PreparedStatement ps_BRob2=con.prepareStatement("delete from FAS_BRS_PART_2B where ACCOUNTING_UNIT_ID=?  and PASS_SHEET_YEAR=? and PASS_SHEET_MONTH=? and ACCOUNT_NO=?");
                    	   ps_BRob2.setInt(1, cmbAcc_UnitCode);
                    	//   ps_BRob2.setInt(2, cmbOffice_code);
                    	   ps_BRob2.setInt(2, pre_yr);
                    	   ps_BRob2.setInt(3, pre_mon);
                    	   ps_BRob2.setLong(4, acono);
                    	   
                    	   PreparedStatement ps_BRob3=con.prepareStatement("delete from FAS_BRS_PART_2C where ACCOUNTING_UNIT_ID=?  and PASS_SHEET_YEAR=? and PASS_SHEET_MONTH=? and ACCOUNT_NO=?");
                    	   ps_BRob3.setInt(1, cmbAcc_UnitCode);
                    	//   ps_BRob3.setInt(2, cmbOffice_code);
                    	   ps_BRob3.setInt(2, pre_yr);
                    	   ps_BRob3.setInt(3, pre_mon);
                    	   ps_BRob3.setLong(4, acono); 
                    	   
                    	   PreparedStatement ps_cn_col=con.prepareStatement("select * from FAS_BRS_PART1 where ACCOUNTING_UNIT_ID=?  and PASS_SHEET_YEAR=? and PASS_SHEET_MONTH=? and ACCOUNT_NO=?");
                   	    ps_cn_col.setInt(1, cmbAcc_UnitCode);
                   	    ps_cn_col.setInt(2, pre_yr);
                   	    ps_cn_col.setInt(3, pre_mon);
                   	    ps_cn_col.setLong(4, acono);
                   	    ResultSet rs_cn1_col=ps_cn_col.executeQuery();
                   	
                   	   if(rs_cn1_col.next()){
                   		   part1=ps_BRobCol.executeUpdate();
                   	    if(part1>0){
                   	    	check1=check1+0;
                   		
                   		    System.out.println("part1..."+part1);
                   	   }else{
                   		check1=check1+1;  System.out.println("part1 check..."+check);
                   	   }
                   	   }else{
                   		check1=check1+0;  System.out.println("part1 check..."+check);
                   	   }
                   	 
                   	    // part 2A
                   	    
                   	    PreparedStatement ps_cn1=con.prepareStatement("select * from FAS_BRS_PART_2A where ACCOUNTING_UNIT_ID=?  and PASS_SHEET_YEAR=? and PASS_SHEET_MONTH=? and ACCOUNT_NO=?");
                   	    ps_cn1.setInt(1, cmbAcc_UnitCode);
                   	    ps_cn1.setInt(2, pre_yr);
                   	    ps_cn1.setInt(3, pre_mon);
                   	    ps_cn1.setLong(4, acono);
                   	    ResultSet rs_cn1=ps_cn1.executeQuery();
                   	
                   	   if(rs_cn1.next()){
                   		   part2a=ps_BRob1.executeUpdate();
                   	    if(part2a>0){
                   	    	check1=check1+0;
                   		
                   		    System.out.println("part2a..."+part2a);
                   	   }else{
                   		check1=check1+1;  System.out.println("part2a check..."+check);
                   	   }
                   	   }else{
                   		check1=check1+0;  System.out.println("part2a check..."+check);
                   	   }
                   	    
                   	    // part 2B
                   	    
                   	    PreparedStatement ps_cn2=con.prepareStatement("select * from FAS_BRS_PART_2B where ACCOUNTING_UNIT_ID=?  and PASS_SHEET_YEAR=? and PASS_SHEET_MONTH=? and ACCOUNT_NO=?");
                   	    ps_cn2.setInt(1, cmbAcc_UnitCode);
                   	    ps_cn2.setInt(2, pre_yr);
                   	    ps_cn2.setInt(3, pre_mon);
                   	    ps_cn2.setLong(4, acono);
                   	    ResultSet rs_cn2=ps_cn2.executeQuery();
                   	
                   	   if(rs_cn2.next()){
                   		    part2b=ps_BRob2.executeUpdate();
                   	    if(part2b>0){
                   	    	check1=check1+0;
                   		
                   		    System.out.println("part2b..."+part2b);
                   	    }else{
                   	    	check1=check1+1; System.out.println("part2b check..."+check);
                    	   }
                    	   }else{
                    		   check1=check1+0; System.out.println("part2b check..."+check);
                    	   }
                   	    // part 2C temporary comment joan 30 Mar 2015
                   	    
                   	   PreparedStatement ps_cn3=con.prepareStatement("select * from FAS_BRS_PART_2C where ACCOUNTING_UNIT_ID=?  and PASS_SHEET_YEAR=? and PASS_SHEET_MONTH=? and ACCOUNT_NO=?");
                   	    ps_cn3.setInt(1, cmbAcc_UnitCode);
                   	    ps_cn3.setInt(2, pre_yr);
                   	    ps_cn3.setInt(3, pre_mon);
                   	    ps_cn3.setLong(4, acono);
                   	    ResultSet rs_cn3=ps_cn3.executeQuery();
                   	
                   	   if(rs_cn3.next()){
                   		   part2c=ps_BRob3.executeUpdate();
                   	    if(part2c>0){
                   	    	check1=check1+0;
                   		  // 
                   		    System.out.println("part2c..."+part2c);
                   	    }else{
                   	    	check1=check1+1; System.out.println("part2c check..."+check);
                    	   }
                    	   }else{
                    		   check1=check1+0; System.out.println("part2c check..."+check);
                    	   }
                    	   
                    	   
                       }
                       System.out.println("check1===>"+check1);
                       
                       
                       if(check1==0)
                       {
                       
                       ps2 = con.prepareStatement("update BRS_START_MONTH_AND_YEAR set CASHBOOK_YEAR=?,CASHBOOK_MONTH=?,UPDATED_DATE=? where ACCOUNTING_UNIT_ID=? "
                       		//+ "and CASHBOOK_YEAR=? "
                       		+ "and ACCOUNT_NO=? "
                       		//+ "AND CASHBOOK_MONTH      =?"
                       		+ " ");
                       ps2.setInt(1,txtCB_Year);
                       ps2.setInt(2, txtCB_Month);
                       ps2.setTimestamp(3, ts);
                       ps2.setInt(4, cmbAcc_UnitCode);
                     //  ps2.setInt(4, cmbOffice_code);
                     //  ps2.setInt(5, txtCB_Year);
                       ps2.setLong(5, acono);
                     //  ps2.setInt(7, txtCB_Month);
                      // System.out.println("ssss "+sqqq);
                       //ps2 = con.prepareStatement(sqqq);
                       upd = ps2.executeUpdate();
                      // System.out.println("upd "+upd);
                       if(upd>0) {
                    	   System.out.println("update in BRS_START_MONTH_AND_YEAR");
                    	   
                    	   //Joan Add on 21 Jan 2015
                    	   
                    	   PreparedStatement ps_BRobCol=con.prepareStatement("delete from FAS_BRS_PART1 where ACCOUNTING_UNIT_ID=?  and PASS_SHEET_YEAR=? and PASS_SHEET_MONTH=? and ACCOUNT_NO=?");
                    	   ps_BRobCol.setInt(1, cmbAcc_UnitCode);
                    	//   ps_BRob1.setInt(2, cmbOffice_code);
                    	   ps_BRobCol.setInt(2, txtCB_Year);
                    	   ps_BRobCol.setInt(3, txtCB_Month);
                    	   ps_BRobCol.setLong(4, acono);
                    	   
                    	   PreparedStatement ps_BRob1=con.prepareStatement("delete from FAS_BRS_PART_2A where ACCOUNTING_UNIT_ID=?  and PASS_SHEET_YEAR=? and PASS_SHEET_MONTH=? and ACCOUNT_NO=?");
                    	   ps_BRob1.setInt(1, cmbAcc_UnitCode);
                    	//   ps_BRob1.setInt(2, cmbOffice_code);
                    	   ps_BRob1.setInt(2, txtCB_Year);
                    	   ps_BRob1.setInt(3, txtCB_Month);
                    	   ps_BRob1.setLong(4, acono);
                    	   
                    	   PreparedStatement ps_BRob2=con.prepareStatement("delete from FAS_BRS_PART_2B where ACCOUNTING_UNIT_ID=?  and PASS_SHEET_YEAR=? and PASS_SHEET_MONTH=? and ACCOUNT_NO=?");
                    	   ps_BRob2.setInt(1, cmbAcc_UnitCode);
                    	//   ps_BRob2.setInt(2, cmbOffice_code);
                    	   ps_BRob2.setInt(2, txtCB_Year);
                    	   ps_BRob2.setInt(3, txtCB_Month);
                    	   ps_BRob2.setLong(4, acono);
                    	   
                    	   PreparedStatement ps_BRob3=con.prepareStatement("delete from FAS_BRS_PART_2C where ACCOUNTING_UNIT_ID=?  and PASS_SHEET_YEAR=? and PASS_SHEET_MONTH=? and ACCOUNT_NO=?");
                    	   ps_BRob3.setInt(1, cmbAcc_UnitCode);
                    	//   ps_BRob3.setInt(2, cmbOffice_code);
                    	   ps_BRob3.setInt(2, txtCB_Year);
                    	   ps_BRob3.setInt(3, txtCB_Month);
                    	   ps_BRob3.setLong(4, acono);
                    	   System.out.println("cmbAcc_UnitCode >> "+cmbAcc_UnitCode);
                    	   System.out.println("cmbOffice_code >> "+cmbOffice_code);
                    	   System.out.println("txtCB_Year >> "+txtCB_Year);
                    	   System.out.println("txtCB_Month >> "+txtCB_Month);
                    	   System.out.println("acono >> "+acono);
                    	    
                    	    System.out.println("part2c..."+part2c);
                          
                    	    
                     // part 1
                    	    
                    	    PreparedStatement ps_cn_col=con.prepareStatement("select * from FAS_BRS_PART1 where ACCOUNTING_UNIT_ID=?  and PASS_SHEET_YEAR=? and PASS_SHEET_MONTH=? and ACCOUNT_NO=?");
                    	    ps_cn_col.setInt(1, cmbAcc_UnitCode);
                    	    ps_cn_col.setInt(2, txtCB_Year);
                    	    ps_cn_col.setInt(3, txtCB_Month);
                    	    ps_cn_col.setLong(4, acono);
                    	    ResultSet rs_cn1_col=ps_cn_col.executeQuery();
                    	
                    	   if(rs_cn1_col.next()){
                    		   part1=ps_BRobCol.executeUpdate();
                    	    if(part1>0){
                    	    	check=check+0;
                    		
                    		    System.out.println("part1..."+part1);
                    	   }else{
                    		   check=check+1;  System.out.println("part1 check..."+check);
                    	   }
                    	   }else{
                    		   check=check+0;  System.out.println("part1 check..."+check);
                    	   }
                    	    
                         
                    	    
                    	    
                    	    // part 2A
                    	    
                    	    PreparedStatement ps_cn1=con.prepareStatement("select * from FAS_BRS_PART_2A where ACCOUNTING_UNIT_ID=?  and PASS_SHEET_YEAR=? and PASS_SHEET_MONTH=? and ACCOUNT_NO=?");
                    	    ps_cn1.setInt(1, cmbAcc_UnitCode);
                    	    ps_cn1.setInt(2, txtCB_Year);
                    	    ps_cn1.setInt(3, txtCB_Month);
                    	    ps_cn1.setLong(4, acono);
                    	    ResultSet rs_cn1=ps_cn1.executeQuery();
                    	
                    	   if(rs_cn1.next()){
                    		   part2a=ps_BRob1.executeUpdate();
                    	    if(part2a>0){
                    	    	check=check+0;
                    		
                    		    System.out.println("part2a..."+part2a);
                    	   }else{
                    		   check=check+1;  System.out.println("part2a check..."+check);
                    	   }
                    	   }else{
                    		   check=check+0;  System.out.println("part2a check..."+check);
                    	   }
                    	    
                    	    // part 2B
                    	    
                    	    PreparedStatement ps_cn2=con.prepareStatement("select * from FAS_BRS_PART_2B where ACCOUNTING_UNIT_ID=?  and PASS_SHEET_YEAR=? and PASS_SHEET_MONTH=? and ACCOUNT_NO=?");
                    	    ps_cn2.setInt(1, cmbAcc_UnitCode);
                    	    ps_cn2.setInt(2, txtCB_Year);
                    	    ps_cn2.setInt(3, txtCB_Month);
                    	    ps_cn2.setLong(4, acono);
                    	    ResultSet rs_cn2=ps_cn2.executeQuery();
                    	
                    	   if(rs_cn2.next()){
                    		    part2b=ps_BRob2.executeUpdate();
                    	    if(part2b>0){
                    	    	check=check+0;
                    		
                    		    System.out.println("part2b..."+part2b);
                    	    }else{
                     		   check=check+1; System.out.println("part2b check..."+check);
                     	   }
                     	   }else{
                     		   check=check+0; System.out.println("part2b check..."+check);
                     	   }
                    	    // part 2C temporary comment joan 30 Mar 2015
                    	    
                    	   PreparedStatement ps_cn3=con.prepareStatement("select * from FAS_BRS_PART_2C where ACCOUNTING_UNIT_ID=?  and PASS_SHEET_YEAR=? and PASS_SHEET_MONTH=? and ACCOUNT_NO=?");
                    	    ps_cn3.setInt(1, cmbAcc_UnitCode);
                    	    ps_cn3.setInt(2, txtCB_Year);
                    	    ps_cn3.setInt(3, txtCB_Month);
                    	    ps_cn3.setLong(4, acono);
                    	    ResultSet rs_cn3=ps_cn3.executeQuery();
                    	
                    	   if(rs_cn3.next()){
                    		   part2c=ps_BRob3.executeUpdate();
                    	    if(part2c>0){
                    	    	check=check+0;
                    		  // 
                    		    System.out.println("part2c..."+part2c);
                    	    }else{
                     		   check=check+1; System.out.println("part2c check..."+check);
                     	   }
                     	   }else{
                     		   check=check+0; System.out.println("part2c check..."+check);
                     	   }
                    	    
                    	    
                    	    
                    	   System.out.println("final check..."+check); 
                    	    
                    	    if(check==0){
                    	    	con.commit();
                                con.setAutoCommit(true);
                                msg = "BRS Froze Status is Removed...  ";
                                msg = msg + "<br><br>";
                                sendMessage(response, msg, "ok");	
return;
                    	    }
                    	    else{
                    		   String msgg = "UnFreeze BRS is Unsuccessful.......";
                               msgg = msgg + "<br><br>";
                               sendMessage(response, msgg, "ok");
                               return;
                    	   }
                    	 
                    	 /*   
                    	    if(part2b>0){
             				   part2a=ps_BRob1.executeUpdate();
             				   System.out.println("part2a..."+part2a);
             				   if(part2a>0){
             					   System.out.println("welcome...");
             	                   con.commit();
             	                   con.setAutoCommit(true);
             	                   msg = "BRS Froze Status is Removed...  ";
             	                   msg = msg + "<br><br>";
             	                   sendMessage(response, msg, "ok");
             				   }else{
             					   String msgg = "UnFreeze BRS is Unsuccessful.......";
             	                   msgg = msgg + "<br><br>";
             	                   sendMessage(response, msgg, "ok");
             				   }
             			   }else{
             				   String msgg = "UnFreeze BRS is Unsuccessful.......";
                                msgg = msgg + "<br><br>";
                                sendMessage(response, msgg, "ok");
             			   }
                    	    }   */
                    	   
                          // xml = xml + "<flag>success</flag>";
                       }else{
                    	   System.out.println("else ---else---not update------update in BRS_START_MONTH_AND_YEAR");
                       }
                     
                   }
            	   System.out.println("welcome11111...");
                  con.commit();
                   con.setAutoCommit(true);
                   msg = "BRS Froze Status is Removed...  ";
                   msg = msg + "<br><br>";
                   sendMessage(response, msg, "ok");
                   return;
               }
               else 
               {
                   try {
                       con.rollback();
                   } catch (SQLException e1) {
                       System.out.println("exception in rollback" + e1);
                   }
                   String msgg = "UnFreeze BRS is Unsuccessful.......";
                   msgg = msgg + "<br><br>";
                   sendMessage(response, msgg, "ok");
                   return;
               }
               }
               else {
                   try {
                       con.rollback();
                   } catch (SQLException e1) {
                       System.out.println("exception in rollback" + e1);
                   }
                   String msgg = "UnFreeze BRS is Unsuccessful.......";
                   msgg = msgg + "<br><br>";
                   sendMessage(response, msgg, "ok");
                   return;
               }
	       
	    } catch (Exception e) {
	        try {
	            con.rollback();
	        } catch (SQLException e1) {
	            System.out.println("exception in rollback" + e1);
	        }
	        System.out.println("Exception in unfreeze " + e);
	        String msg =
	            "UnFreeze BRS is Unsuccessful.......";
	        msg = msg + "<br><br>";
	        sendMessage(response, msg, "ok");
	        return;

	    }
	    finally
	    {
	       
	        try{con.setAutoCommit(true);  }catch(SQLException sqle){}
	    }

	}    
        	
	}
    private void sendMessage(HttpServletResponse response, String msg,
                             String bType) {
        try {
            String url =
                "org/Library/jsps/Messenger.jsp?message=" + msg + "&button=" +
                bType;
            response.sendRedirect(url);
            return;
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
