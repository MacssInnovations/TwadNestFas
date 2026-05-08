package Servlets.FAS.FAS1.BRS.servlets;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * Servlet implementation class BRSReport_Part2B
 */
public class BRSReport_Part2B extends HttpServlet {
	private String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BRSReport_Part2B() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String xml=null;
		
		Connection con = null;
                String UnitName=null;
                Double amount=0.00;
                String mode_id="";
                int bank_id=0,branch_id=0;
	    BigDecimal ii  = null;
	    String hid="";
		try {
			HttpSession session = request.getSession(false);
			if (session == null) {
				System.out.println(request.getContextPath() + "/index.jsp");
				response.sendRedirect(request.getContextPath() + "/index.jsp");
				return;
			}
			System.out.println(session);

		} catch (Exception e) {
			System.out.println("Redirect Error :" + e);
		}
		try {
			ResourceBundle rs1 = ResourceBundle
					.getBundle("Servlets.Security.servlets.Config");
			String ConnectionString = "";

			String strDriver = rs1.getString("Config.DATA_BASE_DRIVER");
			String strdsn = rs1.getString("Config.DSN");
			String strhostname = rs1.getString("Config.HOST_NAME");
			String strportno = rs1.getString("Config.PORT_NUMBER");
			String strsid = rs1.getString("Config.SID");
			String strdbusername = rs1.getString("Config.USER_NAME");
			String strdbpassword = rs1.getString("Config.PASSWORD");
			ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":"
					+ strportno.trim() + ":" + strsid.trim();
			Class.forName(strDriver.trim());
			con = DriverManager.getConnection(ConnectionString, strdbusername
					.trim(), strdbpassword.trim());
		} catch (Exception e) {
			System.out.println("Exception in opening connection :" + e);
			// sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

		}
		
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
		 String update_user=(String)session.getAttribute("UserId");
      long l=System.currentTimeMillis();
      Timestamp ts=new Timestamp(l);	
		String opr_node="";
		int cboAcc_UnitCode = Integer.parseInt(request
				.getParameter("cmbAcc_UnitCode"));
		int cboOffice_code = Integer.parseInt(request
				.getParameter("cmbOffice_code"));
		int cboCashBook_Year = Integer.parseInt(request
				.getParameter("cboCashBook_Year"));
		int cboCashBook_Month = Integer.parseInt(request
				.getParameter("cboCashBook_Month"));
		long cmbBankAccNo = Long.parseLong(request
				.getParameter("cmbBankAccNo"));
		try{
			PreparedStatement ps_node=con.prepareStatement("select AC_OPERATIONAL_MODE_ID from fas_mst_bank_balance where  bank_ac_no="+cmbBankAccNo)	;
			
			ResultSet rs_node=ps_node.executeQuery();
			while(rs_node.next()){
				opr_node=rs_node.getString("AC_OPERATIONAL_MODE_ID");
			}
			}catch(Exception e){
				e.printStackTrace();
			}
			
			
		String command=request.getParameter("command");
hid=request.getParameter("old");
	    try {
	    PreparedStatement ps=null;
	    ResultSet rs=null;
	    
	    ps=con.prepareStatement("select ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=?");
	    ps.setInt(1,cboAcc_UnitCode);
	    rs=ps.executeQuery();
	    if(rs.next())
	         UnitName=rs.getString("ACCOUNTING_UNIT_NAME");
	    
	    
	    }
	    catch (SQLException e) {
	        System.out.println("SQL Exception -->"+e);
	    }
String BAnk_name="",Branch_name="";
		
	    try{
			String ac= " SELECT aa.bank_ac_no,\n" + 
					 " aa.AC_OPERATIONAL_MODE_ID,\n" + 
					 " aa.bank_id,\n" + 
					 "  aa.branch_id,\n" + 
					 " b.bank_name,\n" + 
					 " br.branch_name\n" + 
					 " FROM Fas_Mst_Bank_Balance aa ,\n" + 
					 " FAS_MST_BANK_BRANCHES br,\n" + 
					 "  FAS_MST_BANKS B\n" + 
					 " WHERE B.BANK_ID           =aa.BANK_ID\n" + 
					 " AND Br.BANK_ID            =aa.BANK_ID\n" + 
					 " AND Br.BRANCH_ID          =aa.BRANCH_ID\n" + 
					 " AND aa.accounting_unit_id = "+cboAcc_UnitCode+
					 " AND aa.status             ='Y'\n" + 
					 " AND aa.bank_ac_no         ="+cmbBankAccNo;
			PreparedStatement pss=con.prepareStatement(ac);
			ResultSet rss=pss.executeQuery();
			if(rss.next())
			{
				mode_id=rss.getString("AC_OPERATIONAL_MODE_ID");//opr
				bank_id=rss.getInt("bank_id");//bank_id
				branch_id=rss.getInt("branch_id");//branch_id
				BAnk_name=rss.getString("bank_name");
				Branch_name=rss.getString("branch_name");
			}
		}
		catch (Exception e) {
			System.out.println("Error in mode_id -->" + e);
		}
	    
	    if(cboOffice_code==5000) {
                try {
                PreparedStatement ps=null;
                ResultSet rs=null;
                
                ps=con.prepareStatement("SELECT SUM(Amount)AS Amt\n" + 
                " FROM Fas_Fund_Trf_From_Ho_Trn Trn,\n" + 
                "  Fas_Fund_Trf_From_Ho_Master Mas\n" + 
                " Where Mas.Accounting_Unit_Id=Trn.Accounting_Unit_Id\n" + 
                " And Mas.Accounting_For_Office_Id=Trn.Accounting_For_Office_Id\n" + 
                " And Mas.Cashbook_Year=Trn.Cashbook_Year\n" + 
                " And Mas.Cashbook_Month=Trn.Cashbook_Month\n" + 
                " And Mas.VOUCHER_NO=Trn.VOUCHER_NO\n" + 
                " and trn.Transfer_To_Office_Id=?\n" + 
                " AND trn.Cashbook_Year          =?\n" + 
                " AND trn.Cashbook_Month         =?\n" + 
                " AND trn.Office_Account_No      =?\n" + 
                " And Mas.Transfer_Status        ='L'\n" + 
                " and Transfered_To_Ho_Unit_Id=?");
                ps.setInt(1,cboOffice_code);
            
                ps.setInt(2,cboCashBook_Year);
                ps.setInt(3,cboCashBook_Month);
                ps.setLong(4,cmbBankAccNo);
                ps.setInt(5,cboAcc_UnitCode);
               
                //    System.out.println("cboOffice_code:::"+cboOffice_code+""+);
                rs=ps.executeQuery();
                if(rs.next())
                {
                System.out.println("amount:::yyyyy:::"+rs.getDouble("Amt"));
                     amount=rs.getDouble("Amt");
                    ii = new BigDecimal(amount);
                }
                
                
                }
                catch (SQLException e) {
                    System.out.println("SQL Exception -->"+e);
                }
            }
            else {
                try {
                PreparedStatement ps=null;
                ResultSet rs=null;
                String sql="";
                String s1="";
                if(cboOffice_code==5000)
                {
                	  ps=con.prepareStatement("SELECT SUM(Amount)AS Amt\n" + 
                              " FROM Fas_Fund_Trf_From_Ho_Trn Trn,\n" + 
                              "  Fas_Fund_Trf_From_Ho_Master Mas\n" + 
                              " Where Mas.Accounting_Unit_Id=Trn.Accounting_Unit_Id\n" + 
                              " And Mas.Accounting_For_Office_Id=Trn.Accounting_For_Office_Id\n" + 
                              " And Mas.Cashbook_Year=Trn.Cashbook_Year\n" + 
                              " And Mas.Cashbook_Month=Trn.Cashbook_Month\n" + 
                              " And Mas.VOUCHER_NO=Trn.VOUCHER_NO\n" + 
                              " and trn.Transfer_To_Office_Id=? and TRANSFERED_TO_HO_UNIT_ID=" +cboAcc_UnitCode+
                              " AND trn.Cashbook_Year          =?\n" + 
                              " AND trn.Cashbook_Month         =?\n" + 
                              " AND trn.Office_Account_No      =?\n" + 
                              " AND mas.TRANSFER_STATUS        ='L'");  
                }
                else
                {
                	
                
                s1="SELECT SUM(Amount)AS Amt\n" + 
                " FROM Fas_Fund_Trf_From_Ho_Trn Trn,\n" + 
                "  Fas_Fund_Trf_From_Ho_Master Mas\n" + 
                " Where Mas.Accounting_Unit_Id=Trn.Accounting_Unit_Id\n" + 
                " And Mas.Accounting_For_Office_Id=Trn.Accounting_For_Office_Id\n" + 
                " And Mas.Cashbook_Year=Trn.Cashbook_Year\n" + 
                " And Mas.Cashbook_Month=Trn.Cashbook_Month\n" + 
                " And Mas.VOUCHER_NO=Trn.VOUCHER_NO\n" + 
                " and trn.Transfer_To_Office_Id=?\n" + 
                " AND trn.Cashbook_Year          =?\n" + 
                " AND trn.Cashbook_Month         =?\n" + 
                " AND trn.Office_Account_No      =?\n" + 
                " AND mas.TRANSFER_STATUS        ='L'";  
                }
                ps=con.prepareStatement(s1);
                ps.setInt(1,cboOffice_code);
                ps.setInt(2,cboCashBook_Year);
                ps.setInt(3,cboCashBook_Month);
                ps.setLong(4,cmbBankAccNo);
                    
                rs=ps.executeQuery();
                if(rs.next())
                     amount=rs.getDouble("Amt");
                    ii = new BigDecimal(amount);
                
                }
                catch (SQLException e) {
                    System.out.println("SQL Exception -->"+e);
                }
            }
            System.out.println("amount::::"+amount);
		String month = null;
		if(cboCashBook_Month == 1){
			month = "January";
		}else if(cboCashBook_Month == 2){
			month = "Febrary";
		}else if(cboCashBook_Month == 3){
			month = "March";
		}else if(cboCashBook_Month == 4){
			month = "April";
		}else if(cboCashBook_Month == 5){
			month = "May";
		}else if(cboCashBook_Month == 6){
			month = "June";
		}else if(cboCashBook_Month == 7){
			month = "July";
		}else if(cboCashBook_Month == 8){
			month = "August";
		}else if(cboCashBook_Month == 9){
			month = "September";
		}else if(cboCashBook_Month == 10){
			month = "October";
		}else if(cboCashBook_Month == 11){
			month = "November";
		}else if(cboCashBook_Month == 12){
			month = "December";
		}
		
