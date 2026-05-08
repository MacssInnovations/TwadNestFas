package Servlets.FAS.FAS1.TPA.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Servlets.Security.classes.UserProfile;

/**
 * Servlet implementation class Verify_TPA_JVR
 */
public class Verify_TPA_JVR extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=windows-1252"; 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Verify_TPA_JVR() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        Date txtCrea_date=null;
        
        
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
               
               
           xml="<response><command>loadPendingJournals</command>";

               
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
        
           String tpaType=null;
        
         /** Get From Date */
          try {
            
             fromdate=request.getParameter("txtfromdate");            
             System.out.println("fromdate----->"+fromdate);
          }
          catch (Exception e) {
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
        
          try {
              
        	  tpaType=request.getParameter("tpatype");    
              System.out.println("tpaType---------->"+tpaType);             
           }
           catch (Exception e) {
             System.out.println(e);
           }
        String journalType="";
           if(tpaType.equalsIgnoreCase("CR"))
           {
        	   journalType="68,69";  
           }else if(tpaType.equalsIgnoreCase("DR"))
           {
        	   journalType="70,71";    
           }
        
        java.util.Date d=null;     
        java.util.Date d1=null;      
        java.sql.Date FromDate=null; 
        java.sql.Date ToDate=null; 
        System.out.println(fromdate+"   "+todate);
        System.out.println(fromdate!=null && todate!=null);
        
         /** Convert Java Date (From , To )  into SQL Date */  
         if((!fromdate.equalsIgnoreCase("")) && (!todate.equalsIgnoreCase("")))  
         {
               
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
         if(fromdate.equalsIgnoreCase("")  && todate.equalsIgnoreCase("") )       
         {
             Query_String= " and  m.CASHBOOK_YEAR = "+txtCash_year+" and  m.CASHBOOK_MONTH = "+txtCash_Month_hid;
         }   
         else
         {   
             Query_String="and m.VOUCHER_DATE between  ?  and  ? ";
             
         }         
         
         System.out.println("Query_String--------->"+Query_String); 
                
     
     
         try 
         {
                if(strCommand.equalsIgnoreCase("loadPendingJournals_NV")) 
                {
                    ps=con.prepareStatement("                                   \n" + 
                    "select                                                     \n" + 
                    "    m.VOUCHER_NO,                                          \n" + 
                    "    to_char(m.VOUCHER_DATE,'DD/MM/YYYY') as VOUCHER_DATE,  \n" + 
                    "    t.ACCOUNT_HEAD_CODE,                                   \n" + 
                    "    (select account_head_desc from com_mst_account_heads where account_head_code=t.account_head_code) as account_head_desc, \n" +  
                    "    t.CR_DR_INDICATOR,                                     \n" +                                      
                    "   trim(to_char(t.AMOUNT,'99999999999999.99')) as AMOUNT,  \n" +                                           
                    "    t.PARTICULARS                                          \n" + 
                    "from                                                       \n" + 
                    "   fas_journal_master m,                                   \n" + 
                    "   fas_journal_transaction t                               \n" + 
                    "where                                                      \n" + 
                    "    m.ACCOUNTING_UNIT_ID  = t.ACCOUNTING_UNIT_ID and       \n" + 
                    "    m.ACCOUNTING_FOR_OFFICE_ID =  t.ACCOUNTING_FOR_OFFICE_ID and \n" + 
                    "    m.CASHBOOK_YEAR = t.CASHBOOK_YEAR and                  \n" + 
                    "    m.CASHBOOK_MONTH = t.CASHBOOK_MONTH and                \n" + 
                    "    m.VOUCHER_NO = t.VOUCHER_NO and                        \n" + 
                    "    m.ACCOUNTING_UNIT_ID = ? and                           \n" + 
                    "    m.ACCOUNTING_FOR_OFFICE_ID = ? and                     \n" +   
                    "    m.JOURNAL_TYPE_CODE in("+journalType+") and                     \n" +   
                    "    m.JOURNAL_STATUS = 'L' and                             \n" +                    
                    "    m.CREATED_BY_MODULE='GJV' and                            " +
                    "    t.VERIFIED is null     "+Query_String+"                 ");
                    
                    
                    
                }   
                else if(strCommand.equalsIgnoreCase("loadPendingJournals_V")) 
                {
                    ps=con.prepareStatement("                                   \n" + 
                    "select                                                     \n" + 
                    "    m.VOUCHER_NO,                                          \n" + 
                    "    to_char(m.VOUCHER_DATE,'DD/MM/YYYY') as VOUCHER_DATE,  \n" + 
                    "    t.ACCOUNT_HEAD_CODE,                                   \n" + 
                    "    (select account_head_desc from com_mst_account_heads where account_head_code=t.account_head_code) as account_head_desc, \n" +  
                    "    t.CR_DR_INDICATOR,                                     \n" +                                      
                    "   trim(to_char(t.AMOUNT,'99999999999999.99')) as AMOUNT,  \n" +                                           
                    "    t.PARTICULARS                                          \n" + 
                    "from                                                       \n" + 
                    "   fas_journal_master m,                                   \n" + 
                    "   fas_journal_transaction t                               \n" + 
                    "where                                                      \n" + 
                    "    m.ACCOUNTING_UNIT_ID  = t.ACCOUNTING_UNIT_ID and       \n" + 
                    "    m.ACCOUNTING_FOR_OFFICE_ID =  t.ACCOUNTING_FOR_OFFICE_ID and \n" + 
                    "    m.CASHBOOK_YEAR = t.CASHBOOK_YEAR and                  \n" + 
                    "    m.CASHBOOK_MONTH = t.CASHBOOK_MONTH and                \n" + 
                    "    m.VOUCHER_NO = t.VOUCHER_NO and                        \n" + 
                    "    m.ACCOUNTING_UNIT_ID = ? and                           \n" + 
                    "    m.ACCOUNTING_FOR_OFFICE_ID = ? and                     \n" + 
                    "    m.JOURNAL_TYPE_CODE in(68,69,70,71) and                     \n" +  
                    "    m.JOURNAL_STATUS = 'L' and                             \n" +                    
                    "    m.CREATED_BY_MODULE='GJV' and                            " +
                    "    t.VERIFIED ='Y'        "+Query_String+"                  ");
                }
                    
                    ps.setInt(1,cmbAcc_UnitCode);
                    ps.setInt(2,cmbOffice_code);       
                    if((!fromdate.equalsIgnoreCase("")) && (!todate.equalsIgnoreCase("")))       
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  /* Variables Declaration
	         */
	        String strCommand="";
	        Connection con=null;
	        
	        
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
	                               ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection	                               Class.forName(strDriver.trim());
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
	            PreparedStatement ps=null;            
	            Calendar c;
	            int cmbAcc_UnitCode=0;
	            int cmbOffice_code=0;
	            int txtVoucher_No=0;
	            Date txtVoucher_Date=null;
	            Date txtCrea_date=null;
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
	            "update                                          \n" + 
	            "   fas_journal_transaction                      \n" + 
	            "set VERIFIED = 'Y',                             \n" + 
	            "    VERIFIED_DATE = (select now() ), \n" + 
	            "    VERIFIED_AUTHORITY =?                       \n" + 
	            "where                                           \n" + 
	            "    ACCOUNTING_UNIT_ID = ?  and                 \n" + 
	            "    ACCOUNTING_FOR_OFFICE_ID = ? and            \n" + 
	            "    CASHBOOK_YEAR = ? and                       \n" + 
	            "    CASHBOOK_MONTH = ? and                      \n" + 
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
	             
	                try{txtVoucher_No=Integer.parseInt(Voucher_no[k]);}catch(Exception e){System.out.println("exception in trans "+e);}
	                 
	                ps.setInt(1,txtusrid);
	                ps.setInt(2,cmbAcc_UnitCode);
	                ps.setInt(3,cmbOffice_code);     
	                ps.setInt(4,txtCash_year);
	                ps.setInt(5,txtCash_Month_hid);                               
	                ps.setInt(6,txtVoucher_No);            
	                
	                ps.executeUpdate();
	                txtVoucher_No=0;
	                txtVoucher_Date=null;
	             }             
	             con.commit();
	             sendMessage(response,"The TPA Journals Verified Successfully ","ok");
	           } 
	            catch(Exception e) 
	            {
	                try{con.rollback();}catch(SQLException sqle){System.out.println("Excep"+sqle);}
	                sendMessage(response,"The Liability Journals Verification Failed ","ok");
	                System.out.println("Exception occur due to "+e);
	            }
	            finally
	            {                
	                try{con.setAutoCommit(true);  }catch(SQLException sqle){System.out.println("Excep"+sqle);}
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
        System.out.println("Excep"+e);
        }
    }

}
