package Servlets.FAS.FAS1.MonthEnd.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



/**
 * Servlet implementation class BRS_status
 */
public class BRS_status extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	    private static final String DOC_TYPE = null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BRS_status() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 Connection con=null;
   	 
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

              ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
               Class.forName(strDriver.trim());
               con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
    }
    catch(Exception e)
    {
      System.out.println("Exception in connection...."+e);
    }
	        ResultSet rs=null,rs1=null,rs2=null,rs3=null;
	        CallableStatement cs=null;
	        PreparedStatement ps=null,ps1=null,ps2=null,ps3=null;
	        String xml="";
	   	   
	        response.setContentType(CONTENT_TYPE);
	        PrintWriter out = response.getWriter();
	        response.setHeader("Cache-Control","no-cache");  
	        HttpSession session=request.getSession(false);
	        try
	        {
	                if(session==null)
	                {
	                        xml="<response><command>sessionout</command><flag>sessionout</flag></response>";
	                        out.println(xml);
	                        System.out.println(xml);
	                        out.close();
	                        return;

	                    }
	                    //System.out.println(session);

	        }catch(Exception e)
	        {
	                System.out.println("Redirect Error :"+e);
	        }
	        System.out.println("java");
	        String command;
	        command = request.getParameter("command");
	        
	        session =request.getSession(false);
	        String updatedby=(String)session.getAttribute("UserId");
	        long l=System.currentTimeMillis();
	        java.sql.Timestamp ts=new java.sql.Timestamp(l);
	           
	            System.out.println("command"+command);
	            if(command.equalsIgnoreCase("add")) {
	            	int onlinetxtCB_Year=0;
	            	int onlinetxtCB_Month=0;
	            	String cmbBankAccNo="";
	            xml="<response><command>Add</command>";            
		            int acUnit = Integer.parseInt(request.getParameter("acc_unit").trim());
		            int officeId=Integer.parseInt(request.getParameter("office_id").trim());
		            int brscb_year=Integer.parseInt(request.getParameter("brscb_year"));
                    int brscb_month1=Integer.parseInt(request.getParameter("brscb_month1"));
                    int tbcb_year=Integer.parseInt(request.getParameter("tbcb_year"));
                    int tbcb_month1=Integer.parseInt(request.getParameter("tbcb_month1"));
                    cmbBankAccNo=request.getParameter("cmbBankAccNo");
                    System.out.println("cmbBankAccNo:"+cmbBankAccNo);
                    String rabtn=request.getParameter("radiobtn");
                    System.out.println("rabtn:"+rabtn);
                    if(rabtn.equals("yes")){
                    onlinetxtCB_Year=Integer.parseInt(request.getParameter("onlinetxtCB_Year"));
                    onlinetxtCB_Month=Integer.parseInt(request.getParameter("onlinetxtCB_Month"));
                     
                    }
                    else
                    {
                    	onlinetxtCB_Year=0;
                    	onlinetxtCB_Month=0;
                    }
                         System.out.println("tbcb_month1::::"+tbcb_month1);
                          System.out.println("tbcb_year::::::::"+tbcb_year);
                    System.out.println("acUnit::::"+acUnit);
               System.out.println("officeId::::::::"+officeId);
		            boolean isInsert = false;
		            int count = 0;String tb_status="";Date tb_date=null;;int count1=0; 
		            try{               	              		                  		                          
	                    xml=xml+"<flag>success</flag>";
	                    
	                    ps=con.prepareStatement("select tb_status,tb_date from fas_trial_balance_status where accounting_unit_id=? " + 
	                    " and cashbook_year=? " + 
	                    " and CASHBOOK_MONTH=? ");
	                    ps.setInt(1,acUnit);
                            ps.setInt(2,tbcb_year);
                            ps.setInt(3,tbcb_month1);
		                rs = ps.executeQuery();
		                if(rs.next()){
		                	isInsert = true;
                                        tb_status=rs.getString("tb_status");
                                        tb_date=rs.getDate("tb_date");
                                        System.out.println("tb month and date from fas_trial balance status table ::::::::::"+tb_status+":::::::::"+tb_date);
		                }else{
		                	isInsert = false;
                                        tb_status="N";
                                        tb_date=null;
		                }
		                
		                
		                
		                
		                
		                
		                
		                
		                if(isInsert){
                                System.out.println("if tb freezed status and date exists.....");
                                ps1=con.prepareStatement("select * from BRS_STATUS_UPDATE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and" +
                                " tb_year=? and tb_month=? and ACCOUNT_NO=?");
		                    ps1.setInt(1,acUnit);
		                    ps1.setInt(2,officeId);
		                    ps1.setInt(3,tbcb_year);
		                    ps1.setInt(4,tbcb_month1);
		                    ps2.setString(5,cmbBankAccNo);
		                   // ps1.setInt(5,cmbBankAccNo);
                                    rs1=ps1.executeQuery();
                                    if(rs1.next())
                                    {
                                        xml=xml+"<data>already</data>";
                                    }
                                    else
                                    {
                                        xml=xml+"<data>nodata</data>";
                                    
		                	ps=con.prepareStatement("insert into BRS_STATUS_UPDATE(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,tb_year, " + 
		                	"tb_month," + 
		                	"tb_freeze_date," + 
		                	"tb_freeze," + 
		                	"brs_year," + 
		                	"brs_month," + 
		                	"UPDATED_BY_USERID,UPDATED_DATE,BRS_ONLINE_YEAR,BRS_ONLINE_MONTH,ACCOUNT_NO) values(?,?,?,?,to_date(?,'dd-mm-yyyy'),?,?,?,?,?,?,?,?)");
			                ps.setInt(1,acUnit);
			                ps.setInt(2,officeId);
			                ps.setInt(3,tbcb_year);
			                ps.setInt(4,tbcb_month1);
                                        ps.setDate(5,tb_date);
                                        ps.setString(6,tb_status);
                                        ps.setInt(7,brscb_year);
                                        ps.setInt(8,brscb_month1);
                                        ps.setString(9,updatedby);
                                        ps.setTimestamp(10,ts);
                                        if(rabtn.equals("yes"))
                                        {
                                        ps.setInt(11,onlinetxtCB_Year);
                                        ps.setInt(12,onlinetxtCB_Month);
                                        }
                                        else
                                        {
                                        	   ps.setInt(11,onlinetxtCB_Year);
                                               ps.setInt(12,onlinetxtCB_Month);	
                                        }
                                        ps.setString(13,cmbBankAccNo);	
                                        count = ps.executeUpdate();
                                        System.out.println("count*******"+count);
                                    }
                                    }
                                    else if(!isInsert)
                                    {
                                            System.out.println("data for the tb_status No and tb_date is null");
                                            ps2=con.prepareStatement("select * from BRS_STATUS_UPDATE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and" +
                                            " tb_year=? and tb_month::numeric=? and ACCOUNT_NO=?");
                                            ps2.setInt(1,acUnit);
                                            ps2.setInt(2,officeId);
                                            ps2.setInt(3,tbcb_year);
                                            ps2.setInt(4,tbcb_month1);
                                            ps2.setString(5,cmbBankAccNo);
                                            rs2=ps2.executeQuery();
                                            if(rs2.next())
                                            {
                                            	count1=0;
                                                xml=xml+"<data>already</data>";
                                            }
                                            else
                                            {
                                                xml=xml+"<data>nodata</data>";
                                            
                                                ps3=con.prepareStatement("insert into BRS_STATUS_UPDATE(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,tb_year, " + 
                                                "tb_month," + 
                                                "tb_freeze_date," + 
                                                "tb_freeze," + 
                                                "brs_year," + 
                                                "brs_month," + 
                                                //"UPDATED_BY_USERID,UPDATED_DATE,ACCOUNT_NO) values(?,?,?,?,(?,'dd-mm-yyyy'),?,?,?,?,?,?)");
                                                "UPDATED_BY_USERID,UPDATED_DATE,ACCOUNT_NO) values(?,?,?,?,?,?,?,?,?,?,?)");
                                                ps3.setInt(1,acUnit);
                                                ps3.setInt(2,officeId);
                                                ps3.setInt(3,tbcb_year);
                                                ps3.setInt(4,tbcb_month1);
                                                ps3.setDate(5,tb_date);
                                                ps3.setString(6,tb_status);
                                                ps3.setInt(7,brscb_year);
                                                ps3.setInt(8,brscb_month1);
                                                ps3.setString(9,updatedby);
                                                ps3.setTimestamp(10,ts);
                                                ps3.setString(11,cmbBankAccNo);	
                                                count1 = ps3.executeUpdate();
                                                System.out.println("count1*******"+count1);
                                            }
                                    
                                    }
                                    System.out.println("isInsert value *********"+!isInsert);
		                if(count>0 && isInsert){
		                	xml=xml+"<status>success</status>";
		                }else if(count==0 && isInsert){
		                	xml=xml+"<status>already</status>";
		                }else if(count1>0 && !isInsert){
		                	xml=xml+"<status>success</status>";
		                }else if(count1==0 && !isInsert){
		                	xml=xml+"<status>already</status>";
		                }
                                else{
		                	xml=xml+"<status>failure</status>";
		                }
	           }catch(SQLException e) {
	                //xml=xml+"<flag>failure</flag>";
	                e.printStackTrace();
	            }
	            xml=xml+"</response>";
                    //out.println(xml);
	            System.out.println(xml);
	        }
	            else if(command.equalsIgnoreCase("closureCheck")) 
                {
	            	int tablemn=0,tableyr=0,accNo=0,inc_count=0;
                    xml="<response><command>closureCheck</command>";
                    int acUnit = Integer.parseInt(request.getParameter("acc_unit"));
                    int office_id = Integer.parseInt(request.getParameter("office_id"));
                    int onlinetxtCB_Year=Integer.parseInt(request.getParameter("onlinetxtCB_Year"));
                    int onlinetxtCB_Month=Integer.parseInt(request.getParameter("onlinetxtCB_Month"));
                    int cmbBankAccNo=Integer.parseInt(request.getParameter("cmbBankAccNo"));
                    System.out.println("cmbBankAccNo:"+cmbBankAccNo);
                    try{
                    
                    	
                    	
                        ps=con.prepareStatement("Select Max(Cashbook_Year)as tableyr,Max(Cashbook_Month)as tablemn,ACCOUNT_NO " +
                        " FROM Fas_Brs_Monthly_Closure WHERE Accounting_Unit_Id    ="+acUnit+" And Accounting_For_Office_Id= "+office_id+
                        " And Cashbook_Year=(Select Max(Cashbook_Year) As Cbyr FROM Fas_Brs_Monthly_Closure " +
                        " Where Accounting_Unit_Id    ="+acUnit+" And Accounting_For_Office_Id="+office_id+") AND ACCOUNT_NO ="+cmbBankAccNo+"group by ACCOUNT_NO"); 

                        System.out.println("Select Max(Cashbook_Year)as tableyr,Max(Cashbook_Month)as tablemn,ACCOUNT_NO " +
                        " FROM Fas_Brs_Monthly_Closure WHERE Accounting_Unit_Id    ="+acUnit+" And Accounting_For_Office_Id= "+office_id+
                        " And Cashbook_Year=(Select Max(Cashbook_Year) As Cbyr FROM Fas_Brs_Monthly_Closure " +
                        " Where Accounting_Unit_Id    ="+acUnit+" And Accounting_For_Office_Id="+office_id+") AND ACCOUNT_NO ="+cmbBankAccNo+"group by ACCOUNT_NO");
                        
                        
//                    	 ps=con.prepareStatement("Select Max(Cashbook_Year)as tableyr,Max(Cashbook_Month)as tablemn" +
//                                 " FROM Fas_Brs_Monthly_Closure WHERE Accounting_Unit_Id    ="+acUnit+" And Accounting_For_Office_Id= "+office_id+
//                                 " And Cashbook_Year=(Select Max(Cashbook_Year) As Cbyr FROM Fas_Brs_Monthly_Closure " +
//                                 " Where Accounting_Unit_Id    ="+acUnit+" And Accounting_For_Office_Id="+office_id+")");
                    	
                        rs3=ps.executeQuery();
                        if(rs3.next()) {
                        	 xml=xml+"<flag>success</flag>";
                        	tableyr=rs3.getInt("tableyr");
                        	tablemn=rs3.getInt("tablemn");
                        	accNo=rs3.getInt("ACCOUNT_NO");
                           inc_count++;
                           
                           
                           System.out.println("onlinetxtCB_Year==>"+onlinetxtCB_Year);
                      		System.out.println("tableyr==>"+tableyr);
                           System.out.println("onlinetxtCB_Month==>"+onlinetxtCB_Month);
                      		System.out.println("Month==>"+tablemn);
                           
		                       	if(onlinetxtCB_Year==tableyr)
		                       	{
		                       		xml=xml+"<equalyr>yes</equalyr>";
		                       		
		                       		
		                       		
		                       		if(onlinetxtCB_Month==tablemn)
		                       		{
		                       			xml=xml+"<equalmn>yes</equalmn>";	
		                       		}
		                       		else
		                       		{
		                       			xml=xml+"<equalmn>no</equalmn>";	
		                       		}
		                       		
		                       	}
		                       	else
		                       	{
		                       		xml=xml+"<equalyr>no</equalyr>";
		                       	}
                        }
                        else 
                        //(inc_count==0)
                        {
                        	xml=xml+"<flag>failure</flag>";
                        	 
                        }
                       
                        
                    }
                    
                    catch(SQLException e) {
                    xml=xml+"<flag>failure</flag>";
                    e.printStackTrace();
                    }
                    xml=xml+"</response>";
                    System.out.println(xml);
                }
	            
	           
                    else if(command.equalsIgnoreCase("getMax")) 
                    {
                        xml="<response><command>getMax</command>";
                        int acUnit = Integer.parseInt(request.getParameter("acc_unit"));
                      //  int TBtxtCB_Year=Integer.parseInt(request.getParameter("TBtxtCB_Year"));
                        try{
                        
                            xml=xml+"<flag>success</flag>";
                            
                            int open_year=0,open_month=0; 
                            int year=0,month=0;
                            ResultSet rs11=null;
                            ps=con.prepareStatement("select max(CASHBOOK_YEAR) as CASHBOOK_YEAR from fas_trial_balance_status where accounting_unit_id=?");
            	                    ps.setInt(1,acUnit);
              		                rs = ps.executeQuery();
              		               
              		              if(rs.next()) {
              		            	year=rs.getInt("CASHBOOK_YEAR");
              		              
              		                           		              
              		            PreparedStatement   ps11=con.prepareStatement("select max(CASHBOOK_MONTH) as CASHBOOK_MONTH from fas_trial_balance_status where accounting_unit_id=? and CASHBOOK_YEAR="+year); 
          		            	ps11.setInt(1,acUnit);
          		            	rs11 = ps11.executeQuery(); 
          		            	
              		              }
          		            	if(rs11.next()) {
          		            	month=rs11.getInt("CASHBOOK_MONTH");
          		            	
          		            	}
          		            	if(year !=0 && month !=0)
          		            	{
          		            		xml=xml+"<year>"+year+"</year>"; 
              		            	xml=xml+"<month>"+month+"</month>"; 
              		            	xml=xml+"<open_year>"+open_year+"</open_year>"; 
              		            	xml=xml+"<open_month>"+open_month+"</open_month>";
          		            	}
          		            	else
          		            	{
                            
    	                    try {
    	        				PreparedStatement ps_chk=con.prepareStatement("select to_char(DATE_OF_OPENING,'yyyy') year, to_char(DATE_OF_OPENING,'mm') month from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID="+acUnit);
    	        			ResultSet rs_chk=ps_chk.executeQuery();
    	        			if(rs_chk.next())
    	        			{
    	        			open_year=rs_chk.getInt(1)	;open_month=rs_chk.getInt(2)	;
    	        			xml=xml+"<year>"+year+"</year>"; 
      		            	xml=xml+"<month>"+month+"</month>"; 
	                    	xml=xml+"<open_year>"+open_year+"</open_year>"; 
      		            	xml=xml+"<open_month>"+open_month+"</open_month>"; 
    	        			
    	        			}
    	        			System.out.println("open_year :: "+open_year);
    	        			System.out.println("open_month :: "+open_month);
    	                	} catch (SQLException e1) {
    	        				// TODO Auto-generated catch block
    	        				e1.printStackTrace();
    	        			}
    	                    System.out.println("year-->"+year);
    	                    System.out.println("month-->"+month);
    	                   
    	                    
          		            	}
                            
                            
//                            ps=con.prepareStatement("select max(CASHBOOK_MONTH)as mn1 from fas_trial_balance_status  where ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=?");
//                            ps.setInt(1,acUnit);
//                            ps.setInt(2,TBtxtCB_Year);
//                            rs3=ps.executeQuery();
//                            if(rs3.next()) {
//                                xml=xml+"<monthmax>"+rs3.getInt("mn1")+"</monthmax>"; 
//                            }
                            
                        }
                        
                        catch(SQLException e) {
                        xml=xml+"<flag>failure</flag>";
                        e.printStackTrace();
                        }
                        xml=xml+"</response>";
                        System.out.println(xml);
                        
                        
                    }
	            else if(command.equalsIgnoreCase("update")) {
	            	 xml="<response><command>Update</command>";	           
		            int acUnit = Integer.parseInt(request.getParameter("acc_unit").trim());
		            int officeId=Integer.parseInt(request.getParameter("office_id").trim());
		            int brscb_year=Integer.parseInt(request.getParameter("brscb_year").trim());
                    int brscb_month1=Integer.parseInt(request.getParameter("brscb_month1").trim());
                    int tbcb_year=Integer.parseInt(request.getParameter("tbcb_year").trim());
		            int tbcb_month1=Integer.parseInt(request.getParameter("tbcb_month1").trim());
		            String cmbBankAccNo=request.getParameter("cmbBankAccNo");
		            try{
	                    xml=xml+"<flag>success</flag>";
	                    
	                   // to_date(?,'dd-mm-yyyy')
	                    ps=con.prepareStatement("update BRS_STATUS_UPDATE set brs_year=?,brs_month=?,UPDATED_BY_USERID=?,UPDATED_DATE=?,ACCOUNT_NO=? " +
                            " where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and tb_year=? and tb_month=? and ACCOUNT_NO=?");
		               
	                        ps.setInt(1,brscb_year);
	                        ps.setInt(2,brscb_month1);
	                        ps.setString(3,updatedby);
	                        ps.setTimestamp(4,ts);
	                        ps.setString(5,cmbBankAccNo);
	                        ps.setInt(6,acUnit);
	                        ps.setInt(7,officeId);
	                        ps.setInt(8,tbcb_year);
	                        ps.setInt(9,tbcb_month1);  
	                        ps.setString(10,cmbBankAccNo);
	                        
		                ps.executeUpdate();
	                                 
	                    }
	           
	            catch(SQLException e) {
	                xml=xml+"<flag>failure</flag>";
	                e.printStackTrace();
	            }
	            xml=xml+"</response>";
	           // System.out.println(xml);
	        }    
	            else if(command.equalsIgnoreCase("Delete")) {
                    System.out.println("sathyaaaaaaaaaaaaaaaa");
	            	 xml="<response><command>Delete</command>";	           
		            int acUnit = Integer.parseInt(request.getParameter("acc_unit").trim());
		            int officeId=Integer.parseInt(request.getParameter("office_id").trim());
                            int tbcb_year=Integer.parseInt(request.getParameter("tbcb_year").trim());
                            int tbcb_month1=Integer.parseInt(request.getParameter("tbcb_month1").trim());
                            String cmbBankAccNo=request.getParameter("cmbBankAccNo");
		           System.out.println("tbcb_month1******"+tbcb_month1);		           
		            try{
	                   // to_date(?,'dd-mm-yyyy')
	                    xml=xml+"<flag>success</flag>";
	                    ps=con.prepareStatement("delete from BRS_STATUS_UPDATE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and tb_year=? and tb_month=? and ACCOUNT_NO=?");
	                        ps.setInt(1,acUnit);
	                        ps.setInt(2,officeId);
	                        ps.setInt(3,tbcb_year);
	                        ps.setInt(4,tbcb_month1); 
	                        ps.setString(5,cmbBankAccNo); 
		                ps.executeUpdate();
	                        
	                    }
	           
	            catch(SQLException e) {
	                xml=xml+"<flag>failure</flag>";
	                e.printStackTrace();
	            }
	            xml=xml+"</response>";
	           // System.out.println(xml);
	        }    
	            
	            
	            out.write(xml);
	        //    out.println(xml);
		       	       
                    out.close();
	            
	}
}