		String sql_rep="";
        int c_new=0;String c_flag="";
        
        /// For there s no records in any transaction and that stuation we need to check previous month records
        
     try{   PreparedStatement ps_new=con.prepareStatement(
    		 "SELECT ACCOUNTING_UNIT_ID  AS acc_u_id, " +
        		"  ACCOUNTING_FOR_OFFICE_ID AS acc_off_id, " +
        		"  CASHBOOK_YEAR            AS csh_bk_yr, " +
        		"  CASHBOOK_MONTH           AS csh_bk_mnth, " +
        		"  Account_No               AS Acc_No, " +
        		"  SUM(total_amount)        AS only_4 " +
        		"FROM FAS_payment_master " +
        	
        	       "          WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
   	            "          AND accounting_for_office_id=" +cboOffice_code+ 
   	            "          AND cashbook_month          = " +cboCashBook_Month+ 
   	            "          AND Cashbook_Year           =" +cboCashBook_Year+ 
   	            "          AND Account_No              = " +cmbBankAccNo+ 
        		"AND Payment_Status          ='L' " +
        		"GROUP BY Accounting_Unit_Id, " +
        		"  Accounting_For_Office_Id, " +
        		"  Cashbook_Year, " +
        		"  Cashbook_Month, " +
        		"  ACCOUNT_NO " +
        		"UNION ALL " +
        		"SELECT ACCOUNTING_UNIT_ID  AS acc_u_id, " +
        		"  ACCOUNTING_FOR_OFFICE_ID AS acc_off_id, " +
        		"  CASHBOOK_YEAR            AS csh_bk_yr, " +
        		"  CASHBOOK_MONTH           AS csh_bk_mnth, " +
        		"  Account_No               AS Acc_No, " +
        		"  SUM(Total_Amount)        AS only_4 " +
        		"FROM FAS_receipt_master " +
        	       "          WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
   	            "          AND accounting_for_office_id=" +cboOffice_code+ 
   	            "          AND cashbook_month          = " +cboCashBook_Month+ 
   	            "          AND Cashbook_Year           =" +cboCashBook_Year+ 
   	            "          AND Account_No              = " +cmbBankAccNo+ 
        		"AND receipt_Status          ='L' " +
        		"AND CREATED_BY_MODULE       ='SC' " +
        		"GROUP BY Accounting_Unit_Id, " +
        		"  Accounting_For_Office_Id, " +
        		"  Cashbook_Year, " +
        		"  Cashbook_Month, " +
        		"  Account_No " +
        		"UNION ALL " +
        		"SELECT ACCOUNTING_UNIT_ID  AS acc_u_id, " +
        		"  ACCOUNTING_FOR_OFFICE_ID AS acc_off_id, " +
        		"  CASHBOOK_YEAR            AS csh_bk_yr, " +
        		"  CASHBOOK_MONTH           AS csh_bk_mnth, " +
        		"  OFFICE_ACCOUNT_NO        AS Acc_No, " +
        		"  SUM(Total_Amount)        AS only_4 " +
        		"FROM FAS_FUND_TRF_FROM_OFFICE " +
        	       "          WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
   	            "          AND accounting_for_office_id=" +cboOffice_code+ 
   	            "          AND cashbook_month          = " +cboCashBook_Month+ 
   	            "          AND Cashbook_Year           =" +cboCashBook_Year+ 
   	            "          AND OFFICE_ACCOUNT_NO              = " +cmbBankAccNo+ 
        		
        		"GROUP BY Accounting_Unit_Id, " +
        		"  Accounting_For_Office_Id, " +
        		"  Cashbook_Year, " +
        		"  Cashbook_Month, " +
        		"  OFFICE_ACCOUNT_NO " +
        		"UNION ALL " +
        		"SELECT ACCOUNTING_UNIT_ID  AS acc_u_id, " +
        		"  ACCOUNTING_FOR_OFFICE_ID AS acc_off_id, " +
        		"  CASHBOOK_YEAR            AS csh_bk_yr, " +
        		"  CASHBOOK_MONTH           AS csh_bk_mnth, " +
        		"  FROM_ACCOUNT_NO          AS Acc_No, " +
        		"  SUM(Total_Amount)        AS only_4 " +
        		"FROM FAS_INTER_BANK_TRF_AT_HO " +
        	      "          WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
     	            "          AND accounting_for_office_id=" +cboOffice_code+ 
     	            "          AND cashbook_month          = " +cboCashBook_Month+ 
     	            "          AND Cashbook_Year           =" +cboCashBook_Year+ 
     	            "          AND FROM_ACCOUNT_NO              = " +cmbBankAccNo+ 
        	
        		"GROUP BY Accounting_Unit_Id, " +
        		"  Accounting_For_Office_Id, " +
        		"  Cashbook_Year, " +
        		"  CASHBOOK_MONTH, " +
        		"  FROM_ACCOUNT_NO" );
     ResultSet rs_new=ps_new.executeQuery();
     while(rs_new.next()){
    	 c_new=rs_new.getInt(1);
    	 c_new++;
    	
     }
     }catch(Exception e){
    	 e.printStackTrace();
     }
     
