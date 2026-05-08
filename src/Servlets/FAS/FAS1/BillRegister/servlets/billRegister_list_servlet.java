package Servlets.FAS.FAS1.BillRegister.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class billRegister_list_servlet
 */
public class billRegister_list_servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public billRegister_list_servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 
	
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
	         ResultSet rs=null,rs2=null;
	         int  txtCB_Year=0, txtCB_Month=0,cmbAcc_UnitCode=0,cmbOffice_code=0;
	         PreparedStatement ps=null,ps2=null;
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
	         
	        System.out.println("servlet called in bill register list ");
	        String CONTENT_TYPE = "text/xml; charset=windows-1252";
	        response.setContentType(CONTENT_TYPE);
	        PrintWriter out = response.getWriter();
	        String strType = "",cmd="",xml="<response>";
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
             Date fromDte=null,toDate=null;
             Calendar ca;
             String sub="";
             try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
             catch(NumberFormatException e){System.out.println("exception"+e );}
             String ShwType=request.getParameter("hid");
             txtCB_Year=Integer.parseInt(request.getParameter("txtCB_Year"));
             txtCB_Month=Integer.parseInt(request.getParameter("txtCB_Month"));  
             String sancWith=request.getParameter("sancidwith");
             
             String optionId=request.getParameter("optionId");
             String optioiiid="",SUB_QRY="";
             
            if(optionId.equalsIgnoreCase("live")){
          	  optioiiid=" and STATUS='L'";
            }else if(optionId.equalsIgnoreCase("cancel")){
          	  optioiiid=" and STATUS='C'";
            }
            if(ShwType.equalsIgnoreCase("OF")){
            	  String[] sd=request.getParameter("fromDte").split("/");
                  ca=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
                  java.util.Date d=ca.getTime();
                  fromDte=new Date(d.getTime());
                  System.out.println("from_date "+fromDte);
                  String[] sd1=request.getParameter("toDte").split("/");
                  ca=new GregorianCalendar(Integer.parseInt(sd1[2]),Integer.parseInt(sd1[1])-1,Integer.parseInt(sd1[0]));
                  java.util.Date d1=ca.getTime();
                  toDate=new Date(d1.getTime());
                  System.out.println("toDate "+toDate);
                   cmd=request.getParameter("cmd");
            	SUB_QRY=" ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and";
            	if(cmd.equalsIgnoreCase("searchByMonth")){
            	sub=	 " AND m.CASHBOOK_YEAR            = " +txtCB_Year+" "+
                        	 " AND m.CASHBOOK_MONTH           = " +txtCB_Month+" ";
            	}else{
            		sub=	 " AND m.bill_date between ? and  ?  ";
            	}
            }else if(ShwType.equalsIgnoreCase("HO")){
            	SUB_QRY="";
            }
             xml="<response><command>searchByMonth</command><hid>"+ShwType+"</hid>";                        
             String  sql="";
             if(sancWith.equalsIgnoreCase("Y"))
            
             {
            	  if(ShwType.equalsIgnoreCase("OF")){
            		  
            		  
            		  
                  	SUB_QRY=" and m.ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode;}
            	  else{SUB_QRY="";}
            	  if(optionId.equalsIgnoreCase("live")){
                  	  optioiiid=" and m.STATUS='L'";
                    }else if(optionId.equalsIgnoreCase("cancel")){
                  	  optioiiid=" and m.STATUS='C'";
                    }
                  	
	              /* sql="SELECT m.ACCOUNTING_UNIT_ID,m.ACCOUNTING_UNIT_OFFICE_ID,(select u.accounting_unit_name from fas_mst_acct_units u where u.accounting_unit_id=m.accounting_unit_id) as unit_name,m.BILL_MINOR_TYPE_CODE,m.BILL_MAJOR_TYPE,m.BILL_SUB_TYPE_CODE, "+
						 " (SELECT BILL_SUB_TYPE_DESC "+
				          "  FROM FAS_BILL_SUB_TYPES o "+
				          " WHERE BILL_MAJOR_TYPE_CODE=m.BILL_MAJOR_TYPE "+
				          " AND BILL_MINOR_TYPE_CODE  =m.BILL_MINOR_TYPE_CODE "+
				          " AND m.BILL_SUB_TYPE_CODE  =o.bill_sub_type_code "+
				          " AND status                ='L' "+
				          " )AS subdesc, BILL_NO,SANCTION_PROC_NO,(select SANCTION_PROC_NO from HRM_SANCTIONS_BILLS_LINK_MST where HRMS_SANCTION_ID=m.SANCTION_PROC_NO)as SanProcNo,PAYEE_CODE,to_char(BILL_DATE,'dd/mm/yyyy')BILL_DATE,BILL_PROCESSING_DONE_BY,(select v.sl_codename " +
				        		"    from SL_TYPE_CODE_NAME_VIEW v where SL_TYPE=7 and SL_CODE=BILL_PROCESSING_DONE_BY)as proccessingdesc, "+
				        			" to_char(PROCEEDING_RECD_DATE,'dd/mm/yyyy')as PROCEEDING_RECD_DATE,PAYEE_TYPE_CODE,decode((select v.sl_codename from SL_TYPE_CODE_NAME_VIEW v where SL_TYPE=PAYEE_TYPE_CODE and SL_CODE=PAYEE_CODE),null,'-',(select v.sl_codename from SL_TYPE_CODE_NAME_VIEW v where SL_TYPE=PAYEE_TYPE_CODE and SL_CODE=PAYEE_CODE))as paydesc," +
				        			"TOTAL_SANCTIONED_AMOUNT,TOTAL_BILL_AMOUNT,REMARKS from FAS_BILL_REGISTER_MASTER m "+
				        			" where "+SUB_QRY+"   CASHBOOK_YEAR="+txtCB_Year+" and CASHBOOK_MONTH="+txtCB_Month+optioiiid+" order by m.accounting_unit_id,m.bill_no";//" and STATUS='L'";
				            */
            	 
            	 sql="SELECT " +
            	 " distinct m.BILL_NO, " +
            	 		"m.ACCOUNTING_UNIT_ID, " +
            	 "  m.ACCOUNTING_UNIT_OFFICE_ID, " +
            	 "  (SELECT u.accounting_unit_name " +
            	 "  FROM fas_mst_acct_units u " +
            	 "  WHERE u.accounting_unit_id=m.accounting_unit_id " +
            	 "  ) AS unit_name, " +
            	 "  m.BILL_MINOR_TYPE_CODE, " +
            	 "  m.BILL_MAJOR_TYPE, " +
            	 "  m.BILL_SUB_TYPE_CODE, " +
            	 "  (SELECT o.BILL_SUB_TYPE_DESC " +
            	 "  FROM FAS_BILL_SUB_TYPES o " +
            	 "  WHERE o.BILL_MAJOR_TYPE_CODE=m.BILL_MAJOR_TYPE " +
            	 "  AND o.BILL_MINOR_TYPE_CODE  =m.BILL_MINOR_TYPE_CODE " +
            	 "  AND o.BILL_SUB_TYPE_CODE    =m.bill_sub_type_code " +
            	 "  AND status                  ='L' " +
            	 "  )AS subdesc, " +
            	
            	 "  m.SANCTION_PROC_NO, " +
            	 "  case  when m.bill_type <> 'WOSP' then (SELECT SANCTION_PROC_NO " +
            	 "  FROM HRM_SANCTIONS_BILLS_LINK_MST " +
            	 "  WHERE (HRMS_SANCTION_ID)::varchar=m.SANCTION_PROC_NO " +
            	 "  ) else m.SANCTION_PROC_NO end AS SanProcNo, " +
            	 "  m.PAYEE_CODE as mas_payCode, " +
            	// "  t.payable_to as PAYEE_CODE , " +
            	 "  TO_CHAR(m.BILL_DATE,'dd/mm/yyyy')BILL_DATE, " +
            	 "  m.BILL_PROCESSING_DONE_BY, " +
            	 "  (SELECT v.sl_codename " +
            	 "  FROM SL_TYPE_CODE_NAME_VIEW v " +
            	 "  WHERE SL_TYPE=7 " +
            	 "  AND SL_CODE  =m.BILL_PROCESSING_DONE_BY " +
            	 "  )                                           AS proccessingdesc, " +
            	 "  TO_CHAR(m.PROCEEDING_RECD_DATE,'dd/mm/yyyy')AS PROCEEDING_RECD_DATE, " +
            	// "  t.PAYEE_TYPE_CODE, " +
            	/* "  DECODE( " +
            	 "  (SELECT v.sl_codename FROM SL_TYPE_CODE_NAME_VIEW v WHERE SL_TYPE=t.PAYEE_TYPE_CODE " +
            	 "  AND SL_CODE                                                      =t.payable_to " +
            	 "  ),NULL,'-', " +
            	 "  (SELECT v.sl_codename " +
            	 "  FROM SL_TYPE_CODE_NAME_VIEW v " +
            	 "  WHERE v.SL_TYPE=t.PAYEE_TYPE_CODE " +
            	 "  AND v.SL_CODE  =t.payable_to " +
            	 "  ))AS paydesc, " +*/
            	 "  m.TOTAL_SANCTIONED_AMOUNT," +
            	 //"t.AMOUNT, " +
            	 "  m.TOTAL_BILL_AMOUNT, " +
            	// "  m.REMARKS,"
            	  "  CASE WHEN INSTR(M.REMARKS, '0', -1)=LENGTH(M.REMARKS) THEN   substr(M.REMARKS,0,INSTR(m.REMARKS, '0', -1)-2) else M.REMARKS end as REMARKS ,"
            	 + "m.BILL_TYPE " +
            	 "FROM FAS_BILL_REGISTER_MASTER m, " +
            	 "  fas_bill_register_transaction t " +
            	 "WHERE m.accounting_unit_id     =t.accounting_unit_id " +
            	 "AND m.accounting_unit_office_id=t.accounting_unit_office_id " +
            	 "AND m.cashbook_year            =t.cashbook_year and m.bill_no=t.bill_no " +
            	 "AND m.cashbook_month           =t.cashbook_month " +
            	 SUB_QRY+
            	 " and m.bill_date < '01-Apr-15' and ( BILL_TYPE='WSP'  or  BILL_TYPE is null ) "+
            /*	 " AND m.CASHBOOK_YEAR            = " +txtCB_Year+" "+
            	 " AND m.CASHBOOK_MONTH           = " +txtCB_Month+" "+*/
            sub+
            	optioiiid+
            	" union all "+
            	" SELECT " +
           	 " distinct m.BILL_NO, " +
           	 		"m.ACCOUNTING_UNIT_ID, " +
           	 "  m.ACCOUNTING_UNIT_OFFICE_ID, " +
           	 "  (SELECT u.accounting_unit_name " +
           	 "  FROM fas_mst_acct_units u " +
           	 "  WHERE u.accounting_unit_id=m.accounting_unit_id " +
           	 "  ) AS unit_name, " +
           	 "  m.BILL_MINOR_TYPE_CODE, " +
           	 "  m.BILL_MAJOR_TYPE, " +
           	 "  m.BILL_SUB_TYPE_CODE, " +
           	 "  (SELECT o.BILL_SUB_TYPE_DESC " +
           	 "  FROM FAS_BILL_SUB_TYPES o " +
           	 "  WHERE o.BILL_MAJOR_TYPE_CODE=m.BILL_MAJOR_TYPE " +
           	 "  AND o.BILL_MINOR_TYPE_CODE  =m.BILL_MINOR_TYPE_CODE " +
           	 "  AND o.BILL_SUB_TYPE_CODE    =m.bill_sub_type_code " +
           	 "  AND status                  ='L' " +
           	 "  )AS subdesc, " +
           	
           	 "  m.SANCTION_PROC_NO, " +
           	 "  case  when m.bill_type <> 'WOSP' then (SELECT SANCTION_PROC_NO " +
           	 "  FROM HRM_SANCTIONS_BILLS_LINK_MST " +
           	 "  WHERE (HRMS_SANCTION_ID)::varchar=m.SANCTION_PROC_NO " +
           	 "  ) else m.SANCTION_PROC_NO end AS SanProcNo, " +
           	 "  m.PAYEE_CODE as mas_payCode, " +
           	// "  t.payable_to as PAYEE_CODE , " +
           	 "  TO_CHAR(m.BILL_DATE,'dd/mm/yyyy')BILL_DATE, " +
           	 "  m.BILL_PROCESSING_DONE_BY, " +
           	 "  (SELECT v.sl_codename " +
           	 "  FROM SL_TYPE_CODE_NAME_VIEW v " +
           	 "  WHERE SL_TYPE=7 " +
           	 "  AND SL_CODE  =m.BILL_PROCESSING_DONE_BY " +
           	 "  )                                           AS proccessingdesc, " +
           	 "  TO_CHAR(m.PROCEEDING_RECD_DATE,'dd/mm/yyyy')AS PROCEEDING_RECD_DATE, " +
           	// "  t.PAYEE_TYPE_CODE, " +
           	/* "  DECODE( " +
           	 "  (SELECT v.sl_codename FROM SL_TYPE_CODE_NAME_VIEW v WHERE SL_TYPE=t.PAYEE_TYPE_CODE " +
           	 "  AND SL_CODE                                                      =t.payable_to " +
           	 "  ),NULL,'-', " +
           	 "  (SELECT v.sl_codename " +
           	 "  FROM SL_TYPE_CODE_NAME_VIEW v " +
           	 "  WHERE v.SL_TYPE=t.PAYEE_TYPE_CODE " +
           	 "  AND v.SL_CODE  =t.payable_to " +
           	 "  ))AS paydesc, " +*/
           	 "  m.TOTAL_SANCTIONED_AMOUNT," +
           	 //"t.AMOUNT, " +
           	 "  m.TOTAL_BILL_AMOUNT, " +
           	// "  m.REMARKS,"
           	  "  CASE WHEN INSTR(M.REMARKS, '0', -1)=LENGTH(M.REMARKS) THEN   substr(M.REMARKS,0,INSTR(m.REMARKS, '0', -1)-2) else M.REMARKS end as REMARKS ,"
           	 + "m.BILL_TYPE " +
           	 "FROM FAS_BILL_REGISTER_MASTERNEW m, " +
           	 "  fas_bill_register_transactionw t " +
           	 "WHERE m.accounting_unit_id     =t.accounting_unit_id " +
           	 "AND m.accounting_unit_office_id=t.accounting_unit_office_id " +
           	 "AND m.cashbook_year            =t.cashbook_year and m.bill_no=t.bill_no " +
           	 "AND m.cashbook_month           =t.cashbook_month " +
           	 SUB_QRY+
           	 " and ( BILL_TYPE='WSP'  or  BILL_TYPE is null ) "+
           /*	 " AND m.CASHBOOK_YEAR            = " +txtCB_Year+" "+
           	 " AND m.CASHBOOK_MONTH           = " +txtCB_Month+" "+*/
           sub+
           	optioiiid+
            	 " ORDER BY accounting_unit_id, " +
            	 "  bill_no";

            	 
             }else if(sancWith.equalsIgnoreCase("GPF"))
            	 {
            	  if(ShwType.equalsIgnoreCase("OF")){
                    	SUB_QRY=" and m.ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode;}
              	  else{SUB_QRY="";}
              	  if(optionId.equalsIgnoreCase("live")){
                    	  optioiiid=" and m.STATUS='L'";
                      }else if(optionId.equalsIgnoreCase("cancel")){
                    	  optioiiid=" and m.STATUS='C'";
                      }
            	 sql="SELECT " +
            	 " distinct m.BILL_NO, " +
            	 		"m.ACCOUNTING_UNIT_ID, " +
            	 "  m.ACCOUNTING_UNIT_OFFICE_ID, " +
            	 "  (SELECT u.accounting_unit_name " +
            	 "  FROM fas_mst_acct_units u " +
            	 "  WHERE u.accounting_unit_id=m.accounting_unit_id " +
            	 "  ) AS unit_name, " +
            	 "  m.BILL_MINOR_TYPE_CODE, " +
            	 "  m.BILL_MAJOR_TYPE, " +
            	 "  m.BILL_SUB_TYPE_CODE, " +
            	 "  (SELECT o.BILL_SUB_TYPE_DESC " +
            	 "  FROM FAS_BILL_SUB_TYPES o " +
            	 "  WHERE o.BILL_MAJOR_TYPE_CODE=m.BILL_MAJOR_TYPE " +
            	 "  AND o.BILL_MINOR_TYPE_CODE  =m.BILL_MINOR_TYPE_CODE " +
            	 "  AND o.BILL_SUB_TYPE_CODE    =m.bill_sub_type_code " +
            	 "  AND status                  ='L' " +
            	 "  )AS subdesc, " +
            	
            	 "  m.SANCTION_PROC_NO, " +
            	 "  case when  m.bill_type <> 'WOSP' then (SELECT SANCTION_PROC_NO " +
            	 "  FROM HRM_SANCTIONS_BILLS_LINK_MST " +
            	 "  WHERE (HRMS_SANCTION_ID)::varchar=m.SANCTION_PROC_NO " +
            	 "  ) else m.SANCTION_PROC_NO end AS SanProcNo, " +
            	 "  m.PAYEE_CODE as mas_payCode, " +
            	// "  t.payable_to as PAYEE_CODE , " +
            	 "  TO_CHAR(m.BILL_DATE,'dd/mm/yyyy')BILL_DATE, " +
            	 "  m.BILL_PROCESSING_DONE_BY, " +
            	 "  (SELECT v.sl_codename " +
            	 "  FROM SL_TYPE_CODE_NAME_VIEW v " +
            	 "  WHERE SL_TYPE=7 " +
            	 "  AND SL_CODE  =m.BILL_PROCESSING_DONE_BY " +
            	 "  )                                           AS proccessingdesc, " +
            	 "  TO_CHAR(m.PROCEEDING_RECD_DATE,'dd/mm/yyyy')AS PROCEEDING_RECD_DATE, " +
            	// "  t.PAYEE_TYPE_CODE, " +
            	/* "  DECODE( " +
            	 "  (SELECT v.sl_codename FROM SL_TYPE_CODE_NAME_VIEW v WHERE SL_TYPE=t.PAYEE_TYPE_CODE " +
            	 "  AND SL_CODE                                                      =t.payable_to " +
            	 "  ),NULL,'-', " +
            	 "  (SELECT v.sl_codename " +
            	 "  FROM SL_TYPE_CODE_NAME_VIEW v " +
            	 "  WHERE v.SL_TYPE=t.PAYEE_TYPE_CODE " +
            	 "  AND v.SL_CODE  =t.payable_to " +
            	 "  ))AS paydesc, " +*/
            	 "  m.TOTAL_SANCTIONED_AMOUNT," +
            	 //"t.AMOUNT, " +
            	 "  m.TOTAL_BILL_AMOUNT, " +
            	 "  m.REMARKS,m.BILL_TYPE " +
            	 "FROM FAS_BILL_REGISTER_MASTER m, " +
            	 "  fas_bill_register_transaction t " +
            	 "WHERE m.accounting_unit_id     =t.accounting_unit_id " +
            	 "AND m.accounting_unit_office_id=t.accounting_unit_office_id " +
            	 "AND m.cashbook_year            =t.cashbook_year and m.bill_no=t.bill_no " +
            	 "AND m.cashbook_month           =t.cashbook_month " +
            	 SUB_QRY+
            	 " and BILL_TYPE='WOSP' and m.bill_date < '01-Apr-15'  "+
            /*	 " AND m.CASHBOOK_YEAR            = " +txtCB_Year+" "+
            	 " AND m.CASHBOOK_MONTH           = " +txtCB_Month+" "+*/
            sub+
            	optioiiid+
            	"  union all "+"SELECT " +
           	 "  distinct m.BILL_NO, " +
 	 		" m.ACCOUNTING_UNIT_ID, " +
 	 "  m.ACCOUNTING_UNIT_OFFICE_ID, " +
 	 "  (SELECT u.accounting_unit_name " +
 	 "  FROM fas_mst_acct_units u " +
 	 "  WHERE u.accounting_unit_id=m.accounting_unit_id " +
 	 "  ) AS unit_name, " +
 	 "  m.BILL_MINOR_TYPE_CODE, " +
 	 "  m.BILL_MAJOR_TYPE, " +
 	 "  m.BILL_SUB_TYPE_CODE, " +
 	 "  (SELECT o.BILL_SUB_TYPE_DESC " +
 	 "  FROM FAS_BILL_SUB_TYPES o " +
 	 "  WHERE o.BILL_MAJOR_TYPE_CODE=m.BILL_MAJOR_TYPE " +
 	 "  AND o.BILL_MINOR_TYPE_CODE  =m.BILL_MINOR_TYPE_CODE " +
 	 "  AND o.BILL_SUB_TYPE_CODE    =m.bill_sub_type_code " +
 	 "  AND status                  ='L' " +
 	 "  )AS subdesc, " +
 	
 	 "  m.SANCTION_PROC_NO, " +
 	 "  case when  m.bill_type <> 'WOSP' then (SELECT SANCTION_PROC_NO " +
 	 "  FROM HRM_SANCTIONS_BILLS_LINK_MST " +
 	 "  WHERE (HRMS_SANCTION_ID)::varchar=m.SANCTION_PROC_NO " +
 	 "  ) else m.SANCTION_PROC_NO end AS SanProcNo, " +
 	 "  m.PAYEE_CODE as mas_payCode, " +
 	// "  t.payable_to as PAYEE_CODE , " +
 	 "  TO_CHAR(m.BILL_DATE,'dd/mm/yyyy')BILL_DATE, " +
 	 "  m.BILL_PROCESSING_DONE_BY, " +
 	 "  (SELECT v.sl_codename " +
 	 "  FROM SL_TYPE_CODE_NAME_VIEW v " +
 	 "  WHERE SL_TYPE=7 " +
 	 "  AND SL_CODE  =m.BILL_PROCESSING_DONE_BY " +
 	 "  )                                           AS proccessingdesc, " +
 	 "  TO_CHAR(m.PROCEEDING_RECD_DATE,'dd/mm/yyyy')AS PROCEEDING_RECD_DATE, " +
 	// "  t.PAYEE_TYPE_CODE, " +
 	/* "  DECODE( " +
 	 "  (SELECT v.sl_codename FROM SL_TYPE_CODE_NAME_VIEW v WHERE SL_TYPE=t.PAYEE_TYPE_CODE " +
 	 "  AND SL_CODE                                                      =t.payable_to " +
 	 "  ),NULL,'-', " +
 	 "  (SELECT v.sl_codename " +
 	 "  FROM SL_TYPE_CODE_NAME_VIEW v " +
 	 "  WHERE v.SL_TYPE=t.PAYEE_TYPE_CODE " +
 	 "  AND v.SL_CODE  =t.payable_to " +
 	 "  ))AS paydesc, " +*/
 	 "  m.TOTAL_SANCTIONED_AMOUNT," +
 	 //"t.AMOUNT, " +
 	 "  m.TOTAL_BILL_AMOUNT, " +
 	 "  m.REMARKS,m.BILL_TYPE " +
 	 "FROM FAS_BILL_REGISTER_MASTERnew m, " +
 	 "  fas_bill_register_transactionw t " +
 	 "WHERE m.accounting_unit_id     =t.accounting_unit_id " +
 	 "AND m.accounting_unit_office_id=t.accounting_unit_office_id " +
 	 "AND m.cashbook_year            =t.cashbook_year and m.bill_no=t.bill_no " +
 	 "AND m.cashbook_month           =t.cashbook_month " +
 	 SUB_QRY+
 	 " and BILL_TYPE='WOSP' "+
 /*	 " AND m.CASHBOOK_YEAR            = " +txtCB_Year+" "+
 	 " AND m.CASHBOOK_MONTH           = " +txtCB_Month+" "+*/
 sub+
 	optioiiid+
 	 " ORDER BY accounting_unit_id, " +
 	 "  bill_no";

            	 
            	 }else if(sancWith.equalsIgnoreCase("N"))
            	 
             {
            	 sql="SELECT  m.ACCOUNTING_UNIT_ID,m.ACCOUNTING_UNIT_OFFICE_ID,(select u.accounting_unit_name from fas_mst_acct_units u where u.accounting_unit_id=m.accounting_unit_id) as unit_name," +
            	 		"m.BILL_MINOR_TYPE_CODE,m.BILL_MAJOR_TYPE,m.BILL_SUB_TYPE_CODE, "+
				 " (SELECT BILL_SUB_TYPE_DESC "+
		          "  FROM FAS_BILL_SUB_TYPES o "+
		          " WHERE BILL_MAJOR_TYPE_CODE=m.BILL_MAJOR_TYPE "+
		          " AND BILL_MINOR_TYPE_CODE  =m.BILL_MINOR_TYPE_CODE "+
		          " AND m.BILL_SUB_TYPE_CODE  =o.bill_sub_type_code "+
		          " AND status                ='L')as subdesc, "+
            	" BILL_NO,SANCTION_PROC_NO,'WOSP' as BILL_TYPE, " +
            	"(select SANCTION_PROC_NO from HRM_SANCTIONS_BILLS_LINK_MST " +
            	" where (HRMS_SANCTION_ID)::varchar=m.SANCTION_PROC_NO)as SanProcNo," +
            //	"PAYEE_CODE," +
            	 "to_char(BILL_DATE,'dd/mm/yyyy')BILL_DATE," +
            	" BILL_PROCESSING_DONE_BY,(select v.sl_codename " +
	        		"    from SL_TYPE_CODE_NAME_VIEW v where SL_TYPE=7 and SL_CODE=BILL_PROCESSING_DONE_BY)as proccessingdesc, "+
	        			" to_char(PROCEEDING_RECD_DATE,'dd/mm/yyyy')as PROCEEDING_RECD_DATE,"+
	        			// "PAYEE_TYPE_CODE,decode((select v.sl_codename " +
	        			//"from SL_TYPE_CODE_NAME_VIEW v where SL_TYPE=PAYEE_TYPE_CODE and SL_CODE=PAYEE_CODE),null,'-',(select v.sl_codename from SL_TYPE_CODE_NAME_VIEW v where SL_TYPE=PAYEE_TYPE_CODE and SL_CODE=PAYEE_CODE))as paydesc," +
	        			"TOTAL_SANCTIONED_AMOUNT,TOTAL_BILL_AMOUNT,REMARKS from FAS_BILL_REGISTERNEW m "+
	        			" where "+SUB_QRY+"  CASHBOOK_YEAR="+txtCB_Year+" and CASHBOOK_MONTH="+txtCB_Month+optioiiid+" order by m.accounting_unit_id,m.bill_no";//" and STATUS='L'";
	            
             }
				            System.out.println("SQL::::"+sql);
				            try
				            {
						            int count=0;
						            ps=con.prepareStatement(sql);
						        	if(cmd.equalsIgnoreCase("searchByMonth")){
						        
						        	}else{
						        		ps.setDate(1, fromDte);	
							        	ps.setDate(2, toDate);	
						        	}
						            rs=ps.executeQuery();
						            xml=xml+"<SancWith>"+sancWith+"</SancWith>";
					                while(rs.next())
					                {
					                	String SANCTION_PROC_NO=rs.getString("SANCTION_PROC_NO");
						                    xml=xml+"<leng>";
						                   
						                    
						                    	
						                    xml=xml+"<ACCOUNTING_UNIT_ID>"+rs.getInt("ACCOUNTING_UNIT_ID")+"</ACCOUNTING_UNIT_ID>";
						                    xml=xml+"<ACCOUNTING_UNIT_OFFICE_ID>"+rs.getInt("ACCOUNTING_UNIT_OFFICE_ID")+"</ACCOUNTING_UNIT_OFFICE_ID>";
						                    xml=xml+"<unitname><![CDATA["+rs.getString("unit_name").trim()+"]]></unitname>";
						                   
						                    xml=xml+"<billno>"+rs.getInt("BILL_NO")+"</billno>";
						                    xml=xml+"<sancno>"+rs.getString("SANCTION_PROC_NO")+"</sancno>";
						                    System.out.println("SanProcNo"+rs.getString("SanProcNo"));
						                  
						                    String s_pro="";
						                    System.out.println("rs.getString(BILL_TYPE) === >"+rs.getString("BILL_TYPE"));
						                    if((rs.getString("SanProcNo")==null ) &&(rs.getString("BILL_TYPE").equalsIgnoreCase("WSP")))
						                    {
						                    	System.out.println("null test22");
						                    	String ss="SELECT SANCTION_PROC_NO FROM SLS_SANCTIONS_BILLS_LINK_MST1 m WHERE ACCOUNTING_UNIT_ID="+rs.getInt("ACCOUNTING_UNIT_ID")+" AND HRMS_SANCTION_ID= " +SANCTION_PROC_NO+" and " +
						                    			"BILL_MAJOR_TYPE_CODE="+rs.getInt("BILL_MAJOR_TYPE")+" and BILL_MINOR_TYPE_CODE="+rs.getInt("BILL_MINOR_TYPE_CODE")+" and " +
						                    					"BILL_SUB_TYPE_CODE="+rs.getInt("BILL_SUB_TYPE_CODE");
						                    	try{
						                    		System.out.println("sss >>> "+ss);
						                    	ps2=con.prepareStatement(ss);
										            rs2=ps2.executeQuery();
										            int c=0;String val="";
										            while(rs2.next())
										            {
										            	//xml=xml+"<sanprocno>"+rs2.getString("SANCTION_PROC_NO")+"</sanprocno>";
										            	//xml=xml+rs2.getString("SANCTION_PROC_NO");
										            	val=rs2.getString("SANCTION_PROC_NO");
										            	c++;
										            }if(c==0){
										            	s_pro="-";
										            }else if(c!=0){
										            	s_pro=val;
										            }
										            } catch (Exception e) {
										            	System.out.println("Exeptopon in part");
														e.printStackTrace();
													}   
										            System.out
													.println("if part"+s_pro);
								                    
						                    }
						                    else
						                    {
						                    	System.out.println("Else part"+rs.getString("SanProcNo"));
						                    	//xml=xml+"<sanprocno>"+rs.getString("SanProcNo")+"</sanprocno>";
						                    	//xml=xml+rs.getString("SanProcNo");
						                    	s_pro=rs.getString("SanProcNo");
						                    	System.out
														.println("s_pro else  "+s_pro);
						                    	if(s_pro==null){
						                    		s_pro="-";
						                    	}
						                    }
						                    System.out.println("s_pro >>> "+s_pro);
						                    xml=xml+"<sanprocno>"+s_pro+"</sanprocno>";
						                    xml=xml+"<billdate>"+rs.getString("BILL_DATE")+"</billdate>";
						                   // xml=xml+"<paycode>"+rs.getString("paydesc")+"</paycode>";
						                    
						                  //  xml=xml+"<paytypecode>"+rs.getInt("PAYEE_TYPE_CODE")+"</paytypecode>";
						                    xml=xml+"<processing>"+rs.getString("proccessingdesc") +"</processing>";
						                    xml=xml+"<processingdate>"+rs.getString("PROCEEDING_RECD_DATE") +"</processingdate>";
						                    xml=xml+"<sancamt>"+rs.getString("TOTAL_SANCTIONED_AMOUNT") +"</sancamt>";
						                    xml=xml+"<billamt>"+rs.getString("TOTAL_BILL_AMOUNT") +"</billamt>";
						                    if(sancWith.equalsIgnoreCase("Y"))
						                    {
						                    	   xml=xml+"<AMOUNT>WS</AMOUNT>";
						                    }else{
						                    	 xml=xml+"<AMOUNT>WOS</AMOUNT>";
						                    }
						                    xml=xml+"<remarks><![CDATA["+rs.getString("REMARKS")+"]]></remarks>";						                    
						                    xml=xml+"<subdesc>"+rs.getString("subdesc") +"</subdesc>";
						                    xml=xml+"</leng>";
						                    count++;
					                }
					                if(count>0) 
					                {
						                    System.out.println("inside count==0");
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
	}