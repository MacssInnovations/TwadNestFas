package Servlets.FAS.FAS1.BillVerification.servlets;

//import Servlets.Security.classes.UserProfile;

import java.io.*;
import java.sql.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import javax.servlet.http.*;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

public class Cheqmemo_liabjournal extends HttpServlet
{
  //private static final String CONTENT_TYPE="text/xml; charset=windows-1252";
	public void init(ServletConfig config)throws ServletException
	{ 
		super.init(config);
	}
	public void doGet(HttpServletRequest request,HttpServletResponse response)throws IOException,ServletException		
 	{
		String CONTENT_TYPE="text/xml; charset=windows-1252";
                response.setContentType(CONTENT_TYPE);
                response.setHeader("Cache-Control","no-cache");
                System.out.println("Welcome to Cheque Memo Liability Servlet");
		String cmnd="";
		String xml="";
                String user_id;
                user_id = "";int CB_year=0;int CB_month=0;
                //String emp_name="";
                int count=0;
                String update_user="";
                HttpSession session=null;
                Timestamp ts=null;
                int acc_unit_id=0;int acc_off_id=0;int subled_type=0;int subled_code=0;
                PrintWriter pw=response.getWriter();
                
                /*********** connection establishment****************/
                Connection con=null;
                ResultSet rs2,rs3;rs2=null;rs3=null;
                PreparedStatement ps2,ps3;ps2=null;ps3=null;
                xml="<response>";
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
                                             
                            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection                                Class.forName(strDriver.trim());
                                con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
                }
                catch(Exception e)
                {
                        System.out.println("Exception in connection...."+e);
                } 
                         try
                      {
                          session=request.getSession(false);
                          if(session==null)
                          {
                              System.out.println(request.getContextPath()+"/index.jsp");
                              response.sendRedirect(request.getContextPath()+"/index.jsp");
                              return;
                          }
                          System.out.println(session);
                      } 
                      catch(Exception e)
                      {
                            System.out.println("Redirect Error :"+e);
                      }
                        String userid=(String)session.getAttribute("UserId");
                        System.out.println("session id is:"+userid);
                        update_user=(String)session.getAttribute("UserId");
                        long l=System.currentTimeMillis();
                        ts=new Timestamp(l);           
   