     if(c_new==0)
     {
    	 int month_new=0;
    	 int year_new=0;
    	 if (cboCashBook_Month==1){
    		 month_new=12;year_new=cboCashBook_Year-1;
    	 }else{
    		 month_new=cboCashBook_Month;
    		 year_new=cboCashBook_Year;
    	 }
    	 c_flag="SELECT ACCOUNTING_UNIT_ID  AS acc_u_id, " +
         		"  ACCOUNTING_FOR_OFFICE_ID AS acc_off_id, " +
         		  cboCashBook_Year  +"         AS csh_bk_yr, " +
         		 cboCashBook_Month+"        AS csh_bk_mnth, " +
         		  cmbBankAccNo+"               AS Acc_No, " +
         		"  0       AS only_4 " +
         		"FROM FAS_payment_master " +
         	
         	       "          WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
    	            "          AND accounting_for_office_id=" +cboOffice_code+ 
    	            "          AND cashbook_month          = "+ month_new+ 
    	            "          AND Cashbook_Year           =" +year_new+ 
    	            "          AND Account_No              = " +cmbBankAccNo+ 
         		"AND Payment_Status          ='L' " +  
         		" AND CREATED_BY_MODULE !='CRM' " +
         		"GROUP BY Accounting_Unit_Id, " +
         		"  Accounting_For_Office_Id, " +
         		"  Cashbook_Year, " +
         		"  Cashbook_Month, " +
         		"  ACCOUNT_NO " ;
     }else if(c_new!=0)
     {
    	 c_flag= "SELECT ACCOUNTING_UNIT_ID  AS acc_u_id, " +
         		"  ACCOUNTING_FOR_OFFICE_ID AS acc_off_id, " +
         		"  CASHBOOK_YEAR            AS csh_bk_yr, " +
         		"  CASHBOOK_MONTH           AS csh_bk_mnth, " +
         		"  Account_No               AS Acc_No, " +
         		"  SUM(total_amount)        AS only_4 " +
         		"FROM FAS_payment_master " +
         	
         	       "          WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
    	            "          AND accounting_for_office_id=" +cboOffice_code+ 
    	            "          AND cashbook_month          = " +cboCashBook_Month+ 
    	            "          AND Cashbook_Year           =" +cboCashBook_Year+ 
    	            "          AND Account_No              = " +cmbBankAccNo+ 
         		"AND Payment_Status          ='L' " +
         		" AND CREATED_BY_MODULE !='CRM' " +
         		"GROUP BY Accounting_Unit_Id, " +
         		"  Accounting_For_Office_Id, " +
         		"  Cashbook_Year, " +
         		"  Cashbook_Month, " +
         		"  ACCOUNT_NO " ;
     }

        String sql_qu="SELECT a.*,\n" + 
        "  decode(b.OB_PART2B,null,0,b.OB_PART2B)as OB_PART2B,\n" + 
        " decode(B.A_2a,null,0,b.A_2a)as A_2a,\n" + 
        "  Decode(A_2b,Null,'0',A_2b)As A_2b,\n" + 
        "  Only_4+(Decode(A_2b,Null,'0',A_2b)) As A_2c,\n" + 
        "  Decode(A_2d,Null,'0',A_2d)As A_2d,\n" + 
        "  (only_4+(Decode(A_2b,Null,'0',A_2b)))-(decode(A_2d,null,'0',A_2d)) AS A_2e,\n" + 
        "  B.Bname,\n" + 
        "  b.branchName\n" + 
        "\n" + 
        "FROM\n" + 
        "  ("+
 
  "   select \n" + 
  "    xx.acc_u_id,\n" + 
  "    xx. acc_off_id,\n" + 
  "   xx. csh_bk_yr,\n" + 
  "   xx. csh_bk_mnth,\n" + 
  "    xx. acc_no,\n" + 
  "    only_4 from \n" + 
  "   (SELECT  "+cboAcc_UnitCode+"  as acc_u_id,\n" + 
  +cboOffice_code+ " as  acc_off_id,\n" + 
  cboCashBook_Year+  "    as csh_bk_yr,\n" + 
  cboCashBook_Month+   "     csh_bk_mnth,\n" + 
  cmbBankAccNo+ " acc_no   )xx\n" + 
  
  "   left outer join "+
  "   ("
        + "SELECT acc_u_id,\n" + 
        "    acc_off_id,\n" + 
        "    csh_bk_yr,\n" + 
        "    csh_bk_mnth,\n" + 
        "    acc_no,\n" + 
        "    SUM(only_4) AS only_4\n" + 
        "  FROM\n" + 
        "    ( " +c_flag +
    /*    + "SELECT ACCOUNTING_UNIT_ID AS acc_u_id,\n" + 
        "      ACCOUNTING_FOR_OFFICE_ID AS acc_off_id,\n" + 
        "      CASHBOOK_YEAR            AS csh_bk_yr,\n" + 
        "      CASHBOOK_MONTH           AS csh_bk_mnth,\n" + 
        "      Account_No               AS Acc_No,\n" + 
        "      SUM(total_amount)        AS only_4\n" + 
        "    FROM FAS_payment_master\n" + 
        "    WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
        "    AND accounting_for_office_id=" +cboOffice_code+ 
        "    AND cashbook_month          = " +cboCashBook_Month+ 
        "    AND Cashbook_Year           =" +cboCashBook_Year+ 
        "    AND ACCOUNT_NO              = " +cmbBankAccNo+ 
        "    AND Payment_Status          ='L'\n" + 
        "    GROUP BY Accounting_Unit_Id,\n" + 
        "      Accounting_For_Office_Id,\n" + 
        "      Cashbook_Year,\n" + 
        "      Cashbook_Month,\n" + 
        "      Account_No\n" + */
        "    UNION ALL\n" + 
        "    SELECT ACCOUNTING_UNIT_ID  AS acc_u_id,\n" + 
        "      ACCOUNTING_FOR_OFFICE_ID AS acc_off_id,\n" + 
        "      CASHBOOK_YEAR            AS csh_bk_yr,\n" + 
        "      CASHBOOK_MONTH           AS csh_bk_mnth,\n" + 
        "      Account_No               AS Acc_No,\n" + 
        "      SUM(Total_Amount)        AS only_4\n" + 
        "    FROM FAS_receipt_master\n" + 
        "    WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
        "    AND accounting_for_office_id=" +cboOffice_code+ 
        "    AND cashbook_month          = " +cboCashBook_Month+ 
        "    AND Cashbook_Year           =" +cboCashBook_Year+ 
        "    AND Account_No              = " +cmbBankAccNo+ 
        "    AND receipt_Status          ='L'\n" + 
        "    AND CREATED_BY_MODULE       ='SC'\n" + 
        "    GROUP BY Accounting_Unit_Id,\n" + 
        "      Accounting_For_Office_Id,\n" + 
        "      Cashbook_Year,\n" + 
        "      Cashbook_Month,\n" + 
        "      Account_No\n" + 
        
             " UNION ALL " +
" SELECT ACCOUNTING_UNIT_ID  AS acc_u_id, " +
"  ACCOUNTING_FOR_OFFICE_ID AS acc_off_id, " +
"  CASHBOOK_YEAR            AS csh_bk_yr, " +
"  CASHBOOK_MONTH           AS csh_bk_mnth, " +
"  OFFICE_ACCOUNT_NO        AS Acc_No, " +
"  SUM(Total_Amount)        AS only_4 " +
" FROM FAS_FUND_TRF_FROM_OFFICE " +
" WHERE accounting_unit_id    =  " +cboAcc_UnitCode+ 
" AND accounting_for_office_id=" +cboOffice_code+ 
" AND cashbook_month          = " +cboCashBook_Month+ 
 " AND Cashbook_Year           =" +cboCashBook_Year+ 
" AND OFFICE_ACCOUNT_NO       = " +cmbBankAccNo+ 
"   and TRANSFER_STATUS <> 'C'  GROUP BY Accounting_Unit_Id, " +
"  Accounting_For_Office_Id, " +
"  Cashbook_Year, " +
"  Cashbook_Month, " +
"  OFFICE_ACCOUNT_NO   union all " +
"  SELECT ACCOUNTING_UNIT_ID  AS acc_u_id," +
"  ACCOUNTING_FOR_OFFICE_ID AS acc_off_id," +
"  CASHBOOK_YEAR            AS csh_bk_yr," +
"  CASHBOOK_MONTH           AS csh_bk_mnth," +
"  FROM_ACCOUNT_NO        AS Acc_No," +
"  SUM(Total_Amount)        AS only_4" +
"  FROM FAS_INTER_BANK_TRF_AT_HO" +
"  WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
"  AND accounting_for_office_id=" +cboOffice_code+ 
"  AND cashbook_month          = " +cboCashBook_Month+ 
"  AND Cashbook_Year           =" +cboCashBook_Year+ 
"  AND FROM_ACCOUNT_NO       =" +cmbBankAccNo+ 
"    and TRANSFER_STATUS <> 'C'  GROUP BY Accounting_Unit_Id," +
"  Accounting_For_Office_Id," +
"  Cashbook_Year," +
"  Cashbook_Month," +
"  FROM_ACCOUNT_NO" +
        "    )\n" + 
        "  GROUP BY acc_u_id,\n" + 
        "    acc_off_id,\n" + 
        "    csh_bk_yr,\n" + 
        "    csh_bk_mnth,\n" + 
        "    Acc_No\n" + 
        
