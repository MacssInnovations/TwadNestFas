package Servlets.FAS.FAS1.Imprest.servlets;

import Servlets.FAS.FAS1.CommonControls.servlets.Com_CashBook1;
import Servlets.FAS.FAS1.ReceiptSystem.servlets.SL_TYPE_CODE_NAME_DETAILS;
import Servlets.FAS.FAS1.ReceiptSystem.servlets.SL_TYPE_CODE_NAME_GENERAL;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class Imprest_Refund_Receipt_Cancel extends HttpServlet 
{
            private String CONTENT_TYPE = "text/xml; charset=windows-1252";
           
            public void init(ServletConfig config) throws ServletException 
            {
                super.init(config);
            }

            public void doPost(HttpServletRequest request, 
                              HttpServletResponse response) throws ServletException, IOException 
            {
                String strCommand="";
                Connection con=null;
                ResultSet rs=null;
                CallableStatement cs=null,cs1=null;
                PreparedStatement ps=null,ps3=null;
                String xml="",txtMode_of_creat="",doc_type="";
                HttpSession session=request.getSession(false);
                int ss=0;
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
                
                try {
                                       ResourceBundle rs1=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
                                       String ConnectionString="";

                                       String strDriver=rs1.getString("Config.DATA_BASE_DRIVER");
                                       String strdsn=rs1.getString("Config.DSN");
                                       String strhostname=rs1.getString("Config.HOST_NAME");
                                       String strportno=rs1.getString("Config.PORT_NUMBER");
                                       String strsid=rs1.getString("Config.SID");
                                       String strdbusername=rs1.getString("Config.USER_NAME");
                                       String strdbpassword=rs1.getString("Config.PASSWORD");
                                       ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection                                       Class.forName(strDriver.trim());
                                       con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
                      }
                      catch(Exception e)
                          {
                             System.out.println("Exception in opening connection :"+e);
                          }
                try {
                
                    strCommand=request.getParameter("Command");
                    System.out.println("assign..here command..."+strCommand);
                }
                
                catch(Exception e) 
                {
                    System.out.println("Exception in assigning..."+e);
                }
                if(strCommand.equalsIgnoreCase("Cancel")) 
                {
                	int cmbMas_SL_Code=0;
                     String CONTENT_TYPE = "text/html; charset=windows-1252";
                     response.setContentType(CONTENT_TYPE);
                    Calendar c;
                    int cmbAcc_UnitCode=0,cmbOffice_code=0,txtCash_Month_hid=0,txtCash_year=0,txtReceipt_No=0;
                    Date txtCrea_date=null;
                    String update_user=(String)session.getAttribute("UserId");
                    long l=System.currentTimeMillis();
                    Timestamp ts=new Timestamp(l);
                    
                    try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
                    catch(NumberFormatException e){System.out.println("exception"+e );}
                    System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);
                    
                    try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
                    catch(NumberFormatException e){System.out.println("exception"+e );}
                    System.out.println("cmbOffice_code "+cmbOffice_code);
                    
                    String[] sd=request.getParameter("txtCrea_date").split("/");
                    c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
                    java.util.Date d=c.getTime();
                    txtCrea_date=new Date(d.getTime());
                    System.out.println("txtCrea_date "+txtCrea_date);
                    
                    System.out.println("b4 getting month and year");
                    try{txtCash_year=Integer.parseInt(sd[2]);}
                                catch(Exception e){System.out.println("exception"+e );}
                                System.out.println("txtCash_year "+txtCash_year);
                                
                                try{txtCash_Month_hid=Integer.parseInt(sd[1]);}
                                catch(Exception e){System.out.println("exception"+e );}
                                System.out.println("txtCash_Month_hid "+txtCash_Month_hid);
                    
                    
                    try{txtReceipt_No=Integer.parseInt(request.getParameter("txtReceipt_No"));}
                    catch(Exception e){System.out.println("exception"+e );}
                    System.out.println("txtReceipt_No "+txtReceipt_No);
                    
                    try{txtMode_of_creat=request.getParameter("cmbAdvance_type");}
                    catch(Exception e){System.out.println("Err in get txtMode_of_creat :: "+e.getMessage());}
                    
                    if(txtMode_of_creat.equals("I"))
                    {
                    	txtMode_of_creat="I";
                    	doc_type="IMPR";
                     
                    }
                    
                    else if(txtMode_of_creat.equals("T"))
                    {
                    	txtMode_of_creat="T";
                    	doc_type="TMPR";
                    }
                   String ss1=request.getParameter("cmbMas_SL_Code");
                   System.out.println("SL Code ::::"+ss1);
                   String ss2=request.getParameter("sl_code_hid");
                   System.out.println("SL Code Hidden **"+ss2);
                   
                    try{
                    	if(ss2.equalsIgnoreCase("null"))
                    	{
                    		cmbMas_SL_Code=0;
                    	}else {
                    	cmbMas_SL_Code=Integer.parseInt(request.getParameter("sl_code_hid"));
                    	}}
                    catch(Exception e){System.out.println("exception in getting SL Code&&"+e );}
                    System.out.println("cmbMas_SL_Code "+cmbMas_SL_Code);
//                    if(txtMode_of_creat.equals("I"))
//                         doc_type="IMPR";
//                    else
//                         doc_type="TMPR"; 
                    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~        
                            
                              /** Get Receipt Creation Date */          
                                String Receipt_Creation_Date=request.getParameter("txtCrea_date");
                          
                              /** Call Com_CashBook Servlet for Calculating Cash Book Month and Year */    
                                Com_CashBook1 cb=new Com_CashBook1();
                              
                              /** Assign Cashbook Year and Month to year_month Variable */
                                String year_month=cb.cb_date(Receipt_Creation_Date).toString();  
                             // System.out.println("year_month -->"+year_month);
                              /** Split Cash Book Year and Month */
                                String []ym=year_month.split("/");
                              
                              /** Assign Year and Month */
                                txtCash_year=Integer.parseInt(ym[0]);
                                txtCash_Month_hid=Integer.parseInt(ym[1]);
                                         
                    //System.out.println(txtCash_year+"---"+txtCash_Month_hid);
                    String sql_del="update FAS_RECEIPT_MASTER set RECEIPT_STATUS='C',UPDATED_BY_USER_ID=?,UPDATED_DATE=? where ACCOUNTING_UNIT_ID=? and " +
                                    " CASHBOOK_YEAR=?  and CASHBOOK_MONTH=?  and RECEIPT_NO=?  ";
                    
                    try
                    {
                    	//System.out.println("tr");
	                    con.clearWarnings();
	                    con.setAutoCommit(false);
	                    //System.out.println("pspps first false"+update_user+"--"+ts+"---"+cmbAcc_UnitCode+""+txtCash_year+"---"+txtCash_Month_hid+"--"+txtReceipt_No);
	                    ps=con.prepareStatement(sql_del);
	                    ps.setString(1,update_user);
	                    ps.setTimestamp(2,ts);
	                    ps.setInt(3,cmbAcc_UnitCode);
	                  //  ps.setInt(4,cmbOffice_code);
	                    ps.setInt(4,txtCash_year); System.out.println(txtCash_year+" "+txtCash_Month_hid+" "+txtReceipt_No );
	                    ps.setInt(5,txtCash_Month_hid);
	                    ps.setInt(6,txtReceipt_No);
	                   int kk= ps.executeUpdate();
	                    System.out.println("kk "+kk);
	                   // System.out.println("pspps ");
                        String txtReferNO_edit="",txtRemak_edit="", txtRefdate="";        
                        Date txtReferDate_edit=null;
                        String radAuth_MC="";
                        int txtAuth_By=0;
                       int pay_vouNo=0,pay_mon=0,pay_yer=0;
                       Date pay_VouDate=null;
                      //  System.out.println("inside try"+sql_del);
                        cs1=con.prepareCall("call FAS_CROSS_REFERENCE_PROC(?::NUMERIC,?,?,?,?,?,?,?,?,?,?::VARCHAR,?,?,?,?,?)") ; 
                        cs1.setInt(1,cmbAcc_UnitCode);
                        cs1.setInt(2,txtCash_year);
                        cs1.setInt(3,txtCash_Month_hid);
                        cs1.setInt(4,txtReceipt_No);
                        cs1.setInt(5,cmbOffice_code);System.out.println(txtCrea_date+" "+doc_type+" "+txtReferNO_edit +" "+txtReferDate_edit+" "+txtRemak_edit+" "+txtAuth_By+" "+radAuth_MC);
                        cs1.setDate(6,txtCrea_date);
                        cs1.setString(7,doc_type);
                        cs1.setString(8,txtReferNO_edit);
                        cs1.setDate(9,txtReferDate_edit);
                        cs1.setString(10,txtRemak_edit);
                        cs1.setInt(11,txtAuth_By);                                                      
                        cs1.setString(12,"insert");
                        cs1.registerOutParameter(13,java.sql.Types.NUMERIC); 
                        cs1.setNull(13,java.sql.Types.NUMERIC);
                        cs1.setString(14,update_user);
                                        cs1.setTimestamp(15,ts);  
                                        cs1.setString(16,radAuth_MC);
                        cs1.execute();                               
                        // insertion into cross reference table
                       // System.out.println("after call proce");
                        int errcode=cs1.getInt(13);
                       System.out.println("errcode >> "+errcode);
                        if(errcode!=0)
                        {   
                          con.rollback();
                          sendMessage(response,"The Receipt Cancellation Failed ","ok");
                          xml=xml+"<flag>failure</flag>";                          
                          return;
                        }
                        else
                        {
					try {
						PreparedStatement ps_strt = con
								.prepareStatement(" SELECT m.VOUCHER_NO ,extract(month from M.PAYMENT_DATE) mn,extract(year from M.PAYMENT_DATE) yr "
										+ " FROM FAS_PAYMENT_TRANSACTION T, "
										+ "  FAS_PAYMENT_MASTER M "
										+ " WHERE M.ACCOUNTING_UNIT_ID          =T.ACCOUNTING_UNIT_ID "
										+ " AND M.ACCOUNTING_FOR_OFFICE_ID      =T.ACCOUNTING_FOR_OFFICE_ID "
										+ " AND M.CASHBOOK_MONTH                =T.CASHBOOK_MONTH "
										+ " AND M.CASHBOOK_YEAR                 =T.CASHBOOK_YEAR "
										+ " AND M.VOUCHER_NO                    =T.VOUCHER_NO "
										+ " AND m.ACCOUNTING_UNIT_ID            = "
										+ cmbAcc_UnitCode
										//+ " AND M.CASHBOOK_YEAR                 = "
										//+ txtCash_year
										//+ " AND M.CASHBOOK_MONTH                = "
										//+ txtCash_Month_hid
										+ " AND (m.VOUCHER_NO ,M.PAYMENT_DATE) IN "
										+ " (SELECT RECEIVABLE_VOUCHER_NO, "
										+ "  RECEIVABLE_VOUCHER_DATE "
										+ " FROM FAS_RECEIPT_MASTER "
										+ " WHERE ACCOUNTING_UNIT_ID= "
										+ cmbAcc_UnitCode
										+ "  AND CASHBOOK_YEAR     = "
										+ txtCash_year
										+ "  AND CASHBOOK_MONTH    = "
										+ txtCash_Month_hid
										+ "  AND RECEIPT_NO          = "
										+ txtReceipt_No
										+ "  ) "
										+ " AND t.SUB_LEDGER_CODE = "
										+ cmbMas_SL_Code
										//+ " AND M.CASHBOOK_YEAR     = "
										//+ txtCash_year
										+ " and m.PAYMENT_STATUS='L' "
										//+ "AND M.CASHBOOK_MONTH    ="
										//+ txtCash_Month_hid
										);
						System.out.println(" SELECT m.VOUCHER_NO ,extract(month from M.PAYMENT_DATE) mn,extract(year from M.PAYMENT_DATE) yr "
										+ " FROM FAS_PAYMENT_TRANSACTION T, "
										+ "  FAS_PAYMENT_MASTER M "
										+ " WHERE M.ACCOUNTING_UNIT_ID          =T.ACCOUNTING_UNIT_ID "
										+ " AND M.ACCOUNTING_FOR_OFFICE_ID      =T.ACCOUNTING_FOR_OFFICE_ID "
										+ " AND M.CASHBOOK_MONTH                =T.CASHBOOK_MONTH "
										+ " AND M.CASHBOOK_YEAR                 =T.CASHBOOK_YEAR "
										+ " AND M.VOUCHER_NO                    =T.VOUCHER_NO "
										+ " AND m.ACCOUNTING_UNIT_ID            = "
										+ cmbAcc_UnitCode
										//+ " AND M.CASHBOOK_YEAR                 = "
										//+ txtCash_year
										//+ " AND M.CASHBOOK_MONTH                = "
										//+ txtCash_Month_hid
										+ " AND (m.VOUCHER_NO ,M.PAYMENT_DATE) IN "
										+ " (SELECT RECEIVABLE_VOUCHER_NO, "
										+ "  RECEIVABLE_VOUCHER_DATE "
										+ " FROM FAS_RECEIPT_MASTER "
										+ " WHERE ACCOUNTING_UNIT_ID= "
										+ cmbAcc_UnitCode
										+ "  AND CASHBOOK_YEAR     = "
										+ txtCash_year
										+ "  AND CASHBOOK_MONTH    = "
										+ txtCash_Month_hid
										+ "  AND RECEIPT_NO          = "
										+ txtReceipt_No
										+ "  ) "
										+ " AND t.SUB_LEDGER_CODE = "
										+ cmbMas_SL_Code
										//+ " AND M.CASHBOOK_YEAR     = "
										//+ txtCash_year
										+ " and m.PAYMENT_STATUS='L' "
										//+ "AND M.CASHBOOK_MONTH    ="
										//+ txtCash_Month_hid
										);
						ResultSet rs_strt = ps_strt.executeQuery();
						if (rs_strt.next()) {
							pay_mon=rs_strt.getInt("mn");
							pay_yer=rs_strt.getInt("yr");
							pay_vouNo = rs_strt.getInt("VOUCHER_NO");
						}
                        	}catch(Exception e){
                        		e.printStackTrace();
                        	}
					System.out.println("pay_VouDate   "+pay_VouDate);
					System.out.println("pay_vouNo   "+pay_vouNo);
                        	System.out.println("update FAS_PAYMENT_TRANSACTION set AMOUNT_FULLY_SPENT=null where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and CASHBOOK_YEAR="+pay_yer+" and CASHBOOK_MONTH="+pay_mon+" and VOUCHER_NO="+pay_vouNo+"  AND SUB_LEDGER_CODE="+cmbMas_SL_Code);
                        	 ps3=con.prepareStatement("update FAS_PAYMENT_TRANSACTION set AMOUNT_FULLY_SPENT=null where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and CASHBOOK_YEAR="+pay_yer+" and CASHBOOK_MONTH="+pay_mon+" and VOUCHER_NO="+pay_vouNo+"  AND SUB_LEDGER_CODE="+cmbMas_SL_Code);
                  			 //ps3.setDate(1, pay_VouDate);
                        	 ss=ps3.executeUpdate();
                        	      	
                        }
                        System.out.println("ss  "+ss);
                        if(ss>0)
                        {
                        	con.commit();
                            sendMessage(response,"The Receipt '"+txtReceipt_No+"' has been Cancelled Successfully ","ok");	
                        }else{
                        	 con.rollback();
                             sendMessage(response,"The Receipt Cancellation Failed ","ok");
                             xml=xml+"<flag>failure</flag>";                          
                             return;
                        	 //con.commit();
                        	 //sendMessage(response,"The Receipt '"+txtReceipt_No+"' has been Cancelled Successfully ","ok");
                        }
                   
                    }
                    catch(Exception e) 
                    {
                        try{con.rollback();}catch(SQLException sqle){System.out.println("Excep"+sqle);}
                        sendMessage(response,"The Receipt Cancellation Failed ","ok");
                        System.out.println("Exception occur due to "+e);
                    }
                    finally
                    {
                        System.out.println("done");
                        try{con.setAutoCommit(true);  }catch(SQLException sqle){System.out.println("Excep"+sqle);}
                    }
                    
                }
            }
            public void doGet(HttpServletRequest request, 
                              HttpServletResponse response) throws ServletException, IOException 
            {
                Connection con=null;
                ResultSet rs=null,rs2=null,rs3=null,rs4=null;
                PreparedStatement ps=null,ps2=null,ps3=null,ps4=null;
                
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
                
                
                try {
                                      ResourceBundle rs1=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
                                      String ConnectionString="";

                                      String strDriver=rs1.getString("Config.DATA_BASE_DRIVER");
                                      String strdsn=rs1.getString("Config.DSN");
                                      String strhostname=rs1.getString("Config.HOST_NAME");
                                      String strportno=rs1.getString("Config.PORT_NUMBER");
                                      String strsid=rs1.getString("Config.SID");
                                      String strdbusername=rs1.getString("Config.USER_NAME");
                                      String strdbpassword=rs1.getString("Config.PASSWORD");
                                      ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection                                      Class.forName(strDriver.trim());
                                      con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
                     }
                     catch(Exception e)
                         {
                            System.out.println("Exception in opening connection :"+e);
                         }
                
                response.setContentType(CONTENT_TYPE);
                response.setHeader("Cache-Control","no-cache");
                PrintWriter out = response.getWriter();
                String strCommand="",txtMode_of_creat="",doc_type="";
                
                try 
                {
                    strCommand=request.getParameter("Command");
                    System.out.println("assign..here command..."+strCommand);
                }
                
                catch(Exception e) 
                {
                    System.out.println("Exception in assigning..."+e);
                }
                int cmbAcc_UnitCode=0,cmbOffice_code=0;
                   Date txtCrea_date=null;
                   try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
                   catch(NumberFormatException e){System.out.println("exception"+e );}
                   System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);
                   
                   try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
                   catch(NumberFormatException e){System.out.println("exception"+e );}
                   System.out.println("cmbOffice_code "+cmbOffice_code);
                   
                   try{txtMode_of_creat=request.getParameter("txtMode_of_creat");}
                   catch(Exception e){System.out.println("Err in get txtMode_of_creat :: "+e.getMessage());}
                