                      /****************** getting the values from Button Pressed***********/
                try
                {
                          cmnd =  request.getParameter("command");     
                          System.out.println("Command passed via the button pressed : " + cmnd);
                }
                  catch(Exception e3)
                  {
                    e3.printStackTrace();
                  }
                  /*****************Getting the values from jsp page ***************/
                    if(cmnd.equalsIgnoreCase("loadcheqmemo")) 
                    {
                        xml=xml+"<command>loadcheqmemo</command>";
                        try
                            {             
                                String sqlload="select CHEQUE_MEMO_TYPE_CODE,CHEQUE_MEMO_DESC from FAS_CHEQUE_MEMO_TYPES_MST where STATUS='L' order by CHEQUE_MEMO_TYPE_CODE";
                                ps2 = con.prepareStatement(sqlload);
                                rs2=ps2.executeQuery();
                                while(rs2.next())
                                {
                                    xml=xml+"<option><cheqmemo_type_code>"+rs2.getInt("CHEQUE_MEMO_TYPE_CODE")+"</cheqmemo_type_code>";
                                    xml=xml+"<cheqmemo_type_desc>"+rs2.getString("CHEQUE_MEMO_DESC")+"</cheqmemo_type_desc></option>";
                                    count++;
                                }
                                if(count>0)
                                {
                                    xml=xml+"<flag>success</flag>"; 
                                }
                                else
                                {
                                    xml=xml+"<flag>nodata</flag>";    
                                }
                                 ps2.close();
                                 rs2.close();
                             } //try close
                              catch(Exception e)
                              {
                                                xml=xml+"<flag>"+e.getMessage()+"</flag>";
                                                System.out.println(e);
                               }
                    }
                    else if(cmnd.equalsIgnoreCase("loadpayeetype")) 
                    {
                        xml=xml+"<command>loadPayeeType</command>";
                        try
                            {             
                                int cheqmemo_type=Integer.parseInt(request.getParameter("cheqmemo_type1"));
                                System.out.println("cheqmemo_type1 ......"+cheqmemo_type);
                                String sqlload="select ms.payee_type_code,ms.payee_type_desc from FAS_CHEQUEMEMO_PAYEE_TYPES_MST fs,FAS_PAYEE_TYPES_MST ms where fs.cheque_memo_type_code=? and fs.payee_type_code=ms.payee_type_code";
                                System.out.println("query ****"+sqlload);
                                ps2 = con.prepareStatement(sqlload);
                                ps2.setInt(1,cheqmemo_type);
                                rs2=ps2.executeQuery();
                                
                               while(rs2.next())
                                {
                                    xml=xml+"<option><payee_type_code>"+rs2.getInt("payee_type_code")+"</payee_type_code>";
                                    xml=xml+"<payee_type_desc>"+rs2.getString("payee_type_desc")+"</payee_type_desc></option>";
                                    count++;
                                }
                                if(count>0)
                                {
                                    xml=xml+"<flag>success</flag>"; 
                                }
                                else
                                {
                                    xml=xml+"<flag>nodata</flag>";    
                                }
                                 ps2.close();
                                 rs2.close();
                             } //try close
                              catch(Exception e)
                              {
                                                xml=xml+"<flag>"+e.getMessage()+"</flag>";
                                                System.out.println(e);
                               }
                    }
                    else if(cmnd.equalsIgnoreCase("loadDetails")) 
                    {
                        xml=xml+"<command>loadDetails</command>";
                        try
                        {             
                                    acc_unit_id=Integer.parseInt(request.getParameter("acc_unitid1"));
                                    System.out.println("acc......."+acc_unit_id);
                                    acc_off_id=Integer.parseInt(request.getParameter("acc_offid1"));
                                    System.out.println("office........"+acc_off_id);
                                    CB_year=Integer.parseInt(request.getParameter("CB_year1"));
                                    System.out.println("Cash Book Year........."+CB_year);
                                    CB_month=Integer.parseInt(request.getParameter("CB_month1"));
                                    System.out.println("Cash Book Year........."+CB_month);
                                     subled_type=Integer.parseInt(request.getParameter("subled_type1"));
                                     System.out.println("Sub Ledger Type........."+subled_type);
                                     subled_code=Integer.parseInt(request.getParameter("subled_code1"));
                                     System.out.println("Sub Ledger Code........."+subled_code);
                                    
                                    String sqlload="    select  " + 
                                    "	jrnl_mst.VOUCHER_NO, to_char(jrnl_mst.VOUCHER_DATE,'DD/MM/YYYY') as VOUCHER_DATE,   " + 
                                    "	jrnl_trn.ACCOUNT_HEAD_CODE,jrnl_trn.SUB_LEDGER_TYPE_CODE,jrnl_trn.SUB_LEDGER_CODE,  " + 
                                    "	jrnl_trn.CR_DR_INDICATOR,   " + 
                                    "	trim(to_char(jrnl_trn.AMOUNT,'99999999999999.99')) as AMOUNT,   " + 
                                    "   jrnl_trn.PARTICULARS,   " + 
                                    "	acc_head.ACCOUNT_HEAD_DESC  " + 
                                    "   from    " + 
                                    "	FAS_JOURNAL_MASTER jrnl_mst,    " + 
                                    "	FAS_JOURNAL_TRANSACTION jrnl_trn,   " + 
                                    "	COM_MST_ACCOUNT_HEADS acc_head  " + 
                                    "   where   " + 
                                    "	jrnl_mst.ACCOUNTING_UNIT_ID=jrnl_trn.ACCOUNTING_UNIT_ID and     " + 
                                    "	jrnl_mst.ACCOUNTING_FOR_OFFICE_ID=jrnl_trn.ACCOUNTING_FOR_OFFICE_ID and  " + 
                                    "   jrnl_mst.CASHBOOK_YEAR=jrnl_trn.CASHBOOK_YEAR and   " + 
                                    "	jrnl_mst.CASHBOOK_MONTH=jrnl_trn.CASHBOOK_MONTH and     " + 
                                    "   jrnl_mst.VOUCHER_NO=jrnl_trn.VOUCHER_NO and     " + 
                                    "   jrnl_trn.ACCOUNT_HEAD_CODE=acc_head.ACCOUNT_HEAD_CODE and   " + 
                                    "	jrnl_mst.ACCOUNTING_UNIT_ID=? and   " + 
                                    "	jrnl_mst.ACCOUNTING_FOR_OFFICE_ID=? and     " + 
                                    "	jrnl_mst.CASHBOOK_YEAR=? and    " + 
                                    "	jrnl_mst.CASHBOOK_MONTH=?   and " +
                                    "   jrnl_trn.SUB_LEDGER_TYPE_CODE=? and     "    + 
                                    "   jrnl_trn.SUB_LEDGER_CODE=?      and     "   +
                                    "   jrnl_trn.CB_REF_NO='0'                    "   + 
                                    "   order by    " + 
                                    "	jrnl_mst.VOUCHER_NO ";
                                    System.out.println("Query to get the record"+sqlload);
                                    ps2 = con.prepareStatement(sqlload);
                                    ps2.setInt(1,acc_unit_id);
                                    ps2.setInt(2,acc_off_id);
                                    ps2.setInt(3,CB_year);
                                    ps2.setInt(4,CB_month);
                                     ps2.setInt(5,subled_type);
                                     ps2.setInt(6,subled_code);
                                    rs2=ps2.executeQuery();
                                    while(rs2.next())
                                    {
                                        xml=xml+"<voucher_no>"+rs2.getInt("VOUCHER_NO")+"</voucher_no>";
                                        xml=xml+"<voucher_date>"+rs2.getString("VOUCHER_DATE")+"</voucher_date>";
                                        xml=xml+"<acchead_code>"+rs2.getInt("ACCOUNT_HEAD_CODE")+"</acchead_code>";
                                        xml=xml+"<acchead_desc>"+rs2.getString("ACCOUNT_HEAD_DESC")+"</acchead_desc>";
                                       // xml=xml+"<sl_no>"+rs2.getInt("SL_NO")+"</sl_no>";
                                        xml=xml+"<subledger_typecode>"+rs2.getInt("SUB_LEDGER_TYPE_CODE")+"</subledger_typecode>";
                                        xml=xml+"<subledger_code>"+rs2.getInt("SUB_LEDGER_CODE")+"</subledger_code>";
                                        xml=xml+"<cr_dr>"+rs2.getString("CR_DR_INDICATOR")+"</cr_dr>";
                                        xml=xml+"<amount>"+rs2.getDouble("AMOUNT")+"</amount>";
                                        xml=xml+"<particulars>"+rs2.getString("PARTICULARS")+"</particulars>";
                                        count++;
                                    }
                                     if(count>0)
                                     {
                                         xml=xml+"<flag>success</flag>"; 
                                     }
                                     else
                                     {
                                         xml=xml+"<flag>nodata</flag>";    
                                     }
                             ps2.close();
                             rs2.close();
                         } //try close
                          catch(Exception e)
                          {
                                            xml=xml+"<flag>"+e.getMessage()+"</flag>";
                                            System.out.println(e);
                           }
                    }
        else if(cmnd.equalsIgnoreCase("cheqcheck")) 
        {
            xml=xml+"<command>cheqcheck</command>";
            try
                {             
                     acc_unit_id=Integer.parseInt(request.getParameter("acc_unitid1"));
                     System.out.println("acc......."+acc_unit_id);
                     acc_off_id=Integer.parseInt(request.getParameter("acc_offid1"));
                     System.out.println("office........"+acc_off_id);
                     CB_year=Integer.parseInt(request.getParameter("CB_year1"));
                     System.out.println("Cash Book Year........."+CB_year);
                     CB_month=Integer.parseInt(request.getParameter("CB_month1"));
                     System.out.println("Cash Book Year........."+CB_month);
                     int cheqmemotype=Integer.parseInt(request.getParameter("cheqmemo_type1"));
                     System.out.println("cheque memo type ......"+cheqmemotype);
                     int cheqno=Integer.parseInt(request.getParameter("cheqno"));
                     System.out.println("cheque Number ......"+cheqno);

                    String sqlload="select CHEQUE_NO from FAS_CHEQUE_MEMO_MST where ACCOUNTING_UNIT_ID=? and     " + 
                    "ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=?                        " + 
                    "and CHEQUE_MEMO_TYPE_CODE=? and CHEQUE_NO=?";
                    
                    System.out.println("query ****"+sqlload);
                    ps2 = con.prepareStatement(sqlload);
                    ps2.setInt(1,acc_unit_id);
                    ps2.setInt(2,acc_off_id);
                    ps2.setInt(3,CB_year);
                    ps2.setInt(4,CB_month);
                    ps2.setInt(5,cheqmemotype);
                    ps2.setInt(6,cheqno);
                    rs2=ps2.executeQuery();
                    
                   if(rs2.next())
                    {
                        xml=xml+"<cheqno>"+rs2.getInt("CHEQUE_NO")+"</cheqno>";
                        count++;
                    }
                    if(count>0)
                    {
                        xml=xml+"<flag>success</flag>"; 
                    }
                    else
                    {
                        xml=xml+"<flag>nodata</flag>";    
                    }
                     ps2.close();
                     rs2.close();
                 } //try close
                  catch(Exception e)
                  {
                                    xml=xml+"<flag>"+e.getMessage()+"</flag>";
                                    System.out.println(e);
                   }
            }
                    