    "    )yy\n" + 
    "   on xx.acc_u_id=yy.acc_u_id\n" + 
    "   and xx.acc_off_id=yy.acc_off_id\n" + 
    "      and xx.csh_bk_yr=yy.csh_bk_yr\n" + 
    "        and xx.csh_bk_mnth=yy.csh_bk_mnth\n" + 
        "  )a\n" + 
        "left OUTER JOIN\n" + 
        "  (SELECT t1.OB_PART2B,\n" + 
        "    t2.A_2a,\n" + 
        "    T1.Bname,\n" + 
        "    t1.branchName,\n" + 
        "    acc_u_id6,\n" + 
        "    acc_off_id6,\n" + 
        "    csh_bk_yr6,\n" + 
        "    csh_bk_mnth6,\n" + 
        "    Acc_No6\n" + 
        "  FROM\n" + 
        "    (SELECT acc_u_id6,\n" + 
        "      acc_off_id6,\n" + 
        "      csh_bk_yr6,\n" + 
        "      csh_bk_mnth6,\n" + 
        "      Acc_No6,\n" + 
        "      OB_PART2B,\n" + 
        "      Bname,\n" + 
        "      branchName\n" + 
        "    FROM\n" + 
        "      (SELECT ACCOUNTING_UNIT_ID AS acc_u_id6,\n" + 
        "        ACCOUNTING_FOR_OFFICE_ID AS acc_off_id6,\n" + 
        "        CASHBOOK_YEAR            AS csh_bk_yr6,\n" + 
        "        CASHBOOK_MONTH           AS csh_bk_mnth6,\n" + 
        "        Account_No               AS Acc_No6,\n" + 
        "        OB_PART2B,\n" + 
        "        Bank_Id,\n" + 
        "        (SELECT List1.Bank_Name\n" + 
        "        FROM Fas_Bank_List List1\n" + 
        "        WHERE List1.Bank_Id=ob1.Bank_Id\n" + 
        "        )AS Bname,\n" + 
        "        Branch_Id,\n" + 
        "        (SELECT Br.Branch_Name\n" + 
        "        FROM Fas_mst_Bank_Branches Br\n" + 
        "        WHERE Br.Bank_Id=ob1.Bank_Id\n" + 
        "        AND Br.Branch_Id=ob1.Branch_Id\n" + 
        "        )AS Branchname\n" + 
        "      FROM FAS_BRS_OB ob1\n" + 
        "      WHERE accounting_unit_id    =" +cboAcc_UnitCode+ 
        "      AND accounting_for_office_id= " +cboOffice_code+ 
        "      AND cashbook_month          =" +cboCashBook_Month+ 
        "      AND cashbook_year           =" +cboCashBook_Year+ 
        "      AND ACCOUNT_NO              = " +cmbBankAccNo+ 
        "      )\n" + 
        "    ) t1\n" + 
        "  left OUTER JOIN\n" + 
        "    (SELECT acc_u_id,\n" + 
        "      acc_off_id,\n" + 
        "      Csh_Bk_Yr,\n" + 
        "      Csh_Bk_Mnth,\n" + 
        "      Acc_No,\n" + 
        "      sum(A_2a) as A_2a\n" + 
        "    FROM\n" + 
        "      (\n" + 
      /*  + "SELECT ACCOUNTING_UNIT_ID AS acc_u_id,\n" + 
        "        ACCOUNTING_FOR_OFFICE_ID AS acc_off_id,\n" + 
        "        CASHBOOK_YEAR            AS csh_bk_yr,\n" + 
        "        CASHBOOK_MONTH           AS csh_bk_mnth,\n" + 
        "        Account_No               AS Acc_No,\n" + 
        "        SUM(CR_AMOUNT)           AS A_2a\n" + 
        "      FROM Fas_Brs_Transaction\n" + 
        "      WHERE Accounting_Unit_Id    = " +cboAcc_UnitCode+ 
        "      AND Accounting_For_Office_Id=" +cboOffice_code+ 
        "      AND Cashbook_Month          = " +cboCashBook_Month+ 
        "      AND Cashbook_Year           =" +cboCashBook_Year+ 
        "      AND Account_No              =" +cmbBankAccNo+ 
        "      AND doc_type                ='FR by Office'\n" + 
        "      GROUP BY ACCOUNTING_UNIT_ID,\n" + 
        "        ACCOUNTING_FOR_OFFICE_ID,\n" + 
        "        CASHBOOK_YEAR,\n" + 
        "        Cashbook_Month,\n" + 
        "        Account_No\n" + */
        
  "         SELECT ACCOUNTING_UNIT_ID AS acc_u_id,\n" + 
  "          ACCOUNTING_FOR_OFFICE_ID AS acc_off_id,\n" + 
  "          CASHBOOK_YEAR            AS csh_bk_yr,\n" + 
  "         CASHBOOK_MONTH           AS csh_bk_mnth,\n" + 
  "           OFFICE_ACCOUNT_NO               AS Acc_No,\n" + 
  "            SUM(TOTAL_AMOUNT)           AS A_2a\n" + 
  "          FROM FAS_FUND_RECEIPT_BY_OFFICE\n" + 
  "         WHERE Accounting_Unit_Id    = " +  +cboAcc_UnitCode+ 
  "         AND Accounting_For_Office_Id=" + cboOffice_code+ 
  "         AND Cashbook_Month          =" + cboCashBook_Month+ 
  "         AND Cashbook_Year           =" + cboCashBook_Year+ 
  "               and RECEIPT_STATUS='L'  AND OFFICE_ACCOUNT_NO              =" + cmbBankAccNo+ 
  "       GROUP BY ACCOUNTING_UNIT_ID,\n" + 
  "          ACCOUNTING_FOR_OFFICE_ID,\n" + 
  "          CASHBOOK_YEAR,\n" + 
  "           Cashbook_Month,\n" + 
  "            OFFICE_ACCOUNT_NO\n" + 
      /*  "     UNION ALL    SELECT ACCOUNTING_UNIT_ID AS acc_u_id,"+
        "          ACCOUNTING_FOR_OFFICE_ID AS acc_off_id,"+
        cboCashBook_Year+ "                      AS csh_bk_yr,"+
        cboCashBook_Month+   "                      AS csh_bk_mnth,"+
        "        Account_No               AS Acc_No,"+
        "         SUM(CR_AMOUNT)           AS A_2a"+
        "           FROM Fas_Brs_Transaction"+
        "      WHERE Accounting_Unit_Id    = " +cboAcc_UnitCode+ 
        "      AND Accounting_For_Office_Id=" +cboOffice_code+ 
        "      AND Cashbook_Month          = " +cboCashBook_Month+ 
        "          AND Cashbook_Month          <  "+cboCashBook_Month+
        "           AND Cashbook_Year           ="+cboCashBook_Year+
    
 "        and    extract(month from PASSBOOK_DATE ) ="+cboCashBook_Year+" and extract(year from PASSBOOK_DATE ) = "+cboCashBook_Year+
 "          AND Account_No              ="+cmbBankAccNo+
 "          AND doc_type                ='FR by Office'"+
 "           GROUP BY ACCOUNTING_UNIT_ID,"+
 "             ACCOUNTING_FOR_OFFICE_ID,"+
 "             CASHBOOK_YEAR,"+
 "             Cashbook_Month,"+
 "            Account_No"+*/
 "        UNION ALL"+
 "        SELECT ACCOUNTING_UNIT_ID  AS acc_u_id,"+
 "         ACCOUNTING_FOR_OFFICE_ID AS acc_off_id,"+
 "          CASHBOOK_YEAR            AS csh_bk_yr,"+
 "          CASHBOOK_MONTH           AS csh_bk_mnth,"+
 "          TO_ACCOUNT_NO               AS Acc_No,"+
 "          SUM(TOTAL_AMOUNT)        AS A_2a"+
 "         FROM FAS_INTER_BANK_TRF_AT_HO"+
 "         WHERE Accounting_Unit_Id    =  "+cboAcc_UnitCode+
 "        AND Accounting_For_Office_Id= "+cboOffice_code+
 "         AND Cashbook_Month          =  "+cboCashBook_Month+
 "         AND Cashbook_Year           = "+cboCashBook_Year+
 "        AND TO_ACCOUNT_NO              =  "+cmbBankAccNo+
 "         AND TRANSFER_STATUS       ='L'"+
 "      GROUP BY ACCOUNTING_UNIT_ID,        ACCOUNTING_FOR_OFFICE_ID,        CASHBOOK_YEAR,        CASHBOOK_MONTH,        TO_ACCOUNT_NO "+
	    "   union all "+
	 
	    
	    /*
	     * 
	     * 
	     * Joan chanaged on 23 Jun 2015
	     * 
	     * 
	     * 
	     * "   select ACCOUNTING_UNIT_ID AS acc_u_id, "+
	    " ACCOUNTING_FOR_OFFICE_ID AS acc_off_id, "+
        " CASHBOOK_YEAR            AS csh_bk_yr, "+
        "   CASHBOOK_MONTH           AS csh_bk_mnth, "+
        "   Account_No               AS Acc_No,sum(TOTAL_AMOUNT) AS A_2a from fas_receipt_master " +
        " where Accounting_Unit_Id    =  "+cboAcc_UnitCode+
        " AND Accounting_For_Office_Id= "+cboOffice_code+
        " AND Cashbook_Month          =  "+cboCashBook_Month+
        " AND Cashbook_Year           = "+cboCashBook_Year+
        " AND Account_No              = "+cmbBankAccNo+
        " and REMITTANCE_STATUS='Y' "+
        " and REMITTANCE_IN_CURR_MONTH='Y' group by ACCOUNTING_UNIT_ID, "+
        " ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,Account_No "+*/
        
        
  /*
   * 
   * 
   * Joan chanaged on 23 Jun 2015
   * 
   * 
   */
  " SELECT m.ACCOUNTING_UNIT_ID  AS acc_u_id, " +
"  m.ACCOUNTING_FOR_OFFICE_ID AS acc_off_id, " +
"  m.CASHBOOK_YEAR            AS csh_bk_yr, " +
"  m.CASHBOOK_MONTH           AS csh_bk_mnth, " +
"  m.Account_No               AS Acc_No, " +
"  SUM(t.Amount)              AS A_2a " +
" FROM FAS_receipt_master m, " +
"  FAS_RECEIPT_TRANSACTION t " +
" WHERE m.accounting_unit_id    =t.accounting_unit_id " +
" AND m.accounting_for_office_id=t.accounting_for_office_id " +
" AND m.cashbook_month          =t.cashbook_month " +
" AND m.Cashbook_Year           =t.Cashbook_Year " +
" AND m.RECEIPT_NO              =t.RECEIPT_NO " +
" AND m.accounting_unit_id      ="+cboAcc_UnitCode+
" AND m.accounting_for_office_id="+cboOffice_code+
" AND m.cashbook_month          = "+cboCashBook_Month+
" AND m.Cashbook_Year           = "+cboCashBook_Year+
" AND m.Account_No              = "+cmbBankAccNo+
" AND m.receipt_Status          ='L' " +
" AND m.REMITTANCE_STATUS       ='Y' " +
" AND m.REMITTANCE_IN_CURR_MONTH='Y' " +
//" AND t.ACCOUNT_HEAD_CODE NOT  IN (420428,420429,120201) " +
" GROUP BY m.Accounting_Unit_Id, " +
"  m.Accounting_For_Office_Id, " +
"  m.Cashbook_Year, " +
"  m.Cashbook_Month, " +
"  m.Account_No"+
        