//                   if(txtMode_of_creat.equals("I"))
//                        doc_type="IMPR";
//                   else
//                        doc_type="TMPR"; 
                   if(txtMode_of_creat.equals("I"))
                   {
                   	txtMode_of_creat="I";
                   	doc_type="IMPR";
                    
                   }
                   
                   else if(txtMode_of_creat.equals("T"))
                   {
                   	txtMode_of_creat="T";
                   	doc_type="TMPR";
                   }
                   
                
                if(strCommand.equalsIgnoreCase("load_Receipt_No")) 
                {
                     String CONTENT_TYPE = "text/xml; charset=windows-1252";
                     response.setContentType(CONTENT_TYPE);
                     Calendar c;
                     String xml="";
                      xml="<response><command>load_Receipt_No</command>";
                     String[] sd=request.getParameter("txtCrea_date").split("/");
                     c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
                     java.util.Date d=c.getTime();
                     txtCrea_date=new Date(d.getTime());
                     System.out.println("txtCrea_date "+txtCrea_date);
                     
                            try {
                                    ps=con.prepareStatement("select i.RECEIPT_NO from FAS_RECEIPT_MASTER i,FAS_CROSS_REFERENCE c where " +
                                    " i.ACCOUNTING_UNIT_ID=?  and i.RECEIPT_DATE=? and i.RECEIPT_STATUS!='C' and MODE_OF_CREATION =? and CREATED_BY_MODULE in ('CR','BR') " +
                                    " and i.ACCOUNTING_UNIT_ID=c.ACCOUNTING_UNIT_ID and i.ACCOUNTING_FOR_OFFICE_ID=c.ACCOUNTING_FOR_OFFICE_ID " +
                                    " and i.CASHBOOK_YEAR=c.CASHBOOK_YEAR and i.CASHBOOK_MONTH=c.CASHBOOK_MONTH and i.RECEIPT_NO=c.VOUCHER_NO " +
                                    " and c.CHANGE_NO=0 and c.AUTHORIZED_TO='C' and DOC_TYPE=? ");
                                    ps.setInt(1,cmbAcc_UnitCode);
                                  //  ps.setInt(2,cmbOffice_code);
                                    ps.setDate(2,txtCrea_date);
                                    ps.setString(3,txtMode_of_creat);
                                    ps.setString(4,doc_type);
                                    rs=ps.executeQuery();
                                    
                                    int count=0;
                                    while(rs.next())
                                    {
                                    
                                    xml=xml+"<Rec_No>"+rs.getInt("RECEIPT_NO")+
                                    "</Rec_No>";
                                        count++;
                                    }
                                    if(count==0)
                                        xml=xml+"<flag>failure</flag>";
                                    else 
                                        xml=xml+"<flag>success</flag>";
                                System.out.println("count  "+count);
                                ps.close();
                                rs.close();
                                }
                                catch(Exception e)
                                {
                                System.out.println("catch..HERE.in load head code."+e);
                                    xml=xml+"<flag>failure</flag>";
                                }
                                xml=xml+"</response>";
                                System.out.println(xml);
                                out.println(xml);
                                
                 }
                 
                 else if(strCommand.equalsIgnoreCase("load_Receipt_Details")) 
                 {
                        String CONTENT_TYPE = "text/xml; charset=windows-1252";
                        response.setContentType(CONTENT_TYPE);
                        Calendar c;
                        String xml="";
                         xml="<response><command>load_Receipt_Details</command>";
                         int txtReceipt_No=0;
                        // Date txtCrea_date;
                         
                        try{txtReceipt_No=Integer.parseInt(request.getParameter("txtReceipt_No"));}
                        catch(NumberFormatException e){System.out.println("exception"+e );}
                        System.out.println("txtReceipt_No "+txtReceipt_No);
                        
                        String[] sd=request.getParameter("txtCrea_date").split("/");
                        c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
                        java.util.Date d=c.getTime();
                        txtCrea_date=new Date(d.getTime());
                        System.out.println("txtCrea_date "+txtCrea_date);
                               try {
                                       ps=con.prepareStatement("select ACCOUNT_HEAD_CODE,CASHBOOK_YEAR,CASHBOOK_MONTH,BANK_ID ,BRANCH_ID ,ACCOUNT_NO ,REF_NO," +
                                       " to_char(REF_DATE,'DD/MM/YYYY')as ref_date,trim(to_char(TOTAL_AMOUNT,'99999999999999.99')) as TOTAL_AMOUNT,TOTAL_TRN_RECORDS,RECEIVED_FROM,REMARKS,SUB_LEDGER_TYPE_CODE," +
                                       " SUB_LEDGER_CODE  from  FAS_RECEIPT_MASTER where ACCOUNTING_UNIT_ID=? and RECEIPT_DATE=? " +
                                       " and MODE_OF_CREATION=? and CREATED_BY_MODULE in ('CR','BR') and RECEIPT_NO=?");
                                       ps.setInt(1,cmbAcc_UnitCode);
                                     //  ps.setInt(2,cmbOffice_code);
                                       ps.setDate(2,txtCrea_date);
                                       ps.setString(3,txtMode_of_creat);
                                       ps.setInt(4,txtReceipt_No);
                                       rs=ps.executeQuery();
                                       if(rs.next())
		                               {
		                                       xml=xml+"<flag>success</flag>";
		                                       xml=xml+"<MasHeadCode>"+rs.getString("ACCOUNT_HEAD_CODE").trim()+"</MasHeadCode>";
		
		                            
		                                       ps3=con.prepareStatement("select bk.BANK_NAME ||'-' ||br.BRANCH_NAME || '-' ||coalesce (br.CITY_TOWN_NAME,'') as bankNAME" +
		                                       " from FAS_MST_BANKS bk,FAS_MST_BANK_BRANCHES br where br.BANK_ID=? and br.BRANCH_ID=? and br.BANK_ID=bk.BANK_ID");
		                                       ps3.setInt(1,rs.getInt("BANK_ID"));
		                                       ps3.setInt(2,rs.getInt("BRANCH_ID"));
		                                       rs3=ps3.executeQuery();
		                                       int i=0;
		                                       if(rs3.next())
		                                       {
		                                    	   xml=xml+"<bk_name>"+rs3.getString("bankNAME")+"</bk_name>";
		                                    	   i++;
		                                       }   
		                                       rs3.close();
		                                       ps3.close();
		                                       
		                                       if(i==0)
		                                       {
		                                    	   xml=xml+"<bk_name>--</bk_name>";
		                                       }
		                                       
		                                       xml=xml+"<Ref_No>"+rs.getString("REF_NO")+
		                                       "</Ref_No><Ref_Date>"+rs.getString("ref_date")+
		                                       
		                                       "</Ref_Date><accNo>"+rs.getString("ACCOUNT_NO")+
		                                       "</accNo>";
		                                       
		                                       
		                                       xml=xml+"<bk_id>"+rs.getInt("BANK_ID")+
		                                       "</bk_id><br_id>"+rs.getInt("BRANCH_ID")+
		                                       "</br_id><Total_amt>"+rs.getString("TOTAL_AMOUNT")+
		                                       "</Total_amt><No_TRN_Rec>"+rs.getInt("TOTAL_TRN_RECORDS")+
		                                       "</No_TRN_Rec><Rec_From>"+rs.getString("RECEIVED_FROM")+
		                                       "</Rec_From><Remak>"+rs.getString("REMARKS")+
		                                       "</Remak><Mas_SL_type>"+rs.getInt("SUB_LEDGER_TYPE_CODE")+
		                                       "</Mas_SL_type><Mas_SL_code>"+rs.getInt("SUB_LEDGER_CODE")+
		                                       "</Mas_SL_code>";
		                                  }
                                       
                                         System.out.println("1. xml-->"+xml);
                                         
                                         if(rs.getInt("SUB_LEDGER_TYPE_CODE")!=0)
                                         {
                                             SL_TYPE_CODE_NAME_GENERAL obj_gen=new SL_TYPE_CODE_NAME_GENERAL();
                                             ResultSet rs_get=obj_gen.getResult_General(cmbAcc_UnitCode,cmbOffice_code,rs.getInt("SUB_LEDGER_TYPE_CODE"),rs.getInt("SUB_LEDGER_CODE"),0);
                                             if(rs_get!=null)
                                             {
                                                 while(rs_get.next())
                                                 {
                                                     xml=xml+"<cid>"+rs_get.getInt("cid")+"</cid><cname>"+rs_get.getString("cname")+"</cname>";
                                                 }
                                                 rs_get.close();
                                             }
                                             else
                                                 System.out.println("null result set");
                                         } 
                                                 
                                       
                                       System.out.println("2. xml-->"+xml);
                                         
                                       ps2=con.prepareStatement("select ACCOUNT_HEAD_CODE ,CR_DR_INDICATOR ,SUB_LEDGER_TYPE_CODE ,SUB_LEDGER_CODE " +
                                                                 ",CHEQUE_OR_DD ,CHEQUE_DD_NO ,to_char(CHEQUE_DD_DATE,'DD/MM/YYYY') as cheq_dd_date ,BANK_NAME ," +
                                                                 "DRAWEE_BRANCH ,BANK_MICR_CODE, RECEIVED_FROM ,trim(to_char(AMOUNT,'99999999999999.99')) as  AMOUNT, PARTICULARS  from FAS_RECEIPT_TRANSACTION where ACCOUNTING_UNIT_ID=? and " +
                                                                  " CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and RECEIPT_NO=?");
                                       ps2.setInt(1,cmbAcc_UnitCode);
                                    //   ps2.setInt(2,cmbOffice_code);
                                       ps2.setString(2,rs.getString("CASHBOOK_YEAR"));
                                       ps2.setInt(3,rs.getInt("CASHBOOK_MONTH"));
                                       ps2.setInt(4,txtReceipt_No);
                                       rs2=ps2.executeQuery();
                                       while(rs2.next()) 
		                               {
		                                       xml=xml+"<AHcode>"+rs2.getInt("ACCOUNT_HEAD_CODE")+"</AHcode>";
		                                       ps3=con.prepareStatement("select ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_CODE=?");
		                                       ps3.setInt(1,rs2.getInt("ACCOUNT_HEAD_CODE"));
		                                       rs3=ps3.executeQuery();
		                                       if(rs3.next())
		                                       xml=xml+"<AHdesc>"+rs3.getString("ACCOUNT_HEAD_DESC")+"</AHdesc>";
		                                       ps3.close();
		                                       rs3.close();
		                                       xml=xml+"<CR_DR_ind>"+rs2.getString("CR_DR_INDICATOR")+
		                                       "</CR_DR_ind><SL_Type>"+rs2.getInt("SUB_LEDGER_TYPE_CODE")+
		                                       "</SL_Type>";
		                                       
		                                       if(rs2.getInt("SUB_LEDGER_TYPE_CODE")!=0)
			                                   {
			                                       System.out.println("take SL DESC");
			                                       ps3=con.prepareStatement("select SUB_LEDGER_TYPE_DESC from COM_MST_SL_TYPES where SUB_LEDGER_TYPE_CODE=?");
			                                       ps3.setInt(1,rs2.getInt("SUB_LEDGER_TYPE_CODE"));
			                                       rs3=ps3.executeQuery();
			                                       if(rs3.next())
			                                       xml=xml+"<SL_Desc>"+rs3.getString("SUB_LEDGER_TYPE_DESC")+"</SL_Desc>";
			                                   }
		                                       else
		                                       {
		                                          xml=xml+"<SL_Desc>"+null+"</SL_Desc>";   // it also return null value
		                                           System.out.println("else part  23");
		                                       }
		                                       
		                                       rs3.close();                            
		                                       ps3.close();
		                                       
		                                       xml=xml+"<SL_Code>"+rs2.getInt("SUB_LEDGER_CODE")+"</SL_Code>";
		                                       
		                                           if(rs2.getInt("SUB_LEDGER_TYPE_CODE")!=0)
		                                           {
		                                               SL_TYPE_CODE_NAME_DETAILS obj_det=new SL_TYPE_CODE_NAME_DETAILS();
		                                               ResultSet rs_det=obj_det.getResult_Details(cmbAcc_UnitCode,cmbOffice_code,rs2.getInt("SUB_LEDGER_TYPE_CODE"),rs2.getString("SUB_LEDGER_CODE"),0);
		                                               if(rs_det!=null)
		                                               {
		                                                   if(rs_det.next())
		                                                   {
		                                                       System.out.println(rs_det.getString("cname"));
		                                                       xml=xml+"<desc_type>"+rs_det.getString("cname")+"</desc_type>";
		                                                   }
		                                                   rs_det.close();
		                                               }
		                                               else
		                                                   System.out.println("null result set");
		                                           }
		                                           else
		                                           {
		                                               xml=xml+"<desc_type>"+null+"</desc_type>";  
		                                           }
		                                               
		                                           xml=xml+"<che_or_DD>"+rs2.getString("CHEQUE_OR_DD")+ "</che_or_DD>" +
		                                           "<che_DD_no>"+rs2.getString("CHEQUE_DD_NO")+"</che_DD_no>" +
		                                           "<che_DD_date>"+rs2.getString("cheq_dd_date")+"</che_DD_date>" +
		                                           "<bank_na>"+rs2.getString("BANK_NAME")+"</bank_na>" +
		                                           "<drawee>"+rs2.getString("DRAWEE_BRANCH")+"</drawee>" +
		                                           "<micr>"+rs2.getString("BANK_MICR_CODE")+"</micr>"+
		                                           "<sub_rec_frm>"+rs2.getString("RECEIVED_FROM")+
		                                           "</sub_rec_frm><sub_amount>"+rs2.getString("AMOUNT")+
		                                           "</sub_amount><sub_part>"+rs2.getString("PARTICULARS")+"</sub_part>";
		                                 }
                                       
                                       System.out.println("3. xml-->"+xml);
                                       
                                   }
                                   catch(Exception e)
                                   {
                                   System.out.println("catch..HERE.in failure to retrieve."+e);
                                       xml=xml+"<flag>failure</flag>";
                                   }
                                   xml=xml+"</response>";
                                   System.out.println(xml);
                                   out.println(xml);
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
                System.out.println("Excep"+e);
                }
            }


}