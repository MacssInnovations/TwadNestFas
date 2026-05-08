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
 * Servlet implementation class tda_tca_verification_units_only_sup
 */
public class tda_tca_verification_units_only_sup extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public tda_tca_verification_units_only_sup() {
        super();
        
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Supplement units");
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
         int eid=0,supNo=0;
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
         System.out.println("Redirect Error :"+e);
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
              try{supNo=Integer.parseInt(request.getParameter("supNo"));}
              catch(NumberFormatException e){System.out.println("exception"+e );}
              
                xml="<response><command>verify_cr_dr</command>"; 
                 try 
                         {
                	String dtsup="31-mar-"+txtCB_Year;
   	              System.out.println(dtsup);
                     String query_new="SELECT Unit_Id,\n" + 
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
                     "          AND A.Mode_Of_Creation        ='A' and a.created_by_module='SJV' " +
                     "       and a.supplement_no= "+supNo+
                     "          And B.Cashbook_Year           = " +txtCB_Year+ 
                     "          and B.Cashbook_Month=" +txtCB_Month+ 
                     "          AND B.Account_Head_Code      IN (900108,900109,901001,901002)\n" + 
                     "          GROUP BY B.Accounting_Unit_Id,\n" + 
                     "            B.Cashbook_Month,\n" + 
                     "            B.Account_Head_Code,\n" + 
                     "            B.Cr_Dr_Indicator\n" + 
                    /* "          UNION ALL\n" + 
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
                     "          AND A.Journal_Type_Code      IN (54)" +
                     "  		and a.supplement_no= "+supNo+
                     "          And B.Cashbook_Year           = " +txtCB_Year+
                     "          and B.Cashbook_Month=" +txtCB_Month+
                     "          AND B.Account_Head_Code      IN (900108,900109,901001,901002)\n" + 
                     "          GROUP BY B.Accounting_Unit_Id,\n" + 
                     "            B.Cashbook_Month,\n" + 
                     "            B.Account_Head_Code,\n" + 
                     "            B.Cr_Dr_Indicator\n" + */
                     "          )as sm\n" + 
                     "        GROUP BY Accounting_Unit_Id,\n" + 
                     "          Cashbook_Month,\n" + 
                     "          Account_Head_Code\n" + 
               /*      "        UNION ALL\n" + 
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
                     "          B.Cr_Dr_Indicator\n" +   */
                     "        )as sm\n" + 
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
                     "        and B.Cashbook_Month=" +txtCB_Month+ " and a.supplement_no="+supNo+
                     "        AND B.Account_Head_Code      IN (900108,900109,901001,901002)\n" + 
                   //  " and a.voucher_date='"+dtsup+"'  "+
                     "        GROUP BY B.Accounting_Unit_Id,\n" + 
                     "          B.Cashbook_Month,\n" + 
                     "          B.Account_Head_Code,\n" + 
                     "          B.Cr_Dr_Indicator\n" + 
                     "        ORDER BY B.Accounting_Unit_Id,\n" + 
                     "          B.Cashbook_Month,\n" + 
                     "          b.Account_Head_Code\n" + 
                     "        )as sm\n" + 
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
                     "    )as sm\n";
                    
                	 System.out.println("query_new:::"+query_new);
                                 ps = con.prepareStatement(query_new);
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
                                    	pss2=con.prepareStatement("select VERIFY_FLAG from FAS_TDA_TCA_MONTHEND_SJV " +
                                    			" where ACCOUNTING_UNIT_ID=? and Cashbook_Year=? and CASHBOOK_MONTH::numeric=? and SUPPLEMENT_NO=?");
                                    	pss2.setInt(1,cmbAcc_UnitCode);
                                  	    pss2.setInt(2,txtCB_Year);
                                  	    pss2.setInt(3,txtCB_Month);
                                  	    pss2.setInt(4,supNo);
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
             try{supNo=Integer.parseInt(request.getParameter("supNo"));}
             catch(NumberFormatException e){System.out.println("exception"+e );}
             System.out.println("supNo:::"+supNo);
               xml="<response><command>submit_all</command>";
               
               try
               {
            	   ps3=con.prepareStatement("Select Verify_Flag From FAS_TDA_TCA_MONTHEND_SJV Where Accounting_Unit_Id=? and ACCOUNTING_FOR_OFFICE_ID=? and Cashbook_Year=? and CASHBOOK_MONTH::numeric=? AND SUPPLEMENT_NO=?");
            	   ps3.setInt(1,cmbAcc_UnitCode);
            	   ps3.setInt(2,cmbOffice_code);
            	   ps3.setInt(3,txtCB_Year);
            	   ps3.setInt(4,txtCB_Month);
            	   ps3.setInt(5,supNo);
            	   
            	   
            	   rs3=ps3.executeQuery();
            	   while(rs3.next())
            	   {
            		   ps4=con.prepareStatement("delete from FAS_TDA_TCA_MONTHEND_SJV Where Accounting_Unit_Id=? and ACCOUNTING_FOR_OFFICE_ID=? and Cashbook_Year=? and CASHBOOK_MONTH::numeric=? AND SUPPLEMENT_NO=?");
            		   ps4.setInt(1,cmbAcc_UnitCode);
            		   ps4.setInt(2,cmbOffice_code);
            		   ps4.setInt(3,txtCB_Year);
            		   ps4.setInt(4,txtCB_Month);
            		   ps4.setInt(5,supNo);
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
            	   ps2=con.prepareStatement("insert into FAS_TDA_TCA_MONTHEND_SJV (ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,VERIFY_FLAG,UPDATED_BY_USERID,UPDATED_DATE,SUPPLEMENT_NO) values(?,?,?,?::varchar,?,?,?,?)");
            	   ps2.setInt(1,cmbAcc_UnitCode);
            	   ps2.setInt(2,cmbOffice_code);
            	   ps2.setInt(3,txtCB_Year);
            	   ps2.setInt(4,txtCB_Month);
            	   ps2.setString(5,"Y");
            	   ps2.setString(6,userid);
            	   ps2.setTimestamp(7,ts);
            	   ps2.setInt(8,supNo);
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
