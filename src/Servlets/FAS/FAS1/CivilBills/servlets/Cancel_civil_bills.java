package Servlets.FAS.FAS1.CivilBills.servlets;

import java.io.IOException;
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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.media.sound.AutoConnectSequencer;

import Servlets.FAS.FAS1.Masters.servlets.AuctionAsset;

/**
 * Servlet implementation class Cancel_civil_bills
 */
public class Cancel_civil_bills extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Cancel_civil_bills() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException {


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
	         Connection con=null;
	         ResultSet rs=null,renew=null;
	         int  txtCB_Year=0, txtCB_Month=0,cmbAcc_UnitCode=0,cmbOffice_code=0;
	         PreparedStatement ps=null,psnew=null;
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
	                 ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection	                 Class.forName(strDriver.trim());
	                 con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
	         }
	         catch(Exception e)
	         {
	        	 	 System.out.println("Exception in openeing connection :"+e);

	         }
	         
	        System.out.println("servlet called");
	        String CONTENT_TYPE = "text/xml; charset=windows-1252";
	        response.setContentType(CONTENT_TYPE);
	        PrintWriter out = response.getWriter();
	        String strType = "",xml="<response>";
	        try
	        {
	        	     strType = request.getParameter("Command");
	        }
	        catch(Exception e)
	        {
	        		 e.printStackTrace();
	        }
	       
		 if(strType.equalsIgnoreCase("searchByMonth"))  
	        {
			 System.out.println();
			  xml="<response><command>searchByMonth</command>";
	 try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
     catch(NumberFormatException e){System.out.println("exception"+e );}
     
     
     try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
     catch(NumberFormatException e){System.out.println("exception"+e );}
     
     txtCB_Year=Integer.parseInt(request.getParameter("txtCB_Year"));
     txtCB_Month=Integer.parseInt(request.getParameter("txtCB_Month"));  
     int optionId=Integer.parseInt(request.getParameter("optionId"));
     System.out.println("optionId====>"+optionId);
     String sql="";
     String pasdate="";
     int passno=0,billamt=0,billno=0;
     String sub_q = "",sub_main="";
     if(optionId==1)
     {
    	
			/*if(txtCB_Year>2014 && txtCB_Month>3)
			{
				 sub_q = " FAS_BILL_REGISTER_MASTERNEW "; 
				 sub_main=" Fas_Bill_Register_MasterNEW M, "+
				" 	  Fas_Bill_Register_Transactionw T ";
			}else{
				sub_q = " FAS_BILL_REGISTER_MASTER "; 
				 sub_main=" Fas_Bill_Register_Master M, "+
							" 	  Fas_Bill_Register_Transaction T ";
			}*/
    		if (txtCB_Year > 2014) {
				if (txtCB_Year == 2015 && txtCB_Month <= 3) {
					sub_q = " FAS_BILL_REGISTER_MASTER "; 
					 sub_main=" Fas_Bill_Register_Master M, "+
								" 	  Fas_Bill_Register_Transaction T ";
				}else{
					 sub_q = " FAS_BILL_REGISTER_MASTERNEW "; 
					 sub_main=" Fas_Bill_Register_MasterNEW M, "+
					" 	  Fas_Bill_Register_Transactionw T ";	
				}
    		}else{
    			sub_q = " FAS_BILL_REGISTER_MASTER "; 
				 sub_main=" Fas_Bill_Register_Master M, "+
							" 	  Fas_Bill_Register_Transaction T ";
    		}
         sql="SELECT distinct m.BILL_NO," +
         		//"t.account_head_code,(select account_head_desc from com_mst_account_heads h where h.account_head_code=t.account_head_code)as headdesc, "+
			  " m.SANCTION_PROC_NO,to_char(m.BILL_DATE,'dd/mm/yyyy')as BILL_DATE,m.TOTAL_SANCTIONED_AMOUNT as sancamount, "+
        	// " t.amount,t.PAYEE_TYPE_CODE,t.PAYABLE_TO,(select s.employee_name from hrm_mst_employees s where s.employee_id=t.PAYABLE_TO)as codedesc,
			  " m.STATUS "+
        	 " FROM  "+sub_main+
        	 " WHERE m.ACCOUNTING_UNIT_ID     =t.ACCOUNTING_UNIT_ID "+
        	 " AND m.accounting_unit_office_id=t.accounting_unit_office_id "+
        	 " AND m.CASHBOOK_YEAR            =t.CASHBOOK_YEAR "+
        	 " AND m.CASHBOOK_MONTH           =t.CASHBOOK_MONTH "+
        	 " AND m.BILL_NO                  =t.BILL_NO "+
        	 " AND m.ACCOUNTING_UNIT_ID       = "+cmbAcc_UnitCode+
        	 " AND m.accounting_unit_office_id=  "+cmbOffice_code+
        	 " AND m.CASHBOOK_YEAR            =  "+txtCB_Year+
        	 " AND m.CASHBOOK_MONTH           =  "+txtCB_Month+
        	 " AND m.STATUS                   ='L' "+
        	 " AND m.MEMO_ENTRY              IS NULL  and m.BILL_TYPE <> 'WOSP' ";
         
         try
         {
		            int count=0;
		            ps=con.prepareStatement(sql);
		            rs=ps.executeQuery();
	                while(rs.next())
	                {
	                	 xml=xml+"<leng>";
	                	
		                    xml=xml+"<bill_no>"+rs.getInt("bill_no")+"</bill_no>";						                  
		                    xml=xml+"<BILL_DATE>"+rs.getString("BILL_DATE")+"</BILL_DATE>";
		                    xml=xml+"<sanction_proceeding_no>"+rs.getString("SANCTION_PROC_NO")+"</sanction_proceeding_no>";	                
		                    xml=xml+"<sanctioned_amount>"+rs.getString("sancamount") +"</sanctioned_amount>";
		                    xml=xml+"<STATUS>"+rs.getString("STATUS") +"</STATUS>";
	                	
	                	
		                    xml=xml+"</leng>";
		                    count++;
	                }
	                if(count>0) 
	                {
		                    xml=xml+"<flag>success</flag>";
		                  
	                }
	                else
	                {
	                	 xml=xml+"<flag>failure</flag>";
	                	// sendMessage(response,"No Data","ok"); 
	                }
         }
         catch(SQLException sqle)
         {
	        	    sqle.printStackTrace();
	        	    System.out.println("error while fetching data " + sqle);
	                xml="<response><command>searchByMonth</command><flag>failure</flag>";
         }
           
     }
     else if(optionId==2)
     {
    	 /*sql="SELECT distinct m.BILL_NO," +
  		  "  m.MANUAL_PROCEEDING_NO as SANCTION_PROC_NO,to_char(m.BILL_DATE,'dd/mm/yyyy')as BILL_DATE," +
  		  " m.TOTAL_SANCTIONED_AMOUNT as sancamount, "+
 		  " m.STATUS "+
 	 " FROM FAS_BILL_REGISTERNEW m "+
 	 " WHERE m.ACCOUNTING_UNIT_ID       = "+cmbAcc_UnitCode+
 	 " AND m.accounting_unit_office_id=  "+cmbOffice_code+
 	 " AND m.CASHBOOK_YEAR            =  "+txtCB_Year+
 	 " AND m.CASHBOOK_MONTH           =  "+txtCB_Month+
 	 " AND m.STATUS                   ='L' "+
 	 " AND m.MEMO_ENTRY              IS NULL";*/
    	 
    	 

		/*	if(txtCB_Year>2014 && txtCB_Month>3)
			{
				 sub_q = " FAS_BILL_REGISTER_MASTERNEW "; 
				 sub_main=" Fas_Bill_Register_MasterNEW M, "+
				" 	  Fas_Bill_Register_Transactionw T ";
			}else{
				sub_q = " FAS_BILL_REGISTER_MASTER "; 
				 sub_main=" Fas_Bill_Register_Master M, "+
							" 	  Fas_Bill_Register_Transaction T ";
			}*/
    	 if (txtCB_Year > 2014) {
				if (txtCB_Year == 2015 && txtCB_Month <= 3) {
					sub_q = " FAS_BILL_REGISTER_MASTER "; 
					 sub_main=" Fas_Bill_Register_Master M, "+
								" 	  Fas_Bill_Register_Transaction T ";
				}else{
					 sub_q = " FAS_BILL_REGISTER_MASTERNEW "; 
					 sub_main=" Fas_Bill_Register_MasterNEW M, "+
					" 	  Fas_Bill_Register_Transactionw T ";	
				}
 		}else{
 			sub_q = " FAS_BILL_REGISTER_MASTER "; 
			 sub_main=" Fas_Bill_Register_Master M, "+
						" 	  Fas_Bill_Register_Transaction T ";
 		}
    	 sql="SELECT distinct m.BILL_NO," +
          		//"t.account_head_code,(select account_head_desc from com_mst_account_heads h where h.account_head_code=t.account_head_code)as headdesc, "+
 			  " m.SANCTION_PROC_NO,to_char(m.BILL_DATE,'dd/mm/yyyy')as BILL_DATE,m.TOTAL_SANCTIONED_AMOUNT as sancamount, "+
         	// " t.amount,t.PAYEE_TYPE_CODE,t.PAYABLE_TO,(select s.employee_name from hrm_mst_employees s where s.employee_id=t.PAYABLE_TO)as codedesc,
 			  " m.STATUS "+
         	 " FROM  "+sub_main+
         	 " WHERE m.ACCOUNTING_UNIT_ID     =t.ACCOUNTING_UNIT_ID "+
         	 " AND m.accounting_unit_office_id=t.accounting_unit_office_id "+
         	 " AND m.CASHBOOK_YEAR            =t.CASHBOOK_YEAR "+
         	 " AND m.CASHBOOK_MONTH           =t.CASHBOOK_MONTH "+
         	 " AND m.BILL_NO                  =t.BILL_NO "+
         	 " AND m.ACCOUNTING_UNIT_ID       = "+cmbAcc_UnitCode+
         	 " AND m.accounting_unit_office_id=  "+cmbOffice_code+
         	 " AND m.CASHBOOK_YEAR            =  "+txtCB_Year+
         	 " AND m.CASHBOOK_MONTH           =  "+txtCB_Month+
         	 " AND m.STATUS                   ='L' "+
         	 " AND m.MEMO_ENTRY              IS NULL and m.BILL_TYPE = 'WOSP' ";
    	 try
         {
		            int count=0;
		            ps=con.prepareStatement(sql);
		            rs=ps.executeQuery();
	                while(rs.next())
	                {
	                	 xml=xml+"<leng>";
	                	
		                    xml=xml+"<bill_no>"+rs.getInt("bill_no")+"</bill_no>";						                  
		                    xml=xml+"<BILL_DATE>"+rs.getString("BILL_DATE")+"</BILL_DATE>";
		                    xml=xml+"<sanction_proceeding_no>"+rs.getString("SANCTION_PROC_NO")+"</sanction_proceeding_no>";	                
		                    xml=xml+"<sanctioned_amount>"+rs.getString("sancamount") +"</sanctioned_amount>";
		                    xml=xml+"<STATUS>"+rs.getString("STATUS") +"</STATUS>";
	                	
	                	
		                    xml=xml+"</leng>";
		                    count++;
	                }
	                if(count>0) 
	                {
		                    xml=xml+"<flag>success</flag>";
		                  
	                }
	                else
	                {
	                	 xml=xml+"<flag>failure</flag>";
	                	// sendMessage(response,"No Data","ok"); 
	                }   rs.close();
 		              ps.close(); 
         }
         catch(SQLException sqle)
         {
	        	    sqle.printStackTrace();
	        	    System.out.println("error while fetching data " + sqle);
	                xml="<response><command>searchByMonth</command><flag>failure</flag>";
         }
     }
     else if(optionId==3)
     {
    	 sql="SELECT m.BILL_NO, "+
				  " m.SANCTION_PROCEEDING_NO as SANCTION_PROC_NO, "+
    		 " TO_CHAR(m.BILL_DATE,'dd/mm/yyyy')AS BILL_DATE, "+
    		 "   m.SANCTIONED_AMOUNT as sancamount, "+
    		 "   m.STATUS "+
    		 " FROM FAS_MEMO_OF_PAYMENT_MST m "+
    		 " WHERE m.ACCOUNTING_UNIT_ID       =  "+cmbAcc_UnitCode+
    		 " AND m.ACCOUNTING_FOR_OFFICE_ID=  "+cmbOffice_code+
    		 " AND m.CASHBOOK_YEAR            =  "+txtCB_Year+
    		 " AND m.CASHBOOK_MONTH           =  "+txtCB_Month+
    		 " AND m.STATUS                   ='L' "+
    		 " AND (m.BILL_SCRUTINY              IS NULL OR m.BILL_SCRUTINY='')";
    	 try
         {
		            int count=0;
		            ps=con.prepareStatement(sql);
		            rs=ps.executeQuery();
	                while(rs.next())
	                {
	                	 xml=xml+"<leng>";
	                	
		                    xml=xml+"<bill_no>"+rs.getInt("bill_no")+"</bill_no>";						                  
		                    xml=xml+"<BILL_DATE>"+rs.getString("BILL_DATE")+"</BILL_DATE>";
		                    xml=xml+"<sanction_proceeding_no>"+rs.getString("SANCTION_PROC_NO")+"</sanction_proceeding_no>";	                
		                    xml=xml+"<sanctioned_amount>"+rs.getString("sancamount") +"</sanctioned_amount>";
		                    xml=xml+"<STATUS>"+rs.getString("STATUS") +"</STATUS>";
	                	
	                	
		                    xml=xml+"</leng>";
		                    count++;
	                }
	                if(count>0) 
	                {
		                    xml=xml+"<flag>success</flag>";
		                  
	                }
	                else
	                {
	                	 xml=xml+"<flag>failure</flag>";
	                //	 sendMessage(response,"No Data","ok"); 
	                }
	                rs.close();
 		              ps.close(); 
         }
         catch(SQLException sqle)
         {
	        	    sqle.printStackTrace();
	        	    System.out.println("error while fetching data " + sqle);
	                xml="<response><command>searchByMonth</command><flag>failure</flag>";
         }
     }
     else if(optionId==4)
     {
    		/*if(txtCB_Year>2014 && txtCB_Month>3)
			{
				 sub_q = " FAS_BILL_REGISTER_MASTERNEW "; 
				 sub_main=" Fas_Bill_Register_MasterNEW M, "+
				" 	  Fas_Bill_Register_Transactionw T ";
			}else{
				sub_q = " FAS_BILL_REGISTER_MASTER "; 
				 sub_main=" Fas_Bill_Register_Master M, "+
							" 	  Fas_Bill_Register_Transaction T ";
			}*/
    	 if (txtCB_Year > 2014) {
				if (txtCB_Year == 2015 && txtCB_Month <= 3) {
					sub_q = " FAS_BILL_REGISTER_MASTER "; 
					 sub_main=" Fas_Bill_Register_Master M, "+
								" 	  Fas_Bill_Register_Transaction T ";
				}else{
					sub_q = " FAS_BILL_REGISTER_MASTERNEW "; 
					 sub_main=" Fas_Bill_Register_MasterNEW M, "+
					" 	  Fas_Bill_Register_Transactionw T ";	
				}
 		}else{
 			sub_q = " FAS_BILL_REGISTER_MASTER "; 
			 sub_main=" Fas_Bill_Register_Master M, "+
						" 	  Fas_Bill_Register_Transaction T ";
 		}
    	 
    	 sql=" SELECT distinct m.BILL_NO,TO_CHAR(M.MEMO_UPDATED_DATE,'dd/mm/yyyy')AS memo_date, "+
			 " m.SANCTION_PROC_NO         AS SANCTION_PROC_NO,"+
    	 "  TO_CHAR(m.BILL_DATE,'dd/mm/yyyy')AS BILL_DATE, "+
    	 "   m.TOTAL_SANCTIONED_AMOUNT              AS sancamount, "+
    	 "   m.STATUS "+
    	 " FROM  "+sub_main+
    	 " WHERE m.ACCOUNTING_UNIT_ID     =t.ACCOUNTING_UNIT_ID "+
    	 " AND m.ACCOUNTING_UNIT_OFFICE_ID=t.ACCOUNTING_UNIT_OFFICE_ID "+
    	 " AND m.CASHBOOK_YEAR            =t.CASHBOOK_YEAR "+
    	 " AND m.CASHBOOK_MONTH           =t.CASHBOOK_MONTH "+
    	 " AND m.BILL_NO                  =t.BILL_NO "+
    	 " AND m.PASS_ORDER_DATE         IS NULL "+
    	 " AND m.PASS_ORDER_BY           IS NULL "+
    	 " AND m.BILL_SCRUTINY_DONE      IS NOT NULL "+
    	 " AND m.BILL_SCRUTINY_BY        IS NOT NULL "+
    	 " AND m.status                   ='L' " +
    	 " and m.accounting_unit_id="+cmbAcc_UnitCode+" and m.CASHBOOK_YEAR=" +txtCB_Year+
    	 " and m.CASHBOOK_MONTH="+txtCB_Month+" union all "+
    	 " SELECT m.BILL_NO, TO_CHAR(M.MEMO_UPDATED_DATE,'dd/mm/yyyy')AS memo_date,"+
		 " m.SANCTION_PROC_NO         AS SANCTION_PROC_NO,"+
	 "  TO_CHAR(m.BILL_DATE,'dd/mm/yyyy')AS BILL_DATE, "+
	 "   m.TOTAL_SANCTIONED_AMOUNT              AS sancamount, "+
	 "   m.STATUS "+
	 " FROM fas_bill_registernew m "+
	 " WHERE m.PASS_ORDER_DATE         IS NULL "+
	 " AND m.PASS_ORDER_BY           IS NULL "+
	 " AND m.BILL_SCRUTINY_DONE      IS NOT NULL "+
	 " AND m.BILL_SCRUTINY_BY        IS NOT NULL "+
	 " AND m.status                   ='L' " +
	 " and m.accounting_unit_id="+cmbAcc_UnitCode+" and m.CASHBOOK_YEAR=" +txtCB_Year+
	 " and m.CASHBOOK_MONTH="+txtCB_Month;
    	 try
         {
    		 System.out.println(sql);
		            int count=0;
		            ps=con.prepareStatement(sql);
		            rs=ps.executeQuery();
	                while(rs.next())
	                {
	                	 xml=xml+"<leng>";
	                	
		                    xml=xml+"<bill_no>"+rs.getInt("bill_no")+"</bill_no>";						                  
		                    xml=xml+"<BILL_DATE>"+rs.getString("BILL_DATE")+"</BILL_DATE>";
		                    xml=xml+"<sanction_proceeding_no>"+rs.getString("SANCTION_PROC_NO")+"</sanction_proceeding_no>";	                
		                    xml=xml+"<sanctioned_amount>"+rs.getString("sancamount") +"</sanctioned_amount>";
		                    xml=xml+"<STATUS>"+rs.getString("STATUS") +"</STATUS>";
		                    xml=xml+"<memo_date>"+rs.getString("memo_date")+"</memo_date>";
		                    xml=xml+"</leng>";
		                    count++;
	                }
	                if(count>0) 
	                {
		                    xml=xml+"<flag>success</flag>";
		                  
	                }
	                else
	                {
	                	 xml=xml+"<flag>failure</flag>";
	                //	 sendMessage(response,"No Data","ok"); 
	                }   rs.close();
 		              ps.close(); 
         }
         catch(SQLException sqle)
         {
	        	    sqle.printStackTrace();
	        	    System.out.println("error while fetching data " + sqle);
	                xml="<response><command>searchByMonth</command><flag>failure</flag>";
         }
     }
     else if(optionId==5)
     {
    	 
    	/* sql="SELECT m.PASS_ORDER_NO, "+
		 " t.SANCTION_PROC_NO         AS SANCTION_PROC_NO, "+
		 "  t.BILL_NO, "+
		 " TO_CHAR(m.PASS_ORDER_DATE,'dd/mm/yyyy')AS PASS_ORDER_DATE, "+
		 "  t.BILL_AMOUNT              AS billamt, "+
		 "  m.STATUS "+
		 " FROM FAS_PASS_ORDER_MST m,FAS_PASS_ORDER_TRN t "+
		 " WHERE m.ACCOUNTING_UNIT_ID=t.ACCOUNTING_UNIT_ID "+
		 " and m.ACCOUNTING_FOR_OFFICE_ID=t.ACCOUNTING_FOR_OFFICE_ID "+
		 " and m.CASHBOOK_YEAR=t.CASHBOOK_YEAR "+
		 " and m.CASHBOOK_MONTH=t.CASHBOOK_MONTH  "+
		 " and m.PASS_ORDER_NO=t.PASS_ORDER_NO "+
		 " and m.ACCOUNTING_UNIT_ID    =  "+cmbAcc_UnitCode+
		 " AND m.ACCOUNTING_FOR_OFFICE_ID=  "+cmbOffice_code+
		 " AND m.CASHBOOK_YEAR           =  "+txtCB_Year+
		 " AND m.CASHBOOK_MONTH          =  "+txtCB_Month+
		 " AND m.STATUS                  ='L' "+
		 " AND m.APPROVED_BY            IS NULL";*/
    	 sql="SELECT distinct m.PASS_ORDER_NO, "+
    			// " t.SANCTION_PROC_NO         AS SANCTION_PROC_NO, "+
    			// "  t.BILL_NO, "+
    			 " TO_CHAR(m.PASS_ORDER_DATE,'dd/mm/yyyy')AS PASS_ORDER_DATE, "+
    		//	 "  t.BILL_AMOUNT              AS billamt, "+
    			 "  m.STATUS "+
    			 " FROM FAS_PASS_ORDER_MST m,FAS_PASS_ORDER_TRN t "+
    			 " WHERE m.ACCOUNTING_UNIT_ID=t.ACCOUNTING_UNIT_ID "+
    			 " and m.ACCOUNTING_FOR_OFFICE_ID=t.ACCOUNTING_FOR_OFFICE_ID "+
    			 " and m.CASHBOOK_YEAR=t.CASHBOOK_YEAR "+
    			 " and m.CASHBOOK_MONTH=t.CASHBOOK_MONTH  "+
    			 " and m.PASS_ORDER_NO=t.PASS_ORDER_NO "+
    			 " and m.ACCOUNTING_UNIT_ID    =  "+cmbAcc_UnitCode+
    			 " AND m.ACCOUNTING_FOR_OFFICE_ID=  "+cmbOffice_code+
    			 " AND m.CASHBOOK_YEAR           =  "+txtCB_Year+
    			 " AND m.CASHBOOK_MONTH          =  "+txtCB_Month+
    			 " AND m.STATUS                  ='L' "+
    			 " AND m.APPROVED_BY            IS NULL";
    	 try
         {
		            int count=0;
		            ps=con.prepareStatement(sql);
		            rs=ps.executeQuery();
	                while(rs.next())
	                {
	                	 xml=xml+"<leng>";
	                	
	                
	                		xml=xml+"<passno>"+rs.getInt("PASS_ORDER_NO")+"</passno>";						                  
	                		xml=xml+"<billno>"+0+"</billno>";
	                		xml=xml+"<passdate>"+rs.getString("PASS_ORDER_DATE")+"</passdate>";
		                    xml=xml+"<billamt>"+0+"</billamt>";
		                    xml=xml+"<STATUS>"+rs.getString("STATUS") +"</STATUS>";
	                	
	                	
		                    xml=xml+"</leng>";
		                    count++;
	                }
	                if(count>0) 
	                {
		                    xml=xml+"<flag>success</flag>";
		                  
	                }
	                else
	                {
	                	 xml=xml+"<flag>failure</flag>";
	                	// sendMessage(response,"No Data","ok"); 
	                }   rs.close();
 		              ps.close(); 
         }
         catch(SQLException sqle)
         {
	        	    sqle.printStackTrace();
	        	    System.out.println("error while fetching data " + sqle);
	                xml="<response><command>searchByMonth</command><flag>failure</flag>";
         }
     }
     else if(optionId==6)
     {
    	 try{
    	/*psnew=con.prepareStatement("SELECT m.PASS_ORDER_NO,t.bill_no,TO_CHAR(m.PASS_ORDER_DATE,'dd/mm/yyyy')" +
    			"AS PASS_ORDER_DATE,t.BILL_AMOUNT              AS billamt, "+
    			 "  m.STATUS "+
								" FROM FAS_PASS_ORDER_MST m,fas_pass_order_trn t "+
    			" WHERE m.accounting_unit_id=t.accounting_unit_id "+
    			" 			and m.accounting_for_office_id=t.accounting_for_office_id "+
    			" 			and m.cashbook_year=t.cashbook_year "+
    			" 			and m.cashbook_month=t.cashbook_month "+
    			" 			and m.pass_order_no=t.pass_order_no "+
    			" 			and m.ACCOUNTING_UNIT_ID = "+cmbAcc_UnitCode+
    			" 			AND m.CASHBOOK_YEAR= "+txtCB_Year+
    			" 			AND m.CASHBOOK_MONTH       = "+txtCB_Month+
    			" 			and m.status='L' and m.APPROVED_BY is not null");*/
    		 
    		 
    		String sql1="SELECT distinct m.PASS_ORDER_NO, 0 as bill_no,"+
    	    			// " t.SANCTION_PROC_NO         AS SANCTION_PROC_NO, "+
    	    			// "  t.BILL_NO, "+
    	    			 " TO_CHAR(m.PASS_ORDER_DATE,'dd/mm/yyyy')AS PASS_ORDER_DATE, "+
    	    		 "  0             AS billamt, "+
    	    			 "  m.STATUS "+
    	    			 " FROM FAS_PASS_ORDER_MST m,FAS_PASS_ORDER_TRN t "+
    	    			 " WHERE m.ACCOUNTING_UNIT_ID=t.ACCOUNTING_UNIT_ID "+
    	    			 " and m.ACCOUNTING_FOR_OFFICE_ID=t.ACCOUNTING_FOR_OFFICE_ID "+
    	    			 " and m.CASHBOOK_YEAR=t.CASHBOOK_YEAR "+
    	    			 " and m.CASHBOOK_MONTH=t.CASHBOOK_MONTH  "+
    	    			 " and m.PASS_ORDER_NO=t.PASS_ORDER_NO "+
    	    			 " and m.ACCOUNTING_UNIT_ID    =  "+cmbAcc_UnitCode+
    	    			 " AND m.ACCOUNTING_FOR_OFFICE_ID=  "+cmbOffice_code+
    	    			 " AND m.CASHBOOK_YEAR           =  "+txtCB_Year+
    	    			 " AND m.CASHBOOK_MONTH          =  "+txtCB_Month+
    	    			 " AND m.STATUS                  ='L' "+
    	    			 " AND m.APPROVED_BY            IS not NULL" +
    	    			 " AND T.BILL_NO NOT            IN" + 
    	    			 "  (SELECT T.BILL_NO" + 
    	    			 "  FROM FAS_PASS_ORDER_MST m ," + 
    	    			 "    FAS_PASS_ORDER_TRN T" + 
    	    			 "  WHERE m.ACCOUNTING_UNIT_ID    =t.ACCOUNTING_UNIT_ID" + 
    	    			 "  AND m.ACCOUNTING_FOR_OFFICE_ID=t.ACCOUNTING_FOR_OFFICE_ID" + 
    	    			 "  AND m.CASHBOOK_YEAR           =t.CASHBOOK_YEAR" + 
    	    			 "  AND M.CASHBOOK_MONTH          =T.CASHBOOK_MONTH" + 
    	    			 "  AND m.PASS_ORDER_NO           =t.PASS_ORDER_NO" + 
    	    			 "  AND t.ACCOUNTING_UNIT_ID      =" +cmbAcc_UnitCode+ 
    	    			 "  AND t.CASHBOOK_YEAR           =" +txtCB_Year+
    	    			 "  AND T.CASHBOOK_MONTH          ="+txtCB_Month+
    	    			 "  AND m.STATUS                  ='L'" + 
    	    			 "  AND t.BILL_NO                IN" + 
    	    			 "    (SELECT Mm.BILL_NO" + 
    	    			 "    FROM FAS_MTC70_REGISTER MM" + 
    	    			 "    WHERE Mm.ACCOUNTING_UNIT_ID =" +cmbAcc_UnitCode+
    	    			 "    AND MM.CASHBOOK_YEAR        =" +txtCB_Year+ 
    	    			 "    AND Mm.CASHBOOK_MONTH       =" +txtCB_Month+ 
    	    			 "    AND MM.STATUS               ='L'" + 
    	    			 "    AND T.BILL_NO=MM.BILL_NO" + 
    	    			 "    and T.BILL_DATE=MM.BILL_DATE " +
    	    			 "    )" + 
    	    			 "  ) ";
    	    			 
    		System.out.println(sql1);
    		PreparedStatement pa_t=con.prepareStatement(sql1);
    		 
    	ResultSet rene01w=pa_t.executeQuery();
   	
   	int count=0;
    	while(rene01w.next())
    	{
    		 billno=rene01w.getInt("bill_no");
    		passno=rene01w.getInt("PASS_ORDER_NO");
    		pasdate=rene01w.getString("PASS_ORDER_DATE");
    		billamt=rene01w.getInt("billamt");
    		/*sql="SELECT m.BILL_NO,m.SANCTION_PROC_NO          AS SANCTION_PROC_NO,"+
				" m.STATUS"+
    			" FROM fas_bill_register_master m"+
    			" WHERE m.ACCOUNTING_UNIT_ID     = "+cmbAcc_UnitCode+
    			" AND m.accounting_unit_office_id= "+cmbOffice_code+
    			" AND m.CASHBOOK_YEAR            = "+txtCB_Year+
    			" AND m.CASHBOOK_MONTH           = "+txtCB_Month+
    			" AND m.STATUS                   ='L'"+
    			" AND m.MTC_70_REGISTER_DATE    IS NULL and m.DRAWING_OFFICER_CODE is not null and m.BILL_APPROVED is not null "+
    			" and m.BILL_NO="+rene01w.getInt("bill_no")+
    			" union all"+
    			" SELECT m.BILL_NO,"+
    			"   m.SANCTION_PROC_NO           AS SANCTION_PROC_NO,"+
    			"  m.STATUS"+
    			" FROM fas_bill_registernew m"+
    			" WHERE m.ACCOUNTING_UNIT_ID     = "+cmbAcc_UnitCode+
    			" AND m.accounting_unit_office_id= "+cmbOffice_code+
    			" AND m.CASHBOOK_YEAR            = "+txtCB_Year+
    			" AND m.CASHBOOK_MONTH           = "+txtCB_Month+
    			" AND m.STATUS                   ='L'"+
    			" AND m.MTC_70_REGISTER_DATE    IS NULL and m.DRAWING_OFFICER_CODE is not null and m.BILL_APPROVED is not null"+
    			" and m.BILL_NO="+rene01w.getInt("bill_no");
    		
    		System.out.println("BILL NO"+billno);
    		System.out.println("sql"+sql);*/
    		//try
           // {
   		            
   		           // ps=con.prepareStatement(sql);
   		       ///     rs=ps.executeQuery();
   	              //  while(rs.next())
   	              //  {
   	                
   	                 xml=xml+"<leng>";
   	                		xml=xml+"<passno>"+passno+"</passno>";						                  
   	                		xml=xml+"<billno>"+rene01w.getInt("BILL_NO")+"</billno>";
   	                		xml=xml+"<passdate>"+pasdate+"</passdate>";
   		                    xml=xml+"<billamt>"+billamt +"</billamt>";
   		                    xml=xml+"<STATUS>"+rene01w.getString("STATUS") +"</STATUS>";
   		                 xml=xml+"</leng>";
   		                    count++;
   	               // }
   	               
         //   }
          //  catch(SQLException sqle)
           // {
   	        	   // sqle.printStackTrace();
   	        	   // System.out.println("error while fetching data " + sqle);
   	               
           // }
           
   		              
    	} System.out.println("xml>>> "+xml);
    	if(count>0) 
        {
            xml=xml+"<flag>success</flag>";
          
   }
   else
   {
   	 xml=xml+"<flag>failure</flag>";
   //	 sendMessage(response,"No Data","ok"); 
   }
    	   rene01w.close();
              pa_t.close(); 
    	}
    	 catch(Exception ee)
    	 {
    		 xml="<response><command>searchByMonth</command><flag>failure</flag>";
    		 System.out.println("exception in st:::"+ee);
    	 }
    	
     }
     else if(optionId==7)
     {
    	 int ori_unit=0;
 		int	ori_office=0;
    	 
    	
    	 
    	/* if(txtCB_Year>2014 && txtCB_Month>3)
			{
				 sub_q = " FAS_BILL_REGISTER_MASTERNEW "; 
				 sub_main=" Fas_Bill_Register_MasterNEW M, "+
				" 	  Fas_Bill_Register_Transactionw T ";
			}else{
				sub_q = " FAS_BILL_REGISTER_MASTER "; 
				 sub_main=" Fas_Bill_Register_Master M, "+
							" 	  Fas_Bill_Register_Transaction T ";
			}*/
 		
 		if (txtCB_Year > 2014) {
			if (txtCB_Year == 2015 && txtCB_Month <= 3) {
				sub_q = " FAS_BILL_REGISTER_MASTER "; 
				 sub_main=" Fas_Bill_Register_Master M, "+
							" 	  Fas_Bill_Register_Transaction T ";
			}else{
				 sub_q = " FAS_BILL_REGISTER_MASTERNEW "; 
				 sub_main=" Fas_Bill_Register_MasterNEW M, "+
				" 	  Fas_Bill_Register_Transactionw T ";	
			}
		}else{
			sub_q = " FAS_BILL_REGISTER_MASTER "; 
			 sub_main=" Fas_Bill_Register_Master M, "+
						" 	  Fas_Bill_Register_Transaction T ";
		}
    	 try{
    	    	/*psnew=con.prepareStatement("SELECT m.SANCTIONED_AMOUNT  AS sancamount,m.bill_no as bno,m.bill_date FROM FAS_MTC70_REGISTER m "+
										" WHERE m.ACCOUNTING_UNIT_ID      = "+cmbAcc_UnitCode+" AND m.CASHBOOK_YEAR           = "+txtCB_Year+
										" AND m.CASHBOOK_MONTH          = "+txtCB_Month+" 			AND m.status                  ='L' "
												+ " AND   M.BILL_NO     not     IN (SELECT T.BILL_NO FROM FAS_CHEQUE_MEMO_TRN T WHERE  T.ACCOUNTING_UNIT_ID = "+cmbAcc_UnitCode+" )");
    	    	*/
    		
    		 psnew=con.prepareStatement("SELECT m.SANCTIONED_AMOUNT AS sancamount, " +
"  M.BILL_NO                AS BNO, " +
"  m.bill_date, " +
"  M.MTC70_ENTRY_DATE " +
" FROM FAS_MTC70_REGISTER m " +
" WHERE m.ACCOUNTING_UNIT_ID =  " +cmbAcc_UnitCode+
" AND M.CASHBOOK_YEAR        =  " +txtCB_Year+
"  AND M.CASHBOOK_MONTH       = " +txtCB_Month+
" AND m.status               ='L' " +
" AND M.BILL_NO       not      IN " +
"  (SELECT M.BILL_NO " +
"  FROM FAS_MTC70_REGISTER m " +
"  WHERE m.ACCOUNTING_UNIT_ID = " +cmbAcc_UnitCode+
"  AND M.CASHBOOK_YEAR        = " +txtCB_Year+
"  AND M.CASHBOOK_MONTH       = " +txtCB_Month+
"  AND m.status               ='L' " +
"  AND M.BILL_NO             IN " +
"    (SELECT T.BILL_NO " +
"    FROM FAS_CHEQUE_MEMO_TRN T,FAS_CHEQUE_MEMO_MST MM  " +
"    WHERE T.ACCOUNTING_UNIT_ID = " +cmbAcc_UnitCode+
 "   and T.ACCOUNTING_UNIT_ID = MM.ACCOUNTING_UNIT_ID  " +
 "    and T.CASHBOOK_MONTH = MM.CASHBOOK_MONTH AND T.CASHBOOK_YEAR = MM.CASHBOOK_YEAR " +
 "    and T.CHEQUE_MEMO_NO = MM.CHEQUE_MEMO_NO " +
"    AND T.BILL_NO              =M.BILL_NO " +
"    AND T.BILL_DATE            =M.BILL_DATE " +
"      and mm.status='L'  ) " +
"  )" );
    		 
    		 System.out.println("SELECT m.SANCTIONED_AMOUNT AS sancamount, " +
    				 "  M.BILL_NO                AS BNO, " +
    				 "  m.bill_date, " +
    				 "  M.MTC70_ENTRY_DATE " +
    				 " FROM FAS_MTC70_REGISTER m " +
    				 " WHERE m.ACCOUNTING_UNIT_ID =  " +cmbAcc_UnitCode+
    				 " AND M.CASHBOOK_YEAR        =  " +txtCB_Year+
    				 "  AND M.CASHBOOK_MONTH       = " +txtCB_Month+
    				 " AND m.status               ='L' " +
    				 " AND M.BILL_NO       not      IN " +
    				 "  (SELECT M.BILL_NO " +
    				 "  FROM FAS_MTC70_REGISTER m " +
    				 "  WHERE m.ACCOUNTING_UNIT_ID = " +cmbAcc_UnitCode+
    				 "  AND M.CASHBOOK_YEAR        = " +txtCB_Year+
    				 "  AND M.CASHBOOK_MONTH       = " +txtCB_Month+
    				 "  AND m.status               ='L' " +
    				 "  AND M.BILL_NO             IN " +
    				 "    (SELECT T.BILL_NO " +
    				 "    FROM FAS_CHEQUE_MEMO_TRN T,FAS_CHEQUE_MEMO_MST MM  " +
    				 "    WHERE T.ACCOUNTING_UNIT_ID = " +cmbAcc_UnitCode+
    				  "   and T.ACCOUNTING_UNIT_ID = MM.ACCOUNTING_UNIT_ID  " +
    				  "    and T.CASHBOOK_MONTH = MM.CASHBOOK_MONTH AND T.CASHBOOK_YEAR = MM.CASHBOOK_YEAR " +
    				  "    and T.CHEQUE_MEMO_NO = MM.CHEQUE_MEMO_NO " +
    				 "    AND T.BILL_NO              =M.BILL_NO " +
    				 "    AND T.BILL_DATE            =M.BILL_DATE " +
    				 "      and mm.status='L'  ) " +
    				 "  )");
    	    	renew=psnew.executeQuery();
    	    	  int count=0;
    	    	
    	    	while(renew.next())
    	    	{
    	    		
    	    		 try{
    	    	   			PreparedStatement ps_ori=con.prepareStatement("SELECT DISTINCT m.CASHBOOK_YEAR, " +
    	    	   					"  M.CASHBOOK_month, " +
    	    	   					"  M.ACCOUNTING_UNIT_ID, " +
    	    	   					"  M.ACCOUNTING_FOR_OFFICE_ID " +
    	    	   					"FROM FAS_MEMO_OF_PAYMENT_MST M, " +
    	    	   					"  FAS_MEMO_OF_PAYMENT_TRN T " +
    	    	   					"WHERE M.ACCOUNTING_UNIT_ID    =T.ACCOUNTING_UNIT_ID " +
    	    	   					"AND M.ACCOUNTING_FOR_OFFICE_ID=T.ACCOUNTING_FOR_OFFICE_ID " +
    	    	   					"AND M.CASHBOOK_YEAR           =T.CASHBOOK_YEAR " +
    	    	   					"AND M.CASHBOOK_MONTH          =T.CASHBOOK_MONTH " +
    	    	   					"AND M.Bill_No                 =T.Bill_No " +
    	    	   					"AND M.Bill_No                 =? " +
    	    	   					"AND m.STATUS                  ='L' " +
    	    	   					"AND t.Payment_Unit            = ? " +
    	    	   					"AND T.Payment_Office          = ? " +
    	    	   					"AND M.Cashbook_Year           =? " +
    	    	   					"AND M.CASHBOOK_MONTH          =?");
    	    	   			ps_ori.setInt(1, renew.getInt("bno"));
    	    	   			ps_ori.setInt(2, cmbAcc_UnitCode);
    	    	   			ps_ori.setInt(3, cmbOffice_code);
    	    	   			ps_ori.setInt(4, txtCB_Year);
    	    	   			ps_ori.setInt(5, txtCB_Month);
    	    	   			ResultSet rs_ori=ps_ori.executeQuery();
    	    	   			if(rs_ori.next())
    	    	   			{
    	    	   				ori_unit=rs_ori.getInt("ACCOUNTING_UNIT_ID");
    	    	   				ori_office=rs_ori.getInt("ACCOUNTING_FOR_OFFICE_ID");
    	    	   			}
    	    	   		}catch(Exception e)
    	    	   		{
    	    	   			e.printStackTrace();
    	    	   		}
    	    	System.out.println("ori_unit  >>> "+ori_unit);
    	    	System.out.println("ori_office  >>> "+ori_office);
    	    	  		
    	    	  		
    	    	  		 
    	    		
    	    		
    	    		
    	    		 sql="SELECT m.BILL_NO, "+
					  " m.SANCTION_PROC_NO          AS SANCTION_PROC_NO, "+
   		 " TO_CHAR(m.BILL_DATE,'dd/mm/yyyy')AS BILL_DATE, "+
   		 " 	  m.TOTAL_SANCTIONED_AMOUNT        AS sancamount, "+
   		 " 	  m.STATUS,'' as manual_proc_no "+
   		 " 	FROM "+sub_q+" m "+
   		 " 	WHERE m.ACCOUNTING_UNIT_ID     =  "+ori_unit+
   		 " 	AND m.accounting_unit_office_id=  "+ori_office+
   		 " 	AND m.CASHBOOK_YEAR            =  "+txtCB_Year+
   		 " 	AND m.CASHBOOK_MONTH           =  "+txtCB_Month+
   		 " 	AND m.STATUS                   ='L' and m.bill_no="+renew.getInt("bno")+
   		 " 	AND m.MTC_70_REGISTER_DATE    IS not NULL "+
   		 " 	and m.TREASURY_VERIFY_DATE is null "+
   		 " 	union all "+
   		 " 	SELECT m.BILL_NO, "+
   		 " 	  m.SANCTION_PROC_NO           AS SANCTION_PROC_NO, "+
   		 " 	  TO_CHAR(m.BILL_DATE,'dd/mm/yyyy')AS BILL_DATE, "+
   		 " 	  m.TOTAL_SANCTIONED_AMOUNT        AS sancamount, "+
   		 " 	  m.STATUS,m.manual_proceeding_no as manual_proc_no "+
   		 " 	FROM fas_bill_registernew m "+
   		 " 	WHERE m.ACCOUNTING_UNIT_ID     =  "+ori_unit+
   		 " 		AND m.accounting_unit_office_id=  "+ori_office+
   		 " 	AND m.CASHBOOK_YEAR            =  "+txtCB_Year+
   		 " 	AND m.CASHBOOK_MONTH           =  "+txtCB_Month+
   		 " 	AND m.STATUS                   ='L' and m.bill_no="+renew.getInt("bno")+
   		 " 	AND m.MTC_70_REGISTER_DATE    IS not NULL "+
   		 " 	and m.TREASURY_VERIFY_DATE is null";
    	    		// System.out.println("bill no"+renew.getInt("bno"));
    	    		// System.out.println("SQL::::"+sql);	
    	    	try	 
    	    		 {
				          
				            ps=con.prepareStatement(sql);
				            rs=ps.executeQuery();
			                while(rs.next())
			                {
			                	 xml=xml+"<leng>";
			                	
				                    xml=xml+"<bill_no>"+rs.getInt("bill_no")+"</bill_no>";						                  
				                    xml=xml+"<BILL_DATE>"+rs.getString("BILL_DATE")+"</BILL_DATE>";
				                    xml=xml+"<sanction_proceeding_no>"+rs.getString("SANCTION_PROC_NO")+"</sanction_proceeding_no>";	                
				                    xml=xml+"<sanctioned_amount>"+rs.getString("sancamount") +"</sanctioned_amount>";
				                    xml=xml+"<STATUS>"+rs.getString("STATUS") +"</STATUS>";
			                	
				                    xml=xml+"</leng>";
				                    count++;
			                }
			            	rs.close();
			            	ps.close(); 
		            }
		            catch(SQLException sqle)
		            {
			        	    sqle.printStackTrace();
			        	    System.out.println("error while fetching data " + sqle);
			               
		            }
    	    		 
    	    	}if(count>0) 
                {
                    xml=xml+"<flag>success</flag>";
                  
            }
            else
            {
            	 xml=xml+"<flag>failure</flag>";
            	// sendMessage(response,"No Data","ok"); 
            }renew.close();
             psnew.close(); 
    	 }
    	 catch(Exception e2)
    	 {
    		System.out.println(e2); 
    		 xml="<response><command>searchByMonth</command><flag>failure</flag>";
    	 }
    	
     }
     else if(optionId==8)
     {
    	 
    	int ori_unit=0;
		int	ori_office=0;
    	/* if(txtCB_Year>2014 && txtCB_Month>3)
			{
				 sub_q = " FAS_BILL_REGISTER_MASTERNEW "; 
				 sub_main=" Fas_Bill_Register_MasterNEW M, "+
				" 	  Fas_Bill_Register_Transactionw T ";
			}else{
				sub_q = " FAS_BILL_REGISTER_MASTER "; 
				 sub_main=" Fas_Bill_Register_Master M, "+
							" 	  Fas_Bill_Register_Transaction T ";
			}*/

		if (txtCB_Year > 2014) {
					if (txtCB_Year == 2015 && txtCB_Month <= 3) {
						sub_q = " FAS_BILL_REGISTER_MASTER "; 
						 sub_main=" Fas_Bill_Register_Master M, "+
									" 	  Fas_Bill_Register_Transaction T ";
					}else{
						 sub_q = " FAS_BILL_REGISTER_MASTERNEW "; 
						 sub_main=" Fas_Bill_Register_MasterNEW M, "+
						" 	  Fas_Bill_Register_Transactionw T ";
					}
	    		}else{
	    			sub_q = " FAS_BILL_REGISTER_MASTER "; 
					 sub_main=" Fas_Bill_Register_Master M, "+
								" 	  Fas_Bill_Register_Transaction T ";	
	    		}
    	 try{
 	    	psnew=con.prepareStatement("SELECT SANCTIONED_AMOUNT,BILL_NO,BILL_DATE FROM FAS_MTC70_REGISTER "+
 	    	" WHERE CHECKED_AND_PASSED_DATE IS NOT NULL AND CHECKED_AND_PASSED_BY IS NOT NULL AND STATUS  ='L' " +
 	    	" and accounting_unit_id="+cmbAcc_UnitCode+" and cashbook_year="+txtCB_Year+" and cashbook_month="+txtCB_Month);
 	    	renew=psnew.executeQuery();
 	    	 int count=0;
 	    	 
 	    	while(renew.next())
 	    	{
 	    		  try{
          			PreparedStatement ps_ori=con.prepareStatement("SELECT DISTINCT m.CASHBOOK_YEAR, " +
          					"  M.CASHBOOK_month, " +
          					"  M.ACCOUNTING_UNIT_ID, " +
          					"  M.ACCOUNTING_FOR_OFFICE_ID " +
          					"FROM FAS_MEMO_OF_PAYMENT_MST M, " +
          					"  FAS_MEMO_OF_PAYMENT_TRN T " +
          					"WHERE M.ACCOUNTING_UNIT_ID    =T.ACCOUNTING_UNIT_ID " +
          					"AND M.ACCOUNTING_FOR_OFFICE_ID=T.ACCOUNTING_FOR_OFFICE_ID " +
          					"AND M.CASHBOOK_YEAR           =T.CASHBOOK_YEAR " +
          					"AND M.CASHBOOK_MONTH          =T.CASHBOOK_MONTH " +
          					"AND M.Bill_No                 =T.Bill_No " +
          					"AND M.Bill_No                 =? " +
          					"AND m.STATUS                  ='L' " +
          					"AND t.Payment_Unit            = ? " +
          					"AND T.Payment_Office          = ? " +
          					"AND M.Cashbook_Year           =? " +
          					"AND M.CASHBOOK_MONTH          =?");
          			ps_ori.setInt(1, renew.getInt("BILL_NO"));
          			ps_ori.setInt(2, cmbAcc_UnitCode);
          			ps_ori.setInt(3, cmbOffice_code);
          			ps_ori.setInt(4, txtCB_Year);
          			ps_ori.setInt(5, txtCB_Month);
          			ResultSet rs_ori=ps_ori.executeQuery();
          			if(rs_ori.next())
          			{
          				ori_unit=rs_ori.getInt("ACCOUNTING_UNIT_ID");
          				ori_office=rs_ori.getInt("ACCOUNTING_FOR_OFFICE_ID");
          			}
          		}catch(Exception e)
          		{
          			e.printStackTrace();
          		}
System.out.println("ori_unit  >>> "+ori_unit);
System.out.println("ori_office  >>> "+ori_office);
 	    		
 	    		
 	    		
 	    		
 	    		
 	    		sql="SELECT m.BILL_NO, "+
						  " m.SANCTION_PROC_NO               AS SANCTION_PROC_NO, "+
 	    			" TO_CHAR(m.BILL_DATE,'dd/mm/yyyy')AS BILL_DATE, "+
 	    			"   m.TOTAL_SANCTIONED_AMOUNT        AS sancamount, "+
 	    			"   m.STATUS, "+
 	    			"   '' AS manual_proc_no "+
 	    			" FROM "+sub_q+" m "+
 	    			" WHERE m.ACCOUNTING_UNIT_ID     =  "+ori_unit+
 	    			" AND m.accounting_unit_office_id=  "+ori_office+
 	    			" AND m.CASHBOOK_YEAR            =  "+txtCB_Year+
 	    			" AND m.CASHBOOK_MONTH           =  "+txtCB_Month+
 	    			" AND m.STATUS                   ='L' "+
 	    			" AND m.bill_no                  ="+renew.getInt("BILL_NO")+
 	    			" AND m.MTC_70_REGISTER_DATE    IS NOT NULL "+
 	    			" AND m.TREASURY_VERIFY_DATE    IS NOT NULL and m.DOR_BY_PRE_AUDIT is null "+
 	    			" UNION ALL "+
 	    			" SELECT m.BILL_NO, "+
 	    			"   m.SANCTION_PROC_NO               AS SANCTION_PROC_NO, "+
 	    			"   TO_CHAR(m.BILL_DATE,'dd/mm/yyyy')AS BILL_DATE, "+
 	    			"   m.TOTAL_SANCTIONED_AMOUNT        AS sancamount, "+
 	    			"   m.STATUS, "+
 	    			"   m.manual_proceeding_no AS manual_proc_no "+
 	    			" FROM fas_bill_registernew m "+
 	    			" WHERE m.ACCOUNTING_UNIT_ID     = "+ori_unit+
 	    			" AND m.accounting_unit_office_id=  "+ori_office+
 	    			" AND m.CASHBOOK_YEAR            =  "+txtCB_Year+
 	    			" AND m.CASHBOOK_MONTH           =  "+txtCB_Month+
 	    			" AND m.STATUS                   ='L' "+
 	    			" AND m.bill_no                  ="+renew.getInt("BILL_NO")+
 	    			" AND m.MTC_70_REGISTER_DATE    IS NOT NULL "+
 	    			" AND m.TREASURY_VERIFY_DATE    IS NOT NULL and m.DOR_BY_PRE_AUDIT is null ";
 	    		try
	            {
			           
			            ps=con.prepareStatement(sql);
			            rs=ps.executeQuery();
		                while(rs.next())
		                {
		                	 xml=xml+"<leng>";
		                	
			                    xml=xml+"<bill_no>"+rs.getInt("bill_no")+"</bill_no>";						                  
			                    xml=xml+"<BILL_DATE>"+rs.getString("BILL_DATE")+"</BILL_DATE>";
			                    xml=xml+"<sanction_proceeding_no>"+rs.getString("SANCTION_PROC_NO")+"</sanction_proceeding_no>";	                
			                    xml=xml+"<sanctioned_amount>"+rs.getString("sancamount") +"</sanctioned_amount>";
			                    xml=xml+"<STATUS>"+rs.getString("STATUS") +"</STATUS>";
		                	
			                    xml=xml+"</leng>";
			                    count++;
		                }
		            	rs.close();
		            	ps.close();
	            }
	            catch(SQLException sqle)
	            {
		        	    sqle.printStackTrace();
		        	    System.out.println("error while fetching data " + sqle);
		               
	            }
 	    	}if(count>0) 
            {
                xml=xml+"<flag>success</flag>";
              
        }
        else
        {
        	 xml=xml+"<flag>failure</flag>";
        //	 sendMessage(response,"No Data","ok"); 
        }
    	 }
    	 catch(Exception ee)
		     {
    		 xml="<response><command>searchByMonth</command><flag>failure</flag>";
		    	 System.out.println(ee);
		     }
     }
     else if(optionId==9)
     {
    	 try{
    		  psnew=con.prepareStatement("SELECT distinct m.BILL_NO as bno,m.ACCOUNTING_UNIT_ID ,m.ACCOUNTING_FOR_OFFICE_ID "+
				" FROM FAS_MEMO_OF_PAYMENT_TRN t, "+
    				  "   fas_memo_of_payment_mst m "+
    				  " WHERE m.ACCOUNTING_UNIT_ID    =t.ACCOUNTING_UNIT_ID "+
    				  " AND m.ACCOUNTING_FOR_OFFICE_ID=t.ACCOUNTING_FOR_OFFICE_ID "+
    				  " AND m.cashbook_year           =t.cashbook_year "+
    				  " AND m.cashbook_month          =t.cashbook_month "+
    				  " AND m.CASHBOOK_MONTH          =t.CASHBOOK_MONTH "+
    				  " AND t.PVR_NO                 IS NULL "+
    				  " AND t.Payment_Unit      = "+cmbAcc_UnitCode+
    				  " AND m.CASHBOOK_YEAR           = "+txtCB_Year+
    				  " AND m.CASHBOOK_MONTH          = "+txtCB_Month+
    				  " AND m.status                  ='L'");
    		 	    	renew=psnew.executeQuery();
    		 	    	int count=0;
    		 	    	while(renew.next())
    		 	    	{
    		 	    		/* if(txtCB_Year>2014 && txtCB_Month>3)
    		 				{
    		 					 sub_q = " FAS_BILL_REGISTER_MASTERNEW "; 
    		 					 sub_main=" Fas_Bill_Register_MasterNEW M, "+
    		 					" 	  Fas_Bill_Register_Transactionw T ";
    		 				}else{
    		 					sub_q = " FAS_BILL_REGISTER_MASTER "; 
    		 					 sub_main=" Fas_Bill_Register_Master M, "+
    		 								" 	  Fas_Bill_Register_Transaction T ";
    		 				}*/
    		 	    		if (txtCB_Year > 2014) {
    							if (txtCB_Year == 2015 && txtCB_Month <= 3) {
    								sub_q = " FAS_BILL_REGISTER_MASTER "; 
    								 sub_main=" Fas_Bill_Register_Master M, "+
    											" 	  Fas_Bill_Register_Transaction T ";
    							}else{
    								 sub_q = " FAS_BILL_REGISTER_MASTERNEW "; 
    								 sub_main=" Fas_Bill_Register_MasterNEW M, "+
    								" 	  Fas_Bill_Register_Transactionw T ";
    							}
    			    		}else{
    			    			sub_q = " FAS_BILL_REGISTER_MASTER "; 
    							 sub_main=" Fas_Bill_Register_Master M, "+
    										" 	  Fas_Bill_Register_Transaction T ";	
    			    		}
    		 	 	 
 	    		sql="SELECT m.BILL_NO, "+
						  " m.SANCTION_PROC_NO               AS SANCTION_PROC_NO, "+
 	    			" TO_CHAR(m.BILL_DATE,'dd/mm/yyyy')AS BILL_DATE, "+
 	    			"   m.TOTAL_SANCTIONED_AMOUNT        AS sancamount, "+
 	    			"   m.STATUS, "+
 	    			"   '' AS manual_proc_no "+
 	    			" FROM "+sub_q+" m "+
 	    			" WHERE m.ACCOUNTING_UNIT_ID     =  "+renew.getInt("ACCOUNTING_UNIT_ID")+
 	    			" AND m.accounting_unit_office_id=  "+renew.getInt("ACCOUNTING_FOR_OFFICE_ID")+
 	    			" AND m.CASHBOOK_YEAR            =  "+txtCB_Year+
 	    			" AND m.CASHBOOK_MONTH           =  "+txtCB_Month+
 	    			" AND m.BILL_NO="+renew.getInt("bno")+" and m.STATUS                   ='L' "+
 	    			" AND m.TREASURY_VERIFY_DATE    IS NOT NULL and m.DRAWING_OFFICER_APPROVE_DATE is not null and m.DOR_BY_PRE_AUDIT is not null "+
 	    			" UNION ALL "+
 	    			" SELECT m.BILL_NO, "+
 	    			"   m.SANCTION_PROC_NO               AS SANCTION_PROC_NO, "+
 	    			"   TO_CHAR(m.BILL_DATE,'dd/mm/yyyy')AS BILL_DATE, "+
 	    			"   m.TOTAL_SANCTIONED_AMOUNT        AS sancamount, "+
 	    			"   m.STATUS, "+
 	    			"   m.manual_proceeding_no AS manual_proc_no "+
 	    			" FROM fas_bill_registernew m "+
 	    			" WHERE m.ACCOUNTING_UNIT_ID     =  "+renew.getInt("ACCOUNTING_UNIT_ID")+
 	    			" AND m.accounting_unit_office_id=   "+renew.getInt("ACCOUNTING_FOR_OFFICE_ID")+
 	    			" AND m.CASHBOOK_YEAR            =  "+txtCB_Year+
 	    			" AND m.CASHBOOK_MONTH           =  "+txtCB_Month+
 	    			" AND m.BILL_NO="+renew.getInt("bno")+" AND m.STATUS                   ='L' "+
 	    			" AND m.TREASURY_VERIFY_DATE    IS NOT NULL and m.DRAWING_OFFICER_APPROVE_DATE is not null and m.DOR_BY_PRE_AUDIT is not null ";
 	    		try
	            {
			           
			            ps=con.prepareStatement(sql);
			            rs=ps.executeQuery();
		                while(rs.next())
		                {
		                	 xml=xml+"<leng>";
		                
			                    xml=xml+"<bill_no>"+rs.getInt("bill_no")+"</bill_no>";						                  
			                    xml=xml+"<BILL_DATE>"+rs.getString("BILL_DATE")+"</BILL_DATE>";
			                    xml=xml+"<sanction_proceeding_no>"+rs.getString("SANCTION_PROC_NO")+"</sanction_proceeding_no>";	                
			                    xml=xml+"<sanctioned_amount>"+rs.getString("sancamount") +"</sanctioned_amount>";
			                    xml=xml+"<STATUS>"+rs.getString("STATUS") +"</STATUS>";
		                
		                	
			                    xml=xml+"</leng>";
			                    count++;
		                }
		            	rs.close();
		            	ps.close();
	            }
	            catch(SQLException sqle)
	            {
		        	    sqle.printStackTrace();
		        	    System.out.println("error while fetching data " + sqle);
		                
	            }
 	    	
    	 }
    		 	    	 if(count>0) 
 		                {
 			                    xml=xml+"<flag>success</flag>";
 			                  
 		                }
 		                else
 		                {
 		                	 xml=xml+"<flag>failure</flag>";
 		            //    	 sendMessage(response,"No Data","ok"); 
 		                }
    		 	    	 renew.close();
 		               psnew.close(); 
    	 }
    	 catch(Exception ee)
		     {
		    	 System.out.println(ee);
		    	 xml="<response><command>searchByMonth</command><flag>failure</flag>";
		     }
     }
     /*else if(optionId==10)
     {
    		int count=0;
    	 try{
    		  psnew=con.prepareStatement("select * from (SELECT distinct m.BILL_NO as bno ,EXTRACT (MONTH FROM T.PVR_DATE) MONTH,extract (year from t.PVR_DATE) year"+
				" FROM FAS_MEMO_OF_PAYMENT_TRN t, "+
    				  "   fas_memo_of_payment_mst m "+
    				  " WHERE m.ACCOUNTING_UNIT_ID    =t.ACCOUNTING_UNIT_ID "+
    				  " AND m.ACCOUNTING_FOR_OFFICE_ID=t.ACCOUNTING_FOR_OFFICE_ID "+
    				  " AND m.cashbook_year           =t.cashbook_year "+
    				  " AND m.cashbook_month          =t.cashbook_month "+
    				  " AND m.CASHBOOK_MONTH          =t.CASHBOOK_MONTH "+
    				  "   and M.BILL_NO=T.BILL_NO  AND t.PVR_NO                 IS not NULL "+
    				  " AND m.ACCOUNTING_UNIT_ID      = "+cmbAcc_UnitCode+
    			//	  " AND extract(year from m.PVR_NO)           = "+txtCB_Year+
    				//  " AND extract(month from m.PVR_NO)          = "+txtCB_Month+
    				  " AND m.status                  ='L' )f  where month="+txtCB_Month+" and year="+txtCB_Year);
    		 	    	renew=psnew.executeQuery();
    		 	    
    		 	    	while(renew.next())
    		 	    	{
    		 	    		sql="SELECT m.CHEQUE_MEMO_NO,"+
    		 	    		" TO_CHAR(m.CHEQUE_MEMO_DATE,'dd/mm/yyyy')AS CHEQUE_MEMO_DATE,t.BILL_NO, "+
    		 	    		" TO_CHAR(t.BILL_DATE,'dd/mm/yyyy')AS BILL_DATE, "+
							  " m.CHEQUE_AMOUNT,m.STATUS "+
    		 	    		" FROM FAS_CHEQUE_MEMO_MST m,FAS_CHEQUE_MEMO_TRN t "+
    		 	    		" WHERE m.ACCOUNTING_UNIT_ID=t.ACCOUNTING_UNIT_ID "+
    		 	    		" AND m.CASHBOOK_YEAR  =t.CASHBOOK_YEAR "+
    		 	    		" AND m.CASHBOOK_MONTH  =t.CASHBOOK_MONTH "+
    		 	    		" and m.CHEQUE_MEMO_NO=t.CHEQUE_MEMO_NO "+
    		 	    		" AND m.STATUS                ='L' "+
    		 	    		" AND m.ACCOUNTING_UNIT_ID    = "+cmbAcc_UnitCode+
    		 	    		" AND m.CASHBOOK_YEAR         = "+txtCB_Year+
    		 	    		" AND m.CASHBOOK_MONTH        = "+txtCB_Month+
    		 	    			" AND m.CASHBOOK_MONTH         = "+renew.getInt("MONTH")+
    		 	    		" AND m.CASHBOOK_YEAR        = "+renew.getInt("year")+
    		 	    		" AND t.bill_no="+renew.getInt("bno");
    		 	    	//	System.out.println("sql >>> "+sql);
    		 	    		try
				            {
						            
						            ps=con.prepareStatement(sql);
						            rs=ps.executeQuery();
					                while(rs.next())
					                {
					                	 xml=xml+"<leng>";
					                	
					                		 	xml=xml+"<memono>"+rs.getInt("CHEQUE_MEMO_NO")+"</memono>";						                  
							                    xml=xml+"<memodate>"+rs.getString("CHEQUE_MEMO_DATE")+"</memodate>";
							                    xml=xml+"<billno>"+rs.getInt("BILL_NO")+"</billno>";
							                    xml=xml+"<billdate>"+rs.getString("BILL_DATE")+"</billdate>";	                
							                    xml=xml+"<chequeamt>"+rs.getString("CHEQUE_AMOUNT") +"</chequeamt>";
							                  //  xml=xml+"<STATUS>"+rs.getString("STATUS") +"</STATUS>";
					                	
						                    xml=xml+"</leng>";
						                    count++;
					                }
					                
				            }
				            catch(SQLException sqle)
				            {
					        	    sqle.printStackTrace();
					        	    System.out.println("error while fetching data " + sqle);
					               
				            }
				           
    		 	    	}
    		 	    	 if(count>0) 
  		                {
  			                    xml=xml+"<flag>success</flag>";
  			                  
  		                }
  		                else
  		                {
  		                	 xml=xml+"<flag>failure</flag>";
  		                	 sendMessage(response,"No Data","ok"); 
  		                }
    	 }
    	 catch(Exception ee)
		     {
    		 xml="<response><command>searchByMonth</command><flag>failure</flag>";
		    	 System.out.println(ee);
		     }
     }*/
     else if(optionId==10)
     {
    		int count=0;
    	 try{
    		  
    		 	    	
    		 	    		sql="SELECT distinct m.CHEQUE_MEMO_NO,"+
    		 	    		" TO_CHAR(m.CHEQUE_MEMO_DATE,'dd/mm/yyyy')AS CHEQUE_MEMO_DATE," +
    		 	    //		+ "t.BILL_NO, "+
    		 	    	//	" TO_CHAR(t.BILL_DATE,'dd/mm/yyyy')AS BILL_DATE, "+
							  " m.CHEQUE_AMOUNT,m.STATUS "+
    		 	    		" FROM FAS_CHEQUE_MEMO_MST m,FAS_CHEQUE_MEMO_TRN t "+
    		 	    		" WHERE m.ACCOUNTING_UNIT_ID=t.ACCOUNTING_UNIT_ID "+
    		 	    		" AND m.CASHBOOK_YEAR  =t.CASHBOOK_YEAR "+
    		 	    		" AND m.CASHBOOK_MONTH  =t.CASHBOOK_MONTH "+
    		 	    		" and m.CHEQUE_MEMO_NO=t.CHEQUE_MEMO_NO "+
    		 	    		" AND m.STATUS                ='L' "+
    		 	    		" AND m.ACCOUNTING_UNIT_ID    = "+cmbAcc_UnitCode+
    		 	    		" AND m.CASHBOOK_YEAR         = "+txtCB_Year+
    		 	    		" AND m.CASHBOOK_MONTH        = "+txtCB_Month;
    		 	    		
    		 	    //		" AND t.bill_no="+renew.getInt("bno");
    		 	    	//	System.out.println("sql >>> "+sql);
    		 	    		try
				            {
						            
						            ps=con.prepareStatement(sql);
						            rs=ps.executeQuery();
					                while(rs.next())
					                {
					                	 xml=xml+"<leng>";
					                	
					                		 	xml=xml+"<memono>"+rs.getInt("CHEQUE_MEMO_NO")+"</memono>";						                  
							                    xml=xml+"<memodate>"+rs.getString("CHEQUE_MEMO_DATE")+"</memodate>";
							                    xml=xml+"<billno>0</billno>";
							                    xml=xml+"<billdate>"+rs.getString("CHEQUE_MEMO_DATE")+"</billdate>";	                
							                    xml=xml+"<chequeamt>"+rs.getString("CHEQUE_AMOUNT") +"</chequeamt>";
							                  //  xml=xml+"<STATUS>"+rs.getString("STATUS") +"</STATUS>";
					                	
						                    xml=xml+"</leng>";
						                    count++;
					                }
					            	rs.close();
					            	ps.close(); 
				            }
				            catch(SQLException sqle)
				            {
					        	    sqle.printStackTrace();
					        	    System.out.println("error while fetching data " + sqle);
					               
				            }
				           
    		 	    	
    		 	    	 if(count>0) 
  		                {
  			                    xml=xml+"<flag>success</flag>";
  			                  
  		                }
  		                else
  		                {
  		                	 xml=xml+"<flag>failure</flag>";
  		                //	 sendMessage(response,"No Data","ok"); 
  		                }
    	 }
    	 catch(Exception ee)
		     {
    		 xml="<response><command>searchByMonth</command><flag>failure</flag>";
		    	 System.out.println(ee);
		     }
     }
     else if(optionId==12)
     {
    	 //only for SLS CANCEL
    	 try{
   		  psnew=con.prepareStatement("SELECT DISTINCT sls.SANCTION_PROC_NO AS SANCTION_PROC_NO, "+
							  " sls.HRMS_SANCTION_ID               AS HRMS_SANCTION_ID "+
   				" FROM FAS_MEMO_OF_PAYMENT_MST mas "+
   				" 		INNER JOIN SLS_SANCTIONS_BILLS_LINK_MST1 sls "+
   				" 		ON mas.SANCTION_PROCEEDING_NO   =sls.HRMS_SANCTION_ID "+
   				" 		AND mas.ACCOUNTING_FOR_OFFICE_ID=  "+cmbOffice_code+
   				" 		AND mas.BILL_MAJOR_TYPE_CODE    = 2 and mas.BILL_MINOR_TYPE_CODE=2 and mas.BILL_SUB_TYPE_CODE=1 "+
   				" 		AND mas.STATUS                  ='L' "+
   				" 		AND mas.cashbook_year           =  "+txtCB_Year+
   				" 	    AND mas.cashbook_month          = "+txtCB_Month+
   				"  		AND mas.JVR_NO                 IS not NULL "+
   				" 		AND mas.JVR_DATE               IS not NULL");
   		 	    	renew=psnew.executeQuery();
   		 	    	while(renew.next())
   		 	    	{
//   		 	    		sql="SELECT m.CHEQUE_MEMO_NO,"+
//   		 	    		" TO_CHAR(m.CHEQUE_MEMO_DATE,'dd/mm/yyyy')AS CHEQUE_MEMO_DATE,t.BILL_NO, "+
//   		 	    		" TO_CHAR(t.BILL_DATE,'dd/mm/yyyy')AS BILL_DATE, "+
//							  " m.CHEQUE_AMOUNT,m.STATUS "+
//   		 	    		" FROM FAS_CHEQUE_MEMO_MST m,FAS_CHEQUE_MEMO_TRN t "+
//   		 	    		" WHERE m.ACCOUNTING_UNIT_ID=t.ACCOUNTING_UNIT_ID "+
//   		 	    		" AND m.CASHBOOK_YEAR  =t.CASHBOOK_YEAR "+
//   		 	    		" AND m.CASHBOOK_MONTH  =t.CASHBOOK_MONTH "+
//   		 	    		" and m.CHEQUE_MEMO_NO=t.CHEQUE_MEMO_NO "+
//   		 	    		" AND m.STATUS                ='L' "+
//   		 	    		" AND m.ACCOUNTING_UNIT_ID    = "+cmbAcc_UnitCode+
//   		 	    		" AND m.CASHBOOK_YEAR         = "+txtCB_Year+
//   		 	    		" AND m.CASHBOOK_MONTH        = "+txtCB_Month+
//   		 	    		" AND t.bill_no="+renew.getInt("bno");
   		 	    	}	renew.close();
   		 	    psnew.close();
   	 }
   	 catch(Exception ee)
		     {
		    	 System.out.println(ee);
		     } 
     }
     System.out.println("SQL::::"+sql);		
    	
     /// joe modified on 29 Apr 2014
     /*try
				            {
						            int count=0;
						            ps=con.prepareStatement(sql);
						            rs=ps.executeQuery();
					                while(rs.next())
					                {
					                	 xml=xml+"<leng>";
					                	if(optionId!=5 && optionId!=6 && optionId!=10)
					                	{
						                    xml=xml+"<bill_no>"+rs.getInt("bill_no")+"</bill_no>";						                  
						                    xml=xml+"<BILL_DATE>"+rs.getString("BILL_DATE")+"</BILL_DATE>";
						                    xml=xml+"<sanction_proceeding_no>"+rs.getString("SANCTION_PROC_NO")+"</sanction_proceeding_no>";	                
						                    xml=xml+"<sanctioned_amount>"+rs.getString("sancamount") +"</sanctioned_amount>";
						                    xml=xml+"<STATUS>"+rs.getString("STATUS") +"</STATUS>";
					                	} 
					                	else if(optionId==5)
					                	{
					                		xml=xml+"<passno>"+rs.getInt("PASS_ORDER_NO")+"</passno>";						                  
					                		xml=xml+"<billno>"+rs.getInt("BILL_NO")+"</billno>";
					                		xml=xml+"<passdate>"+rs.getString("PASS_ORDER_DATE")+"</passdate>";
						                    xml=xml+"<billamt>"+rs.getString("billamt") +"</billamt>";
						                    xml=xml+"<STATUS>"+rs.getString("STATUS") +"</STATUS>";
					                	}
					                	else if(optionId==6)
					                	{
					                		xml=xml+"<passno>"+passno+"</passno>";						                  
					                		xml=xml+"<billno>"+rs.getInt("BILL_NO")+"</billno>";
					                		xml=xml+"<passdate>"+pasdate+"</passdate>";
						                    xml=xml+"<billamt>"+billamt +"</billamt>";
						                    xml=xml+"<STATUS>"+rs.getString("STATUS") +"</STATUS>";
					                	}
					                	else if(optionId==10)
					                	{
					                		 	xml=xml+"<memono>"+rs.getInt("CHEQUE_MEMO_NO")+"</memono>";						                  
							                    xml=xml+"<memodate>"+rs.getString("CHEQUE_MEMO_DATE")+"</memodate>";
							                    xml=xml+"<billno>"+rs.getInt("BILL_NO")+"</billno>";
							                    xml=xml+"<billdate>"+rs.getString("BILL_DATE")+"</billdate>";	                
							                    xml=xml+"<chequeamt>"+rs.getString("CHEQUE_AMOUNT") +"</chequeamt>";
							                  //  xml=xml+"<STATUS>"+rs.getString("STATUS") +"</STATUS>";
					                	}
						                    xml=xml+"</leng>";
						                    count++;
					                }
					                if(count>0) 
					                {
						                    xml=xml+"<flag>success</flag>";
						                  
					                }
					                else
					                {
					                	 xml=xml+"<flag>failure</flag>";
					                }
				            }
				            catch(SQLException sqle)
				            {
					        	    sqle.printStackTrace();
					        	    System.out.println("error while fetching data " + sqle);
					                xml="<response><command>searchByMonth</command><flag>failure</flag>";
				            }*/
        	 
	        
	}
	        xml=xml+"</response>";   
	        out.println(xml); 
	        System.out.println(xml); 
	
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
     

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
		PreparedStatement ps2=null,ps=null,pstwo=null,psthree=null,psfour=null,psfive=null,PSYE=null;        
		ResultSet rs2=null,rs=null,resfour=null,refive=null,rsye=null;
                Date txtCrea_date=null,ch_memodate=null;
                Calendar c,c1;
                int txtCash_year=0,txtCash_Month_hid=0;
                int Bills_list=0;
                
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
			ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection			Class.forName(strDriver.trim());
			con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
		}
		catch(Exception e)
		{
			System.out.println("Exception in opening connection :"+e);
		}
		int cmbAcc_UnitCode=0,cmbOffice_code=0;
		Servlets.Security.classes.UserProfile empProfile = (Servlets.Security.classes.UserProfile) session
		.getAttribute("UserProfile");
		System.out.println("do post starts");
		int empid = empProfile.getEmployeeId();
		
	
		
		String update_user=(String)session.getAttribute("UserId");
		long l=System.currentTimeMillis();
		Timestamp ts=new Timestamp(l);                      
		 Date ctdate = new java.sql.Date(ts.getTime());  
		
		String chckparameter_Voucher_no[]=null; 
		try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
		catch(NumberFormatException e){System.out.println("exception"+e );}
	//	System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);

		try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
		catch(NumberFormatException e){System.out.println("exception"+e );}
	//	System.out.println("cmbOffice_code "+cmbOffice_code);
		
		try{Bills_list=Integer.parseInt(request.getParameter("Bills_list"));}
		catch(NumberFormatException e){System.out.println("exception Bills_list:"+e );}
	//	System.out.println("cmbOffice_code "+cmbOffice_code);
		
		int cb_month=0,cb_year=0;
		try{cb_month=Integer.parseInt(request.getParameter("txtCB_Month"));}
		catch(NumberFormatException e){System.out.println("exception"+e );}
	//	System.out.println("cmbOffice_code "+cmbOffice_code);
		
		try{cb_year=Integer.parseInt(request.getParameter("txtCB_Year"));}
		catch(NumberFormatException e){System.out.println("exception Bills_list:"+e );}
	//	System.out.println("cmbOffice_code "+cmbOffice_code);

		String[] billno1=request.getParameterValues("billno");
		String[] sanno1=request.getParameterValues("sanno");
		//System.out.println("voucherno1"+voucherno1);
		String[] voucherDate1=request.getParameterValues("billDate"); 
		String[] sanamt1=request.getParameterValues("sanamt");
		String[] status1=request.getParameterValues("status");
		String userid = (String) session.getAttribute("UserId");
		System.out.println("User Id is:" + userid);
		String updationvalue="";
		int billno2=0;
		int arrchk=0,insref=0;
		int up=0,uptwo=0,upthree=0,sans_bno2=0,uptwo_New=0,chkparam=0,ori_unit=0,ori_office=0;
		String Memo_date="";
		try{
			con.clearWarnings();
			con.setAutoCommit(false);
			//chckparameter_Voucher_no = request.getParameterValues("chckparameter"); 
		    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~chckparameter_Voucher_no>>>>"+chckparameter_Voucher_no);
                    
                    
		//	System.out.println(empid+"  chckparameter_Voucher_no>>>"+chckparameter_Voucher_no.length);
		  //  System.out.println(empid+"  billno1 length >>>"+billno1.length);          
			for(int i=0;i<billno1.length;i++)
			{
				String chkparamNew=request.getParameter("param"+i);
				System.out.println(i+"    i    check parameter value "+chkparamNew);
			if(chkparamNew.equalsIgnoreCase("Checked")){
				int asg=0;
				if(Bills_list==4){
					   asg=Integer.parseInt(billno1[i].split("-")[0]);
					   Memo_date=billno1[i].split("-")[1];
					   System.out.println("))))))))))))))))))))0 Memo Dte >>> "+Memo_date);
                     
				}else{
					   asg=Integer.parseInt(billno1[i]);
				}
				
                      
                        System.out.println("asg    "+asg);
                         String[] sd = voucherDate1[i].split("/");
                         c =
                         new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                                      Integer.parseInt(sd[0]));
                         java.util.Date d = c.getTime();
                         txtCrea_date = new Date(d.getTime());
                       
                         try {
                             txtCash_year = Integer.parseInt(sd[2]);
                         } catch (Exception e) {
                             System.out.println("exception" + e);
                         }
                       try {
                             txtCash_Month_hid = Integer.parseInt(sd[1]);
                         } catch (Exception e) {
                             System.out.println("exception" + e);
                         }
                    System.out.println("hhhh"+txtCash_Month_hid);
                         if(Bills_list==10)
                         {
                         	
                         	 String[] sd_one = status1[i].split("/");
                              c1 =
                              new GregorianCalendar(Integer.parseInt(sd_one[2]), Integer.parseInt(sd_one[1]) - 1,
                                           Integer.parseInt(sd_one[0]));
                              java.util.Date d1 = c1.getTime();
                              ch_memodate = new Date(d1.getTime());
                		      
                         }
                        // if(Bills_list!=2 && Bills_list!=4 && Bills_list!=7 && Bills_list!=8 && Bills_list!=9)
                       
                        	 sans_bno2=Integer.parseInt(sanno1[i]);
                      
                      //   System.out.println("afterrrr");
                        // billno2=Integer.parseInt(billno1[i]); 
                         if(Bills_list==4){
                        	 billno2=Integer.parseInt(billno1[i].split("-")[0]);
      					   Memo_date=billno1[i].split("-")[1];
      					   System.out.println("))))))))))))))))))))0 Memo Dte >>> "+Memo_date);
                           
      				}else{
      					billno2=Integer.parseInt(billno1[i]);
      				}
                        
                        	 arrchk=billno2;
                        	 System.out.println("Bills_list:::"+Bills_list+"sans_bno2 >>> "+sans_bno2);
                        	System.out.println("billno2 >> "+billno2);
                        	String sub_q="",sub_main="";
                        	System.out.println("************************************** Bills_list **********"+Bills_list);
                        	
                        	
                        	if(Bills_list==1)
                        	 {
                        		
                        		
                        		/*try{
                        			PreparedStatement ps_ori=con.prepareStatement("SELECT DISTINCT m.CASHBOOK_YEAR, " +
                        					"  M.CASHBOOK_month, " +
                        					"  M.ACCOUNTING_UNIT_ID, " +
                        					"  M.ACCOUNTING_FOR_OFFICE_ID " +
                        					"FROM FAS_MEMO_OF_PAYMENT_MST M, " +
                        					"  FAS_MEMO_OF_PAYMENT_TRN T " +
                        					"WHERE M.ACCOUNTING_UNIT_ID    =T.ACCOUNTING_UNIT_ID " +
                        					"AND M.ACCOUNTING_FOR_OFFICE_ID=T.ACCOUNTING_FOR_OFFICE_ID " +
                        					"AND M.CASHBOOK_YEAR           =T.CASHBOOK_YEAR " +
                        					"AND M.CASHBOOK_MONTH          =T.CASHBOOK_MONTH " +
                        					"AND M.Bill_No                 =T.Bill_No " +
                        					"AND M.Bill_No                 =? " +
                        					"AND m.STATUS                  ='L' " +
                        					"AND t.Payment_Unit            = ? " +
                        					"AND T.Payment_Office          = ? " +
                        					"AND M.Cashbook_Year           =? " +
                        					"AND M.CASHBOOK_MONTH          =?");
                        			ps_ori.setInt(1, billno2);
                        			ps_ori.setInt(2, cmbAcc_UnitCode);
                        			ps_ori.setInt(3, cmbOffice_code);
                        			ps_ori.setInt(4, txtCash_year);
                        			ps_ori.setInt(5, txtCash_Month_hid);
                        			ResultSet rs_ori=ps_ori.executeQuery();
                        			if(rs_ori.next())
                        			{
                        				ori_unit=rs_ori.getInt("ACCOUNTING_UNIT_ID");
                        				ori_office=rs_ori.getInt("ACCOUNTING_FOR_OFFICE_ID");
                        			}
                        		}catch(Exception e)
                        		{
                        			e.printStackTrace();
                        		}
System.out.println("ori_unit  >>> "+ori_unit);
System.out.println("ori_office  >>> "+ori_office);*/
       		 	    		/* if(txtCash_year>2014 && txtCash_Month_hid>3)
       		 				{
       		 					 sub_q = " FAS_BILL_REGISTER_MASTERNEW "; 
       		 					 sub_main=" Fas_Bill_Register_MasterNEW M, "+
       		 					" 	  Fas_Bill_Register_Transactionw T ";
       		 				}else{
       		 					sub_q = " FAS_BILL_REGISTER_MASTER "; 
       		 					 sub_main=" Fas_Bill_Register_Master M, "+
       		 								" 	  Fas_Bill_Register_Transaction T ";
       		 				}*/
                        		
                        		if (txtCash_year > 2014) {
                					if (txtCash_year == 2015 && txtCash_Month_hid <= 3) {
                						sub_q = " FAS_BILL_REGISTER_MASTER "; 
                						 sub_main=" Fas_Bill_Register_Master M, "+
                									" 	  Fas_Bill_Register_Transaction T ";
                					}else{
                						 sub_q = " FAS_BILL_REGISTER_MASTERNEW "; 
                						 sub_main=" Fas_Bill_Register_MasterNEW M, "+
                						" 	  Fas_Bill_Register_Transactionw T ";
                					}
                	    		}else{
                	    			sub_q = " FAS_BILL_REGISTER_MASTER "; 
                					 sub_main=" Fas_Bill_Register_Master M, "+
                								" 	  Fas_Bill_Register_Transaction T ";	
                	    		}
                        	 ps=con.prepareStatement("update "+sub_q+" set STATUS='C',UPDATED_BY_USERID=?,UPDATED_DATE=? where \n" + 
                     				"ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and \n" + 
                     				"ACCOUNTING_UNIT_OFFICE_ID="+cmbOffice_code+" and BILL_NO="+billno2+" and CASHBOOK_YEAR="+txtCash_year+" and CASHBOOK_MONTH="+txtCash_Month_hid+" and SANCTION_PROC_NO = "+sans_bno2+" and MEMO_ENTRY is null and BILL_TYPE <> 'WOSP'" );
                        	
                        	 ps.setInt(1, empid);
              				ps.setDate(2, ctdate);
              				 up=ps.executeUpdate();  
              				
                        	 updationvalue="Bill No "+billno2;
                        	 uptwo_New=1;
                        	 }
                        	 else if(Bills_list==2)
                        	 {
                        		/* ps=con.prepareStatement("update FAS_BILL_REGISTERNEW set STATUS='C',UPDATED_BY_USERID=?,UPDATED_DATE=? where \n" + 
                          				"ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and \n" + 
                          				"ACCOUNTING_UNIT_OFFICE_ID="+cmbOffice_code+" and BILL_NO="+billno2+" and CASHBOOK_YEAR="+txtCash_year+" and CASHBOOK_MONTH="+txtCash_Month_hid+" and MEMO_ENTRY is null"); 
                        		*/
                        			/*try{
                            			PreparedStatement ps_ori=con.prepareStatement("SELECT DISTINCT m.CASHBOOK_YEAR, " +
                            					"  M.CASHBOOK_month, " +
                            					"  M.ACCOUNTING_UNIT_ID, " +
                            					"  M.ACCOUNTING_FOR_OFFICE_ID " +
                            					"FROM FAS_MEMO_OF_PAYMENT_MST M, " +
                            					"  FAS_MEMO_OF_PAYMENT_TRN T " +
                            					"WHERE M.ACCOUNTING_UNIT_ID    =T.ACCOUNTING_UNIT_ID " +
                            					"AND M.ACCOUNTING_FOR_OFFICE_ID=T.ACCOUNTING_FOR_OFFICE_ID " +
                            					"AND M.CASHBOOK_YEAR           =T.CASHBOOK_YEAR " +
                            					"AND M.CASHBOOK_MONTH          =T.CASHBOOK_MONTH " +
                            					"AND M.Bill_No                 =T.Bill_No " +
                            					"AND M.Bill_No                 =? " +
                            					"AND m.STATUS                  ='L' " +
                            					"AND t.Payment_Unit            = ? " +
                            					"AND T.Payment_Office          = ? " +
                            					"AND M.Cashbook_Year           =? " +
                            					"AND M.CASHBOOK_MONTH          =?");
                            			ps_ori.setInt(1, billno2);
                            			ps_ori.setInt(2, cmbAcc_UnitCode);
                            			ps_ori.setInt(3, cmbOffice_code);
                            			ps_ori.setInt(4, txtCash_year);
                            			ps_ori.setInt(5, txtCash_Month_hid);
                            			ResultSet rs_ori=ps_ori.executeQuery();
                            			if(rs_ori.next())
                            			{
                            				ori_unit=rs_ori.getInt("ACCOUNTING_UNIT_ID");
                            				ori_office=rs_ori.getInt("ACCOUNTING_FOR_OFFICE_ID");
                            			}
                            		}catch(Exception e)
                            		{
                            			e.printStackTrace();
                            		}
    System.out.println("ori_unit  >>> "+ori_unit);
    System.out.println("ori_office  >>> "+ori_office);*/
                        		 /*if(txtCash_year>2014 && txtCash_Month_hid>3)
            		 				{
            		 					 sub_q = " FAS_BILL_REGISTER_MASTERNEW "; 
            		 					 sub_main=" Fas_Bill_Register_MasterNEW M, "+
            		 					" 	  Fas_Bill_Register_Transactionw T ";
            		 				}else{
            		 					sub_q = " FAS_BILL_REGISTER_MASTER "; 
            		 					 sub_main=" Fas_Bill_Register_Master M, "+
            		 								" 	  Fas_Bill_Register_Transaction T ";
            		 				}
                        		 
                        		 */
                        			if (txtCash_year > 2014) {
                    					if (txtCash_year == 2015 && txtCash_Month_hid <= 3) {
                    						sub_q = " FAS_BILL_REGISTER_MASTER "; 
                    						 sub_main=" Fas_Bill_Register_Master M, "+
                    									" 	  Fas_Bill_Register_Transaction T ";
                    					}else{
                    						 sub_q = " FAS_BILL_REGISTER_MASTERNEW "; 
                    						 sub_main=" Fas_Bill_Register_MasterNEW M, "+
                    						" 	  Fas_Bill_Register_Transactionw T ";
                    					}
                    	    		}else{
                    	    			sub_q = " FAS_BILL_REGISTER_MASTER "; 
                    					 sub_main=" Fas_Bill_Register_Master M, "+
                    								" 	  Fas_Bill_Register_Transaction T ";	
                    	    		}
                        		try{ 
                        		 ps=con.prepareStatement("update "+sub_q+" set STATUS='C',UPDATED_BY_USERID=?::varchar,UPDATED_DATE=? where \n" + 
                          				"ACCOUNTING_UNIT_ID='"+cmbAcc_UnitCode+"' and \n" + 
                          				"ACCOUNTING_UNIT_OFFICE_ID='"+cmbOffice_code+"' and BILL_NO='"+billno2+"' and CASHBOOK_YEAR="+txtCash_year+" and CASHBOOK_MONTH="+txtCash_Month_hid+" and SANCTION_PROC_NO::numeric = "+sans_bno2+" and MEMO_ENTRY is null and BILL_TYPE = 'WOSP' ");
                             	System.out.println("update "+sub_q+" set STATUS='C',UPDATED_BY_USERID=?::varchar,UPDATED_DATE=? where \n" + 
                          				"ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and \n" + 
                          				"ACCOUNTING_UNIT_OFFICE_ID="+cmbOffice_code+" and BILL_NO="+billno2+" and CASHBOOK_YEAR="+txtCash_year+" and CASHBOOK_MONTH="+txtCash_Month_hid+" and SANCTION_PROC_NO = "+sans_bno2+" and MEMO_ENTRY is null and BILL_TYPE = 'WOSP'" );
                        		 ps.setInt(1, empid);
                   				ps.setDate(2, ctdate);
                   				 up=ps.executeUpdate();
                        		}catch(Exception e){
                        			System.out.println("TESTING WOSP   "+billno2+" sans_bno2  "+sans_bno2);
                        			e.printStackTrace();
                        		}
                        		 updationvalue="Bill No "+billno2;
                        		 uptwo_New=1;
                        	 }
                        	 else if(Bills_list==3)
                        	 {
                        		// System.out.println("33");
                        			try{
                            			PreparedStatement ps_ori=con.prepareStatement("SELECT DISTINCT m.CASHBOOK_YEAR, " +
                            					"  M.CASHBOOK_month, " +
                            					"  M.ACCOUNTING_UNIT_ID, " +
                            					"  M.ACCOUNTING_FOR_OFFICE_ID " +
                            					"FROM FAS_MEMO_OF_PAYMENT_MST M, " +
                            					"  FAS_MEMO_OF_PAYMENT_TRN T " +
                            					"WHERE M.ACCOUNTING_UNIT_ID    =T.ACCOUNTING_UNIT_ID " +
                            					"AND M.ACCOUNTING_FOR_OFFICE_ID=T.ACCOUNTING_FOR_OFFICE_ID " +
                            					"AND M.CASHBOOK_YEAR           =T.CASHBOOK_YEAR " +
                            					"AND M.CASHBOOK_MONTH          =T.CASHBOOK_MONTH " +
                            					"AND M.Bill_No                 =T.Bill_No " +
                            					"AND M.Bill_No                 =? " +
                            					"AND m.STATUS                  ='L' " +
                            					"AND t.Payment_Unit            = ? " +
                            					"AND T.Payment_Office          = ? " +
                            					"AND M.Cashbook_Year           =? " +
                            					"AND M.CASHBOOK_MONTH          =?");
                            			ps_ori.setInt(1, billno2);
                            			ps_ori.setInt(2, cmbAcc_UnitCode);
                            			ps_ori.setInt(3, cmbOffice_code);
                            			ps_ori.setInt(4, txtCash_year);
                            			ps_ori.setInt(5, txtCash_Month_hid);
                            			ResultSet rs_ori=ps_ori.executeQuery();
                            			if(rs_ori.next())
                            			{
                            				ori_unit=rs_ori.getInt("ACCOUNTING_UNIT_ID");
                            				ori_office=rs_ori.getInt("ACCOUNTING_FOR_OFFICE_ID");
                            			}
                            		}catch(Exception e)
                            		{
                            			e.printStackTrace();
                            		}
    System.out.println("ori_unit  >>> "+ori_unit);
    System.out.println("ori_office  >>> "+ori_office);
                        		 
                        		 
                        		 int y=0,m=0;
                        		 
                        		 try{
                               		 PSYE=con.prepareStatement("SELECT EXTRACT(MONTH FROM BILL_DATE) MONTH1,EXTRACT(YEAR FROM BILL_DATE) YEAR1,SANCTION_PROCEEDING_NO  from FAS_MEMO_OF_PAYMENT_MST where \n" + 
                                 				"ACCOUNTING_UNIT_ID="+ori_unit+" and \n" + 
                                 				"ACCOUNTING_FOR_OFFICE_ID="+ori_office+" and BILL_NO="+billno2+" and CASHBOOK_YEAR="+cb_year+" and CASHBOOK_MONTH="+cb_month+" and BILL_SCRUTINY is null"); 
                               		System.out.println("SELECT EXTRACT(MONTH FROM BILL_DATE) MONTH1,EXTRACT(YEAR FROM BILL_DATE) YEAR1,SANCTION_PROCEEDING_NO  from FAS_MEMO_OF_PAYMENT_MST where \n" + 
                                 				"ACCOUNTING_UNIT_ID="+ori_unit+" and \n" + 
                                 				"ACCOUNTING_FOR_OFFICE_ID="+ori_office+" and BILL_NO="+billno2+" and CASHBOOK_YEAR="+txtCash_year+" and CASHBOOK_MONTH="+txtCash_Month_hid+" and BILL_SCRUTINY is null");
                               		rsye=PSYE.executeQuery();
                          			while(rsye.next()){
                          				m=rsye.getInt("MONTH1");
                          						y=rsye.getInt("YEAR1");
                          					
                          			}
                               	}catch(Exception e)
                               	{
                               		System.out.println("FAS_MEMO_OF_PAYMENT_MST");
                               		e.printStackTrace();
                               	}
                                	
                               System.out.println("mmmmm"+m); 	
                               System.out.println("yyyyyy"+y); 
                        		 
                        		 
                        		 
                        		 
                        		
                        	try{
                        		 ps=con.prepareStatement("delete from FAS_MEMO_OF_PAYMENT_MST where \n" + 
                          				"ACCOUNTING_UNIT_ID="+ori_unit+" and \n" + 
                          				"ACCOUNTING_FOR_OFFICE_ID="+ori_office+" and BILL_NO="+billno2+" and CASHBOOK_YEAR="+cb_year+" and CASHBOOK_MONTH="+cb_month+" and BILL_SCRUTINY is null"); 
                        		
                   				 up=ps.executeUpdate();
                        	}catch(Exception e)
                        	{
                        		System.out.println("FAS_MEMO_OF_PAYMENT_MST");
                        		e.printStackTrace();
                        	}
                        	
                        	
                        			
                        	
                        	try{
                        		PreparedStatement ps_ps=con.prepareStatement("delete from FAS_MEMO_OF_PAYMENT_TRN where \n" + 
                        	
                          				"ACCOUNTING_UNIT_ID="+ori_unit+" and \n" + 
                          				"ACCOUNTING_FOR_OFFICE_ID="+ori_office+" and BILL_NO="+billno2+" and CASHBOOK_YEAR="+cb_year+" and CASHBOOK_MONTH="+cb_month); 
                        		
                   				 ps_ps.executeUpdate();
                        		 updationvalue="Bill No "+billno2;
                        	}catch(Exception e)
                        	{
                        		
                        		e.printStackTrace();
                        	} 
                        	try{
                        		 /*if(y>2014 && m>3)
            		 				{
            		 					 sub_q = " FAS_BILL_REGISTER_MASTERNEW "; 
            		 					 sub_main=" Fas_Bill_Register_MasterNEW M, "+
            		 					" 	  Fas_Bill_Register_Transactionw T ";
            		 				}else{
            		 					sub_q = " FAS_BILL_REGISTER_MASTER "; 
            		 					 sub_main=" Fas_Bill_Register_Master M, "+
            		 								" 	  Fas_Bill_Register_Transaction T ";
            		 				}*/
                        		if (y > 2014) {
                					if (y == 2015 && m <= 3) {
                						sub_q = " FAS_BILL_REGISTER_MASTER "; 
                						 sub_main=" Fas_Bill_Register_Master M, "+
                									" 	  Fas_Bill_Register_Transaction T ";
                					}else{
                						 sub_q = " FAS_BILL_REGISTER_MASTERNEW "; 
                						 sub_main=" Fas_Bill_Register_MasterNEW M, "+
                						" 	  Fas_Bill_Register_Transactionw T ";
                					}
                	    		}else{
                	    			sub_q = " FAS_BILL_REGISTER_MASTER "; 
                					 sub_main=" Fas_Bill_Register_Master M, "+
                								" 	  Fas_Bill_Register_Transaction T ";	
                	    		}
                        		
                        		PreparedStatement ps_ps1=con.prepareStatement("update "+sub_q+" set MEMO_ENTRY=null,MEMO_UPDATED_DATE=null , UPDATED_BY_USERID="+empid+",UPDATED_DATE=clock_timestamp() where \n" + 
                        	
                          				"ACCOUNTING_UNIT_ID="+ori_unit+" and \n" + 
                          				"ACCOUNTING_UNIT_OFFICE_ID="+ori_office+" and BILL_NO="+billno2+" and CASHBOOK_YEAR="+y+" and CASHBOOK_MONTH="+m+" and SANCTION_PROC_NO::numeric = "+sans_bno2 +" and status='L'"); 
                        	System.out.println("update "+sub_q+" set MEMO_ENTRY='',MEMO_UPDATED_DATE=null , UPDATED_BY_USERID="+empid+",UPDATED_DATE=clock_timestamp() where \n" + 
                        	
                          				"ACCOUNTING_UNIT_ID="+ori_unit+" and \n" + 
                          				"ACCOUNTING_UNIT_OFFICE_ID="+ori_office+" and BILL_NO="+billno2+" and CASHBOOK_YEAR="+y+" and CASHBOOK_MONTH="+m+" and SANCTION_PROC_NO = "+sans_bno2 +" and status='L'");	
                        		uptwo_New= ps_ps1.executeUpdate();
                        		System.out.println("uptwo >>>> "+uptwo_New);
                        		 updationvalue="Bill No "+billno2;
                        	}catch(Exception e)
                        	{
                        		System.out.println("FAS_BILL_REGISTER_MASTER MEMO_ENTRY ");
                        		e.printStackTrace();
                        	} 
                        	 }
                        	 else if(Bills_list==4)
                        	 {
                        		  Date newdate=null;
                        		System.out.println("Bills_list"+ Bills_list);
                        	/*	
                        		try{
                        			PreparedStatement ps_ori=con.prepareStatement("SELECT DISTINCT m.CASHBOOK_YEAR, " +
                        					"  M.CASHBOOK_month, " +
                        					"  M.ACCOUNTING_UNIT_ID, " +
                        					"  M.ACCOUNTING_FOR_OFFICE_ID " +
                        					"FROM FAS_MEMO_OF_PAYMENT_MST M, " +
                        					"  FAS_MEMO_OF_PAYMENT_TRN T " +
                        					"WHERE M.ACCOUNTING_UNIT_ID    =T.ACCOUNTING_UNIT_ID " +
                        					"AND M.ACCOUNTING_FOR_OFFICE_ID=T.ACCOUNTING_FOR_OFFICE_ID " +
                        					"AND M.CASHBOOK_YEAR           =T.CASHBOOK_YEAR " +
                        					"AND M.CASHBOOK_MONTH          =T.CASHBOOK_MONTH " +
                        					"AND M.Bill_No                 =T.Bill_No " +
                        					"AND M.Bill_No                 =? " +
                        					"AND m.STATUS                  ='L' " +
                        					"AND t.Payment_Unit            = ? " +
                        					"AND T.Payment_Office          = ? " +
                        					"AND M.Cashbook_Year           =? " +
                        					"AND M.CASHBOOK_MONTH          =?");
                        			ps_ori.setInt(1, billno2);
                        			ps_ori.setInt(2, cmbAcc_UnitCode);
                        			ps_ori.setInt(3, cmbOffice_code);
                        			ps_ori.setInt(4, txtCash_year);
                        			ps_ori.setInt(5, txtCash_Month_hid);
                        			ResultSet rs_ori=ps_ori.executeQuery();
                        			if(rs_ori.next())
                        			{
                        				ori_unit=rs_ori.getInt("ACCOUNTING_UNIT_ID");
                        				ori_office=rs_ori.getInt("ACCOUNTING_FOR_OFFICE_ID");
                        			}
                        		}catch(Exception e)
                        		{
                        			e.printStackTrace();
                        		}
System.out.println("ori_unit  >>> "+ori_unit);
System.out.println("ori_office  >>> "+ori_office);*/
                    		 
                        		
                        		
                        		try{
                             		 PSYE=con.prepareStatement("SELECT BILL_SCRUTINY_DATE FROM FAS_BILL_SCRUTINY_DETAILS where \n" + 
                               				"ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and \n" + 
                               				"ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and BILL_NO="+billno2+" and STATUS = 'L'"); 
                             		
                             		rsye=PSYE.executeQuery();
                        			while(rsye.next()){
                        			newdate=rsye.getDate(1);
                        			}
                             	}catch(Exception e)
                             	{
                             		System.out.println("FAS_MEMO_OF_PAYMENT_MST");
                             		e.printStackTrace();
                             	}
                        		
                        		System.out.println(" ^^^^^^^^^^^^^^^^^^^^^^ newdate >>> "+newdate);
                        		
                        	try{
                        		ps=con.prepareStatement("delete from FAS_BILL_SCRUTINY_DETAILS where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and BILL_NO="+billno2); 
                        		 up=ps.executeUpdate();  
                        		 updationvalue="Bill No "+billno2;
                        	}catch(Exception e)
                        	{
                        		
                        		e.printStackTrace();
                        	} 
                        	int n=0,jj=0;
                        	if(up>0){
                        		
                        		
                        		
                        		try{
                            		PreparedStatement ps_psn=con.prepareStatement("update FAS_MEMO_OF_PAYMENT_MST set BILL_SCRUTINY = null where \n" + 
                            	
                              				"ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and \n" + 
                              				"ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and BILL_NO="+billno2+" and CASHBOOK_YEAR="+txtCash_year+" and CASHBOOK_MONTH="+txtCash_Month_hid); 
                            		 n=	ps_psn.executeUpdate();
                            		 updationvalue="Bill No "+billno2;
                            	}catch(Exception e)
                            	{
                            		
                            		e.printStackTrace();
                            	} 
                        	}
                        	try{
                        		/*if(txtCash_year>2014 && txtCash_Month_hid>3)
        		 				{
        		 					 sub_q = " FAS_BILL_REGISTER_MASTERNEW "; 
        		 					 sub_main=" Fas_Bill_Register_MasterNEW M, "+
        		 					" 	  Fas_Bill_Register_Transactionw T ";
        		 				}else{
        		 					sub_q = " FAS_BILL_REGISTER_MASTER "; 
        		 					 sub_main=" Fas_Bill_Register_Master M, "+
        		 								" 	  Fas_Bill_Register_Transaction T ";
        		 				}*/
                        		if (txtCash_year > 2014) {
                					if (txtCash_year == 2015 && txtCash_Month_hid <= 3) {
                						sub_q = " FAS_BILL_REGISTER_MASTER "; 
                						 sub_main=" Fas_Bill_Register_Master M, "+
                									" 	  Fas_Bill_Register_Transaction T ";
                					}else{
                						 sub_q = " FAS_BILL_REGISTER_MASTERNEW "; 
                						 sub_main=" Fas_Bill_Register_MasterNEW M, "+
                						" 	  Fas_Bill_Register_Transactionw T ";
                					}
                	    		}else{
                	    			sub_q = " FAS_BILL_REGISTER_MASTER "; 
                					 sub_main=" Fas_Bill_Register_Master M, "+
                								" 	  Fas_Bill_Register_Transaction T ";	
                	    		}
                       		PreparedStatement ps_ps1=con.prepareStatement("update "+sub_q+" set BILL_SCRUTINY_DONE='', BILL_SCRUTINY_BY=null , BILL_SCRUTINY_DATE=null , UPDATED_BY_USERID='"+empid+"',UPDATED_DATE=clock_timestamp() where \n" +
                                        
                        	
                          				"ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and \n" + 
                          			//	"ACCOUNTING_UNIT_OFFICE_ID="+cmbOffice_code+" and BILL_NO="+billno2+" and CASHBOOK_YEAR="+txtCash_year+" and CASHBOOK_MONTH="+txtCash_Month_hid+" and SANCTION_PROC_NO = "+sans_bno2+"  and MEMO_ENTRY='Y' and status='L' " ); 
                          			"ACCOUNTING_UNIT_OFFICE_ID="+cmbOffice_code+" and BILL_NO="+billno2+" and SANCTION_PROC_NO::numeric = "+sans_bno2+" and CASHBOOK_YEAR="+txtCash_year+" and CASHBOOK_MONTH="+txtCash_Month_hid+"   and MEMO_ENTRY='Y' and status='L' " );
                       		
                       		
                       		System.out.println("update "+sub_q+" set BILL_SCRUTINY_DONE='', BILL_SCRUTINY_BY='' , BILL_SCRUTINY_DATE='' , UPDATED_BY_USERID='"+empid+"',UPDATED_DATE=clock_timestamp() where \n" +
                      				"ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and \n" + 
                      			//	"ACCOUNTING_UNIT_OFFICE_ID="+cmbOffice_code+" and BILL_NO="+billno2+" and CASHBOOK_YEAR="+txtCash_year+" and CASHBOOK_MONTH="+txtCash_Month_hid+" and SANCTION_PROC_NO = "+sans_bno2+"  and MEMO_ENTRY='Y' and status='L' " ); 
                      			"ACCOUNTING_UNIT_OFFICE_ID="+cmbOffice_code+" and BILL_NO="+billno2+" and SANCTION_PROC_NO::numeric = "+sans_bno2+" and CASHBOOK_YEAR="+txtCash_year+" and CASHBOOK_MONTH="+txtCash_Month_hid+"   and MEMO_ENTRY='Y' and status='L' " );
                        	
                        		
                        		 jj=ps_ps1.executeUpdate();
                        	
                        		uptwo_New= ps_ps1.executeUpdate();
                        	
                        		 updationvalue="Bill No "+billno2;
                        	}catch(Exception e)
                        	{
                        		System.out.println("FAS_BILL_REGISTER_MASTER BILL_SCRUTINY_DONE ");
                        		e.printStackTrace();
                        	} 
                        if(jj >0 && n>0){
                        	uptwo_New=1;
                        }
                    	System.out.println("uptwoNEWWWW >>>> "+uptwo_New);
                        	
                        	 }
                        	 else if(Bills_list==5)
                        	 {
                        		 int y=0,M=0;
                        		 String qry="";
                        		 System.out.println("billno2::"+ctdate);
                        		 System.out.println("txtCash_year::"+txtCash_year);
                        		 System.out.println("txtCash_Month_hid::"+txtCash_Month_hid);
                       
                        		/* try{
                         			PreparedStatement ps_ori=con.prepareStatement("SELECT DISTINCT m.CASHBOOK_YEAR, " +
                         					"  M.CASHBOOK_month, " +
                         					"  M.ACCOUNTING_UNIT_ID, " +
                         					"  M.ACCOUNTING_FOR_OFFICE_ID " +
                         					"FROM FAS_MEMO_OF_PAYMENT_MST M, " +
                         					"  FAS_MEMO_OF_PAYMENT_TRN T " +
                         					"WHERE M.ACCOUNTING_UNIT_ID    =T.ACCOUNTING_UNIT_ID " +
                         					"AND M.ACCOUNTING_FOR_OFFICE_ID=T.ACCOUNTING_FOR_OFFICE_ID " +
                         					"AND M.CASHBOOK_YEAR           =T.CASHBOOK_YEAR " +
                         					"AND M.CASHBOOK_MONTH          =T.CASHBOOK_MONTH " +
                         					"AND M.Bill_No                 =T.Bill_No " +
                         					"AND M.Bill_No                 =? " +
                         					"AND m.STATUS                  ='L' " +
                         					"AND t.Payment_Unit            = ? " +
                         					"AND T.Payment_Office          = ? " +
                         					"AND M.Cashbook_Year           =? " +
                         					"AND M.CASHBOOK_MONTH          =?");
                         			ps_ori.setInt(1, billno2);
                         			ps_ori.setInt(2, cmbAcc_UnitCode);
                         			ps_ori.setInt(3, cmbOffice_code);
                         			ps_ori.setInt(4, txtCash_year);
                         			ps_ori.setInt(5, txtCash_Month_hid);
                         			ResultSet rs_ori=ps_ori.executeQuery();
                         			if(rs_ori.next())
                         			{
                         				ori_unit=rs_ori.getInt("ACCOUNTING_UNIT_ID");
                         				ori_office=rs_ori.getInt("ACCOUNTING_FOR_OFFICE_ID");
                         			}
                         		}catch(Exception e)
                         		{
                         			e.printStackTrace();
                         		}
 System.out.println("ori_unit  >>> "+ori_unit);
 System.out.println("ori_office  >>> "+ori_office);
                     		 
                        		 */
                        	Date pasDte=null;
                        		 	try{	
                                   	 
                        			String qry_ne=	 "SELECT T.BILL_NO, " +
                                   		 "  T.SANCTION_PROC_NO, " +
                                   		 "  m.PASS_ORDER_NO, " +
                                   		 "  T.BILL_DATE , " +
                                   		 "  EXTRACT (MONTH FROM T.BILL_DATE) M, " +
                                   		 "  extract (YEAR FROM t.BILL_DATE) y " +
                                   		 " FROM FAS_PASS_ORDER_mst m, " +
                                   		 "  FAS_PASS_ORDER_TRN t " +
                                   		 " WHERE M. ACCOUNTING_UNIT_ID    =T.ACCOUNTING_UNIT_ID " +
                                   		 " AND M.ACCOUNTING_FOR_OFFICE_ID =T.ACCOUNTING_FOR_OFFICE_ID " +
                                   		 " AND M.CASHBOOK_YEAR            =T.CASHBOOK_YEAR " +
                                   		 " AND M.CASHBOOK_MONTH           =T.CASHBOOK_MONTH " +
                                   		 " AND M.PASS_ORDER_NO            =T.PASS_ORDER_NO " +
              " and m.ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and \n" + 
           	" m.ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and m.PASS_ORDER_NO="+billno2+" and m.CASHBOOK_YEAR="+txtCash_year+" and m.CASHBOOK_MONTH="+txtCash_Month_hid+" and status='L' and m.APPROVED_BY is null ";
                                   		 //	 "AND m.STATUS                   = 'L'";
                        			System.out.println(qry_ne);
                                   	PSYE=con.prepareStatement(qry_ne);
                                   	rsye=PSYE.executeQuery();
                           			if(rsye.next()){
                           				M=rsye.getInt("M");
                           				y=rsye.getInt("y");
							
							}
						} catch (Exception e) {
							e.printStackTrace();
                                   	}
                        		 	
                        			System.out.println("MMMM"+M);
                        			System.out.println("YYYYYYY"+y);
                           		

						try {
							PreparedStatement ps_chk = con
									.prepareStatement("SELECT t. bill_no   FROM FAS_PASS_ORDER_mst m, "
											+ " FAS_PASS_ORDER_TRN t   WHERE M. ACCOUNTING_UNIT_ID    =T.ACCOUNTING_UNIT_ID "
											+ " AND M.ACCOUNTING_FOR_OFFICE_ID =T.ACCOUNTING_FOR_OFFICE_ID "
											+ " AND M.CASHBOOK_YEAR            =T.CASHBOOK_YEAR "
											+ "  AND M.CASHBOOK_MONTH           =T.CASHBOOK_MONTH "
											+ "  AND M.PASS_ORDER_NO            =T.PASS_ORDER_NO "
											+ "  AND m.ACCOUNTING_UNIT_ID       =  "
											+ cmbAcc_UnitCode
											+ "  AND m.ACCOUNTING_FOR_OFFICE_ID = "
											+ cmbOffice_code
											+ "  AND m.PASS_ORDER_NO            = "
											+ billno2
											+ "  AND m.CASHBOOK_YEAR            = "
											+ txtCash_year
											+ "  AND m.CASHBOOK_MONTH           = "
											+ txtCash_Month_hid
											+ " AND STATUS                     ='L'"
											+ "  AND m.APPROVED_BY             IS NULL");
							ResultSet rs_chk = ps_chk.executeQuery();
							while (rs_chk.next()) {

								
							/*	if(y>2014 && M>3)
        		 				{
        		 					 sub_q = " FAS_BILL_REGISTER_MASTERNEW "; 
        		 					 sub_main=" Fas_Bill_Register_MasterNEW M, "+
        		 					" 	  Fas_Bill_Register_Transactionw T ";
        		 				}else{
        		 					sub_q = " FAS_BILL_REGISTER_MASTER "; 
        		 					 sub_main=" Fas_Bill_Register_Master M, "+
        		 								" 	  Fas_Bill_Register_Transaction T ";
        		 				}*/
								
								if (y > 2014) {
                					if (y == 2015 && M <= 3) {
                						sub_q = " FAS_BILL_REGISTER_MASTER "; 
                						 sub_main=" Fas_Bill_Register_Master M, "+
                									" 	  Fas_Bill_Register_Transaction T ";
                					}else{
                						 sub_q = " FAS_BILL_REGISTER_MASTERNEW "; 
                						 sub_main=" Fas_Bill_Register_MasterNEW M, "+
                						" 	  Fas_Bill_Register_Transactionw T ";
                					}
                	    		}else{
                	    			sub_q = " FAS_BILL_REGISTER_MASTER "; 
                					 sub_main=" Fas_Bill_Register_Master M, "+
                								" 	  Fas_Bill_Register_Transaction T ";	
                	    		}
								
								System.out
										.println("********************qrrrruerrrrrrry****************"+rs_chk.getInt(1));
								qry = "update "+sub_q+" set PASS_ORDER_DATE=null,PASS_ORDER_BY=null,PASS_ORDER_AMOUNT=null, UPDATED_BY_USERID="
										+ empid
										+ ",UPDATED_DATE=clock_timestamp()  where \n"
										+ " ACCOUNTING_UNIT_ID="
										+ cmbAcc_UnitCode
										+ " and \n"
										+ " ACCOUNTING_UNIT_OFFICE_ID="
										+ cmbOffice_code
										+ " and CASHBOOK_YEAR="
										+ y
										+ " and CASHBOOK_MONTH="
										+ M
										+ " and BILL_SCRUTINY_DONE='Y' and MEMO_ENTRY='Y' and status='L'"
										+ " AND bill_no                  = "
										+ rs_chk.getInt(1);
								/*
								 * + "  (SELECT t. bill_no " +
								 * "  FROM FAS_PASS_ORDER_mst m, " +
								 * "    FAS_PASS_ORDER_TRN t " +
								 * "  WHERE M. ACCOUNTING_UNIT_ID    =T.ACCOUNTING_UNIT_ID "
								 * +
								 * "  AND M.ACCOUNTING_FOR_OFFICE_ID =T.ACCOUNTING_FOR_OFFICE_ID "
								 * +
								 * "  AND M.CASHBOOK_YEAR            =T.CASHBOOK_YEAR "
								 * +
								 * "  AND M.CASHBOOK_MONTH           =T.CASHBOOK_MONTH "
								 * +
								 * "  AND M.PASS_ORDER_NO            =T.PASS_ORDER_NO "
								 * + "  AND m.ACCOUNTING_UNIT_ID       = " +
								 * cmbAcc_UnitCode +
								 * "  AND m.ACCOUNTING_FOR_OFFICE_ID = " +
								 * cmbOffice_code +
								 * "  AND m.PASS_ORDER_NO            = " +
								 * billno2 +
								 * "  AND m.CASHBOOK_YEAR            = " +
								 * txtCash_year +
								 * "  AND m.CASHBOOK_MONTH           = " +
								 * txtCash_Month_hid +
								 * "  AND STATUS                     ='L' " +
								 * "  AND m.APPROVED_BY             IS NULL " +
								 * "  )";
								 */	 
                           						PreparedStatement ps_ps1new=con.prepareStatement(qry);
                         	
                         		System.out.println(qry);
                         		uptwo_New =uptwo_New+ ps_ps1new.executeUpdate();
                  				 }
                        		System.out.println("uptwo_New >>>> "+uptwo_New);
                         		 //updationvalue="Bill No "+billno2;
                         	}catch(Exception e)
                         	{
                         		System.out.println("FAS_BILL_REGISTER_MASTER PASS_ORDER_DATE ");
                         		e.printStackTrace();
                         	} 
						
						 try{
								ps = con.prepareStatement("update FAS_PASS_ORDER_MST set STATUS='C',UPDATED_BY_USERID=?,UPDATED_DATE=? where \n"
										+ "ACCOUNTING_UNIT_ID="
										+ cmbAcc_UnitCode
										+ " and \n"
										+ "ACCOUNTING_FOR_OFFICE_ID="
										+ cmbOffice_code
										+ " and PASS_ORDER_NO="
										+ billno2
										+ " and CASHBOOK_YEAR="
										+ txtCash_year
										+ " and CASHBOOK_MONTH="
										+ txtCash_Month_hid
										+ " and status='L' and APPROVED_BY is null");
										ps.setInt(1,empid);
	                      			   ps.setDate(2, ctdate);
	                      
	                      				 up=ps.executeUpdate(); 
	                      				 System.out.println("up:::"+up);
	                           		 updationvalue="Pass Order No "+billno2;
	                           		}
	                           		catch(Exception e){
	                           			 System.out.println("Exception part >>> ");
	                           			e.printStackTrace();
	                           		}	
                  			
                        	 }
                        	 else if(Bills_list==6)
                        	 {
                        		 System.out.println("PASS_ORDER_NO "+billno2);
                          		 System.out.println("BILL_NO "+sans_bno2);
                          		// System.out.println("sanamt1 "+sanamt1[i]);
                          		 
                          		 int y=0,M=0;
                        		 System.out.println("billno2::"+ctdate);
                        		 System.out.println("txtCash_year::"+txtCash_year);
                        		 System.out.println("txtCash_Month_hid::"+txtCash_Month_hid);
                        		/* try{
                         			PreparedStatement ps_ori=con.prepareStatement("SELECT DISTINCT m.CASHBOOK_YEAR, " +
                         					"  M.CASHBOOK_month, " +
                         					"  M.ACCOUNTING_UNIT_ID, " +
                         					"  M.ACCOUNTING_FOR_OFFICE_ID " +
                         					"FROM FAS_MEMO_OF_PAYMENT_MST M, " +
                         					"  FAS_MEMO_OF_PAYMENT_TRN T " +
                         					"WHERE M.ACCOUNTING_UNIT_ID    =T.ACCOUNTING_UNIT_ID " +
                         					"AND M.ACCOUNTING_FOR_OFFICE_ID=T.ACCOUNTING_FOR_OFFICE_ID " +
                         					"AND M.CASHBOOK_YEAR           =T.CASHBOOK_YEAR " +
                         					"AND M.CASHBOOK_MONTH          =T.CASHBOOK_MONTH " +
                         					"AND M.Bill_No                 =T.Bill_No " +
                         					"AND M.Bill_No                 =? " +
                         					"AND m.STATUS                  ='L' " +
                         					"AND t.Payment_Unit            = ? " +
                         					"AND T.Payment_Office          = ? " +
                         					"AND M.Cashbook_Year           =? " +
                         					"AND M.CASHBOOK_MONTH          =?");
                         			ps_ori.setInt(1, billno2);
                         			ps_ori.setInt(2, cmbAcc_UnitCode);
                         			ps_ori.setInt(3, cmbOffice_code);
                         			ps_ori.setInt(4, txtCash_year);
                         			ps_ori.setInt(5, txtCash_Month_hid);
                         			ResultSet rs_ori=ps_ori.executeQuery();
                         			if(rs_ori.next())
                         			{
                         				ori_unit=rs_ori.getInt("ACCOUNTING_UNIT_ID");
                         				ori_office=rs_ori.getInt("ACCOUNTING_FOR_OFFICE_ID");
                         			}
                         		}catch(Exception e)
                         		{
                         			e.printStackTrace();
                         		}
 System.out.println("ori_unit  >>> "+ori_unit);
 System.out.println("ori_office  >>> "+ori_office);*/
                     		 
                        	
                        		 	try{	
                                   	 
                        			String qry_ne=	 "SELECT T.BILL_NO, " +
                                   		 "  T.SANCTION_PROC_NO, " +
                                   		 "  m.PASS_ORDER_NO, " +
                                   		 "  T.BILL_DATE , " +
                                   		 "  EXTRACT (MONTH FROM T.BILL_DATE) M, " +
                                   		 "  extract (YEAR FROM t.BILL_DATE) y " +
                                   		 " FROM FAS_PASS_ORDER_mst m, " +
                                   		 "  FAS_PASS_ORDER_TRN t " +
                                   		 " WHERE M. ACCOUNTING_UNIT_ID    =T.ACCOUNTING_UNIT_ID " +
                                   		 " AND M.ACCOUNTING_FOR_OFFICE_ID =T.ACCOUNTING_FOR_OFFICE_ID " +
                                   		 " AND M.CASHBOOK_YEAR            =T.CASHBOOK_YEAR " +
                                   		 " AND M.CASHBOOK_MONTH           =T.CASHBOOK_MONTH " +
                                   		 " AND M.PASS_ORDER_NO            =T.PASS_ORDER_NO " +
              " and m.ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and \n" + 
           	" m.ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and m.PASS_ORDER_NO="+billno2+" and m.CASHBOOK_YEAR="+txtCash_year+" and m.CASHBOOK_MONTH="+txtCash_Month_hid+" and m.status='L' and m.APPROVED_BY is not null ";
                                   		 //	 "AND m.STATUS                   = 'L'";
                        			System.out.println(qry_ne);
                                   	PSYE=con.prepareStatement(qry_ne); 	
                                   	rsye=PSYE.executeQuery();
                           			if(rsye.next()){
                           				M=rsye.getInt("M");
                           				y=rsye.getInt("y");
                           			}
                                   	}catch(Exception e){
                                   		e.printStackTrace();
                                   	}
                          		 
                          		 
                        		 try{
                        		   ps=con.prepareStatement("update FAS_PASS_ORDER_MST set APPROVED_BY=null,APPROVED_DATE=null,UPDATED_BY_USERID=?,UPDATED_DATE=clock_timestamp() where ACCOUNTING_UNIT_ID= " +cmbAcc_UnitCode+
                         		   		" and ACCOUNTING_FOR_OFFICE_ID ="+cmbOffice_code+" and PASS_ORDER_NO="+billno2+" and CASHBOOK_YEAR="+txtCash_year+" and CASHBOOK_MONTH="+txtCash_Month_hid+" and status='L'");
                        	
                        		   ps.setInt(1, empid);	   System.out.println("empid >>> "+empid);
                     				//ps.setDate(2, ctdate);	   System.out.println("ctdate >>> "+ctdate);
                     				up=ps.executeUpdate();
                     				 System.out.println("----------------- Remove Approved  "+up);
                        		   updationvalue="Pass Order Approval Details for Passoder No "+billno2;
                        		 }   catch(Exception e)
                               	{
                               		
                               		e.printStackTrace();
                               	}
                        	 
                        			try {
            							PreparedStatement ps_chk = con
            									.prepareStatement("SELECT distinct t. bill_no   FROM FAS_PASS_ORDER_mst m, "
            											+ " FAS_PASS_ORDER_TRN t   WHERE M. ACCOUNTING_UNIT_ID    =T.ACCOUNTING_UNIT_ID "
            											+ " AND M.ACCOUNTING_FOR_OFFICE_ID =T.ACCOUNTING_FOR_OFFICE_ID "
            											+ " AND M.CASHBOOK_YEAR            =T.CASHBOOK_YEAR "
            											+ "  AND M.CASHBOOK_MONTH           =T.CASHBOOK_MONTH "
            											+ "  AND M.PASS_ORDER_NO            =T.PASS_ORDER_NO "
            											+ "  AND m.ACCOUNTING_UNIT_ID       =  "
            											+ cmbAcc_UnitCode
            											+ "  AND m.ACCOUNTING_FOR_OFFICE_ID = "
            											+ cmbOffice_code
            											+ "  AND m.PASS_ORDER_NO            = "
            											+ billno2
            											+ "  AND m.CASHBOOK_YEAR            = "
            											+ txtCash_year
            											+ "  AND m.CASHBOOK_MONTH           = "
            											+ txtCash_Month_hid
            											+ " AND STATUS                     ='L'"
            											+ "  AND m.APPROVED_BY             IS NULL");
            							
            							System.out.println("SELECT distinct t. bill_no   FROM FAS_PASS_ORDER_mst m, "
    											+ " FAS_PASS_ORDER_TRN t   WHERE M. ACCOUNTING_UNIT_ID    =T.ACCOUNTING_UNIT_ID "
    											+ " AND M.ACCOUNTING_FOR_OFFICE_ID =T.ACCOUNTING_FOR_OFFICE_ID "
    											+ " AND M.CASHBOOK_YEAR            =T.CASHBOOK_YEAR "
    											+ "  AND M.CASHBOOK_MONTH           =T.CASHBOOK_MONTH "
    											+ "  AND M.PASS_ORDER_NO            =T.PASS_ORDER_NO "
    											+ "  AND m.ACCOUNTING_UNIT_ID       =  "
    											+ cmbAcc_UnitCode
    											+ "  AND m.ACCOUNTING_FOR_OFFICE_ID = "
    											+ cmbOffice_code
    											+ "  AND m.PASS_ORDER_NO            = "
    											+ billno2
    											+ "  AND m.CASHBOOK_YEAR            = "
    											+ txtCash_year
    											+ "  AND m.CASHBOOK_MONTH           = "
    											+ txtCash_Month_hid
    											+ " AND STATUS                     ='L'"
    											+ "  AND m.APPROVED_BY             IS NULL");
            							ResultSet rs_chk = ps_chk.executeQuery();
            							while (rs_chk.next()) {

            								System.out
            										.println("********************qrrrruerrrrrrry****************"+rs_chk.getInt(1)); 
            								/*if(y>2014 && M>3)
                    		 				{
                    		 					 sub_q = " FAS_BILL_REGISTER_MASTERNEW "; 
                    		 					 sub_main=" Fas_Bill_Register_MasterNEW M, "+
                    		 					" 	  Fas_Bill_Register_Transactionw T ";
                    		 				}else{
                    		 					sub_q = " FAS_BILL_REGISTER_MASTER "; 
                    		 					 sub_main=" Fas_Bill_Register_Master M, "+
                    		 								" 	  Fas_Bill_Register_Transaction T ";
                    		 				}*/
            								if (y > 2014) {
                            					if (y == 2015 && M <= 3) {
                            						sub_q = " FAS_BILL_REGISTER_MASTER "; 
                            						 sub_main=" Fas_Bill_Register_Master M, "+
                            									" 	  Fas_Bill_Register_Transaction T ";
                            					}else{
                            						 sub_q = " FAS_BILL_REGISTER_MASTERNEW "; 
                            						 sub_main=" Fas_Bill_Register_MasterNEW M, "+
                            						" 	  Fas_Bill_Register_Transactionw T ";
                            					}
                            	    		}else{
                            	    			sub_q = " FAS_BILL_REGISTER_MASTER "; 
                            					 sub_main=" Fas_Bill_Register_Master M, "+
                            								" 	  Fas_Bill_Register_Transaction T ";	
                            	    		}
                        		 
                        		   try{
                                		PreparedStatement ps_ps1=con.prepareStatement("update "+sub_q+" set DRAWING_OFFICER_CODE=null,BILL_APPROVED=' ',DRAWING_OFFICER_APPROVE_DATE=null," +
                        					"REASON_FOR_REJECT=null,UPDATED_BY_USERID="+empid+",UPDATED_DATE=clock_timestamp()   where \n" + 
                                	
                                  				"ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and \n" + 
                                  				"ACCOUNTING_UNIT_OFFICE_ID="+cmbOffice_code+" and CASHBOOK_YEAR="+y+" and CASHBOOK_MONTH="+M+" and BILL_SCRUTINY_DONE='Y' and MEMO_ENTRY='Y' and status='L'  AND bill_no                  = "+ rs_chk.getInt(1) ); 
                                		
                                		uptwo_New= ps_ps1.executeUpdate();
                                		System.out.println("uptwo >>>> "+uptwo_New);
                                		 updationvalue="Bill No "+billno2;
                                	}catch(Exception e)
                                	{
                                		System.out.println("FAS_BILL_REGISTER_MASTER DRAWING_OFFICER_CODE ");
                                		e.printStackTrace();
                                	} 
                        		   
                        	 }
                        			}catch(Exception e)
                                	{
                                		System.out.println("FAS_BILL_REGISTER_MASTER DRAWING_OFFICER_CODE ");
                                		e.printStackTrace();
                                	} 
                        			
                        			}
                        	 else if(Bills_list==7)
                        	 {
                        		 
int M=0,y=0;
                     		 	try{	
                                	 
                     			String qry_ne=	 "SELECT " +
                                		 "  BILL_DATE , " +
                                		 "  EXTRACT (MONTH FROM BILL_DATE) M, " +
                                		 "  extract (YEAR FROM BILL_DATE) y " +
                                		 " FROM FAS_MTC70_REGISTER  " +
                                		 " WHERE " +
           " ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" \n" + 
       	" and ACCOUNTING_FOR_OFFICE_ID ="+cmbOffice_code+" and SANCTIONED_AMOUNT="+Integer.parseInt(sanamt1[i])+" and CASHBOOK_YEAR="+txtCash_year+" and CASHBOOK_MONTH="+txtCash_Month_hid+"  and STATUS='L'";
                                		 //	 "AND m.STATUS                   = 'L'";
                     		 	PSYE=con.prepareStatement(qry_ne); 	
                                	rsye=PSYE.executeQuery();
                        			if(rsye.next()){
                        				M=rsye.getInt("M");
                        				y=rsye.getInt("y");
                        			}
                                	}catch(Exception e){
                                		e.printStackTrace();
                                	}
                       		 
                        		 
                        		 
                        		 try{
                        		 System.out.println("yesss");
                      		   ps=con.prepareStatement("delete from FAS_MTC70_REGISTER where ACCOUNTING_UNIT_ID= " +cmbAcc_UnitCode+
                       		   		" and ACCOUNTING_FOR_OFFICE_ID ="+cmbOffice_code+" and SANCTIONED_AMOUNT="+Integer.parseInt(sanamt1[i])+" and CASHBOOK_YEAR="+txtCash_year+" and CASHBOOK_MONTH="+txtCash_Month_hid+"  and STATUS='L'");
                      		 up=ps.executeUpdate(); 
                      		   updationvalue="MTC 70 Register ";
                      		   
                        		}catch(Exception e)
                            	{
                            		
                            		e.printStackTrace();
                            	} 

                    		   try{
                    			/*   if(txtCash_year>2014 && txtCash_Month_hid>3)
           		 				{
           		 					 sub_q = " FAS_BILL_REGISTER_MASTERNEW "; 
           		 					 sub_main=" Fas_Bill_Register_MasterNEW M, "+
           		 					" 	  Fas_Bill_Register_Transactionw T ";
           		 				}else{
           		 					sub_q = " FAS_BILL_REGISTER_MASTER "; 
           		 					 sub_main=" Fas_Bill_Register_Master M, "+
           		 								" 	  Fas_Bill_Register_Transaction T ";
           		 				}*/
                    			   if (txtCash_year > 2014) {
                   					if (txtCash_year == 2015 && txtCash_Month_hid <= 3) {
                   						sub_q = " FAS_BILL_REGISTER_MASTER "; 
                   						 sub_main=" Fas_Bill_Register_Master M, "+
                   									" 	  Fas_Bill_Register_Transaction T ";
                   					}else{
                   						 sub_q = " FAS_BILL_REGISTER_MASTERNEW "; 
                   						 sub_main=" Fas_Bill_Register_MasterNEW M, "+
                   						" 	  Fas_Bill_Register_Transactionw T ";
                   					}
                   	    		}else{
                   	    			sub_q = " FAS_BILL_REGISTER_MASTER "; 
                   					 sub_main=" Fas_Bill_Register_Master M, "+
                   								" 	  Fas_Bill_Register_Transaction T ";	
                   	    		}  
                    			   
                    			   try{
                           			PreparedStatement ps_ori=con.prepareStatement("SELECT DISTINCT m.CASHBOOK_YEAR, " +
                           					"  M.CASHBOOK_month, " +
                           					"  M.ACCOUNTING_UNIT_ID, " +
                           					"  M.ACCOUNTING_FOR_OFFICE_ID " +
                           					"FROM FAS_MEMO_OF_PAYMENT_MST M, " +
                           					"  FAS_MEMO_OF_PAYMENT_TRN T " +
                           					"WHERE M.ACCOUNTING_UNIT_ID    =T.ACCOUNTING_UNIT_ID " +
                           					"AND M.ACCOUNTING_FOR_OFFICE_ID=T.ACCOUNTING_FOR_OFFICE_ID " +
                           					"AND M.CASHBOOK_YEAR           =T.CASHBOOK_YEAR " +
                           					"AND M.CASHBOOK_MONTH          =T.CASHBOOK_MONTH " +
                           					"AND M.Bill_No                 =T.Bill_No " +
                           					"AND M.Bill_No                 =? " +
                           					"AND m.STATUS                  ='L' " +
                           					"AND t.Payment_Unit            = ? " +
                           					"AND T.Payment_Office          = ? " +
                           					"AND M.Cashbook_Year           =? " +
                           					"AND M.CASHBOOK_MONTH          =?");
                           			ps_ori.setInt(1, billno2);
                           			ps_ori.setInt(2, cmbAcc_UnitCode);
                           			ps_ori.setInt(3, cmbOffice_code);
                           			ps_ori.setInt(4, txtCash_year);
                           			ps_ori.setInt(5, txtCash_Month_hid);
                           			ResultSet rs_ori=ps_ori.executeQuery();
                           			if(rs_ori.next())
                           			{
                           				ori_unit=rs_ori.getInt("ACCOUNTING_UNIT_ID");
                           				ori_office=rs_ori.getInt("ACCOUNTING_FOR_OFFICE_ID");
                           			}
                           		}catch(Exception e)
                           		{
                           			e.printStackTrace();
                           		}
   System.out.println("ori_unit  >>> "+ori_unit);
   System.out.println("ori_office  >>> "+ori_office);
                       		 
                    			   
                    			   
                    			   
                    			   
                            		PreparedStatement ps_ps1=con.prepareStatement("update "+sub_q+" set MTC_70_REGISTER_DATE=null,PRE_AUDIT_DATE=null,UPDATED_BY_USERID="+empid+",UPDATED_DATE=clock_timestamp()   where  " +
                              				"ACCOUNTING_UNIT_ID="+ori_unit+" and \n" + 
                              				"ACCOUNTING_UNIT_OFFICE_ID="+ori_office+" and BILL_NO="+billno2+" and CASHBOOK_YEAR="+txtCash_year+" and CASHBOOK_MONTH="+txtCash_Month_hid+" and SANCTION_PROC_NO::numeric = "+sans_bno2+ "  and status='L'" ); 
                            		
                            		uptwo_New= ps_ps1.executeUpdate();
                            		System.out.println("uptwo >>>> "+uptwo_New);
                            		 updationvalue="Bill No "+billno2;
                            	}catch(Exception e)
                            	{
                            		System.out.println("FAS_BILL_REGISTER_MASTER MTC_70_REGISTER_DATE ");
                            		e.printStackTrace();
                            	} 
                        	 }
                        	 else if(Bills_list==8)
                        	 {
                        		try{
                        			ps=con.prepareStatement("update FAS_MTC70_REGISTER set CHECKED_AND_PASSED_DATE=null,CHECKED_AND_PASSED_BY=null,REGISTER_UPDATED_DATE=null,PRE_AUDIT_SENT_DATE=null,UPDATED_BY_USERID=?,UPDATED_DATE=? where ACCOUNTING_UNIT_ID= " +cmbAcc_UnitCode+
                        		
                          		   		" and ACCOUNTING_FOR_OFFICE_ID ="+cmbOffice_code+" and BILL_NO="+billno2+" and CASHBOOK_YEAR="+txtCash_year+" and CASHBOOK_MONTH="+txtCash_Month_hid+" and status='L'");
                        		 ps.setInt(1, empid);
                   				ps.setDate(2, ctdate);
                   				 up=ps.executeUpdate();
                        		 updationvalue="MTC Upproval Details ";
                        		}catch(Exception e)
                            	{
                            		
                            		e.printStackTrace();
                            	} 

                     		   try{
                     			 /* if(txtCash_year>2014 && txtCash_Month_hid>3)
             		 				{
             		 					 sub_q = " FAS_BILL_REGISTER_MASTERNEW "; 
             		 					 sub_main=" Fas_Bill_Register_MasterNEW M, "+
             		 					" 	  Fas_Bill_Register_Transactionw T ";
             		 				}else{
             		 					sub_q = " FAS_BILL_REGISTER_MASTER "; 
             		 					 sub_main=" Fas_Bill_Register_Master M, "+
             		 								" 	  Fas_Bill_Register_Transaction T ";
             		 				}*/
                     			  if (txtCash_year > 2014) {
                  					if (txtCash_year == 2015 && txtCash_Month_hid <= 3) {
                  						sub_q = " FAS_BILL_REGISTER_MASTER "; 
                  						 sub_main=" Fas_Bill_Register_Master M, "+
                  									" 	  Fas_Bill_Register_Transaction T ";
                  					}else{
                  						 sub_q = " FAS_BILL_REGISTER_MASTERNEW "; 
                  						 sub_main=" Fas_Bill_Register_MasterNEW M, "+
                  						" 	  Fas_Bill_Register_Transactionw T ";
                  					}
                  	    		}else{
                  	    			sub_q = " FAS_BILL_REGISTER_MASTER "; 
                  					 sub_main=" Fas_Bill_Register_Master M, "+
                  								" 	  Fas_Bill_Register_Transaction T ";	
                  	    		}
                     			  
                     			 try{
                         			PreparedStatement ps_ori=con.prepareStatement("SELECT DISTINCT m.CASHBOOK_YEAR, " +
                         					"  M.CASHBOOK_month, " +
                         					"  M.ACCOUNTING_UNIT_ID, " +
                         					"  M.ACCOUNTING_FOR_OFFICE_ID " +
                         					"FROM FAS_MEMO_OF_PAYMENT_MST M, " +
                         					"  FAS_MEMO_OF_PAYMENT_TRN T " +
                         					"WHERE M.ACCOUNTING_UNIT_ID    =T.ACCOUNTING_UNIT_ID " +
                         					"AND M.ACCOUNTING_FOR_OFFICE_ID=T.ACCOUNTING_FOR_OFFICE_ID " +
                         					"AND M.CASHBOOK_YEAR           =T.CASHBOOK_YEAR " +
                         					"AND M.CASHBOOK_MONTH          =T.CASHBOOK_MONTH " +
                         					"AND M.Bill_No                 =T.Bill_No " +
                         					"AND M.Bill_No                 =? " +
                         					"AND m.STATUS                  ='L' " +
                         					"AND t.Payment_Unit            = ? " +
                         					"AND T.Payment_Office          = ? " +
                         					"AND M.Cashbook_Year           =? " +
                         					"AND M.CASHBOOK_MONTH          =?");
                         			ps_ori.setInt(1, billno2);
                         			ps_ori.setInt(2, cmbAcc_UnitCode);
                         			ps_ori.setInt(3, cmbOffice_code);
                         			ps_ori.setInt(4, txtCash_year);
                         			ps_ori.setInt(5, txtCash_Month_hid);
                         			ResultSet rs_ori=ps_ori.executeQuery();
                         			if(rs_ori.next())
                         			{
                         				ori_unit=rs_ori.getInt("ACCOUNTING_UNIT_ID");
                         				ori_office=rs_ori.getInt("ACCOUNTING_FOR_OFFICE_ID");
                         			}
                         		}catch(Exception e)
                         		{
                         			e.printStackTrace();
                         		}
 System.out.println("ori_unit  >>> "+ori_unit);
 System.out.println("ori_office  >>> "+ori_office);
                     		 
                     			  
                             		PreparedStatement ps_ps1=con.prepareStatement("update "+sub_q+" set TREASURY_VERIFY_DATE=null,SENT_TO_PRE_AUDIT_ON=null,DOR_BY_PRE_AUDIT='',PRE_AUDIT_DATE=null ,UPDATED_BY_USERID="+empid+",UPDATED_DATE=clock_timestamp()  where  " +
                               				"ACCOUNTING_UNIT_ID="+ori_unit+" and \n" + 
                               				"ACCOUNTING_UNIT_OFFICE_ID="+ori_office+" and BILL_NO="+billno2+" and CASHBOOK_YEAR="+txtCash_year+" and CASHBOOK_MONTH="+txtCash_Month_hid+" and SANCTION_PROC_NO ::numeric= "+sans_bno2+ " and MTC_70_REGISTER_DATE is not null and status='L'" ); 
                             		
                             		uptwo_New= ps_ps1.executeUpdate();
                            		System.out.println("uptwo >>>> "+uptwo_New);
                             		 updationvalue="Bill No "+billno2;
                             	}catch(Exception e)
                             	{
                             		System.out.println("FAS_BILL_REGISTER_MASTER set TREASURY_VERIFY_DATE ");
                             		e.printStackTrace();
                             	} 
                        	 }
                        	 else if(Bills_list==9)
                        	 {
                        		 
                        		 try{
                         			PreparedStatement ps_ori=con.prepareStatement("SELECT DISTINCT m.CASHBOOK_YEAR, " +
                         					"  M.CASHBOOK_month, " +
                         					"  M.ACCOUNTING_UNIT_ID, " +
                         					"  M.ACCOUNTING_FOR_OFFICE_ID " +
                         					"FROM FAS_MEMO_OF_PAYMENT_MST M, " +
                         					"  FAS_MEMO_OF_PAYMENT_TRN T " +
                         					"WHERE M.ACCOUNTING_UNIT_ID    =T.ACCOUNTING_UNIT_ID " +
                         					"AND M.ACCOUNTING_FOR_OFFICE_ID=T.ACCOUNTING_FOR_OFFICE_ID " +
                         					"AND M.CASHBOOK_YEAR           =T.CASHBOOK_YEAR " +
                         					"AND M.CASHBOOK_MONTH          =T.CASHBOOK_MONTH " +
                         					"AND M.Bill_No                 =T.Bill_No " +
                         					"AND M.Bill_No                 =? " +
                         					"AND m.STATUS                  ='L' " +
                         					"AND t.Payment_Unit            = ? " +
                         					"AND T.Payment_Office          = ? " +
                         					"AND M.Cashbook_Year           =? " +
                         					"AND M.CASHBOOK_MONTH          =?");
                         			ps_ori.setInt(1, billno2);
                         			ps_ori.setInt(2, cmbAcc_UnitCode);
                         			ps_ori.setInt(3, cmbOffice_code);
                         			ps_ori.setInt(4, txtCash_year);
                         			ps_ori.setInt(5, txtCash_Month_hid);
                         			ResultSet rs_ori=ps_ori.executeQuery();
                         			if(rs_ori.next())
                         			{
                         				ori_unit=rs_ori.getInt("ACCOUNTING_UNIT_ID");
                         				ori_office=rs_ori.getInt("ACCOUNTING_FOR_OFFICE_ID");
                         			}
                         		}catch(Exception e)
                         		{
                         			e.printStackTrace();
                         		}
 System.out.println("ori_unit  >>> "+ori_unit);
 System.out.println("ori_office  >>> "+ori_office);
                     		 
                        		 
                        		 try{
                        		 System.out.println("tttttt delete");
                      		   ps=con.prepareStatement("delete from FAS_PRE_AUDIT_CHECK_NEW where ACCOUNTING_UNIT_ID= " +cmbAcc_UnitCode+
                       		   		" and ACCOUNTING_FOR_OFFICE_ID ="+cmbOffice_code+" and BILLNO="+billno2+" and CASHBOOK_YEAR="+txtCash_year+" and CASHBOOK_MONTH="+txtCash_Month_hid+"  and STATUS='L'");
                      		
                      		   System.out.println("delete from FAS_PRE_AUDIT_CHECK_NEW where ACCOUNTING_UNIT_ID= " +cmbAcc_UnitCode+
                       		   		" and ACCOUNTING_FOR_OFFICE_ID ="+cmbOffice_code+" and BILLNO="+billno2+" and CASHBOOK_YEAR="+txtCash_year+" and CASHBOOK_MONTH="+txtCash_Month_hid+"  and STATUS='L'");
                      		   
                      		   up=ps.executeUpdate(); 
                      		   updationvalue="Pre-Audit Approval ";
                      		   
                        			}catch(Exception e)
                                 	{
                                 		e.printStackTrace();
                                 	} 
                      		   
                        		 try{
                        			/* if(txtCash_year>2014 && txtCash_Month_hid>3)
                		 				{
                		 					 sub_q = " FAS_BILL_REGISTER_MASTERNEW "; 
                		 					 sub_main=" Fas_Bill_Register_MasterNEW M, "+
                		 					" 	  Fas_Bill_Register_Transactionw T ";
                		 				}else{
                		 					sub_q = " FAS_BILL_REGISTER_MASTER "; 
                		 					 sub_main=" Fas_Bill_Register_Master M, "+
                		 								" 	  Fas_Bill_Register_Transaction T ";
                		 				}*/
                        			 
                        			 if (txtCash_year > 2014) {
                     					if (txtCash_year == 2015 && txtCash_Month_hid <= 3) {
                     						sub_q = " FAS_BILL_REGISTER_MASTER "; 
                     						 sub_main=" Fas_Bill_Register_Master M, "+
                     									" 	  Fas_Bill_Register_Transaction T ";
                     					}else{
                     						 sub_q = " FAS_BILL_REGISTER_MASTERNEW "; 
                     						 sub_main=" Fas_Bill_Register_MasterNEW M, "+
                     						" 	  Fas_Bill_Register_Transactionw T ";
                     					}
                     	    		}else{
                     	    			sub_q = " FAS_BILL_REGISTER_MASTER "; 
                     					 sub_main=" Fas_Bill_Register_Master M, "+
                     								" 	  Fas_Bill_Register_Transaction T ";	
                     	    		}
                              		PreparedStatement ps_ps1=con.prepareStatement("update "+sub_q+" set DRAWING_OFFICER_APPROVE_DATE=null,DOR_BY_PRE_AUDIT=null ,PRE_AUDIT_RECEIVED_BY=null,PRE_AUDIT_BY=null,PRE_AUDIT_DATE=null,BILL_APPROVED='Y',DATE_SENT_TO_TREASURY_SECTION='',PRE_AUDIT_REMARKS=''  ,UPDATED_BY_USERID="+empid+",UPDATED_DATE=clock_timestamp()  where  " +
                                				"ACCOUNTING_UNIT_ID="+ori_unit+" and \n" + 
                                				"ACCOUNTING_UNIT_OFFICE_ID="+ori_office+" and BILL_NO="+billno2+" and CASHBOOK_YEAR="+txtCash_year+" and CASHBOOK_MONTH="+txtCash_Month_hid+" and SANCTION_PROC_NO::numeric = "+sans_bno2+ "  and status='L'" ); 
                              		System.out.println(" >>>> "+"update "+sub_q+" set DRAWING_OFFICER_APPROVE_DATE='',DOR_BY_PRE_AUDIT='' ,PRE_AUDIT_RECEIVED_BY='',PRE_AUDIT_BY='',PRE_AUDIT_DATE='',BILL_APPROVED='Y',DATE_SENT_TO_TREASURY_SECTION='',PRE_AUDIT_REMARKS=''  ,UPDATED_BY_USERID="+empid+",UPDATED_DATE=SYSTIMESTAMP  where  " +
                            				"ACCOUNTING_UNIT_ID="+ori_unit+" and \n" + 
                            				"ACCOUNTING_UNIT_OFFICE_ID="+ori_office+" and BILL_NO="+billno2+" and CASHBOOK_YEAR="+txtCash_year+" and CASHBOOK_MONTH="+txtCash_Month_hid+" and SANCTION_PROC_NO = "+sans_bno2+ "  and status='L'");
                              		
                              		uptwo_New= ps_ps1.executeUpdate();
                            		System.out.println("uptwo >>>> "+uptwo_New);
                              		 updationvalue=updationvalue+"Bill No "+billno2;
                              	}catch(Exception e)
                              	{
                              		System.out.println("FAS_BILL_REGISTER_MASTER set Pre Audit ");
                              		e.printStackTrace();
                              	} 
                      		   
                      		   
                        	 }
                        	 else if(Bills_list==10)
                        	 {
                        
                        		 
                        		 String qq="";
                        			String flag_chk="";
                        			int newMonth=0,newYear=0;
                                	
                              
                        			int k=0;
                        			int j=0;
                               	 try{
                               	PreparedStatement ps_chk=con.prepareStatement("select ACCOUNTING_UNIT_ID as cnt from FAS_TRIAL_BALANCE_STATUS where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+ " and CASHBOOK_YEAR= "+txtCash_year+" and CASHBOOK_MONTH="+txtCash_Month_hid);
                               
                               	
                               	 ResultSet rs_chk=ps_chk.executeQuery();
                               	 if(rs_chk.next()){
                               		 flag_chk="Y";
                               	 }else{
                               		 flag_chk="N";
                               	 }
                               	 }catch(Exception e){
                               		 e.printStackTrace();
                               	 }System.out.println("flag_chk "+flag_chk);
                               	 if(flag_chk.equalsIgnoreCase("N")){
                        		
                        		PreparedStatement ps_two=con.prepareStatement("update FAS_CHEQUE_MEMO_MST set STATUS ='C' ,UPDATED_BY_USERID= ? , UPDATED_DATE=?  where ACCOUNTING_UNIT_ID= " +cmbAcc_UnitCode+
                           		   		" and ACCOUNTING_FOR_OFFICE_ID ="+cmbOffice_code+" and CHEQUE_MEMO_NO="+sans_bno2+" and CASHBOOK_YEAR="+txtCash_year+" and CASHBOOK_MONTH="+txtCash_Month_hid+" and status='L'");
                           		
                        		updationvalue="ChequeMemo Details ";
                           	
                        		ps_two.setString(1, userid);
                        		ps_two.setTimestamp(2, ts);
                           		  
                           		  System.out.println("update query ***"+ps_two);
                           		
          
                           		  
                           		  int nbill=ps_two.executeUpdate();
                           		  System.out.println("(((((((((((((((((((((((("+nbill);
                           		  
                           			
                           		if(nbill>0)
                    			{	 
                               		try{
                                   		PreparedStatement  psYM=con.prepareStatement("SELECT  m.CHEQUE_MEMO_NO,"+
    		 	    		" TO_CHAR(m.CHEQUE_MEMO_DATE,'dd/mm/yyyy')AS CHEQUE_MEMO_DATE," 
    		 	    	+ "t.BILL_NO, "+
    		 	    	" t.BILL_DATE,extract(month from t.BILL_DATE) as mon ,extract(year from t.BILL_DATE) as yer,  "+
							  " m.CHEQUE_AMOUNT,m.STATUS "+
    		 	    		" FROM FAS_CHEQUE_MEMO_MST m,FAS_CHEQUE_MEMO_TRN t "+
    		 	    		" WHERE m.ACCOUNTING_UNIT_ID=t.ACCOUNTING_UNIT_ID "+
    		 	    		" AND m.CASHBOOK_YEAR  =t.CASHBOOK_YEAR "+
    		 	    		" AND m.CASHBOOK_MONTH  =t.CASHBOOK_MONTH "+
    		 	    		" and m.CHEQUE_MEMO_NO=t.CHEQUE_MEMO_NO "+
    		 	    	//	" AND m.STATUS                ='L' "+
    		 	    		" AND m.ACCOUNTING_UNIT_ID    = "+cmbAcc_UnitCode+
    		 	    		" AND m.CASHBOOK_YEAR         = "+txtCash_year+
    		 	    		" AND m.CASHBOOK_MONTH        = "+txtCash_Month_hid+ " and m.CHEQUE_MEMO_NO ="+sans_bno2);
                                   	
                                   		 	  ResultSet	reYM=psYM.executeQuery();
                                   		 	  
                                   		 	    	while(reYM.next())
                                   		 	    	{
                                   		 	    	
                                   		 	    	/*if(reYM.getInt("yer")>2014 && reYM.getInt("mon")>3)
                            		 				{
                            		 					 sub_q = " FAS_BILL_REGISTER_MASTERNEW "; 
                            		 					 sub_main=" Fas_Bill_Register_MasterNEW M, "+
                            		 					" 	  Fas_Bill_Register_Transactionw T ";
                            		 				}else{
                            		 					sub_q = " FAS_BILL_REGISTER_MASTER "; 
                            		 					 sub_main=" Fas_Bill_Register_Master M, "+
                            		 								" 	  Fas_Bill_Register_Transaction T ";
                            		 				}*/
                                   		 	    		
                                   		 	    	if (reYM.getInt("yer") > 2014) {
                                    					if (reYM.getInt("yer") == 2015 && reYM.getInt("mon") <= 3) {
                                    						sub_q = " FAS_BILL_REGISTER_MASTER "; 
                                    						 sub_main=" Fas_Bill_Register_Master M, "+
                                    									" 	  Fas_Bill_Register_Transaction T ";
                                    					}else{
                                    						 sub_q = " FAS_BILL_REGISTER_MASTERNEW "; 
                                    						 sub_main=" Fas_Bill_Register_MasterNEW M, "+
                                    						" 	  Fas_Bill_Register_Transactionw T ";
                                    					}
                                    	    		}else{
                                    	    			sub_q = " FAS_BILL_REGISTER_MASTER "; 
                                    					 sub_main=" Fas_Bill_Register_Master M, "+
                                    								" 	  Fas_Bill_Register_Transaction T ";	
                                    	    		}
                                   		 	    	
                                   		 	    	
                                   		 	    try{
                                        			PreparedStatement ps_ori=con.prepareStatement("SELECT DISTINCT m.CASHBOOK_YEAR, " +
                                        					"  M.CASHBOOK_month, " +
                                        					"  M.ACCOUNTING_UNIT_ID, " +
                                        					"  M.ACCOUNTING_FOR_OFFICE_ID " +
                                        					"FROM FAS_MEMO_OF_PAYMENT_MST M, " +
                                        					"  FAS_MEMO_OF_PAYMENT_TRN T " +
                                        					"WHERE M.ACCOUNTING_UNIT_ID    =T.ACCOUNTING_UNIT_ID " +
                                        					"AND M.ACCOUNTING_FOR_OFFICE_ID=T.ACCOUNTING_FOR_OFFICE_ID " +
                                        					"AND M.CASHBOOK_YEAR           =T.CASHBOOK_YEAR " +
                                        					"AND M.CASHBOOK_MONTH          =T.CASHBOOK_MONTH " +
                                        					"AND M.Bill_No                 =T.Bill_No " +
                                        					"AND M.Bill_No                 =? " +
                                        					"AND m.STATUS                  ='L' " +
                                        					"AND t.Payment_Unit            = ? " +
                                        					"AND T.Payment_Office          = ? " +
                                        					"AND M.Cashbook_Year           =? " +
                                        					"AND M.CASHBOOK_MONTH          =?");
                                        			ps_ori.setInt(1, reYM.getInt("BILL_NO"));
                                        			ps_ori.setInt(2, cmbAcc_UnitCode);
                                        			ps_ori.setInt(3, cmbOffice_code);
                                        			ps_ori.setInt(4, txtCash_year);
                                        			//ps_ori.setInt(5, txtCash_Month_hid);
                                        			ps_ori.setInt(5, reYM.getInt("mon"));
                                        			ResultSet rs_ori=ps_ori.executeQuery();
                                        			if(rs_ori.next())
                                        			{
                                        				ori_unit=rs_ori.getInt("ACCOUNTING_UNIT_ID");
                                        				ori_office=rs_ori.getInt("ACCOUNTING_FOR_OFFICE_ID");
                                        			}
                                        		}catch(Exception e)
                                        		{
                                        			e.printStackTrace();
                                        		}
                System.out.println("ori_unit  >>> "+ori_unit);
                System.out.println("ori_office  >>> "+ori_office);
                                    		 
                                   		 	    	
                                   		 	    	
                                   		 	    	
                                               			qq="SELECT HRMS_SANCTION_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,VOUCHER_DATE "+
                    											" FROM HRM_SANCTIONS_BILLS_LINK_MST WHERE OFFICE_ID      ="+ori_unit+" AND SANCTIONED_AMOUNT="+Integer.parseInt(sanamt1[i])+" and HRMS_SANCTION_ID::varchar in ( "+
                    											" SELECT SANCTION_PROC_NO FROM  "+sub_q+"  WHERE ACCOUNTING_UNIT_ID="+ori_office+" AND BILL_NO             ="+reYM.getInt("BILL_NO")+" AND BILL_DATE=? and bill_type <> 'WOSP')";
                            	            			
                                               			psfive=con.prepareStatement(qq);
                            	            		psfive.setDate(1,reYM.getDate("BILL_DATE"));
                            	            			refive=psfive.executeQuery();
                            	            			if(refive.next())
                            	            			{
                            	            				int sanid=refive.getInt("HRMS_SANCTION_ID");
                            	            				
                            	            				int yr=refive.getInt("CASHBOOK_YEAR");
                            	            			
                            	            				 updationvalue="ChequeMemo Details ";
                            	            				ps_two=con.prepareStatement("update HRM_SANCTIONS_BILLS_LINK_MST set CASHBOOK_YEAR=null,CASHBOOK_MONTH=null,VOUCHER_NO=null,VOUCHER_DATE=null where "+
                                                       		   		" OFFICE_ID ="+ori_office+" and SANCTIONED_AMOUNT=? and HRMS_SANCTION_ID=? and CASHBOOK_YEAR=?");
                            	            				ps_two.setInt(1, Integer.parseInt(sanamt1[i]));
                            	            				ps_two.setInt(2,sanid);
                            	            				ps_two.setInt(3,yr);
                                                       		k=ps_two.executeUpdate();
                            	            			if(k>1)	
                            	            				j=j+0;
                            	            			else
                            	            				j=1;
                            	            			}
                            	            			else
                            	            			{
                            	            				
                            	            				qq="SELECT HRMS_SANCTION_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,VOUCHER_DATE "+
                    										" FROM SLS_SANCTIONS_BILLS_LINK_MST1 WHERE OFFICE_ID      ="+ori_office+" AND SANCTIONED_AMOUNT="+Integer.parseInt(sanamt1[i])+" and HRMS_SANCTION_ID::varchar in ( "+
                    												" SELECT SANCTION_PROC_NO FROM "+sub_q+" WHERE ACCOUNTING_UNIT_ID="+ori_unit+" AND BILL_NO             ="+reYM.getInt("BILL_NO")+" AND BILL_DATE=? and bill_type <> 'WOSP' )";
                            	            			
                            	            				psfive=con.prepareStatement(qq);
                            	            				psfive.setDate(1,reYM.getDate("BILL_DATE"));
                    		    	            			refive=psfive.executeQuery();
                    		    	            			if(refive.next())
                    		    	            			{
                    		    	            				insref++;
                    		    	            				ps_two=con.prepareStatement("update SLS_SANCTIONS_BILLS_LINK_MST1 set CASHBOOK_YEAR=null,CASHBOOK_MONTH=null,VOUCHER_NO=null,VOUCHER_DATE=null where "+
                    		                               		   		" OFFICE_ID ="+ori_office+" and SANCTIONED_AMOUNT="+Integer.parseInt(sanamt1[i])+" and HRMS_SANCTION_ID="+refive.getInt("HRMS_SANCTION_ID")+" and CASHBOOK_YEAR="+refive.getInt("CASHBOOK_YEAR"));
                    		                               		  updationvalue="ChequeMemo Details ";
                    		                               		k=ps_two.executeUpdate();
                                    	            			if(k>1)	
                                    	            				j=j+0;
                                    	            			else
                                    	            				j=1;
                    		    	            				
                    		    	            				
                    		    	            			}
                    		    	            			else
                    		    	            			{
                    		    	            				 updationvalue="ChequeMemo Details ";
                    		    	            				//if without sanction proceeding,then no need to go sls or hr table
                    		    	            				j=0;
                    		    	            				
                    		    	            			}
                            	            				
                            	            			}
                            	            			
                                        			
                                   		 	    	}if(j==0){up=1;
                                   		 	    updationvalue="ChequeMemo Details ";
                                   		 	    	}
                                   		 	    	else{
                                   		 	    		up=0;}
                                    	 }catch(Exception e){
                                    		 e.printStackTrace();
                                    	 }
                               	}
                               	 }else{
                               		 sendMessage(response,"TRIAL BALANCE FREEZED","ok"); 
                               	 }
                        	 }
                        	 
                         
             			
			System.out.println(">>> "+up);

            if(up==0)
            {
                System.out.println("redirect here");                                
                sendMessage(response,"Error in Cancellation","ok"); 
            }else{
            	if(Bills_list==1)
            	{
            		uptwo=uptwo_New;
            	}
            	else if(Bills_list==2)
            	{
            		uptwo=uptwo_New;
            	}
            	else if(Bills_list==3)
           	 	{
            		uptwo=uptwo_New;
            	/* psfour=con.prepareStatement("select bill_no from FAS_BILL_REGISTER_MASTER where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and CASHBOOK_YEAR="+txtCash_year+" and bill_no="+billno2);
        		 resfour=psfour.executeQuery();
        		 if(resfour.next())
        		 {
            	 pstwo=con.prepareStatement("update FAS_BILL_REGISTER_MASTER set MEMO_ENTRY=null,MEMO_UPDATED_DATE=null where \n" + 
          				"ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and \n" + 
          				"ACCOUNTING_UNIT_OFFICE_ID="+cmbOffice_code+" and BILL_NO="+billno2+" and CASHBOOK_YEAR="+txtCash_year+" and CASHBOOK_MONTH="+txtCash_Month_hid);
        		 }
        		 else
        		 {
        			 pstwo=con.prepareStatement("update FAS_BILL_REGISTERNEW set MEMO_ENTRY=null,MEMO_UPDATED_DATE=null where \n" + 
               				"ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and \n" + 
               				"ACCOUNTING_UNIT_OFFICE_ID="+cmbOffice_code+" and BILL_NO="+billno2+" and CASHBOOK_YEAR="+txtCash_year+" and CASHBOOK_MONTH="+txtCash_Month_hid);
        		 }
        		 uptwo=pstwo.executeUpdate();*/
           	 }
            else if(Bills_list==4)
            {
            	
            	
            	  try{
          			PreparedStatement ps_ori=con.prepareStatement("SELECT DISTINCT m.CASHBOOK_YEAR, " +
          					"  M.CASHBOOK_month, " +
          					"  M.ACCOUNTING_UNIT_ID, " +
          					"  M.ACCOUNTING_FOR_OFFICE_ID " +
          					"FROM FAS_MEMO_OF_PAYMENT_MST M, " +
          					"  FAS_MEMO_OF_PAYMENT_TRN T " +
          					"WHERE M.ACCOUNTING_UNIT_ID    =T.ACCOUNTING_UNIT_ID " +
          					"AND M.ACCOUNTING_FOR_OFFICE_ID=T.ACCOUNTING_FOR_OFFICE_ID " +
          					"AND M.CASHBOOK_YEAR           =T.CASHBOOK_YEAR " +
          					"AND M.CASHBOOK_MONTH          =T.CASHBOOK_MONTH " +
          					"AND M.Bill_No                 =T.Bill_No " +
          					"AND M.Bill_No                 =? " +
          					"AND m.STATUS                  ='L' " +
          					"AND t.Payment_Unit            = ? " +
          					"AND T.Payment_Office          = ? " +
          					"AND M.Cashbook_Year           =? " +
          					"AND M.CASHBOOK_MONTH          =?");
          			ps_ori.setInt(1, billno2);
          			ps_ori.setInt(2, cmbAcc_UnitCode);
          			ps_ori.setInt(3, cmbOffice_code);
          			ps_ori.setInt(4, txtCash_year);
          			ps_ori.setInt(5, txtCash_Month_hid);
          			ResultSet rs_ori=ps_ori.executeQuery();
          			if(rs_ori.next())
          			{
          				ori_unit=rs_ori.getInt("ACCOUNTING_UNIT_ID");
          				ori_office=rs_ori.getInt("ACCOUNTING_FOR_OFFICE_ID");
          			}
          		}catch(Exception e)
          		{
          			e.printStackTrace();
          		}
System.out.println("ori_unit  >>> "+ori_unit);
System.out.println("ori_office  >>> "+ori_office);
            	
            	
            	
            	 psthree=con.prepareStatement("update FAS_MEMO_OF_PAYMENT_MST set BILL_SCRUTINY=null where \n" + 
            				"ACCOUNTING_UNIT_ID="+ori_unit+" and \n" + 
            				"ACCOUNTING_FOR_OFFICE_ID="+ori_office+" and BILL_NO="+billno2+" and CASHBOOK_YEAR="+txtCash_year+" and CASHBOOK_MONTH="+txtCash_Month_hid);
            	 upthree=psthree.executeUpdate(); 
            	 System.out.println("upthree  >>> " +upthree);
            	 if(upthree>0)
            	 {
            		/* //check whether with or without sanction
            		 psfour=con.prepareStatement("select bill_no from FAS_BILL_REGISTERNEW where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and CASHBOOK_YEAR="+txtCash_year+" and bill_no="+billno2);
            		 resfour=psfour.executeQuery();
            		 if(resfour.next())
            		 {
            			 System.out.println("last if "+resfour.getString("bill_no"));
            			 pstwo=con.prepareStatement("update FAS_BILL_REGISTERNEW set BILL_SCRUTINY_DONE=null,BILL_SCRUTINY_BY=null,BILL_SCRUTINY_DATE=null where \n" + 
                    				"ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and \n" + 
                    				"ACCOUNTING_UNIT_OFFICE_ID="+cmbOffice_code+" and BILL_NO="+billno2+" and CASHBOOK_YEAR="+txtCash_year+" and CASHBOOK_MONTH="+txtCash_Month_hid); 
            		 }
            		 else
            		 { System.out.println("last if else ");
            			 pstwo=con.prepareStatement("update FAS_BILL_REGISTER_MASTER set BILL_SCRUTINY_DONE=null,BILL_SCRUTINY_BY=null,BILL_SCRUTINY_DATE=null where \n" + 
                 				"ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and \n" + 
                 				"ACCOUNTING_UNIT_OFFICE_ID="+cmbOffice_code+" and BILL_NO="+billno2+" and CASHBOOK_YEAR="+txtCash_year+" and CASHBOOK_MONTH="+txtCash_Month_hid);
            		 }
            		 uptwo=pstwo.executeUpdate();*/
            		 uptwo=uptwo_New;
            	 }
            	
            }
            else if(Bills_list==5)
            {
            	 /*String ss="select bill_no from FAS_BILL_REGISTERNEW where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and CASHBOOK_YEAR="+txtCash_year+" and bill_no="+sans_bno2;
            		 psfour=con.prepareStatement(ss);
            		 resfour=psfour.executeQuery();
            		 if(resfour.next())
            		 {
            			// System.out.println("newwww");
            			 pstwo=con.prepareStatement("update FAS_BILL_REGISTERNEW set PASS_ORDER_DATE=null,PASS_ORDER_BY=null,PASS_ORDER_AMOUNT=null where \n" + 
                    				"ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and \n" + 
                    				"ACCOUNTING_UNIT_OFFICE_ID="+cmbOffice_code+" and BILL_NO="+sans_bno2+" and CASHBOOK_YEAR="+txtCash_year+" and CASHBOOK_MONTH="+txtCash_Month_hid); 
            		 }
            		 else
            		 {
            			 System.out.println("sans_bno2::"+sans_bno2+"txtCash_year:::"+txtCash_year+"txtCash_Month_hid:::"+txtCash_Month_hid);
            			 pstwo=con.prepareStatement("update FAS_BILL_REGISTER_MASTER set PASS_ORDER_DATE=null,PASS_ORDER_BY=null,PASS_ORDER_AMOUNT=null where \n" + 
                 				"ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and \n" + 
                 				"ACCOUNTING_UNIT_OFFICE_ID="+cmbOffice_code+" and BILL_NO="+sans_bno2+" and CASHBOOK_YEAR="+txtCash_year+" and CASHBOOK_MONTH="+txtCash_Month_hid);
            		 }
            		 uptwo=pstwo.executeUpdate(); */
            	uptwo=uptwo_New;
            	 
            }
            else if(Bills_list==6)
            {
            	/*// System.out.println("here");
            		 //check whether with or without sanction
            		 psfour=con.prepareStatement("select bill_no from FAS_BILL_REGISTERNEW where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and CASHBOOK_YEAR="+txtCash_year+" and bill_no="+sans_bno2);
            		 resfour=psfour.executeQuery();
            		 if(resfour.next())
            		 {
            			// System.out.println("newwww");
            			 pstwo=con.prepareStatement("update FAS_BILL_REGISTERNEW set DRAWING_OFFICER_CODE=null,BILL_APPROVED=null,DRAWING_OFFICER_APPROVE_DATE=null where \n" + 
                    				"ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and \n" + 
                    				"ACCOUNTING_UNIT_OFFICE_ID="+cmbOffice_code+" and BILL_NO="+sans_bno2+" and CASHBOOK_YEAR="+txtCash_year+" and CASHBOOK_MONTH="+txtCash_Month_hid); 
            		 }
            		 else
            		 {
            			// System.out.println("billno2::"+billno2+"txtCash_year:::"+txtCash_year+"txtCash_Month_hid:::"+txtCash_Month_hid);
            			 pstwo=con.prepareStatement("update FAS_BILL_REGISTER_MASTER set DRAWING_OFFICER_CODE=null,BILL_APPROVED=null,DRAWING_OFFICER_APPROVE_DATE=null where \n" + 
                 				"ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and \n" + 
                 				"ACCOUNTING_UNIT_OFFICE_ID="+cmbOffice_code+" and BILL_NO="+sans_bno2+" and CASHBOOK_YEAR="+txtCash_year+" and CASHBOOK_MONTH="+txtCash_Month_hid);
            		 }
            		 uptwo=pstwo.executeUpdate(); */
            	uptwo=uptwo_New;
            	 
            }
            else if(Bills_list==7)
            {
            	/*// System.out.println("here");
            		 //check whether with or without sanction
            		 psfour=con.prepareStatement("select bill_no from FAS_BILL_REGISTERNEW where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and CASHBOOK_YEAR="+txtCash_year+" and bill_no="+billno2);
            		 resfour=psfour.executeQuery();
            		 if(resfour.next())
            		 {
            			// System.out.println("newwww");
            			 pstwo=con.prepareStatement("update FAS_BILL_REGISTERNEW set MTC_70_REGISTER_DATE=null where \n" + 
                    				"ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and \n" + 
                    				"ACCOUNTING_UNIT_OFFICE_ID="+cmbOffice_code+" and BILL_NO="+billno2+" and CASHBOOK_YEAR="+txtCash_year+" and CASHBOOK_MONTH="+txtCash_Month_hid); 
            		 }
            		 else
            		 {
            			// System.out.println("billno2::"+billno2+"txtCash_year:::"+txtCash_year+"txtCash_Month_hid:::"+txtCash_Month_hid);
            			 pstwo=con.prepareStatement("update FAS_BILL_REGISTER_MASTER set MTC_70_REGISTER_DATE=null where \n" + 
                 				"ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and \n" + 
                 				"ACCOUNTING_UNIT_OFFICE_ID="+cmbOffice_code+" and BILL_NO="+billno2+" and CASHBOOK_YEAR="+txtCash_year+" and CASHBOOK_MONTH="+txtCash_Month_hid);
            		 }
            		 uptwo=pstwo.executeUpdate(); */
            	uptwo=uptwo_New;
            	 
            }
            else if(Bills_list==8)
            {
           /* //	 System.out.println("here");
            		 //check whether with or without sanction
            		 psfour=con.prepareStatement("select bill_no from FAS_BILL_REGISTERNEW where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and CASHBOOK_YEAR="+txtCash_year+" and bill_no="+billno2);
            		 resfour=psfour.executeQuery();
            		 if(resfour.next())
            		 {
            			// System.out.println("newwww");
            			 pstwo=con.prepareStatement("update FAS_BILL_REGISTERNEW set TREASURY_VERIFY_DATE=null,SENT_TO_PRE_AUDIT_ON=null where \n" + 
                    				"ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and \n" + 
                    				"ACCOUNTING_UNIT_OFFICE_ID="+cmbOffice_code+" and BILL_NO="+billno2+" and CASHBOOK_YEAR="+txtCash_year+" and CASHBOOK_MONTH="+txtCash_Month_hid); 
            		 }
            		 else
            		 {
            			// System.out.println("billno2::"+billno2+"txtCash_year:::"+txtCash_year+"txtCash_Month_hid:::"+txtCash_Month_hid);
            			 pstwo=con.prepareStatement("update FAS_BILL_REGISTER_MASTER set TREASURY_VERIFY_DATE=null,SENT_TO_PRE_AUDIT_ON=null where \n" + 
                 				"ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and \n" + 
                 				"ACCOUNTING_UNIT_OFFICE_ID="+cmbOffice_code+" and BILL_NO="+billno2+" and CASHBOOK_YEAR="+txtCash_year+" and CASHBOOK_MONTH="+txtCash_Month_hid);
            		 }
            		 uptwo=pstwo.executeUpdate(); */
            	uptwo=uptwo_New;
            	 
            }
            else if(Bills_list==9)
            {
            	 System.out.println("here");
            	/*	 //check whether with or without sanction
            		 psfour=con.prepareStatement("select bill_no from FAS_BILL_REGISTERNEW where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and CASHBOOK_YEAR="+txtCash_year+" and bill_no="+billno2);
            		 resfour=psfour.executeQuery();
            		 if(resfour.next())
            		 {
            			// System.out.println("newwww");
            			 pstwo=con.prepareStatement("update FAS_BILL_REGISTERNEW set DOR_BY_PRE_AUDIT=null,PRE_AUDIT_RECEIVED_BY=null,PRE_AUDIT_BY=null,PRE_AUDIT_DATE=null where \n" + 
                    				"ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and \n" + 
                    				"ACCOUNTING_UNIT_OFFICE_ID="+cmbOffice_code+" and BILL_NO="+billno2+" and CASHBOOK_YEAR="+txtCash_year+" and CASHBOOK_MONTH="+txtCash_Month_hid); 
            		 }
            		 else
            		 {
            			// System.out.println("billno2::"+billno2+"txtCash_year:::"+txtCash_year+"txtCash_Month_hid:::"+txtCash_Month_hid);
            			 pstwo=con.prepareStatement("update FAS_BILL_REGISTER_MASTER set DOR_BY_PRE_AUDIT=null,PRE_AUDIT_RECEIVED_BY=null,PRE_AUDIT_BY=null,PRE_AUDIT_DATE=null where \n" + 
                 				"ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and \n" + 
                 				"ACCOUNTING_UNIT_OFFICE_ID="+cmbOffice_code+" and BILL_NO="+billno2+" and CASHBOOK_YEAR="+txtCash_year+" and CASHBOOK_MONTH="+txtCash_Month_hid);
            		 }
            		 uptwo=pstwo.executeUpdate(); */
            	 uptwo=uptwo_New;
            	 
            }
            else if(Bills_list==10)
            {
            	// System.out.println("here");
            	int newMonth=0,newYear=0;
            	String flag_chk="";
            	/*
            	 try{
           		PreparedStatement  psYM=con.prepareStatement("select * from (SELECT distinct m.BILL_NO as bno ,EXTRACT (MONTH FROM T.PVR_DATE) MONTH,extract (year from t.PVR_DATE) year"+
       				" FROM FAS_MEMO_OF_PAYMENT_TRN t, "+
           				  "   fas_memo_of_payment_mst m "+
           				  " WHERE m.ACCOUNTING_UNIT_ID    =t.ACCOUNTING_UNIT_ID "+
           				  " AND m.ACCOUNTING_FOR_OFFICE_ID=t.ACCOUNTING_FOR_OFFICE_ID "+
           				  " AND m.cashbook_year           =t.cashbook_year "+
           				  " AND m.cashbook_month          =t.cashbook_month "+
           				  " AND m.CASHBOOK_MONTH          =t.CASHBOOK_MONTH "+
           				  "   and M.BILL_NO=T.BILL_NO  AND t.PVR_NO                 IS not NULL "+
           				  " AND m.ACCOUNTING_UNIT_ID      = "+cmbAcc_UnitCode+
           				  " AND m.CASHBOOK_YEAR           = "+txtCash_year+
           				  " AND m.CASHBOOK_MONTH          = "+txtCash_Month_hid+
           				  " and m.BILL_NO="+billno2+
           				  " AND m.status                  ='L') f where month=");
           		 	  ResultSet	reYM=psYM.executeQuery();
           		 	    
           		 	    	while(reYM.next())
           		 	    	{
           		 	    	newMonth=reYM.getInt("MONTH");
           		 	    newYear=reYM.getInt("year");
           		 	    	}
            	 }catch(Exception e){
            		 e.printStackTrace();
            	 }*/
            	//System.out.println("newMonth >>> "+newMonth);
            //	System.out.println("newYear >>> "+newYear);
            	int kk=0;
            	
            	 try{
            	PreparedStatement ps_chk=con.prepareStatement("select ACCOUNTING_UNIT_ID as cnt from FAS_TRIAL_BALANCE_STATUS where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+ " and CASHBOOK_YEAR= "+txtCash_year+" and CASHBOOK_MONTH="+txtCash_Month_hid);
            	 ResultSet rs_chk=ps_chk.executeQuery();
            	 if(rs_chk.next()){
            		 flag_chk="Y";
            	 }else{
            		 flag_chk="N";
            	 }
            	 }catch(Exception e){
            		 e.printStackTrace();
            	 }
            	 System.out.println("**************************** 2 part "+flag_chk);
            	 if(flag_chk.equalsIgnoreCase("N")){
//            		 psfour=con.prepareStatement("select m.voucher_no payvno,m.cashbook_year,m.cashbook_month "+
//									" from fas_payment_master m,fas_payment_transaction t "+
//									" where m.ACCOUNTING_UNIT_ID=t.ACCOUNTING_UNIT_ID "+
//									" and m.CASHBOOK_YEAR=t.CASHBOOK_YEAR "+
//									" and m.CASHBOOK_MONTH=t.CASHBOOK_MONTH "+
//									" and m.VOUCHER_NO=t.VOUCHER_NO "+
//									" and m.PAYMENT_STATUS='L' "+
//									" and m.accounting_unit_id= "+cmbAcc_UnitCode+
//									" and m.cashbook_year= "+txtCash_year+
//									" and m.cashbook_month= "+txtCash_Month_hid+
//									" and m.TOTAL_AMOUNT= "+sanamt1[i] +" and MODE_OF_CREATION = 'A' and CREATED_BY_MODULE = 'BPF'"+
//								" and m.PAYMENT_DATE=?");
//									/*" and t.BILL_NO= "+billno2+
//									" and t.BILL_DATE=to_date(?,'dd-mm-yy')");*/
//            		 System.out.println("qry :: select m.voucher_no payvno,m.cashbook_year,m.cashbook_month "+
//									" from fas_payment_master m,fas_payment_transaction t "+
//									" where m.ACCOUNTING_UNIT_ID=t.ACCOUNTING_UNIT_ID "+
//									" and m.CASHBOOK_YEAR=t.CASHBOOK_YEAR "+
//									" and m.CASHBOOK_MONTH=t.CASHBOOK_MONTH "+
//									" and m.VOUCHER_NO=t.VOUCHER_NO "+
//									" and m.PAYMENT_STATUS='L' "+
//									" and m.accounting_unit_id= "+cmbAcc_UnitCode+
//									" and m.cashbook_year= "+txtCash_year+
//									" and m.cashbook_month= "+txtCash_Month_hid+
//									" and m.TOTAL_AMOUNT= "+sanamt1[i]);
            		 
            		 
            		 
            		 psfour=con.prepareStatement("select m.voucher_no as payvno,m.cashbook_year,m.cashbook_month "+
								" from fas_payment_master m,fas_payment_transaction t "+
								" where m.ACCOUNTING_UNIT_ID=t.ACCOUNTING_UNIT_ID "+
								" and m.CASHBOOK_YEAR=t.CASHBOOK_YEAR "+
								" and m.CASHBOOK_MONTH=t.CASHBOOK_MONTH "+
								" and M.VOUCHER_NO  =T.VOUCHER_NO"+
								" and m.PAYMENT_STATUS='L' "+
								" and m.accounting_unit_id= "+cmbAcc_UnitCode+
								" and m.cashbook_year= "+txtCash_year+
								" and m.cashbook_month= "+txtCash_Month_hid+
								" and m.TOTAL_AMOUNT= "+sanamt1[i] +" and MODE_OF_CREATION = 'A' and CREATED_BY_MODULE = 'BPF'"+
							" and m.PAYMENT_DATE=? and t.PAYABLE_VOUCHER_NO="+sanno1[i]);
								/*" and t.BILL_NO= "+billno2+
								" and t.BILL_DATE=to_date(?,'dd-mm-yy')");*/
            		 System.out.println("qry :: select m.voucher_no as payvno,m.cashbook_year,m.cashbook_month "+
								" from fas_payment_master m,fas_payment_transaction t "+
								" where m.ACCOUNTING_UNIT_ID=t.ACCOUNTING_UNIT_ID "+
								" and m.CASHBOOK_YEAR=t.CASHBOOK_YEAR "+
								" and m.CASHBOOK_MONTH=t.CASHBOOK_MONTH "+
								" and M.VOUCHER_NO  =T.VOUCHER_NO"+
								" and m.PAYMENT_STATUS='L' "+
								" and m.accounting_unit_id= "+cmbAcc_UnitCode+
								" and m.cashbook_year= "+txtCash_year+
								" and m.cashbook_month= "+txtCash_Month_hid+
								" and m.TOTAL_AMOUNT= "+sanamt1[i]+"and t.PAYABLE_VOUCHER_NO="+sanno1[i]);
            		 
            		 System.out.println("txtCrea_date::::"+txtCrea_date);
            		 psfour.setDate(1,ch_memodate);
            		 resfour=psfour.executeQuery();
            		 if(resfour.next())
            		 {
            			System.out.println("payvno:::"+resfour.getInt("payvno")); 
            			pstwo=con.prepareStatement("update FAS_PAYMENT_MASTER set PAYMENT_STATUS='C',UPDATED_BY_USER_ID=?,UPDATED_DATE=? where \n" + 
                 				"ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and \n" + 
                 				"ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and VOUCHER_NO="+resfour.getInt("payvno")+" and CASHBOOK_YEAR="+txtCash_year+" and CASHBOOK_MONTH="+txtCash_Month_hid);
            			
            			System.out.println("update FAS_PAYMENT_MASTER set PAYMENT_STATUS='C',UPDATED_BY_USER_ID=?,UPDATED_DATE=? where \n" + 
                 				"ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and \n" + 
                 				"ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and VOUCHER_NO="+resfour.getInt("payvno")+" and CASHBOOK_YEAR="+txtCash_year+" and CASHBOOK_MONTH="+txtCash_Month_hid);
            			
            			
            			pstwo.setString(1,userid);
            			pstwo.setTimestamp(2,ts);
            			uptwo_New=pstwo.executeUpdate();
            			 System.out.println("______________________"+sans_bno2 +"------------------"+ch_memodate);
                		 PreparedStatement pstoo=con.prepareStatement("update FAS_MEMO_OF_PAYMENT_TRN set PVR_NO=null,PVR_DATE=null where \n" + 
                  				" Payment_Unit="+cmbAcc_UnitCode+" and \n" + 
                  				" Payment_Office="+cmbOffice_code+" and PVR_NO="+resfour.getInt("payvno")+" and PVR_DATE=? ");
                		 pstoo.setDate(1,ch_memodate);
                		 System.out.println("update FAS_MEMO_OF_PAYMENT_TRN set PVR_NO=null,PVR_DATE=null where \\n\" + \r\n"
                		 		+ "                  				\" Payment_Unit=\"+cmbAcc_UnitCode+\" and \\n\" + \r\n"
                		 		+ "                  				\" Payment_Office=\"+cmbOffice_code+\" and PVR_NO=\"+resfour.getInt(\"payvno\")+\" and PVR_DATE=? ");
             			 kk=pstoo.executeUpdate();
            			
            			
            		 }
            		
         			System.out.println("uptwo_New  ....  "+uptwo_New);
         			System.out.println("kk  ....  "+kk);
         			if(uptwo_New >= 1 && kk >= 1)
         			{
         				uptwo=1;
         			}
            	 }	else{
            		 System.out.println("TEST");
            		    sendMessage(response," TRIAL BALANCE FREEZED ","ok");  
            	 }
            } 
            }
            }
            }
            System.out.println("uptwo"+uptwo);
            if(uptwo>0)
        	{
                sendMessage(response,updationvalue+" has been cancelled Successfully ","ok");   
            con.commit();
        	}
        	else
        	{
        		System.out.println("redirect");                                
                sendMessage(response,"Error in Cancellation","ok"); 
                con.rollback();
        	}
			
		}
	catch(Exception e){System.out.println(e);e.printStackTrace();
		try {
			con.rollback();
		}
		catch (SQLException e1) {
			e1.printStackTrace();
		}
		}
		/*try{
			con.commit();
		}catch(Exception e){System.out.println(e);}*/
		
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
