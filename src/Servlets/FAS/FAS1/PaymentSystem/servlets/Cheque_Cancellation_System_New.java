package Servlets.FAS.FAS1.PaymentSystem.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;

import java.sql.DriverManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Statement;
import java.sql.Timestamp;

import java.sql.Types;

import java.text.DateFormat;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.GregorianCalendar;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;
import java.text.*;

import java.util.ArrayList;

//import sun.management.Flag;

public class Cheque_Cancellation_System_New extends HttpServlet {
    private static final String CONTENT_TYPE = 
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, 
                      HttpServletResponse response) throws ServletException, 
                                                           IOException {

        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                return;
            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }
        Connection con = null;
        ResultSet rs = null, rs2 = null, rs3 = null;
        //CallableStatement cs=null,cs1=null;
        PreparedStatement ps = null, ps1 = null, ps2 = null, ps3 = null;
        String xml = "";
        String strCommand = "";
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
            //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

        }

        response.setContentType(CONTENT_TYPE);
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();

        try {
            strCommand = request.getParameter("Command");
            System.out.println("assign..here command..." + strCommand);
        }

        catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }
        int cmbAcc_UnitCode = 0, cmbOffice_code = 0;
        Date txtch_date = null,txtcur_date=null,txtdate2=null;
        String CashBook_Year = request.getParameter("txtCB_Year");
        String CashBook_Month = request.getParameter("txtCB_Month");
        String txtdoctype = request.getParameter("doc_type");
        Date txtCrea_date=null;
        int CashBookYear = 0;
        int CashBookMonth = 0;
       String txtCheque_DD_NO = "";
        String doctype = "";
        try {
            doctype = request.getParameter("doc_type");
        } catch (Exception e) {
            System.out.println("exception in doctype" + e);
        }
        System.out.println("doctype " + doctype);
        try {
            cmbAcc_UnitCode = 
                    Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
        } catch (NumberFormatException e) {
            System.out.println("exception" + e);
        }
        System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);


        try {
            cmbOffice_code = 
                    Integer.parseInt(request.getParameter("cmbOffice_code"));
        } catch (NumberFormatException e) {
            System.out.println("exception" + e);
        }
        System.out.println("cmbOffice_code " + cmbOffice_code);
        if (strCommand.equalsIgnoreCase("chequenodetails_cheqwise")){
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            Calendar c;
                Statement st = null;
                ResultSet rs1 = null;
                String ora_dt = null;
                String ora_mn = null;
                String ora_yr = null;
                int date_value = 0;
                int mm = 0;
                String month_value = null;
                int year_value = 0;
                int oramn=0,orayr=0;
                String doc_date = null;
            xml = "<response><command>chequenodetails_cheqwise</command>"; 
                      String[] sd = request.getParameter("txtCrea_date").split("/");
                c = 
                               new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                                            Integer.parseInt(sd[0]));
                                java.util.Date d = c.getTime();
                                txtcur_date = new Date(d.getTime());
                                System.out.println("txtcur_date " + txtcur_date);
                        date_value = Integer.parseInt(sd[0]);
                        mm = Integer.parseInt(sd[1]);
                        year_value = Integer.parseInt(sd[2]);


                        if (mm == 1) {
                            month_value = "jan";
                        } else if (mm == 2) {
                            month_value = "feb";
                        } else if (mm == 3) {
                            month_value = "mar";
                        } else if (mm == 4) {
                            month_value = "apr";
                        } else if (mm == 5) {
                            month_value = "may";
                        } else if (mm == 6) {
                            month_value = "jun";
                        } else if (mm == 7) {
                            month_value = "jul";
                        } else if (mm == 8) {
                            month_value = "aug";
                        } else if (mm == 9) {
                            month_value = "sep";
                        } else if (mm == 10) {
                            month_value = "oct";
                        } else if (mm == 11) {
                            month_value = "nov";
                        } else if (mm == 12) {
                            month_value = "dec";
                        }

                        doc_date = date_value + "-" + month_value + "-" + year_value;
                        
                        
                        System.out.println("doc Date --->"+doc_date);
                        
        
                String sql ="SELECT to_char(to_date('" + doc_date + "') -180,   'dd') AS dt, to_char(to_date('" +doc_date + "') -180,   'mm') AS mn, to_char(to_date('" + doc_date +"') -180,   'yyyy') AS yr  ";

                        try {
                            st = con.createStatement();
                            rs1 = st.executeQuery(sql);
                            while (rs1.next()) {
                                ora_dt = rs1.getString("dt");
                                ora_mn = rs1.getString("mn");
                                ora_yr = rs1.getString("yr");
                                if(ora_mn=="1"||ora_mn=="2"||ora_mn=="3"||ora_mn=="4"||ora_mn=="5"||ora_mn=="6"||ora_mn=="7"||ora_mn=="8"||ora_mn=="9")
                                {
                                   ora_mn="0"+ora_mn;
                                   System.out.println("ora_mn"+ora_mn);
                                }
                                if(ora_dt=="1"||ora_dt=="2"||ora_dt=="3"||ora_dt=="4"||ora_dt=="5"||ora_dt=="6"||ora_dt=="7"||ora_dt=="8"||ora_dt=="9")
                                {
                                   ora_dt="0"+ora_dt;
                                   System.out.println("ora_dt"+ora_dt);
                                }
                            }
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                int ora_year2 = Integer.parseInt(ora_yr);
                int ora_month2 = Integer.parseInt(ora_mn);
               int ora_day2 = Integer.parseInt(ora_dt);
               System.out.println("ora_month2"+ora_month2);
                System.out.println("ora_day2"+ora_day2);
               System.out.println(ora_year2);
             
               String ora_date2 = ora_yr + "-" + ora_mn + "-" + ora_dt;
  System.out.println(ora_date2);
         
                
           
               
            try{
          
        String cheqsql= "select i.CHEQUE_DD_NO as cheqno " + 
            "from FAS_FUND_TRF_FROM_OFFICE i " + 
            "where (i.CHEQUE_OR_DD='C' or i.CHEQUE_OR_DD='D') " + 
            "and (i.CHEQ_CANCEL_STATUS is null or i.CHEQ_CANCEL_STATUS!='Y') " + 
            "and i.ACCOUNTING_UNIT_ID=? " + 
            "and i.ACCOUNTING_FOR_OFFICE_ID=? " + 
            "and  i.TRANSFER_STATUS ='L' and i.CHEQUE_DD_DATE>=to_date('"+ora_date2+"','yyyy-mm-dd') and i.CHEQUE_DD_DATE<=to_date('"+txtcur_date+"','yyyy-mm-dd') " +
            "union " + 
            "select j.CHEQUE_DD_NO as cheqno from FAS_FUND_TRF_FROM_HO_MASTER i,FAS_FUND_TRF_FROM_HO_TRN j  " + 
            "where  i.VOUCHER_NO=j.VOUCHER_NO and (j.CHEQ_CANCEL_STATUS is null or j.CHEQ_CANCEL_STATUS!='Y') " + 
            "and i.ACCOUNTING_UNIT_ID=j.ACCOUNTING_UNIT_ID and  " + 
            "i.ACCOUNTING_FOR_OFFICE_ID=j.ACCOUNTING_FOR_OFFICE_ID  " + 
            "and i.CASHBOOK_YEAR=j.CASHBOOK_YEAR and i.CASHBOOK_MONTH = j.CASHBOOK_MONTH  " + 
            "and (j.CHEQUE_OR_DD='C' or j.CHEQUE_OR_DD='D') and i.TRANSFER_STATUS='L' and " + 
            "i.ACCOUNTING_UNIT_ID=?  and i.ACCOUNTING_FOR_OFFICE_ID=? and " +
            "j.CHEQUE_DD_DATE>=to_date('"+ora_date2+"','yyyy-mm-dd') and j.CHEQUE_DD_DATE<=to_date('"+txtcur_date+"','yyyy-mm-dd') " +
            "union " +     
            "select j.CHEQUE_DD_NO as cheqno from FAS_PAYMENT_MASTER i,FAS_PAYMENT_TRANSACTION j " + 
            "where  i.VOUCHER_NO=j.VOUCHER_NO and (j.CHEQ_CANCEL_STATUS is null or j.CHEQ_CANCEL_STATUS!='Y') " + 
            "and i.ACCOUNTING_UNIT_ID=j.ACCOUNTING_UNIT_ID and  " + 
            "i.ACCOUNTING_FOR_OFFICE_ID=j.ACCOUNTING_FOR_OFFICE_ID  " + 
            "and i.CASHBOOK_YEAR=j.CASHBOOK_YEAR and i.CASHBOOK_MONTH = j.CASHBOOK_MONTH  " + 
            "and (j.CHEQUE_OR_DD='C' or j.CHEQUE_OR_DD='D') and " + 
            "i.ACCOUNTING_UNIT_ID=?  and i.ACCOUNTING_FOR_OFFICE_ID=?  " + 
            "and j.CHEQUE_DD_DATE>=to_date('"+ora_date2+"','yyyy-mm-dd') and j.CHEQUE_DD_DATE<=to_date('"+txtcur_date+"','yyyy-mm-dd')  and i.PAYMENT_STATUS ='L' ";
           System.out.println(cheqsql);
                ps=con.prepareStatement(cheqsql);
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setInt(3, cmbAcc_UnitCode);
                ps.setInt(4, cmbOffice_code);
                ps.setInt(5, cmbAcc_UnitCode);
                ps.setInt(6, cmbOffice_code);
    
                rs = ps.executeQuery();
              
                int count = 0;
                while (rs.next()) {

                    xml = xml +"<Cheq_No>" + rs.getString("cheqno") + "</Cheq_No>";
                        
                
                    count++;
                }
                if (count == 0)
                    xml = xml + "<flag>failure</flag>";
                else
                    xml = xml + "<flag>success</flag>";
                System.out.println("count  " + count);
                ps.close();
                rs.close();
            
            }
                catch (Exception e) {
                                System.out.println("catch..HERE.in load VOUCHER." + e);
                                xml = xml + "<flag>failure</flag>";
                            }
                            xml = xml + "</response>";
                            System.out.println(xml);
                            out.println(xml);
                            
                            
          
            }

        else if (strCommand.equalsIgnoreCase("Voucher_Details")) {
             String CONTENT_TYPE = "text/xml; charset=windows-1252";
             response.setContentType(CONTENT_TYPE);
           //  Calendar c;
             xml = "<response><command>Voucher_Details</command>";
             int txtCheque_No = 0;


             try {
                 txtCheque_No = 
                         Integer.parseInt(request.getParameter("txtCheque_No"));
             } catch (NumberFormatException e) {
                 System.out.println("exception" + e);
             }
             System.out.println("txtCheque_No " + txtCheque_No);

            

             try {
             String cheqsql="select i.VOUCHER_NO as vno from FAS_FUND_TRF_FROM_OFFICE i  \n" + 
             "where (i.CHEQUE_OR_DD='C' or i.CHEQUE_OR_DD='D')  \n" + 
             "and (i.CHEQ_CANCEL_STATUS is null or i.CHEQ_CANCEL_STATUS!='Y') and  i.TRANSFER_STATUS ='L'  \n" + 
             "and i.ACCOUNTING_UNIT_ID=?  \n" + 
             "and i.ACCOUNTING_FOR_OFFICE_ID=?  \n" + 
             "and i.CHEQUE_DD_NO =?  \n" + 
             "union  \n" + 
             "select i.voucher_no as vno from FAS_FUND_TRF_FROM_HO_MASTER i,FAS_FUND_TRF_FROM_HO_TRN j  \n" + 
             "where i.VOUCHER_NO=j.VOUCHER_NO and (j.CHEQ_CANCEL_STATUS is null or j.CHEQ_CANCEL_STATUS!='Y')  \n" + 
             "and i.ACCOUNTING_UNIT_ID=j.ACCOUNTING_UNIT_ID and   \n" + 
             "i.ACCOUNTING_FOR_OFFICE_ID=j.ACCOUNTING_FOR_OFFICE_ID   \n" + 
             "and (j.CHEQUE_OR_DD='C' or j.CHEQUE_OR_DD='D')  and  \n" + 
             "i.ACCOUNTING_UNIT_ID=?  and i.ACCOUNTING_FOR_OFFICE_ID=? and j.CHEQUE_DD_NO =? and (j.CHEQUE_OR_DD='C' or j.CHEQUE_OR_DD='D')  \n" + 
             "and (j.CHEQ_CANCEL_STATUS is null or j.CHEQ_CANCEL_STATUS!='Y') \n" +
             "union  \n" + 
             "select i.VOUCHER_NO as vno from FAS_PAYMENT_MASTER i,FAS_PAYMENT_TRANSACTION j  \n" + 
             "where  i.VOUCHER_NO=j.VOUCHER_NO and (j.CHEQ_CANCEL_STATUS is null or j.CHEQ_CANCEL_STATUS!='Y')  \n" + 
             "and i.ACCOUNTING_UNIT_ID=j.ACCOUNTING_UNIT_ID and   \n" + 
             "i.ACCOUNTING_FOR_OFFICE_ID=j.ACCOUNTING_FOR_OFFICE_ID   \n" + 
             "and i.CASHBOOK_YEAR=j.CASHBOOK_YEAR and i.CASHBOOK_MONTH = j.CASHBOOK_MONTH " + 
             "and (j.CHEQUE_OR_DD='C' or j.CHEQUE_OR_DD='D') and i.PAYMENT_STATUS ='L' and " + 
             "i.ACCOUNTING_UNIT_ID=?  and i.ACCOUNTING_FOR_OFFICE_ID=?  and j.CHEQUE_DD_NO =?";
        
                 ps=con.prepareStatement(cheqsql);
                 ps.setInt(1, cmbAcc_UnitCode);
                 ps.setInt(2, cmbOffice_code);
                 ps.setInt(3,txtCheque_No);
                 ps.setInt(4, cmbAcc_UnitCode);
                 ps.setInt(5, cmbOffice_code);
                 ps.setInt(6,txtCheque_No);
                 ps.setInt(7, cmbAcc_UnitCode);
                 ps.setInt(8, cmbOffice_code);
                 ps.setInt(9,txtCheque_No);
                 rs = ps.executeQuery();
        
                 int count = 0;
                 while (rs.next()) {
                    // xml=xml+"<leng>";
                     xml = xml + "<Rec_No>" + rs.getInt("vno") + "</Rec_No>" ;
                  
                   //  xml=xml+"</leng>";
                 
                     count++;
                 }
                 if (count == 0)
                     xml = xml + "<flag>failure</flag>";
                 else
                     xml = xml + "<flag>success</flag>";
                 System.out.println("count  " + count);
                 ps.close();
                 rs.close();
             
             }
                 catch (Exception e) {
                                 System.out.println("catch..HERE.in load VOUCHER." + e);
                                 xml = xml + "<flag>failure</flag>";
                             }
                             xml = xml + "</response>";
                             System.out.println(xml);
                             out.println(xml);
                             
                             
           
             }
       
        else if (strCommand.equalsIgnoreCase("Other_Details")) {
             String CONTENT_TYPE = "text/xml; charset=windows-1252";
             response.setContentType(CONTENT_TYPE);
         //    Calendar c;
             xml = "<response><command>Other_Details</command>";
             int txtCheque_No = 0,txtVoucher_No=0;
             System.out.println("inside otherdetails");  

             try {
                 txtCheque_No = 
                         Integer.parseInt(request.getParameter("txtCheque_No"));
             } catch (NumberFormatException e) {
                 System.out.println("exception" + e);
             }
             System.out.println("txtCheque_No " + txtCheque_No);

                 try {
                     txtVoucher_No = 
                             Integer.parseInt(request.getParameter("txtReceipt_No"));
                 } catch (NumberFormatException e) {
                     System.out.println("exception" + e);
                 }
                 System.out.println("txtVoucher_No " + txtVoucher_No);

             try {
                 String cheqsql= "select to_char(i.DATE_OF_TRANSFER,'DD/MM/YYYY') as transdate,i.CHEQUE_OR_DD as cheqtype,'Fund Transfer' as doctype," + 
                            "i.CHEQUE_DD_NO as cheqno,to_char(i.CHEQUE_DD_DATE,'DD/MM/YYYY') as cheqdate,i.TOTAL_AMOUNT as tot " + 
                            "from FAS_FUND_TRF_FROM_OFFICE i " + 
                            "where (i.CHEQUE_OR_DD='C' or i.CHEQUE_OR_DD='D') " + 
                            "and (i.CHEQ_CANCEL_STATUS is null or i.CHEQ_CANCEL_STATUS!='Y') and  i.TRANSFER_STATUS ='L' " + 
                            "and i.ACCOUNTING_UNIT_ID=? " + 
                            "and i.ACCOUNTING_FOR_OFFICE_ID=? " + 
                            "and i.CHEQUE_DD_NO =? and i.VOUCHER_NO=? " + 
                            "union " + 
                            "select to_char(i.DATE_OF_TRANSFER,'DD/MM/YYYY') as transdate,j.CHEQUE_OR_DD as cheqtype,'Fund Transfer HO' as doctype, " + 
                            "j.CHEQUE_DD_NO as cheqno,to_char(j.CHEQUE_DD_DATE,'DD/MM/YYYY') as cheqdate,i.TOTAL_AMOUNT  as tot from FAS_FUND_TRF_FROM_HO_MASTER i,FAS_FUND_TRF_FROM_HO_TRN j " + 
                            "where  i.VOUCHER_NO=j.VOUCHER_NO and (j.CHEQ_CANCEL_STATUS is null or j.CHEQ_CANCEL_STATUS!='Y') " + 
                            "and i.ACCOUNTING_UNIT_ID=j.ACCOUNTING_UNIT_ID and  " + 
                            "i.ACCOUNTING_FOR_OFFICE_ID=j.ACCOUNTING_FOR_OFFICE_ID  " + 
                            "and i.CASHBOOK_YEAR=j.CASHBOOK_YEAR and i.CASHBOOK_MONTH = j.CASHBOOK_MONTH  " + 
                            "and (j.CHEQUE_OR_DD='C' or j.CHEQUE_OR_DD='D') and  " + 
                            "i.ACCOUNTING_UNIT_ID=? and i.ACCOUNTING_FOR_OFFICE_ID=? and j.CHEQUE_DD_NO =? and i.VOUCHER_NO=?  " +
                            "union " +
                            "select to_char(i.PAYMENT_DATE,'DD/MM/YYYY') as transdate,j.CHEQUE_OR_DD as cheqtype,'Payment' as doctype, " + 
                            "j.CHEQUE_DD_NO as cheqno,to_char(j.CHEQUE_DD_DATE,'DD/MM/YYYY') as cheqdate,i.TOTAL_AMOUNT  as tot from FAS_PAYMENT_MASTER i,FAS_PAYMENT_TRANSACTION j " + 
                            "where  i.VOUCHER_NO=j.VOUCHER_NO and (j.CHEQ_CANCEL_STATUS is null or j.CHEQ_CANCEL_STATUS!='Y') " + 
                            "and i.ACCOUNTING_UNIT_ID=j.ACCOUNTING_UNIT_ID and  " + 
                            "i.ACCOUNTING_FOR_OFFICE_ID=j.ACCOUNTING_FOR_OFFICE_ID  " + 
                            "and i.CASHBOOK_YEAR=j.CASHBOOK_YEAR and i.CASHBOOK_MONTH = j.CASHBOOK_MONTH  " + 
                            "and (j.CHEQUE_OR_DD='C' or j.CHEQUE_OR_DD='D') and i.PAYMENT_STATUS ='L' and " + 
                            "i.ACCOUNTING_UNIT_ID=?  and i.ACCOUNTING_FOR_OFFICE_ID=? and j.CHEQUE_DD_NO =? and i.VOUCHER_NO=?  ";
                           
                                ps=con.prepareStatement(cheqsql);
                                ps.setInt(1, cmbAcc_UnitCode);
                                ps.setInt(2, cmbOffice_code);
                                ps.setInt(3,txtCheque_No);
                                ps.setInt(4,txtVoucher_No);
                                ps.setInt(5, cmbAcc_UnitCode);
                                ps.setInt(6, cmbOffice_code);
                                ps.setInt(7,txtCheque_No);
                                ps.setInt(8,txtVoucher_No);
                                ps.setInt(9, cmbAcc_UnitCode);
                                ps.setInt(10, cmbOffice_code);
                                ps.setInt(11,txtCheque_No);
                                ps.setInt(12,txtVoucher_No);
                                rs = ps.executeQuery();

                 int count = 0;
                 while (rs.next()) {

                     xml = 
                     xml +"<cheq_dd>" + rs.getString("cheqtype") + "</cheq_dd>" + 
                      "<che_DD_date>" + rs.getString("cheqdate") + "</che_DD_date>" + 
                    //  "<Total_amt>" + rs.getInt("tot") + "</Total_amt>" +
                      "<transdate>"+rs.getString("transdate")+"</transdate>" +
                      "<doctype>"+rs.getString("doctype")+"</doctype>" 
                     
                      
                 ;
                     count++;
                 }
                 if (count == 0)
                     xml = xml + "<flag>failure</flag>";
                 else
                     xml = xml + "<flag>success</flag>";
                 System.out.println("count  " + count);
                 System.out.println("cheqtype"+ rs.getString("cheqtype"));
                 System.out.println("transdate"+rs.getString("transdate"));
                 System.out.println("doctype"+rs.getString("doctype"));
                 ps.close();
                 rs.close();
             
             }
                 catch (Exception e) {
                                 System.out.println("catch..HERE.in Other_Details." + e);
                                 xml = xml + "<flag>failure</flag>";
                             }
                
                 try {
                     String cheqsql= "select i.TOTAL_AMOUNT as tot from FAS_FUND_TRF_FROM_OFFICE i \n" + 
                     "where (i.CHEQUE_OR_DD='C' or i.CHEQUE_OR_DD='D') \n" + 
                     "and (i.CHEQ_CANCEL_STATUS is null or i.CHEQ_CANCEL_STATUS!='Y') and  i.TRANSFER_STATUS ='L'\n" + 
                     "and i.ACCOUNTING_UNIT_ID=? and i.ACCOUNTING_FOR_OFFICE_ID=? \n" + 
                     "and i.CHEQUE_DD_NO =? and i.voucher_no=? \n" + 
                     "union\n" + 
                     "select sum(j.AMOUNT) as tot from FAS_FUND_TRF_FROM_HO_MASTER i,FAS_FUND_TRF_FROM_HO_TRN j \n" + 
                     "where  i.VOUCHER_NO=j.VOUCHER_NO and (j.CHEQ_CANCEL_STATUS is null or j.CHEQ_CANCEL_STATUS!='Y') \n" + 
                     "and i.ACCOUNTING_UNIT_ID=j.ACCOUNTING_UNIT_ID and  \n" + 
                     "i.ACCOUNTING_FOR_OFFICE_ID=j.ACCOUNTING_FOR_OFFICE_ID  \n" + 
                     "and i.CASHBOOK_YEAR=j.CASHBOOK_YEAR and i.CASHBOOK_MONTH = j.CASHBOOK_MONTH  \n" + 
                     "and (j.CHEQUE_OR_DD='C' or j.CHEQUE_OR_DD='D') and \n" + 
                     "i.ACCOUNTING_UNIT_ID=?  and i.ACCOUNTING_FOR_OFFICE_ID=? and j.CHEQUE_DD_NO =? and j.VOUCHER_NO = ? " +
                     "union " +
                     "select sum(j.AMOUNT) as tot from FAS_PAYMENT_MASTER i,FAS_PAYMENT_TRANSACTION j \n" + 
                     "where  i.VOUCHER_NO=j.VOUCHER_NO and (j.CHEQ_CANCEL_STATUS is null or j.CHEQ_CANCEL_STATUS!='Y') \n" + 
                     "and i.ACCOUNTING_UNIT_ID=j.ACCOUNTING_UNIT_ID and  \n" + 
                     "i.ACCOUNTING_FOR_OFFICE_ID=j.ACCOUNTING_FOR_OFFICE_ID  \n" + 
                     "and i.CASHBOOK_YEAR=j.CASHBOOK_YEAR and i.CASHBOOK_MONTH = j.CASHBOOK_MONTH  \n" + 
                     "and (j.CHEQUE_OR_DD='C' or j.CHEQUE_OR_DD='D') and i.PAYMENT_STATUS ='L' and \n" + 
                     "i.ACCOUNTING_UNIT_ID=?  and i.ACCOUNTING_FOR_OFFICE_ID=? and j.CHEQUE_DD_NO =? and j.VOUCHER_NO = ? ";
                               
                                    ps=con.prepareStatement(cheqsql);
                                    ps.setInt(1, cmbAcc_UnitCode);
                                    ps.setInt(2, cmbOffice_code);
                                    ps.setInt(3,txtCheque_No);
                                    ps.setInt(4,txtVoucher_No);
                                    ps.setInt(5, cmbAcc_UnitCode);
                                    ps.setInt(6, cmbOffice_code);
                                    ps.setInt(7,txtCheque_No);
                                    ps.setInt(8,txtVoucher_No);
                                    ps.setInt(9, cmbAcc_UnitCode);
                                    ps.setInt(10, cmbOffice_code);
                                    ps.setInt(11,txtCheque_No);
                                    ps.setInt(12,txtVoucher_No);
                                    rs = ps.executeQuery();

                     int count = 0;
                     while (rs.next()) {

                         xml = xml+"<Total_amt>" + rs.getInt("tot") + "</Total_amt>" 
                        
                     ;
                         count++;
                     }
                     if (count == 0)
                         xml = xml + "<flag>failure</flag>";
                     else
                         xml = xml + "<flag>successamt</flag>";
                     System.out.println("count  " + count);
                     ps.close();
                     rs.close();
                 
                 }
                     catch (Exception e) {
                                     System.out.println("catch..HERE.in TOTAL_AMOUNT." + e);
                                     xml = xml + "<flag>failure</flag>";
                                 }             
                             xml = xml + "</response>";
                             System.out.println(xml);
                             out.println(xml);
                 
             }
    }

   
       public void doPost(HttpServletRequest request, 
                       HttpServletResponse response) throws ServletException, 
                                                            IOException {
        String strCommand = "";
        String xml = "";
        Connection con = null;
        ResultSet rs = null, rs2 = null, rs3 = null,rs4 = null;
        //  CallableStatement cs=null,cs1=null;
        PreparedStatement ps = null, ps1 = null, ps2 = null, ps3 = null,ps4=null;
        int max_branch_id = 0,rec_count=0;

        HttpSession session = request.getSession(false);
        try {

            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                return;
            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }


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
            //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

        }

        try {

            strCommand = request.getParameter("Command");
            System.out.println("assign..here command..." + strCommand);
        }

        catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }

        if (strCommand.equalsIgnoreCase("Add")) {
            xml = "<response><command>Add</command>";
            Calendar c;
            int cmbAcc_UnitCode = 0, cmbOffice_code = 0, txtReceipt_No = 0,CashBookYear_Cur=0,CashBookMonth_Cur=0;
            int txtCash_Month_hid=0,txtCash_year=0;
            Date txtDoc_date = null;
            Date cbrefdate_new=null;
            Date trans_cbrefdate=null;
            Date cbref_transdate=null;
            
            Date txtCrea_date=null;
            Date che_DD_date = null;
            double txtAmount = 0;
            double payAmount=0;
            int subledger = 0;
            int cbrefno=0;
            String cbrefdate="";
            int subledger_trans=0;
            int accnthead=0;
            int subledgertype=0;
            int accnthead_trans=0;
            int subledgertype_trans=0;
            String crdrindicator="";
            String crdrindicator_trans="";
            String update_user = (String)session.getAttribute("UserId");
            long l = System.currentTimeMillis();
            Timestamp ts = new Timestamp(l);
            String CashBook_Year = request.getParameter("txtCB_Year");
            String CashBook_Month = request.getParameter("txtCB_Month");
            String txtCheque_DD = request.getParameter("txtCheque_DD");
            String txtCheq_DD_New=request.getParameter("txtCheq_DD_Issued");
            String txtCheque_DD2 = "",txtCheque_DD3="";
            String CashBook_Year_Cur = request.getParameter("txtCash_year");
            String CashBook_Month_Cur = request.getParameter("txtCash_Month");
            String chqdate=request.getParameter("txtCheque_DD_date2");
            String paychequedate="";
            String remarks_date="";
            String oldcheqdate=request.getParameter("txtCheque_DD_date");
            int CashBookYear = 0;
            int CashBookMonth = 0;

            String doctype = "";
            String txtRemarks = "";
            String txtCheque_DD_NO = "";
            String txtCheque_DD_NO2 = null;
            Date txtCheque_DD_date2 = null;
            double txtAmount2 = 0;
            int txtVoucher_No = 0;
            int transn_records=0;
            try{con.setAutoCommit(false);  }catch(SQLException sqle){}
            String Flag ="false";
            try {
                txtVoucher_No = 
                        Integer.parseInt(request.getParameter("txtReceipt_No"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtVoucher_No " + txtVoucher_No);
            int txtCheque_No = 0;

            try {
                txtCheque_DD_NO = 
                        request.getParameter("txtCheque_No");
            } catch (Exception e) {
                System.out.println("excepion in cheque number :" + e);
            }
            try {
                doctype = request.getParameter("doc_type");
            } catch (Exception e) {
                System.out.println("exception in doctype" + e);
            }
            System.out.println("doctype " + doctype);

            try {
                remarks_date = request.getParameter("txtDoc_date");
            } catch (Exception e) {
                System.out.println("exception in remarks_date" + e);
            }
            System.out.println("remarks_date " + remarks_date);

            try {
                cmbAcc_UnitCode = 
                        Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);

            try {
                cmbOffice_code = 
                        Integer.parseInt(request.getParameter("cmbOffice_code"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("cmbOffice_code " + cmbOffice_code);
        /*    try {
                CashBookYear = Integer.parseInt(CashBook_Year);
                System.out.println("CashBook_Year After is:" + CashBookYear);
            } catch (Exception e) {
                System.out.println("Exception in Year:" + e);
                CashBookYear = 0;
            }
            
            try {
                CashBookMonth = Integer.parseInt(CashBook_Month);
                System.out.println("CashBook month is:" + CashBookMonth);
            } catch (Exception e) {
                System.out.println("Exception in CashBookMonth:" + e);
                CashBookMonth = 0;
            }*/
           /* try {
                CashBookYear_Cur = Integer.parseInt(CashBook_Year_Cur);
                System.out.println("Current CashBook_Year :" + CashBookYear_Cur);
            } catch (Exception e) {
                System.out.println("Exception in Current CashBook_Year:" + e);
                CashBookYear_Cur = 0;
            }

            try {
                CashBookMonth_Cur = Integer.parseInt(CashBook_Month_Cur);
                System.out.println("Current CashBook Month :" + CashBookMonth_Cur);
            } catch (Exception e) {
                System.out.println("Exception in Current CashBook Month:" + e);
                CashBookMonth_Cur = 0;
            }*/
            
           String[] sd = request.getParameter("txtDoc_date").split("/");
            c = 
  new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1, 
                        Integer.parseInt(sd[0]));
            java.util.Date d = c.getTime();
            txtDoc_date = new Date(d.getTime());
            System.out.println("txtDoc_date " + txtDoc_date);

            try {
                txtReceipt_No = 
                        Integer.parseInt(request.getParameter("txtReceipt_No"));
            } catch (NumberFormatException e) {
                System.out.println("exception in voucher no" + e);
            }
            System.out.println("txtReceipt_No " + txtReceipt_No);
            String[] sd1 =request.getParameter("txtCheque_DD_date").split("/");
            c =   new GregorianCalendar(Integer.parseInt(sd1[2]), Integer.parseInt(sd1[1]) - 1, 
                        Integer.parseInt(sd1[0]));
            java.util.Date d1 = c.getTime();
            che_DD_date = new Date(d1.getTime());
            System.out.println("che_DD_date " + che_DD_date);

            try {
                txtCheque_DD = request.getParameter("txtCheque_DD");
            } catch (Exception e) {
                System.out.println("excepion in cheque/dd type :" + e);
            }

            try {
                txtRemarks = request.getParameter("txtRemarks");
            } catch (Exception e) {
                System.out.println("excepion in txtremarks :" + e);
            }
            try {
                txtAmount = 
                        Double.parseDouble(request.getParameter("txtAmount"));
            } catch (Exception e) {
                System.out.println("exception" + e);
            }

            System.out.println("txtAmount " + txtAmount);

            try {
                txtCheque_DD2 = request.getParameter("txtCheque_DD2");
            } catch (Exception e) {
                System.out.println("excepion in issue cheque yes/no :" + e);
            }
            
            System.out.println("txtCheque_DD2"+txtCheque_DD2);
            try {
                txtCheque_DD3 = request.getParameter("txtCheque_DD3");
            } catch (Exception e) {
                System.out.println("excepion in create voucher :" + e);
            }
            
            System.out.println("txtCheque_DD2"+txtCheque_DD2);
            
         //checking Create payment voucher yes/No   
            if((txtCheque_DD3.equalsIgnoreCase("N"))){
                try {
                    txtAmount2 = 
                            Double.parseDouble(request.getParameter("txtAmount"));
                } catch (Exception e) {
                    System.out.println("exception" + e);
                }
            }
           else {
            try {
                txtAmount2 = 
                        Double.parseDouble(request.getParameter("txtAmount2"));
            } catch (Exception e) {
                System.out.println("exception" + e);
            }
           }
            System.out.println("txtAmount2 " + txtAmount2);
            
        //-------------    
            String[] sd4=request.getParameter("txtCrea_date").split("/");
            c=new GregorianCalendar(Integer.parseInt(sd4[2]),Integer.parseInt(sd4[1])-1,Integer.parseInt(sd4[0]));
            java.util.Date d4=c.getTime();
            txtCrea_date=new Date(d4.getTime());
            System.out.println("txtCrea_date "+txtCrea_date);
                 // ------------ added on 03/08/2011
                  try {
                                       CashBookYear_Cur = Integer.parseInt(sd4[2]);
                               } catch (Exception e) {
                                       System.out.println("exception" + e);
                               }
                               System.out.println("txtCash_year from dishonur date " + CashBookYear_Cur);

                               try {
                                       CashBookMonth_Cur = Integer.parseInt(sd4[1]);
                               } catch (Exception e) {
                                       System.out.println("exception" + e);
                               }  
            
              /** Get Cheque Cancelation Date */          
                String Cheq_Cancel_Date=request.getParameter("txtCrea_date");
        //======================        
         int count=0,vcode=0;
                      ArrayList<String> voucher = new ArrayList(); 
                       ArrayList<String> v_code = new ArrayList(); 
                       ArrayList<String> doc_date=new ArrayList();
                       ArrayList<String> cheq_date = new ArrayList();                     
                       ArrayList<String> cheq_type = new ArrayList();                     
                       ArrayList<String> cheq_no = new ArrayList(); 
                       ArrayList<String> CR_DR = new ArrayList(); 
                       ArrayList<String> Amtvalue = new ArrayList();
         //==========Same cheque for mutiple Vouchers===================//
         
          System.out.println("Before Selection ");         
         
                 try {
                            String cheqsql= "select i.voucher_no as vno,i.CASHBOOK_YEAR,i.CASHBOOK_MONTH,to_char(i.DATE_OF_TRANSFER,'DD/MM/YYYY') as transdate,i.CHEQUE_OR_DD as cheqtype,'Fund Transfer' as doctype," + 
                                       "i.CHEQUE_DD_NO as cheqno,to_char(i.CHEQUE_DD_DATE,'DD/MM/YYYY') as cheqdate,i.TOTAL_AMOUNT as tot " + 
                                       "from FAS_FUND_TRF_FROM_OFFICE i " + 
                                       "where (i.CHEQUE_OR_DD='C' or i.CHEQUE_OR_DD='D') " + 
                                       "and (i.CHEQ_CANCEL_STATUS is null or i.CHEQ_CANCEL_STATUS!='Y') and  i.TRANSFER_STATUS ='L' " + 
                                       "and i.ACCOUNTING_UNIT_ID=? " + 
                                       "and i.ACCOUNTING_FOR_OFFICE_ID=? " + 
                                       "and i.CHEQUE_DD_NO =?  " + 
                                       "union " +
                                       "select i.voucher_no as vno,i.CASHBOOK_YEAR,i.CASHBOOK_MONTH,to_char(i.DATE_OF_TRANSFER,'DD/MM/YYYY') as transdate,j.CHEQUE_OR_DD as cheqtype,'Fund Transfer HO' as doctype, \n" + 
                                       "j.CHEQUE_DD_NO as cheqno,to_char(j.CHEQUE_DD_DATE,'DD/MM/YYYY') as cheqdate,i.TOTAL_AMOUNT as tot  \n" + 
                                       "from FAS_FUND_TRF_FROM_HO_MASTER i,FAS_FUND_TRF_FROM_HO_TRN j  \n" + 
                                       "where i.VOUCHER_NO=j.VOUCHER_NO and (j.CHEQ_CANCEL_STATUS is null or j.CHEQ_CANCEL_STATUS!='Y')  \n" + 
                                       "and i.ACCOUNTING_UNIT_ID=j.ACCOUNTING_UNIT_ID and   \n" + 
                                       "i.ACCOUNTING_FOR_OFFICE_ID=j.ACCOUNTING_FOR_OFFICE_ID   \n" + 
                                       "and i.CASHBOOK_YEAR=j.CASHBOOK_YEAR and i.CASHBOOK_MONTH = j.CASHBOOK_MONTH   \n" + 
                                       "and (j.CHEQUE_OR_DD='C' or j.CHEQUE_OR_DD='D')  and  \n" + 
                                       "i.ACCOUNTING_UNIT_ID=?  and i.ACCOUNTING_FOR_OFFICE_ID=?  \n" + 
                                       "and j.CHEQUE_DD_NO =? and (j.CHEQUE_OR_DD='C' or j.CHEQUE_OR_DD='D')  \n" + 
                                       "and (j.CHEQ_CANCEL_STATUS is null or j.CHEQ_CANCEL_STATUS!='Y') \n" +
                                       "union " + 
                                       "select i.voucher_no as vno,i.CASHBOOK_YEAR,i.CASHBOOK_MONTH,to_char(i.PAYMENT_DATE,'DD/MM/YYYY') as transdate,j.CHEQUE_OR_DD as cheqtype,'Payment' as doctype, " + 
                                       "j.CHEQUE_DD_NO as cheqno,to_char(j.CHEQUE_DD_DATE,'DD/MM/YYYY') as cheqdate,j.AMOUNT as tot from FAS_PAYMENT_MASTER i,FAS_PAYMENT_TRANSACTION j " + 
                                       "where  i.VOUCHER_NO=j.VOUCHER_NO and (j.CHEQ_CANCEL_STATUS is null or j.CHEQ_CANCEL_STATUS!='Y') " + 
                                       "and i.ACCOUNTING_UNIT_ID=j.ACCOUNTING_UNIT_ID and  " + 
                                       "i.ACCOUNTING_FOR_OFFICE_ID=j.ACCOUNTING_FOR_OFFICE_ID  " + 
                                       "and i.CASHBOOK_YEAR=j.CASHBOOK_YEAR and i.CASHBOOK_MONTH = j.CASHBOOK_MONTH  " + 
                                       "and (j.CHEQUE_OR_DD='C' or j.CHEQUE_OR_DD='D') and i.PAYMENT_STATUS ='L' and " + 
                                       "i.ACCOUNTING_UNIT_ID=?  and i.ACCOUNTING_FOR_OFFICE_ID=?  and j.CHEQUE_DD_NO =?  ";
                                      
                                           ps=con.prepareStatement(cheqsql);
                                           ps.setInt(1, cmbAcc_UnitCode);
                                           ps.setInt(2, cmbOffice_code);
                                           ps.setString(3,txtCheque_DD_NO);
                                           ps.setInt(4, cmbAcc_UnitCode);
                                           ps.setInt(5, cmbOffice_code);
                                           ps.setString(6,txtCheque_DD_NO);
                                           ps.setInt(7, cmbAcc_UnitCode);
                                           ps.setInt(8, cmbOffice_code);
                                           ps.setString(9,txtCheque_DD_NO);
                                          
                                           rs = ps.executeQuery();

                         //    int count = 0;
                               // int k=0;
                                                                       while (rs.next()) {
                                                                             voucher.add(rs.getString("vno"));
                                                                           CashBookYear=rs.getInt("CASHBOOK_YEAR");
                                                                           CashBookMonth=rs.getInt("CASHBOOK_MONTH");
                                                                             System.out.println("voucher"+voucher);
                                                                    
                                                                          count++; 
                                                                           if (count == 0)
                                                                               xml = xml + "<flag>failure</flag>";
                                                                           else
                                                                               xml = xml + "<flag>success</flag>";
                                                                           System.out.println("count  " + count);
                                                                           System.out.println("cheqtype"+ rs.getString("cheqtype"));
                                                                           System.out.println("transdate"+rs.getString("transdate"));
                                                                           System.out.println("doctype"+rs.getString("doctype"));
                                                                       }

                                                                    //   k--;
                            ps.close();
                            rs.close();
                         
                         }
                            catch (Exception e) {
                                            System.out.println("catch..HERE.in Other_Details." + e);
                                            xml = xml + "<flag>failure</flag>";
                                        }
         for(int j=0;j<voucher.size();j++){ 
         try{vcode=Integer.parseInt(voucher.get(j));System.out.println("vcode==========="+vcode);}catch(Exception e){System.out.println("exception in voucher "+e);} 
         }
         
         //other details====================================//
         try {
         
         System.out.println("otherdetails vcode"+vcode);
         System.out.println("otherdetails voucher"+voucher);
         for(int k=0;k<voucher.size();k++){
             String cheqsql= "select to_char(i.DATE_OF_TRANSFER,'DD/MM/YYYY') as transdate,i.CHEQUE_OR_DD as cheqtype,'Fund Transfer' as doctype," + 
                        "i.CHEQUE_DD_NO as cheqno,to_char(i.CHEQUE_DD_DATE,'DD/MM/YYYY') as cheqdate,i.TOTAL_AMOUNT as tot " + 
                        "from FAS_FUND_TRF_FROM_OFFICE i " + 
                        "where (i.CHEQUE_OR_DD='C' or i.CHEQUE_OR_DD='D') " + 
                        "and (i.CHEQ_CANCEL_STATUS is null or i.CHEQ_CANCEL_STATUS!='Y') and  i.TRANSFER_STATUS ='L' " + 
                        "and i.ACCOUNTING_UNIT_ID=? " + 
                        "and i.ACCOUNTING_FOR_OFFICE_ID=? " + 
                        "and i.CASHBOOK_YEAR=? and i.CASHBOOK_MONTH=? and i.CHEQUE_DD_NO =? and i.VOUCHER_NO=? " + 
                        "union " + 
                        "select to_char(i.DATE_OF_TRANSFER,'DD/MM/YYYY') as transdate,j.CHEQUE_OR_DD as cheqtype,'Fund Transfer HO' as doctype, " + 
                        "j.CHEQUE_DD_NO as cheqno,to_char(j.CHEQUE_DD_DATE,'DD/MM/YYYY') as cheqdate,i.TOTAL_AMOUNT  as tot from FAS_FUND_TRF_FROM_HO_MASTER i,FAS_FUND_TRF_FROM_HO_TRN j " + 
                        "where  i.VOUCHER_NO=j.VOUCHER_NO and (j.CHEQ_CANCEL_STATUS is null or j.CHEQ_CANCEL_STATUS!='Y') " + 
                        "and i.ACCOUNTING_UNIT_ID=j.ACCOUNTING_UNIT_ID and  " + 
                        "i.ACCOUNTING_FOR_OFFICE_ID=j.ACCOUNTING_FOR_OFFICE_ID  " + 
                        "and i.CASHBOOK_YEAR=j.CASHBOOK_YEAR and i.CASHBOOK_MONTH = j.CASHBOOK_MONTH  " + 
                        "and (j.CHEQUE_OR_DD='C' or j.CHEQUE_OR_DD='D') and  " + 
                        "i.ACCOUNTING_UNIT_ID=?  and i.ACCOUNTING_FOR_OFFICE_ID=? and i.CASHBOOK_YEAR=? " + 
                        "and i.CASHBOOK_MONTH=? and j.CHEQUE_DD_NO =? and i.VOUCHER_NO=?  " +
                        "union " +
                        "select to_char(i.PAYMENT_DATE,'DD/MM/YYYY') as transdate,j.CHEQUE_OR_DD as cheqtype,'Payment' as doctype, " + 
                        "j.CHEQUE_DD_NO as cheqno,to_char(j.CHEQUE_DD_DATE,'DD/MM/YYYY') as cheqdate,i.TOTAL_AMOUNT  as tot from FAS_PAYMENT_MASTER i,FAS_PAYMENT_TRANSACTION j " + 
                        "where  i.VOUCHER_NO=j.VOUCHER_NO and (j.CHEQ_CANCEL_STATUS is null or j.CHEQ_CANCEL_STATUS!='Y') " + 
                        "and i.ACCOUNTING_UNIT_ID=j.ACCOUNTING_UNIT_ID and  " + 
                        "i.ACCOUNTING_FOR_OFFICE_ID=j.ACCOUNTING_FOR_OFFICE_ID  " + 
                        "and i.CASHBOOK_YEAR=j.CASHBOOK_YEAR and i.CASHBOOK_MONTH = j.CASHBOOK_MONTH  " + 
                        "and (j.CHEQUE_OR_DD='C' or j.CHEQUE_OR_DD='D') and i.PAYMENT_STATUS ='L' and " + 
                        "i.ACCOUNTING_UNIT_ID=?  and i.ACCOUNTING_FOR_OFFICE_ID=? and i.CASHBOOK_YEAR=? " + 
                        "and i.CASHBOOK_MONTH=? and j.CHEQUE_DD_NO =? and i.VOUCHER_NO=?  ";
                                                 
               
                    ps=con.prepareStatement(cheqsql);
         try{vcode=Integer.parseInt(voucher.get(k));System.out.println("vcode----------"+vcode);}catch(Exception e){System.out.println("exception in voucher "+e);}
                    ps.setInt(1, cmbAcc_UnitCode);
                    ps.setInt(2, cmbOffice_code);
                    ps.setInt(3, CashBookYear);
                    ps.setInt(4, CashBookMonth);
                    ps.setString(5,txtCheque_DD_NO);
                    ps.setInt(6,vcode);
                    ps.setInt(7, cmbAcc_UnitCode);
                    ps.setInt(8, cmbOffice_code);
                    ps.setInt(9, CashBookYear);
                    ps.setInt(10, CashBookMonth);
                    ps.setString(11,txtCheque_DD_NO);
                    ps.setInt(12,vcode);
                    ps.setInt(13, cmbAcc_UnitCode);
                    ps.setInt(14, cmbOffice_code);
                    ps.setInt(15, CashBookYear);
                    ps.setInt(16, CashBookMonth);
                    ps.setString(17,txtCheque_DD_NO);
                    ps.setInt(18,vcode);
                    rs = ps.executeQuery();
                   
         //  int j = 0;
         while (rs.next()) {
         System.out.println("insidewhile");
         doc_date.add(rs.getString("transdate"));
         cheq_date.add(rs.getString("cheqdate"));
         cheq_type.add(rs.getString("cheqtype"));
         Amtvalue.add(rs.getString("tot"));
         System.out.println("cheqtype"+ cheq_type);
         System.out.println("cheqdate"+cheq_date);
         System.out.println("Amtvalue"+Amtvalue);   
         System.out.println("doctdate"+doc_date);
         ;
         count++;
         }
         count--;
         if (count == 0)
         xml = xml + "<flag>failure</flag>";
         else
         xml = xml + "<flag>success</flag>";
         System.out.println("count  " + count);
         
         ps.close();
         rs.close();
         
         }
         }   catch (Exception e) {
                     System.out.println("catch..HERE.in Other_Details." + e);
                     xml = xml + "<flag>failure</flag>";
                 }
         //======================
          System.out.println("After Selection ");         
          System.out.println("txtCheque_DD2 ::"+txtCheque_DD2);   
 if (txtCheque_DD2.equalsIgnoreCase("Y")) {
             
   //int rec_count=0;
         String cbreftype=""; String paydate=null;
         String paid_to="";
        txtCheque_DD_NO2 = request.getParameter("txtCheque_DD_NO2");
        System.out.println("txtCheque_DD_NO2------------->"+txtCheque_DD_NO2);
        System.out.println("issue cheque yes/no"+txtCheque_DD_NO2);
        System.out.println("chqdate==============================>"+chqdate);
            
         try 
         {
            // con.setAutoCommit(false);
            txtAmount2 = Double.parseDouble(request.getParameter("txtAmount2"));
              
         } catch (Exception e) {System.out.println("exception" + e); }
         System.out.println("txtAmount2 " + txtAmount2);
                
               
                int v_code_ins=0,cheq_no_ins=0,Amt_value_ins=0;
                Date doc_date_ins=null,cheq_date_ins=null;
                String cheq_type_ins=null,CR_DR_ins=null,doc_date2=null,cheq_date2=null;
                System.out.println("V_Code Length ------------------->>>"+voucher.size());                     
                              for(int k=0; k< voucher.size(); k++){ 
                        

                                  try{v_code_ins=Integer.parseInt(voucher.get(k));}catch(Exception e){System.out.println("exception in voucher "+e);}
                                  try{doc_date2=doc_date.get(k);}catch(Exception e){System.out.println("exception in doc_date2 "+e);}
                                  try{cheq_date2=cheq_date.get(k);}catch(Exception e){System.out.println("exception in cheq_date2 "+e);}
                                  try{
                                      System.out.println("Before Date Convertion :"+doc_date2);
                                      String[] sd5=doc_date2.split("/");
                                      c=new GregorianCalendar(Integer.parseInt(sd5[2]),Integer.parseInt(sd5[1])-1,Integer.parseInt(sd5[0]));
                                      java.util.Date d5=c.getTime();
                                      doc_date_ins=new Date(d5.getTime());}catch(Exception e){System.out.println("exception in docdate "+e);}
                                      System.out.println("After Date Convertion :"+doc_date_ins);
                                  try{System.out.println("Before Date Convertion :"+cheq_date2);
                                      String[] sd5=cheq_date2.split("/");
                                      c=new GregorianCalendar(Integer.parseInt(sd5[2]),Integer.parseInt(sd5[1])-1,Integer.parseInt(sd5[0]));
                                      java.util.Date d5=c.getTime();
                                      cheq_date_ins=new Date(d5.getTime());}catch(Exception e){System.out.println("exception in cheqdate "+e);}   
                                  System.out.println("After Date Convertion :"+cheq_date_ins);
                                  try{cheq_type_ins=cheq_type.get(k);}catch(Exception e){System.out.println("exception in cheqtype "+e);}
                                  try{Amt_value_ins=Integer.parseInt(Amtvalue.get(k));}catch(Exception e){System.out.println("exception in amount "+e);}
                                 
                  
                              String sql_fund = 
                                  ("insert into FAS_CHEQUE_CANCEL(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,DOCUMENT_DATE,DOCUMENT_TYPE,DOCUMENT_NO,CHEQUE_OR_DD,CHEQUE_DD_NO,CHEQUE_DD_DATE,AMOUNT,REMARKS,ISSUE_CHEQUE,ISSUE_CHEQUE_NO,ISSUE_CHEQUE_DATE,ISSUE_CHEQUE_AMOUNT,UPDATED_BY_USER_ID,UPDATED_DATE,ISSUE_CHEQUE_OR_DD,CHEQ_CANCEL_DATE,CREA_PAY_VOU) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

                              try {
                                  
                                  System.out.println("here begin..add for y condn");
                                  ps = con.prepareStatement(sql_fund);
                                  
                                  ps.setInt(1, cmbAcc_UnitCode);
                                  ps.setInt(2, cmbOffice_code);
                                  ps.setInt(3, CashBookYear_Cur);
                                  ps.setInt(4, CashBookMonth_Cur);
                                  ps.setDate(5, doc_date_ins);
                                  ps.setString(6, doctype);
                                  ps.setInt(7, v_code_ins);
                                  ps.setString(8, txtCheque_DD);
                                  ps.setString(9, txtCheque_DD_NO);
                                  ps.setDate(10, cheq_date_ins);
                                  ps.setDouble(11, Amt_value_ins);
                                  ps.setString(12, txtRemarks);
                                  ps.setString(13, txtCheque_DD2);
                                  if(txtCheque_DD_NO2.equals(""))
                                      ps.setNull(14,Types.INTEGER);
                                  else
                                      ps.setInt(14, Integer.parseInt(txtCheque_DD_NO2));
                                  if(chqdate.equals(""))
                                      ps.setNull(15,Types.DATE);
                                  else
                                  {
                                      String[] sd2 =chqdate.split("/");
                                      c = new GregorianCalendar(Integer.parseInt(sd2[2]), Integer.parseInt(sd2[1]) - 1,Integer.parseInt(sd2[0]));
                                      java.util.Date d2 = c.getTime();
                                      txtCheque_DD_date2 = new Date(d2.getTime());
                                      System.out.println("issue_cheque_date " + txtCheque_DD_date2);
                                      ps.setDate(15, txtCheque_DD_date2);    
                                  }
                                  ps.setDouble(16, txtAmount2);
                                  ps.setString(17, update_user);
                                  ps.setTimestamp(18, ts);
                                  ps.setString(19,txtCheq_DD_New);
                                  ps.setDate(20,txtCrea_date);
                                  ps.setString(21,txtCheque_DD3);
                                 int z= ps.executeUpdate();
                                 System.out.println(z);
                                 if(z>0){
                                     Flag="true";
                                     System.out.println("FAS_CHEQUE_CANCEL insertion success");
                                 }
                                 else{
                                     Flag="false";
                                     System.out.println("FAS_CHEQUE_CANCEL insertion failed");
                                 }
                                  ps.close(); 
                              } catch (Exception e) {

                                  System.out.println("Exception occur due to " + e);
                                  Flag="false";
                                 // sendMessage(response, "Cheque Details already exist ", 
                                   //           "ok");
                                   }
                  
                    if (doctype.equalsIgnoreCase("Payment")) 
                    {
                                    if(Flag.equals("true"))           
                                     {     
                                    try {
                                                          System.out.println("v_code_ins============"+v_code_ins);              
                                                    
                                                           ps2 = con.prepareStatement("UPDATE FAS_PAYMENT_MASTER SET CHEQ_CANCEL_STATUS=?,UPDATED_DATE=?, UPDATED_BY_USER_ID=? WHERE ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID=? AND CASHBOOK_YEAR=? AND CASHBOOK_MONTH=? " +
                                                                                      " AND VOUCHER_NO=?  ");
                                                             
                                                           
                                                           ps2.setString(1,"Y");
                                                           ps2.setTimestamp(2, ts);
                                                           ps2.setString(3, update_user);
                                                           ps2.setInt(4, cmbAcc_UnitCode);
                                                           ps2.setInt(5, cmbOffice_code);
                                                           ps2.setInt(6, CashBookYear);
                                                           ps2.setInt(7, CashBookMonth);
                                                           ps2.setInt(8, v_code_ins);
                                                         
                                                        int z= ps2.executeUpdate();
                                                             if(z>0){
                                                                 Flag="true";
                                                                 System.out.println("FAS_PAYMENT_MASTER updation success");
                                                             }
                                                             else{
                                                                 Flag="false";
                                                                 System.out.println("FAS_PAYMENT_MASTER updation failed");
                                                             }
                                                           ps2.close();
                                                           // xml = xml + "<flag>success</flag>";
                                                            System.out.println("here update in payment entry ok");
                                                         } catch (Exception e) 
                                                         {
                                                             System.out.println("exception in update payment entry:" + e);
                                                             Flag="false";
                                                           //  sendMessage(response, "Cheque Details Updation Failed ","ok");
                                                         }
                                     }          
                                               
                                    if(Flag.equals("true"))
                       
                                    try {
                                             
                                    
                                           ps2 = con.prepareStatement("UPDATE FAS_PAYMENT_TRANSACTION SET CHEQ_CANCEL_STATUS=?,UPDATED_DATE=?, UPDATED_BY_USER_ID=? WHERE ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID=? AND CASHBOOK_YEAR=? AND CASHBOOK_MONTH=? " +
                                                                      " AND VOUCHER_NO=? AND CHEQUE_DD_NO=? ");
                                             
                                           
                                           ps2.setString(1,"Y");
                                           ps2.setTimestamp(2, ts);
                                           ps2.setString(3, update_user);
                                           ps2.setInt(4, cmbAcc_UnitCode);
                                           ps2.setInt(5, cmbOffice_code);
                                           ps2.setInt(6, CashBookYear);
                                           ps2.setInt(7, CashBookMonth);
                                           ps2.setInt(8, v_code_ins);
                                           ps2.setString(9, txtCheque_DD_NO );
                                          int z= ps2.executeUpdate();
                                                  if(z>0){
                                                      Flag="true";
                                                      System.out.println("FAS_PAYMENT_TRANSACTION updation success");
                                                  }
                                                  else{
                                                      Flag="false";
                                                      System.out.println("FAS_PAYMENT_TRANSACTION updation failed");
                                                  }
                                           ps2.close();
                                           // xml = xml + "<flag>success</flag>";
                                            System.out.println("here update in payment transaction entry ok");
                                         } catch (Exception e) {
                                             System.out.println("exception in update payment transaction entry:" + e);
                                                       Flag="false";
                                                     //  sendMessage(response, "Cheque Details Updation Failed","ok");
                                                   }
                                }
                                
                    else if (doctype.equalsIgnoreCase("Fund Transfer")){
                        if(Flag.equals("true")){
                        try {
                                 
                        
                               ps2 = con.prepareStatement("UPDATE FAS_FUND_TRF_FROM_OFFICE SET CHEQ_CANCEL_STATUS=?,UPDATED_DATE=?,UPDATED_BY_USER_ID=? WHERE ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID=? AND CASHBOOK_YEAR=? AND CASHBOOK_MONTH=? " +
                                                          " AND VOUCHER_NO=? ");
                               
                               ps2.setString(1,"Y");
                               ps2.setTimestamp(2, ts);
                               ps2.setString(3, update_user);  
                               ps2.setInt(4, cmbAcc_UnitCode);
                               ps2.setInt(5, cmbOffice_code);
                               ps2.setInt(6, CashBookYear);
                               ps2.setInt(7, CashBookMonth);
                               ps2.setInt(8, v_code_ins);
                                 int z= ps2.executeUpdate();
                                         if(z>0){
                                             Flag="true";
                                             System.out.println("FAS_FUND_TRF_FROM_OFFICE updation success");
                                         }
                                         else{
                                             Flag="false";
                                             System.out.println("FAS_FUND_TRF_FROM_OFFICE updation failed");
                                         }
                               ps2.close();
                               // xml = xml + "<flag>success</flag>";
                                System.out.println("here update in payment entry ok");
                             } catch (Exception e) {
                                 System.out.println("exception in journal entry:" + e);
                                           Flag="false";
                                       }
                        }
                    }
                       else if (doctype.equalsIgnoreCase("Fund Transfer HO")){
                           if(Flag.equals("true")){
                           try {
                                    
                           
                                  ps2 = con.prepareStatement("UPDATE FAS_FUND_TRF_FROM_HO_MASTER SET CHEQ_CANCEL_STATUS=?,UPDATED_DATE=?,UPDATED_BY_USER_ID=? WHERE ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID=? AND CASHBOOK_YEAR=? AND CASHBOOK_MONTH=? " +
                                                             " AND VOUCHER_NO=? ");
                                  
                                  ps2.setString(1,"Y");
                                  ps2.setTimestamp(2, ts);
                                  ps2.setString(3, update_user);  
                                  ps2.setInt(4, cmbAcc_UnitCode);
                                  ps2.setInt(5, cmbOffice_code);
                                  ps2.setInt(6, CashBookYear);
                                  ps2.setInt(7, CashBookMonth);
                                  ps2.setInt(8, v_code_ins);
                                    int z= ps2.executeUpdate();
                                            if(z>0){
                                                Flag="true";
                                                System.out.println("FAS_FUND_TRF_FROM_OFFICE updation success");
                                            }
                                            else{
                                                Flag="false";
                                                System.out.println("FAS_FUND_TRF_FROM_OFFICE updation failed");
                                            }
                                  ps2.close();
                                  // xml = xml + "<flag>success</flag>";
                                   System.out.println("here update in FAS_FUND_TRF_FROM_HO_MASTER entry ok");
                                } catch (Exception e) {
                                    System.out.println("exception in FAS_FUND_TRF_FROM_HO_MASTER entry:" + e);
                                              Flag="false";
                                          }
                          
                           try {
                                    
                           
                                  ps2 = con.prepareStatement("UPDATE FAS_FUND_TRF_FROM_HO_TRN SET CHEQ_CANCEL_STATUS=?,UPDATED_DATE=?, UPDATED_BY_USER_ID=? WHERE ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID=? AND CASHBOOK_YEAR=? AND CASHBOOK_MONTH=? " +
                                                             " AND VOUCHER_NO=? AND CHEQUE_DD_NO=? ");
                                    
                                  
                                  ps2.setString(1,"Y");
                                  ps2.setTimestamp(2, ts);
                                  ps2.setString(3, update_user);
                                  ps2.setInt(4, cmbAcc_UnitCode);
                                  ps2.setInt(5, cmbOffice_code);
                                  ps2.setInt(6, CashBookYear);
                                  ps2.setInt(7, CashBookMonth);
                                  ps2.setInt(8, v_code_ins);
                                  ps2.setString(9, txtCheque_DD_NO );
                                 int z= ps2.executeUpdate();
                                         if(z>0){
                                             Flag="true";
                                             System.out.println("FAS_FUND_TRF_FROM_HO_TRN updation success");
                                         }
                                         else{
                                             Flag="false";
                                             System.out.println("FAS_FUND_TRF_FROM_HO_TRN updation failed");
                                         }
                                  ps2.close();
                                  // xml = xml + "<flag>success</flag>";
                                   System.out.println("here update in FAS_FUND_TRF_FROM_HO_TRN entry ok");
                                } catch (Exception e) {
                                    System.out.println("exception in update FAS_FUND_TRF_FROM_HO_TRN entry:" + e);
                                              Flag="false";
                                            //  sendMessage(response, "Cheque Details Updation Failed","ok");
                                          }
                           }       
                       }
                    System.out.println("txtCheque_DD2 && txtCheque_DD3 ::"+txtCheque_DD2+"   "+txtCheque_DD3);
          if(Flag.equals("true")){
                  if(txtCheque_DD2.equalsIgnoreCase("Y")&&(txtCheque_DD3.equalsIgnoreCase("Y"))){
                        
                                    try {
                                        ps2 = 
                con.prepareStatement("SELECT nvl(max(VOUCHER_NO),0)+1 AS br_id FROM FAS_JOURNAL_MASTER where ACCOUNTING_UNIT_ID=? and " + 
                                     "ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=?");
                                        ps2.setInt(1, cmbAcc_UnitCode);
                                        ps2.setInt(2, cmbOffice_code);
                                        ps2.setInt(3, CashBookYear_Cur);
                                        ps2.setInt(4, CashBookMonth_Cur);
                                      //  ps2.setInt(3, CashBookYear);
                                      //  ps2.setInt(4, CashBookMonth);
                                       // ps2.setDate(5, txtDoc_date);
                                        rs2 = ps2.executeQuery();

                                        while (rs2.next()) {
                                            max_branch_id = rs2.getInt("br_id");
                                       
                                           // max_branch_id++;
                                        }

                                        System.out.println("Maximum Brach ID is ==" + 
                                                           max_branch_id);

                                       // ps2.close();
                                      //  rs2.close();

                                    } catch (Exception e) {
                                        System.out.println("Failed to Fetch Maximum Branch ID " + 
                                                           e);
                                        Flag="false";
                                    }
                                    
                                    ArrayList<String> H_code = new ArrayList();                                         
                                    ArrayList<String> SL_code = new ArrayList();                     
                                    ArrayList<String> SL_type = new ArrayList();                     
                                    ArrayList<String> CR_DR_type = new ArrayList(); 
                                    ArrayList<String> Amt_value = new ArrayList();
                                    if(doctype.equalsIgnoreCase("Payment")) {
                                    try {
                                        ps2 = con.prepareStatement("SELECT VOUCHER_NO,ACCOUNT_HEAD_CODE,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,CR_DR_INDICATOR,TOTAL_AMOUNT,to_char(PAYMENT_DATE,'DD/MM/YYYY') as paymentdate," +
                                      "PAID_TO,PAYMENT_DATE as transpaymentdate FROM FAS_PAYMENT_MASTER where ACCOUNTING_UNIT_ID=? and " + 
                                     "ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=?");
                                        ps2.setInt(1, cmbAcc_UnitCode);
                                        ps2.setInt(2, cmbOffice_code);
                                        ps2.setInt(3, CashBookYear);
                                        ps2.setInt(4, CashBookMonth);
                                        ps2.setInt(5, v_code_ins);
                                        rs2 = ps2.executeQuery();

                                        while (rs2.next()) {
                                            subledger = rs2.getInt("SUB_LEDGER_CODE");
                                            accnthead=rs2.getInt("ACCOUNT_HEAD_CODE");
                                            subledgertype=rs2.getInt("SUB_LEDGER_TYPE_CODE");
                                          crdrindicator=rs2.getString("CR_DR_INDICATOR");
                                          payAmount=rs2.getDouble("TOTAL_AMOUNT");
                                          cbrefno=rs2.getInt("VOUCHER_NO");
                                            trans_cbrefdate=rs2.getDate("transpaymentdate");
                                            System.out.println("cbrefdate_trans"+trans_cbrefdate);
                                            if((rs2.getString("paymentdate"))==null){
                                                cbrefdate="";
                                            }
                                            else{
                                              cbrefdate=rs2.getString("paymentdate");}
                                          
                                          
                                            if((rs2.getString("paymentdate"))==null){
                                                paydate="";
                                            }
                                            else{
                                              paydate=rs2.getString("paymentdate");}
                                              if((rs2.getString("PAID_TO"))==null){
                                                  paid_to="";
                                              }
                                              else{
                                              paid_to=rs2.getString("PAID_TO");}
                                              System.out.println("paymentdate"+paydate);
                                              System.out.println("paidto"+paid_to);
                                        }
                                      

                                        System.out.println("subledger is ==" + subledger);
                                        System.out.println("Accounthead"+accnthead);
                                        System.out.println("Subledger"+subledgertype);
                                        System.out.println("crdrindicator"+crdrindicator);
                                        System.out.println("cbrefno"+cbrefno);
                                        System.out.println("cbrefdate"+cbrefdate);
                                        ps2.close();
                                        rs2.close();

                                    } catch (Exception e) {
                                        System.out.println("Failed to Fetch FAS_PAYMENT_MASTER details " + 
                                                           e);
                                        Flag="false";
                                    }
                                    cbreftype="P";
                                    try {
                                        ps2 = 
                                      con.prepareStatement("SELECT ACCOUNT_HEAD_CODE,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,CR_DR_INDICATOR,AMOUNT FROM FAS_PAYMENT_TRANSACTION where ACCOUNTING_UNIT_ID=? and " + 
                                     "ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and CHEQUE_DD_NO=? and VOUCHER_NO=? ");
                                        ps2.setInt(1, cmbAcc_UnitCode);
                                        ps2.setInt(2, cmbOffice_code);
                                        ps2.setInt(3, CashBookYear);
                                        ps2.setInt(4, CashBookMonth);
                                        ps2.setString(5, txtCheque_DD_NO );
                                        ps2.setInt(6,v_code_ins);
                                        rs2 = ps2.executeQuery();
                                        System.out.println("SELECT ACCOUNT_HEAD_CODE,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,CR_DR_INDICATOR,AMOUNT FROM FAS_PAYMENT_TRANSACTION where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and " + 
                                     "ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and CASHBOOK_YEAR="+CashBookYear+" and CASHBOOK_MONTH="+CashBookMonth+" and CHEQUE_DD_NO="+txtCheque_DD_NO+" and VOUCHER_NO="+v_code_ins+" ");
                                        int j=0;
                                        while (rs2.next()) {
                                        
                                            H_code.add(rs2.getString("ACCOUNT_HEAD_CODE"));
                                            SL_type.add(rs2.getString("SUB_LEDGER_TYPE_CODE"));
                                            SL_code.add(rs2.getString("SUB_LEDGER_CODE"));
                                            CR_DR_type.add(rs2.getString("CR_DR_INDICATOR"));
                                            Amt_value.add(rs2.getString("Amount"));
                                            //System.out.println("H_code ==" + H_code.get(k));
                                           // System.out.println("SL_code"+SL_code.get(k));
                                          //  System.out.println("SL_type"+SL_type.get(k));
                                           // System.out.println("CR_DR_type"+CR_DR_type.get(k));                            
                                            j++;
                                        }

                                       j--;
                                       
                                        ps2.close();
                                        rs2.close();

                                    } catch (Exception e) {
                                        System.out.println("Failed to Fetch values from payment_transaction-------------->>" + e);
                                        Flag="false";
                                    }
                                }
                           else if (doctype.equalsIgnoreCase("Fund Transfer")){
                                                   try {
                                                       ps4 = 
                                                   con.prepareStatement("SELECT CR_ACCOUNT_HEAD_CODE,DR_ACCOUNT_HEAD_CODE,TOTAL_AMOUNT FROM FAS_FUND_TRF_FROM_OFFICE where ACCOUNTING_UNIT_ID=? and " +
                                                    "ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=? and CHEQUE_DD_NO=?");
                                                       ps4.setInt(1, cmbAcc_UnitCode);
                                                       ps4.setInt(2, cmbOffice_code);
                                                       ps4.setInt(3, CashBookYear);
                                                       ps4.setInt(4, CashBookMonth);
                                                       ps4.setInt(5, v_code_ins);
                                                       ps4.setString(6, txtCheque_DD_NO );
                                                       rs4 = ps4.executeQuery();

                                                       while (rs4.next()) {
                                                      accnthead          = rs4.getInt("CR_ACCOUNT_HEAD_CODE");
                                                    accnthead_trans        =rs4.getInt("DR_ACCOUNT_HEAD_CODE");
                                                          // subledgertype=rs4.getInt("SUB_LEDGER_TYPE_CODE");
                                                        // crdrindicator=rs4.getString("CR_DR_INDICATOR");
                                                         payAmount=rs4.getDouble("TOTAL_AMOUNT");
                                                           txtAmount=rs4.getDouble("TOTAL_AMOUNT");
                                                       }
                                                   }
                                                   catch (Exception e) {
                                                     System.out.println("Failed to Fetch FAS_FUND_TRF_FROM_OFFICE details " +  e);
                                                                                       }
                                               
                                                    cbreftype="F";
                                                   subledger=0;
                                                   subledgertype=0;
                                                   
                                               }
                                     else if (doctype.equalsIgnoreCase("Fund Transfer HO")){
                                                                                       
                                                                                       try {
                                                                                           ps2 = con.prepareStatement("SELECT VOUCHER_NO,ACCOUNT_HEAD_CODE,CR_DR_INDICATOR,TOTAL_AMOUNT,to_char(DATE_OF_TRANSFER,'DD/MM/YYYY') as paymentdate," +
                                                                                         "DATE_OF_TRANSFER as transpaymentdate FROM FAS_FUND_TRF_FROM_HO_MASTER where ACCOUNTING_UNIT_ID=? and " + 
                                                                                        "ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=?");
                                                                                           ps2.setInt(1, cmbAcc_UnitCode);
                                                                                           ps2.setInt(2, cmbOffice_code);
                                                                                           ps2.setInt(3, CashBookYear);
                                                                                           ps2.setInt(4, CashBookMonth);
                                                                                           ps2.setInt(5, v_code_ins);
                                                                                           rs2 = ps2.executeQuery();

                                                                                           while (rs2.next()) {
                                                                                               
                                                                                               accnthead=rs2.getInt("ACCOUNT_HEAD_CODE");
                                                                                            //   subledgertype=rs2.getInt("SUB_LEDGER_TYPE_CODE");
                                                                                             crdrindicator=rs2.getString("CR_DR_INDICATOR");
                                                                                             payAmount=rs2.getDouble("TOTAL_AMOUNT");
                                                                                             cbrefno=rs2.getInt("VOUCHER_NO");
                                                                                               trans_cbrefdate=rs2.getDate("transpaymentdate");
                                                                                               System.out.println("cbrefdate_trans"+trans_cbrefdate);
                                                                                               if((rs2.getString("paymentdate"))==null){
                                                                                                   cbrefdate="";
                                                                                               }
                                                                                               else{
                                                                                                 cbrefdate=rs2.getString("paymentdate");}
                                                                                             
                                                                                             
                                                                                               if((rs2.getString("paymentdate"))==null){
                                                                                                   paydate="";
                                                                                               }
                                                                                               else{
                                                                                                 paydate=rs2.getString("paymentdate");}
                                                                                                
                                                                                                 System.out.println("paymentdate"+paydate);
                                                                                                // System.out.println("paidto"+paid_to);
                                                                                           }
                                                                                         

                                                                                         //  System.out.println("subledger is ==" + subledger);
                                                                                           System.out.println("Accounthead"+accnthead);
                                                                                       //    System.out.println("Subledger"+subledgertype);
                                                                                           System.out.println("crdrindicator"+crdrindicator);
                                                                                           System.out.println("cbrefno"+cbrefno);
                                                                                           System.out.println("cbrefdate"+cbrefdate);
                                                                                           ps2.close();
                                                                                           rs2.close();

                                                                                       } catch (Exception e) {
                                                                                           System.out.println("Failed to Fetch FAS_FUND_TRF_FROM_HO_MASTER details " + 
                                                                                                              e);
                                                                                           Flag="false";
                                                                                       }
                                                                                       cbreftype="FH";
                                                                                       try {
                                                                                           ps2 = 
                                                                                         con.prepareStatement("SELECT ACCOUNT_HEAD_CODE,CR_DR_INDICATOR,AMOUNT FROM FAS_FUND_TRF_FROM_HO_TRN where ACCOUNTING_UNIT_ID=? and " + 
                                                                                        "ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and CHEQUE_DD_NO=? and VOUCHER_NO=? ");
                                                                                           ps2.setInt(1, cmbAcc_UnitCode);
                                                                                           ps2.setInt(2, cmbOffice_code);
                                                                                           ps2.setInt(3, CashBookYear);
                                                                                           ps2.setInt(4, CashBookMonth);
                                                                                           ps2.setString(5, txtCheque_DD_NO );
                                                                                           ps2.setInt(6,v_code_ins);
                                                                                           rs2 = ps2.executeQuery();
                                                                                           System.out.println("SELECT ACCOUNT_HEAD_CODE,CR_DR_INDICATOR,AMOUNT FROM FAS_FUND_TRF_FROM_HO_TRN where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and " + 
                                                                                        "ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and CASHBOOK_YEAR="+CashBookYear+" and CASHBOOK_MONTH="+CashBookMonth+" and CHEQUE_DD_NO="+txtCheque_DD_NO+" and VOUCHER_NO="+v_code_ins+" ");
                                                                                           int j=0;
                                                                                           while (rs2.next()) {
                                                                                           
                                                                                               H_code.add(rs2.getString("ACCOUNT_HEAD_CODE"));
                                                                                              // SL_type.add(rs2.getString("SUB_LEDGER_TYPE_CODE"));
                                                                                            //   SL_code.add(rs2.getString("SUB_LEDGER_CODE"));
                                                                                               CR_DR_type.add(rs2.getString("CR_DR_INDICATOR"));
                                                                                               Amt_value.add(rs2.getString("Amount"));
                                                                                               //System.out.println("H_code ==" + H_code.get(k));
                                                                                              // System.out.println("SL_code"+SL_code.get(k));
                                                                                             //  System.out.println("SL_type"+SL_type.get(k));
                                                                                              // System.out.println("CR_DR_type"+CR_DR_type.get(k));                            
                                                                                               j++;
                                                                                           }

                                                                                          j--;
                                                                                          
                                                                                           ps2.close();
                                                                                           rs2.close();

                                                                                       } catch (Exception e) {
                                                                                           System.out.println("Failed to Fetch values from FAS_FUND_TRF_FROM_HO_TRN values-------------->>" + e);
                                                                                           Flag="false";
                                                                                       }
                                                                      
                                                                                   
                                                                                        cbreftype="FH";
                                                                                       subledger=0;
                                                                                       subledgertype=0;
                                                                                       
                                                                                   }         
                    
                                         
                if (doctype.equalsIgnoreCase("Payment")) {
                    try {
                        
             ps4 = con.prepareStatement("select max(SL_NO) AS trn_records  FROM FAS_PAYMENT_TRANSACTION WHERE ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID=? AND CASHBOOK_YEAR=?" +
                                          "AND CASHBOOK_MONTH=? AND VOUCHER_NO=? AND CHEQUE_DD_NO=?");
                    ps4.setInt(1, cmbAcc_UnitCode);
                    ps4.setInt(2, cmbOffice_code);
                    ps4.setInt(3, CashBookYear);
                    ps4.setInt(4, CashBookMonth);
                    ps4.setInt(5, v_code_ins);
                    ps4.setString(6, txtCheque_DD_NO );
                    rs4 = ps4.executeQuery();  
                        while (rs4.next()) {
                        transn_records = rs4.getInt("trn_records");
                        System.out.println(transn_records);
                          }
                    }
                    catch (Exception e) {
                      System.out.println("Failed to Fetch FAS_PAYMENT_TRANSACTION details " +  e);
                                                            Flag="false";
                                                        }
                }
                                     if (doctype.equalsIgnoreCase("Fund Transfer Ho")) {
                                         try {
                                             
                                     ps4 = con.prepareStatement("select max(SL_NO) AS trn_records  FROM FAS_FUND_TRF_FROM_HO_TRN WHERE ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID=? AND CASHBOOK_YEAR=?" +
                                                               "AND CASHBOOK_MONTH=? AND VOUCHER_NO=? AND CHEQUE_DD_NO=?");
                                         ps4.setInt(1, cmbAcc_UnitCode);
                                         ps4.setInt(2, cmbOffice_code);
                                         ps4.setInt(3, CashBookYear);
                                         ps4.setInt(4, CashBookMonth);
                                         ps4.setInt(5, v_code_ins);
                                         ps4.setString(6, txtCheque_DD_NO );
                                         rs4 = ps4.executeQuery();  
                                             while (rs4.next()) {
                                             transn_records = rs4.getInt("trn_records");
                                             System.out.println(transn_records);
                                               }
                                         }
                                         catch (Exception e) {
                                           System.out.println("Failed to Fetch trn_records for FAS_FUND_TRF_FROM_HO_TRN " +  e);
                                                                                 Flag="false";
                                                                             }
                                     }
                else if (doctype.equalsIgnoreCase("Fund Transfer")){
                    transn_records=1;
                }
               

                                     String sqljournal_pay= "insert into FAS_JOURNAL_MASTER(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID," + 
                                     "CASHBOOK_YEAR,CASHBOOK_MONTH, VOUCHER_DATE,VOUCHER_NO,JOURNAL_TYPE_CODE,SUB_LEDGER_CODE, " + 
                                     "DEPRECIATION_RATE,CHEQUE_NO,CHEQUE_DATE,CB_REF_TYPE,TOTAL_TRN_RECORDS,REMARKS," + 
                                     "MODE_OF_CREATION,CREATED_BY_MODULE,JOURNAL_STATUS,UPDATED_BY_USER_ID,UPDATED_DATE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                                     if(Flag.equals("true"))
                                     {
                                
                                           try {
                                                ps2 = con.prepareStatement(sqljournal_pay);
                                                ps2.setInt(1, cmbAcc_UnitCode);
                                                ps2.setInt(2, cmbOffice_code);
                                                ps2.setInt(3, CashBookYear_Cur);
                                                ps2.setInt(4, CashBookMonth_Cur);
                                                ps2.setDate(5,txtCrea_date);
                                             //  ps2.setTimestamp(5,ts);
                                                ps2.setInt(6, max_branch_id);
                                                ps2.setInt(7, 56);
                                                ps2.setInt(8,subledger);
                                                ps2.setInt(9,subledgertype);
                                                ps2.setString(10, txtCheque_DD_NO);
                                                ps2.setDate(11, cheq_date_ins);
                                                ps2.setString(12,cbreftype);
                                                ps2.setInt(13,transn_records);
                                                ps2.setString(14,"GJV created on Cheque Cancellation for cheqno:"+txtCheque_DD_NO+"cheqdate:"+ oldcheqdate+"payvrno:"+txtVoucher_No+"paidto:"+paid_to+"paiddate:"+paydate+"");
                                                ps2.setString(15,"A");
                                                ps2.setString(16, "GJV");
                                                ps2.setString(17, "L");
                                                ps2.setString(18, update_user);
                                                ps2.setTimestamp(19, ts);
                                              //  max_branch_id++;
                                                int z=ps2.executeUpdate();
                                                if(z>0)
                                                {
                                                    Flag="true";
                                                    System.out.println("FAS_JOURNAL_MASTER insertion success");
                                                }
                                                else
                                                {
                                                    Flag="false";
                                                    System.out.println("FAS_JOURNAL_MASTER insertion failed");
                                                }
                                                System.out.println("here is journal entry ok");
                                               ps2.close();
                                            } catch (Exception e) {
                                                System.out.println("exception in journal entry:" + e);
                                                Flag="false";
                                            }
                                    }
                                    System.out.println(sqljournal_pay);
                                    int SL_NO=1;
                                    try {
                                        txtCheque_DD = request.getParameter("txtCheque_DD");
                                    } catch (Exception e) {
                                        System.out.println("excepion in cheque/dd type :" + e);
                                        Flag="false";
                                    }
                                    String txtCheque_Tran1="",txtCheque_Tran2="";
                    int cbrefno_trans=0;
                            if(Flag.equals("true"))
                            {        
                                if(doctype.equalsIgnoreCase("Payment")){
                                    if (crdrindicator_trans.equalsIgnoreCase("CR")) {txtCheque_Tran2="DR";}//cbrefno=0;cbrefdate="";}
                                    else if (crdrindicator_trans.equalsIgnoreCase("DR"))  txtCheque_Tran2="CR";//cbrefno_trans=cbrefno;cbref_transdate=cbrefdate;
                                    System.out.println("txtCheque_Tran=="+txtCheque_Tran1);
                                    if (crdrindicator.equalsIgnoreCase("CR")) {txtCheque_Tran1="DR";}//cbrefno=0;cbrefdate="";}
                                    else if (crdrindicator.equalsIgnoreCase("DR"))  txtCheque_Tran1="CR";//cbrefno_trans=cbrefno;cbref_transdate=cbrefdate;
                                    System.out.println("txtCheque_Tran=="+txtCheque_Tran1);
                                }
                                else if(doctype.equalsIgnoreCase("Fund Transfer")){
                                
                                    txtCheque_Tran1="DR";
                                    txtCheque_Tran2="CR";
                                    crdrindicator="CR";
                                    crdrindicator_trans="DR";
                                    subledger=0;
                                    subledgertype=0;
                                    cbrefno=0;cbrefdate="";
                                }
                                    else if(doctype.equalsIgnoreCase("Fund Transfer HO")){
                                         
                                             if (crdrindicator_trans.equalsIgnoreCase("CR")) {txtCheque_Tran2="DR";}//cbrefno=0;cbrefdate="";}
                                             else if (crdrindicator_trans.equalsIgnoreCase("DR"))  txtCheque_Tran2="CR";//cbrefno_trans=cbrefno;cbref_transdate=cbrefdate;
                                             System.out.println("txtCheque_Tran=="+txtCheque_Tran1);
                                             if (crdrindicator.equalsIgnoreCase("CR")) {txtCheque_Tran1="DR";}//cbrefno=0;cbrefdate="";}
                                             else if (crdrindicator.equalsIgnoreCase("DR"))  txtCheque_Tran1="CR";//cbrefno_trans=cbrefno;cbref_transdate=cbrefdate;
                                             System.out.println("txtCheque_Tran=="+txtCheque_Tran1);
                                         }
                   if(Flag.equals("true"))
                   {
                                //-----------journal transaction from payment master----------
                                 String sqljournaltrans_pay1="insert into FAS_JOURNAL_TRANSACTION(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID," + 
                                  "CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,SL_NO,ACCOUNT_HEAD_CODE,CR_DR_INDICATOR,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE, " + 
                                  "CHEQUE_OR_DD,CHEQUE_DD_NO,CHEQUE_DD_DATE,AMOUNT,PARTICULARS,UPDATED_BY_USER_ID,UPDATED_DATE,CB_REF_NO,CB_REF_DATE)" + 
                                  "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                                 // String particulars=""+txtCheque_DD_NO+","che_DD_date",txtVoucher_No,;
                                 try {
                                      ps2 = con.prepareStatement(sqljournaltrans_pay1);
                                      ps2.setInt(1, cmbAcc_UnitCode);
                                      ps2.setInt(2, cmbOffice_code);
                                      ps2.setInt(3, CashBookYear_Cur);
                                      ps2.setInt(4, CashBookMonth_Cur);
                                      ps2.setInt(5,max_branch_id);
                                      ps2.setInt(6,SL_NO);
                                      ps2.setInt(7,accnthead);
                                      ps2.setString(8,txtCheque_Tran1);
                                      ps2.setInt(9,subledgertype);
                                      ps2.setInt(10,subledger);
                                      ps2.setString(11,txtCheque_DD);
                                      ps2.setString(12, txtCheque_DD_NO);
                                      ps2.setDate(13, cheq_date_ins);
                                      ps2.setDouble(14,Amt_value_ins); 
                                    //  ps2.setDouble(14,txtAmount);
                                      ps2.setString(15,"GJV created on Cheque Cancellation for cheqno:"+txtCheque_DD_NO+"cheqdate:"+ oldcheqdate+"payvrno:"+txtVoucher_No+"paidto:"+paid_to+"paiddate:"+paydate+"");
                                     
                                      ps2.setString(16, update_user);
                                      ps2.setTimestamp(17, ts);
                                      
                                      ps2.setInt(18,cbrefno);
                                      ps2.setDate(19,trans_cbrefdate);
                                      SL_NO++;
                                     // max_branch_id++;
                                      int z=ps2.executeUpdate();
                                      if(z>0)
                                      {
                                          Flag="true";
                                          System.out.println("FAS_JOURNAL_TRANSACTION--->1 insertion success");
                                      }
                                      else
                                      {
                                          Flag="false";
                                          System.out.println("FAS_JOURNAL_TRANSACTION--->1 insertion failed");
                                      }
                                      System.out.println("here is journaltransaction entry from payment_master ok");
                                   ps2.close();
                                  } catch (Exception e) {
                                      System.out.println("exception in journal entry:" + e);
                                  }
                                            
                }
                
                ///------------------------
                 if(Flag.equals("true"))
                 {
                    if(doctype.equalsIgnoreCase("Payment")){
                System.out.println("doctype for journal trns:"+doctype);
                                String rad_sub_CR_DR="";
                                int ahead_trans=0,cmbSL_type=0,cmbSL_Code=0,Amt_ind=0;
                                
                                 try {
                                         
                                            System.out.println("doctype for journal trns:"+doctype);
                                            
                                            System.out.println("H_CODE Length ------------------->>>"+H_code.size());                    
                                            
                                            for(int j=0;j<H_code.size();j++)
                                            {
                                                String sqljournaltrans_pay="" +
                                                "insert into FAS_JOURNAL_TRANSACTION                    \n" + 
                                                "(  ACCOUNTING_UNIT_ID,                                 \n" + 
                                                "   ACCOUNTING_FOR_OFFICE_ID,                           \n" + 
                                                "   CASHBOOK_YEAR,                                      \n" + 
                                                "   CASHBOOK_MONTH,                                     \n" + 
                                                "   VOUCHER_NO,                                         \n" + 
                                                "   SL_NO,                                              \n" + 
                                                "   ACCOUNT_HEAD_CODE,                                  \n" + 
                                                "   CR_DR_INDICATOR,                                    \n" + 
                                                "   SUB_LEDGER_TYPE_CODE,                               \n" + 
                                                "   SUB_LEDGER_CODE,                                    \n" + 
                                                "   CHEQUE_OR_DD,                                       \n" + 
                                                "   CHEQUE_DD_NO,                                       \n" + 
                                                "   CHEQUE_DD_DATE,                                     \n" + 
                                                "   AMOUNT,                                             \n" + 
                                                "   PARTICULARS,                                        \n" + 
                                                "   UPDATED_BY_USER_ID,                                 \n" + 
                                                "   UPDATED_DATE,                                       \n" + 
                                                "   CB_REF_NO,                                          \n" + 
                                                "   CB_REF_DATE                                         \n" + 
                                                ")                                                      \n" + 
                                                "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                                             
                                                 ps2 = con.prepareStatement(sqljournaltrans_pay);
                                                 
                                                     try{ahead_trans=Integer.parseInt(H_code.get(j));}catch(Exception e){System.out.println("exception in trans "+e);}
                                                     try{rad_sub_CR_DR=CR_DR_type.get(j);}catch(Exception e){System.out.println("exception in CR_DR Indicator "+e);}    
                                                     try{cmbSL_type=Integer.parseInt(SL_type.get(j));}catch(Exception e){System.out.println("exception in trans "+e);}
                                                     try{cmbSL_Code=Integer.parseInt(SL_code.get(j));}catch(Exception e){System.out.println("exception in trans "+e);}
                                                     try{Amt_ind=Integer.parseInt(Amt_value.get(j));}catch(Exception e){System.out.println("exception in trans "+e);}
                                                     
                                                     System.out.println("------------------------------------------------->>>>>>>>>>>>>>>>>");
                                                     System.out.println("AH_code[j] "+ahead_trans);
                                                     System.out.println("IND[j] "+rad_sub_CR_DR);
                                                     System.out.println("ST[j]"+cmbSL_type);
                                                     System.out.println("SL[j]"+cmbSL_Code); 
                                                     System.out.println("Amt_ind"+Amt_ind);
                                                     System.out.println("------------------------------------------------->>>>>>>>>>>>>>>>>");
                                                     String rad_sub_CR_DR2="";
                                                     if(doctype.equalsIgnoreCase("Payment")){
                                                         if (rad_sub_CR_DR.equalsIgnoreCase("CR")) {rad_sub_CR_DR2="DR";}//cbrefno=0;cbrefdate="";}
                                                         else if (rad_sub_CR_DR.equalsIgnoreCase("DR"))  rad_sub_CR_DR2="CR";//cbrefno_trans=cbrefno;cbref_transdate=cbrefdate;
                                                         System.out.println("rad_sub_CR_DR=="+rad_sub_CR_DR2);
                                                         
                                                     }
                                                  
                                                 ps2.setInt(1, cmbAcc_UnitCode);
                                                 ps2.setInt(2, cmbOffice_code);
                                                 ps2.setInt(3, CashBookYear_Cur);
                                                 ps2.setInt(4, CashBookMonth_Cur);
                                                 ps2.setInt(5, max_branch_id);
                                                 ps2.setInt(6,SL_NO);
                                                 ps2.setInt(7,ahead_trans);
                                                 ps2.setString(8,rad_sub_CR_DR2);
                                                 ps2.setInt(9,cmbSL_type);
                                                 ps2.setInt(10,cmbSL_Code);
                                                 ps2.setString(11,txtCheque_DD);
                                                 ps2.setString(12, txtCheque_DD_NO);
                                                 ps2.setDate(13, cheq_date_ins);
                                                 ps2.setDouble(14,Amt_ind);    
                                                 ps2.setString(15,"GJV created on Cheque Cancellation for cheqno:"+txtCheque_DD_NO+"cheqdate:"+ oldcheqdate+"payvrno:"+txtVoucher_No+"paidto:"+paid_to+"paiddate:"+paydate+""); 
                                                 ps2.setString(16, update_user);
                                                 ps2.setTimestamp(17, ts);                         
                                                 ps2.setInt(18,cbrefno_trans);
                                                 ps2.setDate(19,cbref_transdate);
                                                 
                                                 SL_NO++;
                                                 int z=ps2.executeUpdate();
                                                 if(z>0)
                                                 {
                                                     Flag="true";
                                                     System.out.println("Flag"+Flag);
                                                     System.out.println("FAS_JOURNAL_TRANSACTION-->2 insertion success");
                                                 }
                                                 else
                                                 {
                                                     Flag="false";
                                                     System.out.println("FAS_JOURNAL_TRANSACTION-->2 insertion failed");
                                                 }
                                                
                                                 System.out.println("here is journaltransaction entry ok");
                                                 
                                                 
                                                 }
                                        ps2.close();
                                            
                                     }catch (Exception e) {
                                         System.out.println("exception in journal entry:" + e);
                                         Flag="false";
                                     }
                            }
                                             if(doctype.equalsIgnoreCase("Fund Transfer Ho")){
                                             System.out.println("doctype for journal trns:"+doctype);
                                                         String rad_sub_CR_DR="";
                                                         int ahead_trans=0,cmbSL_type=0,cmbSL_Code=0,Amt_ind=0;
                                                         
                                                          try {
                                                                  
                                                                     System.out.println("doctype for journal trns:"+doctype);
                                                                     
                                                                     System.out.println("H_code Length ------------------->>>"+H_code.size());                    
                                                                     
                                                                     for(int j=0;j<H_code.size();j++)
                                                                     {
                                                                         String sqljournaltrans_pay="" +
                                                                         "insert into FAS_JOURNAL_TRANSACTION                    \n" + 
                                                                         "(  ACCOUNTING_UNIT_ID,                                 \n" + 
                                                                         "   ACCOUNTING_FOR_OFFICE_ID,                           \n" + 
                                                                         "   CASHBOOK_YEAR,                                      \n" + 
                                                                         "   CASHBOOK_MONTH,                                     \n" + 
                                                                         "   VOUCHER_NO,                                         \n" + 
                                                                         "   SL_NO,                                              \n" + 
                                                                         "   ACCOUNT_HEAD_CODE,                                  \n" + 
                                                                         "   CR_DR_INDICATOR,                                    \n" + 
                                                                         "   SUB_LEDGER_TYPE_CODE,                               \n" + 
                                                                         "   SUB_LEDGER_CODE,                                    \n" + 
                                                                         "   CHEQUE_OR_DD,                                       \n" + 
                                                                         "   CHEQUE_DD_NO,                                       \n" + 
                                                                         "   CHEQUE_DD_DATE,                                     \n" + 
                                                                         "   AMOUNT,                                             \n" + 
                                                                         "   PARTICULARS,                                        \n" + 
                                                                         "   UPDATED_BY_USER_ID,                                 \n" + 
                                                                         "   UPDATED_DATE,                                       \n" + 
                                                                         "   CB_REF_NO,                                          \n" + 
                                                                         "   CB_REF_DATE                                         \n" + 
                                                                         ")                                                      \n" + 
                                                                         "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                                                                      
                                                                          ps2 = con.prepareStatement(sqljournaltrans_pay);
                                                                          
                                                                              try{ahead_trans=Integer.parseInt(H_code.get(j));}catch(Exception e){System.out.println("exception in trans "+e);}
                                                                              try{rad_sub_CR_DR=CR_DR_type.get(j);}catch(Exception e){System.out.println("exception in CR_DR Indicator "+e);}    
                                                                              try{cmbSL_type=Integer.parseInt(SL_type.get(j));}catch(Exception e){System.out.println("exception in trans "+e);}
                                                                              try{cmbSL_Code=Integer.parseInt(SL_code.get(j));}catch(Exception e){System.out.println("exception in trans "+e);}
                                                                              try{Amt_ind=Integer.parseInt(Amt_value.get(j));}catch(Exception e){System.out.println("exception in trans "+e);}
                                                                              
                                                                              System.out.println("------------------------------------------------->>>>>>>>>>>>>>>>>");
                                                                              System.out.println("AH_code[j] "+ahead_trans);
                                                                              System.out.println("IND[j] "+rad_sub_CR_DR);
                                                                              System.out.println("ST[j]"+cmbSL_type);
                                                                              System.out.println("SL[j]"+cmbSL_Code); 
                                                                              System.out.println("Amt_ind"+Amt_ind);
                                                                              System.out.println("------------------------------------------------->>>>>>>>>>>>>>>>>");
                                                                              String rad_sub_CR_DR2="";
                                                                              if(doctype.equalsIgnoreCase("Fund Transfer HO")){
                                                                                  if (rad_sub_CR_DR.equalsIgnoreCase("CR")) {rad_sub_CR_DR2="DR";}//cbrefno=0;cbrefdate="";}
                                                                                  else if (rad_sub_CR_DR.equalsIgnoreCase("DR"))  rad_sub_CR_DR2="CR";//cbrefno_trans=cbrefno;cbref_transdate=cbrefdate;
                                                                                  System.out.println("rad_sub_CR_DR=="+rad_sub_CR_DR2);
                                                                                  
                                                                              }
                                                                           
                                                                          ps2.setInt(1, cmbAcc_UnitCode);
                                                                          ps2.setInt(2, cmbOffice_code);
                                                                          ps2.setInt(3, CashBookYear_Cur);
                                                                          ps2.setInt(4, CashBookMonth_Cur);
                                                                          ps2.setInt(5, max_branch_id);
                                                                          ps2.setInt(6,SL_NO);
                                                                          ps2.setInt(7,ahead_trans);
                                                                          ps2.setString(8,rad_sub_CR_DR2);
                                                                          ps2.setInt(9,cmbSL_type);
                                                                          ps2.setInt(10,cmbSL_Code);
                                                                          ps2.setString(11,txtCheque_DD);
                                                                          ps2.setString(12, txtCheque_DD_NO);
                                                                          ps2.setDate(13, cheq_date_ins);
                                                                          ps2.setDouble(14,Amt_ind);    
                                                                          ps2.setString(15,"GJV created on Cheque Cancellation for cheqno:"+txtCheque_DD_NO+"cheqdate:"+ oldcheqdate+"payvrno:"+txtVoucher_No+"paidto:"+paid_to+"paiddate:"+paydate+""); 
                                                                          ps2.setString(16, update_user);
                                                                          ps2.setTimestamp(17, ts);                         
                                                                          ps2.setInt(18,cbrefno_trans);
                                                                          ps2.setDate(19,cbref_transdate);
                                                                          
                                                                          SL_NO++;
                                                                          int z=ps2.executeUpdate();
                                                                          if(z>0)
                                                                          {
                                                                              Flag="true";
                                                                              System.out.println("Flag"+Flag);
                                                                              System.out.println("FAS_JOURNAL_TRANSACTION-->2 insertion success");
                                                                          }
                                                                          else
                                                                          {
                                                                              Flag="false";
                                                                              System.out.println("FAS_JOURNAL_TRANSACTION-->2 insertion failed");
                                                                          }
                                                                         
                                                                          System.out.println("here is journaltransaction entry ok");
                                                                          
                                                                          
                                                                          }
                                                                 ps2.close();
                                                                     
                                                              }catch (Exception e) {
                                                                  System.out.println("exception in journal entry:" + e);
                                                                  Flag="false";
                                                              }
                                                     }     
                                            
                         else if(doctype.equalsIgnoreCase("Fund Transfer")){
                             String sqljournaltrans_pay="insert into FAS_JOURNAL_TRANSACTION(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID," + 
                                                  "CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,SL_NO,ACCOUNT_HEAD_CODE,CR_DR_INDICATOR,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE, " + 
                                                  "CHEQUE_OR_DD,CHEQUE_DD_NO,CHEQUE_DD_DATE,AMOUNT,PARTICULARS,UPDATED_BY_USER_ID,UPDATED_DATE,CB_REF_NO,CB_REF_DATE)" + 
                                                  "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                                                  
                                                 try {
                                                     
                                                 
                                                        // System.out.println("txtCheque_Tran"+txtCheque_Tran2);
                                                              ps2 = con.prepareStatement(sqljournaltrans_pay);
                                                              ps2.setInt(1, cmbAcc_UnitCode);
                                                              ps2.setInt(2, cmbOffice_code);
                                                              ps2.setInt(3, CashBookYear_Cur);
                                                              ps2.setInt(4, CashBookMonth_Cur);
                                                              ps2.setInt(5, max_branch_id);
                                                              ps2.setInt(6,SL_NO);
                                                              ps2.setInt(7,accnthead_trans);
                                                              ps2.setString(8,crdrindicator_trans);
                                                              ps2.setInt(9,subledgertype_trans);
                                                              ps2.setInt(10,subledger_trans);
                                                              ps2.setString(11,txtCheque_DD);
                                                              ps2.setString(12, txtCheque_DD_NO);
                                                              ps2.setDate(13, cheq_date_ins);
                                                              ps2.setDouble(14,Amt_value_ins) ;
                                                          //    ps2.setDouble(14,txtAmount);
                                                              ps2.setString(15,"GJV created on Cheque Cancellation for cheqno:"+txtCheque_DD_NO+"cheqdate:"+ oldcheqdate+"payvrno:"+txtVoucher_No+"paidto:"+paid_to+"paiddate:"+paydate+"");
                                                             
                                                              ps2.setString(16, update_user);
                                                              ps2.setTimestamp(17, ts);
                                                             
                                                              ps2.setInt(18,cbrefno_trans);
                                                              ps2.setDate(19,cbref_transdate);
                                                              SL_NO++;
                                                              //max_branch_id++;
                                                              int z=ps2.executeUpdate();
                                                              if(z>0)
                                                              {
                                                                  Flag="true";
                                                                  System.out.println("FAS_JOURNAL_TRANSACTION--->3 insertion success");
                                                              }
                                                              else
                                                              {
                                                                  Flag="false";
                                                                  System.out.println("FAS_JOURNAL_TRANSACTION-->3 insertion failed");
                                                              }
                                                             
                                                              System.out.println("here is journaltransaction entry ok");
                                                      ps2.close();
                                                  } catch (Exception e) {
                                                      System.out.println("exception in journal entry:" + e);
                                                      Flag="false";
                                                  }
                                             

                                            }
                                         }
                                     }             
             
            
                                 }
                           }
                       if(Flag.equals("true"))
                       {
                            rec_count ++;     
                       }
                   }
                            
                if(rec_count==voucher.size())
                    {
                        try{con.commit();}catch(SQLException sqle){}
                        System.out.println("All records are saved without exception");
                        sendMessage(response, 
                                    " Cross Reference details stored for the voucher no"+voucher, 
                                    "ok");
                    }
                    else {
                         
                        {
                            try{con.rollback();}catch(SQLException sqle){}
                            System.out.println("failed to save all records");
                            sendMessage(response,"The Cheque Cancellation Failed ","ok");
                            //System.out.println("Exception occur due to "+e);
                        }
                       
                    }
               
                
                    //System.out.println("done");
                  //  try{con.setAutoCommit(true);  }catch(SQLException sqle){}
                
            }
            
           
            /// for no condn for issuing  new cheque//////////////////////////////////
            
             else {    
                    String paydate=null;
                    String paid_to="";
                   
                        if(txtCheque_DD2.equalsIgnoreCase("N")) {txtAmount2=0;txtCheque_DD2="N";txtCheque_DD_NO2=null;txtCheque_DD_date2=null;txtCheq_DD_New=null;txtCheque_DD3="N";}
                 int v_code_ins=0,cheq_no_ins=0,Amt_value_ins=0;
                 Date doc_date_ins=null,cheq_date_ins=null;
                 String cheq_type_ins=null,CR_DR_ins=null,doc_date2=null,cheq_date2=null;
                 System.out.println("V_Code Length ------------------->>>"+voucher.size());                     
                               for(int k=0; k< voucher.size(); k++){ 
                         

                                   try{v_code_ins=Integer.parseInt(voucher.get(k));}catch(Exception e){System.out.println("exception in voucher "+e);}
                                   try{doc_date2=doc_date.get(k);}catch(Exception e){System.out.println("exception in doc_date2 "+e);}
                                   try{cheq_date2=cheq_date.get(k);}catch(Exception e){System.out.println("exception in cheq_date2 "+e);}
                                   try{
                                       System.out.println("Before Date Convertion :"+doc_date2);
                                       String[] sd5=doc_date2.split("/");
                                       c=new GregorianCalendar(Integer.parseInt(sd5[2]),Integer.parseInt(sd5[1])-1,Integer.parseInt(sd5[0]));
                                       java.util.Date d5=c.getTime();
                                       doc_date_ins=new Date(d5.getTime());}catch(Exception e){System.out.println("exception in docdate "+e);}
                                       System.out.println("After Date Convertion :"+doc_date_ins);
                                   try{System.out.println("Before Date Convertion :"+cheq_date2);
                                       String[] sd5=cheq_date2.split("/");
                                       c=new GregorianCalendar(Integer.parseInt(sd5[2]),Integer.parseInt(sd5[1])-1,Integer.parseInt(sd5[0]));
                                       java.util.Date d5=c.getTime();
                                       cheq_date_ins=new Date(d5.getTime());}catch(Exception e){System.out.println("exception in cheqdate "+e);}   
                                   System.out.println("After Date Convertion :"+cheq_date_ins);
                                   try{cheq_type_ins=cheq_type.get(k);}catch(Exception e){System.out.println("exception in cheqtype "+e);}
                                   try{Amt_value_ins=Integer.parseInt(Amtvalue.get(k));}catch(Exception e){System.out.println("exception in amount "+e);}
                                  
                 String sql_fund1 = 
                 ("insert into FAS_CHEQUE_CANCEL(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,DOCUMENT_DATE,DOCUMENT_TYPE,DOCUMENT_NO,CHEQUE_OR_DD,CHEQUE_DD_NO,CHEQUE_DD_DATE,AMOUNT,REMARKS,UPDATED_BY_USER_ID,UPDATED_DATE,CHEQ_CANCEL_DATE,ISSUE_CHEQUE,CREA_PAY_VOU) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                // ("insert into FAS_CHEQUE_CANCEL(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,DOCUMENT_DATE,DOCUMENT_TYPE,DOCUMENT_NO,CHEQUE_OR_DD,CHEQUE_DD_NO,CHEQUE_DD_DATE,AMOUNT,REMARKS,ISSUE_CHEQUE,ISSUE_CHEQUE_NO,ISSUE_CHEQUE_DATE,ISSUE_CHEQUE_AMOUNT,UPDATED_BY_USER_ID,UPDATED_DATE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                 String cbreftype="";
                          
                 try {

                     System.out.println("here begin..add2 for no condn");
                     ps = con.prepareStatement(sql_fund1);
                     ps.setInt(1, cmbAcc_UnitCode);
                     ps.setInt(2, cmbOffice_code);
                     ps.setInt(3, CashBookYear_Cur);
                     ps.setInt(4, CashBookMonth_Cur);
                     ps.setDate(5, doc_date_ins);
                     ps.setString(6, doctype);
                     ps.setInt(7, v_code_ins);
                     ps.setString(8, txtCheque_DD);
                     ps.setString(9, txtCheque_DD_NO);
                     ps.setDate(10, cheq_date_ins);
                     ps.setDouble(11, Amt_value_ins);
                     ps.setString(12, txtRemarks);
                     ps.setString(13, update_user);
                     System.out.println("update_user"+update_user);
                     ps.setTimestamp(14, ts);
                     ps.setDate(15,txtCrea_date);
                     ps.setString(16,txtCheque_DD2);
                     ps.setString(17,txtCheque_DD3);
                    // ps.setString(16,"");
                  int  z= ps.executeUpdate();
                     if(z>0){
                         Flag="true";
                         System.out.println("FAS_CHEQUE_CANCEL insertion success");
                     }
                     else{
                         Flag="false";
                         System.out.println("FAS_CHEQUE_CANCEL insertion failed");
                     }
                      ps.close(); 
                    xml = xml + "<flag>success</flag>";
                     System.out.println("here is ok");
                     //sendMessage(response, 
                                // " Cross Reference details stored for the voucher no"+txtReceipt_No, 
                                // "ok");
                
                 } catch (Exception e) {
                        Flag="false";
                     System.out.println("Exception occur due to " + e);
                     //  sendMessage(response,"Cheque Details already exist ","ok");
                 }
                      if (doctype.equalsIgnoreCase("Payment")) {
                      
                                      try {
                                                               
                                                      
                                                             ps2 = con.prepareStatement("UPDATE FAS_PAYMENT_MASTER SET CHEQ_CANCEL_STATUS=?,UPDATED_DATE=?, UPDATED_BY_USER_ID=? WHERE ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID=? AND CASHBOOK_YEAR=? AND CASHBOOK_MONTH=? " +
                                                                                        " AND VOUCHER_NO=?  ");
                                                               
                                                             
                                                             ps2.setString(1,"Y");
                                                             ps2.setTimestamp(2, ts);
                                                             ps2.setString(3, update_user);
                                                             ps2.setInt(4, cmbAcc_UnitCode);
                                                             ps2.setInt(5, cmbOffice_code);
                                                             ps2.setInt(6, CashBookYear);
                                                             ps2.setInt(7, CashBookMonth);
                                                             ps2.setInt(8, v_code_ins);
                                                           
                                                            int  z= ps2.executeUpdate();
                                                                  if(z>0){
                                                                      Flag="true";
                                                                      System.out.println("FAS_PAYMENT_MASTER insertion success");
                                                                  }
                                                                  else{
                                                                      Flag="false";
                                                                      System.out.println("FAS_PAYMENT_MASTER insertion failed");
                                                                  }
                                                                   ps2.close(); 
                                                             
                                                             
                                                              System.out.println("here update in payment entry ok");
                                                           } catch (Exception e) {
                                                                Flag="false";
                                                               System.out.println("exception in update payment entry:" + e);
                                                                     }
                                                                
                                                              

                         
                                      try {
                                               
                                      
                                             ps2 = con.prepareStatement("UPDATE FAS_PAYMENT_TRANSACTION SET CHEQ_CANCEL_STATUS=?,UPDATED_DATE=?, UPDATED_BY_USER_ID=? WHERE ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID=? AND CASHBOOK_YEAR=? AND CASHBOOK_MONTH=? " +
                                                                        " AND VOUCHER_NO=? AND CHEQUE_DD_NO=? ");
                                               
                                             
                                             ps2.setString(1,"Y");
                                             ps2.setTimestamp(2, ts);
                                             ps2.setString(3, update_user);
                                             ps2.setInt(4, cmbAcc_UnitCode);
                                             ps2.setInt(5, cmbOffice_code);
                                             ps2.setInt(6, CashBookYear);
                                             ps2.setInt(7, CashBookMonth);
                                             ps2.setInt(8, v_code_ins);
                                             ps2.setString(9, txtCheque_DD_NO );
                                           
                                               int  z= ps2.executeUpdate();
                                                  if(z>0){
                                                      Flag="true";
                                                      System.out.println("FAS_PAYMENT_TRANSACTION insertion success");
                                                  }
                                                  else{
                                                      Flag="false";
                                                      System.out.println("FAS_PAYMENT_TRANSACTION insertion failed");
                                                  }
                                                   ps2.close(); 
                                              System.out.println("here update in payment transaction entry ok");
                                           } catch (Exception e) {
                                               System.out.println("exception in update payment transaction entry:" + e);
                                                     }
                                  }
                                  
                      else if (doctype.equalsIgnoreCase("Fund Transfer")){
                          try {
                                   
                          
                                 ps2 = con.prepareStatement("UPDATE FAS_FUND_TRF_FROM_OFFICE SET CHEQ_CANCEL_STATUS=?,UPDATED_DATE=?,UPDATED_BY_USER_ID=? WHERE ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID=? AND CASHBOOK_YEAR=? AND CASHBOOK_MONTH=? " +
                                                            " AND VOUCHER_NO=? ");
                                 
                                 ps2.setString(1,"Y");
                                 ps2.setTimestamp(2, ts);
                                 ps2.setString(3, update_user);  
                                 ps2.setInt(4, cmbAcc_UnitCode);
                                 ps2.setInt(5, cmbOffice_code);
                                 ps2.setInt(6, CashBookYear);
                                 ps2.setInt(7, CashBookMonth);
                                 ps2.setInt(8, v_code_ins);
                                   int  z= ps2.executeUpdate();
                                      if(z>0){
                                          Flag="true";
                                          System.out.println("FAS_FUND_TRF_FROM_OFFICE insertion success");
                                      }
                                      else{
                                          Flag="false";
                                          System.out.println("FAS_FUND_TRF_FROM_OFFICE insertion failed");
                                      }
                                       ps2.close(); 
                                  System.out.println("here update in FAS_FUND_TRF_FROM_OFFICE entry ok");
                               } catch (Exception e) {
                                     Flag="false";
                                   System.out.println("exception in FAS_FUND_TRF_FROM_OFFICE entry:" + e);
                                         }
                      }
                      
                 else if (doctype.equalsIgnoreCase("Fund Transfer HO")){
                     try {
                              
                     
                            ps2 = con.prepareStatement("UPDATE FAS_FUND_TRF_FROM_HO_MASTER SET CHEQ_CANCEL_STATUS=?,UPDATED_DATE=?,UPDATED_BY_USER_ID=? WHERE ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID=? AND CASHBOOK_YEAR=? AND CASHBOOK_MONTH=? " +
                                                       " AND VOUCHER_NO=? ");
                            
                            ps2.setString(1,"Y");
                            ps2.setTimestamp(2, ts);
                            ps2.setString(3, update_user);  
                            ps2.setInt(4, cmbAcc_UnitCode);
                            ps2.setInt(5, cmbOffice_code);
                            ps2.setInt(6, CashBookYear);
                            ps2.setInt(7, CashBookMonth);
                            ps2.setInt(8, v_code_ins);
                              int  z= ps2.executeUpdate();
                                 if(z>0){
                                     Flag="true";
                                     System.out.println("FAS_FUND_TRF_FROM_HO_MASTER insertion success");
                                 }
                                 else{
                                     Flag="false";
                                     System.out.println("FAS_FUND_TRF_FROM_HO_MASTER insertion failed");
                                 }
                                  ps2.close(); 
                             System.out.println("here update in FAS_FUND_TRF_FROM_HO_MASTER entry ok");
                          } catch (Exception e) {
                                Flag="false";
                              System.out.println("exception in FAS_FUND_TRF_FROM_HO_MASTER entry:" + e);
                                    }
                     try {
                           System.out.println("inside fund transfer HO TRANSACTION");   
                     
                            ps2 = con.prepareStatement("UPDATE FAS_FUND_TRF_FROM_HO_TRN SET CHEQ_CANCEL_STATUS=?,UPDATED_DATE=?,UPDATED_BY_USER_ID=? WHERE ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID=? AND CASHBOOK_YEAR=? AND CASHBOOK_MONTH=? " +
                                                       " AND VOUCHER_NO=? ");
                            
                            ps2.setString(1,"Y");
                            ps2.setTimestamp(2, ts);
                            ps2.setString(3, update_user);  
                            ps2.setInt(4, cmbAcc_UnitCode);
                            ps2.setInt(5, cmbOffice_code);
                            ps2.setInt(6, CashBookYear);
                            ps2.setInt(7, CashBookMonth);
                            ps2.setInt(8, v_code_ins);
                              int  z= ps2.executeUpdate();
                                 if(z>0){
                                     Flag="true";
                                     System.out.println("FAS_FUND_TRF_FROM_HO_MASTER insertion success");
                                 }
                                 else{
                                     Flag="false";
                                     System.out.println("FAS_FUND_TRF_FROM_HO_MASTER insertion failed");
                                 }
                                  ps2.close(); 
                             System.out.println("here update in FAS_FUND_TRF_FROM_HO_MASTER entry ok");
                          } catch (Exception e) {
                                Flag="false";
                              System.out.println("exception in FAS_FUND_TRF_FROM_HO_MASTER entry:" + e);
                                    }         
                 }
                      
             try {
                         ps2 = con.prepareStatement("SELECT nvl(max(VOUCHER_NO),0)+1 AS br_id FROM FAS_JOURNAL_MASTER where ACCOUNTING_UNIT_ID=? and " + 
                      "ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=?");
                      
                     
                         ps2.setInt(1, cmbAcc_UnitCode);
                         ps2.setInt(2, cmbOffice_code);
                         ps2.setInt(3, CashBookYear_Cur);
                         ps2.setInt(4, CashBookMonth_Cur);
                        // ps2.setDate(5, txtDoc_date);
                         rs2 = ps2.executeQuery();

                         while (rs2.next()) {
                             max_branch_id = rs2.getInt("br_id");
                           //  max_branch_id++;
                         }
                     
                         System.out.println("Maximum Brach ID is ==" + 
                                            max_branch_id);

                      //   ps2.close();
                   //      rs2.close();

                     } catch (Exception e) {
                         System.out.println("Failed to Fetch Maximum Branch ID " + 
                                            e);
                     }
                     
                 ArrayList<String> H_code = new ArrayList();                                         
                 ArrayList<String> SL_code = new ArrayList();                     
                 ArrayList<String> SL_type = new ArrayList();                     
                 ArrayList<String> CR_DR_type = new ArrayList(); 
                 ArrayList<String> Amt_value = new ArrayList();
                 if(doctype.equalsIgnoreCase("Payment")) {
                 try {
                     ps2 = con.prepareStatement("SELECT VOUCHER_NO,ACCOUNT_HEAD_CODE,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,CR_DR_INDICATOR,TOTAL_AMOUNT,to_char(PAYMENT_DATE,'DD/MM/YYYY') as paymentdate," +
                   "PAID_TO,PAYMENT_DATE as transpaymentdate FROM FAS_PAYMENT_MASTER where ACCOUNTING_UNIT_ID=? and " + 
                  "ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=?");
                     ps2.setInt(1, cmbAcc_UnitCode);
                     ps2.setInt(2, cmbOffice_code);
                     ps2.setInt(3, CashBookYear);
                     ps2.setInt(4, CashBookMonth);
                     ps2.setInt(5, v_code_ins);
                     rs2 = ps2.executeQuery();

                     while (rs2.next()) {
                         subledger = rs2.getInt("SUB_LEDGER_CODE");
                         accnthead=rs2.getInt("ACCOUNT_HEAD_CODE");
                         subledgertype=rs2.getInt("SUB_LEDGER_TYPE_CODE");
                       crdrindicator=rs2.getString("CR_DR_INDICATOR");
                       payAmount=rs2.getDouble("TOTAL_AMOUNT");
                       cbrefno=rs2.getInt("VOUCHER_NO");
                         trans_cbrefdate=rs2.getDate("transpaymentdate");
                         System.out.println("cbrefdate_trans"+trans_cbrefdate);
                         if((rs2.getString("paymentdate"))==null){
                             cbrefdate="";
                         }
                         else{
                           cbrefdate=rs2.getString("paymentdate");}
                       
                       
                         if((rs2.getString("paymentdate"))==null){
                             paydate="";
                         }
                         else{
                           paydate=rs2.getString("paymentdate");}
                           if((rs2.getString("PAID_TO"))==null){
                               paid_to="";
                           }
                           else{
                           paid_to=rs2.getString("PAID_TO");}
                           System.out.println("paymentdate"+paydate);
                           System.out.println("paidto"+paid_to);
                     }
                   

                     System.out.println("subledger is ==" + subledger);
                     System.out.println("Accounthead"+accnthead);
                     System.out.println("Subledger"+subledgertype);
                     System.out.println("crdrindicator"+crdrindicator);
                     System.out.println("cbrefno"+cbrefno);
                     System.out.println("cbrefdate"+cbrefdate);
                     ps2.close();
                     rs2.close();

                 } catch (Exception e) {
                     System.out.println("Failed to Fetch FAS_PAYMENT_MASTER details2222 " + 
                                        e);
                     Flag="false";
                 }
                 cbreftype="P";
                 try {
                     ps2 = 
                   con.prepareStatement("SELECT ACCOUNT_HEAD_CODE,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,CR_DR_INDICATOR,AMOUNT FROM FAS_PAYMENT_TRANSACTION where ACCOUNTING_UNIT_ID=? and " + 
                  "ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and CHEQUE_DD_NO=? and VOUCHER_NO=? ");
                     ps2.setInt(1, cmbAcc_UnitCode);
                     ps2.setInt(2, cmbOffice_code);
                     ps2.setInt(3, CashBookYear);
                     ps2.setInt(4, CashBookMonth);
                     ps2.setString(5, txtCheque_DD_NO );
                     ps2.setInt(6,v_code_ins);
                     rs2 = ps2.executeQuery();
                     System.out.println("SELECT ACCOUNT_HEAD_CODE,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,CR_DR_INDICATOR,AMOUNT FROM FAS_PAYMENT_TRANSACTION where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and " + 
                  "ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and CASHBOOK_YEAR="+CashBookYear+" and CASHBOOK_MONTH="+CashBookMonth+" and CHEQUE_DD_NO="+txtCheque_DD_NO+" and VOUCHER_NO="+v_code_ins+" ");
                     int j=0;
                     while (rs2.next()) {
                     
                         H_code.add(rs2.getString("ACCOUNT_HEAD_CODE"));
                         SL_type.add(rs2.getString("SUB_LEDGER_TYPE_CODE"));
                         SL_code.add(rs2.getString("SUB_LEDGER_CODE"));
                         CR_DR_type.add(rs2.getString("CR_DR_INDICATOR"));
                         Amt_value.add(rs2.getString("Amount"));
                         //System.out.println("H_code ==" + H_code.get(k));
                        // System.out.println("SL_code"+SL_code.get(k));
                       //  System.out.println("SL_type"+SL_type.get(k));
                        // System.out.println("CR_DR_type"+CR_DR_type.get(k));                            
                         j++;
                     }

                    j--;
                    
                     ps2.close();
                     rs2.close();

                 } catch (Exception e) {
                     System.out.println("Failed to Fetch values from payment_transaction-------------->>" + e);
                     Flag="false";
                 }
                 }
                 else if (doctype.equalsIgnoreCase("Fund Transfer")){
                                         try {
                                             ps4 = 
                                         con.prepareStatement("SELECT CR_ACCOUNT_HEAD_CODE,DR_ACCOUNT_HEAD_CODE,TOTAL_AMOUNT FROM FAS_FUND_TRF_FROM_OFFICE where ACCOUNTING_UNIT_ID=? and " +
                                          "ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=? and CHEQUE_DD_NO=?");
                                             ps4.setInt(1, cmbAcc_UnitCode);
                                             ps4.setInt(2, cmbOffice_code);
                                             ps4.setInt(3, CashBookYear);
                                             ps4.setInt(4, CashBookMonth);
                                             ps4.setInt(5, v_code_ins);
                                             ps4.setString(6, txtCheque_DD_NO );
                                             rs4 = ps4.executeQuery();

                                             while (rs4.next()) {
                                            accnthead          = rs4.getInt("CR_ACCOUNT_HEAD_CODE");
                                          accnthead_trans        =rs4.getInt("DR_ACCOUNT_HEAD_CODE");
                                                // subledgertype=rs4.getInt("SUB_LEDGER_TYPE_CODE");
                                              // crdrindicator=rs4.getString("CR_DR_INDICATOR");
                                               payAmount=rs4.getDouble("TOTAL_AMOUNT");
                                                 txtAmount=rs4.getDouble("TOTAL_AMOUNT");
                                             }
                                         }
                                         catch (Exception e) {
                                           System.out.println("Failed to Fetch Fund Transfer " +  e);
                                                                             }
                                     
                                          cbreftype="F";
                                         subledger=0;
                                         subledgertype=0;
                                         
                                     }
                           else if (doctype.equalsIgnoreCase("Fund Transfer HO")){
                                                   
                                                   try {
                                                       ps2 = con.prepareStatement("SELECT VOUCHER_NO,ACCOUNT_HEAD_CODE,CR_DR_INDICATOR,TOTAL_AMOUNT,to_char(DATE_OF_TRANSFER,'DD/MM/YYYY') as paymentdate," +
                                                     "DATE_OF_TRANSFER as transpaymentdate FROM FAS_FUND_TRF_FROM_HO_MASTER where ACCOUNTING_UNIT_ID=? and " + 
                                                    "ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=?");
                                                       ps2.setInt(1, cmbAcc_UnitCode);
                                                       ps2.setInt(2, cmbOffice_code);
                                                       ps2.setInt(3, CashBookYear);
                                                       ps2.setInt(4, CashBookMonth);
                                                       ps2.setInt(5, v_code_ins);
                                                       rs2 = ps2.executeQuery();

                                                       while (rs2.next()) {
                                                           
                                                           accnthead=rs2.getInt("ACCOUNT_HEAD_CODE");
                                                       //    subledgertype=rs2.getInt("SUB_LEDGER_TYPE_CODE");
                                                         crdrindicator=rs2.getString("CR_DR_INDICATOR");
                                                         payAmount=rs2.getDouble("TOTAL_AMOUNT");
                                                         cbrefno=rs2.getInt("VOUCHER_NO");
                                                           trans_cbrefdate=rs2.getDate("transpaymentdate");
                                                           System.out.println("cbrefdate_trans"+trans_cbrefdate);
                                                           if((rs2.getString("paymentdate"))==null){
                                                               cbrefdate="";
                                                           }
                                                           else{
                                                             cbrefdate=rs2.getString("paymentdate");}
                                                         
                                                         
                                                           if((rs2.getString("paymentdate"))==null){
                                                               paydate="";
                                                           }
                                                           else{
                                                             paydate=rs2.getString("paymentdate");}
                                                            
                                                             System.out.println("paymentdate"+paydate);
                                                            // System.out.println("paidto"+paid_to);
                                                       }
                                                     

                                                     //  System.out.println("subledger is ==" + subledger);
                                                       System.out.println("Accounthead"+accnthead);
                                                   //    System.out.println("Subledger"+subledgertype);
                                                       System.out.println("crdrindicator"+crdrindicator);
                                                       System.out.println("cbrefno"+cbrefno);
                                                       System.out.println("cbrefdate"+cbrefdate);
                                                       ps2.close();
                                                       rs2.close();

                                                   } catch (Exception e) {
                                                       System.out.println("Failed to Fetch Fund Transfer Ho " + 
                                                                          e);
                                                       Flag="false";
                                                   }
                                                   cbreftype="FH";
                                                   try {
                                                       ps2 = 
                                                     con.prepareStatement("SELECT ACCOUNT_HEAD_CODE,CR_DR_INDICATOR,AMOUNT FROM FAS_FUND_TRF_FROM_HO_TRN where ACCOUNTING_UNIT_ID=? and " + 
                                                    "ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and CHEQUE_DD_NO=? and VOUCHER_NO=? ");
                                                       ps2.setInt(1, cmbAcc_UnitCode);
                                                       ps2.setInt(2, cmbOffice_code);
                                                       ps2.setInt(3, CashBookYear);
                                                       ps2.setInt(4, CashBookMonth);
                                                       ps2.setString(5, txtCheque_DD_NO );
                                                       ps2.setInt(6,v_code_ins);
                                                       rs2 = ps2.executeQuery();
                                                       System.out.println("SELECT ACCOUNT_HEAD_CODE,CR_DR_INDICATOR,AMOUNT FROM FAS_FUND_TRF_FROM_HO_TRN where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and " + 
                                                    "ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and CASHBOOK_YEAR="+CashBookYear+" and CASHBOOK_MONTH="+CashBookMonth+" and CHEQUE_DD_NO="+txtCheque_DD_NO+" and VOUCHER_NO="+v_code_ins+" ");
                                                       int j=0;
                                                       while (rs2.next()) {
                                                       
                                                           H_code.add(rs2.getString("ACCOUNT_HEAD_CODE"));
                                                          // SL_type.add(rs2.getString("SUB_LEDGER_TYPE_CODE"));
                                                        //   SL_code.add(rs2.getString("SUB_LEDGER_CODE"));
                                                           CR_DR_type.add(rs2.getString("CR_DR_INDICATOR"));
                                                           Amt_value.add(rs2.getString("Amount"));
                                                          System.out.println("H_code =="+ H_code.add(rs2.getString("ACCOUNT_HEAD_CODE")));
                                                          // System.out.println("SL_code"+SL_code.get(k));
                                                         //  System.out.println("SL_type"+SL_type.get(k));
                                                          // System.out.println("CR_DR_type"+CR_DR_type.get(k));                            
                                                           j++;
                                                       }

                                                      j--;
                                                      
                                                       ps2.close();
                                                       rs2.close();

                                                   } catch (Exception e) {
                                                       System.out.println("Failed to Fetch values from FAS_FUND_TRF_FROM_HO_TRN-------------->>" + e);
                                                       Flag="false";
                                                   }
                                  
                                               
                                                    cbreftype="FH";
                                                   subledger=0;
                                                   subledgertype=0;
                                                   
                                               }         
                 
                 if (doctype.equalsIgnoreCase("Payment")) {
                     try {
                         
                 ps4 = con.prepareStatement("select max(SL_NO) AS trn_records  FROM FAS_PAYMENT_TRANSACTION WHERE ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID=? AND CASHBOOK_YEAR=?" +
                                           "AND CASHBOOK_MONTH=? AND VOUCHER_NO=? AND CHEQUE_DD_NO=?");
                     ps4.setInt(1, cmbAcc_UnitCode);
                     ps4.setInt(2, cmbOffice_code);
                     ps4.setInt(3, CashBookYear);
                     ps4.setInt(4, CashBookMonth);
                     ps4.setInt(5, v_code_ins);
                     ps4.setString(6, txtCheque_DD_NO );
                     rs4 = ps4.executeQuery();  
                         while (rs4.next()) {
                         transn_records = rs4.getInt("trn_records");
                               System.out.println(transn_records);
                           }
                     }
                     catch (Exception e) {
                       System.out.println("Failed to Fetch trn_records for FAS_PAYMENT_TRANSACTION " +  e);
                                                         }
                 }
                     if (doctype.equalsIgnoreCase("Fund Transfer Ho")) {
                         try {
                             
                     ps4 = con.prepareStatement("select max(SL_NO) AS trn_records  FROM FAS_FUND_TRF_FROM_HO_TRN WHERE ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID=? AND CASHBOOK_YEAR=?" +
                                               "AND CASHBOOK_MONTH=? AND VOUCHER_NO=? AND CHEQUE_DD_NO=?");
                         ps4.setInt(1, cmbAcc_UnitCode);
                         ps4.setInt(2, cmbOffice_code);
                         ps4.setInt(3, CashBookYear);
                         ps4.setInt(4, CashBookMonth);
                         ps4.setInt(5, v_code_ins);
                         ps4.setString(6, txtCheque_DD_NO );
                         rs4 = ps4.executeQuery();  
                             while (rs4.next()) {
                             transn_records = rs4.getInt("trn_records");
                             System.out.println(transn_records);
                               }
                         }
                         catch (Exception e) {
                           System.out.println("Failed to Fetch trn_records for FAS_FUND_TRF_FROM_HO_TRN " +  e);
                                                                 Flag="false";
                                                             }
                     }
                     else if (doctype.equalsIgnoreCase("Fund Transfer")){
                     transn_records=1;
                     }
                     
                      String sqljournal_pay= "insert into FAS_JOURNAL_MASTER(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID," + 
                      "CASHBOOK_YEAR,CASHBOOK_MONTH, VOUCHER_DATE,VOUCHER_NO,JOURNAL_TYPE_CODE,SUB_LEDGER_CODE, " + 
                      "DEPRECIATION_RATE,CHEQUE_NO,CHEQUE_DATE,CB_REF_TYPE,TOTAL_TRN_RECORDS,REMARKS," + 
                      "MODE_OF_CREATION,CREATED_BY_MODULE,JOURNAL_STATUS,UPDATED_BY_USER_ID,UPDATED_DATE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                   
                     
                    try {
                         ps2 = con.prepareStatement(sqljournal_pay);
                         ps2.setInt(1, cmbAcc_UnitCode);
                         ps2.setInt(2, cmbOffice_code);
                         ps2.setInt(3, CashBookYear_Cur);
                         ps2.setInt(4, CashBookMonth_Cur);
                         ps2.setDate(5,txtCrea_date);
                        // ps2.setTimestamp(5,ts);
                         ps2.setInt(6, max_branch_id);
                         ps2.setInt(7, 56);
                         ps2.setInt(8,subledger);
                         ps2.setInt(9,subledgertype);
                         ps2.setString(10, txtCheque_DD_NO);
                         ps2.setDate(11, cheq_date_ins);
                         ps2.setString(12,cbreftype);
                         ps2.setInt(13,transn_records);
                         ps2.setString(14,"GJV created on Cheque Cancellation for cheqno:"+txtCheque_DD_NO+"cheqdate:"+ oldcheqdate+"payvrno:"+txtVoucher_No+"paidto:"+paid_to+"paiddate:"+paydate+"");
                         ps2.setString(15,"A");
                         ps2.setString(16, "GJV");
                         ps2.setString(17, "L");
                         ps2.setString(18, update_user);
                         ps2.setTimestamp(19, ts);
                         int  z= ps2.executeUpdate();
                            if(z>0){
                                Flag="true";
                                System.out.println("FAS_JOURNAL_MASTER insertion success");
                            }
                            else{
                                Flag="false";
                                System.out.println("FAS_JOURNAL_MASTER insertion failed");
                            }
                             ps2.close();
                       
                        
                         System.out.println("here is journal entry ok");

                     } catch (Exception e) {
                     Flag="false";
                         System.out.println("exception in journal entry:" + e);
                     }
                     System.out.println(sqljournal_pay);
                     int SL_NO=1;
                     try {
                         txtCheque_DD = request.getParameter("txtCheque_DD");
                     } catch (Exception e) {
                         System.out.println("excepion in cheque/dd type :" + e);
                     }
                     String txtCheque_Tran="";
                     
                     int cbrefno_trans=0;
                 String txtCheque_Tran1="",txtCheque_Tran2="";
             {        System.out.println("crdrindicator=="+crdrindicator);
                        
                 if(doctype.equalsIgnoreCase("Payment")){
                     if (crdrindicator_trans.equalsIgnoreCase("CR")) {txtCheque_Tran2="DR";}//cbrefno=0;cbrefdate="";}
                     else if (crdrindicator_trans.equalsIgnoreCase("DR"))  txtCheque_Tran2="CR";//cbrefno_trans=cbrefno;cbref_transdate=cbrefdate;
                     System.out.println("txtCheque_Tran=="+txtCheque_Tran1);
                     if (crdrindicator.equalsIgnoreCase("CR")) {txtCheque_Tran1="DR";}//cbrefno=0;cbrefdate="";}
                     else if (crdrindicator.equalsIgnoreCase("DR"))  txtCheque_Tran1="CR";//cbrefno_trans=cbrefno;cbref_transdate=cbrefdate;
                     System.out.println("txtCheque_Tran=="+txtCheque_Tran1);
                 }
                 else if(doctype.equalsIgnoreCase("Fund Transfer")){
                 
                     txtCheque_Tran1="DR";
                     txtCheque_Tran2="CR";
                     subledger=0;
                     subledgertype=0;
                 }
                 else if(doctype.equalsIgnoreCase("Fund Transfer HO")){
                 
                     txtCheque_Tran1="DR";
                     txtCheque_Tran2="CR";
                     subledger=0;
                     subledgertype=0;
                 }
             
             
                 //-----------journal transaction from payment master----------

                  
               String sqljournaltrans_pay1="insert into FAS_JOURNAL_TRANSACTION(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID," + 
                "CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,SL_NO,ACCOUNT_HEAD_CODE,CR_DR_INDICATOR,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE, " + 
                "CHEQUE_OR_DD,CHEQUE_DD_NO,CHEQUE_DD_DATE,AMOUNT,PARTICULARS,UPDATED_BY_USER_ID,UPDATED_DATE,CB_REF_NO,CB_REF_DATE)" + 
                "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
               // String particulars=""+txtCheque_DD_NO+","che_DD_date",txtVoucher_No,;
               try {
                    ps2 = con.prepareStatement(sqljournaltrans_pay1);
                    ps2.setInt(1, cmbAcc_UnitCode);
                    ps2.setInt(2, cmbOffice_code);
                    ps2.setInt(3, CashBookYear_Cur);
                    ps2.setInt(4, CashBookMonth_Cur);
                    ps2.setInt(5,max_branch_id);
                    ps2.setInt(6,SL_NO);
                    ps2.setInt(7,accnthead);
                    ps2.setString(8,txtCheque_Tran1);
                    ps2.setInt(9,subledgertype);
                    ps2.setInt(10,subledger);
                    ps2.setString(11,txtCheque_DD);
                    ps2.setString(12, txtCheque_DD_NO);
                    ps2.setDate(13, cheq_date_ins);
                    ps2.setDouble(14, Amt_value_ins);
                   // ps2.setDouble(14,txtAmount);
                    ps2.setString(15,"GJV created on Cheque Cancellation for cheqno:"+txtCheque_DD_NO+"cheqdate:"+oldcheqdate+"payvrno:"+txtVoucher_No+"paidto:"+paid_to+"paiddate:"+paydate+"");
                   
                    ps2.setString(16, update_user);
                    ps2.setTimestamp(17, ts);
                    
                    ps2.setInt(18,cbrefno);
                    ps2.setDate(19,trans_cbrefdate);
                    SL_NO++;
                    int  z= ps2.executeUpdate();
                            if(z>0){
                                Flag="true";
                                System.out.println("FAS_JOURNAL_TRANSACTION insertion--------1 success");
                            }
                            else{
                                Flag="false";
                                System.out.println("FAS_JOURNAL_TRANSACTION insertion--------1 failed");
                            }
                             ps2.close();
                       
                    System.out.println("here is journaltransaction entry from payment_master ok");

                } catch (Exception e) {
                        Flag="false";
                    System.out.println("exception in journal entry:" + e);
                }
                // String AH_code[]=request.getParameterValues("H_code");
                // String SL[]=request.getParameterValues("SL_code");
                // String ST[]=request.getParameterValues("SL_type");
                 //String IND[]=request.getParameterValues("CR_DR_type");
                  if(doctype.equalsIgnoreCase("Payment")){  
                 String rad_sub_CR_DR="";
                 int ahead_trans=0,cmbSL_type=0,cmbSL_Code=0,Amt_ind=0;
                 try {
                         
                            System.out.println("doctype for journal trns:"+doctype);
                            
                            System.out.println("H_Code Length ------------------->>>"+H_code.size());                    
                            
                            for(int j=0;j<H_code.size();j++)
                            {
                                String sqljournaltrans_pay="" +
                                "insert into FAS_JOURNAL_TRANSACTION                    \n" + 
                                "(  ACCOUNTING_UNIT_ID,                                 \n" + 
                                "   ACCOUNTING_FOR_OFFICE_ID,                           \n" + 
                                "   CASHBOOK_YEAR,                                      \n" + 
                                "   CASHBOOK_MONTH,                                     \n" + 
                                "   VOUCHER_NO,                                         \n" + 
                                "   SL_NO,                                              \n" + 
                                "   ACCOUNT_HEAD_CODE,                                  \n" + 
                                "   CR_DR_INDICATOR,                                    \n" + 
                                "   SUB_LEDGER_TYPE_CODE,                               \n" + 
                                "   SUB_LEDGER_CODE,                                    \n" + 
                                "   CHEQUE_OR_DD,                                       \n" + 
                                "   CHEQUE_DD_NO,                                       \n" + 
                                "   CHEQUE_DD_DATE,                                     \n" + 
                                "   AMOUNT,                                             \n" + 
                                "   PARTICULARS,                                        \n" + 
                                "   UPDATED_BY_USER_ID,                                 \n" + 
                                "   UPDATED_DATE,                                       \n" + 
                                "   CB_REF_NO,                                          \n" + 
                                "   CB_REF_DATE                                         \n" + 
                                ")                                                      \n" + 
                                "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                             
                                 ps2 = con.prepareStatement(sqljournaltrans_pay);
                                 
                                     try{ahead_trans=Integer.parseInt(H_code.get(j));}catch(Exception e){System.out.println("exception in trans "+e);}
                                     try{rad_sub_CR_DR=CR_DR_type.get(j);}catch(Exception e){System.out.println("exception in CR_DR Indicator "+e);}    
                                     try{cmbSL_type=Integer.parseInt(SL_type.get(j));}catch(Exception e){System.out.println("exception in trans "+e);}
                                     try{cmbSL_Code=Integer.parseInt(SL_code.get(j));}catch(Exception e){System.out.println("exception in trans "+e);}
                                     try{Amt_ind=Integer.parseInt(Amt_value.get(j));}catch(Exception e){System.out.println("exception in trans "+e);}
                                     
                                     System.out.println("------------------------------------------------->>>>>>>>>>>>>>>>>");
                                     System.out.println("AH_code[j] "+ahead_trans);
                                     System.out.println("IND[j] "+rad_sub_CR_DR);
                                     System.out.println("ST[j]"+cmbSL_type);
                                     System.out.println("SL[j]"+cmbSL_Code); 
                                     System.out.println("Amt_ind"+Amt_ind);
                                     System.out.println("------------------------------------------------->>>>>>>>>>>>>>>>>");
                                     String rad_sub_CR_DR2="";
                                     if(doctype.equalsIgnoreCase("Payment")){
                                         if (rad_sub_CR_DR.equalsIgnoreCase("CR")) {rad_sub_CR_DR2="DR";}//cbrefno=0;cbrefdate="";}
                                         else if (rad_sub_CR_DR.equalsIgnoreCase("DR"))  rad_sub_CR_DR2="CR";//cbrefno_trans=cbrefno;cbref_transdate=cbrefdate;
                                         System.out.println("rad_sub_CR_DR=="+rad_sub_CR_DR2);
                                         
                                     }
                                     else if(doctype.equalsIgnoreCase("Fund Transfer")){
                                     
                                         rad_sub_CR_DR2="CR";
                                         subledger=0;
                                         subledgertype=0;
                                         
                                     } 
                                     else if(doctype.equalsIgnoreCase("Fund Transfer HO")){
                                     
                                         rad_sub_CR_DR2="CR";
                                         subledger=0;
                                         subledgertype=0;
                                     }   
                                     
                                 ps2.setInt(1, cmbAcc_UnitCode);
                                 ps2.setInt(2, cmbOffice_code);
                                 ps2.setInt(3, CashBookYear_Cur);
                                 ps2.setInt(4, CashBookMonth_Cur);
                                 ps2.setInt(5, max_branch_id);
                                 ps2.setInt(6,SL_NO);
                                 ps2.setInt(7,ahead_trans);
                                 ps2.setString(8,rad_sub_CR_DR2);
                                 ps2.setInt(9,cmbSL_type);
                                 ps2.setInt(10,cmbSL_Code);
                                 ps2.setString(11,txtCheque_DD);
                                 ps2.setString(12, txtCheque_DD_NO);
                                 ps2.setDate(13, cheq_date_ins);
                                 ps2.setDouble(14,Amt_ind);    
                                 ps2.setString(15,"GJV created on Cheque Cancellation for cheqno:"+txtCheque_DD_NO+"cheqdate:"+ oldcheqdate+"payvrno:"+txtVoucher_No+"paidto:"+paid_to+"paiddate:"+paydate+""); 
                                 ps2.setString(16, update_user);
                                 ps2.setTimestamp(17, ts);                         
                                 ps2.setInt(18,cbrefno_trans);
                                 ps2.setDate(19,cbref_transdate);
                                 
                                 SL_NO++;
                                 int z=ps2.executeUpdate();
                                 if(z>0)
                                 {
                                     Flag="true";
                                     System.out.println("Flag"+Flag);
                                     System.out.println("FAS_JOURNAL_TRANSACTION-->2 insertion success");
                                 }
                                 else
                                 {
                                     Flag="false";
                                     System.out.println("FAS_JOURNAL_TRANSACTION-->2 insertion failed");
                                 }
                                
                                 System.out.println("here is journaltransaction entry ok");
                                 
                                 
                                 }
                        ps2.close();
                            
                     }catch (Exception e) {
                         System.out.println("exception in journal entry:" + e);
                         Flag="false";
                     }
                 if(doctype.equalsIgnoreCase("Fund Transfer Ho")){
                 System.out.println("doctype for journal trns:"+doctype);
                            // String rad_sub_CR_DR="";
                           //  int ahead_trans=0,cmbSL_type=0,cmbSL_Code=0,Amt_ind=0;
                             
                              try {
                                      
                                         System.out.println("doctype for journal trns:"+doctype);
                                         
                                         System.out.println("H_Code Length ------------------->>>"+H_code.size());                    
                                         
                                         for(int j=0;j<H_code.size();j++)
                                         {
                                             String sqljournaltrans_pay="" +
                                             "insert into FAS_JOURNAL_TRANSACTION                    \n" + 
                                             "(  ACCOUNTING_UNIT_ID,                                 \n" + 
                                             "   ACCOUNTING_FOR_OFFICE_ID,                           \n" + 
                                             "   CASHBOOK_YEAR,                                      \n" + 
                                             "   CASHBOOK_MONTH,                                     \n" + 
                                             "   VOUCHER_NO,                                         \n" + 
                                             "   SL_NO,                                              \n" + 
                                             "   ACCOUNT_HEAD_CODE,                                  \n" + 
                                             "   CR_DR_INDICATOR,                                    \n" + 
                                             "   SUB_LEDGER_TYPE_CODE,                               \n" + 
                                             "   SUB_LEDGER_CODE,                                    \n" + 
                                             "   CHEQUE_OR_DD,                                       \n" + 
                                             "   CHEQUE_DD_NO,                                       \n" + 
                                             "   CHEQUE_DD_DATE,                                     \n" + 
                                             "   AMOUNT,                                             \n" + 
                                             "   PARTICULARS,                                        \n" + 
                                             "   UPDATED_BY_USER_ID,                                 \n" + 
                                             "   UPDATED_DATE,                                       \n" + 
                                             "   CB_REF_NO,                                          \n" + 
                                             "   CB_REF_DATE                                         \n" + 
                                             ")                                                      \n" + 
                                             "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                                          
                                              ps2 = con.prepareStatement(sqljournaltrans_pay);
                                              
                                                  try{ahead_trans=Integer.parseInt(H_code.get(j));}catch(Exception e){System.out.println("exception in trans "+e);}
                                                  try{rad_sub_CR_DR=CR_DR_type.get(j);}catch(Exception e){System.out.println("exception in CR_DR Indicator "+e);}    
                                                  try{cmbSL_type=Integer.parseInt(SL_type.get(j));}catch(Exception e){System.out.println("exception in trans "+e);}
                                                  try{cmbSL_Code=Integer.parseInt(SL_code.get(j));}catch(Exception e){System.out.println("exception in trans "+e);}
                                                  try{Amt_ind=Integer.parseInt(Amt_value.get(j));}catch(Exception e){System.out.println("exception in trans "+e);}
                                                  
                                                  System.out.println("------------------------------------------------->>>>>>>>>>>>>>>>>");
                                                  System.out.println("AH_code[j] "+ahead_trans);
                                                  System.out.println("IND[j] "+rad_sub_CR_DR);
                                                  System.out.println("ST[j]"+cmbSL_type);
                                                  System.out.println("SL[j]"+cmbSL_Code); 
                                                  System.out.println("Amt_ind"+Amt_ind);
                                                  System.out.println("------------------------------------------------->>>>>>>>>>>>>>>>>");
                                                  String rad_sub_CR_DR2="";
                                                  if(doctype.equalsIgnoreCase("Fund Transfer HO")){
                                                      if (rad_sub_CR_DR.equalsIgnoreCase("CR")) {rad_sub_CR_DR2="DR";}//cbrefno=0;cbrefdate="";}
                                                      else if (rad_sub_CR_DR.equalsIgnoreCase("DR"))  rad_sub_CR_DR2="CR";//cbrefno_trans=cbrefno;cbref_transdate=cbrefdate;
                                                      System.out.println("rad_sub_CR_DR=="+rad_sub_CR_DR2);
                                                      
                                                  }
                                               
                                              ps2.setInt(1, cmbAcc_UnitCode);
                                              ps2.setInt(2, cmbOffice_code);
                                              ps2.setInt(3, CashBookYear_Cur);
                                              ps2.setInt(4, CashBookMonth_Cur);
                                              ps2.setInt(5, max_branch_id);
                                              ps2.setInt(6,SL_NO);
                                              ps2.setInt(7,ahead_trans);
                                              ps2.setString(8,rad_sub_CR_DR2);
                                              ps2.setInt(9,cmbSL_type);
                                              ps2.setInt(10,cmbSL_Code);
                                              ps2.setString(11,txtCheque_DD);
                                              ps2.setString(12, txtCheque_DD_NO);
                                              ps2.setDate(13, cheq_date_ins);
                                              ps2.setDouble(14,Amt_ind);    
                                              ps2.setString(15,"GJV created on Cheque Cancellation for cheqno:"+txtCheque_DD_NO+"cheqdate:"+ oldcheqdate+"payvrno:"+txtVoucher_No+"paidto:"+paid_to+"paiddate:"+paydate+""); 
                                              ps2.setString(16, update_user);
                                              ps2.setTimestamp(17, ts);                         
                                              ps2.setInt(18,cbrefno_trans);
                                              ps2.setDate(19,cbref_transdate);
                                              
                                              SL_NO++;
                                              int z=ps2.executeUpdate();
                                              if(z>0)
                                              {
                                                  Flag="true";
                                                  System.out.println("Flag"+Flag);
                                                  System.out.println("FAS_JOURNAL_TRANSACTION-->2 insertion success");
                                              }
                                              else
                                              {
                                                  Flag="false";
                                                  System.out.println("FAS_JOURNAL_TRANSACTION-->2 insertion failed");
                                              }
                                             
                                              System.out.println("here is journaltransaction entry ok");
                                              
                                              
                                              }
                                     ps2.close();
                                         
                                  }catch (Exception e) {
                                      System.out.println("exception in journal entry:" + e);
                                      Flag="false";
                                  }
                         }     
              
               
             }
             
             else if(doctype.equalsIgnoreCase("Fund Transfer")) {
                 System.out.println("doctype for journal trns:"+doctype);
                         txtCheque_Tran1="DR";
                         txtCheque_Tran2="CR";
                         crdrindicator="CR";
                         crdrindicator_trans="DR";
                         subledger=0;
                         subledgertype=0;
                         cbrefno=0;cbrefdate="";
                                     String sqljournaltrans_pay="insert into FAS_JOURNAL_TRANSACTION(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID," + 
                                      "CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,SL_NO,ACCOUNT_HEAD_CODE,CR_DR_INDICATOR,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE, " + 
                                      "CHEQUE_OR_DD,CHEQUE_DD_NO,CHEQUE_DD_DATE,AMOUNT,PARTICULARS,UPDATED_BY_USER_ID,UPDATED_DATE,CB_REF_NO,CB_REF_DATE)" + 
                                      "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                                      
                                     try {
                                          ps2 = con.prepareStatement(sqljournaltrans_pay);
                                          ps2.setInt(1, cmbAcc_UnitCode);
                                          ps2.setInt(2, cmbOffice_code);
                                          ps2.setInt(3, CashBookYear_Cur);
                                          ps2.setInt(4, CashBookMonth_Cur);
                                          ps2.setInt(5, max_branch_id);
                                          ps2.setInt(6,SL_NO);
                                          ps2.setInt(7,accnthead_trans);
                                         // ps2.setString(8,txtCheque_Tran);
                                          ps2.setString(8,crdrindicator_trans);
                                          ps2.setInt(9,subledgertype_trans);
                                          ps2.setInt(10,subledger_trans);
                                          ps2.setString(11,txtCheque_DD);
                                          ps2.setString(12, txtCheque_DD_NO);
                                          ps2.setDate(13, cheq_date_ins);
                                          ps2.setDouble(14, Amt_value_ins); 
                                        //  ps2.setDouble(14,txtAmount);
                                          ps2.setString(15,"GJV created on Cheque Cancellation for cheqno:"+txtCheque_DD_NO+"cheqdate:"+ oldcheqdate+"payvrno:"+txtVoucher_No+"paidto:"+paid_to+"paiddate:"+paydate+"");
                                         
                                          ps2.setString(16, update_user);
                                          ps2.setTimestamp(17, ts);
                                          
                                          ps2.setInt(18,cbrefno_trans);
                                          ps2.setDate(19,cbref_transdate);
                                          SL_NO++;
                                       int  z= ps2.executeUpdate();
                                           if(z>0){
                                               Flag="true";
                                                     System.out.println("FAS_JOURNAL_TRANSACTION insertion--------3 success");
                                                   }
                                            else{
                                                Flag="false";
                                                   System.out.println("FAS_JOURNAL_TRANSACTION insertion--------3 failed");
                                                }
                                          ps2.close();
                                          System.out.println("here is journaltransaction entry ok");

                                      } catch (Exception e) {
                                      Flag="false";
                                          System.out.println("exception in journal entry:" + e);
                                      }
             
                                }
                         }
                 if(Flag.equals("true"))
                 {
                      rec_count ++;     
                 }
             } 
             if(rec_count==voucher.size())
                 {
                     try{con.commit();}catch(SQLException sqle){}
                     System.out.println("All records are saved without exception");
                     sendMessage(response, 
                                 " Cross Reference details stored for the voucher no"+voucher, 
                                 "ok");
                 }
                 else {
                      
                     {
                         try{con.rollback();}catch(SQLException sqle){}
                         System.out.println("failed to save all records");
                         sendMessage(response,"The Cheque Cancellation Failed ","ok");
                         //System.out.println("Exception occur due to "+e);
                     }
                    
                 }
             }
             }
             }

             private void sendMessage(HttpServletResponse response, String msg,
                              String bType) {
             try {
             String url = 
                 "org/Library/jsps/MessengerOkBack.jsp?message=" + msg + 
                 "&button=" + bType;
             response.sendRedirect(url);
             response.flushBuffer();
             } catch (IOException e) {
             System.out.println("error in send msg");
             }
             }
             }            
            
           