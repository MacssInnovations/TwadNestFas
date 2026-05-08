package Servlets.FAS.FAS1.FundReceiptSystem.servlets;

import Servlets.FAS.FAS1.CommonControls.servlets.Com_CashBook1;

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
import java.sql.Types;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class Fund_Receipt_Cancel_byOffice extends HttpServlet 
{
    private static final String CONTENT_TYPE = "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException 
    {
        super.init(config);
    }
    public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException 
    {
     
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
        
        /**Variables Decalrtion */ 
        Connection con=null;
        CallableStatement cs1=null;
        PreparedStatement ps=null;
        
        String xml="";
        String strCommand="";
        
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
                             ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection                             Class.forName(strDriver.trim());
                             con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
            }
            catch(Exception e)
                {
                   System.out.println("Exception in opening connection :"+e);
                   //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

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
             String CONTENT_TYPE = "text/html; charset=windows-1252";
             response.setContentType(CONTENT_TYPE);
             //xml="<response><command>Add</command>";
            Calendar c;
            int cmbAcc_UnitCode=0,cmbOffice_code=0,txtCash_Month_hid=0,txtCash_year=0,txtVoucher_No=0;
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
                   
            try{txtVoucher_No=Integer.parseInt(request.getParameter("txtVoucher_No"));}
            catch(Exception e){System.out.println("exception"+e );}
            System.out.println("txtVoucher_No "+txtVoucher_No);
            
            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~        
                    
                      /** Get Receipt Creation Date */          
                        String Receipt_Creation_Date=request.getParameter("txtCrea_date");
                  
                      /** Call Com_CashBook Servlet for Calculating Cash Book Month and Year */    
                        Com_CashBook1 cb=new Com_CashBook1();
                      
                      /** Assign Cashbook Year and Month to year_month Variable */
                        String year_month=cb.cb_date(Receipt_Creation_Date).toString();  
                      
                      /** Split Cash Book Year and Month */
                        String []ym=year_month.split("/");
                      
                      /** Assign Year and Month */
                        txtCash_year=Integer.parseInt(ym[0]);
                        txtCash_Month_hid=Integer.parseInt(ym[1]);
                                 
           //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~              
           
           /** Get Trf Voucher Number and Date of Transfer from 'fas_fund_receipt_by_office'  */ 
           
           int HO_voucher_no=0;
           int HO_cb_Month=0;
           int HO_cb_year=0;
           
           PreparedStatement ps2=null,ps4=null;
           ResultSet rs2=null,rs4=null ;           
            
           String sql1="" +
           "SELECT                                                          \n" + 
           "  trf_voucher_no,                                               \n" + 
           "  TO_CHAR(trf_voucher_date,'DD/MM/YYYY') AS trf_voucher_date,   \n" + 
           "  TO_CHAR(trf_voucher_date,'MM') AS cbmonth,                    \n" + 
           "  TO_CHAR(trf_voucher_date,'YYYY') AS cbyear                    \n" + 
           "FROM fas_fund_receipt_by_office                                 \n" + 
           "WHERE cashbook_month        =?                                  \n" + 
           "AND cashbook_year           =?                                  \n" + 
           "AND accounting_for_office_id=?                                  \n" + 
           "AND accounting_unit_id      =?                                  \n" + 
           "AND receipt_no              =?                                  \n";           
           
           
           try {
               con.clearWarnings();
               con.setAutoCommit(false);
               
               ps2=con.prepareStatement(sql1);
               ps2.setInt(1,txtCash_Month_hid);
               ps2.setInt(2,txtCash_year);
               ps2.setInt(3,cmbOffice_code);
               ps2.setInt(4,cmbAcc_UnitCode);
               ps2.setInt(5,txtVoucher_No);
               rs2=ps2.executeQuery();
               while (rs2.next()) {
                   HO_voucher_no=rs2.getInt("trf_voucher_no");
                   HO_cb_Month = rs2.getInt("cbmonth");
                   HO_cb_year = rs2.getInt("cbyear");
               }
           }
           catch (SQLException e) {
               System.out.println(e);
           }
           
           //tranfer month and receipt month are not same
           int trf_year=0,trf_month=0;
           
           try{
           
        	   System.out.println("**update*"+cmbAcc_UnitCode);
        	   System.out.println("**updatecmbOffice_code*"+cmbOffice_code);
        	   System.out.println("**update*txtCash_year"+txtCash_year);
        	   System.out.println("**update*txtCash_Month_hid"+txtCash_Month_hid);
        	   
           String sql_del="select TRF_CB_YEAR,TRF_CB_MONTH from FAS_FUND_RECEIPT_BY_OFFICE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? "+
           " and CASHBOOK_YEAR=?  and CASHBOOK_MONTH=?  and RECEIPT_NO=?  ";
           ps4=con.prepareStatement(sql_del);
           ps4.setInt(1,cmbAcc_UnitCode);
           ps4.setInt(2,cmbOffice_code);
           ps4.setInt(3,txtCash_year);
           ps4.setInt(4,txtCash_Month_hid);
           ps4.setInt(5,txtVoucher_No);
           rs4=ps4.executeQuery();
           if(rs4.next())
           {
        	   trf_year=rs4.getInt("TRF_CB_YEAR");
        	   trf_month=rs4.getInt("TRF_CB_MONTH");
           }
           }catch(Exception e){System.out.println(e);}
           /** Update Auto status in HO Transaction Table */ 
            
           PreparedStatement ps3=null;
           
           System.out.println("**update*trf_year"+trf_year);
    	   System.out.println("**update*trf_month"+trf_month);
           
           String sql2="" +
           "UPDATE fas_fund_trf_from_ho_trn                 \n" + 
           "SET AUTO_STATUS  =null                            \n" + 
           "WHERE                                           \n" + 
           "ACCOUNTING_UNIT_ID= 5                           \n" + 
           "and ACCOUNTING_FOR_OFFICE_ID = 5000             \n" + 
           "and CASHBOOK_YEAR = ?                           \n" + 
           "and CASHBOOK_MONTH = ?                          \n" + 
           "and VOUCHER_NO = ?                              \n" + 
           "and TRANSFER_TO_OFFICE_ID= ?                    \n";
           
           try {
               ps3=con.prepareStatement(sql2);
               ps3.setInt(1,trf_year);
               ps3.setInt(2,trf_month);
               ps3.setInt(3,HO_voucher_no);
               ps3.setInt(4,cmbOffice_code);
               ps3.execute();
           }
           catch (Exception e){               
               System.out.println(e);
               try{con.rollback();}catch(SQLException sqle){System.out.println("Excep"+sqle);}
           }
           
           
          
           String  sql_del="update FAS_FUND_RECEIPT_BY_OFFICE set RECEIPT_STATUS='C',UPDATED_BY_USER_ID=?,UPDATED_DATE=?  where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? "+
            " and CASHBOOK_YEAR=?  and CASHBOOK_MONTH=?  and RECEIPT_NO=?  ";
            try
            {
            con.clearWarnings();
            con.setAutoCommit(false);
            ps=con.prepareStatement(sql_del);
                ps.setString(1,update_user);
                ps.setTimestamp(2,ts);
                ps.setInt(3,cmbAcc_UnitCode);
                ps.setInt(4,cmbOffice_code);
                ps.setInt(5,txtCash_year);
                ps.setInt(6,txtCash_Month_hid);
                ps.setInt(7,txtVoucher_No);
                ps.executeUpdate();
                String txtReferNO_edit="",txtRemak_edit="";         // for cross reference
                Date txtReferDate_edit=null; 
                String radAuth_MC="";
                int txtAuth_By=0;
                
               
                
                cs1=con.prepareCall("call FAS_CROSS_REFERENCE_PROC(?::NUMERIC,?,?,?,?,?,?,?,?,?,?::VARCHAR,?,?,?,?,?)") ; 
                cs1.setInt(1,cmbAcc_UnitCode);
                cs1.setInt(2,txtCash_year);
                cs1.setInt(3,txtCash_Month_hid);
                cs1.setInt(4,txtVoucher_No);
                cs1.setInt(5,cmbOffice_code);
                cs1.setDate(6,txtCrea_date);
                cs1.setString(7,"FRO");
                cs1.setString(8,txtReferNO_edit);
                cs1.setDate(9,txtReferDate_edit);
                cs1.setString(10,txtRemak_edit);
                cs1.setInt(11,txtAuth_By);                                                      
                cs1.setString(12,"insert");
                cs1.registerOutParameter(13,Types.NUMERIC);  
           //     cs1.setNull(13,java.sql.Types.NUMERIC);
                cs1.setInt(13, 0);
                cs1.setString(14,update_user);
                cs1.setTimestamp(15,ts);   
                cs1.setString(16,radAuth_MC);
                cs1.execute();                                            // insertion into cross reference table
//                int errcode=cs1.getInt(13);
                int errcode = cs1.getBigDecimal(13).intValue();
                
                
                System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);
                System.out.println("cmbOffice_code "+cmbOffice_code);
                System.out.println("txtCrea_date "+txtCrea_date);
                System.out.println("txtCash_year "+txtCash_year);
                System.out.println("txtCash_Month_hid "+txtCash_Month_hid);
                
                System.out.println("SQLCODE:::"+errcode);
                
                if(errcode!=0)
                {   
                  con.rollback();
                  sendMessage(response,"The Fund Receipt  Cancellation Failed ","ok");
                  xml=xml+"<flag>failure</flag>";                          
                }
            con.commit();
            sendMessage(response,"The Fund Receipt Transaction Voucher Number '"+txtVoucher_No+"' has been Cancelled Successfully ","ok");
            }
            catch(Exception e) 
            {
                try{con.rollback();}catch(SQLException sqle){System.out.println("Excep"+sqle);}
                sendMessage(response,"The Fund Receipt Cancellation Failed ","ok");
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
           Connection con=null;
           ResultSet rs=null,rs2=null,rs3=null;
           //CallableStatement cs=null,cs1=null;
           PreparedStatement ps=null,ps3=null,ps2=null;
           String xml="";
           String strCommand="";
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
                                ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection                                Class.forName(strDriver.trim());
                                con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
               }
               catch(Exception e)
                   {
                      System.out.println("Exception in opening connection :"+e);
                      //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

                   }
        
        response.setContentType(CONTENT_TYPE);
        response.setHeader("Cache-Control","no-cache");
        PrintWriter out = response.getWriter();
        
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
        
        if(strCommand.equalsIgnoreCase("load_Voucher_No")) 
        {
             String CONTENT_TYPE = "text/xml; charset=windows-1252";
             response.setContentType(CONTENT_TYPE);
             Calendar c;
              xml="<response><command>load_Voucher_No</command>";
              
                    try {
                            String[] sd=request.getParameter("txtCrea_date").split("/");
                            c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
                            java.util.Date d=c.getTime();
                            txtCrea_date=new Date(d.getTime());
                            System.out.println("txtCrea_date "+txtCrea_date);
                            
                            //ps=con.prepareStatement("select RECEIPT_NO from FAS_FUND_RECEIPT_BY_OFFICE where ACCOUNTING_UNIT_ID=?  and ACCOUNTING_FOR_OFFICE_ID=? and RECEIPT_DATE=? and RECEIPT_STATUS!='C'");
                             ps=con.prepareStatement("select i.RECEIPT_NO from FAS_FUND_RECEIPT_BY_OFFICE i,FAS_CROSS_REFERENCE c where " +
                             " i.ACCOUNTING_UNIT_ID=?  and i.ACCOUNTING_FOR_OFFICE_ID=? and i.RECEIPT_DATE=? and i.RECEIPT_STATUS!='C' " +
                             " and i.ACCOUNTING_UNIT_ID=c.ACCOUNTING_UNIT_ID and i.ACCOUNTING_FOR_OFFICE_ID=c.ACCOUNTING_FOR_OFFICE_ID " +
                             " and i.CASHBOOK_YEAR=c.CASHBOOK_YEAR and i.CASHBOOK_MONTH=c.CASHBOOK_MONTH and i.RECEIPT_NO=c.VOUCHER_NO " +
                             " and c.CHANGE_NO=0 and c.AUTHORIZED_TO='C' and DOC_TYPE='FRO'");
                            ps.setInt(1,cmbAcc_UnitCode);
                            ps.setInt(2,cmbOffice_code);
                            ps.setDate(3,txtCrea_date);
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
                        System.out.println("catch..HERE.in load VOUCHER."+e);
                            xml=xml+"<flag>failure</flag>";
                        }
                        xml=xml+"</response>";
                        System.out.println(xml);
                        out.println(xml);
                        
         }
         
         else if(strCommand.equalsIgnoreCase("load_Voucher_Details")) 
           {
                String CONTENT_TYPE = "text/xml; charset=windows-1252";
                response.setContentType(CONTENT_TYPE);
                Calendar c;
                 xml="<response><command>load_Voucher_Details</command>";
                 int txtVoucher_No=0;
                // Date txtCrea_date;
                 
                try{txtVoucher_No=Integer.parseInt(request.getParameter("txtVoucher_No"));}
                catch(NumberFormatException e){System.out.println("exception"+e );}
                System.out.println("txtVoucher_No "+txtVoucher_No);
                
               
                
                       try {
                               String[] sd=request.getParameter("txtCrea_date").split("/");
                               c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
                               java.util.Date d=c.getTime();
                               txtCrea_date=new Date(d.getTime());
                               System.out.println("txtCrea_date "+txtCrea_date);
                               
                               ps=con.prepareStatement("select DR_ACCOUNT_HEAD_CODE,OFFICE_BANK_ID,OFFICE_BRANCH_ID,OFFICE_ACCOUNT_NO," +
                               "trim(to_char(TOTAL_AMOUNT,'99999999999999.99')) as TOTAL_AMOUNT ,CHEQUE_OR_DD,CHEQUE_DD_NO,to_char(CHEQUE_DD_DATE,'DD/MM/YYYY') as cheq_dd_date,CR_ACCOUNT_HEAD_CODE," +
                               "HO_BANK_ID,HO_BRANCH_ID,HO_ACCOUNT_NO," +
                               "HO_REF_NO,to_char(HO_REF_DATE,'DD/MM/YYYY') as referDate,PARTICULARS from " +
                               "FAS_FUND_RECEIPT_BY_OFFICE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? " +
                               " and RECEIPT_DATE=? and RECEIPT_NO=?");
                               ps.setInt(1,cmbAcc_UnitCode);
                               ps.setInt(2,cmbOffice_code);
                               ps.setDate(3,txtCrea_date);
                               ps.setInt(4,txtVoucher_No);
                               rs=ps.executeQuery();
                               System.out.println("outside if loop");
                               if(rs.next())
                               {
                                   System.out.println("indide if loop");
                                   xml=xml+"<flag>success</flag>";
                                   
                                   xml=xml+"<MasHeadCode>"+rs.getString("DR_ACCOUNT_HEAD_CODE").trim()+"</MasHeadCode>";
                                   xml=xml+"<accNo>"+rs.getString("OFFICE_ACCOUNT_NO").trim()+"</accNo>";
                                   ps3=con.prepareStatement("select bk.BANK_NAME ||'-' ||br.BRANCH_NAME || '-' ||coalesce(br.CITY_TOWN_NAME,'') as bankNAME" +
                                   " from FAS_MST_BANKS bk,FAS_MST_BANK_BRANCHES br where br.BANK_ID=? and br.BRANCH_ID=? and br.BANK_ID=bk.BANK_ID");
                                   ps3.setInt(1,rs.getInt("OFFICE_BANK_ID"));
                                   ps3.setInt(2,rs.getInt("OFFICE_BRANCH_ID"));
                                   rs3=ps3.executeQuery();
                                   if(rs3.next())
                                   {
                                   xml=xml+"<bk_name>"+rs3.getString("bankNAME")+"</bk_name>";
                                   rs3.close();
                                   ps3.close();
                                   }
                                   xml=xml+"<bk_id>"+rs.getInt("OFFICE_BANK_ID")+
                                   "</bk_id><br_id>"+rs.getInt("OFFICE_BRANCH_ID")+
                                   "</br_id><Total_amt>"+rs.getString("TOTAL_AMOUNT")+
                                   "</Total_amt><Remak>"+rs.getString("PARTICULARS").replace("", "-")+
                                   "</Remak><REF_NO>"+rs.getString("HO_REF_NO").replace("", "-")+
                                    "</REF_NO><referDate>"+rs.getString("referDate")+
                                   "</referDate>";
                                  
                                       xml=xml+"<subAHcode>"+rs.getInt("CR_ACCOUNT_HEAD_CODE")+"</subAHcode>";
                                       xml=xml+"<sub_bank_id>"+rs.getInt("HO_BANK_ID")+
                                        "</sub_bank_id><sub_branch_id>"+rs.getInt("HO_BRANCH_ID")+"</sub_branch_id>"+
                                        "<subbankAccNo>"+rs.getString("HO_ACCOUNT_NO").trim()+"</subbankAccNo>";
                                       xml=xml+"<che_or_DD>"+rs.getString("CHEQUE_OR_DD")+ "</che_or_DD>" +
                                       "<che_DD_no>"+rs.getString("CHEQUE_DD_NO")+"</che_DD_no>" +
                                       "<che_DD_date>"+rs.getString("cheq_dd_date")+"</che_DD_date>" ;
                                      System.out.println("here ending..1");
                                       ps3=con.prepareStatement("select bk.BANK_NAME ||'-' ||br.BRANCH_NAME || '-' ||coalesce(br.CITY_TOWN_NAME,'') as bankNAME" +
                                       " from FAS_MST_BANKS bk,FAS_MST_BANK_BRANCHES br where br.BANK_ID=? and br.BRANCH_ID=? and br.BANK_ID=bk.BANK_ID");
                                       ps3.setInt(1,rs.getInt("HO_BANK_ID"));
                                       ps3.setInt(2,rs.getInt("HO_BRANCH_ID"));
                                       rs3=ps3.executeQuery();
                                       if(rs3.next())
                                       {
                                           xml=xml+"<sub_bank_name>"+rs3.getString("bankNAME")+"</sub_bank_name>";
                                       rs3.close();
                                       ps3.close();
                                       }
                                   
                               }
                               
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
        System.out.println("error in send message");
        }
    }
}