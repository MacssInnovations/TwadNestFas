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
 * Servlet implementation class BRSReport_Part2C_BS
 */
public class BRSReport_Part2C_BS extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public BRSReport_Part2C_BS() {
        super();
       
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
                Connection con = null;
                String UnitName=null,mode_id=null;
                String totalyear="";
                Double amount=0.00,brs_amtcr=0.00;
                int bank_id=0,branch_id=0;
	    BigDecimal ii  = null,brs_amt=null;
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
		    ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection

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
		int cboAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
		int cboOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
		int cboCashBook_Year = Integer.parseInt(request.getParameter("cboCashBook_Year"));
		int cboCashBook_Month = Integer.parseInt(request.getParameter("cboCashBook_Month"));
		String mode=request.getParameter("Mode");
		long cmbBankAccNo = Long.parseLong(request.getParameter("cmbBankAccNo"));
		 String cmd=request.getParameter("command");
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		try{
			PreparedStatement prep1=con.prepareStatement("select a.brs_amtcr,b.OB_PART2B, "+
		" (decode(a.brs_amtcr,null,0,a.brs_amtcr)+decode(b.OB_PART2B,null,0,b.OB_PART2B))as newtotal "+
					" from "+
					" (SELECT ACCOUNTING_UNIT_ID  AS acc_u_id, "+
					"   ACCOUNTING_FOR_OFFICE_ID AS acc_off_id, "+
					"   CASHBOOK_YEAR            AS csh_bk_yr, "+
					"   CASHBOOK_MONTH           AS csh_bk_mnth, "+
					"   Account_No               AS Acc_No, "+
					"   SUM(CR_AMOUNT)           AS brs_amtcr "+
					" FROM Fas_Brs_Transaction "+
					" WHERE Accounting_Unit_Id    = "+cboAcc_UnitCode+
					" AND Accounting_For_Office_Id= "+cboOffice_code+
					" AND Cashbook_Month          = "+cboCashBook_Month+
					" AND Cashbook_Year           = "+cboCashBook_Year+
					" AND Account_No              = "+cmbBankAccNo+
					" AND doc_type                ='FR by Office' "+
					" GROUP BY ACCOUNTING_UNIT_ID, "+
					"   ACCOUNTING_FOR_OFFICE_ID, "+
					"   CASHBOOK_YEAR, "+
					"   Cashbook_Month, "+
					"   Account_No)a "+
					" full outer join "+
					"   (SELECT ACCOUNTING_UNIT_ID AS acc_u_id6, "+
					"  ACCOUNTING_FOR_OFFICE_ID AS acc_off_id6, "+
					"  CASHBOOK_YEAR            AS csh_bk_yr6, "+
					"  CASHBOOK_MONTH           AS csh_bk_mnth6, "+
					"  Account_No               AS Acc_No6, "+
					"  OB_PART2A, "+
					" OB_PART2B "+
					"  FROM FAS_BRS_OB "+
					"  WHERE accounting_unit_id    = "+cboAcc_UnitCode+
					"  AND accounting_for_office_id=  "+cboOffice_code+
					"  AND cashbook_month          = "+cboCashBook_Month+
					"  AND cashbook_year           = "+cboCashBook_Year+
					"  AND ACCOUNT_NO              = "+cmbBankAccNo+")b "+
					"   on a.acc_u_id=b.acc_u_id6 "+
					"   and  a.acc_off_id=b.acc_off_id6 "+
					"  and  a.csh_bk_yr=b.csh_bk_yr6 "+
					"  and  a.csh_bk_mnth=b.csh_bk_mnth6 "+
					"  and  a.Acc_No=b.Acc_No6");
			ResultSet ress=prep1.executeQuery();
			if(ress.next())
			{
				brs_amtcr=ress.getDouble("newtotal");
				brs_amt = new BigDecimal(brs_amtcr);
			}
			
		}
		catch(Exception ee)
		{
			System.out.println("exce in brs entry:"+ee);
		}
		System.out.println("brs_amt::"+brs_amt);
		
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
                System.out.println("amount:::yyyyy:::"+rs.getString("Amt"));
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
                " AND mas.TRANSFER_STATUS        ='L'");
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
		
		
		try {
		    
		    
			 PreparedStatement  ps_l=con.prepareStatement("SELECT to_char(last_day(to_date(date1, 'dd-mm-yy')),'dd-mm-yyyy')ls_date "+
									"  FROM "+
			    		"   (SELECT DISTINCT ('01' "+
			    		" 		      ||'-' "+
			    		" 		      ||CASHBOOK_MONTH "+
			    		" 		      ||'-' "+
			    		" 		      ||CASHBOOK_YEAR)date1 "+
			    		" 		    FROM FAS_BRS_TRANSACTION "+
			    		" 		    WHERE CASHBOOK_YEAR   = "+cboCashBook_Year+
			    		" 		    AND CASHBOOK_MONTH    = "+cboCashBook_Month+
			    		" 		    AND account_no        = "+cmbBankAccNo+
			    		" 		    AND accounting_unit_id= "+cboAcc_UnitCode+" 		    )");
			    
			  ResultSet  rs_l=ps_l.executeQuery();
			    if(rs_l.next())
			    {
			    String  last_date_one =rs_l.getString("ls_date");
			    System.out.println("last_date_one::"+last_date_one);
			    String[] splto=last_date_one.split("-");
			  String smonth="";
			    if(splto[1].equals("01"))
			    {
			    	smonth="jan";
			    }
			    else if(splto[1].equals("02"))
			    {
			    	smonth="feb";
			    }else if(splto[1].equals("03"))
			    {
			    	smonth="mar";
			    }else if(splto[1].equals("04"))
			    {
			    	smonth="apr";
			    }else if(splto[1].equals("05"))
			    {
			    	smonth="may";
			    }else if(splto[1].equals("06"))
			    {
			    	smonth="jun";
			    }else if(splto[1].equals("07"))
			    {
			    	smonth="jul";
			    }else if(splto[1].equals("08"))
			    {
			    	smonth="aug";
			    }else if(splto[1].equals("09"))
			    {
			    	smonth="sep";
			    }else if(splto[1].equals("10"))
			    {
			    	smonth="oct";
			    }else if(splto[1].equals("11"))
			    {
			    	smonth="nov";
			    }else if(splto[1].equals("12"))
			    {
			    	smonth="dec";
			    }
			    totalyear=splto[0]+"-"+smonth+"-"+splto[2];
			    System.out.println("totalyear:::"+totalyear);
			   
			    }
			    
			    }
			    catch (SQLException e) {
			        System.out.println("SQL Exception -->"+e);
			    }
			    
			   /* try{
					String ac="SELECT bank_ac_no,AC_OPERATIONAL_MODE_ID,bank_id,branch_id From Fas_Mst_Bank_Balance WHERE accounting_unit_id = "+cboAcc_UnitCode+" and status='Y' and bank_ac_no="+cmbBankAccNo;
					PreparedStatement pss=con.prepareStatement(ac);
					ResultSet rss=pss.executeQuery();
					if(rss.next())
					{
						mode_id=rss.getString("AC_OPERATIONAL_MODE_ID");//opr
						bank_id=rss.getInt("bank_id");//bank_id
						branch_id=rss.getInt("branch_id");//branch_id
					}
				}
				catch (Exception e) {
					System.out.println("Error in mode_id -->" + e);
				}
			    
			    */
			    
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
					rss.close();
					pss.close();
				}
				catch (Exception e) {
					System.out.println("Error in mode_id -->" + e);
				}
			    
			    
			    
			    
			    
			    
			    
			    
			    
			    
			    String sql_que="";
			    mode=mode_id;
			    if(mode.equalsIgnoreCase("SCH")){/*
			    	sql_que="SELECT test1.acc_u_id_T, " +
			    			"  test1.acc_off_id_T, " +
			    			"  test1.csh_bk_yr_T, " +
			    			"  test1.csh_bk_mnth_T, " +
			    			"  test1.acc_no_T, " +
			    			"  test1.cr, " +
			    			"  test1.dr, " +
			    			"  test1.OB_PART1, " +
			    			"  TEST1.OB_ONE, " +
			    			"  TEST1.OB_TWO, " +
			    			"  TEST1.FR, " +
			    			"  TEST1.cheque_issue, " +
			    			"  test1.total, " +
			    			"  test1.row_num, " +
			    			"  test1.not_ack, " +
			    			"  test1.rem_2a, " +
			    			"  test1.rem_2b, " +
			    			"  test1.total_three, " +
			    			"  DECODE(test2.four_b,NULL,0,test2.four_b)              AS fourb, " +
			    			"  ((rem_2a)           +(DECODE(test2.four_b,NULL,0,test2.four_b)))AS total_five, " +
			    			"  ((test1.total_three)-((rem_2a)+(DECODE(test2.four_b,NULL,0,test2.four_b))))six_cb, " +
			    			"  test3.PASSBOOK_BALANCE,test33.com_charge,test33.TRANSACTION_TYPE,tran_type.TRANS_DESC, " +
			    			"  (((test1.total_three)-((rem_2a)+(DECODE(test2.four_b,NULL,0,test2.four_b))))-(PASSBOOK_BALANCE))AS eight " +
			    			" FROM " +
			    			"  (SELECT acc_u_id_T, " +
			    			"    acc_off_id_T, " +
			    			"    csh_bk_yr_T, " +
			    			"    csh_bk_mnth_T, " +
			    			"    acc_no_T, " +
			    			"    cr, " +
			    			"    dr, " +
			    			"    OB_PART1, " +
			    			"    OB_PART2B AS ob_two, " +
			    			"    OB_PART2C AS OB_ONE, " +
			    			"    fr, " +
			    			"    cheque_issue, " +
			    			"    total, " +
			    			"    row_num, " +
			    			"    not_ack, " +
			    			"    (total-(not_ack))rem_2a, " +
			    			"    amt_2t                                AS rem_2b, " +
			    			"    (OB_PART2C+(total-(not_ack))+(amt_2t))AS total_three " +
			    			"  FROM " +
			    			"    (SELECT one.*, " +
			    			"      two.amt_2t " +
			    			"    FROM " +
			    			"      ( " +
			    		//	"      ----Old One Strat Hree " +
			    		//	"      ----New  One Strat Hree " +
			    			"      SELECT xxx.acc_u_id_T, " +
			    			"        XXX.ACC_OFF_ID_T, " +
			    			"        xxx.csh_bk_yr_T, " +
			    			"        XXX.CSH_BK_MNTH_T, " +
			    			"        xxx.acc_no_T, " +
			    			"        xxx.cr, " +
			    			"        xxx.DR, " +
			    			"        XXX.OB_PART1, " +
			    			"        xxx.OB_PART2B, " +
			    			"        XXX.OB_PART2C, " +
			    			"        xxx.total, " +
			    			"        NVL(yyy.fr,0) AS fr, " +
			    			"        yyy.cheque_issue " +
			    			"      FROM " +
			    			"        (SELECT s2.acc_u_id_T, " +
			    			"          s2.acc_off_id_T, " +
			    			"          s2.csh_bk_yr_T, " +
			    			"          s2.csh_bk_mnth_T, " +
			    			"          s2.acc_no_T, " +
			    			"          s2.cr, " +
			    			"          s2.dr, " +
			    			"          S1.OB_PART1, " +
			    			"          s1.OB_PART2B, " +
			    			"          s1.OB_PART2C, " +
			    			"          (s1.OB_PART1+s2.cr)AS total " +
			    			"        FROM " +
			    			"          (SELECT ACCOUNTING_UNIT_ID          AS acc_u_id6, " +
			    			"            ACCOUNTING_FOR_OFFICE_ID          AS acc_off_id6, " +
			    			"            CASHBOOK_YEAR                     AS csh_bk_yr6, " +
			    			"            CASHBOOK_MONTH                    AS csh_bk_mnth6, " +
			    			"            Account_No                        AS Acc_No6, " +
			    			"            DECODE(OB_PART1,NULL,0,OB_PART1)  AS OB_PART1, " +
			    			"            DECODE(OB_PART2B,NULL,0,OB_PART2B)AS OB_PART2B, " +
			    			"            DECODE(OB_PART2C,NULL,0,OB_PART2C)AS OB_PART2C " +
			    			"          FROM FAS_BRS_OB " +
			    			"          WHERE accounting_unit_id    ="+cboAcc_UnitCode+
			    			"          AND accounting_for_office_id="+cboOffice_code+
			    			"          AND cashbook_month          = "+cboCashBook_Month+
			    			"          AND cashbook_year           = "+cboCashBook_Year+
			    			"          AND ACCOUNT_NO              = "+cmbBankAccNo+
			    			"          )s1 " +
			    			"        FULL OUTER JOIN " +
			    			"          (SELECT acc_u_id_T, " +
			    			"            acc_off_id_T, " +
			    			"            csh_bk_yr_T, " +
			    			"            csh_bk_mnth_T, " +
			    			"            acc_no_T, " +
			    			"            ibt   AS cr, " +
			    			"            dr_T1 AS dr " +
			    			"          FROM " +
			    			"            (SELECT ACCOUNTING_UNIT_ID AS acc_u_id_T, " +
			    			"              ACCOUNTING_FOR_OFFICE_ID AS acc_off_id_T, " +
			    			"              CASHBOOK_MONTH           AS csh_bk_mnth_T, " +
			    			"              CASHBOOK_YEAR            AS csh_bk_yr_T, " +
			    			"              SUM(TOTAL_AMOUNT)        AS ibt, " +
			    			cmbBankAccNo+"  AS acc_no_T " +
			    			"            FROM FAS_INTER_BANK_TRF_AT_HO " +
			    			"            WHERE ACCOUNTING_UNIT_ID     ="+cboAcc_UnitCode+
			    			"            AND ACCOUNTING_FOR_OFFICE_ID ="+cboOffice_code+
			    			"            AND CASHBOOK_MONTH           = "+cboCashBook_Month+
			    			"            AND CASHBOOK_YEAR            = "+cboCashBook_Year+
			    			"            AND (FROM_ACCOUNT_NO         = "+cmbBankAccNo+
			    			"            OR TO_ACCOUNT_NO             = "+cmbBankAccNo+") " +
			    			"            AND TRANSFER_STATUS          ='L' " +
			    			"            GROUP BY ACCOUNTING_UNIT_ID, " +
			    			"              ACCOUNTING_FOR_OFFICE_ID, " +
			    			"              CASHBOOK_MONTH, " +
			    			"              CASHBOOK_YEAR " +
			    			"            )a " +
			    			"          LEFT OUTER JOIN " +
			    			"            (SELECT SUM(dr_T1)AS dr_T1, " +
			    			"              acc_u_id_T1, " +
			    			"              acc_off_id_T1, " +
			    			"              csh_bk_yr_T1, " +
			    			"              csh_bk_mnth_T1, " +
			    			"              acc_no_T1 " +
			    			"            FROM " +
			    			"              (SELECT ACCOUNTING_UNIT_ID AS acc_u_id_T1, " +
			    			"                ACCOUNTING_FOR_OFFICE_ID AS acc_off_id_T1, " +
			    			"                CASHBOOK_YEAR            AS csh_bk_yr_T1, " +
			    			"                CASHBOOK_MONTH           AS csh_bk_mnth_T1, " +
			    			"                ACCOUNT_NO               AS acc_no_T1, " +
			    			"                SUM(DR_AMOUNT)           AS dr_T1 " +
			    			"              FROM FAS_BRS_TRANSACTION " +
			    			"              WHERE accounting_unit_id    ="+cboAcc_UnitCode+
			    			"              AND accounting_for_office_id="+cboOffice_code+
			    			"              AND cashbook_month          = "+cboCashBook_Month+
			    			"              AND cashbook_year           = "+cboCashBook_Year+
			    			"              AND ACCOUNT_NO              ="+cmbBankAccNo+
			    			"              AND doc_type                ='IBT' " +
			    			"              GROUP BY ACCOUNTING_UNIT_ID, " +
			    			"                ACCOUNTING_FOR_OFFICE_ID, " +
			    			"                CASHBOOK_YEAR, " +
			    			"                CASHBOOK_MONTH, " +
			    			"                ACCOUNT_NO " +
			    			"              UNION ALL " +
			    			"              SELECT ACCOUNTING_UNIT_ID  AS acc_u_id_T1, " +
			    			"                ACCOUNTING_FOR_OFFICE_ID AS acc_off_id_T1, " +
			    			"                CASHBOOK_YEAR            AS csh_bk_yr_T1, " +
			    			"                CASHBOOK_MONTH           AS csh_bk_mnth_T1, " +
			    			"                ACCOUNT_NO               AS acc_no_T1, " +
			    			"                SUM(DR_AMOUNT)           AS dr_T1 " +
			    			"              FROM fas_brs_transaction_noentry " +
			    			"              WHERE accounting_unit_id    ="+cboAcc_UnitCode+
			    			"              AND accounting_for_office_id="+cboOffice_code+
			    			"              AND cashbook_month          = "+cboCashBook_Month+
			    			"              AND cashbook_year           = "+cboCashBook_Year+
			    			"              AND ACCOUNT_NO              ="+cmbBankAccNo+
			    			"              AND doc_type                ='IBT' " +
			    			"              GROUP BY ACCOUNTING_UNIT_ID, " +
			    			"                ACCOUNTING_FOR_OFFICE_ID, " +
			    			"                CASHBOOK_YEAR, " +
			    			"                CASHBOOK_MONTH, " +
			    			"                ACCOUNT_NO " +
			    			"              ) " +
			    			"            GROUP BY acc_u_id_T1, " +
			    			"              acc_off_id_T1, " +
			    			"              csh_bk_yr_T1, " +
			    			"              csh_bk_mnth_T1, " +
			    			"              acc_no_T1 " +
			    			"            )c " +
			    			"          ON a.acc_u_id_T     =c.acc_u_id_T1 " +
			    			"          AND a.acc_off_id_T  = c.acc_off_id_T1 " +
			    			"          AND a.csh_bk_yr_T   = c.csh_bk_yr_T1 " +
			    			"          AND a.csh_bk_mnth_T = c.csh_bk_mnth_T1 " +
			    			"          AND a.acc_no_T      = c.acc_no_T1 " +
			    			"          )s2 ON s1.acc_u_id6 =s2.acc_u_id_T " +
			    			"        AND s1.acc_off_id6    =s2.acc_off_id_T " +
			    			"        AND s1.csh_bk_yr6     =s2.csh_bk_yr_T " +
			    			"        AND s1.csh_bk_mnth6   =s2.csh_bk_mnth_T " +
			    			"        AND S1.ACC_NO6        =S2.ACC_NO_T " +
			    			"        )xxx " +
			    		//	"        ----New  One Strat Hree " +
			    			"      LEFT OUTER JOIN " +
			    			"        (SELECT fr, " +
			    			"          cheque_issue, " +
			    			"          b.acc_u_id, " +
			    			"          b.acc_off_id, " +
			    			"          b.csh_bk_yr, " +
			    			"          b.csh_bk_mnth, " +
			    			"          b.Acc_No " +
			    			"        FROM " +
			    			"          (SELECT ACCOUNTING_UNIT_ID AS acc_u_id, " +
			    			"            ACCOUNTING_FOR_OFFICE_ID AS acc_off_id, " +
			    			"            CASHBOOK_YEAR            AS csh_bk_yr, " +
			    			"            CASHBOOK_MONTH           AS csh_bk_mnth, " +
			    			"            911101391061             AS Acc_No, " +
			    			"            SUM(TOTAL_AMOUNT)        AS fr " +
			    			"          FROM FAS_INTER_BANK_TRF_AT_HO " +
			    			"          WHERE ACCOUNTING_UNIT_ID     ="+cboAcc_UnitCode+
			    			"          AND ACCOUNTING_FOR_OFFICE_ID ="+cboOffice_code+
			    			"          AND CASHBOOK_MONTH           = "+cboCashBook_Month+
			    			"          AND CASHBOOK_YEAR            = "+cboCashBook_Year+
			    			"          AND (TO_ACCOUNT_NO           = "+cmbBankAccNo+") " +
			    			"          AND TRANSFER_STATUS          ='L' " +
			    			"          GROUP BY ACCOUNTING_UNIT_ID, " +
			    			"            ACCOUNTING_FOR_OFFICE_ID, " +
			    			"            CASHBOOK_MONTH, " +
			    			"            CASHBOOK_YEAR " +
			    			"          )a " +
			    			"        FULL OUTER JOIN " +
			    			"          (SELECT ACCOUNTING_UNIT_ID AS acc_u_id, " +
			    			"            ACCOUNTING_FOR_OFFICE_ID AS acc_off_id, " +
			    			"            CASHBOOK_YEAR            AS csh_bk_yr, " +
			    			"            CASHBOOK_MONTH           AS csh_bk_mnth, " +
			    			"            911101391061             AS Acc_No, " +
			    			"            SUM(TOTAL_AMOUNT)        AS cheque_issue " +
			    			"          FROM FAS_INTER_BANK_TRF_AT_HO " +
			    			"          WHERE ACCOUNTING_UNIT_ID     ="+cboAcc_UnitCode+
			    			"          AND ACCOUNTING_FOR_OFFICE_ID ="+cboOffice_code+
			    			"          AND CASHBOOK_MONTH           = "+cboCashBook_Month+
			    			"          AND CASHBOOK_YEAR            = "+cboCashBook_Year+
			    			"          AND (FROM_ACCOUNT_NO         = "+cmbBankAccNo+") " +
			    			"          AND TRANSFER_STATUS          ='L' " +
			    			"          GROUP BY ACCOUNTING_UNIT_ID, " +
			    			"            ACCOUNTING_FOR_OFFICE_ID, " +
			    			"            CASHBOOK_MONTH, " +
			    			"            CASHBOOK_YEAR " +
			    			"          )b " +
			    			"        ON a.acc_u_id         =b.acc_u_id " +
			    			"        AND a.acc_off_id      =b.acc_off_id " +
			    			"        AND a.csh_bk_yr       =b.csh_bk_yr " +
			    			"        AND A.CSH_BK_MNTH     =B.CSH_BK_MNTH " +
			    			"        AND A.ACC_NO          =B.ACC_NO " +
			    			"        )YYY ON XXx.ACC_U_ID_T=YyY.ACC_U_ID " +
			    			"      AND XXX.ACC_OFF_ID_T    =YYY.ACC_OFF_ID " +
			    			"      AND XXX.CSH_BK_YR_T     =YYY.CSH_BK_YR " +
			    			"      AND XXX.CSH_BK_MNTH_T   =YYY.CSH_BK_MNTH " +
			    		//	"        ---- One End Hree " +
			    			"      )one " +
			    			"    LEFT OUTER JOIN " +
			    			"      (SELECT rownum AS t2_sl_no, " +
			    			"        amt_2t, " +
			    			"        2014AS cbyear, " +
			    			"        4 AS cbmonth, " +
			    			"        auid_2T_5A, " +
			    			"        aoid_2T_5A, " +
			    			"        ano_2T_5A " +
			    			"      FROM " +
			    			"        (SELECT SUM (amt_2t)AS amt_2t, " +
			    			"          auid_2T_5A, " +
			    			"          aoid_2T_5A, " +
			    			"          ano_2T_5A " +
			    			"        FROM " +
			    			"          (SELECT DECODE( (SUM(dr_amount) - SUM(cr_amount)),NULL,0,(SUM(dr_amount) -SUM(cr_amount)) ) AS amt_2t, " +
			    			"            transaction_type, " +
			    			"            accounting_unit_id       AS auid_2T_5A, " +
			    			"            accounting_for_office_id AS aoid_2T_5A, " +
			    			"            cashbook_month           AS cbm_2T_5A, " +
			    			"            cashbook_year            AS cby_2T_5A, " +
			    			"            ACCOUNT_NO               AS ano_2T_5A " +
			    			"          FROM fas_brs_transaction " +
			    			"          WHERE accounting_unit_id        ="+cboAcc_UnitCode+
			    			"          AND accounting_for_office_id    ="+cboOffice_code+
			    			"          AND ((cashbook_year             < "+cboCashBook_Year+
			    			"          AND cashbook_month             <=12) " +
			    			"          OR (cashbook_year               = "+cboCashBook_Year+
			    			"          AND cashbook_month             <="+cboCashBook_Month+")) " +
			    			"          AND ACCOUNT_NO                  = "+cmbBankAccNo+
			    			"          AND twad_or_non_twad            = 'NT' " +
			    			"          AND (CLEARED_BASED_ON_FOLLOWUP IS NULL " +
			    			"          OR CLEARED_BASED_ON_FOLLOWUP    ='N') " +
			    			"          GROUP BY transaction_type, " +
			    			"            accounting_unit_id, " +
			    			"            accounting_for_office_id, " +
			    			"            cashbook_month, " +
			    			"            cashbook_year, " +
			    			"            ACCOUNT_NO " +
			    			"          UNION ALL " +
			    			"          SELECT DECODE( (SUM(dr_amount) - SUM(cr_amount)),NULL,0,(SUM(dr_amount) -SUM(cr_amount)) ) AS amt_2t, " +
			    			"            transaction_type, " +
			    			"            accounting_unit_id       AS auid_2T_5A, " +
			    			"            accounting_for_office_id AS aoid_2T_5A, " +
			    			"            cashbook_month           AS cbm_2T_5A, " +
			    			"            cashbook_year            AS cby_2T_5A, " +
			    			"            ACCOUNT_NO               AS ano_2T_5A " +
			    			"          FROM fas_brs_transaction " +
			    			"          WHERE accounting_unit_id      ="+cboAcc_UnitCode+
			    			"          AND accounting_for_office_id  ="+cboOffice_code+
			    			"          AND ((cashbook_year           < "+cboCashBook_Year+
			    			"          AND cashbook_month           <=12) " +
			    			"          OR (cashbook_year             = "+cboCashBook_Year+
			    			"          AND cashbook_month           <="+cboCashBook_Month+")) " +
			    			"          AND ACCOUNT_NO                = "+cmbBankAccNo+
			    			"          AND twad_or_non_twad          = 'NT' " +
			    			"          AND (CLEARED_BASED_ON_FOLLOWUP='Y' " +
			    			"          AND clearence_date            >('31-oct-2012')) " +
			    			"          GROUP BY transaction_type, " +
			    			"            accounting_unit_id, " +
			    			"            accounting_for_office_id, " +
			    			"            cashbook_month, " +
			    			"            cashbook_year, " +
			    			"            ACCOUNT_NO " +
			    			"          ) " +
			    			"        GROUP BY auid_2T_5A, " +
			    			"          aoid_2T_5A, " +
			    			"          ano_2T_5A " +
			    			"        ) t2_c " +
			    			"      )two " +
			    			"    ON one.acc_u_id_T     =two.auid_2T_5A " +
			    			"    AND one.acc_off_id_T  =two.aoid_2T_5A " +
			    			"    AND one.csh_bk_yr_T   =two.cbyear " +
			    			"    AND one.csh_bk_mnth_T =two.cbmonth " +
			    			"    AND one.acc_no_T      =two.ano_2T_5A " +
			    			"    ), " +
			    			"    (SELECT rownum AS row_num, " +
			    			"      not_ack " +
			    			"    FROM " +
			    			"      (SELECT SUM(amt_t2_A) AS not_ack " +
			    			"      FROM " +
			    			"        (SELECT ( SUM(cr_amount) - SUM(dr_amount) ) AS amt_t2_A " +
			    			"        FROM FAS_BRS_TRANSACTION_NOENTRY " +
			    			"        WHERE accounting_unit_id     ="+cboAcc_UnitCode+
			    			"        AND accounting_for_office_id ="+cboOffice_code+
			    			"        AND ((cashbook_year          < "+cboCashBook_Year+
			    			"        AND cashbook_month          <=12) " +
			    			"        OR (cashbook_year            = "+cboCashBook_Year+
			    			"        AND cashbook_month          <="+cboCashBook_Month+")) " +
			    			"        AND ACCOUNT_NO               = "+cmbBankAccNo+
			    			"        AND TWAD_OR_NON_TWAD         ='T' " +
			    			"        UNION ALL " +
			    			"        SELECT ( SUM(cr_amount) - SUM(dr_amount) ) AS amt_t2_A " +
			    			"        FROM FAS_BRS_TRANSACTION " +
			    			"        WHERE accounting_unit_id     ="+cboAcc_UnitCode+
			    			"        AND Accounting_For_Office_Id ="+cboOffice_code+
			    			"        AND PASSBOOK_DATE            >('31-oct-2012') " +
			    			"        AND (01 " +
			    			"          ||'-' " +
			    			"          ||Cashbook_Month " +
			    			"          ||'-' " +
			    			"          ||cashbook_year)  <=('31-oct-2012') " +
			    			"        AND ((cashbook_year  < "+cboCashBook_Year+
			    			"        AND cashbook_month  <=12) " +
			    			"        OR (cashbook_year    = "+cboCashBook_Year+
			    			"        AND cashbook_month  <="+cboCashBook_Month+")) " +
			    			"        AND Account_No       ="+cmbBankAccNo+
			    			"        AND Twad_Or_Non_Twad ='T' " +
			    			"        ) " +
			    			"      ) " +
			    			"    ) " +
			    			"  )test1 " +
			    			" LEFT OUTER JOIN " +
			    			"  (SELECT ACCOUNTING_UNIT_ID AS acc_u_id5, " +
			    			"    ACCOUNTING_FOR_OFFICE_ID AS acc_off_id5, " +
			    			"    Account_No               AS Acc_No5, " +
			    			"    2014as year1, " +
			    			"    4as month1, " +
			    			"    SUM(DR_AMOUNT) AS four_b " +
			    			"  FROM FAS_BRS_TRANSACTION " +
			    			"  WHERE accounting_unit_id              ="+cboAcc_UnitCode+
			    			"  AND accounting_for_office_id          ="+cboOffice_code+
			    			"  AND ACCOUNT_NO                        = "+cmbBankAccNo+
			    			"  AND (extract(YEAR FROM PASSBOOK_DATE) < "+cboCashBook_Year+
			    			"  OR (Extract(YEAR FROM Passbook_Date)  = "+cboCashBook_Year+
			    			"  AND extract(MONTH FROM PASSBOOK_DATE)<="+cboCashBook_Month+")) " +
			    			"  AND Twad_Or_Non_Twad                  ='NT' " +
			    			"  AND TRANSACTION_TYPE                 IN(3,23,12) " +
			    			"  GROUP BY ACCOUNTING_UNIT_ID, " +
			    			"    ACCOUNTING_FOR_OFFICE_ID, " +
			    			"    Account_No " +
			    			"  )test2 " +
			    			" ON test1.acc_u_id_T    =test2.acc_u_id5 " +
			    			" AND test1.acc_off_id_T =test2.acc_off_id5 " +
			    			" AND test1.csh_bk_yr_T  =test2.year1 " +
			    			" AND test1.csh_bk_mnth_T=test2.month1 " +
			    			" AND test1.acc_no_T     =test2.Acc_No5 " +
					// Commision charge Add here

					"  LEFT OUTER JOIN  "
					+ "   (SELECT ACCOUNTING_UNIT_ID AS acc_u_id5,  "
					+ " 	    ACCOUNTING_FOR_OFFICE_ID AS acc_off_id5,  "
					+ " 	    ACCOUNT_NO               AS ACC_NO5,  "
					+ " 	    2014 AS YEAR1,  "
					+ " 	    4 AS MONTH1, TRANSACTION_TYPE, "
					+ " 		    			    SUM(DR_AMOUNT) AS Com_charge  "
					+ " 		    			  FROM FAS_BRS_TRANSACTION  "
					+ " 		    			  WHERE accounting_unit_id               ="+cboAcc_UnitCode
					+ " 		    			  AND accounting_for_office_id         ="+cboOffice_code
					+ "     			  AND ACCOUNT_NO                        =  "+cmbBankAccNo
					+ "     			  AND (extract(YEAR FROM PASSBOOK_DATE) <"+cboCashBook_Year
					+ "     			  OR (Extract(YEAR FROM Passbook_Date)  ="+cboCashBook_Year
					+ "     			  AND extract(MONTH FROM PASSBOOK_DATE)<="+cboCashBook_Month+"))  "
					+ "     			  AND Twad_Or_Non_Twad                  ='NT'  "
					+ "     			  AND TRANSACTION_TYPE                 IN(6,12,24,2)  "
					+ "     			  GROUP BY ACCOUNTING_UNIT_ID,  "
					+ "     			    ACCOUNTING_FOR_OFFICE_ID,  "
					+ "     			    ACCOUNT_NO ,TRANSACTION_TYPE "
					+ "     			  )TEST33  "
					+ "     			 ON test1.acc_u_id_T    =TEST33.acc_u_id5  "
					+ "     			 AND test1.acc_off_id_T =TEST33.acc_off_id5  "
					+ "     			 AND test1.csh_bk_yr_T  =TEST33.year1  "
					+ " 		 AND TEST1.CSH_BK_MNTH_T=TEST33.MONTH1  "
					+ "     			 AND TEST1.ACC_NO_T     =TEST33.ACC_NO5  "
					+
			    			
			    			
			    			
			    			" LEFT OUTER JOIN " +
			    			"  (SELECT ACCOUNTING_UNIT_ID, " +
			    			"    ACCOUNT_NO, " +
			    			"    PASSBOOK_BALANCE " +
			    			"  FROM fas_brs_master " +
			    			"  WHERE ACCOUNTING_UNIT_ID     ="+cboAcc_UnitCode+
			    			"  AND accounting_for_office_id ="+cboOffice_code+
			    			"  AND ACCOUNT_NO               ="+cmbBankAccNo+
			    			"  AND CASHBOOK_YEAR            ="+cboCashBook_Year+
			    			"  AND CASHBOOK_MONTH           ="+cboCashBook_Month+
			    			"  )test3 " +
			    			" ON test1.acc_u_id_T=test3.ACCOUNTING_UNIT_ID " +
			    			" AND test1.acc_no_T =test3.ACCOUNT_NO 	 LEFT OUTER JOIN  "
			    			+ "  ( select TRANS_CODE,TRANS_DESC from FAS_BRS_TRANSACTION_TYPE )tran_type on test33.TRANSACTION_TYPE=tran_type.TRANS_CODE ";
			    */
			    	
			    sql_que="SELECT a.*, " +
			    		//"  --  DECODE(b.OB_PART2B,NULL,0,b.OB_PART2B)                             AS OB_PART2B, " +
			    		//"  --  DECODE(B.A_2a,NULL,0,b.A_2a)                                       AS A_2a, " +
			    		//"  --  DECODE(A_2b,NULL,'0',A_2b)                                         AS A_2b, " +
			    		//"  --  Only_4+(DECODE(A_2b,NULL,'0',A_2b))                                AS A_2c, " +
			    		//"  --  DECODE(A_2d,NULL,'0',A_2d)                                         AS A_2d, " +
			    	//	"  --  (only_4+(DECODE(A_2b,NULL,'0',A_2b)))-(DECODE(A_2d,NULL,'0',A_2d)) AS A_2e, " +
			    		"  DECODE(b.OB_PART2B,NULL,0,b.OB_PART2B) - (only_4+(DECODE(A_2b,NULL,'0',A_2b)))-(DECODE(A_2d,NULL,'0',A_2d)) AS res, " +
			    		"  B.Bname, " +
			    		"  b.branchName, " +
			    		"  (SELECT PASSBOOK_BALANCE " +
			    		"  FROM " +
			    		"    (SELECT rownum AS slno2, " +
			    		"      BANK_ID, " +
			    		"      BRANCH_ID, " +
			    		"      PASSBOOK_BALANCE " +
			    		"    FROM FAS_BRS_MASTER " +
			    		"    WHERE accounting_unit_id    = " +cboAcc_UnitCode+
			    		"    AND accounting_for_office_id=  " +cboOffice_code+
			    		"    AND cashbook_month          =  " +cboCashBook_Month+
			    		"    AND cashbook_year           = " +cboCashBook_Year+
			    		"    AND ACCOUNT_NO              = " +cmbBankAccNo+
			    		"    )sss " +
			    		"  LEFT OUTER JOIN " +
			    		"    (SELECT BRANCH_ID AS brnch_id, " +
			    		"      BANK_ID         AS bnk_id, " +
			    		"      BRANCH_NAME " +
			    		"    FROM FAS_MST_BANK_BRANCHES " +
			    		"    )c " +
			    		"  ON sss.BANK_ID    = c.bnk_id " +
			    		"  AND sss.BRANCH_ID = c.brnch_id " +
			    		"  LEFT OUTER JOIN " +
			    		"    (SELECT BANK_ID AS bnk_id1,BANK_NAME FROM FAS_BANK_LIST " +
			    		"    )d " +
			    		"  ON sss.BANK_ID = d.bnk_id1 " +
			    		"  ) AS PASSBOOK_BALANCE " +
			    		"FROM " +
			    		"  (SELECT xx.acc_u_id, " +
			    		"    xx. acc_off_id, " +
			    		"    xx. csh_bk_yr, " +
			    		"    xx. csh_bk_mnth, " +
			    		"    xx. acc_no, " +
			    		"    only_4 " +
			    		"  FROM " +
			    		"    (SELECT "+cboAcc_UnitCode+" AS acc_u_id, " +
			    		cboOffice_code+"          AS acc_off_id, " +
			    		cboCashBook_Year+"          AS csh_bk_yr, " +
			    		cboCashBook_Month+"       csh_bk_mnth, " +
			    		cmbBankAccNo+"       acc_no " +
			    		"     " +
			    		"    )xx " +
			    		"  LEFT OUTER JOIN " +
			    		"    (SELECT acc_u_id, " +
			    		"      acc_off_id, " +
			    		"      csh_bk_yr, " +
			    		"      csh_bk_mnth, " +
			    		"      acc_no, " +
			    		"      SUM(only_4) AS only_4 " +
			    		"    FROM " +
			    		"      (SELECT ACCOUNTING_UNIT_ID AS acc_u_id, " +
			    		"        ACCOUNTING_FOR_OFFICE_ID AS acc_off_id, " +
			    		"        CASHBOOK_YEAR            AS csh_bk_yr, " +
			    		"        CASHBOOK_MONTH           AS csh_bk_mnth, " +
			    		"        Account_No               AS Acc_No, " +
			    		"        SUM(total_amount)        AS only_4 " +
			    		"      FROM FAS_payment_master " +
			    		"      WHERE accounting_unit_id    =  " +cboAcc_UnitCode+
			    		"      AND accounting_for_office_id= " +cboOffice_code+
			    		"      AND cashbook_month          =  " +cboCashBook_Month+
			    		"      AND Cashbook_Year           = " +cboCashBook_Year+
			    		"      AND Account_No              = "+cmbBankAccNo+" AND Payment_Status ='L' " +
			    		"      GROUP BY Accounting_Unit_Id, " +
			    		"        Accounting_For_Office_Id, " +
			    		"        Cashbook_Year, " +
			    		"        Cashbook_Month, " +
			    		"        ACCOUNT_NO " +
			    		"      UNION ALL " +
			    		"      SELECT ACCOUNTING_UNIT_ID  AS acc_u_id, " +
			    		"        ACCOUNTING_FOR_OFFICE_ID AS acc_off_id, " +
			    		"        CASHBOOK_YEAR            AS csh_bk_yr, " +
			    		"        CASHBOOK_MONTH           AS csh_bk_mnth, " +
			    		"        Account_No               AS Acc_No, " +
			    		"        SUM(Total_Amount)        AS only_4 " +
			    		"      FROM FAS_receipt_master " +
			    		"      WHERE accounting_unit_id    =  " +cboAcc_UnitCode+
			    		"      AND accounting_for_office_id= " +cboOffice_code+
			    		"      AND cashbook_month          =  " +cboCashBook_Month+
			    		"      AND Cashbook_Year           = " +cboCashBook_Year+
			    		"      AND Account_No              =  " +cmbBankAccNo+
			    		"      AND receipt_Status          ='L' " +
			    		"      AND CREATED_BY_MODULE       ='SC' " +
			    		"      GROUP BY Accounting_Unit_Id, " +
			    		"        Accounting_For_Office_Id, " +
			    		"        Cashbook_Year, " +
			    		"        Cashbook_Month, " +
			    		"        Account_No " +
			    		"      UNION ALL " +
			    		"      SELECT ACCOUNTING_UNIT_ID  AS acc_u_id, " +
			    		"        ACCOUNTING_FOR_OFFICE_ID AS acc_off_id, " +
			    		"        CASHBOOK_YEAR            AS csh_bk_yr, " +
			    		"        CASHBOOK_MONTH           AS csh_bk_mnth, " +
			    		"        OFFICE_ACCOUNT_NO        AS Acc_No, " +
			    		"        SUM(Total_Amount)        AS only_4 " +
			    		"      FROM FAS_FUND_TRF_FROM_OFFICE " +
			    		"      WHERE accounting_unit_id    =  " +cboAcc_UnitCode+
			    		"      AND accounting_for_office_id= " +cboOffice_code+
			    		"      AND cashbook_month          =  " +cboCashBook_Month+
			    		"      AND Cashbook_Year           = " +cboCashBook_Year+
			    		"      AND OFFICE_ACCOUNT_NO       =  " +cmbBankAccNo+
			    		"      AND TRANSFER_STATUS        <> 'C' " +
			    		"      GROUP BY Accounting_Unit_Id, " +
			    		"        Accounting_For_Office_Id, " +
			    		"        Cashbook_Year, " +
			    		"        Cashbook_Month, " +
			    		"        OFFICE_ACCOUNT_NO " +
			    		"      UNION ALL " +
			    		"      SELECT ACCOUNTING_UNIT_ID  AS acc_u_id, " +
			    		"        ACCOUNTING_FOR_OFFICE_ID AS acc_off_id, " +
			    		"        CASHBOOK_YEAR            AS csh_bk_yr, " +
			    		"        CASHBOOK_MONTH           AS csh_bk_mnth, " +
			    		"        FROM_ACCOUNT_NO          AS Acc_No, " +
			    		"        SUM(Total_Amount)        AS only_4 " +
			    		"      FROM FAS_INTER_BANK_TRF_AT_HO " +
			    		"      WHERE accounting_unit_id    =  " +cboAcc_UnitCode+
			    		"      AND accounting_for_office_id= " +cboOffice_code+
			    		"      AND cashbook_month          =  " +cboCashBook_Month+
			    		"      AND Cashbook_Year           = " +cboCashBook_Year+
			    		"      AND FROM_ACCOUNT_NO         = " +cmbBankAccNo+
			    		"      AND TRANSFER_STATUS        <> 'C' " +
			    		"      GROUP BY Accounting_Unit_Id, " +
			    		"        Accounting_For_Office_Id, " +
			    		"        Cashbook_Year, " +
			    		"        Cashbook_Month, " +
			    		"        FROM_ACCOUNT_NO " +
			    		"      ) " +
			    		"    GROUP BY acc_u_id, " +
			    		"      acc_off_id, " +
			    		"      csh_bk_yr, " +
			    		"      csh_bk_mnth, " +
			    		"      Acc_No " +
			    		"    )yy " +
			    		"  ON xx.acc_u_id    =yy.acc_u_id " +
			    		"  AND xx.acc_off_id =yy.acc_off_id " +
			    		"  AND xx.csh_bk_yr  =yy.csh_bk_yr " +
			    		"  AND xx.csh_bk_mnth=yy.csh_bk_mnth " +
			    		"  )a " +
			    		"LEFT OUTER JOIN " +
			    		"  (SELECT t1.OB_PART2B, " +
			    		"    t2.A_2a, " +
			    		"    T1.Bname, " +
			    		"    t1.branchName, " +
			    		"    acc_u_id6, " +
			    		"    acc_off_id6, " +
			    		"    csh_bk_yr6, " +
			    		"    csh_bk_mnth6, " +
			    		"    Acc_No6 " +
			    		"  FROM " +
			    		"    (SELECT acc_u_id6, " +
			    		"      acc_off_id6, " +
			    		"      csh_bk_yr6, " +
			    		"      csh_bk_mnth6, " +
			    		"      Acc_No6, " +
			    		"      OB_PART2B, " +
			    		"      Bname, " +
			    		"      branchName " +
			    		"    FROM " +
			    		"      (SELECT ACCOUNTING_UNIT_ID AS acc_u_id6, " +
			    		"        ACCOUNTING_FOR_OFFICE_ID AS acc_off_id6, " +
			    		"        CASHBOOK_YEAR            AS csh_bk_yr6, " +
			    		"        CASHBOOK_MONTH           AS csh_bk_mnth6, " +
			    		"        Account_No               AS Acc_No6, " +
			    		"        OB_PART2B, " +
			    		"        Bank_Id, " +
			    		"        (SELECT List1.Bank_Name " +
			    		"        FROM Fas_Bank_List List1 " +
			    		"        WHERE List1.Bank_Id=ob1.Bank_Id " +
			    		"        )AS Bname, " +
			    		"        Branch_Id, " +
			    		"        (SELECT Br.Branch_Name " +
			    		"        FROM Fas_mst_Bank_Branches Br " +
			    		"        WHERE Br.Bank_Id=ob1.Bank_Id " +
			    		"        AND Br.Branch_Id=ob1.Branch_Id " +
			    		"        )AS Branchname " +
			    		"      FROM FAS_BRS_OB ob1 " +
			    		"      WHERE accounting_unit_id    =  " +cboAcc_UnitCode+
			    		"      AND accounting_for_office_id= " +cboOffice_code+
			    		"      AND cashbook_month          =  " +cboCashBook_Month+
			    		"      AND Cashbook_Year           = " +cboCashBook_Year+
			    		"      AND ACCOUNT_NO              =  " +cmbBankAccNo+
			    		"      ) " +
			    		"    ) t1 " +
			    		"  LEFT OUTER JOIN " +
			    		"    (SELECT acc_u_id, " +
			    		"      acc_off_id, " +
			    		"      Csh_Bk_Yr, " +
			    		"      Csh_Bk_Mnth, " +
			    		"      Acc_No, " +
			    		"      SUM(A_2a) AS A_2a " +
			    		"    FROM " +
			    		"      (SELECT ACCOUNTING_UNIT_ID AS acc_u_id, " +
			    		"        ACCOUNTING_FOR_OFFICE_ID AS acc_off_id, " +
			    		"        CASHBOOK_YEAR            AS csh_bk_yr, " +
			    		"        CASHBOOK_MONTH           AS csh_bk_mnth, " +
			    		"        OFFICE_ACCOUNT_NO        AS Acc_No, " +
			    		"        SUM(TOTAL_AMOUNT)        AS A_2a " +
			    		"      FROM FAS_FUND_RECEIPT_BY_OFFICE " +
			    		"      WHERE accounting_unit_id    =  " +cboAcc_UnitCode+
			    		"      AND accounting_for_office_id= " +cboOffice_code+
			    		"      AND cashbook_month          =  " +cboCashBook_Month+
			    		"      AND Cashbook_Year           = " +cboCashBook_Year+
			    		"      AND OFFICE_ACCOUNT_NO       = " +cmbBankAccNo+
			    		"      GROUP BY ACCOUNTING_UNIT_ID, " +
			    		"        ACCOUNTING_FOR_OFFICE_ID, " +
			    		"        CASHBOOK_YEAR, " +
			    		"        Cashbook_Month, " +
			    		"        OFFICE_ACCOUNT_NO " +
			    		"      UNION ALL " +
			    		"      SELECT ACCOUNTING_UNIT_ID  AS acc_u_id, " +
			    		"        ACCOUNTING_FOR_OFFICE_ID AS acc_off_id, " +
			    		"        CASHBOOK_YEAR            AS csh_bk_yr, " +
			    		"        CASHBOOK_MONTH           AS csh_bk_mnth, " +
			    		"        TO_ACCOUNT_NO            AS Acc_No, " +
			    		"        SUM(TOTAL_AMOUNT)        AS A_2a " +
			    		"      FROM FAS_INTER_BANK_TRF_AT_HO " +
			    		"      WHERE accounting_unit_id    =  " +cboAcc_UnitCode+
			    		"      AND accounting_for_office_id= " +cboOffice_code+
			    		"      AND cashbook_month          =  " +cboCashBook_Month+
			    		"      AND Cashbook_Year           = " +cboCashBook_Year+
			    		"      AND TO_ACCOUNT_NO           =  " +cmbBankAccNo+
			    		"      AND TRANSFER_STATUS         ='L' " +
			    		"      GROUP BY ACCOUNTING_UNIT_ID, " +
			    		"        ACCOUNTING_FOR_OFFICE_ID, " +
			    		"        CASHBOOK_YEAR, " +
			    		"        CASHBOOK_MONTH, " +
			    		"        TO_ACCOUNT_NO " +
			    		"      UNION ALL " +
			    		"      SELECT m.ACCOUNTING_UNIT_ID  AS acc_u_id, " +
			    		"        m.ACCOUNTING_FOR_OFFICE_ID AS acc_off_id, " +
			    		"        m.CASHBOOK_YEAR            AS csh_bk_yr, " +
			    		"        m.CASHBOOK_MONTH           AS csh_bk_mnth, " +
			    		"        m.Account_No               AS Acc_No, " +
			    		"        SUM(t.Amount)              AS A_2a " +
			    		"      FROM FAS_receipt_master m, " +
			    		"        FAS_RECEIPT_TRANSACTION t " +
			    		"      WHERE m.accounting_unit_id    =t.accounting_unit_id " +
			    		"      AND m.accounting_for_office_id=t.accounting_for_office_id " +
			    		"      AND m.cashbook_month          =t.cashbook_month " +
			    		"      AND m.Cashbook_Year           =t.Cashbook_Year " +
			    		"      AND m.RECEIPT_NO              =t.RECEIPT_NO " +
			    		"      AND m.accounting_unit_id    =  " +cboAcc_UnitCode+
			    		"      AND m.accounting_for_office_id= " +cboOffice_code+
			    		"      AND m.cashbook_month          =  " +cboCashBook_Month+
			    		"      AND m.Cashbook_Year           = " +cboCashBook_Year+
			    		"      AND m.Account_No              =  " +cmbBankAccNo+
			    		"      AND m.receipt_Status          ='L' " +
			    		"      AND m.REMITTANCE_STATUS       ='Y' " +
			    		"      AND m.REMITTANCE_IN_CURR_MONTH='Y' " +
			    		"      AND t.ACCOUNT_HEAD_CODE NOT  IN (420428,420429,120201) " +
			    		"      GROUP BY m.Accounting_Unit_Id, " +
			    		"        m.Accounting_For_Office_Id, " +
			    		"        m.Cashbook_Year, " +
			    		"        m.Cashbook_Month, " +
			    		"        m.Account_No " +
			    		"      ) " +
			    		"    GROUP BY acc_u_id, " +
			    		"      acc_off_id, " +
			    		"      csh_bk_yr, " +
			    		"      csh_bk_mnth, " +
			    		"      Acc_No " +
			    		"    )t2 " +
			    		"  ON T1.Acc_U_Id6     = T2.Acc_U_Id " +
			    		"  AND T1.acc_off_id6  = T2.Acc_Off_Id " +
			    		"  AND T1.csh_bk_yr6   = T2.Csh_Bk_Yr " +
			    		"  AND T1.Csh_Bk_Mnth6 = T2.Csh_Bk_Mnth " +
			    		"  AND T1.Acc_No6      = T2.Acc_No " +
			    		"  )B ON A.Acc_U_Id    =B.Acc_U_Id6 " +
			    		"AND A.acc_off_id      =B.acc_off_id6 " +
			    		"AND A.csh_bk_yr       =B.csh_bk_yr6 " +
			    		"AND A.Csh_Bk_Mnth     =B.Csh_Bk_Mnth6 " +
			    		"AND A.Acc_No          =B.Acc_No6 " +
			    		"LEFT OUTER JOIN " +
			    		"  (SELECT ACCOUNTING_UNIT_ID AS acc_u_id1, " +
			    		"    ACCOUNTING_FOR_OFFICE_ID AS acc_off_id1, " +
			    		"    CASHBOOK_YEAR            AS csh_bk_yr1, " +
			    		"    CASHBOOK_MONTH           AS csh_bk_mnth1, " +
			    		"    ACCOUNT_NO               AS acc_no1, " +
			    		"    SUM(CR_AMOUNT)           AS A_2b " +
			    		"  FROM FAS_BRS_TRANSACTION_NOENTRY " +
			    		"  WHERE accounting_unit_id    =  " +cboAcc_UnitCode+
			    		"      AND accounting_for_office_id= " +cboOffice_code+
			    		"      AND cashbook_month          =  " +cboCashBook_Month+
			    		"      AND Cashbook_Year           = " +cboCashBook_Year+
			    		"  AND ACCOUNT_NO              =  " +cmbBankAccNo+
			    		"  AND doc_type                ='J' " +
			    		"  GROUP BY ACCOUNTING_UNIT_ID, " +
			    		"    ACCOUNTING_FOR_OFFICE_ID, " +
			    		"    CASHBOOK_YEAR, " +
			    		"    CASHBOOK_MONTH, " +
			    		"    Account_No " +
			    		"  )c " +
			    		"ON a.acc_u_id     =c.acc_u_id1 " +
			    		"AND a.acc_off_id  = c.acc_off_id1 " +
			    		"AND a.csh_bk_yr   = c.csh_bk_yr1 " +
			    		"AND A.Csh_Bk_Mnth = c.Csh_Bk_Mnth1 " +
			    		"AND A.Acc_No      = C.Acc_No1 " +
			    		"LEFT OUTER JOIN " +
			    		"  (SELECT ACCOUNTING_UNIT_ID AS acc_u_id2, " +
			    		"    ACCOUNTING_FOR_OFFICE_ID AS acc_off_id2, " +
			    		"    CASHBOOK_YEAR            AS csh_bk_yr2, " +
			    		"    CASHBOOK_MONTH           AS csh_bk_mnth2, " +
			    		"    ACCOUNT_NO               AS acc_no2, " +
			    		"    SUM(DR_AMOUNT)           AS A_2d " +
			    		"  FROM FAS_BRS_TRANSACTION_NOENTRY " +
			    		"  WHERE accounting_unit_id    =  " +cboAcc_UnitCode+
			    		"      AND accounting_for_office_id= " +cboOffice_code+
			    		"      AND cashbook_month          =  " +cboCashBook_Month+
			    		"      AND Cashbook_Year           = " +cboCashBook_Year+
			    		"  AND ACCOUNT_NO              =  " +cmbBankAccNo+
			    		"  AND doc_type                ='J' " +
			    		"  GROUP BY ACCOUNTING_UNIT_ID, " +
			    		"    ACCOUNTING_FOR_OFFICE_ID, " +
			    		"    CASHBOOK_YEAR, " +
			    		"    CASHBOOK_MONTH, " +
			    		"    Account_No " +
			    		"  )d " +
			    		"ON a.acc_u_id     =d.acc_u_id2 " +
			    		"AND a.acc_off_id  = d.acc_off_id2 " +
			    		"AND a.csh_bk_yr   = d.csh_bk_yr2 " +
			    		"AND a.csh_bk_mnth = d.csh_bk_mnth2 " +
			    		"AND a.acc_no      = d.acc_no2";
			    
			    
			    
			    
			    }else{

	                sql_que="select test1.acc_u_id_T,test1.acc_off_id_T,test1.csh_bk_yr_T,test1.csh_bk_mnth_T,test1.acc_no_T,test1.cr,test1.dr,test1.OB_PART1,\n" + 
	               "test1.ob_one,test1.total,test1.row_num,test1.not_ack,\n" + 
	               "test1.rem_2a,test1.rem_2b,test1.total_three,\n" + 
	               "decode(test2.four_b,null,0,test2.four_b)as fourb,\n" + 
	               "((rem_2a)+(decode(test2.four_b,null,0,test2.four_b)))as total_five,\n" + 
	               "((test1.total_three)-((rem_2a)+(decode(test2.four_b,null,0,test2.four_b))))six_cb,test3.PASSBOOK_BALANCE,\n" + 
	               "(((test1.total_three)-((rem_2a)+(decode(test2.four_b,null,0,test2.four_b))))-(PASSBOOK_BALANCE))as eight\n" + 
	               "from\n" + 
	               "(select acc_u_id_T,acc_off_id_T,csh_bk_yr_T,\n" + 
	               "csh_bk_mnth_T,acc_no_T,cr,dr,OB_PART1,OB_PART2C as ob_one,\n" + 
	               "total,row_num,not_ack,\n" + 
	               "(total-(not_ack))rem_2a,amt_2t as rem_2b,\n" + 
	               "(OB_PART2C+(total-(not_ack))+(amt_2t))as total_three\n" + 
	               "from\n" + 
	               "(\n" + 
	               "select one.*,two.amt_2t from\n" + 
	               "(SELECT s2.acc_u_id_T,\n" + 
	               "  s2.acc_off_id_T,\n" + 
	               "  s2.csh_bk_yr_T,\n" + 
	               "  s2.csh_bk_mnth_T,\n" + 
	               "  s2.acc_no_T,\n" + 
	               "  s2.cr,\n" + 
	               "  s2.dr,\n" + 
	               "  s1.OB_PART1,\n" + 
	               "  s1.OB_PART2C,\n" + 
	               "  (s1.OB_PART1+s2.cr)AS total\n" + 
	               "FROM\n" + 
	               "  (SELECT ACCOUNTING_UNIT_ID          AS acc_u_id6,\n" + 
	               "    ACCOUNTING_FOR_OFFICE_ID          AS acc_off_id6,\n" + 
	               "    CASHBOOK_YEAR                     AS csh_bk_yr6,\n" + 
	               "    CASHBOOK_MONTH                    AS csh_bk_mnth6,\n" + 
	               "    Account_No                        AS Acc_No6,\n" + 
	               "    DECODE(OB_PART1,NULL,0,OB_PART1)  AS OB_PART1,\n" + 
	               "    DECODE(OB_PART2C,NULL,0,OB_PART2C)AS OB_PART2C\n" + 
	               "  FROM FAS_BRS_OB\n" +
	               "  WHERE accounting_unit_id    =" +cboAcc_UnitCode+ 
	               "  AND accounting_for_office_id= " +cboOffice_code+ 
	               "  AND cashbook_month          = " +cboCashBook_Month+ 
	               "  AND cashbook_year           =" +cboCashBook_Year+ 
	               "  AND ACCOUNT_NO              = " +cmbBankAccNo+ 
	               "  )s1\n" + 
	               "FULL OUTER JOIN\n" + 
	               "  (SELECT acc_u_id_T,\n" + 
	               "    acc_off_id_T,\n" + 
	               "    csh_bk_yr_T,\n" + 
	               "    csh_bk_mnth_T,\n" + 
	               "    acc_no_T,\n" + 
	               "    ibt   AS cr,\n" + 
	               "    dr_T1 AS dr\n" + 
	               "  FROM\n" + 
	               "    (SELECT ACCOUNTING_UNIT_ID AS acc_u_id_T,\n" + 
	               "      ACCOUNTING_FOR_OFFICE_ID AS acc_off_id_T,\n" + 
	               "      CASHBOOK_MONTH           AS csh_bk_mnth_T,\n" + 
	               "      CASHBOOK_YEAR            AS csh_bk_yr_T,\n" + 
	               "      SUM(TOTAL_AMOUNT)        AS ibt,\n" + 
	               "      "+cmbBankAccNo+"             AS acc_no_T\n" + 
	               "    FROM FAS_INTER_BANK_TRF_AT_HO\n" + 
	               "    WHERE ACCOUNTING_UNIT_ID     = " +cboAcc_UnitCode+ 
	               "    AND ACCOUNTING_FOR_OFFICE_ID = " +cboOffice_code+ 
	               "    AND CASHBOOK_MONTH           = " +cboCashBook_Month+ 
	               "    AND CASHBOOK_YEAR            = " +cboCashBook_Year+ 
	               "    AND (FROM_ACCOUNT_NO         = " +cmbBankAccNo+ 
	               "    OR TO_ACCOUNT_NO             ="+cmbBankAccNo+")\n" + 
	               "    AND TRANSFER_STATUS          ='L'\n" + 
	               "    GROUP BY ACCOUNTING_UNIT_ID,\n" + 
	               "      ACCOUNTING_FOR_OFFICE_ID,\n" + 
	               "      CASHBOOK_MONTH,\n" + 
	               "      CASHBOOK_YEAR\n" + 
	               "    )a\n" + 
	               "  LEFT OUTER JOIN\n" + 
	               "    (SELECT SUM(dr_T1)AS dr_T1,\n" + 
	               "      acc_u_id_T1,\n" + 
	               "      acc_off_id_T1,\n" + 
	               "      csh_bk_yr_T1,\n" + 
	               "      csh_bk_mnth_T1,\n" + 
	               "      acc_no_T1\n" + 
	               "    FROM\n" + 
	               "      (SELECT ACCOUNTING_UNIT_ID AS acc_u_id_T1,\n" + 
	               "        ACCOUNTING_FOR_OFFICE_ID AS acc_off_id_T1,\n" + 
	               "        CASHBOOK_YEAR            AS csh_bk_yr_T1,\n" + 
	               "        CASHBOOK_MONTH           AS csh_bk_mnth_T1,\n" + 
	               "        ACCOUNT_NO               AS acc_no_T1,\n" + 
	               "        SUM(DR_AMOUNT)           AS dr_T1\n" + 
	               "      FROM FAS_BRS_TRANSACTION\n" + 
	               "      WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
	               "      AND accounting_for_office_id=" +cboOffice_code+ 
	               "      AND cashbook_month          =" +cboCashBook_Month+ 
	               "      AND cashbook_year           =" +cboCashBook_Year+ 
	               "      AND ACCOUNT_NO              =" +cmbBankAccNo+ 
	               "      AND doc_type                ='IBT'\n" + 
	               "      GROUP BY ACCOUNTING_UNIT_ID,\n" + 
	               "        ACCOUNTING_FOR_OFFICE_ID,\n" + 
	               "        CASHBOOK_YEAR,\n" + 
	               "        CASHBOOK_MONTH,\n" + 
	               "        ACCOUNT_NO\n" + 
	               "      UNION ALL\n" + 
	               "      SELECT ACCOUNTING_UNIT_ID  AS acc_u_id_T1,\n" + 
	               "        ACCOUNTING_FOR_OFFICE_ID AS acc_off_id_T1,\n" + 
	               "        CASHBOOK_YEAR            AS csh_bk_yr_T1,\n" + 
	               "        CASHBOOK_MONTH           AS csh_bk_mnth_T1,\n" + 
	               "        ACCOUNT_NO               AS acc_no_T1,\n" + 
	               "        SUM(DR_AMOUNT)           AS dr_T1\n" + 
	               "      FROM fas_brs_transaction_noentry\n" + 
	               "      WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
	               "      AND accounting_for_office_id=" +cboOffice_code+ 
	               "      AND cashbook_month          =" +cboCashBook_Month+ 
	               "      AND cashbook_year           =" +cboCashBook_Year+ 
	               "      AND ACCOUNT_NO              =" +cmbBankAccNo+ 
	               "      AND doc_type                ='IBT'\n" + 
	               "      GROUP BY ACCOUNTING_UNIT_ID,\n" + 
	               "        ACCOUNTING_FOR_OFFICE_ID,\n" + 
	               "        CASHBOOK_YEAR,\n" + 
	               "        CASHBOOK_MONTH,\n" + 
	               "        ACCOUNT_NO\n" + 
	               "      )\n" + 
	               "    GROUP BY acc_u_id_T1,\n" + 
	               "      acc_off_id_T1,\n" + 
	               "      csh_bk_yr_T1,\n" + 
	               "      csh_bk_mnth_T1,\n" + 
	               "      acc_no_T1\n" + 
	               "    )c\n" + 
	               "  ON a.acc_u_id_T     =c.acc_u_id_T1\n" + 
	               "  AND a.acc_off_id_T  = c.acc_off_id_T1\n" + 
	               "  AND a.csh_bk_yr_T   = c.csh_bk_yr_T1\n" + 
	               "  AND a.csh_bk_mnth_T = c.csh_bk_mnth_T1\n" + 
	               "  AND a.acc_no_T      = c.acc_no_T1\n" + 
	               "  )s2 ON s1.acc_u_id6 =s2.acc_u_id_T\n" + 
	               "AND s1.acc_off_id6    =s2.acc_off_id_T\n" + 
	               "AND s1.csh_bk_yr6     =s2.csh_bk_yr_T\n" + 
	               "AND s1.csh_bk_mnth6   =s2.csh_bk_mnth_T\n" + 
	               "AND s1.Acc_No6        =s2.acc_no_T)one\n" + 
	               "LEFT OUTER JOIN\n" + 
	               "  (SELECT rownum AS t2_sl_no,\n" + 
	               "    amt_2t,\n" + 
	               "   \n" + 
	               "    "+cboCashBook_Year+" AS cbyear,\n" + 
	               "    "+cboCashBook_Month+"   AS cbmonth,\n" + 
	               "    auid_2T_5A,\n" + 
	               "    aoid_2T_5A,\n" + 
	               "    ano_2T_5A\n" + 
	               "  FROM\n" + 
	               "    (SELECT SUM (amt_2t)AS amt_2t,\n" + 
	               "      auid_2T_5A,\n" + 
	               "      aoid_2T_5A,\n" + 
	               "      ano_2T_5A\n" + 
	               "    FROM\n" + 
	               "      (SELECT DECODE( (SUM(dr_amount) - SUM(cr_amount)),NULL,0,(SUM(dr_amount) -SUM(cr_amount)) ) AS amt_2t,\n" + 
	               "        transaction_type,\n" + 
	               "        accounting_unit_id       AS auid_2T_5A,\n" + 
	               "        accounting_for_office_id AS aoid_2T_5A,\n" + 
	               "        cashbook_month           AS cbm_2T_5A,\n" + 
	               "        cashbook_year            AS cby_2T_5A,\n" + 
	               "        ACCOUNT_NO               AS ano_2T_5A\n" + 
	               "      FROM fas_brs_transaction\n" + 
	               "      WHERE accounting_unit_id        = " +cboAcc_UnitCode+ 
	               "      AND accounting_for_office_id    =" +cboOffice_code+ 
	               "      AND ((cashbook_year             <" +cboCashBook_Year+ 
	               "      AND cashbook_month             <=12)\n" + 
	               "      OR (cashbook_year               =" +cboCashBook_Year+ 
	               "      AND cashbook_month             <="+cboCashBook_Month+"))\n" + 
	               "      AND ACCOUNT_NO                  = " +cmbBankAccNo+ 
	               "      AND twad_or_non_twad            = 'NT'\n" + 
	               "      AND (CLEARED_BASED_ON_FOLLOWUP IS NULL\n" + 
	               "      OR CLEARED_BASED_ON_FOLLOWUP    ='N')\n" + 
	               "      GROUP BY transaction_type,\n" + 
	               "        accounting_unit_id,\n" + 
	               "        accounting_for_office_id,\n" + 
	               "        cashbook_month,\n" + 
	               "        cashbook_year,\n" + 
	               "        ACCOUNT_NO\n" + 
	               "      UNION ALL\n" + 
	               "      SELECT DECODE( (SUM(dr_amount) - SUM(cr_amount)),NULL,0,(SUM(dr_amount) -SUM(cr_amount)) ) AS amt_2t,\n" + 
	               "        transaction_type,\n" + 
	               "        accounting_unit_id       AS auid_2T_5A,\n" + 
	               "        accounting_for_office_id AS aoid_2T_5A,\n" + 
	               "        cashbook_month           AS cbm_2T_5A,\n" + 
	               "        cashbook_year            AS cby_2T_5A,\n" + 
	               "        ACCOUNT_NO               AS ano_2T_5A\n" + 
	               "      FROM fas_brs_transaction\n" + 
	               "      WHERE accounting_unit_id      = " +cboAcc_UnitCode+ 
	               "      AND accounting_for_office_id  =" +cboOffice_code+ 
	               "      AND ((cashbook_year           <" +cboCashBook_Year+ 
	               "      AND cashbook_month           <=12)\n" + 
	               "      OR (cashbook_year             =" +cboCashBook_Year+ 
	               "      AND cashbook_month           <="+cboCashBook_Month+"))\n" + 
	               "      AND ACCOUNT_NO                = " +cmbBankAccNo+ 
	               "      AND twad_or_non_twad          = 'NT'\n" + 
	               "      AND (CLEARED_BASED_ON_FOLLOWUP='Y'\n" + 
	               "      AND clearence_date            >('31-oct-2012'))\n" + 
	               "      GROUP BY transaction_type,\n" + 
	               "        accounting_unit_id,\n" + 
	               "        accounting_for_office_id,\n" + 
	               "        cashbook_month,\n" + 
	               "        cashbook_year,\n" + 
	               "        ACCOUNT_NO\n" + 
	               "      )\n" + 
	               "    GROUP BY \n" + 
	               "      auid_2T_5A,\n" + 
	               "      aoid_2T_5A,\n" + 
	               "      ano_2T_5A\n" + 
	               "    ) t2_c\n" + 
	               "  \n" + 
	               "  )two\n" + 
	               "  ON one.acc_u_id_T     =two.auid_2T_5A\n" + 
	               "AND one.acc_off_id_T        =two.aoid_2T_5A\n" + 
	               "AND one.csh_bk_yr_T         =two.cbyear\n" + 
	               "AND one.csh_bk_mnth_T       =two.cbmonth\n" + 
	               "AND one.acc_no_T             =two.ano_2T_5A\n" + 
	               "      \n" + 
	               "      ),\n" + 
	               "      (SELECT rownum AS row_num,not_ack\n" + 
	               "        FROM\n" + 
	               "          (SELECT SUM(amt_t2_A) AS not_ack\n" + 
	               "          FROM\n" + 
	               "            (SELECT ( SUM(cr_amount) - SUM(dr_amount) ) AS amt_t2_A\n" + 
	               "            FROM FAS_BRS_TRANSACTION_NOENTRY\n" + 
	               "            WHERE accounting_unit_id     =" +cboAcc_UnitCode+ 
	               "            AND accounting_for_office_id =" +cboOffice_code+ 
	               "            AND ((cashbook_year          <" +cboCashBook_Year+ 
	               "            AND cashbook_month          <=12)\n" + 
	               "            OR (cashbook_year            =" +cboCashBook_Year+ 
	               "            AND cashbook_month          <="+cboCashBook_Month+"))\n" + 
	               "            AND ACCOUNT_NO               = " +cmbBankAccNo+ 
	               "            AND TWAD_OR_NON_TWAD         ='T'\n" + 
	               "            UNION ALL\n" + 
	               "            SELECT ( SUM(cr_amount) - SUM(dr_amount) ) AS amt_t2_A\n" + 
	               "            FROM FAS_BRS_TRANSACTION\n" + 
	               "            WHERE accounting_unit_id     =" +cboAcc_UnitCode+ 
	               "            AND Accounting_For_Office_Id =" +cboOffice_code+ 
	               "            AND PASSBOOK_DATE            >('31-oct-2012')\n" + 
	               "            AND (01\n" + 
	               "              ||'-'\n" + 
	               "              ||Cashbook_Month\n" + 
	               "              ||'-'\n" + 
	               "              ||cashbook_year)  <=('31-oct-2012')\n" + 
	               "            AND ((cashbook_year  <" +cboCashBook_Year+ 
	               "            AND cashbook_month  <=12)\n" + 
	               "            OR (cashbook_year    =" +cboCashBook_Year+ 
	               "            AND cashbook_month  <="+cboCashBook_Month+"))\n" + 
	               "            AND Account_No       =" +cmbBankAccNo+ 
	               "            AND Twad_Or_Non_Twad ='T'\n" + 
	               "            )\n" + 
	               "          )))test1\n" + 
	               "          left outer join\n" + 
	               "          \n" + 
	               "          (\n" + 
	               "          SELECT ACCOUNTING_UNIT_ID  AS acc_u_id5,\n" + 
	               "        ACCOUNTING_FOR_OFFICE_ID AS acc_off_id5,\n" + 
	               "        Account_No               AS Acc_No5,"+cboCashBook_Year+" as year1,"+cboCashBook_Month+" as month1,\n" + 
	               "        SUM(DR_AMOUNT)           AS four_b\n" + 
	               "      FROM FAS_BRS_TRANSACTION\n" + 
	               "      WHERE accounting_unit_id              = " +cboAcc_UnitCode+ 
	               "      AND accounting_for_office_id          = " +cboOffice_code+ 
	               "      AND ACCOUNT_NO                        = " +cmbBankAccNo+ 
	               "      AND (extract(YEAR FROM PASSBOOK_DATE) <" +cboCashBook_Year+ 
	               "      OR (Extract(YEAR FROM Passbook_Date)  =" +cboCashBook_Year+ 
	               "      AND extract(MONTH FROM PASSBOOK_DATE)<="+cboCashBook_Month+"))\n" + 
	               "      AND Twad_Or_Non_Twad                  ='NT'\n" + 
	               "      AND TRANSACTION_TYPE                 IN(3,23,12)\n" + 
	               "      GROUP BY ACCOUNTING_UNIT_ID,\n" + 
	               "        ACCOUNTING_FOR_OFFICE_ID,\n" + 
	               "        Account_No\n" + 
	               "          )test2\n" + 
	               "          on test1.acc_u_id_T=test2.acc_u_id5\n" + 
	               "          and test1.acc_off_id_T=test2.acc_off_id5\n" + 
	               "          and test1.csh_bk_yr_T=test2.year1\n" + 
	               "          and test1.csh_bk_mnth_T=test2.month1\n" + 
	               "          and test1.acc_no_T=test2.Acc_No5\n" + 
	               "          left outer join\n" + 
	               "          (select ACCOUNTING_UNIT_ID,ACCOUNT_NO,PASSBOOK_BALANCE from fas_brs_master where accounting_unit_id  = " +cboAcc_UnitCode+ 
	               "           AND accounting_for_office_id  = "+cboOffice_code+" AND ACCOUNT_NO = "+cmbBankAccNo+" and CASHBOOK_YEAR="+cboCashBook_Year+" and CASHBOOK_MONTH="+cboCashBook_Month+"\n" + 
	               "          )test3\n" + 
	               "      on test1.acc_u_id_T=test3.ACCOUNTING_UNIT_ID\n" + 
	               "      and test1.acc_no_T=test3.ACCOUNT_NO";
	               System.out.println("sql ::: "+sql_que);
			    }
		File reportFile = null;
		String sql="";
		Map map = null;
		map = new HashMap();
		try {
			
			if(cmd.equalsIgnoreCase("printFunc")){
				
			if(mode.equalsIgnoreCase("SCH"))	{
			reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/BRS/jaspers/banking_section/BRS_Report_2C_BS.jasper"));
			}else{
			reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/BRS/jaspers/banking_section/BRS_Report_2C_BSBKUp.jasper"));
			}
			System.out.println(reportFile);
			System.out.println("sql_que :: "+sql_que);
			map.put("sql", sql_que);
			map.put("BAnk_name", BAnk_name);
			map.put("Branch_name", Branch_name);
			
			  
			if (!reportFile.exists())
				throw new JRRuntimeException("File J not found. The report design must be compiled first.");

			JasperReport jasperReport = (JasperReport) JRLoader
					.loadObject(reportFile.getPath());
			
			//System.out.println("brs_amt:::::::final:::::"+brs_amt);
			map.put("UnitId", cboAcc_UnitCode);
			map.put("OfficeId", cboOffice_code);
			map.put("cbyear", cboCashBook_Year);
			map.put("cbmonth", cboCashBook_Month);
			map.put("accNo", cmbBankAccNo);
			map.put("month", month);
            map.put("amount", ii);
		    map.put("UnitName", UnitName);
		    map.put("brs_amt_n", brs_amt);
		
		    
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					jasperReport, map, con);
			
			String rtype = "PDF";
			//System.out.println(rtype);
			 if (rtype.equalsIgnoreCase("PDF")) {
				System.out.println(rtype);
				byte buf[] = JasperExportManager.exportReportToPdf(jasperPrint);
				System.out.println("Length  " + buf.length);
				response.setContentType("application/pdf");
				response.setContentLength(buf.length);
				
				response.setHeader("Content-Disposition",
						"attachment;filename=\"BRS_Part2C.pdf\"");
				OutputStream out = response.getOutputStream();
				out.write(buf, 0, buf.length);
				out.close();
			} 
			}
			else if(cmd.equalsIgnoreCase("f_brs"))
			{

				int jk=0;
				//double amount=0.0;
				double four_cAmount=0.0;
				int unitcode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
				int offCode=Integer.parseInt(request.getParameter("cmbOffice_code"));
				int passYear=Integer.parseInt(request.getParameter("cboCashBook_Year"));
				int passMonth=Integer.parseInt(request.getParameter("cboCashBook_Month"));
				long accNo=Long.parseLong(request.getParameter("cmbBankAccNo"));
				System.out.println("sql_que:::"+sql_que);
	                try{
	               PreparedStatement pss=con.prepareStatement(sql_que);
	               ResultSet rss=pss.executeQuery();
		               if(rss.next())
		               {
		            	   PreparedStatement psta=con.prepareStatement("delete from FAS_BRS_PART_2C where ACCOUNTING_UNIT_ID="+cboAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cboOffice_code+" and PASS_SHEET_YEAR="+cboCashBook_Year+" and PASS_SHEET_MONTH="+cboCashBook_Month+" and ACCOUNT_NO="+cmbBankAccNo);
		            	   int s= psta.executeUpdate();
		            	   
		            	  PreparedStatement pss1=con.prepareStatement("insert into FAS_BRS_PART_2C (ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID," +
		            	   		"PASS_SHEET_YEAR,PASS_SHEET_MONTH,ACCOUNT_NO,S1A,S1b,S1D,S2,CHEQUE_CASHED,TWAD_AMOUNT," +
		            	   		"UPDATED_BY_USER_ID,UPDATED_DATE,BANK_ID,BRANCH_ID) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		            	   pss1.setInt(1,unitcode);
		            	   pss1.setInt(2,offCode);
		            	   pss1.setInt(3,passYear);
		            	   pss1.setInt(4,passMonth);
		            	   pss1.setLong(5,accNo);
		            	   pss1.setDouble(6,rss.getDouble("Idamt"));
		            	   pss1.setDouble(7,rss.getDouble("A_6a"));
		            	   pss1.setDouble(8,rss.getDouble("Intallowed"));
		            	   pss1.setDouble(9,rss.getDouble("Passbook_Balance"));
		            	  
		            	   pss1.setDouble(10,rss.getDouble("Rfordiff"));
		            	   pss1.setDouble(11,rss.getDouble("Only_26"));
		            	  
		            	   pss1.setString(12,update_user);
		                   pss1.setTimestamp(13,ts);
		                   pss1.setInt(14,bank_id);
		                   pss1.setInt(15,branch_id);
		                   jk=pss1.executeUpdate();
		                   System.out.println("value jk:::"+jk);
		            	   
		               }
		               if(jk>0)
		               {
		            	    con.commit();
							con.setAutoCommit(true);
							sendMessage(response,"Records Inserted Successfully  ","ok"); 
		               }
		               else
		               {
		            	    con.rollback();
							con.setAutoCommit(true);
		            	    sendMessage(response,"Records Not Inserted into Part-2c ","ok");   
		               }
		            
		               
	                }
	                catch(Exception ee)
	                {
	                	System.out.println("exception in fetching query::::"+ee);
	                }
	                
			}
			
		} catch (Exception ex) {
			String connectMsg = "Could not create the report "
					+ ex.getMessage() + " uu " + ex.getLocalizedMessage();
			System.out.println(connectMsg);
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
