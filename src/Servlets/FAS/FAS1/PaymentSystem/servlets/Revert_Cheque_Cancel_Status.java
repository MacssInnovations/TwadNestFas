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


public class Revert_Cheque_Cancel_Status extends HttpServlet {
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
                if(strCommand.equalsIgnoreCase("check_TB"))
                {
                   
                    System.out.println("check_TB if condi");
                    xml="<response><command>check_TB</command>";
                    
                 try
                 {  
                  
                        ps=con.prepareStatement("select TB_STATUS from FAS_TRIAL_BALANCE_STATUS where ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=?  and CASHBOOK_MONTH=? ");
                           ps.setInt(1,accunit);
                           ps.setInt(2,year);
                           ps.setInt(3,month);
                           rs=ps.executeQuery();
                           //System.out.println(rs.next());
                            if(rs.next())
                             {
                                if(rs.getString("TB_STATUS").equalsIgnoreCase("N"))
                                    xml=xml+"<flag>success</flag>";
                                else
                                  xml=xml+"<flag>failure</flag>";
                             }
                          else
                                xml=xml+"<flag>success</flag>";
                            
                            }
                            catch(Exception e)
                            {
                                System.out.println("catch..HERE.in TB_date "+e);
                                xml=xml+"<flag>failure</flag>";
                            }
                      
                }      
             
