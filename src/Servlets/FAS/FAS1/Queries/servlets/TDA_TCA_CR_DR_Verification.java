package Servlets.FAS.FAS1.Queries.servlets;

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
 * Servlet implementation class TDA_TCA_CR_DR_Verification
 */
public class TDA_TCA_CR_DR_Verification extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TDA_TCA_CR_DR_Verification() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("verify_cr_dr:::");
		 response.setHeader("Cache-Control", "no-cache");
         String CONTENT_TYPE = "text/xml; charset=windows-1252";
         response.setContentType(CONTENT_TYPE);
        
         PrintWriter out = response.getWriter();
         String cmd;
         int major;
         int unitid=0,offid=0,invoiceNo=0,billno=0,headcode=0;
         String todate="";
         int agreeno=0,count=0;
         String isection="",expen="";
         String xml="",sql="";;
         
         Connection con=null;
         PreparedStatement ps;
         Statement st=null;
         ResultSet result=null;
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
          //    System.out.println("txtCB_Month "+txtCB_Month);
                xml="<response><command>verify_cr_dr</command>"; 
                 try 
                         {
                	 
                     sql="Select Cashbook_Month,Account_Head_Code,Trn_Dr,Trn_Cr,Tda_Dr,Tda_Cr,Trn_Net,Tda_Net,\n" + 
                     " (Trn_Net-tda_net)as difference from\n" + 
                     " (Select Aaa.Cashbook_Month,Aaa.Account_Head_Code,Aaa.Trn_Dr,Aaa.Trn_Cr,Bbb.Tda_Dr,Bbb.Tda_Cr,\n" + 
                     " Case When Aaa.Trn_Dr>Aaa.Trn_Cr Then (Aaa.Trn_Dr-Aaa.Trn_Cr)\n" + 
                     "     When Aaa.Trn_Cr>Aaa.Trn_Dr Then (Aaa.Trn_Cr-Aaa.Trn_Dr) Else 0 End As Trn_Net,\n" + 
                     " Case When Bbb.Tda_Dr>Bbb.Tda_Cr Then (Bbb.Tda_Dr-Bbb.Tda_Cr)\n" + 
                     "    When Bbb.Tda_Cr>Bbb.Tda_Dr Then (Bbb.Tda_Cr-Bbb.Tda_Dr) Else 0 End As Tda_Net\n" + 
                     " from\n" + 
                     " (select Cashbook_Month,Account_Head_Code,sum(dr_Amount)as trn_dr,sum(cr_amount)as trn_cr from\n" + 
                     " (" +
                    " select Cashbook_Month,Account_Head_Code,sum(dr_Amount)as dr_Amount,sum(cr_amount)as cr_amount from" +
                     " (SELECT B.Cashbook_Month,B.Account_Head_Code," +
                     "      CASE" +
                     "         WHEN B.Cr_Dr_Indicator='DR'" +
                     "         THEN SUM(B.Amount)" +
                     "         ELSE 0" +
                     "       END AS dr_Amount," +
                     "       CASE" +
                     "         WHEN b.Cr_Dr_Indicator='CR'" +
                     "         THEN SUM(B.Amount) " +
                     "         ELSE 0 " +
                     "       END AS cr_amount " +
                     "     FROM fas_journal_master a,Fas_Journal_Transaction b " +
                     "     WHERE A.Accounting_Unit_Id    =b.Accounting_Unit_Id " +
                     "      AND A.Accounting_For_Office_Id=B.Accounting_For_Office_Id " +
                     "     AND A.Cashbook_Year           =B.Cashbook_Year " +
                     "     AND A.Cashbook_Month          =B.Cashbook_Month " +
                     "     AND A.Voucher_No              =B.Voucher_No " +
                     "     And A.Journal_Status          ='L' " +
                     "     And A.Journal_Type_Code      In (62,63,65,66) and A.Mode_Of_Creation='A' " +
                     "     AND B.Accounting_Unit_Id      = " +cmbAcc_UnitCode+
                     "     And B.Cashbook_Year           = " +txtCB_Year+
                     "     And B.Account_Head_Code      In (900108,900109,901001,901002) " +
                     "     GROUP BY B.Cashbook_Month,B.Account_Head_Code,B.Cr_Dr_Indicator " +
                     "      union all " +
                     "        SELECT B.Cashbook_Month,B.Account_Head_Code, " +
                     "      CASE " +
                     "         WHEN B.Cr_Dr_Indicator='DR' " +
                     "         THEN SUM(B.Amount) " +
                     "         ELSE 0 " +
                     "       END AS dr_Amount, " +
                     "       CASE " +
                     "         WHEN b.Cr_Dr_Indicator='CR' " +
                     "         THEN SUM(B.Amount) " +
                     "         ELSE 0 " +
                     "       END AS cr_amount " +
                     "     FROM fas_journal_master a,Fas_Journal_Transaction b " +
                     "     WHERE A.Accounting_Unit_Id    =b.Accounting_Unit_Id " +
                     "     AND A.Accounting_For_Office_Id=B.Accounting_For_Office_Id " +
                     "      AND A.Cashbook_Year           =B.Cashbook_Year " +
                     "     AND A.Cashbook_Month          =B.Cashbook_Month " +
                     "     AND A.Voucher_No              =B.Voucher_No " +
                     "     And A.Journal_Status          ='L' " +
                     "     AND A.Journal_Type_Code      IN (54) " +
                     "     AND B.Accounting_Unit_Id      = " +cmbAcc_UnitCode+
                     "     And B.Cashbook_Year           = " +txtCB_Year+
                     "     And B.Account_Head_Code      In (900108,900109,901001,901002) " +
                       //  and (B.Cb_Ref_No!=0)
                     "   GROUP BY B.Cashbook_Month,B.Account_Head_Code,B.Cr_Dr_Indicator) " +
                     "     GROUP BY Cashbook_Month,Account_Head_Code " +
                     " Union All\n" + 
                     " Select B.Cashbook_Month,\n" + 
                     "  B.Account_Head_Code,\n" + 
                     " case When B.Cr_Dr_Indicator='DR' Then Sum(B.Amount) else 0 end as dr_Amount,\n" + 
                     "  case when b.Cr_Dr_Indicator='CR' then Sum(B.Amount) else 0 end as cr_amount\n" + 
                     " FROM FAS_PAYMENT_MASTER a,FAS_PAYMENT_TRANSACTION b\n" + 
                     " Where A.Accounting_Unit_Id=b.Accounting_Unit_Id\n" + 
                     " And A.Accounting_For_Office_Id=B.Accounting_For_Office_Id\n" + 
                     " And A.Cashbook_Year=B.Cashbook_Year\n" + 
                     " And A.Cashbook_Month=B.Cashbook_Month\n" + 
                     " And A.Voucher_No=B.Voucher_No\n" + 
                     " And A.PAYMENT_STATUS='L'\n" + 
                     " and  B.Accounting_Unit_Id=" +cmbAcc_UnitCode+ 
                     " And B.Cashbook_Year       ="+txtCB_Year+ 
                     " And b.Account_Head_Code  In (900108,900109,901001,901002)\n" + 
                     " Group By B.Cashbook_Month,B.Account_Head_Code,B.Cr_Dr_Indicator\n" + 
                     " Union All\n" + 
                     " Select B.Cashbook_Month,\n" + 
                     "  B.Account_Head_Code,\n" + 
                     " case When B.Cr_Dr_Indicator='DR' Then Sum(B.Amount) else 0 end as dr_Amount,\n" + 
                     "  case when b.Cr_Dr_Indicator='CR' then Sum(B.Amount) else 0 end as cr_amount\n" + 
                     " FROM FAS_RECEIPT_MASTER a,FAS_RECEIPT_TRANSACTION b\n" + 
                     " Where A.Accounting_Unit_Id=b.Accounting_Unit_Id\n" + 
                     " And A.Accounting_For_Office_Id=B.Accounting_For_Office_Id\n" + 
                     " And A.Cashbook_Year=B.Cashbook_Year\n" + 
                     " And A.Cashbook_Month=B.Cashbook_Month\n" + 
                     " And A.RECEIPT_NO=B.RECEIPT_NO\n" + 
                     " And A.Receipt_Status='L'\n" + 
                     " and  B.Accounting_Unit_Id="+cmbAcc_UnitCode+ 
                     " And B.Cashbook_Year       ="+txtCB_Year+ 
                     " And b.Account_Head_Code  In (900108,900109,901001,901002)\n" + 
                     " Group By B.Cashbook_Month,B.Account_Head_Code,B.Cr_Dr_Indicator)\n" + 
                     " Group By Cashbook_Month,Account_Head_Code\n" + 
                     " Order By Cashbook_Month,Account_Head_Code\n" + 
                     " )aaa\n" + 
                     " left outer Join\n" + 
                     " (\n" + 
                     " select Cashbook_Month,Account_Head_Code,sum(dr_Amount)as tda_dr,sum(cr_amount)as tda_cr from\n" + 
                     " (Select B.Cashbook_Month,B.Account_Head_Code,\n" + 
                     " case When B.Cr_Dr_Indicator='DR' Then Sum(B.Amount) else 0 end as dr_Amount,\n" + 
                     "  case when b.Cr_Dr_Indicator='CR' then Sum(B.Amount) else 0 end as cr_amount\n" + 
                     " From Fas_Tda_Tca_Raised_Mst A,Fas_Tda_Tca_Raised_Trn B\n" + 
                     " Where A.Accounting_Unit_Id=b.Accounting_Unit_Id\n" + 
                     " And A.Accounting_For_Office_Id=B.Accounting_For_Office_Id\n" + 
                     " And A.Cashbook_Year=B.Cashbook_Year\n" + 
                     " And A.Cashbook_Month=B.Cashbook_Month\n" + 
                     " And A.Voucher_No=B.Voucher_No\n" + 
                     " And A.Status='L'\n" + 
                     " And  B.Accounting_Unit_Id="+cmbAcc_UnitCode+ 
                     " And B.Cashbook_Year       =" +txtCB_Year+ 
                     " And b.Account_Head_Code  In (900108,900109,901001,901002)\n" + 
                     " Group By B.Cashbook_Month,B.Account_Head_Code,B.Cr_Dr_Indicator\n" + 
                     " order by b.Cashbook_Month,b.Account_Head_Code)\n" + 
                     " Group By Cashbook_Month,Account_Head_Code\n" + 
                     " Order By Cashbook_Month,Account_Head_Code\n" + 
                     " )Bbb\n" + 
                     " On Aaa.Cashbook_Month=Bbb.Cashbook_Month\n" + 
                     " and Aaa.Account_Head_Code=Bbb.Account_Head_Code)";
                	 System.out.println("sql:::"+sql);
                                 ps = con.prepareStatement(sql);
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
                                     xml=xml+"<flag>success</flag>";
                                 else
                                     xml=xml+"<flag>failure</flag>";
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
}
