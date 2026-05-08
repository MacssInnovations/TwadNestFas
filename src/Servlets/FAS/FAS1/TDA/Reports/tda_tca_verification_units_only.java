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
 * Servlet implementation class tda_tca_verification_units_only
 */
public class tda_tca_verification_units_only extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public tda_tca_verification_units_only() {
        super();
        
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
             	
	              
                  String que="SELECT Unit_Id,\n" + 
                  "    (SELECT U.Accounting_Unit_Name\n" + 
                  "    FROM Fas_Mst_Acct_Units u\n" + 
                  "    WHERE U.Accounting_Unit_Id=unit_id\n" + 
                  "    )AS unit_name,\n" + 
                  "    Cashbook_Month,\n" + 
                  "    CASE\n" + 
                  "      WHEN Cashbook_Month=1\n" + 
                  "      THEN 'January'\n" + 
                  "      WHEN Cashbook_Month=2\n" + 
                  "      THEN 'February'\n" + 
                  "      WHEN Cashbook_Month=3\n" + 
                  "      THEN 'March'\n" + 
                  "      WHEN Cashbook_Month=4\n" + 
                  "      THEN 'April'\n" + 
                  "      WHEN Cashbook_Month=5\n" + 
                  "      THEN 'May'\n" + 
                  "      WHEN Cashbook_Month=6\n" + 
                  "      THEN 'June'\n" + 
                  "      WHEN Cashbook_Month=7\n" + 
                  "      THEN 'July'\n" + 
                  "      WHEN Cashbook_Month=8\n" + 
                  "      THEN 'August'\n" + 
                  "      WHEN Cashbook_Month=9\n" + 
                  "      THEN 'September'\n" + 
                  "      WHEN Cashbook_Month=10\n" + 
                  "      THEN 'October'\n" + 
                  "      WHEN Cashbook_Month=11\n" + 
                  "      THEN 'November'\n" + 
                  "      WHEN Cashbook_Month=12\n" + 
                  "      THEN 'December'\n" + 
                  "    END AS month_desc,\n" + 
                  "    Account_Head_Code,\n" + 
                  "    Trn_Dr,\n" + 
                  "    Trn_Cr,\n" + 
                  "    Tda_Dr,\n" + 
                  "    Tda_Cr,\n" + 
                  "    Trn_Net,\n" + 
                  "    Tda_Net,\n" + 
                  "    (Trn_Net-tda_net)AS difference\n" + 
                  "  FROM\n" + 
                  "    (SELECT aaa.Accounting_Unit_Id AS unit_id,\n" + 
                  "      Aaa.Cashbook_Month,\n" + 
                  "      Aaa.Account_Head_Code,\n" + 
                  "      Aaa.Trn_Dr,\n" + 
                  "      Aaa.Trn_Cr,\n" + 
                  "      Bbb.Tda_Dr,\n" + 
                  "      Bbb.Tda_Cr,\n" + 
                  "      CASE\n" + 
                  "        WHEN Aaa.Trn_Dr>Aaa.Trn_Cr\n" + 
                  "        THEN (Aaa.Trn_Dr-Aaa.Trn_Cr)\n" + 
                  "        WHEN Aaa.Trn_Cr>Aaa.Trn_Dr\n" + 
                  "        THEN (Aaa.Trn_Cr-Aaa.Trn_Dr)\n" + 
                  "        ELSE 0\n" + 
                  "      END AS Trn_Net,\n" + 
                  "      CASE\n" + 
                  "        WHEN Bbb.Tda_Dr>Bbb.Tda_Cr\n" + 
                  "        THEN (Bbb.Tda_Dr-Bbb.Tda_Cr)\n" + 
                  "        WHEN Bbb.Tda_Cr>Bbb.Tda_Dr\n" + 
                  "        THEN (Bbb.Tda_Cr-Bbb.Tda_Dr)\n" + 
                  "        ELSE 0\n" + 
                  "      END AS Tda_Net\n" + 
                  "    FROM\n" + 
                  "      (SELECT Accounting_Unit_Id,\n" + 
                  "        Cashbook_Month,\n" + 
                  "        Account_Head_Code,\n" + 
                  "        SUM(dr_Amount)AS trn_dr,\n" + 
                  "        SUM(cr_amount)AS trn_cr\n" + 
                  "      FROM\n" + 
                  "        (SELECT Accounting_Unit_Id,\n" + 
                  "          Cashbook_Month,\n" + 
                  "          Account_Head_Code,\n" + 
                  "          SUM(dr_Amount)AS dr_Amount,\n" + 
                  "          SUM(cr_amount)AS cr_amount\n" + 
                  "        FROM\n" + 
                  "          (SELECT B.Cashbook_Month,\n" + 
                  "            B.Account_Head_Code,\n" + 
                  "            B.Accounting_Unit_Id,\n" + 
                  "            CASE\n" + 
                  "              WHEN B.Cr_Dr_Indicator='DR'\n" + 
                  "              THEN SUM(B.Amount)\n" + 
                  "              ELSE 0\n" + 
                  "            END AS dr_Amount,\n" + 
                  "            CASE\n" + 
                  "              WHEN b.Cr_Dr_Indicator='CR'\n" + 
                  "              THEN SUM(B.Amount)\n" + 
                  "              ELSE 0\n" + 
                  "            END AS cr_amount\n" + 
                  "          FROM fas_journal_master a,\n" + 
                  "            Fas_Journal_Transaction b\n" + 
                  "          WHERE A.Accounting_Unit_Id    =b.Accounting_Unit_Id\n" + 
                  "          AND A.Accounting_For_Office_Id=B.Accounting_For_Office_Id\n" + 
                  "          AND A.Cashbook_Year           =B.Cashbook_Year\n" + 
                  "          AND A.Cashbook_Month          =B.Cashbook_Month\n" + 
                  "          AND A.Voucher_No              =B.Voucher_No\n" + 
                  "          And A.Journal_Status          ='L'\n" + 
                  "          and A.Accounting_Unit_Id=" +cmbAcc_UnitCode+ 
                  "          AND A.Journal_Type_Code      IN (62,63,65,66)\n" + 
                  "          AND A.Mode_Of_Creation        ='A' and a.created_by_module='GJV' and (a.SUPPLEMENT_NO is null or a.SUPPLEMENT_NO=0) \n" + 
                  "          And B.Cashbook_Year           = " +txtCB_Year+ 
                  "          and B.Cashbook_Month=" +txtCB_Month+ 
                  "          AND B.Account_Head_Code      IN (900108,900109,901001,901002)\n" + 
                  "          GROUP BY B.Accounting_Unit_Id,\n" + 
                  "            B.Cashbook_Month,\n" + 
                  "            B.Account_Head_Code,\n" + 
                  "            B.Cr_Dr_Indicator\n" + 
                  "          UNION ALL\n" + 
                  "          SELECT B.Cashbook_Month,\n" + 
                  "            B.Account_Head_Code,\n" + 
                  "            B.Accounting_Unit_Id,\n" + 
                  "            CASE\n" + 
                  "              WHEN B.Cr_Dr_Indicator='DR'\n" + 
                  "              THEN SUM(B.Amount)\n" + 
                  "              ELSE 0\n" + 
                  "            END AS dr_Amount,\n" + 
                  "            CASE\n" + 
                  "              WHEN b.Cr_Dr_Indicator='CR'\n" + 
                  "              THEN SUM(B.Amount)\n" + 
                  "              ELSE 0\n" + 
                  "            END AS cr_amount\n" + 
                  "          FROM fas_journal_master a,\n" + 
                  "            Fas_Journal_Transaction b\n" + 
                  "          WHERE A.Accounting_Unit_Id    =b.Accounting_Unit_Id\n" + 
                  "          AND A.Accounting_For_Office_Id=B.Accounting_For_Office_Id\n" + 
                  "          AND A.Cashbook_Year           =B.Cashbook_Year\n" + 
                  "          AND A.Cashbook_Month          =B.Cashbook_Month\n" + 
                  "          AND A.Voucher_No              =B.Voucher_No\n" + 
                  "          And A.Journal_Status          ='L'\n" + 
                  "          and A.Accounting_Unit_Id=" +cmbAcc_UnitCode+ 
                  "          AND A.Journal_Type_Code      IN (54) and (a.SUPPLEMENT_NO is null or a.SUPPLEMENT_NO=0)\n" + 
                  "          And B.Cashbook_Year           = " +txtCB_Year+
                  "          and B.Cashbook_Month=" +txtCB_Month+
                  "          AND B.Account_Head_Code      IN (900108,900109,901001,901002)\n" + 
                  "          GROUP BY B.Accounting_Unit_Id,\n" + 
                  "            B.Cashbook_Month,\n" + 
                  "            B.Account_Head_Code,\n" + 
                  "            B.Cr_Dr_Indicator\n" + 
                  "          )as ps\n" + 
                  "        GROUP BY Accounting_Unit_Id,\n" + 
                  "          Cashbook_Month,\n" + 
                  "          Account_Head_Code\n" + 
                  "        UNION ALL\n" + 
                  "        SELECT B.Accounting_Unit_Id,\n" + 
                  "          B.Cashbook_Month,\n" + 
                  "          B.Account_Head_Code,\n" + 
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
                  "        FROM FAS_PAYMENT_MASTER a,\n" + 
                  "          FAS_PAYMENT_TRANSACTION b\n" + 
                  "        WHERE A.Accounting_Unit_Id    =b.Accounting_Unit_Id\n" + 
                  "        AND A.Accounting_For_Office_Id=B.Accounting_For_Office_Id\n" + 
                  "        AND A.Cashbook_Year           =B.Cashbook_Year\n" + 
                  "        AND A.Cashbook_Month          =B.Cashbook_Month\n" + 
                  "        And A.Voucher_No              =B.Voucher_No\n" + 
                  "        and A.Accounting_Unit_Id=" +cmbAcc_UnitCode+ 
                  "        AND A.Payment_Status          ='L'\n" + 
                  "        And B.Cashbook_Year           = " +txtCB_Year+
                  "        and B.Cashbook_Month=" +txtCB_Month+
                  "        AND B.Account_Head_Code      IN (900108,900109,901001,901002)\n" + 
                  "        GROUP BY B.Accounting_Unit_Id,\n" + 
                  "          B.Cashbook_Month,\n" + 
                  "          B.Account_Head_Code,\n" + 
                  "          B.Cr_Dr_Indicator\n" + 
                  "        UNION ALL\n" + 
                  "        SELECT B.Accounting_Unit_Id,\n" + 
                  "          B.Cashbook_Month,\n" + 
                  "          B.Account_Head_Code,\n" + 
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
                  "        FROM FAS_RECEIPT_MASTER a,\n" + 
                  "          FAS_RECEIPT_TRANSACTION b\n" + 
                  "        WHERE A.Accounting_Unit_Id    =b.Accounting_Unit_Id\n" + 
                  "        AND A.Accounting_For_Office_Id=B.Accounting_For_Office_Id\n" + 
                  "        AND A.Cashbook_Year           =B.Cashbook_Year\n" + 
                  "        AND A.Cashbook_Month          =B.Cashbook_Month\n" + 
                  "        AND A.RECEIPT_NO              =B.RECEIPT_NO\n" + 
                  "        And A.Receipt_Status          ='L'\n" + 
                  "        and A.Accounting_Unit_Id=" +cmbAcc_UnitCode+ 
                  "        And B.Cashbook_Year           = " +txtCB_Year+
                  "        and B.Cashbook_Month=" +txtCB_Month+ 
                  "        AND B.Account_Head_Code      IN (900108,900109,901001,901002)\n" + 
                  "        GROUP BY B.Accounting_Unit_Id,\n" + 
                  "          B.Cashbook_Month,\n" + 
                  "          B.Account_Head_Code,\n" + 
                  "          B.Cr_Dr_Indicator\n" + 
                  "        )as ps\n" + 
                  "      GROUP BY Accounting_Unit_Id,\n" + 
                  "        Cashbook_Month,\n" + 
                  "        Account_Head_Code\n" + 
                  "      ORDER BY Accounting_Unit_Id,\n" + 
                  "        Cashbook_Month,\n" + 
                  "        Account_Head_Code\n" + 
                  "      )aaa\n" + 
                  "    FULL OUTER JOIN\n" + 
                  "      (SELECT Accounting_Unit_Id,\n" + 
                  "        Cashbook_Month,\n" + 
                  "        Account_Head_Code,\n" + 
                  "        SUM(dr_Amount)AS tda_dr,\n" + 
                  "        SUM(cr_amount)AS tda_cr\n" + 
                  "      FROM\n" + 
                  "        (SELECT B.Accounting_Unit_Id,\n" + 
                  "          B.Cashbook_Month,\n" + 
                  "          B.Account_Head_Code,\n" + 
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
                  "        FROM Fas_Tda_Tca_Raised_Mst A,\n" + 
                  "          Fas_Tda_Tca_Raised_Trn B\n" + 
                  "        WHERE A.Accounting_Unit_Id    =b.Accounting_Unit_Id\n" + 
                  "        AND A.Accounting_For_Office_Id=B.Accounting_For_Office_Id\n" + 
                  "        AND A.Cashbook_Year           =B.Cashbook_Year\n" + 
                  "        AND A.Cashbook_Month          =B.Cashbook_Month\n" + 
                  "        AND A.Voucher_No              =B.Voucher_No\n" + 
                  "        And A.Status                  ='L'\n" + 
                  "        and A.Accounting_Unit_Id=" +cmbAcc_UnitCode+ 
                  "        And B.Cashbook_Year           = " +txtCB_Year+
                  "        and B.Cashbook_Month=" +txtCB_Month+
                  "        AND B.Account_Head_Code      IN (900108,900109,901001,901002)and (a.SUPPLEMENT_NO is null or a.SUPPLEMENT_NO=0) \n" + 
                  "        GROUP BY B.Accounting_Unit_Id,\n" + 
                  "          B.Cashbook_Month,\n" + 
                  "          B.Account_Head_Code,\n" + 
                  "          B.Cr_Dr_Indicator\n" + 
                  "        ORDER BY B.Accounting_Unit_Id,\n" + 
                  "          B.Cashbook_Month,\n" + 
                  "          b.Account_Head_Code\n" + 
                  "        )as ps \n" + 
                  "      GROUP BY Accounting_Unit_Id,\n" + 
                  "        Cashbook_Month,\n" + 
                  "        Account_Head_Code\n" + 
                  "      ORDER BY Accounting_Unit_Id,\n" + 
                  "        Cashbook_Month,\n" + 
                  "        Account_Head_Code\n" + 
                  "      )Bbb\n" + 
                  "    ON aaa.Accounting_Unit_Id=bbb.Accounting_Unit_Id\n" + 
                  "    AND Aaa.Cashbook_Month   =Bbb.Cashbook_Month\n" + 
                  "    AND Aaa.Account_Head_Code=Bbb.Account_Head_Code\n" + 
                  "    )as ps\n";
                 