        xml=xml+"</response>";
        pw.write(xml);
        System.out.println("xml is : " + xml);
        pw.flush();
        pw.close();
    }//DOGET close
public void doPost(HttpServletRequest request,HttpServletResponse response)throws IOException,ServletException          
{
	int last=0;
              String CONTENT_TYPE="text/html";
              response.setContentType(CONTENT_TYPE);
              response.setHeader("Cache-Control","no-cache");
              String cmnd="";
              int acc_unitid=0;int acc_offid=0;int CB_year=0;int CB_month=0;int cheqmemo_typecode=0;int cheqmemo_no=0;
              String cheqmemo_date="";String vouc_date="";String particulars="";String particulars1="";
              int acc_headcode=0;int payee_typecode=0;int payee_code=0;int bank_id=0;int branch_id=0;
              double cheq_amt=0.00;int cheq_no=0;String cheq_date="";
              int Total_TRN_Rec=0;
              int vouc_no_trn=0;String vouc_date_trn="";int acc_headcode_trn=0;int subled_typecode_trn=0;
              int subled_code_trn=0;String CR_DR_indi="";double amnt=0.00;String particulars_trn="";
              int reclist=0;
              long bankac_no=0;
              PrintWriter pw=response.getWriter();
              System.out.println("Welcome to dopost of cheque memo from liability journal");
              HttpSession session=request.getSession(false);
              String update_user=(String)session.getAttribute("UserId");
              long l=System.currentTimeMillis();
              Timestamp ts=new Timestamp(l);
              System.out.println("Session :"+session);
              double dep_rate = 0; // changes here
              /*********** connection establishment****************/
              Connection con=null;
              ResultSet rs=null;
              CallableStatement cs = null;
              PreparedStatement ps=null;
              ResultSet rs2=null;
              String txtCB_REF_TYPE="";
            
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
                                           
                          ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection                              Class.forName(strDriver.trim());
                              con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
              }
              catch(Exception e)
              {
                      System.out.println("Exception in connection...."+e);
              } 
               try
               {
                   session=request.getSession(false);
                   if(session==null)
                   {
                       System.out.println(request.getContextPath()+"/index.jsp");
                       response.sendRedirect(request.getContextPath()+"/index.jsp");
                       return;
                   }
                   System.out.println(session);
               } 
               catch(Exception e)
               {
                     System.out.println("Redirect Error :"+e);
               }
         String userid=(String)session.getAttribute("UserId");
         System.out.println("session id is:"+userid);
         update_user=(String)session.getAttribute("UserId");
         System.out.println("Updaated_by_userid  ::::"+update_user);
         l=System.currentTimeMillis();
         ts=new Timestamp(l);  
                    session=request.getSession(false);
                  try
                     {
                            cmnd =  request.getParameter("Command");     
                            System.out.println("Command passed via the button pressed : " + cmnd);
                     }
                  catch(Exception e3)
                     {
                             e3.printStackTrace();
                     }
