package Servlets.FAS.FAS1.CommonControls.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Calendar;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class Common_Bank_Account_Number_Loading extends HttpServlet {
  
    public void doGet(HttpServletRequest request, 
                      HttpServletResponse response) throws ServletException, IOException {
    	
    	
    	/**
         * Set Content Type 
         */
        PrintWriter out = response.getWriter();
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
        PreparedStatement ps2=null;        
        ResultSet rs2=null;
        int year = 0;
        int month = 0;
        int day = 0;
        long accno=0;
        String cmd="";
        
        
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
                           ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection                           Class.forName(strDriver.trim());
                           con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
           }
           catch(Exception e)
           {
        	   System.out.println("Exception in opening connection :"+e);
           }
              
           int cmbAcc_UnitCode=0,bank_id=0,branch_id=0;
           long acc_no=0;
           String xml=null,opr_mode=null;   
           int count=0;
              /** Get Accouting UNit ID */
          	try
          	{
          		cmd=request.getParameter("command");
                        System.out.println("command passed ::::"+cmd);
          		cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
          	}
          	catch(Exception e)
          	{
          		System.out.println(e);
          	}
          	try
          	{
          		bank_id = Integer.parseInt(request.getParameter("bank_id"));
          	}catch(Exception e){System.out.println("Err in bank_id :: "+bank_id);}    
          	try
          	{
          		branch_id = Integer.parseInt(request.getParameter("branch_id"));
          	}catch(Exception e){System.out.println("Err in branch_id :: "+branch_id);}    
          	try
          	{
          		acc_no = Long.parseLong(request.getParameter("acc_no"));
          	}catch(Exception e){System.out.println("Err in acc_no :: "+acc_no);}    
          	try
          	{
          		opr_mode = request.getParameter("opr_mode");
          	}catch(Exception e){System.out.println("Err in opr_mode :: "+opr_mode);} 
          	 
          	xml="<response>";
            if(cmd.equalsIgnoreCase("LoadBankAccountNumber"))
            {
            System.out.println("Common_Bank_Account_Number_Loading********");
            		String sql =
            	  	"       select *             													\n"+   						
            	  	"		from 																	\n"+	
            	  	"		(																		\n"+	
            	  	"			select																\n"+
            	  	"				bank_id,														\n"+
            	  	"				BRANCH_ID,														\n"+
            	  	"				bank_ac_no, 													\n"+
            	  	"				AC_OPERATIONAL_MODE_ID,                                         \n"+
            	  	"				trim(AC_OPERATIONAL_MODE_ID)||'-'||trim(bank_ac_no::varchar) as acc_no			    \n"+  
            	  	"			from																\n"+
            	  	"				fas_mst_bank_balance												\n"+
            	  	"			where																\n"+		
            	  	"				accounting_unit_id = ? and status='Y'  										\n"+
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
			              ps2=con.prepareStatement(sql);
			              ps2.setInt(1,cmbAcc_UnitCode);
			              rs2=ps2.executeQuery();
			           
			              
			              xml=xml+"<command>LoadBankAccountNumber</command>";
			              
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
         }else if(cmd.equalsIgnoreCase("Bank_Branch_Name"))
         {
        	 try
           	{
        		 accno = Long.parseLong(request.getParameter("accno"));
        		 System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+accno);
           	}catch(Exception e){System.out.println("Err in bank_id :: "+accno);}    
           	
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
        	  	"				accounting_unit_id = ? and   										\n"+
        	  	"				BANK_AC_NO = ?   										\n"+
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
        	  	"      X.BRANCH_ID = Z.z_branch_id												\n"+
        	  	" 																			      ";
        		
         // System.out.println(sql);
	            try
	            {
		              ps2=con.prepareStatement(sql);
		              ps2.setInt(1,cmbAcc_UnitCode);
		              ps2.setLong(2,accno);
		              rs2=ps2.executeQuery();
		           
		              
		              xml=xml+"<command>Bank_Branch_Name</command>";
		              
		              /** Count How many Records are available  */
		              while (rs2.next()) 
		              {
		                 xml=xml+ "<acc_no>"+ rs2.getString("bank_ac_no") +"</acc_no>";	 
		                 xml=xml+ "<bank_id>"+ rs2.getString("bank_id") +"</bank_id>";  
		                 xml=xml+ "<branch_id>"+ rs2.getString("branch_id") +"</branch_id>";                 
		                 xml=xml+ "<opr_mode>"+ rs2.getString("AC_OPERATIONAL_MODE_ID") +"</opr_mode>";                 
		                 xml=xml+ "<acc_desc>"+ rs2.getString("acc_no") +"-"+ rs2.getString("y_bank_name") +"</acc_desc>";
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
         else if(cmd.equalsIgnoreCase("LoadOprCode"))   // this is for BRS_twad and BRS_non_twad  
         {
        	 	   xml=xml+"<command>LoadOprCode</command>";
        	 	   try
        	 	   {
		        	 	   String sql="select AC_HEAD_CODE from FAS_OFFICE_BANK_AC_CURRENT where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and " +
		        	 	   		"BANK_ID="+bank_id+" and BRANCH_ID="+branch_id+" and BANK_AC_NO="+acc_no+" and AC_OPERATIONAL_MODE_ID='"+opr_mode+"' and MODULE_ID='MF035'";
		        	 	   System.out.println(sql);
		        	 	   ps2=con.prepareStatement(sql);
		        	 	    	 	   
		        	 	   rs2=ps2.executeQuery();
		        	 	   while(rs2.next())
		        	 	   {
		        	 		   		count++;
		        	 		   		xml=xml+"<acc_head_code>"+rs2.getString("AC_HEAD_CODE")+"</acc_head_code>";
		        	 		   		
		        	 	   }
		        	 	   if(count>0)
		        	 		   xml=xml+"<flag>success</flag>";
		        	 	   else
		        	 		   xml=xml+"<flag>failure</flag>";
        	 	   }
        	 	   catch(Exception e)
        	 	   {
	        	 		   System.out.println("Err in LoadOprCode :: "+e.getMessage());
	        	 		   xml=xml+"<flag>failure</flag>";
        	 	   }
         }
         xml=xml+"</response>";
         System.out.println(xml);
         out.println(xml);
         out.close();
    }
}
