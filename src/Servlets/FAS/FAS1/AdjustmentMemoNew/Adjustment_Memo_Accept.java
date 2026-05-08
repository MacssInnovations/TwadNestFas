package Servlets.FAS.FAS1.AdjustmentMemoNew;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.http.HttpSession;

import Servlets.FAS.FAS1.CommonClass.ConvertDate;

/**
 * Servlet implementation class Adjustment_Memo_Accept
 */
public class Adjustment_Memo_Accept extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	PreparedStatement ps1 = null;
	ResultSet rs1,result = null;
	PreparedStatement ps2 = null;
	ResultSet rs2 = null;
	
    public Adjustment_Memo_Accept() {
        super();
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		PrintWriter out = response.getWriter();
	    HttpSession session=request.getSession(true);
	    String CONTENT_TYPE = "text/xml";
	    response.setContentType(CONTENT_TYPE);
		  
	    String cmd=request.getParameter("command");
	   
	    ConvertDate cc=new ConvertDate();
        String xml="";
        int count=0;
        String cmbAcc_UnitCode=request.getParameter("cmbAcc_UnitCode");
		int accno=cc.ConvertInt(cmbAcc_UnitCode);
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
			con = DriverManager.getConnection(ConnectionString,
					strdbusername.trim(), strdbpassword.trim());
			
		} catch (Exception e) {
			System.out.println("Exception in openeing connection:" + e);
		}	
		
		if(cmd.equalsIgnoreCase("Add")) 
        {
			    System.out.println("Add function starts");
			    //String CONTENT_TYPE = "text/html; charset=windows-1252";
	            response.setContentType(CONTENT_TYPE);
	           
	            int cmbAcc_UnitCode1=0,cmbOffice_code=0,txtCash_Month_hid=0,txtCash_year=0;            
	            String cr_dr_indicator="",flag="",particulars="";
	            String adjustmentDate=null;
	            int sub_ledger_code=0,depriciation_rate=0;
	            int supNo=0;
	            String remaks=""; 
	            int sub_ledger_type=0;
	            double amount=0;	       
	            Calendar c;
	            Date txtCrea_date=null;
	                                    // changes here
	            String update_user=(String)session.getAttribute("UserId");
	            long l=System.currentTimeMillis();
	            Timestamp ts=new Timestamp(l);
	            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	                               
	            try{cmbAcc_UnitCode1=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
	            catch(NumberFormatException e){System.out.println("exception"+e );}
	            System.out.println("cmbAcc_UnitCode........ "+cmbAcc_UnitCode);
	            
	            try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
	            catch(NumberFormatException e){System.out.println("exception"+e );}
	            System.out.println("cmbOffice_code...... "+cmbOffice_code);
	             String date=request.getParameter("txtDate");         
	             
	             
	             String[] sd=request.getParameter("txtDate").split("/");
	             c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
	             java.util.Date d=c.getTime();
	             txtCrea_date=new Date(d.getTime());
	             System.out.println("txtCrea_date "+txtCrea_date);
	             
	             
	             
	           // String[] sd=request.getParameter("txtDate").split("/");
	         
	             
	            //txtCrea_date=request.getParameter("txtDate");
	            System.out.println("txtCrea_date "+txtCrea_date);
	            
	            System.out.println("b4 getting month and year");
	            try{txtCash_year=Integer.parseInt(sd[2]);}
	            catch(Exception e){System.out.println("exception"+e );}
	            System.out.println("txtCash_year "+txtCash_year);
	            
	            try{txtCash_Month_hid=Integer.parseInt(sd[1]);}
	            catch(Exception e){System.out.println("exception"+e );}
	            System.out.println("txtCash_Month_hid "+txtCash_Month_hid);
	            
	            remaks=request.getParameter("txtRemarks1");
	            System.out.println("remaks "+remaks);
	                     
	            adjustmentDate=request.getParameter("adjustmentDate");
	            System.out.println("adjustmentDate "+adjustmentDate);  
	            int mon=Integer.parseInt(sd[1]);
	           // System.out.println("mon "+mon);
	            if(mon==3){
	            	//System.out.println("inside if mon");
	            	String supnno=request.getParameter("supNo");
	            	if((supnno=="")||(supnno.equalsIgnoreCase(""))){
	            		//System.out.println("if supno  null");
	            		supNo=0;
	            		
	            	}else{
	            		//System.out.println("supno else ");
	            	supNo=Integer.parseInt(supnno);
	            	}
	            }
              //changed on 09/08/2016 sss onsubmit voucher no without split up is taken
	            String[] cmbAdviceNO=request.getParameter("cmbAdviceNO").split("-");
                
               int adjustmentNo=Integer.parseInt(cmbAdviceNO[0]);  
               int adjustmentSerialNo=Integer.parseInt(cmbAdviceNO[1]);
               //Changed on 27Feb2017 onsubmit voucher no split up 
               
	            //String cmbAdviceNO  = request.getParameter("cmbAdviceNO");
	           // int adjustmentNo=Integer.parseInt(cmbAdviceNO);
	            //int adjustmentSerialNo = 1;
             
	            
	            
               String[] H_code=request.getParameterValues("H_code");
  	           System.out.println("H_code"+H_code[0]);	
               int accCode=0;
	           int trnRecords=H_code.length;
	           System.out.println("trnRecords"+trnRecords);
	           int org_VouNo=0;
	           
                   
	        	  
	           try
	           {
	                    ps=con.prepareStatement("select VOUCHER_NO from FAS_JOURNAL_MASTER GROUP BY VOUCHER_NO HAVING VOUCHER_NO =(select max(VOUCHER_NO) from FAS_JOURNAL_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=?)");
	                    ps.setInt(1,cmbAcc_UnitCode1);
	                    ps.setInt(2,cmbOffice_code);
	                    ps.setInt(3,txtCash_year);
	                    ps.setInt(4,txtCash_Month_hid);                      
	                        rs=ps.executeQuery();
	                    if(rs.next()) 
	                    {
	                    		org_VouNo = rs.getInt(1); 
	                              System.out.println("org_VouNo"+org_VouNo);
	                    }
	                    org_VouNo=org_VouNo+1;
	                    rs.close();
	           }           
	           catch(Exception e){System.out.println("exception"+e );}
	           System.out.println("org_VouNo "+org_VouNo);            
	             
	             try {
	                 ps.close();
	                 con.setAutoCommit(false);                 
	                 
//on 03-12-2019	                 ps1=con.prepareStatement("insert into FAS_JOURNAL_MASTER(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,VOUCHER_DATE,CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,TOTAL_TRN_RECORDS,REMARKS,CREATED_BY_MODULE,JOURNAL_STATUS,UPDATED_BY_USER_ID,UPDATED_DATE,DEPRECIATION_RATE,JOURNAL_TYPE_CODE,SUB_LEDGER_CODE,MODE_OF_CREATION,SUPPLEMENT_NO)values(?,?,to_date(?,'dd-mm-yyyy'),?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
	                 ps1=con.prepareStatement("insert into FAS_JOURNAL_MASTER(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,VOUCHER_DATE,CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,TOTAL_TRN_RECORDS,REMARKS,CREATED_BY_MODULE,JOURNAL_STATUS,UPDATED_BY_USER_ID,UPDATED_DATE,DEPRECIATION_RATE,JOURNAL_TYPE_CODE,SUB_LEDGER_CODE,MODE_OF_CREATION,SUPPLEMENT_NO)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

	                 ps1.setInt(1,cmbAcc_UnitCode1);
	                 ps1.setInt(2,cmbOffice_code);
	                 ps1.setDate(3,txtCrea_date);
	                 ps1.setInt(4,txtCash_year);
	                 ps1.setInt(5,txtCash_Month_hid);  
	                 ps1.setInt(6,org_VouNo);
	                 ps1.setInt(7,trnRecords);
	                 ps1.setString(8,remaks);
	                 
	                /* if(mon==3){
	                 ps1.setString(9,"SJV");
	                 }else{
	                 ps1.setString(9,"GJV");
	                 }*/
	                 ps1.setString(9,"GJV");
	                 ps1.setString(10,"L");
	                 ps1.setString(11,update_user);
	                 ps1.setTimestamp(12,ts);
	                 ps1.setInt(13,depriciation_rate);
	                 ps1.setInt(14,70);
	                 ps1.setInt(15,0);
	                 ps1.setString(16,"A");
	                 ps1.setInt(17,supNo);
	                 
	                 int kk=ps1.executeUpdate();
	                 if(kk==0)
	                     {
	                         System.out.println("redirect");                                
	                         sendMessage(response,"Master Creation Failed ","ok"); 
	                     }
                     else
                         
                     {
                    	 System.out.println("inside 2 nd table"+H_code.length);	                       
                       
                    	
                         try
                         {
                        	  String cr_dr_indicators[]=request.getParameterValues("CR_DR_type");
              	              String Grid_SL_type[]=request.getParameterValues("SL_type");
              	              
                              String Grid_SL_code[]=request.getParameterValues("SL_code");
                              String Grid_sl_amt[]=request.getParameterValues("sl_amt");
                              String Grid_particular[]=request.getParameterValues("particular");
                              count=1; 
                        	 for(int k=0;k<H_code.length;k++)
               	           {
                        		
                        		   accCode=Integer.parseInt(H_code[k]);
                        		  cr_dr_indicator=cr_dr_indicators[k];
                 	        	  
                 	        	 //  System.out.println("Grid_SL_type[k]::::"+Grid_SL_type[k]);
                 	        	   if(Grid_SL_type[k].equals(""))
                 	        	   {
                 	        		   System.out.println("empty::::");
                 	        		  sub_ledger_type=0;
                 	        		 sub_ledger_code=0;
                 	        	   }
                 	        	   else{
                 	        	   try{sub_ledger_type=Integer.parseInt(Grid_SL_type[k]);}
                 	        	   catch(Exception e){System.out.println("exception in trans "+e);}
                 	        	  System.out.println("sub_ledger_type.............."+sub_ledger_type); 
                                    try{sub_ledger_code=Integer.parseInt(Grid_SL_code[k]);}
                                    catch(Exception e){System.out.println("exception in trans "+e);}
                                    System.out.println("sub_ledger_code.............."+sub_ledger_code); 
                 	        	   }
                                    amount=Double.parseDouble(Grid_sl_amt[k]);
                                    particulars=Grid_particular[k];
                                 
                             ps=con.prepareStatement("insert into FAS_JOURNAL_TRANSACTION(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,SL_NO,ACCOUNT_HEAD_CODE,CR_DR_INDICATOR,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,AMOUNT,PARTICULARS,UPDATED_BY_USER_ID,UPDATED_DATE,CB_REF_NO,CB_REF_DATE,ADJ_DOC_NO,ADJ_DOC_TYPE)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,to_date(?,'dd-mm-yyyy'),?,?)");
                            
                             ps.setInt(1,cmbAcc_UnitCode1);
                             ps.setInt(2,cmbOffice_code);
                             ps.setInt(3,txtCash_year);
                             ps.setInt(4,txtCash_Month_hid);
                             ps.setInt(5,org_VouNo);
                             ps.setInt(6,count);
                             ps.setInt(7,accCode);
                             ps.setString(8,cr_dr_indicator);
                             ps.setInt(9,sub_ledger_type);
                             ps.setInt(10,sub_ledger_code);
                             ps.setDouble(11,amount);
                             ps.setString(12,particulars);
                             ps.setString(13,update_user);
                             ps.setTimestamp(14,ts);
                             ps.setInt(15,adjustmentNo);
                             ps.setString(16,adjustmentDate);
                             ps.setInt(17,adjustmentSerialNo);
                             ps.setInt(18,70);
                             int kkkk=ps.executeUpdate();
                             if(kkkk>0)
                                count++;
               	           }
                        	  if(count==trnRecords+1)  //total tr records=4 so if count matches trnrecords then flag is success
                                  flag="success";
                              else
                                  flag="failure";
                         
                         }
                         catch(Exception e)
                         {
                      	   e.getStackTrace();
                      	   System.out.println("Err in value setting for insertion:::"+e.getMessage());
                      	  // con.rollback();
                         }                                  
                    
                       }
	                 try {

		                  if(flag.equals("success")) 
		                  {
	                        System.out.println("inside update");  
		                   // String txtDate[]=request.getParameter("txtDate").split("/");
		           			int month=cc.ConvertInt(request.getParameter("txtCB_Month"));
		           			int year=cc.ConvertInt(request.getParameter("txtCB_Year"));
		                    int counttemp=0;
		                    String[] memoadv=request.getParameter("cmbAdviceNO").split("-");
		                    //String memoadv=request.getParameter("cmbAdviceNO");
		                     int adv=Integer.parseInt(memoadv[0]);  
		                     int serialNo=Integer.parseInt(memoadv[1]);  
		                    //int adv=Integer.parseInt(memoadv);  
		                   // int serialNo=1;  
		                      System.out.println("Voucherno "+adv);                                                                                                                       
		                      System.out.println("serialNo "+serialNo);
		      				
		      				ps=con.prepareStatement("select ADVICE_TYPE from FAS_ADJUST_MEMO_MST where   CASHBOOK_YEAR='"+year+"' and CASHBOOK_MONTH='"+month+"' and VOUCHER_NO='"+adv+"' and ACCEPT_VOUCHER_NO is null");
		      				ResultSet rs8=ps.executeQuery();
		      				
		      				if(rs8.next())
		      				{
		      					System.out.println("rs>>>>>>>>>"+rs8.getInt("ADVICE_TYPE"));
		      					counttemp=rs8.getInt("ADVICE_TYPE");
		      					
		      			   	}
		      				if(counttemp==1)
		      					{
		      					     ps=con.prepareStatement("update FAS_ADJUST_MEMO_TRN set ACCEPTANCE_STATUS=? ,ACCEPT_VOUCHER_NO=?,ACCEPT_VOUCHER_DATE=to_date(?,'dd-mm-yy') where FOR_ACCOUNTING_UNIT_ID="+accno+" and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=? and SL_NO=? ");
		      					      ps.setString(1,"Y");
		      	                      ps.setInt(2,org_VouNo);System.out.println("vou'''''''''''''''''"+org_VouNo);
		      	                      ps.setString(3,date);
		      	                     System.out.println("year"+year+":::month:::"+month);
		      	                      ps.setInt(4,year);
		      	                      ps.setInt(5,month);
		      	                      ps.setInt(6,adv);
		      	                    ps.setInt(7,serialNo);
		      	                  int s=  ps.executeUpdate();
		      	                  System.out.println("up count:::"+s);
		      	                    sendMessage(response,"The  Journal Number "+org_VouNo+" has been Created Successfully ","ok");   
		                            con.commit();
		      				   	}
		      				
		      				else
		      				{
		      					
	      	                      ps=con.prepareStatement("update FAS_ADJUST_MEMO_TRN set ACCEPTANCE_STATUS=? ,ACCEPT_VOUCHER_NO=?,ACCEPT_VOUCHER_DATE=to_date(?,'dd-mm-yy') where FOR_ACCOUNTING_UNIT_ID="+accno+" and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=? and SL_NO=?");
	      					      ps.setString(1,"Y");
	      	                      ps.setInt(2,org_VouNo);System.out.println("vou'''''''''''''''''"+org_VouNo);
	      	                      ps.setString(3,date);
	      	                     
	      	                      ps.setInt(4,year);
	      	                      ps.setInt(5,month);
	      	                      ps.setInt(6,adv);
	      	                    ps.setInt(7,serialNo);
	      	                      ps.executeUpdate();
	      	                      sendMessage(response,"The  Journal Number "+org_VouNo+" has been Created Successfully ","ok");   
	                               con.commit();
		      					
		      				}
		      				
		      				
		      				
		      				}	      			
		            } 
		            catch(Exception e) 
	                  {
	                      System.out.println("Err in insertion ::: "+e.getMessage());    
	                  }
	                
	                
	             }
	            catch(Exception e) {
	            System.out.println("exception"+e.getMessage());
	                 flag="failure";
	                 
	             }
	           
                 finally
                 {
                             
                              System.out.println("done");
                              try{
                            	  //sendMessage(response,"The  Journal Number  has been Created Successfully ","ok");
                            	  con.setAutoCommit(true); 
                              
                              
                              }catch(SQLException sqle){}
                 }
	       } 		
		
}
	private void sendMessage(HttpServletResponse response,String msg,String bType)
    {
        try
        {
                  System.out.println("sendMessage");
                  String url="org/Library/jsps/MessengerOkBack.jsp?message=" + msg + "&button=" + bType;
                  response.sendRedirect(url);
        }
        catch(IOException e)
        {
        }
    }       
}