        "      )\n" + 
        " group by acc_u_id,acc_off_id,csh_bk_yr,csh_bk_mnth,Acc_No "+
        "    )t2\n" + 
        "  ON T1.Acc_U_Id6     = T2.Acc_U_Id\n" + 
        "  AND T1.acc_off_id6  = T2.Acc_Off_Id\n" + 
        "  AND T1.csh_bk_yr6   = T2.Csh_Bk_Yr\n" + 
        "  AND T1.Csh_Bk_Mnth6 = T2.Csh_Bk_Mnth\n" + 
        "  AND T1.Acc_No6      = T2.Acc_No\n" + 
        "  )B\n" + 
        "ON A.Acc_U_Id    =B.Acc_U_Id6\n" + 
        "AND A.acc_off_id      =B.acc_off_id6\n" + 
        "AND A.csh_bk_yr       =B.csh_bk_yr6\n" + 
        "AND A.Csh_Bk_Mnth     =B.Csh_Bk_Mnth6\n" + 
        "And A.Acc_No          =B.Acc_No6\n" + 
        " LEFT OUTER JOIN\n" + 
        "      (SELECT ACCOUNTING_UNIT_ID AS acc_u_id1,\n" + 
        "        ACCOUNTING_FOR_OFFICE_ID AS acc_off_id1,\n" + 
        "        CASHBOOK_YEAR            AS csh_bk_yr1,\n" + 
        "        CASHBOOK_MONTH           AS csh_bk_mnth1,\n" + 
        "        ACCOUNT_NO               AS acc_no1,\n" + 
        "        SUM(CR_AMOUNT)           AS A_2b\n" + 
        "      FROM FAS_BRS_TRANSACTION_NOENTRY\n" + 
        "      WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
        "      AND accounting_for_office_id=" +cboOffice_code+ 
        "      AND cashbook_month          = " +cboCashBook_Month+ 
        "      AND cashbook_year           =" +cboCashBook_Year+ 
        "      AND ACCOUNT_NO              = " +cmbBankAccNo+ 
        "      AND doc_type                ='J'\n" + 
        "      GROUP BY ACCOUNTING_UNIT_ID,\n" + 
        "        ACCOUNTING_FOR_OFFICE_ID,\n" + 
        "        CASHBOOK_YEAR,\n" + 
        "        CASHBOOK_MONTH,\n" + 
        "        Account_No\n" + 
        "      )c\n" + 
        "    ON a.acc_u_id     =c.acc_u_id1\n" + 
        "    AND a.acc_off_id  = c.acc_off_id1\n" + 
        "    AND a.csh_bk_yr   = c.csh_bk_yr1\n" + 
        "    And A.Csh_Bk_Mnth = c.Csh_Bk_Mnth1\n" + 
        "    And A.Acc_No      = C.Acc_No1\n" + 
        "     LEFT OUTER JOIN\n" + 
        "      (SELECT ACCOUNTING_UNIT_ID AS acc_u_id2,\n" + 
        "        ACCOUNTING_FOR_OFFICE_ID AS acc_off_id2,\n" + 
        "        CASHBOOK_YEAR            AS csh_bk_yr2,\n" + 
        "        CASHBOOK_MONTH           AS csh_bk_mnth2,\n" + 
        "        ACCOUNT_NO               AS acc_no2,\n" + 
        "        SUM(DR_AMOUNT)           AS A_2d\n" + 
        "      FROM FAS_BRS_TRANSACTION_NOENTRY\n" + 
        "      WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
        "      AND accounting_for_office_id=" +cboOffice_code+ 
        "      AND cashbook_month          = " +cboCashBook_Month+ 
        "      AND cashbook_year           =" +cboCashBook_Year+ 
        "      AND ACCOUNT_NO              = " +cmbBankAccNo+ 
        "      AND doc_type                ='J'\n" + 
        "      GROUP BY ACCOUNTING_UNIT_ID,\n" + 
        "        ACCOUNTING_FOR_OFFICE_ID,\n" + 
        "        CASHBOOK_YEAR,\n" + 
        "        CASHBOOK_MONTH,\n" + 
        "        Account_No\n" + 
        "      )d\n" + 
        "    ON a.acc_u_id     =d.acc_u_id2\n" + 
        "    AND a.acc_off_id  = d.acc_off_id2\n" + 
        "    AND a.csh_bk_yr   = d.csh_bk_yr2\n" + 
        "    AND a.csh_bk_mnth = d.csh_bk_mnth2\n" + 
        "    AND a.acc_no      = d.acc_no2";
		
