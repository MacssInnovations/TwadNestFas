package Servlets.FAS.FAS1.AccountHeadDirectory.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class SL_Progress_Bal_new extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
    
  
    public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
                                                           {
    
    
     int CashBookYear=0; 
     String CashBook_Year=null;
     int CashBookMonth_from=0,CashBookYear_from=0;
     
     /** Get Cashbook Year */
     try {
         CashBook_Year=request.getParameter("txtCB_Year");
         CashBookYear=Integer.parseInt(CashBook_Year);  
           
     }
     catch (Exception e) {
         System.out.println(e);
     }
    /** Get Cashbook Month */
     int CashBookMonth=0;    
     String CashBook_Month =null;
     
     try {
         CashBook_Month=request.getParameter("txtCB_Month");
         CashBookMonth=Integer.parseInt(CashBook_Month);   
     }
     catch (Exception e) {
         System.out.println(e);
     }
    
      /**Calculate Previous Month and Year**/
      if(CashBookMonth==1) 
      {
          CashBookMonth_from=12;
          CashBookYear_from=CashBookYear-1;
      }
      
      else {
          CashBookMonth_from=CashBookMonth-1;
          CashBookYear_from=CashBookYear;
      }
     
      /**End**/
      
      System.out.println("Cashbook year="+CashBookYear);
      System.out.println("Cashbook year="+CashBookMonth);
      System.out.println("Cashbook year="+CashBookMonth_from);
      System.out.println("Cashbook year="+CashBookYear_from);
   
       /**
        * Variables Declarations 
        */
      //dhana
      
        Connection connection=null;
        Statement statement=null;
        ResultSet results2=null,results_jan=null,results_ins=null,rs4=null,results_mn=null,rs7=null;
        ResultSet results3=null,results_three=null,result_3=null,resul=null,results_month=null,rs5=null;
        PreparedStatement ps=null; 
        PreparedStatement ps1=null;
        PreparedStatement ps5=null;
        PreparedStatement ps4=null,presta=null;
        String sqlquery5="";
        String sqlquery6="";
        int Month_OB_Debit=0,Month_OB_Credit=0,Month_CL_Debit=0,Month_CL_Credit=0,Month_CB_Credit=0,Month_CB_Debit=0;
        int Month_CB=0;
         String Month_CB_Indicator="";
      int acchcode=0,count_cb=0,count_one=0;
        /**
         * Database Connection
         */
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
                           
                         //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
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
        PrintWriter out = response.getWriter();
        HttpSession session=request.getSession(false);
        
        
        
        /**
         * Session Checking 
         */         
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
        
        
        
        /**
         * Get Employee ID 
         */
        String userid=(String)session.getAttribute("UserId");
        System.out.println("session id is:"+userid);
        
      
        /**
         * Get Parameters 
         */
         
        /** Get Accounting Unit ID */  
        String Account_unit_Code=request.getParameter("cmbAcc_UnitCode");
        
        /** Get Accounting Office ID */ 
        String Office_Code=request.getParameter("cmbOffice_code");
         
        /** 
         *  Variables Declarations 
         */
        int AccountUnitCode=0,no_permit=0;
        int OfficeCode=0,count_sql_test=0,pb_count=0;
     String update_user = (String)session.getAttribute("UserId");
     long l = System.currentTimeMillis();
     Timestamp ts = new Timestamp(l);
     Date ctdate = new java.sql.Date(ts.getTime()); 
     SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
     System.out.println(dateFormat);
     java.util.Date d; 
    // d = dateFormat;
     dateFormat.applyPattern("dd-MMM-yyyy");
    // appor_date = dateFormat.format(d);
    // String update_user=(String)session.getAttribute("UserId");
        /**
         * Convert String Data into Intger Value 
         */
        try 
        {
                AccountUnitCode=Integer.parseInt(Account_unit_Code);
                OfficeCode=Integer.parseInt(Office_Code);
         }catch(Exception e) 
        {
                System.out.println("Exception in Converting Integer:"+e);    
        }
        
        String tb_status=null;
        try 
        {
                String sql=" select                \n" + 
                "  tb_status                                              \n" + 
                "from                                          \n" + 
                "  fas_trial_balance_status              \n" + 
                "where                                         \n" + 
                "  cashbook_year=?                      \n" + 
                "and cashbook_month=?                        \n" + 
                "and accounting_unit_id =?\n" + 
                "order by accounting_unit_id ";
                ps=connection.prepareStatement(sql);
                ps.setInt(1,CashBookYear);
                ps.setInt(2,CashBookMonth);
                ps.setInt(3,AccountUnitCode);
                results2=ps.executeQuery();
                if(results2.next()) 
                {
                    tb_status = results2.getString("tb_status");
                } 
        }
        catch(Exception e) 
        {
                System.out.println("Exception in Status checking ::: "+e.getMessage());   
        }
        int cy=0;
       // System.out.println("tb_status ::: "+tb_status);
        if(tb_status.equals("Y")) 
        {
            
        	if(CashBookMonth<4)
            {
        		cy=CashBookYear-1;
            }
            else
            {
            	cy=CashBookYear;
            }
        	
        	try{
        		
                String sql_cb="select SL_STATUS from FAS_SL_CB_STATUS where CASHBOOK_YEAR=? and ACCOUNTING_UNIT_ID=?";
               // System.out.println("sql_cb::::"+sql_cb);
                ps=connection.prepareStatement(sql_cb);
            
                ps.setInt(1,cy);
                ps.setInt(2,AccountUnitCode);
                results_three=ps.executeQuery();  
                while(results_three.next())
                {
                count_cb++;
                }
                ps.close();
                results_three.close();
            }
            catch(Exception excep1)  {
                System.out.println("excep in fetching FAS_SL_CB_STATUS records:::"+excep1);
            }
            if(count_cb==0) {
               String msg="SL Closing Balance Not Verified";
               msg = msg + "<br><br>" ;
               sendMessage( response , msg , "ok" ); 
            }
            else{
        
            try{
                String sql_test="select ACCOUNTING_UNIT_ID from FAS_SL_PBSTATUS where accounting_unit_id=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=?";
               // System.out.println("sql_test::::"+sql_test);
                ps=connection.prepareStatement(sql_test);
                ps.setInt(1,AccountUnitCode);
                ps.setInt(2,CashBookYear);
                ps.setInt(3,CashBookMonth);
                result_3=ps.executeQuery();  
                while(result_3.next())
                {
                count_sql_test++;
                    try{
                        String sql_test_hh="select ACCOUNTING_UNIT_ID from FAS_SL_PBSTATUS where accounting_unit_id=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and SL_PB_FREEZE_STATUS='Y'";
                       // System.out.println("sql_test::::"+sql_test_hh);
                        ps=connection.prepareStatement(sql_test_hh);
                        ps.setInt(1,AccountUnitCode);
                        ps.setInt(2,CashBookYear);
                        ps.setInt(3,CashBookMonth);
                        resul=ps.executeQuery();  
                        while(resul.next())
                        {
                        no_permit++;
                        }
                        ps.close();
                    }
                    catch(Exception excep1)  {
                        System.out.println("excep in fetching FAS_SL_PBSTATUS records:::"+excep1);
                    }
                }
                ps.close();
              //  results3.close();
            }
            catch(Exception excep1)  {
                System.out.println("excep in fetching FAS_SL_PBSTATUS records:::"+excep1);
            }
            if(no_permit>0) {
                String msg="PB Updation Already Froze::::::";
                msg = msg + "<br><br>" ;
                sendMessage( response , msg , "ok" ); 
            }
            else{
            
            if(CashBookMonth==4) {
            
                try{
                String mon_sql="select SL_PB_STATUS from FAS_SL_PBSTATUS where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH =4 ";
                 ps4=connection.prepareStatement(mon_sql);
                 
                 ps4.setInt(1,AccountUnitCode);
                 ps4.setInt(2,OfficeCode);
                 ps4.setInt(3,CashBookYear);
                 
                results_month=ps4.executeQuery();  
                    while(results_month.next())
                    {
                    //if record is there then delete tat record and add
                        try{
                            String finalUpdate1="delete from FAS_SL_PBSTATUS where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=4";
                            ps5=connection.prepareStatement(finalUpdate1);
                            ps5.setInt(1,AccountUnitCode);          
                            ps5.setInt(2,OfficeCode);
                            ps5.setInt(3,CashBookYear);
                           
                            ps5.executeUpdate();  
                            ps5.close();
                        }
                        catch(Exception e) {
                            System.out.println("excep in :::insert into FAS_SL_PBSTATUS::::::"+e);
                        }
                    }
                    ps4.close();
                    results_month.close();
                }
                catch(Exception ee) {
                    System.out.println("exception in select query::::::"+ee);
                }
            
            try{
                String finalUpdate1="insert into FAS_SL_PBSTATUS(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,SL_PB_STATUS,SL_PB_DATE) values (?,?,?,?,?,?)"  ;
                ps4=connection.prepareStatement(finalUpdate1);
                ps4.setInt(1,AccountUnitCode);          
                ps4.setInt(2,OfficeCode);
                ps4.setInt(3,CashBookYear);
                ps4.setInt(4,CashBookMonth);
                ps4.setString(5,"Y");
                ps4.setTimestamp(6,ts);
                int tt=ps4.executeUpdate();  
                if(tt>0)
                pb_count++;
                
                
                ps4.close();
            }
            catch(Exception e) {
                System.out.println("excep in :::insert into FAS_SL_PBSTATUS::::::"+e);
            }
                
            }
            else if(CashBookMonth==1) {
            try{
            // int mn_one=CashBookMonth-1;
            String mon_sql="select SL_PB_STATUS from FAS_SL_PBSTATUS where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH =12";
             ps4=connection.prepareStatement(mon_sql);
             
             ps4.setInt(1,AccountUnitCode);
             ps4.setInt(2,OfficeCode);
             ps4.setInt(3,CashBookYear_from);
             
            results_jan=ps4.executeQuery();  
            while(results_jan.next())
            {
          //  System.out.println("inside while:::******12:::::");
            pb_count++;
                try{
                 String mon_sql_test="select SL_PB_STATUS from FAS_SL_PBSTATUS where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH =1 ";
                  ps4=connection.prepareStatement(mon_sql_test);
                  
                  ps4.setInt(1,AccountUnitCode);
                  ps4.setInt(2,OfficeCode);
                  ps4.setInt(3,CashBookYear);
                  
                 results_ins=ps4.executeQuery();  
                     while(results_ins.next())
                     {
                     //if record is there then delete tat record and add
                         try{
                             String finalUpdate1="delete from FAS_SL_PBSTATUS where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=1";
                             ps5=connection.prepareStatement(finalUpdate1);
                             ps5.setInt(1,AccountUnitCode);          
                             ps5.setInt(2,OfficeCode);
                             ps5.setInt(3,CashBookYear);
                            
                             ps5.executeUpdate();  
                             ps5.close();
                         }
                         catch(Exception e) {
                             System.out.println("excep in :::insert into FAS_SL_PBSTATUS::::::"+e);
                         }
                     }
                     ps4.close();
                     results_ins.close();
                 }
                 catch(Exception ee) {
                     System.out.println("exception in select query::::::"+ee);
                 }
                 
                try{
                    String finalUpdate1="insert into FAS_SL_PBSTATUS(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,SL_PB_STATUS,SL_PB_DATE) values (?,?,?,?,?,?)"  ;
                    ps5=connection.prepareStatement(finalUpdate1);
                    ps5.setInt(1,AccountUnitCode);          
                    ps5.setInt(2,OfficeCode);
                    ps5.setInt(3,CashBookYear);
                    ps5.setInt(4,CashBookMonth);
                    ps5.setString(5,"Y");
                    ps5.setTimestamp(6,ts);
                    ps5.executeUpdate();  
                    ps5.close();
                }
                catch(Exception e) {
                    System.out.println("excep in :::insert into FAS_SL_PBSTATUS::::::"+e);
                }
            }
                ps4.close();
                results_jan.close();
            }
            catch(Exception e3) {
               System.out.println("exception in fetching records from FAS_SL_PBSTATUS:"+e3.getMessage());
            }
            }
            else {
                try{
                 int mn_one=CashBookMonth-1;
                String mon_sql="select SL_PB_STATUS from FAS_SL_PBSTATUS where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH =?";
                 ps4=connection.prepareStatement(mon_sql);
                 
                 ps4.setInt(1,AccountUnitCode);
                 ps4.setInt(2,OfficeCode);
                 ps4.setInt(3,CashBookYear);
                 ps4.setInt(4,mn_one);
                results_mn=ps4.executeQuery(); 
                    while(results_mn.next())
                    {
                  //  System.out.println("inside while: for not 12 month:::");
                    pb_count++;
                        try{
                         String mon_sql_test="select SL_PB_STATUS from FAS_SL_PBSTATUS where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH =? ";
                          ps4=connection.prepareStatement(mon_sql_test);
                          
                          ps4.setInt(1,AccountUnitCode);
                          ps4.setInt(2,OfficeCode);
                          ps4.setInt(3,CashBookYear);
                          ps4.setInt(4,CashBookMonth);
                         rs4=ps4.executeQuery();  
                             while(rs4.next())
                             {
                             //if record is there then delete tat record and add
                                 try{
                                     String finalUpdate1="delete from FAS_SL_PBSTATUS where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=?";
                                     ps5=connection.prepareStatement(finalUpdate1);
                                     ps5.setInt(1,AccountUnitCode);          
                                     ps5.setInt(2,OfficeCode);
                                     ps5.setInt(3,CashBookYear);
                                     ps5.setInt(4,CashBookMonth);
                                     ps5.executeUpdate(); 
                                     
                                     ps5.close();
                                 }
                                 catch(Exception e) {
                                     System.out.println("excep in :::insert into FAS_SL_PBSTATUS::::::"+e);
                                 }
                             }
                             ps4.close();
                             rs4.close();
                         }
                         catch(Exception ee) {
                             System.out.println("exception in select query::::::"+ee);
                         }
                    
                        try{
                            String finalUpdate1="insert into FAS_SL_PBSTATUS(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,SL_PB_STATUS,SL_PB_DATE) values (?,?,?,?,?,?)"  ;
                            ps5=connection.prepareStatement(finalUpdate1);
                            ps5.setInt(1,AccountUnitCode);          
                            ps5.setInt(2,OfficeCode);
                            ps5.setInt(3,CashBookYear);
                            ps5.setInt(4,CashBookMonth);
                            ps5.setString(5,"Y");
                            ps5.setDate(6,ctdate);
                            ps5.executeUpdate();  
                            ps5.close();
                        }
                        catch(Exception e) {
                            System.out.println("excep in :::insert into FAS_SL_PBSTATUS::::::"+e);
                        }
                    }
                        ps4.close();
                        results_mn.close();
                }
                catch(Exception exc) {
                    
                }
                
            }
            if(pb_count==0)   {
                String msg="PB Updation for Previous Month Not Done*****";
                msg = msg + "<br><br>" ;
                sendMessage( response , msg , "ok" ); 
            }
            else{
        //pradha coding ended
         
            
            int year_one=CashBookYear-1;
            int year_two=CashBookYear-2;
            
            try{
                String sql_one="select TB_STATUS from FAS_ANNUAL_TB_CLOSURE where FINANCIAL_YEAR=? ";
                System.out.println("sql_one::::"+sql_one);
                ps=connection.prepareStatement(sql_one);
              
                if(CashBookMonth<4)
                    ps.setString(1,year_two+"-"+year_one);
                 else
                 {
                	 ps.setString(1,year_one+"-"+CashBookYear); 
                 }
               System.out.println("fin:::****:::::"+year_one+"-"+CashBookYear);
                rs5=ps.executeQuery();  
                while(rs5.next())
                {
                count_one++;
                }
                
            }
            catch(Exception excep)  {
                System.out.println("excep in fetching FAS_ANNUAL_TB_CLOSURE records:::"+excep);
            }
            
            if(count_one==0) {
             String msg="Annual TB Closure is Not Done:::";
             msg = msg + "<br><br>" ;
             sendMessage( response , msg , "ok" );  
            }
            else{
            
               try {
                      System.out.println("check");
                      CallableStatement cs1=null;
                      int result_code = -1;
                      int error_code = -1;
                      cs1=connection.prepareCall("call  FAS_SL_GL_TB_GENERATION_CHECK(?::numeric,?::numeric,?::numeric,?::numeric,?,?::numeric,?::numeric) ");
                      cs1.setInt(1,AccountUnitCode);
                      cs1.setInt(2,0);
                      cs1.setInt(3,CashBookMonth);
                      cs1.setInt(4,CashBookYear);
                      cs1.setString(5,"TB_FREEZE");
                      cs1.registerOutParameter(6,java.sql.Types.NUMERIC);
                      cs1.registerOutParameter(7,java.sql.Types.NUMERIC);
                      cs1.setNull(6,java.sql.Types.NUMERIC);
                      cs1.setNull(7,java.sql.Types.NUMERIC);
                      cs1.execute();
                      
//                      result_code=cs1.getInt(6);
//                      error_code=cs1.getInt(7);
                      result_code = cs1.getBigDecimal(6).intValue();
                      error_code = cs1.getBigDecimal(7).intValue();
                    //  System.out.println(result_code);
                      if ( error_code == 0 ) {
                        
                        System.out.println("result_code ::: "+result_code);
                          if (result_code ==-10000 || result_code ==50 || result_code==0) {
                                System.out.println("inside result code -10000");
                                connection.setAutoCommit(false);
                                /**
                                   * deleteing current month entries first..
                                */ 
                              
                                String sql_del="delete from FAS_SL_PB_one where accounting_unit_id=? and accounting_for_office_id=? and year=? and month=? ";
                                ps=connection.prepareStatement(sql_del);
                                ps.setInt(1,AccountUnitCode);
                                ps.setInt(2,OfficeCode);
                                ps.setInt(3,CashBookYear);
                                ps.setInt(4,CashBookMonth);
                                ps.executeUpdate();
                                
                             //   System.out.println("FAS_SL_PB deleted successfully");
                                CallableStatement cs2=null;
                                if(CashBookMonth==4)
                                {
                                     //   System.out.println("Enter into April Month-----------------------------------------------------------");
                                    System.out.println("Enter into April Month-----------------------------------------------------------"+AccountUnitCode);
                                    System.out.println("Enter into April Month-----------------------------------------------------------"+OfficeCode);
                                    System.out.println("Enter into April Month-----------------------------------------------------------"+CashBookMonth);
                                    System.out.println("Enter into April Month-----------------------------------------------------------"+CashBookYear);
                                        cs2=connection.prepareCall("call FAS_SL_PB_APRIL(?::numeric,?::numeric,?::numeric,?::numeric,?,?::numeric) ");
                                        cs2.setInt(1,AccountUnitCode);
                                        cs2.setInt(2,OfficeCode);
                                        cs2.setInt(3,CashBookMonth);
                                        cs2.setInt(4,CashBookYear);                                       
                                        cs2.setString(5,userid);
                                        cs2.registerOutParameter(6,java.sql.Types.NUMERIC);
                                        cs2.setNull(6,java.sql.Types.NUMERIC);
                                        cs2.execute();
                                        //int errcode=cs2.getInt(6);.
                                        int errcode = cs2.getBigDecimal(6).intValue();
                                   // System.out.println("Enter into April Month-----------------------------------------------------------"+errcode);
                                        if(errcode==0) 
                                        {
                                            connection.commit();
                                            connection.setAutoCommit(true);
                                            String msg="SL Progressive Balance is done successfully";
                                            msg = msg + "<br><br>" ;
                                           // sendMessage( response , msg , "ok" );  
                                        }
                                        else {
                                            connection.rollback();
                                            String msg="Cannot Update SL Progressive Balance";
                                            msg = msg + "<br><br>" ;
                                            sendMessage( response , msg , "ok" );  
                                        }
                                }
                                else 
                                {   
                                	System.out.println("comes here::::");
                                        cs2=connection.prepareCall("call FAS_PROGRESSIVEBALANCE_MAY_SL(?::numeric,?::numeric,?::numeric,?::numeric,?::numeric,?::numeric,?,?::numeric) ");
                                        cs2.setInt(1,AccountUnitCode);
                                        cs2.setInt(2,OfficeCode);
                                        cs2.setInt(3,CashBookMonth);
                                        cs2.setInt(4,CashBookYear);
                                        cs2.setInt(5,CashBookMonth_from);
                                        cs2.setInt(6,CashBookYear_from);
                                        cs2.setString(7,userid);
                                        cs2.registerOutParameter(8,java.sql.Types.NUMERIC);
                                        cs2.setNull(8,java.sql.Types.NUMERIC);
                                        cs2.execute();
                                        //int errcode=cs2.getInt(8);
                                        int errcode = cs2.getBigDecimal(8).intValue();
                                        if(errcode==0) 
                                        {
                                            connection.commit();
                                            connection.setAutoCommit(true);
                                            String msg="SL Progressive Balance is done successfully";
                                            msg = msg + "<br><br>" ;
                                            //sendMessage( response , msg , "ok" );  
                                        }
                                        else {
                                            connection.rollback();
                                            String msg="Cannot Update SL Progressive Balance for"+CashBookMonth;
                                            msg = msg + "<br><br>" ;
                                            sendMessage( response , msg , "ok" );  
                                        }
                                }
                                
                                
                          }
                         
                          else  {
                              System.out.println("first else");
                              //sendMessage(response,"Cannot Update SL Progressive Balance","ok");
                              return;
                          }
                     // System.out.println("inside error_code 0");
                      
                      /** Updation of Sub ledger 25-03-2011*/
                      
                      try{   
                          String updatesql="select ACCOUNT_HEAD_CODE,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,UPTO_DEBIT_BALANCE," +
                          "UPTO_CREDIT_BALANCE,CURRENT_YEAR_DEBIT_BALANCE,CURRENT_YEAR_CREDIT_BALANCE," +
                          "MONTH_OPENING_BALANCE,MONTH_OPENING_BAL_DR_CR_IND ,CURRENT_MONTH_DEBIT," +
                          "CURRENT_MONTH_CREDIT,FINANCIAL_YEAR,YEAR,MONTH,MONTH_CLOSING_BALANCE,MONTH_CLOSING_BAL_DR_CR_IND " +
                          "from FAS_SL_PB_one where accounting_unit_id=? and ACCOUNTING_FOR_OFFICE_ID=? and YEAR=? and MONTH=?";
                          
                         ps=connection.prepareStatement(updatesql);
                         
                          ps.setInt(1,AccountUnitCode);
                         ps.setInt(2,OfficeCode);
                          ps.setInt(3,CashBookYear);
                          ps.setInt(4,CashBookMonth);
                          rs7=ps.executeQuery();  
                          while(rs7.next())
                          {
                             double  month_cl_credit=0;
                        	  double month_cl_debit=0;
                        	  String month_CB_ind="";
                        	//  System.out.println("vvvvvvvvvvvvvvvvvvvv"+results3.getString("MONTH_OPENING_BAL_DR_CR_IND"));
                        	if(rs7.getString("MONTH_OPENING_BAL_DR_CR_IND").trim().equalsIgnoreCase("CR"))
                        	{
                        		   month_cl_credit=rs7.getDouble("MONTH_OPENING_BALANCE")+rs7.getDouble("CURRENT_MONTH_CREDIT");
                        	//	   System.out.println("enter if&&&&&&&&&&&&"+month_cl_credit);
                        		   month_cl_debit=rs7.getDouble("CURRENT_MONTH_DEBIT");
                        	}
                        	    if(rs7.getString("MONTH_OPENING_BAL_DR_CR_IND").trim().equalsIgnoreCase("DR"))
                        	{
                                
//                        	    System.out.println("enter else---------"+results3.getDouble("CURRENT_MONTH_DEBIT"));
//                        	    System.out.println("enter else---------"+results3.getDouble("MONTH_OPENING_BALANCE"));
                                
                        		  month_cl_debit=rs7.getDouble("MONTH_OPENING_BALANCE")+rs7.getDouble("CURRENT_MONTH_DEBIT");
                        		//  System.out.println("enter else---------"+month_cl_debit);
                        		  month_cl_credit=rs7.getDouble("CURRENT_MONTH_CREDIT");
                        	}
                        	
                       	  
                       	
                       	  
                       	  
                       	  double month_cl_db_cr=-(month_cl_debit-month_cl_credit);
                       	 // System.out.println("balance"+month_cl_db_cr);
                       	  
                       	  if(month_cl_db_cr<0)
                       	  {
                       		month_cl_db_cr=(month_cl_db_cr)*(-1) ;
                       		 // month_cl_credit=0;
                       		month_CB_ind="DR";
                       	  }else
                       	  {
                       		month_cl_db_cr= month_cl_db_cr;
                       		//  month_cl_debit=0;
                       		month_CB_ind="CR";
                       	  }
                       	 String finalUpdate="update FAS_SUB_LEDGER_MASTER set " +
                         " UPTO_DEBIT_BALANCE=?,UPTO_CREDIT_BALANCE=? ,CURRENT_YEAR_DEBIT_BALANCE=? ," +
                         " CURRENT_YEAR_CREDIT_BALANCE=?,MONTH_OPENING_BALANCE=? ,MONTH_OPENING_BAL_DR_CR_IND=?,PB_UPDATED_BY_USER_ID=? ," +
                         " PB_UPDATED_DATE=? where accounting_unit_id=? and ACCOUNTING_FOR_OFFICE_ID=? and YEAR=? and MONTH=? and " +
                         " ACCOUNT_HEAD_CODE=? and SUB_LEDGER_TYPE_CODE=? and SUB_LEDGER_CODE=? "  ;
//                       	 System.out.println("********************final******updatequery"+finalUpdate);
//                       System.out.println("upto db"+results3.getDouble("UPTO_DEBIT_BALANCE"));
                       	 ps1=connection.prepareStatement(finalUpdate);
                       	 ps1.setDouble(1, rs7.getDouble("UPTO_DEBIT_BALANCE"));
                       	 ps1.setDouble(2,rs7.getDouble("UPTO_CREDIT_BALANCE"));
                    	 ps1.setDouble(3,rs7.getDouble("CURRENT_YEAR_DEBIT_BALANCE"));
                    	 ps1.setDouble(4,rs7.getDouble("CURRENT_YEAR_CREDIT_BALANCE"));
                    	 if(CashBookMonth==4)
                    	 {
                    		// month_cl_db_cr=rs7.getDouble("MONTH_OPENING_BALANCE");
                    		 ps1.setDouble(5,rs7.getDouble("MONTH_OPENING_BALANCE")); 
                    	 }
                    	 else{
                    	 ps1.setDouble(5,month_cl_db_cr);
                    	 }
                    	 ps1.setString(6, month_CB_ind);
                    	 ps1.setString(7,update_user);
                    	 ps1.setTimestamp(8, ts);
                       	 ps1.setInt(9,AccountUnitCode); 
                       	 ps1.setInt(10,OfficeCode);
                        ps1.setInt(11,CashBookYear);
                        ps1.setInt(12,CashBookMonth);
                        ps1.setInt(13,rs7.getInt("ACCOUNT_HEAD_CODE"));
                      ps1.setInt(14,rs7.getInt("SUB_LEDGER_TYPE_CODE"));
                      ps1.setInt(15,rs7.getInt("SUB_LEDGER_CODE"));
                            
                        int pb_one=ps1.executeUpdate();
                        System.out.println("pb_one:::::::::::::::::;"+pb_one);
                        if(pb_one>0)
                        {
                        	System.out.println("executed successfully");
                        }
                        else
                        {
                        	if(CashBookMonth==4)
                        	{
                        	System.out.println("ttt"+rs7.getInt("ACCOUNT_HEAD_CODE"));
                        	 presta=connection.prepareStatement("insert into FAS_SUB_LEDGER_MASTER(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID," +
                        	 		"FINANCIAL_YEAR,YEAR,MONTH,ACCOUNT_HEAD_CODE,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,Project_Id,MONTH_OPENING_BALANCE," +
                        	 		"Month_Opening_Bal_Dr_Cr_Ind,Current_Month_Debit,Current_Month_Credit,Month_Closing_Balance," +
                        	 		"MONTH_CLOSING_BAL_DR_CR_IND,Updated_By_User_Id,Updated_Date,Pb_Updated_By_User_Id) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                       
                        	 presta.setInt(1,AccountUnitCode);
                        	 presta.setInt(2,OfficeCode);
                        	 presta.setString(3,rs7.getString("FINANCIAL_YEAR"));
                        	 presta.setInt(4,rs7.getInt("YEAR"));
                        	 presta.setInt(5,rs7.getInt("MONTH"));
                        	 presta.setInt(6,rs7.getInt("ACCOUNT_HEAD_CODE"));
                        	 presta.setInt(7,rs7.getInt("SUB_LEDGER_TYPE_CODE"));
                        	 presta.setInt(8,rs7.getInt("SUB_LEDGER_CODE"));
                        	 presta.setInt(9,0);
                        //	 System.out.println("be4444444444444");
                        	 presta.setDouble(10,rs7.getDouble("MONTH_OPENING_BALANCE"));
                        	 presta.setString(11,rs7.getString("MONTH_OPENING_BAL_DR_CR_IND").trim());
                        	 presta.setDouble(12,rs7.getDouble("CURRENT_MONTH_DEBIT"));
                        //	 System.out.println("2222222222222222222222222222222:"+rs7.getString("MONTH_OPENING_BAL_DR_CR_IND")+":::");
                        	 presta.setDouble(13,rs7.getDouble("CURRENT_MONTH_CREDIT"));
                        	 presta.setDouble(14,rs7.getDouble("Month_Closing_Balance"));
                        	 
                        	 presta.setString(15,rs7.getString("MONTH_CLOSING_BAL_DR_CR_IND"));
                        	 System.out.println("dr::::::"+rs7.getString("MONTH_CLOSING_BAL_DR_CR_IND"));
                        	 presta.setString(16,update_user);
                        	 presta.setTimestamp(17,ts);
                        	 presta.setString(18,update_user);
                        	 int newinsert=presta.executeUpdate();
                        	 System.out.println("newinsert::::"+newinsert);
                        	// presta.setInt(19,rs7.getInt("PB_UPDATED_DATE"));
                        	 
                        }
                        
                        }
                       	   
                          }
                          
                          
                                        connection.commit();
                                        connection.setAutoCommit(true);
                                        
                                        sendMessage(response," Updated SL Progressive Balance Successfully **","ok");
                                 
                                
                                }
                      catch(Exception e){
                                    connection.rollback();
                                    String msg="Cannot Update SL Progressive Balance:";
                                    msg = msg + "<br><br>" ;
                                    sendMessage( response , msg , "ok" ); 
                                
                                System.out.println("*************Exception"+e.getMessage());
                                } 
                      
                      
                  // Cal Closing Balnce For FAS_SUB_LEDGER_MASTER
                  
                   try{   
                       String updatesql="select ACCOUNT_HEAD_CODE,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE," +
                       " MONTH_OPENING_BALANCE,MONTH_OPENING_BAL_DR_CR_IND,CURRENT_MONTH_DEBIT,CURRENT_MONTH_CREDIT " +
                       " from FAS_SUB_LEDGER_MASTER where accounting_unit_id=? and ACCOUNTING_FOR_OFFICE_ID=? and YEAR=? and MONTH=?";
                       
                      ps=connection.prepareStatement(updatesql);
                      
                       ps.setInt(1,AccountUnitCode);
                      ps.setInt(2,OfficeCode);
                       ps.setInt(3,CashBookYear);
                       ps.setInt(4,CashBookMonth);
                       
                     results3=ps.executeQuery();  
                       while(results3.next())
                       {
                          System.out.println("enter*&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&Year"+CashBookYear+" month"+CashBookMonth);
                           System.out.println("**************************AcHead Code "+results3.getInt("ACCOUNT_HEAD_CODE"));
                           System.out.println("**************************SUB_LEDGER_TYPE_CODE "+results3.getInt("SUB_LEDGER_TYPE_CODE"));
                           System.out.println("**************************SUB_LEDGER_CODE "+results3.getInt("SUB_LEDGER_CODE"));
                               double  month_cl_credit=0;
                               double month_cl_debit=0;
                               String month_CB_ind="";
                               System.out.println("vvvvvvvvvvvvvvvvvvvv"+results3.getString("MONTH_OPENING_BAL_DR_CR_IND"));
                             if(results3.getString("MONTH_OPENING_BAL_DR_CR_IND").trim().equalsIgnoreCase("CR"))
                             {
                                        month_cl_credit=results3.getDouble("MONTH_OPENING_BALANCE")+results3.getDouble("CURRENT_MONTH_CREDIT");
                                        System.out.println("enter if&&&&&&&&&&&&"+month_cl_credit);
                                        month_cl_debit=results3.getDouble("CURRENT_MONTH_DEBIT");
                             }
                                 if(results3.getString("MONTH_OPENING_BAL_DR_CR_IND").trim().equalsIgnoreCase("DR"))
                             {
                             
                                System.out.println("enter else---------"+results3.getDouble("CURRENT_MONTH_DEBIT"));
                                 System.out.println("enter else---------"+results3.getDouble("MONTH_OPENING_BALANCE"));
                             
                                       month_cl_debit=results3.getDouble("MONTH_OPENING_BALANCE")+results3.getDouble("CURRENT_MONTH_DEBIT");
                                       System.out.println("enter else---------"+month_cl_debit);
                                       month_cl_credit=results3.getDouble("CURRENT_MONTH_CREDIT");
                             }
                             
                       
                     
                       
                       
                       double month_cl_db_cr=-(month_cl_debit-month_cl_credit);
                     //  System.out.println("balance"+month_cl_db_cr);
                       
                       if(month_cl_db_cr<0)
                       {
                             month_cl_db_cr=(month_cl_db_cr)*(-1) ;
                              // month_cl_credit=0;
                             month_CB_ind="DR";
                       }else
                       {
                             month_cl_db_cr= month_cl_db_cr;
                             //  month_cl_debit=0;
                             month_CB_ind="CR";
                       }
                      String finalUpdate="update FAS_SUB_LEDGER_MASTER   set MONTH_CLOSING_BALANCE=?, MONTH_CLOSING_BAL_DR_CR_IND=? " +
                      " where accounting_unit_id=? and ACCOUNTING_FOR_OFFICE_ID=? and YEAR=? and MONTH=? and " +
                      " ACCOUNT_HEAD_CODE=? and SUB_LEDGER_TYPE_CODE=? and SUB_LEDGER_CODE=? "  ;
                      System.out.println("**************************updatequery"+finalUpdate);
                    
                      ps1=connection.prepareStatement(finalUpdate);
                     
                      ps1.setDouble(1,month_cl_db_cr);
                      ps1.setString(2, month_CB_ind);
                    
                      ps1.setInt(3,AccountUnitCode); 
                      ps1.setInt(4,OfficeCode);
                         ps1.setInt(5,CashBookYear);
                         ps1.setInt(6,CashBookMonth);
                         ps1.setInt(7,results3.getInt("ACCOUNT_HEAD_CODE"));
                           ps1.setInt(8,results3.getInt("SUB_LEDGER_TYPE_CODE"));
                           ps1.setInt(9,results3.getInt("SUB_LEDGER_CODE"));
                         
                       ps1.executeUpdate();  
                        
                       }
                       
                       
                                     connection.commit();
                                     connection.setAutoCommit(true);
                                     
                                     sendMessage(response," Updated SL Progressive Balance Successfully","ok");
                              
                             
                             }catch(Exception e){
                                 connection.rollback();
                                 String msg="Cannot Update SL Progressive Balance";
                                 msg = msg + "<br><br>" ;
                                 sendMessage( response , msg , "ok" ); 
                             
                             System.out.println("*****************************************************************Exception"+e);} 
                   
                   
                   
                   
                   
                   
                   }
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  
                      
                  else {
                      connection.rollback();
                      String msg="Cannot Update SL Progressive Balance";
                      msg = msg + "<br><br>" ;
                      sendMessage( response , msg , "ok" ); 
                          System.out.println("2nd else");
                          sendMessage(response,"Cannot Update GL Progressive Balance","ok");
                          return;
                  }
                 
             }
             catch(Exception e ) {
                
                 System.out.println(e);
                 
             }
        }
           
            } 
        }//final coding end part
        }//cb_closed
        }
        else {
          
            System.out.println("3rd else");
             System.out.println("Cannot SL progressive balance,Because TB is not freezed yet");
             sendMessage(response,"Trial Balance Status is not Updated for this Month","ok");
            //dhana
               
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
            System.out.println("Eror in send message");
        }
    }   
    
    
}

