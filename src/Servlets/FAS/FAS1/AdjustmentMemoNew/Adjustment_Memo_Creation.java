package Servlets.FAS.FAS1.AdjustmentMemoNew;

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
import java.sql.Types;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class Adjustment_Memo_Creation extends HttpServlet
{
    private String CONTENT_TYPE = "text/xml; charset=windows-1252";
  
    public void init(ServletConfig config) throws ServletException 
    {
          super.init(config);
      
    }
    public void doGet(HttpServletRequest request, 
            HttpServletResponse response) throws ServletException, IOException 
	{
    	  String strCommand="";
          Connection con=null;        
          PreparedStatement ps=null;
          String xml="";
        
		  String CONTENT_TYPE = "text/xml";
		  response.setContentType(CONTENT_TYPE);
          int sl_NO=0,txtAcc_HeadCode=0;
          ResultSet result=null,result1=null,result2=null;
          PrintWriter out = response.getWriter();
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
	             ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection	             Class.forName(strDriver.trim());
	             con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
          }
          catch(Exception e)
          {
	             System.out.println("Exception in opening connection :"+e);
	             //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

          }
                  
          //-----------------------------------------------------------------------------------------------        
         
          try {
         
	             strCommand=request.getParameter("Command");
	             System.out.println("assign..here command..."+strCommand);
            
          }
          catch(Exception e) 
          {
        	     System.out.println("Exception in assigning..."+e);
          }
         
          Calendar c;
          int cmbAcc_UnitCode=0,cmbOffice_code=0,txtCash_Month_hid=0,txtCash_year=0;
          int cashbookyear=0,cashbookmonth=0,txtReceipt_No=0;
          int count=0,office_id=0;
        
          Date txtCrea_date=null;
          
          try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);
            
            try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            System.out.println("cmbOffice_code "+cmbOffice_code);
            
            
            
            try{cashbookyear=Integer.parseInt(request.getParameter("txtCB_Year"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            System.out.println("cashbookyear "+cashbookyear);
            
            try{cashbookmonth=Integer.parseInt(request.getParameter("txtCB_Month"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            System.out.println("cashbookmonth "+cashbookmonth);
          
       
            
           
            
            
            
            
         //----------------------------------------------------------------------------------------------- 
          if(strCommand.equals("loadno"))
          {
        	  
        	  
        	  String[] sd1=request.getParameter("txtCrea_date").split("/");
              c=new GregorianCalendar(Integer.parseInt(sd1[2]),Integer.parseInt(sd1[1])-1,Integer.parseInt(sd1[0]));
              java.util.Date dd=c.getTime();
              txtCrea_date=new Date(dd.getTime());
              System.out.println("txtCrea_date "+txtCrea_date);
              
              System.out.println("b4 getting month and year");
              try{txtCash_year=Integer.parseInt(sd1[2]);}
              catch(Exception e){System.out.println("exception"+e );}
              System.out.println("txtCash_year "+txtCash_year);
              
              try{txtCash_Month_hid=Integer.parseInt(sd1[1]);}
              catch(Exception e){System.out.println("exception"+e );}
              System.out.println("txtCash_Month_hid "+txtCash_Month_hid);
              
        	  
              
              try{txtAcc_HeadCode=Integer.parseInt(request.getParameter("txtAcc_HeadCode"));}
              catch(Exception e){System.out.println("exception"+e );}
              
              try{office_id=Integer.parseInt(request.getParameter("office_id"));}
              catch(Exception e){System.out.println("exception"+e );}
              
              System.out.println("txtAcc_HeadCode "+txtAcc_HeadCode);
          	System.out.println("**************************************enter");
          	xml="<response><command>receiptNo</command>"; 
            try 
                    {

           	 ps = con.prepareStatement("select RECEIPT_NO from FAS_RECEIPT_TRANSACTION where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=?  and CASHBOOK_MONTH=? and ACCOUNT_HEAD_CODE=? and SUB_LEDGER_CODE=? and RECEIPT_NO not in (select RECEIPT_NO from FAS_ADJUST_MEMO_MST where RECEIPTNO_YEAR=? and RECEIPTNO_MONTH=? and RECEIPT_NO is not null AND MEMO_STATUS='L' union (select RECEIPT_NO from FAS_ADJUST_MEMO_TRN where  RECEIPTNO_YEAR=? and RECEIPTNO_MONTH=? and RECEIPT_NO is not null)) ");

            	
            	//            	 ps = con.prepareStatement("select RECEIPT_NO from FAS_RECEIPT_TRANSACTION where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=?  and CASHBOOK_MONTH=? and ACCOUNT_HEAD_CODE=? and SUB_LEDGER_CODE=? and RECEIPT_NO not in (select RECEIPT_NO from FAS_ADJUST_MEMO_MST where RECEIPTNO_YEAR=? and RECEIPTNO_MONTH=? and RECEIPT_NO is not null union (select RECEIPT_NO from FAS_ADJUST_MEMO_TRN where  RECEIPTNO_YEAR=? and RECEIPTNO_MONTH=? and RECEIPT_NO is not null)) ");
                 ps.setInt(1, cmbAcc_UnitCode);
                 ps.setInt(2, cmbOffice_code);
                 ps.setInt(3, cashbookyear);
                 ps.setInt(4, cashbookmonth);
                 ps.setInt(5, txtAcc_HeadCode);
                 ps.setInt(6, office_id);
                 
                 ps.setInt(7, cashbookyear);
                 ps.setInt(8, cashbookmonth);
                
                 ps.setInt(9, cashbookyear);
                 ps.setInt(10, cashbookmonth);
                 result = ps.executeQuery();                               
                            while(result.next()) 
                            {
                                xml=xml+"<receiptno>"+result.getInt("RECEIPT_NO")+"</receiptno>";
                                count++;
                            }
                            if(count>0)
                                xml=xml+"<flag>success</flag>";
                            else
                                xml=xml+"<flag>failure</flag>";
                    }
              catch(Exception e) 
                    {
                            System.out.println("Exception in receipt no ===> "+e);   
                            xml=xml+"<flag>failure</flag>";  
                    }
                xml=xml+"</response>";
            
          }
          else if(strCommand.equalsIgnoreCase("load_Ref_No")) 
          {
              xml="<response><command>load_Ref_Number</command>";
              try 
              {
              	
            	 txtReceipt_No=Integer.parseInt(request.getParameter("txtReceipt_No"));
            	 System.out.println("txtReceipt_No"+txtReceipt_No);
                 ps=con.prepareStatement("select RECEIPT_NO,to_char(RECEIPT_DATE,'dd/mm/yyyy')as RECEIPT_DATE,trim(to_char(TOTAL_AMOUNT,'99999999999999.99')) as TOTAL_AMOUNT from FAS_RECEIPT_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and RECEIPT_NO=?"); 
                 ps.setInt(1, cmbAcc_UnitCode);
                 ps.setInt(2, cmbOffice_code);
                 ps.setInt(3, cashbookyear);
                 ps.setInt(4, cashbookmonth);
                 ps.setInt(5, txtReceipt_No);
                 result=ps.executeQuery();
                 System.out.println("result"+result);
                 while(result.next()) 
                 {
                	
                      xml=xml+"<HoNo>"+result.getInt("RECEIPT_NO")+"</HoNo>";    
                      xml=xml+"<HoDate>"+result.getString("RECEIPT_DATE")+"</HoDate>";
                      xml=xml+"<totalAmt>"+result.getString("TOTAL_AMOUNT")+"</totalAmt>";
                      count++;
                 }
                 if(count>0) 
                 xml=xml+"<flag>success</flag>";
                 else
                 xml=xml+"<flag>failure</flag>";
              }
              catch(Exception e) 
              {
                System.out.println("Exception in loading ref no:::"+e.getMessage());  
                xml=xml+"<flag>failure</flag>";
              }
              xml=xml+"</response>";
           }
          System.out.println("xml in second"+xml);
          out.println(xml);
          out.close();	
        
	}
    public void doPost(HttpServletRequest request, 
                      HttpServletResponse response) throws ServletException, IOException 
    {
    
    	  response.setContentType(CONTENT_TYPE);
		  String CONTENT_TYPE = "text/html";
		  response.setHeader("Cache-Control", "no-cache");
		  PrintWriter out = response.getWriter(); 
    	
         String strCommand="";
         Connection con=null;        
         PreparedStatement ps=null;
         String xml="";
     
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
                              ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection                              Class.forName(strDriver.trim());
                              con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
         }
         catch(Exception e)
         {
            System.out.println("Exception in opening connection :"+e);
            //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

         }
        
        try {
        
            strCommand=request.getParameter("Command");
            System.out.println("assign..here mycommand..>>>>>>>>.."+strCommand);
           
        }
        
        catch(Exception e) 
        {
            System.out.println("Exception in assigning..."+e);
        }
       
    
//-----------------------------------------------------------------------------------------------        
       
          if(strCommand.equalsIgnoreCase("Add")) 
        {
	         
	            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	            xml="<response><command>Add</command>";
	            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	            Calendar c;
	            int txtAcc_HeadCode=0,cmbAcc_UnitCode=0,cmbOffice_code=0,txtCash_Month_hid=0,txtCash_year=0;
	            int office_id=0,count=0,letterNo=0;
	            double txtTotalAmt=0;
	            Date txtCrea_date=null;
	            String particulars="",letterDate="",autName="",autAddress="";
	      
	                                    // changes here
	            String update_user=(String)session.getAttribute("UserId");
	            long l=System.currentTimeMillis();
	            Timestamp ts=new Timestamp(l);	                
	            int errcode=0;
	            
	            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	                               
	            try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
	            catch(NumberFormatException e){System.out.println("exception"+e );}
	            System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);
	            
	            try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
	            catch(NumberFormatException e){System.out.println("exception"+e );}
	            System.out.println("cmbOffice_code "+cmbOffice_code);
	            
	            String[] sd=request.getParameter("txtCrea_date").split("/");
	            c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
	            java.util.Date d=c.getTime();
	            txtCrea_date=new Date(d.getTime());
	            System.out.println("txtCrea_date "+txtCrea_date);
	            
	            System.out.println("b4 getting month and year");
	            try{txtCash_year=Integer.parseInt(sd[2]);}
	            catch(Exception e){System.out.println("exception"+e );}
	            System.out.println("txtCash_year "+txtCash_year);
	            
	            try{txtCash_Month_hid=Integer.parseInt(sd[1]);}
	            catch(Exception e){System.out.println("exception"+e );}
	            System.out.println("txtCash_Month_hid "+txtCash_Month_hid);
	            int txtAdvice_No=0;
	            try
	            {
	            	Statement st=con.createStatement();
	            //	ResultSet rs=st.executeQuery("SELECT VOUCHER_NO FROM FAS_ADJUSTMENT_MASTER GROUP BY VOUCHER_NO HAVING VOUCHER_NO =(select max(VOUCHER_NO) from FAS_ADJUSTMENT_MASTER where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and CASHBOOK_YEAR="+txtCash_year+" and CASHBOOK_MONTH="+txtCash_Month_hid+")");
	            	ResultSet rs=st.executeQuery("SELECT VOUCHER_NO FROM FAS_ADJUST_MEMO_MST GROUP BY VOUCHER_NO HAVING VOUCHER_NO =(select max(VOUCHER_NO) from FAS_ADJUST_MEMO_MST where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and CASHBOOK_YEAR="+txtCash_year+" and CASHBOOK_MONTH="+txtCash_Month_hid+")");
	            	if(rs.next()) 
	                {
	                	txtAdvice_No = rs.getInt(1);                		                   
	                }
	                txtAdvice_No=txtAdvice_No+1;
	            }
	            catch(Exception e){System.out.println("exception"+e );}
	            System.out.println("txtAdvice_No "+txtAdvice_No);
	            
	            try{office_id=Integer.parseInt(request.getParameter("office_id"));}
	            catch(NumberFormatException e){System.out.println("exception"+e );}
	            System.out.println("office_id "+office_id);
	            
	            try{letterNo=Integer.parseInt(request.getParameter("letterNo"));}
	            catch(NumberFormatException e){System.out.println("exception"+e );}
	            System.out.println("letterNo "+letterNo);
	            
	            letterDate=request.getParameter("letterDate");
	            
	            particulars=request.getParameter("particulars");
	            autName=request.getParameter("authority");
		           autAddress=request.getParameter("authorityaddress");
		           
		         System.out.println("autName"+autName);
		         System.out.println("autAddress"+autAddress);
	            
	            
	            try{txtTotalAmt=Double.parseDouble(request.getParameter("txtTotalAmt"));}
	            catch(Exception e){System.out.println("exception"+e );}
	            System.out.println("txtAmount "+txtTotalAmt);
               
	            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~        
                        
	            try 
	            {   
	                     con.clearWarnings();
	                     con.setAutoCommit(false);
	                    ps=con.prepareStatement("insert into FAS_ADJUST_MEMO_MST(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,VOUCHER_DATE,FOR_ACCOUNTING_UNIT_ID,LETTER_NO,LETTER_DATE,AUTHORITY_NAME,PARTICULARS,TOTAL_AMOUNT,UPDATED_BY_USER_ID,UPDATED_DATE,ADVICE_TYPE,AUTHORITY_ADDRESS,MEMO_STATUS,VERIFIED_STATUS) values (?,?,?,?,?,?,?,?,to_date(?,'dd/mm/yyyy'),?,?,?,?,?,?,?,?,?)");
	                    System.out.println("ps"+ps);
	                     ps.setInt(1,cmbAcc_UnitCode);
	                     ps.setInt(2,cmbOffice_code);
	                     ps.setInt(3,txtCash_year);
	                     ps.setInt(4,txtCash_Month_hid);
	                     ps.setInt(5,txtAdvice_No);
	                     ps.setDate(6,txtCrea_date);
	                     ps.setInt(7,office_id);
	                     ps.setInt(8,letterNo);
	                     ps.setString(9,letterDate);
	                     ps.setString(10,autName);
	                     ps.setString(11,particulars);		                     
	                     ps.setDouble(12,txtTotalAmt);
	                     ps.setString(13,update_user);
	                     ps.setTimestamp(14,ts);
	                     ps.setInt(15,1);
	                     ps.setString(16,autAddress);
	                    
	                     ps.setString(17, "L");
	                     ps.setString(18, "N");
	                     
	                     System.out.println("last");
	                     errcode=ps.executeUpdate();
	                     System.out.println("errcode"+errcode);
	                     if(errcode==0)
	                     {         
		                       System.out.println("redirect");
		                       sendMessage(response,"The Adjustment Memo Creation Failed ","ok");		                       
	                     }
	                     else
	                     {  
	                         System.out.println("inside 2 nd table");	                       
	                    	 String Grid_H_code[]=request.getParameterValues("H_code");
	                         String Grid_CR_DR_type[]=request.getParameterValues("CR_DR_type");
	                         String Grid_SL_type[]=request.getParameterValues("SL_type");
	                         String Grid_SL_code[]=request.getParameterValues("SL_code");
	                         String Grid_cbyear[]=request.getParameterValues("cashbkyear");
	                         String Grid_cbmonth[]=request.getParameterValues("cashbkmonth");
	                         String receiptNumber[]=request.getParameterValues("receiptNo");
	                         String ref_no[]=request.getParameterValues("ref_no");
	                         String ref_date[]=request.getParameterValues("ref_date");
	                         String Grid_sl_amt[]=request.getParameterValues("sl_amt");
	                         String Grid_particular[]=request.getParameterValues("sl_particular");
	                         
	                         System.out.println("after grid");
	                        
	                         String txtParticular="",receipt_date="",ho_date="";
		                     Date sl_ref_date=null;
		                     int SL_NO=0,cmbSL_type=0,cmbSL_Code=0,ref_num=0,cashbookyear=0,cashbookmonth=0,receiptNo=0;
		                     double txtsub_Amount=0;
		                   
	                         for(int k=0;k<Grid_H_code.length;k++) 
	                         {	  
	                        	 
	                        	  try
	  		     	            {
	  		     	            	Statement st=con.createStatement();
	  		     	            //	ResultSet rs=st.executeQuery("SELECT VOUCHER_NO FROM FAS_ADJUSTMENT_MASTER GROUP BY VOUCHER_NO HAVING VOUCHER_NO =(select max(VOUCHER_NO) from FAS_ADJUSTMENT_MASTER where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and CASHBOOK_YEAR="+txtCash_year+" and CASHBOOK_MONTH="+txtCash_Month_hid+")");
	  		     	            	ResultSet rs=st.executeQuery("select max(SL_NO) from FAS_ADJUST_MEMO_TRN where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and CASHBOOK_YEAR="+txtCash_year+" and CASHBOOK_MONTH="+txtCash_Month_hid);
	  		     	            	if(rs.next()) 
	  		     	                {
	  		     	            		System.out.println("inside if");
	  		     	            		SL_NO = rs.getInt(1);                		                   
	  		     	                }
	  		     	            	SL_NO=SL_NO+1;
	  		     	            }
	  		     	            catch(Exception e){System.out.println("exception"+e );}
	  		     	            System.out.println("txtAdvice_No "+SL_NO);
	                        	 
	                        	   cmbSL_type=0;cmbSL_Code=0;ref_num=0;
	                        	   txtAcc_HeadCode=0;	
	                        	   txtsub_Amount=0;
	                        	 String sql="insert into FAS_ADJUST_MEMO_TRN(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,ACCOUNT_HEAD_CODE,CR_DR_TYPE,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,RECEIPT_NO,RECEIPT_DATE,HO_REF_NO,HO_REF_DATE,AMOUNT,REMARKS,UPDATED_BY_USER_ID,UPDATED_DATE,SL_NO,RECEIPTNO_YEAR,RECEIPTNO_MONTH,VERIFIED_STATUS,FOR_ACCOUNTING_UNIT_ID,LETTER_NO,LETTER_DATE) values(?,?,?,?,?,?,?,?,?,?,to_date(?,'dd/mm/yyyy'),?,to_date(?,'dd/mm/yyyy'),?,?,?,?,?,?,?,?,?,?,?)" ;
	                        	   ps=con.prepareStatement(sql);
		                           try
		                           {
		                        	   
				                           ps.setInt(1,cmbAcc_UnitCode);	
				                           System.out.println("cmbAcc_UnitCode:  "+cmbAcc_UnitCode);				                          
			                               
				                           ps.setInt(2,cmbOffice_code);	   
			                               System.out.println("cmbOffice_code:  "+cmbOffice_code);				                          
			                               
			                               cashbookyear=Integer.parseInt(Grid_cbyear[k]);
			                               ps.setInt(3,txtCash_year);
			                               System.out.println("cashbookyear:  "+cashbookyear);	
			                               
			                               cashbookmonth=Integer.parseInt(Grid_cbmonth[k]);
			                               ps.setInt(4,txtCash_Month_hid);
			                               System.out.println("cashbookmonth:  "+cashbookmonth);
			                               
			                               ps.setInt(5,txtAdvice_No);	
			                               System.out.println("txtAdvice_No:  "+txtAdvice_No);		
			                               
			                               txtAcc_HeadCode=Integer.parseInt(Grid_H_code[k]);
			                               ps.setInt(6,txtAcc_HeadCode);
			                               System.out.println("txtAcc_HeadCode:  "+txtAcc_HeadCode);		
			                               
			                               System.out.println("Grid_CR_DR_type["+k+"]: "+Grid_CR_DR_type[k]);
			                               String rad_sub_CR_DR=Grid_CR_DR_type[k];	                              
			                               ps.setString(7,rad_sub_CR_DR);
			                               
			                               System.out.println("Grid_SL_type["+k+"]: "+Grid_SL_type[k]);	                               
			                               try{cmbSL_type=Integer.parseInt(Grid_SL_type[k]);}
			               	               catch(NumberFormatException e){System.out.println("exception"+e );}
			                               ps.setInt(8,cmbSL_type);	
			                               
			                               System.out.println("Grid_SL_code["+k+"]: "+Grid_SL_code[k]);	                               
			                               try{cmbSL_Code=Integer.parseInt(Grid_SL_code[k]);}
			               	               catch(NumberFormatException e){System.out.println("exception"+e );}
			               	               ps.setInt(9,cmbSL_Code);
			                               
			               	               receiptNo=Integer.parseInt(receiptNumber[k]);
			                               ps.setInt(10,receiptNo);
			                               System.out.println("receiptNo:  "+receiptNo);
			                               
			                               receipt_date=ref_date[k];
			                               ps.setString(11,receipt_date);	    
			                               
			                               
			               	               System.out.println("ref_no["+k+"]: "+ref_no[k]);	                               
			                               try{ref_num=Integer.parseInt(ref_no[k]);}
			               	               catch(NumberFormatException e){System.out.println("exception"+e );}
			               	               ps.setInt(12,ref_num);
			               	               
			               	               ho_date=ref_date[k];
			                               ps.setString(13,ho_date);	
			                              
			                               
			                               System.out.println("Grid_sl_amt["+k+"]: "+Grid_sl_amt[k]);	                               
			                               try{txtsub_Amount=Double.parseDouble(Grid_sl_amt[k]);}
			               	               catch(NumberFormatException e){System.out.println("exception"+e );}
			               	               ps.setDouble(14,txtsub_Amount);
			                                                          	                              
			                               txtParticular=Grid_particular[k];
			                               ps.setString(15,txtParticular);	    
			                               
			                               ps.setString(16,update_user);
			                               System.out.println("update_user : "+update_user);
			                               ps.setTimestamp(17,ts);
			                               System.out.println("ts : "+ts);
			                               ps.setInt(18,SL_NO);
			                              
			                               System.out.println("yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy"+cashbookyear);
			                               System.out.println("yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy"+cashbookyear);
			                               ps.setInt(19,cashbookyear);
			                               ps.setInt(20, cashbookmonth);
			                               ps.setString(21, "N");
			                               ps.setInt(22,office_id);
			      	                     	ps.setInt(23,letterNo);
			      	                     	ps.setString(24,letterDate);
			                               int i=ps.executeUpdate(); 
			                               if(i>0)
			                            	   count++;
			                               }
		                           catch(Exception e)
		                           {
		                        	   e.getStackTrace();
		                        	   System.out.println("Err in value setting for insertion:::"+e.getMessage());
		                        	  // con.rollback();
		                           }
	                         }
	                         ps.close();
	                         if(count==Grid_H_code.length)
	                         {
                                 
                                 
	                             try
	                                     {
                                             System.out.println("updateeeeeeeeeeeeeeeeeeee");
                                                     ps=con.prepareStatement("update FAS_RECEIPT_TRANSACTION set CB_REF_NO=?,CB_REF_DATE=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and RECEIPT_NO=? ");
                                                     ps.setInt(1,txtAdvice_No);System.out.println("txtAdvice_No::::"+txtAdvice_No);
                                                     ps.setDate(2,txtCrea_date);System.out.println("txtCrea_date"+txtCrea_date);
                                                     ps.setInt(3, cmbAcc_UnitCode);System.out.println("cmbAcc_UnitCode"+cmbAcc_UnitCode);
                                                     ps.setInt(4, cmbOffice_code);System.out.println("cmbOffice_code>>>>>"+cmbOffice_code);
                                                     ps.setInt(5, cashbookyear);System.out.println("cashbookyear"+cashbookyear);
                                                     ps.setInt(6, cashbookmonth);System.out.println("cashbookmonth"+cashbookmonth);
                                                     ps.setInt(7,receiptNo);System.out.println("receiptNo:::"+receiptNo);
	                                           
                                                     int kk=ps.executeUpdate();
                                                     if(kk>0)
                                                     {
                                                                           System.out.println("b4 commit");
                                                                           con.commit();
                                                                           sendMessage(response,"The Memo Advice Number '"+txtAdvice_No+"' has been Created Successfully ","ok");
                                                     }
                                                     else
	                                             {
	                                                                     
	                                                                                   con.rollback();
	                                                                                   sendMessage(response,"The Memo Advice Number. Creation Failed ","ok");           
	                                             }
	                                                   
	                                     }
	                                     catch(Exception e)
	                                     {
	                                                             System.out.println("Err in updation :: "+e.getMessage());
	                                                             con.rollback();
	                                                         sendMessage(response,"The Memo Advice Number. Creation Failed ","ok");     
	                                       
	                                     } 
                                 
                                 
		                        
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
	                     sendMessage(response,"The Adjustment Memo Creation Failed ","ok");
	                     System.out.println("Exception occur due to "+e);
	                     
	                 }
	                 finally
	                 {
	                     System.out.println("done");
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
}
