package Servlets.FAS.FAS1.AdjustmentMemoNew;

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
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Servlets.Security.classes.UserProfile;

/**
 * Servlet implementation class Acceptance_Verification
 */
public class Acceptance_Verification extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Acceptance_Verification() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 Connection con=null;
	        PreparedStatement ps=null;
	        ResultSet rs=null;
	      
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
	                
	        }catch(Exception e)
	        {
	        System.out.println("Redirect Error :"+e);
	        }
	        
	      
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
	                              ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
	                              Class.forName(strDriver.trim());
	                              con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
	             }
	             catch(Exception e)
	                 {
	                    System.out.println("Exception in opening connection :"+e);
	                    //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

	                 }
	        
	        response.setContentType(CONTENT_TYPE);
	        response.setHeader("Cache-Control","no-cache");
	        PrintWriter out = response.getWriter();
	        String strCommand="";
	        try 
	        {
	            strCommand=request.getParameter("command");
	            System.out.println("assign..here command..."+strCommand);
	        }
	        
	        catch(Exception e) 
	        {
	            System.out.println("Exception in assigning..."+e);
	        }
	        
	        int cmbAcc_UnitCode=0,cmbOffice_code=0,cashbookYear=0,cashbookMonth=0;
	          
	        
	        if(strCommand.equalsIgnoreCase("loadvoucher")) 
	        {
	             String CONTENT_TYPE = "text/xml; charset=windows-1252";
	             response.setContentType(CONTENT_TYPE);
	           
	             String xml="";
	           
	               try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
	               catch(NumberFormatException e){System.out.println("exception"+e );}
	               System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);
	               
	               try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
	               catch(NumberFormatException e){System.out.println("exception"+e );}
	               System.out.println("cmbOffice_code "+cmbOffice_code);
	               
	               try{cashbookYear=Integer.parseInt(request.getParameter("cashyear"));}
	               catch(NumberFormatException e){System.out.println("exception"+e );}
	               System.out.println("cashbookYear "+cashbookYear);
	               
	               try{cashbookMonth=Integer.parseInt(request.getParameter("cashmonth"));}
	               catch(NumberFormatException e){System.out.println("exception"+e );}
	               System.out.println("cashbookMonth "+cashbookMonth);
	               
	               DecimalFormat df=new DecimalFormat("#0.00"); 
	               
	               xml="<response><command>loadvoucher</command>";

	              int cnt=0,cnt1=0;
	          
	          
	                
	                
	            try 
	            {                        
                    ps=con.prepareStatement("SELECT * "+
								"	FROM "+
									  " (SELECT T.VOUCHER_NO,t.SL_NO, "+
                    		"   TO_CHAR(M.VOUCHER_DATE,'dd/MM/yyyy') AS VOUCHER_DATE, "+
                    		" 	    T.FOR_ACCOUNTING_UNIT_ID, "+
                    		" 	    TO_CHAR(T.ACCEPT_VOUCHER_DATE,'dd/MM/yyyy') AS ACCEPT_VOUCHER_DATE, "+
                    		" 	    T.AMOUNT, "+
                    		" 	    t.REMARKS "+
                    		" 	  FROM FAS_ADJUST_MEMO_MST M,FAS_ADJUST_MEMO_TRN T "+
                    		" 	  WHERE M.ACCOUNTING_UNIT_ID=T.ACCOUNTING_UNIT_ID "+
                    		" 	  AND M.ACCOUNTING_FOR_OFFICE_ID=T.ACCOUNTING_FOR_OFFICE_ID "+
                    		" 	  and M.CASHBOOK_YEAR=T.CASHBOOK_YEAR "+
                    		" 	  AND M.CASHBOOK_MONTH=T.CASHBOOK_MONTH "+
                    		" 	  AND M.VOUCHER_NO=T.VOUCHER_NO "+
                    		" 	  AND M.MEMO_STATUS='L' "+
                    		" 	  AND T.ACCEPTANCE_STATUS     ='Y' "+
                    		" 	  AND T.ACCEPT_VERIFY_STATUS IS NULL "+
                    		"	  AND T.CASHBOOK_MONTH        =? "+
                    		" 		  AND t.CASHBOOK_YEAR         =? "+
                    		" 	  and t.FOR_ACCOUNTING_UNIT_ID= "+cmbAcc_UnitCode+
                    		" 	  ) ab "+
                    		" 	LEFT OUTER JOIN "+
                    		" 	  ( SELECT ACCOUNTING_UNIT_ID, ACCOUNTING_UNIT_NAME FROM FAS_MST_ACCT_UNITS "+
                    		" 	  )b "+
                    		" 	ON ab.FOR_ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID "+
                    		" 	ORDER BY voucher_no");
      
				          ps.setInt(1,cashbookMonth);
				          ps.setInt(2,cashbookYear);
	                    
	                    rs=ps.executeQuery();
	                   while(rs.next()) 
	                    {
	                        
	                        xml=xml+"<vno>"+rs.getInt("voucher_no")+"</vno>";
	                        xml=xml+"<slno>"+rs.getInt("SL_NO")+"</slno>";
	                       xml=xml+"<vdate>"+rs.getString("voucher_date")+"</vdate>";
	                        xml=xml+"<unitid>"+rs.getInt("for_accounting_unit_id")+"</unitid>";
	                        xml=xml+"<acceptdate>"+rs.getString("accept_voucher_date")+"</acceptdate>";
	                        xml=xml+"<amount>"+df.format(rs.getBigDecimal("amount"))+"</amount>";
	                        xml=xml+"<particular>"+rs.getString("REMARKS")+"</particular>";
	                        xml=xml+"<unitname>"+rs.getString("ACCOUNTING_UNIT_NAME")+"</unitname>";
	                        xml=xml+"<adviceno>2</adviceno>";
	                   
	                        cnt++;
	                    } 
	                   
             if(cnt==0)
	                        xml+="<flag>failure</flag>";
	                    else
	                    	xml=xml+"<flag>success</flag>";
	                rs.close();
	                ps.close();
	                   
	            }
	            catch(Exception e)
	            {
	            System.out.println("catch..HERE.in failure to retrieve."+e);
	                xml="<response><command>loadvoucher</command>"+"<flag>failure</flag>";
	            }
	            xml=xml+"</response>";
	            System.out.println(xml);
	            out.println(xml);
	        }
	        
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  response.setContentType(CONTENT_TYPE);
	        
	        
	        /* Variables Declaration */ 
	        Connection con = null;      
	        CallableStatement cs= null;
	        PreparedStatement ps=null,ps1=null;
	      
	        /* Session Checking */
	        HttpSession session = request.getSession(false);       
	              
	      
	        /* Database Connection */
	        try {
	            ResourceBundle rs1 = ResourceBundle.getBundle("Servlets.Security.servlets.Config");
	            String ConnectionString = "";
	            String strDriver = rs1.getString("Config.DATA_BASE_DRIVER");
	            String strdsn = rs1.getString("Config.DSN");
	            String strhostname = rs1.getString("Config.HOST_NAME");
	            String strportno = rs1.getString("Config.PORT_NUMBER");
	            String strsid = rs1.getString("Config.SID");
	            String strdbusername = rs1.getString("Config.USER_NAME");
	            String strdbpassword = rs1.getString("Config.PASSWORD");
	            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
	            Class.forName(strDriver.trim());
	            con = DriverManager.getConnection(ConnectionString, strdbusername.trim(),strdbpassword.trim());
	        } catch (Exception e) {
	            System.out.println("Exception in opening connection :" + e);
	            sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

	        }
	        
	        
	        /* Variables Declaration */ 
	        
	        
	        String HO_accunitid=null;
	        String HO_officeid=null;
	        int cashbookYear=0;
	        int cashbookMonth=0;
	        
	        Date txtCrea_date=null;
	       
	        
	      
	        
	        
	        String chckparameter_Voucher_no[]=null;
	        String vou_No[]=null;
	        String advice_No[] =null;
	       
	        String unit_Id[]=null; // ( Head Office Account Number plus its corresponding Bank and Branch ID //
	       
	        Calendar c;
	        
	        int err_code=0;
	        
	        
	        /* Get HO Accounting Unit ID */
	         HO_accunitid=request.getParameter("cmbAcc_UnitCode");
	         System.out.println(HO_accunitid);
	        
	      int kk=0;
	        /* Get HO Office ID */
	         HO_officeid=request.getParameter("cmbOffice_code");
	         System.out.println(HO_officeid);
	         
	        
	        /* Get Receipt Date */
	        String[] sd=request.getParameter("txtCrea_date").split("/");
	        String dateStr=request.getParameter("txtCrea_date");
	        c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
	        java.util.Date d=c.getTime();
	        txtCrea_date=new Date(d.getTime());
	        System.out.println("txtCrea_date "+txtCrea_date);
	        
	      
	       
	         /* Get User ID */ 
	         String update_user=(String)session.getAttribute("UserId");
	       
	        
	         
	         try{cashbookYear=Integer.parseInt(request.getParameter("txtCB_Year"));}
             catch(NumberFormatException e){System.out.println("exception"+e );}
             System.out.println("cashbookYear "+cashbookYear);
             
             try{cashbookMonth=Integer.parseInt(request.getParameter("txtCB_Month"));}
             catch(NumberFormatException e){System.out.println("exception"+e );}
             System.out.println("cashbookMonth "+cashbookMonth);
	        //----------------------------------------------------------------------------------------------//
	         /* Get voucher Number from Selected Check Box */         
	         try
	         {
	    	   chckparameter_Voucher_no = request.getParameterValues("chckparameter");  
	           for(int i=0;i<chckparameter_Voucher_no.length;i++)
	           {
	              System.out.println("chckparameter_Voucher_no -->"+chckparameter_Voucher_no[i]);
	           }
	         }
	         catch(Exception e)
	         {
	    	   System.out.println("Error 1 ---->"+e);
	         }
	         
	         /* Get Office Accounting Unit ID */          
	          try {
	              vou_No = request.getParameterValues("Vou_No");  
	          }
	          catch (Exception e) {
	              System.out.println("Error getting Accounting Unit ID "+e);
	          }
	         
	         /* Get Office Accounting for Office ID */         
	          try {
	              advice_No = request.getParameterValues("adviceno");  
	          }
	          catch (Exception e) {
	              System.out.println("Error getting Office ID "+e);
	          }
	         
	       
	         /* Get Cashbook Year */
	          try {
	              unit_Id = request.getParameterValues("unitid");  
	          }
	          catch (Exception e) {
	              System.out.println("Error getting CB Year "+e);
	          }
	         
	         /* Get Voucher Number */         
	        
	        
	        try
	         {
	         //  con.clearWarnings();
	          // con.setAutoCommit(false);
	           
	           
	         
	             
	              /** Initially set the status as false */
	     	      boolean flag =false;
	     	      int k=0;
	              Date Refdate=null;
	              int i=0;
	              /** Iterate selected Records and make status true if it matches */
	     	      for(k=0;k<chckparameter_Voucher_no.length;k++)
	     	      {
	     	       
	     	    	i= Integer.parseInt(chckparameter_Voucher_no[k]);
	              
	              /** Only true condition */
	              
	                   System.out.println("----------------------------Starts----------------------------------");              
	                   
	                   
	                   String[] vsplit=vou_No[i].split("-");
	                  
	             
	                if(Integer.parseInt(advice_No[i])==1){   
	             ps=con.prepareStatement("update FAS_ADJUST_MEMO_MST set ACCEPT_VERIFY_STATUS=? ,VERIFIED_BY=?,VERIFIED_DATE=? where CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=? and ADVICE_TYPE=1");
	             ps.setString(1,"Y");
	             ps.setString(2, update_user);
	             ps.setDate(3, txtCrea_date);
	             ps.setInt(4, cashbookYear);
	             ps.setInt(5, cashbookMonth);
	             ps.setInt(6, Integer.parseInt(vsplit[0]));
	           //  ps.setInt(7, Integer.parseInt(vsplit[1]));
	             ps.executeUpdate();
	             
	           
	                }
	                else if(Integer.parseInt(advice_No[i])==2){ 
	                	 
	   	             ps=con.prepareStatement("update FAS_ADJUST_MEMO_TRN set ACCEPT_VERIFY_STATUS='Y' ,VERIFIED_BY=?,VERIFIED_DATE=? where CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and FOR_ACCOUNTING_UNIT_ID=? and VOUCHER_NO=? and SL_NO=?");
	   	         
		             ps.setString(1, update_user);
		             ps.setDate(2, txtCrea_date);
		             ps.setInt(3, cashbookYear);
		             ps.setInt(4, cashbookMonth);
		             ps.setInt(5, Integer.parseInt(unit_Id[i]));
		             ps.setInt(6, Integer.parseInt(vsplit[0]));
		             ps.setInt(7, Integer.parseInt(vsplit[1]));
		            kk= ps.executeUpdate();
		             
		            
		             
		            
		                }
	                   
	                
	     	       } 	
	     	   
	             if(kk>0)
	     	     sendMessage(response,"Acceptance Verification Completed Successfully ","ok");
	             else
	            	  sendMessage(response,"Failed to Updated Acceptance Verification ","ok");
	         }
	         catch(Exception e) 
	         {
	        	 e.printStackTrace();
	             System.out.println("the exception in update is"+e.getMessage());    
	             try{con.rollback();}catch(SQLException sqle){System.out.println("Excep"+sqle);}           
	         }         
	         finally
	         {
	           try{con.setAutoCommit(true);}
	           catch(SQLException sqle)
	           {System.out.println("Excep"+sqle);}
	         }    
	}
	
	 private void sendMessage(HttpServletResponse response,String msg,String bType)
     {
         try
         {
             String url="org/Library/jsps/MessengerOkBack.jsp?message=" + msg + "&button=" + bType;
             response.sendRedirect(url);
         }
         catch(Exception e)
         {
                 System.out.println("error in messenger"+e);
         }
     }

}