           if(strCommand.equalsIgnoreCase("loadCheque")) 
            {
            int count=0;
            
                
    xml="<response><command>loadCheque</command>";
    sql="select rank() OVER (ORDER BY DOCUMENT_NO) as rwnum,cheqcancel_date,DOCUMENT_TYPE,DOCUMENT_NO,doc_date,CHEQUE_OR_DD,oldcheq_date, \n" + 
              "oldcheqamt,REMARKS,ISSUE_CHEQUE,ISSUE_CHEQUE_NO,ch_date,newcheqamt,CHEQUE_DD_NO, GJVNO,GJVDATE  \n" + 
              "from (\n" + 
              "select rownum,to_char(a.CHEQ_CANCEL_DATE,'DD/MM/YYYY') as cheqcancel_date,a.DOCUMENT_TYPE,a.DOCUMENT_NO,to_char(a.DOCUMENT_DATE,'DD/MM/YYYY') as doc_date,\n" + 
              "a.CHEQUE_OR_DD,to_char(a.CHEQUE_DD_DATE,'DD/MM/YYYY') as oldcheq_date, \n" + 
              "trim(to_char(a.AMOUNT,'99999999999999.99')) as oldcheqamt,a.REMARKS,a.ISSUE_CHEQUE,a.ISSUE_CHEQUE_NO, \n" + 
              "to_char(a.ISSUE_CHEQUE_DATE,'DD/MM/YYYY') as ch_date, \n" + 
              "trim(to_char(a.ISSUE_CHEQUE_AMOUNT,'99999999999999.99')) as newcheqamt, a.CHEQUE_DD_NO,  \n" + 
              "b.VOUCHER_NO as GJVNO,to_char(b.VOUCHER_DATE,'DD/MM/YYYY') as GJVDATE from FAS_CHEQUE_CANCEL a,FAS_JOURNAL_MASTER b, fas_payment_master c,\n" + 
              " fas_journal_transaction d, fas_cross_reference e  \n" + 
              " where a.ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID  \n" + 
              "and a.ACCOUNTING_FOR_OFFICE_ID=b.ACCOUNTING_FOR_OFFICE_ID \n" + 
              "and a.CASHBOOK_YEAR=b.CASHBOOK_YEAR  \n" + 
              "and a.CASHBOOK_MONTH=b.CASHBOOK_MONTH  \n" + 
              "and a.CHEQUE_DD_NO=b.CHEQUE_NO  \n" + 
              "and a.CHEQUE_DD_DATE=b.CHEQUE_DATE " +
              "and a.CHEQUE_DD_NO=e.VOUCHER_NO "+
              "and CHEQ_CANCEL_DATE=e.ORIGINAL_DATE "+
            //  "and a.ISSUE_CHEQUE='N' and a.CREA_PAY_VOU='N'  \n" + 
              "and (a.REVERT_CHEQCANCEL_STATUS is null or a.REVERT_CHEQCANCEL_STATUS!='Y')\n" + 
              "   AND a.DOCUMENT_NO = c.voucher_no\n" + 
              "   and d.cb_ref_no = c.voucher_no\n" + 
              "   AND a.document_date = c.payment_date\n" + 
              "   AND c.cheq_cancel_status = 'Y'\n" + 
              "   AND a.accounting_unit_id = d.accounting_unit_id\n" + 
              "   AND a.accounting_for_office_id = d.accounting_for_office_id\n" + 
              "   AND a.cashbook_year =d.cashbook_year\n" + 
              "   AND a.cashbook_month = d.cashbook_month\n" + 
              "   AND b.voucher_no=d.voucher_no and " +
              "a.ACCOUNTING_UNIT_ID=? and a.ACCOUNTING_FOR_OFFICE_ID=? and  \n" + 
              "a.CASHBOOK_YEAR=? and a.CASHBOOK_MONTH=?\n" + 
            /*  "union all \n" + 
              "select rownum,to_char(a.CHEQ_CANCEL_DATE,'DD/MM/YYYY') as cheqcancel_date,a.DOCUMENT_TYPE,a.DOCUMENT_NO,to_char(a.DOCUMENT_DATE,'DD/MM/YYYY') as doc_date,\n" + 
              "a.CHEQUE_OR_DD,to_char(a.CHEQUE_DD_DATE,'DD/MM/YYYY') as oldcheq_date, \n" + 
              "trim(to_char(a.AMOUNT,'99999999999999.99')) as oldcheqamt,a.REMARKS,a.ISSUE_CHEQUE,\n" + 
              "a.ISSUE_CHEQUE_NO,to_char(a.ISSUE_CHEQUE_DATE,'DD/MM/YYYY') as ch_date,\n" + 
              "trim(to_char(a.ISSUE_CHEQUE_AMOUNT,'99999999999999.99')) as newcheqamt,a.CHEQUE_DD_NO,  \n" + 
              "0 as GJVNO,'---' as GJVDATE from FAS_CHEQUE_CANCEL a,  \n" + 
              "  fas_cross_reference e  "+
               "where " +
               //"a.ISSUE_CHEQUE='Y' and\n" +
              "(a.REVERT_CHEQCANCEL_STATUS is null or a.REVERT_CHEQCANCEL_STATUS!='Y')  \n" + 
             " and a.CHEQUE_DD_NO=e.VOUCHER_NO "+
             "   and CHEQ_CANCEL_DATE=e.ORIGINAL_DATE and  "+
              "a.ACCOUNTING_UNIT_ID=? and a.ACCOUNTING_FOR_OFFICE_ID=? and  \n" + 
              "a.CASHBOOK_YEAR=? and a.CASHBOOK_MONTH=?  */
              " ) z order by DOCUMENT_NO ";

                    
                        
                          try
                          {
                          System.out.println("1:::"+sql);
                        
                          ps=con.prepareStatement(sql);
                          ps.setInt(1,accunit);
                          ps.setInt(2,offid);
                          ps.setInt(3,year);
                          ps.setInt(4,month);
//                          ps.setInt(5,accunit);
//                          ps.setInt(6,offid);
//                          ps.setInt(7,year);
//                          ps.setInt(8,month);
                          
                          xml=xml+"<flag>success</flag><Ucode>"+cmbAcc_UnitCode+"</Ucode><Offid>"+cmbOffice_code+
                          "</Offid><txtCB_Year>"+year+"</txtCB_Year><txtCB_Month>"+month+"</txtCB_Month>";
                       //   System.out.println("xml......");
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


                                     if (rs.getString("ch_date") == null) {
                                         newcheq_date = "-";
                                     } else {
                                         newcheq_date = rs.getString("ch_date");
                                         System.out.println("newcheq_date" + newcheq_date);
                                     }
                                 } catch (Exception e) {
                                     System.out.println("Error in getting newcheq_date values"+e);

                                 }
                                 try {


                                     if (rs.getString("ISSUE_CHEQUE") == null) {
                                         newcheq_status = "N";
                                     } else {
                                         newcheq_status = rs.getString("ISSUE_CHEQUE");
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


                                     if (rs.getInt("ISSUE_CHEQUE_NO") ==0 ) {
                                         new_cheqno = 0;
                                     } else {
                                         new_cheqno = rs.getInt("ISSUE_CHEQUE_NO");
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
                                 xml=xml+"<doc_no>"+rs.getString("DOCUMENT_NO")+"</doc_no>";
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
             //   System.out.println("here "+strCommand.equalsIgnoreCase("searchByDate"));
                Date txtFrom_date=null,txtTo_date=null;
                Calendar c;
                String sqldate="";
           if(strCommand.equalsIgnoreCase("searchByDate"))
                { 
                   // String CONTENT_TYPE = "text/xml; charset=windows-1252";
                  xml="<response><command>searchByDate</command>";
               //   System.out.println("here>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "+strCommand.equalsIgnoreCase("searchByDate"));
                 
                  String[] sd=request.getParameter("txtFrom_date").split("/");
                  c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
                  java.util.Date d=c.getTime();
                  txtFrom_date=new Date(d.getTime());
               //   System.out.println("from_date "+txtFrom_date);
                  
                  sd=request.getParameter("txtTo_date").split("/");
                  c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
                  d=c.getTime();
                  txtTo_date=new Date(d.getTime());
                //  System.out.println("txtTo_date "+txtTo_date);
                  int count=0;
                  sqldate="SELECT rank() OVER (ORDER BY DOCUMENT_NO) AS rwnum,  "+
							"  cheqcancel_date,DOCUMENT_TYPE,DOCUMENT_NO, doc_date,CHEQUE_OR_DD,oldcheq_date,  "+
                  "   oldcheqamt,REMARKS,ISSUE_CHEQUE, ISSUE_CHEQUE_NO, ch_date, newcheqamt, "+
                  " 		  CHEQUE_DD_NO,  GJVNO, GJVDATE  FROM (      "+
                  " 		SELECT rownum, "+
                  " 		  TO_CHAR(a.CHEQ_CANCEL_DATE,'DD/MM/YYYY') AS cheqcancel_date, "+
                  " 		  a.DOCUMENT_TYPE, a.DOCUMENT_NO, TO_CHAR(a.DOCUMENT_DATE,'DD/MM/YYYY') AS doc_date,  "+
                  " 		    a.CHEQUE_OR_DD, "+
                  " 		  TO_CHAR(a.CHEQUE_DD_DATE,'DD/MM/YYYY')           AS oldcheq_date, "+
                  " 		    trim(TO_CHAR(a.AMOUNT,'99999999999999.99')) AS oldcheqamt, "+
                  " 		  a.REMARKS, a.ISSUE_CHEQUE, a.ISSUE_CHEQUE_NO, "+
                  " 		    TO_CHAR(a.ISSUE_CHEQUE_DATE,'DD/MM/YYYY')                AS ch_date, "+
                  " 		    trim(TO_CHAR(a.ISSUE_CHEQUE_AMOUNT,'99999999999999.99')) AS newcheqamt, "+
                  " 		  a.CHEQUE_DD_NO,  b.VOUCHER_NO  AS GJVNO, "+
                  " 		  TO_CHAR(b.VOUCHER_DATE,'DD/MM/YYYY') AS GJVDATE "+
                  " 		FROM FAS_CHEQUE_CANCEL a,  FAS_JOURNAL_MASTER b, fas_payment_master c, "+
                  " 		  fas_journal_transaction d, fas_cross_reference e "+
                  " 		WHERE a.ACCOUNTING_UNIT_ID       =b.ACCOUNTING_UNIT_ID        "+ 
                  " 		AND a.ACCOUNTING_FOR_OFFICE_ID   =b.ACCOUNTING_FOR_OFFICE_ID   "+
                  " 		AND a.CASHBOOK_YEAR              =b.CASHBOOK_YEAR       "+       
                  " 		AND a.CASHBOOK_MONTH             =b.CASHBOOK_MONTH     "+        
                  " 		AND a.CHEQUE_DD_NO               =b.CHEQUE_NO       "+          
                  " 		AND a.CHEQUE_DD_DATE             =b.CHEQUE_DATE "+
                  " 		and a.CHEQUE_DD_NO=e.VOUCHER_NO "+
                  " 		and a.CHEQUE_DD_DATE=e.ORIGINAL_DATE "+
                  " 		AND a.ISSUE_CHEQUE               ='N' "+
                  " 		AND a.CREA_PAY_VOU               ='N'   "+
                  " 		AND (a.REVERT_CHEQCANCEL_STATUS IS NULL "+
                  " 		OR a.REVERT_CHEQCANCEL_STATUS!   ='Y')    "+                     
                  " 		AND a.DOCUMENT_NO                = c.voucher_no        "+        
                  " 		AND d.cb_ref_no                  = c.voucher_no    "+            
                  " 		AND a.document_date              = c.payment_date   "+           
                  " 		AND c.cheq_cancel_status         = 'Y'          "+               
                  " 		AND a.accounting_unit_id         = d.accounting_unit_id   "+     
                  " 		AND a.accounting_for_office_id   = d.accounting_for_office_id  "+
                  " 		AND a.cashbook_year              =d.cashbook_year     "+         
                  " 		AND a.cashbook_month             = d.cashbook_month  "+          
                  " 		AND b.voucher_no                 =d.voucher_no "+
                  " 		AND  a.ACCOUNTING_UNIT_ID       =? "+
                  " 		AND a.ACCOUNTING_FOR_OFFICE_ID   =? "+
                  " 		AND a.DOCUMENT_DATE between ? and ? "+
                  " 		UNION ALL   "+
                  " 		SELECT rownum, "+
                  " 		  TO_CHAR(a.CHEQ_CANCEL_DATE,'DD/MM/YYYY') AS cheqcancel_date, "+
                  " 		  a.DOCUMENT_TYPE,  a.DOCUMENT_NO, "+
                  " 		  TO_CHAR(a.DOCUMENT_DATE,'DD/MM/YYYY') AS doc_date, "+
                  " 		    a.CHEQUE_OR_DD, TO_CHAR(a.CHEQUE_DD_DATE,'DD/MM/YYYY') AS oldcheq_date, "+
                  " 		    trim(TO_CHAR(a.AMOUNT,'99999999999999.99')) AS oldcheqamt, "+
                  " 		  a.REMARKS, a.ISSUE_CHEQUE, a.ISSUE_CHEQUE_NO, "+
                  " 		  TO_CHAR(a.ISSUE_CHEQUE_DATE,'DD/MM/YYYY')  AS ch_date, "+
                  " 		    trim(TO_CHAR(a.ISSUE_CHEQUE_AMOUNT,'99999999999999.99')) AS newcheqamt, "+
                  " 		  a.CHEQUE_DD_NO, 0 AS GJVNO, "+
                  " 		  '---'  AS GJVDATE "+
                  " 		FROM FAS_CHEQUE_CANCEL a,  "+
                  " 		 fas_cross_reference e "+
                  " 		WHERE a.ISSUE_CHEQUE                 ='Y' "+
                  " 		AND  (a.REVERT_CHEQCANCEL_STATUS IS NULL "+
                  " 		OR a.REVERT_CHEQCANCEL_STATUS!       ='Y') "+
                  " 		and a.CHEQUE_DD_NO=e.VOUCHER_NO "+
                  " 		and a.CHEQUE_DD_DATE=e.ORIGINAL_DATE "+
                  " 		AND   a.ACCOUNTING_UNIT_ID        =? "+
                  " 		AND a.ACCOUNTING_FOR_OFFICE_ID       =? "+
                  " 		AND a.DOCUMENT_DATE between ? and ?) "+
                  " 		ORDER BY DOCUMENT_NO  ";
               
                 
                 try
                 {
               //  System.out.println("11111111111111111"+sqldate);
               
                 ps=con.prepareStatement(sqldate);
                 ps.setInt(1,accunit);
                 ps.setInt(2,offid);
                 ps.setDate(3,txtFrom_date);
                 ps.setDate(4,txtTo_date);
                 ps.setInt(5,accunit);
                 ps.setInt(6,offid);
                 ps.setDate(7,txtFrom_date);
                 ps.setDate(8,txtTo_date);
                 
                 xml=xml+"<flag>success</flag><Ucode>"+cmbAcc_UnitCode+"</Ucode><Offid>"+cmbOffice_code+
                 "</Offid><txtCB_Year>"+year+"</txtCB_Year><txtCB_Month>"+month+"</txtCB_Month>";
              //   System.out.println("xml......");
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


                            if (rs.getString("ch_date") == null) {
                                newcheq_date = "-";
                            } else {
                                newcheq_date = rs.getString("ch_date");
                                System.out.println("newcheq_date" + newcheq_date);
                            }
                        } catch (Exception e) {
                            System.out.println("Error in getting newcheq_date values"+e);

                        }
                        try {


                            if (rs.getString("ISSUE_CHEQUE") == null) {
                                newcheq_status = "N";
                            } else {
                                newcheq_status = rs.getString("ISSUE_CHEQUE");
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


                            if (rs.getInt("ISSUE_CHEQUE_NO") ==0 ) {
                                new_cheqno = 0;
                            } else {
                                new_cheqno = rs.getInt("ISSUE_CHEQUE_NO");
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
                        xml=xml+"<doc_no>"+rs.getString("DOCUMENT_NO")+"</doc_no>";
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
             
            String fundcompare="",cheqdate="";
             int year=Integer.parseInt(cashbookyear);
             int month=Integer.parseInt(cashbookmonth);
          //   System.out.println("Month:========"+month);
             int accunit=Integer.parseInt(accunitid);
            // System.out.println(accunit);
             int office_id=Integer.parseInt(officeid);  
           //  System.out.println(office_id);
            String update_user = (String)session.getAttribute("UserId");
            long l = System.currentTimeMillis();
            Timestamp ts = new Timestamp(l);
             int voucherno=0;
             String sel[]=null;
            Date che_DD_date = null;
                                 try{
                                 
                                               int selrow=0;
                                               sel=request.getParameterValues("sel");
                                             //  int selnumber[]=new int[sel.length];
                                               int vno=0,sno=0,offno=0,cheqno=0;
                                               
                                               String vnos[]=request.getParameterValues("vno_hid");
                                           
                                               String snos[]=request.getParameterValues("sl_no_hid");
                                           
                                               String cheqs[]=request.getParameterValues("offlet_no_hid");
                                        
                                               for(int i=0;i<sel.length;i++)
                                               {
//                                            	   System.out.println("sel.length"+sel.length);
//                                             System.out.println("sel[i]"+sel[i]);
                                            	   int sap=Integer.parseInt(sel[i]);
                                            	
                                            	  vno=Integer.parseInt(vnos[sap]); 
                                            
                                            	   cheqno=Integer.parseInt(cheqs[sap]); 
                                            	   
                                                   String fundtypenew[] = request.getParameterValues("fundtype_hid");
//                                                
                                                       //---------------
                                                        String cheqdatenew[] = request.getParameterValues("let_date_hid");
                                                       
                                                            
//                                            

                                                   try {
                                                           
                                                          ps2 = con.prepareStatement("UPDATE FAS_CHEQUE_CANCEL SET REVERT_CHEQCANCEL_STATUS=?,UPDATED_DATE=?, UPDATED_BY_USER_ID=? WHERE ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID=? AND CASHBOOK_YEAR=? AND CASHBOOK_MONTH=? " +
                                                                                     " AND DOCUMENT_NO=? AND CHEQUE_DD_NO=? ");
                                                            
                                                          
                                                          ps2.setString(1,"Y");
                                                          ps2.setTimestamp(2, ts);
                                                          ps2.setString(3, update_user);
                                                          ps2.setInt(4, accunit);
                                                          ps2.setInt(5, office_id);
                                                          ps2.setInt(6, year);
                                                          ps2.setInt(7, month);
                                                          ps2.setInt(8, vno);
                                                          ps2.setInt(9, cheqno);
                                                        
                                                         
                                                          ps2.executeUpdate();
                                                          ps2.close();
                                                           xml = xml + "<flag>success</flag>";
                                                           System.out.println("here update in cheque cancel entry ok");
                                                        } catch (Exception e) {
                                                            System.out.println("exception in update payment transaction entry:" + e);
                                                                  }
                                                                  
                                                   if (fundtypenew[sap].equalsIgnoreCase("Payment")){                
                                                   try {
                                                            
                                                   
                                                          ps2 = con.prepareStatement("UPDATE FAS_PAYMENT_TRANSACTION SET CHEQ_CANCEL_STATUS=?,UPDATED_DATE=?, UPDATED_BY_USER_ID=? WHERE ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID=? " +
                                                                                     " AND VOUCHER_NO=? AND CHEQUE_DD_NO=? and CHEQUE_DD_DATE=to_date(?,'dd-mm-yy') ");
                                                          
                                                          ps2.setString(1,"N");
                                                          ps2.setTimestamp(2, ts);
                                                          ps2.setString(3, update_user);
                                                          ps2.setInt(4, accunit);
                                                          ps2.setInt(5, office_id);
                                                          ps2.setInt(6, vno);
                                                          ps2.setInt(7, cheqno);
                                                          ps2.setString(8,cheqdatenew[sap]);
                                                        
                                                            
                                                          
                                                          ps2.executeUpdate();
                                                          ps2.close();
                                                           xml = xml + "<flag>success</flag>";
                                                           System.out.println("here update in payment transaction entry ok");
                                                        } catch (Exception e) {
                                                            System.out.println("exception in update payment transaction entry:" + e);
                                                                  }
                                             
                                         
                                               System.out.println("comes inside flag else part");
                                                   }
                                              else{
                                                  try {
                                                           
                                                  
                                                         ps2 = con.prepareStatement("UPDATE FAS_FUND_TRF_FROM_OFFICE SET CHEQ_CANCEL_STATUS=?,UPDATED_DATE=?,UPDATED_BY_USER_ID=? WHERE ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID=? " +
                                                                                    " AND VOUCHER_NO=? and CHEQUE_DD_NO=? and CHEQUE_DD_DATE=to_date(?,'dd-mm-yy') ");
                                                         
                                                         ps2.setString(1,"N");
                                                         ps2.setTimestamp(2, ts);
                                                         ps2.setString(3, update_user);  
                                                         ps2.setInt(4, accunit);
                                                         ps2.setInt(5, office_id);
                                                         ps2.setInt(6, vno);
                                                         ps2.setInt(7, cheqno ); 
                                                         ps2.setString(8,cheqdatenew[sap]);  
                                                         
                                                         ps2.executeUpdate();
                                                         ps2.close();
                                                          xml = xml + "<flag>success</flag>";
                                                          System.out.println("here update in fundtransfer entry ok");
                                                       } catch (Exception e) {
                                                           System.out.println("exception in journal entry:" + e);
                                                                 }
                                              }
                                              try{  
                                              int count =0;
                                               String sql_update="update FAS_JOURNAL_MASTER set JOURNAL_STATUS=?,UPDATED_DATE=?, UPDATED_BY_USER_ID=? where JOURNAL_STATUS=? and JOURNAL_TYPE_CODE=? and ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_MONTH=? and CASHBOOK_YEAR=? and CHEQUE_NO=? and CHEQUE_DATE=to_date(?,'dd-mm-yy') ";
                                       //  System.out.println("jourrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
                                               ps2=con.prepareStatement(sql_update);
                                               ps2.setString(1,"C");
                                               ps2.setTimestamp(2, ts);
                                               ps2.setString(3, update_user);
                                               ps2.setString(4,"L");
                                               ps2.setInt(5,56); 
                                               ps2.setInt(6,accunit);   
                                               ps2.setInt(7,office_id);
                                               ps2.setInt(8,month);
                                               ps2.setInt(9,year);
                                             //  ps2.setInt(10,vno); 
                                               ps2.setInt(10,cheqno);
                                               ps2.setString(11,cheqdatenew[sap]);
                                               ps2.executeUpdate();
                                               
                                        //      System.out.println("update FAS_JOURNAL_MASTER set JOURNAL_STATUS='C',UPDATED_DATE='"+ts+"', UPDATED_BY_USER_ID='"+update_user+"' where JOURNAL_STATUS='L'and JOURNAL_TYPE_CODE=56 and ACCOUNTING_UNIT_ID="+accunit+" and ACCOUNTING_FOR_OFFICE_ID="+office_id+" and CASHBOOK_MONTH="+month+" and CASHBOOK_YEAR="+year+" and VOUCHER_NO="+vno+" and CHEQUE_NO="+cheqno+"");
                                                                                   
                                               ps2.close();
                                               
                                                  xml = xml + "<flag>success</flag>";
                                                  System.out.println("here update in cheque cancel entry ok");
                                              
                                                
                                              }
                                                   catch (Exception e) {
                                                 System.out.println("exception in update journal master entry:" + e);
                                                      }
                                                //   con.commit();
                                             
                                               }
                                               sendMessage(response,"The Revert Cheque Cancellation done successfully","ok");
                                           }
                                
                                             
               catch(Exception e) 
            {
            	   sendMessage(response,"Failure in Updating Revert Cheque Cancellation","ok");
               
                System.out.println("Exception occur due to "+e);
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