/*///////////////////////////////Getting the values from the JSP Page//////////////////////////////////////////////////*/
       if(cmnd.equalsIgnoreCase("Add")) 
      {
    	   Calendar c,c1,c2;
             int errcode=0,errcode_no=0,journal_upd=0;
             int selckbox=0;
             int final_upd=0;
             double txtAmount=0.0;
             int bill_no_Recs=0;
             int txtJournalVou_No=0,voucher_year=0,voucher_month=0;
             int paymentno=0;
             Date cmDate=null,billDate=null,voucherDate=null;
             try{acc_unitid=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}catch(Exception e){System.out.println("cmbAcc_UnitCodeException arised"+e);}
             
             try{acc_offid=Integer.parseInt(request.getParameter("cmbOffice_code"));}catch(Exception e){System.out.println("cmbOffice_code Exception arised"+e);}
             
             try{cheqmemo_typecode=Integer.parseInt(request.getParameter("memotype"));}catch(Exception e){System.out.println("cmbcheqmemotype Exception arised"+e);}
              
             String[] sd=request.getParameter("memodate").split("/");
             c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
             java.util.Date d=c.getTime();
             cmDate=new Date(d.getTime());
     
             try{CB_year=Integer.parseInt(sd[2]);}
             catch(Exception e){System.out.println("exception"+e );}
          
             try{CB_month=Integer.parseInt(sd[1]);}
             catch(Exception e){System.out.println("exception"+e );}
          
             
             try{vouc_date=request.getParameter("vochardate");}catch(Exception e){System.out.println("txtvoucher_date Exception arised"+e);}
             
             String[] sd2=request.getParameter("vochardate").split("/");
             c2=new GregorianCalendar(Integer.parseInt(sd2[2]),Integer.parseInt(sd2[1])-1,Integer.parseInt(sd2[0]));
             java.util.Date d2=c2.getTime();
             voucherDate=new Date(d2.getTime());
     
             try{voucher_year=Integer.parseInt(sd2[2]);}
             catch(Exception e){System.out.println("exception"+e );}
             
             try{voucher_month=Integer.parseInt(sd2[1]);}
             catch(Exception e){System.out.println("exception"+e );}
             
             
             try{acc_headcode=Integer.parseInt(request.getParameter("txtCash_Acc_code"));}catch(Exception e){System.out.println("txtCash_Acc_code Exception arised"+e);}
             
             try{bankac_no=Long.parseLong(request.getParameter("txtBankAccountNo"));}catch(Exception e){System.out.println("txtBankAccountNo Exception arised"+e);}
                  
             try{bank_id=Integer.parseInt(request.getParameter("txtBankID"));}catch(Exception e){System.out.println("txtBankID Exception arised"+e);}
             
             try{branch_id=Integer.parseInt(request.getParameter("txtBranchID"));}catch(Exception e){System.out.println("txtBranchID Exception arised"+e);}
                  
             try{payee_typecode=Integer.parseInt(request.getParameter("payeetype"));}catch(Exception e){System.out.println("cmbMas_SL_type Exception arised"+e);}      
             
             try{payee_code=Integer.parseInt(request.getParameter("txtEmpId"));}catch(Exception e){System.out.println("cmbMas_SL_Code Exception arised"+e);}
             
             
             try{txtAmount=Double.parseDouble(request.getParameter("txtAmount"));}catch(Exception e){System.out.println("Exception arised"+e);}
                
             try{particulars=request.getParameter("txtParticulars");}catch(Exception e){System.out.println("Exception arised"+e);} 
             
                  
                if(particulars!=null)
                {
                    particulars1=particulars;
                }
                else
                    particulars1="";
                
                 
                    /**********************************calculating Max value of Cheque Memo Type Code************************************/
                    try
                    {
                            String sqlsel="select decode(max(CHEQUE_MEMO_NO),null,0,max(CHEQUE_MEMO_NO))as CHEQUE_MEMO_NO from FAS_CHEQUE_MEMO_MST";
                            ps=con.prepareStatement(sqlsel);
                            rs=ps.executeQuery();
                            System.out.println(sqlsel);
                            if(rs.next())
                            {
                                     cheqmemo_no=rs.getInt("CHEQUE_MEMO_NO");
                            }
                            cheqmemo_no=cheqmemo_no+1;
                            System.out.println("Maximum value of Cheque Memo Type Code Number is :"+cheqmemo_no);
                            ps.close();
                            rs.close();
                    }
                    catch(Exception ee) 
                    {
                         System.out.println("Exception in getting maximum value of Cheque Memo Type Code Number :"+ee);    
                    }
                            
                try
                {
                        con.clearWarnings();
                        con.setAutoCommit(false);
                        System.out.println("check whether the record is already exisis or not");
                        
                        String sqlins="insert into FAS_CHEQUE_MEMO_MST(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,         "  +
                                 "CASHBOOK_YEAR,CASHBOOK_MONTH,CHEQUE_MEMO_TYPE_CODE,CHEQUE_MEMO_NO,CHEQUE_MEMO_DATE,       "  +
                                 "VOUCHER_DATE,BANK_AC_NO,ACCOUNT_HEAD_CODE,PAYEE_TYPE_CODE,PAYEE_CODE,                     "  +
                                 "BANK_ID,BRANCH_ID,CHEQUE_AMOUNT,PARTICULARS,                        "  +
                                 "UPDATED_BY_USERID,UPDATED_DATE)                                                          "  +
                                 "values (?,?,?,?,?,?,to_date(?,'dd/mm/yyyy'),to_date(?,'dd/mm/yyyy'),?,?,?,?,?,?,?,?,?,?)";
                      
                     
                        
                        
                        
                        
                  ps=con.prepareStatement(sqlins);
                  ps.setInt(1,acc_unitid);
                  ps.setInt(2,acc_offid);
                  ps.setInt(3,CB_year);
                  ps.setInt(4,CB_month);
                  ps.setInt(5,cheqmemo_typecode);
                  ps.setInt(6,cheqmemo_no);
                  ps.setString(7,cheqmemo_date);
                  ps.setString(8,vouc_date);
                  ps.setLong(9,bankac_no);
                  ps.setInt(10,acc_headcode);
                  ps.setInt(11,payee_typecode);
                  ps.setInt(12,payee_code);
                  ps.setInt(13,bank_id);
                  ps.setInt(14,branch_id);
                 // ps.setInt(15,cheq_no);
               //   ps.setString(16,cheq_date);
                  ps.setDouble(15,txtAmount);
                  ps.setString(16,particulars1);
                  ps.setString(17,update_user);
                  ps.setTimestamp(18,ts);          
                   errcode_no=ps.executeUpdate();
                   System.out.println("Error code :"+errcode_no);
                     ps.close();
                           if(errcode_no==0)
                           {         
                                     System.out.println("redirect");
                                     sendMessage(response, "Insertion Failed",
                       						"ok", "cheqmemo_liabjournal.jsp");                                  
                           }
                           else
                           {
                                      System.out.println("The records inserted into the FAS_CHEQUE_MEMO_MST master table scuccessfully");
                                      int SL_NO=1;
                                      
                                      String bill_no_Rec[]=request.getParameterValues("bill_no");
                                     bill_no_Recs=bill_no_Rec.length;//Integer.parseInt(No_TRN_Rec);
                                      System.out.println(" bill_no_Recs :"+bill_no_Recs);
                                      
                                      
        	                          String Grid_bill_date[]=request.getParameterValues("bill_date");
        	                          String Grid_pass_amount[]=request.getParameterValues("pass_amount");
        	                          String Grid_head_code[]=request.getParameterValues("head_code");
        	                          String Grid_sl_type[]=request.getParameterValues("sl_type");
        	                          String Grid_SL_code[]=request.getParameterValues("sl_code");                          
        	                          String Grid_crdrindicator[]=request.getParameterValues("crdrindicator");
        	                          String Grid_dr_amount[]=request.getParameterValues("dr_amount");                         
        	                          String Trn_remarks12[]=request.getParameterValues("remarks12"); 
        	                          
        	                          String grid_cheque_no_dates[]=request.getParameterValues("cheque_no_dates");                          
        	                          String grid_bill_no[]=request.getParameterValues("bill_no");
                                      
                               
                             String sql="insert into FAS_CHEQUE_MEMO_TRN(ACCOUNTING_UNIT_ID,                    "   +
                                         "  ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,              "   +
                                         "  CHEQUE_MEMO_NO,SL_NO,BILL_NO,BILL_DATE,ACCOUNT_HEAD_CODE,     "   +
                                         "  SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,CR_DR_INDICATOR,PASS_ORDER_AMOUNT,        "   +
                                         "  PARTICULARS,                                                        "   +
                                         "  UPDATED_BY_USERID,UPDATED_DATE,AMOUNT)                                     "   +
                                         "  values (?,?,?,?,?,?,?,to_date(?,'dd/mm/yyyy'),?,?,?,?,?,?,?,?,?)       "   ;
                         ps=con.prepareStatement(sql);
                         for(int k=0;k<bill_no_Recs;k++) 
                         {      
                                 ps.setInt(1,acc_unitid);
                                 ps.setInt(2,acc_offid);
                                 ps.setInt(3,CB_year);
                                 ps.setInt(4,CB_month);
                                 ps.setInt(5,cheqmemo_no);
                                 ps.setInt(6,SL_NO);
                                 ps.setInt(7,Integer.parseInt(grid_bill_no[k]));
                             
                                 try
                                 {
		                             	 if(!Grid_bill_date[k].equalsIgnoreCase(""))
		                                 {
				                              String[] sd1=Grid_bill_date[k].split("/");
				                                 c1=new GregorianCalendar(Integer.parseInt(sd1[2]),Integer.parseInt(sd1[1])-1,Integer.parseInt(sd1[0]));
				                                 java.util.Date d1=c1.getTime();
				                                 billDate=new Date(d1.getTime());
				                                 ps.setDate(8,billDate);
				                                
		                                 }
		                                 else
		                                 {
		                                	 	 ps.setNull(8,java.sql.Types.DATE);
		                                 }
	    	                    	 
                                 }
                                 catch(Exception e) {
                                     	 System.out.println(e);
                                 }
                                 
                                 ps.setInt(9,Integer.parseInt(Grid_head_code[k]));
                                 ps.setInt(10,Integer.parseInt(Grid_sl_type[k]));
                                 ps.setInt(11,Integer.parseInt(Grid_SL_code[k]));
                                 ps.setString(12,Grid_crdrindicator[k]);   
                                 ps.setDouble(13,Double.parseDouble(Grid_pass_amount[k]));
                                 ps.setString(14,Trn_remarks12[k]);                              
                                 ps.setString(15,update_user);
                                 ps.setTimestamp(16,ts);
                                 ps.setDouble(17,Double.parseDouble(Grid_dr_amount[k]));
                                 SL_NO++;
                               last=ps.executeUpdate();
                              
                            }
                        
                         ps.close();
                       
                        }
                        
//                           if(last>0)
//                           {
                        	   /*
                        	   String txtMode_of_creat = "A", txtCreat_By_Module = "LJV";
                        	 String remar="Cheque Memo Journal";
                               System.out.println("inside proc");
                               cs =con.prepareCall("{call TEST_JOURNAL_MASTER_PROC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
                               cs.setInt(1, acc_unitid);
                               cs.setInt(2, acc_offid);
                               cs.setInt(3,voucher_year);
                               cs.setInt(4,voucher_month);
                               cs.setInt(5, txtJournalVou_No);
                               cs.setDate(6,voucherDate);
                          
                               cs.setInt(7, payee_typecode);
                               cs.setInt(8, payee_code);
                               cs.setDouble(9, dep_rate);
                               cs.setInt(10, cheq_no);
                               cs.setString(11, cheq_date);
                               cs.setString(12, txtCB_REF_TYPE);
                               cs.setInt(13, bill_no_Recs);
                               cs.setString(14,remar);
                               cs.setString(15, txtMode_of_creat);
                               cs.setString(16, txtCreat_By_Module);
                               cs.setString(17, "insert");
                               cs.registerOutParameter(5, java.sql.Types.NUMERIC);
                               cs.registerOutParameter(18, java.sql.Types.NUMERIC);
                               cs.setString(19, update_user);
                               cs.setTimestamp(20, ts);
                             //  cs.setInt(21,supplement_no);
                               System.out.println("b4 exe ");
                               cs.execute();
                               txtJournalVou_No = cs.getInt(5);
                               errcode = cs.getInt(18);
                               System.out.println("SQLCODE:::" + errcode);
                               if (errcode != 0) {
                                   System.out.println("redirect");
                                   
                                   sendMessage(response, "Cheque Memo Creation Failed",
                      						"ok", "cheqmemo_liabjournal.jsp");     
                                  
                               } else {
                            	 
                            	   String bill_no_Rec[]=request.getParameterValues("bill_no");
                                   bill_no_Recs=bill_no_Rec.length;//Integer.parseInt(No_TRN_Rec);
                                    System.out.println(" bill_no_Recs :"+bill_no_Recs);
                                    
                                    
      	                          String Grid_bill_date[]=request.getParameterValues("bill_date");
      	                          String Grid_pass_amount[]=request.getParameterValues("pass_amount");
      	                          String Grid_head_code[]=request.getParameterValues("head_code");
      	                          String Grid_sl_type[]=request.getParameterValues("sl_type");
      	                          String Grid_SL_code[]=request.getParameterValues("sl_code");                          
      	                          String Grid_crdrindicator[]=request.getParameterValues("crdrindicator");
      	                          String Grid_dr_amount[]=request.getParameterValues("dr_amount");                         
      	                          String Trn_remarks12[]=request.getParameterValues("remarks12"); 
      	                          
      	                          String grid_cheque_no_dates[]=request.getParameterValues("cheque_no_dates");                          
      	                          String grid_bill_no[]=request.getParameterValues("bill_no");

                                   String sql =
                                       "insert into TEST_JOURNAL_TRANSACTION(ACCOUNTING_UNIT_ID, " +
                                       "ACCOUNTING_FOR_OFFICE_ID ,CASHBOOK_YEAR, CASHBOOK_MONTH, VOUCHER_NO, SL_NO, ACCOUNT_HEAD_CODE, " +
                                       "CR_DR_INDICATOR, SUB_LEDGER_TYPE_CODE, SUB_LEDGER_CODE, BILL_NO,BILL_TYPE,AGREEMENT_NO,AGREEMENT_DATE," +
                                       "BILL_DATE,  " +
                                       "AMOUNT, PARTICULARS,CB_REF_NO,CB_REF_DATE,UPDATED_BY_USER_ID,UPDATED_DATE ) " +
                                       "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                                   int SL_NO = 1, txtAcc_HeadCode = 0, 
                                   cmbSL_Code = 0, cmbSL_type = 0, txtCB_REF_NO = 0;
                                  String txtBill_no="",txtBill_Type="",txtAgree_No="",cbdate="";
                                  Date txtAgree_Date=null,txtBill_Date=null;
                                  int cbno=0;
                                   ps = con.prepareStatement(sql);
                                   for (int k = 0; k < bill_no_Recs; k++) {
                                      
                                       ps.setInt(1, acc_unitid);
                                       ps.setInt(2, acc_offid);
                                       ps.setInt(3, voucher_year);
                                       ps.setInt(4, voucher_month);
                                       ps.setInt(5, txtJournalVou_No);
                                       ps.setInt(6, SL_NO);
                                       ps.setInt(7,Integer.parseInt(Grid_head_code[k]));
                                       ps.setString(8,Grid_crdrindicator[k]);
                                       ps.setInt(9,Integer.parseInt(Grid_sl_type[k]));
                                       ps.setInt(10,Integer.parseInt(Grid_SL_code[k]));
                                       ps.setInt(11,Integer.parseInt(grid_bill_no[k]));
                                       ps.setString(12, txtBill_Type);
                                       ps.setString(13, txtAgree_No);
                                       ps.setDate(14, txtAgree_Date);
                                       try
                                       { if(!Grid_bill_date[k].equalsIgnoreCase(""))
      		                                 {
      				                              String[] sd1=Grid_bill_date[k].split("/");
      				                                 c1=new GregorianCalendar(Integer.parseInt(sd1[2]),Integer.parseInt(sd1[1])-1,Integer.parseInt(sd1[0]));
      				                                 java.util.Date d1=c1.getTime();
      				                                 billDate=new Date(d1.getTime());
      				                                 ps.setDate(15,billDate);
      				                         }
      		                                 else
      		                                 { 	 ps.setNull(15,java.sql.Types.DATE);
      		                                 }
      	    	                    	 }
                                       catch(Exception e) {
                                           	 System.out.println(e);
                                       }
                                      
                                       ps.setDouble(16,Double.parseDouble(Grid_pass_amount[k]));
                                       ps.setString(17,Trn_remarks12[k]);
                                       ps.setInt(18, cbno);
                                       ps.setString(19, cbdate);
                                       ps.setString(20, update_user);
                                       ps.setTimestamp(21, ts);
                                       SL_NO++;
                                     journal_upd=ps.executeUpdate();
                                     System.out.println("journal_upd::"+journal_upd);
                                   
                                   }
                                   
                                 
                               }  */
                               if(last>0)
                               {
                            	   int txtJournal_type_code=0,txtchallan_No=0,txtNo_Acq_rolls=0;
                            	   String mode="M",module="BPP",paid="";
                            	   String radPart_Amt = "",parti="Cheque Memo to Liability Journal";
                            	   cs =con.prepareCall("call TEST_PAYMENT_MASTER_PROC(?::numeric,?::numeric,?,?::numeric,?::numeric,?,?::numeric,?::numeric,?::numeric,?::numeric,?::numeric,?::numeric,?::numeric,?,?,?,?::numeric,?,?,?::numeric,?::numeric,?::numeric,?::numeric,?::numeric,?,?,?,?::numeric,?,?,?::numeric)");
                            		                 cs.setInt(1, acc_unitid);
                            		                 cs.setInt(2, acc_offid);
                            		                 cs.setDate(3,voucherDate);
                            		                 cs.setInt(4,voucher_year);
                            		                 cs.setInt(5,voucher_month);
                            		                 cs.setString(6, "A");
                            		                 cs.setInt(7,paymentno);
                            		                 cs.setInt(8,acc_headcode);
                            		                 
                            		                 cs.setInt(9, bank_id);
                            		                 cs.setInt(10, branch_id);
                            		                 cs.setLong(11, bankac_no);
                            		                 cs.setInt(12,payee_typecode);
                            		                 cs.setInt(13,payee_code);
                            		                 cs.setString(14, "CR");
                            		                 cs.setString(15, paid);
                            		                 cs.setString(16, "C");
                            		                 // cs.setInt(17,txtPay_Vou_No);
                            		                 //cs.setDate(18,txtPay_Vou_date);
                            		                 cs.setInt(17, txtJournal_type_code);
                            		                 cs.setString(18, parti);
                            		                 cs.setString(19, radPart_Amt);
                            		                 cs.setDouble(20, cheq_amt);
                            		                 cs.setInt(21, txtchallan_No);
                            		                 cs.setInt(22,bill_no_Recs);
                            		                 cs.setDouble(23,txtAmount);
                            		                 cs.setInt(24, txtNo_Acq_rolls);
                            		                 cs.setString(25,mode);
                            		                 cs.setString(26, module);
                            		                 cs.setString(27, "insert");
                            		                 cs.registerOutParameter(7, java.sql.Types.NUMERIC);
                            		                 cs.registerOutParameter(28, java.sql.Types.NUMERIC);
                            		                 cs.setNull(7,java.sql.Types.NUMERIC);
                            		                 cs.setNull(28,java.sql.Types.NUMERIC);
                            		                 cs.setString(29, update_user);
                            		                 cs.setTimestamp(30, ts);
                            		                 cs.setInt(31, 0);
                            		                 System.out.println("b4 exe ");
                            		                 cs.execute();
                            		                 //paymentno =cs.getInt(7);  
                            		                 paymentno = cs.getBigDecimal(7).intValue();
                            		                 System.out.println("paymentno "+paymentno);
                            		                 //int pay_mas = cs.getInt(28);
                            		                 int pay_mas = cs.getBigDecimal(28).intValue();
                            		                 System.out.println(pay_mas);
                            		                 if(pay_mas==0)
                            		                 {
                            		                	 System.out.println("ffff");
                            		                	 
                            		                	 String bill_no_Rec[]=request.getParameterValues("bill_no");
                                                         bill_no_Recs=bill_no_Rec.length;//Integer.parseInt(No_TRN_Rec);
                                                          System.out.println(" bill_no_Recs :"+bill_no_Recs);
                                                          
                                                          
                            	                          String Grid_bill_date[]=request.getParameterValues("bill_date");
                            	                          String Grid_pass_amount[]=request.getParameterValues("pass_amount");
                            	                          String Grid_head_code[]=request.getParameterValues("head_code");
                            	                          String Grid_sl_type[]=request.getParameterValues("sl_type");
                            	                          String Grid_SL_code[]=request.getParameterValues("sl_code");                          
                            	                          String Grid_crdrindicator[]=request.getParameterValues("crdrindicator");
                            	                          String Grid_dr_amount[]=request.getParameterValues("dr_amount");                         
                            	                          String Trn_remarks12[]=request.getParameterValues("remarks12"); 
                            	                          
                            	                          String grid_cheque_no_dates[]=request.getParameterValues("cheque_no_dates");                          
                            	                          String grid_bill_no[]=request.getParameterValues("bill_no");
                            		                	 
                            		                	 String sql =
                            		                         "insert into TEST_PAYMENT_TRANSACTION(ACCOUNTING_UNIT_ID, " +
                            		                         "ACCOUNTING_FOR_OFFICE_ID ,CASHBOOK_YEAR, CASHBOOK_MONTH, VOUCHER_NO, SL_NO, ACCOUNT_HEAD_CODE, " +
                            		                         "CR_DR_INDICATOR, SUB_LEDGER_TYPE_CODE, SUB_LEDGER_CODE, BILL_NO,BILL_TYPE,AGREEMENT_NO,AGREEMENT_DATE," +
                            		                         "BILL_DATE,BANK_ID,BRANCH_ID,ACCOUNT_NO,CHEQUE_DD_NO, CHEQUE_DD_DATE, " +
                            		                         "AMOUNT, PARTICULARS, PAYABLE_VOUCHER_NO,PAYABLE_VOUCHER_DATE,UPDATED_BY_USER_ID,UPDATED_DATE,REFERENCE_NO) " +
                            		                         "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";


                            		                     int SL_NO = 1, txtAcc_HeadCode = 0, cmbSL_Code =
                            		                         0, cmbSL_type = 0, txtPay_Vou_No = 0;
                            		                     Date txtBill_Date = null, txtAgree_Date =
                            		                         null, txtCheque_DD_date = null, txtPay_Vou_date = null;
                            		                     double txtsub_Amount = 0;
                            		                     String rad_sub_CR_DR = "", txtBill_no = "", txtBill_Type =
                            		                         "", txtAgree_No = "", txtParticular = "";
                            		                     String txtCheque_DD = "", txtCheque_DD_NO =
                            		                         "", txtsub_Paid_to = "", txtReference_No = "";
                            		                     
                            		                     ps = con.prepareStatement(sql);

                            		                     
                            		                     for (int k = 0; k <bill_no_Recs; k++) {
                            		                    	 ps.setInt(1, acc_unitid);
                            		                         ps.setInt(2, acc_offid);
                            		                         ps.setInt(3, voucher_year);
                                                             ps.setInt(4, voucher_month);
                            		                         ps.setInt(5,paymentno);
                            		                         ps.setInt(6, SL_NO);
                            		                         ps.setInt(7,Integer.parseInt(Grid_head_code[k]));
                            		                         ps.setString(8, "DR");
                            		                         ps.setInt(9,Integer.parseInt(Grid_sl_type[k]));
                                                             ps.setInt(10,Integer.parseInt(Grid_SL_code[k]));
                                                             ps.setInt(11,Integer.parseInt(grid_bill_no[k]));
                            		                         ps.setString(12, txtBill_Type);
                            		                         ps.setString(13, txtAgree_No);
                            		                         ps.setDate(14, txtAgree_Date);
                            		                         try
                                                             { if(!Grid_bill_date[k].equalsIgnoreCase(""))
                            		                                 {
                            				                              String[] sd1=Grid_bill_date[k].split("/");
                            				                                 c1=new GregorianCalendar(Integer.parseInt(sd1[2]),Integer.parseInt(sd1[1])-1,Integer.parseInt(sd1[0]));
                            				                                 java.util.Date d1=c1.getTime();
                            				                                 billDate=new Date(d1.getTime());
                            				                                 ps.setDate(15,billDate);
                            				                         }
                            		                                 else
                            		                                 { 	 ps.setNull(15,java.sql.Types.DATE);
                            		                                 }
                            	    	                    	 }
                                                             catch(Exception e) {
                                                                 	 System.out.println(e);
                                                             }
                                                             
                                                             ps.setInt(16,bank_id);
                                                             ps.setInt(17,branch_id);
                                                             ps.setLong(18,bankac_no);
                                                          
                                                             String[] ck=grid_cheque_no_dates[k].split("-");
                            		                         ps.setString(19, ck[0]);
                            		                         ps.setString(20, ck[1]);  
                            		                      
                            		                         ps.setDouble(21,Double.parseDouble(Grid_pass_amount[k]));
                            		                         ps.setString(22,Trn_remarks12[k]);
                            		                         ps.setInt(23, txtPay_Vou_No);
                            		                         ps.setDate(24, txtPay_Vou_date);
                            		                         ps.setString(25, update_user);
                            		                         ps.setTimestamp(26, ts);
                            		                         ps.setString(27, txtReference_No);

                            		                         SL_NO++;
                            		                        final_upd=ps.executeUpdate();
                            		                       System.out.println("final_upd:"+final_upd);

                            		                     }
                            		                 }
                            		                 else
                            		                 {
                            		                	 try{con.rollback();}catch(SQLException sqle){System.out.println("exception in rollback "+sqle);}
                                                         
                            		                	  sendMessage(response, "Insertion Failed",
                                            						"ok", "cheqmemo_liabjournal.jsp"); 
                                                         System.out.println("Exception occur due to "); 
                            		                 }
                            		                 if(final_upd>0)
                            		                 {
                            		                	 con.commit();
                            		                	  sendMessage(response, "Records Inserted Successfully",
                            		         						"ok", "cheqmemo_liabjournal.jsp");
                            		                 }
                                
                               }
                               else
                               {
                            	   try{con.rollback();}catch(SQLException sqle){System.out.println("exception in rollback "+sqle);}
                                   
                            	   sendMessage(response, "Insertion Failed",
                      						"ok", "cheqmemo_liabjournal.jsp"); 
                               }  
//                           }
//                           else
//                           {
//                        	   try{con.rollback();}catch(SQLException sqle){System.out.println("exception in rollback "+sqle);}
//                               
//                        	   sendMessage(response, "Insertion Failed",
//                  						"ok", "cheqmemo_liabjournal.jsp");
//                           }
                           
            }//try close
            catch(Exception e) 
                   {
                       try{con.rollback();}catch(SQLException sqle){System.out.println("exception in rollback "+sqle);}
           
                       sendMessage(response, "Insertion Failed",
       						"ok", "cheqmemo_liabjournal.jsp");
                   }
                   finally
                   {
                       System.out.println("done");
                       try{con.setAutoCommit(true);  }
                       catch(SQLException sqle){System.out.println("Exception arised in finally:"+sqle);}
                   }
              }
              
                        pw.flush();
                        pw.close();
   }//DoPost method close

private void sendMessage(HttpServletResponse response, String msg,
		String bType, String jsp) {
	try {
		String url = "org/FAS/FAS1/BillVerification/jsps/MessengerOkBack.jsp?message="
				+ msg + "&button=" + bType + "&jspname=" + jsp;
		response.sendRedirect(url);
	} catch (IOException e) {
		e.printStackTrace();
	}
}
}

