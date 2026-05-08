package Servlets.FAS.FAS1.TDA.Reports;

import Servlets.Security.classes.UserProfile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
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
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;


public class Twad_common_servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private static final String CONTENT_TYPE = 
	        "text/xml; charset=windows-1252";
	    Connection connection = null;

    public void init(ServletConfig config) throws ServletException 
    {
        super.init(config);
      
    }
   
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
        {
             System.out.println("commmmmmmmmmmmmmmmmmmmmmmmmmmm");           
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
             
            try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
         //   System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);
            
           
            
            try{txtCB_Year=Integer.parseInt(request.getParameter("txtCB_Year"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
         //   System.out.println("txtCB_Year "+txtCB_Year);
            
           
             
             
            if(cmd.equalsIgnoreCase("advno"))
                {
            	 try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
                 catch(NumberFormatException e){System.out.println("exception"+e );}
              //   System.out.println("cmbOffice_code "+cmbOffice_code);
                 try{txtCB_Month=Integer.parseInt(request.getParameter("txtCB_Month"));}
                 catch(NumberFormatException e){System.out.println("exception"+e );}
             //    System.out.println("txtCB_Month "+txtCB_Month);
                   xml="<response><command>advno</command>"; 
                    try 
                            {
                                    ps = con.prepareStatement("select voucher_no from fas_tda_tca_raised_mst " +
                                    " where ACCOUNTING_UNIT_ID    ="+cmbAcc_UnitCode+"	\n" + 
                                    "  AND ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+"\n" + 
                                    "  AND CASHBOOK_YEAR           ="+txtCB_Year+"\n" + 
                                    "  AND CASHBOOK_MONTH          ="+txtCB_Month+"\n" + 
                                    "  AND (TDA_OR_TCA              ='TDAO' or TDA_OR_TCA              ='TCAO' OR \n" + 
                                    "  TDA_OR_TCA              ='TDACB'  or TDA_OR_TCA              ='TCACB')  \n"+
                                    " and status = 'L'");
                                    result = ps.executeQuery();                                
                                    while(result.next()) 
                                    {
                                        xml=xml+"<voucherno>"+result.getInt("voucher_no")+"</voucherno>";
                                      
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
            else if(cmd.equalsIgnoreCase("changeAdvNo")) {
            	 try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
                 catch(NumberFormatException e){System.out.println("exception"+e );}
              //   System.out.println("cmbOffice_code "+cmbOffice_code);
                 try{txtCB_Month=Integer.parseInt(request.getParameter("txtCB_Month"));}
                 catch(NumberFormatException e){System.out.println("exception"+e );}
             //    System.out.println("txtCB_Month "+txtCB_Month);
                xml="<response><command>changeAdvNo</command>";
                try{advnumber=Integer.parseInt(request.getParameter("advnumber"));}
                catch(NumberFormatException e){System.out.println("exception"+e );}
                 try 
                         {
                                 ps = con.prepareStatement("SELECT TDA_OR_TCA\n" + 
                                 " FROM fas_tda_tca_raised_mst   \n" + 
                                 " WHERE ACCOUNTING_UNIT_ID    =       "+cmbAcc_UnitCode+"    \n" + 
                                 " AND ACCOUNTING_FOR_OFFICE_ID       = "+cmbOffice_code+"    \n" + 
                                 " AND CASHBOOK_YEAR     =         "+txtCB_Year+"     \n" + 
                                 " AND CASHBOOK_MONTH    =         "+txtCB_Month+"        \n" + 
                                 " and VOUCHER_NO="+advnumber+" AND (TDA_OR_TCA     ='TDAA'\n" + 
                                 " OR TDA_OR_TCA      ='TCAA')");
                                 result = ps.executeQuery();                                
                                 while(result.next()) 
                                 {
                                     xml=xml+"<tda_type>"+result.getString("TDA_OR_TCA")+"</tda_type>";
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
            else if(cmd.equalsIgnoreCase("advno_a89"))
                {
                //System.out.println("advno_a89...........");
            	 try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
                 catch(NumberFormatException e){System.out.println("exception"+e );}
              //   System.out.println("cmbOffice_code "+cmbOffice_code);
                 try{txtCB_Month=Integer.parseInt(request.getParameter("txtCB_Month"));}
                 catch(NumberFormatException e){System.out.println("exception"+e );}
             //    System.out.println("txtCB_Month "+txtCB_Month);
                   xml="<response><command>advno_a89</command>"; 
                    try 
                            {
                                    ps = con.prepareStatement("SELECT voucher_no\n" + 
                                    " FROM fas_tda_tca_raised_mst   \n" + 
                                    " WHERE ACCOUNTING_UNIT_ID    =       "+cmbAcc_UnitCode+"	 \n" + 
                                    " AND ACCOUNTING_FOR_OFFICE_ID       = "+cmbOffice_code+"    \n" + 
                                    " AND CASHBOOK_YEAR     =         "+txtCB_Year+"     \n" + 
                                    " AND CASHBOOK_MONTH    =         "+txtCB_Month+"        \n" + 
                                    " AND (TDA_OR_TCA     ='TDAA'\n" + 
                                    " OR TDA_OR_TCA      ='TCAA') \n" +
                                    " and status = 'L'");
                                    result = ps.executeQuery();                                
                                    while(result.next()) 
                                    {
                                        xml=xml+"<voucherno>"+result.getInt("voucher_no")+"</voucherno>";
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
            else if(cmd.equalsIgnoreCase("marReg"))
            {
                //   System.out.println("marReg...........");
                      xml="<response><command>marReg</command>"; 
                       try 
                               {
                           String sql_test=  "Select A.Account_Head_Code,\n" + 
						   "Decode(A.Dramt,Null,0,Dramt)As Dramt,\n" + 
							"Decode(A.Cramt,Null,0,Cramt)As Cramt,\n" + 
							"decode(a.netTrn,null,0,netTrn)as netTrn,\n" + 
							"B.*,\n" + 
                           "case when decode(a.netTrn,null,0,netTrn)=b.netTrial then 'Tally' else 'NotTally' end as Dif_march\n" + 
                           "from\n" + 
                           "(\n" + 
                           "Select Account_Head_Code,\n" + 
                           "Sum(Dramt)As Dramt,Sum(Cramt) As Cramt,\n" + 
                           "(Sum(Cramt)-Sum(Dramt)) as netTrn\n" + 
                           " from \n" + 
                           "(Select Case When Pt.Cr_Dr_Indicator='DR' Then Sum(Pt.Amount) Else 0 End As Dramt,\n" + 
                           "Case When Pt.Cr_Dr_Indicator='CR' Then Sum(Pt.Amount) Else 0 End As cramt,\n" + 
                           "pt.ACCOUNTING_UNIT_ID,Pt.Account_Head_Code,pt.cr_dr_indicator\n" + 
                           "FROM fas_payment_master pm,\n" + 
                           "  Fas_Payment_Transaction Pt\n" + 
                           "Where Pt.Accounting_Unit_Id = Pm.Accounting_Unit_Id and  Pt.ACCOUNTING_FOR_OFFICE_ID = Pm.ACCOUNTING_FOR_OFFICE_ID\n" + 
                           "And Pt.Cashbook_Year = Pm.Cashbook_Year\n" + 
                           "And Pt.Cashbook_Month = Pm.Cashbook_Month\n" + 
                           "And Pt.Voucher_No = Pm.Voucher_No\n" + 
                           "And Pm.Payment_Status='L'\n" + 
                           "And Pt.Account_Head_Code In(900108,900109,901001,901002)\n" + 
                           "And Pt.Cashbook_Year=2012 And Pt.Cashbook_Month=3 \n" + 
                           "and pt.accounting_unit_id=?\n" + 
                           "GROUP BY pt.ACCOUNTING_UNIT_ID, Pt.Account_Head_Code, pt.cr_dr_indicator\n" + 
                           "\n" + 
                           "\n" + 
                           "Union All\n" + 
                           "Select Case When Pt.Cr_Dr_Indicator='DR' Then Sum(Pt.Amount) Else 0 End As Dramt,\n" + 
                           "Case When Pt.Cr_Dr_Indicator='CR' Then Sum(Pt.Amount) Else 0 End As cramt,\n" + 
                           "pt.ACCOUNTING_UNIT_ID,Pt.Account_Head_Code,pt.cr_dr_indicator\n" + 
                           "FROM FAS_JOURNAL_MASTER pm,\n" + 
                           "  FAS_JOURNAL_TRANSACTION Pt\n" + 
                           "Where Pt.Accounting_Unit_Id = Pm.Accounting_Unit_Id and  Pt.ACCOUNTING_FOR_OFFICE_ID = Pm.ACCOUNTING_FOR_OFFICE_ID\n" + 
                           "And Pt.Cashbook_Year = Pm.Cashbook_Year\n" + 
                           "And Pt.Cashbook_Month = Pm.Cashbook_Month\n" + 
                           "And Pt.Voucher_No = Pm.Voucher_No\n" + 
                           "And Pm.Journal_Status='L'\n" + 
                           "and pm.created_by_module in ('GJV','LJV')\n" + 
                           "And Pt.Account_Head_Code In(900108,900109,901001,901002)\n" + 
                           "And Pt.Cashbook_Year=2012 And Pt.Cashbook_Month=3 \n" + 
                           "and pt.accounting_unit_id=?\n" + 
                           "Group By Pt.Accounting_Unit_Id,Pt.Account_Head_Code,Pt.Cr_Dr_Indicator\n" + 
                           "\n" + 
                           "Union All\n" + 
                           "Select Case When Pt.Cr_Dr_Indicator='DR' Then Sum(Pt.Amount) Else 0 End As Dramt,\n" + 
                           "Case When Pt.Cr_Dr_Indicator='CR' Then Sum(Pt.Amount) Else 0 End As cramt,\n" + 
                           "pt.ACCOUNTING_UNIT_ID,Pt.Account_Head_Code,pt.cr_dr_indicator\n" + 
                           "FROM FAS_RECEIPT_MASTER pm,\n" + 
                           "  FAS_RECEIPT_TRANSACTION Pt\n" + 
                           "Where Pt.Accounting_Unit_Id = Pm.Accounting_Unit_Id and  Pt.ACCOUNTING_FOR_OFFICE_ID = Pm.ACCOUNTING_FOR_OFFICE_ID\n" + 
                           "And Pt.Cashbook_Year = Pm.Cashbook_Year\n" + 
                           "And Pt.Cashbook_Month = Pm.Cashbook_Month\n" + 
                           "And Pt.RECEIPT_NO = Pm.RECEIPT_NO\n" + 
                           "And Pm.Receipt_Status='L'\n" + 
                           "And Pt.Account_Head_Code In(901001,901002,900108,900109)\n" + 
                           "And Pt.Cashbook_Year=2012 And Pt.Cashbook_Month=3 \n" + 
                           "And Pt.Accounting_Unit_Id=?\n" + 
                           "Group By Pt.Accounting_Unit_Id,Pt.Account_Head_Code,Pt.Cr_Dr_Indicator) GROUP BY Account_Head_Code)a \n" + 
                           "\n" + 
                           "full outer Join\n" + 
                           "(\n" + 
                           "select Account_Head_Code as hcode,Dramt as drA,cramt as crA,(cramt-Dramt)as netTrial from\n" + 
                           "(\n" + 
                           "Select CURRENT_MONTH_DEBIT As Dramt,\n" + 
                           "CURRENT_MONTH_CREDIT As cramt,\n" + 
                           "ACCOUNTING_UNIT_ID,Account_Head_Code\n" + 
                           "FROM FAS_TRIAL_BALANCE \n" + 
                           "Where Account_Head_Code In(900108,900109,901001,901002)\n" + 
                           "And Cashbook_Year=2012 And Cashbook_Month=3 \n" + 
                           "And Accounting_Unit_Id=?))B\n" + 
                           "On A.Account_Head_Code=B.Hcode\n" ;
                           System.out.println("sql_test:::"+sql_test);
                                       ps = con.prepareStatement(sql_test);
                                       ps.setInt(1,cmbAcc_UnitCode);
                                       ps.setInt(2,cmbAcc_UnitCode);
                                       ps.setInt(3,cmbAcc_UnitCode);
                                       ps.setInt(4,cmbAcc_UnitCode);
                                       result = ps.executeQuery();                                
                                       while(result.next()) 
                                       {
                                           xml=xml+"<accountCode>"+result.getString("Account_Head_Code")+"</accountCode>";
                                           xml=xml+"<Dramt>"+result.getString("Dramt")+"</Dramt>";
                                           xml=xml+"<Cramt>"+result.getString("Cramt")+"</Cramt>";
                                           xml=xml+"<netTrn>"+result.getString("netTrn")+"</netTrn>";
                                           
                                           xml=xml+"<hcode>"+result.getString("hcode")+"</hcode>";
                                           xml=xml+"<drA>"+result.getString("drA")+"</drA>";
                                           xml=xml+"<crA>"+result.getString("crA")+"</crA>";
                                           xml=xml+"<netTrial>"+result.getString("netTrial")+"</netTrial>";
                                           xml=xml+"<Dif_march>"+result.getString("Dif_march")+"</Dif_march>";
                                         
                                           count++;
                                       }
                                       if(count>0)
                                           xml=xml+"<flag>success</flag>";
                                       else
                                       {
                                    	   xml=xml+"<accountCode>"+"900108"+"</accountCode>";
                                           xml=xml+"<Dramt>"+"0"+"</Dramt>";
                                           xml=xml+"<Cramt>"+"0"+"</Cramt>";
                                           xml=xml+"<netTrn>"+"0"+"</netTrn>";
                                           
                                           xml=xml+"<hcode>"+"900108"+"</hcode>";
                                           xml=xml+"<drA>"+"0"+"</drA>";
                                           xml=xml+"<crA>"+"0"+"</crA>";
                                           xml=xml+"<netTrial>"+"0"+"</netTrial>";
                                           xml=xml+"<Dif_march>"+"Tally"+"</Dif_march>";
                                    	   
                                           xml=xml+"<flag>success</flag>";
                                           
                                       }
                               }
                         catch(Exception e) 
                               {
                                       System.out.println("Exception in advno ===> "+e);   
                                       xml=xml+"<flag>failure</flag>";  
                               }
                           xml=xml+"</response>";
                   }
               else  if(cmd.equalsIgnoreCase("marSupp"))
               {
                   //   System.out.println("advno...........");
                         xml="<response><command>marSupp</command>"; 
                          try 
                                  {
                              String ss="Select C.Account_Head_Code,\n" + 
							 "	Decode(C.Dramt_Supp,Null,0,C.Dramt_Supp)As Dramt_Supp,\n" + 
							 "	Decode(C.Cramt_Supp,Null,0,C.Cramt_Supp)As Cramt_Supp,\n" + 
							 "	Decode(C.Nettrn_Supp,Null,0,C.Nettrn_Supp)As Nettrn_Supp,\n" + 
							 "	D.*,\n" + 
							 "	case when Decode(C.Nettrn_Supp,Null,0,C.Nettrn_Supp)=d.Nettrial_Supp then 'Tally' else 'NotTally' end as diff_supplement \n" + 
                              "from\n" + 
                              "(Select Account_Head_Code,\n" + 
                              "Sum(Dramt)As Dramt_supp,Sum(Cramt) As Cramt_supp,\n" + 
                              "(Sum(Cramt)-Sum(Dramt)) As Nettrn_supp\n" + 
                              "from\n" + 
                              "(Select Case When Pt.Cr_Dr_Indicator='DR' Then Sum(Pt.Amount) Else 0 End As Dramt,\n" + 
                              "Case When Pt.Cr_Dr_Indicator='CR' Then Sum(Pt.Amount) Else 0 End As cramt,\n" + 
                              "pt.ACCOUNTING_UNIT_ID,Pt.Account_Head_Code,pt.cr_dr_indicator\n" + 
                              "FROM FAS_JOURNAL_MASTER pm,\n" + 
                              "  FAS_JOURNAL_TRANSACTION Pt\n" + 
                              "Where Pt.Accounting_Unit_Id = Pm.Accounting_Unit_Id and  Pt.ACCOUNTING_FOR_OFFICE_ID = Pm.ACCOUNTING_FOR_OFFICE_ID\n" + 
                              "And Pt.Cashbook_Year = Pm.Cashbook_Year\n" + 
                              "And Pt.Cashbook_Month = Pm.Cashbook_Month\n" + 
                              "And Pt.Voucher_No = Pm.Voucher_No\n" + 
                              "And Pm.Journal_Status='L'\n" + 
                              "and pm.created_by_module in ('SJV')\n" + 
                              "And Pt.Account_Head_Code In(900108,900109,901001,901002)\n" + 
                              "And Pt.Cashbook_Year=2012 And Pt.Cashbook_Month=3 \n" + 
                              "And Pt.Accounting_Unit_Id=?\n" + 
                              "Group By Pt.Accounting_Unit_Id,Pt.Account_Head_Code,Pt.Cr_Dr_Indicator) Group By Account_Head_Code)c\n" + 
                              "\n" + 
                              "\n" + 
                              "full outer Join\n" + 
                              "(\n" + 
                              "select Account_Head_Code as hcode_supp,Dramt as drA_supp,cramt as crA_supp,(cramt-Dramt)as netTrial_supp from\n" + 
                              "(\n" + 
                              "Select CURRENT_MONTH_DEBIT As Dramt,\n" + 
                              "CURRENT_MONTH_CREDIT As cramt,\n" + 
                              "Account_Head_Code\n" + 
                              "FROM FAS_TRIAL_BALANCE_SUPPLEMENT \n" + 
                              "Where Account_Head_Code In(900108,900109,901001,901002)\n" + 
                              "And Cashbook_Year=2012 And Cashbook_Month=3 \n" + 
                              "And Accounting_Unit_Id=?))D\n" + 
                              "on c.Account_Head_Code=d.hcode_supp";
                              System.out.println("ss::"+ss);
                              ps = con.prepareStatement(ss);
                              ps.setInt(1,cmbAcc_UnitCode);
                              ps.setInt(2,cmbAcc_UnitCode);
                              
                              result = ps.executeQuery();                                
                              while(result.next()) 
                              {
                                  xml=xml+"<accountCode>"+result.getString("Account_Head_Code")+"</accountCode>";
                                  xml=xml+"<Dramt>"+result.getString("Dramt_supp")+"</Dramt>";
                                  xml=xml+"<Cramt>"+result.getString("Cramt_supp")+"</Cramt>";
                                  xml=xml+"<netTrn>"+result.getString("Nettrn_supp")+"</netTrn>";
                                  
                                  xml=xml+"<hcode>"+result.getString("hcode_supp")+"</hcode>";
                                  xml=xml+"<drA>"+result.getString("drA_supp")+"</drA>";
                                  xml=xml+"<crA>"+result.getString("crA_supp")+"</crA>";
                                  xml=xml+"<netTrial>"+result.getString("netTrial_supp")+"</netTrial>";
                                  xml=xml+"<Dif_march>"+result.getString("diff_supplement")+"</Dif_march>";
                                
                                  count++;
                              }
                                          if(count>0)
                                              xml=xml+"<flag>success</flag>";
                                          else
                                          {
                                       	   xml=xml+"<accountCode>"+"900108"+"</accountCode>";
                                              xml=xml+"<Dramt>"+"0"+"</Dramt>";
                                              xml=xml+"<Cramt>"+"0"+"</Cramt>";
                                              xml=xml+"<netTrn>"+"0"+"</netTrn>";
                                              
                                              xml=xml+"<hcode>"+"900108"+"</hcode>";
                                              xml=xml+"<drA>"+"0"+"</drA>";
                                              xml=xml+"<crA>"+"0"+"</crA>";
                                              xml=xml+"<netTrial>"+"0"+"</netTrial>";
                                              xml=xml+"<Dif_march>"+"Tally"+"</Dif_march>";
                                       	   
                                              xml=xml+"<flag>success</flag>";
                                              
                                          }
                                  }
                            catch(Exception e) 
                                  {
                                          System.out.println("Exception in advno ===> "+e);   
                                          xml=xml+"<flag>failure</flag>";  
                                  }
                              xml=xml+"</response>";
                      }
               else  if(cmd.equalsIgnoreCase("aprReg"))
               {
                   //   System.out.println("advno...........");
                         xml="<response><command>aprReg</command>"; 
                          try 
                                  {
                              String tt="\n" + 
                             " Select A.Account_Head_Code,\n" + 
                             " Decode(A.Dramt,Null,0,Dramt)As Dramt,\n" + 
                              " Decode(A.Cramt,Null,0,Cramt)As Cramt,\n" + 
                              " decode(a.netTrn,null,0,netTrn)as netTrn,\n" + 
                              " B.*,\n" + 
                              " case when decode(a.netTrn,null,0,netTrn)=b.netTrial then 'Tally' else 'NotTally' end as Dif_april\n" +  
                              " from\n" + 
                              "(\n" + 
                              "Select Account_Head_Code,\n" + 
                              "Sum(Dramt)As Dramt,Sum(Cramt) As Cramt,\n" + 
                              "(Sum(Cramt)-Sum(Dramt)) as netTrn\n" + 
                              " from \n" + 
                              "(Select Case When Pt.Cr_Dr_Indicator='DR' Then Sum(Pt.Amount) Else 0 End As Dramt,\n" + 
                              "Case When Pt.Cr_Dr_Indicator='CR' Then Sum(Pt.Amount) Else 0 End As cramt,\n" + 
                              "pt.ACCOUNTING_UNIT_ID,Pt.Account_Head_Code,pt.cr_dr_indicator\n" + 
                              "FROM fas_payment_master pm,\n" + 
                              "  Fas_Payment_Transaction Pt\n" + 
                              "Where Pt.Accounting_Unit_Id = Pm.Accounting_Unit_Id\n" + 
                              "And Pt.Cashbook_Year = Pm.Cashbook_Year\n" + 
                              "And Pt.Cashbook_Month = Pm.Cashbook_Month\n" + 
                              "And Pt.Voucher_No = Pm.Voucher_No\n" + 
                              "And Pm.Payment_Status='L'\n" + 
                              "And Pt.Account_Head_Code In(900108,900109,901001,901002)\n" + 
                              "And Pt.Cashbook_Year=2012 And Pt.Cashbook_Month=4\n" + 
                              "and pt.accounting_unit_id=?\n" + 
                              "GROUP BY pt.ACCOUNTING_UNIT_ID, Pt.Account_Head_Code, pt.cr_dr_indicator\n" + 
                              "\n" + 
                              "\n" + 
                              "Union All\n" + 
                              "Select Case When Pt.Cr_Dr_Indicator='DR' Then Sum(Pt.Amount) Else 0 End As Dramt,\n" + 
                              "Case When Pt.Cr_Dr_Indicator='CR' Then Sum(Pt.Amount) Else 0 End As cramt,\n" + 
                              "pt.ACCOUNTING_UNIT_ID,Pt.Account_Head_Code,pt.cr_dr_indicator\n" + 
                              "FROM FAS_JOURNAL_MASTER pm,\n" + 
                              "  FAS_JOURNAL_TRANSACTION Pt\n" + 
                              "Where Pt.Accounting_Unit_Id = Pm.Accounting_Unit_Id\n" + 
                              "And Pt.Cashbook_Year = Pm.Cashbook_Year\n" + 
                              "And Pt.Cashbook_Month = Pm.Cashbook_Month\n" + 
                              "And Pt.Voucher_No = Pm.Voucher_No\n" + 
                              "And Pm.Journal_Status='L'\n" + 
                              "and pm.created_by_module in ('GJV','LJV')\n" + 
                              "And Pt.Account_Head_Code In(900108,900109,901001,901002)\n" + 
                              "And Pt.Cashbook_Year=2012 And Pt.Cashbook_Month=4 \n" + 
                              "and pt.accounting_unit_id=?\n" + 
                              "Group By Pt.Accounting_Unit_Id,Pt.Account_Head_Code,Pt.Cr_Dr_Indicator\n" + 
                              "\n" + 
                              "Union All\n" + 
                              "Select Case When Pt.Cr_Dr_Indicator='DR' Then Sum(Pt.Amount) Else 0 End As Dramt,\n" + 
                              "Case When Pt.Cr_Dr_Indicator='CR' Then Sum(Pt.Amount) Else 0 End As cramt,\n" + 
                              "pt.ACCOUNTING_UNIT_ID,Pt.Account_Head_Code,pt.cr_dr_indicator\n" + 
                              "FROM FAS_RECEIPT_MASTER pm,\n" + 
                              "  FAS_RECEIPT_TRANSACTION Pt\n" + 
                              "Where Pt.Accounting_Unit_Id = Pm.Accounting_Unit_Id\n" + 
                              "And Pt.Cashbook_Year = Pm.Cashbook_Year\n" + 
                              "And Pt.Cashbook_Month = Pm.Cashbook_Month\n" + 
                              "And Pt.RECEIPT_NO = Pm.RECEIPT_NO\n" + 
                              "And Pm.Receipt_Status='L'\n" + 
                              "And Pt.Account_Head_Code In(901001,901002,900108,900109)\n" + 
                              "And Pt.Cashbook_Year=2012 And Pt.Cashbook_Month=4\n" + 
                              "And Pt.Accounting_Unit_Id=?\n" + 
                              "Group By Pt.Accounting_Unit_Id,Pt.Account_Head_Code,Pt.Cr_Dr_Indicator) GROUP BY Account_Head_Code)a \n" + 
                              "\n" + 
                              "full outer Join\n" + 
                              "(\n" + 
                              "select Account_Head_Code as hcode,Dramt as drA,cramt as crA,(cramt-Dramt)as netTrial from\n" + 
                              "(\n" + 
                              "Select CURRENT_MONTH_DEBIT As Dramt,\n" + 
                              "CURRENT_MONTH_CREDIT As cramt,\n" + 
                              "ACCOUNTING_UNIT_ID,Account_Head_Code\n" + 
                              "FROM FAS_TRIAL_BALANCE \n" + 
                              "Where Account_Head_Code In(900108,900109,901001,901002)\n" + 
                              "And Cashbook_Year=2012 And Cashbook_Month=4\n" + 
                              "And Accounting_Unit_Id=?))B\n" + 
                              "On A.Account_Head_Code=B.Hcode\n" + 
                              "\n" + 
                              "\n";
                              System.out.println("tt:::"+tt);
                                          ps = con.prepareStatement(tt);
                                          ps.setInt(1,cmbAcc_UnitCode);
                                          ps.setInt(2,cmbAcc_UnitCode);
                                          ps.setInt(3,cmbAcc_UnitCode);
                                          ps.setInt(4,cmbAcc_UnitCode);
                                          result = ps.executeQuery();                                
                                          while(result.next()) 
                                          {
                                              xml=xml+"<accountCode>"+result.getString("Account_Head_Code")+"</accountCode>";
                                              xml=xml+"<Dramt>"+result.getString("Dramt")+"</Dramt>";
                                              xml=xml+"<Cramt>"+result.getString("Cramt")+"</Cramt>";
                                              xml=xml+"<netTrn>"+result.getString("netTrn")+"</netTrn>";
                                              
                                              xml=xml+"<hcode>"+result.getString("hcode")+"</hcode>";
                                              xml=xml+"<drA>"+result.getString("drA")+"</drA>";
                                              xml=xml+"<crA>"+result.getString("crA")+"</crA>";
                                              xml=xml+"<netTrial>"+result.getString("netTrial")+"</netTrial>";
                                              xml=xml+"<Dif_march>"+result.getString("Dif_april")+"</Dif_march>";
                                            
                                              count++;
                                          }
                                          if(count>0)
                                              xml=xml+"<flag>success</flag>";
                                          else
                                          {
                                       	   xml=xml+"<accountCode>"+"900108"+"</accountCode>";
                                              xml=xml+"<Dramt>"+"0"+"</Dramt>";
                                              xml=xml+"<Cramt>"+"0"+"</Cramt>";
                                              xml=xml+"<netTrn>"+"0"+"</netTrn>";
                                              
                                              xml=xml+"<hcode>"+"900108"+"</hcode>";
                                              xml=xml+"<drA>"+"0"+"</drA>";
                                              xml=xml+"<crA>"+"0"+"</crA>";
                                              xml=xml+"<netTrial>"+"0"+"</netTrial>";
                                              xml=xml+"<Dif_march>"+"Tally"+"</Dif_march>";
                                       	   
                                              xml=xml+"<flag>success</flag>";
                                              
                                          }
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
    public void doPost(HttpServletRequest request, 
	            HttpServletResponse response) throws ServletException, IOException 
					{
					System.out.println("dopost");
					
					String strCommand="";
					Connection con=null;        
					PreparedStatement ps=null,ps_one=null,ps2=null;     
					Statement st=null;
					ResultSet rs=null,rs2=null;
					//-----------------------------------------------------------------------------------------------        
					
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
					
					//-----------------------------------------------------------------------------------------------        
					
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
					   ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection					   Class.forName(strDriver.trim());
					   con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
					   st=con.createStatement();
					}
					catch(Exception e)
					{
					   System.out.println("Exception in opening connection :"+e);
					   //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");
					
					}
					       
					//-----------------------------------------------------------------------------------------------        
					
					try
					{        
					   strCommand=request.getParameter("Command");
					   System.out.println("assign..here command..."+strCommand);
					 
					}
					
					catch(Exception e) 
					{
					   System.out.println("Exception in assigning..."+e);
					}
					 if(strCommand.equalsIgnoreCase("Add")) 
				        {
				             String CONTENT_TYPE = "text/html; charset=windows-1252";
				             response.setContentType(CONTENT_TYPE);
				            
				             //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
				             Calendar c;
				             int cmbAcc_UnitCode=0,cmbOffice_code=0,txtCash_Month_hid=0,txtCash_year=0,cmbJournal_type=0;            
				             Date txtCrea_date=null,voucher_date=null;
				             String journal_type_desc="",cr_dr_indicator="",sql="",flag="",particulars="",voucher_display="";
				             String financial_year[]=null;
				             int account_head_code=0,sub_ledger_code=0,trn_records=0,trn_count=0,grid_count=0,depriciation_rate=0,cashbook_year=0,cashbook_month=0;
				             
				             String paid_to=""; 
				     		 int year=0,month=0,count_inc=0;
				     		 double amount=0;
				           String txtoption=null,txtParticular=null;
				                                     // changes here
				             String update_user=(String)session.getAttribute("UserId");
				             long l=System.currentTimeMillis();
				             Timestamp ts=new Timestamp(l);
				             //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
				                                
				             try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
				             catch(NumberFormatException e){System.out.println("exception"+e );}
				             System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);
				             
				             try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
				             catch(NumberFormatException e){System.out.println("exception"+e );}
				             System.out.println("cmbOffice_code "+cmbOffice_code);
				             
				             int enter=0;
				             
				             txtoption=request.getParameter("txtoption");
				             System.out.println("txtoption::"+txtoption);
				             
				             txtParticular=request.getParameter("txtParticular");				             
				             
				             if(txtoption.equalsIgnoreCase("mr"))
				             {
				            	 year=2012;
				            	 month=3;
				            	 try{
				            	 ps=con.prepareStatement("delete from FAS_TDA_TCA_REG_MAR2012 where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=?");
				            	 ps.setInt(1,cmbAcc_UnitCode);
                                 ps.setInt(2,cmbOffice_code);
                                 ps.setInt(3,year);
                                 ps.setInt(4,month);
                                 ps.executeUpdate();
				            	 }
				            	 catch(Exception e)
				            	 {
				            		 System.out.println("e in delete:"+e);
				            	 }
				         	
				             try
				             {

		                           String sql_test="\n" + 
		                           "Select A.Account_Head_Code,\n" + 
								   "Decode(A.Dramt,Null,0,Dramt)As Dramt,\n" + 
									"Decode(A.Cramt,Null,0,Cramt)As Cramt,\n" + 
									"decode(a.netTrn,null,0,netTrn)as netTrn,\n" + 
									"B.*,\n" + 
		                           "case when decode(a.netTrn,null,0,netTrn)=b.netTrial then 'Tally' else 'NotTally' end as Dif_march\n" + 
		                           "from\n" + 
		                           "(\n" + 
		                           "Select Account_Head_Code,\n" + 
		                           "Sum(Dramt)As Dramt,Sum(Cramt) As Cramt,\n" + 
		                           "(Sum(Cramt)-Sum(Dramt)) as netTrn\n" + 
		                           " from \n" + 
		                           "(Select Case When Pt.Cr_Dr_Indicator='DR' Then Sum(Pt.Amount) Else 0 End As Dramt,\n" + 
		                           "Case When Pt.Cr_Dr_Indicator='CR' Then Sum(Pt.Amount) Else 0 End As cramt,\n" + 
		                           "pt.ACCOUNTING_UNIT_ID,Pt.Account_Head_Code,pt.cr_dr_indicator\n" + 
		                           "FROM fas_payment_master pm,\n" + 
		                           "  Fas_Payment_Transaction Pt\n" + 
		                           "Where Pt.Accounting_Unit_Id = Pm.Accounting_Unit_Id\n" + 
		                           "And Pt.Cashbook_Year = Pm.Cashbook_Year\n" + 
		                           "And Pt.Cashbook_Month = Pm.Cashbook_Month\n" + 
		                           "And Pt.Voucher_No = Pm.Voucher_No\n" + 
		                           "And Pm.Payment_Status='L'\n" + 
		                           "And Pt.Account_Head_Code In(900108,900109,901001,901002)\n" + 
		                           "And Pt.Cashbook_Year=2012 And Pt.Cashbook_Month=3 \n" + 
		                           "and pt.accounting_unit_id=?\n" + 
		                           "GROUP BY pt.ACCOUNTING_UNIT_ID, Pt.Account_Head_Code, pt.cr_dr_indicator\n" + 
		                           "\n" + 
		                           "\n" + 
		                           "Union All\n" + 
		                           "Select Case When Pt.Cr_Dr_Indicator='DR' Then Sum(Pt.Amount) Else 0 End As Dramt,\n" + 
		                           "Case When Pt.Cr_Dr_Indicator='CR' Then Sum(Pt.Amount) Else 0 End As cramt,\n" + 
		                           "pt.ACCOUNTING_UNIT_ID,Pt.Account_Head_Code,pt.cr_dr_indicator\n" + 
		                           "FROM FAS_JOURNAL_MASTER pm,\n" + 
		                           "  FAS_JOURNAL_TRANSACTION Pt\n" + 
		                           "Where Pt.Accounting_Unit_Id = Pm.Accounting_Unit_Id\n" + 
		                           "And Pt.Cashbook_Year = Pm.Cashbook_Year\n" + 
		                           "And Pt.Cashbook_Month = Pm.Cashbook_Month\n" + 
		                           "And Pt.Voucher_No = Pm.Voucher_No\n" + 
		                           "And Pm.Journal_Status='L'\n" + 
		                           "and pm.created_by_module in ('GJV','LJV')\n" + 
		                           "And Pt.Account_Head_Code In(900108,900109,901001,901002)\n" + 
		                           "And Pt.Cashbook_Year=2012 And Pt.Cashbook_Month=3 \n" + 
		                           "and pt.accounting_unit_id=?\n" + 
		                           "Group By Pt.Accounting_Unit_Id,Pt.Account_Head_Code,Pt.Cr_Dr_Indicator\n" + 
		                           "\n" + 
		                           "Union All\n" + 
		                           "Select Case When Pt.Cr_Dr_Indicator='DR' Then Sum(Pt.Amount) Else 0 End As Dramt,\n" + 
		                           "Case When Pt.Cr_Dr_Indicator='CR' Then Sum(Pt.Amount) Else 0 End As cramt,\n" + 
		                           "pt.ACCOUNTING_UNIT_ID,Pt.Account_Head_Code,pt.cr_dr_indicator\n" + 
		                           "FROM FAS_RECEIPT_MASTER pm,\n" + 
		                           "  FAS_RECEIPT_TRANSACTION Pt\n" + 
		                           "Where Pt.Accounting_Unit_Id = Pm.Accounting_Unit_Id\n" + 
		                           "And Pt.Cashbook_Year = Pm.Cashbook_Year\n" + 
		                           "And Pt.Cashbook_Month = Pm.Cashbook_Month\n" + 
		                           "And Pt.RECEIPT_NO = Pm.RECEIPT_NO\n" + 
		                           "And Pm.Receipt_Status='L'\n" + 
		                           "And Pt.Account_Head_Code In(901001,901002,900108,900109)\n" + 
		                           "And Pt.Cashbook_Year=2012 And Pt.Cashbook_Month=3 \n" + 
		                           "And Pt.Accounting_Unit_Id=?\n" + 
		                           "Group By Pt.Accounting_Unit_Id,Pt.Account_Head_Code,Pt.Cr_Dr_Indicator) GROUP BY Account_Head_Code)a \n" + 
		                           "\n" + 
		                           "full outer Join\n" + 
		                           "(\n" + 
		                           "select Account_Head_Code as hcode,Dramt as drA,cramt as crA,(cramt-Dramt)as netTrial from\n" + 
		                           "(\n" + 
		                           "Select CURRENT_MONTH_DEBIT As Dramt,\n" + 
		                           "CURRENT_MONTH_CREDIT As cramt,\n" + 
		                           "ACCOUNTING_UNIT_ID,Account_Head_Code\n" + 
		                           "FROM FAS_TRIAL_BALANCE \n" + 
		                           "Where Account_Head_Code In(900108,900109,901001,901002)\n" + 
		                           "And Cashbook_Year=2012 And Cashbook_Month=3 \n" + 
		                           "And Accounting_Unit_Id=?))B\n" + 
		                           "On A.Account_Head_Code=B.Hcode\n" ;
		                          // System.out.println("sql_test:::"+sql_test);
		                                       ps = con.prepareStatement(sql_test);
		                                       ps.setInt(1,cmbAcc_UnitCode);
		                                       ps.setInt(2,cmbAcc_UnitCode);
		                                       ps.setInt(3,cmbAcc_UnitCode);
		                                       ps.setInt(4,cmbAcc_UnitCode);
		                                      ResultSet result = ps.executeQuery();                                
		                                       while(result.next()) 
		                                       {
		                                    	   enter++;
		                                    	   System.out.println("whileeeeee");
		                                          ps_one=con.prepareStatement("insert into FAS_TDA_TCA_REG_MAR2012(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,ACCOUNT_HEAD_CODE_TRN,ACCOUNT_HEAD_CODE_TB,TRANSACTION_DR_AMT,TRANSACTION_CR_AMT,TB_DR_AMOUNT,TB_CR_AMOUNT,DIFFERENCE,PARTICULARS,UPDATED_BY_USERID,UPDATED_DATE) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		                                          System.out.println("cmbAcc_UnitCode:::"+cmbAcc_UnitCode);
		                                          ps_one.setInt(1,cmbAcc_UnitCode);
		                                          ps_one.setInt(2,cmbOffice_code);
		                                          ps_one.setInt(3,year);
		                                          ps_one.setInt(4,month);
		                                          System.out.println("lll");
		                                          ps_one.setString(5,result.getString("Account_Head_Code"));
		                                          ps_one.setString(6,result.getString("hcode"));
		                                          ps_one.setString(7,result.getString("Dramt"));
		                                          ps_one.setString(8,result.getString("Cramt"));
		                                          System.out.println("888");
		                                          ps_one.setString(9,result.getString("drA"));
		                                          ps_one.setString(10,result.getString("crA"));
		                                          ps_one.setString(11,result.getString("Dif_march"));
		                                          ps_one.setString(12,txtParticular);
		                                          ps_one.setString(13,update_user);
		                                          ps_one.setTimestamp(14,ts);
		                                         int errcode=ps_one.executeUpdate();
		            		                      if(errcode==0)
		            		                      {         
		            			                          System.out.println("redirect");
		            			                          con.rollback();
		            			                          sendMessage(response,"Accepting Creation Failed ","ok");     
		            			                          
		            		                      }
		            		                      else
		            		                      {
		            		                    	 // try{con.setAutoCommit(true);con.close(); }catch(SQLException sqle){}
		            		                    	  count_inc++;
		            		                      }
		            		                   if(count_inc>0)
		            		                   {
		            		                	   System.out.println("b4 commit");
		     	        						  con.commit();
		     	        						  sendMessage(response,"TDA/TCA has been updated successfully For March2012 Regular","ok");
		            		                   }
		                                         
		                                       }
		                                       if(enter==0)
		                                       {
		                                    	   System.out.println("no record defaultly added");
			                                          ps_one=con.prepareStatement("insert into FAS_TDA_TCA_REG_MAR2012(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,ACCOUNT_HEAD_CODE_TRN,ACCOUNT_HEAD_CODE_TB,TRANSACTION_DR_AMT,TRANSACTION_CR_AMT,TB_DR_AMOUNT,TB_CR_AMOUNT,DIFFERENCE,PARTICULARS,UPDATED_BY_USERID,UPDATED_DATE) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			                                          System.out.println("cmbAcc_UnitCode:::"+cmbAcc_UnitCode);
			                                          ps_one.setInt(1,cmbAcc_UnitCode);
			                                          ps_one.setInt(2,cmbOffice_code);
			                                          ps_one.setInt(3,year);
			                                          ps_one.setInt(4,month);
			                                          ps_one.setString(5,"900108");
			                                          ps_one.setString(6,"900108");
			                                          ps_one.setString(7,"0");
			                                          ps_one.setString(8,"0");
			                                          System.out.println("all zero");
			                                          ps_one.setString(9,"0");
			                                          ps_one.setString(10,"0");
			                                          ps_one.setString(11,"Tally");
			                                          ps_one.setString(12,txtParticular);
			                                          ps_one.setString(13,update_user);
			                                          ps_one.setTimestamp(14,ts);
			                                         int errcode_def=ps_one.executeUpdate();
			                                         if(errcode_def==0)
			            		                      {         
			            			                          System.out.println("redirect");
			            			                          con.rollback();
			            			                          sendMessage(response,"Accepting Creation Failed ","ok");     
			            			                          
			            		                      }
			            		                      else
			            		                      {
			            		                    	  System.out.println("b4 commit");
				     	        						  con.commit();
				     	        						  sendMessage(response,"TDA/TCA has been updated successfully For March2012 Regular","ok");
			            		                      }
		                                       }
		                                       
		                               }
		                         catch(Exception e) 
		                               {
		                                       System.out.println("Exception in add ===> "+e);  
		                                       
		                                        
		                               }
				        }
				             else  if(txtoption.equalsIgnoreCase("ms"))
				             {
				            	 year=2012;
				            	 month=3;
				            	 try{
				            	 ps=con.prepareStatement("delete from FAS_TDA_TCA_SUP_2012 where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=?");
				            	 ps.setInt(1,cmbAcc_UnitCode);
                                 ps.setInt(2,cmbOffice_code);
                                 ps.setInt(3,year);
                                 ps.setInt(4,month);
                                 ps.executeUpdate();
				            	 }
				            	 catch(Exception e)
				            	 {
				            		 System.out.println("e in delete:"+e);
				            	 }
				         	
				             try
				             {

				            	 String ss="Select C.Account_Head_Code,\n" + 
								 "	Decode(C.Dramt_Supp,Null,0,C.Dramt_Supp)As Dramt_Supp,\n" + 
								 "	Decode(C.Cramt_Supp,Null,0,C.Cramt_Supp)As Cramt_Supp,\n" + 
								 "	Decode(C.Nettrn_Supp,Null,0,C.Nettrn_Supp)As Nettrn_Supp,\n" + 
								 "	D.*,\n" + 
								 "	case when Decode(C.Nettrn_Supp,Null,0,C.Nettrn_Supp)=d.Nettrial_Supp then 'Tally' else 'NotTally' end as diff_supplement \n" + 
	                              "from\n" + 
	                              "(Select Account_Head_Code,\n" + 
	                              "Sum(Dramt)As Dramt_supp,Sum(Cramt) As Cramt_supp,\n" + 
	                              "(Sum(Cramt)-Sum(Dramt)) As Nettrn_supp\n" + 
	                              "from\n" + 
	                              "(Select Case When Pt.Cr_Dr_Indicator='DR' Then Sum(Pt.Amount) Else 0 End As Dramt,\n" + 
	                              "Case When Pt.Cr_Dr_Indicator='CR' Then Sum(Pt.Amount) Else 0 End As cramt,\n" + 
	                              "pt.ACCOUNTING_UNIT_ID,Pt.Account_Head_Code,pt.cr_dr_indicator\n" + 
	                              "FROM FAS_JOURNAL_MASTER pm,\n" + 
	                              "  FAS_JOURNAL_TRANSACTION Pt\n" + 
	                              "Where Pt.Accounting_Unit_Id = Pm.Accounting_Unit_Id and  Pt.ACCOUNTING_FOR_OFFICE_ID = Pm.ACCOUNTING_FOR_OFFICE_ID\n" + 
	                              "And Pt.Cashbook_Year = Pm.Cashbook_Year\n" + 
	                              "And Pt.Cashbook_Month = Pm.Cashbook_Month\n" + 
	                              "And Pt.Voucher_No = Pm.Voucher_No\n" + 
	                              "And Pm.Journal_Status='L'\n" + 
	                              "and pm.created_by_module in ('SJV')\n" + 
	                              "And Pt.Account_Head_Code In(900108,900109,901001,901002)\n" + 
	                              "And Pt.Cashbook_Year=2012 And Pt.Cashbook_Month=3 \n" + 
	                              "And Pt.Accounting_Unit_Id=?\n" + 
	                              "Group By Pt.Accounting_Unit_Id,Pt.Account_Head_Code,Pt.Cr_Dr_Indicator) Group By Account_Head_Code)c\n" + 
	                              "\n" + 
	                              "\n" + 
	                              "full outer Join\n" + 
	                              "(\n" + 
	                              "select Account_Head_Code as hcode_supp,Dramt as drA_supp,cramt as crA_supp,(cramt-Dramt)as netTrial_supp from\n" + 
	                              "(\n" + 
	                              "Select CURRENT_MONTH_DEBIT As Dramt,\n" + 
	                              "CURRENT_MONTH_CREDIT As cramt,\n" + 
	                              "Account_Head_Code\n" + 
	                              "FROM FAS_TRIAL_BALANCE_SUPPLEMENT \n" + 
	                              "Where Account_Head_Code In(900108,900109,901001,901002)\n" + 
	                              "And Cashbook_Year=2012 And Cashbook_Month=3 \n" + 
	                              "And Accounting_Unit_Id=?))D\n" + 
	                              "on c.Account_Head_Code=d.hcode_supp";
	                              System.out.println(ss);
	                              ps = con.prepareStatement(ss);
	                              ps.setInt(1,cmbAcc_UnitCode);
	                              ps.setInt(2,cmbAcc_UnitCode);
		                                      ResultSet result = ps.executeQuery();                                
		                                       while(result.next()) 
		                                       {
		                                    	   enter++;
		                                    	   System.out.println("whileeeeee");
		                                          ps_one=con.prepareStatement("insert into FAS_TDA_TCA_SUP_2012(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,ACCOUNT_HEAD_CODE_TRN,ACCOUNT_HEAD_CODE_TB,TRANSACTION_DR_AMT,TRANSACTION_CR_AMT,TB_DR_AMOUNT,TB_CR_AMOUNT,DIFFERENCE,PARTICULARS,UPDATED_BY_USERID,UPDATED_DATE) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		                                          ps_one.setInt(1,cmbAcc_UnitCode);
		                                          ps_one.setInt(2,cmbOffice_code);
		                                          ps_one.setInt(3,year);
		                                          ps_one.setInt(4,month);
		                                          //System.out.println("lll");
		                                          ps_one.setString(5,result.getString("Account_Head_Code"));
		                                          ps_one.setString(6,result.getString("hcode_supp"));
		                                          ps_one.setString(7,result.getString("Dramt_supp"));
		                                          ps_one.setString(8,result.getString("Cramt_supp"));
		                                         // System.out.println("888");
		                                          ps_one.setString(9,result.getString("drA_supp"));
		                                          ps_one.setString(10,result.getString("crA_supp"));
		                                          ps_one.setString(11,result.getString("diff_supplement"));
		                                          ps_one.setString(12,txtParticular);
		                                          ps_one.setString(13,update_user);
		                                          ps_one.setTimestamp(14,ts);
		                                         int errcode=ps_one.executeUpdate();
		            		                      if(errcode==0)
		            		                      {         
		            			                          System.out.println("redirect");
		            			                          con.rollback();
		            			                          sendMessage(response,"Accepting Creation Failed ","ok");     
		            			                          
		            		                      }
		            		                      else
		            		                      {
		            		                    	 // try{con.setAutoCommit(true);con.close(); }catch(SQLException sqle){}
		            		                    	  count_inc++;
		            		                      }
		            		                   if(count_inc>0)
		            		                   {
		            		                	   System.out.println("b4 commit");
		     	        						  con.commit();
		     	        						  sendMessage(response,"TDA/TCA has been updated successfully For Supplement March","ok");
		            		                   }
		                                         
		                                       }
		                                       if(enter==0)
		                                       {
		                                    	   System.out.println("no record defaultly added");
			                                          ps_one=con.prepareStatement("insert into FAS_TDA_TCA_SUP_2012(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,ACCOUNT_HEAD_CODE_TRN,ACCOUNT_HEAD_CODE_TB,TRANSACTION_DR_AMT,TRANSACTION_CR_AMT,TB_DR_AMOUNT,TB_CR_AMOUNT,DIFFERENCE,PARTICULARS,UPDATED_BY_USERID,UPDATED_DATE) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			                                          System.out.println("cmbAcc_UnitCode:::"+cmbAcc_UnitCode);
			                                          ps_one.setInt(1,cmbAcc_UnitCode);
			                                          ps_one.setInt(2,cmbOffice_code);
			                                          ps_one.setInt(3,year);
			                                          ps_one.setInt(4,month);
			                                          ps_one.setString(5,"900108");
			                                          ps_one.setString(6,"900108");
			                                          ps_one.setString(7,"0");
			                                          ps_one.setString(8,"0");
			                                          System.out.println("all zero");
			                                          ps_one.setString(9,"0");
			                                          ps_one.setString(10,"0");
			                                          ps_one.setString(11,"Tally");
			                                          ps_one.setString(12,txtParticular);
			                                          ps_one.setString(13,update_user);
			                                          ps_one.setTimestamp(14,ts);
			                                         int errcode_def=ps_one.executeUpdate();
			                                         if(errcode_def==0)
			            		                      {         
			            			                          System.out.println("redirect");
			            			                          con.rollback();
			            			                          sendMessage(response,"Accepting Creation Failed ","ok");     
			            			                          
			            		                      }
			            		                      else
			            		                      {
			            		                    	  System.out.println("b4 commit");
				     	        						  con.commit();
				     	        						  sendMessage(response,"TDA/TCA has been updated successfully For March2012 Supplement","ok");
			            		                      }
		                                       }
		                                       
		                               }
		                         catch(Exception e) 
		                               {
		                                       System.out.println("Exception in add ===> "+e);  
		                                       
		                                        
		                               }
				        }
				             else  if(txtoption.equalsIgnoreCase("ar"))
				             {
				            	 year=2012;
				            	 month=4;
				            	 try{
				            	 ps=con.prepareStatement("delete from FAS_TDA_TCA_REG_APR2012 where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=?");
				            	 ps.setInt(1,cmbAcc_UnitCode);
                                 ps.setInt(2,cmbOffice_code);
                                 ps.setInt(3,year);
                                 ps.setInt(4,month);
                                 ps.executeUpdate();
				            	 }
				            	 catch(Exception e)
				            	 {
				            		 System.out.println("e in delete:"+e);
				            	 }
				         	
				             try
				             {

				            	 String tt="\n" + 
				            	 " Select A.Account_Head_Code,\n" + 
	                             " Decode(A.Dramt,Null,0,Dramt)As Dramt,\n" + 
	                              " Decode(A.Cramt,Null,0,Cramt)As Cramt,\n" + 
	                              " decode(a.netTrn,null,0,netTrn)as netTrn,\n" + 
	                              " B.*,\n" + 
	                              " case when decode(a.netTrn,null,0,netTrn)=b.netTrial then 'Tally' else 'NotTally' end as Dif_april\n" + 
	                              "from\n" + 
	                              "(\n" + 
	                              "Select Account_Head_Code,\n" + 
	                              "Sum(Dramt)As Dramt,Sum(Cramt) As Cramt,\n" + 
	                              "(Sum(Cramt)-Sum(Dramt)) as netTrn\n" + 
	                              " from \n" + 
	                              "(Select Case When Pt.Cr_Dr_Indicator='DR' Then Sum(Pt.Amount) Else 0 End As Dramt,\n" + 
	                              "Case When Pt.Cr_Dr_Indicator='CR' Then Sum(Pt.Amount) Else 0 End As cramt,\n" + 
	                              "pt.ACCOUNTING_UNIT_ID,Pt.Account_Head_Code,pt.cr_dr_indicator\n" + 
	                              "FROM fas_payment_master pm,\n" + 
	                              "  Fas_Payment_Transaction Pt\n" + 
	                              "Where Pt.Accounting_Unit_Id = Pm.Accounting_Unit_Id\n" + 
	                              "And Pt.Cashbook_Year = Pm.Cashbook_Year\n" + 
	                              "And Pt.Cashbook_Month = Pm.Cashbook_Month\n" + 
	                              "And Pt.Voucher_No = Pm.Voucher_No\n" + 
	                              "And Pm.Payment_Status='L'\n" + 
	                              "And Pt.Account_Head_Code In(900108,900109,901001,901002)\n" + 
	                              "And Pt.Cashbook_Year=2012 And Pt.Cashbook_Month=4\n" + 
	                              "and pt.accounting_unit_id=?\n" + 
	                              "GROUP BY pt.ACCOUNTING_UNIT_ID, Pt.Account_Head_Code, pt.cr_dr_indicator\n" + 
	                              "\n" + 
	                              "\n" + 
	                              "Union All\n" + 
	                              "Select Case When Pt.Cr_Dr_Indicator='DR' Then Sum(Pt.Amount) Else 0 End As Dramt,\n" + 
	                              "Case When Pt.Cr_Dr_Indicator='CR' Then Sum(Pt.Amount) Else 0 End As cramt,\n" + 
	                              "pt.ACCOUNTING_UNIT_ID,Pt.Account_Head_Code,pt.cr_dr_indicator\n" + 
	                              "FROM FAS_JOURNAL_MASTER pm,\n" + 
	                              "  FAS_JOURNAL_TRANSACTION Pt\n" + 
	                              "Where Pt.Accounting_Unit_Id = Pm.Accounting_Unit_Id\n" + 
	                              "And Pt.Cashbook_Year = Pm.Cashbook_Year\n" + 
	                              "And Pt.Cashbook_Month = Pm.Cashbook_Month\n" + 
	                              "And Pt.Voucher_No = Pm.Voucher_No\n" + 
	                              "And Pm.Journal_Status='L'\n" + 
	                              "and pm.created_by_module in ('GJV','LJV')\n" + 
	                              "And Pt.Account_Head_Code In(900108,900109,901001,901002)\n" + 
	                              "And Pt.Cashbook_Year=2012 And Pt.Cashbook_Month=4 \n" + 
	                              "and pt.accounting_unit_id=?\n" + 
	                              "Group By Pt.Accounting_Unit_Id,Pt.Account_Head_Code,Pt.Cr_Dr_Indicator\n" + 
	                              "\n" + 
	                              "Union All\n" + 
	                              "Select Case When Pt.Cr_Dr_Indicator='DR' Then Sum(Pt.Amount) Else 0 End As Dramt,\n" + 
	                              "Case When Pt.Cr_Dr_Indicator='CR' Then Sum(Pt.Amount) Else 0 End As cramt,\n" + 
	                              "pt.ACCOUNTING_UNIT_ID,Pt.Account_Head_Code,pt.cr_dr_indicator\n" + 
	                              "FROM FAS_RECEIPT_MASTER pm,\n" + 
	                              "  FAS_RECEIPT_TRANSACTION Pt\n" + 
	                              "Where Pt.Accounting_Unit_Id = Pm.Accounting_Unit_Id\n" + 
	                              "And Pt.Cashbook_Year = Pm.Cashbook_Year\n" + 
	                              "And Pt.Cashbook_Month = Pm.Cashbook_Month\n" + 
	                              "And Pt.RECEIPT_NO = Pm.RECEIPT_NO\n" + 
	                              "And Pm.Receipt_Status='L'\n" + 
	                              "And Pt.Account_Head_Code In(901001,901002,900108,900109)\n" + 
	                              "And Pt.Cashbook_Year=2012 And Pt.Cashbook_Month=4\n" + 
	                              "And Pt.Accounting_Unit_Id=?\n" + 
	                              "Group By Pt.Accounting_Unit_Id,Pt.Account_Head_Code,Pt.Cr_Dr_Indicator) GROUP BY Account_Head_Code)a \n" + 
	                              "\n" + 
	                              "full outer Join\n" + 
	                              "(\n" + 
	                              "select Account_Head_Code as hcode,Dramt as drA,cramt as crA,(cramt-Dramt)as netTrial from\n" + 
	                              "(\n" + 
	                              "Select CURRENT_MONTH_DEBIT As Dramt,\n" + 
	                              "CURRENT_MONTH_CREDIT As cramt,\n" + 
	                              "ACCOUNTING_UNIT_ID,Account_Head_Code\n" + 
	                              "FROM FAS_TRIAL_BALANCE \n" + 
	                              "Where Account_Head_Code In(900108,900109,901001,901002)\n" + 
	                              "And Cashbook_Year=2012 And Cashbook_Month=4\n" + 
	                              "And Accounting_Unit_Id=?))B\n" + 
	                              "On A.Account_Head_Code=B.Hcode\n" + 
	                              "\n" + 
	                              "\n";
	                              System.out.println("tt:::"+tt);
	                                          ps = con.prepareStatement(tt);
	                                          ps.setInt(1,cmbAcc_UnitCode);
	                                          ps.setInt(2,cmbAcc_UnitCode);
	                                          ps.setInt(3,cmbAcc_UnitCode);
	                                          ps.setInt(4,cmbAcc_UnitCode);
		                                      ResultSet result = ps.executeQuery();                                
		                                       while(result.next()) 
		                                       {
		                                    	   enter++;
		                                    	   System.out.println("whileeeeee");
		                                          ps_one=con.prepareStatement("insert into FAS_TDA_TCA_REG_APR2012(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,ACCOUNT_HEAD_CODE_TRN,ACCOUNT_HEAD_CODE_TB,TRANSACTION_DR_AMT,TRANSACTION_CR_AMT,TB_DR_AMOUNT,TB_CR_AMOUNT,DIFFERENCE,PARTICULARS,UPDATED_BY_USERID,UPDATED_DATE) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		                                          ps_one.setInt(1,cmbAcc_UnitCode);
		                                          ps_one.setInt(2,cmbOffice_code);
		                                          ps_one.setInt(3,year);
		                                          ps_one.setInt(4,month);
		                                          //System.out.println("lll");
		                                          ps_one.setString(5,result.getString("Account_Head_Code"));
		                                          ps_one.setString(6,result.getString("hcode"));
		                                          ps_one.setString(7,result.getString("Dramt"));
		                                          ps_one.setString(8,result.getString("Cramt"));
		                                         // System.out.println("888");
		                                          ps_one.setString(9,result.getString("drA"));
		                                          ps_one.setString(10,result.getString("crA"));
		                                          ps_one.setString(11,result.getString("Dif_april"));
		                                          ps_one.setString(12,txtParticular);
		                                          ps_one.setString(13,update_user);
		                                          ps_one.setTimestamp(14,ts);
		                                         int errcode=ps_one.executeUpdate();
		            		                      if(errcode==0)
		            		                      {         
		            			                          System.out.println("redirect");
		            			                          con.rollback();
		            			                          sendMessage(response,"Accepting Creation Failed ","ok");     
		            			                          
		            		                      }
		            		                      else
		            		                      {
		            		                    	 // try{con.setAutoCommit(true);con.close(); }catch(SQLException sqle){}
		            		                    	  count_inc++;
		            		                      }
		            		                   if(count_inc>0)
		            		                   {
		            		                	   System.out.println("b4 commit");
		     	        						  con.commit();
		     	        						  sendMessage(response,"TDA/TCA has been updated successfully For April2012 Regular ","ok");
		            		                   }
		                                         
		                                       }
		                                       if(enter==0)
		                                       {
		                                    	   System.out.println("no record defaultly added");
			                                          ps_one=con.prepareStatement("insert into FAS_TDA_TCA_REG_APR2012(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,ACCOUNT_HEAD_CODE_TRN,ACCOUNT_HEAD_CODE_TB,TRANSACTION_DR_AMT,TRANSACTION_CR_AMT,TB_DR_AMOUNT,TB_CR_AMOUNT,DIFFERENCE,PARTICULARS,UPDATED_BY_USERID,UPDATED_DATE) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			                                          System.out.println("cmbAcc_UnitCode:::"+cmbAcc_UnitCode);
			                                          ps_one.setInt(1,cmbAcc_UnitCode);
			                                          ps_one.setInt(2,cmbOffice_code);
			                                          ps_one.setInt(3,year);
			                                          ps_one.setInt(4,month);
			                                          ps_one.setString(5,"900108");
			                                          ps_one.setString(6,"900108");
			                                          ps_one.setString(7,"0");
			                                          ps_one.setString(8,"0");
			                                          System.out.println("all zero");
			                                          ps_one.setString(9,"0");
			                                          ps_one.setString(10,"0");
			                                          ps_one.setString(11,"Tally");
			                                          ps_one.setString(12,txtParticular);
			                                          ps_one.setString(13,update_user);
			                                          ps_one.setTimestamp(14,ts);
			                                         int errcode_def=ps_one.executeUpdate();
			                                         if(errcode_def==0)
			            		                      {         
			            			                          System.out.println("redirect");
			            			                          con.rollback();
			            			                          sendMessage(response,"Accepting Creation Failed ","ok");     
			            			                          
			            		                      }
			            		                      else
			            		                      {
			            		                    	  System.out.println("b4 commit");
				     	        						  con.commit();
				     	        						  sendMessage(response,"TDA/TCA has been updated successfully For April2012 Regular","ok");
			            		                      }
		                                       }
		                                       
		                               }
		                         catch(Exception e) 
		                               {
		                                       System.out.println("Exception in add ===> "+e);  
		                                       
		                                        
		                               }
				        }
	}
   
}
    private void sendMessage(HttpServletResponse response,String msg,String bType)
    {
        try
        {
                  String url="org/Library/jsps/MessengerOkBack.jsp?message=" + msg + "&button=" + bType;
                  response.sendRedirect(url);
        }
        catch(IOException e)
        {
        }
    }
}
