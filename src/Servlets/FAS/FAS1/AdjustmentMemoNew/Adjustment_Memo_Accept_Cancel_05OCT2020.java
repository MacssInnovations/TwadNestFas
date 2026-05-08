package Servlets.FAS.FAS1.AdjustmentMemoNew;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ResourceBundle;

import javax.servlet.http.HttpSession;

import Servlets.FAS.FAS1.CommonClass.ConvertDate;

/**
 * Servlet implementation class Adjustment_Memo_Accept
 */
public class Adjustment_Memo_Accept_Cancel_05OCT2020 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	PreparedStatement ps1 = null;
	ResultSet rs1,result = null;
	PreparedStatement ps2 = null;
	
    public Adjustment_Memo_Accept_Cancel_05OCT2020() {
        super();
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		   HttpSession session=request.getSession(true);
		    String cmd=request.getParameter("command");
		    ConvertDate cc=new ConvertDate();
		    
		    System.out.println("The Command is================================================================================================>"+cmd);
		  
		    ResultSet rs2 = null;
			
		   
	        String xml="";
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
		
		 if(cmd.equalsIgnoreCase("loadmomono"))
		{
			 
			xml="<response><command>loadmomono</command>";
			String cmbAcc_UnitCode=request.getParameter("cmbAcc_UnitCode");
			int unitcode=cc.ConvertInt(cmbAcc_UnitCode);
			//System.out.println("*********************************************************"+unitcode);
			String cmbOffice_code=request.getParameter("cmbOffice_code");
			int officecode=cc.ConvertInt(cmbOffice_code);
			//System.out.println("*******************************************************************"+officecode);
			String txtDate[]=request.getParameter("txtDate").split("/");
			int month=cc.ConvertInt(txtDate[1]);
			int year=cc.ConvertInt(txtDate[2]);
			
			
			try
			{
				
//				String qus="SELECT t.VOUCHER_NO,t.sl_no "+
//					"	FROM FAS_ADJUST_MEMO_MST m,FAS_ADJUST_MEMO_TRN t "+
//						" WHERE m.accounting_unit_id=t.accounting_unit_id "+
//					" and m.accounting_for_office_id=t.accounting_for_office_id "+
//					" and m.CASHBOOK_YEAR=t.CASHBOOK_YEAR "+
//					" and m.CASHBOOK_MONTH=t.CASHBOOK_MONTH "+
//					" and m.voucher_no=t.voucher_no "+
//					" and t.CASHBOOK_YEAR       ="+year+
//					" AND t.ACCEPT_VOUCHER_NO    IS NOT NULL "+
//					" AND t.ACCEPT_VOUCHER_DATE  IS NOT NULL "+
//					" AND t.CASHBOOK_MONTH        ="+month+
//					" AND t.FOR_ACCOUNTING_UNIT_ID= "+unitcode+
//					" and m.memo_status='L' "+
//					" AND t.ACCEPTANCE_STATUS     ='Y' and t.ACCEPT_VERIFY_STATUS is null";
				
				String qus="SELECT t.VOUCHER_NO,\n" + 
						"  t.sl_no\n" + 
						"FROM FAS_ADJUST_MEMO_MST M,\n" + 
						"  FAS_ADJUST_MEMO_TRN T,\n" + 
						"  FAS_CROSS_REFERENCE C\n" + 
						"WHERE m.accounting_unit_id    =t.accounting_unit_id\n" + 
						"AND m.accounting_for_office_id=t.accounting_for_office_id\n" + 
						"AND m.CASHBOOK_YEAR           =t.CASHBOOK_YEAR\n" + 
						"AND m.CASHBOOK_MONTH          =t.CASHBOOK_MONTH\n" + 
						"AND M.VOUCHER_NO              =T.VOUCHER_NO\n" + 
						"AND T.FOR_ACCOUNTING_UNIT_ID  =C.ACCOUNTING_UNIT_ID\n" + 
						"AND M.CASHBOOK_YEAR           =C.CASHBOOK_YEAR\n" + 
						"AND T.CASHBOOK_MONTH          =C.CASHBOOK_MONTH\n" + 
						"and T.VOUCHER_NO              =C.VOUCHER_NO "+
						"and t.CASHBOOK_YEAR       ="+year+
						"AND t.ACCEPT_VOUCHER_NO    IS NOT NULL "+
						"AND t.ACCEPT_VOUCHER_DATE  IS NOT NULL "+
						"AND t.CASHBOOK_MONTH        ="+month+
						"AND t.FOR_ACCOUNTING_UNIT_ID= "+unitcode+
						"and m.memo_status='L' "+
						"AND t.ACCEPTANCE_STATUS     ='Y' and t.ACCEPT_VERIFY_STATUS is null " +
						"AND C.CHANGE_NO               =0\n" + 
						"AND C.AUTHORIZED_TO           ='C'\n" + 
						"AND DOC_TYPE                  ='ADM_A' ";				
				PreparedStatement ps22=con.prepareStatement(qus);
				
				rs=ps22.executeQuery();				
				 
				while(rs.next())
				{
					xml=xml+"<flag>success</flag>";
					xml=xml+"<memono>"+rs.getInt("VOUCHER_NO")+"</memono>";
					xml=xml+"<slno>"+rs.getInt("sl_no")+"</slno>";
				}			
				
				System.out.println("qus....>"+qus);
			}
			catch(Exception e)
			{
				xml = xml + "<flag>failure</flag>";
				System.out.println(e);
			}
				
			xml = xml + "</response>";
			System.out.println(xml);		
			
		}
		 
		 else if(cmd.equalsIgnoreCase("details"))
			{
				  
				
				System.out.println("enter into details******************************************************************************************@@@@@@@@@@");
				xml="<response><command>memodetails</command>";
				
				String cmbAcc_UnitCode=request.getParameter("cmbAcc_UnitCode");
				int accno=cc.ConvertInt(cmbAcc_UnitCode);
				
				String cmbOffice_code=request.getParameter("cmbOffice_code");
				int officecode=cc.ConvertInt(cmbOffice_code);
				
				String txtDate[]=request.getParameter("txtDate").split("/");
				int month=cc.ConvertInt(txtDate[1]);
				int year=cc.ConvertInt(txtDate[2]);
				
				String cmbAdviceNO=request.getParameter("cmbAdviceNO");
				int no=cc.ConvertInt(cmbAdviceNO);
				int serialno=cc.ConvertInt(request.getParameter("slno"));
				try
				{      
					
					String q1="SELECT trim(to_char(t.AMOUNT,'99999999999999.99')) as AMOUNT, "+
							
							"	t.LETTER_NO,to_char(t.LETTER_DATE,'DD/MM/YYYY') as LETTER_DATE,t.ACCOUNT_HEAD_CODE," +
							" (select h.account_head_desc from com_mst_account_heads h where h.account_head_code=t.ACCOUNT_HEAD_CODE)as headdesc, "+
							"   t.CR_DR_TYPE, "+
								" t.SUB_LEDGER_TYPE_CODE, "+
							"(select s.sub_ledger_type_desc from com_mst_sl_types s where s.sub_ledger_type_code=t.SUB_LEDGER_TYPE_CODE)as type_desc, "+
							" t.SUB_LEDGER_CODE, "+
							" (select v.sl_codename from sl_type_code_name_view v where v.sl_type=t.SUB_LEDGER_TYPE_CODE and v.sl_code=t.sub_ledger_code) as code_desc, "+
							" t.REMARKS,m.AUTHORITY_NAME,m.AUTHORITY_ADDRESS "+
							" FROM FAS_ADJUST_MEMO_MST m,FAS_ADJUST_MEMO_TRN t "+
							" WHERE m.accounting_unit_id=t.accounting_unit_id "+
							" and m.accounting_for_office_id=t.accounting_for_office_id "+
							" and m.CASHBOOK_YEAR=t.CASHBOOK_YEAR "+
							" and m.CASHBOOK_MONTH=t.CASHBOOK_MONTH "+
							" and m.voucher_no=t.voucher_no "+
							" and t.CASHBOOK_YEAR       = "+year+
							" AND t.ACCEPT_VOUCHER_NO    IS NOT NULL "+
							" AND t.ACCEPT_VOUCHER_DATE  IS NOT NULL "+
							" AND t.CASHBOOK_MONTH        = "+month+
							" AND t.FOR_ACCOUNTING_UNIT_ID= "+accno+
							" and m.memo_status='L' "+
							" AND t.ACCEPTANCE_STATUS     ='Y' "+
							" and t.VOUCHER_NO="+no+" and t.sl_no="+serialno;
					System.out.println(q1);
					ps=con.prepareStatement(q1);
					result=ps.executeQuery();
					
					if(result.next())
					{ 
						xml=xml+"<flag>success</flag>";
						xml=xml+"<authorityname>"+result.getString("AUTHORITY_NAME")+"</authorityname>";
						xml=xml+"<authorityaddress>"+result.getString("AUTHORITY_ADDRESS")+"</authorityaddress>";
						xml=xml+"<lNo>"+result.getInt("LETTER_NO")+"</lNo>";
						xml=xml+"<ldate>"+result.getString("LETTER_DATE")+"</ldate>";
						
						xml=xml+"<remarks>"+result.getString("REMARKS")+"</remarks>";
						xml=xml+"<AMOUNT>"+result.getString("AMOUNT")+"</AMOUNT>";
						xml=xml+"<accCode>"+result.getInt("ACCOUNT_HEAD_CODE")+"</accCode>";
						xml=xml+"<headdesc>"+result.getString("headdesc")+"</headdesc>";
						
						xml=xml+"<crdr>"+result.getString("CR_DR_TYPE")+"</crdr>";
						
						xml=xml+"<type_code>"+result.getInt("SUB_LEDGER_TYPE_CODE")+"</type_code>";
						xml=xml+"<type_desc>"+result.getString("type_desc")+"</type_desc>";
						
						xml=xml+"<code>"+result.getInt("SUB_LEDGER_CODE")+"</code>";
						xml=xml+"<code_desc>"+result.getString("code_desc")+"</code_desc>";
					}
					
					
					else
					{
						xml=xml+"<flag>nodata</flag>";
					}
					
					
					
				}
				catch(Exception e)
				{
					xml = xml + "<flag>failure</flag>";
					System.out.println(e);
				}
				xml = xml + "</response>";
				System.out.println(xml);	
			} 
		
		out.write(xml);		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		PrintWriter out = response.getWriter();
	    HttpSession session=request.getSession(true);
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
			    String CONTENT_TYPE = "text/html; charset=windows-1252";
	            response.setContentType(CONTENT_TYPE);
	           
	            int cmbAcc_UnitCode1=0,cmbOffice_code=0,txtCash_Month_hid=0,txtCash_year=0,adviceno=0;            
	            String cr_dr_indicator="",flag="",particulars="";
	            String txtCrea_date=null;
	            int sub_ledger_code=0,depriciation_rate=0,serialno=0;
	          
	            String remaks=""; 
	            int sub_ledger_type=0,upAdj=0;
	            double amount=0;
	            String Journal_type="";
	                                    // changes here
	            String update_user=(String)session.getAttribute("UserId");
	            long l=System.currentTimeMillis();
	            Timestamp ts=new Timestamp(l);
	            CallableStatement cs1=null;
	            int errcode=0;
	            Boolean check=true;
	            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	                               
	            try{cmbAcc_UnitCode1=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
	            catch(NumberFormatException e){System.out.println("exception"+e );}
	          //  System.out.println("cmbAcc_UnitCode........ "+cmbAcc_UnitCode);
	            
	            try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
	            catch(NumberFormatException e){System.out.println("exception"+e );}
	         //   System.out.println("cmbOffice_code...... "+cmbOffice_code);
	            // String date=request.getParameter("txtDate");             
	            String[] sd=request.getParameter("txtDate").split("/");
	         
	             
	            txtCrea_date=request.getParameter("txtDate");
	           
	            
	           
	            try{txtCash_year=Integer.parseInt(sd[2]);}
	            catch(Exception e){System.out.println("exception"+e );}
	            //System.out.println("txtCash_year "+txtCash_year);
	            
	            try{txtCash_Month_hid=Integer.parseInt(sd[1]);}
	            catch(Exception e){System.out.println("exception"+e );}
	          //  System.out.println("txtCash_Month_hid "+txtCash_Month_hid);
	            
	           String advicenospl=request.getParameter("cmbAdviceNO");
	           String[] splvv=advicenospl.split("-");
	            adviceno=Integer.parseInt(splvv[0]);
	            serialno=Integer.parseInt(splvv[1]);
	            
	          
	            remaks=request.getParameter("txtRemarks1");
	            
	                     
	           Double txtAmount=Double.parseDouble(request.getParameter("txtAmount"));
              
               String[] H_code=request.getParameterValues("H_code");
  	         //  System.out.println("H_code"+H_code[0]);	
               int accCode=0,firSelect=0;
	           int trnRecords=H_code.length;
	          // System.out.println("trnRecords"+trnRecords);
	           int accept_VouNo=0,acceptCash_year=0,acceptCash_Month=0;
	           
	           
	           try{Journal_type="ADM_A";}
	             catch(Exception e){System.out.println("Journal_type "+e );}
	         
	           
	           try{
	        	   
	        	   String squ="SELECT ACCEPT_VOUCHER_NO,to_char(ACCEPT_VOUCHER_DATE,'dd/mm/yyyy')as ACCEPT_VOUCHER_DATE,ACCEPTANCE_STATUS  "+
								" FROM FAS_ADJUST_MEMO_TRN WHERE FOR_ACCOUNTING_UNIT_ID= "+cmbAcc_UnitCode1+
	        			   " AND CASHBOOK_YEAR           ="+txtCash_year+" AND CASHBOOK_MONTH          ="+txtCash_Month_hid+" AND VOUCHER_NO              = "+adviceno+
	        			   " 	AND SL_NO                   ="+serialno+" AND AMOUNT                  ="+txtAmount+" and ACCEPTANCE_STATUS='Y' and (ACCEPT_VOUCHER_NO is not null or ACCEPT_VOUCHER_NO!=0)";
	        	 //  System.out.println(squ);
	        	   PreparedStatement prep=con.prepareStatement(squ);
	        	   ResultSet res=prep.executeQuery();
	        	   if(res.next())
	        	   {
	        		   firSelect++;
	        		   accept_VouNo=res.getInt("ACCEPT_VOUCHER_NO");
	        		 //  System.out.println("accept_VouNo:"+accept_VouNo);
	        		   
	        		 String accept_VouDate=res.getString("ACCEPT_VOUCHER_DATE");
	        		// System.out.println(accept_VouDate);
	        		String[] accvDate= accept_VouDate.split("/");
	        		   try{acceptCash_year=Integer.parseInt(accvDate[2]);}
	   	            catch(Exception e){System.out.println("exception"+e );}
	   	        // System.out.println("acceptCash_year:"+acceptCash_year);
	   	            
	   	            try{acceptCash_Month=Integer.parseInt(accvDate[1]);}
	   	            catch(Exception e){System.out.println("exception"+e );}
		   	         System.out.println("acceptCash_Month:"+acceptCash_Month);
	        	   }
	        	   
	           }
	           catch(Exception ee)
	           {
	        	   System.out.println("exception in getting accept vNo:"+ee);
	           }
                if(firSelect>0)   
                { 
		           try
		           {
		        	 
		        	   con.setAutoCommit(false);
	                    ps=con.prepareStatement("update FAS_ADJUST_MEMO_TRN set ACCEPTANCE_STATUS=null,ACCEPT_VOUCHER_NO=0,ACCEPT_VOUCHER_DATE=null where FOR_ACCOUNTING_UNIT_ID=? "+
	                    			" AND CASHBOOK_YEAR           =? AND CASHBOOK_MONTH          =? AND VOUCHER_NO              =? and SL_NO=? and AMOUNT=? and ACCEPTANCE_STATUS='Y' and ACCEPT_VOUCHER_NO is not null");
	                    ps.setInt(1,cmbAcc_UnitCode1);
	                    ps.setInt(2,txtCash_year);
	                    ps.setInt(3,txtCash_Month_hid);
	                    ps.setInt(4, adviceno);
	                    ps.setInt(5, serialno);
	                    ps.setDouble(6,txtAmount);
	                   
	                    upAdj=ps.executeUpdate();
	                    if(upAdj>0) 
	                    {
	                    		   
	                             ps1=con.prepareStatement("update FAS_JOURNAL_MASTER set JOURNAL_STATUS=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=? and JOURNAL_TYPE_CODE=70 and JOURNAL_STATUS='L'");
	         	                 ps1.setString(1, "C");
	         	                 ps1.setInt(2,cmbAcc_UnitCode1);
	         	                 ps1.setInt(3,cmbOffice_code);   
	         	                 ps1.setInt(4,acceptCash_year);
	    	                     ps1.setInt(5,acceptCash_Month);
	    	                     ps1.setInt(6, accept_VouNo);
	    	                     
	    	                    int ss=ps1.executeUpdate();
	    	                    if(ss>0)
	    	                    {
	    	                    	String txtReferNO_edit="",txtRemak_edit="";         // for cross reference
					                Date txtReferDate_edit=null; 
					                String radAuth_MC="";
					                int txtAuth_By=0;						    						                
					                cs1=con.prepareCall("call FAS_CROSS_REFERENCE_PROC(?::NUMERIC,?,?,?,?,?,?,?,?,?,?::VARCHAR,?,?,?,?,?)") ; 
					                cs1.setInt(1,cmbAcc_UnitCode1);
					                cs1.setInt(2,txtCash_year);
					                cs1.setInt(3,txtCash_Month_hid);
					                cs1.setInt(4,adviceno);
					                cs1.setInt(5,cmbOffice_code);
					                cs1.setString(6,txtCrea_date);
					                cs1.setString(7,Journal_type);
					                cs1.setString(8,txtReferNO_edit);
					                cs1.setDate(9,txtReferDate_edit);
					                cs1.setString(10,txtRemak_edit);
					                cs1.setInt(11,txtAuth_By);                                                      
					                cs1.setString(12,"insert");
					                cs1.registerOutParameter(13,java.sql.Types.NUMERIC);
					                cs1.setNull(13,java.sql.Types.NUMERIC);
					                cs1.setString(14,update_user);
					                cs1.setTimestamp(15,ts);     
					                cs1.setString(16,radAuth_MC);
					                cs1.execute();                                            // insertion into cross reference table
					                errcode=cs1.getInt(13);						    						               
					                System.out.println("SQLCODE:::"+errcode);
					                if(errcode!=0)
					                {   
					                		check=false;                      
					                }
					                else 
					                {
					                		check=true;
					                }
	    	                    	if(check)
	    	                    	{	    	                    	
	    	                    	con.commit();
	    	                    	sendMessage(response,"The Memo Advice Number  has been Cancelled Successfully ","ok");
	    	                    	}
	    	                    }
	    	                    else
	    	                    {
	    	                    	 con.rollback();
	    	                    	 sendMessage(response,"Failed To Cancel","ok");
	    	                    }
	                    }
	                    else
	                    {
	                    	 con.rollback();
	                    	sendMessage(response,"Failed To Cancel","ok");
	                    }
	                    
		           }           
		           catch(Exception e){
		        	   try {
						con.rollback();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		        	   sendMessage(response,"The Memo Advice Number Creation  has been Failed ","ok");    }
		           System.out.println("org_VouNo "+accept_VouNo);            
                }
                else
                {
                	System.out.println("Error in Choosing acc voucher no");
                	sendMessage(response,"The Memo Advice Number Cancellation  has been Failed ","ok"); 
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

