package Servlets.FAS.FAS1.TDALIST;


import Servlets.Security.classes.UserProfile;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.sql.Timestamp;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;



public class TDA_TCA_Verify_List extends HttpServlet 
{
    private static final String CONTENT_TYPE = "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }
    
    
    public void doPost(HttpServletRequest request, 
                      HttpServletResponse response) throws ServletException, IOException 
    {
        
        /**
         * Variables Declaration
         */
        String strCommand="";
        Connection con=null;
        PreparedStatement ps=null,ps1=null,ps3=null;      
        ResultSet rs5=null,rs3=null;
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
         * Database Connection 
         */
        try {
                               ResourceBundle rs1=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
                               String ConnectionString="";

                               String strDriver=rs1.getString("Config.DATA_BASE_DRIVER");
                               String strdsn=rs1.getString("Config.DSN");
                               String strhostname=rs1.getString("Config.HOST_NAME");
                               String strportno=rs1.getString("Config.PORT_NUMBER");
                               String strsid=rs1.getString("Config.SID");
                               String strdbusername=rs1.getString("Config.USER_NAME");
                               String strdbpassword=rs1.getString("Config.PASSWORD");
                               ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection                               Class.forName(strDriver.trim());
                               con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
              }
              catch(Exception e)
                  {
                     System.out.println("Exception in opening connection :"+e);
                  }
                  
                  
        /** Get Command Parameter */          
        try {        
            strCommand=request.getParameter("Command");            
        }        
        catch(Exception e) 
        {
            System.out.println("Exception in assigning..."+e);
        }
        
        
        
        if(strCommand.equalsIgnoreCase("Add")) 
        {
            String CONTENT_TYPE = "text/html; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);            
                 
            Calendar c;
            int cmbAcc_UnitCode=0;
            int cmbOffice_code=0;
            int txtVoucher_No=0;            
            int txtCash_year=0;
            int txtCash_Month_hid=0;
            
            /** Get Accounting Unit ID */
            try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);
            
            /** Get Accounting for Office ID */
            try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            System.out.println("cmbOffice_code "+cmbOffice_code);
            
            /** Get Cashbook Year */
            try{txtCash_year=Integer.parseInt(request.getParameter("txtCB_Year"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            System.out.println("txtCash_year "+txtCash_year);
                      
            /** Get Cashbook Month */
            try{txtCash_Month_hid=Integer.parseInt(request.getParameter("txtCB_Month"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            System.out.println("txtCash_Month_hid "+txtCash_Month_hid);
            
            
         try
         {
         
             con.clearWarnings();
             con.setAutoCommit(false);
                              
            String sql_update="" +
            "update                             \n" + 
            "   FAS_TDA_TCA_RAISED_TRN         \n" + 
            "set VERIFIED = 'Y',                \n" + 
            "    VERIFY_DATE = (select now() ) , \n" + 
            "    VERIFIED_AUTHORITY =?          \n" + 
            "where                              \n" + 
            "    ACCOUNTING_UNIT_ID = ?  and    \n" + 
            "    ACCOUNTING_FOR_OFFICE_ID = ? and       \n" + 
            "    CASHBOOK_YEAR = ? and                  \n" + 
            "    CASHBOOK_MONTH = ? and                 \n" + 
            "    voucher_no=? ";  
                              
                              
             ps=con.prepareStatement(sql_update);
             
             String Voucher_no[]=request.getParameterValues("Voucher_no");
             String Voucher_date[]=request.getParameterValues("Voucher_date");
             int txtusrid=0;
             UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
             txtusrid= empProfile.getEmployeeId();       
             
             System.out.println("user id::"+txtusrid);
             
             
             for(int k=0;k<Voucher_no.length;k++)                           
             {
                System.out.println("K---------------------------------<> "+k);
                try{
                	System.out.println("Enter into try");
                	txtVoucher_No=Integer.parseInt(Voucher_no[k]);
                System.out.println("======================================================================================>"+txtVoucher_No);
                }catch(Exception e){System.out.println("exception in trans "+e);}
                ps.setInt(1,txtusrid);
                ps.setInt(2,cmbAcc_UnitCode);
                ps.setInt(3,cmbOffice_code);     
                ps.setInt(4,txtCash_year);
                ps.setInt(5,txtCash_Month_hid);                               
                ps.setInt(6,txtVoucher_No);                      
                ps.executeUpdate();
                txtVoucher_No=0;                   
             }        
             
             con.commit();
             sendMessage(response,"The TDA_TCA Verified Successfully ","ok");
             
           } 
            catch(Exception e) 
            {
                try{con.rollback();}catch(SQLException sqle){System.out.println("Excep"+sqle);}
                sendMessage(response,"TDA_TCA Verification Failed ","ok");
                System.out.println("Exception occur due to "+e);
            }
            finally
            {                
                try{con.setAutoCommit(true);  }catch(SQLException sqle){System.out.println("Excep"+sqle);}
            }
        }
        else if(strCommand.equalsIgnoreCase("verify_list")) {
            System.out.println("this is for RJV_for_tda_tca_rejected:::");
            String rem=null;
            Calendar c;
            Date txtCrea_date=null,txtCrea_date_one=null;
            String CONTENT_TYPE = "text/html; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);     
           
           //String ="";
            int cmbAcc_UnitCode=0,cmbOffice_code=0,txtCash_year=0,txtCash_Month_hid=0,typejrnl=0,depriciation_rate=0;
            String particulars=null,flag=null,indic="",indicator="";
            int trn_count=0,tot_check=0,tda_tca_year=0,tda_tca_Month=0,code=0;
            int suc_val=0;
            String update_user=(String)session.getAttribute("UserId");
            long l=System.currentTimeMillis();
            Timestamp ts=new Timestamp(l);                      
             Date ctdate = new java.sql.Date(ts.getTime());  
            
            String chckparameter_Voucher_no[]=null; 
            try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
          
            try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
          
            String[] sd=request.getParameter("txtCrea_date").split("/");
            c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
            java.util.Date d=c.getTime();
            txtCrea_date=new Date(d.getTime());
            
            try{txtCash_year=Integer.parseInt(sd[2]);}
            catch(Exception e){System.out.println("exception"+e );}
            
            try{txtCash_Month_hid=Integer.parseInt(sd[1]);}
            catch(Exception e){System.out.println("exception"+e );}
             
             typejrnl=Integer.parseInt(request.getParameter("jrnltype"));
          //   System.out.println("typejrnl:::"+typejrnl);
          
            String[] voucherno1=request.getParameterValues("v_number");
          //  System.out.println("voucherno1");
             String[] voucherDate1=request.getParameterValues("voucher_date"); 
          //   System.out.println("voucherDate1"+voucherDate1);
            String[] total_amt=request.getParameterValues("total_amount");
            String[] remarks_one=request.getParameterValues("remarks");
            
            int Originated_SL_No=0,sub_ledger_code=0;
            try
            {
                     ps=con.prepareStatement("select VOUCHER_NO from FAS_JOURNAL_MASTER GROUP BY VOUCHER_NO HAVING VOUCHER_NO =(select max(VOUCHER_NO) from FAS_JOURNAL_MASTER where ACCOUNTING_UNIT_ID=? and" +
                     " ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=?)");
                     ps.setInt(1,cmbAcc_UnitCode);
                     ps.setInt(2,cmbOffice_code);
                     ps.setInt(3,txtCash_year);
                     ps.setInt(4,txtCash_Month_hid);                      
                    rs5=ps.executeQuery();
                     if(rs5.next()) 
                     {
                               Originated_SL_No = rs5.getInt(1);                                               
                     }
                     Originated_SL_No=Originated_SL_No+1;
                     rs5.close();
            }           
            catch(Exception e){System.out.println("exception"+e );}
          //  System.out.println("Originated_SL_No "+Originated_SL_No);
            
            
            int voucherno2=0,count=0;
            double tamount=0.00;
            try{
                    con.clearWarnings();
                    con.setAutoCommit(false);
                    chckparameter_Voucher_no = request.getParameterValues("chckparameter"); 
             //   System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~chckparameter_Voucher_no>>>>"+chckparameter_Voucher_no);
                
                
                    System.out.println("chckparameter_Voucher_no>>>"+chckparameter_Voucher_no.length);
                
                    for(int i=0;i<chckparameter_Voucher_no.length;i++)
                    {
                       
                    System.out.println("for loop");
                        int asg=Integer.parseInt(chckparameter_Voucher_no[i]);
                        rem=remarks_one[asg];
                        System.out.println("rem::::"+rem);
                       
                        ps1=con.prepareStatement("insert into FAS_JOURNAL_MASTER(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,VOUCHER_DATE,CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,JOURNAL_TYPE_CODE,SUB_LEDGER_CODE,TOTAL_TRN_RECORDS,REMARKS," +
                        "MODE_OF_CREATION,CREATED_BY_MODULE,JOURNAL_STATUS,UPDATED_BY_USER_ID,UPDATED_DATE,DEPRECIATION_RATE,CB_REF_TYPE)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                        ps1.setInt(1,cmbAcc_UnitCode);
                        ps1.setInt(2,cmbOffice_code);
                        ps1.setDate(3,txtCrea_date);
                        ps1.setInt(4,txtCash_year);
                        ps1.setInt(5,txtCash_Month_hid);
                        ps1.setInt(6,Originated_SL_No);
                        ps1.setInt(7,typejrnl);System.out.println("typejrnl >> "+typejrnl);
                        ps1.setInt(8,sub_ledger_code);
                        ps1.setInt(9,2);
                   //     ps1.setString(10,particulars);
                    ps1.setString(10,rem);
                        ps1.setString(11,"A");
                        ps1.setString(12,"GJV");
                        ps1.setString(13,"L");
                        ps1.setString(14,update_user);
                        ps1.setTimestamp(15,ts);
                        ps1.setInt(16,depriciation_rate);
                        ps1.setString(17,"T");
                        int kk=ps1.executeUpdate();
                        if(kk>0)
                        {
                                flag="success";
                          //      System.out.println("Flag ::: "+flag);
                        }
                        else
                                flag="failure";
                    
                    
                        if(flag.equals("success"))
                        {
                      //  System.out.println("transactionnnnnnnnnnnnnnnnnnnnnn");
                                    //    trn_count=0;
                                        
                            voucherno2=Integer.parseInt(voucherno1[asg]); 
                           // System.out.println("voucherno2:::"+voucherno2);
                          //  System.out.println("voucherDate1[asg]:::"+voucherDate1[asg]);
                            try{
                               String[] sd1 = voucherDate1[asg].split("-");
                               c =                new GregorianCalendar(Integer.parseInt(sd1[2]), Integer.parseInt(sd1[1]) - 1,
                                            Integer.parseInt(sd1[0]));
                               java.util.Date d1 = c.getTime();
                               txtCrea_date_one = new Date(d1.getTime());
                            
                            
                               System.out.println("txtCrea_date_one " + txtCrea_date_one);
                               
                            try{tda_tca_year=Integer.parseInt(sd1[2]);}
                            catch(Exception e){System.out.println("exception"+e );}
                             //  System.out.println("tda_tca_year "+tda_tca_year);
                            
                            try{tda_tca_Month=Integer.parseInt(sd1[1]);}
                            catch(Exception e){System.out.println("exception"+e );}
                              // System.out.println("tda_tca_Month "+tda_tca_Month);
                            }
                            catch(Exception eee) {
                                System.out.println("Exception in changing cbrefdate::::"+eee.getMessage());
                            }
                               
                                String query="select * from FAS_TDA_TCA_RAISED_TRN where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and CASHBOOK_YEAR="+tda_tca_year+" and CASHBOOK_MONTH="+tda_tca_Month+" and VOUCHER_NO="+voucherno2;
                          System.out.println("query for FAS_TDA_TCA_RAISED_TRN:::"+query);
                            ps3=con.prepareStatement(query);
                            rs3=ps3.executeQuery();
                            while(rs3.next()) 
                            {
                            System.out.println("111111111111111");
                         //   System.out.println("indicator::::"+rs3.getString("CR_DR_INDICATOR"));
                            indic=rs3.getString("CR_DR_INDICATOR");
                            if(indic.equals("DR")) {
                                code=rs3.getInt("ACCOUNT_HEAD_CODE");
                                indicator="CR";
                            }
                            else if(indic.equals("CR")) {
                                code=rs3.getInt("ACCOUNT_HEAD_CODE");
                                indicator="DR";
                            }
                                    count++;
//   on 12-11-19                                     ps=con.prepareStatement("insert into FAS_JOURNAL_TRANSACTION(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID," +
//                                        "CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,SL_NO,ACCOUNT_HEAD_CODE,CR_DR_INDICATOR,SUB_LEDGER_TYPE_CODE," +
//                                        "SUB_LEDGER_CODE,AMOUNT,PARTICULARS,CB_REF_NO,CB_REF_DATE,UPDATED_BY_USER_ID,UPDATED_DATE)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                                    
                                    ps=con.prepareStatement("insert into FAS_JOURNAL_TRANSACTION(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID," +
                                            "CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,SL_NO,ACCOUNT_HEAD_CODE,CR_DR_INDICATOR,SUB_LEDGER_TYPE_CODE," +
                                            "SUB_LEDGER_CODE,AMOUNT,PARTICULARS,CB_REF_NO,CB_TDCA_REF_NO,CB_TDCA_REF_DATE,UPDATED_BY_USER_ID,UPDATED_DATE)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                                        ps.setInt(1,cmbAcc_UnitCode);
                                        ps.setInt(2,cmbOffice_code);
                                        ps.setInt(3,txtCash_year);
                                        ps.setInt(4,txtCash_Month_hid);
                                        ps.setInt(5,Originated_SL_No);
                                        ps.setInt(6,count);
                                        ps.setInt(7,code);
                                        ps.setString(8,indicator);
                                        ps.setInt(9,rs3.getInt("SUB_LEDGER_TYPE_CODE"));
                                        ps.setInt(10,rs3.getInt("SUB_LEDGER_CODE"));
                                       // System.out.println("be44444444 amount");
                                    tamount=Double.parseDouble(total_amt[asg]);
                                   // System.out.println("tamount::::"+tamount);
                                        ps.setDouble(11,rs3.getDouble("AMOUNT"));
                                        ps.setString(12,rs3.getString("PARTICULARS"));
                                        ps.setInt(13, 0); 
                                        //ps.setInt(13, voucherno2); 
                                         ps.setInt(14, rs3.getInt("VOUCHER_NO")); 
                                        
                                        ps.setDate(15,txtCrea_date_one);
                                        ps.setString(16,update_user);
                                        ps.setTimestamp(17,ts);
                                        int row=ps.executeUpdate();
                                        if(row>0) {
                                        System.out.println("row:::"+row);
                                                 trn_count++;
                                             }
                              }
                            System.out.println("count:::"+count);
                            System.out.println("trn_count"+trn_count);
                            if(count==trn_count) {
                                
                            	 
                                    System.out.println("update");
                                    ps=con.prepareStatement("update FAS_TDA_TCA_RAISED_MST set RESPONDING_JVR_NO=?,RESPONDING_JVR_DATE=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=?");
                                  
                                    ps.setInt(1,Originated_SL_No);System.out.println("Originated_SL_No"+Originated_SL_No);
                                    ps.setDate(2,txtCrea_date);System.out.println("txtCrea_date"+txtCrea_date);
                                    ps.setInt(3,cmbAcc_UnitCode);
                                    ps.setInt(4,cmbOffice_code);
                                    ps.setInt(5,tda_tca_year);System.out.println("cashbook_year"+tda_tca_year);
                                    ps.setInt(6,tda_tca_Month);System.out.println("cashbook_month"+tda_tca_Month);
                                    ps.setInt(7,voucherno2);System.out.println("vouchno"+voucherno2);
                                    int kk1=ps.executeUpdate();
                                    if(kk1>0){
                                  suc_val=suc_val+0;
                                   
                                    }
                                    else {
                                    	  suc_val=suc_val+1; 
                                      
                                    }
                                }
                              else {
                                
                                    con.rollback();
                                    sendMessage(response,"* RJV Creation Failed *","ok");  
                                
                            }    
                        }
                 
                    }
                  if(suc_val==0){
                	  con.commit();
                      sendMessage(response,"The RJV has been Created Successfully ","ok");  
                  }
                  else{
                	  con.rollback();
                      sendMessage(response,"RJV Creation Failed in updation","ok");
                  }
                	  
               // tot_check=chckparameter_Voucher_no.length*2;
               
            }
            catch(Exception e)
            {
                try{con.rollback();}catch(SQLException sqle){System.out.println("exception in rollback "+sqle);}
                e.getStackTrace();                                                    
                System.out.println("Exception occur due to "+e);
                sendMessage(response,"RJV Creation Failed ::::","ok");
            }
            finally
            {
                         System.out.println("done");
                         try{con.setAutoCommit(true);  }catch(SQLException sqle){}
            }
        }
    }
    
       
    
    
    public void doGet(HttpServletRequest request, 
                      HttpServletResponse response) throws ServletException, IOException 
    {    
    
        /**
         * Variables Declaration
         */
        Connection con=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        int empid=0;
        int txtCash_year=0;
        int txtCash_Month_hid=0;
        int cmbAcc_UnitCode=0;
        int cmbOffice_code=0;
              
        
        /**
         * Session Checking 
         */
        try
        {
            HttpSession session=request.getSession(false);
            if(session==null)
            {
                System.out.println(request.getContextPath()+"/index.jsp");
                response.sendRedirect(request.getContextPath()+"/index.jsp");
                return;
            }
            System.out.println(session);            
            
            UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
            System.out.println("user id::"+empProfile.getEmployeeId());
            empid=empProfile.getEmployeeId();           
            
        }catch(Exception e)
        {
        System.out.println("Redirect Error :"+e);
        }
        
        
        
        /**
         * Database Connection 
         */
        try {
                              ResourceBundle rs1=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
                              String ConnectionString="";

                              String strDriver=rs1.getString("Config.DATA_BASE_DRIVER");
                              String strdsn=rs1.getString("Config.DSN");
                              String strhostname=rs1.getString("Config.HOST_NAME");
                              String strportno=rs1.getString("Config.PORT_NUMBER");
                              String strsid=rs1.getString("Config.SID");
                              String strdbusername=rs1.getString("Config.USER_NAME");
                              String strdbpassword=rs1.getString("Config.PASSWORD");
                              ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection                              Class.forName(strDriver.trim());
                              con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
             }
             catch(Exception e)
                 {
                    System.out.println("Exception in opening connection :"+e);                   

                 }
                 
                 
        
        response.setContentType(CONTENT_TYPE);
        response.setHeader("Cache-Control","no-cache");
        PrintWriter out = response.getWriter();
        String strCommand="";
        
        
        /**
         * Get Command Parameter 
         */
        try 
        {
            strCommand=request.getParameter("Command");           
        }        
        catch(Exception e) 
        {
            System.out.println("Exception in assigning..."+e);
        }
           
       
             String CONTENT_TYPE = "text/xml; charset=windows-1252";
             response.setContentType(CONTENT_TYPE);
             Calendar c;
             String xml="";
             
     /** Get Accounting Unit ID */
      try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
      catch(NumberFormatException e){System.out.println("exception"+e );}
      System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);
               
     /** Get Accounting for Office ID */
      try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
      catch(NumberFormatException e){System.out.println("exception"+e );}
      System.out.println("cmbOffice_code "+cmbOffice_code);
             
      /** Get Cashbook Year */
      try{txtCash_year=Integer.parseInt(request.getParameter("txtCB_Year"));}
      catch(NumberFormatException e){System.out.println("exception"+e );}
      System.out.println("txtCash_year "+txtCash_year);
                  
      /** Get Cashbook Month */
      try{txtCash_Month_hid=Integer.parseInt(request.getParameter("txtCB_Month"));}
      catch(NumberFormatException e){System.out.println("exception"+e );}
      System.out.println("txtCash_Month_hid "+txtCash_Month_hid);
        
                
       String fromdate=null;
       String todate=null; 
        
                
        /** Get From Date */
         try {           
            fromdate=request.getParameter("txtfromdate");            
            System.out.println("fromdate----->"+fromdate);
         }
         catch (Exception e){
           System.out.println(e);
         }
        
        
         /** Get To Date */
         try {            
            todate=request.getParameter("txttodate");    
            System.out.println("todate---------->"+todate);             
         }
         catch (Exception e) {
           System.out.println(e);
         }
        
        System.out.println("5");
        
        
        java.util.Date d=null;
        java.util.Date d1=null;
        java.sql.Date FromDate=null;
        java.sql.Date ToDate=null;
        
        
        /** Convert Java Date (From , To )  into SQL Date */  
        
        if( (!fromdate.equalsIgnoreCase("")) && (!todate.equalsIgnoreCase("")))  
        {
              System.out.println("6");
              try {
                    System.out.println("before converting date");
                    String dateString = fromdate;
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                     
                    d = dateFormat.parse(fromdate.trim());
                    System.out.println("util date is:"+d);
                    dateFormat.applyPattern("yyyy-MM-dd");
                    dateString = dateFormat.format(d);
                    FromDate = java.sql.Date.valueOf(dateString);
                     
                    
                    System.out.println("before converting date");
                    String dateString1 = todate;
                    SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
                                  
                    d1 = dateFormat1.parse(todate.trim());
                    dateFormat1.applyPattern("yyyy-MM-dd");
                    dateString1 = dateFormat1.format(d1);
                    ToDate = java.sql.Date.valueOf(dateString1);
                    
                    System.out.println("fromdate"+FromDate);
                    System.out.println("todate"+ToDate);
                    
                }
                catch (ParseException e) {
                    System.out.println(e);
                }
        }   
          
        String Query_String="";  
        
        
        /** Report by Cashbook Month/Year wise or Date wise */
        if(fromdate.equalsIgnoreCase("") && todate.equalsIgnoreCase(""))       
        {
            Query_String= " and  m.CASHBOOK_YEAR = "+txtCash_year+" and  m.CASHBOOK_MONTH = "+txtCash_Month_hid;
        }   
        else
        {  
            Query_String="and m.VOUCHER_DATE between  ?  and  ? ";            
        }         
        
        System.out.println("Query_String--------->"+Query_String); 
               
               
               
       
         xml="<response><command>loadPendingJournals</command>";
               
            try 
            {
                if(strCommand.equalsIgnoreCase("loadPendingJournals_NV")) 
                {
                    String sql= "select                                         \n" + 
                    "    m.VOUCHER_NO,                                          \n" + 
                    "    to_char(m.VOUCHER_DATE,'DD/MM/YYYY') as VOUCHER_DATE,  \n" + 
                    "    t.ACCOUNT_HEAD_CODE,                                   \n" + 
                    "    (select account_head_desc from com_mst_account_heads where account_head_code=t.account_head_code) as account_head_desc, \n" +  
                    "    t.CR_DR_INDICATOR,                                     \n" +                                      
                    "   trim(to_char(t.AMOUNT,'99999999999999.99')) as AMOUNT,  \n" +                                           
                    "    t.PARTICULARS                                          \n" + 
                    "from                                                       \n" + 
                    "   FAS_TDA_TCA_RAISED_MST m,                                   \n" + 
                    "   FAS_TDA_TCA_RAISED_TRN t                               \n" + 
                    "where                                                      \n" + 
                    "    m.ACCOUNTING_UNIT_ID  = t.ACCOUNTING_UNIT_ID and       \n" + 
                    "    m.ACCOUNTING_FOR_OFFICE_ID =  t.ACCOUNTING_FOR_OFFICE_ID and \n" + 
                    "    m.CASHBOOK_YEAR = t.CASHBOOK_YEAR and                  \n" + 
                    "    m.CASHBOOK_MONTH = t.CASHBOOK_MONTH and                \n" + 
                    "    m.VOUCHER_NO = t.VOUCHER_NO and                        \n" + 
                    "    m.ACCOUNTING_UNIT_ID = ? and                           \n" + 
                    "    m.ACCOUNTING_FOR_OFFICE_ID = ? and                     \n" + 
                    "    m.STATUS = 'L' and                             \n" +                    
                 
                    "    t.VERIFIED is null     "+Query_String;
                    
                    
                    System.out.println(sql);
                    ps=con.prepareStatement(sql);                    
                    
                    
                }   
                else if(strCommand.equalsIgnoreCase("loadPendingJournals_V")) 
                {
                    String sql=
                    "select                                                     \n" + 
                    "    m.VOUCHER_NO,                                          \n" + 
                    "    to_char(m.VOUCHER_DATE,'DD/MM/YYYY') as VOUCHER_DATE,  \n" + 
                    "    t.ACCOUNT_HEAD_CODE,                                   \n" + 
                    "    (select account_head_desc from com_mst_account_heads where account_head_code=t.account_head_code) as account_head_desc, \n" +  
                    "    t.CR_DR_INDICATOR,                                     \n" +                                      
                    "   trim(to_char(t.AMOUNT,'99999999999999.99')) as AMOUNT,  \n" +                                           
                    "    t.PARTICULARS                                          \n" + 
                    "from                                                       \n" + 
                    "   FAS_TDA_TCA_RAISED_MST m,                                   \n" + 
                    "   FAS_TDA_TCA_RAISED_TRN t                               \n" + 
                    "where                                                      \n" + 
                    "    m.ACCOUNTING_UNIT_ID  = t.ACCOUNTING_UNIT_ID and       \n" + 
                    "    m.ACCOUNTING_FOR_OFFICE_ID =  t.ACCOUNTING_FOR_OFFICE_ID and \n" + 
                    "    m.CASHBOOK_YEAR = t.CASHBOOK_YEAR and                  \n" + 
                    "    m.CASHBOOK_MONTH = t.CASHBOOK_MONTH and                \n" + 
                    "    m.VOUCHER_NO = t.VOUCHER_NO and                        \n" + 
                    "    m.ACCOUNTING_UNIT_ID = ? and                           \n" + 
                    "    m.ACCOUNTING_FOR_OFFICE_ID = ? and                     \n" + 
                    "    m.STATUS = 'L' and                             \n" +                    
                   
                    "    t.VERIFIED ='Y'     "+Query_String;          
                
                    System.out.println(sql);
                    ps=con.prepareStatement(sql);
                    
                 }
                                            
                ps.setInt(1,cmbAcc_UnitCode);
                ps.setInt(2,cmbOffice_code);       
                if(!fromdate.equalsIgnoreCase("") && !todate.equalsIgnoreCase(""))       
                {
                   ps.setDate(3,FromDate);
                   ps.setDate(4,ToDate);                    
                }   
                    
                    rs=ps.executeQuery();
                    int cnt=0;                   
                    xml=xml+"<flag>success</flag>";
                    while(rs.next()) 
                    {
                        xml=xml+
                        "<Voucher_no>"+rs.getInt("VOUCHER_NO")+"</Voucher_no>" +
                        "<Voucher_date>"+rs.getString("VOUCHER_DATE")+"</Voucher_date>" + 
                        
                        "<Account_Head>"+rs.getString("ACCOUNT_HEAD_CODE")+"</Account_Head>" +
                        "<Account_Head_Desc>"+rs.getString("account_head_desc")+"</Account_Head_Desc>" +
                        "<cr_dr_ind>"+rs.getString("CR_DR_INDICATOR")+"</cr_dr_ind>" +  
                        
                        "<Amount>"+rs.getString("AMOUNT") +"</Amount>" +
                        "<remarks>"+rs.getString("PARTICULARS")+"</remarks>";
                        cnt++;
                    } 
               
                    System.out.println("count"+cnt);
                    if(cnt==0)
                        xml="<response><command>loadPendingJournals</command>"+"<flag>failure</flag>";                    
            }
            catch(Exception e)
            {
            System.out.println("catch..HERE.in failure to retrieve."+e);
                xml="<response><command>loadPendingJournals</command>"+"<flag>failure</flag>";
            
            
            }
            xml=xml+"</response>";
            System.out.println(xml);
            out.println(xml);
        
        
        
      
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
           System.out.println("Excep"+e);
           }
       }
}

        
        
        
