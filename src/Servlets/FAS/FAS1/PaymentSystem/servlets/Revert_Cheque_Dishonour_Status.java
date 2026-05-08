package Servlets.FAS.FAS1.PaymentSystem.servlets;

import java.io.IOException;
import java.io.PrintWriter;

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


public class Revert_Cheque_Dishonour_Status extends HttpServlet {
    private static final String CONTENT_TYPE = 
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }
    public void doGet(HttpServletRequest request, 
                      HttpServletResponse response) throws ServletException, IOException 
        {
            Connection con=null;
            ResultSet rs=null,rs2=null,rs3=null,rs4=null;
            //CallableStatement cs=null,cs1=null;
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
              ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection              Class.forName(strDriver.trim());
              con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
              System.out.println("connected");
              }
              catch(Exception e)
                     {
                        System.out.println("Exception in opening connection :"+e);
                        //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");
                     }
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            response.setHeader("Cache-Control","no-cache");
            PrintWriter out = response.getWriter();
            String strCommand="";
            
            try 
            {
                strCommand=request.getParameter("Command");
                System.out.println("assign.. command..."+strCommand);
            }
            
            catch(Exception e) 
            {
                System.out.println("Exception in assigning..."+e);
            }
            String cmbAcc_UnitCodestr="",cmbOffice_codestr="";
            int cmbAcc_UnitCode=0,cmbOffice_code=0;
            Date txtCrea_date=null;
                String remarks="";
                try{
                cmbAcc_UnitCodestr=(request.getParameter("accunit"));
                cmbAcc_UnitCode=Integer.parseInt(cmbAcc_UnitCodestr);
                System.out.println("cmbAcc_UnitCode========"+cmbAcc_UnitCodestr);
                }
                catch(NumberFormatException e)
                {System.out.println("exception  here"+e );}
                System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);
               
               try{
                cmbOffice_codestr=request.getParameter("officeId");
             // cmbOffice_code=Integer.parseInt(cmbOffice_codestr);
                System.out.println(" cmbOffice_code========"+ cmbOffice_codestr);
               }
               catch(NumberFormatException e){System.out.println("exception"+e );}
              // System.out.println("cmbOffice_code "+cmbOffice_code);
                
                String xml="";
                String stryear=request.getParameter("txtyear");
                String strmonth=request.getParameter("txtmonth");
                int year=Integer.parseInt(stryear);
                int month=Integer.parseInt(strmonth);
                System.out.println("year month:"+year+"  "+month);
                int accunit=Integer.parseInt(request.getParameter("accunit"));
                System.out.println("accounting unit is :"+accunit);
                
                int offid=Integer.parseInt(request.getParameter("officeId"));
                System.out.println("office id is :"+offid);
                
                System.out.println("txtmonth "+month);
                long l=System.currentTimeMillis();
                Timestamp ts=new Timestamp(l);
                    
                String sql="",newcheq_status="";
                String newcheq_date="",newcheqamt="",gjvdate="";
                int new_cheqno=0,gjvno=0;
             
           if(strCommand.equalsIgnoreCase("loadCheque")) 
            {
            int count=0;
           
                // String CONTENT_TYPE = "text/xml; charset=windows-1252";
                 response.setContentType(CONTENT_TYPE);
                
                 xml="<response><command>loadCheque</command>";
                           sql="select row_number() OVER (ORDER BY doc_no) AS SNo,DOCUMENT_TYPE,DOC_NO,doc_date,CHEQUE_OR_DD,CHEQUE_DD_NO,oldcheq_date,   \n" + 
                           "oldcheqamt,REMARKS,recieved_date,RECEIVED_CHEQUE,RECEIVED_CHEQUE_NO,  \n" + 
                           "newcheq_date,newcheqamt,cheq_dish_date,GJVNO,GJVDATE,cashbook_year,cashbook_month from ( " +
                           "select row_number() OVER (ORDER BY doc_no) AS SNo,a.DOCUMENT_TYPE,a.DOC_NO,to_char(a.DOCUMENT_DATE,'DD/MM/YYYY') as doc_date,a.CHEQUE_OR_DD,a.CHEQUE_DD_NO,to_char(a.CHEQUE_DD_DATE,'DD/MM/YYYY') as oldcheq_date,    \n" + 
                           " trim(to_char(a.AMOUNT,'99999999999999.99')) as oldcheqamt,a.REMARKS,to_char(a.RECIEVED_DATE,'DD/MM/YYYY') as recieved_date, \n" + 
                           " a.RECEIVED_CHEQUE,a.RECEIVED_CHEQUE_NO,   \n" + 
                           " to_char(a.RECEIVED_CHEQUE_DATE,'DD/MM/YYYY') as newcheq_date,   \n" + 
                           " trim(to_char(a.RECEIVED_CHEQUE_AMOUNT,'99999999999999.99')) as newcheqamt,to_char(a.CHEQ_DISHONOUR_DATE,'DD/MM/YYYY') as cheq_dish_date,  \n" + 
                           " b.VOUCHER_NO as GJVNO,to_char(b.VOUCHER_DATE,'DD/MM/YYYY') as GJVDATE,c.cashbook_year,c.cashbook_month from FAS_CHEQUE_DISHONOUR a,FAS_JOURNAL_MASTER b,fas_receipt_master c,     \n" + 
                           "fas_journal_transaction d,fas_cross_reference e  " +
                           " where a.ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID   \n" + 
                           "and a.ACCOUNTING_FOR_OFFICE_ID=b.ACCOUNTING_FOR_OFFICE_ID  \n" + 
                           "and a.CASHBOOK_YEAR=b.CASHBOOK_YEAR   \n" + 
                           "and a.CASHBOOK_MONTH=b.CASHBOOK_MONTH   \n" + 
                           "and a.CHEQUE_DD_NO=b.CHEQUE_NO   \n" + 
                           "and a.CHEQUE_DD_DATE=b.CHEQUE_DATE  \n" + 
                           "and a.CHEQUE_DD_NO::numeric=e.VOUCHER_NO "+
                           "and a.CHEQ_DISHONOUR_DATE=e.ORIGINAL_DATE "+
                           "and a.RECEIVED_CHEQUE='N' and a.CREA_PAY_VOU='N'  \n" + 
                           "and (a.REVERT_CHEQDISHONOUR_STATUS is null or a.REVERT_CHEQDISHONOUR_STATUS!='Y')  \n" + 
                           "   AND a.DOC_NO = c.receipt_no\n" + 
                           "   and d.cb_ref_no = c.receipt_no\n" + 
                           "   AND a.document_date = c.RECEIPT_DATE\n" + 
                           "   AND c.CHEQ_DISHONOUR_STATUS = 'Y'\n" + 
                           "   AND a.accounting_unit_id = d.accounting_unit_id\n" + 
                           "   AND a.accounting_for_office_id = d.accounting_for_office_id\n" + 
                           "   AND a.cashbook_year =d.cashbook_year\n" + 
                           "   AND a.cashbook_month = d.cashbook_month\n" + 
                           "   AND b.voucher_no=d.voucher_no " +
                          // "and a.ACCOUNTING_UNIT_ID=? and a.ACCOUNTING_FOR_OFFICE_ID=?  \n" + 
                           "  and a.ACCOUNTING_UNIT_ID=? \n" + 
                           "and a.CASHBOOK_YEAR=? and a.CASHBOOK_MONTH=?\n" + 
                        //   "union all \n" + 
                        //   "select rownum,a.DOCUMENT_TYPE,a.DOCUMENT_NO,to_char(a.DOCUMENT_DATE,'DD/MM/YYYY') as doc_date,a.CHEQUE_OR_DD,a.CHEQUE_DD_NO,to_char(a.CHEQUE_DD_DATE,'DD/MM/YYYY') as oldcheq_date,    \n" + 
                        //   " trim(to_char(a.AMOUNT,'99999999999999.99')) as oldcheqamt,a.REMARKS,to_char(a.RECIEVED_DATE,'DD/MM/YYYY') as recieved_date,a.RECEIVED_CHEQUE,a.RECEIVED_CHEQUE_NO,   \n" + 
                        //   " to_char(a.RECEIVED_CHEQUE_DATE,'DD/MM/YYYY') as newcheq_date,   \n" + 
                        //   " trim(to_char(RECEIVED_CHEQUE_AMOUNT,'99999999999999.99')) as newcheqamt,to_char(a.CHEQ_DISHONOUR_DATE,'DD/MM/YYYY') as cheq_dish_date, 0 as GJVNO,'---' as GJVDATE from FAS_CHEQUE_DISHONOUR a,   \n" + 
                       //    "  fas_cross_reference e  "+
                       //    " where a.CHEQUE_DD_NO=e.VOUCHER_NO "+
                        //   "   and a.CHEQ_DISHONOUR_DATE=e.ORIGINAL_DATE  "+
                         //  "and a.CREA_PAY_VOU='N' " +
                         //  "and (a.REVERT_CHEQDISHONOUR_STATUS is null or a.REVERT_CHEQDISHONOUR_STATUS!='Y')  \n" + 
                       //    " and a.ACCOUNTING_UNIT_ID=? and a.ACCOUNTING_FOR_OFFICE_ID=? and a.CASHBOOK_YEAR=? and a.CASHBOOK_MONTH=?  \n" + 
                           ") z  order by DOC_NO ";
                        
                      
                          try
                          {
                          //System.out.println("1"+sql);
                        
                          ps=con.prepareStatement(sql);
                          ps.setInt(1,accunit);
                          //ps.setInt(2,offid);
                          ps.setInt(2,year);
                          ps.setInt(3,month);
                        //  ps.setInt(5,accunit);
                       //   ps.setInt(6,offid);
                       //   ps.setInt(7,year);
                        //  ps.setInt(8,month);
                         
                          System.out.println("1"+sql);
                          xml=xml+"<flag>success</flag><Ucode>"+cmbAcc_UnitCode+"</Ucode><Offid>"+cmbOffice_code+
                          "</Offid><txtCB_Year>"+year+"</txtCB_Year><txtCB_Month>"+month+"</txtCB_Month>";
                          System.out.println("xml......");
                          rs=ps.executeQuery();
                          
                             while(rs.next())
                             {
                             System.out.println("2");
                             
                                 try {


                                     if (rs.getString("REMARKS") == null) {
                                         remarks = "-";
                                     } else {
                                         remarks = rs.getString("REMARKS");
                                         System.out.println("REMARKS" + remarks);
                                     }
                                 } catch (Exception e) {
                                     System.out.println("Error in getting REMARKS values"+e);

                                 }
                                 try {


                                     if (rs.getString("newcheq_date") == null) {
                                         newcheq_date = "-";
                                     } else {
                                         newcheq_date = rs.getString("newcheq_date");
                                         System.out.println("newcheq_date" + newcheq_date);
                                     }
                                 } catch (Exception e) {
                                     System.out.println("Error in getting newcheq_date values"+e);

                                 }
                                 try {


                                     if (rs.getString("RECEIVED_CHEQUE") == null) {
                                         newcheq_status = "N";
                                     } else {
                                         newcheq_status = rs.getString("RECEIVED_CHEQUE");
                                         System.out.println("newcheq_status" + newcheq_status);
                                     }
                                 } catch (Exception e) {
                                     System.out.println("Error in getting newcheq_date values"+e);

                                 }
                                 try {

                                         gjvdate = rs.getString("GJVDATE");
                                         System.out.println("GJVDATE" + gjvdate);
                                  
                                 } catch (Exception e) {
                                     System.out.println("Error in getting GJVDATE values"+e);

                                 }
                                 try {


                                     if (rs.getInt("RECEIVED_CHEQUE_NO") ==0 ) {
                                         new_cheqno = 0;
                                     } else {
                                         new_cheqno = rs.getInt("RECEIVED_CHEQUE_NO");
                                         System.out.println("new_cheqno" + new_cheqno);
                                     }
                                 } catch (Exception e) {
                                     System.out.println("Error in getting new_cheqno values"+e);

                                 }
                                 try {
                                         gjvno = rs.getInt("GJVNO");
                                         System.out.println("GJVNO" + gjvno);
                                   
                                 } catch (Exception e) {
                                     System.out.println("Error in getting new_cheqno values"+e);

                                 }
                                 try {

                                     if (rs.getString("newcheqamt") == null) {
                                         newcheqamt = "-";
                                     } else {
                                         newcheqamt = rs.getString("newcheqamt");
                                         System.out.println("newcheqamt" + newcheqamt);
                                     }
                                 } catch (Exception e) {
                                     System.out.println("Error in getting newcheqamt values");

                                 }
                                
                                 xml=xml+"<leng>";
                                 xml=xml+"<doc_type>"+rs.getString("DOCUMENT_TYPE")+"</doc_type>";
                                 xml=xml+"<doc_no>"+rs.getString("DOC_NO")+"</doc_no>";
                                 xml=xml+"<receiptYR>"+rs.getInt("cashbook_year")+"</receiptYR>";
                                 xml=xml+"<receiptMN>"+rs.getInt("cashbook_month")+"</receiptMN>";
                                 xml=xml+"<doc_date>"+rs.getString("doc_date") +"</doc_date>";
                                 xml=xml+"<oldcheq_type>"+rs.getString("CHEQUE_OR_DD")+"</oldcheq_type>"; 
                                 xml=xml+"<old_cheqno>"+rs.getInt("CHEQUE_DD_NO")+"</old_cheqno>";
                                 xml=xml+"<old_cheqdate>"+rs.getString("oldcheq_date") +"</old_cheqdate>";
                                 xml=xml+"<old_cheqamt>"+rs.getString("oldcheqamt") +"</old_cheqamt>";
                                 xml=xml+"<remarks>"+remarks +"</remarks>";
                                 xml=xml+"<newcheq_status>"+newcheq_status+"</newcheq_status>"; 
                                 xml=xml+"<new_cheqno>"+new_cheqno+"</new_cheqno>";
                                 xml=xml+"<new_cheqdate>"+newcheq_date +"</new_cheqdate>";
                                 xml=xml+"<new_cheqamt>"+rs.getString("newcheqamt") +"</new_cheqamt>";
                                 xml=xml+"<gjvno>"+gjvno+"</gjvno>";
                                 xml=xml+"<gjvdate>"+gjvdate+"</gjvdate>";
                              
                                 xml=xml+"</leng>";
                                 count++;
                             }
                            if(count==0) 
                            {
                               System.out.println("inside count==0");
                               xml="<response><command>loadCheque</command><flag>failure</flag>";
                            }
                          }
                          catch(SQLException sqle)
                          {
                           System.out.println("error while fetching data " + sqle);
                             xml="<response><command>loadCheque</command><flag>failure</flag>";
                          }
            }    
                System.out.println("here "+strCommand.equalsIgnoreCase("searchByDate"));
                Date txtFrom_date=null,txtTo_date=null;
                Calendar c;
                String sqldate="";
           if(strCommand.equalsIgnoreCase("searchByDate"))
                { 
                   // String CONTENT_TYPE = "text/xml; charset=windows-1252";
                  xml="<response><command>searchByDate</command>";
                  System.out.println("here "+strCommand.equalsIgnoreCase("searchByDate"));
                 
                  String[] sd=request.getParameter("txtFrom_date").split("/");
                  c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
                  java.util.Date d=c.getTime();
                  txtFrom_date=new Date(d.getTime());
                  System.out.println("from_date "+txtFrom_date);
                  
                  sd=request.getParameter("txtTo_date").split("/");
                  c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
                  d=c.getTime();
                  txtTo_date=new Date(d.getTime());
                  
                  System.out.println("txtTo_date "+txtTo_date);
                    sqldate="select rownum,DOCUMENT_TYPE,DOC_NO,doc_date,CHEQUE_OR_DD,CHEQUE_DD_NO,oldcheq_date,   \n" + 
                    "oldcheqamt,REMARKS,recieved_date,RECEIVED_CHEQUE,RECEIVED_CHEQUE_NO,  \n" + 
                    "newcheq_date,newcheqamt,cheq_dish_date,GJVNO,GJVDATE from ( " +
                    "select rownum,a.DOCUMENT_TYPE,DOC_NO,to_char(a.DOCUMENT_DATE,'DD/MM/YYYY') as doc_date,a.CHEQUE_OR_DD,a.CHEQUE_DD_NO,to_char(a.CHEQUE_DD_DATE,'DD/MM/YYYY') as oldcheq_date,    \n" + 
                    " trim(to_char(a.AMOUNT,'99999999999999.99')) as oldcheqamt,a.REMARKS,to_char(a.RECIEVED_DATE,'DD/MM/YYYY') as recieved_date, \n" + 
                    " a.RECEIVED_CHEQUE,a.RECEIVED_CHEQUE_NO,   \n" + 
                    " to_char(a.RECEIVED_CHEQUE_DATE,'DD/MM/YYYY') as newcheq_date,   \n" + 
                    " trim(to_char(a.RECEIVED_CHEQUE_AMOUNT,'99999999999999.99')) as newcheqamt,to_char(a.CHEQ_DISHONOUR_DATE,'DD/MM/YYYY') as cheq_dish_date,  \n" + 
                    " b.VOUCHER_NO as GJVNO,to_char(b.VOUCHER_DATE,'DD/MM/YYYY') as GJVDATE from FAS_CHEQUE_DISHONOUR a,FAS_JOURNAL_MASTER b,fas_receipt_master c, \n" + 
                    " fas_journal_transaction d,fas_cross_reference e "+
                    " where a.ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID   \n" + 
                    "and a.ACCOUNTING_FOR_OFFICE_ID=b.ACCOUNTING_FOR_OFFICE_ID  \n" + 
                    "and a.CASHBOOK_YEAR=b.CASHBOOK_YEAR   \n" + 
                    "and a.CASHBOOK_MONTH=b.CASHBOOK_MONTH   \n" + 
                    " and a.CHEQUE_DD_NO=b.CHEQUE_NO   \n" + 
                    " and a.CHEQUE_DD_DATE=b.CHEQUE_DATE  \n" + 
                    " and a.CHEQUE_DD_NO=e.VOUCHER_NO "+
                    " and a.CHEQ_DISHONOUR_DATE=e.ORIGINAL_DATE "+
                    "and a.RECEIVED_CHEQUE='N' and a.CREA_PAY_VOU='N'  \n" + 
                    "and (a.REVERT_CHEQDISHONOUR_STATUS is null or a.REVERT_CHEQDISHONOUR_STATUS!='Y')  \n" + 
                    "   AND DOC_NO = c.receipt_no\n" + 
                    "   and d.cb_ref_no = c.receipt_no\n" + 
                    "   AND a.document_date = c.RECEIPT_DATE\n" + 
                    "   AND c.CHEQ_DISHONOUR_STATUS = 'Y'\n" + 
                    "   AND a.accounting_unit_id = d.accounting_unit_id\n" + 
                    "   AND a.accounting_for_office_id = d.accounting_for_office_id\n" + 
                    "   AND a.cashbook_year =d.cashbook_year\n" + 
                    "   AND a.cashbook_month = d.cashbook_month\n" + 
                    "   AND b.voucher_no=d.voucher_no " +
                    "and a.ACCOUNTING_UNIT_ID=? and a.ACCOUNTING_FOR_OFFICE_ID=?  \n" + 
                    "  AND a.CHEQ_DISHONOUR_DATE between ? and ?)z "+
                  //  "union all \n" + 
                   // "select rownum,a.DOCUMENT_TYPE,a.DOCUMENT_NO,to_char(a.DOCUMENT_DATE,'DD/MM/YYYY') as doc_date,a.CHEQUE_OR_DD,a.CHEQUE_DD_NO,to_char(a.CHEQUE_DD_DATE,'DD/MM/YYYY') as oldcheq_date,    \n" + 
                  //  " trim(to_char(a.AMOUNT,'99999999999999.99')) as oldcheqamt,a.REMARKS,to_char(a.RECIEVED_DATE,'DD/MM/YYYY') as recieved_date,a.RECEIVED_CHEQUE,a.RECEIVED_CHEQUE_NO,   \n" + 
                   // " to_char(a.RECEIVED_CHEQUE_DATE,'DD/MM/YYYY') as newcheq_date,   \n" + 
                  //  " trim(to_char(RECEIVED_CHEQUE_AMOUNT,'99999999999999.99')) as newcheqamt,to_char(a.CHEQ_DISHONOUR_DATE,'DD/MM/YYYY') as cheq_dish_date, 0 as GJVNO,'---' as GJVDATE from FAS_CHEQUE_DISHONOUR a,fas_cross_reference e   \n" + 
                   // " where (a.REVERT_CHEQDISHONOUR_STATUS is null or a.REVERT_CHEQDISHONOUR_STATUS!='Y')  \n" +
                   // " 		and a.CHEQUE_DD_NO=e.VOUCHER_NO "+
                   // " 		and a.CHEQ_DISHONOUR_DATE=e.ORIGINAL_DATE "+
                   // " and a.ACCOUNTING_UNIT_ID=? and a.ACCOUNTING_FOR_OFFICE_ID=? " +
                   // " 		AND a.DOCUMENT_DATE between ? and ?) "+
                    " order by DOC_NO";
                  
             
                 try
                 {
                   int count=0;
                   System.out.println("sqldate>>>>>>>>>"+sqldate);
                  ps=con.prepareStatement(sqldate);
                         ps.setInt(1,accunit);
                         ps.setInt(2,offid);
                         ps.setDate(3,txtFrom_date);
                         ps.setDate(4,txtTo_date);
                        // ps.setInt(5,accunit);
                       //  ps.setInt(6,offid);
                       //  ps.setDate(7,txtFrom_date);
                     //    ps.setDate(8,txtTo_date);
                       
                  
                  
                             xml=xml+"<flag>success</flag><Ucode>"+cmbAcc_UnitCode+"</Ucode><Offid>"+cmbOffice_code+
                             "</Offid><txtCB_Year>"+year+"</txtCB_Year><txtCB_Month>"+month+"</txtCB_Month>";
                             System.out.println("xml......");
                             rs=ps.executeQuery();
                             
                                while(rs.next())
                                {
                                System.out.println("2");
                                
                                    try {


                                        if (rs.getString("REMARKS") == null) {
                                            remarks = "-";
                                        } else {
                                            remarks = rs.getString("REMARKS");
                                            System.out.println("REMARKS" + remarks);
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Error in getting REMARKS values"+e);

                                    }
                                    try {


                                        if (rs.getString("newcheq_date") == null) {
                                            newcheq_date = "-";
                                        } else {
                                            newcheq_date = rs.getString("newcheq_date");
                                            System.out.println("newcheq_date" + newcheq_date);
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Error in getting newcheq_date values"+e);

                                    }
                                    try {


                                        if (rs.getString("RECEIVED_CHEQUE") == null) {
                                            newcheq_status = "N";
                                        } else {
                                            newcheq_status = rs.getString("RECEIVED_CHEQUE");
                                            System.out.println("newcheq_status" + newcheq_status);
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Error in getting newcheq_date values"+e);

                                    }
                                    try {

                                            gjvdate = rs.getString("GJVDATE");
                                            System.out.println("GJVDATE" + gjvdate);
                                     
                                    } catch (Exception e) {
                                        System.out.println("Error in getting GJVDATE values"+e);

                                    }
                                    try {


                                        if (rs.getInt("RECEIVED_CHEQUE_NO") ==0 ) {
                                            new_cheqno = 0;
                                        } else {
                                            new_cheqno = rs.getInt("RECEIVED_CHEQUE_NO");
                                            System.out.println("new_cheqno" + new_cheqno);
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Error in getting new_cheqno values"+e);

                                    }
                                    try {
                                            gjvno = rs.getInt("GJVNO");
                                            System.out.println("GJVNO" + gjvno);
                                      
                                    } catch (Exception e) {
                                        System.out.println("Error in getting new_cheqno values"+e);

                                    }
                                    try {

                                        if (rs.getString("newcheqamt") == null) {
                                            newcheqamt = "-";
                                        } else {
                                            newcheqamt = rs.getString("newcheqamt");
                                            System.out.println("newcheqamt" + newcheqamt);
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Error in getting newcheqamt values");

                                    }
                                   
                                    xml=xml+"<leng>";
                                    xml=xml+"<doc_type>"+rs.getString("DOCUMENT_TYPE")+"</doc_type>";
                                    xml=xml+"<doc_no>"+rs.getString("DOC_NO")+"</doc_no>";
                                    xml=xml+"<doc_date>"+rs.getString("doc_date") +"</doc_date>";
                                    xml=xml+"<oldcheq_type>"+rs.getString("CHEQUE_OR_DD")+"</oldcheq_type>"; 
                                    xml=xml+"<old_cheqno>"+rs.getInt("CHEQUE_DD_NO")+"</old_cheqno>";
                                    xml=xml+"<old_cheqdate>"+rs.getString("oldcheq_date") +"</old_cheqdate>";
                                    xml=xml+"<old_cheqamt>"+rs.getString("oldcheqamt") +"</old_cheqamt>";
                                    xml=xml+"<remarks>"+remarks +"</remarks>";
                                    xml=xml+"<newcheq_status>"+newcheq_status+"</newcheq_status>"; 
                                    xml=xml+"<new_cheqno>"+new_cheqno+"</new_cheqno>";
                                    xml=xml+"<new_cheqdate>"+newcheq_date +"</new_cheqdate>";
                                    xml=xml+"<new_cheqamt>"+rs.getString("newcheqamt") +"</new_cheqamt>";
                                    xml=xml+"<gjvno>"+gjvno+"</gjvno>";
                                    xml=xml+"<gjvdate>"+gjvdate+"</gjvdate>";
                                 
                                    xml=xml+"</leng>";
                                    count++;
                         }
                        if(count==0) 
                        {
                           xml="<response><command>searchByDate</command><flag>failure</flag>";
                        }
                     }
                     catch(SQLException sqle)
                     {
                       System.out.println("error while fetching data " + sqle);
                         xml="<response><command>searchByDate</command><flag>failure</flag>";
                     }
                }
                      
                xml=xml+"</response>";
                System.out.println(xml);
                
                    out.println(xml);
           
            
            }
            
      
    public void doPost(HttpServletRequest request, 
                       HttpServletResponse response) throws ServletException, 
                                                            IOException {
        response.setContentType(CONTENT_TYPE);
        
        PrintWriter out = response.getWriter();
        System.out.println("inside fundsubmit");
        String strCommand = "";
        Connection con = null;
        ResultSet rs = null;
        ResultSet res1 = null;
        ResultSet rs3 = null;
        ResultSet rs2 = null;
        ResultSet rs4 = null;
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps4 = null;
        PreparedStatement ps3 = null;
        PreparedStatement ps2 = null;
        String xml = "";
        Calendar c;
        Date LetterDate1 = null;
        int tot_amt = 0, ho_refno = 0;
        java.sql.Date ho_refdate = null;
        HttpSession session = request.getSession(false);
        String updatedby = (String)session.getAttribute("UserId");
        System.out.println("updated by user id is:" + updatedby);
        java.util.Date dt = new java.util.Date(System.currentTimeMillis());
        java.sql.Timestamp sqldt = new java.sql.Timestamp(dt.getTime());
        System.out.println("Time Stamp:" + sqldt);
        try {
            ResourceBundle rs1 = 
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
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
            con = 
               DriverManager.getConnection(ConnectionString, strdbusername.trim(), 
                            strdbpassword.trim());
        } catch (Exception e) {
            System.out.println("Exception in opening connection :" + e);
            sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

        }

             out.println("<html>");
             out.println("<head><title>RevertChequeSubmitServ</title></head>");
             out.println("<body>");
             String accunitid=request.getParameter("cmbAcc_UnitCode");
            
             String officeid=request.getParameter("cmbOffice_code");
            
             String cashbookyear=request.getParameter("txtCB_Year");
            
             String cashbookmonth=request.getParameter("txtCB_Month");
             
           
             int year=Integer.parseInt(cashbookyear);
             int month=Integer.parseInt(cashbookmonth);
             System.out.println("Month:========"+month);
             int accunit=Integer.parseInt(accunitid);
             System.out.println(accunit);
             int office_id=Integer.parseInt(officeid);  
             System.out.println(office_id);
            String update_user = (String)session.getAttribute("UserId");
            long l = System.currentTimeMillis();
            Timestamp ts = new Timestamp(l);
             int voucherno=0;
            Date che_DD_date=null;
            String fundcompare="",cheqdate="";
                                 try{
                                 
                                               int selrow=0;
                                               String sel[]=request.getParameterValues("sel");
                                               int selnumber[]=new int[sel.length];
                                               int vno=0,sno=0,offno=0,cheqno=0,receiptYEAR=0,receiptMONTH=0;
                                               double required_amt=0;
                                               for(int i=0;i<sel.length;i++)
                                               {
                                                 selnumber[i]=Integer.parseInt(sel[i]);
                                                 System.out.println("selected rows is-------"+selnumber[i]);
                                                 int x=selnumber[i];
                                                   System.out.println("vno_hid::::"+x);
                                                   String vnos[]=request.getParameterValues("vno_hid"+x);
                                                   System.out.println("vnos::::::"+vnos.length);
                                                   int vonumber[]=new int[vnos.length];
                                                   System.out.println(vnos.length);
                                                   vonumber[0]=Integer.parseInt(vnos[0]);
                                                   System.out.println("voucher new is-------"+vonumber[0]);
                                                   vno=vonumber[0];
                                                 //----------------
                                                  System.out.println("sl_no_hid"+x);
                                                  String snos[]=request.getParameterValues("sl_no_hid"+x);
                                                  int sonumber[]=new int[snos.length];
                                                  System.out.println(snos.length);
                                                  sonumber[0]=Integer.parseInt(snos[0]);
                                                  System.out.println("sl new is-------"+sonumber[0]);
                                                  sno=sonumber[0];
                                                  //---------------
                                                   System.out.println("offlet_no_hid"+x);
                                                   String cheqs[]=request.getParameterValues("offlet_no_hid"+x);
                                                   int cheqnumber[]=new int[cheqs.length];
                                                   System.out.println(cheqs.length);
                                                  cheqnumber[0]=Integer.parseInt(cheqs[0]);
                                                   System.out.println("cheqno new is-------"+cheqnumber[0]);
                                                  cheqno=cheqnumber[0];
                                                  
                                                //---------------receipt year
                                                
                                                  String ryear[]=request.getParameterValues("receiptyear"+x);
                                                  int recyear[]=new int[ryear.length];
                                                 // System.out.println(cheqs.length);
                                                  recyear[0]=Integer.parseInt(ryear[0]);
                                               //   System.out.println("ryear new is-------"+recyear[0]);
                                                  receiptYEAR=recyear[0];
                                                 //---------------receipt month
                                               
                                                 String rmonth[]=request.getParameterValues("receiptmonth"+x);
                                                 int recmonth[]=new int[rmonth.length];
                                                 //System.out.println(cheqs.length);
                                                 recmonth[0]=Integer.parseInt(rmonth[0]);
                                                 //System.out.println("cheqno new is-------"+cheqnumber[0]);
                                                 receiptMONTH=recmonth[0]; 
                                                 
                                               //---------------receipt month
                                                 
                                                 String req_amt[]=request.getParameterValues("req_amt_hid"+x);
                                                 double req_int_amt[]=new double[req_amt.length];
                                                
                                                 req_int_amt[0]=Double.parseDouble(req_amt[0]);
                                                
                                                 required_amt=req_int_amt[0]; 
                                                 
                                                 
                                                  //------------
                                                   String fundtypenew[] = request.getParameterValues("fundtype_hid"+x);
                                                   String fundtypechar[]= new String[fundtypenew.length];
                                                   
                                                       fundtypenew[0] = fundtypenew[0];
                                                       fundtypechar[0] = fundtypenew[0];
                                                       System.out.println("The Fund Type Value = "+fundtypechar[0]);
                                                       fundcompare=fundtypechar[0];
                                                       //---------------
                                                        String cheqdatenew[] = request.getParameterValues("let_date_hid"+x);
                                                        String cheqdatechar[] = new String[cheqdatenew.length];
                                                        
                                                            cheqdatenew[0] = cheqdatenew[0];
                                                            cheqdatechar[0] = cheqdatenew[0];
                                                            System.out.println("The cheqdate = "+cheqdatechar[0]);
                                                            cheqdate=cheqdatechar[0]; 
                                                   String[] sd2 =cheqdate.split("/");
                                                   c = new GregorianCalendar(Integer.parseInt(sd2[2]), Integer.parseInt(sd2[1]) - 1,Integer.parseInt(sd2[0]));
                                                   java.util.Date d2 = c.getTime();
                                                   che_DD_date = new Date(d2.getTime());
                                                   System.out.println("cheque_date " + che_DD_date);
                                                   try {
                                                            
                                                   
                                                          ps2 = con.prepareStatement("UPDATE FAS_CHEQUE_DISHONOUR SET REVERT_CHEQDISHONOUR_STATUS=?,UPDATED_DATE=?, UPDATED_BY_USER_ID=? WHERE ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID=? AND CASHBOOK_YEAR=? AND CASHBOOK_MONTH=? " +
                                                                                     " AND DOC_NO=? AND CHEQUE_DD_NO::numeric=? ");
                                                            
                                                          
                                                          ps2.setString(1,"Y");
                                                          ps2.setTimestamp(2, ts);
                                                          ps2.setString(3, update_user);
                                                          ps2.setInt(4, accunit);
                                                          ps2.setInt(5, office_id);
                                                          ps2.setInt(6, year);
                                                          ps2.setInt(7, month);
                                                          ps2.setInt(8, vno);
                                                          ps2.setInt(9, cheqno );
                                                        
                                                            
                                                          
                                                          ps2.executeUpdate();
                                                          ps2.close();
                                                           xml = xml + "<flag>success</flag>";
                                                           System.out.println("here update in Cheque Dishonour entry ok");
                                                        } catch (Exception e) {
                                                            System.out.println("exception in update Cheque Dishonour entry:" + e);
                                                                  }
                                                   if (fundcompare.equalsIgnoreCase("Bank Receipt")){                 
                                                   try {
                                                             
                                                    
                                                           ps2 = con.prepareStatement("UPDATE FAS_RECEIPT_TRANSACTION SET CHEQ_DISHONOUR_STATUS=?,UPDATED_DATE=?, UPDATED_BY_USER_ID=? WHERE ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID=?  " +
                                                                                      " AND RECEIPT_NO=? and CHEQUE_DD_NO::numeric=? and CHEQUE_DD_DATE=?  ");
                                                             
                                                           
                                                             ps2.setString(1,"");
                                                             ps2.setTimestamp(2, ts);
                                                             ps2.setString(3, update_user);
                                                             ps2.setInt(4, accunit);
                                                             ps2.setInt(5, office_id);
                                                             ps2.setInt(6, vno);
                                                             ps2.setInt(7, cheqno );
                                                             ps2.setDate(8,che_DD_date);
                                                             
                                                           
                                                           ps2.executeUpdate();
                                                           ps2.close();
                                                            xml = xml + "<flag>success</flag>";
                                                            System.out.println("here update in payment entry ok");
                                                         } catch (Exception e) {
                                                             System.out.println("exception in update payment entry:" + e);
                                                                   }
                                                   }
                                                   else{
                                                       try {
                                                                
                                                       
                                                              ps2 = con.prepareStatement("UPDATE FAS_FUND_RECEIPT_BY_OFFICE SET CHEQ_DISHONOUR_STATUS=?,UPDATED_DATE=?,UPDATED_BY_USER_ID=? WHERE ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID=? " +
                                                                                         " AND RECEIPT_NO=? and CHEQUE_DD_NO::numeric=? and CHEQUE_DD_DATE=?");
                                                              
                                                                ps2.setString(1,"");
                                                                ps2.setTimestamp(2, ts);
                                                                ps2.setString(3, update_user);
                                                                ps2.setInt(4, accunit);
                                                                ps2.setInt(5, office_id);
                                                                ps2.setInt(6, vno);
                                                                ps2.setInt(7, cheqno );
                                                                ps2.setDate(8,che_DD_date);
                                                                
                                                              
                                                              ps2.executeUpdate();
                                                              ps2.close();
                                                               xml = xml + "<flag>success</flag>";
                                                               System.out.println("here update in payment entry ok");
                                                            } catch (Exception e) {
                                                                System.out.println("exception in journal entry:" + e);
                                                                      }
                                                   }
                                         
                                                   try{
                                                	   ps2 = con.prepareStatement("UPDATE FAS_REMITTANCE SET CHEQ_DISHONOUR_STATUS=?,UPDATED_DATE=?,UPDATED_BY_USER_ID=? WHERE ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID=? AND CASHBOOK_YEAR=? AND CASHBOOK_MONTH=? " +
                                                       " and CHEQ_DISHONOUR_STATUS='Y' and AMOUNT_REMITTED=?");
                            
						                              ps2.setString(1,"");
						                              ps2.setTimestamp(2, ts);
						                              ps2.setString(3, update_user);
						                              ps2.setInt(4, accunit);
						                              ps2.setInt(5, office_id);
						                              ps2.setInt(6, receiptYEAR);
						                              ps2.setInt(7, receiptMONTH);
						                              ps2.setDouble(8,required_amt);
						                            
						                              
						                            
						                            ps2.executeUpdate();
						                            ps2.close();
						                             xml = xml + "<flag>success</flag>";
						                             System.out.println("here update in Remittance ok"); 
                                                   }
                                                   catch (Exception e5)
                                                   {
                                                	   System.out.println("exception in remittance cancel:::"+e5);
                                                   }
                                                   
                                                   
                                               System.out.println("comes inside flag else part");
                                   
                                                String ltrstatus=""; int dname1=0; int vnumber=0; int snumber=0;
                                               //update FUND_ALLOTMENT_TRANSACTION set SL_NO=?,REF_NO=?,REF_DATE=?,FUND_TYPE=?,AMOUNT=?,PARTICULARS=?,FUND_REQUESTED=?,voucher_no=?,updated_by_user_id=?,updated_date=?,CHEQUE_OR_DD=?,CHEQUE_DD_NO=?,CHEQUE_DD_DATE=?,HO_REF_NO=?,HO_REF_DATE=?,REASON =? where ACCOUNTING_UNIT_ID=? and office_id=? and CASHBOOK_YEAR=? and cashbook_month=? and LETTER_GEN='N' "); 
                                              //  for(int j=0;j<refno.length;j++) {
                                               
                                               String sql_update="update FAS_JOURNAL_MASTER set JOURNAL_STATUS=?,UPDATED_DATE=?, UPDATED_BY_USER_ID=? where JOURNAL_STATUS=? and JOURNAL_TYPE_CODE=? and ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_MONTH=? and CASHBOOK_YEAR=? and CHEQUE_NO::numeric=? and CHEQUE_DATE=?";
                                         
                                                   ps2=con.prepareStatement(sql_update);
                                                   ps2.setString(1,"C");
                                                   ps2.setTimestamp(2, ts);
                                                   ps2.setString(3, update_user);
                                                   ps2.setString(4,"L");
                                                   ps2.setInt(5,57); 
                                                   ps2.setInt(6,accunit);   
                                                   ps2.setInt(7,office_id);
                                                   ps2.setInt(8,month);
                                                   ps2.setInt(9,year);
                                                   //  ps2.setInt(10,vno);
                                                   ps2.setInt(10,cheqno);
                                                   ps2.setDate(11,che_DD_date);
                                                   ps2.executeUpdate();
                                               ps2.executeUpdate();
                                               
                                             System.out.println("update FAS_JOURNAL_MASTER set JOURNAL_STATUS='C',UPDATED_DATE='"+ts+"', UPDATED_BY_USER_ID='"+update_user+"' where JOURNAL_STATUS='L'and JOURNAL_TYPE_CODE=56 and ACCOUNTING_UNIT_ID="+accunit+" and ACCOUNTING_FOR_OFFICE_ID="+office_id+" and CASHBOOK_MONTH="+month+" and CASHBOOK_YEAR="+year+" and VOUCHER_NO="+vno+" and CHEQUE_NO="+cheqno+"");
                                               
                                                sno=0;
                                                vno=0;
                                                cheqno=0;
                                               
                                               
                                               ps2.close();
                                                 System.out.println("last" );
                                               
                                                   //con.commit();
                                              sendMessage(response,"The Revert Cheque Cancellation done successfully","ok");
                                               }
                                           }
                                
                                             
                                             catch(Exception e) 
            {
                try{con.rollback();}catch(SQLException sqle){System.out.println("Excep"+sqle);}
               
                System.out.println("Exception occur due to "+e);
            }
           
            finally
            {
                System.out.println("done here");
                try{con.setAutoCommit(true);  }catch(SQLException sqle){System.out.println("Excep"+sqle);}
            }
        }
   
             private void sendMessage(HttpServletResponse response,String msg,String bType)
             {
             try
             {
             System.out.println("Inside.....................");
                 String url="org/Library/jsps/MessengerOkBack.jsp?message=" + msg + "&button=" + bType;
                 response.sendRedirect(url);
             }
             catch(Exception e)
             {
             System.out.println("error in messenger"+e);
             }
             }
             
             
             }