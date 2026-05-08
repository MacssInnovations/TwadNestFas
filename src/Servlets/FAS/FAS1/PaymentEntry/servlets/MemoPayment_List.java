package Servlets.FAS.FAS1.PaymentEntry.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class MemoPayment_List
 */
public class MemoPayment_List extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
    Connection connection = null; 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemoPayment_List() {
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
	         ResultSet rs=null;
	         int  txtCB_Year=0, txtCB_Month=0,cmbAcc_UnitCode=0,cmbOffice_code=0;
	         PreparedStatement ps=null;
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

  	 try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
       catch(NumberFormatException e){System.out.println("exception"+e );}
       
       
       try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
       catch(NumberFormatException e){System.out.println("exception"+e );}
       
       txtCB_Year=Integer.parseInt(request.getParameter("txtCB_Year"));
       txtCB_Month=Integer.parseInt(request.getParameter("txtCB_Month"));  
       String optionId=request.getParameter("optionId");
       String sub_q="",sub_main="";
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
       String optioiiid="";
       
      if(optionId.equalsIgnoreCase("live")){
    	  optioiiid=" and m.STATUS='L'";
      }else if(optionId.equalsIgnoreCase("cancel")){
    	  optioiiid=" and m.STATUS='C'";
      }
	            xml="<response><command>searchByMonth</command>";                        
	       
	            String sql="  select  m.bill_no,"+
				" to_char(m.BILL_DATE,'dd/mm/yyyy')BILL_DATE,"+
				//" sanction_proceeding_no,(select SANCTION_PROC_NO from HRM_SANCTIONS_BILLS_LINK_MST where HRMS_SANCTION_ID=m.sanction_proceeding_no)as SanProcNo,"+
				" m.sanction_proceeding_no,"+
                "  bm.bill_major_type,bm.bill_minor_type_code,bm.bill_sub_type_code,"
				//	+ "  case when INSTR(M.SANCTION_PROCEEDING_NO,'/')=0  and bill_major_type=2 and bm.bill_minor_type_code=2 and bm.bill_sub_type_code=1"
				+ "  case when bill_type <> 'WOSP'  and bill_major_type=2 and bm.bill_minor_type_code=2 and bm.bill_sub_type_code=1"
				
					+ "  then "
					+ "  (SELECT SANCTION_PROC_NO "
					+ "  FROM SLS_SANCTIONS_BILLS_LINK_MST1 "
					+ "  WHERE HRMS_SANCTION_ID=m.sanction_proceeding_no::NUMERIC  "
					+ "  ) "
				//	+ "     WHEN INSTR(M.SANCTION_PROCEEDING_NO,'/')<>0  THEN M.SANCTION_PROCEEDING_NO "
				+ "     WHEN bill_type = 'WOSP'   THEN M.SANCTION_PROCEEDING_NO "
					+ " else   "
					+ " (SELECT SANCTION_PROC_NO "
					+ " FROM HRM_SANCTIONS_BILLS_LINK_MST "
					+ " WHERE HRMS_SANCTION_ID=m.sanction_proceeding_no::numeric "
					+ " ) end AS SanProcNo,"
					+
				" to_char(m.sanction_proceeding_date,'dd/mm/yyyy')sanction_proceeding_date,"+
				" m.dr_account_head_code,"+
				" m.sub_ledger_type_code,(select sub_ledger_type_desc from com_mst_sl_types a where a.sub_ledger_type_code=m.sub_ledger_type_code)as typedesc,"+
				" m.sub_ledger_code,"+
				" (select v.sl_codename from SL_TYPE_CODE_NAME_VIEW v where SL_TYPE=m.sub_ledger_type_code and SL_CODE=m.sub_ledger_code)as paydesc," +
				" m.amount,"+
				" m.remarks,"+
				" m.sanctioned_amount,"+
				" m.pvr_no,"+
				" to_char(m.pvr_date,'dd/mm/yyyy')pvr_date"+
				" from FAS_MEMO_OF_PAYMENT_MST m,"+sub_q+" bm "+
					"WHERE m.accounting_for_office_id=bm.accounting_unit_office_id"
					+ " and m.accounting_unit_id = bm.accounting_unit_id"
					+ " and m.bill_no = bm.bill_no"
			/*		+ " and m.cashbook_month=bm.cashbook_month"
					+ " and m.cashbook_year=bm.cashbook_year and"*/
					+ " 	AND M.BILL_DATE=BM.BILL_DATE "
				+ " 	and M.SANCTION_PROCEEDING_NO = BM.SANCTION_PROC_NO "
				+ " and m.ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and m.CASHBOOK_YEAR="+txtCB_Year+" and m.CASHBOOK_MONTH="+txtCB_Month+optioiiid +" order by  m.bill_no ";
	            
				          System.out.println("SQL::::"+sql);
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
						                    xml=xml+"<sanction_proceeding_no>"+rs.getString("SanProcNo")+"</sanction_proceeding_no>";	                
						                    xml=xml+"<sanction_proceeding_date>"+rs.getString("sanction_proceeding_date") +"</sanction_proceeding_date>";						                    
						                    xml=xml+"<dr_account_head_code>"+rs.getString("dr_account_head_code") +"</dr_account_head_code>";
						                    xml=xml+"<sub_ledger_type_code>"+rs.getString("typedesc") +"</sub_ledger_type_code>";
						                    xml=xml+"<sub_ledger_code>"+rs.getString("sub_ledger_code") +"</sub_ledger_code>";
						                    xml=xml+"<paydesc>"+rs.getString("paydesc") +"</paydesc>";						                    
						                    xml=xml+"<amount>"+rs.getString("amount") +"</amount>";
						                    xml=xml+"<sanctioned_amount>"+rs.getString("sanctioned_amount") +"</sanctioned_amount>";
						                    xml=xml+"<pvr_no>"+rs.getString("pvr_no") +"</pvr_no>";
						                    xml=xml+"<pvr_date>"+rs.getString("pvr_date") +"</pvr_date>";
						                    
						                    xml=xml+"<remarks><![CDATA["+rs.getString("remarks")+"]]></remarks>";
						                    
						                    xml=xml+"</leng>";
						                    count++;
					                }
					                if(count>0) 
					                {
						                   // System.out.println("inside count==0");
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
				            }
          
         
			 
	        }
	        
	        xml=xml+"</response>";   
	        out.println(xml); 
	        System.out.println(xml); 
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
