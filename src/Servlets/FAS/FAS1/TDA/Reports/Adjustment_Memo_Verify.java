package Servlets.FAS.FAS1.TDA.Reports;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Servlets.Security.classes.UserProfile;

/**
 * Servlet implementation class Adjustment_Memo_Verify
 */
public class Adjustment_Memo_Verify extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Adjustment_Memo_Verify() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		System.out.println("units only:::");
		 response.setHeader("Cache-Control", "no-cache");
        String CONTENT_TYPE = "text/xml; charset=windows-1252";
        response.setContentType(CONTENT_TYPE);
       
        PrintWriter out = response.getWriter();
        String cmd;
        int major;
        int unitid=0,offid=0,invoiceNo=0,billno=0,headcode=0;
        String todate="";
        int agreeno=0,count=0,verified_already=0;
        String isection="",expen="";
        String xml="",sql="";
        
        Connection con=null;
        PreparedStatement ps=null,ps2=null,ps3=null,ps4=null,pss2=null;
        Statement st=null;
        ResultSet result=null,rs3=null,rss2=null;
        int eid=0;
        cmd=request.getParameter("command");
        try
        {
                 ResourceBundle rs=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
                 String ConnectionString="";
                
                 String strDriver=rs.getString("Config.DATA_BASE_DRIVER");
                 String strdsn=rs.getString("Config.DSN");
                 String strhostname=rs.getString("Config.HOST_NAME");
                 String strportno=rs.getString("Config.PORT_NUMBER");
                 String strsid=rs.getString("Config.SID");
                 String strdbusername=rs.getString("Config.USER_NAME");
                 String strdbpassword=rs.getString("Config.PASSWORD");
                   
                 ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection        
                  Class.forName(strDriver.trim());
                  con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
                  try
                  {
                        st=con.createStatement();
                        con.clearWarnings();
                  }
                  catch(SQLException e)
                  {
                        System.out.println("Exception in creating statement:"+e);
                  }          
        }
        catch(Exception e)
        {
                   System.out.println("Exception in openeing connection:"+e);
        }
          
        HttpSession session=request.getSession(false);
        UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
        eid=empProfile.getEmployeeId();
        int cmbAcc_UnitCode=0,cmbOffice_code=0,txtCB_Month=0,txtCB_Year=0,advnumber=0;
        System.out.println("employee id:"+eid);
         try
         {
            
                if(session==null)
                {
                    System.out.println(request.getContextPath()+"/index.jsp");
                    response.sendRedirect(request.getContextPath()+"/index.jsp");                   
                }
                System.out.println(session);
                
        }catch(Exception e)
        {
        //System.out.println("Redirect Error :"+e);
        }
         String userid=(String)session.getAttribute("UserId");
         System.out.println("session id is:"+userid);
         long l=System.currentTimeMillis();
         Timestamp ts=new Timestamp(l);
         
         if(cmd.equalsIgnoreCase("submit_all")) 
         {
        	 int errcode=0,err=0;
        	 try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
             catch(NumberFormatException e){System.out.println("exception"+e );}
             try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
             catch(NumberFormatException e){System.out.println("exception"+e );}
             try{txtCB_Year=Integer.parseInt(request.getParameter("txtCB_Year"));}
             catch(NumberFormatException e){System.out.println("exception"+e );}
             try{txtCB_Month=Integer.parseInt(request.getParameter("txtCB_Month"));}
             catch(NumberFormatException e){System.out.println("exception in txtCB_Month:"+e );}
               xml="<response><command>submit_all</command>";
               
               try
               {
            	   ps3=con.prepareStatement("Select Verify_Flag From Fas_AdjMemo_Monthend Where Accounting_Unit_Id=? and ACCOUNTING_FOR_OFFICE_ID=? and Cashbook_Year=? and CASHBOOK_MONTH=?::varchar ");
            	   ps3.setInt(1,cmbAcc_UnitCode);
            	   ps3.setInt(2,cmbOffice_code);
            	   ps3.setInt(3,txtCB_Year);
            	   ps3.setInt(4,txtCB_Month);
            	   rs3=ps3.executeQuery();
            	   while(rs3.next())
            	   {
            		   verified_already++;	
            	   }
            	   rs3.close();
            	   ps3.close();
               }
               catch(Exception qe)
               {
            	 System.out.println("error in delete");  
               }
               
               try{
            	   ps2=con.prepareStatement("insert into Fas_AdjMemo_Monthend (ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,VERIFY_FLAG,UPDATED_BY_USERID,UPDATED_DATE) values(?,?,?,?,?,?,?)");
            	   ps2.setInt(1,cmbAcc_UnitCode);
            	   ps2.setInt(2,cmbOffice_code);
            	   ps2.setInt(3,txtCB_Year);
            	   ps2.setInt(4,txtCB_Month);
            	   ps2.setString(5,"Y");
            	   ps2.setString(6,userid);
            	   ps2.setTimestamp(7,ts);
            	   errcode=ps2.executeUpdate();
                   if(errcode==0)
                   {         
                       System.out.println("redirect");
                       xml=xml+"<flag>failure</flag>";  
                       
                   }
                   else
                   { 
                	   xml=xml+"<flag>success</flag>";
                   }
            	
               }
               catch(Exception ee)
               {
            	   System.out.println("exception in submit:::"+ee);
               }
               xml=xml+"</response>";
         }
         if(cmd.equalsIgnoreCase("verify_cr_dr"))
         {
     	 try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
          catch(NumberFormatException e){System.out.println("exception"+e );}
       //   System.out.println("cmbOffice_code "+cmbOffice_code);
          try{txtCB_Year=Integer.parseInt(request.getParameter("txtCB_Year"));}
          catch(NumberFormatException e){System.out.println("exception"+e );}
          try{txtCB_Month=Integer.parseInt(request.getParameter("txtCB_Month"));}
          catch(NumberFormatException e){System.out.println("exception in txtCB_Month:"+e );}
            xml="<response><command>verify_cr_dr</command>"; 
             try 
                     {
            	 String que="";
	              if(cmbAcc_UnitCode==5)
	              {
            	  que="SELECT Unit_Id,\n" + 
            	 		"  (SELECT U.Accounting_Unit_Name\n" + 
            	 		"  FROM Fas_Mst_Acct_Units u\n" + 
            	 		"  WHERE U.Accounting_Unit_Id=unit_id\n" + 
            	 		"  )AS unit_name,\n" + 
            	 		"  Cashbook_Month,\n" + 
            	 		"  CASE\n" + 
            	 		"    WHEN Cashbook_Month=1\n" + 
            	 		"    THEN 'January'\n" + 
            	 		"    WHEN Cashbook_Month=2\n" + 
            	 		"    THEN 'February'\n" + 
            	 		"    WHEN Cashbook_Month=3\n" + 
            	 		"    THEN 'March'\n" + 
            	 		"    WHEN Cashbook_Month=4\n" + 
            	 		"    THEN 'April'\n" + 
            	 		"    WHEN Cashbook_Month=5\n" + 
            	 		"    THEN 'May'\n" + 
            	 		"    WHEN Cashbook_Month=6\n" + 
            	 		"    THEN 'June'\n" + 
            	 		"    WHEN Cashbook_Month=7\n" + 
            	 		"    THEN 'July'\n" + 
            	 		"    WHEN Cashbook_Month=8\n" + 
            	 		"    THEN 'August'\n" + 
            	 		"    WHEN Cashbook_Month=9\n" + 
            	 		"    THEN 'September'\n" + 
            	 		"    WHEN Cashbook_Month=10\n" + 
            	 		"    THEN 'October'\n" + 
            	 		"    WHEN Cashbook_Month=11\n" + 
            	 		"    THEN 'November'\n" + 
            	 		"    WHEN Cashbook_Month=12\n" + 
            	 		"    THEN 'December'\n" + 
            	 		"  END AS month_desc,\n" + 
            	 		"  ACCOUNT_HEAD_CODE,\n" + 
            	 		"  TRN_DR,\n" + 
            	 		"  TRN_CR,\n" + 
//            	 		"  AdjMemo_Dr,\n" + 
//            	 		"  AdjMemo_Cr,\n" + 
						" coalesce (ADJMEMO_DR,NULL,0,ADJMEMO_DR) as AdjMemo_Dr,\n" + 
						"  coalesce (AdjMemo_Cr,NULL,0,AdjMemo_Cr) as AdjMemo_Cr,\n" +
            	 		"  Trn_Net,\n" + 
            	 		"  AdjMemo_Net,\n" + 
            	 		"  (Trn_Net-AdjMemo_net)AS difference\n" + 
            	 		"FROM\n" + 
            	 		"  (SELECT aaa.Accounting_Unit_Id AS unit_id,\n" + 
            	 		"    Aaa.Cashbook_Month,\n" + 
            	 		"    Aaa.Account_Head_Code,\n" + 
            	 		"    Aaa.Trn_Dr,\n" + 
            	 		"    Aaa.Trn_Cr,\n" + 
            	 		"    Bbb.AdjMemo_Dr,\n" + 
            	 		"    Bbb.AdjMemo_Cr,\n" + 
            	 		"    CASE\n" + 
            	 		"      WHEN Aaa.Trn_Dr>Aaa.Trn_Cr\n" + 
            	 		"      THEN (Aaa.Trn_Dr-Aaa.Trn_Cr)\n" + 
            	 		"      WHEN Aaa.Trn_Cr>Aaa.Trn_Dr\n" + 
            	 		"      THEN (Aaa.Trn_Cr-Aaa.Trn_Dr)\n" + 
            	 		"      ELSE 0\n" + 
            	 		"    END AS Trn_Net,\n" + 
            	 		"    CASE\n" + 
            	 		"      WHEN Bbb.AdjMemo_Dr>Bbb.AdjMemo_Cr\n" + 
            	 		"      THEN (Bbb.AdjMemo_Dr-Bbb.AdjMemo_Cr)\n" + 
            	 		"      WHEN Bbb.AdjMemo_Cr>Bbb.AdjMemo_Dr\n" + 
            	 		"      THEN (Bbb.AdjMemo_Cr-Bbb.AdjMemo_Dr)\n" + 
            	 		"      ELSE 0\n" + 
            	 		"    END AS AdjMemo_Net\n" + 
            	 		"  FROM\n" + 
            	 		"    (SELECT Accounting_Unit_Id,\n" + 
            	 		"      Cashbook_Month,\n" + 
            	 		"      Account_Head_Code,\n" + 
            	 		"      SUM(dr_Amount)AS trn_dr,\n" + 
            	 		"      SUM(cr_amount)AS trn_cr\n" + 
            	 		"    FROM\n" + 
            	 		"      (SELECT Accounting_Unit_Id,\n" + 
            	 		"        Cashbook_Month,\n" + 
            	 		"        Account_Head_Code,\n" + 
            	 		"        SUM(dr_Amount)AS dr_Amount,\n" + 
            	 		"        SUM(cr_amount)AS cr_amount\n" + 
            	 		"      FROM\n" + 
            	 		"        (SELECT B.Cashbook_Month,\n" + 
            	 		"          B.Account_Head_Code,\n" + 
            	 		"          B.Accounting_Unit_Id,\n" + 
            	 		"          CASE\n" + 
            	 		"            WHEN B.Cr_Dr_Indicator='DR'\n" + 
            	 		"            THEN SUM(B.Amount)\n" + 
            	 		"            ELSE 0\n" + 
            	 		"          END AS dr_Amount,\n" + 
            	 		"          CASE\n" + 
            	 		"            WHEN b.Cr_Dr_Indicator='CR'\n" + 
            	 		"            THEN SUM(B.Amount)\n" + 
            	 		"            ELSE 0\n" + 
            	 		"          END AS cr_amount\n" + 
            	 		"        FROM fas_journal_master a,\n" + 
            	 		"          Fas_Journal_Transaction b\n" + 
            	 		"        WHERE A.Accounting_Unit_Id    =b.Accounting_Unit_Id\n" + 
            	 		"        AND A.Accounting_For_Office_Id=B.Accounting_For_Office_Id\n" + 
            	 		"        AND A.Cashbook_Year           =B.Cashbook_Year\n" + 
            	 		"        AND A.Cashbook_Month          =B.Cashbook_Month\n" + 
            	 		"        AND A.Voucher_No              =B.Voucher_No\n" + 
            	 		"        AND A.JOURNAL_STATUS          ='L'\n" + 
            	 		"        AND B.Accounting_Unit_Id      =" +cmbAcc_UnitCode +
            	 		"        AND (a.SUPPLEMENT_NO         IS NULL\n" + 
            	 		"        OR a.SUPPLEMENT_NO            =0)\n" + 
            	 		"        AND B.CASHBOOK_YEAR           =" +txtCB_Year+
            	 		"        AND B.CASHBOOK_MONTH          =" +txtCB_Month+ 
            	 		"        AND B.Account_Head_Code      IN (610101,610102,900201,900202)\n" + 
            	 		"        GROUP BY B.Accounting_Unit_Id,\n" + 
            	 		"          B.Cashbook_Month,\n" + 
            	 		"          B.Account_Head_Code,\n" + 
            	 		"          B.Cr_Dr_Indicator\n" + 
            	 		"        )as ps\n" + 
            	 		"      GROUP BY Accounting_Unit_Id,\n" + 
            	 		"        Cashbook_Month,\n" + 
            	 		"        Account_Head_Code\n" + 
            	 		"      UNION ALL\n" + 
            	 		"      SELECT B.Accounting_Unit_Id,\n" + 
            	 		"        B.Cashbook_Month,\n" + 
            	 		"        B.Account_Head_Code,\n" + 
            	 		"        CASE\n" + 
            	 		"          WHEN B.Cr_Dr_Indicator='DR'\n" + 
            	 		"          THEN SUM(B.Amount)\n" + 
            	 		"          ELSE 0\n" + 
            	 		"        END AS dr_Amount,\n" + 
            	 		"        CASE\n" + 
            	 		"          WHEN b.Cr_Dr_Indicator='CR'\n" + 
            	 		"          THEN SUM(B.Amount)\n" + 
            	 		"          ELSE 0\n" + 
            	 		"        END AS cr_amount\n" + 
            	 		"      FROM FAS_PAYMENT_MASTER a,\n" + 
            	 		"        FAS_PAYMENT_TRANSACTION b\n" + 
            	 		"      WHERE A.Accounting_Unit_Id    =b.Accounting_Unit_Id\n" + 
            	 		"      AND A.Accounting_For_Office_Id=B.Accounting_For_Office_Id\n" + 
            	 		"      AND A.Cashbook_Year           =B.Cashbook_Year\n" + 
            	 		"      AND A.Cashbook_Month          =B.Cashbook_Month\n" + 
            	 		"      AND A.Voucher_No              =B.Voucher_No\n" + 
            	 		"      AND B.Accounting_Unit_Id      =" +cmbAcc_UnitCode +
            	 		"      AND A.Payment_Status          ='L'\n" + 
            	 		"      AND B.Cashbook_Year           =" +txtCB_Year+
            	 		"      AND B.CASHBOOK_MONTH          =" +txtCB_Month+ 
            	 		"      AND B.Account_Head_Code      IN (610101,610102,900201,900202)\n" + 
            	 		"      GROUP BY B.Accounting_Unit_Id,\n" + 
            	 		"        B.Cashbook_Month,\n" + 
            	 		"        B.Account_Head_Code,\n" + 
            	 		"        B.Cr_Dr_Indicator\n" + 
            	 		"      UNION ALL\n" + 
            	 		"      SELECT B.Accounting_Unit_Id,\n" + 
            	 		"        B.Cashbook_Month,\n" + 
            	 		"        B.Account_Head_Code,\n" + 
            	 		"        CASE\n" + 
            	 		"          WHEN B.Cr_Dr_Indicator='DR'\n" + 
            	 		"          THEN SUM(B.Amount)\n" + 
            	 		"          ELSE 0\n" + 
            	 		"        END AS dr_Amount,\n" + 
            	 		"        CASE\n" + 
            	 		"          WHEN b.Cr_Dr_Indicator='CR'\n" + 
            	 		"          THEN SUM(B.Amount)\n" + 
            	 		"          ELSE 0\n" + 
            	 		"        END AS cr_amount\n" + 
            	 		"      FROM FAS_RECEIPT_MASTER a,\n" + 
            	 		"        FAS_RECEIPT_TRANSACTION b\n" + 
            	 		"      WHERE A.Accounting_Unit_Id    =b.Accounting_Unit_Id\n" + 
            	 		"      AND A.Accounting_For_Office_Id=B.Accounting_For_Office_Id\n" + 
            	 		"      AND A.Cashbook_Year           =B.Cashbook_Year\n" + 
            	 		"      AND A.Cashbook_Month          =B.Cashbook_Month\n" + 
            	 		"      AND A.RECEIPT_NO              =B.RECEIPT_NO\n" + 
            	 		"      AND A.RECEIPT_STATUS          ='L'\n" + 
            	 		"      AND B.Accounting_Unit_Id      ="+cmbAcc_UnitCode +
            	 		"      AND B.CASHBOOK_YEAR           ="+txtCB_Year+
            	 		"      AND B.CASHBOOK_MONTH          ="+txtCB_Month+ 
            	 		"      AND B.Account_Head_Code      IN (610101,610102,900201,900202)\n" + 
            	 		"      GROUP BY B.Accounting_Unit_Id,\n" + 
            	 		"        B.Cashbook_Month,\n" + 
            	 		"        B.Account_Head_Code,\n" + 
            	 		"        B.Cr_Dr_Indicator\n" + 
            	 		"      ) as ps\n" + 
            	 		"    GROUP BY Accounting_Unit_Id,\n" + 
            	 		"      Cashbook_Month,\n" + 
            	 		"      Account_Head_Code\n" + 
            	 		"    ORDER BY Accounting_Unit_Id,\n" + 
            	 		"      Cashbook_Month,\n" + 
            	 		"      Account_Head_Code\n" + 
            	 		"    )aaa\n" + 
            	 		"  FULL OUTER JOIN\n" + 
            	 		"    (SELECT Accounting_Unit_Id,\n" + 
            	 		"      Cashbook_Month,\n" + 
            	 		"      Account_Head_Code,\n" + 
            	 		"      SUM(dr_Amount)AS AdjMemo_dr,\n" + 
            	 		"      SUM(cr_amount)AS AdjMemo_cr\n" + 
            	 		"    FROM\n" + 
            	 		"      (SELECT B.Accounting_Unit_Id,\n" + 
            	 		"        B.Cashbook_Month,\n" + 
            	 		"        B.Account_Head_Code,\n" + 
            	 		"        CASE\n" + 
            	 		"          WHEN B.CR_DR_TYPE='DR'\n" + 
            	 		"          THEN SUM(B.Amount)\n" + 
            	 		"          ELSE 0\n" + 
            	 		"        END AS dr_Amount,\n" + 
            	 		"        CASE\n" + 
            	 		"          WHEN b.CR_DR_TYPE='CR'\n" + 
            	 		"          THEN SUM(B.Amount)\n" + 
            	 		"          ELSE 0\n" + 
            	 		"        END AS cr_amount\n" + 
            	 		"      FROM FAS_ADJUST_MEMO_MST A,\n" + 
            	 		"        FAS_ADJUST_MEMO_TRN B\n" + 
            	 		"      WHERE A.Accounting_Unit_Id    =b.Accounting_Unit_Id\n" + 
            	 		"      AND A.Accounting_For_Office_Id=B.Accounting_For_Office_Id\n" + 
            	 		"      AND A.Cashbook_Year           =B.Cashbook_Year\n" + 
            	 		"      AND A.Cashbook_Month          =B.Cashbook_Month\n" + 
            	 		"      AND A.VOUCHER_NO              =B.VOUCHER_NO\n" + 
            	 		"      AND A.MEMO_STATUS             ='L'\n" + 
            	 		"      AND b.ACCOUNTING_UNIT_ID      =" +cmbAcc_UnitCode +
            	 		"      AND B.CASHBOOK_YEAR           =" +txtCB_Year+  
            	 		"      AND B.CASHBOOK_MONTH          =" +txtCB_Month+ 
            	 		"      AND B.Account_Head_Code      IN (610101,610102,900201,900202)\n" + 
            	 		"      GROUP BY B.Accounting_Unit_Id,\n" + 
            	 		"        B.Cashbook_Month,\n" + 
            	 		"        B.Account_Head_Code,\n" + 
            	 		"        B.CR_DR_TYPE\n" + 
            	 		"      ORDER BY B.Accounting_Unit_Id,\n" + 
            	 		"        B.Cashbook_Month,\n" + 
            	 		"        b.Account_Head_Code\n" + 
            	 		"      )as ps\n" + 
            	 		"    GROUP BY Accounting_Unit_Id,\n" + 
            	 		"      Cashbook_Month,\n" + 
            	 		"      Account_Head_Code\n" + 
            	 		"    ORDER BY Accounting_Unit_Id,\n" + 
            	 		"      Cashbook_Month,\n" + 
            	 		"      Account_Head_Code\n" + 
            	 		"    )Bbb\n" + 
            	 		"  ON aaa.Accounting_Unit_Id=bbb.Accounting_Unit_Id\n" + 
            	 		"  AND Aaa.Cashbook_Month   =Bbb.Cashbook_Month\n" + 
            	 		"  AND AAA.ACCOUNT_HEAD_CODE=BBB.ACCOUNT_HEAD_CODE\n" + 
            	 		"  )as ps";
	              }
	              else
	              {
	            	  que="SELECT Unit_Id,\n" + 
	            	  		"  (SELECT U.Accounting_Unit_Name\n" + 
	            	  		"  FROM Fas_Mst_Acct_Units u\n" + 
	            	  		"  WHERE U.Accounting_Unit_Id=unit_id\n" + 
	            	  		"  )AS unit_name,\n" + 
	            	  		"  Cashbook_Month,\n" + 
	            	  		"  CASE\n" + 
	            	  		"    WHEN Cashbook_Month=1\n" + 
	            	  		"    THEN 'January'\n" + 
	            	  		"    WHEN Cashbook_Month=2\n" + 
	            	  		"    THEN 'February'\n" + 
	            	  		"    WHEN Cashbook_Month=3\n" + 
	            	  		"    THEN 'March'\n" + 
	            	  		"    WHEN Cashbook_Month=4\n" + 
	            	  		"    THEN 'April'\n" + 
	            	  		"    WHEN Cashbook_Month=5\n" + 
	            	  		"    THEN 'May'\n" + 
	            	  		"    WHEN Cashbook_Month=6\n" + 
	            	  		"    THEN 'June'\n" + 
	            	  		"    WHEN Cashbook_Month=7\n" + 
	            	  		"    THEN 'July'\n" + 
	            	  		"    WHEN Cashbook_Month=8\n" + 
	            	  		"    THEN 'August'\n" + 
	            	  		"    WHEN Cashbook_Month=9\n" + 
	            	  		"    THEN 'September'\n" + 
	            	  		"    WHEN Cashbook_Month=10\n" + 
	            	  		"    THEN 'October'\n" + 
	            	  		"    WHEN Cashbook_Month=11\n" + 
	            	  		"    THEN 'November'\n" + 
	            	  		"    WHEN Cashbook_Month=12\n" + 
	            	  		"    THEN 'December'\n" + 
	            	  		"  END AS month_desc,\n" + 
	            	  		"  ACCOUNT_HEAD_CODE,\n" + 
	            	  		"  TRN_DR,\n" + 
	            	  		"  TRN_CR,\n" + 
	            	  		"  coalesce (ADJMEMO_DR,NULL,0,ADJMEMO_DR) AS AdjMemo_Dr,\n" + 
	            	  		"  coalesce (AdjMemo_Cr,NULL,0,AdjMemo_Cr) AS AdjMemo_Cr,\n" + 
	            	  		"  Trn_Net,\n" + 
	            	  		"  AdjMemo_Net,\n" + 
	            	  		"  (Trn_Net-AdjMemo_net)AS difference\n" + 
	            	  		"FROM\n" + 
	            	  		"  (SELECT aaa.Accounting_Unit_Id AS unit_id,\n" + 
	            	  		"    Aaa.Cashbook_Month,\n" + 
	            	  		"    Aaa.Account_Head_Code,\n" + 
	            	  		"    Aaa.Trn_Dr,\n" + 
	            	  		"    Aaa.Trn_Cr,\n" + 
	            	  		"    Bbb.AdjMemo_Dr,\n" + 
	            	  		"    Bbb.AdjMemo_Cr,\n" + 
	            	  		"    CASE\n" + 
	            	  		"      WHEN Aaa.Trn_Dr>Aaa.Trn_Cr\n" + 
	            	  		"      THEN (Aaa.Trn_Dr-Aaa.Trn_Cr)\n" + 
	            	  		"      WHEN Aaa.Trn_Cr>Aaa.Trn_Dr\n" + 
	            	  		"      THEN (Aaa.Trn_Cr-Aaa.Trn_Dr)\n" + 
	            	  		"      ELSE 0\n" + 
	            	  		"    END AS Trn_Net,\n" + 
	            	  		"    CASE\n" + 
	            	  		"      WHEN Bbb.AdjMemo_Dr>Bbb.AdjMemo_Cr\n" + 
	            	  		"      THEN (Bbb.AdjMemo_Dr-Bbb.AdjMemo_Cr)\n" + 
	            	  		"      WHEN Bbb.AdjMemo_Cr>Bbb.AdjMemo_Dr\n" + 
	            	  		"      THEN (Bbb.AdjMemo_Cr-Bbb.AdjMemo_Dr)\n" + 
	            	  		"      ELSE 0\n" + 
	            	  		"    END AS AdjMemo_Net\n" + 
	            	  		"  FROM\n" + 
	            	  		"    (SELECT Accounting_Unit_Id,\n" + 
	            	  		"      Cashbook_Month,\n" + 
	            	  		"      Account_Head_Code,\n" + 
	            	  		"      SUM(dr_Amount)AS trn_dr,\n" + 
	            	  		"      SUM(cr_amount)AS trn_cr\n" + 
	            	  		"    FROM\n" + 
	            	  		"      (SELECT Accounting_Unit_Id,\n" + 
	            	  		"        Cashbook_Month,\n" + 
	            	  		"        Account_Head_Code,\n" + 
	            	  		"        SUM(dr_Amount)AS dr_Amount,\n" + 
	            	  		"        SUM(cr_amount)AS cr_amount\n" + 
	            	  		"      FROM\n" + 
	            	  		"        (SELECT B.Cashbook_Month,\n" + 
	            	  		"          B.Account_Head_Code,\n" + 
	            	  		"          B.Accounting_Unit_Id,\n" + 
	            	  		"          CASE\n" + 
	            	  		"            WHEN B.Cr_Dr_Indicator='DR'\n" + 
	            	  		"            THEN SUM(B.Amount)\n" + 
	            	  		"            ELSE 0\n" + 
	            	  		"          END AS dr_Amount,\n" + 
	            	  		"          CASE\n" + 
	            	  		"            WHEN b.Cr_Dr_Indicator='CR'\n" + 
	            	  		"            THEN SUM(B.Amount)\n" + 
	            	  		"            ELSE 0\n" + 
	            	  		"          END AS cr_amount\n" + 
	            	  		"        FROM fas_journal_master a,\n" + 
	            	  		"          Fas_Journal_Transaction b\n" + 
	            	  		"        WHERE A.Accounting_Unit_Id    =b.Accounting_Unit_Id\n" + 
	            	  		"        AND A.Accounting_For_Office_Id=B.Accounting_For_Office_Id\n" + 
	            	  		"        AND A.Cashbook_Year           =B.Cashbook_Year\n" + 
	            	  		"        AND A.Cashbook_Month          =B.Cashbook_Month\n" + 
	            	  		"        AND A.Voucher_No              =B.Voucher_No\n" + 
	            	  		"        AND A.JOURNAL_STATUS          ='L'\n" + 
	            	  		"        AND b.Accounting_Unit_Id      =" +cmbAcc_UnitCode +
	            	  		"        AND (a.SUPPLEMENT_NO         IS NULL\n" + 
	            	  		"        OR a.SUPPLEMENT_NO            =0)\n" + 
	            	  		"        AND B.CASHBOOK_YEAR           =" +txtCB_Year+ 
	            	  		"        AND B.CASHBOOK_MONTH          =" +txtCB_Month+  
	            	  		"        AND B.Account_Head_Code      IN (610102,900202)\n" + 
	            	  		"        GROUP BY B.Accounting_Unit_Id,\n" + 
	            	  		"          B.Cashbook_Month,\n" + 
	            	  		"          B.Account_Head_Code,\n" + 
	            	  		"          B.Cr_Dr_Indicator\n" + 
	            	  		"        )as ps\n" + 
	            	  		"      GROUP BY Accounting_Unit_Id,\n" + 
	            	  		"        Cashbook_Month,\n" + 
	            	  		"        Account_Head_Code\n" + 
	            	  		"      UNION ALL\n" + 
	            	  		"      SELECT B.Accounting_Unit_Id,\n" + 
	            	  		"        B.Cashbook_Month,\n" + 
	            	  		"        B.Account_Head_Code,\n" + 
	            	  		"        CASE\n" + 
	            	  		"          WHEN B.Cr_Dr_Indicator='DR'\n" + 
	            	  		"          THEN SUM(B.Amount)\n" + 
	            	  		"          ELSE 0\n" + 
	            	  		"        END AS dr_Amount,\n" + 
	            	  		"        CASE\n" + 
	            	  		"          WHEN b.Cr_Dr_Indicator='CR'\n" + 
	            	  		"          THEN SUM(B.Amount)\n" + 
	            	  		"          ELSE 0\n" + 
	            	  		"        END AS cr_amount\n" + 
	            	  		"      FROM FAS_PAYMENT_MASTER a,\n" + 
	            	  		"        FAS_PAYMENT_TRANSACTION b\n" + 
	            	  		"      WHERE A.Accounting_Unit_Id    =b.Accounting_Unit_Id\n" + 
	            	  		"      AND A.Accounting_For_Office_Id=B.Accounting_For_Office_Id\n" + 
	            	  		"      AND A.Cashbook_Year           =B.Cashbook_Year\n" + 
	            	  		"      AND A.Cashbook_Month          =B.Cashbook_Month\n" + 
	            	  		"      AND A.VOUCHER_NO              =B.VOUCHER_NO\n" + 
	            	  		"      AND B.Accounting_Unit_Id      =" +cmbAcc_UnitCode +
	            	  		"      AND A.Payment_Status          ='L'\n" + 
	            	  		"      AND B.Cashbook_Year           =" +txtCB_Year+ 
	            	  		"      AND B.CASHBOOK_MONTH          =" +txtCB_Month+  
	            	  		"      AND B.Account_Head_Code      IN (610102,900202)\n" + 
	            	  		"      GROUP BY B.Accounting_Unit_Id,\n" + 
	            	  		"        B.Cashbook_Month,\n" + 
	            	  		"        B.Account_Head_Code,\n" + 
	            	  		"        B.Cr_Dr_Indicator\n" + 
	            	  		"      UNION ALL\n" + 
	            	  		"      SELECT B.Accounting_Unit_Id,\n" + 
	            	  		"        B.Cashbook_Month,\n" + 
	            	  		"        B.Account_Head_Code,\n" + 
	            	  		"        CASE\n" + 
	            	  		"          WHEN B.Cr_Dr_Indicator='DR'\n" + 
	            	  		"          THEN SUM(B.Amount)\n" + 
	            	  		"          ELSE 0\n" + 
	            	  		"        END AS dr_Amount,\n" + 
	            	  		"        CASE\n" + 
	            	  		"          WHEN b.Cr_Dr_Indicator='CR'\n" + 
	            	  		"          THEN SUM(B.Amount)\n" + 
	            	  		"          ELSE 0\n" + 
	            	  		"        END AS cr_amount\n" + 
	            	  		"      FROM FAS_RECEIPT_MASTER a,\n" + 
	            	  		"        FAS_RECEIPT_TRANSACTION b\n" + 
	            	  		"      WHERE A.Accounting_Unit_Id    =b.Accounting_Unit_Id\n" + 
	            	  		"      AND A.Accounting_For_Office_Id=B.Accounting_For_Office_Id\n" + 
	            	  		"      AND A.Cashbook_Year           =B.Cashbook_Year\n" + 
	            	  		"      AND A.Cashbook_Month          =B.Cashbook_Month\n" + 
	            	  		"      AND A.RECEIPT_NO              =B.RECEIPT_NO\n" + 
	            	  		"      AND A.RECEIPT_STATUS          ='L'\n" + 
	            	  		"      AND B.Accounting_Unit_Id      =" +cmbAcc_UnitCode +
	            	  		"      AND B.CASHBOOK_YEAR           =" +txtCB_Year+ 
	            	  		"      AND B.CASHBOOK_MONTH          =" +txtCB_Month+  
	            	  		"      AND B.Account_Head_Code      IN (610102,900202)\n" + 
	            	  		"      GROUP BY B.Accounting_Unit_Id,\n" + 
	            	  		"        B.Cashbook_Month,\n" + 
	            	  		"        B.Account_Head_Code,\n" + 
	            	  		"        B.Cr_Dr_Indicator\n" + 
	            	  		"      )as ps\n" + 
	            	  		"    GROUP BY Accounting_Unit_Id,\n" + 
	            	  		"      Cashbook_Month,\n" + 
	            	  		"      Account_Head_Code\n" + 
	            	  		"    ORDER BY Accounting_Unit_Id,\n" + 
	            	  		"      Cashbook_Month,\n" + 
	            	  		"      Account_Head_Code\n" + 
	            	  		"    )aaa\n" + 
	            	  		"  FULL OUTER JOIN\n" + 
	            	  		"    (SELECT FOR_ACCOUNTING_UNIT_ID,\n" + 
	            	  		"      CASHBOOK_MONTH,\n" + 
	            	  		"      Account_Head_Code,\n" + 
	            	  		"      SUM(dr_Amount)AS AdjMemo_dr,\n" + 
	            	  		"      SUM(cr_amount)AS AdjMemo_cr\n" + 
	            	  		"    FROM\n" + 
	            	  		"      (SELECT B.FOR_ACCOUNTING_UNIT_ID,\n" + 
	            	  		"        TO_CHAR( B.ACCEPT_VOUCHER_DATE, 'mm' ) AS Cashbook_Month,\n" + 
	            	  		"       '610102' as Account_Head_Code,\n" + 
	            	  		"        CASE\n" + 
	            	  		"          WHEN B.CR_DR_TYPE='DR'\n" + 
	            	  		"          THEN SUM(B.Amount)\n" + 
	            	  		"          ELSE 0\n" + 
	            	  		"        END AS dr_Amount,\n" + 
	            	  		"        CASE\n" + 
	            	  		"          WHEN b.CR_DR_TYPE='CR'\n" + 
	            	  		"          THEN SUM(B.Amount)\n" + 
	            	  		"          ELSE 0\n" + 
	            	  		"        END AS cr_amount\n" + 
	            	  		"      FROM FAS_ADJUST_MEMO_MST A,\n" + 
	            	  		"        FAS_ADJUST_MEMO_TRN B\n" + 
	            	  		"      WHERE A.Accounting_Unit_Id    =b.Accounting_Unit_Id\n" + 
	            	  		"      AND A.Accounting_For_Office_Id=B.Accounting_For_Office_Id\n" + 
	            	  		"      AND A.Cashbook_Year           =B.Cashbook_Year\n" + 
	            	  		"      AND A.Cashbook_Month          =B.Cashbook_Month\n" + 
	            	  		"      AND A.VOUCHER_NO              =B.VOUCHER_NO\n" + 
	            	  		"      AND A.MEMO_STATUS             ='L'\n" + 
	            	  		"      and B.FOR_ACCOUNTING_UNIT_ID  =" +cmbAcc_UnitCode +
//	            	  		"      AND B.CASHBOOK_YEAR           =" +txtCB_Year+ 
//	            	  		"      AND B.CASHBOOK_MONTH          =" +txtCB_Month+  
	            	  		"      AND EXTRACT(YEAR FROM B.ACCEPT_VOUCHER_DATE) = " +txtCB_Year+
	            	  		"      AND EXTRACT(MONTH FROM B.ACCEPT_VOUCHER_DATE) = " +txtCB_Month+  
	            	  		"      AND B.ACCEPTANCE_STATUS       ='Y'\n" + 
	            	  		"      AND B.ACCOUNT_HEAD_CODE      IN (610101,610102)\n" + 
	            	  		"      GROUP BY B.FOR_ACCOUNTING_UNIT_ID,\n" + 
	            	  		"      TO_CHAR( B.ACCEPT_VOUCHER_DATE, 'mm' ),\n" + 
	            	  		"      B.ACCOUNT_HEAD_CODE,\n" + 
	            	  		"      B.CR_DR_TYPE " + 
	            	  		"      Union all "   +
	            	  		"       SELECT B.FOR_ACCOUNTING_UNIT_ID, \n" + 
	            	  		"	            	  		        TO_CHAR( B.ACCEPT_VOUCHER_DATE, 'mm' ) AS Cashbook_Month, \n" + 
	            	  		"	            	  		        '900202' as Account_Head_Code, \n" + 
	            	  		"	            	  		        CASE  \n" + 
	            	  		"	            	  		          WHEN B.CR_DR_TYPE='DR' \n" + 
	            	  		"	            	  		          THEN SUM(B.Amount) \n" + 
	            	  		"	            	  		          ELSE 0 \n" + 
	            	  		"	            	  		        END AS dr_Amount,  \n" + 
	            	  		"	            	  		        CASE  \n" + 
	            	  		"	            	  		          WHEN b.CR_DR_TYPE='CR'  \n" + 
	            	  		"	            	  		          THEN SUM(B.Amount)  \n" + 
	            	  		"	            	  		          ELSE 0  \n" + 
	            	  		"	            	  		        END AS cr_amount  \n" + 
	            	  		"	            	  		      FROM FAS_ADJUST_MEMO_MST A, \n" + 
	            	  		"	            	  		        FAS_ADJUST_MEMO_TRN B  \n" + 
	            	  		"	            	  		      WHERE A.Accounting_Unit_Id    =b.Accounting_Unit_Id  \n" + 
	            	  		"	            	  		      AND A.Accounting_For_Office_Id=B.Accounting_For_Office_Id  \n" + 
	            	  		"	            	  		      AND A.Cashbook_Year           =B.Cashbook_Year  \n" + 
	            	  		"	            	  		      AND A.Cashbook_Month          =B.Cashbook_Month  \n" + 
	            	  		"	            	  		      AND A.VOUCHER_NO              =B.VOUCHER_NO  \n" + 
	            	  		"	            	  		      AND A.MEMO_STATUS             ='L'  \n" + 
	            	  		"	            	  		      and B.FOR_ACCOUNTING_UNIT_ID  = " +cmbAcc_UnitCode +	            	  		
	            	  		"	            	  		      AND EXTRACT(YEAR FROM B.ACCEPT_VOUCHER_DATE) ="  +txtCB_Year+
	            	  		"	            	  		      AND EXTRACT(MONTH FROM B.ACCEPT_VOUCHER_DATE) = " +txtCB_Month+ 
	            	  		"	            	  		      and b.acceptance_status       ='Y'  \n" + 
	            	  		"	            	  		      AND B.ACCOUNT_HEAD_CODE      IN (900201,900202) \n" + 
	            	  		"	            	  		      GROUP BY B.FOR_ACCOUNTING_UNIT_ID,  \n" + 
	            	  		"	            	  		      TO_CHAR( B.ACCEPT_VOUCHER_DATE, 'mm' ),  \n" + 
	            	  		"	            	  		      B.ACCOUNT_HEAD_CODE, \n" + 
	            	  		"	            	  		      B.CR_DR_TYPE  \n" + 
	            	  		"      )as ps\n" + 
	            	  		"    GROUP BY FOR_ACCOUNTING_UNIT_ID,\n" + 
	            	  		"      Cashbook_Month,\n" + 
	            	  		"      Account_Head_Code\n" + 
	            	  		"    ORDER BY FOR_ACCOUNTING_UNIT_ID,\n" + 
	            	  		"      Cashbook_Month,\n" + 
	            	  		"      Account_Head_Code\n" + 
	            	  		"    )Bbb\n" + 
	            	  		"  ON aaa.Accounting_Unit_Id=bbb.FOR_ACCOUNTING_UNIT_ID\n" + 
	            	  		"  AND Aaa.Cashbook_Month   =Bbb.Cashbook_Month::numeric \n" + 
	            	  		"  AND AAA.ACCOUNT_HEAD_CODE=BBB.ACCOUNT_HEAD_CODE::numeric \n" + 
	            	  		"  )as ps";
	              }
	              
            	 
             System.out.println("que:::"+que);
                             ps = con.prepareStatement(que);
                             result = ps.executeQuery();                                
                             while(result.next()) 
                             {
                                 xml=xml+"<month>"+result.getInt("Cashbook_Month")+"</month>";
                                 xml=xml+"<achead>"+result.getInt("Account_Head_Code")+"</achead>";
                                 xml=xml+"<trndr>"+result.getString("Trn_Dr")+"</trndr>";
                                 xml=xml+"<trncr>"+result.getString("Trn_Cr")+"</trncr>";
                                 
                                 xml=xml+"<AdjMemo_Dr>"+result.getString("AdjMemo_Dr")+"</AdjMemo_Dr>";
                                 xml=xml+"<AdjMemo_Cr>"+result.getString("AdjMemo_Cr")+"</AdjMemo_Cr>";
                                 xml=xml+"<Trn_Net>"+result.getString("Trn_Net")+"</Trn_Net>";
                                 xml=xml+"<AdjMemo_Net>"+result.getString("AdjMemo_Net")+"</AdjMemo_Net>";
                                 
                                 xml=xml+"<difference>"+result.getString("difference")+"</difference>";
                               
                                 count++;
                             }
                             if(count>0)
                             {
                                 xml=xml+"<flag>success</flag>";
                                 try
                                 {
                                	pss2=con.prepareStatement("select VERIFY_FLAG from Fas_AdjMemo_Monthend " +
                                			" where ACCOUNTING_UNIT_ID=? and Cashbook_Year=? and CASHBOOK_MONTH=?::varchar");
                                	pss2.setInt(1,cmbAcc_UnitCode);
                              	    pss2.setInt(2,txtCB_Year);
                              	    pss2.setInt(3,txtCB_Month);
                              	    rss2=pss2.executeQuery();
                              	    while(rss2.next())
                              	    {
                              	    verified_already++;	
                              	    }
                              	  xml=xml+"<verified_already>"+verified_already+"</verified_already>";
                                 }
                                 catch(Exception ee)
                                 {
                                	 
                                 }
                             }
                             else
                                 xml=xml+"<flag>NoRecord</flag>";
                     }
               catch(Exception e) 
                     {
                             System.out.println("Exception in advno ===> "+e);   
                             xml=xml+"<flag>failure</flag>";  
                     }
                 xml=xml+"</response>";
         }  
         System.out.println("xml ::"+xml);
         out.println(xml);
         out.close();
		
	}

	
	
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
