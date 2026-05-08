package Servlets.FAS.FAS1.TDA.servlets;

import Servlets.FAS.FAS1.CommonControls.servlets.Com_CashBook1;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

import java.sql.SQLException;

import java.sql.Statement;
import java.sql.Timestamp;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class TDA_Raised_Create extends HttpServlet
{
    private String CONTENT_TYPE = "text/html; charset=windows-1252";
  
    public void init(ServletConfig config) throws ServletException 
    {
        super.init(config);
      
    }
    public void doGet(HttpServletRequest request, 
            HttpServletResponse response) throws ServletException, IOException 
    {
System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~`  do get called   ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
  	
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
	      PreparedStatement ps2=null,ps1=null,ps22=null;        
	      ResultSet rs2=null,rs22=null;
	      String sql="";String sql21 = "";
	        	      
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
                    ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection                    Class.forName(strDriver.trim());
                    con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
            }
            catch(Exception e)
            {
                	System.out.println("Exception in opening connection :"+e);
            }
        
       
            int count=0,AccUnitId=0,yesCount=0;
            String xml=null,cmd="",option="";          
      
            /** Get Employee ID */
            try{cmd=request.getParameter("command");}
            catch(Exception e){System.out.println(e);}
            
            try{option=request.getParameter("Option");}
            catch(Exception e){System.out.println(e);}
            
            try{AccUnitId=Integer.parseInt(request.getParameter("txtUnitId"));}
            catch(Exception e){System.out.println(e);}
            
            xml="<response>";
            if(cmd.equalsIgnoreCase("loadTransferUnit"))
            {
                 
                    xml=xml+"<command>loadTransferUnit</command>";
                    sql="select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME " +
                    		"from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID not in(?) AND " +
                    		"ACCOUNTING_UNIT_OFFICE_ID not in(select OFFICE_ID from COM_MST_OFFICES " +
                    		"where OFFICE_STATUS_ID in('CL','RD','NC')) " +
                    		"order by ACCOUNTING_UNIT_NAME ";                    
                 //   System.out.println(" SQL :: "+sql);
                    try
                    {
                             ps2=con.prepareStatement(sql);
                             System.out.println(AccUnitId);
                             ps2.setInt(1,AccUnitId);
                             rs2=ps2.executeQuery();                                 
                             while(rs2.next()) 
                             {
                                     xml+= "<unit_id>"+ rs2.getInt("ACCOUNTING_UNIT_ID") +"</unit_id>";	 
                                     xml+= "<unit_name>"+ rs2.getString("ACCOUNTING_UNIT_NAME") +"</unit_name>";  				                		                 
                                     count++;
                             }					              
                             if(count==0)
                                     xml+="<flag>NoData</flag>";					           
                             else               
                                     xml+="<flag>success</flag>";
                                         
                    }
                    catch(Exception e) 
                    {
                             System.out.println("Exception in loadTransferUnit..."+e);
                             xml+="<flag>"+e.getMessage()+"</flag>";
                    }                      
            }
            
            
            if(cmd.equalsIgnoreCase("loadTransferUnit_TPANew"))
            {
                 
                    xml=xml+"<command>loadTransferUnit</command>";
                    sql="select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME " +
                    		"from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID not in(?) " +
                    		// "AND " +
                    		//"ACCOUNTING_UNIT_OFFICE_ID not in(select OFFICE_ID from COM_MST_OFFICES " +
                    		//"where OFFICE_STATUS_ID in('CL','RD','NC')
                    		//" ) " +
                    		" order by ACCOUNTING_UNIT_NAME ";                    
                 //   System.out.println(" SQL :: "+sql);
                    try
                    {
                             ps2=con.prepareStatement(sql);
                             System.out.println(AccUnitId);
                             ps2.setInt(1,AccUnitId);
                             rs2=ps2.executeQuery();                                 
                             while(rs2.next()) 
                             {
                                     xml+= "<unit_id>"+ rs2.getInt("ACCOUNTING_UNIT_ID") +"</unit_id>";	 
                                     xml+= "<unit_name>"+ rs2.getString("ACCOUNTING_UNIT_NAME") +"</unit_name>";  				                		                 
                                     count++;
                             }					              
                             if(count==0)
                                     xml+="<flag>NoData</flag>";					           
                             else               
                                     xml+="<flag>success</flag>";
                                         
                    }
                    catch(Exception e) 
                    {
                             System.out.println("Exception in loadTransferUnit..."+e);
                             xml+="<flag>"+e.getMessage()+"</flag>";
                    }                      
            }
            //for TPA
            
            if(cmd.equalsIgnoreCase("loadTransferUnit_tpa"))
            {
                 
                    xml=xml+"<command>loadTransferUnit_tpa</command>";
                    sql="select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME " +
                    		" from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID not in(?) AND " +
                    		"ACCOUNTING_UNIT_OFFICE_ID not in(select OFFICE_ID from COM_MST_OFFICES " +
                    		"where OFFICE_STATUS_ID in('CL','RD','NC')) " +
                    		" order by ACCOUNTING_UNIT_NAME ";                    
                 //   System.out.println(" SQL :: "+sql);
                    try
                    {
                             ps2=con.prepareStatement(sql);
                             System.out.println(AccUnitId);
                             ps2.setInt(1,AccUnitId);
                             rs2=ps2.executeQuery();                                 
                             while(rs2.next()) 
                             {
                                     xml+= "<unit_id>"+ rs2.getInt("ACCOUNTING_UNIT_ID") +"</unit_id>";	 
                                     xml+= "<unit_name>"+ rs2.getString("ACCOUNTING_UNIT_NAME") +"</unit_name>";  				                		                 
                                     count++;
                             }					              
                             if(count==0)
                                     xml+="<flag>NoData</flag>";					           
                             else               
                                     xml+="<flag>success</flag>";
                                         
                    }
                    catch(Exception e) 
                    {
                             System.out.println("Exception in loadTransferUnit..."+e);
                             xml+="<flag>"+e.getMessage()+"</flag>";
                    }                      
            }
            //testAsset
            else if(cmd.equalsIgnoreCase("testAsset"))
            {
                
                xml=xml+"<command>testAsset</command>";
                sql="Select Issued_To_Office From Fas_Issueof_Assets Where Accounting_Unit_Id      =3 And Accounting_For_Office_Id  =5000  "+
				" And Cashbook_Year             =2010 And Cashbook_Month            =6 And (Tda_Originating_Jvr_No   Is Null Or Tda_Originating_Jvr_No=0) "+
				" And Tda_Originating_Jvr_Date Is Null and Issued_To_Office=5011";               
             //   System.out.println(" SQL :: "+sql);
                try
                {
                         ps2=con.prepareStatement(sql);
                         System.out.println(AccUnitId);
                         ps2.setInt(1,AccUnitId);
                         rs2=ps2.executeQuery();                                 
                         while(rs2.next()) 
                         {
                                 xml+= "<unit_id>"+ rs2.getInt("ACCOUNTING_UNIT_ID") +"</unit_id>";	 
                                 xml+= "<unit_name>"+ rs2.getString("ACCOUNTING_UNIT_NAME") +"</unit_name>";  				                		                 
                                 count++;
                         }					              
                         if(count==0)
                                 xml+="<flag>NoData</flag>";					           
                         else               
                                 xml+="<flag>success</flag>";
                                     
                }
                catch(Exception e) 
                {
                         System.out.println("Exception in testAsset..."+e);
                         xml+="<flag>"+e.getMessage()+"</flag>";
                }                      
        }
            else if(cmd.equalsIgnoreCase("forTBstatus"))
            {
            	int from_txtCB_Month=0,from_txtCB_Year=0;
            	 try{from_txtCB_Year=Integer.parseInt(request.getParameter("from_txtCB_Year"));}
                 catch(Exception e){System.out.println(e);}
            	 
            	 try{from_txtCB_Month=Integer.parseInt(request.getParameter("from_txtCB_Month"));}
                 catch(Exception e){System.out.println(e);}
                
                xml=xml+"<command>forTBstatus</command>";
                sql="Select S.Accounting_Unit_Id,U.Accounting_Unit_Name "+
					" From Fas_Trial_Balance_Status S Inner Join Fas_Mst_Acct_Units U "+
                		" on S.Accounting_Unit_Id=U.Accounting_Unit_Id "+
					" Where Tb_Status   ='Y' "+
                		" And S.Cashbook_Year ="+from_txtCB_Year+
                		" AND s.cashbook_month="+from_txtCB_Month+" order by S.Accounting_Unit_Id ";                    
                System.out.println(" SQL :: "+sql);
                try
                {
                         ps2=con.prepareStatement(sql);
                        // System.out.println(AccUnitId);
                         rs2=ps2.executeQuery();                                 
                         while(rs2.next()) 
                         {
                                 xml+= "<unit_id>"+ rs2.getInt("ACCOUNTING_UNIT_ID") +"</unit_id>";	 
                                 xml+= "<unit_name>"+ rs2.getString("ACCOUNTING_UNIT_NAME") +"</unit_name>";  				                		                 
                                 count++;
                         }					              
                         if(count==0)
                                 xml+="<flag>NoData</flag>";					           
                         else               
                                 xml+="<flag>success</flag>";
                                     
                }
                catch(Exception e) 
                {
                         System.out.println("Exception in loadTransferUnit..."+e);
                         xml+="<flag>"+e.getMessage()+"</flag>";
                }                      
        }//added by B.Sathya on02/Feb2015*************88888
            else if(cmd.equalsIgnoreCase("forSupTBstatus"))
            {
            	int from_txtCB_Month=0,from_txtCB_Year=0;
            	 try{from_txtCB_Year=Integer.parseInt(request.getParameter("from_txtCB_Year"));}
                 catch(Exception e){System.out.println(e);}
            	 
            	 try{from_txtCB_Month=Integer.parseInt(request.getParameter("from_txtCB_Month"));}
                 catch(Exception e){System.out.println(e);}
                
                xml=xml+"<command>forTBstatus</command>";
                sql="Select S.Accounting_Unit_Id,U.Accounting_Unit_Name "+
					" From FAS_TRIAL_BALANCE_STATUS_SJV S Inner Join Fas_Mst_Acct_Units U "+
                		" on S.Accounting_Unit_Id=U.Accounting_Unit_Id "+
					" Where Tb_Status   ='Y' "+
                		" And S.Cashbook_Year ="+from_txtCB_Year+
                		" AND s.cashbook_month="+from_txtCB_Month+" order by S.Accounting_Unit_Id ";                    
                System.out.println(" SQL :: "+sql);
                try
                {
                         ps2=con.prepareStatement(sql);
                        // System.out.println(AccUnitId);
                         rs2=ps2.executeQuery();                                 
                         while(rs2.next()) 
                         {
                                 xml+= "<unit_id>"+ rs2.getInt("ACCOUNTING_UNIT_ID") +"</unit_id>";	 
                                 xml+= "<unit_name>"+ rs2.getString("ACCOUNTING_UNIT_NAME") +"</unit_name>";  				                		                 
                                 count++;
                         }					              
                         if(count==0)
                                 xml+="<flag>NoData</flag>";					           
                         else               
                                 xml+="<flag>success</flag>";
                                     
                }
                catch(Exception e) 
                {
                         System.out.println("Exception in loadTransferUnit..."+e);
                         xml+="<flag>"+e.getMessage()+"</flag>";
                }                      
        }
            // added by B.Sathya NIC on 28/11/2014 for Downloading Journal Record File ****
            else if(cmd.equalsIgnoreCase("forJournalTBstatus"))
            {
            	int from_txtCB_Month=0,from_txtCB_Year=0;
            	 try{from_txtCB_Year=Integer.parseInt(request.getParameter("from_txtCB_Year"));}
                 catch(Exception e){System.out.println(e);}
            	 
            	 try{from_txtCB_Month=Integer.parseInt(request.getParameter("from_txtCB_Month"));}
                 catch(Exception e){System.out.println(e);}
                
                xml=xml+"<command>forJournalTBstatus</command>";
                /*sql21="SELECT accounting_unit_id, " +
                		"  accounting_unit_name " +
                		"FROM fas_mst_acct_units " +
                		"WHERE Accounting_Unit_Id NOT IN " +
                		"  (SELECT accounting_unit_id " +
                		"  FROM fas_trial_balance_status tbs " +
                		"  WHERE tbs.cashbook_month =  " +from_txtCB_Month +
                		"  AND tbs.cashbook_year    =  " +from_txtCB_Year +
                		"  AND tbs.tb_status        = 'Y' " +
                		"  ) " +
                		"ORDER BY accounting_unit_id";  */
                
                sql21 = "SELECT x.unitid, " +
                		"  Y.accounting_unit_name " +
                		"FROM " +
                		"  (SELECT DISTINCT M.Accounting_Unit_Id AS unitid " +
                		"  FROM Fas_Journal_Master M, " +
                		"    Fas_Journal_Transaction T " +
                		"  WHERE M.ACCOUNTING_UNIT_ID NOT IN " +
                		"    (SELECT accounting_unit_id " +
                		"    FROM fas_trial_balance_status tbs " +
                		"    WHERE Tbs.Cashbook_Month =  " + from_txtCB_Month +
                		"    AND tbs.cashbook_year    =  " + from_txtCB_Year +
                		"    AND tbs.tb_status        = 'Y' " +
                		"    ) " +
                		"  AND M.ACCOUNTING_UNIT_ID = T.ACCOUNTING_UNIT_ID " +
                		"  AND M.Cashbook_Year      = T.Cashbook_Year " +
                		"  AND M.Cashbook_Month     = T.Cashbook_Month " +
                		"  AND M.Voucher_No         = T.Voucher_No " +
                		"  AND M.Cashbook_Year      =  " + from_txtCB_Year +
                		"  AND M.Cashbook_Month     =  " + from_txtCB_Month +
                		"  )X " +
                		"INNER JOIN " +
                		"  (SELECT A.Accounting_Unit_Id unid, " +
                		"    A.Accounting_Unit_Name " +
                		"  FROM Fas_Mst_Acct_Units A " +
                		"  ) Y " +
                		"ON x.unitid = y.unid " +
                		"ORDER BY unitid";

               
                System.out.println(" SQL :: "+sql21);
                try
                {
                         ps22=con.prepareStatement(sql21);
                        // System.out.println(AccUnitId);
                         rs22=ps22.executeQuery();                                 
                         while(rs22.next()) 
                         {
                                 xml+= "<unit_id>"+ rs22.getInt("unitid") +"</unit_id>";	 
                                 
                                 xml+= "<unit_name><![CDATA[" + rs22.getString("ACCOUNTING_UNIT_NAME") + "]]></unit_name>";  				                		                 
                                 count++;
                         }					              
                         if(count==0)
                                 xml+="<flag>NoData</flag>";					           
                         else               
                                 xml+="<flag>success</flag>";
                                     
                }
                                
                catch(Exception e) 
                {
                         System.out.println("Exception in loadTransferUnit..."+e);
                         xml+="<flag>"+e.getMessage()+"</flag>";
                }                      
        }
            
            //siva changed on  2016-03-14 load acc.units in jrnl_download.jsp 
            else if(cmd.equalsIgnoreCase("forJournalUnits"))
            {
            	int from_txtCB_Month=0,from_txtCB_Year=0;
            	 try{from_txtCB_Year=Integer.parseInt(request.getParameter("from_txtCB_Year"));}
                 catch(Exception e){System.out.println(e);}
            	 
            	 try{from_txtCB_Month=Integer.parseInt(request.getParameter("from_txtCB_Month"));}
                 catch(Exception e){System.out.println(e);}
                
                xml=xml+"<command>forJournalUnits</command>";
               
                
                sql21 = "SELECT x.unitid, Y.Accounting_Unit_Name "
                		+ "FROM  (SELECT DISTINCT M.Accounting_Unit_Id AS unitid  FROM Fas_Journal_Master M,"
                		+ "   Fas_Journal_Transaction T  Where  M.ACCOUNTING_UNIT_ID = T.ACCOUNTING_UNIT_ID "
                		+ " AND M.Cashbook_Year      = T.Cashbook_Year   "
                		+ "AND M.Cashbook_Month     = T.Cashbook_Month  "
                		+ "And M.Voucher_No         = T.Voucher_No  And "
                		+ "M.Cashbook_Year      = " + from_txtCB_Year +  
                		 " AND  M.Cashbook_Month     =  "+ from_txtCB_Month  +
                		  "   )X "
                		+ " INNER JOIN  (SELECT A.Accounting_Unit_Id unid,  "
                		+ " A.Accounting_Unit_Name  FROM Fas_Mst_Acct_Units A  ) "
                		+ "Y ON x.unitid = y.unid  ORDER BY unitid";

               
                System.out.println(" SQL :: "+sql21);
                try
                {
                         ps22=con.prepareStatement(sql21);
                        // System.out.println(AccUnitId);
                         rs22=ps22.executeQuery();                                 
                         while(rs22.next()) 
                         {
                                 xml+= "<unit_id>"+ rs22.getInt("unitid") +"</unit_id>";	 
                                 
                                 xml+= "<unit_name><![CDATA[" + rs22.getString("ACCOUNTING_UNIT_NAME") + "]]></unit_name>";  				                		                 
                                 count++;
                         }					              
                         if(count==0)
                                 xml+="<flag>NoData</flag>";					           
                         else               
                                 xml+="<flag>success</flag>";
                                     
                }
                                
                catch(Exception e) 
                {
                         System.out.println("Exception in loadTransferUnit..."+e);
                         xml+="<flag>"+e.getMessage()+"</flag>";
                }                      
        }
          //added this method  to load the supplement_no  
            else if(cmd.equalsIgnoreCase("loadsupplno"))
            {
            	int from_txtCB_Month=0,from_txtCB_Year=0;
           	 try{from_txtCB_Year=Integer.parseInt(request.getParameter("from_txtCB_Year"));}
                catch(Exception e){System.out.println(e);}
           	 
           	 try{from_txtCB_Month=Integer.parseInt(request.getParameter("from_txtCB_Month"));}
                catch(Exception e){System.out.println(e);}
               
               xml=xml+"<command>loadsupplno</command>";
              
               
               sql21 = "SELECT DISTINCT M.SUPPLEMENT_NO AS suppl_no  FROM Fas_Journal_Master M,"
               		+ "   Fas_Journal_Transaction T  Where  M.ACCOUNTING_UNIT_ID = T.ACCOUNTING_UNIT_ID "
               		+ " AND M.Cashbook_Year      = T.Cashbook_Year   "
               		+ "AND M.Cashbook_Month     = T.Cashbook_Month  "
               		+ "And M.Voucher_No         = T.Voucher_No  And "
               		+ "M.Cashbook_Year      = " + from_txtCB_Year +  
               		 " AND  M.Cashbook_Month     =  "+ from_txtCB_Month +"order by M.SUPPLEMENT_NO" ;
              
               System.out.println(" SQL :: "+sql21);
               try
               {
                        ps22=con.prepareStatement(sql21);
                       // System.out.println(AccUnitId);
                        rs22=ps22.executeQuery();                                 
                        while(rs22.next()) 
                        {
                                xml+= "<supple_no>"+ rs22.getInt("suppl_no") +"</supple_no>";	 
                                
                              //  xml+= "<unit_name><![CDATA[" + rs22.getString("ACCOUNTING_UNIT_NAME") + "]]></unit_name>";  				                		                 
                                count++;
                        }					              
                        if(count==0)
                                xml+="<flag>NoData</flag>";					           
                        else               
                                xml+="<flag>success</flag>";
                                    
               }
                               
               catch(Exception e) 
               {
                        System.out.println("Exception in loadTransferUnit..."+e);
                        xml+="<flag>"+e.getMessage()+"</flag>";
               }                      	
            }
            
            
            else if(cmd.equalsIgnoreCase("forPaymentUnits"))
            {
            	int from_txtCB_Month=0,from_txtCB_Year=0;
            	 try{from_txtCB_Year=Integer.parseInt(request.getParameter("from_txtCB_Year"));}
                 catch(Exception e){System.out.println(e);}
            	 
            	 try{from_txtCB_Month=Integer.parseInt(request.getParameter("from_txtCB_Month"));}
                 catch(Exception e){System.out.println(e);}
                
                xml=xml+"<command>forPaymentUnits</command>";
               
                
                sql21 = "SELECT x.unitid, Y.Accounting_Unit_Name "
                		+ "FROM  (SELECT DISTINCT M.Accounting_Unit_Id AS unitid  FROM "
                		+ " Fas_payment_Master M,   Fas_payment_Transaction T  Where  M.ACCOUNTING_UNIT_ID = T.ACCOUNTING_UNIT_ID "
                		+ " AND M.Cashbook_Year      = T.Cashbook_Year   "
                		+ "AND M.Cashbook_Month     = T.Cashbook_Month  "
                		+ "And M.Voucher_No         = T.Voucher_No  And "
                		+ "M.Cashbook_Year      = " + from_txtCB_Year +  
                		 " AND  M.Cashbook_Month     =  "+ from_txtCB_Month  
                		 // "  and M.account_head_code like '%07' "
                		  + " )X "
                		+ " INNER JOIN  (SELECT A.Accounting_Unit_Id unid,  "
                		+ " A.Accounting_Unit_Name  FROM Fas_Mst_Acct_Units A  ) "
                		+ "Y ON x.unitid = y.unid  ORDER BY unitid";

               
                System.out.println(" SQL :: "+sql21);
                try
                {
                         ps22=con.prepareStatement(sql21);
                        // System.out.println(AccUnitId);
                         rs22=ps22.executeQuery();                                 
                         while(rs22.next()) 
                         {
                                 xml+= "<unit_id>"+ rs22.getInt("unitid") +"</unit_id>";	 
                                 
                                 xml+= "<unit_name><![CDATA[" + rs22.getString("ACCOUNTING_UNIT_NAME") + "]]></unit_name>";  				                		                 
                                 count++;
                         }					              
                         if(count==0)
                                 xml+="<flag>NoData</flag>";					           
                         else               
                                 xml+="<flag>success</flag>";
                                     
                }
                                
                catch(Exception e) 
                {
                         System.out.println("Exception in loadTransferUnit..."+e);
                         xml+="<flag>"+e.getMessage()+"</flag>";
                }                      
        }
            //siva added on 2016-06-04 in the purpose off  receipt unit only load in the Accouning unit Id
            //* start
            else if(cmd.equalsIgnoreCase("forReceiptUnits"))
            {
            	int from_txtCB_Month=0,from_txtCB_Year=0;
            	 try{from_txtCB_Year=Integer.parseInt(request.getParameter("from_txtCB_Year"));}
                 catch(Exception e){System.out.println(e);}
            	 
            	 try{from_txtCB_Month=Integer.parseInt(request.getParameter("from_txtCB_Month"));}
                 catch(Exception e){System.out.println(e);}
                
                xml=xml+"<command>forReceiptUnits</command>";
               
                
                sql21 = "SELECT x.unitid,  Y.Accounting_Unit_Name FROM "
                		+ "  (SELECT DISTINCT M.ACCOUNTING_UNIT_ID AS UNITID   "
                		+ "FROM FAS_RECEIPT_MASTER M,    FAS_RECEIPT_TRANSACTION T  "
                		+ "WHERE M.ACCOUNTING_UNIT_ID = T.ACCOUNTING_UNIT_ID  "
                		+ "AND M.Cashbook_Year        = T.Cashbook_Year  "
                		+ "AND M.CASHBOOK_MONTH       = T.CASHBOOK_MONTH "
                		+ "  AND M.RECEIPT_NO           = T.RECEIPT_NO"
                		+ "  AND M.Cashbook_Year        = " + from_txtCB_Year 
                		+ "  AND M.Cashbook_Month       = "+ from_txtCB_Month+" )X  INNER JOIN "
                		+ "  (SELECT A.Accounting_Unit_Id unid,     A.Accounting_Unit_Name"
                		+ "  FROM Fas_Mst_Acct_Units A   ) Y  ON x.unitid = y.unid "
                		+ "ORDER BY unitid";

               
                System.out.println(" SQL :: "+sql21);
                try
                {
                         ps22=con.prepareStatement(sql21);
                        // System.out.println(AccUnitId);
                         rs22=ps22.executeQuery();                                 
                         while(rs22.next()) 
                         {
                                 xml+= "<unit_id>"+ rs22.getInt("unitid") +"</unit_id>";	 
                                 
                                 xml+= "<unit_name><![CDATA[" + rs22.getString("ACCOUNTING_UNIT_NAME") + "]]></unit_name>";  				                		                 
                                 count++;
                         }					              
                         if(count==0)
                                 xml+="<flag>NoData</flag>";					           
                         else               
                                 xml+="<flag>success</flag>";
                                     
                }
                                
                catch(Exception e) 
                {
                         System.out.println("Exception in loadTransferUnit..."+e);
                         xml+="<flag>"+e.getMessage()+"</flag>";
                }                      
        }
            //****************End.
     //siva added on 2016-06-09 in the purpose of fund transfer download  
            //start
            else if(cmd.equalsIgnoreCase("forFundTransferUnits"))
            {
            	int from_txtCB_Month=0,from_txtCB_Year=0;
            	 try{from_txtCB_Year=Integer.parseInt(request.getParameter("from_txtCB_Year"));}
                 catch(Exception e){System.out.println(e);}
            	 
            	 try{from_txtCB_Month=Integer.parseInt(request.getParameter("from_txtCB_Month"));}
                 catch(Exception e){System.out.println(e);}
                
                xml=xml+"<command>forFundTransferUnits</command>";
               
                
                sql21 = "SELECT x.unitid, Y.Accounting_Unit_Name "
                		+ " FROM "
                		+ " (SELECT DISTINCT M.ACCOUNTING_UNIT_ID AS UNITID "
                		+ " FROM FAS_FUND_TRF_FROM_OFFICE M, Fas_Mst_Acct_Units t "
                		+ " WHERE M.ACCOUNTING_UNIT_ID = T.ACCOUNTING_UNIT_ID "
                		+ " AND M.accounting_for_office_id        = T.accounting_unit_office_id "
                		+ " AND M.CASHBOOK_YEAR        =" + from_txtCB_Year 
                		+ " AND M.Cashbook_Month       ="+ from_txtCB_Month
                		+ " )X "
                		+ " INNER JOIN "
                		+ " (SELECT A.Accounting_Unit_Id unid, "
                		+ " A.Accounting_Unit_Name"
                		+ " FROM Fas_Mst_Acct_Units A ) Y "
                		+ " ON x.unitid = y.unid ORDER BY unitid";

               
                System.out.println(" SQL :: "+sql21);
                try
                {
                         ps22=con.prepareStatement(sql21);
                        // System.out.println(AccUnitId);
                         rs22=ps22.executeQuery();                                 
                         while(rs22.next()) 
                         {
                                 xml+= "<unit_id>"+ rs22.getInt("unitid") +"</unit_id>";	 
                                 
                                 xml+= "<unit_name><![CDATA[" + rs22.getString("ACCOUNTING_UNIT_NAME") + "]]></unit_name>";  				                		                 
                                 count++;
                         }					              
                         if(count==0)
                                 xml+="<flag>NoData</flag>";					           
                         else               
                                 xml+="<flag>success</flag>";
                                     
                }
                                
                catch(Exception e) 
                {
                         System.out.println("Exception in loadTransferUnit..."+e);
                         xml+="<flag>"+e.getMessage()+"</flag>";
                }                      
        }
            
            else if(cmd.equalsIgnoreCase("forFundTransferBank"))
            {
            	int from_txtCB_Month=0,from_txtCB_Year=0;
            	 try{from_txtCB_Year=Integer.parseInt(request.getParameter("from_txtCB_Year"));}
                 catch(Exception e){System.out.println(e);}
            	 
            	 try{from_txtCB_Month=Integer.parseInt(request.getParameter("from_txtCB_Month"));}
                 catch(Exception e){System.out.println(e);}
                
                xml=xml+"<command>forFundTransferBank</command>";
               
                
                sql21 = "SELECT x.unitid, Y.Accounting_Unit_Name , y.ACCOUNTING_UNIT_OFFICE_ID "
                		+ "  FROM "
                		+ "  (SELECT DISTINCT t.ACCOUNTING_UNIT_ID AS UNITID , M.TRANSFER_TO_OFFICE_ID "
                		+ "  FROM FAS_FUND_TRF_FROM_HO_TRN M, FAS_MST_ACCT_UNITS T   WHERE "
                		+ "  M.TRANSFER_TO_OFFICE_ID       = T.ACCOUNTING_UNIT_OFFICE_ID "
                		+ "  AND M.CASHBOOK_YEAR        =" + from_txtCB_Year
                		+ "  AND M.CASHBOOK_MONTH       ="+ from_txtCB_Month+")X "
                		+ "  INNER JOIN    		  (SELECT A.ACCOUNTING_UNIT_ID AS UNID , A.Accounting_Unit_Name, A.ACCOUNTING_UNIT_OFFICE_ID "
                		+ "  FROM FAS_MST_ACCT_UNITS A ) Y   ON X.UNITID = Y.UNID "
                		+ " AND X.TRANSFER_TO_OFFICE_ID=y.ACCOUNTING_UNIT_OFFICE_ID   "
                		+ " ORDER BY unitid ";
               
                System.out.println(" SQL :: "+sql21);
                try
                {
                         ps22=con.prepareStatement(sql21);
                        // System.out.println(AccUnitId);
                         rs22=ps22.executeQuery();                                 
                         while(rs22.next()) 
                         {
                                 xml+= "<unit_id>"+ rs22.getInt("unitid") +"</unit_id>";	 
                                 
                                 xml+= "<unit_name><![CDATA[" + rs22.getString("ACCOUNTING_UNIT_NAME") + "]]></unit_name>";
                                 
                                // xml+= "<Office_Id><![CDATA[" + rs22.getString("ACCOUNTING_UNIT_OFFICE_ID") + "]]></Office_Id>";
                                 
                                 count++;
                         }					              
                         if(count==0)
                                 xml+="<flag>NoData</flag>";					           
                         else               
                                 xml+="<flag>success</flag>";
                                     
                }
                                
                catch(Exception e) 
                {
                         System.out.println("Exception in loadTransferUnit..."+e);
                         xml+="<flag>"+e.getMessage()+"</flag>";
                }                      
        }
            
            else if(cmd.equalsIgnoreCase("forFundReceiptUnit"))
            {
            	int from_txtCB_Month=0,from_txtCB_Year=0;
            	 try{from_txtCB_Year=Integer.parseInt(request.getParameter("from_txtCB_Year"));}
                 catch(Exception e){System.out.println(e);}
            	 
            	 try{from_txtCB_Month=Integer.parseInt(request.getParameter("from_txtCB_Month"));}
                 catch(Exception e){System.out.println(e);}
                
                xml=xml+"<command>forFundReceiptUnit</command>";
               
                
                sql21 = "SELECT x.unitid, Y.Accounting_Unit_Name  "
                		+ "  FROM "
                		+ "  (SELECT DISTINCT t.ACCOUNTING_UNIT_ID AS UNITID "
                		+ "   FROM FAS_FUND_RECEIPT_BY_OFFICE  M,    FAS_MST_ACCT_UNITS T "
                		+ "  WHERE M.ACCOUNTING_UNIT_ID = T.ACCOUNTING_UNIT_ID "
                		+ "  AND M.CASHBOOK_YEAR        =" + from_txtCB_Year
                		+ "  AND M.CASHBOOK_MONTH       ="+ from_txtCB_Month+")X "
                		+ "  INNER JOIN    		  (SELECT A.ACCOUNTING_UNIT_ID AS UNID , A.Accounting_Unit_Name "
                		+ "  FROM FAS_MST_ACCT_UNITS A ) Y   ON X.UNITID = Y.UNID "                		
                		+ " ORDER BY unitid ";
               
                System.out.println(" SQL :: "+sql21);
                try
                {
                         ps22=con.prepareStatement(sql21);
                        // System.out.println(AccUnitId);
                         rs22=ps22.executeQuery();                                 
                         while(rs22.next()) 
                         {
                                 xml+= "<unit_id>"+ rs22.getInt("unitid") +"</unit_id>";	 
                                 
                                 xml+= "<unit_name><![CDATA[" + rs22.getString("ACCOUNTING_UNIT_NAME") + "]]></unit_name>";
                                 
                                // xml+= "<Office_Id><![CDATA[" + rs22.getString("ACCOUNTING_UNIT_OFFICE_ID") + "]]></Office_Id>";
                                 
                                 count++;
                         }					              
                         if(count==0)
                                 xml+="<flag>NoData</flag>";					           
                         else               
                                 xml+="<flag>success</flag>";
                                     
                }
                                
                catch(Exception e) 
                {
                         System.out.println("Exception in loadTransferUnit..."+e);
                         xml+="<flag>"+e.getMessage()+"</flag>";
                }                      
        }
            else if(cmd.equalsIgnoreCase("forFundReceiptBank"))
            {
            	int from_txtCB_Month=0,from_txtCB_Year=0;
            	 try{from_txtCB_Year=Integer.parseInt(request.getParameter("from_txtCB_Year"));}
                 catch(Exception e){System.out.println(e);}
            	 
            	 try{from_txtCB_Month=Integer.parseInt(request.getParameter("from_txtCB_Month"));}
                 catch(Exception e){System.out.println(e);}
                
                xml=xml+"<command>forFundReceiptBank</command>";
               
                
                sql21 = "SELECT x.unitid, Y.Accounting_Unit_Name  "
                		+ "  FROM "
                		+ "  (SELECT DISTINCT t.ACCOUNTING_UNIT_ID AS UNITID "
                		+ "   FROM  FAS_FUND_RECEIPT_BY_HO  M, FAS_MST_ACCT_UNITS T "
                		+ "  WHERE M.RECEIVED_FROM_OFFICE_ID = T.ACCOUNTING_UNIT_OFFICE_ID "  
                		+ "  AND M.CASHBOOK_YEAR        =" + from_txtCB_Year
                		+ "  AND M.CASHBOOK_MONTH       ="+ from_txtCB_Month+")X "
                		+ "  INNER JOIN    		  (SELECT A.ACCOUNTING_UNIT_ID AS UNID , A.Accounting_Unit_Name "
                		+ "  FROM FAS_MST_ACCT_UNITS A ) Y   ON X.UNITID = Y.UNID "                		
                		+ " ORDER BY unitid ";
                
                
                
                
               
               
                System.out.println(" SQL :: "+sql21);
                try
                {
                         ps22=con.prepareStatement(sql21);
                        // System.out.println(AccUnitId);
                         rs22=ps22.executeQuery();                                 
                         while(rs22.next()) 
                         {
                                 xml+= "<unit_id>"+ rs22.getInt("unitid") +"</unit_id>";	 
                                 
                                 xml+= "<unit_name><![CDATA[" + rs22.getString("ACCOUNTING_UNIT_NAME") + "]]></unit_name>";
                                 
                                // xml+= "<Office_Id><![CDATA[" + rs22.getString("ACCOUNTING_UNIT_OFFICE_ID") + "]]></Office_Id>";
                                 
                                 count++;
                         }					              
                         if(count==0)
                                 xml+="<flag>NoData</flag>";					           
                         else               
                                 xml+="<flag>success</flag>";
                                     
                }
                                
                catch(Exception e) 
                {
                         System.out.println("Exception in loadTransferUnit..."+e);
                         xml+="<flag>"+e.getMessage()+"</flag>";
                }                      
        }
            
            else if(cmd.equalsIgnoreCase("loadIBTUnit"))
            {
            	int from_txtCB_Month=0,from_txtCB_Year=0;
            	 try{from_txtCB_Year=Integer.parseInt(request.getParameter("from_txtCB_Year"));}
                 catch(Exception e){System.out.println(e);}
            	 
            	 try{from_txtCB_Month=Integer.parseInt(request.getParameter("from_txtCB_Month"));}
                 catch(Exception e){System.out.println(e);}
                
                xml=xml+"<command>loadIBTUnit</command>";
               
                
                sql21 = "SELECT x.unitid, Y.Accounting_Unit_Name  "
                		+ "  FROM "
                		+ "  (SELECT DISTINCT t.ACCOUNTING_UNIT_ID AS UNITID "
                		+ "   FROM  FAS_INTER_BANK_TRF_AT_HO   M, FAS_MST_ACCT_UNITS T "
                		+ "  WHERE M.ACCOUNTING_UNIT_ID = T.ACCOUNTING_UNIT_ID"  
                		+ "  AND M.CASHBOOK_YEAR        =" + from_txtCB_Year
                		+ "  AND M.CASHBOOK_MONTH       ="+ from_txtCB_Month+")X "
                		+ "  INNER JOIN    		  (SELECT A.ACCOUNTING_UNIT_ID AS UNID , A.Accounting_Unit_Name "
                		+ "  FROM FAS_MST_ACCT_UNITS A ) Y   ON X.UNITID = Y.UNID "                		
                		+ " ORDER BY unitid ";
                              
                System.out.println(" SQL :: "+sql21);
                try
                {
                         ps22=con.prepareStatement(sql21);
                        // System.out.println(AccUnitId);
                         rs22=ps22.executeQuery();                                 
                         while(rs22.next()) 
                         {
                                 xml+= "<unit_id>"+ rs22.getInt("unitid") +"</unit_id>";	 
                                 
                                 xml+= "<unit_name><![CDATA[" + rs22.getString("ACCOUNTING_UNIT_NAME") + "]]></unit_name>";
                                 
                                // xml+= "<Office_Id><![CDATA[" + rs22.getString("ACCOUNTING_UNIT_OFFICE_ID") + "]]></Office_Id>";
                                 
                                 count++;
                         }					              
                         if(count==0)
                                 xml+="<flag>NoData</flag>";					           
                         else               
                                 xml+="<flag>success</flag>";
                                     
                }
                                
                catch(Exception e) 
                {
                         System.out.println("Exception in loadTransferUnit..."+e);
                         xml+="<flag>"+e.getMessage()+"</flag>";
                }                      
        }
            
                      
         // 2016-06-09 end   
            
            //12345   end
            
            //tb status checking
            //this is for checking TB Regular
            else if(cmd.equalsIgnoreCase("check_TB"))
            {
                 int count_no_one=0,count_no_two=0,verify_sup=0;
                    xml=xml+"<command>check_TB</command>";
                    String sql1="Select A.*,B.*,C.* From\n" + 
                    "(SELECT COUNT(*)as onecount,Accounting_Unit_Id,DIFFERENCE as DIFFERENCE_sup\n" + 
                    "From Fas_Tda_Tca_Sup_2012\n" + 
                    "WHERE ACCOUNTING_UNIT_ID=?\n" + 
                    "AND Cashbook_Year       =2012\n" + 
                    "And Cashbook_Month      =3 GROUP BY Accounting_Unit_Id, DIFFERENCE)a\n" + 
                    "\n" + 
                    "Full Outer Join \n" + 
                    "(SELECT COUNT(*)as twocount,Accounting_Unit_Id,DIFFERENCE as DIFFERENCE_mar\n" + 
                    "From Fas_Tda_Tca_Reg_Mar2012\n" + 
                    "Where Accounting_Unit_Id=?\n" + 
                    "AND Cashbook_Year       =2012\n" + 
                    "And Cashbook_Month      =3\n" + 
                    " GROUP BY Accounting_Unit_Id, DIFFERENCE)B\n" + 
                    "On A.Accounting_Unit_Id=B.Accounting_Unit_Id\n" + 
                    "Full Outer Join\n" + 
                    "(SELECT COUNT(*)as threecount,Accounting_Unit_Id,DIFFERENCE as DIFFERENCE_apr\n" + 
                    "From FAS_TDA_TCA_REG_APR2012\n" + 
                    "Where Accounting_Unit_Id=?\n" + 
                    "And Cashbook_Year       =2012\n" + 
                    "And Cashbook_Month      =4\n" + 
                    " GROUP BY Accounting_Unit_Id, DIFFERENCE)C\n" + 
                    "On A.Accounting_Unit_Id=c.Accounting_Unit_Id";
                  System.out.println(sql1);
                    try
                    {
                             ps2=con.prepareStatement(sql1);
                             System.out.println(AccUnitId);
                             ps2.setInt(1,AccUnitId);
                             ps2.setInt(2,AccUnitId);
                             ps2.setInt(3,AccUnitId);
                             rs2=ps2.executeQuery();                                 
                             while(rs2.next()) 
                             {
                            	String mar= rs2.getString("DIFFERENCE_mar");
                            	System.out.println("march::::"+mar);
                            	if(mar==null)
                            	{
                            		xml+="<march>NoDatainMarch</march>";	
                            	}
                            	else
                            	{
                            		xml+="<march>"+mar+"</march>";	
                            	}
                            	String sup= rs2.getString("DIFFERENCE_sup");
                            	System.out.println("supple::::"+sup);
                            	if(sup==null)
                            	{
                            		xml+="<supp>NoDatainsupp</supp>";	
                            	}
                            	else
                            	{
                            		xml+="<supp>"+sup+"</supp>";	
                            	}
                            	
                            	
                            	String april= rs2.getString("DIFFERENCE_apr");
                            	System.out.println("april::::"+april);
                            	if(april==null)
                            	{
                            		xml+="<april>NoDatainApril</april>";	
                            	}
                            	else
                            	{
                            		xml+="<april>"+sup+"</april>";	
                            	}
                            	
                            	
                            	 yesCount++;
                                    
                             }
                             rs2.close();
                             ps2.close();
                             if(yesCount==0)
                                     xml+="<flag>NoDataForThree</flag>";					           
                             else               
                                     xml+="<flag>success</flag>";
                                         
                    }
                    catch(Exception e) 
                    {
                             System.out.println("Exception in loadTransferUnit..."+e);
                             xml+="<flag>"+e.getMessage()+"</flag>";
                    }                      
            }
            //tb check for Supplement
            else if(cmd.equalsIgnoreCase("tb_supp"))
            {    
            	int year=0,month=0;
            	try{year=Integer.parseInt(request.getParameter("year"));}
                catch(Exception e){System.out.println(e);}
                
                try{month=Integer.parseInt(request.getParameter("month"));}
                catch(Exception e){System.out.println(e);}
                xml=xml+"<command>tb_supp</command>";
                
                    try
                    {
                    	ps2 =con.prepareStatement("select TB_STATUS from FAS_TRIAL_BALANCE_STATUS_SJV where ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=?  and CASHBOOK_MONTH=? ");
                    	  ps2.setInt(1,AccUnitId);
    		                //ps.setInt(2,cmbOffice_code);
    		                ps2.setInt(2, year);
    		                ps2.setInt(3, month);
    		                rs2 = ps2.executeQuery();                             
                             if(rs2.next()) 
                             {
                            	 if (rs2.getString("TB_STATUS").equalsIgnoreCase("N"))
                                     xml = xml + "<flag>success</flag>";
                                 else
                                     xml = xml + "<flag>failure</flag>";
                             }					              
                             else
                                 xml = xml + "<flag>success</flag>";
                                         
                    }
                    catch(Exception e) 
                    {
                             System.out.println("Exception in tb..."+e);
                             xml+="<flag>"+e.getMessage()+"</flag>";
                    } 
                    System.out.println("xml for TB:::::"+xml);
            }
            else if(cmd.equalsIgnoreCase("checkSubLedgerMandatory_payroll")) 
	        {
                int count1=0;
               // System.out.println("checkSubLedgerMandatory");
                  
                    xml=xml+"<command>checkSubLedgerMandatory_payroll</command>";
                     try{
                         
                         int acc_hd_code =Integer.parseInt(request.getParameter("txtAcc_HeadCode"));
                         
                         System.out.println("acc_hd_code~~~~~~~~~~~~~@@@@@@@@@@@@~~~~~~~~:"+acc_hd_code);
                         ps2=con.prepareStatement("select count as count1 from(SELECT COUNT(*) as count FROM com_mst_account_heads WHERE account_head_code =? AND SUB_LEDGER_TYPE_APPLICABLE in ('Y','N') AND SL_MANDATORY  ='N' )as ps");    
                         ps2.setInt(1,acc_hd_code);
                         rs2=ps2.executeQuery();   
                         
                         while(rs2.next()) 
                         {
                           //  System.out.println("count1~~~~~~~~~~~++++++++++++++++~~~~~~~~~~~~~~:"+rs2.getInt("count1"));
                         
                            //changes on 02jan2014
                              if(rs2.getInt("count1")>0) {
                                  xml+="<flag>success</flag>";
                                //  xml+="<M_flag>Madatory</M_flag>";     
                                  xml+="<M_flag>Not_Madatory</M_flag>";
                              }else{
                                  xml+="<flag>success</flag>";
                                  xml+="<M_flag>Madatory</M_flag>";
                               //   xml+="<M_flag>Not_Madatory</M_flag>";    
                              }
                         }  
                        
                         System.out.println("last xml"+xml);
                     }
                    catch(Exception e1) 
                    {
                             System.out.println("Exception in checkSubLedgerMandatory..."+e1);
                        xml+="<flag>failure</flag>";
                    } 
                }
            else if(cmd.equalsIgnoreCase("checkSubLedgerMandatory")) 
	        {
                int count1=0;
               // System.out.println("checkSubLedgerMandatory");
                  
                    xml=xml+"<command>checkSubLedgerMandatory</command>";
                     try{
                         
                         int acc_hd_code =Integer.parseInt(request.getParameter("txtAcc_HeadCode"));
                         
                         System.out.println("acc_hd_code~~~~~~~~~~~~~@@@@@@@@@@@@~~~~~~~~:"+acc_hd_code);
                         ps2=con.prepareStatement("select count as count1 from(SELECT COUNT(*) as count FROM com_mst_account_heads WHERE account_head_code =? AND SUB_LEDGER_TYPE_APPLICABLE in ('Y','N') AND SL_MANDATORY  ='N' )as ps");    
                         ps2.setInt(1,acc_hd_code);
                         rs2=ps2.executeQuery();   
                         
                         while(rs2.next()) 
                         {
                           //  System.out.println("count1~~~~~~~~~~~++++++++++++++++~~~~~~~~~~~~~~:"+rs2.getInt("count1"));
                         
                            //changes on 02jan2014
                              if(rs2.getInt("count1")>0) {
                                  xml+="<flag>success</flag>";
                                  xml+="<M_flag>Madatory</M_flag>";     
                                  
                              }else{
                                  xml+="<flag>success</flag>";
                                  
                                  xml+="<M_flag>Not_Madatory</M_flag>";    
                              }
                         }  
                        
                         System.out.println("last xml"+xml);
                     }
                    catch(Exception e1) 
                    {
                             System.out.println("Exception in checkSubLedgerMandatory..."+e1);
                        xml+="<flag>failure</flag>";
                    } 
                }
                else if(cmd.equalsIgnoreCase("loadTransferUnit1"))
            {    
                 
                    xml=xml+"<command>loadTransferUnit</command>";
                    sql="select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME " +
                    		"from FAS_MST_ACCT_UNITS where " +
                    		"ACCOUNTING_UNIT_OFFICE_ID not in(select OFFICE_ID from COM_MST_OFFICES " +
                    		"where OFFICE_STATUS_ID in('CL','RD')) " +
                    		"order by ACCOUNTING_UNIT_NAME ";                    
                 //   System.out.println(" SQL :: "+sql);
                    try
                    {
                             ps2=con.prepareStatement(sql);
                            // System.out.println(AccUnitId);
                             //ps2.setInt(1,AccUnitId);
                             rs2=ps2.executeQuery();                                 
                             while(rs2.next()) 
                             {
                                     xml+= "<unit_id>"+ rs2.getInt("ACCOUNTING_UNIT_ID") +"</unit_id>";	 
                                     xml+= "<unit_name>"+ rs2.getString("ACCOUNTING_UNIT_NAME") +"</unit_name>";  				                		                 
                                     count++;
                             }					              
                             if(count==0)
                                     xml+="<flag>NoData</flag>";					           
                             else               
                                     xml+="<flag>success</flag>";
                                         
                    }
                    catch(Exception e) 
                    {
                             System.out.println("Exception in loadTransferUnit..."+e);
                             xml+="<flag>"+e.getMessage()+"</flag>";
                    }                      
            }
        else if(cmd.equalsIgnoreCase("loadTransferUnit_one"))
        {
         
            xml=xml+"<command>loadTransferUnit</command>";
            sql="select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME " +
                        "from FAS_MST_ACCT_UNITS order by ACCOUNTING_UNIT_NAME";                    
         //   System.out.println(" SQL :: "+sql);
            try
            {
                     ps2=con.prepareStatement(sql);
                    // System.out.println(AccUnitId);
                     //ps2.setInt(1,AccUnitId);
                     rs2=ps2.executeQuery();                                 
                     while(rs2.next()) 
                     {
                             xml+= "<unit_id>"+ rs2.getInt("ACCOUNTING_UNIT_ID") +"</unit_id>";  
                             xml+= "<unit_name>"+ rs2.getString("ACCOUNTING_UNIT_NAME") +"</unit_name>";                                                                                 
                             count++;
                     }                                                
                     if(count==0)
                             xml+="<flag>NoData</flag>";                                                   
                     else               
                             xml+="<flag>success</flag>";
                                 
            }
            catch(Exception e) 
            {
                     System.out.println("Exception in loadTransferUnit..."+e);
                     xml+="<flag>"+e.getMessage()+"</flag>";
            }                      
        }
            else if(cmd.equalsIgnoreCase("loadSLType"))
            {
            	    xml=xml+"<command>loadSLType</command>";
            	    try	
            	    {			        	 			  	                 		  
            	    		 sql="select trn.ACCOUNTING_UNIT_ID,trn.ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS trn,COM_MST_OFFICES mst where trn.ACCOUNTING_UNIT_OFFICE_ID=mst.OFFICE_ID and trn.ACCOUNTING_UNIT_ID=? and OFFICE_STATUS_ID NOT IN('CL','RD','NC')";            	    		 
            	    		 ps2=con.prepareStatement(sql);
            	    		 ps2.setInt(1,AccUnitId);
                             rs2=ps2.executeQuery();                                 
                             while(rs2.next()) 
                             {
                                     xml+= "<office_id>"+ rs2.getInt("ACCOUNTING_UNIT_ID") +"</office_id>";	 
                                     xml+= "<office_name>"+ rs2.getString("ACCOUNTING_UNIT_NAME") +"</office_name>";  				                		                 
                                     count++;
                             }					              
                             if(count==0)
                                     xml+="<flag>NoData</flag>";					           
                             else               
                                     xml+="<flag>success</flag>";
            	    }
            	    catch(Exception e)
            	    {
            		  		 System.out.println("Err in loading loadSLType ::: "+e.getMessage());            		  	
            	    }
            }
            xml=xml+"</response>";
            System.out.println(xml);
            out.println(xml);
            out.close();
    }

    public void doPost(HttpServletRequest request, 
                      HttpServletResponse response) throws ServletException, IOException 
    {
    
         
	         String strCommand="";
	         Connection con=null;        
	         PreparedStatement ps=null,ps1=null,ps2=null;
	         String xml="";
	         Statement st=null;
	         ResultSet rs=null;
	         Date bkDate=null;
	 //-----------------------------------------------------------------------------------------------        
	 
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
	        
	//-----------------------------------------------------------------------------------------------        
	        
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
	              ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection	              Class.forName(strDriver.trim());
	              con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
	              st=con.createStatement();
	         }
	         catch(Exception e)
	         {
	             System.out.println("Exception in opening connection :"+e);
	           
	         }
	                 
	 //-----------------------------------------------------------------------------------------------        
	        
	        try
	        {        
	             strCommand=request.getParameter("Command");
	          
	        }
	        
	        catch(Exception e) 
	        {
	             System.out.println("Exception in assigning..."+e);
	        }
	        
	//-----------------------------------------------------------------------------------------------        
	       
	        if(strCommand.equalsIgnoreCase("Add")) 
	        {
	             String CONTENT_TYPE = "text/html; charset=windows-1252";
	             response.setContentType(CONTENT_TYPE);
	            
	             //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	             Calendar c;
	             int txtAcc_HeadCode=0,cmbAcc_UnitCode=0,cmbOffice_code=0,txtCash_Month_hid=0,txtCash_year=0,txtUnitId=0,txtDebitHead=0;
	             int count=0,cmbMas_SL_type=0,cmbMas_SL_Code=0,cmbReason=0;
	             double txtTotalAmt=0;
	             Date txtCrea_date=null;
	             String txtRemarks="",paid_to="",Journal_type="",cr_dr_indicator="",sql="";
	             
	                                     // changes here
	             String update_user=(String)session.getAttribute("UserId");
	             long l=System.currentTimeMillis();
	             Timestamp ts=new Timestamp(l);                      
	             int errcode=0;
	             
	             //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	          //   System.out.println("dhana::"+request.getParameter("butSub"));
	             
	                                
	             try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
	             catch(NumberFormatException e){System.out.println("exception"+e );}
	           //  System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);
	             
	             try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
	             catch(NumberFormatException e){System.out.println("exception"+e );}
	          //   System.out.println("cmbOffice_code "+cmbOffice_code);
	             
	             String[] sd=request.getParameter("txtCrea_date").split("/");
	             c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
	             java.util.Date d=c.getTime();
	             txtCrea_date=new Date(d.getTime());
	     
	             try{txtCash_year=Integer.parseInt(sd[2]);}
	             catch(Exception e){System.out.println("exception"+e );}
	           //  System.out.println("txtCash_year "+txtCash_year);
	             
	             try{txtCash_Month_hid=Integer.parseInt(sd[1]);}
	             catch(Exception e){System.out.println("exception"+e );}
	           //  System.out.println("txtCash_Month_hid "+txtCash_Month_hid);
	             int Originated_SL_No=0;
	             int fin_year_from=0,fin_year_to=0;
	             
	             //////////////////////Financial year calculation/////////////////////////////////
	             if(txtCash_Month_hid>3)
	             {
	            	 	  fin_year_from=txtCash_year;
	            	 	  fin_year_to=txtCash_year+1;
	             }
	             else
	             {
	            	 	  fin_year_from=txtCash_year-1;
	            	 	  fin_year_to=txtCash_year;
	             }
	             System.out.println("fin_year_from ::: "+fin_year_from+"  fin_year_to:::"+fin_year_to);
	             try
	             {
	            	 	  sql="SELECT VOUCHER_NO FROM FAS_TDA_TCA_RAISED_MST GROUP BY VOUCHER_NO HAVING  "+
	            	 	  	  " VOUCHER_NO =(select max(VOUCHER_NO) from FAS_TDA_TCA_RAISED_MST where to_date(CASHBOOK_MONTH||'-'||CASHBOOK_YEAR,'mm-yyyy') between to_date(4||'-'||?,'mm-yyyy') and to_date(3||'-'||?,'mm-yyyy'))";	
	                      ps=con.prepareStatement(sql);
	                      ps.setInt(1,fin_year_from);
	                      ps.setInt(2,fin_year_to);
	            	 	  rs=ps.executeQuery();
	                      if(rs.next()) 
	                      {
	                    	  Originated_SL_No = rs.getInt(1);                                               
	                      }
	                      Originated_SL_No=Originated_SL_No+1;
	                      rs.close();
	             }           	    
	             catch(Exception e){System.out.println("exception"+e );}
	            	             
	             try{cmbReason=Integer.parseInt(request.getParameter("cmbReason"));}
	             catch(NumberFormatException e){System.out.println("exception"+e );}
	             
	             try{txtUnitId=Integer.parseInt(request.getParameter("txtUnitId"));}
	             catch(NumberFormatException e){System.out.println("exception"+e );}
	              
	             try{txtDebitHead=Integer.parseInt(request.getParameter("txtDebitHead"));}
	             catch(NumberFormatException e){System.out.println("exception"+e );}
	            
	             try{cmbMas_SL_type=Integer.parseInt(request.getParameter("cmbMas_SL_type"));}
	             catch(NumberFormatException e){System.out.println("exception"+e );}
	            
	             try{cmbMas_SL_Code=Integer.parseInt(request.getParameter("cmbMas_SL_Code"));}
	             catch(NumberFormatException e){System.out.println("exception"+e );}
	             
	             try{paid_to=request.getParameter("txtMas_PaidTo");}
	             catch(NumberFormatException e){System.out.println("exception"+e );}
	                                    
	             try{txtRemarks=request.getParameter("txtRemarks");}
	             catch(NumberFormatException e){System.out.println("exception"+e );}
	            
	             try{txtTotalAmt=Double.parseDouble(request.getParameter("txtTotalAmt"));}
	             catch(Exception e){System.out.println("exception"+e );}
	             
	             try{Journal_type=request.getParameter("Journal_type");}
	             catch(Exception e){System.out.println("Journal_type "+e );}
	             if(Journal_type.equals("TDAO"))
	            	 	  cr_dr_indicator="DR";
	             else
	            	 	  cr_dr_indicator="CR";
	             
	             
	             //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~        
	                 
	             try 
	             {  
	                      con.clearWarnings();
	                      con.setAutoCommit(false);
	                      ps=con.prepareStatement("insert into FAS_TDA_TCA_RAISED_MST(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,ADVICE_TYPE,VOUCHER_DATE,CR_DR_INDICATOR,ACCOUNT_HEAD_CODE,PAID_TO,TRF_ACCOUNTING_UNIT_ID,REASON_FOR_TRANSFER,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,TOTAL_AMOUNT,PARTICULARS,STATUS,UPDATED_BY_USERID,UPDATED_DATE,TDA_OR_TCA) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
	                      ps.setInt(1,cmbAcc_UnitCode);
	                      ps.setInt(2,cmbOffice_code);
	                      ps.setInt(3,txtCash_year);
	                      ps.setInt(4,txtCash_Month_hid);
	                      ps.setInt(5,Originated_SL_No);
	                      ps.setString(6,"J");
	                      ps.setDate(7,txtCrea_date);
	                      ps.setString(8,cr_dr_indicator);                      
	                      ps.setInt(9,txtDebitHead);                      
	                      ps.setString(10,paid_to);
	                      ps.setInt(11,txtUnitId);
	                      ps.setInt(12,cmbReason);
	                      ps.setInt(13,cmbMas_SL_type);
	                      ps.setInt(14,cmbMas_SL_Code);
	                      ps.setDouble(15,txtTotalAmt);
	                      ps.setString(16,txtRemarks);
	                      ps.setString(17,"L");
	                      ps.setString(18,update_user);
	                      ps.setTimestamp(19,ts);
	                      ps.setString(20,Journal_type);
	                      errcode=ps.executeUpdate();
	                      if(errcode==0)
	                      {         
	                          System.out.println("redirect");
	                          sendMessage(response,"The TDA Creation Failed ","ok");                                      
	                      }
	                      else
	                      {  
	                                                   
	                          String Grid_H_code[]=request.getParameterValues("H_code");
	                          String Grid_CR_DR_type[]=request.getParameterValues("CR_DR_type");
	                          String Grid_SL_type[]=request.getParameterValues("SL_type");
	                          String Grid_SL_code[]=request.getParameterValues("SL_code");                          
	                          String Grid_sl_amt[]=request.getParameterValues("sl_amt");
	                          String Grid_particular[]=request.getParameterValues("sl_particular");                         
	                          String Trn_Paid_To[]=request.getParameterValues("Paid_To"); 
	                          
	                          String grid_bookno[]=request.getParameterValues("m_bookno");                          
	                          String grid_bookpageno[]=request.getParameterValues("m_bookpageno");
	                          String grid_bookdate[]=request.getParameterValues("m_bookdate"); 
	                          Date sl_ref_date=null;
	                          int SL_NO=0,cmbSL_type=0,cmbSL_Code=0,ref_num=0,bookPageNo=0,bookNo=0;
	                          double txtsub_Amount=0;
	                          try
	                          {
	                                      sql="insert into FAS_TDA_TCA_RAISED_TRN(ACCOUNTING_UNIT_ID, ACCOUNTING_FOR_OFFICE_ID, CASHBOOK_YEAR, CASHBOOK_MONTH, VOUCHER_NO, SL_NO, ACCOUNT_HEAD_CODE, CR_DR_INDICATOR, SUB_LEDGER_TYPE_CODE, SUB_LEDGER_CODE,PAID_TO, AMOUNT, PARTICULARS, UPDATED_BY_USERID, UPDATED_DATE,MBOOK_NO,MBOOK_PAGENO,MBOOK_DATE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)" ;                          
	                                      ps=con.prepareStatement(sql);
	                                      for(int k=0;k<Grid_H_code.length;k++) 
	                                      {                                                                                                        
	                                                cmbSL_type=0;cmbSL_Code=0;ref_num=0;
	                                                txtAcc_HeadCode=0;  txtsub_Amount=0; 
	                                                txtsub_Amount=0;
	                                                                                  
	                                                SL_NO++;
	                                                ps.setInt(1,cmbAcc_UnitCode);     
	                                                ps.setInt(2,cmbOffice_code);    
	                                                ps.setInt(3,txtCash_year);
	                                                ps.setInt(4,txtCash_Month_hid);
	                                                ps.setInt(5,Originated_SL_No);       
	                                                ps.setInt(6,SL_NO);
	                                                
	                                                txtAcc_HeadCode=Integer.parseInt(Grid_H_code[k]);
	                                                ps.setInt(7,txtAcc_HeadCode);
	                                                
	                                                String rad_sub_CR_DR=Grid_CR_DR_type[k];                               
	                                                ps.setString(8,rad_sub_CR_DR);   
	                                                
	                                                try{cmbSL_type=Integer.parseInt(Grid_SL_type[k]);}
	                                                catch(NumberFormatException e){System.out.println("exception"+e );}
	                                                ps.setInt(9,cmbSL_type); 
	                                                
	                                                try{cmbSL_Code=Integer.parseInt(Grid_SL_code[k]);}
	                                                catch(NumberFormatException e){System.out.println("exception"+e );}
	                                                ps.setInt(10,cmbSL_Code);
	                                                
	                                                ps.setString(11,Trn_Paid_To[k]);
	                                                                                   
	                                                try{txtsub_Amount=Double.parseDouble(Grid_sl_amt[k]);}
	                                                catch(NumberFormatException e){System.out.println("exception"+e );}
	                                                ps.setDouble(12,txtsub_Amount);
	                                                
	                                                ps.setString(13,Grid_particular[k]);      
	                                                
	                                                ps.setString(14,update_user);
	                                                ps.setTimestamp(15,ts);
	                                               
	                                                try{bookNo=Integer.parseInt(grid_bookno[k]);}
	                                                catch(NumberFormatException e){System.out.println("exception"+e );}
	                                                ps.setInt(16,bookNo); 
	                                                
	                                                try{bookPageNo=Integer.parseInt(grid_bookpageno[k]);}
	                                                catch(NumberFormatException e){System.out.println("exception"+e );}
	                                                ps.setInt(17,bookPageNo);
	                                               
	                                                try
					                                 {
							                             	 if(!grid_bookdate[k].equalsIgnoreCase(""))
							                                 {
									                                 sd=grid_bookdate[k].split("/");
									                                 c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
									                                 d=c.getTime();
									                                 bkDate=new Date(d.getTime());
									                                 ps.setDate(18,bkDate);
									                                
							                                 }
							                                 else
							                                 {
							                                	 	 ps.setNull(18,java.sql.Types.DATE);
							                                 }
						    	                    	 
					                                 }
					                                 catch(Exception e) {
					                                     	 System.out.println(e);
					                                 }
	                                              
	                                                int i=ps.executeUpdate(); 
	                                                if(i>0)
	                                                    count++;
	                                        }
	                            }
	                            catch(Exception e)
	                            {
	                                        e.getStackTrace();
	                                        System.out.println("Err in value setting for insertion:::"+e.getMessage());
	                                        con.rollback();
	                            }
	                            ps.close();
	                        //    System.out.println("Length:  "+count+" "+Grid_H_code.length);
	                            if(count==Grid_H_code.length)
	                            {
	                                     //   System.out.println("b4 commit");
	                                        con.commit();
	                                        if(Journal_type.equals("TDAO"))
	                                        {
	                                        	//sendMessage(response,"The TDA Originated Sl.No '"+Originated_SL_No+"' has been Created Successfully ","ok");
	                                        	sendMessage_test(response,"The TDA Originated Sl.No '"+Originated_SL_No+"' has been Created Successfully ","ok",request);
	                                        }
	                                        else
	                                        	sendMessage(response,"The TCA Originated Sl.No '"+Originated_SL_No+"' has been Created Successfully ","ok");
	                            }
	                            else
	                            {
	                                        System.out.println("b4 Rollback");
	                                        con.rollback();
	                            }
	                            
	                      }
	                     
	                  }
	                  
	                  catch(Exception e) 
	                  {
	                      try{con.rollback();}catch(SQLException sqle){System.out.println("exception in rollback "+sqle);}
	                      e.getStackTrace();
	                      if(Journal_type.equals("TDAO"))
	                    	  	sendMessage(response,"The TDA Creation Failed ","ok");
	                      else
	                  	  		sendMessage(response,"The TCA Creation Failed ","ok");                    	  	
	                      System.out.println("Exception occur due to "+e);
	                      
	                  }
	                  finally
	                  {
	                     
	                      try{con.setAutoCommit(true);  }catch(SQLException sqle){}
	                  }
		             
		                  
		                
	        }
	        else  if(strCommand.equalsIgnoreCase("AddSupp")) 
	        {
	             String CONTENT_TYPE = "text/html; charset=windows-1252";
	             response.setContentType(CONTENT_TYPE);
	            
	             //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	             Calendar c;
	             int txtAcc_HeadCode=0,cmbAcc_UnitCode=0,cmbOffice_code=0,txtCash_Month_hid=0,txtCash_year=0,txtUnitId=0,txtDebitHead=0;
	             int count=0,cmbMas_SL_type=0,cmbMas_SL_Code=0,cmbReason=0;
	             double txtTotalAmt=0;
	             Date txtCrea_date=null;
	             String txtRemarks="",paid_to="",Journal_type="",cr_dr_indicator="",sql="";
	             
	                                     // changes here
	             String update_user=(String)session.getAttribute("UserId");
	             long l=System.currentTimeMillis();
	             Timestamp ts=new Timestamp(l);                      
	             int errcode=0,supplement_no=0;
	             
	             //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	                                
	             try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
	             catch(NumberFormatException e){System.out.println("exception"+e );}
	           //  System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);
	             
	             try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
	             catch(NumberFormatException e){System.out.println("exception"+e );}
	          //   System.out.println("cmbOffice_code "+cmbOffice_code);
	             
	             String[] sd=request.getParameter("txtCrea_date").split("/");
	             c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
	             java.util.Date d=c.getTime();
	             txtCrea_date=new Date(d.getTime());
	     
	             try{txtCash_year=Integer.parseInt(sd[2]);}
	             catch(Exception e){System.out.println("exception"+e );}
	           //  System.out.println("txtCash_year "+txtCash_year);
	             
	             try{txtCash_Month_hid=Integer.parseInt(sd[1]);}
	             catch(Exception e){System.out.println("exception"+e );}
	           //  System.out.println("txtCash_Month_hid "+txtCash_Month_hid);
	             try {
	                 supplement_no =Integer.parseInt(request.getParameter("supNo"));
	                 System.out.println("supplement_no>>>>>>"+supplement_no);
	             } catch (Exception e) {
	                 System.out.println("exception in supplement_no" + e);
	             }
	             
	             
	             int Originated_SL_No=0;
	             int fin_year_from=0,fin_year_to=0;
	             
	             //////////////////////Financial year calculation/////////////////////////////////
	             if(txtCash_Month_hid>3)
	             {
	            	 	  fin_year_from=txtCash_year;
	            	 	  fin_year_to=txtCash_year+1;
	             }
	             else
	             {
	            	 	  fin_year_from=txtCash_year-1;
	            	 	  fin_year_to=txtCash_year;
	             }
	             System.out.println("fin_year_from ::: "+fin_year_from+"  fin_year_to:::"+fin_year_to);
	             try
	             {
	            	 	  sql="SELECT VOUCHER_NO FROM FAS_TDA_TCA_RAISED_MST GROUP BY VOUCHER_NO HAVING  "+
	            	 	  	  " VOUCHER_NO =(select max(VOUCHER_NO) from FAS_TDA_TCA_RAISED_MST where to_date(CASHBOOK_MONTH||'-'||CASHBOOK_YEAR,'mm-yyyy') between to_date(4||'-'||?,'mm-yyyy') and to_date(3||'-'||?,'mm-yyyy'))";	
	                      ps=con.prepareStatement(sql);
	                      ps.setInt(1,fin_year_from);
	                      ps.setInt(2,fin_year_to);
	            	 	  rs=ps.executeQuery();
	                      if(rs.next()) 
	                      {
	                    	  Originated_SL_No = rs.getInt(1);                                               
	                      }
	                      Originated_SL_No=Originated_SL_No+1;
	                      rs.close();
	             }           	    
	             catch(Exception e){System.out.println("exception"+e );}
	            	             
	             try{cmbReason=Integer.parseInt(request.getParameter("cmbReason"));}
	             catch(NumberFormatException e){System.out.println("exception"+e );}
	             
	             try{txtUnitId=Integer.parseInt(request.getParameter("txtUnitId"));}
	             catch(NumberFormatException e){System.out.println("exception"+e );}
	              
	             try{txtDebitHead=Integer.parseInt(request.getParameter("txtDebitHead"));}
	             catch(NumberFormatException e){System.out.println("exception"+e );}
	            
	             try{cmbMas_SL_type=Integer.parseInt(request.getParameter("cmbMas_SL_type"));}
	             catch(NumberFormatException e){System.out.println("exception"+e );}
	            
	             try{cmbMas_SL_Code=Integer.parseInt(request.getParameter("cmbMas_SL_Code"));}
	             catch(NumberFormatException e){System.out.println("exception"+e );}
	             
	             try{paid_to=request.getParameter("txtMas_PaidTo");}
	             catch(NumberFormatException e){System.out.println("exception"+e );}
	                                    
	             try{txtRemarks=request.getParameter("txtRemarks");}
	             catch(NumberFormatException e){System.out.println("exception"+e );}
	            
	             try{txtTotalAmt=Double.parseDouble(request.getParameter("txtTotalAmt"));}
	             catch(Exception e){System.out.println("exception"+e );}
	             
	             try{Journal_type=request.getParameter("Journal_type");}
	             catch(Exception e){System.out.println("Journal_type "+e );}
	             if(Journal_type.equals("TDAO"))
	            	 	  cr_dr_indicator="DR";
	             else
	            	 	  cr_dr_indicator="CR";
	             
	           /*  if(supplement_no==1)
	             {
	            	 Journal_type=Journal_type+supplement_no;
	             }
	             else
	             {
	            	 Journal_type=Journal_type+supplement_no;
	             }*/
	             System.out.println("Journal_type for supp::::"+Journal_type);
	             //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~        
	                 
	             try 
	             {  
	                      con.clearWarnings();
	                      con.setAutoCommit(false);
	                      ps=con.prepareStatement("insert into FAS_TDA_TCA_RAISED_MST(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,ADVICE_TYPE,VOUCHER_DATE,CR_DR_INDICATOR,ACCOUNT_HEAD_CODE,PAID_TO,TRF_ACCOUNTING_UNIT_ID,REASON_FOR_TRANSFER,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,TOTAL_AMOUNT,PARTICULARS,STATUS,UPDATED_BY_USERID,UPDATED_DATE,TDA_OR_TCA,SUPPLEMENT_NO) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
	                      ps.setInt(1,cmbAcc_UnitCode);
	                      ps.setInt(2,cmbOffice_code);
	                      ps.setInt(3,txtCash_year);
	                      ps.setInt(4,txtCash_Month_hid);
	                      ps.setInt(5,Originated_SL_No);
	                      ps.setString(6,"J");
	                      ps.setDate(7,txtCrea_date);
	                      ps.setString(8,cr_dr_indicator);                      
	                      ps.setInt(9,txtDebitHead);                      
	                      ps.setString(10,paid_to);
	                      ps.setInt(11,txtUnitId);
	                      ps.setInt(12,cmbReason);
	                      ps.setInt(13,cmbMas_SL_type);
	                      ps.setInt(14,cmbMas_SL_Code);
	                      ps.setDouble(15,txtTotalAmt);
	                      ps.setString(16,txtRemarks);
	                      ps.setString(17,"L");
	                      ps.setString(18,update_user);
	                      ps.setTimestamp(19,ts);
	                      ps.setString(20,Journal_type);
	                      ps.setInt(21,supplement_no);
	                      
	                      errcode=ps.executeUpdate();
	                      if(errcode==0)
	                      {         
	                          System.out.println("redirect");
	                          sendMessage(response,"The TDA Supplement Creation Failed ","ok");                                      
	                      }
	                      else
	                      {  
	                                                   
	                          String Grid_H_code[]=request.getParameterValues("H_code");
	                          String Grid_CR_DR_type[]=request.getParameterValues("CR_DR_type");
	                          String Grid_SL_type[]=request.getParameterValues("SL_type");
	                          String Grid_SL_code[]=request.getParameterValues("SL_code");                          
	                          String Grid_sl_amt[]=request.getParameterValues("sl_amt");
	                          String Grid_particular[]=request.getParameterValues("sl_particular");                         
	                          String Trn_Paid_To[]=request.getParameterValues("Paid_To"); 
	                          
	                          String grid_bookno[]=request.getParameterValues("m_bookno");                          
	                          String grid_bookpageno[]=request.getParameterValues("m_bookpageno");
	                          String grid_bookdate[]=request.getParameterValues("m_bookdate"); 
	                          Date sl_ref_date=null;
	                          int SL_NO=0,cmbSL_type=0,cmbSL_Code=0,ref_num=0,bookPageNo=0,bookNo=0;
	                          double txtsub_Amount=0;
	                          try
	                          {
	                                      sql="insert into FAS_TDA_TCA_RAISED_TRN(ACCOUNTING_UNIT_ID, ACCOUNTING_FOR_OFFICE_ID, CASHBOOK_YEAR, CASHBOOK_MONTH, VOUCHER_NO, SL_NO, ACCOUNT_HEAD_CODE, CR_DR_INDICATOR, SUB_LEDGER_TYPE_CODE, SUB_LEDGER_CODE,PAID_TO, AMOUNT, PARTICULARS, UPDATED_BY_USERID, UPDATED_DATE,MBOOK_NO,MBOOK_PAGENO,MBOOK_DATE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)" ;                          
	                                      ps=con.prepareStatement(sql);
	                                      for(int k=0;k<Grid_H_code.length;k++) 
	                                      {                                                                                                        
	                                                cmbSL_type=0;cmbSL_Code=0;ref_num=0;
	                                                txtAcc_HeadCode=0;  txtsub_Amount=0; 
	                                                txtsub_Amount=0;
	                                                                                  
	                                                SL_NO++;
	                                                ps.setInt(1,cmbAcc_UnitCode);     
	                                                ps.setInt(2,cmbOffice_code);    
	                                                ps.setInt(3,txtCash_year);
	                                                ps.setInt(4,txtCash_Month_hid);
	                                                ps.setInt(5,Originated_SL_No);       
	                                                ps.setInt(6,SL_NO);
	                                                
	                                                txtAcc_HeadCode=Integer.parseInt(Grid_H_code[k]);
	                                                ps.setInt(7,txtAcc_HeadCode);
	                                                
	                                                String rad_sub_CR_DR=Grid_CR_DR_type[k];                               
	                                                ps.setString(8,rad_sub_CR_DR);   
	                                                
	                                                try{cmbSL_type=Integer.parseInt(Grid_SL_type[k]);}
	                                                catch(NumberFormatException e){System.out.println("exception"+e );}
	                                                ps.setInt(9,cmbSL_type); 
	                                                
	                                                try{cmbSL_Code=Integer.parseInt(Grid_SL_code[k]);}
	                                                catch(NumberFormatException e){System.out.println("exception"+e );}
	                     
	                                                ps.setInt(10,cmbSL_Code);
	                                                
	                                                ps.setString(11,Trn_Paid_To[k]);
	                                                                                   
	                                                try{txtsub_Amount=Double.parseDouble(Grid_sl_amt[k]);}
	                                                catch(NumberFormatException e){System.out.println("exception"+e );}
	                                                ps.setDouble(12,txtsub_Amount);
	                                                
	                                                ps.setString(13,Grid_particular[k]);      
	                                                
	                                                ps.setString(14,update_user);
	                                                ps.setTimestamp(15,ts);
	                                               
	                                                try{bookNo=Integer.parseInt(grid_bookno[k]);}
	                                                catch(NumberFormatException e){System.out.println("exception"+e );}
	                                                ps.setInt(16,bookNo); 
	                                                
	                                                try{bookPageNo=Integer.parseInt(grid_bookpageno[k]);}
	                                                catch(NumberFormatException e){System.out.println("exception"+e );}
	                                                ps.setInt(17,bookPageNo);
	                                               
	                                                try
					                                 {
							                             	 if(!grid_bookdate[k].equalsIgnoreCase(""))
							                                 {
									                                 sd=grid_bookdate[k].split("/");
									                                 c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
									                                 d=c.getTime();
									                                 bkDate=new Date(d.getTime());
									                                 ps.setDate(18,bkDate);
									                                
							                                 }
							                                 else
							                                 {
							                                	 	 ps.setNull(18,java.sql.Types.DATE);
							                                 }
						    	                    	 
					                                 }
					                                 catch(Exception e) {
					                                     	 System.out.println(e);
					                                 }
	                                              
	                                                int i=ps.executeUpdate(); 
	                                                if(i>0)
	                                                    count++;
	                                        }
	                            }
	                            catch(Exception e)
	                            {
	                                        e.getStackTrace();
	                                        System.out.println("Err in value setting for insertion:::"+e.getMessage());
	                                        con.rollback();
	                            }
	                            ps.close();
	                        //    System.out.println("Length:  "+count+" "+Grid_H_code.length);
	                            if(count==Grid_H_code.length)
	                            {
	                                     //   System.out.println("b4 commit");
	                                        con.commit();
	                                        if(Journal_type.equals("TDAO"))
	                                        {
	                                        	sendMessage(response,"The TDA Originated Sl.No '"+Originated_SL_No+"' has been Created Successfully ","ok");
	                                        }
	                                        else
	                                        	sendMessage(response,"The TCA Originated Sl.No '"+Originated_SL_No+"' has been Created Successfully ","ok");
	                            }
	                            else
	                            {
	                                        System.out.println("b4 Rollback");
	                                        con.rollback();
	                            }
	                            
	                      }
	                     
	                  }
	                  
	                  catch(Exception e) 
	                  {
	                      try{con.rollback();}catch(SQLException sqle){System.out.println("exception in rollback "+sqle);}
	                      e.getStackTrace();
	                      if(Journal_type.equals("TDAO"))
	                    	  	sendMessage(response,"The TDA Supplement Creation Failed ","ok");
	                      else
	                  	  		sendMessage(response,"The TCA Supplement Creation Failed ","ok");                    	  	
	                      System.out.println("Exception occur due to "+e);
	                      
	                  }
	                  finally
	                  {
	                     
	                      try{con.setAutoCommit(true);  }catch(SQLException sqle){}
	                  }
		             
		                  
		                
	        }
	    }
	   
	    private void sendMessage(HttpServletResponse response,String msg,String bType)
	    {
	        try
	        {
	                  String url="org/Library/jsps/MessengerOkBack.jsp?message=" + msg + "&button=" + bType;
	                  response.sendRedirect(url);
	                  
	        }
	        catch(IOException e)
	        {
	        }
	    }
	    private void sendMessage_test(HttpServletResponse response,String msg,String bType,HttpServletRequest request)
	    {
	        try
	        {
	        	RequestDispatcher rd;
	                  String url="org/Library/jsps/MessengerOkBack.jsp?message=" + msg + "&button=" + bType;
	                  rd=request.getRequestDispatcher(url);
	                  rd.forward(request,response);
	                  
	                  
	                  
	        }
	        catch(Exception e)
	        {
	        }
	    }
	   
}