		File reportFile = null;
		String sql="";
		Map map = null;
		map = new HashMap();
		map.put("opr_node", opr_node);
		map.put("Bank", BAnk_name);
		map.put("Branch", Branch_name);
		try {
			System.out.println("calling servlet...");
			if(command.equalsIgnoreCase("brs_Freeze")){
				sql="SELECT p1.ACCOUNTING_UNIT_ID, " +
				"  unit.ACCOUNTING_UNIT_NAME, " +
				"  office.OFFICE_NAME, " +
				"  p1.ACCOUNTING_FOR_OFFICE_ID, " +
				"  p1.PASS_SHEET_YEAR, " +
				"  p1.PASS_SHEET_MONTH, " +
				"  TWAD_OR_NON_TWAD, " +
				"  p1.ACCOUNT_NO, " +
				"  p1.BANK_ID, " +
				"  (SELECT BANK_NAME FROM FAS_BANK_LIST Bname WHERE p1.BANK_ID=Bname.BANK_ID " +
				"  )AS BANK_NAME, " +
				"  (SELECT BRANCH_NAME " +
				"  FROM FAS_MST_BANK_BRANCHES branch " +
				"  WHERE p1.BANK_ID=branch.BANK_ID " +
				"  AND p1.BRANCH_ID=branch.BRANCH_ID " +
				"  )AS BRANCH_NAME, " +
				"  (SELECT PASSBOOK_BALANCE " +
				"  FROM FAS_BRS_MASTER pbk " +
				"  WHERE pbk.ACCOUNTING_UNIT_ID    =p1.ACCOUNTING_UNIT_ID " +
				"  AND pbk.ACCOUNTING_FOR_OFFICE_ID=p1.ACCOUNTING_FOR_OFFICE_ID " +
				"  AND pbk.CASHBOOK_YEAR           =p1.PASS_SHEET_YEAR " +
				"  AND pbk.CASHBOOK_MONTH          =p1.PASS_SHEET_MONTH " +
				"  AND pbk.ACCOUNT_NO              =p1.ACCOUNT_NO " +
				"  )AS PASSBOOK_BALANCE, " +
				"  S1, " +
				"  S2A, " +
				"  S2B, " +
				"  S2C, " +
				"  S3, " +
				"  S4, " +
				"  S5 " +
				"FROM FAS_BRS_PART_2B p1, " +
				"  FAS_MST_ACCT_UNITS unit, " +
				"  COM_MST_OFFICES office " +
				"WHERE p1.ACCOUNTING_UNIT_ID    =  " +cboAcc_UnitCode+
				"AND ACCOUNTING_FOR_OFFICE_ID   =  " +cboOffice_code+
				"AND PASS_SHEET_YEAR            =  " +cboCashBook_Year+
				"AND PASS_SHEET_MONTH           =  " +cboCashBook_Month+
				"AND ACCOUNT_NO                 =  " +cmbBankAccNo+
				"AND p1.ACCOUNTING_UNIT_ID      =unit.ACCOUNTING_UNIT_ID " +
				"AND p1.ACCOUNTING_FOR_OFFICE_ID=office.OFFICE_ID";
if(hid.equalsIgnoreCase("old")){
			reportFile = new File(getServletContext().getRealPath(
					"/org/FAS/FAS1/BRS/jaspers/Part_Jasper/BRS_Report_2B_Report.jasper"));}
else 
{
	reportFile = new File(getServletContext().getRealPath(
			"/org/FAS/FAS1/BRS/jaspers/Part_Jasper/FDW_PDF_BRS2B new.jasper"));
	
}

System.out.println("report fileeeeeeeeeeeeeeeeeeeee"+reportFile);

			map.put("sql", sql);
			if (!reportFile.exists())
				throw new JRRuntimeException("File J not found. The report design must be compiled first.");

			JasperReport jasperReport = (JasperReport) JRLoader
					.loadObject(reportFile.getPath());
			

			map.put("UnitId", cboAcc_UnitCode);
			map.put("OfficeId", cboOffice_code);
			map.put("cbyear", cboCashBook_Year);
			map.put("cbmonth", cboCashBook_Month);
			map.put("accNo", cmbBankAccNo);
			map.put("Bank", BAnk_name);
			map.put("Branch", Branch_name);
			map.put("month", month);
                        map.put("amount", ii);
		        map.put("UnitName", UnitName);
		    
		    
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					jasperReport, map, con);
			System.out.println("upto");
			String rtype = "PDF";// request.getParameter("cmbReportType");
			System.out.println(rtype);
			 if (rtype.equalsIgnoreCase("PDF")) {
				System.out.println(rtype);
				byte buf[] = JasperExportManager.exportReportToPdf(jasperPrint);
				System.out.println("Length  " + buf.length);
				response.setContentType("application/pdf");
				response.setContentLength(buf.length);
				// response.setHeader("content-disposition",
				// "inline;filename=OpenActionItems.pdf");
				// response.setContentType("application/force-download");

				response.setHeader("Content-Disposition",
						"attachment;filename=\"BRS_Part2B.pdf\"");
				OutputStream out = response.getOutputStream();
				out.write(buf, 0, buf.length);
				out.close();
			}
			}
			else if(command.equalsIgnoreCase("printFunc")){
				System.out.println("sql_qu:::"+sql_qu);
				try{
					if(hid.equalsIgnoreCase("old")){
				reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/BRS/jaspers/BRS_Report_2B.jasper"));}
				
					else{
						reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/BRS/jaspers/Part_Jasper/BRS_fdw_2B.jasper"));
					}
				if (!reportFile.exists())
					throw new JRRuntimeException("File J not found. The report design must be compiled first.");

				JasperReport jasperReport = (JasperReport) JRLoader
						.loadObject(reportFile.getPath());
				
				System.out.println("reportFile====>"+reportFile);
				map.put("UnitId", cboAcc_UnitCode);
				map.put("OfficeId", cboOffice_code);
				map.put("cbyear", cboCashBook_Year);
				map.put("cbmonth", cboCashBook_Month);
				map.put("accNo", cmbBankAccNo);
				map.put("month", month);
	            map.put("amount", ii);
		        map.put("UnitName", UnitName);
		        map.put("Bank", BAnk_name);
				map.put("Branch", Branch_name);
		    	map.put("sql", sql_qu);
			        
			    
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						jasperReport, map, con);
				
				String rtype = "PDF";
				
				 if (rtype.equalsIgnoreCase("PDF")) {
					System.out.println(rtype);
					byte buf[] = JasperExportManager.exportReportToPdf(jasperPrint);
					System.out.println("Length  " + buf.length);
					response.setContentType("application/pdf");
					response.setContentLength(buf.length);
					
					response.setHeader("Content-Disposition",
							"attachment;filename=\"BRS_Part2B.pdf\"");
					OutputStream out = response.getOutputStream();
					out.write(buf, 0, buf.length);
					out.close();
				}
				}
				catch (Exception ex) {
					String connectMsg = "Could not create the report "
							+ ex.getMessage() + " uu " + ex.getLocalizedMessage();
					System.out.println(connectMsg);
				}
			}
			
			
			else if(command.equalsIgnoreCase("f_brs_check"))
			{

				response.setContentType(CONTENT_TYPE);
				xml="<response><command>f_brs_check</command>";
				
				
				PrintWriter output = response.getWriter();
				
				System.out.println("command >> "+command);
				int insertCount=0,jk=0,	checkfre=0;
				//double amount=0.0;
				double four_cAmount=0.0;
				int unitcode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
				int offCode=Integer.parseInt(request.getParameter("cmbOffice_code"));
				int passYear=Integer.parseInt(request.getParameter("cboCashBook_Year"));
				int passMonth=Integer.parseInt(request.getParameter("cboCashBook_Month"));
				long accNo=Long.parseLong(request.getParameter("cmbBankAccNo"));
				
			
					try{
						    
						    
						    if(offCode==5000) {
					                try {
					               
					                
					              PreparedStatement  ps=con.prepareStatement("SELECT SUM(Amount)AS Amt\n" + 
					                " FROM Fas_Fund_Trf_From_Ho_Trn Trn,\n" + 
					                "  Fas_Fund_Trf_From_Ho_Master Mas\n" + 
					                " Where Mas.Accounting_Unit_Id=Trn.Accounting_Unit_Id\n" + 
					                " And Mas.Accounting_For_Office_Id=Trn.Accounting_For_Office_Id\n" + 
					                " And Mas.Cashbook_Year=Trn.Cashbook_Year\n" + 
					                " And Mas.Cashbook_Month=Trn.Cashbook_Month\n" + 
					                " And Mas.VOUCHER_NO=Trn.VOUCHER_NO\n" + 
					                " and trn.Transfer_To_Office_Id=?\n" + 
					                " AND trn.Cashbook_Year          =?\n" + 
					                " AND trn.Cashbook_Month         =?\n" + 
					                " AND trn.Office_Account_No      =?\n" + 
					                " And Mas.Transfer_Status        ='L'\n" + 
					                " and Transfered_To_Ho_Unit_Id=?");
					                ps.setInt(1,offCode);
					            
					                ps.setInt(2,passYear);
					                ps.setInt(3,passMonth);
					                ps.setLong(4,accNo);
					                ps.setInt(5,unitcode);
					               
					                //    System.out.println("cboOffice_code:::"+cboOffice_code+""+);
					               ResultSet rs=ps.executeQuery();
					                if(rs.next())
					                {
					                     amount=rs.getDouble("Amt");
					                }
					                
					                
					                }
					                catch (SQLException e) {
					                    System.out.println("SQL Exception -->"+e);
					                }
					            }
					            else {
					                try {
					               
					              PreparedStatement  ps=con.prepareStatement("SELECT SUM(Amount)AS Amt\n" + 
					                " FROM Fas_Fund_Trf_From_Ho_Trn Trn,\n" + 
					                "  Fas_Fund_Trf_From_Ho_Master Mas\n" + 
					                " Where Mas.Accounting_Unit_Id=Trn.Accounting_Unit_Id\n" + 
					                " And Mas.Accounting_For_Office_Id=Trn.Accounting_For_Office_Id\n" + 
					                " And Mas.Cashbook_Year=Trn.Cashbook_Year\n" + 
					                " And Mas.Cashbook_Month=Trn.Cashbook_Month\n" + 
					                " And Mas.VOUCHER_NO=Trn.VOUCHER_NO\n" + 
					                " and trn.Transfer_To_Office_Id=?\n" + 
					                " AND trn.Cashbook_Year          =?\n" + 
					                " AND trn.Cashbook_Month         =?\n" + 
					                " AND trn.Office_Account_No      =?\n" + 
					                " AND mas.TRANSFER_STATUS        ='L'");
					                ps.setInt(1,offCode);
					                ps.setInt(2,passYear);
					                ps.setInt(3,passMonth);
					                ps.setLong(4,accNo);
					                    
					               ResultSet rs=ps.executeQuery();
					                if(rs.next())
					                     amount=rs.getDouble("Amt");
					                
					                }
					                catch (SQLException e) {
					                    System.out.println("SQL Exception -->"+e);
					                }
					            }
					}
					catch(Exception ee)
					{
						System.out.println("exception in four_b"+ee);
					}
					System.out.println("amount  >>> "+amount);
						try{  
			   PreparedStatement ps1=con.prepareStatement("SELECT ACCOUNTING_UNIT_ID  AS acc_u_id5,\n" + 
			    	    "  ACCOUNTING_FOR_OFFICE_ID AS acc_off_id5,\n" + 
			    	    "  CASHBOOK_YEAR            AS csh_bk_yr5,\n" + 
			    	    "  CASHBOOK_MONTH           AS csh_bk_mnth5,\n" + 
			    	    "  Account_No               As Acc_No5,\n" + 
			    	    "  SUM(DR_AMOUNT) as four_c\n" + 
			    	    " FROM FAS_BRS_TRANSACTION\n" + 
			    	    " WHERE accounting_unit_id    = " + unitcode+
			    	    " AND accounting_for_office_id=" + offCode+" AND cashbook_year           ="+ passYear+
			    	    " AND ACCOUNT_NO              = " + accNo+
			    	    " AND cashbook_month          = " +passMonth+
			    	    " And Twad_Or_Non_Twad        ='NT'\n" + 
			    	    " and TRANSACTION_TYPE not in(3,23)\n" + 
			    	    " GROUP BY ACCOUNTING_UNIT_ID, ACCOUNTING_FOR_OFFICE_ID, CASHBOOK_YEAR, CASHBOOK_MONTH, Account_No");
			    	  ResultSet rs1=ps1.executeQuery();
			    	    if(rs1.next())
			                {
			                      four_cAmount=rs1.getDouble("four_c");
			    	        }    
						    
						}
						catch(Exception ee)
						{
							System.out.println("exception in four_c"+ee);
						}
				  System.out.println("four_cAmount >> "+four_cAmount);
				  System.out.println("sql_qu >> "+sql_qu);
	                try{
	               PreparedStatement pss=con.prepareStatement(sql_qu);
	               ResultSet rss=pss.executeQuery();
	             //Lakshmi 6Nov13
		               if(rss.next())
		               {
		            	  
		            	   
		            	   
		            	   //  PreparedStatement psta=con.prepareStatement("delete from FAS_BRS_PART_2B where ACCOUNTING_UNIT_ID="+cboAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cboOffice_code+" and PASS_SHEET_YEAR="+cboCashBook_Year+" and PASS_SHEET_MONTH="+cboCashBook_Month+" and ACCOUNT_NO="+cmbBankAccNo);
			            	 //  insertCount= psta.executeUpdate();
		            	   
		            	 
				            	  PreparedStatement psta=con.prepareStatement("select 'X' from FAS_BRS_PART_2B where ACCOUNTING_UNIT_ID="+cboAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cboOffice_code+" and PASS_SHEET_YEAR="+cboCashBook_Year+" and PASS_SHEET_MONTH="+cboCashBook_Month+" and ACCOUNT_NO="+cmbBankAccNo);
				            	  ResultSet rsss= psta.executeQuery();
		            	   if(rsss.next()){
		            		   checkfre=1;  
		            	   }
		            		   
		            		   
				              
				               if(checkfre==1)
				               {
				            	   con.commit();
									con.setAutoCommit(true);
//									sendMessage(response,"Already Part-2B Frozen  ","ok");
									xml=xml+"<flag>Already_Frozen</flag>";
				               }
				               else
				               {
				            	   xml=xml+"<flag>success</flag>";
				               }
		               }
		               
	                }
	                catch(Exception ee)
	                {
	                	System.out.println("exception in fetching query::::"+ee);
	                }
	               
	                xml = xml + "</response>";
					output.println(xml); 
					System.out.println("xml=====>"+xml);
			}
			