              System.out.println("que:::"+que);
                              ps = con.prepareStatement(que);
                              result = ps.executeQuery();                                
                              while(result.next()) 
                              {
                                  xml=xml+"<month>"+result.getInt("Cashbook_Month")+"</month>";
                                  xml=xml+"<achead>"+result.getInt("Account_Head_Code")+"</achead>";
                                  xml=xml+"<trndr>"+result.getString("Trn_Dr")+"</trndr>";
                                  xml=xml+"<trncr>"+result.getString("Trn_Cr")+"</trncr>";
                                  
                                  xml=xml+"<Tda_Dr>"+result.getString("Tda_Dr")+"</Tda_Dr>";
                                  xml=xml+"<Tda_Cr>"+result.getString("Tda_Cr")+"</Tda_Cr>";
                                  xml=xml+"<Trn_Net>"+result.getString("Trn_Net")+"</Trn_Net>";
                                  xml=xml+"<Tda_Net>"+result.getString("Tda_Net")+"</Tda_Net>";
                                  
                                  xml=xml+"<difference>"+result.getString("difference")+"</difference>";
                                
                                  count++;
                              }
                              if(count>0)
                              {
                                  xml=xml+"<flag>success</flag>";
                                  try
                                  {
                                 	pss2=con.prepareStatement("select VERIFY_FLAG from FAS_TDA_TCA_MONTHEND " +
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
          else  if(cmd.equalsIgnoreCase("verifycheque_cr_dr"))
             {
         	 try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
              catch(NumberFormatException e){System.out.println("exception"+e );}
         	 try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
             catch(NumberFormatException e){System.out.println("exception"+e );}
           //   System.out.println("cmbOffice_code "+cmbOffice_code);
              try{txtCB_Year=Integer.parseInt(request.getParameter("txtCB_Year"));}
              catch(NumberFormatException e){System.out.println("exception"+e );}
              try{txtCB_Month=Integer.parseInt(request.getParameter("txtCB_Month"));}
              catch(NumberFormatException e){System.out.println("exception in txtCB_Month:"+e );}
                xml="<response><command>verifycheque_cr_dr</command>"; 
                 try 
                         {
                	
   	              
                     String que="SELECT Yy.*, " +
                    		 "  CASE " +
                    		 "    WHEN yy.Cashbook_Month=1 " +
                    		 "    THEN 'January' " +
                    		 "    WHEN yy.Cashbook_Month=2 " +
                    		 "    THEN 'February' " +
                    		 "    WHEN yy.Cashbook_Month=3 " +
                    		 "    THEN 'March' " +
                    		 "    WHEN yy.Cashbook_Month=4 " +
                    		 "    THEN 'April' " +
                    		 "    WHEN yy.Cashbook_Month=5 " +
                    		 "    THEN 'May' " +
                    		 "    WHEN yy.Cashbook_Month=6 " +
                    		 "    THEN 'June' " +
                    		 "    WHEN yy.Cashbook_Month=7 " +
                    		 "    THEN 'July' " +
                    		 "    WHEN yy.Cashbook_Month=8 " +
                    		 "    THEN 'August' " +
                    		 "    WHEN yy.Cashbook_Month=9 " +
                    		 "    THEN 'September' " +
                    		 "    WHEN yy.Cashbook_Month=10 " +
                    		 "    THEN 'October' " +
                    		 "    WHEN yy.Cashbook_Month=11 " +
                    		 "    THEN 'November' " +
                    		 "    WHEN yy.Cashbook_Month=12 " +
                    		 "    THEN 'December' " +
                    		 "  END AS month_desc, " +
                    		 "  xx.voucher_no, " +
                    		 "  xx.payment_date, " +
                    		 "  Xx.Pay_Amt, " +
                    		 "  xx.pay_amt-yy.che_amt AS diff " +
                    		 "FROM " +
                    		 "  (SELECT SUM(t.amount) AS pay_amt, " +
                    		 "    T.Cheque_Dd_No, " +
                    		"  TO_CHAR(T.cheque_dd_date,'dd/mm/yy') AS cheque_dd_date, " +
                    		 "    m.Accounting_Unit_Id, " +
                    		 "    M.Cashbook_Year, " +
                    		 "    m.cashbook_month, " +
                    		 "    m.voucher_no, " +
                    		 "    to_char(m.payment_date,'dd/mm/yy') as payment_date  " +
                    		 "  FROM Fas_Payment_Master M " +
                    		 "  INNER JOIN Fas_Payment_Transaction T " +
                    		 "  ON m.accounting_for_office_id        =t.accounting_for_office_id " +
                    		 "  AND M.Accounting_Unit_Id             =T.Accounting_Unit_Id " +
                    		 "  AND M.Cashbook_Month                 =T.Cashbook_Month " +
                    		 "  AND M.Cashbook_Year                  =T.Cashbook_Year " +
                    		 "  AND m.voucher_no                     =T.voucher_no " +
                    		 "  AND (M.Voucher_No , M.Payment_Date) IN " +
                    		 "    (SELECT DISTINCT T.Pvr_No , " +
                    		 "      T.Pvr_Date " +
                    		 //"      --, " +
                    		 //"      -- t.payment_unit, " +
                    		 //"      --   t.payment_office " +
                    		 "    FROM Fas_Memo_Of_Payment_Mst M " +
                    		 "    INNER JOIN Fas_Memo_Of_Payment_Trn T " +
                    		 "    ON m.accounting_for_office_id =t.accounting_for_office_id " +
                    		 "    AND M.Accounting_Unit_Id      =T.Accounting_Unit_Id " +
                    		 "    AND M.Cashbook_Month          =T.Cashbook_Month " +
                    		 "    AND M.Cashbook_Year           =T.Cashbook_Year " +
                    		 "    AND M.Bill_No                 =T.Bill_No " +
                    		 "    AND t.payment_unit            =? " +
                    		 "    AND t.payment_office      =? " +
                    		 "    AND M.Cashbook_Year           =? " +
                    		 "    AND M.Cashbook_Month          =? " +
                    		 "    AND M.Status                  ='L' " +
                    		 "    ) " +
                    		 "  AND M.Payment_Status     ='L' " +
                    		 "  AND M.Accounting_Unit_Id =? " +
                    		 "  GROUP BY T.Cheque_Dd_No, " +
                    		 "    t.cheque_dd_date, " +
                    		 "    m.Accounting_Unit_Id, " +
                    		 "    M.Cashbook_Year, " +
                    		 "    m.cashbook_month, " +
                    		 "    m.voucher_no, " +
                    		 "    m.payment_date " +
                    		 "  ) Xx " +
                    		 " INNER JOIN " +
                    		 "  (SELECT SUM(cheque_amount) AS che_amt, " +
                    		 "    cheque_no, " +
                    		 "    to_char(cheque_date,'dd/mm/yy') as cheque_date , " +
                    		 "    Accounting_Unit_Id, " +
                    		 "    Cashbook_Year, " +
                    		 "    Cashbook_Month, " +
                    		 "    CHEQUE_MEMO_NO, " +
                    		 "    to_char(CHEQUE_MEMO_DATE,'dd/mm/yy') as CHEQUE_MEMO_DATE " +
                    		 "  FROM Fas_Cheque_Memo_Mst " +
                    		 "  WHERE Accounting_Unit_Id=? " +
                    		 "  and  ACCOUNTING_FOR_OFFICE_ID=? " +
                    		 "  AND Cashbook_Year       =? " +
                    		 "  AND Cashbook_Month      =? " +
                    		 "  AND Status              ='L' " +
                    		 "  GROUP BY cheque_no, " +
                    		 "    cheque_date, " +
                    		 "    Accounting_Unit_Id, " +
                    		 "    Cashbook_Year, " +
                    		 "    Cashbook_Month, " +
                    		 "    CHEQUE_MEMO_NO, " +
                    		 "    CHEQUE_MEMO_DATE " +
                    		 "  )Yy " +
                    		 " ON xx.Accounting_Unit_Id=yy.Accounting_Unit_Id " +
                    		 " AND Xx.Cheque_Dd_No     =Yy.Cheque_No " +
                    		 " AND Xx.Cheque_Dd_Date   =Yy.Cheque_Date " +
                    		 " ORDER BY xx.Cheque_Dd_No";
                    
                	// System.out.println("que::::::"+que);
                                 ps = con.prepareStatement(que);
                                 ps.setInt(1,cmbAcc_UnitCode);
                                 ps.setInt(2,cmbOffice_code);
                                 ps.setInt(3,txtCB_Year);
                                 ps.setInt(4,txtCB_Month);
                                 ps.setInt(5,cmbAcc_UnitCode);
                                 ps.setInt(6,cmbAcc_UnitCode);
                                 ps.setInt(7,cmbOffice_code);
                                 ps.setInt(8,txtCB_Year);
                                 ps.setInt(9,txtCB_Month);
                                 ResultSet result1 = ps.executeQuery(); 
                                 count=0;
                              //   System.out.println("count   >>> "+count);
                                 while(result1.next()) 
                                 {
                                	 System.out.println("TESTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
                                     xml=xml+"<month>"+result1.getString("month_desc")+"</month>";
                                     xml=xml+"<CHEQUE_MEMO_NO>"+result1.getInt("CHEQUE_MEMO_NO")+"</CHEQUE_MEMO_NO>";
                                     xml=xml+"<CHEQUE_MEMO_DATE>"+result1.getString("CHEQUE_MEMO_DATE")+"</CHEQUE_MEMO_DATE>";
                                     xml=xml+"<Payment_Date>"+result1.getString("Payment_Date")+"</Payment_Date>";
                                     xml=xml+"<voucher_no>"+result1.getInt("voucher_no")+"</voucher_no>";
                                     xml=xml+"<CHEQUE_NO>"+result1.getString("CHEQUE_NO")+"</CHEQUE_NO>";
                                     xml=xml+"<cheque_date>"+result1.getString("cheque_date")+"</cheque_date>";
                                    xml=xml+"<che_amt>"+result1.getString("che_amt")+"</che_amt>";
                                     xml=xml+"<pay_amt>"+result1.getString("pay_amt")+"</pay_amt>";
                                     xml=xml+"<difference>"+result1.getString("diff")+"</difference>";
                                   
                                     count++;
                                    // System.out.println("count1111   >>> "+count);
                                 }
                                 if(count>0)
                                 {
                                     xml=xml+"<flag>success</flag>";
                                     try
                                     {
                                    	pss2=con.prepareStatement("select VERIFY_FLAG from FAS_CHEQUE_MEMO_MONTHEND " +
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
                                 else{
                                     xml=xml+"<flag>NoRecord</flag>";
                                 }
                         }
                   catch(Exception e) 
                         {
                                 System.out.println("Exception in advno ===> "+e);   
                                 xml=xml+"<flag>failure</flag>";  
                         }
                     xml=xml+"</response>";
             }else  if(cmd.equalsIgnoreCase("verifycheque_cr_dr1"))
             {
         	 try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
              catch(NumberFormatException e){System.out.println("exception"+e );}
         	 try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
             catch(NumberFormatException e){System.out.println("exception"+e );}
           //   System.out.println("cmbOffice_code "+cmbOffice_code);
              try{txtCB_Year=Integer.parseInt(request.getParameter("txtCB_Year"));}
              catch(NumberFormatException e){System.out.println("exception"+e );}
              try{txtCB_Month=Integer.parseInt(request.getParameter("txtCB_Month"));}
              catch(NumberFormatException e){System.out.println("exception in txtCB_Month:"+e );}
                xml="<response><command>verifycheque_cr_dr1</command>"; 
                 try 
                         {
                	
   	              
                     /*String que="SELECT aa.Pvr_No , " +
                    		 " SUM(Aa.Amount)AS Amount , " +
                    		 " aa.Pvr_Date, " +
                    		// "  Aa.Bill_No, " +
                    	//	 "  aa.bill_date,"
                    		  " SUM(yy.pay_amt) as pay_amt, " +
                    		 "  Yy.Cheque_Dd_No, " +
                    		 "  yy.cheque_dd_date, " +
                    		 "   yy.Accounting_Unit_Id, " +
                    		 "  yy.Cashbook_Year, " +
                    		 "  yy.Cashbook_Month, " +
                    		 "  Yy.Voucher_No, " +
                    		 "  Yy.Payment_Date, " +
                    		 "   yy.payment_date_fr " +
                    		 " FROM " +
                    		 "  (SELECT Z.Pvr_No , " +
                    		 "    Z.Amount, " +
                    		 "    z.Accounting_Unit_Id, " +
                    		 "    z.Pvr_Date, " +
                    		 "    z.Bill_No, " +
                    		 "    z.bill_date " +
                    		 "  FROM " +
                    		 "    (SELECT t.*, " +
                    		 "      m.cheque_date , " +
                    		 "      m.cheque_no " +
                    		 "    FROM FAS_CHEQUE_MEMO_MST M " +
                    		 "    INNER JOIN FAS_CHEQUE_MEMO_TRN T " +
                    		 "    ON m.accounting_for_office_id =t.accounting_for_office_id " +
                    		 "    AND M.Accounting_Unit_Id      =T.Accounting_Unit_Id " +
                    		 "    AND M.Cashbook_Month          =T.Cashbook_Month " +
                    		 "    AND M.Cashbook_Year           =T.Cashbook_Year " +
                    		 "    AND m.cheque_memo_no          =T.cheque_memo_no " +
                    		 "    AND t.accounting_unit_id      =? " +
                    		 "    AND t.accounting_for_office_id      =? " +
                    		 "    AND M.Cashbook_Year           =? " +
                    		 "    AND M.Cashbook_Month          =? " +
                    		 "    AND m.status                  ='L' " +
                    		 "    )u " +
                    		 "  INNER JOIN " +
                    		 "    (SELECT T.Pvr_No , " +
                    		 "      SUM(T.Amount)  AS Amount, " +
                    		 "      t.payment_unit AS Accounting_Unit_Id, " +
                    		 "      T.Pvr_Date, " +
                    		 "      m.bill_no, " +
                    		 "      m.bill_date " +
                    		 "    FROM Fas_Memo_Of_Payment_Mst M " +
                    		 "    INNER JOIN Fas_Memo_Of_Payment_Trn T " +
                    		 "    ON m.accounting_for_office_id =t.accounting_for_office_id " +
                    		 "    AND M.Accounting_Unit_Id      =T.Accounting_Unit_Id " +
                    		 "    AND M.Cashbook_Month          =T.Cashbook_Month " +
                    		 "    AND M.Cashbook_Year           =T.Cashbook_Year " +
                    		 "    AND M.Bill_No                 =T.Bill_No " +
                    		 "    AND M.Status                  ='L' " +
                    		 "    GROUP BY T.Pvr_No, " +
                    		 "      t.payment_unit, " +
                    		 "      T.Pvr_Date, " +
                    		 "      m.bill_no, " +
                    		 "      m.bill_date " +
                    		 "    )Z ON U.Accounting_Unit_Id=Z.Accounting_Unit_Id " +
                    		 "  AND U.Bill_No               =Z.Bill_No " +
                    		 "  AND u.bill_date             =z.bill_date " +
                    		 "  )aa " +
                    		 " FULL OUTER JOIN " +
                    		 "  (SELECT SUM(t.amount) AS pay_amt, " +
                    		 "    T.Cheque_Dd_No, " +
                    		 "    TO_CHAR(T.cheque_dd_date,'dd/mm/yy') AS cheque_dd_date, " +
                    		 "    m.Accounting_Unit_Id, " +
                    		 "    M.Cashbook_Year, " +
                    		 "    M.Cashbook_Month, " +
                    		 "    m.voucher_no, " +
                    		 "    Payment_Date, " +
                    		 "    TO_CHAR(m.payment_date,'dd/mm/yy') AS payment_date_fr " +
                    		 "  FROM Fas_Payment_Master M " +
                    		 "  INNER JOIN Fas_Payment_Transaction T " +
                    		 "  ON m.accounting_for_office_id =t.accounting_for_office_id " +
                    		 "  AND M.Accounting_Unit_Id      =T.Accounting_Unit_Id " +
                    		 "  AND M.Cashbook_Month          =T.Cashbook_Month " +
                    		 "  AND M.Cashbook_Year           =T.Cashbook_Year " +
                    		 "  AND M.Voucher_No              =T.Voucher_No " +
                    		 "  AND M.Cashbook_Year           =? " +
                    		 "  AND M.Cashbook_Month          =? " +
                    		 "  AND T.Account_Head_Code      IN (390302,390303,390305,391002,391003,391302,391303,391502,391503) " +
                    		 "  AND M.Payment_Status          ='L' " +
                    		 "  AND M.Accounting_Unit_Id      =? " +
                    		 "  AND M.accounting_for_office_id      =? " +
                    		 "  GROUP BY T.Cheque_Dd_No, " +
                    		 "    TO_CHAR(T.cheque_dd_date,'dd/mm/yy'), " +
                    		 "    m.Accounting_Unit_Id, " +
                    		 "    M.Cashbook_Year, " +
                    		 "    M.Cashbook_Month, " +
                    		 "    m.voucher_no, " +
                    		 "    Payment_Date, " +
                    		 "    TO_CHAR(m.payment_date,'dd/mm/yy') " +
                    		 "  )Yy ON Aa.Pvr_No       =Yy.Voucher_No " +
                    		 " AND Aa.Pvr_Date          =Yy.Payment_Date " +
                    		 " AND Aa.Accounting_Unit_Id=Yy.Accounting_Unit_Id"+
                    		 " GROUP BY Aa.Pvr_No, aa.Pvr_Date, Yy.Cheque_Dd_No, yy.cheque_dd_date, yy.Accounting_Unit_Id, yy.Cashbook_Year, yy.Cashbook_Month, Yy.Voucher_No, Yy.Payment_Date, yy.payment_date_fr"
;*/
                	 String que="SELECT aa.Pvr_No , " +
                			 "  SUM(Aa.Amount)AS Amount , " +
                			 "  aa.Pvr_Date, " +
                			 "  SUM(yy.pay_amt) AS pay_amt, " +
                			 "  Yy.Cheque_Dd_No, " +
                			 "  yy.cheque_dd_date, " +
                			 "  yy.Accounting_Unit_Id, " +
                			 "  yy.Cashbook_Year, " +
                			 "  yy.Cashbook_Month, " +
                			 "  Yy.Voucher_No, " +
                			 "  Yy.Payment_Date, " +
                			 "  yy.payment_date_fr " +
                			 " FROM " +
                			 "  (SELECT Z.Pvr_No , " +
                			 "    SUM(Z.Amt) AS Amount, " +
                			 "    z.Accounting_Unit_Id, " +
                			 "    Z.Pvr_Date " +
                			// "    --  z.Bill_No, " +
                			// "    --  z.bill_date " +
                			 "  FROM " +
                			 "    (SELECT T.ACCOUNTING_UNIT_ID,T.ACCOUNTING_FOR_OFFICE_ID,T.CASHBOOK_YEAR," +
                			 " T.CASHBOOK_MONTH,t.CHEQUE_MEMO_NO,T.SL_NO,T.BILL_NO,T.BILL_DATE,T.PASS_ORDER_AMOUNT,"+
                			 " T.VOUCHER_NO,T.VOUCHER_DATE,T.ACCOUNT_HEAD_CODE,T.SUB_LEDGER_TYPE_CODE,T.SUB_LEDGER_CODE," +
                			 " T.AMOUNT,T.PARTICULARS,T.UPDATED_BY_USERID,T.UPDATED_DATE,t.CR_DR_INDICATOR, " +
                			 "      m.cheque_date , " +
                			 "      m.cheque_no " +
                			 "    FROM FAS_CHEQUE_MEMO_MST M " +
                			 "    INNER JOIN FAS_CHEQUE_MEMO_TRN T " +
                			 "    ON m.accounting_for_office_id  =t.accounting_for_office_id " +
                			 "    AND M.Accounting_Unit_Id       =T.Accounting_Unit_Id " +
                			 "    AND M.Cashbook_Month           =T.Cashbook_Month " +
                			 "    AND M.Cashbook_Year            =T.Cashbook_Year " +
                			 "    AND M.Cheque_Memo_No           =T.Cheque_Memo_No " +
                			 "    AND T.Accounting_Unit_Id       =? " +
                			 "    AND T.Accounting_For_Office_Id =? " +
                			 "    AND M.Cashbook_Year            =? " +
                			 "    AND M.Cashbook_Month           =? " +
                			 "    AND m.status                   ='L' " +
                			 "    )u " +
                			 "  INNER JOIN " +
                			 "    (SELECT DISTINCT T.Amount as amt, " +
                			// "      --// Sum(T.Amount)  As Amount, " +
                			 "      T.Pvr_No , " +
                			 "      t.payment_unit AS Accounting_Unit_Id, " +
                			 "      T.Pvr_Date, " +
                			 "      m.bill_no, " +
                			 "      m.bill_date " +
                			 "    FROM Fas_Memo_Of_Payment_Mst M " +
                			 "    INNER JOIN Fas_Memo_Of_Payment_Trn T " +
                			 "    ON m.accounting_for_office_id =t.accounting_for_office_id " +
                			 "    AND M.Accounting_Unit_Id      =T.Accounting_Unit_Id " +
                			 "    AND M.Cashbook_Month          =T.Cashbook_Month " +
                			 "    AND M.Cashbook_Year           =T.Cashbook_Year " +
                			 "    AND M.Bill_No                 =T.Bill_No " +
                			 "    AND M.Status                  ='L' " +
                			// "      --    GROUP BY T.Pvr_No, " +
                			// "      --      t.payment_unit, " +
                			// "      --      T.Pvr_Date, " +
                			// "      --      m.bill_no, " +
                			// "      --      m.bill_date " +
                			 "    )Z ON U.Accounting_Unit_Id=Z.Accounting_Unit_Id " +
                			 "  AND U.Bill_No               =Z.Bill_No " +
                			 "  AND U.Bill_Date             =Z.Bill_Date " +
                			 "  AND u.amount                =z.amt " +
                			// "  and u.sl_no                 =z.sl_no " +
                			 "  GROUP BY Z.Pvr_No, " +
                			 "    z.Accounting_Unit_Id, " +
                			 "    Z.Pvr_Date " +
                			 "  )aa " +
                			 " FULL OUTER JOIN " +
                			 "  (SELECT SUM(t.amount) AS pay_amt, " +
                			 "    T.Cheque_Dd_No, " +
                			 "    TO_CHAR(T.cheque_dd_date,'dd/mm/yy') AS cheque_dd_date, " +
                			 "    m.Accounting_Unit_Id, " +
                			 "    M.Cashbook_Year, " +
                			 "    M.Cashbook_Month, " +
                			 "    m.voucher_no, " +
                			 "    Payment_Date, " +
                			 "    TO_CHAR(m.payment_date,'dd/mm/yy') AS payment_date_fr " +
                			 "  FROM Fas_Payment_Master M " +
                			 "  INNER JOIN Fas_Payment_Transaction T " +
                			 "  ON m.accounting_for_office_id  =t.accounting_for_office_id " +
                			 "  AND M.Accounting_Unit_Id       =T.Accounting_Unit_Id " +
                			 "  AND M.Cashbook_Month           =T.Cashbook_Month " +
                			 "  AND M.Cashbook_Year            =T.Cashbook_Year " +
                			 "  AND M.Voucher_No               =T.Voucher_No " +
                			 "  AND M.Cashbook_Year            =? " +
                			 "  AND M.Cashbook_Month           =? " +
                			 "  AND T.Account_Head_Code       IN (390302,390303,390305,391002,391003,391302,391303,391502,391503) " +
                			 "  AND M.Payment_Status           ='L' " +
                			 "  AND M.created_by_module != 'NP'	"+
                			 "  AND M.Accounting_Unit_Id       =? " +
                			 "  AND M.accounting_for_office_id =? " +
                			 "  GROUP BY T.Cheque_Dd_No, " +
                			 "    TO_CHAR(T.cheque_dd_date,'dd/mm/yy'), " +
                			 "    m.Accounting_Unit_Id, " +
                			 "    M.Cashbook_Year, " +
                			 "    M.Cashbook_Month, " +
                			 "    m.voucher_no, " +
                			 "    Payment_Date, " +
                			 "    TO_CHAR(m.payment_date,'dd/mm/yy') " +
                			 "  )Yy ON Aa.Pvr_No       =Yy.Voucher_No " +
                			 " AND Aa.Pvr_Date          =Yy.Payment_Date " +
                			 " AND Aa.Accounting_Unit_Id=Yy.Accounting_Unit_Id " +
                			 " GROUP BY Aa.Pvr_No, " +
                			 "  Aa.Pvr_Date, " +
                			 "  Yy.Cheque_Dd_No, " +
                			 "  Yy.Cheque_Dd_Date, " +
                			 "  Yy.Accounting_Unit_Id, " +
                			 "  Yy.Cashbook_Year, " +
                			 "  Yy.Cashbook_Month, " +
                			 "  Yy.Voucher_No, " +
                			 "  Yy.Payment_Date, " +
                			 "  Yy.Payment_Date_Fr  order by Aa.Pvr_No,  Aa.Pvr_Date,  Yy.Cheque_Dd_No ";

                    
                 System.out.println("que::::::"+que);
                                 ps = con.prepareStatement(que);
                                 ps.setInt(1,cmbAcc_UnitCode);
                                 ps.setInt(2,cmbOffice_code);
                                 ps.setInt(3,txtCB_Year);
                                 ps.setInt(4,txtCB_Month);
                                 ps.setInt(5,txtCB_Year);
                                 ps.setInt(6,txtCB_Month);
                                 ps.setInt(7,cmbAcc_UnitCode);
                                 ps.setInt(8,cmbOffice_code);
                           
                                 ResultSet result1 = ps.executeQuery(); 
                                 count=0;
                               //  System.out.println("count   >>> "+count);
                                 while(result1.next()) 
                                 {
                                	 System.out.println("TESTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
                                   //  xml=xml+"<month>"+result1.getString("month_desc")+"</month>";
                                    /* xml=xml+"<CHEQUE_MEMO_NO>"+result1.getInt("CHEQUE_MEMO_NO")+"</CHEQUE_MEMO_NO>";
                                     xml=xml+"<CHEQUE_MEMO_DATE>"+result1.getString("CHEQUE_MEMO_DATE")+"</CHEQUE_MEMO_DATE>";*/
                                     xml=xml+"<Payment_Date>"+result1.getString("payment_date_fr")+"</Payment_Date>";
                                     xml=xml+"<voucher_no>"+result1.getInt("voucher_no")+"</voucher_no>";
                                     xml=xml+"<CHEQUE_NO>"+result1.getString("Cheque_Dd_No")+"</CHEQUE_NO>";
                                     xml=xml+"<cheque_date>"+result1.getString("cheque_dd_date")+"</cheque_date>";
                                    xml=xml+"<che_amt>"+result1.getString("amount")+"</che_amt>";
                                     xml=xml+"<pay_amt>"+result1.getString("pay_amt")+"</pay_amt>";
                                     
                                     count++;
                                    // System.out.println("count1111   >>> "+count);
                                 }
                                 if(count>=0)
                                 {
                           
                                     try
                                     {
                                    	pss2=con.prepareStatement("select VERIFY_FLAG from FAS_CHEQUE_MEMO_MONTHEND  " +
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
                                 if(count>0) xml=xml+"<flag>success</flag>";
                                 else
                                     xml=xml+"<flag>NoRecord</flag>";
                                 
                         }
                   catch(Exception e) 
                         {
                                 System.out.println("Exception in advno ===> "+e);   
                                 xml=xml+"<flag>failure</flag>";  
                         }
                     xml=xml+"</response>";
             }else if (cmd.equalsIgnoreCase("cheYear_Month"))
             {
            	 try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
                 catch(NumberFormatException e){System.out.println("exception"+e );}
                 try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
                 catch(NumberFormatException e){System.out.println("exception"+e );}
                 xml="<response><command>submit_all</command>";
                 
                 try{
                	   sql="SELECT MAX(Cashbook_Month), " +
                			  "  Cashbook_Year " +
                			  " FROM Fas_Cheque_Memo_Monthend " +
                			  " WHERE Accounting_Unit_Id= " +cmbAcc_UnitCode+
                			  " AND Cashbook_Year       = " +
                			  "  (SELECT MAX(cashbook_year) " +
                			  "  FROM FAS_CHEQUE_MEMO_MONTHEND " +
                			  "  WHERE accounting_unit_id= " +cmbAcc_UnitCode+
                			  "  ) " +
                			  " GROUP BY cashbook_year";
                	    ps=con.prepareStatement(sql);
                	                     
                	                      ResultSet  rs=ps.executeQuery();                        
                	                        while(rs.next())
                	                        {   
                	                           out.println("<option value="+rs.getInt("REDEPLOYED_OFFICE_ID")+">"+rs.getString("office_desc")+"</option>");
                	                        }
                	                    }                    
                	                 
                	                catch(Exception e)
                	                {
                	                   System.out.println("Exception in Office combo..."+e);
                	                }
                	                 
                     xml=xml+"</response>";
                     }
         else if(cmd.equalsIgnoreCase("submit_all")) 
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
            	   ps3=con.prepareStatement("Select Verify_Flag From Fas_Tda_Tca_Monthend Where Accounting_Unit_Id=? and ACCOUNTING_FOR_OFFICE_ID=? and Cashbook_Year=? and CASHBOOK_MONTH=? ");
            	   ps3.setInt(1,cmbAcc_UnitCode);
            	   ps3.setInt(2,cmbOffice_code);
            	   ps3.setInt(3,txtCB_Year);
            	   ps3.setInt(4,txtCB_Month);
            	   rs3=ps3.executeQuery();
            	   while(rs3.next())
            	   {
            		   ps4=con.prepareStatement("delete from FAS_TDA_TCA_MONTHEND Where Accounting_Unit_Id=? and ACCOUNTING_FOR_OFFICE_ID=? and Cashbook_Year=? and CASHBOOK_MONTH=? ");
            		   ps4.setInt(1,cmbAcc_UnitCode);
            		   ps4.setInt(2,cmbOffice_code);
            		   ps4.setInt(3,txtCB_Year);
            		   ps4.setInt(4,txtCB_Month);
            		  err= ps4.executeUpdate();
            	   }
            	   rs3.close();
            	   ps3.close();
               }
               catch(Exception qe)
               {
            	 System.out.println("error in delete");  
               }
               
               try{
            	   ps2=con.prepareStatement("insert into FAS_TDA_TCA_MONTHEND (ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,VERIFY_FLAG,UPDATED_BY_USERID,UPDATED_DATE) values(?,?,?,?,?,?,?)");
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
         } else if(cmd.equalsIgnoreCase("submit_allNew")) 
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
            	   ps3=con.prepareStatement("Select Verify_Flag From FAS_CHEQUE_MEMO_MONTHEND Where Accounting_Unit_Id=? and ACCOUNTING_FOR_OFFICE_ID=? and Cashbook_Year=? and CASHBOOK_MONTH=?::varchar ");
            	   ps3.setInt(1,cmbAcc_UnitCode);
            	   ps3.setInt(2,cmbOffice_code);
            	   ps3.setInt(3,txtCB_Year);
            	   ps3.setInt(4,txtCB_Month);
            	   rs3=ps3.executeQuery();
            	   while(rs3.next())
            	   {
            		   ps4=con.prepareStatement("delete from FAS_CHEQUE_MEMO_MONTHEND Where Accounting_Unit_Id=? and ACCOUNTING_FOR_OFFICE_ID=? and Cashbook_Year=? and CASHBOOK_MONTH=? ");
            		   ps4.setInt(1,cmbAcc_UnitCode);
            		   ps4.setInt(2,cmbOffice_code);
            		   ps4.setInt(3,txtCB_Year);
            		   ps4.setInt(4,txtCB_Month);
            		  err= ps4.executeUpdate();
            	   }
            	   rs3.close();
            	   ps3.close();
               }
               catch(Exception qe)
               {
            	 System.out.println("error in delete");  
               }
               
               try{
            	   ps2=con.prepareStatement("insert into FAS_CHEQUE_MEMO_MONTHEND (ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,VERIFY_FLAG,UPDATED_BY_USERID,UPDATED_DATE) values(?,?,?,?,?,?,?)");
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
         System.out.println("xml ::"+xml);
         out.println(xml);
         out.close();
}
}
