package Servlets.FAS.FAS1.BRS.servlets;

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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * Servlet implementation class BRSReport_Part2B
 */
public class BRSReport_Part2C extends HttpServlet {
	
	private String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BRSReport_Part2C() {
        super();
       
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
	     response.setHeader("cache-control","no-cache");
	      String CONTENT_TYPE = "text/xml; charset=windows-1252";
	      response.setContentType(CONTENT_TYPE);
	    Connection con = null;
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
	    PreparedStatement ps2=null,ps=null,ps1=null;
	    ResultSet rs2=null,rs=null,rs1=null;
	    String cmd="",xml=null;
	    int count=0;
	    int cmbAcc_UnitCode=0;
	    try
	    {
	    cmd=request.getParameter("command");
	    cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
	    
	    }catch (Exception e) {
	            e.printStackTrace();
	    }
	    System.out.println("cmd---------"+cmd);
		
	/*	if(cmd.equalsIgnoreCase("f_brs"))
			{
				int insertCount=0;
				double amount=0.0;
				double four_cAmount=0.0;
				int unitcode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
				int offCode=Integer.parseInt(request.getParameter("cmbOffice_code"));
				int passYear=Integer.parseInt(request.getParameter("cboCashBook_Year"));
				int passMonth=Integer.parseInt(request.getParameter("cboCashBook_Month"));
				long accNo=Long.parseLong(request.getParameter("cmbBankAccNo"));
				
				  xml="<response><command>free_brs</command>";
					
                        String s2="SELECT DECODE(OFFICE_NAME,NULL,'-',OFFICE_NAME) AS OFFICE_NAME,\n" + 
                        "  DECODE(BANK_NAME,NULL,'-',BANK_NAME)          AS BANK_NAME,\n" + 
                        "  DECODE(BRANCH_NAME,NULL,'-',BRANCH_NAME)      AS BRANCH_NAME,\n" + 
                        "  acc_u_id,\n" + 
                        "  acc_off_id,\n" + 
                        "  csh_bk_yr,\n" + 
                        "  csh_bk_mnth,\n" + 
                        "  acc_no,\n" + 
                        "  A_2a,\n" + 
                        "  A_4,\n" + 
                        "  ( (DECODE(Ob_Part2a,NULL,0,Ob_Part2a)+A_2e)-A_4) AS A_5,\n" + 
                        "  Passbook_Balance,\n" + 
                        "  (SELECT Initial_Deposit_Amt\n" + 
                        "  FROM Fas_Mst_Bank_Balance\n" + 
                        "  Where Accounting_Unit_Id=3\n" + 
                        "  AND Bank_Ac_No          =4181\n" + 
                        "  )AS Idamt,\n" + 
                        "  Ob_Part2b,\n" + 
                        "  DECODE(Intallowed,NULL,0,Intallowed)as Intallowed,\n" + 
                        "(decode(Intallowed,null,0,Intallowed)+A_6a) as int6aAdd,\n" + 
                        "  (SELECT SUM(DR_AMOUNT)\n" + 
                        "  FROM FAS_BRS_TRANSACTION\n" + 
                        "  WHERE accounting_unit_id    = " +unitcode+ 
                        "  AND Accounting_For_Office_Id= "+offCode+ 
                        "  AND (Cashbook_Year           = "+passYear+" or Cashbook_Year           <"+passYear+")\n" + 
                        "  AND ACCOUNT_NO              = " +accNo+ 
                        "  AND Twad_Or_Non_Twad        ='NT'\n" + 
                        "  AND Transaction_Type       IN(3,23)\n" + 
                        "  GROUP BY ACCOUNTING_UNIT_ID,\n" + 
                        "    ACCOUNTING_FOR_OFFICE_ID,\n" + 
                        "    Account_No\n" + 
                        "  )AS Rfordiff,\n" + 
                        "  (SELECT SUM(CR_AMOUNT)\n" + 
                        "  FROM FAS_BRS_TRANSACTION\n" + 
                        "  WHERE accounting_unit_id    = " +unitcode+ 
                        "  AND Accounting_For_Office_Id= "+offCode+ 
                        "  AND Cashbook_Year           =" +passYear+ 
                        "  AND ACCOUNT_NO              = " +accNo+ 
                        "  AND cashbook_month          ="+passMonth+  
                        "  AND Twad_Or_Non_Twad        ='NT'\n" + 
                        "  AND Transaction_Type        =26\n" + 
                        "  )AS Only_26,\n" + 
                        "  (SELECT SUM(DR_AMOUNT)\n" + 
                        "  FROM FAS_BRS_TRANSACTION\n" + 
                        "  WHERE accounting_unit_id    = " +unitcode+ 
                        "  AND Accounting_For_Office_Id= "+offCode+ 
                        "  AND Cashbook_Year           = " +passYear+ 
                        "  AND ACCOUNT_NO              = " +accNo+ 
                        "  AND Cashbook_Month          = "+passMonth+  
                        "  AND Twad_Or_Non_Twad        ='NT'\n" + 
                        "  AND Transaction_Type        =3\n" + 
                        "  )AS Three_Dr,\n" + 
                        "  (SELECT SUM(CR_AMOUNT)\n" + 
                        "  FROM FAS_BRS_TRANSACTION\n" + 
                        "  WHERE accounting_unit_id    = " +unitcode+ 
                        "  AND Accounting_For_Office_Id=  "+offCode+ 
                        "  AND Cashbook_Year           = " +passYear+ 
                        "  AND Account_No              = " +accNo+ 
                        "  AND Cashbook_Month          = "+passMonth+  
                        "  AND Twad_Or_Non_Twad        ='NT'\n" + 
                        "  AND Transaction_Type        =4\n" + 
                        "  )AS Four_Cr,\n" + 
                        "  (SELECT SUM(DR_AMOUNT)\n" + 
                        "  FROM FAS_BRS_TRANSACTION\n" + 
                        "  WHERE accounting_unit_id    = " +unitcode+ 
                        "  AND Accounting_For_Office_Id=  "+offCode+ 
                        "  AND Cashbook_Year           =  " +passYear+ 
                        "  AND Account_No              = " +accNo+ 
                        "  AND Cashbook_Month          = "+passMonth+  
                        "  AND Twad_Or_Non_Twad        ='NT'\n" + 
                        "  AND Transaction_Type        =6\n" + 
                        "  )AS Com_Charges,\n" + 
                        "  (SELECT SUM(CR_AMOUNT)\n" + 
                        "  FROM FAS_BRS_TRANSACTION\n" + 
                        "  WHERE accounting_unit_id    =" +unitcode+ 
                        "  AND Accounting_For_Office_Id="+offCode+ 
                        "  AND Cashbook_Year           =" +passYear+ 
                        "  AND Account_No              =  " +accNo+ 
                        "  AND Cashbook_Month          = "+passMonth+  
                        "  AND Twad_Or_Non_Twad        ='NT'\n" + 
                        "  AND Transaction_Type        =27\n" + 
                        "  )AS err_depo,decode(A_2e,null,0,A_2e) as A_2e,A_6a\n" + 
                        "FROM (\n" + 
                        "  (SELECT Acc_U_Id6,\n" + 
                        "    Acc_Off_Id6,\n" + 
                        "    Csh_Bk_Yr6,\n" + 
                        "    Csh_Bk_Mnth6,\n" + 
                        "    Acc_No6,\n" + 
                        "    Ob_Part2a,\n" + 
                        "    OB_PART2B,\n" + 
                        "    OFFICE_NAME,\n" + 
                        "    BANK_NAME,\n" + 
                        "    BRANCH_NAME,\n" + 
                        "    PASSBOOK_BALANCE,(SELECT CR_AMOUNT\n" + 
                        "  FROM FAS_BRS_TRANSACTION\n" + 
                        "  WHERE TRANSACTION_TYPE      =14\n" + 
                        "  AND accounting_unit_id      =" +unitcode+ 
                        "  AND accounting_for_office_id="+offCode+
                        "  AND cashbook_year           =" +passYear+ 
                        "  AND ACCOUNT_NO              = " +accNo+ 
                        "  AND Cashbook_Month          ="+passMonth+  
                        "  AND Twad_Or_Non_Twad        ='NT'\n" + 
                        "  )AS Intallowed\n" + 
                        "  FROM\n" + 
                        "    (SELECT rownum AS slno1,\n" + 
                        "      acc_u_id6,\n" + 
                        "      acc_off_id6,\n" + 
                        "      csh_bk_yr6,\n" + 
                        "      csh_bk_mnth6,\n" + 
                        "      Acc_No6,\n" + 
                        "      OB_PART2A,\n" + 
                        "      OB_PART2B,\n" + 
                        "      OFFICE_NAME\n" + 
                        "    FROM\n" + 
                        "      (SELECT ACCOUNTING_UNIT_ID AS acc_u_id6,\n" + 
                        "        ACCOUNTING_FOR_OFFICE_ID AS acc_off_id6,\n" + 
                        "        CASHBOOK_YEAR            AS csh_bk_yr6,\n" + 
                        "        CASHBOOK_MONTH           AS csh_bk_mnth6,\n" + 
                        "        Account_No               AS Acc_No6,\n" + 
                        "        OB_PART2A,\n" + 
                        "        OB_PART2B\n" + 
                        "      FROM FAS_BRS_OB\n" + 
                        "      WHERE accounting_unit_id    =" +unitcode+ 
                        "      AND accounting_for_office_id="+offCode+
                        "      AND cashbook_month          ="+passMonth+
                        "      AND cashbook_year           =" +passYear+ 
                        "      AND ACCOUNT_NO              =  " +accNo+ 
                        "      )AUID1\n" + 
                        "    LEFT OUTER JOIN\n" + 
                        "      (SELECT OFFICE_ID,OFFICE_NAME FROM COM_MST_OFFICES\n" + 
                        "      )AUID2\n" + 
                        "    ON AUID1.acc_off_id6 = AUID2.OFFICE_ID\n" + 
                        "    ) t1\n" + 
                        "  LEFT OUTER JOIN\n" + 
                        "    (SELECT Slno2,\n" + 
                        "      Bank_Name,\n" + 
                        "      Branch_Name,\n" + 
                        "      PASSBOOK_BALANCE\n" + 
                        "    FROM\n" + 
                        "      (SELECT rownum AS slno2,\n" + 
                        "        BANK_ID,\n" + 
                        "        BRANCH_ID,\n" + 
                        "        PASSBOOK_BALANCE\n" + 
                        "      FROM FAS_BRS_MASTER\n" + 
                        "      WHERE accounting_unit_id    = " +unitcode+  
                        "      AND accounting_for_office_id= "+offCode+
                        "      AND cashbook_month          ="+passMonth+
                        "      AND cashbook_year           =" +passYear+ 
                        "      AND ACCOUNT_NO              = " +accNo+  
                        "      )sss\n" + 
                        "    LEFT OUTER JOIN\n" + 
                        "      (SELECT BRANCH_ID AS brnch_id,\n" + 
                        "        BANK_ID         AS bnk_id,\n" + 
                        "        BRANCH_NAME\n" + 
                        "      FROM FAS_MST_BANK_BRANCHES\n" + 
                        "      )c\n" + 
                        "    ON sss.BANK_ID    = c.bnk_id\n" + 
                        "    AND sss.BRANCH_ID = c.brnch_id\n" + 
                        "    LEFT OUTER JOIN\n" + 
                        "      (SELECT BANK_ID AS bnk_id1,BANK_NAME FROM FAS_BANK_LIST\n" + 
                        "      )d\n" + 
                        "    ON sss.BANK_ID  = d.bnk_id1\n" + 
                        "    )t2 ON t1.slno1 = t2.slno2\n" + 
                        "  )XX\n" + 
                        "LEFT OUTER JOIN\n" + 
                        "  (SELECT acc_u_id,\n" + 
                        "    acc_off_id,\n" + 
                        "    csh_bk_yr,\n" + 
                        "    csh_bk_mnth,\n" + 
                        "    acc_no,\n" + 
                        "    A_2a,\n" + 
                        "    A_2b,\n" + 
                        "    A_2c,\n" + 
                        "    A_2d,\n" + 
                        "    A_2e,\n" + 
                        "    A_4,A_6a\n" + 
                        "  FROM\n" + 
                        "    (SELECT acc_u_id,\n" + 
                        "      acc_off_id,\n" + 
                        "      csh_bk_yr,\n" + 
                        "      csh_bk_mnth,\n" + 
                        "      acc_no,\n" + 
                        "      A_2a,\n" + 
                        "      A_2b,\n" + 
                        "      A_2a+A_2b AS A_2c,\n" + 
                        "      A_2d,\n" + 
                        "      (A_2a+A_2b)-A_2d AS A_2e,A_6a\n" + 
                        "    FROM\n" + 
                        "      (SELECT acc_u_id,\n" + 
                        "        acc_off_id,\n" + 
                        "        csh_bk_yr,\n" + 
                        "        csh_bk_mnth,\n" + 
                        "        acc_no,\n" + 
                        "        SUM(A_2a) AS A_2a\n" + 
                        "      FROM\n" + 
                        "        (\n" + 
                        "        SELECT acc_u_id,\n" + 
                        "    acc_off_id,\n" + 
                        "    csh_bk_yr,\n" + 
                        "    csh_bk_mnth,\n" + 
                        "    acc_no,\n" + 
                        "    SUM(A_2a) AS A_2a\n" + 
                        "  FROM\n" + 
                        "    (SELECT ACCOUNTING_UNIT_ID AS acc_u_id,\n" + 
                        "      ACCOUNTING_FOR_OFFICE_ID AS acc_off_id,\n" + 
                        "      CASHBOOK_YEAR            AS csh_bk_yr,\n" + 
                        "      CASHBOOK_MONTH           AS csh_bk_mnth,\n" + 
                        "      Account_No               AS Acc_No,\n" + 
                        "      SUM(total_amount)        AS A_2a\n" + 
                        "    FROM FAS_payment_master\n" + 
                        "    WHERE accounting_unit_id    = " +unitcode+ 
                        "    And Accounting_For_Office_Id="+offCode+ 
                        "    AND cashbook_month          = "+passMonth+
                        "    AND Cashbook_Year           =" +passYear+ 
                        "    AND ACCOUNT_NO              =" +accNo+ 
                        "    AND Payment_Status          ='L'\n" + 
                        "    GROUP BY Accounting_Unit_Id,\n" + 
                        "      Accounting_For_Office_Id,\n" + 
                        "      Cashbook_Year,\n" + 
                        "      Cashbook_Month,\n" + 
                        "      Account_No\n" + 
                        "    UNION ALL\n" + 
                        "    SELECT ACCOUNTING_UNIT_ID  AS acc_u_id,\n" + 
                        "      ACCOUNTING_FOR_OFFICE_ID AS acc_off_id,\n" + 
                        "      CASHBOOK_YEAR            AS csh_bk_yr,\n" + 
                        "      CASHBOOK_MONTH           AS csh_bk_mnth,\n" + 
                        "      Account_No               AS Acc_No,\n" + 
                        "      SUM(Total_Amount)        AS A_2a\n" + 
                        "    FROM FAS_receipt_master\n" + 
                        "    WHERE accounting_unit_id    = " +unitcode+ 
                        "    And Accounting_For_Office_Id= "+offCode+ 
                        "    AND cashbook_month          ="+passMonth+  
                        "    AND Cashbook_Year           =" +passYear+ 
                        "    AND Account_No              =" +accNo+ 
                        "    AND receipt_Status          ='L'\n" + 
                        "    AND CREATED_BY_MODULE       ='SC'\n" + 
                        "    GROUP BY Accounting_Unit_Id,\n" + 
                        "      Accounting_For_Office_Id,\n" + 
                        "      Cashbook_Year,\n" + 
                        "      Cashbook_Month,\n" + 
                        "      Account_No\n" + 
                        "    )\n" + 
                        "  GROUP BY acc_u_id,\n" + 
                        "    acc_off_id,\n" + 
                        "    csh_bk_yr,\n" + 
                        "    csh_bk_mnth,\n" + 
                        "    Acc_No\n" + 
                        "  \n" + 
                        "        )\n" + 
                        "      GROUP BY acc_u_id,\n" + 
                        "        acc_off_id,\n" + 
                        "        csh_bk_yr,\n" + 
                        "        csh_bk_mnth,\n" + 
                        "        acc_no\n" + 
                        "      )a\n" + 
                        "    LEFT OUTER JOIN\n" + 
                        "      (SELECT ACCOUNTING_UNIT_ID AS acc_u_id1,\n" + 
                        "        ACCOUNTING_FOR_OFFICE_ID AS acc_off_id1,\n" + 
                        "        CASHBOOK_YEAR            AS csh_bk_yr1,\n" + 
                        "        CASHBOOK_MONTH           AS csh_bk_mnth1,\n" + 
                        "        ACCOUNT_NO               AS acc_no1,\n" + 
                        "        SUM(CR_AMOUNT)           AS A_2b\n" + 
                        "      FROM FAS_BRS_TRANSACTION_NOENTRY\n" + 
                        "      WHERE accounting_unit_id    =" +unitcode+ 
                        "      And Accounting_For_Office_Id="+offCode+ 
                        "      AND cashbook_month          ="+passMonth+  
                        "      AND cashbook_year           =" +passYear+ 
                        "      AND ACCOUNT_NO              =" +accNo+ 
                        "      AND doc_type                ='J'\n" + 
                        "      GROUP BY ACCOUNTING_UNIT_ID,\n" + 
                        "        ACCOUNTING_FOR_OFFICE_ID,\n" + 
                        "        CASHBOOK_YEAR,\n" + 
                        "        CASHBOOK_MONTH,\n" + 
                        "        ACCOUNT_NO\n" + 
                        "      )b\n" + 
                        "    ON a.acc_u_id     =b.acc_u_id1\n" + 
                        "    AND a.acc_off_id  = b.acc_off_id1\n" + 
                        "    AND a.csh_bk_yr   = b.csh_bk_yr1\n" + 
                        "    AND a.csh_bk_mnth = b.csh_bk_mnth1\n" + 
                        "    AND a.acc_no      = b.acc_no1\n" + 
                        "    LEFT OUTER JOIN\n" + 
                        "      (SELECT ACCOUNTING_UNIT_ID AS acc_u_id2,\n" + 
                        "        ACCOUNTING_FOR_OFFICE_ID AS acc_off_id2,\n" + 
                        "        CASHBOOK_YEAR            AS csh_bk_yr2,\n" + 
                        "        CASHBOOK_MONTH           AS csh_bk_mnth2,\n" + 
                        "        ACCOUNT_NO               AS acc_no2,\n" + 
                        "        SUM(DR_AMOUNT)           AS A_2d\n" + 
                        "      FROM FAS_BRS_TRANSACTION_NOENTRY\n" + 
                        "      WHERE accounting_unit_id    = " +unitcode+
                        "      And Accounting_For_Office_Id="+offCode+ 
                        "      AND cashbook_month          = "+passMonth+
                        "      AND cashbook_year           =" +passYear+  
                        "      AND ACCOUNT_NO              =" +accNo+ 
                        "      AND doc_type                ='J'\n" + 
                        "      GROUP BY ACCOUNTING_UNIT_ID,\n" + 
                        "        ACCOUNTING_FOR_OFFICE_ID,\n" + 
                        "        CASHBOOK_YEAR,\n" + 
                        "        CASHBOOK_MONTH,\n" + 
                        "        ACCOUNT_NO\n" + 
                        "      )c\n" + 
                        "    ON a.acc_u_id     =c.acc_u_id2\n" + 
                        "    AND a.acc_off_id  = c.acc_off_id2\n" + 
                        "    AND a.csh_bk_yr   = c.csh_bk_yr2\n" + 
                        "    AND a.csh_bk_mnth = c.csh_bk_mnth2\n" + 
                        "    AND a.acc_no      = c.acc_no2\n" + 
                        "\n" + 
                        " Left Outer Join\n" + 
                        "    (SELECT acc_u_id4,\n" + 
                        "      acc_off_id4,\n" + 
                        "      acc_no4,\n" + 
                        "      SUM(A_6a) AS A_6a\n" + 
                        "    FROM\n" + 
                        "      (SELECT ACCOUNTING_UNIT_ID AS acc_u_id4,\n" + 
                        "        ACCOUNTING_FOR_OFFICE_ID AS acc_off_id4,\n" + 
                        "        CASHBOOK_YEAR            AS csh_bk_yr4,\n" + 
                        "        CASHBOOK_MONTH           AS csh_bk_mnth4,\n" + 
                        "        ACCOUNT_NO               AS acc_no4,\n" + 
                        "        SUM(DR_AMOUNT)           AS A_6a\n" + 
                        "      FROM FAS_BRS_TRANSACTION_NOENTRY\n" + 
                        "      WHERE accounting_unit_id    = " +unitcode+ 
                        "      AND accounting_for_office_id="+offCode+ 
                        "      AND (Cashbook_Year          =" +passYear+ 
                        "      AND Cashbook_Month          <"+passMonth+")\n" + 
                        "      OR (cashbook_year           <"+passYear+")\n" + 
                        "      AND ACCOUNT_NO              ="+accNo+
                        "      AND doc_type                ='P'\n" + 
                        "      GROUP BY ACCOUNTING_UNIT_ID,\n" + 
                        "        ACCOUNTING_FOR_OFFICE_ID,\n" + 
                        "        CASHBOOK_YEAR,\n" + 
                        "        CASHBOOK_MONTH,\n" + 
                        "        ACCOUNT_NO\n" + 
                        "      UNION ALL\n" + 
                        "      SELECT ACCOUNTING_UNIT_ID  AS acc_u_id4,\n" + 
                        "        ACCOUNTING_FOR_OFFICE_ID AS acc_off_id4,\n" + 
                        "        CASHBOOK_YEAR            AS csh_bk_yr4,\n" + 
                        "        CASHBOOK_MONTH           AS csh_bk_mnth4,\n" + 
                        "        ACCOUNT_NO               AS acc_no4,\n" + 
                        "        SUM(CR_AMOUNT)           AS A_6a\n" + 
                        "      FROM FAS_BRS_TRANSACTION_NOENTRY\n" + 
                        "      WHERE accounting_unit_id    =" +unitcode+ 
                        "      AND accounting_for_office_id="+offCode+ 
                        "      And (Cashbook_Year          =" +passYear+ 
                        "      AND Cashbook_Month          <"+passMonth+")\n" + 
                        "      Or (Cashbook_Year           <"+passYear+")\n" + 
                        "      AND ACCOUNT_NO              = "+accNo+"\n" + 
                        "      AND doc_type                ='SC'\n" + 
                        "      GROUP BY ACCOUNTING_UNIT_ID,\n" + 
                        "        ACCOUNTING_FOR_OFFICE_ID,\n" + 
                        "        CASHBOOK_YEAR,\n" + 
                        "        CASHBOOK_MONTH,\n" + 
                        "        ACCOUNT_NO\n" + 
                        "      )\n" + 
                        "    GROUP BY acc_u_id4,\n" + 
                        "      acc_off_id4,\n" + 
                        "      acc_no4\n" + 
                        "    )g\n" + 
                        "  ON a.acc_u_id    =g.acc_u_id4\n" + 
                        "  And a.acc_off_id = G.Acc_Off_Id4\n" + 
                        "  AND a.acc_no     = g.acc_no4\n" + 
                        "    )x\n" + 
                        "  LEFT OUTER JOIN\n" + 
                        "    (SELECT acc_u_id3,\n" + 
                        "      acc_off_id3,\n" + 
                        "      csh_bk_yr3,\n" + 
                        "      9 AS csh_bk_mnth3,\n" + 
                        "      acc_no3,\n" + 
                        "      A_4\n" + 
                        "    FROM\n" + 
                        "      (SELECT acc_u_id3,\n" + 
                        "        acc_off_id3,\n" + 
                        "        csh_bk_yr3,\n" + 
                        "        acc_no3,\n" + 
                        "        SUM(A_4) AS A_4\n" + 
                        "      FROM\n" + 
                        "        (SELECT ACCOUNTING_UNIT_ID AS acc_u_id3,\n" + 
                        "          ACCOUNTING_FOR_OFFICE_ID AS acc_off_id3,\n" + 
                        "          CASHBOOK_YEAR            AS csh_bk_yr3,\n" + 
                        "          ACCOUNT_NO               AS acc_no3,\n" + 
                        "          SUM(DR_AMOUNT)           AS A_4\n" + 
                        "        FROM FAS_BRS_TRANSACTION\n" + 
                        "        WHERE accounting_unit_id             = " +unitcode+  
                        "        AND accounting_for_office_id         ="+offCode+ 
                        "        AND cashbook_year                    =" +passYear+ 
                        "        And Account_No                       = "+accNo+ 
                        "        AND extract(MONTH FROM PASSBOOK_DATE)="+passMonth+  
                        "        AND doc_type                        IN ('P','NT')\n" + 
                        "        GROUP BY ACCOUNTING_UNIT_ID,\n" + 
                        "          ACCOUNTING_FOR_OFFICE_ID,\n" + 
                        "          CASHBOOK_YEAR,\n" + 
                        "          ACCOUNT_NO\n" + 
                        "        UNION ALL\n" + 
                        "        SELECT ACCOUNTING_UNIT_ID  AS acc_u_id3,\n" + 
                        "          ACCOUNTING_FOR_OFFICE_ID AS acc_off_id3,\n" + 
                        "          CASHBOOK_YEAR            AS csh_bk_yr3,\n" + 
                        "          ACCOUNT_NO               AS acc_no3,\n" + 
                        "          SUM(CR_AMOUNT)           AS A_4\n" + 
                        "        FROM FAS_BRS_TRANSACTION\n" + 
                        "        WHERE accounting_unit_id             = " +unitcode+  
                        "        AND accounting_for_office_id         ="+offCode+ 
                        "        AND cashbook_year                    =" +passYear+ 
                        "        And Account_No                       ="+accNo+ 
                        "        AND extract(MONTH FROM PASSBOOK_DATE)="+passMonth+
                        "        AND doc_type                        IN ('SC','NT')\n" + 
                        "        GROUP BY ACCOUNTING_UNIT_ID,\n" + 
                        "          ACCOUNTING_FOR_OFFICE_ID,\n" + 
                        "          CASHBOOK_YEAR,\n" + 
                        "          CASHBOOK_MONTH,\n" + 
                        "          ACCOUNT_NO\n" + 
                        "        )\n" + 
                        "      GROUP BY acc_u_id3,\n" + 
                        "        acc_off_id3,\n" + 
                        "        csh_bk_yr3,\n" + 
                        "        acc_no3\n" + 
                        "      )\n" + 
                        "    )y\n" + 
                        "  ON x.acc_u_id       =y.acc_u_id3\n" + 
                        "  AND x.acc_off_id    = y.acc_off_id3\n" + 
                        "  AND x.csh_bk_yr     = y.csh_bk_yr3\n" + 
                        "  AND x.csh_bk_mnth   = y.csh_bk_mnth3\n" + 
                        "  AND x.acc_no        = y.acc_no3\n" + 
                        "  )YY ON XX.acc_u_id6 = YY.acc_u_id\n" + 
                        "AND XX.acc_off_id6    = YY.acc_off_id\n" + 
                        "AND XX.csh_bk_yr6     = YY.csh_bk_yr\n" + 
                        "And Xx.Csh_Bk_Mnth6   = Yy.Csh_Bk_Mnth\n" + 
                        "AND XX.acc_no6        = YY.acc_no)";
	                
	                System.out.println("s2::::"+s2);
	                try{
	               PreparedStatement pss=con.prepareStatement(s2);
	               ResultSet rss=pss.executeQuery();
		               while(rss.next())
		               {
		            	   
		            	   insertCount++;
		            	  PreparedStatement pss1=con.prepareStatement("insert into FAS_BRS_PART_2C (ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID," +
		            	   		"PASS_SHEET_YEAR,PASS_SHEET_MONTH,ACCOUNT_NO,S1A,S1b,S1D,S2,CHEQUE_CASHED,TWAD_AMOUNT," +
		            	   		"UPDATED_BY_USER_ID,UPDATED_DATE) values (?,?,?,?,?,?,?,?,?,?,?,?,?)");
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
		                   int jk=pss1.executeUpdate();
		                   System.out.println("value jk:::"+jk);
		            	 //  pss1.setInt(15,rss.getInt("total2"));
		            	   
		               }
		               if(insertCount>0)
		               {
		            	   xml=xml+"<flag>success</flag>";
		               }
		               else
		               {
		            	   xml=xml+"<flag>failure</flag>";   
		               }
		            
		               
	                }
	                catch(Exception ee)
	                {
	                	System.out.println("exception in fetching query::::"+ee);
	                }
	                xml = xml + "</response>";
	            	System.out.println(xml);
					out.println(xml); 
			}  */
		if(cmd.equalsIgnoreCase("chkRec"))
		{
			  xml="<response><command>chkRec</command>";
			int isRecord=0;
			int unitcode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
			int offCode=Integer.parseInt(request.getParameter("cmbOffice_code"));
			int passYear=Integer.parseInt(request.getParameter("cboCashBook_Year"));
			int passMonth=Integer.parseInt(request.getParameter("cboCashBook_Month"));
			long accNo=Long.parseLong(request.getParameter("cmbBankAccNo"));
			
			String ss="SELECT COUNT(*) as count_one "+
					" FROM FAS_BRS_PART_2C "+
					" WHERE Accounting_Unit_Id    = "+unitcode+
					" AND Accounting_For_Office_Id="+offCode+
					" AND Pass_Sheet_Year         ="+passYear+
					" AND Pass_Sheet_Month        ="+passMonth+
					" AND ACCOUNT_NO              ="+accNo;
			System.out.println("ss:"+ss);
			try{
				
				  xml=xml+"<flag>success</flag>";
			PreparedStatement ps_one=con.prepareStatement(ss);
			ResultSet rs_one=ps_one.executeQuery();
			while(rs_one.next())
			{
				isRecord=rs_one.getInt("count_one");
			}
			if(isRecord>0)
			{
				  xml=xml+"<proceed>stop</proceed>";
			}
			else
			{
				  xml=xml+"<proceed>start</proceed>";
			}
				
			}
			 catch(Exception e) 
		       {
		              System.out.println("Exception in onload..."+e);
		              xml=xml+"<flag>failure</flag>";
		       }
			
			  xml = xml + "</response>";
				System.out.println(xml);
				out.println(xml);
		}else if(cmd.equalsIgnoreCase("chkPart")){
			  xml="<response><command>chkPart</command>";
			
				int unitcode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
				String param=request.getParameter("part");
				int passYear=Integer.parseInt(request.getParameter("cboCashBook_Year"));
				int passMonth=Integer.parseInt(request.getParameter("cboCashBook_Month"));
				long accNo=Long.parseLong(request.getParameter("cmbBankAccNo"));
				String qry="";
				int count1=0,partA=0,partB=0;
				  xml=xml+"<param>"+param+"</param>";
				if(param.equalsIgnoreCase("2a")){
					qry="SELECT COUNT(*)  as count  " +
							" FROM FAS_BRS_PART_2A part2a " +
							" WHERE part2b.accounting_unit_id         =? " +
							 " and part2a.ACCOUNT_NO    =? " +
							"  AND part2a.pass_sheet_month=? " +
							" AND part2a.pass_sheet_year =?";
				}
				else if(param.equalsIgnoreCase("2b")){
					/*qry="SELECT COUNT(*) as count  " +
							"FROM FAS_BRS_PART_2A part2a, " +
							"  FAS_BRS_PART_2B part2b " +
							" WHERE part2b.accounting_for_office_id = part2a.accounting_for_office_id " +
							" AND part2b.accounting_unit_id         = part2a.accounting_unit_id " +
							" AND part2b.account_no                 = part2a.account_no " +
							" AND part2b.pass_sheet_month           = part2a.pass_sheet_month " +
							" AND part2b.pass_sheet_year            = part2a.pass_sheet_year " +
							" AND part2b.accounting_unit_id         =? " +
							" AND PART2B.ACCOUNT_NO                 =? " +
							" AND part2a.pass_sheet_month           =? " +
							" AND part2a.pass_sheet_year            =?";*/
					
					qry=" select nvl(sum(partA),0) as partA,nvl(sum(partB),0) as partB from (SELECT " +
							"  CASE " +
							"    WHEN type='A' " +
							"    THEN COUNT " +
							"    ELSE 0 " +
							"  END AS partA, " +
							"  CASE " +
							"    WHEN type='B' " +
							"    THEN COUNT " +
							"    ELSE 0 " +
							"  END AS partB " +
							"FROM " +
							"  (SELECT 'A'                AS type, " +
							"    COUNT(PART2a.ACCOUNT_NO) AS COUNT " +
							"  FROM FAS_BRS_PART_2A part2a " +
							"  WHERE part2a.accounting_unit_id=? " +
							"  AND part2a.ACCOUNT_NO          =? " +
							"  AND part2a.pass_sheet_month    =? " +
							"  AND part2a.pass_sheet_year     =? " +
							"  GROUP BY 'A' " +
							"  UNION " +
							"  SELECT 'B'                 AS type, " +
							"    COUNT(PART2a.ACCOUNT_NO) AS COUNT " +
							"  FROM FAS_BRS_PART_2B part2a " +
							"  WHERE part2a.accounting_unit_id=? " +
							"  AND part2a.ACCOUNT_NO          =? " +
							"  AND part2a.pass_sheet_month    =? " +
							"  AND part2a.pass_sheet_year     =? " +
							"  GROUP BY 'B' " +
							"  ))"
							
;
				}
				System.out.println(""+qry);
				try{
				PreparedStatement ps_part=con.prepareStatement(qry);
				ps_part.setInt(1, unitcode);
				ps_part.setLong(2, accNo);
				ps_part.setInt(3, passMonth);
				ps_part.setInt(4, passYear);
				if(param.equalsIgnoreCase("2b")){
					ps_part.setInt(5, unitcode);
					ps_part.setLong(6, accNo);
					ps_part.setInt(7, passMonth);
					ps_part.setInt(8, passYear);
				}
				ResultSet rs_part=ps_part.executeQuery();
			
				if(param.equalsIgnoreCase("2a")){
					
					if(rs_part.next()){
						count1=rs_part.getInt(1);
					}
					
					
					if(count1==0){
					  xml=xml+"<Coutn>"+count1+"</Coutn><flag>success</flag>";
				}else{
					  xml=xml+"<Coutn>"+count1+"</Coutn><flag>success</flag>";
				}
				}else
				{
					
					if(rs_part.next()){
				
						partA=rs_part.getInt(1);
						partB=rs_part.getInt(2);
						count1=1;
					}
					
					if(partA==0 && partB==1)
					{
						xml=xml+"<part>partA</part>";
					}else if(partA==1 && partB==0){
						xml=xml+"<part>partB</part>";
					}	else if(partA==1 && partB==1){
						xml=xml+"<part>OK</part>";
					}else if(partA==0 && partB==0){
						xml=xml+"<part>partAandB</part>";
					}
					  xml=xml+"<Coutn>"+count1+"</Coutn><flag>success</flag>";
				}
				}catch(Exception e){
					e.printStackTrace();
					  xml=xml+"<flag>failure</flag>";
				}
				  xml = xml + "</response>";
					System.out.println(xml);
					out.println(xml);
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
                
		String xml=null;
		Connection con = null;
                String UnitName=null;
                Double amount=0.00,brs_amtcr=0.00,bank_credit=0.00,cr_26=0.00,twad_cr=0.00;
                String mode_id="",totalyear="";
                int bank_id=0,branch_id=0;
                String hid="";	String sub_str2= "",sub1="",sub_noEntry="",sub_noEntry_1="";
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
		   String opr_node="",bankName="",branchName="";
		   String prf_date="";
		int cboAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
		int cboOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
		int cboCashBook_Year = Integer.parseInt(request.getParameter("cboCashBook_Year"));
		int cboCashBook_Month = Integer.parseInt(request.getParameter("cboCashBook_Month"));
		long cmbBankAccNo = Long.parseLong(request.getParameter("cmbBankAccNo"));
		try{
			PreparedStatement ps_node=con.prepareStatement(" SELECT BB.AC_OPERATIONAL_MODE_ID,B.BANK_NAME,BR.BRANCH_NAME FROM FAS_MST_BANK_BALANCE BB,FAS_MST_BANKS B,FAS_MST_BANK_BRANCHES BR WHERE BB.BANK_ID=B.BANK_ID AND B.BANK_ID=BR.BANK_ID AND BB.BRANCH_ID=BR.BRANCH_ID and bank_ac_no= "+cmbBankAccNo)	;
			
			ResultSet rs_node=ps_node.executeQuery();
			while(rs_node.next()){
				opr_node=rs_node.getString("AC_OPERATIONAL_MODE_ID");
				bankName=rs_node.getString("BANK_NAME");
				branchName=rs_node.getString("BRANCH_NAME");
			}
			}catch(Exception e){
				e.printStackTrace();
			}
			
		 String cmd=request.getParameter("command");
		 hid=request.getParameter("old");
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
		String pre_dte="";
		  try {
			    
			    
				 PreparedStatement  ps_l=con.prepareStatement("SELECT to_char(last_day(to_date(date1, 'dd-mm-yy')),'dd-mm-yyyy')ls_date, to_char(last_day(add_months(trunc(to_date(date1, 'dd-mm-yy'),'mm'),-1)),'dd-mm-yyyy') as dte "+
										"  FROM "+
				    		"   (SELECT DISTINCT ('01' "+
				    		" 		      ||'-' "+
				    		" 		      ||"+cboCashBook_Month+
				    		" 		      ||'-' "+
				    		" 		      ||"+cboCashBook_Year+")date1  "+
				    		/*" 		    FROM FAS_BRS_TRANSACTION "+
				    		" 		    WHERE CASHBOOK_YEAR   = "+cboCashBook_Year+
				    		" 		    AND CASHBOOK_MONTH    = "+cboCashBook_Month+
				    		" 		    AND account_no        = "+cmbBankAccNo+
				    		" 		    AND accounting_unit_id= "+cboAcc_UnitCode+" 	"*/
				    				 "	    )");
				    
				  ResultSet  rs_l=ps_l.executeQuery();
				    if(rs_l.next())
				    {
				    String  last_date_one =rs_l.getString("ls_date");
				   pre_dte=rs_l.getString("dte");
				    System.out.println("last_date_one::"+last_date_one);
				    String[] splto=last_date_one.split("-");
				    String[] splpre=pre_dte.split("-");
				  String smonth="",smonth_pre="";
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
				    
				    if(splpre[1].equals("01"))
				    {
				    	smonth_pre="jan";
				    }
				    else if(splpre[1].equals("02"))
				    {
				    	smonth_pre="feb";
				    }else if(splpre[1].equals("03"))
				    {
				    	smonth_pre="mar";
				    }else if(splpre[1].equals("04"))
				    {
				    	smonth_pre="apr";
				    }else if(splpre[1].equals("05"))
				    {
				    	smonth_pre="may";
				    }else if(splpre[1].equals("06"))
				    {
				    	smonth_pre="jun";
				    }else if(splpre[1].equals("07"))
				    {
				    	smonth_pre="jul";
				    }else if(splpre[1].equals("08"))
				    {
				    	smonth_pre="aug";
				    }else if(splpre[1].equals("09"))
				    {
				    	smonth_pre="sep";
				    }else if(splpre[1].equals("10"))
				    {
				    	smonth_pre="oct";
				    }else if(splpre[1].equals("11"))
				    {
				    	smonth_pre="nov";
				    }else if(splpre[1].equals("12"))
				    {
				    	smonth_pre="dec";
				    }
				    
				    
				    totalyear=splto[0]+"-"+smonth+"-"+splto[2];
				     prf_date=splpre[0]+"-"+smonth_pre+"-"+splpre[2];
				    System.out.println("totalyear:::"+totalyear);
				   
				    }
				    
				    }
				    catch (SQLException e) {
				        System.out.println("SQL Exception -->"+e);
				    }
		
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
		
		 try{
      	  
      	   String bankCredit="SELECT DECODE(SUM(Cr_Amount),NULL,0,SUM(Cr_Amount))AS cramt "+
							  " FROM FAS_BRS_TRANSACTION "+
      		 " WHERE accounting_unit_id                =  "+cboAcc_UnitCode+
      		 " 			  AND Accounting_For_Office_Id            =  "+cboOffice_code+
      		 " 			  AND ( (cashbook_year < "+cboCashBook_Year+" and cashbook_month<=12)  " +
      		 "OR (cashbook_year    = "+cboCashBook_Year+" AND cashbook_month  <="+cboCashBook_Month+")) "+
      		 " 			  AND Account_No                          =  "+cmbBankAccNo+
      		 " 			  AND Twad_Or_Non_Twad                    ='NT' "+
      		 " 			  AND Doc_Type                            ='NT' "+
      		 " 			  AND Transaction_Type                    =26 "+
      		 " 			  AND (CLEARED_BASED_ON_FOLLOWUP='Y'  "+
      		 " 			   AND  (extract (year from clearence_date) < "+cboCashBook_Year+" and extract (month from clearence_date)<=12)  "+ 
      		 " 			   OR (extract (year from clearence_date)    = "+cboCashBook_Year+" AND extract (month from clearence_date)  <="+cboCashBook_Month+"))";
      	//   System.out.println("bankCredit:"+bankCredit);
     	    PreparedStatement pstat=con.prepareStatement(bankCredit);
     	    ResultSet set1=pstat.executeQuery();
     	    if(set1.next()){
     	    	bank_credit=set1.getDouble("cramt");
     	       
                 }
     	    
	       	 
         }
         catch(Exception eee)
         {
      	System.out.println("exception in bankcredit:::"+eee);   
         }
         System.out.println(bank_credit);
         
         try{
        	 String only_26f="SELECT SUM(cramt)as cr_26 "+
						" FROM "+
        		 "   (SELECT DECODE(SUM(Cr_Amount),NULL,0,SUM(Cr_Amount))AS cramt "+
        		 " 	  FROM FAS_BRS_TRANSACTION "+
        		 " 	  WHERE accounting_unit_id             =  "+cboAcc_UnitCode+
        		 " 	  AND Accounting_For_Office_Id =  "+cboOffice_code+
        		 " 	  AND ( (extract(YEAR FROM Passbook_Date) < "+cboCashBook_Year+") "+
        		 " 	  OR (Extract(YEAR FROM Passbook_Date) = "+cboCashBook_Year+
        		 " 	  AND extract(MONTH FROM Passbook_Date) <="+cboCashBook_Month+")) "+
        		 " 	  AND Account_No                       =  "+cmbBankAccNo+
        		 " 	  AND Twad_Or_Non_Twad ='NT' "+
        		 " 	  AND Doc_Type                         ='NT' "+
        		 " 	  AND Transaction_Type                 =26)";
        		
        	 
        	 PreparedStatement pstat1=con.prepareStatement(only_26f);
      	    ResultSet set11=pstat1.executeQuery();
      	    if(set11.next()){
      	    	cr_26=set11.getDouble("cr_26");
      	       
                  }
         }
         catch(Exception ee)
         {
        	 System.out.println("exception in2:::"+ee);
         }
         twad_cr=(cr_26-bank_credit);
         System.out.println("TWAD Board amount credited:::"+twad_cr);
         
         
         int c_new=0;String c_flag="";
         
         /// For there s no records in any transaction and that stuation we need to check previous month records
         
      try{   PreparedStatement ps_new=con.prepareStatement(
     		 "SELECT ACCOUNTING_UNIT_ID  AS acc_u_id, " +
         		"  ACCOUNTING_FOR_OFFICE_ID AS acc_off_id, " +
         		"  CASHBOOK_YEAR            AS csh_bk_yr, " +
         		"  CASHBOOK_MONTH           AS csh_bk_mnth, " +
         		"  Account_No               AS Acc_No, " +
         		"  SUM(total_amount)        AS A_2a " +
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
         		"  SUM(Total_Amount)        AS A_2a " +
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
         		"  SUM(Total_Amount)        AS A_2a " +
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
         		"  SUM(Total_Amount)        AS A_2a " +
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
     	 c_flag="SELECT ACCOUNTING_UNIT_ID AS acc_u_id,\n" + 
     	         "      ACCOUNTING_FOR_OFFICE_ID AS acc_off_id,\n" + 
     	        cboCashBook_Year+   "                 AS csh_bk_yr,\n" + 
     	        cboCashBook_Month+ "                 AS csh_bk_mnth,\n" + 
     	         "      Account_No               AS Acc_No,\n" + 
     	         "      SUM(total_amount)        AS Only_4\n" + 
     	         "    FROM FAS_payment_master\n" + 
     	        "          WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
 	            "          AND accounting_for_office_id=" +cboOffice_code+ 
 	            "          AND cashbook_month          = "+ month_new+ 
 	            "          AND Cashbook_Year           =" +year_new+ 
 	            "          AND Account_No              = " +cmbBankAccNo+ 
     	         "    AND Payment_Status          ='L'\n" + 
     	         "    GROUP BY Accounting_Unit_Id,\n" + 
     	         "      Accounting_For_Office_Id,\n" + 
     	         "      Cashbook_Year,\n" + 
     	         "      Cashbook_Month,\n" + 
     	         "      Account_No\n" ;
     	 
     	 
      }else if(c_new!=0)
      {
     	 c_flag="SELECT ACCOUNTING_UNIT_ID AS acc_u_id,\n" + 
     	         "      ACCOUNTING_FOR_OFFICE_ID AS acc_off_id,\n" + 
     	         "      CASHBOOK_YEAR            AS csh_bk_yr,\n" + 
     	         "      CASHBOOK_MONTH           AS csh_bk_mnth,\n" + 
     	         "      Account_No               AS Acc_No,\n" + 
     	         "      SUM(total_amount)        AS Only_4\n" + 
     	         "    FROM FAS_payment_master\n" + 
     	         "    WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
     	         "    And Accounting_For_Office_Id=" +cboOffice_code+ 
     	         "    AND cashbook_month          = " +cboCashBook_Month+ 
     	         "    AND Cashbook_Year           =" +cboCashBook_Year+ 
     	         "    AND ACCOUNT_NO              = " +cmbBankAccNo+ 
     	         "    AND Payment_Status          ='L'\n" + 
     	         "    GROUP BY Accounting_Unit_Id,\n" + 
     	         "      Accounting_For_Office_Id,\n" + 
     	         "      Cashbook_Year,\n" + 
     	         "      Cashbook_Month,\n" + 
     	         "      Account_No\n"  ;
      }
      double uptoDR = 0,uptoCR = 0,forDR = 0,forCR = 0,notreconcile_uptoDR=0,notreconcile_uptoCR=0,notreconcile_forDR=0,notreconcile_forCR=0;
         
         int cboCashBook_Year1=cboCashBook_Year-1;
         int cboCashBook_Month1=0;
        String month1="";
         if(cboCashBook_Month==12)
         {
        	 cboCashBook_Month1=1;
        	
         }else{
        	 cboCashBook_Month1=cboCashBook_Month+1;
         }
         if(cboCashBook_Month1 == 1){
 			month1 = "January";
 		}else if(cboCashBook_Month1 == 2){
 			month1 = "Febrary";
 		}else if(cboCashBook_Month1 == 3){
 			month1 = "March";
 		}else if(cboCashBook_Month1 == 4){
 			month1 = "April";
 		}else if(cboCashBook_Month1 == 5){
 			month1 = "May";
 		}else if(cboCashBook_Month1 == 6){
 			month1 = "June";
 		}else if(cboCashBook_Month1 == 7){
 			month1 = "July";
 		}else if(cboCashBook_Month1 == 8){
 			month1 = "August";
 		}else if(cboCashBook_Month1 == 9){
 			month1 = "September";
 		}else if(cboCashBook_Month1 == 10){
 			month1 = "October";
 		}else if(cboCashBook_Month1 == 11){
 			month1 = "November";
 		}else if(cboCashBook_Month1 == 12){
 			month1 = "December";
 		}	 
         
                  String sql_que="SELECT DECODE(OFFICE_NAME,NULL,'-',OFFICE_NAME) AS OFFICE_NAME,\n" + 
         "'"+bankName+"'  AS BANK_NAME,\n" + 
         "'"+branchName+"'   AS BRANCH_NAME,\n" + 
         "  acc_u_id,\n" + 
         "  acc_off_id,\n" + 
         "  csh_bk_yr,\n" + 
         "  csh_bk_mnth,\n" + 
         cmbBankAccNo+"  acc_no,\n" + 
         "  nvl(Only_4,0) as Only_4,decode(a_2a,null,0,a_2a)a_2a,\n" + 
         "  nvl(A_4,0) as A_4,\n" + 
         "  ( (DECODE(Ob_Part2a,NULL,0,Ob_Part2a)+nvl(A_2e,0))-nvl(A_4,0)) AS A_5,\n" + 
         "  nvl(Passbook_Balance,0) as Passbook_Balance,\n" + 
         "  (SELECT Initial_Deposit_Amt\n" + 
         "  FROM Fas_Mst_Bank_Balance\n" + 
         "  Where Accounting_Unit_Id=" +cboAcc_UnitCode+ 
         "  AND Bank_Ac_No          =" +cmbBankAccNo+ 
         "  )AS Idamt,\n" + 
         "  nvl(Ob_Part2b,0) as Ob_Part2b,\n" + 
         "  DECODE(Intallowed,NULL,0,Intallowed)as Intallowed,\n" + 
         "(decode(Intallowed,null,0,Intallowed)+nvl(A_6a,0)) as int6aAdd,\n" + 
         "  (SELECT SUM(DR_AMOUNT)\n" + 
         "  FROM FAS_BRS_TRANSACTION\n" + 
         "  WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
         "  AND Accounting_For_Office_Id= " +cboOffice_code+ 
         "  AND (extract(YEAR FROM PASSBOOK_DATE) <" +cboCashBook_Year+ 
         " Or (Extract(Year From Passbook_Date)  =" +cboCashBook_Year+ 
         " AND extract(MONTH FROM PASSBOOK_DATE)<"+cboCashBook_Month+"))\n" + 
         "  AND ACCOUNT_NO              = " +cmbBankAccNo+ 
         "  AND Twad_Or_Non_Twad        ='NT'\n" + 
         "  AND Transaction_Type       IN(3,23,12)\n" + 
         "  GROUP BY ACCOUNTING_UNIT_ID,\n" + 
         "    ACCOUNTING_FOR_OFFICE_ID,\n" + 
         "    Account_No\n" + 
         "  )AS Rfordiff,\n" + 
         // Joan Added on 23 Jan 2015 for Bank Credit up to previous Month
         "    (SELECT DECODE(SUM(CR_AMOUNT),NULL,0,SUM(CR_AMOUNT)) \n" + 
         "     FROM FAS_BRS_TRANSACTION \n" + 
         "     WHERE accounting_unit_id      = " +cboAcc_UnitCode+ 
      "      AND Accounting_For_Office_Id=  " +cboOffice_code+ 
      "      AND Account_No              =  " +cmbBankAccNo+ 
      "      AND EXTRACT(YEAR FROM PASSBOOK_DATE)  in (" +cboCashBook_Year+ ","+cboCashBook_Year1+")"+
    //   "     AND extract(MONTH FROM PASSBOOK_DATE) <= " +cboCashBook_Month+ 
      
    // Joan chengd on 17 Mar 2015 for upto month not contain current month data
    //  " and passbook_date < '01-"+month1.substring(0,3)+"-"+cboCashBook_Year+"'"+
    
 " and passbook_date < '01-"+month.substring(0,3)+"-"+cboCashBook_Year+"'"+
       
       "     AND TWAD_OR_NON_TWAD        ='NT' \n" + 
       "     AND TRANSACTION_TYPE     in (39,40) \n" + 
       "     )AS Bank_Cr, \n" + 
         "  (select sum(cramt) from\n" + 
         "(SELECT decode(SUM(Cr_Amount),null,0,sum(Cr_Amount))as cramt\n" + 
         " FROM FAS_BRS_TRANSACTION\n" + 
         " WHERE accounting_unit_id                = " +cboAcc_UnitCode+ 
         " AND Accounting_For_Office_Id            = " +cboOffice_code+ 
         " AND ( (extract(YEAR FROM Passbook_Date) < "+cboCashBook_Year+")\n" + 
         " Or (Extract(Year From Passbook_Date)    =" +cboCashBook_Year+ 
         " AND extract(MONTH FROM Passbook_Date)  <="+cboCashBook_Month+"))\n" + 
         " AND Account_No                          = " +cmbBankAccNo+ 
         " AND Twad_Or_Non_Twad                    ='NT'\n" + 
         " AND Doc_Type                            ='NT'\n" + 
         " AND Transaction_Type                    =26\n" + 
         " Union All \n" + 
         " SELECT decode(SUM(Cr_Amount),null,0,sum(Cr_Amount))as cramt\n" + 
         " FROM FAS_BRS_TRANSACTION\n" + 
         " WHERE accounting_unit_id                = " +cboAcc_UnitCode+ 
         " AND Accounting_For_Office_Id            = " +cboOffice_code+ 
         " AND ( (extract(YEAR FROM DOC_DATE) < "+cboCashBook_Year+")\n" + 
         " OR (extract(YEAR FROM DOC_DATE)    =" +cboCashBook_Year+ 
         " AND extract(MONTH FROM DOC_DATE)  <="+cboCashBook_Month+"))\n" + 
         " AND Account_No                          = " +cmbBankAccNo+ 
         " And Twad_Or_Non_Twad                    ='NT'\n" + 
         " And Doc_Type                            in ('J')\n" + 
         " And Transaction_Type                    =26\n" + 
         " and Cleared_Based_On_Followup!='Y')\n" + 
         "  )AS Only_26,\n" + 
         "  (SELECT SUM(DR_AMOUNT)\n" + 
         "  FROM FAS_BRS_TRANSACTION\n" + 
         "  WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
         "  AND Accounting_For_Office_Id= " +cboOffice_code+ 
         "  AND Cashbook_Year           = " +cboCashBook_Year+ 
         "  AND ACCOUNT_NO              = " +cmbBankAccNo+ 
         "  AND Cashbook_Month          = " +cboCashBook_Month+ 
         "  AND Twad_Or_Non_Twad        ='NT'\n" + 
         "  AND Transaction_Type        =3\n" + 
         "  )AS Three_Dr,\n" + 
         "  (SELECT SUM(DR_AMOUNT)\n" + 
         "  FROM FAS_BRS_TRANSACTION\n" + 
         "  WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
         "  AND Accounting_For_Office_Id= " +cboOffice_code+ 
         "  AND Account_No              = " +cmbBankAccNo+ 
       //  "  AND Cashbook_Year           = " +cboCashBook_Year+ 
       //  "  AND Cashbook_Month          = " +cboCashBook_Month+ 
        " AND ((CASHBOOK_YEAR < "+cboCashBook_Year+") OR (CASHBOOK_YEAR    ="+cboCashBook_Year+

        //joan Chenge on 17 Mar 2015 reason Four_Cr upto month not contain current month value
        
    //    " AND CASHBOOK_MONTH  <="+cboCashBook_Month+")) "+
        " AND CASHBOOK_MONTH  < "+cboCashBook_Month+")) "+
         "  AND Twad_Or_Non_Twad        ='NT'\n" + 
      //   "  AND Transaction_Type        =4\n" + 
         "   AND Transaction_Type        in (6,7,31,1)   )AS Four_Cr,\n" + 
     
         // Joan Change on 19 Dec 2014 for unit 374 Acc end with '%250' Amount  1500-200 = 1300 Issue 
         
         /*   "  (SELECT SUM(DR_AMOUNT)\n" + 
         "  FROM FAS_BRS_TRANSACTION\n" + 
         "  WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
         "  AND Accounting_For_Office_Id= " +cboOffice_code+ 
         "   AND extract(year from PASSBOOK_DATE)           = " +cboCashBook_Year+ 
         "  AND Account_No              = " +cmbBankAccNo+ 
         "  AND extract(month from PASSBOOK_DATE)          = " +cboCashBook_Month+ 
         "  AND Twad_Or_Non_Twad        ='NT'\n" + 
         "  AND Transaction_Type        in(6,12)\n" + 
         "  )AS Com_Charges,\n" + */
         
         
      // Joan Changed on 31Mar2015 for Commision Charges ONly taken so remoove Transaction_Type 12,24,2
                        
         "  (SELECT SUM(DR_AMOUNT)-SUM(CR_AMOUNT) \n" + 
         "  FROM FAS_BRS_TRANSACTION\n" + 
         "  WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
         "  AND Accounting_For_Office_Id= " +cboOffice_code+ 
         "   AND extract(year from PASSBOOK_DATE)           = " +cboCashBook_Year+ 
         "  AND Account_No              = " +cmbBankAccNo+ 
         "  AND extract(month from PASSBOOK_DATE)          <= " +cboCashBook_Month+ 
         "  AND Twad_Or_Non_Twad        ='NT'\n" + 
      //   "  AND Transaction_Type        in(6,12,24,2)\n" + 
      "  AND Transaction_Type        in (6)\n" + 
         "  )AS Com_Charges,\n" +     
         
         
         // joan added on 17 Mar 2015 6.35 PM reason : Service charge for the month & upto the month
         "    (SELECT SUM(DR_AMOUNT)-SUM(CR_AMOUNT)\n" + 
         "   		  FROM FAS_BRS_TRANSACTION\n" + 
         "   		  WHERE accounting_unit_id               =  " +cboAcc_UnitCode+ 
         "   		  AND Accounting_For_Office_Id           = " +cboOffice_code+ 
         "   		  AND extract(YEAR FROM PASSBOOK_DATE)   = " +cboCashBook_Year+ 
         "   		  AND Account_No                         =" +cmbBankAccNo+ 
         "   		  AND extract(MONTH FROM PASSBOOK_DATE) = " +cboCashBook_Month+ 
         "  		  AND TWAD_OR_NON_TWAD                   ='NT'\n" + 
         "  		  AND TRANSACTION_TYPE                  IN(8)\n" + 
         "  		  )AS sev_for_CHARGES,\n" + 
        		  "  		   (SELECT SUM(DR_AMOUNT)-SUM(CR_AMOUNT)\n" + 
        		  "  		  FROM FAS_BRS_TRANSACTION\n" + 
        		  "  		  WHERE accounting_unit_id               =   " +cboAcc_UnitCode+ 
        				  "  		  AND Accounting_For_Office_Id           = " +cboOffice_code+ 
        				  "  		  AND extract(YEAR FROM PASSBOOK_DATE)   = " +cboCashBook_Year+ 
        				  "  		  AND ACCOUNT_NO                         = " +cmbBankAccNo+ 
        				  "  		  AND extract(MONTH FROM PASSBOOK_DATE) <  " +cboCashBook_Month+ 
        				  "  	  AND TWAD_OR_NON_TWAD                   ='NT'\n" + 
        				  "  	  AND TRANSACTION_TYPE                  IN(8)\n" + 
        		  "  	  )AS service_Charges,\n" + 
         
         
  // Joan Changed on 31Mar2015 for Credit Charges ONly taken so except Transaction_Type 6 / credit amount
         "  (SELECT SUM(CR_AMOUNT)\n" + 
         "  FROM FAS_BRS_TRANSACTION\n" + 
         "  WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
         "  AND Accounting_For_Office_Id= " +cboOffice_code+ 
         "  AND Cashbook_Year           = " +cboCashBook_Year+ 
         "  AND Account_No              = " +cmbBankAccNo+ 
         "  AND Cashbook_Month          = " +cboCashBook_Month+ 
         "  AND Twad_Or_Non_Twad        ='NT'\n" + 
     //    "  AND Transaction_Type        =27\n" + 
     "  AND Transaction_Type        <> 6 \n" + 
         "  )AS err_depo,"+
     
         
         
  
// Joan Added on 31Mar2015 for Debit Charges ONly taken so except Transaction_Type 6 / Debit amount
    "  (SELECT SUM(DR_AMOUNT)\n" + 
    "  FROM FAS_BRS_TRANSACTION\n" + 
    "  WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
    "  AND Accounting_For_Office_Id= " +cboOffice_code+ 
    "  AND Cashbook_Year           = " +cboCashBook_Year+ 
    "  AND Account_No              = " +cmbBankAccNo+ 
    "  AND Cashbook_Month          = " +cboCashBook_Month+ 
    "  AND Twad_Or_Non_Twad        ='NT'\n" + 
//    "  AND Transaction_Type        =27\n" + 
"  AND Transaction_Type        <> 6 \n" + 
    "  )AS Miscl_deb,"+
       // Joan Added on 23 Jun 2015
    
       
  "  (SELECT S5\n" + 
  "  	  FROM FAS_BRS_PART_2B\n" + 
  "  	  WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
  "  	  AND Accounting_For_Office_Id= " +cboOffice_code+ 
  "  	  AND PASS_SHEET_YEAR           =  " +cboCashBook_Year+ 
  "  	  AND Account_No              = " +cmbBankAccNo+ 
  "  	  AND PASS_SHEET_MONTH          =  " +cboCashBook_Month+ 
 	 
  "  	  )                        AS Balance,\n" + 
  "  	   (SELECT S5A\n" + 
  "  	  FROM FAS_BRS_PART_2A\n" + 
  "  	  WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
  "  	  AND Accounting_For_Office_Id= " +cboOffice_code+ 
  "  	  AND PASS_SHEET_YEAR           = " +cboCashBook_Year+ 
  "  	  AND Account_No              = " +cmbBankAccNo+ 
  "  	  AND PASS_SHEET_MONTH          =  " +cboCashBook_Month+ 
		 
  "  	  ) as  uncheque                 ,\n" + 
        "decode(A_2e,null,0,A_2e) as A_2e,decode(A_6a,null,0,A_6a)as A_6a \n" + 
         " FROM (\n" + 
         "  (SELECT Acc_U_Id6,\n" + 
         "    Acc_Off_Id6,\n" + 
         "    Csh_Bk_Yr6,\n" + 
         "    Csh_Bk_Mnth6,\n" + 
         "    Acc_No6,\n" + 
         "    Ob_Part2a,\n" + 
         "    OB_PART2B,\n" + 
         "    OFFICE_NAME,\n" + 
         "    BANK_NAME,\n" + 
         "    BRANCH_NAME,\n" + 
         "    PASSBOOK_BALANCE,(SELECT CR_AMOUNT\n" + 
         "  FROM FAS_BRS_TRANSACTION\n" + 
         "  WHERE TRANSACTION_TYPE      in (14,31,39,40)\n" + 
         "  AND accounting_unit_id      = " +cboAcc_UnitCode+ 
         "  AND accounting_for_office_id=" +cboOffice_code+ 
         "  AND cashbook_year           =" +cboCashBook_Year+ 
         "  AND ACCOUNT_NO              = " +cmbBankAccNo+ 
         "  AND Cashbook_Month          = " +cboCashBook_Month+ 
         "  AND Twad_Or_Non_Twad        ='NT'\n" + 
         "  )AS Intallowed\n" + 
         "  FROM\n" + 
         "    (SELECT rownum AS slno1,\n" + 
         "      acc_u_id6,\n" + 
         "      acc_off_id6,\n" + 
         "      csh_bk_yr6,\n" + 
         "      csh_bk_mnth6,\n" + 
         "      Acc_No6,\n" + 
         "      OB_PART2A,\n" + 
         "      OB_PART2B,\n" + 
         "      OFFICE_NAME\n" + 
         "    FROM\n" + 
         "      (SELECT ACCOUNTING_UNIT_ID AS acc_u_id6,\n" + 
         "        ACCOUNTING_FOR_OFFICE_ID AS acc_off_id6,\n" + 
         "        CASHBOOK_YEAR            AS csh_bk_yr6,\n" + 
         "        CASHBOOK_MONTH           AS csh_bk_mnth6,\n" + 
         "        Account_No               AS Acc_No6,\n" + 
         "        OB_PART2A,\n" + 
         "        OB_PART2B\n" + 
         "      FROM FAS_BRS_OB\n" + 
         "      WHERE accounting_unit_id    =" +cboAcc_UnitCode+ 
         "      AND accounting_for_office_id= " +cboOffice_code+ 
         "      AND cashbook_month          = " +cboCashBook_Month+ 
         "      AND cashbook_year           =" +cboCashBook_Year+ 
         "      AND ACCOUNT_NO              = " +cmbBankAccNo+ 
         "      )AUID1\n" + 
         "    LEFT OUTER JOIN\n" + 
         "      (SELECT OFFICE_ID,OFFICE_NAME FROM COM_MST_OFFICES\n" + 
         "      )AUID2\n" + 
         "    ON AUID1.acc_off_id6 = AUID2.OFFICE_ID\n" + 
         "    ) t1\n" + 
         "  LEFT OUTER JOIN\n" + 
         "    (SELECT Slno2,\n" + 
         "      Bank_Name,\n" + 
         "      Branch_Name,\n" + 
         "      PASSBOOK_BALANCE\n" + 
         "    FROM\n" + 
         "      (SELECT rownum AS slno2,\n" + 
         "        BANK_ID,\n" + 
         "        BRANCH_ID,\n" + 
         "        PASSBOOK_BALANCE\n" + 
         "      FROM FAS_BRS_MASTER\n" + 
         "      WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
         "      AND accounting_for_office_id= " +cboOffice_code+ 
         "      AND cashbook_month          = " +cboCashBook_Month+ 
         "      AND cashbook_year           =" +cboCashBook_Year+ 
         "      AND ACCOUNT_NO              = " +cmbBankAccNo+ 
         "      )sss\n" + 
         "    LEFT OUTER JOIN\n" + 
         "      (SELECT BRANCH_ID AS brnch_id,\n" + 
         "        BANK_ID         AS bnk_id,\n" + 
         "        BRANCH_NAME\n" + 
         "      FROM FAS_MST_BANK_BRANCHES\n" + 
         "      )c\n" + 
         "    ON sss.BANK_ID    = c.bnk_id\n" + 
         "    AND sss.BRANCH_ID = c.brnch_id\n" + 
         "    LEFT OUTER JOIN\n" + 
         "      (SELECT BANK_ID AS bnk_id1,BANK_NAME FROM FAS_BANK_LIST\n" + 
         "      )d\n" + 
         "    ON sss.BANK_ID  = d.bnk_id1\n" + 
         "    )t2 ON t1.slno1 = t2.slno2\n" + 
         "  )XX\n" + 
         " LEFT OUTER JOIN\n" + 
         "  (SELECT acc_u_id,\n" + 
         "    acc_off_id,\n" + 
         "    csh_bk_yr,\n" + 
         "    csh_bk_mnth,\n" + 
         "    acc_no,\n" + 
         "    Only_4,a_2a,\n" + 
         "    A_2b,\n" + 
         "    A_2c,\n" + 
         "    A_2d,\n" + 
         "    A_2e,\n" + 
         "    A_4,decode(A_6a,null,0,A_6a)as A_6a \n" + 
         "  FROM\n" + 
         "    (SELECT acc_u_id,\n" + 
         "      acc_off_id,\n" + 
         "      csh_bk_yr,\n" + 
         "      csh_bk_mnth,\n" + 
         "      acc_no,\n" + 
         "      Only_4,a_2a,\n" + 
         "      A_2b,\n" + 
         "      Only_4+A_2b AS A_2c,\n" + 
         "      A_2d,\n" + 
         " ((Only_4+decode(A_2b,null,0,A_2b))-decode(A_2d,null,0,A_2d)) As A_2e,\n" + 
         "      --(Only_4+A_2b)-A_2d AS A_2e,\n" + 
         "      A_6a\n" + 
         "    FROM\n" + 
         "      (SELECT acc_u_id,\n" + 
         "        acc_off_id,\n" + 
         "        csh_bk_yr,\n" + 
         "        csh_bk_mnth,\n" + 
         "        acc_no,\n" + 
         "        SUM(Only_4) AS Only_4\n" + 
         "      FROM\n" + 
         "        (\n" + 
         "        SELECT acc_u_id,\n" + 
         "    acc_off_id,\n" + 
         "    csh_bk_yr,\n" + 
         "    csh_bk_mnth,\n" + 
         "    acc_no,\n" + 
         "    SUM(Only_4) AS Only_4\n" + 
         "  FROM\n" + 
         "    ( "
         + "SELECT ACCOUNTING_UNIT_ID AS acc_u_id,\n" + 
         "      ACCOUNTING_FOR_OFFICE_ID AS acc_off_id,\n" + 
         "      CASHBOOK_YEAR            AS csh_bk_yr,\n" + 
         "      CASHBOOK_MONTH           AS csh_bk_mnth,\n" + 
         "      Account_No               AS Acc_No,\n" + 
         "      SUM(total_amount)        AS Only_4\n" + 
         "    FROM FAS_payment_master\n" + 
         "    WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
         "    And Accounting_For_Office_Id=" +cboOffice_code+ 
         "    AND cashbook_month          = " +cboCashBook_Month+ 
         "    AND Cashbook_Year           =" +cboCashBook_Year+ 
         "    AND ACCOUNT_NO              = " +cmbBankAccNo+ 
         "    AND Payment_Status          ='L'\n" + 
         "    GROUP BY Accounting_Unit_Id,\n" + 
         "      Accounting_For_Office_Id,\n" + 
         "      Cashbook_Year,\n" + 
         "      Cashbook_Month,\n" + 
         "      Account_No\n" + 
         //+c_flag+
         "    UNION ALL\n" + 
         "    SELECT ACCOUNTING_UNIT_ID  AS acc_u_id,\n" + 
         "      ACCOUNTING_FOR_OFFICE_ID AS acc_off_id,\n" + 
         "      CASHBOOK_YEAR            AS csh_bk_yr,\n" + 
         "      CASHBOOK_MONTH           AS csh_bk_mnth,\n" + 
         "      Account_No               AS Acc_No,\n" + 
         "      SUM(Total_Amount)        AS Only_4\n" + 
         "    FROM FAS_receipt_master\n" + 
         "    WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
         "    And Accounting_For_Office_Id=" +cboOffice_code+ 
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
         "  SUM(Total_Amount)        AS Only_4 " +
         " FROM FAS_FUND_TRF_FROM_OFFICE " +
         "    WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
         "    And Accounting_For_Office_Id=" +cboOffice_code+ 
         "    AND cashbook_month          = " +cboCashBook_Month+ 
         "    AND Cashbook_Year           =" +cboCashBook_Year+ 
         "  and TRANSFER_STATUS <> 'C'  AND OFFICE_ACCOUNT_NO              = " +cmbBankAccNo+ 
         " GROUP BY Accounting_Unit_Id, " +
         "  Accounting_For_Office_Id, " +
         "  Cashbook_Year, " +
         "  Cashbook_Month, " +
         "  OFFICE_ACCOUNT_NO" +
         " UNION ALL " +
         " SELECT ACCOUNTING_UNIT_ID  AS acc_u_id, " +
         "  ACCOUNTING_FOR_OFFICE_ID AS acc_off_id, " +
         "  CASHBOOK_YEAR            AS csh_bk_yr, " +
         "  CASHBOOK_MONTH           AS csh_bk_mnth, " +
         "  FROM_ACCOUNT_NO        AS Acc_No, " +
         "  SUM(Total_Amount)        AS Only_4 " +
         " FROM FAS_INTER_BANK_TRF_AT_HO " +
         "    WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
         "    And Accounting_For_Office_Id=" +cboOffice_code+ 
         "    AND cashbook_month          = " +cboCashBook_Month+ 
         "    AND Cashbook_Year           =" +cboCashBook_Year+ 
         "  and TRANSFER_STATUS <> 'C'  AND FROM_ACCOUNT_NO              = " +cmbBankAccNo+ 
         " GROUP BY Accounting_Unit_Id, " +
         "  Accounting_For_Office_Id, " +
         "  Cashbook_Year, " +
         "  Cashbook_Month, " +
         "  FROM_ACCOUNT_NO" +
         "    )\n" + 
         "  GROUP BY acc_u_id,\n" + 
         "    acc_off_id,\n" + 
         "    csh_bk_yr,\n" + 
         "    csh_bk_mnth,\n" + 
         "    Acc_No\n" + 
         "  \n" + 
         "        )\n" + 
         "      GROUP BY acc_u_id,\n" + 
         "        acc_off_id,\n" + 
         "        csh_bk_yr,\n" + 
         "        csh_bk_mnth,\n" + 
         "        acc_no\n" + 
         "      )a\n" + 
         "    LEFT OUTER JOIN\n" + 
         "      (SELECT ACCOUNTING_UNIT_ID AS acc_u_id1,\n" + 
         "        ACCOUNTING_FOR_OFFICE_ID AS acc_off_id1,\n" + 
         "        CASHBOOK_YEAR            AS csh_bk_yr1,\n" + 
         "        CASHBOOK_MONTH           AS csh_bk_mnth1,\n" + 
         "        ACCOUNT_NO               AS acc_no1,\n" + 
         "        SUM(CR_AMOUNT)           AS A_2b\n" + 
         "      FROM FAS_BRS_TRANSACTION_NOENTRY\n" + 
         "      WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
         "      And Accounting_For_Office_Id=" +cboOffice_code+ 
         "      AND cashbook_month          = " +cboCashBook_Month+ 
         "      AND cashbook_year           =" +cboCashBook_Year+ 
         "      AND ACCOUNT_NO              = " +cmbBankAccNo+
         "      AND doc_type                ='J'\n" + 
         "      GROUP BY ACCOUNTING_UNIT_ID,\n" + 
         "        ACCOUNTING_FOR_OFFICE_ID,\n" + 
         "        CASHBOOK_YEAR,\n" + 
         "        CASHBOOK_MONTH,\n" + 
         "        ACCOUNT_NO\n" + 
         "      )b\n" + 
         "    ON a.acc_u_id     =b.acc_u_id1\n" + 
         "    AND a.acc_off_id  = b.acc_off_id1\n" + 
         "    AND a.csh_bk_yr   = b.csh_bk_yr1\n" + 
         "    AND a.csh_bk_mnth = b.csh_bk_mnth1\n" + 
         "    AND a.acc_no      = b.acc_no1\n" + 
         "    LEFT OUTER JOIN\n" + 
         "      (SELECT ACCOUNTING_UNIT_ID AS acc_u_id2,\n" + 
         "        ACCOUNTING_FOR_OFFICE_ID AS acc_off_id2,\n" + 
         "        CASHBOOK_YEAR            AS csh_bk_yr2,\n" + 
         "        CASHBOOK_MONTH           AS csh_bk_mnth2,\n" + 
         "        ACCOUNT_NO               AS acc_no2,\n" + 
         "        SUM(DR_AMOUNT)           AS A_2d\n" + 
         "      FROM FAS_BRS_TRANSACTION_NOENTRY\n" + 
         "      WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
         "      And Accounting_For_Office_Id=" +cboOffice_code+ 
         "      AND cashbook_month          = " +cboCashBook_Month+ 
         "      AND cashbook_year           =" +cboCashBook_Year+ 
         "      AND ACCOUNT_NO              = " +cmbBankAccNo+ 
         "      AND doc_type                ='J'\n" + 
         "      GROUP BY ACCOUNTING_UNIT_ID,\n" + 
         "        ACCOUNTING_FOR_OFFICE_ID,\n" + 
         "        CASHBOOK_YEAR,\n" + 
         "        CASHBOOK_MONTH,\n" + 
         "        ACCOUNT_NO\n" + 
         "      )c\n" + 
         "    ON a.acc_u_id     =c.acc_u_id2\n" + 
         "    AND a.acc_off_id  = c.acc_off_id2\n" + 
         "    AND a.csh_bk_yr   = c.csh_bk_yr2\n" + 
         "    AND a.csh_bk_mnth = c.csh_bk_mnth2\n" + 
         "    AND a.acc_no      = c.acc_no2\n" + 
         "\n" + 
         "        LEFT OUTER JOIN "+
		         " (SELECT acc_u_id_new, "+
         "   acc_off_id_new, "+
         "         Csh_Bk_Yr_new, "+
         "         Csh_Bk_Mnth_new, "+
         "         Acc_No_new, "+
         "         SUM(A_2a) AS A_2a "+
         "       FROM "+
         "         (SELECT ACCOUNTING_UNIT_ID AS acc_u_id_new, "+
         "           ACCOUNTING_FOR_OFFICE_ID AS acc_off_id_new, "+
         "           CASHBOOK_YEAR            AS csh_bk_yr_new, "+
         "           CASHBOOK_MONTH           AS csh_bk_mnth_new, "+
         "           Account_No               AS Acc_No_new, "+
         "           SUM(CR_AMOUNT)           AS A_2a "+
         "         FROM Fas_Brs_Transaction "+
         "        WHERE Accounting_Unit_Id    =  "+cboAcc_UnitCode+
         "         AND Accounting_For_Office_Id= "+cboOffice_code+
         "         AND Cashbook_Month          =  "+cboCashBook_Month+
         "         AND Cashbook_Year           = "+cboCashBook_Year+
         "        AND Account_No              = "+cmbBankAccNo+
         "         AND doc_type                ='FR by Office' "+
         "         GROUP BY ACCOUNTING_UNIT_ID, "+
         "           ACCOUNTING_FOR_OFFICE_ID, "+
         "           CASHBOOK_YEAR, "+
         "           Cashbook_Month, "+
         "           Account_No "+
         "         UNION ALL "+
         "         SELECT ACCOUNTING_UNIT_ID  AS acc_u_id, "+
         "           ACCOUNTING_FOR_OFFICE_ID AS acc_off_id, "+
         "           CASHBOOK_YEAR            AS csh_bk_yr, "+
         "            CASHBOOK_MONTH           AS csh_bk_mnth, "+
         "           Account_No               AS Acc_No, "+
         "           SUM(TOTAL_AMOUNT) as a_2a "+
         "         FROM fas_receipt_master "+
         "         WHERE Accounting_Unit_Id    =  "+cboAcc_UnitCode+
         "         AND Accounting_For_Office_Id=  "+cboOffice_code+
         "           AND Cashbook_Month          =  "+cboCashBook_Month+
         "         AND Cashbook_Year           =  "+cboCashBook_Year+
         "         AND Account_No              =  "+cmbBankAccNo+
         "         AND REMITTANCE_STATUS       ='Y' "+
         "         AND REMITTANCE_IN_CURR_MONTH='Y' "+
         "         GROUP BY ACCOUNTING_UNIT_ID, "+
         "           ACCOUNTING_FOR_OFFICE_ID, "+
         "           CASHBOOK_YEAR, "+
         "           CASHBOOK_MONTH, "+
         "           Account_No "+
         "         ) "+
         "       GROUP BY acc_u_id_new, "+
         "         acc_off_id_new, "+
         "         csh_bk_yr_new, "+
         "         csh_bk_mnth_new, "+
         "         Acc_No_new "+
         "       )t2 "+
         "     ON a.acc_u_id     = T2.Acc_U_Id_new "+
         "     AND a.acc_off_id  = T2.Acc_Off_Id_new "+
         "     AND a.csh_bk_yr   = T2.Csh_Bk_Yr_new "+
         "     AND a.csh_bk_mnth = T2.Csh_Bk_Mnth_new "+
         "     AND a.acc_no      = T2.Acc_No_new "+
         " Left Outer Join\n" + 
         "    (SELECT acc_u_id4,\n" + 
         "      acc_off_id4,\n" + 
         "      Acc_No4,\n" + 
         "      SUM(A_6a) AS A_6a\n" + 
         "    FROM (\n" + 
         "      (\n" + 
         "      SELECT acc_u_id4,\n" + 
         "  acc_off_id4,\n" + 
         "  acc_no4,\n" + 
         "  SUM(A_6a) AS A_6a\n" + 
         " FROM\n" + 
         "  (SELECT ACCOUNTING_UNIT_ID AS acc_u_id4,\n" + 
         "    ACCOUNTING_FOR_OFFICE_ID AS acc_off_id4,\n" + 
         "    CASHBOOK_YEAR            AS csh_bk_yr4,\n" + 
         "    CASHBOOK_MONTH           AS csh_bk_mnth4,\n" + 
         "    ACCOUNT_NO               AS acc_no4,\n" + 
         "    SUM(DR_AMOUNT)           AS A_6a\n" + 
         "  FROM FAS_BRS_TRANSACTION_NOENTRY\n" + 
         "  WHERE accounting_unit_id    =" +cboAcc_UnitCode+ 
         "  AND Accounting_For_Office_Id=" +cboOffice_code+ 
         " AND ((cashbook_year                     <"+cboCashBook_Year+" and cashbook_month<=12) or (cashbook_year="+cboCashBook_Year+" and cashbook_month<="+cboCashBook_Month+"))\n" + 
         "  AND ACCOUNT_NO              = " +cmbBankAccNo+ 
         "  AND doc_type                ='P'\n" + 
         "  GROUP BY ACCOUNTING_UNIT_ID,\n" + 
         "    ACCOUNTING_FOR_OFFICE_ID,\n" + 
         "    CASHBOOK_YEAR,\n" + 
         "    CASHBOOK_MONTH,\n" + 
         "    ACCOUNT_NO\n" + 
         "  UNION ALL\n" + 
         "  SELECT ACCOUNTING_UNIT_ID  AS acc_u_id4,\n" + 
         "    ACCOUNTING_FOR_OFFICE_ID AS acc_off_id4,\n" + 
         "    CASHBOOK_YEAR            AS csh_bk_yr4,\n" + 
         "    CASHBOOK_MONTH           AS csh_bk_mnth4,\n" + 
         "    ACCOUNT_NO               AS acc_no4,\n" + 
         "    SUM(CR_AMOUNT)           AS A_6a\n" + 
         "  FROM FAS_BRS_TRANSACTION_NOENTRY\n" + 
         "  WHERE accounting_unit_id    =" +cboAcc_UnitCode+ 
         "  AND Accounting_For_Office_Id=" +cboOffice_code+ 
         " AND ((cashbook_year                     <"+cboCashBook_Year+" and cashbook_month<=12) or (cashbook_year="+cboCashBook_Year+" and cashbook_month<="+cboCashBook_Month+"))\n" + 
         "  AND ACCOUNT_NO              = " +cmbBankAccNo+ 
  "    AND doc_type                in ('SC','IBT') \n" + 
         //    "  AND doc_type                ='SC'\n" + 
         "  GROUP BY ACCOUNTING_UNIT_ID,\n" + 
         "    ACCOUNTING_FOR_OFFICE_ID,\n" + 
         "    CASHBOOK_YEAR,\n" + 
         "    CASHBOOK_MONTH,\n" + 
         "    ACCOUNT_NO\n" + 
         "  union all\n" + 
         "  SELECT ACCOUNTING_UNIT_ID  AS acc_u_id4,\n" + 
         "    ACCOUNTING_FOR_OFFICE_ID AS acc_off_id4,\n" + 
         "    CASHBOOK_YEAR            AS csh_bk_yr4,\n" + 
         "    CASHBOOK_MONTH           AS csh_bk_mnth4,\n" + 
         "    ACCOUNT_NO               AS acc_no4,\n" + 
         "    SUM(DR_AMOUNT)           AS A_6a\n" + 
         "  FROM FAS_BRS_TRANSACTION\n" + 
         "  WHERE accounting_unit_id             =" +cboAcc_UnitCode+ 
         "  AND Accounting_For_Office_Id         =" +cboOffice_code+ 
         "  and PASSBOOK_DATE>('"+totalyear+"')\n" + 
         "  and (01||'-'||Cashbook_Month||'-'||cashbook_year)<=('"+totalyear+"')\n" + 
         "  AND ((cashbook_year                     <"+cboCashBook_Year+" and cashbook_month<=12) or (cashbook_year="+cboCashBook_Year+" and cashbook_month<="+cboCashBook_Month+"))\n" + 
         "  AND Account_No                       =" +cmbBankAccNo+ 
       //  "  AND doc_type                        IN ('P')\n" +
         "  AND doc_type                        IN ('P','IBT')\n" + 
         "  GROUP BY ACCOUNTING_UNIT_ID,\n" + 
         "    ACCOUNTING_FOR_OFFICE_ID,\n" + 
         "    CASHBOOK_YEAR,\n" + 
         "    CASHBOOK_MONTH,\n" + 
         "    ACCOUNT_NO\n" + 
         "  UNION ALL\n" + 
         "  SELECT ACCOUNTING_UNIT_ID  AS acc_u_id4,\n" + 
         "    ACCOUNTING_FOR_OFFICE_ID AS acc_off_id4,\n" + 
         "    CASHBOOK_YEAR            AS csh_bk_yr4,\n" + 
         "    CASHBOOK_MONTH           AS csh_bk_mnth4,\n" + 
         "    ACCOUNT_NO               AS acc_no4,\n" + 
         "    SUM(CR_AMOUNT)           AS A_6a\n" + 
         "  FROM FAS_BRS_TRANSACTION\n" + 
         "  WHERE accounting_unit_id             =" +cboAcc_UnitCode+ 
         "  AND Accounting_For_Office_Id         =" +cboOffice_code+ 
         "   and PASSBOOK_DATE>('"+totalyear+"')\n" + 
         "  and (01||'-'||Cashbook_Month||'-'||cashbook_year)<=('"+totalyear+"')\n" + 
         "  AND ((cashbook_year                     <"+cboCashBook_Year+" and cashbook_month<=12) or (cashbook_year="+cboCashBook_Year+" and cashbook_month<="+cboCashBook_Month+"))\n" + 
         "  AND Account_No                       =" +cmbBankAccNo+ 
         "  AND doc_type                        IN ('SC')\n" + 
         "  GROUP BY ACCOUNTING_UNIT_ID,\n" + 
         "    ACCOUNTING_FOR_OFFICE_ID,\n" + 
         "    CASHBOOK_YEAR,\n" + 
         "    CASHBOOK_MONTH,\n" + 
         "    ACCOUNT_NO\n" + 
         "  )\n" + 
         " GROUP BY acc_u_id4,\n" + 
         "  Acc_Off_Id4,\n" + 
         "  acc_no4\n" + 
         "      )\n" + 
         "     )\n" + 
         "    GROUP BY Acc_U_Id4,\n" + 
         "      Acc_Off_Id4,\n" + 
         "      Acc_No4\n" + 
         "    )g\n" + 
         "  ON a.acc_u_id    =g.acc_u_id4\n" + 
         "  And a.acc_off_id = G.Acc_Off_Id4\n" + 
         "  AND a.acc_no     = g.acc_no4\n" + 
         "    )x\n" + 
         "  LEFT OUTER JOIN\n" + 
         "    (SELECT acc_u_id3,\n" + 
         "      acc_off_id3,\n" + 
         "      csh_bk_yr3,\n" + 
         "      9 AS csh_bk_mnth3,\n" + 
         "      acc_no3,\n" + 
         "      A_4\n" + 
         "    FROM\n" + 
         "      (SELECT acc_u_id3,\n" + 
         "        acc_off_id3,\n" + 
         "        csh_bk_yr3,\n" + 
         "        acc_no3,\n" + 
         "        SUM(A_4) AS A_4\n" + 
         "      FROM\n" + 
         "        (SELECT ACCOUNTING_UNIT_ID AS acc_u_id3,\n" + 
         "          ACCOUNTING_FOR_OFFICE_ID AS acc_off_id3,\n" + 
         "          CASHBOOK_YEAR            AS csh_bk_yr3,\n" + 
         "          ACCOUNT_NO               AS acc_no3,\n" + 
         "          SUM(DR_AMOUNT)           AS A_4\n" + 
         "        FROM FAS_BRS_TRANSACTION\n" + 
         "        WHERE accounting_unit_id             = " +cboAcc_UnitCode+ 
         "        AND accounting_for_office_id         =" +cboOffice_code+ 
         "        AND cashbook_year                    =" +cboCashBook_Year+ 
         "        And Account_No                       = " +cmbBankAccNo+ 
         "        AND extract(MONTH FROM PASSBOOK_DATE)=" +cboCashBook_Month+ 
         "        AND doc_type                        IN ('P','NT')\n" + 
         "        GROUP BY ACCOUNTING_UNIT_ID,\n" + 
         "          ACCOUNTING_FOR_OFFICE_ID,\n" + 
         "          CASHBOOK_YEAR,\n" + 
         "          ACCOUNT_NO\n" + 
         "        UNION ALL\n" + 
         "        SELECT ACCOUNTING_UNIT_ID  AS acc_u_id3,\n" + 
         "          ACCOUNTING_FOR_OFFICE_ID AS acc_off_id3,\n" + 
         "          CASHBOOK_YEAR            AS csh_bk_yr3,\n" + 
         "          ACCOUNT_NO               AS acc_no3,\n" + 
         "          SUM(CR_AMOUNT)           AS A_4\n" + 
         "        FROM FAS_BRS_TRANSACTION\n" + 
         "        WHERE accounting_unit_id             = " +cboAcc_UnitCode+ 
         "        AND accounting_for_office_id         =" +cboOffice_code+ 
         "        AND cashbook_year                    =" +cboCashBook_Year+ 
         "        And Account_No                       = " +cmbBankAccNo+ 
         "        AND extract(MONTH FROM PASSBOOK_DATE)=" +cboCashBook_Month+ 
         "        AND doc_type                        IN ('SC','NT')\n" + 
         "        GROUP BY ACCOUNTING_UNIT_ID,\n" + 
         "          ACCOUNTING_FOR_OFFICE_ID,\n" + 
         "          CASHBOOK_YEAR,\n" + 
         "          CASHBOOK_MONTH,\n" + 
         "          ACCOUNT_NO\n" + 
         "        )\n" + 
         "      GROUP BY acc_u_id3,\n" + 
         "        acc_off_id3,\n" + 
         "        csh_bk_yr3,\n" + 
         "        acc_no3\n" + 
         "      )\n" + 
         "    )y\n" + 
         "  ON x.acc_u_id       =y.acc_u_id3\n" + 
         "  AND x.acc_off_id    = y.acc_off_id3\n" + 
         "  AND x.csh_bk_yr     = y.csh_bk_yr3\n" + 
         "  AND x.csh_bk_mnth   = y.csh_bk_mnth3\n" + 
         "  AND x.acc_no        = y.acc_no3\n" + 
         "  )YY ON XX.acc_u_id6 = YY.acc_u_id\n" + 
         " AND XX.acc_off_id6    = YY.acc_off_id\n" + 
         " AND XX.csh_bk_yr6     = YY.csh_bk_yr\n" + 
         " And Xx.Csh_Bk_Mnth6   = Yy.Csh_Bk_Mnth\n" + 
         " AND XX.acc_no6        = YY.acc_no)";
		
        try{
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
        
		File reportFile = null;
		String sql="";
		Map map = null;
		map = new HashMap();
		map.put("opr_node", opr_node);
		try {
			System.out.println("calling servlet...");
			if(cmd.equalsIgnoreCase("fzd_report")){
				/*sql="SELECT fin.*, " +
				"  (DECODE(fin.S1A,NULL,0,fin.S1A)+DECODE(fin.S1B,NULL,0,fin.S1B)+ DECODE(fin.S1C,NULL,0,fin.S1C) +DECODE(fin.S1D,NULL,0,fin.S1D))AS tort " +
				"FROM " +
				"  (SELECT p1.ACCOUNTING_UNIT_ID, " +
				"    unit.ACCOUNTING_UNIT_NAME, " +
				"    office.OFFICE_NAME, " +
				"    p1.ACCOUNTING_FOR_OFFICE_ID, " +
				"    p1.PASS_SHEET_YEAR, " +
				"    p1.PASS_SHEET_MONTH, " +
				"    TWAD_OR_NON_TWAD, " +
				"    p1.ACCOUNT_NO, " +
				"    p1.BANK_ID, " +
				"    (SELECT DECODE(Bname.BANK_NAME,NULL,'',Bname.BANK_NAME)as BANK_NAME FROM FAS_BANK_LIST Bname WHERE p1.BANK_ID=Bname.BANK_ID " +
				"    )AS BANK_NAME, " +
				"    (SELECT decode(branch.BRANCH_NAME,NULL,'',branch.BRANCH_NAME)AS BRANCH_NAME " +
				"    FROM FAS_MST_BANK_BRANCHES branch " +
				"    WHERE p1.BANK_ID=branch.BANK_ID " +
				"    AND p1.BRANCH_ID=branch.BRANCH_ID " +
				"    )AS BRANCH_NAME, " +
				"    (SELECT PASSBOOK_BALANCE " +
				"    FROM FAS_BRS_MASTER pbk " +
				"    WHERE pbk.ACCOUNTING_UNIT_ID    =p1.ACCOUNTING_UNIT_ID " +
				"    AND pbk.ACCOUNTING_FOR_OFFICE_ID=p1.ACCOUNTING_FOR_OFFICE_ID " +
				"    AND pbk.CASHBOOK_YEAR           =p1.PASS_SHEET_YEAR " +
				"    AND pbk.CASHBOOK_MONTH          =p1.PASS_SHEET_MONTH " +
				"    AND pbk.ACCOUNT_NO              =p1.ACCOUNT_NO " +
				"    )AS PASSBOOK_BALANCE, " +
				"    S1A, " +
				"    S1B, " +
				"    S1C, " +
				"    S1D, " +
				"    S2, " +
				"    S3, " +
				"    CHEQUE_CASHED, " +
				"    TWAD_AMOUNT " +
				"  FROM FAS_BRS_PART_2C p1, " +
				"    FAS_MST_ACCT_UNITS unit, " +
				"    COM_MST_OFFICES office " +
				"  WHERE p1.ACCOUNTING_UNIT_ID    =" +cboAcc_UnitCode+
				"  AND ACCOUNTING_FOR_OFFICE_ID   =  " +cboOffice_code+
				"  AND PASS_SHEET_YEAR            =  " +cboCashBook_Year+
				"  AND PASS_SHEET_MONTH           = " +cboCashBook_Month+
				"  AND ACCOUNT_NO                 =  " +cmbBankAccNo+
				"  AND p1.ACCOUNTING_UNIT_ID      =unit.ACCOUNTING_UNIT_ID " +
				"  AND p1.ACCOUNTING_FOR_OFFICE_ID=office.OFFICE_ID " +
				"  )fin";*/
				sql="SELECT fin.*, " +
						"  (DECODE(fin.S1A,NULL,0,fin.S1A)+DECODE(fin.S1B,NULL,0,fin.S1B)+ DECODE(fin.S1C,NULL,0,fin.S1C) )AS tort " +
						"FROM " +
						"  (SELECT p1.ACCOUNTING_UNIT_ID, " +
						"    unit.ACCOUNTING_UNIT_NAME, " +
						"    office.OFFICE_NAME, " +
						"    p1.ACCOUNTING_FOR_OFFICE_ID, " +
						"    p1.PASS_SHEET_YEAR, " +
						"    p1.PASS_SHEET_MONTH, " +
						"    TWAD_OR_NON_TWAD, " +
						"    p1.ACCOUNT_NO, " +
						"    p1.BANK_ID, " +
						"    (SELECT DECODE(Bname.BANK_NAME,NULL,'',Bname.BANK_NAME)as BANK_NAME FROM FAS_BANK_LIST Bname WHERE p1.BANK_ID=Bname.BANK_ID " +
						"    )AS BANK_NAME, " +
						"    (SELECT decode(branch.BRANCH_NAME,NULL,'',branch.BRANCH_NAME)AS BRANCH_NAME " +
						"    FROM FAS_MST_BANK_BRANCHES branch " +
						"    WHERE p1.BANK_ID=branch.BANK_ID " +
						"    AND p1.BRANCH_ID=branch.BRANCH_ID " +
						"    )AS BRANCH_NAME, " +
						"    (SELECT PASSBOOK_BALANCE " +
						"    FROM FAS_BRS_MASTER pbk " +
						"    WHERE pbk.ACCOUNTING_UNIT_ID    =p1.ACCOUNTING_UNIT_ID " +
						"    AND pbk.ACCOUNTING_FOR_OFFICE_ID=p1.ACCOUNTING_FOR_OFFICE_ID " +
						"    AND pbk.CASHBOOK_YEAR           =p1.PASS_SHEET_YEAR " +
						"    AND pbk.CASHBOOK_MONTH          =p1.PASS_SHEET_MONTH " +
						"    AND pbk.ACCOUNT_NO              =p1.ACCOUNT_NO " +
						"    )AS PASSBOOK_BALANCE, " +
						"    S1A, " +
						"    S1B, " +
						"    S1C, " +
						//"    S1D, " +
						"    S2, " +
						"    S3, " +
						"   UPTO_NT_CR as CHEQUE_CASHED, " +
						"    UPTO_NT_DR as TWAD_AMOUNT, " +
						"   NOTRECONCILED_UPTO_REASON45_CR as UPTO_REASON45_CR, " +
					    "	NOTRECONCILED_UPTO_REASON45_DR as UPTO_REASON45_DR, " +
					    "	NOTRECONCILED_FOR_REASON45_CR as FOR_REASON45_CR, " +
					    "	NOTRECONCILED_FOR_REASON45_DR as FOR_REASON45_DR, " +
						"   FOR_NT_CR , " +
						"    FOR_NT_DR  " +
						"  FROM FAS_BRS_PART_2C p1, " +
						"    FAS_MST_ACCT_UNITS unit, " +
						"    COM_MST_OFFICES office " +
						"  WHERE p1.ACCOUNTING_UNIT_ID    =" +cboAcc_UnitCode+
						"  AND ACCOUNTING_FOR_OFFICE_ID   =  " +cboOffice_code+
						"  AND PASS_SHEET_YEAR            =  " +cboCashBook_Year+
						"  AND PASS_SHEET_MONTH           = " +cboCashBook_Month+
						"  AND ACCOUNT_NO                 =  " +cmbBankAccNo+
						"  AND p1.ACCOUNTING_UNIT_ID      =unit.ACCOUNTING_UNIT_ID " +
						"  AND p1.ACCOUNTING_FOR_OFFICE_ID=office.OFFICE_ID " +
						"  )fin";
				System.out.println(sql);
				
			
				
			
			reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/BRS/jaspers/Part_Jasper/BRS_Report_2C_Report.jasper"));//}
		//		reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/BRS/jaspers/BRS_Report_2C.jasper"));//}
				
				//else{
					//reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/BRS/jaspers/Part_Jasper/BRS_Report_2C_Report.jasper"));	
				//}
				map.put("sql",sql);
			  
			if (!reportFile.exists())
				throw new JRRuntimeException(
						"File J not found. The report design must be compiled first.");

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
		    map.put("UnitName","("+cboAcc_UnitCode+" ) "+ UnitName);
		    map.put("brs_amt_n", brs_amt);
		    map.put("twad_cr", twad_cr);
		 System.out.println(""+map);
		/*	String path = getServletContext().getRealPath("/org/FAS/FAS1/BRS/jaspers/BRS_Report_2C.jasper");
    	
    		String ctxpath = path.substring(0, path.lastIndexOf("BRS_Report_2C.jasper"));
    		   map.put("sub2", ctxpath);*/
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
				
				response.setHeader("Content-Disposition",
						"attachment;filename=\"BRS_Part2C.pdf\"");
				OutputStream out = response.getOutputStream();
				out.write(buf, 0, buf.length);
				out.close();
			}
			}
			
			
			
			
			
			
			else if(cmd.equalsIgnoreCase("printFunc")){
				try{
					
				System.out.println("sql_que:"+sql_que);	
		reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/BRS/jaspers/BRS_Report_2CNEWONE.jasper"));
		//}
			//reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/BRS/jaspers/BRS_Report_2C.jasper"));//}	
				//if(hid.equalsIgnoreCase("old")){
				String path = getServletContext().getRealPath("/org/FAS/FAS1/BRS/jaspers/BRS_Report_2CNEWONE.jasper");
		    	
	    		String ctxpath = path.substring(0, path.lastIndexOf("BRS_Report_2CNEWONE.jasper"));
	    		  map.put("SUBREPORT_DIR", ctxpath);
	    		  
	    		  System.out.println("SUBREPORT_DIR===>"+ctxpath);
	    	
				//else
				//{
					//reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/BRS/jaspers/BRS_Report_2C.jasper"));
				//}
	    		  
	    		  System.out.println("reportFile====>"+reportFile);
	    		  System.out.println("sql_que==>"+sql_que);
	    		  
				map.put("sql",sql_que);
			if (!reportFile.exists())
				throw new JRRuntimeException("File J not found. The report design must be compiled first.");

			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportFile.getPath());
			/* sub1="  SELECT  "+cboAcc_UnitCode+" AS ACCOUNTING_UNIT_ID ," +
					 +cboOffice_code+	"         AS Accounting_For_Office_Id ," +
					"    AMT_VAL," +
					"    TYPE," +
					"    CASE" +
					"      WHEN TYPE=1" +
					"      THEN 'Cheque cashed but not related to this division account '" +
					"      WHEN TYPE=2" +
					"      THEN 'TWAD Board amount credited'" +
					"     WHEN TYPE=3" +
					"   THEN 'Amount Credited By Bank'" +
					"   WHEN TYPE=4" +
					"   THEN 'Misclassification error if any made in the pass book by the bank'" +
					"   WHEN TYPE=5" +
					"   THEN 'Misclassification error if any made in the pass book by the bank'" +
					"   WHEN TYPE=6" +
					"   THEN 'Service Charges'" +
					"   END AS exp" +
					"  FROM (" +
					"  (SELECT DECODE(SUM(CR_AMOUNT),NULL,0,SUM(CR_AMOUNT)) AS AMT_VAL ," +
					"   3                                                  AS type" +
					"  FROM FAS_BRS_TRANSACTION" +
				      "     WHERE accounting_unit_id      = " +cboAcc_UnitCode+ 
				      "      AND Accounting_For_Office_Id=  " +cboOffice_code+ 
				      "      AND Account_No              =  " +cmbBankAccNo+ 
				      "      AND EXTRACT(YEAR FROM PASSBOOK_DATE)  in (" +cboCashBook_Year+ ","+cboCashBook_Year1+")"+
				
				 " and passbook_date < '01-"+month.substring(0,3)+"-"+cboCashBook_Year+"'"+
				       
				       "     AND TWAD_OR_NON_TWAD        ='NT' \n" + 
				    
					"  AND TRANSACTION_TYPE                 IN (39,40)" +
					"  GROUP BY 3" +
					"  )" +
					"  UNION ALL" +
					"  (SELECT "+twad_cr+" AS AMT_VAL , 2 AS type " +
					"  )" +
					"  UNION ALL" +
					"  (SELECT SUM(DR_AMOUNT) AS AMT_VAL," +
					"    1                    AS type" +
					"  FROM FAS_BRS_TRANSACTION" +
				     "  WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
			         "  AND Accounting_For_Office_Id= " +cboOffice_code+ 
			         "  AND (extract(YEAR FROM PASSBOOK_DATE) <" +cboCashBook_Year+ 
			         " Or (Extract(Year From Passbook_Date)  =" +cboCashBook_Year+ 
			         " AND extract(MONTH FROM PASSBOOK_DATE)<"+cboCashBook_Month+"))\n" + 
			         "  AND ACCOUNT_NO              = " +cmbBankAccNo+ 
			         "  AND Twad_Or_Non_Twad        ='NT'\n" + 
			         "  AND Transaction_Type       IN(3,23,12)\n" + 
					"  GROUP BY 1" +
					"  )" +
					"  UNION ALL" +
					"  (SELECT SUM(DR_AMOUNT) AS AMT_VAL," +
					"   4                    AS type" +
					"  FROM FAS_BRS_TRANSACTION" +
					   "  WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
				         "  AND Accounting_For_Office_Id= " +cboOffice_code+ 
				         "  AND Cashbook_Year           = " +cboCashBook_Year+ 
				         "  AND ACCOUNT_NO              = " +cmbBankAccNo+ 
				         "  AND Cashbook_Month          = " +cboCashBook_Month+ 
				         "  AND Twad_Or_Non_Twad        ='NT'\n" + 
					"  AND Transaction_Type        =3" +
					"  GROUP BY 4" +
					"  )" +
					"  UNION ALL" +
					"  (SELECT SUM(DR_AMOUNT) AS AMT_VAL," +
					"    5                    AS type" +
					"  FROM FAS_BRS_TRANSACTION" +
					"  WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
			         "  AND Accounting_For_Office_Id= " +cboOffice_code+ 
			         "  AND Account_No              = " +cmbBankAccNo+ 
			        " AND ((CASHBOOK_YEAR < "+cboCashBook_Year+") OR (CASHBOOK_YEAR    ="+cboCashBook_Year+

			        " AND CASHBOOK_MONTH  < "+cboCashBook_Month+")) "+
			         "  AND Twad_Or_Non_Twad        ='NT'\n" + 
		
					"  AND Transaction_Type       IN (6,7,31,1)" +
					"  GROUP BY 5" +
					"  )" +
					"  	UNION ALL" +
					"   (SELECT SUM(DR_AMOUNT)-SUM(CR_AMOUNT) AS AMT_VAL ," +
					"    6                                   AS type" +
					"    FROM FAS_BRS_TRANSACTION" +
					  "  		  WHERE accounting_unit_id               =   " +cboAcc_UnitCode+ 
   				  "  		  AND Accounting_For_Office_Id           = " +cboOffice_code+ 
   				  "  		  AND extract(YEAR FROM PASSBOOK_DATE)   = " +cboCashBook_Year+ 
   				  "  		  AND ACCOUNT_NO                         = " +cmbBankAccNo+ 
   				  "  		  AND extract(MONTH FROM PASSBOOK_DATE) <  " +cboCashBook_Month+ 
   				  "  	  AND TWAD_OR_NON_TWAD                   ='NT'\n" + 
   		
					"    AND TRANSACTION_TYPE                 IN(8)" +
					"  GROUP BY 6" +
					"   ) )ty " ;*/
			
			
			sub1="select b.* from (select " +cboAcc_UnitCode+ " as ACCOUNTING_UNIT_ID ,  " +cboOffice_code+ " Accounting_For_Office_Id  )a left outer join (select rownum as id,cc.* from(select ACCOUNTING_UNIT_ID ,   Accounting_For_Office_Id ,exp,sum(crAMT_VAL) crAMT_VAL,sum(drAMT_VAL) as drAMT_VAL from (SELECT  " +cboAcc_UnitCode+" AS ACCOUNTING_UNIT_ID , " +
					   cboOffice_code+ "    AS Accounting_For_Office_Id , " +
					"  AMT_VAL, " +
					"  TYPE, " +
					"  (SELECT TRANS_SHORT_DESC " +
				/*	"    ||' - ' " +Wrong Booking in Accounts but deposited in correct bank
					"    ||crDr " +*/
					"  FROM FAS_BRS_TRANSACTION_TYPE " +
					"  WHERE TRANS_CODE=TYPE " +
					"  ) AS exp, " +
					" case when crDr='Credit' then AMT_VAL else 0 end as crAMT_VAL, " +
					" 	  case when crDr='Debit' then AMT_VAL else 0 end as drAMT_VAL " +
					"FROM " +
					"  (SELECT SUM(DR_AMOUNT) AS AMT_VAL, " +
					"    'Debit'             AS crDr, " +
					"    Transaction_Type     AS TYPE " +
					"  FROM FAS_BRS_TRANSACTION " +
					"  WHERE accounting_unit_id    =  " +cboAcc_UnitCode+
					"  AND Accounting_For_Office_Id=  " +cboOffice_code+
					"  AND Account_No              =  " +cmbBankAccNo+
					"  AND ((CASHBOOK_YEAR         < "+cboCashBook_Year+") " +
					"  OR (CASHBOOK_YEAR           ="+cboCashBook_Year+
					"  AND CASHBOOK_MONTH          < "+cboCashBook_Month+")) " +
					"  AND PASSBOOK_DATE          <= '"+prf_date+"' " +
					"  AND Twad_Or_Non_Twad        ='NT'       AND (CLEARED_BASED_ON_FOLLOWUP      ='N' or CLEARED_BASED_ON_FOLLOWUP is null )" +
					"  GROUP BY 'Debit', " +
					"    Transaction_Type " +
					"  UNION ALL " +
					"  SELECT SUM(CR_AMOUNT) AS AMT_VAL, " +
					"    'Credit'             AS crDr, " +
					"    Transaction_Type    AS TYPE " +
					"  FROM FAS_BRS_TRANSACTION " +
					"  WHERE accounting_unit_id    =  " +cboAcc_UnitCode+
					"  AND Accounting_For_Office_Id=  " +cboOffice_code+
					"  AND Account_No              =  " +cmbBankAccNo+
					"  AND ((CASHBOOK_YEAR         < "+cboCashBook_Year+") " +
					"  OR (CASHBOOK_YEAR           ="+cboCashBook_Year+
					"  AND CASHBOOK_MONTH          <  "+cboCashBook_Month+")) " +
					"  AND PASSBOOK_DATE          <= '"+prf_date+"' " +
					"  AND Twad_Or_Non_Twad        ='NT'     AND (CLEARED_BASED_ON_FOLLOWUP      ='N' or CLEARED_BASED_ON_FOLLOWUP is null ) " +
					"  GROUP BY 'Credit', " +
					"    Transaction_Type " +
					"  )ty   where  TYPE is not null  and AMT_VAL <> 0 )group by ACCOUNTING_UNIT_ID, Accounting_For_Office_Id, exp)cc)b    on  a. ACCOUNTING_UNIT_ID=b. ACCOUNTING_UNIT_ID";
			
			
			 sub_noEntry="SELECT b.*\r\n" + 
			 		"FROM\r\n" + 
			 		"  (SELECT " +cboAcc_UnitCode+ " AS ACCOUNTING_UNIT_ID , "+cboOffice_code+" Accounting_For_Office_Id \r\n" + 
			 		"  )a\r\n" + 
			 		"LEFT OUTER JOIN\r\n" + 
			 		"  (SELECT rownum AS id,\r\n" + 
			 		"    cc.*\r\n" + 
			 		"  FROM\r\n" + 
			 		"    (SELECT ACCOUNTING_UNIT_ID ,\r\n" + 
			 		"      Accounting_For_Office_Id ,\r\n" + 
			 		"      exp,\r\n" + 
			 		"      SUM(crAMT_VAL_noentry) crAMT_VAL_noentry,\r\n" + 
			 		"      SUM(drAMT_VAL_noentry) AS drAMT_VAL_noentry\r\n" + 
			 		"    FROM\r\n" + 
			 		"      (SELECT "+cboAcc_UnitCode+" AS ACCOUNTING_UNIT_ID ,\r\n" + 
			 		"        "+cboOffice_code+"    AS Accounting_For_Office_Id ,\r\n" + 
			 		"       TYPE,\r\n" + 
			 		"        (SELECT TRANS_SHORT_DESC FROM FAS_BRS_TRANSACTION_TYPE WHERE TRANS_CODE=TYPE\r\n" + 
			 		"        ) AS exp,\r\n" + 
			 		"        CASE\r\n" + 
			 		"          WHEN crDr='Credit'\r\n" + 
			 		"          THEN AMT_VAL\r\n" + 
			 		"          ELSE 0\r\n" + 
			 		"        END AS crAMT_VAL_noentry,\r\n" + 
			 		"        CASE\r\n" + 
			 		"          WHEN crDr='Debit'\r\n" + 
			 		"          THEN AMT_VAL\r\n" + 
			 		"          ELSE 0\r\n" + 
			 		"        END AS DRAMT_VAL_noentry\r\n" + 
			 		"        \r\n" + 
			 		"      FROM\r\n" + 
			 		"        (SELECT SUM(DR_AMOUNT) AS AMT_VAL,\r\n" + 
			 		"          'Debit'              AS crDr,\r\n" + 
			 		"          REASON_FOR_DIFFERENCE     AS TYPE\r\n" + 
			 		"        FROM FAS_BRS_TRANSACTION_NOENTRY\r\n" + 
			 		"        WHERE accounting_unit_id     = "+cboAcc_UnitCode+ 
			 		"        AND Accounting_For_Office_Id = " +cboOffice_code+
			 		"        AND Account_No               = " +cmbBankAccNo+ 
			 		"  		AND ((CASHBOOK_YEAR         < "+cboCashBook_Year+") " +
					"  		OR (CASHBOOK_YEAR           ="+cboCashBook_Year+
					"  		AND CASHBOOK_MONTH          < "+cboCashBook_Month+")) " +
			 		"        AND REASON_FOR_DIFFERENCE    = 45 " + 
			 		"        AND FOLLOW_UP_ACTION_REQUIRED = 'Y'" + 
			 		"        GROUP BY 'Debit', REASON_FOR_DIFFERENCE" + 
			 		"        UNION ALL\r\n" + 
			 		"        SELECT SUM(CR_AMOUNT) AS AMT_VAL," + 
			 		"          'Credit'            AS crDr," + 
			 		"          REASON_FOR_DIFFERENCE    AS TYPE" + 
			 		"        FROM FAS_BRS_TRANSACTION_NOENTRY" + 
			 		"        WHERE accounting_unit_id     = "+cboAcc_UnitCode+ 
			 		"        AND Accounting_For_Office_Id = " +cboOffice_code+
			 		"        AND Account_No               = " +cmbBankAccNo+ 
			 		"  		 AND ((CASHBOOK_YEAR         < "+cboCashBook_Year+") " +
					"  	 	 OR (CASHBOOK_YEAR           ="+cboCashBook_Year+
					"  		 AND CASHBOOK_MONTH          < "+cboCashBook_Month+")) " +
			 		"        AND REASON_FOR_DIFFERENCE    = 45" + 
			 		"        AND FOLLOW_UP_ACTION_REQUIRED = 'Y'" + 
			 		"        GROUP BY 'Credit',\r\n" + 
			 		"        REASON_FOR_DIFFERENCE\r\n" + 
			 		"       )ty\r\n" + 
			 		"        \r\n" + 
			 		"        \r\n" + 
			 		"      WHERE TYPE  IS NOT NULL\r\n" + 
			 		"      AND AMT_VAL <> 0\r\n" + 
			 		"      )\r\n" + 
			 		"    GROUP BY ACCOUNTING_UNIT_ID,\r\n" + 
			 		"      Accounting_For_Office_Id,\r\n" + 
			 		"      exp\r\n" + 
			 		"    )cc\r\n" + 
			 		"  )b\r\n" + 
			 		"ON a. ACCOUNTING_UNIT_ID=b. ACCOUNTING_UNIT_ID" ;
			 
			 
			 sub_noEntry_1="SELECT b.*\r\n" + 
				 		"FROM\r\n" + 
				 		"  (SELECT " +cboAcc_UnitCode+ " AS ACCOUNTING_UNIT_ID , "+cboOffice_code+" Accounting_For_Office_Id \r\n" + 
				 		"  )a\r\n" + 
				 		"LEFT OUTER JOIN\r\n" + 
				 		"  (SELECT rownum AS id,\r\n" + 
				 		"    cc.*\r\n" + 
				 		"  FROM\r\n" + 
				 		"    (SELECT ACCOUNTING_UNIT_ID ,\r\n" + 
				 		"      Accounting_For_Office_Id ,\r\n" + 
				 		"      exp,\r\n" + 
				 		"      SUM(crAMT_VAL_noentry1) crAMT_VAL_noentry1,\r\n" + 
				 		"      SUM(drAMT_VAL_noentry1) AS drAMT_VAL_noentry1\r\n" + 
				 		"    FROM\r\n" + 
				 		"      (SELECT "+cboAcc_UnitCode+" AS ACCOUNTING_UNIT_ID ,\r\n" + 
				 		"        "+cboOffice_code+"    AS Accounting_For_Office_Id ,\r\n" + 
				 		"       TYPE,\r\n" + 
				 		"        (SELECT TRANS_SHORT_DESC FROM FAS_BRS_TRANSACTION_TYPE WHERE TRANS_CODE=TYPE\r\n" + 
				 		"        ) AS exp,\r\n" + 
				 		"        CASE\r\n" + 
				 		"          WHEN crDr='Credit'\r\n" + 
				 		"          THEN AMT_VAL\r\n" + 
				 		"          ELSE 0\r\n" + 
				 		"        END AS crAMT_VAL_noentry1,\r\n" + 
				 		"        CASE\r\n" + 
				 		"          WHEN crDr='Debit'\r\n" + 
				 		"          THEN AMT_VAL\r\n" + 
				 		"          ELSE 0\r\n" + 
				 		"        END AS DRAMT_VAL_noentry1\r\n" + 
				 		"        \r\n" + 
				 		"      FROM\r\n" + 
				 		"        (SELECT SUM(DR_AMOUNT) AS AMT_VAL,\r\n" + 
				 		"          'Debit'              AS crDr,\r\n" + 
				 		"          REASON_FOR_DIFFERENCE     AS TYPE\r\n" + 
				 		"        FROM FAS_BRS_TRANSACTION_NOENTRY\r\n" + 
				 		"        WHERE accounting_unit_id     = "+cboAcc_UnitCode+ 
				 		"        AND Accounting_For_Office_Id = " +cboOffice_code+
				 		"        AND Account_No               = " +cmbBankAccNo+ 
//				 		"        AND CASHBOOK_YEAR            = " +cboCashBook_Year+ 
//				 		"        AND CASHBOOK_MONTH           = " +cboCashBook_Month+
				 		"  		AND ((CASHBOOK_YEAR         < "+cboCashBook_Year+") " +
				 		"  		OR (CASHBOOK_YEAR           ="+cboCashBook_Year+
				 		"  		AND CASHBOOK_MONTH          < "+cboCashBook_Month+")) " +				 		
				 		"        AND REASON_FOR_DIFFERENCE    = 45 " + 
				 		"        AND FOLLOW_UP_ACTION_REQUIRED = 'Y'" + 
				 		"        GROUP BY 'Debit', REASON_FOR_DIFFERENCE" + 
				 		"        UNION ALL\r\n" + 
				 		"        SELECT SUM(CR_AMOUNT) AS AMT_VAL," + 
				 		"          'Credit'            AS crDr," + 
				 		"          REASON_FOR_DIFFERENCE    AS TYPE" + 
				 		"        FROM FAS_BRS_TRANSACTION_NOENTRY" + 
				 		"        WHERE accounting_unit_id     = "+cboAcc_UnitCode+ 
				 		"        AND Accounting_For_Office_Id = " +cboOffice_code+
				 		"        AND Account_No               = " +cmbBankAccNo+ 
				 		"        AND CASHBOOK_YEAR            = " +cboCashBook_Year+ 
				 		"        AND CASHBOOK_MONTH           = " +cboCashBook_Month+
//				 		"  		AND ((CASHBOOK_YEAR         < "+cboCashBook_Year+") " +
//				 		"  		OR (CASHBOOK_YEAR           ="+cboCashBook_Year+
//				 		"  		AND CASHBOOK_MONTH          < "+cboCashBook_Month+")) " +
				 		"        AND REASON_FOR_DIFFERENCE    = 45" + 
				 		"        AND FOLLOW_UP_ACTION_REQUIRED = 'Y'" + 
				 		"        GROUP BY 'Credit',\r\n" + 
				 		"        REASON_FOR_DIFFERENCE\r\n" + 
				 		"       )ty\r\n" + 
				 		"        \r\n" + 
				 		"        \r\n" + 
				 		"      WHERE TYPE  IS NOT NULL\r\n" + 
				 		"      AND AMT_VAL <> 0\r\n" + 
				 		"      )\r\n" + 
				 		"    GROUP BY ACCOUNTING_UNIT_ID,\r\n" + 
				 		"      Accounting_For_Office_Id,\r\n" + 
				 		"      exp\r\n" + 
				 		"    )cc\r\n" + 
				 		"  )b\r\n" + 
				 		"ON a. ACCOUNTING_UNIT_ID=b. ACCOUNTING_UNIT_ID" ;
			 
			
			 System.out.println("sub_noEntry_1===>sub4===>"+sub_noEntry_1);
			
			
			/* sub_str2=  "  	SELECT  " +cboAcc_UnitCode+ " AS ACCOUNTING_UNIT_ID ," 
					    		  +cboOffice_code+ "     AS Accounting_For_Office_Id ," +
					  "  		  amt_val1," +
					  "  		  TYPE," +
					  "  		  CASE" +
					  "  		    WHEN TYPE=1" +
					  "  		    THEN 'Commission charges levied by bank for issued of cheque book.'" +
					  "  		    WHEN TYPE=2" +
					  "  		    THEN 'Amount which was erraneously deposited in this account is transferre to SB A/c No.'" +
					  "  		    WHEN TYPE=3" +
					  "  		    THEN 'Service Charges'" +
					  "  		    WHEN TYPE=4" +
					  "  		    THEN 'CashBook Charges'" +
					  "  		  END AS EXP" +
					  "  		FROM (" +
					  "  		  (SELECT SUM(DR_AMOUNT)-SUM(CR_AMOUNT)AS amt_val1," +
					  "  		    1                                  AS type" +
					    "  		  FROM FAS_BRS_TRANSACTION" +
					    "  WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
				         "  AND Accounting_For_Office_Id= " +cboOffice_code+ 
				         "   AND extract(year from PASSBOOK_DATE)           = " +cboCashBook_Year+ 
				         "  AND Account_No              = " +cmbBankAccNo+ 
				         "  AND extract(month from PASSBOOK_DATE)          <= " +cboCashBook_Month+ 
				         "  AND Twad_Or_Non_Twad        ='NT'\n" + 
					  "  			  AND Transaction_Type                  IN(6,12,24,2)" +
					  "  			  GROUP BY 1" +
					  "  			  )" +
					  "  			UNION ALL" +
					  		 
					  "  			  (" +
					  "  	  SELECT SUM(DR_AMOUNT)-SUM(CR_AMOUNT)AS amt_val1," +
					  "  		    3                                 AS type" +
					  "  		  FROM FAS_BRS_TRANSACTION" +
					  "  		  WHERE accounting_unit_id               =   " +cboAcc_UnitCode+ 
   				  "  		  AND Accounting_For_Office_Id           = " +cboOffice_code+ 
   				  "  		  AND extract(YEAR FROM PASSBOOK_DATE)   = " +cboCashBook_Year+ 
   				  "  		  AND ACCOUNT_NO                         = " +cmbBankAccNo+ 
   				  "  		  AND extract(MONTH FROM PASSBOOK_DATE) =  " +cboCashBook_Month+ 
   				  "  	  AND TWAD_OR_NON_TWAD                   ='NT'\n" + 
   			
					  "  		  AND TRANSACTION_TYPE                 IN(8)" +
					  "  		  GROUP BY 3" +
					  "  		  )" +
					  "  			UNION ALL" +
				  		 
					  "  			  (" +
					  "  	  SELECT SUM(DR_AMOUNT)-SUM(CR_AMOUNT)AS amt_val1," +
					  "  		    4                                 AS type" +
					  "  		  FROM FAS_BRS_TRANSACTION" +
					  "  		  WHERE accounting_unit_id               =   " +cboAcc_UnitCode+ 
   				  "  		  AND Accounting_For_Office_Id           = " +cboOffice_code+ 
   				  "  		  AND Cashbook_Year      = " +cboCashBook_Year+ 
   				  "  		  AND ACCOUNT_NO                         = " +cmbBankAccNo+ 
   				  "  		  AND Cashbook_Month   =  " +cboCashBook_Month+ 
   				  "  	  AND TWAD_OR_NON_TWAD                   ='NT'\n" + 
   			
					  "  		  AND TRANSACTION_TYPE                 IN(7)" +
					  "  		  GROUP BY 4" +
					  "  		  )" +
					  "  		UNION ALL" +
					 
					  "  		  (" +
					  "  		  SELECT SUM(CR_AMOUNT) AS amt_val1," +
					  "  		    2                   AS type" +
					  "  		  FROM FAS_BRS_TRANSACTION" +
					  "  WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
				         "  AND Accounting_For_Office_Id= " +cboOffice_code+ 
				         "  AND Cashbook_Year           = " +cboCashBook_Year+ 
				         "  AND Account_No              = " +cmbBankAccNo+ 
				         "  AND Cashbook_Month          = " +cboCashBook_Month+ 
				         "  AND Twad_Or_Non_Twad        ='NT'\n" + 
				         "  AND Transaction_Type        =27\n" + 
					  "  		  GROUP BY 2" +
					  "  		  )" +
					  "  		  )TY" +
					  "  		ORDER BY type";*/
			sub_str2="select ACCOUNTING_UNIT_ID ,   Accounting_For_Office_Id ,exp,sum(crAMT_VAL1) crAMT_VAL1,sum(drAMT_VAL1) as drAMT_VAL1 from (SELECT "+cboAcc_UnitCode+ " AS ACCOUNTING_UNIT_ID , " +
					cboOffice_code+"   AS Accounting_For_Office_Id , " +
				
					"  TYPE, " +
					"  (SELECT TRANS_SHORT_DESC " +
				/*	"    ||' - ' " +
					"    ||crDr " +*/
					"  FROM FAS_BRS_TRANSACTION_TYPE " +
					"  WHERE TRANS_CODE=TYPE " +
					"  ) AS exp ," +
					" case when crDr='Credit' then AMT_VAL else 0 end as crAMT_VAL1, " +
					" 	  case when crDr='Debit' then AMT_VAL else 0 end as drAMT_VAL1 " +
					"FROM " +
					"  (SELECT SUM(CR_AMOUNT) AS AMT_VAL, " +
					"    'Credit'             AS crDr, " +
					"    Transaction_Type     AS TYPE " +
					"  FROM FAS_BRS_TRANSACTION " +
					"  WHERE accounting_unit_id                =" +cboAcc_UnitCode+ 
					"  AND Accounting_For_Office_Id            = " +cboOffice_code+ 
					"  AND Account_No                          = " +cmbBankAccNo+ 
					"  AND (( extract(YEAR FROM PASSBOOK_DATE) =" +cboCashBook_Year+ 
					"  AND extract(MONTH FROM PASSBOOK_DATE)   =  " +cboCashBook_Month+ ") " +
					"  OR (CASHBOOK_YEAR                       =" +cboCashBook_Year+ 
					"  AND CASHBOOK_MONTH                      =  " +cboCashBook_Month+ " )) " +
					"  AND Twad_Or_Non_Twad                    ='NT' " +
					"  GROUP BY 'Credit', " +
					"    Transaction_Type " +
					"  UNION ALL " +
					"  SELECT SUM(DR_AMOUNT) AS AMT_VAL, " +
					"    'Debit'             AS crDr, " +
					"    Transaction_Type    AS TYPE " +
					"  FROM FAS_BRS_TRANSACTION " +
					"  WHERE accounting_unit_id                = " +cboAcc_UnitCode+ 
					"  AND Accounting_For_Office_Id            =  " +cboOffice_code+ 
					"  AND Account_No                          = " +cmbBankAccNo+ 
					"  AND (( extract(YEAR FROM PASSBOOK_DATE) =" +cboCashBook_Year+ 
					"  AND extract(MONTH FROM PASSBOOK_DATE)   =  " +cboCashBook_Month+ ") " +
					"  OR (CASHBOOK_YEAR                       =" +cboCashBook_Year+ 
					"  AND CASHBOOK_MONTH                      =  " +cboCashBook_Month+ " )) " +
					"  AND Twad_Or_Non_Twad                    ='NT' " +
					"  GROUP BY 'Debit', " +
					"    Transaction_Type " +
					"  )TY   where  TYPE is not null  and AMT_VAL <> 0 )group by ACCOUNTING_UNIT_ID, Accounting_For_Office_Id, exp";
					//"ORDER BY type";
			System.out.println("brs_amt:::::::final:::::"+brs_amt);
			map.put("UnitId", cboAcc_UnitCode);
			map.put("OfficeId", cboOffice_code);
			map.put("cbyear", cboCashBook_Year);
			map.put("cbmonth", cboCashBook_Month);
			map.put("accNo", cmbBankAccNo);
			map.put("month", month);
            map.put("amount", ii);
            map.put("UnitName","("+cboAcc_UnitCode+" ) "+ UnitName);
		    map.put("brs_amt_n", brs_amt);
		    map.put("twad_cr", twad_cr);
		    map.put("sub1", sub1);
		    map.put("sub2", sub_str2);
		    map.put("sub3", sub_noEntry);
		    map.put("sub4", sub_noEntry_1);
		    
		    
		    System.out.println("*************************************************************");
		    System.out.println("sub1  "+sub1);	 
		    System.out.println("sub_noEntry===>"+sub_noEntry);
		    System.out.println("sub_str2   "+sub_str2);
		   // System.out.println(ctxpath);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, con);
			System.out.println("jasperPrint==>"+jasperPrint);
			
			String rtype = "PDF";
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
			 catch (Exception ex) {
					String connectMsg = "Could not create the report "
							+ ex.getMessage() + " uu " + ex.getLocalizedMessage();
					System.out.println(connectMsg);
				}
			}
			
			
			else if(cmd.equalsIgnoreCase("f_brs_check"))
			{
				System.out.println("coming here to freeze part 2c Report***");
				
				response.setContentType(CONTENT_TYPE);
				xml="<response><command>f_brs_check</command>";
				
				
				PrintWriter output = response.getWriter();

				int jk=0,checkfre=0;
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

		            	   try{   PreparedStatement ps_val=con.prepareStatement("select ACCOUNTING_UNIT_ID ,   Accounting_For_Office_Id ,sum(crAMT_VAL) crAMT_VAL,sum(drAMT_VAL) as drAMT_VAL from (SELECT  " +cboAcc_UnitCode+" AS ACCOUNTING_UNIT_ID , " +
		    					   cboOffice_code+ "    AS Accounting_For_Office_Id , " +
		    					"  AMT_VAL, " +
		    					"  TYPE, " +
		    					"  (SELECT TRANS_SHORT_DESC " +
		    				/*	"    ||' - ' " +
		    					"    ||crDr " +*/
		    					"  FROM FAS_BRS_TRANSACTION_TYPE " +
		    					"  WHERE TRANS_CODE=TYPE " +
		    					"  ) AS exp, " +
		    					" case when crDr='Credit' then AMT_VAL else 0 end as crAMT_VAL, " +
		    					" 	  case when crDr='Debit' then AMT_VAL else 0 end as drAMT_VAL " +
		    					"FROM " +
		    					"  (SELECT SUM(DR_AMOUNT) AS AMT_VAL, " +
		    					"    'Debit'             AS crDr, " +
		    					"    Transaction_Type     AS TYPE " +
		    					"  FROM FAS_BRS_TRANSACTION " +
		    					"  WHERE accounting_unit_id    =  " +cboAcc_UnitCode+
		    					"  AND Accounting_For_Office_Id=  " +cboOffice_code+
		    					"  AND Account_No              =  " +cmbBankAccNo+
		    					"  AND ((CASHBOOK_YEAR         < "+cboCashBook_Year+") " +
		    					"  OR (CASHBOOK_YEAR           ="+cboCashBook_Year+
		    					"  AND CASHBOOK_MONTH          < "+cboCashBook_Month+")) " +
		    					"  AND PASSBOOK_DATE          <= '"+prf_date+"' " +
		    					"  AND Twad_Or_Non_Twad        ='NT'     AND (CLEARED_BASED_ON_FOLLOWUP      ='N' or CLEARED_BASED_ON_FOLLOWUP is null ) " +
		    					"  GROUP BY 'Debit', " +
		    					"    Transaction_Type " +
		    					"  UNION ALL " +
		    					"  SELECT SUM(CR_AMOUNT) AS AMT_VAL, " +
		    					"    'Credit'             AS crDr, " +
		    					"    Transaction_Type    AS TYPE " +
		    					"  FROM FAS_BRS_TRANSACTION " +
		    					"  WHERE accounting_unit_id    =  " +cboAcc_UnitCode+
		    					"  AND Accounting_For_Office_Id=  " +cboOffice_code+
		    					"  AND Account_No              =  " +cmbBankAccNo+
		    					"  AND ((CASHBOOK_YEAR         < "+cboCashBook_Year+") " +
		    					"  OR (CASHBOOK_YEAR           ="+cboCashBook_Year+
		    					"  AND CASHBOOK_MONTH          <  "+cboCashBook_Month+")) " +
		    					"  AND PASSBOOK_DATE          <= '"+prf_date+"' " +
		    					"  AND Twad_Or_Non_Twad        ='NT'     AND (CLEARED_BASED_ON_FOLLOWUP      ='N' or CLEARED_BASED_ON_FOLLOWUP is null )  " +
		    					"  GROUP BY 'Credit', " +
		    					"    Transaction_Type " +
		    					"  )ty   where  TYPE is not null  and AMT_VAL <> 0 )");
		    		    ResultSet rs_new=ps_val.executeQuery();
		    		    if(rs_new.next()){
		    		    	uptoCR=rs_new.getDouble("crAMT_VAL");	
		    		    	uptoDR=rs_new.getDouble("drAMT_VAL");	
		    		    	System.out.println("uptoCR"+uptoCR);
		    		    	System.out.println("uptoDR"+uptoDR);
		    		    }else{
		    		    	uptoCR=0.0;
		    		    	uptoDR=0.0;
		    		    }
		    		    }catch(Exception e){
		    		    	e.printStackTrace();
		    		    }
		    		 try{
		    		    PreparedStatement ps_val1=con.prepareStatement("select ACCOUNTING_UNIT_ID ,   Accounting_For_Office_Id ,sum(crAMT_VAL1) crAMT_VAL1,sum(drAMT_VAL1) as drAMT_VAL1 from (SELECT "+cboAcc_UnitCode+ " AS ACCOUNTING_UNIT_ID , " +
		    					cboOffice_code+"   AS Accounting_For_Office_Id , " +
		    				
		    					"  TYPE, " +
		    					"  (SELECT TRANS_SHORT_DESC " +
		    				/*	"    ||' - ' " +
		    					"    ||crDr " +*/
		    					"  FROM FAS_BRS_TRANSACTION_TYPE " +
		    					"  WHERE TRANS_CODE=TYPE " +
		    					"  ) AS exp ," +
		    					" case when crDr='Credit' then AMT_VAL else 0 end as crAMT_VAL1, " +
		    					" 	  case when crDr='Debit' then AMT_VAL else 0 end as drAMT_VAL1 " +
		    					"FROM " +
		    					"  (SELECT SUM(CR_AMOUNT) AS AMT_VAL, " +
		    					"    'Credit'             AS crDr, " +
		    					"    Transaction_Type     AS TYPE " +
		    					"  FROM FAS_BRS_TRANSACTION " +
		    					"  WHERE accounting_unit_id                =" +cboAcc_UnitCode+ 
		    					"  AND Accounting_For_Office_Id            = " +cboOffice_code+ 
		    					"  AND Account_No                          = " +cmbBankAccNo+ 
		    					"  AND (( extract(YEAR FROM PASSBOOK_DATE) =" +cboCashBook_Year+ 
		    					"  AND extract(MONTH FROM PASSBOOK_DATE)   =  " +cboCashBook_Month+ ") " +
		    					"  OR (CASHBOOK_YEAR                       =" +cboCashBook_Year+ 
		    					"  AND CASHBOOK_MONTH                      =  " +cboCashBook_Month+ " )) " +
		    					"  AND Twad_Or_Non_Twad                    ='NT' " +
		    					"  GROUP BY 'Credit', " +
		    					"    Transaction_Type " +
		    					"  UNION ALL " +
		    					"  SELECT SUM(DR_AMOUNT) AS AMT_VAL, " +
		    					"    'Debit'             AS crDr, " +
		    					"    Transaction_Type    AS TYPE " +
		    					"  FROM FAS_BRS_TRANSACTION " +
		    					"  WHERE accounting_unit_id                = " +cboAcc_UnitCode+ 
		    					"  AND Accounting_For_Office_Id            =  " +cboOffice_code+ 
		    					"  AND Account_No                          = " +cmbBankAccNo+ 
		    					"  AND (( extract(YEAR FROM PASSBOOK_DATE) =" +cboCashBook_Year+ 
		    					"  AND extract(MONTH FROM PASSBOOK_DATE)   =  " +cboCashBook_Month+ ") " +
		    					"  OR (CASHBOOK_YEAR                       =" +cboCashBook_Year+ 
		    					"  AND CASHBOOK_MONTH                      =  " +cboCashBook_Month+ " )) " +
		    					"  AND Twad_Or_Non_Twad                    ='NT' " +
		    					"  GROUP BY 'Debit', " +
		    					"    Transaction_Type " +
		    					"  )TY   where  TYPE is not null  and AMT_VAL <> 0 )group by ACCOUNTING_UNIT_ID, Accounting_For_Office_Id");
		    		    ResultSet rs_new1=ps_val1.executeQuery();
		    		    if(rs_new1.next()){
		    		    	forCR=rs_new1.getDouble("crAMT_VAL1");	
		    		    	forDR=rs_new1.getDouble("drAMT_VAL1");	
		    		    	System.out.println("forCR"+forCR);
		    		    	System.out.println("forDR"+forDR);
		    		    }else{
		    		    	forCR=0.0;
		    		    	forDR=0.0;
		    		    }
		    		 }catch(Exception e){
		    			 e.printStackTrace();
		    		 }
		            	   
		            	   PreparedStatement psta=con.prepareStatement("select 'X' from FAS_BRS_PART_2C where ACCOUNTING_UNIT_ID="+cboAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cboOffice_code+" and PASS_SHEET_YEAR="+cboCashBook_Year+" and PASS_SHEET_MONTH="+cboCashBook_Month+" and ACCOUNT_NO="+cmbBankAccNo);
		            	   ResultSet rsss= psta.executeQuery();
		            	   if(rsss.next()){
		            		   checkfre=1;  
		            	   }
		            	   
		              
		               if(checkfre==1)
		               {
		            	   con.commit();
							con.setAutoCommit(true);
//							sendMessage(response,"Already Part-2C Frozen  ","ok");
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
			
			
			
			
			
			
			else if(cmd.equalsIgnoreCase("f_brs"))
			{
				response.setContentType(CONTENT_TYPE);
				
				System.out.println("coming here to freeze part 2c Report***");
				
				PrintWriter output = response.getWriter();

				int jk=0,checkfre=0;
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
		               {/*
		            	
			            	   
			            	   PreparedStatement psta=con.prepareStatement("select 'X' from FAS_BRS_PART_2C where ACCOUNTING_UNIT_ID="+cboAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cboOffice_code+" and PASS_SHEET_YEAR="+cboCashBook_Year+" and PASS_SHEET_MONTH="+cboCashBook_Month+" and ACCOUNT_NO="+cmbBankAccNo);
			            	   ResultSet rsss= psta.executeQuery();
			            	   if(rsss.next()){
			            		   checkfre=1;  
			            	   }
			            	   else{
			            	   double s1c= ((rss.getDouble("A_2A") +rss.getDouble("OB_PART2B") )-rss.getDouble("A_2E")) ;//$F{A_2A}.add($F{OB_PART2B}).subtract($F{A_2E})
			            	   double s33= (rss.getDouble("PASSBOOK_BALANCE") -(rss.getDouble("IDAMT")+(rss.getDouble("INT6AADD"))+(rss.getDouble("A_2A")+(rss.getDouble("OB_PART2B"))-rss.getDouble("A_2E") ))) ;
			            	double s4c= rss.getDouble("BANK_CR");
			            	// joan Added for OtherChrarges(service char) added into misscla amount (vasanthi TWAD - suggestion)
			            	double s4d=rss.getDouble("THREE_DR")+rss.getDouble("service_Charges");//or   $F{FOUR_CR}
			            	double s41a=rss.getDouble("COM_CHARGES");
			            	double s41b=rss.getDouble("ERR_DEPO");
			            	   
			            	   //  System.out.println("s1c "+s1c);
			            	  // System.out.println("s33 "+s33);
			            	   //$F{PASSBOOK_BALANCE}.subtract($F{IDAMT}.add($F{INT6AADD}).add($F{A_2A}.add($F{OB_PART2B}).subtract($F{A_2E})));
			            	  
			            	  // BANK_AMOUNT
			            	  // MISCLASS_AMOUNT
			            	  // COMMISSION_AMOUNT
			            	  // ERROR_DEPOSIT
			            	   PreparedStatement pss1=con.prepareStatement("insert into FAS_BRS_PART_2C (ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID," +
			            	   		"PASS_SHEET_YEAR,PASS_SHEET_MONTH,ACCOUNT_NO,S1A,S1b,S1D,S2,CHEQUE_CASHED,TWAD_AMOUNT," +
			            	   		"UPDATED_BY_USER_ID,UPDATED_DATE,BANK_ID,BRANCH_ID,S1C,S3,BANK_AMOUNT,MISCLASS_AMOUNT,COMMISSION_AMOUNT,ERROR_DEPOSIT) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
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
			                   
			                   //Lakshmi
			                   pss1.setDouble(16,s1c);
			                   pss1.setDouble(17,s33);
			                   //added by sathya 30/01/2015
			                   pss1.setDouble(18,s4c);
			                   pss1.setDouble(19,s4d);
			                   pss1.setDouble(20,s41a);
			                   pss1.setDouble(21,s41b);
			                   
			                   jk=pss1.executeUpdate();
			                   System.out.println("value jk:::"+jk);
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
			              
			               if(checkfre==1)
			               {
			            	   con.commit();
								con.setAutoCommit(true);
								sendMessage(response,"Already Part-2C Frozen  ","ok");
			               }
		            	   
		               */
		            	   
		               /*
		                * 
		                * Joan Change on 19 jun 15
		                * 
		                */

		            	   try{   PreparedStatement ps_val=con.prepareStatement("select ACCOUNTING_UNIT_ID ,   Accounting_For_Office_Id ,sum(crAMT_VAL) crAMT_VAL,sum(drAMT_VAL) as drAMT_VAL from (SELECT  " +cboAcc_UnitCode+" AS ACCOUNTING_UNIT_ID , " +
		    					   cboOffice_code+ "    AS Accounting_For_Office_Id , " +
		    					"  AMT_VAL, " +
		    					"  TYPE, " +
		    					"  (SELECT TRANS_SHORT_DESC " +
		    				/*	"    ||' - ' " +
		    					"    ||crDr " +*/
		    					"  FROM FAS_BRS_TRANSACTION_TYPE " +
		    					"  WHERE TRANS_CODE=TYPE " +
		    					"  ) AS exp, " +
		    					" case when crDr='Credit' then AMT_VAL else 0 end as crAMT_VAL, " +
		    					" 	  case when crDr='Debit' then AMT_VAL else 0 end as drAMT_VAL " +
		    					"FROM " +
		    					"  (SELECT SUM(DR_AMOUNT) AS AMT_VAL, " +
		    					"    'Debit'             AS crDr, " +
		    					"    Transaction_Type     AS TYPE " +
		    					"  FROM FAS_BRS_TRANSACTION " +
		    					"  WHERE accounting_unit_id    =  " +cboAcc_UnitCode+
		    					"  AND Accounting_For_Office_Id=  " +cboOffice_code+
		    					"  AND Account_No              =  " +cmbBankAccNo+
		    					"  AND ((CASHBOOK_YEAR         < "+cboCashBook_Year+") " +
		    					"  OR (CASHBOOK_YEAR           ="+cboCashBook_Year+
		    					"  AND CASHBOOK_MONTH          < "+cboCashBook_Month+")) " +
		    					"  AND PASSBOOK_DATE          <= '"+prf_date+"' " +
		    					"  AND Twad_Or_Non_Twad        ='NT'     AND (CLEARED_BASED_ON_FOLLOWUP      ='N' or CLEARED_BASED_ON_FOLLOWUP is null ) " +
		    					"  GROUP BY 'Debit', " +
		    					"    Transaction_Type " +
		    					"  UNION ALL " +
		    					"  SELECT SUM(CR_AMOUNT) AS AMT_VAL, " +
		    					"    'Credit'             AS crDr, " +
		    					"    Transaction_Type    AS TYPE " +
		    					"  FROM FAS_BRS_TRANSACTION " +
		    					"  WHERE accounting_unit_id    =  " +cboAcc_UnitCode+
		    					"  AND Accounting_For_Office_Id=  " +cboOffice_code+
		    					"  AND Account_No              =  " +cmbBankAccNo+
		    					"  AND ((CASHBOOK_YEAR         < "+cboCashBook_Year+") " +
		    					"  OR (CASHBOOK_YEAR           ="+cboCashBook_Year+
		    					"  AND CASHBOOK_MONTH          <  "+cboCashBook_Month+")) " +
		    					"  AND PASSBOOK_DATE          <= '"+prf_date+"' " +
		    					"  AND Twad_Or_Non_Twad        ='NT'     AND (CLEARED_BASED_ON_FOLLOWUP      ='N' or CLEARED_BASED_ON_FOLLOWUP is null )  " +
		    					"  GROUP BY 'Credit', " +
		    					"    Transaction_Type " +
		    					"  )ty   where  TYPE is not null  and AMT_VAL <> 0 )");
		    		    ResultSet rs_new=ps_val.executeQuery();
		    		    if(rs_new.next()){
		    		    	uptoCR=rs_new.getDouble("crAMT_VAL");	
		    		    	uptoDR=rs_new.getDouble("drAMT_VAL");	
		    		    	System.out.println("uptoCR"+uptoCR);
		    		    	System.out.println("uptoDR"+uptoDR);
		    		    }else{
		    		    	uptoCR=0.0;
		    		    	uptoDR=0.0;
		    		    }
		    		    }catch(Exception e){
		    		    	e.printStackTrace();
		    		    }
		    		 try{
		    		    PreparedStatement ps_val1=con.prepareStatement("select ACCOUNTING_UNIT_ID ,   Accounting_For_Office_Id ,sum(crAMT_VAL1) crAMT_VAL1,sum(drAMT_VAL1) as drAMT_VAL1 from (SELECT "+cboAcc_UnitCode+ " AS ACCOUNTING_UNIT_ID , " +
		    					cboOffice_code+"   AS Accounting_For_Office_Id , " +
		    				
		    					"  TYPE, " +
		    					"  (SELECT TRANS_SHORT_DESC " +
		    				/*	"    ||' - ' " +
		    					"    ||crDr " +*/
		    					"  FROM FAS_BRS_TRANSACTION_TYPE " +
		    					"  WHERE TRANS_CODE=TYPE " +
		    					"  ) AS exp ," +
		    					" case when crDr='Credit' then AMT_VAL else 0 end as crAMT_VAL1, " +
		    					" 	  case when crDr='Debit' then AMT_VAL else 0 end as drAMT_VAL1 " +
		    					"FROM " +
		    					"  (SELECT SUM(CR_AMOUNT) AS AMT_VAL, " +
		    					"    'Credit'             AS crDr, " +
		    					"    Transaction_Type     AS TYPE " +
		    					"  FROM FAS_BRS_TRANSACTION " +
		    					"  WHERE accounting_unit_id                =" +cboAcc_UnitCode+ 
		    					"  AND Accounting_For_Office_Id            = " +cboOffice_code+ 
		    					"  AND Account_No                          = " +cmbBankAccNo+ 
		    					"  AND (( extract(YEAR FROM PASSBOOK_DATE) =" +cboCashBook_Year+ 
		    					"  AND extract(MONTH FROM PASSBOOK_DATE)   =  " +cboCashBook_Month+ ") " +
		    					"  OR (CASHBOOK_YEAR                       =" +cboCashBook_Year+ 
		    					"  AND CASHBOOK_MONTH                      =  " +cboCashBook_Month+ " )) " +
		    					"  AND Twad_Or_Non_Twad                    ='NT' " +
		    					"  GROUP BY 'Credit', " +
		    					"    Transaction_Type " +
		    					"  UNION ALL " +
		    					"  SELECT SUM(DR_AMOUNT) AS AMT_VAL, " +
		    					"    'Debit'             AS crDr, " +
		    					"    Transaction_Type    AS TYPE " +
		    					"  FROM FAS_BRS_TRANSACTION " +
		    					"  WHERE accounting_unit_id                = " +cboAcc_UnitCode+ 
		    					"  AND Accounting_For_Office_Id            =  " +cboOffice_code+ 
		    					"  AND Account_No                          = " +cmbBankAccNo+ 
		    					"  AND (( extract(YEAR FROM PASSBOOK_DATE) =" +cboCashBook_Year+ 
		    					"  AND extract(MONTH FROM PASSBOOK_DATE)   =  " +cboCashBook_Month+ ") " +
		    					"  OR (CASHBOOK_YEAR                       =" +cboCashBook_Year+ 
		    					"  AND CASHBOOK_MONTH                      =  " +cboCashBook_Month+ " )) " +
		    					"  AND Twad_Or_Non_Twad                    ='NT' " +
		    					"  GROUP BY 'Debit', " +
		    					"    Transaction_Type " +
		    					"  )TY   where  TYPE is not null  and AMT_VAL <> 0 )group by ACCOUNTING_UNIT_ID, Accounting_For_Office_Id");
		    		    ResultSet rs_new1=ps_val1.executeQuery();
		    		    if(rs_new1.next()){
		    		    	forCR=rs_new1.getDouble("crAMT_VAL1");	
		    		    	forDR=rs_new1.getDouble("drAMT_VAL1");	
		    		    	System.out.println("forCR"+forCR);
		    		    	System.out.println("forDR"+forDR);
		    		    }else{
		    		    	forCR=0.0;
		    		    	forDR=0.0;
		    		    }
		    		 }catch(Exception e){
		    			 e.printStackTrace();
		    		 }
		    		 
		    		
		    		 try{   PreparedStatement ps_val=con.prepareStatement("select ACCOUNTING_UNIT_ID ,   Accounting_For_Office_Id ,sum(crAMT_VAL) crAMT_VAL,sum(drAMT_VAL) as drAMT_VAL from (SELECT  " +cboAcc_UnitCode+" AS ACCOUNTING_UNIT_ID , " +
	    					   cboOffice_code+ "    AS Accounting_For_Office_Id , " +
	    					"  AMT_VAL, " +
	    					"  TYPE, " +
	    					"  (SELECT TRANS_SHORT_DESC " +
	    				/*	"    ||' - ' " +
	    					"    ||crDr " +*/
	    					"  FROM FAS_BRS_TRANSACTION_TYPE " +
	    					"  WHERE TRANS_CODE=TYPE " +
	    					"  ) AS exp, " +
	    					" case when crDr='Credit' then AMT_VAL else 0 end as crAMT_VAL, " +
	    					" 	  case when crDr='Debit' then AMT_VAL else 0 end as drAMT_VAL " +
	    					"FROM " +
	    					"  (SELECT SUM(DR_AMOUNT) AS AMT_VAL, " +
	    					"    'Debit'             AS crDr, " +
	    					"    REASON_FOR_DIFFERENCE     AS TYPE " +
	    					"  FROM FAS_BRS_TRANSACTION_NOENTRY " +
	    					"  WHERE accounting_unit_id    =  " +cboAcc_UnitCode+
	    					"  AND Accounting_For_Office_Id=  " +cboOffice_code+
	    					"  AND Account_No              =  " +cmbBankAccNo+
	    					"  AND ((CASHBOOK_YEAR         < "+cboCashBook_Year+") " +
	    					"  OR (CASHBOOK_YEAR           ="+cboCashBook_Year+
	    					"  AND CASHBOOK_MONTH          < "+cboCashBook_Month+")) " +
	    					"  AND REASON_FOR_DIFFERENCE               = 45 " +
	    					"  AND FOLLOW_UP_ACTION_REQUIRED           = 'Y'" +
	    					"  AND (CLEARED_BASED_ON_FOLLOWUP      ='N' or CLEARED_BASED_ON_FOLLOWUP is null ) " +
	    					"  GROUP BY 'Debit', " +
	    					"    REASON_FOR_DIFFERENCE " +
	    					"  UNION ALL " +
	    					"  SELECT SUM(CR_AMOUNT) AS AMT_VAL, " +
	    					"    'Credit'             AS crDr, " +
	    					"    REASON_FOR_DIFFERENCE    AS TYPE " +
	    					"  FROM FAS_BRS_TRANSACTION_NOENTRY " +
	    					"  WHERE accounting_unit_id    =  " +cboAcc_UnitCode+
	    					"  AND Accounting_For_Office_Id=  " +cboOffice_code+
	    					"  AND Account_No              =  " +cmbBankAccNo+
	    					"  AND ((CASHBOOK_YEAR         < "+cboCashBook_Year+") " +
	    					"  OR (CASHBOOK_YEAR           ="+cboCashBook_Year+
	    					"  AND CASHBOOK_MONTH          <  "+cboCashBook_Month+")) " +
	    					"  AND REASON_FOR_DIFFERENCE               = 45 " +
	    					"  AND FOLLOW_UP_ACTION_REQUIRED           = 'Y'" +
	    					"  AND (CLEARED_BASED_ON_FOLLOWUP      ='N' or CLEARED_BASED_ON_FOLLOWUP is null )  " +
	    					"  GROUP BY 'Credit', " +
	    					"    REASON_FOR_DIFFERENCE " +
	    					"  )ty   where  TYPE is not null  and AMT_VAL <> 0 )");
	    		    ResultSet rs_new=ps_val.executeQuery();
	    		    if(rs_new.next()){
	    		    	notreconcile_uptoCR=rs_new.getDouble("crAMT_VAL");	
	    		    	notreconcile_uptoDR=rs_new.getDouble("drAMT_VAL");	
	    		    	System.out.println("notreconcile_uptoCR"+notreconcile_uptoCR);
	    		    	System.out.println("notreconcile_uptoDR"+notreconcile_uptoDR);
	    		    }else{
	    		    	notreconcile_uptoCR=0.0;
	    		    	notreconcile_uptoDR=0.0;
	    		    }
	    		    }catch(Exception e){
	    		    	e.printStackTrace();
	    		    }
		    		 
		    		 
		    		 
		    		 
		    		 try{
			    		    PreparedStatement ps_val11=con.prepareStatement("select ACCOUNTING_UNIT_ID ,   Accounting_For_Office_Id ,sum(crAMT_VAL1) crAMT_VAL1,sum(drAMT_VAL1) as drAMT_VAL1 from (SELECT "+cboAcc_UnitCode+ " AS ACCOUNTING_UNIT_ID , " +
			    					cboOffice_code+"   AS Accounting_For_Office_Id , " +
			    				
			    					"  TYPE, " +
			    					"  (SELECT TRANS_SHORT_DESC " +
			    				/*	"    ||' - ' " +
			    					"    ||crDr " +*/
			    					"  FROM FAS_BRS_TRANSACTION_TYPE " +
			    					"  WHERE TRANS_CODE=TYPE " +
			    					"  ) AS exp ," +
			    					" case when crDr='Credit' then AMT_VAL else 0 end as crAMT_VAL1, " +
			    					" 	  case when crDr='Debit' then AMT_VAL else 0 end as drAMT_VAL1 " +
			    					"FROM " +
			    					"  (SELECT SUM(CR_AMOUNT) AS AMT_VAL, " +
			    					"    'Credit'             AS crDr, " +
			    					"    REASON_FOR_DIFFERENCE     AS TYPE " +
			    					"  FROM FAS_BRS_TRANSACTION_NOENTRY " +
			    					"  WHERE accounting_unit_id                =" +cboAcc_UnitCode+ 
			    					"  AND Accounting_For_Office_Id            = " +cboOffice_code+ 
			    					"  AND Account_No                          = " +cmbBankAccNo+ 
			    					"  AND CASHBOOK_YEAR                       =" +cboCashBook_Year+ 
			    					"  AND CASHBOOK_MONTH                      = " +cboCashBook_Month+ 
			    					"  AND REASON_FOR_DIFFERENCE               = 45 " +
			    					"  AND FOLLOW_UP_ACTION_REQUIRED           = 'Y'" +
			    					"  GROUP BY 'Credit', " +
			    					"    REASON_FOR_DIFFERENCE " +
			    					"  UNION ALL " +
			    					"  SELECT SUM(DR_AMOUNT) AS AMT_VAL, " +
			    					"    'Debit'             AS crDr, " +
			    					"    REASON_FOR_DIFFERENCE    AS TYPE " +
			    					"  FROM FAS_BRS_TRANSACTION_NOENTRY " +
			    					"  WHERE accounting_unit_id                = " +cboAcc_UnitCode+ 
			    					"  AND Accounting_For_Office_Id            =  " +cboOffice_code+ 
			    					"  AND Account_No                          = " +cmbBankAccNo+ 
			    					"  AND CASHBOOK_YEAR                       =" +cboCashBook_Year+ 
			    					"  AND CASHBOOK_MONTH                      = " +cboCashBook_Month+
			    					"  AND REASON_FOR_DIFFERENCE               = 45 " +
			    					"  AND FOLLOW_UP_ACTION_REQUIRED           = 'Y'" +
			    					"  GROUP BY 'Debit', " +
			    					"    REASON_FOR_DIFFERENCE " +
			    					"  )TY   where  TYPE is not null  and AMT_VAL <> 0 )group by ACCOUNTING_UNIT_ID, Accounting_For_Office_Id");
			    		    ResultSet rs_new1=ps_val11.executeQuery();
			    		    if(rs_new1.next()){
			    		    	notreconcile_forCR=rs_new1.getDouble("crAMT_VAL1");	
			    		    	notreconcile_forDR=rs_new1.getDouble("drAMT_VAL1");	
			    		    	System.out.println("notreconcile_forCR"+notreconcile_forCR);
			    		    	System.out.println("notreconcile_forDR"+notreconcile_forDR);
			    		    }else{
			    		    	notreconcile_forCR=0.0;
			    		    	notreconcile_forDR=0.0;
			    		    }
			    		 }catch(Exception e){
			    			 e.printStackTrace();
			    		 }
		    		 
		    		 
		    		 
		    		 
		            	   
		            	   PreparedStatement psta=con.prepareStatement("select 'X' from FAS_BRS_PART_2C where ACCOUNTING_UNIT_ID="+cboAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cboOffice_code+" and PASS_SHEET_YEAR="+cboCashBook_Year+" and PASS_SHEET_MONTH="+cboCashBook_Month+" and ACCOUNT_NO="+cmbBankAccNo);
		            	   ResultSet rsss= psta.executeQuery();
		            	   if(rsss.next()){
		            		   checkfre=1;  
		            	   }
		            	   else{
		            	   double s1c= ((rss.getDouble("A_2A") +rss.getDouble("OB_PART2B") )-rss.getDouble("A_2E")) ;//$F{A_2A}.add($F{OB_PART2B}).subtract($F{A_2E})
		            	//   double s33= (rss.getDouble("PASSBOOK_BALANCE") -(rss.getDouble("IDAMT")+(rss.getDouble("INT6AADD"))+(rss.getDouble("A_2A")+(rss.getDouble("OB_PART2B"))-rss.getDouble("A_2E") ))) ;
		            	 
		            	
		            	   double s33= (rss.getDouble("PASSBOOK_BALANCE") -(rss.getDouble("IDAMT")+rss.getDouble("UNCHEQUE")+rss.getDouble("BALANCE")));
		            	   double s4c= rss.getDouble("BANK_CR");
		            	// joan Added for OtherChrarges(service char) added into misscla amount (vasanthi TWAD - suggestion)
		            	double s4d=rss.getDouble("THREE_DR")+rss.getDouble("service_Charges");//or   $F{FOUR_CR}
		            	double s41a=rss.getDouble("COM_CHARGES");
		            	double s41b=rss.getDouble("ERR_DEPO");
		            	   
		            	   //  System.out.println("s1c "+s1c);
		            	  // System.out.println("s33 "+s33);
		            	   //$F{PASSBOOK_BALANCE}.subtract($F{IDAMT}.add($F{INT6AADD}).add($F{A_2A}.add($F{OB_PART2B}).subtract($F{A_2E})));
		            	  
		            	  // BANK_AMOUNT
		            	  // MISCLASS_AMOUNT
		            	  // COMMISSION_AMOUNT
		            	  // ERROR_DEPOSIT
		            	
		            	   PreparedStatement pss1=con.prepareStatement("insert into FAS_BRS_PART_2C (ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID," +
		            	   		"PASS_SHEET_YEAR,PASS_SHEET_MONTH,ACCOUNT_NO,S1A,S1b,"
		            	   		+ "UPTO_NT_CR,UPTO_NT_DR," +
		            	   		"UPDATED_BY_USER_ID,UPDATED_DATE,S1C,S3,FOR_NT_CR,FOR_NT_DR,BANK_ID,BRANCH_ID,S2,NOTRECONCILED_UPTO_REASON45_CR," + 
		            	   		"NOTRECONCILED_UPTO_REASON45_DR," + 
		            	   		"NOTRECONCILED_FOR_REASON45_CR," + 
		            	   		"NOTRECONCILED_FOR_REASON45_DR) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		            	   pss1.setInt(1,unitcode);
		            	   pss1.setInt(2,offCode);
		            	   pss1.setInt(3,passYear);
		            	   pss1.setInt(4,passMonth);
		            	   pss1.setLong(5,accNo);
		            	   pss1.setDouble(6,rss.getDouble("Idamt")); 
		            	   pss1.setDouble(7, rss.getDouble("UNCHEQUE"));
		            	//   pss1.setDouble(7,rss.getDouble("A_6a"));
		            	   pss1.setDouble(8,uptoCR);
		            	   pss1.setDouble(9,uptoDR);
		            	   pss1.setString(10,update_user);
		                   pss1.setTimestamp(11,ts);
		                //   pss1.setDouble(12,s1c);
		                   pss1.setDouble(12, rss.getDouble("BALANCE"));
		                   pss1.setDouble(13,s33);
		                   pss1.setDouble(14,forCR);
		                   pss1.setDouble(15,forDR);
		                   pss1.setInt(16,bank_id);
		                   pss1.setInt(17,branch_id);
		                   pss1.setDouble(18,rss.getDouble("Passbook_Balance"));
		                   pss1.setDouble(19,notreconcile_uptoCR);
		            	   pss1.setDouble(20,notreconcile_uptoDR);
		                   pss1.setDouble(21,notreconcile_forCR);
		                   pss1.setDouble(22,notreconcile_forDR);
		                   jk=pss1.executeUpdate();
		                   System.out.println("value jk:::"+jk);
		                   if(jk>0)
			               {
			            	    con.commit();
								con.setAutoCommit(true);
//								sendMessage(response,"Records Inserted Successfully  ","ok"); 
					              xml="<response><command><flag>success</flag></command>";

			               }
			               else
			               {
			            	    con.rollback();
								con.setAutoCommit(true);
//			            	    sendMessage(response,"Records Not Inserted into Part-2c ","ok");  
								xml="<response><command><flag>failure</flag></command>";
			               }
		               }
		              
		               if(checkfre==1)
		               {
		            	   con.commit();
							con.setAutoCommit(true);
//							sendMessage(response,"Already Part-2C Frozen  ","ok");
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
			
			System.out.println("error in report"+ex);
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
	
/*	private static Date getNextDate(Date nowDate) {
	    Calendar c = Calendar.getInstance();
	    c.setTime(nowDate);
	    c.add(Calendar.MONTH, 1);
	    c.set(Calendar.DATE, c.getMaximum(Calendar.DATE));
	    Date nextDate = c.getTime();
	    return nextDate;
	}*/
}