			else if(command.equalsIgnoreCase("f_brs"))
			{

				response.setContentType(CONTENT_TYPE);
				
				PrintWriter output = response.getWriter();
				
				System.out.println("command >> "+command);
				int insertCount=0,jk=0,	checkfre=0;
				//double amount=0.0;
				double four_cAmount=0.0;
				int unitcode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
				int offCode=Integer.parseInt(request.getParameter("cmbOffice_code"));
				int passYear=Integer.parseInt(request.getParameter("cboCashBook_Year"));
				int passMonth=Integer.parseInt(request.getParameter("cboCashBook_Month"));
				long accNo=Long.parseLong(request.getParameter("cmbBankAccNo"));
				
			
					try{
						    
						    
						    if(offCode==5000) {
					                try {
					               
					                
					              PreparedStatement  ps=con.prepareStatement("SELECT SUM(Amount)AS Amt\n" + 
					                " FROM Fas_Fund_Trf_From_Ho_Trn Trn,\n" + 
					                "  Fas_Fund_Trf_From_Ho_Master Mas\n" + 
					                " Where Mas.Accounting_Unit_Id=Trn.Accounting_Unit_Id\n" + 
					                " And Mas.Accounting_For_Office_Id=Trn.Accounting_For_Office_Id\n" + 
					                " And Mas.Cashbook_Year=Trn.Cashbook_Year\n" + 
					                " And Mas.Cashbook_Month=Trn.Cashbook_Month\n" + 
					                " And Mas.VOUCHER_NO=Trn.VOUCHER_NO\n" + 
					                " and trn.Transfer_To_Office_Id=?\n" + 
					                " AND trn.Cashbook_Year          =?\n" + 
					                " AND trn.Cashbook_Month         =?\n" + 
					                " AND trn.Office_Account_No      =?\n" + 
					                " And Mas.Transfer_Status        ='L'\n" + 
					                " and Transfered_To_Ho_Unit_Id=?");
					                ps.setInt(1,offCode);
					            
					                ps.setInt(2,passYear);
					                ps.setInt(3,passMonth);
					                ps.setLong(4,accNo);
					                ps.setInt(5,unitcode);
					               
					                //    System.out.println("cboOffice_code:::"+cboOffice_code+""+);
					               ResultSet rs=ps.executeQuery();
					                if(rs.next())
					                {
					                     amount=rs.getDouble("Amt");
					                }
					                
					                
					                }
					                catch (SQLException e) {
					                    System.out.println("SQL Exception -->"+e);
					                }
					            }
					            else {
					                try {
					               
					              PreparedStatement  ps=con.prepareStatement("SELECT SUM(Amount)AS Amt\n" + 
					                " FROM Fas_Fund_Trf_From_Ho_Trn Trn,\n" + 
					                "  Fas_Fund_Trf_From_Ho_Master Mas\n" + 
					                " Where Mas.Accounting_Unit_Id=Trn.Accounting_Unit_Id\n" + 
					                " And Mas.Accounting_For_Office_Id=Trn.Accounting_For_Office_Id\n" + 
					                " And Mas.Cashbook_Year=Trn.Cashbook_Year\n" + 
					                " And Mas.Cashbook_Month=Trn.Cashbook_Month\n" + 
					                " And Mas.VOUCHER_NO=Trn.VOUCHER_NO\n" + 
					                " and trn.Transfer_To_Office_Id=?\n" + 
					                " AND trn.Cashbook_Year          =?\n" + 
					                " AND trn.Cashbook_Month         =?\n" + 
					                " AND trn.Office_Account_No      =?\n" + 
					                " AND mas.TRANSFER_STATUS        ='L'");
					                ps.setInt(1,offCode);
					                ps.setInt(2,passYear);
					                ps.setInt(3,passMonth);
					                ps.setLong(4,accNo);
					                    
					               ResultSet rs=ps.executeQuery();
					                if(rs.next())
					                     amount=rs.getDouble("Amt");
					                
					                }
					                catch (SQLException e) {
					                    System.out.println("SQL Exception -->"+e);
					                }
					            }
					}
					catch(Exception ee)
					{
						System.out.println("exception in four_b"+ee);
					}
					System.out.println("amount  >>> "+amount);
						try{  
			   PreparedStatement ps1=con.prepareStatement("SELECT ACCOUNTING_UNIT_ID  AS acc_u_id5,\n" + 
			    	    "  ACCOUNTING_FOR_OFFICE_ID AS acc_off_id5,\n" + 
			    	    "  CASHBOOK_YEAR            AS csh_bk_yr5,\n" + 
			    	    "  CASHBOOK_MONTH           AS csh_bk_mnth5,\n" + 
			    	    "  Account_No               As Acc_No5,\n" + 
			    	    "  SUM(DR_AMOUNT) as four_c\n" + 
			    	    " FROM FAS_BRS_TRANSACTION\n" + 
			    	    " WHERE accounting_unit_id    = " + unitcode+
			    	    " AND accounting_for_office_id=" + offCode+" AND cashbook_year           ="+ passYear+
			    	    " AND ACCOUNT_NO              = " + accNo+
			    	    " AND cashbook_month          = " +passMonth+
			    	    " And Twad_Or_Non_Twad        ='NT'\n" + 
			    	    " and TRANSACTION_TYPE not in(3,23)\n" + 
			    	    " GROUP BY ACCOUNTING_UNIT_ID, ACCOUNTING_FOR_OFFICE_ID, CASHBOOK_YEAR, CASHBOOK_MONTH, Account_No");
			    	  ResultSet rs1=ps1.executeQuery();
			    	    if(rs1.next())
			                {
			                      four_cAmount=rs1.getDouble("four_c");
			    	        }    
						    
						}
						catch(Exception ee)
						{
							System.out.println("exception in four_c"+ee);
						}
				  System.out.println("four_cAmount >> "+four_cAmount);
				  System.out.println("sql_qu >> "+sql_qu);
	                try{
	               PreparedStatement pss=con.prepareStatement(sql_qu);
	               ResultSet rss=pss.executeQuery();
	             //Lakshmi 6Nov13
		               if(rss.next())
		               {
		            	  
		            	   
		            	   
		            	   //  PreparedStatement psta=con.prepareStatement("delete from FAS_BRS_PART_2B where ACCOUNTING_UNIT_ID="+cboAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cboOffice_code+" and PASS_SHEET_YEAR="+cboCashBook_Year+" and PASS_SHEET_MONTH="+cboCashBook_Month+" and ACCOUNT_NO="+cmbBankAccNo);
			            	 //  insertCount= psta.executeUpdate();
		            	   
		            	 
				            	  PreparedStatement psta=con.prepareStatement("select 'X' from FAS_BRS_PART_2B where ACCOUNTING_UNIT_ID="+cboAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cboOffice_code+" and PASS_SHEET_YEAR="+cboCashBook_Year+" and PASS_SHEET_MONTH="+cboCashBook_Month+" and ACCOUNT_NO="+cmbBankAccNo);
				            	  ResultSet rsss= psta.executeQuery();
		            	   if(rsss.next()){
		            		   checkfre=1;  
		            	   }
		            		   
		            		   else{
				            	   double s_two_c=amount-rss.getDouble("A_2a");
				            	   System.out.println("s_two_c:::"+s_two_c);
				            	   double s_three=rss.getDouble("OB_PART2B")+amount;
				            	   System.out.println("s_three::"+s_three);
				            	   double s_five=(rss.getDouble("OB_PART2B")+rss.getDouble("A_2a"))-rss.getDouble("A_2e");
				            	   System.out.println("insert starts:::"+s_five);
				            	 //  insertCount++;
				            	  PreparedStatement pss1=con.prepareStatement("insert into FAS_BRS_PART_2B (ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID," +
				            	   		"PASS_SHEET_YEAR,PASS_SHEET_MONTH,ACCOUNT_NO,S1,S2A,S2B,S2C,S3,S4,S5," +
				            	   		"UPDATED_BY_USER_ID,UPDATED_DATE,BANK_ID,BRANCH_ID) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				            	   pss1.setInt(1,unitcode);
				            	   pss1.setInt(2,offCode);
				            	   pss1.setInt(3,passYear);
				            	   pss1.setInt(4,passMonth);
				            	   pss1.setLong(5,accNo);
				            	   pss1.setDouble(6,rss.getDouble("OB_PART2B"));
				            	   pss1.setDouble(7,amount);
				            	   pss1.setDouble(8,rss.getDouble("A_2a"));
				            	   System.out.println(rss.getDouble("A_2a")+" >> "+amount);
				            	   pss1.setDouble(9,rss.getDouble("A_2a")-amount);
				            	   pss1.setDouble(10,s_three);
				            	   
				            	   pss1.setDouble(11,rss.getDouble("A_2e"));
				            	   pss1.setDouble(12,s_five);
				            	  
				            	   pss1.setString(13,update_user);
				                   pss1.setTimestamp(14,ts);
				                   pss1.setInt(15,bank_id);
				                   pss1.setInt(16,branch_id);
				                   jk=pss1.executeUpdate();
				                   System.out.println("value jk:::"+jk);
				            	 //  pss1.setInt(15,rss.getInt("total2"));
				                   if(jk>0)
					               {
					            	    con.commit();
										con.setAutoCommit(true);
//										sendMessage(response,"Records Inserted Successfully  ","ok");  
							              xml="<response><command><flag>success</flag></command>";

					               }
					               else
					               {
					            	    con.rollback();
										con.setAutoCommit(true);
//					            	    sendMessage(response,"Records Not Inserted into Part-2b ","ok"); 
										xml="<response><command><flag>failure</flag></command>";
					               }
				               }
				              
				               if(checkfre==1)
				               {
				            	   con.commit();
									con.setAutoCommit(true);
//									sendMessage(response,"Already Part-2B Frozen  ","ok");
									xml="<response><command><flag>Already_Frozen</flag></command>";
				               }
		               }
		               
	                }
	                catch(Exception ee)
	                {
	                	System.out.println("exception in fetching query::::"+ee);
	                }
	               
	                xml = xml + "</response>";
					output.println(xml); 
					System.out.println("xml=====>"+xml);
			}
			 
		} catch (Exception ex) {
			System.out.println("exception in fetching query::::"+ex);
		}

	}
	private void sendMessage(HttpServletResponse response, String msg,
			String bType) {
		try {
			String url = "org/Library/jsps/MessengerOkBack.jsp?message=" + msg
					+ "&button=" + bType;
			response.sendRedirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
